package com.vpu.mp.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.goods.sort.GoodsRecommendSortConfig;
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

    @GetMapping("/api/admin/goods/sort/get/{sortId}")
    public JsonResult getSort(@PathVariable("sortId") Integer sortId) {
        if (sortId == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }

        return success(shop().goods.goodsSort.getSort(sortId));
    }

    /**
     * 新增
     * @param sort
     * @return
     */
    @PostMapping("/api/admin/goods/sort/add")
    public JsonResult insert(@RequestBody Sort sort) {

        if (sort.getSortName()==null) {
            return fail(JsonResultCode.GOODS_SORT_NAME_IS_NULL);
        }

        boolean isExist = shop().goods.goodsSort.isSortNameExist(sort);
        if (isExist) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.insert(sort);

        return success();
    }

    /**
     *  推荐商品批量新增接口
     * @param sort
     * @return
     */
    @PostMapping("/api/admin/goods/sort/recommend/add")
    public JsonResult insertRecommendSort(@RequestBody Sort sort) {

        //如果提交的内容内部有重复
        List<Sort> sorts = new ArrayList<>();
        sorts.add(sort);

        sorts.addAll(sort.getChildren());
        if (isSortNameRepeat(sorts)) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        //判断内容和数据库内有重复
        if (shop().goods.goodsSort.isSortNameExist(sorts)) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.insertRecommendSort(sort);
        return success();
    }

    @GetMapping("/api/admin/goods/sort/recommend/get/{sortId}")
    public JsonResult getRecommendSort(@PathVariable("sortId") Integer sortId) {
        if (sortId == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }
        return success(shop().goods.goodsSort.getRecommendSort(sortId));
    }

    /**
     *  判断批量商品新增时候是否内部有重复值
     * @param sorts
     * @return
     */
    private boolean isSortNameRepeat(List<Sort> sorts) {
        Map<String,Object> map=new HashMap<>(sorts.size());
        for (Sort sort : sorts) {
            map.put(sort.getSortName(),null);
        }

        if (map.size() != sorts.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除
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

    @PostMapping("/api/admin/goods/sort/recommend/update")
    public JsonResult updateRecommendSort(@RequestBody Sort sort) {
        if (sort.getSortId() == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }

        //如果提交的内容内部有重复
        List<Sort> sorts = new ArrayList<>();
        sorts.add(sort);

        sorts.addAll(sort.getChildren());
        if (isSortNameRepeat(sorts)) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        //判断子分类内容和数据库内有重复
        if (shop().goods.goodsSort.isOtherSortNameExist(sorts,sort.getSortId())) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.updateRecommendSort(sort);
        return success();
    }

    /**
     * 设置推荐分类配置
     * @param goodsRecommendSortConfig 推荐配置参数
     * @return
     */
    @PostMapping("/api/admin/goods/sort/setConfig")
    public JsonResult setRecommendSortConfig(@RequestBody GoodsRecommendSortConfig goodsRecommendSortConfig){
        shop().config.recommendSortConfigService.setRecommendSortConfig(goodsRecommendSortConfig);
        return success();
    }

    /**
     * 获取推荐分类配置
     * @return 推荐分类配置项
     */
    @GetMapping("/api/admin/goods/sort/getConfig")
    public JsonResult getRecommendSortConfig() {
        return success(shop().config.recommendSortConfigService.getRecommendSortConfig());
    }

}
