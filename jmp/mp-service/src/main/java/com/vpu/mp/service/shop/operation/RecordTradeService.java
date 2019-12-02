package com.vpu.mp.service.shop.operation;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.TradesRecordRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumpData;
import com.vpu.mp.service.pojo.shop.member.data.AccountData;
import com.vpu.mp.service.pojo.shop.member.data.BaseData;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.member.data.UserCardData;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.operation.dao.TradesRecordDaoService;

/**
* @author 黄壮壮
* @Date: 2019年8月22日
* @Description: 
*/
@Service
public class RecordTradeService extends ShopBaseService{
	@Autowired private ScoreService scoreService;
	@Autowired private MemberCardService memberCardService;
	@Autowired private AccountService accountService;
	@Autowired private TradesRecordDaoService tradeRecDao;
	/**
	 * 统一更细用户的余额，积分，会员卡余额入口
	 * 
	 *{@link com.vpu.mp.service.pojo.shop.member.data.AccountData} 余额参数
	 * 
	 *{@link com.vpu.mp.service.pojo.shop.member.data.ScoreData}  积分参数 
	 *
	 *{@link com.vpu.mp.service.pojo.shop.member.data.UserCardData} 用户会员卡参数
	 * @param data
	 * @throws MpException 
	 */
 	public void updateUserEconomicData(BaseData data) throws MpException {
		if(data instanceof AccountData) {
			logger().info("余额变动");
			/** 余额变动 */
			AccountData accountData = (AccountData)data;
			AccountParam accountParam = new AccountParam();
			FieldsUtil.assignNotNull(accountData, accountParam);
			
			Integer adminUser = accountData.getAdminUser();
			Byte tradeType = accountData.getTradeType();
			Byte tradeFlow = accountData.getTradeFlow();
			
			
			accountService.addUserAccount(accountParam, TradeOptParam.builder().tradeType(tradeType).tradeFlow(tradeFlow).build());
		}else if(data instanceof ScoreData) {
			logger().info("积分变动");
			/** 积分变动 */
			ScoreData scoreData = (ScoreData)data;
			ScoreParam scoreParam = new ScoreParam();
			FieldsUtil.assignNotNull(scoreData, scoreParam);
			scoreParam.setUserId(new Integer[] {scoreData.getUserId()});
			String language = scoreData.getLanguage();
			scoreService.updateMemberScore(scoreParam, scoreData.getAdminUser(),scoreData.getUserId(), scoreData.getTradeType(), scoreData.getTradeFlow(),language);
		}else if(data instanceof UserCardData) {
			logger().info("会员卡余额，兑换次数，消费次数变动 ");
			/** 会员卡余额，兑换次数，消费次数变动 */
			UserCardData userCardData = (UserCardData)data;
			CardConsumpData cardConsumpData = new CardConsumpData();
			BeanUtils.copyProperties(userCardData, cardConsumpData);
			String language = userCardData.getLanguage();
			memberCardService.updateMemberCardAccount(cardConsumpData,userCardData.getTradeOpt(),language);
			
		}
			
	}
	
	/**
	 * 交易明细
	 */
	public void insertRecord(TradesRecordRecord oldRecord) {
		logger().info("开始插入trades_record表");
		TradesRecordRecord newRecord = new TradesRecordRecord();
		/** 确保null 不被执行 */
		FieldsUtil.assignNotNull(oldRecord,newRecord);
		newRecord.insert();
	}
	
	public void insertTradeRecord(TradeOptParam tradeOpt) {
		
		if(isTradeSnNull(tradeOpt.getTradeSn())) {
			tradeOpt.setTradeSn("");
		}
		
		tradeRecDao.insertTradesRecord(tradeOpt);
	}
	

	private boolean isTradeSnNull(String sn) {
		return StringUtils.isBlank(sn);
	}
	
}
