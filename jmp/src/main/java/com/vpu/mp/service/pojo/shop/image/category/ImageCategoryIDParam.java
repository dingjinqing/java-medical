package com.vpu.mp.service.pojo.shop.image.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 孔德成
 * @date 2019/7/15 17:27
 */
@Data
@NoArgsConstructor
public class ImageCategoryIDParam {
    @NotNull
    private Integer imgCatId;
}
