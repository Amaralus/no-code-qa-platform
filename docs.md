# No-Code QA Platform — Архитектура и абстракции

## Обзор

Платформа для управления и выполнения QA-тест-кейсов без необходимости писать код. В качестве хранилища используется RocksDB — встраиваемая key-value
база данных, что исключает необходимость настройки внешнего SQL/NoSQL-сервера. Тест-кейсы организованы внутри проектов, обогащены датасетами (наборы
переменных), окружениями (IT-сервисы, API), метками и выполняются через state-machine движок исполнения.

---

## Технологический стек

| Слой             | Технология                                                              |
|------------------|-------------------------------------------------------------------------|
| Язык             | Java 21                                                                 |
| Фреймворк        | Spring Boot 3.1.4                                                       |
| База данных      | RocksDB 8.5 (встраиваемая, через JNI)                                   |
| Доступ к данным  | Spring Data KeyValue + кастомный `RocksDbKeyValueAdapter`               |
| API              | Spring Web + SpringDoc OpenAPI (`/swagger-ui.html`)                     |
| Фронтенд         | React 16.5 + vanilla webpack 4, отдаётся через Thymeleaf (`index.html`) |
| Сборка           | Maven + `frontend-maven-plugin` (Node 18.18.2)                          |
| Docker           | Jib plugin → `amaralus/qa-platform:<version>` (нет `Dockerfile`)        |
| Маппинг объектов | MapStruct + Lombok                                                      |
| Сериализация     | Jackson (`ObjectMapper` + `JavaTimeModule`)                             |

---

## Структура проекта

```
apps.amaralus.qa.platform
├── common/                 # Базовые абстракции
│   ├── api/Routes.java     #   Константы маршрутов (/api/projects/{project}/...)
│   ├── CrudService.java    #   Generic CRUD-сервис с Entity/Model разделением
│   ├── GenericMapper.java  #   Интерфейс маппера (Entity ↔ Model)
│   ├── ErrorHandler.java   #   @RestControllerAdvice → RFC 7807 ProblemDetail
│   ├── IndexController.java#   Thymeleaf index.html на "/"
│   ├── exception/          #   EntityNotFoundException, EntityAlreadyExistsException
│   └── model/              #   IdSource<I>, CrudModel<I>
├── project/                # Изоляция проектов (ThreadLocal-based multitenancy)
│   ├── context/            #   @InterceptProjectId, ProjectContext, ProjectContextLinked,
│   │                       #   ProjectIdInterceptorAspect, ThreadLocalProjectContext
│   └── linked/             #   ProjectLinkedService, ProjectLinkedModel, ProjectLinkedRepository
├── rocksdb/                # Кастомный RocksDB-адаптер и интеграция с Spring Data
│   ├── config/             #   @EnableRocksDbRepositories, RocksDbRepositoryConfigurationExtension
│   ├── sequence/           #   @GeneratedSequence, AutoIncrementSequenceAspect, SequenceGenerator
│   ├── RocksDbKeyValueAdapter.java  #   extends AbstractKeyValueAdapter
│   ├── RocksDbEntityConverter.java  #   ID → bytes, entity → JSON bytes
│   └── CustomKeyValueTemplate.java
├── testcase/               # CRUD тест-кейсов
│   ├── model/              #   TestCase (entity), TestCaseModel, TestCaseStatus, TestStep
│   ├── TestCaseService.java
│   ├── TestCaseMapper.java #   MapStruct: TestCase ↔ TestCaseModel
│   ├── TestCaseRepository.java
│   └── action/             #   Фабрики шагов (asserts/, debug/, rest/)
├── testplan/               # Тест-планы (коллекции кейсов) + отчётность
│   ├── TestPlan.java, TestPlanModel.java
│   ├── TestPlanService.java, TestPlanMapper.java
│   ├── AutomationDegree.java   #   MANUAL / SEMI_AUTO / AUTO
│   └── report/             #   TestReport, ReportSupplier
├── dataset/                # Наборы переменных (datasets) внутри проекта
│   ├── model/              #   Dataset, DatasetModel, Backlink
│   ├── DatasetService.java, DatasetMapper.java
│   ├── DatasetController.java
│   ├── alias/              #   Псевдонимы полей датасета
│   └── linked/             #   DatasetLinkedService, DatasetSource
├── environment/            # Окружения: IT-сервисы и их API
│   ├── itservice/          #   ITService + model
│   ├── serviceapi/         #   ServiceApi + модель запросов/ответов
│   └── database/           #   Поддержка СУБД-окружений
├── folder/                 # Древовидная структура папок для кейсов
│   ├── FolderService.java
│   └── model/
├── label/                  # Метки с двунаправленной связью с кейсами
│   ├── LabelService.java, LabelController.java
│   └── linked/
├── placeholder/            # Парсинг и резолвинг {{плейсхолдеров}}
│   ├── Placeholder.java    #   Основной парсер: "{{dataset#123:var}}"
│   ├── PlaceholderType.java
│   ├── DefaultPlaceholderType.java  # DATASET, UNKNOWN
│   ├── InvalidPlaceholderException.java
│   ├── resolve/            #   PlaceholderResolver + стратегии резолвинга
│   ├── generate/           #   PlaceholderGeneratorsProvider — генераторы тестовых данных
│   └── ValidationRules.java
├── runtime/                # Движок исполнения тестов
│   ├── ExecutionManager.java  #   Точка входа: run/stop/state
│   ├── execution/          #   State machine + граф исполнения
│   │   ├── ExecutableTestPlan.java
│   │   ├── ExecutableTestCase.java
│   │   ├── ExecutableTestStep.java
│   │   ├── ExecutionGraph.java # DAG стадий
│   │   ├── DefaultStage.java   # Стадия с join-логикой
│   │   ├── Stage.java / StageTask.java
│   │   ├── SimpleTask.java
│   │   ├── RuntimeExecutor.java
│   │   ├── context/        #     TestState (CREATED→RUNNING→...), TestInfo, TestContext
│   │   ├── result/         #     ExecutionResult, TestFailedException
│   │   └── properties/     #     TaskExecutionProperties, TestCaseExecutionProperties
│   ├── schedule/           #   Планировщики и фабрики
│   │   ├── SequentialExecutionScheduler.java
│   │   ├── ParallelExecutionScheduler.java
│   │   ├── TestPlanFactory.java
│   │   └── TestCaseFactory.java
│   ├── action/             #   Фабрики действий шагов
│   │   ├── ActionType.java #     NONE, DEBUG, ASSERT, REST
│   │   ├── StepAction.java
│   │   ├── RestCallFactory.java  #     HTTP-вызовы через OkHttp
│   │   ├── AssertActionFactory.java
│   │   ├── DebugActionFactory.java
│   │   └── ActionFactory.java
│   └── resolve/            #   Контекст резолвинга плейсхолдеров в runtime
```

