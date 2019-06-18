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

    var default_opt = {
        crop_width: 0,
        crop_height: 0,
        aspectRatio: 1.0,
        remote_img_path: '', // 必须的
        remote_img_url: '',
        remote_img_id: 0,
        img_cat_id: 0,
        crop_url: "/system/image/crop",
        ok_cb: function (img_info) {
        },
        lang: 'zh-cn'
    };

    var g_opts = default_opt;
    var g_dlg = null;

    var g_el_template = $('<div id="j_upload_div"><div style="margin:5px 0 5px 0;">' + window.jslang.image_common.crop_width + '<input type="text" id="crop_width" size=5>'
        + '	  ' + window.jslang.image_common.crop_height + '<input type="text" id="crop_height" size=5>'
        + '	  ' + window.jslang.image_common.crop_ratio + '<input type="text" id="aspectRatio" value="1.0" size=5>'
        + '	  <br /><span style="color:gray;">' + window.jslang.image_common.crop_tip + '</span>'
        + '</div>'
        + '<table id="j_upload_pic" style="border-spacing:0;width:350px;height:150px;;;">'
        + '	<tr><td style="width:150px;;">'
        + '		<div style="border:1px solid #F79D01; display: table-cell; vertical-align:middle; width:150px;;height:150px;;">'
        + '			<div style="margin:*;">'
        + '				<img src="" id="target" style="max-width:150px;;;max-height:150px;;;"/>'
        + '			</div>'
        + '		</div>'
        + '	</td>'
        + '	<td style="vertical-align:middle;">'
        + '		<div id="preview_div" style="margin:2px; boder:1px solid#F79D01; width:100px;height:100px;overflow:hidden;">'
        + '			<img src="" id="preview" />'
        + '		</div>'
        + '	</td>'
        + '	</tr>'
        + '</table></div>');
    var g_el = null;
    $.jCropRemoteImage = function (opts) {
        if (g_el) {
            g_el.remove();
        }
        g_el = g_el_template.clone();

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
            art.dialog.tips(window.jslang.image_common.err_crop_width_gt0);
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
            art.dialog.tips(window.jslang.image_common.err_crop_height_gt0);
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
            art.dialog.tips(window.jslang.image_common.crop_ratio_gt0);
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
        if (g_dlg) {
            g_dlg.close();
            g_dlg = null;
        }
        g_dlg = art.dialog({
            id: "art_dlg" + g_opts.remote_img_id,
            title: window.jslang.image_common.crop_image,
            content: g_el[0],
            padding: 0,
            okVal: window.jslang.image_common.save,
            init: function () {
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
            },
            ok: function () {
                var param = g_upload_params;
                g_upload_params.img_scale_w = $('#j_upload_pic #target').width();
                g_upload_params.img_scale_h = $('#j_upload_pic #target').height();
                util.ajax_json(g_opts.crop_url, function (d) {
                    g_dlg.close();
                    g_dlg = null;
                    g_opts.ok_cb(d.content[0]);
                }, param);
                return false;
            },
            cancelVal: window.jslang.image_common.cancel,
            cancel: function () {

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
