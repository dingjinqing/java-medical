package com.vpu.mp.common.pojo.shop.table;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2020/7/28 14:07
 */
public class OrderMedicalHistoryDo {

    private Integer   id;
    private Integer   orderId;
    private Integer   patientId;
    private String    patientName;
    private Byte      sex;
    private Integer   age;
    private String    patientComplain;
    private String    diseaseHistory;
    private String    imagesList;
    private Byte      isDelete;
    private Timestamp createTime;
    private Timestamp updateTime;

}
