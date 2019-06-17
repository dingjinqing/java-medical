
<div class="row_item m_shop" not_allow_del>
<div class="shop_module" style="position:relative;">
    <div class="shop_bg"><img src="http://${image_domain!}/image/admin/shop_beautify/beau3.png"/></div>
    <div class="shop_name">${shop->shop_name!}</div>
    <div class="shop_desc">为您的店铺发些公告吧~</div>
    <div class="shop_logo">
        <#if (!$shop->shop_avatar)
            <img src="http://${image_domain!}/image/admin/shop_beautify/shop_deco_icon.png"/>
        <#else>
            <img src="${shop->shop_avatar!}"/>\
        </#if>
    </div>
</div>
<div class="item_module_title">
    <span>店招设置</span>
</div>
<div class="item_operation">
    <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
    <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
    <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
</div>
<div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_shop edit_m_shop">
    <div class="shop_edit_module" style="position:relative;">
        <h2>
            店招模块
            <span>背景图片建议尺寸：640*300</span>
        </h2>
        <table class="auto_recommend" cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:110px;">店铺名称：</td>
                <td><input type="text" name="shop_name" value="${shop->shop_name!}" id="shop_name" maxlength=20 size="34">
                    <span style="color:#9a9a9a">最多20个字</span></td>
            </tr>
            <tr>
                <td style="width:110px;">店铺公告：</td>
                <td><input type="text" name="shop_notice" id="shop_notice" maxlength=30 size="34">
                    <span style="color:#9a9a9a;">最多30个字</span></td>
            </tr>
            <tr>
                <td style="width:110px;">背景图片：</td>
                <td>
                    默认背景选择：
                    <select id="select_pic">
                        <option path="/image/admin/shop_beautify/beau1.png" value="/image/admin/shop_beautify/beau1.png">背景图1</option>
                        <option path="/image/admin/shop_beautify/beau2.png" value="/image/admin/shop_beautify/beau2.png">背景图2</option>
                        <option path="/image/admin/shop_beautify/beau3.png" value="/image/admin/shop_beautify/beau3.png">背景图3</option>
                        <option path="/image/admin/shop_beautify/beau4.png" value="/image/admin/shop_beautify/beau4.png">背景图4</option>
                        <option path="/image/admin/shop_beautify/beau5.png" value="/image/admin/shop_beautify/beau5.png">背景图5</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:110px;"></td>
                <td>
                    <div class="btn-group">
                        <span style="float: left;vertical-align: top;margin-right: 6px">上传背景图片：</span>
                        <input class="btn btn-default" type='button' id="upload_bg_file" name="upload_bg_file" value="">
                        <span class="bg_img_container">
                             <img class="shop_bg_img" style="max-width:200px; max-height:100px;">
                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_img_icon">
                        </span>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    {{--<div style="margin-top:15px;">--!}
        {{--<input class="btn btn-primary queren " type='button' id="ok" name="ok" value="确定">--!}
    {{--</div>--!}
</div>


