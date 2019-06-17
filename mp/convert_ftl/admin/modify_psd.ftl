<html id="extr-page" style="background: rgb(241, 247, 252);">
<head>
    <title>${global_title!}</title>
    <meta name="csrf-token" content="{{ csrf_token()!}">
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
    <link rel="stylesheet" type="text/css" href="/css/admin/login.css"/>
    <link rel="stylesheet" href="/css/admin/modify_psd.css" type="text/css" />
</head>
<body>
<div class="container">
    <div class="head_logo">
        <img src="http://${image_domain!}/image/admin/logo3.png" alt="微铺宝logo" width="150px"/>
    </div>
    <div class="main clearfix">
        <div class="main-left">
            <img src="http://${image_domain!}/image/admin/login_back.png" alt="" />
        </div>
        <div class="main-right">
            <div class="main-right-title">
                <div class="title-head">忘记密码</div>
            </div>
            <div class="main-right-content">
                <div class=" content-account" data-type="0">
                    <div class="mesg-error"></div>
                    <form name="" method="post" id="loginForm" class="smart-form client-form">
                        {{ csrf_field()!}
                        <div class="">
                            <span>手机号码</span>
                            <input type="text" name="" placeholder="注册时修改的手机号" />
                        </div>
                        <div class="clearfix">
                            <span>验证码</span>
                            <input type="text" name="" placeholder="请填写短信验证码" />
                            <button>获取验证码</button>
                        </div>
                        <div class="">
                            <span>设置密码</span>
                            <input type="text" name="" placeholder="请重新设置密码" />
                        </div>
                        <div class="account-login clearfix">
                            <input type="button" class="one-login to-login btn-login" value="确认修改" />
                        </div>
                    </form>
                </div>
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
<script language="JavaScript" src="/js/admin/util.js"></script>

</body>
</html>
