(function ($) {

    window.call_parent_video_cb = function (video_info) {
        var on_video_cb = $("#on_video_cb").val();
        if (on_video_cb && window.parent && window.parent.window[on_video_cb]) {
            on_video_cb = window.parent.window[on_video_cb];
            on_video_cb(video_info);
        }
    }

    window.cancel_selected_video = function (video_id) {
        var cancel_select_video = $(".video_iframe")[0].contentWindow.cancel_select_video;
        if (cancel_select_video) {
            cancel_select_video(video_id);
        }
    }

    window.check_video = function (video) {
        var need_video_width = isNaN(parseInt($("#need_video_width").val())) ? 0 : parseInt($("#need_video_width").val());
        var need_video_height = isNaN(parseInt($("#need_video_height").val())) ? 0 : parseInt($("#need_video_height").val());
        var video_max_num = isNaN(parseInt($("#video_max_num").val())) ? 0 : parseInt($("#video_max_num").val());
        var show_video_sort = isNaN(parseInt($("#show_video_sort").val())) ? 0 : parseInt($("#show_video_sort").val());
        show_video_sort = video_max_num == 1 ? 0 : show_video_sort;
        if (need_video_width > 0 && video.video_width != need_video_width) {
            util.mobile_alert("视频宽高不符合要求");
            cancel_selected_video(video.video_id);
            return false;
        }
        if (need_video_height > 0 && video.video_height != need_video_height) {
            util.mobile_alert("视频宽高不符合要求");
            cancel_selected_video(video.video_id);
            return false;
        }
        if (show_video_sort == 1 && video_max_num > 0 && $(".select-video-list  li").length > video_max_num) {
            util.mobile_alert("最多可以放置" + video_max_num + "张视频");
            cancel_selected_video(video.video_id);
            return false;
        }
        return true;
    }

    window.on_video_cb = function (video_info) {
        var find_el = $(".select-video-list ul").find("[video_id='" + video_info.video_id + "']");
        if (video_info.selected && find_el.length == 0) {
            if (!check_video(video_info)) return false;
            var el = $(".template .sel-video-template").clone();
            el.attr("video_id", video_info.video_id);
            el.attr("title", video_info.video_name);
            if(video_info.url){
                el.find("video").attr("src", video_info.url);
                el.find(".old_pic").attr("href", video_info.url);
            }
            else{
                video_info.url =  video_info.video_url;
                el.find("video").attr("src", video_info.video_url);
                el.find(".old_pic").attr("href", video_info.video_url);
            }
            //el.find(".video_dim p").text(video_info.video_width + "x" + video_info.video_height);
            el.appendTo($(".select-video-list ul"));
            el.find(".remove_video").click(function () {
                cancel_selected_video(video_info.video_id);
                el.remove();
            });
            el.attr("data", $.toJSON(video_info));
        } else {
            find_el.remove();
        }
        $(".video-num").text($(".select-video-list  li").length);

        call_parent_video_cb(video_info);
    }

    window.get_selected_video = function () {
        var video_arr = [];
        $(".select-video-list li").each(function () {
            var t = $.parseJSON($(this).attr("data"));
            video_arr.push(t);
        });
        return video_arr;
    }

    $(".video_iframe").load(function () {
        var win = $(".video_iframe")[0].contentWindow;
        var select_video = win.select_video;
        $(".select-video-list li").each(function () {
            select_video($(this).attr("video_id"));
        });
    });
    $(".select-video-data").sortable({
        contentWidth: ".select-video-data"
    });
    $(".select-video-data").disableSelection();

})(jQuery);
