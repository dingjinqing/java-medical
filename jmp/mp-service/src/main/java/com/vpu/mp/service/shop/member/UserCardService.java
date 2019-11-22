package com.vpu.mp.service.shop.member;

import com.google.common.collect.Lists;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.process.DefaultMarketingProcess;
import org.apache.commons.collections4.CollectionUtils;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreVo;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.shop.distribution.DistributorSpendVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSmallVo;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.account.WxAppUserCardVo;
import com.vpu.mp.service.pojo.shop.member.bo.UserCardGradePriceBo;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;
import com.vpu.mp.service.pojo.shop.member.exception.CardSendRepeatException;
import com.vpu.mp.service.pojo.shop.member.exception.LimitCardAvailSendNoneException;
import com.vpu.mp.service.pojo.shop.member.exception.MemberCardNullException;
import com.vpu.mp.service.pojo.shop.member.exception.UserCardNullException;
import com.vpu.mp.service.pojo.shop.member.builder.ChargeMoneyRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.MemberCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardParamBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserScoreVoBuilder;
import com.vpu.mp.service.shop.distribution.DistributorLevelService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.operation.dao.TradesRecordDaoService;
import com.vpu.mp.service.shop.store.store.StoreService;

import jodd.util.StringUtil;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TYPE_SCORE_CREATE_CARD;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_IN;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TYPE_DEFAULT;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LOWEST_GRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_LIMIT;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_YES;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_DURING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_FIX;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_DAY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_WEEK;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_MONTH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_ACT_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.OPEN_CARD_SEND;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.SYSTEM_UPGRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.ADMIN_OPTION;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.EXCHANGE_GOODS_NUM;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.STORE_SERVICE_TIMES;
import static com.vpu.mp.service.pojo.shop.member.card.CardMessage.MEMBER_MONEY;



/**
 * @author 黄壮壮
 * @Date: 2019年10月10日
 * @Description:
 */
@Service
public class UserCardService extends ShopBaseService {
	/** 升级 */
	final static Byte UPGRADE = 1;
	/** 检测有卡领取 */
	final static Byte TP_CHECK = 0;
	/** 领取 */
	final static Byte TP_RECEIVE_ONE = 1;
	final static Byte TP_RECEIVE_TWO = 2;
	@Autowired
	public UserCardDaoService userCardDao;
	@Autowired
	public CardDaoService cardDao;
	@Autowired
	public ScoreService scoreService;
	@Autowired
	public MemberCardService memberCardService;
	@Autowired
	public MemberService memberService;
	@Autowired
	public DistributorLevelService distributorLevelService;
	@Autowired
	public TradesRecordService tradesRecord;
	@Autowired
	public StoreService storeService;
	@Autowired
	public GoodsService goodsService;
	@Autowired
	public CardVerifyService cardVerifyService;
	@Autowired
	private GoodsCardCoupleService goodsCardCoupleService;
	@Autowired
	private CardUpgradeService cardUpgradeService;
    @Autowired
    private Calculate calculate;
	public static final String DEFAULT_ADMIN = "0";

	public static final String OPTIONINFO = OPEN_CARD_SEND;
	public static final String DESC = "score_open_card";
	public static final String SYSTEM_UP_GRADE = SYSTEM_UPGRADE;

	/**
	 * 返回会员等级-按照持有会员等级卡划分，若无持有等级会员卡，则返回默认最低等级
	 */
	public String getUserGrade(Integer userId) {
		String userGrade = userCardDao.calcUserGrade(userId);
		return StringUtils.isBlank(userGrade) ? LOWEST_GRADE : userGrade;
	}

	/**
	 * 用户是否有等级卡
	 */
	private boolean isHasAvailableGradeCard(Integer userId) {
		return !StringUtils.isBlank(userCardDao.calcUserGrade(userId));
	}

	/**
	 * 获取用户持有的等级卡
	 */
	public MemberCardRecord getUserGradeCard(Integer userId) {
		MemberCardRecord card = userCardDao.getUserGradeCard(userId);
		return card != null ? card : new MemberCardRecord();
	}

	/**
	 * 用户卡等级变动
	 */
	private void changeUserGradeCard(Integer userId, MemberCardRecord oldCard, MemberCardRecord newCard) {
		logger().info("用户会员卡升级");
		updateUserGradeCardId(userId, newCard.getId());
		saveUpdateGradeRecord(userId, oldCard, newCard);
		if (newCard.getSorce() != null && newCard.getSorce() > 0) {
			addUserCardScore(userId, newCard);
		}
	}

