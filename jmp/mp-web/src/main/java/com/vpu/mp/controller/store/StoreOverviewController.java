package com.vpu.mp.controller.store;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.overview.BindAndUnParam;
import com.vpu.mp.service.pojo.shop.overview.BindofficialVo;
import com.vpu.mp.service.pojo.shop.store.article.ArticleParam;
import com.vpu.mp.service.pojo.shop.store.article.ArticlePojo;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticConstant;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticOrderWaitVo;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreBestSellersParam;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author chenjie
 * @date 2020年08月28日
 */
@RestController
public class StoreOverviewController extends StoreBaseController {

    @Value(value = "${official.appId}")
    private String bindAppId;
    /**
     * 门店待发货和待核销订单数量
     * @return
     */
    @GetMapping(value = "/api/store/overview/wait/data")
    public JsonResult getStoreWaitOrder() {
        StatisticOrderWaitVo statisticOrderWaitVo = new StatisticOrderWaitVo();
        statisticOrderWaitVo.setWaitDeliverNum(shop().readOrder.getStoreOrderWaitDeliver(storeAuth.user().getStoreIds()));
        statisticOrderWaitVo.setWaitVerifyNum(shop().readOrder.getStoreOrderWaitVerify(storeAuth.user().getStoreIds()));
        return success(statisticOrderWaitVo);
    }

    /**
     * 门店下单和支付统计数据 **
     * @return
     */
    @PostMapping(value = "/api/store/overview/statistic/data")
    public JsonResult getStoreStatistic(@RequestBody StatisticParam param) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        param.setRefDate(Date.valueOf(today.minusDays(1).toLocalDate()));
        return success(shop().storeSummary.getStoreStatistic(param));
    }

    /**
     * 门店公告-列表
     * @return
     */
    @PostMapping(value = "/api/store/overview/article/list")
    public JsonResult getArticleList(@RequestBody ArticleParam param) {
        PageResult<ArticlePojo> result = shop().store.articleList(param);
        return success(result);
    }

    /**
     * 获取绑定店铺的二维码
     * @return
     */
    @GetMapping("/api/store/public/service/bind/getOfficialQrCode")
    public JsonResult generateThirdPartCode() {
        try {
            String generateThirdPartCode = saas.shop.officeAccount.generateThirdPartCodeForStore(storeAuth.user(),bindAppId);
            return success(generateThirdPartCode);
        } catch (WxErrorException e) {
            logger().debug(e.getMessage(),e);
        }
        return fail();

    }

    /**
     * 绑定解绑
     *
     * @param param the param
     * @return json result
     */
    @PostMapping("/api/store/survey/official/bind")
    public JsonResult bindUnBindOfficial(@RequestBody BindAndUnParam param){
        boolean bindUnBindOfficial = saas.overviewService.bindUnBindOfficialForStore(param.getAct(),storeAuth.user(),storeAuth.user().getStoreAccountId());
        return bindUnBindOfficial?success():fail();
    }

    /**
     * 获取绑定/解绑状态
     *
     * @return json result
     */
    @GetMapping("/api/store/malloverview/getbindUnBindStatus")
    public JsonResult getbindUnBindStatus(){
        BindofficialVo getbindUnBindStatusUseByOver = saas.overviewService.getbindUnBindStatusUseByOverForStore(storeAuth.user(),bindAppId);
        return success(getbindUnBindStatusUseByOver);
    }

    /**
     * 查询门店热销商品报表
     * @param storeBestSellersParam 热销商品入参
     * @return JsonResult
     */
    @PostMapping("/api/store/view/bestsellers")
    public JsonResult getBestSellers(@Validated @RequestBody StoreBestSellersParam storeBestSellersParam) {
        return success(saas.overviewService.getBestSellers(storeBestSellersParam));
    }
}
