package com.vpu.mp.service.pojo.shop.distribution;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DistributorLevelUserNumVo {
    @JsonProperty("distributor_level")
	private Integer distributorLevel;
	private Integer userNumber;
}
