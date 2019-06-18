/*
 * need jquery art.dialog Jcrop ajaxfileupload
 * usage: $.jCropRemoteImage({
 *		crop_width:400,  // 剪裁后的默认图片宽度，可选
 *		crop_height:400, // 剪裁后的默认图片高度，可选
 *		aspectRatio:1.0, // 剪裁默认比例，可选
 *		remote_img_url:"", // 远程图片的URL
 *		remote_img_path:"", // 远程图片的存储相对路径
 *		crop_url:"?c=image_manager&m=crop_img", // 上传图片的剪裁的URL
 *		ok_cb:function(img_info){},   //img_info= {path:,url:,img_id:}
 *	});
 *
 **/

(function ($) {

    var jcrop_api, boundx, boundy, preview_width = 100, preview_height = 100;
    var g_upload_params = {
        x: 0,
        y: 0,
        w: 0,
        h: 0,
        img_scale_w: 0,
        img_scale_h: 0,
        type: '',
        remote_img_path: '',
        remote_img_url: '',
        remote_img_id: '',
        crop_width: 0,
        crop_height: 0,
        img_cat_id: 0,
        url: ""
    };
    var url = location.href;
    var action = '/admin/ajax/image/crop';
    if (url.indexOf("admin/public/image/account/dialog") != -1) {
        action = '/admin/public/image/account/crop';
    }
    var default_opt = {
        crop_width: 0,
        crop_height: 0,
        aspectRatio: 1.0,
        remote_img_path: '', // 必须的
        remote_img_url: '',
        remote_img_id: 0,
        img_cat_id: 0,
        crop_url: action,
        ok_cb: function (img_info) {
        },
        lang: 'zh-cn'
    };

    var g_opts = default_opt;

    var g_html ='<style>\n' +
        '    .remote-crop-container {\n' +
        '        padding: 10px 15px;\n' +
        '    }\n' +
        '\n' +
        '    .crop-input-form {\n' +
        '        margin: 5px 0 5px 0;\n' +
        '    }\n' +
        '\n' +
        '    .crop-input-form:after {\n' +
        '        content: "";\n' +
        '       display: block;\n'+
        '        clear: both;\n'+
        '    }\n' +
        '\n' +
        '    .crop-input-line {\n' +
        '        height: 24px;\n' +
        '        line-height: 24px;\n' +
        '        float: left;\n'+
        '    }\n' +
        '\n' +
        '    .crop-text-label {\n' +
        '        width: 100px;\n' +
        '    }\n' +
        '\n' +
        '    .crop-input {\n' +
        '        height: 20px;\n' +
        '    }\n' +
        '\n' +
        '    .crop-table {\n' +
        '        border-spacing: 0;\n' +
        '        width: 350px;\n' +
        '        height: 150px;\n' +
        '    }\n' +
        '\n' +
        '    .crop-table td {\n' +
        '        padding: 0;\n' +
        '        vertical-align: middle;\n' +
        '        width: 175px;\n' +
        '    }\n' +
        '\n' +
        '    .crop-image-container {\n' +
        '        border: 1px solid #999;\n' +
        '        display: table-cell;\n' +
        '        vertical-align: middle;\n' +
        '        width: 150px;;\n' +
        '        height: 150px;\n' +
        '    }\n' +
        '\n' +
        '    .crop-target {\n' +
        '        max-width: 150px;\n' +
        '        max-height: 150px;\n' +
        '    }\n' +
        '\n' +
        '    .crop-preview {\n' +
        '        margin: 2px;\n' +
        '        boder: 1px solid #999;\n' +
        '        width: 100px;\n' +
        '        height: 100px;\n' +
        '        overflow: hidden;\n' +
        '    }\n' +
        '\n' +
        '    .jcrop-holder {\n' +
        '        display: block;\n' +
        '        float: none;\n' +
        '        margin-bottom: 0;\n' +
        '    }\n' +
        '\n' +
        '</style>\n' +
        '\n' +
        '<div id="j_upload_div" class="remote-crop-container">\n' +
        '    <div class="crop-input-form">\n' +
        '        <div class="crop-input-line">\n' +
        '            <span class="crop-text-label">裁剪图片宽度：</span>\n' +
        '            <input type="text" id="crop_width" size=3 class="crop-input">\n' +
        '        </div>\n' +
        '        <div class="crop-input-line">\n' +
        '            <span class="crop-text-label">裁剪图片高度：</span>\n' +
        '            <input type="text" id="crop_height" size=3 class="crop-input">\n' +
        '        </div>\n' +
        '        <div class="crop-input-line">\n' +
        '            <span class="crop-text-label">裁剪图片比例：</span>\n' +
        '            <input type="text" id="aspectRatio" size=3 class="crop-input">\n' +
        '        </div>\n' +
        '        <div class="crop-input-line">\n' +
        '            <span class="text-warning crop-text-hint">剪裁后图片宽高为空，剪裁为实际宽高存储</span>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <table id="j_upload_pic" class="crop-table">\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <div class="crop-image-container">\n' +
        '                    <img src="" id="target" class="crop-target"/>\n' +
        '                </div>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <div id="preview_div" class="crop-preview"><div>\n' +
        '                    <img src="" id="preview" />\n' +
        '                </div>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '    </table>\n' +
        '</div>';


    var g_el = null;
    $.jCropRemoteImage = function (opts) {
        for (var i in opts) {
            g_opts[i] = opts[i];
        }
        g_upload_params.img_cat_id = g_opts.img_cat_id;
        g_upload_params.crop_width = g_opts.crop_width;
        g_upload_params.crop_height = g_opts.crop_height;
        init();
    };

    $(document).on("change", "#j_upload_div #crop_width", function () {
        if ($(this).val() == '') {
            g_upload_params.crop_width = 0;
            return;
        }
        var r = parseInt($(this).val());
        if (isNaN(r) || !isNaN(r) && r <= 0) {
            layer.msg(window.jslang.image_common.err_crop_width_gt0);
            $(this).focus();
            return;
        }
        g_opts.crop_width = r;
        if (g_opts.crop_height > 0) {
            g_opts.aspectRatio = g_opts.crop_width / g_opts.crop_height;
            $("#j_upload_div #aspectRatio").val(g_opts.aspectRatio);
        } else {
            g_opts.crop_height = parseInt(g_opts.crop_width / g_opts.aspectRatio);
            if (g_opts.crop_height == 0) g_opts.crop_height = 1;
            $("#j_upload_div #crop_height").val(g_opts.crop_height);
        }
        preview_height = preview_width / g_opts.aspectRatio;
        g_el.find("#Preview").height(preview_height);
        show_image_to_crop(g_opts);
    });

    $(document).on("change", "#j_upload_div #crop_height", function () {
        if ($(this).val() == '') {
            g_upload_params.crop_height = 0;
            return;
        }
        var r = parseInt($(this).val());
        if (isNaN(r) || !isNaN(r) && r <= 0) {
            layer.msg(window.jslang.image_common.err_crop_height_gt0);
            $(this).focus();
            return;
        }
        g_opts.crop_height = r;
        if (g_opts.crop_width > 0) {
            g_opts.aspectRatio = g_opts.crop_width / g_opts.crop_height;
            $("#j_upload_div #aspectRatio").val(g_opts.aspectRatio);
        }
        preview_height = preview_width / g_opts.aspectRatio;
        g_el.find("#Preview").height(preview_height);
        show_image_to_crop(g_opts);
    });

    $(document).on("change", "#j_upload_div #aspectRatio", function () {
        var r = parseFloat($(this).val());
        if (isNaN(r) || !isNaN(r) && r <= 0) {
            layer.msg(window.jslang.image_common.crop_ratio_gt0);
            $(this).focus();
            return;
        }
        g_opts.aspectRatio = r;
        if (g_opts.crop_width > 0) {
            g_opts.crop_height = parseInt(g_opts.crop_width / g_opts.aspectRatio);
            if (g_opts.crop_height == 0) g_opts.crop_height = 1;
            $("#j_upload_div #crop_height").val(g_opts.crop_height);
        }
        preview_height = preview_width / g_opts.aspectRatio;
        g_el.find("#Preview").height(preview_height);
        show_image_to_crop(g_opts);
    });


    function init() {

        layer.open({
            type: 1
            , title: [window.jslang.image_common.crop_image, 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: '520px'
            , content: g_html
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                g_el = layero.find("#j_upload_div");
                //保存按钮的回调
                if (g_opts.crop_width > 0) {
                    $("#j_upload_div #crop_width").val(g_opts.crop_width);
                    $("#j_upload_div #crop_width").prop("disabled", true);
                }
                if (g_opts.crop_height > 0) {
                    $("#j_upload_div #crop_height").val(g_opts.crop_height);
                    $("#j_upload_div #crop_height").prop("disabled", true);
                }
                if (g_opts.crop_width > 0 && g_opts.crop_height > 0) {
                    g_opts.aspectRatio = (g_opts.crop_width / g_opts.crop_height);
                    $("#j_upload_div #aspectRatio").prop("disabled", true);
                }
                if (g_opts.aspectRatio > 0) {
                    $("#j_upload_div #aspectRatio").val(g_opts.aspectRatio);
                }
                preview_height = preview_width / g_opts.aspectRatio;
                g_el.find("#Preview").height(preview_height);
                show_image_to_crop(g_opts);
            }
            , yes: function (index, layero) {
                var param = g_upload_params;
                g_upload_params.img_scale_w = $('#j_upload_pic #target').width();
                g_upload_params.img_scale_h = $('#j_upload_pic #target').height();
                util.ajax_json(g_opts.crop_url, function (d) {
                    g_opts.ok_cb(d.content[0]);
                }, param);

                layer.close(index);
                return true;

            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    }

    function updatePreview(c) {
        if (parseInt(c.w) > 0) {
            var rx = preview_width / c.w;
            var ry = preview_height / c.h;

            $('#j_upload_pic #preview').css({
                width: Math.round(rx * boundx) + 'px',
                height: Math.round(ry * boundy) + 'px',
                marginLeft: '-' + Math.round(rx * c.x) + 'px',
                marginTop: '-' + Math.round(ry * c.y) + 'px'
            });
            g_upload_params.x = c.x;
            g_upload_params.y = c.y;
            g_upload_params.w = c.w;
            g_upload_params.h = c.h;
        } else {
            g_upload_params.x = 0;
            g_upload_params.y = 0;
            g_upload_params.w = 0;
            g_upload_params.h = 0;
        }
    }


    function show_image_to_crop(d) {
        g_upload_params.remote_img_path = d.remote_img_path;
        g_upload_params.remote_img_url = d.remote_img_url;
        g_upload_params.remote_img_id = d.remote_img_id;
        g_upload_params.crop_width = d.crop_width;
        g_upload_params.crop_height = d.crop_height;
        if (jcrop_api) {
            jcrop_api.destroy();
            jcrop_api = undefined;
        }

        g_el.find("#target").attr('src', d.remote_img_url);
        g_el.find("#preview").attr('src', d.remote_img_url);
        g_el.find("#preview_div").width(preview_width);
        g_el.find("#preview_div").height(preview_height);
        g_el.find("#target").Jcrop({
            onChange: updatePreview,
            onSelect: updatePreview,
            aspectRatio: g_opts.aspectRatio
        }, function () {
            var bounds = this.getBounds();
            boundx = bounds[0];
            boundy = bounds[1];
            jcrop_api = this;
            var rect = [];
            if (boundy * g_opts.aspectRatio > boundx) {
                rect = [0, (boundy - boundx / g_opts.aspectRatio) / 2, boundx, (boundy - boundx / g_opts.aspectRatio) / 2 + boundx / g_opts.aspectRatio];
            } else {
                rect = [(boundx - boundy * g_opts.aspectRatio) / 2, 0, (boundx - boundy / g_opts.aspectRatio) / 2 + boundy / g_opts.aspectRatio, boundy];
            }
            g_upload_params.x = rect[0];
            g_upload_params.y = rect[1];
            g_upload_params.w = rect[2];
            g_upload_params.h = rect[3];
            jcrop_api.setSelect(rect);
        });
    }


})(jQuery);
