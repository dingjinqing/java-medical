jQuery(function ($) {
    initKindEditor("#editor", function () {
        window.keditor.html($("#content").val());
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
            window.keditor.sync();
            $("#content").val($('#editor').val());
            var param = $("#form1").serialize();
            util.ajax_json('/system/article/info', function (d) {
                if (d && d.error == 0) {
                    if ($('#article_id').val() != "") {
                        art.dialog.tips("保存成功！");
                    } else {
                        art.dialog.tips("添加成功，继续添加！");
                        $("#form1 input[type='text']").val("");
                        $("#form1 textarea").val("");
                        window.keditor.html("");
                    }
                } else if (d && d.error > 0) {
                    alert(d.message);
                }
            }, param);
        }
    };

    window.article_util = article_util;

});