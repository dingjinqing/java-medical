package com.vpu.mp.service.pojo.shop.member.data;

import lombok.Data;

@Data
public class EducationVo {
	private Integer value;
	private String label;
	public EducationVo(Integer value,String label) {
		this.value = value;
		this.label = label;
	}
}
