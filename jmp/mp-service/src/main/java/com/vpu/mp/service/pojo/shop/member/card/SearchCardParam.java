package com.vpu.mp.service.pojo.shop.member.card;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * 
 * @author 黄壮壮
 * @Date: 2019年8月1日
 * @Description: 分页查询会员卡列表参数
 */
@Data
public class SearchCardParam {
	/** 会员卡类型 如： {@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.NORMAL_TYPE } */
	private Byte cardType;
	/** 
	 * 分页信息
	 */
	/**
     * 分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
