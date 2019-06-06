
<div class="row_item m_goods_top">
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

<div class="goods_top_module">
    <p class="rank-name">排行榜</p>

    <div class="item row" item-id="1">
		<div class="col-xs-1 rank-badge" ><span style="margin-left:-5px;">1</span></div>
        <div class="item-picture col-xs-2 no-padding">
            <img class="item-img" src="">
        </div>
        <div class="item-detail col-xs-8">
            <p class="item-name"></p>
			<div class="clearfix" style="position: absolute;bottom: 0;width: 100%;">
	            <p class="item-price"></p>
	            <p class="item-sale_num pull-right"></p>
			</div>
        </div>
    </div>
    <div class="item row" item-id="2">
		<div class="col-xs-1 rank-badge" ><span style="margin-left:-5px;">2</span></div>
        <div class="item-picture col-xs-2 no-padding">
            <img class="item-img" src="">
        </div>
        <div class="item-detail col-xs-8">
            <p class="item-name"></p>
			<div class="clearfix" style="position: absolute;bottom: 0;width: 100%;">
	            <p class="item-price"></p>
	            <p class="item-sale_num pull-right"></p>
			</div>
        </div>
    </div>
    <div class="item row" item-id="3">
		<div class="col-xs-1 rank-badge" ><span style="margin-left:-5px;">3</span></div>
        <div class="item-picture col-xs-2 no-padding">
            <img class="item-img" src="">
        </div>
        <div class="item-detail col-xs-8">
            <p class="item-name"></p>
			<div class="clearfix" style="position: absolute;bottom: 0;width: 100%;">
	            <p class="item-price"></p>
	            <p class="item-sale_num pull-right"></p>
			</div>
        </div>
    </div>
</div>


<div class="data_item d_m_goods_top">
    <div class="goods_top_edit_module" style="position:relative;">
        <h2>商品排行配置模块</h2>

        <table class="auto_recommend" cellspacing='1' cellpadding='3'>
            <tr>
                <td>分类</td>
                <td>
                    <select name="category">
                        <option value="0">全部分类</option>
                        <#list  ($cat_list as $item)
                            <option value="${item->cat_id!}">${item->cat_name!}</option>
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <td>关键词</td>
                <td><input type="text" name="keywords" value="" maxlength=255 size="34"></td>
            </tr>
			<tr>
                <td style="width:80px;">商品数量</td>
                <td><select name="goods_num">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>商品价格</td>
                <td><input type="text" name="min_price" value="" maxlength=10 size="5">到
                    <input type="text" name="max_price" value="" maxlength=10 size="5">
                </td>
            </tr>

        </table>


    </div>
</div>



<div class="row_item m_goods_search" style="background: #eee;width: 100%;padding: 5px 0;">
    <div class="goods_search_module">
        <div class="panel panel-info">
            <span class="glyphicon glyphicon-search"></span>

            <div class="search_tip">商品搜索：请输入商品关键字</div>
        </div>
    </div>
    <div class="item_module_title">
        <span>商品搜索</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>
<div class="data_item d_m_goods_search edit_search_module">
    <div class="goods_top_edit_module" style="position:relative;">
        <h2>商品搜索模块</h2>
    </div>
    <div class="search_style clearfix">
        <div class="sy_left">框体样式：</div>
        <div class="sy_right">
            <input type="radio" id="square" name="search_style" value="0" checked>方形
            <input type="radio" id="circle" name="search_style" value="1" style="margin-left: 15px">圆形
        </div>
    </div>
    <div class="search_style clearfix">
        <div class="sy_left">框体高度：</div>
        <div class="sy_right">
            <input type="radio" name="search_font" id="high" value="0" >高
            <input type="radio" name="search_font" id="middle" value="1" checked>中
            <input type="radio" name="search_font" id="low" value="2" >低
        </div>
    </div>
    <div class="search_style clearfix">
        <div class="sy_left">框体颜色：</div>
        <div class="sy_right">
            <input type="color" value="#eeeeee" name="box_color" style="height: 30px;width: 60px;border-radius: 0">
            <input class="chongzhi" type="button" value="重置" onclick="$(this).prev().val('#eeeeee');manager.change_show_module();">
        </div>

    </div>
    <div class="search_style clearfix">
        <div class="sy_left">背景颜色：</div>
        <div class="sy_right">
            <input type="color" value="#ffffff" name="back_color" style="height: 30px;width: 60px;border-radius: 0">
            <input class="chongzhi" type="button" value="重置" onclick="$(this).prev().val('#ffffff');manager.change_show_module();">
        </div>
    </div>
</div>


<div class="row_item m_all_goods">
    <div class="all_goods_module">
        <div class="panel panel-info">
            <img src="http://${image_domain!}/image/admin/shop_beautify/m_all_goods.jpg" style="max-width:100%;">
        </div>
    </div>
    <div class="item_operation">

    </div>
    <div class="item_no_data">没有数据</div>
</div>

