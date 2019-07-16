package com.vpu.mp.service.shop.summary.visit;

import com.vpu.mp.db.shop.tables.records.MpVisitPageRecord;
import com.vpu.mp.service.pojo.shop.summary.visit.PageVisitVo;
import com.vpu.mp.service.pojo.shop.summary.visit.PageVisitVoItem;
import com.vpu.mp.service.pojo.shop.summary.visit.VisitPageParam;
import org.jooq.Result;
import org.jooq.SortField;
import org.jooq.impl.DSL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.MpVisitPage.MP_VISIT_PAGE;

/**
 * 访问页面统计
 *
 * @author 郑保乐
 */
public class PageService extends BaseVisitService {

    public PageVisitVo getPageVisit(VisitPageParam param) {
        String startDate = param.getStartDate();
        String endDate = param.getEndDate();
        SortField<?> sortField = param.getSortField();
        Result<MpVisitPageRecord> result = getPageVisitResult(startDate, endDate, sortField);
        List<MpVisitPageRecord> records = result.into(MpVisitPageRecord.class);
        PageVisitVo vo = new PageVisitVo();
        List<PageVisitVoItem> items = records.parallelStream().map(r -> {
            PageVisitVoItem item = new PageVisitVoItem();
            item.setPagePath(r.getPagePath());
            item.setEntryPagePv(String.valueOf(r.getEntrypagePv()));
            item.setExitPagePv(String.valueOf(r.getExitpagePv()));
            item.setPageSharePv(String.valueOf(r.getPageSharePv()));
            item.setPageShareUv(String.valueOf(r.getPageShareUv()));
            item.setPageVisitPv(String.valueOf(r.getPageVisitPv()));
            item.setPageVisitUv(String.valueOf(r.getPageVisitUv()));
            item.setPageName(pageNameOf(r.getPagePath()));
            return item;
        }).collect(Collectors.toList());
        vo.setList(items);
        return vo;
    }

