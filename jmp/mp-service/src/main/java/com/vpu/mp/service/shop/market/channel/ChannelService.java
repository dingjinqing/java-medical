package com.vpu.mp.service.shop.market.channel;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.tables.Channel.CHANNEL;
import static com.vpu.mp.db.shop.tables.XcxCustomerPage.XCX_CUSTOMER_PAGE;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelConstant;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelPageParam;
import com.vpu.mp.service.pojo.shop.market.channel.ChannelPageVo;
import com.vpu.mp.service.shop.decoration.ShopMpDecorationService;
import com.vpu.mp.service.shop.goods.GoodsService;

/**
 * @author huangronggang
 * @date 2019年8月14日
 * 渠道分析
 */
@Service
public class ChannelService extends ShopBaseService {
	
	@Autowired ShopMpDecorationService shopMpDecoration;
	@Autowired GoodsService goodsService;
	
	/**
	 * 渠道页面分析 分页查询
	 * @param param
	 * @return
	 */
	public PageResult<ChannelPageVo> getPageList(ChannelPageParam param){
//		 SelectJoinStep<Record> step = db().select().from(CHANNEL);
		SelectOnConditionStep<?> step = db().select(CHANNEL.ID, CHANNEL.PAGE_ID, DSL.iif(CHANNEL.SOURCE_TYPE.eq(ChannelConstant.SOURCETYPE_CUSTOMIZE), XCX_CUSTOMER_PAGE.PAGE_NAME, GOODS.GOODS_NAME), CHANNEL.CHANNEL_NAME, CHANNEL.SOURCE_TYPE, CHANNEL.SHARE, CHANNEL.DEL_FLAG, CHANNEL.CREATE_TIME)
		.from(CHANNEL).leftJoin(XCX_CUSTOMER_PAGE).on(CHANNEL.PAGE_ID.eq(XCX_CUSTOMER_PAGE.PAGE_ID))
		.leftJoin(GOODS).on(CHANNEL.GOODS_ID.eq(GOODS.GOODS_ID));
		 buildOptions(step, param);
		 PageResult<ChannelPageVo> result = getPageResult(step, param.getCurrentPage(), param.getPageRows(), ChannelPageVo.class);
		 if(result.dataList == null) {
			 return result;
		 }
//		 for(ChannelPageVo vo:result.dataList) {
//		 }
		 return result;
	}

	/**
	 * 如果是自定义类型渠道页面 将页面名称填入vo；
	 * 如果是商品类型的渠道页面 将商品名称填入vo中
	 * @param vo
	 */


	/**
	 * @param step
	 * @param param
	 * @return
	 */
	private void buildOptions(SelectJoinStep<?> step, ChannelPageParam param) {
		if(!StringUtils.isBlank(param.getChannelName())) {
			step.where(CHANNEL.CHANNEL_NAME.like(this.likeValue(param.getChannelName())));
		}
		if(param.getSourceType() != null) {
			step.where(CHANNEL.SOURCE_TYPE.eq(param.getSourceType()));
		}
		if(param.getStartTime() != null) {
			step.where(CHANNEL.CREATE_TIME.ge(param.getStartTime()));
		}
		if(param.getEndTime()!= null) {
			step.where(CHANNEL.CREATE_TIME.le(param.getEndTime()));
		}
		
//		if(!StringUtils.isBlank(param.getSourcePage())){
//			List<Integer> idList = shopMpDecoration.getIdByName(param.getSourcePage());
//			if(idList == null || idList.isEmpty()) {
//				return ;
//			}
//			step.where(CHANNEL.PAGE_ID.in(idList));
//		}
		step.orderBy(CHANNEL.CREATE_TIME);
		return ;
	}
}

