
/**
 * Created by 新国 on 2015/3/16.
 */

(function () {

    $('[name="add_role"]').click(function () {
        $('[name="role_name"]').val("");
        $('[name="main_cbx[]"]').prop("checked", true);
        $('[name="sub_cbx[]"]').prop("checked", true);
        art.dialog({
            title: window.jslang.priv_role_list.add_role,
            lock: true,
            margin: 0,
            padding: 0,
            opacity: 0.1,
            content: $('#role_template')[0],
            okVal: window.jslang.priv_role_list.add_role,
            ok: function () {
                if ($('[name="role_name"]').val() == "") {
                    art.dialog.tips(window.jslang.priv_role_list.role_name_cant_null);
                    return false;
                }
                var param = $("#role_template :input").serializeArray();
                util.ajax_json("/system/account/role/add", function (d) {
                    if (d && d.error == 0) {
                        art.dialog.tips(window.jslang.priv_role_list.add_sucess);
                        setTimeout(function () {
                            $('[name="act"]').val('');
                            $("#form1").submit();
                        }, 500);
                    } else {
                        art.dialog.tips(window.jslang.priv_role_list.add_failed)
                    }
                }, param)
                return true;
            },
            cancel: true
        });
    });

    var sign_sub = 0;
    window.edit_role = function (role_id) {
        var tr = $("tr[role_id=" + role_id + "]");
        var role_name = tr.attr('role_name');
        var privilege_list = tr.attr('privilege_list');
        if (privilege_list == "") privilege_list = "{}";
        privilege_list = $.parseJSON(privilege_list);
        $('[name="role_name"]').val(role_name);
        $('[name="cur_role_id"]').val(role_id);
        $('[type="checkbox"]').prop("checked", false);
        sign_sub = 1;
        for (var j in privilege_list) {
            $('[name="sub_cbx[]"][value="' + privilege_list[j] + '"]').prop('checked', true);
        }
        sign_sub = 2;
        $('[name_a="third"]').change();
        sign_sub = 3;
        $('[name_a="two"]').change();

        sign_sub = 0;
        //$('[name_a="two"]').change();
        //$('[name_a="third"]').change();
        art.dialog({
            title: window.jslang.priv_role_list.edit_role,
            lock: true,
            margin: 0,
            padding: 0,
            opacity: 0.1,
            content: $('#role_template')[0],
            okVal: window.jslang.priv_role_list.save_modify,
            ok: function () {
                if ($('[name="role_name"]').val() == "") {
                    art.dialog.tips(window.jslang.priv_role_list.role_name_cant_null);
                    return false;
                }
                var param = $("#role_template :input").serializeArray();
                util.ajax_json("/system/account/role/edit", function (d) {
                    if (d && d.error == 0) {
                        art.dialog.tips(window.jslang.priv_role_list.update_ok);
                        setTimeout(function () {
                            $('[name="act"]').val('');
                            $("#form1").submit();
                        }, 500);
                    } else {
                        art.dialog.tips(window.jslang.priv_role_list.update_failed)
                    }
                }, param)
                return true;
            },
            cancel: true
        })
    }

    $('[name="main_cbx[]"]').change(function () {
        if (sign_sub === 1) return false;
        var priv_name = $(this).attr('priv_name');
        var checked = $(this).prop("checked");

        $('[main="' + priv_name + '"]').prop("checked", checked);
    });


    $('[name_a="two"]').change(function () {
        if (sign_sub === 1) return false;
        var priv_name = $(this).attr('priv_name');
        var main = $(this).attr('main');
        var checked = $(this).prop("checked");
        if (sign_sub !== 3) {
            $('[main_next="' + priv_name + '"]').prop("checked", checked);
        }

        var main_length = parseInt($('[main="' + main + '"]').length);
        var main_checked = parseInt($('[main="' + main + '"]:checked').length);

        if (main_checked > 0) {
            $('[priv_name="' + main + '"]').prop("checked", true);
        } else {
            $('[priv_name="' + main + '"]').prop("checked", false);
        }
    });

    $('[name_a="third"]').change(function () {
        if (sign_sub === 1) return false;
        var main_next = $(this).attr('main_next');

        var main_length = parseInt($('[main_next="' + main_next + '"]').length);
        var main_checked = parseInt($('[main_next="' + main_next + '"]:checked').length);

        if (main_checked > 0) {
            $('[priv_name="' + main_next + '"]').prop("checked", true);
        } else {
            $('[priv_name="' + main_next + '"]').prop("checked", false);
        }

        var main = $('[priv_name="' + main_next + '"]').attr('main');

        var main_length = parseInt($('[main="' + main + '"]').length);
        var main_checked = parseInt($('[main="' + main + '"]:checked').length);

        if (main_checked > 0) {
            $('[priv_name="' + main + '"]').prop("checked", true);
        } else {
            $('[priv_name="' + main + '"]').prop("checked", false);
        }
    });

}());