---

## Основные абстракции и подходы

### 1. CRUD-паттерн: разделение Entity и Model

Каждый домен следует строгой двухслойной модели, где Entity — это DTO для API (то, что видит клиент), а Model — внутренний объект, сохраняемый в
RocksDB.

**Иерархия типов:**

```
IdSource<I>  ←  имеет getId() / setId(I id)
  └── CrudModel<I>  ←  модель, сохраняемая в БД
  └── (Entity)       ←  DTO для API, реализует IdSource<I>
```

**GenericMapper<E, M>** — интерфейс, который каждая доменная MapStruct-маппер реализует:

```java
public interface GenericMapper<E, M> {
    E toEntity(M model);

    M toModel(E entity);

    List<M> toModelList(List<E> entityList);

    List<E> toEntityList(List<M> modelList);
}
```

Пример конкретной реализации (полностью генерируется MapStruct):

```java

@Mapper(componentModel = SPRING)
public interface TestCaseMapper extends GenericMapper<TestCase, TestCaseModel> {
}
```

**CrudService<E, M, I>** — базовый сервис, инкапсулирующий стандартные CRUD-операции. Типовые параметры автоматически резолвятся через рефлексию
`ParameterizedType.getActualTypeArguments()` в конструкторе:

```java
protected final Class<E> entityClass = getGenericType(0);
protected final Class<M> modelClass = getGenericType(1);
protected final Class<I> idClass = getGenericType(2);
```

**Механизм инициализации ID:** В `initIdValue()` сервис устанавливает 0 или 0L в зависимости от типа ID, а AOP-аспект `AutoIncrementSequenceAspect`
перехватывает `save()` и подставляет сгенерированное значение, если поле равно null, 0 или 0L.

