package com.kon.framework.demo;

import com.kon.framework.mybatis.annotation.EnableKonMybatis;
import com.kon.framework.netty.annotation.EnableKonNetty;
import com.kon.framework.swagger.annotation.EnableKonSwagger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.kon.framework.demo.App.SCAN_MAPPER_PATH;

/**
 * SpringBoot Launcher
 *
 * @author Kong, created on 2020-12-15T15:30.
 * @version 1.0.0-SNAPSHOT
 */
@EnableKonSwagger
@EnableKonMybatis
@EnableKonNetty
@MapperScan(SCAN_MAPPER_PATH)
@SpringBootApplication
public class App {

    public static final String SCAN_MAPPER_PATH = "com.kon.framework.dao";

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
