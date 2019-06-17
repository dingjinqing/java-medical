<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.28" type="text/css"/>
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
    .order-list-table tbody td{
        border: 1px solid #eee;
    }
    .order-list-table td a{
        color: #5a8bff
    }
    .order-goods-info {
        margin-bottom: 5px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
    <span style="color: #666;"><a href="/admin/market/purchase/list?nav=1">加价购</a>  / </span><span style="color: #666;">换购订单</span>
</div>

<#if ($message)
    <div class="message-tip alert alert-warning margin-top-10 margin-bottom-0">
        ${message!}
    </div>
</#if>
<form name="formData" action="/admin/orders/manage/list?purchase_price_order=1&purchase_price_id=${request['purchase_price_id']!}" method="post" id="form1">
    <div class="order-container">
        <div class="order-info">
            {{ csrf_field()!}
            <input type="hidden" name="act" id="act">
            <input type="hidden" name="act_order_sn" id="act_order_sn">
            <input type="hidden" name="shipping_id" id="shipping_id">
            <input type="hidden" name="shipping_no" id="shipping_no">
            <input type="hidden" name="product_ids" id="product_ids">
            <input type="hidden" name="verify_code" id="verify_code">
            <input type="hidden" name="act_star_flag" id="act_star_flag">
            <input type="hidden" name="refund_money" id="refund_money">
            <input type="hidden" name="reason" id="reason">
            <input type="hidden" name="return_type" id="return_type">
            <input type="hidden" name="pin_group_id" value="<#if  ($request['pin_group_fail'] == 1 || $query['goods_type'] == 1)${request['pin_group_id']!} </#if>">
            <input type="hidden" name="pin_group_fail" value="${request['pin_group_fail']!}">
            <input type="hidden" name="purchase_price_order" value="1"/>
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
                        <span><#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if></span>
                        <input type="text" name="consignee" placeholder="<#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if>" style="width: 155px;"
                               value="${request['consignee']!}"/>
                    </div>
                    <div class="fl">
                        <span><#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if></span>
                        <input type="text" name="mobile" placeholder="<#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if>" value="${request['mobile']!}"/>
                    </div>
                </li>
                <div class="fl receive_address kuaidi">
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
                <li class="order-info-li clearfix btn_zu">
                    <button type="button" class="btn-choose">筛选</button>
                    <button type="button" class="btn-excel" style="margin-left: 12px;">导出表格</button>
                </li>
            </ul>

        </div>
        <div class="order-list">
            <div class="order-list-table" style="padding-top: 20px;">
                <table width="100%" style="font-size: inherit;">
                    <thead>
                    <tr>
                        <td width="170px">订单号</td>
                        <td width="270px">主商品</td>
                        <td width="270px">换购商品</td>
                        <td width="130px">下单时间</td>
                        <td>收货人信息</td>
                        <td>订单状态</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list  ($data_list as $order)
                    <tr>
                        <td>
                            <a href="/admin/orders/manage/info?order_sn=${order->order_sn!}" target="_blank">${order->order_sn!}</a>
                        </td>
                        <td>
                            <#list  ($order->order_goods as $item)
                                <#if  ($item->purchase_price_rule_id == 0)
                                    <div class="order-goods-info clearfix">
                                        <div class="fl">
                                            <img src="${item->goods_img!}">
                                        </div>
                                        <div class="fr">
                                            <p><a style="color:#000;" title="${item->goods_name!}">${item->goods_name!}</a></p>
                                            <div style="width: 80%"><a style="color: #000;" title="${item->goods_attr!}">${item->goods_attr!}</a></div>
                                            <div style="width:15%;right:0">&times;${item->goods_number!}</div>
                                        </div>
                                    </div>
                                </#if>
                            </#list>
                        </td>
                        <td>
                            <#list  ($order->order_goods as $item)
                                <#if  ($item->purchase_price_rule_id > 0)
                                    <div class="order-goods-info clearfix">
                                        <div class="fl">
                                            <img src="${item->goods_img!}">
                                        </div>
                                        <div class="fr">
                                            <p><a style="color:#000;" title="${item->goods_name!}">${item->goods_name!}</a></p>
                                            <div style="width: 80%"><a style="color:#000;" title="${item->goods_attr!}">${item->goods_attr!}</a></div>
                                            <div style="width:15%;right:0">&times;${item->goods_number!}</div>
                                        </div>
                                    </div>
                                </#if>
                            </#list>
                        </td>
                        <td>${order->add_time!}</td>
                        <td>
                            <a href="/admin/user/manage/list?id=${order->user_id!}" target="_blank" style="color: #5a8bff">
                                <p>${order->consignee!}</p>
                                <span>${order->mobile!}</span>
                            </a>
                        </td>
                        <td width="180px" class="order-status"
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
                        </td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="clearfix" style="margin-top: 10px;">
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
                        </td>
                    </tr>
                </table>
            </div >
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

<script type="text/javascript">
    var anchor = "${anchor!}";

    //分页
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(parseInt(page) > parseInt(last_page)) {
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
    /*$(".order-list-nav a").click(function (event) {
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
    });*/
</script>

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/region.js?v=1.0.0" type="text/javascript"></script>
<script type="text/javascript">
    region_util.init_ev();
</script>
<script src="/js/admin/order_list.js?v=1.1.1" type="text/javascript"></script>

<#include "/admin/footer.ftl">