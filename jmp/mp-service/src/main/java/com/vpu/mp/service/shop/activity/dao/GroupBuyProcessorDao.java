package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.GroupBuyDefineRecord;
import com.vpu.mp.db.shop.tables.records.GroupBuyListRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.groupbuy.GroupBuyConstant;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.groupbuy.GroupBuyListMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.groupbuy.GroupBuyMpVo;
import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.groupbuy.GroupBuyPrdMpVo;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.market.goupbuy.GroupBuyService;
import org.jooq.Record3;
import org.jooq.Record5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GroupBuyProcessorDao extends GroupBuyService {

    @Autowired
    private ImageService imageService;

    /**
     * 获取集合内商品所参与的拼团信息
     *
     * @param goodsIds 待查询商品id集合
     * @param date     限制时间
     * @return key:商品id value:List<Record3<Integer, Integer, BigDecimal>> GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE
     * @author 李晓冰
     */
    public Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> getGoodsGroupBuyListInfo(List<Integer> goodsIds, Timestamp date) {
        // 获取有效拼团规格信息
        return db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_DEFINE).innerJoin(GROUP_BUY_PRODUCT_DEFINE).on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.START_TIME.lt(date)).and(GROUP_BUY_DEFINE.END_TIME.gt(date)).and(GROUP_BUY_DEFINE.STOCK.gt((short) 0))
            .and(GROUP_BUY_DEFINE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL)).and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds))
            .orderBy(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GROUP_BUY_DEFINE.GOODS_ID)));
    }

    /**
     * 商品详情-获取拼团信息
     *
     * @param userId     用户id
     * @param activityId 拼团活动id
     * @return 拼团活动信息
     */
    public GroupBuyMpVo getGroupBuyInfo(Integer userId, Integer activityId) {
        GroupBuyMpVo vo = new GroupBuyMpVo();
        vo.setActivityId(activityId);
        vo.setActivityType(BaseConstant.ACTIVITY_TYPE_GROUP_BUY);

        GroupBuyDefineRecord groupBuyDefineRecord = getGroupBuyDefinedInfoBuyId(activityId);
        Timestamp now = DateUtil.getLocalDateTime();

        Byte aByte = canCreatePinGroupOrder(userId, now, activityId, groupBuyDefineRecord);
        vo.setActState(aByte);

        // 活动不存在
        if (BaseConstant.ACTIVITY_STATUS_NOT_HAS.equals(aByte)) {
            return vo;
        }

        // 活动未开始
        if (BaseConstant.ACTIVITY_STATUS_NOT_START.equals(aByte)) {
            vo.setStartTime((groupBuyDefineRecord.getStartTime().getTime() - now.getTime())/1000);
        }
        vo.setEndTime((groupBuyDefineRecord.getEndTime().getTime() - now.getTime())/1000);

        /**是否团长优惠*/
        vo.setIsGrouperCheap(groupBuyDefineRecord.getIsGrouperCheap());
        /**参团人数*/
        vo.setLimitAmount(groupBuyDefineRecord.getLimitAmount());

        /** 商品拼团最小最大购买数量 */
        vo.setLimitBuyNum(groupBuyDefineRecord.getLimitBuyNum());
        vo.setLimitMaxNum(groupBuyDefineRecord.getLimitMaxNum());

        /** 拼团表中 shippingType 活动运费 1 免运费 2 按照商品原运费模板*/
        vo.setFreeShip((byte) (groupBuyDefineRecord.getShippingType() == 1 ? 0 : 1));

        /**已成功拼团数量*/
        logger().debug("小程序-商品详情-拼团信息-已成团数量");
        vo.setGroupBuySuccessCount(getGroupBuySuccessCount(activityId));

        /** 正在进行中拼团信息列表 */
        logger().debug("小程序-商品详情-拼团信息-正在拼团列表信息");
        vo.setGroupBuyListMpVos(getGroupBuyListInfo(activityId, groupBuyDefineRecord.getLimitAmount(), now));

        return vo;
    }


    /**
     * 商品详情-获取拼团规格信息
     *
     * @param activityId 拼团活动id
     * @return {@link GroupBuyPrdMpVo} 拼团规格信息
     */
    public List<GroupBuyPrdMpVo> getGroupBuyPrdInfo(Integer activityId) {
        return db().select(GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID, GROUP_BUY_PRODUCT_DEFINE.STOCK, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE, GROUP_BUY_PRODUCT_DEFINE.GROUPER_PRICE)
            .from(GROUP_BUY_PRODUCT_DEFINE)
            .where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(activityId))
            .fetchInto(GroupBuyPrdMpVo.class);
    }

    /**
     * 获取拼团成功数量
     *
     * @param activityId 拼团活动id
     * @return 已成团数量
     */
    private Integer getGroupBuySuccessCount(Integer activityId) {
        return db().fetchCount(GROUP_BUY_LIST, GROUP_BUY_LIST.ACTIVITY_ID.eq(activityId).and(GROUP_BUY_LIST.STATUS.eq(GroupBuyConstant.STATUS_SUCCESS)).and(GROUP_BUY_LIST.IS_GROUPER.eq(GroupBuyConstant.IS_GROUPER_Y)));
    }

    /**
     * 获取正在进行拼团-列表信息信息
     *
     * @param activityId  活动id
     * @param limitAmount 活动成团人数
     * @param now         当前时间
     * @return {@link GroupBuyListMpVo} 列表信息
     */
    private List<GroupBuyListMpVo> getGroupBuyListInfo(Integer activityId, Short limitAmount, Timestamp now) {
        Map<Integer, List<Record5<Integer, Byte, Timestamp, String, String>>> groups =
            db().select(GROUP_BUY_LIST.GROUP_ID, GROUP_BUY_LIST.IS_GROUPER, GROUP_BUY_LIST.START_TIME, USER_DETAIL.USERNAME, USER_DETAIL.USER_AVATAR)
                .from(GROUP_BUY_LIST).innerJoin(USER_DETAIL).on(GROUP_BUY_LIST.USER_ID.eq(USER_DETAIL.USER_ID))
                .where(GROUP_BUY_LIST.ACTIVITY_ID.eq(activityId).and(GROUP_BUY_LIST.STATUS.eq(GroupBuyConstant.STATUS_ONGOING)))
                .orderBy(GROUP_BUY_LIST.START_TIME.desc(), GROUP_BUY_LIST.IS_GROUPER.desc())
                .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GROUP_BUY_LIST.GROUP_ID)));

        List<GroupBuyListMpVo> groupBuyListMpVos = new ArrayList<>(groups.size());

        groups.forEach((key, values) -> {
            GroupBuyListMpVo vo = new GroupBuyListMpVo();
            Record5<Integer, Byte, Timestamp, String, String> record5 = values.get(0);
            vo.setGroupId(record5.get(GROUP_BUY_LIST.GROUP_ID));
            vo.setUserName(record5.get(USER_DETAIL.USERNAME));
            vo.setUserAvatar(imageService.getImgFullUrl(record5.get(USER_DETAIL.USER_AVATAR)));
            vo.setRemainNum(limitAmount - values.size());
            long passedTime = (now.getTime() - record5.get(GROUP_BUY_LIST.START_TIME).getTime())/1000;
            vo.setRemainTime(GoodsConstant.GROUP_BUY_LIMIT_TIME - passedTime);
            groupBuyListMpVos.add(vo);
        });
        return groupBuyListMpVos;
    }

    /**
     * 保存
     * @param groupBuyProductList
     * @return
     */
    public int save(GroupBuyListRecord groupBuyProductList) {
        return db().executeInsert(groupBuyProductList);
    }

    /**
     * 修改拼团库存和销量
     *
     * @param activityId
     * @param productId
     * @param goodsNumber 商品数量
     * @return
     */
    public boolean updateGroupBuyStock(Integer activityId, Integer productId, Integer goodsNumber) {
        //规格库存`
        int prdFlag = db().update(GROUP_BUY_PRODUCT_DEFINE)
            .set(GROUP_BUY_PRODUCT_DEFINE.STOCK, GROUP_BUY_PRODUCT_DEFINE.STOCK.minus(goodsNumber))
            .set(GROUP_BUY_PRODUCT_DEFINE.SALE_NUM, GROUP_BUY_PRODUCT_DEFINE.SALE_NUM.add(goodsNumber))
            .where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(activityId))
            .and(GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID.eq(productId))
            .and(GROUP_BUY_PRODUCT_DEFINE.STOCK.ge(goodsNumber.shortValue())).execute();
        if (prdFlag == 1) {
            //总库存
            int tolFlag = db().update(GROUP_BUY_DEFINE)
                .set(GROUP_BUY_DEFINE.STOCK, GROUP_BUY_DEFINE.STOCK.minus(goodsNumber))
                .set(GROUP_BUY_DEFINE.SALE_NUM, GROUP_BUY_DEFINE.SALE_NUM.add(goodsNumber))
                .where(GROUP_BUY_DEFINE.ID.eq(activityId))
                .and(GROUP_BUY_DEFINE.STOCK.ge(goodsNumber.shortValue())).execute();
            if (tolFlag == 1) {
                return true;
            } else {
                db().update(GROUP_BUY_PRODUCT_DEFINE)
                    .set(GROUP_BUY_PRODUCT_DEFINE.STOCK, GROUP_BUY_PRODUCT_DEFINE.STOCK.add(goodsNumber))
                    .set(GROUP_BUY_PRODUCT_DEFINE.SALE_NUM, GROUP_BUY_PRODUCT_DEFINE.SALE_NUM.minus(goodsNumber))
                    .where(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID.eq(activityId))
                    .and(GROUP_BUY_PRODUCT_DEFINE.PRODUCT_ID.eq(productId))
                    .and(GROUP_BUY_PRODUCT_DEFINE.STOCK.ge(goodsNumber.shortValue())).execute();
            }
        }
        return false;
    }
}
