package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	public Integer page;
	/**
	 * 分组id
	 */
	@NonNull
	public Integer imgCatId;
	/**
	 * 关键词
	 */
	public String keywords;
	/**
	 * 每页显示的个数
	 */
	public  Integer pageRows;
	/**
	 * 更多需求 1.开启 0关闭
	 */
	public Integer searchNeed;
	/**
	 * 宽度要求 （更多）
	 */
	public Integer needImgWidth;
	/**
	 * 高度需求（更多）
	 */
	public Integer needImgHeight;
	/**
	 * 排序
	 */
	public Integer uploadSortId;
};
