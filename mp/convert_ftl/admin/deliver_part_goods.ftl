{{--<#include "/admin/header.ftl">--!}

<link rel="stylesheet" href="{{asset('css/admin/order_all.css')!}" type="text/css"/>
<style>
    .order-list-table td{
        padding: 8px;
        font-size: 14px;
    }
    .order-info{
        padding: 15px 25px 0;
    }
    .order-info li{
        float:left;
    }
    .order-info li+li{
        margin-left:20px;
    }
    .send_num{
        border: none;
        outline: none;
        width:50px;
        text-align:center;
        background-color:#fff;
    }
    .ipt-change{
        border:1px solid #E5E9F4;
    }
    input[type='checkbox']{
        background: url(/image/admin/square_no.png) no-repeat;
        background-size: 100%;
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        width: 12px;
        height: 12px;
    }
    input[type='checkbox']:checked {
        background: url(/image/admin/square_yes.png) no-repeat;
        background-size: 100%;
    }
</style>
<div  style="min-height: 300px;background: #fff;font-size: 14px;">
    <table class="order-info" style="width: 100%">
        <tr>
            <td style="background-color: #f5f5f5; padding: 10px">
                <div style="float: left" class="">配送信息</div>
                <div style="float: left;margin-left: 20px" class="">
                    <p style="padding-bottom: 5px">收货人：${order->username!}&nbsp;&nbsp;${order->user_mobile!}</p>
                    <p>收货地址：${order->complete_address!}</p>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <ul>
                    <li class="order-info-li clearfix">
                        快递列表：<select name="shipping_id" id="shipping_id">
                            <#list ($shipping_list as $item)
                                <option value="${item->shipping_id!}">${item->shipping_name!}</option>
                            </#list>
                        </select>
                    </li>

                    <li class="order-info-li clearfix">
                        快递单号：<input type="text" name="shipping_no" id="shipping_no" value="" maxlength="120"
                                    size="18" placeholder="请输入快递单号"/>
                    </li>
                </ul>
            </td>
        </tr>
    </table>
    <div class="order-list margin-top-0" style="margin-top: 0;">
        <div class="order-list-table">
            <table style="width: 100%;border-collapse: collapse;border-spacing: 0;">
                <thead>
                <tr>
                    <td width="10%"><input type="checkbox" class="checkall"/>选中</td>
                    <td width="30%">商品名称</td>
                    <td width="20%">规格</td>
                    <td width="20%">单价</td>
                    <td width="10%">数量</td>
                    <td width="10%">发货数量</td>
                </tr>
                </thead>
                <tbody>
                <#list ($orderGoods as $orderGood)
                    <tr class="order-tb-body" product_id="${orderGood->product_id!}" rec_id ="${orderGood->rec_id!}">
                        <td>
                            <#if ($orderGood->source > 0)
                                <input type="checkbox" name="" value="${orderGood->rec_id!}" disabled>
                            <#else>
                                <input type="checkbox" name="cbk_goods[]" value="${orderGood->rec_id!}" checked>
                            </#if>
                        </td>
                        <td> <#if  ($orderGood->source == 0 && in_array($shop_flag,[1,2]))
                                <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">自营</span>
                            </#if>${orderGood->goods_name!}</td>
                        <td>${orderGood->goods_attr!}</td>
                        <td>${orderGood->goods_price!}</td>
                        <td>${orderGood->to_send_number!}</td>
                        <td>
                            <input class="send_num" send_number="${orderGood->to_send_number!}" value="${orderGood->to_send_number!}" onkeyup="value=value.replace(/\./,'')" disabled/>
                            {{--<img src="http://mpdevimg.weipubao.cn/image/admin/good_edit.png" grade="0" alt="" class="goods-number-img" style="display: inline-block;">--!}
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
{{--<#include "/admin/footer.ftl">--!}
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script language="JavaScript" src="/js/admin/util.js?v=1.1.9"></script>
<script>
    $(function(){
        var is_support_part_ship = '${isSupportPartShip!}';
        if (parseInt(is_support_part_ship) == 0) {
            $('.goods-number-img').css('display', 'none');
        }

        $('.checkall').click(function(){
            if (parseInt(is_support_part_ship) == 0) {
                return false;
            }
            let check_status = $(this).prop('checked');
            $('input[name="cbk_goods[]"]').each(function(){
                $(this).prop('checked',check_status);
            })
        })
        $('tbody').on('click','input[name="cbk_goods[]"]',function(){
            if (parseInt(is_support_part_ship) == 0) {
                return false;
            }
            let allLength = $('tbody').find('input[type="checkbox"]').length;
            let checkedLength = $('tbody').find('input[type="checkbox"]:checked').length;
            if(allLength === checkedLength){
                $('.checkall').prop('checked',true);
            } else {
                $('.checkall').prop('checked',false);
            }
        })
        $(".goods-number-img").click(function() {
                    var _this = $(this);
                    var prevVal = _this.prev().val();
                    $(".goods-number-img").show();
                    _this.hide();
                    $(".send_num").attr("disabled", true);
                    $(".send_num").removeClass('ipt-change');
                    _this.prev().attr('disabled', false);
                    _this.prev().addClass('ipt-change');
                    _this.prev().val('').focus().val(prevVal);//光标移动到内容后
        });
        $('.send_num').blur(function(){
            let sendNum = $(this).val();
            let goodsNum = $(this).parent().prev().text();
            if(sendNum === ''){
                $(this).val(0);
            }
            if(isNaN(sendNum)){
                util.mobile_alert('输入有误，请确认后输入');
                $(this).val('');
                $(this).focus();
                return false;
            }
            if(Number(sendNum) < 0){
                util.mobile_alert('发货数量不能小于0');
                $(this).val('');
                $(this).focus();
                return false;
            }
            if(Number(sendNum) > Number(goodsNum)){
                $(this).val(goodsNum);
                $(this).focus();
            }
            $('.ipt-change').next().show();
            $('.send_num').removeClass('ipt-change');
            $(".send_num").attr("disabled",true);
        })
        function loadCheck(){
            let allLength = $('tbody').find('input[type="checkbox"]').length;
            let checkedLength = $('tbody').find('input[type="checkbox"]:checked').length;
            if(allLength === checkedLength){
                $('.checkall').prop('checked',true);
            } else {
                $('.checkall').prop('checked',false);
            }
        }
        loadCheck();
    })
</script>