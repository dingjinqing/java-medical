<#include ("system.header")
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
        <div class="return-goods-box">
            <form name="formData" action="/admin/orders/manage/manual/return" id="form1" method="post">
                {{ csrf_field()!}
                <input type="hidden" name="order_sn" value="${order->order_sn!}"/>
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table style="width: 100%" class="return-order-th">
                            <thead>
                            <tr>
                                <th width="50px">选中</th>
                                <th width="200px">商品名称</th>
                                <th>规格</th>
                                <th>商品价格</th>
                                <th>用户购买折后价格</th>
                                <th width="200px">可退货数量/已提交/总数量</th>
                                <th width="200px">退款/退货数量</th>
                                <th width="100px">退货商品折后总计金额</th>
                            </tr>
                            </thead>
                            <#list ($orderGoods as $orderGood)
                                <tr product_id="${orderGood->product_id!}"
                                    max_return_number="${orderGood->max_return_number!}"
                                    goods_price="${orderGood->goods_price!}"
                                    discounted_goods_price="${orderGood->discounted_goods_price!}"
                                    max_return_money="${orderGood->max_return_money!}">
                                    <td>
                                        <input type="checkbox" name="cbk_goods[]" value="${orderGood->product_id!}">
                                    </td>
                                    <td>${orderGood->goods_name!}</td>
                                    <td>${orderGood->goods_attr!}</td>
                                    <td>${orderGood->goods_price!}</td>
                                    <td>${orderGood->discounted_goods_price!}</td>
                                    <td width="200px">${orderGood->goods_number -$orderGood->apply_number!}
                                        /${orderGood->apply_number!}/${orderGood->goods_number!}
                                    </td>
                                    <td width="200px" class="form-inline">
                                        <input type="text" size="5" class="form-control text-center return_number" name="return_number[${orderGood->product_id!}]"
                                               value="${orderGood->max_return_number!}">
                                    </td>
                                    <td width="200px">
                                        <span class="goods-amount">{{ number_format( $orderGood->max_return_money,2,".","")!}</span>
                                    </td>
                                </tr>
                            </#list>
                        </table>


                        <div id="return-info">
                            <table>
                                {{--<tr>--!}
                                    {{--<td>--!}
                                        {{--手动类型：--!}
                                    {{--</td>--!}
                                    {{--<td class="form-inline">--!}
                                        {{--<select name="manual_type" id="manual_type" class="form-control">--!}
                                            {{--<option value="0">自动申请和退款退货</option>--!}
                                            {{--<option value="1">仅申请</option>--!}
                                        {{--</select>--!}
                                    {{--</td>--!}
                                {{--</tr>--!}
                                <tr>
                                    <td>
                                        退款/退货类型：
                                    </td>
                                    <td class="form-inline">
                                        <select name="return_type" id="return_type" class="form-control">
                                            <#if ($order->operation->can_return)
                                                <option value="1">退款且退货</option> </#if>
                                            <option value="0">仅退款</option>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        退款/退货金额：
                                    </td>
                                    <td class="form-inline" style="line-height: 24px;">

                                        <div class="refund-info">
                                            退商品金额：<input type="text" name="refund_money" id="refund_money" class="form-control"
                                                         style="width: 100px;text-align: center" value="0.00"
                                                         max_discounted_money="0"/>&nbsp;元，
                                            退运费金额：<input type="text" name="shipping_fee" id="shipping_fee"
                                                         maxlength="20" class="form-control"
                                                         style="width: 100px;text-align: center" value="0.00">&nbsp;元，
                                            可退最大运费：{{ number_format($can_refund['shipping_fee'],2,".","")!}元
                                            <br/>
                                            <div style="color: #666;">
                                                总退款金额：￥<span class="text-warning refund-money">0.00</span> =
                                                退余额：￥<span class="text-warning refund-balance-money">0.00</span> +
                                                退积分抵扣：￥<span class="text-warning refund-score-money">0.00</span> +
                                                退支付金额：￥<span class="text-warning refund-pay-money">0.00</span>
                                            </div>
                                        </div>
                                        <p class="text-warning">
                                            注：总退款金额 = 退商品金额 + 退运费金额，扣款优先级：余额，积分，支付金额
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
<script src="/js/admin/order_manual_return.js?v=1.0.1" type="text/javascript"></script>

<#include ("system.footer")