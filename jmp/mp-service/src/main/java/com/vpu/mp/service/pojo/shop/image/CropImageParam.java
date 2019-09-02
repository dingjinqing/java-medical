package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 *
 * @author 新国
 *
 */
@Data
public class CropImageParam {
	/**
	 * 图片存放地址
	 */
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
	private Integer size;
};
