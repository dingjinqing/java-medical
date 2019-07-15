package com.vpu.mp.controller.admin;

import java.util.List;

import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsSortListParam;
import com.vpu.mp.service.pojo.shop.goods.sort.Sort;

/**
 * @author 李晓冰
 * @date 2019年07月01日
 */
@RestController
public class AdminGoodsSortController extends AdminBaseController {

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
     *  推荐商品新增接口
     * @param sorts
     * @return
     */
    @PostMapping("/api/admin/goods/sort/recommend/add")
    public JsonResult insertRecommendSort(@RequestBody List<Sort> sorts) {
        if (sorts == null || sorts.size() == 0) {
            return success();
        }

        shop().goods.goodsSort.insertRecommendSort(sorts);

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

        if (sort.getSortId() == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }

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

        if (sort.getSortId() == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }

        boolean isExist = shop().goods.goodsSort.isOtherSortNameExist(sort);
        if (isExist) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.update(sort);

        return success();
    }

}
