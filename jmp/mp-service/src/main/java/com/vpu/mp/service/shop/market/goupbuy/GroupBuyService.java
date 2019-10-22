package com.vpu.mp.service.shop.market.goupbuy;


import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyProductDefineRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyDetailParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyEditParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.*;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.analysis.OrderActivityUserNum;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.order.OrderReadService;
import org.jooq.Record;
import org.jooq.Record2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author 孔德成
 * @date 2019/7/18 15:55
 */

@Service
public class GroupBuyService extends ShopBaseService {

    private static final byte USE_STATUS = 1;
    private static final byte STOP_STATUS = 0;


    @Autowired
    private GroupBuyListService groupBuyListService;
    @Autowired
    private OrderReadService orderReadService;
    @Autowired
    private CouponService couponService;

    /**
     * 添加拼团活动
     *
     * @param groupBuy
     * @param flag
     */
    public void addGroupBuy(GroupBuyParam groupBuy, Boolean status) {
        transaction(() -> {
            //分享配置转json
            groupBuy.setShareConfig(Util.toJson(groupBuy.getShare()));
            //订单总库存
            Integer stock = groupBuy.getProduct().stream().mapToInt(group -> group.getStock()).sum();
            //拼团信息
            GroupBuyDefineRecord groupBuyDefineRecord = db().newRecord(GROUP_BUY_DEFINE, groupBuy);
            groupBuyDefineRecord.setStatus(status == true ? USE_STATUS : STOP_STATUS);
            groupBuyDefineRecord.setStock(stock.shortValue());
            groupBuyDefineRecord.insert();
            //拼团商品规格价格信息
            groupBuy.getProduct().forEach((product) -> {
                GroupBuyProductDefineRecord productDefineRecord = db().newRecord(GROUP_BUY_PRODUCT_DEFINE, product);
                productDefineRecord.setActivityId(groupBuyDefineRecord.getId());
                productDefineRecord.insert();
            });
        });
    }

    /**
     * 根据id 获取活动record
     * @param id
     * @return
     */
    public GroupBuyDefineRecord getGroupBuyRecord(Integer id){
       return  db().selectFrom(GROUP_BUY_DEFINE).where(GROUP_BUY_DEFINE.ID.eq(id)).fetchOne();
    }
    /**
     * 删除
     *
     * @param id id
     */
    public int deleteGroupBuy(Integer id) {
        return db().update(GROUP_BUY_DEFINE)
                .set(GROUP_BUY_DEFINE.DEL_FLAG, DelFlag.DISABLE.getCode())
                .where(GROUP_BUY_DEFINE.ID.eq(id))
                .execute();
    }

