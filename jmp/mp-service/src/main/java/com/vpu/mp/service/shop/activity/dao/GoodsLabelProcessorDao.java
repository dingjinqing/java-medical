package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.GoodsLabelForListInfo;
import org.jooq.Condition;
import org.jooq.Record4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
public class GoodsLabelProcessorDao extends ShopBaseService {

    /**
     * 根据商品id，平台分类，商家分类数据获取关系最紧密的标签映射表。
     * 先从数据库获取标签信息对应表
     * 映射表分成两级，第一级的key是商品id，平台分类，商家分类或全部商品的码值，value是另一个映射表（二级表）
     * 二级表的key是对应的gtaid值，value是该值对应的标签信息List集合，按照level,createTime 进行排序
     * 栗子：{
     *    // 平台分类:{id为3的平台分类项:[标签1，标签2]}
     *     2:{3:[label1Info,label2Info]}
     * }
     * @param goodsIds 商品id集合
     * @param catIds 平台分类集合
     * @param sortIds 商家分类集合
     * @return Map {key:标签type码值，value:innerMap},innerMap {key:id值，value:[GoodsLabelForListInfo]}
     */
    public Map<Byte,Map<Integer, GoodsLabelForListInfo>> getGoodsClosestLabelsInfo(List<Integer> goodsIds,List<Integer> catIds,List<Integer> sortIds){

        Condition goodsIdsCondition =GOODS_LABEL_COUPLE.GTA_ID.in(goodsIds).and(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()));
        Condition catIdsCondition = GOODS_LABEL_COUPLE.GTA_ID.in(catIds).and(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.CATTYPE.getCode()));
        Condition sortIdsCondition = GOODS_LABEL_COUPLE.GTA_ID.in(sortIds).and(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode()));
        Condition allGoodsCondition =  GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode());

        Map<Byte, Map<Integer, List<Record4<String, Short, Byte, Integer>>>> goodsLabelsMap = db().select(GOODS_LABEL.NAME, GOODS_LABEL.LIST_PATTERN, GOODS_LABEL_COUPLE.TYPE, GOODS_LABEL_COUPLE.GTA_ID).from(GOODS_LABEL).innerJoin(GOODS_LABEL_COUPLE).on(GOODS_LABEL.ID.eq(GOODS_LABEL_COUPLE.LABEL_ID))
            .where(GOODS_LABEL.GOODS_LIST.eq(GoodsConstant.GOODS_LIST)).and(GOODS_LABEL.DEL_FLAG.eq(0))
            .and(goodsIdsCondition.or(catIdsCondition).or(sortIdsCondition).or(allGoodsCondition))
            .orderBy(GOODS_LABEL.LEVEL.asc(), GOODS_LABEL.CREATE_TIME)
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(GOODS_LABEL_COUPLE.TYPE), Collectors.groupingBy(x -> x.get(GOODS_LABEL_COUPLE.GTA_ID))));

        Map<Byte,Map<Integer, GoodsLabelForListInfo>> returnMap = new HashMap<>();

        goodsLabelsMap.forEach((key,value)->{
            Map<Integer,GoodsLabelForListInfo> innerMap = new HashMap<>();
            returnMap.put(key,innerMap);
            value.forEach((innerKey,innerValue)->{
                GoodsLabelForListInfo labelInfo = new GoodsLabelForListInfo();
                Record4<String, Short, Byte, Integer> record4 = innerValue.get(0);
                labelInfo.setListPattern(record4.get(GOODS_LABEL.LIST_PATTERN));
                labelInfo.setName(record4.get(GOODS_LABEL.NAME));
                innerMap.put(innerKey,labelInfo);
            });
        });

        returnMap.putIfAbsent(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode(),new HashMap<>());
        returnMap.putIfAbsent(GoodsLabelCoupleTypeEnum.CATTYPE.getCode(),new HashMap<>());
        returnMap.putIfAbsent(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode(),new HashMap<>());
        returnMap.putIfAbsent(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode(),new HashMap<>());

        return returnMap;
    }
}
