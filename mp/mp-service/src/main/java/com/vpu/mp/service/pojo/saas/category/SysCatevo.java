package com.vpu.mp.service.pojo.saas.category;

import lombok.Data;

@Data
public class SysCatevo {
	public Short catId;
	public String catName;
	public Integer parentId;
	public Integer level;
	public Integer hasChild;
	public Integer first;
}