    private Result<MpVisitPageRecord> getPageVisitResult(
            String startDate, String endDate, SortField<?> orderBy) {
        return db().select(
                MP_VISIT_PAGE.PAGE_PATH,
                DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_VISIT_UV),
                DSL.sum(MP_VISIT_PAGE.ENTRYPAGE_PV),
                DSL.sum(MP_VISIT_PAGE.EXITPAGE_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_SHARE_PV),
                DSL.sum(MP_VISIT_PAGE.PAGE_SHARE_UV),
                DSL.sum(MP_VISIT_PAGE.PAGE_STAYTIME_PV))
                .from(MP_VISIT_PAGE)
                .where(MP_VISIT_PAGE.REF_DATE.between(startDate).and(endDate))
                .groupBy(MP_VISIT_PAGE.REF_DATE, MP_VISIT_PAGE.PAGE_PATH)
                .orderBy(orderBy)
                .fetch().into(MP_VISIT_PAGE);
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
        return new HashMap<String, String>() {{
            put("pages/index/index", "首页");
            put("pages/storeinfo/storeinfo", "门店");
            put("pages/usercenter/usercenter", "个人中心");
            put("pages/bottom/bottom", "底部导航");
            put("pages/item/item", "商品详情");
            put("pages/cartOld/cartOld", "购物车");
            put("pages/cart/cart", "购物车");
            put("pages/groupbuyitem/groupbuyitem", "拼团详情");
            put("pages/appointment/appointment", "预约详情");
            put("pages/checkout/checkout", "订单结算");
            put("pages/storelist/storelist", "门店列表");
            put("pages/groupbuyinfo/groupbuyinfo", "成团详情");
            put("pages/searchs/search", "商品搜索");
            put("pages/orderinfo/orderinfo", "订单详情");
            put("pages/orderlist/orderlist", "订单列表");
            put("pages/usercardinfo/usercardinfo", "会员卡详情");
            put("pages/splitcoupon/splitcoupon", "分裂优惠券详情");
            put("pages/usercardlist/usercardlist", "会员卡列表");
            put("pages/appointorder/appointorder", "预约结算");
            put("pages/form/form", "表单");
            put("pages/appointinfo/appointinfo", "预约订单详情");
            put("pages/couponlist/couponlist", "优惠券列表");
            put("pages/splitinfo/splitinfo", "分裂优惠券领取详情");
            put("pages/appointlist/appointlist", "预约订单列表");
            put("pages/userinfo/userinfo", "账户设置");
            put("pages/getcoupon/getcoupon", "优惠券详情");
            put("pages/shopcheckout/shopcheckout", "门店买单");
            put("pages/groupbuyrule/groupbuyrule", "拼团规则");
            put("pages/cardpay/cardpay", "会员卡充值");
            put("pages/servicecomment/servicecomment", "服务评价");
            put("pages/goodscomment/goodscomment", "商品评价列表");
            put("pages/returnorder/returnorder", "申请退货/退款");
            put("pages/return_order_list/return_order_list", "退货中心");
            put("pages/appointcomment/appointcomment", "预约评价");
            put("pages/account/account", "我的余额");
            put("pages/comment/comment", "商品评价");
            put("pages/agreement/agreement", "充值活动协议");
            put("pages/shoporderinfo/shoporderinfo", "买单订单详情");
            put("pages/integral/integral", "我的积分");
            put("pages/collect/collect", "我的收藏");
            put("pages/distribution/distribution", "分销员中心");
            put("pages/inviteduser/inviteduser", "邀请用户列表");
            put("pages/distributionorder/distributionorder", "返利订单明细");
            put("pages/brokeragerank/brokeragerank", "佣金排行榜");
            put("pages/distributionrule/distributionrule", "分销说明");
            put("pages/signrule/signrule", "积分签到说明");
            put("pages/bargainitem/bargainitem", "砍价商品详情");
            put("pages/bargaininfo/bargaininfo", "砍价详情");
            put("pages/bargainlist/bargainlist", "我的砍价");
            put("pages/distributionspread/distributionspread", "申请分销员");
            put("pages/lottery/lottery", "幸运大抽奖");
            put("pages/lotteryrule/lotteryrule", "抽奖记录");
            put("pages/integralitem/integralitem", "积分商品详情");
            put("pages/sort/sort", "分类");
            put("pages/bargainrule/bargainrule", "砍价记录");
            put("pages/usercardup/usercardup", "会员卡升级记录");
            put("pages/auth/auth", "微信全链路授权页");
            put("pages/seckillitem/seckillitem", "秒杀详情页");
            put("pages/scancode/scancode", "扫码购");
            put("pages/goodsCheckout/goodsCheckout", "结算页");
            put("pages/balance/balance", "我的余额");
            put("pages/pinlotteryinfo/pinlotteryinfo", "拼团抽奖详情页");
            put("pages/payment/payment", "订单支付成功页");
            put("pages/webview/webview", "H5页");
            put("pages/maingoodslist/maingoodslist", "加价购主商品页");
            put("pages/fullprice/fullprice", "满折满减页");
            put("pages/userqrcode/userqrcode", "我的-二维码页	");
            put("pages/pinlotteryitem/pinlotteryitem", "拼团抽奖商品详情");
            put("pages/pinintegration/pinintegration", "瓜分积分页");
            put("pages/cartplus/cartplus", "购物车");
            put("pages/pinintegrationdetail/pinintegrationdetail", "瓜分积分详情页");
            put("pages/distrigrade/distrigrade", "分销员待审核页");
            put("pages/appearancecode/appearancecode", "出厂码");
            put("pages/pinlotterylist/pinlotterylist", "拼团抽奖列表");
            put("pages/widthdrawrecord/widthdrawrecord", "提现记录");
            put("pages/distrirecord/distrirecord", "分销员审核记录");
            put("pages/widthdraw/widthdraw", "提现页");
            put("pages/express/express", "物流详情页");
            put("pages/pinintegrationrule/pinintegrationrule", "瓜分积分活动规则");
            put("pages/pinlotteryrule/pinlotteryrule", "拼团抽奖活动详情");
        }};
    }
}
