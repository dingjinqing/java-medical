package com.vpu.mp.service.pojo.shop.member.address;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 孔德成
 * @date 2019/11/18 17:15
 */
@Getter
@Setter
public class AddressCode {
    private String postalName;
    private Integer postalId;
    private String cityName;
    private Integer cityId;
    private String districtName;
    private Integer districtId;
}
