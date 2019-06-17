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
    .member_list_main tbody td a{
        color: #447af9;
    }
    .member_list_footer td{
        border: none !important;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span><a href="/admin/market/bargain/list?nav=1&top_index=4">砍价</a> / </span><span><span class="ellipsis">${bargin_name!}</span> - </span>
        <span style="color: #666;">砍价用户</span>
    </div>
    <form action="/admin/market/bargain/record/list?id=${bargain_id!}" method="post" id="form1">
        <div class="order-container">
            <div class="order-info">
                {{ csrf_field()!}
                <input name="action" hidden>
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>用户昵称</span>
                            <input name="username" type="text" value="${options['username']!}"/>
                        </div>
                        <div class="fl">
                            <span>手机号</span>
                            <input name="mobile" type="text" value="${options['mobile']!}"/>
                        </div>
                        <div class="fl">
                            <span>砍价状态</span>
                            <select name="status" id="user_card">
                                <option value="-1">{{ trans("admin/user_list.all")!}</option>
                                <option value="1" <#if ($options['status'] == '1') selected </#if>>成功</option>
                                <option value="2" <#if ($options['status'] == '2') selected </#if>>失败</option>
                                <option value="0" <#if ($options['status'] == '0') selected </#if>>砍价中</option>
                            </select>
                        </div>
                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl" style="width: auto;">
                            <span>发起时间</span>
                            <input  style="width: 150px; height: 30px;" type="text" name="start_time" value="${options['start_time']!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input  style="width: 150px; height: 30px;" type="text" name="end_time" value="${options['end_time']!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <button class="btn-choose">{{ trans("admin/user_list.choose")!}</button>
                        <button class="btn-excel">导出表格</button>
                    </li>
                </ul>
            </div>
        </div>
        <div class="order-container" style="padding-top: 0;">
            <div>
                <div class="member_list_main" style="min-height:410px;">
                    <table width="100%">
                        <thead>
                        <tr>
                            <td width="50px">ID</td>
                            <td width="310px">商品名称</td>
                            <td>发起砍价用户昵称</td>
                            <td>手机号码</td>
                            <td>发起时间</td>
                            <td>已砍金额</td>
                            <td>待砍金额</td>
                            <td>参与砍价人数</td>
                            <td>砍价状态</td>
                            <td>{{ trans("admin/user_list.operation")!}</td>
                        </tr>
                        </thead>
                        <tbody>
                            <#list ($data_list as $key => $item)
                                <tr>
                                    <td >{{($data_list->currentPage() - 1) * $data_list->perPage() + $key + 1!}</td>
                                    <td>${item->goods_name!}</td>
                                    <td>${item->username!}</td>
                                    <td >${item->mobile!}</td>
                                    <td >${item->add_time!}</td>
                                    <td >${item->bargain_money!}</td>
                                    <td>{{bcsub($item->goods_price - $item->expectation_price,$item->bargain_money,2)!}</td>
                                    <td>${item->user_number!}</td>
                                    <td>
                                        <#if ($item->status == 0)砍价中
                                        <#elseif>($item->status == 1)成功
                                        <#elseif>($item->status == 2)失败
                                        </#if>
                                    </td>
                                    <td>
                                        <a href="/admin/market/bargain/record/detail?id=${item->id!}">
                                            查看砍价用户
                                        </a>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                    <div class="member_list_footer" style="padding: 0px 0px;margin-top: 10px">
                        <#include "/admin/jump_page_admin.ftl">
                    </div>
                </div>

            </div>
        </div>
    </form>

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
