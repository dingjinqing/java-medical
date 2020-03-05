package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author lixinguo
 *
 */
@Getter
@Setter
public class ModuleTitle extends ModuleBase {
    @JsonProperty("title")
    private String title;

    @JsonProperty("title_model")
    private Byte titleModel;

    @JsonProperty("title_link")
    private String titleLink;

    @JsonProperty("tit_center")
    private Byte titCenter;

    @JsonProperty("color")
    private String color;

    @JsonProperty("bg_color")
    private String bgColor;

    @JsonProperty("title_date")
    private String titleDate;

    @JsonProperty("title_author")
    private String titleAuthor;

    @JsonProperty("link_title")
    private String linkTitle;

    @JsonProperty("img_url")
    private String imgUrl;
}
