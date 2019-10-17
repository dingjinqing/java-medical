package com.vpu.mp.service.pojo.wxapp.goods.brand;
import lombok.Data;

/**
 * @author 李晓冰
 * @date 2019年10月17日
 * 商品品牌附带其拼音开头字母处理结果
 */
@Data
public class GoodsBrandMpVo {
    private Integer id;
    private String brandName;
    /**拼音开头的字符*/
    private String pinYinStart;
    private String eName;
    private String logo;
}