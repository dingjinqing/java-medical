<style>
    .down_img1, .up_img2{
        position: relative;
        width: 30px;
        height: 30px;
        cursor: pointer;
    }
    .down_img1{
        background: url(/image/admin/shop_beautify/add_down.png) no-repeat;
        left: 100px;
        top: -30px;
    }
    .up_img2{
        background: url(/image/admin/shop_beautify/add_up_use.png) no-repeat;
        left: 100px;
        top: -30px;
    }

</style>

<div class="row_item m_title">
    <div class="title_module">
		    <div class="basiclines clearfix">
				<img src="" class="image" style="height: 31px;">
				<span class="new_title">点击编辑【标题】</span>
				<img style="height: 13px; width: 7px;float:right; margin-top:5px;" src="/image/admin/shop_beautify/gt.png">
                <span style="float: right;color: #999;font-size: 13px;padding-top: 3px;padding-right: 3px">更多</span>
			</div>
            <div class="headlines">
                <div class="new_title" style="font-size: 18px">标题名称</div>
                <div class="headContent">
                    <span class="headdate">xxxx-xx-xx</span>
                    <span class="headauthor">作者</span>
                    <span class="headlink">标题链接</span>
                </div>
            </div>
    </div>
    <div class="item_module_title">
        <span>标题</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_title edit_title_modules">
    <div class="title_edit_module" style="position:relative;">
        <h2>标题模块</h2>
        <table class="auto_recommend" cellspacing='1' cellpadding='3'>
            <tr>
                <td>标题:</td>
                <td>
                    <input type="text" name="title" id="title" maxlength=20 size="34">
                    <span style="color: #a7a7a7;">最多20个字</span>
                </td>
            </tr>
            <tr>
                <td>标题模板:</td>
                <td>
                    <label id="title_basic">
                        <input type="radio" value="1" name="title_show" checked>基础样式
                    </label>
                    <label id="title_news">
                        <input type="radio" value="2" name="title_show">新闻标题样式
                    </label>
                </td>
            </tr>
            <tr>
                <td>显示位置:</td>
                <td>
                    <label id="title_left">
                        <input type="radio" value="1" checked  name="title_position">居左
                    </label>
                    <label id="title_center">
                        <input type="radio" value="2" name="title_position">居中
                    </label>
                </td>
            </tr>
            <tr class="m_basic">
                <td>图标:</td>
                <td>
                    <div class="add_image">
                        <div style="text-align: center;position: absolute;top: 46px;left: 9px;"></div>
                        <img class="image" style="max-width:100%;max-height:100%;position: absolute;z-index: 8">
                    </div>
                    <a class="del-has-image" href="javascript:void(0)" title="删除图片"></a>

                </td>
            </tr>
            <tr class="m_basic">
                <td>字体颜色:</td>
                <td>
                    <input type="color" value="#333333" name="font_color"  style="height: 30px;width: 60px;border-radius: 0">
                    <input class="chongzhi" type="button" value="重置" onclick="$(this).prev().val('#333333');manager.change_show_module();">
                </td>
            </tr>
            <tr class="m_basic">
                <td>背景颜色:</td>
                <td>
                    <input type="color" value="#ffffff" name="bg_color"  style="height: 30px;width: 60px;border-radius: 0">
                    <input class="chongzhi" type="button" value="重置" onclick="$(this).prev().val('#ffffff');manager.change_show_module();">
                </td>
            </tr>
            <tr class="m_new">
                <td>日期:</td>
                <td>
                    <input type="text" name="title_date" placeholder="请选择日期" onfocus="(this.type='date')" id="date" onblur="(this.type='text')">
                </td>
            </tr>
            <tr class="m_new">
                <td>作者:</td>
                <td>
                    <input type="text" name="title_author"  maxlength="34" size="34">
                </td>
            </tr>
            <tr class="m_new">
                <td>链接标题:</td>
                <td>
                    <input type="text" name="link_title"  maxlength="20" size="34">
                    <span style="color: #a7a7a7;">最多20个字</span>
                </td>
            </tr>
            <tr>
                <td>链接:</td>
                <td><input type="text" name="title_link" id="title_link" maxlength=255 size="24">
                    <input type="button" value="选择链接" class="select_links">
                </td>
            </tr>
		</table>
    </div>
    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
</div>

