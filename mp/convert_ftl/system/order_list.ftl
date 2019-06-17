<#include ("system.header")
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.27" type="text/css"/>
<link rel="stylesheet" href="/css/system/order_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/shop_pv.css" type="text/css" />
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />
<style type="text/css">
    .btn-choose:hover, .btn-verify:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-choose:focus,.btn-verify:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-excel:hover{
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none
    }
    .btn-excel:focus{
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none
    }
    .btn-deliver:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-deliver:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .order-status .btn-close:hover{
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none
    }
    .order-status .btn-close:focus{
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none
    }
    .btn-finish:hover,.btn-finish:focus {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }
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
    .kuaidi{
        padding:8px 0;
    }
    .kuaidi span{
        display: inline-block;
        width: 85px;
        line-height: 30px;
        text-align: right;
        margin-right: 25px;
        color: #333;
    }
    .btn_zu{
        float: right;
        width: 33%;
    }
    .mendian{
        float: left;
        width: 63%;
    }
    .tb_paging input {
        height: 30px;
        border: 1px solid #dedede;
        text-align: center;
        margin: 0 8px;
    }
</style>
<div class="title">
    <span>订单管理 / </span><span style="color: #666;"><#if ($request['user_id']) ${request['username']!}的订单列表 <#else> ${title!}</#if></span>
</div>

<#if ($message)
    <div class="message-tip alert alert-warning margin-top-10 margin-bottom-0">
        ${message!}
    </div>
