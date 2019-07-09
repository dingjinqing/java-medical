package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 新国
 *
 */
@Data
public class CategoryTreeItem {
	private Integer id = 0;
	private String name ;
	private Integer level=0;
	private List<CategoryTreeItem> child=new ArrayList<>();
}