<div class="row_item m_text">
    <div class="text_module">
        <span>点此添加一个【文本模块】</span>
    </div>
    <div class="item_module_title">
        <span>文本</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_text edit_text_modules">
    <div class="text_edit_module" style="position:relative;">
        <h2>文本模块</h2>
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td>文本：</td>
                <td><input type="text" name="title" id="title" maxlength=30 size="34">
                    <span style="color: #a7a7a7;display: inline" class="text_title">最多30个字</span></td>
            </tr>
            <tr>
                <td>字体大小：</td>
                <td>
                    <span><input type="radio" name="fonts_size" value="1">大</span>
                    <span><input type="radio" name="fonts_size" value="2" checked>中</span>
                    <span><input type="radio" name="fonts_size" value="3">小</span>
                </td>
            </tr>
            <tr>
                <td>字体颜色：</td>
                <td>
                    <input type="color"  name="fonts_color" value="#333333">
                    <input type="button" class="chongzhi" onclick="$(this).prev().val('#333333');manager.change_show_module();" value="重置">
                </td>
            </tr>
            <tr>
                <td>背景颜色：</td>
                <td>
                    <input type="color" name="bgs_color" value="#ffffff">
                    <input type="button" class="chongzhi" onclick="$(this).prev().val('#ffffff');manager.change_show_module();" value="重置">
                </td>
            </tr>
            <tr >
                <td>显示位置：</td>
                <td>
                    <span><input type="radio" name="show_pos" checked value="1">居左</span>
                    <span><input type="radio" name="show_pos" value="2">居中</span>
                    <span><input type="radio" name="show_pos" value="3">居右</span>
                </td>
            </tr>
            <tr>
                <td>链接：</td>
                <td><input type="text" name="title_link" id="title_link" maxlength=255 size="24">
                    <input type="button" value="选择链接" class="select_links">
                </td>
            </tr>

        </table>

    </div>
    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
</div>



<div class="row_item m_single_image">
	<div class="app-field clearfix ">
        <div class="control-group row_content">
            <ul class="sc-goods-list clearfix size-2 card pic">

                <li class="goods-card big-pic card">
                    <a class="clearfix">
                        <div class="photo-block">
                            <img class="goods-photo image" src="" style="max-width:100%;width: auto;max-height:100% !important;height: auto;margin: 0;">
                        </div>
                        <!-- <div class="info clearfix  info-price"> -->
                            <!-- <p class="goods-title bs_name">此处显示商品名称</p> -->
                            <!-- <p class="goods-price"><em class="bs_price">￥379.00</em></p> -->
							<!-- <p class="market_price"><em class="mk_price"></em></p> -->
                        <!-- </div> -->
                    </a>
					<div class="choice_layer_new hide">添加文字</div>
                </li>
            </ul>
        </div>
    </div>
    <div class="item_module_title">
        <span>图片广告</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_single_image edit_singleimg_modules">
    <div class="single_image_edit_module" style="position:relative;">
        <h2>单列图片模块
            <span>(可添加商品图或广告图)</span>
            <span>图片建议尺寸：600*300</span>
        </h2>
        <div class="is_preview clearfix" style="padding:0;">
            <span>预览原图：</span>
            <div class="radio_group">
                <label for="no">
                    <input type="radio" name="if_preview" id="no" value="0" checked="true">否
                </label>
                <label for="yes">
                    <input type="radio" name="if_preview" id="yes" value="1">是
                </label>
            </div>
        </div>
        <div class="is_preview tips clearfix" style="margin-bottom: 10px;padding: 0">
            <span></span>
            <div class="preview_tips">
                选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图
            </div>
        </div>
        <table class="auto_recommend" cellspacing='1' cellpadding='3'>
            <tr>
                <td>图片：</td>
                <td>
                    <div class="add_image single_add_img">
                        <div></div>
                        <img class="image" >
                    </div>

                </td>
            </tr>
            <tr>
                <td>链接：</td>
                <td>
                	<input type="text" name="title_link" id="title_link" maxlength=255 size="24">
                    <input type="button" value="选择链接" class="select_links">
					<input type="hidden" value="" class="name_value">
					<input type="hidden" value="" class="price_value">
				    <input type="hidden" value="" class="goods_value">
					<input type="hidden" value="" class="mk_price_val">
                </td>
            </tr>
			<tr class="text_area hide">
                <td>文本：</td>
                <td>
                	<input type="text" name="title" id="title" maxlength=20 size="34">
                    <span style="color: #a7a7a7;">最多19个字</span>
                </td>
            </tr>
			<tr class="hide_name hide">
				<td>隐藏商品名称：</td>
				<td>
                    <label id="hide_goods_name">
                    	<input type="checkbox" value="1" >隐藏商品名称
                    </label>
				</td>
			</tr>
			<tr class="hide_price hide">
				<td>隐藏商品价格：</td>
				<td>
                    <label id="hide_goods_price">
                    	<input type="checkbox" value="1">隐藏商品价格
                    </label>
				</td>
			</tr>
        </table>

        <!-- <div style="margin-top:15px;"> -->
            <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
        <!-- </div> -->
    </div>
</div>



