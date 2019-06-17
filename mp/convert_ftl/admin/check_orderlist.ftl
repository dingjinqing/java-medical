<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/check_order.css?v=1.0.0" type="text/css"/>
<style>
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
</style>
<div class="title">
    <span>订单管理 / </span><span style="color: #666;">买单订单</span>
</div>
<form action="" method="post" id="form1">
    {{ csrf_field()!}
<div class="order-container">
    <div class="order_serarch_info">
        <ul class="clearfix">
            <li class="check_info_li clearfix">
                <div class="fl">
                    <span>订单号</span>
                    <input type="text" value="${request['order_sn']!}" name="order_sn" placeholder="请输入订单号">
                </div>
                <div class="fl time_choose">
                    <span>支付时间</span>
                    <input type="text" name="pay_time_start" placeholder="支付时间" value="${request['pay_time_start']!}" onclick="picker();" autocomplete="off">
                    至
                    <input type="text" name="pay_time_end" placeholder="支付时间" value="${request['pay_time_end']!}" onclick="picker();" autocomplete="off">
                </div>
            </li>
            <li class="check_info_li clearfix">
                <div class="fl">
                    <span>买单人</span>
                    <input type="text" value="${request['payer_name']!}" name="payer_name" placeholder="请输入买单人昵称">
                </div>
                <div class="fl">
                    <span>门店</span>
                    <select name="store_id" id="">
                        <option value="0">请选择</option>
                        <#list ($store as $item)
                            <option value="${item->store_id!}" <#if ($request['store_id'] == $item->store_id) selected </#if>>${item->store_name!}</option>
                        </#list>
                    </select>
                </div>
                <div class="fl" style="width: 200px;">
                    <span>订单状态</span>
                    <select name="order_status" style="width: 80px">
                        <option value="0">请选择</option>
                        <option value="1"  <#if ($request['order_status'] == 1) selected </#if>>已支付</option>
                        <option value="2"  <#if ($request['order_status'] == 2) selected </#if>>已退款</option>
                    </select>
                </div>
                <div class="fl" style="margin-left: 48px;">
                    <button type="button" class="btn_search">筛选</button>
                    {{--<button type="button" class="btn_excel">导出表格</button> --!}
                </div>
            </li>
        </ul>
    </div>
    {{--买单总金额--!}
    <div class='money_amount'>
        <img src="http://${image_domain!}/image/admin/icon_checkout.png" alt="">
        买单收银总金额 <span>${data_list['all_money']!} 元</span>
    </div>
    <div class="check_list">
        <ul class="cl_banner clearfix">
            <li <#if ($request['star_flag'] != 1) class="nav_active" </#if>>
                <a href="/admin/orders/check/list">全部</a>
            </li>
            <li <#if ($request['star_flag'] == 1) class="nav_active" </#if>>
                <a href="/admin/orders/check/list?star_flag=1">追星订单</a>
            </li>
        </ul>
        <div class="checklist_table">
            <table width="100%">
                <thead>
                    <tr>
                        <td>买单门店</td>
                        <td>买单人</td>
                        <td>买单时间</td>
                        <td>订单状态</td>
                        <td>买单收银金额</td>
                    </tr>
                    <tr class="jiange">
                        <td colspan="4"></td>
                    </tr>
                </thead>
                <tbody>
                <#list ($data_list['list'] as $item)
                    <tr class="check-tb-head">
                        <td colspan="5">
                            <span class="span1"> 支付单号：${item->order_sn!}</span>
                            <span class="span2">支付方式：微信支付</span>
                            <span class="fr" order_sn="${item->order_sn!}">
                                <span class="btn-star-flag ${item->star_flag ? "" : "empty-flag"!}"
                                      title="切换星标"></span>
                                <a href="javascript:void(0);" class="add_text" order_sn="${item->order_sn!}"
                                   seller_remark="${item->seller_remark!}">添加备注</a>
                                <a href="/admin/orders/check/info?order_sn=${item->order_sn!}" class="click_to_detail" target="_blank">查看详情</a>
                            </span>
                        </td>
                    </tr>
                    {{--这里的tr循环订单下的多个买单--!}
                    <tr class="check_tb_body">
                        <td>${item->store_name!}</td>
                        <td>${item->username!}</td>
                        <td>${item->pay_time!}</td>
                        <td>${item->order_status_name!}</td>
                        <td>${item->money_paid!}</td>
                    </tr>
                    {{--第二个循环结束--!}
                </#list>
                </tbody>
                {{--第一个循环结束--!}
            </table>
        </div>
            ${data_list =  $data_list['list']!}
        <div class="clearfix">
           <#include "/admin/jump_page_admin.ftl">
        </div>
    </div>
</div>
</form>

<div id="set-text">
    <textarea name="seller_remark" id="seller_remark"></textarea>
</div>

<script src="/js/admin/region.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/check_order.js?v=1.0.5" type="text/javascript"></script>
<script type="text/javascript">
    //分页
    function gopage(page) {
        var last_page = parseInt('${data_list->lastPage()!}');
        if (parseInt(page) > last_page) {
            page = last_page;
        }
        var href = $(".order-list-nav li.nav-active a").attr('href');
        $("#act").val("");
        $("#page").val(page);
        $('#form1').attr('action', href);
        $("#form1").submit();
    }


    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    $(".check_list .cl_banner li a").click(function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#form1').attr('action', href);
        $("#page").val(1);
        $('#form1').submit();
    });
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">