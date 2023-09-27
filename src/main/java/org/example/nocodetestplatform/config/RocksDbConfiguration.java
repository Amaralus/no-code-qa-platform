package org.example.nocodetestplatform.config;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocksDbConfiguration {

    @Value("${rocksdb.data-path}")
    private String rocksDbDataPath;

    @Bean
    public RocksDbLifecycleBean rocksDbLifecycleBean() throws RocksDBException {
        return new RocksDbLifecycleBean(rocksDbDataPath);
    }

    @Bean
    public RocksDB rocksDB(RocksDbLifecycleBean rocksDbLifecycleBean) {
        return rocksDbLifecycleBean.getRocksDB();
    }
}
