package com.vpu.mp.service.shop.market.bargain;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.BargainGoodsRecord;
import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfig;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfigVo;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleBargain;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.bargain.*;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisDataVo;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisTotalVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.MemberService;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.BargainGoods.BARGAIN_GOODS;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;

/**
 * @author 王兵兵
 *
 * 2019年7月24日
 */
@Service
public class BargainService extends ShopBaseService  {
	
	/**
	 *  砍价发起记录
	 */
	@Autowired public BargainRecordService bargainRecord;

    @Autowired
    private QrCodeService qrCode;
    @Autowired
    private DomainConfig domainConfig;

	
	/**
	 * 活动类型 固定人数
	 */
	public static final byte BARGAIN_TYPE_FIXED = 0;
	/**
	 * 活动类型 砍到区间内结算 
	 */
	public static final byte BARGAIN_TYPE_RANDOM = 1;

    /**
     * 任意金额结算模式的单次帮砍金额模式：0固定金额
     */
    public static final byte BARGAIN_MONEY_TYPE_FIXED = 0;
    /**
     * 任意金额结算模式的单次帮砍金额模式：1区间随机金额
     */
    public static final byte BARGAIN_MONEY_TYPE_RANDOM = 1;

    /**
     * 取holdDate的一下天
     * @param holdDate java.sql.Date类型
     * @return java.sql.Date
     */
    private Date getNextDay(Date holdDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(holdDate);
        calendar.add(Calendar.DATE, 1);
        return new Date(calendar.getTime().getTime());
    }
	
