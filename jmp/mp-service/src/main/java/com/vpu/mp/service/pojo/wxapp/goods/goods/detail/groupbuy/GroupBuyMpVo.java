package com.vpu.mp.service.pojo.wxapp.goods.goods.detail.groupbuy;

import com.vpu.mp.service.pojo.wxapp.goods.goods.detail.GoodsActivityDetailMp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 拼团详情
 * @author 李晓冰
 * @date 2019年12月23日
 */
@Getter
@Setter
public class GroupBuyMpVo extends GoodsActivityDetailMp {
    /** 是否团长优惠 */
    private Byte isGrouperCheap;

    /** 参团人数 */
    private Short limitAmount;

    /** 拼团最少购买数量 */
    private Integer limitBuyNum;
    /** 拼团最大购买数量 */
    private Integer limitMaxNum;

    /** 已成功拼团数量 */
    private Integer groupBuySuccessCount;

    /**活动运费 0 不免运费 1 免运费*/
    private Byte freeShip;

    /** 正在进行中拼团列表信息 */
    private List<GroupBuyListMpVo> groupBuyListMpVos;

    /** 拼团商品规格信息 */
    private List<GroupBuyPrdMpVo> groupBuyPrdMpVos;
}
