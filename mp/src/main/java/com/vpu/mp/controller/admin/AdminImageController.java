package com.vpu.mp.controller.admin;

import org.springframework.stereotype.Controller;

/**
 * 
 * @author 新国
 *
 */
@Controller
public class AdminImageController extends AdminBaseController {
	
	

//	$message = $this->processImagePost();
//    $post = $this->post();
//    $post['img_cat_id'] = $post['act'] == 'op_cat_del' ? 0 : $this->post('img_cat_id');
//    $res = saas()->shop->version->VerisonNuwShow(saas()->shop->getShopInfo($this->auth->user()['shop_id']), 'picture_num');
//    $res['self']['use'] = bcadd($this->shop()->image->getAllSize() / 1024 / 1024, 0,2);
//    $data = [
//        'title' => trans("admin/image.image_manger"),
//        'start_rq' => $this->input('start_rq'),
//        'end_rq' => $this->input('end_rq'),
//        'img_width' => $this->input('img_width'),
//        'img_height' => $this->input('img_height'),
//        'upload_img_cat_id' => $this->input('upload_img_cat_id'),
//        'img_cat_id' => $post['img_cat_id'],
//        'show_type' => $this->input('show_type') == "list" ? $this->input('show_type') : "bigimg",
//        'page' => $this->input('page'),
//        'no_full' => $this->input('no_full'),
//        'on_img_cb' => $this->input('on_img_cb'),
//        'need_img_width' => $this->input('need_img_width'),
//        'need_img_height' => $this->input('need_img_height'),
//        'search_need' => $this->input('search_need'),
//        'crop_img_id' => $this->input('crop_img_id'),
//        'upload_sort_id' => $this->input('upload_sort_id'),
//        'rows_per_page' => $this->input('rows_per_page'),
//        'keywords' => $this->input('keywords'),
//        'upload_sort_list' => $this->shop()->image->getUploadSortList(),
//        'img_cat_list' => $this->shop()->image->category->getCategoryTree(),
//        'nav_type' => 0,
//        'data_list' => $this->shop()->image->getPageList($post, $this->post("page"), $this->input('rows_per_page') ?? 20),
//        'img_cat_arr' => $this->shop()->image->category->getImageCategoryForZTree(),
//        'msg' => $message,
//        'version' => $res,
//    ];
//    return view("admin.image_manager_list", $data);
}
