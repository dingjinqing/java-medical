<#include ("system.header")
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.0" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_since.css?v=1.0.1" type="text/css"/>
<style>
    .btn-verify:hover, .btn-verify:focus {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-deliver:hover, .btn-deliver:focus {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .order-status .btn-close:hover,
    .order-status .btn-close:focus {
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none
    }

    .btn-finish:hover, .btn-finish:focus {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }

    /*.since-info-detail .fl {*/
    /*height: 314px;*/
    /*}*/

    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .fanli_tables tr:first-of-type{
        background: #f5f5f5;
    }
    .fanli_tables tr:first-of-type th{
        font-weight:normal;
        padding: 10px 0;
    }
    .fanli_tables tr td{
        border:1px solid #eee;
        padding: 10px 0;
    }
    .li-message,.li-time{
        width: 100%;
        text-align: center;
    }
    .li-time{
        font-size: 12px;
        height: 18px;
        line-height: 18px;
    }
    .li-message{
        height: 18px;
        line-height: 18px;
        margin-top: 5px;
    }
    .since-info-detail .order-flex{
        padding:10px 0;
    }
    .order-tb-body td {
        padding: 15px 15px;
    }
    .tishi_content{
        width: 300px;
        padding: 10px;
        background: #fff;
        box-shadow: 0px 0px 10px #f0f0f0;
        position: absolute;
        z-index: 100;
        display: none;
        top: -160px;
        left: -45px;
        text-align: left;
    }
</style>
<div class="title">
    <span>订单管理 / </span>
    <span style="color: #666;">查看详情</span>
    {{--<span style="color: #666;"><#if ($order->deliver_type == 0) 快递订单 <#else> 自提订单 </#if></span>--!}
</div>

<#if ($message)
    <div class="message-tip alert alert-warning margin-top-10 margin-bottom-0">
        ${message!}
    </div>
</#if>

<div class="order-container">
    <form name="formData" action="${act_url!}" id="form1" method="post">
        {{ csrf_field()!}
        <input type="hidden" name="act" id="act">
        <input type="hidden" name="act_order_sn" id="act_order_sn">
        <input type="hidden" name="shipping_id" id="shipping_id">
        <input type="hidden" name="shipping_no" id="shipping_no">
        <input type="hidden" name="verify_code" id="verify_code">
        <input type="hidden" name="act_star_flag" id="act_star_flag">
        <input type="hidden" name="product_ids" id="product_ids">
        <input type="hidden" name="send_numbers" id="send_numbers">
        <input type="hidden" name="refund_money" id="refund_money">
        <input type="hidden" name="reason" id="reason">
        <input type="hidden" name="return_type" id="return_type">


        <div class="since-info">
            <div class="since-info-top">
                <span>订单号：${order->order_sn!}</span>
                <span>订单状态：
                    <#if ($order->order_status !=3 && $order->order_status != 5)
                        ${order->order_status_name!}
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
                </span>
                <#if ($order->part_ship_flag == 1)
                    <span>发货类型： 部分发货</span>
                </#if>
            </div>
            <div class="since-info-path">
                <ul class="clearfix">
                    <#if ($order->order_status == 0)
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li3">买家已付款</li>
                        <li class="since-info-li3">卖家已发货</li>
                        <li class="since-info-li4">订单完成</li>
                    <#elseif>($order->order_status == 1)
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li5">
                            <div class="li-message">订单已取消</div>
                            <div class="li-time">${order->cancelled_time!}</div>
                        </li>
                    <#elseif>($order->order_status == 2)
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li5">
                            <div class="li-message">订单已关闭</div>
                            <div class="li-time">${order->closed_time!}</div>
                        </li>
                    <#elseif>(in_array($order->order_status,[3]))
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <#if ($order->deliver_type==1)
                            <li class="since-info-li3">买家已自提</li>
                        </#if>
                        <#if ($order->deliver_type==0)
                            <li class="since-info-li3">卖家已发货</li>
                        </#if>
                        <li class="since-info-li4">订单完成</li>
                    <#elseif>(in_array($order->order_status,[4]))
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">卖家已发货</div>
                            <div class="li-time">${order->shipping_time!}</div>
                        </li>
                        <li class="since-info-li4">订单完成</li>
                    <#elseif>(in_array($order->order_status,[5]))
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <#if ($order->deliver_type==1)
                            <li class="since-info-li2">
                                <div class="li-message">买家已自提</div>
                                <div class="li-time">${order->confirm_time!}</div>
                            </li>
                        </#if>
                        <#if ($order->deliver_type==0)
                            <li class="since-info-li2">
                                <div class="li-message">卖家已发货</div>
                                <div class="li-time">${order->shipping_time!}</div>
                            </li>
                        </#if>
                        <li class="since-info-li4">订单完成</li>
                    <#elseif>($order->order_status == 6)
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <#if ($order->deliver_type==1)
                            <li class="since-info-li2">
                                <div class="li-message">买家已自提</div>
                                <div class="li-time">${order->confirm_time!}</div>
                            </li>
                        </#if>
                        <#if ($order->deliver_type==0)
                            <li class="since-info-li2">
                                <div class="li-message">卖家已发货</div>
                                <div class="li-time">${order->shipping_time!}</div>
                            </li>
                        </#if>
                        <li class="since-info-li5">
                            <div class="li-message">订单完成</div>
                            <div class="li-time">${order->finished_time!}</div>
                        </li>
                    <#elseif>(in_array($order->order_status,[7]))
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <li class="since-info-li4">退货完成</li>
                    <#elseif>(in_array($order->order_status,[8]))
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <li class="since-info-li5">
                            <div class="li-message">退货完成</div>
                            <div class="li-time">${order->return_finish_time!}</div>
                        </li>
                    <#elseif>(in_array($order->order_status,[9]))
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <li class="since-info-li4">退款完成</li>
                    <#elseif>(in_array($order->order_status,[10]))
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <li class="since-info-li5">
                            <div class="li-message">退款完成</div>
                            <div class="li-time">${order->refund_finish_time!}</div>
                        </li>
                    <#elseif> ($order->order_status == 11)
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">拼团中</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li3">卖家已发货</li>
                        <li class="since-info-li4">订单完成</li>
                    <#elseif> ($order->order_status == 12)
                        <li class="since-info-li1">
                            <div class="li-message">买家已下单</div>
                            <div class="li-time">${order->add_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">买家已付款</div>
                            <div class="li-time">${order->pay_time!}</div>
                        </li>
                        <li class="since-info-li2">
                            <div class="li-message">已成团</div>
                            <div class="li-time">${order->pin_end_time!}</div>
                        </li>
                        <li class="since-info-li3">卖家已发货</li>
                        <li class="since-info-li4">订单完成</li>
                    </#if>
                </ul>
            </div>
            <div class="since-info-detail clearfix">
                <div class="fl">
                    <div class="since-order">订单信息</div>
                    <div class="order-flex">
                        <div>订单状态：
                            <#if ($order->order_status !=3 && $order->order_status != 5)
                                ${order->order_status_name!}
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
                        </div>
                        <div>订单金额：${order->money_paid!}元</div>
                    </div>
                    <div class="order-flex">
                        <div>下单时间：${order->add_time!}</div>
                        <#if ($order->deliver_type == 1 && !empty($order->pickup_time))
                            <div>预约自提时间：${order->pickup_time!}</div>
                        </#if>
                    </div>
                    <div class="order-flex">
                        <#if  ($order->goods_type == 4)
                            <div>支付方式：积分兑换</div>
                        <#else>
                            <div>支付方式：${order->pay_name!}</div>
                        </#if>
                        <div>配送方式：${order->deliver_type == 0 ? "快递" : "自提"!}</div>
                    </div>
                    <div class="order-flex">
                        <div>订单号：${order->order_sn!}</div>
                        <#if ($order->deliver_type==1)
                            <div>自提门店：${order->store_name!}</div>
                        </#if>
                    </div>
                    <#if ($order->deliver_type==0 && ($order->order_status!=0 && $order->order_status !=1 && $order->order_status != 2))
                        <div class="order-flex">
                            <#if ($order->part_ship_flag != 1 && $order->order_status != 3)
                                <div class="shipping-container">
                                    发货时间：${order->shipping_time!}
                                </div>
                            <#elseif>($order->part_ship_flag == 1)
                                <div class="shipping-container">
                                    发货类型：部分发货
                                </div>
                            </#if>
                        </div>
                    </#if>

                    {{--<div class="order-flex">--!}
                    {{--<div class="shipping-container">--!}
                    {{--<p>使用会员卡：牡丹园店会员卡</p>--!}
                    {{--<p style="padding-left: 72px">使用会员卡享受会员折扣2折,本次减价12元</p>--!}
                    {{--</div>--!}
                    {{--</div>--!}

                </div>
                <div class="fl">
                    <div class="since-order">用户信息</div>
                    <div class="order-flex">
                        <div>
                            {{--                            收货客户：${order->consignee!}--!}
                            收货客户：
                            <a href="/system/user/list?shop_id=${order->shop_id!}&id=${order->user_id!}"
                               style="color: #0E70CA">${order->consignee!}</a>
                        </div>
                        <div>手机号码：${order->mobile!}</div>
                    </div>
                    <#if ($order->deliver_type==0)
                        <div class="order-flex">
                            <div>收货地址：${order->complete_address!}</div>
                        </div>
                    </#if>
                    <#if ($invoice_info!= "")
                        <div class="order-flex">
                            <#if ($invoice_info->type == 0 && $invoice_info!= "")
                                <div>
                                    <div style="margin: 0 0 10px 0px;">
                                        <span style="margin-right: 73px">发票信息：发票类型：企业发票</span>
                                        <span>发票抬头：${invoice_info->title!}</span>
                                    </div>
                                    <#if ($invoice_info->taxNumber)
                                        <div style="margin: 0 0 10px 60px;">公司税号：${invoice_info->taxNumber!}</div>
                                    </#if>
                                    <#if ($invoice_info->companyAddress)
                                        <div style="margin: 0 0 0px 60px;">公司地址：${invoice_info->companyAddress!}</div>
                                    </#if>
                                </div>
                            </#if>
                            <#if ($invoice_info->type == 1 && $invoice_info!= "")
                                <div>
                                    <div>
                                        <span style="margin-right: 73px">发票信息： 发票类型：个人发票</span>
                                        <span>发票抬头：${invoice_info->title!}</span>
                                    </div>
                                </div>
                            </#if>
                        </div>
                    </#if>
                    <#if ($order->id_card !="" || $order->true_name !="")
                        <div class="order-flex">
                            <#if ($order->true_name !="")
                                <div>真实姓名：${order->true_name!}</div>
                            </#if>
                            <#if ($order->id_card !="")
                                <div>身份证号：${order->id_card!}</div>
                            </#if>
                        </div>
                    </#if>
                    <div class="order-flex">
                        <div>买家留言：${order->add_message!}</div>
                    </div>
                </div>
            </div>
            <div class="order-remark">
                <img src="http://${image_domain!}/image/admin/since-edit.png" class="add_text" style="cursor: pointer"
                     order_sn="${order->order_sn!}"
                     seller_remark="${order->seller_remark!}" alt=""/>
                卖家备注：<span class="seller-remark">${order->seller_remark!}</span>
            </div>
        </div>
        <#if (count($partShipGroup) > 0)
            <div class="order-list-table">
                <h4 class="padding-bottom-10">${order->deliver_type==0 ? "快递" :"自提"!}信息：</h4>
                <table style="width: 100%">
                    <thead>
                    <tr>
                        <td width="300px">商品名称</td>
                        <td width="150px">规格</td>
                        <td>${order->deliver_type == 0?'发货数量':'提货数量'!}</td>
                        <#if ($order->deliver_type == 0)
                            <td>物流公司</td>
                            <td>物流单号</td>
                            <td width="200px">发货时间</td>
                            <td width="200px">确认收货时间</td>
                        <#else>
                            <td>自提门店</td>
                            <td>自提时间</td>
                        </#if>
                    </tr>
                    </thead>
                    <#list ($partShipGroup as $shipGroup)
                        <#list ($shipGroup->goods as $i=>$orderGood)
                            <#if ($shipGroup->send_number>0)
                                <tr class="order-tb-body"
                                    order_sn="${shipGroup->order_sn!}"
                                    batch_no="${shipGroup->batch_no!}"
                                    shipping_id="${shipGroup->shipping_id!}"
                                    shipping_no="${shipGroup->shipping_no!}">
                                    <td>${orderGood->goods_name!}</td>
                                    <td>${orderGood->goods_attr!}</td>
                                    <td>${orderGood->send_number!}</td>
                                    <#if ($i == 0)
                                        <#if ($order->deliver_type==0 )
                                            <td rowspan="{{ count($shipGroup->goods)!}">
                                                <span class="shipping-name">${shipGroup->shipping_name!}</span>
                                            </td>
                                            <td rowspan="{{ count($shipGroup->goods)!}">
                                                <span class="shipping-no"> ${shipGroup->shipping_no!}</span>
                                                <#if ($order->deliver_type==0 )
                                                    <#if (in_array($order->order_status,[3,4]) && (!$shipGroup->confirm_time))
                                                        <a href="javascript:void(0);"
                                                           class="text-info btn-modify-shipping">修改</a>
                                                    </#if>

                                                    <a class="text-info" target="_blank"
                                                   href="/system/order/express/show?shipping_no=${shipGroup->shipping_no!}">查询</a>
                                                </#if>
                                            </td>
                                            <td rowspan="{{ count($shipGroup->goods)!}">
                                                ${shipGroup->shipping_time!}
                                            </td>
                                            <td rowspan="{{ count($shipGroup->goods)!}">
                                                {{--                                        ${shipGroup->confirm_time!}--!}
                                                ${order->confirm_time!}
                                            </td>
                                        <#else>
                                            <td>${order->store_name!}</td>
                                            <td>${order->confirm_time!}</td>
                                        </#if>
                                    </#if>
                                </tr>
                            </#if>
                        </#list>
                    </#list>
                </table>
            </div>
        </#if>

        <#if (count($returnOrders) > 0)
            <div class="order-list-table">
                <h4 class="padding-bottom-10">退货/退款信息：</h4>
                <table style="width: 100%">
                    <thead>
                    <tr>
                        <td width="250px">商品名称</td>
                        <td width="110px">规格</td>
                        <td>退货数量</td>
                        <td>退款类型</td>
                        <td width="160px">退款状态</td>
                        <td>退款金额</td>
                        <td width="110px">申请时间</td>
                        <td width="110px">退款时间</td>
                    </tr>
                    </thead>
                    <#list ($returnOrders as $returnOrder)
                        <#list ($returnOrder->return_order_goods as $i=>$orderGood)
                            <tr class="order-tb-body">
                                <td style="word-wrap:break-word;">${orderGood->goods_name!}</td>
                                <td>${orderGood->goods_attr!}</td>
                                <td>${orderGood->goods_number!}</td>
                                <#if ($i == 0)
                                    <td rowspan="{{ count($returnOrder->return_order_goods)!}">
                                        ${returnOrder->return_type_name!}
                                    </td>
                                    <td rowspan="{{ count($returnOrder->return_order_goods)!}">
                                        ${returnOrder->refund_status_name!}
                                    </td>
                                    <td rowspan="{{ count($returnOrder->return_order_goods)!}">
                                        {{ bcadd($returnOrder->money,$returnOrder->shipping_fee,2)!}
                                        <a class="text-link" target="_blank"
                                           href="/system/order/return/detail?shop_id=${order->shop_id!}&order_sn=${returnOrder->order_sn!}&ret_id=${returnOrder->ret_id!}">
                                            <#if (in_array($returnOrder->refund_status,[1,2,4])) 审批 <#else> 详情 </#if>
                                        </a>
                                    </td>
                                    <td rowspan="{{ count($returnOrder->return_order_goods)!}">
                                        <#if ($returnOrder->return_type==1)
                                            ${returnOrder->apply_time!}
                                        <#else>
                                            ${returnOrder->shipping_or_refund_time!}
                                        </#if>
                                    </td>
                                    <td rowspan="{{ count($returnOrder->return_order_goods)!}">
                                        ${returnOrder->refund_success_time!}
                                    </td>
                                </#if>
                            </tr>
                        </#list>
                    </#list>
                </table>
            </div>
        </#if>

        <div class="order-list-table">
            <table width="100%">
                <thead>
                <tr>
                    <td width="270px">商品</td>
                    <td width="100px">货号</td>
                    <td width="100px">单价</td>
                    <td width="100px">数量</td>
                    <td>
                        <#if ($order->deliver_type==0)
                            收货人信息
                        </#if>
                        <#if ($order->deliver_type==1)
                            提货人信息
                        </#if>
                    </td>
                    <td>下单时间</td>
                    <td>订单状态</td>
                    <#if ($order->fanli_type > 0)
                        <td>返利金额</td>
                    </#if>
                    <td>支付金额</td>
                </tr>
                <tr class="jiange">
                    <td colspan="8"></td>
                </tr>
                </thead>

                <tbody>
                <tr class="order-tb-head" id="${order->order_sn!}">
                    <td <#if ($order->fanli_type > 0) colspan="9" <#else> colspan="8" </#if>>
                        <span class="span1">
                            <span style="color: #999;">订单号：</span>${order->order_sn!}
                        </span>
                        <span class="span2">
                            <#if  ($order->goods_type == 4)
                                支付方式：积分兑换
                            <#else>
                                支付方式：${order->pay_name!}
                            </#if>
                        </span>
                        <span class="span3">
                            配送方式：${order->deliver_type == 0 ? "快递" : "自提"!}
                        </span>
                        <#if ($order->part_ship_flag == 1)
                            <span class="span3">发货类型： 部分发货</span>
                        </#if>
                        <#if ($order->deliver_type == 1)
                            {{--<span class="span3">核销码：${order->verify_code!}</span>--!}
                        </#if>
                        <span class="fr" order_sn="${order->order_sn!}">
                               {{--<span class="text-overflow seller-remark"--!}
                            {{--title="${order->seller_remark!}">${order->seller_remark!}</span>--!}
                            <span class="btn-star-flag ${order->star_flag ? "":"empty-flag"!}"
                                  title="切换星标"></span>
                            <a href="javascript:void(0);" class="add_text" order_sn="${order->order_sn!}"
                               seller_remark="${order->seller_remark!}">添加备注</a>
                            {{--<a href="/system/order/info?order_sn=${order->order_sn!}" target="_blank">查看详情</a>--!}
                            <#if ($order->pos_flag != 1 && ($order->operation->can_refund || $order->operation->can_return))
                                <a class="text-link" target="_blank"
                                   href="/system/order/manual/return?shop_id=${order->shop_id!}&order_sn=${order->order_sn!}">手动退款退货</a>
                            </#if>
                            <a href="/system/good/comment?shop_id=${order->shop_id!}&order_sn=${order->order_sn!}">查看评价</a>
                        </span>
                    </td>
                </tr>
                <#list ($order->order_goods as $i => $order_good)
                    <tr class="order-tb-body">
                        <td>
                            <div class="order-goods-info clearfix">
                                <div class="fl">
                                    <img src="${order_good->goods_img!}!small"/>
                                </div>
                                <div class="fr">
                                    <p>
                                        ${order_good->goods_name!}
                                    </p>
                                    <div title="${order_good->goods_attr!}">${order_good->goods_attr!}</div>
                                </div>
                            </div>
                        </td>
                        <td width="100px">${order_good->goods_sn!}</td>
                        <td width="100px">{{ number_format($order_good->goods_price,2)!}</td>
                        <td width="100px">${order_good->goods_number!}</td>
                        <#if ($i == 0)
                            <td rowspan="{{ count($order->order_goods)!}">
                                <p>${order->consignee!}</p>
                                <span>${order->mobile!}</span>
                            </td>
                            <td rowspan="{{ count($order->order_goods)!}" width="110px">${order->add_time!}</td>
                            <td class="order-status" rowspan="{{ count($order->order_goods)!}"
                                order_sn="${order->order_sn!}"
                                order_status="${order->order_status!}"
                                refund_money="${order->max_refund_money!}"
                                verify_code="${order->verify_code!}">

                                <div class="text-center">
                                    <#if ($order->order_status !=3 && $order->order_status != 5)
                                        ${order->order_status_name!}
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
                                        <#if ($order->part_ship_flag && $order->order_status==3)
                                        </br>(部分发货)
                                    </#if>
                                </div>

                                <#if ($order->operation->can_deliver && !$order->is_return)
                                    <button type="button" class="btn-common btn-deliver">发货</button>
                                </#if>


                                <#if (($order->refund_status > 0) )
                                    <#if (in_array($order->refund_status,[1,2,4]))
                                        <a class="text-link" target="_blank"
                                           href="/system/order/return/list?order_sn=${order->order_sn!}&shop_id=${order->shop_id!}">退款退货审批</a>
                                    <#else>
                                        <a class="text-link" target="_blank"
                                           href="/system/order/return/list?order_sn=${order->order_sn!}&shop_id=${order->shop_id!}">查看退款</a>
                                    </#if>
                                </#if>

                                <#if ($order->operation->can_verify)
                                    <button type="button" class="btn-common btn-verify">核销</button>
                                </#if>
                                <#if ($order->operation->can_close)
                                    <button type="button" class="btn-common btn-close">关闭</button>
                                </#if>
                                <#if ($order->operation->can_finish)
                                    <button type="button" class="btn-common btn-finish">完成</button>
                                </#if>

                            </td>
                            <#if ($order->fanli_type > 0)
                                <td width="80px" rowspan="{{ count($order->order_goods)!}">
                                    ${order->fanli_money!}
                                </td>
                            </#if>
                            <td width="80px" rowspan="{{ count($order->order_goods)!}">
                                <p>
                                    <span class="text-warning">￥{{ number_format($order->money_paid,2)!}</span>
                                </p>
                                <#if ($order->deliver_type == 0)
                                    <p>
                                        (含快递：<span
                                                class="text-warning">￥{{ number_format($order->shipping_fee,2)!}</span>)
                                    </p>
                                </#if>
                            </td>
                        </#if>
                    </tr>
                </#list>
                </tbody>


                <tr>
                    <td <#if ($order->fanli_type > 0) colspan="9" <#else> colspan="8" </#if> style="text-align: right">
                        <p>
                            商品金额：<span class="text-amount">￥{{ number_format($order->order_amount,2)!}</span>
                        </p>
                        <#if ($order->deliver_type==0)
                            <p>
                                运费：<span class="text-amount">+￥{{ number_format($order->shipping_fee,2)!}</span>
                            </p>
                        </#if>
                        <#if ($order->package_discount >0)
                            <p>
                                一口价优惠：<span
                                        class="text-amount">-￥{{ number_format($order->package_discount,2)!}</span>
                            </p>
                        </#if>
                        <#if ($order->member_card_reduce >0)
                            <p>
                                会员卡优惠：<span
                                        class="text-amount">-￥{{ number_format($order->member_card_reduce,2)!}</span>
                            </p>
                        </#if>
                        <#if ($order->promotion_reduce >0)
                            <p>
                                满折满减优惠：<span
                                        class="text-amount">-￥{{ number_format($order->promotion_reduce,2)!}</span>
                            </p>
                        </#if>
                        <#if ($order->discount >0)
                            <p>
                                优惠券优惠：<span class="text-amount">-￥{{ number_format($order->discount,2)!}</span>
                            </p>
                        </#if>
                        <#if ($order->use_account >0)
                            <p>
                                余额消费金额：<span class="text-amount">-￥{{ number_format($order->use_account,2)!}</span>
                            </p>
                        </#if>
                        <#if ($order->score_discount >0)
                            <#if  ($order->goods_type == 4)
                                <p>
                                    积分兑换：<span
                                            class="text-amount">${order->score_discount * 100!}积分</span>
                                </p>
                            <#else>
                                <p>
                                    积分抵扣：<span
                                            class="text-amount">-￥{{ number_format($order->score_discount,2)!}</span>
                                </p>
                            </#if>
                        </#if>

                        <#if ($order->discount_price >0)
                            <p>
                                会员等级优惠：<span
                                        class="text-amount">-￥{{ number_format($order->discount_price,2)!}</span>
                            </p>
                        </#if>
                        <#if ($order->dapei_reduce_amount >0)
                            <p>
                                搭配立减优惠：<span
                                        class="text-amount">-￥{{ number_format($order->dapei_reduce_amount,2)!}</span>
                            </p>
                        </#if>

                        <#if ($order->member_card_balance >0)
                            <p>
                                会员卡消费金额：<span
                                        class="text-amount">-￥{{ number_format($order->member_card_balance,2)!}</span>
                            </p>
                        </#if>

                        <p>
                            实收款：<span
                                    class="text-amount amount-sum">￥{{ number_format($order->money_paid,2)!}</span>
                        </p>


                    </td>
                </tr>


                <tbody style="border: none;">
                <tr>
                    <td colspan="8"></td>
                </tr>
                </tbody>
            </table>
            <#if ($order->fanli_type == 1)
                <div>
                    <p style="color: #333;margin-bottom: 10px">返利详情</p>
                    <table width="100%" class="fanli_tables">
                        <tr>
                            <th width="9%">分销员手机号</th>
                            <th width="9%">分销员微信昵称</th>
                            <th width="10%">商品名称</th>
                            <th width="9%">返利商品总金额</th>
                            <th width="9%">成本价</th>
                            <th width="9%">商品可返利金额</th>
                            <th width="8%">返利关系</th>
                            <th width="8%">返利比例</th>
                            <th width="11%">
                                <div class="tishikuang" style="position: relative">
                                    返利佣金金额
                                    <img src="http://${image_domain!}/image/admin/system_icon.png" alt="" class="btn_tishi" style="margin-top: -3px">
                                    <div class="tishi_content">
                                        <div>未开启成本价保护时</div>
                                        <div>返利佣金金额=返利比例*返利商品总金额</div>
                                        <div>开启成本价保护时</div>
                                        <div>返利佣金金额=(返利商品总金额-成本价)*实际返利比例</div>
                                        <div>实际返利比例=A分销员返利比例/(A分销员返利比例+B分销员返利比例+……)</div>
                                    </div>
                                </div>
                            </th>
                            <th width="9%">返利状态</th>
                            <th width="9%">返利日期</th>
                        </tr>
                        <#list ($order->order_goods as $i => $order_good)
                            <tr>
                                <#if ($loop->first)
                                    <td rowspan="{{count($order->order_goods)!}">${fanli[0]->fanli_mobile!}</td>
                                    <td rowspan="{{count($order->order_goods)!}">${fanli[0]->fanli_username!}</td>
                                </#if>
                                <td>${order_good->goods_name!}</td>
                                <td><#if ($fanli[0]->settlement_flag == 0 || $fanli[0]->settlement_flag == 1){{number_format(floatval($order_good->can_calculate_money * ($order_good->goods_number - $order_good->return_number)),2)!} <#else> 0.00 </#if></td>
                                <td>{{number_format(floatval($order_good->cost_price),2)!}</td>
                                <td>{{number_format(floatval(($order_good->can_calculate_money * ($order_good->goods_number - $order_good->return_number) - $order_good->cost_price) > 0 ? ($order_good->can_calculate_money * ($order_good->goods_number - $order_good->return_number) - $order_good->cost_price) : 0),2)!}</td>
                                <td>直接邀请<!--间接邀请 || 自购返利--></td>
                                <td>${order_good->fanli_percent!}%</td>
                                <td>
                                    <#if ($order_good->return_number == 0){{number_format($order_good->total_fanli_money,2)!}
                                    <#else>{{number_format(floatval($order_good->fanli_money * ($order_good->goods_number - $order_good->return_number)),2)!}
                                    </#if>
                                </td>
                                <#if ($loop->first)
                                    <td rowspan="{{count($order->order_goods) + count($rebate_list)!}"><#if ($fanli[0]->settlement_flag == 0)待返利 <#elseif>($fanli[0]->settlement_flag == 1) 已返利 <#elseif>($fanli[0]->settlement_flag == 2)不返利 </#if></td>
                                    <td rowspan="{{count($order->order_goods) + count($rebate_list)!}">${fanli[0]->finished_time!}</td>
                                </#if>
                            </tr>
                        </#list>
                        {{--//自购或二级返利--!}
                        <#if (count($rebate_list))
                            <#list ($rebate_list as $i => $rebate)
                                <tr>
                                    <#if ($loop->first)
                                        <td rowspan="{{count($rebate_list)!}">${rebate->mobile!}</td>
                                        <td rowspan="{{count($rebate_list)!}">${rebate->username!}</td>
                                    </#if>
                                    <td>${rebate->goods_name!}</td>
                                    <td>{{number_format(floatval($rebate->can_rebate_money),2)!}</td>
                                    <td>{{number_format(floatval($rebate->cost_price),2)!}</td>
                                    <td>{{number_format(floatval(($rebate->can_rebate_money - $rebate->cost_price) > 0 ? ($rebate->can_rebate_money - $rebate->cost_price) : 0),2)!}</td>
                                    <td>${rebate->rebate_level == 2 ? '间接邀请' : '自购返利'!}</td>
                                    <td>{{number_format($rebate->rebate_percent * 100,2)!}%</td>
                                    <td>{{number_format(floatval($rebate->total_rebate_money),2)!}</td>
                                </tr>
                            </#list>
                        </#if>
                        {{--<#list ($fanli as $item)--!}
                        {{--<tr>--!}
                        {{--<td>${item->fanli_mobile!}</td>--!}
                        {{--<td>${item->fanli_username!}</td>--!}
                        {{--<td width="10%"><a href="/system/order/list?top_index=3" style="color: #5a8bff;">${item->order_sn!}</a></td>--!}
                        {{--<td width="10%">${item->mobile!}</td>--!}
                        {{--<td width="10%">${item->username!}</td>--!}
                        {{--<td><#if ($item->settlement_flag == 0 || $item->settlement_flag == 1){{number_format(floatval($item->fanli_goods_money),2)!} <#else> 0.00 </#if></td>--!}
                        {{--<td>${order->fanli_percent * 100!}%</td>--!}
                        {{--<td>{{number_format($item->fanli_money,2)!}</td>--!}
                        {{--                            <td width="10%">{{number_format(floatval($item->fanli_money) / floatval($item->fanli_percent),2)!}</td>--!}
                        {{--<td><#if ($item->settlement_flag == 0)待返利 <#elseif>($item->settlement_flag == 1) 已返利 <#elseif>($item->settlement_flag == 2)不返利 </#if></td>--!}
                        {{--<td>${item->finished_time!}</td>--!}
                        {{--</tr>--!}
                        {{--</#list>--!}
                    </table>
                </div>
            </#if>
        </div>
    </form>
</div>

<div id="set-text">
    <textarea name="seller_remark" id="seller_remark"></textarea>
</div>

<div id="shipping-list" style="display: none">
    <table class="table">
        <tr>
            <td>
                快递列表：
            </td>
            <td>
                <select name="template_shipping_id" id="template_shipping_id">
                    <#list ($shipping_list as $item)
                        <option value="${item->shipping_id!}">${item->shipping_name!}</option>
                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                快递单号：
            </td>
            <td>
                <input type="text" name="template_shipping_no" id="template_shipping_no" value="" maxlength="120"
                       size="18" placeholder="请输入快递单号"/>
            </td>
        </tr>
    </table>
</div>

<div id="return-info" style="display: none">
    <table class="table">
        <tr>
            <td>
                退款/退货类型：
            </td>
            <td>
                <select name="template_return_type" id="template_return_type">
                    <option value="1">退款且退货</option>
                    <option value="0">仅退款</option>
                </select>
            </td>
        </tr>

        <tr>
            <td>
                退款/退货金额：
            </td>
            <td>
                <input type="text" name="template_refund_money" id="template_refund_money" value="" maxlength="20"
                       size="18" placeholder="请输入退款金额" value=""/>
                <span class="text-warning">退款/退货金额不包含运费</span>
            </td>
        </tr>

        <tr>
            <td>
                退款/退货原因：
            </td>
            <td>
                <input type="text" name="template_reason" id="template_reason" value="" maxlength="30"
                       size="30" placeholder=" 退款/退货原因" value=""/>
            </td>
        </tr>
    </table>
</div>


<div id="expr_tpl_id" class="hide">
    <table>
        <tr class="expr_tpl_li">
            <th class="text-info rec_time"></th>
            <td class="text-info rec_info"></td>
        </tr>
    </table>
</div>


<script src="/js/admin/order_list.js?v=1.0.1" type="text/javascript"></script>
<#include ("system.footer")
<script type="text/javascript">
    $(".tishikuang").hover(function(){
        $('.tishi_content').css("display","block")
    },function () {
        $('.tishi_content').css("display","none")
    })
</script>