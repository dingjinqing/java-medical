package com.vpu.mp.service.pojo.shop.goods.comment;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liangchen
 * @date 2019年07月07日
 */
@Data
public class GoodsCommentAnswerParam {
	
	private Integer answerId;
	private Integer commentId;
    private String content;
}
