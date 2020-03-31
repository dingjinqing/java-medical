package com.vpu.mp.service.pojo.shop.member.builder;

import com.vpu.mp.service.pojo.shop.member.card.CardBatchVo;

/**
* @author 黄壮壮
* @Date: 2019年11月8日
* @Description:
*/


public class CardBatchVoBuilder {
	private CardBatchVo cardBatchVo;
	
	private CardBatchVoBuilder(){
		cardBatchVo = new CardBatchVo();
	}
	private CardBatchVoBuilder(CardBatchVo cardBatchVo) {
		this.cardBatchVo = cardBatchVo;
	}
	
	
	public static CardBatchVoBuilder create() {
		return new CardBatchVoBuilder();
	}
	
	
	public static CardBatchVoBuilder create(CardBatchVo cardBatchVo) {
		return new CardBatchVoBuilder(cardBatchVo);
	}

	public CardBatchVoBuilder id (Integer id) {
		cardBatchVo.setId(id);
		return this;
	}

	public CardBatchVoBuilder name (String name) {
		cardBatchVo.setName(name);
		return this;
	}
	
	public CardBatchVoBuilder pwdBatch (boolean flag) {
		cardBatchVo.setPwdBatch(flag);
		return this;
	}

	public CardBatchVo build() {
		return cardBatchVo;
	}
	
}

