<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_center_config.css?v=1.0.2" type="text/css" />
<style type="text/css">
    .ia_title,.eia_his,.eia_num span{
        font-weight: bold;
    }
    .ia_title .iat2{
        font-weight:normal;
    }
    .order-info{
        padding-bottom: 0;
    }
    .ia_float .each_ia_item{
        width:98px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>基础配置 / </span>
        <span style="color: #666;">${title!}</span>
    </div>
    <div class="main-container">
        <div class="order-info fix_every_footer">
            <form action="/admin/config/usercenter" method="post" id="form1">
                {{ csrf_field()!}
                <input type="hidden" name="bg_img" id="bg_img_path" value="${config_info['bg_img']!}">
                <input type="hidden" name="user_center" value="{{json_encode($module_data)!}" />
                <div class="prompt">
                    <img src="http://${image_domain!}/image/admin/notice_img.png" />
                    <span>为避免产生不必要的运营问题，建议关闭不使用的展示配置项</span>
                </div>
                <div class="clearfix">
                    <div class="config_left">
                        <div class="left_title"></div>
                        <div class="left_info">

                        </div>
                    </div>
                    <div class="config_right">
                        <div class="style_set clearfix">
                            <div class="sty_title">页面布局样式：</div>
                            <div class="sty_choose">
                                <label for="cover_style">
                                    <input type="radio" name="page_style" value="1" id="cover_style" checked> 平铺式
                                </label>
                                <label for="card_style">
                                    <input type="radio" name="page_style" value="2" id="card_style" > 卡片式
                                </label>
                            </div>
                        </div>
                        {{--背景设置--!}
                        <div class="config_right_info" module_name="center_header" >
                            <div class="info_title">
                                个人信息背景色配置
                                <span class="info_con_block">
                                    收起
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" />
                                </span>
                                <span class="info_con_none">
                                    展开
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" />
                                </span>
                            </div>
                            <div class="info_content">
                                <div class="bg_choose">
                                    <div>
                                        <input type="radio" name="bg_type" id="bg_color" <#if (!$config_info['bg_type']) checked="checked" </#if> class="checkbox_actives" value="0" >同步店铺配色
                                    </div>
                                    <div class="back_images clearfix" style="    margin-top: 10px;">
                                        <input type="radio" name="bg_type" id="bg_img" value="1" style="float: left;top:0px" <#if ($config_info['bg_type'] == 1) checked="checked" </#if>>
                                        <span style="float:left;;">自定义图片</span>
                                        <a href="##" class="add_img" style="width: 175px;">
                                            <#if ($config_info['bg_type'] == 1)
                                                <img src="http://${image_domain!}/${config_info['bg_img']!}"/>
                                            </#if>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {{--个人资产设置--!}
                        <div class="config_right_info" module_name="account_money">
                            <div class="info_title">
                                账户资产信息展示项配置
                                <span class="info_con_block">
                                    收起
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" />
                                </span>
                                <span class="info_con_none">
                                    展开
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" />
                                </span>
                            </div>
                            <div class="info_content clearfix">
                                <div class="list_promt account_money">
                                    <div class="lp_kaiguan clearfix">
                                        <span>资产：</span>
                                        <div class="store_pay_fl" img_id="1" >
                                            <label>
                                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_list" />
                                            </label>
                                        </div>
                                        <span>已开启</span>
                                        <span class="kaigun_tips">开关开启，则展示账户资产信息</span>
                                    </div>
                                    <div class="lp_somth">
                                        <div class="lp_title">
                                            标题：<input type="text" name="" placeholder="我的资产">
                                            <span class="kaigun_tips">用户资产模块标题，不填写则不显示</span>
                                        </div>
                                        <div class="lp_content">
                                            <ul>
                                                <li class="if_show_sth clearfix" icon_name="account">
                                                    <div class="iss_title">我的余额：</div>
                                                    <div class="iss_detail">
                                                        <label for="show_yue">
                                                            <input type="radio" name="my_yue" value="1" checked id="show_yue"> 展示
                                                        </label>
                                                        <label for="no_yue">
                                                            <input type="radio" name="my_yue" value="0"  id="no_yue"> 不展示
                                                        </label>
                                                    </div>
                                                </li>
                                                <li class="if_show_sth clearfix" icon_name="score">
                                                    <div class="iss_title">我的积分：</div>
                                                    <div class="iss_detail">
                                                        <label for="show_score">
                                                            <input type="radio" name="my_score" value="1" checked id="show_score"> 展示
                                                        </label>
                                                        <label for="no_score">
                                                            <input type="radio" name="my_score" value="0"  id="no_score"> 不展示
                                                        </label>
                                                    </div>
                                                </li>
                                                <li class="if_show_sth clearfix" icon_name="coupon">
                                                    <div class="iss_title">优惠券：</div>
                                                    <div class="iss_detail">
                                                        <label for="show_coupon">
                                                            <input type="radio" name="my_coupon" value="1" checked id="show_coupon"> 展示
                                                        </label>
                                                        <label for="no_coupon">
                                                            <input type="radio" name="my_coupon" value="0"  id="no_coupon"> 不展示
                                                        </label>
                                                    </div>
                                                </li>
                                                <li class="if_show_sth clearfix" icon_name="card">
                                                    <div class="iss_title">会员卡：</div>
                                                    <div class="iss_detail">
                                                        <label for="show_card">
                                                            <input type="radio" name="my_card" value="1" checked id="show_card"> 展示
                                                        </label>
                                                        <label for="no_card">
                                                            <input type="radio" name="my_card" value="0"  id="no_card"> 不展示
                                                        </label>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {{--订单设置--!}
                        <div class="config_right_info" module_name="order">
                            <div class="info_title">
                                订单信息展示项配置
                                <span class="info_con_block">
                                    收起
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" />
                                </span>
                                <span class="info_con_none">
                                    展开
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" />
                                </span>
                            </div>
                            <div class="info_content clearfix">
                                <div class="list_promt order_message">
                                    <div class="lp_kaiguan clearfix">
                                        <span>订单：</span>
                                        <div class="store_pay_fl" img_id="1" >
                                            <label>
                                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_list"/>
                                            </label>
                                        </div>
                                        <span>已开启</span>
                                        <span class="kaigun_tips">开关开启，则展示订单信息</span>
                                    </div>
                                    <div class="lp_somth">
                                        <div class="lp_title">
                                            标题：<input type="text" name="" placeholder="我的订单">
                                            <span class="kaigun_tips">订单模块标题，不填写则不显示</span>
                                        </div>
                                        <div class="lp_style clearfix">
                                            <div class="ls_left">模块样式：</div>
                                            <div class="ls_right">
                                                <label for="ls_sty1">
                                                    <input type="radio" name="order_style" value="1" checked id="ls_sty1" class="module_order_type">样式1
                                                </label>
                                                <label for="ls_sty2">
                                                    <input type="radio" name="order_style" value="2" id="ls_sty2" class="module_order_type">样式2
                                                </label>
                                            </div>
                                        </div>
                                        <div class="lp_have_img">
                                            <ul>
                                                <li class="rach_line_set clearfix" icon_name="wait_pay">
                                                    <div class="rls_title">待付款订单：</div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon1.png" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="rach_line_set clearfix" icon_name="wait_deliver">
                                                    <div class="rls_title">待发货订单：</div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon2.png" alt="" class="uc_order_icon2">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="rach_line_set clearfix" icon_name="wait_receive">
                                                    <div class="rls_title">待收货订单：</div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon3.png" alt="" class="uc_order_icon3">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="rach_line_set clearfix show_comment" icon_name="wait_comment">
                                                    <div class="rls_title"><input type="radio" name="show_which" value="0" checked>待评价订单：</div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon4.png" alt="" class="uc_order_icon4">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="rach_line_set clearfix shou_return" icon_name="refund">
                                                    <div class="rls_title"><input type="radio" name="show_which" value="1" >退款中订单：</div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon5.png" alt="" class="uc_order_icon5">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        {{--预约设置--!}
                        <div class="config_right_info" module_name="appointment">
                            <div class="info_title">
                                预约信息展示项配置
                                <span class="info_con_block">
                                    收起
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" />
                                </span>
                                <span class="info_con_none">
                                    展开
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" />
                                </span>
                            </div>
                            <div class="info_content">
                                <div class="list_promt appointment_message">
                                    <div class="lp_kaiguan clearfix">
                                        <span>预约：</span>
                                        <div class="store_pay_fl" img_id="1" >
                                            <label>
                                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_list"/>
                                            </label>
                                        </div>
                                        <span>已开启</span>
                                        <span class="kaigun_tips">开关开启，则展示预约信息</span>
                                    </div>
                                    <div class="lp_somth">
                                        <div class="lp_title">
                                            标题：<input type="text" name="" placeholder="我的预约">
                                            <span class="kaigun_tips">预约模块标题，不填写则不显示</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {{--店铺使用数据--!}
                        <div class="config_right_info" module_name="use_record">
                            <div class="info_title">
                                店铺使用相关数据展示项配置
                                <span class="info_con_block">
                                    收起
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" />
                                </span>
                                <span class="info_con_none">
                                    展开
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" />
                                </span>
                            </div>
                            <div class="info_content clearfix">
                                <div class="list_promt some_history">
                                    <div class="lp_kaiguan clearfix">
                                        <span>店铺使用：</span>
                                        <div class="store_pay_fl" img_id="1" >
                                            <label>
                                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_list" />
                                            </label>
                                        </div>
                                        <span>已开启</span>
                                        <span class="kaigun_tips">开关开启，则展示店铺使用信息</span>
                                    </div>
                                    <div class="lp_somth">
                                        <div class="lp_title">
                                            标题：<input type="text" name="" placeholder="使用记录">
                                            <span class="kaigun_tips">店铺使用模块标题，不填写则不显示</span>
                                        </div>
                                        <div class="lp_content">
                                            <ul>
                                                <li class="if_show_sth clearfix">
                                                    <div class="iss_title">我的收藏：</div>
                                                    <div class="iss_detail">
                                                        <label for="show_collect">
                                                            <input type="radio" name="my_collect" value="1" checked id="show_collect"> 展示
                                                        </label>
                                                        <label for="no_collect">
                                                            <input type="radio" name="my_collect" value="0"  id="no_collect"> 不展示
                                                        </label>
                                                        <span class="laben_tups">用户收藏的商品</span>
                                                    </div>
                                                </li>
                                                <li class="if_show_sth clearfix">
                                                    <div class="iss_title">历史购买：</div>
                                                    <div class="iss_detail">
                                                        <label for="show_his">
                                                            <input type="radio" name="buy_history" value="1" checked id="show_his"> 展示
                                                        </label>
                                                        <label for="no_his">
                                                            <input type="radio" name="buy_history" value="0"  id="no_his"> 不展示
                                                        </label>
                                                        <span class="laben_tups">用户购买过的商品</span>
                                                    </div>
                                                </li>
                                                <li class="if_show_sth clearfix">
                                                    <div class="iss_title">我的足迹：</div>
                                                    <div class="iss_detail">
                                                        <label for="show_foot">
                                                            <input type="radio" name="my_foot" value="1" checked id="show_foot"> 展示
                                                        </label>
                                                        <label for="no_foot">
                                                            <input type="radio" name="my_foot" value="0"  id="no_foot"> 不展示
                                                        </label>
                                                        <span class="laben_tups">用户浏览过的商品</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {{--服务展示设置--!}
                        <div class="config_right_info" module_name="service">
                            <div class="info_title">
                                服务展示项配置
                                <span class="info_con_block">
                                    收起
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" />
                                </span>
                                <span class="info_con_none">
                                    展开
                                    <img src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" />
                                </span>
                            </div>
                            <div class="info_content clearfix">
                                <div class="list_promt other_message">
                                    <div class="lp_kaiguan clearfix">
                                        <span>服务：</span>
                                        <div class="store_pay_fl" img_id="1" >
                                            <label>
                                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_list"/>
                                            </label>
                                        </div>
                                        <span>已开启</span>
                                        <span class="kaigun_tips">开关开启，则展示店铺服务信息</span>
                                    </div>
                                    <div class="lp_somth">
                                        <div class="lp_title">
                                            标题：<input type="text" name="" placeholder="我的服务">
                                            <span class="kaigun_tips">店铺使用模块标题，不填写则不显示</span>
                                        </div>
                                        <div class="lp_have_img">
                                            <ul>
                                                <li class="each_line_set clearfix" icon_name="distribution">
                                                    <div class="rls_title clearfix">
                                                        <div class="tti_word">分销中心：</div>
                                                        <div class="show_or_no">
                                                            <label for="dis_show">
                                                                <input type="radio" name="has_dis" value="1" checked id="dis_show">展示
                                                            </label>
                                                            <label for="dis_no">
                                                                <input type="radio" name="has_dis" value="0"  id="dis_no">不展示
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/icon_dis.png" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="each_line_set clearfix" icon_name="bargain">
                                                    <div class="rls_title clearfix">
                                                        <div class="tti_word">我的砍价：</div>
                                                        <div class="show_or_no">
                                                            <label for="bar_show">
                                                                <input type="radio" value="1" name="has_bar" checked id="bar_show">展示
                                                            </label>
                                                            <label for="bar_no">
                                                                <input type="radio" value="0" name="has_bar"  id="bar_no">不展示
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/icon_bargain.png" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="each_line_set clearfix" icon_name="award">
                                                    <div class="rls_title clearfix">
                                                        <div class="tti_word">我的奖品：</div>
                                                        <div class="show_or_no">
                                                            <label for="gift_show">
                                                                <input type="radio" value="1" name="has_award" checked id="gift_show">展示
                                                            </label>
                                                            <label for="gift_no">
                                                                <input type="radio" value="0" name="has_award"  id="gift_no">不展示
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/icon_award.png" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="each_line_set clearfix" icon_name="store_list">
                                                    <div class="rls_title clearfix">
                                                        <div class="tti_word">门店列表：</div>
                                                        <div class="show_or_no">
                                                            <label for="store_show">
                                                                <input type="radio" value="1" name="has_store" checked id="store_show">展示
                                                            </label>
                                                            <label for="store_no">
                                                                <input type="radio" value="0"  name="has_store" id="store_no">不展示
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/icon_store.png" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="each_line_set clearfix" icon_name="user_activate">
                                                    <div class="rls_title clearfix">
                                                        <div class="tti_word">会员激活：</div>
                                                        <div class="show_or_no">
                                                            <label for="member_show">
                                                                <input type="radio" value="1" name="has_member" checked id="member_show">展示
                                                            </label>
                                                            <label for="member_no">
                                                                <input type="radio" value="0" name="has_member"  id="member_no">不展示
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/icon_member.png" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <li class="each_line_set clearfix" icon_name="order_verify">
                                                    <div class="rls_title clearfix">
                                                        <div class="tti_word">扫码核销：</div>
                                                        <div class="show_or_no">
                                                            <label for="scan_show">
                                                                <input type="radio" value="1" name="has_scan" checked id="scan_show">展示
                                                            </label>
                                                            <label for="scan_no">
                                                                <input type="radio" value="0" name="has_scan"  id="scan_no">不展示
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="http://${image_domain!}/image/admin/uc_config/icon_scan.png" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                </li>
                                                <#list  ((array)$module_data as $moduleData)
                                                    <#if  ($moduleData['module_name'] == 'service')
                                                        <#list ((array)$moduleData['content'] as $iconName)
                                                            <#if  ($iconName['icon_name'] == 'custom_icon')
                                                <li class="each_line_set clearfix own_set_module" icon_name="custom_icon">
                                                    <div class="rls_title clearfix">
                                                        <div class="tti_word">自定义模块：</div>
                                                        <div class="show_or_no">
                                                            <label for="has_own">
                                                                <input type="radio" value="1" name="is_show_${loop->iteration!}" <#if  ($iconName['is_show']) checked </#if>  id="has_own">展示
                                                            </label>
                                                            <label for="no_own">
                                                                <input type="radio" value="0" name="is_show_${loop->iteration!}"  id="no_own" <#if  (!$iconName['is_show']) checked </#if> >不展示
                                                            </label>
                                                            <a href="##" class="btn_del_this">删除模块</a>
                                                        </div>
                                                    </div>
                                                    <div class="set_own_name">
                                                         标题：<input type="text" name="" placeholder="帮助中心" class="own_title" maxlength="6" value="${iconName['title']!}">
                                                    </div>
                                                    <div class="rls_imgs clearfix">
                                                        <div class="img_home">
                                                            <img src="${iconName['icon']!}" alt="" class="uc_order_icon1">
                                                            <div class="change_icon">更换图标</div>
                                                        </div>
                                                        <span class="img_tips">建议尺寸：50px*50px</span>
                                                    </div>
                                                    <div class="choose_link">
                                                        跳转到的页面：
                                                        <#if  (!$iconName['link'])
                                                            <a href="##" class="btn_choose_link">选择链接</a>
                                                            <div class="choosed_link"></div>
                                                            <#else>
                                                            <a href="##" class="btn_choose_link">重新选择</a>
                                                            <div class="choosed_link"><span>${iconName['link']!}</span>
                                                                <#if  ($iconName['link_name'])
                                                                (<span>${iconName['link_name']!}</span>)
                                                                </#if>
                                                            </div>
                                                        </#if>

                                                    </div>
                                                </li>
                                                            </#if>
                                                        </#list>
                                                    </#if>
                                                </#list>
                                            </ul>
                                            <div class="btn_add_module">添加自定义模块</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="fix_footer">
            <span class="btn_footer_save" style="width: 90px;line-height: 28px">保存</span>
        </div>
    </div>
</div>
<div id="clone" hidden>
    <img src="http://${image_domain!}/image/wxapp/center_set.png" class="center_set" />
    <img src="http://${image_domain!}/image/admin/user_touxiang.png" class="center_head" />
    <div class="center_name">用户昵称</div>
    <div class="center_sign">
        <img src="http://${image_domain!}/image/wxapp/sign_icon.png" />
        积分签到：今日可领10积分
    </div>
</div>
<div id="center_template" hidden>
    <div>
        <div class="left_info_head" module_name="center_header">
            <img src="http://${image_domain!}/image/wxapp/center_set.png" class="center_set" />
            <img src="http://${image_domain!}/image/admin/user_touxiang.png" class="center_head" />
            <div class="center_name">用户昵称</div>
            <div class="center_sign">
                <img src="http://${image_domain!}/image/wxapp/sign_icon.png" />
                积分签到：今日可领10积分
            </div>
        </div>
    </div>
    {{--我的资产--!}
    <div>
        <div class="indoor_area my_account"  module_name="account_money">
            <div class="ia_title" style="justify-content: flex-start">我的资产</div>
            <div class="ia_content">
                <div class="each_ia_item" icon_name="account">
                    <div class="eia_num" style="color: ${bg_color[1]!};"><span>1000.99</span>元</div>
                    <div class="eia_word">我的余额</div>
                </div>
                <div class="each_ia_item" icon_name="score">
                    <div class="eia_num" style="color: ${bg_color[1]!};"><span>1000</span></div>
                    <div class="eia_word">我的积分</div>
                </div>
                <div class="each_ia_item" icon_name="coupon">
                    <div class="eia_num" style="color: ${bg_color[1]!};"><span>122</span>张</div>
                    <div class="eia_word">优惠券</div>
                </div>
                <div class="each_ia_item" icon_name="card">
                    <div class="eia_num" style="color: ${bg_color[1]!};"><span>90</span>张</div>
                    <div class="eia_word">会员卡</div>
                </div>
            </div>
            <div class="item_operation">
                <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
            </div>
        </div>
    </div>
    {{--我的订单--!}
    <div>
        <div class="indoor_area my_order"  module_name="order">
            <div class="ia_title">
                <div class="iat1">我的订单</div>
                <div class="iat2">查看全部订单 <img src="http://${image_domain!}/image/wxapp/right_into.png" alt=""></div>
            </div>
            <div class="ia_content">
                <div class="each_ia_item" icon_name="wait_pay">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon1.png" alt=""></div>
                    <div class="eia_word">待付款</div>
                </div>
                <div class="each_ia_item" icon_name="wait_deliver">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon2.png" alt=""></div>
                    <div class="eia_word">待发货</div>
                </div>
                <div class="each_ia_item" icon_name="wait_receive">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon3.png" alt=""></div>
                    <div class="eia_word">待收货</div>
                </div>
                <div class="each_ia_item comment_info" icon_name="wait_comment">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon4.png" alt=""></div>
                    <div class="eia_word">待评价</div>
                </div>
                <div class="each_ia_item return_order_info" icon_name="refund">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon5.png" alt=""></div>
                    <div class="eia_word">退款中</div>
                </div>
                <div class="each_ia_special">
                    <img src="http://${image_domain!}/image/admin/uc_config/icon_jiantou.png" alt="" class="jiantou">
                    <div class="each_ia_item">
                        <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/uc_order_icon6.png" alt=""></div>
                        <div class="eia_word">全部订单</div>
                    </div>
                </div>
            </div>
            <div class="item_operation">
                <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
            </div>
        </div>
    </div>
    {{--我的预约--!}
    <div>
        <div class="indoor_area my_appointment"  module_name="appointment">
            <div class="ia_title">
                <div class="iat1">我的预约</div>
                <div class="iat2">查看全部预约 <img src="http://${image_domain!}/image/wxapp/right_into.png" alt=""></div>
            </div>
            <div class="ia_content clearfix">
                <div class="app_img"><img src="http://${image_domain!}/image/wxapp/img1.jpg" alt=""></div>
                <div class="app_info">
                    <div class="app_name">单人璀璨睫毛嫁接套餐1次</div>
                    <div class="app_time">预约到店时间：2018-03-05 12:30</div>
                </div>
            </div>
            <div class="item_operation">
                <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
            </div>
        </div>
    </div>
    {{--使用记录--!}
    <div>
        <div class="indoor_area use_history"  module_name="use_record">
            <div class="ia_title" style="justify-content: flex-start">使用记录</div>
            <div class="ia_content">
                <div class="each_ia_item">
                    <div class="eia_his">100</div>
                    <div class="eia_word">我的收藏</div>
                </div>
                <div class="each_ia_item">
                    <div class="eia_his">100</div>
                    <div class="eia_word">历史购买</div>
                </div>
                <div class="each_ia_item">
                    <div class="eia_his">100</div>
                    <div class="eia_word">我的足迹</div>
                </div>
            </div>
            <div class="item_operation">
                <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
            </div>
        </div>
    </div>
    {{--我的服务--!}
    <div>
        <div class="indoor_area my_service"  module_name="service">
            <div class="ia_title" style="justify-content: flex-start">我的服务</div>
            <div class="ia_float clearfix" style="flex-wrap: wrap">
                <div class="each_ia_item" icon_name="distribution">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/icon_dis.png" alt=""></div>
                    <div class="eia_word">分销中心</div>
                </div>
                <div class="each_ia_item" icon_name="bargain">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/icon_bargain.png" alt=""></div>
                    <div class="eia_word">我的砍价</div>
                </div>
                <div class="each_ia_item" icon_name="award">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/icon_award.png" alt=""></div>
                    <div class="eia_word">我的奖品</div>
                </div>
                <div class="each_ia_item" icon_name="store_list">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/icon_store.png" alt=""></div>
                    <div class="eia_word">门店列表</div>
                </div>
                <div class="each_ia_item" icon_name="user_activate">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/icon_member.png" alt=""></div>
                    <div class="eia_word">会员激活</div>
                </div>
                <div class="each_ia_item" icon_name="order_verify">
                    <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/icon_scan.png" alt=""></div>
                    <div class="eia_word">扫码核销</div>
                </div>
            </div>
            <div class="item_operation">
                <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
                <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
            </div>
        </div>
    </div>
</div>
<div id="custom_icon">
    <div class="each_ia_item" icon_name="custom_icon">
        <div class="eia_img"><img src="http://${image_domain!}/image/admin/uc_config/icon_ownset.png" alt=""></div>
        <div class="eia_word">帮助中心</div>
    </div>
</div>
<div id="own_set_modules" hidden>
    <li class="each_line_set clearfix own_set_module" icon_name="custom_icon">
        <div class="rls_title clearfix">
            <div class="tti_word">自定义模块：</div>
            <div class="show_or_no">
                <label for="has_own">
                    <input type="radio" value="1" name="is_show" checked id="has_own">展示
                </label>
                <label for="no_own">
                    <input type="radio" value="0" name="is_show"  id="no_own">不展示
                </label>
                <a href="##" class="btn_del_this">删除模块</a>
            </div>
        </div>
        <div class="set_own_name">
            标题：<input type="text" name="" placeholder="帮助中心" class="own_title">
        </div>
        <div class="rls_imgs clearfix">
            <div class="img_home">
                <img src="http://${image_domain!}/image/admin/uc_config/icon_ownset.png" alt="" class="uc_order_icon1">
                <div class="change_icon">更换图标</div>
            </div>
            <span class="img_tips">建议尺寸：50px*50px</span>
        </div>
        <div class="choose_link">
            跳转到的页面：
            <a href="##" class="btn_choose_link">选择链接</a>
            <div class="choosed_link"></div>
        </div>
    </li>
</div>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/jquery.bigcolorpicker.min.js"></script>
<script language="JavaScript" src="/js/admin/user_center_config.js?v=1.6.0"></script>
<script type="text/javascript">

    /*背景色*/
    var main_color = "${bg_color[1]!}";
    var no_main_color = "${bg_color[0]!}";


    $("input[name='bg_type']").change(function () {
        if($("#bg_color").is(":checked")){
            if($("#bg_color").is(":checked")){
                var clone = $('#clone').html();
                $(".left_info_head").html("");
                $(".left_info_head").append("<div style='width:100%;height:148px;' id='show_bg_color'></div>");
                $(".left_info_head").append(clone);
                $(document).find("#show_bg_color").css("background","linear-gradient(left,"+no_main_color+","+main_color+")");
                $(document).find("#show_bg_color").css("background","-webkit-linear-gradient(left,"+no_main_color+","+main_color+")");
                $(document).find("#show_bg_color").css("background","-moz-linear-gradient(left,"+no_main_color+","+main_color+")");
            }
        }
        if($("#bg_img").is(":checked")){
            if($(".add_img").html()!= ""){
                var clone = $('#clone').html();
                $(".left_info_head").html("");
                $(".left_info_head").append("<img style='width:100%;height:148px' class='top'>");
                $(".left_info_head").append(clone);
                $(".left_info_head").find('img').eq(0).attr("src",$(".add_img img").attr("src"));
            }
        }
    });

    var moduleData = @json($module_data);
    var module_name_obj;

    userCenter.initRight();
    userCenter.initLeft();
</script>
<#include "/admin/footer.ftl">