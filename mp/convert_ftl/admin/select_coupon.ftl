<html style="height: 320px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css" />
    <link href="/css/admin/select_coupon.css?v=1.0.3" rel="stylesheet" type="text/css" />
    <style>
        a{
            text-decoration: none;
        }
    </style>
</head>
<body style="background:none;height: 320px;margin:0;<#if (!count($coupon_list))border-bottom:1px solid #eee;height: 319px;</#if>">
<div class="coupon-select">
    <form action="/admin/frame/coupon/select" id="form1">
        <div class="voucher_top">
            <input type="text" placeholder="请输入优惠券名称" name="act_name" value="${request['act_name']!}">
            <span class="search" style="cursor:pointer;">
                    <img src="http://${image_domain!}/image/admin/shop_beautify/search.png" alt="" width="12px">
                    搜索
                </span>
            <img src="http://${image_domain!}/image/admin/shop_beautify/refresh.png" alt="" class="refresh">
        </div>
        <#if (count($coupon_list))
        <div class="voucher_content clearfix">
            <#list ($coupon_list as $coupon)
            <div class="coupon_list">
                <input type="hidden" coupon_id="${coupon->id!}" act_code="${coupon->act_code!}" denomination="{{floatval($coupon->denomination)!}" use_score="${coupon->use_score!}" score_number="${coupon->score_number!}" recommend_type="<#if ($coupon->recommend_goods_id!='' ||$coupon->recommend_cat_id!='' || $coupon->recommend_sort_id!='') 2 <#else> 1 </#if>" start_time="${coupon->start_time!}" end_time="${coupon->end_time!}" validity="${coupon->validity!}" class="coupon_info" />
                <img src="http://${image_domain!}/image/admin/shop_beautify/checked_card.png" class="card_checked" />
                <div class="coupon_list_top">
                    <#if ($coupon->act_code=='voucher')
                    &yen;<span>{{floatval($coupon->denomination)!}</span>
                    <#elseif>($coupon->act_code=='discount')
                    <span>{{floatval($coupon->denomination)!}</span>折
                    </#if>
                </div>
                <div class="coupon_list_center">
                    <#if ($coupon->use_consume_restrict == 1)
                    <div class="coupon_center_limit">满${coupon->least_consume!}使用</div>
                    <#else>
                    <div class="coupon_center_limit">不限制</div>
                    </#if>
                    <div class="coupon_center_number">剩余<span>${coupon->surplus!}</span>张</div>
                </div>
                <div class="coupon_list_bottom">
                    <#if  ($coupon->use_score == 1)${coupon->score_number!}积分 兑换
                    <#else>领取
                    </#if>

                </div>
                <div class="coupon_name">${coupon->act_name!}</div>
            </div>
            </#list>
            <div class="coupon_add" style="margin-bottom:10px;">
                <a href="/admin/market/coupon/add" target="_blank">
                    <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="" />
                    <p>添加优惠券</p>
                </a>
            </div>
        </div>
        <#else>
            <div class="no_coupon_style">
                <img src="http://${image_domain!}/image/admin/coupon-style.png" />
                <p>暂无优惠券，快去添加吧</p>
                <a href="/admin/market/coupon/add" target="_blank">
                    添加优惠券 >
                </a>
            </div>
        </#if>
    </form>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
    $(".search").on("click",function () {
        $("#form1").submit();
    });
    $(".refresh").on("click",function () {
        location.reload();
    });
</script>
</body>
</html>