package com.vpu.mp.service.pojo.wxapp.order;

import com.vpu.mp.service.pojo.shop.member.address.UserAddressVo;

import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
	private Byte deliverType;
	private String memberCardNo;
    private String couponSn;
    //goodstype
    private List<StorePojo> storeList;

    public Byte getDefaultDeliverType(){
        for (int i = 0, length = expressList.length ; i < length ; i++){
            if(expressList[i] == OrderConstant.yes){
                return (byte)i;
            }
        }
        return null;
    }
}
