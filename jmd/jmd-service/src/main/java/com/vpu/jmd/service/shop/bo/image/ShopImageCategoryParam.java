package com.vpu.jmd.service.shop.bo.image;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/16 15:43
 */
@Data
@NoArgsConstructor
public class ShopImageCategoryParam {

    @NotBlank(message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATNAME_NOT_NULL)
    private String imgCatName;
    @NotNull(message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATPARENTID_NOT_NULL)
    private Integer imgCatParentId;
    private Integer sort;

}
