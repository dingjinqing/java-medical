jQuery(function ($) {

    var g_ue = UE.getEditor('editor');

    g_ue.ready(function () {
        g_ue.setContent($("#content").val());
        g_ue.setHeight(350);
    });

    var article_util = {
        count: function (o) {
            if (typeof(o) == 'object') {
                var ct = 0;
                for (var i in o) {
                    ct++;
                }
                return ct;
            }
            if (typeof(o) == 'array' || typeof(o) == 'string') {
                return o.length;
            }
            return 0;
        },


        submit: function () {
            $("#content").val(g_ue.getContent());
            var param = $("#form1").serialize();
            var art_id = $("[name='id']").val();
            var article_type = $('[name="article_type"]').val();
            util.ajax_json('/admin/help/article/add?article_id=' + art_id, function (d) {
                if (d && d.error == 0) {
                    if (art_id != "") {
                        layer.msg(window.jslang.article_util.save_success);
                    } else {
                        layer.msg(window.jslang.article_util.add_success_continue_add);
                        $("#form1 input[type='text']").val("");
                        $("#form1 textarea").val("");
                        g_ue.setContent("");
                    }
                } else if (d && d.error > 0) {
                    alert(d.message);
                }
            }, param);
        }
    };

    $('[name="article_type"]').change(function () {
        var article_type = parseInt($(this).val());
        if (article_type == 0) {
            $('[name="class_id"]').show();
        } else {
            $('[name="class_id"]').hide();
        }
    });

    $(".add-article-type").click(function () {
        var article_type_name = prompt(window.jslang.article_util.add_article_type);
        if (article_type_name) {
            var param = {
                op: 'add',
                article_type_name: article_type_name
            };
            util.ajax_json("/admin/help/article/article_type", function (d) {
                if (d && d.error == 0) {
                    var el = $('[name="article_type"]');
                    var opt = $("<option></option>");
                    opt.val(d.content.article_type);
                    opt.text(d.content.article_type_name);
                    opt.appendTo(el);
                    $('[name="article_type"]').val(d.content.article_type);
                    $('[name="article_type"]').change();
                }
            }, param)
        }
    });

    $(".del-article-type").click(function () {
        if ($('[name="article_type"]').val() == 0) {
            layer.msg(window.jslang.article_util.help_class_cant_del);
            return;
        }
        var article_type = $('[name="article_type"]').val();
        var param = {
            op: 'del',
            article_type: article_type
        };
        util.ajax_json("/admin/help/article/article_type", function (d) {
            if (d && d.error == 0) {
                var opt = $('[name="article_type"] option[value="' + article_type + '"');
                var el = opt.next();
                if (el.length == 0)
                    el = opt.prev();
                opt.remove();
                $('[name="article_type"]').val(el.val());
                $('[name="article_type"]').change();
            } else if (d && d.error > 0) {
                layer.msg(d.message);
            }
        }, param)
    });

    window.article_util = article_util;

});