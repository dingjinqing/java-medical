(function ($) {
    $("#list_show").click(function () {
        $("#bigimg_data").hide();
        $("#list_data").show();
        $('[name="show_type"]').val('list');
    });

    $("#bigimg_show").click(function () {
        $("#bigimg_data").show();
        $("#list_data").hide();
        $('[name="show_type"]').val('bigimg');
    });

    $("#selall").change(function () {
        if ($(this).prop('checked')) {
            $("input[cbx_list]").prop('checked', 'checked');
            $("input[cbx_bigimg]").prop('checked', 'checked');
        } else {
            $("input[cbx_list]").prop('checked', false);
            $("input[cbx_bigimg]").prop('checked', false);
        }
    });

    $("input[cbx_list]").change(function () {
        var img_id = $(this).val();
        if ($(this).prop('checked')) {
            $("input[cbx_bigimg][value='" + img_id + "']").prop('checked', 'checked');
        } else {
            $("input[cbx_bigimg][value='" + img_id + "']").prop('checked', false);
        }
        if ($("input[cbx_list]:checked").length == $("input[cbx_list]").length) {
            $("#selall").prop('checked', 'checked');
        } else {
            $("#selall").prop('checked', false);
        }

    });
    $("input[cbx_bigimg]").change(function () {
        var img_id = $(this).val();
        if ($(this).prop('checked')) {
            $("input[cbx_list][value='" + img_id + "']").prop('checked', 'checked');
        } else {
            $("input[cbx_list][value='" + img_id + "']").prop('checked', false);
        }
        if ($("input[cbx_list]:checked").length == $("input[cbx_list]").length) {
            $("#selall").prop('checked', 'checked');
        } else {
            $("#selall").prop('checked', false);
        }
    });

    var g_dlg = null;

    function set_cat_id() {
        if (g_dlg) {
            g_dlg.close();
        }
        var cat_el = $("#upload_img_cat_id").clone().attr("id", "upload_img_cat_id_art");
        g_dlg = art.dialog({
            title: js_lang_set_cat_name,
            content: cat_el[0],
            okVal: js_lang_set_cat_name,
            ok: function () {
                $("#act").val('set_cat_id');
                $("#set_cat_id").val($('#upload_img_cat_id_art option:selected').val());
                $("#form1").submit();
                return true;
            },
            cancelVal: js_lang_cancel,
            cancel: function () {
            }
        });
    }


    $("#operate").click(function () {
        var op = $("#op_type option:selected").val();
        if (op == '0') {
            layer.msg(js_lang_err_select_operation);
            return;
        }
        var show_type = $('[name="show_type"]').val();
        if (show_type == 'list' && $("input[cbx_list]:checked").length == 0
            || show_type == 'bigimg' && $("input[cbx_bigimg]:checked").length == 0) {
            layer.msg(js_lang_err_select_image);
            return;
        }
        if (op == '3') {
            set_cat_id();
            return;
        }
        var cbx = (show_type == 'list') ? $("input[cbx_list]:checked") : $("input[cbx_bigimg]:checked");
        var el = $("<textarea style='width:600px;height:200px;overflow:auto;'></textarea>");
        cbx.each(function () {
            var txt = (op == '1') ? $(this).attr('url') : "<img src='" + $(this).attr('url') + "' >";
            var t = el.text();
            if (t == '') {
                el.text(txt);
            } else {
                el.text(t + "\n" + txt);
            }
        });
        if (g_dlg) {
            g_dlg.close();
        }
        var title = (op == '1') ? js_lang_copy_img_url : js_lang_copy_img_url_code;
        g_dlg = art.dialog({
            padding: 0,
            title: title,
            content: el[0],
            okVal: js_lang_ok,
            ok: function () {
                return true;
            },
            cancelVal: js_lang_cancel,
            cancel: function () {
            }
        });
    });

    $("#bigimg_data a.crop_image").click(function (e) {
        var w = parseInt($("#need_img_width").val());
        var h = parseInt($("#need_img_height").val());
        var opts = {
            remote_img_path: $(this).attr('img_path'),
            remote_img_url: $(this).attr('url'),
            remote_img_id: $(this).attr('img_id'),
            ok_cb: function (img_info) {
                var on_img_cb = $("#on_img_cb").val();
                if (on_img_cb && window.parent.window[on_img_cb]) {
                    on_img_cb = window.parent.window[on_img_cb];
                    on_img_cb(img_info);
                }
            }
        };
        if (!isNaN(w) && w > 0) opts.crop_width = w;
        if (!isNaN(h) && h > 0) opts.crop_height = h;
        $.jCropRemoteImage(opts);
    });

    $("#bigimg_data li img").click(function () {
        var w = parseInt($("#need_img_width").val());
        var h = parseInt($("#need_img_height").val());
        var el = $(this).parents("li").find("[cbx_bigimg]");

        if (w > 0 && parseInt(el.attr("img_width")) != w || h > 0 && parseInt(el.attr("img_height")) != h) {
            $(this).parents("li").find("a.crop_image").click();
        } else {
            el.click();
            var on_img_cb = $("#on_img_cb").val();
            if (on_img_cb && window.parent.window[on_img_cb]) {
                on_img_cb = window.parent.window[on_img_cb];
                var o = {img_id: $(el).val(), url: $(el).attr('url'), path: $(el).attr('img_path')};
                on_img_cb(o);
            } else {
                if (el.prop("checked")) {
                    $(this).parents("li").find(".img_sel").show();
                } else {
                    $(this).parents("li").find(".img_sel").hide();
                }

            }
        }
    });

    $('[name="img_cat_id"]').change(function () {
        $("#form1").submit();
    });

    $("#search_need").click(function () {
        $("#form1").submit();
    });

    var formData = {
        dynamic_data: [{
            name: "img_cat_id",
            selector: "#upload_img_cat_id"
        }]
    };
    util.init_image_upload('up_image_btn', function (d) {
        if (d && d.error == 0) {
            $("#page").val(1);
            $("#form1").submit();
        } else {
            layer.msg(d.message);
        }
    }, formData, true);


})(jQuery);
