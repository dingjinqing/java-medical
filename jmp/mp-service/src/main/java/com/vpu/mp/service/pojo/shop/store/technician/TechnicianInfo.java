package com.vpu.mp.service.pojo.shop.store.technician;

import lombok.Data;

/**
 * @author liufei
 * @date 11/5/19
 */
@Data
public class TechnicianInfo {
    private Integer id;
    private Integer storeId;
    private String technicianName;
    private String technicianMobile;
    private String bgImgPath;
    private String technicianIntroduce;
    private Long groupId;
    private Byte serviceType;
    private String remarks;
    String workDate;
    Integer scheduleId;
    String scheduleName;
    String begcreateTime;
    String endTime;
}
