package com.vpu.mp.service.pojo.shop.image;

import java.sql.Timestamp;

import com.vpu.mp.service.foundation.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author 新国
 *
 */
@Data
@NoArgsConstructor
public class ImageListQueryParam {

	/**
	 * 页码
	 */
	public Integer page;
	/**
	 * 分组id
	 */
	public Integer imgCatId;
	/**
	 * 关键词
	 */
	public String keywords;
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