	/**
	 * 砍价活动列表分页查询
	 *
	 */
	public PageResult<BargainPageListQueryVo> getPageList(BargainPageListQueryParam param) {
		SelectWhereStep<? extends Record> select = db().select(
				BARGAIN.ID,BARGAIN.BARGAIN_NAME,BARGAIN.BARGAIN_TYPE,BARGAIN.START_TIME,BARGAIN.END_TIME,BARGAIN.STATUS,
				BARGAIN.GOODS_ID,BARGAIN.STOCK
				).
				from(BARGAIN);
        select = buildOptions(select,param);
		select.orderBy(BARGAIN.CREATE_TIME.desc());
        PageResult<BargainPageListQueryVo> page = getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainPageListQueryVo.class);
		page.dataList.forEach(vo -> {
            vo.setSuccessNumber(bargainRecord.getBargainRecordNumberByStatus(vo.getId(), BargainRecordService.STATUS_SUCCESS));
            vo.setBargainUserNumber(bargainRecord.getBargainRecordNumber(vo.getId()));
            vo.setCurrentState(Util.getActStatus(vo.getStatus(),vo.getStartTime(),vo.getEndTime()));
        });
        return page;
	}

    /**
     * 砍价活动列表分页查询
     *
     */
    public PageResult<BargainDecoratePageListQueryVo> getDecoratePageList(BargainPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(
            BARGAIN.ID,BARGAIN.BARGAIN_NAME,BARGAIN.BARGAIN_TYPE,BARGAIN.START_TIME,BARGAIN.END_TIME,BARGAIN.STATUS,
            BARGAIN_GOODS.STOCK,BARGAIN_GOODS.GOODS_ID,BARGAIN_GOODS.FLOOR_PRICE,BARGAIN_GOODS.EXPECTATION_PRICE,
            GOODS.GOODS_NAME,GOODS.GOODS_IMG,GOODS.SHOP_PRICE
        ).
            from(BARGAIN_GOODS).leftJoin(BARGAIN).on(BARGAIN.ID.eq(BARGAIN_GOODS.BARGAIN_ID))
            .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(BARGAIN_GOODS.GOODS_ID));
        select = buildOptions(select,param);
        if(!StringUtils.isEmpty(param.getKeywords())){
            select.where(BARGAIN.BARGAIN_NAME.contains(param.getKeywords()).or(GOODS.GOODS_NAME.contains(param.getKeywords())));
        }
        select.orderBy(BARGAIN.CREATE_TIME.desc());
        PageResult<BargainDecoratePageListQueryVo> page = getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainDecoratePageListQueryVo.class);
        page.dataList.forEach(vo -> {
            vo.setCurrentState(Util.getActStatus(vo.getStatus(),vo.getStartTime(),vo.getEndTime()));
            vo.setGoodsImg(domainConfig.imageUrl(vo.getGoodsImg()));
        });
        return page;
    }

    private SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select,BargainPageListQueryParam param){
        select.where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if(param.getState() != null && param.getState().length > 0) {
            /** 状态过滤*/
            Condition stateCondition = DSL.noCondition();
            Timestamp now = DateUtil.getLocalDateTime();
            for(byte state : param.getState()){
                if(state == 1){
                    stateCondition = stateCondition.or((BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(BARGAIN.START_TIME.lt(now)).and(BARGAIN.END_TIME.gt(now)));
                }
                if(state == 2){
                    stateCondition = stateCondition.or((BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(BARGAIN.START_TIME.gt(now)));
                }
                if(state == 3){
                    stateCondition = stateCondition.or((BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(BARGAIN.END_TIME.lt(now)));
                }
                if(state == 4){
                    stateCondition = stateCondition.or(BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_DISABLE));
                }
            }
            select.where(stateCondition);
        }

        return select;
    }
	
	/**
	 * 新建砍价活动
	 *
	 */
	public void addBargain(BargainAddParam param){
		BargainRecord record = db().newRecord(BARGAIN);
		assign(param,record);
		if(param.getShareConfig() != null) {
            if(param.getShareConfig().getShareAction().equals(PictorialShareConfig.CUSTOMER_IMG) && StringUtil.isNotEmpty(param.getShareConfig().getShareImg())){
                try {
                    param.getShareConfig().setShareImg(new URL(param.getShareConfig().getShareImg()).getPath());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
			record.setShareConfig(Util.toJson(param.getShareConfig()));
		}
		record.setGoodsId(Util.listToString(param.getBargainGoods().stream().map(BargainGoods::getGoodsId).collect(Collectors.toList())));
        record.setStock(param.getBargainGoods().stream().mapToInt((x)->x.getStock()).sum());

		this.transaction(()->{
            record.insert();
            int id = record.getId();
            for(BargainGoods bargainGoods : param.getBargainGoods()){
                BargainGoodsRecord bargainGoodsRecord = db().newRecord(BARGAIN_GOODS);
                assign(bargainGoods,bargainGoodsRecord);
                bargainGoodsRecord.setBargainId(id);
                bargainGoodsRecord.setSaleNum(0);
                bargainGoodsRecord.insert();
            }
        });

        if(param.getStartTime().before(DateUtil.getLocalDateTime()) && param.getEndTime().after(DateUtil.getLocalDateTime())){
            //活动已生效
            saas.getShopApp(getShopId()).shopTaskService.bargainTaskService.monitorGoodsType();
        }
	}
	
	/**
	 * 更新砍价活动
	 *
	 */
	public void updateBargain(BargainUpdateParam param) {
		BargainRecord record = new BargainRecord();
		assign(param,record);
		record.setBargainMax(param.getBargainMax());
		record.setBargainMin(param.getBargainMin());
        if(param.getShareConfig() != null) {
            if(param.getShareConfig().getShareAction().equals(PictorialShareConfig.CUSTOMER_IMG) && StringUtil.isNotEmpty(param.getShareConfig().getShareImg())){
                try {
                    param.getShareConfig().setShareImg(new URL(param.getShareConfig().getShareImg()).getPath());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            record.setShareConfig(Util.toJson(param.getShareConfig()));
        }
        this.transaction(()->{
            db().executeUpdate(record);
            if(CollectionUtils.isNotEmpty(param.getBargainGoods())){
                param.getBargainGoods().forEach(bargainGoods->{
                    BargainGoodsRecord bargainGoodsRecord = db().newRecord(BARGAIN_GOODS);
                    assign(bargainGoods,bargainGoodsRecord);
                    bargainGoodsRecord.update();
                });
            }
        });

		//刷新goodsType
		saas.getShopApp(getShopId()).shopTaskService.bargainTaskService.monitorGoodsType();
	}

    /**
     * 删除砍价活动
     *
     */
    public void delBargain(Integer id) {
        db().update(BARGAIN).
            set(BARGAIN.DEL_FLAG,DelFlag.DISABLE.getCode()).
            set(BARGAIN.DEL_TIME,DateUtil.getLocalDateTime()).
            where(BARGAIN.ID.eq(id)).
            execute();
        //刷新goodsType
        saas.getShopApp(getShopId()).shopTaskService.bargainTaskService.monitorGoodsType();
    }
	
	/**
	 *
	 * 编辑页面 取单个砍价活动信息
	 *
	 */
	public BargainUpdateVo getBargainByIsd(Integer bargainId) {
        BargainUpdateVo bargain = db().selectFrom(BARGAIN).where(BARGAIN.ID.eq(bargainId)).fetchOneInto(BargainUpdateVo.class);
		if(bargain != null) {
		    bargain.setBargainGoods(getBargainGoods(bargainId));
		    bargain.getBargainGoods().forEach(g->{
                GoodsRecord goodsView = saas.getShopApp(getShopId()).goods.getGoodsRecordById(g.getGoodsId());
                g.setGoodsName(goodsView.getGoodsName());
                g.setGoodsNumber(goodsView.getGoodsNumber());
                g.setShopPrice(goodsView.getShopPrice());
                g.setGoodsImg(domainConfig.imageUrl(goodsView.getGoodsImg()));
            });

            bargain.setShopShareConfig(Util.parseJson(bargain.getShareConfig(), PictorialShareConfigVo.class));
            if(bargain.getShopShareConfig() != null && StringUtil.isNotEmpty(bargain.getShopShareConfig().getShareImg())){
                bargain.getShopShareConfig().setShareImgFullUrl(domainConfig.imageUrl(bargain.getShopShareConfig().getShareImg()));
            }
            bargain.setMrkingVoucherList(saas().getShopApp(getShopId()).coupon.getCouponViewByIds(Util.splitValueToList(bargain.getMrkingVoucherId())));
            bargain.setRewardCouponList(saas().getShopApp(getShopId()).coupon.getCouponViewByIds(Util.splitValueToList(bargain.getRewardCouponId())));
			return bargain;
		}else {
			return null;
		}
	}

	private List<BargainGoodsUpdateVo> getBargainGoods(int bargainId){
	    return db().selectFrom(BARGAIN_GOODS).where(BARGAIN_GOODS.BARGAIN_ID.eq(bargainId)).fetchInto(BargainGoodsUpdateVo.class);
    }

	/**
	 * 砍价效果分析的echarts图表数据
	 *
	 *
	 */
	public BargainAnalysisDataVo getBargainAnalysisData(BargainAnalysisParam param){
		Map<Date,Integer> recordMap = saas().getShopApp(getShopId()).bargain.bargainRecord.getRecordAnalysis(param);
		Map<Date,Integer> userMap = saas().getShopApp(getShopId()).bargain.bargainRecord.getBargainUserAnalysis(param);

        MarketAnalysisParam marketParam = new MarketAnalysisParam();
        marketParam.setActId(param.getBargainId());
        marketParam.setInviteSource(MemberService.INVITE_SOURCE_BARGAIN);
        marketParam.setStartTime(param.getStartTime());
        marketParam.setEndTime(param.getEndTime());
        Map<Date,Integer> orderMap = saas().getShopApp(getShopId()).readOrder.getMarketOrderAnalysis(marketParam);
		Map<Date,Integer> sourceMap = saas().getShopApp(getShopId()).member.getMarketSourceUserAnalysis(marketParam);

		Date temDate = new Date(param.getStartTime().getTime());
		Date endTime = new Date(param.getEndTime().getTime());
		endTime = getNextDay(endTime);

		BargainAnalysisDataVo bargainAnalysisDataVo = new BargainAnalysisDataVo();

		/** 组装输出数据格式 */
		while(temDate.before(endTime)){
			/**发起砍价用户数*/
			if(recordMap.get(temDate) != null && recordMap.get(temDate) > 0){
				bargainAnalysisDataVo.getRecordNumber().add(recordMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getRecordNumber().add(0);
			}

			/**帮砍价用户数*/
			if(userMap.get(temDate) != null && userMap.get(temDate) > 0){
				bargainAnalysisDataVo.getUserNumber().add(userMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getUserNumber().add(0);
			}

			/**活动订单数*/
			if(orderMap.get(temDate) != null && orderMap.get(temDate) > 0){
				bargainAnalysisDataVo.getOrderNumber().add(orderMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getOrderNumber().add(0);
			}

			/**活动拉新用户数*/
			if(sourceMap.get(temDate) != null && sourceMap.get(temDate) > 0){
				bargainAnalysisDataVo.getSourceNumber().add(orderMap.get(temDate));
			}else{
				bargainAnalysisDataVo.getSourceNumber().add(0);
			}

			/**日期列表*/
			bargainAnalysisDataVo.getDateList().add(temDate);

			temDate = getNextDay(temDate);
		}
		BargainAnalysisTotalVo total = new BargainAnalysisTotalVo();
		total.setRecordTotal(bargainAnalysisDataVo.getRecordNumber().stream().mapToInt(Integer::intValue).sum());
		total.setUserTotal(bargainAnalysisDataVo.getUserNumber().stream().mapToInt(Integer::intValue).sum());
		total.setOrderTotal(bargainAnalysisDataVo.getOrderNumber().stream().mapToInt(Integer::intValue).sum());
		total.setSourceTotal(bargainAnalysisDataVo.getSourceNumber().stream().mapToInt(Integer::intValue).sum());
		bargainAnalysisDataVo.setTotal(total);
		return bargainAnalysisDataVo;
	}

    /**
     * 活动新增用户
     *
     * @param param
     */
    public PageResult<MemberInfoVo> getBargainSourceUserList(MarketSourceUserListParam param) {
        MemberPageListParam pageListParam = new MemberPageListParam();
        pageListParam.setCurrentPage(param.getCurrentPage());
        pageListParam.setPageRows(param.getPageRows());
        pageListParam.setMobile(param.getMobile());
        pageListParam.setUsername(param.getUserName());
        pageListParam.setInviteUserName(param.getInviteUserName());

        return saas().getShopApp(getShopId()).member.getSourceActList(pageListParam, MemberService.INVITE_SOURCE_BARGAIN, param.getActivityId());
    }

    /**
     * 砍价订单
     *
     */
	public PageResult<MarketOrderListVo> getBargainOrderList(MarketOrderListParam param) {
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param,BaseConstant.ACTIVITY_TYPE_BARGAIN);
    }

    /**
     * 获取小程序码
     */
    public ShareQrCodeVo getMpQrCode(Integer id) {

        //String pathParam="paramId="+id;
        //String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.BARGAIN_ITEM, pathParam);

        int goodsId = db().select(BARGAIN.GOODS_ID).from(BARGAIN).where(BARGAIN.ID.eq(id)).fetchAny().into(Integer.class);
        String pathParam=String.format("gid=%d&aid=%d&atp=%d", goodsId, id, BaseConstant.ACTIVITY_TYPE_BARGAIN);
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.GOODS_ITEM, pathParam);

        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.GOODS_ITEM.getPathUrl(pathParam));
        return vo;
    }

    private Condition isConflictingActTime(Timestamp startTime,Timestamp endTime){
        return (BARGAIN.START_TIME.ge(startTime).and(BARGAIN.START_TIME.le(endTime))).or(BARGAIN.END_TIME.ge(startTime).and(BARGAIN.END_TIME.le(endTime))).or(BARGAIN.START_TIME.le(startTime).and(BARGAIN.END_TIME.ge(endTime)));
    }

    /**
     * 取单个活动
     * @param actId
     * @return
     */
    public BargainRecord getBargainActById(int actId){
        return db().select().from(BARGAIN).where(BARGAIN.ID.eq(actId)).fetchOptionalInto(BargainRecord.class).orElse(null);
    }

    /**
     * 小程序装修砍价模块显示异步调用
     * @param moduleBargain
     * @return
     */
    public ModuleBargain getPageIndexBargain(ModuleBargain moduleBargain){
        moduleBargain.getBargainGoods().forEach(bargainGoods->{
            BargainRecord bargain = getBargainActById(bargainGoods.getActId());
            BargainGoodsRecord bargainGoodsRecord = getBargainGoods(bargainGoods.getActId(),bargainGoods.getGoodsId());
            GoodsRecord goodsInfo = saas.getShopApp(getShopId()).goods.getGoodsRecordById(bargainGoods.getGoodsId());

            //set goods info
            bargainGoods.setGoodsId(goodsInfo.getGoodsId());
            bargainGoods.setGoodsName(goodsInfo.getGoodsName());
            bargainGoods.setGoodsImg(domainConfig.imageUrl(goodsInfo.getGoodsImg()));
            bargainGoods.setGoodsPrice(goodsInfo.getShopPrice());
            bargainGoods.setUnit(goodsInfo.getUnit());
            bargainGoods.setGoodsIsDelete(goodsInfo.getDelFlag());
            bargainGoods.setIsOnSale(goodsInfo.getIsOnSale());
            bargainGoods.setGoodsNumber(goodsInfo.getGoodsNumber());

            //set prd info
            bargainGoods.setIsPrd(goodsInfo.getIsDefaultProduct());
            if(goodsInfo.getIsDefaultProduct().equals(GoodsConstant.IS_DEFAULT_PRODUCT_Y)){
                bargainGoods.setMaxPrice(goodsInfo.getShopPrice());
                bargainGoods.setPrdId(saas.getShopApp(getShopId()).goods.goodsSpecProductService.getDefaultPrdId(goodsInfo.getGoodsId()));
            }else{
                bargainGoods.setMaxPrice(saas.getShopApp(getShopId()).goods.goodsSpecProductService.getMaxPrdPrice(goodsInfo.getGoodsId()));
            }

            //set bargain info
            bargainGoods.setBargainPrice(bargain.getBargainType() == BARGAIN_TYPE_RANDOM ? bargainGoodsRecord.getFloorPrice() : bargainGoodsRecord.getExpectationPrice());
            bargainGoods.setActBeginTime(bargain.getStartTime());
            bargainGoods.setActEndTime(bargain.getEndTime());
            bargainGoods.setActDelFlag(bargain.getDelFlag());
            bargainGoods.setActStatus(bargain.getStatus());
            bargainGoods.setBargainNum(bargainGoodsRecord.getStock());
            if(bargain.getStartTime().after(DateUtil.getLocalDateTime())){
                //未开始
                bargainGoods.setTimeState((byte)0);
                bargainGoods.setRemainingTime((bargain.getStartTime().getTime() - DateUtil.getLocalDateTime().getTime())/1000);
            }else if(bargain.getEndTime().after(DateUtil.getLocalDateTime())){
                //进行中
                bargainGoods.setTimeState((byte)1);
                bargainGoods.setRemainingTime((bargain.getEndTime().getTime() - DateUtil.getLocalDateTime().getTime())/1000);
            }else{
                //已结束
                bargainGoods.setTimeState((byte)2);
            }

        });

        return moduleBargain;
    }

    /**
     * 更新砍价活动库存和销量，减少number个库存，增加number个销量，number可以是负数
     * @param bargainId
     * @param goodsId
     * @param number
     */
    public void updateBargainStock(int bargainId,int goodsId, int number){
        db().update(BARGAIN).set(BARGAIN.STOCK,BARGAIN.STOCK.sub(number)).set(BARGAIN.SALE_NUM,BARGAIN.SALE_NUM.add(number)).where(BARGAIN.ID.eq(bargainId)).execute();
        db().update(BARGAIN_GOODS).set(BARGAIN_GOODS.STOCK,BARGAIN_GOODS.STOCK.sub(number)).set(BARGAIN_GOODS.SALE_NUM,BARGAIN_GOODS.SALE_NUM.add(number)).where(BARGAIN_GOODS.BARGAIN_ID.eq(bargainId)).and(BARGAIN_GOODS.GOODS_ID.eq(goodsId)).execute();
    }

    /**
     * 导出
     * @param param
     * @param lang
     * @return
     */
    public Workbook exportBargainOrderList(MarketOrderListParam param,String lang){
        List<MarketOrderListVo> list = saas.getShopApp(getShopId()).readOrder.marketOrderInfo.getMarketOrderList(param, BaseConstant.ACTIVITY_TYPE_BARGAIN);

        List<BargainOrderExportVo> res = new ArrayList<>();
        list.forEach(order->{
            BargainOrderExportVo vo = new BargainOrderExportVo();
            vo.setOrderSn(order.getOrderSn());
            vo.setGoodsName(order.getGoods().get(0).getGoodsName());
            vo.setPrice(order.getGoods().get(0).getGoodsPrice());
            vo.setCreateTime(order.getCreateTime());
            vo.setUsername(order.getUsername() + ";" + (StringUtil.isNotBlank(order.getUserMobile()) ? order.getUserMobile() : ""));
            vo.setConsignee(order.getConsignee() + ";" + order.getMobile());
            vo.setOrderStatus(OrderConstant.getOrderStatusName(order.getOrderStatus(),lang));

            res.add(vo);
        });

        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(res, BargainOrderExportVo.class);
        return workbook;
    }

    public BargainGoodsRecord getBargainGoods(int bargainId,int goodsId){
        return db().fetchAny(BARGAIN_GOODS,BARGAIN_GOODS.BARGAIN_ID.eq(bargainId).and(BARGAIN_GOODS.GOODS_ID.eq(goodsId)));
    }
}
