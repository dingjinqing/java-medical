package com.vpu.mp.service.pojo.shop.market.payaward;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/8/13 14:59
 */
@Getter
@Setter
public class PayAwardListVo {

    private Integer id;
    private String activityNames;
    /**
     * 有效期类型 0定期1永久
     */
    private Byte timeType;
    private Timestamp startTime;
    private Timestamp endTime;
    private Byte status;
    /**
     * 优先级
     */
    private Integer actFirst;
    /**
     * 奖励json
     */
    private String awardList;
    /**
     * 奖励内容
     */
    private  List<PayAwardContentBo>  awardContentList;
}
