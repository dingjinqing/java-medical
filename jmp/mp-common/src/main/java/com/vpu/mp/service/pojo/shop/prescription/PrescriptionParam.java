package com.vpu.mp.service.pojo.shop.prescription;

import com.vpu.mp.common.pojo.shop.table.PrescriptionDo;
import com.vpu.mp.common.pojo.shop.table.PrescriptionItemDo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 处方全字段
 * @author 孔德成
 * @date 2020/7/2 18:09
 */
@Data
public class PrescriptionParam  extends PrescriptionDo {


    List<PrescriptionItemDo> list =new ArrayList<>();
}
