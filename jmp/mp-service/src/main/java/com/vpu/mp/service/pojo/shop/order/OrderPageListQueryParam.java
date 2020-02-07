package com.vpu.mp.service.pojo.shop.order;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 
 * @author wangshuai
 */
@Data
public class OrderPageListQueryParam {
	public Integer currentPage;
	public Integer pageRows;
	/**查询类型:0综合查询,1退货退款订单*/
	public Byte searchType;
	public String goodsName;
	public String orderSn;
	public Byte[] orderStatus;
	/** 订单类型 */
	public Byte[] goodsType;
	/** 收货人姓名 */
	public String consignee;
	public String mobile;
	/** 下单时间开始 */
	public Timestamp createTimeStart;
	/** 下单时间结束 */
	public Timestamp createTimeEnd;
	/** 配送方式 :0 快递 1 自提 */
	public Byte deliverType;
	/** 买家昵称,会员表的username */
	public String userName;
	/** 买家来源 */
	public String source;
	/** 标签,买家会员标签 */
	public Integer[] tagIds;
	/** 门店id */
	public Integer storeId;
	/** 核销自提码 */
	public String verifyCode;
	/** 完成时间 */
	public Timestamp finishedTimeStart;
	public Timestamp finishedTimeEnd;
	/** 国别、省、市、区 */
	public Integer countryCode;
	public Integer provinceCode;
	public Integer cityCode;
	public Integer districtCode;
	/**支付方式*/
	public Byte payWay;
	/**规格编码*/
	public String productSn;
	/**标星订单*/
	public Byte isStar;
	/**
	 * 拼团退款失败单
	 */
	/** 拼-拼团状态:0:拼团中 1:拼团成功 2:拼团失败 */
	public Byte[] pinStatus;

	/**
	 * 退货退款订单特色参数
	 */
	/** 退-退款编号 */
	public String returnOrderSn;
	/** 退-退款类型:0仅退款 1:退货退款 2:仅退运费 3:手动退款 */
	public Byte[] returnType;
	/** 退-申请时间开始 */
	public Timestamp returnStart;
	/** 退-申请时间 */
	public Timestamp returnEnd;
	/** 退款退货状态*/
	public Byte refundStatus;
    /**营销活动主键 过滤某营销活动订单*/
	public Integer activityId;
	/**用户id*/
	public Integer userId;
}
