package com.kon.framework.swagger.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger 配置
 *
 * @author Kong, created on 2020-12-21T10:34.
 * @version 1.0.0-SNAPSHOT
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "kon.swagger")
public class SwaggerProperties {

    private String title = "API文档";

    private String description = "接口文档";

    private String basePackage = "";

    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String version = "1.0";
    
    private List<SwaggerParam> header;

    private List<SwaggerParam> query;

}
