package com.kon.framework.mybatis.handler;

import cn.hutool.core.util.StrUtil;
import com.kon.framework.mybatis.annotation.Conditions;
import com.kon.framework.mybatis.core.Condition;
import com.kon.framework.mybatis.core.Group;

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
     * 条件
     */
    protected Map<String, Conditions[]> conditionMap;

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
            if (!StrUtil.equals(column, this.primaryKey)) {
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
        return "SELECT " + baseColumn() +" FROM " + this.tableName + whereCondition(Group.ONE);
    }

    /**
     * 根据条件查询条数XMLMapper
     * @return findCount XMLMapper
     */
    protected String selectCountMapper() {
        return "SELECT COUNT(1) FROM " + this.tableName + whereCondition(Group.LIST);
    }

    /**
     * 根据条件查询集合XMLMapper
     * @return findList XMLMapper
     */
    protected String selectList() {
        return "SELECT " + baseColumn() +" FROM " + this.tableName + whereCondition(Group.LIST) + addOrderBy();
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
     * where条件
     * @return  拼接where 条件 eg: <where> <if test="null != fieldName "> AND `column` = #{fieldName} </if> ... </where>\n
     */
    private String whereCondition(Group group) {
        StringBuilder paramColumns = new StringBuilder();
        this.columns.forEach((fieldName, column) -> {
            Conditions[] conditions = this.conditionMap.get(fieldName);
            // 获取当前属性条件
            Condition condition = getCondition(group, conditions);
            // 添加条件
            paramColumns.append(conditionParam(fieldName, column, condition));
        });
        return addWhere(paramColumns.toString());
    }

    /**
     * 获取当前属性条件
     * @param group 组
     * @param conditions    条件
     * @return 条件
     */
    private Condition getCondition(Group group, Conditions[] conditions) {
        Condition condition = Condition.EQUAL;
        if (null != conditions) {
            for (Conditions value : conditions) {
                if (group == value.group()) {
                    condition = value.condition();
                }
            }
        }
        return condition;
    }

    /**
     * 条件参数
     * @param fieldName 字段名
     * @param column    列名
     * @param condition 条件
     * @return  添加条件
     */
    private String conditionParam(String fieldName, String column, Condition condition) {
        String param;
        switch (condition) {
            case LIKE:
                param = likeCondition(column, fieldName);
                break;
            case LT:
                param = ltCondition(column, fieldName);
                break;
            case LE:
                param = leCondition(column, fieldName);
                break;
            case GT:
                param = gtCondition(column, fieldName);
                break;
            case GE:
                param = geCondition(column, fieldName);
                break;
            default:
                param = equalCondition(column, fieldName);
        }
        return param;
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
     * 添加 等于 条件
     * @param column        列名
     * @param fieldName     属性名
     * @return 拼接 where if 条件  eg: <if test="null != fieldName "> AND `column` = #{fieldName} </if>\n
     */
    private String equalCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\"> AND `" + column + "` = #{" + fieldName + "} </if>\n ";
    }

    /**
     * 添加 LIKE 条件
     * @param column        列名
     * @param fieldName     属性名
     * @return 拼接 where if 条件  eg: <if test="null != fieldName "> AND `column` like #{fieldName} </if>\n
     */
    private String likeCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\"> AND `" + column + "` like CONCAT(CONVERT('%', CHAR), " +
                "CONVERT(#{" + fieldName + "}, CHAR), CONVERT('%', CHAR)) </if>\n ";
    }

    /**
     * 添加 小于 条件
     * @param column        列名
     * @param fieldName     属性名
     * @return 拼接 where if 条件  eg: <if test="null != fieldName "> AND `column` < #{fieldName} </if>\n
     */
    private String ltCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\"> AND `" + column + "` <![CDATA[ < ]]> #{" + fieldName + "} </if>\n ";
    }

    /**
     * 添加 小于等于 条件
     * @param column        列名
     * @param fieldName     属性名
     * @return 拼接 where if 条件  eg: <if test="null != fieldName "> AND `column` <= #{fieldName} </if>\n
     */
    private String leCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\"> AND `" + column + "` <![CDATA[ <= ]]> #{" + fieldName + "} </if>\n ";
    }

    /**
     * 添加 大于 条件
     * @param column        列名
     * @param fieldName     属性名
     * @return 拼接 where if 条件  eg: <if test="null != fieldName "> AND `column` > #{fieldName} </if>\n
     */
    private String gtCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\"> AND `" + column + "` <![CDATA[ > ]]> #{" + fieldName + "} </if>\n ";
    }

    /**
     * 添加 大于等于 条件
     * @param column        列名
     * @param fieldName     属性名
     * @return 拼接 where if 条件  eg: <if test="null != fieldName "> AND `column` >= #{fieldName} </if>\n
     */
    private String geCondition(String column, String fieldName) {
        return " <if test=\"null != " + fieldName + "\"> AND `" + column + "` <![CDATA[ >= ]]> #{" + fieldName + "} </if>\n ";
    }

    /**
     * 添加排序
     * @return  排序字符串： eg: ORDER BY `this.ascField` ASC  or ORDER BY `this.descField` DESC
     */
    private String addOrderBy() {
        String orderBy = "";
        if (StrUtil.isNotEmpty(this.ascColumn)) {
            orderBy = " ORDER BY `" + this.ascColumn + "` ASC ";
        } else if (StrUtil.isNotEmpty(this.descColumn)) {
            orderBy = " ORDER BY `" + this.descColumn + "` DESC ";
        }
        return orderBy;
    }

}