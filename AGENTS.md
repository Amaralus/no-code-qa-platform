# Agent Guidelines

## Build & Run

```bash
# Full build (installs Node 18.18.2 via frontend-maven-plugin)
mvn clean install

# Run backend
mvn spring-boot:run                    # Spring Boot on :8080

# Frontend watch (requires mvn install first to get Node in node/)
cd node/node && ../npm run watch       # → target/classes/static/built/bundle.js
```

CI runs `mvn clean install` only (no separate lint/typecheck steps). Docker image built via Jib (`mvn compile jib:build`) in a separate workflow.

## Architecture

- **Backend**: Spring Boot 3, Java 21, RocksDB 8.5 (via custom `@EnableRocksDbRepositories`), Spring Data KeyValue
- **Frontend**: React 16, vanilla webpack 4, Thymeleaf index.html at `/`
- **API**: Spring Web + SpringDoc OpenAPI at `/swagger-ui.html`
- **Persistence**: `./rocksdb` dir (configurable via `rocksdb.data-path` in `application.yml`)
- **Docker**: Jib plugin → `amaralus/qa-platform:<version>`, no `Dockerfile`
- **Root package**: `apps.amaralus.qa.platform`

## Key Patterns

- **CRUD**: `CrudService<E, M, I>` + `GenericMapper<E, M>` interface + MapStruct (`@Mapper(componentModel = SPRING)`). Each domain has a Service
  extending CrudService or DatasetLinkedService.
- **Entity/Model split**: Domain objects use an entity (API DTO) and a model (RocksDB-persisted), mapped via MapStruct.
- **Project isolation**: Controllers extend `ProjectContextLinked`, methods annotated with `@InterceptProjectId` resolve the project path variable
  into a ThreadLocal via `ProjectIdInterceptorAspect`.
- **Dataset-linked entities**: Entities that belong to a project's dataset extend `DatasetLinkedService` for automatic link management.
- **Sequence generation**: `@GeneratedSequence` annotation + `AutoIncrementSequenceAspect` for auto-increment IDs stored in RocksDB.
- **Lombok**: Used extensively (`@Slf4j`, `@RequiredArgsConstructor`, `@Getter`, `@AllArgsConstructor`).
- **Error handling**: `@RestControllerAdvice` → `ErrorHandler` returns RFC 7807 `ProblemDetail` responses.

## Domain Packages

| Package        | Purpose                                                                                             |
|----------------|-----------------------------------------------------------------------------------------------------|
| `testcase/`    | Test case CRUD + labels + steps                                                                     |
| `testplan/`    | Test plans (collections of cases) + reporting                                                       |
| `dataset/`     | Datasets per project                                                                                |
| `environment/` | Environments + IT services + service APIs                                                           |
| `folder/`      | Folder tree structure                                                                               |
| `label/`       | Labels with bidirectional case linking                                                              |
| `placeholder/` | `{{placeholder}}` resolution (e.g. `{{dataset#123:var}}`)                                           |
| `runtime/`     | Execution engine: `ExecutionManager`, `ExecutableTestPlan`, state machine (`TestState`), scheduling |
| `rocksdb/`     | Custom RocksDB adapter: `RocksDbKeyValueAdapter`, `RocksDbEntityConverter`, repository config       |

## Testing

- Tests in `src/test/java/`, run via `mvn test`
- Minimal placeholder tests exist (JUnit 5)
- No integration test fixtures or test DB setup needed (RocksDB is embedded)

## Config

- `application.yml` at `src/main/resources/` — no `.properties` files
- Server port: 8080, full Actuator exposure, git info enabled
