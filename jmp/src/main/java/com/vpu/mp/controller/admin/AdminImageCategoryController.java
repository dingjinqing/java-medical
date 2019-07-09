package com.vpu.mp.controller.admin;


import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.pojo.shop.image.UploadedImageCategoryParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图片分组列表
 *
 * @author 孔德成
 * @date 2019/7/8 13:59
 */
@RequestMapping("/api")
@RestController
public class AdminImageCategoryController extends AdminShopController {


    /**
     * 保存
     *
     * @param param
     * @return
     */
    @PostMapping("/admin/ImageCategory/add")
    public JsonResult addImageCategory(@RequestBody UploadedImageCategoryParam param) {
        shop().imageCatgory.addCategory(param);
        return success();
    }


    /**
     * 删除
     *
     * @param catId
     * @return
     */
    @PostMapping("/admin/ImageCategory/delete")
    public JsonResult deleteImageCategory( Integer catId) {
        shop().imageCatgory.removeCategory(catId);
        return success();
    }


    /**
     * 更新图片分组节点
     *
     * @return
     */
    @PostMapping("/admin/imageCategory/move")
    public JsonResult moveImageCategory(Integer catId, Integer parantCatId) {
        shop().imageCatgory.moveCategory(catId,parantCatId);
        return success();
    }

    /**
     * 更新分组名称
     *
     * @param rename
     * @param catId
     * @return
     */
    @PostMapping("/admin/imageCategory/rename")
    public JsonResult renameImageCategory(String rename, Integer catId) {
        shop().imageCatgory.setCategoryName(catId, rename);
        return success();
    }


    /**
     * 查询图片分组列表
     *
     * @return
     */
    @PostMapping("/admin/imageCategory/list")
    public JsonResult getImageCategoryList() {
        return success(shop().imageCatgory.getImageCategoryForZTree(0));
    }


}
