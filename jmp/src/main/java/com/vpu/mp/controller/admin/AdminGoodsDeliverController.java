package com.vpu.mp.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverIdParam;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverPageListParam;
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

		PageResult<GoodsDeliverTemplateVo> pageResult = shop().goods.goodsDeliver.getDeliverTemplateList(param);

		/*try {
			for (GoodsDeliverTemplateVo vo : pageResult.dataList) {
				System.out.println("****************************");
				System.out.println(vo);
				ObjectMapper objectMapper = new ObjectMapper();
				List<GoodsDeliverTemplateAreaVo> goodsDeliverTemplateAreaVo;
				System.out.println("****************************");
				System.out.println(vo.getTemplateContent());
				
				goodsDeliverTemplateAreaVo = objectMapper.readValue(vo.getTemplateContent(),
						new TypeReference<List<GoodsDeliverTemplateAreaVo>>(){});
				System.out.println("****************************");
				System.out.println(goodsDeliverTemplateAreaVo);
				
				String jsonString = vo.getTemplateContent();
				jsonString	= objectMapper.writeValueAsString(goodsDeliverTemplateAreaVo);
				System.out.println("****************************");
				System.out.println(jsonString);
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		return success(pageResult);

	}

	/*
	 * public JsonResult inworker(JsonResult pageResult) {
	 * for(GoodsDeliverTemplateVo vo : pageResult.dataList) {
	 * System.out.println("****************************"); System.out.println(vo);
	 * ObjectMapper objectMapper = new ObjectMapper(); GoodsDeliverTemplateAreaParam
	 * goodsDeliverTemplateAreaParam;
	 * 
	 * goodsDeliverTemplateAreaParam =
	 * objectMapper.readValue(vo.getTemplateContent(),GoodsDeliverTemplateAreaParam.
	 * class);
	 * 
	 * System.out.println("****************************");
	 * System.out.println(goodsDeliverTemplateAreaParam); }
	 * 
	 * }
	 */
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

		shop().goods.goodsDeliver.addDeliverTemplate(goodsDeliverTemplateParam);

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

		shop().goods.goodsDeliver.addDeliverWeightTemplate(goodsDeliverTemplateParam);

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
}
