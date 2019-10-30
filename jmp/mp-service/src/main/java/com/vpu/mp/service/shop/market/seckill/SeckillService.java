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
import com.vpu.mp.service.pojo.shop.market.MarketAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.seckill.*;
import com.vpu.mp.service.pojo.shop.market.seckill.analysis.SeckillAnalysisDataVo;
import com.vpu.mp.service.pojo.shop.market.seckill.analysis.SeckillAnalysisParam;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.analysis.OrderActivityUserNum;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.goods.activity.SecKillActivityVo;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.util.Comparator;
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
        SelectWhereStep<? extends Record> select = db().select(SEC_KILL_DEFINE.SK_ID,SEC_KILL_DEFINE.NAME,SEC_KILL_DEFINE.START_TIME,SEC_KILL_DEFINE.END_TIME,
            SEC_KILL_DEFINE.STATUS,SEC_KILL_DEFINE.SALE_NUM,SEC_KILL_DEFINE.LIMIT_AMOUNT,SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_DEFINE.STOCK,
            GOODS.GOODS_NAME,GOODS.GOODS_NUMBER,GOODS.GOODS_IMG,GOODS.SHOP_PRICE,GOODS.IS_ON_SALE).
            from(SEC_KILL_DEFINE).
            leftJoin(GOODS).on(SEC_KILL_DEFINE.GOODS_ID.eq(GOODS.GOODS_ID));
        select = buildOptions(select,param);
        select.orderBy(SEC_KILL_DEFINE.CREATE_TIME.desc());
        return getPageResult(select,param.getCurrentPage(),param.getPageRows(),SeckillPageListQueryVo.class);
    }
    /**
     * 秒杀活动列表分页查询(装修页弹窗选择)
     *
     */
    public PageResult<SeckillPageListQueryVo> getPageListDialog(SeckillPageListQueryParam param) {
        PageResult<SeckillPageListQueryVo> res = getPageList(param);
        for(SeckillPageListQueryVo vo : res.dataList){
            vo.setSecPrice(getMinProductSecPrice(vo.getSkId()));
        }
        return res;
    }

    private SelectWhereStep<? extends Record> buildOptions(SelectWhereStep<? extends  Record> select,SeckillPageListQueryParam param){
        select.where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if(param.getState() != null && param.getState().length > 0) {
            /** 状态过滤*/
            Condition stateCondition = DSL.noCondition();
            Timestamp now = DateUtil.getLocalDateTime();
            for(byte state : param.getState()){
                if(state == 1){
                    stateCondition = stateCondition.or((SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.lt(now)).and(SEC_KILL_DEFINE.END_TIME.gt(now)));
                }
                if(state == 2){
                    stateCondition = stateCondition.or((SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.gt(now)));
                }
                if(state == 3){
                    stateCondition = stateCondition.or((SEC_KILL_DEFINE.STATUS.eq(STATUS_NORMAL)).and(SEC_KILL_DEFINE.END_TIME.lt(now)));
                }
                if(state == 4){
                    stateCondition = stateCondition.or(SEC_KILL_DEFINE.STATUS.eq(STATUS_DISABLED));
                }
            }
            select.where(stateCondition);
        }
        if(!StringUtils.isEmpty(param.getKeywords())){
            select.where(SEC_KILL_DEFINE.NAME.contains(param.getKeywords()).or(GOODS.GOODS_NAME.contains(param.getKeywords())));
        }

        return select;
    }

    /**
     * 取单个秒杀活动的最小的一个规格秒杀价
     *
     */
    private BigDecimal getMinProductSecPrice(int skId){
        return db().select(DSL.min(SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE)).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(skId)).fetchOne().into(BigDecimal.class);
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
     * 秒杀效果分析的echarts图表数据
     *
     *
     */
    public SeckillAnalysisDataVo getSeckillAnalysisData(SeckillAnalysisParam param){
        SeckillAnalysisDataVo analysisVo = new SeckillAnalysisDataVo();
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        if (startDate == null || endDate == null) {
            startDate = DateUtil.currentMonthFirstDay();
            endDate = DateUtil.getLocalDateTime();
        }
        //获取销售额等金额
        List<ActiveDiscountMoney> discountMoneyList = saas.getShopApp(getShopId()).readOrder.getActiveDiscountMoney(OrderConstant.GOODS_TYPE_SECKILL, param.getSkId(), startDate, endDate);
        //获取参与用户信息
        ActiveOrderList activeOrderUserList = saas.getShopApp(getShopId()).readOrder.getActiveOrderList(OrderConstant.GOODS_TYPE_SECKILL, param.getSkId(), startDate, endDate);

        while (Objects.requireNonNull(startDate).compareTo(endDate) <= 0) {
            //活动实付金额、付款订单数、付款商品件数
            ActiveDiscountMoney discountMoney = getDiscountMoneyByDate(discountMoneyList, startDate);
            if (discountMoney == null) {
                analysisVo.getPaymentAmount().add(BigDecimal.ZERO);
                analysisVo.getDiscountAmount().add(BigDecimal.ZERO);
                analysisVo.getCostEffectivenessRatio().add(BigDecimal.ZERO);
                analysisVo.getPaidOrderNumber().add(0);
                analysisVo.getPaidGoodsNumber().add(0);
            } else {
                BigDecimal goodsPrice = Optional.ofNullable(discountMoney.getPaymentAmount()).orElse(BigDecimal.ZERO);
                BigDecimal marketPric = Optional.ofNullable(discountMoney.getDiscountAmount()).orElse(BigDecimal.ZERO);
                analysisVo.getPaymentAmount().add(Optional.ofNullable(discountMoney.getPaymentAmount()).orElse(BigDecimal.ZERO));
                analysisVo.getDiscountAmount().add(Optional.ofNullable(discountMoney.getDiscountAmount()).orElse(BigDecimal.ZERO));
                analysisVo.getCostEffectivenessRatio().add(goodsPrice.compareTo(BigDecimal.ZERO) > 0 ?
                    marketPric.divide(goodsPrice, BigDecimal.ROUND_FLOOR) : BigDecimal.ZERO);
                analysisVo.getPaidOrderNumber().add(discountMoney.getPaidOrderNumber());
                analysisVo.getPaidGoodsNumber().add(discountMoney.getPaidGoodsNumber());
            }

            //新用户数
            OrderActivityUserNum newUser = getUserNum(activeOrderUserList.getNewUserNum(), startDate);
            if (newUser == null) {
                analysisVo.getNewUserNumber().add(0);
            } else {
                analysisVo.getNewUserNumber().add(newUser.getNum());
            }
            //老用户数
            OrderActivityUserNum oldUser = getUserNum(activeOrderUserList.getOldUserNum(), startDate);
            if (oldUser == null) {
                analysisVo.getOldUserNumber().add(0);
            } else {
                analysisVo.getOldUserNumber().add(oldUser.getNum());
            }
            analysisVo.getDateList().add(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE, startDate));
            startDate = Util.getEarlyTimeStamp(startDate, 1);
        }
        return analysisVo;
    }

    private ActiveDiscountMoney getDiscountMoneyByDate(List<ActiveDiscountMoney> discountMoneyList, Timestamp timestamp) {
        for (ActiveDiscountMoney data : discountMoneyList) {
            if (data.getCreateTime().equals(timestamp)) {
                return data;
            }
        }
        return null;
    }

    private OrderActivityUserNum getUserNum(List<OrderActivityUserNum> list, Timestamp timestamp) {
        for (OrderActivityUserNum activityUserNum : list) {
            if (activityUserNum.getDate().equals(timestamp)) {
                return activityUserNum;
            }
        }
        return null;
    }

    /**
     *
     * @param goodsId
     * @param nowDate
     */
    public void isOnGoingSecKill(Integer goodsId, Timestamp nowDate) {
    }
}
