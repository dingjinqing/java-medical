package com.vpu.mp.service.shop;

import com.vpu.mp.service.foundation.ServiceContainer;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.config.ShopBasicConfigService;
import com.vpu.mp.service.shop.config.TradeService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.decoration.AppletsJumpService;
import com.vpu.mp.service.shop.decoration.ChooseLinkService;
import com.vpu.mp.service.shop.decoration.MpDecorationService;
import com.vpu.mp.service.shop.decoration.PageClassificationService;
import com.vpu.mp.service.shop.distribution.RebateStrategyService;
import com.vpu.mp.service.shop.goods.BrandService;
import com.vpu.mp.service.shop.goods.GoodsRecommendService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.ImageCategoryService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.member.ScoreCfgService;
import com.vpu.mp.service.shop.member.TagService;
import com.vpu.mp.service.shop.operation.RecordAdminActionService;
import com.vpu.mp.service.shop.order.OrderService;
import com.vpu.mp.service.shop.overview.MallOverviewService;
import com.vpu.mp.service.shop.overview.OverviewService;
import com.vpu.mp.service.shop.store.store.StoreService;
import com.vpu.mp.service.shop.summary.portrait.PortraitService;
import com.vpu.mp.service.shop.summary.visit.AmountService;
import com.vpu.mp.service.shop.summary.visit.DistributionService;
import com.vpu.mp.service.shop.summary.visit.PageService;
import com.vpu.mp.service.shop.summary.visit.RetainService;
import com.vpu.mp.service.shop.version.VersionService;
import javafx.application.Application;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 新国
 *
 */
@Service
public class ShopApplication extends ServiceContainer {

	public GoodsService goods;
	public ImageService image;
	public ImageCategoryService imageCatgory;
	public MpDecorationService mpDecoration;	//装修模块
	public OrderService order;
	public PageClassificationService pageClassification;
	public VersionService version;
	public ConfigService config;
	public StoreService store;
	public ChooseLinkService chooselink;	//选择链接通用弹窗
	public TradeService trade;
	public GoodsRecommendService goodsRecommend;
	public AmountService amount;
	public OverviewService overview;
	public DistributionService distribution;
	public BrandService brand;	//品牌管理
	public AppletsJumpService appletsJump;
	public MallOverviewService mallOverview;
	public CouponService coupon;	//优惠券管理
	public RetainService retain;
	public PageService page;
	public RecordAdminActionService record;
	public PortraitService portrait;
	public RebateStrategyService rebateStrategy;
	/**
	 * 会员
	 */
	public MemberService member;
	/**
	 * 会员标签
	 */
	public TagService tag;
	/**
	 * 积分管理
	 */
	public ScoreCfgService score;

	public ShopBasicConfigService shopBasicConfig;

	@Override
	public void setShopId(Integer shopId){
		this.shopId = shopId;
	}

}
