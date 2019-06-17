<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.0" type="text/css"/>
<style type="text/css">
    .tb_paging tr td{
        border: none;
    }
    .tb_paging{
        margin-top: 10px;
    }
    #page{
        width: 80px;
        padding-left: 0;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;"><a href="/admin/market/presale/list">${title!}</a> /</span>
            <span>${presale->presale_name!} - 活动明细</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <form action="/admin/market/presale/detail?pin_group_id=${presale->id!}" method="post" id="form1">
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
            <div class="content_table order_table clearfix">
                <table width="100%">
                    <thead>
                    <tr>
                        <td width="10%">用户ID</td>
                        <td width="10%">用户昵称</td>
                        <td width="10%">手机号</td>
                        <td width="15%">订单号</td>
                        <td width="20%">下单商品</td>
                        <td width="5%">商品数量</td>
                        <td width="10%">下单时间</td>
                        <td width="10%">订单状态</td>
                        <td width="10%">下单金额</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#if ($data_list)
                        <#list ($data_list as $order)
                            <tr>
                                <td width="5%">${order->user_id!}</td>
                                <td width="15%">
                                    <a href="/admin/user/manage/list?id=${order->user_id!}">${order->user_name!}</a>
                                </td>
                                <td width="10%">${order->user_mobile!}</td>
                                <td width="15%">
                                    <a href="/admin/orders/manage/info?order_sn=${order->order_sn!}">${order->order_sn!}</a>
                                </td>
                                <td width="25%">
                                    <div class="goods_info clearfix">
                                        <#list ($order->order_goods as $goods)
                                            <div class="goods_img">
                                                <img src="${goods->goods_img!}" alt="">
                                            </div>
                                            <div class="goods_content">
                                                <div class="goods_name">${goods->goods_name!}</div>
                                                <div class="goods_desc">${goods->goods_attr!}</div>
                                            </div>
                                        </#list>
                                    </div>
                                </td>
                                <td width="5%"> ${order->goods_amount!}</td>
                                <td width="10%">${order->add_time!}</td>
                                <td width="10%" >
                                    <#if ($order->order_status !=3 && $order->order_status != 5)
                                        ${order->order_status_name!}
                                    </#if>
                                    <#if ($order->part_ship_flag && $order->order_status==3)
                                        (部分发货)
                                    </#if>
                                    <#if ($order->deliver_type==1 && $order->order_status==3)
                                        待核销
                                    </#if>
                                    <#if ($order->deliver_type==0 && $order->order_status==3)
                                        待发货
                                    </#if>
                                    <#if ($order->deliver_type==1 && $order->order_status==5)
                                        已自提
                                    </#if>
                                    <#if ($order->deliver_type==0 && $order->order_status==5)
                                        已收货
                                    </#if>
                                </td>
                                <td width="10%">
                                    <#if  ($order->bk_order_paid == 0)
                                        0.00
                                        <#elseif> ($order->bk_order_paid == 1)
                                        {{number_format($order->money_paid + $order->score_discount + $order->use_account, 2, '.', '')!}
                                        <#else>
                                        {{number_format($order->money_paid + $order->score_discount + $order->use_account + $order->bk_order_money, 2, '.', '')!}
                                    </#if>
                                </td>
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
    getPowerInfo('main_config','pre_sale','sub_4','定金膨胀',0);
</script>