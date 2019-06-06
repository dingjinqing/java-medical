<div class="row_item m_bargain">
    <div class="no_use" style="display: none;">
        <img src="http://${image_domain!}/image/admin/no_drag_use.png" />该模块仅旗舰版可用
        <img class="del_img" src="http://${image_domain!}/image/admin/icon_delete.png" style="float: right;margin: 10px 10px 0 0;cursor:pointer;" />
    </div>
    <div class="bargain_default">
        <ul class="bargain_default_ul clearfix">
            <li>
                <div class="bargain_default_img">
                    <img src="" />
                </div>
                <div class="bargain_default_info">
                    <div class="bargain_info_head">
                        <span class="bargain_price new_class">￥<span style="font-size: 18px;">0</span></span>
                    </div>
                </div>
            </li>
            <li>
                <div class="bargain_default_img">
                    <img src="" />
                </div>
                <div class="bargain_default_info">
                    <div class="bargain_info_head">
                        <span class="bargain_price new_class">￥<span class="expectation_price" style="font-size: 18px;">0</span></span>
                    </div>
                </div>
            </li>
        </ul>
        <ul class="bargain_ul_clone hide">
            <li class="double_act">
                <div class="bargain_default_img">
                    <img src="" width="100%" />
                    <div class="bargain_time_down">
                        <div>开始时间</div>
                        <p>1天23时30分24秒</p>
                    </div>
                </div>
                <div class="bargain_default_info">
                    <div class="bargain_info_head">
                        <span class="bargain_price new_class">￥<span style="font-size: 18px;">0</span></span>
                        <span class="bargain_old">￥<span>100</span></span>
                    </div>
                    <div class="bargain_info_name">faesasaca</div>
                    <div class="clearfix bargain_info_bottom">
                        <span class="bargain_num">仅剩10件</span>
                        <span class="bargain_free_btn back_color">去砍价</span>
                    </div>
                </div>
            </li>
            <li class="single_act clearfix">
                <div class="bargain_default_img">
                    <img src="" width="100%" />
                    <div class="bargain_time_down">
                        <div>开始时间</div>
                        <p>1天23时30分24秒</p>
                    </div>
                </div>
                <div class="bargain_single_info">
                    <div class="bargain_info_name">faesasaca</div>
                    <span class="bargain_num">仅剩10件</span>
                    <div class="bargain_single_bottom clearfix">
                        <div class="bargain_info_head">
                            <span class="bargain_price new_class">￥<span class="expectation_price" style="font-size: 18px;">0</span></span>
                            <span class="bargain_old">￥<span>100</span></span>
                        </div>
                        <span class="bargain_free_btn back_color">去砍价</span>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="item_module_title">
        <span>砍价</span>
    </div>

    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_bargain edit_bargain_modules">
    <h2>砍价模块</h2>
    <div class="bargain_item">
        <div class="clearfix">
            <div class="fl">列表样式：</div>
            <div class="fl_r">
                <label for="double_list">
                    <input type="radio" name="list_style" id="double_list" checked="checked" value="0" />
                    双列
                </label>
                <label for="single_list">
                    <input type="radio" name="list_style" id="single_list" value="1" />
                    单列
                </label>
            </div>
        </div>
        <div class="clearfix">
            <div class="fl"><em>*</em>选择商品：</div>
            <div class="fl_r">
                <div class="bargain_add_goods">
                    <img src="/image/admin/shop_beautify/add_decorete.png" />
                    <p>添加商品</p>
                </div>
                <p class="bargain_prompt">
                    仅可以选择已经添加进砍价活动的砍价商品，每个砍价装修模块最多选择6个商品。
                </p>
                <table class="bargain_goods_tb" style="display: none;margin-left:-80px;width:125%;">
                    <tr class="bargain_select_th">
                        <th>商品名称</th>
                        <th width="90px">开始时间</th>
                        <th>底价</th>
                        <th>砍价库存</th>
                        <th>商品状态</th>
                        <th>操作</th>
                    </tr>
                </table>
            </div>
        </div>
        <div class="clearfix">
            <div class="fl">显示内容：</div>
            <div class="fl_r">
                <label for="goods_price">
                    <input type="checkbox" name="goods_price" checked id="goods_price" />
                    商品原价
                </label>
                <label for="goods_count_down" style="width: 110px;">
                    <input type="checkbox" name="goods_count_down" checked id="goods_count_down" />
                    活动倒计时
                </label>
                <label for="free_btn">
                    <input type="checkbox" name="free_btn" checked id="free_btn" />
                    去砍价按钮
                </label>
            </div>
        </div>
    </div>
    {{--<div style="margin-top:15px;">--!}
        {{--<input class="btn btn-primary queren " type="button" id="ok" name="ok" value="确定" style="margin-left: 110px;">--!}
    {{--</div>--!}
    <div class="bargain_table_clone hide">
        <table>
            <tr class="bargain_select_tr">
                <td>
                    <img class="bargain_select_img" src="" />
                    <div class="bargain_select_name">上交所拉进来就爱我呢克拉</div>
                </td>
                <td>10-12</td>
                <td class="expectation_price">0</td>
                <td>23</td>
                <td>正常</td>
                <td>
                    <span class="bargain_select_del">删除</span>
                </td>
            </tr>
        </table>
    </div>
</div>
