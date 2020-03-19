package com.vpu.mp.service.shop.market.integralconvert;

import com.mysql.cj.util.StringUtils;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.IntegralMallDefine;
import com.vpu.mp.db.shop.tables.IntegralMallProduct;
import com.vpu.mp.db.shop.tables.IntegralMallRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.IntegralMallDefineRecord;
import com.vpu.mp.db.shop.tables.records.IntegralMallProductRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
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
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.member.MemberService;
import jodd.util.StringUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * 积分兑换
 * 
 * @author liangchen
 * @date 2019年8月14日
 */
@Service
public class IntegralConvertService extends ShopBaseService {
    private IntegralMallDefine imd = INTEGRAL_MALL_DEFINE.as("imd");
    protected IntegralMallProduct imp = INTEGRAL_MALL_PRODUCT.as("imp");
    protected IntegralMallRecord imr = INTEGRAL_MALL_RECORD.as("imr");
    /** 活动状态为启用 */
	public static final Byte NORMAL = 1;
    /** 默认查找上架和下架全部商品 */
    public static final Byte DEFAULT_SALE_STATUS = -1;
	@Autowired
	private MemberService memberService;
	@Autowired
    private DomainConfig domainConfig;
    @Autowired
    private GoodsService goodsService;

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
	    for (PopListVo vo :result.dataList){
	        vo.setGoodsImg(domainConfig.imageUrl(vo.getGoodsImg()));
        }
	    return result;
    }

	/**
	 * 积分兑换分页查询列表
	 *
	 * @param param 活动状态
	 * @return 活动列表
	 */
	public PageResult<IntegralConvertListVo> getList(IntegralConvertListParam param) {
		Timestamp nowTime = new Timestamp(System.currentTimeMillis());
		SelectConditionStep<? extends Record> sql = db().select(imd.ID, imd.NAME, GOODS.GOODS_ID, GOODS.GOODS_IMG, GOODS.GOODS_NAME, imd.START_TIME,
						imd.END_TIME, DSL.sum(imr.MONEY).as("money"), DSL.sum(imr.SCORE).as("score"),
						GOODS.GOODS_NUMBER, imp.STOCK, DSL.sum(imr.NUMBER).as("number"),
						DSL.count(imr.USER_ID).as("user_number"),imd.STATUS,imd.DEL_FLAG)
				.from(imd).leftJoin(GOODS).on(imd.GOODS_ID.eq(GOODS.GOODS_ID)).leftJoin(imp)
				.on(imd.ID.eq(imp.INTEGRAL_MALL_DEFINE_ID)).leftJoin(imr).on(imd.ID.eq(imr.INTEGRAL_MALL_DEFINE_ID))
				.where(imd.DEL_FLAG.equal(IntegralConvertConstant.NOT_DELETE));

		// 活动状态0全部
		if (IntegralConvertConstant.ALL == param.getActState()) {

		}
		// 活动状态1进行中
		if (IntegralConvertConstant.DOING == param.getActState()) {
			sql.and(imd.STATUS.eq(IntegralConvertConstant.NOT_BLOCK)).and(imd.START_TIME.lessOrEqual(nowTime))
					.and(imd.END_TIME.greaterOrEqual(nowTime));
		}
		// 活动状态2未开始
		if (IntegralConvertConstant.TODO == param.getActState()) {
			sql.and(imd.STATUS.eq( IntegralConvertConstant.NOT_BLOCK))
					.and(imd.START_TIME.greaterOrEqual(nowTime));
		}
		// 活动状态3已过期
		if (IntegralConvertConstant.DID == param.getActState()) {
			sql.and(imd.STATUS.eq( IntegralConvertConstant.NOT_BLOCK)).and(imd.END_TIME.lessOrEqual(nowTime));
		}
		// 活动状态0已停用
		if (IntegralConvertConstant.STOP == param.getActState()) {
			sql.and(imd.STATUS.eq( IntegralConvertConstant.BLOCK));
		}
		sql.groupBy(imd.ID, imd.NAME, GOODS.GOODS_ID, GOODS.GOODS_IMG, GOODS.GOODS_NAME, imd.START_TIME, imd.END_TIME,
				GOODS.GOODS_NUMBER, imp.STOCK,imd.STATUS,imd.DEL_FLAG)
        .orderBy(imd.ID.desc());
		PageResult<IntegralConvertListVo> listVo = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				IntegralConvertListVo.class);
        for (IntegralConvertListVo item:listVo.getDataList()){
            //4 已停用
            if (item.getStatus()==(byte)0){
                item.setActStatus(4);
            }else {
                //1 进行中
                if (nowTime.after(item.getStartTime())&&nowTime.before(item.getEndTime())){
                    item.setActStatus(1);
                }
                //2 未开始
                else if (nowTime.before(item.getStartTime())){
                    item.setActStatus(2);
                }
                //3 已结束
                else if (nowTime.after(item.getEndTime())){
                    item.setActStatus(3);
                }
            }
        }
		return listVo;
	}

	/**
	 * 停用或启用活动
	 *
	 * @param param 活动id
	 */
	public void startOrStop(IntegralConvertId param) {
		//判断当前状态 停用中or启用中
		Byte status = db().select(imd.STATUS).from(imd).where(imd.ID.eq(param.getId())).fetchOptionalInto(Byte.class)
				.get();
		// 若已停用 则启用
		if (IntegralConvertConstant.BLOCK.equals(status)) {
			db().update(imd).set(imd.STATUS,  IntegralConvertConstant.NOT_BLOCK)
					.where(imd.ID.eq(param.getId())).execute();
		}
		// 若启用中 则停用
		if (IntegralConvertConstant.NOT_BLOCK.equals(status)) {
			db().update(imd).set(imd.STATUS,  IntegralConvertConstant.BLOCK).where(imd.ID.eq(param.getId()))
					.execute();
		}

	}

	/**
	 * 删除单个活动
	 *
	 * @param param 活动id
	 */
	public void deleteAct(IntegralConvertId param) {
		// 修改del_flag
		db().update(imd).set(imd.DEL_FLAG, IntegralConvertConstant.DELETE).where(imd.ID.eq(param.getId()))
				.execute();
	}

	/**
	 * 积分兑换用户列表
	 *
	 * @param param 用户名or手机号
	 * @return 用户/订单/积分兑换信息
	 */
	public PageResult<IntegralConvertUserVo> userList(IntegralConvertUserParam param) {

		SelectConditionStep<? extends Record> sql = db().select(imr.USER_ID, imr.ORDER_SN, imr.GOODS_ID, GOODS.GOODS_IMG,
            GOODS.GOODS_NAME, imr.MONEY, imr.SCORE, USER.USERNAME, USER.MOBILE, imr.NUMBER, imr.CREATE_TIME)
				.from(imr).leftJoin(GOODS).on(imr.GOODS_ID.eq(GOODS.GOODS_ID)).leftJoin(USER)
				.on(imr.USER_ID.eq(USER.USER_ID)).where(imr.INTEGRAL_MALL_DEFINE_ID.eq(param.getId()));
		// 用户昵称
		if (!StringUtils.isNullOrEmpty(param.getUsername())) {
			sql.and(USER.USERNAME.like(this.likeValue(param.getUsername())));
		}
		// 手机号
		if (!StringUtils.isNullOrEmpty(param.getMobile())) {
			sql.and(USER.MOBILE.like(this.likeValue(param.getMobile())));
		}
        //降序排序
        sql.orderBy(imr.ID.desc());
		// 整合分页信息
		PageResult<IntegralConvertUserVo> pageResult = getPageResult(sql, param.getCurrentPage(), param.getPageRows(),
				IntegralConvertUserVo.class);

		return pageResult;
	}

	/**
	 * 返回指定商品规格详情
	 *
	 * @param param
	 * @return List<IntegralConvertGoodsVo>
	 */
	public List<IntegralConvertGoodsVo> getProduct(IntegralConvertGoodsParam param) {

		List<IntegralConvertGoodsVo> sql = db().select().from(GOODS_SPEC_PRODUCT)
				.where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(param.getGoodsId()))
				.fetchInto(IntegralConvertGoodsVo.class);

		return sql;
	}

	/**
	 * 添加积分兑换活动
	 *
	 * @param param 活动详情
	 */
	public void addAction(IntegralConvertAddParam param) {

			// 入参对象解析为json字符串
			String shareConfig = Util.toJson(param.getShareConfig());

			// 添加数据-活动信息表
			db().insertInto(INTEGRAL_MALL_DEFINE, imd.NAME, imd.START_TIME, imd.END_TIME, imd.MAX_EXCHANGE_NUM,
                    imd.GOODS_ID, imd.SHARE_CONFIG)
					.values(param.getName(), param.getStartTime(), param.getEndTime(),param.getMaxExchangeNum(),
                        param.getGoodsId(), shareConfig)
					.execute();
			// 得到刚添加到活动表的活动id
			int imdId = db().lastID().intValue();
            // 添加数据-活动规格信息表
			for (IntegralConvertProductVo item : param.getProduct()){
                db().insertInto(INTEGRAL_MALL_PRODUCT, imp.INTEGRAL_MALL_DEFINE_ID, imp.PRODUCT_ID, imp.MONEY, imp.SCORE, imp.STOCK)
                    .values(imdId, item.getPrdId(),item.getMoney(),item.getScore(),item.getStock())
                    .execute();
            }
	}

	/**
	 * 查询指定活动信息
	 *
	 * @param param 活动id
	 * @return 活动信息
	 */
	public IntegralConvertSelectVo selectOne(IntegralConvertSelectParam param) {

		IntegralConvertSelectVo selectVo = db().select(imd.ID,imd.NAME, imd.START_TIME, imd.END_TIME, imd.MAX_EXCHANGE_NUM, imd.GOODS_ID, GOODS.GOODS_NAME,
						imd.SHARE_CONFIG,imd.DEL_FLAG,imd.STATUS)
				.from(imd).leftJoin(GOODS).on(imd.GOODS_ID.eq(GOODS.GOODS_ID)).where(imd.ID.eq(param.getId()))
				.fetchOneInto(IntegralConvertSelectVo.class);
		int goodId = selectVo.getGoodsId();
        //当前商品的所有规格
		List<Integer> productIds = db().select(GOODS_SPEC_PRODUCT.PRD_ID).from(GOODS_SPEC_PRODUCT)
				.where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodId))
				.fetchInto(Integer.class);

		List<IntegralConvertProductVo> productList = new ArrayList<>();
		//遍历规格
		for (Integer prdId : productIds){
            IntegralConvertProductVo listVo = db().select(imp.MONEY, imp.SCORE, imp.STOCK,GOODS_SPEC_PRODUCT.PRD_ID,GOODS_SPEC_PRODUCT.PRD_DESC
                ,GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_NUMBER)
                .from(GOODS_SPEC_PRODUCT)
                .leftJoin(imp).on(imp.PRODUCT_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID))
                .where(imp.PRODUCT_ID.eq(prdId)).and(imp.INTEGRAL_MALL_DEFINE_ID.eq(param.getId()))
                .fetchOneInto(IntegralConvertProductVo.class);
            productList.add(listVo);
        }
		selectVo.setProductVo(productList);
        selectVo.setObjectShareConfig(Util.json2Object(selectVo.shareConfig,IntegralConvertShareConfig.class,false));
		return selectVo;
	}

	/**
	 * 修改积分兑换活动
	 *
	 * @param param 活动信息
	 */
	public void updateAction(IntegralConvertAddParam param) {

        // 入参对象解析为json字符串
        String shareConfig = Util.toJson(param.getShareConfig());
			// 修改数据-活动信息表
			db().update(imd).set(imd.NAME, param.getName())
					.set(imd.START_TIME, param.getStartTime())
					.set(imd.END_TIME, param.getEndTime())
                    .set(imd.MAX_EXCHANGE_NUM,param.getMaxExchangeNum())
					.set(imd.GOODS_ID, param.getGoodsId())
					.set(imd.SHARE_CONFIG, shareConfig)
					.where(imd.ID.eq(param.getId())).execute();
			//修改数据-活动规格信息表
			for (IntegralConvertProductVo item : param.getProduct()) {
			    IntegralMallProductRecord record = db().select().from(imp)
                    .where(imp.INTEGRAL_MALL_DEFINE_ID.eq(param.getId()))
                    .and(imp.PRODUCT_ID.eq(item.getPrdId()))
                    .fetchOneInto(IntegralMallProductRecord.class);
			    //不为空-更新
			    if (record!=null){
                    db().update(imp)
                        .set(imp.MONEY, item.getMoney())
                        .set(imp.SCORE, item.getScore())
                        .set(imp.STOCK, item.getStock())
                        .where(imp.INTEGRAL_MALL_DEFINE_ID.eq(param.getId()))
                        .and(imp.PRODUCT_ID.eq(item.getPrdId())).execute();
                }
			    //为空-插入
				else {
                    db().insertInto(INTEGRAL_MALL_PRODUCT, imp.INTEGRAL_MALL_DEFINE_ID, imp.PRODUCT_ID, imp.MONEY, imp.SCORE, imp.STOCK)
                        .values(param.getId(), item.getPrdId(),item.getMoney(),item.getScore(),item.getStock())
                        .execute();
                }
			}

	}

	/**
	 * 活动新增用户
	 *
	 * @param param
	 * @return
	 */
	public PageResult<IntegralNewUser> integralNewUserList(MarketSourceUserListParam param) {
		MemberPageListParam pageListParam = new MemberPageListParam();
		pageListParam.setCurrentPage(param.getCurrentPage());
		pageListParam.setPageRows(param.getPageRows());
		pageListParam.setMobile(param.getMobile());
		pageListParam.setUsername(param.getUserName());
		pageListParam.setInviteUserName(param.getInviteUserName());
        PageResult<MemberInfoVo> result =  memberService.getSourceActList(pageListParam, MemberService.INVITE_SOURCE_INTEGRAL,
				param.getActivityId());
        String actName = db().select(INTEGRAL_MALL_DEFINE.NAME).from(INTEGRAL_MALL_DEFINE)
            .where(INTEGRAL_MALL_DEFINE.ID.eq(param.getActivityId()))
            .fetchOptionalInto(String.class).get();
        List<IntegralNewUser> userList = new ArrayList<>();
        if (result.getDataList()!=null&&result.getDataList().size()>0){
            for (MemberInfoVo item:result.getDataList()){
                IntegralNewUser newUser = new IntegralNewUser();
                FieldsUtil.assignNotNull(item,newUser);
                newUser.setActName(actName);
                userList.add(newUser);
            }
        }
        PageResult<IntegralNewUser> pageResult = new PageResult<>();
        pageResult.setDataList(userList);
        pageResult.setPage(result.getPage());
        return pageResult;
	}

	/**
	 * 查看积分兑换订单
	 *
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageResult<IntegralConvertOrderVo> integralOrderList(MarketOrderListParam param) {
		PageResult<MarketOrderListVo> pageList = saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param, BaseConstant.ACTIVITY_TYPE_INTEGRAL);

		List<IntegralConvertOrderVo> newList = new ArrayList<>(20);

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

				Integer productId = db().select(ORDER_GOODS.PRODUCT_ID)
                    .from(ORDER_GOODS)
                    .where(ORDER_GOODS.ORDER_SN.eq(vo.getOrderSn()))
                    .fetchOneInto(Integer.class);
                OrderTempVo orderTempVo = db().select(GOODS.GOODS_NAME,GOODS.GOODS_IMG,GOODS_SPEC_PRODUCT.PRD_DESC,
                    GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_NUMBER)
                    .from(GOODS_SPEC_PRODUCT)
                    .leftJoin(GOODS).on(GOODS_SPEC_PRODUCT.GOODS_ID.eq(GOODS.GOODS_ID))
                    .where(GOODS_SPEC_PRODUCT.PRD_ID.eq(productId))
                    .fetchOneInto(OrderTempVo.class);
                vo.setGoodsName(orderTempVo.getGoodsName());
                vo.setGoodsImg(orderTempVo.getGoodsImg());
                vo.setPrdDesc(orderTempVo.getPrdDesc());
                vo.setGoodsPrice(orderTempVo.getPrdPrice());
                vo.setNumber(orderTempVo.getPrdNumber());

				newList.add(vo);
			});
		}
		PageResult<IntegralConvertOrderVo> pageResult = new PageResult<>();
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
        if(!moduleIntegral.getIntegralGoods().isEmpty()){
            for(ModuleIntegral.IntegralGoods g : moduleIntegral.getIntegralGoods()){
                IntegralMallDefineRecord integralMallDefineRecord = db().fetchAny(imd,imd.ID.eq(g.getIntegralGoodsId()));
                GoodsRecord goodsRecord = goodsService.getGoodsRecordById(g.getGoodsId());
                if(goodsRecord == null || goodsRecord.getDelFlag().equals(DelFlag.DISABLE_VALUE)){
                    g.setTip((byte)1);
                }else if(integralMallDefineRecord == null || integralMallDefineRecord.getDelFlag().equals(DelFlag.DISABLE_VALUE)){
                    g.setTip((byte)2);
                }else if(integralMallDefineRecord.getStatus().equals(BaseConstant.ACTIVITY_STATUS_DISABLE)){
                    g.setTip((byte)3);
                }else if(integralMallDefineRecord.getStartTime().after(DateUtil.getLocalDateTime())){
                    g.setTip((byte)4);
                }else if(integralMallDefineRecord.getEndTime().before(DateUtil.getLocalDateTime())){
                    g.setTip((byte)5);
                }else {
                    g.setTip((byte)0);
                }

                if(integralMallDefineRecord != null){
                    g.setActDelFlag(integralMallDefineRecord.getDelFlag());
                    g.setStartTime(integralMallDefineRecord.getStartTime());
                    g.setEndTime(integralMallDefineRecord.getEndTime());
                }
                IntegralMallProductRecord integralMallProductRecord = getMinScoreIntegralMallProduct(g.getIntegralGoodsId());
                g.setScore(integralMallProductRecord.getScore());
                g.setMoney(integralMallProductRecord.getMoney());

                if(goodsRecord != null){
                    g.setGoodsIsDelete(goodsRecord.getDelFlag());
                }

                if(goodsRecord != null){
                    g.setGoodsId(goodsRecord.getGoodsId());
                    g.setGoodsName(goodsRecord.getGoodsName());
                    g.setGoodsIsDelete(goodsRecord.getDelFlag());
                    g.setGoodsNumber(goodsRecord.getGoodsNumber());
                    if(StringUtil.isNotEmpty(goodsRecord.getGoodsImg())){
                        g.setGoodsImg(domainConfig.imageUrl(goodsRecord.getGoodsImg()));
                    }
                    g.setPrdPrice(goodsService.goodsSpecProductService.getMaxPrdPrice(g.getGoodsId()));
                }

            }
        }

        return moduleIntegral;
    }

    /**
     * 积分最小的一个
     * @param integralMallDefineId
     * @return
     */
    private IntegralMallProductRecord getMinScoreIntegralMallProduct(int integralMallDefineId){
        return db().selectFrom(imp).where(imp.INTEGRAL_MALL_DEFINE_ID.eq(integralMallDefineId)).orderBy(imp.SCORE.asc()).fetchAny();
    }

    /**
     * 订单详情表格导出
     * @param param 查询信息
     * @param lang 语言
     * @return
     */
    public Workbook orderExport(MarketOrderListParam param, String lang){
        List<IntegralOrderExport> orderExportList = new ArrayList<>();
        PageResult<IntegralConvertOrderVo> pageResult = integralOrderList(param);
        List<IntegralConvertOrderVo> tempList = pageResult.getDataList();
        for (IntegralConvertOrderVo item:tempList){
            IntegralOrderExport tempExport = new IntegralOrderExport();
            tempExport.setOrderSn(item.getOrderSn());
            tempExport.setGoods(item.getGoodsName()+" "+item.getPrdDesc());
            tempExport.setGoodsPrice(item.getGoodsPrice());
            tempExport.setNumber(item.getNumber());
            tempExport.setMoney(item.getMoney());
            tempExport.setScore(item.getScore());
            tempExport.setUser(item.getUsername()+" "+item.getUserMobile());
            tempExport.setReceiveUser(item.getConsignee()+" "+item.getMobile());
            switch (item.getOrderStatus()){
                case OrderConstant.ORDER_WAIT_PAY:
                    tempExport.setOrderStatus("待付款");
                    break;
                case OrderConstant.ORDER_CANCELLED:
                    tempExport.setOrderStatus("客户已取消");
                    break;
                case OrderConstant.ORDER_CLOSED:
                    tempExport.setOrderStatus("卖家关闭");
                    break;
                case OrderConstant.ORDER_WAIT_DELIVERY:
                    tempExport.setOrderStatus("待发货");
                    break;
                case OrderConstant.ORDER_SHIPPED:
                    tempExport.setOrderStatus("已发货");
                    break;
                case OrderConstant.ORDER_FINISHED:
                    tempExport.setOrderStatus("已完成");
                    break;
                default:
                    tempExport.setOrderStatus("订单完成");
            }
            orderExportList.add(tempExport);
        }
        //表格导出
        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(orderExportList, IntegralOrderExport.class);
        return workbook;
    }

    /**
     * 用户列表表格导出
     * @param param 查询信息
     * @param lang 语言
     * @return
     */
    public Workbook userExport(IntegralConvertUserParam param, String lang){
        List<IntegralUserExport> exportList = new ArrayList<>();
        PageResult<IntegralConvertUserVo> userList = userList(param);
        for (IntegralConvertUserVo item : userList.getDataList()){
            IntegralUserExport export = new IntegralUserExport();
            FieldsUtil.assignNotNull(item,export);
            exportList.add(export);
        }
        //表格导出
        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(exportList, IntegralUserExport.class);
        return workbook;
    }
}
