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
public class CategoryTreeItemVO {
	private Integer id = 0;
	private String name ;
	private Integer level=1;
	private List<CategoryTreeItemVO> child=new ArrayList<>();
}
