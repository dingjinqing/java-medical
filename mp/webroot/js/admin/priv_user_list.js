(function () {

    $('[name="add_account_role"]').click(function () {
        var mobile = $('[name="mobile"]').val();

        var re = /^1[34578]{1}[0-9]{9}$/;
        if ($('[name="account_id"] option').length == 0) {
            layer.msg("子账号不能为空");
            return false;
        }

        if ($('[name="role_id"] option').length == 0) {
            layer.msg(window.jslang.priv_user_list.role_cant_null);
            return false;
        }

        $('[name="act"]').val('add');
        layer.ready(function () {
            layer.msg('保存成功', {time: 2000},function () {
                $("#form1").submit();
            });
        });
    });

    window.edit_account_role = function (rec_id) {
        var tr = $("tr[rec_id=" + rec_id + "]");
        var account_id = tr.attr('account_id');
        var account_name = tr.attr('account_name');
        var role_id = tr.attr('role_id');
        $('#edit_account .account_name').text(account_name);
        $('[name="edit_rec_id"]').val(rec_id);
        $('[name="edit_account_id"]').val(account_id);
        $('[name="edit_role_id"]').val(role_id);

        art.dialog({
            title: window.jslang.priv_user_list.edit_account_info,
            lock: true,
            opacity: 0.1,
            content: $('#edit_account')[0],
            okVal: window.jslang.priv_user_list.save,
            ok: function () {
                var param = $("#edit_account :input").serializeArray();
                util.ajax_json("/admin/ajax/config/role/edit", function (d) {
                    if (d && d.error == 0) {
                        layer.ready(function () {
                            layer.msg(window.jslang.priv_user_list.update_ok, {time: 2000});
                        });
                        setTimeout(function () {
                            $('[name="act"]').val('');
                            $("#form1").submit();
                        }, 500);
                    } else {
                        layer.ready(function () {
                            layer.msg(window.jslang.priv_user_list.update_failed, {time: 2000});
                        });
                    }
                }, param);
                return true;
            },
            cancel: true
        })
    }
    // $(document).ready(function () {
    //     util.init_zero_clipboard($("#copy_to_clip"));
    //     setTimeout(function () {
    //         $(".msg-info").fadeOut(500);
    //     }, 2000);
    // });
}());