	/**
	 * 更新用户等级卡Id
	 */
	private void updateUserGradeCardId(Integer userId, Integer cardId) {
		userCardDao.updateUserGradeCardId(userId, cardId);
	}

	/**
	 * 保存用户卡等级变动信息
	 */
	private void saveUpdateGradeRecord(Integer userId, MemberCardRecord oldCard, MemberCardRecord newCard) {
		cardUpgradeService.recordCardUpdateGrade(userId, oldCard, newCard);
	}

	/**
	 * 赠送积分
	 */
	private void addUserCardScore(Integer userId, MemberCardRecord card) {
		if (card.getSorce() == null || card.getSorce() <= 0) {
			return;
		}
		logger().info("卡升级赠送积分");
		// TODO 国际化
		UserScoreVo userScore = UserScoreVoBuilder.create().userId(userId).score(card.getSorce())
				.scoreDis(memberService.getUserScore(userId)).desc("score_open_card").remark("open.card.send.score")
				.expireTime(scoreService.getScoreExpireTime()).shopId(getShopId()).build();

		scoreService.addUserScore(userScore, DEFAULT_ADMIN, TYPE_SCORE_CREATE_CARD.val(),
				TRADE_FLOW_IN.val());
	}

	/**
	 * 会员卡升级检测并升级
	 * @param type 是否领取 1领取 0只是检测
	 * @return 检测的结果
	 */
	public Integer updateGrade(Integer userId, Integer cardId, Byte type) throws MpException {
		assert userId!=null:"userId required";

		if(cardId != null) {
				// 直接升级
				updateUserGradeCard(userId, cardId);
		}else{
				if(TP_RECEIVE_ONE.equals(type) || TP_RECEIVE_TWO.equals(type)) {
					// 检测升级
					cardId = checkAndUpgradeUserCard(userId);
				}else if(TP_CHECK.equals(type)) {
					// 检测可升级到的卡
					cardId = checkCardCanUpgrade(userId);
				}

				if (cardId == null) {
					logger().info("没有可升级的等级会员卡");
					throw new MemberCardNullException(JsonResultCode.CODE_CARD_GRADE_NONE);
				}else if(type == 0) {
					logger().info(String.format("检测到可领取等级卡 %d",cardId));
					// 仅仅检测是否可领取等级卡
					return cardId;
				}
		}
		return null;
	}


	private Integer checkAndUpgradeUserCard(Integer userId) throws MpException {
		Integer cardId = null;
		// 获取等级卡列表等级升序
		List<MemberCardRecord> gCardList = getAvailGradeCard();

		String uGrade = userCardDao.getUserCardGrade(userId);
		// 判断用户是否拥有等级卡
		if (StringUtils.isBlank(uGrade)) {
			// 用户第一次领取会员卡，给用户分配一级会员卡
			MemberCardRecord gCard = gCardList.get(0);
			logger().info("给用户分配等级卡: " + gCard.getCardName() + "等级: " + gCard.getGrade());
			addUserCard(userId, gCard.getId());
			uGrade = userCardDao.getUserCardGrade(userId);
		}

		// 获取用户累积获得积分和累积消费总额
		Integer userTotalScore = scoreService.getAccumulationScore(userId);
		BigDecimal amount = getUserTotalSpendAmount(userId);

		for (MemberCardRecord gCard : gCardList) {
			if(!StringUtils.isBlank(gCard.getGradeCondition())) {
				// 升级条件
				GradeConditionJson gradeCondition = getGradeCondition(userTotalScore, amount, gCard);
				// 等级卡的等级高于用户卡等级或者用户目前等级为空
				if (isCardGradeGtUserGrade(uGrade, gCard)) {
					if (isSatisfyUpgradeCondition(userTotalScore, amount, gradeCondition)) {
							cardId = gCard.getId();
							MemberCardRecord oldGradeCard = getUserGradeCard(userId);
							changeUserGradeCard(userId, oldGradeCard, gCard);
					}else {
						break;
					}
				}
			}
		}
		return cardId;
	}


	/**
	 * 检测可升级到的等级卡
	 */
	private Integer checkCardCanUpgrade(Integer userId) throws MemberCardNullException {
		String uGrade = userCardDao.getUserCardGrade(userId);
		Integer cardId = null;
		if(!StringUtils.isBlank(uGrade)) {
			List<MemberCardRecord> gradeCard = getAvailGradeCard();

			Integer userTotalScore = scoreService.getAccumulationScore(userId);
			BigDecimal amount = distributorLevelService.getTotalSpend(userId).getTotal();

			for (MemberCardRecord gCard : gradeCard) {
				GradeConditionJson gradeCondition = getGradeCondition(userTotalScore, amount, gCard);

				if(isSatisfyUpgradeCondition(userTotalScore, amount, gradeCondition)) {
					cardId = gCard.getId();
				} else {
					break;
				}
			}
		}
		return cardId;
	}

