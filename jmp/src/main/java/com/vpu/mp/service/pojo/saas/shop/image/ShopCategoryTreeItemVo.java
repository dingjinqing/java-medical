package com.vpu.mp.service.pojo.saas.shop.image;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/7/16 18:54
 */
@Data
public class ShopCategoryTreeItemVo {
    private Integer id = 0;
    private String name ;
    private Integer level=1;
    private List<ShopCategoryTreeItemVo> child=new ArrayList<>();
}
