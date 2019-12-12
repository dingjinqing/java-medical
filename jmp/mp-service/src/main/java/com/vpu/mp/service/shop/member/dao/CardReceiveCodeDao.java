package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.Tables.CARD_BATCH;
import static com.vpu.mp.db.shop.Tables.CARD_RECEIVE_CODE;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardBatchRecord;
import com.vpu.mp.db.shop.tables.records.CardReceiveCodeRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;

/**
* @author 黄壮壮
* @Date: 2019年11月8日
* @Description:
*/
@Service
public class CardReceiveCodeDao extends ShopBaseService {
	/** del_flag 删除状态： 0：没有删除 */
	final static Byte CBATCH_DF_NO = 0;
	/** del_flag 删除状态： 1：确定删除 */
	final static Byte CBATCH_DF_YES = 1;
	
	/**
	 * 获取正在使用的批次
	 */
	public List<CardBatchRecord> getCardBatchByCardId(Integer cardId) {
		 return db().selectFrom(CARD_BATCH)
				 	.where(CARD_BATCH.CARD_ID.eq(cardId))
				 	.and(CARD_BATCH.DEL_FLAG.eq(CBATCH_DF_NO))
				 	.fetchInto(CardBatchRecord.class);
	}
	
	/**
	 * 获取全部批次
	 */
	public List<CardBatchRecord> getAllCardBatchByCardId(Integer cardId) {
		return db().selectFrom(CARD_BATCH)
			 	.where(CARD_BATCH.CARD_ID.eq(cardId))
			 	.fetchInto(CardBatchRecord.class);
	}
	
	/**
	 * 添加卡id到批次
	 */
	public void addCardIdToCardBatch(Integer cardId,Set<Integer> batchIdList) {
		this.transaction(()->{
			logger().info("添加卡id到批次");
			bindCardIdToBatch(cardId,batchIdList);
			bindCardIdToReceiveCode(cardId,batchIdList);
		});
		
	}
	
	/**
	 * 绑定卡id到批次
	 */
	private void bindCardIdToBatch(Integer cardId,Set<Integer> batchIdList) {
		db().update(CARD_BATCH)
		.set(CARD_BATCH.CARD_ID, cardId)
		.where(CARD_BATCH.ID.in(batchIdList))
		.execute();
	}
	
	/**
	 * 绑定卡id到领取码
	 */
	private void bindCardIdToReceiveCode(Integer cardId,Set<Integer> batchIdList) {
		db().update(CARD_RECEIVE_CODE)
		.set(CARD_RECEIVE_CODE.CARD_ID,cardId)
		.where(CARD_RECEIVE_CODE.BATCH_ID.in(batchIdList))
		.and(CARD_RECEIVE_CODE.ERROR_MSG.isNull())
		.execute();
	}
	
	/**
	 * 删除批次
	 */
	public void deleteBatchId(Set<Integer> batchIdSet) {
		this.transaction(()->{
			logger().info("删除批次");
			deleteCardBatch(batchIdSet);
			deleteCardReceiveCode(batchIdSet);
		});
	}

	private void deleteCardReceiveCode(Set<Integer> batchIdSet) {
		db().update(CARD_RECEIVE_CODE)
			.set(CARD_RECEIVE_CODE.DEL_FLAG,CBATCH_DF_YES)
			.set(CARD_RECEIVE_CODE.DEL_TIME,DateUtil.getLocalDateTime())
			.where(CARD_RECEIVE_CODE.BATCH_ID.in(batchIdSet))
			.and(CARD_RECEIVE_CODE.USER_ID.eq(0))
			.execute();
	}

	private void deleteCardBatch(Set<Integer> batchIdSet) {
		db().update(CARD_BATCH)
			.set(CARD_BATCH.DEL_FLAG,CBATCH_DF_YES)
			.set(CARD_BATCH.DEL_TIME,DateUtil.getLocalDateTime())
			.where(CARD_BATCH.ID.in(batchIdSet))
			.execute();
	}

	public CardReceiveCodeRecord getRow(Condition condition) {
		return db().selectFrom(CARD_RECEIVE_CODE)
			.where(condition)
			.fetchAny();
	}

	public CardReceiveCodeRecord getUserHasCode(Integer userId, String code) {
		Condition condition = DSL.noCondition();
		if(userId!=null) {
			condition = condition.and(CARD_RECEIVE_CODE.USER_ID.eq(userId));
		}
		if(code != null) {
			condition = condition.and(CARD_RECEIVE_CODE.CODE.eq(code));
		}	
		return getRow(condition);
	}

	public CardReceiveCodeRecord getUserHasCode(Integer userId, String cardNo, String cardPwd) {
		Condition condition = DSL.noCondition();
		if(userId!=null) {
			condition = condition.and(CARD_RECEIVE_CODE.USER_ID.eq(userId));
		}
		if(cardNo != null) {
			condition = condition.and(CARD_RECEIVE_CODE.CARD_NO.eq(cardNo));
		}
		if(cardPwd != null) {
			condition = condition.and(CARD_RECEIVE_CODE.CARD_PWD.eq(cardPwd));
		}
		return getRow(condition);
	}
	
	private Condition getValidCondition() {
		Condition condition = DSL.noCondition();
		condition = condition
				.and(CARD_RECEIVE_CODE.DEL_FLAG.eq(CBATCH_DF_NO))
				.and(CARD_RECEIVE_CODE.USER_ID.eq(0))
				.and(CARD_RECEIVE_CODE.ERROR_MSG.isNull())
				.and(CARD_RECEIVE_CODE.STATUS.eq((byte)1));
		return condition;
	}
	public CardReceiveCodeRecord getCardCode(Integer cardId, String code) {
		Condition condition = getValidCondition();
		if(cardId!=null) {
			condition = condition.and(CARD_RECEIVE_CODE.CARD_ID.eq(cardId));
		}
		if(code != null) {
			condition = condition.and(CARD_RECEIVE_CODE.CODE.eq(code));
		}
		return getRow(condition);
	}
	
	public CardReceiveCodeRecord getCardPwd(Integer cardId, String cardNo, String cardPwd) {
		Condition condition = getValidCondition();
		if(cardId!=null) {
			condition = condition.and(CARD_RECEIVE_CODE.CARD_ID.eq(cardId));
		}
		if(!StringUtils.isBlank(cardNo)) {
			condition = condition.and(CARD_RECEIVE_CODE.CARD_NO.eq(cardNo));
		}
		if(!StringUtils.isBlank(cardPwd)) {
			condition = condition.and(CARD_RECEIVE_CODE.CARD_PWD.eq(cardPwd));
		}
		return getRow(condition);
	}
	
	
	public void updateRecord(CardReceiveCodeRecord cardReceiveCodeRecord) {
		db().executeUpdate(cardReceiveCodeRecord, CARD_RECEIVE_CODE.ID.eq(cardReceiveCodeRecord.getId()));
	}




	
}
