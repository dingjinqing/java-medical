<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/overview.css?v=1.0.9" type="text/css" />
<style type="text/css">
    .title_share {
        width: 120px;
    }
    .ov_head_title {
        position: relative;
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
    .title_share .share_span{
        right:10px;
        z-index: 99;
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
    .one_piece{
        float: right;
        font-size: 14px;
        color: #333;
        font-weight: normal;
    }
    .one_piece a{
        border: 1px solid #5a8bff;
        color: #5A8BFF;
        padding: 5px 10px;
        border-radius: 2px;
        margin-left: 10px;
    }
    .left-one{
        padding-top: 20px;
    }
    #set_official_account{

        display:none
    }
    .off-area{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        color: #666;
    }
    #set_official_account img{
        width: 80px;
        height: 80px;
    }
    .custom_title{
        float: none;
        margin-left: 20px;
    }
</style>
<div class="title">
    <div class="fl">
        <#if ($shop->shop_avatar)
            <img src="${shop->shop_avatar!}" class="shop_defu" style="width: 44px;border: 1px solid #fff;"/>
        <#else>
            <img src="http://${image_domain!}/image/admin/shop_def_y.png" class="shop_defu" style="border: 1px solid #fff"/>
        </#if>
        <#if ($shop->business_state && $time_flag == 1)
            <img src="http://${image_domain!}/image/admin/img_home/type_open.png" class="shop_type" />
        <#elseif>(!$shop->business_state)
            <img src="http://${image_domain!}/image/admin/img_home/type_none.png" class="shop_type" />
        <#elseif>($time_flag == 0)
            <img src="http://${image_domain!}/image/admin/img_home/type_over.png" class="shop_type" />
        </#if>
        <span>
            <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" />
            <span><#if ($shop->business_state && $time_flag == 1) 营业  <#elseif>(!$shop->business_state) 未营业 <#elseif>($time_flag == 0) 已过期  </#if> </span>
        </span>

    </div>
    <span>${shop->shop_name!}</span>
    <span class="title_type_par">
        <span class="title_type"><#if ($version_array[$shop->shop_type])${version_array[$shop->shop_type]!} <#else> 无版本 </#if></span>
        <img src="http://${image_domain!}/image/admin/system_shadow.png" class="system_shadow" />
        <div class="system_info_content">
            <div class="system_info_content_top">
                当前版本为<span class="version_name">基础版</span>，有效期至：<span class="expire_time">2018年1月20日</span>
            </div>
            <div class="system_info_content_bottom">
                <a href="/admin/version/notice">我要续费</a>
                <a href="/admin/version/notice">版本升级</a>
            </div>
        </div>
    </span>

    <div class="title_share">
        <img src="http://${image_domain!}/image/admin/img_home/share_shop.png" alt="" />
        分享店铺
        <span class="share_span">
            <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" class="share_sj" />
            <span class="share_span_top">
                <span>扫一扫，分享给好友吧~</span>
                <img class="qrcode" src="http://${image_domain!}/image/system/qrcode.png" alt="" width="120px" />
                <a href="##" download="" class="down_qrcode">下载二维码</a>
            </span>
            <span class="share_link">
                <input type="text" value="" id="fe_text" />
                <button class="btn_copy" id="d_clip_button" data-clipboard-target="fe_text">复制</button>
            </span>
        </span>
    </div>
