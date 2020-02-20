package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author lixinguo
 *
 */
@Getter
@Setter
public class ModuleGoodsSearch extends ModuleBase {

    /**
     *
     */
    @JsonProperty("list_style")
    private Byte searchStyle;

    /**
     *
     */
    @JsonProperty("search_font")
    private Byte searchFont;

    /**
     *
     */
    @JsonProperty("box_color")
    private String boxColor;

    /**
     *
     */
    @JsonProperty("back_color")
    private String backColor;

    /**
     *
     */
    @JsonProperty("search_sort")
    private Byte searchSort;

    /**
     *
     */
    @JsonProperty("sort_bg_color")
    private String sortBgColor;

    /**
     *
     */
    @JsonProperty("search_position")
    private Byte searchPosition;
}
