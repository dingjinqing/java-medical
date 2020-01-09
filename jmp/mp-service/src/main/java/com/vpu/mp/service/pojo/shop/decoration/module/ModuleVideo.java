package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 
 * @author lixinguo
 *
 */
@Data
public class ModuleVideo extends ModuleBase {
    @JsonProperty("video_url")
    private String videoUrl;

    @JsonProperty("video_img")
    private String videoImg;

    @JsonProperty("video_size")
    private Integer videoSize;

    @JsonProperty("video_width")
    private Integer videoWidth;

    @JsonProperty("video_height")
    private Integer videoHeight;

    @JsonProperty("video_title")
    private String videoTitle;

    @JsonProperty("video_poster")
    private Byte videoPoster;

    /**
     * 自定义
     */
    @JsonProperty("img_url")
    private String imgUrl;
}