	private List<MemberCardRecord> getAvailGradeCard() throws MemberCardNullException {
		List<MemberCardRecord> gradeCard = cardDao.getAllUsingGradeCard();

		if (gradeCard.size() == 0) {
			logger().info("系统中没有等级卡");
			throw new MemberCardNullException(JsonResultCode.CODE_CARD_GRADE_NONE);
		}
		return gradeCard;
	}

	private GradeConditionJson getGradeCondition(Integer userTotalScore, BigDecimal amount, MemberCardRecord gCard) {

		GradeConditionJson gradeCondition = Util.parseJson(gCard.getGradeCondition(),
				GradeConditionJson.class);

		if (BigDecimalUtil.compareTo(gradeCondition.getGradeScore(), BigDecimal.ZERO) < 1) {
			gradeCondition.setGradeScore(new BigDecimal(userTotalScore + 1000));
		}

		if (BigDecimalUtil.compareTo(gradeCondition.getGradeMoney(), BigDecimal.ZERO) < 1) {
			gradeCondition.setGradeMoney(amount.add(new BigDecimal(1000)));
		}
		return gradeCondition;
	}

	/**
	 * 是否满足升级条件
	 */
	private boolean isSatisfyUpgradeCondition(Integer userTotalScore, BigDecimal amount,
			GradeConditionJson gradeCondition) {
		return gradeCondition.getGradeScore().intValue() <= userTotalScore
				|| BigDecimalUtil.compareTo(gradeCondition.getGradeMoney(), amount) <= 0;
	}

	private boolean isCardGradeGtUserGrade(String uGrade, MemberCardRecord gCard) {
		return !StringUtils.isBlank(uGrade) && !StringUtils.isBlank(gCard.getGrade())
				&& gCard.getGrade().compareTo(uGrade) > 0;
	}

	/**
	 * 用户卡升级
	 */
	private void updateUserGradeCard(Integer userId, Integer cardId) throws MemberCardNullException {
		// 等级卡升级
		if (isHasAvailableGradeCard(userId)) { 
			MemberCardRecord oldGradeCard = getUserGradeCard(userId);
			MemberCardRecord newGradeCard = memberCardService.getCardById(cardId);
			changeUserGradeCard(userId, oldGradeCard, newGradeCard);
		} else { 
			// 发放等级卡
			sendCard(userId, cardId);
		}
	}



	public void addUserCard(Integer userId, Integer... cardId) throws MpException {
		List<UserCardParam> cardList = new ArrayList<>();
		for(Integer id: cardId) {
			cardList.add(UserCardParamBuilder.create().cardId(id).build());
		}
		addUserCard(userId, cardList, UCARD_ACT_NO);
	}

	/**
	 * 添加会员卡
	 * @param isActive 是否直接激活  {@link UCARD_ACT_NO}
	 */
	public void addUserCard(Integer userId, List<UserCardParam> cardList, boolean isActivate) throws MpException {

		stopUserLimitCard(cardList);
		for(UserCardParam card: cardList) {
			MemberCardRecord mCard = cardDao.getCardById(card.getCardId());
			if(isLimitCard(mCard)) {
				if(canSendLimitCard(userId,mCard)) {
					sendCard(userId,mCard,isActivate);
				}else {
					logger().info("该限次卡领取次数用完");
					throw new LimitCardAvailSendNoneException();
				}
			}else if(StringUtils.isBlank(card.getCardNo())){
				sendCard(userId,mCard,isActivate);
			}else {
				logger().info("一张卡不能重复领取，除非此卡过期或删除");
				throw new CardSendRepeatException();
			}
		}
	}

	/**
	 * 检测限次卡是否能发放
	 */
	private boolean canSendLimitCard(Integer userId, MemberCardRecord mCard) {
		int numSendToUser = userCardDao.getNumHasSendUser(userId, mCard.getId());
		int totalSend = userCardDao.getHasSend(mCard.getId());
		// 检测每人领取次数,和允许发送的总量
		return !((mCard.getLimit() > 0 && numSendToUser >= mCard.getLimit())
				|| (mCard.getStock() > 0 && totalSend >= mCard.getStock()));
	}

