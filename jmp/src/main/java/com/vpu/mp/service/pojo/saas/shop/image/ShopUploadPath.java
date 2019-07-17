package com.vpu.mp.service.pojo.saas.shop.image;

import com.vpu.mp.service.pojo.shop.image.CropImageParam;
import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/7/17 10:29
 */
@Data
public class ShopUploadPath {
    public String relativeFilePath;
    public String relativeDirectory;
    public String fullPath;
    public String fullDirectory;
    public String type;
    public String filname;
    public String extension;
    public Object into(Class<CropImageParam> class1) {
        // TODO Auto-generated method stub
        return null;
    }
}
