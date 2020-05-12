package com.vpu.mp.controller.system;


import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.saas.index.param.ShopViewParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class SystemIndexController extends SystemBaseController {

    @PostMapping("/api/system/index/shopView")
    public JsonResult shopView(ShopViewParam param){
        return success(saas.shopViewService.getShopViewData(param));
    }
}
