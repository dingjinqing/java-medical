(function () {

    $(document).ready(function () {
        if (window.anchor)
            window.location.href = "#" + window.anchor;
        setTimeout(function () {
            $(".message-tip").hide();
        }, 5000);
    });

    //添加备注弹框
    $('.add_text').on('click', function () {
        var that = $(this);
        var order_sn = $(this).attr("order_sn");
        var seller_remark = $(this).attr("seller_remark");
        $("#seller_remark").text(seller_remark);
        layer.open({
            type: 1,
            title: "添加备注",
            content: $('#set-text'),
            area: "300px",
            btn: ['确定', '取消'],
            yes: function (index) {
                if($("#seller_remark").val() == ''){
                    util.mobile_alert('请添加备注');
                    return false;
                }
                var param = {order_sn: order_sn, seller_remark: $("#seller_remark").val()};
                util.ajax_json("/admin/order/set/seller/remark", function (d) {
                    if (d && d.error == 0) {
                        layer.msg("保存成功",{
                            time: 2000
                        },function () {
                            location.reload();
                        });
                        // layer.contect()



                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                }, param);
                layer.close(index);

            }
        });
    });

    $(".btn-deliver").click(function () {
        var that = $(this);
        var order_sn = $(this).parent().attr("order_sn");
        util.ajax_json("/admin/order/has/returning/order?order_sn=" + order_sn, function (d) {
            if (d && d.error == 0) {
                if (d.content.has_returning) {
                    layer.alert("有退款请求需要审批处理，请先处理退款请求!");
                } else {
                    part_deliver_dialog(order_sn);
                }
            }
        });


    });

    function part_deliver_dialog(order_sn) {
        layer.open({
            type: 2,
            title: "填入快递信息发货",
            area: ["800px", "400px"],
            offset:'100px',
            content: "/admin/order/part/deliver?order_sn=" + order_sn,
            btn: ['发货', '取消'],
            yes: function (index, layero) {
                var body = layer.getChildFrame('body', index);
                var shipping_id = body.find('#shipping_id').val();
                var shipping_no = body.find('#shipping_no').val();
                if (shipping_no == "") {
                    layer.msg("运单号不能为空！");
                    return false;
                }

                var product_ids = [];
                body.find('[name="cbk_goods[]"]:checked').each(function () {
                    product_ids.push($(this).val())
                });
                if (product_ids.length == 0) {
                    layer.msg("未选中发布商品！");
                    return false;
                }

                $("#act").val("deliver");
                $("#act_order_sn").val(order_sn);
                $("#shipping_id").val(shipping_id);
                $("#shipping_no").val(shipping_no);
                $("#product_ids").val(product_ids.join(","));
                $("#form1").submit();
            }
        });
    }

    $(".btn-close").click(function () {
        var order_sn = $(this).parent().attr("order_sn");
        $("#act").val("close");
        $("#act_order_sn").val(order_sn);
        $("#form1").submit();
    });

    $(".btn-finish").click(function () {
        var order_sn = $(this).parent().attr("order_sn");
        $("#act").val("finish");
        $("#act_order_sn").val(order_sn);
        $("#form1").submit();
    });

    $(".btn-verify").click(function () {
        var that = $(this);
        var order_sn = $(this).parent().attr("order_sn");
        util.ajax_json("/admin/order/has/returning/order?order_sn=" + order_sn, function (d) {
            if (d && d.error == 0) {
                if (d.content.has_returning) {
                    layer.alert("有退款请求需要审批处理，请先处理退款请求!");
                } else {
                    layer.prompt({title: "请输入核销码"}, function (value, index, elem) {
                        $("#act").val("verify");
                        $("#act_order_sn").val(order_sn);
                        var code = $("#verify_code").val(value).val();
                        var verify_code = that.parents().attr("verify_code");
                        var param = {
                            "act_order_sn":order_sn,
                            // "code":code,
                            "act":"verify",
                            "verify_code":verify_code,
                        };
                        if(code == verify_code){
                            util.ajax_json("/admin/order/verify",function (d) {
                                if(d && d.error == 0){
                                    util.mobile_alert("核销成功!");
                                    location.reload();
                                }
                            },param)
                            layer.close(index);
                        }else{
                            util.mobile_alert("核销码有误");
                            $(".layui-layer-input").val("");
                            $(".layui-layer-input").focus();
                        }
                    });
                }
            }
        });
    });

    $(".btn-star-flag").click(function () {
        var _this = $(this);
        var order_sn = $(this).parent().attr("order_sn");
        var flag = $(this).hasClass("empty-flag") ? 1 : 0;
        var param = {
            act: 'star-flag',
            act_order_sn : order_sn,
            act_star_flag: flag
        };
        util.ajax_json("/admin/order/list",function (response) {
            if(response.error == 0){
                flag == 1 ? _this.removeClass('empty-flag') : _this.addClass('empty-flag');
            }else{
                util.mobile_alert(response.message);
            }
        }, param);
    });

    $(".btn-choose").click(function () {
        $('#page').val(1);
        $("#act").val("");
        $("#form1").submit();
    });

    $(".btn-excel").click(function () {
        $('#page').val(1);
        $("#act").val("export_csv");
        var href = $(".order-list-nav .nav-active a").attr('href');
        if(href != '') {
            $("#form1").attr('action', href);
        }
        $("#form1").submit();
        $("#act").val('');
    })

    $(".btn-shipping-query").click(function () {
        var param = {shipping_no: $(this).parent().attr("shipping_no")};
        util.ajax_json("/admin/order/express/query", function (d) {
            if (d && d.error == 0) {
                var table = $(".shipping-info table");
                table.html("");

                var tr = $("#expr_tpl_id table tr").clone();
                tr.find('.rec_time').html("快递公司");
                tr.find('.rec_info').html(d.content.name);
                tr.appendTo(table);

                for (var i = 0; i < d.content.data.length; i++) {
                    var tr = $("#expr_tpl_id table tr").clone();
                    tr.find('.rec_time').html(d.content.data[i].time);
                    tr.find('.rec_info').html(d.content.data[i].context);
                    if (i == 0) {
                        tr.find('.rec_time').addClass("text-warning");
                        tr.find('.rec_info').addClass("text-warning");
                    }
                    tr.appendTo(table);
                }
                layer.open({
                    type: 0,
                    title: "快递单号物流：" + param.shipping_no,
                    area: "700px",
                    content: $(".shipping-dialog").html()
                });
            }
            else if (d && d.error > 0) {
                layer.msg(d.message);
            }
        }, param);
    });

    $(".btn-modify-shipping").click(function () {
        var that = $(this);
        var order_sn = $(this).parents("tr").attr("order_sn");
        var batch_no = $(this).parents("tr").attr("batch_no");
        var shipping_no = $(this).parents("tr").attr("shipping_no");
        var shipping_id = $(this).parents("tr").attr("shipping_id");
        $('#shipping-list').find("select").val(shipping_id);
        $('#template_shipping_no').val(shipping_no);
        layer.open({
            type: 1,
            title: "修改快递运单号",
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
                var shipping_name = $('#shipping-list').find('option[value="' + param.shipping_id + '"]').text();
                util.ajax_json("/admin/order/express/modify", function (d) {
                    if (d && d.error == 0) {
                        layer.close(index);
                        layer.msg("修改成功");
                        $(that).parents("tr").find(".shipping-name").text(shipping_name);
                        $(that).parents("tr").find(".shipping-no").text(param.shipping_no);
                        $(that).parents("tr").attr("shipping_id", param.shipping_id);
                        $(that).parents("tr").attr("shipping_no", param.shipping_no);

                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                }, param);
            },
            btn2: null,
        });

    });

    $(".btn-return").click(function () {
        var order_sn = $(this).parent().attr("order_sn");
        window.location.href = "/admin/order/return/list?order_sn=" + order_sn;
    });

    if($("#deliver_type option:selected").val() == 0){
        $(".mendian").css('display','none');
        $(".kuaidi").css('display','block');
    }

    if($("#deliver_type option:selected").val() == -1){
        $(".mendian").css('display','block');
        $(".kuaidi").css('display','block');
        $(".mendian").css("width","70%");
    }
    if($("#deliver_type option:selected").val() == 1){
        $(".mendian").css("width","62%");
        $(".mendian").css('display','block');
        $(".kuaidi").css('display','none');
    }

    $("#deliver_type").change(function () {
        if($("#deliver_type option:selected").val() == -1){
            $(".mendian").css('display','block');
            $(".kuaidi").css('display','block');
            $(".mendian").css("width","70%");
        }
        if($("#deliver_type option:selected").val() == 0){
            $(".mendian").css('display','none');
            $(".kuaidi").css('display','block');
        }

        if($("#deliver_type option:selected").val() == 1){
            $(".mendian").css('display','block');
            $(".kuaidi").css('display','none');
            $(".mendian").css("width","66%");
        }
    })


}());