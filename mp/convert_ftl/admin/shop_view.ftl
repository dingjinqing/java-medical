<#include "/admin/header.ftl">
    <link rel="stylesheet" href="/css/admin/shop_setting.css?v=1.3" type="text/css" />
    <link rel="stylesheet" href="/css/admin/coupon_manage.css?v=1.0.1">
    <link href="/css/admin/iconfont.css?v=1.0.1" rel="stylesheet">
<style type="text/css">
    .content1-pay {
        width: 892px;
    }
    .default_sort{
        width: 120px;
        height:30px;
        line-height: 30px;
        border:1px solid #ccc;
    }
    .text_set{
        height: 60px;
        line-height: 60px;
        color: #666;
        padding-left: 8px;
        font-size: 13px;
    }
    input[name="service_name"]{
        border: 1px solid #ccc;
        height: 30px;
        width: 165px;
        margin: 0 5px;
    }
    .content5{
        margin-top:20px;
    }
    .content5 ul {
        display: block;
    }
</style>
<div class="title">
    <span>基础配置 / </span><span style="color: #666;">交易配置</span>
</div>
<div class="order-container" style="padding-bottom: 80px">
    <div class="setting-head fix_every_footer">
        <ul class="setting-head-ul clearfix">
            <li <#if ($act == 'pay')class="head-active"</#if>><a href="##">支付配置</a></li>
            {{--<li <#if ($act == 'deliver')class="head-active"</#if>><a href="##">配送方式配置</a></li>--!}
            <li <#if ($act == 'order')class="head-active"</#if>><a href="##">订单流程相关配置</a></li>
            <li <#if ($act == 'draw')class="head-active"</#if>><a href="##">退换货配置</a></li>
            {{--<li <#if ($act == 'other')class="head-active"</#if>><a href="##">其他配置</a></li>--!}
        </ul>
        <div class="setting-content">
            <div class="_content content1"  <#if ($act == 'pay')style="display: block" </#if>>
                <form action="/admin/config/trade?act=pay" method="post" id="form1">
                    {{ csrf_field()!}
                    <div class="content1-pay">
                        <ul>
                            <li class="con1-pay-list clearfix">
                                <span class="fl mr_10">微信支付</span>
                                <div class="fl pay_fl" img_id="${pay->wxpay == 1 ? 1 : 0!}"  <#if ($pay->wxpay == 1)style='background: url("http://${image_domain!}/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if>>
                                    <label>
                                        <input type="checkbox" name="wxpay" aid="" <#if ($pay->wxpay == 1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($pay->wxpay == 1)style="right:0px;"</#if>/>
                                    </label>
                                </div>
                                <span></span>
                                <span class="pay-text">
                                    开关开启，用户可以通过微信支付完成订单支付
                                </span>
                                <a href="##" data-method="offset" data-type="weixin" class="pay-config fr layui-btn layui-btn-normal" data-title="微信支付配置" data-id="wx-pay">配置</a>
                            </li>
                            <li class="con1-pay-list clearfix">
                                <span class="fl mr_10">积分支付</span>
                                <div class="fl pay_fl" img_id="${pay->score == 1 ? 1 : 0!}" <#if ($pay->score == 1)style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if>>
                                    <label>
                                        <input type="checkbox" name="score" aid="" <#if ($pay->score == 1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($pay->score == 1)style="right:0px;"</#if>/>
                                    </label>
                                </div>
                                <span></span>
                                <span class="pay-text">
                                    开关开启，用户购买商品时可以用积分抵扣一定金额
                                </span>
                            </li>
                            <li class="con1-pay-list clearfix">
                                <span class="fl mr_10">余额支付</span>
                                <div class="fl pay_fl" img_id="${pay->balance == 1 ? 1 : 0!}" <#if ($pay->balance == 1)style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if>>
                                    <label>
                                        <input type="checkbox" name="balance" aid="" <#if ($pay->balance == 1) checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($pay->balance == 1)style="right:0px;"</#if> />
                                    </label>
                                </div>
                                <span></span>
                                <span class="pay-text">
                                    开关开启，用户可以使用余额进行支付
                                </span>
                            </li>
                            <li class="con1-pay-list clearfix" >
                                <span class="fl mr_10">货到付款</span>
                                <div class="fl pay_fl" img_id="${pay->cod == 1 ? 1 : 0!}" <#if ($pay->cod == 1)style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if>>
                                    <label>
                                        <input type="checkbox" name="cod" aid="" <#if ($pay->cod == 1) checked </#if>  />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($pay->cod == 1)style="right:0px;"</#if>/>
                                    </label>
                                </div>
                                <span></span>
                                <span class="pay-text">
                                    开关开启，用户在提交订单时可以使用货到付款方式支付；开关关闭则不显示货到付款支付方式
                                </span>
                            </li>
                        </ul>
                    </div>
                    <button class="btn-save">保存</button>
                </form>
            </div>
            <div class="_content content5" <#if ($act == 'order')style="display: block" </#if>>
                <form action="/admin/config/trade?act=order" method="post" id="form5">
                    {{ csrf_field()!}
                    <div class="content1-pay">
                        <ul>
                            <li class="con1-pay-list clearfix">
                                <span class="fl mr_10">快递</span>
                                <div class="fl pay_fl" img_id="${express == 1 ? 1 : 0!}"  <#if ($express == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                    <label>
                                        <input type="checkbox" name="express" aid=""  <#if ($express == 1)checked </#if>/>
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($express == 1) style="right:0px" </#if>/>
                                    </label>
                                </div>
                                <span></span>
                                <span class="pay-text">
                                    启用后，卖家下单可以选择快递发货，由你安排快递送货上门
                                </span>
                            </li>
                            <li class="con1-pay-list clearfix">
                                <span class="fl mr_10">自提</span>
                                <div class="fl pay_fl" img_id="${fetch == 1 ? 1 : 0!}" <#if ($fetch == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                    <label>
                                        <input type="checkbox" name="fetch" aid="" <#if ($fetch == 1)checked </#if> />
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($fetch == 1) style="right:0px" </#if>/>
                                    </label>
                                </div>
                                <span></span>
                                <span class="pay-text">
                                    启用上门自提功能后，买家可以就近选择你预设的自提门店进行提货。默认所有门店均可自提
                                </span>
                                <a href="##" data-method="third" data-type="since" class="pay-config fr layui-btn layui-btn-normal" data-title="设置自提门店" data-id="store">设置自提门店</a>
                            </li>
                        </ul>
                    </div>
                    <div class="text-prompt"><span class="blue_border"></span>发货后自动确认收货时间设置</div>
                    <div class="text-set">发货后<input type="text" name="drawback_days" value="${drawback_days!}" onkeyup="value=value.replace(/[^\d\.]/g,'')" />天，自动确认收货</div>
                    <div class="text-prompt"><span class="blue_border"></span>确认收货后自动订单完成时间设置<span>(订单完成则不可退换货)</span></div>
                    <div class="text-set">确认收货后<input type="text" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="order_timeout_days" value="${order_timeout_days!}" />天，订单完成</div>
                    <button class="btn-save">保存</button>
                </form>
            </div>
            <div class="_content content2" <#if ($act == 'draw')style="display: block" </#if>>
                <form action="/admin/config/trade?act=draw" method="post" id="form2">
                    {{ csrf_field()!}
                    <div class="con2-head clearfix">
                        <div class="fl">退货配置</div>
                        <div class="fl">
                            <label for="yes-re">
                                <#if ($drawback_type ==1)
                                    <img src="http://${image_domain!}/image/admin/check_yes.png" alt="yes" class="re-radio" />
                                <#else>
                                    <img src="http://${image_domain!}/image/admin/check_no.png" alt="no" class="re-radio" />
                                </#if>
                                <input type="radio" name="drawback_type"  value='1' id="yes-re" <#if ($drawback_type ==1) checked </#if> />
                                &nbsp;&nbsp;支持退换货
                            </label>
                            <label for="no-re">
                                <#if ($drawback_type ==0)
                                    <img src="http://${image_domain!}/image/admin/check_yes.png" alt="no" class="re-radio" />
                                <#else>
                                    <img src="http://${image_domain!}/image/admin/check_no.png" alt="no" class="re-radio" />
                                </#if>
                                <input type="radio" name="drawback_type"  value='0' id="no-re" <#if ($drawback_type ==0) checked </#if> />
                                &nbsp;&nbsp;不支持退换货
                            </label>
                        </div>
                    </div>
                    <div style="width: 104%;height: 10px;background: #e6e9f0;margin-left: -20px;"></div>
                    <div style="padding:15px 0">
                        <div class="text-prompt"><span class="blue_border"></span>自动退款/退货设置</div>
                        <div class="text-set" style="height: auto; line-height: 40px;">
                            <div class="radio_input">
                                <input type="radio" name="auto_return"   value="1" <#if ($auto_return == 1) checked </#if>>开启
                                <input type="radio" name="auto_return"  value="0" <#if ($auto_return == 0) checked </#if>>关闭
                            </div>
                            <div <#if ($auto_return != 0) style="display: none" </#if> class="auto_return_close">
                                <div>商家同意退款退货，买家在7日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。</div>
                            </div>
                            <div <#if ($auto_return != 1) style="display: none" </#if> class="auto_return_open">
                                <span style="color: #999">注：默认自动退款/退货处理时间为7日，填写0表示不设置</span>
                                <div style="color: #333">1、买家发起仅退款申请后，商家在<input type="text" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="return_momey_days" value="${return_momey_days==="" ? 7 : $return_momey_days!}" />日内未处理，系统将自动退款。</div>
                                <div style="color: #333">2、商家已发货，买家发起退款退货申请，商家在<input type="text" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="return_address_days" value="${return_address_days==="" ? 7 : $return_address_days!}" />日内未处理，系统将默认同意退款退货，并自动向买家发送商家的默认收货地址。</div>
                                <div style="color: #333">3、买家已提交物流信息，商家在<input type="text" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="return_shopping_days" value="${return_shopping_days==="" ? 7 :$return_shopping_days!}" />日内未处理，系统将默认同意退款退货，并自动退款给买家。</div>
                                <div style="color: #333">4、商家同意退款退货，买家在7日内未提交物流信息，且商家未确认收货并退款，退款申请将自动完成。</div>
                            </div>
                        </div>
                        <div class="text-prompt" hidden><span class="blue_border"></span>退款密码设置</div>
                        <div class="text-set" style="height: 170px;" hidden>
                            <div class="radio_input">
                                <input type="radio" name="return_pass"   value="1" <#if ($return_pass == 1) checked </#if>>开启
                                <input type="radio" name="return_pass"  value="0" <#if ($return_pass == 0) checked </#if>>关闭
                            </div>
                            <span  style="color: #999">注：除主账号外，子账号如操作退款/退货需输入“退款密码</span>
                            <div  style="color: #333"><span>退款密码：</span><input type="password" value="${return_password!}" readonly style="border: none;background-color: white;" name="return_password">  <div style="width: 60px;height: 30px;border: 1px solid #CCCCCC;background: white;color: black;line-height: 28px;display: inline-block;margin-left: 20px;padding-left: 17px;" class="modify">修改</div></div>
                        </div>
                        <div class="text-prompt"><span class="blue_border"></span>商家默认收货地址</div>
                        <div class="text-set" style="height: 250px;" id="re_address">
                            <div class="re_address">&nbsp;&nbsp;<span>  收件人：</span><input type="text"  name="consignee" value="${business_adress['consignee']!}" style=""/></div>
                            <div class="re_address"><span>收件电话：</span><input type="text" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="merchant_telephone" value="${business_adress['merchant_telephone']!}" /></div>
                            <div class="re_address">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>    邮编：</span><input type="text" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="zip_code" value="${business_adress['zip_code']!}" /></div>
                            <div class="re_address"><span>退货地址：</span><input type="text"  name="return_address" value="${business_adress['return_address']!}" /></div>
                        </div>
                    </div>
                    <div class="save2 fix_footer">
                        <a href="##" class="btn-save ">保存</a>
                    </div>
                </form>
            </div>
            <div class="_content content4" <#if ($act == 'other')style="display: block" </#if>>
                <form action="/admin/config/trade?act=other" method="post" id="form4">
                    {{ csrf_field()!}
                    <div>
                        <div class="text-prompt"><span class="blue_border"></span>待付款订单取消时间设置</div>
                        <div class="text-set">拍下未付款订单<input type="text" name="cancel_time" value="${cancel_time!}" />分钟内未付款，自动取消订单</div>
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
                        <div class="text-prompt"><span class="blue_border"></span>绑定手机号设置</div>
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
                        <div class="text-prompt"><span class="blue_border"></span>默认平台分类</div>
                        <div class="text-set clearfix">
                            平台分类：
                            <select name="" id="" class="default_sort">
                                <option value="0">平台分类1</option>
                            </select>
                        </div>
                         {{--购买按钮展示--!}
                        <div class="text-prompt"><span class="blue_border"></span>购买按钮展示设置</div>
                        <div class="text-set clearfix" style="min-height: 60px !important;height: auto !important;">
                            <div class="fl pay_fl" img_id="${show_cart['show_cart'] == 1 ? 1 : 0!}" <#if ($show_cart['show_cart'] == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                <label>
                                    <input type="checkbox" name="show_cart" <#if ($show_cart['show_cart'] ==1) checked </#if> id="cart_btn"/>
                                    <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($show_cart['show_cart'] ==1) style="right:0px" </#if>  />
                                </label>
                            </div>
                            <span class="con4-hide"></span>
                            <span>开关开启，商品搜索页以及推荐商品列表中会显示购买按钮</span>
                            <div class="btn_cart <#if ($show_cart['show_cart'] != 1) hide </#if>">
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
                        {{--划线价展示--!}
                        <div class="text-prompt"><span class="blue_border"></span>划线价(市场价)展示设置</div>
                        <div class="text-set clearfix">
                            <div class="fl pay_fl" img_id="${del_market == 1 ? 1 : 0!}" <#if ($del_market == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                <label>
                                    <input type="checkbox" name="del_market" aid="" <#if ($del_market ==1) checked </#if> />
                                    <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($del_market ==1) style="right:0px" </#if>  />
                                </label>
                            </div>
                            <span class="con4-hide"></span>
                            <span>开关开启，商品搜索页以及推荐商品列表中会显示商品划线价（市场价）。注：为保证页面展示效果，若开启购买按钮，则小程序前端不会显示划线价</span>
                        </div>
                        {{--客服按钮配置--!}
                        <div class="text-prompt"><span class="blue_border"></span>客服展示设置</div>
                        <div class="text-set clearfix">
                            <div class="fl pay_fl" img_id="${custom_service == 1 ? 1 : 0!}" <#if ($custom_service == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;' </#if>>
                                <label>
                                    <input type="checkbox" name="custom_service" aid="" <#if ($custom_service ==1) checked </#if> />
                                    <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($custom_service ==1) style="right:0px" </#if>  />
                                </label>
                            </div>
                            <span class="con4-hide"></span>
                            <span>开关开启，商品详情页会展示小程序客服入口</span>
                            <span style="margin-left: 10px;"><a href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=685&fromuid=1" target="_blank" class="to_bbc" style="color: #5a8bff;">功能介绍</a></span>
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
                                    <#if ($service_choose ==1)
                                        <img src="http://${image_domain!}/image/admin/check_yes.png" alt="yes" class="re-radio" />
                                    <#else>
                                        <img src="http://${image_domain!}/image/admin/check_no.png" alt="no" class="re-radio" />
                                    </#if>
                                    <input type="radio" name="service_choose"  value='1'  <#if ($service_choose ==1) checked </#if> />
                                    &nbsp;&nbsp;是
                                </label>
                                <label for="no-re">
                                    <#if ($service_choose ==0)
                                        <img src="http://${image_domain!}/image/admin/check_yes.png" alt="no" class="re-radio" />
                                    <#else>
                                        <img src="http://${image_domain!}/image/admin/check_no.png" alt="no" class="re-radio" />
                                    </#if>
                                    <input type="radio" name="service_choose"  value='0'  <#if ($service_choose ==0) checked </#if> />
                                    &nbsp;&nbsp;否
                                </label>
                            </div>

                        </div>
                        <div class="text-prompt"><span class="blue_border"></span>下单必填信息设置</div>
                        <div class="text-set clearfix" style="height: 270px;line-height: 50px">
                            <ul>
                                <li>
                                    <div class="fl order_must_set">下单人真实姓名</div>
                                    <div class="fl pay_fl" img_id="${order_real_name == 1 ? 1 : 0!}" <#if ($order_real_name == 1) style='background: url("/image/admin/on_1.png") left top / 100% 100% no-repeat;margin-top: 0px' </#if>>
                                        <label>
                                            <input type="checkbox" name="order_real_name" aid="" <#if ($order_real_name ==1) checked </#if> />
                                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"<#if ($order_real_name ==1) style="right:0px" </#if>  />
                                        </label>
                                    </div>
                                    <span class="con4-hide"></span>
                                    <span>开关开启，用户下单时须填写下单人真实姓名</span>
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
                                    <span>开关开启，用户下单时须填写下单人身份证号码</span>
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
                                    <span>开关开启，用户下单时须填写收货人真实姓名</span>
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
                                    <span>开关开启，用户下单时须填写收货人身份证号码</span>
                                </li>
                                <li>
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
                <div class="fix_footer">
                    <span class="btn_footer_save btn-save save4" style="width: 90px;line-height: 28px">保存</span>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="set-wx-pay">
        <table width="100%">
            <tr>
                <td>appid:</td>
                <td><input type="text" name="app_id"  value="${wxpay->app_id!}"/></td>
            </tr>
            {{--<tr>--!}
                {{--<td>appsecret:</td>--!}
                {{--<td><input type="text" name="app_secret"  value="${wxpay->app_secret!}"/></td>--!}
            {{--</tr>--!}
            <tr>
                <td>商户号:</td>
                <td><input type="text" name="pay_mch_id" value="${wxpay->pay_mch_id!}"/></td>
            </tr>
            <tr>
                <td>支付秘钥:</td>
                <td><input type="text" name="pay_key" value="${wxpay->pay_key!}" /></td>
            </tr>
            <tr>
                <td>支付证书:</td>
                <td class="wx-file">
                    <textarea name="pay_cert_content" style="width:240px;height:100px" >${wxpay->pay_cert_content!}</textarea>
                </td>
            </tr>
            <tr>
                <td>支付私钥:</td>
                <td class="wx-file">
                    <textarea name="pay_key_content" style="width:240px;height:100px"/>${wxpay->pay_key_content!}</textarea>
                </td>
            </tr>
        </table>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script src="/js/admin/util.js" type="text/javascript"></script>
<script>
    var shop_id = '${res!}';
    var set_colors = @json($shop_style);
    $(".new_class").css("color",set_colors[0]);
    $(".border_class").css("border-color",set_colors[0]);
    $(".new_back").css("background-color",set_colors[0]);
    $('#cart_btn').click(function(){
        if($(this).prop('checked') == true){
            $('.btn_cart').removeClass('hide');
        }else{
            $('.btn_cart').addClass('hide');
        }
    })

    $(".set_pay").click(function(){
        var flag = 0;
        if($('input[name="wxpay"]').prop('checked')){
            $('#set-wx-pay input').each(function(){
                if($(this).val()==''){
                    flag++;
                }
            });
            $('#set-wx-pay textarea').each(function(){
                if($(this).val()==''){
                    flag++;
                }
            });
        }
        if(flag == 0){
            $('#form1').submit();
        }
        else{
            util.mobile_alert('支付配置信息不完整');
        }
    });
    $('.setting-head-ul').on('click','li',function(){
        $(this).addClass('head-active').siblings().removeClass('head-active');
        $(this).parent().next().find('._content').eq($(this).index()).show().siblings().hide();
    });
    $('.content5 .auth-tab').on ('click', 'li', function () {
        $(this).addClass('head-active').siblings().removeClass('head-active');
        $(this).parent().parent().find('ul').hide();
        $(this).parent().parent().find('ul').eq($(this).index()).show();
    })
    $(".draggable").click(function(){

        var img_id=$(this).parent().parent().attr("img_id");

        if(img_id==0 ){
            $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
            $(this).animate({right:"0px"});
            $(this).prev().attr("aid","1");
            $(this).prev().prop('checked',false);
        }
        else if(img_id==1){
            $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
            $(this).animate({right:"20px"});
            $(this).prev().attr("aid","0");
            $(this).prev().prop('checked',true);
        }

    });

    $('input[name="drawback_type"]').click(function(){
//        alert($(this).prev().attr('alt'));
        if($(this).is(':checked')){
            $(this).parent().parent().find('img').attr('src','/image/admin/check_no.png');
            $(this).prev().attr('src','/image/admin/check_yes.png');
        }
    });
    if($('input[name="service_terms"]').parent().parent().attr("img_id") == 1){
        $(".service_config").css("display",'block')
    }else{
        $(".service_config").css("display",'none')
    }
    $('input[name="service_terms"]').click(function () {
        if($('input[name="service_terms"]').parent().parent().attr("img_id") == 1){
            $(".service_config").css("display",'block');
        }else{
            $(".service_config").css("display",'none');
        }
    });
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        //触发事件
        var active = {
            offset: function(othis){
                var type = othis.data('type')
                    ,text = othis.data('title')
                    ,id = othis.data('id');
                layer.open({
                    type: 1
                    ,title: [text,'text-align:center;padding: 0']
                    ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    ,area: '680px'
                    ,id: 'layerDemo'+type //防止重复弹出
                    ,content: $('#set-' + id) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,btn: ['保存','取消']
                    ,btnAlign: 'r' //按钮居右
                    ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,yes: function(index, layero){ //保存按钮的回调
                        var flag = 0;
                        var data={};
                        $('#set-wx-pay input').each(function(){
                            if($(this).val()==''){
                                flag++;
                            }
                            data[$(this).attr('name')] = $(this).val();
                        });
                        $('#set-wx-pay textarea').each(function(){
                            if($(this).val()==''){
                                flag++;
                            }
                            data[$(this).attr('name')] = $(this).val();
                        });
                        if(flag == 0){
                            util.ajax_json('/admin/ajax/shop/pay',function(d){
                                if(d&&d.error == 0){
                                    util.mobile_alert('保存成功');
                                    layer.close(index);
                                }
                                else{
                                    util.mobile_alert(d.message);
                                }
                            },data)
                        }
                        else{
                            util.mobile_alert('支付配置信息不完整');
                        }

                    },btn2: function(index, layero){
                        //按钮取消的回调

                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            }
            ,third: function(othis){
                var type = othis.data('type')
                    ,text = othis.data('title')
                    ,id = othis.data('id');
                layer.open({
                    type: 2
                    ,title: [text,'text-align:center;padding: 0']
                    ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    ,area: ['750px','430px']
                    ,id: 'layerDemo'+type //防止重复弹出
                    ,content: '/admin/frame/shop/list' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,btn: ['保存','取消']
                    ,btnAlign: 'r' //按钮居右
                    ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,yes: function(index, layero){ //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var list={};
                        var i = 0;
                        iframe.contents().find('tr[auto="true"]').each(function(){
                            list[i]={'userid':$(this).attr('store_id'),'auto':$(this).find(".pay_fl").attr('img_id')};
                            i++;
                        });
                        // if(i){
                            util.ajax_json('/admin/ajax/shop/storeupdate',function(d){
                                if(d&&d.error == 0){
                                    util.mobile_alert(d.content);
                                    layer.close(index);
                                }else{
                                    util.mobile_alert(d.message);
                                }
                            },list)
                        // }else{
                        //     util.mobile_alert('请修改设置');
                        // }
                    },btn2: function(index, layero){
                        //按钮取消的回调

                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            }
        };
        $('.layui-btn').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
    });
    $(".sync_store").click(function () {
        layer.msg('同步中...', {time: 10000});
    })

    $('input[name="auto_return"]').click(function () {
        if($(this).val() == 0){
            $('.auto_return_close').show();
            $('.auto_return_open').hide();
        }else {
            $('.auto_return_close').hide();
            $('.auto_return_open').show();
        }
    })

    $('input[ name="return_pass"]').click(function () {
        if("${sub_account_id!}" != 0){
            util.mobile_alert("此功能需主账号权限");
            return false;
        }else{
            if($(this).val() == 0){
                $('input[name="return_password"]').val("");
            }else{
                $("#alert_pass").val($('input[name=return_password]').val());
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.alert('<div style="margin-left: 60px;margin-top: 30px">退款密码：<input type="password" value="" style="width:160px;height:30px;padding-left: 5px" id="alert_pass"></div>', {
                        title: ['退款密码设置', 'text-align:center;padding: 0px;']
                        , area: ['400px','200px']
                        , closeBtn: 0
                        , btn: ['确定', '取消']
                    }, function (index) {
                        $('input[name=return_password]').val($("#alert_pass").val());
                        // util.ajax_json('/admin/market/pingroup/list', function (response) {
                        //     if (response.error == 0) {
                        //         $("#form1").submit();
                        //     } else {
                        //         util.mobile_alert(response.message);
                        //     }
                        // }, param)
                        layer.close(index);
                    });
                });
            }
        }
    })
    $(".save2").click(function () {
        if($('input[name="auto_return"]:checked').val() == 1){
            if($('input[name="return_momey_days"]').val()<1 || $('input[name="return_address_days"]').val()<1 || $('input[name="return_shopping_days"]').val()<1){
                util.mobile_alert("请确认填写自动退款退货天数");
                return false;
            }
            if(!$('input[name="consignee"]').val() ||!$('input[name="merchant_telephone"]').val() || !$('input[name="return_address"]').val()){
                util.mobile_alert("请确认填写默认收货地址");
                return false;
            }
        }
        // if($('input[name="return_pass"]:checked').val() == 1){
        //     if(!$('input[name="return_password"]').val()){
        //         util.mobile_alert("请填写退款密码");
        //         return false;
        //     }
        // }
        $("#form2").submit();
    })
    $(".modify").click(function () {
        if("${sub_account_id!}" != 0){
            util.mobile_alert("此功能需主账号权限");
            return false;
        }else{
            $("#alert_pass").val($('input[name=return_password]').val());
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="margin-left: 60px;margin-top: 30px">退款密码：<input type="password" value="" style="width:160px;height:30px;padding-left: 5px" id="alert_pass"></div>', {
                    title: ['退款密码设置', 'text-align:center;padding: 0px;']
                    , area: ['400px','200px']
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    $('input[name=return_password]').val($("#alert_pass").val());
                    // util.ajax_json('/admin/market/pingroup/list', function (response) {
                    //     if (response.error == 0) {
                    //         $("#form1").submit();
                    //     } else {
                    //         util.mobile_alert(response.message);
                    //     }
                    // }, param)
                    layer.close(index);
                });
            });
        }
    })
    $('input[type="checkbox"]').click(function(){
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
            let str = $(this).prop('checked') ? "已开启" : "已关闭";
            $(this).parent().parent().next().text(str);
        })
    }
    $("input[name='custom_title']").blur(function () {
        if($("input[name='custom_title']").val().length>6){
            util.mobile_alert("自定义信息标题不得超过6个字");
            return false;
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
    check_checked_status();
    if(shop_id > 0){
        layer.ready(function () {
            layer.msg('保存成功', {time: 3000});
        });
    }else if(shop_id==0){
        util.mobile_alert('保存失败');
    }

</script>
