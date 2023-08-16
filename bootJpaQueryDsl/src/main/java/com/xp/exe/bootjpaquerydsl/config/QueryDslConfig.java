package com.xp.exe.bootjpaquerydsl.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: queryDsl QueryFactory config
 * @author: coderXp
 * @date: 2023年06月07日
 * @version: 1.0.0
 */
@Configuration
public class QueryDslConfig {
    @Bean
    public JPAQueryFactory getQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
