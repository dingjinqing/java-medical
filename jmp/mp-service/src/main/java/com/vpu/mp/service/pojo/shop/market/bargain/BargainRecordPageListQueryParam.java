package com.vpu.mp.service.pojo.shop.market.bargain;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
 * @author 王兵兵
 *
 * 2019年7月25日
 */
@Data
public class BargainRecordPageListQueryParam {
	
	@NotNull
	private int bargainId;
	
	private String username;
	private String mobile;
	
	/**
	 * 状态过滤 0砍价中，1成功，2失败，-1全部 
	 */
	private byte status = -1;
	
	/**
	 * 发起时间过滤 
	 */
	private Timestamp startTime;
	private Timestamp endTime;
	
	/**
     * 	分页信息
     */
    private int currentPage = Page.DEFAULT_CURRENT_PAGE;
    private int pageRows = Page.DEFAULT_PAGE_ROWS;
}
