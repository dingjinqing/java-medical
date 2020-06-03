package com.vpu.mp.service.shop.user.user;

import static com.vpu.mp.db.shop.Tables.CHECKED_GOODS_CART;
import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;

import java.math.BigDecimal;

import com.vpu.mp.db.shop.tables.CheckedGoodsCart;
import com.vpu.mp.db.shop.tables.records.CheckedGoodsCartRecord;

import org.jooq.impl.DSL;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;


import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.member.card.CardConstant;
import com.vpu.mp.service.pojo.wxapp.user.UserCheckedGoodsParam;
import com.vpu.mp.service.pojo.wxapp.user.UserCheckedGoodsVo;
/**
 * 用户已选商品服务
 * @author 黄壮壮
 */
@Service
public class UserCheckedGoodsService extends ShopBaseService {
	private final CheckedGoodsCart TABLE = CHECKED_GOODS_CART;
	
	/**
	 * 获得用户已经选择的商品数量
	 * @return 商品数量 没有则为0
	 */
	public Integer getUserCheckedCount(UserCheckedGoodsParam param) {
		logger().info("获得已选商品数");
		Condition condition = getSearchCheckedGoodsCondition(param);
		
		return db().select(DSL.sum(TABLE.GOODS_NUMBER))
			.from(TABLE)
			.where(condition)
			.fetchOne(0,int.class);
	}

	private Condition getSearchCheckedGoodsCondition(UserCheckedGoodsParam param) {
		Condition condition = DSL.noCondition()
			.and(TABLE.USER_ID.eq(param.getUserId()))
			.and(TABLE.ACTION.eq(param.getAction()))
			.and(TABLE.IDENTITY_ID.eq(param.getIdentityId()));
		return condition;
	}
	
	/**
	 * 获得用户已选商品
	 * @return 
	 */
	public CheckedGoodsCartRecord getUserCheckedGoods(UserCheckedGoodsParam param) {
		Condition condition = getSearchCheckedGoodsCondition(param);
		condition = condition.and(TABLE.PRODUCT_ID.eq(param.getProductId()))
				 			 .and(TABLE.GOODS_ID.eq(param.getGoodsId()));
		return db().selectFrom(TABLE).where(condition).fetchAny();
	}
	
	/**
	 * 获得已选商品列表
	 * @return 
	 */
	public PageResult<UserCheckedGoodsVo> getUsercheckedList(UserCheckedGoodsParam param) {
		Condition condition = getSearchCheckedGoodsCondition(param);
		SelectConditionStep<Record> select = db().select(TABLE.fields())
			.select(GOODS.GOODS_NAME,GOODS.GOODS_SN,GOODS.MARKET_PRICE,GOODS.GOODS_IMG,GOODS.IS_ON_SALE,GOODS.GOODS_TYPE)
			.select(GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_SN,GOODS_SPEC_PRODUCT.PRD_SPECS,GOODS_SPEC_PRODUCT.PRD_DESC,GOODS_SPEC_PRODUCT.PRD_IMG)
			.from(TABLE)
			.leftJoin(GOODS).on(TABLE.GOODS_ID.eq(GOODS.GOODS_ID))
			.leftJoin(GOODS_SPEC_PRODUCT).on(TABLE.PRODUCT_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID))
			.where(condition);
		
		return getPageResult(select, param.getCurrentPage(), param.getPageRows(), UserCheckedGoodsVo.class);
	}

	/**
	 * 删除记录
	 * @param param
	 */
	public void removeChoosedGoods(UserCheckedGoodsParam param) {
		db().deleteFrom(TABLE)
			.where(TABLE.IDENTITY_ID.eq(param.getIdentityId()))
			.and(TABLE.PRODUCT_ID.eq(param.getProductId()))
			.and(TABLE.GOODS_ID.eq(param.getGoodsId()))
			.and(TABLE.ACTION.eq(CardConstant.MCARD_TP_LIMIT))
			.execute();
	}

}
