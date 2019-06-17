<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>${column!}_${global_title!}${title!}</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token()!}">
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
<#include ("index.official_header")
<form action="" id="form1" method="post">
    {{ csrf_field()!}
    <div class="p_apply">
        <div class="p_apply_free">
            <div class="apply_left">
                <div class="tiao"></div>
                <h5 class="p_h2">免费申请试用</h5>
                <div class="input_icon">
                    <input type="contact" placeholder="请填写您的姓名"   name="contact">
                    <span>姓名</span>
                </div>
                <div class="input_icon">
                    <input type="mobile" placeholder="请填写手机号" name="mobile" onkeyup="value=value.replace(/[^\d\-]/g,'')">
                    <span>电话</span>
                </div>
                <div class="p_apply_submit">提交</div>
            </div>
            <div class="apply_right">
                <img src="http://${image_domain!}/image/admin/index_circle.png" alt="" />
                <h3>提供企业规模化、多体系新零售解决方案</h3>
                <h4>多系统无缝对接</h4>
            </div>
        </div>
    </div>
</form>


<script type="text/javascript">
    $('.p_apply_submit').click(function(){
        var code = $('input[name="code"]').val();
        if ($('input[name="mobile"]').val() == '') {
            util.mobile_alert('请填写手机号');
            return false;
        }
        if($('input[name="mobile"]').val().length > 20){
            util.mobile_alert("请输入20个字符内的电话号码");
            return false;
        }
        if ($('input[name="contact"]').val() == '') {
            util.mobile_alert('请填写您的姓名');
            return false;
        }

        var param = $(".apply_left :input").serializeArray();
        util.ajax_json("/index/check/free/experience", function (d) {
            if (d && d.error == 0) {
                util.mobile_alert('提交申请成功，请等待业务员联系');
            } else {
                util.mobile_alert(d.message);
            }
        }, param);
    });
</script>

<#include ("index.official_footer")
<#include "/admin/footer.ftl">