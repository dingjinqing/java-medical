package com.vpu.mp.common.foundation.data;

/**
 * 会话常量
 * @author 李晓冰
 * @date 2020年07月22日
 */
public class ImSessionConstant {
    /**
     * 医师待接诊
     */
    public static final Byte SESSION_READY_TO_START = 0;

    /**
     * 会话中
     */
    public static final Byte SESSION_ON = 1;

    /**
     * 会话取消
     */
    public static final Byte SESSION_CANCEL = 2;

    /**
     * 会话正常结束
     */
    public static final Byte SESSION_END = 3;

    /**
     * 会话取消等待时间 24小时
     */
    public static final Integer CANCEL_LIMIT_TIME = 24;

    /**
     * 会话关闭默认等待时间24小时
     */
    public static final Integer CLOSE_LIMIT_TIME = 24;

    /**会话可用 可发消息*/
    public static final Byte SESSION_CAN_USE = 1;
    /**会话不可用 不可发消息*/
    public static final Byte SESSION_CAN_NOT_USE = 0;
}