</div>
<div class="main-container">
    <div class="over-left">
        <div class="left-one">
            <div class="left-title">待办事项
                <span class="custom_title">自定义</span>
                <div class="one_piece">

                    <#if  ($account_info->is_bind)
                        <span>当前账号已关注公众号，可实时接收消息通知</span>
                        <a href="##" class="bind_action" act="del_bind">解除绑定</a>
                    <#elseif> ($account_info->official_open_id)
                        <span>当前账号已关注公众号，但未绑定，不能实时接收消息通知</span>
                        <a href="##" class="bind_action" act="bind">去绑定</a>
                    <#else>
                        <span>关注公众号，实时接收消息通知</span>
                        <a href="##" class="btn_follow">关注</a>
                    </#if>
                </div>
            </div>
            <div class="left-order-content">
                <#list ($backlog as $v)
                    <div class="new_order" data-index="${v!}">
                        <a href="" link="${backlog_link[$v]!}">
                            <div class="order_top">
                                ${backlog_num[$v] ?? 0!}
                            </div>
                            <p>${backlog_content[$v]!}</p>
                        </a>
                    </div>
                </#list>
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
                 访问人数：统计时间内，店铺所有页面（包括店铺主页、单品页、会员主页等）被访问的去重人数，一个人在统计时间范围内访问多次只记为一个
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
                    <option value="0">今日</option>
                    <option value="1">昨日</option>
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
        <div class="left-three">
            <div class="left-title">功能推荐</div>
            <div class="function-content">
                <a class="single-func"  title="拼团" val="pin_group" href="/admin/market/pingroup/list?nav=1&top_index=4" link="/admin/market/pingroup/list?nav=1&top_index=4" target="_blank">
                    <img src="http://${image_domain!}/image/admin/new_ov/drpt.png" alt="多人拼团">
                    <span>多人拼团</span>
                </a>
                <a class="single-func" title="分销" val="distribution" href="/admin/market/distribution/config?top_index=4" link="/admin/market/distribution/config?top_index=4" target="_blank">
                    <img src="http://${image_domain!}/image/admin/new_ov/fx.png" alt="分销">
                    <span style="position: relative">
                        分销
                          <img style="position: absolute;left: 40px" src="http://${image_domain!}/image/admin/new_ov/Hot.png" alt="hot">
                    </span>

                </a>
                <a class="single-func" title="瓜分" val="pin_integration" href="/admin/market/integration/list?top_index=4&nav=1" link="/admin/market/integration/list?top_index=4&nav=1" target="_blank">
                    <img src="http://${image_domain!}/image/admin/new_ov/gfjf.png" alt="瓜分积分">
                    <span>瓜分积分</span>
                </a>
                <a class="single-func" title="活动有礼" val="activity_reward" href="/admin/market/activityreward/list?nav=1&top_index=4" link="/admin/market/activityreward/list?nav=1&top_index=4" target="_blank">
                    <img src="http://${image_domain!}/image/admin/new_ov/hdyl.png" alt="活动有礼">
                    <span>活动有礼</span>
                </a>
                <a title="砍价" class="single-func" val="bargain" href="/admin/market/bargain/list?nav=1&top_index=4" link="/admin/market/bargain/list?nav=1&top_index=4" target="_blank">

                    <img src="http://${image_domain!}/image/admin/new_ov/kj.png" alt="砍价">
                    <span>砍价</span>

                </a>
                <a class="single-func"  title="拼团抽奖" val="group_draw" href="/admin/market/groupdraw/list?top_index=4&nav=1" link="/admin/market/groupdraw/list?top_index=4&nav=1" target="_blank">
                    <img src="http://${image_domain!}/image/admin/new_ov/ptcj.png" alt="拼团抽奖">
                    <span style="position: relative">
                        拼团抽奖
                          <img style="position: absolute;left: 70px" src="http://${image_domain!}/image/admin/new_ov/Hot.png" alt="hot">
                    </span>
                </a>
                <a class="single-func" title="抽奖" val="lottery" href="/admin/market/lottery/list?top_index=4&nav=1" link="/admin/market/lottery/list?top_index=4&nav=1" target="_blank">
                    <img src="http://${image_domain!}/image/admin/new_ov/xydcj.png" alt="幸运大抽奖">
                    <span>幸运大抽奖</span>
                </a>
                <a class="single-func" title="支付有礼" val="pay_reward" href="/admin/market/payreward/list?nav=1&top_index=4" link="/admin/payreward/list?nav=1&top_index=4" target="_blank">
                    <img src="http://${image_domain!}/image/admin/new_ov/zfyl.png" alt="支付有礼">
                    <span>支付有礼</span>
                </a>
            </div>
        </div>
    </div>

    <div class="over-right">
        <div class="right-one">
            <div class="right-title">
                <span>公告</span>
                <a href="/admin/notice/list" class="gengduo">更多<img src="http://${image_domain!}/image/admin/new_ov/go.png"></a>
            </div>
            <div class="one-zx">
                <#list ($notice_acticle as $na)
                    <div class="single-zx">
                        <span class="circle"></span>
                        <a class="zx-text" href="/admin/notice/show?article_id=${na->article_id!}">${na->title!}</a>
                        <span class="zx-time">${na->create_time!}</span>
                    </div>
                </#list>
            </div>
        </div>
        <div class="right-two">
            <div class="right-title">更多服务</div>
            <div class="two-zx">
                <a class="single-icon"  href="http://www.wangdian.cn/pc/erpCompany.html" link="http://www.wangdian.cn/pc/erpCompany.html" target="_blank">
                    <div class="icon-img">
                        <img src="http://${image_domain!}/image/admin/new_ov/wangdian.png" alt="旺店通ERP">
                    </div>
                    <div class="icon-name">旺店通ERP</div>
                </a>
                <a class="single-icon"  href="http://pos.vpubao-mall.com/" link="http://pos.vpubao-mall.com/" target="_blank">
                    <div class="icon-img">
                        <img src="http://${image_domain!}/image/admin/new_ov/pos.png" alt="微铺宝POS">
                    </div>
                    <div class="icon-name">微铺宝POS</div>
                </a>
                <a class="single-icon"  href="http://www.ekbyun.com/" link="http://www.ekbyun.com/" target="_blank">
                    <div class="icon-img">
                        <img src="http://${image_domain!}/image/admin/new_ov/ekuai.png" alt="E快帮ERP">
                    </div>
                    <div class="icon-name">E快帮ERP</div>
                </a>
                <a class="single-icon"  href="http://www.wangdian.cn/pc/data.html" link="http://www.wangdian.cn/pc/data.html" target="_blank">
                    <div class="icon-img">
                        <img src="http://${image_domain!}/image/admin/new_ov/dashuju.png" alt="大数据">
                    </div>
                    <div class="icon-name">大数据</div>
                </a>
                <a class="single-icon"  href="http://www.wangdian.cn/pc/o2o.html" link="http://www.wangdian.cn/pc/o2o.html" target="_blank">
                    <div class="icon-img">
                        <img src="http://${image_domain!}/image/admin/new_ov/020.png" alt="O2O">
                    </div>
                    <div class="icon-name">O2O</div>
                </a>
                <a class="single-icon"  href="http://www.wangdian.cn/pc/wms.html" link="http://www.wangdian.cn/pc/wms.html" target="_blank">
                    <div class="icon-img">
                        <img src="http://${image_domain!}/image/admin/new_ov/wms.png" alt="旺店通WMS">
                    </div>
                    <div class="icon-name">旺店通WMS</div>
                </a>
            </div>
        </div>
    </div>
