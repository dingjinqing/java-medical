
<div class="row_item m_west_street" not_allow_del>
    <div class="west_street_module" style="position:relative;overflow:visible;">
        <img class="tpl_img"/>

        <div class="nav_div west_street_nav_div">
            <div class="west_street_title"></div>
            <table style="margin:9px;" id="west_street_content" cellspacing="">
                <tr>
                    <td rowspan="2">
                        <img class="west_street_img" width="132" height="205"/>
                    </td>
                    <td>
                        <img class="west_street_img" width="132" height="96"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <img class="west_street_img" width="132" height="96"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <img class="west_street_img" width="274" height="127"/>
                    </td>
                </tr>
                <tr>
                    <td style="padding-right:10px;">
                        <img class="west_street_img" width="132" height="96"/>
                    </td>
                    <td><img class="west_street_img" width="132" height="96"/></td>
                </tr>
            </table>
        </div>

    </div>
    <div class="item_operation"></div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_west_street">
    <div class="west_street_edit_module" style="position:relative;">
        <p class="nav_title">主图设置</p>
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:80px;">背景图片</td>
                <td><input type="button" value="更换图片" class="add_image">
                    &nbsp;<img class="bg_img" style="max-width:100px; max-height:100px;">尺寸：640*1136
                </td>
            </tr>
            <tr>
                <td>背景链接</td>
                <td><input type="text" name="bg_link" id="bg_link" maxlength=255 size="24" required/>
                    <input type="button" value="选择链接" class="select_links">
                </td>
            </tr>
        </table>
        <p class="nav_title">导航链接</p>

        <div class="nav_list"></div>
    </div>
</div>

<div class="box nav_data nav_data_tpl west_street_nav_item">
    <table cellspacing='1' cellpadding='3'>
        <tr>
            <td style="width:80px;">标题名称</td>
            <td><input type="text" name="nav_title" id="nav_title" maxlength=8 size="15"><span class="tips">最多8个字</span>
            </td>
        </tr>
        <tr>
            <td>导航链接</td>
            <td><input type="text" name="nav_link" id="nav_link" maxlength=255 size="24">
                <input type="button" value="选择链接" class="select_links">
            </td>
        </tr>
        <tr>
            <td>背景图</td>
            <td><input type="button" value="更换图片" class="add_bg_image">&nbsp;
                <img class="nav_bg_img" style="max-width:90px; max-height:100px;"/>
                <span class="tips size_tips" id="size_tips"></span>
            </td>
        </tr>
    </table>
</div>