package com.vpu.mp.service.pojo.wxapp.footprint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsListMpVo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * 我的足迹列表
 * @author 孔德成
 * @date 2019/11/4 14:50
 */
@Getter
@Setter
public class FootprintListVo {

    /**
     * 商品id
     */
    @JsonIgnore
    private Integer goodsId;
    /**
     * 时间 yyyy-MM-dd
     */
    private Timestamp date;
    /**
     * 商品列表
     */
    private List<GoodsListMpVo> goodsList;

}
