(function () {
    $("input").keyup(function (event ) {
        if ( event.which == 13 ) {
            $("#btn-login").click();
        }
    });

    $("#btn-login").click(function () {
        if ($("#username").val().length <= 0) {
            $("#username").focus();
            return false;
        }

        if ($("[name='password']").val().length <= 0) {
            $("#password").focus();
            return false;
        }

        var url = "/system/login/attempt";
        var param = {};
        param.username = $("[name='username']").val();
        param.password = $("#password").val();
        util.ajax_json(url, function (d) {
            if (d && d.error == 0) {
                $("#loginForm").submit();
                return true;
            } else if (d && d.error < 0) {
                if (d.error == -1) {
                    $("#username").focus();
                } else if (d.error == -2) {
                    $("#password").focus();
                }
                art.dialog.tips(d.message);
                return false;
            }
        }, param);
    });

}());
