package com.vpu.jmd.service.image.bo.category;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/15 17:29
 */
@Data
@NoArgsConstructor
public class ImageCategoryRenameParam {
    @NotNull(message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL)
    private Integer imgCatId;
    @NotBlank(message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL)
    private String imgCatName;
}
