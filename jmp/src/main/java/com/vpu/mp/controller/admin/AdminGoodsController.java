package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.goods.Goods;
import com.vpu.mp.service.pojo.shop.goods.GoodsColumnCheckExistParam;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李晓冰
 * @date 2019年07月08日
 */
@RestController
public class AdminGoodsController extends AdminBaseController{

    @Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }

    @PostMapping("/api/admin/goods/add")
    public JsonResult insert(@RequestBody Goods goods){
        shop().goods.insert(goods);

        return success();
    }

    @PostMapping("/api/admin/goods/columns/exist")
    public JsonResult isColumnValueExist(@RequestBody GoodsColumnCheckExistParam goodsColumnCheckExistParam){
        boolean isExist= shop().goods.isColumnValueExist(goodsColumnCheckExistParam);
        if (isExist) {
            return success();
        } else {
            return fail();
        }
    }

}
