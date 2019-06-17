<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/check_order.css?v=1.0.3" type="text/css"/>
<div class="title">
    <span>订单管理 / </span><span style="color: #666;">买单订单详情</span>
</div>
<div class="checkinfo_container">
    <div class="checkorder_info">
        <div class="checkorder_info_top">订单号：${order->order_sn!}</div>
        <div class="checkorder_info_detail clearfix">
            <div class="fl">
                <div class="title_content">订单信息</div>
                <div class="order_flex">
                    <div>订单金额：${order->money_paid!}</div>
                </div>
                <div class="order_flex">
                    <div>买单时间：${order->pay_time!}</div>
                </div>
                <div class="order_flex">
                    <div>支付方式：微信支付</div>
                </div>
                <div class="order_flex">
                    <div>订单状态：${order->order_status_name!}</div>
                </div>
                <div class="order_flex">
                    <div>买单门店：${store->store_name!}</div>
                </div>
            </div>
            <div class="fl">
                <div class="title_content">买家信息</div>
                <div class="order_flex">
                    <div>买单客户：${user->username!}</div>
                </div>
                <div class="order_flex">
                    <div>客户留言：${order->add_message!}</div>
                </div>
                <#if ($invoiceInfo != "")
                    <div class="order-flex" style="height: 88px;">
                        <#if ($invoiceInfo->type == 0)
                            <div>
                                <div style="margin: 0 0 10px 0px;">
                                    <span style="margin-right: 73px">发票信息：发票类型：企业发票</span>
                                </div>
                                <div style="margin: 0 0 10px 60px;">发票抬头：${invoiceInfo->title!}</div>
                                <#if ($invoiceInfo->taxNumber)
                                    <div style="margin: 0 0 10px 60px;">公司税号：${invoiceInfo->taxNumber!}</div>
                                </#if>
                                <#if ($invoiceInfo->companyAddress)
                                    <div style="margin: 0 0 0px 60px;">公司地址：${invoiceInfo->companyAddress!}</div>
                                </#if>
                            </div>
                        </#if>
                        <#if ($invoiceInfo->type == 1)
                            <div>
                                <div>
                                    <span style="margin-right: 73px">发票信息： 发票类型：个人发票</span>
                                </div>
                                <div style="margin: 0 0 10px 60px;">发票抬头：${invoiceInfo->title!}</div>
                            </div>
                        </#if>
                    </div>
                </#if>
            </div>
    </div>
    <div class="order_notice">
        <img src="http://${image_domain!}/image/admin/since-edit.png" class="add_text" style="cursor: pointer"  seller_remark="" alt="">
        卖家备注：<span class="seller-remark">${order->seller_remark!}</span>
    </div>
    <div class="pay_detail" >
        <div class="pd_titile">支付明细 <a href="##" class="manual-return" data-order-sn="${order->order_sn!}" <#if  (!$order->can_return) hidden </#if>>手动退款</a></div>
        <div class="pd_detail_list">
            <div style="padding-bottom: 0">
                <span>优惠额度：</span>
                <span>会员卡折扣-${order->member_card_redunce!} 元</span>
            </div>
            <div style="padding: 0;">
                <span></span>
                <span>会员卡抵扣-${order->member_card_balance!} 元</span>
            </div>
            <div style="padding: 0;">
                <span></span>
                <span>积<item style="margin-right: 42px;"></item>分-${order->score_discount!} 元</span>
            </div>
            <div style="padding: 0;">
                <span></span>
                <span>余<item style="margin-right: 42px;"></item>额-${order->use_account!} 元</span>
            </div>
            <div class="">
                <span>总价：</span>
                <span>${order->order_amount!} 元</span>
            </div>
            <div>
                <span>实收款：</span>
                <span style="color: #f66;">${order->money_paid!}元</span>
            </div>
        </div>
    </div>
</div>
<div class="manual-return-module" hidden>
    <div class="pd_detail_list" style="padding: 0;">
        <div <#if  (!($order->member_card_balance > 0)) hidden </#if>>
            <span></span>
            <span>会员卡抵扣：${order->member_card_balance!} 元</span>
        </div>
        <div <#if  (!($order->score_discount > 0)) hidden </#if>>
            <span></span>
            <span>积<item style="margin-right: 42px;"></item>分：${order->score_discount * 100!} 积分</span>
        </div>
        <div <#if  (!($order->use_account > 0)) hidden </#if>>
            <span></span>
            <span>余<item style="margin-right: 42px;"></item>额：${order->use_account!} 元</span>
        </div>
        <div <#if  (!($order->money_paid > 0)) hidden </#if>>
            <span></span>
            <span>微信支付：${order->money_paid!}元</span>
        </div>
    </div>
</div>

<script src="{{asset('js/admin/check_order.js')!}?v=1.0.6" type="text/javascript"></script>
<#include "/admin/footer.ftl">