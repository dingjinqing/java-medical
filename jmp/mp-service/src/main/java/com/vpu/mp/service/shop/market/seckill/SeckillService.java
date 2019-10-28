package com.vpu.mp.service.shop.market.seckill;

import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;
import com.vpu.mp.db.shop.tables.records.SecKillProductDefineRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import com.vpu.mp.service.pojo.shop.goods.goods.GoodsView;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.seckill.*;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.SecKillActivityVo;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            for(SeckillProductAddParam secKillProduct : param.getSecKillProduct()){
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

    public List<SecKillProductVo> getSecKillProductVo(Integer skId){
        return  db().select(SEC_KILL_PRODUCT_DEFINE.SKPRO_ID,GOODS_SPEC_PRODUCT.PRD_DESC,GOODS_SPEC_PRODUCT.PRD_PRICE,GOODS_SPEC_PRODUCT.PRD_NUMBER,SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE,SEC_KILL_PRODUCT_DEFINE.TOTAL_STOCK,SEC_KILL_PRODUCT_DEFINE.STOCK).
            from(SEC_KILL_PRODUCT_DEFINE).innerJoin(GOODS_SPEC_PRODUCT).on(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(GOODS_SPEC_PRODUCT.PRD_ID)).
            where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(skId)).fetch().into(SecKillProductVo.class);
    }

    /**
     * 获取商品集合内的秒杀价格
     * @param goodsIds 商品id集合
     * @param date 限制时间
     * @return key:商品id，value:活动价格
     */
    public Map<Integer, BigDecimal> getSecKillProductVo(List<Integer> goodsIds, Timestamp date){
        return db().select(SEC_KILL_DEFINE.GOODS_ID, DSL.min(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE).as(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE))
            .from(SEC_KILL_DEFINE).innerJoin(SEC_KILL_PRODUCT_DEFINE).on(SEC_KILL_DEFINE.SK_ID.eq(SEC_KILL_PRODUCT_DEFINE.SK_ID))
            .where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL))
            .and(SEC_KILL_DEFINE.END_TIME.gt(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds))
            .groupBy(SEC_KILL_DEFINE.GOODS_ID)
            .fetchMap(SEC_KILL_DEFINE.GOODS_ID, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE);
    }

    /**
     * 获取商品集合内的秒杀活动信息
     * @param goodsIds 商品id集合
     * @param date 限制时间
     * @return key:商品id，value:{@link com.vpu.mp.service.pojo.wxapp.goods.goods.activity.SecKillActivityVo}
     */
    public Map<Integer, SecKillActivityVo> getGoodsSecKillInfo(List<Integer> goodsIds, Timestamp date){
        Map<Integer, List<Record5<Integer,Integer,Timestamp, Timestamp, BigDecimal>>> secKillInfos = db().select(SEC_KILL_DEFINE.SK_ID,SEC_KILL_DEFINE.GOODS_ID, SEC_KILL_DEFINE.START_TIME, SEC_KILL_DEFINE.END_TIME, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)
            .from(SEC_KILL_DEFINE).innerJoin(SEC_KILL_PRODUCT_DEFINE).on(SEC_KILL_DEFINE.SK_ID.eq(SEC_KILL_PRODUCT_DEFINE.SK_ID))
            .where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL))
            .and(SEC_KILL_DEFINE.END_TIME.gt(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds))
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(SEC_KILL_DEFINE.GOODS_ID)));

        Map<Integer, SecKillActivityVo> returnMap = new HashMap<>();

        secKillInfos.forEach((goodsId,secKillPrds)->{
            secKillPrds.sort(Comparator.comparing(x->x.get(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)));
            Record5<Integer,Integer,Timestamp, Timestamp, BigDecimal> secKillPrd = secKillPrds.get(0);
            SecKillActivityVo vo = new SecKillActivityVo();
            vo.setActivityId(secKillPrd.get(SEC_KILL_DEFINE.SK_ID));
            vo.setActivityPrice(secKillPrd.get(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE));
            vo.setStartTime(secKillPrd.get(SEC_KILL_DEFINE.START_TIME));
            vo.setEndTime(secKillPrd.get(SEC_KILL_DEFINE.END_TIME));
            returnMap.put(goodsId,vo);
        });

        return returnMap;
    }

    /**
     * 活动新增用户
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
	public PageResult<MarketOrderListVo> getSeckillOrderList(MarketOrderListParam param) {
        return saas().getShopApp(getShopId()).readOrder.getMarketOrderList(param,OrderConstant.GOODS_TYPE_SECKILL);
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

    /**
     * 根据商品id获取秒杀活动id
     * @param goodsId 商品id
     * @param date 当前时间
     * @return 秒杀活动id
     */
    public Integer getSecKillIdByGoodsId(Integer goodsId,Timestamp date){
        return db().select(SEC_KILL_DEFINE.SK_ID)
            .from(SEC_KILL_DEFINE)
            .where(SEC_KILL_DEFINE.START_TIME.lessThan(date))
            .and(SEC_KILL_DEFINE.END_TIME.greaterThan(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.eq(goodsId))
            .and(SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL))
            .fetchOne(SEC_KILL_DEFINE.SK_ID);
    }
    /**
     * 根据商品id获取秒杀活动id
     * @param goodsIds 商品id
     * @param date 当前时间
     * @return 秒杀活动id
     */
    public Map<Integer,Integer> getSecKillIdByGoodsIds(List<Integer> goodsIds, Timestamp date){
        return db().select(SEC_KILL_DEFINE.SK_ID,SEC_KILL_DEFINE.GOODS_ID)
            .from(SEC_KILL_DEFINE)
            .where(SEC_KILL_DEFINE.START_TIME.lessThan(date))
            .and(SEC_KILL_DEFINE.END_TIME.greaterThan(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds))
            .and(SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL))
            .fetchMap(SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_DEFINE.SK_ID);
    }

    /**
     *
     * @param goodsId
     * @param nowDate
     */
    public void isOnGoingSecKill(Integer goodsId, Timestamp nowDate) {
    }
}
