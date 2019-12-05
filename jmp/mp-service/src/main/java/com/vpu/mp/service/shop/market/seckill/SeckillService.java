package com.vpu.mp.service.shop.market.seckill;

import com.vpu.mp.db.shop.tables.records.SecKillDefineRecord;
import com.vpu.mp.db.shop.tables.records.SecKillProductDefineRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
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
import com.vpu.mp.service.pojo.shop.market.seckill.analysis.SeckillAnalysisDataVo;
import com.vpu.mp.service.pojo.shop.market.seckill.analysis.SeckillAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.seckill.analysis.SeckillAnalysisTotalVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.shop.operation.RecordContentTemplate;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.analysis.OrderActivityUserNum;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.member.MemberService;
import jodd.util.StringUtil;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.OrderInfo.ORDER_INFO;
import static com.vpu.mp.db.shop.tables.SecKillDefine.SEC_KILL_DEFINE;
import static com.vpu.mp.db.shop.tables.SecKillList.SEC_KILL_LIST;
import static com.vpu.mp.db.shop.tables.SecKillProductDefine.SEC_KILL_PRODUCT_DEFINE;

/**
 * @author 王兵兵
 *
 * 2019年8月5日
 */
@Service
public class SeckillService extends ShopBaseService{

    @Autowired
    public SeckillListService seckillList;

    @Autowired
    private QrCodeService qrCode;

    @Autowired
    private GoodsService goodsService;

