package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultMessage;
import com.vpu.mp.common.foundation.util.DateUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.OrderParam;
import com.vpu.mp.service.pojo.shop.order.export.OrderExportQueryParam;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderParam;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.batch.BatchShipListParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.batch.BatchShipParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.verify.verifyParam;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkParam;
import com.vpu.mp.service.pojo.shop.order.write.star.StarParam;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 	订单模块
 * 
 * @author 常乐,王帅 2019年6月27日
 */
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController extends AdminBaseController {

	/**
	 * 	订单综合查询（不包括买单订单、虚拟商品订单）
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult orderList(@RequestBody OrderPageListQueryParam param) {
		return success(shop().readOrder.getPageList(param));
	}

	/**
	 * 	买单订单列表查询
	 */
	@PostMapping("/store/list")
	public JsonResult storyOrderList(@RequestBody StoreOrderPageListQueryParam param) {
		PageResult<StoreOrderListInfoVo> result = shop().readOrder.getPageList(param);
		return success(result);
	}
	
	/**
	 * 	买单订单详情查询
	 */
	@PostMapping("/store/get")
	public JsonResult getStoreOrder(@RequestBody @Valid OrderParam order) {
		StoreOrderInfoVo result = shop().readOrder.getStoreOrder(order.getOrderSn());
		return success(result);
	}

	/**
	 * 	订单详情（不包括退款货、买单订单、虚拟商品订单）
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping("/get")
	public JsonResult get(@RequestBody @Valid OrderParam order) {
		return success(shop().readOrder.get(order.getOrderSn()));
	}

    /**
     * 	订单简略信息
     *
     * @param order
     * @return
     */
    @PostMapping("/simple")
    public JsonResult getSimpleInfo(@RequestBody @Valid OrderParam order) {
        return success(shop().readOrder.getSimpleInfo(order.getOrderSn()));
    }

	/**
	 * 	退款订单详情
	 * @param order
	 * @return
	 */
	@PostMapping("/return/get")
	public JsonResult get(@RequestBody @Valid ReturnOrderParam order) {
		try {
			return success(shop().readOrder.getReturnOrder(order));
		} catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
		}
	}
	
	/**
	 * 	订单标星切换
	 */
	@PostMapping("/star")
	public JsonResult switchStar(@RequestBody StarParam param) {
		shop().writeOrder.switchStar(param);
		return success();
	}
	
	/**
	 * 	更新卖家备注
	 */
	@PostMapping("/sellerRemark")
	public JsonResult sellerRemark(@RequestBody @Valid SellerRemarkParam param) {
		return success(shop().writeOrder.sellerRemark(param));
	}
	
	/**
	 * 	发货_查询可发货商品
	 */
	@PostMapping("/shipGoods")
	public JsonResult shipGoodsList(@RequestBody @Valid OrderOperateQueryParam param) {
		param.setIsMp(OrderConstant.IS_MP_ADMIN);
		try {
			return success(shop().orderActionFactory.orderQuery(param));
		} catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
		}
	}
	
	/**
	 * 	发货
	 */
	@PostMapping("/ship")
	public JsonResult ship(@RequestBody @Valid ShipParam param ) {
		param.setIsMp(OrderConstant.IS_MP_ADMIN);
		param.setAdminInfo(adminAuth.user());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
	}
	
	/**
	 * 	退款、退货查询
	 */
	@PostMapping("/refund/list")
	public JsonResult mpRefundGoodsList(@RequestBody @Valid OrderOperateQueryParam param) {
		param.setIsMp(OrderConstant.IS_MP_ADMIN);
		try {
			return success(shop().orderActionFactory.orderQuery(param));
		} catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
		}
	}
	
	/**
	 * 退款
	 */
	@PostMapping("/refund")
	public JsonResult refundMoney(@RequestBody @Valid RefundParam param) {
		param.setIsMp(OrderConstant.IS_MP_ADMIN);
		param.setAdminInfo(adminAuth.user());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
	}
	
	/**
	 * 订单关闭
	 */
	@PostMapping("/close")
	public JsonResult close(@RequestBody @Valid OrderOperateQueryParam param) {
		param.setIsMp(OrderConstant.IS_MP_ADMIN);
		param.setAdminInfo(adminAuth.user());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
	}
	
	/**
	 * 订单核销
	 */
	@PostMapping("/verify")
	public JsonResult verify(@RequestBody @Valid verifyParam param) {
		param.setIsMp(OrderConstant.IS_MP_ADMIN);
		param.setAdminInfo(adminAuth.user());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
	}
	
	/**
	 * 订单完成
	 */
	@PostMapping("/finish")
	public JsonResult finish(@RequestBody @Valid OrderOperateQueryParam param) {
		param.setIsMp(OrderConstant.IS_MP_ADMIN);
		param.setAdminInfo(adminAuth.user());
        ExecuteResult executeResult = shop().orderActionFactory.orderOperate(param);
        if(executeResult == null || executeResult.isSuccess()) {
            return success(executeResult == null ? null : executeResult.getResult());
        }else {
            return result(executeResult.getErrorCode(), executeResult.getResult(), executeResult.getErrorParam());
        }
	}

    /**
     * 获取当前表格导出列表头
     */
    @PostMapping("/export/columns/get")
    public JsonResult getExportColumns() {
        List<String> columns = shop().config.orderExportCfg.getOrderExportList();
        return success(columns);
    }

    /**
     * 设置表格导出列表头
     */
    @PostMapping("/export/columns/set")
    public JsonResult setExportColumns(@RequestBody List<String> columns) {
        shop().config.orderExportCfg.setOrderExportList(columns);
        return success();
    }

    /**
     * 取将要导出的列数
     */
    @PostMapping("/export/rows")
    public JsonResult getExportTotalRows(@RequestBody @Valid OrderExportQueryParam param) {
        return success(shop().readOrder.getExportOrderListSize(param));
    }

    /**
     * 订单导出
     */
    @PostMapping("/export")
    public void orderExport(@RequestBody @Valid OrderExportQueryParam param, HttpServletResponse response) {
        List<String> columns = shop().config.orderExportCfg.getOrderExportList();
        Workbook workbook =shop().readOrder.exportOrderList(param,columns,getLang());
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.ORDER_EXPORT_FILE_NAME ,OrderConstant.LANGUAGE_TYPE_EXCEL,OrderConstant.LANGUAGE_TYPE_EXCEL) + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SHORT);
        export2Excel(workbook,fileName,response);
    }

    /**
     * 批量发货
     */
    @PostMapping("/ship/batch")
    public JsonResult batchShip(BatchShipParam param) {
        try {
            param.setLang(getLang());
            param.setAdminInfo(adminAuth.user());
            shop().writeOrder.batchShip(param);
            return success();
        } catch (MpException e) {
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());
        }
    }

    /**
     * 批量发货查询
     */
    @PostMapping("/ship/batch/list")
    public JsonResult batchShipList(@RequestBody BatchShipListParam param) {
        return success(shop().readOrder.batchShipList(param));
    }

    /**
     * 批量发货失败数据下载
     */
    @PostMapping("/ship/batch/fail/download/{batchId}")
    public void downloadFailData(@PathVariable Integer batchId, HttpServletResponse response) {
        Workbook workbook = shop().readOrder.downloadFailData(batchId, getLang());
        export2Excel(workbook, DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL_NO_UNDERLINE), response);
    }
}
