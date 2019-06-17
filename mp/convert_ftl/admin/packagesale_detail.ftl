<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/packagesale_manage.css?v=1.0.1" type="text/css"/>
<style type="text/css">
    .no_data_style{
        margin-top: 10px !important;
    }
    .tb_paging{
        margin-top: 10px !important;
    }
    .tb_paging tr td{
        border:none !important;
    }
    .tb_paging input[type='text']{
        width: 80px;
        padding-left: 0;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">打包一口价 / </span>
            <span>${act_name!}-活动明细</span>
        </div>
    </div>
    <div class="main-container">
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="search_info" style="padding-bottom: 15px">
                <ul class="clearfix">
                    <li class="clearfix">
                        <div class="fl">
                            <span>用户昵称</span>
                            <input type="text" placeholder="请输入用户昵称" name="username" value="${request['username']!}">
                        </div>
                        <div class="fl">
                            <span>手机号</span>
                            <input type="text" placeholder="请输入用户手机号" name="user_mobile" value="${request['user_mobile']!}">
                        </div>
                        <div class="fl">
                            <span>订单号</span>
                            <input type="text" placeholder="请输入订单号" name="order_sn" value="${request['order_sn']!}">
                        </div>
                        <div class="fl btn_group" style="width: 160px">
                            <a href="##" class="btn_search">筛选</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                    <tr>
                        <td width="15%">用户ID</td>
                        <td width="15%">用户昵称</td>
                        <td width="15%">手机号</td>
                        <td width="25%">一口价订单号</td>
                        <td width="20%">下单时间</td>
                        <td width="10%">下单金额</td>
                    </tr>
                    </thead>
                    <tbody>
                        <#if ($data_list)
                            <#list ($data_list as $order)
                                <tr>
                                    <td width="15%">${order->user_id!}</td>
                                    <td width="15%">
                                        <a href="/admin/user/manage/list?id=${order->user_id!}">${order->user_name!}</a>
                                    </td>
                                    <td width="15%">${order->user_mobile!}</td>
                                    <td width="25%">
                                        <a href="/admin/orders/manage/info?order_sn=${order->order_sn!}">${order->order_sn!}</a>
                                    </td>
                                    <td width="20%">${order->add_time!}</td>
                                    <td width="10%">${order->money_paid!}</td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
                <#include ('admin.jump_page_admin')
            </div>
        </form>
    </div>
</div>
<#include "/admin/footer.ftl">
<script>
    $('.btn_search').click(function () {
        $('#form1').submit();
    })
    getPowerInfo('main_config','package_sale','sub_4','打包一口价',0);
</script>