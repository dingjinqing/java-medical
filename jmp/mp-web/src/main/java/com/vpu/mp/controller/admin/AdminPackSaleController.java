package com.vpu.mp.controller.admin;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDefineVo;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDetailParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleDetailVo;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleOrderPageParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSalePageParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleParam;
import com.vpu.mp.service.pojo.shop.market.packagesale.PackSaleShareVo;

/**
 * @author huangronggang
 * @date 2019年8月12日 营销活动 一口价
 */
@RestController
@RequestMapping("/api/admin/market/packsale")
public class AdminPackSaleController extends AdminBaseController {
	/**
	 * 一口价活动分页查询
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/list")
	public JsonResult getList(@RequestBody PackSalePageParam param) {
		return success(shop().packSale.getPageList(param));
	}

	/**
	 * 添加打包一口价活动
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/add")
	public JsonResult insert(@RequestBody @Valid PackSaleParam param) {
		int result = shop().packSale.insert(param);
		return result(result);
	}

	/**
	 * 编辑打包一口价活动
	 * 
	 * @param param
	 * @return
	 */
	@PostMapping("/update")
	public JsonResult update(@RequestBody @Valid PackSaleParam param) {
		int result = shop().packSale.update(param);
		return result(result);

	}

	/**
	 * 删除 打包一口价活动
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/delete/{id}")
	public JsonResult delete(@PathVariable Integer id) {
		if (id == null) {
			return fail(JsonResultCode.CODE_FAIL);
		}
		int result = shop().packSale.delete(id);
		return result(result);
	}

	/**
	 * 分享打包一口价活动
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/qrcode/{id}")
	public JsonResult getPackageCode(@PathVariable Integer id) {
		PackSaleShareVo mpQrCode = shop().packSale.getMpQrCode(id);
		return success(mpQrCode);
	}

	/**
	 * 启用活动
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/enable/{id}")
	public JsonResult enableStatus(@PathVariable Integer id) {
		int result = shop().packSale.enableStatus(id);
		return result(result);
	}

	/**
	 * 停用活动
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("/disable/{id}")
	public JsonResult disableStatus(@PathVariable Integer id) {
		int result = shop().packSale.disableStatus(id);
		return result(result);
	}
	/**
	 * 查指定一口价活动详细
	 * @param id
	 * @return
	 */
	@GetMapping("/select/{id}")
	public JsonResult select(@PathVariable Integer id) {
		PackSaleDefineVo defineVo = shop().packSale.selectDefine(id);
		return success(defineVo);
	}
	/**
	 * 活动订单
	 * @param param
	 * @return
	 */
	@PostMapping("/order")
	public JsonResult getOrderList(@RequestBody @Valid PackSaleOrderPageParam param) {
		PageResult<?> orderList = shop().packSale.getOrderList(param);
		return success(orderList);
	}
	/**
	 * 活动明细
	 * @param param
	 * @return
	 */
	@PostMapping("/detail")
	public JsonResult detail(@RequestBody @Valid PackSaleDetailParam param) {
		 PageResult<PackSaleDetailVo> detail = shop().packSale.getPackSaleDetail(param);
		return success(detail);
	}
	private JsonResult result(int result) {
		if (result != 0) {
			return success(JsonResultCode.CODE_SUCCESS);
		}
		return fail(JsonResultCode.CODE_FAIL);
	}

}
