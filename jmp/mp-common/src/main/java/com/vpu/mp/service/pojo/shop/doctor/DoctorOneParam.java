package com.vpu.mp.service.pojo.shop.doctor;

import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 * @author chenjie
 */
@Data
public class DoctorOneParam {
    private Integer   id;
    private Integer   accountId=0;
    private Integer   age;
    private Integer   workTime;
    private Byte      sex;
    private Byte      duty;
    private String    url;
    private String    name;
    private String    hospitalCode;
    private String    certificateCode;
    private String    professionalCode;
    private String    registerHospital;
    private Date registerTime;
    private String    mobile;
    private Integer   titleId;
    private Byte      status = 1;
    private Byte      isDelete = 0;
    private List<DepartmentOneParam> departmentList;
    private String    titleName;
    private List<Integer> departmentIds;
    private List<String> departmentNames;
    private String departmentIdsStr;
    private Long      consultationPrice;
    private String    treatDisease;
}
