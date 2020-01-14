package com.vpu.mp.controller.wxapp;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.image.UploadImageParam;
import com.vpu.mp.service.pojo.shop.image.UploadPath;
import com.vpu.mp.service.pojo.shop.image.UploadedImageVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Part;
import java.io.IOException;

/**
 * 图片
 *
 * @author liangchen 2019.10.30
 */
@RestController
@RequestMapping("/api/wxapp/image")
public class WxAppImageController extends WxAppBaseController {

  /**
   * 上传单张图片
   *
   * @param param 图片信息
   * @param file 图片文件
   * @throws IOException 异常
   * @throws Exception 异常
   */
  @PostMapping("/upload")
  protected JsonResult upload(UploadImageParam param, Part file) throws IOException, Exception {
      logger().info("上传单张图片"+file);
    // 校验
    ResultMessage jsonResultCode = shop().image.validImageParam(param, file);
    if (!jsonResultCode.getFlag()) {
      return this.fail(jsonResultCode);
    }
      logger().info("校验结束");
    UploadPath uploadPath = shop().image.getImageWritableUploadPath(file.getContentType());
      logger().info("开始上传又拍云");
    // 上传又拍云
    boolean ret =
        shop().image.uploadToUpYunBySteam(uploadPath.relativeFilePath, file.getInputStream());
    if (ret) {
      // 保存记录
      UploadedImageVo uploadedImageVo =
          shop().image.addImageToDb(param, file, uploadPath).into(UploadedImageVo.class);
      logger().info("上传完成");
      return this.success(uploadedImageVo);
    }
    return fail(JsonResultCode.CODE_IMGAE_UPLOAD_FAILED);
  }
}
