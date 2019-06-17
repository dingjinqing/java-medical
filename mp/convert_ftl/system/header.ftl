<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <link href="/css/system/base.css?v=0.1.8" rel="stylesheet" type="text/css"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-production.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-skins.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/jquery.bigcolorpicker.css" rel="stylesheet" type="text/css"/>
    <link href="/css/system/common.css?v=0.1.6" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.0" type="text/css"/>
    <link href="${favicon or "/favicon.ico"!}" rel="shortcut icon"/>
    <title>${global_title!} ${title!}</title>

    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <script language="JavaScript" src="/js/jquery.json.js"></script>

    <script language="JavaScript" src="/js/date/WdatePicker.js"></script>
    <script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
    <script language="JavaScript" src="/js/artDialog/plugins/iframeTools.source.js"></script>
    <script language="JavaScript" src="/js/system/util.js?v=1.0.8"></script>
    <script language="JavaScript" src="/js/system/table.js?v=1.0.5"></script>
    <script src="/js/layui/layui.js" type="text/javascript"></script>
    <script src="/js/layer/layer.js" type="text/javascript"></script>

</head>
<body style="padding-left:5px;padding-right:5px;">

<div id="j_ajax_loading"></div>
<#if  (!($no_full??0))
    <div class="split_container">
        <div class="split_tab hide"></div>
    </div>
    <div class="breadcrumb header-crumb">
        <li>${global_title!}</li>
        <li <#if  (!($sub_title ?? ""))class="active" </#if>>${title!}</li>
        <#if  ($sub_title ?? "")
            <li class="active">${sub_title!}</li>
        </#if>
    </div>
</#if>

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



