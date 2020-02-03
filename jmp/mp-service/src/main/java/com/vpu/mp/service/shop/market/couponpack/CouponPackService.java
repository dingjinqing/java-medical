package com.vpu.mp.service.shop.market.couponpack;

import com.vpu.mp.db.shop.tables.records.CouponPackRecord;
import com.vpu.mp.db.shop.tables.records.CouponPackVoucherRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.foundation.excel.ExcelFactory;
import com.vpu.mp.service.foundation.excel.ExcelTypeEnum;
import com.vpu.mp.service.foundation.excel.ExcelWriter;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.couponpack.*;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.order.virtual.CouponPackOrderService;
import com.vpu.mp.service.shop.order.virtual.VirtualOrderService;
import org.apache.poi.ss.usermodel.Workbook;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.tables.CouponPack.COUPON_PACK;
import static com.vpu.mp.db.shop.tables.CouponPackVoucher.COUPON_PACK_VOUCHER;
import static com.vpu.mp.db.shop.tables.VirtualOrder.VIRTUAL_ORDER;
import static com.vpu.mp.db.shop.tables.User.USER;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author: 王兵兵
 * @create: 2019-08-20 11:01
 **/
@Service
public class CouponPackService extends ShopBaseService {

    @Autowired
    private CouponPackVoucherService couponPackVoucherService;
    @Autowired
    private CouponPackOrderService couponPackOrderService;
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
     * 获取方式，0：现金购买
     */
    public static final byte ACCESS_MODE_CASH = 0;
    /**
     * 获取方式，1：积分购买
     */
    public static final byte ACCESS_MODE_SCORE = 1;
    /**
     * 获取方式，2直接领取
     */
    public static final byte ACCESS_MODE_FREE = 2;

    private static final String LANGUAGE_TYPE_EXCEL= "excel";

    /**
     * 新建优惠券礼包活动
     *
     */
    public void addCouponPack(CouponPackAddParam param) {
        this.transaction(()->{
            CouponPackRecord record = db().newRecord(COUPON_PACK);
            assign(param,record);
            record.insert();
            Integer couponPackId = record.getId();
            for(CouponPackVoucherAddParam goods : param.getCouponPackVoucher()){
                CouponPackVoucherRecord couponPackVoucherRecord = db().newRecord(COUPON_PACK_VOUCHER);
                assign(goods,couponPackVoucherRecord);
                couponPackVoucherRecord.setActId(couponPackId);
                couponPackVoucherRecord.insert();
            }
        });
    }

