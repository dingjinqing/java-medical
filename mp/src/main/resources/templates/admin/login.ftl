<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html id="extr-page" style="background: rgb(241, 247, 252);">
<head>
    <title>${global_title!}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
          name="viewport"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-production.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-skins.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/admin/base.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/common.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/css/admin/login.css?v=1.0.1"/>
    <style type="text/css">
        .fr a{
            color: #5a8bff;
        }
        .fr a:focus, .fr a:hover{
            text-decoration: none;
            color: #5a8bff;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="head_logo">
        <img src="http://${image_domain!}/image/admin/shop_logoswe.png" alt="微铺宝logo" width="200px"/>
    </div>
    <div class="main clearfix">
        <div class="main-left">
            <img src="http://${image_domain!}/image/admin/login_new_new.png" alt="" />
        </div>
        <div class="main-right">
            <div class="main-right-title">
                <div class="title-head title-active">登录</div>
                <div class="title-head">子账号登录</div>
            </div>
            <div class="main-right-content">
                <div class="content-zhu content-account" data-type="0">
                    <div class="mesg-error"></div>
                    <form name="loginForm" method="post" id="loginForm" class="smart-form client-form">
                        <input type="hidden" value="0" name="is_sub_login" />
                        <div class="account-user"><input type="text" name="username" placeholder="主账号用户名" /></div>
                        <div class="account-pawd"><input type="password" name="password" placeholder="密码"></div>
                        <div class="account-login clearfix">
                            <input type="button" class="one-login to-login btn-login" value="登录">
                        </div>
                    </form>
                </div>
                <div class="content-zi content-account" data-type="1">
                    <div class="mesg-error"></div>
                    <form  name="loginForm" method="post" id="loginFormSub" class="smart-form client-form">
                        <input type="hidden" value="1" name="is_sub_login" />
                        <div class="account-name"><input type="text" name="username" placeholder="主账号用户名" /></div>
                        <div class="account-user"><input type="text" name="sub_username" placeholder="子账号用户名/手机号" /></div>
                        <div class="account-pawd"><input type="password"  name="password"placeholder="密码" /></div>
                        <div class="account-login clearfix">
                            <input type="button" class="child-login to-login btn-login" value="登录">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script language="JavaScript" src="/js/layui/layui.js"></script>
<script language="JavaScript" src="/js/smartadmin/libs/jquery-ui-1.10.3.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/bootstrap/bootstrap.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/notification/SmartNotification.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/smartwidgets/jarvis.widget.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/sparkline/jquery.sparkline.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/jquery-validate/jquery.validate.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/masked-input/jquery.maskedinput.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/select2/select2.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/msie-fix/jquery.mb.browser.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/fastclick/fastclick.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/app.config.js"></script>
<script language="JavaScript" src="/js/smartadmin/app.min.js"></script>
<script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=blue"></script>
<script language="JavaScript" src="/js/artDialog/plugins/iframeTools.js"></script>
<script language="JavaScript" src="/js/admin/util.js"></script>
<script language="JavaScript" src="/js/admin/login.js"></script>

</body>
</html>
