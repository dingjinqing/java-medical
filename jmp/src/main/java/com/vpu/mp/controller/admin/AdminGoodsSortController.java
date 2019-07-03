package com.vpu.mp.controller.admin;

import java.util.List;

import com.vpu.mp.service.foundation.JsonResultCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;
import com.vpu.mp.service.shop.ShopApplication;

/**
 * @author 李晓冰
 * @date 2019年07月01日
 */
@RestController
public class AdminGoodsSortController extends AdminBaseController {

    @Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }

    /**
     * 商品分类查询，未分页
     *
     * @param param
     * @return
     */
    @PostMapping("/api/admin/goods/sort/list")
    public JsonResult getList(@RequestBody GoodsSortListParam param) {

        List<Sort> list = shop().goods.goodsSort.getList(param);

        return success(list);
    }

    /**
     * 新增
     *
     * @param sort
     * @return
     */
    @PostMapping("/api/admin/goods/sort/add")
    public JsonResult insert(@RequestBody Sort sort) {

        boolean isExist = shop().goods.goodsSort.isSortNameExist(sort);
        if (isExist) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.insert(sort);

        return success();
    }

    /**
     * 删除
     *
     * @param sort
     * @return
     */
    @PostMapping("/api/admin/goods/sort/delete")
    public JsonResult delete(@RequestBody Sort sort) {

        shop().goods.goodsSort.delete(sort);

        return success();
    }

    /**
     * 修改
     *
     * @param sort
     * @return
     */
    @PostMapping("/api/admin/goods/sort/update")
    public JsonResult update(@RequestBody Sort sort) {

        boolean isExist = shop().goods.goodsSort.isOtherSortNameExist(sort);
        if (isExist) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.update(sort);

        return success();
    }

}
