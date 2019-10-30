package com.vpu.mp.service.pojo.wxapp.order;

import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author 王帅
 *
 */
@Data
@Builder(toBuilder = false)
public class OrderBeforeVo {
	private UserAddressVo address;
	private byte[] expressList;
}
