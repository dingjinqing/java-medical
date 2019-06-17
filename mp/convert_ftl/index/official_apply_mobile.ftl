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
    <script language="JavaScript" src="/js/admin/util.js?v=1.0.9"></script>
    <script language="JavaScript" src="/js/admin/table.js?v=1.0.5"></script>
    <link rel="stylesheet" type="text/css" href="/css/admin/official_index.css?v=2.2.5"/>
    <link rel="stylesheet" href="/css/admin/official_mobile.css?v=1.0.9" type="text/css" />
    <style type="text/css">
        html{
            height:100%;
        }
        body {
            height: 100%;
        }
        .apply .apply_free{
            width: auto;
        }
        .apply_list textarea{
            height:80px;
        }
        .apply_list span{
            width: 20%;
            text-align: right;
           color: #333;
            font-size: 14px;
        }
        .apply_list input, .apply_list select, .apply_list textarea{
            width: 70%;
        }
        /*店+*/
        .apply_bgc{
            width: 100%;
            height: 7.05rem;
            background: url("/image/admin/apply_fortrial_bg2.jpg") no-repeat;
            background-size: 100% 100%;
        }
        .apply{
            padding: 0;
            height:100%;
        }
        .apply .apply_free{
            padding: 0;
            padding-bottom: 5%;
            height:100%;
            box-shadow: 0 0 0 #fff;
            background: #dee8ff;
        }
        .apply_submit{
            width: 80%;
            margin-left: 11%;
            position: absolute;
            bottom: -20px;
            background: linear-gradient(left,#5A8BFF,#28a6ff);
            background: -webkit-linear-gradient(left,#5A8BFF,#28a6ff);
            background: -moz-linear-gradient(left,#5A8BFF,#28a6ff);
            border-radius: 20px;
        }
      .apply_list input{
          box-shadow:0px 0px 0px rgba(0,0,0,0);
          -webkit-appearance:none;
          border: 1px solid #ddd;
          line-height: 20px;
      }

    </style>
</head>


<form action="" id="form1" method="post" style="height: 100%;background: #fff;">
    {{ csrf_field()!}
    <input type="hidden" id="source" name="source" value="${source!}">
    <input type="hidden" id="user_id" name="user_id" value="${user_id!}">
    <div class="apply">
        <div class="apply_free">
            <div class="apply_bgc">
            </div>
            <div class="message_content" >
                <div class="apply_list fs_list" style="margin-top: 20px">
                    <span>联系人</span>
                    <input type="text" name="contact" placeholder="请填写您的姓名" />
                </div>
                <div class="apply_list" style="margin-bottom: 40px">
                    <span>手机号</span>
                    <input type="text" name="mobile" placeholder="请填写手机号" />
                </div>
                {{--<div class="apply_list" style="margin-bottom: 40px">--!}
                    {{--<span>公&nbsp;&nbsp;司</span>--!}
                    {{--<input type="text" name="company" placeholder="请填写您的公司" />--!}
                {{--</div>--!}
                <div class="apply_submit">提交</div>
            </div>
            <div class="apply_success">
                <div class="succ">
                    <img src="http://${image_domain!}/image/admin/new_apply_mobile/success.png" alt="">
                </div>
                <p>提交成功,我们会尽快与您联系</p>
            </div>
            <div class="func funcc">
                <img src="http://${image_domain!}/image/admin/new_apply_mobile/function_great.png">
            </div>
            <div class="func_content">
                 <div class="single-func">
                     <div class="func_img">
                         <img src="http://${image_domain!}/image/admin/new_apply_mobile/apply.png" alt="">
                     </div>
                     <p>预约服务</p>
                     <h4>线上引流、多项预约模式</h4>
                     <h4>提高顾客体验</h4>
                 </div>
                <div class="single-func">
                    <div class="func_img">
                        <img src="http://${image_domain!}/image/admin/new_apply_mobile/store_management.png" alt="">
                    </div>
                    <p>门店管理</p>
                    <h4>多门店连锁经营,获客细分</h4>
                    <h4>增加曝光、降低管理成本</h4>
                </div>
                <div class="single-func">
                    <div class="func_img">
                        <img src="http://${image_domain!}/image/admin/new_apply_mobile/yxhd.png" alt="">
                    </div>
                    <p>营销活动</p>
                    <h4>拼团、砍价、分销、秒杀、</h4>
                    <h4>优惠券、抽奖、加价购等</h4>
                </div>
                <div class="single-func">
                    <div class="func_img">
                        <img src="http://${image_domain!}/image/admin/new_apply_mobile/dpzj.png" alt="">
                    </div>
                    <p>店铺装修</p>
                    <h4>行业模板按需试用</h4>
                    <h4>模块拖拽、无需代码编辑</h4>
                </div>
                <div class="single-func">
                    <div class="func_img">
                        <img src="http://${image_domain!}/image/admin/new_apply_mobile/data_count.png" alt="">
                    </div>
                    <p>数据统计</p>
                    <h4>销量、流量来源、精准统计</h4>
                    <h4>用户画像精准分析</h4>
                </div>
                <div class="single-func">
                    <div class="func_img">
                        <img src="http://${image_domain!}/image/admin/new_apply_mobile/vip.png" alt="">
                    </div>
                    <p>会员管理</p>
                    <h4>线上线下会员打通</h4>
                    <h4>会员卡、积分、限次卡</h4>
                </div>
                <div class="single-func">
                    <div class="func_img">
                        <img src="http://${image_domain!}/image/admin/new_apply_mobile/wlgl.png" alt="">
                    </div>
                    <p>物流管理</p>
                    <h4>多种运费模板一键选择</h4>
                    <h4>区域性运费模板轻松设置</h4>
                </div>
                <div class="single-func">
                    <div class="func_img">
                        <img src="http://${image_domain!}/image/admin/new_apply_mobile/zxjy.png" alt="">
                    </div>
                    <p>在线交易</p>
                    <h4>微信、余额、积分、买单</h4>
                    <h4>多种支付方式轻松开启</h4>
                </div>
            </div>
            <div class="func" style="margin-top: 30px">
                <img src="http://${image_domain!}/image/admin/new_apply_mobile/khal.png">
            </div>
            <div class="customer">
                <div class="customer_img">
                    <img src="http://${image_domain!}/image/admin/new_apply_mobile/ajjc.png" alt="">
                </div>
                <p>安吉九茶</p>
                <h4>让更多人喝到安心正味好茶</h4>
            </div>
            <div class="customer">
                <div class="customer_img">
                    <img src="http://${image_domain!}/image/admin/new_apply_mobile/lsr.png" alt="">
                </div>
                <p>雷瑟尔行货商城</p>
                <h4>行货商品更有性价比</h4>
            </div>
            <div class="customer">
                <div class="customer_img">
                    <img src="http://${image_domain!}/image/admin/new_apply_mobile/bjhz.png" alt="">
                </div>
                <p>北京寒竹梅鲜花</p>
                <h4>良心品质、用心服务</h4>
            </div>
            <div class="customer">
                <div class="customer_img">
                    <img src="http://${image_domain!}/image/admin/new_apply_mobile/zwlj.png" alt="">
                </div>
                <p>中外旅居</p>
                <h4>品质为本，精益求精</h4>
            </div>
            <div class="apply_footer">
                <div class="footer_content">
                    <p class="fp1">北京掌上先机网络科技有限公司</p>
                    <p class="fp2">电话:&nbsp;  400-010-1039 &nbsp; 7*24小时</p>
                    <p class="fp2">北京总部：北京市海淀区花园路天博中润2层216</p>
                    <p class="fp2">上海分公司：上海市闵行区七莘路1188号A座509</p>
                </div>
            </div>
        </div>
    </div>
</form>

<script>
        // 调试工具
        !(function () {
            var src = 'http://cdn.bootcss.com/eruda/1.3.2/eruda.min.js';
            if (!/eruda=true/.test(window.location) && localStorage.getItem('active-eruda') != 'true') return;
            document.write('<scr' + 'ipt src="' + src + '"></scr' + 'ipt>');
            document.write('<scr' + 'ipt>eruda.init();</scr' + 'ipt>');
        })();
        function setHTMLSize(){
            var oHtml = document.querySelector('html');
            var width = oHtml.getBoundingClientRect().width;
            oHtml.style.fontSize = width / 7.5 + 'px';  // 针对设计尺寸为750 , 如果为 640 此处 / 16;
        }
        function windowResize(){
            window.addEventListener('resize',function(){
                setHTMLSize();
            },false);
        }

        setHTMLSize();
        windowResize();
    </script>
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
    $('.apply_submit').click(function(){
        if ($('input[name="contact"]').val() == '') {
            util.mobile_alert('请填写您的姓名');
            return false;
        }
        if ($('input[name="mobile"]').val() == '') {
            util.mobile_alert('请填写手机号');
            return false;
        }
        if ($('input[name="company"]').val() == '') {
            //util.mobile_alert('请填写公司名称');
            //return false;
        }
        /*验证正确格式的手机号*/
        if(!util.verify_mobile($('input[name="mobile"]').val())){
            return false;
        }
        var data = {};
        data.contact = $('input[name="contact"]').val();
        data.mobile = $('input[name="mobile"]').val();
        data.company = $('input[name="company"]').val();
        data.source = $('input[name="source"]').val();
        data.user_id = $('input[name="user_id"]').val();
        util.ajax_json('/index/check/free/experience',function (d) {
            if(d && d.error == 0){
                layer.ready(function () {
                    layer.msg('提交成功，请等待客服联系！', {time: 1000},function () {
                        // wx.miniProgram.reLaunch({url: '/pages/index/index'});
                        $(".message_content").hide();
                        $(".funcc").css("margin-top","30px");
                        $(".apply_success").show();
                    });
                });
            }else{
                util.mobile_alert(d.message);
            }
        },data)
    })
</script>