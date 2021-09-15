package com.kon.framework.mybatis.handler;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * XmlMapper处理者
 *
 * @author Kong, created on 2020-12-18T10:46.
 * @version 1.0.0-SNAPSHOT
 */
public class XmlMapperHandler<T> {

    /**
     * 主键：默认是id
     */
    protected String primaryKey = "id";
    /**
     * Class
     */
    protected Class<T> clazz;
    /**
     * 表名
     */
    protected String tableName;
    /**
     * columns key -> field , value -> column
     */
    protected Map<String, String> columns;
    /**
     * 正序列，默认null
     */
    protected String ascColumn;
    /**
     * 倒序列，默认null
     */
    protected String descColumn;

    /**
     * 保存XMLMapper
     * @return  SaveXmlMapper
     */
    protected String saveXmlMapper() {
        StringBuilder tableColumns = new StringBuilder();
        StringBuilder paramColumns = new StringBuilder();
        this.columns.forEach((fieldName, column) -> {
            tableColumns.append(addIfColumn(column, fieldName));
            paramColumns.append(addIfValues(fieldName));
        });
        return "INSERT INTO " + this.tableName + addColumnTrim(tableColumns.toString()) + addValuesTrim(paramColumns.toString());
    }

    /**
     * 修改XMLMapper
     * @return  SaveXmlMapper
     */
    protected String updateXmlMapper() {
        StringBuilder paramColumns = new StringBuilder();
        this.columns.forEach((fieldName, column) -> {
            if (!StringUtils.equals(column, this.primaryKey)) {
                paramColumns.append(updateIfCondition(column, fieldName));
            }
        });
        return "UPDATE " + this.tableName + addSet(paramColumns.toString()) + wherePrimaryKey();
    }

    /**
     * 删除XMLMapper
     * @return  SaveXmlMapper
     */
    protected String deleteXmlMapper() {
        return "DELETE FROM " + this.tableName + wherePrimaryKey();
    }

    /**
     * 根据ID查询对象XMLMapper
     * @return findById XMLMapper
     */
    protected String selectByIdMapper() {
        return "SELECT " + baseColumn() +" FROM " + this.tableName + wherePrimaryKey();
    }

    /**
     * 根据条件查询对象XMLMapper
     * @return findOne XMLMapper
     */
    protected String selectOneMapper() {
        return "SELECT " + baseColumn() +" FROM " + this.tableName + whereCondition();
    }

    /**
     * 根据条件查询条数XMLMapper
     * @return findCount XMLMapper
     */
    protected String selectCountMapper() {
        return "SELECT COUNT(1) FROM " + this.tableName + whereCondition();
    }

    /**
     * 根据条件查询集合XMLMapper
     * @return findList XMLMapper
     */
    protected String selectList() {
        return "SELECT " + baseColumn() +" FROM " + this.tableName + whereCondition() + addOrderBy();
    }

    /**
     * 查询列
     * @return BaseColumn eg: column1,column2,...,columnX
     */
    private String baseColumn() {
        StringBuilder columnBuilder = new StringBuilder();
        for (String column : this.columns.values()) {
            columnBuilder.append("`").append(column).append("`").append(",");
        }
        return columnBuilder.deleteCharAt(columnBuilder.length() - 1).toString();
    }

    /**
     * 添加if column 列
     * @param column        列名
     * @param fieldName     属性名
     * @return  拼接 ifColumn eg: <if test="null != fieldName">`column`,</if>\n
     */
    private String addIfColumn(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\">`" + column + "`,</if>\n ";
    }

    /**
     * 添加if value
     * @param fieldName     属性名
     * @return  拼接ifValues eg: <if test="null != fieldName">#{fieldName},</if>\n
     */
    private String addIfValues(String fieldName) {
        return " <if test=\"null != " + fieldName + "\">#{" + fieldName + "},</if>\n ";
    }

    /**
     * 添加Column Trim
     * @param param  拼接ifColumn
     * @return  拼接 trim  eg: <trim prefix="(" suffix=")" suffixOverrides=","> ifColumn </trim>
     */
    private String addColumnTrim(String param) {
        return " <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" + param + "</trim>";
    }

    /**
     * 添加Values Trim
     * @param param  拼接 ifValues
     * @return  拼接 trim  eg: <trim prefix="VALUES (" suffix=")" suffixOverrides=","> ifValues </trim>
     */
    private String addValuesTrim(String param) {
        return " <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">" + param + "</trim>";
    }

    /**
     * 修改if条件
     * @param column        列名
     * @param fieldName     属性名
     * @return  拼接if condition  eg: <if test="null != fieldName ">  `column` = #{fieldName},</if>\n
     */
    private String updateIfCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\">`" + column + "` = #{" + fieldName + "},</if>\n ";
    }

    /**
     * 添加Set
     * @param param     参数值
     * @return  拼接 set  eg: <set> param </set>\n
     */
    private String addSet(String param) {
        return " <set>" + param + "</set>\n ";
    }

    /**
     * 添加where
     * @param param     参数值
     * @return  拼接where eg: <where> param </where>\n
     */
    private String addWhere(String param) {
        return " <where>" + param + "</where>\n ";
    }

    /**
     * 添加主键where条件
     * @return  拼接where主键  eg: <where> `primaryKey` = #{primaryKey} </where>\n
     */
    private String wherePrimaryKey() {
        return addWhere("`" + this.primaryKey + "` = #{" + primaryKey + "}");
    }

    /**
     * 添加where if 条件
     * @param column        列名
     * @param fieldName     属性名
     * @return 拼接 where if 条件  eg: <if test="null != fieldName "> AND `column` = #{fieldName} </if>\n
     */
    private String addWhereIfCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\"> AND `" + column + "` = #{" + fieldName + "} </if>\n ";
    }

    /**
     * where条件
     * @return  拼接where 条件 eg: <where> <if test="null != fieldName "> AND `column` = #{fieldName} </if> ... </where>\n
     */
    private String whereCondition() {
        StringBuilder paramColumns = new StringBuilder();
        this.columns.forEach((fieldName, column) -> paramColumns.append(addWhereIfCondition(column, fieldName)));
        return addWhere(paramColumns.toString());
    }

    /**
     * 添加排序
     * @return  排序字符串： eg: ORDER BY `this.ascField` ASC  or ORDER BY `this.descField` DESC
     */
    private String addOrderBy() {
        String orderBy = "";
        if (StringUtils.isNoneEmpty(this.ascColumn)) {
            orderBy = " ORDER BY `" + this.ascColumn + "` ASC ";
        } else if (StringUtils.isNoneEmpty(this.descColumn)) {
            orderBy = " ORDER BY `" + this.descColumn + "` DESC ";
        }
        return orderBy;
    }

}