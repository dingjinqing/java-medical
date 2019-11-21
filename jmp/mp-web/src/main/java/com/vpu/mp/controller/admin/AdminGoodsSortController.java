package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.sort.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 李晓冰
 * @date 2019年07月01日
 */
@RestController
public class AdminGoodsSortController extends AdminBaseController {

    /**
     * 商品分类查询，未分页
     * @return
     */
    @GetMapping("/api/admin/goods/sort/list/{sortType}")
    public JsonResult getList(@PathVariable("sortType") Byte sortType) {
        if (!GoodsConstant.NORMAL_SORT.equals(sortType) && !GoodsConstant.RECOMMEND_SORT.equals(sortType)) {
            sortType = GoodsConstant.NORMAL_SORT;
        }
        GoodsSortListParam param = new GoodsSortListParam();
        param.setParentId(GoodsConstant.ROOT_PARENT_ID);
        param.setType(sortType);
        return success(shop().goods.goodsSort.getSortList(param));
    }

    /**
     * 选择框下拉列表（非树形数据）
     */
    @GetMapping("/api/admin/goods/sort/select/list")
    public JsonResult getSelectList(){
        return success(shop().goods.goodsSort.getSelectList());
    }

    /**
     *  普通分类新增
     * @param param {@link GoodsNormalSortAddParam}
     */
    @PostMapping("/api/admin/goods/sort/add")
    public JsonResult insert(@RequestBody GoodsNormalSortAddParam param) {
        if (param.getSortName()==null) {
            return fail(JsonResultCode.GOODS_SORT_NAME_IS_NULL);
        }

        boolean isExist = shop().goods.goodsSort.isSortNameExist(null,param.getSortName());
        if (isExist) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.insertNormal(param);

        return success();
    }

    /**
     * 普通分类修改
     * @param param {@link GoodsNormalSortUpdateParam}
     */
    @PostMapping("/api/admin/goods/sort/update")
    public JsonResult update(@RequestBody GoodsNormalSortUpdateParam param) {
        if (param.getSortId() == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }
        boolean isExist = shop().goods.goodsSort.isSortNameExist(param.getSortId(),param.getSortName());
        if (isExist) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }
        shop().goods.goodsSort.updateNormal(param);
        return success();
    }

    /**
     * 删除商家分类
     * @param sortId 分类id
     */
    @GetMapping("/api/admin/goods/sort/delete/{sortId}")
    public JsonResult delete(@PathVariable("sortId") Integer sortId) {
        if (sortId == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }
        shop().goods.goodsSort.delete(sortId);
        return success();
    }

    /**
     * 根据id获取普通商家分类
     * @param sortId 普通商家分类id
     */
    @GetMapping("/api/admin/goods/sort/get/{sortId}")
    public JsonResult getSort(@PathVariable("sortId") Integer sortId) {
        if (sortId == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }
        return success(shop().goods.goodsSort.getSort(sortId));
    }

    /**
     *  推荐商品批量新增接口
     * @param param
     */
    @PostMapping("/api/admin/goods/sort/recommend/add")
    public JsonResult insertRecommendSort(@RequestBody GoodsRecommendSortParam param) {

        //如果提交的内容内部有重复
        List<String> sortsNames = new ArrayList<>();
        sortsNames.add(param.getSortName());
        if (param.getChildren() != null) {
            sortsNames.addAll(param.getChildren().stream().map(GoodsRecommendSortParam.GoodsRecommendSortChild::getSortName).collect(Collectors.toList()));
        }
        // 判断传入的数据是否存在名称重复
        if (isSortNamesRepeat(sortsNames)) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        //判断内容和数据库内有重复
        if (shop().goods.goodsSort.isSortNameExist(sortsNames)) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.insertRecommendSort(param);
        return success();
    }

    @PostMapping("/api/admin/goods/sort/recommend/update")
    public JsonResult updateRecommendSort(@RequestBody GoodsRecommendSortParam param){
        if (param.getSortId() == null) {
            return fail(JsonResultCode.GOODS_SORT_ID_IS_NULL);
        }

        //如果提交的内容内部有重复
        List<String> sortsNames = new ArrayList<>();
        sortsNames.add(param.getSortName());
        if (param.getChildren() != null) {
            sortsNames.addAll(param.getChildren().stream().map(GoodsRecommendSortParam.GoodsRecommendSortChild::getSortName).collect(Collectors.toList()));
        }
        // 判断传入的数据是否存在名称重复
        if (isSortNamesRepeat(sortsNames)) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        //判断子分类内容和数据库内有重复
        if (shop().goods.goodsSort.isSortNameExist(param.getSortId(),sortsNames)) {
            return fail(JsonResultCode.GOODS_SORT_NAME_EXIST);
        }

        shop().goods.goodsSort.updateRecommendSort(param);
        return success();
    }

    private boolean isSortNamesRepeat(List<String> sortNames){
        Set<String> set = new HashSet<>(sortNames);
        return set.size()!=sortNames.size();
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
