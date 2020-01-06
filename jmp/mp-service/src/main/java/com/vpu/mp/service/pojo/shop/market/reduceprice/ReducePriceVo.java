package com.vpu.mp.service.pojo.shop.market.reduceprice;

import java.sql.Timestamp;
import java.util.List;

import com.vpu.mp.service.pojo.shop.config.PictorialShareConfigVo;
import lombok.Data;


/**
 * @author: 王兵兵
 * @create: 2019-08-14 17:32
 **/
@Data
public class ReducePriceVo {

    /** 活动ID */
    private Integer id;

    /** 活动名称 */
    private String name;

    /** 活动开始时间 */
    private Timestamp startTime;

    /** 活动结束时间 */
    private Timestamp endTime;

    /** 限购数量 */
    private Integer limitAmount;

    /** 周期类型：1:每天 2:每月 3:每周 */
    private Byte periodAction;

    /** 时间段字符串 格式：09:00@12:00 */
    private String pointTime;

    /** 每月第几日；每周第几天 */
    private String extendTime;

    /** 超限购买设置标记，1禁止超限购买，0超限全部恢复原价 */
    private Byte limitFlag;

    /** 优先级 */
    private Byte first;

    /** 改价的商品数组 */
    private List<ReducePriceGoodsVo> reducePriceGoods;

    /** 分享设置 */
    private String shareConfig;
    private PictorialShareConfigVo shopShareConfig;
}
