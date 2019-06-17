<#include "/admin/header.ftl">
<link href="/css/admin/deliver_fee.css?v=0.1.11" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/goods_label.css?v=1.1.2" type="text/css"/>
<link rel="stylesheet" href="/css/admin/full_shipping.css?v=1.0.3" type="text/css"/>
<style type="text/css">
    body {
        padding-bottom: 40px;
    }

    .tb_paging td a:hover {
        background: #fff !important;
        color: #5a8bff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }

    .tb_paging td a:focus {
        background: #5a8bff !important;
        color: #fff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }

    input[name='page']:focus, input[type="text"]:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
    }

    .tb_paging tr td, .tb_paging tr td a {
        color: #333;
        font-size: 14px;
    }

    #product-info table tr td {
        border: 1px solid #eee;
    }

    input[readonly] {
        background: #EBEBE4;
    }
    .full_num{
        margin-top: 10px;
        margin-left: 130px;
    }
    .full_num input[type='text'] {
        width: 100px;
        margin-left: 10px;
    }

</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;"><a href="/admin/market/ship/list">满包邮活动</a></span>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/ship/list?nav=0&top_index=4">全部满包邮活动</a>
                </li>
                <li>
                    <a href="/admin/market/ship/list?nav=1&top_index=4">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/ship/list?nav=2&top_index=4">未开始</a>
                </li>
                <li>
                    <a href="/admin/market/ship/list?nav=3&top_index=4">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/ship/list?nav=4&top_index=4">已停用</a>
                </li>
                <li class="active">
                    <#if ($id)
                        <a href="/admin/market/ship/create?top_index=4&id=${id!}">编辑满包邮活动</a>
                    <#else>
                        <a href="/admin/market/ship/create?top_index=4">添加满包邮活动</a>
                    </#if>
                </li>
            </ul>
        </div>
        <div class="return-full_ship-box" style="padding-top: 20px;">
            <form name="formData" id="form1" method="post" action="">
                {{ csrf_field()!}
                <input type="hidden" name="id" value="${id!}">
                <input type="hidden" name="rule" value="">
                <table class="tb-full_ship">
                    <tbody>
                    <tr style="height: 50px;">
                        <td style="width: 100px ;">
                            <span class="tb-full-left"><strong>*</strong>活动名称：</span>
                        </td>
                        <td>
                            <input type="text" class="name" placeholder="请填写活动名称" name="name"
                                   value="${basicData->name!}" maxlength="20">
                        </td>

                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>活动时间：</span>
                        </td>
                        <td>
                            <label for="check_1">
                                <input type="radio" name="expire_type" value="0" <#if ($basicData->expire_type == 0) checked </#if> id="check_1">固定时间段
                            </label>
                            <span style="padding-left: 10px">
{{--                                        <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s"))--!}
                                    {{--<input type="text" class="tb-text date-text" value="${basicData->start_time!}"--!}
                                           {{--name="start_time"--!}
                                           {{--id="startTime" readonly> 至--!}
                                    {{--<input type="text" class="tb-text date-text" value="${basicData->end_time!}"--!}
                                           {{--name="end_time"--!}
                                           {{--id="endTime" readonly>--!}
                                {{--<#else>--!}
                                    <input type="text" class="tb-text date-text" value="${basicData->start_time!}"
                                           name="start_time"
                                           id="startTime" onclick="picker();"
                                           onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
                                           autocomplete="off"> 至
                                    <input type="text" class="tb-text date-text" value="${basicData->end_time!}"
                                           name="end_time"
                                           id="endTime" onclick="picker();"
                                           onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
                                           autocomplete="off">
                                {{--</#if>--!}
                                </span>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="padding: 0px 10px 8px 10px">
                            <label for="check_2">
                                <input type="radio" name="expire_type" <#if ($basicData->expire_type != 0) checked </#if> value="1" id="check_2"> 永久有效
                            </label>
                        </td>
                    </tr>
                     <tr>
                         <td style="width: 100px">
                             <span class="tb-full-left" style="padding-left: 27px;">优先级：</span>
                         </td>
                         <td>
                             <input type="text" class="name" placeholder="请填写活动优先级" name="level"
                                    value="${basicData->level ?? 0!}" maxlength="3">
                             <span style="margin-left: 10px;color: #ccc;">用于区分不同满包邮活动得优先级，请填写正整数，数值越大优先级越大</span>
                         </td>
                     </tr>
                    </tbody>
                </table>

            <div class="re_lis clearfix rule_list" style="padding-top: 0">
                <span class="re_title" style="padding-right: 10px"><em style="padding-right: 10px">*</em>添加商品：</span>
                <div class="goods_sort">
                    <div class="choose_type">
                        <input type="radio" name="type" value="0" checked>全部商品
                        <input type="radio" name="type" value="1" <#if ($basicData->type == 1) checked </#if>>指定商品
                    </div>
                    <input type="hidden" name="recommend_goods_id" value="${basicData->recommend_goods_id!}">
                    <input type="hidden" name="recommend_cat_id" value="${basicData->recommend_cat_id!}">
                    <input type="hidden" name="recommend_sort_id" value="${basicData->recommend_sort_id!}">
                    <input type="hidden" name="sort_ids" value="${basicData->recommend_sort_id!}">
                    <div class="choose_goods">
                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                        添加商品
                    </div>
                    <div class="goods_area">
                        <#if ($basicData->recommend_goods_id)
                            <table class="goods_table" width="100%" goods_array="${basicData->goods_array!}">
                                <thead>
                                <tr>
                                    <td width="50%">商品名称</td>
                                    <td width="15%">价格</td>
                                    <td width="15%">库存</td>
                                    <td width="20%">操作</td>
                                </tr>
                                </thead>
                                <tbody class="label_tbody">
                                <#list ($basicData->goods_array as $item)
                                    <tr>
                                        <td width="50%">
                                            <div class="goods_info clearfix">
                                                <div class="goods_img"><img src="${item->goods_img!}" alt=""/></div>
                                                <div class="goods_name">
                                                    ${item->goods_name!}
                                                </div>
                                            </div>
                                        </td>
                                        <td width="15%">${item->shop_price!}</td>
                                        <td width="15%">${item->goods_number!}</td>
                                        {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                        <td width="20%"><a href="javascript:void(0)" goods_id="${item->goods_id!}"
                                                           class="del">删除</a></td>
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
                        <table class="sort_table" width="528px" sort_array="${basicData->recommend_sort_id!}">
                            <tr>
                                <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span>
                                    <div class="fr" style="margin-right: 6px;"><a href="javascript:;"
                                                                                  style="margin-right:10px"
                                                                                  class="edit_sort_cls">编辑</a><a
                                                href="javascript:;" class="del_sort_cat">删除</a></div>
                                </th>
                            </tr>
                        </table>
                    </div>
                    <div class="add_cate">
                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                        添加平台分类
                    </div>

                    <div class="sort_area">
                        <table class="cat_table" width="528px" cat_array="${basicData->recommend_cat_id!}">
                            <tr>
                                <th width="100%" style="border-bottom: 1px solid #ddd;"><span>平台分类</span>
                                    <div class="fr" style="margin-right: 6px;"><a href="javascript:;"
                                                                                  style="margin-right:10px"
                                                                                  class="edit_cls">编辑</a><a
                                                href="javascript:;" class="del_cat">删除</a></div>
                                </th>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            </form>
            <div class="re_lis clearfix">
                <span class="re_title" style="padding-right: 10px"><em style="padding-right: 10px">*</em>包邮规则：</span>
            </div>
            <div class="re_lis clearfix" id="full_content">
                {{--<#list ($rule_list as $k=>$rule)--!}
                {{--<div class="full_rule">--!}
                    {{--<div class="rule_name">--!}
                        {{--规则<span class="rule_num"></span>--!}
                        {{--<img src="http://${image_domain!}/image/admin/decorate_delete.png" alt="" class="del-deliver">--!}
                    {{--</div>--!}
                    {{--<div class="rule_content">--!}
                        {{--<div class="rule_top">--!}
                            {{--<span class="re_title" style="padding-right: 10px" rule_id="${rule->id!}"><em style="padding-right: 10px">*</em>包邮条件：</span>--!}
                            {{--<span class="full_re_radio">--!}
                                   {{--<input type="radio" value="0" class="con_type" name="con_type" <#if ($rule->con_type == 0) checked </#if>>满金额--!}
                                   {{--<input type="radio" value="1" class="con_type" name="con_type" <#if ($rule->con_type == 1) checked </#if>>满件数--!}
                                   {{--<input type="radio" value="2" class="con_type" name="con_type" <#if ($rule->con_type == 2) checked </#if>>满金额或满件数--!}
                            {{--</span>--!}
                            {{--<#if ($rule->con_type == 1)--!}
                                {{--<div class="full_price num" >--!}
                                    {{--满 <input type="text"  value="${rule->num!}">件--!}
                                {{--</div>--!}
                            {{--<#elseif>($rule->con_type == 2)--!}
                                {{--<div class="full_price money_num" >--!}
                                    {{--满 <input type="text" value="${rule->money!}">元，满 <input type="text"  value="${rule->num!}">件--!}
                                {{--</div>--!}
                            {{--<#else>--!}
                                {{--<div class="full_price money">--!}
                                    {{--满 <input type="text" value="${rule->money!}">元--!}
                                {{--</div>--!}
                            {{--</#if>--!}



                        {{--</div>--!}
                        {{--<div class="rule_bottom">--!}
                            {{--<span class="re_title" style="padding-right: 10px"><em style="padding-right: 10px">*</em>包邮区域：</span>--!}
                            {{--<span class="add-deliver-area" style="padding-left: 0px">--!}
                                  {{--选择包邮区域--!}
                            {{--</span>--!}
                            {{--<div class="ship_area_content">--!}
                                {{--<#if ($rule->area)--!}
                                    {{--<#list ($rule->area['area_list'] as $k=>$area)--!}
                                        {{--<span area-data-code="${area!}" area-data-text="${rule->area['area_text'][$k]!}">${rule->area['area_text'][$k]!}</span>--!}
                                    {{--</#list>--!}
                                {{--</#if>--!}
                            {{--</div>--!}
                        {{--</div>--!}
                    {{--</div>--!}
                {{--</div>--!}
                {{--</#list>--!}
            </div>
            <div class="add_full_rule">
                +添加规则
            </div>
        </div>
    </div>
    <div class="full_ship_footer fix_footer">
        <button type="submit" class="save" onclick="if(save() === false) return false;">保存</button>
    </div>
</div>
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
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open"
                                 data-flag="true"/>
                            <span>
                            <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}"
                                   <#if ($item->checked) checked </#if>/>
                            <label for="cat_${item->cat_id!}">${item->cat_name!}(${item->goods_num!})</label>
                        </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                                <span>
                                    <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}"
                                           id="cat_${sub_item->cat_id!}" class="second_box"
                                           <#if ($sub_item->checked) checked </#if> />
                                    <label for="cat_${sub_item->cat_id!}">${sub_item->cat_name!}
                                        (${sub_item->goods_num!})</label>
                                </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                                <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}"
                                                       id="cat_${th_item->cat_id!}"
                                                       <#if ($th_item->checked) checked </#if>/>
                                                <label for="cat_${th_item->cat_id!}">${th_item->cat_name!}
                                                    (${th_item->goods_num!})</label>
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
<table class="goods_modal_clone hide">
    <tr>
        <td></td>

        <td></td>
        <td>上架</td>
        <td><a href="javascript:void(0)" class="del">删除</a></td>
    </tr>
</table>
<div id="template" class="hide">
    <div id="area_template" style="width: 600px;height: 400px;overflow-y: auto">
        <table class="table table-striped">
            <tr>
                <td colspan="2">
                    <label>
                        <input type="checkbox" name="sel_all" class="sel-all">全选
                    </label>
                </td>
            </tr>
            <#list  ($deliver_area as $item)
                <tr class="province-row">
                    <td width="100px">
                        <label><input type="checkbox" name="province[]" province_id="${item->province_id!}"
                                      province_name="${item->province_name!}"
                                      value="${item->province_id!}">${item->province_name!}

                        </label>
                    </td>
                    <td>
                        <div class="city-list">
                            <#list  ($item->city_list as $item2)
                                <div class="city-item">
                                    <label><input class="city-cbx" type="checkbox" name="city[]"
                                                  its_province_id="${item->province_id!}"
                                                  city_id="${item2->city_id!}" value="${item2->city_id!}"
                                                  city_name="${item2->city_name!}">${item2->city_name!}
                                        <span class="sel-num"></span>
                                    </label>
                                    <img class="expand" src="http://${image_domain!}/image/admin/expand.png">

                                    <div class="district-list" city_id="${item2->city_id!}">
                                        <#list  ($item2->district_list as $item3)
                                            <label>
                                                <input type="checkbox" name="district[]" class="district-cbx"
                                                       its_province_id="${item->province_id!}"
                                                       its_city_id="${item2->city_id!}"
                                                       district_id="${item3->district_id!}"
                                                       value="${item3->district_id!}"
                                                       district_name="${item3->district_name!}">
                                                ${item3->district_name!}
                                            </label>
                                        </#list>
                                    </div>
                                </div>
                            </#list>
                            <div class="clear"></div>
                        </div>
                    </td>
                </tr>
            </#list>
        </table>
    </div>
    <div class="full_rule">
        <div class="rule_name">
            规则<span class="rule_num"></span>
            <img src="http://${image_domain!}/image/admin/decorate_delete.png" alt="" class="del-deliver">
        </div>
        <div class="rule_content">
            <div class="rule_top">
                <span class="re_title" style="padding-right: 10px" rule_id=""><em style="padding-right: 10px">*</em>包邮条件：</span>
                <span class="full_re_radio">
                                   <input type="radio" value="0" class="con_type" name="con_type" checked>满金额
                                   <input type="radio" value="1" class="con_type" name="con_type" >满件数
                                   <input type="radio" value="2" class="con_type" name="con_type" >满金额或满件数
                            </span>
                <div class="full_price money">
                    满 <input type="text" value="">元
                </div>
                <div class="full_price num" hidden>
                    满 <input type="text"  value="">件
                </div>
                <div class="full_price money_num" hidden>
                    满 <input type="text" value="">元，满 <input type="text"  value="">件
                </div>
            </div>
            <div class="rule_bottom">
                <span class="re_title" style="padding-right: 10px"><em style="padding-right: 10px">*</em>包邮区域：</span>
                <span class="add-deliver-area" style="padding-left: 0px">
                                  选择包邮区域
                            </span>
                <div class="ship_area_content">
                    <span area-data-code="" area-data-text=""></span>
                </div>
            </div>
        </div>
    </div>
</div>


</div>

<script>
    hasSaved = true;
    var edit = '${edit!}';
    var cat_id = "${basicData->recommend_cat_id!}";
    var goods_id = '${basicData->recommend_goods_id!}';
    window.deliver_area = @json($deliver_area);
    window.condition_deliver_area = @json($deliver_area);
    var hasSaved = true;
    if(edit!=1){
        var rule = $('#template').find('.full_rule').clone();
        var par = $('#full_content');
        rule.appendTo(par);
        changeRuleNum();
    }else{
        var php_rule_list = @json($rule_list);
        for(var i in php_rule_list){
            var rule = $('#template').find('.full_rule').clone(true);
            var par = $('#full_content');
            rule.appendTo(par);
            $('#full_content').find('.full_re_radio').eq(i).find('input').eq(php_rule_list[i].con_type).prop('checked',true);
            if (php_rule_list[i].con_type == 1) {
                $('#full_content').find('.rule_content').eq(i).find('.num').show();
                $('#full_content').find('.rule_content').eq(i).find('.money_num').hide();
                $('#full_content').find('.rule_content').eq(i).find('.money').hide();
                $('#full_content').find('.rule_content').eq(i).find('.num').find('input').val(php_rule_list[i].num);
            } else if(php_rule_list[i].con_type == 2){
                $('#full_content').find('.rule_content').eq(i).find('.num').hide();
                $('#full_content').find('.rule_content').eq(i).find('.money_num').show();
                $('#full_content').find('.rule_content').eq(i).find('.money').hide();
                $('#full_content').find('.rule_content').eq(i).find('.money_num').find('input').eq(0).val(php_rule_list[i].money);
                $('#full_content').find('.rule_content').eq(i).find('.money_num').find('input').eq(1).val(php_rule_list[i].num);
            }else{
                $('#full_content').find('.rule_content').eq(i).find('.num').hide();
                $('#full_content').find('.rule_content').eq(i).find('.money_num').hide();
                $('#full_content').find('.rule_content').eq(i).find('.money').show();
                $('#full_content').find('.rule_content').eq(i).find('.money').find('input').val(php_rule_list[i].money);
            }
            if(php_rule_list[i].area){
                for(var j in php_rule_list[i].area.area_list){
                    $('#full_content').find('.rule_content').eq(i).find('.ship_area_content').find('span').attr('area-data-code',php_rule_list[i].area.area_list[j]);
                    $('#full_content').find('.rule_content').eq(i).find('.ship_area_content').find('span').attr('area-data-text',php_rule_list[i].area.area_text[j]);
                    $('#full_content').find('.rule_content').eq(i).find('.ship_area_content').find('span').text(php_rule_list[i].area.area_text[j]);
                }
            }
            changeRuleNum();
        }
    }
    function save() {
        var _this = $(this);
        if ($('.xuanchuan').val() == '') {
            return false;
        }
        if ($('input[name="name"]').val() == '') {
            util.mobile_alert('请填写活动名称');
            return false;
        }
        if($('input[name="expire_type"]:checked').val()!=1){
            if ($('input[name="start_time"]').val() == '' || $('input[name="end_time"]').val() == '') {
                util.mobile_alert('请填写有效期');
                return false;
            }
            if ($('input[name="start_time"]').val() > $('input[name="end_time"]').val()) {
                util.mobile_alert('开始时间不能大于结束时间');
                return false;
            }
        }
        if($('input[name="sort_ids"]').val()){
            $('input[name="recommend_sort_id"]').val($('input[name="sort_ids"]').val())
        }
        if($('input[name="level"]').val()>100){
            util.mobile_alert('活动优先级不可大于100');
            return false;
        }
        if($('input[name="level"]').val()<0){
            util.mobile_alert('活动优先级不可小于0');
            return false;
        }
        //规则填充
        var rule_list = $(".re_lis ").find(".full_rule");
        if(rule_list.length<1){
            util.mobile_alert('请添加规则');
            return false;
        }
        for(var i=0;i<rule_list.length;i++){
            if($(".re_lis ").find(".full_rule").eq(i).find('.con_type:checked').val()==1){
                if(!$(".re_lis ").find(".full_rule").eq(i).find(".num").find("input").val()){
                    util.mobile_alert('请填写包邮件数');
                    return false;
                }
            }
            else if($(".re_lis ").find(".full_rule").eq(i).find('.con_type:checked').val()==2) {
                if (!$(".re_lis ").find(".full_rule").eq(i).find(".money_num").find("input").eq(0).val()) {
                    util.mobile_alert('请填写包邮金额');
                    return false;
                }
                if (!$(".re_lis ").find(".full_rule").eq(i).find(".money_num").find("input").eq(1).val()) {
                    util.mobile_alert('请填写包邮件数');
                    return false;
                }
            }else{
                if(!$(".re_lis ").find(".full_rule").eq(i).find(".money").find("input").val()){
                    util.mobile_alert('请填写包邮金额');
                    return false;
                }
            }

            if($(".re_lis ").find(".full_rule").eq(i).find(".ship_area_content").find("span").text()==''){
                $(".re_lis ").find(".full_rule").eq(i).find(".rule_bottom").prev().find("input").focus();
                util.mobile_alert('请选择包邮区域');
                return false;
            }
        }
        var rule = []
        for(var i=0;i<rule_list.length;i++){
            if($(".re_lis ").find(".full_rule").eq(i).find('.con_type:checked').val()==1){
                var num = $(".re_lis ").find(".full_rule").eq(i).find('.num').find("input").val();
                var money = 0;
            }else if($(".re_lis ").find(".full_rule").eq(i).find('.con_type:checked').val()==2){
                var money = $(".re_lis ").find(".full_rule").eq(i).find(".money_num").find("input").eq(0).val();
                var num = $(".re_lis ").find(".full_rule").eq(i).find(".money_num").find("input").eq(1).val();
            }else{
                var num = 0;
                var money = $(".re_lis ").find(".full_rule").eq(i).find(".money").find("input").val();
            }
            var area_list = [];
            var area_text = [];
            $(".re_lis ").find(".full_rule").eq(i).find(".ship_area_content").find("span").each(function (k,v) {
                area_list.push($(v).attr("area-data-code"));
                area_text.push($(v).attr("area-data-text"));
            });
            var area_detail = {
                area_list:area_list,
                area_text:area_text
            }
            var rule_con={
                con_type:$(".re_lis ").find(".full_rule").eq(i).find('.con_type:checked').val(),
                num:num,
                id:$(".re_lis ").find(".full_rule").eq(i).find('.re_title').attr('rule_id'),
                money:money,
                area:area_detail
            }
            rule.push(rule_con);
        }
        $("input[name='rule']").val(JSON.stringify(rule));
        util.ajax_json('/admin/market/ship/create', function (response) {
            if (response.error == 0) {
                hasSaved = true;
                layer.ready(function () {
                    layer.msg('保存成功', {time: 1000}, function () {
                        window.location = '/admin/market/ship/list?nav=1';
                    });
                });
            } else {
                util.mobile_alert(response.message);
            }
        }, $('#form1').serialize());
        return false;
    };

    function picker() {
        hasSaved = false;
        return WdatePicker(
            {
                realTimeFmt: "HH:mm:ss",
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    };
    function changeRuleNum(){
        var rule_arr = $('#full_content').find('.full_rule');
        rule_arr.each(function(index,item){
            $(this).find('.rule_num').text(index + 1);
            $(this).find('.con_type').attr('name','rule_' + (index + 1));
            $(this).find('.full_price input[type="text"]').attr('name','full_price_' + (index + 1));
        })
    }


</script>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/full_ship.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/admin/select_sort.js?v=1.0.3"></script>
<script type="text/javascript">
    getPowerInfo('main_config','free_ship','sub_4','满包邮',0);
</script>

