/*
 * need jquery art.dialog 
 * usage: $.jImageManager({
 *		img_width:400,  // 所需要的图片宽度,可选
 *		img_height:400, // 所需要的图片高度,可选
 *		ok_cb:function(img_arr){}, // 选中的图片回调 img_arr是图片的数组
 *	});
 *
 **/

(function ($) {

    var default_opt = {
        browse_url: "/system/image/list",
        img_width: 0,
        img_height: 0,
        max_img_num: 1,
        show_img_sort: 1,
        ok_cb: function (img_arr) {
        }
    };

    var g_opts = default_opt;
    var g_dlg = null;

    $.jImageManager = function (options) {
        g_opts = {
            browse_url: "/system/image/list",
            img_width: 0,
            img_height: 0,
            max_img_num: 1,
            show_img_sort: 1,
            ok_cb: function (img_arr) {
            }
        };

        for (var i in options) {
            g_opts[i] = options[i];
        }

        init();
    };

    function init() {
        var width = 800;
        var height = 500;
        var title = window.jslang.image_common.browse_image;
        if (g_opts.img_width > 0 || g_opts.img_height > 0) {
            title += "  [" + window.jslang.image_common.need_image;
            if (g_opts.img_width > 0)  title += window.jslang.image_common.image_width + g_opts.img_width;
            if (g_opts.img_height > 0) title += window.jslang.image_common.image_height + g_opts.img_height;
            title += "]";
        }


        var param = g_opts.img_width > 0 ? "&need_img_width=" + g_opts.img_width : '';
        param += g_opts.img_height > 0 ? "&need_img_height=" + g_opts.img_height : '';
        param += "&max_img_num=" + g_opts.max_img_num;
        param += "&show_img_sort=" + g_opts.show_img_sort;
        if (g_opts.max_img_num == 1 || g_opts.show_img_sort == 0) {
            if (g_opts.on_img_cb == undefined)
                g_opts.on_img_cb = "on_img_cb_" + Math.floor(Math.random() * 10240);
            window[g_opts.on_img_cb] = function (d) {
                d.img_path = d.path;
                var arr = [d];
                var ret = g_opts.ok_cb(arr);
                if (g_opts.max_img_num == 1 || ret == 'finish') {
                    if (g_dlg) {
                        g_dlg.hide();
                    }
                }
            }
            param += "&on_img_cb=" + g_opts.on_img_cb;
        }
        var dlg_height = (g_opts.max_img_num == 1 || g_opts.max_img_num != 1 && g_opts.show_img_sort == 0) ? 400 : 500;


        g_dlg = art.dialog.open("/system/image/dialog/select?no_full=1" + param, {
            title: title,
            width: 720,
            height: dlg_height,
            padding: 0,
            margin: 0,
            drag: true,
            resize: true,
            //lock: true,
            fixed: true,
            // 在open()方法中，init会等待iframe加载完毕后执行
            init: function () {
                var iframe = this.iframe.contentWindow;
                window.cancel_selected_img = function (img_id) {
                    var cancel_select_img = iframe.cancel_select_img;
                    if (cancel_select_img) {
                        cancel_select_img(img_id);
                    }
                }
            },
            okVal: window.jslang.image_common.ok,
            ok: function () {
                var iframe = this.iframe.contentWindow;
                if (!iframe.document.body) {
                    alert('iframe还没加载完毕呢')
                    return false;
                }
                if (g_opts.max_img_num == 1 || g_opts.max_img_num != 1 && g_opts.show_img_sort == 0) {
                    return true;
                }
                var tmp_img_arr = [], img_arr = [];
                if (iframe.get_selected_img) {
                    tmp_img_arr = iframe.get_selected_img();
                }
                for (var i in tmp_img_arr) {
                    var img = tmp_img_arr[i];
                    var skip = false;
                    if (g_opts.img_width > 0 && img.img_width != g_opts.img_width) skip = true;
                    if (g_opts.img_height > 0 && img.img_height != g_opts.img_height) skip = true;
                    if (!skip) {
                        img_arr.push(img);
                    }
                }
                if (img_arr.length == 0) {
                    if (g_opts.img_width > 0 || g_opts.img_height > 0) {
                        art.dialog.alert("没有选中符合宽高要求的图片，可以上传符合规格的图片或者对已上传的图片进行剪裁。");
                    } else {
                        art.dialog.alert("没有选中图片。");
                    }
                    return false;
                }
                g_opts.ok_cb(img_arr);
                return true;

                var shop_type = $("[name='show_type']", iframe.document).val();
                var cbx_el = (shop_type == 'list') ? $("input[cbx_list]:checked", iframe.document) : $("input[cbx_bigimg]:checked", iframe.document);
                var img_arr = [];
                cbx_el.each(function () {
                    var skip = false;
                    if (g_opts.img_width > 0 && parseInt($(this).attr('img_width')) != g_opts.img_width) skip = true;
                    if (g_opts.img_height > 0 && parseInt($(this).attr('img_height')) != g_opts.img_height) skip = true;
                    if (!skip) {
                        var o = {
                            img_id: $(this).val(), url: $(this).attr('url'), img_path: $(this).attr('img_path'), 'img_name': $(this).attr('img_name'),
                            'img_width': $(this).attr('img_width'), 'img_height': $(this).attr('img_height'), 'img_size': $(this).attr('img_size')
                        };
                        img_arr.push(o);
                    }
                });
                if (img_arr.length == 0) {
                    if (g_opts.img_width > 0 || g_opts.img_height > 0) {
                        art.dialog.alert("没有选中符合宽高要求的图片，可以上传符合规格的图片或者对已上传的图片进行剪裁。");
                    } else {
                        art.dialog.alert("没有选中图片。");
                    }
                    return false;
                }
                g_opts.ok_cb(img_arr);
                return true;
            },
            cancelVal: window.jslang.image_common.cancel,
            cancel: function () {
            }
        });
    }

})(jQuery);
