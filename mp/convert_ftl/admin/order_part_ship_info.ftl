<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_since.css" type="text/css"/>
<div class="title">
    <span>订单管理 / </span><span style="color: #666;">部分发货信息</span>
</div>

<#if ($message)
    <div class="message-tip alert alert-warning margin-top-10 margin-bottom-0">
        ${message!}
    </div>
</#if>

<div class="order-container">
    <form name="formData" action="${act_url!}" id="form1" method="post">
        {{ csrf_field()!}
        <input type="hidden" name="act" id="act">
        <input type="hidden" name="batch_no" id="batch_no">
        <input type="hidden" name="shipping_id" id="shipping_id">
        <input type="hidden" name="shipping_no" id="shipping_no">


        <div class="since-info">
            <div class="since-info-top">
                <span>订单号：${order->order_sn!}</span>
                <span>订单状态：${order->order_status_name!}</span>
            </div>
        </div>
        <#if (count($partShipGroup) > 0)
            <div class="order-list-table">
                <h4>发货信息</h4>
                <table style="width: 100%">
                    <thead>
                    <tr>
                        <td>商品名称</td>
                        <td>规格</td>
                        <td>发货数量</td>
                        <td>物流公司</td>
                        <td>物流单号</td>
                        <td>发货时间</td>
                    </tr>
                    </thead>
                    <#list ($partShipGroup as $shipGroup)
                        <#list ($shipGroup->goods as $i=>$orderGood)
                            <tr class="order-tb-body"
                                order_sn="${shipGroup->order_sn!}"
                                batch_no="${shipGroup->order_status!}"
                                shipping_id="${shipGroup->shipping_id!}"
                                shipping_no="${shipGroup->shipping_no!}">
                                <td>${orderGood->goods_name!}</td>
                                <td>${orderGood->goods_attr!}</td>
                                <td>${orderGood->send_number!}</td>
                                <#if ($i == 0)
                                    <td rowspan="{{ count($shipGroup->goods)!}">
                                        ${shipGroup->shipping_name!}
                                    </td>
                                    <td rowspan="{{ count($shipGroup->goods)!}">
                                        <span class="shipping-no"> ${shipGroup->shipping_no!}</span>
                                        <a href="javascript:void(0);" class="text-info btn-modify-shipping">修改</a>
                                        &nbsp;&nbsp;<a href="/admin/orders/manage/express/query?shipping_no=${shipGroup->shipping_no!}" >查询</a>
                                    </td>
                                    <td rowspan="{{ count($shipGroup->goods)!}">
                                        ${shipGroup->shipping_time!}
                                    </td>
                                </#if>
                            </tr>
                        </#list>
                    </#list>
                </table>
            </div>
        </#if>


    </form>
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

<script>

    $(".btn-modify-shipping").click(function () {
        var that = $(this);
        var order_sn = $(this).parent().attr("order_sn");
        var batch_no = $(this).parent().attr("batch_no");
        var shipping_no = $(this).parent().attr("shipping_no");
        var shipping_id = $(this).parent().attr("shipping_id");
        $('#shipping-list').find("select").val(shipping_id);
        $('#template_shipping_no').val(shipping_no);
        layer.open({
            type: 1,
            title: ['修改快递运单号', 'text-align:center;padding: 0px;'],
            content: $('#shipping-list'),
            area: "300px",
            btn: ['修改', '取消'],
            yes: function (index) {
                if ($("#template_shipping_no").val() == "") {
                    util.layui_msg("运单号不能为空！");
                    return false;
                }
                var param = {
                    order_sn: order_sn,
                    batch_no: batch_no,
                    shipping_id: $("#template_shipping_id").val(),
                    shipping_no: $("#template_shipping_no").val()
                };
                util.ajax_json("/admin/orders/manage/express/modify", function (d) {
                    if (d && d.error == 0) {
                        layer.close(index);
                        layer.msg("修改成功");
                        $(that).parent().find(".shipping_no").text(param.shipping_no);
                        $(that).parent().attr("shipping_id", param.shipping_id);
                        $(that).parent().attr("shipping_no", param.shipping_no);
                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                }, param);
            },
            btn2: null,
        });

    });
</script>

<#include "/admin/footer.ftl">