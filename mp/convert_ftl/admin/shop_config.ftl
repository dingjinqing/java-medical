<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/shop_config.css?v=2.0.2" type="text/css" />
<link rel="stylesheet" href="/css/admin/common_share.css?v=1.2.2" type="text/css" />
<link rel="stylesheet" href="/css/admin/wx_auth_success.css?v=1.0.3" type="text/css" />
<link href="/css/admin/iconfont.css?v=1.0.1" rel="stylesheet">
<style type="text/css">
	.shop-config li {
		min-height: 30px;
	}
    .other_content input {
        border: none;
        height: 15px;
        width: 15px;
    }

    .other_content label {
        font-size: 14px;
        margin-right: 5px;
    }
    .orderinfo_page {
        float: left;
        margin-right: 100px;
    }

    .orderinfo_page>div {
        float: left;
    }

    .orderinfo_page>div a {
        color: #5a8bff;
        margin-left: 10px;
    }

    .orderinfo_page>div input[type='checkbox'] {
        top: 1px;
        margin-right: 10px;
    }

    .look_ex {
        position: relative;
    }

    .look_ex div {
        position: absolute;
        width: 100px;
        height: 100px;
        display: none;
    }

    .look_ex div img {
        position: absolute;
        top: -390px;
        left: 65px;
        z-index: 99;
    }
    .hover_show:before{
        top:269px !important;
    }
    .btn-save{
        text-align: center;
    }
    .shop-config{
     min-height: 760px;
    }
    .shop-config li .btn-save:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .shop-config li .btn-save:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .btn_link{
        height: 28px;
        line-height: 28px;
        margin: 5px 5px 0;
        padding: 0 15px;
        border: 1px solid #dedede !important;
        background-color: #fff;
        color: #333;
        border-radius: 2px;
        font-weight: 400;
        cursor: pointer;
        text-decoration: none;
    }
    .logo-img .logoPic{
        width:80px;
        position: absolute;
        top: 23px;
        left: 0;
        padding: 0;
    }
    .defaultPic{
        display: none;
    }
    .tips{
        line-height: 25px;
        margin-top: 12px;
        margin-left: 36px;
        float: left;
        position:relative;
    }
    .tips .logo_tips{
        position: absolute;
        top: -113px;
        left: 55px;
        display: none;
    }
    .content3 {
        margin-top: -10px;
    }
    .auth_info {
        padding: 20px 25px 20px 0;
        min-height: 510px;
    }
    .hover_show {
        top: -250px !important;
    }
    .order_must li{
        min-height: 45px;
    }
    .pay_fl{
        margin-top: 4px;
    }  
    .setting-head-ul {
        border-bottom: 1px solid #eee;
    }
    .text-prompt {
        border-left: none;
    }
    .btn_cart {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 380px;
    }
    .btn_cart label {
        width: 85px;
        height: 55px;
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }
    .btn_cart input[type='radio'] {
        top: -10px !important;
    }
    .btn_cart input {
        border: none;
        margin-top: 20px;
        margin-right: 8px;
        height: 15px;
        width: 15px;
    }
    .icon_font_size {
        font-size: 32px !important;
    }
    .right_buy {
        width: 70px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        background: rebeccapurple;
        color: white;
        font-size: 12px;
        border-radius: 15px;
    }
    .cart_buy {
        width: 55px;
        height: 30px;
        text-align: center;
        line-height: 30px;
        border: 1px solid rebeccapurple;
        color: rebeccapurple;
        font-size: 12px;
        border-radius: 15px;
        background: white;
    }
</style>
<div class="title">
    <span>基础配置 / </span><span style="color: #666;">店铺信息配置</span>
