package com.vpu.mp.service.pojo.wxapp.store;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * The type Store list param.
 *
 * @author liufei
 * @date 10 /15/19
 */
@Data
public class StoreListParam {
    /**
     * The Location.用户位置信息json
     * "{"latitude":39.95933,"longitude":116.29845,"speed":-1,"accuracy":65,"verticalAccuracy":65,"horizontalAccuracy":65,"errMsg":"getLocation:ok"}"
     */
    @NotBlank
    public String location;
    /**
     * The Type.type为0时,获得可购买的门店列表 todo 门店列表入口{所有门店,可购买该商品门店列表,同城服务门店列表,扫码购门店列表,会员卡页面进入的门店列表}
     */
    public Byte type = 0;
    /**
     * The Scan stores.筛选支持扫码购的门店;1:是,0:否
     */
    @JsonProperty("scan_stores")
    @JsonAlias({"scan_stores", "scanStores"})
    public Byte scanStores;
    /**
     * The Goods id.商品id
     */
    @JsonProperty("goods_id")
    @JsonAlias({"goods_id", "goodsId"})
    public Integer goodsId;

    /**
     * The Card id.会员卡id
     */
    @JsonProperty("card_id")
    @JsonAlias({"card_id", "cardId"})
    public Integer cardId;

}
