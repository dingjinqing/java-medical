/*
 * need jquery art.dialog
 * usage: $.jImageManager({
 *		video_width:400,  // 所需要的图片宽度,可选
 *		video_height:400, // 所需要的图片高度,可选
 *		ok_cb:function(video_arr){}, // 选中的图片回调 video_arr是图片的数组
 *	});
 *
 **/

(function ($) {

    var default_opt = {
        browse_url: "/admin/manage/video/list",
        video_width: 0,
        video_height: 0,
        max_video_num: 1,
        show_video_sort: 1,
        ok_cb: function (video_arr) {
        }
    };

    var g_opts = default_opt;
    var g_dlg = null;
    var g_index = 0;

    $.jVideoManager = function (options) {
        g_opts = {
            browse_url: "/admin/manage/video/list",
            video_width: 0,
            video_height: 0,
            max_video_num: 1,
            show_video_sort: 1,
            ok_cb: function (video_arr) {
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
        var title = "浏览视频";
        if (g_opts.video_width > 0 || g_opts.video_height > 0) {
            // title += "  [" + window.jslang.video_common.need_video;
            // if (g_opts.video_width > 0)  title += window.jslang.video_common.video_width + g_opts.video_width;
            // if (g_opts.video_height > 0) title += window.jslang.video_common.video_height + g_opts.video_height;
            // title += "]";
        }


        var param = g_opts.video_width > 0 ? "&need_video_width=" + g_opts.video_width : '';
        param += g_opts.video_height > 0 ? "&need_video_height=" + g_opts.video_height : '';
        param += "&max_video_num=" + g_opts.max_video_num;
        param += "&show_video_sort=" + g_opts.show_video_sort;
        if (g_opts.max_video_num == 1 || g_opts.show_video_sort == 0) {
            if (g_opts.on_video_cb == undefined)
                g_opts.on_video_cb = "on_video_cb_" + Math.floor(Math.random() * 10240);
            window[g_opts.on_video_cb] = function (d) {
                d.video_path = d.path;
                console.log(d);
                var arr = [d];
                if(g_index>0) layer.close(g_index);
                var ret = g_opts.ok_cb(arr);
            }
            param += "&on_video_cb=" + g_opts.on_video_cb;
        }
        var dlg_height = (g_opts.max_video_num == 1 || g_opts.max_video_num != 1 && g_opts.show_video_sort == 0) ? 517 : 610;

        layer.open({
            type: 2
            , title: [title, 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: ['690px', dlg_height + "px"]
            , content: "/admin/frame/video/select?no_full=1" + param
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var iframe = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                window.cancel_selected_video = function (video_id) {
                    var cancel_select_video = iframe.cancel_select_video;
                    if (cancel_select_video) {
                        cancel_select_video(video_id);
                    }
                }
                g_index = index;
            }
            , yes: function (index, layero) {
                //保存按钮的回调
                var body = layer.getChildFrame('body', index);
                var iframe = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                if (g_opts.max_video_num == 1 || g_opts.max_video_num != 1 && g_opts.show_video_sort == 0) {
                    g_index = 0;
                    layer.close(index);
                    return true;
                }
                var tmp_video_arr = [], video_arr = [];
                if (iframe.get_selected_video) {
                    tmp_video_arr = iframe.get_selected_video();
                }
                for (var i in tmp_video_arr) {
                    var video = tmp_video_arr[i];
                    var skip = false;
                    if (g_opts.video_width > 0 && video.video_width != g_opts.video_width) skip = true;
                    if (g_opts.video_height > 0 && video.video_height != g_opts.video_height) skip = true;
                    if (!skip) {
                        video_arr.push(video);
                    }
                }
                if (video_arr.length == 0) {
                    if (g_opts.video_width > 0 || g_opts.video_height > 0) {
                        util.mobile_alert("没有选中符合宽高要求的图片，可以上传符合规格的图片或者对已上传的图片进行剪裁。");
                    } else {
                        util.mobile_alert("没有选中图片。");
                    }
                    return false;
                }
                g_opts.ok_cb(video_arr);
                layer.close(index);
                return true;

            }, btn2: function (index, layero) {
                //按钮取消的回调
                g_index = 0;
            }
        });

    }

})(jQuery);
