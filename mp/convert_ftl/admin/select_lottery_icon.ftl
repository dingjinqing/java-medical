<html>
    <head>
        <link href="/css/admin/common.css?v=1.1.1" rel="stylesheet" type="text/css"/>
        <link href="/css/admin/lottery_manage.css?v=1.1.11" rel="stylesheet" type="text/css"/>
        <meta name="csrf-token" content="{{ csrf_token()!}">
    </head>
    <body style="background: #fff;">
        <div class="icon_container">
            <ul class="clearfix">
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/1.png" alt="" >
                    <input type="radio" name="icon_lottery" checked>
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/2.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/3.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/4.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
                <li style="margin-right: 0">
                    <img src="http://${image_domain!}/image/admin/icon_lottery/5.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
            </ul>
            <ul class="clearfix">
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/6.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/7.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/8.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
                <li>
                    <img src="http://${image_domain!}/image/admin/icon_lottery/thank.png" alt="">
                    <input type="radio" name="icon_lottery">
                </li>
            </ul>
        </div>
    </body>
</html>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
<script src="/js/artDialog/plugins/iframeTools.source.js"></script>
<script language="JavaScript" src="/js/admin/util.js?v=1.0.4"></script>