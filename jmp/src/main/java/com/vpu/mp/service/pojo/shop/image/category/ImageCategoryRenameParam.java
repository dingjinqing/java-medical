package com.vpu.mp.service.pojo.shop.image.category;

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
    @NotNull
    private Integer imgCatId;
    @NotBlank
    private String imgCatName;
}
