package com.vpu.mp.service.pojo.wxapp.card.vo;
/**
 * 限次卡已选商品出参
 * @author 黄壮壮
 *
 */

import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.wxapp.user.UserCheckedGoodsVo;

import lombok.Data;
@Data
public class CardCheckedGoodsVo {
	private PageResult<UserCheckedGoodsVo> goodsList;
	private Integer totalNumber;
}
