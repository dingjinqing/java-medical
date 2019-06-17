<html>
<head>
    <link href="/css/admin/common.css?v=3.2.1" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/template_list.css?v=1.0.1" type="text/css" />
</head>
<body style="background: #fff;">
<div class="list_container">
    <ul>
        <li>
            <input type="radio" name="tem_list" checked />
            <span>偷偷告诉你，花旗西洋参今晚7点准时开抢，库存有限，错过就涨价，赶快加购物车吧~</span>
        </li>
        <li>
            <input type="radio" name="tem_list" />
            <span>520表白日，因为爱你，所以店内商品限时88折，点击查看详情</span>
        </li>
        <li>
            <input type="radio" name="tem_list" />
            <span>感谢你常常光临，我们特为你准备了一张满100减20的优惠券，赶快领取吧</span>
        </li>
        <li>
            <input type="radio" name="tem_list" />
            <span>亲爱的会员，今天是61活动最后一天了，全场产品都可以使用优惠券，还有会员折扣哦，千万不要错过</span>
        </li>
        <li>
            <input type="radio" name="tem_list" />
            <span>六一活动开始啦，有机奶粉238元罐，金装奶粉159元罐，3听起送礼品，进店查看详情</span>
        </li>
        <li>
            <input type="radio" name="tem_list" />
            <span>五一特惠活动，买五送一、买九送二、买十二送三正在火热进行中，还没有下单的亲尽快下单哦！</span>
        </li>
        <#list  ($list as $item)
            <li>
                <input type="radio" name="tem_list" />
                <span>${item->content!}</span>
            </li>
        </#list>
    </ul>
</div>

<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
</body>
</html>