(function () {
    window.region_util = {
        select_province_el_name: "province_code",
        select_city_el_name: "city_code",
        select_district_el_name: "district_code",
        get_province_list: function (cb) {
            util.ajax_json("/region/province/list", function (d) {
                if (d && d.error == 0) {
                    cb(d.content);
                }
            });
        },
        get_city_list: function (province_id, cb) {
            var param = {province_id: province_id};
            util.ajax_json("/region/city/list", function (d) {
                if (d && d.error == 0) {
                    cb(d.content);
                }
            }, param);
        },
        get_district_list: function (city_id, cb) {
            var param = {city_id: city_id};
            util.ajax_json("/region/district/list", function (d) {
                if (d && d.error == 0) {
                    cb(d.content);
                }
            }, param);
        },
        set_el_name: function (province_el_name, city_el_name, district_el_name) {
            this.select_province_el_name = province_el_name;
            this.select_city_el_name = city_el_name;
            this.select_district_el_name = district_el_name;
            return this;
        },
        init_ev: function () {
            var that = this;
            var province_el = $('[name="' + this.select_province_el_name + '"]');
            var city_el = $('[name="' + this.select_city_el_name + '"]');
            var district_el = $('[name="' + this.select_district_el_name + '"]');
            $(province_el).change(function () {
                var op0_el = $(city_el).find("option:eq(0)");
                if (op0_el.length > 0 && op0_el.val().length != 6) {
                    $(city_el).find("option:gt(0)").remove();
                    $(district_el).find("option:gt(0)").remove();
                } else {
                    $(district_el).find("option").remove();
                }
                if ($(this).val().length == 6) {
                    that.get_city_list($(this).val(), function (d) {
                        for (var i in d) {
                            $("<option></option>").text(d[i].name).val(d[i].city_id).appendTo(city_el);
                        }
                    });
                }
            });

            $(city_el).change(function () {
                var op0_el = $(district_el).find("option:eq(0)");
                if (op0_el.length > 0 && op0_el.val().length != 6) {
                    $(district_el).find("option:gt(0)").remove();
                } else {
                    $(district_el).find("option").remove();
                }
                if ($(this).val().length == 6) {
                    that.get_district_list($(this).val(), function (d) {
                        for (var i in d) {
                            $("<option></option>").text(d[i].name).val(d[i].district_id).appendTo(district_el);
                        }
                    });
                }
            });
            return this;
        }
    };
}());

