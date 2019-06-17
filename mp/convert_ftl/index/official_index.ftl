<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>店+小程序-微信小程序开发  一站式行业解决方案服务商</title>
    <meta charset="utf-8">
    <meta http-equiv="content-Type"content="text/html;charset=UTF-8">
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
</head>

<#include ("index.official_header")
<div class="banner_item">
    <div id="myCarousel" class="carousel slide">
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
        </ol>
        <div class="carousel-inner">
            <div class="item item1 active">
                <a href="/index/free/experience" target="_blank">
                    <img src="http://${image_domain!}/image/admin/official/banner1.jpg?v=1.1" alt="First slide" />
                </a>
            </div>
            <div class="item item2">
                <a href="/index/free/experience" target="_blank">
                    <img src="http://${image_domain!}/image/admin/official/banner3.jpg?v=1.1" alt="Second slide" />
                </a>
            </div>
        </div>
        <!--<a class="carousel-control left" href="#myCarousel"
            data-slide="prev">&lsaquo;
        </a>
        <a class="carousel-control right" href="#myCarousel"
            data-slide="next">&rsaquo;
        </a>-->
    </div>
</div>
<div class="pro_highlights solution">
    <div class="light_head">
        <h4><span>多样营销功能应用</span></h4>
        <div>丰富的营销工具，多样营销，助力畅享千亿级流量</div>
    </div>
    <ul class="light_four clearfix">
        <li class=" ">
            <img src="http://${image_domain!}/image/admin/official/marketing1.png" />
            <div>预约服务</div>
            <p>多种预约模式，帮您提高顾客体验，自动化解决顾客预约难、等位久的问题</p>
        </li>
        <li class=" ">
            <img src="http://${image_domain!}/image/admin/official/marketing2.png" />
            <div>砍价活动</div>
            <p>高扩散、低价让利消费者，从而宣传品牌、提高人气、大量引流，提高转化率</p>
        </li>
        <li class=" ">
            <img src="http://${image_domain!}/image/admin/official/marketing3.png" />
            <div>分销模式</div>
            <p>一键分享，邀请下级，成单分佣。海量用户转发引流量，提高店铺曝光</p>
        </li>
        <li class=" ">
            <img src="http://${image_domain!}/image/admin/official/marketing4.png" />
            <div>拼团活动</div>
            <p>低价限时多人团购，快速下单，海量分享。助您累积会员、走量促销，成交更轻松</p>
        </li>
    </ul>
