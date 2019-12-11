package com.vpu.mp.service.shop.market.bargain;

import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
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
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
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
	
	/**
	 *  帮忙砍价的用户
	 */
	@Autowired public BargainUserService bargainUser;

    @Autowired
    private QrCodeService qrCode;
	
	/**
	 * 启用状态 
	 */
	public static final byte STATUS_NORMAL = 1;
	/**
	 * 停用状态 
	 */
	public static final byte STATUS_DISABLED = 0;
	
	/**
	 * 活动类型 固定人数
	 */
	public static final byte BARGAIN_TYPE_FIXED = 0;
	/**
	 * 活动类型 砍到区间内结算 
	 */
	public static final byte BARGAIN_TYPE_RANDOM = 1;

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
				BARGAIN.GOODS_ID,BARGAIN.STOCK,BARGAIN.EXPECTATION_PRICE,
				GOODS.GOODS_NAME,GOODS.GOODS_NUMBER,GOODS.GOODS_IMG,GOODS.SHOP_PRICE,GOODS.IS_ON_SALE
				).
				from(BARGAIN).
				leftJoin(GOODS).on(BARGAIN.GOODS_ID.eq(GOODS.GOODS_ID));
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

    private SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select,BargainPageListQueryParam param){
        select.where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if(param.getState() != null && param.getState().length > 0) {
            /** 状态过滤*/
            Condition stateCondition = DSL.noCondition();
            Timestamp now = DateUtil.getLocalDateTime();
            for(byte state : param.getState()){
                if(state == 1){
                    stateCondition = stateCondition.or((BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.START_TIME.lt(now)).and(BARGAIN.END_TIME.gt(now)));
                }
                if(state == 2){
                    stateCondition = stateCondition.or((BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.START_TIME.gt(now)));
                }
                if(state == 3){
                    stateCondition = stateCondition.or((BARGAIN.STATUS.eq(STATUS_NORMAL)).and(BARGAIN.END_TIME.lt(now)));
                }
                if(state == 4){
                    stateCondition = stateCondition.or(BARGAIN.STATUS.eq(STATUS_DISABLED));
                }
            }
            select.where(stateCondition);
        }
        if(!StringUtils.isEmpty(param.getKeywords())){
            select.where(BARGAIN.BARGAIN_NAME.contains(param.getKeywords()).or(GOODS.GOODS_NAME.contains(param.getKeywords())));
        }

        return select;
    }
	
	/**
	 * 新建砍价活动
	 *
	 */
	public void addBargain(BargainAddParam param) {
		BargainRecord record = new BargainRecord();
		assign(param,record);
		if(param.getShareConfig() != null) {
			record.setShareConfig(Util.toJson(param.getShareConfig()));
		}
		db().executeInsert(record);
	}
	
	/**
	 * 更新砍价活动
	 *
	 */
	public void updateBargain(BargainUpdateParam param) {
		BargainRecord record = new BargainRecord();
		assign(param,record);
		if(param.getShareConfig() != null) {
			record.setShareConfig(Util.toJson(param.getShareConfig()));
		}
		db().executeUpdate(record);
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
	 * 取单个砍价活动信息
	 *
	 *
	 */
	public BargainUpdateVo getBargainByIsd(Integer bargainId) {
        BargainUpdateVo bargain = db().select(BARGAIN.ID,BARGAIN.BARGAIN_NAME,BARGAIN.START_TIME,BARGAIN.END_TIME,BARGAIN.EXPECTATION_NUMBER,BARGAIN.EXPECTATION_PRICE,BARGAIN.BARGAIN_MIN,BARGAIN.BARGAIN_MAX,BARGAIN.STOCK,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE,BARGAIN.BARGAIN_MONEY_TYPE,BARGAIN.BARGAIN_FIXED_MONEY,BARGAIN.BARGAIN_MIN_MONEY,BARGAIN.BARGAIN_MAX_MONEY,BARGAIN.FREE_FREIGHT,BARGAIN.GOODS_ID,BARGAIN.MRKING_VOUCHER_ID,BARGAIN.REWARD_COUPON_ID,BARGAIN.SHARE_CONFIG).from(BARGAIN).where(BARGAIN.ID.eq(bargainId)).fetchOne().into(BargainUpdateVo.class);
		if(bargain != null) {
			bargain.setGoods(saas().getShopApp(getShopId()).goods.getGoodsView(bargain.getGoodsId()));
            bargain.setShopShareConfig(Util.parseJson(bargain.getShareConfig(), ShopShareConfig.class));
            bargain.setMrkingVoucherList(saas().getShopApp(getShopId()).coupon.getCouponViewByIds(Util.splitValueToList(bargain.getMrkingVoucherId())));
            bargain.setRewardCouponList(saas().getShopApp(getShopId()).coupon.getCouponViewByIds(Util.splitValueToList(bargain.getRewardCouponId())));
			return bargain;
		}else {
			return null;
		}
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
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param,OrderConstant.GOODS_TYPE_BARGAIN);
    }

    /**
     * 根据商品id获取处于可使用状态的砍价记录信息
     * @param goodsId 商品id
     * @return BargainRecord
     */
    public BargainRecord getBargainRecordByGoodsId(Integer goodsId,Timestamp timestamp){
        if (timestamp == null) {
            timestamp = Timestamp.valueOf(LocalDateTime.now());
        }
	    return db().select(BARGAIN.ID,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE,BARGAIN.EXPECTATION_PRICE)
            .from(BARGAIN)
            .where(BARGAIN.STATUS.eq(STATUS_NORMAL))
            .and(BARGAIN.GOODS_ID.eq(goodsId))
            .and(BARGAIN.START_TIME.lt(timestamp))
            .and(BARGAIN.END_TIME.gt(timestamp))
            .fetchOneInto(BARGAIN);
    }
    /**
     * 根据商品id获取砍价信息
     * @param goodsIds 商品id
     * @param date 当前时间
     * @return BargainRecord
     */
    public Map<Integer,List<BargainRecord>> getBargainRecordByGoodsIds(List<Integer> goodsIds, Timestamp date){
        return db().select(BARGAIN.ID,BARGAIN.GOODS_ID,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE,BARGAIN.EXPECTATION_PRICE)
            .from(BARGAIN)
            .where(BARGAIN.STATUS.eq(STATUS_NORMAL))
            .and(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(BARGAIN.GOODS_ID.in(goodsIds))
            .and(BARGAIN.START_TIME.lessThan(date))
            .and(BARGAIN.END_TIME.greaterThan(date))
            .fetchInto(BARGAIN)
            .stream()
            .collect(Collectors.groupingBy(BargainRecord::getGoodsId));
    }
    /**
     * 获取小程序码
     */
    public ShareQrCodeVo getMpQrCode(Integer id) {

        String pathParam="paramId="+id;
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.BARGAIN_ITEM, pathParam);

        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.BARGAIN_ITEM.getPathUrl(pathParam));
        return vo;
    }

    /**
     * 判断是否已经有有进行中的商品
     * @param goodsId       商品ID
     * @param startTime     起始时间
     * @param endTime       终止时间
     * @return bool
     */
    public boolean isOnGoingBargain(int goodsId,Timestamp startTime,Timestamp endTime){
        Record r = db().select(BARGAIN.ID).from(BARGAIN).where(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(BARGAIN.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(BARGAIN.GOODS_ID.eq(goodsId)).and(isConflictingActTime(startTime,endTime))).fetchOne();
        return r != null;
    }

    private Condition isConflictingActTime(Timestamp startTime,Timestamp endTime){
        return (BARGAIN.START_TIME.gt(startTime).and(BARGAIN.START_TIME.lt(endTime))).or(BARGAIN.END_TIME.gt(startTime).and(BARGAIN.END_TIME.lt(endTime))).or(BARGAIN.START_TIME.lt(startTime).and(BARGAIN.END_TIME.gt(endTime)));
    }
}
