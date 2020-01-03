package com.vpu.mp.service.shop.market.bargain;

import static com.vpu.mp.db.shop.tables.Bargain.BARGAIN;
import static com.vpu.mp.db.shop.tables.BargainRecord.BARGAIN_RECORD;
import static com.vpu.mp.db.shop.tables.BargainUserList.BARGAIN_USER_LIST;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserDetail.USER_DETAIL;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static org.jooq.impl.DSL.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.records.BargainRecord;
import com.vpu.mp.db.shop.tables.records.BargainRecordRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.config.PictorialShareConfigVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.market.bargain.*;
import com.vpu.mp.service.shop.activity.dao.BargainProcessorDao;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordExportVo;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryParam;
import com.vpu.mp.service.pojo.shop.market.bargain.BargainRecordPageListQueryVo;
import com.vpu.mp.service.pojo.shop.market.bargain.analysis.BargainAnalysisParam;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Service
public class BargainRecordService extends ShopBaseService {

    /**
     *  帮忙砍价的用户
     */
    @Autowired
    public BargainUserService bargainUser;
    /**
     *  商品列表和详情页的砍价处理类
     */
    @Autowired
    private BargainProcessorDao bargainProcessorDao;
    @Autowired
    private DomainConfig domainConfig;
    @Autowired
    private OrderInfoService orderInfo;

    /**
     * 状态：正在砍价
     */
    public static final byte STATUS_IN_PROCESS = 0;
    /**
     *  状态：砍价成功
     */
    public static final byte STATUS_SUCCESS = 1;
    /**
     *  状态：砍价失败
     */
    public static final byte STATUS_FAILED = 2;

    /**
     *  已下单
     */
    public static final byte IS_ORDERED_Y = 1;
    /**
     *  未下单
     */
    public static final byte IS_ORDERED_N = 0;

    private static final String LANGUAGE_TYPE_EXCEL= "excel";

    /**
     * 根据状态取发起砍价的数量
     *
     */
    public Integer getBargainRecordNumberByStatus(int bargainId,byte status) {
        return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.STATUS.eq(status)).and(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
    }

    /**
     * 某活动的发起砍价数量
     *
     *
     */
    public Integer getBargainRecordNumber(int bargainId) {
        return db().selectCount().from(BARGAIN_RECORD).where(BARGAIN_RECORD.BARGAIN_ID.eq(bargainId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).fetchOne().into(Integer.class);
    }

    /**
     * 发起记录的分页列表
     *
     *
     */
    public PageResult<BargainRecordPageListQueryVo> getRecordPageList(BargainRecordPageListQueryParam param){
        SelectWhereStep<? extends Record> select = db().select(
            BARGAIN_RECORD.ID,GOODS.GOODS_NAME,BARGAIN_RECORD.GOODS_PRICE,USER.USERNAME,USER.MOBILE,BARGAIN_RECORD.CREATE_TIME,BARGAIN_RECORD.BARGAIN_MONEY,
            BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.STATUS ,BARGAIN.EXPECTATION_PRICE,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE
        ).
            from(BARGAIN_RECORD).
            leftJoin(GOODS).on(BARGAIN_RECORD.GOODS_ID.eq(GOODS.GOODS_ID)).
            leftJoin(USER).on(BARGAIN_RECORD.USER_ID.eq(USER.USER_ID)).
            leftJoin(BARGAIN).on(BARGAIN_RECORD.BARGAIN_ID.eq(BARGAIN.ID));
        select = this.buildOptions(select, param);
        select.where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        return getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainRecordPageListQueryVo.class);
    }

