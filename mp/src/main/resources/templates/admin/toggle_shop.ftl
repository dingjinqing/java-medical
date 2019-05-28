<#include "/admin/toggle_header.ftl">
<style type="text/css">
    body{
        position: relative;
    }
    .shop-li{
        margin-bottom: 20px;
        position: relative;
    }
    .shop-li p{
        font-size: 14px;
        color: #666;
        margin: 10px 0px;
    }
    .shop-state {
        height: 40px;
        background-color: #fafafa;
        line-height: 40px;
        color: #999;
        font-size: 14px;
    }
    .sel-top-img {
        margin-top: -4px;
    }

    .no-use-label {
        color: #f7931e;
        /*fcd7ab*/
    }
    .line {
        width: 1px;
        height: 14px;
        background-color: #fcd7ab;

        position: absolute;
        top: 3px;
        left: 61px;
    }
    .shop-logo .shop_img_default{
        -webkit-border-radius: 100%;
        -moz-border-radius: 100%;
        border-radius: 100%;
    }
    .no-shop{
        text-align: center;
        padding: 6% 0;
    }
    .no-shop p{
        margin: 10px 0 0 0;
    }
    .title_type{
        background: #457bf9;
        color: #fff;
        font-size: 14px;
        width: 54px;
        height: 20px;
        line-height: 20px;
        text-align: center;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
        cursor: pointer;
        display: inline-block;
    }
</style>
<style type="text/css">
    .tz_message{
        width: 600px;
        height: 300px;
        background-color: white;
        display: flex;
        align-items: center;
        flex-direction: column;
    }
    .tz_lb{
        width: 100px;
        height: 100px;
        text-align: center;
        position: relative;
    }
    .tz_lb>img{
        position: relative;
        top: 10px;
    }
    .tz_header{
        width: 350px;
        height: 50px;
        line-height: 50px;
        text-align: center;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
    }
    .tz_header>span{
        color: #333;
        font-size: 16px;
    }
    .tz_text{
        width: 500px;
        height: 50px;
    }
    .tz_text>span{
        color: #999;
        font-size: 14px;
    }
    .tz_watch{
        width: 100%;
        height: 50px;
        text-align: center;
        line-height: 50px;
        margin-top: 25px;
    }
    .tz_watch>a{
        text-decoration: none;
        font-size: 14px;
        color: #72ACE3;
    }
    .tz_watch>a:hover{
        text-decoration: underline;
    }
    .tz_watch>a:visited{
        color: #72ACE3;
    }
    /*导航栏*/
    .left_suspension{
        position: absolute;
        position: fixed;
        width:50px;
        height: 75px;
        /*top:500px;*/
        z-index: 9999;
        bottom: 10px;
        display: block !important;
    }
    .suspension{
        width: 40px;
        height: 40px;
        margin-bottom: 2px;
        background-color: #515768;
        border-radius: 0 4px 4px 0;
        -moz-border-radius:0 4px 4px 0;
        -webkit-border-radius: 0 4px 4px 0;
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
        position: relative;
    }
    .suspension:hover{
        background-color: #7c818d;
    }
    .suspension_message{
        width: 60px;
        height: 30px;
        background-color: white;
        position: absolute;
        left: 44px;
        color:#5A8BFF;
        line-height: 26px;
        font-size: 12px;
        text-align: left;
        padding-left: 5px;
        border-radius: 2px;
        border:1px solid #eee;
        display: none;
        top: 4px;
    }
    .sm1{
        width: 150px;
    }
    .suspension_message>span>a{
        color: #5A8BFF;
        padding-left: 0px;
        line-height: 26px;
    }
    .suspension_message>span>a:hover{
        color: #5A8BFF;
        text-decoration: underline !important;
        padding-left: 0px;
        line-height: 26px;
    }
    .suspension_message>span>a:active{
        color: #5A8BFF;
        padding-left: 0px;
        line-height: 26px;
    }
    .suspension_message>img{
        position: absolute;
        left: -4px;
        top:8px;
        transform: rotate(0);
    }
