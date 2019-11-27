package com.vpu.mp.service.shop.goods.es.goods.label;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.vpu.mp.db.shop.tables.records.GoodsLabelRecord;
import com.vpu.mp.service.foundation.es.EsManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchName;
import com.vpu.mp.service.pojo.shop.goods.es.EsSearchParam;
import com.vpu.mp.service.pojo.shop.goods.es.FieldProperty;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCouple;
import com.vpu.mp.service.pojo.shop.goods.label.GoodsLabelCoupleTypeEnum;
import com.vpu.mp.service.shop.goods.GoodsLabelCoupleService;
import com.vpu.mp.service.shop.goods.GoodsLabelService;
import com.vpu.mp.service.shop.goods.es.EsUtilSearchService;
import com.vpu.mp.service.shop.goods.es.goods.EsGoodsConstant;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.Tables.GOODS_LABEL;
import static com.vpu.mp.db.shop.Tables.GOODS_LABEL_COUPLE;

/**
 * 商品标签建立索引
 * @author 卢光耀
 * @date 2019/11/22 2:42 下午
 *
*/
@Service
@Slf4j
public class EsGoodsLabelCreateService extends ShopBaseService {

    @Autowired private EsUtilSearchService utilSearchService;

    @Autowired private GoodsLabelCoupleService goodsLabelCoupleService;

    @Autowired private GoodsLabelService goodsLabelService;

    @Autowired private EsManager esManager;


