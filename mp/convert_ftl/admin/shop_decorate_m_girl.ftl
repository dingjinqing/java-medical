
<div class="row_item m_girl" not_allow_del>
    <div class="girl_module" style="position:relative;">
        <img class="tpl_img" src="http://${image_domain!}/image/admin/shop_beautify/girl_bg.png">

        <div class="nav_div">
            <table>
                <tr class="nav_list"></tr>
            </table>
        </div>
    </div>
    <div class="item_operation">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<table>
    <tr>
        <td class="nav_item_tpl">
            <div class="nav_item">
                <img class="bg_img" src="" style="max-width:100%;">

                <div class="nav_item_info">
                    <div class="nav_item_title">新鲜资讯</div>
                    <div class="nav_item_line "></div>
                    <div class="nav_item_logo"><img class="logo_img" src="" style="max-width:100%;"></div>
                    <div class="nav_item_sumary">第一时间了解咨询.</div>
                </div>
            </div>
        </td>
    </tr>
</table>


<div class="data_item d_m_girl">
    <div class="girl_edit_module" style="position:relative;">
        <p class="nav_title">主图设置</p>
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:80px;">背景图片</td>
                <td><input type="button" value="添加图片" class="add_image btn btn-default">
                    &nbsp;<img class="bg_img" style="max-width:100px; max-height:100px;">尺寸：640*1136
                </td>
            </tr>
            <tr>
                <td>背景链接</td>
                <td><input type="text" name="bg_link" id="bg_link" maxlength=255 size="24">
                    <input type="button" value="选择链接" class="select_links">
                </td>
            </tr>
        </table>
        <p class="nav_title">导航链接</p>

        <p><input type="button" value="添加导航" name="add_nav" class="add_nav"></p>

        <div class="nav_list"></div>
    </div>
</div>

<div class="box nav_data nav_data_tpl">
    <div class="nav_close"></div>
    <table cellspacing='1' cellpadding='3'>
        <tr>
            <td style="width:80px;">小标题</td>
            <td><input type="text" name="nav_title" id="nav_title" maxlength=5 size="10"><span class="tips">最多5个字</span>
            </td>
        </tr>
        <tr>
            <td>导航链接</td>
            <td><input type="text" name="nav_link" id="nav_link" maxlength=255 size="24">
                <input type="button" value="选择链接" class="select_links">
            </td>
        </tr>

        <tr>
            <td style="width:80px;">小图标</td>
            <td><input type="button" value="添加图片" class="add_logo_image">&nbsp;
                <img class="nav_logo_img" style="max-width:100px; max-height:100px;">
                <a class="del_logo_img" href="javascript:void(0);">删除</a>
                <span class="tips">尺寸为50x50</span>
            </td>
        </tr>

        <tr>
            <td style="width:80px;">简介</td>
            <td><input type="text" name="nav_sumary" id="nav_sumary" maxlength=18 size="20"> <span class="tips">最多18个字</span>
            </td>
        </tr>
        <tr>
            <td>背景图</td>
            <td><input type="button" value="添加图片" class="add_bg_image">&nbsp;
                <img class="nav_bg_img" style="max-width:100px; max-height:100px;">
                <a class="del_bg_img" href="javascript:void(0);">删除</a>
                <span class="tips">尺寸为172x276</span>
            </td>
        </tr>
    </table>
</div>

