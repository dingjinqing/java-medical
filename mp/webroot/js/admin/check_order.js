$(function () {
    //标注星级
    $(".btn-star-flag").click(function () {
        var _this = $(this);
        var order_sn = $(this).parent().attr("order_sn");
        var flag = $(this).hasClass("empty-flag") ? 1 : 0;
        var param = {
            order_sn : order_sn,
            star_flag: flag
        };
        util.ajax_json("/admin/orders/check/list", function (response) {
            if(response.error == 0){
                flag == 1 ? _this.removeClass('empty-flag') : _this.addClass('empty-flag');
            }else{
                util.mobile_alert(response.message);
            }
        }, param);
    });

    $(".btn_search").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    })

    //添加备注弹框
    $('.add_text').on('click', function () {
        var _this = $(this);
        $("#seller_remark").text(_this.attr('seller_remark'));
        layer.open({
            type: 1,
            title: "添加备注",
            content: $('#set-text'),
            area: "300px",
            btn: ['确定', '取消'],
            yes: function (index) {
                var param = {
                    order_sn : _this.parent().attr('order_sn'),
                    seller_remark: $('#seller_remark').val()
                };
                util.ajax_json("/admin/orders/check/list", function (response) {
                    if(response.error == 0){
                        layer.ready(function () {
                            layer.msg("保存成功", {time: 2000},function () {
                                window.location.reload();
                            });
                        });
                    }else{
                        util.mobile_alert(response.message);
                    }
                }, param);
            }
        });
    });


});