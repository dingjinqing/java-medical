package com.vpu.mp.service.shop.market.integralconvert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.vpu.mp.db.shop.tables.IntegralMallDefine;
import com.vpu.mp.db.shop.tables.IntegralMallProduct;
import com.vpu.mp.db.shop.tables.IntegralMallRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.FieldsUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleIntegral;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.integralconvert.*;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.order.OrderReadService;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS;
import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.Tables.INTEGRAL_MALL_DEFINE;
import static com.vpu.mp.db.shop.Tables.INTEGRAL_MALL_PRODUCT;
import static com.vpu.mp.db.shop.Tables.INTEGRAL_MALL_RECORD;
import static com.vpu.mp.db.shop.Tables.USER;

/**
 * 积分兑换
 * 
 * @author liangchen
 * @date 2019年8月14日
 */
@Service
public class IntegralConvertService extends ShopBaseService {
	IntegralMallDefine imd = INTEGRAL_MALL_DEFINE.as("imd");
	IntegralMallProduct imp = INTEGRAL_MALL_PRODUCT.as("imp");
	IntegralMallRecord imr = INTEGRAL_MALL_RECORD.as("imr");
    /** 活动状态为启用 */
	public static final Byte NORMAL = 1;
    /** 默认查找上架和下架全部商品 */
    public static final Byte DEFAULT_SALE_STATUS = -1;
	@Autowired
	private OrderReadService orderReadService;
	@Autowired
	private MemberService memberService;

    /**
     * 积分兑换弹窗
     * @param param 商品名称 是否上架
     * @return 活动商品信息
     */
	public PageResult<PopListVo> getPopList(PopListParam param){
	    SelectConditionStep<? extends Record> select = db().select(imd.ID.as("integral_goods_id"),GOODS.GOODS_ID,GOODS.GOODS_NAME,GOODS.GOODS_IMG,
        GOODS.SHOP_PRICE.as("prd_price"),imp.STOCK.as("stock_sum"),imp.MONEY,imp.SCORE,imd.START_TIME,imd.END_TIME,GOODS.IS_ON_SALE,GOODS.DEL_FLAG.as("is_delete"))
            .from(imd)
            .leftJoin(GOODS).on(imd.GOODS_ID.eq(GOODS.GOODS_ID))
            .leftJoin(imp).on(imd.ID.eq(imp.INTEGRAL_MALL_DEFINE_ID))
            .where(imd.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(imd.STATUS.eq(NORMAL))
            .and(imd.START_TIME.lessThan(Util.currentTimeStamp()))
            .and(imd.END_TIME.greaterThan(Util.currentTimeStamp()));
	    if (!StringUtils.isNullOrEmpty(param.getGoodsName())){
	        select.and(GOODS.GOODS_NAME.like(likeValue(param.getGoodsName())));
        }
	    if (null!=param.getIsOnSale()&&!param.getIsOnSale().equals(DEFAULT_SALE_STATUS)){
            select.and(GOODS.IS_ON_SALE.eq(param.getIsOnSale()));
        }
	    //整合分页信息
        PageResult<PopListVo> result = getPageResult(select,param.getCurrentPage(),param.getPageRows(),PopListVo.class);
	    return result;
    }

	/**
	 * 积分兑换分页查询列表
	 *
	 * @param param
	 * @return PageResult<IntegralConvertListVo>
	 */
	public PageResult<IntegralConvertListVo> getList(IntegralConvertListParam param) {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		SelectConditionStep<? extends Record> sql = db().select(imd.ID, imd.NAME, GOODS.GOODS_ID, GOODS.GOODS_IMG, GOODS.GOODS_NAME, imd.START_TIME,
						imd.END_TIME, DSL.sum(imr.MONEY).as("money"), DSL.sum(imr.SCORE).as("score"),
						GOODS.GOODS_NUMBER, imp.STOCK, DSL.sum(imr.NUMBER).as("number"),
						DSL.count(imr.USER_ID).as("userNumber"))
				.from(imd).leftJoin(GOODS).on(imd.GOODS_ID.eq(GOODS.GOODS_ID)).leftJoin(imp)
				.on(imd.ID.eq(imp.INTEGRAL_MALL_DEFINE_ID)).leftJoin(imr).on(imd.ID.eq(imr.INTEGRAL_MALL_DEFINE_ID))
				.where(imd.DEL_FLAG.equal((byte)IntegralConvertConstant.NOT_DELETE));

		/* 活动状态0全部 */
		if (IntegralConvertConstant.ALL == param.getActState()) {

		}
		/* 活动状态1进行中 */
		if (IntegralConvertConstant.DOING == param.getActState()) {
			sql.and(imd.STATUS.eq((byte) IntegralConvertConstant.NOT_BLOCK)).and(imd.START_TIME.lessOrEqual(nowTime))
					.and(imd.END_TIME.greaterOrEqual(nowTime));
		}
		/* 活动状态2未开始 */
		if (IntegralConvertConstant.TODO == param.getActState()) {
			sql.and(imd.STATUS.eq((byte) IntegralConvertConstant.NOT_BLOCK))
					.and(imd.START_TIME.greaterOrEqual(nowTime));
		}
		/* 活动状态3已过期 */
		if (IntegralConvertConstant.DID == param.getActState()) {
			sql.and(imd.STATUS.eq((byte) IntegralConvertConstant.NOT_BLOCK)).and(imd.END_TIME.lessOrEqual(nowTime));
		}
		/* 活动状态0已停用 */
		if (IntegralConvertConstant.STOP == param.getActState()) {
			sql.and(imd.STATUS.eq((byte) IntegralConvertConstant.BLOCK));
		}
		sql.groupBy(imd.ID, imd.NAME, GOODS.GOODS_ID, GOODS.GOODS_IMG, GOODS.GOODS_NAME, imd.START_TIME, imd.END_TIME,
				GOODS.GOODS_NUMBER, imp.STOCK);
		PageResult<IntegralConvertListVo> listVo = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				IntegralConvertListVo.class);

		return listVo;
	}

