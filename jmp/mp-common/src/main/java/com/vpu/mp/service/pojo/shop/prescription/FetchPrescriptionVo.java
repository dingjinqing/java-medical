package com.vpu.mp.service.pojo.shop.prescription;

import com.vpu.mp.common.pojo.shop.table.PrescriptionDo;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author zhaoxiaodong
 * @create 2020-07-16 14:31
 */

@Data
public class FetchPrescriptionVo extends PrescriptionDo {
    private List<FetchPrescriptionItemVo> list;
}
