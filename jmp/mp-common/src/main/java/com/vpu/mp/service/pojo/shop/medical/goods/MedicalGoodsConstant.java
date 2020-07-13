package com.vpu.mp.service.pojo.shop.medical.goods;

/**
 * 药品、规格使用常量名值定义处
 * @author 李晓冰
 * @date 2020年07月05日
 */
public class MedicalGoodsConstant {
    /**
     * 默认规格标识
     */
    public static final Byte DEFAULT_SKU = 1;

    /**
     * 是否是药品
     */
    public static final Byte GOODS_IS_MEDICAL = 1;
    /**
     * 颜色:红色;尺寸:xl
     * 2:1!!3:1
     * 规格名值分割符
     */
    public static final String SPEC_NAME_VAL_SPLITER = ":";

    /**
     * 规格id分隔符
     */
    public static final String PRD_SPEC_SPLITER = "!!";

    /**
     * 规格描述分隔符
     */
    public static final String PRD_DESC_SPLITER =";";

    /**
     * 售罄
     */
    public static final Byte SALE_OUT_YES = 1;

    /**
     * 未售罄
     */
    public static final Byte SALE_OUT_NO = 0;
}
