<html style="height: 420px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/sort_manage.css?v=1.1.10" rel="stylesheet" type="text/css"/>
    <meta name="csrf-token" content="{{ csrf_token()!}">
</head>
<body style="background: #fff;">
    <div class="icon_container">
        <ul class="clearfix">
            <li class="each_line clearfix">
                <div class="el_name">男装</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/man_cloth1.jpg">
                    <input type="radio" name="special_one" checked>
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/man_cloth2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">女装</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/women_cloth1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/women_cloth2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">男鞋</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/man_shoe1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/man_shoe2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">女鞋</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/women_shoe1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/women_shoe2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">内衣</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/neiyi1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/neiyi2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">箱包</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/bag1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/bag2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">美妆</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/make1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/make2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">个护</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/own_wash1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/own_wash2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">钟表</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/watch1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/watch2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">数码</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/shyma1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/shyma2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">家电</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/jiadian1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/jiadian2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">食品</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/foods1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/foods2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">酒水</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/water1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/water2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">母婴</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/mum1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/mum2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">玩具</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/toy1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/toy2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">运动</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/sport1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/sport2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">家具</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/jiaju1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/jiaju2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">厨具</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/kitchen1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/kitchen2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">鲜花</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/flower1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/flower2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
            <li class="each_line clearfix">
                <div class="el_name">图书</div>
                <div class="el_img1">
                    <img src="http://${image_domain!}/image/admin/icon_sort/book1.jpg">
                    <input type="radio" name="special_one">
                </div>
                <div class="el_img2">
                    <img src="http://${image_domain!}/image/admin/icon_sort/book2.jpg">
                    <input type="radio" name="special_one">
                </div>
            </li>
        </ul>
    </div>
</body>
</html>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
<script src="/js/artDialog/plugins/iframeTools.source.js"></script>
<script language="JavaScript" src="/js/admin/util.js?v=1.0.5"></script>