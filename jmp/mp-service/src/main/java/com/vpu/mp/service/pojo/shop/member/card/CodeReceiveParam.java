package com.vpu.mp.service.pojo.shop.member.card;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月25日
* @Description: 会员领取码-会员卡领取入参
*/
@Data
public class CodeReceiveParam {
	/** -每页总数 */
	public Integer pageRows;
	/** -当前页 */
	public Integer currentPage;
	/** -会员卡id  */
	private Integer cardId;
	/** -手机号  */
	private String mobile;
	/** -用户昵称  */
	private String username;
	/** -批次名称id  */
	private Integer batchId;
}
