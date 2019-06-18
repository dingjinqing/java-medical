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
        browse_url: "/admin/manage/image/list",
        img_width: 0,
        img_height: 0,
        max_img_num: 1,
        show_img_sort: 1,
        ok_cb: function (img_arr) {
        }
    };

    var g_opts = default_opt;
    var g_dlg = null;
    var g_index = 0;

    $.jImageManager = function (options) {
        g_opts = {
            browse_url: "/admin/manage/image/list",
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

    function close_layer() {
        if (g_index > 0) {
            layer.close(g_index);
            g_index = 0;
        }
    }

    function init() {
        var width = 800;
        var height = 500;
        var title = window.jslang.image_common.browse_image;
        if (g_opts.img_width > 0 || g_opts.img_height > 0) {
            // title += "  [" + window.jslang.image_common.need_image;
            // if (g_opts.img_width > 0)  title += window.jslang.image_common.image_width + g_opts.img_width;
            // if (g_opts.img_height > 0) title += window.jslang.image_common.image_height + g_opts.img_height;
            // title += "]";
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
                console.log(d);
                var arr = [d];
                close_layer();
                var ret = g_opts.ok_cb(arr);
            }
            param += "&on_img_cb=" + g_opts.on_img_cb;
        }
        var dlg_height = (g_opts.max_img_num == 1 || g_opts.max_img_num != 1 && g_opts.show_img_sort == 0) ? 517 : 610;
        close_layer();
        g_index = layer.open({
            type: 2
            , title: [title, 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: ['825px', dlg_height + "px"]
            , content: "/admin/frame/image/dialog/select?no_full=1" + param
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var iframe = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                window.cancel_selected_img = function (img_id) {
                    var cancel_select_img = iframe.cancel_select_img;
                    if (cancel_select_img) {
                        cancel_select_img(img_id);
                    }
                }
            }
            , yes: function (index, layero) {
                //保存按钮的回调
                var body = layer.getChildFrame('body', index);
                var iframe = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                if (g_opts.max_img_num == 1 || g_opts.max_img_num != 1 && g_opts.show_img_sort == 0) {
                    close_layer();
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
                        util.mobile_alert("没有选中符合宽高要求的图片，可以上传符合规格的图片或者对已上传的图片进行剪裁。");
                    } else {
                        util.mobile_alert("没有选中图片。");
                    }
                    return false;
                }
                g_opts.ok_cb(img_arr);
                close_layer();
                return true;

            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });

    }

})(jQuery);
