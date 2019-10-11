package com.vpu.mp.service.pojo.shop.goods.comment;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liangchen
 * @date 2019年07月010日
 */
@Data
public class GoodsCommentAddCommParam {
	
	private Integer goodsId;
	private String bogusUsername;
	private String bogusUserAvatar;
	private Timestamp createTime;
	private Byte commstar;
    private String commNote;
    private Byte anonymousFlag;
    private String commImg;
    
}