<div class="row_item m_double_image">
    <div class="double_image_module">
	<div class="app-field clearfix">
        <div class="control-group row_content">
            <ul class="sc-goods-list clearfix size-2 card pic">

                <li class="goods-card small-pic card">
                    <a href="javascript: void(0);" class="link clearfix">
                        <div class="photo-block image_view">
                            <img class="goods-photo" src="">
                        </div>
                        <!-- <div class="info clearfix"> -->
                            <!-- <p class="goods-title bs_head">此处显示商品名称</p> -->
                            <!-- <p class="goods-price"><em class="bs_prix">￥5.50</em></p> -->
							<!-- <p class="market_price"><em class="mk_price"></em></p> -->
                        <!-- </div> -->
                    </a>
                </li>

                <li class="goods-card small-pic card">
                    <a href="javascript: void(0);" class="link clearfix">
                        <div class="photo-block image_view">
                            <img class="goods-photo" src="">
                        </div>
                        <!-- <div class="info clearfix"> -->
                            <!-- <p class="goods-title bs_head">此处显示商品名称</p> -->
                            <!-- <p class="goods-price"><em class="bs_prix">￥60.00</em></p> -->
							<!-- <p class="market_price"><em class="mk_price"></em></p> -->
                        <!-- </div> -->
                    </a>
                </li>
            </ul>
        </div>
    </div>

    </div>
    <div class="item_module_title">
        <span>图片广告</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_double_image edit_doubleimg_modules">
    <div class="double_image_edit_module" style="position:relative;">
        <h2>
            双列图片模块
            <span>图片建议尺寸：298*233</span>
        </h2>
        <div class="is_preview clearfix" style="padding:0;">
            <span>预览原图：</span>
            <div class="radio_group">
                <label for="no">
                    <input type="radio" name="if_preview" id="no" value="0" checked="true">否
                </label>
                <label for="yes">
                    <input type="radio" name="if_preview" id="yes" value="1">是
                </label>
            </div>
        </div>
        <div class="is_preview tips clearfix" style="margin-bottom: 10px;padding: 0">
            <span></span>
            <div class="preview_tips">
                选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图
            </div>
        </div>

        <table cellspacing='1' cellpadding='3' class="double_table">
            <tr>
                <td style="vertical-align: top !important;">第一张图片：</td>
                <td>
                    <div class="add_image1">
                        <div></div>
                        <img class="image1">
                    </div>
                    <img class="down_img1">
                </td>
            </tr>
            <tr>
                <td>第一个链接：</td>
                <td class="sth_links">
                	<input type="text" name="title_link1" id="title_link1" maxlength=255 size="24">
                    <input type="button" value="选择链接" class="select_links1">
					<input type="hidden" value="" class="name_value1">
					<input type="hidden" value="" class="price_value1">
					<input type="hidden" value="" class="goods_value1">
					<input type="hidden" value="" class="mk_price_val1">
                </td>
            </tr>
            <tr>
                <td style="vertical-align: top !important;">第二张图片：</td>
                <td >
                    <div class="add_image2">
                        <div></div>
                        <img class="image2">
                    </div>
                    <img class="up_img2">
                </td>
            </tr>
            <tr>
                <td>第二个链接：</td>
                <td class="sth_links">
                	<input type="text" name="title_link2" id="title_link2" maxlength=255 size="24">
                    <input type="button" value="选择链接" class="select_links2">
					<input type="hidden" value="" class="name_value2">
					<input type="hidden" value="" class="price_value2">
					<input type="hidden" value="" class="goods_value2">
					<input type="hidden" value="" class="mk_price_val2">
                </td>
            </tr>
			<!-- <tr class="hide_name hide"> -->
				<!-- <td>隐藏商品名称：</td> -->
				<!-- <td> -->
                    <!-- <label id="hide_goods_name"> -->
                    	<!-- <input type="checkbox" value="1" >隐藏商品名称 -->
                    <!-- </label> -->
				<!-- </td> -->
			<!-- </tr> -->
			<!-- <tr class="hide_price hide"> -->
			<!-- <td>隐藏商品价格：</td> -->
			<!-- <td> -->
                <!-- <label id="hide_goods_price"> -->
                	<!-- <input type="checkbox" value="1">隐藏商品价格 -->
                <!-- </label> -->
			<!-- </td> -->
			<!-- </tr> -->
        </table>

        <!-- <div style="margin-top:15px;"> -->
            <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
        <!-- </div> -->
    </div>
</div>



