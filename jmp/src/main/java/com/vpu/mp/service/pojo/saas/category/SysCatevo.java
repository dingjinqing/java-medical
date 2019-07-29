package com.vpu.mp.service.pojo.saas.category;

import java.util.List;

import lombok.Data;

@Data
public class SysCatevo {
	public Short catId;
	public String catName;
	public List<? extends SysCatevo> childCate;
	public List<? extends SysCatevo> childCate1;
	
	
}
