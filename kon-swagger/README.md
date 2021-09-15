#### Kon Swagger 服务

##### 使用方式
````java
@EnableKonSwagger
````

####Swagger配置：
````yaml
kon:
  swagger:
    title: 测试Swagger
    description: Swagger描述
    basePackage: com.kon.framework
    contactName: Kon
    contactUrl:
    contactEmail: dlkong@incarcloud.com
    version: 1.0
    header:
      - name: kon-header
        description: 描述
        required: true
      - name: kon1-header
        description: 描述
    query:
        - name: kon-query
          description: 描述
          required: true
        - name: kon1-query
          description: 描述
````