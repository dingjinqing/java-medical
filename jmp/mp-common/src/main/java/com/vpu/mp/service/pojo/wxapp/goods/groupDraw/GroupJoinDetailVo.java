package com.vpu.mp.service.pojo.wxapp.goods.groupDraw;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinDetailVo {
	private Byte groupStatus;
	private Integer surplusGroupNum;
	private GroupDrawBotton button;
	private DrawUser drawUser;
	private List<GroupDrawList> userList;
}
