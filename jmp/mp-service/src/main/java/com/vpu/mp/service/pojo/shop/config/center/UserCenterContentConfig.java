package com.vpu.mp.service.pojo.shop.config.center;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/11 10:55
 */
@Data
@NoArgsConstructor
public class UserCenterContentConfig {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String iconName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isShow;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String link;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String linkName;


}
