package com.vpu.mp.service.pojo.shop.image.category;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/15 17:23
 */
@Data
@NoArgsConstructor
public class ImageCategoryParam {

    private String imgCatName;
    private Integer imgCatParentId;
    private Integer sort;

}