    /**
     * 编辑测试
     *
     * @param param GroupBuyEditParam
     * @return int
     */
    public void updateGroupBuy(GroupBuyEditParam param) {
        transaction(() -> {
            //分享配置转json
            param.setShareConfig(Util.toJson(param.getShare()));
            //订单总库存
            Integer stock = param.getProduct().stream().mapToInt(group -> group.getStock()).sum();
            //拼团信息
            GroupBuyDefineRecord groupBuyDefineRecord = db().newRecord(GROUP_BUY_DEFINE, param);
            groupBuyDefineRecord.setStock(stock.shortValue());
            groupBuyDefineRecord.update();
            //拼团商品规格价格信息
            db().delete(GROUP_BUY_PRODUCT_DEFINE).where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(groupBuyDefineRecord.getId())).execute();
            param.getProduct().forEach((product) -> {
                GroupBuyProductDefineRecord productDefineRecord = db().newRecord(GROUP_BUY_PRODUCT_DEFINE, product);
                productDefineRecord.setActivityId(groupBuyDefineRecord.getId());
                productDefineRecord.insert();
            });
        });

    }

    public void shareGroupBuy() {
    }

    /**
     * 停用或者启用
     *
     * @param id
     * @return
     */
    public int changeStatusActivity(Integer id) {
        Byte status = db().select(GROUP_BUY_DEFINE.STATUS).from(GROUP_BUY_DEFINE).where(GROUP_BUY_DEFINE.ID.eq(id)).fetchOne().component1();
        if (status != null && status == STOP_STATUS) {
            return db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS, USE_STATUS)
                    .where(GROUP_BUY_DEFINE.ID.eq(id))
                    .and(GROUP_BUY_DEFINE.STATUS.eq(STOP_STATUS))
                    .execute();
        } else if (status != null && status == USE_STATUS) {
            return db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS, STOP_STATUS)
                    .where(GROUP_BUY_DEFINE.ID.eq(id))
                    .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
                    .execute();
        }
        return 0;
    }


    /**
     * 查询拼团详情
     *
     * @param id
     * @return
     */
    public GroupBuyDetailVo detailGroupBuy(Integer id) {
        Record record = db().select().from(GROUP_BUY_DEFINE).where(GROUP_BUY_DEFINE.ID.eq(id)
                .and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
        ).fetchOne();
        if (record == null) {
            return null;
        }
        GroupBuyDetailVo groupBuy = record.into(GroupBuyDetailVo.class);
        //产品规格信息
        List<GroupBuyProductVo> buyProductVos = db()
                .select(GROUP_BUY_PRODUCT_DEFINE.ID,
                        GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID,
                        GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID,
                        GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE,
                        GROUP_BUY_PRODUCT_DEFINE.GROUPER_PRICE,
                        GROUP_BUY_PRODUCT_DEFINE.SALE_NUM,
                        GROUP_BUY_PRODUCT_DEFINE.STOCK,
                        GOODS_SPEC_PRODUCT.PRD_DESC,
                        GOODS_SPEC_PRODUCT.PRD_PRICE,
                        GOODS_SPEC_PRODUCT.PRD_NUMBER
                )
                .from(GROUP_BUY_PRODUCT_DEFINE)
                .leftJoin(GOODS_SPEC_PRODUCT).on(GOODS_SPEC_PRODUCT.PRD_ID.eq(GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID))
                .where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(id)).fetch().into(GroupBuyProductVo.class);
        //优惠卷信息
        List<Integer> ids = Util.splitValueToList(groupBuy.getRewardCouponId());
        List<CouponView> couponViews = couponService.getCouponViewByIds(ids);
        groupBuy.setCouponViews(couponViews);
        groupBuy.setProductList(buyProductVos);
        groupBuy.setShare(Util.parseJson(groupBuy.getShareConfig(), GroupBuyShareConfigVo.class));
        groupBuy.setShareConfig(null);
        return groupBuy;
    }

    /**
     * 校验商品是否有叠加
     *
     * @param param GroupBuyParam
     * @return 0
     */
    public Boolean validGroupGoods(Integer id,Integer goodsId,Timestamp startTime,Timestamp endTime) {
        return db().fetchCount(GROUP_BUY_DEFINE, GROUP_BUY_DEFINE.GOODS_ID.eq(goodsId)
                        .and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                        .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
                        .and(GROUP_BUY_DEFINE.ID.notEqual(id))
                        .and(GROUP_BUY_DEFINE.START_TIME.lt(startTime))
                        .and(GROUP_BUY_DEFINE.END_TIME.gt(endTime))) == 0;

    }

    /**
     * 拼团订单列表
     *
     * @return
     */
    public PageResult<MarketOrderListVo> groupBuyOrderList(MarketOrderListParam param) {
        return groupBuyListService.groupBuyOrderList(param);
    }

    /**
     * 活动新增用户列表
     *
     * @param param
     */
    public PageResult<MemberInfoVo> groupBuyNewUserList(MarketSourceUserListParam param) {
        return groupBuyListService.groupBuyNewUserList(param);
    }

    /**
     * 参团明细列表
     *
     * @param param
     * @return
     */
    public PageResult<GroupBuyDetailListVo> detailGroupBuyList(GroupBuyDetailParam param) {
        return groupBuyListService.detailGroupBuyList(param);
    }

    /**
     * 查询团购列表
     *
     * @param param
     * @return
     */
    public Object getListGroupBuy(GroupBuyListParam param) {
        return groupBuyListService.getListGroupBuy(param);
    }

    /**
     * 拼团活动效果数据
     *
     * @param param
     * @return
     */
    public GroupBuyAnalysisVo groupBuyAnalysis(GroupBuyAnalysisParam param) {
        GroupBuyAnalysisVo analysisVo = new GroupBuyAnalysisVo();
        Timestamp startDate = param.getStartTime();
        Timestamp endDate = param.getEndTime();
        if (startDate == null || endDate == null) {
            startDate = DateUtil.currentMonthFirstDay();
            endDate = DateUtil.getLocalDateTime();
        }
        //获取销售额等金额
        List<ActiveDiscountMoney> discountMoneyList = orderReadService.getActiveDiscountMoney(OrderConstant.GOODS_TYPE_PIN_GROUP, param.getId(), startDate, endDate);
        //获取参与用户信息
        ActiveOrderList activeOrderList = orderReadService.getActiveOrderList(OrderConstant.GOODS_TYPE_PIN_GROUP, param.getId(), startDate, endDate);

        while (Objects.requireNonNull(startDate).compareTo(endDate) <= 0) {
            //活动实付金额
            ActiveDiscountMoney discountMoney = getDiscountMoneyByDate(discountMoneyList, startDate);
            if (discountMoney == null) {
                analysisVo.getGoodsPriceList().add(BigDecimal.ZERO);
                analysisVo.getMarketPriceList().add(BigDecimal.ZERO);
                analysisVo.getRatioList().add(BigDecimal.ZERO);
            } else {
                BigDecimal goodsPrice = Optional.ofNullable(discountMoney.getGoodsPrice()).orElse(BigDecimal.ZERO);
                BigDecimal marketPric = Optional.ofNullable(discountMoney.getMarketPrice()).orElse(BigDecimal.ZERO);
                BigDecimal totalPrice = Optional.ofNullable(discountMoney.getDiscountedTotalPrice()).orElse(BigDecimal.ZERO);
                analysisVo.getGoodsPriceList().add(goodsPrice);
                analysisVo.getMarketPriceList().add(marketPric);
                analysisVo.getRatioList().add(totalPrice.compareTo(BigDecimal.ZERO) > 0 ?
                        marketPric.subtract(goodsPrice).divide(totalPrice, BigDecimal.ROUND_FLOOR) : BigDecimal.ZERO);
            }
            //新用户数
            OrderActivityUserNum newUser = getUserNum(activeOrderList.getNewUserNum(), startDate);
            if (newUser == null) {
                analysisVo.getNewUserList().add(0);
            } else {
                analysisVo.getNewUserList().add(newUser.getNum());
            }
            //老用户数
            OrderActivityUserNum oldUser = getUserNum(activeOrderList.getOldUserNum(), startDate);
            if (oldUser == null) {
                analysisVo.getOldUserList().add(0);
            } else {
                analysisVo.getOldUserList().add(oldUser.getNum());
            }
            analysisVo.getDateList().add(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE, startDate));
            startDate = Util.getEarlyTimeStamp(startDate, 1);
        }
        return analysisVo;
    }

    /**
     * 根据goodsId获取拼团定义
     * @param goodsId 商品id
     * @param date 当前时间
     * @return List<GroupBuyProductDefineRecord>
     */
    public List<Record2<Integer,BigDecimal>> getGroupBuyProductByGoodsId(Integer goodsId, Timestamp date){
        return db().select(GROUP_BUY_DEFINE.GOODS_ID,GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_PRODUCT_DEFINE)
            .leftJoin(GROUP_BUY_DEFINE)
            .on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
            .and(GROUP_BUY_DEFINE.STOCK.notEqual((short) 0))
            .and(GROUP_BUY_DEFINE.GOODS_ID.eq(goodsId))
            .and(GROUP_BUY_DEFINE.START_TIME.lessThan(date))
            .and(GROUP_BUY_DEFINE.END_TIME.greaterThan(date))
            .fetch();
    }
    /**
     * 根据goodsIds获取拼团定义
     * @param goodsIds 商品id
     * @param date 当前时间
     * @return List<GroupBuyProductDefineRecord>
     */
    public Map<Integer,List<Record2<Integer,BigDecimal>>> getGroupBuyProductByGoodsIds(List<Integer> goodsIds, Timestamp date){
        return db().select(GROUP_BUY_DEFINE.GOODS_ID,GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_PRODUCT_DEFINE)
            .leftJoin(GROUP_BUY_DEFINE)
            .on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS))
            .and(GROUP_BUY_DEFINE.STOCK.notEqual((short) 0))
            .and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds))
            .and(GROUP_BUY_DEFINE.START_TIME.lessThan(date))
            .and(GROUP_BUY_DEFINE.END_TIME.greaterThan(date))
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(x->x.get(GROUP_BUY_DEFINE.GOODS_ID)));
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

}
