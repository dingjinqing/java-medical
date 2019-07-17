package com.vpu.mp.service.pojo.saas.shop.image;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/17 10:00
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ShopUploadImageCatNameVo extends ShopUploadedImageVo {
    private String imgCatName;
}
