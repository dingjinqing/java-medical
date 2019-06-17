<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.4" type="text/css"/>
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
    .change_goods {
        border: 1px red solid;
        padding: 0px 3px;
        color: red;
        border-radius: 2px;
        font-size: 12px;
        display: inline-block;
    }
    .expand-view-more, .expand-collapse{
        display: none;
        cursor: pointer;
        color: #999;
        font-size: 12px;
    }
    .expand-collapse{
        border-right: 1px solid #eee;
    }
    .expand-view-more img, .expand-collapse img{
        margin-top: -2px;
    }
    .order-tb-more{
        background: #f5f5f5;
    }
    .order-tb-more td{
        text-align: left;
        padding-top: 0;
    }
    .order-tb-more span{
        display: inline-block;
        color: #666666;
    }
    .order-goods-info .fr{
        margin-left: 0px !important;
    }
    .order-goods-info .fl{
        width: 60px !important;
        height: 60px !important;
    }
    .pay_way{
        color: #ff6666;
        padding: 2px;
        font-size: 10px;
        margin-left: 4px;
    }
    .tips{
        position: absolute;
        background: url(/image/admin/decorate_tips.png);
        background-size: 76px 27px;
        width: 75px;
        height: 27px;
        padding: 0;
        color: #fff;
        font-size: 5px;
        line-height: 25px;
        text-align: center;
        top: -28px;
        left: -30px;
    }
</style>
<div class="title">
    <#if ($top_index == 4)
        <span><a href="/admin/market/view?top_index=4" >营销管理</a> / </span>
        <#else>
        <span>订单管理 / </span>
    </#if>
    <span style="color: #666;"><#if ($request['user_id']) ${request['username']!}的订单列表 <#else> ${title!}</#if></span>
</div>

<#if ($message)
    <div class="message-tip alert alert-warning margin-top-10 margin-bottom-0">
        ${message!}
    </div>
