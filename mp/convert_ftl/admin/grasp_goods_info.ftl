<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css?v=1.2.4" type="text/css" />
<link href="/css/admin/goods_decorate.css?v=1.1.6" rel="stylesheet">
<link href="/css/admin/admin_deco_style.css?v=1.0.5" type="text/css" rel="stylesheet">
<style type="text/css">
    body{
        padding-bottom: 40px;
    }
    .tb-decorate-list>tbody>tr>td{
        height:50px;
    }
    .btn-save:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-save:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
    }
    .show-box-white{
        overflow: auto;
    }
    .aui_main{
        height:420px !important;
    }
    /*.add-goods-video{*/
    /*width: 150px;*/
    /*height: 80px;*/
    /*}*/
    .goods-item-video li{
        float: left;
        margin-right: 15px;
        background: #f7f7f7;
        border: 1px solid #ccc;
        width: 80px;
        height: 81px;
        text-align: center;
        line-height: 80px
    }
    .goods-item-video li img{
        max-width: 100%;
        max-height:100%;
    }
    .btn_playa {
        color: #5a8bff !important;
        width: 80px;
        height: 20px;
        line-height: 20px;
        position: absolute;
        left: 110px;
        text-align: left;
        top: 0;
        z-index: 999;
    }
    .jiaogeshane{
        position: absolute;
        bottom: 0;
        width: 800px;
        height: 30px;
        line-height: 30px;
        left: 84px;
    }
    .same_tr_price,.same_td_price a{
        color: #5a8bff;
    }
    .same_td_price{
        display: none;
    }
    .master_diagram_left, .master_diagram_right{
        position: absolute;
        bottom: 0;
    }
    .master_diagram_left{
        left: 0;
    }
    .master_diagram_right{
        right: 0;
    }
    .card_select_box{
        display:none;
    }
