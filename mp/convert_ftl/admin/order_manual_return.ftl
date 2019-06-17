<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<style type="text/css">
    .btn-common:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }
    .btn-common:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        color: #fff;
        text-decoration: none
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/order_info.order_manage")!} / </span><span
                style="color: #666;">手动退货退款
        </span>
    </div>
    <div class="main-container">
        <ul class="order-center">
            <li>订单号：${order->order_sn!}</li>
            <li>下单时间：${order->add_time!}</li>
            <li><a href="/admin/orders/manage/return/list?order_sn=${order->order_sn!}" class="btn-common">返回退款退货列表</a></li>
        </ul>
{{--        {{dd($can_refund)!}--!}
        <div class="return-goods-box">
            <form name="formData" action="/admin/orders/manage/manual/return" id="form1" method="post">
                {{ csrf_field()!}
                <input type="hidden" name="order_sn" value="${order->order_sn!}"/>
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="return-order-th" style="width: 100%;<#if  (!$order->operation->can_refund
                        && !$order->operation->can_return && !$order->operation->can_return_more) display: none; </#if>" >
                            <thead>
                            <tr>
                                <th width="50px">选中</th>
                                <th width="200px">商品名称</th>
                                <th>规格</th>
                                <th>商品价格</th>
                                <th>购买折后价格</th>
                                <th width="200px">可退货数量/已提交/总数量</th>
                                <th width="200px" class="return_goods_td" style="display: none;">退款/退货数量</th>
                                <th width="100px" class="return_goods_td" style="display: none;">退货商品折后总计金额</th>

                                <th class="return_more_td" style="display: none;">已退金额</th>
                                <th class="return_more_td" style="display: none;">最多可退金额</th>
                                <th class="return_more_td" style="display: none;">退款金额</th>
                            </tr>
                            </thead>
                            <#list ($orderGoods as $orderGood)
                                <tr product_id="${orderGood->product_id!}"
                                    max_return_number="${orderGood->max_return_number!}"
                                    goods_price="${orderGood->goods_price!}"
                                    discounted_goods_price="${orderGood->discounted_goods_price!}"
                                    max_return_money="${orderGood->max_return_money!}">
                                    <td>
                                        <input type="checkbox" class="goods_checked" name="cbk_goods[]" value="${orderGood->product_id!}">
                                    </td>
                                    <td>
                                        <#if  ($orderGood->is_gift)
                                        <span style="border: 1px red solid;color:red;">赠品</span>
                                        </#if>
                                        ${orderGood->goods_name!}
                                    </td>
                                    <td>${orderGood->goods_attr!}</td>
                                    <td>${orderGood->goods_price!}</td>
                                    <td>${orderGood->discounted_goods_price!}</td>

                                    <td width="200px">
                                        ${orderGood->goods_number -$orderGood->apply_number!}
                                        /${orderGood->apply_number!}/${orderGood->goods_number!}
                                    </td>
                                    <td width="200px" class="return_goods_td form-inline" style="display: none;">
                                        <input type="text" size="5" class="form-control text-center return_number"
                                               name="return_number[${orderGood->product_id!}]"
                                               value="${orderGood->max_return_number!}">
                                    </td>
                                    <td width="200px" class="return_goods_td" style="display: none;">
                                        <span class="goods-amount">{{ number_format( $orderGood->max_return_money,2,".","")!}</span>
                                    </td>


                                    <td class="return_more_td" style="display: none;">${orderGood->has_return_money!}</td>
                                    <td class="return_more_td" style="display: none;">{{ number_format($orderGood->can_return_more_money, 2)!}</td>
                                    <td class="return_more_td" style="display: none;">
                                        <input type="text" name="return_more_money[${orderGood->product_id!}]"
                                               class="form-control text-center return_more_money"
                                               can_return_more_money="${orderGood->can_return_more_money!}"
                                               value="{{ number_format($orderGood->can_return_more_money, 2)!}">
                                    </td>
                                </tr>
                            </#list>
                        </table>

                        <div id="return-info">
                            <table>
                                <tr>
                                    <td>
                                        退款/退货类型：
                                    </td>
                                    <td class="form-inline">
                                        <select name="return_type" id="return_type" class="form-control">
                                            <#if ($order->operation->can_return)
                                                <option value="1">${returnTypeName[1]!}</option>
                                            </#if>
                                            <#if ($order->operation->can_refund)
                                                <option value="0">${returnTypeName[0]!}</option>
                                            </#if>
                                            <#if ($order->operation->can_return_more)
                                                <option value="3">${returnTypeName[3]!}</option>
                                            </#if>
                                            <#if ($order->operation->can_return_ship_fee)
                                                <option value="2">${returnTypeName[2]!}</option>
                                            </#if>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        退款/退货金额：
                                    </td>
                                    <td class="form-inline" style="line-height: 24px;">

                                        <div class="refund-info">
                                            <span class="goods_money_module" style="<#if  (!$order->operation->can_return_more
                                            && !$order->operation->can_return && !$order->operation->can_refund) display: none; </#if>">
                                                退商品金额：<input type="text" name="refund_money" id="refund_money" class="form-control"
                                                             style="width: 100px;text-align: center" value="0.00"
                                                             max_discounted_money="0"/>&nbsp;元，
                                            </span>
                                            <span class="shipping_fee_module">
                                                退运费金额：<input type="text" name="shipping_fee" id="shipping_fee"
                                                             maxlength="20" class="form-control"
                                                             style="width: 100px;text-align: center" value="0.00">&nbsp;元，
                                                可退最大运费：{{ number_format($can_refund['shipping_fee'],2,".","")!}元
                                            </span>
                                            <br/>
                                            <div style="color: #666;">
                                                总退款金额：￥<span class="text-warning refund-money">0.00</span> =
                                                <#if ($order->bk_order_money > 0)退定金尾款：￥<span class="text-warning refund-bk-order-money">0.00</span> +</#if>
                                                退会员卡余额：￥<span class="text-warning refund-member-card-money">0.00</span> +
                                                退余额：￥<span class="text-warning refund-balance-money">0.00</span> +
                                                退积分抵扣：￥<span class="text-warning refund-score-money">0.00</span> +
                                                退支付金额：￥<span class="text-warning refund-pay-money">0.00</span>
                                            </div>
                                        </div>
                                        <p class="text-warning">
                                            注：总退款金额 = 退商品金额 + 退运费金额，扣款优先级： <#if ($order->bk_order_money > 0)定金尾款，</#if>员卡余额，余额，积分，支付金额
                                        </p>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        退款/退货原因：
                                    </td>
                                    <td class="form-inline">
                                        <select name="reason" class="form-control">
                                            <#list ($reason_list as $item)
                                                <option value="${item!}">${item!}</option>
                                            </#list>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        退款/退货原因说明：
                                    </td>
                                    <td>
                                        <textarea name="return_desc" class="form-control" id="return_desc" placeholder="请输入退款/退货原因说明"
                                                  style="width: 300px;height: 80px"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        凭证图片：
                                    </td>
                                    <td>
                                        <input type="hidden" name="goods_images">
                                        <img src="" class="goods_images" style="max-width: 50px;max-height: 50px">
                                        <input type="button" name="btn-add-image" id="btn-add-image" value="添加图片">
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="2">
                                        <button type="button" class="btn-common btn-return">确定</button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var can_refund = @json($can_refund);
</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="{{asset('js/admin/order_manual_return.js')!}?v=1.0.6" type="text/javascript"></script>

<#include "/admin/footer.ftl">