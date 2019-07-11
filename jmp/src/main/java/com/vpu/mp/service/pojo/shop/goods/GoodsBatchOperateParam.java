package com.vpu.mp.service.pojo.shop.goods;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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

    public String[] toUpdateSql() {
        if (goodsIds == null || goodsIds.size() == 0) {
            return null;
        }

        String[] updateSql = new String[goodsIds.size()];
        for (int i=0;i<goodsIds.size();i++) {
            Integer goodsId =goodsIds.get(i);

            StringBuilder sb = new StringBuilder();

            if (isOnSale != null) {
                sb.append(",is_on_sale=").append(isOnSale);
            }

            if (saleTime != null) {
                sb.append(",sale_time='").append(saleTime).append("'");
            }

            if (shopPrices != null) {
                BigDecimal bigDecimal = shopPrices.get(goodsId);
                if (bigDecimal != null) {
                    sb.append(",shop_price=").append(bigDecimal.toString());
                }
            }

            if (catId != null) {
                sb.append(",cat_id=").append(catId);
            }

            if (deliverTemplateId != null) {
                sb.append(",deliver_template_id=").append(deliverTemplateId);
            }

            if (limitBuyNum != null) {
                sb.append(",limit_buy_num=").append(limitBuyNum);
            }

            if (limitMaxNum != null) {
                sb.append(",limit_max_num=").append(limitMaxNum);
            }

            if (isPageUp != null) {
                sb.append(",is_page_up=").append(isPageUp);
            }

            if (goodsPageId != null) {
                sb.append(",goods_page_id=").append(goodsPageId);
            }

            if (brandId != null) {
                sb.append(",brand_id=").append(brandId);
            }

            if (sb.length()==0){
                return null;
            }

            sb.append(" where goods_id=").append(goodsId);
            sb.deleteCharAt(0);

            sb.insert(0,"update b2c_goods set ");

            updateSql[i]=sb.toString();
        }

        return updateSql;
    }
}
