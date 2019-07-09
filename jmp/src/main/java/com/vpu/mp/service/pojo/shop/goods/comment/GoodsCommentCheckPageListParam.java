package com.vpu.mp.service.pojo.shop.goods.comment;

import com.thoughtworks.xstream.mapper.Mapper.Null;
import com.vpu.mp.service.foundation.Page;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 映射分页查询条件实体类
 * @author liangchen
 * @date 2019年07月08日
 */
@Data
public class GoodsCommentCheckPageListParam {
	public static final int FLAG_DEFAULT_VALUE =3;// 审核状态默认值
	public static final int FLAG_PASS_VALUE =1;
	public static final int FLAG_REFUSE_VALUE =2;
    public static final int COMMSTAR_DEFAULT_VALUE = 0;// 评价星级分类默认值
    public static final int IS_DELETE_DEFAULT_VALUE = 0;// 是否删除默认值0 未删除，1已删除
    
    /**
     * 	搜索条件
     */
    private String orderSn;
    private String goodsName;
    private String mobile;
    private int flag=FLAG_DEFAULT_VALUE;
    private int commstar = COMMSTAR_DEFAULT_VALUE;
   /*private int isRecommend = COMMSTAR_DEFAULT_VALUE;*/  //应该是评价奖励

    /**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
