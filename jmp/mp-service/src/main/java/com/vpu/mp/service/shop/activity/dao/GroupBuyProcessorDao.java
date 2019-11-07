package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.GroupBuyProcessorDataInfo;
import org.jooq.Record3;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GROUP_BUY_DEFINE;
import static com.vpu.mp.db.shop.Tables.GROUP_BUY_PRODUCT_DEFINE;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Service
public class GroupBuyProcessorDao extends ShopBaseService {

    /**
     * 获取集合内商品所参与的拼团信息
     * @param goodsIds 待查询商品id集合
     * @param date 限制时间
     * @return key:商品id value:{@link com.vpu.mp.service.pojo.wxapp.activity.info.GroupBuyProcessorDataInfo}
     * @author 李晓冰
     */
    public Map<Integer, GroupBuyProcessorDataInfo> getGoodsGroupBuyListInfo(List<Integer> goodsIds, Timestamp date) {
        // 获取有效拼团规格信息
        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> groupsInfos = db().select(GROUP_BUY_DEFINE.ID, GROUP_BUY_DEFINE.GOODS_ID, GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)
            .from(GROUP_BUY_DEFINE).innerJoin(GROUP_BUY_PRODUCT_DEFINE).on(GROUP_BUY_DEFINE.ID.eq(GROUP_BUY_PRODUCT_DEFINE.ACTIVITY_ID))
            .where(GROUP_BUY_DEFINE.START_TIME.lt(date)).and(GROUP_BUY_DEFINE.END_TIME.gt(date)).and(GROUP_BUY_DEFINE.STOCK.gt((short) 0))
            .and(GROUP_BUY_DEFINE.STATUS.eq(GoodsConstant.USE_STATUS)).and(GROUP_BUY_DEFINE.DEL_FLAG.eq(DelFlag.NORMAL.getCode())).and(GROUP_BUY_DEFINE.GOODS_ID.in(goodsIds))
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GROUP_BUY_DEFINE.GOODS_ID)));


        Set<Integer> validGoodsIds = groupsInfos.keySet();


        Map<Integer, GroupBuyProcessorDataInfo> returnMap = new HashMap<>(validGoodsIds.size());

        validGoodsIds.forEach(goodsId->{
            List<Record3<Integer, Integer, BigDecimal>> groupsInfo = groupsInfos.get(goodsId);

            groupsInfo.sort(Comparator.comparing(x -> x.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE)));
            // 获取拼团规格中的最小值
            Record3<Integer, Integer, BigDecimal> group = groupsInfo.get(0);

            GroupBuyProcessorDataInfo vo =new GroupBuyProcessorDataInfo();
            vo.setDataId(group.get(GROUP_BUY_DEFINE.ID));
            vo.setDataPrice(group.get(GROUP_BUY_PRODUCT_DEFINE.GROUP_PRICE));
            returnMap.put(goodsId,vo);
        });
        return returnMap;
    }

    private void outPutLog( Integer goodsId) {
        logger().error("拼团相关联的{}号商品不存在",goodsId);
    }
}
