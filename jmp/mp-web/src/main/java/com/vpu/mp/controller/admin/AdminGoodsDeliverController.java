package com.vpu.mp.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.area.AreaProvinceVo;
import com.vpu.mp.service.pojo.shop.config.DeliverTemplateConfig;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverBoxVo;
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
@RequestMapping("/api/admin/goods/deliver")
public class AdminGoodsDeliverController extends AdminBaseController {

	/**
	 * 返回所有地区代码及名称
	 * 
	 * @param
	 * @return JsonResult
	 */
	@GetMapping("/select")
	public JsonResult getAllArea() {
		List<AreaProvinceVo> areaSelectVo = shop().goods.goodsDeliver.getAllArea();
		return success(areaSelectVo);
	}

	/**
	 * 运费模版分页查询
	 *
	 * @param param
	 * @return
	 */

	@PostMapping("/templatelist")
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
	@PostMapping("/weighttemplatelist")
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
	@PostMapping("/addtemplate")
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
	@PostMapping("/addweighttemplate")
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
	@PostMapping("/updatetemplate")
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
	@PostMapping("/updateweighttemplate")
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
	@PostMapping("/delete")
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

	@PostMapping("/templateone")
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
	@PostMapping("/config")
	public JsonResult setDefaultDeliverTemplate(@RequestBody DeliverTemplateConfig param) {

		shop().config.deliverTemplateConfigService.setDefaultDeliverTemplate(param);

		return success();
	}

	/**
	 * 运费模板下拉框
	 *
	 * @param
	 * @return
	 */
	@GetMapping("/box")
	public JsonResult getBox() {
		List<GoodsDeliverBoxVo> boxVos = shop().goods.goodsDeliver.getBox();
		return success(boxVos);
	}
	/**
	 * 默认运费模板配置
	 *
	 * @param
	 * @return
	 */
	@GetMapping("/getconfig")
	public JsonResult getDefaultDeliverTemplate() {
		String config = shop().config.deliverTemplateConfigService.getDefaultDeliverTemplate();
		return success(config);
	}
}
