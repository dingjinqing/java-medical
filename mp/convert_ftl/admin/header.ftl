<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <link href="/css/admin/base.css?v=0.1.8" rel="stylesheet" type="text/css"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-production.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-skins.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/jquery.bigcolorpicker.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui/css/layui.css?v=1.0.7" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.7" type="text/css"/>
    <link href="/css/admin/common.css?v=1.1.3" rel="stylesheet" type="text/css"/>
    <link href="${favicon or "/favicon.ico"!}" rel="shortcut icon"/>
    <link rel="stylesheet" href="/js/artDialog/skins/opera.css?4.1.8">
    <link href="/css/admin/menu.css?v=1.1.17" rel="stylesheet" type="text/css">

    <title>${title!} ${global_title!}</title>

    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <script language="JavaScript" src="/js/jquery.json.js"></script>
    <script language="JavaScript" src="/js/admin/index.js?v=1.1.5"></script>

    <script language="JavaScript" src="/js/date/WdatePicker.js"></script>
    <script src="/js/layui/layui.js?v=1.0.7" type="text/javascript"></script>
    <script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
    <script language="JavaScript" src="/js/artDialog/plugins/iframeTools.source.js?v=1.0.8"></script>
    <script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/util.js?v=1.0.10"></script>
    <script language="JavaScript" src="/js/admin/util.js?v=1.1.10"></script>
    <script language="JavaScript" src="/js/admin/table.js?v=1.0.6"></script>

</head>
<body class="${no_full ? "no-full" : ""!}">


<div id="j_ajax_loading"></div>
<script>
    $(".help_detail").find("li").hover(function () {
        $(this).addClass("color-white");
    }, function () {
        $(this).removeClass("color-white");
    });
    $(".help_container").hover(function () {
        $(this).find(".help_center").addClass("bg-blue");
        $(this).find(".help_detail").show();
    }, function () {
        $(this).find(".help_center").removeClass("bg-blue");
        $(this).find(".help_detail").hide();
    });
</script>

<#include ("admin.menu")
