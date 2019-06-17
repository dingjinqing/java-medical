<#include "/admin/header.ftl">

<form id="form1" method="post" action="/admin/shop/passwdset">
    <input type="hidden" name="act" value="edit"/>
    {{ csrf_field()!}
    <div class="box panel panel-body">
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td>{{ trans("admin/passwdset.old_password")!}</td>
                <td><input type="password" name="old_passwd" placeholder="{{ trans("admin/passwdset.please_input_old_password")!}" id="old_passwd" value="" size="34"/>
                <td><span id="old_pwd_msg" class="err_msg"></span></td>
            </tr>
            <tr>
                <td>{{ trans("admin/passwdset.new_password")!}</td>
                <td><input type="password" name="new_passwd" id="new_passwd" placeholder="{{ trans("admin/passwdset.password_limit")!}" value="" size="34"/></td>
                <td><span id="new_pwd_msg" class="err_msg"></span></td>
            </tr>
            <tr>
                <td>{{ trans("admin/passwdset.confirm_password")!}</td>
                <td><input type="password" name="confirm_passwd" id="confirm_passwd" value="" size="34"/></td>
                <td><span id="confirm_pwd_msg" class="err_msg"></span></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td align="center">
                    <input type="button" name="modify_password" value="{{ trans("admin/passwdset.submit")!}"/>&nbsp;&nbsp;&nbsp;
                    <input type="reset" id="reset" value="{{ trans("admin/passwdset.reset")!}" class="button"/>
                </td>
            </tr>
        </table>
    </div>
</form>

<script>
    $('[name="modify_password"]').click(function () {
        var confirm_result = check_confirm_pwd();    //  验证码确认密码
        var new_result = check_new_pwd();    //  验证码新密码
        var old_result = check_old_pwd();    //  验证码原密码
        if (!old_result || !new_result || !confirm_result) {
            return false;
        }
        /* 验证密码是否正确 */
        var flag = null;
        var url = "/admin/shop/check/password";
        var param = {};
        param.old_passwd = $('#old_passwd').val();
        util.ajax_json(url, function (d) {
            if (d && d.error == 0) {
                $("#form1").submit();
            } else if (d && d.error < 0) {
                $('#old_pwd_msg').html(d.message);
            }
        }, param);
    });


    /* 焦点移动-原密码 */
    $("#old_passwd").blur(function () {
        check_old_pwd();
    });

    /* 焦点移动-新密码 */
    $("#new_passwd").blur(function () {
        check_new_pwd();
    });

    /* 焦点移动-确认密码 */
    $("#confirm_passwd").blur(function () {
        check_confirm_pwd();
    });

    /* 重置表单 */
    $("#reset").click(function () {
        $("#old_pwd_msg").html("");
        $("#new_pwd_msg").html("");
        $("#confirm_pwd_msg").html("");
    });

    /* 验证原密码 */
    function check_old_pwd() {

        if ($('#old_passwd').val() == '') {
            $('#old_pwd_msg').html('{{ trans("admin/passwdset.old_password_cant_null")!}');
            return false;
        }
        $("#old_pwd_msg").html("");
        return true;

    }

    /* 验证新密码 */
    function check_new_pwd() {

        if ($('#new_passwd').val() == '') {
            $('#new_pwd_msg').html('{{ trans("admin/passwdset.new_password_cant_null")!}');
            return false;
        } else if ($('#new_passwd').val().length < 6 || $('#new_passwd').val().length > 20) {
            $('#new_pwd_msg').html('{{ trans("admin/passwdset.new_password_length_limit")!}');
            return false;

        } else if ($('#new_passwd').val() == $('#old_passwd').val()) {
            $('#new_pwd_msg').html('{{ trans("admin/passwdset.new_password_no_change")!}');
            return false;
        }
        $("#new_pwd_msg").html("");
        return true;

    }

    /* 验证码确认密码 */
    function check_confirm_pwd() {
        if ($('#confirm_passwd').val() == '') {
            $("#confirm_pwd_msg").html('{{ trans("admin/passwdset.confirm_password_cant_null")!}');
            return false;
        } else if ($('#confirm_passwd').val() != $('#new_passwd').val()) {
            $('#confirm_pwd_msg').html('{{ trans("admin/passwdset.confirm_password_not_consistent")!}');
            return false;
        }
        $("#confirm_pwd_msg").html("");
        return true;

    }
</script>

<#include "/admin/footer.ftl">
