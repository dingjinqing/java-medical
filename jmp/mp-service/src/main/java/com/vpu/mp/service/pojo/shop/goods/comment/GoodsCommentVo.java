package com.vpu.mp.service.pojo.shop.goods.comment;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author liangchen
 * @date 2019年07月07日
 */
@Data
public class GoodsCommentVo {
	
	private Integer id;
    private String orderSn;
    private Byte commstar;
    private String commNote;
    private String content;
    private Timestamp createTime;
    private String goodsName;
    private String goodsImg;
    private String username;
    private String mobile;
    private Byte anonymousflag;
    private String name;
    private Integer awardType;
    private Integer score;
    private BigDecimal account;
    private String bogusUsername;
   
}
