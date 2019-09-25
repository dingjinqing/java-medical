package com.vpu.mp.service.shop.member.dao;

import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record8;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.springframework.stereotype.Service;
import org.jooq.tools.StringUtils;
import com.vpu.mp.db.shop.tables.User;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderParam;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderVo;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveParam;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveVo;

import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.CARD_RECEIVE_CODE;
import static com.vpu.mp.db.shop.Tables.CARD_BATCH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_EXPIRED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_DELETE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.DELETE_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ALL_BATCH;
import java.sql.Timestamp;

/**
* @author 黄壮壮
* @Date: 2019年9月24日
* @Description: 会员卡dao访问
*/
@Service
public class CardDaoService extends ShopBaseService {
	
	public PageResult<CardHolderVo> getAllCardHolder(CardHolderParam param) {

		User invitedUser = USER.as("a");
		SelectJoinStep<?> select = db().select(USER_CARD.USER_ID,USER.USERNAME,USER.MOBILE,invitedUser.USERNAME.as("invitedName"),USER_CARD.CREATE_TIME,USER_CARD.CARD_NO,USER_CARD.FLAG,USER_CARD.EXPIRE_TIME)
			.from( USER_CARD.leftJoin(USER
										.leftJoin(invitedUser).on(USER.INVITE_ID.eq(invitedUser.USER_ID))
					
									).on(USER_CARD.USER_ID.eq(USER.USER_ID))	
				);
		
		buildOptions(param, select);
		select.where(USER_CARD.CARD_ID.eq(param.getCardId()));
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), CardHolderVo.class);
	}
	/**
	 * 构建查询参数
	 * @param param
	 * @param select
	 */
	private void buildOptions(CardHolderParam param, SelectJoinStep<?> select) {
		/** - 会员id */
		if(param.getUserId()!=null) {
			select.where(USER_CARD.USER_ID.eq(param.getUserId()));
		}
		/** - 昵称 */
		if(!StringUtils.isBlank(param.getUsername())) {
			String likeValue = likeValue(param.getUsername());
			select.where(USER.USERNAME.like(likeValue));
		}
		/** -手机号 */
		if(!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.eq(param.getMobile()));
		}
		/** - 会员卡号 */
		if(!StringUtils.isBlank(param.getCardNo())) {
			select.where(USER_CARD.CARD_NO.eq(param.getCardNo()));
		}
		/** - 卡状态 */
		if(param.getFlag() != null) {
			/** - 状态为过期 */
			if(param.getFlag().equals(CARD_EXPIRED)) {
				select.where(USER_CARD.EXPIRE_TIME.le(DateUtil.getLocalDateTime()).or(USER_CARD.EXPIRE_TIME.isNull()));
			}else if(param.getFlag().equals(CARD_USING) || param.getFlag().equals(CARD_DELETE)) {
				select.where(USER_CARD.FLAG.eq(param.getFlag()));
			}
		}
		/** - 领卡时间  开始范围 */
		if(param.getFirstDateTime() != null) {
			select.where(USER_CARD.CREATE_TIME.ge(param.getFirstDateTime()));
		}
		
		/** - 领卡时间  结束范围 */
		if(param.getSecondDateTime() != null) {
			select.where(USER_CARD.CREATE_TIME.le(param.getSecondDateTime()));
		}
	}
	
	/**
	 * 分页查询会员卡领取详情
	 * @param param
	 * @return
	 */
	public PageResult<CodeReceiveVo> getReceiveListSql(CodeReceiveParam param) {
		// CARD_RECEIVE_CODE
		SelectConditionStep<?> select = db().select(CARD_BATCH.NAME,CARD_RECEIVE_CODE.ID,USER.USER_ID,USER.USERNAME,USER.MOBILE,CARD_RECEIVE_CODE.RECEIVE_TIME,
														CARD_RECEIVE_CODE.CARD_NO,CARD_RECEIVE_CODE.CODE,CARD_RECEIVE_CODE.CARD_PWD,CARD_RECEIVE_CODE.DEL_FLAG)
		.from(CARD_RECEIVE_CODE
							.leftJoin(USER).on(CARD_RECEIVE_CODE.USER_ID.eq(USER.USER_ID))
							.leftJoin(CARD_BATCH).on(CARD_RECEIVE_CODE.BATCH_ID.eq(CARD_BATCH.ID))
		     ).where(CARD_RECEIVE_CODE.CARD_ID.eq(param.getCardId()));
		
		buildOptionForReceiveCode(param, select);
		return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), CodeReceiveVo.class);
		
	}
	
	/**
	 * 会员卡领取详情构建多条件查询参数
	 * @param param
	 * @param select
	 */
	private void buildOptionForReceiveCode(CodeReceiveParam param, SelectConditionStep<?> select) {
		/** -手机号 */
		if(!StringUtils.isBlank(param.getMobile())) {
			select.and(USER.MOBILE.eq(param.getMobile()));
		}
		/** -用户名 */
		if(!StringUtils.isBlank(param.getUsername())) {
			String likeValue = this.likeValue(param.getUsername());
			select.and(USER.USERNAME.like(likeValue));
		}
		/** -批次号 */
		if(param.getBatchId() != null && !param.getBatchId().equals(ALL_BATCH)) {
			select.and(CARD_RECEIVE_CODE.BATCH_ID.eq(param.getBatchId()));
		}
	}
	
	
	public Result<?> getCardBatchList(Integer cardId) {
		 return db().select(CARD_BATCH.ID,CARD_BATCH.NAME)
				 	.from(CARD_BATCH)
				 	.where(CARD_BATCH.CARD_ID.eq(cardId))
				 	.and(CARD_BATCH.DEL_FLAG.eq(DELETE_NO))
				 	.fetch();
		
	}

}
