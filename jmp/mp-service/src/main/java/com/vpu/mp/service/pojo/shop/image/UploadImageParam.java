package com.vpu.mp.service.pojo.shop.image;

import lombok.Data;

/** @author 新国 */
@Data
public class UploadImageParam {
  /** 图片宽度 */
  public Integer needImgWidth;
  /** 图片高度 */
  public Integer needImgHeight;
  /** 图片种类 -1：用户上传 */
  public Integer imgCatId = 0;
  /** 用户id */
  public Integer userId;

  public String uploadFileId;
};
