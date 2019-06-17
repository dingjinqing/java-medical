<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>${column!}_${global_title!}${title!}</title>
    <meta charset="utf-8">
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
<body>
<#include ("index.official_header")

<!-- 通栏 -->
<section id="banner">
    <div class="container" style="height: 250px;color:#fff">
        <!--图片文字-->
        <div class="word">
            <h1 style="font-size:40px;margin-top:73px;">关于我们</h1>
            <p  style="font-size:33px;opacity:0.7;margin-top:16px;" class="common-p" >About Us</p>
        </div>
        <!--/图片文字-->
    </div>
</section>
<!-- /通栏 -->
<!-- 关于我们 -->
<section id="about-us">
    <div style="width:100%;">
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="about-introduce">
                <div id="com-introduce">
                    <div class="container">
                        <h2 class="common-h2">公司简介</h2>
                        <ul>
                            <li><h2>10000+</h2>服务店铺体量</li>
                            <li style="background: url('http://${image_domain!}/image/admin/official/introduce/02.png') center center no-repeat;"><h2>100%</h2>连续5年客户增长率</li>
                            <li style="background: url('http://${image_domain!}/image/admin/official/introduce/03.png') center center no-repeat;"><h2>56.16%</h2>皇冠以上用户</li>
                            <li style="background: url('http://${image_domain!}/image/admin/official/introduce/04.png') center center no-repeat;"><h2>91.3%</h2>客户续费率</li>
                            <li style="background: url('http://${image_domain!}/image/admin/official/introduce/01.png') center center no-repeat;"><h2>CMMI3</h2>国际认证</li>
                        </ul>
                        <div>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;北京掌上先机网络科技有限公司，零售云服务提供商，基于云计算SaaS服务模式，以体系化解决方案，助力零售企业数字化智能化管理升级，成就企业规模化发展之路。<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2012年，旺店通成立于北京，之后在天津、上海、广州、杭州、义乌等25个省市设立了分支机构，员工近1000人，服务范围辐射全国并延伸至海外。 凭借技术创新、产品创新、服务创新和市场创新，旺店通实现了每年100%以上的客户增长；10万+客户涵盖了中粮、强生、3M、百威、周黑鸭、MG小象、水密码等世界500强、上市公司、知名品牌、TOP商家……2017全年交易额近万亿。
                            规模化客户及头部客户优势给予了旺店通更高更全的行业视角，市场敏感度及强执行力。
                            旺店通创始团队来自互联网上市公司技术管理层，产品及研发团队占比35%以上，核心成员皆为清华、北大、北邮等985大学研究生，拥有ACM、数学建模等重量级竞赛获奖经历，具有千万用户运营经验。多年来旺店通构建了内外部“互联网、电商、管理”等资源池，“技术流”行业大咖云集。<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扎实的技术根基，使旺店通通过了CMMI3级国际认证、ISO27001信息安全认证、国家级高新技术企业认证、双软认证等多项资质认证，并获得了多项荣誉 ：中国电子商务服务商五十强企业、中国产业创新领域十佳SaaS服务商、电商奥斯卡金麦奖“最佳技术服务奖”、中国国际电商博览会“最佳电商服务企业奖”、阿里巴巴CCO“AG最佳赋能合作伙伴奖”、淘宝金牌淘拍档、京东“京卓越”奖项、苏宁易购“金牌易伙伴”等，现已与天猫、淘宝、京东等80+主流电商、外卖等平台建立了战略合作关系。<br>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未来，旺店通仍将继续秉持以人为本、客户至上、持续创新、产业共赢的发展理念，打造“ERP+”一体化零售企业服务生态。
                        </div>
                    </div>
                </div>
                <div id="com-develop">
                    <div class="container">
                        <h2 class="common-h2">发展历程</h2>
                        <ul class="time-vertical clearfix">
                            <li class="clearfix" style="margin-top:41px;height:150px;">
                                <b></b>
                                <div class="before">
                                    2017
                                </div>
                                <div class="after">
                                    2017年 旺店通旗舰版上线，开创零售信息服务领域SaaS时代<br />
                                    2017年 小程序SaaS平台正式上线，服务覆盖多行业<br />
                                    2017年 员工超过800人，设立25家分支机构，服务范围辐射全国，延伸至海外<br />
                                    2017年 连续5年客户超100%增长，双11订单总量2.89亿单，交易总额402亿，实现5连增
                                </div>
                            </li>
                            <li class="clearfix" style="height:150px;">
                                <b></b>
                                <div class="before">
                                    2016
                                </div>
                                <div class="after">
                                    2016年10月 服务超过100000家线上线下店铺<br />
                                    2016年11月 双11客户总交易额237.7亿，订单量16800万<br />
                                    2016年 E快帮、旺店通WMS、POS门店管理系统上线，服务覆盖大中小商家<br />
                                    2016年 荣获淘宝服务市场金牌淘拍档称号
                                </div>
                            </li>
                            <li class="clearfix" style="height:123px;">
                                <b></b>
                                <div class="before">
                                    2015
                                </div>
                                <div class="after">
                                    2015年 与中粮、强生、好想你、来伊份、君乐宝、云集小也、十月妈咪、RIO锐澳、同仁堂、洽洽等世界500强、上市企业、知名品牌等重量级客户达成合作<br />
                                    2015年11月 双11客户总交易额超过100亿元，单客户最大订单量97万单<br />
                                    2015年 设立15家分支机构，服务遍及全国
                                </div>
                            </li>
                            <li class="clearfix" style="height:128px;">
                                <b></b>
                                <div class="before">
                                    2014
                                </div>
                                <div class="after">
                                    2014年 旺店通ERP企业版、微商城、B2B2C商城等多个产品上线，开始布局电商企业服务领域<br />
                                    2014年 旺店通与周黑鸭达成战略合作，打造订单全渠道解决方案<br />
                                    2014年 荣获中国电子商务协会“中国电子商务百强企业”称号
                                </div>
                            <li class="clearfix" style="height:128px;">
                                <b></b>
                                <div class="before">
                                    2013
                                </div>
                                <div class="after">
                                    2013年7月 全网第一家五金冠商家——朵朵云签约旺店通<br />
                                    2013年 旺店通斩获最佳电商ERP新锐服务商、电商服务明日之星等多项殊荣<br />
                                    2013年 企业成员过百，在上海、广州、杭州设立分支机构
                                </div>
                            </li>
                            <li class="clearfix">
                                <b></b>
                                <div class="before">
                                    2012
                                </div>
                                <div class="after">
                                    2012年11月 SaaS产品旺店通ERP专业版上线<br />
                                    2012年 北京掌上先机网络科技有限公司在北京正式成立
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="com-work">
                    <div class="container">
                        <h2 class="common-h2">办公环境</h2>
                    </div>
                </div>
                <div id="com-contact">
                    <div class="container">
                        <h2 class="common-h2">联系我们</h2>
                        <!--<img src="__STATIC__/index/img/about-us/introduce/07.png" alt="">-->
                        <div class="efficiency">
                            <div>
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:76px;right:390px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:90px;right:423px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:112px;right:336px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:126px;right:390px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:132px;right:502px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:166px;right:429px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:172px;right:352px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:202px;right:373px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:210px;right:318px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:210px;right:590px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:226px;right:481px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:246px;right:326px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:302px;right:346px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:346px;right:436px;">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                            <div style="top:352px;right:392px">
                                <span class="pulse delay-06"></span>
                                <span class="pulse delay-05"></span>
                                <span class="pulse delay-04"></span>
                            </div>
                        </div>
                        <div class="bottom">
                            <span>：北京市海淀区花园路13号天博中润216室</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="honor">
                <div id="company-honor">
                    <div class="container">
                        <h2 class="common-h2">荣誉</h2>
                        <div id="myCarousel" class="carousel slide" data-ride="carousel">
                            <!-- 轮播（Carousel）项目 -->
                            <div class="carousel-inner">
                                <div class="item active">
                                    <div style="background-color:#fff;" >
                                        <ul>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/01.png" ><br><br>
                                                2015<br>中国电子商务金砖奖
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/02.png" ><br><br>
                                                2015<br>电商后台系统类目<br>银牌淘拍档
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/03.png" ><br><br>
                                                2014<br>金马奖<br>
                                                中国电子商务杰出导师
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/04.png" ><br><br>
                                                2016<br> 新网商<br>
                                                年度十佳服务商
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="pic">
                                        <ul>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/01.png" ><br><br>
                                                2015<br>中国电子商务金砖奖
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/02.png" ><br><br>
                                                2015<br>电商后台系统类目<br>银牌淘拍档
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/03.png" ><br><br>
                                                2014<br>金马奖<br>
                                                中国电子商务杰出导师
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/04.png" ><br><br>
                                                2016<br> 新网商<br>
                                                年度十佳服务商
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="item">
                                    <div class="pic">
                                        <ul>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/01.png" ><br><br>
                                                2015<br>中国电子商务金砖奖
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/02.png" ><br><br>
                                                2015<br>电商后台系统类目<br>银牌淘拍档
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/03.png" ><br><br>
                                                2014<br>金马奖<br>
                                                中国电子商务杰出导师
                                            </li>
                                            <li class="pic">
                                                <img src="http://${image_domain!}/image/admin/official/honor/04.png" ><br><br>
                                                2016<br> 新网商<br>
                                                年度十佳服务商
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <!-- 轮播（Carousel）导航 -->
                            <a style="background:transparent;width:60px;" class="carousel-control left" href="#myCarousel" data-slide="prev">
                                <img style="margin-top:198px;margin-left:-12px;" src="http://${image_domain!}/image/admin/official/honor/07.png" alt="">
                            </a>
                            <a style="background:transparent;width:60px;" class="carousel-control right" href="#myCarousel" data-slide="next">
                                <img style="margin-top:198px;margin-left:12px;" src="http://${image_domain!}/image/admin/official/honor/08.png" alt="">
                            </a>
                        </div>
                    </div>
                </div>
                <div id="ho-aptitude">
                    <div class="container">
                        <h2 class="common-h2">资质</h2>
                        <ul>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/09.png" alt="">
                                <div  >
                                    <h4>CMMI3</h4>
                                    <p>
                                        CMMI3是指CMMI三级，称为定义级。在这个水平上，企业不仅能够对项目的实施
                                        有一整套的管理措施，并保障项目的完成；而且，企业能够根据自身的特殊情况以
                                        及自己的标准流程，将这套管理体系与流程予以制度化，这样企业不仅能够在同类
                                        的项目上得到成功的实施，在不同类的项目上一样能够得到成功的实施。
                                    </p>
                                </div>
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/10.png" alt="">
                                <div  >
                                    <h4>ISO</h4>
                                    <p>
                                        ISO（International Organization for Standardization）是一个组织的英语简称，
                                        翻译成中文就是“国际标准化组织”。ISO9000不是指一个标准，而是一族标准的
                                        统称。“ISO9000族标准”指由ISO/TC176制定的所有国际标准。
                                    </p>
                                </div>
                            </li>
                        </ul>
                        <ul>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/11.png" alt="">
                                <div  >
                                    <h4>高新</h4>
                                    <p>
                                        高新技术企业是指在《国家重点支持的高新技术领域》内，持续进行研究开发与技
                                        术成果转化，形成企业核心自主知识产权，并以此为基础开展经营活动，在中国境
                                        内（不包括港、澳、台地区）注册一年以上的居民企业。它是知识密集、技术密集
                                        的经济实体。
                                    </p>
                                </div>
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/12.png" alt="">
                                <div  >
                                    <h4>双软</h4>
                                    <p>
                                        双软认证”是指软件企业的认定和软件产品的登记；企业申请双软认证除了获得
                                        软件企业和软件产品的认证资质，同时也是对企业知识产权的一种保护方式，更可
                                        以让企业享受国家提供给软件行业的税收优惠政策。
                                    </p>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="ho-certificate">
                    <div class="container">
                        <h2 class="common-h2">证书</h2>
                        <div class="wrap" id="wrap">
                            <div class="slide" id="slide">
                                <ul>
                                    <li><a href="#"><img src="http://${image_domain!}/image/admin/official/honor/13.png" alt=""/></a></li>
                                    <li><a href="#"><img src="http://${image_domain!}/image/admin/official/honor/13-1.png" alt=""/></a></li>
                                    <li><a href="#"><img src="http://${image_domain!}/image/admin/official/honor/13-2.png" alt=""/></a></li>
                                </ul>
                                <div class="arrow" id="arrow">
                                    <a href="javascript:;" class="prev"></a>
                                    <a href="javascript:;" class="next"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="ho-copyright">
                    <div class="container">
                        <h2 class="common-h2">软件著作权</h2>
                        <ul>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/14.png" alt="">
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/15.png" alt="">
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/16.png" alt="">
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/17.png" alt="">
                            </li>
                        </ul>
                        <ul>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/18.png" alt="">
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/19.png" alt="">
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/20.png" alt="">
                            </li>
                            <li>
                                <img src="http://${image_domain!}/image/admin/official/honor/21.png" alt="">
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="about-partner">
                <div id="co-partner">
                    <div class="container">
                        <h2 class="common-h2">合作伙伴</h2>
                        <img style="margin-top:60px;" src="http://${image_domain!}/image/admin/official/cooperate/01.png" alt="">
                        <img style="margin-top:33px;" src="http://${image_domain!}/image/admin/official/cooperate/02.png" alt="">
                    </div>
                </div>
                <div id="co-storage">
                    <div class="container">
                        <h2 class="common-h2">仓储</h2>
                        <ul>
                            <li>科捷</li>
                            <li>申通</li>
                            <li>顺丰</li>
                            <li>韵达</li>
                            <li>百世</li>
                        </ul>
                        <ul>
                            <li>心怡</li>
                            <li>通天晓</li>
                            <li>富勒</li>
                            <li>递四方</li>
                            <li>EMS</li>
                        </ul>
                    </div>
                </div>
                <div id="co-logistics">
                    <div class="container">
                        <h2 class="common-h2">支持物流</h2>
                        <img style="margin-top:60px;" src="http://${image_domain!}/image/admin/official/cooperate/05.png" alt="">
                        <img style="margin-top:33px;" src="http://${image_domain!}/image/admin/official/cooperate/06.png" alt="">
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="join">
                <div id="join-us">
                    <div class="container">
                        <h2 class="common-h2">加入我们</h2>
                        <p class="common-p">与有趣、专业并热爱生活的人一起努力</p>
                        <div class="touch-scroll">
                            <ul class="nav nav-pills nav-stacked news-nav">
                                <li role="presentation" class="active">
                                    <a href="#category_01" aria-controls="category_01" role="tab" data-toggle="tab">
                                        <span>查看全部</span>
                                    </a>
                                </li>
                                <li role="presentation">
                                    <a href="#category_02" aria-controls="category_02" role="tab" data-toggle="tab">
                                        <span>技术团队</span>
                                    </a>
                                </li>
                                <li role="presentation">
                                    <a href="#category_03" aria-controls="category_03" role="tab" data-toggle="tab">
                                        <span>销售团队</span>
                                    </a>
                                </li>
                                <li role="presentation">
                                    <a href="#category_04" aria-controls="category_04" role="tab" data-toggle="tab">
                                        <span>产品团队</span>
                                    </a>
                                </li>
                                <!-- <li role="presentation">
                                  <a href="#category_05" aria-controls="category_05" role="tab" data-toggle="tab">
                                    <span>设计团队</span>
                                  </a>
                                </li> -->
                            </ul>
                        </div>
                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div role="tabpanel" style="color:#fff;" class="tab-pane fade active in" id="category_01">
                                <ul>
                                    <li>
                                        <dl>
                                            <dt class="dt-hover">
                                                <span>PHP开发工程师</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/06.png" alt="">
                                            </dt>
                                            <dd style="display: block;">
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、负责全渠道会员管理系统的性能优化，逻辑优化及数据库问题排查。</li>
                                                    <li>2、开发维护会员系统的核心模块如营销模块、数据中心模块，保证系统的稳定性。</li>
                                                    <li>3、参与系统开放接口的开发，第三方平台的对接，微信公众号的开发。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、具有数据库优化设计能力，有过深度的SQL的优化经验，能够编写相对复杂的存储过程，能够处理排查数据库死锁问题。</li>
                                                    <li>2、熟练掌握ThinkPHP框架，至少有过一个上线运行的项目。</li>
                                                    <li>3、良好的算法能力和逻辑思维能力。</li>
                                                    <li>4、有良好的编码习惯。</li>
                                                    <li>5、有开源项目参与经历和高质量博客的优先考虑。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>Web前端工程师</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、负责开发电商管理后台的前端展现部分和商户店铺的移动端展现部分。</li>
                                                    <li>2、配合网站设计师一起优化产品的使用体验。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、熟悉HTML、CSS和Javascript的基本使用，并且有一定的网站开发经验，有移动网站开发经验更佳。</li>
                                                    <li>2、希望您对JQuery、Bootstrap熟悉，最好熟悉一种常用的JS框架(Backbone,Angular等)。</li>
                                                    <li>3、希望您有很好的沟通能力和学习能力，愿意在技术上不断地探索新的领域、学习新的技术。</li>
                                                    <li>4、希望您有一定的产品能力，对网站交互和设计有自己的想法，能够帮助我们一起改进网站的使用体验。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>Java开发工程师</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、明确开发目标, 制定开发计划。</li>
                                                    <li>2、开发PC客户端及相关软件。</li>
                                                    <li>3、开发新功能、修复BUG、及编写相关文档。</li>
                                                    <li>4、有效地进行团队配合，有效地获取和满足需求，有效沟通。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、计算机相关专业优秀毕业生亦可，懂财务知识优先。</li>
                                                    <li>2、熟练掌握java语言，了解java8特性, 熟悉Mysql、Oracle等主流数据库，熟悉SQL语言。</li>
                                                    <li>3、熟练掌握常用数据结构和算法。</li>
                                                    <li>4、熟悉htmlayout或CSS者优先。</li>
                                                    <li>5、获过“蓝桥杯”或“ACM”比赛省级以上奖项者优先考虑。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>产品经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、主要负责电商后台、运营管理、ERP（供应链管理、仓储管理，商品管理、业务报表管理）等系统功能的产品规划。</li>
                                                    <li>2、制定ERP产品的界面原型、功能、流程设计和交互设计，协调研发工作，掌控项目开发参与项目需求分析。</li>
                                                    <li>3、组织、协调与产品研发的相关资源，实现产品的发展目标，满足采购、销售、财务业务需求。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、至少1年电商产品或者SaaS产品相关经验，独立产品负责人优先。</li>
                                                    <li>2、具有较强的沟通能力，逻辑思维能力和文档编写能力。</li>
                                                    <li>3、具备优秀的学习适应能力，高度责任心、抗压性强。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>产品运营</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1．负责门店小程序运营策略的制定，对核心指标负责。</li>
                                                    <li>2．负责管理运营团队，有团队管理经验。</li>
                                                    <li>3．负责优化整个产品流程，提升用户体验以及不断尝试新的运营方式，提升用户粘性与满意度。</li>
                                                    <li>4．负责电商活动的组织和新媒体运营。</li>
                                                    <li>5．负责通过整理数据指标，及时调整和发现运营中的问题，并不断优化。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1．3年以上运营管理经验，对零售行业有浓厚兴趣。</li>
                                                    <li>2．有较强的逻辑思维、数据分析能力和判断力，可以数据驱动、目标驱动地解决问题。</li>
                                                    <li>3．有较强的沟通、协调能力优秀，能拆解任务、分配KPI，有效管理团队。</li>
                                                    <li>4．有电商，用户运营经验者优先。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>客户成功经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1．提供产品咨询服务，对新客户进行培训，确保客户能迅速利用产品获得切实的商业价值。</li>
                                                    <li>2．负责解答客户日常使用问题，辅助客户根据工作内容找到最佳使用方案。</li>
                                                    <li>3．定期客户电话回访，季度见面，及时发现客户使用过程中的问题, 激发用户持续使用产品。</li>
                                                    <li>4．收集客户反馈，与产品团队沟通，对产品优化和改进提出建议。</li>
                                                    <li>5．与销售团队密切配合，提高账户续费率以及二次销售比例。</li>
                                                    <li>6．与市场部门配合，定期做用户故事整理，辅助输出典型用户案例。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1．本科以上学历，计算机相关专业等优先。</li>
                                                    <li>2．2年以上客户管理、销售、售后、技术支持、咨询、实施等任一相关职能方面的工作经历。</li>
                                                    <li>3．对“以客户为出发点”深入理解，对工作认真、细致，责任心强，具有高度的服务意识和团队精神。</li>
                                                    <li>4．有指导和帮助用户在整个团队使用 SaaS 服务的丰富经验，能够为不同类型的用户制定不同的使用策略，以提升用户的使用率。</li>
                                                    <li>5．能够自我激励，为了增加用户满意度和留存而不断尝试新方法。</li>
                                                    <li>6．热情，专业，能同客户建立良好关系，并具较好的沟通、应变及学习能力。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>出纳</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1. 严格按照公司的财务制度规定，办理现金收付业务、各种费用报销业务、银行结算业务，并编制相关凭证；</li>
                                                    <li>2. 完成月末与会计各项收支单据的交接、对账、结账工作。</li>
                                                    <li>3. 妥善保管库存现金、有关印章、空白收据和空白支票。</li>
                                                    <li>4. 认真登记现金日记账和银行存款日记账，并做到日清月结；每日盘点库存现金</li>
                                                    <li>5. 按照相关规定开具发票，并做好发票的统计工作.</li>
                                                    <li>6. 根据工作需要外出办理银行、税务相关事项</li>
                                                    <li>7. 完成上级领导交办的其他任务。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、为人诚实、正直、责任心强、做事细心、良好的职业道德和敬业精神，原则性强； </li>
                                                    <li>2、财会等相关专业本科及以上学历。</li>
                                                    <li>3、熟悉日常现金、银行结算业务和流程；熟悉现金支票管理制度；熟悉固定资产管理业务和流程，熟练使用财务软件和办公软件； </li>
                                                    <li>4、有较强的配合、协调能力和严谨的工作态度； </li>
                                                    <li>5、具有会计人员从业资格证书。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>电话销售</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 西安、北京、天津
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、负责搜集新客户的资料并进行沟通，开发新客户;</li>
                                                    <li>2、通过电话与客户进行有效沟通了解客户需求, 寻找销售机会并完成销售业绩;</li>
                                                    <li>3、维护老客户的业务，挖掘客户的最大潜力;</li>
                                                    <li>4、定期与合作客户进行沟通，建立良好的长期合作关系。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、口齿清晰，普通话流利，语音富有感染力; </li>
                                                    <li>2、对销售工作有较高的热情;充足的自信心</li>
                                                    <li>3、具备较强的学习能力和优秀的沟通能力; </li>
                                                    <li>4、性格坚韧，思维敏捷，具备良好的应变能力和承压能力; </li>
                                                    <li>5、有敏锐的市场洞察力，有强烈的事业心、责任心和积极的工作态度。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>商务合作经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt="">北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1. 扩展平台、平行企业、电子商务商会、协会等的合作资源;</li>
                                                    <li>2. 负责异业类的合作洽谈及相关商务渠道的拓展、资源置换和联合营销；</li>
                                                    <li>3. 策划合作内容，合作项目进行跟踪，完成相关项目的谈判和执行；</li>
                                                    <li>4. 善于整合各种资源，制定合作实施资料计划，开拓深度合作；</li>
                                                    <li>5.了解竞品公司的资源合作及投放模式，提出新的资源拓展方案；</li>
                                                    <li>6.评估展会，并推进展会的合作事宜并执行；</li>
                                                    <li>7.完成上级领导安排的其他工作。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1．多渠道引入优质合作资源，推进合作进程，并对资源效果进行把控负责</li>
                                                    <li>2．责任心强，工作主动；</li>
                                                    <li>3. 有较强的执行的能力。</li>
                                                    <li>4. 推进各项资源合作进程及对接工作</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>活动策划经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt="">北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、负责大中小型活动策划及执行，洽谈与活动相关的合作，增加获客线索，协助销售达成业绩；</li>
                                                    <li>2、活动方案的内部宣导，扩大活动影响力；</li>
                                                    <li>3、解决活动推进中的各类问题，把控活动的关键节点，并进行优化；</li>
                                                    <li>4、活动结束形成有建设性意见的总结报告，不同阶段提出建设性方案及建议；</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、  有相关的从业经验，本科以上学历，可接受出差；</li>
                                                    <li>2、  沟通能力强，能够很好的把控现场，关注细节；</li>
                                                    <li>3、  对数据敏感，有较强的分析问题的能力，良好的PPT汇报能力；</li>
                                                    <li>4、  责任心强，有较强的迅速执行力，工作主动；</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>公关策划经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt="">北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1. 参与公司及项下各品牌建设的规划及执行；</li>
                                                    <li>2. 构建公司各产品日常传播规划及各品牌所需公共关系资源； </li>
                                                    <li>3. 研究媒体趋势和环境，制定品牌媒介策略；</li>
                                                    <li>4. 协助业务团队完成公司公关传播工作</li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1.3年以上企业服务、电商行业公关工作经验，全日制本科及以上学历；</li>
                                                    <li>2.有较好的财经媒体资源、电商媒体资源；</li>
                                                    <li>3.熟悉公关活动流程，具有优秀的公关活动项目策划及执行能力，文笔好；</li>
                                                    <li>4.优秀的语言表达能力，较强的组织协调和策划能力，思维敏捷，善于创新，具有百折不挠的团队精神；</li>
                                                    <li>5.具备项目推动、跨部门协调、自我驱动能力。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>SEO外链专员</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt="">北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、负责网站外部推广，免费网络渠道推广，友情链接的交换。 </li>
                                                    <li>2、制定seo阶段性计划，负责网站pv/uv等综合指标，提高收录和排名。 </li>
                                                    <li>3、协助部门进行网站优化，推广方面的工作。 </li>
                                                    <li>4、有良好的表达能力和解决问题能力。 </li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、一年以上网络推广方向，了解seo相关知识（搜索引擎，百度快照、pr等,熟悉各种外链建设方向和技巧。</li>
                                                    <li>2、熟悉百度知道问答、空间，博客，微博，知乎等论坛营销及软文推广，分类等信息推广。 </li>
                                                    <li>3、具有良好的的策划分析能力，有seo外部资源，经验丰富从业者优先。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>项目实施经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt="">全国
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、负责整个项目实施过程，有效确认项目实施范围、成本控制与风险控制； </li>
                                                    <li>2、制定实施计划，把控项目进度及验收；/uv等综合指标，提高收录和排名。 </li>
                                                    <li>3、管理整个实施团队，负责实施团队的建设、培养、评估、考核、激励； </li>
                                                    <li>4、负责实施团队的日常管理运营，实施案例整理及成果提炼，新员工内训及知识库的建设。 </li>
                                                </ul>
                                                <ul>
                                                    <li>任职要求</li>
                                                    <li>1、大专（含）以上学历，计算机、企业管理、信息管理等相关专业者优先；,熟悉各种外链建设方向和技巧。</li>
                                                    <li>2、5年以上ERP软件实施工作经验，3年以上项目管理经验； </li>
                                                    <li>3、有PMP证书者优先，有过相关行业工作经验者优先；</li>
                                                    <li>4、具备较强的团队组织、协调能力，具有较好的文案编写能力，具备较强的与客户内部组织及关键角色的沟通协作能力。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                </ul>
                            </div>
                            <div role="tabpanel" style="color:yellow;" class="tab-pane fade" id="category_02">
                                <ul>
                                    <li>
                                        <dl>
                                            <dt class="dt-hover">
                                                <span>PHP开发工程师</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/06.png" alt="">
                                            </dt>
                                            <dd style="display: block;">
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1．负责全渠道会员管理系统的性能优化，逻辑优化及数据库问题排查。</li>
                                                    <li>2．开发维护会员系统的核心模块如营销模块、数据中心模块，保证系统的稳定性。</li>
                                                    <li>3．参与系统开放接口的开发，第三方平台的对接，微信公众号的开发。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职资格</li>
                                                    <li>1．具有数据库优化设计能力，有过深度的SQL的优化经验，能够编写相对复杂的存储过程，能够处理排查数据库死锁问题。</li>
                                                    <li>2．熟练掌握ThinkPHP框架，至少有过一个上线运行的项目。</li>
                                                    <li>3．良好的算法能力和逻辑思维能力。</li>
                                                    <li>4．有良好的编码习惯。</li>
                                                    <li>5．有开源项目参与经历和高质量博客的优先考虑。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>Web前端工程师</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 100人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1．负责开发电商管理后台的前端展现部分和商户店铺的移动端展现部分。</li>
                                                    <li>2．配合网站设计师一起优化产品的使用体验。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职资格</li>
                                                    <li>1．熟悉HTML、CSS和Javascript的基本使用，并且有一定的网站开发经验，有移动网站开发经验更佳。</li>
                                                    <li>2．希望您对JQuery、Bootstrap熟悉，最好熟悉一种常用的JS框架(Backbone,Angular等)。</li>
                                                    <li>3．希望您有很好的沟通能力和学习能力，愿意在技术上不断地探索新的领域、学习新的技术。</li>
                                                    <li>4．希望您有一定的产品能力，对网站交互和设计有自己的想法，能够帮助我们一起改进网站的使用体验。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>Java开发工程师</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 100人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1. 明确开发目标, 制定开发计划。</li>
                                                    <li>2．开发PC客户端及相关软件。</li>
                                                    <li>3．开发新功能、修复BUG、及编写相关文档。</li>
                                                    <li>4．有效地进行团队配合，有效地获取和满足需求，有效沟通。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职资格</li>
                                                    <li>1．计算机相关专业优秀毕业生亦可，懂财务知识优先。</li>
                                                    <li>2．熟练掌握java语言，了解java8特性, 熟悉Mysql、Oracle等主流数据库，熟悉SQL语言。</li>
                                                    <li>3．熟练掌握常用数据结构和算法。</li>
                                                    <li>4．熟悉htmlayout或CSS者优先。</li>
                                                    <li>5．获过“蓝桥杯”或“ACM”比赛省级以上奖项者优先考虑。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                </ul>
                            </div>
                            <div role="tabpanel" style="color:yellow;" class="tab-pane fade" id="category_03">
                                <ul>
                                    <li>
                                        <dl>
                                            <dt class="dt-hover">
                                                <span>电话销售</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 西安、北京、天津
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/06.png" alt="">
                                            </dt>
                                            <dd style="display: block;">
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1、负责搜集新客户的资料并进行沟通，开发新客户;</li>
                                                    <li>2、通过电话与客户进行有效沟通了解客户需求, 寻找销售机会并完成销售业绩;</li>
                                                    <li>3、维护老客户的业务，挖掘客户的最大潜力;</li>
                                                    <li>4、定期与合作客户进行沟通，建立良好的长期合作关系。;</li>
                                                </ul>
                                                <ul>
                                                    <li>任职资格</li>
                                                    <li>1、口齿清晰，普通话流利，语音富有感染力;</li>
                                                    <li>2、对销售工作有较高的热情;充足的自信心</li>
                                                    <li>3、具备较强的学习能力和优秀的沟通能力;</li>
                                                    <li>4、性格坚韧，思维敏捷，具备良好的应变能力和承压能力;</li>
                                                    <li>5、有敏锐的市场洞察力，有强烈的事业心、责任心和积极的工作态度。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>商务合作经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 100人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1. 扩展平台、平行企业、电子商务商会、协会等的合作资源；</li>
                                                    <li>2. 负责异业类的合作洽谈及相关商务渠道的拓展、资源置换和联合营销；</li>
                                                    <li>3. 策划合作内容，合作项目进行跟踪，完成相关项目的谈判和执行；</li>
                                                    <li>4. 善于整合各种资源，制定合作实施资料计划，开拓深度合作；</li>
                                                    <li>5.了解竞品公司的资源合作及投放模式，提出新的资源拓展方案；</li>
                                                    <li>6.评估展会，并推进展会的合作事宜并执行；</li>
                                                    <li>7.完成上级领导安排的其他工作。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职资格</li>
                                                    <li>1．多渠道引入优质合作资源，推进合作进程，并对资源效果进行把控负责</li>
                                                    <li>2．责任心强，工作主动；</li>
                                                    <li>3. 有较强的执行的能力。</li>
                                                    <li>4. 推进各项资源合作进程及对接工作</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                </ul>
                            </div>
                            <div role="tabpanel" style="color:red;" class="tab-pane fade" id="category_04">
                                <ul>
                                    <li>
                                        <dl>
                                            <dt class="dt-hover">
                                                <span>产品经理</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/06.png" alt="">
                                            </dt>
                                            <dd style="display: block;">
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1．主要负责电商后台、运营管理、ERP（供应链管理、仓储管理，商品管理、业务报表管理）等系统功能的产品规划。</li>
                                                    <li>2．制定ERP产品的界面原型、功能、流程设计和交互设计，协调研发工作，掌控项目开发参与项目需求分析。</li>
                                                    <li>3．组织、协调与产品研发的相关资源，实现产品的发展目标，满足采购、销售、财务业务需求。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职资格</li>
                                                    <li>1．至少1年电商产品或者SaaS产品相关经验，独立产品负责人优先。</li>
                                                    <li>2．具有较强的沟通能力，逻辑思维能力和文档编写能力。</li>
                                                    <li>3．具备优秀的学习适应能力，高度责任心、抗压性强。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                    <li>
                                        <dl>
                                            <dt>
                                                <span>产品运营</span>
                                                <!-- <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 100人 -->
                                                <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                                <img class="last" src="http://${image_domain!}/image/admin/official/join/03.png" alt="">
                                            </dt>
                                            <dd>
                                                <ul>
                                                    <li>岗位职责</li>
                                                    <li>1．负责门店小程序运营策略的制定，对核心指标负责。</li>
                                                    <li>2．负责管理运营团队，有团队管理经验。</li>
                                                    <li>3．负责优化整个产品流程，提升用户体验以及不断尝试新的运营方式，提升用户粘性与满意度。</li>
                                                    <li>4．负责电商活动的组织和新媒体运营。</li>
                                                    <li>5．负责通过整理数据指标，及时调整和发现运营中的问题，并不断优化。</li>
                                                </ul>
                                                <ul>
                                                    <li>任职资格</li>
                                                    <li>1．3年以上运营管理经验，对零售行业有浓厚兴趣。</li>
                                                    <li>2．有较强的逻辑思维、数据分析能力和判断力，可以数据驱动、目标驱动地解决问题。</li>
                                                    <li>3．有较强的沟通、协调能力优秀，能拆解任务、分配KPI，有效管理团队。</li>
                                                    <li>4．有电商，用户运营经验者优先。</li>
                                                    <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                                </ul>
                                            </dd>
                                        </dl>
                                    </li>
                                </ul>
                            </div>
                            <!-- <div role="tabpanel" style="color:blue;" class="tab-pane fade" id="category_05">
                             <ul>
                                <li>
                                  <dl>
                                    <dt class="dt-hover">
                                      <span>C++工程师</span>
                                      <img class="first" src="http://${image_domain!}/image/admin/official/join/04.png" alt=""> 23人
                                      <img class="second" src="http://${image_domain!}/image/admin/official/join/05.png" alt=""> 北京
                                      <img class="last" src="http://${image_domain!}/image/admin/official/join/06.png" alt="">
                                    </dt>
                                    <dd style="display: block;">
                                      <ul>
                                        <li>岗位职责</li>
                                        <li>1、开发pc客户端及相关软件；</li>
                                        <li>2、开发新功能、修复BUG、及编写相关</li>
                                        <li>3、开发pc客户端及相关软件。</li>
                                      </ul>
                                      <ul>
                                        <li>任职资格</li>
                                        <li>1、熟练掌握C/C++语言，最新标准C++0x，标准库STL；</li>
                                        <li>2、熟悉Windows SDK，COM组件；</li>
                                        <li>3、熟练掌握常用数据结构和算法；</li>
                                        <li>4、熟练使用VisualC++编码、开发、测试；</li>
                                        <li>5、优秀的沟通能力和团队合作，能保证在开发过程中不断与相关人员进行沟通；</li>
                                        <li>6、优秀的学习能力，能在工作中不断提高自己的业务能力，并适应新的业务需求；</li>
                                        <li>7、熟悉htmlayout或CSS者优先；</li>
                                        <li>8、获过“蓝桥杯”或“ACM”比赛省级以上奖项者优先考虑。</li>
                                        <li class="li-last">虚以待位-简历请投递邮箱:<span>hr@wangdian.cn</span>我们收到会尽快与您联系。</li>
                                      </ul>
                                    </dd>
                                  </dl>
                                </li>
                              </ul>
                            </div> -->
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="channel">
                <div id="about-choice">
                    <div class="container">
                        <h2 class="common-h2 ">为什么选择旺店通</h2>
                        <div class="detail">
                            <div>
                                <h4 class="special">全面的扶持</h4>
                                <p>全面的从产品技术、服务、市场营销等<br>多维助推，满足合作伙伴的市场需求。</p>
                            </div>
                            <div style="left:112px;top:142px;color: #70affe;text-align: right;">
                                <h4>高质量的产品</h4>
                                <p style="width:290px;">
                                    场景化云服务，满足用户需求，旺店通5年行业技术积累，匠心精神，铸就高品质体验。
                                </p>
                            </div>
                            <div style="left:772px;top:142px;color: #f17261;">
                                <h4>五年的品牌沉淀</h4>
                                <p style="width:271px;">
                                    5年电商ERP行业深耕，5000多家客户的信赖和支持。
                                </p>
                            </div>
                            <div style="left:140px;top:334px;color: #e04c88;text-align: right;">
                                <h4>强培训支持</h4>
                                <p style="width:257px;">
                                    全线产品及服务培训，运营管理及营销培训，以及用户操作培训。
                                </p>
                            </div>
                            <div style="left:772px;top:333px;color: #38c7c2;">
                                <h4>全面的产品体系</h4>
                                <p style="width:259px">
                                    旺店通ERP、WMS、全渠道、商城、小程序等，为企业提供全渠道管理与服务的整体解决方案。
                                </p>
                            </div>
                            <div style="left:468px;top:466px;color: #00a820;">
                                <h4 class="special">快速的市场协助</h4>
                                <p>重点客户签约支持，商务、产品、技术、实施服务快速响应。</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="about-cooperate">
                    <div class="container">
                        <h2 class="common-h2">合作旺店通，我们能提供什么</h2>
                        <ul>
                            <li style="background:url('http://${image_domain!}/image/admin/official/channel/03.png')">
                                <div class="current">
                                    <img src="http://${image_domain!}/image/admin/official/channel/08.png" alt="">
                                    <h4>品牌</h4>
                                    <hr>
                                </div>
                                <div class="change">
                                    <img src="http://${image_domain!}/image/admin/official/channel/08.png" alt="">
                                    <h4>品牌</h4>
                                    <hr>
                                    <p>
                                        公司对合作商区域进行品牌授权，合作商有权对旺店通产品进行宣传和销售；
                                    </p>
                                </div>
                            </li>
                            <li class="hover" style="background:url('http://${image_domain!}/image/admin/official/channel/04.png')">
                                <div class="current">
                                    <img src="http://${image_domain!}/image/admin/official/channel/09.png" alt="">
                                    <h4>产品</h4>
                                    <hr>
                                </div>
                                <div class="change">
                                    <img src="http://${image_domain!}/image/admin/official/channel/09.png" alt="">
                                    <h4>产品</h4>
                                    <hr>
                                    <p>
                                        公司提供全线产品，以及后续的更新维护，满足各类电商客户的需求，最大限度的减少合作商投资风险
                                    </p>
                                </div>
                            </li>
                            <li style="background:url('http://${image_domain!}/image/admin/official/channel/05.png')">
                                <div class="current">
                                    <img src="http://${image_domain!}/image/admin/official/channel/10.png" alt="">
                                    <h4>服务</h4>
                                    <hr>
                                </div>
                                <div class="change">
                                    <img src="http://${image_domain!}/image/admin/official/channel/10.png" alt="">
                                    <h4>服务</h4>
                                    <hr>
                                    <p>
                                        公司在技术上和产品上给予充分的培训，让合作商五后顾之忧
                                    </p>
                                </div>
                            </li>
                            <li style="background:url('http://${image_domain!}/image/admin/official/channel/06.png')">
                                <div class="current">
                                    <img src="http://${image_domain!}/image/admin/official/channel/11.png" alt="">
                                    <h4>市场</h4>
                                    <hr>
                                </div>
                                <div class="change">
                                    <img src="http://${image_domain!}/image/admin/official/channel/11.png" alt="">
                                    <h4>市场</h4>
                                    <hr>
                                    <p>
                                        公司从产品技术、服务、市场等多维度助推合作商展开商务活动，满足合作商的市场需求
                                    </p>
                                </div>
                            </li>
                            <li style="background:url('http://${image_domain!}/image/admin/official/channel/07.png')">
                                <div class="current">
                                    <img src="http://${image_domain!}/image/admin/official/channel/12.png" alt="">
                                    <h4>培训</h4>
                                    <hr>
                                </div>
                                <div class="change">
                                    <img src="http://${image_domain!}/image/admin/official/channel/12.png" alt="">
                                    <h4>培训</h4>
                                    <hr>
                                    <p>
                                        线上培训，首次集中产品培训（直播），新功能跟总部同步培训（直播或视频），销售场景培训（直播或视频）；同时也支持线下培训，需客户自费过来，到总部培训。
                                    </p>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="about-mode">
                    <div class="container">
                        <h2 class="common-h2">旺店通的合作模式</h2>
                        <ul>
                            <li>
                                <a href="{:url('index/apply')}" style="color: inherit">
                                    <h4>销售软件提点返点收益</h4>
                                    <p>申请加入</p>
                                </a>
                            </li>
                            <li>
                                <a href="{:url('index/apply')}" style="color: inherit">
                                    <h4>后续实施维护收益</h4>
                                    <p>申请加入</p>
                                </a>
                            </li>
                            <li class="hover">
                                <a href="{:url('index/apply')}" style="color: inherit">
                                    <h4>代运营支持服务收益</h4>
                                    <p>申请加入</p>
                                </a>
                            </li>
                            <li>
                                <a href="{:url('index/apply')}" style="color: inherit">
                                    <h4>续费提点返点收益</h4>
                                    <p>申请加入</p>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="about-five">
                    <div class="container">
                        <h2 class="common-h2">合作旺店通五步骤</h2>
                        <div style="position: relative;">
                            <img src="http://${image_domain!}/image/admin/official/channel/20.png" style="margin-top:106px;" alt="">
                            <div>
                                <img src="http://${image_domain!}/image/admin/official/channel/15.png" alt="">
                                <p>提交申请合作</p>
                            </div>
                            <div style="left:586px;">
                                <img src="http://${image_domain!}/image/admin/official/channel/16.png" alt="">
                                <p>合作洽谈</p>
                            </div>
                            <div style="left:960px;">
                                <img src="http://${image_domain!}/image/admin/official/channel/17.png" alt="">
                                <p>签约合作</p>
                            </div>
                            <div style="top:282px;left:378px;">
                                <img src="http://${image_domain!}/image/admin/official/channel/18.png" alt="">
                                <p>业务培训</p>
                            </div>
                            <div style="top:282px;left:788px;">
                                <img src="http://${image_domain!}/image/admin/official/channel/19.png" alt="">
                                <p>展开合作</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--<div class="tab-pane fade" id="market">-->
            <!--</div>-->
        </div>
    </div>
