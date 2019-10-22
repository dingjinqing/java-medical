package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author liufei
 * @date 10/21/19
 */
@Data
public class StorePayOrderVo {
    public Integer source;
    public Integer score;
    public BigDecimal account;
    public Byte invoiceSwitch;
    public List<ValidUserCardBean> memberCardList;
    public Byte shopBusinessState;
    public Byte storeBusinessState;
    public String shopLogo;
    public Byte storeBuy;
    public Byte delFlag;
}
