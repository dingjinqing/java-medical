package com.vpu.mp.service.pojo.shop.goods.goods;

/**
 * 商品导入时，数据格式错误类型
 *
 * @author 李晓冰
 * @date 2020年03月20日
 */
public enum GoodsDataIIllegalEnum {
    /**
     * 数据在操作过程中产生异常错误
     */
    GOODS_FAIL((byte) -1, null),
    /**
     * 数据操作成功
     */
    GOODS_OK((byte) 0, null),
    /**
     * 货品编号为null
     */
    GOODS_SN_NULL((byte) 1, "goods.sn.is.null"),
    /**
     * 商品名称为null
     */
    GOODS_NAME_NULL((byte) 2, "goods.name.is.null"),
    /**
     * 商家编码为null
     */
    GOODS_PRD_SN_NULL((byte) 3, "goods.prd.sn.is.null"),
    /**
     * excel数据同一商品的不同sku商家编码输入数据重复
     */
    GOODS_PRD_SN_INNER_REPEATED((byte) 4, "goods.prd.sn.inner.repeated"),
    /**
     * sku商家编码数据库重复
     */
    GOODS_PRD_SN_EXIST((byte) 5, "goods.spec.prd.sn.exist"),
    /**
     * 商品名称重复
     */
    GOODS_NAME_EXIST((byte) 6, "goods.name.exist"),
    /**
     * goods_sn重复
     */
    GOODS_SN_EXIST((byte) 7, "goods.sn.exist"),
    /**
     * 商品规格项名称或值填写重复
     */
    GOODS_SPEC_K_V_REPEATED((byte) 8, "goods.spec.k.v.repeated"),
    /**
     * 商品的规格项填写错误
     */
    GOODS_PRD_DESC_WRONG((byte) 9, "goods.prd.desc.wrong"),
    /**
     * 商品主图不存在
     */
    GOODS_IMG_IS_WRONG((byte) 10, "goods.img.is.wrong"),
    /**
     * 商品零售价格不存在
     */
    GOODS_SHOP_PRICE_IS_NULL((byte) 11, "goods.shop.price.is.null");


    private byte errorCode;
    private String errorMsg;

    GoodsDataIIllegalEnum(byte errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public byte getErrorCode() {
        return errorCode;
    }

    public static GoodsDataIIllegalEnum getIllegalEnum(byte errorCode) {
        GoodsDataIIllegalEnum retEnum = null;
        switch (errorCode) {
            case -1:
                retEnum = GoodsDataIIllegalEnum.GOODS_FAIL;
                break;
            case 0:
                retEnum = GoodsDataIIllegalEnum.GOODS_OK;
                break;
            case 1:
                retEnum = GoodsDataIIllegalEnum.GOODS_SN_NULL;
                break;
            case 2:
                retEnum = GoodsDataIIllegalEnum.GOODS_NAME_NULL;
                break;
            case 3:
                retEnum = GoodsDataIIllegalEnum.GOODS_PRD_SN_NULL;
                break;
            case 4:
                retEnum = GoodsDataIIllegalEnum.GOODS_PRD_SN_INNER_REPEATED;
                break;
            case 5:
                retEnum = GoodsDataIIllegalEnum.GOODS_PRD_SN_EXIST;
                break;
            case 6:
                retEnum = GoodsDataIIllegalEnum.GOODS_NAME_EXIST;
                break;
            case 7:
                retEnum = GoodsDataIIllegalEnum.GOODS_SN_EXIST;
                break;
            case 8:
                retEnum = GoodsDataIIllegalEnum.GOODS_SPEC_K_V_REPEATED;
                break;
            case 9:
                retEnum = GoodsDataIIllegalEnum.GOODS_PRD_DESC_WRONG;
                break;
            case 10:
                retEnum = GoodsDataIIllegalEnum.GOODS_IMG_IS_WRONG;
                break;
            case 11:
                retEnum = GoodsDataIIllegalEnum.GOODS_SHOP_PRICE_IS_NULL;
                break;
            default:
                retEnum = null;
        }
        return retEnum;
    }
}
