<#include ("system.header")
<link rel="stylesheet" href="/css/admin/overview.css?v=1.0.8" type="text/css" />
<style type="text/css">
    .over-left {
        width: 93%;
        float: left;
    }
    .float-layer {
        float: right;
        padding: 15px;
        position: absolute;
        left: 110px;
        top: -103px;
        z-index:2;
        border:1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150, 150, 150, 0.5);
        border-radius: 5px;
        background-color: #fff;
        line-height: 30px;
        display: none;
        font-size: 12px;
    }
    .float-layer .float-layer-text {
        font:inherit;
        margin: 0;
        padding: 0;
        line-height:1.5;
        vertical-align: baseline;
    }
    .float-layer .float-layer-i{
        position: absolute;
        top: 50%;
        left: 0;
        margin-left: -6px;
        transform: translateY(-50%) rotate(45deg);
        width: 0;
        height: 0;
        border-width: 7px;
        border-style: dashed;
        border-color: #fff;
        box-shadow: -2px 2px 3px rgba(150, 150, 150, 0.5);
        z-index: 1;

    }
    .ov_head_often div{
        cursor:pointer;
        padding: 0;
    }
    .ov_head_often>div>a{
        width: 100%;
        height: 100%;
        display: block;
        padding: 23px 0;
    }
    .left-one {
        height: 240px;
    }
    .left-order-content {
        display: flex;
        justify-content: center;
        align-items: center;
        /* margin-top: 0px; */
        padding-top: 15px;
    }
