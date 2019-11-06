package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import lombok.Data;

import java.util.List;

/**
 * @author liufei
 * @date 11/5/19
 */
@Data
public class ReservationDetailVo {
    private StoreServiceParam serviceInfo;
    private StorePojo storeInfo;
    private List<ReservationInfo> reservationInfoList;
}