	private void stopUserLimitCard(List<UserCardParam> cardList) {
		logger().info("处理user_card中限次卡逻辑");
		List<String> cardNos = cardList.stream().map(UserCardParam::getCardNo).collect(Collectors.toList());
		List<Integer> timeCardsId = memberCardService.getCardIdByType(MCARD_TP_LIMIT);
		userCardDao.updateCardFlag(timeCardsId, cardNos);
	}

	private void sendCard(Integer userId, Integer cardId) throws MemberCardNullException {
		MemberCardRecord card = MemberCardRecordBuilder.create().id(cardId).build();
		sendCard(userId, card, UCARD_ACT_NO);
	}

	/**
	 * 发卡
	 */
	public void sendCard(Integer userId, MemberCardRecord card, boolean isActivate) throws MemberCardNullException {
		logger().info("给用户发送会员卡");
		if (MCARD_DF_YES.equals(card.getDelFlag())) {
			throw new MemberCardNullException();
		}
		UserCardRecord newCard = createNewUserCard(userId, card, isActivate);

		addChargeMoney(card, newCard);
		addUserCardScore(userId, card);
		sendScoreTemplateMsg(card);
		return;
	}

	/**
	 * 发送模板消息
	 */
	private void sendScoreTemplateMsg(MemberCardRecord card) {
		StringBuilder content = new StringBuilder();
		if (isLimitCard(card)) {
			// TODO 国际化
			if (card.getIsExchang() != null && card.getIsExchang() > 0) {
				content.append(String.format("可兑换商品数量%d件,", card.getExchangCount()));
			} else if (card.getStoreUseSwitch() != null && card.getCount() > 0) {
				content.append(String.format("可使用门店服务%d次,", card.getCount()));
			}
			content.setLength(content.length() - 1);
		} else if (card.getDiscount().intValue() > 0) {
			content.append(String.format("打%f折", card.getDiscount().floatValue()));
		}
		StringBuilder expireTimeMessage = new StringBuilder();
		if (calcCardExpireTime(card) == null) {
			expireTimeMessage.append("card.effective.forever");
		} else {
			expireTimeMessage.append("card.effective.expired");
		}
		// TODO 消息队列
	}

	private void addChargeMoney(MemberCardRecord card, UserCardRecord userCard) {
		logger().info("正在充值卡数据");
		ChargeMoneyRecordBuilder builder = 
				ChargeMoneyRecordBuilder
				.create(db().newRecord(CHARGE_MONEY))
				.userId(userCard.getUserId())
				.cardId(userCard.getCardId())
				.type(card.getCardType())
				.cardNo(userCard.getCardNo())
				.payment("store.payment") 
				.createTime(DateUtil.getLocalDateTime());
		// TODO 门店支付国际化
		if (isNormalCard(card) && card.getSendMoney() != null) {
			// TODO 管理员发卡
			builder.charge(new BigDecimal(card.getSendMoney()))
					.reason("member.card.admin.send.card") 
					.build().insert();

		}
		if (isLimitCard(card)) {
			builder.count(card.getCount().shortValue()).reason("member.card.charge.money.reason").build().insert();
		}
		if (isLimitCard(card) && card.getIsExchang() != null) {
			builder.exchangCount(card.getExchangCount().shortValue()).reason("charge.money.reason.exchange").build()
					.insert();
		}

	}

	private UserCardRecord createNewUserCard(Integer userId, MemberCardRecord card, boolean isActivate) {
		UserCardRecordBuilder cardBuilder =
				UserCardRecordBuilder
				.create(db().newRecord(USER_CARD))
				.userId(userId)
				.cardId(card.getId())
				.cardNo(getRandomCardNo(card.getId()))
				.createTime(DateUtil.getLocalDateTime())
				.expireTime(calcCardExpireTime(card));

		if (isLimitCard(card)) {
			cardBuilder.surplus(card.getCount());
			if (card.getIsExchang() != null) {
				cardBuilder.exchangSurplus(card.getExchangCount());
			}
		} else if (isNormalCard(card)) {
			if (card.getSendMoney() != null) {
				cardBuilder.money(new BigDecimal(card.getSendMoney()));
			}
		} else if (isGradeCard(card) && isHasAvailableGradeCard(userId)) {
			logger().info("即将更改用户的等级");
			MemberCardRecord oldGradeCard = getUserGradeCard(userId);
			MemberCardRecord newGradeCard = memberCardService.getCardById(card.getId());
			changeUserGradeCard(userId, oldGradeCard, newGradeCard);
		}

		if (isActivate || isActivateNow(card)) {
			cardBuilder.activationTime(DateUtil.getLocalDateTime());
		}

		int result = 0;
		if(isGradeCard(card) && !isHasAvailableGradeCard(userId)) {
			logger().info("用户目前没有等级卡，设置一个等级卡: "+card.getGrade());
			result = cardBuilder.build().insert();
		}else {
			logger().info("发送普通或者限次卡：" + card.getCardName());
			result = cardBuilder.build().insert();
		}
		logger().info(String.format("成功向ID为%d的用户，发送了%d张会员卡", userId, result));
		return cardBuilder.build();
	}

