package com.vpu.mp.service.pojo.shop.config.pledge;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luguangyao
 */
@Data
@NoArgsConstructor
public class PledgeInfo {
    private Integer   id;
    private String    pledgeName;
    private String    pledgeLogo;
    private String    pledgeContent;
    private Byte      state;
}
