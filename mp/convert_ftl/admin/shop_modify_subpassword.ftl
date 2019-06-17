<#include "/admin/toggle_header.ftl">
    <link rel="stylesheet" href="/css/admin/shop_modify_passwd.css?v=1.0.13" type="text/css" />
    <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />
<div class="shop_container">
    <div class="account-title">
        <span style="color: #333;">账户设置</span>
    </div>
    <div class="order-container">
        <div class="shop-config">
            <form action="" id="form2" method="post" style="display: block;">
                {{ csrf_field()!}
                <ul>
                    <li class="clearfix">
                        <div class="fl">旧密码：</div>
                        <div class="fl">
                            <input type="password" name='pass_old' value="" class="mdy-input pass_old"/>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">新密码：</div>
                        <div class="fl">
                            <input type="password" name='pass' value="" class="mdy-input pass"/>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">确认新密码：</div>
                        <div class="fl">
                            <input type="password" name='pass_con' value="" class="mdy-input pass_con"/>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">&nbsp;</div>
                        <div class="fl">
                            <button class="btn-save-pass"  onClick="submitAction();return false;">确认修改</button>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>
<div id="set-number">
    <ul>
        <li>
            <span>原手机号：</span>
            <input type="text" value="${data_list->mobile!}" name="old_number" disabled />
        </li>
        <li>
            <span>验证码：</span>
            <input type="text" />
            <button>获取短信验证码</button>
        </li>
        <li>
            <span>新手机号：</span>
            <input type="text" name="new_number"/>
        </li>
    </ul>
</div>

</form>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script>

    function  submitAction(){
        var pass_old = $(".pass_old").val();
        var pass = $(".pass").val();
        var pass_con = $(".pass_con").val();
        if(pass_old == ""){
            $(".pass_old").focus();
            layer.msg('原密码不能为空');
            return false;
        }else if (pass_old.length<6 || pass_old.length>16){
            $(".pass_old").focus();
            layer.msg('原密码长度为6到16位之间');
            return false;
        }
        if(pass == ""){
            $(".pass").focus();
            layer.msg('新密码不能为空');
            return false;
        }else if (pass.length<6 || pass.length>16){
            $(".pass").focus();
            layer.msg('新密码长度为6到16位之间');
            return false;
        }
        if(pass_con == ""){
            $(".pass_con").focus();
            layer.msg('确认密码不能为空');
            return false;
        }else if (pass_con.length<6 || pass_con.length>16){
            $(".pass_con").focus();
            layer.msg('确认密码长度为6到16位之间');
            return false;
        }
        if(pass_con.length != pass.length || pass_con != pass){
            $(".pass_con").focus();
            layer.msg('新密码与确认密码不一致');
            return false;
        }
        var data={};
        data.pass_old = pass_old;
        data.pass = pass;
        data.pass_con = pass_con;
        data.is_sub_account = '${user['is_sub_account']!}';
        util.ajax_json('/admin/subPasswordModify',function(d){
            if(d&&d.error==0){
                util.mobile_alert(d.message);
                setTimeout(function(){
                    window.location.reload();
                },1200);
                return false;
            }
            else{
                util.mobile_alert(d.message);
                return false;
            }
        },data);
    }
    $('#form2').show();
    $('.account-title').find('span').html('密码修改');

</script>