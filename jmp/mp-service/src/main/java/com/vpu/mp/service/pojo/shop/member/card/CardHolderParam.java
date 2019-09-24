package com.vpu.mp.service.pojo.shop.member.card;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.util.Page;

import lombok.Data;

/**
* @author 黄壮壮
* @Date: 2019年9月24日
* @Description: 查询持卡会员信息
*/
@Data
public class CardHolderParam {
	/** -每页总数 */
	public Integer pageRows;
	/** -当前页 */
	public Integer currentPage;
	/** - 会员卡Id */
	private Integer cardId;
	/** - 会员ID */
	private Integer userId;
	/** - 昵称 */
	private String username;
	/** - 手机号 */
	private String mobile;
	/** - 会员卡号 */
	private String cardNo;
	/** - 0:正常，1:删除 2 已过期{@link com.vpu.mp.service.pojo.shop.member.card.CardConstant.CARD_USING } */
	private Byte flag;
	/** - 领卡时间 开始范围 */
	private Timestamp firstDateTime;
	/** - 领卡时间 结束范围 */
	private Timestamp secondDateTime;
}
