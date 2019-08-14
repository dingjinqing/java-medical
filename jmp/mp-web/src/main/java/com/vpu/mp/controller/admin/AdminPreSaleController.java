package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.market.presale.PreSaleListParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author 郑保乐
 */
@RestController
@RequestMapping("/api/admin/market/pre_sale")
public class AdminPreSaleController extends AdminBaseController {

    @PostMapping("/list")
    public JsonResult getPageList(@RequestBody PreSaleListParam param) {
        return success(shop().preSale.getPageList(param));
    }

    @PostMapping("/delete/{id}")
    public JsonResult deletePreSale(@PathVariable Integer id) {
        shop().preSale.deletePreSale(id);
        return success();
    }

    @PostMapping("/disable/{id}")
    public JsonResult disablePreSale(@PathVariable Integer id) {
        shop().preSale.disablePreSale(id);
        return success();
    }

    @PostMapping("/enable/{id}")
    public JsonResult enablePreSale(@PathVariable Integer id) {
        shop().preSale.enablePreSale(id);
        return success();
    }
}
