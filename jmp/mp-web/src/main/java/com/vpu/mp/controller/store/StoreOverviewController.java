package com.vpu.mp.controller.store;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.store.article.ArticleParam;
import com.vpu.mp.service.pojo.shop.store.article.ArticlePojo;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticConstant;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticOrderWaitVo;
import com.vpu.mp.service.pojo.shop.store.statistic.StatisticParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenjie
 * @date 2020年08月28日
 */
@RestController
public class StoreOverviewController extends StoreBaseController {
    /**
     * 门店待发货和待核销订单数量
     * @return
     */
    @RequestMapping(value = "/api/store/overview/wait/data")
    public JsonResult getStoreWaitOrder() {
        StatisticOrderWaitVo statisticOrderWaitVo = new StatisticOrderWaitVo();
        statisticOrderWaitVo.setWaitDeliverNum(shop().readOrder.getStoreOrderWaitDeliver(storeAuth.user().getStoreIds()));
        statisticOrderWaitVo.setWaitVerifyNum(shop().readOrder.getStoreOrderWaitVerify(storeAuth.user().getStoreIds()));
        return success(statisticOrderWaitVo);
    }

    /**
     * 门店下单和支付统计数据
     * @return
     */
    @PostMapping(value = "/api/store/overview/statistic/data")
    public JsonResult getStoreStatistic(@RequestBody StatisticParam param) {
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
}
