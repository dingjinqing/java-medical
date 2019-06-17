<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/add_coupon.css?v=1.0.8" type="text/css" />
<style type="text/css">
    body{
        padding-bottom: 40px;
    }
    .coupon_footer button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .coupon_footer button:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .prompt{
        width: 100%;
        margin-top: 20px;
        height: 40px;
        line-height: 40px;
        border: 1px solid #F2E1C8;
        background: #FFF7EC;
        color: #666;
        margin-bottom: 12px;
        padding-left: 12px;
    }
    .prompt img{
        margin-top: -2px;
    }
    .pay_two{
        margin-bottom: 20px;
        width: 100%;
        text-align: center;
        display:flex;
    }
    .pay_two a{
        display: block;
        height: 50px;
        line-height: 50px;
        background: #F8F8F8;
        color: #666;
        /* width: 50%; */
        flex:1;
        float: left;
    }
    .pay_two .pay_active{
        background: #5a8bff;
        color: #fff;
    }
    a:link,a:focus,a:hover,a:active{
        text-decoration: none;
    }
    .goods_table td, .goods_modal td, .cat_modal td{
        border-left: none;
        border-bottom: none;
    }
    .goods_img{
        border: none;
    }
    .goods_img img{
        border: 1px solid #ddd;
    }
    .sort_table div{
        margin:0 !important;
    }
    .goods_area::-webkit-scrollbar{
    width:4px;
    height:4px;
    }
    .goods_area::-webkit-scrollbar-track{
        background: #fff;
        border-radius:2px;
    }
    .goods_area::-webkit-scrollbar-thumb{
        background: #dddddd;
        border-radius:2px;
    }
    .goods_area::-webkit-scrollbar-thumb:hover{
        background: #747474;
    }
    .goods_area::-webkit-scrollbar-corner{
        background: #fff;
    }
    #card_id{
        height: 30px;
        border: 1px solid #ccc;
        padding-left: 20px;
        width: 160px;
    }
    .card_select_box a {
        display: inline-block;
        color: #5a8bff;
        margin-left: 8px;
    }
    .card-info{
        padding: 10px;
        margin-top: 10px;
        background: #f5f5f5;
        width: 410px;
    }
    .card_span {
        position: relative;
        background-color: white;
        padding: 0 3px;
        height: 22px;
        line-height: 22px;
        text-align: center;
        display: inline-block;
        margin-left: 10px;
        border: 1px solid #ccc;
    }
    .card-delete {
        position: absolute;
        right: -10px;
        top: -7px;
        cursor: pointer;
    }
    .info_top{
        position:relative;
    }
    .vip_exclusive{
        position: absolute;
        top: 10px;
        left: 10px;
        width: 70px;
        line-height: 5px;
        color: #fff;
        border-radius:15px;
        background-color: rgba(0,0,0,0.1);
        font-size: 12px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <#if ($type==1)
        <span>支付有礼</span>
        <#else>
            <span style="color: #666;">{{ trans("admin/market_manage.coupon_manage_title")!}</span>
        {{--<span style="color: #666;">${title!}</span>--!}
        </#if>
    </div>
    <div class="main-container fix_every_footer">
        <form <#if ($coupon) action="/admin/market/coupon/edit?id=${coupon->id!}&type=${type!}" <#else> action="/admin/market/coupon/add" </#if> method="post" id="form1">
            {{ csrf_field()!}
            <input type="text" name="type" value="${type!}" hidden>
            <div class="main_coupon">
                <ul class="coupon-head-ul clearfix">
                    <li><a <#if ($type == 1) href="/admin/market/payreward/list?nav=0" <#else> href="/admin/market/coupon/manage?nav=0" </#if>><#if ($type==1)全部支付有礼活动<#else>所有优惠券</#if></a></li>
                    <li><a <#if ($type == 1) href="/admin/market/payreward/list?nav=1" <#else> href="/admin/market/coupon/manage?nav=1" </#if>>进行中</a></li>
                    <li><a <#if ($type == 1) href="/admin/market/payreward/list?nav=2" <#else> href="/admin/market/coupon/manage?nav=2" </#if>>未开始</a></li>
                    <li><a <#if ($type == 1) href="/admin/market/payreward/list?nav=3" <#else> href="/admin/market/coupon/manage?nav=3" </#if>>已过期</a></li>
                    <li><a <#if ($type == 1) href="/admin/market/payreward/list?nav=4" <#else> href="/admin/market/coupon/manage?nav=4" </#if>>已停用</a></li>
                    <li  class="head-active"><a <#if ($type == 0) href="/admin/market/coupon/add" <#else> href="/admin/market/coupon/add?type=1" </#if>>
                            <#if ($type == 0)
                                ${title!}
                            <#else>
                                <#if ($coupon->id)
                                    编辑${coupon->activity_names!}活动
                                <#else>
                                    添加支付有礼活动
                                </#if>
                            </#if>
                        </a>
                    </li>
                </ul>
                <#if ($type==1)
                    <div class="prompt">
                        <img src="http://${image_domain!}/image/admin/notice_img.png">
                        <span>同一时段仅允许进行一个支付有礼活动</span>
                    </div>
                    <div class="clearfix pay_two">
                        <a href="/admin/market/coupon/add?type=1" class="pay_active">分裂优惠券</a>
                        <a href="/admin/market/payreward/coupon" target="_blank">普通优惠券</a>
                        <a href="/admin/market/lottery/payreward" link="/admin/market/lottery/payreward" class="version">幸运大抽奖</a>
                        <a href="/admin/market/payreward/payrewardurl" target="_blank">自定义</a>
                    </div>
                </#if>
                <div class="add_coupon">
                    <div class="add_coupon_content clearfix">
                        <#if ($coupon)
                            <div class="fl content_left">
                                <div class="fl_title"><div>优惠券</div></div>
                                <div class="info">
                                    <div class="info_top">
                                        <div class="vip_exclusive">会员专享</div>
                                        <div class="coupon_name">${coupon->act_name!}</div>
                                        <#if ($coupon->act_code == 'voucher')
                                            <div class="coupon_vou">￥<span>${coupon->denomination!}</span></div>
                                        <#else>
                                            <div><span>${coupon->denomination!}</span>折</div>
                                        </#if>
                                    </div>
                                </div>
                                <div class="info_mid">
                                    <div class="clearfix">
                                        <span class="sub_title">有效日期：</span>
                                        <#if ($coupon->start_time)
                                        <span class="date"><span class="start">${coupon->start_time!}</span>-<span class="end">${coupon->end_time!}</span></span>
                                        <#else>
                                            <span>领券日开始<span>${coupon->validity!}</span>日内有效</span>
                                        </#if>
                                    </div>
                                    <div>
                                        <span class="sub_title">使用限制：</span>
                                        <#if ($coupon->least_consume)
                                            <span class="least">订单满${coupon->least_consume!}元可用</span>
                                        </#if>
                                            <span class="part" <#if ($coupon->recommend_goods_id || $coupon->recommend_cat_id)style="display: inline-block;"</#if>>部分商品可用</span>
                                            <span class="all" <#if ($coupon->recommend_goods_id || $coupon->recommend_cat_id||$coupon->least_consume>0)style="display: none;"</#if>>无限制</span>
                                    </div>
                                </div>
                                <div class="info_bot">
                                    <#if ($coupon->validation_code)<div class="code">请输入领取码</div></#if>
                                    <div class="use">立即使用</div>
                                    <div>
                                        <span class="sub_title">使用说明:</span>
                                        <div class="instruction"><#if ($coupon->explain)${coupon->explain!}<#else>暂无使用说明</#if></div>
                                    </div>
                                </div>
                            </div>
                        <#else>
                        <div class="fl content_left">
                            <div class="fl_title"><div>优惠券</div></div>
                            <div class="info">
                                <div class="info_top">
                                    <div class="vip_exclusive">会员专享</div>
                                    <div class="coupon_name">优惠券名称</div>
                                    <div class="coupon_vou">￥<span>0.00</span></div>
                                    <div class="coupon_dis"><span></span>折</div>
                                </div>
                            </div>
                            <div class="info_mid">
                                <div class="clearfix">
                                    <span class="sub_title">有效日期：</span>
                                    <span class="date"><span class="start">xxxx-xx-xx xx:xx:xx</span>-<span class="end">xxxx-xx-xx xx:xx:xx</span></span>
                                    <span class="day">领券日开始<span>X</span>日内有效</span>
                                </div>
                                <div>
                                    <span class="sub_title">使用限制：</span>
                                    <span class="least" style="display: none;">订单满<span>X</span>元可用</span>
                                    <span class="all">无限制</span>
                                    <span class="part">部分商品可用</span>
                                </div>
                            </div>
                            <div class="info_bot">
                                <div class="code">请输入领取码</div>
                                <div class="use">立即使用</div>
                                <div>
                                    <span class="sub_title">使用说明:</span>
                                    <div class="instruction">暂无使用说明</div>
                                </div>
                            </div>
                        </div>
                        </#if>
                        <div class="content_right">
                            {{--分裂优惠券单独显示的部分--!}
                            <p style="color: #cccccc"  <#if ($type == 0) hidden </#if>>分裂优惠券，客户完成在线支付或货到付款，满足条件可以获得几个可以分享的优惠券，分享到朋友群，自己和朋友都可以领取。只有分裂优惠券活动关闭才可以创建新活动</p>
                            <div class="coupon_info split_coupon" <#if ($type == 0) hidden </#if>>
                                <div class="coupon_info_title">活动信息</div>
                                <ul>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            活动名称：
                                        </div>
                                        <div class="fl">
                                            <input type="text" placeholder="最多支持10个字" name="activity_names" value="${coupon->activity_names!}"/>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            活动有效期：
                                        </div>
                                        <div class="fl">
                                            <div style="margin-bottom: 10px">生效时间：
                                                <input type="text" name="act_start_time" value="${coupon->act_start_time!}" id="actstartDate"
                                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                                            </div>
                                            <div>过期时间：
                                                <input type="text" name="act_end_time" value="${coupon->act_end_time!}" id="actendDate"
                                                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                                            </div>
                                        </div>
                                    </li>
                                    {{--<style>--!}
                                        {{--.number .fl:first-child{--!}
                                            {{--width: 220px;--!}
                                            {{--text-align: right;--!}
                                        {{--}--!}
                                    {{--</style>--!}
                                    <li class="content_right_li clearfix number">
                                        <div class="fl">
                                            <em>*</em>
                                            {{--优惠券数量：--!}
                                            单次下单可分享的优惠券数量：
                                        </div>
                                        <div class="fl">
                                            <select name="cou_limit" id="">
                                                <option value="0">请选择</option>
                                                <#list ($cou_limit as $cou)
                                                    <option value="${cou!}}" <#if ($coupon->cou_limit == $cou) selected </#if>>${cou!}张</option>
                                                </#list>
                                            </select>
                                            {{--<span style="color: #999">单次下单可分享</span>--!}
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            触发条件：
                                        </div>
                                        <div class="fl">
                                            支付金额满
                                            <input type="text" name="least_money" value="${coupon->least_money!}" style="width: 70px" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                            元
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            {{--分裂优惠券单独显示的部分结束--!}
                            <div class="coupon_info">
                                <div class="coupon_info_title">优惠券基础信息</div>
                                <ul>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            优惠券名称：
                                        </div>
                                        <div class="fl">
                                            <input type="text" placeholder="最多支持10个字" name="act_name" value="${coupon->act_name!}"/>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            优惠类型：
                                        </div>
                                        <div class="fl">
                                            <div class="to_choose">
                                                <input type="radio" id="radio_type_one" name="act_code" value="voucher" <#if ($coupon->act_code == 'voucher'|| (!$coupon) )checked </#if> />
                                                <label for="radio_type_one">指定金额</label>
                                                <span style="margin-left: 25px;">
                                                    面值：<input type="text" class="ipt_short" name="denomination" <#if ($coupon->act_code == 'voucher')value="${coupon->denomination!}"</#if> onkeyup="value=value.replace(/[^\d.]/g,'')"/>元
                                                </span>
                                            </div>
                                            <div class="to_choose">
                                                <input type="radio" id="radio_type_two" name="act_code" value="discount" <#if ($coupon->act_code == 'discount')checked </#if>/>
                                                <label for="radio_type_two">折扣</label>
                                                <span>
                                                    <input type="text" class="ipt_short" name="denomination" <#if ($coupon->act_code == 'discount')value="{{floatval($coupon->denomination)!}"</#if> onkeyup="value=value.replace(/[^\d.]/g,'')"/>折
                                                </span>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            使用门槛：
                                        </div>
                                        <div class="fl">
                                            <div class="to_choose">
                                                <input type="radio" id="radio_use_one" name="use_consume_restrict" value="0" <#if ($coupon->use_consume_restrict == 0)checked </#if>  value="0"/>
                                                <label for="radio_use_one">不限制</label>
                                            </div>
                                            <div class="to_choose">
                                                <input type="radio" id="radio_use_two" name="use_consume_restrict" <#if ($coupon->use_consume_restrict == 1)checked </#if>  value="1"/>
                                                <label for="radio_use_two">
                                                    满<input type="text" class="ipt_short" name="least_consume"  value="${coupon->least_consume!}" onkeyup="value=value.replace(/[^\d.]/g,'')"/>元可用
                                                </label>
                                            </div>
                                        </div>
                                    </li>
                                    <#if ($type!=1)
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            是否需要兑换：
                                        </div>
                                        <div class="fl">
                                            <div class="to_choose">
                                                <input type="radio" name="use_score" value="0" id="use_score_zero" checked value="0"/>
                                                <label for="use_score_zero">不需要</label>&nbsp;&nbsp;&nbsp;
                                                <input type="radio" name="use_score" value="1" id="use_score_one" <#if ($coupon->use_score == 1)checked </#if>  value="0"/>
                                                <label for="use_score_one">需要兑换</label>
                                            </div>
                                            <div class="to_choose">
                                                <label class="input_score_number" <#if ($coupon->use_score != 1) hidden </#if>>
                                                    <input type="text" name="score_number" class="ipt_short" value="${coupon->score_number!}" onkeyup="value=value.replace(/[^\d.]/g,'')"/> 积分兑换
                                                </label>
                                            </div>
                                        </div>
                                    </li>
                                    </#if>
                                </ul>
                            </div>
                            <div class="coupon_info coupon_rule">
                                <div class="coupon_info_title">基本规则</div>
                                <ul>

                                    <li class="content_right_li clearfix" <#if ($type == 1) hidden  </#if>>
                                        <div class="fl">
                                            <em>*</em>
                                            每人限领：
                                        </div>
                                        <div class="fl">
                                            <select name="receive_per_person" id="">
                                                <option <#if ($coupon->receive_per_person == 0) value="0" selected </#if>>不限制</option>
                                                <option <#if ($coupon->receive_per_person == 1) value="1" selected </#if>>1</option>
                                                <option <#if ($coupon->receive_per_person == 2) value="2" selected </#if>>2</option>
                                                <option <#if ($coupon->receive_per_person == 3) value="3" selected </#if>>3</option>
                                                <option <#if ($coupon->receive_per_person == 4) value="4" selected </#if>>4</option>
                                                <option <#if ($coupon->receive_per_person == 5) value="5" selected </#if>>5</option>
                                                <option <#if ($coupon->receive_per_person == 8) value="8" selected </#if>>8</option>
                                                <option <#if ($coupon->receive_per_person == 10) value="10" selected </#if>>10</option>
                                                <option <#if ($coupon->receive_per_person == 20) value="20" selected </#if>>20</option>
                                            </select>张
                                        </div>
                                    </li>
                                    <#if (isset($goods_cards))
                                    <li class="content_right_li clearfix card_goods">
                                        <div class="fl">会员专享：</div>
                                        <div class="fl">
                                            <input type="checkbox" name="is_card_exclusive" <#if ($coupon->card_id) checked </#if> style="padding:0;border:0;vertical-align: text-top;">
                                            <span style="color:#000;line-height:30px;">用户持有会员卡才可以参与活动</span>
                                            <input type="hidden" value="${goods_have_card_str!}" name="goods_have_card_str">
                                            <input type="hidden" value="${goods_have_card_name!}" name="goods_have_card_name">
                                            <div>
                                                <div class="card_select_box">
                                                    <select name="" id="card_id">
                                                        <option value="0">请选择会员卡</option>
                                                        <#list ($goods_cards as $gc)
                                                            <option value="${gc->id!}">${gc->card_name!}</option>
                                                        </#list>
                                                    </select>
                                                    <a href="javascript:void(0)" class="refresh-card">刷新</a>&nbsp;&nbsp;|
                                                    <a href="/admin/user/member/create?top_index=5" target="_blank">新建会员卡 </a>&nbsp;&nbsp;|
                                                    <a href="/admin/user/member/list?top_index=5" target="_blank">管理会员卡 </a>
                                                </div>
                                                <div class="card-info template-0" style="display: none">
                                                    <div class="card-info-row">
                                                        <span class="card-choose">已选：</span>
                                                    </div>
                                                </div>
                                            </div> 
                                        </div>
                                    </li>
                                    </#if>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            有效期：
                                        </div>
                                        <div class="fl">
                                            <div class="to_choose">
                                                <input type="radio" id="radio_date_one" name="radio_date" <#if ($coupon->start_time || (!$coupon)) checked </#if> value="1"/>
                                                <label for="radio_date_one">固定日期</label>
                                                <div>生效时间：
                                                    <input type="text" name="start_time" value="${coupon->start_time!}" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                                                </div>
                                                <div>过期时间：
                                                    <input type="text" name="end_time" value="${coupon->end_time!}" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off"/>
                                                </div>
                                            </div>
                                            <div class="to_choose">
                                                <input type="radio" id="radio_date_two" name="radio_date" <#if ($coupon->validity) checked </#if> value="2"/>
                                                <label for="radio_date_two">
                                                    领券开始<input type="text" class="ipt_short" name="validity" value="${coupon->validity!}" onkeyup="value=value.replace(/[^\d]/g,'')"/>天内有效
                                                </label>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            发放总量：
                                        </div>
                                        <div class="fl">
                                            <input type="text" class="ipt_short" name="total_amount" value="${coupon->total_amount!}"  amount = "${coupon->giveout_amount+$coupon->receive_amount!}" onkeyup="value=value.replace(/[^\d]/g,'')"/>张
                                            <#if ($coupon->total_amount && ($coupon->giveout_amount+$coupon->receive_amount)>0)
                                                <span class="tips" style="float:none">
                                                    <#if ($type == 0)（发行总量需不小于已领取发放的总量：${coupon->giveout_amount+$coupon->receive_amount!} 张）</#if>
                                                    <#if ($type == 1)（发行总量需不小于已领取的总量：${coupon->giveout_amount+$coupon->receive_amount!} 张）</#if>
                                                </span>
                                                </#if>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix" <#if ($type == 1) hidden  </#if>>
                                        <div class="fl">
                                            领取码：
                                        </div>
                                        <div class="fl">
                                            <input type="text" class="ipt_short" name="validation_code" value="${coupon->validation_code!}"/>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            可使用商品：
                                        </div>
                                        <div class="fl">
                                            <div class="to_choose">
                                                <input type="radio" id="radio_goods_one" value="0" name="radio_goods" <#if (!$coupon->recommend_goods_id && (!$coupon->recommend_cat_id) && (!$coupon->recommend_sort_id))checked </#if>/>
                                                <label for="radio_goods_one">全部商品</label>
                                            </div>
                                            <div class="to_choose">
                                                <input type="radio" id="radio_goods_two" value="1" name="radio_goods" <#if ($coupon->recommend_goods_id || $coupon->recommend_cat_id || $coupon->recommend_sort_id)checked </#if> />
                                                <label for="radio_goods_two">指定商品</label>
                                                <div class="radio_part_goods">
                                                    <div class="choose_goods">
                                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />选择商品
                                                        <input type="hidden" name="recommend_goods_id" value="${coupon->recommend_goods_id!}">
                                                    </div>
                                                    <div class="goods_area" style="margin:0;padding-right:5px">
                                                    <#if ($coupon->recommend_goods_id)
                                                        <table class="goods_table" goods_array="${act->recommend_goods_id!}">
                                                            <thead>
                                                                <tr>
                                                                <th>商品名称</th>
                                                                <th>价格</th>
                                                                <th>库存</th>
                                                                <th>操作</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody class="tbody">
                                                                <#list ($coupon->goods_info as $item)
                                                                    <tr>
                                                                        <td>
                                                                            <div class="goods_info clearfix">
                                                                                <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                                                                <div class="goods_name">
                                                                                    ${item->goods_name!}
                                                                                </div>
                                                                            </div>
                                                                        </td>
                                                                        <td>${item->shop_price!}</td>
                                                                        <td>${item->goods_number!}</td>
                                                                        {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                                        <td><a href="javascript:void(0)" goods_id="${item->goods_id!}" class="del">删除</a></td>
                                                                    </tr>
                                                                </#list>
                                                            </tbody>
                                                        </table>

                                                    <#else>

                                                        <table class="goods_modal">
                                                            <thead>
                                                                <tr>
                                                                    <th>商品名称</th>
                                                                    <th>价格</th>
                                                                    <th>库存</th>
                                                                    <th>操作</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody class="tbody"></tbody>
                                                        </table>
                                                    </#if>
                                                    </div>
                                                    
                                                <input type="hidden" name="sort_ids" value="${coupon->recommend_sort_id!}">
                                                <div class="add_sort">
                                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                                    添加商家分类
                                                </div>
                                                <table class="sort_table" sort_array="${strategy->recommend_sort_id!}">
                                                <tr>
                                                    <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span><div class="fr" style="margin:0;margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_sort_cls">编辑</a><a href="javascript:;" class="del_sort_cat">删除</a></div></th>
                                                    <!-- <th width="30%" >操作</th> -->
                                                </tr>
                                                 </table>
                                                 <div class="add_cate" <#if ($cat_list) cat_count="1" <#else> cat_count="0"</#if>>
                                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />添加平台分类
                                                        <input type="hidden" name="recommend_cat_id" value="${coupon->recommend_cat_id!}">

                                                    </div>
                                                    
                                                    <table class="cat_table"  cat_array="${strategy->recommend_cat_id!}" width="100%">
                                                    <tr>
                                                        <th width="100%"><span>平台分类</span><div class="fr" style="margin:0;margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_cls">编辑</a><a href="javascript:;" class="del_cat">删除</a></div></th>
                                                        <!-- <th width="30%" >操作</th> -->
                                                    </tr>
                                                </table>
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="content_right_li" <#if ($type == 1) hidden </#if>>
                                        <div class="fl">
                                            是否隐藏：
                                        </div>
                                        <div class="fl coupon_hide">
                                            <span>
                                                <input type="radio" id="radio_hide" name="suit_goods" value="0" <#if (!$coupon || $coupon->suit_goods == 0)checked </#if>>
                                                <label for="radio_hide">否</label>
                                            </span>
                                            <span>
                                                <input type="radio" id="radio_show" name="suit_goods" value="1" <#if ($coupon->suit_goods == 1)checked </#if>>
                                                <label for="radio_show">是</label>
                                            </span>
                                            <span class="tips">隐藏则不显示在前端商品详情页。否则显示到前端商品详情页可以供用户领取。</span>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl">
                                            使用说明：
                                        </div>
                                        <div class="fl">
                                            <textarea name="use_explain" id="" >${coupon->use_explain!}</textarea>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="coupon_footer fix_footer">
                <button onclick="return false;" class="save">保存</button>
            </div>
        </form>
        <#if (\Session::has('error'))
            <script>util.mobile_alert('该时间段存在进行中的活动');</script>
        </#if>
    </div>
</div>

<div id="set-category">
    <#if (!$cat_list)
        <div class="no_category">
            <div>
            <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
            <p>暂无分类</p>
            </div>    
        </div>
    <#else>
    <ul>
        <#if ($cat_list)
        <#list ($cat_list as $item)
            <li class="cate_li">
                <div class="first_cate">
                    <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" />
                    <span>
                        <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                        <label for="cat_${item->cat_id!}">${item->cat_name!}(${item->goods_num!})</label>
                    </span>
                </div>
                <#if ($item->child)
                    <div class="second_cate">
                        <#list ($item->child as $sub_item)
                        <div>
                            <span>
                                <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                <label for="cat_${sub_item->cat_id!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                            </span>
                            <#if ($sub_item->child)
                                <div class="third_cate">
                                    <#list ($sub_item->child as $th_item)
                                        <span>
                                            <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                            <label for="cat_${th_item->cat_id!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                        </span>
                                    </#list>
                                </div>
                            </#if>
                        </div>
                        </#list>
                    </div>
                    </#if>
            </li>
        </#list>
        </#if>
    </ul>
    </#if>
</div>
<table  class="goods_modal_clone hide">
    <tr>
        <td></td>
        <td></td>
        <td>上架</td>
        <td><a href="javascript:void(0)"  class="del">删除</a></td>
    </tr>
</table>

<script>
    var type = "${type!}";
    var act_end_time = "${act_end_time!}";
    //$("a").attr("target",'main');
   if(($("input[name='act_name']").val() !='' && "${coupon->act_start_time!}" && '${coupon->act_start_time<= date("Y-m-d H:i:s")!}') || ($("input[name='act_name']").val() !='' && !"${coupon->act_start_time!}" )){
       var name;
       $('input').each(function(){
            name =$(this).attr("name");
           if(name !='total_amount' && name!='recommend_goods_id'&& name!='recommend_cat_id'&&name!='_token'
               &&name!='radio_goods'&& name !='cat_id[]'&&name !='suit_goods' && $(this).attr("type")!='radio'){
               $(this).prop('readonly','readonly');
               $(this).css("background","#eee")
           }
           if( $(this).attr("type")=='radio'){
               $(this).prop('disabled','true');
           }
       });
   }
   $("body").on('click','.del',function(){
       var goods_id = $(this).attr('goods_id');
       var goods = $('input[name="recommend_goods_id"]').val();
       if(isNaN(goods)) {
           var goods_array = goods.split(',');
           for (var i = 0; i < goods_array.length; i++) {
               if (goods_array[i] == goods_id) {
                   goods_array.splice(i, 1);
                   break;
               }
           }
           $('input[name="recommend_goods_id"]').val(goods_array.join());
       }
       else{
           $('input[name="recommend_goods_id"]').val('');
       }
       $(this).parent().parent().remove();
       if($('.goods_table tr').length == 1){
           $('.goods_table').hide();
       }
       check_goods_area_height();
   });
   /* $("body").on('click','.del_cat',function(){
       var cat_id = $(this).attr('cat_id');
       var cat = $('input[name="recommend_cat_id"]').val();
       if(isNaN(cat)) {
           var cat_array = cat.split(',');
           for (var i = 0; i < cat_array.length; i++) {
               if (cat_array[i] == cat_id) {
                   cat_array.splice(i, 1);
                   break;
               }
           }
           console.log(cat_array);
           $('input[name="recommend_cat_id"]').val(cat_array.join());
       }
       else{
           $('input[name="recommend_cat_id"]').val('');
       }
       $(this).parent().parent().remove();
       $("#cat_"+cat_id).prop('checked',false);
       if($('.cat_table tr').length == 1){
           $('.cat_table').hide();
       }
   }); */
   $("body").on('click','.del_cat',function(){
        $('input[name="recommend_cat_id"]').val('');
        $('.cat_table').find('tr').eq(1).remove();
        $('#set-category ul input[type="checkbox"]:checked').each(function(){
            $(this).prop('checked',false);
        });
        $('.cat_table').hide();
    })
   //左边同步显示
   $("input[name='act_name']").change(function(){
       $('.coupon_name').text($(this).val());
   });
   $("input[name='validation_code']").change(function(){
       if($(this).val() == ''){
           $(".code").hide();
       }
       else{
           $(".code").show();
       }
   });
   $("input[name='denomination']").change(function() {
       var value = Number($(this).val());
       var act_code = $(this).parent().parent().find('[name="act_code"]').val();
       if (act_code == 'voucher') {
           $(".coupon_vou span").text(value.toFixed(2));
       } else {
           $(".coupon_dis span").text((value).toFixed(2));
       }
   });
   $('input[name="act_code"]').change(function(){
       if($(this).val()=='voucher'){
           $(".coupon_vou").show();
           $(".coupon_dis").hide();
       }
       else{
           $(".coupon_vou").hide();
           $(".coupon_dis").show();
       }
   });
   $("input[name='start_time']").blur(function(){
       $('.start').text($(this).val());
   });
   $("input[name='end_time']").blur(function(){
       $('.end').text($(this).val());
   });
   $("input[name='validity']").change(function(){
       $('.day span').text($(this).val());
   });
   $("input[name='radio_date']").change(function(){
       if($(this).val()==1){
           $('.date').show();
           $('.day').hide();
       }
       else{
           $('.date').hide();
           $('.day').show();
       }
   });
   $("textarea[name='use_explain']").change(function(){
       $('.instruction').text($(this).val());
   });
   $("input[name='radio_goods']").change(function(){
       if($(this).val()==1){
           $('.part').show();
           $('.all').hide();
       }
       else{
           $('.part').hide();
           if($("input[name='use_consume_restrict']").val()==0){
               $('.all').show();
           }
       }
   });
   $("input[name='use_consume_restrict']").change(function(){
       if($(this).val()==1){
           $('.least').show();
           $('.all').hide();
       }
       else{
           $('.least').hide();
           if($("input[name='radio_goods']").val()==0){
               $('.all').show();
           }
       }
   });
   $("input[name='least_consume']").change(function(){
       $(".least span").text($(this).val());
   });

    $("input[name='denomination']:eq(1)").blur(function () {
        if($("input[name='denomination']:eq(1)").val() != ""){
            if($(this).val()<1 || $(this).val()>10){
                util.mobile_alert('请填写1-10之间的数字');
                $(this).val("");
                $(this).focus();
            }
        }
    });
    var cat_id = '${coupon->recommend_cat_id!}';
    var goods_id = '${coupon->recommend_goods_id!}';
    var hasSaved = true;

    $('[name="use_score"]').click(function () {
        var hasSaved = true;
        if ($(this).val() == 1) {
            $('.input_score_number').css('display','block');
        } else {
            $('.input_score_number').css('display','none');
        }
    })

    let card_arry = [];
    console.log($('input[name="goods_have_card_str"]').val());
    if ($('input[name="goods_have_card_str"]').val()){
        card_arry = $('input[name="goods_have_card_str"]').val().split(',');
        var card_name = $('input[name="goods_have_card_name"]').val().split(',');
        for (var i in card_arry){
            var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
            var span =' <span class="card_span">';
            var inner_html = span + '<span value="'+card_arry[i]+'">'+ card_name[i] + '</span>' + img + '</span>';
            $(".card-info-row").append(inner_html);
            $('#card_id').children("option[value='"+card_arry[i]+"']").remove();
            // if(!in_array(all_label[i].id,label_arry)){
            //     html+='<option value='+all_label[i].id+'>'+all_label[i].name+'</option>';
            // }
        }
        $('.card-info').show();
    }
    $('.card-info-row').on('click','.card-delete',function(){
        var op_name = $(this).parent().html();
        var opp_val = $(this).prev().attr("value");
    $(this).parent().remove();
    var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
    $('#card_id').append(op_html);
        card_arry.splice($.inArray(opp_val,card_arry),1);
        $('input[name="goods_have_card_str"]').val(card_arry.join(','));
        if(card_arry.length == 0){
            $('.card-info').hide();
        }
    });
    $('#card_id').change(function(){
        var card_name = $(this).children('option:selected').html();
        var card_val = $(this).children('option:selected').attr('value');
        if(card_arry.length == 0){
            $('.card-info').show();
        }
        card_arry.push(card_val);
        $('input[name="goods_have_card_str"]').val(card_arry.join(','));
        var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
        var span =' <span class="card_span">';
        var inner_html = span + '<span value="'+card_val+'">'+ card_name + '</span>' + img + '</span>';
        $(this).parent().parent().find(".card-info-row").append(inner_html);
        $(this).children('option:selected').remove();
    })
    if($("input[name='is_card_exclusive']").is(":checked")){
        $('.card_select_box').css("display",'block');
        if($('.card-info-row').find('.card_span').length > 0){
            $('.card-info').css("display",'block');
        }
    }else{
        $('.card_select_box').css("display",'none');
        $('.card-info').css("display",'none');      
    }

    $("input[name='is_card_exclusive']").change(function () {
        if($("input[name='is_card_exclusive']").is(":checked")){
            $('.card_select_box').css("display",'block');
            $('.vip_exclusive').show();
            if($('.card-info-row').find('.card_span').length > 0){
                $('.card-info').css("display",'block');
            }
        }else{
            $('.card_select_box').css("display",'none');
            $('.card-info').css("display",'none');   
            $('.vip_exclusive').hide();
        }
    })
    if($("input[name='is_card_exclusive']").is(":checked")){
        $('.vip_exclusive').show();        
    } else {
        $('.vip_exclusive').hide();
    }
    $('.refresh-card').click(function () {
        util.ajax_json('/admin/ajax/card/exclusive',function(d){
            if(d.error!=0){
                util.mobile_alert('刷新失败');
                return false;
            }
            else{
                var all_card = d.content.cards;
                var html = '<option value="0"  selected="selected" >请选择会员卡</option>';
                for (var i in all_card){
                    html+='<option value='+all_card[i].id+'>'+all_card[i].card_name+'</option>';
                }
                $('#card_id').html(html);
                for (var j in card_arry){
                    $('#card_id').children("option[value='"+card_arry[j]+"']").remove();
                }
                util.mobile_alert('刷新成功');
            }
        },{is_goods:0,is_not_time:1});
    });
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/add_coupon.js?v=1.1.7"></script>
<script type="text/javascript" src="/js/admin/select_sort.js?v=1.0.2"></script>
<script>
    $(".fix_footer").outerWidth($('.fix_every_footer').width());

    $('input[name="radio_goods"]').click(function () {
       if($(this).val() == 0){
           $('.radio_part_goods').hide();
       }
        if($(this).val() == 1){
            $('.radio_part_goods').show();
        }
    });
    if($('input[name="radio_goods"]:checked').val() == 0){
        $('.radio_part_goods').hide();
    }else{
        $('.radio_part_goods').show();
    }
    function check_goods_area_height(){
        let goods_modal = $('.goods_modal').outerHeight();
        let goods_table = $('.goods_table').outerHeight();
        if( goods_table > 300 || goods_modal > 300){
            $('.goods_area').css({
                'height': '300px',
                'overflow-y': 'scroll',
            })
        } else {
            $('.goods_area').css({
                'height': 'auto',
                'overflow-y': 'auto',
            })
        }
    }
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    var disabled = '${coupon->act_name!}';
    //版本控制
    var type = "${type!}";
    if(type == 1){
        // getPowerInfo('main_config','coupon_split','sub_4','分裂优惠券',0);
        getPowerInfo('main_config','pay_reward','sub_4','支付有礼',0);
    }
    $(".version").click(function () {
        var _html = '抽奖';
        var mod = 'lottery';
        var url = $(this).attr("link");
        $(this).attr('href','##');
        $(this).removeAttr('target');
        if(getAuthorityDetail(0,url) == 0){
            var data = {};
            data = getPowerInfo('main_config',mod,'sub_4',_html);
            if(data.content == 1){
                $(this).attr('href',url);
                $(this).attr('target','_blank');
            }
        }
    });

</script>