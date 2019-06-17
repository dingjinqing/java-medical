<link rel="stylesheet" type="text/css" href="/css/admin/login_reg.css"/>
<div class="container">
    <div class="header clearfix">
        <a href="/admin/official/indexl" class="logo_img">
            <img src="http://${image_domain!}/image/admin/official/logo3.png" height="35px"/>
        </a>
        <div class="head_title">找回密码</div>
        <a href="/admin/official/login" class="to_login">登录</a>
    </div>
    <div class="main">
        <form action="" method="post">
            <div class="main_form">
                <div class="control_group_container">
                    <div class="control_group control_group_active clearfix">
                        <label>手机号码</label>
                        <div class="group_1">
                            <input type="text" name="" id="" value="" placeholder="注册时使用的手机号"/>
                        </div>
                    </div>
                    <div class="err_msg">手机号格式不对</div>
                </div>
                <div class="control_group_container">
                    <div class="control_group clearfix">
                        <label>短信验证码</label>
                        <div class="group_1">
                            <input type="text" name="" id="" value="" placeholder="填写六位短信验证码"/>
                            <button class="btn_msg">短信验证码</button>
                        </div>
                    </div>
                    <div class="err_msg">手机号格式不对</div>
                </div>
                <div class="control_group_container">
                    <div class="control_group clearfix">
                        <label>设置新密码</label>
                        <div class="group_1">
                            <input type="text" name="" id="" value="" placeholder="8~20个字，包含字母和数字"/>
                        </div>
                    </div>
                    <div class="err_msg">手机号格式不对</div>
                </div>
                <div class="control_group_container">
                    <button class="btn_reg">确认修改</button>
                </div>
            </div>
        </form>
    </div>
    <div class="footer">
        <a href="/admin/official/index">@vpubao.com</a>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('input').focus(function(){
        $('.control_group').removeClass('control_group_active');
        $(this).parents('.control_group').addClass('control_group_active');
    });
    $('input').blur(function(){
        $(this).parents('.control_group').removeClass('control_group_active');
    });
</script>