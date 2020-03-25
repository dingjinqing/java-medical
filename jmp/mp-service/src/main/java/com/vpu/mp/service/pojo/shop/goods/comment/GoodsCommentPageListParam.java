package com.vpu.mp.service.pojo.shop.goods.comment;

import com.vpu.mp.service.foundation.util.Page;
import lombok.Data;

import java.util.List;


/**
 * 映射分页查询条件实体类
 * @author liangchen
 * @date 2019年07月07日
 */
@Data
public class GoodsCommentPageListParam {
	/** 审核状态默认值 */
	public static final Byte FLAG_DEFAULT_VALUE =-1;
	/** 审核通过 置1 */
	public static final Byte FLAG_PASS_VALUE =1;
	/** 审核拒绝 置2 */
	public static final Byte FLAG_REFUSE_VALUE =2;
	/** 评价星级分类默认值 */
    public static final Byte COMMSTAR_DEFAULT_VALUE = 0;
    /** 是否删除默认值0 未删除，1已删除 */
    public static final Byte IS_DELETE_DEFAULT_VALUE = 0;
    /** 商家分类默认搜索条为空 */
    public static final Integer SORT_DEFAULT_VALUE=-1;
    /** 不是从店铺助手跳转来 */
    public static final Byte NOT_FROM_SHOP_ASSISTANT=0;
    /** 从店铺助手跳转来 */
    public static final Byte FROM_SHOP_ASSISTANT=1;
    /**
     * 	搜索条件
     */
    private String orderSn;
    private String goodsName;
    private String mobile;
    private Integer sortId = SORT_DEFAULT_VALUE;
    private Byte flag = FLAG_DEFAULT_VALUE;
    private Byte commstar = COMMSTAR_DEFAULT_VALUE;
    private Integer awardActivityId;
    private Integer nDays = 3;
    /** 是否从店铺助手跳转来 默认否 */
    private Byte shopAssistantFlag = NOT_FROM_SHOP_ASSISTANT;
    /**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
