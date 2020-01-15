package com.vpu.mp.service.pojo.wxapp.goods.goods.list;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpu.mp.service.pojo.shop.decoration.module.ModuleGoodsGroup;
import lombok.Data;

import java.util.List;

/**
 * 商品分组-查询参数
 *
 * @author 李晓冰
 * @date 2020年01月15日
 */
@Data
public class GoodsGroupListMpParam {
    public static final Byte SECTION_SHOW_ALL = 1;
    /**分组是商家分类*/
    public static final String SORT_TYPE = "";
    /**分组是标签*/
    public static final String LABEL_TYPE = "1";
    /**分组是品牌*/
    public static final String BRAND_TYPE = "2";
    /**商品可以显示的最大数量*/
    public static final Integer NUM_TO_SHOW = 6;


    /**菜单位置radio，0 顶部固定，1左侧固定*/
    @JsonProperty("position_style")
    private Byte positionStyle;

    /**是否展示"全部商品"栏位 1展示 0不展示*/
    @JsonProperty("group_display")
    private Byte groupDisplay;

    /**筛选条件*/
    @JsonProperty("sort_group_arr")
    private List<ModuleGoodsGroup.SortGroup> sortGroupArr;

    /**用户id controller层设置*/
    private Integer userId;
}
