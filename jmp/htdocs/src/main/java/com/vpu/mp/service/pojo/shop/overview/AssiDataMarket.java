package com.vpu.mp.service.pojo.shop.overview;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author:liufei
 * @Date:2019/7/18
 * @Description:
 */
@Data
@Component
public class AssiDataMarket {
    //分销审核超时 0: 分销员审核进度良好 否有examine个分销员申请超过3天未处理
    public int examine;
    /**
     * 会员卡激活审核，主要取决于card_num的数量
     * K---card_id：card_num非0时表示待处理申请的会员卡id
     * K---card_name：card_num非0时表示待处理申请的会员卡名称
     * K---card_num：为0表示会员卡激活审核进度良好，否表示card_name类会员卡有card_num个会员卡激活申请超过2天未处理
     */
    public Map<String,String> member;
    /**
     * 优惠券
     * K---id:优惠券id
     * V---actName:优惠券名称
     */
    public Map<Integer,String> voucher;
}
