<style type="text/css">
    .item-picture .item-price .goods-price {
        width: 57%;
        -ms-text-overflow: ellipsis;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .item-picture .item-price .market_price {
        width: 35%;
        -ms-text-overflow: ellipsis;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .display_show label {
        width: 70px;
    }

    .display_show input {
        margin-right: 2px;
    }

    .choosed_goods span.goods_status {
        color: red;
        border: 1px solid red;
        padding: 0 3px;
        margin-right: 5px;
    }

    .item-picture {
        border: 1px solid #eee;
        background:#fff;
    }

    .add_goods_area {
        margin-left: 0px;
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5A8BFF;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin-top: 17px;
        margin-bottom: 10px;
        padding: 3px 5px;
    }
</style>
<div class="row_item m_goods">
    <div class="tit_cent">
        <div style="height: 55px;line-height:55px;">
            <img src="" class="image hide" style="height: 31px;">
            <span class="p_goods_title title title_link"></span>
        </div>
    </div>
    <img class='title_image' src="" style="width: 100%;">
    <div class="item_module_title">
        <span>商品</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="single_goods_module">
    <ul class="goods_container">
        <li class="p_goods_item goods_wrapper">
            <div class="item-picture">
                <div class="photo-block" style="max-height: 150px;overflow: hidden;text-align: center;position:relative;
            ">
                    <div class=" label-control">
                        <span>11.1111</span>
                    </div>
                    <img class="item_img" src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png
            " style="width:100%;">
                </div>
                <div class="item-price clearfix">
                    <div class="goods-title bs_head" style="width:100%">
                        <div class="title_one van-ellipsis hide"></div>
                        <div class="title_two van-two-ellipsis"></div>
                        <div class="title_label hide">
                            <span class="new_class border_class">秒杀</span>
                            <span class="new_class border_class">满100享5折</span>
                        </div>
                    </div>
                    <p class="goods-price new_class goods_market_price not_float"><em class="bs_prix"></em></p>
                    <p class="market_price not_float"><em class="mk_price" style="font-style: normal;">￥0.00</em></p>
                    <span class="cart_btn iconfont icontianjia new_class float-right"></span>
                    <span class="cart_btn iconfont icongouwuche1 new_class float-right"></span>
                    <span class="cart_btn right_buy_btn new_back ">马上抢</span>
                    <span class="cart_btn cart_buy_btn  new_class border_class">购买</span>
                </div>
            </div>
        </li>
    </ul>
</div>

<div class="double_goods_module">
    <ul class="goods_container">
        <li class="p_goods_item1 goods_wrapper" >
            <div class="item-picture">
                <div class="photo-two" style="overflow: hidden;position: relative">
                    <div class="label-control ">
                        <span>热卖推</span>
                    </div>
                    <img class="item_img" src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png"
                        style="max-width:100%!important;min-height: auto;">
                </div>
                <div class="item-price">
                    <div class="goods-title bs_head ">
                        <div class="title_one van-ellipsis hide"></div>
                        <div class="title_two van-two-ellipsis"></div>
                        <div class="title_label hide">
                            <span class="new_class border_class">秒杀</span>
                            <span class="new_class border_class">满100享5折</span>
                        </div>
                    </div>
                    <div class="clearfix">
                        <span class="goods-price new_class goods_market_price">
                            <em class="bs_prix" style="font-style: normal;">￥0.00</em>
                        </span>
                        <span class="market_price not_float">
                            <em class="mk_price" style="font-style: normal;">￥0.00</em>
                        </span>
                        <span class="cart_btn iconfont icontianjia new_class float-right"></span>
                        <span class="cart_btn iconfont icongouwuche1 new_class float-right"></span>
                        <span class="cart_btn right_buy_btn new_back ">马上抢</span>
                        <span class="cart_btn cart_buy_btn  new_class border_class">购买</span>
                    </div>
                </div>
        </li>
    </ul>
</div>

<div class="three_goods_module">
    <ul class="goods_container">
        <li class="t_goods_item1 goods_wrapper">
            <div class="item-picture">
                <div class="photo-three" style="position: relative">
                    <div class="label-control ">
                        <span>热卖推荐</span>
                    </div>
                    <img class="item_img" src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png"
                        style="max-width:100%;">
                </div>
                <div class="item-price clearfix">
                    <div class="goods-title bs_head">
                        <div class="title_one van-ellipsis hide"></div>
                        <div class="title_two van-two-ellipsis"></div>
                        <div class="title_label hide">
                            <span class="new_class border_class">秒杀</span>
                        </div>
                    </div>
                    <p class="goods-price  new_class goods_market_price">
                        <em class="bs_prix" style="font-style: normal;">￥0.00</em>
                    </p>
                    <span class="cart_btn iconfont icontianjia new_class float-right"></span>
                    <span class="cart_btn iconfont icongouwuche1 new_class float-right"></span>
                </div>
            </div>
        </li>
    </ul>

</div>

<div class="four_goods_module">
    <ul class="goods_container">
        <li class="t_goods_item1 goods_wrapper" >
            <div class="item-picture">
                <div class="photo-three" style="position: relative">
                    <div class="label-control ">
                        <span>热卖推荐</span>
                    </div>
                    <img class="item_img" src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png"
                        style="max-width:100%;min-height: 100px">
                </div>
                <div class="item-price clearfix">
                    <div class="goods-title bs_head ">
                        <div class="title_one van-ellipsis hide"></div>
                        <div class="title_two van-two-ellipsis"></div>
                        <div class="title_label hide">
                            <span class="new_class border_class">秒杀</span>
                            <span class="new_class border_class">满100享5折</span>
                        </div>
                    </div>

                    <div class="clearfix">
                        <span class="goods-price new_class new_class goods_market_price">
                            <em class="bs_prix" style="font-style: normal;">￥0.00</em>
                        </span>
                        <span class="market_price not_float">
                            <em class="mk_price" style="font-style: normal;">￥0.00</em>
                        </span>
                        <span class="cart_btn iconfont icontianjia new_class float-right"></span>
                        <span class="cart_btn iconfont icongouwuche1 new_class float-right"></span>
                    </div>
                </div>
            </div>
        </li>
    </ul>

</div>


<div class="data_item d_m_goods edit_goods_module">
    <input type="hidden" name="col_type" id="col_type" value="0">
    <div class="goods_edit_module" style="position:relative;">
        <h2 class="module_name">商品模块</h2>
        <div style="margin-bottom:10px">
            <span>模块标题：</span>
            <div style="display: inline-block">
                <input type="radio" name="goods_module_title" value="0">不设置
                <input type="radio" name="goods_module_title" value="1" style="margin-left:10px">文字标题
                <input type="radio" name="goods_module_title" value="2" style="margin-left:10px">图片标题
            </div>
        </div>
        <table class="module_goods_title" cellspacing='1' cellpadding='3' style="width: 100%">
            <tbody class="bg_white">
                <tr class="tr_padding">
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td style="height: 30px;line-height: 30px">标题：</td>
                    <td><input type="text" name="title" id="title" maxlength=10 size="34" class="dg_goods_title">
                        <span style="color:#a7a7a7;">最多10个字</span></td>
                </tr>
                <tr>
                    <td style="height: 30px;line-height: 30px">标题链接：</td>
                    <td><input type="text" name="title_link" id="title_link" maxlength=255 size="34">
                        <input type="button" value="选择链接" class="select_links">
                    </td>
                </tr>
                <tr>
                    <td>标题位置：</td>
                    <td>
                        <label id="title_center">
                            <input type="checkbox" value="1">标题居中
                        </label>
                    </td>
                </tr>
                <tr class="title_icon">
                    <td>图标：</td>
                    <td>
                        <div class="add_image deco_add_img">
                            <img src="" alt="">
                            <a class="del-cube-block del-has-image" href="javascript:void(0)"></a>
                        </div>
                    </td>
                </tr>
                <tr class="title_image">
                    <td>标题图片：</td>
                    <td>
                        <div class="add_title_image deco_add_img">
                            <img src="" alt="">
                            <a class="del-cube-block del-title-image" href="javascript:void(0)"></a>
                        </div>
                    </td>
                </tr>
                <tr class='tr_padding'>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
        <div style="margin-bottom:10px;margin-top: 10px;">
            <span>列表样式</span>
        </div>
        <table cellspacing='1' cellpadding='3' class="base_message" style="width: 100%">
            <tbody class="bg_white">
                <tr>
                    <td class="img_list">
                        <div class="single_list change-to-save">
                            <div></div>
                            <div></div>
                            <p>单列</p>
                        </div>
                        <div class="double_list blue_border change-to-save">
                            <div></div>
                            <div></div>
                            <div></div>
                            <div></div>
                            <p style="margin-top: 65px">双列</p>
                        </div>
                        <div class="triple_list change-to-save">
                            <div></div>
                            <div></div>
                            <div></div>
                            <div></div>
                            <div></div>
                            <div></div>
                            <p style="margin-top: 65px">三列</p>
                        </div>
                        <div class="fourth_list change-to-save" style="margin-top: 10px">
                            <div></div>
                            <div></div>
                            <div style="width: 15%"></div>
                            <p style="margin-top: 65px">横向滑动</p>
                        </div>
                    </td>
                </tr>
            </tbody>

        </table>
        <div style="margin-bottom:10px;margin-top: 10px;">
            <span>商品模块</span>
        </div>
        <table cellspacing='1' cellpadding='3' class="base_message" style="width: 100%">
            <tbody class="bg_white">
                <tr class="display_show" style="display: none;">
                    <td style="padding: 0">单列展示：</td>
                    <td>
                        <label for="paved">
                            <input type="radio" id="paved" name="goods_display" checked value="0" />
                            平铺
                        </label>
                        <label for="center">
                            <input type="radio" id="center" name="goods_display" value="1" />
                            居中
                        </label>
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align: top !important;">显示内容：</td>
                    <td>
                        <label id="hide_goods_name">
                            <input type="checkbox" value="0">商品名称
                        </label>
                        <label id="hide_goods_price">
                            <input type="checkbox" value="0">商品价格
                        </label>
                        <label id="hide_goods_label">
                            <input type="checkbox" value="0">商品标签
                        </label>
                        <div class="shopping_btn" style="margin-bottom: 5px;">
                            <input type="checkbox" value="0" id="shop_cart_btn">购买按钮
                            <span style="color:#999;font-size:14px;display: inline-block;margin-left: 5px;">显示购买按钮时将不显示其他信息</span>
                            <div class="btn_cart">
                                <label for="shop_cart_1">
                                    <input type="radio" value="0" id="shop_cart_1" name="cart_checked" checked>
                                    <div class="iconfont icontianjia icon_font_size new_class"></div>
                                </label>
                                <label for="shop_cart_2" style="width: 55px">
                                    <input type="radio" value="1" id="shop_cart_2" name="cart_checked">
                                    <div class="iconfont icongouwuche1 icon_font_size new_class"></div>
                                </label>
                                <label for="shop_cart_3" style="width: 90px">
                                    <input type="radio" value="2" id="shop_cart_3" name="cart_checked">
                                    <div class="right_buy new_back">
                                        马上抢
                                    </div>
                                </label>
                                <label for="shop_cart_4" style="width: 70px">
                                    <input type="radio" value="3" id="shop_cart_4" name="cart_checked">
                                    <div class="cart_buy border_class new_class">购买</div>
                                </label>
                            </div>
                        </div>
                        <div class="other_messages">
                            <input type="checkbox" value="0" id="other_message" checked>其他信息
                            <span style="color:#999;font-size:14px;display: inline-block;margin-left: 5px;">后台数据仅为参考请以实际显示为准</span>
                            <div class="other_div">
                              <input type="radio" value="1" checked id="show_market_price" name='show_market'>市场价
                              <input type="radio" value="2" name='show_market' id='show_market_num'>销量
                              <input type="radio" value="3" name='show_market' id='show_market_evaluate'>评价数
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>模块角度：</td>
                    <td>
                        <div>
                            <input type="radio" name="if_radius" value="0" id="">直角
                            <input type="radio" name="if_radius" value="1" style="margin-left: 10px">圆角
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>模块样式：</td>
                    <td>
                        <div>
                            <input type="radio" name="goods_module_style" value="0">白底无边框
                            <input type="radio" name="goods_module_style" value="1" style="margin-left:10px">边框投影
                            <input type="radio" name="goods_module_style" value="2" style="margin-left:10px">白底有边框
                        </div>
                    </td>
                </tr>
                <tr class="goods_bg_con">
                    <td style="vertical-align: top !important">背景颜色：</td>
                    <td>
                        <label> 
                            <input type="radio" name="goods_module_bg" value="0" checked>与页面背景一致   
                        </label>
                        <label for="">
                            <input type="radio" name="goods_module_bg" value="1">自定义
                            <input type="color" value="#f5f5f5" name="goods_bg_color" style="width: 60px;height:30px;margin-right: 5px;">
                            <input class="js-reset-bg huo_col chongzhi" type="button" value="重置" onclick="resetColor(this)">
                        </label>
                    </td>
                </tr>
            </tbody>
        </table>
        <div style="margin-bottom:10px;margin-top: 10px;">
            <span>模块推荐:</span>
            <div style="display: inline-block">
                <input type="radio" name="recommend_type" value="0" style="margin-left:10px">自动推荐
                <input type="radio" name="recommend_type" value="1" style="margin-left:10px">手动推荐
            </div>
        </div>
        <table class="auto_recommend" cellspacing='1' cellpadding='3' style="width: 100%">
            <tbody class='bg_white'>
                <tr>
                    <td>商品数量：</td>
                    <td><select name="goods_num" style="width: 40%">
                            @for($i = 1; $i <= 20; $i ++) <option value="${i!}">${i!}</option>
                                @endfor
                        </select>
                    </td>
                </tr>
                <tr class="price_choose">
                    <td>商品价格：</td>
                    <td><input type="text" name="min_price" value="" maxlength=10 size="5"> &nbsp;到&nbsp;
                        <input type="text" name="max_price" value="" maxlength=10 size="5">
                    </td>
                </tr>
                <tr>
                    <td>关键词：</td>
                    <td><input type="text" name="keywords" value="" maxlength=255 size="34"
                            style="height: 30px;width: 45%;"></td>
                </tr>
                <tr>
                    <td>排序规则：</td>
                    <td><select name="sort_type" style="width: 30%">
                            <option value="1">上新</option>
                            <option value="2">销量</option>
                            <option value="3">价格（由低到高）</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>商品范围：</td>
                    <td>
                        <select name="goods_area" style="width: 30%">
                            <option value="all">请选择</option>
                            <option value="sort">商家分类</option>
                            <option value="cat">平台分类</option>
                            <option value="brand">商品品牌</option>
                            <option value="label">商品标签</option>
                        </select>
                    </td>
                </tr>
                <tr class="goods_area_detail">
                    <td></td>
                    <td>
                        <a class="add_goods_area" area_data="">+添加商家分类</a>
                        <span class="select_area_show">已选择分类：1个分类</span>
                    </td>
                </tr>
            </tbody>
            {{--<tr>--!}
            {{--<td>分类：</td>--!}
            {{--<td>--!}
            {{--<select name="category" style="width:45%;">--!}
            {{--<option value="0">全部分类</option>--!}
            {{--<#list ($cat_list as $item)--!}
            {{--<option value="${item['cat_id']!}"--!}
            {{--<#if ($cat_id == $item['cat_id'])selected="selected"</#if>>${item['cat_name']!}</option>--!}
            {{--</#list>--!}
            {{--</select>--!}
            {{--</td>--!}
            {{--</tr>--!}
        </table>

        <table class="custom_goods" cellspacing='1' cellpadding='3' style="width: 100%">
            <tbody class="bg_white">
                <tr>
                    <td style="width: 30%;text-align: right;">商品列表：</td>
                    <td>
                        <div>
                            <input type="button" value="添加商品" title="添加商品" class="add_goods">
                            <!-- 添加商品弹框隐藏域 -->
                            <input type="hidden" name="recommend_goods_id" value="" />
                        </div>
                        <div class="goods_item_list">

                        </div>
                        <div class="goods_item" hide>
                            <table class="choosed_goods">
                                <tr>
                                    <td width="50px;"><img style="max-width:50px;max-height:50px;"></td>
                                    <td width="240px;">
                                        <span class="goods_name"></span>
                                        <span class="goods_price"></span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input type="button" value="↑" title="向上" class="up_arrow">
                                        <input type="button" value="↓" title="向下" class="down_arrow">
                                        <input type="button" value="X" title="移除" class="remove">
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>

    </div>
    {{--<div style="margin-top:15px;">--!}
    {{--<input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定">--!}
    {{--</div>--!}
</div>
<script>
    var drag_width = $('#drag_area').width();
    drag_width -= 72;
    drag_width /= 2;
    $('.photo-two').height(drag_width);

    $(document).on("click", ".img_list>div", function () {
        hasSaved = false;
        $(this).addClass("blue_border").siblings().removeClass("blue_border");
    });
</script>