```java
// CrudService.initIdValue()
private void initIdValue(M model) {
    Object id;
    if (Long.class == idClass) id = 0L;
    else if (Integer.class == idClass) id = 0;
    else id = model.getId();
    model.setId((I) id);
}
```

**Цепочка вызовов при создании:**

```
Controller → entity → service.create(entity)
  → mapper.toModel(entity) → model
  → beforeCreate(model)        // hook
  → initIdValue(model)         // 0/0L
  → repository.save(model)     // перехватывается аспектом → sequence
  → afterCreate(model)         // hook
  → mapper.toEntity(model) → entity → response
```

**Иерархия сервисов:**

```java
CrudService<E, M, I>                       // базовый CRUD
  └──ProjectLinkedService<E, M, I>        // добавляет фильтрацию по проекту
        └──DatasetLinkedService<E, M, I>  // добавляет авто-создание датасета
```

---

### 2. Изоляция проектов (Project Context)

Все сущности привязаны к проекту. Изоляция реализована через **ThreadLocal**-механизм, чтобы избежать пробрасывания ID проекта через каждый вызов
сервиса.

**Компоненты:**

- **`@InterceptProjectId(parameterName = "project")`** — аннотация на методах контроллера. Указывает, какой path-параметр содержит ID проекта.
- **`ProjectIdInterceptorAspect`** — `@Around`-аспект: до выполнения метода находит значение параметра `{project}` в аргументах и устанавливает его в
  `ThreadLocalProjectContext`, после выполнения (в `finally`) очищает.
- **`ThreadLocalProjectContext`** — реализация `ProjectContext`, хранящая ID проекта в `ThreadLocal<String>`.
- **`ProjectContextLinked`** — абстрактный базовый класс для контроллеров, внедряет `ProjectContext` через `@Autowired setter`.
- **`ProjectLinkedService`** — автоматически проставляет `model.setProject(context.getProjectId())` в `beforeCreate()`, фильтрует все `findById()` и
  `findAll()` по текущему проекту.

```java
// ProjectLinkedService.findById()
public Optional<E> findById(I id) {
    return super.findModelById(id)
            .filter(model -> model.getProject().equals(projectContext.getProjectId()))
            .map(mapper::toEntity);
}
```

Фильтрация выполняется на стороне приложения (а не на уровне БД), так как RocksDB — это key-value хранилище без поддержки запросов по полям модели.

**Проектная привязка моделей:**

```java
public interface ProjectLinkedModel<I> extends CrudModel<I> {
    String getProject();

    void setProject(String project);
}
```

Каждая RocksDB-модель, относящаяся к проекту, реализует этот интерфейс, гарантируя наличие поля `project` в JSON при сериализации/десериализации.

---

### 3. Слой персистентности RocksDB

Вместо стандартной SQL/NoSQL базы данных используется кастомная интеграция Spring Data KeyValue с RocksDB.

#### @EnableRocksDbRepositories

Кастомная аннотация, аналогичная `@EnableMongoRepositories` или `@EnableRedisRepositories`. В параметре
`keyValueTemplateRef = "rocksDbKeyValueTemplate"` указывает, какой `KeyValueTemplate` бин использовать для всех RocksDB-репозиториев.

Регистратор `RocksDbRepositoriesRegistrar` настраивает Spring Data под кастомный адаптер, а `RocksDbRepositoryConfigurationExtension` указывает, что
репозитории сканируются в пакете приложения.

#### RocksDbKeyValueAdapter

Расширяет `AbstractKeyValueAdapter` — ключевой класс Spring Data KeyValue, реализующий все операции через RocksDB API.

**Архитектура хранения:** Каждому классу сущности соответствует отдельный **Column Family** в RocksDB (аналог таблицы в SQL). Column Families
создаются при старте приложения по именам keyspace'ов, которые извлекаются из конфигурации репозиториев.

```java
public RocksDbKeyValueAdapter(String dirPath, Map<String, Class<?>> keySpacesEntityClasses) {
    // Для каждого класса сущности создаётся ColumnFamilyDescriptor
    keySpacesEntityClasses.keySet()
            .forEach(name -> cfDescriptors.add(new ColumnFamilyDescriptor(name.getBytes(), columnFamilyOptions)));

    // RocksDB.open() со списком Column Families
    rocksDB = RocksDB.open(options, dirPath, cfDescriptors, columnFamilyHandleList);
}
```

