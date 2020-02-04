package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.pledge.group.DeleteGroup;
import com.vpu.mp.service.pojo.shop.config.pledge.group.UpdateGroup;
import com.vpu.mp.service.pojo.shop.config.store.StoreServiceConfig;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsListQueryParam;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsUpdateParam;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroup;
import com.vpu.mp.service.pojo.shop.store.group.StoreGroupQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceCategoryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceListQueryParam;
import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.service.order.*;
import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;
import com.vpu.mp.service.pojo.shop.store.store.StoreListQueryParam;
import com.vpu.mp.service.pojo.shop.store.store.StoreParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import com.vpu.mp.service.pojo.shop.store.validated.StoreAddValidatedGroup;
import com.vpu.mp.service.pojo.shop.store.validated.StoreCodingCheckValidatedGroup;
import com.vpu.mp.service.pojo.shop.store.validated.StoreUpdateValidatedGroup;
import com.vpu.mp.service.pojo.shop.store.verifier.VerifierAddParam;
import com.vpu.mp.service.pojo.shop.store.verifier.VerifierListQueryParam;
import com.vpu.mp.service.pojo.shop.store.verifier.VerifierSimpleParam;
import com.vpu.mp.service.shop.store.service.ServiceOrderService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

import static com.vpu.mp.service.shop.store.service.ServiceOrderService.*;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * 门店管理
 * @author: 卢光耀
 * @date: 2019-07-09 15:06
 *
*/
@RestController
public class AdminStoreController extends AdminBaseController{

    private static final String LANGUAGE_TYPE_EXCEL= "excel";

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
     * 门店分组-全部分组
     * @return
     */
    @GetMapping(value = "/api/admin/store/group/all")
    public JsonResult getAllStoreGroup() {
        return success(shop().store.storeGroup.getAllStoreGroup());
    }

    /**
     * 获取门店列表
     * @return
     */
    @PostMapping(value = "/api/admin/store/list")
    public JsonResult getStorePageList(@RequestBody(required = false) StoreListQueryParam param) {
        return success(shop().store.getPageList(param));
    }

