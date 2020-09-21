package com.vpu.mp.service.shop;

import com.vpu.mp.service.shop.assess.AssessService;
import com.vpu.mp.service.shop.collection.CollectService;
import com.vpu.mp.service.shop.config.*;
import com.vpu.mp.service.shop.coupon.CouponMpService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.decoration.*;
import com.vpu.mp.service.shop.department.DepartmentService;
import com.vpu.mp.service.shop.distribution.*;
import com.vpu.mp.service.shop.doctor.DoctorService;
import com.vpu.mp.service.shop.doctor.DoctorStatisticService;
import com.vpu.mp.service.shop.express.ExpressService;
import com.vpu.mp.service.shop.goods.*;
import com.vpu.mp.service.shop.goods.es.EsDataUpdateMqService;
import com.vpu.mp.service.shop.goods.es.EsGoodsCreateService;
import com.vpu.mp.service.shop.goods.es.EsGoodsSearchService;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelCreateService;
import com.vpu.mp.service.shop.goods.es.goods.label.EsGoodsLabelSearchService;
import com.vpu.mp.service.shop.goods.es.goods.product.EsGoodsProductCreateService;
import com.vpu.mp.service.shop.goods.goodsimport.GoodsImportRecordService;
import com.vpu.mp.service.shop.goods.goodsimport.GoodsImportService;
import com.vpu.mp.service.shop.goods.mp.GoodsActivityShareRecordService;
import com.vpu.mp.service.shop.goods.mp.GoodsMpService;
import com.vpu.mp.service.shop.im.ImSessionService;
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
import com.vpu.mp.service.shop.market.groupdraw.GroupDrawService;
import com.vpu.mp.service.shop.market.increasepurchase.IncreasePurchaseService;
import com.vpu.mp.service.shop.market.integralconvert.IntegralConvertService;
import com.vpu.mp.service.shop.market.integration.GroupIntegrationService;
import com.vpu.mp.service.shop.market.live.LiveService;
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
import com.vpu.mp.service.shop.market.sharereward.WxShareRewardService;
import com.vpu.mp.service.shop.marketcalendar.MarketCalendarService;
import com.vpu.mp.service.shop.medicine.MedicalHistoryService;
import com.vpu.mp.service.shop.member.*;
import com.vpu.mp.service.shop.message.UserMessageService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.operation.RecordTradeService;
import com.vpu.mp.service.shop.order.OrderApiService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.OrderWriteService;
import com.vpu.mp.service.shop.order.action.AuditService;
import com.vpu.mp.service.shop.order.action.base.OrderOperateFactory;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.order.inquiry.InquiryOrderService;
import com.vpu.mp.service.shop.order.virtual.CouponPackOrderService;
import com.vpu.mp.service.shop.order.virtual.MemberCardOrderService;
import com.vpu.mp.service.shop.overview.*;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.payment.PaymentService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import com.vpu.mp.service.shop.question.FeedbackService;
import com.vpu.mp.service.shop.rebate.DoctorWithdrawService;
import com.vpu.mp.service.shop.rebate.InquiryOrderRebateService;
import com.vpu.mp.service.shop.rebate.PrescriptionRebateService;
import com.vpu.mp.service.shop.recommend.RecommendService;
import com.vpu.mp.service.shop.store.statistic.StoreOrderSummaryTrendService;
import com.vpu.mp.service.shop.store.store.StoreGoodsService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.summary.portrait.PortraitService;
import com.vpu.mp.service.shop.summary.visit.AmountService;
import com.vpu.mp.service.shop.summary.visit.DistributionService;
import com.vpu.mp.service.shop.summary.visit.PageService;
import com.vpu.mp.service.shop.summary.visit.RetainService;
import com.vpu.mp.service.shop.task.ShopTaskService;
import com.vpu.mp.service.shop.task.department.DepartmentTaskService;
import com.vpu.mp.service.shop.task.doctor.DoctorTaskService;
import com.vpu.mp.service.shop.task.order.InquiryOrderTaskService;
import com.vpu.mp.service.shop.task.prescription.PrescriptionTaskService;
import com.vpu.mp.service.shop.task.store.StoreTaskService;
import com.vpu.mp.service.shop.title.TitleService;
import com.vpu.mp.service.shop.user.cart.CartService;
import com.vpu.mp.service.shop.user.message.MessageRecordService;
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
    public GoodsWrapService goodsWrap;
	@Autowired
	public GoodsMpService goodsMp;
    @Autowired
    public GoodsImportService goodsImportService;
    @Autowired
    public GoodsImportRecordService goodsImportRecordService;
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
	public MpDecorationService mpDecoration;
    @Autowired
    public AdminDecorationService adminDecoration;
    @Autowired
    public UserMessageService userMessageService;

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
     * 订单业务操作
     */
	@Autowired
	public OrderInfoService orderInfoService;
    /**
     * 订单对外统一接口
     */
    @Autowired
    public OrderApiService orderApi;
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
     * 门店商品
     */
    @Autowired
    public StoreGoodsService storeGoodsService;
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
    @Autowired
    public CouponMpService mpCoupon; //小程序端优惠券
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
     * 分销提现
     */
    @Autowired
    public WithdrawService withdrawService;
    /**
     * mp分销service
     */
    @Autowired
    public MpDistributionService  mpDistribution;
    @Autowired
    public MpDistributorLevelService mpDisLevel;
    /**分销商品service*/
    @Autowired
    public MpDistributionGoodsService mpDisGoods;
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

    @Autowired
    public StoreTaskService storeTaskService;

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
    public EsGoodsProductCreateService esGoodsProductCreateService;

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
	 * 小程序订阅消息
	 */
	@Autowired
	public SubscribeMessageService subservice;

    @Autowired
    public EnterPolitelyService enterPolitelyService;

    /**
     * 小程序端分享有礼
     */
    @Autowired
    public WxShareRewardService wxShareReward;

    /**
     * 小程序公众号短信发送记录表
     */
    @Autowired
    public MessageRecordService msgRecordService;
    /**
     * 第三方对接配置
     */
    @Autowired
    public ThirdAuthConfigService thirdAuthService;

    /**
     * 第三方对接
     */
    @Autowired
    public ApiGoodsService apiGoodsService;

    @Autowired
    public FeedbackService feedbackService;

    /**
     * 小程序直播
     */
    @Autowired
    public LiveService liveService;
    /**
     * 营销日历
     */
    @Autowired
    public MarketCalendarService calendarService;

    /**
     * 科室
     */
    @Autowired
    public DepartmentService departmentService;

    /**
     * 职称
     */
    @Autowired
    public TitleService titleService;

    /**
     * 医师
     */
    @Autowired
    public DoctorService doctorService;

    /**
     * 患者
     */
    @Autowired
    public PatientService patientService;

    /**
     * 药品
     */
    @Autowired
    public MedicalGoodsService medicalGoodsService;

    /**
     * 病历
     */
    @Autowired
    public MedicalHistoryService medicalHistoryService;

    @Autowired
    public AuditService auditService;

    /**
     * 处方
     */
    @Autowired
    public PrescriptionService prescriptionService;

    /**
     * 问诊订单
     */
    @Autowired
    public InquiryOrderService inquiryOrderService;
    /**
     * 问诊订单任务
     */
    @Autowired
    public InquiryOrderTaskService inquiryOrderTaskService;

    /**
     * 商品
     */
    @Autowired
    public GoodsService goodsService;
    /**
     * 处方任务
     */
    @Autowired
    public PrescriptionTaskService prescriptionTaskService;
    /**咨询聊天*/
    @Autowired
    public ImSessionService imSessionService;
    /**
     * 处方返利
     */
    @Autowired
    public PrescriptionRebateService prescriptionRebateService;
    /**
     * 咨询返利
     */
    @Autowired
    public InquiryOrderRebateService inquiryOrderRebateService;
    /**
     * 医师提现
     */
    @Autowired
    public DoctorWithdrawService doctorWithdrawService;
    /**
     * 门店订单统计
     */
    @Autowired
    public StoreOrderSummaryTrendService storeSummary;

    /**
     * 科室信息统计
     */
    @Autowired
    public DepartmentTaskService departmentTaskService;

    /**
     * 医师业绩统计
     */
    @Autowired
    public DoctorTaskService doctorTaskService;

    /**
     * 医师数据统计
     */
    @Autowired
    public DoctorStatisticService doctorStatisticService;

    /**
     * 热销商品数据统计
     */
    @Autowired
    public GoodsAnalysisService goodsAnalysisService;
}