<div class="row_item m_multi_image" style="padding:5px;padding-left:0 ">
    <div class="title m_multi_image_title"><span>标题</span></div>
    <div class="multi_image_module">
        <table style="width:100%;">
            <tr>
                <td>
                    <div class="multi_image_view">
                        <img src="" alt="" style="max-width: 100%">
                    </div>
					<div class="bs_tit bs_caption"></div>
                    <div class="bs_num bs_rate" style="color:red;"></div>
                </td>
                <td>
                    <div class="multi_image_view">
                        <img src="" alt="" style="max-width: 100%">
                    </div>
					<div class="bs_tit bs_caption"></div>
                    <div class="bs_num bs_rate" style="color:red;"></div>
                </td>
                <td>
                    <div class="multi_image_view">
                        <img src="" alt="" style="max-width: 100%">
                    </div>
					<div class="bs_tit bs_caption"></div>
                    <div class="bs_num bs_rate" style="color:red;"></div>
                </td>
			</tr>

        </table>
	</div>
    <div class="item_module_title">
        <span>图片广告</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_multi_image edit_mulimg_modules" >
    <div class="multi_image_edit_module" style="position:relative;">
        <h2>
            多列图片模块
            <span> 图片最多6个，最少3个，建议尺寸：250*150</span>
        </h2>
        <div class="is_preview clearfix" style="padding:0;">
            <span>预览原图：</span>
            <div class="radio_group">
                <label for="no">
                    <input type="radio" name="if_preview" id="no" value="0" checked="true">否
                </label>
                <label for="yes">
                    <input type="radio" name="if_preview" id="yes" value="1">是
                </label>
            </div>
        </div>
        <div class="is_preview tips clearfix" style="margin-bottom: 10px;padding: 0">
            <span></span>
            <div class="preview_tips">
                选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图
            </div>
        </div>
        <table class="multi_image_tbl" cellspacing='1' cellpadding='3'>
            <tr>
                <td>
                    <span class="mul_title">标题：</span>
                    <input type="text" name="title" id="title" maxlength=10 size="20" style="width: 27%;margin-left: 4%">
                    <span style="color: #a7a7a7;">最多10个字</span>
                </td>
            </tr>
            <tr class="image_row_item">
                <td colspan=2>
                    <div class="image_item">
                        <table cellspacing='1' cellpadding='3'>
                            <tr>
                                <td rowspan="3" style="text-align: center">
                                    <div class="add_image deco_add_img">
                                        <img class="jiahao"  src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                                        <div></div>
                                        <img class="image">
                                    </div>
                                </td>

                            </tr>
                            <tr style="padding:0 10px">
                                <td>链接：</td>
                                <td><input type="text" name="title_link" id="title_link" maxlength=255 size="24">
                                    <input type="button" value="选择链接" class="select_links">
									<input type="hidden" value="" class="name_worth">
					                <input type="hidden" value="" class="price_worth">
									<input type="hidden" value="" class="goods_val">
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
            </tr>
        </table>
        <p style="margin-top: 15px"><input type="button" value="添加列表" class="add_row_item"></p>
    </div>
    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
</div>


<div class="row_item m_dashed_line">
    <div class="dashed_line">
        <hr/>
    </div>
    <div class="item_module_title">
        <span>辅助线</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="row_item m_scroll_image">
    <div class="scroll_image_module">
        <div class="scroll_image_view">
            <img src="" style="max-width:100%;">
            <div class="contain_circle">
                <div class="small_circle1 hide"></div>
                <div class="small_circle2 hide"></div>
                <div class="small_circle3 hide"></div>
                <div class="small_circle4 hide"></div>
                <div class="small_circle5 hide"></div>
                <div class="small_circle6 hide"></div>
            </div>
        </div>
        <div class="scroll_image_dot_nav">
            <span class="nav_dot"></span>
        </div>
    </div>
    <div class="item_module_title">
        <span>轮播图</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_scroll_image edit_scrollimg_modules">
    <div class="scroll_image_edit_module" style="position:relative;">
        <h2>轮播图片模块
            <span style="color:#9a9a9a;">图片最多6个，最少1个，建议尺寸：620*310</span>
        </h2>
        <div class="is_preview clearfix">
            <span>预览原图：</span>
            <div class="radio_group">
                <label for="no">
                    <input type="radio" name="if_preview" id="no" value="0" checked>否
                </label>
                <label for="yes">
                    <input type="radio" name="if_preview" id="yes" value="1">是
                </label>
            </div>
        </div>
        <div class="is_preview tips clearfix">
            <span></span>
            <div class="preview_tips">
                选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图
            </div>
        </div>
        <table class="scroll_image_tbl" cellspacing='1' cellpadding='3'>
            <tr class="image_row_item">
                <td colspan=2>
                    <div class="image_item">
                        <table cellspacing='1' cellpadding='3'>
                            <tr>
                                <td rowspan="2">
                                    <div class="add_image deco_add_img">
                                        <img class="jiahao"  src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                                        <div></div>
                                        <img class="image">
                                    </div>

                                </td>
                                <td>链接：</td>
                                <td><input type="text" name="title_link" id="title_link" maxlength=255 size="24">
                                    <input type="button" value="选择链接" class="select_links">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" class="position_change" style="padding-left: 15%;">
                                    <input type="button" value="↑" title="向上" class="up_arrow">
                                    <input type="button" value="↓" title="向下" class="down_arrow">
                                    <input type="button" value="X" title="移除" class="remove">
                                </td>
                            </tr>
                        </table>
                    </div>
            </tr>
        </table>
        <p><input type="button" value="+添加列表" class="add_row_item" style="margin-left: 2.5%"></p>
    </div>
    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
</div>



<div class="row_item m_blank" >
    <div class="m_blank_module" ></div>
    <div class="item_module_title">
        <span>辅助空白</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>
<div class="data_item d_m_blank edit_blank_modules">
    <div class="blank_edit_module" style="position:relative;">
        <h2>
            辅助空白模块
            <span>空白高度最高为60像素，最低10像素</span>
        </h2>
        <p>
            空白高度: &nbsp;<input type="text" name="blank_height" id="blank_height" min=10 max=60 value="10" maxlength=3 size="5">像素
        </p>
    </div>

    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
</div>



