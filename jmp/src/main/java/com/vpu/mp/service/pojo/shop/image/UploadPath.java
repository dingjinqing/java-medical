package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

@Data
public class UploadPath {
	public String relativeFilePath;
	public String relativeDirectory;
	public String fullPath;
	public String fullDirectory;
	public String type;
	public String filname;
	public String extension;
};