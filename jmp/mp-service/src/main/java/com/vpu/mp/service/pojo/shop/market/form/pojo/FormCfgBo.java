package com.vpu.mp.service.pojo.shop.market.form.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.List;

/**
 * 表单设置pojo
 * @author 孔德成
 * @date 2020/4/28
 */
@Getter
@Setter
@ToString
public class FormCfgBo {
    /**标题*/
    private String page_name;
    /**有效期*/
    private Byte is_forever_valid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp start_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp end_time;
    /**底部导航*/
    private Byte has_bottom;
    /**提交次数限制 1不限制 */
    private Byte post_times;
    /**每天提交值*/
    private Integer day_times;
    /**累计提交值*/
    private Integer total_times;
    /**总反馈数量限制*/
    private Integer get_times;
    /**提交按钮文字*/
    private String notice_name;
    /**提交按钮文字颜色*/
    private String font_color;
    /**提交按钮背景颜色*/
    private String bg_color;
    /**表单海报背景图片*/
    private String bg_img;
    /**自定义跳转checked*/
    private Integer set_own_link;
    /**自定义按钮名称*/
    private String custom_btn_name;
    /**跳转链接*/
    private String custom_link_path;
    /**跳转链接名称*/
    private String custom_link_name;
    /**参与送优惠卷checkbox*/
    private Integer send_coupon;
    /**选择的优惠卷数据列表*/
    private List<SendCoupon> send_coupon_list;
    /**参与送积分选中*/
    private Byte send_score;
    /**输入的送积分*/
    private Integer send_score_number;
    /**授权手机号*/
    private Integer authorized_name;
    /**授权用户信息*/
    private Integer authorized_mobile;

}
