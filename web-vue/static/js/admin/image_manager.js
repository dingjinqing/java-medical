(function ($) {

    $("#convert_view_mode").click(function () {
        if ($('[name="show_type"]').val() == 'list') {
            $('[name="show_type"]').val('bigimg');
            $("#bigimg_data").show();
            $("#list_data").hide();
            $(this).val(window.jslang.image_manager.list_show);
        } else {
            $('[name="show_type"]').val('list');
            $("#bigimg_data").hide();
            $("#list_data").show();
            $(this).val(window.jslang.image_manager.big_image_show);
        }
    });

    function remove_item(img_id) {
        if (!img_id) {
            var show_type = $('[name="show_type"]').val();
            if (show_type == 'list' && $("input[cbx_list]:checked").length == 0
                || show_type == 'bigimg' && $("input[cbx_bigimg]:checked").length == 0) {
                layer.msg(window.jslang.image_manager.select_del_image);
                return false;
            }
        }
        // if (!confirm(window.jslang.image_manager.confirm_del_image)) {
        //     return false;
        // }
        if (img_id) {
            $("input[cbx_list]").prop('checked', false);
            $("input[cbx_bigimg]").prop('checked', false);
            $("input[cbx_bigimg][value='" + img_id + "']").prop('checked', 'checked');
            $("input[cbx_list][value='" + img_id + "']").prop('checked', 'checked');
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: left;">' + '确认要删除么' + '</div>', {
                title: '提示'
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("#act").val("del");
                $("#form1").submit();
                layer.close(index);
            });
        });
        // $("#act").val("del");
        // $("#form1").submit();
    }


    $("#selall").change(function () {
        if ($(this).prop('checked')) {
            $("input[cbx_list]").prop('checked', 'checked');
            $("input[cbx_bigimg]").prop('checked', 'checked');
            $("li .img_sel").show();
        } else {
            $("input[cbx_list]").prop('checked', false);
            $("input[cbx_bigimg]").prop('checked', false);
            $("li .img_sel").hide();
        }
    });

    $("input[cbx_list]").change(function () {
        var img_id = $(this).val();
        var el = $("input[cbx_bigimg][value='" + img_id + "']");
        if ($(this).prop('checked')) {
            el.prop('checked', true);
            el.parents("li").find(".img_sel").show();
        } else {
            el.prop('checked', false);
            el.parents("li").find(".img_sel").hide();
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
            title: window.jslang.image_manager.set_cat_name,
            content: cat_el[0],
            okVal: window.jslang.image_manager.set_cat_name,
            ok: function () {
                $("#act").val('set_cat_id');
                $("#set_cat_id").val($('#upload_img_cat_id_art').val());
                $("#form1").submit();
                return true;
            },
            cancelVal: window.jslang.image_manager.cancel,
            cancel: function () {
            }
        });
    }


    function operate_submit(op) {
        var show_type = $('[name="show_type"]').val();
        var el_chk = $("input[cbx_list]:checked");
        var el_bigimg_chk = $("input[cbx_bigimg]:checked");
        if (show_type == 'list' && el_chk.length == 0
            || show_type == 'bigimg' && el_chk.length == 0) {
            layer.msg(window.jslang.image_manager.select_image);
            return;
        }
        if (op == '3') {
            set_cat_id();
            return;
        }
        var cbx = (show_type == 'list') ? el_chk : el_bigimg_chk;
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
        var title = (op == '1') ? window.jslang.image_manager.copy_img_url
            : window.jslang.image_manager.copy_img_url_code;
        g_dlg = art.dialog({
            padding: 0,
            title: title,
            content: el[0],
            okVal: window.jslang.image_manager.ok,
            ok: function () {
                return true;
            },
            cancelVal: window.jslang.image_manager.cancel,
            cancel: function () {
            }
        });
    }


    $("#copy_img_url").click(function () {
        operate_submit(1);
    });

    $("#copy_img_url_code").click(function () {
        operate_submit(2);
    });

    $("#bat_set_img_cat").click(function () {
        operate_submit(3);
    });

    $('[name="img_cat_id"]').change(function () {
        $("#form1").submit();
    });

    $('[name="rows_per_page"]').change(function () {
        $("#form1").submit();
    });


    $("#list_data a.crop_image").click(function () {
        var w = parseInt($("#need_img_width").val());
        var h = parseInt($("#need_img_height").val());
        var opts = {
            remote_img_path: $(this).attr('img_path'),
            remote_img_url: $(this).attr('url'),
            remote_img_id: $(this).attr('img_id'),
            ok_cb: function (img_info) {
                $("#crop_img_id").val(img_info.img_id);
                $("#form1").submit();
            }
        };
        if (!isNaN(w) && w > 0) opts.crop_width = w;
        if (!isNaN(h) && h > 0) opts.crop_height = h;
        $.jCropRemoteImage(opts);
    });

    $("#bigimg_data a.crop_image").click(function (e) {
        var w = parseInt($("#need_img_width").val());
        var h = parseInt($("#need_img_height").val());
        var opts = {
            remote_img_path: $(this).attr('img_path'),
            remote_img_url: $(this).attr('url'),
            remote_img_id: $(this).attr('img_id'),
            ok_cb: function (img_info) {
                $("#crop_img_id").val(img_info.img_id);
                $("#form1").submit();
            }
        };
        if (!isNaN(w) && w > 0) opts.crop_width = w;
        if (!isNaN(h) && h > 0) opts.crop_height = h;
        $.jCropRemoteImage(opts);
    });

    $("#bigimg_data a.remove_image").click(function (e) {
        remove_item($(this).attr('img_id'));
    });

    $("#list_data a.remove_image").click(function (e) {
        remove_item($(this).attr('img_id'));
    });

    $("#bat_remove_img").click(function () {
        remove_item();
    });

    $("#bigimg_data li img").click(function () {
        var w = parseInt($("#need_img_width").val());
        var h = parseInt($("#need_img_height").val());
        var el = $(this).parents("li").find("[cbx_bigimg]");

        if (w > 0 && parseInt(el.attr("img_width")) != w || h > 0 && parseInt(el.attr("img_height")) != h) {
            $(this).parents("li").find("a.crop_image").click();
        } else {
            el.click();

            if (el.prop("checked")) {
                $(this).parents("li").find(".img_sel").show();
            } else {
                $(this).parents("li").find(".img_sel").hide();
            }
        }
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
