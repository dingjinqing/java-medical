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
public class ModuleTextImage extends ModuleBase {
    @JsonProperty("ti_type")
    private Byte tiType;

    @JsonProperty("img_style")
    private Byte imgStyle;

    @JsonProperty("img_url")
    private String imgUrl;

    @JsonProperty("title_link")
    private String titleLink;

    @JsonProperty("rich_text")
    private String richText;
}