    public SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select, BargainRecordPageListQueryParam param) {
        if (param == null) {
            return select;
        }
        if(!StringUtils.isBlank(param.getUsername())) {
            select.where(USER.USERNAME.contains(param.getUsername()));
        }
        if(!StringUtils.isBlank(param.getMobile())) {
            select.where(USER.MOBILE.contains(param.getMobile()));
        }
        if(param.getStatus() > -1) {
            select.where(BARGAIN_RECORD.STATUS.eq(param.getStatus()));
        }
        if(param.getStartTime() != null) {
            select.where(BARGAIN_RECORD.CREATE_TIME.gt(param.getStartTime()));
        }
        if(param.getEndTime() != null) {
            select.where(BARGAIN_RECORD.CREATE_TIME.lt(param.getEndTime()));
        }
        return select;
    }

    /**
     * 算出待砍金额
     *
     */
    public BigDecimal getBargainRecordSurplusMoney(BargainRecordPageListQueryVo record) {
        if(record.getBargainType() == BargainService.BARGAIN_TYPE_FIXED) {
            return record.getGoodsPrice().subtract(record.getExpectationPrice()).subtract(record.getBargainMoney());
        }else if(record.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM) {
            return record.getGoodsPrice().subtract(record.getFloorPrice()).subtract(record.getBargainMoney());
        }
        return BigDecimal.ZERO;
    }

    public Workbook exportBargainRecordList(BargainRecordPageListQueryParam param, String lang) {
        SelectWhereStep<? extends Record> select = db().select(
            BARGAIN_RECORD.ID,GOODS.GOODS_NAME,BARGAIN_RECORD.GOODS_PRICE,USER.USERNAME,USER.MOBILE,BARGAIN_RECORD.CREATE_TIME,BARGAIN_RECORD.BARGAIN_MONEY,
            BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.STATUS,BARGAIN.EXPECTATION_PRICE,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE
        ).
            from(BARGAIN_RECORD).
            leftJoin(GOODS).on(BARGAIN_RECORD.GOODS_ID.eq(GOODS.GOODS_ID)).
            leftJoin(USER).on(BARGAIN_RECORD.USER_ID.eq(USER.USER_ID)).
            leftJoin(BARGAIN).on(BARGAIN_RECORD.BARGAIN_ID.eq(BARGAIN.ID));
        select = this.buildOptions(select, param);
        select.where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        List<BargainRecordExportVo> bargainRecordList =  select.fetchInto(BargainRecordExportVo.class);

        /**循环处理状态和待砍金额列*/
        for(BargainRecordExportVo vo : bargainRecordList) {
            switch(vo.getStatus()) {
                case 0:
                    vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.STATUS_IN_PROGRESS,LANGUAGE_TYPE_EXCEL));
                    break;
                case 1:
                    vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.STATUS_SUCCESS,LANGUAGE_TYPE_EXCEL));
                    break;
                case 2:
                    vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.STATUS_FAIL,LANGUAGE_TYPE_EXCEL));
                    break;
                default:
            }
            if(vo.getBargainType() == BargainService.BARGAIN_TYPE_FIXED) {
                vo.setSurplusMoney(vo.getGoodsPrice().subtract(vo.getExpectationPrice()).subtract(vo.getBargainMoney()));
            }else if(vo.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM) {
                vo.setSurplusMoney(vo.getGoodsPrice().subtract(vo.getFloorPrice()).subtract(vo.getBargainMoney()));
            }
        }

        Workbook workbook=ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(bargainRecordList,BargainRecordExportVo.class);
        return workbook;
    }

    /**
     * 发起砍价的人次数据分析
     *
     */
    public Map<Date,Integer> getRecordAnalysis(BargainAnalysisParam param){
        Map<Date,Integer> map =  db().select(date(BARGAIN_RECORD.CREATE_TIME).as("date"),count().as("number")).from(BARGAIN_RECORD).
            where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).
            and(BARGAIN_RECORD.CREATE_TIME.between(param.getStartTime(),param.getEndTime())).
            groupBy(date(BARGAIN_RECORD.CREATE_TIME)).fetch().intoMap(date(BARGAIN_RECORD.CREATE_TIME).as("date"),count().as("number"));
        return map;
    }

    /**
     * 帮砍价的用户数据分析
     *
     */
    public Map<Date,Integer> getBargainUserAnalysis(BargainAnalysisParam param){
        Map<Date,Integer> map =  db().select(date(BARGAIN_USER_LIST.CREATE_TIME).as("date"),count().as("number")).from(BARGAIN_USER_LIST).
            leftJoin(BARGAIN_RECORD).on(BARGAIN_USER_LIST.RECORD_ID.eq(BARGAIN_RECORD.ID)).
            where(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).
            and(BARGAIN_RECORD.CREATE_TIME.between(param.getStartTime(),param.getEndTime())).
            groupBy(date(BARGAIN_USER_LIST.CREATE_TIME)).fetch().intoMap(date(BARGAIN_USER_LIST.CREATE_TIME).as("date"),count().as("number"));
        return map;
    }

    /**
     * 小程序端-个人中心-我的砍价列表
     *
     *
     */
    public PageResult<BargainRecordListQueryVo> getRecordPageList(Integer userId,BargainRecordListQueryParam param){
        SelectWhereStep<? extends Record> select = db().select(
            BARGAIN_RECORD.ID,BARGAIN_RECORD.GOODS_PRICE,BARGAIN_RECORD.CREATE_TIME,BARGAIN_RECORD.BARGAIN_MONEY,
            BARGAIN_RECORD.USER_NUMBER,BARGAIN_RECORD.STATUS ,GOODS.GOODS_NAME,GOODS.GOODS_ID,GOODS.GOODS_IMG,GOODS.GOODS_NUMBER,BARGAIN.EXPECTATION_PRICE,BARGAIN.BARGAIN_TYPE,BARGAIN.FLOOR_PRICE,BARGAIN.STOCK,BARGAIN.END_TIME,ORDER_INFO.ORDER_STATUS
        ).
            from(BARGAIN_RECORD).
            leftJoin(GOODS).on(BARGAIN_RECORD.GOODS_ID.eq(GOODS.GOODS_ID)).
            leftJoin(BARGAIN).on(BARGAIN_RECORD.BARGAIN_ID.eq(BARGAIN.ID)).
            leftJoin(ORDER_INFO).on(BARGAIN_RECORD.ORDER_SN.eq(ORDER_INFO.ORDER_SN));

        select.where(BARGAIN_RECORD.USER_ID.eq(userId)).and(BARGAIN_RECORD.STATUS.eq(param.getStatus())).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        select.orderBy(BARGAIN_RECORD.CREATE_TIME.desc());
        PageResult<BargainRecordListQueryVo> list = getPageResult(select,param.getCurrentPage(),param.getPageRows(),BargainRecordListQueryVo.class);

        for(BargainRecordListQueryVo bargainRecord : list.dataList){
            if(bargainRecord.getGoodsNumber() < bargainRecord.getStock()){
                bargainRecord.setStock(bargainRecord.getGoodsNumber());
            }
            if(bargainRecord.getBargainType() == BargainService.BARGAIN_TYPE_RANDOM){
                BigDecimal remainMoney = bargainRecord.getGoodsPrice().subtract(bargainRecord.getBargainMoney());
                if(remainMoney.compareTo(bargainRecord.getExpectationPrice()) < 0){
                    bargainRecord.setExpectationPrice(remainMoney);
                }
            }
        }
        return list;
    }
    /**
     * 申请发起砍价
     * @param param
     * @return bargain_record id 发起砍价的ID
     */
    public BargainApplyVo applyBargain(int userId, BargainApplyParam param){
        GoodsSpecProductRecord goodsPrd = db().selectFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(param.getPrdId())).fetchAny();
        //发起新的砍价
        BargainRecordRecord insertRecord = db().newRecord(BARGAIN_RECORD);
        insertRecord.setUserId(userId);
        insertRecord.setBargainId(param.getBargainId());
        insertRecord.setGoodsId(goodsPrd.getGoodsId());
        insertRecord.setPrdId(param.getPrdId());
        insertRecord.setGoodsPrice(goodsPrd.getPrdPrice());
        transaction(()->{
            insertRecord.insert();
            bargainUser.addUserBargain(userId,insertRecord.getId());
        });

        return insertRecord.getId() != null && insertRecord.getId() > 0 ? BargainApplyVo.builder().recordId(insertRecord.getId()).resultCode((byte)0).build() : BargainApplyVo.builder().resultCode((byte)-1).build();
    }

    /**
     * 该用户已经发起了对这个活动的砍价 的ID
     * @param userId
     * @param param
     * @return
     */
    public BargainApplyVo getUserBargainRecordId(int userId, BargainApplyParam param){
        Integer recordId = db().select(BARGAIN_RECORD.ID).from(BARGAIN_RECORD).where(BARGAIN_RECORD.USER_ID.eq(userId).and(BARGAIN_RECORD.BARGAIN_ID.eq(param.getBargainId())).and(BARGAIN_RECORD.PRD_ID.eq(param.getPrdId()))).fetchOptionalInto(Integer.class).orElse(0);

        return recordId > 0 ? BargainApplyVo.builder().recordId(recordId).resultCode((byte)0).build() : BargainApplyVo.builder().resultCode((byte)-1).build();
    }

    /**
     * 判断用户是否可以对该规格发起砍价
     * @param userId 用户id
     * @param param
     * @return
     */
    public Byte canApplyBargain(Integer userId, BargainApplyParam param) {
        BargainRecord bargain = db().selectFrom(BARGAIN).where(BARGAIN.ID.eq(param.getBargainId()).and(BARGAIN.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))).fetchAny();
        GoodsSpecProductRecord goodsPrd = db().selectFrom(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(param.getPrdId())).fetchAny();

        //校验活动信息
        Byte res = bargainProcessorDao.canApplyBargain(userId,DateUtil.getLocalDateTime(),bargain);

        if(!res.equals(BaseConstant.ACTIVITY_STATUS_CAN_USE)){
            return res;
        }else{
            //校验库存
            if(bargain.getStock() <= 0){
                return BaseConstant.ACTIVITY_STATUS_NOT_HAS_NUM;
            }
            if(goodsPrd.getPrdNumber() <= 0){
                return BaseConstant.ACTIVITY_STATUS_NOT_HAS_NUM;
            }
            int goodsNumber = db().select(GOODS.GOODS_NUMBER).from(GOODS).where(GOODS.GOODS_ID.eq(bargain.getGoodsId())).fetchOptionalInto(Integer.class).orElse(0);
            if(goodsNumber <= 0){
                return BaseConstant.ACTIVITY_STATUS_NOT_HAS_NUM;
            }
        }
        return BaseConstant.ACTIVITY_STATUS_CAN_USE;
    }

    /**
     * 砍价详情
     * @param userId
     * @param recordId
     * @return
     */
    public BargainInfoVo getBargainInfo(int userId,int recordId){
        BargainInfoVo vo = new BargainInfoVo();
        BargainRecordInfo recordInfo = getRecordInfo(recordId);

        //详情
        if(recordInfo != null){
            //状态
            byte recordStatus = userBargainRecordStatus(userId,recordInfo);
            vo.setState(recordStatus);
            if(recordStatus == 9){
                vo.setStateMoney(recordInfo.getBargainType().equals(BargainService.BARGAIN_MONEY_TYPE_RANDOM) ? recordInfo.getFloorPrice() : recordInfo.getExpectationPrice());
            }else if(recordStatus == 8){
                vo.setStateMoney(bargainUser.getFirstUserBargain(userId,recordId).getBargainMoney());
            }

            //分享配置
            recordInfo.setRemainingTime((recordInfo.getEndTime().getTime() - DateUtil.getLocalDateTime().getTime())/1000);
            vo.setRecordShareImg(Util.parseJson(recordInfo.getShareConfig(), PictorialShareConfigVo.class));
            if(vo.getRecordShareImg() != null){
                if(vo.getRecordShareImg().getShareAction().equals(PictorialShareConfigVo.CUSTOMER_STYLE) && vo.getRecordShareImg().getShareImgAction().equals(PictorialShareConfigVo.CUSTOMER_IMG) && StringUtil.isNotEmpty(vo.getRecordShareImg().getShareImg())){
                    //自定义分享图
                    vo.getRecordShareImg().setShareImgFullUrl(domainConfig.imageUrl(vo.getRecordShareImg().getShareImg()));
                }else if(vo.getRecordShareImg().getShareAction().equals(PictorialShareConfigVo.CUSTOMER_STYLE) && vo.getRecordShareImg().getShareImgAction().equals(PictorialShareConfigVo.DEFAULT_IMG)){
                    //分享商品图
                    vo.getRecordShareImg().setShareImg(recordInfo.getGoodsImg());
                    vo.getRecordShareImg().setShareImgFullUrl(domainConfig.imageUrl(recordInfo.getGoodsImg()));
                }else{
                    //TODO 生成砍价分享图
                }
            }
            vo.setRecordInfo(recordInfo);

            //商品图片地址
            vo.getRecordInfo().setGoodsImg(domainConfig.imageUrl(recordInfo.getGoodsImg()));

            //帮忙砍价用户
            vo.setRecordUserList(bargainUser.getBargainUserList(recordId));

            vo.setTimestamp(DateUtil.getLocalDateTime());
            vo.setBargainPrice(recordInfo.getBargainType().equals(BargainService.BARGAIN_MONEY_TYPE_RANDOM) ? recordInfo.getFloorPrice() : recordInfo.getExpectationPrice());
        }
        return vo;
    }

    public BargainRecordInfo getRecordInfo(int recordId){
        return db().select(BARGAIN_RECORD.asterisk(),
            GOODS.GOODS_ID,GOODS.GOODS_IMG,GOODS.GOODS_NAME,
            USER_DETAIL.USER_AVATAR,
            GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_DESC,GOODS_SPEC_PRODUCT.PRD_NUMBER,
            BARGAIN.BARGAIN_TYPE,BARGAIN.START_TIME,BARGAIN.END_TIME,BARGAIN.EXPECTATION_PRICE,BARGAIN.FLOOR_PRICE,BARGAIN.UPDATE_TIME,BARGAIN.SHARE_CONFIG,BARGAIN.STOCK,
            USER.WX_OPENID,USER.USERNAME).from(
            BARGAIN_RECORD
                .leftJoin(USER_DETAIL).on(BARGAIN_RECORD.USER_ID.eq(USER_DETAIL.USER_ID))
                .leftJoin(GOODS).on(GOODS.GOODS_ID.eq(BARGAIN_RECORD.GOODS_ID))
                .leftJoin(GOODS_SPEC_PRODUCT).on(BARGAIN_RECORD.PRD_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID))
                .leftJoin(BARGAIN).on(BARGAIN.ID.eq(BARGAIN_RECORD.BARGAIN_ID))
                .leftJoin(USER).on(USER.USER_ID.eq(BARGAIN_RECORD.USER_ID))
        )
            .where(BARGAIN_RECORD.ID.eq(recordId)).and(BARGAIN_RECORD.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .fetchOptionalInto(BargainRecordInfo.class).orElse(null);
    }

    /**
     * 判断记录状态
     * @param userId
     * @param recordInfo
     * @return 状态码
     * 0可以砍价（别人的砍价） 11可以邀请砍价（自己的砍价） 1活动不存在 2砍价失败 3活动未开始 4或已结束
     * 5砍价成功 6商品已抢光 7可以邀请砍价（自己的砍价，已经砍了2刀） 8可以再砍一刀（自己的砍价） 9我也要X元得好物（别人的砍价，已帮砍过一刀） 10已完成订单（自己的砍价）
     */
    private byte userBargainRecordStatus(int userId,BargainRecordInfo recordInfo){
        if(recordInfo == null){
            return 1;
        }
        if(recordInfo.getStartTime().after(DateUtil.getLocalDateTime())){
            return 3;
        }
        if(recordInfo.getEndTime().before(DateUtil.getLocalDateTime())){
            return 4;
        }
        if(recordInfo.getStock() <= 0 || recordInfo.getPrdNumber() <= 0){
            return 6;
        }
        //自己的砍价详情
        if(userId == recordInfo.getUserId()){
            if(recordInfo.getStatus().equals(STATUS_FAILED)){
                return 2;
            }
            //区间结算
            if(recordInfo.getBargainType().equals(BargainService.BARGAIN_TYPE_RANDOM)){
                //已下单
                BigDecimal remainMoney = recordInfo.getGoodsPrice().subtract(recordInfo.getBargainMoney()).subtract(recordInfo.getFloorPrice().compareTo(BigDecimal.ZERO) > 0 ? recordInfo.getFloorPrice() : BigDecimal.ZERO);
                if(recordInfo.getIsOrdered().equals(IS_ORDERED_Y)){
                    OrderInfoRecord order = orderInfo.getOrderByOrderSn(recordInfo.getOrderSn());
                    if(order.getOrderAmount().equals(recordInfo.getFloorPrice()) || order.getOrderStatus() > OrderConstant.ORDER_CLOSED){
                        return 10;
                    }else{
                        if(remainMoney.compareTo(BigDecimal.ZERO) > 0){
                            return 11;
                        }else{
                            return 5;
                        }
                    }
                }else {
                    if(recordInfo.getIsOrdered().equals(IS_ORDERED_Y)){
                        return 10;
                    }
                    if(recordInfo.getStatus().equals(STATUS_SUCCESS)){
                        return 5;
                    }
                    if(remainMoney.compareTo(BigDecimal.ZERO) > 0){
                        return 11;
                    }
                }
            }
        }

        int userNumber = bargainUser.getUserBargainNumber(userId,recordInfo.getId());
        if(userId == recordInfo.getUserId()){
            if(userNumber == 2){
                return 7;
            }
            if(recordInfo.getUserNumber() == 1){
                return 8;
            }
        }else {
            if(userNumber > 0){
                return 9;
            }else{
                if(recordInfo.getBargainType().equals(BargainService.BARGAIN_TYPE_RANDOM)){
                    if(recordInfo.getIsOrdered().equals(IS_ORDERED_Y)){
                        OrderInfoRecord order = orderInfo.getOrderByOrderSn(recordInfo.getOrderSn());
                        if(order.getOrderAmount().equals(recordInfo.getFloorPrice()) || order.getOrderStatus() > OrderConstant.ORDER_CLOSED){
                            return 9;
                        }else{
                            BigDecimal remainMoney = recordInfo.getGoodsPrice().subtract(recordInfo.getBargainMoney()).subtract(recordInfo.getFloorPrice().compareTo(BigDecimal.ZERO) > 0 ? recordInfo.getFloorPrice() : BigDecimal.ZERO);
                            if(remainMoney.compareTo(BigDecimal.ZERO) > 0){
                                return 9;
                            }
                        }
                    }
                }else {
                    if(recordInfo.getIsOrdered().equals(IS_ORDERED_Y) || recordInfo.getStatus().equals(STATUS_IN_PROCESS)){
                        return 9;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 帮助砍价
     * @param userId
     * @param recordId
     * @return
     */
    public BargainCutVo getBargainCut(int userId,int recordId){
        BargainCutVo vo = new BargainCutVo();

        //判断今天砍价次数
        int daileCutTimes = saas.getShopApp(getShopId()).config.bargainCfg.getDailyCutTimes();
        int userTodayCutTimes = bargainUser.getUserTodayCutTimes(userId);
        if(daileCutTimes > 0 && userTodayCutTimes >= daileCutTimes){
            vo.setState((byte)12);
            return vo;
        }

        //可用状态过滤
        byte canCutStatus = userBargainRecordStatus(userId,getRecordInfo(recordId));
        if(canCutStatus != 0 && canCutStatus != 8 && canCutStatus != 11){
            vo.setState(canCutStatus);
            return vo;
        }

        //进行砍价
        BigDecimal bargainMoney = bargainUser.addUserBargain(userId,recordId);
        vo.setState((byte)0);
        vo.setBargainMoney(bargainMoney);
        return vo;
    }

}
