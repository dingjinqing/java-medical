<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html>
<head>
    <title>${global_title!}</title>
    <meta name="csrf-token" content="{{ csrf_token()!}">
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
		<img src="http://${image_domain!}/image/admin/official/bottom_logo.png" alt="微铺宝" style="height:49px;margin-top: 18px;" />
	</div>
    <div class="fr">
        {{--<span><img src="/image/admin/head_icon.png" alt=""/></span>--!}
        {{--<span class="fr-number">13678290022</span>--!}
        {{--<img src="/image/admin/img1.png" alt=""  />--!}
        <div class="account">
            <!-- logout button -->
            <div class="btn-header transparent pull-right">
                <#if  (!$user['is_sub_account'])<span>
                    <img <#if ($user['shop_avatar'] != '') src="${user['shop_avatar']!}" <#else> src="http://${image_domain!}/image/admin/head_icon.png" </#if> style="border-radius: 100%"/>
                </span>
                </#if>
                <span>
                    <label>
                        ${user['account_name'] or $user['user_name']!}
                    </label>
                    <img src="http://${image_domain!}/image/admin/img1.png" alt=""  />
                </span>
            </div>
            <img src="http://${image_domain!}/image/admin/menu_top_1.png" class="menu_top" id="menu_top" style="display:none;">
        </div>
        <div class="log-menu" style="display:none;">
            <#if  (!$user['is_sub_account'])
                <a href="/admin/account/manage">{{ trans("admin/index.memu.accountset")!}</a>
            <#else>
                <a href="/admin/subPasswordModify">{{ trans("admin/index.memu.passwdset")!}</a>
            </#if>
            <#if  (!$user['is_sub_account'])
                <a href="/admin/account/user/list" target="main">{{ trans("admin/index.memu.child_account_admin")!}</a>
            </#if>
            <a href="/admin/public/service/auth/list">授权公众号</a>
            <a href="/admin/account/shop/select" title="{{ trans("admin/index.memu.select_shop")!}"
               id="logout">{{ trans("admin/index.memu.select_shop")!}</a>
            <a href="/admin/logout" title="{{ trans("admin/index.memu.logout")!}"
               id="logout">{{ trans("admin/index.memu.logout")!}</a>
        </div>
    </div>
    <div class="lingdang">
        <#if ($is_article)<span></span></#if>
        <a href="/admin/notice/list"><img src="/image/admin/notice_ld.png" /></a>
    </div>
</div>