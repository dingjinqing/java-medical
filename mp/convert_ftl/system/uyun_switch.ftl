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
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
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
    <script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/util.js?v=1.0.9"></script>
    <script language="JavaScript" src="/js/admin/util.js?v=1.1.9"></script>
    <script language="JavaScript" src="/js/admin/table.js?v=1.0.6"></script>

</head>

<link rel="stylesheet" href="/css/admin/shop_setting.css?v=1.3" type="text/css" />
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<style type="text/css">
    .btn-save:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .btn-save:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }

    input[type="text"]{
        padding-left:12px;
    }
    .btn-save{
        color: #fff !important;
        margin-left: 20%;
        background: #aac1e0;
        border:#aac1e0;
    }
    .pay_fl{
        margin-left: 20px;
    }
</style>
<div style="position: absolute;top:0;left:0;width: 100%;height:170px;">
<div class="title" style="font-size: 14px;">
    <span>图片存储 / </span><span style="color: #666;">cdn开关</span>
</div>
<div class="order-container" style="padding-bottom: 10px;height: 170px;">
    <div class="setting-head fix_every_footer">

        <div class="">
            <div style="padding-top: 25px;padding-left: -10px;">
                <form action="/system/image/uyunswitch" method="post" id="form1">
                    {{ csrf_field()!}
                    <div class="content1-pay1">
                        <ul>
                            <li class="con1-pay-list clearfix" style="background: white;border:0px;">
                                <span class="fl mr_10">上传cdn开关  </span>
                                <div class="fl pay_fl" img_id="${switch_flag == 1 ? 1 : 0!}"  <#if ($switch_flag == 1)style='background: url("http://${image_domain!}/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if>>
                                    <label>
                                        {{--<input type="text" value="${switch_flag!}">--!}
                                        <input type="checkbox" name="uyun_switch" aid="" <#if ($switch_flag == 1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($switch_flag == 1)style="right:0px;"</#if>/>
                                    </label>
                                </div>
                                <span></span>
                                <span class="pay-text">
                                    开关开启，图片将上传到又拍云cdn
                                </span>
                                <span> <button class="btn-save">保存</button></span>
                            </li>
                        </ul>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script src="/js/admin/util.js" type="text/javascript"></script>
<script>

    $('input[type="checkbox"]').click(function(){
        let str = $(this).prop('checked') ? "已开启" : "已关闭";
        $(this).parent().parent().next().text(str);
        if($(this).prop('checked')){
            $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
            $(this).next().animate({right:"0px"});
            $(this).attr("aid","1");
        } else {
            $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
            $(this).next().animate({right:"20px"});
            $(this).attr("aid","0");
        }
    })

</script>

</div>
</html>
