package com.vpu.jmd.service.shop.bo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author 新国
 *
 */
@Data
public class ShopListQueryParam {
	public String accountKey;
	public String keywords;
	public Integer isUse;
	public String shopType;
	public Byte shopFlag;
	public Byte isEnabled;
	public Byte hidBottom;
	public Integer currentPage;
	public Integer pageRows;
	/**
	 * 区分体验版还是付费版
	 * 1为体验版，2为付费版
	 */
	public String shopTypes;

	public Timestamp expireStartTime;
	public Timestamp expireEndTime;
	/** 营销日历对应的id*/
	private Integer calendarActId;
	/** 所属标签id集合 */
	private List<Integer> tagIds;

	/**失败时间-开始 */
	private Timestamp  failStartTime;
	/**失败时间-结束 */
	private Timestamp  failEndTime;
	/**售后负责人 未设置传-1；所有为null */
	private Integer afterSaleId;

	private Integer provinceCode;
	private Integer cityCode;
	private Integer districtCode;
	/**运营审核人  未设置传-1；所有为null*/
	private Integer accountId;

	/**开户类型 */
	private Byte openAccountType;

	/**续费次数 -开始 */
	private Integer renewTimesStart;
	/**续费次数 -结束 */
	private Integer renewTimesEnd;

	/** 排序的不同 1：创建时间；2：到期时间；3：失败次数*/
	private Byte orderByType;
	/** 为1 desc，0：asc*/
	private Byte orderByAsc;
}
