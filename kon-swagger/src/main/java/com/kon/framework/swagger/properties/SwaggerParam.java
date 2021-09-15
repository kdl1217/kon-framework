package com.kon.framework.swagger.properties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Swagger 参数
 *
 * @author Kong, created on 2020-12-21T11:35.
 * @version 1.0.0-SNAPSHOT
 */
@Data
@NoArgsConstructor
public class SwaggerParam {

    private String name;

    private String description;

    private Boolean required = false;
}
