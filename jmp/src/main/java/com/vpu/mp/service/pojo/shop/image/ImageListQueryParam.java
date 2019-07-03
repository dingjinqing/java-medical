package com.vpu.mp.service.pojo.shop.image;

import java.sql.Timestamp;

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
	public Integer page;
	public Integer imgCatId;
	public Timestamp startRq;
	public Timestamp endRq;
	public Integer imgWidth;
	public Integer imgHeight;
	public String keywords;
	public Integer searchNeed;
	public Integer needImgWidth;
	public Integer needImgHeight;
	public Integer uploadSortId;
	public Integer uploadImgCatId;
	public Integer noFull;
	public String onImgCb;
	public Integer cropImgId;
	public String act;
	public String showType;
	public Integer[] cbxImg;
	public Integer[] cbxImg2;
	public Integer setCatId;
	public String opCatName;
	public Integer opCatId;
	public Integer opCatPid;
};
