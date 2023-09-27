package org.example.nocodetestplatform.rocksdb.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

public class RocksDbRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    private Environment environment;

    @Override
    protected @NotNull Class<? extends Annotation> getAnnotation() {
        return EnableRocksDbRepositories.class;
    }

    @Override
    protected @NotNull RepositoryConfigurationExtension getExtension() {
        return new RocksDbRepositoryConfigurationExtension(environment);
    }

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        super.setEnvironment(environment);
        this.environment = environment;
    }
}
