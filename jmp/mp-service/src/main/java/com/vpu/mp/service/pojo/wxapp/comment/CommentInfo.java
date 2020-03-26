package com.vpu.mp.service.pojo.wxapp.comment;

import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.CommentDetailVo;
import lombok.Data;

import java.util.List;

/**
 * 小程序单商品评价详情
 * @author liangchen
 * @date 2020.03.26
 */
@Data
public class CommentInfo {
    private List<MPGoodsCommentVo> comment;
    private List<CommentDetailVo.CommentLevelInfo> number;
}
