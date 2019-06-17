<#include ("system.header")
<link rel="stylesheet" href="/css/system/order_all.css?v=1.0.28" type="text/css"/>
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
    .order-list{
        padding-top: 10px;
    }
    .order-tb-body td{
        padding:15px;
    }
</style>
<div class="title">
    {!! $navigation !!}
</div>

<form name="formData"  action="/system/orders/activity/order/list?top_index=4&goods_type=${request['goods_type']!}&act_id=${request['act_id']!}"  method="post" id="form1">
    <div class="order-container">
        <div class="order-info">
            {{ csrf_field()!}
            <input type="hidden" name="act" id="act">
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
                            {{--<#list ($order_status_map as $orderStatus => $orderStatusName)--!}
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
                            {{--</#list>--!}
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
                    <div class="fl">
                        <span>下单时间</span>
                        <input type="text" name="add_time" placeholder="下单时间" value="${request['add_time']!}"
                               onclick="picker();" autocomplete="off"/>
                    </div>
                </li>
                <div class="fl receive_address kuaidi">
                    <span>收货地址</span>
                    <select name="province_code" id="province_code">
                        <option value="">请选择省</option>
                        {{--<#list ($provinceList as $province)--!}
                            <option value="${province->province_id!}"
                                    <#if ($request['province_code']  == $province->province_id) selected </#if>>
                                ${province->name!}
                            </option>
                        {{--</#list>--!}
                    </select>
                    <select name="city_code" id="city_code">
                        <option value="">请选择市</option>
                        {{--<#list ($cityList as $city)--!}
                            <option value="${city->city_id!}"
                                    <#if ($request['city_code']  == $city->city_id) selected </#if>>
                                ${city->name!}
                            </option>
                        {{--</#list>--!}
                    </select>
                    <select name="district_code" id="district_code">
                        <option value="">请选择区县</option>
                        {{--<#list ($districtList as $district)--!}
                            <option value="${district->district_id!}"
                                    <#if ($request['district_code']  == $district->district_id) selected </#if>>
                                ${district->name!}
                            </option>
                        {{--</#list>--!}
                    </select>
                </div>
                <li class="order-info-li clearfix btn_zu">
                    <button type="button" class="btn-choose">筛选</button>
                    <button type="button" class="btn-excel" style="margin-left: 12px;">导出表格</button>
                </li>
            </ul>
        </div>
        <div class="order-list">
            <div class="order-list-table">
                <table width="100%" style="font-size: inherit;">
                    <thead>
                    <tr>
                        <td width="10%">活动名称</td>
                        <td width="15%">订单号</td>
                        <td width="8%">降价商品</td>
                        <td width="10%">单价</td>
                        <td width="9%">折后价</td>
                        <td width="12%">下单时间</td>
                        <td width="10%">下单人信息</td>
                        <td width="10%">收货人信息</td>
                        <td width="8%">支付金额</td>
                        <td width="8%">订单状态</td>
                    </tr>
                    </thead>
                    {{--<#list ($data_list as $order)--!}
                        <tbody>
                        {{--<#list ($order->order_goods as $i => $order_good)--!}
                            <tr class="order-tb-body">
                                <td width="10%">${act_name!}</td>
                                <td width="15%"><a target="_blank" href="/system/orders/manage/info?order_sn=${order->order_sn!}" style="color: #5a8bff;word-break: break-all">${order->order_sn!}</a></td>
                                <td width="8%">
                                    <div class="order-goods-info clearfix">
                                        <div class="fl">
                                            <img src="${order_good->goods_img!}" alt="商品名称"/>
                                        </div>
                                        <div class="fr">
                                            <p>${order_good->goods_name!}</p>
                                            <div>${order_good->goods_attr!}</div>
                                        </div>
                                    </div>
                                </td>
                                <td width="10%"></td>
                                <td width="9%">
                                ${order_good->goods_price!}
                                </td>
                                <td width="12%">${order->add_time!}</td>
                                <td width="10%">
                                    <a target="_blank" href="/system/user/manage/center?top_index=5&user_id=${order->user_id!}&sub_index=0" style="color: #5a8bff">
                                        <p>${order->user_name!}</p>
                                        <span>${order->user_mobile!}</span>
                                    </a>
                                </td>
                                <td width="10%">
                                    <a onclick="showAddress(this)" consignee="${order->consignee!}" mobile="${order->mobile!}"
                                       address="${order->complete_address!}" style="color: #5a8bff">
                                        <p>${order->consignee!}</p>
                                        <span>${order->mobile!}</span>
                                    </a>
                                </td>
                                    <td width="8%">
                                        <p>
                                            <span class="text-warning">￥{{ number_format($order->money_paid,2)!}</span>
                                        </p>
                                        <#if ($order->deliver_type == 0)
                                            <p>
                                                (含快递：<span
                                                        class="text-warning">￥{{ number_format($order->shipping_fee,2)!}</span>)
                                            </p>
                                    </td>
                                </#if>
                                <td width="8%">
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
                            </tr>
                        {{--</#list>--!}
                        </tbody>
                    {{--</#list>--!}
                </table>
            </div>
            <div class="clearfix" style="margin-top: 10px">
                {{--<table width="100%" border="0" class="tb_paging" style="font-size: inherit;">--!}
                    {{--<tr>--!}
                        {{--<td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}--!}
                            {{--<a href="#" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>--!}
                            {{--<a href="#"--!}
                               {{--onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>--!}
                            {{--<a href="#"--!}
                               {{--onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>--!}
                            {{--<a href="#"--!}
                               {{--onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>--!}
                            {{--<input id="page" name="page" type="text" value="${data_list->currentPage()!}"--!}
                                   {{--size="5"--!}
                                   {{--onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}--!}
                            {{--<a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("system/common.page.jump_page")!}</a>--!}
                        {{--</td>--!}
                    {{--</tr>--!}
                {{--</table>--!}
            </div>
        </div>
    </div>
</form>
<div class="show_address_tab" style="padding: 14px 14px 0 14px;color: #666;font-size: 14px;display: none">
    <p style="padding: 2px">姓名：<a class="consignee" style="color: #666;font-size: 14px"></a></p>
    <p style="padding: 2px">手机号：<a class="mobile" style="color: #666;font-size: 14px"></a></p>
    <p style="padding: 2px">地址：<a class="address" style="color: #666;font-size: 14px"></a></p>
</div>
<script type="text/javascript">
    //分页
    {{--function gopage(page) {--!}
        {{--var last_page = '${data_list -> lastPage()!}';--!}
        {{--if(parseInt(page) > parseInt(last_page)) {--!}
            {{--page = last_page;--!}
        {{--}--!}
        {{--var href = $(".order-list-nav li.nav-active a").attr('href');--!}
        {{--$("#act").val("");--!}
        {{--$("#page").val(page);--!}
        {{--$('#form1').attr('action', href);--!}
        {{--$("#form1").submit();--!}
    {{--}--!}

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    function showAddress(_this) {
        var consignee = $(_this).attr('consignee');
        var mobile = $(_this).attr('mobile');
        var address = $(_this).attr('address');
        layer.open({
            type: 1
            , title: ['收货人信息', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: '300px'
            , id: 'layerDemoD' //防止重复弹出
            , content: $('.show_address_tab') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['确定']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success:function () {
                $('.show_address_tab .consignee').html(consignee);
                $('.show_address_tab .mobile').html(mobile);
                $('.show_address_tab .address').html(address);
            }, yes:function (index) {
                layer.close(index);
            }
        });
    }
    // $(".order-list-nav a").click(function (event) {
    //     event.preventDefault();
    //     var href = $(this).attr('href');
    //     var tabStr = href.indexOf('?') > -1 ? href.split('?')[1].split('=')[0] : '';
    //
    //     //tab切换时不保留上次的 tab提交条件
    //     function removeEle(callback) {
    //         var ele = ['return_flag', 'star_flag', 'order_status_d'];   //tab清除定义
    //         $.each(ele, function (index, item) {
    //             $('#' +item).removeAttrs('value');
    //         });
    //         if($.inArray(tabStr, ['return_flag', 'star_flag']) == -1) {
    //             //这两个tab字段需要保留搜索框的订单状态
    //             $('#order_status').val('-1');
    //         }
    //         callback();
    //     }
    //     function doCallback() {
    //         $("#page").val(1);
    //         $('#form1').attr('action', href);
    //         $('#form1').submit();
    //     }
    //     removeEle(doCallback);
    // });
</script>

<script src="/js/system/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/system/region.js?v=1.0.0" type="text/javascript"></script>
<script type="text/javascript">
    region_util.init_ev();
</script>
<script src="/js/system/order_list.js?v=1.1.1" type="text/javascript"></script>

<#include ("system.footer")