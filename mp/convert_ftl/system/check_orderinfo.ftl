<#include ("system.header")
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.27" type="text/css"/>
<link rel="stylesheet" href="/css/admin/check_order.css?v=1.0.3" type="text/css"/>
<div class="title">
    <span>订单管理 / </span><span style="color: #666;">买单订单详情</span>
</div>
<div class="checkinfo_container">
    <div class="checkorder_info">
        <div class="checkorder_info_top">订单号：</div>
        <div class="checkorder_info_detail clearfix">
            <div class="fl">
                <div class="title_content">订单信息</div>
                <div class="order_flex">
                    <div>订单金额：</div>
                </div>
                <div class="order_flex">
                    <div>买单时间：</div>
                </div>
                <div class="order_flex">
                    <div>支付方式：微信支付</div>
                </div>
                <div class="order_flex">
                    <div>买单门店：</div>
                </div>
            </div>
            <div class="fl">
                <div class="title_content">买家信息</div>
                <div class="order_flex">
                    <div>买单客户：</div>
                </div>
                <div class="order_flex">
                    <div>客户留言：</div>
                </div>
                
                    <div class="order-flex" style="height: 88px;">
                        
                            <div>
                                <div style="margin: 0 0 10px 0px;">
                                    <span style="margin-right: 73px">发票信息：发票类型：企业发票</span>
                                </div>
                                <div style="margin: 0 0 10px 60px;">发票抬头：</div>
                                
                                    <div style="margin: 0 0 10px 60px;">公司税号：</div>
                                
                                
                                    <div style="margin: 0 0 0px 60px;">公司地址：</div>
                                
                            </div>
                        
                        
                            <div>
                                <div>
                                    <span style="margin-right: 73px">发票信息： 发票类型：个人发票</span>
                                </div>
                                <div style="margin: 0 0 10px 60px;">发票抬头：</div>
                            </div>
                        
                    </div>
                
            </div>
    </div>
    <div class="order_notice">
        <img src="http:///image/admin/since-edit.png" class="add_text" style="cursor: pointer"  seller_remark="" alt="">
        卖家备注：<span class="seller-remark"></span>
    </div>
    <div class="pay_detail" >
        <div class="pd_titile">支付明细</div>
        <div class="pd_detail_list">
            <div style="padding-bottom: 0">
                <span>优惠额度：</span>
                <span>会员卡折扣- 元</span>
            </div>
            <div style="padding: 0;">
                <span></span>
                <span>会员卡抵扣- 元</span>
            </div>
            <div style="padding: 0;">
                <span></span>
                <span>积<item style="margin-right: 42px;"></item>分- 元</span>
            </div>
            <div style="padding: 0;">
                <span></span>
                <span>余<item style="margin-right: 42px;"></item>额- 元</span>
            </div>
            <div class="">
                <span>总价：</span>
                <span> 元</span>
            </div>
            <div>
                <span>实收款：</span>
                <span style="color: #f66;">元</span>
            </div>
        </div>
    </div>
</div>

<script src="/js/admin/check_order.js?v=1.0.4" type="text/javascript"></script>
<#include ("system.footer")