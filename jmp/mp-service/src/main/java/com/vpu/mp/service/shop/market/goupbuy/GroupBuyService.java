package com.vpu.mp.service.shop.market.goupbuy;


import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyProductDefineRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.image.ShareQrCodeVo;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListParam;
import com.vpu.mp.service.pojo.shop.market.MarketOrderListVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyAnalysisParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyDetailParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyEditParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyListParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.param.GroupBuyProductParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyAnalysisVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailListVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyDetailVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyParam;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyProductVo;
import com.vpu.mp.service.pojo.shop.market.groupbuy.vo.GroupBuyShareConfigVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveDiscountMoney;
import com.vpu.mp.service.pojo.shop.order.analysis.ActiveOrderList;
import com.vpu.mp.service.pojo.shop.order.analysis.OrderActivityUserNum;
import com.vpu.mp.service.pojo.shop.qrcode.QrCodeTypeEnum;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsPrdMpVo;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyGoodsInfo;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyInfoVo;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyProductInfo;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyStatusInfo;
import com.vpu.mp.service.pojo.wxapp.market.groupbuy.GroupBuyUserInfo;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.goods.GoodsSpecProductService;
import com.vpu.mp.service.shop.goods.GoodsSpecService;
import com.vpu.mp.service.shop.image.QrCodeService;
import com.vpu.mp.service.shop.order.OrderReadService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_DEFINE;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_DISABLE;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_STATUS_NORMAL;
import static com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant.IS_GROUPER_N;

/**
 * @author 孔德成
 * @date 2019/7/18 15:55
 */

@Service
@Primary
public class GroupBuyService extends ShopBaseService {

    /**
     * 是否默认成团
     */
    public static final Byte IS_DEFAULT_Y = 1;
    public static final Byte IS_DEFAULT_N = 0;

    @Autowired
    private GroupBuyListService groupBuyListService;
    @Autowired
    private OrderReadService orderReadService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private ShopCommonConfigService shopCommonConfigService;
    @Autowired
    private GoodsSpecProductService goodsSpecProductService;
    @Autowired
    private QrCodeService qrCode;

