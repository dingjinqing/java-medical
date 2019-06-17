<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/reserve_detail.css?v=1.0.0" type="text/css"/>
<div class="title">
    <span>预约管理 / </span><span style="color: #666;">预约服务详情</span>
</div>
<div class="service-order-container">
    <form name="formData" id="form1" method="post">
        {{ csrf_field()!}
        <div class="since-info">
            <div class="since-info-top">
                <span>订单号：${data_list->order_sn!}</span>
                <span>订单状态：
                    <#if ($data_list->order_status==0)
                        待服务
                    <#elseif>($data_list->order_status==1)
                        已取消
                    <#elseif>($data_list->order_status==2)
                        已完成
                    </#if>
                </span>
            </div>
            <div class="since-info-path">
                <ul class="clearfix">
                    <li <#if ($data_list->order_status==0 or $data_list->order_status==2) class="since-info-li1" </#if>>买家已预约</li>
                    <li class="since-info-li{{ ($data_list->order_status == 2) ? 5: 2!}">订单完成</li>
                </ul>
            </div>
            <div class="since-info-detail clearfix">
                <div class="fl">
                    <div class="since-order">订单信息</div>
                    <div class="order-flex">
                        <div>订单状态：
                            <#if ($data_list->order_status==0)
                                待服务
                            <#elseif>($data_list->order_status==1)
                                已取消
                            <#else>
                                已完成
                            </#if>
                        </div>
                        {{--<div>订单金额：${data_list->service_subsist!}</div>--!}
                        <div>订单金额：${data_list->service_price!}</div>
                    </div>
                    <div class="order-flex">
                        <div>预约到店时间：${data_list->service_date!} ${data_list->service_period!}</div>
                        <div>门店：${storeinfo->store_name!}</div>
                    </div>
                    <div class="order-flex">
                        <#if (!empty($data_list->pay_name))
                        <div>支付方式：${data_list->pay_name!}</div>
                        <div>预约${technician_title!}：<#if ($data_list->technician_id != 0) ${data_list->technician_name!} <#else> 未指定${technician_title!} </#if></div>
                        </#if>
                        <div>订单号：${data_list->order_sn!}</div>
                        <div>预约${technician_title!}：<#if ($data_list->technician_id != 0) ${data_list->technician_name!} <#else> 未指定${technician_title!} </#if></div>
                    </div>
                </div>
                <div class="fl">
                    <div class="since-order">用户信息</div>
                    <div class="order-flex">
                        <div>预约人：${data_list->subscriber!}</div>
                        <div>手机号码：${data_list->mobile!}</div>
                    </div>
                    <div class="order-flex">
                        <div width="100%">买家留言：${data_list->add_message!}</div>
                    </div>
                    <div class="order-flex" <#if ($data_list->order_status != 1) hidden </#if>>
                        <div width="100%">取消原因：${data_list->cancel_reason!}</div>
                    </div>
                </div>
            </div>
            <div class="order-remark">
                <img src="http://${image_domain!}/image/admin/since-edit.png" class="add_text" style="cursor: pointer" alt=""/>
                卖家备注：<span class="seller-remark">${data_list->admin_message!}</span>
            </div>
        </div>
        <div class="order-list-table">
            <table style="width: 100%">
                <thead>
                    <tr>
                        <td width="40%">服务</td>
                        {{--<td>订金</td>--!}
                        <#if ($data_list->money_paid > 0)
                            <td>订金</td>
                        </#if>
                        <#if (!empty($data_list->technician_name))
                            <td>${technician_title!}</td>
                        </#if>
                        <td>总价</td>
                        <td>订单状态</td>
                    </tr>
                    <tr class="jiange">
                        <td colspan="8"></td>
                    </tr>
                </thead>
                <tbody>
                    <tr class="order-tb-body">
                        <td width="40%">
                            <div class="order-goods-info clearfix">
                                <div class="fl">
                                    <img src='${img!}'/>
                                </div>
                                <div class="fr">
                                    <p>
                                       ${data_list->service_name!}
                                    </p>
                                </div>
                            </div>
                        </td>
                        {{--<td>${data_list->money_paid!}</td>--!}
                        <#if ($data_list->money_paid > 0)
                            <td>${data_list->money_paid!}</td>
                        </#if>
                        <#if (!empty($data_list->technician_name))
                            <td>${data_list->technician_name!}</td>
                        </#if>
                        <td>${data_list->service_price!}</td>
                        <td><#if ($data_list->order_status==0)
                                待服务
                            <#elseif>($data_list->order_status==1)
                                已取消
                            <#else>
                                已完成
                            </#if></td>
                    </tr>
                    <tr>
                        <td colspan="4" class="price_amount">
                            实收款：<span class="text-amount amount-sum">￥${data_list->service_price!}</span>
{{--                            实收款：<span class="text-amount amount-sum">￥${data_list->money_paid!}</span>--!}
                            <#if ($data_list->order_status == 0)
                            <a href="##" class="btn_finish" order_id="${data_list->order_id!}" store_id="${store_id!}" order_sn="${data_list->order_sn!}">确定完成</a>
                                </#if>
                        </td>

                    </tr>
                </tbody>

            </table>
        </div>

        <#if (!empty($data_list->finished_time))
        <div class="order-list-table since-cancel">
            <table style="width: 70%">
                <thead>
                    <tr>
                        <td width="150px">核销码</td>
                        <td width="150px">核销人</td>
                        <td width="150px">核销时间</td>
                        <td width="150px">核销方式</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td width="150px">${data_list->verify_code!}</td>
                        <td width="150px">
                            <#if ($data_list->verify_type == 1)
                                <a href="/admin/user/manage/center?user_id=${data_list->user_id!}&top_index=5&sub_index=0">${data_list->verify_admin!}</a>
                            <#else>
                                ${data_list->verify_admin!}
                            </#if>
                        </td>
                        <td width="150px">${data_list->finished_time!}</td>
                        <td width="150px">
                            <#if ($data_list->verify_pay == 0) 门店买单
                            <#elseif>($data_list->verify_pay == 1)会员卡
                            <#elseif>($data_list->verify_pay == 2)账户余额</#if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        </#if>
    </form>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    var store_id = ${data_list->store_id!};
    var order_sn = '${data_list->order_sn!}';
    var order_id = ${data_list->order_id!};
</script>
<script language="JavaScript" src="/js/admin/reserve_detail.js?v=1.1.1"></script>

