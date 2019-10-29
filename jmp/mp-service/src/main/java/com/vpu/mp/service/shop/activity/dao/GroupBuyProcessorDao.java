package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.wxapp.activity.info.groupbuy.ActivityGroupBuyListInfo;
import org.jooq.Record2;
import org.jooq.Record3;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.*;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
public class GroupBuyProcessorDao extends ShopBaseService {

    private static final byte USE_STATUS = 1;

    /**
     * 获取集合内商品所参与的拼团信息
     * @param goodsIds 待查询商品id集合
     * @param date 限制时间
     * @return key:商品id value:{@link com.vpu.mp.service.pojo.wxapp.goods.goods.activity.GroupBuyActivityVo}
     * @author 李晓冰
     */
    public Map<Integer, ActivityGroupBuyListInfo> getGoodsGroupByListInfo(List<Integer> goodsIds, Timestamp date) {
        // 获取有效拼团规格信息
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> groupsInfos = db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_DEFINE).innerJoin(GROUP_BUY_PRODUCT_DEFINE).on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.START_TIME.lt(date)).and(GROUP_BUY_DEFINE.END_TIME.gt(date)).and(GROUP_BUY_DEFINE.STOCK.gt((short) 0))
            .and(GROUP_BUY_DEFINE.STATUS.eq(USE_STATUS)).and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds))
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GROUP_BUY_DEFINE.GOODS_ID)));

        if (groupsInfos == null) {
            return null;
        }
        Set<Integer> validGoodsIds = groupsInfos.keySet();

        // 获取有效商品规格信息
        Map<Integer, List<Record2<Integer, BigDecimal>>> prdsInfos = db().select(GOODS_SPEC_PRODUCT.GOODS_ID, GOODS_SPEC_PRODUCT.PRD_PRICE).from(GOODS_SPEC_PRODUCT).where(GOODS_SPEC_PRODUCT.GOODS_ID.in(validGoodsIds))
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GOODS_SPEC_PRODUCT.GOODS_ID)));

        Map<Integer, ActivityGroupBuyListInfo> returnMap = new HashMap<>(validGoodsIds.size());

        validGoodsIds.forEach(goodsId->{
            List<Record2<Integer, BigDecimal>> prdsInfo = prdsInfos.get(goodsId);
            //可能存在拼团商品存在但是该商品已经不存在（商品列表里调用此方法应该不存在这种情况，防止其他地方直接调用）
            if (prdsInfo == null||prdsInfo.size()==0) {
                outPutLog(goodsId);
                return;
            }
            List<Record3<Integer, Integer, BigDecimal>> groupsInfo = groupsInfos.get(goodsId);
            // 从小到大正序排序
            prdsInfo.sort(Comparator.comparing(x -> x.get(GOODS_SPEC_PRODUCT.PRD_PRICE)));
            groupsInfo.sort(Comparator.comparing(x -> x.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)));
            // 获取规格中的最大值
            Record2<Integer, BigDecimal> prd = prdsInfo.get(prdsInfo.size()-1);
            // 获取拼团规格中的最小值
            Record3<Integer, Integer, BigDecimal> group = groupsInfo.get(0);

            ActivityGroupBuyListInfo vo =new ActivityGroupBuyListInfo();
            vo.setActivityId(group.get(GROUP_BUY_DEFINE.ID));
            vo.setOriginalMaxPrice(prd.get(GOODS_SPEC_PRODUCT.PRD_PRICE));
            vo.setActivityPrice(group.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE));
            returnMap.put(goodsId,vo);
        });
        return returnMap;
    }

    private void outPutLog( Integer goodsId) {
        logger().error("拼团相关联的{}号商品不存在",goodsId);
    }
}
