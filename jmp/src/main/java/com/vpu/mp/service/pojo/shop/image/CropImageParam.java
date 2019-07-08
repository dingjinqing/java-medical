package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

/**
 * 
 * @author 新国
 *
 */
@Data
public class CropImageParam {
	public String remoteImgPath;
	public Integer cropWidth;
	public Integer cropHeight;
	public Integer x;
	public Integer y;
	public Integer w;
	public Integer h;
	public Double imgScaleW;
	public Integer imgCatId;
	public Integer remoteImgId;
	public String imageCrop;
};