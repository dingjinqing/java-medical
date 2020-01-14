package com.vpu.mp.service.shop.member.dao;

import static com.vpu.mp.db.shop.Tables.CARD_BATCH;
import static com.vpu.mp.db.shop.Tables.CARD_CONSUMER;
import static com.vpu.mp.db.shop.Tables.CARD_EXAMINE;
import static com.vpu.mp.db.shop.Tables.CARD_RECEIVE_CODE;
import static com.vpu.mp.db.shop.Tables.CHARGE_MONEY;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.ALL_BATCH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_FG_STOP;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_FG_EXPIRED;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_FG_USING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.COUNT_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.EXCHANG_COUNT_TYPE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.SHORT_ZERO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_GRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_FLAG_USING;
import java.sql.Timestamp;
import java.util.List;

import com.vpu.mp.db.shop.tables.User;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.db.shop.tables.records.CardReceiveCodeRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.RemarkUtil;
import com.vpu.mp.service.pojo.shop.member.card.CardBatchDetailVo;
import com.vpu.mp.service.pojo.shop.member.card.CardBatchParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumeParam;
import com.vpu.mp.service.pojo.shop.member.card.CardConsumeVo;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderParam;
import com.vpu.mp.service.pojo.shop.member.card.CardHolderVo;
import com.vpu.mp.service.pojo.shop.member.card.ChargeParam;
import com.vpu.mp.service.pojo.shop.member.card.ChargeVo;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveParam;
import com.vpu.mp.service.pojo.shop.member.card.CodeReceiveVo;

/**
 * @author 黄壮壮
 * @Date: 2019年9月24日
 * @Description: 会员卡dao访问
 */
@Service
public class CardDaoService extends ShopBaseService {

