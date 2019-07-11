package com.vpu.mp.service.pojo.shop.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.vpu.mp.service.foundation.JsonResultMessage;

import lombok.Data;

@Data
public class TagInfoParam extends BaseMemberPojo {
	@NotBlank(message = JsonResultMessage.MSG_MEMBER_TAG_NOT_NULL)
	@Size(max=15,message = JsonResultMessage.MSG_MEMBER_TAG_LENGTH_LIMIT)
	private String tagName;
}
