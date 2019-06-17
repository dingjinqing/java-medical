
<div class="row_item m_magic_cube">
    <div class="magic_cube_module">
        {{--没图片时候的样式，添加图片时隐藏--!}
        <div class="no_img_style">
            点击编辑魔方
        </div>
        <table class="cube-ev-tbl"></table>
    </div>
    <div class="item_module_title">
        <span>魔方多图</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_magic_cube edit_magicube_modules">
    <div class="magic_cube_edit_module" style="position:relative;">
        <h4>魔方多图模块</h4>
        <ul class="">
            <li class="clearfix">
                <span>选择模板：</span>
                <div class="img_list clearfix">
                    <div class="twice_line" model_id="1">
                        <div></div>
                        <div></div>
                        <p>1行2个</p>
                    </div>
                    <div class="third_line" model_id="2">
                        <div></div>
                        <div></div>
                        <div></div>
                        <p>1行3个</p>
                    </div>
                    <div class="fourth_line" model_id="3">
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <p>1行4个</p>
                    </div>
                    <div class="two_lines" model_id="4">
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <p>2行2个</p>
                    </div>
                    <div class="one_two_left" model_id="5">
                        <div></div>
                        <div></div>
                        <div></div>
                        <p>1左2右</p>
                    </div>
                    <div class="one_two_top" model_id="6">
                        <div></div>
                        <div></div>
                        <div></div>
                        <p>1上2下</p>
                    </div>
                    <div class="one_three" model_id="7">
                        <div></div>
                        <div></div>
                        <div class="special_one">
                            <div style="margin-right: 6%"></div>
                            <div></div>
                        </div>
                        <p>1左3右</p>
                    </div>
                    <div class="own_define" model_id="0">
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <div></div>
                        <p>自定义</p>
                    </div>
                </div>
            </li>
            <li class="clearfix change_table_size">
                <span>魔方密度：</span>
                <div>
                    <select name="" id="select_table_size">
                        <option value="4">4X4</option>
                        <option value="5">5X5</option>
                        <option value="6">6X6</option>
                        <option value="7">7X7</option>
                    </select>
                </div>
            </li>
            <li class="clearfix">
                <span>布局：</span>
                    <div class="sth_layout">
                        <div class="magic-cube-layout">
                            <table class="cube-block-tbl">

                            </table>
                        </div>
                    </div>
            </li>
            <li class="clearfix">
                <span></span>
                <div style="color: #666;font-size: 13px">
                    选定布局区域，在下方添加图片
                </div>
            </li>
            {{--<li class="clearfix">--!}
                {{--<span style="vertical-align:middle!important;">图片间隙：</span>--!}
                {{--<div>--!}
                    {{--<input type="range" name="points" min="0" max="20" value="0" onchange="$(this).next().val($(this).val())">--!}
                    {{--<input name="image_space" type="text" value="0">--!}
                {{--</div>--!}
            {{--</li>--!}
            <li class=" clearfix" style="margin-bottom: 0">
                <table style="margin-bottom: 0" class="table table-striped cube_block_edit" cellspacing='1' cellpadding='3' hidden>
                    <tr>
                        <td style="padding-top: 20px">选择图片：</td>
                        <td style="padding-top: 20px;position:relative;padding-left: 0" class="buzhidao">
                            <div  class="first_add">
                                <img src class="added_img">
                            </div>

                            <div class="deco_add_img">
                                <a class="op-img" href="javascript:void(0)" style="background: url('/image/admin/shop_beautify/add_decorete.png') no-repeat;background-position: center center;background-size: 65% 65%;width: 100%;height: 100%;display: block;color: #f8f8f8;"></a>
                                {{--<img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">--!}
                            </div><br>
                            <span class="img-tip" style="position: absolute;top: 46px;left: 26%;"></span>
                        </td>
                    </tr>
                    <tr class="add_sthlink">
                        <td>图片跳转链接：</td>
                        <td style="padding-left: 0"><input type="text" name="jump_link" id="jump_link" maxlength=255 size="24">
                            <input type="button" value="选择链接" class="select_links" style="border-color: #eee">
                        </td>
                    </tr>
                </table>
            </li>
            <li class="clearfix">
                <div style="color: #999;text-align: left;margin-left: 3%">
                    魔方图片比例与要求的不一致会被裁剪
                </div>
            </li>
        </ul>
    </div>
</div>
