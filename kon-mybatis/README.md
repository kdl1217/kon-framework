#### Kon Mybatis 服务

#####目前支持功能

- 通过ID查询对象
- 根据属性查询单条记录
- 保存对象信息
- 修改对象信息
- 通过ID删除对象信息
- 查询全部数据
- 分页查询列表


##### 使用方式

````java
@EnableKonMybatis

……

Service extends BaseRepository<XXXBean, Long>
````

##### 使用注解说明
``````
# 不是数据列
@DisableColumn

# 列名， 不添加注解，默认的列表以下划线的形式查询
# 可以指定在单个或者列表中的查询条件 conditions @Conditions
@KonColumn

# 排序，目前只支持一个属性，以最后一个为主
@KonOrder

# 主键Id注解， 不添加注解，默认注解是id
@KonPrimaryKey

# 表名， 不添加注解，默认的表名以大写转下划线的形式, 可以根据配置加上前缀
@KonTable
``````

##### mybatis配置
````yaml
kon:
  mybatis:
    # 全局的表前缀，表名 t_ + entity驼峰名称 eg:（t_vehicle_type）
    tablePrefix: t_
    # 指定方言，默认mysql
    dialect: mysql
````