	/**
	 * 停用或启用活动
	 *
	 * @param IntegralConvertSwitchParam
	 * @return
	 */
	public void startOrStop(IntegralConvertSwitchParam param) {
		/* 判断当前状态 停用中or启用中 */
		int status = db().select(imd.STATUS).from(imd).where(imd.ID.eq(param.getId())).fetchOptionalInto(Integer.class)
				.get();
		/* 若已停用 则启用 */
		if (status == IntegralConvertConstant.BLOCK) {
			db().update(imd).set(imd.STATUS, (byte) IntegralConvertConstant.NOT_BLOCK)
					.where(imd.ID.eq(param.getId())).execute();
		}
		/* 若启用中 则停用 */
		if (status == IntegralConvertConstant.NOT_BLOCK) {
			db().update(imd).set(imd.STATUS, (byte) IntegralConvertConstant.BLOCK).where(imd.ID.eq(param.getId()))
					.execute();
		}

	}

	/**
	 * 删除单个活动
	 *
	 * @param IntegralConvertSwitchParam
	 * @return void
	 */
	public void deleteAct(IntegralConvertSwitchParam param) {
		/* 修改del_flag */
		db().update(imd).set(imd.DEL_FLAG, (byte) IntegralConvertConstant.DELETE).where(imd.ID.eq(param.getId()))
				.execute();
	}
	/**
	 * 积分兑换用户列表
	 *
	 * @param IntegralConvertUserParam
	 * @return PageResult<IntegralConvertUserVo>
	 */
	public PageResult<IntegralConvertUserVo> userList(IntegralConvertUserParam param) {

		SelectConditionStep<? extends Record> sql = db()
				.select(imr.USER_ID, imr.ORDER_SN, imr.GOODS_ID, GOODS.GOODS_IMG, GOODS.GOODS_NAME, imr.MONEY,
						imr.SCORE, USER.USERNAME, USER.MOBILE, imr.NUMBER, imr.CREATE_TIME)
				.from(imr).leftJoin(GOODS).on(imr.GOODS_ID.eq(GOODS.GOODS_ID)).leftJoin(USER)
				.on(imr.USER_ID.eq(USER.USER_ID)).where(imr.INTEGRAL_MALL_DEFINE_ID.eq(param.getId()));
		/* 查询条件 */
		/* 用户昵称 */
		if (!StringUtils.isNullOrEmpty(param.getUsername())) {
			sql.and(USER.USERNAME.like(this.likeValue(param.getUsername())));
		}
		/* 手机号 */
		if (!StringUtils.isNullOrEmpty(param.getMobile())) {
			sql.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}

		/* 整合分页信息 */
		PageResult<IntegralConvertUserVo> pageResult = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				IntegralConvertUserVo.class);

