package com.vpu.mp.service.pojo.saas.shop.image;

import javax.validation.constraints.NotNull;

import com.vpu.mp.common.foundation.data.JsonResultMessage;

import lombok.Data;
import lombok.NoArgsConstructor;

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
