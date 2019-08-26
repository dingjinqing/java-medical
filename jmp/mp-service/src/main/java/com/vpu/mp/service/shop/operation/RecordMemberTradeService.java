package com.vpu.mp.service.shop.operation;

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
import com.vpu.mp.service.pojo.shop.member.data.BasicData;
import com.vpu.mp.service.pojo.shop.member.data.ScoreData;
import com.vpu.mp.service.pojo.shop.member.data.UserCardData;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.MemberCardService;
import com.vpu.mp.service.shop.member.ScoreService;

/**
* @author 黄壮壮
* @Date: 2019年8月22日
* @Description: 
*/
@Service
public class RecordMemberTradeService extends ShopBaseService{
	@Autowired private ScoreService scoreService;
	@Autowired private MemberCardService memberCardService;
	@Autowired private AccountService accountService;
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
	public void updateUserEconomicData(BasicData data) throws MpException {
		if(data instanceof AccountData) {
			logger().info("余额变动");
			/** 余额变动 */
			AccountData d = (AccountData)data;
			AccountParam p = new AccountParam();
			FieldsUtil.assignNotNull(d, p);
			accountService.addUserAccount(p, d.getAdminUser(), d.getTradeType(), d.getTradeFlow());
		}else if(data instanceof ScoreData) {
			logger().info("积分变动");
			/** 积分变动 */
			ScoreData d = (ScoreData)data;
			ScoreParam scoreParam = new ScoreParam();
			FieldsUtil.assignNotNull(d, scoreParam);
			scoreParam.setUserId(new Integer[] {d.getUserId()});
			scoreService.updateMemberScore(scoreParam, d.getAdminUser(),d.getUserId(), d.getTradeType(), d.getTradeFlow());
		}else if(data instanceof UserCardData) {
			logger().info("会员卡余额，兑换次数，消费次数变动 ");
			/** 会员卡余额，兑换次数，消费次数变动 */
			UserCardData d = (UserCardData)data;
			CardConsumpData c = new CardConsumpData();
			FieldsUtil.assignNotNull(d, c);
			memberCardService.cardConsumer(c, d.getAdminUser(), d.getTradeType(), d.getTradeFlow(), d.getStoreOrConsump());
			
		}
			
	}
	
	/**
	 * 交易明细
	 * @param oldRecord
	 */
	public void insertRecord(TradesRecordRecord oldRecord) {
		logger().info("开始插入trades_record表");
		TradesRecordRecord newRecord = new TradesRecordRecord();
		/** 确保null 不被执行 */
		FieldsUtil.assignNotNull(oldRecord,newRecord);
		newRecord.insert();
	}
}
