package com.vpu.mp.service.shop.goods;

import static com.vpu.mp.db.shop.Tables.DELIVER_FEE_TEMPLATE;

import java.util.List;

import org.jooq.Record4;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
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
@Service

public class GoodsDeliverTamplateService extends ShopBaseService{
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
			//* 外层模版类（不含包邮条件） */
			String jsonLimit = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateLimitParam());
			JsonNode jsonNodeLimit = objectMapper.readTree(jsonLimit);
			
			String jsonArea = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateAreaParam());
			JsonNode jsonNodeArea = objectMapper.readTree(jsonArea);
			
			ArrayNode arrayTemplate = objectMapper.createArrayNode();
			arrayTemplate.add(jsonNodeLimit);
			arrayTemplate.add(jsonNodeArea);
			//* 包邮条件 */
			String jsonFee = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeParam());
			JsonNode jsonNodeFee = objectMapper.readTree(jsonFee);
			
			String jsonFeeCon = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeConditionParam());
			JsonNode jsonNodeFeeCon = objectMapper.readTree(jsonFeeCon);
			ArrayNode arrayFeeCon = objectMapper.createArrayNode();
			arrayFeeCon.add(jsonNodeFeeCon);
			
			JsonNode jsonResultTemplate = objectMapper.createObjectNode().set("datalist", arrayTemplate);
			JsonNode jsonResultFeeCon = objectMapper.createObjectNode().set("fee_0_data_list", arrayFeeCon);
			
			ArrayNode arrayTotal = objectMapper.createArrayNode();
			arrayTotal.add(jsonResultTemplate);
			arrayTotal.add(jsonNodeFee);
			arrayTotal.add(jsonResultFeeCon);
			
			
			int result = db()
	                .insertInto(DELIVER_FEE_TEMPLATE, DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.FLAG,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT)
	                .values(param.getTemplateName(),(byte)0,arrayTotal.toString())
	                .execute();
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
			//** 复用ObjectMapper对象 */
			ObjectMapper objectMapper = new ObjectMapper();
			//* 外层模版类（不含包邮条件） */
			String jsonLimit = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateLimitParam());
			JsonNode jsonNodeLimit = objectMapper.readTree(jsonLimit);
			
			String jsonArea = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateAreaParam());
			JsonNode jsonNodeArea = objectMapper.readTree(jsonArea);
			
			ArrayNode arrayTemplate = objectMapper.createArrayNode();
			arrayTemplate.add(jsonNodeLimit);
			arrayTemplate.add(jsonNodeArea);
			//* 包邮条件 */
			String jsonFee = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeParam());
			JsonNode jsonNodeFee = objectMapper.readTree(jsonFee);
			
			String jsonFeeCon = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeConditionParam());
			JsonNode jsonNodeFeeCon = objectMapper.readTree(jsonFeeCon);
			ArrayNode arrayFeeCon = objectMapper.createArrayNode();
			arrayFeeCon.add(jsonNodeFeeCon);
			
			JsonNode jsonResultTemplate = objectMapper.createObjectNode().set("datalist", arrayTemplate);
			JsonNode jsonResultFeeCon = objectMapper.createObjectNode().set("fee_0_data_list", arrayFeeCon);
			
			ArrayNode arrayTotal = objectMapper.createArrayNode();
			arrayTotal.add(jsonResultTemplate);
			arrayTotal.add(jsonNodeFee);
			arrayTotal.add(jsonResultFeeCon);
			
			
			int result = db()
	                .insertInto(DELIVER_FEE_TEMPLATE, DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.FLAG,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT)
	                .values(param.getTemplateName(),(byte)1,arrayTotal.toString())
	                .execute();
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
    /**
	 * 修改模版前先查询单个模版的信息，将其参数作为修改时的默认值
	 * 
	 * @param param
	 * @return List<GoodsDeliverTemplateVo>
	 */
	
	public List<GoodsDeliverTemplateVo> selectOne(GoodsDeliverIdParam param) {

		List<GoodsDeliverTemplateVo> goodsDeliverTemplateVos = 
				db().select(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID,DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,DELIVER_FEE_TEMPLATE.FLAG)
				.from(DELIVER_FEE_TEMPLATE)
				.where(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.eq(param.getDeliverTemplateId()))
				.fetchInto(GoodsDeliverTemplateVo.class);

		return goodsDeliverTemplateVos;

	}
	/**
     *修改运费模版
     *
     * @param param
     * @return 
     */
	public int updateDeliverTemplate(GoodsDeliverTemplateParam param) {
				
		try {
			//** 复用ObjectMapper对象 */
			ObjectMapper objectMapper = new ObjectMapper();
			//* 外层模版类（不含包邮条件） */
			String jsonLimit = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateLimitParam());
			JsonNode jsonNodeLimit = objectMapper.readTree(jsonLimit);
			
			String jsonArea = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateAreaParam());
			JsonNode jsonNodeArea = objectMapper.readTree(jsonArea);
			
			ArrayNode arrayTemplate = objectMapper.createArrayNode();
			arrayTemplate.add(jsonNodeLimit);
			arrayTemplate.add(jsonNodeArea);
			//* 包邮条件 */
			String jsonFee = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeParam());
			JsonNode jsonNodeFee = objectMapper.readTree(jsonFee);
			
			String jsonFeeCon = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeConditionParam());
			JsonNode jsonNodeFeeCon = objectMapper.readTree(jsonFeeCon);
			ArrayNode arrayFeeCon = objectMapper.createArrayNode();
			arrayFeeCon.add(jsonNodeFeeCon);
			
			JsonNode jsonResultTemplate = objectMapper.createObjectNode().set("datalist", arrayTemplate);
			JsonNode jsonResultFeeCon = objectMapper.createObjectNode().set("fee_0_data_list", arrayFeeCon);
			
			ArrayNode arrayTotal = objectMapper.createArrayNode();
			arrayTotal.add(jsonResultTemplate);
			arrayTotal.add(jsonNodeFee);
			arrayTotal.add(jsonResultFeeCon);
			
			int result = db()
					.update(DELIVER_FEE_TEMPLATE)
					.set(DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,param.getTemplateName())
	                .set(DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,arrayTotal.toString())
	                .where(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.eq(param.getDeliverTemplateId()))
	                .execute();
	                		
	        return result;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
     *修改运费模版
     *
     * @param param
     * @return 
     */
	public int updateDeliverWeightTemplate(GoodsDeliverTemplateParam param) {
				
		try {
			//** 复用ObjectMapper对象 */
			ObjectMapper objectMapper = new ObjectMapper();
			//* 外层模版类（不含包邮条件） */
			String jsonLimit = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateLimitParam());
			JsonNode jsonNodeLimit = objectMapper.readTree(jsonLimit);
			
			String jsonArea = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateAreaParam());
			JsonNode jsonNodeArea = objectMapper.readTree(jsonArea);
			
			ArrayNode arrayTemplate = objectMapper.createArrayNode();
			arrayTemplate.add(jsonNodeLimit);
			arrayTemplate.add(jsonNodeArea);
			//* 包邮条件 */
			String jsonFee = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeParam());
			JsonNode jsonNodeFee = objectMapper.readTree(jsonFee);
			
			String jsonFeeCon = objectMapper.writeValueAsString(param.getGoodsDeliverTemplateFeeConditionParam());
			JsonNode jsonNodeFeeCon = objectMapper.readTree(jsonFeeCon);
			ArrayNode arrayFeeCon = objectMapper.createArrayNode();
			arrayFeeCon.add(jsonNodeFeeCon);
			
			JsonNode jsonResultTemplate = objectMapper.createObjectNode().set("datalist", arrayTemplate);
			JsonNode jsonResultFeeCon = objectMapper.createObjectNode().set("fee_0_data_list", arrayFeeCon);
			
			ArrayNode arrayTotal = objectMapper.createArrayNode();
			arrayTotal.add(jsonResultTemplate);
			arrayTotal.add(jsonNodeFee);
			arrayTotal.add(jsonResultFeeCon);
			
			int result = db()
					.update(DELIVER_FEE_TEMPLATE)
					.set(DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,param.getTemplateName())
	                .set(DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,arrayTotal.toString())
	                .where(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.eq(param.getDeliverTemplateId()))
	                .execute();
	                		
	        return result;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
