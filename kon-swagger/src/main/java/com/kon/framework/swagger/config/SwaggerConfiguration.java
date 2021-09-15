package com.kon.framework.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.kon.framework.swagger.properties.SwaggerParam;
import com.kon.framework.swagger.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 *
 * @author Kong, created on 2020-05-14T11:14.
 * @since 1.0.0-SNAPSHOT
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class SwaggerConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(operationParameters())
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                // base，最终调用接口后会和paths拼接在一起
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.swaggerProperties.getBasePackage()))
                .build();
    }

    /**
     * API文档名称和版本
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(this.swaggerProperties.getTitle())
                .description(this.swaggerProperties.getDescription())
                .contact(new Contact(this.swaggerProperties.getContactName(), this.swaggerProperties.getContactUrl(),
                        this.swaggerProperties.getContactEmail()))
                // 版本
                .version(this.swaggerProperties.getVersion())
                .build();
    }

    /**
     * 全局配置参数说明
     *
     * @return RequestParameter
     */
    private List<Parameter> operationParameters() {
        // 请求参数
        List<Parameter> params = new ArrayList<>();

        if (null != this.swaggerProperties.getHeader() && this.swaggerProperties.getHeader().size() > 0) {
            for (SwaggerParam parameter : this.swaggerProperties.getHeader()) {
                params.add(getParameterBuilder(parameter.getName(), parameter.getDescription(),
                        "header", parameter.getRequired()));
            }
        }

        if (null != this.swaggerProperties.getQuery() && this.swaggerProperties.getQuery().size() > 0) {
            for (SwaggerParam parameter : this.swaggerProperties.getQuery()) {
                params.add(getParameterBuilder(parameter.getName(), parameter.getDescription(),
                        "query", parameter.getRequired()));
            }
        }
        return params;
    }


    /**
     * 参数配置
     * @return RequestParameterBuilder
     */
    private Parameter getParameterBuilder(String name, String description, String parameterType, boolean required) {
        return new ParameterBuilder().name(name).description(description).parameterType(parameterType).required(required).build();
    }
}
