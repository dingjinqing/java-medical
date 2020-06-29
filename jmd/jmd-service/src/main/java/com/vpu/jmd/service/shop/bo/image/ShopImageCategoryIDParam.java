package com.vpu.jmd.service.shop.bo.image;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/16 18:28
 */
@Data
@NoArgsConstructor
public class ShopImageCategoryIDParam {

    @NotNull( message = JsonResultMessage.MSG_IMAGE_CATEGORY_IMGCATID_NOT_NULL)
    private Integer imgCatId;
}
