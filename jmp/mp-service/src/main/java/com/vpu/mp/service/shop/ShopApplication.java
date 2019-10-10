package com.vpu.mp.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.shop.assess.AssessService;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.config.ShopBasicConfigService;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.config.WxShoppingListConfigService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.decoration.AppletsJumpService;
import com.vpu.mp.service.shop.decoration.ChooseLinkService;
import com.vpu.mp.service.shop.decoration.PageClassificationService;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;
import com.vpu.mp.service.shop.distribution.BrokerageStatisticalService;
import com.vpu.mp.service.shop.distribution.DistributorCheckService;
import com.vpu.mp.service.shop.distribution.DistributorGroupService;
import com.vpu.mp.service.shop.distribution.DistributorLevelService;
import com.vpu.mp.service.shop.distribution.DistributorListService;
import com.vpu.mp.service.shop.distribution.DistributorWithdrawService;
import com.vpu.mp.service.shop.distribution.PromotionLanguageService;
import com.vpu.mp.service.shop.distribution.RebateGoodsService;
import com.vpu.mp.service.shop.distribution.RebateStrategyService;
import com.vpu.mp.service.shop.goods.GoodsRecommendService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageCategoryService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.market.activity.ActivityIssueService;
import com.vpu.mp.service.shop.market.activity.ActivityService;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import com.vpu.mp.service.shop.market.channel.ChannelService;
import com.vpu.mp.service.shop.market.channel.ChannelStatisticalService;
import com.vpu.mp.service.shop.market.commentaward.CommentAwardService;
import com.vpu.mp.service.shop.market.couponpack.CouponPackService;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import com.vpu.mp.service.shop.market.form.FormStatisticsService;
import com.vpu.mp.service.shop.market.freeshipping.FreeShippingService;
import com.vpu.mp.service.shop.market.friendpromote.FriendPromoteService;
import com.vpu.mp.service.shop.market.fullcut.MrkingStrategyService;
import com.vpu.mp.service.shop.market.gift.GiftService;
import com.vpu.mp.service.shop.market.givegift.GiveGiftService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawGroupService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawInviteService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawJoinUserService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawOrderService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawService;
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawUserService;
import com.vpu.mp.service.shop.market.increasepurchase.IncreasePurchaseService;
import com.vpu.mp.service.shop.market.integralconvert.IntegralConvertService;
import com.vpu.mp.service.shop.market.integration.GroupIntegrationService;
import com.vpu.mp.service.shop.market.lottery.LotteryService;
import com.vpu.mp.service.shop.market.message.MessageTemplateService;
import com.vpu.mp.service.shop.market.packagesale.PackSaleService;
import com.vpu.mp.service.shop.market.payreward.PayRewardService;
import com.vpu.mp.service.shop.market.presale.PreSaleOrderService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import com.vpu.mp.service.shop.market.sharereward.ShareRewardService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import com.vpu.mp.service.shop.member.TagService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.operation.RecordMemberTradeService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.OrderWriteService;
import com.vpu.mp.service.shop.order.action.ReturnService;
import com.vpu.mp.service.shop.order.action.base.OrderOperateFactory;
import com.vpu.mp.service.shop.order.virtual.CouponPackOrderService;
import com.vpu.mp.service.shop.order.virtual.MemberCardOrderService;
import com.vpu.mp.service.shop.overview.AssetManagementService;
import com.vpu.mp.service.shop.overview.CommodityStatisticsService;
import com.vpu.mp.service.shop.overview.MallOverviewService;
import com.vpu.mp.service.shop.overview.OverviewService;
import com.vpu.mp.service.shop.overview.RealTimeOverviewService;
import com.vpu.mp.service.shop.overview.TransactionStatisticsService;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.summary.portrait.PortraitService;
import com.vpu.mp.service.shop.summary.visit.AmountService;
import com.vpu.mp.service.shop.summary.visit.DistributionService;
import com.vpu.mp.service.shop.summary.visit.PageService;
import com.vpu.mp.service.shop.summary.visit.RetainService;
import com.vpu.mp.service.shop.task.ShopTaskService;
import com.vpu.mp.service.shop.user.message.WechatMessageTemplateService;
import com.vpu.mp.service.shop.user.user.MpOfficialAccountUserByShop;
import com.vpu.mp.service.shop.user.user.UserLoginRecordService;
import com.vpu.mp.service.shop.user.user.UserService;
import com.vpu.mp.service.shop.version.VersionService;
import com.vpu.mp.service.shop.video.VideoService;

/**
 * 
 * @author 新国
 *
 */
@Service
public class ShopApplication {

	@Autowired
	public GoodsService goods;
	@Autowired
	public ImageService image;
	@Autowired
	public QrCodeService qrCode;
	@Autowired
	public ImageCategoryService imageCatgory;
	@Autowired
	public VideoService video;