    /**
     * 添加拼团活动
     *
     * @param groupBuy 拼团
     * @param status true 启用 false 禁用
     */
    public void addGroupBuy(GroupBuyParam groupBuy, Boolean status) {
        transaction(() -> {
            //分享配置转json
            groupBuy.setShareConfig(Util.toJson(groupBuy.getShare()));
            //订单总库存
            Integer stock = groupBuy.getProduct().stream().mapToInt(GroupBuyProductParam::getStock).sum();
            //拼团信息
            GroupBuyDefineRecord groupBuyDefineRecord = db().newRecord(GROUP_BUY_DEFINE, groupBuy);
            groupBuyDefineRecord.setStatus(status == true ? ACTIVITY_STATUS_NORMAL : ACTIVITY_STATUS_DISABLE);
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
     *
     * @param id
     * @return
     */
    public GroupBuyDefineRecord getGroupBuyRecord(Integer id) {
        return db().selectFrom(GROUP_BUY_DEFINE).where(GROUP_BUY_DEFINE.ID.eq(id)).fetchOne();
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

    /**
     * 分享拼团链接
     * @param id 活动Id
     * @return 二维码信息
     */
    public ShareQrCodeVo shareGroupBuy(Integer id) {
        String pathParam="paramId="+id;
        String imageUrl = qrCode.getMpQrCode(QrCodeTypeEnum.GROUP_BOOKING, pathParam);
        ShareQrCodeVo vo = new ShareQrCodeVo();
        vo.setImageUrl(imageUrl);
        vo.setPagePath(QrCodeTypeEnum.GROUP_BOOKING.getPathUrl(pathParam));
        return vo;
    }

    /**
     * 停用或者启用
     *
     * @param id
     * @return
     */
    public int changeStatusActivity(Integer id,Byte status) {
        if (ACTIVITY_STATUS_DISABLE.equals(status)) {
            return db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS, ACTIVITY_STATUS_DISABLE)
                    .where(GROUP_BUY_DEFINE.ID.eq(id))
                    .and(GROUP_BUY_DEFINE.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                    .execute();
        } else if (ACTIVITY_STATUS_NORMAL.equals(status)) {
            return db().update(GROUP_BUY_DEFINE)
                    .set(GROUP_BUY_DEFINE.STATUS, ACTIVITY_STATUS_NORMAL)
                    .where(GROUP_BUY_DEFINE.ID.eq(id))
                    .and(GROUP_BUY_DEFINE.STATUS.eq(ACTIVITY_STATUS_DISABLE))
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
            .and(GROUP_BUY_DEFINE.STATUS.eq(ACTIVITY_STATUS_NORMAL))
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
     * @param
     * @param date
     * @return 0
     */
    public Boolean validGroupGoods(Integer id, Integer goodsId, Timestamp startTime, Timestamp endTime, Timestamp date) {
        Condition where  =GROUP_BUY_DEFINE.GOODS_ID.eq(goodsId)
                .and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                .and(GROUP_BUY_DEFINE.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                .and(GROUP_BUY_DEFINE.START_TIME.le(endTime))
                .and(GROUP_BUY_DEFINE.END_TIME.ge(startTime))
                .and(GROUP_BUY_DEFINE.END_TIME.gt(date))
                .and(GROUP_BUY_DEFINE.START_TIME.lt(date));
        if (id!=null){
            where.and(GROUP_BUY_DEFINE.ID.notEqual(id));
        }
      return   db().fetchCount(GROUP_BUY_DEFINE,where)==0;
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
                BigDecimal goodsPrice = Optional.ofNullable(discountMoney.getPaymentAmount()).orElse(BigDecimal.ZERO);
                BigDecimal marketPric = Optional.ofNullable(discountMoney.getDiscountAmount()).orElse(BigDecimal.ZERO);
                analysisVo.getGoodsPriceList().add(goodsPrice);
                analysisVo.getMarketPriceList().add(marketPric);
                analysisVo.setTotalPrice(analysisVo.getTotalPrice().add(goodsPrice));
                analysisVo.setTotalMarketPrice(analysisVo.getTotalMarketPrice().add(marketPric));
                analysisVo.getRatioList().add(goodsPrice.compareTo(BigDecimal.ZERO) > 0 ?
                    marketPric.divide(goodsPrice, BigDecimal.ROUND_FLOOR) : BigDecimal.ZERO);
            }
            //新用户数
            OrderActivityUserNum newUser = getUserNum(activeOrderList.getNewUserNum(), startDate);
            if (newUser == null) {
                analysisVo.getNewUserList().add(0);
            } else {
                analysisVo.getNewUserList().add(newUser.getNum());
                analysisVo.setTotalNewUser(analysisVo.getTotalNewUser()+newUser.getNum());
            }
            //老用户数
            OrderActivityUserNum oldUser = getUserNum(activeOrderList.getOldUserNum(), startDate);
            if (oldUser == null) {
                analysisVo.getOldUserList().add(0);
            } else {
                analysisVo.getOldUserList().add(oldUser.getNum());
                analysisVo.setTotalOldUser(analysisVo.getTotalOldUser()+oldUser.getNum());
            }
            analysisVo.getDateList().add(DateUtil.dateFormat(DateUtil.DATE_FORMAT_SIMPLE, startDate));
            startDate = Util.getEarlyTimeStamp(startDate, 1);
        }
        return analysisVo;
    }

    /**
     * 根据goodsId获取拼团定义
     *
     * @param goodsId 商品id
     * @param date    当前时间
     * @return List<GroupBuyProductDefineRecord>
     */
    public List<Record2<Integer, BigDecimal>> getGroupBuyProductByGoodsId(Integer goodsId, Timestamp date) {
        return db().select(GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_PRODUCT_DEFINE)
            .leftJoin(GROUP_BUY_DEFINE)
            .on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(GROUP_BUY_DEFINE.STATUS.eq(ACTIVITY_STATUS_NORMAL))
            .and(GROUP_BUY_DEFINE.STOCK.notEqual((short) 0))
            .and(GROUP_BUY_DEFINE.GOODS_ID.eq(goodsId))
            .and(GROUP_BUY_DEFINE.START_TIME.lessThan(date))
            .and(GROUP_BUY_DEFINE.END_TIME.greaterThan(date))
            .fetch();
    }

    /**
     * 根据goodsIds获取拼团定义
     *
     * @param goodsIds 商品id
     * @param date     当前时间
     * @return List<GroupBuyProductDefineRecord>
     */
    public Map<Integer, List<Record2<Integer, BigDecimal>>> getGroupBuyProductByGoodsIds(List<Integer> goodsIds, Timestamp date) {
        return db().select(GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_PRODUCT_DEFINE)
            .leftJoin(GROUP_BUY_DEFINE)
            .on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
            .and(GROUP_BUY_DEFINE.STATUS.eq(ACTIVITY_STATUS_NORMAL))
            .and(GROUP_BUY_DEFINE.STOCK.notEqual((short) 0))
            .and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds))
            .and(GROUP_BUY_DEFINE.START_TIME.lessThan(date))
            .and(GROUP_BUY_DEFINE.END_TIME.greaterThan(date))
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(x -> x.get(GROUP_BUY_DEFINE.GOODS_ID)));
    }

//    /**
//     * 获取集合内商品所参与的拼团信息
//     * @param goodsIds 待查询商品id集合
//     * @param date 限制时间
//     * @return key:商品id value:{@link com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GroupBuyActivityVo}
//     * @author 李晓冰
//     */
//    public Map<Integer, GroupBuyActivityVo>  getGoodsGroupByInfo(List<Integer> goodsIds, Timestamp date) {
//        // 获取有效拼团规格信息
//        Map<Integer, List<Record5<Integer, Integer, Timestamp, Timestamp, BigDecimal>>> groupsInfos = db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_DEFINE.START_TIME, GROUP_BUY_DEFINE.END_TIME, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
//            .from(GROUP_BUY_DEFINE).innerJoin(GROUP_BUY_PRODUCT_DEFINE).on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
//            .where(GROUP_BUY_DEFINE.START_TIME.lt(date)).and(GROUP_BUY_DEFINE.END_TIME.gt(date)).and(GROUP_BUY_DEFINE.STOCK.gt((short) 0))
//            .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS)).and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds))
//            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GROUP_BUY_DEFINE.GOODS_ID)));
//
//        if (groupsInfos == null) {
//            return null;
//        }
//        Set<Integer> validGoodsIds = groupsInfos.keySet();
//
//        // 获取有效商品规格信息
//        Map<Integer, List<Record2<Integer, BigDecimal>>> prdsInfos = db().select(GOODS_SPEC_PRODUCT.GOODS_ID, GOODS_SPEC_PRODUCT.PRD_PRICE).from(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.GOODS_ID.in(validGoodsIds))
//            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GOODS_SPEC_PRODUCT.GOODS_ID)));
//
//        Map<Integer, GroupBuyActivityVo> returnMap = new HashMap<>(validGoodsIds.size());
//
//        validGoodsIds.forEach(goodsId->{
//            List<Record2<Integer, BigDecimal>> prdsInfo = prdsInfos.get(goodsId);
//            //可能存在拼团商品存在但是该商品已经不存在（商品列表里调用此方法应该不存在这种情况，防止其他地方直接调用）
//            if (prdsInfo == null||prdsInfo.size()==0) {
//                outPutLog(goodsId);
//                return;
//            }
//            List<Record5<Integer, Integer, Timestamp, Timestamp, BigDecimal>> groupsInfo = groupsInfos.get(goodsId);
//            // 从小到大正序排序
//            prdsInfo.sort(Comparator.comparing(x -> x.get(GOODS_SPEC_PRODUCT.PRD_PRICE)));
//            groupsInfo.sort(Comparator.comparing(x -> x.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)));
//            // 获取规格中的最大值
//            Record2<Integer, BigDecimal> prd = prdsInfo.get(prdsInfo.size()-1);
//            // 获取拼团规格中的最小值
//            Record5<Integer, Integer, Timestamp, Timestamp, BigDecimal> group = groupsInfo.get(0);
//
//            GroupBuyActivityVo vo =new GroupBuyActivityVo();
//            vo.setActivityId(group.get(GROUP_BUY_DEFINE.ID));
//            vo.setOriginalPrice(prd.get(GOODS_SPEC_PRODUCT.PRD_PRICE));
//            vo.setActivityPrice(group.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE));
//            vo.setStartTime(group.get(GROUP_BUY_DEFINE.START_TIME));
//            vo.setEndTime(group.get(GROUP_BUY_DEFINE.END_TIME));
//
//            returnMap.put(goodsId,vo);
//        });
//
//        return returnMap;
//    }

    private void outPutLog( Integer goodsId) {
        logger().error("拼团相关联的{}号商品不存在",goodsId);
    }

    /**
     *  活动实付金额
     * @param discountMoneyList
     * @param timestamp
     * @return
     */
    private ActiveDiscountMoney getDiscountMoneyByDate(List<ActiveDiscountMoney> discountMoneyList, Timestamp timestamp) {
        for (ActiveDiscountMoney data : discountMoneyList) {
            if (data!=null&&timestamp.equals(data.getCreateTime())) {
                return data;
            }
        }
        return null;
    }

    /**
     * 活动用户数量
     * @param list
     * @param timestamp
     * @return
     */
    private OrderActivityUserNum getUserNum(List<OrderActivityUserNum> list, Timestamp timestamp) {
        for (OrderActivityUserNum activityUserNum : list) {
            if (activityUserNum!=null&& timestamp.equals(activityUserNum.getDate())) {
                return activityUserNum;
            }
        }
        return null;
    }

    /**
     * 活动详情
     *
     * @param userId
     * @param createTime
     * @param groupId
     * @param activityId
     * @param lang
     * @return
     */
    public GroupBuyInfoVo getGroupBuyInfo(Integer userId, Timestamp createTime, Integer groupId, Integer activityId, String lang){
        Timestamp date =DateUtil.getLocalDateTime();
        // 拼团状态
        ResultMessage resultMessage = groupBuyListService.canCreatePinGroupOrder(userId, date,activityId, groupId, IS_GROUPER_N);
        //拼团活动
        GroupBuyDefineRecord groupBuy = getGroupBuyRecord(activityId);
        Result<Record2<BigDecimal, Short>> groupBuyProductRecord = db().select(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE,GROUP_BUY_PRODUCT_DEFINE.STOCK).from(GROUP_BUY_PRODUCT_DEFINE).where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(activityId)).fetch();
        //商品
        GoodsRecord goodsRecord = goodsService.getGoodsById(groupBuy.getGoodsId()).get();
        //规格
        List<GroupBuyProductInfo> product =  goodsService.getProductByGoodsId(groupBuy.getGoodsId()).into(GroupBuyProductInfo.class);
        //sku
        List<GoodsSpecProductRecord> prdInfos = goodsSpecProductService.getGoodsDetailPrds(groupBuy.getGoodsId());
        List<GoodsPrdMpVo> prdMpVos = prdInfos.stream().map(GoodsPrdMpVo::new).collect(Collectors.toList());
        //用户
        List<GroupBuyUserInfo> userList = groupBuyListService.getPinUserList(groupId);
        boolean newUser = orderInfoService.isNewUser(userId);
        // $shop->shop_cfg->getShopCfg('bind_mobile');
        Byte bindMobile = shopCommonConfigService.getBindMobile();
        Integer groupBuyStock = groupBuyProductRecord.stream().mapToInt(Record2<BigDecimal, Short>::component2).sum();
        BigDecimal maxPrice =groupBuyProductRecord.stream().map(Record2<BigDecimal, Short>::component1).distinct().max(BigDecimal::compareTo).get();
        BigDecimal minPrice =groupBuyProductRecord.stream().map(Record2<BigDecimal, Short>::component1).distinct().min(BigDecimal::compareTo).get();
        long dateDiff =date.getTime()-	createTime.getTime();
        long hour = 24- (dateDiff / (60 * 60 * 1000));
        long min = 60- dateDiff %  (60 * 60 * 1000) / (60 * 1000);
        long s = 60- dateDiff% (60 * 60 * 1000)%(60*1000) / 1000 ;

        GroupBuyInfoVo groupBuyInfo =new GroupBuyInfoVo();
        GroupBuyGoodsInfo goods =new GroupBuyGoodsInfo();
        goods.setGoodsId(goodsRecord.getGoodsId());
        goods.setGoodsImg(goodsRecord.getGoodsImg());
        goods.setGoodsName(goodsRecord.getGoodsName());
        goods.setGoodsNumber(goodsRecord.getGoodsNumber());
        goods.setGroupBuygoodsNum(groupBuyStock);
        goods.setMaxGroupBuyPrice(maxPrice);
        goods.setMinGroupBuyPrice(minPrice);
        GroupBuyStatusInfo statusInfo =new GroupBuyStatusInfo();
        statusInfo.setStatus(resultMessage.getJsonResultCode().getCode());
        statusInfo.setMessage(Util.translateMessage(lang,resultMessage.getJsonResultCode().getMessage(),"messages"));
        groupBuyInfo.setGoodsInfo(goods);
        groupBuyInfo.setProductList(product);
        groupBuyInfo.setPrdSpecsList(prdMpVos);
        groupBuyInfo.setStatusInfo(statusInfo);
        groupBuyInfo.setUserInfoList(userList);
        groupBuyInfo.setHour(Math.toIntExact(hour));
        groupBuyInfo.setMinute(Math.toIntExact(min));
        groupBuyInfo.setSecond(Math.toIntExact(s));
        groupBuyInfo.setBindMobile(bindMobile);
        groupBuyInfo.setNewUser(newUser);
        return groupBuyInfo;
    }



}
