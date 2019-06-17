<link rel="stylesheet" type="text/css" href="/css/admin/official_index.css?v=2.3.3"/>
<div class="header clearfix">
    <div class="logo"><img src="http://${image_domain!}/image/admin/official/head_logo.png"/></div>
    <div class="nav2">
        <#if (session()->get("shop_login_user")["user_name"])
            <div>
                <img src="<#if (session()->get("shop_login_user")["shop_avatar"]) {{session()->get("shop_login_user")["shop_avatar"]!} <#else> http://${image_domain!}/image/admin/head_icon.png </#if>"  width="30px" height="30px"  style="border: 1px solid #ddd;"/>
                <span>{{session()->get("shop_login_user")["user_name"]!}</span>
                <img src="http://${image_domain!}/image/admin/official/blue_down.png" class="head_down" />
                <div class="head_list">
                    <a href="/admin/account/shop/select" target="_blank">进入后台</a>
                    <a href="/admin/logout?type=index">退出登录</a>
                </div>
            </div>
        <#else>
            <ul class="clearfix">
                <li><a href="/admin/login" class="login" target="_blank">登录</a></li>
                <li><a href="/index/free/experience" target="_blank" class="reg">注册</a></li>
            </ul>
        </#if>
    </div>
    <div class="nav">
        <ul class="clearfix">
            <li class=""><a href="/">首页</a></li>
            <li><a href="/index/article/list" target="_blank">新闻资讯</a></li>
            <li><a href="http://bbs.weipubao.cn/" target="_blank">社区论坛</a></li>
            <li><a href="/index/about" target="_blank">关于我们</a></li>
            <li><a href="/index/free/experience" target="_blank">申请试用</a></li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    $('.nav2').find('div').hover(function () {
        $('.head_list').show();
        $('.head_down').attr('src','http://${image_domain!}/image/admin/official/blue_up.png');
    },function () {
        $('.head_list').hide();
        $('.head_down').attr('src','http://${image_domain!}/image/admin/official/blue_down.png');
    });
</script>