package com.vpu.mp.service.pojo.shop.decoration.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 
 * @author lixinguo
 *
 */
@Getter
@Setter
public class ModuleCard extends ModuleBase {

    @JsonProperty("card_id")
    private Integer cardId;

    @JsonProperty("hidden_card")
    private Byte hiddenCard;

    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("card_state")
    private Byte cardState;

    @JsonProperty("card_grade")
    private String cardGrade;

    @JsonProperty("receive_day")
    private String receiveDay;

    @JsonProperty("card_type")
    private Byte cardType;

    @JsonProperty("legal")
    private String legal;

    @JsonProperty("exchang_count_legal")
    private String exchangCountLegal;

    @JsonProperty("bg_type")
    private Byte bgType;

    @JsonProperty("bg_color")
    private String bgColor;

    @JsonProperty("bg_img")
    private String bgImg;

    @JsonProperty("is_pay")
    private Byte isPay;

    @JsonProperty("pay_type")
    private Byte payType;

    @JsonProperty("pay_fee")
    private BigDecimal payFee;

}
