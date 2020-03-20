package com.vpu.mp.controller.wxapp;

import com.vpu.mp.db.shop.tables.records.UploadedVideoRecord;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.video.UploadVideoParam;
import com.vpu.mp.service.pojo.shop.video.UploadedVideoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @author 孔德成
 * @date 2020/3/20
 */
@RestController
@RequestMapping("/api/wxapp/video")
public class WxAppVideoController extends WxAppBaseController {


    /**
     * 上传单视频
     * @param param
     * @param file
     * @return
     * @throws IOException
     * @throws Exception
     */
    @PostMapping(value = "/upload/one")
    protected JsonResult uploadOneFile(UploadVideoParam param, Part file)  {
        Integer userId = wxAppAuth.user().getUserId();
        // 校验
        ResultMessage jsonResultCode = null;
        try {
            jsonResultCode = shop().video.validVideoParam(param, file);
        } catch (IOException e) {
            e.printStackTrace();
            return fail();
        }
        if (!jsonResultCode.getFlag()) {
           return this.fail(jsonResultCode);
        }
        // 上传又拍云
        String filename = "";
        String dispostion = file.getHeader("content-disposition");
        String[] segs = dispostion.split(";");
        for (String seg : segs) {
            String[] values = seg.trim().split("=");
            if (values[0].equals("filename")) {
                filename = values[1].trim();
                filename = filename.substring(1, filename.length() - 1);
                break;
            }
        }
        String ext = shop().video.getFileType(filename);
        UploadedVideoRecord record = null;
        try {
            record = shop().video.uploadVideoFile(
                    filename,
                    (new Long(file.getSize())).intValue(),
                    file.getInputStream(),
                    ext,
                    param.getVideoCatId(),
                    userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        UploadedVideoVo uploadedVideoVo = record.into(UploadedVideoVo.class);
        return this.success(uploadedVideoVo);
    }

}