</div>

{{--关注公众号的弹窗--!}
<div id="set_official_account">
    <div class="off-area">
        <div style="margin: 24px auto;">用手机扫下方二维码关注公众号，及时接收新订单提醒。</div>
        <img src="${thirdPartCode!}" alt="">
    </div>
</div>
<div id="set_custom">
    <p class="tips">需选择5个待办事项</p>
    <ul class="custom_show_box clearfix">
        <#list ($backlog_content as $ky=>$va)
            <li><input type="checkbox" name="options" value="${ky!}" <#if (in_array($ky,$backlog)) checked </#if>>${backlog_content[$ky]!}</li>
        </#list>
        {{--<li><input type="checkbox" name="options" value="2">待处理退款退货</li>--!}
        {{--<li><input type="checkbox" name="options" value="3">已售罄商品</li>--!}
        {{--<li><input type="checkbox" name="options" value="4">商品评价待审核</li>--!}
        {{--<li><input type="checkbox" name="options" value="5">待提货订单</li>--!}
        {{--<li><input type="checkbox" name="options" value="6">分销员待审核</li>--!}
        {{--<li><input type="checkbox" name="options" value="7">会员卡激活待审核</li>--!}
        {{--<li><input type="checkbox" name="options" value="8">分销提现待审核</li>--!}
        {{--<li><input type="checkbox" name="options" value="9">服务评价待审核</li>--!}
    </ul>
</div>
<div id="custom_clone">
    <#list ($backlog_content as $key=>$val)
        <div class="new_order" data-index="${key!}">
            <a href="" link="${backlog_link[$key]!}">
                <div class="order_top">
                    ${backlog_num[$key] ?? 0!}
                </div>
                <p>${backlog_content[$key]!}</p>
            </a>
        </div>
    </#list>
    {{--<div class="new_order" data-index="1">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--<#if ($order_status['refund_status']) ${order_status['refund_status']!} <#else> 0 </#if>--!}
            {{--</div>--!}
            {{--<p>待发货订单</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="2">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--<#if ($order_status['refund_status']) ${order_status['refund_status']!} <#else> 0 </#if>--!}
            {{--</div>--!}
            {{--<p>退款退货订单</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="3">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--92929--!}
            {{--</div>--!}
            {{--<p>已售罄商品</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="4">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--100000--!}
            {{--</div>--!}
            {{--<p>商品评价待审核</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="5">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--<#if ($order_status['ziti_num']) ${order_status['ziti_num']!} <#else> 0 </#if>--!}
            {{--</div>--!}
            {{--<p>待提货订单</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="6">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--100000--!}
            {{--</div>--!}
            {{--<p>分销员待审核</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="7">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--100000--!}
            {{--</div>--!}
            {{--<p>会员卡激活待审核</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="8">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--100000--!}
            {{--</div>--!}
            {{--<p>分销提现待审核</p>--!}
        {{--</a>--!}
    {{--</div>--!}
    {{--<div class="new_order" data-index="9">--!}
        {{--<a href="">--!}
            {{--<div class="order_top">--!}
                {{--100000--!}
            {{--</div>--!}
            {{--<p>服务评价待审核</p>--!}
        {{--</a>--!}
    {{--</div>--!}
</div>
<div id="card_select" style="display: none;padding: 20px 0px;padding-left: 36px;   border-bottom: 1px solid rgb(238, 238, 238)">
    <select name="select_id" id="" class="cat_select" style="width: 200px; height: 30px;">
        <option value=""  selected>请选择会员卡</option>
        <#list ($card_list as $card)
            <option value="${card->id!}" >${card->card_name!}</option>
        </#list>
    </select>
</div>
<div id="store_select" style="display: none;padding: 20px 0px;padding-left: 36px;   border-bottom: 1px solid rgb(238, 238, 238)">
    <select name="select_id" id="" class="cat_select" style="width: 200px; height: 30px;">
        <option value=""  selected>请选择门店</option>
        <#list ($store_list as $store)
            <option value="${store->store_id!}" >${store->store_name!}</option>
        </#list>
    </select>
</div>
<script type="text/javascript">
            {{--console.log(${today!});--!}
    var param = {
            config_name: 'main_config',
            mod_name : 'basic_yesterday',
            sub_name: 'sub_0'
        };
    util.ajax_json('/admin/version/judgment',function(d){
        var version_name = d.message.version_name;
        var expire_time = d.message.expire_time;
        $('.version_name').html(version_name);
        $('.expire_time').html(expire_time);
    },param);

    $('.title .fl').hover(function(){
        $(this).find('span').show();
    },function(){
        $(this).find('span').hide();
    });
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
    $('.custom_title').click(function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['自定义待办事项', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['500px', 'auto']
                , content: $('#set_custom') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    if($('input[name="options"]:checked').length != 5){
                        util.mobile_alert('请选择5项待办事项');
                        return false;
                    }
                    var content = [];
                    $('input[name="options"]:checked').each(function (i) {
                        content.push($('input[name="options"]:checked').eq(i).val())
                    })
                    util.ajax_json('/admin/survey/overview',function (res) {
                        if(res && res.error == 0){
                            $('.left-order-content').html('');
                            [].forEach.call($('input[name="options"]:checked'),(el) => {
                                let i = el.value;
                                let cl_el = $('#custom_clone').children('[data-index="'+i+'"]').clone(true);
                                $('.left-order-content').append(cl_el)
                            })
                        }else{
                            util.mobile_alert("修改失败");
                        }
                    },{backlog:content.join(",")})
                    layer.close(index);
                }
                ,btn2: function(index, layero){
                    layer.close(index);
                    location.reload();
                }
            });
        });
    })
    $('.new_order').click(function(){
        var _this = $(this);
        $(this).find("a").attr('href','##');
        $(this).find("a").removeAttr('target');
        if(_this.find("a .order_top").text().trim()!=0){
            if($(this).attr('data-index') == 7 || $(this).attr('data-index') == 9){
                if($(this).attr('data-index') == 7){var con = $('#card_select'); var title='会员卡';}
                if($(this).attr('data-index') == 9){var con = $('#store_select'); var title='门店';}
                layui.use('layer', function() { //独立版的layer无需执行这一句
                    var $ = layui.jquery, layer = layui.layer;
                    layer.open({
                        type: 1
                        , title: ['请选择'+title, 'text-align:center;padding: 0px;']
                        , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                        , area: '300px'
                        , content: con //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        , btn: ['确定', '取消']
                        , btnAlign: 'r' //按钮居右
                        , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                        ,yes: function (index) {
                            if(con.find('select[name="select_id"]').val()==''){
                                util.mobile_alert('请选择要审核的'+title);
                                return false;
                            }else{
                                if(_this.attr('data-index') == 7){
                                    window.location.href = _this.find("a").attr('link')+'?nav=1&card_id='+con.find('select[name="select_id"]').val();
                                }
                                if(_this.attr('data-index') == 9){
                                    window.location.href = _this.find("a").attr('link')+'?nav=1&store_id='+con.find('select[name="select_id"]').val();
                                }
                            }
                        }
                    });
                });
            }else{
                window.location.href = _this.find("a").attr("href",_this.find("a").attr('link'));
            }
        }else{
            util.mobile_alert("没有待办事项");
            return false;
        }

    })

//    关注公众号
    $('.btn_follow').click(function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['关注公众号', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['380px', '205px']
                , content: $('#set_official_account') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            });
        });
    })
</script>
<script language="JavaScript" src="/js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript">
    //复制
    // 定义一个新的复制对象
    var clip = new ZeroClipboard( document.getElementById("d_clip_button"), {
        moviePath: "ZeroClipboard.swf"
    });
    // 复制内容到剪贴板成功后的操作
    clip.on( 'copy', function(client, args) {
        util.mobile_alert("复制成功");
    });
    {{--var flag = 0;--!}
    {{--var nowTime = Number(new Date());--!}
    {{--console.log(nowTime);--!}
    {{--var expireTime = "${expire_time!}";--!}
    {{--if(nowTime < expireTime) {--!}
    {{--flag = 1;--!}
    {{--} else {--!}
    {{--flag = 0;--!}
    {{--console.log(flag);--!}
    {{--}--!}
    $('.bind_action').click(function () {
        var act = $(this).attr('act');
        if (act == 'del_bind') {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">请确认是否真的解绑?</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    operateBind(act, function (res) {
                        if (res) {
                            layer.close(index);
                            window.location.reload();
                        }
                    });
                });
            });
        } else {
            operateBind(act ,function (res) {
                window.location.reload();
            });
        }
    });
    function operateBind(act, cb) {
        util.ajax_json('/admin/survey/official/bind', function (res) {
            if (res.error == 0) {
                util.mobile_alert('操作成功');
                cb(true);
            } else {
                util.mobile_alert(res.message);
                cb(false);
            }
        }, {act:act});
    }
</script>
<#include "/admin/footer.ftl">
