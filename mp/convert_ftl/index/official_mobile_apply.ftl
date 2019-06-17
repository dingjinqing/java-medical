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
    <link rel="stylesheet" type="text/css" href="/css/admin/official_index.css?v=2.2.5"/>
    <link rel="stylesheet" href="/css/admin/official_mobile.css?v=1.0.1" type="text/css" />
    <style type="text/css">
        .apply{
            padding:20px;
        }
        .apply .apply_free{
            width: auto;
        }
        .apply_list textarea{
            height:80px;
        }
        .apply_list span{
            width: 22%;
        }
        .apply_list input, .apply_list select, .apply_list textarea{
            width: 70%;
        }
    </style>
</head>
{{--<div class="header clearfix" style="min-width: auto;padding: 0 20px">--!}
    {{--<div class="logo"><img src="/image/admin/official/head_logo.png" /></div>--!}
{{--</div>--!}

<form action="" id="form1" method="post">
    {{ csrf_field()!}
    <div class="apply">
        <div class="apply_free">
            <h2>免费申请试用</h2>
            <div class="apply_list">
                <span><em>* </em>公司</span>
                <input type="text" name="company" placeholder="请填写您的公司名称"  />
            </div>
            <div class="apply_list">
                <span><em>* </em>电话</span>
                <input type="text" name="mobile" placeholder="请填写手机号" />
            </div>
            <div class="apply_list">
                <span><em>* </em>联系人</span>
                <input type="text" name="contact" placeholder="请填写您的姓名" />
            </div>
            <div class="apply_list">
                <span><em>* </em>省份</span>
                <select name="province_id" id="province_id"  >
                    <option value="" style="color: #666">请选择您的省份</option>
                    <#list  ($provinces as $item)
                        <option value="${item->province_id!}" style="color: #666">${item->name!}</option>
                    </#list>

                </select>
            </div>
            <div class="apply_list">
                <span><em>* </em>验证码</span>
                <input type="text" name="code" placeholder="请填写验证码" style="width: 38%;" />
                <img class="code" src="" title="看不清楚，换一张"
                     style="cursor: pointer; vertical-align:middle;width:30%;height:40px;margin-left:5px;" onClick="create_code()"/>
            </div>
            <div class="apply_list" style="margin-bottom: 5px;">
                <span><em>* </em>留言</span>
                <textarea name="content" id="" placeholder="在此填写申请留言，我们会及时联系您！（最多可输入200字）"></textarea>
            </div>
            <div class="apply_submit">提交</div>
        </div>
    </div>
</form>

<div class="part6">
    <div class="part6_top">
        <p>TEL：400-010-1039&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7*24小时服务热线</p>
        <p>公司地址：北京市海淀区花园路天博中润2层216</p>
    </div>
    <div class="part6_bot">
        <img src="http://${image_domain!}/image/admin/official/weixin.jpg" width="100px"/>
        <p>关注微铺宝精选，了解小程序动态</p>
    </div>
</div>

<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?3480acc4fc995baa9433695b180a4893";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?168b8cbc117fcc08fdae905189d4feb9";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
<script type="text/javascript">

    function create_code(flag){
        if(typeof(flag) == 'undefined'){
            $("[name='code']").val("");
        }
        $(".code")[0].src = '/index/create/code?'+Math.random()*10000;
    }
    create_code();
    $('.apply_submit').click(function(){
        var code = $('input[name="code"]').val();
        $.ajax({
            type: "post",
            url: "/index/check/code",
            data: {code:code,'_token':'{{csrf_token()!}'},
            dataType: "json",
            success: function(data){
                switch (data.error){
                    case 0:
                        if ($('input[name="company"]').val() == '') {
                            util.mobile_alert('请填写您的公司名称');
                            return false;
                        }
                        if ($('input[name="mobile"]').val() == '') {
                            util.mobile_alert('请填写手机号');
                            return false;
                        }
                        if ($('input[name="contact"]').val() == '') {
                            util.mobile_alert('请填写您的姓名');
                            return false;
                        }
                        if ($('input[name="code"]').val() == '') {
                            util.mobile_alert('请填写验证码');
                            return false;
                        }
                        if ($('select[name="province_id"]').val() == '') {
                            util.mobile_alert('请填写您所在的省份');
                            return false;
                        }
                        if ($('textarea[name="content"]').val() == '') {
                            util.mobile_alert('请填写申请留言');
                            return false;
                        }
                        if ($('textarea[name="content"]').val().trim().length > 200) {
                            util.mobile_alert('留言不能超过200字！');
                            return false;
                        }
                        util.mobile_alert('提交成功，请等待客服联系！');
                        $("#form1").submit();
                        break;
                    case -1: console.log('验证码传输失败');
                        util.mobile_alert('验证码发送失败');
                        break;
                    case 1: console.log('错误');
                        util.mobile_alert('验证码错误');
                        break;

                }


            }
        });
    });


    $('.nav ul').find('li').eq(4).addClass('active');
</script>