<div class="row_item m_rich_text">
    <div class="m_rich_text_module">
	    <div class="app-field clearfix">
                <div class="control-group">
                    <div class="custom-richtext" style="">
                        富文本
                        <!-- <p>点此编辑『富文本』内容 ——&gt;</p> -->
                        <!-- <p>你可以对文字进行<strong>加粗</strong>、<em>斜体</em>、<span style="text-decoration: underline;">下划线</span>、<span style="text-decoration: line-through;">删除线</span>、文字<span style="color: rgb(0, 176, 240);">颜色</span>、<span style="background-color: rgb(255, 192, 0); color: rgb(255, 255, 255);">背景色</span>、以及字号<span style="font-size: 20px;">大</span><span style="font-size: 14px;">小</span>等简单排版操作。</p> -->
                        <!-- <p>还可以在这里加入表格了</p> -->
                        <!-- <table> -->
                            <!-- <tbody> -->
                            <!-- <tr> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;">中奖客户</td> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;">发放奖品</td> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;">备注</td> -->
                            <!-- </tr> -->
                            <!-- <tr> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;">猪猪</td> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;">内测码</td> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;"><em><span style="color: rgb(255, 0, 0);">已经发放</span></em></td> -->
                            <!-- </tr> -->
                            <!-- <tr> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;">大麦</td> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;">积分</td> -->
                                <!-- <td width="93" valign="top" style="word-break: break-all;"><a href="javascript: void(0);" target="_blank">领取地址</a></td> -->
                            <!-- </tr> -->
                            <!-- </tbody> -->
                        <!-- </table> -->
                        <!-- <p style="text-align: left;"><span style="text-align: left;">也可在这里插入图片、并对图片加上超级链接，方便用户点击。</span></p> -->
                    </div>
                </div>

        </div>
	</div>
    <div class="item_module_title">
        <span>富文本</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>
<div class="data_item d_m_rich_text">
    <div class="rich_text_edit_module" style="position:relative;">
        <h2 style="font-size: 14px;border-bottom: 1px solid #eee;padding-bottom: 7px;">富文本模块(最多上传30个此模块)</h2>
    </div>
    <div style="position: relative;top: 450px;">
        <input class="btn btn-primary queren " type='button' id="ok" name="ok" value="确定">
    </div>
</div>


<div class="row_item m_image_small">
    <div class="single_image_module_small">
        <div class="single_img">
            <p>点击编辑广告图片</p>
            <p>建议宽度630像素</p>
            <img src="" alt="" style="min-width: 100%" class="image">
        </div>
        <div class="single_img_title hide"><a target="_blank"></a></div>
    </div>
    <div class="item_module_title">
        <span>图片广告</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_image_small edit_qiang_modules">
    <div class="single_image_edit_module" style="position:relative;">
        <h2>
            广告图片模块
            <span>图片建议尺寸：宽度630</span>
        </h2>
        <div class="is_preview clearfix" style="padding:0;">
            <span style="text-align: right">预览原图：</span>
            <div class="radio_group" style="margin-left: 10px">
                <label for="no">
                    <input type="radio" name="if_preview" id="no" value="0" checked="true">否
                </label>
                <label for="yes">
                    <input type="radio" name="if_preview" id="yes" value="1">是
                </label>
            </div>
        </div>
        <div class="is_preview tips clearfix" style="margin-bottom: 10px;padding: 0">
            <span></span>
            <div class="preview_tips"  style="margin-left: 10px">
                选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图
            </div>
        </div>

        <table class="auto_recommend" cellspacing='1' cellpadding='3'>
            <tr>
                <td>图片：</td>
                <td class="tupian">
                    <input type="button" value="" class="add_image">
                    <span class="build_for_del">
                        <img class="image" style="max-width:70px; max-height:70px;">
                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_icon">
                    </span>
                </td>
            </tr>
            <tr>
                <td>文本：</td>
                <td><input type="text" name="title" id="title" maxlength=20 size="34">
                    <span style="color:#a7a7a7;">最多19个字</span></td>
            </tr>
            <tr>
                <td>链接：</td>
                <td><input type="text" name="title_link" id="title_link" maxlength=255 size="24" style="margin-right: 0">
                    <input type="button" value="选择链接" class="select_links">
                </td>
            </tr>
        </table>
    </div>
    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren " type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
</div>


<div class="row_item m_image_guide">
    <div class="you_con">
    <div class="content_img_row clearfix" style="width:auto;">
        <a class="image_view_" >
            <div class="guide_nodel">
                <img src="" alt="">
            </div>
            <p class="title_set">导航一</p>
        </a>
        <a class="image_view_" >
            <div class="guide_nodel">
                <img src="" alt="">
            </div>
            <p class="title_set">导航二</p>
        </a>
        <a class="image_view_">
            <div class="guide_nodel">
                <img src="" alt="">
            </div>
            <p class="title_set">导航三</p>
        </a>
        <a class="image_view_" >
            <div class="guide_nodel">
                <img src="" alt="">
            </div>
            <p class="title_set">导航四</p>
        </a>
		<a class="image_view_  image_five" >
            <div class="guide_nodel">
                <img src="" alt="">
            </div>
            <p class="title_set">导航五</p>
        </a>
    </div>
