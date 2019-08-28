package com.vpu.mp.controller.admin;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.reduceprice.ReducePriceAddParam;
import com.vpu.mp.service.pojo.shop.market.reduceprice.ReducePricePageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.reduceprice.ReducePriceUpdateParam;
import com.vpu.mp.service.pojo.shop.market.reduceprice.ReducePriceVo;
import com.vpu.mp.service.pojo.shop.market.reduceprice.SimpleReducePriceParam;

/**
 * @author: 王兵兵
 * @create: 2019-08-14 10:56
 **/
@RestController
public class AdminReducePriceController extends AdminBaseController {

    /**
     * 限时降价活动活动分页查询列表
     *
     */
    @PostMapping(value = "/api/admin/market/reduceprice/list")
    public JsonResult getReducePricePageList(@RequestBody @Validated ReducePricePageListQueryParam param) {
        return success(shop().reducePrice.getPageList(param));
    }

    /**
     *添加 限时降价活动
     *
     */
    @PostMapping(value = "/api/admin/market/reduceprice/add")
    public JsonResult addReducePrice(@RequestBody @Validated ReducePriceAddParam param) {
        shop().reducePrice.addReducePrice(param);
        return success();
    }
    /**
     *取单个限时降价活动信息
     *
     */
    @PostMapping(value = "/api/admin/market/reduceprice/get")
    public JsonResult getReducePriceById(@RequestBody @Validated SimpleReducePriceParam param) {
        ReducePriceVo reducePriceVo = shop().reducePrice.getReducePriceById(param.getId());
        if(reducePriceVo != null) {
            return success(reducePriceVo);
        }else {
            return fail();
        }
    }
    /**
     *更新 限时降价活动
     *
     */
    @PostMapping(value = "/api/admin/market/reduceprice/update")
    public JsonResult updateReducePrice(@RequestBody @Validated ReducePriceUpdateParam param) {
        shop().reducePrice.updateReducePrice(param);
        return success();
    }

    /**
     *删除 限时降价活动
     *
     */
    @PostMapping(value = "/api/admin/market/reduceprice/del")
    public JsonResult delReducePrice(@RequestBody @Validated SimpleReducePriceParam param) {
        shop().reducePrice.delReducePrice(param.getId());
        return success();
    }

    /**
     * 限时降价订单列表
     *
     */
    @PostMapping(value = "/api/admin/market/reduceprice/order")
    public JsonResult getReducePriceOrderList(@RequestBody @Validated MarketOrderListParam param) {
        return success(shop().reducePrice.getReducePriceOrderList(param));
    }
}
