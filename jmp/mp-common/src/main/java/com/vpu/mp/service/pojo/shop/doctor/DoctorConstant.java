package com.vpu.mp.service.pojo.shop.doctor;

/**
 * @author chenjie
 * @date 2020年08月11日
 */
public class DoctorConstant {
    /**
     * 咨询列表查询关注参数
     */
    public static final Byte ATTENTION_TYPE = 1;
    /**
     * 上班状态
     */
    public static final Byte ON_DUTY = 1;
    /**
     * 休息状态
     */
    public static final Byte NOT_ON_DUTY = 0;
    /**
     * 自动上下班类型
     */
    public static final Byte DOCTOC_DUTY_AUTOMATIC = 0;

    /**
     * 综合排序
     */
    public static final Byte COMMON_SORT_TYPE = 0;
    /**
     * 职称级别排序
     */
    public static final Byte TITLE_SORT_TYPE = 1;
    /**
     * 医师评价排序
     */
    public static final Byte COMMENT_SORT_TYPE = 2;
    /**
     * 响应时间排序
     */
    public static final Byte ANSWER_SORT_TYPE = 3;
    /**
     * 接诊数排序
     */
    public static final Byte CONSULTATION_SORT_TYPE = 4;
    /**
     * 关注数排序
     */
    public static final Byte ATTENTION_SORT_TYPE = 5;
}
