(function ($) {

    window.call_parent_img_cb = function (img_info) {
        var on_img_cb = $("#on_img_cb").val();
        if (on_img_cb && window.parent && window.parent.window[on_img_cb]) {
            on_img_cb = window.parent.window[on_img_cb];
            on_img_cb(img_info);
        }
    }

    window.cancel_selected_img = function (img_id) {
        var cancel_select_img = $(".img_iframe")[0].contentWindow.cancel_select_img;
        if (cancel_select_img) {
            cancel_select_img(img_id);
        }
    }

    window.check_img = function (img) {
        var need_img_width = isNaN(parseInt($("#need_img_width").val())) ? 0 : parseInt($("#need_img_width").val());
        var need_img_height = isNaN(parseInt($("#need_img_height").val())) ? 0 : parseInt($("#need_img_height").val());
        var img_max_num = isNaN(parseInt($("#img_max_num").val())) ? 0 : parseInt($("#img_max_num").val());
        var show_img_sort = isNaN(parseInt($("#show_img_sort").val())) ? 0 : parseInt($("#show_img_sort").val());
        show_img_sort = img_max_num == 1 ? 0 : show_img_sort;
        if (need_img_width > 0 && img.img_width != need_img_width) {
            art.dialog.tips("图片宽高不符合要求");
            cancel_selected_img(img.img_id);
            return false;
        }
        if (need_img_height > 0 && img.img_height != need_img_height) {
            art.dialog.tips("图片宽高不符合要求");
            cancel_selected_img(img.img_id);
            return false;
        }
        if (show_img_sort == 1 && img_max_num > 0 && $(".select-img-list  li").length > img_max_num) {
            art.dialog.tips("最多可以放置" + img_max_num + "张图片");
            cancel_selected_img(img.img_id);
            return false;
        }
        return true;
    }

    window.on_img_cb = function (img_info) {
        var find_el = $(".select-img-list ul").find("[img_id='" + img_info.img_id + "']");
        if (img_info.selected && find_el.length == 0) {
            if (!check_img(img_info)) return false;
            var el = $(".template .sel-img-template").clone();
            el.attr("img_id", img_info.img_id);
            el.attr("title", img_info.img_name);
            el.find("img").attr("src", img_info.url);
            el.find(".old_pic").attr("href", img_info.url);
            el.find(".img_dim p").text(img_info.img_width + "x" + img_info.img_height);
            el.appendTo($(".select-img-list ul"));
            el.find(".remove_image").click(function () {
                cancel_selected_img(img_info.img_id);
                el.remove();
            });
            el.attr("data", $.toJSON(img_info));
        } else {
            find_el.remove();
        }
        $(".img-num").text($(".select-img-list  li").length);

        call_parent_img_cb(img_info);
    }

    window.get_selected_img = function () {
        var img_arr = [];
        $(".select-img-list li").each(function () {
            var t = $.parseJSON($(this).attr("data"));
            img_arr.push(t);
        });
        return img_arr;
    }

    $(".img_iframe").load(function () {
        var win = $(".img_iframe")[0].contentWindow;
        var select_img = win.select_img;
        $(".select-img-list li").each(function () {
            select_img($(this).attr("img_id"));
        });
    });
    $(".select-img-data").sortable({
        contentWidth: ".select-img-data"
    });
    $(".select-img-data").disableSelection();

})(jQuery);
