package com.kon.framework.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.kon.framework.mybatis.config.KonMybatisConfiguration.MYBATIS_PREFIX;


/**
 * Kon配置
 *      kon.mybatis.tablePrefix default ""
 * @author Kong, created on 2020-12-18T16:37.
 * @version 1.0.0-SNAPSHOT
 */
@Data
@Configuration
@ConfigurationProperties(MYBATIS_PREFIX)
public class KonMybatisConfiguration {

    public static final String MYBATIS_PREFIX = "kon.mybatis";

    public static String KON_MYBATIS_TABLE_PREFIX = "";

    private String tablePrefix;

    private String scanMapper;

    private boolean cacheEnabled = false;

    private boolean lazyLoadingEnabled = false;

    private boolean multipleResultSetsEnabled = true;

    private boolean useColumnLabel = true;

    private boolean useGeneratedKeys = false;



    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        KonMybatisConfiguration.KON_MYBATIS_TABLE_PREFIX = tablePrefix;
    }


//    @Autowired
//    @Qualifier("dataSource")
//    private DataSource dataSource;
//
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        Properties properties = new Properties();
//        properties.setProperty("", "");
//        sqlSessionFactoryBean.setConfigurationProperties(properties);
//        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(mybatisConfigFilePath));
//
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        String packageSearchPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperPath;
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageSearchPath));
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
//        return sqlSessionFactoryBean;
//
//    }
//
//
//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage(this.scanMapper);
//        return mapperScannerConfigurer;
//    }

}
