<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_label.css?v=1.1.2" type="text/css" />
<div class="title">
    <span>商品管理 / </span><span><a href="/admin/goods/label/list?top_index=2">商品标签</a> / </span><span>${title!}</span>
</div>
<div class="recommend_container fix_every_footer">
    <form action="/admin/goods/label/add?top_index=2" method="post" id="form1">
        {{ csrf_field()!}
        <input name="id" hidden value="${goodsLabel->id!}">
        <div class="reco_config" style="min-height: 650px">
            <div class="reco_content">
                <ul>
                    <li class="re_lis clearfix" style="position: relative">
                        <span class="re_title"><em>* </em>标签名称：</span>
                        <input name="name" type="text" onBlur="check_label_name()" id="label_name" value="${goodsLabel->name!}" maxlength="8" class="label_name" placeholder="请填写标签名称" autocomplete="off">
                        <span class="re_tips">每个商品最多可以设置5个标签，若超出，优先级最低的标签将自动失效</span>
                        <div class="down_label" >
                            <span >推荐：</span>
                            <span class="tc_label">新品首发</span>
                            <span class="tc_label">人气推荐</span>
                            <span class="tc_label">特价商品</span>
                            <span class="tc_label">今日特价</span>
                        </div>
                    </li>
                    <li class="re_lis clearfix">
                        <span class="re_title"><em>* </em>优先级：</span>
                        <input name="level" type="text"  value="${goodsLabel->level!}" class="label_level" style="width: 50px">
                        <span class="re_tips">用于确定商品标签的优先程度，请填写正整数，数值越大，优先级越高</span>
                    </li>
                    <li class="re_lis clearfix">
                        <span class="re_title"><em> </em>前端应用模块：</span>
                        <div class="check_group" style="position: relative">
                            <ul>
                                <li>
                                    <input type="checkbox" name="goods_detail" value="1" class="gd_detail"
                                           <#if ($goodsLabel->goods_detail) checked </#if> >
                                    <span class="label_item">商品详情页</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/label/xiangqing.jpg" alt="">
                                        </div>
                                    </a>
                                </li>
                                <li>
                                    <input type="checkbox" name="goods_list" value="1" class="gd_list"
                                           <#if ($goodsLabel->goods_list) checked </#if> >
                                    <span class="check_item">商品列表</span>
                                </li>
                                <li>
                                    <input type="checkbox" name="goods_select" value="1" class="gd_detail"
                                           <#if ($goodsLabel->goods_select) checked </#if> >
                                    <span class="label_item">商品筛选页</span>
                                    <a href="javascript:;" class="show_eg">查看示例
                                        <div class="hover_show">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/label/shaixuan.jpg" alt="">
                                        </div>
                                    </a>
                                </li>
                            </ul>
                            <div class="label-content" <#if (!$goodsLabel->goods_list)style="display: none" </#if>>
                                <span class="re_title" style="text-align: left !important; margin-left: 5px;float: none"><em>* </em>标签样式：</span>
                                <div class="label-flex">
                                    <div>
                                        <input type="radio" name="list_pattern" value="1" checked>
                                        <img src="http://${image_domain!}/image/admin/again1.png">
                                    </div>
                                    <div>
                                        <input type="radio" name="list_pattern" value="2" <#if ($goodsLabel->list_pattern==2) checked </#if>>
                                        <img src="http://${image_domain!}/image/admin/again2.png">
                                    </div>
                                    <div>
                                        <input type="radio" name="list_pattern" value="3" <#if ($goodsLabel->list_pattern==3) checked </#if>>
                                        <img src="http://${image_domain!}/image/admin/again3.png">
                                    </div>
                                    <div>
                                        <input type="radio" name="list_pattern" value="4" <#if ($goodsLabel->list_pattern==4) checked </#if>>
                                        <img src="http://${image_domain!}/image/admin/again4.png">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="re_lis clearfix" style="padding-bottom: 0">
                        <span class="re_title">添加商品：</span>
                        <span class="re_tips">选择需要添加商品标签的商品</span>
                    </li>
                    <li class="re_lis clearfix" style="padding-top: 0">
                        <span class="re_title"><em></em></span>
                        <div class="goods_sort">
                            <div class="choose_type">
                                <input type="radio" name="is_all" value="1" checked>全部商品
                                <input type="radio" name="is_all" value="0" <#if ($goodsLabel->is_all === 0) checked </#if>>指定商品
                            </div>
                            <input  type="hidden" name="gl_goods_id" value="${goodsLabel->gl_goods_id!}">
                            <input  type="hidden" name="gl_cat_id" value="${goodsLabel->gl_cat_id!}">
                            <input  type="hidden" name="sort_ids" value="${goodsLabel->gl_sort_id!}">
                            <div class="choose_goods">
                                <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                添加商品
                            </div>
                            <div class="goods_area">
                                <#if ($goodsLabel->gl_goods_id)
                                    <table class="goods_table" width="100%" goods_array="${goodsLabel->goods_array!}">
                                        <thead>
                                        <tr>
                                            <td width="50%">商品名称</td>
                                            <td width="15%">价格</td>
                                            <td width="15%">库存</td>
                                            <td width="20%">操作</td>
                                        </tr>
                                        </thead>
                                        <tbody class="label_tbody">
                                        <#list ($goodsLabel->goods_array as $item)
                                            <tr>
                                                <td width="50%">
                                                    <div class="goods_info clearfix">
                                                        <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                                        <div class="goods_name">
                                                            ${item->goods_name!}
                                                        </div>
                                                    </div>
                                                </td>
                                                <td width="15%">${item->shop_price!}</td>
                                                <td width="15%">${item->goods_number!}</td>
                                                {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                <td width="20%"><a href="javascript:void(0)" goods_id="${item->goods_id!}" class="del">删除</a></td>
                                            </tr>
                                        </#list>
                                        </tbody>
                                    </table>

                                <#else>
                                    <table class="goods_table" width="100%" style="display: none">
                                        <thead>
                                        <tr>
                                            <td width="50%">商品名称</td>
                                            <td width="15%">价格</td>
                                            <td width="15%">库存</td>
                                            <td width="20%">操作</td>
                                        </tr>
                                        </thead>
                                        <tbody class="label_tbody"></tbody>
                                    </table>
                                </#if>
                            </div>
                            
                            <div class="add_sort">
                                <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                添加商家分类
                            </div>
                            <div class="sort_area">
                                <table class="sort_table" width="528px" sort_array="${strategy->recommend_sort_id!}">
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
                                <table class="cat_table" width="528px" cat_array="${strategy->recommend_cat_id!}">
                                    <tr>
                                        <th width="100%" style="border-bottom: 1px solid #ddd;"><span>平台分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_cls">编辑</a><a href="javascript:;" class="del_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                                <!-- <#if ($goodsLabel->gl_cat_id)
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
<script type="text/javascript" src="/js/admin/goods_label.js?v=1.1.2"></script>
<script type="text/javascript" src="/js/admin/select_sort.js?v=1.0.3"></script>
<script type="text/javascript">
    var cat_id = "${goodsLabel->gl_cat_id!}";
    var goods_id = '${goodsLabel->gl_goods_id!}';
    $("body").on('click','.del',function(){
        var del_goods_id = $(this).attr('goods_id');
        console.log(del_goods_id);
        var goods = $('input[name="gl_goods_id"]').val();
        if(isNaN(goods)) {
            var goods_array = goods.split(',');
            for (var i = 0; i < goods_array.length; i++) {
                if (goods_array[i] == del_goods_id) {
                    goods_array.splice(i, 1);
                    break;
                }
            }
            $('input[name="gl_goods_id"]').val(goods_array.join(','));
        }
        else{
            $('input[name="gl_goods_id"]').val('');
        }
        $(this).parent().parent().remove();
        if ($('.goods_table tr').length == 1) {
            $('.goods_table').hide();
        }
        check_goods_area_height()
    });
    $("body").on('click','.del_cat',function(){
        $('input[name="gl_cat_id"]').val('');
        $('.cat_table').find('tr').eq(1).remove();
        $('#set-category ul input[type="checkbox"]:checked').each(function(){
            $(this).prop('checked',false);
        });
        $('.cat_table').hide();
    })
    function check_label_name() {
        var recommend_id = $('input[name="id"]').val()
        var label_name = $('.label_names').val()
        // if(recommend_name){
        //     util.ajax_json('/admin/ajax/recommend/config/check_name',function (res) {
        //         if (res.error == 0) {
        //             util.mobile_alert('该模板名称已存在')
        //         }
        //     },{recommend_id: recommend_id,recommend_name:recommend_name})
        // }

    }
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
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
    function check_goods_area_height(){
        let goods_table = $('.goods_table').outerHeight();
        if( goods_table > 300 ){
            $('.goods_area').css({
                'height': '300px',
                'overflow-y': 'scroll'
            })
            $('.choose_goods').css({
                'margin-bottom':'10px'
            })
        } else {
            $('.goods_area').css({
                'height': 'auto',
                'overflow-y': 'auto'
            })
        }
        if(goods_table < 50 ){
            $('.choose_goods').css({
                'margin-bottom':'0'
            })
        }
    }

    var label_name = $('.label_name').get(0);
    var flag = false;
    label_name.addEventListener('compositionstart', function(){
        console.log(1);
        flag = true;
 6  });
    label_name.addEventListener('compositionend',function(){
        console.log(2)
        flag = false
        limitLength(this.value,'8','','label_name')
    });
    label_name.addEventListener('input',function(){
        console.log(3)
        limitLength(this.value,'8','','label_name')
    });
    function limitLength(value, byteLength, title, attribute) { 
        if(!flag){
            var newvalue = value.replace(/[^\x00-\xff]/g, "**");               
            var length = newvalue.length;
            if (length * 1 <=byteLength * 1){ 
                    return; 
            } 
            var limitDate = newvalue.substr(0, byteLength); 
            var count = 0; 
            var limitvalue = ""; 
            for (var i = 0; i < limitDate.length; i++) { 
                    var flat = limitDate.substr(i, 1); 
                    if (flat == "*") { 
                        count++; 
                    } 
            } 
            var size = 0; 
            var istar = newvalue.substr(byteLength * 1 - 1, 1);
        
            if (count % 2 == 0) { 
                    size = count / 2 + (byteLength * 1 - count); 
                    limitvalue = value.substr(0, size); 
            } else { 
                    size = (count - 1) / 2 + (byteLength * 1 - count); 
                    limitvalue = value.substr(0, size); 
            }
            document.getElementById(attribute).value = limitvalue; 
            return; 
        }         
    }
</script>