    /**
     * 根据商品ID集合建立索引
     * @param goodsIds 商品ID集合
     */
    @SuppressWarnings("unchecked")
    public void createEsLabelIndexForGoodsId(List<Integer> goodsIds){
        Set<Integer> sortIds = Sets.newHashSet();
        Set<Integer> categoryIds = Sets.newHashSet();
        List<Map<String,Object>> goodsInfoList = getGoodsInfoByParam(goodsIds,null,null);
        for( Map<String,Object> infoMap: goodsInfoList ){
            List<Integer> s = (ArrayList<Integer>)infoMap.get(EsSearchName.FULL_SORT_ID);
            if( s != null && !s.isEmpty() ){
                sortIds.addAll(s);
            }
            List<Integer> c = (ArrayList<Integer>)infoMap.get(EsSearchName.FULL_CAT_ID);
            if( c != null &&!c.isEmpty() ){
                categoryIds.addAll(c);
            }
        }
        Condition condition = GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.CATTYPE.getCode())
            .and(GOODS_LABEL_COUPLE.GTA_ID.in(categoryIds));
        condition = condition.or(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode())
            .and(GOODS_LABEL_COUPLE.GTA_ID.in(sortIds)));
        condition = condition.or(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode())
            .and(GOODS_LABEL_COUPLE.GTA_ID.in(goodsIds)));
        condition = condition.or(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode()));
        List<GoodsLabelCouple> goodsLabelCouples = goodsLabelCoupleService.getGoodsLabelCouple(condition);
        GoodsLabelTypeInfo labelTypeInfo = GoodsLabelTypeInfo.convert(goodsLabelCouples);
        Map<Integer, GoodsLabelRecord> labelInfoMap = goodsLabelService
            .getByCondition(GOODS_LABEL.ID.in(labelTypeInfo.getAllLabelId()))
            .stream()
            .collect(Collectors.toMap(GoodsLabelRecord::getId, Function.identity()));
        List<EsGoodsLabel> esGoodsLabelList =assemblyEsGoodsLabels(labelInfoMap,labelTypeInfo,goodsInfoList);
        batchCommitEsGoodsIndex(esGoodsLabelList);
    }
    /**
     * 根据商品ID建立索引
     * @param goodsId 商品ID
     */
    @SuppressWarnings("unchecked")
    private void createEsLabelIndexForGoodsId(Integer goodsId){
        Set<Integer> sortIds = Sets.newHashSet();
        Set<Integer> categoryIds = Sets.newHashSet();
        List<Map<String,Object>> goodsInfoList = getGoodsInfoByParam(Collections.singletonList(goodsId),null,null);
        for( Map<String,Object> infoMap: goodsInfoList ){
            sortIds.addAll((ArrayList<Integer>)infoMap.get(EsSearchName.FULL_SORT_ID));
            categoryIds.addAll((ArrayList<Integer>)infoMap.get(EsSearchName.FULL_CAT_ID));
        }
        Condition condition = GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.CATTYPE.getCode())
            .and(GOODS_LABEL_COUPLE.GTA_ID.in(categoryIds));
        condition.or(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode())
            .and(GOODS_LABEL_COUPLE.GTA_ID.in(sortIds)));
        condition.or(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode())
            .and(GOODS_LABEL_COUPLE.GTA_ID.eq(goodsId)));
        condition.or(GOODS_LABEL_COUPLE.TYPE.eq(GoodsLabelCoupleTypeEnum.ALLTYPE.getCode()));
        List<GoodsLabelCouple> goodsLabelCouples = goodsLabelCoupleService.getGoodsLabelCouple(condition);
        GoodsLabelTypeInfo labelTypeInfo = GoodsLabelTypeInfo.convert(goodsLabelCouples);
        Map<Integer, GoodsLabelRecord> labelInfoMap = goodsLabelService
            .getByCondition(GOODS_LABEL.ID.in(labelTypeInfo.getAllLabelId()))
            .stream()
            .collect(Collectors.toMap(GoodsLabelRecord::getId, Function.identity()));
        List<EsGoodsLabel> esGoodsLabelList =assemblyEsGoodsLabels(labelInfoMap,labelTypeInfo,goodsInfoList);
        batchCommitEsGoodsIndex(esGoodsLabelList);
    }
    private void createEsLabelIndexForLabelId(List<Integer> labelIds){
        //TODO 暂时没有发现商品标签批量修改的功能
    }

    /**
     * 根据商品标签ID建立索引
     * @param labelId 商品标签ID
     */
    private void createEsLabelIndexForLabelId(Integer labelId){
        Set<Integer> sortIds = Sets.newHashSet();
        Set<Integer> goodsIds = Sets.newHashSet();
        Set<Integer> categoryIds = Sets.newHashSet();
        Map<Integer, GoodsLabelRecord> labelInfoMap = goodsLabelService
            .getByCondition(GOODS_LABEL.ID.eq(labelId))
            .stream()
            .collect(Collectors.toMap(GoodsLabelRecord::getId, Function.identity()));
        Condition condition = GOODS_LABEL_COUPLE.LABEL_ID.eq(labelId);
        List<GoodsLabelCouple> goodsLabelCouples = goodsLabelCoupleService.getGoodsLabelCouple(condition);
        for( GoodsLabelCouple goodsLabelCouple:goodsLabelCouples ){
            Integer id = goodsLabelCouple.getGtaId();
            //如果是对全部商品生效
            if( goodsLabelCouple.getType().equals(GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode()) ){
                goodsIds.add(id);
            }else if( goodsLabelCouple.getType().equals(GoodsLabelCoupleTypeEnum.SORTTYPE.getCode()) ){
                sortIds.add(id);
            }else if( goodsLabelCouple.getType().equals(GoodsLabelCoupleTypeEnum.CATTYPE.getCode()) ){
                categoryIds.add(id);
            }
        }
        List<Map<String,Object>> goodsInfoList = getGoodsInfoByParam(
            new ArrayList<>(goodsIds),
            new ArrayList<>(sortIds),
            new ArrayList<>(categoryIds)
        );
        GoodsLabelTypeInfo labelTypeInfo = GoodsLabelTypeInfo.convert(goodsLabelCouples);
        List<EsGoodsLabel> esGoodsLabelList =assemblyEsGoodsLabels(labelInfoMap,labelTypeInfo,goodsInfoList);
        batchCommitEsGoodsIndex(esGoodsLabelList);
    }

    /**
     * 转换生成EsGoodsLabel
     * @param info 商品标签信息
     * @param shopId 店铺ID
     * @param goodsId 商品ID
     * @param type 商品标签类型
     * @param typeId 商品标签类型对应存储的ID(1:商品ID，2:商家分类ID，3:平台分类ID，4:null;)
     * @return {EsGoodsLabel}
     */
    private EsGoodsLabel createEsGoodsLabel(GoodsLabelRecord info,Integer shopId,Integer goodsId,Byte type,Integer typeId){
        EsGoodsLabel label = new EsGoodsLabel();
        label.setId(info.getId());
        label.setShopId(shopId);
        label.setCreateTime(DateUtil.dateFormat(DateUtil.DATE_FORMAT_FULL,info.getCreateTime()));
        label.setGoodsId(goodsId);
        label.setType(type);
        label.setDetailShow(info.getGoodsDetail() == 1);
        label.setSearchShow(info.getGoodsSelect() == 1);
        label.setListShow(info.getGoodsList() == 1);
        label.setName(info.getName());
        label.setLevel(info.getLevel());
        label.setListPattern(info.getListPattern());
        if( null != typeId ){
            label.setTypeId(typeId);
        }
        return label;
    }
    /**
     * 封装EsGoodsLabel
     * @param labelTypeInfo {@link GoodsLabelTypeInfo}
     * @param goodsInfoList {List<Map<String,Object>>} goodsIds以及其对应的商家分类和平台分类
     * @return  {EsGoodsLabel}
     */
    @SuppressWarnings("unchecked")
    public List<EsGoodsLabel> assemblyEsGoodsLabels(Map<Integer, GoodsLabelRecord> labelInfoMap,
                                                    GoodsLabelTypeInfo labelTypeInfo,List<Map<String,Object>> goodsInfoList){
        Integer shopId = getShopId();
        List<EsGoodsLabel> esGoodsLabelList = Lists.newArrayList();

        for(Map<String,Object> goodsMap : goodsInfoList){
            Integer goodsId = Integer.valueOf(goodsMap.get(EsSearchName.GOODS_ID).toString());
            if ( labelTypeInfo.getIdGoods().containsKey(goodsId) ){
                List<Integer> labelIds = labelTypeInfo.getIdGoods().get(goodsId);
                labelIds.forEach(
                    x->
                        esGoodsLabelList.add(
                            createEsGoodsLabel(labelInfoMap.get(x),shopId,goodsId,GoodsLabelCoupleTypeEnum.GOODSTYPE.getCode(),goodsId)
                        )
                );
            }
            ArrayList<Integer> goodsSortIds = (ArrayList<Integer>)goodsMap.get(EsSearchName.FULL_SORT_ID);
            if( goodsSortIds != null && !goodsSortIds.isEmpty() ){
                goodsSortIds.forEach(x->{
                    if ( labelTypeInfo.getSortGoods().containsKey(x) ){
                        List<Integer> labelIds = labelTypeInfo.getIdGoods().get(goodsId);
                        labelIds.forEach(
                            y->
                                esGoodsLabelList.add(
                                    createEsGoodsLabel(labelInfoMap.get(x),shopId,goodsId,GoodsLabelCoupleTypeEnum.SORTTYPE.getCode(),y)
                                )
                        );
                    }
                });
            }

            ArrayList<Integer> goodsCategoryIds = (ArrayList<Integer>)goodsMap.get(EsSearchName.FULL_CAT_ID);
            if( goodsCategoryIds != null && !goodsCategoryIds.isEmpty() ){
                goodsCategoryIds.forEach(x->{
                    if ( labelTypeInfo.getCategoryGoods().containsKey(x) ){
                        List<Integer> labelIds = labelTypeInfo.getIdGoods().get(goodsId);
                        labelIds.forEach(
                            y->
                                esGoodsLabelList.add(
                                    createEsGoodsLabel(labelInfoMap.get(x),shopId,goodsId,GoodsLabelCoupleTypeEnum.CATTYPE.getCode(),y)
                                )
                        );
                    }
                });
            }
            labelTypeInfo.getAllGoods().forEach(x->
                esGoodsLabelList.add(
                    createEsGoodsLabel(labelInfoMap.get(x),shopId,goodsId,GoodsLabelCoupleTypeEnum.ALLTYPE.getCode(),null)
                )
            );
        }
        return esGoodsLabelList;
    }
    /**
     * 根据商品标签的商品ID/平台分类/商家分类推出受影响的商品以及他的所有商家和平台分类
     * @param sortIds 商家分类
     * @param categoryIds 平台分类
     * @return {List<Map<String,Object>>} goodsIds以及其对应的商家分类和平台分类
     */
    public List<Map<String,Object>> getGoodsInfoByParam(List<Integer> goodsIds,List<Integer> sortIds, List<Integer> categoryIds){
        EsSearchParam param = new EsSearchParam();
        List<FieldProperty> searchList = new ArrayList<>(4);
        searchList.add( new FieldProperty(EsSearchName.SHOP_ID,getShopId()) );
        if( categoryIds != null && !categoryIds.isEmpty() ){
            searchList.add(new FieldProperty(EsSearchName.FULL_CAT_ID,categoryIds));
        }
        if( sortIds != null && !sortIds.isEmpty() ){
            searchList.add( new FieldProperty(EsSearchName.FULL_SORT_ID,sortIds));
        }
        if( sortIds != null && !sortIds.isEmpty() ){
            searchList.add( new FieldProperty(EsSearchName.GOODS_ID,goodsIds));
        }
        param.setSearchList(searchList);
        try {
            return utilSearchService.getGoodsIdsByParam(param);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 批量提交商品标签ES数据
     * @param goodsLabelList {List<EsGoodsLabel> }
     */
    private void batchCommitEsGoodsIndex(List<EsGoodsLabel> goodsLabelList){
        BulkRequest requests = new BulkRequest();
        for( EsGoodsLabel goodsLabel: goodsLabelList ){
            requests.add(esManager.assemblyRequest(EsGoodsConstant.LABEL_INDEX_NAME,goodsLabel));
        }
        try {
            esManager.batchDocuments(requests);
        } catch (IOException e) {
            log.error("批量建立索引失败");
        }
    }
}