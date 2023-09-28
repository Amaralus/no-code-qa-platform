package apps.amaralus.qa.platform;

import apps.amaralus.qa.platform.rocksdb.config.EnableRocksDbRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRocksDbRepositories
public class NoCodeTestPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoCodeTestPlatformApplication.class, args);
    }

}
