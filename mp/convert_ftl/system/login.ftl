<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html id="extr-page">
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
    <link href="/css/system/base.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/system/common.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/css/admin/login.css"/>
</head>

<body style="background: #f1f7fc;">

<!-- <header id="header">
    <div id="logo-group">
        <span id="logo"> <img src="http://${image_domain!}/image/system/logo.png" style="padding:0" /> </span>
    </div>
</header> -->

<!-- <div id="content" class="row" style="margin: 0px;padding-left: 0px;padding-right: 0px;">
    <div class="col-xs-12 col-sm-12 col-md-7 col-lg-8">
        <div id="pictureslide" class="center-block" style="height: 400px;max-width: 800px;">
            <img src="http://${image_domain!}/image/system/slide.jpg" alt="">
        </div>
    </div>
    <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4 ">
        <div class="well no-padding">
            <form name="loginForm" method="post" id="loginForm" class="smart-form client-form">

                {{ csrf_field()!}

                <header>{{ trans("system/login.form.title")!}

                </header>
                <fieldset>

                    <section>
                        <label class="label">{{ trans("system/login.form.username")!}</label>
                        <label class="input"> <i class="icon-append fa fa-user"></i>
                            <input type="text" name="username" id="username"/>
                            <b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i>
                                <font id="username_msg">{{ trans("system/login.form.username_hint")!}</font>
                            </b>
                        </label>
                    </section>

                    <section>
                        <label class="label">{{ trans("system/login.form.password")!}</label>
                        <label class="input"> <i class="icon-append fa fa-lock"></i>
                            <input type="password" id="password" name="password"/>
                            <b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i>
                                <font id="passwd_msg">{{ trans("system/login.form.password_hint")!}</font>
                            </b>
                        </label>
                    </section>
                </fieldset>
                <footer>
                    <input type="button" id="btn-login" value="{{ trans("system/login.form.submit")!}"
                           class="btn btn-primary btn-login"/>
                </footer>
            </form>

        </div>
    </div>
</div> -->

<div class="container">
    <div class="head_logo">
        <img src="http://${image_domain!}/image/admin/shop_logoswe.png" alt="微铺宝logo" width="200px"/>
    </div>
    <div class="main clearfix">
        <div class="main-left">
            <img src="http://${image_domain!}/image/admin/login_back.png" alt="" />
        </div>
        <div class="main-right">
           <!--  <div class="main-right-title">
                <div class="title-head title-active">{{ trans("admin/login.form.title")!}</div>
                <div class="title-head">{{ trans("admin/login.form.sub_login")!}</div>
            </div>
            <div class="main-right-content">
                <div class="content-zhu content-account" data-type="0">
                    <div class="mesg-error"></div>
                    <form name="loginForm" method="post" id="loginForm" class="smart-form client-form">
                        {{ csrf_field()!}
                        <div class="account-user"><input type="text" name="username" placeholder="{{ trans("admin/login.form.username")!}" /></div>
                        <div class="account-pawd"><input type="password" name="password" placeholder="{{ trans("admin/login.form.password")!}"></div>
                        <div class="account-login clearfix">
                            <input type="button" class="one-login to-login btn-login" value="{{ trans("admin/login.form.submit")!}">
                            {{--<span class="fr"> <a href="/admin/password">忘记密码</a> </span>--!}
                        </div>
                    </form>
                </div>
                <div class="content-zi content-account" data-type="1">
                    <div class="mesg-error"></div>
                    <form  name="loginForm" method="post" id="loginFormSub" class="smart-form client-form">
                        {{ csrf_field()!}
                        <input type="hidden" value="1" name="is_sub_login" />
                        <div class="account-name"><input type="text" name="username" placeholder="{{ trans("admin/login.form.username")!}" /></div>
                        <div class="account-user"><input type="text" name="sub_username" placeholder="{{ trans("admin/login.form.sub_username")!}" /></div>
                        <div class="account-pawd"><input type="password"  name="password"placeholder="{{ trans("admin/login.form.password")!}" /></div>
                        <div class="account-login clearfix">
                            <input type="button" class="child-login to-login btn-login" value="{{ trans("admin/login.form.submit")!}">
                            {{--<span class="fr"><a href="/admin/password">忘记密码</a></span>--!}
                        </div>
                    </form>
                </div>
            </div> -->
            <div class="well no-padding">
            <form name="loginForm" method="post" id="loginForm" class="smart-form client-form">

                {{ csrf_field()!}

                <header>{{ trans("system/login.form.title")!}

                </header>
                <fieldset>

                    <section>
                        <label class="label">{{ trans("system/login.form.username")!}</label>
                        <label class="input"> <i class="icon-append fa fa-user"></i>
                            <input type="text" name="username" id="username"/>
                            <b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i>
                                <font id="username_msg">{{ trans("system/login.form.username_hint")!}</font>
                            </b>
                        </label>
                    </section>

                    <section>
                        <label class="label">{{ trans("system/login.form.password")!}</label>
                        <label class="input"> <i class="icon-append fa fa-lock"></i>
                            <input type="password" id="password" name="password"/>
                            <b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i>
                                <font id="passwd_msg">{{ trans("system/login.form.password_hint")!}</font>
                            </b>
                        </label>
                    </section>
                </fieldset>
                <footer>
                    <input type="button" id="btn-login" value="{{ trans("system/login.form.submit")!}"
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