</style>
<div class="main-container">
    <div class="over-left">
        <div class="left-one">
            <div class="left-title">订单管理</div>
            <div class="left-order-content">
                <div class="order clearfix" style="border-right: 1px solid #eee;">
                    <a href="/system/order/list?order_status_d=3">
                        <div class="order-img">
                            <img src="http://${image_domain!}/image/admin/new_ov/ov_wait_order.png">
                        </div>
                        <div class="order-data">
                            <h3>待发货订单</h3>
                            <h2><#if ($order_status["express_num"]) ${order_status["express_num"]!} <#else> 0 </#if></h2>
                        </div>
                    </a>
                </div>
                <div class="order clearfix" style="border-right: 1px solid #eee;">
                    <a href="/system/order/list?return_flag=1">
                            <div class="order-img">
                            <img src="http://${image_domain!}/image/admin/new_ov/ov_back_order.png">
                        </div>
                        <div class="order-data">
                            <h3>退款退货订单</h3>
                            <h2><#if ($order_status['refund_status']) ${order_status['refund_status']!} <#else> 0 </#if></h2>
                        </div>
                    </a>
                </div>
                <div class="order clearfix">
                    <a href="/system/order/list?order_status=3&deliver_type=1">
                        <div class="order-img">
                            <img src="http://${image_domain!}/image/admin/new_ov/over_wait_back.png">
                        </div>
                        <div class="order-data">
                            <h3>待提货订单</h3>
                            <h2><#if ($order_status['pickup_num']) ${order_status['pickup_num']!} <#else> 0 </#if></h2>
                        </div>
                    </a>
                </div>
            </div>
            <div class="left-order-content">
                <div class="order clearfix" style="border-right: 1px solid #eee;">
                    <a href="/system/order/list?order_status_d=3&time=1">
                        <div class="order-img">
                            <img src="http://${image_domain!}/image/admin/new_ov/ov_wait_order.png">
                        </div>
                        <div class="order-data">
                            <h3>七天未发货订单</h3>
                            <h2><#if ($order_status["sev_express_num"]) ${order_status["sev_express_num"]!} <#else> 0 </#if></h2>
                        </div>
                    </a>
                </div>
                <div class="order clearfix" style="border-right: 1px solid #eee;">
                    <a href="/system/order/list?return_flag=1&time=1">
                        <div class="order-img">
                            <img src="http://${image_domain!}/image/admin/new_ov/ov_back_order.png">
                        </div>
                        <div class="order-data">
                            <h3>七天未退款退货订单</h3>
                            <h2><#if ($order_status['sev_refund_status']) ${order_status['sev_refund_status']!} <#else> 0 </#if></h2>
                        </div>
                    </a>
                </div>
                <div class="order clearfix">
                    <a href="/system/order/list?order_status_d=3&time=1">
                        <div class="order-img">
                            <img src="http://${image_domain!}/image/admin/new_ov/over_wait_back.png">
                        </div>
                        <div class="order-data">
                            <h3>七天未提货订单</h3>
                            <h2><#if ($order_status['sev_pickup_num']) ${order_status['sev_pickup_num']!} <#else> 0 </#if></h2>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div class="left-two">
            <div class="left-title clearfix" style="margin-bottom: 10px">
                <span>数据展示</span>
                <i class="item-image" >
                    <img src="http://${image_domain!}/image/admin/analysis_tishi.png" width="14" height="14" style="vertical-align: middle;margin-bottom: 2px;"/>
                </i>
                <div class="float-layer">
                    <div class="float-layer-i"></div>
                    <div class="float-layer-text">
                 <span>
                 访问人数：统计时间内，所有店铺所有页面（包括店铺主页、单品页、会员主页等）被访问的去重人数，一个人在统计时间范围内访问多次只记为一个
                 <br/>
                 下单笔数：统计时间内，下单成功的订单数，一个订单对应唯一一个订单号
                 <br/>
                 下单人数：统计时间内，成功下单的客户数，一人多次下单记为一人（不剔除退款订单）
                 <br/>
                 支付订单：统计时间内，成功付款的订单数，一个订单对应唯一一个订单号（拼团在成团时计入付款订单，货到付款在发货时计入付款订单，不剔除退款订单）
                 <br/>
                 支付金额(元)：统计时间内，所有付款订单金额之和（拼团在成团时计入付款金额，货到付款在发货时计入付款金额，不剔除退款金额）
                 <br/>
                 访问-下单转化率：统计时间内，下单人数/访客数
                 <br/>
                 下单-支付转化率：统计时间内，付款人数/下单人数
                 <br/>
                 访问-支付转化率：统计时间内，付款人数/访客数
                 </span>
                    </div>
                </div>
                <select name="" id="time">
                    <option value="0">昨日</option>
                    {{--<option value="1">今日</option>--!}
                    <option value="2">近一周</option>
                    <option value="3">近一个月</option>
                    <option value="4">近三个月</option>
                </select>
            </div>
            <div>
                <div class="left-data-content data_info_change">
                    <div class="left-data">
                        <div class="single-data">
                            <h4>访问人数</h4>
                            <h3>${oneDay->login_data!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>单笔下单</h4>
                            <h3>${oneDay->order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>下单人数</h4>
                            <h3>${oneDay->order_user_data!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付单数(笔数)</h4>
                            <h3>${oneDay->pay_order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付金额(元)</h4>
                            <h3>${oneDay->total_paid_money!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>付款人数</h4>
                            <h3>${oneDay->order_user_data!}</h3>
                        </div>
                    </div>
                    <div class="right-data">
                        <div class="data-img clearfix">
                            <div class="fw-app">
                                <div class="data-title">访问-支付转化率</div>
                                <div class="data-text">
                                    <#if  ($oneDay->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($oneDay->order_user_data / $oneDay->login_data * 100, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="fw-xd">
                                <div class="data-title">访问-下单转化率</div>
                                <div  class="data-text">
                                    <#if  ($oneDay->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($oneDay->order_num / $oneDay->login_data * 100, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="xd-app">
                                <div class="data-title">下单-支付转化率</div>
                                <div  class="data-text">
                                    <#if  ($oneDay->order_num == 0)
                                        -
                                    <#else>
                                        {{ round($oneDay->order_user_data / $oneDay->order_user_num * 100, 2)!}%
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="left-data-content data_info_change data_info_hide ">
                    <div class="left-data">
                        <div class="single-data">
                            <h4>访问人数</h4>
                            <h3>${today['getUv']!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>单笔下单</h4>
                            <h3>${today['generateOrderNum']!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>下单人数</h4>
                            <h3>${today['generateOrderUserNum']!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付单数(笔数)</h4>
                            <h3>${today['orderNum']!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付金额(元)</h4>
                            <h3>${today['orderUserMoney']!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>付款人数</h4>
                            <h3>${today['orderUserNum']!}</h3>
                        </div>
                    </div>
                    <div class="right-data">
                        <div class="data-img clearfix">
                            <div class="fw-app">
                                <div class="data-title">访问-支付转化率</div>
                                <div class="data-text">
                                    <#if  ($today['getUv'] == 0)
                                        -
                                    <#else>
                                        {{ round($today['orderUserNum'] / $today['getUv'] * 100, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="fw-xd">
                                <div class="data-title">访问-下单转化率</div>
                                <div  class="data-text">
                                    <#if  ($today['getUv'] == 0)
                                        -
                                    <#else>
                                        {{ round($today['generateOrderUserNum'] / $today['getUv'] * 100, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="xd-app">
                                <div class="data-title">下单-支付转化率</div>
                                <div  class="data-text">
                                    <#if  ($today['generateOrderUserNum'] == 0)
                                        -
                                    <#else>
                                        {{ round($today['orderUserNum'] / $today['generateOrderUserNum'] * 100, 2)!}%
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="left-data-content data_info_change data_info_hide">
                    <div class="left-data">
                        <div class="single-data">
                            <h4>访问人数</h4>
                            <h3>${oneWeek->login_data!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>单笔下单</h4>
                            <h3>${oneWeek->order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>下单人数</h4>
                            <h3>${oneWeek->order_user_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付单数(笔数)</h4>
                            <h3>${oneWeek->pay_order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付金额(元)</h4>
                            <h3>${oneWeek->total_paid_money!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>付款人数</h4>
                            <h3>${oneWeek->order_user_data!}</h3>
                        </div>
                    </div>
                    <div class="right-data">
                        <div class="data-img clearfix">
                            <div class="fw-app">
                                <div class="data-title">访问-支付转化率</div>
                                <div class="data-text">
                                    <#if  ($oneWeek->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($oneWeek->order_user_data * 100 / $oneWeek->login_data, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="fw-xd">
                                <div class="data-title">访问-下单转化率</div>
                                <div  class="data-text">
                                    <#if  ($oneWeek->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($oneWeek->order_user_num * 100 / $oneWeek->login_data, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="xd-app">
                                <div class="data-title">下单-支付转化率</div>
                                <div  class="data-text">
                                    <#if  ($oneWeek->order_user_num == 0)
                                        -
                                    <#else>
                                        {{ round($oneWeek->order_user_data * 100 / $oneWeek->order_user_num, 2)!}%
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="left-data-content data_info_change data_info_hide">
                    <div class="left-data">
                        <div class="single-data">
                            <h4>访问人数</h4>
                            <h3>${oneMonth->login_data!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>单笔下单</h4>
                            <h3>${oneMonth->order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>下单人数</h4>
                            <h3>${oneMonth->order_user_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付单数(笔数)</h4>
                            <h3>${oneMonth->pay_order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付金额(元)</h4>
                            <h3>${oneMonth->total_paid_money!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>付款人数</h4>
                            <h3>${oneMonth->order_user_data!}</h3>
                        </div>
                    </div>
                    <div class="right-data">
                        <div class="data-img clearfix">
                            <div class="fw-app">
                                <div class="data-title">访问-支付转化率</div>
                                <div class="data-text">
                                    <#if  ($oneMonth->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($oneMonth->order_user_data * 100 / $oneMonth->login_data, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="fw-xd">
                                <div class="data-title">访问-下单转化率</div>
                                <div  class="data-text">
                                    <#if  ($oneMonth->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($oneMonth->order_user_num * 100 / $oneMonth->login_data, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="xd-app">
                                <div class="data-title">下单-支付转化率</div>
                                <div  class="data-text">
                                    <#if  ($oneMonth->order_user_num == 0)
                                        -
                                    <#else>
                                        {{ round($oneMonth->order_user_data * 100 / $oneMonth->order_user_num, 2)!}%
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="left-data-content data_info_change data_info_hide">
                    <div class="left-data">
                        <div class="single-data">
                            <h4>访问人数</h4>
                            <h3>${threeMonth->login_data!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>单笔下单</h4>
                            <h3>${threeMonth->order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>下单人数</h4>
                            <h3>${threeMonth->order_user_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付单数(笔数)</h4>
                            <h3>${threeMonth->pay_order_num!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>支付金额(元)</h4>
                            <h3>${threeMonth->total_paid_money!}</h3>
                        </div>
                        <div class="single-data">
                            <h4>付款人数</h4>
                            <h3>${threeMonth->order_user_data!}</h3>
                        </div>
                    </div>
                    <div class="right-data">
                        <div class="data-img clearfix">
                            <div class="fw-app">
                                <div class="data-title">访问-支付转化率</div>
                                <div class="data-text">
                                    <#if  ($threeMonth->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($threeMonth->order_user_data * 100 / $threeMonth->login_data, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="fw-xd">
                                <div class="data-title">访问-下单转化率</div>
                                <div  class="data-text">
                                    <#if  ($threeMonth->login_data == 0)
                                        -
                                    <#else>
                                        {{ round($threeMonth->order_user_num * 100 / $threeMonth->login_data, 2)!}%
                                    </#if>
                                </div>
                            </div>
                            <div class="xd-app">
                                <div class="data-title">下单-支付转化率</div>
                                <div  class="data-text">
                                    <#if  ($threeMonth->order_user_num == 0)
                                        -
                                    <#else>
                                        {{ round($threeMonth->order_user_data * 100 / $threeMonth->order_user_num, 2)!}%
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

<script type="text/javascript">


    $('#time').change(function(){
        // $(this).addClass('btn_data_active').siblings().removeClass('btn_data_active');
        console.log($(this).val());
        $(this).parent().parent().find('.data_info_change').eq($(this).val()).removeClass('data_info_hide').siblings().addClass('data_info_hide');

    });
    $('.title_share').hover(function(){
        util.ajax_json('/admin/bottom/getqrcode',function(d){
            // console.log(d.content.type_url)
            if(d&&d.error==0){
                $('.qrcode').attr("src",d.content.qrcode_img);
                $('.down_qrcode').attr("href",d.content.qrcode_img);
                $('#fe_text').attr("value",d.content.type_url);
                $('.share_span').show();
            }
            else{
                util.mobile_alert(d.message);
            }
        },{});
    },function(){
        $('.share_span').hide();
    });
    $('.share_span').hover(function () {
        $(this).show();
    },function () {
        $(this).hide();
    });
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
    $('.title_type_par').hover(function () {
        $('.system_info_content').show();
        $('.system_shadow').show();
    },function () {
        $('.system_info_content').hide();
        $('.system_shadow').hide();
    });

    $('.item-image').hover(function () {
        $(this).parent().parent().find('.float-layer').show();
    },function () {
        $(this).parent().parent().find('.float-layer').hide();
    })
</script>
<#include ("system.footer")
