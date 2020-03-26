package com.vpu.mp.service.pojo.shop.store.account;

import java.util.List;

import com.vpu.mp.service.pojo.shop.store.store.StoreBasicVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 已经设置的店铺和总店铺
 * 
 * @author zhaojianqiang
 * @time 下午2:19:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfoVo {
	List<StoreBasicVo> haveStore;
	List<StoreBasicVo> allStore;
}
