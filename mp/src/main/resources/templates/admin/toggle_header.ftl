<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>${global_title!}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
          name="viewport"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/admin/base.css?v=1.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/menu.css?v=1.0.5" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/css/admin/create_shop.css" type="text/css">
    <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css">
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <script language="JavaScript" src="/js/smartadmin/libs/jquery-ui-1.10.3.min.js"></script>
    <script language="JavaScript" src="/js/admin/index.js"></script>
    <script language="JavaScript" src="/js/jquery.json.js"></script>
    <script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
    <script language="JavaScript" src="/js/artDialog/plugins/iframeTools.source.js"></script>
    <script language="JavaScript" src="/js/layui/layui.js?v=1.0.1"></script>
    <script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/util.js"></script>
    <script language="JavaScript" src="/js/admin/util.js?v=1.0.1"></script>
    <script language="JavaScript" src="/js/admin/table.js?v=1.0.1"></script>
</head>
<body>
<div class="header clearfix">
    <div class="fl">
		<img src="http://${image_domain}/image/admin/official/bottom_logo.png" alt="微铺宝" style="height:49px;margin-top: 18px;" />
	</div>
    <div class="fr">
        <div class="account">
            <!-- logout button -->
            <div class="btn-header transparent pull-right">
                <#if  login_user['is_sub_account']  == 0>
                <span> 
                
	                <img 
	                <#if ("${login_user['user']['shop_avatar']!}" != "")> 
	                	src="${login_user['user']['shop_avatar']!}" 
	                <#else> 
	                	src="http://${image_domain!}/image/admin/head_icon.png" 
	                </#if> 
	                 style="border-radius: 100%" />
                </span>
                </#if>
                <span>
                
                    <label>
                    <#if  "${login_user['is_sub_account']}" == "0">
                    ${login_user['user_name']!}
                    <#else>
                     ${login_user['account_name']!}
                    </#if>
                    </label>
                    <img src="http://${image_domain!}/image/admin/img1.png" alt=""  />
                </span>
            </div>
            <img src="http://${image_domain!}/image/admin/menu_top_1.png" class="menu_top" id="menu_top" style="display:none;">
        </div>
        <div class="log-menu" style="display:none;">
            <#if  "${login_user['is_sub_account']}" == "0">
                <a href="/admin/account/manage">账户设置</a>
            <#else>
                <a href="/admin/subPasswordModify">修改密码</a>
            </#if>
            <#if  "${login_user['is_sub_account']}" == "0">
                <a href="/admin/account/user/list" target="main">子账号管理</a>
            </#if>
            <a href="/admin/public/service/auth/list">授权公众号</a>
            <a href="/admin/account/shop/select" title="选择店铺"  id="logout">选择店铺</a>
            <a href="/admin/logout" title="退出" id="logout">退出</a>
        </div>
    </div>
    <div class="lingdang">
        <#if  "${is_article!0}" == "1"><span></span></#if>
        <a href="/admin/notice/list"><img src="/image/admin/notice_ld.png" /></a>
    </div>
</div>