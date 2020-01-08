package com.vpu.mp.service.pojo.wxapp.goods.goods.detail.firstspecial;

import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsActivityDetailMp;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * 首单特惠类
 * @author 李晓冰
 * @date 2020年01月06日
 */
@Getter
@Setter
public class FirstSpecialMpVo extends GoodsActivityDetailMp{
    private Boolean isForever;
    /**是否限购 */
    private Boolean isLimit;
    /**限购数量*/
    private Integer limitAmount;
    /**是否进行超购限制*/
    private Boolean limitFlag;
    /**规格信息列表*/
    private List<FirstSpecialPrdMpVo> firstSpecialPrdMpVos;

    /**活动开始时间点*/
    private Timestamp startTimestamp;
    /**活动结束时间点*/
    private Timestamp endTimestamp;
}