</section>
<!-- /关于我们 -->





<#include ("index.official_footer")
<#include "/admin/footer.ftl">
</body>
<script type="text/javascript">
    $('.nav ul').find('li').eq(3).addClass('active');

</script>
<script src="/js/admin/about.js?v=1.0.0"></script>
<script>
    $(function() {
        $("#about-us #join #join-us a").mouseenter(function (e) {
            $("#about-us #join #join-us a").css('background', 'transparent');
            $(this).tab('show');
            $(this).css('background', '#0489ff');
        });
        var toggle = true;
        $("#about-us #join #join-us dt").mouseenter(function(){

        });
        $("#about-us #join #join-us dt").mouseleave(function(){

        });
        $('.tab-content dt').on('click',function(){
            var display = $(this).parent().parent().find('dd').css('display');
            if(display=="none"){
                $(this).find('.first').attr('src','http://${image_domain!}/image/admin/official/join/04.png');
                $(this).find('.second').attr('src','http://${image_domain!}/image/admin/official/join/05.png');
                $(this).find('.last').attr('src','http://${image_domain!}/image/admin/official/join/06.png');
                toggle = false;
            }else{
                $(this).find('.first').attr('src','http://${image_domain!}/image/admin/official/join/01.png');
                $(this).find('.second').attr('src','http://${image_domain!}/image/admin/official/join/02.png');
                $(this).find('.last').attr('src','http://${image_domain!}/image/admin/official/join/03.png');
                toggle = true;
            }
            $(this).parent().find('dd').toggle();
            $(this).toggleClass('dt-hover');
        });
        $("#about-cooperate li").mousemove(function(event) {
            $(this).addClass('hover').siblings().removeClass('hover');
        });
        $("#about-mode li").mousemove(function(event) {
            $(this).addClass('hover').siblings().removeClass('hover');
        });
    });
    window.onload = function(){
        function $(id){return document.getElementById(id);}
        var wrap = $("wrap");
        var slide = $("slide");
        var lis = slide.children[0].children;
        var arrow = $("arrow");
        var as = arrow.children;
        wrap.onmouseover = function(){
            animate(arrow,{opacity:100});
        };
        wrap.onmouseout= function(){
            animate(arrow,{opacity:30});
        };
        var json = [
            {
                width:317,
                height:240,
                top:46,
                opacity:100,
                left:60,
                z:1,
                lineHeight:236
            },
            {
                width:431,
                height:327,
                top:0,
                opacity:100,
                left:366,
                z:2,
                lineHeight:323
            },
            {
                width:317,
                height:240,
                top:46,
                opacity:100,
                left:780,
                z:1,
                lineHeight:236
            }
        ];
        var jieliu = true;
        change();
        for(var k in as){
            as[k].onclick = function(){
                if(this.className == "prev"){
                    if(jieliu == true){
                        change(false);
                        jieliu = false;
                    }
                }else{
                    if(jieliu == true){
                        change(true);
                        jieliu = false;
                    }
                }
            }
        }
        function change(flag){
            if(flag){
                json.unshift(json.pop());
            }else{
                json.push(json.shift());
            }
            for(var i= 0; i< json.length; i++){
                animate(lis[i],{
                    width:json[i].width,
                    height:json[i].height,
                    top:json[i].top,
                    opacity:json[i].opacity,
                    left:json[i].left,
                    zIndex:json[i].z,
                    lineHeight:json[i].lineHeight
                },function(){
                    jieliu =true;
                });
            }
        }

    }
</script>