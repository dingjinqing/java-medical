package com.vpu.mp.service.pojo.shop.image;

import java.util.List;

import lombok.Data;

import javax.validation.constraints.Size;

import com.vpu.mp.common.foundation.validator.ListValid;

/**
 * @author 孔德成
 * @date 2019/7/9 14:30
 */
@Data
public class BatchDeleteImageParam {

    @ListValid(min = 0)
    List<Integer> imageIds;

}