	public boolean isNormalCard(MemberCardRecord card) {
		return memberCardService.isNormalCard(card.getCardType());
	}

	public boolean isLimitCard(MemberCardRecord card) {
		return memberCardService.isLimitCard(card.getCardType());
	}

	public boolean isGradeCard(MemberCardRecord card) {
		return memberCardService.isGradeCard(card.getCardType());
	}

	public boolean isActivateNow(MemberCardRecord card) {
		return MCARD_ACT_NO.equals(card.getActivation());
	}

	private Timestamp calcCardExpireTime(MemberCardRecord card) {
		assert card != null : "card should not be null";
		LocalDateTime expireTime = null;
		LocalDateTime now = LocalDateTime.now();
		if (isFixDate(card)) {
			return card.getEndTime();
		} else if (isDuringDay(card)) {
			expireTime = now.plusDays(card.getReceiveDay());
		} else if (isDuringWeek(card)) {
			expireTime = now.plusWeeks(card.getReceiveDay());
		} else if (isDuringMonth(card)) {
			expireTime = now.plusMonths(card.getReceiveDay());
		}
		return expireTime != null ? Timestamp.valueOf(expireTime) : null;
	}

	private boolean isDuring(MemberCardRecord card) {
		return MCARD_ET_DURING.equals(card.getExpireType());
	}

	private boolean isDuringDay(MemberCardRecord card) {
		return isDuring(card) && MCARD_DT_DAY.equals(card.getDateType());
	}

	private boolean isDuringWeek(MemberCardRecord card) {
		return isDuring(card) && MCARD_DT_WEEK.equals(card.getDateType());
	}

	private boolean isDuringMonth(MemberCardRecord card) {
		return isDuring(card) && MCARD_DT_MONTH.equals(card.getDateType());
	}

	private boolean isFixDate(MemberCardRecord card) {
		return MCARD_ET_FIX.equals(card.getExpireType());
	}

	/**
	 * 计算用户卡使用的时间段
	 */
	public List<Integer> useInDate() {
		LocalDate now = DateUtil.getLocalDate();

		DayOfWeek dayOfWeek = now.getDayOfWeek();
		List<Integer> inData = new ArrayList<>(Arrays.asList(new Integer[] { 0, 1 }));

		if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
			inData.clear();
			inData.addAll(Arrays.asList(new Integer[] { 0, 2 }));
		}