</#if>
<form name="formData" <#if ($request['user_id']) action="/system/order/list?user_id=${request['user_id']!}" <#else> action="/system/order/list" </#if> method="post" id="form1">
    <div class="order-container">
        <div class="order-info">
            {{ csrf_field()!}
            <input type="hidden" name="act" id="act">
            <input type="hidden" name="act_order_sn" id="act_order_sn">
            <input type="hidden" name="shipping_id" id="shipping_id">
            <input type="hidden" name="shipping_no" id="shipping_no">
            <input type="hidden" name="product_ids" id="product_ids">
            <input type="hidden" name="verify_code" id="verify_code">
            {{--<input type="hidden" name="star_flag" id="star_flag" value="${request['star_flag']!}">--!}
            <input type="hidden" name="act_star_flag" id="act_star_flag">
            {{--<input type="hidden" name="return_flag" id="return_flag" value="${request['return_flag']!}">
            <input type="hidden" name="order_status_d" id="order_status_d" value="${request['order_status_d']!}">--!}

            <input type="hidden" name="refund_money" id="refund_money">
            <input type="hidden" name="reason" id="reason">
            <input type="hidden" name="return_type" id="return_type">
            <input type="hidden" name="pin_group_id" value="<#if  ($request['pin_group_fail'] == 1 || $query['goods_type'] == 1)${request['pin_group_id']!} </#if>">
            <input type="hidden" name="pin_group_fail" value="${request['pin_group_fail']!}">
            <ul class="clearfix">
                <li class="order-info-li clearfix">
                    <div class="fl">
                        <span>商品名称</span>
                        <input type="text" name="goods_name" placeholder="商品名称" value="${request['goods_name']!}"/>
                    </div>
                    <div class="fl">
                        <span>订单号</span>
                        <input type="text" name="order_sn" placeholder="订单号" value="${request['order_sn']!}"/>
                    </div>
                    <div class="fl">
                        <span>订单状态</span>
                        <select name="order_status" id="order_status">
                            <option value="-1">全部订单</option>
                            <#list ($order_status_map as $orderStatus => $orderStatusName)
                                <#if ($request['deliver_type'] == 1)
                                <option value="${orderStatus!}"
                                        <#if (($query['order_status'] ?? -1) == $orderStatus) selected </#if> <#if ($orderStatus==4) hidden </#if>>
                                    <#if ($orderStatus == 3)
                                        待核销
                                    <#elseif>($orderStatus == 5)
                                        已自提
                                    <#else>
                                        ${orderStatusName!}
                                    </#if>
                                </option>
                                <#elseif>($query['order_status'] == 3)
                                    <option value="${orderStatus!}"
                                            <#if (($query['order_status'] ?? -1) == $orderStatus) selected </#if>>
                                        <#if ($orderStatus == 3)
                                            待发货
                                        <#elseif>($orderStatus == 5)
                                            待收货
                                        <#else>
                                        ${orderStatusName!}
                                        </#if>
                                    </option>
                                <#else>
                                    <option value="${orderStatus!}"
                                            <#if (($query['order_status'] ?? -1) == $orderStatus) selected </#if>>
                                        ${orderStatusName!}
                                    </option>
                                    </#if>
                            </#list>
                        </select>
                    </div>
                </li>
                <li class="order-info-li clearfix">
                    <div class="fl">
                        <span>下单时间</span>
                        <input type="text" name="add_time" placeholder="下单时间" value="${request['add_time']!}" onclick="picker();" autocomplete="off" style="width: 85px;padding-left: 5px;"/>至
                        <input type="text" name="add_time_end" placeholder="下单时间" value="${request['add_time_end']!}" onclick="picker();" autocomplete="off" style="width: 85px;padding-left: 5px;"/>
                    </div>
                    <div class="fl">
                        <span>完成时间</span>
                        <input type="text" name="finished_time" placeholder="完成时间" value="${request['finished_time']!}" onclick="picker();" autocomplete="off" style="width: 85px;padding-left: 5px;"/>至
                        <input type="text" name="finished_time_end" placeholder="完成时间" value="${request['finished_time_end']!}" onclick="picker();" autocomplete="off" style="width: 85px;padding-left: 5px;"/>
                    </div>
                    <div class="fl">
                        <span><#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if></span>
                        <input type="text" name="consignee" placeholder="<#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if>" style="width: 155px;" value="${request['consignee']!}"/>
                    </div>
                </li>
                <li class="order-info-li clearfix">
                    <div class="fl">
                        <span><#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if></span>
                        <input type="text" name="mobile" placeholder="<#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if>" value="${request['mobile']!}"/>
                    </div>
                    <div class="fl">
                        <span>会员昵称</span>
                        <input type="text" name="username" placeholder="会员昵称" value="${request['username']!}"/>
                    </div>
                    <div class="fl" style="width: auto;">
                        <span>配送方式</span>
                        <select name="deliver_type" id="deliver_type">
                            <option value="-1">全部</option>
                            <option value="0" <#if (($request['deliver_type'] ?? -1)  == 0) selected </#if>>快递</option>
                            <option value="1" <#if (($request['deliver_type'] ?? -1)  == 1) selected </#if>>自提</option>
                        </select>
                    </div>
                </li>
                <li class="order-info-li clearfix">
                    <div class="fl">
                        <span>店铺ID</span>
                        <input type="text" name="shop_id" placeholder="店铺ID" value="${request['shop_id']!}"/>
                    </div>
                    <div class="fl">
                        <span>核销码</span>
                        <input type="text" name="verify_code" placeholder="核销码" value="${request['verify_code']!}"/>
                    </div>
                    <div class="fl" style="width: auto;">
                        <span>订单类型</span>
                        <select name="goods_type" style="width: 175px;">
                            <option value="-1">全部</option>
                            <#list  ($goodsType as $key => $item)
                                <option value="${key!}"
                                        <#if (isset($request['goods_type']) && $request['goods_type'] == 0 && $key == 0)
                                        selected
                                        <#elseif> ($key == 1 && ($request['goods_type'] == 1 || $request['pin_group_fail'] == 1))
                                        selected
                                        <#elseif> ($key > 1 && $request['goods_type'] == $key)
                                        selected
                                        </#if>
                                > ${item!}
                                </option>
                            </#list>
                            <option value="99" <#if  ($request['goods_type'] == 99) selected </#if>>代付订单</option>
                            <option value="100" <#if  ($request['goods_type'] == 100) selected </#if>>扫码购订单</option>
                        </select>
                    </div>
                </li>

                <div class="fl receive_address kuaidi" style="display: none;">
                    <span style="text-align:left; margin-right:0px">收货地址</span>
                    <select name="province_code" id="province_code">
                        <option value="">请选择省</option>
                        <#list ($provinceList as $province)
                            <option value="${province->province_id!}"
                                    <#if ($request['province_code']  == $province->province_id) selected </#if>>
                                ${province->name!}
                            </option>
                        </#list>
                    </select>
                    <select name="city_code" id="city_code">
                        <option value="">请选择市</option>
                        <#list ($cityList as $city)
                            <option value="${city->city_id!}"
                                    <#if ($request['city_code']  == $city->city_id) selected </#if>>
                                ${city->name!}
                            </option>
                        </#list>
                    </select>
                    <select name="district_code" id="district_code">
                        <option value="">请选择区县</option>
                        <#list ($districtList as $district)
                            <option value="${district->district_id!}"
                                    <#if ($request['district_code']  == $district->district_id) selected </#if>>
                                ${district->name!}
                            </option>
                        </#list>
                    </select>
                </div>
                <li class="order-info-li clearfix btn_zu" style="width:50%">
                    <button type="button" class="btn-choose">筛选</button>
                    <button type="button" class="btn-excel" style="margin-left: 12px;">导出表格</button>
                </li>

            </ul>


        </div>
        <div class="order-list">
            <#if  ($request['pin_group_fail'] != 1)
            <ul class="order-list-nav clearfix">
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag']) && ($request['order_status'] ?? -1) == -1 && empty($request["order_status_d"]))
                    class="nav-active" </#if>>
                    <a id="all" <#if ($request['deliver_type'] == 1) href="/system/order/list?deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0) href="/system/order/list?deliver_type=0"
                        <#else> href="/system/order/list" </#if> >全部</a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 0)
                    class="nav-active" </#if>>
                    <a id="wait_pay" <#if ($request['deliver_type']== 1)  href="/system/order/list?order_status=0&deliver_type=1"
                       <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                       href="/system/order/list?order_status=0&deliver_type=0"
                            <#else> href="/system/order/list?order_status=0" </#if>>待付款</a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 3 || $request["order_status_d"]==3)
                    class="nav-active" </#if>>
                    <#if ($request['deliver_type'] == 1)
                    <a id="wait_ti" href="/system/order/list?order_status=3&deliver_type=1">
                        <span class="wait_re">待提货 <span class="wait_num">${amount['wait_take_deliver']!}</span></span>
                    </a>
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    <a id="wait_fa" href="/system/order/list?order_status=3&deliver_type=0">
                        <span class="wait_re">待发货 <span class="wait_num">${amount['wait_delivery']!}</span></span>
                    </a>
                    <#else>
                    <a id="wait_t_f" href="/system/order/list?order_status_d=3">
                        <span class="wait_re">待发货 <span class="wait_num">${amount['wait_delivery']!}</span></span> /<span class="wait_re">待提货 <span class="wait_num">${amount['wait_take_deliver']!}</span></span>
                    </a>
                    </#if>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 4)
                    class="nav-active" </#if>  <#if ($request['deliver_type']== 1) hidden </#if>>
                    <a  id="fa" <#if ($request['deliver_type']== 1) href="/system/order/list?order_status=4&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/system/order/list?order_status=4&deliver_type=0"
                            <#else> href="/system/order/list?order_status=4"</#if>>已发货</a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 5 || $request["order_status_d"]==5)
                    class="nav-active" </#if>>
                    <#if ($request['deliver_type'] == 1)
                        <a id="ti" href="/system/order/list?order_status=5&deliver_type=1">已提货</a>
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                        <a id="shou" <#if ($request['deliver_type']== 1) href="/system/order/list?order_status=5&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/system/order/list?order_status=5&deliver_type=0" </#if>>已收货</a>
                    <#else>
                    <a id="" href="/system/order/list?order_status_d=5">已收货/已提货</a>
                    </#if>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 6)
                    class="nav-active" </#if>>
                    <a id="finish" <#if ($request['deliver_type']== 1) href="/system/order/list?order_status=6&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/system/order/list?order_status=6&deliver_type=0"
                        <#else> href="/system/order/list?order_status=6" </#if>>已完成</a>
                </li>
                <li <#if (($request['return_flag'] ?? 0) == 1) class="nav-active" </#if>>
                    <a id="return" <#if ($request['deliver_type']== 1) href="/system/order/list?return_flag=1&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/system/order/list?return_flag=1&deliver_type=0"
                       <#else> href="/system/order/list?return_flag=1" </#if>>
                        <span class="wait_re">退货/退款中 <span class="wait_num">${amount['refunding']!}</span></span>
                    </a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 2)
                    class="nav-active" </#if>>
                    <a id="close" <#if ($request['deliver_type']== 1) href="/system/order/list?order_status=2&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/system/order/list?order_status=2&deliver_type=0"
                        <#else> href="/system/order/list?order_status=2" </#if>>已关闭</a>
                </li>
                <li <#if (($request['star_flag'] ?? 0) == 1) class="nav-active" </#if>>
                    <a id="star" <#if ($request['deliver_type']== 1) href="/system/order/list?star_flag=1&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/system/order/list?star_flag=1&deliver_type=0"
                        <#else> href="/system/order/list?star_flag=1" </#if>>追星订单</a>
                </li>
            </ul>
            </#if>
            <div class="order-list-table">
                <table width="100%" style="font-size: inherit;">
                    <thead>
                    <tr>
                        <td width="270px">商品</td>
                        <td width="100px">货号</td>
                        <td width="100px">单价</td>
                        <td width="100px">数量</td>
                        <td>收货人信息</td>
                        <td>下单时间</td>
                        <td>订单状态</td>
                        <td>支付金额</td>
                    </tr>
                    <tr class="jiange">
                        <td colspan="8"></td>
                    </tr>
                    </thead>

                    <#list ($data_list as $order)
                        <tbody>
                        <tr class="order-tb-head" id="${order->order_sn!}">
                            <td colspan="8">
                                <span class="span1">
                                    <span style="color: #999;">订单号：</span>${order->order_sn!}
                                </span>
                                        <span class="span2">
                                    支付方式：${order->pay_name!}
                                </span>

                                        <span class="span3">
                                    配送方式：${order->deliver_type == 0 ? "快递" : "自提"!}
                                </span>
                                <#if  ($order->goods_type == 1)
                                    <span class="span3"> 订单类型：拼团订单</span>
                                <#elseif> ($order->fanli_type > 0)
                                    <span class="span3"> 订单类型：返利订单</span>
                                    <#if ($order->fanli_money>0 && $order->settlement_flag == 0)
                                        <img src="http://${image_domain!}/image/admin/no_fanli.png" alt="" title="未返利" style="cursor: pointer">
                                    <#elseif>($order->fanli_money>0 && $order->settlement_flag == 1)
                                        <img src="http://${image_domain!}/image/admin/fanli.png" alt="" title="已返利" style="cursor: pointer">
                                    <#elseif>($order->settlement_flag == 2)
                                        <img src="http://${image_domain!}/image/admin/return_fanli.png" alt="" title="不返利" style="cursor: pointer">
                                    </#if>
                                </#if>
                                <#if ($order->deliver_type !=0)
                                <span class="span3" >核销码：${order->verify_code!}</span>
                                </#if>
                                <span class="fr" order_sn="${order->order_sn!}">
                                       <span class="text-overflow seller-remark"
                                             title="${order->seller_remark!}">${order->seller_remark!}</span>
                                       <span class="btn-star-flag ${order->star_flag ? "":"empty-flag"!}"
                                             title="切换星标"></span>
                                    {{--<a href="javascript:void(0);" class="add_text" order_sn="${order->order_sn!}" seller_remark="${order->seller_remark!}">添加备注</a>--!}
                                    <a href="/system/order/info?shop_id=${order->shop_id!}&order_sn=${order->order_sn!}" target="_blank">查看详情</a>
                                    <a href="/system/good/comment?shop_id=${order->shop_id!}&order_sn=${order->order_sn!}" target="_blank">查看评价</a>
                                </span>
                            </td>
                        </tr>
                        <#list ($order->order_goods as $i => $order_good)
                            <tr class="order-tb-body">
                                <td>
                                    <div class="order-goods-info clearfix">
                                        <div class="fl">
                                            <img src="${order_good->goods_img!}"/>
                                        </div>
                                        <div class="fr">
                                            <p>
                                                ${order_good->goods_name!}
                                            </p>
                                            <div>${order_good->goods_attr!}</div>
                                        </div>
                                    </div>


                                </td>
                                <td width="100px">${order_good->goods_sn!}

                                    <p>
                                        <a href="/system/order/list?shop_id=${order->shop_id!}" target="_blank">${order_good->shop_id!}</a>

                                    </p>
                                </td>
                                <td width="100px">${order_good->goods_price!}</td>
                                <td width="100px">${order_good->goods_number!}</td>
                                <#if ($i == 0)
                                    <td rowspan="{{ count($order->order_goods)!}">
                                        <a href="/system/user/list?shop_id=${order->shop_id!}&id=${order->user_id!}" target="_blank" style="color: #5a8bff">
                                            <p>${order->consignee!}</p>
                                            <span>${order->mobile!}</span>
                                        </a>
                                    </td>
                                    <td rowspan="{{ count($order->order_goods)!}">${order->add_time!}</td>
                                    <td width="180px" class="order-status" rowspan="{{ count($order->order_goods)!}"
                                        order_sn="${order->order_sn!}"
                                        order_status="${order->order_status!}"
                                        refund_money="${order->max_refund_money!}"
                                        verify_code="${order->verify_code!}">

                                        <div class="text-center padding-10">
                                            <#if ($order->order_status !=3 && $order->order_status != 5)
                                                ${order->order_status_name!}
                                            </#if>
                                            <#if ($order->part_ship_flag && $order->order_status==3)
                                                (部分发货)
                                            </#if>
                                            <#if ($order->deliver_type==1 && $order->order_status==3)
                                                待核销
                                            </#if>
                                            <#if ($order->deliver_type==0 && $order->order_status==3 && $request['pin_group_fail'] != 1)
                                                待发货
                                            </#if>
                                            <#if ($order->deliver_type==1 && $order->order_status==5)
                                                已自提
                                            </#if>
                                            <#if ($order->deliver_type==0 && $order->order_status==5)
                                                已收货
                                            </#if>
                                        </div>

                                        <#if ($order->operation->can_deliver && $request['pin_group_fail'] != 1)
                                            <button type="button" class="btn-common btn-deliver">发货</button>
                                        </#if>

                                        <#if (($order->refund_status > 0) )
                                            <#if (in_array($order->refund_status,[1,2,4]))
                                                <a class="text-link" href="/system/order/return/list?order_sn=${order->order_sn!}&shop_id=${order->shop_id!}" target="_blank">退款退货审批</a>
                                            <#else>
                                                <a class="text-link" href="/system/order/return/list?order_sn=${order->order_sn!}&shop_id=${order->shop_id!}" target="_blank">查看退款</a>
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
                                    <td width="150px" rowspan="{{ count($order->order_goods)!}">
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

                        <tbody style="border: none;">
                        <tr>
                            <td colspan="8"></td>
                        </tr>
                        </tbody>
                    </#list>
                </table>
            </div>
            <div class="clearfix">
                <table width="100%" border="0" class="tb_paging" style="font-size: inherit;">
                    <tr>
                        <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                            <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                            <a href="#"
                               onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                            <a href="#"
                               onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                            <a href="#"
                               onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                            <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                   size="5"
                                   onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                            <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>

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
                    {{--<#list ($shipping_list as $item)--!}
                        {{--<option value="${item->shipping_id!}">${item->shipping_name!}</option>--!}
                    {{--</#list>--!}
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

<script type="text/javascript">
    var anchor = "${anchor!}";

    //分页
    function gopage(page) {
        var href = $(".order-list-nav li.nav-active a").attr('href');
        $("#act").val("");
        $("#page").val(page);
        $('#form1').attr('action', href);
        $("#form1").submit();
    }

    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}'            //总页码数

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    $(".order-list-nav a").click(function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var tabStr = href.indexOf('?') > -1 ? href.split('?')[1].split('=')[0] : '';

        //tab切换时不保留上次的 tab提交条件
        function removeEle(callback) {
            var ele = ['return_flag', 'star_flag', 'order_status_d'];   //tab清除定义
            $.each(ele, function (index, item) {
                $('#' +item).removeAttrs('value');
            });
            if($.inArray(tabStr, ['return_flag', 'star_flag']) == -1) {
                //这两个tab字段需要保留搜索框的订单状态
                $('#order_status').val('-1');
            }
            callback();
        }
        function doCallback() {
            $("#page").val(1);
            $('#form1').attr('action', href);
            $('#form1').submit();
        }
        removeEle(doCallback);
    });
</script>

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/region.js?v=1.0.0" type="text/javascript"></script>
<script type="text/javascript">
    region_util.init_ev();
</script>
<script src="/js/admin/order_list.js?v=1.1.0" type="text/javascript"></script>

<#include ("system.footer")