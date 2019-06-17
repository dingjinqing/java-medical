<html style="height: 320px;">
<head>
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.0" type="text/css"/>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="/css/admin/brand_related.css?v=1.0.2" type="text/css" />    
    <style>
        a{
            text-decoration: none;
        }
        .info_table{
            margin-top: 10px;
        }
        .brands_tr_choose{
            background: #eee;
        }
    </style>
</head>
<body style="background:none;height: 320px;margin:0">
<form action="/admin/goods/brand/selectBrand" method="POST" id="form1">
    {{ csrf_field()!}
<div class="brand_top">
    <input type="hidden" name="not_classify_id" value="${not_classify_id!}">
    <input type="hidden" name="all_id" value="${allId!}" disabled />
    <input type="hidden" name="is_single" value="${is_single!}" />
    <input type="hidden" name="single_brand_name" value="${single_brand_name!}">
    <ul class="search_box clearfix">
        <li>
            品牌名称：<input type="text" name="keywords" value="${keywords!}">
        </li>
        <li>
            品牌分类：
            <select name="classify_id" id="">
                <option value="0">请选择</option>
                <#list ($classify_list as $item)
                    <#if ($item->classify_id != $not_classify_id )
                    <option value="${item->classify_id!}"
                            <#if ($classify_id == $item->classify_id)selected="selected"</#if>>${item->classify_name!}</option>
                    </#if>
                </#list>
            </select>
        </li>
            <button class="btn_search" style="margin-left:10px;margin-top:10px;">筛选</button>
        </li>
    </ul>
    <input type="hidden" name="record_select_value" id="record_select_value" value="${record_select_value!}">
    <div class="info_table">
        <table width="100%" class="brand_table">
            <thead>
                <tr>
                    <#if ($is_single == 0)<th width="5%"><input type="checkbox" id="all_check"></th></#if>
                    <th width="40%">品牌名称</th>
                    <th width="30%">品牌分类</th>
                    <th width="25%">创建时间</th>
                </tr>
            </thead>
            <tbody>
            <#list ($data_list as $item)
                <tr <#if ($is_single == 1 && $record_select_value == $item->id) class="brands_tr_choose" checked_tr="1" <#else> class="checked_brands" checked_tr="0"  </#if>   brand_id="${item->id!}" brand_name="${item->brand_name!}">
                    <#if ($is_single == 0)<td><input type="checkbox" name="brand_select[]" value="${item->id!}"></td></#if>
                    <td>${item->brand_name!}</td>
                    <td>${item->classify_name!}</td>
                    <td>${item->add_time!}</td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="clearfix jump_page">
            <#include "/admin/jump_page_admin.ftl">
        </div>
    </div>
</div>
</form>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script language="JavaScript" src="/js/admin/util.js?v=1.0.1"></script>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script>
    var record_select_value = $('#record_select_value').val() == '' ? [] : $('#record_select_value').val().split(',');
    var all_ids = $('input[name="all_id"]').val().split(',') ? $('input[name="all_id"]').val().split(',') : [];
    // var is_single = $('input[name="is_single"]').val();
    <#if (!$is_single)
        $('input[name="brand_select[]"]').each(function (index, item) {
            if ($(this).is(':disabled')) {
                $(item).prop('checked', false);
                $(item).parents('tr').removeClass('brands_tr_choose');
                return true;
            }
            if ($.inArray($(item).val(), record_select_value) > -1) {
                $(item).prop('checked', true);
                $(item).parents('tr').addClass('brands_tr_choose');
            } else {
                $(item).prop('checked', false);
                $(item).parents('tr').removeClass('brands_tr_choose');
            }
        });
        if (all_ids.length > 0 && util.isSubArray(all_ids,record_select_value)) {
            //触发全选
            $('#all_check').trigger('click');
        }
    </#if>

    function checked(danxuan) {
        var is_checked = $(danxuan).is(':checked');
        if (is_checked) {
            record_select_value.push($(danxuan).val());
            $(danxuan).parents('tr').addClass('brands_tr_choose');
        } else {
            var index = $.inArray($(danxuan).val(), record_select_value);
            record_select_value.splice(index, 1);
            $(danxuan).parents('tr').removeClass('brands_tr_choose');
        }
        $('#record_select_value').val(record_select_value.join(','));
        if (all_ids.length > 0 && util.isSubArray(all_ids,record_select_value)) {
            $('#all_check').prop('checked', true);
        } else {
            $('#all_check').prop('checked', false);
        }
    }
    $('.checked_brands').on('click',function (e) {
        if(event.target.localName !== 'input') {
            $(this).find('input[type="checkbox"]').prop('checked', $(this).find('input[type="checkbox"]').is(':checked') ? false : true);
        }
        checked($(this).find('input[type="checkbox"]'));
    });
    $('.btn_search').click(function(){
        $('#form1').submit();
    })
    $('[name="classify_id"]').change(function(){
        $("#form1").submit();
    });
    //全选
    $('#all_check').click(function () {
        var is_checked = $(this).is(':checked');
        if (is_checked) {
            record_select_value = util.mergeArray(record_select_value,all_ids) ;
            $('input[name="brand_select[]"]').parents('tr').addClass('brands_tr_choose');
        } else {
            for (var i = 0; i < all_ids.length; i++) {
                var index = $.inArray(all_ids[i],record_select_value);
                record_select_value.splice(index, 1);
            }
            $('input[name="brand_select[]"]').parents('tr').removeClass('brands_tr_choose');
        }
        $('input[name="brand_select[]"]').prop('checked', is_checked);
        $('#record_select_value').val(record_select_value.join(','));
    });
    <#if  ($is_single)
    $('.brand_table').on('click','tbody tr', function(){
        var flag_back = $(this).attr('checked_tr');
        $('.brand_table tbody tr').each( function () {
            $(this).removeClass('brands_tr_choose');
            $(this).attr('checked_tr',0)
            $('#record_select_value').val('');
            $('[name="single_brand_name"]').val('');
        })
        if(flag_back == 0){
            $(this).addClass('brands_tr_choose');
            $(this).attr('checked_tr',1)
            $('#record_select_value').val($(this).attr('brand_id'));
            $('[name="single_brand_name"]').val($(this).attr('brand_name'));
        }
    });
    </#if>
</script>
</body>
</html>