package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

/**
 * @author 新国
 */
@Data
public class UploadImageParam {
    public Integer needImgWidth;
    public Integer needImgHeight;
    public Integer imgCatId = 0;
    public String uploadFileId;
};
