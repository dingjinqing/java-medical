
/**
 * Created by 新国 on 2015/3/16.
 */

(function () {

    $('[name="add_role"]').click(function () {
        $('[name="role_name"]').val("");
        $('[name="main_cbx[]"]').prop("checked", true);
        $('[name="sub_cbx[]"]').prop("checked", true);
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
        var pri_list = $(".role_ul").find("input");
        pri_list.each(function (i,v) {
            for (var k in privilege_list){
                if(privilege_list[k] == $(v).val()){
                    $('[name="sub_cbx[]"][value="' + privilege_list[j] + '"]').prop('checked', true);
                    continue;
                }else{
                    if($(v).attr("pre_name")){
                        var pre_name = JSON.parse($(v).attr("pre_name"));
                        for (var p in pre_name){
                            if(privilege_list[k] == pre_name[p]){
                                $('[name="sub_cbx[]"][value="' + privilege_list[j] + '"]').prop('checked', true);
                                continue;
                            }
                        }
                    }
                    continue;
                }
            }
        })
        sign_sub = 2;
        $('[name_a="third"]').change();
        sign_sub = 3;
        $('[name_a="two"]').change();

        sign_sub = 0;
        //$('[name_a="two"]').change();
        //$('[name_a="third"]').change();

        if(sub_account_id == 0) {
            //密码
            var privilege_pass = tr.attr("privilege_pass");
            if (privilege_pass == "") privilege_pass = "{}";
            privilege_pass = $.parseJSON(privilege_pass);
            if (role_pass) {
                $(".edit_role_pass").show();
                $(".set_role_pass").hide();
            }
            if (privilege_pass[0]) {
                privilege_pass[0] = privilege_pass[0].split(",")
                for (var pp in privilege_pass[0]) {
                    $('[name="privilege_pass[]"][value="' + privilege_pass[0][pp].trim('"') + '"]').prop('checked', true);
                }
            }
            if (privilege_pass[1]) {
                privilege_pass[1] = privilege_pass[1].split(",")
                for (var pr in privilege_pass[1]) {
                    $('[name="privilege_pass_status[]"][value="' + privilege_pass[1][pr].trim('"') + '"]').prop('checked', true);
                    $('[name="privilege_pass_status[]"][value="' + privilege_pass[1][pr].trim('"') + '"]').next().next().text('已开启');
                }
            }
        }


        art.dialog({
            title: window.jslang.priv_role_list.edit_role,
            lock: true,
            margin: 0,
            padding: 0,
            opacity: 0.5,
            top:100,
            content: $('#role_template')[0],
            okVal: window.jslang.priv_role_list.save_modify,
            init:function(){$('.function_role').eq(2).hide();},
            ok: function () {
                if ($('[name="role_name"]').val() == "") {
                    layer.ready(function () {
                        layer.msg(window.jslang.priv_role_list.role_name_cant_null, {time: 2000});
                    });
                    return false;
                }
                var pass_type = false;
                $(".functional_groups").find(".pass_box").each(function (q) {
                    if($(".functional_groups").find(".pass_box").eq(q).prop("checked") == true)
                        pass_type = true;
                })
                if(pass_type){
                    if($('input[name="cur_role_id"]').val() == '' || ($('input[name="cur_role_id"]').val() && $("input[name='edit_pass']").val() == 1)){
                        //添加
                        if(hex_md5($(".login_pass").val()) != login_pass){
                            util.mobile_alert("登录密码输入有误");
                            return false;
                        }
                        if($(".role_pass").val() == ""){
                            util.mobile_alert("功能密码设置不能为空");
                            return false;
                        }
                    }
                }
                var param = $("#role_template :input").serializeArray();
                util.ajax_json("/admin/config/role/edit", function (d) {
                    if (d && d.error == 0) {
                        layer.ready(function () {
                            layer.msg(window.jslang.priv_role_list.update_ok, {time: 2000});
                        });
                        setTimeout(function () {
                            $('[name="act"]').val('');
                            $("#form1").submit();
                        }, 500);
                    } else {
                        layer.ready(function () {
                            layer.msg(window.jslang.priv_role_list.update_failed, {time: 2000});
                        });
                    }
                }, param);
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

    $(".login_pass").blur(function () {
        if(hex_md5($(this).val()) != login_pass){
            util.mobile_alert("登录密码输入有误");
        }
    })
    $(".login_pass").change(function () {
        $(this).attr("old","") ;
    })
    $(".role_pass").change(function () {
        $(this).attr("old","");
    })
}());


