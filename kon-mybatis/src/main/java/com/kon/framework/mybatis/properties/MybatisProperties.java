package com.kon.framework.mybatis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 配置信息
 *
 * @author kon, created on 2021/9/15T13:58.
 * @version 1.0.0-SNAPSHOT
 */
@Data
@Configuration
@ConfigurationProperties(MybatisProperties.MYBATIS_PREFIX)
public class MybatisProperties {

    public static final String MYBATIS_PREFIX = "kon.mybatis";

    public static String KON_MYBATIS_TABLE_PREFIX = "";

    /**
     * 表名前缀
     */
    private String tablePrefix;
    /**
     * 方言
     */
    private String dialect;

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        MybatisProperties.KON_MYBATIS_TABLE_PREFIX = tablePrefix;
    }

}