	public PageResult<CardHolderVo> getAllCardHolder(CardHolderParam param) {

		User invitedUser = USER.as("a");
		SelectJoinStep<?> select = db()
				.select(USER_CARD.USER_ID, USER.USERNAME, USER.MOBILE, invitedUser.USERNAME.as("invitedName"),
						USER_CARD.CREATE_TIME, USER_CARD.CARD_NO, USER_CARD.FLAG, USER_CARD.EXPIRE_TIME,USER_CARD.UPDATE_TIME)
				.from(USER_CARD.leftJoin(USER.leftJoin(invitedUser).on(USER.INVITE_ID.eq(invitedUser.USER_ID))

				).on(USER_CARD.USER_ID.eq(USER.USER_ID)));

		buildOptions(param, select);
		select.where(USER_CARD.CARD_ID.eq(param.getCardId())).orderBy(USER_CARD.USER_ID.desc());
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), CardHolderVo.class);
	}

	/**
	 * 构建查询参数
	 * 
	 * @param param
	 * @param select
	 */
	private void buildOptions(CardHolderParam param, SelectJoinStep<?> select) {
		/** - 会员id */
		if (param.getUserId() != null) {
			select.where(USER_CARD.USER_ID.eq(param.getUserId()));
		}
		/** - 昵称 */
		if (!StringUtils.isBlank(param.getUsername())) {
			String likeValue = likeValue(param.getUsername().trim());
			select.where(USER.USERNAME.like(likeValue));
		}
		/** -手机号 */
		if (!StringUtils.isBlank(param.getMobile())) {
			String likeValue = likeValue(param.getMobile().trim());
			select.where(USER.MOBILE.like(likeValue));
		}
		/** - 会员卡号 */
		if (!StringUtils.isBlank(param.getCardNo())) {
			String likeValue = likeValue(param.getCardNo().trim());
			select.where(USER_CARD.CARD_NO.like(likeValue));
		}
		/** - 卡状态 */
		if (param.getFlag() != null) {
			/** - 状态为过期 */
			Condition condition = DSL.noCondition();
			if (param.getFlag().equals(UCARD_FG_EXPIRED)) {
				
				condition = condition.and(USER_CARD.EXPIRE_TIME.le(DateUtil.getLocalDateTime()));
				select.where(condition);
			} else if (param.getFlag().equals(UCARD_FG_USING)) {
				condition = condition.and(USER_CARD.EXPIRE_TIME.ge(DateUtil.getLocalDateTime()).or(USER_CARD.EXPIRE_TIME.isNull()))
									.and(USER_CARD.FLAG.eq(param.getFlag()));
				select.where(condition);
			}else if(param.getFlag().equals(UCARD_FG_STOP)) {
				condition = condition.and(USER_CARD.FLAG.eq(param.getFlag()));
				select.where(condition);
			}
		}
		/** - 领卡时间 开始范围 */
		if (param.getFirstDateTime() != null) {
			select.where(USER_CARD.CREATE_TIME.ge(param.getFirstDateTime()));
		}

		/** - 领卡时间 结束范围 */
		if (param.getSecondDateTime() != null) {
			select.where(USER_CARD.CREATE_TIME.le(param.getSecondDateTime()));
		}
	}

	/**
	 * 分页查询会员卡领取详情
	 */
	public PageResult<CodeReceiveVo> getReceiveListSql(CodeReceiveParam param) {
		// CARD_RECEIVE_CODE
		SelectConditionStep<?> select = db()
				.select(CARD_BATCH.NAME, CARD_RECEIVE_CODE.ID, USER.USER_ID, USER.USERNAME, USER.MOBILE,
						CARD_RECEIVE_CODE.RECEIVE_TIME, CARD_RECEIVE_CODE.CARD_NO, CARD_RECEIVE_CODE.CODE,
						CARD_RECEIVE_CODE.CARD_PWD, CARD_RECEIVE_CODE.DEL_FLAG)
				.from(CARD_RECEIVE_CODE.leftJoin(USER).on(CARD_RECEIVE_CODE.USER_ID.eq(USER.USER_ID))
						.leftJoin(CARD_BATCH).on(CARD_RECEIVE_CODE.BATCH_ID.eq(CARD_BATCH.ID)))
				.where(CARD_RECEIVE_CODE.CARD_ID.eq(param.getCardId()));

		buildOptionForReceiveCode(param, select);
		return this.getPageResult(select, param.getCurrentPage(), param.getPageRows(), CodeReceiveVo.class);

	}

	/**
	 * 会员卡领取详情构建多条件查询参数
	 * 
	 * @param param
	 * @param select
	 */
	private void buildOptionForReceiveCode(CodeReceiveParam param, SelectConditionStep<?> select) {
		/** -手机号 */
		if (!StringUtils.isBlank(param.getMobile())) {
			select.and(USER.MOBILE.eq(param.getMobile()));
		}
		/** -用户名 */
		if (!StringUtils.isBlank(param.getUsername())) {
			String likeValue = this.likeValue(param.getUsername());
			select.and(USER.USERNAME.like(likeValue));
		}
		/** -批次号 */
		if (param.getBatchId() != null && !param.getBatchId().equals(ALL_BATCH)) {
			select.and(CARD_RECEIVE_CODE.BATCH_ID.eq(param.getBatchId()));
		}
	}

	/**
	 * 设置del_flag为删除状态
	 * 
	 * @param id
	 */
	public Integer deleteCardBatchSql(Integer id) {
		return db().update(CARD_RECEIVE_CODE).set(CARD_RECEIVE_CODE.DEL_FLAG, MCARD_DF_YES)
				.where(CARD_RECEIVE_CODE.ID.eq(id)).execute();
	}

	/**
	 * 会员卡充值明细
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<ChargeVo> getChargeList(ChargeParam param,String language) {
		SelectJoinStep<?> select = db()
				.select(USER.USERNAME, USER.MOBILE, CHARGE_MONEY.CHARGE.as("money"), CHARGE_MONEY.REASON,
						CHARGE_MONEY.CHARGE,CHARGE_MONEY.TYPE,CHARGE_MONEY.COUNT,CHARGE_MONEY.EXCHANG_COUNT,
						CHARGE_MONEY.CREATE_TIME, CHARGE_MONEY.MESSAGE)
				.from(CHARGE_MONEY.leftJoin(USER).on(CHARGE_MONEY.USER_ID.eq(USER.USER_ID)));

		buildOptionsForCharge(param, select);

		PageResult<ChargeVo> result = getPageResult(select, param.getCurrentPage(), param.getPageRows(), ChargeVo.class);
		for(ChargeVo vo: result.dataList) {
			String reason = RemarkUtil.remarkI18N(language, vo.getReasonId(), vo.getReason());
			vo.setReason(reason);
		}
		return result;
	}

	/**
	 * 充值明细多条件构建
	 * 
	 * @param param
	 * @param select
	 */
	private void buildOptionsForCharge(ChargeParam param, SelectJoinStep<?> select) {
		// 会员卡id
		if (param.getCardId() != null) {
			select.where(CHARGE_MONEY.CARD_ID.eq(param.getCardId()));
		}
		// 会员卡类型
		if (param.getCardType() != null) {
			select.where(CHARGE_MONEY.TYPE.eq(param.getCardType()));
		}
		// 用户名
		if (!StringUtils.isBlank(param.getUsername())) {
			select.where(USER.USERNAME.eq(param.getUsername()));
		}

		// 手机号
		if (!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.eq(param.getMobile()));
		}

		// 余额变动时间 - 开始
		if (param.getStartTime() != null) {
			select.where(CHARGE_MONEY.CREATE_TIME.ge(param.getStartTime()));
		}
		// 余额变动时间 - 结束
		if (param.getEndTime() != null) {
			select.where(CHARGE_MONEY.CREATE_TIME.le(param.getEndTime()));
		}
		if(StringUtils.isEmpty(param.getCardNo())) {
			select.where(CHARGE_MONEY.CARD_NO.eq(param.getCardNo()));
		}
	}

	/**
	 * 会员卡消费明细
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<ChargeVo> getConsumeList(ChargeParam param,String language) {
		SelectJoinStep<?> select = db()
				.select(USER.USERNAME, USER.MOBILE, CARD_CONSUMER.MONEY, CARD_CONSUMER.REASON,CARD_CONSUMER.TYPE,CARD_CONSUMER.EXCHANG_COUNT,CARD_CONSUMER.COUNT,
						CARD_CONSUMER.CREATE_TIME, CARD_CONSUMER.MESSAGE)
				.from(CARD_CONSUMER.leftJoin(USER).on(CARD_CONSUMER.USER_ID.eq(USER.USER_ID)));
		buildOptionsForConsume(param, select);
		PageResult<ChargeVo> result = getPageResult(select, param.getCurrentPage(), param.getPageRows(), ChargeVo.class);
		// 处理国际化
		for(ChargeVo vo: result.dataList) {
			String reason = RemarkUtil.remarkI18N(language, vo.getReasonId(), vo.getReason());
			vo.setReason(reason);
		}
		return result;
	}

	/**
	 * 消费明细多条件查询构建
	 * 
	 * @param param
	 * @param select
	 */
	private void buildOptionsForConsume(ChargeParam param, SelectJoinStep<?> select) {

		// 会员卡id
		if (param.getCardId() != null) {
			select.where(CARD_CONSUMER.CARD_ID.eq(param.getCardId()));
		}
		// 会员卡类型
		if (param.getCardType() != null) {
			select.where(CARD_CONSUMER.TYPE.eq(param.getCardType()));
		}
		// 用户名
		if (!StringUtils.isBlank(param.getUsername())) {
			select.where(USER.USERNAME.eq(param.getUsername()));
		}

		// 手机号
		if (!StringUtils.isBlank(param.getMobile())) {
			select.where(USER.MOBILE.eq(param.getMobile()));
		}

		// 余额变动时间 - 开始
		if (param.getStartTime() != null) {
			select.where(CARD_CONSUMER.CREATE_TIME.ge(param.getStartTime()));
		}
		// 余额变动时间 - 结束
		if (param.getEndTime() != null) {
			select.where(CARD_CONSUMER.CREATE_TIME.le(param.getEndTime()));
		}
	}

	/**
	 * 更新审核会员卡表
	 * 
	 * @param record
	 */
	public void updateCardExamine(CardExamineRecord record) {
		db().executeUpdate(record, CARD_EXAMINE.ID.eq(record.getId()));
	}

	/**
	 * 更新user_card 激活时间
	 * 
	 * @param cardNo
	 * @param now
	 */
	public void updateUserCardByCardNo(String cardNo, Timestamp now) {
		db().update(USER_CARD).set(USER_CARD.ACTIVATION_TIME, now).where(USER_CARD.CARD_NO.eq(cardNo)).execute();
	}

	/**
	 * 获取会员卡订单信息
	 * 
	 * @param param
	 * @return
	 */
	public PageResult<CardConsumeVo> getCardConsumeOrderList(CardConsumeParam param) {
		SelectConditionStep<?> select = db()
				.select(CARD_CONSUMER.ORDER_SN, CARD_CONSUMER.EXCHANG_COUNT, CARD_CONSUMER.COUNT,
						CARD_CONSUMER.CREATE_TIME, USER.USERNAME, USER.MOBILE)
				.from(CARD_CONSUMER.leftJoin(USER).on(CARD_CONSUMER.USER_ID.eq(USER.USER_ID)))
				.where(CARD_CONSUMER.CARD_ID.eq(param.getCardId())).and(CARD_CONSUMER.ORDER_SN.isNotNull());

		buildOptionsForCardConsumer(select, param);
		select.orderBy(CARD_CONSUMER.CREATE_TIME.desc());
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), CardConsumeVo.class);
	}

	/**
	 * 会员卡查询获取参数s
	 * 
	 * @param select
	 * @param param
	 */
	private void buildOptionsForCardConsumer(SelectConditionStep<?> select, CardConsumeParam param) {
		// 订单号
		if (!StringUtils.isBlank(param.getOrderSn())) {
			select.and(CARD_CONSUMER.ORDER_SN.eq(param.getOrderSn()));
		}
		// 会员昵称
		if (!StringUtils.isBlank(param.getUsername())) {
			String likeValue = this.likeValue(param.getUsername());
			select.and(USER.USERNAME.like(likeValue));
		}
		// 手机号
		if (!StringUtils.isBlank(param.getMobile())) {
			select.and(USER.MOBILE.eq(param.getMobile()));
		}
		// 次数使用类型
		if (param.getType() != null) {
			// 1 兑换商品
			if (EXCHANG_COUNT_TYPE.equals(param.getType())) {
				select.and(CARD_CONSUMER.EXCHANG_COUNT.notEqual(SHORT_ZERO));
			} else if (COUNT_TYPE.equals(param.getType())) {
				// 2 门店服务
				select.and(CARD_CONSUMER.COUNT.notEqual(SHORT_ZERO));
			}
		}

		// 次数变动时间
		if (param.getFirstTime() != null) {
			select.and(CARD_CONSUMER.CREATE_TIME.ge(param.getFirstTime()));
		}
		if (param.getSecondTime() != null) {
			select.and(CARD_CONSUMER.CREATE_TIME.le(param.getSecondTime()));
		}
	}

	/**
	 * 插入card_batch 批次记录
	 * 
	 * @param param
	 * @return 批次id
	 */
	public Integer createCardBatch(CardBatchParam param) {
		Record1<Integer> batchId = db()
				.insertInto(CARD_BATCH, CARD_BATCH.ACTION, CARD_BATCH.CODE_PREFIX, CARD_BATCH.NAME,
						CARD_BATCH.CODE_SIZE,CARD_BATCH.CARD_PWD_SIZE, CARD_BATCH.NUMBER)
				.values(param.getAction(), param.getCodePrefix(), param.getBatchName(), param.getCodeSize(),param.getCardPwdSize(),
						param.getNumber())
				.returningResult(CARD_BATCH.ID).fetchOne();

		if (batchId != null) {
			logger().info("批次id为： " + batchId.get(CARD_BATCH.ID));
			return batchId.get(CARD_BATCH.ID);
		} else {
			return 0;
		}

	}

	/**
	 * 生成分组ID
	 * 
	 * @param batchId
	 */
	public Integer generateGroupId(Integer batchId) {
		Record1<Integer> maxGroupId = db().select(DSL.max(CARD_RECEIVE_CODE.GROUP_ID)).from(CARD_RECEIVE_CODE)
				.where(CARD_RECEIVE_CODE.BATCH_ID.eq(batchId)).fetchOne();
		if (maxGroupId.value1() != null) {
			return maxGroupId.into(Integer.class) + 1;
		} else {
			return 1;
		}
	}

	/**
	 * code是否存在，是返回true，否返回false
	 * 
	 * @param code
	 * @return
	 */
	public boolean isExistCode(String code) {

		CardReceiveCodeRecord r = db().selectFrom(CARD_RECEIVE_CODE).where(CARD_RECEIVE_CODE.CODE.eq(code))
				.and(CARD_RECEIVE_CODE.DEL_FLAG.eq(MCARD_DF_NO)).and(CARD_RECEIVE_CODE.ERROR_MSG.isNull()).fetchAny();

		return r != null;
	}

	/**
	 * 将领取批次的生成码存入数据库
	 * 
	 * @param param
	 * @param codeList
	 */
	public void insertIntoCardReceiveCode(CardBatchParam param, List<String> codeList) {
		InsertValuesStep3<CardReceiveCodeRecord, Integer, Integer, String> insert = db().insertInto(CARD_RECEIVE_CODE)
				.columns(CARD_RECEIVE_CODE.BATCH_ID, CARD_RECEIVE_CODE.GROUP_ID, CARD_RECEIVE_CODE.CODE);

		for (String code : codeList) {
			insert.values(param.getBatchId(), param.getGroupId(), code);
		}
		int res = insert.execute();
		logger().info("成功生成领取码" + res + "条");

	}

	/**
	 * 卡号存在 是 true, 否 false
	 * 
	 * @param cardNo
	 * @return
	 */
	public boolean isExistCardNo(String cardNo) {
		CardReceiveCodeRecord res = db().selectFrom(CARD_RECEIVE_CODE).where(CARD_RECEIVE_CODE.CARD_NO.eq(cardNo))
				.and(CARD_RECEIVE_CODE.ERROR_MSG.isNull()).fetchAny();
		return res != null;
	}

	/**
	 * 插入数据
	 * 
	 * @param param
	 * @param cardNoList
	 * @param pwdList
	 */
	public void insertIntoCardReceiveCode(CardBatchParam param, List<String> cardNoList, List<String> pwdList) {
		InsertValuesStep4<CardReceiveCodeRecord, Integer, Integer, String, String> insert = db()
				.insertInto(CARD_RECEIVE_CODE).columns(CARD_RECEIVE_CODE.BATCH_ID, CARD_RECEIVE_CODE.GROUP_ID,
						CARD_RECEIVE_CODE.CARD_NO, CARD_RECEIVE_CODE.CARD_PWD);

		for (int i = 0; i < param.getNumber(); i++) {
			insert.values(param.getBatchId(), param.getGroupId(), cardNoList.get(i), pwdList.get(i));
		}
		int res = insert.execute();
		logger().info("成功执行" + res + "条");
	}

	/**
	 * 根据会员卡类型查询会员卡
	 */
	public List<Integer> getCardIdByType(Byte type) {
		return db().select(MEMBER_CARD.ID).from(MEMBER_CARD).where(MEMBER_CARD.CARD_TYPE.eq(type))
				.fetchInto(Integer.class);
	}

	/**
	 * 获取会员卡信息
	 */
	public MemberCardRecord getCardById(Integer cardId) {
		MemberCardRecord mCard = getInfoByCardId(cardId);
		// 处理背景图片
		if(mCard != null && CardUtil.isBgImgType(mCard.getBgType())) {
			if(!StringUtils.isBlank(mCard.getBgImg())) {
				String imageUrl = saas.getShopApp(getShopId()).image.imageUrl(mCard.getBgImg());
				mCard.setBgImg(imageUrl);
			}
		}
		return mCard;
		
	}

	public MemberCardRecord getInfoByCardId(Integer cardId) {
		MemberCardRecord mCard = db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.ID.eq(cardId)).fetchAny();
		return mCard;
	}

	/**
	 * 获取所有正在使用的等级会员卡
	 */
	public List<MemberCardRecord> getAllUsingGradeCard() {
		return db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
				.and(MEMBER_CARD.FLAG.eq(MCARD_FLAG_USING)).orderBy(MEMBER_CARD.GRADE.asc())
				.fetchInto(MemberCardRecord.class);
	}
	
	
	/**
	 * 将数据放入到数据库
	 */
	public int addMemberCardAndGetCardId(MemberCardRecord cardRecord) {
		MemberCardRecord c = db().insertInto(MEMBER_CARD).set(cardRecord).returning(MEMBER_CARD.ID).fetchOne();
		logger().info("新成功新的会员卡，id: "+c.getId());
		return c.getId();
	}

	/**
	 * 会员卡数据更新
	 */
	public void updateMemberCardById(MemberCardRecord cardRecord, Integer id) {
		int num = db().executeUpdate(cardRecord, MEMBER_CARD.ID.eq(id));
		logger().info("成功更新： " + num + " 行数据");
	}

	public List<CardBatchDetailVo> selectBatchCfgById(Integer batchId) {
		 return db()
				 .selectFrom(CARD_BATCH)
				 .where(CARD_BATCH.ID.eq(batchId))
				 .fetchInto(CardBatchDetailVo.class);
	}
	
}
