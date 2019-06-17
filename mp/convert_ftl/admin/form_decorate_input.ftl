
/**
* Created by PhpStorm.
* User: zuodaoqiang,lianghong
* Date: 2018/5/22
* Time: 13:52
*/
{{--姓名模块--!}
<div class="row_item m_input_name">
    <div class="name_module">
        <b class="module-star">*</b>
        <img src="http://${image_domain!}/image/admin/shop_deco/name_change.png" class="image">
        <span class="name_title">姓名</span>
        <input class="name_title_place" placeholder="请输入姓名" type="text" readonly="readonly">
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_input_name edit_name_modules">
    <div class="name_edit_module" style="position:relative;">
        <table>
        <tr>
            <td>标题文字:</td>
            <td>
                <input type="text" id="form_title" maxlength=20 size="34" value="姓名">
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <span class="img_tishi">最多可输入20个字</span>
            </td>
        </tr>
        <tr>
            <td>展现形式:</td>
            <td>
                <input type="radio" name="name_image_type" value="1">有图标
                <input type="radio" name="name_image_type" value="0" class="have_margin">无图标
            </td>
        </tr>
        <tr class="tr_image">
            <td style="vertical-align: top!important;">图标:</td>
            <td>
                <div class="add_image deco_add_img">
                    <div class="click_to_change">更换图标</div>
                    <img id="name_image">
                </div>
            </td>
        </tr>
        <tr class="tr_image">
            <td></td>
            <td><span class="img_tishi">建议尺寸：36X36</span></td>
        </tr>
        <tr>
            <td  style="position: relative;top: 2px;">条件验证:</td>
            <td><input type="checkbox" name="confirm" id="confirm" value="1">必填</td>
        </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>


{{--手机号模块--!}
<div class="row_item m_input_mobile">
    <div class="mobile_module">
        <b class="module-star">*</b>
        <img src="http://${image_domain!}/image/admin/shop_deco/mobile_change.png" class="image">
        <span class="mobile_title">手机号</span>
        <input class="mobile_title_place" placeholder="请输入手机号" readonly="readonly">
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_input_mobile edit_name_modules">
    <div class="name_edit_module" style="position:relative;">
        <table>
            <tr>
                <td>标题文字:</td>
                <td><input type="text" id="form_title" maxlength=20 size="34" value="手机号"></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <span class="img_tishi">最多可输入20个字</span>
                </td>
            </tr>
            <tr>
                <td>展现形式:</td>
                <td>
                    <input type="radio" name="mobile_image_type" value="1">有图标
                    <input type="radio" name="mobile_image_type" value="0" class="have_margin" >无图标
                </td>
            </tr>
            <tr class="tr_image">
                <td style="vertical-align: top!important;">图标:</td>
                <td>
                    <div class="add_image deco_add_img">
                        <div class="click_to_change">更换图标</div>
                        <img id="mobile_image">
                    </div>
                </td>
            </tr>
            <tr class="tr_image">
                <td></td>
                <td><span class="img_tishi">建议尺寸：36X36</span></td>
            </tr>
            <tr>
                <td  style="position: relative;top: 2px;">条件验证:</td>
                <td><input type="checkbox" name="confirm" id="confirm" >必填</td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>

{{--邮箱模块--!}
<div class="row_item m_input_email">
    <div class="email_module">
        <b class="module-star">*</b>
        <img src="http://${image_domain!}/image/admin/shop_deco/email_change.png" class="image">
        <span class="email_title">邮箱</span>
        <input class="email_title_place" placeholder="请输入邮箱" readonly="readonly">
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_input_email edit_name_modules">
    <div class="name_edit_module" style="position:relative;">
        <table>
        <tr>
            <td>标题文字:</td>
            <td><input type="text" id="form_title" maxlength=20 size="34" value="邮箱"></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <span class="img_tishi">最多可输入20个字</span>
            </td>
        </tr>
        <tr>
            <td>展现形式:</td>
            <td>
                <input type="radio" name="email_image_type" value="1">有图标
                <input type="radio" name="email_image_type" value="0" class="have_margin" >无图标
            </td>
        </tr>
        <tr class="tr_image">
            <td>图标:</td>
            <td>
                <div class="add_image deco_add_img">
                    <div class="click_to_change">更换图标</div>
                    <img id="email_image">
                </div>
            </td>
        </tr>
        <tr class="tr_image">
            <td></td>
            <td><span class="img_tishi">建议尺寸：36X36</span></td>
        </tr>
        <tr>
            <td style="position: relative;top: 2px;">条件验证:</td>
            <td><input type="checkbox" name="confirm" id="confirm" value="1">必填</td>
        </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>


{{--输入框模块--!}
<div class="row_item m_input_text">
    <div class="email_module duohangheng">
        <b class="module-star">*</b><div class="email_title" style="background: #f5f5f5">输入框</div>
        <textarea class="input_box" placeholder="请输入内容" readonly="readonly" style="padding-top: 5px"></textarea>
    </div>
    <div class="email_module hengxiang clearfix">
        <b class="confirm module-star">*</b><div class="email_title">输入框</div>
        <input class="input_box" placeholder="请输入内容" readonly="readonly"></input>
    </div>
    <div class="email_module zongxaing">
        <b class="confirm module-star">*</b><div class="email_title" style="background: #f5f5f5">输入框</div>
        <input class="input_box" placeholder="请输入内容" readonly="readonly"></input>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_input_text edit_email_modules">
    <div class="email_edit_module" style="position:relative;">
        <table>
            <tr>
                <td>展现形式:</td>
                <td>
                    <input type="radio" name="show_types" value="0" >多行
                    <input type="radio" name="show_types" value="1" class="have_margin">单行
                    <input type="radio" name="show_types" value="2" class="have_margin">单行纵向
                </td>
            </tr>
            <tr>
                <td>标题文字:</td>
                <td><input type="text" id="form_title" maxlength=20 size="34" value="输入框">
                   </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="img_tishi">最多20个字</span></td>
            </tr>
            <tr>
                <td>提示语:</td>
                <td><input type="text" id="placeholder" maxlength=20 size="34" placeholder="请输入提示语">
                    </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="img_tishi">最多20个字</span></td>
            </tr>
            <tr>
                <td style="vertical-align: top !important;position: relative;top: 4px;">条件验证:</td>
                <td class="mul_dic">
                    <div><input type="checkbox" name="confirm" id="confirm" value="1">必填</div>
                    <div>至少输入<input id="least_number" placeholder="1" type="text"  onkeyup="value=value.replace(/[^\d]/g,'')">字</div>
                    <div>至多输入<input id="most_number" placeholder="500" type="text"  onkeyup="value=value.replace(/[^\d]/g,'')">字<span>不可超过500字</span></div>
                </td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>

{{--省市区模块--!}
<div class="row_item m_address">
    <div class="address_module">
        <div class="clearfix choose_area">
            <b class="module-star">*</b><div class="address_names">省/市/区</div>
            <div class="have_bg">请选择
                <img src="http://${image_domain!}/image/wxapp/right_into.png" alt="">
            </div>
        </div>
        <div class="clearfix area_detail">
            <div>详细地址</div>
            <input type="text" readonly placeholder="请填写详细地址">
        </div>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_address edit_address_modules">
    <div class="address_edit_module" style="position:relative;">
        <table>
            <tr>
                <td>标题文字:</td>
                <td><input type="text" id="form_title" maxlength=20 size="34" value="省/市/区">
                </td>
            </tr>
            <tr>
                <td></td>
                <td><span class="img_tishi">最多20个字</span></td>
            </tr>
            <tr>
                <td>展现形式:</td>
                <td>
                    <input type="radio" name="with_detail" value="0" >不带详细地址
                    <input type="radio" name="with_detail" value="1" class="have_margin">带详细地址
                </td>
            </tr>
            <tr>
                <td style="position: relative;top: 2px;">条件验证:</td>
                <td><input type="checkbox" name="confirm" id="confirm" value="1">必填</td>
            </tr>
        </table>
    </div>
    <div style="margin-top:15px;">
        <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
    </div>
</div>

