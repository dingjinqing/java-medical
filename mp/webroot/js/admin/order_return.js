(function () {

    var order_sn = $("#order_sn").val();
    var ret_id = $("#ret_id").val();
    var return_type = $("#return_type").val();
    var refund_status = $("#refund_status").val();
    $(".btn-not-pass").click(function () {
        if (refund_status == "1") {
            not_pass();
        } else if (refund_status == "4") {
            refuse_refund();
        }
    });
    $(".btn-pass").click(function () {
        if (refund_status == "1") {
            pass();
        } else if (refund_status == "4") {
            exchange_confirm("订单退款确认","#set-template-confirm");
        }
    });
    $(".btn-refund").click(function () {
        exchange_confirm("确认退货完成", "#set-get-confirm");
    });
    function exchange_confirm(title,id) {
        var status = false;
        layer.open({
            type: 1
            ,title: [title,'text-align:center;padding: 0px;']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: ['480px', '220px']
            ,content: $(id) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['确认退款','取消']
            ,btnAlign: 'r' //按钮居右
            ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function(index, layero){ //确定按钮的回调
                if (status) return false;
                if(id=='#set-get-confirm'){
                    agree_refund();
                }
                if(id=='#set-template-confirm'){
                    agree_refund();
                }
                status = true;
            },btn2: function(index, layero){                //按钮取消的回调

            }
        });
    }
    $(".btn-refuse-refund").click(function () {
        // if(!pass_type){
        //     check_role();
        // }
        // if(pass_type) {
            refuse_refund();
            // pass_type = false;
        // }
    });


    $('[name="refund_money"]').keyup(function () {
        refresh_can_refund();
    });

    $('[name="shipping_fee"]').keyup(function () {
         refresh_can_refund();
    });


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
        }

        if (left > 0) {
            var refund_score_money = left > window.can_refund.score_discount ? window.can_refund.score_discount : left;
            $(".refund-score-money").text(refund_score_money.toFixed(2));
            left = left - refund_score_money;
        }
        if (left > 0) {
            var refund_pay_money = left > parseFloat(window.can_refund.money_paid) ? parseFloat(window.can_refund.money_paid) : left;
            $(".refund-pay-money").text(refund_pay_money.toFixed(2));
            left = left - refund_pay_money;
        }
        if(left < 0)  {
            layer.msg("意外错误，请联系管理");
            return false;
        }
        return true;
    }


    /**
     * 审核通过，并提交卖家收货地址
     */
    function pass() {
        layer.open({
            type: 1,
            title: "填写退货商品收货地址",
            content: $('#receiver-address'),
            area: "400px",
            btn: ['确定', '取消'],
            yes: function (index) {
                if ($("#consignee").val() == "") {
                    util.layui_msg("收货人不能为空！");
                    return false;
                }
                if ($("#return_address").val() == "") {
                    util.layui_msg("收货地址不能为空！");
                    return false;
                }
                if ($("#merchant_telephone").val() == "") {
                    util.layui_msg("电话不能为空！");
                    return false;
                }
                var shipping_fee = parseFloat($("#shipping_fee").val());
                var param = {
                    ret_id: ret_id,
                    consignee: $("#consignee").val(),
                    return_address: $("#return_address").val(),
                    merchant_telephone: $("#merchant_telephone").val(),
                    zip_code: $("#zip_code").val()
                };
                getAuthorityDetail(1,"","return_order","","return_pass",param)
                // util.ajax_json("/admin/orders/manage/return/pass", function (d) {
                //     if (d && d.error == 0) {
                //         layer.close(index);
                //         layer.msg("操作成功");
                //         setTimeout(function () {
                //             window.location.reload();
                //         }, 1000);
                //     } else if (d && d.error > 0) {
                //         layer.msg(d.message);
                //     }
                // }, param);
            },
            btn2: null,
        });
    }

    /**
     * 审核不通过，填写拒绝理由
     */
    function not_pass() {
        layer.prompt({
            title: "请输入拒绝理由",
            formType:2,
            area: ['260px', '80px']
        }, function (value, index, elem) {
            var param = {
                ret_id: ret_id,
                apply_not_pass_reason: value
            };
            getAuthorityDetail(1,"","return_order","","return_no_pass",param)
            // layer.close(index);
            // util.ajax_json("/admin/orders/manage/return/not/pass", function (d) {
            //     if (d && d.error == 0) {
            //         layer.msg("操作成功");
            //         setTimeout(function () {
            //             window.location.reload();
            //         }, 1000);
            //     } else if (d && d.error > 0) {
            //         layer.msg(d.message);
            //     }
            // }, param);
        });
    }

    /**
     * 同意退款
     */
    function agree_refund() {
        var ret = refresh_can_refund();
        if(!ret) return false;

        var param = {
            ret_id: ret_id,
            new_money:$("#refund_money").val(),
            shipping_fee:$("#shipping_fee").val()
        };
        getAuthorityDetail(1,"","return_order","","return_agree_refund",param)
        // util.ajax_json("/admin/orders/manage/return/refund/agree", function (d) {
        //     if (d && d.error == 0) {
        //         layer.msg("操作成功");
        //         setTimeout(function () {
        //             window.location.reload();
        //         }, 1000);
        //     } else if (d && d.error > 0) {
        //         layer.msg(d.message);
        //     }
        // }, param);
    }

    /**
     * 拒绝退款，填写拒绝理由
     */
    function refuse_refund() {
        layer.prompt({
            title: "请输入拒绝理由",
            formType:2,
            area: ['260px', '80px']
        }, function (value, index, elem) {
            var param = {
                ret_id: ret_id,
                refund_refuse_reason: value
            };
            getAuthorityDetail(1,"","return_order","","return_refuse_refund",param)
            // util.ajax_json("/admin/orders/manage/return/refund/refuse", function (d) {
            //     if (d && d.error == 0) {
            //         layer.msg("操作成功");
            //         setTimeout(function () {
            //             window.location.reload();
            //         }, 1000);
            //     } else if (d && d.error > 0) {
            //         layer.msg(d.message);
            //     }
            // }, param);
            // layer.close(index);
        });
    }

    if($("#refund_money").length >0)
        refresh_can_refund();

}());

