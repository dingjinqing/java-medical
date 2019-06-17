<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<style type="text/css">
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .btn-excel {
        width: 85px;
        height: 30px;
        border: 1px solid #ccc;
        background: #f5f5f5;
        color: #666;
        margin-left: 15px;
    }
    .btn-excel:hover{
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none
    }
    .member_list_main thead td{
        height: 38px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span><a href="/admin/market/bargain/list?nav=1&top_index=4">砍价</a> / </span>
        <span> <span class="ellipsis">${bargin_name!} </span> </span>/
        <span><a href="/admin/market/bargain/record/list?id=${bargin_id!}">砍价用户列表  /</a></span>
        <span style="color: #666;">帮忙砍价用户列表</span>
    </div>
    <div class="order-container">
        <div class="order-info">
            <form action="/admin/market/bargain/record/detail?id=${record_id!}" method="post" id="form1">
                {{ csrf_field()!}
                <input name="action" hidden>
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>用户昵称</span>
                            <input type="text" name="username" value="${options['username']!}"/>
                        </div>
                        <div class="fl">
                            <span>手机号</span>
                            <input type="text" name="mobile" value="${options['mobile']!}"/>
                        </div>
                        <button class="btn-choose">{{ trans("admin/user_list.choose")!}</button>
                        <button class="btn-excel">导出表格</button>
                    </li>
                </ul>
            </form>
        </div>
    </div>
    <div class="order-container" style="padding-top: 0;">
        <div>
            <form action="" id="form2" method="post">
                {{ csrf_field()!}
                <div class="member_list_main" style="min-height:410px;">
                    <table width="70%">
                        <thead>
                        <tr>
                            <td>ID</td>
                            <td>用户昵称</td>
                            <td>手机号码</td>
                            <td>砍价时间</td>
                            <td>帮砍价格</td>
                        </tr>
                        </thead>
                        <tbody>
                            <#list ($data_list as $key => $item)
                                <tr>
                                    <td>{{($data_list->currentPage() - 1) * $data_list->perPage() + $key + 1!}</td>
                                    <td>${item->username!}</td>
                                    <td>${item->mobile!}</td>
                                    <td>${item->add_time!}</td>
                                    <td>${item->bargain_money!}</td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <div class="member_list_footer">
                  <#include "/admin/jump_page_admin.ftl">;
                </div>
            </form>
        </div>
    </div>
</div>

<script>


$('.btn-choose').click(function () {
    $("#page").val(1);
    $("input[name='action']").val("");
    $("#form1").submit();
})

$('.btn-excel').click(function () {
    $("input[name='action']").val("export_excel");
})
</script>


<#include "/admin/footer.ftl">
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script type="text/javascript">
    getPowerInfo('main_config','bargain','sub_4','砍价',0);
</script>
