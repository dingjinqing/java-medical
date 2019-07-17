package com.vpu.mp.service.shop.overview;

import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisDateParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisPageParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisPageVo;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectParam;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisSelectVo;
import com.vpu.mp.service.pojo.shop.overview.analysis.OverviewAnalysisYesterdayVo;

import static com.vpu.mp.db.shop.Tables.MP_DAILY_VISIT;
import static com.vpu.mp.db.shop.Tables.MP_SUMMARY_TREND;
import static com.vpu.mp.db.shop.Tables.MP_VISIT_PAGE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jooq.TableField;
import org.jooq.impl.DSL;


/**
 * 概况统计
 * 
 * @author liangchen
 * @date 2019年7月15日
 */
public class OverviewAnalysisService extends BaseService {

	/**
	 * 查询昨日概况
	 * 
	 * @param param
	 * @return List<OverviewAnalysisYesterdayVo>
	 */
	
	public List<OverviewAnalysisYesterdayVo> yesterdayAnalysis(OverviewAnalysisDateParam param) {

		List<OverviewAnalysisYesterdayVo> overviewAnalysisYesterdayVos = db()
				.select(MP_DAILY_VISIT.SESSION_CNT, MP_DAILY_VISIT.VISIT_PV, MP_DAILY_VISIT.VISIT_UV,
						MP_DAILY_VISIT.VISIT_UV_NEW, MP_SUMMARY_TREND.SHARE_PV, MP_SUMMARY_TREND.SHARE_UV)
				.from(MP_DAILY_VISIT, MP_SUMMARY_TREND).where(MP_DAILY_VISIT.REF_DATE.eq(param.getDateNowStr()))
				.and(MP_SUMMARY_TREND.REF_DATE.eq(param.getDateYesterdayStr())).fetchInto(OverviewAnalysisYesterdayVo.class);

		return overviewAnalysisYesterdayVos;

	}
	
	/**
	 *折线图综合概况统计
	 *@Param param
	 *@return
	 */
	public List<OverviewAnalysisSelectVo> getSelect(OverviewAnalysisSelectParam param) {
		
		TableField<?,?> numCondition = MP_DAILY_VISIT.SESSION_CNT;
		
		numCondition = param.getSessionCnt()!=null ? MP_DAILY_VISIT.SESSION_CNT: numCondition;
		numCondition = param.getVisitPv()!=null ? MP_DAILY_VISIT.VISIT_PV : numCondition;
		numCondition = param.getVisitUv()!=null ? MP_DAILY_VISIT.VISIT_UV: numCondition;
		numCondition = param.getSharePv()!=null ? MP_SUMMARY_TREND.SHARE_PV: numCondition;
		numCondition = param.getShareUv()!=null ? MP_SUMMARY_TREND.SHARE_UV: numCondition;
		numCondition = param.getVisitUvNew()!=null ? MP_DAILY_VISIT.VISIT_UV_NEW : numCondition;
		numCondition = param.getStayTimeUv()!=null ? MP_DAILY_VISIT.STAY_TIME_UV: numCondition;
		numCondition = param.getStayTimeSession()!=null ? MP_DAILY_VISIT.STAY_TIME_SESSION : numCondition;
		
		List<OverviewAnalysisSelectVo> overviewAnalysisSelectVos;
		if(param.getShareUv()!=null||param.getSharePv()!=null) {
			overviewAnalysisSelectVos = 
					db().select(MP_SUMMARY_TREND.REF_DATE,numCondition.as("num"))
						.from(MP_SUMMARY_TREND)
						.where(MP_SUMMARY_TREND.REF_DATE.between(param.getStartTime(), param.getEndTime()))
						.fetchInto(OverviewAnalysisSelectVo.class);
		} else if(param.getTotalSessionCnt()!=null){
			overviewAnalysisSelectVos = 
		//* 累计访问人数功能未实现 */
					db().select(DSL.sum(MP_DAILY_VISIT.SESSION_CNT).as("num"))
						.from(MP_DAILY_VISIT)
						.where(MP_DAILY_VISIT.REF_DATE.greaterOrEqual(param.getStartTime()))
						.fetchInto(OverviewAnalysisSelectVo.class);
			
		}else {
			overviewAnalysisSelectVos = 
					db().select(MP_DAILY_VISIT.REF_DATE,numCondition.as("num"))
						.from(MP_DAILY_VISIT)
						.where(MP_DAILY_VISIT.REF_DATE.between(param.getStartTime(), param.getEndTime()))
						.fetchInto(OverviewAnalysisSelectVo.class);
		}
		
		return overviewAnalysisSelectVos;
	}
	
