package com.vpu.mp.service.shop.member;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditVo;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyResultVo;
import com.vpu.mp.service.shop.member.dao.CardVerifyDaoService;

/**
* @author 黄壮壮
* @Date: 2019年10月30日
* @Description: 会员卡审核服务操作功能
*/
@Service
public class CardVerifyService extends ShopBaseService {
	/** 审核状态： 拒绝 */
	private final static Byte VSTAT_REFUSED = 3;
	@Autowired
	public CardVerifyDaoService verifyDao;
	
	
	
	
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
	
	
	
	
	
}