</#if>
<form name="formData" <#if ($request['user_id']) action="/admin/orders/manage/list?user_id=${request['user_id']!}&top_index=${top_index!}&sub_index=${sub_index!}" <#else> action="/admin/orders/manage/list?top_index=3" </#if> method="post" id="form1">
    <div class="order-container">
        <div class="order-info">
            {{ csrf_field()!}
            <input type="hidden" name="act" id="act">
            <input type="hidden" name="act_order_sn" id="act_order_sn">
            <input type="hidden" name="shipping_id" id="shipping_id">
            <input type="hidden" name="shipping_no" id="shipping_no">
            <input type="hidden" name="product_ids" id="product_ids">
            <input type="hidden" name="rec_ids" id="rec_ids"/>
            <input type="hidden" name="send_numbers" id="send_numbers">
            <input type="hidden" name="verify_code" id="verify_code">
            {{--<input type="hidden" name="star_flag" id="star_flag" value="${request['star_flag']!}">--!}
            <input type="hidden" name="act_star_flag" id="act_star_flag">
            {{--<input type="hidden" name="return_flag" id="return_flag" value="${request['return_flag']!}">--!}
            <input type="hidden" name="order_status_d" id="order_status_d" value="${request['order_status_d']!}">

            <input type="hidden" name="refund_money" id="refund_money">
            <input type="hidden" name="reason" id="reason">
            <input type="hidden" name="return_type" id="return_type">
            <input type="hidden" name="pin_group_id" value="<#if  ($request['pin_group_fail'] == 1 || $query['goods_type'] == 1)${request['pin_group_id']!} </#if>">
            <input type="hidden" name="pin_group_fail" value="${request['pin_group_fail']!}">
            <input type="hidden" name="verifier_list" value="${request['verifier_list']!}">
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
                    <div class="fl" >
                        <span>订单状态</span>
                        <select name="order_status" style="width: 175px;" id="order_status">
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
                    <#if  ($request['pin_group_fail'] != 1)
                        <div class="fl" >
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
                    </#if>
                    <div class="fl" >
                        <span><#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if></span>
                        <input type="text" name="consignee" placeholder="<#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if>" style="width: 175px;"
                               value="${request['consignee']!}"/>
                    </div>
                    <div class="fl">
                        <span><#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if></span>
                        <input type="text" name="mobile" placeholder="<#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if>" value="${request['mobile']!}"/>
                    </div>
                </li>
                <li class="order-info-li clearfix">
                    <div class="fl" style="width: 680px;">
                        <span>下单时间</span>
                        <input type="text" name="add_time1" placeholder="下单时间" value="${request['add_time1']!}"
                               onclick="picker();" autocomplete="off"/>
                        至
                        <input type="text" name="add_time2" placeholder="下单时间" value="${request['add_time2']!}"
                               onclick="picker();" autocomplete="off"/>
                    </div>
                    <div class="fl">
                        <span>配送方式</span>
                        <select name="deliver_type" id="deliver_type" style="width:175px">
                            <option value="-1">全部</option>
                            <option value="0" <#if (($request['deliver_type'] ?? -1)  == 0) selected </#if>>快递</option>
                            <option value="1" <#if (($request['deliver_type'] ?? -1)  == 1) selected </#if>>自提</option>
                        </select>
                    </div>
                </li>
                <li class="order-info-li clearfix more_search" <#if ($request['username'] || $request['store_id'] || $request['verify_code'] || $request['province_code'] || $request['city_code'] ||$request['district_code'] || $request['finished_time1'] || $request['finished_time2']) <#else> hidden </#if>>

                    <div class="fl">
                        <span>会员昵称</span>
                        <input type="text" name="username" placeholder="会员昵称" value="${request['username']!}"/>
                    </div>
                    <div class="fl">
                        <span>门店</span>
                        <select name="store_id" style="width: 175px;">
                            <option value="">请选门店</option>
                            <#list ($store as $st)
                                <option value="${st->store_id!}" <#if ($request['store_id']==$st->store_id) selected </#if>>${st->store_name!}</option>
                            </#list>
                        </select>

                    </div>
                    <div class="fl">
                        <span>核销码</span>
                        <input type="text" name="verify_code" placeholder="核销码" value="${request['verify_code']!}"/>
                    </div>
                </li>
                <li class="order-info-li clearfix more_search" <#if ($request['username'] || $request['store_id'] || $request['verify_code'] || $request['province_code'] || $request['city_code'] ||$request['district_code'] || $request['finished_time1'] || $request['finished_time2']) <#else> hidden </#if>>

                <div class="fl" style="width: auto;">
                        <span>完成时间</span>
                        <input type="text" name="finished_time1" placeholder="完成时间" value="${request['finished_time1']!}"
                               onclick="picker();" autocomplete="off"/>
                        至
                        <input type="text" name="finished_time2" placeholder="完成时间" value="${request['finished_time2']!}"
                               onclick="picker();" autocomplete="off"/>
                    </div>
                    <div class="fl receive_address ">
                        <span>收货地址</span>
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
                </li>
                {{--<li class="order-info-li clearfix more_search" <#if ($request['username'] || $request['store_id'] || $request['verify_code'] || $request['add_time1'] ||--!}
                {{--$request['add_time2'] || $request['finished_time1'] || $request['finished_time2']) <#else> hidden </#if>>--!}

                    {{----!}
                {{--</li>--!}
                <li class="order-info-li clearfix ">
                    <#if (in_array($shop_flag,[1,2]))
                    <div class="fl">
                        <span>订单来源</span>
                        <select name="goods_source" id="goods_source" style="width: 175px">
                            <option value="">请选择订单来源</option>
                            <option value=1 <#if ($request['goods_source']  == 1) selected </#if>>自营订单</option>
                            <option value=2 <#if ($request['goods_source']  == 2) selected </#if>>平台订单</option>
                        </select>
                    </div>
                    </#if>
                    <#if ($request['deliver_type'] == 1)
                        <div class="fl">
                            <span>核销员手机号</span>
                            <input type="text" name="verifier_mobile" placeholder="核销员手机号" value="${request['verifier_mobile']!}"/>
                        </div>

                    </#if>
                <div>
                    <button type="button" class="btn-choose">筛选</button>
                    <button type="button" class="btn-excel" style="margin-left: 12px;" orderExport_list="">导出表格</button>
                </div>
                </li>
                <li class="order-info-li clearfix" style="text-align: center;">
                    <a href="javascript:void(0);" style="color: #5a8bff;" class="show_more" <#if ($request['username'] || $request['store_id'] || $request['verify_code'] || $request['province_code'] || $request['city_code'] ||$request['district_code'] || $request['finished_time1'] || $request['finished_time2']) hidden <#else>  </#if>>更多 <img src="http://${image_domain!}/image/admin/show_more.png" alt=""></a>
                    <a href="javascript:void(0);" style="color: #5a8bff;" class="hid_some" <#if ($request['username'] || $request['store_id'] || $request['verify_code'] || $request['province_code'] || $request['city_code'] ||$request['district_code'] || $request['finished_time1'] || $request['finished_time2'])  <#else> hidden </#if> >收起 <img src="http://${image_domain!}/image/admin/hid_some.png" alt=""></a>
                </li>
            </ul>


        </div>
        <div class="order-list">
            <#if  ($request['pin_group_fail'] != 1)
            <ul class="order-list-nav clearfix">
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag']) && ($request['order_status'] ?? -1) == -1 && empty($request["order_status_d"]))
                    class="nav-active" </#if>>
                    <a id="all" <#if ($request['deliver_type'] == 1) href="/admin/orders/manage/list?deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0) href="/admin/orders/manage/list?deliver_type=0"
                        <#else> href="/admin/orders/manage/list" </#if> >全部</a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 0)
                    class="nav-active" </#if>>
                    <a id="wait_pay" <#if ($request['deliver_type']== 1)  href="/admin/orders/manage/list?order_status=0&deliver_type=1"
                       <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                       href="/admin/orders/manage/list?order_status=0&deliver_type=0"
                            <#else> href="/admin/orders/manage/list?order_status=0" </#if>>待付款</a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 3 || $request["order_status_d"]==3)
                    class="nav-active" </#if>>
                    <#if ($request['deliver_type'] == 1)
                    <a id="wait_ti" href="/admin/orders/manage/list?order_status=3&deliver_type=1">
                        <span class="wait_re">待提货 <span class="wait_num">${amount['wait_take_deliver']!}</span></span>
                    </a>
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    <a id="wait_fa" href="/admin/orders/manage/list?order_status=3&deliver_type=0">
                        <span class="wait_re">待发货 <span class="wait_num">${amount['wait_delivery']!}</span></span>
                    </a>
                    <#else>
                    <a id="wait_t_f" href="/admin/orders/manage/list?order_status_d=3">
                        <span class="wait_re">待发货 <span class="wait_num">${amount['wait_delivery']!}</span></span> /<span class="wait_re">待提货 <span class="wait_num">${amount['wait_take_deliver']!}</span></span>
                    </a>
                    </#if>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 4)
                    class="nav-active" </#if>  <#if ($request['deliver_type']== 1) hidden </#if>>
                    <a  id="fa" <#if ($request['deliver_type']== 1) href="/admin/orders/manage/list?order_status=4&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/admin/orders/manage/list?order_status=4&deliver_type=0"
                            <#else> href="/admin/orders/manage/list?order_status=4"</#if>>已发货</a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 5 || $request["order_status_d"]==5)
                    class="nav-active" </#if>>
                    <#if ($request['deliver_type'] == 1)
                        <a id="ti" href="/admin/orders/manage/list?order_status=5&deliver_type=1">已提货</a>
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                        <a id="shou" <#if ($request['deliver_type']== 1) href="/admin/orders/manage/list?order_status=5&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/admin/orders/manage/list?order_status=5&deliver_type=0" </#if>>已收货</a>
                    <#else>
                    <a id="" href="/admin/orders/manage/list?order_status_d=5">已收货/已提货</a>
                    </#if>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 6)
                    class="nav-active" </#if>>
                    <a id="finish" <#if ($request['deliver_type']== 1) href="/admin/orders/manage/list?order_status=6&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/admin/orders/manage/list?order_status=6&deliver_type=0"
                        <#else> href="/admin/orders/manage/list?order_status=6" </#if>>已完成</a>
                </li>
                <li <#if (($request['return_flag'] ?? 0) == 1) class="nav-active" </#if>>
                    <a id="return" <#if ($request['deliver_type']== 1) href="/admin/orders/manage/list?return_flag=1&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/admin/orders/manage/list?return_flag=1&deliver_type=0"
                       <#else> href="/admin/orders/manage/list?return_flag=1" </#if>>
                        <span class="wait_re">退货/退款中 <span class="wait_num">${amount['refunding']!}</span></span>
                    </a>
                </li>
                <li <#if (!isset($request['star_flag']) && !isset($request['return_flag'])&& ($request['order_status'] ?? -1) == 2)
                    class="nav-active" </#if>>
                    <a id="close" <#if ($request['deliver_type']== 1) href="/admin/orders/manage/list?order_status=2&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/admin/orders/manage/list?order_status=2&deliver_type=0"
                        <#else> href="/admin/orders/manage/list?order_status=2" </#if>>已关闭</a>
                </li>
                <li <#if (($request['star_flag'] ?? 0) == 1) class="nav-active" </#if>>
                    <a id="star" <#if ($request['deliver_type']== 1) href="/admin/orders/manage/list?star_flag=1&deliver_type=1"
                    <#elseif>(!is_null($request['deliver_type']) && $request['deliver_type']== 0)
                    href="/admin/orders/manage/list?star_flag=1&deliver_type=0"
                        <#else> href="/admin/orders/manage/list?star_flag=1" </#if>>追星订单</a>
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
                                <span class="span2 tb-decorate-a">
                                        支付方式：
                                    <#if (in_array('wxpay', $order->pay_way))
                                    <span class="pay_way" style="color:#ff6666;" data-tips="微信支付"><img src="http://${image_domain!}/image/admin/wxpay.png" alt=""></span>
                                    </#if>
                                    <#if (in_array('balance', $order->pay_way))
                                    <span class="pay_way" style="color:#ff6666;" data-tips="余额支付"><img src="http://${image_domain!}/image/admin/account.png" alt=""></span>
                                    </#if>
                                    <#if (in_array('cod', $order->pay_way))
                                    <span class="pay_way" style="color:#ff6666;" data-tips="货到付款"><img src="http://${image_domain!}/image/admin/cod.png" alt=""></span>
                                    </#if>
                                    <#if (in_array('score', $order->pay_way))
                                    <span class="pay_way" style="color:#ff6666;" data-tips="积分支付"><img src="http://${image_domain!}/image/admin/score1.png" alt=""></span>
                                    </#if>
                                    <#if ($order->goods_type == 4)
                                    <span class="pay_way" style="color:#ff6666;" data-tips="积分兑换"><img src="http://${image_domain!}/image/admin/rewards_points.png" alt=""></span>
                                    </#if>
                                    <#if (in_array('lottery', $order->pay_way))
                                    <span class="pay_way" style="color:#ff6666;" data-tips="活动奖品"><img src="http://${image_domain!}/image/admin/lottery.png" alt=""></span>
                                    </#if>
                                </span>
                                {{--<#if  ($order->goods_type == 4)--!}
                                    {{--<span class="span2">--!}
                                        {{--支付方式：积分兑换--!}
                                    {{--</span>--!}
                                    {{--<#else>--!}
                                    {{--<span class="span2">--!}
                                        {{--支付方式：${order->pay_name!}--!}
                                    {{--</span>--!}
                                {{--</#if>--!}
                                 <span class="span3">
                                    配送方式：${order->deliver_type == 0 ? "快递" : "自提"!}
                                </span>
                                 <span style="vertical-align: bottom;position: relative" >
                                     <span class="span4">订单类型：${order->goods_type_str!}</span>
                                       <div class="span_div hide">
                                         <div class="triangle"></div>
                                         <span>订单类型：${order->goods_type_str!}</span>
                                     </div>
                                 </span>

                                {{--<#if ($order->deliver_type !=0)--!}
                                {{--<span class="span3" >核销码：${order->verify_code!}</span>--!}
                                {{--</#if>--!}
                                <span class="fr" order_sn="${order->order_sn!}">
                                       {{--<span class="text-overflow seller-remark"--!}
                                             {{--title="${order->seller_remark!}">${order->seller_remark!}</span>--!}
                                       <span class="btn-star-flag ${order->star_flag ? "":"empty-flag"!}"
                                             title="切换星标"></span>
                                    <a href="javascript:void(0);" class="add_text" order_sn="${order->order_sn!}"
                                       seller_remark="${order->seller_remark!}">添加备注</a>
                                    <a href="/admin/orders/manage/info?order_sn=${order->order_sn!}&sub_index=${sub_index!}" target="_blank">查看详情</a>
                                    <a href="/admin/goods/comment/list?order_sn=${order->order_sn!}" target="_blank">查看评价</a>
                                </span>
                            </td>

                        </tr>
                        {{--定金膨胀的信息--!}
                        <#if  ($order->goods_type == 10)
                        <tr class="order-tb-more">
                            <td colspan="8">
                                <span class="span1">
                                    定金：￥{{ number_format($order->money_paid + $order->member_card_balance + $order->score_discount + $order->use_account, 2, '.', '')!}
                                </span>
                                <#if  (!empty(floatval($order->bk_order_money)))
                                <span class="span2" style="margin-left: 30px">
                                    尾款：￥${order->bk_order_money!}
                                </span>
                                </#if>
                                <span style="margin-left: 90px">
                                    <#if  ($order->deliver_type == 1)自提时间：${order->pickup_time!}
                                    <#else> 发货时间：${order->bk_shipping_time!}</#if>
                                </span>
                            </td>
                        </tr>
                        </#if>
                        {{--定金膨胀的信息结束--!}
                        <#list ($order->order_goods as $i => $order_good)
                            <tr class="order-tb-body">
                                <td>
                                    <div class="order-goods-info clearfix">
                                        <div class="fl">
                                            <img src="${order_good->goods_img!}!small"/>
                                        </div>
                                        <div class="fr">
                                            <p style="padding-bottom: 1px;">
                                                <#if  ($order_good->purchase_price_rule_id)
                                                <span class="change_goods">换购</span>
                                                </#if>
                                                <#if ($order_good->is_card_exclusive)
                                                    <span style="color: red;border: 1px solid;">会员专享</span>
                                                </#if>
                                                <#if ($order_good->is_gift && $order_good->gift_id)
                                                    <span style="color: red;border: 1px solid; padding: 0px 3px;display: inline-block;">赠品</span>
                                                </#if>
                                                    <#if  ($order_good->source == 0 && in_array($shop_flag,[1,2]))
                                                        <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">自营</span>
                                                    </#if>
                                                ${order_good->goods_name!}
                                            </p>
                                            <div>${order_good->goods_attr!}</div>
                                        </div>
                                    </div>


                                </td>
                                <td width="100px">${order_good->goods_sn!}</td>
                                <td width="100px">
                                    <#if  ($order->goods_type == 4)
                                    ￥${order_good->market_price!}
                                        <#else>
                                    ${order_good->goods_price!}
                                    </#if>
                                </td>
                                <td width="100px">${order_good->goods_number!}</td>
                                <#if ($i == 0)
                                    <td rowspan="{{ count($order->order_goods)>2 ? 3 : count($order->order_goods)!}" count="{{count($order->order_goods)!}">
                                        <a href="/admin/user/manage/list?id=${order->user_id!}" style="color: #5a8bff">
                                            <p>${order->consignee!}</p>
                                            <span>${order->mobile!}</span>
                                        </a>
                                    </td>
                                    <td rowspan="{{ count($order->order_goods)>2 ? 3 : count($order->order_goods)!}" count="{{count($order->order_goods)!}">${order->add_time!}</td>
                                    <td width="180px" class="order-status" rowspan="{{ count($order->order_goods)>2 ? 3 : count($order->order_goods)!}" count="{{count($order->order_goods)!}"
                                        order_sn="${order->order_sn!}"
                                        order_status="${order->order_status!}"
                                        refund_money="${order->max_refund_money!}"
                                        verify_code="${order->verify_code!}">

                                        <div class="text-center padding-10">
                                            <#if ($order->order_status !=3 && $order->order_status != 5)
                                                <#if  ($order->order_status == 0 && $order->goods_type == 10)
                                                    <#if  ($order->bk_order_paid == 0)
                                                        待付定金
                                                        <#else>
                                                        待付尾款
                                                    </#if>
                                                    <#else>
                                                    ${order->order_status_name!}
                                                </#if>
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
                                            <#if ($order->part_ship_flag && $order->order_status==3)
                                                </br>(部分发货)
                                            </#if>
                                        </div>

                                        <#if ($order->operation->can_deliver && $request['pin_group_fail'] != 1 && !$order->is_return_all)
                                            <button type="button" class="btn-common btn-deliver">发货</button>
                                        </#if>
                                        {{--等待付定金的状态下面的按钮--!}
                                        {{--<button type="button" class="btn-common">关闭</button>--!}
                                        <#if (($order->refund_status > 0) )
                                            <#if (in_array($order->refund_status,[1,2,4]))
                                                <a class="text-link" href="/admin/orders/manage/return/list?order_sn=${order->order_sn!}&sub_index=${sub_index!}">退款退货审批</a>
                                            <#else>
                                                <a class="text-link" href="/admin/orders/manage/return/list?order_sn=${order->order_sn!}&sub_index=${sub_index!}">查看退款</a>
                                            </#if>
                                        </#if>

                                        <#if ($order->operation->can_verify)
                                            <button type="button" class="btn-common btn-verify" pos_flag="${order->pos_flag!}">核销</button>
                                        </#if>
                                        <#if ($order->operation->can_close)
                                            <button type="button" class="btn-common btn-close">关闭</button>
                                        </#if>
                                        <#if ($order->operation->can_finish)
                                            <button type="button" class="btn-common btn-finish">完成</button>
                                        </#if>

                                    </td>
                                    <td width="150px" rowspan="{{ count($order->order_goods)>2 ? 3 : count($order->order_goods)!}" count="{{count($order->order_goods)!}">
                                        <#if ($order->goods_type != 4)
                                            <p>
                                                <span class="text-warning">￥
                                                    <#if  ($order->bk_order_paid > 1)
                                                        {{ number_format($order->money_paid + $order->bk_order_money, 2)!}
                                                    <#else>
                                                        {{ number_format($order->money_paid, 2)!}
                                                    </#if>
                                                </span>
                                            </p>
                                            <#if ($order->deliver_type == 0)
                                                <p>
                                                    (含快递：<span
                                                            class="text-warning">￥{{ number_format($order->shipping_fee,2)!}</span>)
                                                </p>
                                            </#if>
                                         <#else> 
                                         <p class="text-warning">
                                             <span>￥{{number_format($order->money_paid,2)!}</span> + <span>${order->score_discount*100!}积分</span>
                                         </p>
                                         <p>(免运费)</p>
                                         </#if>
                                    </td>
                                </#if>
                            </tr>
                        </#list>
                            <tr class="expand-view-more">
                                <td colspan="4">
                                    点击展开 查看更多
                                    <img src="http://${image_domain!}/image/admin/expand-view-more.png" alt="">
                                </td>
                                
                            </tr>
                            <tr class="expand-collapse">
                                <td colspan="4">
                                    点击收起
                                    <img src="http://${image_domain!}/image/admin/expand-collapse.png" alt="">
                                </td>
                                
                            </tr>
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
              <#include "/admin/jump_page_admin.ftl">
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
<div id="set-pre-edit" style="display: none;">

    <div id="role_template" style="padding: 0px 20px;">
        <input name="namestr" value="" type="hidden">
        <div data-toggle="switch_box" class="active">
            <table style="width: 100%;">
                <tr>
                    <table class="table table-bordered">
                                <tr>
                                    <td style="width:25%;">
                                        <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                        <label for="select-all"></label>
                                        基础信息：
                                    </td>
                                    <td>
                                        <ul class="role_ul">
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox"
                                                       name="sub_cbx[]" checked="true" value="order_sn"
                                                       onclick="return false;" class="necessary_chose two_click basic_info"/>
                                                <span style="color: red;">订单号</span>
                                            </li>
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox" class='two_click basic_info'
                                                       name="sub_cbx[]" checked="true" value="order_status_name"
                                                />
                                                <span>订单状态</span>
                                            </li>
                                            <li style="display: inline-block; width: 122px">
                                                <input type="checkbox" class='two_click basic_info'
                                                       name="sub_cbx[]" checked="true" value="pay_names"
                                                />
                                                <span>支付方式</span>
                                            </li>
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox" class='two_click basic_info'
                                                       name="sub_cbx[]" checked="true" value="add_time"
                                                />
                                                <span>提交订单时间</span>
                                            </li>
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox" class='two_click basic_info'
                                                       name="sub_cbx[]" checked="true" value="pay_time"
                                                />
                                                <span>支付时间</span>
                                            </li>
                                            {{--<li style="display: inline-block; ">--!}
                                                {{--<input type="checkbox" class='two_click'--!}
                                                       {{--name="sub_cbx[]" checked="true" value="goods_number"--!}
                                                {{--/>--!}
                                                {{--<span>订单商品总数量</span>--!}
                                            {{--</li>--!}
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox" class='two_click'
                                                       name="sub_cbx[]" checked="true" value="closed_time"
                                                />
                                                <span>关闭订单时间</span>
                                            </li>
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox" class='two_click'
                                                       name="sub_cbx[]" checked="true" value="cancelled_time"
                                                />
                                                <span>取消订单时间</span>
                                            </li>
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox" class='two_click'
                                                       name="sub_cbx[]" checked="true" value="finished_time"
                                                />
                                                <span>订单完成时间</span>
                                            </li>
                                            <li style="display: inline-block;width: 122px ">
                                                <input type="checkbox" class='two_click'
                                                       name="sub_cbx[]" checked="true" value="is_cod"
                                                />
                                                <span>是否货到付款</span>
                                            </li>
                                        </ul>
                                    </td>
                                </tr>
                        <tr>
                            <td style="width:25%;">
                                <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                <label for="select-all"></label>
                                收货人信息：
                            </td>
                            <td>
                                <ul class="role_ul">
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="consignee"
                                        />
                                        <span>收货人姓名</span>
                                    </li>
                                    <li style="display: inline-block; width: 122px">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="complete_address"
                                        />
                                        <span>收货地址</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:10%;">
                                <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                <label for="select-all"></label>
                                买家信息：
                            </td>
                            <td>
                                <ul class="role_ul">
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="province_name"
                                        />
                                        <span>买家省份</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="city_name"
                                        />
                                        <span>买家城市</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="district_name"
                                        />
                                        <span>买家地区</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="zipcode"
                                        />
                                        <span>邮政编码</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="mobile"
                                        />
                                        <span>手机号</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="add_message"
                                        />
                                        <span>买家留言</span>
                                    </li>
                                    <li style="display: inline-block; width: 122px">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="is_new"
                                        />
                                        <span>新老用户</span>
                                        <img src="http://${image_domain!}/image/admin/system_icon.png"class="img" />
                                        <div class="system_info_content" style="background: order#F0F0F0;width:273px;">
                                            <div class="system_info_content_top">
                                               <span class="system_v1">下此订单前，是否在小程序店铺有过订单</span>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:20%;">
                                <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                <label for="select-all"></label>
                                物流信息：
                            </td>
                            <td>
                                <ul class="role_ul">
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="shipping_time"
                                        />
                                        <span>发货时间</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="shipping_name"
                                        />
                                        <span>货运名称</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="shipping_no"
                                        />
                                        <span>物流单号</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="deliver_type_name"
                                        />
                                        <span>配送类型</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="confirm_time"
                                        />
                                        <span>确认收货时间</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="store_id"
                                        />
                                        <span>门店ID</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="store_name"
                                        />
                                        <span>门店名称</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:20%;">
                                <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                <label for="select-all"></label>
                                商品信息：
                            </td>
                            <td>
                                <ul class="role_ul">
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox"
                                               name="sub_cbx[]" checked="true" value="goods_name"
                                               onclick="return false;" class="necessary_chose two_click"/>
                                        <span style="color: red;">商品名称</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox"
                                               name="sub_cbx[]" checked="true" value="product_sn"
                                               onclick="return false;" class="necessary_chose two_click"/>
                                        <span style="color: red;">商家编码</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="goods_number"
                                        />
                                        <span>商品数量</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="discounted_goods_price"
                                        />
                                        <span>实际售价</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="goods_attr"
                                        />
                                        <span>SKU属性</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="goods_price"
                                        />
                                        <span>商品售价</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="market_price"
                                        />
                                        <span>商品市场价</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="goods_sn"
                                        />
                                        <span>商品货号</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="goods_id"
                                        />
                                        <span>商品ID</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="send_number"
                                        />
                                        <span>已发货数量</span>
                                    </li>
                                    <li style="display: inline-block; width: 122px">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="return_number"
                                        />
                                        <span>退货数量</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="source"
                                        />
                                        <span>商品来源</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:20%;">
                                <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                <label for="select-all"></label>
                                交易信息：
                            </td>
                            <td>
                                <ul class="role_ul">
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="order_amount"
                                        />
                                        <span>订单总金额</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="discount"
                                        />
                                        <span>优惠金额</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="shipping_fee"
                                        />
                                        <span>邮费</span>
                                    </li>
                                    <li style="display: inline-block; width: 122px">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="score_discount"
                                        />
                                        <span>积分抵扣</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="use_account"
                                        />
                                        <span>使用余额</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="promotion_reduce"
                                        />
                                        <span>满折满减</span>
                                    </li>
                                    <li style="display: inline-block;width: 135px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="money_paid"
                                        />
                                        <span>买家实际支付金额</span>
                                    </li>
                                    <li style="display: inline-block;width: 122px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="member_card_balance"
                                        />
                                        <span>会员卡抵扣</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:20%;">
                                <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                <label for="select-all"></label>
                                售后信息：
                            </td>
                            <td>
                                <ul class="role_ul">
                                    <li style="display: inline-block;width: 135px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="return_time"
                                        />
                                        <span>申请退货时间</span>
                                    </li>
                                    <li style="display: inline-block;width: 135px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="return_finish_time"
                                        />
                                        <span>退货完成时间</span>
                                    </li>
                                    <li style="display: inline-block;width: 130px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="return_order_money"
                                        />
                                        <span>退款金额</span>
                                    </li>
                                    <li style="display: inline-block;width: 130px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="return_shipping_fee"
                                        />
                                        <span>退运费金额</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:20%;">
                                <input type="checkbox" class="select-all" />&nbsp;&nbsp;
                                <label for="select-all"></label>
                                其他信息：
                            </td>
                            <td>
                                <ul class="role_ul">
                                    <li style="display: inline-block;width: 130px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="seller_remark"
                                        />
                                        <span>卖家备注</span>
                                    </li>
                                    <li style="display: inline-block;width: 130px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="order_real_name"
                                        />
                                        <span>真实姓名</span>
                                    </li>
                                    <li style="display: inline-block;width: 130px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="order_cid"
                                        />
                                        <span>身份证号</span>
                                    </li>
                                    <li style="display: inline-block;width: 150px ">
                                        <input type="checkbox" class='two_click'
                                               name="sub_cbx[]" checked="true" value="custom"
                                        />
                                        <span>自定义下单必填信息</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>


                    </table>
                    </td>
                </tr>

            </table>

        </div>
    </div>
</div>
<script type="text/javascript">
    var anchor = "${anchor!}";

    $(".span4").each(function () {
        $(this).mouseover(function () {
            $(this).parent().find(".span_div").removeClass('hide');
        })
        $(this).mouseleave(function(){
            $(this).parent().find(".span_div").addClass('hide');
        })
    })


    //分页
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        var href = $(".order-list-nav li.nav-active a").attr('href');
        if('${request['pin_group_fail']!}'==1){
            href = '/admin/orders/manage/list?pin_group_fail=1';
        }
        $("#act").val("");
        $("#page").val(page);
        $('#form1').attr('action', href);
        $("#form1").submit();
    }

    $(".img").hover(function(){
        $(".system_info_content").css("display","block");
    },function(){
        $(".system_info_content").css("display","none");
    });

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


    $.each($('tr.order-tb-head'), function (i, item) {
        if($(this).siblings('.order-tb-body').length > 2) {
            $(this).siblings('.order-tb-body:gt(1)').hide();
            $(this).siblings('.expand-view-more').css('display', 'table-row');
        } else {
            $(this).siblings('.expand-view-more').css('display', 'none');
        }
    }) 
    $('.expand-view-more').on('click', function () {
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(4).attr("rowspan",parseInt($(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(4).attr("count"))+1);
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(5).attr("rowspan",parseInt($(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(5).attr("count"))+1);
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(6).attr("rowspan",parseInt($(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(6).attr("count"))+1);
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(7).attr("rowspan",parseInt($(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(7).attr("count"))+1);
        $(this).siblings('.order-tb-body:gt(1)').show();
        
        $(this).css('display', 'none');
        $(this).siblings('.expand-collapse').css('display', 'table-row');
    })
    $('.expand-collapse').on('click', function () {
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(4).attr("rowspan",3);
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(5).attr("rowspan",3);
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(6).attr("rowspan",3);
        $(this).parents("tbody").find(".order-tb-body").eq(0).find("td").eq(7).attr("rowspan",3);
        $(this).siblings('.order-tb-body:gt(1)').hide();
        $(this).siblings('.expand-view-more').css('display', 'table-row');
        $(this).css('display', 'none');
    })


</script>

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/region.js?v=1.0.0" type="text/javascript"></script>
<script type="text/javascript">
    region_util.init_ev();
    $(".select-all").click(function () {
        var isChecked = $(".select-all").prop("checked");
        if($(this).is(':checked')) {
            $(this).attr('src','/image/admin/square_yes.png');
            $(this).parent().next().find("li").find("input[type='checkbox']").each(function(){
                if(!$(this).is(':checked'))
                    $(this).click();
            })
        }else{
            $(this).attr('src','/image/admin/square_no.png');
            $(this).parent().next().find("li").find("input[type='checkbox']").each(function(){
                if($(this).is(':checked'))
                    $(this).click();
            })
        }
    });
    $("input[type='checkbox']").click(function(){
        if($(this).is(':checked')) {
             $(this).prop('checked',true);
            $(this).parent().parent().parent().prev().find("input[type='checkbox']").prop('checked',true);
        }else{
            $(this).prop('checked',false);
        }

    })
    $('.tb-decorate-a').on("mouseover",'.pay_way',function(){
        $(this).css({
            "position" : "relative"
        })
        $('<div class="tips"></div>').html($(this).data('tips')).appendTo(this);
    }).on('mouseleave','.pay_way',function(){
        $(this).css({
            "position":"static"
        })
        $(this).find('.tips').remove();
    });
</script>
<script src="{{asset('js/admin/order_list.js')!}?v=1.1.9" type="text/javascript"></script>

<#include "/admin/footer.ftl">