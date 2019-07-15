package com.vpu.mp.service.pojo.shop.image.category;

import com.vpu.mp.config.JacksonConfig;
import com.vpu.mp.service.foundation.JsonResultMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/15 17:23
 */
@Data
@NoArgsConstructor
public class ImageCategoryParam {

    @NotBlank(message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL)
    private String imgCatName;
    @NotNull(message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL)
    private Integer imgCatParentId;
    private Integer sort;

}
