package com.vpu.mp.service.pojo.shop.store.store;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 王兵兵
 *
 * 2019年7月4日
 */
@Data
@NoArgsConstructor
public class StoreListQueryParam {
	private String groupName;
	private Integer groupId;
	private Boolean isAuthPos;
	/**
	 *  门店名称/编码/负责人
	 */
	private String keywords;
    /**营业状态1:营业，0:关店*/
    private Byte businessState = CONDITION_ALL;
    /** The Auto pick.是否自提设置，0否，1是 */
    private Short autoPick = CONDITION_ALL_SHORT;
    /** 是否支持同城配送 0否 1支持 */
    private Byte cityService = CONDITION_ALL;
    /** 搜索条件为全部 */
    public static final Byte CONDITION_ALL = -1;
    public static final Short CONDITION_ALL_SHORT = -1;
	/**
     * 	分页信息
     */
    private Integer currentPage = Page.DEFAULT_CURRENT_PAGE;
    private Integer pageRows = Page.DEFAULT_PAGE_ROWS;
}
