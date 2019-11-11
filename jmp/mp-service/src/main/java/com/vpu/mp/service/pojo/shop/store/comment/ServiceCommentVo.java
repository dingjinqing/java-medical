package com.vpu.mp.service.pojo.shop.store.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author 黄荣刚
 * @date 2019年7月18日
 *
 */
@Data
@NoArgsConstructor
public class ServiceCommentVo {
	/**评论ID */
	private Integer id;
	/** 门店ID */
	private Integer storeId;
	/** 订单编号 */
	private String orderSn;

	/** 服务ID */
	private Integer serviceId;
	/** 服务主图 */
	private String serviceImg;
	/** 服务名称 */
	private String serviceName;

    /** 用户ID*/
	private Integer userId;
	/** 用户名*/
	private String username;
	/** 用户手机号 */
	private String mobile;


    /** 评价星级 */
	private Byte commstar;
	/** 评论内容 */
	private String commNote;
	/** 评论图片*/
	private String commImg;

    /** 技师ID*/
	private Integer technicianId;
	/** 技师名称 */
	private String technicianName;

    /** 评论创建时间 */
	private Timestamp createTime;


    /** 匿名评价  0.未匿名；1.匿名 */
	private Byte anonymousflag;

	/** 0:未审批,1:审批通过,2:审批未通过 */
	private Byte flag;

}
