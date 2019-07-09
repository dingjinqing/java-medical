package com.vpu.mp.service.pojo.shop.goods.brand;

import com.vpu.mp.service.foundation.Page;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 映射分页查询条件实体类
 * @author 李晓冰
 * @date 2019年07月02日
 */
@Data
public class GoodsBrandPageListParam {
	/**
	 * 品牌所附分类默认值
	 */
    public static final int CLASSIFY_ID_DEFAULT_VALUE = 0;
    /**
     *是否推荐默认值
     */
    public static final int IS_RECOMMEND_DEFAULT_VALUE = 0;
    /**
     * 是否删除默认值0 未删除，1已删除
     */
    public static final int IS_DELETE_DEFAULT_VALUE = 0;
    /**
     * 	搜索条件
     */
    private String brandName;
    private Timestamp startAddTime;
    private Timestamp endAddTime;
    private int classifyId = CLASSIFY_ID_DEFAULT_VALUE;
    private int isRecommend = CLASSIFY_ID_DEFAULT_VALUE;

    /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
