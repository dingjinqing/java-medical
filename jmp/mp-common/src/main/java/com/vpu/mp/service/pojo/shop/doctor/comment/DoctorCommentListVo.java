package com.vpu.mp.service.pojo.shop.doctor.comment;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 医师评价
 * @author 孔德成
 * @date 2020/8/12 15:50
 */
@Data
public class DoctorCommentListVo {

    private String userName;
    private Byte stars;
    private Byte isAnonymou;
    private String commNote;
    private Integer commNoteLength;
    private String orderSn;
    private Timestamp createTime;
}
