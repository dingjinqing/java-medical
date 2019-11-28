package com.vpu.mp.controller.admin;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.order.*;
import com.vpu.mp.service.pojo.shop.order.export.OrderExportQueryParam;
import com.vpu.mp.service.shop.order.action.base.ExecuteResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.refund.ReturnOrderParam;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.store.StoreOrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderOperateQueryParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.refund.RefundParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.ship.ShipParam;
import com.vpu.mp.service.pojo.shop.order.write.operate.verify.verifyParam;
import com.vpu.mp.service.pojo.shop.order.write.remark.SellerRemarkParam;
import com.vpu.mp.service.pojo.shop.order.write.star.StarParam;

import java.util.List;

/**
 * 	订单模块
 * 
 * @author 常乐,王帅 2019年6月27日
 */
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController extends AdminBaseController {

    private static final String LANGUAGE_TYPE_EXCEL = "excel";

	/**
	 * 	订单综合查询（不包括买单订单、虚拟商品订单）
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult orderList(@RequestBody OrderPageListQueryParam param) {
		PageResult<? extends OrderListInfoVo> result = shop().readOrder.getPageList(param);
		return success(result);
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
	 * 	退款订单详情
	 * @param order
	 * @return
	 */
	@PostMapping("/return/get")
	public JsonResult get(@RequestBody @Valid ReturnOrderParam order) {
		try {
			return success(shop().readOrder.getReturnOrder(order));
		} catch (MpException e) {
			return fail(e.getErrorCode());
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
			return fail(e.getErrorCode());
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
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
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
			return fail(e.getErrorCode());
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
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
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
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
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
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
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
            return fail(executeResult.getErrorCode(), executeResult.getErrorParam());
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
        String fileName = Util.translateMessage(getLang(), JsonResultMessage.ORDER_EXPORT_FILE_NAME ,LANGUAGE_TYPE_EXCEL,LANGUAGE_TYPE_EXCEL) + DateUtil.dateFormat(DateUtil.DATE_FORMAT_SHORT);
        export2Excel(workbook,fileName,response);
    }
}
