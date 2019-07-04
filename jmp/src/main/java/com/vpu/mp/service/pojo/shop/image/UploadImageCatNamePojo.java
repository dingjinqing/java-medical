package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class UploadImageCatNamePojo extends UploadedImagePojo {
	 private String    imgCatName;
}
