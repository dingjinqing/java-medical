package com.vpu.mp.service.shop.market.seckill;

import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;
import com.vpu.mp.db.shop.tables.records.SecKillProductDefineRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.seckill.*;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.OrderListInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderPageListQueryParam;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.Record;
import org.jooq.SelectWhereStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

/**
 * @author 王兵兵
 *
 * 2019年8月5日
 */
@Service
public class SeckillService extends ShopBaseService {

    @Autowired
    public SeckillListService seckillList;

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
     * 秒杀活动列表分页查询
     *
     */
    public PageResult<SeckillPageListQueryVo> getPageList(SeckillPageListQueryParam param) {
        SelectWhereStep<? extends Record> select = db().select(SEC_KILL_DEFINE.SK_ID,SEC_KILL_DEFINE.NAME,GOODS.GOODS_NAME,SEC_KILL_DEFINE.START_TIME,SEC_KILL_DEFINE.END_TIME,
            SEC_KILL_DEFINE.STATUS,SEC_KILL_DEFINE.SALE_NUM,SEC_KILL_DEFINE.LIMIT_AMOUNT).
            from(SEC_KILL_DEFINE).
            leftJoin(GOODS).on(SEC_KILL_DEFINE.GOODS_ID.eq(GOODS.GOODS_ID));
        if(param.getState() > 0) {
            /** 状态过滤*/
            Timestamp now = DateUtil.getLocalDateTime();
            switch(param.getState()) {
                case (byte)1:
                    select.where(SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.lt(now)).and(SEC_KILL_DEFINE.END_TIME.gt(now));
                    break;
                case (byte)2:
                    select.where(SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.gt(now));
                    break;
                case (byte)3:
                    select.where(SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL)).and(SEC_KILL_DEFINE.END_TIME.lt(now));
                    break;
                case (byte)4:
                    select.where(SEC_KILL_DEFINE.STATUS.eq(STATUS_DISABLED));
                    break;
                default:
            }
        }
        select.where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).orderBy(SEC_KILL_DEFINE.CREATE_TIME.desc());
        return getPageResult(select,param.getCurrentPage(),param.getPageRows(),SeckillPageListQueryVo.class);
    }

    /**
     * 新建秒杀活动
     *
     */
    public void addSeckill(SeckillAddParam param) {
        this.transaction(()->{
            SecKillDefineRecord record = db().newRecord(SEC_KILL_DEFINE);
            assign(param,record);
            if(param.getShareConfig() != null) {
                record.setShareConfig(Util.toJson(param.getShareConfig()));
            }
            record.insert();
            Integer skId = record.getSkId();
            for(SecKillProductDefine secKillProduct : param.getSecKillProduct()){
                SecKillProductDefineRecord productRecord = new SecKillProductDefineRecord();
                assign(secKillProduct,productRecord);
                productRecord.setSkId(skId);
                db().executeInsert(productRecord);
            }
        });
    }

    /**
     * 更新秒杀活动
     *
     */
    public void updateSeckill(SeckillUpdateParam param) {
        SecKillDefineRecord record = new SecKillDefineRecord();
        assign(param,record);
        if(param.getShareConfig() != null) {
            record.setShareConfig(Util.toJson(param.getShareConfig()));
        }
        db().executeUpdate(record);
    }

    /**
     * 删除秒杀活动
     *
     */
    public void delSeckill(Integer skId) {
        db().update(SEC_KILL_DEFINE).
            set(SEC_KILL_DEFINE.DEL_FLAG,DelFlag.DISABLE.getCode()).
            set(SEC_KILL_DEFINE.DEL_TIME,DateUtil.getLocalDateTime()).
            where(SEC_KILL_DEFINE.SK_ID.eq(skId)).
            execute();
    }

    /**
     * 取单个秒杀活动信息
     *
     */
    public SeckillVo getSeckillById(Integer skId){
        SecKillDefineRecord record = db().select(SEC_KILL_DEFINE.SK_ID,SEC_KILL_DEFINE.NAME,SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_DEFINE.START_TIME,SEC_KILL_DEFINE.END_TIME,
            SEC_KILL_DEFINE.LIMIT_AMOUNT,SEC_KILL_DEFINE.LIMIT_PAYTIME,SEC_KILL_DEFINE.FREE_FREIGHT,SEC_KILL_DEFINE.CARD_ID,SEC_KILL_DEFINE.SHARE_CONFIG).
            from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(skId)).fetchOne().into(SecKillDefineRecord.class);
        SeckillVo res = record.into(SeckillVo.class);

        GoodsView goods = saas().getShopApp(getShopId()).goods.getGoodsView(record.getGoodsId());
        res.setGoods(goods);
        res.setSecKillProduct(this.getSecKillProductVo(skId));
        res.setMemberCard(saas().getShopApp(getShopId()).member.card.getMemberCardByCardIdsString(record.getCardId()));
        res.setShopShareConfig(Util.parseJson(record.getShareConfig(), ShopShareConfig.class));

        return res;
    }

    private List<SecKillProductVo> getSecKillProductVo(Integer skId){
        return  db().select(SEC_KILL_PRODUCT_DEFINE.SKPRO_ID,GOODS_SPEC_PRODUCT.PRD_DESC,GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_NUMBER,SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE,SEC_KILL_PRODUCT_DEFINE.TOTAL_STOCK,SEC_KILL_PRODUCT_DEFINE.STOCK).
            from(SEC_KILL_PRODUCT_DEFINE).innerJoin(GOODS_SPEC_PRODUCT).on(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).
            where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(skId)).fetch().into(SecKillProductVo.class);
    }

    /**
     * 活动新增用户
     *
     * @param param
     */
    public PageResult<MemberInfoVo> getSeckillSourceUserList(MarketSourceUserListParam param) {
        MemberPageListParam pageListParam = new MemberPageListParam();
        pageListParam.setCurrentPage(param.getCurrentPage());
        pageListParam.setPageRows(param.getPageRows());
        pageListParam.setMobile(param.getMobile());
        pageListParam.setUsername(param.getUserName());
        pageListParam.setInviteUserName(param.getInviteUserName());

        return saas().getShopApp(getShopId()).member.getSourceActList(pageListParam, MemberService.INVITE_SOURCE_SECKILL, param.getActivityId());
    }

    /**
     * 秒杀订单
     *
     */
    public PageResult<OrderListInfoVo> getSeckillOrderList(MarketOrderListParam param) {
        OrderPageListQueryParam orderParam =new OrderPageListQueryParam();
        orderParam.setCurrentPage(param.getCurrentPage());
        orderParam.setPageRows(param.getPageRows());
        orderParam.setActivityId(param.getActivityId());
        orderParam.setGoodsType(OrderConstant.GOODS_TYPE_SECKILL);
        orderParam.setGoodsName(param.getGoodsName());
        orderParam.setOrderSn(param.getOrderSn());
        orderParam.setOrderStatus(param.getOrderStatus());

        orderParam.setMobile(param.getMobile());
        orderParam.setConsignee(param.getConsignee());
        orderParam.setCreateTimeStart(param.getCreateTimeStart());
        orderParam.setCreateTimeEnd(param.getCreateTimeEnd());

        orderParam.setCountryCode(param.getCountryCode());
        orderParam.setProvinceCode(param.getProvinceCode());
        orderParam.setCityCode(param.getCityCode());
        orderParam.setDistrictCode(param.getDistrictCode());

        PageResult<OrderListInfoVo> pageList = (PageResult<OrderListInfoVo>) saas().getShopApp(getShopId()).readOrder.getPageList(orderParam);

        if(pageList.getDataList() != null){
            pageList.getDataList().forEach(data->{
                data.setChildOrders(null);
                data.setGoods(null);
            });
        }

        return pageList;
    }
    /**
     * 获取小程序码
     */
    public ShareQrCodeVo getMpQrCode(Integer skId) throws Exception {

        String pathParam="paramId="+skId;
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.SECKILL_GOODS_ITEM_INFO, pathParam);

        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.SECKILL_GOODS_ITEM_INFO.getPathUrl(pathParam));
        return vo;
    }

}