</div>
<div class="advan">
    <div class="light_head">
        <h4><span style="color: #fff;">小程序核心功能优势</span></h4>
        <div>众多核心功能，一键应用，提前布局微信新生态，抢占红利</div>
    </div>
    <div class="advan_con carousel slide" id="myAdvan" data-pause="hover">
        <ul class="advan_con_ul carousel-indicators">
            <li data-target="#myAdvan" data-slide-to="0" class="active">营销活动</li>
            <li data-target="#myAdvan" data-slide-to="1">会员管理</li>
            <li data-target="#myAdvan" data-slide-to="2">门店管理</li>
            <li data-target="#myAdvan" data-slide-to="3">模板装修</li>
            <li data-target="#myAdvan" data-slide-to="4">数据统计</li>
            <li data-target="#myAdvan" data-slide-to="5">在线交易</li>
        </ul>
        <div class="advan_div carousel-inner">
            <div class="advan_div_list item active clearfix">
                <div class="advan_li_left">
                    <img src="http://${image_domain!}/image/admin/official/advan_1.png?v=1" alt="" />
                </div>
                <div class="advan_li_right">
                    <span></span>
                    <h2>营销活动</h2>
                    <p>
                        分裂优惠券：下单获得优惠券，分享到群、多人领取享优惠
                    </p>
                    <p>
                        表单统计：问卷调查、简单预约信息收集，获取顾客喜好
                    </p>
                    <p>
                        满折满减：实现刺激消费、促进消费、走量促销
                    </p>
                    <p>
                        消息模板：自定义活动消息模板，定向选择顾客发送小程序消息信息
                    </p>
                    <p style="width: 500px;">
                        更包含：拼团、砍价、秒杀、分销、抽奖、优惠券、积分商城、活动
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;有礼、支付有礼等丰富营销活动
                    <a href="/index/free/experience" target="_blank">立即使用</a>
                </div>
            </div>
            <div class="advan_div_list item clearfix">
                <div class="advan_li_left">
                    <img src="http://${image_domain!}/image/admin/official/advan_2.png?v=1" alt="" />
                </div>
                <div class="advan_li_right">
                    <span></span>
                    <h2>会员管理</h2>
                    <p>
                        会员等级、会员积分、会员余额、会员卡、限次卡（规定使用次数的卡）
                    </p>
                    <p>
                        为会员提供精准的营销活动
                    </p>
                    <p>
                        线上线下的会员打通，全时全地的为用户提供持续服务
                    </p>
                    <a href="/index/free/experience" target="_blank">立即使用</a>
                </div>
            </div>
            <div class="advan_div_list item clearfix">
                <div class="advan_li_left">
                    <img src="http://${image_domain!}/image/admin/official/advan_3.png?v=1" alt="" />
                </div>
                <div class="advan_li_right">
                    <span></span>
                    <h2>门店管理</h2>
                    <p>
                        支持多门店连锁经营，增加门店曝光率
                    </p>
                    <p>
                        门店细分获客、店员分销，降低门店获客成本
                    </p>
                    <p>
                        门店预约服务单独设定，提高顾客体验，降低管理成本
                    </p>
                    <a href="/index/free/experience" target="_blank">立即使用</a>
                </div>
            </div>
            <div class="advan_div_list item clearfix">
                <div class="advan_li_left">
                    <img src="http://${image_domain!}/image/admin/official/advan_4.png?v=1" alt="" />
                </div>
                <div class="advan_li_right">
                    <span></span>
                    <h2>模板装修</h2>
                    <p>
                        多种行业模板按需选择使用，模块拖拽即可完成装修
                    </p>
                    <p>
                        导航菜单灵活设置。无需代码编辑
                    </p>
                    <p>
                        图片广告、会员卡、优惠券、地图定位板块，一键添加展示
                    </p>
                    <a href="/index/free/experience" target="_blank">立即使用</a>
                </div>
            </div>
            <div class="advan_div_list item clearfix">
                <div class="advan_li_left">
                    <img src="http://${image_domain!}/image/admin/official/advan_5.png?v=1" alt="" />
                </div>
                <div class="advan_li_right">
                    <span></span>
                    <h2>数据统计</h2>
                    <p>
                        销量、流量、转化率全面统计
                    </p>
                    <p>
                        流量页面来源，精准统计
                    </p>
                    <p>
                        用户画像形象展示，为您精准分析提供依据
                    </p>
                    <a href="/index/free/experience" target="_blank">立即使用</a>
                </div>
            </div>
            <div class="advan_div_list item clearfix">
                <div class="advan_li_left">
                    <img src="http://${image_domain!}/image/admin/official/advan_6.png?v=1" alt="" />
                </div>
                <div class="advan_li_right">
                    <span></span>
                    <h2>在线交易</h2>
                    <p>
                        商品展示、在线下单、在线微信支付、余额支付、积分抵现
                    </p>
                    <p>
                        完整线上交易流程；门店网店一键打通，降低运营成本，提升用户体验
                    </p>
                    <a href="/index/free/experience" target="_blank">立即使用</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="pro_highlights cover_all">
    <div class="light_head">
        <h4><span>覆盖多行业</span></h4>
        <div>更加垂直的行业解决方案，满足广泛的业务需求，服务大众</div>
    </div>
    <ul class="light_four">
        <li><img val="1" src="http://${image_domain!}/image/admin/official/cover1.png?v=1" /></li>
        <li><img val="2" src="http://${image_domain!}/image/admin/official/cover2.png?v=1" /></li>
        <li><img val="3" src="http://${image_domain!}/image/admin/official/cover3.png?v=1" /></li>
        <li><img val="4" src="http://${image_domain!}/image/admin/official/cover4.png?v=1" /></li>
        <li><img val="5" src="http://${image_domain!}/image/admin/official/cover5.png?v=1" /></li>
    </ul>
    <ul class="light_four">
        <li><img val="6" src="http://${image_domain!}/image/admin/official/cover6.png?v=1" /></li>
        <li><img val="7" src="http://${image_domain!}/image/admin/official/cover7.png?v=1" /></li>
        <li><img val="8" src="http://${image_domain!}/image/admin/official/cover8.png?v=1" /></li>
        <li><img val="9" src="http://${image_domain!}/image/admin/official/cover9.png?v=1" /></li>
        <li><img val="10" src="http://${image_domain!}/image/admin/official/cover10.png?v=1" /></li>
    </ul>
</div>
<div class="pro_highlights ten_enter">
    <div class="light_head">
        <h4><span>小程序十大流量入口</span></h4>
        <div>众多的流量入口增加了消费者与品牌的黏性，共享微信全生态</div>
    </div>
    <div class="flow_img">
        <img src="http://${image_domain!}/image/admin/official/flow_enter1.png" class="flow_enter1" />
        <img src="http://${image_domain!}/image/admin/official/flow_enter4.png" class="flow_enter2" />
    </div>
