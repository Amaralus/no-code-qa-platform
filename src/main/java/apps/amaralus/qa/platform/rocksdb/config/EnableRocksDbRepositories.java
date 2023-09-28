package apps.amaralus.qa.platform.rocksdb.config;

import org.springframework.context.annotation.Import;
import org.springframework.data.keyvalue.repository.support.KeyValueRepositoryFactoryBean;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;
import org.springframework.data.repository.query.QueryLookupStrategy;

import java.lang.annotation.*;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RocksDbRepositoriesRegistrar.class)
public @interface EnableRocksDbRepositories {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Filter[] excludeFilters() default {};

    Filter[] includeFilters() default {};

    String repositoryImplementationPostfix() default "Impl";

    String namedQueriesLocation() default "";

    QueryLookupStrategy.Key queryLookupStrategy() default QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND;

    Class<?> repositoryFactoryBeanClass() default KeyValueRepositoryFactoryBean.class;

    Class<?> repositoryBaseClass() default DefaultRepositoryBaseClass.class;

    String keyValueTemplateRef() default "rocksDbKeyValueTemplate";

    boolean considerNestedRepositories() default false;
}