	@Autowired
	public ShopMpDecorationService mpDecoration;
	/**
	 * 订单普通查询
	 */
	@Autowired
	public OrderReadService readOrder;
	/**
	 * 订单普通写操作
	 */
	@Autowired
	public OrderWriteService writeOrder;
	/**
	 * 订单状态查询、操作(目前支持发货、退货)
	 */
	@Autowired
	public OrderOperateFactory orderActionFactory;
	@Autowired
	public PageClassificationService pageClassification;
	@Autowired
	public VersionService version;
	@Autowired
	public ConfigService config;
	@Autowired
	public StoreService store;
	@Autowired
	public ChooseLinkService chooselink;
	@Autowired
	public TradeService trade;
	@Autowired
	public GoodsRecommendService goodsRecommend;
	@Autowired
	public AmountService amount;
	@Autowired
	public OverviewService overview;
	@Autowired
	public DistributionService distribution;
	@Autowired
	public AppletsJumpService appletsJump;
	@Autowired
	public MallOverviewService mallOverview;
    /**
     * 优惠券管理
     */
	@Autowired
	public CouponService coupon;
	@Autowired
	public RetainService retain;
	@Autowired
	public PageService page;
	@Autowired
	public RecordAdminActionService record;
	@Autowired
	public PortraitService portrait;
	@Autowired
	public RebateStrategyService rebateStrategy;
	@Autowired
	public RealTimeOverviewService realTimeOverview;
    /**
     * 分销员分组
     */
	@Autowired
	public DistributorGroupService distributorGroup;
    /**
     * 分销员等级配置
     */
	@Autowired
	public DistributorLevelService distributorLevel;
    /**
     * 分销员列表
     */
    @Autowired
	public DistributorListService distributorList;
    /**
     * 佣金统计
     */
    @Autowired
	public BrokerageStatisticalService brokerage;
    /**
     * 返利商品统计
     */
    @Autowired
	public RebateGoodsService rebateGoods;
    /**
     * 分销员审核
     */
    @Autowired
   	public DistributorCheckService distributorCheck;
    /**
     * 分销推广语
     */
    @Autowired
	public PromotionLanguageService promotionLanguage;
    /**
     * 分销提现
     */
    @Autowired
	public DistributorWithdrawService withdraw;
	@Autowired
	public MemberCardOrderService memberCardOrder;
	@Autowired
	public TransactionStatisticsService transactionService;
	@Autowired
	public CouponPackOrderService couponPackOrder;
    @Autowired public AssessService assess;
	/**
	 * 满免包邮
	 */
	@Autowired
	public FreeShippingService freeShipping;
	/**
	 * 幸运抽奖
	 */
	@Autowired
	public LotteryService lottery;

	@Autowired
	public ShopTaskService shopTaskService;

	/**
	 * 组团购
	 */
	@Autowired
	public GroupBuyService groupBuy;
	/**
	 * 会员
	 */
	@Autowired
	public MemberService member;
	/**
	 * 会员标签
	 */
	@Autowired
	public TagService tag;
	/**
	 * 积分管理
	 */
	@Autowired
	public ScoreCfgService score;

	@Autowired
	public ShopBasicConfigService shopBasicConfig;

	/**
	 * 砍价
	 */
	@Autowired
	public BargainService bargain;

	/**
	 * 秒杀
	 */
	@Autowired
	public SeckillService seckill;

	@Autowired
	public CommodityStatisticsService statisticsService;
	@Autowired
	public AssetManagementService assetService;

	/**
	 * 拼团抽奖
	 */
	@Autowired
	public GroupDrawService groupDraw;
	@Autowired
	public GroupDrawJoinUserService groupDrawUsers;
	@Autowired
	public GroupDrawOrderService groupDrawOrders;
	@Autowired
	public GroupDrawGroupService groupDrawGroups;
	@Autowired
	public GroupDrawInviteService groupDrawInvite;
	@Autowired
	public GroupDrawUserService groupDrawUser;

	/**
	 * 瓜分积分
	 */
	@Autowired
	public GroupIntegrationService groupIntegration;
	/**
	 * 微信好物圈配置
	 */
	@Autowired
	public WxShoppingListConfigService shoppingListConfig;

	/**
	 * 好友助力活动
	 */
	@Autowired
	public FriendPromoteService friendPromoteService;

	@Autowired
	public FormStatisticsService formService;

	@Autowired
	public UserService user;
	
	@Autowired
	public UserLoginRecordService userLoginRecordService;

	/**
	 * 活动有礼
	 */
	@Autowired
	public ActivityService activity;
	@Autowired
	public ActivityIssueService issue;

	/**
	 * 满折满减
	 */
	@Autowired
	public MrkingStrategyService mrkingStrategy;

	/**
	 * 支付有礼
	 */
	@Autowired
	public PayRewardService payReward;

	/**
	 * 我要送礼
	 */
	@Autowired public GiveGiftService giveGift;
    /**
     * 定金膨胀
     */
    @Autowired public PreSaleService preSale;

	@Autowired
	public PreSaleOrderService preSaleOrder;

	/**
	 * 打包一口价
	 */
	@Autowired
	public PackSaleService packSale;

    /**
     * 限时降价
     */
    @Autowired public ReducePriceService reducePrice;

	/**
	 * 小程序跳转
	 */
	@Autowired
  	protected AppletsJumpService appletsJumpService;

	/** 加价购 */
	@Autowired
	public IncreasePurchaseService increaseService;

	/**
	 * 首单特惠
	 */
	@Autowired
	public FirstSpecialService firstSpecial;
	/**
	 * 积分兑换
	 */
	@Autowired
	public IntegralConvertService integralConvertService;

    /** 渠道页面分析 */
    @Autowired public ChannelService channelService;
    @Autowired public ChannelStatisticalService channelStatitical;
	@Autowired
	public PaymentService pay;

    /**
     * 赠品
     */
    @Autowired
    public GiftService gift;

	@Autowired public ShareRewardService shareRewardService;

	/**
	 * 评价有礼
	 */
	@Autowired
    public CommentAwardService commentAward;

    /**
     * 优惠券礼包
     */
    @Autowired
    public CouponPackService couponPack;
    /**
     * 消息推送
     */
    @Autowired
    public MessageTemplateService messageTemplateService;
    /**
     * 微信小程序/公众号模版消息
     */
    @Autowired
    public WechatMessageTemplateService wechatMessageTemplateService;
    /**
     * 交易明细
     */
    @Autowired
    public RecordMemberTradeService tradeService;

    /**
     * 店铺库的公众号用户
     */
	@Autowired
	public MpOfficialAccountUserByShop officialAccountUser;
}
