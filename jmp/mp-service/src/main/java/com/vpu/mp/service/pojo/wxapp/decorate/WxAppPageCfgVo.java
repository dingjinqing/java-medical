package com.vpu.mp.service.pojo.wxapp.decorate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * @author: 王兵兵
 * @create: 2020-01-02 18:29
 **/
@Data
public class WxAppPageCfgVo {
    @JsonProperty(value = "is_ok")
    Byte isOk;
    @JsonProperty(value = "cat_id")
    Integer catId;
    @JsonProperty(value = "page_name")
    String pageName;
    @JsonProperty(value = "bg_types")
    Byte bgTypes;
    @JsonProperty(value = "has_bottom")
    Byte hasBottom;
    @JsonProperty(value = "show_margin")
    Byte showMargin;
    @JsonProperty(value = "margin_val")
    Integer marginVal;
    @JsonProperty(value = "page_bg_color")
    String pageBgColor;
    @JsonProperty(value = "page_bg_image")
    String pageBgImage;
    @JsonProperty(value = "last_cur_idx")
    Integer lastCurIdx;
    @JsonProperty(value = "pictorial")
    PageCfgPictorial pictorial;


    /**
     * 分享海报相关配置
     */
    @Getter
    @Setter
    public static class PageCfgPictorial{
        /**
         *是否添加分享海报
         */
        @JsonProperty(value = "is_add")
        Byte isAdd;

        /**
         *是所有用户可见还是仅分销员可见
         */
        @JsonProperty(value = "user_visibility")
        Byte userVisibility;

        /**
         *按钮名称
         */
        @JsonProperty(value = "share_btn_name")
        Byte shareBtnName;

        /**
         *分享语
         */
        @JsonProperty(value = "share_desc")
        String shareDesc;

        /**
         *分享图片路径
         */
        @JsonProperty(value = "share_img_path")
        String shareImgPath;

        /**
         *按钮名称长度
         */
        @JsonProperty(value = "name_length")
        Integer nameLength;
    }
}