</style>
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">查看商品</span>
</div>
<div class="main-container ">
    <div class="goods-box fix_every_footer">
        <div class="goods-box-top">
            <div class="box-top-info box-top-first box-top-active">1.编辑基本信息</div>
            <div class="box-top-info box-top-detail">2.编辑商品详情</div>
        </div>
        <form name="formData" <#if ($nav==0) action="/admin/goods/manage/add" <#else> action="/admin/goods/manage/edit?goods_id=${goods->goods_id!}&top_index=${_GET['top_index'] ?? 0!}"  </#if>  id="form1" method="post" >
            {{ csrf_field()!}
            <input type="hidden" name="spec_info"/>
            <input type="hidden" name="no_spec_grade">
            <input type="hidden" id="page_id" name="page_id" value="${page->page_id!}">
            <input type="hidden" id="page_type" name="page_type" value="${page->page_type!}">
            <input type="hidden" id="page_tpl_type" name="page_tpl_type" value="${page->page_tpl_type!}">
            <input type="hidden" id="page_content" name="page_content" value="${page->page_content!}">
            <input type="hidden" id="is_page_up" name="is_page_up" value="${goods->is_page_up!}">
            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <table width="100%" class="basic-table">
                        <tr>
                            <td ><em>*</em>商品名称：</td>
                            <td><input type="text" name="goods_name" value="${goods->goods_name!}" disabled style="width: 400px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td >商品广告词：</td>
                            <td><input type="text" name="goods_ad"  value="${goods->goods_ad!}" disabled style="width: 400px;" /></td>
                        </tr>
                        <tr>
                            <td >商品货号：</td>
                            <td><input type="text" name="goods_sn" value="${goods->goods_sn!}" disabled placeholder=""/></td>
                        </tr>
                        <tr>
                            <td ><em>*</em>商品库存：</td>
                            <td><input type="number" name="goods_number"  value="${goods->goods_number!}" disabled min="0"/></td>
                        </tr>
                        <tr>
                            <td >最小限购数量：</td>
                            <td><input type="number" name="limit_buy_num" value="${goods->limit_buy_num!}" disabled min="1" placeholder="" /></td>
                        </tr>
                        <tr>
                            <td >最大限购数量：</td>
                            <td><input type="number" name="limit_max_num" value="${goods->limit_max_num > 0 ? $goods->limit_max_num : ''!}" disabled min="1" placeholder="" /></td>
                        </tr>
                        <tr>
                            <td >商品重量：</td>
                            <td><input type="text" name="goods_weight" disabled value="${goods->goods_weight!}"/>Kg</td>
                        </tr>
                        <tr>
                            <td ><em>*</em>价格：</td>
                            <td><input type="text" name="shop_price" disabled value="${goods->shop_price!}" /></td>
                        </tr>
                        <tr>
                            <td >成本价格：</td>
                            <td><input type="text" name="cost_price" disabled value="${goods->cost_price!}"  /></td>
                        </tr>
                        <tr>
                            <td >市场价格：</td>
                            <td><input type="text" name="market_price" disabled value="${goods->market_price!}"  /></td>
                        </tr>
                        {{--<tr>--!}
                        {{--<td >虚假销量：</td>--!}
                        {{--<td><input type="text" name="add_sale_num"  value="${goods->add_sale_num!}" /></td>--!}
                        {{--</tr>--!}
                        <tr>
                            <td style="width: 105px;padding: 13px 0;color: #666;position: relative;vertical-align: top !important;"><em>*</em>平台分类：</td>
                            <td>
                                {{--<select name="cat_id">--!}
                                {{--<option value="0">{{ trans("system/category.top_name.select_cat")!}</option>--!}
                                {{--<#list ($cat_list as $item)--!}
                                {{--<option value="${item['cat_id']!}"--!}
                                {{--<#if ($goods->cat_id == $item['cat_id'])--!}
                                {{--selected="selected" </#if>>${item['cat_name']!}</option>--!}
                                {{--</#list>--!}
                                {{--</select>--!}
                                <input type="hidden" name="cat_id">
                                <#if ($cat_list1)
                                    <select name="level0" id="level0" disabled onchange="select_cat(0);">
                                        <option value="0">请选择平台分类</option>
                                        <#list ($cat_list1 as $item)
                                            <option value="${item->cat_id!}" <#if ($goods->cat_id == $item->cat_id) selected="selected" </#if>>${item->cat_name!}</option>
                                        </#list>
                                    </select>
                                    <select name="level1" id="level1" onchange="select_cat(1);" style="display: none">
                                    </select>
                                    <select name="level2" id="level2"  onchange="select_cat(2);"style="display: none">
                                    </select>
                                </#if>
                                <#if ($cat_array)
                                    <#list ($cat_array as $k=>$row)
                                        <select name="level${k!}" id="level${k!}" disabled onchange="select_cat(${k!});">
                                            <option value="0">请选择平台分类</option>
                                            <#list ($row['cat_list'] as $item)
                                                <option value="${item->cat_id!}" <#if ($row['cat_id'] == $item->cat_id) selected="selected" </#if>>${item->cat_name!}</option>
                                            </#list>
                                        </select>
                                    </#list>
                                    <#if (count($cat_array) ==1)
                                        <select name="level1" id="level1" disabled onchange="select_cat(1);" style="display: none">
                                        </select>
                                        <select name="level2" id="level2"  disabled onchange="select_cat(2);"style="display: none">
                                        </select>
                                    </#if>
                                    <#if (count($cat_array) ==2)
                                        <select name="level2" id="level2" disabled  onchange="select_cat(2);"style="display: none">
                                        </select>
                                    </#if>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 105px;padding: 13px 0;color: #666;position: relative;vertical-align: top !important;">商家分类：</td>
                            <td>
                                <input type="hidden" name="sort_id">
                                <select name="sort_id" id="level0" disabled onchange="select_sort();">
                                    <option value="0">请选择商家分类</option>
                                    <#if ($sort_list)
                                        <#list ((array)$sort_list as $k=>$item)
                                            <option value="${item['sort_id']!}" <#if ($goods->sort_id == $item['sort_id']) selected </#if>>${item['sort_name']!}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td >单位：</td>
                            <td>
                                <#if ($goods->unit && !in_array($goods->unit,(array)$unit))
                                    <select name="unit" disabled >
                                        <#list ((array)$unit as $item)
                                            <option value="${item!}" <#if ('自定义' == $item)selected="selected"</#if>>${item!}</option>
                                        </#list>
                                    </select>
                                <#else>
                                    <select name="unit" disabled >
                                        <#list ((array)$unit as $item)
                                            <option value="${item!}" <#if ($goods->unit == $item)selected="selected"</#if>>${item!}</option>
                                        </#list>
                                    </select>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;">商品规格：</td>
                            <td>
                                <#if ($has_prd)
                                    <div class="show-spec">
                                        <table width="100%">
                                            <#list ((array)$prd_list['spec_list'] as $k=>$v)
                                                <tr>
                                                    <td>规格名：</td>
                                                    <td><span class="spec_span"><input type="text" name="spec_name[]" disabled spec_id="${v['index']!}" value="${v['spec_name']!}"/></span></td>
                                                </tr>
                                                <tr>
                                                    <td>规格值：</td>
                                                    <td>
                                                        <#list ((array)$v['spec_vals'] as $k1 => $v1)
                                                            <span  class="spec_span"><input type="text" name='spec_val[]' disabled spec_id="${v['index']!}" item_id="${k1!}" value="${v1!}"/></span>
                                                        </#list>
                                                    </td>
                                                </tr>
                                            </#list>
                                        </table>
                                    </div>
                                <#else>
                                    <div class="show-spec" style="display:none">
                                        <table width="100%">
                                            <tr>
                                                <td>规格名：</td>
                                                <td>
                                                    <span class="spec_span">
                                                        <input type="text" name="spec_name[]" disabled spec_id="0"/>
                                                    </span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>规格值：</td>
                                                <td>
                                                    <span  class="spec_span">
                                                        <input type="text" name='spec_val[]' disabled  spec_id="0" item_id="0"/>
                                                    </span>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </#if>
                            </td>
                        </tr>
                        <tr class="spec_price" <#if (!$has_prd) style="display: none" </#if>>
                            <td style="vertical-align: top!important;">规格价格：</td>
                            <td>
                                {{--<button  class="add-spec add_spec_price" onClick='return false;'>添加规格价</button>--!}
                                <div class="spec-price" >
                                    <table width="100%">
                                        <tr>
                                            <td class="spec_name_th">${prd_list['spec_name']!}</td>
                                            <td><em>*</em>价格(元)</td>
                                            <td><em>*</em>成本价格(元)</td>
                                            <td><em>*</em>库存</td>
                                            <td>规格编码</td>
                                            <td>规格图片</td>
                                        </tr>
                                        <#if ($has_prd)
                                            <#list ((array)($prd_list['prd_list']) as $item)
                                                <tr>
                                                    <td class="spec_item">${item['prd_name']!}</td>
                                                    <td><input type="text" onblur="prd_price_blur(this)" disabled name="prd_price[]" value="${item['prd_price']!}" spec_val_id="${item['spec_val_id']!}"/></td>
                                                    <td><input type="text" name="prd_cost_price[]" disabled  value="${item['prd_cost_price']!}" spec_val_id="${item['spec_val_id']!}"/></td>
                                                    <td><input type="number" min="0" name="prd_number[]" disabled spec_val_id="${item['spec_val_id']!}" value="${item['prd_number']!}"/></td>
                                                    <td>
                                                        <input type="text" name="prd_sn[]" disabled spec_val_id="${item['spec_val_id']!}" value="${item['prd_sn']!}" onchange="prdChange(this);"/>
                                                    </td>
                                                    <td>
                                                        <div class="spec-img">
                                                            <input type="hidden" name="prd_img[]" spec_val_id="${item['spec_val_id']!}" value="${item['prd_img']!}"/>
                                                            <input type="hidden" name="prd_desc[]"spec_val_id="${item['spec_val_id']!}"  value="${item['prd_desc']!}"/>
                                                            <img class='add_spec_img' <#if ($item['prd_img'])src="${item['prd_img']!}"<#else> src="http://${image_domain!}/image/admin/add_img.png" </#if> alt="" width="36px" />
                                                        </div>
                                                    </td>
                                                </tr>
                                            </#list>
                                        </#if>
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr class="goods_prd_sn" <#if ($has_prd) style="display: none" </#if>>
                            <td style="vertical-align: top!important;">商品规格编码：</td>
                            <td><input type="text" name="goods_prd_sn" value="${noSpecProduct['prd_sn']!}" /></td>
                        </tr>
                        {{--<tr>
                            <td>无规格编码：</td>
                            <td><input type="text" name="product_sn" value=""/></td>
                        </tr>--!}
                        <tr style="<#if ($goods->goods_video) display:block <#else> display:none </#if>">
                            <td style="vertical-align: top!important;">商品视频：</td>
                            <td class="">
                                <ul class="goods-item-video">
                                    <li>
                                        <input name="goods_video" type="hidden" value="${goods->goods_video!}">
                                        <input name="goods_video_img" type="hidden" value="${goods->goods_video_img!}">
                                        <input name="goods_video_size" type="hidden" value="${goods->goods_video_size!}">
                                        <input name="goods_video_id" type="hidden" value="${goods->goods_video_id!}">
                                        <input hidden class="add_video_image" value="http://${image_domain!}/image/admin/add_video.png" />
                                        <#if ($goods->goods_video)
                                            <img  src="${goods->goods_video_img!}" class="add-goods-video" style="width: 100%;height: 100%;"/>
                                            <a href="${goods->goods_video!}" target="_blank" class="btn_playa">播放</a>
                                        </#if>
                                        <span class="jiaogeshane" style="color: #999;">上传视频仅支持MP4格式。为保障无线端各种网络环境下正常播放，只支持上传大小不超过10M，时长不超过3分钟的视频。</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><em>*</em>商品主图：</td>
                            <td class="">
                                <ul class="goods-item-img clearfix">
                                    <#if ($goods->img_list)
                                        <#list ($goods->img_list as $item)
                                            <#if  (!empty($item))
                                            <li>
                                                <input name="goods_img[]" type="hidden" value="${item!}">
                                                <img src="${item!}" class='' alt="" />
                                            </li>
                                            </#if>
                                        </#list>
                                    </#if>
                                </ul>
                                <div class="goods-suggest">建议尺寸：800*800像素</div>
                            </td>
                        </tr>
                    </table>

                </div >
                <div class="goods-edit-detail clearfix">
                    <div style="width:385px; margin:0px auto 10px; padding:0;">
                        <div class="phone_box">
                            <div class="phone_top"
                                 style="background: url(/image/admin/new_shop_beautify/page_name.png) no-repeat;">
                                <span class="phone_page_title">商品详情</span>
                            </div>
                            <div class="phone">
                                <div id="drag_area_container">
                                    <div class="item_goods">
                                         <div class="item_img">
                                             <img src="http://${image_domain!}/upload/1221495/image/20181129/crop_P1vtYO9ddPYZtuAN.jpeg" alt="">
                                         </div>
                                         <div class="item_name">
                                             <span></span>
                                         </div>
                                        <div class="item_price">
                                            ￥<span></span>
                                        </div>
                                        <div class="item_mess">
                                            <div class="kucun">库存：<span></span></div>
                                            {{--<div class="yunfei">运费：<span>0</span></div>--!}
                                        </div>
                                    </div>
                                    <div class="detail-show-box">
                                        {{--<div class="drag_img">--!}
                                            {{--<img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">--!}
                                            {{--<img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">--!}
                                        {{--</div>--!}
                                        <div class="sp_title">
                                            <span>商品详情</span>
                                        </div>
                                        <div class="show-box-white" id="show">
                                            <div class="show-begin">
                                                <p>可在右侧编辑商品详情</p>
                                                <p>未添加内容时,不显示此模块内容</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="goods_desc" name="goods_desc" value="${goods->goods_desc!}">                    
                </div>
            </div>
        </form>
    </div>
    <div class="goods-save fix_footer">
        <a  class="btn-next">下一步</a>
        <a  class="btn-prev" style="display: none;">上一步</a>
    </div>
</div>
<script type="text/javascript">
    var hasSaved = true;
    var has_prd = "${has_prd!}";
    var is_has_prd = 0;
    if(has_prd !='' ){
        is_has_prd = $('input[name="prd_price[]"]').length-1;
    }

    


    function cut_str(str, len){
        var char_length = 0;
        for (var i = 0; i < str.length; i++){
            var son_str = str.charAt(i);
            encodeURI(son_str).length > 2 ? char_length += 1 : char_length += 0.5;
            if (char_length >= len){
                var sub_len = char_length == len ? i+1 : i;
                return str.substr(0, sub_len);
                break;
            }
        }
    }

</script>
<#include "/admin/footer.ftl">
<script>
    // $(document).ready(function () {
    //     util.init_zero_clipboard($("#copy_to_clip"));
    //
    // });
    var url = 'http://${image_domain!}/';
    var hasSaved = true;
    var is_card = "${version_mod["m_member_card"]!}";
    var is_coupon = "${version_mod["m_voucher"]!}";
    var is_bargain = "${version_mod["m_bargain"]!}";
    var is_video = "${version_mod["m_video"]!}";
    var is_integral = "${version_mod["m_integral_goods"]!}";
    var is_seckill = "${version_mod["m_seckill_goods"]!}";
    var is_group_draw = "${version_mod["m_group_draw"]!}";
    var is_pin_integration = "${version_mod["m_pin_integration"]!}";
</script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&key=GO6BZ-EUL6P-ZLPDD-VYOYE-CCZBH-UFBOU"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/video_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.1.3"></script>
<script language="JavaScript" src="/js/admin/jVideoManager.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/js/kindeditor/lang/{{ config("app.locale")!}.js"></script>
<script type="text/javascript" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>
<script type="text/javascript" src="/js/ZeroClipboard/ZeroClipboard.js"></script>

<script type="text/javascript">

    //版本控制
    // var num = "${version['num']!}";
    // var use = '${version['use']!}';
    // var pageId = '${page->page_id!}';
    // var goods_id  = util.getUrlParam('goods_id');
    // if(goods_id > 0){//编辑
    //     if(num>=0 && num-use < 0){
    //         window.location.href = '/admin/authority/not?mod_name=商品数量';
    //     }
    // }else{//新建
    //     if(num>=0 && num-use <= 0){
    //         window.location.href = '/admin/authority/not?mod_name=商品数量';
    //     }
    // }

    // if(pageId){
    //     $('.show_area').hide();
    // }
    
    function picker(){
        return WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
    }
    //进入页面查询是否交换
    $(function () {
        var drag_area = $('#drag_area');
        var box = $('.detail-show-box');
        var is_page_up = $('#is_page_up').val();
        if(is_page_up == 0){
            box.after(drag_area);
            $('#position2').prop('checked',true);
        }else{
            $('#position1').prop('checked',true);
        }
        if($('input[name="goods_desc"]').val() != ''){
            console.log($('input[name="goods_desc"]').val());
            $(document).find('.show-box-white').html($('input[name="goods_desc"]').val());
        }
    })


    function getContent() {
        var fullHtml = '';
        var div;
        var value;
        var show_begin = `   <div class="show-begin">
                                       <p>可在右侧编辑商品详情</p>
                                       <p>未添加内容时,不显示此模块内容</p>
                               </div>`;
        KindEditor.ready(function(K) {
            var editor = KindEditor.instances[0];
            fullHtml = editor.fullHtml();
            div = K.query('.ke-edit-iframe');
            //console.log(div);
            editor.sync();
            //value = K('#editor').val();
            //var cmd = K.cmd(document);
        });
        //console.log($('#editor').val());
        //$('#editor').val(fullHtml);
        //console.log(value);
        if(fullHtml == "<!doctype html>"){
            $('#show').html(show_begin);
        }else{
            console.log(fullHtml);
            $('#show').html(fullHtml);
        }
    }

    $(document).on('blur','.grade_price_value',function(){
        if($(this).val() == ''){
            util.mobile_alert('请输入等级会员卡价格');
            $(this).focus();
            return false;
        }else if(parseFloat($(this).val()) > parseFloat($(this).parent().parent().find(".grade_goods_price").text())) {
            util.mobile_alert('等级会员卡价格不能大于原商品价格');
            $(this).focus();
            return false;
        }
    });
    $("input[name='goods_number']").blur(function() {
        var goods_number = $("input[name='goods_number']").val();
        if(goods_number < 0) {
            util.mobile_alert("库存不能少于0");
        }
    });
    $("input[name='limit_buy_num']").blur(function() {
        var limit_buy_num = parseInt($("input[name='limit_buy_num']").val());
        if($("input[name='limit_buy_num']").val() != '' && (limit_buy_num < 0 || limit_buy_num != $("input[name='limit_buy_num']").val())) {
            util.mobile_alert("最小限购数量不合法");
        }
    });
    $("input[name='unit_temp']").blur(function() {
        if($("input[name='unit_temp']").val() == '' ) {
            util.mobile_alert("自定义单位不能为空");
        }
    });

    util.inputChange();
    util.selectChange();
    var sale_type_val = $('input[name="sale_type"]:checked').val();
    $('input[name="sale_type"]').click(function () {
        if($(this).val() != sale_type_val){
            //alert('修改过radio');
            hasSaved = false;
        }
    });
    var body_html = $('#goods_desc').val();
    var label_html = $('.choose-time').html();
    $('.goods-box-edit').click(function () {
        getContent();
        if(body_html != $('#editor').val()){
            //alert('修改过详情');
            hasSaved = false;
        }
        if(label_html != $('.choose-time').html()){
            //alert('修改过label');
            hasSaved = false;
        }
    });

    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            event.returnValue = "确认要离开吗？";
        }
    };
    $(function(){
        $(document).on('mouseenter','.goods-item-img li',function(){
            if($(this).index() != ($('.goods-item-img li').length - 1)){
                $(this).find('img.master_diagram_left').show()
                $(this).find('img.master_diagram_right').show()
            }      
        })
        $(document).on('mouseleave','.goods-item-img li',function(){
            $(this).find('img.master_diagram_left').hide()
            $(this).find('img.master_diagram_right').hide()
        })
        $(document).on('click','.master_diagram_left',function () {
            var pre_src = $(this).parents('li').prev().find("img").eq(0).attr('src');
            var cur_src = $(this).prev().prev().attr('src');
            if(pre_src) {
                $(this).parents('li').prev().find("img").eq(0).attr('src', cur_src);
                $(this).parents('li').prev().find("input").eq(0).val(cur_src);
                $(this).prev().prev().attr('src', pre_src);
                $(this).prev().prev().prev().val(pre_src);
            }
        })
        $(document).on('click','.master_diagram_right',function () {
            var add_img_src = $('.goods-item-img li:last').find('.add_img').attr('src');
            var next_src = $(this).parents('li').next().find("img").eq(0).attr('src');
            var cur_src = $(this).prev().prev().prev().attr('src');
            if(next_src && next_src != add_img_src) {
                $(this).parents('li').next().find('img').eq(0).attr('src', cur_src);
                $(this).parents('li').next().find("input").eq(0).val(cur_src);
                $(this).prev().prev().prev().attr('src', next_src);
                $(this).prev().prev().prev().prev().val(next_src);
            }
        })
    })

    $('#choose_page').on('click','.tb-decorate-list tbody tr', function(){
        var flag_back = $(this).attr('data-back');
        $('.tb-decorate-list tbody tr.goods_tr_choose').each( function () {
            $(this).removeClass('goods_tr_choose');
            $(this).attr('data-back','true');
        })
        if (flag_back == 'true') {
            $(this).addClass('goods_tr_choose');
            $(this).attr('data-back', 'false');
        }
    });
    $('.box-top-first,.btn-prev').click(function(){
        $('.box-top-detail').removeClass('box-top-detail2');
        $('.box-top-first').removeClass('box-top-first2');
        $('.goods-edit-detail').hide();
        $('.goods-edit-basic').show();
        $('.btn-next').show();
        $('.btn-prev').hide();
    });
    $(".btn-next,.box-top-detail").click(function() {
        var flag =0;
        if($('#level2').val()>0){
            $("input[name='cat_id']").val($('#level2').val());
        }else if($('#level1').val()>0) {
            $("input[name='cat_id']").val($('#level1').val());
        }else if($('#level0').val()>0) {
            $("input[name='cat_id']").val($('#level0').val());
        }

        $(".item_name>span").text($("input[name='goods_name']").val());


        $(".kucun>span").text($("input[name='goods_number']").val());


        $(".item_price>span").text($("input[name='shop_price']").val());


        $(".item_img>img").attr('src',$('input[name="goods_img[]"]:eq(0)').val());
        $('.box-top-first').addClass('box-top-first2');
        $('.box-top-detail').addClass('box-top-detail2');
        $('.goods-edit-detail').show();
        $('.goods-edit-basic').hide();
        $('.btn-prev').show();
        $('.btn-next').hide();
    });
    function select_cat(level,parent_id) {
        get_cat_lc(level);
        if(level < 2){
            var url = "/admin/ajax/category/level/list";
            var param = {};
            param.level = parseInt(level+1);
            if (parent_id == null || parent_id == '') {
                param.parent_id = $('#level' + level).val();
            }else{
                param.parent_id = parent_id;
            }
            if(level==0){
                $('#level1').html( '');
                $('#level1').hide();
                $('#level2').html( '');
                $('#level2').hide();
            }
            var op = '<option value="0">选择分类</option>';

            util.ajax_json(url, function (d) {
                if (d && d.error == 0) {
                    var next_level = parseInt(level+1).toString();
                    var html = '';
                    var cat_id = 0;
                    for (var i in d.content){
                        var t = d.content[i];
                        cat_id = d.content[0].cat_id;
                        html += '<option value="'+t.cat_id+'"';
                        if(i==0) {
                            // html += ' selected="selected" ';
                        }
                        html += '>'+ t.cat_name +'</option>';
                    }
                    setTimeout(function () {
                        if(html) {
                            $('#level' + next_level).html(op + html);
                            $('#level' + next_level).show();
                        }else{
                            $('#level' + next_level).html( html);
                            $('#level' + next_level).hide();
                        }
                        if(param.level<=2 && cat_id>0) {
                            //select_cat(param.level,cat_id);
                        }
                    }, 500);
                    return true;
                } else if (d && d.error < 0) {
                    util.mobile_alert('获取数据失败');
                    return false;
                }
            }, param);
        }
    }
    function get_cat_lc(level) {
        var url = "/admin/ajax/category/label";
        var param = [];
        var level0 = $('select[name="level0"]').val();
        var level1 = $('select[name="level1"]').val();
        var level2 = $('select[name="level2"]').val();
        param[0] = level0;
        if(level>0){
            param.push(level1);
        }
        if(level>1){
            param.push(level2);
        }
        console.log(param);
        var kk = 1;
        util.ajax_json(url, function (d) {
            if (d && d.error == 0) {
                var inner_html = '';
                var inner_html1 = '';
                var labels = d.content.labels;
                var cards = d.content.cards;
                for (var i in labels){
                    var span =' <span class="label_span">';
                    inner_html += span + '<span>'+ labels[i].name + '</span>' + '</span>';
                }
                for (var j in cards){
                    var span1 =' <span class="card_span">';
                    inner_html1 += span + '<span>'+ cards[j].card_name + '</span>' + '</span>';
                }
                if(inner_html !=''){
                    $('.label-info-cat').html('<span class="label-cat">所属种类标签：</span>'+inner_html);
                    $('.label-info1').show();
                }else{
                    $('.label-info1').hide();
                }
                if(inner_html1 !=''){
                    $('.card-info-cat').html('<span class="card-cat">所属种类会员卡：</span>'+inner_html1);
                    $('.card-info1').show();
                }else{
                    $('.card-info1').hide();
                }
                return true;
            } else if (d && d.error < 0) {
                util.mobile_alert('获取数据失败');
                return false;
            }
        }, {cat:param});
    }
    function select_sort() {
        var url = "/admin/ajax/sort/label";
        var sort_id = $('select[name="sort_id"]').val();
        util.ajax_json(url, function (d) {
            if (d && d.error == 0) {
                var inner_html = '';
                var inner_html1 = '';
                var labels = d.content.labels;
                var cards = d.content.cards;
                for (var i in labels){
                    var span =' <span class="label_span">';
                    inner_html += span + '<span>'+ labels[i].name + '</span>' + '</span>';
                }
                for (var j in cards){
                    var span1 =' <span class="card_span">';
                    inner_html1 += span + '<span>'+ cards[j].card_name + '</span>' + '</span>';
                }
                if(inner_html !=''){
                    $('.label-info-sort').html('<span class="label-sort">所属种类标签：</span>'+inner_html);
                    $('.label-info2').show();
                }else{
                    $('.label-info2').hide();
                }
                if(inner_html1 !=''){
                    $('.card-info-sort').html('<span class="card-sort">所属种类会员卡：</span>'+inner_html1);
                    $('.card-info2').show();
                }else{
                    $('.card-info2').hide();
                }
                return true;
            } else if (d && d.error < 0) {
                util.mobile_alert('获取数据失败');
                return false;
            }
        }, {sort_id: sort_id});
    }
    $(document).ready(function () {
        initKindEditor("#editor", function () {
            window.keditor.html($("#goods_desc").val());
        });
    });
</script>
