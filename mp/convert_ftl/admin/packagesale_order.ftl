<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/packagesale_manage.css?v=1.0.2" type="text/css"/>
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
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">打包一口价 / </span>
            <span>${act_name!}-活动订单</span>
        </div>
    </div>
    <div class="main-container">
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="search_info search_order_info">
                <input name="act" hidden>
                <ul class="clearfix">
                    <li class="clearfix">
                        <div class="fl">
                            <span>商品名称</span>
                            <input type="text" name="goods_name" placeholder="商品名称" value="${request['goods_name']!}"/>
                        </div>
                        <div class="fl">
                            <span>订单号</span>
                            <input type="text" name="order_sn" placeholder="订单号" value="${request['order_sn']!}"/>
                        </div>
                        <div class="fl">
                            <span>订单类型</span>
                            <select name="order_status" id="order_status">
                                <option value="-1">全部订单</option>
                                <#list ($order_status_map as $orderStatus => $orderStatusName)
                                    <#if ($request['deliver_type'] == 1)
                                        <option value="${orderStatus!}"
                                                <#if (($request['order_status'] ?? -1) == $orderStatus) selected </#if> <#if ($orderStatus==4) hidden </#if>>
                                            <#if ($orderStatus == 3)
                                                待核销
                                            <#elseif>($orderStatus == 5)
                                                已自提
                                            <#else>
                                                ${orderStatusName!}
                                            </#if>
                                        </option>
                                    <#elseif>($request['order_status'] == 3)
                                        <option value="${orderStatus!}"
                                                <#if (($request['order_status'] ?? -1) == $orderStatus) selected </#if>>
                                            <#if ($orderStatus == 3)
                                                待发货
                                            <#elseif>($orderStatus == 5)
                                                待收货
                                            <#else>
                                                ${orderStatusName!}
                                            </#if>
                                        </option>
                                    <#else>
                                        <#if  ($orderStatus <= 10)
                                            <option value="${orderStatus!}"
                                                    <#if (($request['order_status'] ?? -1) == $orderStatus) selected </#if>>
                                                ${orderStatusName!}
                                            </option>
                                        </#if>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">
                            <span><#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if></span>
                            <input type="text" name="consignee" placeholder="<#if ($request['deliver_type']==1) 提货人姓名 <#else> 收货人姓名 </#if>" style="width: 175px;" value="${request['consignee']!}"/>
                        </div>
                        <div class="fl" style="width: 510px">
                            <span><#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if></span>
                            <input type="text" name="mobile" placeholder="<#if ($request['deliver_type']==1) 提货人手机号 <#else> 收货人手机号 </#if>" value="${request['mobile']!}"/>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl address_fl" style="width: 550px;">
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
                        <div class="fl btn_group">
                            <a href="##" class="btn_search">筛选</a>
                            <a href="##" class="btn_reset">导出表格</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content_table order_table clearfix">
                <table width="100%">
                    <thead>
                    <tr>
                        <td width="20%">订单号</td>
                        <td width="25%">商品信息</td>
                        <td width="15%">商品数量</td>
                        <td width="15%">下单时间</td>
                        <td width="15%">收货人信息</td>
                        <td width="10%">订单状态</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#if  ($data_list)
                        <#list ($data_list as $order)
                            <tr>
                                <td width="20%">
                                    <a href="/admin/orders/manage/info?order_sn=${order->order_sn!}">${order->order_sn!}</a>
                                </td>
                                <td width="40%" colspan="2" style="padding: 0;">
                                    <table width="100%" border="0">
                                        <#list ($order->order_goods as $goods)
                                            <tr <#if ($loop->index >= 2) class="all_rows" hidden </#if>>
                                                <td width="62.5%">
                                                    <div class="goods_info clearfix">
                                                        <div class="goods_img"><img src="${goods->goods_img!}" alt=""></div>
                                                        <div class="goods_detail">
                                                            <div class="goods_name">${goods->goods_name!}</div>
                                                            <div class="goods_desc">${goods->goods_attr!}</div>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td width="37.5%">${goods->goods_number!}</td>
                                            </tr>
                                            <#if  ($loop->index >= 2 && $loop->last)
                                                <tr><td colspan="2"><a href="javascript:void(0);" show_type="0" onclick="show_change(this)">点击展开 <img src="/image/admin/expand-view-more.png" alt=""></a></td></tr>
                                            </#if>
                                        </#list>
                                    </table>
                                </td>
                                <td width="15%" >${order->add_time!}</td>
                                <td width="15%" >
                                    <a href="/admin/user/manage/list?id=${order->user_id!}">${order->consignee!} <br> ${order->mobile!}</a>
                                </td>
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
    $('.btn_reset').click(function () {
        $('[name="act"]').val('export_csv');
        $('#form1').submit();
    })

    function show_change(that) {
        let _this = $(that);
        if (_this.attr('show_type') == 1) {
            _this.parent().parent().parent().find('.all_rows').hide();
            _this.attr('show_type','0');
            _this.html('点击展开 <img src="/image/admin/expand-view-more.png" alt="">');
        } else {
            _this.parent().parent().parent().find('.all_rows').show();
            _this.attr('show_type','1');
            _this.html('点击收起 <img src="/image/admin/expand-collapse.png" alt="">')
        }
    }
    getPowerInfo('main_config','package_sale','sub_4','打包一口价',0);
</script>