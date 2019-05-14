jQuery(function ($) {

    $('[name="province_code"]').change(function () {
        var province_id = $(this).val();
        if (province_id == '0') {
            $('[name="city_code"] option:not(:first)').remove();
            $('[name="district_code"] option:not(:first)').remove();
            return;
        }
        var param = {province_id: province_id};
        util.ajax_json("/admin/?c=shop&m=get_city_list", function (d) {
            if (d && d.error == 0) {
                $('[name="city_code"] option:not(:first)').remove();
                $('[name="district_code"] option:not(:first)').remove();
                for (var i in d.message) {
                    var t = d.message[i];
                    $("<option></option>").val(t['city_id']).text(t['name']).appendTo($('[name="city_code"]'));
                }
            }
        }, param);
    });

    $('[name="city_code"]').change(function () {
        var city_id = $(this).val();
        if (city_id == '0') {
            $('[name="district_code"] option:not(:first)').remove();
            return;
        }
        var param = {city_id: city_id};
        util.ajax_json("/admin/?c=shop&m=get_district_list", function (d) {
            if (d && d.error == 0) {
                $('[name="district_code"] option:not(:first)').remove();
                for (var i in d.message) {
                    var t = d.message[i];
                    $("<option></option>").val(t['district_id']).text(t['name']).appendTo($('[name="district_code"]'));
                }
            }
        }, param);
    });

    // tab切换
    $("[data-toggle='tab']").click(function () {
        var idx = $(this).parent().index();
        if (idx == 1) {
            window.location.href = "/admin/?c=shop&m=modify_receive_mobile";
            return;
        }
    });

    $('[name="save"]').click(function () {
        var idx = $("li.active").index();
        switch (idx) {
            case 0:
                return save_shop_base_info();
        }
    });

    // 保存店铺基本信息
    function save_shop_base_info() {
        if ($('[name="shop_name"]').val() == '') {
            layer.msg(window.jslang.shop_info.shop_name_cant_null);
            return;
        }
        var param = {
            shop_name: $('[name="shop_name"]').val(),
            mobile: $('[name="mobile"]').val(),
            shop_phone: $('[name="shop_phone"]').val(),
            shop_qq: $('[name="shop_qq"]').val(),
            shop_notice: $('[name="shop_notice"]').val(),
            shop_wx: $('[name="shop_wx"]').val(),
            shop_sell_type: $('[name="shop_sell_type"]').val(),
            province_code: $('[name="province_code"]').val(),
            city_code: $('[name="city_code"]').val(),
            district_code: $('[name="district_code"]').val(),
            address: $('[name="address"]').val(),
            business_state: $('[name="business_state"]').val(),
            shop_stat_code: $('[name="shop_stat_code"]').val(),
            shop_icp: $('[name="shop_icp"]').val(),
            shop_copyright: $('[name="shop_copyright"]').val(),
            shop_email: $('[name="shop_email"]').val(),
            site_keywords: $('[name="site_keywords"]').val(),
            site_description: $('[name="site_description"]').val(),
        };
        util.ajax_json("/admin/?c=shop&m=save_shop_info&type=base", function (d) {
            if (d && d.error == 0) {
                layer.msg(window.jslang.shop_info.save_shop_success);
            } else if (d) {
                layer.msg(d.message);
            }
        }, param);
    }

    $('[name="save"]').click(function () {
        save_shop_base_info();
    });
    $('[name="state"]').change(function () {
        if ($(this).val() == 2) {
            $('.reject').show();
        } else {
            $('.reject').hide();
        }
    });
});

function change_sku(index) {
    var max_sku_num = shop_grade[index - 1].max_sku_num;
    var manage_fee = shop_grade[index - 1].manage_fee;
    $('[name="max_sku_num"]').val(max_sku_num);
    $('[name="manage_fee"]').val(manage_fee);
}