**Ключи и значения:**

- **Ключ (ID):** преобразуется в байты через `RocksDbEntityConverter`:
    - `Long` → 8 байт (big-endian)
    - `Integer` → 4 байта (big-endian)
    - `String` → UTF-8 байты
- **Значение (Entity):** JSON-сериализация через Jackson `ObjectMapper` с `JavaTimeModule` и отключённым `FAIL_ON_UNKNOWN_PROPERTIES`.

```java
// RocksDbEntityConverter
private final ObjectMapper mapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new JavaTimeModule());
```

**Управление жизненным циклом:** `destroy()` корректно закрывает Column Family Handle'ы, опции и саму RocksDB. При `deleteAllOf()` Column Family
дропается и создаётся заново.

**Конфигурация:** `rocksdb.data-path: ./rocksdb` в `application.yml`. Если директория не существует — RocksDB создаёт её автоматически.

---

### 4. Генерация автоинкрементных последовательностей

Поскольку RocksDB — key-value хранилище без встроенной поддержки автоинкремента, реализован кастомный механизм.

#### @GeneratedSequence

Аннотация на поле модели, указывающая, что значение должно генерироваться автоматически:

```java

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GeneratedSequence {
    String value() default "";  // кастомное имя последовательности
}
```

**Именование:** Если `value` не указано, имя формируется как `{полное_имя_класса}.{имя_поля}`. Например:
`apps.amaralus.qa.platform.testcase.model.TestCaseModel.id`.

#### AutoIncrementSequenceAspect

AOP-аспект, перехватывающий вызовы `KeyValueRepository.save()` и `CrudRepository.saveAll()`:

```java

@Around("execution(public * org.springframework.data.keyvalue.repository.KeyValueRepository.save(..))")
public Object aroundSave(ProceedingJoinPoint joinPoint) {
    // получаем entity из аргументов
    // проверяем, есть ли у его класса поля с @GeneratedSequence
    // если да — инкрементируем последовательность
}
```

**Логика инкремента:** Если текущее значение поля — `null`, `0` или `0L`, то:

1. Вызывается `sequenceGenerator.increment(sequenceName)`
2. Новое значение присваивается полю через `PersistentPropertyAccessor`
3. Если тип поля `Integer`/`int` — значение кастуется к `int`

#### SequenceGenerator

Инициализируется при старте приложения:

1. Сканирует все entity-классы, зарегистрированные в RocksDB
2. Для каждого поля с `@GeneratedSequence` создаёт объект `Sequence`
3. Загружает существующие последовательности из выделенного Column Family `__sequence__`
4. Сливает: удаляет последовательности для полей, которых больше нет; сохраняет существующие значения
5. Записывает все последовательности обратно в `__sequence__` Column Family

```java
// Sequence хранится в RocksDB как:
record Sequence(String name, Class<?> sequenceClass, long value) {
    static final String KEY_SPACE = "__sequence__";
}
```

**Валидация:**

- `Record`-типы не поддерживаются для последовательностей
- Поддерживаются только типы: `Long`, `long`, `Integer`, `int`

---

### 5. Датасет-связанные сущности

Некоторые сущности (например, `TestCase`) могут автоматически получать собственный датасет — именованный набор переменных, доступный во время
выполнения теста.

#### DatasetLinkedService

```java
public abstract class DatasetLinkedService<
        E extends IdSource<I> & DatasetSource,
        M extends ProjectLinkedModel<I> & DatasetSource,
        I> extends ProjectLinkedService<E, M, I> {
```

**При создании** (`afterCreate`): автоматически создаёт `Dataset` с именем `"System dataset for entity <class>#<id>"` и обратной ссылкой (`Backlink`)
на владельца. ID созданного датасета сохраняется в поле `dataset` модели.

**При удалении:** загружает сущность, получает ID датасета и каскадно удаляет его через `DatasetService.delete()`.

#### DatasetSource

Интерфейс, который должна реализовать модель, чтобы участвовать в этом паттерне:

```java
public interface DatasetSource {
    Long getDataset();

    void setDataset(Long dataset);
}
```

