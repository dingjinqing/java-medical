package com.vpu.mp.service.pojo.shop.doctor.comment;

import com.vpu.mp.common.pojo.shop.table.DoctorCommentReplyDo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 医师评价
 * 医师留言
 *
 * @author 孔德成
 * @date 2020/8/12 15:50
 */
@Data
public class DoctorCommentListVo {

    private Integer id;
    private String userName;
    private Integer doctorId;
    private String name;
    private Byte stars;
    private Byte isAnonymou;
    private String commNote;
    private Integer commNoteLength;
    private String orderSn;
    private Byte auditStatus;
    private Timestamp createTime;
    private List<DoctorCommentReplyDo> replylist;
}
