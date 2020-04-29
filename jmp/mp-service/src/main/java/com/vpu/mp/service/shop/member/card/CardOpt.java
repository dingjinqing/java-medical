package com.vpu.mp.service.shop.member.card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant.TaskJobEnum;
import com.vpu.mp.service.pojo.shop.config.message.MessageTemplateConfigConstant;
import com.vpu.mp.service.pojo.shop.market.message.RabbitMessageParam;
import com.vpu.mp.service.pojo.shop.member.MemberBasicInfoVo;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateConfig;
import com.vpu.mp.service.pojo.shop.official.message.MpTemplateData;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.member.UserCardService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;

/**
 *	 卡操作抽象类
 * @author 黄壮壮
 *
 */
@Service
public abstract class CardOpt extends ShopBaseService{
	@Autowired protected MemberCardService cardService;
	@Autowired protected UserCardService userCardService;
	@Autowired protected UserCardDaoService userCardDao;
	@Autowired protected MemberService memberSvc;
	
	/**	卡类型	*/
	private Byte type;
	
	public CardOpt(Byte _type) {
		this.type = _type;
	}
	
	public Byte getType() {
		return this.type;
	}
	public void setType(Byte _type) {
		this.type = _type;
	}
	
	public Logger logger() {
		return LoggerFactory.getLogger(getClass());
	}
	/**
	 * 	发卡
	 * @param userId 用户Id
	 * @param cardId 卡Id
	 * @param isActivate 是否直接激活 true: 是，false不是 
	 * @return 卡号 null为发卡失败
	 */
	public final String handleSendCard(Integer userId,Integer cardId,boolean isActivate) {
		
		if(canSendCard(userId,cardId)) {
			String cardNo = sendCard(userId,cardId,isActivate);
			if(!StringUtils.isBlank(cardNo)) {
				//	订阅消息
				sendMessage(cardNo);
			}
			return cardNo;
		}
		return null;
	}
	
	
	/**
	 * 	发卡
	 * @param userId 用户Id
	 * @param cardId 卡Id
	 * @param isActivate 是否直接激活 true: 是，false不是
	 * @return 卡号
	 */
	protected abstract String sendCard(Integer userId,Integer cardId,boolean isActivate);
	
	/**
	 * 	是否可以发放此卡给用户
	 * @param userId
	 * @param cardId
	 * @return true 可以 false 不可以
	 */
	public abstract boolean canSendCard(Integer userId,Integer cardId);
	
	/**
	 * 发卡成功模板消息
	 */
	protected void sendMessage(String cardNo) {
		UserCardParam userCard = userCardService.getCard(cardNo);
		MemberCardRecord memberCard = cardService.getCardById(userCard.getCardId());
		MemberBasicInfoVo user = memberSvc.getMemberInfo(userCard.getUserId());
		
		String expireTime = null;
		if(CardUtil.isCardTimeForever(memberCard.getExpireType())) {
			expireTime="永久有效";
		}else {
			expireTime = String.valueOf(userCard.getExpireTime().toLocalDateTime().toLocalDate());
		}
		Byte cardType = memberCard.getCardType();
		String cardTypeText="";
		if(CardUtil.isLimitCard(cardType)) {
			cardTypeText = "限次卡";
		}else if(CardUtil.isNormalCard(cardType)){
			cardTypeText = "普通卡";
		}else if(CardUtil.isGradeCard(cardType)){
			cardTypeText = "等级卡";
		}
		
		// 公众号消息
		String[][] mpData = new String[][] {
			{""},
			{memberCard.getCardName()},
			{cardTypeText},
			{user.getMobile()==null?"":user.getMobile()},
			{expireTime},
			{""}
		};
		List<Integer> arrayList = Collections.<Integer>singletonList(user.getUserId());
		RabbitMessageParam param2 = RabbitMessageParam.builder()
				.mpTemplateData(
						MpTemplateData.builder().config(MpTemplateConfig.GET_CARD).data(mpData).build())
				.page("pages/cardinfo/cardinfo?card_no="+cardNo).shopId(getShopId())
				.userIdList(arrayList)
				.type(MessageTemplateConfigConstant.SUCCESS_MEMBER_CARD_GET).build();
		saas.taskJobMainService.dispatchImmediately(param2, RabbitMessageParam.class.getName(), getShopId(), TaskJobEnum.SEND_MESSAGE.getExecutionType());		
	}
	
}