Во время выполнения теста через `TestContext.putToDataset(property, value)` и `TestContext.getFromDataset(property)` шаги могут читать и писать
переменные в датасет тест-кейса.

---

### 6. Движок исполнения тестов (Runtime Engine)

#### Архитектура состояний (State Machine)

```java
public enum TestState {
    CREATED,    // создан, готов к запуску
    RUNNING,    // выполняется
    COMPLETED,  // успешно завершён
    FAILED,     // упал (assertion error)
    CANCELED,   // отменён
    ERROR,      // неожиданная ошибка
    UNKNOWN     // не найден
}
```

**Диаграмма переходов:**

```
CREATED → RUNNING → COMPLETED
                  → FAILED
                  → CANCELED
Любое состояние может перейти в ERROR при исключении.
UNKNOWN — терминальное состояние для тест-плана, отсутствующего в карте запущенных.
```

#### Исполняемые объекты

Трёхуровневая иерархия, где каждый уровень наследует `ExecutableTestSupport`:

```
ExecutableTestPlan         ← тест-план целиком
  └── ExecutableTestCase   ← отдельный тест-кейс
        └── ExecutableTestStep  ← отдельный шаг
```

Каждый содержит:

- `TestInfo` — id, имя, проект
- `Timer` — замер времени выполнения
- `TestState` — текущее состояние
- `TestReport` — отчёт о выполнении (собирает вложенные отчёты)

#### ExecutionGraph — граф исполнения

`ExecutionGraph` — направленный ациклический граф (DAG) узлов, где каждый узел — это `Stage`.

**Stage** — обёртка над `StageTask`:

- Имеет список входных и выходных стадий
- Реализует **join-семантику**: стадия запускает `StageTask.execute()` только после того, как **все** входные стадии завершились

```java
public class DefaultStage implements Stage {
    private final AtomicInteger inputCounter = new AtomicInteger();
    private final List<Stage> inputStages = new ArrayList<>();

    public void execute() {
        // Увеличиваем счётчик. Если он равен количеству входов — запускаем задачу.
        if (!isCanceled() && (inputStages.isEmpty() || inputCounter.incrementAndGet() == inputsCount())) {
            if (stageTask.checkExecutionCondition())
                stageTask.execute();
            else {
                cancel();
                taskFinishCallback();  // пропускаем, но сообщаем о завершении
            }
        }
    }

    public void taskFinishCallback() {
        inputCounter.set(0);
        outputStages.forEach(Stage::execute);  // запускаем все выходные стадии
    }
}
```

Это позволяет строить как последовательные, так и параллельные цепочки:

- Последовательное исполнение: `A → B → C` (каждая стадия имеет один вход и один выход)
- Параллельное: `A → (B, C) → D` (B и C запускаются одновременно, D ждёт обе)

#### Планировщики

**SequentialExecutionScheduler** — строит линейную цепочку стадий. Используется когда `isParallel == false`:

```java
// Строит цепочку: [initialTask → task1 → task2 → ... → taskN → finalTask]
for(int i = 0; i <stages.

size() -1;i++)
        Stage.

link(stages.get(i),stages.

get(i +1));
```

**ParallelExecutionScheduler** — строит граф, где тест-кейсы запускаются параллельно. Для каждого запуска через `RuntimeExecutor` (пул потоков).

**TestPlanFactory** — собирает исполняемый тест-план:

1. Определяет `AutomationDegree` каждого тест-кейса (на основе `ActionType` его шагов)
2. Фильтрует кейсы по степени автоматизации
3. Создаёт `ExecutableTestCase` через `TestCaseFactory`
4. Выбирает планировщик (sequential/parallel)
5. Инициализирует `TestContext` для каждого шага с `PlaceholderResolver`

**AutomationDegree — определение степени автоматизации:**

```java
if(все шаги
с ActionType.NONE)  →MANUAL
if(
нет шагов
с ActionType.NONE) →AUTO
иначе                           →SEMI_AUTO
```

На текущий момент поддерживается только `AUTO`; при других значениях тест-план принудительно переключается на `AUTO` с предупреждением в логе.

#### Процесс выполнения

