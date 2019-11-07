package com.vpu.mp.service.pojo.wxapp.activity.info;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsCommentProcessorDataInfo extends ProcessorDataInfo {
    public GoodsCommentProcessorDataInfo() {
    }

    public GoodsCommentProcessorDataInfo(Integer commentNum) {
        super();
        this.commentNum = commentNum;
    }

    private Integer commentNum;

}
