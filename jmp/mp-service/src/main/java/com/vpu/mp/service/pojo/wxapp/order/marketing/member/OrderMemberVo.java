package com.vpu.mp.service.pojo.wxapp.order.marketing.member;

import com.vpu.mp.service.pojo.shop.member.card.ValidUserCardBean;
import com.vpu.mp.service.pojo.wxapp.order.marketing.base.BaseMarketingBaseVo;
import com.vpu.mp.service.shop.member.UserCardService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author 王帅
 */
@Getter
@Setter
@Slf4j
@ToString
public class OrderMemberVo extends BaseMarketingBaseVo {
    /**userCard表字段映射*/
    private Integer  userId;
    private Integer  cardId;
    private Byte  flag;
    private String  cardNo;
    private Timestamp  expireTime;
    private Byte  isDefault;
    private BigDecimal  money;
    private Integer  surplus;
    private Timestamp  activationTime;
    private Integer  exchangSurplus;
    /**memberCard表字段映射*/
    private String cardName;
    private Byte cardType;
    private BigDecimal discount;
    private Byte bgType;
    private String bgColor;
    private String bgImg;
    private String buyScore;
    private Byte expireType;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer receiveDay;
    private Byte dateType;
    private String storeList;
    private String grade;
    private Byte storeUseSwitch;
    // 头像
    String avatar;
    private LocalDate startDate;
    private LocalDate endDate;
    /**
     * 使用pojo初始化自己
     * @param source
     * @return
     */
    public OrderMemberVo init(ValidUserCardBean source){
        try {
            PropertyUtils.copyProperties(this, source);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error(UserCardService.class.getName()+"getValidCardList.copyProperties异常");
        }
        this.setBaseCardId(source.getCardId());
        this.setBaseCardType(source.getCardType());
        return this;
    }

    /**
     * 初始化自己
     */
    public OrderMemberVo init(){
        this.setBaseCardId(getCardId());
        this.setBaseCardType(getCardType());
        return this;
    }
}
