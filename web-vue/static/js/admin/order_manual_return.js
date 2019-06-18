(function () {

    var order_sn = $("#order_sn").val();
    var ret_id = $("#ret_id").val();
    var return_type = $("#return_type").val();
    var refund_status = $("#refund_status").val();

    $('#btn-add-image').click(function () {
        var el = $(".goods_images");
        $.jImageManager({
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.attr("src", path);
                $('[name="goods_images"]').val(path);
            }
        });
    });

    $(".btn-return").click(function () {
        var ret = refresh_can_refund();
        if (ret) {
            $("#form1").submit();
        }
    });

    $('[name="cbk_goods[]"]').change(function () {
        refresh_return_amount();
    });

    $('.return_number').keyup(function () {
        var return_number = parseInt($(this).val());
        var max_return_number = parseInt($(this).parents("tr").attr("max_return_number"));
        if ($(this).val() == "") return;

        if (isNaN(return_number) || !isNaN(return_number) && return_number <= 0) {
            layer.msg("退款商品数量不合法");
            return;
        }
        if (return_number > max_return_number) {
            layer.msg("退款商品数量大于最大商品商品数量");
            return;
        }
        var amount = return_number * parseFloat($(this).parents("tr").attr("discounted_goods_price"));
        if(return_number == max_return_number) amount = parseFloat($(this).parents("tr").attr("max_return_money"));
        $(this).parents("tr").find('.goods-amount').text(amount.toFixed(2));
        refresh_return_amount();
    });

    $('.return_number').change(function () {
        var return_number = parseInt($(this).val());
        var max_return_number = parseInt($(this).parents("tr").attr("max_return_number"));
        if (isNaN(return_number) || !isNaN(return_number) && return_number <= 0) {
            layer.msg("退款商品数量不合法");
            $(this).focus();
            return;
        }
        if (return_number > max_return_number) {
            layer.msg("退款商品数量大于最大商品商品数量");
            $(this).focus();
            return;
        }
    });

    $('[name="refund_money"]').keyup(function () {
        refresh_can_refund();
    });

    $('[name="refund_money"]').change(function () {
        refresh_can_refund();
    });

    $('#shipping_fee').keyup(function () {
        refresh_can_refund();
    });
    $('#shipping_fee').change(function () {
        refresh_can_refund();
    });

    $("#return_type").change(function () {
        if($("#return_type").val() == 2) {
            $('.goods_checked').css('disabled','disabled')
        } else {
            $('.goods_checked').css('disabled','')
        }
    });


    function refresh_return_amount() {
        var total_amount = 0;
        var check_ok = true;
        $('[name="cbk_goods[]"]:checked').each(function () {
            var el = $(this).parents("tr").find('.return_number');
            var return_number = parseInt(el.val());
            var max_return_number = parseInt($(this).parents("tr").attr("max_return_number"));
            if (isNaN(return_number) || !isNaN(return_number) && return_number <= 0) {
                layer.msg("退款商品数量不合法");
                el.focus();
                check_ok = false;
                return false;
            }
            if (return_number > max_return_number) {
                layer.msg("退款商品数量大于最大商品商品数量");
                el.focus();
                check_ok = false;
                return false;
            }

            var amount = return_number * parseFloat($(this).parents("tr").attr("discounted_goods_price"));
            if(return_number == max_return_number) amount = parseFloat($(this).parents("tr").attr("max_return_money"));
            total_amount += amount;
            $(this).parents("tr").find('.goods-amount').text(amount.toFixed(2));

        });
        if (check_ok) {
            $("#refund_money").val(total_amount.toFixed(2));
            $("#refund_money").attr("max_discounted_money", total_amount);
            check_ok = refresh_can_refund();
        }
        return check_ok;
    }

    function refresh_can_refund() {
        var money = parseFloat($("#refund_money").val());
        var max_discounted_money = parseFloat($("#refund_money").attr("max_discounted_money"));
        if (isNaN(money) || money < 0) {
            layer.msg("商品金额不合法");
            $("#refund_money").focus();
            return false;
        }
        if (money > max_discounted_money) {
            layer.msg("商品金额大于最大商品折后金额");
            $("#refund_money").focus();
            return false;
        }

        var shipping_fee = parseFloat($("#shipping_fee").val());
        if (isNaN(shipping_fee) || shipping_fee < 0) {
            layer.msg("退运费金额不能小于0");
            $("#shipping_fee").focus();
            return false;
        }
        if (shipping_fee > window.can_refund.shipping_fee) {
            layer.msg("退运费金额大于可退运费");
            $("#shipping_fee").focus();
            return false;
        }

        var left = money + shipping_fee;
        $(".refund-money").text(left.toFixed(2));
        $(".refund-bk-order-money").text("0.00");
        $(".refund-member-card-money").text("0.00");
        $(".refund-balance-money").text("0.00");
        $(".refund-score-money").text("0.00");
        $(".refund-pay-money").text("0.00");
        var bk_order_money = left > parseFloat(window.can_refund.bk_order_money) ? parseFloat(window.can_refund.bk_order_money) : left;
        $(".refund-bk-order-money").text(bk_order_money.toFixed(2));
        left = left - bk_order_money;

        if (left > 0) {
            var member_card_balance = left > parseFloat(window.can_refund.member_card_balance) ? window.can_refund.member_card_balance : left;
            $(".refund-member-card-money").text(member_card_balance.toFixed(2));
            left = left - member_card_balance;
        }

        if (left > 0) {
            var refund_balance_money = left > window.can_refund.use_account ? window.can_refund.use_account : left;
            $(".refund-balance-money").text(refund_balance_money.toFixed(2));
            left = left - refund_balance_money;
        }if (left > 0) {
            var refund_score_money = left > window.can_refund.score_discount ? window.can_refund.score_discount : left;
            $(".refund-score-money").text(refund_score_money.toFixed(2));
            left = left - refund_score_money;
        }
        if (left > 0) {
            var refund_pay_money = left > window.can_refund.money_paid ? window.can_refund.money_paid : left;
            $(".refund-pay-money").text(refund_pay_money.toFixed(2));
            left = left - refund_pay_money;
        }
        if(left < 0)  {
            layer.msg("意外错误，请联系管理");
            return false;
        }
        return true;
    }

}());