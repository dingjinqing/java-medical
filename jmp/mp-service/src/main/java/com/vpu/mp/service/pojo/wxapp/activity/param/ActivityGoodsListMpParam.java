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
    /** 用户id*/
    private Integer userId;
    private List<Integer> goodsIds;

    /**会员专享处传参使用*/
    private List<Integer> catIds;
    private List<Integer> sortIds;
    private List<Integer> brandIds;

    private Timestamp date = DateUtil.getLocalDateTime();

    /** 满折满减，优惠券处使用入参*/
    private List<AllIdsParam> idsParams;

    /** 存放各种过滤id值 */
    public static class AllIdsParam{
        public AllIdsParam() {
        }

        public AllIdsParam(Integer goodsId, Integer catId, Integer sortId, Integer brandId) {
            this.goodsId = goodsId;
            this.catId = catId;
            this.sortId = sortId;
            this.brandId = brandId;
        }
        public Integer goodsId;
        public Integer catId;
        public Integer sortId;
        public Integer brandId;
    }
}
