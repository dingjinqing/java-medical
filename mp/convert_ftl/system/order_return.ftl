<#include ("system.header")
<link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<style type="text/css">
    .btn-common:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-common:focus {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .order-basic-table tr td:first-child{
        text-align: center;
        color: #666;
    }
    .order-basic-table{
        margin-top: 0;
        font-size: 14px;
    }
    .h2_title{
        font-size: 14px;
        padding-left: 12px;
        background: #f5f5f5;
        line-height: 36px;
        border: 1px solid #ddd;
        border-bottom: 0;
        color: #333;
        font-weight:600;
    }
    .refund_status_desc{
        border: 1px solid #ddd;
        padding: 16px 30px;
        position: relative;
        margin-bottom: 10px;
    }
    .refund_status_desc h3{
        font-size: 16px;
        font-weight: 600;
        color: #333;
    }
    .refund_status_desc div{
        font-size: 14px;
        color: #666;
    }
    .refund_status_desc span{
        color: #ff785f;
    }
    .refund_status_desc .btn_box{
        position: absolute;
        right: 30px;
        top: 50%;
        transform: translateY(-50%);
    }
    .refund_status_desc .btn-1{
        display: inline-block;border: 1px solid #5a8bff;background: #5a8bff;color: #fff;margin-right: 20px; height: 30px;line-height: 30px;padding:0 14px;font-size: 14px;border-radius: 3px;
    }
    .refund_status_desc .btn-2{
        display: inline-block;border: 1px solid #ccc;color: #333;height: 30px;line-height: 30px;padding:0 14px;font-size: 14px;border-radius: 3px;
    }
    .top{
        border-bottom: 1px dashed #ccc;
        padding-bottom: 10px;
    }
    .top ul li{
        display: inline-block;
        width: 200px;
        text-align: left;
    }
    .bottom{
        padding: 10px;
    }
    .recode{
        border: 1px solid #ccc;
        padding: 10px;
        border-top:none;
    }
    strong{
        font-weight: 400;
        color: #333;
    }
    .text-warning{
        color: #ff6600
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/order_info.order_manage")!} / </span><span
                style="color: #666;">
             <#if ($return_order->refund_status == 1)退款且退货申请
            <#elseif>($return_order->refund_status == 2)等待退货物流
            <#elseif>($return_order->refund_status == 3)退货且退款详情
            <#elseif>($return_order->refund_status == 4) ${return_order->return_type == 0 ? "退款申请":"收货确认"!}
            <#elseif>($return_order->refund_status == 5)退款详情
            <#elseif>($return_order->refund_status == 6)退款详情
            </#if>
        </span>
    </div>
    <div class="main-container">
        <div class="goods-box">
            <input type="hidden" name="act" id="act"/>
            <input type="hidden" name="order_sn" id="order_sn" value="${order->order_sn!}"/>
            <input type="hidden" name="ret_id" id="ret_id" value="${return_order->ret_id!}"/>
            <input type="hidden" name="return_type" id="return_type" value="${return_order->return_type!}"/>
            <input type="hidden" name="refund_status" id="refund_status"
                   value="${return_order->refund_status!}"/>
            <input type="hidden" name="reason" id="reason" value=""/>
            <input type="hidden" name="return_desc" id="return_desc" value=""/>

            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <h2 class="h2_title"><#if ($return_order->return_type == 0)退款申请<#else>退货且退款申请</#if></h2>
                    <div class="refund_status_desc" onload="show_time()">
                        <#if ($return_order->return_type == 1)
                            <#if ( $return_order->refund_status == 1)
                                <div style="display: inline-block">
                                    <h3>等待商家处理退款申请</h3>
                                    <div style="margin-top:5px;">如果您同意，需要您发送退货地址给买家。</div>
                                    <div>如果您拒绝，将需要您填写拒绝理由。</div>
                                    <#if ($auto_return)
                                        <div>请在<span class="return_address_days" onload="show_time()" id="count_down">07天00小时00分00秒</span>内处理本次退款申请，如果逾期处理，系统将自动发送您的默认退货地址。</div>
                                    </#if>
                                </div>
                                <div style="display: inline-block" class="btn_box">
                                    <div class="btn-1 btn-common btn-pass" style="cursor:pointer;">同意退货，发送退货地址</div>
                                    <div class="btn-2 btn-close btn-not-pass " style="cursor:pointer;">拒绝退款申请</div>
                                </div>
                            <#elseif>( $return_order->refund_status == 2)
                                <div style="display: inline-block">
                                    <h3>商家同意退货退款</h3>
                                    <div style="margin-top:5px;">您已同意退货退款，请等待买家处理。</div>
                                    <#if ($auto_return)
                                        <div>买家在<span class="revoke" onload="show_time()" id="count_down">07天00小时00分00秒</span>内未响应，且商家未确认收货并退款，退款申请将自动完成。</div>
                                    </#if>
                                </div>
                                <div style="display: inline-block" class="btn_box">
                                    <div class="btn-1 btn-refund" style="cursor:pointer;">确认收货并退款</div>
                                    <div class="btn-2 btn-refuse-refund btn-cancel" style="cursor:pointer;">拒绝确认收货</div>
                                </div>
                            <#elseif>( $return_order->refund_status == 3)
                                <h3>商家拒绝退款申请</h3>
                            <#elseif>($return_order->refund_status == 4)
                                <div style="display: inline-block">
                                    <h3>买家已退货，等待商家确认收货并退款</h3>
                                    <div style="margin-top:5px;">买家已退货，物流公司：${return_order->shipping_type!}；物流单号：${return_order->shipping_no!}</div>
                                    <#if ($auto_return)
                                        <div>请在<span class="return_shopping_days" onload="show_time()" id="count_down">07天00小时00分00秒</span>内确认，如逾期未处理，将自动退款给买家。</div>
                                    </#if>
                                </div>
                                <div style="display: inline-block" class="btn_box">
                                    <div class="btn-1 btn-refund" style="cursor:pointer;">确认收货并退款</div>
                                    <div class="btn-2 btn-cancel btn-refuse-refund " style="cursor:pointer;">拒绝确认收货</div>
                                </div>
                            <#elseif>( $return_order->refund_status == 5)
                                <h3>商家同意退款，退款成功</h3>
                            <#elseif>($return_order->refund_status == 6)
                                <h3>商家未收货，拒绝退货退款</h3>
                            <#elseif>( $return_order->refund_status == 7 && $return_order->operator == 1)
                                <h3>买家主动撤销退款申请</h3>
                            <#elseif>($return_order->refund_status == 7 && $return_order->operator == 2)
                                <h3>撤销退款申请（系统自动处理）</h3>
                                <div>买家未在商家审核退款申请之后7日内，提交退货物流（或商家未确认收货），退款申请已自动撤销</div>
                            </#if>
                        <#elseif>($return_order->return_type == 0)
                            <#if ($return_order->refund_status == 4)
                                <div style="display: inline-block">
                                    <h3>等待商家处理退款申请</h3>
                                    <div style="margin-top:5px;">如果您同意，需要您退款给买家。</div>
                                    <div>如果您拒绝，将需要您填写拒绝理由。</div>
                                    <#if ($auto_return)
                                        <div>请在<span class="return_money_days" onload="show_time()" id="count_down">07天00小时00分00秒</span>内处理本次退款申请，如果逾期处理，系统将自动退款。</div>
                                    </#if>
                                </div>
                                <div style="display: inline-block" class="btn_box">
                                    <div class="btn-1 btn-common btn-pass" style="cursor:pointer;">同意买家退款</div>
                                    <div class="btn-2 btn-close btn-not-pass" style="cursor:pointer;">拒绝退款申请</div>
                                </div>
                            <#elseif>( $return_order->refund_status == 5)
                                <h3>商家同意退款，退款成功</h3>
                            <#elseif>($return_order->refund_status == 6)
                                <h3>商家拒绝退款申请</h3>
                            <#elseif>( $return_order->refund_status == 7 && $return_order->operator == 1)
                                <h3>买家主动撤销退款申请</h3>
                            <#elseif>($return_order->refund_status == 7 && $return_order->operator == 2)
                                <h3>撤销退款申请（系统自动处理）</h3>
                                <div>买家未在商家审核退款申请之后7日内，提交退货物流（或商家未确认收货），退款申请已自动撤销</div>
                            </#if>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <h2 class="h2_title"><#if ($return_order->return_type == 0)退款申请详情<#else>退货且退款申请详情</#if></h2>
                    <table class="order-basic-table table table-bordered">
                        <tr>
                            <td style="width: 150px;">{{ trans("admin/order_info.order_code")!}：</td>
                            <td>${order->order_sn!}</td>
                            <td style="width: 150px;color:#666;text-align: center;">退款编号：</td>
                            <td>${return_order->return_order_sn!}</td>
                        </tr>

                        <tr>
                            <td style="width: 150px;vertical-align: top !important;padding-top: 10px;">
                                {{ trans("admin/order_info.return_shop")!}：
                            </td>
                            <td colspan="3">
                                <#list ($return_order_goods as $return_order_good)
                                    <div class="return-goods-info">
                                        <span>
                                            <img src="${return_order_good->goods_img!}" alt="" class="name-img">
                                            <span class="list-name-top">
                                                ${return_order_good->goods_name!}
                                            </span><br/>
                                            <span class="list-name-center">
                                                ${return_order_good->goods_attr!}
                                            </span><br/>
                                            <span class="list-name-bottom">
                                                <span style="color:#ff6600;">￥${return_order_good->goods_price!}&nbsp&nbsp;</span>
                                                &nbsp; x&nbsp;${return_order_good->goods_number!}
                                            </span>
                                        </span>
                                    </div>
                                </#list>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 150px;">{{ trans("admin/order_info.re_monery_kind")!}：</td>
                            <td colspan="3"><strong>${return_order->return_type == 0 ? "仅退款" :"退款且退货"!}</strong></td>
                        </tr>

                        <tr>
                            <td style="width: 150px;">退款状态：</td>
                            <td colspan="3"><strong>${return_order->refund_status_name!}</strong></td>
                        </tr>


                        <tr>
                            <td style="width: 150px;">{{ trans("admin/order_info.re_monery")!}：</td>
                            <td colspan="3">
                                <#if ($return_order->refund_status == 4 || $return_order->refund_status == 2 )
                                    <div class="refund-info" style="line-height: 24px">
                                        退商品金额：<input type="text" name="refund_money" id="refund_money"
                                                     style="width: 100px;text-align: center"
                                                     value="{{ number_format($return_order->money,2,".","")!}"
                                                     max_discounted_money="{{ number_format($return_order->money,2,".","")!}"
                                        />&nbsp;元，
                                        退运费金额：<input type="text" name="shipping_fee" id="shipping_fee"
                                                     maxlength="20"
                                                     style="width: 100px;text-align: center" <#if ($order->order_status == 3)value="{{ number_format($can_refund['shipping_fee'],2,".","")!}"<#else> value="0.00"</#if>>&nbsp;元，
                                        可退最大运费：{{ number_format($can_refund['shipping_fee'],2,".","")!}元
                                        <br/>
                                        <div style="color: #666;">
                                            总退款金额：￥<span class="text-warning refund-money">0.00</span> =
                                            退余额：￥<span class="text-warning refund-balance-money">0.00</span> +
                                            退积分抵扣：￥<span class="text-warning refund-score-money">0.00</span> +
                                            退会员卡余额：￥<span class="text-warning refund-member-card-money">0.00</span> +
                                            退支付金额：￥<span class="text-warning refund-pay-money">0.00</span>
                                        </div>
                                    </div>
                                    <p style="color:#666">
                                        注：总退款金额 = 退商品金额+退运费金额，扣款优先级：会员卡余额，余额，积分，支付金额
                                    </p>
                                <#else>
                                    <strong>
                                        <p>
                                            退商品金额：<span
                                                    class="text-warning">￥{{ number_format($return_order->money,2,".","")!}</span>
                                        </p>
                                        <p>
                                            退运费金额：<span
                                                    class="text-warning">￥{{ number_format($return_order->shipping_fee,2,".","")!}</span>
                                        </p>
                                        <#if ($refund_detail)
                                            <p>
                                                退款详情：
                                                <#if ($refund_detail['use_account'] > 0)退余额：<span
                                                        class="text-warning">￥{{ number_format($refund_detail['use_account'],2,".","")!}</span> </#if>
                                                <#if ($refund_detail['score_discount'] > 0)退积分抵扣：<span
                                                        class="text-warning">￥{{ number_format($refund_detail['score_discount'],2,".","")!}</span> </#if>
                                                <#if ($refund_detail['member_card_balance'] > 0)退会员卡余额：<span
                                                        class="text-warning">￥{{ number_format($refund_detail['member_card_balance'],2,".","")!}</span> </#if>
                                                <#if ($refund_detail['money_paid'] > 0)退支付金额：<span
                                                        class="text-warning">￥{{ number_format($refund_detail['money_paid'],2,".","")!}</span> </#if>
                                            </p>
                                        </#if>
                                    </strong>
                                </#if>
                            </td>
                        </tr>

                        <#if ($return_order->return_type == 1 && $return_order->refund_status == 4)
                            <tr>
                                <td width="150px">客户电话</td>
                                <td colspan="3">${return_order->phone!}</td>
                            </tr>
                            <tr>
                                <td width="150px">物流公司</td>
                                <td colspan="3">${return_order->shipping_type!}</td>
                            </tr>
                            <tr>
                                <td width="150px">物流单号</td>
                                <td colspan="3">${return_order->shipping_no!}
                                    <#if ($return_order->shipping_no)
                                        {{--<a class="text-info" href="/admin/orders/manage/express/show?shipping_no=${return_order->shipping_no!}&shipping_name=${return_order->shipping_type!}"  target="_blank">查询</a>--!}
                                        <a class="text-info"  href="https://www.kuaidi100.com/chaxun?com=${return_order->shipping_code!}&nu=${return_order->shipping_no!}"  target="_blank">查询</a>
                                    </#if>

                                    {{--<input type="button" value="拒绝退款"--!}
                                    {{--class="btn-common btn-cancel btn-refuse-refund "/>--!}
                                    {{--<input type="button" value="收货并退款" class="btn-common btn-refund"/>--!}
                                </td>
                            </tr>
                        </#if>

                        <tr>
                            <td>{{ trans("admin/order_info.re_reason")!}：</td>
                            <td colspan="3">${return_order->reason!}</td>
                        </tr>
                        <tr>
                            <td>说&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;明：</td>
                            <td colspan="3">${return_order->return_desc!}</td>
                        </tr>
                        <tr>
                            <td>退款图片：</td>
                            <td colspan="3">
                                <#if ($return_order->goods_images)
                                    <#list ($return_order->goods_images as $goods_images)
                                        <#if ($goods_images)
                                            <a href="${goods_images!}" title="点击显示大图" target="_blank">
                                                <img src="${goods_images!}"
                                                     style="max-width: 50px;max-height: 50px;">
                                            </a>
                                        </#if>
                                    </#list>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>申请时间：</td>
                            <td colspan="3">${return_order->return_type==1 ? $return_order->apply_time : $return_order->shipping_or_refund_time!}</td>
                        </tr>
                        {{--<#if ($return_order->return_type==0 && $return_order->refund_status==4 ||  $return_order->refund_status==1)--!}
                        {{--<tr>--!}
                        {{--<td colspan="4">--!}
                        {{--&nbsp;&nbsp;&nbsp;&nbsp;--!}
                        {{--<input type="button" value="拒绝" class="btn-common btn-close btn-not-pass "/>--!}
                        {{--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--!}
                        {{--<input type="button" value="同意" class="btn-common btn-pass"/>--!}
                        {{--</td>--!}
                        {{--</tr>--!}
                        {{--</#if>--!}
                    </table>
                </div>

                <#if ($return_order->return_type == 1 && $return_order->refund_status>=2 && $return_order->refund_status!=7)
                    {{-- 商家处理退货申请 --!}
                    <div class="goods-edit-basic" hidden>
                        <h2 class="h2_title">商家处理退货且退款申请</h2>
                        <table class="order-basic-table table table-bordered">
                            <#if ($return_order->refund_status != 3)
                                <tr>
                                    <td width="150px">收件人</td>
                                    <td>${return_order->consignee!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">收件人电话</td>
                                    <td>${return_order->merchant_telephone!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">邮编</td>
                                    <td>${return_order->zip_code!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">退货地址</td>
                                    <td>${return_order->return_address!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">审核通过时间</td>
                                    <td>${return_order->apply_pass_time!}</td>
                                </tr>
                            <#else>
                                <tr>
                                    <td width="150px">审核未通过时间</td>
                                    <td>${return_order->apply_not_pass_time!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">审核未通过原因</td>
                                    <td>${return_order->apply_not_pass_reason!}</td>
                                </tr>
                            </#if>
                        </table>
                    </div>
                    <#if ($return_order->refund_status >=4 && $return_order->refund_status!=7)
                        {{-- 买家退货给商家--!}
                        <div class="goods-edit-basic" hidden>
                            <h2 class="h2_title">买家退货给商家</h2>
                            <table class="order-basic-table table table-bordered">
                                <tr>
                                    <td width="150px">客户电话</td>
                                    <td>${return_order->phone!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">提交物流时间</td>
                                    <td>${return_order->shipping_or_refund_time!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">物流公司</td>
                                    <td>${return_order->shipping_type!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">物流单号</td>
                                    <td>${return_order->shipping_no!}
                                        <#if ($return_order->shipping_no)
                                            {{--<a class="text-info"--!}
                                               {{--href="/admin/orders/manage/express/show?shipping_no=${return_order->shipping_no!}&shipping_name=${return_order->shipping_type!}"--!}
                                               {{--target="_blank">查询</a>--!}
                                            <a class="text-info"  href="https://www.kuaidi100.com/chaxun?com=${return_order->shipping_code!}&nu=${return_order->shipping_no!}"  target="_blank">查询</a>
                                        </#if>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </#if>
                </#if>

                <#if ($return_order->refund_status>=5 && $return_order->refund_status!=7)
                    {{-- 商家退款给买家--!}
                    <div class="goods-edit-basic" hidden>
                        <h2 class="h2_title">商家退款给买家</h2>
                        <table class="order-basic-table table table-bordered">
                            <#if ($return_order->refund_status == 6)
                                <tr>
                                    <td width="150px">拒绝退款时间</td>
                                    <td>${return_order->refund_refuse_time!}</td>
                                </tr>
                                <tr>
                                    <td width="150px">拒绝退款原因</td>
                                    <td>${return_order->refund_refuse_reason!}</td>
                                </tr>
                            <#elseif>($return_order->refund_status == 5)
                                <tr>
                                    <td width="150px">退款成功时间</td>
                                    <td>${return_order->refund_success_time!}</td>
                                </tr>
                            </#if>
                        </table>
                    </div>
                </#if>
                <#if ($change_list && count($change_list))
                    <div class="goods-edit-basic">
                        <h2 class="h2_title">协商记录</h2>
                        <#list ($change_list as $change)
                            <#if (($change->status==4 && $return_order->return_type == 0) ||$change->status==1)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li><#if ($return_order->return_type == 0)
                                                    ${return_order->shipping_or_refund_time!}
                                                <#else>
                                                    ${return_order->apply_time!}</#if></li>
                                            <li>发起申请</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>退款类型：${return_order->return_type == 0? "仅退款" : "退货退款"!}</li>
                                            <li>退款原因：${return_order->reason!}</li>
                                            <li>退款金额：{{ bcadd($return_order->money,$return_order->shipping_fee,2)!}</li>
                                            <li>退款说明：${return_order->return_desc!}</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==6 && $return_order->return_type == 0)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->refund_refuse_time!}</li>
                                            <li>商家拒绝退款申请</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>商家拒绝退款申请</li>
                                            <li>拒绝理由：${return_order->refund_refuse_reason!}</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==5 && $return_order->return_type == 1)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->refund_success_time!}</li>
                                            <li>商家同意退款，退款成功</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>商家同意退款，退款成功</li>
                                            <li>注：退款将在3个工作日内到达买家账户，本次维权结束。</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==2)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->apply_pass_time!}</li>
                                            <li>商家同意退货退款</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>您已同意退货退款，请等待买家处理</li>
                                            <li>退货地址：${return_order->return_address!}； 联系人：${return_order->consignee!}； 电话：${return_order->merchant_telephone!}；</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==3)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->apply_pass_time!}</li>
                                            <li>商家审核不通过</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>您已拒绝退货退款</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==7 && $return_order->return_type == 1 && $change->type == 2)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->refund_cancel_time!}</li>
                                            <li><#if ($change->type==1) 买家主动撤销退款申请 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)撤销退款申请（系统自动处理） </#if></li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>买家未在商家审核退款申请之后7日内，提交退货物流（或商家未确认收货），退款申请已自动撤销</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==7 && $change->type == 1)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->refund_cancel_time!}</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>买家已自动撤销</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==4 && $return_order->return_type == 1)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->shipping_or_refund_time!}</li>
                                            <li>提交物流</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>买家已退货，等待商家确认收货并退款</li>
                                            <li>物流公司：${return_order->shipping_type!}</li>
                                            <li>运单号码：${return_order->shipping_no!}
                                                <#if ($return_order->shipping_no)
                                                    {{--<a class="text-info"--!}
                                                       {{--href="/admin/orders/manage/express/show?shipping_no=${return_order->shipping_no!}&shipping_name=${return_order->shipping_type!}"--!}
                                                       {{--target="_blank">查询</a>--!}
                                                    <a class="text-info"  href="https://www.kuaidi100.com/chaxun?com=${return_order->shipping_code!}&nu=${return_order->shipping_no!}"  target="_blank">查询</a>
                                                </#if></li>
                                            <li>联系电话：${return_order->phone!}</li>
                                            <li>物流凭证：
                                                <#if ($return_order->voucher_images)
                                                    <#list ($return_order->voucher_images as $voucher_images)
                                                        <#if ($voucher_images)
                                                            <a href="${voucher_images!}" title="点击显示大图" target="_blank">
                                                                <img src="${voucher_images!}" alt="" style="max-width: 50px;max-height: 50px;">
                                                            </a>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==6 && $return_order->return_type == 1)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->refund_refuse_time!}</li>
                                            <li>商家未收货，拒绝退款退货</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>商家未收货，拒绝退款退货</li>
                                        </ul>
                                    </div>
                                </div>
                            <#elseif>($change->status==5 && $return_order->return_type == 0)
                                <div class="recode">
                                    <div class="top">
                                        <ul>
                                            <li><#if ($change->type==1) 买家 <#elseif>($change->type==0) 商家 <#elseif>($change->type==2)系统 </#if></li>
                                            <li>${return_order->refund_success_time!}</li>
                                            <li>商家退款，退款成功</li>
                                        </ul>
                                    </div>
                                    <div class="bottom">
                                        <ul>
                                            <li>商家退款，退款成功</li>
                                            <li>注：退款将在3个工作日内到达买家账户，本次维权结束。</li>
                                        </ul>
                                    </div>
                                </div>
                            </#if>
                        </#list>
                    </div>
                </#if>
            </div>

        </div>
    </div>
</div>


<div id="receiver-address" style="display: none">
    <table class="table">
        <tr>
            <td>
                收货人：
            </td>
            <td>
                <input type="text" name="consignee" id="consignee" value="${shop_info->consignee!}" maxlength="120"
                       size="34" placeholder="请输入收货人"/>
            </td>
        </tr>
        <tr>
            <td>
                收货地址：
            </td>
            <td>
                <input type="text" name="return_address" id="return_address" value="${shop_info->return_address!}" maxlength="120"
                       size="34" placeholder="请输入收货地址"/>
            </td>
        </tr>
        <tr>
            <td>
                电话：
            </td>
            <td>
                <input type="text" name="merchant_telephone" id="merchant_telephone" value="${shop_info->merchant_telephone!}" maxlength="120"
                       size="34" placeholder="请输入电话"/>
            </td>
        </tr>
        <tr>
            <td>
                邮编：
            </td>
            <td>
                <input type="text" name="zip_code" id="zip_code" value="${shop_info->zip_code!}" maxlength="120"
                       size="34" placeholder="请输入邮编"/>
            </td>
        </tr>
    </table>
</div>
{{--退款确认弹框--!}
<div id="set-template-confirm" style="display: none;">
    <div style="padding: 30px 15px;border-bottom: 1px solid #eee;">
        <span style="display:inline-block;width: 100%;">
            提醒：
            <span style="color: #f66;">退款操作会把订单金额全部退回给顾客付款账户，</span>
        </span>
        <span>
            由于支付渠道（如跨行退款）的延迟，会在 <span style="color: #f66;">3个工作日内到账，</span>
            <p>您确定要对订单退款吗？</p>
        </span>
    </div>
</div>{{--退款确认弹框END--!}
{{--退货退款弹框--!}
<div id="set-get-confirm" style="display: none;">
    <div style="padding: 20px 15px;border-bottom: 1px solid #eee;">
        <span style="display:inline-block;width: 100%;">
            提醒：
            <span>请在 <span style="color: #f66;">确认已收到退货实物或者协商通过后再操作退货完成，本操作会同步退款给顾客(货款退回到顾客支付的账户)，请谨慎操作</span></span>
        </span>
        <span>
            由于支付渠道（如跨行退款）的延迟，会在 <span style="color: #f66;">3个工作日内到账，</span><P>您确定要对订单操作退货完成吗？</P>
        </span>
    </div>
</div>{{--退货退款弹框END--!}
<script type="text/javascript">
    var can_refund = @json($can_refund);
    console.log(can_refund);

    // 倒计时
    setTimeout("show_time()",1000);
    function show_time(){
        if($("#count_down").attr("class") == 'return_money_days'){
            var time_distance = (new Date("${return_momey_days!}")) - (new Date());
        }else if($("#count_down").attr("class") == 'return_address_days'){
            var time_distance = (new Date("${return_address_days!}")) - (new Date());
        }else if($("#count_down").attr("class") == 'return_shopping_days'){
            var time_distance = (new Date("${return_shopping_days!}")) - (new Date());
        }else if($("#count_down").attr("class") == 'revoke'){
            var time_distance = (new Date("${revoke!}")) - (new Date());
        }
        var int_day, int_hour, int_minute, int_second;
        if(time_distance >= 0){
            // 天时分秒换算
            int_day = Math.floor(time_distance/86400000)
            time_distance -= int_day * 86400000;
            int_hour = Math.floor(time_distance/3600000)
            time_distance -= int_hour * 3600000;
            int_minute = Math.floor(time_distance/60000)
            time_distance -= int_minute * 60000;
            int_second = Math.floor(time_distance/1000)
            // 时分秒为单数时、前面加零站位
            if(int_day < 10)
                int_day = "0"+int_day;
            if(int_hour < 10)
                int_hour = "0" + int_hour;
            if(int_minute < 10)
                int_minute = "0" + int_minute;
            if(int_second < 10)
                int_second = "0" + int_second;
            // 显示时间
            if($("#count_down").attr("class") == 'return_money_days'){
                $(".return_money_days").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }else if($("#count_down").attr("class") == 'return_address_days'){
                $(".return_address_days").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }else if($("#count_down").attr("class") == 'return_shopping_days'){
                $(".return_shopping_days").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }else if($("#count_down").attr("class") == 'revoke'){
                $(".revoke").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }
            setTimeout("show_time()",1000);
        }else{
            if($("#count_down").attr("class") == 'return_money_days'){
                $(".return_money_days").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }else if($("#count_down").attr("class") == 'return_address_days'){
                $(".return_address_days").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }else if($("#count_down").attr("class") == 'return_shopping_days'){
                $(".return_shopping_days").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }else if($("#count_down").attr("class") == 'revoke'){
                $(".revoke").html(int_day+'天'+int_hour+"时"+int_minute+"分"+int_second+"秒");
            }
            // clearTimeout(timerID)
        }

    };
</script>
<#include ("system.footer")
<script src="/js/admin/order_return.js?v=1.0.6" type="text/javascript"></script>
