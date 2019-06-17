<html>
<head>
    <script type="text/javascript" src="{{asset('js/jquery-1.11.1.min.js')!}"></script>
    <script type="text/javascript" src="{{asset('js/admin/util.js')!}"></script>
    <link href="{{asset('css/admin/common.css')!}?v=1.1.2" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="{{asset('css/admin/select_sort.css')!}?v=1.0.0" type="text/css" />
    <link rel="stylesheet" href="{{asset('css/admin/select/select_label.css')!}?v=1.0.2" type="text/css" />
</head>
<body>
<style>
    .has_select_row{
        background-color: #eee;
    }
</style>
<div id="set-category">
    <#if (!$data_list)
        <div class="no_category">
            <input id="select_ids" value="" hidden>
            <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无标签</p>
            </div>
        </div>
    <#else>
        <div class="select-page">
            <form action="{{url('/admin/frame/goods/label/select')!}" method="post" id="form1">

                <div class="select-input-area">
                    <input id="select_ids" name="select_ids" value="${select_ids!}" hidden>
                    <input id="page_all_ids" value="${page_all_ids!}" hidden>
                    <input id="all_label_ids" value="${all_label_ids!}" hidden>
                    <input id="select_single_id"  name="select_single_id" value="" hidden>
                    <input id="single_label_name"  name="single_label_name" value="" hidden>
                    <div class="select-input-row">
                        标签名称：<input name="keywords" type="text" value="${options['keywords']!}">
                    </div>
                    <div><button class="btn_search">查询</button></div>
                </div>
                <div class="select-content">
                    <table class="label_table" >
                        <thead>
                            <tr>
                                <td width="30%">
                                    <#if (count($data_list) != 0 && $is_single !=1)
                                        <input class="select-page-ids" type="checkbox"/>本页全选
                                    </#if>
                                </td>
                                <td width="70%">标签名称</td>
                            </tr>
                        </thead>
                        <#if ($is_single)
                        <tbody  class="select-table-tbody1">

                            <#list  ($data_list as $label)
                            <tr class="has-select-row">
                                <td><input class="select-single-row has-select-row" checked_tr="0" type="checkbox" value="${label->id!}" label_id="${label->id!}" label_name="${label->name!}"/></td>
                                <td>${label->name!}</td>
                            </tr>
                            </#list>
                        </tbody>

                        <#else>
                        <tbody  class="select-table-tbody">
                            <#list  ($data_list as $label)
                                <tr class="<#if  (in_array($label->id,explode(',',$select_ids))) has-select-row </#if>">
                                    <td><input class="select-row" type="checkbox" value="${label->id!}"
                                               <#if  (in_array($label->id,explode(',',$select_ids))) checked </#if>/></td>
                                    <td>${label->name!}</td>
                                </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
                    <div>
                        <div class="all_check" style="width: 10%; float: left;line-height: 50px;<#if (count($data_list) == 0) display:none </#if>"><input class="select-all-ids" type="checkbox"/>全选</div>
                        <div class="jump_page" style="<#if (count($data_list) != 0) width: 90%; </#if> float: left"><#include ('admin.jump_page_admin')</div>
                    </div>
                </div>

            </form>
        </div>
    </#if>
</div>
<script type="text/javascript" src="{{asset('js/admin/select/multiple_select.js')!}?v=1.0.5"></script>
<script>
    <#if  ($is_single)
    $('.label_table').on('click','tbody tr', function(){
        var flag_back = $(this).attr('checked_tr');
        $('.label_table tbody tr').each( function () {
            $(this).attr('checked_tr',0)
            $(this).removeClass('has_select_row');
            $(this).find('input').prop('checked',false);

        })
        if(flag_back == 0){
            $(this).addClass('has_select_row');
            $(this).find('input').prop('checked',true);
            $(this).attr('checked_tr',1);
            $('#select_single_id').val($(this).find('input').attr('label_id'));
            $('[name="single_label_name"]').val($(this).find('input').attr('label_name'));
        }
    });
    </#if>
</script>
</body>
</html>