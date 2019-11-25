package com.vpu.mp.service.shop.member.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.records.*;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.account.WxAppUserCardVo;
import com.vpu.mp.service.pojo.shop.member.bo.UserCardGradePriceBo;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import com.vpu.mp.service.shop.member.UserCardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.*;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
* @author 黄壮壮
* @Date: 2019年10月10日
* @Description:
*/
@Slf4j
@Service
public class UserCardDaoService extends ShopBaseService{
	final static Byte CARD_ONLINE = 0;
	final static Byte CARD_OFFLINE = 1;
	@Autowired private  UserCardService userCardService;


    /**
	 * 获取用户持有的等级卡
	 */
	public MemberCardRecord getUserGradeCard(Integer userId) {
		return  db().select(MEMBER_CARD.asterisk())
				.from(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
				.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
				.and(USER_CARD.USER_ID.eq(userId))
				.fetchAnyInto(MemberCardRecord.class);
	}

	/**
	 * 获取会员卡
	 * @param cardId 会员卡Id
	 */
	public MemberCardRecord getMemberCardById(Integer cardId) {
		  return db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.ID.eq(cardId)).fetchAny();
	}

	/**
	 * 更新userCard
	 */
	public void updateUserCard(Integer userId,Integer cardId) {
		db().update(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
			.set(USER_CARD.CARD_ID,cardId).set(USER_CARD.FLAG,UCARD_FG_USING)
			.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(USER_CARD.USER_ID.eq(userId))
			.execute();
	}

	/**
	 * 插入会员持卡升级记录
	 */
	public void insertIntoCardUpGrade(CardUpgradeRecord r) {

		db().executeInsert(r);
	}

	/**
	 * 获取店铺的积分限制配置信息
	 */
	public String getScoreLimit() {
		String val = "score_limit";
		ShopCfgRecord res = db().selectFrom(SHOP_CFG).where(SHOP_CFG.K.eq(val)).fetchAny();
		if(res != null) {
			return res.getV();
		}else {
			return null;
		}

    }


	public void updateCardFlag(List<Integer> cardIdList, List<String> cardNoList) {
		db().update(USER_CARD)
			.set(USER_CARD.FLAG, UCARD_FG_STOP)
			.where(USER_CARD.CARD_ID.in(cardIdList))
			.and(USER_CARD.CARD_NO.notIn(cardNoList))
			.execute();
	}

	/**
	 * 获取可用的用户卡
	 */
	public UserCardRecord getUsableUserCard(Integer userId, UserCardParam card) {
		SelectConditionStep<UserCardRecord> sql = db().selectFrom(USER_CARD)
			.where(USER_CARD.CARD_ID.eq(userId))
			.and(USER_CARD.CARD_ID.eq(card.getCardId()))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING));
		if(StringUtils.isBlank(card.getCardName())) {
			sql.and((USER_CARD.EXPIRE_TIME.isNull()).or(USER_CARD.EXPIRE_TIME.ge(DateUtil.getLocalDateTime())));
		}

