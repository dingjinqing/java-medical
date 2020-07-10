package com.vpu.mp.service.pojo.shop.goods.comment;

import com.vpu.mp.common.foundation.util.PageResult;

import lombok.Data;

/**
 * 评价数量统计
 * @author liangchen
 * @date 20200429
 */
@Data
public class GoodsCommentListVo extends PageResult {
    private Integer allNum;
    private Integer toDoNum;
    private Integer passNum;
    private Integer refuseNum;
    private Integer topNum;
    private Integer showNum;
}
