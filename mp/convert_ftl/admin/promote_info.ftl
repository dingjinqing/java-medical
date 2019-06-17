<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/add_coupon.css?v=1.0.8" type="text/css" />
<link rel="stylesheet" href="/css/admin/promote_manage.css?v=1.0.1" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">好友助力</span>
        </div>
    </div>
</div>
<div class="main-container fix_every_footer">
    <div class="nav-role">
        <ul id="tab" class="nav-child-tabs clearfix">
            <li ><a href="/admin/market/promote/list?act_status=0">全部好友助力活动</a></li>
            <li ><a href="/admin/market/promote/list?act_status=1">进行中</a></li>
            <li ><a href="/admin/market/promote/list?act_status=2">未开始</a></li>
            <li ><a href="/admin/market/promote/list?act_status=3">已过期</a></li>
            <li ><a href="/admin/market/promote/list?act_status=4">已停用</a></li>
            <li class="active"><a href="#">添加好友助力活动</a></li>
        </ul>
    </div>
    <form action="" method="post" id="form1" class="config_form">
        {{csrf_field()!}
        <div class="promote_config">
            <input type="hidden" name="act_id" value="${act_id!}">
            <input type="hidden" name="act" value="${act_type!}">
            <input type="hidden" name="goods_ids" value="${info->goods_ids!}">
            <input type="hidden" name="reward_ids" value="${info->reward_ids!}">
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>活动名称：</div>
                <div class="el_right">
                    <input type="text" placeholder="请填写活动名称" name="act_name" value="${info->act_name!}">
                    <a href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=736&fromuid=1" target="_blank">查看活动规则</a>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>活动有效期：</div>
                <div class="el_right">
                    <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${info->start_time!}"/>
                    &nbsp; 至&nbsp;&nbsp;
                    <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${info->end_time!}"/>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>奖励类型：</div>
                <div class="el_right">
                    <label for="gift_goods" >
                        <input type="radio" id="gift_goods" name="reward_type" value="0" <#if ($info->reward_type==0) checked </#if>  <#if ($act_type == 'edit') disabled </#if>>赠送商品
                    </label>
                    <label for="discount_goods">
                        <input type="radio" id="discount_goods" name="reward_type" value="1" <#if ($info->reward_type==1) checked </#if>  <#if ($act_type == 'edit') disabled </#if>>折扣商品
                    </label>
                    <label for="coupons">
                        <input type="radio" id="coupons" name="reward_type" value="2" <#if ($info->reward_type==2) checked </#if>  <#if ($act_type == 'edit') disabled </#if>>赠送优惠券
                    </label>

                    <#if ($act_type == 'edit')
                    <input type="hidden" name="reward_type" value="${info->reward_type!}">
                    </#if>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"></div>
                <div class="el_right">
                    {{--折扣商品或赠品--!}
                    <div class="choose_goods "  style=" <#if ($info->reward_type == 2) display: none; </#if>">
                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">选择商品
                    </div>
                    {{--优惠券--!}
                    <div class="coupon_div clearfix" style="<#if ($info->reward_type == 0 || $info->reward_type == 1) display: none; </#if>">
                        <div class="coupon_add card_add_click">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                            <p>添加优惠券</p>
                        </div>
                    </div>

                </div>
            </div>
            <div class="each_line clearfix award_area">
                <div class="el_left"><em>*</em>奖励设置：</div>
                <div class="el_right">
                    {{--商品和赠品--!}
                    <div class="goods_info_table goods_info_list <#if (empty($info->goods_info)) hide </#if>">
                        <table width="100%">
                            <thead>
                            <tr>
                                <td>商品信息</td>
                                <td >商品价格</td>
                                <td >商品库存</td>
                                <td >活动库存</td>
                                <td class="market_price <#if ($info->reward_type == 0) hide </#if> ">活动价</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="goods_list">
                                <td >
                                    <div class="goods_message clearfix">
                                        <div class="goods_img"><img src="<#if ($info->goods_info['goods_img']) ${info->goods_info['goods_img']!} <#else> http://${image_domain!}/image/admin/shop_beautify/add_decorete.png </#if>" alt=""></div>
                                        <div class="goods_info">
                                            <div class="goods_name">
                                                <!-- <span style="border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px;display: inline-block">拼团</span> -->
                                                <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">自营</span>
                                                <span class="name">${info->goods_info['goods_name']!}</span>
                                            </div>
                                            <div class="goods_spec">${info->goods_info['goods_spec']!}</div>
                                        </div>
                                    </div>
                                </td>
                                <td >${info->goods_info['goods_price']!}</td>
                                <td class="goods_store">${info->goods_info['goods_store']!}</td>
                                <td >
                                    <input type="text" name="g_market_store" onkeyup="value=value.replace(/[^\d]/g,'')" value="${info->goods_info['market_store']!}">
                                </td>
                                <td class="market_price <#if ($info->reward_type == 0) hide </#if>">
                                    <input type="text" name="market_price"  onkeyup="value=value.replace(/[^\.\d]/g,'')" value="${info->goods_info['market_price']!}">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    {{--优惠券--!}
                    <div class="coupon_info_table goods_info_table <#if (empty($info->coupon_info)) hide </#if>">
                        <table width="100%">
                            <thead>
                            <tr>
                                <td>优惠券信息</td>
                                <td>活动发放总量(张)</td>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <div>
                                        <p class="coupon_name">${info->coupon_info['act_name']!}</p>
                                        <p class="coupon_money">
                                            <#if ($info->coupon_info['act_code'] == 'voucher') 
                                            &yen;${info->coupon_info['denomination']!} 
                                            <#else>
                                            ${info->coupon_info['denomination']!}折
                                            </#if>
                                        </p>
                                        <p class="coupon_limit">
                                            <#if ($info->coupon_info['use_consume_restrict'] ==1)
                                            满${info->coupon_info['least_consume']!}使用
                                            <#else>
                                            不限制
                                            </#if>
                                        </p>
                                    </div>
                                </td>
                                <td>
                                    <input type="text" name="c_market_store" value="${info->coupon_info['market_store']!}" onkeyup="value=value.replace(/[^\d]/g,'')">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>


            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>奖励有效期：</div>
                <div class="el_right">
                    <input type="text" name="reward_duration" value="${info->reward_duration!}" class="some_value effect_time" onkeyup="value=value.replace(/[^\.\d]/g,'')">
                    <select name="reward_duration_unit" id="">
                        <#list ($reward_duration_unit as $reward_key=>$reward_name)
                        <option value="${reward_key!}" <#if ($info->reward_duration_unit == $reward_key) selected </#if>>${reward_name!}</option>
                        </#list>
                    </select>
                    <span class="promote_tip">用户获得奖励后在有效期内未领取则奖励失效，不可再领取</span>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>单次助力值：</div>
                <div class="el_right">
                    <label for="avarage_value">
                        <input type="radio" name="promote_type" id="avarage_value" value="0" <#if ($info->promote_type == 0) checked </#if>>平均值
                    </label>
                    <label for="random_value">
                        <input type="radio" name="promote_type" id="random_value" value="1" <#if ($info->promote_type == 1) checked </#if>>随机助力值
                    </label>
                    <a href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=736&fromuid=1" target="_blank">查看规则</a>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>所需助力值：</div>
                <div class="el_right">
                    <input type="text" name="promote_amount" value="${info->promote_amount!}" class="some_value need_pro_value" <#if ($act_type == 'edit') readonly </#if> onkeyup="value=value.replace(/[^\d]/g,'')" >
                    <span class="promote_tip">用户发起抢购活动，助力值达到要求则助力成功，可领取奖励，建议填写大于100的整数</span>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>所需助力次数：</div>
                <div class="el_right">
                    <input type="text" name="promote_times" value="${info->promote_times!}" class="some_value need_pro_times" <#if ($act_type == 'edit') readonly </#if> onkeyup="value=value.replace(/[^\d]/g,'')">
                    <span class="promote_tip">活动需要好友帮忙助力的总次数</span>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>发起次数限制：</div>
                <div class="el_right">
                    单用户在
                    <input type="text" name="launch_limit_duration" value="${info->launch_limit_duration!}" class="some_value limit_each_time" onkeyup="value=value.replace(/[^\d]/g,'')">
                    <select name="launch_limit_unit" id="">
                        <#list ($launch_limit_unit as $launch_key=>$launch_name)
                            <option value="${launch_key!}" <#if ($info->launch_limit_unit == $launch_key) selected </#if>>${launch_name!}</option>
                        </#list>
                    </select>
                     内最多可发起
                    <input type="text" name="launch_limit_times" value="${info->launch_limit_times!}" class="some_value limit_times" onkeyup="value=value.replace(/[^\d]/g,'')"> 次
                    <span class="promote_tip">用户在某时间段内最多可发起抢购活动的次数，填写0表示不限制</span>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>分享增加助力机会：</div>
                <div class="el_right">
                    好友可通过分享获得最多
                    <input type="text" name="share_add_times" value="${info->share_add_times!}" class="some_value share_add_times" onkeyup="value=value.replace(/[^\d]/g,'')">
                     次助力机会
                    <span class="promote_tip">好友通过帮忙分享可获得的助力次数。除分享获得助力次数外，默认每人最少1次助力机会</span>
                </div>
            </div>
            {{--<div class="each_line clearfix">--!}
                {{--<div class="el_left"><em>*</em>授权增加助力机会：</div>--!}
                {{--<div class="el_right">--!}
                    {{--<label for="share_can_add">--!}
                        {{--<input type="radio" name="auth_add_times" id="share_can_add" value="0" <#if ($info->auth_add_times == 0) checked </#if> >启用--!}
                    {{--</label>--!}
                    {{--<label for="share_cant_add">--!}
                        {{--<input type="radio" name="auth_add_times" id="share_cant_add" value="1" <#if ($info->auth_add_times == 1) checked </#if>>不启用--!}
                    {{--</label>--!}
                    {{--<span class="promote_tip">若启用授权增加助力机会，则新用户帮忙助力时会在小程序前端引导用户授权个人信息（头像、昵称），授权完可增加一次助力机会</span>--!}
                {{--</div>--!}
            {{--</div>--!}
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>好友助力条件：</div>
                <div class="el_right">
                    <label for="not_need">
                        <input type="radio" name="promote_condition" id="not_need" value="0" <#if ($info->promote_condition == 0) checked </#if>>可不授权个人信息
                    </label>
                    <label for="need">
                        <input type="radio" name="promote_condition" id="need" value="1" <#if ($info->promote_condition == 1) checked </#if>>需要授权个人信息
                    </label>
                    <span class="promote_tip">好友帮忙助力时，是否需要授权个人信息（头像+昵称）</span>
                </div>
            </div>

            <div class="each_line clearfix only_disconnt" <#if ($info->reward_type == 0 || $info->reward_type == 2) style="display: none" </#if>>
                <div class="el_left"><em>*</em>优惠叠加策略：</div>
                <div class="el_right">
                    <label for="can_cou">
                        <input type="radio" name="use_discount" id="can_cou" value="1" <#if ($info->use_discount == 1) checked </#if>>可叠加
                    </label>
                    <label for="no_cou">
                        <input type="radio" name="use_discount" id="no_cou" value="0" <#if ($info->use_discount == 0 )checked </#if> >不可叠加
                    </label>
                    <span class="promote_tip">活动商品结算时是否可与会员卡折扣、优惠券叠加使用</span>
                </div>
            </div>
            <div class="each_line clearfix only_disconnt" <#if ($info->reward_type == 0 || $info->reward_type == 2) style="display: none" </#if>>
                <div class="el_left"><em>*</em>积分抵扣策略：</div>
                <div class="el_right">
                    <label for="can_score">
                        <input type="radio" name="use_score" id="use_score" value="1"  <#if ($info->use_score == 1 || !isset($info->use_score)) checked </#if>>可抵扣
                    </label>
                    <label for="no_score">
                        <input type="radio" name="use_score" id="use_score" value="0"  <#if ($info->use_score == 0 && isset($info->use_score)) checked </#if>>不可抵扣
                    </label>
                    <span class="promote_tip">活动商品结算时是否可使用积分抵扣部分金额</span>
                </div>
            </div>

            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>助力失败赠送：</div>
                <div class="el_right">
                    <label for="no_gift">
                        <input type="radio" name="failed_send_type" id="no_gift" value="0" <#if ($info->failed_send_type == 0) checked </#if>>不赠送
                    </label>
                    <label for="fail_coupon">
                        <input type="radio" name="failed_send_type" id="fail_coupon" value="1" <#if ($info->failed_send_type == 1) checked </#if>>优惠券
                    </label>
                    <label for="fail_score">
                        <input type="radio" name="failed_send_type" id="fail_score" value="2" <#if ($info->failed_send_type == 2) checked </#if>>积分
                    </label>
                    {{--优惠券--!}
                    <div class="failed_send_coupon failed_send_click clearfix hide">
                        <div class="coupon_add ">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                            <p>添加优惠券</p>
                        </div>
                        
                    </div>
                    <input type="hidden" name="failed_send_coupon" value="${info->failed_send_content!}">
                    <div class="coupon_list_clone failed_send_click <#if (in_array($info->failed_send_type,[0,2])) hide </#if>">
                        <div class="coupon_list">
                            <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del">
                            <input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info">
                            <div class="coupon_list_top">
                                <#if ($info->failed_send['act_code'] == 'voucher') 
                                    &yen;<span>${info->failed_send['denomination']!}</span>
                                <#else>
                                    <span>${info->failed_send['denomination']!}</span>折
                                </#if>
                            </div>
                            <div class="coupon_list_center">
                                <div class="coupon_center_limit">
                                    <#if ($info->failed_send['use_consume_restrict'] ==1)
                                        满${info->failed_send['least_consume']!}使用
                                    <#else>
                                        不限制
                                    </#if>
                                </div>
                                <div class="coupon_center_number">剩余<span>${info->failed_send['surplus']!}</span>张</div>
                            </div>
                            <div class="coupon_list_bottom">
                                领取
                            </div>
                        </div>
                    </div>

                    <div class="failed_send_score clearfix <#if ( in_array($info->failed_send_type,[0,1])) hide </#if>">
                        赠送积分值：<input type="text" name="score" class="some_value" value="${info->failed_send['score']!}" onkeyup="value=value.replace(/[^\d]/g,'')">
                    </div>
                </div>
            </div>
            <div class="each_line clearfix">
                <div class="el_left"><em>*</em>活动分享：</div>
                <div class="el_right">
                    <label for="default_type">
                        <input type="radio" name="activity_share_type" id="default_type" value="0" <#if ($info->activity_share_type == 0) checked </#if>>默认样式
                    </label>
                    <a href="javascript:;" class="show_eg promote_share" goods_img="http://${image_domain!}/image/admin/share/promote_share_goods.jpg" voucher_img="http://${image_domain!}/image/admin/share/promote_share_voucher.jpg">分享预览
                        <div class="hover_show">
                            <img src="<#if ($info->reward_type == 2)http://${image_domain!}/image/admin/share/promote_share_voucher.jpg <#else> http://${image_domain!}/image/admin/share/promote_share_goods.jpg </#if>" style="height: auto;" />
                        </div>
                    </a>
                    <a href="javascript:;" class="show_eg promote_pictorial" goods_img="http://${image_domain!}/image/admin/share/promote_pictorial_goods.jpg" voucher_img="http://${image_domain!}/image/admin/share/promote_pictorial_voucher.jpg">海报预览
                        <div class="hover_show">
                            <img src="<#if ($info->reward_type == 2)http://${image_domain!}/image/admin/share/promote_pictorial_voucher.jpg <#else> http://${image_domain!}/image/admin/share/promote_pictorial_goods.jpg </#if>"/>
                        </div>
                    </a>
                    <br>
                    <label for="own_type" style="margin-left: 0;margin-top: 10px">
                        <input type="radio" name="activity_share_type" id="own_type" value="1" <#if ($info->activity_share_type == 1) checked </#if>>自定义样式
                    </label>
                    <br>
                    <div class="own_config" >
                        <div class="clearfix">
                            <div class="oc_left">文案：</div>
                            <div class="oc_right">
                                <input type="text" name="custom_share_word" value="${info->custom_share_word!}">
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="oc_left">分享图：</div>
                            <div class="oc_right">
                                <label for="index_cut">
                                    <input type="radio" name="share_img_type" id="index_cut" value="0" <#if ($info->share_img_type == 0) checked </#if>>活动商品信息图
                                </label>
                                <br>
                                <label for="self_send" style="margin-left: 0;margin-top: 10px">
                                    <input type="radio" name="share_img_type" id="self_send" value="1" <#if ($info->share_img_type == 1) checked </#if>>自定义图片
                                </label>
                                <div class="share_img_area clearfix" >
                                    <div class="add_share_img">
                                        <input type="hidden" name="custom_img_path" value="${info->custom_img_path!}">
                                        <img src="<#if ($info->custom_img_path) http://${image_domain!}/${info->custom_img_path!} <#else> http://${image_domain!}/image/admin/add_img.png </#if>" class="add_img" alt="" default_img="http://${image_domain!}/image/admin/add_img.png" class="add_img">
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" >
                                    </div>
                                    <span class="promote_tip">建议尺寸：800*800像素</span>
                                </div>

                            </div>
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

<div class="act_rules" style="display: none;">
    <div>
        <div style="margin: 10px">
            <p>1.发起助力活动需要授权个人信息（头像+昵称），强制授权，发起按钮调起授权</p>

            <p>2.帮助力好友，助力需授权个人信息，是否强制授权由后台配置</p>

            <p>3.下单授权手机号取后台 “交易配置”中关于手机号授权的开关配置</p>

            <p>4.好友助力下单可自提、可快递，可线上支付、可货到付款</p>

            <p>5.好友助力不参与分销</p>

            <p>6.赠品不可退换货，前端订单列表及订单详情中隐藏退货中心入口</p>

            <p>7.商品中奖即扣库存，过期后失效（失效后台配置页可设置</p>
        </div>
    </div>
</div>

<div class="promote_rules" style="display: none;">
    <div>
        <div style="margin: 10px">
            <p><img src="/image/admin/promoterules.png"></p>
        </div>
    </div>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/admin/promote_config.js?v=1.0.13" type="text/javascript"></script>
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
    getPowerInfo('main_config','promote','sub_4','好友助力',0);
</script>