    /**
     * 门店-新增
     * @return
     */
    @PostMapping(value = "/api/admin/store/add")
    public JsonResult addStore(@RequestBody(required = true) @Validated({StoreAddValidatedGroup.class}) StorePojo store) {
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
    public JsonResult updateStore(@RequestBody(required = true) @Validated({StoreUpdateValidatedGroup.class}) StorePojo store) {
       if(shop().store.updateStore(store)) {
    	   return success();
       }else {
    	   return fail();
       }
    }

    /**
     * 门店-批量修改
     */
    @PostMapping(value = "/api/admin/store/batchupdate")
    public JsonResult batchUpdateStore(@RequestBody(required = true) @Validated({StoreUpdateValidatedGroup.class}) List<StorePojo> storeList) {
        shop().store.batchUpdateStore(storeList);
        return success();
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
    public JsonResult checkStoreCoding(@RequestBody(required = true) @Validated({StoreCodingCheckValidatedGroup.class}) StorePojo store) {
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
     * 核销员列表导出
     * @return
     */
    @PostMapping(value = "/api/admin/store/verifier/export")
    public void exportStoreVerifierList(@RequestBody @Valid VerifierListQueryParam verifierListQueryParam,HttpServletResponse response, HttpServletRequest request) throws IOException {
        Workbook workbook =shop().store.storeVerifier.exportStoreVerifierList(verifierListQueryParam,getLang());
        //门店名称
        String storeName = shop().store.getStoreName(verifierListQueryParam.getStoreId());
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.STORE_VERIFIER_LIST_FILENAME,LANGUAGE_TYPE_EXCEL,LANGUAGE_TYPE_EXCEL,storeName);

        export2Excel(workbook,fileName,response);
    }

    /**
     * 添加核销员
     * @return
     */
    @PostMapping(value = "/api/admin/store/verifier/add")
    public JsonResult addStoreVerifier(@RequestBody(required = false) @Valid VerifierAddParam param) {
    	shop().store.storeVerifier.addVerifiers(param);
    	return success();
    }

    /**
     * 删除核销员
     * @return
     */
    @PostMapping(value = "/api/admin/store/verifier/del")
    public JsonResult delStoreVerifier(@RequestBody @Valid VerifierSimpleParam verifier) {
        shop().store.storeVerifier.delStoreVerifier(verifier);
        return success();
    }

    @GetMapping("/api/admin/store/goods/updateFromShop/{storeId}")
    public JsonResult updateGoodsDataFromShop(@PathVariable("storeId") Integer storeId){
        if (storeId == null) {
            return fail();
        }
        shop().store.storeGoods.updateGoodsDataFromShop(storeId);
        return success();
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
     * 门店商品-上架
     * @return
     */
    @PostMapping(value = "/api/admin/store/goods/on")
    public JsonResult storeGoodsPutOnSale(@RequestBody @Valid StoreGoodsUpdateParam param) {
        shop().store.storeGoods.storeGoodsPutOnSale(param);
        return success();
    }

    /**
     * 门店商品-下架
     * @return
     */
    @PostMapping(value = "/api/admin/store/goods/off")
    public JsonResult storeGoodsPutOffSale(@RequestBody @Valid StoreGoodsUpdateParam param) {
        shop().store.storeGoods.storeGoodsPutOffSale(param);
        return success();
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
    public JsonResult getStoreServicePageList(@RequestBody(required = false) @Valid StoreServiceListQueryParam param) {
    	return success(shop().store.storeService.getServicePageList(param));
    }

    /**
     * 获取所有门店服务
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/all")
    public JsonResult getAllStoreServiceList(@RequestBody(required = false) @Valid StoreServiceListQueryParam param) {
    	return success(shop().store.storeService.getAllStoreServiceByStoreId(param.getStoreId()));
    }

    /**
     * 批量上架门店服务
     */
    @PostMapping(value = "/api/admin/store/service/batch/on")
    public JsonResult batchOnStoreService(@RequestBody @Valid @Size(min = 1, max = Integer.MAX_VALUE - 1) Integer[] serviceIds) {
        shop().store.storeService.batchOnOrOffStoreService(serviceIds, BYTE_ONE);
        return success();
    }

    /**
     * 批量下架门店服务
     */
    @PostMapping(value = "/api/admin/store/service/batch/off")
    public JsonResult batchOffStoreService(@RequestBody @Validated @Size(min = 1, max = Integer.MAX_VALUE - 1) Integer[] serviceIds) {
        shop().store.storeService.batchOnOrOffStoreService(serviceIds, BYTE_ZERO);
        return success();
    }

    /**
     * 门店服务-添加
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
     * 根据id查询门店服务详情
     */
    @GetMapping(value = "/api/admin/store/service/get/{serviceId}")
    public JsonResult getStoreService(@PathVariable @PositiveOrZero Integer serviceId) {
        return success(shop().store.storeService.getStoreService(serviceId));
    }

    /**
     * 门店服务-修改
     */
    @PostMapping(value = "/api/admin/store/service/update")
    public JsonResult updateStoreService(@RequestBody(required = true) @Validated({UpdateGroup.class}) StoreServiceParam storeService) {
        shop().store.storeService.updateStoreService(storeService);
        return success();
    }


    /**
     * 门店服务-删除
     */
    @PostMapping(value = "/api/admin/store/service/del")
    public JsonResult delStoreService(@RequestBody(required = true) @Validated({DeleteGroup.class}) StoreServiceParam storeService) {
        shop().store.storeService.delStoreService(storeService.getId());
        return success();
    }

    /**
     * 获取门店服务预约分页列表
     */
    @PostMapping(value = "/api/admin/store/service/reserve/list")
    public JsonResult getServiceOrderList(@RequestBody(required = true) @Valid ServiceOrderListQueryParam param) {
    	ServiceOrderPageListQueryVo vo = new ServiceOrderPageListQueryVo();
    	vo.setPageList(shop().store.serviceOrder.getPageList(param));
    	ServiceOrderCountingDataVo countingData = new ServiceOrderCountingDataVo();
    	/**全部预约*/
    	param.setOrderStatus((byte)-1);
    	countingData.setAll(shop().store.serviceOrder.getCountData(param));
    	/**待付款*/
    	param.setOrderStatus(ServiceOrderService.ORDER_STATUS_WAIT_PAY);
    	countingData.setWaitPay(shop().store.serviceOrder.getCountData(param));
    	/**待服务*/
    	param.setOrderStatus(ServiceOrderService.ORDER_STATUS_WAIT_SERVICE);
    	countingData.setWaitService(shop().store.serviceOrder.getCountData(param));
    	/**已取消*/
        param.setOrderStatus(ORDER_STATUS_CANCELED);
    	countingData.setCanceled(shop().store.serviceOrder.getCountData(param));
    	/**已完成*/
    	param.setOrderStatus(ServiceOrderService.ORDER_STATUS_FINISHED);
    	countingData.setFinished(shop().store.serviceOrder.getCountData(param));
    	vo.setCountingData(countingData);
    	return success(vo);
    }

    /**
     * 获取服务预约订单详情
     * @return
     */
    @GetMapping(value = "/api/admin/store/service/reserve/detail")
    public JsonResult getServiceOrderDetail(@Valid String orderSn) {
    	return success(shop().store.serviceOrder.getServiceOrderDetail(orderSn));
    }

    /**
     * 预约添加卖家留言
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/reserve/message/add")
    public JsonResult addServiceOrderAdminMessage(@RequestBody(required = true) @Valid ServiceOrderAdminMessageParam param) {
    	if(shop().store.serviceOrder.addServiceOrderAdminMessage(param)) {
    		return success();
    	}else {
    		return fail();
    	}
    }

    /**
     * 后台添加服务预约
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/reserve/add")
    public JsonResult addServiceOrder(@RequestBody(required = true) @Valid ServiceOrderAddParam param) {
    	if(shop().store.serviceOrder.addServiceOrder(param)) {
    		return success();
    	}else {
    		return fail();
    	}
    }

    /**
     * 服务预约核销
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/reserve/charge")
    public JsonResult serviceOrderCharge(@RequestBody(required = true) @Valid ServiceOrderChargeParam param) {
    	if(!shop().store.serviceOrder.checkVerifyCode(param.getOrderSn(), param.getVerifyCode())) {
    		return fail(JsonResultCode.CODE_SERVICE_ORDER_VERIFY_CODE_ERROR);
    	}
    	boolean tradeFlag = false;
        int adminUser = this.adminAuth.user().getSysId();
    	switch (param.getVerifyPay().byteValue()) {
	    	/** 会员卡核销 */
            case ServiceOrderService.VERIFY_PAY_TYPE_MEMBER_CARD:
//                if(param.getCountDis() == null) {
//                    return fail(JsonResultCode.CODE_PARAM_ERROR);
//                }
                if(param.getReduce() == null) {
                    return fail(JsonResultCode.CODE_SERVICE_ORDER_VERIFY_BALANCE_IS_NULL);
                }
                if(param.getReason() == null) {
                    return fail(JsonResultCode.CODE_SERVICE_ORDER_VERIFY_REASON_IS_NULL);
                }

                try{
                    shop().store.serviceOrder.userCardServiceOrderCharge(param,adminUser);
                    tradeFlag = true;
                }catch (MpException e){
                    tradeFlag = false;
                }

                break;
			/** 用户余额核销 */
			case ServiceOrderService.VERIFY_PAY_TYPE_ACCOUNT:
				if(param.getAccount() == null) {
					return fail(JsonResultCode.CODE_PARAM_ERROR);
				}
				if(param.getBalance() == null) {
					return fail(JsonResultCode.CODE_SERVICE_ORDER_VERIFY_BALANCE_IS_NULL);
				}
				if(param.getReason() == null) {
					return fail(JsonResultCode.CODE_SERVICE_ORDER_VERIFY_REASON_IS_NULL);
				}

                try{
                    shop().store.serviceOrder.accountServiceOrderCharge(param,adminUser);
                    tradeFlag = true;
                }catch (MpException e){
                    tradeFlag = false;
                }

				break;
			/** 门店买单 */
			case ServiceOrderService.VERIFY_PAY_TYPE_STORE:
				tradeFlag = true;
				break;
			default:
				return fail();
		}
    	if(tradeFlag) {
    	    /**交易完成，更新服务订单状态 **/
    		ServiceOrderUpdateParam updateParam = new ServiceOrderUpdateParam();
    		updateParam.setFinishedTime(DateUtil.getLocalDateTime());
    		updateParam.setVerifyAdmin(adminAuth.user().getUserName());
    		updateParam.setOrderStatus(ServiceOrderService.ORDER_STATUS_FINISHED);
            updateParam.setOrderStatusName(ORDER_STATUS_NAME_FINISHED);
    		updateParam.setVerifyType(ServiceOrderService.VERIFY_TYPE_ADMIN);
    		FieldsUtil.assignNotNull(param, updateParam);

    		if(shop().store.serviceOrder.serviceOrderUpdate(updateParam)) {
        		return success();
        	}else {
        		return fail();
        	}
    	}else {
    		return fail();
    	}
    }


    /**
     * 服务预约订单后台取消
     * @return
     */
    @PostMapping(value = "/api/admin/store/service/reserve/cancel")
    public JsonResult serviceOrderCancel(@RequestBody(required = true) @Valid ServiceOrderUpdateParam param) {
    	if(param.getCancelReason() == null) {
    		return fail(JsonResultCode.CODE_SERVICE_ORDER_CANCEL_REASON_IS_NULL);
    	}
    	ServiceOrderUpdateParam updateParam = new ServiceOrderUpdateParam();
		updateParam.setCancelledTime(DateUtil.getLocalDateTime());
		FieldsUtil.assignNotNull(param, updateParam);
        updateParam.setOrderStatus(ORDER_STATUS_CANCELED);
        updateParam.setOrderStatusName(ORDER_STATUS_NAME_CANCELED);
		if(shop().store.serviceOrder.serviceOrderUpdate(updateParam)) {
		    //模板消息通知
            shop().store.serviceOrder.serviceOrderCancelNotify(updateParam,param.getCancelReason());
            return success();
    	}else {
    		return fail();
    	}

    }
    /**
     * -门店下拉框弹窗api
     * @return
     */
    @PostMapping(value = "/api/admin/store/all/get")
    public JsonResult getAllStore() {
    	List<StoreBasicVo> allStore = shop().store.getAllStore();
    	return success(allStore);
    }

    /**
     * 门店服务分享
     *
     * @param serviceId 门店服务id
     */
    @GetMapping(value = "/api/admin/store/service/share/{serviceId}")
    public JsonResult shareStoreService(@PathVariable Integer serviceId) {
        return success(shop().store.storeService.shareStoreService(serviceId));
    }

    /**
     * 门店分享
     *
     * @param id 门店id
     */
    @GetMapping(value = "/api/admin/store/share/{id}")
    public JsonResult shareStore(@PathVariable Integer id) {
        return success(shop().store.share(QrCodeTypeEnum.SHOP_SHARE, "id=" + id));
    }

    /**
     * 获取被核销用户余额
     *
     * @param userId userId
     */
    @GetMapping(value = "/api/admin/store/charge/{userId}")
    public JsonResult getUserAccount(@PathVariable Integer userId) {
        return success(shop().user.getUserByUserId(userId).getAccount());
    }
}
