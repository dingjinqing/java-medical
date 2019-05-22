<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html id="extr-page">
<head>
    <title> ${global_title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
          name="viewport"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-production.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-skins.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/system/base.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/system/common.css?v=0.1.1" rel="stylesheet" type="text/css"/>
     <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.0" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/css/admin/login.css"/>
     <script src="/js/layui/layui.js" type="text/javascript"></script>
    <script src="/js/layer/layer.js" type="text/javascript"></script>
</head>

<body style="background: #f1f7fc;">


<div class="container">
    <div class="head_logo">
        <img src="http://${image_domain}/image/admin/shop_logoswe.png" alt="微铺宝logo" width="200px"/>
    </div>
    <div class="main clearfix">
        <div class="main-left">
            <img src="http://${image_domain}/image/admin/login_back.png" alt="" />
        </div>
        <div class="main-right">
          
            <div class="well no-padding">
            <form name="loginForm" method="post" id="loginForm" class="smart-form client-form">

                <header> 登录
                </header>
                <fieldset>

                    <section>
                        <label class="label"> 用户名 / 手机号</label>
                        <label class="input"> <i class="icon-append fa fa-user"></i>
                            <input type="text" name="username" id="username"/>
                            <b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i>
                                <font id="username_msg"> 请输入用户名或者手机号</font>
                            </b>
                        </label>
                    </section>

                    <section>
                        <label class="label">密码</label>
                        <label class="input"> <i class="icon-append fa fa-lock"></i>
                            <input type="password" id="password" name="password"/>
                            <b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i>
                                <font id="passwd_msg"> 请输入密码</font>
                            </b>
                        </label>
                    </section>
                </fieldset>
                <footer>
                    <input type="button" id="btn-login" value="登录"
                           class="btn btn-primary btn-login"/>
                </footer>
            </form>

        </div>
        </div>
    </div>
</div>

<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
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
<script language="JavaScript" src="/js/system/util.js"></script>
<script language="JavaScript" src="/js/system/login.js"></script>

</body>
</html>
