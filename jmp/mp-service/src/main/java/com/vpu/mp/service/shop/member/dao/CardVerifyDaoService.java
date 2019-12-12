package com.vpu.mp.service.shop.member.dao;

import org.apache.commons.lang3.StringUtils;
import org.jooq.SelectJoinStep;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditParam;
import com.vpu.mp.service.pojo.shop.member.card.ActiveAuditVo;
import com.vpu.mp.service.pojo.shop.member.card.CardVerifyResultVo;

import static com.vpu.mp.db.shop.Tables.CARD_EXAMINE;
import static com.vpu.mp.db.shop.Tables.USER;

import static com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant.VDF_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant.VSTAT_CHECKING;
import static com.vpu.mp.service.pojo.shop.member.card.CardVerifyConstant.VSTAT_PASS;

import java.util.List;
/**
* @author 黄壮壮
* @Date: 2019年10月30日
* @Description:
*/
@Service
public class CardVerifyDaoService extends ShopBaseService {

	/**
	 * 获取卡的审核结果
	 * @param cardNo
	 */
	public CardVerifyResultVo getCardVerifyResult(String cardNo){
		return db().select(CARD_EXAMINE.ID,CARD_EXAMINE.STATUS,CARD_EXAMINE.REFUSE_DESC,CARD_EXAMINE.REFUSE_TIME,CARD_EXAMINE.PASS_TIME)
					.from(CARD_EXAMINE)
					.where(CARD_EXAMINE.CARD_NO.eq(cardNo))
					.orderBy(CARD_EXAMINE.ID.desc())
					.fetchAnyInto(CardVerifyResultVo.class);
	}
	
	
	public PageResult<ActiveAuditVo> getVerifyPageList(ActiveAuditParam param) {
		SelectJoinStep<?> select = 
			db().select(CARD_EXAMINE.ID,CARD_EXAMINE.REAL_NAME,CARD_EXAMINE.CARD_NO,
					    CARD_EXAMINE.STATUS,CARD_EXAMINE.CREATE_TIME,CARD_EXAMINE.CID,
					    CARD_EXAMINE.EDUCATION,CARD_EXAMINE.INDUSTRY_INFO,
					    USER.MOBILE,USER.USERNAME)
				.from(CARD_EXAMINE.leftJoin(USER).on(CARD_EXAMINE.USER_ID.eq(USER.USER_ID)));
		buildOptions(select,param);
		return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), ActiveAuditVo.class);
	}
	
	public CardExamineRecord getLastRecord(ActiveAuditParam param) {
		SelectWhereStep<CardExamineRecord> select = db().selectFrom(CARD_EXAMINE);
		buildOptions(select,param);
		return select.orderBy(CARD_EXAMINE.CREATE_TIME.asc()).fetchAny();
	}
	
	/**
	 * 查询审核多条件构建
	 */
	private void buildOptions(SelectWhereStep<?> select, ActiveAuditParam param) {

		// 会员卡id
		if(isNotNull(param.getCardId())) {
			select.where(CARD_EXAMINE.CARD_ID.eq(param.getCardId()));
		}
		
		// 订单id
		if(isNotNull(param.getId())) {
			select.where(CARD_EXAMINE.ID.eq(param.getId()));
		}
			
		// 审核状态
		if(isNotNull(param.getStatus())) {
			select.where(CARD_EXAMINE.STATUS.eq(param.getStatus()));
		}
		// 真实姓名
		if(isNotNull(param.getRealName())) {
			String likeValue = likeValue(param.getRealName());
			select.where(CARD_EXAMINE.REAL_NAME.like(likeValue));
		}
		// 手机号
		if(isNotNull(param.getMobile())) {
			select.where(USER.MOBILE.eq(param.getMobile()));
		}
		// 申请时间 - 开始
		if(isNotNull(param.getFirstTime())) {
			select.where(CARD_EXAMINE.CREATE_TIME.ge(param.getFirstTime()));
		}
		// 申请时间 - 结束
		if(isNotNull(param.getSecondTime())) {
			select.where(CARD_EXAMINE.CREATE_TIME.le(param.getSecondTime()));
		}
		
		// 审核超时
		if(isNotNull(param.getExamineOver())) {
			select.where(CARD_EXAMINE.CREATE_TIME.le(param.getExamineOver()))
				  .and(CARD_EXAMINE.DEL_FLAG.eq(VDF_NO))
				  .and(CARD_EXAMINE.STATUS.eq(VSTAT_CHECKING));
			
		}
	
	}
	
	private boolean isNotNull(Object obj) {
		if(obj instanceof String){
            return !StringUtils.isBlank((String)obj);
        }
        return obj != null;
	}


	public Integer getCountOverDueRecord(ActiveAuditParam param) {
		SelectJoinStep<?> select = db().selectCount().from(CARD_EXAMINE);
		buildOptions(select,param);
		return select.fetchAnyInto(Integer.class);
	}


	public Integer countUndealUser(Integer cardId) {
		return db().fetchCount(CARD_EXAMINE, CARD_EXAMINE.CARD_ID.eq(cardId).and(CARD_EXAMINE.STATUS.eq(VSTAT_CHECKING)));
	}


	public List<CardExamineRecord> selectUndealVerifyRecord(Integer cardId) {
		return db().selectFrom(CARD_EXAMINE)
			.where(CARD_EXAMINE.CARD_ID.eq(cardId))
			.and(CARD_EXAMINE.STATUS.eq(VSTAT_CHECKING))
			.fetchInto(CardExamineRecord.class);
	}


	public void updateCardVerify(Integer id) {
		db().update(CARD_EXAMINE)
			.set(CARD_EXAMINE.STATUS,VSTAT_PASS)
			.set(CARD_EXAMINE.PASS_TIME,DateUtil.getLocalDateTime())
			.where(CARD_EXAMINE.ID.eq(id));
	}


	public CardExamineRecord selectRecordById(Integer id) {
		return db().selectFrom(CARD_EXAMINE).where(CARD_EXAMINE.ID.eq(id)).fetchAny();
	}


	public CardExamineRecord getStatusByNo(String cardNo) {
		return db().selectFrom(CARD_EXAMINE).where(CARD_EXAMINE.CARD_NO.eq(cardNo)).orderBy(CARD_EXAMINE.ID.desc())
				.fetchAny();
	}
	
}
