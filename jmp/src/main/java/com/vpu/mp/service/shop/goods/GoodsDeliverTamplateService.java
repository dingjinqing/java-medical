package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.DELIVER_FEE_TEMPLATE;

import org.jooq.Record4;
import org.jooq.SelectConditionStep;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.foundation.PageResult;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverIdParam;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverPageListParam;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverTemplateParam;
import com.vpu.mp.service.pojo.shop.goods.deliver.GoodsDeliverTemplateVo;



/**
 * 
 * 运费模版
 * 
 * @author liangchen
 * @date   2019年7月11日
 */

public class GoodsDeliverTamplateService extends BaseService{
	/**
	 * 运费模版列表
	 * 
	 * @param param
	 * @return 
	 */
	public PageResult<GoodsDeliverTemplateVo> getDeliverTemplateList(GoodsDeliverPageListParam param) {
		SelectConditionStep<Record4<Integer, String, String,Byte>> selectFrom =
				db().select(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID,DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,DELIVER_FEE_TEMPLATE.FLAG)
				.from(DELIVER_FEE_TEMPLATE)
				.where(DELIVER_FEE_TEMPLATE.FLAG.eq((byte)0));
		
		PageResult<GoodsDeliverTemplateVo> pageResult = this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), GoodsDeliverTemplateVo.class);
		
		return pageResult;
	}
	
	/**
	 * 重量运费模版列表
	 * 
	 * @param param
	 * @return 
	 */
	public PageResult<GoodsDeliverTemplateVo> getWeightDeliverTemplateList(GoodsDeliverPageListParam param) {
		SelectConditionStep<Record4<Integer, String, String,Byte>> selectFrom =
				db().select(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID,DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,DELIVER_FEE_TEMPLATE.FLAG)
				.from(DELIVER_FEE_TEMPLATE)
				.where(DELIVER_FEE_TEMPLATE.FLAG.eq((byte)1));
		
		PageResult<GoodsDeliverTemplateVo> pageResult = this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), GoodsDeliverTemplateVo.class);
		
		return pageResult;
	}
	
	/**
     * 添加运费模版
     *
     * @param param
     * @return 
     */
	public int addDeliverTemplate(GoodsDeliverTemplateParam param) {
				
		try {
			//** 复用ObjectMapper对象 */
			ObjectMapper objectMapper = new ObjectMapper();
			
			String jsonLimit = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateLimitParam());
			JsonNode jsonNodeLimit = objectMapper.readTree(jsonLimit);
			
			String jsonArea = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateAreaParam());
			JsonNode jsonNodeArea = objectMapper.readTree(jsonArea);
			
			ArrayNode array = objectMapper.createArrayNode();
			array.add(jsonNodeLimit);
			array.add(jsonNodeArea);
			
			JsonNode jsonResult = objectMapper.createObjectNode().set("datalist", array);
			int result = db()
	                .insertInto(DELIVER_FEE_TEMPLATE, DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.FLAG,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT)
	                .values(param.getTemplateName(),(byte)0,jsonResult.toString())
	                .execute();
			System.out.println(jsonResult.toString());
	        return result;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
     * 添加重量运费模版
     *
     * @param param
     * @return 
     */
	public int addDeliverWeightTemplate(GoodsDeliverTemplateParam param) {
				
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonLimit = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateLimitParam());
			JsonNode jsonNodeLimit = objectMapper.readTree(jsonLimit);
			
			String jsonArea = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateAreaParam());
			JsonNode jsonNodeArea = objectMapper.readTree(jsonArea);
			
			ArrayNode array = objectMapper.createArrayNode();
			array.add(jsonNodeLimit);
			array.add(jsonNodeArea);
			System.out.println(array.toString());
			
			
			JsonNode jsonResult = objectMapper.createObjectNode().set("datalist", array);
			int result = db()
	                .insertInto(DELIVER_FEE_TEMPLATE, DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.FLAG,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT)
	                .values(param.getTemplateName(),(byte)1,jsonResult.toString())
	                .execute();
			System.out.println(jsonResult.toString());
	        return result;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	 /**
     * 真删除运费模版
     *
     * @param goodsDeliverIdParam
     * @return 数据库受影响行数
     */
    public int deleteDeliverTemplate(GoodsDeliverIdParam goodsDeliverIdParam) {
        return db().deleteFrom(DELIVER_FEE_TEMPLATE)
        		.where(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.eq(goodsDeliverIdParam.getDeliverTemplateId()))
                .execute();
    }
}
