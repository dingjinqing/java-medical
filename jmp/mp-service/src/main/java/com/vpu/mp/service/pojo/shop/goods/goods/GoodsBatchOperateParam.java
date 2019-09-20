package com.vpu.mp.service.pojo.shop.goods.goods;

import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import lombok.Data;

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
     * goodsId 和价格，数量映射
     */
    private Map<Integer, List<PrdPriceNumberParam>> goodsPriceNumbers;
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
     * 未处理goodsLabel,和shopPrice,goodsNumber，仅处理商品表内数据的变化
     * @return
     */
    public List<GoodsRecord> toUpdateGoodsRecord(){
        if (goodsIds == null || goodsIds.size() == 0) {
            return new ArrayList<>(0);
        }
        List<GoodsRecord> list=new ArrayList<>(goodsIds.size());

        Optional<Byte> isOnSaleOptional = Optional.ofNullable(isOnSale);
        Optional<Timestamp> saleTimeOptional = Optional.ofNullable(saleTime);
        Optional<Integer> catIdOptional = Optional.ofNullable(catId);
        Optional<Integer> deliverTemplateIdOptional = Optional.ofNullable(deliverTemplateId);
        Optional<Integer> limitBuyNumOptional = Optional.ofNullable(limitBuyNum);
        Optional<Integer> limitMaxNumOptional = Optional.ofNullable(limitMaxNum);
        Optional<Byte> isPageUpOptional = Optional.ofNullable(isPageUp);
        Optional<Integer> goodsPageIdOptional = Optional.ofNullable(goodsPageId);
        Optional<Integer> brandIdOptional = Optional.ofNullable(brandId);

        goodsIds.forEach(goodsId->{
            GoodsRecord goodsRecord=new GoodsRecord();
            goodsRecord.setGoodsId(goodsId);

            isOnSaleOptional.ifPresent(goodsRecord::setIsOnSale);
            saleTimeOptional.ifPresent(goodsRecord::setSaleTime);
            catIdOptional.ifPresent(goodsRecord::setCatId);
            deliverTemplateIdOptional.ifPresent(goodsRecord::setDeliverTemplateId);
            limitBuyNumOptional.ifPresent(goodsRecord::setLimitBuyNum);
            limitMaxNumOptional.ifPresent(goodsRecord::setLimitMaxNum);
            isPageUpOptional.ifPresent(goodsRecord::setIsPageUp);
            goodsPageIdOptional.ifPresent(goodsRecord::setGoodsPageId);
            brandIdOptional.ifPresent(goodsRecord::setBrandId);

            list.add(goodsRecord);
        });
        return list;
    }
}
