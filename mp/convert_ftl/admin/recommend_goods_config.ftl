<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/recommend_goods_manage.css?v=1.0.9" type="text/css" />
<style type="text/css">
    .reco_config{
        min-height: 700px;
    }
</style>
<div class="title">
    <span>商品管理 / </span><span><a href="/admin/goods/recommend/list?top_index=2">商品推荐</a> / </span><span>${title!}</span>
</div>
<div class="recommend_container fix_every_footer">
    <form action="/admin/goods/recommend/config?top_index=2" method="post" id="form1">
        {{ csrf_field()!}
        <input name="id" hidden value="${recommend->id!}">
        <div class="reco_config">
            <div class="reco_content">
                <ul>
                    <li class="re_lis clearfix">
                        <span class="re_title"><em>*</em>推荐模板名称：</span>
                        <input name="recommend_name" type="text" onBlur="check_recommend_name()" value="${recommend->recommend_name!}" maxlength="20" class="act_names">
                        <span class="re_tips">只作为商家记录使用，用户不会看到这个名称</span>
                    </li>
                    <li class="re_lis clearfix" style="padding-bottom: 0">
                        <span class="re_title"><em>*</em>添加商品：</span>
                        <span class="re_tips">将从已添加商品中随机选出6个商品进行展示</span>
                    </li>
                    <li class="re_lis clearfix" style="padding-top: 0">
                        <span class="re_title"><em></em></span>
                        <div class="goods_sort">
                            <div class="choose_type">
                                <input type="radio" name="recommend_type" value="0" checked>全部商品
                                <input type="radio" name="recommend_type" value="1" <#if ($recommend->recommend_type == 1) checked </#if>>指定商品
                            </div>
                            <input  type="hidden" name="recommend_goods_id" value="${recommend->recommend_goods_id!}">
                            <input  type="hidden" name="recommend_cat_id" value="${recommend->recommend_cat_id!}">
                            <input  type="hidden" name="sort_ids" value="${recommend->recommend_sort_id!}">
                            <div class="choose_goods">
                                <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                添加商品
                            </div>

                            <div class="goods_area">
                                <table class="goods_modal" <#if ($recommend->recommend_goods_id) style="display: block;" </#if>>
                                    <thead>
                                    <tr>
                                        <td width="50%">商品名称</td>
                                        <td width="15%">价格</td>
                                        <td width="15%">库存</td>
                                        <td width="20%">操作</td>
                                    </tr>
                                    </thead>
                                    <tbody class="tbody">
                                    <#if ($recommend->recommend_goods_id)
                                        <#list ($recommend->goods_array as $item)
                                            <tr>
                                                <td>
                                                    <div class="goods_info clearfix">
                                                        <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                                        <div class="goods_name">
                                                            ${item->goods_name!}
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>${item->shop_price!}</td>
                                                <td>${item->goods_number!}</td>
                                                {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                <td><a href="javascript:void(0)" item="${item->goods_id!}" class="change_goods_del">删除</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                    </tbody>
                                </table>
                                {{--<#if ($recommend->recommend_goods_id)--!}
                                    {{--<table class="goods_table" width="100%" goods_array="${recommend->recommend_goods_id!}">--!}
                                        {{--<thead>--!}
                                        {{--<tr>--!}
                                            {{--<td width="50%">商品名称</td>--!}
                                            {{--<td width="15%">价格</td>--!}
                                            {{--<td width="15%">库存</td>--!}
                                            {{--<td width="20%">操作</td>--!}
                                        {{--</tr>--!}
                                        {{--</thead>--!}
                                        {{--<tbody>--!}
                                        {{--<#list ($recommend->goods_array as $item)--!}
                                            {{--<tr>--!}
                                                {{--<td width="50%">--!}
                                                    {{--<div class="goods_info clearfix">--!}
                                                        {{--<div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>--!}
                                                        {{--<div class="goods_name">--!}
                                                            {{--${item->goods_name!}--!}
                                                        {{--</div>--!}
                                                    {{--</div>--!}
                                                {{--</td>--!}
                                                {{--<td width="15%">${item->shop_price!}</td>--!}
                                                {{--<td width="15%">${item->goods_number!}</td>--!}
                                                {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                {{--<td width="20%"><a href="javascript:void(0)" goods_id="${item->goods_id!}" class="del">删除</a></td>--!}
                                            {{--</tr>--!}
                                        {{--</#list>--!}
                                        {{--</tbody>--!}
                                    {{--</table>--!}

                                {{--<#else>--!}
                                    {{--<table class="goods_modal" width="100%">--!}
                                        {{--<thead>--!}
                                            {{--<tr>--!}
                                                {{--<td width="50%">商品名称</td>--!}
                                                {{--<td width="15%">价格</td>--!}
                                                {{--<td width="15%">库存</td>--!}
                                                {{--<td width="20%">操作</td>--!}
                                            {{--</tr>--!}
                                        {{--</thead>--!}
                                        {{--<tbody></tbody>--!}
                                    {{--</table>--!}
                                {{--</#if>--!}
                            </div>
                            
                            <div class="add_sort">
                                <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                添加商家分类
                            </div>
                            <div class="sort_area">
                                <table class="sort_table" width="600px" sort_array="${strategy->recommend_sort_id!}">
                                    <tr>
                                        <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_sort_cls">编辑</a><a href="javascript:;" class="del_sort_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                            </div>
                            <div class="add_cate">
                                <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                添加平台分类
                            </div>

                            <div class="sort_area">
                                
                                <table class="cat_table" width="600px" cat_array="${strategy->recommend_cat_id!}">
                                    <tr>
                                        <th width="100%"><span>平台分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_cls">编辑</a><a href="javascript:;" class="del_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                                <!-- <#if ($recommend->recommend_cat_id)
                                <#else>
                                    <table class="cat_modal" width="100%">
                                        <thead>
                                            <tr>
                                                <td width="70%">商品分类</td>
                                                <td width="30%">操作</td>
                                            </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </#if> -->
                            </div>
                        </div>
                    </li>
                    <li class="re_lis clearfix">
                        <span class="re_title"><em>*</em>应用页面：</span>
                        <div class="check_group">
                            <ul>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="cart"
                                    <#if ($recommend->recommend_use_page && in_array('cart',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">购物车页</span>
                                    <span class="check_tips">展示在购物车页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_cart.png" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="orderlist"
                                    <#if ($recommend->recommend_use_page && in_array('orderlist',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">订单列表页</span>
                                    <span class="check_tips">展示在订单列表页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_order.png" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="bargainitem"
                                    <#if ($recommend->recommend_use_page && in_array('bargainitem',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">砍价活动页</span>
                                    <span class="check_tips">展示在砍价活动页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_bargin.png" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="groupbuyitem"
                                    <#if ($recommend->recommend_use_page && in_array('groupbuyitem',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">参团活动页</span>
                                    <span class="check_tips">展示在参团活动页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_group.jpg" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="search"
                                           <#if ($recommend->recommend_use_page && in_array('search',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">商品列表页</span>
                                    <span class="check_tips">展示在商品列表页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_list.jpg" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="payment"
                                           <#if ($recommend->recommend_use_page && in_array('payment',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">支付成功页</span>
                                    <span class="check_tips">展示在支付成功页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_success.jpg" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="order_complete"
                                           <#if ($recommend->recommend_use_page && in_array('order_complete',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">订单完成页</span>
                                    <span class="check_tips">展示在订单完成页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_order_complete.jpg" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="recommend_use_page[]" value="new_search"
                                           <#if ($recommend->recommend_use_page && in_array('new_search',json_decode($recommend->recommend_use_page,true))) checked </#if> >
                                    <span class="check_item">商品搜索页</span>
                                    <span class="check_tips">展示在商品搜索页底部，用于商品推荐</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/recommend/ex_search.jpg" alt="">
                                        </div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="re_lis clearfix">
                        <span class="re_title"><em>*</em>启用状态：</span>
                        <div class="radio_group">
                            <ul>
                                <li class="clearfix">
                                    <input type="radio" name="status" value="0" <#if ($recommend->status == 0) checked </#if>>
                                    <span class="radio_item">立即启用</span>
                                    <span class="use_tips">每个页面最多可启用一个推荐模板，启用后显示在对应页面，如有重复，则不启用当前模板</span>
                                </li>
                                <li class="clearfix">
                                    <input type="radio" name="status" value="1" <#if ($recommend->status == 1) checked </#if>>
                                    <span class="radio_item">暂时停用</span>
                                    <span class="use_tips">暂时不启用该模板，后续可根据需要启用</span>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
    <div id="set-category">
        <#if (!$cat_list)
            <div class="no_category">
                <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无分类</p>
                </div>    
            </div>
        <#else>
        <ul>
            <#if ($cat_list)
                <#list ($cat_list as $item)
                    <li class="cate_li">
                        <div class="first_cate">
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" />
                            <span>
                        <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                        <label for="cat_${item->cat_id!}">${item->cat_name!}(${item->goods_num!})</label>
                    </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                            <span>
                                <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                <label for="cat_${sub_item->cat_id!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                            </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                            <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                            <label for="cat_${th_item->cat_id!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                        </span>
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </#list>
                            </div>
                        </#if>
                    </li>
                </#list>
            </#if>
        </ul>
        </#if>
    </div>
    <table  class="goods_modal_clone hide">
        <tr>
            <td></td>
            <td></td>
            <td>上架</td>
            <td><a href="javascript:void(0)"  class="del">删除</a></td>
        </tr>
    </table>
    <!-- <table class="cat_modal_clone hide">
        <tr>
            <td></td>
            <td><a href="javascript:void(0)"  class="del_cat">删除</a></td>
        </tr>
    </table> -->
    <div class="btn_save fix_footer">
        <a href="##">保存</a>
    </div>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/recommend_manage.js?v=1.1.13"></script>
<script type="text/javascript" src="/js/admin/select_sort.js?v=1.0.2"></script>
<script type="text/javascript">
    var cat_id = "${recommend->recommend_cat_id!}";
    var goods_id = '${recommend->recommend_goods_id!}';
    // $("body").on('click','.del',function(){
    //     var del_goods_id = $(this).attr('goods_id');
    //     var goods = $('input[name="recommend_goods_id"]').val();
    //     if(isNaN(goods)) {
    //         var goods_array = goods.split(',');
    //         for (var i = 0; i < goods_array.length; i++) {
    //             if (goods_array[i] == del_goods_id) {
    //                 goods_array.splice(i, 1);
    //                 break;
    //             }
    //         }
    //         $('input[name="recommend_goods_id"]').val(goods_array.join());
    //     }
    //     else{
    //         $('input[name="recommend_goods_id"]').val('');
    //     }
    //     $(this).parent().parent().remove();
    //     if (goods_id && $('.goods_table tr').length == 1) {
    //         $('.goods_table').hide();
    //     }
    //     if (!goods_id && $('.goods_modal tr').length == 1) {
    //         $('.goods_modal').hide();
    //     }
    //     check_goods_area_height()
    // });
    // $("body").on('click','.del_cat',function(){
    //     var del_cat_id = $(this).attr('cat_id');
    //     var cat = $('input[name="recommend_cat_id"]').val();
    //     if(isNaN(cat)) {
    //         var cat_array = cat.split(',');
    //         for (var i = 0; i < cat_array.length; i++) {
    //             if (cat_array[i] == del_cat_id) {
    //                 cat_array.splice(i, 1);
    //                 break;
    //             }
    //         }
    //         // console.log(cat_array);
    //         $('input[name="recommend_cat_id"]').val(cat_array.join());
    //     }
    //     else{
    //         $('input[name="recommend_cat_id"]').val('');
    //     }
    //     $(this).parent().parent().remove();
    //     $("#cat_" + del_cat_id).prop('checked',false);
    //     if (cat_id) {
    //         if($('.cat_table tr').length == 1) $('.cat_table').hide();
    //     } else {
    //         if($('.cat_modal tr').length == 1) $('.cat_modal').hide();
    //     }
    // });
    $("body").on('click','.del_cat',function(){
        $('input[name="recommend_cat_id"]').val('');
        $('.cat_table').find('tr').eq(1).remove();
        $('#set-category ul input[type="checkbox"]:checked').each(function(){
            $(this).prop('checked',false);
        });
        $('.cat_table').hide();
    })
    function check_recommend_name() {
        var recommend_id = $('input[name="id"]').val()
        var recommend_name = $('.act_names').val()
        if(recommend_name){
            util.ajax_json('/admin/ajax/recommend/config/check_name',function (res) {
                if (res.error == 0) {
                    util.mobile_alert('该模板名称已存在')
                }
            },{recommend_id: recommend_id,recommend_name:recommend_name})
        }
    }
    function check_goods_area_height(){
        let goods_modal = $('.goods_modal').outerHeight();
        let goods_table = $('.goods_table').outerHeight();
        if(goods_modal > 300){
            $('.goods_area').css({
                'height': '300px',
                'overflow-y': 'scroll',
            })
        } else {
            $('.goods_area').css({
                'height': 'auto',
                'overflow-y': 'auto',
            })
        }
    }
    $(".fix_footer").outerWidth($('.fix_every_footer').width());


    $(".show_ex").each(function () {
        $(this).hover(function () {
            $(".show_ex").find('img').css('display','none');
            $(this).find('img').css('display','block');
        },function () {
            $(".show_ex").find('img').css('display','none');
        })
    })
    var hasSaved = true;
    util.inputChange();
    util.radioChange('is_use');
    util.radioChange('is_all');
    util.checkboxChange();
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
</script>