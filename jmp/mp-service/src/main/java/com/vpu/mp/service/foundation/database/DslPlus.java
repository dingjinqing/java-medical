package com.vpu.mp.service.foundation.database;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;


/**
 * 补充Dsl缺少的mysql函数
 * @author 孔德成
 * @date 2019/8/1 18:06
 */
public  class   DslPlus  {

    private static final Logger log = LoggerFactory.getLogger(DslPlus.class);


    private static final String FORMAT="format";

    /**
     *
     * @param field
     * @param format %Y-%m-%d
     * @return
     */
    public static Field<String> dateFormatDay(Field<Timestamp> field, String format) {
        log.debug(format);
        return DSL.field("date_format({0}, {1})", SQLDataType.VARCHAR, field, DSL.inline(format));
    }


    /**
     *
     * @param field
     * @param format %Y-%m-%d
     * @return
     */
    public static Field<String> dateFormat(Field<Timestamp> field, String format) {
        log.debug(format);
        return DSL.field("date_format({0}, {1})", SQLDataType.VARCHAR, field, DSL.inline(format));

    }



    public static Condition findInSet(String format,Field<String> field) {
        return DSL.condition("FIND_IN_SET({0}, {1})", field, DSL.inline(format));
    }
}