    /**
     * 秒杀活动列表分页数据
     *
     */
    private PageResult<SeckillPageListQueryVo> getPageData(SeckillPageListQueryParam param) {
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
     * 秒杀活动列表分页数据
     *
     */
    public PageResult<SeckillPageListQueryVo> getPageList(SeckillPageListQueryParam param) {
        PageResult<SeckillPageListQueryVo> res = getPageData(param);
        for(SeckillPageListQueryVo vo : res.dataList){
            vo.setCurrentState(Util.getActStatus(vo.getStatus(),vo.getStartTime(),vo.getEndTime()));
        }
        return res;
    }

    /**
     * 秒杀活动列表分页查询(装修页弹窗选择)
     *
     */
    public PageResult<SeckillPageListQueryVo> getPageListDialog(SeckillPageListQueryParam param) {
        PageResult<SeckillPageListQueryVo> res = getPageData(param);
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
                    stateCondition = stateCondition.or((SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.lt(now)).and(SEC_KILL_DEFINE.END_TIME.gt(now)));
                }
                if(state == 2){
                    stateCondition = stateCondition.or((SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.gt(now)));
                }
                if(state == 3){
                    stateCondition = stateCondition.or((SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.END_TIME.lt(now)));
                }
                if(state == 4){
                    stateCondition = stateCondition.or(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_DISABLE));
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
                productRecord.setTotalStock(productRecord.getStock());
                db().executeInsert(productRecord);
            }
        });
        if(param.getStartTime().before(DateUtil.getLocalDateTime()) && param.getEndTime().after(DateUtil.getLocalDateTime())){
            //活动已生效
            saas.getShopApp(getShopId()).shopTaskService.seckillTaskService.monitorGoodsType();
        }

        /** 操作记录 */
        saas().getShopApp(getShopId()).record.insertRecord(Arrays.asList(new Integer[] { RecordContentTemplate.MARKET_SECKILL_ADD.code }), new String[] {param.getName()});
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
            .and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(SEC_KILL_DEFINE.END_TIME.gt(date))
            .and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds))
            .groupBy(SEC_KILL_DEFINE.GOODS_ID)
            .fetchMap(SEC_KILL_DEFINE.GOODS_ID, SEC_KILL_PRODUCT_DEFINE.SEC_KILL_PRICE);
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
    public ShareQrCodeVo getMpQrCode(Integer skId) {

        String pathParam="sk_id="+skId;
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
            .and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
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
            .and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
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
        SeckillAnalysisTotalVo total = new SeckillAnalysisTotalVo();
        total.setTotalPayment(analysisVo.getPaymentAmount().stream().reduce(BigDecimal.ZERO,BigDecimal::add));
        total.setTotalDiscount(analysisVo.getDiscountAmount().stream().reduce(BigDecimal.ZERO,BigDecimal::add));
        total.setTotalCostEffectivenessRatio(analysisVo.getCostEffectivenessRatio().stream().reduce(BigDecimal.ZERO,BigDecimal::add));
        total.setTotalPaidOrderNumber(analysisVo.getPaidOrderNumber().stream().mapToInt(Integer::intValue).sum());
        total.setTotalPaidGoodsNumber(analysisVo.getPaidGoodsNumber().stream().mapToInt(Integer::intValue).sum());
        total.setTotalOldUserNumber(analysisVo.getOldUserNumber().stream().mapToInt(Integer::intValue).sum());
        total.setTotalNewUserNumber(analysisVo.getNewUserNumber().stream().mapToInt(Integer::intValue).sum());
        analysisVo.setTotal(total);
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

    //用户数
    private OrderActivityUserNum getUserNum(List<OrderActivityUserNum> list, Timestamp timestamp) {
        for (OrderActivityUserNum activityUserNum : list) {
            if (activityUserNum.getDate().equals(timestamp)) {
                return activityUserNum;
            }
        }
        return null;
    }

    /**
     * 判断是否已经有有进行中的商品
     * @param goodsId       商品ID
     * @param startTime     起始时间
     * @param endTime       终止时间
     * @return bool
     */
    public boolean isOnGoingSecKill(int goodsId,Timestamp startTime,Timestamp endTime){
        Record r = db().select(SEC_KILL_DEFINE.SK_ID).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.GOODS_ID.eq(goodsId)).and(isConflictingActTime(startTime,endTime))).fetchOne();
        return r != null;
    }

    /**
     * 判断是否已经有有进行中的商品
     * @param skId       秒杀活动ID
     * @return bool
     */
    public boolean isOnGoingSecKill(int skId){
        Record3<Integer, Timestamp, Timestamp> seckill = db().select(SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_DEFINE.START_TIME,SEC_KILL_DEFINE.END_TIME).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(skId)).fetchOne();

        Record r = db().select(SEC_KILL_DEFINE.SK_ID).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.GOODS_ID.eq(seckill.value1())).and(isConflictingActTime(seckill.value2(),seckill.value3()))).fetchOne();
        return r != null;
    }

    private Condition isConflictingActTime(Timestamp startTime,Timestamp endTime){
        return (SEC_KILL_DEFINE.START_TIME.gt(startTime).and(SEC_KILL_DEFINE.START_TIME.lt(endTime))).or(SEC_KILL_DEFINE.END_TIME.gt(startTime).and(SEC_KILL_DEFINE.END_TIME.lt(endTime))).or(SEC_KILL_DEFINE.START_TIME.lt(startTime).and(SEC_KILL_DEFINE.END_TIME.gt(endTime)));
    }

    /**
     * 检查规格库存，更新秒杀规格库存以保证秒杀库存不大于规格库存
     * @param goodsIds
     */
    public void updateSeckillProcudtStock(List<Integer> goodsIds){
        List<SeckillVo> activeSeckillList = getSecKillWithMonitor(goodsIds);
        for(SeckillVo  seckill : activeSeckillList){
            for(SecKillProductVo secKillProduct : seckill.getSecKillProduct()){
                int prdNumber = goodsService.goodsSpecProductService.getPrdNumberByPrdId(secKillProduct.getProductId());
                if(prdNumber < secKillProduct.getStock()){
                    db().update(SEC_KILL_PRODUCT_DEFINE).set(SEC_KILL_PRODUCT_DEFINE.STOCK,prdNumber).set(SEC_KILL_PRODUCT_DEFINE.TOTAL_STOCK,secKillProduct.getTotalStock()-(secKillProduct.getStock()-prdNumber)).execute();
                }
            }
        }
    }

    /**
     * 当前有效的进行中秒杀
     * @return
     */
    private List<SeckillVo> getSecKillWithMonitor(List<Integer> goodsIds){
        List<SeckillVo> res = db().select(SEC_KILL_DEFINE.STOCK,SEC_KILL_DEFINE.GOODS_ID,SEC_KILL_DEFINE.SK_ID).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE).and(SEC_KILL_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(SEC_KILL_DEFINE.START_TIME.lt(DateUtil.getLocalDateTime())).and(SEC_KILL_DEFINE.END_TIME.gt(DateUtil.getLocalDateTime()))).and(SEC_KILL_DEFINE.GOODS_ID.in(goodsIds)).fetchInto(SeckillVo.class);
        for(SeckillVo seckill : res){
            List<SecKillProductVo> seckillProduct = db().select(SEC_KILL_PRODUCT_DEFINE.SKPRO_ID,SEC_KILL_PRODUCT_DEFINE.STOCK,SEC_KILL_PRODUCT_DEFINE.TOTAL_STOCK,SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(seckill.getSkId())).fetchInto(SecKillProductVo.class);
            seckill.setSecKillProduct(seckillProduct);
        }
        return res;
    }

    /**
     * 校验该秒杀规格是否还有库存
     * @param skId
     * @param productId 规格ID
     * @return
     */
    private boolean checkSeckillProductStock(int skId,int productId){
        int seckillStock = db().select(SEC_KILL_PRODUCT_DEFINE.STOCK).from(SEC_KILL_PRODUCT_DEFINE).where(SEC_KILL_PRODUCT_DEFINE.SK_ID.eq(skId).and(SEC_KILL_PRODUCT_DEFINE.PRODUCT_ID.eq(productId))).fetchOne().into(Integer.class);
        int prdNumber = db().select(GOODS_SPEC_PRODUCT.PRD_NUMBER).from(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.PRD_ID.eq(productId)).fetchOne().into(Integer.class);
        return seckillStock > 0 && prdNumber > 0;
    }

    /**
     * 判断秒杀规格的可用状态
     * @param skId 秒杀ID
     * @param productId 规格ID
     * @param userId
     * @return 0正常可用;1该活动不存在;2该活动已停用;3该活动未开始;4该活动已结束;5商品已抢光;6该用户已达到限购数量上限;
     *          7该秒杀为会员专属，该用户没有对应会员卡；8该规格无库存；9有待支付的秒杀订单
     */
    public Byte canApplySecKill(Integer skId,Integer productId,Integer userId) {
        SecKillDefineRecord secKill = db().select(SEC_KILL_DEFINE.asterisk()).from(SEC_KILL_DEFINE).where(SEC_KILL_DEFINE.SK_ID.eq(skId)).fetchOne().into(SecKillDefineRecord.class);
        int goodsNumber = saas.getShopApp(getShopId()).goods.getGoodsView(secKill.getGoodsId()).getGoodsNumber();
        byte res = this.canApplySecKill(secKill,goodsNumber,userId);
        if(res == 0){
            if(!this.checkSeckillProductStock(skId,productId)){
                return 8;
            }
            if(seckillList.checkSeckillOrderWaitPay(skId,userId) != null){
                return 9;
            }
        }
        return res;
    }

    /**
     * 判断秒杀活动的可用状态
     * @param secKill 秒杀基本信息
     * @param goodsNumber goods表的库存
     * @return 0正常;1该活动不存在;2该活动已停用;3该活动未开始;4该活动已结束;5商品已抢光;6该用户已达到限购数量上限;7该秒杀为会员专属，该用户没有对应会员卡
     */
    public Byte canApplySecKill(SecKillDefineRecord secKill,Integer goodsNumber,Integer userId) {
        if(secKill == null){
            return 1;
        }
        if(BaseConstant.ACTIVITY_STATUS_DISABLE.equals(secKill.getStatus())){
            return 2;
        }
        if(secKill.getStartTime().after(DateUtil.getLocalDateTime())){
            return 3;
        }
        if(secKill.getEndTime().before(DateUtil.getLocalDateTime())){
            return 4;
        }
        int minStock = goodsNumber < secKill.getStock() ? goodsNumber : secKill.getStock();
        if(minStock <= 0){
            return 5;
        }
        if(getUserSeckilledGoodsNumber(secKill.getSkId(),userId) >= secKill.getLimitAmount()){
            return 6;
        }
        if(StringUtil.isNotEmpty(secKill.getCardId()) && !userCardExclusiveSeckillIsValid(secKill.getCardId(),userId)){
            return 7;
        }

        return 0;
    }

    /**
     * 已对该活动秒杀下单的数量
     * @param skId
     * @param userId
     * @return
     */
    private Integer getUserSeckilledGoodsNumber(Integer skId,Integer userId) {
        return db().select(DSL.sum(ORDER_INFO.GOODS_AMOUNT)).from(SEC_KILL_LIST).leftJoin(ORDER_INFO).on(SEC_KILL_LIST.ORDER_SN.eq(ORDER_INFO.ORDER_SN)).where(SEC_KILL_LIST.SK_ID.eq(skId).and(SEC_KILL_LIST.USER_ID.eq(userId)).and(SEC_KILL_LIST.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))).groupBy(ORDER_INFO.USER_ID).fetchOne().into(Integer.class);
    }

    /**
     * 判断会员专享的秒杀活动该用户是否可参与
     * @param cardIds 会员卡ID字符串，逗号隔开
     * @param userId
     * @return true是可参与
     */
    private Boolean userCardExclusiveSeckillIsValid(String cardIds,Integer userId) {
        List<ValidUserCardBean> cards = saas.getShopApp(getShopId()).userCard.userCardDao.getValidCardList(userId);
        List<Integer> validCardIds = cards.stream().map(ValidUserCardBean::getCardId).collect(Collectors.toList());
        List<Integer> seckillCardIds = Util.splitValueToList(cardIds);
        validCardIds.removeAll(seckillCardIds);
        return (validCardIds != null && validCardIds.size() > 0);
    }


}
