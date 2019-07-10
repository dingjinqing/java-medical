package com.vpu.mp.service.pojo.shop.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vpu.mp.service.foundation.JsonResultMessage;
import com.vpu.mp.service.foundation.Page;
import lombok.Data;

/**
 * 
 * 标签管理入参
 * 
 * @author 黄壮壮 2019-07-09 11:09
 */

@Data
public class TagPageListParam extends BaseMemberPojo{
		
		@NotNull(message="缺少标签id(tagId)")
		private Integer tagId;
		
		//@NotBlank(message="标签名不能为空")
		@NotBlank(message = JsonResultMessage.MSG_ACCOUNT_NAME_NOT_NULL)
		@Size(max=15,message = JsonResultMessage.MSG_MEMBER_TAG_LENGTH_LIMIT)
		private String tagName;
}
