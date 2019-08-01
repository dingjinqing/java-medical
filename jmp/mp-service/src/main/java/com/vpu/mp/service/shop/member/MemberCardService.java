package com.vpu.mp.service.shop.member;

import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.card.CardParam;
import com.vpu.mp.service.pojo.shop.member.card.PowerCardJson;
import com.vpu.mp.service.pojo.shop.member.card.ScoreJson;


@Service
public class MemberCardService extends ShopBaseService {
	
	public void addMemberCard(CardParam card) {
		MemberCardRecord cardRecord = new MemberCardRecord();

		Byte cardType = card.getCardType();
		
		//设置会员卡类型
		cardRecord.setCardType(cardType);
	
		//判断卡类型
		if(cardType == NORMAL_TYPE || cardType == LIMIT_NUM_TYPE) {
			//判断领取类型
			Byte isPay = card.getIsPay();
			
			//TODO 0的情况呢？直接领取
			if(isPay == NEED_BUY) {
				
			}else if(isPay == PICK_UP_CODE) {
				
			}
		}
		
		Byte payOwnGood;
		if(cardType == NORMAL_TYPE || cardType == RANK_TYPE) {
			payOwnGood = (byte) (BUTTON_ON.equals(card.getPowerPayOwnGood())? 1:0);
		}
		
		
		
		//处理普通会员卡
		if(cardType == NORMAL_TYPE) {
			
			//// 会员有效期
			Byte expiredType = card.getExpiredType();
			if(expiredType == FIX_DATETIME) {
				cardRecord.setExpireType(FIX_DATETIME);
				cardRecord.setStartTime(card.getStartTime());
				cardRecord.setEndTime(card.getEndTime());
				//String endTime = card.getEndTime().substring(0, 10).concat(FINAL_TIME);
			}else if(expiredType == DURING_TIME){
				cardRecord.setExpireType(DURING_TIME);
				cardRecord.setReceiveDay(card.getReceiveDay());
				cardRecord.setDateType(card.getDateType());
			}else if(expiredType == FOREVER) {
				cardRecord.setExpireType(FOREVER);
			}
			
			// 会员卡名称
			cardRecord.setCardName(card.getCardName());
			
			// 背景类型
			Byte bgType = card.getBgType();
			
			
			if(bgType == BG_COLOR_TYPE) {
				//背景色
				cardRecord.setBgType(BG_COLOR_TYPE);
				cardRecord.setBgColor(card.getBgColor());
			}else if(bgType == BG_IMG_TYPE) {
				cardRecord.setBgType(BG_IMG_TYPE);
				cardRecord.setBgImg(card.getBgImg());
			}
			
			// 处理会员权益
			
			boolean flag = false;
			//1. 会员折扣
			if(card.getPowerScore() == CHECKED) {
				flag = true;
				cardRecord.setDiscount(card.getDisCount());
			}else {
				//没有勾选值设置为null
				cardRecord.setDiscount(null);
			}
			
			
			//折扣部分商品还是全部商品
			if(card.getDiscountIsAll() == DISCOUNT_ALL_GOODS) {
				cardRecord.setDiscountIsAll(DISCOUNT_ALL_GOODS);
			}else if (card.getDiscountIsAll() == DISCOUNT_PART_GOODS) {
				// TODO  处理需要折扣的部分商品
				cardRecord.setDiscountIsAll(DISCOUNT_PART_GOODS);
			}
			
			
			
			//2. 会员专享商品
			if(BUTTON_ON.equals(card.getPowerPayOwnGood())) {
				//TODO 处理允许会员专享的商家，分类，平台等
				flag = true;
			}
			
			
			//3. 积分获取
			if(card.getPowerScore() == CHECKED) {
				flag = true;
				//积分
				cardRecord.setSorce(card.getScore());
				
				ScoreJson scoreJson = new ScoreJson();
				FieldsUtil.assignNotNull(card, scoreJson);
				
				//购物送积分策略json数据
				cardRecord.setBuyScore(Util.toJson(scoreJson));
			}
			
				
		    // 4. 卡充值
			if(card.getPowerCard() == CHECKED) {
				flag = true;
				
				//设置金额
				cardRecord.setSendMoney(card.getSendMoney());
				//设置开卡策略
				PowerCardJson powerCardJson = new PowerCardJson();
				FieldsUtil.assignNotNull(card, powerCardJson);
				cardRecord.setChargeMoney(Util.toJson(powerCardJson));
			}
				
			if(!flag) {
				//必须选择一项会员权益
				return ;
			}
			
			
			//使用门店
			if(card.getStoreListType() == ALL_SHOP) {
				cardRecord.setStoreList(ALL_SHOP);
			}else if(card.getStoreListType() == PART_SHOP) {
				String storeList = String.join(",", card.getStoreList());
				cardRecord.setStoreList(storeList);
			}else if(card.getStoreListType() == PROHIBITED) {
				cardRecord.setStoreList(PROHIBITED);
			}
			
			
			// 使用须知
			cardRecord.setDesc(card.getDesc());
			
			//电话号码
			cardRecord.setMobile(card.getMobile());
		
			
			// 领取设置
			
			//购买设置
			Byte isPay = card.getIsPay();
			if (isPay == GET_DIRECTLY) {
				cardRecord.setIsPay(GET_DIRECTLY);
			} else if (isPay == NEED_BUY) {
				cardRecord.setIsPay(NEED_BUY);
				if(card.getPayType() == BUY_BY_CRASH) {
					cardRecord.setPayFee(card.getPayMoney());
				}else if(card.getPayType() == BUY_BY_SCORE) {
					cardRecord.setPayFee(card.getPayScore());
				}
			} else if (isPay == NEED_CODE) {
				//todo 领取码
				cardRecord.setIsPay(NEED_CODE);
			}
			
			
			// 激活设置
			if(card.getActivation() == ACTIVE_NO) {
				cardRecord.setActivation(ACTIVE_NO);
			}else if(card.getActivation() == ACTIVE_YES) {
				cardRecord.setActivation(ACTIVE_YES);
				String activationCfg = String.join(",", card.getActivationCfgBox());
				cardRecord.setActivationCfg(activationCfg);
				cardRecord.setExamine(card.getExamine());
			}
		
		}
		
	}

	
	private void insertIntoMemberCard(MemberCardRecord cardRecord) {
		db().executeInsert(cardRecord);
	}
	
	
	
	
	
	
}
