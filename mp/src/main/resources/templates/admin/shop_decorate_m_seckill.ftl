<div class="row_item m_seckill">
    <div class="no_use" style="display: none;">
        <img src="http://${image_domain!}/image/admin/no_drag_use.png" />该模块仅高级版和旗舰版可用
        <img class="del_img" src="http://${image_domain!}/image/admin/icon_delete.png" style="float: right;margin: 10px 10px 0 0;cursor:pointer;" />
    </div>
    <div class="seckill_default">
        <ul class="seckill_default_ul clearfix">
            <li>
                <div class="seckill_default_img">
                    <img src="" />
                </div>
                <div class="seckill_default_info">
                    <div class="seckill_head_name">秒杀商品名称1</div>
                    <div class="seckill_info_head">
                        <span class="seckill_icon new_class border_class">秒杀</span>
                        <span class="seckill_price new_class">￥<span class="sec_price" style="font-size: 18px;">0</span></span>
                        <span class="seckill_gray" id="seckill_gray">￥<span>0</span></span>
                    </div>
                </div>
            </li>
            <li>
                <div class="seckill_default_img">
                    <img src="" />
                </div>
                <div class="seckill_default_info">
                    <div class="seckill_head_name">秒杀商品名称2</div>
                    <div class="seckill_info_head">
                        <span class="seckill_icon new_class border_class">秒杀</span>
                        <span class="seckill_price new_class">￥<span class="sec_price" style="font-size: 18px;">0</span></span>
                        <span class="seckill_gray" id="seckill_gray">￥<span>0</span></span>
                    </div>
                </div>
            </li>
        </ul>
        <ul class="seckill_ul_clone hide">
            <li class="double_act">
                <div class="seckill_default_img">
                    <img src="" width="100%" />
                    <div class="seckill_time_down">
                        <div>开始时间</div>
                        <p>1天23时30分24秒</p>
                    </div>
                </div>
                <div class="seckill_default_info">
                    <div class="seckill_head_name">秒杀商品名称2</div>
                    <div class="seckill_info_head">
                        <span class="seckill_icon new_class border_class">秒杀</span>
                        <span class="seckill_price new_class">￥<span class="sec_price" style="font-size: 18px;">0</span></span>
                        <span class="seckill_gray" id="seckill_gray">￥<span>0</span></span>
                    </div>
                    <!-- <div class="seckill_info_name">111</div> -->
                    <!-- <div class="clearfix seckill_info_bottom"> -->
                        <!-- <span class="seckill_num">仅剩10件</span> -->
                        <!-- <span class="seckill_free_btn">免费拿</span> -->
                    <!-- </div> -->
                </div>
            </li>
            <li class="single_act clearfix">
                <div class="seckill_default_img">
                    <img src="" width="100%" />
                    <div class="seckill_time_down">
                        <div>开始时间</div>
                        <p>1天23时30分24秒</p>
                    </div>
                </div>
                <div class="seckill_single_info">
                    <div class="seckill_head_name">秒杀商品名称</div>
                    <span class="seckill_icon new_class border_class" style="line-height: 30px;font-size: 12px;">秒杀</span>
                    <div class="seckill_bottom">
                        <div class="seckill_single_bottom clearfix">
                            <div class="seckill_info_head">
                                <span class="seckill_price new_class">￥<span class="sec_price" style="font-size: 18px;">0</span></span>
                            </div>
                            <span class="seckill_free_btn back_color">去抢购</span>
                        </div>
                        <div class="seckill_double_bottom">
                            <span class="seckill_gray" style="text-decoration: line-through;width: 80px;">￥<span>0</span></span>
                            <span class="seckill_pay" >已售<span class="sale_num">50</span>%</span>
                            <span class="seckill_progess border_class">
                                <span class="progess new_back"></span>
                            </span>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="item_module_title">
        <span>秒杀</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_seckill edit_seckill_modules">
    <h2>秒杀模块</h2>
    <div class="seckill_item">
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
                <div class="seckill_add_goods">
                    <img src="/image/admin/shop_beautify/add_decorete.png" />
                    <p>添加商品</p>
                </div>
                <p class="seckill_prompt">
                    仅可以选择已经添加进秒杀活动的秒杀商品，每个秒杀装修模块最多选择6个商品。
                </p>
                <table class="seckill_goods_tb" style="display: none;margin-left:-80px;width:125%;">
                    <tr class="seckill_select_th">
                        <th>商品名称</th>
                        <th width="90px">开始时间</th>
                        <th>秒杀库存</th>
                        <th>商品状态</th>
                        <th>操作</th>
                    </tr>
                </table>
            </div>
        </div>
        <div class="clearfix">
            <div class="fl">显示内容：</div>
            <div class="fl_r">
                <label for="goods_count_down" style="width: 110px;">
                    <input type="checkbox" name="goods_count_down" checked id="goods_count_down" />
                    活动倒计时
                </label>
                <label for="goods_price">
                    <input type="checkbox" name="goods_price" checked id="goods_price" />
                    商品原价
                </label>
            </div>
        </div>
    </div>
    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren " type="button" id="ok" name="ok" value="确定" style="margin-left: 110px;"> -->
    <!-- </div> -->
    <div class="seckill_table_clone hide">
        <table>
            <tr class="seckill_select_tr">
                <td>
                    <img class="seckill_select_img" src="" />
                    <div class="seckill_select_name"></div>
                </td>
                <td>10-12</td>
                <td>23</td>
                <td>正常</td>
                <td>
                    <span class="seckill_select_del">删除</span>
                </td>
            </tr>
        </table>
    </div>
</div>
