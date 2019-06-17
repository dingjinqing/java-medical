<#include ("system.header")
<style type="text/css">
    .err_msg {
        color: red;
    }
    #confirm_passwd, #new_passwd, #old_passwd{
        height: 30px;
        margin-top: 5px;
        margin-left: 10px;
    }
    .submits{
        margin-top: 10px;
    }
</style>
<div class="box panel">
    <form method="post" id="form1">
        {{csrf_field()!}
        <div class="box panel panel-body">
            <table cellspacing='1' cellpadding='3'>
                <tr>
                    <td>{{ trans("system/password_change.form.old_passwd")!}</td>
                    <td><input type="password" name="old_passwd" placeholder="请输入原密码" id="old_passwd" value="" size="34"/>
                    <td><span id="old_pwd_msg" class="err_msg"></span></td>
                </tr>
                <tr>
                    <td>{{trans("system/password_change.form.new_passwd")!}</td>
                    <td><input type="password" name="new_passwd" id="new_passwd" placeholder="6~20个字符" value="" size="34"/></td>
                    <td><span id="new_pwd_msg" class="err_msg"></span></td>
                </tr>
                <tr>
                    <td>{{trans("system/password_change.form.confirm_passwd")!}</td>
                    <td><input type="password" name="confirm_passwd" id="confirm_passwd" value="" size="34"/></td>
                    <td><span id="confirm_pwd_msg" class="err_msg"></span></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="center">
                        <input type="button"  class="submits" value="{{trans("system/password_change.form.submit")!}"/>&nbsp;&nbsp;&nbsp;
                        <input type="reset" id="reset" value="{{trans("system/password_change.form.reset")!}" class="button"/>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>

<#include ("system.footer")
<script type="text/javascript">
    $(".submits").click(function () {

        /*密码不能为空*/
        if ($("#old_passwd").val().length <= 0) {
            $("#old_passwd").focus();
            return false;
        }

        if ($("[name='new_passwd']").val().length <= 0) {
            $("#new_passwd").focus();
            return false;
        }

        if ($("[name='confirm_passwd']").val().length <= 0) {
            $("#confirm_passwd").focus();
            return false;
        }

        /*新密码长度6-20个字符*/
        var nLength = $("#new_passwd").val().length;
        if(nLength < 6 || nLength > 20){
            art.dialog.tips('密码长度6-20位');
            return false;
        }

        var conLnegth = $("#confirm_passwd").val();
        var nValue = $("#new_passwd").val();
        if( conLnegth != nValue){
            art.dialog.tips('确认密码不一致');
            return false;
        }

        var url = "/system/password/update";
        var param = {};
        param.old_passwd = $('#old_passwd').val();
        param.new_passwd = $('#new_passwd').val();
        util.ajax_json(url, function (d) {
            if (d && d.error == 0) {
                window.location.href='/system/login'
                return true;
            } else if (d && d.error < 0) {
                if (d.error == -1) {
                    $("#old_passwd").focus();
                }else if(d.error == -2){
                    $("#new_passwd").focus();
                }
                art.dialog.tips(d.content);
                return false;
            }
        }, param);
    });
</script>