<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>${column!}_${global_title!}${title!}</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="robots" content="all">
    <meta name="author" content="mp.weipubao.cn">
    <meta name="keywords" content="小程序、微信小程序、小程序门店、小程序系统、小程序开发、小程序制作、小程序装修、小程序教程、定制开发、微信电商、微信商城、微信小程序开发、微信小程序装修、微信小程序制作、小程序认证、小程序注册"/>
    <meta name="description"
          content="【店+小程序】是国内专业的小程序服务商，拥有高效的研发资源，精简的实施方案。选择***，无需代码开发、成本低，一站式系统服务为您快速开启在线门店业务！">
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
<body>
<#include ("index.official_header")

    <div class="article_list_back">
        <div class="article_list_div">
            <#list  ($data_list as $item)
            <div class="clearfix article_list">
                <a href="/index/article/detail/${item->article_id!}.html" target="_blank">
                    <div class="article_list_left">
                        <img src="${item->head_pic!}" />
                    </div>
                    <div class="article_list_right">
                        <div class="article_right_title">${item->title!}</div>
                        <div class="article_right_content">${item->desc!}</div>
                        <div class="article_right_time">${item->pub_time_show!}</div>
                    </div>
                </a>
            </div>
            </#list>

        </div>
        <div class="page clearfix">
            <div class="layui-box layui-laypage layui-laypage-default" id="test1">
                <a href="javascript:;" class="layui-laypage-prev layui-disabled" data-page="0">上一页</a>
                <a href="javascript:;" class="layui-laypage-next" data-page="2">下一页</a>
            </div>
        </div>
    </div>

<#include ("index.official_footer")
<#include "/admin/footer.ftl">
</body>
<script type="text/javascript">
    $('.nav ul').find('li').eq(1).addClass('active');
    $('.article_right_title').hover(function () {
        $(this).css('color','#5a8bff');
    },function () {
        $(this).css('color','#333');
    });

    layui.use('laypage', function(){
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: '${data_list->total()!}' //数据总数，从服务端得到
            ,limit: 10
            ,curr: function(){
                    var page = "${page!}"; // 当前页(后台获取到的)
                    return page ? page : 1; // 返回当前页码值
                }(),
            jump: function (obj, first) {
                if (!first) {
                    var curr = obj.curr;
                    // var self = this;
                    location.href = "/index/article/list?page=" + curr;
                }
            }
        });


    });
</script>