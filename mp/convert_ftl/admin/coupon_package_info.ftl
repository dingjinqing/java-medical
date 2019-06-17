<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/add_coupon.css?v=1.0.8" type="text/css" />
<link rel="stylesheet" href="/css/admin/coupon_package_manage.css?v=1.0.0" type="text/css" />
<style type="text/css">
    input[type='number']{
        height: 30px;
        line-height: 30px;
    }
    .cl_content{
        overflow-y:scroll;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">优惠券礼包</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <form action="" method="post" id="form1">
            {{ csrf_field()!}
            <input type="hidden" id="act_id" name="act_id" value="${couponPack->id!}">
            <div class="main_coupon main_package">
                <ul class="coupon-head-ul clearfix">
                    <li <#if ($nav_type==0)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=0">全部优惠券礼包</a></li>
                    <li <#if ($nav_type==1)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=1">进行中</a></li>
                    <li <#if ($nav_type==2)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=2">未开始</a></li>
                    <li <#if ($nav_type==3)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=3">已过期</a></li>
                    <li <#if ($nav_type==4)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=4">已停用</a></li>
                    <#if ($couponPack->id > 0)
                        <li class="head-active"><a href="#">编辑优惠券礼包</a></li>
                    <#else>
                        <li class="head-active"><a href="#">添加优惠券礼包</a></li>
                    </#if>
                </ul>
                <div class="add_coupon">
                    <div class="add_coupon_content clearfix">
                        <div class="fl content_left">
                            <div class="cl_title">优惠券礼包</div>
                            <div class="cl_content">
                                <div class="cl_bg">
                                    <img src="http://${image_domain!}/image/admin/cou_package_bg1.png" alt="">
                                </div>
                                <div class="cleft_neirong">
                                    <div class="title_word"><#if ($couponPack->pack_name) ${couponPack->pack_name!} <#else> 优惠大礼包 </#if></div>
                                    <div class="cle_content">
                                        <div class="youhui_area">
                                            <div class="ya_title">
                                                <div class="yt_hr"><img src="http://${image_domain!}/image/admin/cou_left_icon.png" alt=""></div>
                                                <div class="yt_word">优惠券礼包</div>
                                                <div class="yt_hr"><img src="http://${image_domain!}/image/admin/cou_right_icon.png" alt=""></div>
                                            </div>
                                            <div class="yhq_content clearfix" <#if ($couponPackVoucher)  <#else> hidden </#if>>
                                                <div class="each_yhq clearfix hide" id="coupon_img_exp">
                                                    <div class="ey_left">
                                                        <div class="el_price">￥<text>400</text></div>
                                                        <div class="el_rule">满299元可用</div>
                                                    </div>
                                                    <div class="ey_middle"><img src="http://${image_domain!}/image/admin/cou_midd_icon.png" alt=""></div>
                                                    <div class="ey_righr">
                                                        <div class="cou_names"></div>
                                                        <div class="cou_limits"><text></text><img src="http://${image_domain!}/image/wxapp/right_into.png" alt=""></div>
                                                        <div class="cou_time">2019.04.25-2019.05.08</div>
                                                        <div class="num_icon">0张</div>
                                                    </div>
                                                </div>
                                                <#if ($couponPackVoucher)
                                                <#list ($couponPackVoucher as $item)
                                                    <div class="each_yhq clearfix" id="coupon_img_clone_${item->voucher_id!}">
                                                        <div class="ey_left">
                                                            <div class="el_price">￥<text>${item->denomination!}</text></div>
                                                            <div class="el_rule">
                                                                <#if ($item->least_consume > 0)
                                                                    满${item->least_consume!}使用
                                                                <#else>
                                                                    无限制
                                                                </#if>
                                                            </div>
                                                        </div>
                                                        <div class="ey_middle"><img src="http://${image_domain!}/image/admin/cou_midd_icon.png" alt=""></div>
                                                        <div class="ey_righr">
                                                            <div class="cou_names">${item->act_name!}</div>
                                                            <div class="cou_limits"><text>
                                                                    <#if ($item->recommend_goods_id>0 || $item->recommend_cat_id>0 || $item->recommend_sort_id>0 || $item->recommend_product_id>0) 部分商品可用 <#else> 全部商品可用 </#if>
                                                                </text><img src="http://${image_domain!}/image/wxapp/right_into.png" alt=""></div>
                                                            <div class="cou_time">
                                                                <#if ($item->validity > 0)
                                                                    领券开始${item->validity!}天内有效
                                                                <#else>
                                                                    {{substr($item->start_time,0,10)!}-{{substr($item->end_time,0,10)!}
                                                                </#if>
                                                            </div>
                                                            <div class="num_icon">${item->total_amount!}张</div>
                                                        </div>
                                                    </div>
                                                </#list>
                                                </#if>
                                            </div>
                                            <div class="btn_get">立即领取</div>
                                        </div>
                                        <div class="rule_area">
                                            活动规则:
                                            <div class="rule_infos" style="white-space:pre"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="content_right">
                            <div class="coupon_info">
                                <div class="coupon_info_title">基础设置</div>
                                <ul>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>活动名称：</div>
                                        <div class="fl">
                                            <input type="text" placeholder="最多支持10个字" name="act_name" class="act_name" value="${couponPack->act_name!}">
                                            <span class="some_tips">只作为商家记录使用，用户不会看到这个名称</span>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>有效期：</div>
                                        <div class="fl">
                                            <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${couponPack->start_time!}"/>
                                            &nbsp; 至&nbsp;&nbsp;
                                            <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${couponPack->end_time!}"/>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>礼包名称：</div>
                                        <div class="fl">
                                            <input type="text" placeholder="最多支持8个字" name="pack_name" class="package_name" value="${couponPack->pack_name!}" maxlength="8">
                                            <span class="some_tips">展示在小程序活动页，最多可填写8个汉字</span>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>礼包内容：</div>
                                        <div class="fl">
                                            <div class="btn_add_cou">
                                                <a href="##" class="btn_add_coupons">添加优惠券</a>
                                                <span class="some_tips">最多可添加10种优惠券，每种优惠券最多送6张 </span>
                                            </div>
                                        </div>
                                    </li>
                                    <input type="hidden" id="coupon_ids" name="coupon_ids" value="${couponIdsStr!}">
                                    <li class="content_right_li clearfix">
                                        <div class="crl_content">
                                            <div class="cou_area">
                                                <table width="100%" class="coupon_list_tbl" <#if ($couponPackVoucher)  <#else> hidden </#if>>
                                                    <thead>
                                                    <tr>
                                                        <td>优惠券信息</td>
                                                        <td>发券数量</td>
                                                        <td>发放策略</td>
                                                        <td>操作</td>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr class="coupon_tr hide">
                                                        <input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info">
                                                        <td class="cou_message">
                                                            <div class="cou_name">优惠券名称优惠券名称优惠券名称</div>
                                                            <div class="cou_money">￥100</div>
                                                            <div class="cou_limit">无限制</div>
                                                        </td>
                                                        <td>
                                                            <input type="number" class="total_amount" name="total_amount" onblur="updateLeftTotalmount(this)">
                                                        </td>
                                                        <td>
                                                            <a href="##" class="btn_cs_reset">设置</a>
                                                        </td>
                                                        <td>
                                                            <a href="##" class="btn_del_cs" onclick="deleteTr(this)">删除</a>
                                                        </td>
                                                    </tr>
                                                    <#if ($couponPackVoucher)
                                                    <#list ($couponPackVoucher as $item)
                                                        <tr class="" id="coupon_tr_${item->voucher_id!}" immediately_grant_amount="${item->immediately_grant_amount!}" timing_every="${item->timing_every!}" timing_unit="${item->timing_unit!}" timing_time="${item->timing_time!}" timing_amount="${item->timing_amount!}" pack_voucher_id="${item->id!}">
                                                            <input type="hidden" coupon_id="${item->voucher_id!}" act_code="${item->act_code!}" denomination="${item->denomination!}" class="coupon_info">
                                                            <td class="cou_message">
                                                                <div class="cou_name">${item->act_name!}</div>
                                                                <div class="cou_money">￥${item->denomination!}</div>
                                                                <div class="cou_limit">
                                                                <#if ($item->least_consume > 0)
                                                                    满${item->least_consume!}使用
                                                                <#else>
                                                                    无限制
                                                                </#if>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <input type="number" class="total_amount" name="total_amount" value="${item->total_amount!}" onblur="updateLeftTotalmount(this)" >
                                                            </td>
                                                            <td>
                                                                <div class="send_strategy1 send_strategy">
                                                                    <span>1</span>
                                                                    <span class="cs_info1">领包后立即发放${item->immediately_grant_amount!}张</span>
                                                                </div>
                                                                <#if ($item->timing_every > 0 && $item->timing_amount > 0)
                                                                    <div class="send_strategy1 send_strategy">
                                                                        <span>2</span>
                                                                        <span class="cs_info1">领包后每${item->timing_every!}
                                                                            <#if ($item->timing_unit == 0)
                                                                                天
                                                                            <#elseif>($item->timing_unit == 1)
                                                                                周的周
                                                                                <#if ($item->timing_time == 7)
                                                                                    日
                                                                                <#else>
                                                                                    ${item->timing_time!}
                                                                                </#if>
                                                                            <#else>
                                                                                个月的${item->timing_time!}
                                                                            </#if>
                                                                            发放${item->timing_amount!}张</span>
                                                                    </div>
                                                                </#if>
                                                                <a href="##" class="btn_cs_reset">重新设置</a>
                                                            </td>
                                                            <td>
                                                                <a href="##" class="btn_del_cs" onclick="deleteTr(this)">删除</a>
                                                            </td>
                                                        </tr>
                                                    </#list>
                                                    </#if>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><span style="color: #999;">注：</span></div>
                                        <div class="fl add_tips">
                                            <span class="some_tips">1.优惠券包发放的优惠券不占用原优惠券库存；</span>
                                            <span class="some_tips">2.任意一张优惠券过期或失效，则不会展示在券礼包中，用户可以设置的价格购买券包中其他优惠券；</span>
                                            <span class="some_tips">3.若所有优惠券全部过期或失效，则该礼包失效，用户不可领取。</span>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>每人限领礼包数量：</div>
                                        <div class="fl">
                                            <input type="number" placeholder="" name="limit_get_times" value="${couponPack->limit_get_times!}" class="limit_get_num">
                                            <span class="some_tips">单个用户可以领取该礼包的数量，填写0表示不限制</span>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>礼包发放数量：</div>
                                        <div class="fl">
                                            <input type="number" placeholder="" name="total_amount" value="${couponPack->total_amount!}" class="send_cou_num">
                                            <span class="some_tips">优惠券包发放的总数量</span>
                                            <#if ($couponPack)
                                            <a href="##" class="ensed_num">当前已发放数量：${couponPack->issued_amount!}</a>
                                            </#if>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>礼包领取方式：</div>
                                        <div class="fl">
                                            <div class="get_cou_type">
                                                <label for="money_buy">
                                                    <input type="radio" name="access_mode" value="0" <#if ($couponPack->access_mode == 0) checked </#if> <#if (!$couponPack) checked </#if> id="money_buy">现金购买
                                                </label>
                                                <label for="score_buy">
                                                    <input type="radio" name="access_mode" value='1' <#if ($couponPack->access_mode == 1) checked </#if> id="score_buy">积分购买
                                                </label>
                                                <label for="free">
                                                    <input type="radio" name="access_mode" value="2" <#if ($couponPack->access_mode == 2) checked </#if> id="free">直接领取
                                                </label>
                                            </div>
                                             <div class="should_pay">
                                                 需支付：<input type="number" name="access_cost" <#if ($couponPack->access_mode == 1) value="{{floor($couponPack->access_cost)!}" <#else> value="${couponPack->access_cost!}" </#if>> <span>元</span>，当前已选优惠券可优惠金额总和为 <a href="##" style="color: #5A8BFF;" class="total_denomination_a">333元</a>
                                             </div>
                                        </div>
                                    </li>
                                    <li class="content_right_li clearfix">
                                        <div class="fl"><em>*</em>活动规则：</div>
                                        <div class="fl">
                                            <textarea name="act_rule" id="" cols="30" rows="10" class="act_rules">${couponPack->act_rule!}</textarea>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="fix_footer">
                <a href="##" class="btn_save">保存</a>
            </div>
        </form>
    </div>
</div>

<div id="strategy_set">
    <div class="set_infos">
        <div class="each_set_line">
            <span>1</span>领取后立即发放 <input name="immediately_grant_amount" type="number" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"> 张
        </div>
        <div class="each_set_line">
            <span>2</span>领取后每 <input name="timing_every" type="number" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
            <select name="timing_unit" id="timing_unit">
                <option value="0">天</option>
                <option value="1">自然周</option>
                <option value="2">自然月</option>
            </select>
        </div>
        {{--每日--!}
        <div class="second_line each_day">
            发放 <input name="timing_amount" type="number" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"> 张
        </div>
        {{--每周--!}
        <div class="second_line each_week" hidden>
            每周
            <select name="timing_time">
                <option value="1">一</option>
                <option value="2">二</option>
                <option value="3">三</option>
                <option value="4">四</option>
                <option value="5">五</option>
                <option value="6">六</option>
                <option value="7">日</option>
            </select> 发放 <input name="timing_amount" type="number" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"> 张
        </div>
        {{--每月--!}
        <div class="second_line each_month" hidden>
            每月
            <select name="timing_time" id="">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
                <option value="13">13</option>
                <option value="14">14</option>
                <option value="15">15</option>
                <option value="16">16</option>
                <option value="17">17</option>
                <option value="18">18</option>
                <option value="19">19</option>
                <option value="20">20</option>
                <option value="21">21</option>
                <option value="22">22</option>
                <option value="23">23</option>
                <option value="24">24</option>
                <option value="25">25</option>
                <option value="26">26</option>
                <option value="27">27</option>
                <option value="28">28</option>
                <option value="29">29</option>
                <option value="30">30</option>
            </select>号发放 <input name="timing_amount" type="text" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"> 张
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/coupon_package_manage.js?v=1.0.2"></script>
<script type="text/javascript">
    $('.fix_footer').width($('.main-container').width());
    function picker(){
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
</script>
<script type="text/javascript">
    getPowerInfo('main_config','coupon_package','sub_4','优惠券包',0);
</script>