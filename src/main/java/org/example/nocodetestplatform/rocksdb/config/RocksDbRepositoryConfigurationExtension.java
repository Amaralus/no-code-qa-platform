package org.example.nocodetestplatform.rocksdb.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.nocodetestplatform.rocksdb.RocksDbKeyValueAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.config.ParsingUtils;
import org.springframework.data.keyvalue.annotation.KeySpace;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.keyvalue.repository.config.KeyValueRepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class RocksDbRepositoryConfigurationExtension extends KeyValueRepositoryConfigurationExtension {

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
    protected AbstractBeanDefinition getDefaultKeyValueTemplateBeanDefinition(
            @NotNull RepositoryConfigurationSource configurationSource) {

        var dataFolder = environment.getRequiredProperty("rocksdb.data-path");

        BeanDefinitionBuilder adapterBuilder = BeanDefinitionBuilder.rootBeanDefinition(RocksDbKeyValueAdapter.class);
        adapterBuilder.addConstructorArgValue(dataFolder);
        adapterBuilder.addConstructorArgValue(scanKeySpaces(configurationSource));

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(KeyValueTemplate.class);
        builder.addConstructorArgValue(ParsingUtils.getSourceBeanDefinition(adapterBuilder, configurationSource.getSource()));
        builder.setRole(BeanDefinition.ROLE_SUPPORT);

        return ParsingUtils.getSourceBeanDefinition(builder, configurationSource.getSource());
    }

    private List<String> scanKeySpaces(RepositoryConfigurationSource configurationSource) {
        var scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(KeySpace.class));

        List<String> keySpaces = configurationSource.getBasePackages()
                .flatMap(basePackage -> scanner.findCandidateComponents(basePackage).stream())
                .map(this::getBeanClass)
                .map(clazz -> clazz.getAnnotation(KeySpace.class).value())
                .toList();

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
