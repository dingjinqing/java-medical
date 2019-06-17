<link rel="stylesheet" type="text/css" href="/css/admin/login_reg.css"/>
<div class="container">
    <div class="header clearfix">
        <a href="/admin/official/index" class="logo_img">
            <img src="http://${image_domain!}/image/admin/official/logo3.png" height="35px"/>
        </a>
        <div class="head_title">登录</div>
    </div>
    <div class="main">
        <form action="" method="post">
            <div class="main_form">
                <div class="control_group_container">
                    <div class="control_group control_group_active clearfix">
                        <label>手机号码</label>
                        <div class="group_1">
                            <input type="text" name="" id="" value="" placeholder="请输入手机号"/>
                        </div>
                    </div>
                    <div class="err_msg">手机号格式不对</div>
                </div>
                <div class="control_group_container">
                    <div class="control_group clearfix">
                        <label>设置密码</label>
                        <div class="group_1">
                            <input type="text" name="" id="" value="" placeholder="请输入密码"/>
                        </div>
                    </div>
                    <div class="err_msg">手机号格式不对</div>
                </div>
                <div class="control_group_container">
                    <div class="control_group clearfix">
                        <label>验证码</label>
                        <div class="group_1">
                            <input type="text" name="" id="" value="" placeholder="请输入验证码"/>
                            <img src=""/>
                        </div>
                    </div>
                    <div class="err_msg">手机号格式不对</div>
                </div>
                <div class="control_group_container clearfix cgc_la">
                    <label>
                        <input type="checkbox" name="" id="" value="" class="three_login" />
                        三天内自动登录
                    </label>
                    <a href="/admin/official/recover">忘记密码?</a>
                </div>
                <div class="control_group_container">
                    <button class="btn_reg">登录</button>
                </div>
                <div class="control_group_container clearfix">
                    <a href="/admin/official/reg" class="to_login">免费注册，轻松开店</a>
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