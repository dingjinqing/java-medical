<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/wx_auth_success.css?v=1.0.1" type="text/css"/>
<style>
    .goods_circle_footer{
        width: 1179px;
        display: block;
        border-top: 1px solid #f2f2f2;
        background: #f8f8fa;
        /* padding: 10px; */
        text-align: center;
        position: fixed;
        z-index: 2;
        bottom: 0;
        /* margin-left: 23%; */
    }
    .save{
        margin-top: 16px;
        width: 143px;
        height: 41px;
        border: none;
        background: #5A8BFF;
        color: #fff;
        margin-left: -32%;
        border-radius: 7px;
    }
    .page_title{
        font-size: 25px;
        font-weight:bold;
        padding-bottom: 5px;
        width: 800px;
        text-align: center;
    }
    .page_time{
        width: 800px;
        color: #666;
        margin-bottom: 20px;
        border-bottom: 0px solid #eee;
        padding-bottom: 22px;
        text-align: center;
        margin-top: 12px;
    }

</style>
<div style="min-width: 1090px;">
    <div class="title" style="margin: 0;">
        <span>小程序管理/ </span>
        <span>小程序授权 / </span>
        <span style="color: #666;">微信好物圈-功能介绍</span>
    </div>
    <div class="main-container">
        <div class="all_passage">
            <div class="page_title">小程序"好物圈"功能，已完成对接</div>
            <div class="page_time">2019-05-30</div>
            <div class="content_title">亲爱的小程序商家，您好：</div>
            <div class="page_sen">
                <div>“好物圈”功能，目前已完成开发对接。基于小程序的商品搜索，将进一步帮助商家享受微信流量新红利！目前，在小程序商家后台可以一键开启此功能。用户加到购物车的商品、已购订单以及好友对已购商品的点赞和评价均可出现在小程序“好物圈”里。</div>
            </div>
            <div class="each_ares_tit">【什么是好物圈】</div>
            <div class="page_sen">
                <div>1.好物圈是微信提供的小程序购物车和订单管理工具；</div>
                <div>2.小程序接入好物圈后，购物车和订单商品将同步至购物单的“想买清单”和“已购订单”，同时也可以被微信用户搜索到。用户通过好物圈，可以快速找到在小程序店铺加入购物车的商品和已支付成功的订单。通过好物圈可以直接进入商家的小程序店铺再次访问购买；</div>
                <div>3.用户可以通过“好物圈”统一管理所有小程序内加入购物车的商品以及已支付订单，同时可对已购商品进行点赞评价。点赞评价达到一定次数的商品支持微信所有用户通过“微信-发现-小程序”进行搜索，并且会出现在好物圈的“值得买”列表中。</div>
            </div>
            <img src="http://${image_domain!}/image/admin/wemini_img2s.png" alt="" style="width: 800px;margin: 20px 0">
            <div class="each_ares_tit" style="margin-top: 0">【如何开启好物圈】</div>
            <div class="page_sen">
                <div>商家可通过小程序后台“小程序管理-小程序授权”中的“好物圈”开关开启。</div>
            </div>
            <div class="goodsList_tips">注意：开启和关闭好物圈都需要重新授权小程序，勾选好物圈权限。</div>
            <img src="http://${image_domain!}/image/admin/wemini_img3s.png" alt="" style="width: 800px;margin: 20px 0">
            <div class="each_ares_tit" style="margin-top: 0">【用户使用场景】</div>
            <div class="page_sen">
                <div>1.在“好物圈”，用户可以查看所有在对接该功能的小程序中添加至购物车的商品及已支付成功的订单信息，并可快速回到小程序店铺；</div>
                <div>2.用户可以对已购商品进行推荐评价，被推荐评价的商品会出现在好友的“值得买”列表中，通过好友间互相传播，快速带来流量增长；</div>
                <div>3.用户在微信“发现-小程序”搜索框中搜索商品时，被好友推荐评价过的商品会直接出现在搜索结果中。</div>
            </div>
            <img src="http://${image_domain!}/image/admin/wemini_img4s.png" alt="" style="width: 800px;margin: 20px 0">
            <div class="each_ares_tit" style="margin-top: 0">【用户使用示例】</div>
            <div class="page_sen">
                <div>1.“好物圈”入口；</div>
                <div>用户可以从“微信-发现-小程序”搜索中找到好物圈。用户点击搜索框，获取焦点的同时，好物圈即可出现在搜索框下方。</div>
            </div>
            <div class="goodsList_tips">注意：用户需要在开通了“好物圈”功能的小程序中发生过加购/下单行为，有过商品/订单的同步数据后，才能看到“好物圈”入口。</div>
            <div class="page_sen">
                <div>2.“好物圈”商品及订单的同步；</div>
                <div>用户可以在好物圈中查看通过小程序添加至购物车的商品以及支付成功的订单，点击可直接进入商家的小程序店铺。</div>
            </div>
            <div class="goodsList_tips">温馨提示：仅点赞评价较多的商品才可以通过小程序搜索到，商家可引导买家使用好物圈，并对已经确认收货的订单在【我的购物单】－【已购订单】中点赞评价。</div>
            <div class="each_ares_tit" style="margin-top: 0">【好物推荐】</div>
            <div class="page_sen">
                <div>1.在商家后台“小程序管理”中，开启好物圈功能后，可同时开启“好物推荐”功能。开启后，用户可以在小程序内，商品详情页、订单详情页，将商品直接推荐到好物圈。</div>
            </div>
            <img src="http://${image_domain!}/image/admin/wemini_img5s.png" alt="" style="width: 800px;margin: 20px 0">
            <div class="page_sen">
                <div>赶快使用起来吧！有任何问题可以联系我们。</div>
            </div>
            <div class="goods_circle_footer">
                <button  class="save">开通微信好物圈</button >
            </div>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script>
    $(".save").click(function(){
         window.location.href = "/wechat/mini/info?type=1&top_index=1#goods_circle";
    })
</script>
