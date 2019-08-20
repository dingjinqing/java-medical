package com.vpu.mp.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.config.DeliverTemplateConfig;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverIdParam;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverPageListParam;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverTemplateListVo;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverTemplateParam;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverTemplateVo;

/**
 * 运费模版控制器
 * 
 * @author liangchen
 * @date 2019年7月11日
 */
@RestController
public class AdminGoodsDeliverController extends AdminBaseController {

	/**
	 * 运费模版分页查询
	 *
	 * @param param
	 * @return
	 */

	@RequestMapping("/api/admin/goods/deliver/templatelist")
	public JsonResult getTemplateList(@RequestBody GoodsDeliverPageListParam param) {
		String config = shop().config.deliverTemplateConfigService.getDefaultDeliverTemplate();
		PageResult<GoodsDeliverTemplateVo> pageResult = shop().goods.goodsDeliver.getDeliverTemplateList(param);
		GoodsDeliverTemplateListVo vo = new GoodsDeliverTemplateListVo();
		vo.setConfig(config);
		vo.setPageResult(pageResult);
		return success(vo);

	}


	/**
	 * 重量运费模版分页查询
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping("/api/admin/goods/deliver/weighttemplatelist")
	public JsonResult getWeightTemplateList(@RequestBody GoodsDeliverPageListParam param) {
		PageResult<GoodsDeliverTemplateVo> pageResult = shop().goods.goodsDeliver.getWeightDeliverTemplateList(param);

		return success(pageResult);
	}

	/**
	 * 添加运费模版
	 * 
	 * @param goodsDeliverTemplateParam
	 * @return
	 *
	 */
	@RequestMapping("/api/admin/goods/deliver/addtemplate")
	public JsonResult addDeliverTemplate(@RequestBody GoodsDeliverTemplateParam goodsDeliverTemplateParam) {

		shop().goods.goodsDeliver.addDeliverTemplate(goodsDeliverTemplateParam);

		return success();
	}

	/**
	 * 添加重量运费模版
	 * 
	 * @param goodsDeliverTemplateParam
	 * @return
	 *
	 */
	@RequestMapping("/api/admin/goods/deliver/addweighttemplate")
	public JsonResult addDeliverWeightTemplate(@RequestBody GoodsDeliverTemplateParam goodsDeliverTemplateParam) {

		shop().goods.goodsDeliver.addDeliverWeightTemplate(goodsDeliverTemplateParam);

		return success();
	}

	/**
	 * 修改运费模版
	 * 
	 * @param goodsDeliverTemplateParam
	 * @return
	 *
	 */
	@RequestMapping("/api/admin/goods/deliver/updatetemplate")
	public JsonResult updataDeliverTemplate(@RequestBody GoodsDeliverTemplateParam goodsDeliverTemplateParam) {

		shop().goods.goodsDeliver.updateDeliverTemplate(goodsDeliverTemplateParam);

		return success();
	}

	/**
	 * 修改重量运费模版
	 * 
	 * @param goodsDeliverTemplateParam
	 * @return
	 *
	 */
	@RequestMapping("/api/admin/goods/deliver/updateweighttemplate")
	public JsonResult updateDeliverWeightTemplate(@RequestBody GoodsDeliverTemplateParam goodsDeliverTemplateParam) {

		shop().goods.goodsDeliver.updateDeliverWeightTemplate(goodsDeliverTemplateParam);

		return success();
	}

	/**
	 * 真删除指定运费模版
	 *
	 * @param goodsCommentId
	 * @return
	 */
	@PostMapping("/api/admin/goods/deliver/delete")
	public JsonResult delete(@RequestBody GoodsDeliverIdParam goodsDeliverIdParam) {

		shop().goods.goodsDeliver.deleteDeliverTemplate(goodsDeliverIdParam);

		return success();
	}
	
	/**
	 * 修改模版前先查询单个模版的信息，将其参数作为修改时的默认值
	 *
	 * @param param
	 * @return JsonResult
	 */

	@RequestMapping("/api/admin/goods/deliver/templateone")
	public JsonResult selectOne(@RequestBody GoodsDeliverIdParam param) {

		List<GoodsDeliverTemplateVo> goodsDeliverTemplateVos = shop().goods.goodsDeliver.selectOne(param);

		return success(goodsDeliverTemplateVos);

	}
	
	/**
	 * 默认运费模板配置
	 *
	 * @param 
	 * @return
	 */
	@RequestMapping("/api/admin/goods/deliver/config")
	public JsonResult setDefaultDeliverTemplate(@RequestBody DeliverTemplateConfig param) {
		
		shop().config.deliverTemplateConfigService.setDefaultDeliverTemplate(param);
		
		return success();
	}
	
}
