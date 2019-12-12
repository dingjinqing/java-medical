package com.vpu.mp.service.shop.member;

import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserDetailRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.builder.ActiveOverDueVoBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditVo;
import com.vpu.mp.service.pojo.shop.member.card.ActiveOverDueVo;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyResultVo;
import com.vpu.mp.service.shop.member.dao.CardVerifyDaoService;
import com.vpu.mp.service.shop.user.user.UserDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant.VSTAT_REFUSED;

/**
* @author 黄壮壮
* @Date: 2019年10月30日
* @Description: 会员卡审核服务操作功能
*/
@Service
public class CardVerifyService extends ShopBaseService {

	@Autowired public CardVerifyDaoService verifyDao;
	@Autowired public MemberCardService memberCardService;
	@Autowired public UserCardService userCardService;
	@Autowired public UserDetailService userDetailService;
	/**
	 *  分页查询
	 */
	public PageResult<ActiveAuditVo> getPageList(ActiveAuditParam param) {
		return verifyDao.getVerifyPageList(param);
	}

	/**
	 * 审核通过激活会员卡
	 */
	public void passCardVerify(Integer id,String cardNo) {

		this.transaction(()->{
			updateCardVerifyRecord(id);
			updateUserCardByNo(cardNo);
			updateUserDetailAccordToVerifyData(id);
		});

        //TODO 模板消息
	}

    /**
	 * 根据激活需要的信息，更新用户详情
	 */
	private void updateUserDetailAccordToVerifyData(Integer id) {
		logger().info("更新用户详情");
		UserDetailRecord userDetailRecord = getUserActiveData(id);
		if(userDetailRecord != null) {
			userDetailService.updateRow(userDetailRecord);
		}
	}

	private UserDetailRecord getUserActiveData(Integer id) {
		List<String> keyL = getActiveRequiredField(id);

        CardExamineRecord cEx = getCardExamineRecordById(id);
		// 准备数据
		Map<String, Object> cExMap = cEx.intoMap();
		cExMap.entrySet().removeIf(e->!keyL.contains(e.getKey()));
		cExMap.values().removeIf(Objects::isNull);

        if(cExMap.size()>0) {

            UserDetailRecord userDetailRecord = new UserDetailRecord();
			userDetailRecord.fromMap(cExMap);
			userDetailRecord.setUserId(cEx.getUserId());
			return userDetailRecord;

		}else {
			return null;
		}

	}

    /**
	 * 获取需要激活的信息
	 */
	private List<String> getActiveRequiredField(Integer id) {
		MemberCardRecord card = getCard(id);
		String[] key = card.getActivationCfg().split(",");
		List<String> keyL = new ArrayList<>(Arrays.asList(key));
		formatKeyToUnderline(keyL);
		dealWithActivateBirthday(keyL);
		return keyL;
	}

    /**
	 *  驼峰到下划线
	 */
	private void formatKeyToUnderline(List<String> keyL) {
		for(int i=0;i<keyL.size();i++) {
			String v = Util.humpToUnderline(keyL.get(i));
			keyL.set(i, v);
		}
	}

    private void dealWithActivateBirthday(List<String> keylist) {
		String birthDay = "birthday";
		if(keylist.contains(birthDay)) {
			keylist.removeIf("birthday"::equals);
			keylist.addAll(Arrays.asList("birthday_year","birthday_month","birthday_day"));
		}
	}

	/**
	 * 获取未处理的激活审核信息
	 */
	public List<CardExamineRecord> getUndealVerifyMsg(Integer cardId) {
		return verifyDao.selectUndealVerifyRecord(cardId);
	}


    /**
	 * 获取未处理的激活审核人数
	 */
	public Integer getUndealUserNum(Integer cardId) {
		return verifyDao.countUndealUser(cardId);
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
     * 获取会员卡激活审核超时的最早的一条记录
     */
    public CardExamineRecord getLastRecordCanNull(ActiveAuditParam param) {
        return verifyDao.getLastRecord(param);
    }

	/**
	 * 取会员卡激活审核超时的记录
	 */
	public ActiveOverDueVo getCardActVerifyOverDueRecord(ActiveAuditParam param) {
		CardExamineRecord lastRecord = getLastRecord(param);
		if(lastRecord.getCardId()!=null) {
			return ActiveOverDueVoBuilder.create().cardNum(0).build();
		}
		param.setCardId(lastRecord.getCardId());
		return ActiveOverDueVoBuilder
				.create()
				.cardNum(verifyDao.getCountOverDueRecord(param))
				.cardName(memberCardService.getCardById(param.getCardId()).getCardName())
				.cardId(lastRecord.getCardId())
				.build();

	}

    public CardExamineRecord getCardExamineRecordById(Integer id) {
		CardExamineRecord rec = verifyDao.selectRecordById(id);
		return rec!=null?rec: new CardExamineRecord();
	}

    private void updateCardVerifyRecord(Integer id) {
		logger().info("更新记录状态");
		verifyDao.updateCardVerify(id);
	}

    private void updateUserCardByNo(String cardNo) {
		logger().info("更新激活");
        userCardService.updateUserCardByNo(cardNo,
					UserCardRecordBuilder.create().activationTime(DateUtil.getLocalDateTime()).build());
	}

    private MemberCardRecord getCard(Integer id) {
		CardExamineRecord cardEx = getCardExamineRecordById(id);
		return memberCardService.getCardById(cardEx.getCardId());
	}
    
    
    public CardExamineRecord getStatusByNo(String cardNo) {
    	return verifyDao.getStatusByNo(cardNo);
    }
}
