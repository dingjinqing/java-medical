
/**
 * Created by PhpStorm.
 * User: lianghong
 * Date: 2018/5/23
 * Time: 14:25
 */
{{--性别模块--!}
<div class="row_item m_sex" style="margin-top: 10px">
    <div class="sex_module">
        {{--横向--!}
        <div class="col_style clearfix">
            <b class="module-star">*</b><div class="email_title sex_title">性别</div>
            <div>
                <input type="radio" disabled="disabled" checked>男
                <img src="http://${image_domain!}/image/admin/shop_deco/select_yes.png" alt="">女
            </div>
        </div>
        {{--纵向--!}
        <div class="row_style clearfix">
            <b class="module-star">*</b><div class="xingbie sex_title">性别</div>
            <div><input type="radio" disabled="disabled">男</div>
            <div><img src="http://${image_domain!}/image/admin/shop_deco/select_yes.png" alt="">&nbsp;女</div>
        </div>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_sex edit_sex_modules">
    <div class="sex_edit_module" style="position:relative;">
        <table>
        <tr>
            <td>展示形式:</td>
            <td>
                <input type="radio" name="sex_show_types" value="0">横向
                <input type="radio" name="sex_show_types" value="1" class="have_margin">纵向
            </td>
        </tr>
        <tr>
            <td>标题文字:</td>
            <td>
                <input type="text" id="form_title" maxlength=20 size="34" value="性别">
            </td>
        </tr>
        <tr>
            <td></td>
            <td><span class="img_tishi">最多可输入20个字</span></td>
        </tr>
        <tr>
            <td style="position: relative;top: 3px;">条件验证:</td>
            <td><input type="checkbox" name="confirm" id="confirm" >必填</td>
        </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>
{{--性别模块结束--!}

{{--下拉模块--!}
<div class="row_item m_slide">
    <div class="slide_module clearfix">
        <b class="module-star">*</b><div class="slide_names">下拉</div>
       <div class="have_bg">请选择
           <img src="http://${image_domain!}/image/wxapp/right_into.png" alt="">
       </div>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_slide edit_slide_modules">
    <div class="slide_edit_module" style="position:relative;">
        <table>
            <tr>
                <td>标题文字:</td>
                <td>
                    <input type="text" id="form_title" maxlength=20 size="34" value="下拉">
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="img_tishi">最多可输入20个字</span></td>
            </tr>
            <tr class="select_row select_tr_1">
                <td>选项设置:</td>
                <td>
                    <div class="first_option">
                        <span>选项</span>
                        <input type="text" name="selects" sel_id=1 value="选项1">
                    </div>
                </td>
            </tr>
            <tr class="select_row copy_this" style="display: none">
                <td></td>
                <td>
                    <div class="first_option">
                        <span>选项</span>
                        <input type="text" sel_id="" value="选项">
                        <a class="del_sel_tr" sel_id="" href="javascript:void(0)">删除</a>
                    </div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="add_opn">
                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                        添加选项
                    </div>
                </td>
            </tr>
            <tr>
                <td style="position: relative;top: 3px;">条件验证:</td>
                <td><input type="checkbox" name="confirm" id="confirm" >必填</td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>
{{--下拉模块结束--!}

{{--选项模块--!}
<div class="row_item m_choose">
    <div class="choose_module clearfix">
       {{--单选--!}
        <div class="radio_style">
            <b class="module-star">*</b><div class="choose_name">选项</div>
            <div class="radio_1">
                <img src="http://${image_domain!}/image/admin/shop_deco/select_yes.png" alt="">
                <span class="row_value">选项1</span>
            </div>
            <div class="copy_row radio_2" style="display: none">
                <img src="http://${image_domain!}/image/admin/shop_deco/select_no.png" alt="">
                <span class="row_value">选项</span>
            </div>
        </div>
        {{--多选--!}
        <div class="check_style">
            <b class="module-star">*</b><div class="choose_name">>选项</div>
            <div class="check_1">
                <img src="http://${image_domain!}/image/admin/shop_deco/check_yes.png" alt="">
                <span class="row_value">选项1</span>
            </div>
            <div class="copy_row check_2"  style="display: none">
                <img src="http://${image_domain!}/image/admin/shop_deco/check_no.png" alt="">
                <span class="row_value">选项</span>
            </div>
        </div>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_choose edit_choose_modules">
    <div class="choose_edit_module" style="position:relative;">
        <table>
            <tr>
                <td>展示形式:</td>
                <td>
                    <input type="radio" name="choose_show_types" value="0">单选
                    <input type="radio" name="choose_show_types" value="1" class="have_margin">多选
                </td>
            </tr>
            <tr>
                <td>标题文字:</td>
                <td>
                    <input type="text" id="form_title" maxlength=20 size="34" value="选项">
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="img_tishi">最多可输入20个字</span></td>
            </tr>
            <tr class="select_row select_tr_1">
                <td>选项设置:</td>
                <td>
                    <div class="first_option">
                        <span>选项</span>
                        <input type="text" name="selects" sel_id=1 value="选项1">
                    </div>
                </td>
            </tr>
            <tr class="select_row copy_this" style="display: none">
                <td></td>
                <td>
                    <div class="first_option">
                        <span>选项</span>
                        <input type="text" sel_id="" value="选项">
                        <a class="del_sel_tr" sel_id="" href="javascript:void(0)">删除</a>
                    </div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="add_opn">
                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                        添加选项
                    </div>
                </td>
            </tr>
            <tr>
                <td style="position: relative;top: 3px;">条件验证:</td>
                <td><input type="checkbox" name="confirm" id="confirm" >必填</td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>
{{--选项模块结束--!}


{{--日期模块--!}
<div class="row_item m_dates">
    <div class="dates_module clearfix">
        <b class="module-star">*</b><div class="dates_names">日期</div>
        <div class="have_bg">请选择
            <img src="http://${image_domain!}/image/wxapp/right_into.png" alt="">
        </div>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_dates edit_date_modules">
    <div class="date_edit_module" style="position:relative;">
        <table>
            <tr>
                <td>标题文字:</td>
                <td>
                    <input type="text" id="form_title" maxlength=20 size="34" value="日期">
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="img_tishi">最多可输入20个字</span></td>
            </tr>
            <tr>
                <td>时间格式:</td>
                <td>
                    <input type="radio" name="date_types" value="0">年-月-日
                    {{--<input type="radio" name="date_types" value="1" class="iam_lasr">年-月-日 时-分-秒--!}
                </td>
            </tr>
            <tr>
                <td style="position: relative;top: 3px;">条件验证:</td>
                <td><input type="checkbox" name="confirm" id="confirm" >必填</td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>
{{--日期模块结束--!}

{{--图片模块--!}
<div class="row_item m_imgs">
    <div class="img_module clearfix">
        <b class="module-star">*</b><div class="mod_title">图片上传</div>
        <div class="uoload_jia">
            <img src="http://${image_domain!}/image/admin/shop_deco/upload_deco.png" alt="">
        </div>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_imgs edit_imgs_modules">
    <div class="img_edit_module" style="position:relative;">
        <table>
            <tr>
                <td>标题文字:</td>
                <td>
                    <input type="text" id="form_title" maxlength=20 size="34" value="图片上传">
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="img_tishi">最多可输入20个字</span></td>
            </tr>
            <tr>
                <td>上传数量:</td>
                <td>
                    最多
                    <select name="max_number">
                        <option value="6" selected>6</option>
                        <option value="5">5</option>
                        <option value="4">4</option>
                        <option value="3">3</option>
                        <option value="2">2</option>
                        <option value="1">1</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>图片尺寸:</td>
                <td>
                    <input type="radio" name="size_types" value="0">不限制
                    <input type="radio" name="size_types" value="1" class="iam_lasr">限制
                </td>
            </tr>
            <tr class="image_size">
                <td></td>
                <td class="size_limit">
                    <input type="text" name="width_size"> * <input type="text" name="height_size">
                </td>
            </tr>
            <tr>
                <td style="position: relative;top: 3px;">条件验证:</td>
                <td><input type="checkbox" name="confirm" id="confirm" >必填</td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>
{{--图片模块结束--!}