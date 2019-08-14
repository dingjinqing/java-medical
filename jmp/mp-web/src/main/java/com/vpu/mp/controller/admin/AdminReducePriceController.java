package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.reduceprice.ReducePriceAddParam;
import com.vpu.mp.service.pojo.shop.market.reduceprice.ReducePricePageListQueryParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public JsonResult getSeckillPageList(@RequestBody @Validated ReducePricePageListQueryParam param) {
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
}
