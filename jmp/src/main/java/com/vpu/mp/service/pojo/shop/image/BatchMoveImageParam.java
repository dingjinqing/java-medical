package com.vpu.mp.service.pojo.shop.image;

import com.vpu.mp.service.foundation.JsonResultMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull
    Integer imageCatId;
    @NotBlank()
    List<Integer> imageIds;


}
