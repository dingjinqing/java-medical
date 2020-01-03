package com.vpu.mp.service.shop;

import com.vpu.mp.service.shop.assess.AssessService;
import com.vpu.mp.service.shop.collection.CollectService;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.config.ShopBasicConfigService;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.config.WxShoppingListConfigService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.coupon.MpCouponService;
import com.vpu.mp.service.shop.decoration.AppletsJumpService;
import com.vpu.mp.service.shop.decoration.ChooseLinkService;
import com.vpu.mp.service.shop.decoration.PageClassificationService;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;
import com.vpu.mp.service.shop.distribution.*;
import com.vpu.mp.service.shop.express.ExpressService;
import com.vpu.mp.service.shop.goods.FootPrintService;
import com.vpu.mp.service.shop.goods.GoodsRecommendService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.es.EsDataUpdateMqService;
import com.vpu.mp.service.shop.goods.es.EsGoodsCreateService;
import com.vpu.mp.service.shop.goods.es.EsGoodsSearchService;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelCreateService;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelSearchService;
import com.vpu.mp.service.shop.goods.mp.GoodsActivityShareRecordService;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;
import com.vpu.mp.service.shop.image.ImageCategoryService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.image.postertraits.PictorialIntegrationService;
import com.vpu.mp.service.shop.image.postertraits.UserCenterTraitService;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import com.vpu.mp.service.shop.market.channel.ChannelService;
import com.vpu.mp.service.shop.market.channel.ChannelStatisticalService;
import com.vpu.mp.service.shop.market.commentaward.CommentAwardService;
import com.vpu.mp.service.shop.market.coopen.CoopenRecordService;
import com.vpu.mp.service.shop.market.coopen.CoopenService;
import com.vpu.mp.service.shop.market.coopen.EnterPolitelyService;
import com.vpu.mp.service.shop.market.couponpack.CouponPackService;
import com.vpu.mp.service.shop.market.firstspecial.FirstSpecialService;
import com.vpu.mp.service.shop.market.form.FormStatisticsService;
import com.vpu.mp.service.shop.market.freeshipping.FreeShippingService;
import com.vpu.mp.service.shop.market.friendpromote.FriendPromoteService;
import com.vpu.mp.service.shop.market.fullcut.MrkingStrategyService;
import com.vpu.mp.service.shop.market.gift.GiftService;
import com.vpu.mp.service.shop.market.givegift.GiveGiftService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyListService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.market.groupdraw.*;
import com.vpu.mp.service.shop.market.increasepurchase.IncreasePurchaseService;
import com.vpu.mp.service.shop.market.integralconvert.IntegralConvertService;
import com.vpu.mp.service.shop.market.integration.GroupIntegrationService;
import com.vpu.mp.service.shop.market.lottery.LotteryService;
import com.vpu.mp.service.shop.market.message.MessageTemplateService;
import com.vpu.mp.service.shop.market.packagesale.PackSaleService;
import com.vpu.mp.service.shop.market.payaward.PayAwardService;
import com.vpu.mp.service.shop.market.presale.PreSaleOrderService;
import com.vpu.mp.service.shop.market.presale.PreSaleService;
import com.vpu.mp.service.shop.market.prize.PrizeRecordService;
import com.vpu.mp.service.shop.market.reduceprice.ReducePriceService;
import com.vpu.mp.service.shop.market.seckill.SeckillService;
import com.vpu.mp.service.shop.market.sharereward.ShareRewardService;
import com.vpu.mp.service.shop.member.*;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.operation.RecordTradeService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.OrderWriteService;
import com.vpu.mp.service.shop.order.action.base.OrderOperateFactory;
import com.vpu.mp.service.shop.order.virtual.CouponPackOrderService;
import com.vpu.mp.service.shop.order.virtual.MemberCardOrderService;
import com.vpu.mp.service.shop.overview.*;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.recommend.RecommendService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.summary.portrait.PortraitService;
import com.vpu.mp.service.shop.summary.visit.AmountService;
import com.vpu.mp.service.shop.summary.visit.DistributionService;
import com.vpu.mp.service.shop.summary.visit.PageService;
import com.vpu.mp.service.shop.summary.visit.RetainService;
import com.vpu.mp.service.shop.task.ShopTaskService;
import com.vpu.mp.service.shop.user.cart.CartService;
import com.vpu.mp.service.shop.user.message.SubscribeMessageService;
import com.vpu.mp.service.shop.user.message.WechatMessageTemplateService;
import com.vpu.mp.service.shop.user.user.MpOfficialAccountUserByShop;
import com.vpu.mp.service.shop.user.user.UserLoginRecordService;
import com.vpu.mp.service.shop.user.user.UserService;
import com.vpu.mp.service.shop.version.VersionService;
import com.vpu.mp.service.shop.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public GoodsMpService goodsMp;
	@Autowired
	public GoodsActivityShareRecordService goodsActivityShareRecord;
	@Autowired
	public ImageService image;
	@Autowired
	public QrCodeService qrCode;
	@Autowired
	public PictorialIntegrationService pictorialIntegrationService;
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
	 * 订单状态查询、操作(目前支持发货、退货等)
	 */
	@Autowired
	public OrderOperateFactory orderActionFactory;
	/**快递公司*/
	@Autowired
    public ExpressService express;
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
    /**
     * mp分销service
     */
    @Autowired
    public MpDistributionService  mpDistribution;
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
	@Autowired
	public GroupBuyListService groupBuyList;
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

	/**
	 * 会员用户-持卡服务
	 */
	@Autowired
	public UserCardService userCard;
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
	public CoopenService coopen;
	@Autowired
	public CoopenRecordService coopenRecord;

	/**
	 * 满折满减
	 */
	@Autowired
	public MrkingStrategyService mrkingStrategy;

	/**
	 * 支付有礼
	 */
	@Autowired
	public PayAwardService payAward;

	/**
	 * 我要送礼
	 */
	@Autowired public GiveGiftService giveGift;
	/**
	 * 奖品记录
	 */
	@Autowired
	public PrizeRecordService prizeRecord;
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
    public RecordTradeService recordTradeService;

    /**
     * 店铺库的公众号用户
     */
	@Autowired
	public MpOfficialAccountUserByShop officialAccountUser;
	/**
	 * 商品收藏
	 */
	@Autowired
	public CollectService collect;

	/**
	 * 购物车
	 */
	@Autowired
	public CartService cart;

    @Autowired
    public EsGoodsCreateService esGoodsCreateService;
    @Autowired
    public EsDataUpdateMqService esDataUpdateMqService;
    @Autowired
    public EsGoodsSearchService esGoodsSearchService;
    @Autowired
    public EsGoodsLabelCreateService esGoodsLabelCreateService;
    @Autowired
    public EsGoodsLabelSearchService esGoodsLabelSearchService;

    @Autowired
    public UserCenterTraitService  ucTraitService;
	/**
	 * 足迹
	 */
	@Autowired
    public FootPrintService footPrintService;
	/**
	 * 用户卡审核
	 */
	@Autowired
	public CardVerifyService cardVerifyService;

	/**
	 * 好物圈相关接口
	 */
	@Autowired
	public RecommendService recommendService;

	/**
	 * 用户地址
	 */
	@Autowired
	public AddressService addressService;

	/**
	 * 小程序优惠券
	 */
	@Autowired
	public MpCouponService mpCoupon;

	/**
	 * 小程序订阅消息
	 */
	@Autowired
	public SubscribeMessageService subservice;

    @Autowired
    public EnterPolitelyService enterPolitelyService;
}
