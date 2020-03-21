package com.vpu.mp.service.shop.member;


import com.google.common.collect.Lists;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.CardExamineRecord;
import com.vpu.mp.db.shop.tables.records.MemberCardRecord;
import com.vpu.mp.db.shop.tables.records.UserCardRecord;
import com.vpu.mp.db.shop.tables.records.VirtualOrderRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
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
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
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
import com.vpu.mp.service.pojo.shop.member.card.CardBgBean;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.shop.member.card.EffectTimeBean;
import com.vpu.mp.service.pojo.shop.member.card.EffectTimeParam;
import com.vpu.mp.service.pojo.shop.member.card.GradeConditionJson;
import com.vpu.mp.service.pojo.shop.member.card.RankCardToVo;
import com.vpu.mp.service.pojo.shop.member.card.SearchCardParam;
import com.vpu.mp.service.pojo.shop.member.card.UserCardConsumeBean;
import com.vpu.mp.service.pojo.shop.member.card.create.CardCustomRights;
import com.vpu.mp.service.pojo.shop.member.card.create.CardFreeship;
import com.vpu.mp.service.pojo.shop.member.exception.CardReceiveFailException;
import com.vpu.mp.service.pojo.shop.member.exception.CardSendRepeatException;
import com.vpu.mp.service.pojo.shop.member.exception.LimitCardAvailSendNoneException;
import com.vpu.mp.service.pojo.shop.member.exception.MemberCardNullException;
import com.vpu.mp.service.pojo.shop.member.exception.UserCardNullException;
import com.vpu.mp.service.pojo.shop.member.ucard.DefaultCardParam;
import com.vpu.mp.service.pojo.shop.operation.RemarkMessage;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.wxapp.card.CardUpgradeVo;
import com.vpu.mp.service.pojo.wxapp.cart.list.WxAppCartGoods;
import com.vpu.mp.service.pojo.wxapp.member.card.GeneralUserCardVo;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.member.OrderMemberVo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.process.DefaultMarketingProcess;
import com.vpu.mp.service.shop.card.CardFreeShipService;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.distribution.DistributorLevelService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.couponpack.CouponPackService;
import com.vpu.mp.service.shop.member.card.CardUserOpt;
import com.vpu.mp.service.shop.member.card.GradeCardOpt;
import com.vpu.mp.service.shop.member.card.LimitCardOpt;
import com.vpu.mp.service.shop.member.card.NormalCardOpt;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_DF_NO;
import static com.vpu.mp.service.pojo.shop.member.card.CardConstant.MCARD_FLAG_USING;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.DEFAULT_ADMIN;
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
	private DomainConfig domainConfig;
	@Autowired
	private ShopCommonConfigService shopCommonConfigService;
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponPackService couponPackService;
	@Autowired
	private VirtualOrderService virtualOrderService;
	@Autowired
	private LimitCardOpt limitCardOpt;
	@Autowired
	private NormalCardOpt normalCardOpt;
	@Autowired 
	private GradeCardOpt gradeCardOpt;
	@Autowired 
	private CardUserOpt cardUserOpt;
	@Autowired
	private CardFreeShipService cardFreeShipSvc;
	public static final String DESC = "score_open_card";

	/**
	 * 返回会员等级-按照持有会员等级卡划分，若无持有等级会员卡，则返回默认最低等级
	 */
	public String getUserGrade(Integer userId) {
		String userGrade = userCardDao.calcUserGrade(userId);
		return StringUtils.isBlank(userGrade) ? LOWEST_GRADE : userGrade;
	}

	/**
	 * 	用户是否有可用的等级卡
	 * @Return  true拥有 ，false 未拥有
	 */
	public boolean isHasAvailableGradeCard(Integer userId) {
		String grade = userCardDao.calcUserGrade(userId);
		logger().info("当前用户等级"+grade);
		return !StringUtils.isBlank(grade);
	}

	/**
	 * 	获取用户持有的等级卡
	 */
	public MemberCardRecord getUserGradeCard(Integer userId) {
		return userCardDao.getUserGradeCard(userId);
	}

	/**
	 * 	用户卡等级变动
	 */
	public void changeUserGradeCard(Integer userId, MemberCardRecord oldCard, MemberCardRecord newCard,
			String option) {
		logger().info("用户会员卡升级");
		//	更新卡
		userCardDao.updateUserGradeCardId(userId, newCard.getId());
		//	保存用户卡等级变动信息
		cardUpgradeService.recordCardUpdateGrade(userId, oldCard, newCard, option);
		if (newCard.getSorce() != null && newCard.getSorce() > 0) {
			addUserCardScore(userId, newCard);
		}
	}
	
	/**
	 * 会员卡升降级记录
	 */
	public PageResult<CardUpgradeVo> getGradeList(SearchCardParam param) {
		logger().info("获取会员的升降级记录");
		PageResult<CardUpgradeVo> upgradeList = cardUpgradeService.getGradeList(param);
		List<CardUpgradeVo> upgradeListNew = new ArrayList<>();
		if(upgradeList.dataList !=null && upgradeList.dataList.size()>0) {
			UserCardRecord info = userCardDao.getCardByUserCardId(param.getUserId(), upgradeList.dataList.get(0).getNewCardId());
			if(info!=null) {
				Integer oldCardId = info.getCardId();
				for(CardUpgradeVo vo: upgradeList.dataList) {
					if(oldCardId.equals(vo.getNewCardId())) {
						upgradeListNew.add(vo);
						oldCardId = vo.getNewCardId();
					}else {
						break;
					}
				}
			}
		}
		upgradeList.setDataList(upgradeListNew);
		return upgradeList;
	}


	/**
	 *  开卡赠送积分
	 */
	public void addUserCardScore(Integer userId, MemberCardRecord card) {
		if (card.getSorce() == null || card.getSorce() <= 0) {
			return;
		}		
		ScoreParam scoreParam = new ScoreParam();
		scoreParam.setUserId(userId);
		scoreParam.setScore(card.getSorce());
		scoreParam.setDesc("score_open_card");
		if(CardUtil.isGradeCard(card.getCardType())) {
			scoreParam.setRemarkCode(RemarkTemplate.CARD_UPGRADE.code);
		}else {
			scoreParam.setRemarkCode(RemarkTemplate.OPEN_CARD_SEND.code);
		}
		scoreParam.setExpiredTime(scoreService.getScoreExpireTime());
		
		try {
			scoreService.updateMemberScore(scoreParam, Integer.valueOf(DEFAULT_ADMIN.val()), TYPE_SCORE_CREATE_CARD.val(), TRADE_FLOW_IN.val());
		} catch (MpException e) {
			logger().info("卡赠送积分失败");
		}
	}

	/**
	 * 	会员卡升级检测并升级
	 * 
	 * @param type 是否领取 1领取 0只是检测
	 * @return cardId（当type为0时为检测可升级的卡id,type为1时为领取后的卡id),0为没有可升级的卡
	 */
	public Integer updateGrade(Integer userId, Integer cardId, Byte type) throws MpException {
		Assert.isTrue(userId != null, "userId required");

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
		}
	}

	private Integer checkAndUpgradeUserCard(Integer userId) throws MpException {
		Integer cardId = null;
		// 获取用户累积获得积分和累积消费总额
		Integer userTotalScore = scoreService.getAccumulationScore(userId);
		BigDecimal amount = getUserTotalSpendAmount(userId);
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
	 * 	检测可升级到的等级卡
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

	public GradeConditionJson getGradeCondition(Integer userTotalScore, BigDecimal amount, MemberCardRecord gCard) {

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
	public boolean isSatisfyUpgradeCondition(Integer userTotalScore, BigDecimal amount,
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
	 * 	添加会员卡
	 * 
	 * @return
	 */
	public List<String> addUserCard(Integer userId, List<UserCardParam> cardList, boolean isActivate)
			throws MpException {

		stopUserLimitCard(cardList);
		List<String> cardNoList = new ArrayList<>();
		for (UserCardParam card : cardList) {
			MemberCardRecord mCard = cardDao.getCardById(card.getCardId());
			if (CardUtil.isLimitCard(mCard.getCardType())) {
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
	 *
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
	 * 	发送模板消息
	 */
	public void sendScoreTemplateMsg(MemberCardRecord card) {
		StringBuilder content = new StringBuilder();
		if (CardUtil.isLimitCard(card.getCardType())) {
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
	
	/**
	 * 	会员卡余额，门店，商品兑换次数记录
	 * @param card
	 * @param userCard
	 */
	public void addChargeMoney(MemberCardRecord card, UserCardRecord userCard) {
		ChargeMoneyRecordBuilder builder = ChargeMoneyRecordBuilder.create(db().newRecord(CHARGE_MONEY))
				.userId(userCard.getUserId()).cardId(userCard.getCardId()).type(card.getCardType())
				.cardNo(userCard.getCardNo()).payment("store.payment").createTime(DateUtil.getLocalDateTime());

		
		if (CardUtil.isNormalCard(card.getCardType()) && card.getSendMoney() != null) {
			//  管理员发卡
			builder.charge(new BigDecimal(card.getSendMoney())).reasonId(String.valueOf(RemarkTemplate.ADMIN_SEND_CARD.code)).build().insert();

		}
		if (CardUtil.isLimitCard(card.getCardType())) {
			if(CardUtil.canUseInStore(card.getStoreUseSwitch())) {
				// 管理员发卡 - 门店服务次数
				builder.count(card.getCount().shortValue()).reasonId(String.valueOf(RemarkTemplate.SEND_CARD_REASON.code));
			}
			
			if (CardUtil.canExchangGoods(card.getIsExchang())) {
				// 管理员发卡 - 兑换商品数量
				builder.exchangCount(card.getExchangCount().shortValue()).reasonId(String.valueOf(RemarkTemplate.ADMIN_EXCHANGE_GOODS.code));
			}
			builder.build().insert();
		}
	}

	private UserCardRecord createNewUserCard(Integer userId, MemberCardRecord card, boolean isActivate) {
		UserCardRecordBuilder cardBuilder = UserCardRecordBuilder.create(db().newRecord(USER_CARD)).userId(userId)
				.cardId(card.getId()).cardNo(getRandomCardNo(card.getId())).createTime(DateUtil.getLocalDateTime())
				.expireTime(calcCardExpireTime(card));

		if (CardUtil.isLimitCard(card.getCardType())) {
			cardBuilder.surplus(card.getCount());
			if (card.getIsExchang() != null) {
				cardBuilder.exchangSurplus(card.getExchangCount());
			}
		} else if (CardUtil.isNormalCard(card.getCardType())) {
			if (card.getSendMoney() != null) {
				cardBuilder.money(new BigDecimal(card.getSendMoney()));
			}
		} else if (CardUtil.isGradeCard(card.getCardType()) && 
					isHasAvailableGradeCard(userId)) {
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
		if (CardUtil.isGradeCard(card.getCardType()) && !isHasAvailableGradeCard(userId)) {
			logger().info("用户目前没有等级卡，设置一个等级卡: " + card.getGrade());
			result = cardBuilder.build().insert();
		} else {
			logger().info("发送普通或者限次卡：" + card.getCardName());
			result = cardBuilder.build().insert();
		}
		logger().info(String.format("成功向ID为%d的用户，发送了%d张会员卡", userId, result));
		return cardBuilder.build();
	}


	public boolean isActivateNow(MemberCardRecord card) {
		return MCARD_ACT_NO.equals(card.getActivation());
	}

	public Timestamp calcCardExpireTime(MemberCardRecord card) {
		Assert.isTrue(card != null,"card should not be null");
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
		if (CardUtil.isLimitCard(data.getType())) {
			// 限次卡
			if (NumberUtils.BYTE_ONE.equals(type)) {
				if (adminUser != null && (userInfo.getExchangSurplus() - data.getCountDis()) != 0) {
					return -1;
				}
			} else {
				if (adminUser != null && (userInfo.getSurplus() - data.getCountDis()) != 0) {
					return -1;
				}
			}
		} else {
			// 普通卡
			if (adminUser != null
					&& BigDecimalUtil.subtrac(userInfo.getMoney(), userInfo.getMoney()).floatValue() != 0.00) {
				return -1;
			}
		}

		// serviceOrder

		data.setCreateTime(DateUtil.getLocalDateTime());
		if (StringUtils.isBlank(data.getReason())) {
			data.setReasonId(String.valueOf(RemarkTemplate.ADMIN_OPERATION.code));
		}
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
	 *
	 */
	public PageResult<WxAppUserCardVo> getAllCardsOfUser(SearchCardParam param,String lang) {
		logger().info("获取用户所有的会员卡列表");
		PageResult<WxAppUserCardVo> cardList = userCardDao.getCardList(param);
		String avatar = getCardAvatar();
		for (WxAppUserCardVo card : cardList.dataList) {
			dealWithWxUserCard(card, avatar);
			//过期检查
			setWxUserCardVoExpire(card);
			// 包邮信息
			dealWithFreeShipInfo(card,lang);			
		}
		return cardList;
	}

	/**
	 * 	处理卡的包邮信息
	 */
	private void dealWithFreeShipInfo(WxAppUserCardVo card,String lang) {
		CardFreeship freeShip = cardFreeShipSvc.getFreeshipData(card, lang);
		card.setFreeshipDesc(freeShip.getDesc());
	}

	/**
	 * 处理返回给微信端的用户卡数据
	 *
	 * @param card
	 */
	private void dealWithWxUserCard(WxAppUserCardVo card, String avatar) {
		card.calcCardIsExpired();
		card.calcRenewal();
		card.setAvatar(avatar);
		card.calcCash();
		
		// 背景
		CardBgBean bg = memberCardService.getBackground(card.getBgType(), card.getBgColor(), card.getBgImg());
		BeanUtils.copyProperties(bg, card);

		// 用户卡的有效时间
		setWxUserCardVoExpire(card);
		
		// 设置卡是否过期状态
		card.setStatus(CardUtil.getStatus(card.getExpireType(), card.getEndTime()));
	}
	
	/**
	 * 	设置WxUserCardVo的有效时间
	 */
	private void setWxUserCardVoExpire(WxAppUserCardVo card) {
		EffectTimeParam etParam = new EffectTimeParam();
		BeanUtils.copyProperties(card, etParam);
		etParam.setCreateTime(card.getUserCardCreateTime());
		EffectTimeBean etBean = CardUtil.getUserCardEffectTime(etParam);
		BeanUtils.copyProperties(etBean, card);
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

	public WxAppUserCardVo getUserCardDetail(UserCardParam param,String lang) throws UserCardNullException {
		logger().info("获取卡的详细信息");
		WxAppUserCardVo card = null;
		if(param.getCardId() != null) {
			card = (WxAppUserCardVo) userCardDao.getUserCardInfo(param.getUserId(),param.getCardId());
		}else {
			card = (WxAppUserCardVo) userCardDao.getUserCardInfo(param.getCardNo());
		}

		if (card == null) {
			throw new UserCardNullException();
		}

		dealWithUserCardDetailInfo(card);
		
		card.setCumulativeConsumptionAmounts(orderInfoService.getAllConsumpAmount(param.getUserId()));
		card.setCumulativeScore(scoreService.getAccumulationScore(param.getUserId()));
		card.setCardVerifyStatus(cardVerifyService.getCardVerifyStatus(param.getCardNo()));
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
		// TODO 开卡送卷
		setQrCode(card);

		if (CardUtil.isGradeCard(card.getCardType())) {

			NextGradeCardVo nextGradeCard = getNextGradeCard(card.getGrade());
			card.setNextGradeCard(nextGradeCard);
		}
		
		// 包邮信息
		dealWithFreeShipInfo(card,lang);
		// 自定义权益
		MemberCardRecord mCard = new MemberCardRecord();
		mCard.setCustomRights(card.getCustomRights());
		mCard.setCustomRightsFlag(card.getCustomRightsFlag());
		CardCustomRights customRights = memberCardService.getCustomRights(mCard);
		card.setCardCustomRights(customRights);
		return card;
	}

	private NextGradeCardVo getNextGradeCard(String currentGrade) {
		// 升级进度
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

	public MemberCardRecord getGradeCardByGrade(String grade) {
		return db().selectFrom(MEMBER_CARD)
				   .where(MEMBER_CARD.GRADE.eq(grade))
				   .and(MEMBER_CARD.DEL_FLAG.eq(MCARD_DF_NO))
				   .and(MEMBER_CARD.FLAG.equal(MCARD_FLAG_USING))
				   .fetchAny();
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
						resGoodsIds.add(cartGoods.getGoodsRecord().getSortId());
					}
					if (CardConstant.COUPLE_TP_PLAT.equals(k)) {
						resGoodsIds.add(cartGoods.getCartId());
					}
					if (CardConstant.COUPLE_TP_BRAND.equals(k)) {
						resGoodsIds.add(cartGoods.getGoodsRecord().getBrandId());
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
		}else {
            List<OrderMemberVo> temp = userCardDao.getOrderMembers(userId,
                new Byte[]{CardConstant.MCARD_TP_NORMAL, CardConstant.MCARD_TP_GRADE},
                OrderConstant.MEMBER_CARD_ONLINE);
            for (OrderMemberVo orderMemberVo : temp) {
               if(!defaultCards.get(0).getInfo().getCardId().equals(orderMemberVo.getInfo().getCardId())) {
                   defaultCards.add(orderMemberVo);
               }
            }
        }

		if (CollectionUtils.isEmpty(defaultCards)) {
			// 校验
			return Lists.newArrayList();
		}
		for (Iterator<OrderMemberVo> iterator = defaultCards.iterator(); iterator.hasNext();) {
			OrderMemberVo card = iterator.next();
			// 当前会员卡适用商品
			BigDecimal[] tolalNumberAndPrice = calculate.getTolalNumberAndPriceByType(bos,
					OrderConstant.D_T_MEMBER_CARD,
					DefaultMarketingProcess.builder().card(card).type(OrderConstant.D_T_MEMBER_CARD).build());
			if(CollectionUtils.isEmpty(card.getBos())) {
			    iterator.remove();
			    continue;
            }

			// 折扣金额
			BigDecimal discountAmount;
			// 判断门店（无门店||全部门店||部分门店）
			if (storeId == null || CardConstant.MCARD_STP_ALL.equals(Byte.valueOf(card.getInfo().getStoreList()))
					|| (CardConstant.MCARD_SUSE_OK.equals(card.getInfo().getStoreUseSwitch())
							&& Arrays.asList(card.getInfo().getStoreList().split(",")).contains(storeId.toString()))) {
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
			card.setIdentity(card.getInfo().getCardNo());
			card.initRatio();
            cards.add(card);
		}
		return cards;
	}

	/**
	 * 王帅 校验该商品是否可以打折
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
		return false;
	}

	/**
	 * 王帅 获取该卡打折金额
	 * @param card       会员卡
	 * @param totalPrice 折前总价
	 * @return 折后总价
	 */
	public BigDecimal getDiscountAmount(OrderMemberVo card, BigDecimal totalPrice) {
		if (CardConstant.MCARD_TP_LIMIT.equals(card.getInfo().getCardType())) {
			// 限次卡
			card.setTotalDiscount(totalPrice);
		} else if (BigDecimalUtil.compareTo(card.getInfo().getDiscount(), BigDecimal.ZERO) == -1) {
			// 如果没有打折权益则为十折
			card.setTotalDiscount(BigDecimal.ZERO);
		} else {
			// 正常打折(价格 * （10 - 折扣（eg:6.66） / 10）)
			card.setTotalDiscount(
			    BigDecimalUtil.multiplyOrDivideByMode(RoundingMode.DOWN,
					BigDecimalUtil.BigDecimalPlus.create(totalPrice, BigDecimalUtil.Operator.multiply),
					BigDecimalUtil.BigDecimalPlus.create(
							BigDecimalUtil.addOrSubtrac(
									BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, BigDecimalUtil.Operator.subtrac),
									BigDecimalUtil.BigDecimalPlus.create(card.getInfo().getDiscount(), null)),
							BigDecimalUtil.Operator.divide),
					BigDecimalUtil.BigDecimalPlus.create(BigDecimal.TEN, null))
            );
		}
		return card.getTotalDiscount();
	}

	/**
	 * 获取用户累积消费总额
	 * 
	 * @return 消费总额,默认为0
	 */
	public BigDecimal getUserTotalSpendAmount(Integer userId) {
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
	
	public UserCardJudgeVo userCardJudgement(UserIdAndCardIdParam param,String lang) {
		logger().info("用户卡判断");
		UserCardVo userCard = getUserCardJudge(param);
		MemberCardRecord mCard = cardDao.getCardById(param.getCardId());
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
				
					if (CardUtil.isCardFixTime(userCard.getExpireType())) {
						userCard.setStartDate(userCard.getStartTime().toLocalDateTime().toLocalDate());
						userCard.setEndDate(userCard.getEndTime().toLocalDateTime().toLocalDate());
					}
				} else {
					userCard.setStatus(1);
				}

				userCard.setShopAvatar(getCardAvatar());
				userCard.setScoreAmount(scoreService.getAccumulationScore(param.getUserId()));
				userCard.setPaidAmount(orderInfoService.getAllConsumpAmount(param.getUserId()));
				userCard.setBindMobile(shopCommonConfigService.getBindMobile());

				if(CardUtil.isLimitCard(userCard.getCardType()) && CardUtil.canExchangGoods(userCard.getIsExchang())) {
					logger().info("处理限次卡兑换的商品");
					boolean partGoodsFlag = CardConstant.MCARD_ISE_PART.equals(userCard.getIsExchang());
					if(partGoodsFlag) {
						if(!StringUtils.isBlank(userCard.getExchangGoods())) {
							List<Integer> goodsIdList = Util.splitValueToList(userCard.getExchangGoods());
							List<GoodsSmallVo> goodsList = goodsService.getGoodsList(goodsIdList, false);
							userCard.setGoodsList(goodsList);
						}else {
							userCard.setGoodsList(Collections.<GoodsSmallVo>emptyList());
						}
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

					if(userCard.getGoodsList()!=null) {
						logger().info("价格处理为两位小数");
						for(GoodsSmallVo goodsVo: userCard.getGoodsList()) {
							BigDecimal shopPrice = goodsVo.getShopPrice();
							goodsVo.setShopPrice(shopPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN));
						}
					}

					if(userCard.getExchangCount()==null) {
						userCard.setExchangCount(userCard.getExchangCount());
					}
				}
			// 处理限次兑换次数
			String cardNo = userCard.getCardNo();
			boolean toGetCard = StringUtils.isBlank(cardNo);
			// 没有领取卡取卡的配置，已经领卡取用户卡的快照信息
			if(CardUtil.isLimitCard(userCard.getCardType()) && toGetCard ) {
				// 门店兑换次数
				if(CardUtil.canUseInStore(userCard.getStoreUseSwitch())) {
					if(userCard.getSurplus()==null) {
						userCard.setSurplus(userCard.getCount());
					}
				}
				// 商品兑换次数
				if(CardUtil.canExchangGoods(userCard.getIsExchang())) {
					userCard.setExchangSurplus(userCard.getExchangCount());
				}
				
			}

			if(!StringUtil.isBlank(userCard.getStoreList()) && CardUtil.canUseInStore(userCard.getStoreUseSwitch())) {
				dealWithCardStore(userCard);
			}
			
			// 包邮信息
			dealWithJudgeFreeship(lang, userCard);
			// 自定义权益信息
			CardCustomRights customRights = memberCardService.getCustomRights(mCard);
			userCard.setCardCustomRights(customRights);
			
			
			logger().info("开卡送券");
			dealSendCouponInfo(userCard,lang);
			UserCardJudgeVo userCardJudgeVo = new UserCardJudgeVo();
			userCardJudgeVo.setStatus(1);
			
			// 卡的显示金额
			if(StringUtils.isBlank(userCard.getCardNo())) {
				if(userCard.getSendMoney() != null) {
					userCard.setMoney(BigDecimal.valueOf(userCard.getSendMoney()));
				}
			}
			
			// 有效时间
			setEffectTimeForJudgeCard(userCard);
			userCard.setUserId(param.getUserId());
			userCard.setCardId(param.getCardId());
			userCard.setStoreUseSwitch(CardUtil.getUseStoreType(userCard.getStoreUseSwitch(),userCard.getStoreList()));
			userCardJudgeVo.setCardInfo(userCard);
			return userCardJudgeVo;
		}else{
			UserCardVo uCard = getUserCardByCardNo(userCard.getCardNo());
			uCard.setIsGet(isGet);
			
			// 计算用户卡的有效时间
			EffectTimeParam tp = new EffectTimeParam();
			tp.setStartTime(uCard.getStartTime());
			tp.setEndTime(uCard.getEndTime());
			tp.setExpireTime(uCard.getExpireTime());
			tp.setExpireType(uCard.getExpireType());
			tp.setCreateTime(uCard.getUCreateTime());
			
			EffectTimeBean tB = CardUtil.getUserCardEffectTime(tp);
			if(CardUtil.isCardExpired(tB.getEndTime())) {
				logger().info("卡过期");
				uCard.setStatus(-1);
			}else {
				uCard.setStatus(1);
			}
			uCard.setStartDate(tB.getStartDate());
			uCard.setEndDate(tB.getEndDate());
			uCard.setExpireType(tB.getExpireType());
			
			uCard.setScoreAmount(scoreService.getAccumulationScore(param.getUserId()));
			uCard.setPaidAmount(orderInfoService.getAllConsumpAmount(param.getUserId()));
			if(CardUtil.isGradeCard(uCard.getCardType())) {
				// 升级进度条内容
				NextGradeCardVo nextGradeCard = getNextGradeCard(uCard.getGrade());
				uCard.setNext(nextGradeCard);
			}
			if(!CardUtil.isGradeCard(uCard.getCardType()) && !StringUtil.isBlank(uCard.getStoreList()) && CardUtil.canUseInStore(uCard.getStoreUseSwitch())) {
				dealWithCardStore(uCard);
			}
			// 会员卡头像

			uCard.setShopAvatar(getCardAvatar());
			// 背景图片
			logger().info("虚拟卡订单下单时间");
			VirtualOrderRecord order = virtualOrderService.getInfoByNo(uCard.getCardNo());
			if(order != null) {
				Timestamp buyTime = order.getCreateTime();
				uCard.setBuyTime(buyTime);
			}

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
			// 包邮信息
			dealWithJudgeFreeship(lang, uCard);
			// 自定义权益信息

			CardCustomRights customRights = memberCardService.getCustomRights(mCard);
			uCard.setCardCustomRights(customRights);
						
			dealSendCouponInfo(uCard,lang);
			UserCardJudgeVo userCardJudgeVo = new UserCardJudgeVo();
			userCardJudgeVo.setStatus(1);
			setEffectTimeForJudgeCard(uCard);
			uCard.setUserId(param.getUserId());
			uCard.setCardId(param.getCardId());
			uCard.setStoreUseSwitch(CardUtil.getUseStoreType(uCard.getStoreUseSwitch(),uCard.getStoreList()));
			userCardJudgeVo.setCardInfo(uCard);
			
			return userCardJudgeVo;
		}
	}


	private void dealWithJudgeFreeship(String lang, UserCardVo userCard) {
		logger().info("处理判断卡的包邮信息");
		if(StringUtils.isBlank(userCard.getCardNo())) {
			logger().info("取会员卡的包邮信息");
			MemberCardRecord mCardRec = new MemberCardRecord();
			mCardRec.setFreeshipLimit(userCard.getFreeshipLimit());
			mCardRec.setFreeshipNum(userCard.getFreeshipNum());
			CardFreeship freeshipData = cardFreeShipSvc.getFreeshipData(mCardRec, lang);
			userCard.setFreeshipDesc(freeshipData.getDesc());
		}else {
			logger().info("取用户卡的包邮信息");
			UserCardParam cardParam = new UserCardParam();
			cardParam.setUserId(userCard.getUserId());
			cardParam.setCardId(userCard.getCardId());
			cardParam.setFreeLimit(userCard.getFreeLimit());
			cardParam.setFreeNum(userCard.getFreeNum());
			CardFreeship freeshipData = cardFreeShipSvc.getFreeshipData(cardParam,lang);
			userCard.setFreeshipDesc(freeshipData.getDesc());
		}
	}

	private void dealWithCardStore(UserCardVo userCard) {
		logger().info("获取门店信息");
		// 门店使用类型
		Byte useStoreType = CardUtil.getUseStoreType(userCard.getStoreUseSwitch(), userCard.getStoreList());
		userCard.setStoreUseSwitch(useStoreType);
		// 门店Id
		List<Integer> storeIdList = CardUtil.parseStoreList(userCard.getStoreList());
		userCard.setStoreIdList(storeIdList);
		// 门店具体信息
		List<StoreBasicVo> storeList = storeService.getStoreListByStoreIds(storeIdList);
		userCard.setStoreInfoList(storeList);

	}

	/**
	 * 	设置有效时间
	 * @param userCard
	 */
	private void setEffectTimeForJudgeCard(UserCardVo userCard) {
		EffectTimeParam etParam = new EffectTimeParam();
		etParam.setStartTime(userCard.getStartTime());
		etParam.setEndTime(userCard.getEndTime());
		etParam.setCreateTime(userCard.getUCreateTime());
		etParam.setExpireTime(userCard.getExpireTime());
		etParam.setExpireType(userCard.getExpireType());
		EffectTimeBean etBean = CardUtil.getUserCardEffectTime(etParam);
		userCard.setStartDate(etBean.getStartDate());
		userCard.setStartTime(etBean.getStartTime());
		userCard.setEndDate(etBean.getEndDate());
		userCard.setEndTime(etBean.getEndTime());
		userCard.setExpireType(etBean.getExpireType());
	}
	
	/**
	 * 	处理会员卡相应的优惠券信息
	 * @param userCard
	 * @param lang
	 */
	public void dealSendCouponInfo(UserCardVo userCard, String lang) {
		logger().info("处理会员卡相应的优惠券信息");
		if(userCard == null) {
			return;
		}
		if (CardUtil.isSendCoupon(userCard.getSendCouponType())) {
			logger().info("正在生成优惠券信息");
			List<Integer> couponIds = CardUtil.parseCouponList(userCard.getSendCouponIds());
			if(couponIds == null || couponIds.size()==0) {
				return;
			}
			// 优惠券信息列表
			List<CouponView> couponList = couponService.getCouponViewByIds(couponIds);
			userCard.setCoupons(cardCouponI18N(lang, couponList));
		} else if (CardUtil.isSendCouponPack(userCard.getSendCouponType())) {
			logger().info("处理优惠券礼包");
			if (!StringUtils.isBlank(userCard.getSendCouponIds())) {
				int id = Integer.parseInt(userCard.getSendCouponIds());
				CouponPackUpdateVo couponPack = couponPackService.getCouponPackById(id);
				UserCardCouponPack pack = new UserCardCouponPack();
				if(couponPack != null) {
					pack = UserCardCouponPack.builder()
							.id(couponPack.getId())
							.actName(couponPack.getActName())
							.packName(couponPack.getPackName())
							.build();
				}
				userCard.setCouponPack(pack);
			}
		}
	}
	/**
	 * 	处理会员卡优惠券显示信息
	 */
	private List<UserCardCoupon> cardCouponI18N(String lang, List<CouponView> couponList) {
		logger().info("处理会员卡优惠券信息国际化");
		String i18nfile = "member";
		List<UserCardCoupon> res = new ArrayList<>();
		if(couponList == null || couponList.size()==0) {
			return res;
		}
		for (CouponView coupon : couponList) {
			// 1-优惠券使用范围
			String couponCon = null;
			if (NumberUtils.BYTE_ZERO.equals(coupon.getSuitGoods())) {
				// 全部商品可用
				couponCon = JsonResultMessage.CARD_COUPON_CON_ALL;
			} else {
				// 部分商品可用
				couponCon = JsonResultMessage.CARD_COUPON_CON_PART;
			}
			couponCon = Util.translateMessage(lang, couponCon, i18nfile);
			
			// 2-优惠券过期时间
			Integer day = coupon.getValidity();
			Integer hour = coupon.getValidityHour();
			Integer minute = coupon.getValidityMinute();
			String couponExpireTimeDesc = null;
			if (day > 0 || hour > 0 || minute > 0) {
				StringBuilder con = new StringBuilder();
				// 领券日起
				String receiveInfo = Util.translateMessage(lang, JsonResultMessage.CARD_COUPON_RECEIVE_DAY_START, i18nfile);
				con.append(receiveInfo);
				
				if (day> 0) {
					con.append(Util.translateMessage(lang, JsonResultMessage.CARD_COUPON_DAY, i18nfile, day));
				}
				if (hour > 0) {
					con.append(Util.translateMessage(lang, JsonResultMessage.CARD_COUPON_HOUR, i18nfile,hour));
				}
				if (minute > 0) {
					con.append(Util.translateMessage(lang, JsonResultMessage.CARD_COUPON_MINUTE, i18nfile,minute));
				}
				couponExpireTimeDesc = con.toString();
			} else {
				String startTime = coupon.getStartTime().toLocalDateTime().toLocalDate().toString();
				String endTime = coupon.getEndTime().toLocalDateTime().toLocalDate().toString();
				couponExpireTimeDesc = startTime + "--" + endTime;
			}
			
			// 3-优惠券使用条件限制
			String restrict = null;
			if (NumberUtils.INTEGER_ZERO.equals(coupon.getUseConsumeRestrict())) {
				restrict = Util.translateMessage(lang,JsonResultMessage.CARD_COUPON_NOLIMIT, i18nfile);
			} else {
				restrict = Util.translateMessage(lang, JsonResultMessage.CARD_COUPON_SATISFY, i18nfile, coupon.getLeastConsume());
			}
			res.add(
					UserCardCoupon.builder()
						.couponCondition(couponCon)
						.expireTime(couponExpireTimeDesc)
						.useConsumeRestrict(restrict)
						.build()
					);
		}
		return res;
	}
	
	/**
	 * 	领取会员卡
	 * @param param
	 * @return 带有卡号的卡信息
	 * @throws MpException
	 */
	public CardReceiveVo getCard(UserIdAndCardIdParam param) throws MpException {
		if(param.getCardId()==null) {
			return null;
		}
		CardReceiveVo vo = new CardReceiveVo();
		//	第一次领取
		boolean firstGet = true;
		if (param.getCardId() != null) {
			MemberCardRecord mCard = memberCardService.getCardById(param.getCardId());
			if(mCard == null) {
				return null;
			}
			if(CardUtil.isLimitCard(mCard.getCardType())) {
				logger().info("领取限次会员卡");
				cardUserOpt.setDecorate(limitCardOpt);
				String cardNo = cardUserOpt.handleSendCard(param.getUserId(), param.getCardId(), false);
				
				if(StringUtils.isBlank(cardNo)) {
					logger().info("领取失败");
					throw new CardReceiveFailException();
				}else {
					if (!limitCardOpt.canSendCard(param.getUserId(), param.getCardId())) {
						vo.setIsContinue("not_continue");
					}
					vo.setCardNo(cardNo);
					return vo;
				}
			}else if(CardUtil.isNormalCard(mCard.getCardType())) {
				logger().info("领取普通会员卡");
				cardUserOpt.setDecorate(normalCardOpt);
				String cardNo = cardUserOpt.handleSendCard(param.getUserId(), param.getCardId(), false);
				//	如果已经领取了该普通卡，不再次领取，返回拥有的会员卡号
				if(StringUtil.isNotBlank(cardNo)) {
					vo.setCardNo(cardNo);
				}else {
					cardNo = getCardNoByUserAndCardId(param.getUserId(), param.getCardId());
					if(StringUtil.isBlank(cardNo)) {
						logger().info("领取失败");
						throw new CardReceiveFailException();
					}
					vo.setCardNo(cardNo);
					return vo;
				}
			}else if(CardUtil.isGradeCard(mCard.getCardType())) {
				logger().info("领取等级会员卡");
				cardUserOpt.setDecorate(gradeCardOpt);
				String cardNo = cardUserOpt.handleSendCard(param.getUserId(), param.getCardId(), false);
				if(StringUtil.isBlank(cardNo)) {
					logger().info("领取失败");
					throw new CardReceiveFailException();
				}
				vo.setCardNo(cardNo);
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
			GradeConditionJson gradeCondition = Util.parseJson(newCard.getGradeCondition(), GradeConditionJson.class);
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

		if (cardId.equals(userGradeCard.getId())) {
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
	

	public Integer insertRow(UserCardRecord record) {
		return db().executeInsert(record);
	}

    /**
     * 获得可用的、余额大于0的普通会员卡
     * @param userId
     * @return
     */
	public List<GeneralUserCardVo> getCanUseGeneralCardList(Integer userId){
        List<GeneralUserCardVo> list = db().select(USER_CARD.CARD_NO,USER_CARD.EXPIRE_TIME,USER_CARD.MONEY,MEMBER_CARD.ID,MEMBER_CARD.BG_COLOR,MEMBER_CARD.CARD_NAME,MEMBER_CARD.DISCOUNT,MEMBER_CARD.BG_TYPE,MEMBER_CARD.BG_IMG,MEMBER_CARD.EXPIRE_TYPE,MEMBER_CARD.CARD_TYPE,MEMBER_CARD.START_TIME,MEMBER_CARD.END_TIME).
            from(USER_CARD).leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(USER_CARD.CARD_ID)).
            where(USER_CARD.USER_ID.eq(userId)).
            and(MEMBER_CARD.CARD_TYPE.eq(CardConstant.MCARD_TP_NORMAL)).
            and(USER_CARD.FLAG.eq(DelFlag.NORMAL_VALUE)).
            and(USER_CARD.EXPIRE_TIME.isNull().or(USER_CARD.EXPIRE_TIME.gt(DateUtil.getLocalDateTime()))).
            and(MEMBER_CARD.ACTIVATION.eq(CardConstant.MCARD_ACT_NO).or(USER_CARD.ACTIVATION_TIME.isNotNull().and(MEMBER_CARD.ACTIVATION.eq(CardConstant.MCARD_ACT_YES)))).
            and(USER_CARD.MONEY.gt(BigDecimal.ZERO)).
            fetchInto(GeneralUserCardVo.class);
        list.forEach(c->{
            if(StringUtil.isNotBlank(c.getBgImg())){
                c.setBgImg(domainConfig.imageUrl(c.getBgImg()));
            }
            if(c.getStartTime() != null && c.getEndTime() != null){
                c.setStartDate(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE,c.getStartTime()));
                c.setEndDate(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE,c.getEndTime()));
            }
        });
        return list;
    }
	
}