```
ExecutionManager.run(testPlanId)
  │
  ├─ testPlanService.findById(testPlanId) → TestPlan
  │
  ├─ testPlanFactory.produce(testPlan) → ExecutableTestPlan
  │     ├─ TestCaseFactory.produce() для каждого TestCase
  │     ├─ построение ExecutionGraph через планировщик
  │     └─ инициализация TestContext (PlaceholderResolver + Dataset)
  │
  ├─ executableTestPlan.execute()
  │     ├─ state = RUNNING
  │     ├─ timer.start()
  │     └─ executionGraph.execute()
  │           └─ последовательное/параллельное выполнение стадий
  │
  ├─ При ошибке шага:
  │     ExecutableTestCase.testStepFailCallback()
  │       ├─ failed = true, state = FAILED
  │       ├─ executionGraph.cancel() ← отмена остальных шагов
  │       └─ executionGraphFinishedCallback()
  │
  ├─ При завершении:
  │     executableTestPlan.executionGraphFinishedCallback()
  │       ├─ state = COMPLETED
  │       ├─ timer.stop()
  │       └─ finishCallback.accept(this)
  │
  └─ finishCallback (в ExecutionManager):
        ├─ удаление из runningTestPlans
        └─ testReportService.create(report)
```

**Управление параллельностью:** `RuntimeExecutor` — абстракция пула потоков (скорее всего, `ThreadPoolTaskExecutor` или аналогичный). Внедряется в
планировщики для параллельного запуска стадий.

---

### 7. Система плейсхолдеров (Placeholder)

Позволяет параметризовать шаги тестов через синтаксис `{{ ... }}`.

#### Парсер Placeholder

```java
Placeholder.parse("{{dataset#123:var}}")
// → location = "dataset"
// → type = DATASET (определяется по реестру PlaceholderType)
// → id = 123
// → variable = "var"
```

**Синтаксис:**

```
{{location}}              — без ID и переменной (UNKNOWN)
{{location:variable}}     — с переменной, без ID
{{location#id:variable}}  — с ID и переменной
```

**Валидация (все случаи выбрасывают InvalidPlaceholderException):**

- Незакрытые скобки: `{{}`
- Пустой плейсхолдер: `{{}}`
- Location с разделителем `#` без ID: `{{abc#}}`
- Location с `#` и ID, но без переменной: `{{abc#123}}`
- Location с `:` без переменной: `{{abc:}}`
- Location с `#` и `:` без переменной: `{{abc#123:}}`

**Типы плейсхолдеров:**

```java
public enum DefaultPlaceholderType implements PlaceholderType {
    DATASET,  // {{dataset#<id>:<var>}}
    UNKNOWN   // всё остальное
}
```

Реестр типов расширяемый через интерфейс `PlaceholderType`.

#### Резолвинг (разрешение) плейсхолдеров

В runtime каждый шаг получает `TestContext`, содержащий `PlaceholderResolver`. Резолвер подставляет значения из:

- Датасета тест-кейса (через `putToDataset` / `getFromDataset`)
- Контекста выполнения (`TestInfo`)
- Генераторов случайных данных

#### Генерация тестовых данных

`PlaceholderGeneratorsProvider` — набор генераторов случайных/фиктивных данных для подстановки в плейсхолдеры (например, генерация GUID, случайного
числа, даты, email и т.д.).

---

### 8. Система действий (Action System)

Каждый шаг тест-кейса содержит `ActionType`, определяющий, какое действие выполнять:

| ActionType | Назначение                             | Реализация                 |
|------------|----------------------------------------|----------------------------|
| `NONE`     | Ручной шаг, автоматизация отсутствует  | Не требует обработчика     |
| `DEBUG`    | Отладочный вывод в процессе выполнения | `DebugActionFactory`       |
| `ASSERT`   | Проверка/assertion на данных           | `AssertActionFactory`      |
| `REST`     | HTTP-вызов к внешнему API (REST, gRPC) | `RestCallFactory` (OkHttp) |

**ActionFactory — фабричный метод:**

```java
public interface ActionFactory {
    boolean supports(ActionType actionType);

    StepAction create(TestStep testStep);
}
```

Каждая фабрика регистрируется в общем реестре, и по `ActionType` выбирается соответствующая. Это позволяет легко добавлять новые типы действий без
изменения существующего кода.

---

### 9. Обработка ошибок (Error Handling)