</div>
    <div class="item_module_title">
        <span>图片导航</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>


<div class="data_item d_m_image_guide edit_guideimg_modules">
    <div class="double_image_edit_module" style="position:relative;">
        <h2>
            图片导航模块
            <span>图片建议尺寸：140*140</span>
        </h2>
        <div class="guide_style guide_nav clearfix">
            <span style="float: left" class="sp_title">导航样式：</span>
            <div class="guide_circle guide_nav_style guide_select" module-guide="1">
                <div>
                    <div></div>
                    <p>导航一</p>
                </div>
                <div>
                    <div></div>
                    <p>导航二</p>
                </div>
                <div>
                    <div></div>
                    <p>导航三</p>
                </div>
            </div>
            <div class="guide_rect guide_nav_style" module-guide="2">
                <div>
                    <div></div>
                    <p>导航一</p>
                </div>
                <div>
                    <div></div>
                    <p>导航二</p>
                </div>
                <div>
                    <div></div>
                    <p>导航三</p>
                </div>
            </div>
        </div>
        <div class="guide_style clearfix">
            <span style="float: left" class="sp_title">字体颜色：</span>
            <div class="font_colorss" style="float: left;">
                <input type="color"  name="fonts_color" value="#92b0e4">
                <input type="button" class="chongzhi" onclick="$(this).prev().val('#92b0e4');manager.change_show_module();" value="重置">
            </div>
        </div>
        <div class="guide_style clearfix">
            <span style="float: left" class="sp_title">背景颜色：</span>
            <div class="font_colorss" style="float: left;">
                <input type="color" name="bgs_color" value="#ffffff">
                <input type="button" class="chongzhi" onclick="$(this).prev().val('#ffffff');manager.change_show_module();" value="重置">
            </div>
        </div>
		<ul class="ul-sortable">
            <li class="choice clearfix clone_one">
                <div class="choice-image">
                    <a class="add-image add_image1" href="javascript: void(0);">
                        <div class="choice_ach">
                        <img class="image1" style="max-width:100%;max-height: 100%;position: initial;"></div>
                    </a>
                </div>
                <div class="choice-content">
                    <div class="control-group">
                        <label class="control-label">文字：</label>
                        <div class="controls">
                            <input class="choice_con" type="text"  value="" maxlength="5" >
                        </div>
                    </div>
                    <div class="control-group clearfix">
                        <label class="control-label">链接：</label>
                        <input type="text" class="choice_url"  maxlength=255 size="24" placeholder="文字链接可为空"/>
                        <input class="sortable_btn select_links1" type="button"  value="选择链接" style="height: 30px;margin-top: -7px">
                    </div>
                </div>
                <div class="ab_choice">
                    <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                    <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
                    <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
                </div>
            </li>
            <li class="choice clearfix">
                <div class="choice-image">
                    <a class="add-image add_image1" href="javascript: void(0);">
                        <div class="choice_ach">
                            <img class="image1" style="max-width:100%;max-height: 100%;position: initial;"></div>
                    </a>
                </div>
                <div class="choice-content">
                    <div class="control-group">
                        <label class="control-label">文字：</label>
                        <div class="controls">
                            <input class="choice_con" type="text"  value="" maxlength="5">
                        </div>
                    </div>
                    <div class="control-group clearfix">
                        <label class="control-label">链接：</label>
                        <input type="text" class="choice_url"  maxlength=255 size="24" placeholder="文字链接可为空"/>
                        <input class="sortable_btn select_links1" type="button"  value="选择链接" style="height: 30px;margin-top: -7px">
                    </div>
                </div>
                <div class="ab_choice">
                    <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                    <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
                    <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
                </div>
            </li>
            <li class="choice clearfix">
                <div class="choice-image">
                    <a class="add-image add_image1" href="javascript: void(0);">
                        <div class="choice_ach">
                            <img class="image1" style="max-width:100%;max-height: 100%;position: initial;"></div>
                    </a>
                </div>
                <div class="choice-content">
                    <div class="control-group">
                        <label class="control-label">文字：</label>
                        <div class="controls">
                            <input class="choice_con" type="text"  value="" maxlength="5">
                        </div>
                    </div>
                    <div class="control-group clearfix">
                        <label class="control-label">链接：</label>
                        <input type="text" class="choice_url"  maxlength=255 size="24" placeholder="文字链接可为空"/>
                        <input class="sortable_btn select_links1" type="button"  value="选择链接" style="height: 30px;margin-top: -7px">
                    </div>
                </div>
                <div class="ab_choice">
                    <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                    <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
                    <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
                </div>
            </li>
            <li class="choice clearfix">
                <div class="choice-image">
                    <a class="add-image add_image1" href="javascript: void(0);">
                        <div class="choice_ach">
                            <img class="image1" style="max-width:100%;max-height: 100%;position: initial;"></div>
                    </a>
                </div>
                <div class="choice-content">
                    <div class="control-group">
                        <label class="control-label">文字：</label>
                        <div class="controls">
                            <input class="choice_con" type="text"  value="" maxlength="5">
                        </div>
                    </div>
                    <div class="control-group clearfix">
                        <label class="control-label">链接：</label>
                        <input type="text" class="choice_url"  maxlength=255 size="24" placeholder="文字链接可为空"/>
                        <input class="sortable_btn select_links1" type="button"  value="选择链接" style="height: 30px;margin-top: -7px">
                    </div>
                </div>
                <div class="ab_choice">
                    <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                    <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
                    <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
                </div>
            </li>
        </ul>
        <p style="padding-left:10px;margin-top:10px"><input type="button" value="添加列表" class="add_row_item"></p>
    </div>