		return pageResult;
	}

	/**
	 * 返回指定商品规格详情
	 *
	 * @param IntegralConvertGoodsParam
	 * @return List<IntegralConvertGoodsVo>
	 */
	public List<IntegralConvertGoodsVo> getproduct(IntegralConvertGoodsParam param) {

		List<IntegralConvertGoodsVo> sql = db().select().from(GOODS_SPEC_PRODUCT)
				.where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(param.getGoodsId()))
				.fetchInto(IntegralConvertGoodsVo.class);

		return sql;
	}

	/**
	 * 添加积分兑换活动
	 *
	 * @param IntegralConvertAddParam
	 * @return
	 */
	public void addAction(IntegralConvertAddParam param) {

		try {
			/* 入参对象解析为json字符串 */
			ObjectMapper objectMapper = new ObjectMapper();
			String shareCondfig = objectMapper.writeValueAsString(param.getConfigParam());
			/* 添加数据-活动信息表 */
			db().insertInto(INTEGRAL_MALL_DEFINE, INTEGRAL_MALL_DEFINE.NAME, INTEGRAL_MALL_DEFINE.START_TIME,
					INTEGRAL_MALL_DEFINE.END_TIME, INTEGRAL_MALL_DEFINE.GOODS_ID, INTEGRAL_MALL_DEFINE.SHARE_CONFIG)
					.values(param.getName(), param.getStartTime(), param.getEndTime(), param.getGoodsId(), shareCondfig)
					.execute();
			/* 得到刚添加到活动表的活动id */
			int imdId = db().lastID().intValue();
			/* 查找指定商品的所有规格 */
			List<Integer> productIds = db().select(GOODS_SPEC_PRODUCT.PRD_ID).from(GOODS_SPEC_PRODUCT)
					.where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(param.getGoodsId()))
					.fetchInto(Integer.class);
			/* 添加数据-活动规格信息表 */
			for (int i = 0; i < productIds.size(); i++) {
				db().insertInto(INTEGRAL_MALL_PRODUCT, INTEGRAL_MALL_PRODUCT.INTEGRAL_MALL_DEFINE_ID,
						INTEGRAL_MALL_PRODUCT.PRODUCT_ID, INTEGRAL_MALL_PRODUCT.MONEY, INTEGRAL_MALL_PRODUCT.SCORE,
						INTEGRAL_MALL_PRODUCT.STOCK)
						.values(imdId, productIds.get(i), param.getProductParam().get(i).getMoney(),
								param.getProductParam().get(i).getScore(), param.getProductParam().get(i).getStock())
						.execute();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询指定活动信息
	 *
	 * @param
	 * @return
	 */
	public IntegralConvertSelectVo selectOne(IntegralConvertSelectParam param) {

		IntegralConvertSelectVo selectVo = db()
				.select(imd.NAME, imd.START_TIME, imd.END_TIME, imd.MAX_EXCHANGE_NUM, imd.GOODS_ID, GOODS.GOODS_NAME,
						imd.SHARE_CONFIG)
				.from(imd).leftJoin(GOODS).on(imd.GOODS_ID.eq(GOODS.GOODS_ID)).where(imd.ID.eq(param.getId()))
				.fetchOneInto(IntegralConvertSelectVo.class);
		int goodId = selectVo.getGoodsId();

		List<Integer> productIds = db().select(GOODS_SPEC_PRODUCT.PRD_ID).from(GOODS_SPEC_PRODUCT)
				.where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodId))
				.fetchInto(Integer.class);

		List<IntegralConvertProductVo> productList = new ArrayList<IntegralConvertProductVo>(1024);
		for (int i = 0; i < productIds.size(); i++) {

			IntegralConvertProductVo listVo = db().select(imp.MONEY, imp.SCORE, imp.STOCK).from(imp)
					.where(imp.PRODUCT_ID.eq(productIds.get(i))).and(imp.INTEGRAL_MALL_DEFINE_ID.eq(param.getId()))
					.fetchOneInto(IntegralConvertProductVo.class);

			String prdDesc = db().select(GOODS_SPEC_PRODUCT.PRD_DESC).from(GOODS_SPEC_PRODUCT)
					.where(GOODS_SPEC_PRODUCT.PRD_ID.eq(productIds.get(i)))
					.fetchOptionalInto(String.class).orElse(null);

			listVo.setPrdDesc(prdDesc);

			productList.add(i, listVo);

		}
		selectVo.setProductVo(productList);

		return selectVo;
	}

	/**
	 * 修改积分兑换活动
	 *
	 * @param IntegralConvertAddParam
	 * @return
	 */
	public void updateAction(IntegralConvertAddParam param) {

		try {
			/* 入参对象解析为json字符串 */
			ObjectMapper objectMapper = new ObjectMapper();
			String shareCondfig = objectMapper.writeValueAsString(param.getConfigParam());
			/* 修改数据-活动信息表 */
			db().update(INTEGRAL_MALL_DEFINE).set(INTEGRAL_MALL_DEFINE.NAME, param.getName())
					.set(INTEGRAL_MALL_DEFINE.START_TIME, param.getStartTime())
					.set(INTEGRAL_MALL_DEFINE.END_TIME, param.getEndTime())
					.set(INTEGRAL_MALL_DEFINE.GOODS_ID, param.getGoodsId())
					.set(INTEGRAL_MALL_DEFINE.SHARE_CONFIG, shareCondfig)
					.where(INTEGRAL_MALL_DEFINE.ID.eq(param.getId())).execute();

			/* 查找指定商品的所有规格 */
			List<Integer> productIds = db().select(GOODS_SPEC_PRODUCT.PRD_ID).from(GOODS_SPEC_PRODUCT)
					.where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(param.getGoodsId()))
					.fetchInto(Integer.class);
			/* 修改数据-活动规格信息表 */
			for (int i = 0; i < productIds.size(); i++) {
				db().update(INTEGRAL_MALL_PRODUCT).set(INTEGRAL_MALL_PRODUCT.INTEGRAL_MALL_DEFINE_ID, param.getId())
						.set(INTEGRAL_MALL_PRODUCT.MONEY, param.getProductParam().get(i).getMoney())
						.set(INTEGRAL_MALL_PRODUCT.SCORE, param.getProductParam().get(i).getScore())
						.set(INTEGRAL_MALL_PRODUCT.STOCK, param.getProductParam().get(i).getStock())
						.where(INTEGRAL_MALL_PRODUCT.INTEGRAL_MALL_DEFINE_ID.eq(param.getId()))
						.and(INTEGRAL_MALL_PRODUCT.PRODUCT_ID.eq(productIds.get(i))).execute();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 活动新增用户
	 *
	 * @param param
	 * @return
	 */
	public PageResult<MemberInfoVo> integralNewUserList(MarketSourceUserListParam param) {
		MemberPageListParam pageListParam = new MemberPageListParam();
		pageListParam.setCurrentPage(param.getCurrentPage());
		pageListParam.setPageRows(param.getPageRows());
		pageListParam.setMobile(param.getMobile());
		pageListParam.setUsername(param.getUserName());
		pageListParam.setInviteUserName(param.getInviteUserName());
		return memberService.getSourceActList(pageListParam, MemberService.INVITE_SOURCE_INTEGRAL,
				param.getActivityId());
	}

	/**
	 * 查看积分兑换订单
	 *
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageResult<IntegralConvertOrderVo> interalOrderList(MarketOrderListParam param) {
		PageResult<MarketOrderListVo> pageList = saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param, BaseConstant.ACTIVITY_TYPE_INTEGRAL);

		List<IntegralConvertOrderVo> newList = new ArrayList<IntegralConvertOrderVo>(20);

		if (pageList.getDataList() != null) {
			pageList.getDataList().forEach(data -> {
				IntegralConvertOrderVo vo = new IntegralConvertOrderVo();
				FieldsUtil.assignNotNull(data, vo);

				int number = db().select(DSL.sum(imr.NUMBER)).from(imr).where(imr.ORDER_SN.eq(vo.getOrderSn()))
						.fetchOptionalInto(Integer.class).get();
				vo.setNumber(number);

				BigDecimal money = db().select(DSL.sum(imr.MONEY)).from(imr).where(imr.ORDER_SN.eq(vo.getOrderSn()))
						.fetchOptionalInto(BigDecimal.class).get();
				vo.setMoney(money);

				int score = db().select(DSL.sum(imr.SCORE)).from(imr).where(imr.ORDER_SN.eq(vo.getOrderSn()))
						.fetchOptionalInto(Integer.class).get();
				vo.setScore(score);
				
				newList.add(vo);
			});
		}
		PageResult<IntegralConvertOrderVo> pageResult = new PageResult<IntegralConvertOrderVo>();
		pageResult.setDataList(newList);
		pageResult.setPage(pageList.getPage());

		return pageResult;

	}

    /**
     * 小程序装修积分兑换模块显示异步调用
     * @param moduleIntegral
     * @return
     */
    public ModuleIntegral getPageIndexIntegral(ModuleIntegral moduleIntegral){

        return moduleIntegral;
    }
}