		return inData;
	}

	/**
	 *  更新会员卡详情
	 */
	public int updateUserCardByNo(String cardNo, UserCardRecord record) {
		return userCardDao.updateUserCardByNo(cardNo, record);
	}

	/**
	 * Get Random card_no
	 */
	public String getRandomCardNo(int cardId) {
		return memberCardService.generateCardNo(cardId);
	}

	public void cardConsumer(UserCardConsumeBean data) {
		cardConsumer(data, 0, TYPE_DEFAULT.val(), TRADE_FLOW_IN.val(), (byte) 0, true);
	}

	/**
	 * 增加会员卡消费记录
	 * @param tradeType  {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME}
	 * @param isContinue 卡余额时（次数或余额）在休息时间内（23:00-8:00）是否继续发送消息：true继续，false停止
	 */
	public int cardConsumer(UserCardConsumeBean data, Integer adminUser, Byte tradeType, Byte tradeFlow, Byte type,
			Boolean isContinue) {
		// 生成新的充值记录
		// 验证现有积分跟提交的积分是否一致
		UserCardParam userInfo = userCardDao.getUserCardInfo(data.getCardNo());
		if (data.getType() == (byte) 1) {
			if (type == (byte) 1) {
				if (adminUser != null && (userInfo.getExchangCount() - data.getCountDis()) != 0) {
					return -1;
				}
			} else {
				if (adminUser != null && (userInfo.getCount() - data.getCountDis()) != 0) {
					return -1;
				}
			}
		} else {
			if (adminUser != null
					&& BigDecimalUtil.subtrac(userInfo.getMoney(), data.getMoneyDis()).floatValue() != 0.00) {
				return -1;
			}
		}

		// serviceOrder

		data.setCreateTime(DateUtil.getLocalDateTime());
		if (StringUtils.isBlank(data.getReason())) {
			data.setReason(ADMIN_OPTION);
		}

		if (type == (byte) 1) {
			if (data.getType() == (byte) 1) {
				data.setReason(EXCHANGE_GOODS_NUM);
			} else {
				data.setReason(STORE_SERVICE_TIMES);
			}
		} else {
			data.setReason(MEMBER_MONEY);
		}

		if (data.getType() == (byte) 1) {
			if (type == (byte) 1) {
				if (data.getExchangCount() < 0) {
					// 消费记录
					userCardDao.insertConsume(data);
				} else {
					// 充值记录
					userCardDao.insertIntoCharge(data);
				}
			} else {
				if (data.getCount() < 0) {
					// 消费记录
					userCardDao.insertConsume(data);
				} else {
					// 充值记录
					userCardDao.insertIntoCharge(data);
				}
			}
		} else {
			if (data.getMoneyDis().intValue() < 0) {
				data.setMoney(data.getMoneyDis());
				userCardDao.insertConsume(data);
			} else {
				data.setCharge(data.getMoney());
				userCardDao.insertIntoCharge(data);
			}
		}

		// 更新用户卡余额
		// type=1 次卡 0普通卡
		int ret = 0;
		if (data.getType() == (byte) 1) {
			if (type == (byte) 1) {
				ret = userCardDao.updateUserCardExchangePlus(data, userInfo);
				// TODO 模板消息
			} else {
				ret = userCardDao.updateUserCardSurplus(data, userInfo);
				// TODO 模板消息
			}
		} else {
			ret = userCardDao.updateUserCardMoney(data, userInfo);
			if (tradeType > TYPE_DEFAULT.val()) {
				// 插入交易记录
				TradeOptParam param = TradeOptParam.builder().build();
				// TODO
				//tradesRecord.insertTradesRecord(data, tradeType, tradeFlow);
			}
			// TODO 模板消息
		}
		return ret;
	}

	/**
	 * 通过用户id，获取用户所有的会员卡列表
	 *
	 * @param userId
	 * @return
	 */
	public PageResult<WxAppUserCardVo> getAllCardsOfUser(SearchCardParam param) {
		PageResult<WxAppUserCardVo> cardList = userCardDao.getCardList(param);
		String avatar = getCardAvatar();
		for (WxAppUserCardVo card : cardList.dataList) {
			dealWithWxUserCard(card, avatar);
		}
		return cardList;
	}

	/**
	 * 处理返回给微信端的用户卡数据
	 *
	 * @param card
	 */
	private void dealWithWxUserCard(WxAppUserCardVo card, String avatar) {
		card.calcCardIsExpired();
		card.calcRenewal();
		card.calcUsageTime();
		card.setAvatar(avatar);
		card.calcCash();
	}

	/**
	 * 获取用户卡的头像
	 *
	 * @return
	 */
	public String getCardAvatar() {
		return saas().shop.getShopAvatarById(this.getShopId());
	}

	public WxAppUserCardVo getUserCardDetail(UserCardParam param) throws UserCardNullException {
		WxAppUserCardVo card = (WxAppUserCardVo) userCardDao.getUserCardInfo(param.getCardNo());
		if (card == null) {
			throw new UserCardNullException();
		}
		dealWithUserCardDetailInfo(card);

		// TODO 累计消费 等王帅的接口 
		card.setCumulativeScore(scoreService.getAccumulationScore(param.getUserId()));
		card.setCardVerifyStatus(cardVerifyService.getCardVerifyStatus(param.getCardNo()));

		// TODO 升级进度

		// TODO 开卡送卷
		return card;
	}

	public void dealWithUserCardDetailInfo(WxAppUserCardVo card) {
		logger().info("处理wx 用户会员卡数据详情");
		dealWithUserCardBasicInfo(card);
		dealWithUserCardAvailableStore(card);
		dealWithExchangGoods(card);

	}

	public void dealWithUserCardAvailableStore(WxAppUserCardVo card) {
		logger().info("正在处理会员卡门店列表信息");
		if (card.isStoreAvailable()) {
			List<StoreBasicVo> storeBasicVo = storeService.getStoreListByStoreIds(card.retrieveStoreList());
			card.setStoreInfoList(storeBasicVo);
		}
	}

	public void dealWithUserCardBasicInfo(WxAppUserCardVo card) {
		String avatar = getCardAvatar();
		dealWithWxUserCard(card, avatar);
	}

	/**
	 * 处理可兑换的商品
	 */
	public void dealWithExchangGoods(WxAppUserCardVo card) {

		if (card.hasAvailableExchangGoods()) {
			card.setGoodsList(getAvailGoodsForCard(card));
			// 两位小数
			if (card.getGoodsList() != null) {
				for (GoodsSmallVo good : card.getGoodsList()) {
					good.setShopPrice(good.getShopPrice().setScale(2));
				}
			}
		}
	}

	public List<GoodsSmallVo> getAvailGoodsForCard(WxAppUserCardVo card) {
		logger().info("正在获取可兑换的商品");
		// 获取兑换的商品id
		List<Integer> goodsIds = new ArrayList<Integer>();
		if (!StringUtils.isBlank(card.getExchangGoods())) {
			goodsIds = card.retrieveExchangGoods();
		} else {
			PageResult<GoodsPageListVo> pageList = goodsService.getPageList(new GoodsPageListParam());
			for (GoodsPageListVo goods : pageList.dataList) {
				goodsIds.add(goods.getGoodsId());
			}
		}
		return goodsService.getGoodsList(goodsIds, true);
	}

	/**
     * 王帅
	 * get card type
	 */
	public Byte getCardType(String cardNo) {
		if (StringUtil.isBlank(cardNo)) {
			return null;
		}
		return userCardDao.getCardType(cardNo);
	}

    /**
     * 王帅
     * get card type
     */
    public Byte getCardByCardNo(String cardNo) {
        if (StringUtil.isBlank(cardNo)) {
            return null;
        }
        return userCardDao.getCardType(cardNo);
    }

	/**
	 * 获取商品的等级会员价
	 *
	 * @param userId    用户
	 * @param prdIdList 规格ids
	 */
	public List<UserCardGradePriceBo> getUserCartGradePrice(Integer userId, List<Integer> prdIdList) {
		return userCardDao.getUserCartGradePrice(userId,prdIdList);
	}

    /**
     * 筛选会员专享商品
     * @param userId          用户id
     * @param cartGoodsList 需要筛选的商品
     * @return 反回会员卡绑定商品
     */
    public Set<Integer> getUserCardExclusiveGoodsIds(Integer userId, List<WxAppCartGoods> cartGoodsList) {
		cartGoodsList = cartGoodsList.stream().distinct().collect(Collectors.toList());
        Set<Integer> resGoodsIds = new HashSet<>();
        // 获取关联商品
        Map<Byte, List<Integer>> goodsCardCouple = goodsCardCoupleService.getGoodsCardCouple(userId);
		cartGoodsList.forEach(cartGoods -> {
			goodsCardCouple.forEach((k,v)->{
				if (v!=null){
					if (CardConstant.COUPLE_TP_GOODS.equals(k)){
						resGoodsIds.add(cartGoods.getGoodsId());
					}
					if (CardConstant.COUPLE_TP_STORE.equals(k)){
						resGoodsIds.add(cartGoods.getSortId());
					}
					if (CardConstant.COUPLE_TP_PLAT.equals(k)){
						resGoodsIds.add(cartGoods.getCartId());
					}
					if (CardConstant.COUPLE_TP_BRAND.equals(k)){
						resGoodsIds.add(cartGoods.getBrandId());
					}
				}
			});
        });
        return resGoodsIds;

    }


	/**
	 * 根据id获得具有卡的数量
	 */
	public int getNumCardsWithSameId(Integer cardId) {
		return userCardDao.calcNumCardById(cardId);
	}

    /**
     * 王帅
     * 得到订单下的用户可用会员卡
     * @param userId
     * @param bos
     * @param storeId
     * @param defaultCards
     * @return cards
     */
	public List<OrderMemberVo> getValidCardList(Integer userId, List<OrderGoodsBo> bos, Integer storeId, List<OrderMemberVo> defaultCards) {
        List<OrderMemberVo> cards = new ArrayList<OrderMemberVo>();
        if(CollectionUtils.isEmpty(defaultCards)){
	        //初始化
            int _ = 1;
            defaultCards = userCardDao.getOrderMembers(userId, new Byte[]{CardConstant.MCARD_TP_NORMAL, CardConstant.MCARD_TP_GRADE}, OrderConstant.MEMBER_CARD_ONLINE);
        }
        if(CollectionUtils.isEmpty(defaultCards)){
            //校验
            return Lists.newArrayList();
        }
        for (Iterator<OrderMemberVo> iterator = defaultCards.iterator(); iterator.hasNext() ; ) {
            OrderMemberVo card = iterator.next();
            cards.add(card);
            //当前会员卡适用商品
            BigDecimal[] tolalNumberAndPrice = calculate.getTolalNumberAndPriceByType(bos, OrderConstant.D_T_MEMBER_CARD, DefaultMarketingProcess.builder().card(card).type(OrderConstant.D_T_MEMBER_CARD).build());
            //折扣金额
            BigDecimal discountAmount = null;
            //判断门店（无门店||全部门店||部分门店）
            if(storeId == null || CardConstant.MCARD_STP_ALL.equals(Byte.valueOf(card.getStoreList())) ||
                (CardConstant.MCARD_SUSE_OK.equals(card.getStoreUseSwitch()) && Arrays.asList(card.getStoreList().split(",")).contains(storeId.toString()))) {
                //折扣金额
                discountAmount = getDiscountAmount(card, tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
            }else{
                iterator.remove();
                //TODO 删除判断
                continue;
            }
            card.setBos(bos);
            card.setTotalPrice(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
            card.setTotalGoodsNumber(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER]);
            card.setTotalDiscount(discountAmount);
            card.setIdentity(card.getCardNo());
        }
        return cards;
    }
	
	

    /**
     * 王帅
     * 校验该商品是否可以打折
     * @param cardId 卡
     * @param bo 商品
     * @return boolean
     */
    public boolean checkGoodsDiscount(Integer cardId, OrderGoodsBo bo){
        MemberCardRecord card = userCardDao.getMemberCardById(cardId);
        if(card == null){
            return false;
        }
        if(CardConstant.MCARD_DIS_ALL.equals(card.getDiscountIsAll())){
            return true;
        }
        if(StringUtil.isNotBlank(card.getDiscountGoodsId())){
            //商品id
            return Arrays.asList(card.getDiscountGoodsId().split(",")).contains(bo.getGoodsId());
        }
        if(StringUtil.isNotBlank(card.getDiscountCatId())){
            //平台分类id
            return Arrays.asList(card.getDiscountCatId().split(",")).contains(bo.getCatId());
        }
        if(StringUtil.isNotBlank(card.getDiscountSortId())){
            //商家分类id
            return Arrays.asList(card.getDiscountSortId().split(",")).contains(bo.getSortId());
        }
        if(StringUtil.isNotBlank(card.getDiscountBrandId())){
            //商品品牌id
            return Arrays.asList(card.getDiscountBrandId().split(",")).contains(bo.getBrandId());
        }
        return false;
    }

    /**
     * 王帅
     * 获取该卡打折金额
     * @param card 会员卡
     * @param totalPrice 折前总价
     * @return 折后总价
     */
    public BigDecimal getDiscountAmount(OrderMemberVo card, BigDecimal totalPrice){
        if(CardConstant.MCARD_TP_LIMIT.equals(card.getCardType())){
            //限次卡
            card.setTotalDiscount(totalPrice);
        }else if(BigDecimalUtil.compareTo(card.getDiscount(), BigDecimal.ZERO) == -1){
            //如果没有打折权益则为十折
            card.setTotalDiscount(BigDecimal.ZERO);
        }else{
            //正常打折(价格 * （10 - 折扣（eg:6.66） / 10）)
            card.setTotalDiscount(
                BigDecimalUtil.multiplyOrDivide(
                    BigDecimalUtil.BigDecimalPlus.create(totalPrice, BigDecimalUtil.Operator.multiply),
                    BigDecimalUtil.BigDecimalPlus.create(BigDecimalUtil.addOrSubtrac(BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, BigDecimalUtil.Operator.subtrac), BigDecimalUtil.BigDecimalPlus.create(card.getDiscount(), null)), BigDecimalUtil.Operator.Divide),
                    BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, null))
                );
        }
        return card.getTotalDiscount();
    }

    /**
     * 获取用户累积消费总额
     * @return 消费总额,默认为0
     */
    private BigDecimal getUserTotalSpendAmount(Integer userId) {
    	DistributorSpendVo distributorSpendVo = distributorLevelService.getTotalSpend(userId);
    	return distributorSpendVo.getTotal()!=null?distributorSpendVo.getTotal():BigDecimal.ZERO;
    }
    
    

    /**
     * 王帅
     * get card
     */
    public UserCardParam getCard(String cardNo) {
        if (StringUtil.isBlank(cardNo)) {
            return null;
        }
        return userCardDao.getUserCardInfo(cardNo);
    }
    
    /**
	 * 获取持有会员卡的用户id
	 */
	public List<Integer> getUserIdThatHasValidCard() {
		return userCardDao.getUserIdThatHasValidCard();
	}
    

}