</div>


<!-- 新的图片广告模块 -->
<div class="row_item m_image_adver">
    <!-- 没上传的样式 -->
    <div class="adver_image_module_small">
        <div class="single_img">
            <p>点击编辑图片广告</p>
            <p style="font-size: 12px">建议宽度750像素</p>
            <img src="" alt="" style="min-width: 100%" class="image">
        </div>
    </div>
    <!-- 单列图（合并了之前的广告图，只建议尺寸，不强制） -->
    <div class="new_image_type once_image_area hide">
        <li class="each_once once_image_area_model" style="display: none;">
            <img src="" alt="" style="max-width: 100%;min-width: 100%;"><a class="title"></a>
        </li>
    </div>
    <!-- 双列图 -->
    <ul class="new_image_type twice_img clearfix hide">
        <li class="each_two twice_img_model" style="display: none;">
            <div style="position: relative;">
                <img src="" alt=""><a class="title"></a>
            </div>
        </li>
    </ul>
    <!-- 横向滑动大 -->
    <div class="new_image_type slide_big clearfix hide">
        <div class="each_big slide_big_model" style="display: none;">
            <img src="" alt=""><a class="title"></a>
        </div>
    </div>
    <!-- hengxaing滑动小 -->
    <div class="new_image_type slide_small clearfix hide">
        <div class="each_small slide_small_model" style="display: none;"><img src="" alt=""><a class="title"></a></div>
    </div>
    <!-- 横向滑动导航 -->
    <div class="slide_nav_title clearfix" style="display: none"></div>
    <div class="new_image_type slide_nav clearfix hide">
        <div class="each_nav slide_nav_model" style="display: none;"><img src="" alt=""><a class="title"></a></div>
    </div>
    <div class="item_module_title">
        <span>图片广告</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_image_adver edit_adver_modules">
    <div class="adver_image_edit_module" style="position:relative;">
        <h2>图片广告</h2>
        <div class="choose_style clearfix">
            <span>选择模板：</span>
            <ul class="clearfix">
                <li class="image-type one_img" image-type="0">
                    <div></div><div></div>
                    <p class="style_words">单列图片</p>
                </li>
                <li class="image-type two_img clearfix" image-type="1">
                    <div></div><div></div>
                    <p class="style_words">双列图片</p>
                </li>
                <li class="image-type big_mul clearfix" image-type="2">
                    <div></div><div></div>
                    <p class="style_words">横向滑动(大)</p>
                </li>
                <li class="image-type small_mul clearfix" image-type="3">
                    <div></div><div></div><div style="width: 20%"></div>
                    <p class="style_words">横向滑动(小)</p>
                </li>
                <li class="image-type nav_mul clearfix" image-type="4">
                    <div></div><div></div><div style="width: 20%"></div>
                    <p class="style_words">横向滑动(导航)</p>
                </li>
            </ul>
        </div>
        <div class="is_preview clearfix" style="padding:0;">
            <span style="text-align: right">预览原图：</span>
            <div class="radio_group" style="margin-left: 10px">
                <label for="no">
                    <input type="radio" name="if_preview" id="no" value="0" checked="true">否
                </label>
                <label for="yes">
                    <input type="radio" name="if_preview" id="yes" value="1">是
                </label>
            </div>
        </div>
        <div class="is_preview tips clearfix" style="margin-bottom: 10px;padding: 0">
            <span></span>
            <div class="preview_tips"  style="margin-left: 10px">
                选择是，则在图片没有添加链接时，前端用户点击可以预览原图。选择否，则未添加图片链接时，不可预览原图
            </div>
        </div>
        <div class="img_margin clearfix">
            <span>图片间隙：</span>
            <input type="range" name="points" min="0" max="20" value="0" onchange="$(this).next().val($(this).val())">
            <input name="image_space" type="text" value="0">
        </div>
        <div class="module_title clearfix">
            <span>模块标题：</span>
            <input name="module_title" type="text" value="">
        </div>
        <div class="new_image_list">

        </div>
        <div class="add_img_area">
            <div class="tinajia_img">
                + 添加图片
            </div>
            <div class="advise_size">建议宽度<text class="advise_size_int">750</text>px</div>
        </div>
    </div>
    <!-- <div style="margin-top:15px;"> -->
        <!-- <input class="btn btn-primary queren " type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
    <div class="img_area_model" hidden>
        <div class="each_img_li clearfix">
            <div class="img_weizhi add_image" >
                <img class="image" src="" alt="">
                <p>更换图片</p>
            </div>
            <div class="img_info">
                <div class="img_words clearfix">
                    <span>文本：</span>
                    <input type="text" name="title" maxlength="6" size="34" class="img_wenzi">
                </div>
                <div class="img_words_tips clearfix">
                    <span></span>
                    <p>限制六字，为空则不展示图片标题</p>
                </div>
                <div class="img_url clearfix">
                    <span>链接：</span>
                    <div>
                        <input type="text" name="title_link" id="title_link" maxlength=255 size="24" style="margin-right: 0">
                        <input type="button" value="选择链接" class="select_links">
                    </div>
                </div>
            </div>
            <div style="float: left;margin-top: 10px;margin-left: 90px">
                <input type="button" value="↑" title="向上" class="up_arrow">
                <input type="button" value="↓" title="向下" class="down_arrow">
                <input type="button" value="X" title="移除" class="remove">
            </div>
        </div>

    </div>
