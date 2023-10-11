package apps.amaralus.qa.platform.rocksdb.config;

import apps.amaralus.qa.platform.rocksdb.CustomKeyValueTemplate;
import apps.amaralus.qa.platform.rocksdb.RocksDbKeyValueAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.config.ParsingUtils;
import org.springframework.data.keyvalue.annotation.KeySpace;
import org.springframework.data.keyvalue.repository.config.KeyValueRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationSource;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class RocksDbRepositoryConfigurationExtension extends KeyValueRepositoryConfigurationExtension {
    private static final String ROCKS_DB_ADAPTER_BEAN_NAME = "rocksDbKeyValueAdapter";
    private final Environment environment;
    @Override
    public @NotNull String getModuleName() {
        return "RocksDB";
    }

    @Override
    protected @NotNull String getModulePrefix() {
        return "rocksdb";
    }

    @Override
    protected @NotNull String getDefaultKeyValueTemplateRef() {
        return "rocksDbKeyValueTemplate";
    }

    @Override
    public void registerBeansForRoot(@NotNull BeanDefinitionRegistry registry,
                                     @NotNull RepositoryConfigurationSource configurationSource) {
        var dataFolder = environment.getRequiredProperty("rocksdb.data-path");

        var adapterBeanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RocksDbKeyValueAdapter.class);
        adapterBeanDefinitionBuilder.addConstructorArgValue(dataFolder);
        adapterBeanDefinitionBuilder.addConstructorArgValue(scanKeySpaces(configurationSource));

        registry.registerBeanDefinition(ROCKS_DB_ADAPTER_BEAN_NAME,
                ParsingUtils.getSourceBeanDefinition(adapterBeanDefinitionBuilder, configurationSource.getSource()));

        super.registerBeansForRoot(registry, configurationSource);
    }

    @Override
    protected AbstractBeanDefinition getDefaultKeyValueTemplateBeanDefinition(
            @NotNull RepositoryConfigurationSource configurationSource) {

        var templateBeanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(CustomKeyValueTemplate.class);
        templateBeanDefinitionBuilder.addConstructorArgReference(ROCKS_DB_ADAPTER_BEAN_NAME);
        templateBeanDefinitionBuilder.setRole(BeanDefinition.ROLE_SUPPORT);

        return ParsingUtils.getSourceBeanDefinition(templateBeanDefinitionBuilder, configurationSource.getSource());
    }

    private Map<String, Class<?>> scanKeySpaces(RepositoryConfigurationSource configurationSource) {
        var scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(KeySpace.class));

        Map<String, Class<?>> keySpaces = configurationSource.getBasePackages().stream()
                .flatMap(basePackage -> scanner.findCandidateComponents(basePackage).stream())
                .map(this::getBeanClass)
                .collect(Collectors.toMap(
                        clazz -> clazz.getAnnotation(KeySpace.class).value().isEmpty()
                                ? clazz.getName()
                                : clazz.getAnnotation(KeySpace.class).value(),
                        Function.identity()
                ));

        log.debug("Key spaces found: {}", keySpaces);
        return keySpaces;
    }

    private Class<?> getBeanClass(BeanDefinition definition) {
        try {
            return Class.forName(definition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new RocksDbInitializationException(e);
        }
    }
}