</div>
<div class="pro_highlights">
    <div class="light_head">
        <h4><span>行业案例</span></h4>
        <div>用创造力驱动产品，共赢商机、携手共进</div>
    </div>
    <ul class="case_ul clearfix">
        <li>
            <img src="http://${image_domain!}/image/admin/official/industry1.png" alt="" class="case_img" />
            <div>品牌小程序</div>
            <p>
                平台一体化  数据多维度分析 各行各业全面兼容
            </p>
            <div class="case_hover">
                <img src="http://${image_domain!}/image/admin/official/code1.png" style="display: block" />
                <div>品牌小程序</div>
            </div>
        </li>
        <li>
            <img src="http://${image_domain!}/image/admin/official/industry2.png" alt="" class="case_img" />
            <div>婚纱摄影</div>
            <p>
                样片展示 报价获取 优惠折扣 晒单评论 品牌推广
            </p>
            <div class="case_hover">
                <img src="http://${image_domain!}/image/admin/official/code2.png" alt="" />
                <div>婚纱摄影</div>
            </div>
        </li>
        <li>
            <img src="http://${image_domain!}/image/admin/official/industry3.png?v=1" alt="" class="case_img" />
            <div>服装</div>
            <p>
                线上展示  砍价拼团  折扣优惠   会员管理  可视化装修
            </p>
            <div class="case_hover">
                <img src="http://${image_domain!}/image/admin/official/code3.png" alt="" />
                <div>服装</div>
            </div>
        </li>
        <li>
            <img src="http://${image_domain!}/image/admin/official/industry4.png?v=1" alt="" class="case_img" />
            <div>数码</div>
            <p>
                活动多样  商品展示  限时抢购   秒杀抽奖  在线交易
            </p>
            <div class="case_hover">
                <img src="http://${image_domain!}/image/admin/official/code4.png" alt="" />
                <div>数码</div>
            </div>
        </li>
        <li>
            <img src="http://${image_domain!}/image/admin/official/industry5.png" alt="" class="case_img" />
            <div>新美业</div>
            <p>
                在线预约 门店买单 技师管理 会员营销 活动推广
            </p>
            <div class="case_hover">
                <img src="http://${image_domain!}/image/admin/official/code5.png" alt="" />
                <div>新美业</div>
            </div>
        </li>
    </ul>
</div>
<div class="pro_highlights ten_enter">
    <div class="light_head">
        <h4><span>新闻资讯</span></h4>
        <div>洞察行业新动态，把握行业新方向</div>
    </div>
    <ul class="clearfix news_ul">
        <li>
            <div><img src="http://${image_domain!}/image/admin/official/news1.png" /></div>
            <#list (array2object(array_slice(object2array($news),0,3)) as $item )
                <a href="/index/article/detail/${item->article_id!}.html" class="clearfix" target="_blank">
                    <span>${item->pub_month_day!}</span>
                    <span>${item->title!}</span>
                    <#if ($item->is_recommend == 1)
                        <span style="float: right;">推荐</span>
                    </#if>
                </a>
            </#list>

        </li>
        <li>
            <div><img src="http://${image_domain!}/image/admin/official/news2.png" /></div>
            <#list (array2object(array_slice(object2array($news),3,3)) as $item )
                <a href="/index/article/detail/${item->article_id!}.html" class="clearfix" target="_blank">
                    <span>${item->pub_month_day!}</span>
                    <span>${item->title!}</span>
                    <#if ($item->is_recommend == 1)
                        <span style="float: right;">推荐</span>
                    </#if>
                </a>
            </#list>

        </li>
        <li>
            <div><img src="http://${image_domain!}/image/admin/official/news3.png" /></div>
            <#list (array2object(array_slice(object2array($news),6,3)) as $item )
                <a href="/index/article/detail/${item->article_id!}.html" class="clearfix" target="_blank">
                    <span>${item->pub_month_day!}</span>
                    <span>${item->title!}</span>
                    <#if ($item->is_recommend == 1)
                        <span style="float: right;">推荐</span>
                    </#if>
                </a>
            </#list>

        </li>
    </ul>
    <div class="news_div">
        <a href="/index/article/list" target="_blank">查看更多</a>
    </div>
</div>

<#include ("index.official_footer")

<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('.nav ul').find('li').eq(0).addClass('active');
    $('.carousel').carousel({
        interval: 3000
    });
    $('.advan_con').carousel('pause');
    $(document).scroll(function () {
        //console.log($(document).scrollTop())
        if($(document).scrollTop() >= 1100){
            $('.advan_con').carousel('cycle');
        }
        if($(document).scrollTop() >= 2550){
            $('.flow_enter1').addClass('flowin');
        }
    });

    $('.solution li').hover(function(){
        $(this).find('img').addClass('shadow_img');
    },function(){
        $(this).find('img').removeClass('shadow_img');
    });

    $('.case_ul li').hover(function(){
        $(this).find('.case_hover').css('left','0');
        $(this).addClass('case_li_hover');
    },function(){
        $(this).find('.case_hover').css('left','300px');
        $(this).removeClass('case_li_hover');
    });

    $('.news_ul').find('img').hover(function () {
        $(this).removeClass('zoomout');
        $(this).addClass('zoomin');
    },function () {
        $(this).removeClass('zoomin');
        $(this).addClass('zoomout');
    });

    var cover_src = 'http://${image_domain!}/image/admin/official/cover';
    $('.cover_all').find('img').hover(function () {
        var val = $(this).attr('val');
        $(this).attr('src',cover_src + '_hover' + val + '.png?v=1');
    },function () {
        var val = $(this).attr('val');
        $(this).attr('src',cover_src + val + '.png?v=1');
    });
</script>