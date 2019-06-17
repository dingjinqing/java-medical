<div class="row_item m_pin_integration">
    <div class="no_use" style="display: none;">
        <img src="http://${image_domain!}/image/admin/no_drag_use.png" />该模块仅高级版和旗舰版可用
        <img class="del_img" src="http://${image_domain!}/image/admin/icon_delete.png" style="float: right;margin: 10px 10px 0 0;cursor:pointer;" />
    </div>
   <div class="pin_content clearfix">
       <div style="padding-left: 0px"  class="title-content" >
           <div class="p1">
               <span class="inte_total">xx</span>积分等你拿,
           </div>
           <div class="p2">购物可抵现金！</div>
       </div>

       <div class="user_auto" hide>
         积分等你拿,购物可抵现金！
       </div>


      <div class="p3">
          <span class="limit_amount">xx</span>人瓜分<span class="inte_group">xx</span>积分
      </div>
      <div class="p4"><span class="start_time">xx</span>至<span class="end_time">xx</span></div>
   </div>
    <div class="item_module_title">
       <span>瓜分积分</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>





<div class="data_item d_m_pin_integration edit_pin_integration_modules">
    <div class="pin_integration_edit_module clearfix" style="position:relative;">
        <h2>组团瓜分积分模块</h2>
        {{--<table>--!}
            {{--<tr>--!}
                {{--<td style="vertical-align: top !important;padding: 0px 0px;width: 55px"><span style=" margin-top: 4px;margin-left: 5px;display: inline-block;">标题：</span></td>--!}
                {{--<td style="padding: 0px 0px">--!}
                    {{--<label for="origin"><input type="radio" id="origin" name="pin_title" checked value="1">默认标题：XX(瓜分总积分数量)积分等你拿,购物可抵现金！</label>--!}
                    {{--<label for="auto"><input type="radio" id="auto" name="pin_title" value="0">自定义：--!}
                        {{--<input type="text" class="autoo" maxlength="14" style="padding-left: 5px">--!}
                        {{--<span style="color: #999;font-size: 12px;">限制14字,为空不显示</span>--!}
                    {{--</label>--!}

                {{--</td>--!}
            {{--</tr>--!}
            {{--<tr>--!}
                {{--<td style="vertical-align: top !important;padding: 0px 0px;width: 73px"><span style=" margin-top: 12px;margin-left: 3px;display: inline-block;">隐藏内容：</span></td>--!}
                {{--<td style="padding: 12px 2px;">--!}
                    {{--<label for="active_y"><input type="checkbox" id="active_y" name="pin-act"  value="1"> 活动内容</label>--!}
                    {{--<label for="time_y"><input type="checkbox" id="time_y" name="pin-act" style="margin-left: 15px" value="1">有效期</label>--!}
                {{--</td>--!}
            {{--</tr>--!}
        {{--</table>--!}
        <div class="fl group_select clearfix">
            <span style="float: left;" class="gf_title">标题：</span>
            <div class="set_names" style="float: left">
                <div style="margin-bottom: 10px">
                    <label for="origin"><input type="radio" id="origin" name="pin_title" checked value="1">默认标题：XX积分等你拿,购物可抵现金！</label>
                </div>
                <div>
                    <label for="auto"><input type="radio" id="auto" name="pin_title" value="0">自定义：
                        <input type="text" class="autoo" maxlength="14" style="padding-left: 5px">
                        <span style="color: #999;font-size: 12px;">限制14字,为空不显示</span>
                    </label>
                </div>
            </div>
        </div>
        <div class="fl group_select">
            <span style="display: inline-block;" class="gf_title">隐藏内容：</span>
            <label for="active_y"><input type="checkbox" id="active_y" name="pin-act"  value="1"> 活动内容</label>
            <label for="time_y"><input type="checkbox" id="time_y" name="pin-act" style="margin-left: 15px" value="1">有效期</label>
        </div>
        <div class="fl group_select">
            <span style="display: inline-block;" class="gf_title">活动底图：</span>
            <input type="radio" name="module_bg" value="0" onclick="m_pin_integration.if_bgshow(this)">默认底图
            <input type="radio" name="module_bg" value="1" style="margin-left: 8px" onclick="m_pin_integration.if_bgshow(this)">自定义
        </div>
        <div class="fl group_select add_bg_img">
            <span style="float: left;" class="gf_title"></span>
            <div class="add_bgs" style="float: left;">
                <div style="color: #5a8bff;margin: 27px 0 5px 0;"><img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">添加一个背景图</div>
                <div style="color: #999;font-size: 12px">建议宽度720像素以内，高度300像素以内</div>
                <img class="pin_ig" />
                <div class="change-img2" >更换图片</div>
            </div>
        </div>
        <div class="fl group_select">
            <span style="float: left;" class="gf_title">字体颜色：</span>
            <div class="font_colors" style="float: left;">
                <input type="color" value="#ffffff" name="font_color">
                <input type="button" class="chongzhi" onclick="$(this).prev().val('#ffffff');manager.change_show_module();" value="重置">
            </div>

        </div>
        <div class="fl group_select">
            <span><em style="color: red;padding-right: 5px" class="gf_title">*</em>添加组团瓜分积分活动:</span>
            <select name="pin_integration_act" id="pin_integration_act">
                <option value="0">select</option>
            </select>
        </div>
    </div>
    {{--<div style="margin-top:20px;">--!}
        {{--<input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">--!}
    {{--</div>--!}
</div>