package com.vpu.mp.service.shop.goods;

import com.vpu.mp.db.shop.tables.records.DeliverFeeTemplateRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.area.AreaProvinceVo;
import com.vpu.mp.service.pojo.shop.goods.deliver.*;
import org.jooq.Record4;
import org.jooq.SelectSeekStep1;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.DELIVER_FEE_TEMPLATE;


/**
 * 
 * 运费模版
 * 
 * @author liangchen
 * @date   2019年7月11日
 */
@Service

public class GoodsDeliverTemplateService extends ShopBaseService{
	
	/**
	 *	查询所有省、市、区、县
	 *
	 * @return List<AreaSelectVo>
	 */
	public List<AreaProvinceVo> getAllArea() {
		return saas.areaSelectService.getAllArea();
	}
	
	/**
	 * 运费模版列表
	 * 
	 * @param param flag传0:普通运费模板，传1:重量运费模板
	 * @return 分页信息
	 */
	public PageResult<GoodsDeliverTemplateVo> getDeliverTemplateList(GoodsDeliverPageListParam param) {
        //flag传0:普通运费模板， 传1:重量运费模板
		SelectSeekStep1<Record4<Integer, String, String, Byte>, Integer> selectFrom =
				db().select(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID,DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,DELIVER_FEE_TEMPLATE.FLAG)
				.from(DELIVER_FEE_TEMPLATE)
				.where(DELIVER_FEE_TEMPLATE.FLAG.eq(param.getFlag()))
				.orderBy(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.desc());
		
		PageResult<GoodsDeliverTemplateVo> pageResult = this.getPageResult(selectFrom, param.getCurrentPage(), param.getPageRows(), GoodsDeliverTemplateVo.class);
		return pageResult;
	}
	
	/**
     * 添加运费模版
     *
     * @param param 名称 类型标识 详细配置信息
     */
	public void addDeliverTemplate(GoodsDeliverTemplateParam param) {
	    //flag传0:普通运费模板， 传1:重量运费模板
        db().insertInto(DELIVER_FEE_TEMPLATE,
            DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,
            DELIVER_FEE_TEMPLATE.FLAG,
            DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT)
            .values(param.getTemplateName(),param.getFlag(),Util.toJsonNotNull(param.getContentParam()))
            .execute();
	}
	
	 /**
     * 真删除运费模版
     *
     * @param goodsDeliverIdParam 模板id
     */
    public void deleteDeliverTemplate(GoodsDeliverIdParam goodsDeliverIdParam) {
         db().deleteFrom(DELIVER_FEE_TEMPLATE)
        		.where(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.eq(goodsDeliverIdParam.getDeliverTemplateId()))
                .execute();
    }
    /**
	 * 修改模版前先查询单个模版的信息，将其参数作为修改时的默认值
	 * 
	 * @param param 模板id
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
     * @param param 模板id 名称 类型 详细配置信息
     */
	public void updateDeliverTemplate(GoodsDeliverTemplateParam param) {
				
			db().update(DELIVER_FEE_TEMPLATE)
					.set(DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,param.getTemplateName())
	                .set(DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,Util.toJsonNotNull(param.getContentParam()))
	                .where(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.eq(param.getDeliverTemplateId()))
	                .execute();

	}

	/**
	 *	运费模板下拉框
	 *
	 * @param 
	 * @return List<GoodsDeliverBoxVo>
	 */
	public List<GoodsDeliverBoxVo> getBox() {
		
		List<GoodsDeliverBoxVo> boxVo = db().select().from(DELIVER_FEE_TEMPLATE)
				.fetch().into(GoodsDeliverBoxVo.class);
		return boxVo;
	}
	/**
	 *	复制运费模板
	 *
	 * @param param 模板id
	 */
	public void copyTemplate(GoodsDeliverIdParam param) {
		/** 根据id查模板信息 */
		DeliverFeeTemplateRecord record = db().select(DELIVER_FEE_TEMPLATE.TEMPLATE_NAME,DELIVER_FEE_TEMPLATE.TEMPLATE_CONTENT,
				DELIVER_FEE_TEMPLATE.SHOP_ID,DELIVER_FEE_TEMPLATE.FLAG)
				.from(DELIVER_FEE_TEMPLATE)
				.where(DELIVER_FEE_TEMPLATE.DELIVER_TEMPLATE_ID.eq(param.getDeliverTemplateId()))
				.fetchOneInto(DELIVER_FEE_TEMPLATE);
		/** 重命名复制出来的副本 */
		String newName = record.getTemplateName()+" 副本";
		record.setTemplateName(newName);
		record.setTemplateContent(record.getTemplateContent());
		record.setShopId(record.getShopId());
		record.setFlag(record.getFlag());
		/** 将副本信息插入为一条新的模板信息 */
		db().executeInsert(record);
	}
}
