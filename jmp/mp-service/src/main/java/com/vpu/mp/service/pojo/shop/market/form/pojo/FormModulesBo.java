package com.vpu.mp.service.pojo.shop.market.form.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 表单模块
 * @author 孔德成
 * @date 2020/4/29
 */
@Getter
@Setter
@ToString
public class FormModulesBo {

    /**
     * 标号
     */
    private String cur_idx;
    /**
     * 模块名
     */
    private String module_name;
    /**
     * 是否点击确认
     */
    private Byte ok_ajax;
    /**
     * 条件验证
     */
    private Byte confirm;
    /**
     * 标题文字input值
     */
    private String form_title;
    /**
     * 提示语input值
     */
    private String placeholder;
    /**
     * 展现形式radio
     */
    private Byte image_type;
    /**
     *展现形式
     */
    private Byte show_types;
    private Byte show_type;
    /**
     * 展现形式
     */
    private Integer with_detail;
    /**
     * 图标图片地址
     */
    private String name_url;
    /**
     * 校验最小值
     */
    private Integer least_number;
    /**
     * 校验最大值
     */
    private Integer most_number;
    /**
     * 图片校验 最多上传六个
     */
    private Integer max_number;
    /**
     * 宽度
     */
    private Integer width_size;
    /**
     * 图片 大小校验
     */
    private Integer size_types;
    /**
     * 高度
     */
    private Integer height_size;
    /**
     * 轮播图 图片地址
     */
    private List<FormImgItem> img_items;
    /**
     * 预览原图
     */
    private Byte is_preview;
    /**
     * 富文本内容
     */
    private String rich_text;
    /**
     * 广告 文本 标题
     */
    private String title;
    /**
     * 广告 连接
     */
    private String title_link;
    /**
     * 广告 图片
     */
    private String img_url;
    private Integer fonts_size;
    private String fonts_color;
    private String bgs_color;
    private String show_pos;
    private String blank_height;
    private String sps_icon;
    private Byte align_type;
    private String color;
    private String background_color;
    /**
     * 选择项
     */
    private Map<String,String> selects;
}
