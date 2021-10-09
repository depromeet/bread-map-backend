package com.depromeet.breadmapbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.depromeet.breadmapbackend")
@EnableJpaRepositories("com.depromeet.breadmapbackend")
public class BreadMapBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BreadMapBackendApplication.class, args);
    }

}
