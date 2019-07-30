package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author 李晓冰
 * @date 2019年07月10日
 */
@Data
public class GoodsBatchOperateParam {

    private List<Integer> goodsIds;

    /**
     * 销售状态
     */
    private Byte isOnSale;
    private Timestamp saleTime;
    /**
     * 价格
     */
    private Map<Integer, BigDecimal> shopPrices;
    /**
     * 平台分类
     */
    private Integer catId;
    /**
     * 运费信息
     */
    private Integer deliverTemplateId;
    /**
     * 限购数量
     */
    private Integer limitBuyNum;
    private Integer limitMaxNum;
    /**
     * 模板信息
     */
    private Byte isPageUp;
    private Integer goodsPageId;
    /**
     * 标签
     */
    private List<Integer> goodsLabels;

    private Integer brandId;
    /**
     * 是否会员专享
     */
    private Byte isCardExclusive;

    /**
     * 未处理goodsLabel
     * @return
     */
    public List<GoodsRecord> toUpdateGoodsRecord(){
        if (goodsIds == null || goodsIds.size() == 0) {
            return new ArrayList<>(0);
        }
        List<GoodsRecord> list=new ArrayList<>(goodsIds.size());

        Optional<Byte> isOnSaleO = Optional.ofNullable(isOnSale);
        Optional<Timestamp> saleTimeO = Optional.ofNullable(saleTime);
        Optional<Map<Integer, BigDecimal>> shopPricesO = Optional.ofNullable(shopPrices);
        Optional<Integer> catIdO = Optional.ofNullable(catId);
        Optional<Integer> deliverTemplateIdO = Optional.ofNullable(deliverTemplateId);
        Optional<Integer> limitBuyNumO = Optional.ofNullable(limitBuyNum);
        Optional<Integer> limitMaxNumO = Optional.ofNullable(limitMaxNum);
        Optional<Byte> isPageUpO = Optional.ofNullable(isPageUp);
        Optional<Integer> goodsPageIdO = Optional.ofNullable(goodsPageId);
        Optional<Integer> brandIdO = Optional.ofNullable(brandId);

        goodsIds.forEach(goodsId->{
            GoodsRecord goodsRecord=new GoodsRecord();
            goodsRecord.setGoodsId(goodsId);

            isOnSaleO.ifPresent(goodsRecord::setIsOnSale);
            saleTimeO.ifPresent(goodsRecord::setSaleTime);
            shopPricesO.ifPresent(v->goodsRecord.setShopPrice(shopPrices.get(goodsId)));
            catIdO.ifPresent(goodsRecord::setCatId);
            deliverTemplateIdO.ifPresent(goodsRecord::setDeliverTemplateId);
            limitBuyNumO.ifPresent(goodsRecord::setLimitBuyNum);
            limitMaxNumO.ifPresent(goodsRecord::setLimitMaxNum);
            isPageUpO.ifPresent(goodsRecord::setIsPageUp);
            goodsPageIdO.ifPresent(goodsRecord::setGoodsPageId);
            brandIdO.ifPresent(goodsRecord::setBrandId);

            list.add(goodsRecord);
        });

        return list;
    }

}
