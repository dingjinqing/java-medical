package com.vpu.mp.service.pojo.wxapp.medical.im.param;

import lombok.Data;

/**
 * 拉取信息参数
 * @author 李晓冰
 * @date 2020年07月22日
 */
@Data
public class ImSessionPullMsgParam {
    /**
     * 科室id
     */
    private Integer departmentId;
    /**
     * 患者id
     */
    private Integer patientId;
    /**
     * 要拉取的信息的对方 医生拉取的时候就是用户的id，如果是用户的话就是医师的id
     */
    private Integer pullFromId;
    /**
     * 自己的id，医师id或用户id
     */
    private Integer selfId;
}
