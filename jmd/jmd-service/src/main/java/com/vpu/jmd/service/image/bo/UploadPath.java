package com.vpu.jmd.service.image.bo;

import lombok.Data;

/**
 *
 * @author 新国
 *
 */
@Data
public class UploadPath {
	public String relativeFilePath;
	public String relativeDirectory;
	public String fullPath;
	public String fullDirectory;
	public String type;
	public String filname;
	public String extension;
	private String imageUrl;
	public Object into(Class<CropImageParam> class1) {
		// TODO Auto-generated method stub
		return null;
	}
};