	/**
	 *页面访问次数统计
	 *@Param param
	 *@return
	 */
	public List<OverviewAnalysisPageVo> getPageInfo(OverviewAnalysisPageParam param) {
		
		List<OverviewAnalysisPageVo> overviewAnalysisPageVos;
		overviewAnalysisPageVos = 
				db().select(MP_VISIT_PAGE.PAGE_PATH,
						DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_PV).as("pageVisitPv"))
				.from(MP_VISIT_PAGE)
				.where(MP_VISIT_PAGE.REF_DATE.between(param.getStartTime(), param.getEndTime()))
				.groupBy(MP_VISIT_PAGE.PAGE_PATH)
				.fetchInto(OverviewAnalysisPageVo.class);
		int total = 
				db().select(
						DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_PV).as("total"))
				.from(MP_VISIT_PAGE)
				.where(MP_VISIT_PAGE.REF_DATE.between(param.getStartTime(), param.getEndTime()))
				.fetchInto(Integer.class).get(0);
		for(OverviewAnalysisPageVo overviewAnalysisPageVo:overviewAnalysisPageVos) {
			overviewAnalysisPageVo.setPageName(pageNameOf(overviewAnalysisPageVo.getPagePath()));
			overviewAnalysisPageVo.setRate(((double)overviewAnalysisPageVo.getPageVisitPv()/(double)total));
		}
		
		
		return overviewAnalysisPageVos;
	}

	
	 /**
     * 获取路径对应的页面名称
     */
    private String pageNameOf(String pagePath) {
        return Optional.ofNullable(pageMap().get(pagePath)).orElse("未知");
    }

    /**
     * 路径和页面名称对应关系
     */
    private Map<String, String> pageMap() {
        return new HashMap<String, String>(200) {{
            put("pages/index/index", "首页");put("pages/storeinfo/storeinfo", "门店");
            put("pages/usercenter/usercenter", "个人中心");put("pages/bottom/bottom", "底部导航");
            put("pages/item/item", "商品详情");put("pages/cartOld/cartOld", "购物车");
            put("pages/cart/cart", "购物车");put("pages/groupbuyitem/groupbuyitem", "拼团详情");
            put("pages/appointment/appointment", "预约详情");put("pages/checkout/checkout", "订单结算");
            put("pages/storelist/storelist", "门店列表");put("pages/groupbuyinfo/groupbuyinfo", "成团详情");
            put("pages/searchs/search", "商品搜索");put("pages/orderinfo/orderinfo", "订单详情");
            put("pages/orderlist/orderlist", "订单列表");put("pages/usercardinfo/usercardinfo", "会员卡详情");
            put("pages/splitcoupon/splitcoupon", "分裂优惠券详情");put("pages/usercardlist/usercardlist", "会员卡列表");
            put("pages/appointorder/appointorder", "预约结算");put("pages/form/form", "表单");
            put("pages/appointinfo/appointinfo", "预约订单详情");put("pages/couponlist/couponlist", "优惠券列表");
            put("pages/splitinfo/splitinfo", "分裂优惠券领取详情");put("pages/appointlist/appointlist", "预约订单列表");
            put("pages/userinfo/userinfo", "账户设置");put("pages/getcoupon/getcoupon", "优惠券详情");
            put("pages/shopcheckout/shopcheckout", "门店买单");put("pages/groupbuyrule/groupbuyrule", "拼团规则");
            put("pages/cardpay/cardpay", "会员卡充值");put("pages/servicecomment/servicecomment", "服务评价");
            put("pages/goodscomment/goodscomment", "商品评价列表");put("pages/returnorder/returnorder", "申请退货/退款");
            put("pages/return_order_list/return_order_list", "退货中心");put("pages/appointcomment/appointcomment", "预约评价");
            put("pages/account/account", "我的余额");put("pages/comment/comment", "商品评价");
            put("pages/agreement/agreement", "充值活动协议");put("pages/shoporderinfo/shoporderinfo", "买单订单详情");
            put("pages/integral/integral", "我的积分");put("pages/collect/collect", "我的收藏");
            put("pages/distribution/distribution", "分销员中心");put("pages/inviteduser/inviteduser", "邀请用户列表");
            put("pages/distributionorder/distributionorder", "返利订单明细");put("pages/distributionrule/distributionrule", "分销说明");
            put("pages/brokeragerank/brokeragerank", "佣金排行榜");put("pages/bargaininfo/bargaininfo", "砍价详情");
            put("pages/signrule/signrule", "积分签到说明");put("pages/lotteryrule/lotteryrule", "抽奖记录");
            put("pages/bargainitem/bargainitem", "砍价商品详情");put("pages/lottery/lottery", "幸运大抽奖");
            put("pages/bargainlist/bargainlist", "我的砍价");put("pages/distributionspread/distributionspread", "申请分销员");
            put("pages/integralitem/integralitem", "积分商品详情");put("pages/usercardup/usercardup", "会员卡升级记录");
            put("pages/sort/sort", "分类");put("pages/bargainrule/bargainrule", "砍价记录");
            put("pages/auth/auth", "微信全链路授权页");put("pages/seckillitem/seckillitem", "秒杀详情页");
            put("pages/scancode/scancode", "扫码购");put("pages/goodsCheckout/goodsCheckout", "结算页");
            put("pages/balance/balance", "我的余额");put("pages/pinlotteryinfo/pinlotteryinfo", "拼团抽奖详情页");
            put("pages/payment/payment", "订单支付成功页");put("pages/maingoodslist/maingoodslist", "加价购主商品页");
            put("pages/webview/webview", "H5页");put("pages/userqrcode/userqrcode", "我的-二维码页	");
            put("pages/fullprice/fullprice", "满折满减页");put("pages/pinlotteryitem/pinlotteryitem", "拼团抽奖商品详情");
            put("pages/pinintegration/pinintegration", "瓜分积分页");put("pages/cartplus/cartplus", "购物车");
            put("pages/pinintegrationdetail/pinintegrationdetail", "瓜分积分详情页");put("pages/distrigrade/distrigrade", "分销员待审核页");
            put("pages/appearancecode/appearancecode", "出厂码");put("pages/pinlotterylist/pinlotterylist", "拼团抽奖列表");
            put("pages/widthdrawrecord/widthdrawrecord", "提现记录");put("pages/distrirecord/distrirecord", "分销员审核记录");
            put("pages/widthdraw/widthdraw", "提现页");put("pages/express/express", "物流详情页");
            put("pages/pinintegrationrule/pinintegrationrule", "瓜分积分活动规则");put("pages/pinlotteryrule/pinlotteryrule", "拼团抽奖活动详情");
            
        }};
    }
	
}
