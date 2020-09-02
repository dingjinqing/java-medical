package com.vpu.mp.service.pojo.shop.video.category;

import javax.validation.constraints.NotNull;

import com.vpu.mp.common.foundation.data.JsonResultMessage;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/15 17:27
 */
@Data
@NoArgsConstructor
public class VideoCategoryIDParam {

    @NotNull( message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL)
    private Integer videoCatId;
}
