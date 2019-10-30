package com.vpu.mp.service.pojo.wxapp.activity.param;

import com.vpu.mp.service.foundation.util.DateUtil;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月29日
 */
@Data
public class ActivityGoodsListMpParam implements ActivityParam {
    private List<Integer> goodsIds;
    private Timestamp date = DateUtil.getLocalDateTime();

    /** 满折满减，优惠券处使用入参*/
    private List<AllIdsParam> idsParams;

    /** 存放各种过滤id值 */
    public static class AllIdsParam{
        public Integer goodsId;
        public Integer catId;
        public Integer sortId;
        public Integer brandId;
    }
}
