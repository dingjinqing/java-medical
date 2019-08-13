package com.vpu.mp.service.pojo.shop.market.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liufei
 * @date 2019/8/9
 * @description 表单元素常量
 */
@Data
public class FormConstant {
    /**
     * 姓名
     */
    public static final String M_INPUT_NAME = "m_input_name";
    /**
     * 手机号
     */
    public static final String M_INPUT_MOBILE = "m_input_mobile";
    /**
     * 地址
     */
    public static final String M_ADDRESS = "m_address";
    /**
     * 邮箱
     */
    public static final String M_INPUT_EMAIL = "m_input_email";
    /**
     * 输入框
     */
    public static final String M_INPUT_TEXT = "m_input_text";
    /**
     * 性别
     */
    public static final String M_SEX = "m_sex";
    /**
     * 选项
     */
    public static final String M_CHOOSE = "m_choose";
    /**
     * 下拉
     */
    public static final String M_SLIDE = "m_slide";
    /**
     * 日期
     */
    public static final String M_DATES = "m_dates";
    /**
     * 图片
     */
    public static final String M_IMGS = "m_imgs";

    public static Map<String, String> ALL = new HashMap<String, String>(){
        {
            put(M_INPUT_NAME, M_INPUT_NAME);
            put(M_INPUT_MOBILE, M_INPUT_MOBILE);
            put(M_ADDRESS, M_ADDRESS);
            put(M_INPUT_EMAIL, M_INPUT_EMAIL);
            put(M_INPUT_TEXT, M_INPUT_TEXT);
            put(M_SEX, M_SEX);
            put(M_CHOOSE, M_CHOOSE);
            put(M_SLIDE, M_SLIDE);
            put(M_DATES, M_DATES);
            put(M_IMGS, M_IMGS);
        }
    };
    public static Map<String, String> SPECIAL = new HashMap<String, String>(){
        {
            put(M_SEX, M_SEX);
            put(M_CHOOSE, M_CHOOSE);
            put(M_SLIDE, M_SLIDE);
        }
    };
    public static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * form_page表中page_content字符串中
     * 模块名为m_choose,m_sex,m_slide三个时，module_value的值会可能存在多个
     * 存储格式统一为："select":{"key":"value",...}
     * 定义此常量用来存取该列表值
     */
    public static final String SELECT = "selects";
    /**
     * 表单模块名常量
     */
    public static final String MODULE_NAME = "module_name";

    /**
     * 新建表单配置的参与送优惠卷列表
     */
    public static final String SEND_COUPON_LIST = "send_coupon_list";
    /**
     * 新建表单配置的参与送积分值
     */
    public static final String SEND_SCORE_NUM = "send_score_num";
    /**
     * 优惠券活动id
     */
    public static final String COUPON_ID = "coupon_id";

}

