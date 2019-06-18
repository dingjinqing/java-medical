(function () {

    $('[name="add_account"]').click(function () {
        var mobile = $('[name="mobile"]').val();

        var re = /^1[34578]{1}[0-9]{9}$/;
        if ($('[name="account_name"]').val().length == 0) {
            art.dialog.tips( window.jslang.priv_user_list.account_name_cant_null);
            return false;
        }
        if (mobile.length == 0) {
            art.dialog.tips(window.jslang.priv_user_list.account_mobile_cant_null);
            return false;
        }

        if (!re.test(mobile)) {
            art.dialog.tips(window.jslang.priv_user_list.account_mobile_format_not_valid);
            return false;
        }
        if ($('[name="account_pwd"]').val().length == 0) {
            art.dialog.tips(window.jslang.priv_user_list.password_cant_null);
            return false;
        }
        if ($('[name="account_pwd"]').val() != $('[name="confirm_account_pwd"]').val()) {
            art.dialog.tips(window.jslang.priv_user_list.password_not_consistent);
            return false;
        }

        if ($('[name="role_id"] option').length == 0) {
            art.dialog.tips(window.jslang.priv_user_list.role_cant_null);
            return false;
        }

        $('[name="act"]').val('add');
        $("#form1").submit();
    });

    var mobile;
    var haschild = 3;
    window.edit_account = function (account_id) {
        var tr = $("tr[account_id=" + account_id + "]");
        var account_name = tr.attr('account_name');
        mobile = tr.attr('mobile');
        //alert(mobile)
        var role_id = tr.attr('role_id');
        $('#edit_account .account_name').text(account_name);
        $('[name="edit_account_name"]').val(account_name);
        $('[name="edit_account_mobile"]').val(mobile);
        $('[name="edit_account_id"]').val(account_id);
        $('[name="edit_role_id"]').val(role_id);


        art.dialog({
            title: window.jslang.priv_user_list.edit_account_info,
            lock: true,
            opacity: 0.1,
            content: $('#edit_account')[0],
            okVal: window.jslang.priv_user_list.save,
            ok: function () {
                newmobile = $('[name="edit_account_mobile"]').val();
                var re = /^1[34578]{1}[0-9]{9}$/;
                if (!re.test(newmobile)) {
                    art.dialog.tips(window.jslang.priv_user_list.account_mobile_format_not_valid);
                    return false;
                }
                if (mobile.toString() != newmobile.toString()) {
                    //alert(1)
                    $.ajax({
                        type: "post",
                        url: "/system/account/user/query",
                        dataType: 'json',
                        data: {mobile: newmobile,id:account_id},
                        success: function (data) {
                            if (data.error == 0) {
                                haschild = 1;

                            } else {

                                haschild = 0
                            }
                        },
                        'async': false
                    });
                    if (haschild == 0) {
                        art.dialog.tips(window.jslang.priv_user_list.mobile_exists);
                        return false;
                    }
                }
                if (mobile.toString() == newmobile.toString() || haschild == 1) {
                    var param = $("#edit_account :input").serializeArray();
                    //alert(2)
                    util.ajax_json("/system/account/user/edit", function (d) {
                        if (d && d.error == 0) {
                            art.dialog.tips(window.jslang.priv_user_list.update_ok);
                            setTimeout(function () {
                                $('[name="act"]').val('');
                                $("#form1").submit();
                            }, 500);
                        } else {
                            art.dialog.tips(window.jslang.priv_user_list.update_failed)
                        }
                    }, param)
                    return true;
                }
            },
            cancel: true
        })
    }
    $(document).ready(function () {
        util.init_zero_clipboard($("#copy_to_clip"));
        setTimeout(function () {
            $(".msg-info").fadeOut(500);
        }, 2000);
    });
}());