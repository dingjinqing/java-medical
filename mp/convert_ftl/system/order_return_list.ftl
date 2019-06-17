<#include ("system.header")
<link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<style type="text/css">
    .btn-manual-return:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }
    .btn-manual-return:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }
    .order-status .btn-common:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }
    .order-status .btn-common:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }
    .order-status .btn-common{
        margin: 0 auto 0px auto;
        border-radius:3px;
    }
    .text-warning{
        color:#666;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/order_info.order_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/order_info.order_return_list.return_order")!}</span>
    </div>
    <div class="main-container" >
        <form name="formData" action="/admin/orders/manage/return/list" id="form1" method="post">
            {{csrf_field()!}
            <input type="hidden" name="nav">
            <ul class="order-center clearfix order-info">
                {{--<li>{{ trans("admin/order_info.order_return_list.order_code")!}：${order->order_sn!}</li>--!}
                {{--<li>{{ trans("admin/order_info.order_return_list.order_time")!}：${order->add_time!}</li>--!}
                {{--<li>--!}
                {{--<#if ($order->operation->can_refund || $order->operation->can_refund)--!}
                {{--<a href="/admin/orders/manage/manual/return?order_sn=${order->order_sn!}" target="_blank"--!}
                {{--class="btn-common btn-manual-return">手动退款/退货</a>--!}
                {{--</#if>--!}
                {{--<a href="/admin/orders/manage/info?order_sn=${order->order_sn!}" class="btn-common btn-cancel">查看订单</a>--!}
                {{--<a href="/admin/orders/manage/list" class="btn-common btn-cancel">返回订单列表</a>--!}
                {{--</li>--!}
                <li class="order-info-li clearfix">
                    <div class="fl">
                        <span>订单编号：</span>
                        <input type="text" name="order_sn" placeholder="订单编号" value="${request['order_sn']!}">
                    </div>
                    <div class="fl">
                        <span>退款编号：</span>
                        <input type="text" name="return_order_sn" placeholder="退款编号" value="${request['return_order_sn']!}">
                    </div>
                    <div class="fl" style="width: 350px;">
                        <span>退款状态：</span>
                        <select name="refund_status" id="order_status" style="width:235px">
                            <option value="0" selected>全部</option>
                            <option value="1" <#if ($request['refund_status'] == 1) selected </#if>>退款申请等待商家确认</option>
                            <option value="3" <#if ($request['refund_status'] == 3) selected </#if>>商家拒绝退款申请</option>
                            <option value="2" <#if ($request['refund_status'] == 2) selected </#if>>商家同意退货退款，等待买家退货</option>
                            <option value="41" <#if ($request['refund_status'] == 41) selected </#if>>买家已退货，等待商家确认收货</option>
                            <option value="61" <#if ($request['refund_status'] == 61) selected </#if>>商家未收货，拒绝退款</option>
                            <option value="60" <#if ($request['refund_status'] == 60) selected </#if>>商家拒绝退款申请</option>
                            <option value="7" <#if ($request['refund_status'] == 7) selected </#if>>退款撤销</option>
                            <option value="5" <#if ($request['refund_status'] == 5) selected </#if>>退款成功</option>
                        </select>
                    </div>
                </li>
                <li class="order-info-li clearfix">
                    <div class="fl">
                        <span>退款类型：</span>
                        <select name="return_type" id="order_status" style="width: 175px;">
                            <option value="-1" selected>全部</option>
                            <option value="0" <#if (!is_null($request['return_type']) && $request['return_type'] == 0) selected </#if>>仅退款</option>
                            <option value="1" <#if ($request['return_type'] == 1) selected </#if>>退货（包括退款）</option>
                        </select>
                    </div>
                    <div class="fl" style="width: 590px;">
                        <span>申请时间：</span>
                        <input style="width: 150px; height: 30px;" type="text" name="start_time" value="${request['start_time']!}" placeholder="选择时间" id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input style="width: 150px; height: 30px;" type="text" name="end_time" value="${request['end_time']!}" placeholder="选择时间" id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="##" style="color: #5A8BFF;" class="zuijin" zuijin="7">近7天</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="##" style="color: #5A8BFF;" class="zuijin" zuijin="30">近30天</a>
                    </div>
                    <div class="fl" style="width: 100px;">
                        <div class="btn-choose" style="line-height: 30px;padding-left: 27px">筛选</div>
                    </div>
                </li>
            </ul>
            <div class="return-goods-box order-list">
                <ul class="order-list-nav clearfix tab_nav">
                    <li <#if ($request['nav'] == 0)class="nav-active" </#if> nav = "0">
                        <a href="##">全部</a>
                    </li>
                    <li <#if ($request['nav'] == 1)class="nav-active" </#if>  nav = "1">
                        <a href="##" >商家待处理</a>
                    </li>
                    <li <#if ($request['nav'] == 2)class="nav-active" </#if>  nav = "2">
                        <a href="##" >买家待处理</a>
                    </li>
                    <li <#if ($request['nav'] == 3)class="nav-active" </#if>  nav = "3">
                        <a href="##" >已完成</a>
                    </li>
                </ul>
                {{ csrf_field()!}
                <input type="hidden" name="spec_info"/>
                <div class="order-list-table">
                    <table style="width:100%;" class="return-order-th">
                        <thead>
                        <tr class="order-tb-head">
                            <th style="width: 25%">{{ trans("admin/order_info.order_return_list.goods_name")!}</th>
                            <th style="width: 12%">退款类型</th>
                            <th>{{ trans("admin/order_info.order_return_list.goods_count")!}</th>
                            <th>{{ trans("admin/order_info.order_return_list.goods_price")!}</th>
                            <th width="200px">{{ trans("admin/order_info.order_return_list.reason")!}</th>
                            <th width="100px">{{ trans("admin/order_info.order_return_list.return_status")!}</th>
                            <th>{{ trans("admin/order_info.order_return_list.goods_opration")!}</th>
                        </tr>
                        <tr class="jiange">
                            <td colspan="7"></td>
                        </tr>
                        </thead>

                        <#list ($data_list as $returnOrder)
                            <tbody>
                            <tr class="return-tb-container">
                                <td colspan="7" class="return-status">

                                    <ul>
                                        <li>退款编号：
                                            <span class="text-warning">${returnOrder->return_order_sn!}</span>
                                        </li>
                                        <li>订单编号：
                                            <span class="text-warning">${returnOrder->order_sn!}</span>
                                        </li>
                                        <li>申请时间：<span class="text-warning">
                                                    <#if ($returnOrder->return_type==0)
                                                    ${returnOrder->shipping_or_refund_time!}
                                                <#else>
                                                    ${returnOrder->apply_time!}
                                                </#if>
                                                </span>
                                        </li>
                                        <li>退款金额：<span
                                                    class="text-warning">￥{{ number_format($returnOrder->money,2)!}</span>
                                        </li>
                                        <li>运费退款金额：<span
                                                    class="text-warning">
                                                <#if  ($returnOrder->refund_status == 5)
                                                    ￥{{ number_format($returnOrder->shipping_fee, 2)!}
                                                <#elseif> ($order->order_status == 3)
                                                    ￥{{ number_format($returnOrder->can_return_free, 2)!}
                                                <#else>
                                                    ￥0.00
                                                </#if>
                                            </span>
                                        </li>
                                    </ul>

                                </td>
                            </tr>

                            <#list ($returnOrder->return_order_goods as $i => $return_order_good)
                                <tr class="order-tb-body">
                                    <td>
                                        <div class="order-goods-info clearfix">
                                            <div class="fl">
                                                <img src="${return_order_good->goods_img!}"/>
                                            </div>
                                            <div class="fr">
                                                <p>
                                                    ${return_order_good->goods_name!}
                                                </p>
                                                <div>${return_order_good->goods_attr!}</div>
                                            </div>
                                        </div>


                                    </td>
                                    <td>${returnOrder->return_type==0 ? "仅退款" :"退货且退款"!} </td>
                                    <td width="100px">${return_order_good->goods_number!}</td>
                                    <td width="100px">${return_order_good->goods_price!}</td>

                                    <#if ($i == 0)
                                        <td width="200px" rowspan="{{ count($returnOrder->return_order_goods)!}">
                                            ${returnOrder->reason!}
                                        </td>
                                        <td width="200px" rowspan="{{ count($returnOrder->return_order_goods)!}">
                                            <div style="width: 200px;word-break: break-all">
                                                {{--${returnOrder->return_desc!}--!}
                                                <#if ($returnOrder->refund_status == 1 || ($returnOrder->refund_status==4 && $returnOrder->return_type==0)) 退款申请等待商家确认
                                                <#elseif>($returnOrder->refund_status == 3) 商家拒绝退款申请
                                                <#elseif>($returnOrder->refund_status == 2) 商家同意退货退款，等待买家退货
                                                <#elseif>($returnOrder->refund_status == 4)
                                                    <#if ($returnOrder->return_type==1)买家已退货，等待商家确认收货
                                                    <#else> 退款申请等待商家确认
                                                    </#if>
                                                <#elseif>($returnOrder->refund_status == 6)
                                                    <#if ($returnOrder->return_type==1)商家未收货，拒绝退款
                                                    <#else> 商家拒绝退款申请
                                                    </#if>
                                                <#elseif>($returnOrder->refund_status == 7) 退款撤销
                                                <#elseif>($returnOrder->refund_status == 5) 退款成功
                                                </#if>
                                            </div>
                                        </td>
                                        {{--<td width="100px" rowspan="{{ count($returnOrder->return_order_goods)!}">--!}
                                        {{--<#if ($returnOrder->goods_images)--!}
                                        {{--<img src="${returnOrder->goods_images!}"--!}
                                        {{--style="max-width: 50px;max-height: 50px;">--!}
                                        {{--</#if>--!}
                                        {{--</td>--!}
                                        <td width="150px" class="order-status"
                                            rowspan="{{ count($returnOrder->return_order_goods)!}"
                                            order_sn="${returnOrder->order_sn!}"
                                            ret_id="${returnOrder->ret_id!}">
                                            <a class="btn-common  ${returnOrder->refund_status == 1 || $returnOrder->refund_status == 4 ? "" : "btn-cancel"!}"
                                               href="/admin/orders/manage/return/detail?order_sn=${returnOrder->order_sn!}&ret_id=${returnOrder->ret_id!}&sub_index=${sub_index!}" style="width: 80px;">
                                                <#if ($returnOrder->refund_status == 1)审批申请
                                                <#elseif>($returnOrder->refund_status == 4)处理退款
                                                <#else>
                                                    查看详情
                                                </#if>
                                            </a>
                                        </td>
                                    </#if>
                                </tr>
                            </#list>
                            </tbody>
                            <tbody style="border: none">
                            <tr class="jiange">
                                <td colspan="7" style="border: none"></td>
                            </tr>
                            </tbody>
                        </#list>
                    </table>
                </div>
                <div class="clearfix">
                    <#if ($data_list ->count())
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
                    <#else>
                        <div style="width: 100%;padding: 0px 0px 0px 0px;background: #fff;">
                            <div style="border: 1px solid #eee;">
                                <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                </div>
                                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                            </div>
                        </div>
                    </#if>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
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
    $('.zuijin').click(function () {
        var _this = $(this);
        if(_this.attr("zuijin") == 7){
            $("input[name='start_time']").val(fun_date(-7)[1]);
            $("input[name='end_time']").val(fun_date(-7)[0]);
        }else if(_this.attr("zuijin") == 30){
            $("input[name='start_time']").val(fun_date(-30)[1]);
            $("input[name='end_time']").val(fun_date(-30)[0]);
        }
    })
    function fun_date(aa){
        var date1 = new Date(),
            time1=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();//time1表示当前时间
        var date2 = new Date(date1);
        date2.setDate(date1.getDate()+aa);
        var time2 = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
        return [time1,time2];
    }
    $(".btn-choose").click(function(){
        $("#form1").submit();
    })
    $(".tab_nav li").click(function () {
        var _this = $(this);
        $("input[name='nav']").val(_this.attr("nav"));
        $("#form1").submit();
    })
</script>
<#include ("system.footer")