</div>
<div class="row_item m_text_image">
    <div class="text_image_module">

    </div>
    <div class="item_module_title">
        <span>左图右文</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>



<div class="data_item d_m_text_image edit_textimg_modules">
    <div class="text_image_edit_module" style="position:relative;">
        <h2>左图右文（最多上传10个此模块）</h2>
        <div class="ti_type clearfix">
            <table>
                <tr><span class="choose_style">选择样式：</span></tr>
                <tr>
                    <div class="radio_group">
                        <div style="margin-right:4%;" class="radio_box">
                            <div class="style_top clearfix">
                                <img src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png" alt="" class="style_top_img fl m_r_15">
                                <p class="style_top_text">文本文本文本文本文本文本文本文本文本文本文本文本文本文本</p>
                            </div>
                            <label for="left_img">
                                <input type="radio" name="ti_type" id="left_img" value="0" checked>样式1  (左图右文)
                            </label>
                        </div>
                        <div class="radio_box">
                            <div class="style_top clearfix">
                                <img src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png" alt="" class="style_top_img fr">
                                <p class="style_top_text" style="margin-right:73px;">文本文本文本文本文本文本文本文本文本文本文本文本文本文本</p>
                            </div>
                            <label for="right_img">
                                <input type="radio" name="ti_type" id="right_img" value="1">样式2 (左文右图)
                            </label>
                        </div> 
                    </div>
                <tr>
            </table>
        </div>
        <div class="img_style clearfix">
            <span>样式大小：</span>
            <div class="radio_group">
                <label for="big_img">
                    <input type="radio" name="img_style" id="big_img" value="0" checked>大图样式
                </label>
                <label for="small_img" style="margin-left:30px;">
                    <input type="radio" name="img_style" id="small_img" value="1">小图样式
                </label>
            </div>
        </div>
        <table class="text_image_tbl" cellspacing='1' cellpadding='3'>
            <tr class="text_image_row_item">
                <td colspan=2>
                    <div class="text_image_item">
                        <table cellspacing='1' cellpadding='3'>
                            <tr class="clearfix">
                                <td>
                                    图片：
                                </td>
                                <td rowspan="2">
                                    <div class="add_image deco_add_img">
                                        <img class="jiahao"  src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                                        <div></div>
                                        <img class="image">
                                    </div>
                                    <span style="color:#9a9a9a;">建议尺寸：360px*360px</span>
                                </td>

                            </tr>
                            <tr class="clearfix">
                                <td>链接：</td>
                                <td><input type="text" name="title_link" id="title_link" maxlength=255 size="24">
                                    <input type="button" value="选择链接" class="select_links">
                                </td>
                            </tr>
                        </table>
                    </div>
            </tr>
        </table>
        <div class="text clearfix">
            <span class="fl" class="margin-bottom:10px;">文本内容：</span>
            <div class="text_box">
            <div class="u_editor_text">
                <textarea id="editor_text" style="width:100%;height:100px;"></textarea>
            </div>
            </div>
        </div>
    </div>
    <!-- <div style="margin-top:15px;"> -->
    <!-- <input class="btn btn-primary queren" type='button' id="ok" name="ok" value="确定"> -->
    <!-- </div> -->
</div>
<div class="text_image_view_big_left">
    <img src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png" alt="" class="big_left t_i_img">
    <div class="big_right t_i_text">
        请输入文本
    </div>
</div>
<div class="text_image_view_big_right">
    <img src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png" alt="" class="big_right t_i_img">
    <div class="big_left t_i_text">
        请输入文本
    </div>
</div>
<div class="text_image_view_small_left">
    <img src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png" alt="" class="small_left t_i_img">
    <div class="small_right t_i_text">
        请输入文本
    </div>
</div>
<div class="text_image_view_small_right">
    <img src="http://${image_domain!}/image/admin/shop_beautify/decorate_model.png" alt="" class="small_right t_i_img">
    <div class="small_left t_i_text">
        请输入文本
    </div>
</div>








<script language="JavaScript" src="/js/admin/shop_decorate_m_pictxt_up.js?v=1.0.6"></script>



