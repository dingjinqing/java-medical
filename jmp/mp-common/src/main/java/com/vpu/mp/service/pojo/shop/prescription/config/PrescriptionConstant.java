package com.vpu.mp.service.pojo.shop.prescription.config;

/**
 * @author 孔德成
 * @date 2020/7/8 15:07
 */
public interface PrescriptionConstant {
    //********处方来源******//

    /**
     * 0系统内
     */
    public static final Byte SOURCE_MP_SYSTEM =0;
    /**
     * 1his系统
     */
    public static final Byte SOURCE_HIS_SYSTEM =1;
    //******审核状态********//
    /**
     * 0待审核
     */
    public static final Byte STATUS_NO_NEED=0;
    /**
     * 1审核通过
     */
    public static final Byte STATUS__PASS=1;
    /**
     * 2审核未通过
     */
    public static final Byte STATUS__NO_PASS=2;
    //********有效期*******//
    /**
     * 失效
     */
    public static final Byte EXPIRE_TYPE_INVALID=0;
    /**
     * 永久有效
     */
    public static final Byte EXPIRE_TYPE_EVER =1;
    /**
     * 时间段内有效
     */
    public static final Byte EXPIRE_TYPE_TIME=2;

    /**
     * 处方号前缀
     */
    public static final String PRESCRIPTION_CODE_PREFIX="P";
    /**
     * 默认过期时间
     */
    public static final int PRESCRIPTION_EXPIRE_DAY=3;


}
