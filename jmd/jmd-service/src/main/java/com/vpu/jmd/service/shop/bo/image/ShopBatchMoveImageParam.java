package com.vpu.jmd.service.shop.bo.image;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/17 10:57
 */
@Data
public class ShopBatchMoveImageParam {
    /**
     * 图片分组id
     */
    @NotNull
    Integer imageCatId;
    List<@NotNull Integer> imageIds;

}
