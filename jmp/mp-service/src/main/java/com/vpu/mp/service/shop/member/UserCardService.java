package com.vpu.mp.service.shop.member;

import com.google.common.collect.Lists;
import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.db.shop.tables.records.VirtualOrderRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.foundation.util.CardUtil;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.distribution.DistributorSpendVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListParam;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsPageListVo;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsSmallVo;
import com.vpu.mp.service.pojo.shop.market.couponpack.CouponPackUpdateVo;
import com.vpu.mp.service.pojo.shop.member.account.CardReceiveVo;
import com.vpu.mp.service.pojo.shop.member.account.GradeCardData;
import com.vpu.mp.service.pojo.shop.member.account.NextGradeCardVo;
import com.vpu.mp.service.pojo.shop.member.account.UserCardCoupon;
import com.vpu.mp.service.pojo.shop.member.account.UserCardCouponPack;
import com.vpu.mp.service.pojo.shop.member.account.UserCardJudgeVo;
import com.vpu.mp.service.pojo.shop.member.account.UserCardParam;
import com.vpu.mp.service.pojo.shop.member.account.UserCardVo;
import com.vpu.mp.service.pojo.shop.member.account.UserIdAndCardIdParam;
import com.vpu.mp.service.pojo.shop.member.account.WxAppCardExamineVo;
import com.vpu.mp.service.pojo.shop.member.account.WxAppUserCardVo;
import com.vpu.mp.service.pojo.shop.member.bo.UserCardGradePriceBo;
import com.vpu.mp.service.pojo.shop.member.builder.ChargeMoneyRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.MemberCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardParamBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserCardRecordBuilder;
import com.vpu.mp.service.pojo.shop.member.builder.UserScoreVoBuilder;
import com.vpu.mp.service.pojo.shop.member.card.CardBgBean;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.EffectTimeBean;
import com.vpu.mp.service.pojo.shop.member.card.EffectTimeParam;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.pojo.shop.member.card.RankCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;
import com.vpu.mp.service.pojo.shop.member.exception.CardReceiveFailException;
import com.vpu.mp.service.pojo.shop.member.exception.CardSendRepeatException;
import com.vpu.mp.service.pojo.shop.member.exception.LimitCardAvailSendNoneException;
import com.vpu.mp.service.pojo.shop.member.exception.MemberCardNullException;
import com.vpu.mp.service.pojo.shop.member.exception.UserCardNullException;
import com.vpu.mp.service.pojo.shop.member.score.UserScoreVo;
import com.vpu.mp.service.pojo.shop.member.ucard.DefaultCardParam;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.process.DefaultMarketingProcess;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.distribution.DistributorLevelService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.couponpack.CouponPackService;
import com.vpu.mp.service.shop.member.dao.CardDaoService;
import com.vpu.mp.service.shop.member.dao.UserCardDaoService;
import com.vpu.mp.service.shop.order.action.base.Calculate;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.trade.TradesRecordService;
import com.vpu.mp.service.shop.order.virtual.VirtualOrderService;
import com.vpu.mp.service.shop.store.store.StoreService;
import jodd.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
=======
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> bugfix-2.7-1003441

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
<<<<<<< HEAD
=======
import java.util.Collections;
>>>>>>> bugfix-2.7-1003441
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.CHARGE_MONEY;
import static com.vpu.mp.db.shop.Tables.MEMBER_CARD;
import static com.vpu.mp.db.shop.Tables.USER_CARD;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.LOWEST_GRADE;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ACT_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_DAY;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_MONTH;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DT_WEEK;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_DURING;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_ET_FIX;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_TP_LIMIT;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.UCARD_ACT_NO;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_IN;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TYPE_DEFAULT;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TYPE_SCORE_CREATE_CARD;

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
	@Autowired
	private QrCodeService qrCodeService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private CouponGiveService couponGiveService;
	@Autowired
	private ShopCommonConfigService shopCommonConfigService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponPackService couponPackService;
	@Autowired
	private VirtualOrderService virtualOrderService;
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
	public static final String DEFAULT_ADMIN = "0";

	public static final String DESC = "score_open_card";

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
	private void changeUserGradeCard(Integer userId, MemberCardRecord oldCard, MemberCardRecord newCard,
			String option) {
		logger().info("用户会员卡升级");
		updateUserGradeCardId(userId, newCard.getId());
		saveUpdateGradeRecord(userId, oldCard, newCard, option);
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
	private void saveUpdateGradeRecord(Integer userId, MemberCardRecord oldCard, MemberCardRecord newCard,
			String option) {
		cardUpgradeService.recordCardUpdateGrade(userId, oldCard, newCard, option);
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
				.scoreDis(memberService.getUserScore(userId)).desc("score_open_card").remarkCode(RemarkTemplate.CARD_UPGRADE.code)
				.expireTime(scoreService.getScoreExpireTime()).shopId(getShopId()).build();

		scoreService.addUserScore(userScore, DEFAULT_ADMIN, TYPE_SCORE_CREATE_CARD.val(), TRADE_FLOW_IN.val());
	}

	/**
	 * 会员卡升级检测并升级
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @param type 是否领取 1领取 0只是检测
	 * @return cardId（当type为0时为检测可升级的卡id,type为1时为领取后的卡id),0为没有可升级的卡
	 */
	public Integer updateGrade(Integer userId, Integer cardId, Byte type) throws MpException {
<<<<<<< HEAD
		Assert.isTrue(userId != null, "userId required");
=======
		assert userId != null : "userId required";
>>>>>>> bugfix-2.7-1003441

		if (cardId != null) {
			// 直接升级
			updateUserGradeCard(userId, cardId);
			return 0;
		} else {
			if (TP_RECEIVE_ONE.equals(type) || TP_RECEIVE_TWO.equals(type)) {
				// 检测并升级
				cardId = checkAndUpgradeUserCard(userId);
			} else if (TP_CHECK.equals(type)) {
				// 检测可升级到的卡
				cardId = checkCardCanUpgrade(userId);
			}
			if (cardId == null) {
				logger().info("没有可升级的等级会员卡");
				return 0;
			} else if (type == 0) {
				logger().info(String.format("检测到可领取等级卡 %d", cardId));
				// 仅仅检测是否可领取等级卡
				return cardId;
			}else {
				return cardId;
			}
<<<<<<< HEAD

=======
			
>>>>>>> bugfix-2.7-1003441
		}
	}

	private Integer checkAndUpgradeUserCard(Integer userId) throws MpException {
		Integer cardId = null;
		// 获取用户累积获得积分和累积消费总额
		Integer userTotalScore = scoreService.getAccumulationScore(userId);
		BigDecimal amount = getUserTotalSpendAmount(userId);
<<<<<<< HEAD

=======
		
>>>>>>> bugfix-2.7-1003441
		// 获取等级卡列表等级升序
		List<MemberCardRecord> gCardList = getAvailGradeCard();

		String uGrade = userCardDao.getUserCardGrade(userId);
		// 判断用户是否拥有等级卡
		if (StringUtils.isBlank(uGrade)) {
			// 用户第一次领取会员卡，给用户分配一级会员卡
			MemberCardRecord gCard = gCardList.get(0);
			logger().info("给用户分配等级卡: " + gCard.getCardName() + "等级: " + gCard.getGrade());
			// 升级条件
			GradeConditionJson gradeCondition = getGradeCondition(userTotalScore, amount, gCard);
			if (isSatisfyUpgradeCondition(userTotalScore, amount, gradeCondition)) {
				addUserCard(userId, gCard.getId());
			}
			uGrade = userCardDao.getUserCardGrade(userId);
		}



		for (MemberCardRecord gCard : gCardList) {
			if (!StringUtils.isBlank(gCard.getGradeCondition())) {
				// 升级条件
				GradeConditionJson gradeCondition = getGradeCondition(userTotalScore, amount, gCard);
				// 等级卡的等级高于用户卡等级或者用户目前等级为空
				if (isCardGradeGtUserGrade(uGrade, gCard)) {
					if (isSatisfyUpgradeCondition(userTotalScore, amount, gradeCondition)) {
						cardId = gCard.getId();
						MemberCardRecord oldGradeCard = getUserGradeCard(userId);
						String operation = "admin option";
						changeUserGradeCard(userId, oldGradeCard, gCard, operation);
					} else {
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
		if (!StringUtils.isBlank(uGrade)) {
			List<MemberCardRecord> gradeCard = getAvailGradeCard();
			gradeCard.removeIf(item->item.getGrade().compareTo(uGrade)<1);
			Integer userTotalScore = scoreService.getAccumulationScore(userId);
			BigDecimal amount = distributorLevelService.getTotalSpend(userId).getTotal();

			for (MemberCardRecord gCard : gradeCard) {
				GradeConditionJson gradeCondition = getGradeCondition(userTotalScore, amount, gCard);

				if (isSatisfyUpgradeCondition(userTotalScore, amount, gradeCondition)) {
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

		GradeConditionJson gradeCondition = Util.parseJson(gCard.getGradeCondition(), GradeConditionJson.class);

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
			String option = "Admin operation";
			changeUserGradeCard(userId, oldGradeCard, newGradeCard, option);
		} else {
			// 发放等级卡
			sendCard(userId, cardId);
		}
	}

	public List<String> addUserCard(Integer userId, Integer... cardId) throws MpException {
		List<UserCardParam> cardList = new ArrayList<>();
		for (Integer id : cardId) {
			cardList.add(UserCardParamBuilder.create().cardId(id).build());
		}
		return addUserCard(userId, cardList, UCARD_ACT_NO);
	}

	/**
	 * 添加会员卡
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @return
	 */
	public List<String> addUserCard(Integer userId, List<UserCardParam> cardList, boolean isActivate)
			throws MpException {

		stopUserLimitCard(cardList);
		List<String> cardNoList = new ArrayList<>();
		for (UserCardParam card : cardList) {
			MemberCardRecord mCard = cardDao.getCardById(card.getCardId());
			if (isLimitCard(mCard)) {
				if (canSendLimitCard(userId, mCard)) {
					String cardNo = sendCard(userId, mCard, isActivate);
					cardNoList.add(cardNo);
				} else {
					logger().info("该限次卡领取次数用完");
					throw new LimitCardAvailSendNoneException();
				}
			} else if (StringUtils.isBlank(card.getCardNo())) {
				String cardNo = sendCard(userId, mCard, isActivate);
				cardNoList.add(cardNo);
			} else {
				logger().info("一张卡不能重复领取，除非此卡过期或删除");
				throw new CardSendRepeatException();
			}
		}
		return cardNoList;
	}

	/**
	 * 检测限次卡是否能发放
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @return true: 能,false：不可以
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
	public String sendCard(Integer userId, MemberCardRecord card, boolean isActivate) throws MemberCardNullException {
		logger().info("给用户发送会员卡");
		if (CardUtil.isCardDeleted(card.getDelFlag())) {
			throw new MemberCardNullException();
		}
		UserCardRecord newCard = createNewUserCard(userId, card, isActivate);

		addChargeMoney(card, newCard);
		addUserCardScore(userId, card);
		sendScoreTemplateMsg(card);
		return newCard.getCardNo();
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
		ChargeMoneyRecordBuilder builder = ChargeMoneyRecordBuilder.create(db().newRecord(CHARGE_MONEY))
				.userId(userCard.getUserId()).cardId(userCard.getCardId()).type(card.getCardType())
				.cardNo(userCard.getCardNo()).payment("store.payment").createTime(DateUtil.getLocalDateTime());
<<<<<<< HEAD

=======
		
>>>>>>> bugfix-2.7-1003441
		// TODO 门店支付国际化
		if (isNormalCard(card) && card.getSendMoney() != null) {
			//  管理员发卡
			builder.charge(new BigDecimal(card.getSendMoney())).reasonId(String.valueOf(RemarkTemplate.ADMIN_SEND_CARD.code)).build().insert();

		}
		if (isLimitCard(card)) {
			// 管理员发卡 - 门店服务次数
			builder.count(card.getCount().shortValue()).reasonId(String.valueOf(RemarkTemplate.SEND_CARD_REASON.code)).build().insert();
		}
		if (isLimitCard(card) && card.getIsExchang() != null) {
			// 管理员发卡 - 兑换商品数量
			builder.exchangCount(card.getExchangCount().shortValue()).reasonId(String.valueOf(RemarkTemplate.ADMIN_EXCHANGE_GOODS.code)).build()
					.insert();
		}

	}

	private UserCardRecord createNewUserCard(Integer userId, MemberCardRecord card, boolean isActivate) {
		UserCardRecordBuilder cardBuilder = UserCardRecordBuilder.create(db().newRecord(USER_CARD)).userId(userId)
				.cardId(card.getId()).cardNo(getRandomCardNo(card.getId())).createTime(DateUtil.getLocalDateTime())
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
			String option = "Admin operation";
			changeUserGradeCard(userId, oldGradeCard, newGradeCard, option);
		}

		if (isActivate || isActivateNow(card)) {
			cardBuilder.activationTime(DateUtil.getLocalDateTime());
		}

		int result = 0;
		if (isGradeCard(card) && !isHasAvailableGradeCard(userId)) {
			logger().info("用户目前没有等级卡，设置一个等级卡: " + card.getGrade());
			result = cardBuilder.build().insert();
		} else {
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
<<<<<<< HEAD
		Assert.isTrue(card != null,"card should not be null");
=======
		assert card != null : "card should not be null";
>>>>>>> bugfix-2.7-1003441
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
	 * 更新会员卡详情
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
	 * 	增加会员卡消费记录
	 * @param tradeType  {@link com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_INCOME}
	 * @param isContinue 卡余额时（次数或余额）在休息时间内（23:00-8:00）是否继续发送消息：true继续，false停止
	 * @param type 修改类型 0: 卡余额；1：兑换商品次数，2： 兑换门店次数
	 */
	public int cardConsumer(UserCardConsumeBean data, Integer adminUser, Byte tradeType, Byte tradeFlow, Byte type,
			Boolean isContinue) {
		// 生成新的充值记录
		// 验证现有积分跟提交的积分是否一致
		UserCardParam userInfo = userCardDao.getUserCardInfo(data.getCardNo());
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
		if (CardUtil.isLimitCard(data.getType())) {
			// 限次卡
			if (NumberUtils.BYTE_ONE.equals(type)) {
				if (adminUser != null && (userInfo.getExchangCount() - data.getCountDis()) != 0) {
					return -1;
				}
			} else {
				if (adminUser != null && (userInfo.getCount() - data.getCountDis()) != 0) {
					return -1;
				}
			}
		} else {
			// 普通卡
			if (adminUser != null
					&& BigDecimalUtil.subtrac(userInfo.getMoney(), data.getMoneyDis()).floatValue() != 0.00) {
				return -1;
			}
		}

		// serviceOrder

		data.setCreateTime(DateUtil.getLocalDateTime());
		if (StringUtils.isBlank(data.getReason())) {
			data.setReasonId(String.valueOf(RemarkTemplate.ADMIN_OPERATION.code));
		}
<<<<<<< HEAD

=======
		
>>>>>>> bugfix-2.7-1003441
		if (CardUtil.isLimitCard(data.getType())) {
			if (NumberUtils.BYTE_ONE.equals(type)) {
				// 兑换商品次数
				data.setReasonId(String.valueOf(RemarkTemplate.ADMIN_EXCHANGE_GOODS.code));
			} else {
				// 兑换门店次数
				data.setReasonId(String.valueOf(RemarkTemplate.ADMIN_STORE_SERIVICE.code));
			}
		} else {
			// 卡余额变动
			data.setReasonId(String.valueOf(RemarkTemplate.ADMIN_CARD_ACCOUNT.code));
		}

		if (CardUtil.isLimitCard(data.getType())) {
			if (NumberUtils.BYTE_ONE.equals(type)) {
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
			if (data.getMoney().intValue() < 0) {
				data.setMoney(data.getMoney().abs());
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
				// tradesRecord.insertTradesRecord(data, tradeType, tradeFlow);
			}
			// TODO 模板消息
		}
		return ret;
	}

	/**
	 * 获取用户所有的会员卡列表
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
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
<<<<<<< HEAD
		card.calcUsageTime();
		card.setAvatar(avatar);
		card.calcCash();
		// 背景图
		if(CardUtil.isBgImgType(card.getBgType())) {
			// 全路径
			if(!StringUtils.isBlank(card.getBgImg())) {
				card.setBgImg(saas().getShopApp(getShopId()).image.imageUrl(card.getBgImg()));
			}
		}else {
			if(StringUtils.isBlank(card.getBgColor())) {
				// 默认背景色
				card.setBgColor(CardUtil.getDefaultBgColor());
			}
		}
=======
		card.setAvatar(avatar);
		card.calcCash();
		
		// 背景
		CardBgBean bg = memberCardService.getBackground(card.getBgType(), card.getBgColor(), card.getBgImg());
		BeanUtils.copyProperties(bg, card);

		// 用户卡的有效时间
		EffectTimeParam etParam = new EffectTimeParam();
		BeanUtils.copyProperties(card, etParam);
		etParam.setCreateTime(card.getUserCardCreateTime());
		EffectTimeBean etBean = CardUtil.getUserCardEffectTime(etParam);
		BeanUtils.copyProperties(etBean, card);
		
		// 设置卡是否过期状态
		card.setStatus(CardUtil.getStatus(card.getExpireType(), card.getEndTime()));
>>>>>>> bugfix-2.7-1003441
	}

	/**
	 * 获取用户卡的头像
	 *
	 * @return
	 */
	public String getCardAvatar() {
		String relativePath = saas().shop.getShopAvatarById(this.getShopId());
		if(StringUtils.isBlank(relativePath)) {
			return null;
		}else {
			return saas().getShopApp(getShopId()).image.imageUrl(relativePath);
		}
	}

	public WxAppUserCardVo getUserCardDetail(UserCardParam param) throws UserCardNullException {
		WxAppUserCardVo card = null;
		if(param.getCardId() != null) {
			card = (WxAppUserCardVo) userCardDao.getUserCardInfo(param.getUserId(),param.getCardId());
		}else {
			card = (WxAppUserCardVo) userCardDao.getUserCardInfo(param.getCardNo());
		}
<<<<<<< HEAD

=======
		
>>>>>>> bugfix-2.7-1003441
		if (card == null) {
			throw new UserCardNullException();
		}

<<<<<<< HEAD
		// 背景图片
		if(CardUtil.isBgImgType(card.getBgType())) {
			if(!StringUtils.isBlank(card.getBgImg())) {
				String imageUrl = saas.getShopApp(getShopId()).image.imageUrl(card.getBgImg());
				card.setBgImg(imageUrl);
			}
		}else {
			// 背景色
			if(StringUtils.isBlank(card.getBgColor())) {
				// 默认背景色
				card.setBgColor(CardUtil.getDefaultBgColor());
			}
		}

		if(card.getExpireTime()!=null) {
			card.setStartDate(card.getStartTime().toLocalDateTime().toLocalDate());
			card.setEndDate(card.getEndTime().toLocalDateTime().toLocalDate());
			card.setExpireType(NumberUtils.BYTE_ZERO);
		}else {
			card.setExpireType(CardConstant.MCARD_ET_FOREVER);
		}
		if (!CardUtil.isCardTimeForever(card.getExpireType())) {
			if (CardUtil.isCardFixTime(card.getExpireType()) && CardUtil.isCardExpired(card.getEndTime())) {
				logger().info("卡过期");
				card.setStatus(-1);
			} else {
				card.setStatus(1);
			}

			if (CardUtil.isCardFixTime(card.getExpireType())) {
				card.setStartDate(card.getStartTime().toLocalDateTime().toLocalDate());
				card.setEndDate(card.getEndTime().toLocalDateTime().toLocalDate());
			}
		} else {
			card.setStatus(1);
		}


		dealWithUserCardDetailInfo(card);
		card.setCumulativeConsumptionAmounts(orderInfoService.getAllConsumpAmount(param.getUserId()));
		card.setCumulativeScore(scoreService.getAccumulationScore(param.getUserId()));
		card.setCardVerifyStatus(cardVerifyService.getCardVerifyStatus(param.getCardNo()));
=======
		dealWithUserCardDetailInfo(card);
		
		card.setCumulativeConsumptionAmounts(orderInfoService.getAllConsumpAmount(param.getUserId()));
		card.setCumulativeScore(scoreService.getAccumulationScore(param.getUserId()));
		card.setCardVerifyStatus(cardVerifyService.getCardVerifyStatus(param.getCardNo()));
		
>>>>>>> bugfix-2.7-1003441
		logger().info("卡的校验状态");
		CardExamineRecord  cardExamine = cardVerifyService.getStatusByNo(param.getCardNo());
		if(cardExamine != null) {
			WxAppCardExamineVo cardExamineVo = new WxAppCardExamineVo();
			cardExamineVo.setPassTime(cardExamine.getPassTime());
			cardExamineVo.setRefuseTime(cardExamine.getRefuseTime());
			cardExamineVo.setRefuseDesc(cardExamine.getRefuseDesc());
			cardExamineVo.setStatus(cardExamine.getStatus());
			card.setIsExamine(cardExamineVo);
		}
<<<<<<< HEAD

=======
		
>>>>>>> bugfix-2.7-1003441
		// TODO 开卡送卷
		setQrCode(card);

		if (CardUtil.isGradeCard(card.getCardType())) {

			NextGradeCardVo nextGradeCard = getNextGradeCard(card.getGrade());
			card.setNextGradeCard(nextGradeCard);
		}

		return card;
	}

	private NextGradeCardVo getNextGradeCard(String currentGrade) {
<<<<<<< HEAD
		// 升级进度
=======
		// 升级进度 
>>>>>>> bugfix-2.7-1003441
		logger().info("当前会员卡的等级：" + currentGrade);

		Integer gVal = Integer.valueOf(currentGrade.substring(1));
		gVal = gVal + 1;
		while (gVal < 10) {
			logger().info("设置会员升级的下一张等级卡");
			String newGrade = "v" + gVal;
			MemberCardRecord gradeCard = getGradeCardByGrade(newGrade);
			if(gradeCard==null) {
				gVal = gVal+1;
				continue;
			}
			RankCardToVo resCard = memberCardService.changeToGradeCardDetail(gradeCard);
			NextGradeCardVo vo = new NextGradeCardVo();
			vo.setCardName(resCard.getCardName());
			vo.setPowerCount(resCard.getPowerCount());
			vo.setDiscount(resCard.getDiscount());
			vo.setSorce(resCard.getSorce());
			vo.setScoreJson(resCard.getScoreJson());
			vo.setGrade(resCard.getGrade());
			vo.setPowerScore(resCard.getPowerScore());
			vo.setGradeConditionJson(resCard.getGradeConditionJson());
			return vo;
		}
		return null;
	}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441

	public MemberCardRecord getGradeCardByGrade(String grade) {
		return db().selectFrom(MEMBER_CARD).where(MEMBER_CARD.GRADE.eq(grade)).fetchAny();
	}

	private void setQrCode(WxAppUserCardVo card) {
		MemberCardRecord mCard = memberCardService.getCardById(card.getCardId());
		String qrCode = qrCodeService.getUserCardQrCode(card.getCardNo(), mCard);
		card.setQrCode(qrCode);
	}

	public void dealWithUserCardDetailInfo(WxAppUserCardVo card) {
		logger().info("处理wx 用户会员卡数据详情");
		dealWithUserCardBasicInfo(card);
		dealWithUserCardAvailableStore(card);
		dealWithExchangGoods(card);
	}

	public void dealWithUserCardAvailableStore(WxAppUserCardVo card) {
		logger().info("正在处理会员卡门店列表信息");
<<<<<<< HEAD
		if (card.isStoreAvailable()) {
			List<StoreBasicVo> storeBasicVo = storeService.getStoreListByStoreIds(card.retrieveStoreList());
			card.setStoreInfoList(storeBasicVo);
=======
		card.setStoreInfoList(Collections.emptyList());
		card.setStoreIdList(Collections.emptyList());
		
		
		if (card.isStoreAvailable()) {
			List<Integer> storeIdList = card.retrieveStoreList();
			if(storeIdList != null && storeIdList.size()>0 && storeIdList.get(0) != 0) {
				// 部分门店
				card.setStoreIdList(card.retrieveStoreList());
				List<StoreBasicVo> storeBasicVo = storeService.getStoreListByStoreIds(card.retrieveStoreList());
				card.setStoreInfoList(storeBasicVo);
				card.setStoreUseSwitch(CardConstant.MCARD_STP_PART);
			}else {
				// 全部门店
				card.setStoreUseSwitch(CardConstant.MCARD_STP_ALL);
			}
		}else {
			// 不可在门店使用
			card.setStoreUseSwitch(CardConstant.MCARD_STP_BAN);
>>>>>>> bugfix-2.7-1003441
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
	 * 王帅 get card type
	 */
	public Byte getCardType(String cardNo) {
		if (StringUtil.isBlank(cardNo)) {
			return null;
		}
		return userCardDao.getCardType(cardNo);
	}

	/**
	 * 王帅 get card type
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
		return userCardDao.getUserCartGradePrice(userId, prdIdList);
	}

	/**
	 * 获取商品的等级会员价
	 *
	 * @param grade     等级
	 * @param prdIdList 规格ids
	 */
	public List<UserCardGradePriceBo> getUserCartGradePrice(String grade, List<Integer> prdIdList) {
		return userCardDao.getUserCartGradePrice(grade, prdIdList);
	}

	/**
	 * 筛选会员专享商品
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @param userId        用户id
	 * @param cartGoodsList 需要筛选的商品
	 * @return 反回会员卡绑定商品
	 */
	public Set<Integer> getUserCardExclusiveGoodsIds(Integer userId, List<WxAppCartGoods> cartGoodsList) {
		cartGoodsList = cartGoodsList.stream().distinct().collect(Collectors.toList());
		Set<Integer> resGoodsIds = new HashSet<>();
		// 获取关联商品
		Map<Byte, List<Integer>> goodsCardCouple = goodsCardCoupleService.getGoodsCardCouple(userId);
		cartGoodsList.forEach(cartGoods -> {
			goodsCardCouple.forEach((k, v) -> {
				if (v != null) {
					if (CardConstant.COUPLE_TP_GOODS.equals(k)) {
						resGoodsIds.add(cartGoods.getGoodsId());
					}
					if (CardConstant.COUPLE_TP_STORE.equals(k)) {
<<<<<<< HEAD
						resGoodsIds.add(cartGoods.getGoodsRecord().getSortId());
=======
						resGoodsIds.add(cartGoods.getSortId());
>>>>>>> bugfix-2.7-1003441
					}
					if (CardConstant.COUPLE_TP_PLAT.equals(k)) {
						resGoodsIds.add(cartGoods.getCartId());
					}
					if (CardConstant.COUPLE_TP_BRAND.equals(k)) {
<<<<<<< HEAD
						resGoodsIds.add(cartGoods.getGoodsRecord().getBrandId());
=======
						resGoodsIds.add(cartGoods.getBrandId());
>>>>>>> bugfix-2.7-1003441
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
	 * 王帅 得到订单下的用户可用会员卡
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @param userId
	 * @param bos
	 * @param storeId
	 * @param defaultCards
	 * @return cards
	 */
	public List<OrderMemberVo> getValidCardList(Integer userId, List<OrderGoodsBo> bos, Integer storeId,
			List<OrderMemberVo> defaultCards) {
		List<OrderMemberVo> cards = new ArrayList<OrderMemberVo>();
		if (CollectionUtils.isEmpty(defaultCards)) {
			// 初始化
			defaultCards = userCardDao.getOrderMembers(userId,
					new Byte[] { CardConstant.MCARD_TP_NORMAL, CardConstant.MCARD_TP_GRADE },
					OrderConstant.MEMBER_CARD_ONLINE);
<<<<<<< HEAD
		}else {
            List<OrderMemberVo> temp = userCardDao.getOrderMembers(userId,
                new Byte[]{CardConstant.MCARD_TP_NORMAL, CardConstant.MCARD_TP_GRADE},
                OrderConstant.MEMBER_CARD_ONLINE);
            for (OrderMemberVo orderMemberVo : temp) {
               if(!defaultCards.get(0).getCardId().equals(orderMemberVo.getCardId())) {
                   defaultCards.add(orderMemberVo);
               }
            }
        }
=======
		}
>>>>>>> bugfix-2.7-1003441
		if (CollectionUtils.isEmpty(defaultCards)) {
			// 校验
			return Lists.newArrayList();
		}
		for (Iterator<OrderMemberVo> iterator = defaultCards.iterator(); iterator.hasNext();) {
			OrderMemberVo card = iterator.next();
			cards.add(card);
			// 当前会员卡适用商品
			BigDecimal[] tolalNumberAndPrice = calculate.getTolalNumberAndPriceByType(bos,
					OrderConstant.D_T_MEMBER_CARD,
					DefaultMarketingProcess.builder().card(card).type(OrderConstant.D_T_MEMBER_CARD).build());
<<<<<<< HEAD
=======
			if(CollectionUtils.isEmpty(card.getBos())) {
			    iterator.remove();
			    continue;
            }
>>>>>>> bugfix-2.7-1003441
			// 折扣金额
			BigDecimal discountAmount;
			// 判断门店（无门店||全部门店||部分门店）
			if (storeId == null || CardConstant.MCARD_STP_ALL.equals(Byte.valueOf(card.getStoreList()))
					|| (CardConstant.MCARD_SUSE_OK.equals(card.getStoreUseSwitch())
							&& Arrays.asList(card.getStoreList().split(",")).contains(storeId.toString()))) {
				// 折扣金额
				discountAmount = getDiscountAmount(card, tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
			} else {
				iterator.remove();
				// TODO 删除判断
				continue;
			}
			card.setTotalPrice(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_PRICE]);
			card.setTotalGoodsNumber(tolalNumberAndPrice[Calculate.BY_TYPE_TOLAL_NUMBER]);
			card.setTotalDiscount(discountAmount);
			card.setIdentity(card.getCardNo());
			card.initRatio();
		}
		return cards;
	}

	/**
	 * 王帅 校验该商品是否可以打折
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @param cardId 卡
	 * @param bo     商品
	 * @return boolean
	 */
	public boolean isContainsProduct(Integer cardId, OrderGoodsBo bo) {
		MemberCardRecord card = userCardDao.getMemberCardById(cardId);
		if (card == null) {
			return false;
		}
		if (CardConstant.MCARD_DIS_ALL.equals(card.getDiscountIsAll())) {
			return true;
		}
<<<<<<< HEAD
        if (StringUtil.isNotBlank(card.getDiscountGoodsId()) && Arrays.asList(card.getDiscountGoodsId().split(",")).contains(bo.getGoodsId().toString())) {
            // 商品id
            return true;
        }
        if (StringUtil.isNotBlank(card.getDiscountCatId()) && Arrays.asList(card.getDiscountCatId().split(",")).contains(bo.getCatId().toString())) {
            // 平台分类id
            return true;
        }
        if (StringUtil.isNotBlank(card.getDiscountSortId()) && Arrays.asList(card.getDiscountSortId().split(",")).contains(bo.getSortId().toString())) {
            // 商家分类id
            return true;
        }
        if (StringUtil.isNotBlank(card.getDiscountBrandId()) && Arrays.asList(card.getDiscountBrandId().split(",")).contains(bo.getBrandId().toString())) {
            // 商品品牌id
            return true;
        }
=======
		if (StringUtil.isNotBlank(card.getDiscountGoodsId())) {
			// 商品id
			return Arrays.asList(card.getDiscountGoodsId().split(",")).contains(bo.getGoodsId());
		}
		if (StringUtil.isNotBlank(card.getDiscountCatId())) {
			// 平台分类id
			return Arrays.asList(card.getDiscountCatId().split(",")).contains(bo.getCatId());
		}
		if (StringUtil.isNotBlank(card.getDiscountSortId())) {
			// 商家分类id
			return Arrays.asList(card.getDiscountSortId().split(",")).contains(bo.getSortId());
		}
		if (StringUtil.isNotBlank(card.getDiscountBrandId())) {
			// 商品品牌id
			return Arrays.asList(card.getDiscountBrandId().split(",")).contains(bo.getBrandId());
		}
>>>>>>> bugfix-2.7-1003441
		return false;
	}

	/**
	 * 王帅 获取该卡打折金额
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @param card       会员卡
	 * @param totalPrice 折前总价
	 * @return 折后总价
	 */
	public BigDecimal getDiscountAmount(OrderMemberVo card, BigDecimal totalPrice) {
		if (CardConstant.MCARD_TP_LIMIT.equals(card.getCardType())) {
			// 限次卡
			card.setTotalDiscount(totalPrice);
		} else if (BigDecimalUtil.compareTo(card.getDiscount(), BigDecimal.ZERO) == -1) {
			// 如果没有打折权益则为十折
			card.setTotalDiscount(BigDecimal.ZERO);
		} else {
			// 正常打折(价格 * （10 - 折扣（eg:6.66） / 10）)
			card.setTotalDiscount(BigDecimalUtil.multiplyOrDivide(
					BigDecimalUtil.BigDecimalPlus.create(totalPrice, BigDecimalUtil.Operator.multiply),
					BigDecimalUtil.BigDecimalPlus.create(
							BigDecimalUtil.addOrSubtrac(
									BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN,
											BigDecimalUtil.Operator.subtrac),
									BigDecimalUtil.BigDecimalPlus.create(card.getDiscount(), null)),
							BigDecimalUtil.Operator.divide),
					BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, null)));
		}
		return card.getTotalDiscount();
	}

	/**
	 * 获取用户累积消费总额
<<<<<<< HEAD
	 *
=======
	 * 
>>>>>>> bugfix-2.7-1003441
	 * @return 消费总额,默认为0
	 */
	private BigDecimal getUserTotalSpendAmount(Integer userId) {
		DistributorSpendVo distributorSpendVo = distributorLevelService.getTotalSpend(userId);
		return distributorSpendVo.getTotal() != null ? distributorSpendVo.getTotal() : BigDecimal.ZERO;
	}

	/**
	 * 王帅 get card
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

	/**
	 * 废除会员卡
	 */
	public void repealCardByCardNo(String cardNo) {
		userCardDao.repealCardByCardNo(cardNo);
	}

	/**
	 * Gets single field.
	 *
	 * @param <T>       the type parameter
	 * @param field     the field
	 * @param condition the condition
	 * @return the single field
	 */
	public <T> T getSingleField(Field<T> field, Condition condition) {
		return db().select(field).from(USER_CARD).where(condition).fetchOne(field);
	}

	public UserCardVo getUserCardJudge(UserIdAndCardIdParam param) {
		UserCardVo userCard = userCardDao.getUserCardJudge(param);
		// 处理背景图片
		if(userCard != null && CardUtil.isBgImgType(userCard.getBgType())) {
			if(!StringUtils.isBlank(userCard.getBgImg())) {
				String imageUrl = saas.getShopApp(getShopId()).image.imageUrl(userCard.getBgImg());
				userCard.setBgImg(imageUrl);
			}
		}
		return userCard;
	}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
	public UserCardJudgeVo userCardJudgement(UserIdAndCardIdParam param,String lang) {
		UserCardVo userCard = getUserCardJudge(param);
		MemberCardRecord mCard = cardDao.getCardById(param.getCardId());
		// null
		boolean isGet = false;
		if (userCard != null) {
			logger().info("用户有此卡");
			isGet = true;
		}else {
			logger().info("用户没有有此卡");
			isGet = false;
			userCard = mCard.into(UserCardVo.class);
		}
		userCard.setIsGet(isGet);
		// 限次卡
		if (CardUtil.isLimitCard(userCard.getCardType())) {
			if (!canSendLimitCard(param.getUserId(), mCard)) {
				logger().info("限次卡领取次数用完");
				isGet = false;
			}
		}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
		if ((isGet && CardUtil.isLimitCard(userCard.getCardType())) || !isGet) {
			logger().info("用户有此限次卡，或者没有此卡");
			if (!CardUtil.isNeedToBuy(mCard.getIsPay())) {
				userCard.setPayFee(null);
			}
			if (!CardUtil.isCardTimeForever(userCard.getExpireType())) {
				if (CardUtil.isCardFixTime(userCard.getExpireType()) && CardUtil.isCardExpired(userCard.getEndTime())) {
						logger().info("卡过期");
						userCard.setStatus(-1);
					} else {
						userCard.setStatus(1);
					}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
					if (CardUtil.isCardFixTime(userCard.getExpireType())) {
						userCard.setStartDate(userCard.getStartTime().toLocalDateTime().toLocalDate());
						userCard.setEndDate(userCard.getEndTime().toLocalDateTime().toLocalDate());
					}
				} else {
					userCard.setStatus(1);
				}
<<<<<<< HEAD

=======
			
>>>>>>> bugfix-2.7-1003441
				userCard.setShopAvatar(getCardAvatar());
				userCard.setScoreAmount(scoreService.getAccumulationScore(param.getUserId()));
				userCard.setPaidAmount(orderInfoService.getAllConsumpAmount(param.getUserId()));
				userCard.setBindMobile(shopCommonConfigService.getBindMobile());
<<<<<<< HEAD

=======
				
>>>>>>> bugfix-2.7-1003441
				if(CardUtil.isLimitCard(userCard.getCardType()) && CardUtil.canExchangGoods(userCard.getIsExchang())) {
					logger().info("处理限次卡兑换的商品");
					if(!StringUtils.isBlank(userCard.getExchangGoods())) {
						List<Integer> goodsIdList = Util.splitValueToList(userCard.getExchangGoods());
						List<GoodsSmallVo> goodsList = goodsService.getGoodsList(goodsIdList, false);
						userCard.setGoodsList(goodsList);
					}else {
						GoodsPageListParam goodsParam = new GoodsPageListParam();
						goodsParam.setPageRows(2);
						goodsParam.setCurrentPage(1);
						PageResult<GoodsPageListVo> goodsPageList = goodsService.getPageList(goodsParam);
						List<Integer> goodsIdList = new ArrayList<>();
						for(GoodsPageListVo goodsVo: goodsPageList.dataList) {
							goodsIdList.add(goodsVo.getGoodsId());
						}
						List<GoodsSmallVo> goodsList = goodsService.getGoodsList(goodsIdList, false);
						userCard.setGoodsList(goodsList);
					}
<<<<<<< HEAD

=======
					
>>>>>>> bugfix-2.7-1003441
					if(userCard.getGoodsList()!=null) {
						logger().info("价格处理为两位小数");
						for(GoodsSmallVo goodsVo: userCard.getGoodsList()) {
							BigDecimal shopPrice = goodsVo.getShopPrice();
							goodsVo.setShopPrice(shopPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN));
						}
					}
<<<<<<< HEAD

=======
					
>>>>>>> bugfix-2.7-1003441
					if(userCard.getExchangCount()==null) {
						userCard.setExchangCount(userCard.getExchangCount());
					}
				}
<<<<<<< HEAD

=======
			
>>>>>>> bugfix-2.7-1003441
			if(CardUtil.isLimitCard(userCard.getCardType()) && CardUtil.canUseInStore(userCard.getStoreUseSwitch())) {
				if(userCard.getSurplus()==null) {
					userCard.setSurplus(userCard.getCount());
				}
			}
<<<<<<< HEAD

=======
			
>>>>>>> bugfix-2.7-1003441
			if(!StringUtil.isBlank(userCard.getStoreList()) && CardUtil.canUseInStore(userCard.getStoreUseSwitch())) {
				logger().info("获取门店信息");
				List<Integer> storeIdList = CardUtil.parseStoreList(userCard.getStoreList());
				List<StoreBasicVo> storeList = storeService.getStoreListByStoreIds(storeIdList);
				userCard.setStoreInfoList(storeList);
			}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
			logger().info("开卡送券");
			dealSendCouponInfo(userCard,lang);
			UserCardJudgeVo userCardJudgeVo = new UserCardJudgeVo();
			userCardJudgeVo.setStatus(1);
			userCard.setUserId(param.getUserId());
			userCard.setCardId(param.getCardId());
			userCardJudgeVo.setCardInfo(userCard);
			return userCardJudgeVo;
		}else{
			UserCardVo uCard = getUserCardByCardNo(userCard.getCardNo());
			uCard.setIsGet(isGet);
			if(uCard.getExpireTime()!=null) {
				if(uCard.getStartTime()!=null) {
					uCard.setStartDate(uCard.getStartTime().toLocalDateTime().toLocalDate());
				}
				if(uCard.getEndTime()!=null) {
					uCard.setEndDate(uCard.getEndTime().toLocalDateTime().toLocalDate());
				}
				uCard.setExpireType(NumberUtils.BYTE_ZERO);
			}else {
				uCard.setExpireType(CardConstant.MCARD_ET_FOREVER);
			}
			if (!CardUtil.isCardTimeForever(uCard.getExpireType())) {
				if (CardUtil.isCardFixTime(uCard.getExpireType()) && CardUtil.isCardExpired(uCard.getEndTime())) {
					logger().info("卡过期");
					uCard.setStatus(-1);
				} else {
					uCard.setStatus(1);
				}

				if (CardUtil.isCardFixTime(uCard.getExpireType())) {
					uCard.setStartDate(uCard.getStartTime().toLocalDateTime().toLocalDate());
					uCard.setEndDate(uCard.getEndTime().toLocalDateTime().toLocalDate());
				}
			} else {
				uCard.setStatus(1);
			}
			uCard.setScoreAmount(scoreService.getAccumulationScore(param.getUserId()));
			uCard.setPaidAmount(orderInfoService.getAllConsumpAmount(param.getUserId()));
			if(CardUtil.isGradeCard(uCard.getCardType())) {
				// 升级进度条内容
				NextGradeCardVo nextGradeCard = getNextGradeCard(uCard.getGrade());
<<<<<<< HEAD
				uCard.setNext(nextGradeCard);
=======
				uCard.setNext(nextGradeCard);				
>>>>>>> bugfix-2.7-1003441
			}
			if(!CardUtil.isGradeCard(uCard.getCardType()) && !StringUtil.isBlank(uCard.getStoreList()) && CardUtil.canUseInStore(uCard.getStoreUseSwitch())) {
				logger().info("获取门店信息");
				List<Integer> storeIdList = CardUtil.parseStoreList(uCard.getStoreList());
				List<StoreBasicVo> storeList = storeService.getStoreListByStoreIds(storeIdList);
				uCard.setStoreInfoList(storeList);
			}
			// 会员卡头像
<<<<<<< HEAD

			uCard.setShopAvatar(getCardAvatar());
			// 背景图片

=======
			
			uCard.setShopAvatar(getCardAvatar());
			// 背景图片
			
>>>>>>> bugfix-2.7-1003441
			logger().info("虚拟卡订单下单时间");
			VirtualOrderRecord order = virtualOrderService.getInfoByNo(uCard.getCardNo());
			if(order != null) {
				Timestamp buyTime = order.getCreateTime();
				uCard.setBuyTime(buyTime);
			}
<<<<<<< HEAD

=======
			
>>>>>>> bugfix-2.7-1003441
			logger().info("卡的校验状态");
			CardExamineRecord  cardExamine = cardVerifyService.getStatusByNo(uCard.getCardNo());
			if(cardExamine != null) {
				WxAppCardExamineVo cardExamineVo = new WxAppCardExamineVo();
				cardExamineVo.setPassTime(cardExamine.getPassTime());
				cardExamineVo.setRefuseTime(cardExamine.getRefuseTime());
				cardExamineVo.setRefuseDesc(cardExamine.getRefuseDesc());
				cardExamineVo.setStatus(cardExamine.getStatus());
				uCard.setIsExamine(cardExamineVo);
			}
<<<<<<< HEAD

=======
			
>>>>>>> bugfix-2.7-1003441
			if(CardUtil.isLimitCard(uCard.getCardType()) && CardUtil.canExchangGoods(uCard.getIsExchang())) {
				logger().info("处理限次卡兑换的商品");
				if(!StringUtils.isBlank(uCard.getExchangGoods())) {
					List<Integer> goodsIdList = Util.splitValueToList(uCard.getExchangGoods());
					List<GoodsSmallVo> goodsList = goodsService.getGoodsList(goodsIdList, false);
					uCard.setGoodsList(goodsList);
				}
				if(userCard.getGoodsList()!=null) {
					logger().info("价格处理为两位小数");
					for(GoodsSmallVo goodsVo: userCard.getGoodsList()) {
						BigDecimal shopPrice = goodsVo.getShopPrice();
						goodsVo.setShopPrice(shopPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN));
					}
				}
			}
			dealSendCouponInfo(uCard,lang);
			UserCardJudgeVo userCardJudgeVo = new UserCardJudgeVo();
			userCardJudgeVo.setStatus(1);
			uCard.setUserId(param.getUserId());
			uCard.setCardId(param.getCardId());
			userCardJudgeVo.setCardInfo(uCard);
			return userCardJudgeVo;
		}
	}

	private void dealSendCouponInfo(UserCardVo userCard, String lang) {
		logger().info("开卡送券");
		if (CardUtil.isSendCoupon(userCard.getSendCouponType())) {
			List<Integer> couponIds = CardUtil.parseCouponList(userCard.getSendCouponIds());
			List<CouponView> couponList = couponService.getCouponViewByIds(couponIds);
			List<UserCardCoupon> couponListTwo = new ArrayList<>();
			for (CouponView coupon : couponList) {
				// 国际化 UserCardCoupon
				UserCardCoupon uc = new UserCardCoupon();
				if (NumberUtils.BYTE_ZERO.equals(coupon.getSuitGoods())) {
					uc.setCouponCondition(Util.translateMessage(lang, "user.card.coupon.condition.all", "member"));
				} else {
					uc.setCouponCondition(Util.translateMessage(lang, "user.card.coupon.condition.part", "member"));
				}
				logger().info("优惠券过期时间");
				if (coupon.getValidity() > 0 || coupon.getValidityHour() > 0 || coupon.getValidityMinute() > 0) {
					StringBuilder con = new StringBuilder();
					String receiveInfo = Util.translateMessage(lang, "card.receive.day.start", "member");
					con.append(receiveInfo);
					if (coupon.getValidity() > 0) {
						String val = Util.translateMessage(lang, "card.receive.day", "member", coupon.getValidity());
						con.append(val);
					}
					if (coupon.getValidityHour() > 0) {
						String val = Util.translateMessage(lang, "card.receive.hour", "member",
								coupon.getValidityHour());
						con.append(val);
					}
					if (coupon.getValidityMinute() > 0) {
						String val = Util.translateMessage(lang, "card.receive.minute", "member",
								coupon.getValidityMinute());
						con.append(val);
					}
					uc.setExpireTime(con.toString());
				} else {
					String startTime = coupon.getStartTime().toLocalDateTime().toLocalDate().toString();
					String endTime = coupon.getEndTime().toLocalDateTime().toLocalDate().toString();
					String tmp = startTime + "--" + endTime;
					uc.setExpireTime(tmp);
				}

				logger().info("处理使用条件");

				if (NumberUtils.INTEGER_ZERO.equals(coupon.getUseConsumeRestrict())) {
					uc.setUseConsumeRestrict(Util.translateMessage(lang, "card.coupon.nolimit", "member"));
				} else {
					uc.setUseConsumeRestrict(
							Util.translateMessage(lang, "card.coupon.satisfiy", "member", coupon.getLeastConsume()));
				}
				couponListTwo.add(uc);
			}
			userCard.setCoupons(couponListTwo);
		} else if (CardUtil.isSendCouponPack(userCard.getSendCouponType())) {
			logger().info("处理优惠券礼包");
			if (!StringUtils.isBlank(userCard.getSendCouponIds())) {
				int id = Integer.parseInt(userCard.getSendCouponIds());
				CouponPackUpdateVo couponPack = couponPackService.getCouponPackById(id);
				UserCardCouponPack pack = new UserCardCouponPack();
				pack.setId(couponPack.getId());
				pack.setActName(couponPack.getActName());
				userCard.setCouponPack(pack);
			}
		}
	}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
	/**
	 * 领取会员卡
	 * @param param
	 * @return 带有卡号的卡信息
	 * @throws MpException
	 */
	public CardReceiveVo getCard(UserIdAndCardIdParam param) throws MpException {
		if(param.getCardId()==null) {
			return null;
		}
		CardReceiveVo vo = new CardReceiveVo();
		MemberCardRecord gCard = getGradeCard(param);
		// 第一次领取
		boolean firstGet = true;
		if (param.getCardId() != null) {
			if (gCard == null) {
				// 领取的卡为非等级卡
				MemberCardRecord mCard = memberCardService.getCardById(param.getCardId());
				if(mCard == null) {
					return null;
				}
				// 判断是否需要购买
				if (NumberUtils.BYTE_ZERO.equals(mCard.getIsPay())) {
					int hasSendUser = userCardDao.getHasSendUser(param);
					int hasSend = userCardDao.getHasSend(param.getCardId());
					// 处理限次卡
					if (CardUtil.isLimitCard(mCard.getCardType())) {
						// 每人领取次数
						if (mCard.getLimit() > 0 && hasSendUser >= mCard.getLimit()) {
							logger().info("达到领取上限");
							throw new LimitCardAvailSendNoneException();
						}
						// 限次卡能够领取的总次数
						if (mCard.getStock() > 0 && hasSend >= mCard.getStock()) {
							logger().info("会员卡已领光");
							throw new LimitCardAvailSendNoneException(JsonResultCode.CODE_LIMIT_CARD_AVAIL_SEND_ALL);
						}
					}
<<<<<<< HEAD

=======
					
>>>>>>> bugfix-2.7-1003441
					List<String> cardNoList = null;
					// 普通卡只能领一张
					if(CardUtil.isNormalCard(mCard.getCardType())) {
						// 判断是否有此卡，或者有此卡，但是卡已被废除
						Condition condition = DSL.noCondition();
						condition = condition.and(USER_CARD.USER_ID.eq(param.getUserId()))
									.and(USER_CARD.CARD_ID.eq(param.getCardId()))
									.and(USER_CARD.FLAG.eq(CardConstant.UCARD_FG_USING));
						 UserCardRecord res = db().selectFrom(USER_CARD).where(condition).fetchAnyInto(USER_CARD);
						 if(res == null) {
							 // 没有此会员卡
							 cardNoList = addUserCard(param.getUserId(), param.getCardId());
						 }else {
							 // 已经拥有此会员卡
							cardNoList = new ArrayList<String>();
							cardNoList.add(res.getCardNo());
							firstGet = false;
						 }
					}else {
						// 限次卡
						cardNoList = addUserCard(param.getUserId(), param.getCardId());
					}
<<<<<<< HEAD

=======
					
>>>>>>> bugfix-2.7-1003441
					if (cardNoList == null || cardNoList.size() < 1) {
						logger().info("领取失败");
						throw new CardReceiveFailException();
					}

					if (CardUtil.isLimitCard(mCard.getCardType())) {
						int hasSendNew = userCardDao.getHasSend(param.getCardId());
						if ((mCard.getLimit() > 0 && hasSendUser + 1 >= hasSendUser)
								|| (mCard.getStock() > 0 && hasSendNew >= mCard.getStock())) {
							logger().info("领取到的卡，卡号为: " + cardNoList.get(0));
							vo.setIsContinue("not_continue");
						}
						vo.setCardNo(cardNoList.get(0));
						return vo;
					} else {
						if(firstGet) {
							// 第一次领取
							if (NumberUtils.BYTE_ZERO.equals(mCard.getActivation())
									&& CardUtil.isNormalCard(mCard.getCardType())) {
								memberCardService.sendCoupon(param.getUserId(), param.getCardId());
							}
						}
						vo.setCardNo(cardNoList.get(0));
						return vo;
					}
				} else {
					logger().info("领取失败");
					throw new CardReceiveFailException();
				}
			}
		} else {
			logger().info("首次领取等级卡");
			Integer cardId = 0;
			try {
				cardId = updateGrade(param.getUserId(), param.getCardId(), (byte) 2);
				vo.setIsMostGrade(false);
			} catch (MemberCardNullException e) {
				vo.setIsMostGrade(true);
			}
			String cardNo = getCardNoByUserAndCardId(param.getUserId(), cardId);
			if (StringUtils.isBlank(cardNo)) {
				logger().info("领取失败");
				throw new CardReceiveFailException();
			}
			logger().info("领取的会员卡卡号为： " + cardNo);
			MemberCardRecord newCard = memberCardService.getCardById(cardId);

			GradeCardData data = new GradeCardData();
			GradeConditionJson gradeCondition = Util.parseJson(gCard.getGradeCondition(), GradeConditionJson.class);
			BigDecimal gradeMoney = gradeCondition.getGradeMoney();
			BigDecimal gradeScore = gradeCondition.getGradeScore();
			data.setAmount(gradeMoney);
			data.setScore(gradeScore);
			vo.setCardNo(cardNo);
			vo.setGradeCard(data);
			return vo;
		}
		return vo;
	}

	/**
	 * 获取用户的等级卡
	 * @param param
	 * @return
	 */
	public MemberCardRecord getGradeCard(UserIdAndCardIdParam param) {
		MemberCardRecord card = cardDao.getCardById(param.getCardId());
		if (card == null || !CardUtil.isGradeCard(card.getCardType())) {
			return null;
		}
		Integer accumulationScore = scoreService.getAccumulationScore(param.getUserId());
		BigDecimal consumpAmount = orderInfoService.getAllConsumpAmount(param.getUserId());
		MemberCardRecord userGradeCard = getUserGradeCard(param.getUserId());
		List<MemberCardRecord> gradeCardList = null;
		if (userGradeCard != null) {
			gradeCardList = memberCardService.getGradeCardList(userGradeCard.getGrade());
		} else {
			gradeCardList = memberCardService.getGradeCardList();
		}
		Integer cardId = 0;
		if (gradeCardList == null) {
			logger().info("已有最高等级等级卡");
			return null;
		} else {
			for (MemberCardRecord gCard : gradeCardList) {
				GradeConditionJson gradeCondition = getGradeCondition(accumulationScore, consumpAmount, gCard);
				if (isSatisfyUpgradeCondition(accumulationScore, consumpAmount, gradeCondition)) {
					cardId = gCard.getId();
				}
			}
		}

		if (cardId == userGradeCard.getId()) {
			return null;
		}

		MemberCardRecord mCard = memberCardService.getCardById(param.getCardId());
		if (!CardUtil.isGradeCard(mCard.getCardType())) {
			return null;
		}

		GradeConditionJson gradeCondition = getGradeCondition(accumulationScore, consumpAmount, mCard);
		if (isSatisfyUpgradeCondition(accumulationScore, consumpAmount, gradeCondition)) {
			MemberCardRecord oldCard = getUserGradeCard(param.getUserId());
			if (!StringUtils.isBlank(oldCard.getGrade())) {
				logger().info("升级记录");
				String operation = "首页领取";
				changeUserGradeCard(param.getUserId(), oldCard, mCard, operation);
			} else {
				createNewUserCard(param.getUserId(), mCard, NumberUtils.BYTE_ZERO.equals(mCard.getActivation()));
			}
			return mCard;
		}
		return null;
	}

	public String getCardNoByUserAndCardId(Integer userId, Integer cardId) {
		UserCardRecord rec = db().selectFrom(USER_CARD)
				.where(USER_CARD.USER_ID.eq(userId).and(USER_CARD.CARD_ID.eq(cardId))).fetchAny();
		return rec != null ? rec.getCardNo() : null;
	}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
	public UserCardVo getUserCardByCardNo(String cardNo){
		UserCardVo userCard = userCardDao.getUserCardByCardNo(cardNo);
		if(userCard != null && CardUtil.isBgImgType(userCard.getBgType())) {
			if(!StringUtils.isBlank(userCard.getBgImg())) {
				String imageUrl = saas.getShopApp(getShopId()).image.imageUrl(userCard.getBgImg());
				userCard.setBgImg(imageUrl);
			}
		}
		return userCard;
	}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
	public void updateActivationTime(String cardNo,Timestamp time) {
		if(time==null) {
			time = DateUtil.getLocalDateTime();
		}
		userCardDao.updateActivationTime(cardNo,time);
	}

	/**
	 *	 设置默认会员卡
	 */
	public void setDefault(DefaultCardParam param) {
		logger().info("设置默认会员卡");
		transaction(()->{
			Condition condition = DSL.noCondition();
			// set user card all 0
			condition = condition.and(USER_CARD.USER_ID.eq(param.getUserId()));
			userCardDao.updateIsDefault(condition,NumberUtils.BYTE_ZERO);
			// only set card 1 by cardNo
			condition = condition.and(USER_CARD.CARD_NO.eq(param.getCardNo()));
			userCardDao.updateIsDefault(condition,NumberUtils.BYTE_ONE);
		});
	}
<<<<<<< HEAD

=======
	
>>>>>>> bugfix-2.7-1003441
	/**
	 * 检查用户等级升级
	 * @param userId
	 * @param grade
	 * @return
	 */
	public boolean checkUserGradeCard(Integer userId,String grade) {
		String userGrade = getUserGrade(userId);
		if(Objects.equals(userGrade, grade)) {
			return true;
		}
		MemberCardRecord cardByGrade = getGradeCardByGrade(grade);
		if(cardByGrade==null) {
			return false;
		}
		try {
			updateGrade(userId, cardByGrade.getId(), TP_CHECK);
		} catch (MpException e) {
			logger().info("升级错误");
			logger().info(e.getMessage(),e);
			return false;
		}
		return true;
	}



}

