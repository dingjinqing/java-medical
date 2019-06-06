<div class="row_item m_group_draw">
    <div class="no_use" style="display: none;">
        <img src="http://${image_domain!}/image/admin/no_drag_use.png" />该模块仅高级版和旗舰版可用
        <img class="del_img" src="http://${image_domain!}/image/admin/icon_delete.png" style="float: right;margin: 10px 10px 0 0;cursor:pointer;" />
    </div>
    <div  class="center_pin_draw">
        <div class="pin_group">
            <p class="group_name">拼团抽奖</p>
            <p class="group_time">距结束仅剩:3天23小时35分</p>
        </div>
    </div>
    <div class="item_module_title">
        <span>拼团抽奖</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
</div>
<div class="data_item d_m_group_draw edit_group_draw_module">
    <div class="group_edit_module clearfix">
        <h2>拼团抽奖</h2>
        <div class="fl group_select clearfix">
            <span style="float: left;">活动标题：</span>
            <div class="set_names" style="float: left">
                <div style="margin-bottom: 10px">
                    <input type="radio" name="name_set" value="0">默认：拼团抽奖
                </div>
                <div>
                    <input type="radio" name="name_set" value="1">自定义：<input type="text" maxlength="8" name="group_draw_name"><br>
                    <text style="color: #999;">最多可输入8个字，为空则不显示</text>
                </div>
            </div>
        </div>
        <div class="fl group_select">
            <span style="display: inline-block;">活动有效期：</span>
            <input type="radio" name="show_clock" value="0">隐藏
            <input type="radio" name="show_clock" value="1" style="margin-left: 8px">显示
        </div>
        <div class="fl group_select">
            <span style="display: inline-block;">活动底图：</span>
            <input type="radio" name="module_bg" value="0" onclick="m_group_draw.if_bgshow(this)">默认底图
            <input type="radio" name="module_bg" value="1" style="margin-left: 8px" onclick="m_group_draw.if_bgshow(this)">自定义
        </div>
        <div class="fl group_select add_bg_img">
            <span style="float: left;"></span>
            <div class="add_bgs" style="float: left;">
                <div style="color: #5a8bff;margin: 27px 0 5px 0;"><img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">添加一个背景图</div>
                <div style="color: #999;font-size: 12px">建议宽度720像素以内，高度260像素以内</div>
                <img class="pin_ig" />
                <div class="change-img2" >更换图片</div>
            </div>
        </div>
        <div class="fl group_select">
            <span style="float: left;">字体颜色：</span>
            <div class="font_colors" style="float: left;">
                <input type="color" value="#ffffff" name="font_color">
                <input type="button" class="chongzhi" onclick="$(this).prev().val('#ffffff');manager.change_show_module();" value="重置">
            </div>

        </div>
        <div class="fl group_select">
            <span><em>*</em>添加拼团抽奖活动：</span>
            <select name="group_draw_id" id="group_draw_select"></select>
        </div>
    </div>

    {{--<div style="margin-top: 15px;">--!}
        {{--<input class="btn btn-primary queren group_btn" type="button" id="ok" name="ok" value="确定">--!}
    {{--</div>--!}
</div>