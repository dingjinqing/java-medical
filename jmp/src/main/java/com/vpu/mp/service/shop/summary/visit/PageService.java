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

    public static final String PAGE_OTHER = "page.other";

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
        return Optional.ofNullable(pageMap().get(pagePath)).orElse(PAGE_OTHER);
    }

    /**
     * 路径和页面名称对应关系
     */
    private Map<String, String> pageMap() {
        return new HashMap<String, String>() {{
            put("pages/index/index", "page.index");
            put("pages/storeinfo/storeinfo", "page.storeInfo");
            put("pages/usercenter/usercenter", "page.userCenter");
            put("pages/bottom/bottom", "page.bottom");
            put("pages/item/item", "page.item");
            put("pages/cartOld/cartOld", "page.cartOld");
            put("pages/cart/cart", "page.cart");
            put("pages/groupbuyitem/groupbuyitem", "page.groupBuyItem");
            put("pages/appointment/appointment", "page.appointment");
            put("pages/checkout/checkout", "page.checkout");
            put("pages/storelist/storelist", "page.storeList");
            put("pages/groupbuyinfo/groupbuyinfo", "page.groupBuyInfo");
            put("pages/searchs/search", "page.search");
            put("pages/orderinfo/orderinfo", "page.orderInfo");
            put("pages/orderlist/orderlist", "page.orderList");
            put("pages/usercardinfo/usercardinfo", "page.userCardInfo");
            put("pages/splitcoupon/splitcoupon", "page.splitCoupon");
            put("pages/usercardlist/usercardlist", "page.userCardList");
            put("pages/appointorder/appointorder", "page.appointOrder");
            put("pages/form/form", "page.form");
            put("pages/appointinfo/appointinfo", "page.appointInfo");
            put("pages/couponlist/couponlist", "page.couponList");
            put("pages/splitinfo/splitinfo", "page.splitInfo");
            put("pages/appointlist/appointlist", "page.appointList");
            put("pages/userinfo/userinfo", "page.userInfo");
            put("pages/getcoupon/getcoupon", "page.getCoupon");
            put("pages/shopcheckout/shopcheckout", "page.shopCheckout");
            put("pages/groupbuyrule/groupbuyrule", "page.groupBuyRule");
            put("pages/cardpay/cardpay", "page.cardPay");
            put("pages/servicecomment/servicecomment", "page.serviceComment");
            put("pages/goodscomment/goodscomment", "page.goodsComment");
            put("pages/returnorder/returnorder", "page.returnOrder");
            put("pages/return_order_list/return_order_list", "page.returnOrderList");
            put("pages/appointcomment/appointcomment", "page.appointComment");
            put("pages/account/account", "page.account");
            put("pages/comment/comment", "page.comment");
            put("pages/agreement/agreement", "page.agreement");
            put("pages/shoporderinfo/shoporderinfo", "page.shopOrderInfo");
            put("pages/integral/integral", "page.integral");
            put("pages/collect/collect", "page.collect");
            put("pages/distribution/distribution", "page.distribution");
            put("pages/inviteduser/inviteduser", "page.invitedUser");
            put("pages/distributionorder/distributionorder", "page.distributionOrder");
            put("pages/brokeragerank/brokeragerank", "page.brokerAgeRank");
            put("pages/distributionrule/distributionrule", "page.distributionRule");
            put("pages/signrule/signrule", "page.signRule");
            put("pages/bargainitem/bargainitem", "page.bargainItem");
            put("pages/bargaininfo/bargaininfo", "page.bargainInfo");
            put("pages/bargainlist/bargainlist", "page.bargainList");
            put("pages/distributionspread/distributionspread", "page.distributionSpread");
            put("pages/lottery/lottery", "page.lottery");
            put("pages/lotteryrule/lotteryrule", "page.lotteryRule");
            put("pages/integralitem/integralitem", "page.integralItem");
            put("pages/sort/sort", "page.sort");
            put("pages/bargainrule/bargainrule", "page.bargainRule");
            put("pages/usercardup/usercardup", "page.userCardUp");
            put("pages/auth/auth", "page.auth");
            put("pages/seckillitem/seckillitem", "page.seckillItem");
            put("pages/scancode/scancode", "page.scanCode");
            put("pages/goodsCheckout/goodsCheckout", "page.goodsCheckout");
            put("pages/balance/balance", "page.balance");
            put("pages/pinlotteryinfo/pinlotteryinfo", "page.pinLotteryInfo");
            put("pages/payment/payment", "page.payment");
            put("pages/webview/webview", "page.webview");
            put("pages/maingoodslist/maingoodslist", "page.mainGoodsList");
            put("pages/fullprice/fullprice", "page.fullPrice");
            put("pages/userqrcode/userqrcode", "page.userQRCode");
            put("pages/pinlotteryitem/pinlotteryitem", "page.pinLotteryItem");
            put("pages/pinintegration/pinintegration", "page.pinIntegration");
            put("pages/cartplus/cartplus", "page.cartPlus");
            put("pages/pinintegrationdetail/pinintegrationdetail", "page.pinIntegrationDetail");
            put("pages/distrigrade/distrigrade", "page.distrigrade");
            put("pages/appearancecode/appearancecode", "page.appearanceCode");
            put("pages/pinlotterylist/pinlotterylist", "page.pinLotteryList");
            put("pages/widthdrawrecord/widthdrawrecord", "page.widthdrawRecord");
            put("pages/distrirecord/distrirecord", "page.distriRecord");
            put("pages/widthdraw/widthdraw", "page.widthdraw");
            put("pages/express/express", "page.express");
            put("pages/pinintegrationrule/pinintegrationrule", "page.pinIntegrationRule");
            put("pages/pinlotteryrule/pinlotteryrule", "page.pinLotteryRule");
        }};
    }
}