</style>
    <div class="shop-main">
        <div class="main-top">
            <div class="main-sel">

                全部店铺<img src="http://${image_domain!}/image/admin/expand.png" style="margin-left: 5px;">
            </div>
            <div class="main-ul">
                  <#if data_list??>
                    <ul class="clearfix">
                        <#list data_list  as item>
                    <li class="shop-li" is_enabled="${item.is_enabled!0}" business_state="${item.business_state!0}" expire_time_status="${item.expire_time_status!}">
                        <a <#if ("${item.is_enabled!0}"=="0")> href="/admin/account/shop/switch?shop_id=${item.shop_id!}" <#else> style="cursor: default;" </#if>>
                            <div class="shop-state" style="<#if (item.business_state??  && item.business_state == 1 && item.expire_time_status == 0 &&  item.is_enabled == 0)> background: #fff;<#else> background:#fef4e8;</#if>">
                                <#if (item.is_enabled == 1)>
                                    <img src="http://${image_domain!}/image/admin/no_use.png" alt="" class="sel-top-img">
                                    <span class="no-use-label">已禁用</span>
                                <#elseif ("${item.business_state!0}" != "1")>
                                        <img src="http://${image_domain!}/image/admin/no_business.png" alt="" class="sel-top-img" >
                                        <span class="no-use-label">未营业</span>
                                <#elseif (item.expire_time_status == 1)>
                                        <img src="http://${image_domain!}/image/admin/no_time.png" alt="" class="sel-top-img" >
                                    <span>已过期</span>
                                </#if>
                            </div>
                            <div class="shop-logo">
                                <#if ("${item.shop_avatar!}" !="")>
                                <img src="${item.shop_avatar!}" onerror="javascript:this.src='http://${image_domain!}/image/admin/shop_logo_default.png';" width="60px" class="shop_img_default" />
                                <#else>
                                <img src="http://${image_domain!}/image/admin/shop_logo_default.png" class="shop_img_default" />
                                </#if>
                            </div>
                            <div>${item.shop_name!}</div>
                            <p>有效期：${item.created?string["yyyy-MM-dd"]} ~ <#if item.expire_time??>${item.expire_time?string["yyyy-MM-dd"]}</#if></p>
                            <span class="title_type"><#if (version_array[item.shop_type]??)>${version_array[item.shop_type]!} <#else> 无版本 </#if></span>
                        </a>
                    </li>
                    </#list>
                    </ul>
                <#else>
                    <div class="no-shop">
                        <img src="http://${image_domain!}/image/admin/no_shop.png" alt="">
                        <p>暂无店铺</p>
                    </div>
                </#if>
            </div>
            <div class="mian-ad"><a href="##"><img src="http://${image_domain!}/image/admin/ad_img.png" alt="" /></a></div>
        </div>
    </div>
</body>

<#if article??>
<div id="lay" style="display: none">
    <div class="tz_message" style="align-items: center">
        <div class="tz_lb"><img src="/image/admin/notice_lb.png"></div>
        <div class="tz_header"><span>${article.title!}</span></div>
        <div class="tz_text"><span>${article.desc!}</span></div>
        <div class="tz_watch"><a href="/admin/notice/show?article_id=${article.article_id!}" target="_blank">前往查看 ></a></div>
    </div>
</div>
</#if>

<div class="left_suspension">
    <div class="suspension">
        <img src="/image/admin/left_menu_phone.png">
        <div class="suspension_message sm1">
            <img src="/image/admin/left_menu_jt.png">
            客服电话：400-010-1039
        </div>
    </div>
    <div class="suspension suspension_click1">
        <img src="/image/admin/left_menu_zx.png">
        <div class="suspension_message">
            <img src="/image/admin/left_menu_jt.png">
            <span><a href="tencent://message/?uin=3003715029&Site=&Menu=yes">在线咨询</a></span>
        </div>
    </div>

</div>
<script>
var is_article = "${is_article!}";
 var message = "${message!0}";
</script>


<#noparse>

<script>
    if(message != 0){
        util.mobile_alert(message);
    }
    $(function () {
        if($('.main-ul li').length === 0){
            layer.msg('暂无可选店铺', {time: 2000});
            setTimeout(() => {
                window.location.href = 'http://' + document.domain + '/admin/login';
            }, 2000);
        }
        
        console.log(is_article)
        if(is_article == 1){
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 1
                    , title: ['消息通知', 'text-align:center;padding: 0px;']
                    , offset: 'auto'
                    , area: ['600px',"350px"]
                    , content: $("#lay") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    // , btn: ['确定', '取消']
                    // , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    // ,yes: function (index, layero) {
                    //
                    // }, btn2: function (index, layero) {
                    //     //按钮取消的回调
                    // }
                });
            });
        }
    })
    $(".suspension").mouseover(function () {
        $(this).find(".suspension_message").css("display","block");
    });
    $(".suspension").mouseleave(function () {
        $(this).find(".suspension_message").css("display","none");
    });

    $(".suspension_click1").click(function () {
        window.location.href="tencent://message/?uin=3003715029&Site=&Menu=yes";
    });

</script>
</#noparse>

</html>