		 return sql.fetchAny();
	}

	/**
	 * 获取有效用户会员卡列表
	 * @param cardType -1所有可用卡  卡类型
	 * @param type 0线上 1线下
	 */
	public List<ValidUserCardBean> getValidCardList(Integer userId){
		return getValidCardList(userId,MCARD_TP_ALL,CARD_ONLINE);
	}

	public List<ValidUserCardBean> getValidCardList(Integer userId,Byte cardType,Byte type){
		if(MCARD_TP_ALL.equals(cardType)) {
			return getAllValidCardList(userId);
		}else {
			return getValidCardList(userId,new Byte[] {cardType},type);
		}
	}

    /**
     * Gets store valid card list.获取门店有效用户会员卡列表
     *
     * @param userId  the user id
     * @param storeId the store id
     * @return the store valid card list
     */
    public List<ValidUserCardBean> getStoreValidCardList(Integer userId, Integer storeId) {
        List<ValidUserCardBean> result = getValidCardList(userId, BYTE_ZERO, BYTE_ZERO)
            .stream().filter((c) -> org.jooq.tools.StringUtils.isBlank(c.getStoreList()) || Objects.requireNonNull(Util.json2Object(c.getStoreList(), new TypeReference<List<Integer>>() {
            }, false)).contains(storeId))
            .collect(Collectors.toList());
        log.debug("用户【{}】在门店【{}】的有效会员卡列表：{}", userId, storeId, result);
        return result;
    }

    /**
     * Check store valid card boolean.校验会员卡的有效性
     *
     * @param userId  the user id
     * @param storeId the store id
     * @param cardNo  the card no
     * @return the boolean
     */
    public boolean checkStoreValidCard(Integer userId, Integer storeId, String cardNo) {
        return getStoreValidCardList(userId, storeId).stream()
            // todo 该方法目前只有门店使用，门店暂不支持会员卡次卡，所以过滤掉
            .filter((e) -> BYTE_ZERO.equals(e.getCardType()))
            .anyMatch((e) -> cardNo.equals(e.getCardNo()));
    }

	public List<ValidUserCardBean> getValidCardList(Integer userId,Byte[] cardType,Byte type) {
		logger().info("获取有效会员卡");
		assert cardType != null : "card type should not be null";
		if(cardType.length==1 && MCARD_TP_ALL.equals(cardType[0])) {
			// 所有可用卡
            return getAllValidCardList(userId);
		}
		if(CARD_OFFLINE.equals(type)) {
			// 线下处理
            return getOfflineValidCardList(userId, cardType);
		}
		// 线上
		return getOnlineValidCardList(userId, cardType);
	}

    private List<ValidUserCardBean> getOnlineValidCardList(Integer userId, Byte[] cardType){
		return selectValidCardCondition(userId, cardType)
				.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.GRADE.desc())
				.fetchInto(ValidUserCardBean.class);
	}

    private List<ValidUserCardBean> getOfflineValidCardList(Integer userId, Byte[] cardType) {
		return selectValidCardCondition(userId, cardType)
							.and(MEMBER_CARD.STORE_USE_SWITCH.eq(AVAILABLE_IN_STORE))
							.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.GRADE.desc())
							.fetchInto(ValidUserCardBean.class);
	}

	private SelectConditionStep<Record> selectValidCardCondition(Integer userId, Byte[] cardType) {
		return selectValidCardSQL().where(USER_CARD.USER_ID.eq(userId))
							.and(USER_CARD.FLAG.eq(MCARD_DF_NO))
							.and(MEMBER_CARD.CARD_TYPE.in(cardType))
							.and(
									(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
									.or(MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FOREVER))
								)
							.and(
									(MEMBER_CARD.USE_TIME.in(userCardService.useInDate()))
									.or(MEMBER_CARD.USE_TIME.isNull())
								)
							.and(
									((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
									.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING,MCARD_ET_FOREVER))
								)
							.and(
									(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull()))
									.or(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_NO))
									.or(MEMBER_CARD.ACTIVATION_CFG.isNull())
								);
	}

	/**
	 * 获取用户所有的可用卡列表
	 */
	private List<ValidUserCardBean> getAllValidCardList(Integer userId) {

        return selectValidCardSQL()
			.where(USER_CARD.USER_ID.eq(userId))
			.and(USER_CARD.FLAG.eq(MCARD_DF_NO))
			.and(
					(USER_CARD.EXPIRE_TIME.isNull())
					.or(USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
				)
            .and(
					((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
					.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING,MCARD_ET_FOREVER))
				)
			.orderBy(USER_CARD.IS_DEFAULT.desc(),MEMBER_CARD.GRADE.desc())
			.fetchInto(ValidUserCardBean.class);
	}

    /**
	 * 查询用户有效卡的信息
	 */
	private SelectJoinStep<Record> selectValidCardSQL() {
		 return db().select(USER_CARD.asterisk(),MEMBER_CARD.CARD_NAME,MEMBER_CARD.CARD_TYPE,MEMBER_CARD.DISCOUNT,MEMBER_CARD.BG_TYPE,MEMBER_CARD.BG_COLOR,
				MEMBER_CARD.BG_IMG,MEMBER_CARD.BUY_SCORE,MEMBER_CARD.EXPIRE_TYPE,MEMBER_CARD.START_TIME,MEMBER_CARD.END_TIME,MEMBER_CARD.RECEIVE_DAY,
				MEMBER_CARD.DATE_TYPE,MEMBER_CARD.STORE_USE_SWITCH,MEMBER_CARD.STORE_LIST,MEMBER_CARD.ACTIVATION,MEMBER_CARD.GRADE)
			.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)));
	}

    /**
	 * 获取用户持有会员卡的等级
	 */
	public String getUserCardGrade(Integer userId) {
		return db().select(MEMBER_CARD.GRADE).from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.where(USER_CARD.USER_ID.eq(userId))
			.and(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
			.fetchAnyInto(String.class);
	}

	/**
	 * 计算用户等级
	 */
    public String calcUserGrade(Integer userId) {
		return db().select(MEMBER_CARD.GRADE)
				.from(USER_CARD.leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)))
				.where(USER_CARD.FLAG.eq(UCARD_FG_USING))
				.and(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
				.and(USER_CARD.USER_ID.eq(userId))
				.and((MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_NO)).or(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull())))
            .fetchAnyInto(String.class);
    }

	/**
	 * 根据id获取用户名
	 */
	public String getUserName(Integer id) {
		return db().select(USER.USERNAME).from(USER).where(USER.USER_ID.eq(id)).fetchAnyInto(String.class);
	}

    /**
	 * 更新会员卡详情
	 */
	public int updateUserCardByNo(String cardNo,UserCardRecord record) {
		return  db().update(USER_CARD).set(record).where(USER_CARD.CARD_NO.eq(cardNo)).execute();
	}


    /**
	 * 获取会员卡详情
	 */
	public UserCardParam getUserCardInfo(String cardNo) {
		return wxUserCardSelectSql()
				.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
				.where(USER_CARD.CARD_NO.eq(cardNo))
				.fetchAnyInto(WxAppUserCardVo.class);
	}

    public int getHasSend(Integer cardId){
		return db().selectCount().from(USER_CARD).where(USER_CARD.CARD_ID.eq(cardId)).execute();
	}

    /**
	 * 升级卡
	 */
	public void updateUserRankCard(MemberCardRecord cardInfo,Integer userId) {
		db().update(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.set(USER_CARD.CARD_ID,cardInfo.getId())
			.set(USER_CARD.UPDATE_TIME,DateUtil.getLocalDateTime())
			.set(USER_CARD.FLAG,UCARD_FG_USING)
			.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(USER_CARD.USER_ID.eq(userId))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
			.execute();
	}


    /**
	 * 更新卡余额
	 */
	public int updateUserCardMoney(UserCardConsumeBean data, UserCardParam userInfo) {
		return db().update(USER_CARD)
			.set(USER_CARD.MONEY,userInfo.getMoney().add(data.getMoney()))
			.where(USER_CARD.CARD_NO.eq(data.getCardNo()))
			.execute();
	}
	/**
	 * 更新卡剩余次数
	 */
	public int updateUserCardSurplus(UserCardConsumeBean data, UserCardParam userInfo) {
		return db().update(USER_CARD)
			.set(USER_CARD.SURPLUS,userInfo.getSurplus()+data.getCount())
			.where(USER_CARD.CARD_NO.eq(data.getCardNo()))
			.execute();
	}

    /**
	 * 更新卡剩余兑换次数
	 */
	public int updateUserCardExchangePlus(UserCardConsumeBean data, UserCardParam userInfo) {
		return db().update(USER_CARD)
			.set(USER_CARD.EXCHANG_SURPLUS,userInfo.getExchangSurplus()+data.getExchangCount())
			.where(USER_CARD.CARD_NO.eq(data.getCardNo()))
			.execute();
	}

    /**
	 * 消费记录
	 */
	public void insertIntoCharge(UserCardConsumeBean data) {
		ChargeMoneyRecord chargeMoney = db().newRecord(CHARGE_MONEY);
		FieldsUtil.assignNotNull(data, chargeMoney);
		chargeMoney.insert();
	}
	/**
	 * 充值记录
	 */
	public void insertConsume(UserCardConsumeBean data) {
		CardConsumerRecord cardConsumer = db().newRecord(CARD_CONSUMER);
		FieldsUtil.assignNotNull(data, cardConsumer);
		cardConsumer.insert();
	}

    /**
	 * 获取用户会员卡列表
	 */
	public PageResult<WxAppUserCardVo> getCardList(SearchCardParam param) {

        SelectSeekStep3<Record, String, Byte, Timestamp> select = wxUserCardSelectSql()
				.from(USER_CARD)
				.leftJoin(MEMBER_CARD)
				.on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID))
				.where(USER_CARD.USER_ID.eq(param.getUserId()))
				.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
				.orderBy(MEMBER_CARD.GRADE.desc(),USER_CARD.IS_DEFAULT.desc(),USER_CARD.CREATE_TIME.desc());
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), WxAppUserCardVo.class);
    }

	private SelectSelectStep<Record> wxUserCardSelectSql(){
		return db().select(
			 	USER_CARD.USER_ID,USER_CARD.CARD_ID,USER_CARD.FLAG.as("userCardFlag"),USER_CARD.CARD_NO,USER_CARD.EXPIRE_TIME,
			 	USER_CARD.IS_DEFAULT,USER_CARD.MONEY,USER_CARD.SURPLUS,USER_CARD.ACTIVATION_TIME,USER_CARD.EXCHANG_SURPLUS,
			 	USER_CARD.CREATE_TIME.as("userCardCreateTime"),USER_CARD.UPDATE_TIME.as("userCardUpdateTime"),
			 MEMBER_CARD.asterisk());
	}

    /**
	 * get card type
	 * @param cardNo
	 */
	public Byte getCardType(String cardNo) {
		return db().select(MEMBER_CARD.CARD_TYPE)
			.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.where(USER_CARD.CARD_NO.eq(cardNo))
			.fetchAnyInto(Byte.class);
	}

    public int calcNumCardById(Integer cardId) {
		return db().fetchCount(USER_CARD, USER_CARD.CARD_ID.eq(cardId));
	}

	public int updateUserGradeCardId(Integer userId,Integer cardId) {
		return  db().update(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
			.set(USER_CARD.CARD_ID,cardId)
			.set(USER_CARD.UPDATE_TIME,DateUtil.getLocalDateTime())
			.set(USER_CARD.FLAG,UCARD_FG_USING)
			.where(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
			.and(USER_CARD.FLAG.eq(UCARD_FG_USING))
			.and(USER_CARD.USER_ID.eq(userId))
			.execute();
	}

	public int getNumHasSendUser(Integer userId, Integer cardId) {
		return db().fetchCount(USER_CARD, USER_CARD.USER_ID.eq(userId).and(USER_CARD.CARD_ID.eq(cardId)));
	}

	public List<UserCardGradePriceBo> getUserCartGradePrice(Integer userId, List<Integer> prdIdList) {
		return db().select(MEMBER_CARD.CARD_NAME, MEMBER_CARD.GRADE, GRADE_PRD.GOODS_ID, GRADE_PRD.PRD_ID,
						GRADE_PRD.GRADE_PRICE)
				.from(USER_CARD).leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)).leftJoin(GRADE_PRD)
				.on(GRADE_PRD.GRADE.eq(MEMBER_CARD.GRADE)).where(USER_CARD.FLAG.eq(CardConstant.UCARD_FG_USING))
				.and(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE)).and(USER_CARD.USER_ID.eq(userId))
				.and(GRADE_PRD.PRD_ID.in(prdIdList)).fetchInto(UserCardGradePriceBo.class);

	}

    /**
     * 王帅
     * 获取有效会员卡
     * @param cardNo
     * @return
     */
	public OrderMemberVo getValidByCardNo(String cardNo){
        ValidUserCardBean validUserCardBean = selectValidCardSQL().where(USER_CARD.CARD_NO.eq(cardNo))
            .and(USER_CARD.FLAG.eq(MCARD_DF_NO))
            .and(
                (USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
                    .or(MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FOREVER))
            )
            .and(
                (MEMBER_CARD.USE_TIME.in(userCardService.useInDate()))
                    .or(MEMBER_CARD.USE_TIME.isNull())
            )
            .and(
                ((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
                    .or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING, MCARD_ET_FOREVER))
            )
            .and(
                (MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull()))
                    .or(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_NO))
                    .or(MEMBER_CARD.ACTIVATION_CFG.isNull())
            ).fetchAnyInto(ValidUserCardBean.class);
        if(validUserCardBean != null) {
            return new OrderMemberVo().init(validUserCardBean);
        }
        return null;
    }

    /**
     * 王帅
     * 获取等级卡
     * @param userId id
     * @return result
     */
    public OrderMemberVo getOrderGradeCard(Integer userId){
        OrderMemberVo orderMemberVo = selectValidCardSQL().where(USER_CARD.USER_ID.eq(userId))
            .and(USER_CARD.FLAG.eq(MCARD_DF_NO))
            .and(MEMBER_CARD.CARD_TYPE.eq(MCARD_TP_GRADE))
            .and(
                (USER_CARD.EXPIRE_TIME.greaterThan(DateUtil.getLocalDateTime()))
                    .or(MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FOREVER))
            )
            .and(
                (MEMBER_CARD.USE_TIME.in(userCardService.useInDate()))
                    .or(MEMBER_CARD.USE_TIME.isNull())
            )
            .and(
                ((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX)).and(MEMBER_CARD.START_TIME.le(DateUtil.getLocalDateTime())))
                    .or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING, MCARD_ET_FOREVER))
            )
            .and(
                (MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_YES).and(USER_CARD.ACTIVATION_TIME.isNotNull()))
                    .or(MEMBER_CARD.ACTIVATION.eq(MCARD_ACT_NO))
                    .or(MEMBER_CARD.ACTIVATION_CFG.isNull())
            ).fetchAnyInto(OrderMemberVo.class);
        if(orderMemberVo == null) {
            return orderMemberVo;
        }else {
            return orderMemberVo.init();
        }
    }

    /**
     * 获取营销会员卡（转化）
     * @param userId 用户id
     * @param cardType 卡类型
     * @param type 0线上 1线下
     * @return result
     */
    public List<OrderMemberVo> getOrderMembers(Integer userId,Byte[] cardType,Byte type) {
        List<ValidUserCardBean> validCardList = getValidCardList(userId, cardType, type);
        if(CollectionUtils.isEmpty(validCardList)){
            return Lists.newArrayList();
        }
        List<OrderMemberVo> result = new ArrayList<>(validCardList.size());
        for (ValidUserCardBean card : validCardList) {
            result.add(new OrderMemberVo().init(card));
        }
        return result;
    }

	/**
	 * 获取持有会员卡的用户id
	 */
	public List<Integer> getUserIdThatHasValidCard() {
		Timestamp localDateTime = DateUtil.getLocalDateTime();
		return db().select(USER_CARD.USER_ID)
				.from(USER_CARD.leftJoin(MEMBER_CARD).on(USER_CARD.CARD_ID.eq(MEMBER_CARD.ID)))
				.where(USER_CARD.FLAG.eq(UCARD_FG_USING))
				.and(USER_CARD.EXPIRE_TIME.greaterThan(localDateTime).or(MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FOREVER)))
				.and((MEMBER_CARD.EXPIRE_TYPE.eq(MCARD_ET_FIX).and(MEMBER_CARD.START_TIME.le(localDateTime)))
						.or(MEMBER_CARD.EXPIRE_TYPE.in(MCARD_ET_DURING, MCARD_ET_FOREVER)))
				.fetch().getValues(USER_CARD.USER_ID);
	}

}
