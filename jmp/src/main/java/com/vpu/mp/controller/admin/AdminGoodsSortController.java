package com.vpu.mp.controller.admin;

import com.vpu.mp.db.shop.tables.pojos.Sort;
import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.goods.GoodsSortService.GoodsSortListParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年07月01日
 */
@Controller
public class AdminGoodsSortController extends AdminBaseController {

    @GetMapping("/admin/goods/goodsSorts")
    @ResponseBody
    public JsonResult getList(GoodsSortListParam param) {

        List<Sort> list = shop().goods.goodsSort.getList(param);

        return success(list);
    }

    @PostMapping("/admin/goods/goodsSort")
    @ResponseBody
    public JsonResult insert(@RequestBody Sort sort) {

        shop().goods.goodsSort.insert(sort);

        return success();
    }

    @DeleteMapping("/admin/goods/goodsSort")
    @ResponseBody
    public JsonResult delete(Integer sortId) {

        shop().goods.goodsSort.delete(sortId);

        return success();
    }

    @PutMapping("/admin/goods/goodsSort")
    @ResponseBody
    public JsonResult update(@RequestBody Sort sort) {

        shop().goods.goodsSort.update(sort);

        return success();
    }

}
