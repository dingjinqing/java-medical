/**
 * Created by 新国 on 2015/8/18.
 */

(function () {

    $(".limit_deliver_area").click(function () {
        if ($(this).prop("checked")) {
            $(".def_fee_template").hide();
        } else {
            $(".def_fee_template").show();
        }
    });

    $("#area_template  .expand").click(function () {
        var el = $(this).parent().find(".district-list");
        var el_city_item = $(this).parents(".city-item");
        art.dialog({
            title: "选择区县",
            content: el[0],
            okVal: "确定",
            lock: true,
            padding: 0,
            margin: 0,
            opacity: 0.01,
            ok: function () {
                var len = el.find('[name="district[]"]:checked').length;
                var max_len = el.find('[name="district[]"]').length;
                el_city_item.find('.city-cbx').prop("checked", (len == max_len));
                if (len == 0) {
                    el_city_item.find(".sel-num").text("");
                } else {
                    el_city_item.find(".sel-num").text("(" + len + ")");
                }
                var el_prov_row = el_city_item.parents(".province-row");
                var l1 = el_prov_row.find("input[city_id]:checked").length;
                var l2 = el_prov_row.find("input[city_id]").length;
                if (l1 == l2) {
                    el_prov_row.find("input[province_id]").prop("checked", true);
                    change_province_checked(el_prov_row.find("input[province_id]"), true);
                } else {
                    el_prov_row.find("input[province_id]").prop("checked", false);
                    change_province_checked(el_prov_row.find("input[province_id]"), false);
                }
                return true;
            },
            cancelVal: "取消",
            cancel: function () {
                return true;
            }
        });
    });

    /**
     * 省份选择变化事件处理
     * @param el
     * @param checked
     */
    function change_province_checked(el, checked) {
        var el2 = el.parents("#area_template");
        if (checked) {
            if (el2.find("input[province_id]:not(:checked)").length == 0) {
                el2.find(".sel-all").prop("checked", true);
            }
        } else {
            el2.find(".sel-all").prop("checked", false);
        }
    }

    /**
     * 城市变化事件处理
     * @param el
     * @param checked
     */
    function change_city_checked(el, checked) {
        var el2 = el.parents(".province-row");
        if (checked) {
            if (el2.find("input[city_id]:not(:checked)").length == 0) {
                el2.find("input[province_id]").prop("checked", true);
                change_province_checked(el2.find("input[province_id]"), true);
            }
        } else {
            el2.find("input[province_id]").prop("checked", false);
            change_province_checked(el2.find("input[province_id]"), false);
        }
    }

    /**
     * 区县变化事件处理
     * @param el
     * @param checked
     */
    function change_dist_checked(el, checked) {
        var el2 = el.parents(".city-item");
        if (checked) {
            if (el2.find("input[district_id]:not(:checked)").length == 0) {
                el2.find("input[city_id]").prop("checked", true);
                change_city_checked(el2.find("input[city_id]"), true);
            }
        } else {
            el2.find("input[city_id]").prop("checked", false);
            change_city_checked(el2.find("input[city_id]"), false);
        }
        refresh_one_sel_num(el2.find("input[city_id]"));
    }

    /**
     * 更新城市区县的数字
     * @param el_city_id
     */
    function refresh_one_sel_num(el_city_id) {
        var len = $(el_city_id).parents(".city-item").find(".district-cbx:checked").length;
        if (len == 0) {
            $(el_city_id).parents(".city-item").find(".sel-num").text("");
        } else {
            $(el_city_id).parents(".city-item").find(".sel-num").text("(" + len + ")");
        }
    }

    /**
     * 更新所有城市区县的数字
     */
    function refresh_all_sel_num() {
        $("#area_template input[its_province_id][city_id]").each(function () {
            refresh_one_sel_num($(this));
        });
    }

    /**
     * 省份变化处理
     */
    $("#area_template input[province_id]").change(function () {
        $("#area_template input[its_province_id='" + $(this).val() + "']:not(:disabled)").prop("checked", $(this).prop("checked"));
        change_province_checked($(this), $(this).prop("checked"));
        refresh_all_sel_num();
    });

    /**
     * 城市变化事件
     */
    $("#area_template input[its_province_id][city_id]").change(function () {
        $(this).parents(".city-item").find(".district-cbx:not(:disabled)").prop("checked", $(this).prop("checked"));
        change_city_checked($(this), $(this).prop("checked"));
        refresh_one_sel_num($(this));
    });

    $(".sel-all").click(function () {
        if ($(this).prop("checked")) {
            $("#area_template input[type='checkbox']").prop("checked", $(this).prop("checked"));
        }
        else {
            $("#area_template input[type='checkbox']:not(:disabled)").prop("checked", false);
        }
        refresh_all_sel_num();
    });

    /**
     * 取得地区码类型 0全国，1省 2市 3区县
     * @param code
     * @returns {number}
     */
    function get_area_type(code) {
        if (code == "000000") return 0;
        if (code.substr(2, 4) == "0000") return 1;
        if (code.substr(4, 2) == "00") return 2;
        return 3;
    }

    /**
     * 得到当前选择配送区域的信息
     * @returns {*}
     */
    function get_area_code() {
        var arr = [];
        var code_arr = [];
        var name_arr = {};
        var dist_arr = {};

        $("#area_template input[province_id]:not(:disabled)").each(function () {
            if ($(this).prop("checked")) {
                var len = $(this).parents(".province-row").find("input[its_province_id='" + $(this).attr("province_id") + "']:disabled").length;
                if (len == 0) {
                    code_arr.push($(this).attr("province_id"));
                    name_arr[$(this).attr("province_id")] = {type: 1, name: $(this).attr("province_name")};
                    return true;
                }
            }
            $(this).parents(".province-row").find("input[city_id]:not(:disabled)").each(function () {
                if ($(this).prop("checked")) {
                    var len = $(this).parents(".city-item").find("input[its_city_id='" + $(this).attr("city_id") + "']:disabled").length;
                    if (len == 0) {
                        code_arr.push($(this).attr("city_id"));
                        name_arr[$(this).attr("city_id")] = {type: 2, name: $(this).attr("city_name")};
                        return true;
                    }
                }
                $(this).parents(".city-item").find("input[district_id]:checked").each(function () {
                    if (!$(this).prop("disabled")) {
                        code_arr.push($(this).attr("district_id"));
                        var el_city = $(this).parents(".city-item").find("input[city_id]");
                        var city_id = el_city.attr("city_id");
                        var city_name = el_city.attr("city_name");
                        if (name_arr[city_id] == undefined)
                            name_arr[city_id] = {
                                type: 3,
                                name: city_name,
                                num: 1,
                                dist_arr: [$(this).attr("district_name")]
                            };
                        else {
                            name_arr[city_id].dist_arr.push($(this).attr("district_name"));
                            name_arr[city_id].num = name_arr[city_id].num + 1;
                        }
                    }
                });
            });

        });
        if (code_arr.length == 0) {
            util.mobile_alert("没有选中区域！");
            return false;
        }
        var html = [];
        for (var i in name_arr) {
            if (name_arr[i].type == 3) {
                var dist = name_arr[i].dist_arr.join("、");
                var str = "<span class='city-dist' title='" + dist + "'>" + name_arr[i].name + "<span class='dist-num'>(" + name_arr[i].num + ")</span></span>";
                html.push(str);
            } else {
                html.push(name_arr[i].name);
            }
        }
        html = html.join("、");
        var t = {
            code: $.toJSON(code_arr),
            name: name_arr,
            html: html
        };
        return t;
    }

    /**
     * 初始化选择区域
     * @param el_tbl 优惠tbl或者基本运费tbl
     * @param is_add 是否添加模式
     * @param data_code 如果为编辑模式，当前为编辑的code
     */
    function init_area(el_tbl, is_add, data_code) {
        $("#area_template input[type='checkbox']").prop("checked", false);
        $("#area_template input[type='checkbox']").prop("disabled", false);
        el_tbl.find("span[area-data-code]").each(function () {
            var t = $(this).attr("area-data-code");
            try {
                var code_list = $.parseJSON(t);
                for (var i in code_list) {
                    var code = code_list[i];
                    switch (get_area_type(code)) {
                        case 0:
                        {
                            $("#area_template input[type='checkbox']").prop("checked", true);
                            if (is_add || !is_add && t != data_code) {
                                $("#area_template input[type='checkbox']").prop("disabled", true);
                            }
                            break;
                        }
                        case 1:
                        {
                            $("#area_template input[province_id='" + code + "']").prop("checked", true);
                            $("#area_template input[its_province_id='" + code + "']").prop("checked", true);
                            if (is_add || !is_add && t != data_code) {
                                $("#area_template input[province_id='" + code + "']").prop("disabled", true);
                                $("#area_template input[its_province_id='" + code + "']").prop("disabled", true);
                            }
                            change_province_checked($("#area_template input[province_id='" + code + "']"), true);
                            break;
                        }
                        case 2:
                        {
                            $("#area_template input[city_id='" + code + "']").prop("checked", true);
                            $("#area_template input[its_city_id='" + code + "']").prop("checked", true);
                            if (is_add || !is_add && t != data_code) {
                                $("#area_template input[city_id='" + code + "']").prop("disabled", true);
                                $("#area_template input[its_city_id='" + code + "']").prop("disabled", true);
                            }
                            change_city_checked($("#area_template input[city_id='" + code + "']"), true);
                            break;
                        }

                        case 3:
                        {
                            $("#area_template input[district_id='" + code + "']").prop("checked", true);
                            if (is_add || !is_add && t != data_code) {
                                $("#area_template input[district_id='" + code + "']").prop("disabled", true);
                            }
                            change_dist_checked($("#area_template input[district_id='" + code + "']"), true);
                            break;
                        }
                    }
                }
            } catch (e) {
            }
        });
        refresh_all_sel_num();
    }


    /**
     * 选择配送区域
     */
    function select_area(op_link_obj) {
        var tr = null;
        var data_code = null;
        var tbl_id = "#deliver_template_tbl";
        var is_add = true;
        var clone_tr_sel = ".area-row-info tr";
        if ($(op_link_obj).hasClass("edit-area")) {
            is_add = false;
        } else if ($(op_link_obj).hasClass("edit-fee-0-area")) {
            is_add = false;
            tbl_id = "#deliver_fee_0_tbl";
        } else if ($(op_link_obj).hasClass("add-fee-0-deliver-area")) {
            is_add = true;
            tbl_id = "#deliver_fee_0_tbl";
            clone_tr_sel = ".fee-0-area-row-info tr";
        }
        if (!is_add) {
            tr = $($(op_link_obj).parents("tr")[0]);
            data_code = tr.find("span[area-data-code]").attr("area-data-code");
        }
        init_area($(tbl_id), is_add, data_code);

        art.dialog({
            title: "请选择配送区域",
            content: $("#area_template")[0],
            lock: true,
            opacity: 0.02,
            padding: 0,
            margin: 0,
            okVal: "确定",
            ok: function () {
                var t = get_area_code();
                if (t == false) return false;
                if (is_add)
                    tr = $(clone_tr_sel).clone();
                tr.find("span[area-data-code]").attr("area-data-code", t.code);
                tr.find("span[area-data-code]").html(t.html);
                tr.find("span[area-data-code]").attr("area-data-text", t.html);
                if (is_add)
                    tr.insertBefore($(tbl_id + "  tr:last"));
                return true;
            },
            cancelVal: "取消",
            cancel: function () {
                return true;
            }
        })
    }

    $(".add-deliver-area").click(function () {
        return select_area(this);
    });

    $(document).on("click", ".edit-area", function () {
        return select_area(this);
    });

    $(".add-fee-0-deliver-area").click(function () {
        return select_area(this);
    });

    $(document).on("click", ".edit-fee-0-area", function () {
        return select_area(this);
    });

    $(document).on("click", ".del-deliver", function () {
        var tr2 = $(this).parents("tr.tr-area").next(".tr-area2");
        tr2.remove();
        var tr = $(this).parents("tr.tr-area");
        tr.remove();
    });

    $(document).on("click", ".del-fee-0-deliver", function () {
        var tr2 = $(this).parents("tr.tr-fee-0-area").next(".tr-area2");
        tr2.remove();
        var tr = $(this).parents("tr.tr-fee-0-area");
        tr.remove();
    });

    function check_is_valid_template() {
        if ($("#template_name").val() == "") {
            util.mobile_alert("模板名称不能为空");
            $("#template_name").focus();
            return false;
        }
        var input = $(".template-container input[type='text']");
        // for (var i = 0; i < input.length; i++) {
            // var el = $(input[i]);
            // var n = el.attr("name");
            // switch (n) {
                // case "first_num":
                // {
                    // var t = parseInt(el.val());
                    // if (isNaN(t) || !isNaN(t) && t <= 0) {
                        // layer.msg("首件件数必须为大于0的整数");
                        // el.focus();
                        // return false;
                    // }
                    // break;
                // }
                // case "first_fee":
                // {
                    // var t = parseFloat(el.val());
                    // if (isNaN(t) || !isNaN(t) && t < 0) {
                        // layer.msg("首件运费必须为大于0的数");
                        // el.focus();
                        // return false;
                    // }
                    // break;
                // }
                // case "continue_num":
                // {
                    // var t = parseInt(el.val());
                    // if (isNaN(t) || !isNaN(t) && t <= 0) {
                        // layer.msg("续件件数必须为大于0的整数");
                        // el.focus();
                        // return false;
                    // }
                    // break;
                // }
                // case "continue_fee":
                // {
                    // var t = parseFloat(el.val());
                    // if (isNaN(t) || !isNaN(t) && t < 0) {
                        // layer.msg("续件运费必须为大于0的数");
                        // el.focus();
                        // return false;
                    // }
                    // break;
                // }
            // }
        // }

        // var has_fee_0_condition = $(".fee-0-cbk").prop("checked") ? 1 : 0;
        // var fee_0_data_list = [];
        // if (has_fee_0_condition) {
            // var tr_list = $("#deliver_fee_0_tbl tr.tr-fee-0-area");
            // for (var i = 0; i < tr_list.length; i++) {
                // switch ($(tr_list[i]).find(".fee-0-condition").val()) {
                    // case "1":
                    // {
                        // var el = $(tr_list[i]).find(".fee_0_con1_num");
                        // var t = parseInt(el.val());
                        // if (isNaN(t) || !isNaN(t) && t <= 0) {
                            // layer.msg("件数必须为大于0的整数");
                            // el.focus();
                            // return false;
                        // }
                        // break;
                    // }
                    // case "2":
                    // {
                        // var el = $(tr_list[i]).find(".fee_0_con2_fee");
                        // var t = parseFloat(el.val());
                        // if (isNaN(t) || !isNaN(t) && t < 0) {
                            // layer.msg("金额必须为大于等于0的整数");
                            // el.focus();
                            // return false;
                        // }
                        // break;
                    // }
                    // case "3":
                    // {
                        // var el = $(tr_list[i]).find(".fee_0_con3_num");
                        // var t = parseInt(el.val());
                        // if (isNaN(t) || !isNaN(t) && t <= 0) {
                            // layer.msg("件数必须为大于0的整数");
                            // el.focus();
                            // return false;
                        // }
                        // el = $(tr_list[i]).find(".fee_0_con3_fee");
                        // t = parseInt(el.val());
                        // if (isNaN(t) || !isNaN(t) && t < 0) {
                            // layer.msg("金额必须为大于等于0的整数");
                            // el.focus();
                            // return false;
                        // }
                        // break;
                    // }
                // }
            // }
        // }
         return true;
    }


    $(".btn-save").click(function () {
        if (!check_is_valid_template())
            return false;
        var first_num = parseFloat($(".def_fee_template .first_num").val());
        var first_fee = parseFloat($(".def_fee_template .first_fee").val());
        var first_installation_fee = parseFloat($(".def_fee_template .first_installation_fee").val());
        var continue_num = parseFloat($(".def_fee_template .continue_num").val());
        var continue_fee = parseFloat($(".def_fee_template .continue_fee").val());
        var continue_installation_fee = parseFloat($(".def_fee_template .continue_installation_fee").val());
        var recommend_collocation_id = '';
        $(".def_fee_template .collocation").find('.data_tr').each(function () {
            var pid = $(this).attr('pid');
            recommend_collocation_id += pid + ",";
        });

        recommend_collocation_id = recommend_collocation_id.substring(0, recommend_collocation_id.length - 1);
        var def_fee_temp = {
            limit_deliver_area: $(".limit_deliver_area").prop("checked") ? 1 : 0,
            area_list: '0', area_text: "全国（其他地区）", first_num: first_num,
            first_fee: first_fee, first_installation_fee: first_installation_fee, continue_num: continue_num,
            continue_fee: continue_fee, continue_installation_fee: continue_installation_fee,
            recommend_collocation_id: recommend_collocation_id
        };
        var data_list = [def_fee_temp];
        $("#deliver_template_tbl tr.tr-area").each(function () {

            recommend_collocation_id = '';
            $(this).next(".tr-area2").find(".collocation").find('.data_tr').each(function () {
                var pid = $(this).attr('pid');
                recommend_collocation_id += pid + ",";
            });
            recommend_collocation_id = recommend_collocation_id.substring(0, recommend_collocation_id.length - 1);
            var fee_temp = {
                area_list: $(this).find("span[area-data-code]").attr("area-data-code"),
                area_text: $(this).find("span[area-data-code]").attr("area-data-text"),
                first_num: parseFloat($(this).find(".first_num").val()),
                first_fee: parseFloat($(this).find(".first_fee").val()),
                first_installation_fee: parseFloat($(this).find(".first_installation_fee").val()),
                continue_num: parseFloat($(this).find(".continue_num").val()),
                continue_fee: parseFloat($(this).find(".continue_fee").val()),
                continue_installation_fee: parseFloat($(this).find(".continue_installation_fee").val()),
                recommend_collocation_id: recommend_collocation_id
            };
            data_list.push(fee_temp);
        });
        var has_fee_0_condition = $(".fee-0-cbk").prop("checked") ? 1 : 0;
        var fee_0_data_list = [];
        if (has_fee_0_condition) {
            $("#deliver_fee_0_tbl tr.tr-fee-0-area").each(function () {
                recommend_collocation_id = '';
                $(this).next(".tr-area2").find(".collocation").find('.data_tr').each(function () {
                    var pid = $(this).attr('pid');
                    recommend_collocation_id += pid + ",";
                    //alert(recommend_collocation_id);
                });
                recommend_collocation_id = recommend_collocation_id.substring(0, recommend_collocation_id.length - 1);
                //alert(recommend_collocation_id);
                var fee_temp = {
                    area_list: $(this).find("span[area-data-code]").attr("area-data-code"),
                    area_text: $(this).find("span[area-data-code]").attr("area-data-text"),
                    fee_0_condition: $(this).find(".fee-0-condition").val(),
                    fee_0_con1_num: parseFloat($(this).find(".fee_0_con1_num").val()),
                    fee_0_con2_fee: parseFloat($(this).find(".fee_0_con2_fee").val()),
                    fee_0_con3_num: parseFloat($(this).find(".fee_0_con3_num").val()),
                    fee_0_con3_fee: parseFloat($(this).find(".fee_0_con3_fee").val()),
                    fee_0_con4_fee: parseFloat($(this).find(".fee_0_con4_fee").val()),
                    recommend_collocation_id: recommend_collocation_id

                };
                fee_0_data_list.push(fee_temp);
            });
        }

        if (data_list.length == 1 && $(".limit_deliver_area").prop("checked") && fee_0_data_list.length == 0) {
            util.mobile_alert("请添加可配送区域");
            return false;
        }

        var template_content = {
            data_list: data_list,
            has_fee_0_condition: has_fee_0_condition,
            fee_0_data_list: fee_0_data_list
        };
        template_content = $.toJSON(template_content);
        $("#template_content").val(template_content);
        $("#form1").submit();
    });

    $(".fee-0-cbk").change(function () {
        if ($(this).prop("checked")) {
            $("#deliver_fee_0_tbl").removeClass("hide");
        } else {
            $("#deliver_fee_0_tbl").addClass("hide");
        }
    });

    $(document).on("change", ".fee-0-condition", function () {
        $(this).parent().find("span").addClass("hide");
        $(this).parent().find(".fee_0_con4").removeClass("hide");
        switch ($(this).val()) {
            case "1":
            {
                $(this).parent().find(".fee_0_con1").removeClass("hide");
                break;
            }
            case "2":
            {
                $(this).parent().find(".fee_0_con2").removeClass("hide");
                break;
            }
            case "3":
            {
                $(this).parent().find(".fee_0_con3").removeClass("hide");
                break;
            }
        }
    });

    $(document).ready(function () {
        // 去掉消息提示
        setTimeout(function () {
            $(".msg-info").fadeOut(500);
        }, 2000);
    });
    var collocation_arr = [];

    $(document).on("click", ".add-collocation", function () {
        //$(".add-collocation").click(function () {
        var obj = $(this).parents(".tr-area2").find(".collocation");
        //alert(obj.html())
        var recommend_collocation_id = '';
        var collocation_str = '';
        obj.find('.data_tr').each(function () {
            var pid = $(this).attr('pid');
            recommend_collocation_id += pid + ",";
        });

        recommend_collocation_id = recommend_collocation_id.substring(0, recommend_collocation_id.length - 1);
        if (1) {
            //recommend_collocation_id = obj.attr("recommend_collocation_id");
            if (recommend_collocation_id == '') {
                collocation_arr = [];
            }
            var collocation_arrs = recommend_collocation_id.split(",");
            $.each(collocation_arrs, function (key, value) {
                if (value > 0) {
                    collocation_arr[value] = value;
                } else {
                    collocation_arr[value] = '';
                }
            });
        }
        //alert("old id："+recommend_collocation_id);
        //alert(JSON.stringify(collocation_arr));
        var url = "?c=good&m=goods_collocation_list_forpost&recommend_collocation_id=" + recommend_collocation_id;

        g_dlg = art.dialog.open(url, {
            title: "链接小工具",
            width: 950,
            height: 350,
            padding: 0,
            // 在open()方法中，init会等待iframe加载完毕后执行
            init: function () {
            },
            lock: true,
            okVal: "确定",
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                }
                ;
                var tr = $("#data_list tr[data_row].active", iframe.document);
                if (tr.length == 0) {
                    util.mobile_alert("没有选中任何项");
                    return false;
                }

                $("[name='collocation_arr']").val('');
                $("#data_list tr[data_row].active", iframe.document).each(
                    function () {
                        if ($(this).attr("pid") != 'undefined') {
                            collocation_id = $(this).attr("pid");
                            collocation_arr[$(this).attr("pid")] = $(this).attr("pid");
                        }
                    }
                );

                //alert(JSON.stringify(collocation_arr));
                obj.html("");
                var html = '';
                var i = 0;
                var j = 0;
                $.each(collocation_arr, function (key, value) {
                    if (value > 0) {
                        j++;
                    }
                });
                //alert(html);
                //parent_id 0 主
                $.each(collocation_arr, function (key, value) {
                    if (value > 0) {
                        collocation_str += value + ",";
                        //alert("start:"+value)
                        var param = {id: value};
                        //alert(JSON.stringify(param));
                        util.ajax_json('?c=good&m=goods_collocation_list_forpost', function (d) {
                            if (d && d.error == 0) {
                                if (d.content) {
                                    //alert(JSON.stringify(d.content));
                                    //if(i==0){
                                    html = '<table id="data_list"  cellspacing="1" cellpadding="3" width="100%" '
                                        + 'class="table table-striped" style="border: dashed;">'
                                        + '<tr>'
                                        + '<th class="text-center" width="10%">类型</th> '
                                        + '<th class="text-center" width="10%">商品ID</th> '
                                        + '<th class="text-center" width="10%">图片</th>    '
                                        + '<th class="text-center" width="20%">商品名称</th> '
                                        + '<th class="text-center" width="10%">商品规格</th>	'
                                        + '<th class="text-center" width="10%">售价</th>		'
                                        + '<th class="text-center" width="20%">优惠'
                                        + '<div class="pull-right del_collocation">'
                                        + '		<a href="javascript:void(0);">删除</a>'
                                        + '	</div></th>'
                                        + '</tr>';
                                    //}
                                    html += '<tr pid="' + d.content[0].id + '" class="data_tr" data_row>'
                                        + '<td>主商品</td>'
                                        + '<td>' + d.content[0].goods_id + '</td>'
                                        + '<td>'
                                        + '	<a href="' + value.url + '" target="_blank">'
                                        + '		<img class="img-thumbnail" src="/' + d.content[0].goods_img + '" >'
                                        + '	</a>'
                                        + '</td>'
                                        + '<td>' + d.content[0].goods_name + '</td>'
                                        + '<td></td>'
                                        + '<td><span style="color:red;">' + d.content[0].goods_price + '</span></td>'
                                        + '<td>'
                                        + '		总金额:<span style="color:red;">' + d.content[0].money + '</span><br/>'
                                        + '		优惠金额:<span style="color:red;">' + d.content[0].discount + '</span><br/>'
                                        + '		优惠后金额:<span style="color:red;">' + d.content[0].yh + '</span>'
                                        + '		<div class="pull-right show_collocation">'
                                        + '			<a href="javascript:void(0);">查看详情</a>'
                                        + '		</div>'
                                        + '</td>'
                                        + '</tr>';

                                    var htm_spac = '';
                                    if (typeof(d.content[0].spec_list) == 'undefined') {
                                        alert("您选择的套餐数据不完整，请添加套餐子商品");
                                        return;
                                    }
                                    $.each(d.content[0].spec_list, function (key, value) {
                                        htm_spac += '<tr style="display:none;">'
                                            + '	<td>从商品</td>'
                                            + '	<td>' + value.goods_id + '</td>'
                                            + '	<td>'
                                            + '		<a href="' + value.url + '" target="_blank">'
                                            + '			<img src="' + value.goods_img + '" class="img-thumbnail"/>'
                                            + '		</a>'
                                            + '	</td>'
                                            + '	<td>' + value.goods_name + '</td>'
                                            + '	<td>' + value.prd_desc + '</td>'
                                            + '	<td>'
                                            + '		<span style="color:red;">' + value.prd_price + '</span>'
                                            + '	</td>'
                                            + '	<td></td>'
                                            + '</tr>';
                                    });
                                    html += htm_spac;
                                    //if(i==j-1){
                                    html += '</table>';
                                    obj.append(html);
                                    //}

                                    i++;
                                }
                            } else if (d && d.error > 0) {
                                util.mobile_alert(d.message);
                            }
                        }, param);
                        //alert("end:"+value)
                        return true;
                    }
                });
                collocation_str = collocation_str.substring(0, collocation_str.length - 1);
                //alert(collocation_str);
                obj.attr("recommend_collocation_id", collocation_str);
                return true;
            },
            cancelVal: "取消",
            cancel: function () {

            }
        });
    });
    $(document).on("mouseover", ".del_collocation", function () {
        var table = $(this).parent().parent().parent().parent();
        table.css("background-color", "#0ff");
    });
    $(document).on("mouseout", ".del_collocation", function () {
        var table = $(this).parent().parent().parent().parent();
        table.css("background-color", "#fff");
    });
    $(document).on("click", ".del_collocation", function () {
        var collocation_id = $(this).parents('tr').next('.data_tr').attr("pid");
        //alert(collocation_id);
        var table = $(this).parent().parent().parent().parent();
        var collocation_temp = '';
        if (table.parent().attr("recommend_collocation_id") != '') {
            var recommend_collocation_id = table.parent().attr("recommend_collocation_id");
            var collocation_arrs = recommend_collocation_id.split(",");
            $.each(collocation_arrs, function (key, value) {
                if (value > 0) {

                    if (value != collocation_id) {
                        collocation_arr[value] = value;
                        collocation_temp += value + ",";
                    } else {
                        collocation_arr[value] = '';
                    }
                }
            });
        }

        collocation_temp = collocation_temp.substring(0, collocation_temp.length - 1);
        table.parent().attr("recommend_collocation_id", collocation_temp);
        //alert(JSON.stringify(collocation_arr));
        table.remove();
    });
    $(document).on("click", ".show_collocation", function () {
        $(this).parents('tr').nextAll('tr').toggle();
        var show = $(this).parents('tr').nextAll('tr').css('display');
        if (show == 'none') {
            $(this).find('a').html("查看详情");
        } else {
            $(this).find('a').html("关闭详情");
        }
    });
}());