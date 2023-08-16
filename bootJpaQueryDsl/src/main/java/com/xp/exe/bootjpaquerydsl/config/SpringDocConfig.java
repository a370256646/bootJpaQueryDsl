package com.xp.exe.bootjpaquerydsl.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: spring doc config
 * @author: coderXp
 * @date: 2022年11月3日
 * @version: 1.0.0
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI myCustomOpenApi() {
        final String DOC_TITLE = "Spring doc示例";
        final String DOC_DESCRIPTION = "基础spring doc例子展示";
        final String DOC_VERSION = "2.0.0";
        final String DOC_EXTERNAL_DESCRIPTION = "spring doc框架内容描述";
        final String DOC_URL = "http://localhost:9092";
        return new OpenAPI()
                .info(new Info()
                        .title(DOC_TITLE)
                        .description(DOC_DESCRIPTION)
                        .version(DOC_VERSION)
                ).externalDocs(new ExternalDocumentation()
                        .description(DOC_EXTERNAL_DESCRIPTION)
                        .url(DOC_URL)
                );
    }
}
