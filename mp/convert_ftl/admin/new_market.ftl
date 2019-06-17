<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/new_market.css?v=1.1.3" type="text/css">
<style type="text/css">
    a:link,a:focus,a:hover,a:active{
        text-decoration: none;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a></span>
    </div>
    <div class="main_container">
         <div class="market_container">
             <div class="market_nav">
                 <div class="market_nav_title">
                     <span class="title_one">裂变营销</span>
                     <span class="title_two">开启社交化裂变推广模式</span>
                 </div>
                 <div class="market_nav_content " >
                     <a class="market_yx marketing_type" title="拼团" val="pin_group" href="/admin/market/pingroup/list?nav=1&top_index=4" link="/admin/market/pingroup/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/drpt.png" >
                         </div>
                         <div class="market_text">
                             <p>多人拼团</p>
                             <span>引导用户邀请朋友拼团购买</span>
                         </div>
                     </a>
                     <a class="market_yx marketing_type" title="砍价" val="bargain" href="/admin/market/bargain/list?nav=1&top_index=4" link="/admin/market/bargain/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/kj.png" >
                         </div>
                         <div class="market_text">
                             <p>砍价</p>
                             <span>引导用户邀请朋友砍价</span>
                         </div>
                     </a>
                     <a class="market_yx marketing_type" title="分销" val="distribution" href="/admin/market/distribution/config?top_index=4" link="/admin/market/distribution/config?top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/fx.png" >
                         </div>
                         <div class="market_text" >
                             <p>分销</p>
                             <span>裂变式营销玩法</span>
                         </div>
                     </a>
                     <a class="market_yx marketing_type" title="抽奖" val="lottery" href="/admin/market/lottery/list?top_index=4&nav=1" link="/admin/market/lottery/list?top_index=4&nav=1" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/xxdcj.png?top_index=4" >
                         </div>
                         <div class="market_text">
                             <p>幸运大抽奖</p>
                             <span>九宫格式抽奖玩法</span>
                         </div>
                     </a>
                     <a class="market_yx marketing_type" title="拼团抽奖" val="group_draw" href="/admin/market/groupdraw/list?top_index=4&nav=1" link="/admin/market/groupdraw/list?top_index=4&nav=1" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/ptcj.png" >
                         </div>
                         <div class="market_text">
                             <p>拼团抽奖</p>
                             <span>拼团参与抽奖,裂变式转化</span>
                             {{--<img src="/image/admin/new_market/new.png" class="jjsx">--!}
                         </div>
                     </a>
                     <a class="market_yx marketing_type" title="瓜分积分" val="pin_integration" href="/admin/market/integration/list?top_index=4&nav=1" link="/admin/market/integration/list?top_index=4&nav=1" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/gfjf.png?top_index=4" >
                         </div>
                         <div class="market_text">
                             <p>组团瓜分积分</p>
                             <span>提高用户活跃度,引导用户拼团得积分</span>
                             {{--<img src="/image/admin/new_market/new.png" class="jjsx">--!}
                         </div>
                     </a>
                     <a class="market_yx marketing_type" title="好友助力" val="promote" href="/admin/market/promote/list?top_index=4&nav=1" link="/admin/market/promote/list?top_index=4&nav=1" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/friend_promote_cion.png?top_index=4" >
                         </div>
                         <div class="market_text">
                             <p>好友助力</p>
                             <span>好友帮忙获得奖励</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                 </div>
             </div>

             <div class="market_nav market_nav_new">
                 <div class="market_nav_title">
                     <span class="title_one">营销活动</span>
                     <span class="title_two">让用户更多重复购买，提供客单贡献</span>
                 </div>
                 <div class="market_nav_content " >
                     <a class="market_yx market_hd" href="/admin/market/coupon/manage?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/ptyhq.png" >
                         </div>
                         <div class="market_text" >
                             <p>普通优惠券</p>
                             <span>向用户发放优惠券</span>
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="定向发券" val="coupon_grant" href="/admin/market/grant/list?top_index=4" link="/admin/market/grant/list?top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/dxfq.png" >
                         </div>
                         <div class="market_text">
                             <p>定向发券</p>
                             <span>向指定用户发放优惠券</span>
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="优惠券礼包" val="coupon_package" href="/admin/market/couponpackage/list?nav=1&top_index=4" link="/admin/market/couponpackage/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/icon_coupon_bag.png" >
                         </div>
                         <div class="market_text">
                             <p>优惠券礼包</p>
                             <span>用户一次获得多张优惠券</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="积分商品" val="integral_goods" href="/admin/market/integral/convert/list?nav=1&top_index=4" link="/admin/market/integral/convert/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/jfdh.png" >
                         </div>
                         <div class="market_text">
                             <p>积分兑换</p>
                             <span>使用积分兑换商品</span>
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="满折满减" val="full_cut" href="/admin/market/fullcut/list?nav=1&top_index=4" link="/admin/market/fullcut/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/mzmj.png" >
                         </div>
                         <div class="market_text">
                             <p>满折满减</p>
                             <span>购物满一定金额享受一定优惠</span>
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="支付有礼" val="pay_reward" href="/admin/market/payreward/list?nav=1&top_index=4" link="/admin/market/payreward/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/zfyl.png" >
                         </div>
                         <div class="market_text">
                             <p>支付有礼</p>
                             <span>用户付款后引导参与营销互动</span>
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="活动有礼" val="activity_reward" href="/admin/market/activityreward/list?nav=1&top_index=4" link="/admin/market/activityreward/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/hdyl.png" >
                         </div>
                         <div class="market_text">
                             <p>活动有礼</p>
                             <span>用户来到小程序引导参与营销互动</span>
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="限时降价" val="reduce_price" href="/admin/market/reduce/list?nav=1&top_index=4" link="/admin/market/reduce/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/xsjj.png" >
                         </div>
                         <div class="market_text">
                             <p>限时降价</p>
                             <span>设定商品在指定时间内降价促销</span>
                             {{--<img src="/image/admin/new_market/new.png" class="jjsx">--!}
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="加价购" val="purchase_price" href="/admin/market/purchase/list?nav=1&top_index=4" link="/admin/market/purchase/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/jjg.png" >
                         </div>
                         <div class="market_text">
                             <p>加价购</p>
                             <span>购买指定商品满一定金额加价换购</span>
                             {{--<img src="/image/admin/new_market/new.png" class="jjsx">--!}
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="打包一口价" val="package_sale" href="/admin/market/package/list?nav=1&top_index=4" link="/admin/market/package/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/dbykj.png" >
                         </div>
                         <div class="market_text">
                             <p>打包一口价</p>
                             <span>多件商品一口价打包售卖</span>
                             {{--<img src="/image/admin/new_market/new.png" class="jjsx">--!}
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="定金膨胀" val="pre_sale" href="/admin/market/presale/list?nav=1&top_index=4" link="/admin/market/presale/list?nav=1&top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/djpz.png" >
                         </div>
                         <div class="market_text">
                             <p>定金膨胀</p>
                             <span>预售定金翻倍，大促预热利器</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="好友代付" val="insteadpay" href="/admin/market/insteadpay/config" link="/admin/market/insteadpay/config" >
                         <div class="market_img">
                             <img src="/image/admin/instead_pay.png" >
                         </div>
                         <div class="market_text">
                             <p>好友代付</p>
                             <span>用户可以邀请好友代付款</span>
                             {{--<img src="/image/admin/new_market/jqqd.png" class="jjsx">--!}
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd" title="收藏有礼" val="" href="/admin/shop/collect/config?top_index=4" link="/admin/shop/collect/config?top_index=4" >
                         <div class="market_img">
                             <img src="/image/admin/new_market/scyl.png" >
                         </div>
                         <div class="market_text">
                             <p>收藏有礼</p>
                             <span>引导用户收藏小程序，提升留存</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="赠品" val="gift" href="/admin/market/gift/list?nav=1" link="/admin/market/gift/list?nav=1" >
                         <div class="market_img">
                             <img src="/image/admin/new_market/zp.png" >
                         </div>
                         <div class="market_text">
                             <p>赠品</p>
                             <span>通过丰富的赠品策略，向用户发放赠品</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="测评" val="assess" href="/admin/market/assess/list?top_index=4" link="/admin/market/assess/list?top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/cp.png" >
                         </div>
                         <div class="market_text">
                             <p>测评</p>
                             <span>兴趣测评，让你更了解用户</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="满包邮" val="free_ship" href="/admin/market/ship/list?nav=1" link="/admin/market/ship/list?nav=1" >
                         <div class="market_img">
                             <img src="/image/admin/new_market/mby.png" >
                         </div>
                         <div class="market_text">
                             <p>满包邮</p>
                             <span>购物包邮</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd " title="微信好物圈" val="goods_circle" href="/wechat/mini/goodslist?top_index=1" link="/wechat/mini/goodslist?top_index=1" >
                         <div class="market_img">
                             <img src="/image/admin/new_market/goods_circle.png" >
                         </div>
                         <div class="market_text">
                             <p>微信好物圈</p>
                             <span>朋友间的好物推荐</span>
                             <img src="/image/admin/new_market/HOT.png" class="jjsx">
                         </div>
                     </a>
                     <a class="market_yx market_hd marketing_type" title="评价有礼" val="comment_gift" href="/admin/market/comment/gift/list?nav=0" link="/admin/market/comment/gift/list?nav=0" >
                         <div class="market_img">
                             <img src="/image/admin/new_market/pjyl.png" >
                         </div>
                         <div class="market_text">
                             <p>评价有礼</p>
                             <span>引导用户评价商品，参与营销活动</span>
                             <img src="/image/admin/new_market/new.png" class="jjsx">
                         </div>
                     </a>
                 </div>
             </div>

             <div class="market_nav market_nav_new">
                 <div class="market_nav_title">
                     <span class="title_one">疯狂吸粉</span>
                     <span class="title_two">大量流量带来更多购买</span>
                 </div>
                 <div class="market_nav_content " >
                     <a class="market_yx market_xf marketing_type" title="秒杀" val="seckill_goods" href="/admin/market/seckill/list?nav=1" link="/admin/market/seckill/list?nav=1" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/ms.png" >
                         </div>
                         <div class="market_text">
                             <p>秒杀</p>
                             <span>快速抢购引导用户更多购买</span>
                             {{--<img src="/image/admin/new_market/jjsx.png" class="jjsx">--!}
                         </div>
                     </a>
                     <a class="market_yx market_xf">
                         <div class="market_img">
                             <img src="/image/admin/new_market/zb.png" >
                         </div>
                         <div class="market_text">
                             <p>直播</p>
                             <span>实时与用户进行互动引导更多购买</span>
                             <img src="/image/admin/new_market/jqqd.png" class="jjsx">
                         </div>
                     </a>
                 </div>
         </div>

             <div class="market_nav market_nav_new">
                 <div class="market_nav_title">
                     <span class="title_one">常用工具</span>
                     <span class="title_two">收集信息、发送通知</span>
                 </div>
                 <div class="market_nav_content " >
                     <a class="market_yx market_gj marketing_type" title="表单统计" val="form_decoration" href="/admin/market/form/list?top_index=4" link="/admin/market/form/list?top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/bdtj.png" >
                         </div>
                         <div class="market_text">
                             <p>表单统计</p>
                             <span>收集用户信息</span>
                         </div>
                     </a>
                     <a class="market_yx market_gj marketing_type" title="消息推送" val="message_template" href="/admin/market/message/template/list?top_index=4" link="/admin/market/message/template/list?top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/xxts.png" >
                         </div>
                         <div class="market_text">
                             <p>消息推送</p>
                             <span>向用户发送微信消息通知</span>
                         </div>
                     </a>
                     <a class="market_yx market_gj" title="渠道页面分析" val="" href="/admin/market/channel/list?top_index=4" link="/admin/market/channel/list?top_index=4" target="_blank">
                         <div class="market_img">
                             <img src="/image/admin/new_market/qdymfx.png" >
                         </div>
                         <div class="market_text">
                             <p>渠道页面分析</p>
                             <span>分析不同渠道页面数据</span>
                         </div>
                     </a>
                 </div>
             </div>
    </div>
</div>

<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('.marketing_type').click(function(){
       var _html = $(this).attr('title');
       var mod = $(this).attr('val');
       var url = $(this).attr("link");
       var data = {};
       $(this).attr('href','##');
       $(this).removeAttr('target');
       data = getPowerInfo('main_config',mod,'sub_4',_html);
        if(data.content == 1){
            $(this).attr('href',url);
            $(this).attr('target','_blank');
        }
    });
</script>
