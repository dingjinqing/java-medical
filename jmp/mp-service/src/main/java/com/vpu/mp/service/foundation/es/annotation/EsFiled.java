package com.vpu.mp.service.foundation.es.annotation;

import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;




/**
 * 
 * @author 卢光耀
 * @date 2019-09-30 14:28
 *
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface EsFiled {

    /**
     * @return es存储的键值
     */
    EsSearchName name();

    /**
     * @return es中对应的类型
     */
    String type() ;

    /**
     * @return 启用分词器并指定使用的分词器
     */
    String analyzer() default "";
    /**
     * @return 启用分词器并指定使用的分词器
     */
    String searchAnalyzer() default "";

    /**
     * @return 是否建立索引
     */
    boolean index() default true;

    /**
     * @return 缩放因子
     */
    String scaledNumber() default "100";

    /**
     * @return 复制字段
     */
    EsSearchName copyTo() default EsSearchName.NULL ;


}
