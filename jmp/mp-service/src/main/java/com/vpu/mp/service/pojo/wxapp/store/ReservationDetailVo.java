package com.vpu.mp.service.pojo.wxapp.store;

import com.vpu.mp.service.pojo.shop.store.service.StoreServiceParam;
import com.vpu.mp.service.pojo.shop.store.store.StorePojo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liufei
 * @date 11/5/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetailVo {
    private StoreServiceParam serviceInfo;
    private StorePojo storeInfo;
    private List<ReservationInfo> reservationInfoList;
}
