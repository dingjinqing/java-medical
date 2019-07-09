package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/9 14:29
 */
@Data
public class BatchMoveImageParam {

    /**
     * 图片分组id
     */
    Integer imageCatId;
    List<Integer> imageIds;


}
