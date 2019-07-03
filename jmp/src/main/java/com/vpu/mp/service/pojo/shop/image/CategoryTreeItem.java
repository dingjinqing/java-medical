package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class CategoryTreeItem {
	public Integer id = 0;
	public String name = "";
	public Boolean open = true;
	public Integer pId = 0;
}
