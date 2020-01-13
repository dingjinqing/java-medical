package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2020-01-07 17:32
 **/
@Getter
@Setter
public class ModuleGroupDraw extends ModuleBase {

    /**活动ID */
    @JsonProperty("group_draw_id")
    private Integer groupDrawId;

    /** 是否使用默认活动标题 0 使用 1不使用*/
    @JsonProperty("name_set")
    private Byte nameSet;

    /** 自定义活动标题*/
    @JsonProperty("group_draw_name")
    private String groupDrawName;

    /** 是否显示活动倒计时 0 隐藏 1显示*/
    @JsonProperty("show_clock")
    private Byte showClock;

    /** 字体颜色*/
    @JsonProperty("font_color")
    private String fontColor;

    /** 活动底图 0 默认 1自定义*/
    @JsonProperty("module_bg")
    private Byte moduleBg;

    /** 活动底图路径*/
    @JsonProperty("module_img")
    private String moduleImg;

    //输出属性
    /** */
    @JsonProperty("name")
    private String name;

    /** */
    @JsonProperty("status")
    private Byte status;

    /** */
    @JsonProperty("start_time")
    private Timestamp startTime;

    /** */
    @JsonProperty("end_time")
    private Timestamp endTime;

    /** */
    @JsonProperty("to_num_show")
    private Short toNumShow;

    /**
     * 0正常，1活动不存在，2活动已停用，3活动未开始，4活动已结束
     */
    @JsonProperty("state")
    private Byte state;

    /** 距活动结束的时间 秒 */
    @JsonProperty("surplus_second")
    private Long surplusSecond;

    /** */
    @JsonProperty("join_user_num")
    private Integer joinUserNum;


}
