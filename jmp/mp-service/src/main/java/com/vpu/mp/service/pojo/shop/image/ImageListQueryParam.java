package com.vpu.mp.service.pojo.shop.image;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *
 * @author 新国
 *
 */
@Getter
@Setter
public class ImageListQueryParam {

	/**
	 * 页码
	 */
	private Integer page;
	/**
	 * 分组id
	 */
	@NonNull
	private Integer imgCatId;
	/**
	 * 关键词
	 */
	private String keywords;
	/**
	 * 每页显示的个数
	 */
	private  Integer pageRows;
	/**
	 * 更多需求 1.开启 0关闭
	 */
	private Integer searchNeed;
	/**
	 * 宽度要求 （更多）
	 */
	private Integer needImgWidth;
	/**
	 * 高度需求（更多）
	 */
	private Integer needImgHeight;
	/**
	 * 排序
	 */
	private Integer uploadSortId;
};
