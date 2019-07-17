package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultCode;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.config.store.StoreServiceConfig;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryParam;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePageListVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.shop.store.verify.VerifierAddParam;
import com.vpu.mp.service.pojo.shop.store.verify.VerifierListQueryParam;

/**
 * 门店管理
 * @author: 卢光耀
 * @date: 2019-07-09 15:06
 *
*/
@RestController
public class AdminStoreController extends AdminBaseController{
    /**
     * 门店分组-列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/list")
    public JsonResult getStoreGroupByPage(@RequestBody StoreGroupQueryParam param) {
        PageResult<StoreGroup> storeGroupPageResult = shop().store.storeGroup.getStoreGroupPageList(param);
        return success(storeGroupPageResult);
    }
    
    /**
     * 获取门店列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/list")
    public JsonResult getStorePageList(@RequestBody(required = false) StoreListQueryParam param) {
        PageResult<StorePageListVo> storePageResult = shop().store.getPageList(param);
        return success(storePageResult);
    }
    
    /**
     * 门店-新增
     * @return
     */
    @PostMapping(value = "/api/admin/store/add")
    public JsonResult addStore(@RequestBody(required = true) @Valid StorePojo store) {
       if(shop().store.addStore(store)) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    
    /**
     * 门店-修改
     * @return
     */
    @PostMapping(value = "/api/admin/store/update")
    public JsonResult updateStore(@RequestBody(required = true) @Valid StorePojo store) {
       if(shop().store.updateStore(store)) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    

    /**
     * 门店-删除
     * @return
     */
    @PostMapping(value = "/api/admin/store/del")
    public JsonResult delStore(@RequestBody(required = true) @Valid StoreParam store) {
       if(shop().store.delStore(store.getStoreId())) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    
    /**
     * 门店-取单个门店信息
     * @return
     */
    @PostMapping(value = "/api/admin/store/get")
    public JsonResult getStore(@RequestBody(required = true) @Valid StoreParam store) {
       StorePojo storeRes = shop().store.getStore(store.getStoreId());
       if(null != storeRes) {
    	   return success(storeRes);
       }else {
    	   return fail();
       }
    }
    
    /**
     * 检查门店编码
     * @return
     */
    @PostMapping(value = "/api/admin/store/coding/check")
    public JsonResult checkStoreCoding(@RequestBody(required = true) @Valid StorePojo store) {
       if(shop().store.checkStoreCoding(store.getPosShopId())) {
    	   return success();
       }else {
    	   return fail(JsonResultCode.CODE_POS_SHOP_ID_EXIST);
       }
    }

    /**
     * 门店分组-新增
     * @param param
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/add")
    public JsonResult addStoreGroup(@RequestBody StoreGroupQueryParam param) {
        boolean isExist = shop().store.storeGroup.isStoreGroupExist(param);
        if ( isExist ){
            int count = shop().store.storeGroup.insertStoreGroup(param);
            if (count > 0){
                return success();
            }else{
                return fail();
            }
        }else{
            return fail(JsonResultCode.CODE_STORE_GROUP_NAME_EXIST);
        }
    }
    /**
     * 门店分组-修改
     * @param param
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/update")
    public JsonResult updateStoreGroup(@RequestBody StoreGroupQueryParam param) {
        boolean isExist = shop().store.storeGroup.isStoreGroupExist(param);
        if ( isExist ){
            int count = shop().store.storeGroup.updateStoreGroup(param);
            if (count > 0){
                return success();
            }else{
                return fail();
            }
        }else{
            return fail(JsonResultCode.CODE_STORE_GROUP_NAME_EXIST);
        }
    }
    /**
     * 门店分组-删除
     * @param param
     * @return
     */
    @PostMapping(value = "/api/admin/store/group/del")
    public JsonResult delStoreGroup(@RequestBody StoreGroupQueryParam param) {
        shop().store.storeGroup.deleteStoreGroup(param);
        return success();

    }
    
    /**
     * 获取门店服务配置
     * @return
     */
    @GetMapping(value = "/api/admin/store/config/get")
    public JsonResult getStoreServiceConfig() {
    	return success(shop().config.storeConfigService.getStoreServiceConfig()); 
    }
    
    /**
     * 更新门店服务配置
     * @return
     */
    @PostMapping(value = "/api/admin/store/config/update")
    public JsonResult updateStoreServiceConfig(@RequestBody StoreServiceConfig storeServiceConfig) {
    	if(shop().config.storeConfigService.setStoreServiceConfig(storeServiceConfig)) {
    		return success();
    	}else {
    		return fail();
    	}
    }
    
    /**
     * 获取核销员分页列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/verifier/list")
    public JsonResult getStoreVerifierList(@RequestBody(required = true) @Valid VerifierListQueryParam verifierListQueryParam) {
    	return success(shop().store.storeVerifier.getPageList(verifierListQueryParam));
    }
    
    /**
     * 添加核销员
     * @return
     */
    @PostMapping(value = "/api/admin/store/verifier/add")
    public JsonResult addStoreVerifier(@RequestBody(required = false) @Valid VerifierAddParam param) {
    	if(shop().store.storeVerifier.addVerifiers(param)) {
    		return success();
    	}else {
    		return fail();
    	}
    }
    
    /**
     * 获取门店商品分页列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/goods/list")
    public JsonResult getStoreGoodsList(@RequestBody(required = false) @Valid StoreGoodsListQueryParam param) {
    	return success(shop().store.storeGoods.getPageList(param));
    }
    
    /**
     * 获取门店服务分类分页列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/category/list")
    public JsonResult getStoreServiceCategoryList(@RequestBody(required = false) @Valid StoreServiceCategoryListQueryParam param) {
    	return success(shop().store.storeService.getCatePageList(param));
    }
    
    /**
     * 获取全部门店服务分类列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/category/all")
    public JsonResult getStoreServiceAllCategory(@RequestBody(required = false) @Valid StoreServiceCategoryListQueryParam param) {
    	return success(shop().store.storeService.getAllStoreServiceCategory(param));
    }
    
    /**
     * 门店服务分类-添加
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/category/add")
    public JsonResult addStoreServiceCategory(@RequestBody(required = true) @Valid StoreServiceCategoryParam storeServiceCategory) {
       if(shop().store.storeService.addStoreServiceCategory(storeServiceCategory)) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    
    /**
     * 门店服务分类-修改
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/category/update")
    public JsonResult updateStoreServiceCategory(@RequestBody(required = true) @Valid StoreServiceCategoryParam storeServiceCategory) {
       if(shop().store.storeService.updateStoreServiceCategory(storeServiceCategory)) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    

    /**
     * 门店服务分类-删除
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/category/del")
    public JsonResult delStoreServiceCategory(@RequestBody(required = true) @Valid StoreServiceCategoryParam storeServiceCategory) {
       if(shop().store.storeService.delStoreServiceCategory(storeServiceCategory.getCatId())) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    
    /**
     * 获取门店服务分页列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/list")
    public JsonResult getStoreServiceList(@RequestBody(required = false) @Valid StoreServiceListQueryParam param) {
    	return success(shop().store.storeService.getServicePageList(param));
    }
    
    /**
     * 批量上架门店服务
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/batch/on")
    public JsonResult batchOnStoreService(@RequestBody(required = true) @Valid Integer[] serviceIds) {
    	if(shop().store.storeService.batchOnStoreService(serviceIds)) {
     	   return success();
        }else {
     	   return fail();
        }
    }
    
    /**
     * 批量下架门店服务
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/batch/off")
    public JsonResult batchOffStoreService(@RequestBody(required = true) @Valid Integer[] serviceIds) {
    	if(shop().store.storeService.batchOffStoreService(serviceIds)) {
     	   return success();
        }else {
     	   return fail();
        }
    }
    
    /**
     * 门店服务-添加
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/add")
    public JsonResult addStoreService(@RequestBody(required = true) @Valid StoreServiceParam storeService) {
       if(shop().store.storeService.addStoreService(storeService)) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    
    /**
     * 门店服务-修改
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/update")
    public JsonResult updateStoreService(@RequestBody(required = true) @Valid StoreServiceParam storeService) {
       if(shop().store.storeService.updateStoreService(storeService)) {
    	   return success();
       }else {
    	   return fail();
       }
    }
    

    /**
     * 门店服务-删除
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/del")
    public JsonResult delStoreService(@RequestBody(required = true) @Valid StoreServiceParam storeService) {
       if(shop().store.storeService.delStoreService(storeService.getId())) {
    	   return success();
       }else {
    	   return fail();
       }
    }
}
