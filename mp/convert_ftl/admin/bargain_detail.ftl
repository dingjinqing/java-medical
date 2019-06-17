<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/bargain_detail.css?v=1.0.1" type="text/css" />
<link rel="stylesheet" href="/css/admin/common_share.css?v=1.2.1" type="text/css" />

<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        {{--<a href="/admin/market/bargain/list?nav=1&top_index=4">砍价 </a>--!}
        <span style="color: #666;">砍价</span>
    </div>
    <div class="main-container fix_every_footer">
        <div class="return-pin-group-box">
            <ul class="clearfix">
                <li <#if (empty($nav))class="active"</#if>><a href="/admin/market/bargain/list">全部砍价活动</a></li>
                <li <#if ($nav == 1)class="active"</#if>><a href="/admin/market/bargain/list?nav=1">进行中</a></li>
                <li <#if ($nav == 2)class="active"</#if>><a href="/admin/market/bargain/list?nav=2">未开始</a></li>
                <li <#if ($nav == 3)class="active"</#if>><a href="/admin/market/bargain/list?nav=3">已过期</a></li>
                <li <#if ($nav == 4)class="active"</#if>><a href="/admin/market/bargain/list?nav=4">已停用</a></li>
                <li <#if ($nav == 5)class="active"</#if>><a href="/admin/market/bargain/add?nav=5">${title!}</a></li>
            </ul>
            {{--<div class="add_bargain">添加砍价活动</div>--!}
            <form action="/admin/market/bargain/add" id="form1" method="post">
                {{ csrf_field()!}
                <input name="id" hidden value="${bargain->id!}">
                <div class="tb-pin-group">
                    <div class="clearfix">
                        <div class="fl">
                            <span class="tb-full-left"><strong>*</strong>活动类型：</span>
                        </div>
                        <div class="fl choose_money_type">
                            <label for="same_money">
                                <input type="radio" name="bargain_type" value="0" <#if  ($bargain->bargain_type != 1) checked </#if> id="same_money" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>>砍到指定金额结算
                            </label>
                            <label for="any_money">
                                <input type="radio" name="bargain_type" value='1' <#if  ($bargain->bargain_type == 1) checked </#if> id="any_money" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>>砍到任意金额结算
                            </label>
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="fl">
                            <span class="tb-full-left"><strong>*</strong>活动名称：</span>
                        </div>
                        <div class="fl">
                            <input type="text" name="bargain_name" class="name" placeholder="请输入活动名称"  value="${bargain->bargain_name!}" />
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="fl">
                            <span class="tb-full-left"><strong>*</strong>有效期：</span>
                        </div>
                        <div class="fl">
                            <input type="text" name="start_time" class="tb-text date-text" onclick="picker();"
                                   value="${bargain->start_time!}" AUTOCOMPLETE="off"
                                   <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>/> 至
                            <input type="text" name="end_time" class="tb-text date-text" onclick="picker();"
                                   value="${bargain->end_time!}" AUTOCOMPLETE="off" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>/>
                        </div>
                    </div>
                    <div class="clearfix">
                        <div class="fl">
                            <span class="tb-full-left"><strong>*</strong>活动商品：</span>
                        </div>
                        <div class="fl">
                            <div class="add_goods" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) style="display: none" </#if>>
                                <img src="/image/admin/shop_beautify/add_decorete.png" />
                                <div>选择商品</div>
                            </div>
                            <p class="prompt">所有参与砍价的商品，均需要用户将价格砍到底价后才可以砍价成功，若某商品同一时间段内同时参与了砍价和拼团活动，则优先进行砍价活动</p>
                            <table width="90%" class="bargain_tb" <#if ($bargain->goods_id) style="display: table" </#if>>
                                <tr>
                                    <td>商品名称</td>
                                    <td>商品原库存</td>
                                    <td>砍价库存</td>
                                    <td>商品原价</td>
                                    <td class="same_mon">砍价底价</td>
                                    <td class="any_mon bxianshi">结算金额</td>
                                </tr>
                                <tr>
                                    <input hidden name="goods_id" value="${bargain->goods_id!}" />
                                    <input hidden name="goods_name" value="${bargain->goods_name!}" />
                                    <td>
                                        <div class="goods_img">
                                            <img src="${bargain->goods_img!}" />
                                        </div>
                                        <div class="goods_name">${bargain->goods_name!}</div>
                                    </td>
                                    <td class="goods_num">${bargain->goods_number!}</td>
                                    <td>
                                        <input type="number" name="stock" min="1" value="${bargain->stock > $bargain->goods_number ? $bargain->goods_number : $bargain->stock!}" class="bargain_num" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>/>
                                    </td>
                                    <td>
                                        <div class="goods_price">${bargain->shop_price!}</div>
                                    </td>
                                    <td >
                                        <span class="any_mon bxianshi">
                                            <input type="number" name="floor_price" value="${bargain->floor_price ?? ''!}" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>>至
                                        </span>
                                        <input type="number" name="expectation_price"  class="limit_amount"
                                               value="${bargain->expectation_price ?? ''!}" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>/>（默认为0元）
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    {{--任意金额--!}
                    <div class="clearfix if_any_money bxianshi">
                        <div class="fl">
                            <span class="tb-full-left"><strong>*</strong>单次帮砍金额：</span>
                        </div>
                        <div class="clearfix">
                            <div class="price_type">
                                <label for="one_price">
                                    <input type="radio" name="bargain_money_type" value="0" <#if  ($bargain->bargain_money_type != 1) checked </#if> id="one_price" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>> 固定金额
                                </label>
                                <input type="number" name="bargain_fixed_money" value="${bargain->bargain_fixed_money!}" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>> 元
                            </div>
                            <div class="price_type">
                                <label for="any_price">
                                    <input type="radio" name="bargain_money_type" value="1" id="any_price" <#if  ($bargain->bargain_money_type == 1) checked </#if> <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>> 随机金额
                                </label>
                                <input type="number" name="bargain_min_money" value="${bargain->bargain_min_money!}" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>> 元 &nbsp;&nbsp;&nbsp;&nbsp; 至
                                <input type="number" name="bargain_max_money" value="${bargain->bargain_max_money!}" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>> 元之间取随机金额
                            </div>
                        </div>
                    </div>

                    {{--固定金额--!}
                    <div class="clearfix if_same_money">
                        <div class="fl">
                            <span class="tb-full-left"><strong>*</strong>期望参与砍价人次：</span>
                        </div>
                        <div class="fl ipt_wid">
                            <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')" name="expectation_number"  class="limit_amount"
                                   value="${bargain->expectation_number ?? 100!}" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>/> 人 （期望人次最少为3）
                            <p class="prompt">填写人数为发起人发起砍价后，预计将价格砍到底价时需要参与砍价活动帮助该发起人进行砍价的用户数，默认为100</p>
                        </div>
                    </div>
                    <div class="clearfix if_same_money">
                        <div class="fl">
                            <span class="tb-full-left">商品首次砍价可砍价比例区间：</span>
                        </div>
                        <div class="fl ipt_wid">
                            <div>
                                <input type="number" onkeyup="value=value.replace(/[^\.\d]/g,'')" name="bargain_min" value="${bargain->bargain_min!}"
                                       <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>/> % 至 <input type="number" name="bargain_max" value="${bargain->bargain_max!}" onkeyup="value=value.replace(/[^\.\d]/g,'')" <#if ($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")) disabled </#if>/> %    (比例必须在0~50%之间)
                            </div>
                            <p class="prompt">
                                用户发起砍价后，首次砍价可以砍掉的金额占商品价格的比例 ，该比例在填写区间内随机产生。
                                不填写则按照系统规则计算， 默认为空，为空表示不填写。
                                例如填写20%到50%，商品价格为100元，则用户发起砍价， 首次给自己砍价时，系统会随机取该
                                比例区间数字，例如随机为35%， 则该用户发起砍价后首次砍价金额为100*35%*（系统砍价系数），
                                系统砍价系数按照系统逻辑计算。若系统砍价系数为0.5，则本次砍价金额为100*35%*0.5=17.5元。
                                即该用户给自己 可砍掉17.5元。
                            </p>
                        </div>
                    </div>
                    <div class="show_more show_basic" if_show="0">
                        <text>展开更多配置</text>
                        <img src="http://mpimg.vpubao.com.cn/image/admin/info_down.png" alt="">
                    </div>
                    <div class="som_no_use">
                        <#if (!($bargain->id && $bargain->start_time <= date("Y-m-d H:i:s") && !$bargain->mrking_voucher_id))
                        <div class="clearfix">
                            <div class="fl">
                                <span class="tb-full-left">好友砍价优惠券：</span>
                            </div>
                            <div class="fl ipt_wid">
                                <div class="coupon_content" action="1" style="height: 180px;">
                                    <input type="hidden" name="mrking_voucher_id" value="${bargain->mrking_voucher_id!}">
                                    <div class="tem_right">
                                        <p style="color: #999; padding-bottom: 10px;">向帮忙砍价的用户赠送优惠券，可促使帮砍用户在店铺内下单，提高交易量。</p>
                                        <div class="coupon_div clearfix" action="1" coupon_json="" <#if ($bargain->coupon_list) style="display: block" </#if>>
                                            <#list ($bargain->coupon_list ?? [] as $k => $cou)
                                                <div class="coupon_list">
                                                    <#if (!($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")))
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del" action="1">
                                                    </#if>
                                                    <input type="hidden" coupon_id="${cou->id!}" act_code="${cou->act_code!}" denomination="${cou->denomination!}" class="coupon_info">
                                                    <div class="coupon_list_top">
                                                        <#if ($cou->act_code == 'voucher')
                                                            ¥<span>${cou->denomination!}</span>
                                                        <#elseif>($cou->act_code == 'discount')
                                                            打<span>${cou->denomination!}</span>折
                                                        </#if>
                                                    </div>
                                                    <div class="coupon_list_center">
                                                        <#if ($cou->use_consume_restrict == 0)
                                                            <div class="coupon_center_limit">不限制</div>
                                                        <#else>
                                                            <div class="coupon_center_limit">满${cou->least_consume!}使用</div>
                                                        </#if>
                                                        <div class="coupon_center_number">剩余<span>${cou->surplus!}</span>张</div>
                                                    </div>
                                                    <div class="coupon_list_bottom">
                                                        领取
                                                    </div>
                                                </div>
                                            </#list>
                                            <#if (!($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")))
                                                <div class="card_add card_add_click" action="1">
                                                    <img src="http://mpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png" alt="">
                                                    <p>添加优惠券</p>
                                                </div>
                                            </#if>
                                        </div>
                                        <p style="color:#999999;">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </#if>
                        <#if (!($bargain->id && $bargain->start_time <= date("Y-m-d H:i:s") && !$bargain->reward_coupon_id))
                            <div class="clearfix">
                                <div class="fl">
                                    <span class="tb-full-left">鼓励奖：</span>
                                </div>
                                <div class="fl ipt_wid">
                                    <div class="coupon_content" action="2" style="height: 180px;">
                                        <input type="hidden" name="reward_coupon_id" value="${bargain->reward_coupon_id!}">
                                        <div class="tem_right">
                                            <p style="color: #999; padding-bottom: 10px;">买家砍价失败后给予一定奖励，可提升买家复购</p>
                                            <div class="coupon_div clearfix" action="2" coupon_json="" <#if ($bargain->coupon_list) style="display: block" </#if>>
                                                <#list ($bargain->reward_coupon_list ?? [] as $k => $cou)
                                                    <div class="coupon_list">
                                                        <#if (!($bargain->id && $bargain->start_time <= date("Y-m-d H:i:s")))
                                                            <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del" action="2">
                                                        </#if>
                                                        <input type="hidden" coupon_id="${cou->id!}" act_code="${cou->act_code!}" denomination="${cou->denomination!}" class="coupon_info">
                                                        <div class="coupon_list_top">
                                                            <#if ($cou->act_code == 'voucher')
                                                                ¥<span>${cou->denomination!}</span>
                                                            <#elseif>($cou->act_code == 'discount')
                                                                打<span>${cou->denomination!}</span>折
                                                            </#if>
                                                        </div>
                                                        <div class="coupon_list_center">
                                                            <#if ($cou->use_consume_restrict == 0)
                                                                <div class="coupon_center_limit">不限制</div>
                                                            <#else>
                                                                <div class="coupon_center_limit">满${cou->least_consume!}使用</div>
                                                            </#if>
                                                            <div class="coupon_center_number">剩余<span>${cou->surplus!}</span>张</div>
                                                        </div>
                                                        <div class="coupon_list_bottom">
                                                            领取
                                                        </div>
                                                    </div>
                                                </#list>
                                                <#if (!($bargain->id && $bargain->start_time<=date("Y-m-d H:i:s")))
                                                    <div class="card_add card_add_click" action="2">
                                                        <img src="http://mpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png" alt="">
                                                        <p>添加优惠券</p>
                                                    </div>
                                                </#if>
                                            </div>
                                            <p style="color:#999999;">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#if>
                        <div class="clearfix share_module" style="margin-top: 10px;">
                        <div class="fl">
                            <span class="tb-full-left"><strong>*</strong>店铺分享：</span>
                        </div>

                        <div class="fl">
                            <input type="radio" name="share_action" value="1" <#if  (!$module_share || $module_share['share_action'] == 1) checked </#if>/> 默认样式
                            <a href="javascript:;" class="show_eg">查看示例
                                <div class="hover_show">
                                    <img src="http://${image_domain!}/image/admin/share/bargain_share.jpg"/>
                                </div>
                            </a>
                            <a href="javascript:;" class="show_eg">下载海报
                                <div class="hover_show">
                                    <img src="http://${image_domain!}/image/admin/share/bagain_pictorial.jpg"/>
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
                </div>
                <div class="template_footer fix_footer">
                    <div class="save">保存</div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="coupon_list_clone hide">
    <div class="coupon_list">
        <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del">
        <input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info">
        <div class="coupon_list_top">
            ¥<span>××</span>
        </div>
        <div class="coupon_list_center">
            <div class="coupon_center_limit">满××使用</div>
            <div class="coupon_center_number">剩余<span>××</span>张</div>
        </div>
        <div class="coupon_list_bottom">
            领取
        </div>
    </div>
</div>
<#include ('admin.preview_common')
<script type="text/javascript">
    var hasSaved = true;
</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script type="text/javascript">

    $('.template_footer').width($('.main-container').width());
    $('.add_goods').click(function () {
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center; padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px', '430px']
                , content: '/admin/frame/goods/select/type?goods_type=3&type_goods=0&is_check_single=1&is_tips=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {

                }
                , yes: function (index, layero) {
                    //保存按钮的回调
                    $('.bargain_tb').show();
                    $('.add_goods').find('div').text('重新选择');
                    var iframe = layer.getChildFrame('body', index);
                    var goods_choose = iframe.contents().find('tr[data-back="false"]');
                    $('.goods_img').find('img').attr('src',goods_choose.find('.goods_img').find('img').attr('src'));
                    $('.goods_name').text(goods_choose.find('.goods_name').text());
                    $('.goods_num').text(goods_choose.find('td').eq(3).text());
                    $('.bargain_num').val(goods_choose.find('td').eq(3).text());
                    $('.goods_price').text(goods_choose.find('td').eq(2).text());
                    // var goods_id = goods_choose.attr('goods_id');
                    // var goods_name = goods_choose.find('.goods_name').text();
                    $('input[name="goods_id"]').val(goods_choose.attr('goods_id'));
                    $('input[name="goods_name"]').val(goods_choose.find('.goods_name').text());
//                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    $('.bargain_num').blur(function () {
        if($(this).val() > parseInt($('.goods_num').text())){
            util.mobile_alert('砍价库存不得大于商品本身库存，请重新填写');
            $(this).val('');
        }
    });
    $('input[name="expectation_price"]').change(function () {
        var shop_price = $(".goods_price").text();
        if($(this).val() > parseInt(shop_price)){
            util.mobile_alert('砍价底价不得大于商品原价');
            $(this).val('');
        }
    });
    $('.save').click(function(){
        if($('input[name="bargain_name"]').val() == ''){
            util.mobile_alert('活动名称不能为空');
            return false;
        }
        var start_time = $('input[name="start_time"]').val();
        if(start_time == ''){
            util.mobile_alert('开始时间不能为空');
            return false;
        }
        var end_time = $('input[name="end_time"]').val();
        if(end_time == ''){
            util.mobile_alert('结束时间不能为空');
            return false;
        }
        if(start_time > end_time){
            util.mobile_alert('开始时间不能大于结束时间');
            return false;
        }
        if($('input[name="goods_id"]').val() == ''){
            util.mobile_alert('请选择商品');
            return false;
        }
        var expectation_number = $('input[name="expectation_number"]').val();
        if(expectation_number == '' || parseInt(expectation_number) < 3){
            util.mobile_alert('期望砍价人数最少3人');
            return false;
        }
        var min = $('input[name="bargain_min"]').val();
        var max = $('input[name="bargain_max"]').val();
        if (min != '') {
            if(parseInt(min) < 0 || parseInt(min) > 50){
                util.mobile_alert('首次砍价比例必须再0~50之间');
                return false;
            }
        }
        if (max != '') {
            if(parseInt(max) < 0 || parseInt(max) > 50){
                util.mobile_alert('首次砍价比例必须再0~50之间');
                return false;
            }
        }
        if ((min == '' && max != '') || (min != '' && max == '')) {
            util.mobile_alert('首次砍价比例必须同时填写');
            return false;
        }
        if (parseFloat(min) > parseFloat(max)) {
            util.mobile_alert('首次砍价比例左边不能大于右边');
            return false;
        }
        var stock = $('input[name="stock"]').val();
        if (isNaN(parseInt(stock))) {
            util.mobile_alert('砍价库存必填');
            return false;
        }
        if (parseInt(stock) < 1) {
            util.mobile_alert('砍价库存必须大于0');
            return false;
        }
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
        // var param = {};
        // var list = $('#form1').serializeArray();
        // $.each(list,function () {
        //     param[this.name] = this.value;
        // });
        // console.log(param);
        util.ajax_json('/admin/market/bargain/edit', function (d) {
            // console.log(d);
            if (d && d.error == 0) {
                util.mobile_alert('保存成功');
                location.href = '/admin/market/bargain/list';
                hasSaved = true;
            } else if (d && d.error > 0) {
                util.mobile_alert(d.message);
            }
        }, $('#form1').serialize());
    });
    util.inputChange();
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            event.returnValue = "确认要离开吗？";
        }
    };
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $('.coupon_div').on('click','.coupon_del',function () {
        var name_field = parseInt($(this).attr('action')) == 1 ? 'mrking_voucher_id' : 'reward_coupon_id';
        var coupon_ids = $("input[name="+name_field+"]").val().split(',');
        var index = $.inArray($(this).next().attr('coupon_id'), coupon_ids);
        if (index <= -1) return false;
        coupon_ids.splice(index,1);
        $('input[name='+name_field+']').val(coupon_ids.join(','));
        $(this).parent().remove();
        if(coupon_ids.length < 5){
            $('.card_add[action="'+$(this).attr('action')+'"]').show();
        }
        hasSaved = false;
    });
    $(".coupon_div").on('click','.card_add_click',function(){
        var name_field = parseInt($(this).attr('action')) == 1 ? 'mrking_voucher_id' : 'reward_coupon_id';
        var action = $(this).attr('action');
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择优惠券', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['550px','500px']
                , content: '/admin/frame/coupon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var iframe = layer.getChildFrame('body', index);
                    if($('.coupon_div[action="'+action+'"]').find('.coupon_list').size() > 0){
                        $('.coupon_div[action="'+action+'"]').find('.coupon_list').each(function () {
                            var _this = $(this);
                            iframe.find('.coupon_list').each(function () {
                                if($(this).find('.coupon_info').attr('coupon_id') == _this.find('.coupon_info').attr('coupon_id')){
                                    $(this).addClass('card_list_active');
                                }
                            });
                        });
                    }
                    iframe.find('.coupon_list').click(function () {
                        if($(this).hasClass('card_list_active')){
                            $(this).removeClass('card_list_active');
                        }else{
                            $(this).addClass('card_list_active');
                        }
                    });
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var list_active = iframe.find('.card_list_active');
                    if($(list_active).size() == 0){
                        util.mobile_alert('请选择优惠券');
                        return;
                    }
                    if($(list_active).size() > 5){
                        util.mobile_alert('最多只能选择5张优惠券哦~');
                        return;
                    }
                    var coupon_ids = [];
                    var card_add = $('.card_add[action="'+action+'"]').clone();
                    $('.coupon_div[action="'+action+'"]').html('');
                    $(list_active).each(function (i) {
                        var coupon_clone = $('.coupon_list_clone').find('.coupon_list').clone();
                        coupon_clone.find('.coupon_del').attr('action', action);
                        coupon_clone.find('.coupon_info').attr('act_code',$(this).find('.coupon_info').attr('act_code'));
                        coupon_clone.find('.coupon_info').attr('denomination',$(this).find('.coupon_info').attr('denomination'));
                        coupon_clone.find('.coupon_info').attr('coupon_id', $(this).find('.coupon_info').attr('coupon_id'));
                        if($(this).find('.coupon_info').attr('act_code') == "discount"){
                            coupon_clone.find('.coupon_list_top').html('<span>' + $(this).find('.coupon_info').attr('denomination') + '</span>折');
                        }
                        if($(this).find('.coupon_info').attr('act_code') == "voucher"){
                            coupon_clone.find('.coupon_list_top').html('￥<span>' + $(this).find('.coupon_info').attr('denomination') + '</span>');
                        }
                        coupon_clone.find('.coupon_center_limit').text($(this).find('.coupon_center_limit').text().replace(/\s+/g,""));
                        coupon_clone.find('.coupon_center_number').text($(this).find('.coupon_center_number').text().replace(/\s+/g,""));
                        $('.coupon_div[action="'+action+'"]').show();
                        $('.coupon_div[action="'+action+'"]').prepend(coupon_clone).append(card_add);
                        coupon_ids.push($(this).find('.coupon_info').attr('coupon_id'));
                        $('input[name="'+name_field+'"]').val(coupon_ids);
                    });
                    if($('.coupon_div[action="'+action+'"]').find('.coupon_list').length == 5){
                        $(".card_add_click[action='"+action+"']").hide();
                    }else{
                        $(".card_add_click[action='"+action+"']").show();
                    }
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });


//    新增
//    展开收起
    $(".show_basic").click(function () {
        if($(this).parent().attr('if_show') == 1){
            $(".som_no_use").css("display",'none');
            $(this).find('text').text('展开更多配置');
            $(this).parent().attr('if_show','0');
            $(this).find('img').attr('src',"http://"+image_domain+"/image/admin/info_down.png")
        }else{
            $(".som_no_use").css("display",'block');
            $(this).find('text').text('收起更多配置');
            $(this).parent().attr('if_show','1');
            $(this).find('img').attr('src',"http://"+image_domain+"/image/admin/info_up.png")
        }
    })
    
//    切换
    $(".choose_money_type input[type='radio']").change(function () {
        choose_money_type();
    });
    choose_money_type();
    function choose_money_type() {
        if($(".choose_money_type input[type='radio']:checked").val() == 0){
            $('.if_same_money').removeClass('bxianshi');
            $('.same_mon').removeClass('bxianshi');
            $('.if_any_money').addClass("bxianshi");
            $('.any_mon').addClass("bxianshi");
        }else{
            $('.if_same_money').addClass("bxianshi");
            $('.same_mon').addClass("bxianshi");
            $('.if_any_money').removeClass('bxianshi');
            $('.any_mon').removeClass('bxianshi');
        }
    }
</script>
<script language="JavaScript" src="/js/admin/common_share.js?v=1.1.1"></script>

<#include "/admin/footer.ftl">
<script>
    //版本控制
    getPowerInfo('main_config','bargain','sub_4','砍价',0);
    <#if  ($bargain->id && count(explode(',', $bargain->mrking_voucher_id)) >= 5)
        $('.card_add[action="1"]').hide();
    </#if>
    <#if  ($bargain->id && count(explode(',', $bargain->reward_coupon_id)) >= 5)
        $('.card_add[action="2"]').hide();
    </#if>
</script>