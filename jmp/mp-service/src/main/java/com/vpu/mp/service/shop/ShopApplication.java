package com.vpu.mp.service.shop;


import com.vpu.mp.service.shop.order.MemberCardOrderService;
import com.vpu.mp.service.shop.overview.*;
import com.vpu.mp.service.shop.task.ShopTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.config.ShopBasicConfigService;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.decoration.AppletsJumpService;
import com.vpu.mp.service.shop.decoration.ChooseLinkService;
import com.vpu.mp.service.shop.decoration.PageClassificationService;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;
import com.vpu.mp.service.shop.distribution.DistributorGroupService;
import com.vpu.mp.service.shop.distribution.DistributorLevelService;
import com.vpu.mp.service.shop.distribution.RebateStrategyService;
import com.vpu.mp.service.shop.goods.GoodsRecommendService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageCategoryService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.market.bargain.BargainService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import com.vpu.mp.service.shop.member.TagService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.OrderWriteService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.summary.portrait.PortraitService;
import com.vpu.mp.service.shop.summary.visit.AmountService;
import com.vpu.mp.service.shop.summary.visit.DistributionService;
import com.vpu.mp.service.shop.summary.visit.PageService;
import com.vpu.mp.service.shop.summary.visit.RetainService;
import com.vpu.mp.service.shop.version.VersionService;

/**
 * 
 * @author 新国
 *
 */
@Service
public class ShopApplication  {

	@Autowired public GoodsService goods;
	@Autowired public ImageService image;
	@Autowired public ImageCategoryService imageCatgory;
	@Autowired public ShopMpDecorationService mpDecoration;	//装修模块
	@Autowired public OrderReadService readOrder;	//订单读操作
	@Autowired public OrderWriteService writeOrder;	//订单写操作
	@Autowired public PageClassificationService pageClassification;
	@Autowired public VersionService version;
	@Autowired public ConfigService config;
	@Autowired public StoreService store;
	@Autowired public ChooseLinkService chooselink;	//选择链接通用弹窗
	@Autowired public TradeService trade;
	@Autowired public GoodsRecommendService goodsRecommend;
	@Autowired public AmountService amount;
	@Autowired public OverviewService overview;
	@Autowired public DistributionService distribution;
	@Autowired public AppletsJumpService appletsJump;
	@Autowired public MallOverviewService mallOverview;
	@Autowired public CouponService coupon;	//优惠券管理
	@Autowired public RetainService retain;
	@Autowired public PageService page;
	@Autowired public RecordAdminActionService record;
	@Autowired public PortraitService portrait;
	@Autowired public RebateStrategyService rebateStrategy;
	@Autowired public RealTimeOverviewService realTimeOverview;
	@Autowired public DistributorGroupService distributorGroup; //分销员分组
	@Autowired public DistributorLevelService distributorLevel; //分销员等级配置
	@Autowired public MemberCardOrderService memberCardOrder;
	@Autowired public TransactionStatisticsService transactionService;

	@Autowired public ShopTaskService shopTaskService;
	
	/**
	 * 组团购
	 */
	@Autowired public GroupBuyService groupBuy;
	/**
	 * 会员
	 */
	@Autowired public MemberService member;
	/**
	 * 会员标签
	 */
	@Autowired public TagService tag;
	/**
	 * 积分管理
	 */
	@Autowired public ScoreCfgService score;

	@Autowired public ShopBasicConfigService shopBasicConfig;
	
	/**
	 * 砍价 
	 */
	@Autowired public BargainService bargain;
	@Autowired public CommodityStatisticsService statisticsService;


}
