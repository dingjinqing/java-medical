<#include ("system.header")
<link rel="stylesheet" href="/css/admin/goods_edit.css?v=1.0.3" type="text/css" />
<style type="text/css">
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
</style>
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">新增商品</span>
</div>
<div class="main-container">
    <div class="goods-box">
        <div class="goods-box-top">
            <div class="box-top-info box-top-first box-top-active">1.编辑基本信息</div>
            <div class="box-top-info box-top-detail">2.编辑商品详情</div>
        </div>
        <form name="formData" <#if ($nav==0) action="/admin/goods/add" <#else> action="/system/goods/edit?goods_id=${goods->goods_id!}&top_index=${_GET['top_index'] ?? 0!}"  </#if>  id="form1" method="post" >
            {{ csrf_field()!}
            <input type="hidden" name="spec_info"/>
            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <table width="100%" class="basic-table">
                        <tr>
                            <td ><em>*</em>商品名称：</td>
                            <td><input type="text" name="goods_name" value="${goods->goods_name!}" style="width: 400px;"/></td>
                        </tr>
                        <tr>
                            <td >商品广告词：</td>
                            <td><input type="text" name="goods_ad"  value="${goods->goods_ad!}" style="width: 400px;" /></td>
                        </tr>
                        <tr>
                            <td >商品货号：</td>
                            <td><input type="text" name="goods_sn" value="${goods->goods_sn!}" placeholder="不填则由系统自动生成货号"/></td>
                        </tr>
                        <tr>
                            <td ><em>*</em>商品库存：</td>
                            <td><input type="number" name="goods_number"  value="${goods->goods_number!}" min="0"/></td>
                        </tr>
                        <tr>
                            <td >最小限购数量：</td>
                            <td><input type="number" name="limit_buy_num" value="${goods->limit_buy_num!}" min="1" placeholder="0或不填表示不限制购买数量" /></td>
                        </tr>
                        <tr>
                            <td >商品重量：</td>
                            <td><input type="text" name="goods_weight"  value="${goods->goods_weight!}"/>Kg</td>
                        </tr>
                        <tr>
                            <td ><em>*</em>价格：</td>
                            <td><input type="text" name="shop_price" value="${goods->shop_price!}" /></td>
                        </tr>
                        <tr>
                            <td >市场价格：</td>
                            <td><input type="text" name="market_price" value="${goods->market_price!}"  /></td>
                        </tr>
                        <tr>
                            <td ><em>*</em>平台分类：</td>
                            <td>
                                <input type="hidden" name="cat_id">
                                <#if ($cat_list)
                                    <select name="level0" id="level0" onchange="select_cat(0);">
                                        <option value="0">请选择平台分类</option>
                                        <#list ($cat_list as $item)
                                            <option value="${item->cat_id!}" <#if ($goods->cat_id == $item->cat_id) selected="selected" </#if>>${item->cat_name!}</option>
                                        </#list>
                                    </select>
                                    <select name="level1" id="level1" onchange="select_cat(1);" style="display: none">
                                    </select>
                                    <select name="level2" id="level2"  style="display: none">
                                    </select>
                                </#if>
                                <#if ($cat_array)
                                    <#list ($cat_array as $k=>$row)
                                        <select name="level${k!}" id="level${k!}" onchange="select_cat(${k!});">
                                            <option value="0">请选择平台分类</option>
                                            <#list ($row['cat_list'] as $item)
                                                <option value="${item->cat_id!}" <#if ($row['cat_id'] == $item->cat_id) selected="selected" </#if>>${item->cat_name!}</option>
                                            </#list>
                                        </select>
                                    </#list>
                                    <#if (count($cat_array) ==1)
                                        <select name="level1" id="level1" onchange="select_cat(1);" style="display: none">
                                        </select>
                                        <select name="level2" id="level2"  style="display: none">
                                        </select>
                                    </#if>
                                    <#if (count($cat_array) ==2)
                                        <select name="level2" id="level2"  style="display: none">
                                        </select>
                                    </#if>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td >商家分类：</td>
                            <td>
                                <input type="hidden" name="sort_id">
                                    <select name="sort_id" id="level0" onchange="select_sort(0);">
                                        <option value="0">请选择商家分类</option>
                                        <#if ($sort_list)
                                            <#list ($sort_list as $k=>$item)
                                                <option value="${item['sort_id']!}" <#if ($goods->sort_id == $item['sort_id']) selected </#if>>${item['sort_name']!}</option>
                                            </#list>
                                        </#if>
                                    </select>
                            </td>
                        </tr>
                        <tr>
                            <td >单位：</td>
                            <td>
                                <select name="unit">
                                    <#list ($unit as $item)
                                        <option value="${item!}" <#if ($goods->unit == $item)selected="selected"</#if>>${item!}</option>
                                    </#list>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;">商品规格：</td>
                            <td>
                                <button  class="add-spec add_all" onClick='return false;' <#if ($has_prd)style="display: none;"</#if>>添加规格</button>
                                <table class="spec_modal hide">
                                    <tr>
                                        <td>规格名：</td>
                                        <td><span class="spec_span"><input type="text" name="spec_name[]"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete"  /></span></td></td>
                                    </tr>
                                    <tr>
                                        <td>规格值：</td>
                                        <td>
                                            <span class="spec_span"><input type="text" name='spec_val[]' item_id="0"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" /></span>
                                            <span class="add_spec_value">添加规格值</span>
                                        </td>
                                    </tr>
                                </table>
                                <span class="spec_val_modal hide"><span class="spec_span"><input type="text" name='spec_val[]'/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" /></span></span>
                                <#if ($has_prd)
                                    <div class="show-spec">
{{--                                        {{print_r($prd_list)!}--!}
                                        <table width="100%">
                                            <#list ($prd_list['spec_list'] as $k=>$v)
                                                <tr>
                                                    <td>规格名：</td>
                                                    <td><span class="spec_span"><input type="text" name="spec_name[]" spec_id="${v['index']!}" value="${v['spec_name']!}"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete"  /></span></td>
                                                </tr>
                                                <tr>
                                                    <td>规格值：</td>
                                                    <td>
                                                        <#list ((array)$v['spec_vals'] as $k1 => $v1)
                                                            <span  class="spec_span"><input type="text" name='spec_val[]'  spec_id="${v['index']!}" item_id="${k1!}" value="${v1!}"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" /></span>
                                                        </#list>
                                                        <span class="add_spec_value">添加规格值</span>
                                                    </td>
                                                </tr>
                                            </#list>
                                            <tr class="last_tr">
                                                <td colspan="2"><button class="add-spec add_one" onClick='return false;' <#if ($has_prd) spec_id = "{{count($prd_list['spec_list'])!}"  <#else> spec_id = "1" </#if>>添加规格选项</button></td>
                                            </tr>
                                        </table>
                                    </div>
                                <#else>
                                    <div class="show-spec" style="display:none">
                                        <table width="100%">
                                            <tr>
                                                <td>规格名：</td>
                                                <td><span class="spec_span"><input type="text" name="spec_name[]" spec_id="0"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete"  /></span></td>
                                            </tr>
                                            <tr>
                                                <td>规格值：</td>
                                                <td>
                                                    <span  class="spec_span"><input type="text" name='spec_val[]'  spec_id="0" item_id="0"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" /></span>
                                                    <span class="add_spec_value">添加规格值</span>
                                                </td>
                                            </tr>
                                            <tr class="last_tr">
                                                <td colspan="2"><button class="add-spec add_one" spec_id = "1"  onClick='return false;'>添加规格选项</button></td>
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
                                <table class="spec_price_modal" style="display: none">
                                    <tr>
                                        <td class="spec_item"></td>
                                        <td><input type="text" name="prd_price[]" /></td>
                                        <td><input type="number" name="prd_number[]" min="0"/></td>
                                        <td><input type="text" name="prd_sn[]" onchange="prdChange(this);" /></td>
                                        <td>
                                            <div class="spec-img">
                                                <input type="hidden" name="prd_img[]"/>
                                                <input type="hidden" name="prd_desc[]"/>
                                                <img class='add_spec_img' src="http://${image_domain!}/image/admin/add_img_gg.png" alt="" width="36px" />
                                                <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete prd_img_delete" style='display: none;' />
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                <div class="spec-price" >
                                    <table width="100%">
                                        <tr>
                                            <td class="spec_name_th">${prd_list['spec_name']!}</td>
                                            <td><em>*</em>价格(元)</td>
                                            <td><em>*</em>库存</td>
                                            <td>规格编码</td>
                                            <td>规格图片</td>
                                        </tr>
                                        <#if ($has_prd)
                                            <#list ($prd_list['prd_list'] as $item)
                                                <tr>
                                                    <td class="spec_item">${item['prd_name']!}</td>
                                                    <td><input type="text" name="prd_price[]" value="${item['prd_price']!}" spec_val_id="${item['spec_val_id']!}"/></td>
                                                    <td><input type="number" min="0" name="prd_number[]" spec_val_id="${item['spec_val_id']!}" value="${item['prd_number']!}"/></td>
                                                    <td>
                                                        <input type="text" name="prd_sn[]" spec_val_id="${item['spec_val_id']!}" value="${item['prd_sn']!}" onchange="prdChange(this);"/>
                                                    </td>
                                                    <td>
                                                        <div class="spec-img">
                                                            <input type="hidden" name="prd_img[]" spec_val_id="${item['spec_val_id']!}" value="${item['prd_img']!}"/>
                                                            <input type="hidden" name="prd_desc[]"spec_val_id="${item['spec_val_id']!}"  value="${item['prd_desc']!}"/>
                                                            <img class='add_spec_img' <#if ($item['prd_img'])src="${item['prd_img']!}"<#else> src="http://${image_domain!}/image/admin/add_img.png" </#if> alt="" width="36px" />
                                                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete prd_img_delete" style='display: none;' />
                                                        </div>
                                                    </td>
                                                </tr>
                                            </#list>
                                        </#if>
                                        <tr class="last_spec">
                                            <td colspan="5" class="batche-set">
                                                <span>批量设置：</span>
                                                <a href="javascript:void(0)" class="same_price">价格</a>
                                                <a href="javascript:void(0)" class="same_number">库存</a>
                                                        <a href="javascript:void(0)" class="same_img">规格图片</a>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><em>*</em>运费模板：</td>
                            <td class="goods_deliver">
                                <select name="deliver_template_id">
                                    <option value="0">店铺默认运费模板</option>
                                    <#list ($template_list->template_list as $item)
                                        <option value="${item->deliver_template_id!}"
                                                <#if  ($goods->deliver_template_id == $item->deliver_template_id)selected="selected"</#if> >
                                            <#if  ($item->flag == 0)普通--<#else>重量--</#if>${item->template_name!}
                                        </option>
                                    </#list>
                                </select>

                                <div class="deliver-template-info template-0 <#if ($goods->deliver_template_id >0) hide </#if> ">
                                    <div class="template-info-row">
                                        ${template_list->shop_template->content_desc->desc!}
                                        <div class="pull-right">
                                            <a href="/system/deliver/fee/template/list"
                                               target="_blank">查看详情</a>
                                        </div>
                                    </div>
                                </div>
                                <#list  ($template_list->template_list as $item)
                                    <div class="deliver-template-info template-${item->deliver_template_id!} <#if  ($goods->deliver_template_id != $item->deliver_template_id) hide </#if> ">
                                        <#list  ($item->content_desc as $key =>$item2)
                                            <#if  ($key==0)
                                                <div class="template-info-row">
                                                    ${item2!}
                                                    <div class="pull-right">
                                                        <a href="/system/deliver/fee/template/edit?deliver_template_id=${item->deliver_template_id!}"
                                                           target="_blank">查看详情</a>
                                                    </div>
                                                </div>
                                            <#else>
                                                <#if ($key==1)
                                                    <hr>
                                                    <div class="template-info-row" style="margin-bottom: 8px;">
                                                        指定可配送区域运费:
                                                    </div>
                                                </#if>
                                                <div class="template-info-row">
                                                    ${item2!}
                                                </div>
                                            </#if>
                                        </#list>
                                        <#if ($item->fee_0_condition_desc)
                                            <#list  ($item->fee_0_condition_desc as $key=>$item2)
                                                <#if ($key==0)
                                                    <hr>
                                                    <div class="template-info-row" style="margin-bottom:8px;">
                                                        指定条件包邮可配送区域运费:
                                                    </div>
                                                </#if>
                                                <div class="template-info-row">
                                                    ${item2!}
                                                </div>
                                            </#list>
                                        </#if>
                                    </div>
                                </#list>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;padding-top: 28px;">上下架：</td>
                            <td class="">
                                <ul class="shelves">
                                    <li class="shelves_li">
                                        <input type="radio" name="sale_type" id="now_sale"  value="0" <#if ($goods->sale_type == 0) checked </#if> />
                                        <label for="now_sale">立即上架售卖</label>
                                    </li>
                                    <li class="shelves_li">

                                        <input type="radio" name="sale_type" id="custom_sale" value="1" <#if ($goods->sale_type == 1) checked </#if> />
                                        <label for="custom_sale">自定义上架时间</label>
                                        <label class="choose-time" for="sale_time"  onclick="picker();"><#if ($goods->sale_time)${goods->sale_time!}<#else>选择上架售卖时间</#if></label>
                                        <input type="hidden" name="sale_time" id="sale_time" value="${goods->sale_time!}" >
                                    </li>
                                    <li class="shelves_li">

                                        <input type="radio" name="sale_type" id="stop_sale" value="2" <#if ($goods->sale_type == 2) checked </#if>/>
                                        <label for="stop_sale">暂不售卖，放入仓库</label>
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
                                            <li>
                                                <input name="goods_img[]" type="hidden" value="${item!}">
                                                <img src="${item!}" class='' alt="" />
                                                <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" />
                                            </li>
                                        </#list>
                                    </#if>
                                    <li>
                                        <input name="goods_img[]" type="hidden">
                                        <img src="http://${image_domain!}/image/admin/add_img.png" class="add_img" alt="" />
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" style="display: none;" />
                                    </li>
                                </ul>
                                <div class="goods-suggest">建议尺寸：800*800像素</div>
                            </td>
                        </tr>
                    </table>

                </div>
                <div class="goods-edit-detail clearfix">
                    <div class="detail-show fl">
                        <div class="detail-show-top">商品详情效果预览</div>
                        <div class="detail-show-box">

                            <div class="show-box-white" id="show"></div>
                        </div>
                    </div>
                    <div class="detail-edit fl">
                        <div class="detail-edit-box">
                            <input type="hidden" id="goods_desc" name="goods_desc" value="${goods->goods_desc!}">

                            <textarea id="editor" name="content" style="width:200px;height:466px;" onchange="getContent()"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="goods-save">
        <a  class="btn-next">下一步</a>
        <a  class="btn-prev" style="display: none;">上一步</a>
    </div>
</div>
<script type="text/javascript">
    var hasSaved = true;
</script>
<#include ("system.footer")
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/js/kindeditor/lang/{{ config("app.locale")!}.js"></script>
<script type="text/javascript" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>
<script language="JavaScript" src="/js/admin/goods_info.js?v=1.0.6"></script>

<script type="text/javascript">
    function getContent() {
        var fullHtml = '';
        var div;
        var value;
        KindEditor.ready(function(K) {
            var editor = KindEditor.instances[0];
            fullHtml = editor.fullHtml();
            div = K.query('.ke-edit-iframe');
            //console.log(div);
            editor.sync();
            //value = K('#editor').val();
            //var cmd = K.cmd(document);
        });
        console.log($('#editor').val());
        //$('#editor').val(fullHtml);
        //console.log(value);
        $('#show').html(fullHtml);
    }
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
    $(".box-top-detail").click(function () {
        if($(".detail-edit-box #goods_desc").val() != ""){
            $(".show-box-white").html($(".detail-edit-box #goods_desc").val());
        }
    });
    $(".btn-next").click(function () {
        if($(".detail-edit-box #goods_desc").val() != ""){
            $(".show-box-white").html($(".detail-edit-box #goods_desc").val());
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
    $(document).click(function () {
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
            event.returnValue = "确认要离开吗";
        }
    };
</script>
