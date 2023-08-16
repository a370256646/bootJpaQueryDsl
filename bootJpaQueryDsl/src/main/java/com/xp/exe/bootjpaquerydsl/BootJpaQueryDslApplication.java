package com.xp.exe.bootjpaquerydsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class BootJpaQueryDslApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootJpaQueryDslApplication.class, args);
    }

}
