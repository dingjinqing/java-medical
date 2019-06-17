<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>${news_info->title!}</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="robots" content="all">
    <meta name="author" content="mp.weipubao.cn">
    <meta name="keywords" content="${news_info->keyword!}${news_info->title!}"/>
    <meta name="description"
          content="${news_info->desc!}">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <link href="/css/admin/base.css?v=0.1.8" rel="stylesheet" type="text/css"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-production.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-skins.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/jquery.bigcolorpicker.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />
    <link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.0" type="text/css" />
    <link href="/css/admin/common.css?v=3.2.0" rel="stylesheet" type="text/css"/>
    <link href="/favicon.ico?v=1.0" rel="shortcut icon"/>
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <script language="JavaScript" src="/js/jquery.json.js"></script>
    <script language="JavaScript" src="/js/date/WdatePicker.js"></script>
    <script src="/js/layui/layui.js" type="text/javascript"></script>
    <script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
    <script language="JavaScript" src="/js/artDialog/plugins/iframeTools.source.js"></script>
    <script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/util.js"></script>
    <script language="JavaScript" src="/js/admin/util.js?v=1.0.8"></script>
    <script language="JavaScript" src="/js/admin/table.js?v=1.0.5"></script>
</head>
<#include ("index.official_header")

    <div class="article_list_back">
        <div class="article_list_div">
            <div class="article_detail">
                <div class="article_detail_title">${news_info->title!}</div>
                <div class="article_detail_time">${news_info->pub_time_show!}</div>
                <p class="article_detail_content">{!! $news_info->content !!}</p>
                {{--<img src="${news_info->head_pic!}" />--!}
            </div>
        </div>
    </div>


<#include ("index.official_footer")
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('.nav ul').find('li').eq(1).addClass('active');

</script>