<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css"/>
<link rel="stylesheet" href="/css/admin/common_share.css?v=1.2.1" type="text/css" />

<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/new/market?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">定金膨胀</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/presale/list?nav=0">全部定金膨胀</a>
                </li>
                <li >
                    <a href="/admin/market/presale/list?nav=1">进行中</a>
                </li>
                <li >
                    <a href="/admin/market/presale/list?nav=2">未开始</a>
                </li>
                <li >
                    <a href="/admin/market/presale/list?nav=3">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/presale/list?nav=4">已停用</a>
                </li>
                <li class="active">
                    <a>${title!}</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="presale_info">
                <input type="text" name="id" value="${preSale->id!}" hidden>
                <input type="text" name="cannot_edit" value="${preSale->cannot_edit!}" hidden>
                {{--活动类型--!}
                <div class="clearfix">
                    <div class="pi_title"><em>*</em> 活动类型：</div>
                    <div class="pi_content">
                        <input type="radio" name="presale_type" value="0" checked <#if  ($preSale->cannot_edit) disabled </#if>>定金膨胀
                        <input type="radio" name="presale_type" value="1" <#if  ($preSale->presale_type == 1) checked </#if> <#if  ($preSale->cannot_edit) disabled </#if>>全款预售
                    </div>
                </div>
                {{--                {{dd($preSale)!}--!}
                {{--活动名称--!}
                <div class="clearfix">
                    <div class="pi_title"><em>*</em> 活动名称：</div>
                    <div class="pi_content">
                        <input type="text" name="presale_name" value="${preSale->presale_name!}" class="act_names"
                               placeholder="请输入活动名称">
                        <span class="config_tips">只作为商家记录使用，用户不会看到这个名称</span>
                    </div>
                </div>
                <input type="text" name="pre_pay_step" value="${preSale->pre_pay_step ?? 1!}" hidden>
                {{--定金膨胀的时间设置--!}
                <div class="clearfix presale_type_0" style="margin-bottom: 10px">
                    <div class="pi_title"><em>*</em> 活动时间：</div>
                    <div class="pi_content">
                        <span class="config_tips">请设置定金支付时间以及尾款支付时间，最多可配置两个支付定金时段，定金支付的截止时间不能大于尾款支付的截止时间</span>
                    </div>
                </div>
                {{--定金,只显示一个或二个--!}
                <div class="clearfix some_money presale_type_0">
                    <div class="pi_title"></div>
                    <div class="pi_content">
                        <div class="pay_times1">
                            <span><em>*</em> 定金支付时间：</span>
                            <input type="text" class="pre_start_time" value="<#if ($preSale->presale_type != 1) ${preSale->pre_start_time!} </#if>" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>至
                            <input type="text" class="pre_end_time" value="<#if ($preSale->presale_type != 1) ${preSale->pre_end_time!} </#if>" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>
                            <a href="javascript:void(0);" class="add_tr" <#if  ($preSale->cannot_edit || $preSale->pre_pay_step == 2) hidden </#if>>添加定金支付时段</a>
                        </div>
                    </div>
                </div>
                <div class="clearfix some_money1">
                    <div class="pi_title"></div>
                    <div class="pi_content">
                        <div class="pay_times2">
                            <span><em>*</em> 定金支付时间：</span>
                            <input type="text" name="pre_start_time_2" value="${preSale->pre_start_time_2!}" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>至
                            <input type="text" name="pre_end_time_2" value="${preSale->pre_end_time_2!}" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>
                            <a href="javascript:void(0);" class="delete_pay_times2" <#if  ($preSale->cannot_edit) hidden </#if>>删除</a>
                        </div>
                    </div>
                </div>
                {{--全款--!}
                <div class="clearfix all_money presale_type_0">
                    <div class="pi_title"></div>
                    <div class="pi_content">
                        <span><em>*</em> 尾款支付时间：</span>
                        <input type="text" name="start_time" value="${preSale->start_time!}" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>至
                        <input type="text" name="end_time" value="${preSale->end_time!}" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>
                    </div>
                </div>
                {{--定金膨胀的时间设置结束--!}
                {{--全款预售的时间设置--!}
                <div class="clearfix quankuan presale_type_1">
                    <div class="pi_title"><em>*</em> 定金支付时间：</div>
                    <div class="pi_content">
                        <input type="text" class="pre_start_time" value="<#if ($preSale->presale_type == 1) ${preSale->pre_start_time!} </#if>" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>至
                        <input type="text" class="pre_end_time" value="<#if ($preSale->presale_type == 1) ${preSale->pre_end_time!} </#if>" onclick="picker();" autocomplete="off" <#if  ($preSale->cannot_edit) disabled </#if>>
                    </div>
                </div>
                <input type="text" name="pre_start_time" value="${preSale->pre_start_time!}" hidden>
                <input type="text" name="pre_end_time" value="${preSale->pre_end_time!}" hidden>
                {{--全款预售的时间设置结束--!}
                {{--活动商品--!}
                <div class="clearfix">
                    <div class="pi_title"><em>*</em> 活动商品：</div>
                    <div class="pi_content clearfix">
                        <input type="button" value="+ 选择商品" class="choose_goods" name="choose_goods" <#if  ($preSale->goods_id) hidden </#if>>
                        <input name="goods_id" value="${preSale->goods_id!}" hidden>
                        <span class="select_goods" <#if  (!$preSale->goods_id) hidden </#if>>${preSale->goods_name!}</span>
                        <a href="##" class="change_select" <#if  ((!$preSale->goods_id) || $preSale->cannot_edit) hidden </#if>>修改</a>
                    </div>
                </div>
                {{--商品金额设置--!}
                <div class="clearfix pre_product_div" style="margin-bottom: 0" <#if  (!$preSale->goods_products) hidden </#if>>
                    <div class="pi_title"><em>*</em> 设置商品金额：</div>
                    <div class="pi_content">
                        <input name="goods_products" value="{{\GuzzleHttp\json_encode($preSale->goods_products)!}" hidden>
                    </div>
                </div>
                <div class="clearfix pre_product_div" style="padding: 10px;" <#if  (!$preSale->goods_products) hidden </#if>>
                    <div class="pi_content" style="width: 100%">
                        <table class="pre_product_table">
                            <thead>
                            <tr style="border: 1px solid #eee;">
                                <td width="10%" <#if  ($preSale->goods_products && count($preSale->goods_products) <= 1) hidden </#if>>规格</td>
                                <td width="10%">商品原价(元)</td>
                                <td width="10%">商品库存</td>
                                <td width="10%">活动价格(元)</td>
                                <td width="10%">活动库存</td>
                                <td width="10%" <#if ($preSale->presale_type == 1) hidden </#if>>定金(元)</td>
                                <td width="15%" <#if ($preSale->presale_type == 1) hidden </#if>>1阶段定金可抵扣金额</td>
                                <td width="15%" <#if ($preSale->presale_type == 1 || $preSale->pre_pay_step != 2) hidden </#if>>2阶段定金可抵扣金额</td>
                            </tr>
                            </thead>
                            <tbody>
                            <#if  ($preSale->goods_products)
                                <#list ($preSale->goods_products as $product)
                                    <tr goods_id="${product->goods_id!}" prd_id="${product->product_id!}">
                                        <td width="10%" <#if  ($preSale->goods_products && count($preSale->goods_products) <= 1) hidden </#if>>${product->prd_desc!}</td>
                                        <td width="10%">${product->prd_price!}</td>
                                        <td width="10%">${product->prd_number!}</td>
                                        <td width="10%">
                                            <input type="text" class="act_price" value="${product->presale_price!}" onkeyup="value=value.replace(/[^\d.]/g,'')" <#if  ($preSale->cannot_edit) disabled </#if>>
                                        </td>
                                        <td width="10%">
                                            <input type="text" class="act_number" value="${product->presale_number!}" onkeyup="value=value.replace(/[^\d]/g,'')" <#if  ($preSale->cannot_edit) disabled </#if>>
                                        </td>
                                        <td width="10%" <#if ($preSale->presale_type == 1) hidden </#if>>
                                            <input type="text" class="pre_money" value="${product->presale_money!}" onkeyup="value=value.replace(/[^\d.]/g,'')" <#if  ($preSale->cannot_edit) disabled </#if>>
                                        </td>
                                        <td width="15%" <#if ($preSale->presale_type == 1) hidden </#if>>
                                            <input type="text" class="onece_pre" value="${product->pre_discount_money_1!}" onkeyup="value=value.replace(/[^\d.]/g,'')" <#if  ($preSale->cannot_edit) disabled </#if>>
                                        </td>
                                        <td width="15%" <#if ($preSale->presale_type == 1 || $preSale->pre_pay_step != 2) hidden </#if>>
                                            <input type="text" class="twice_pre" value="${product->pre_discount_money_2!}" onkeyup="value=value.replace(/[^\d.]/g,'')" <#if  ($preSale->cannot_edit) disabled </#if>>
                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                    <div class="btns_common" <#if  ($preSale->cannot_edit) hidden </#if>>
                        批量设置：
                        <a href="javascript:void(0);" class="same_price">活动价格</a>
                        <a href="javascript:void(0);" class="same_num">活动库存</a>
                        <a href="javascript:void(0);" class="same_pre" <#if ($preSale->presale_type == 1) hidden </#if>>定金</a>
                        <a href="javascript:void(0);" class="same_once_money" <#if ($preSale->presale_type == 1) hidden </#if>>1阶段定金可抵扣金额</a>
                        <a href="javascript:void(0);" class="same_twice_money" <#if ($preSale->presale_type == 1 || $preSale->pre_pay_step != 2) hidden </#if>>2阶段定金可抵扣金额</a>
                    </div>
                </div>
                {{--发货时间--!}
                <div class="clearfix">
                    <div class="pi_title"><em>*</em> 发货时间：</div>
                    <div class="pi_content">
                        <div class="send_time1">
                            <input type="radio" name="deliver_type" checked value="0" <#if ($preSale->cannot_edit) disabled </#if>> 指定发货开始时间
                            <input type="text" name="deliver_time" value="${preSale->deliver_time!}" placeholder="请选择发货时间" onclick="picker();" autocomplete="off" <#if ($preSale->cannot_edit) disabled </#if>>
                        </div>
                        <div class="send_time2">
                            <input type="radio" name="deliver_type" <#if ($preSale->deliver_type == 1) checked </#if> value="1"  <#if ($preSale->cannot_edit) disabled </#if>>
                            <span class="deliver_type_span"><#if ($preSale->presale_type != 1)尾款</#if></span>支付完成
                            <input type="text" name="deliver_days" value="${preSale->deliver_days > 0 ? $preSale->deliver_days : ''!}" onkeyup="value=value.replace(/[^\d]/g,'')"  <#if ($preSale->cannot_edit) disabled </#if>>
                            天后发货
                        </div>
                    </div>
                </div>
                {{--优惠叠加--!}
                <div class="clearfix">
                    <div class="pi_title"><em>*</em> 优惠叠加策略：</div>
                    <div class="pi_content">
                        <input type="radio" name="discount_type" value="1" checked  <#if ($preSale->cannot_edit) disabled </#if>>可叠加
                        <input type="radio" name="discount_type" value="0" <#if ($preSale->discount_type != 1) checked </#if>  <#if ($preSale->cannot_edit) disabled </#if>>不可叠加
                        <span class="config_tips raio_tips">预售商品结算时是否可与会员卡折扣、优惠券叠加使用</span>
                    </div>
                </div>
                {{--定金退款--!}
                <div class="clearfix return_type_dev">
                    <div class="pi_title"><em>*</em> 定金退款策略：</div>
                    <div class="pi_content">
                        <input type="radio" name="return_type" value="1" checked <#if ($preSale->cannot_edit) disabled </#if>>自动退回定金
                        <input type="radio" name="return_type" value="0" <#if ($preSale->return_type != 1) checked </#if>  <#if ($preSale->cannot_edit) disabled </#if>>不退定金
                        <span class="config_tips raio_tips">选择自动退回定金，则在指定时间内未支付尾款的订单，将退回定金到原支付账户</span>
                    </div>
                </div>
                {{--预售数量--!}
                <div class="clearfix">
                    <div class="pi_title"><em>*</em> 预售数量展示：</div>
                    <div class="pi_content">
                        <input type="radio" name="show_sale_number" value="1" checked <#if ($preSale->cannot_edit) disabled </#if>>展示
                        <input type="radio" name="show_sale_number" value="0" <#if ($preSale->show_sale_number != 1) checked </#if> <#if ($preSale->cannot_edit) disabled </#if>>不展示
                        <span class="config_tips raio_tips">当前活动商品的预售数量是否展示在商品详情页</span>
                    </div>
                </div>
                {{--商品购买方式--!}
                <div class="clearfix">
                    <div class="pi_title"><em>*</em> 商品购买方式：</div>
                    <div class="pi_content">
                        <input type="radio" name="buy_type" value="1" checked <#if ($preSale->cannot_edit) disabled </#if>>可原价购买
                        <input type="radio" name="buy_type" value="0" <#if ($preSale->buy_type != 1) checked </#if> <#if ($preSale->cannot_edit) disabled </#if>>不可原价购买
                        <span class="config_tips raio_tips">活动进行中是否可直接以原价购买此商品</span>
                    </div>
                </div>
                {{--购买数量限制--!}
                <div class="clearfix">
                    <div class="pi_title">购买数量限制：</div>
                    <div class="pi_content">
                        单用户最多可购买 <input type="text" name="buy_number" value="${preSale->buy_number!}" <#if ($preSale->cannot_edit) disabled </#if>> 件该商品
                        <span class="config_tips raio_tips">单用户可购买活动商品的数量，不填或填写0表示不限制</span>
                    </div>
                </div>
                <div class="clearfix share_module" style="margin-top: 10px;">
                    <div class="pi_title">店铺分享：</div>
                    <div class="fl">
                        <input type="radio" name="share_action" value="1" <#if  (!$module_share || $module_share['share_action'] == 1) checked </#if>/> 默认样式
                        <a href="javascript:;" class="show_eg">查看示例
                            <div class="hover_show">
                                <img src="http://${image_domain!}/image/admin/share/presale_share.jpg"/>
                            </div>
                        </a>
                        <a href="javascript:;" class="show_eg">下载海报
                            <div class="hover_show">
                                <img src="http://${image_domain!}/image/admin/share/presale_pictorial.jpg"/>
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
                            <input type="radio" name="share_img_action" value="1" <#if  (!$module_share || $module_share['share_img_action'] == 1) checked </#if> /> 活动商品信息图
                            <p style="padding-left: 60px;">
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
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer">
            <a class="save_pre_sale" href="javascript:void(0);">保存</a>
        </div>
    </div>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script type="text/javascript" src="{{asset("js/admin/presale_manage.js")!}?v=1.1.2"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/common_share.js?v=1.1.1"></script>

<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    }
    console.log("${preSale->presale_type == 1!}");
    if ("${preSale->presale_type == 1!}") {
        $('.presale_type_0').hide();
        $('.some_money1').hide();
        $('.presale_type_1').show();
    } else {
        $('.presale_type_0').show();
        $('.presale_type_1').hide();
        if ("${preSale->pre_pay_step == 2!}") {
            $('.some_money1').show();
        } else {
            $('.some_money1').hide();
        }
    }
</script>
<script>
    /*getPowerInfo('main_config','pre_sale','sub_4','定金膨胀',0);*/
</script>