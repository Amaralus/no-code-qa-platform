package org.example.nocodetestplatform;

import org.example.nocodetestplatform.rocksdb.config.EnableRocksDbRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRocksDbRepositories
public class NoCodeTestPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoCodeTestPlatformApplication.class, args);
    }

}