</div>
<div class="order-container">
    <div class="setting-head fix_every_footer">
    <ul class="setting-head-ul clearfix">
        <li <#if ($act == 'base')class="head-active"</#if>><a href="##">店铺基础信息</a></li>
        <li <#if ($act == 'com')class="head-active"</#if>><a href="##">店铺通用设置</a></li>
        <li <#if ($act == 'auth')class="head-active"</#if>><a href="##">小程序授权</a></li>
    </ul>
    <div class="shop-config">
        <input type="text" name='act' value="${act!}" hidden>
        <div class="_content content2" <#if ($act != 'base')style="display: none" </#if>>
            <form action="/admin/config/shop?act=base" method="post" id="form1">
            {{ csrf_field()!}
            <ul>
                <li class="clearfix">
                    <div class="fl">店铺名称：</div>
                    <div class="fl">
                        <input type="text" name='shop_name' value="${shop->shop_name!}" class ="shop_name"/>
                        <span>${shop->shop_name!}</span>
                        <button class="btn-change" onclick="return false;">更改</button>
                    </div>
                </li>
                <li>
                    <div class="fl">店铺状态：</div>
                    <div class="fl shop_bussiness_state">
                        <input type="radio" name="shop_state" id="bussiness_yes" value="1" <#if ($shop->business_state == 1) checked </#if> >已营业
                        <input type="radio" name="shop_state" id="bussiness_no" value="0"  <#if ($shop->business_state == 0) checked </#if> >未营业
                    </div>
                </li>
                <li class="clearfix">
                    <div class="fl">创建时间：</div>
                    <div class="fl">${shop->created!}</div>
                </li>
                <li class="clearfix">
                    <div class="fl">有效期至：</div>
                    <div class="fl">${expire_time!}</div>
                </li>
                {{--<li class="clearfix">--!}
                {{--<div class="fl">店铺主体认证：</div>--!}
                {{--<div class="fl">--!}
                {{--<input type="text" value="掌上先机网络科技有限责任公司" />--!}
                {{--<span>掌上先机网络科技有限责任公司</span>--!}
                {{--<button class="btn-change">更改</button>--!}
                {{--<button>查看</button>--!}
                {{--</div>--!}
                {{--</li>--!}
                <li class="clearfix">
                    <div class="fl">后端店铺logo：</div>
                    <div class="fl logo-img add_img" style="width: 80px;text-align: center">
                        <img <#if ($shop->shop_avatar) src="${shop->shop_avatar!}" <#else> src="http://${image_domain!}/image/admin/shop_def_y.png" style="width:66px;" </#if> alt="" />
                        <input type="hidden" name="shop_avatar" value="${shop->shop_avatar!}">
                        <span>更改</span>
                    </div>
                    <div style="float: left;margin-top: 100px;margin-left: -80px;">
                        图片格式必须为：png,bmp,jpeg,jpg,gif；不可大于5M；<br />
                        建议使用png格式图片，以保持最佳效果；建议图片尺寸为144px*144px</div>
                </li>
                <li class="clearfix">
                    <div class="fl">&nbsp;</div>
                    <div class="fl">
                        <button class="btn-save">保存</button>
                    </div>
                </li>
            </ul>
        </form>
        </div>
        <div class="_content content3" <#if ($act != 'com')style="display: none" </#if>>
            <form action="/admin/config/shop?act=com" method="post" id="form4">
                {{ csrf_field()!}
                <div>
                <ul>
                        <li class="clearfix">
                            <div class="text-prompt"><span class="blue_border"></span>前端店铺logo</div>
                            <div class="fl shop_bussiness_state" style="width:100%;margin-top:15px;">
                                <input type="radio" name="show_logo" id="logo_yes" value="0" <#if ($show_logo == 0) checked </#if> >不显示
                                <input type="radio" name="show_logo" id="logo_no" value="1"  <#if ($show_logo == 1) checked </#if> >自定义
                            </div>
                            <div class="fl logo-img add_front_end_img" style="width: 80px;text-align: center;position: relative;margin-top: 13px;<#if ($show_logo == 0) display: none; </#if> ">
                                <img src="http://${image_domain!}/image/admin/shop_def_y.png" class="defaultPic" style="width:66px;" alt="" />
                                <img src="${shop->logo!}" class="logoPic" alt="">
                                <input type="hidden" name="logo" value="${shop->logo!}">
                                <span>更改</span>
                            </div>
                            <div class="tips" style="margin-top: 16px;<#if ($show_logo == 0) display: none; </#if>">
                                <span>将于前端页面底部显示</span><br/>
                                <a href="javascript:;" class="show_eg">查看示例
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/new_preview_image/bottom_logo.jpg"/>
                                    </div>
                                </a>
                            </div>
                            <div class="logo_size" style="margin-top: 115px;margin-left: 116px;<#if ($show_logo == 0) display: none; </#if>">
                                建议使用png格式图片，图片尺寸300px*80px
                            </div>
                            <div class="choose_link" style="height: 50px;margin-top: 30px;<#if ($show_logo == 0) display: none; </#if>     ">
                                <span>链接：</span>
                                <input type="text" name="page" value="${logo_link!}" class="logo_link" style="height: 30px;"  placeholder="默认为首页链接"/>
                                <button class="btn_link " >选择链接</button>
                            </div>
                        </li>
                        <li class="clearfix share_module" style="margin-top: 10px;">
                            <input type="hidden" name="module_action" value="shop"/>
                            <div class="text-prompt"><span class="blue_border"></span>店铺分享</div>
                            <div class="fl" style="margin:15px 0;">
                                <input type="radio" name="share_action" value="1" <#if  (!$module_share || $module_share['share_action'] == 1) checked </#if>/> 默认样式
                                <a href="javascript:;" class="show_eg">查看示例
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/share/shop_share.jpg"/>
                                    </div>
                                </a>

                                <div>
                                    <input type="radio" name="share_action" value="2" <#if  ($module_share['share_action'] == 2) checked </#if>/> 自定义样式
                                </div>

                                <div style="padding-left: 22px;">
                                    <span>文案：</span><input type="text" name="share_doc" value="${module_share['share_doc'] ?: $shop->shop_name!}" style="margin-left: 18px;"/>
                                </div>
                                <div style="padding-left: 22px;">
                                    <span>分享图：</span>
                                    <input type="radio" name="share_img_action" value="1" <#if  ($module_share['share_img_action'] == 1) checked </#if> /> 店铺首页截图
                                    <p style="padding-left: 56px;">
                                        <input type="radio" name="share_img_action" value="2" <#if  ($module_share['share_img_action'] == 2) checked </#if>/>自定义图片
                                    </p>
                                    <div class="module_share_image">
                                        <input type="hidden" name="share_img" value="${module_share['share_img']!}">
                                        <div class="choose_img" <#if  ($module_share['share_img']) style="display: block;" <#else> style="display: none;" </#if>>
                                            <img src="${module_share['share_img']!}"/>
                                            <span>重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_image" <#if  ($module_share['share_img']) style="display: none;" <#else> style="display: inline-block;" </#if>>
                                        <span style="float: left; margin-top: 25px; margin-left: 20px;">建议尺寸: 800*800像素</span>
                                    </div>
                                </div>
                            </div>
                        </li>
                        {{--<li class="clearfix">--!}
                            {{--<div class="fl">&nbsp;</div>--!}
                            {{--<div class="fl">--!}
                                {{--<button class="btn-save">保存</button>--!}
                            {{--</div>--!}
                        {{--</li>--!}
                    </ul>
                    <div class="text-prompt"><span class="blue_border"></span>待付款订单取消时间设置</div>
                    <div class="text-set">拍下未付款订单<input type="text" name="cancel_time" value="${cancel_time!}" />分钟内未付款，自动取消订单</div>
                    <div class="text-prompt"><span class="blue_border"></span>商品默认平台分类</div>
                    <div class="text-set clearfix">
                        商品默认平台分类：
                        <select name="" id="" class="default_sort">
                            <option value="0">平台分类1</option>
                        </select>
                    </div>
                    {{--客服按钮配置--!}
                    <div class="text-prompt"><span class="blue_border"></span>客服配置</div>
                    <div class="text-set clearfix">
                        {{--<div class="fl pay_fl" img_id="${custom_service == 1 ? 1 : 0!}" <#if ($custom_service == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>--!}
                            {{--<label>--!}
                                {{--<input type="checkbox" name="custom_service" aid="" <#if ($custom_service ==1) checked </#if> />--!}
                                {{--<img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($custom_service ==1) style="right:0px" </#if>  />--!}
                            {{--</label>--!}
                        {{--</div>--!}
                        <span class="con4-hide"></span>
                        <div>
                            <span>客服展示位置：</span>
                            <span>
                                <input type="checkbox" class="iscom" name="is_goods" aid="" <#if ($custom_service ==1) checked </#if> />商品详情页
                                <input type="checkbox" class="iscom" name="is_return" aid="" <#if ($return_service ==1) checked </#if> />退/换货中心
                                <span>开关开启，则客服入口会展示在小程序前端相应页面上</span>
                                <span style="margin-left: 10px;"><a href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=685&fromuid=1" target="_blank" class="to_bbc" style="color: #5a8bff;">功能介绍</a></span>
                            </span>
                        </div>
                    </div>
                    {{--购买按钮展示--!}
                    <div class="text-prompt"><span class="blue_border"></span>购买按钮展示设置</div>
                    <div class="text-set clearfix" style="min-height: 60px !important;height: auto !important;">
                        <div class="fl pay_fl" img_id="${show_cart['show_cart'] == 1 ? 1 : 0!}" <#if ($show_cart['show_cart'] == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                            <label>
                                <input type="checkbox" class="showcart" name="show_cart" <#if ($show_cart['show_cart'] ==1) checked </#if> id="cart_btn"/>
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($show_cart['show_cart'] ==1) style="right:0px" </#if>  />
                            </label>
                        </div>
                        <span class="con4-hide"></span>
                        <span>开关开启，商品搜索页以及推荐商品列表中会显示购买按钮</span>
                        <div class="btn_cart">
                            <label for="shop_cart_1">
                                <input type="radio" value="0" id="shop_cart_1"  name="cart_type" <#if (!in_array($show_cart['cart_type'],[1,2,3])) checked </#if>>
                                <div class="iconfont icontianjia icon_font_size new_class"></div>
                            </label>
                            <label for="shop_cart_2" style="width: 75px">
                                <input type="radio" value="1" id="shop_cart_2" name="cart_type" <#if ($show_cart['cart_type'] == 1) checked </#if>>
                                <div class="iconfont icongouwuche1 icon_font_size new_class"></div>
                            </label>
                            <label for="shop_cart_3" style="width: 110px">
                                <input type="radio" value="2" id="shop_cart_3" name="cart_type" <#if ($show_cart['cart_type'] == 2) checked </#if>>
                                <div class="right_buy new_back">
                                    马上抢
                                </div>
                            </label>
                            <label for="shop_cart_4">
                                <input type="radio" value="3" id="shop_cart_4" name="cart_type" <#if ($show_cart['cart_type'] == 3) checked </#if>>
                                <div class="cart_buy border_class new_class">购买</div>
                            </label>
                        </div>
                    </div>
                    <div class="text-prompt"><span class="blue_border"></span>发票展示设置</div>
                    <div class="text-set clearfix">
                        <div class="fl pay_fl" img_id="${invoice == 0 ? 1 : 0!}" <#if ($invoice == 0) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                            <label>
                                <input type="checkbox" name="invoice" aid="" <#if ($invoice ==0) checked </#if> />
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($invoice ==0) style="right:0px" </#if>  />
                            </label>
                        </div>
                        <span class="con4-hide"></span>
                        <span>开关开启，用户在购买时可以使用发票功能</span>
                    </div>
                    <div class="text-prompt"><span class="blue_border"></span>销量展示设置</div>
                    <div class="text-set clearfix">
                        <div class="fl pay_fl" img_id="${sales_number == 1 ? 1 : 0!}" <#if ($sales_number == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                            <label>
                                <input type="checkbox" name="sales_number" aid="" <#if ($sales_number ==1) checked </#if> />
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($sales_number ==1) style="right:0px" </#if>  />
                            </label>
                        </div>
                        <span class="con4-hide"></span>
                        <span>开关开启，商品详情页会展示商品的销量</span>
                    </div>
                    <div class="text-prompt"><span class="blue_border"></span>手机号授权配置</div>
                    <div class="text-set clearfix">
                        <div class="fl pay_fl" img_id="${bind_mobile == 1 ? 1 : 0!}" <#if ($bind_mobile == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                            <label>
                                <input type="checkbox" name="bind_mobile" aid="" <#if ($bind_mobile ==1) checked </#if> />
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($bind_mobile ==1) style="right:0px" </#if>  />
                            </label>
                        </div>
                        <span class="con4-hide"></span>
                        <span>开关开启，用户在购买、预约以及申请成为分销员时需绑定手机号</span>
                    </div>
                    <div class="text-prompt"><span class="blue_border"></span>服务条款设置</div>
                    <div class="text_set clearfix" style="height: auto;">
                        <div class="fl pay_fl" img_id="${service_terms == 1 ? 1 : 0!}" <#if ($service_terms == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                            <label>
                                <input type="checkbox" name="service_terms" aid="" <#if ($service_terms ==1) checked </#if> />
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($service_terms ==1) style="right:0px" </#if>  />
                            </label>
                        </div>
                        <span class="con4-hide"></span>
                        <span>开关开启，结算页会展示服务条款，用户需勾选“同意”才可继续下单</span>
                        <div class="service_config">
                            <span style="color: #000000;">条款名称：</span>
                            <input type="text" name="service_name" value="${service_name!}">
                            <span>展示在结算页的服务条款名称</span>
                            <span style="margin-left: 10px;"><a href="/admin/config/termsService" target="_blank" class="to_bbc" style="color: #5a8bff;">编辑条款</a></span>
                            <span style="margin-left: 10px;"> <a href="javascript:;" class="show_eg" style="color: #5a8bff;">
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/new_preview_image/service_config.jpg" alt="">
                                    </div>
                                查看示例</a></span>
                        </div>
                        <div class="f1 service_config">
                            <span style="color: #000000;">首次下单是否默认勾选:</span>
                            <label for="yes-re">
                                {{--<#if ($service_choose ==1)--!}
                                    {{--<img src="http://${image_domain!}/image/admin/check_yes.png" alt="yes" class="re-radio" />--!}
                                {{--<#else>--!}
                                    {{--<img src="http://${image_domain!}/image/admin/check_no.png" alt="no" class="re-radio" />--!}
                                {{--</#if>--!}
                                <input type="radio" name="service_choose"  value='1'  <#if ($service_choose ==1) checked </#if> />
                                &nbsp;&nbsp;是
                            </label>
                            <label for="no-re">
                                {{--<#if ($service_choose ==0)--!}
                                    {{--<img src="http://${image_domain!}/image/admin/check_yes.png" alt="no" class="re-radio" />--!}
                                {{--<#else>--!}
                                    {{--<img src="http://${image_domain!}/image/admin/check_no.png" alt="no" class="re-radio" />--!}
                                {{--</#if>--!}
                                <input type="radio" name="service_choose"  value='0'  <#if ($service_choose ==0) checked </#if> />
                                &nbsp;&nbsp;否
                            </label>
                        </div>

                    </div>
                    <div class="wx_auth_success clearfix">
                        <div class="text-prompt">
                            <span class="blue_border"></span>
                            短信设置
                        </div>
                        <div style="padding:20px 25px">
                            <#if  (!$shopInfo->sms_account)
                                <input type="text" name="sms_account" style="height: 30px;" />
                                <a class="add_sms_account" style="color: #5a8bff; cursor: pointer;">创建充值账号</a>
                            <#else>
                                当前充值账号是： <span style="font-weight: bold;">${shopInfo->sms_account!}</span>，
                                剩余金额： <span style="font-weight: bold;">￥${smsAccount['balance'] ?? 0.00!}</span>，
                                剩余短信条数： <span style="font-weight: bold;">${smsAccount['sms_num'] ?? 0!}</span>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <#if  (stripos(request()->url(), 'mp.weipubao.cn') === false)
                                    <a target="_blank"
                                       href="http://101.200.202.174/sms/api/alipay/index.php?sms_account=${shopInfo->sms_account!}"
                                       style="color: #5a8bff; cursor: pointer;">前往充值</a>
                                <#else>
                                    <a target="_blank" href="http://alipay.wangdian.cn?sms_account=${shopInfo->sms_account!}"
                                       style="color: #5a8bff; cursor: pointer;">前往充值</a>
                                </#if>
                            </#if>
                        </div>
                    </div>

                    {{--划线价展示--!}
                    <div class="text-prompt"><span class="blue_border"></span>划线价(市场价)展示设置</div>
                    <div class="text-set clearfix">
                        <div class="fl pay_fl" img_id="${del_market == 1 ? 1 : 0!}" <#if ($del_market >= 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                            <label>
                                <input type="checkbox" class="lineprice" name="del_market" aid="" <#if ($del_market >=1) checked </#if> />
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($del_market >=1) style="right:0px" </#if>  />
                            </label>
                        </div>
                        <span class="con4-hide"></span>
                        <span>开关开启，商品搜索页以及推荐商品列表中会显示商品划线价（市场价）。注：为保证页面展示效果，若开启购买按钮，则小程序前端不会显示划线价</span>
                        <div class='other_content'>
                            <label for='shop_market_1'>
                                <input type="radio" value="1" id="shop_market_1" name="show_market" checked>
                                市场价
                            </label>
                            <label for='shop_market_2'>
                                <input type="radio" value="2" id="shop_market_2" name="show_market" <#if ($del_market == 2) checked </#if>>
                                销量
                            </label>
                            <label for='shop_market_3'>
                                <input type="radio" value="3" id="shop_market_3" name="show_market"<#if ($del_market == 3) checked </#if>>
                                评价数
                            </label>
                        </div>
                    </div>
                    <div class="text-prompt"><span class="blue_border"></span>下单必填信息设置</div>
                    <div class="text-set clearfix" style="height: 270px;line-height: 50px">
                        <ul class="order_must">
                            <li>
                                <div class="fl order_must_set">下单人真实姓名</div>
                                <div class="fl pay_fl" img_id="${order_real_name == 1 ? 1 : 0!}" <#if ($order_real_name == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;margin-top: 0px' </#if>>
                                    <label>
                                        <input type="checkbox" name="order_real_name" aid="" <#if ($order_real_name ==1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($order_real_name ==1) style="right:0px" </#if>  />
                                    </label>
                                </div>
                                <span class="con4-hide"></span>
                                <span class="tips">开关开启，用户下单时须填写下单人真实姓名</span>
                            </li>
                            <li>
                                <div class="fl order_must_set">下单人身份证号码</div>
                                <div class="fl pay_fl" img_id="${order_cid == 1 ? 1 : 0!}" <#if ($order_cid == 1) style='margin-top: 0px;background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                    <label>
                                        <input type="checkbox" name="order_cid" aid="" <#if ($order_cid ==1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($order_cid ==1) style="right:0px" </#if>  />
                                    </label>
                                </div>
                                <span class="con4-hide"></span>
                                <span class="tips">开关开启，用户下单时须填写下单人身份证号码</span>
                            </li>
                            <li>
                                <div class="fl order_must_set">收货人真实姓名</div>
                                <div class="fl pay_fl" img_id="${consignee_real_name == 1 ? 1 : 0!}" <#if ($consignee_real_name == 1) style='margin-top: 0px;background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                    <label>
                                        <input type="checkbox" name="consignee_real_name" aid="" <#if ($consignee_real_name ==1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($consignee_real_name ==1) style="right:0px" </#if>  />
                                    </label>
                                </div>
                                <span class="con4-hide"></span>
                                <span class="tips" >开关开启，用户下单时须填写收货人真实姓名</span>
                            </li>
                            <li>
                                <div class="fl order_must_set">收货人身份证号码</div>
                                <div class="fl pay_fl" img_id="${consignee_cid == 1 ? 1 : 0!}" <#if ($consignee_cid == 1) style='margin-top: 0px;background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                    <label>
                                        <input type="checkbox" name="consignee_cid" aid="" <#if ($consignee_cid ==1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($consignee_cid ==1) style="right:0px" </#if>  />
                                    </label>
                                </div>
                                <span class="con4-hide"></span>
                                <span class="tips">开关开启，用户下单时须填写收货人身份证号码</span>
                            </li>
                            <li style="line-height:58px;">
                                <div class="fl order_must_set">自定义信息</div>
                                <div class="fl pay_fl" img_id="${custom == 1 ? 1 : 0!}" <#if ($custom == 1) style='margin-top: 0px;background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                    <label>
                                        <input type="checkbox" name="custom" aid="" <#if ($custom ==1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($custom ==1) style="right:0px" </#if>  />
                                    </label>
                                </div>
                                <span class="con4-hide"></span>
                                <span style="color: #0C0C0C">标题：</span><input type="text" name="custom_title" value="${custom_title!}" size="6" style="width: 100px;color: black"/> <span style="color: black;">限制输入不超过6个字</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </form>
            <div class="fix_footer" style="margin-left: -45px;">
                <span class="btn_footer_save btn-save save4" style="width: 90px;line-height: 28px">保存</span>
            </div>
        </div>
        <div class="_content content3" <#if ($act != 'xcx')style="display: none" </#if>>
            <div class="wx_auth_success clearfix">
                <div class="text-prompt">
                    <span class="blue_border"></span>
                    已绑定小程序
                </div>
                <div class="auth_info">
                    <ul>
                        <li>
                            <span>小程序名称：</span>
                            <span>${mp->nick_name!}</span>
                            <a href="javascript:void(0);" class="auth-link auth_again">重新授权</a>
                            {{--<a href="/wechat/official/account/authorization" target="_blank">授权公众号</a>--!}
                        </li>
                        <li>
                            <#if  (!$mp->link_official_app_id)
                                <span>请选择需要绑定的公众号：</span>
                                <select name="bind_official" id="">
                                    <option value="">请选择</option>
                                    <#list  ($officialList as $item)
                                        <#if  ($item['bind_open_app_id'])
                                            <option value="${item['app_id']!}">${item['nick_name']!}</option>
                                        </#if>
                                    </#list>
                                </select>
                                <button class="auth_btn bind_official">确定</button>
                                <#if  (empty($mp->principal_name))
                                    <span>请优先<a href="javascript:void(0);" class="auth-link auth_again"
                                                style="margin-left: 0px;">重新授权</a>小程序， 再授权公众号</span>
                                <#else>

                                    <#if  (empty($officialList))
                                        <span style="display: inline-block;margin-left:20px;">暂无公众号可绑定，立即</span>
                                        <a href="/wechat/official/account/authorization" target="_blank"
                                           style="margin-left:0; color: #5A8BFF;">添加授权</a>
                                    <#else>
                                        <p class="auth_p">仅可以绑定和该店铺同主体的公众号，绑定后不可以更换，请您谨慎选择</p>
                                    </#if>
                                </#if>
                            <#elseif> (!$officialAccount->is_auth_ok)
                                <span>已绑定的公众号：</span>
                                ${officialAccount->nick_name!} <span
                                        style="margin-left: 20px; color: #b94a48;">此公众号已取消授权，无法给用户发送公众号消息、分销员返利佣金不能提现，如有需要，请重新授权</span>
                            <#else>
                                <span>已绑定的公众号：</span>
                                ${officialAccount->nick_name!}
                            </#if>
                        </li>
                        <li>
                            <span>小程序版本：</span>
                            <span>${mp_version->user_version!}</span><#if ($current_template_id != $mp->bind_template_id)
                                <span class="text-danger">（最新版本${current_version->user_version!}）</span>
                            </#if>

                        </li>
                        <li>
                            <span>审核状态：</span>
                            <span>${audit_state_map[$mp->audit_state]!}</span>
                            <#if ( $mp->is_auth_ok == 1
                            && ( ($current_template_id == $mp->bind_template_id) && in_array($mp->audit_state,[0,3])
                            || ($current_template_id != $mp->bind_template_id) && in_array($mp->audit_state,[0,2,3])
                            )
                            )
                                <a href="javascript:void(0);" class="auth-link upload-code-apply-audit">提交代码审核</a>
                            </#if>
                        </li>
                        <li>
                            <span>授权状态：</span>
                            <span><#if ($mp->is_auth_ok == 1)已授权 <#else> 未授权 </#if></span>
                        </li>
                        <li>
                            <span>小程序头像：</span>
                            <img src="${mp->head_img!}" width="100" alt="" />
                        </li>
                        <li>
                            <span>小程序码：</span>
                            <img width="150"
                                 src="${qrcode['qrcode_img'] ? $qrcode['qrcode_img'] : "/image/admin/shop_default.png"!}"
                                 alt="" />
                        </li>
                        <li>
                            <span>微信认证：</span>
                            <span><#if ($mp->verify_type_info == 0)已认证 <#else> 未认证 </#if></span>
                        </li>
                        <li>
                            <span>原始ID：</span>
                            <span>${mp->user_name!}</span>
                        </li>
                        <li>
                            <span>AppID：</span>
                            <span>${mp->app_id!}</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="wx_auth_success clearfix">
                <div class="text-prompt" id="goods_circle">
                    <span class="blue_border"></span>
                    微信好物圈功能设置
                </div>
                <div style="padding:20px 25px">
                    <div class="check-box-container <#if ($enabled_wx_shopping_list) checked  </#if>"
                    <#if ($enabled_wx_shopping_list) </#if>>
                        <label>
                            <input type="checkbox" name="enabled_wx_shopping_list" <#if ($enabled_wx_shopping_list)
                            checked="true" </#if>>
                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" />
                        </label>
                    </div>
                    <div class="label-check">开关开启，小程序前端可同步购物车商品及支付完成订单信息到微信好物圈，助力享有小程序搜索能力。<a
                                href="/wechat/mini/goodslist?top_index=1" target="_blank" style="color: #5a8bff;">功能介绍</a></div>
                    {{--<div class="" style="margin-left: 50px;color: #999;margin-top:10px;">
                        <text style="color:#ff0000">注：</text>开启或关闭【微信好物圈】，需重新授权小程序并勾选/取消勾选好物圈权限，才能生效
                    </div>
                    <div class="if_open" style="margin-top: 10px;margin-left:25px;">
                        <input type="radio" name="" style="margin-right: 10px">启用好物推荐 <text style="color: #999;margin-left: 5px">启用后，用户可在小程序前端主动推荐商品至微信购物圈</text>
                    </div>--!}
                    <div class="" style="margin-left: 50px;color: #999;margin-top:10px;">
                        <text style="color:#ff0000">注：</text>开启或关闭【好物圈】，需重新授权小程序并勾选/取消勾选好物圈权限，才能生效
                    </div>
                    <div class="" style="margin-left: 50px;margin-top:10px;">
                        【好物推荐】在小程序端展示位置：
                    </div>
                    <div style="margin-left: 50px;margin-top: 10px" class="clearfix">
                        <div class="orderinfo_page clearfix">
                            <div class="checkbox_item">
                                <input type="checkbox" class="sr" name="shipping_recommend[]" value="2" <#if  ($wx_shopping_recommend &&
                                in_array(2, explode(',', $wx_shopping_recommend))) checked </#if>>订单详情页
                            </div>
                            <div class="look_order ">
                                <a href="javascript:;" class="show_eg">查看示例
                                    <div class="hover_show" style = 'top:-266px'>
                                        <img src="http://${image_domain!}/image/admin/new_preview_image/order_thing.jpg" alt="">
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="orderinfo_page clearfix">
                            <div class="checkbox_item">
                                <input type="checkbox" name="shipping_recommend[]" value="1" <#if  ($wx_shopping_recommend &&
                                in_array(1, explode(',', $wx_shopping_recommend))) checked </#if>>商品详情页
                            </div>
                            <div class="look_item ">
                                <a href="javascript:;" class="show_eg">查看示例
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/new_preview_image/goods_thing.jpg" alt="">
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div style="margin-left: 50px; margin-top: 10px;" class="clearfix">
                        <#if  ($mp)
                            <#if  (!empty($plugin))
                                当前【${plugin['nickname']!}】${pulginStatusMp[$plugin['status']]!}
                            <#else>
                                好物圈插件尚未申请， <a href="javascript:void(0)" onclick="submit_apply_plugin()"
                                              style="color: #5a8bff; margin-top: 20px;">提交申请</a>
                            </#if>
                        </#if>
                        <div style="color:red">注：好物圈插件申请后，小程序公众平台后台不要关闭或者删除此插件，负责小程序将无法正常使用。若想关闭此功能，请联系客服、运营人员，协助操作。</div>
                    </div>
                    <input type="button" class="btn btn-primary" value="保存" onclick="save_shipping_cart()"
                           style="background: #5a8bff; margin-left: 50px; margin-top: 20px;">
                </div>
            </div>
        </div>
    </div>
    </div>
</div>
<#include ('admin.preview_common')
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script>
    var shop_id = '${res!}';
    var logo = '${shop->logo!}';
    var set_colors = @json($shop_style);
    $(".new_class").css("color",set_colors[0]);
    $(".border_class").css("border-color",set_colors[0]);
    $(".new_back").css("background-color",set_colors[0]);
    // if(shop_id > 0){
    //     layer.ready(function () {
    //         layer.msg('保存成功', {time: 3000});
    //     });
    // }else if(shop_id==0){
    //     util.mobile_alert('保存失败');
    // }
    $('.btn-change').click(function(){
        $(this).parent().find('input').show();
        $(this).parent().find('input').addClass('ipt_border');
        $(this).parent().find('span').hide();
        $(this).parent().find('input').focus();
        $(this).hide();
    });
    $("input[name='shop_name']").blur(function() {
        $(this).removeClass('ipt_border');
        $(this).parent().find('span').text($(this).val()).show();
        $(this).hide();
        $(this).parent().find('.btn-change').show();
    });
    $('.add_img').click(function() {
        var el = $(this);
        var w = 144;
        var h = 144;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find("img").attr("src", path);
                el.find('input').val(path);
            }
        });
    });
    $('.add_front_end_img').click(function(){
        var el = $(this);
        var w = 300;
        var h = 80;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find(".defaultPic").hide();
                el.find(".logoPic").attr("src", path);
                el.find('input').val(path);
            }
        });
    })
    if(!logo){
        $('.defaultPic').show()
    }
    $('.tips a').hover(function(){
        $('.logo_tips').show()
    },function(){
        $('.logo_tips').hide()
    })

    $('.btn_link').click(function(e){
        e.preventDefault()
        var _this = $(this);
        layui.use('layer', function(){
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type:2,
                title: ['添加链接', 'text-align:center;padding: 0px;'],
                offset: 'auto',
                area: ['800px','470px'],
                content: '/admin/frame/decoration/link',
                btn: ['确定', '取消'],
                btnAlign: 'r' ,
                shade: [0.3, '#000'],
                yes:function(index, layero){
                    var iframe = layer.getChildFrame('body', index);
                    var link = iframe.contents().find('tr[data-back="false"]').find(".link").text();
                    $('.logo_link').val(link);
                    layer.close(index);
                }
            })
        })
    });
    $('input[name="show_logo"]').click(function () {
        if($(this).val() == 0){
            $('.add_front_end_img').hide();
            $('.tips').hide();
            $('.logo_size').hide();
            $('.choose_link').hide();
        }else {
            $('.add_front_end_img').show();
            $('.tips').show();
            $('.logo_size').show();
            $('.choose_link').show();
        }
    })
    $('.btn-save5').click(function () {
        var act = $('input[name="act"]').val();
        if(act == 'com'){
            if (parseInt($('[name="share_action"]:checked').val()) == 2) {
                if ($('[name="share_doc"]').val() == '') {
                    util.mobile_alert('请选择文案');
                    return false;
                }
                if (parseInt($('[name="share_img_action"]:checked').val()) == 2 &&
                    $('[name="share_img"]').val() == '') {
                    util.mobile_alert('请上传图片');
                    return false;
                }
            }
            $('#form1').submit();
        }else if(act == 'base'){

        }
    })

    $('.save4').click(function () {
        if($("input[name='custom']").parent().parent().attr("img_id") == 1){
            if($("input[name='custom_title']").val().length <= 0){
                util.mobile_alert("请输入自定义信息标题");
                return false;
            }
            if($("input[name='custom_title']").val().length>6){
                util.mobile_alert("自定义信息标题不得超过6个字");
                return false;
            }
        }
        if($("input[name='service_terms']").parent().parent().attr("img_id") == 1){
            if($("input[name='service_name']").val().length <= 0){
                util.mobile_alert("请输入条款名称");
                return false;
            }
        }

        $('#form4').submit();
    })
    $('input[type="checkbox"]').click(function(){
        if($(this).attr('class') == 'iscom') return;
		if($(this).attr('class') == 'sr') return;
        if($(this).attr('class') == 'lineprice'){
            if($(this).prop('checked')){
                $('.other_content').show();
            }else{
                $('.other_content').hide();
            }
        }
        if($(this).attr('class') == 'showcart'){
            if($(this).prop('checked')){
                $('.btn_cart').show();
            }else{
                $('.btn_cart').hide();
            }
        }
        let str = $(this).prop('checked') ? "已开启" : "已关闭";
        $(this).parent().parent().next().text(str);
        if($(this).prop('checked')){
            $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
            $(this).next().animate({right:"0px"});
            $(this).attr("aid","1");
        } else {
            $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
            $(this).next().animate({right:"20px"});
            $(this).attr("aid","0");
        }
    })
    function check_checked_status(){
        $('input[type="checkbox"]').each(function(){
            if($(this).attr('class') == 'iscom') return;
			if($(this).attr('class') == 'sr') return;
            if($(this).attr('class') == 'lineprice'){
                if($(this).prop('checked')){
                    $('.other_content').show();
                }else{
                    $('.other_content').hide();
                }
            }
            if($(this).attr('class') == 'showcart'){
                if($(this).prop('checked')){
                    $('.btn_cart').show();
                }else{
                    $('.btn_cart').hide();
                }
            }
            let str = $(this).prop('checked') ? "已开启" : "已关闭";
            $(this).parent().parent().next().text(str);
        })
    }
    check_checked_status();

    $('.setting-head-ul').on('click','li',function(){
        $(this).addClass('head-active').siblings().removeClass('head-active');
        $(this).parent().next().find('._content').eq($(this).index()).show().siblings().hide();
    });
    $('.add_sms_account').click(function () {
        var sms_account = $('[name="sms_account"]').val();
        if (sms_account == '') {
            util.mobile_alert('请填写账号名称');
            return false;
        }
        util.ajax_json("/wechat/mini/create/smsaccount", function (res) {
            if (res.error == 0) {
                util.mobile_alert('创建成功');
                location.reload();
            } else {
                layer.msg(res.message);
            }
        }, {
            sms_account: sms_account
        });
    })
    function save_shipping_cart() {
        var shipping_recommend = [];
        var checked = $('[name="enabled_wx_shopping_list"]').prop('checked');
        $('[name="shipping_recommend[]"]:checked').each(function () {
            shipping_recommend.push($(this).val());
        })
        util.ajax_json("/wechat/mini/switch/shopping/list", function () {

            layer.msg("设置成功");
        }, {
            enabled_wx_shopping_list: checked ? 1 : 0,
            wx_shopping_recommend: shipping_recommend.join(',')
        });
    }

    function submit_apply_plugin() {
        util.ajax_json("/wechat/mini/apply/plugin", function (response) {
            if (response.error == 0) {
                layer.msg("设置成功");
                location.reload();
            } else {
                layer.msg(response.message);
            }
        });
    }
	
	$('[name="enabled_wx_shopping_list"]').change(function () {
        var checked = $(this).prop('checked');
        if (checked) {
            $(this).parents(".check-box-container").addClass("checked");
        } else {
            $(this).parents(".check-box-container").removeClass("checked");
        }
        util.ajax_json("/wechat/mini/switch/shopping/list", function () {
            layer.msg("设置成功");
        }, {
            enabled_wx_shopping_list: checked ? 1 : 0
        });
    });
</script>
<script language="JavaScript" src="/js/admin/common_share.js?v=1.1.1"></script>
<#include "/admin/footer.ftl">