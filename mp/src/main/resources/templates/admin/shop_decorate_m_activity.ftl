<style>
    .phone_module .phone_number {
        width: 100%;
        height: 30px;
        line-height: 30px;
    }
    .integral_select_img {
        width: 40px;
        height: 40px;
        border: 1px solid #eee;
        float: left;
    }
    /*.integral_goods_tb tr, .integral_goods_tb td, integral_goods_tb tr th{
        border: 1px solid #ccc;
    }*/
    .integral_default_img img {
        max-width: 100%;
        max-height: 100%;
    }
    .official_accounts{
        padding: 5px;
    }
    .official_accounts p.mini_title{
        font-size: 13px;
        color: #cdcdcd;
    }
    .mini_info_content img{
        border-radius: 50%;
    }
    .mini_info_content span{
        display: block;
        white-space: nowrap;
        text-overflow:ellipsis;
        overflow:hidden;
    }
    .mini_info_content span:first-child{
        font-size: 14px;
        color:#000;
    }
    .mini_info_content span:last-child{
        font-size: 12px;
        color:#979797;
    }
    .integral_select_name{
        overflow: hidden;
        word-break: break-all;
        text-overflow: -o-ellipsis-lastline;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .integral_original{
        white-space: nowrap;
        text-align: left;
        margin-top: 5px;
    }
    .integral_goods_tb{
        margin:0;
    }
</style>
<div class="row_item m_phone" style="background-color: transparent">
    <div class="phone_module">
        <div class="phone_number"></div>
        <div class="phone-icon"></div>
    </div>
    <div class="item_module_title">
        <span>电话</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_phone edit_phone_module">
    <div class="phone_edit_module" style="position:relative;">
        <h2>电话号码模块</h2>

        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:80px;padding-right: 0">电话号码：</td>
                <td style="padding-left: 0"><input type="text" name="title" id="title" maxlength=14 size="34" value="15093037027">
                    <span style="color:#a7a7a7;">最多14个字</span></td>
            </tr>
            <tr>
                <td style="width:80px;padding-right: 0">选择样式：</td>
                <td style="padding-left: 0">
                    <label for="normala">
                        <input type="radio" name="show_type" value="0" onclick="m_phone.chooseShowType(this)">普通
                    </label>
                    <label for="specials">
                        <input type="radio" name="show_type" value="1" style="margin-left: 20px" onclick="m_phone.chooseShowType(this)">悬浮
                    </label>
                </td>
            </tr>
            <tr id="normala">
                <td style="width:80px;padding-right: 0"></td>
                <td style="padding-left: 0">
                    <div class="normal_content">
                        <div>
                            <span>是否居中：</span>
                            <label>
                                <input type="radio" name="align_type" value="1">居中
                            </label>
                            <label style="margin-left: 20px">
                                <input type="radio" name="align_type" value="0">居左
                            </label>
                        </div>
                        <div>
                            <span>文字颜色：</span>
                            <input type="color" class="choose_color" value="#666666"  name="color">
                            <input class="js-reset-bg huo_col" type="button" value="重置" onclick="$(this).prev().val('#000000');manager.change_show_module()">
                        </div>
                        <div>
                            <span>背景颜色：</span>
                            <input type="color" class="choose_color" value="#ffffff" name="background_color">
                            <input class="js-reset-bg huo_col" type="button" value="重置" onclick="$(this).prev().val('#ffffff');manager.change_show_module()">
                        </div>
                    </div>
                </td>
            </tr>
            <tr id="specials">
                <td style="width:80px;padding-right: 0"></td>
                <td style="padding-left: 0">
                    <div class="sps_content">
                        <ul class="clearfix">
                            <li style="margin-bottom: 18px">
                                <img src="http://${image_domain!}/image/admin/shop_deco/p_gray.png" alt="">
                                <input type="radio" name="sps_icon" src_img="/image/admin/shop_deco/p_gray.png" checked />
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/shop_deco/p_blue.png" alt="">
                                <input type="radio" name="sps_icon" src_img="/image/admin/shop_deco/p_blue.png"/>
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/shop_deco/p_deeppink.png" alt="">
                                <input type="radio" name="sps_icon" src_img="/image/admin/shop_deco/p_deeppink.png"/>
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/shop_deco/p_orange.png" alt="">
                                <input type="radio" name="sps_icon" src_img="/image/admin/shop_deco/p_orange.png"/>
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/shop_deco/p_pink.png" alt="">
                                <input type="radio" name="sps_icon" src_img="/image/admin/shop_deco/p_pink.png"/>
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/shop_deco/p_green.png" alt="">
                                <input type="radio" name="sps_icon" src_img="/image/admin/shop_deco/p_green.png"/>
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/shop_deco/p_aqua.png" alt="">
                                <input type="radio" name="sps_icon" src_img="/image/admin/shop_deco/p_aqua.png"/>
                            </li>
                        </ul>
                    </div>
                </td>
            </tr>
        </table>
       
    </div>
</div>


<div class="row_item m_service" style="background-color: transparent">
    <div class="service_module">
        <img src="http://${image_domain!}/image/admin/shop_deco/customer1.png" />
    </div>
    <div class="item_module_title">
        <span>客服</span>
    </div>
    <div class="item_operation">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_service edit_service_module">
    <div class="service_edit_module" style="position:relative;">
        <h2>在线客服</h2>
        <div class="service_content clearfix">
            <div class="fl">选择样式：</div>
            <ul class="clearfix">
                <input type="hidden" name="m_service" value="1" />
                <li>
                    <img src="http://${image_domain!}/image/admin/shop_deco/customer1.png" />
                    <input type="radio" name="ipt_service" value="1" checked />
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/shop_deco/customer2.png" />
                    <input type="radio" name="ipt_service" value="2" />
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/shop_deco/customer3.png" />
                    <input type="radio" name="ipt_service" value="3" />
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/shop_deco/customer4.png" />
                    <input type="radio" name="ipt_service" value="4" />
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/shop_deco/customer5.png" />
                    <input type="radio" name="ipt_service" value="5" />
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/shop_deco/customer6.png" />
                    <input type="radio" name="ipt_service" value="6" />
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/shop_deco/customer7.png" />
                    <input type="radio" name="ipt_service" value="7" />
                </li>
                <li>
                    <div class="customer_img add_image">
                        <img src="http://${image_domain!}/image/admin/shop_deco/customer_add.png" alt="">
                    </div>
                    <input type="radio" name="ipt_service" value="8" />
                </li>
            </ul>
        </div>
        
    </div>
</div>





<div class="data_item d_m_map edit_map_module">
    <div class="phone_edit_module" style="position:relative;">
        <h2>地图模块</h2>

        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:80px;padding-right: 0">显示地址：</td>
                <td style="padding-left: 0">
                    <select name="province" onchange="m_map.changeProvice(this)">
                        <#list province as item>
                            <option value="${item.province_id!}">${item.name!}</option>
                        </#list>
                    </select>
                    <select name="city" onchange="m_map.changeCity(this)">
                    </select>
                    <select name="area" onchange="$('#module_edit .d_m_map #map_code_address').trigger('click')">
                    </select>
                </td>
            </tr>
            <tr>
                <td style="width:80px;padding-right: 0"></td>
                <td  style="padding-left: 0">
                    <input type="text" placeholder="请输入详细地址" name="address" id="address" onblur="$(this).parent().find('button').trigger('click')">
                    <input type="hidden" name="latitude"/>
                    <input type="hidden" name="longitude"/>
                    <button onclick="m_map.codeAddress()" id="map_code_address">地图定位</button>
                </td>
            </tr>
            <tr>
                <td style="width:80px;padding-right: 0"></td>
                <td style="padding-left: 0">
                    <div id="container" style="width: 100%; height: 245px;"></div>
                </td>
            </tr>
            <tr>
                <td>地图:</td>
                <td>
                    <input type="radio" name="map_show" value="0"/> 不显示
                    <input type="radio" name="map_show" value="1"/> 显示
                </td>
            </tr>
        </table>
        <div style="margin-top:15px;">
            <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">
        </div>
    </div>
</div>


<!-- 地图模块 -->
<div class="row_item m_map">
    <div class="map_module">
        <div style="border-bottom: 2px dashed #7987aa; margin: 0 10px;" class="clearfix">
            <div class="map_head_img" style="background-image: url(/image/admin/shop_deco/map_position.png); width: 30px; height: 30px; background-size: 14px; display: inline-block; float: left; background-repeat: no-repeat; background-position: center;"></div>
            <div class="map_head_text" style="float: left;width: 225px; line-height: 30px; margin-left: 10px;">测试</div>
        </div>
        <div class="map_content" style="width: 100%; height: 200px;" id="map_content">
        </div>
    </div>
    <div class="item_module_title">
        <span>地图</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<!--视频装修 -->
<div class="row_item m_video">
    <div class="no_use" style="display: none;">
        <img src="http://${image_domain!}/image/admin/no_drag_use.png" />该模块仅高级版和旗舰版可用
        <img class="del_img" src="http://${image_domain!}/image/admin/icon_delete.png" style="float: right;margin: 10px 10px 0 0;cursor:pointer;" />
    </div>
    <div class="video_module">
        <video src="" controls="controls" width="100%" height="200px" <!-- style="object-fit: fill;"-->></video>
    </div>
    <div class="item_module_title">
        <span>视频</span>
    </div>
    <div class="video_title">
      <span class="van-ellipsis"></span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>
<div class="data_item d_m_video edit_video_module">
    <div class="video_edit_module" style="position:relative;">
        <h2>视频模块</h2>
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="vertical-align: top !important;">视频：</td>
                <td>
                    <div class="video-content" onclick="m_video.selectVideo(this)">
                        <input name="video" type="hidden" value="">
                        <input name="video_img" type="hidden" value="">
                        <input name="video_size" type="hidden" value="" />
                        <input name="video_width" type="hidden" value="" />
                        <input name="video_height" type="hidden" value="" />
                        <img src="http://${image_domain!}/image/admin/add_video.png" class="add-video">
                    </div>
                    <div style="margin-top: 10px; color: #999;">
                        上传视频仅支持MP4格式。为保障无线端各种网络环境下正常播放，只支持上传大小不超过10M，时长不超过3分钟的视频
                    </div>
                </td>
            </tr>
            <tr>
                <td>视频标题：</td>
                <td>
                    <input type="text" name="video_title" placeholder="请输入标题名称" maxlength="20">
                    <span>最多20个字</span>
                </td>
            </tr>
            <tr>
                <td style="padding-top: 11px">封面图：</td>
                <td>
                    <label for="poster_1">
                        <input type="radio" name="video_poster" value="1" checked id="poster_1">
                        <span>原视频封面(视频第一帧)</span>
                    </label>
                    <label for="poster_2">
                        <input type="radio" name="video_poster" value="2" id="poster_2">
                        <span>自定义封面</span>
                    </label>
                </td>
            </tr>
            <tr class="upload">
                <td style="padding-top: 10px;vertical-align: top !important;">上传封面：</td>
                <td>
                    <div class="add_image">
                        <div style="text-align: center;position: absolute;top: 46px;left: 9px;"></div>
                        <img class="image" style="max-width:100%;max-height:100%;position: absolute;z-index: 1">
                    </div>
                </td>
            </tr>
        </table> 

       
    </div>
</div>


<div class="row_item m_integral">
    <div class="no_use" style="display: none;">
        <img src="http://${image_domain!}/image/admin/no_drag_use.png" />该模块仅高级版和旗舰版可用
        <img class="del_img" src="http://${image_domain!}/image/admin/icon_delete.png" style="float: right;margin: 10px 10px 0 0;cursor:pointer;" />
    </div>
    <div class="integral_default">
        <!-- 双列的样式-->
        <ul class="integral_default_ul clearfix">
            <li class="double_act">
                <div class="integral_default_img">
                    <img src=""/>
                </div>
                <div class="integral_default_info">
                    <div class="goods_namess">积分商城名称-1</div>
                    <div class="integral_info_head clearfix">
                        <div class="goods_prices">
                            <div class="integral_price new_class"><span class="money_price"><em>￥200.00</em> +</span> <span style="font-size: 14px;" class="score">400000</span>积分</div>
                            <div class="goods_prices_bottom">
                                <div class="orignakl_orice">￥1000.00</div>
                                <a href="##" class="btn_convert back_color" >去兑换</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="double_act">
                <div class="integral_default_img">
                    <img src=""/>
                </div>
                <div class="integral_default_info">
                    <div class="goods_namess">积分商品名称-2</div>
                    <div class="integral_info_head clearfix">
                        <div class="goods_prices">
                            <div class="integral_price new_class"><span class="money_price"><em>￥200.00</em> +</span> <span style="font-size: 14px;" class="score">400000</span>积分</div>
                            <div class="goods_prices_bottom">
                                <div class="orignakl_orice">￥1000.00</div>
                                <a href="##" class="btn_convert back_color">去兑换</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <!-- 单列的样式-->
        <ul class="integral_ul_clone hide">
            <li class="single_acts clearfix">
                <div class="integral_default_img">
                    <img src=""/>
                </div>
                <div class="integral_default_info">
                    <div class="goods_namess">单列积分</div>
                    <div class="integral_info_head">
                        <span class="integral_price new_class"><span class="money_price"><em>￥200.00</em> + </span><span style="font-size: 14px;" class="score">40000</span>兑换</span>
                        <span class="orignakl_orice"><span>￥100</span></span>
                        <a href="##"  class="btn_convert back_color">去兑换</a>
                    </div>
                </div>
            </li>
        </ul>
        <ul class="integral_template hide">
            <li class="single_acts clearfix">
                <div class="integral_default_img">
                    <img src=""/>
                </div>
                <div class="integral_default_info">
                    <div class="goods_namess">单列积分</div>
                    <div class="integral_info_head">
                        <span class="integral_price new_class"><span class="money_price"><em>￥200.00</em> + </span><span style="font-size: 14px;" class="score">40000</span>积分</span>
                        <span class="orignakl_orice"><span>￥100</span></span>
                        <a href="##"  class="btn_convert back_color">去兑换</a>
                    </div>
                </div>
            </li>
            <li class="double_act">
                <div class="integral_default_img">
                    <img src=""/>
                </div>
                <div class="integral_default_info">
                    <div class="goods_namess">积分商城名称-1</div>
                    <div class="integral_info_head clearfix">
                        <div class="goods_prices">
                            <div class="integral_price new_class"><span class="money_price"><em>￥200.00</em> +</span> <span style="font-size: 14px;" class="score">400000</span>积分</div>
                            <div class="goods_prices_bottom">
                                <div class="orignakl_orice">￥1000.00</div>
                                <a href="##" class="btn_convert back_color">去兑换</a>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="item_module_title">
        <span>积分兑换</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>
<div class="data_item d_m_integral edit_integral_module">
    <div class="video_edit_module" style="position:relative;">
        <h2>积分兑换</h2>
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:80px;padding-right: 0">列表样式：</td>
                <td style="padding-left: 0">
                    <input type="radio" name="list_styles" value="1">单列
                    <input type="radio" name="list_styles" value="2" style="margin-left: 35px" checked>双列
                </td>
            </tr>
            <tr>
                <td style="width:80px;padding-right: 0;vertical-align: top!important;">选择商品：</td>
                <td style="padding-left: 0">
                    <div class="goods_adds">
                        <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png">
                        <p>添加商品</p>
                    </div>
                </td>
            </tr>
            <tr>
                <td style="width:80px;padding-right: 0;vertical-align: top!important;"></td>
                <td style="padding-left: 0">
                    <div style="color: #999;font-size: 13px">仅可以选择已经添加进积分兑换活动的商品，每个积分兑换模块最多可以添加6个商品</div>
                </td>
            </tr>
            <table class="integral_goods_tb" style="display: none;">
                <tr class="integral_select_th" style="background: #f8f8f8;">
                    <th width="30%">商品名称</th>
                    <th width="10%">库存</th>
                    <th width="30%">现金+积分</th>
                    <!-- <th>开始时间</th>
                    <th>結束时间</th>-->
                    <th width="19%">状态</th>
                    <th width="20%">操作</th>
                </tr>
            </table>
            <tr>
                <td style="width:80px;padding-right: 0;vertical-align: top!important;">显示内容：</td>
                <td style="padding-left: 0">
                    <input type="checkbox" name="show_goods_price" value="1" checked>商品原价
                </td>
            </tr>
        </table>
        <div class="integral_table_clone hide">
            <table>
                <tr class="integral_select_tr">
                    <td width="30%">
                        <div class="clearfix">
                            <img class="integral_select_img" src="" />
                            <div class="integral_select_name" title=""></div>
                        </div>  
                        <p class="integral_original"></p>
                    </td>
                    <td width="10%"></td>
                    <td width="30%"></td>
                    <!-- <td></td>
                    <td></td>-->
                    <td width="19%">正常</td>
                    <td width="20%">
                        <span class="integral_select_del">删除</span>
                    </td>
                </tr>
            </table>
        </div>
        <!-- <div style="margin-top:15px;">-->
            <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">-->
        <!-- </div>-->
    </div>
</div>


<div class="row_item m_qq">
    <div class="qq_module">
        <table>
            <tr>
                <td>
                    <div class="qq-icon"></div>
                </td>
                <td>
                    <span style="color: #999;">QQ:</span>
                    <div class="qq_number"></div>
                </td>
            </tr>
        </table>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_qq">
    <div class="qq_edit_module" style="position:relative;">
        <h2>QQ模块</h2>

        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:100px;">联系QQ</td>
                <td><input type="text" name="title" id="title" maxlength=14 size="34">
                    <span style="color:red;">最多14个字</span></td>
            </tr>

        </table>
    </div>
</div>


<div class="row_item m_wx">
    <div class="wx_module">
        <table>
            <tr>
                <td>
                    <div class="wx-icon"></div>
                </td>
                <td>
                    <span style="color: #999;">微信:</span>
                    <div class="wx_number"></div>
                </td>
            </tr>
        </table>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_wx">
    <div class="wx_edit_module" style="position:relative;">
        <h2>微信模块</h2>

        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td style="width:100px;">联系微信号</td>
                <td><input type="text" name="title" id="title" maxlength=20 size="34">
                    <span style="color:red;">最多20个字</span></td>
            </tr>

        </table>
    </div>
</div>

<div class="data_item d_m_official_accounts edit_official_accounts_module">
    <div class="official_accounts_edit_module" style="position:relative;">
       
        <div style="padding-bottom:10px;border-bottom:1px solid #eee;">引导用户关注公众号功能说明</div>
        <div style="padding:15px 0;">用户通过扫码打开小程序时，可快捷关注公众号</div>
        <p style="font-size: 12px;color: #999;line-height: 25px;">1、在使用此功能前，请前往微信公众平台-小程序后台，在“设置”——“接口设置”——“公众号关注组件中“设置要展示的公众号”</p>
        <p style="font-size: 12px;color: #999;line-height: 25px;">2、只有以下场景进入小程序，才会具有展示引导关注公众号的功能:</p>
        <p style="font-size: 12px;color: #999;line-height: 25px;"><span>（1）</span><span>当小程序从扫二维码场景打开时</span></p>
        <p style="font-size: 12px;color: #999;line-height: 25px;"><span>（2）</span><span>当小程序从扫小程序码场景打开时</span></p>
        <p class="clearfix" style="font-size: 12px;color: #999;line-height: 25px;word-wrap: break-word;
        word-break: break-all;"><span style="float: left;width: 7%">（3）</span> <span style="float: right;width: 93%">当小程序从聊天顶部场景中的“最近使用”内打开时，若小程序之前未被销毁，则该功能保持上一次打开小程序时的状态</span></p>
        <p class="clearfix" style="font-size: 12px;color: #999;line-height: 25px;word-wrap: break-word;
        word-break: break-all;"><span style="float: left;width: 7%">（4）</span> <span style="float: right;width: 93%">当从其他小程序返回小程序时，若小程序之前未被销毁，则该功能保持上一次打开小程序时的状态</span></p>
        <p style="font-size: 12px;color: #999;line-height: 25px;">3、每个页面此功能只能配置一个</p>
    </div>
</div>

<div class="row_item m_official_accounts">
    <div class="official_accounts  clearfix">
        <p class="mini_title">小程序名称xxx关联的公众号</p>
        <div class="mini_info_content clearfix">
            <div style="float:left;margin-right: 10px;margin-top: 5px;">
                <img src="http://${image_domain!}/image/admin/head_icon.png" alt="">
            </div>
            <div style="float: left;margin-top: 5px;max-width: 200;">
                <span class="official_accounts_name">公众号名称</span>
                <span style="line-height: 25px">公众号slogen</span>
            </div>
            <div style="border: 1px solid #38b641;float: right; padding:0 8px;margin-top: 15px;border-radius: 4px;color: #38b641">
                关注
            </div>
        </div>
    </div>
    <div class="item_module_title">
        <span>公众号</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>


<div class="row_item m_shop_announce">
    <div class="shop_announce">
        <img src="http://${image_domain!}/image/admin/m_shop_announce.png" alt="">
        <span></span>
    </div>
    <div class="item_module_title">
        <span>店铺公告</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_shop_announce edit_shop_announce_modules">
    <div class="shop_announce_edit_module clearfix" style="position:relative;">
        <h2>店铺公告模块</h2>
        <div class="fl clearfix">
            <span style="float: left;" class="sp_title">内容：</span>
            <div>
                <input type="text" class="shop_text" maxlength="120" style="padding-left: 5px" placeholder="请填写内容,如果过长,将会滚动显示">
            </div>
        </div>
        <div class="fl clearfix">
            <span style="float: left;" class="sp_title">字体颜色：</span>
            <div class="font_colorss" style="float: left;">
                <input type="color"  name="fonts_color" value="#333333">
                <input type="button" class="chongzhi" onclick="$(this).prev().val('#333333');manager.change_show_module();" value="重置">
            </div>
        </div>
        <div class="fl clearfix">
            <span style="float: left;" class="sp_title">背景颜色：</span>
            <div class="font_colorss" style="float: left;">
                <input type="color" name="bgs_color" value="#fcf9dd">
                <input type="button" class="chongzhi" onclick="$(this).prev().val('#fcf9dd');manager.change_show_module();" value="重置">
            </div>
        </div>
        <div class="fl clearfix">
            <span style="float: left;" class="sp_title">链接：</span>
            <div>
                <input type="text" name="title_link" id="title_link" maxlength=255 size="24">
                <input type="button" value="选择链接" class="select_links">
            </div>
        </div>
    </div>
</div>