package com.vpu.mp.service.foundation.database;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.jooq.impl.DSL.cast;
import static org.jooq.impl.DSL.concat;


/**
 * 补充Dsl缺少的mysql函数
 *
 * @author 孔德成
 * @date 2019/8/1 18:06
 */
public class DslPlus {

    private static final Logger log = LoggerFactory.getLogger(DslPlus.class);


    private static final String FORMAT = "%Y-%m-%d";

    /**
     * 格式化时间到日
     *
     * @param field
     * @return
     */
    public static Field<String> dateFormatDay(Field<Timestamp> field) {
        return DSL.field("date_format({0}, {1})", SQLDataType.VARCHAR, field, DSL.inline(FORMAT));
    }


    /**
     * 格式化时间
     *
     * @param field
     * @param format %Y-%m-%d
     * @return
     */
    public static Field<String> dateFormat(Field<Timestamp> field, String format) {
        log.debug("dateFormat" + format);
        return DSL.field("date_format({0}, {1})", SQLDataType.VARCHAR, field, DSL.inline(format));

    }


    /**
     *  FIND_IN_SET 函数
     * @param format
     * @param field
     * @return
     */
    public static Condition findInSet(String format, Field<?> field) {
        return DSL.condition("FIND_IN_SET({0}, {1})", DSL.inline(format),field);
    }

    /**
     *  FIND_IN_SET 函数
     * @param format
     * @param field
     * @return
     */
    public static Condition findInSet(Field<?> field, String format) {
        return DSL.condition("FIND_IN_SET({0}, {1})", field,DSL.inline(format));
    }

    /**
     *  FIND_IN_SET 函数
     * @param format
     * @param field
     * @return
     */
    public static Condition findInSet(Byte format, Field<?> field) {
        return DSL.condition("FIND_IN_SET({0}, {1})", DSL.inline(format),field);
    }
    /**
     *  FIND_IN_SET 函数
     * @param format
     * @param field
     * @return
     */
    public static Condition findInSet(Integer format, Field<?> field) {
        return DSL.condition("FIND_IN_SET({0}, {1})", DSL.inline(format),field);
    }
    /**
     * mysql 聚合函数
     * group_concat({0} order by {1}   separator '{2}')
     *
     * @param field
     * @param sortField
     * @param separator
     * @param <T>
     * @return
     */
    public static Field<?> groupConCat(Field<?> field, SortField<?> sortField, String separator) {
        // TODO: 2019/8/2    函数有最大长度限制1024 #SET GLOBAL group_concat_max_len = 1024;
        return DSL.field("group_concat({0} order by {1}   separator '{2}')",field,sortField,separator);
    }

    /**
     * mysql CONCAT_WS(separator,str1,str2...)函数
     * @param separator 分隔符
     * @param fields 分割字段
     * @return 组合后的String类型字段
     */
    public static Field<String> concatWs(String separator, Field<?>... fields){
        if (fields.length==0){
            return null;
        }
        Field<String> head = cast(fields[0],String.class);
        head = Arrays.stream(fields).skip(1).reduce(head,(field1,field2)->concatHandler(field1,field2,separator)).cast(String.class);
        return head;
    }
    private static Field<String> concatHandler(Field<?> head,Field<?> next,String separator){
        return concat(concat(cast(head,String.class),separator),cast(next,String.class));
    }

    /**
     * Json extract field.
     *
     * @param field the field
     * @param args  the args
     * @return the field
     */
    public static Field<?> jsonExtract(Field<String> field, String args) {
        return DSL.field("JSON_EXTRACT({0}, {1})", field, args);
    }

}