Централизованная обработка через `@RestControllerAdvice`:

```java

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    public ErrorResponse captureException(Exception e) {
        var errorId = UUID.randomUUID();
        log.error("Exception has occurred, errorId={}", errorId, e);
        return new ServerErrorException(buildErrorMessage(errorId, e), e);
    }
    // ...
}
```

**Маппинг исключений:**

| Исключение                        | HTTP Status    | Формат ответа                      |
|-----------------------------------|----------------|------------------------------------|
| `EntityNotFoundException`         | 404            | `ProblemDetail`                    |
| `EntityAlreadyExistsException`    | 400            | `ProblemDetail`                    |
| `MethodArgumentNotValidException` | 400            | `ProblemDetail`                    |
| `ErrorResponseException`          | (пропускается) | Делегируется                       |
| Любое другое `Exception`          | 500            | `ProblemDetail` с `errorId` (UUID) |

Ответы соответствуют **RFC 7807** (`application/problem+json`). При 500-х ошибках клиент получает только `errorId` для поиска в логах и сообщение
корневой причины.

---

### 10. API маршруты

Все маршруты централизованы в `Routes.java`:

```java
public final class Routes {
    public static final String API = "/api";
    public static final String PROJECTS = API + "/projects";
    public static final String PROJECTS_VARIABLE = PROJECTS + "/{project}";
    public static final String DATASETS = PROJECTS_VARIABLE + "/datasets";
    public static final String ENVIRONMENTS = PROJECTS_VARIABLE + "/environments";
    public static final String IT_SERVICES = PROJECTS_VARIABLE + "/it-services";
    public static final String ALIASES = PROJECTS_VARIABLE + "/aliases";
    public static final String REPORTS = PROJECTS_VARIABLE + "/{testPlanId}/reports";
}
```

Все маршруты версионируются через префикс `/api` и привязаны к проекту через `/{project}`. Контроллеры наследуют `ProjectContextLinked` и используют
`@InterceptProjectId` для автоматической установки контекста проекта.

---

### 11. Фронтенд

React 16.5 приложение собирается через vanilla webpack 4 с Babel-транспиляцией. Точка входа — `src/main/js/app.js`.

**Процесс сборки:**

1. Maven плагин `frontend-maven-plugin` устанавливает Node 18.18.2 и npm
2. `npm run watch` запускает webpack в режиме watch с `--output ./target/classes/static/built/bundle.js`
3. Spring Boot раздаёт статику из `classpath:/static/`
4. Thymeleaf-шаблон `index.html` подключает `/built/bundle.js`

**Текущее состояние:** Фронтенд на начальной стадии — `app.js` содержит закомментированные заглушки и минимальный React-компонент.

---

## Сборка и запуск

```bash
# Полная сборка (тесты + Node + фронтенд + JAR)
mvn clean install

# Запуск бэкенда (Spring Boot на :8080)
mvn spring-boot:run

# Watch-режим фронтенда (требуется хотя бы один mvn install)
cd node/node && ../npm run watch

# Docker-образ
mvn compile jib:build \
  -Djib.to.auth.username=<docker_user> \
  -Djib.to.auth.password=<docker_pass>
```

**CI/CD:**

- `maven-build.yml` — запускается на push/PR в main: `mvn clean install`
- `docker-publish.yml` — запускается на push в main: `mvn compile jib:build` с авторизацией Docker Hub

---

## Тестирование

- JUnit 5 в `src/test/java/`
- Не требуется внешняя инфраструктура — RocksDB встраиваемый
- Существующие тесты: парсинг плейсхолдеров (`PlaceholderTest`), ручной функциональный тест (`FunctionalManualTest`)
- Запуск: `mvn test`

---

## Конфигурация

Единственный файл `application.yml` в `src/main/resources/`:

```yaml
server:
  port: 8080
  forward-headers-strategy: framework
spring:
  application:
    name: qa-platform
management:
  endpoints:
    web:
      exposure:
        include: "*"
  info:
    git:
      enabled: true
      mode: full
  endpoint:
    health:
      show-details: always
rocksdb:
  data-path: ./rocksdb
```

- Actuator доступен полностью, включая `/info` с git-метаданными и `/health` с деталями
- RocksDB хранит данные в директории `./rocksdb` относительно рабочей директории приложения