    /**
     * 优惠券礼包活动列表分页查询
     *
     */
    public PageResult<CouponPackPageListQueryVo> getPageList(CouponPackPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(COUPON_PACK.ID,COUPON_PACK.ACT_NAME,COUPON_PACK.PACK_NAME,COUPON_PACK.START_TIME,COUPON_PACK.END_TIME,COUPON_PACK.TOTAL_AMOUNT,COUPON_PACK.ACCESS_MODE,COUPON_PACK.ACCESS_COST,COUPON_PACK.STATUS).
            from(COUPON_PACK);
        buildOptions(select,param);
        select.where(COUPON_PACK.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(COUPON_PACK.CREATE_TIME.desc());
        PageResult<CouponPackPageListQueryVo> res = getPageResult(select,param.getCurrentPage(),param.getPageRows(),CouponPackPageListQueryVo.class);

        /**查询活动商品数量、订单付款数、付款用户数、付款总金额 */
        for(CouponPackPageListQueryVo vo : res.dataList){
            vo.setVoucherKindsNumber(couponPackVoucherService.getVoucherKindsNumber(vo.getId()));
            vo.setVoucherNumber(couponPackVoucherService.getVoucherNumber(vo.getId()));
            vo.setIssueAmount(couponPackOrderService.getCouponPackIssueAmount(vo.getId()));
            vo.setCurrentState(Util.getActStatus(vo.getStatus(),vo.getStartTime(),vo.getEndTime()));
        }

        return res;
    }

    /**
     * 查询条件
     */
    private void buildOptions(SelectWhereStep<? extends Record> select, CouponPackPageListQueryParam param) {
        if(param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch(param.getState()) {
                case (byte)1:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_NORMAL)).and(COUPON_PACK.START_TIME.lt(now)).and(COUPON_PACK.END_TIME.gt(now));
                    break;
                case (byte)2:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_NORMAL)).and(COUPON_PACK.START_TIME.gt(now));
                    break;
                case (byte)3:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_NORMAL)).and(COUPON_PACK.END_TIME.lt(now));
                    break;
                case (byte)4:
                    select.where(COUPON_PACK.STATUS.eq(STATUS_DISABLED));
                    break;
                default:
            }
        }
        if (isNotEmpty(param.getActName())) {
            select.where(COUPON_PACK.ACT_NAME.contains(param.getActName()));
        }
        if (isNotEmpty(param.getPackName())) {
            select.where(COUPON_PACK.PACK_NAME.contains(param.getPackName()));
        }
        if(param.getAccessMode() != null && param.getAccessMode() >= ACCESS_MODE_CASH) {
            /** 领取方式过滤*/
            select.where(COUPON_PACK.ACCESS_MODE.eq(param.getAccessMode()));
        }
    }

    /**
     * 删除优惠券礼包活动
     *
     */
    public void delCouponPack(Integer id) {
        db().update(COUPON_PACK).
            set(COUPON_PACK.DEL_FLAG,DelFlag.DISABLE.getCode()).
            set(COUPON_PACK.DEL_TIME,DateUtil.getLocalDateTime()).
            where(COUPON_PACK.ID.eq(id)).
            execute();
    }

    /**
     * 取单个优惠券礼包活动信息
     *
     */
    public CouponPackUpdateVo getCouponPackById(Integer id){
        CouponPackUpdateVo res =
        db().select(COUPON_PACK.ID,COUPON_PACK.ACT_NAME,COUPON_PACK.START_TIME,COUPON_PACK.END_TIME,COUPON_PACK.PACK_NAME,COUPON_PACK.LIMIT_GET_TIMES,COUPON_PACK.TOTAL_AMOUNT,COUPON_PACK.ACCESS_MODE,COUPON_PACK.ACCESS_COST,COUPON_PACK.ACT_RULE).
        from(COUPON_PACK).
        where(COUPON_PACK.ID.eq(id)).and(COUPON_PACK.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).
        fetchOneInto(CouponPackUpdateVo.class);

        if(res != null){
            List<CouponPackUpdateVoucherVo> couponPackVoucherList =
            db().select(COUPON_PACK_VOUCHER.VOUCHER_ID,COUPON_PACK_VOUCHER.TOTAL_AMOUNT,COUPON_PACK_VOUCHER.IMMEDIATELY_GRANT_AMOUNT,COUPON_PACK_VOUCHER.TIMING_EVERY,COUPON_PACK_VOUCHER.TIMING_UNIT,COUPON_PACK_VOUCHER.TIMING_TIME,COUPON_PACK_VOUCHER.TIMING_AMOUNT).
            from(COUPON_PACK_VOUCHER).
            where(COUPON_PACK_VOUCHER.ACT_ID.eq(res.getId())).
            fetchInto(CouponPackUpdateVoucherVo.class);

            if(couponPackVoucherList != null && couponPackVoucherList.size() > 0){
                for(CouponPackUpdateVoucherVo couponPackVoucher : couponPackVoucherList){
                    couponPackVoucher.setCouponView(saas.getShopApp(getShopId()).coupon.getCouponViewById(couponPackVoucher.getVoucherId()));
                }
            }

            res.setCouponPackVoucher(couponPackVoucherList);
        }

        return res;
    }

    /**
     * 更新优惠券礼包活动
     *
     */
    public void updateCouponPack(CouponPackUpdateParam param) {
        CouponPackRecord record = new CouponPackRecord();
        assign(param,record);
        db().executeUpdate(record);
    }

    /**
     * 优惠券礼包活动订单列表
     *
     */
    public PageResult<CouponPackOrderListQueryVo> getCouponPackOrderPageList(CouponPackOrderListQueryParam param){
        SelectWhereStep<? extends Record> select = db().select(VIRTUAL_ORDER.ORDER_SN,VIRTUAL_ORDER.MONEY_PAID,VIRTUAL_ORDER.USE_ACCOUNT,VIRTUAL_ORDER.USE_SCORE,VIRTUAL_ORDER.MEMBER_CARD_BALANCE,VIRTUAL_ORDER.ORDER_STATUS,VIRTUAL_ORDER.CREATE_TIME,USER.USER_ID,USER.USERNAME,USER.MOBILE).
        from(VIRTUAL_ORDER).leftJoin(USER).on(VIRTUAL_ORDER.USER_ID.eq(USER.USER_ID));
        buildOrderOptions(select,param);
        select.where(VIRTUAL_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(VIRTUAL_ORDER.CREATE_TIME.desc());
        return getPageResult(select,param.getCurrentPage(),param.getPageRows(),CouponPackOrderListQueryVo.class);
    }

    /**
     * 优惠券礼包订单查询条件
     */
    private void buildOrderOptions(SelectWhereStep<? extends Record> select, CouponPackOrderListQueryParam param) {
        select.where(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(param.getId())).and(VIRTUAL_ORDER.GOODS_TYPE.eq(VirtualOrderService.GOODS_TYPE_COUPON_PACK)).and(VIRTUAL_ORDER.ORDER_STATUS.eq(CouponPackOrderService.ORDER_STATUS_FINISHED));
        if (isNotEmpty(param.getOrderSn())) {
            select.where(VIRTUAL_ORDER.ORDER_SN.contains(param.getOrderSn()));
        }
        if (isNotEmpty(param.getUserInfo())) {
            select.where(USER.USERNAME.contains(param.getUserInfo()).or(USER.MOBILE.contains(param.getUserInfo())));
        }
        if(param.getStartTime() != null){
            select.where(VIRTUAL_ORDER.CREATE_TIME.ge(param.getStartTime()));
        }
        if(param.getEndTime() != null){
            select.where(VIRTUAL_ORDER.CREATE_TIME.le(param.getEndTime()));
        }
    }

    /**
     * 优惠券礼包订单导出
     * @param param
     * @param lang
     * @return
     */
    public Workbook exportCouponPackOrderList(CouponPackOrderListQueryParam param, String lang) {
        SelectWhereStep<? extends Record> select = db().select(VIRTUAL_ORDER.ORDER_SN,VIRTUAL_ORDER.MONEY_PAID,VIRTUAL_ORDER.USE_ACCOUNT,VIRTUAL_ORDER.USE_SCORE,VIRTUAL_ORDER.MEMBER_CARD_BALANCE,VIRTUAL_ORDER.ORDER_STATUS,VIRTUAL_ORDER.CREATE_TIME,USER.USER_ID,USER.USERNAME,USER.MOBILE).
        from(VIRTUAL_ORDER).leftJoin(USER).on(VIRTUAL_ORDER.USER_ID.eq(USER.USER_ID));
        buildOrderOptions(select,param);
        select.where(VIRTUAL_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(VIRTUAL_ORDER.CREATE_TIME.desc());
        List<CouponPackOrderExportVo> couponPackOrderList =  select.fetchInto(CouponPackOrderExportVo.class);

        /**循环处理状态列*/
        for(CouponPackOrderExportVo vo : couponPackOrderList) {
            switch(vo.getOrderStatus()) {
                case 0:
                    vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.ORDER_STATUS_WAIT_PAY,LANGUAGE_TYPE_EXCEL));
                    break;
                case 1:
                    vo.setStatusName(Util.translateMessage(lang, JsonResultMessage.ORDER_STATUS_FINISHED,LANGUAGE_TYPE_EXCEL));
                    break;
                default:
            }
        }

        Workbook workbook= ExcelFactory.createWorkbook(ExcelTypeEnum.XLSX);
        ExcelWriter excelWriter = new ExcelWriter(lang,workbook);
        excelWriter.writeModelList(couponPackOrderList,CouponPackOrderExportVo.class);
        return workbook;
    }

    /**
     * 优惠券礼包活动领取明细列表
     *
     */
    public PageResult<CouponPackDetailListQueryVo> getCouponPackDetailPageList(CouponPackDetailListQueryParam param){
        SelectWhereStep<? extends Record> select = db().select(USER.USERNAME,USER.MOBILE,VIRTUAL_ORDER.ACCESS_MODE,VIRTUAL_ORDER.ORDER_SN,VIRTUAL_ORDER.CREATE_TIME).
        from(VIRTUAL_ORDER).leftJoin(USER).on(USER.USER_ID.eq(VIRTUAL_ORDER.USER_ID));
        buildDetailOptions(select,param);
        select.where(VIRTUAL_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(VIRTUAL_ORDER.CREATE_TIME.desc());
        PageResult<CouponPackDetailListQueryVo> res = getPageResult(select,param.getCurrentPage(),param.getPageRows(),CouponPackDetailListQueryVo.class);
        for (CouponPackDetailListQueryVo vo : res.dataList){
            vo.setVoucherAccessCount(couponPackOrderService.getVoucherAccessCount(vo.getOrderSn()));
        }
        return res;
    }

    /**
     * 优惠券礼包明细查询条件
     */
    private void buildDetailOptions(SelectWhereStep<? extends Record> select, CouponPackDetailListQueryParam param) {
        select.where(VIRTUAL_ORDER.GOODS_TYPE.eq(VirtualOrderService.GOODS_TYPE_COUPON_PACK)).and(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(param.getId())).and(VIRTUAL_ORDER.ORDER_STATUS.eq(CouponPackOrderService.ORDER_STATUS_FINISHED)).and(VIRTUAL_ORDER.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(VIRTUAL_ORDER.VIRTUAL_GOODS_ID.eq(param.getId()));

        if (isNotEmpty(param.getOrderSn())) {
            select.where(VIRTUAL_ORDER.ORDER_SN.contains(param.getOrderSn()));
        }
        if (isNotEmpty(param.getUsername())) {
            select.where(USER.USERNAME.contains(param.getUsername()));
        }
        if (isNotEmpty(param.getMobile())) {
            select.where(USER.MOBILE.contains(param.getMobile()));
        }
        if(param.getStartTime() != null){
            select.where(VIRTUAL_ORDER.CREATE_TIME.ge(param.getStartTime()));
        }
        if(param.getEndTime() != null){
            select.where(VIRTUAL_ORDER.CREATE_TIME.le(param.getEndTime()));
        }
        if(param.getAccessMode() != null && param.getAccessMode() >= ACCESS_MODE_CASH){
            select.where(VIRTUAL_ORDER.ACCESS_MODE.eq(param.getAccessMode()));
        }
    }

    /**
     * 获取小程序码
     */
    public ShareQrCodeVo getMpQrCode(Integer id) {

        String pathParam="paramId="+id;
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.DISCOUNT_COUPON_PAGCKAGE, pathParam);

        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.DISCOUNT_COUPON_PAGCKAGE.getPathUrl(pathParam));
        return vo;
    }
}
