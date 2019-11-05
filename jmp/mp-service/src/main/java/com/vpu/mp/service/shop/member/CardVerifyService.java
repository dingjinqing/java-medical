package com.vpu.mp.service.shop.member;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.builder.ActiveOverDueVoBuilder;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditVo;
import com.vpu.mp.service.pojo.shop.member.card.ActiveOverDueVo;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyResultVo;
import com.vpu.mp.service.shop.member.dao.CardVerifyDaoService;

import static com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant.VSTAT_REFUSED;
/**
* @author 黄壮壮
* @Date: 2019年10月30日
* @Description: 会员卡审核服务操作功能
*/
@Service
public class CardVerifyService extends ShopBaseService {
	
	@Autowired public CardVerifyDaoService verifyDao;
	
	/**
	 *  分页查询
	 */
	public PageResult<ActiveAuditVo> getPageList(ActiveAuditParam param) {
		return verifyDao.getVerifyPageList(param);
	}
	
	
	/**
	 * 根据卡号，获取当前卡的审核状态
	 * @param cardNo 卡号
	 */
	public Byte getCardVerifyStatus(String cardNo){
		if(StringUtils.isBlank(cardNo)) {
			return VSTAT_REFUSED;
		}
		CardVerifyResultVo cardVerifyDaoService = verifyDao.getCardVerifyResult(cardNo);
		return cardVerifyDaoService != null?cardVerifyDaoService.getStatus():VSTAT_REFUSED;
	}
	
	/**
	 * 获取会员卡激活审核超时的最早的一条记录
	 */
	public CardExamineRecord getLastRecord(ActiveAuditParam param) {
		 CardExamineRecord lastRecord = verifyDao.getLastRecord(param);
		 return lastRecord != null?lastRecord: new CardExamineRecord();
	}
	
	/**
	 * 取会员卡激活审核超时的记录
	 */
	public ActiveOverDueVo getCardActVerifyOverDueRecord(ActiveAuditParam param) {
		CardExamineRecord lastRecord = getLastRecord(param);
		if(lastRecord.getCardId()!=null) {
			return ActiveOverDueVoBuilder.create().cardNum(0).build();
		}
//		ActiveOverDueVoBuilder.create()
		return null;
	}
	
	
	
	
}
