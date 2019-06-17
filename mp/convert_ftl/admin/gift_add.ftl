<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/gift_give.css?v=1.0.4" type="text/css" />
<style type="text/css">
    a:link,a:focus,a:hover,a:active{
        text-decoration: none;
    }
    .change-input{
        background: #fff;
        cursor: default;
        width: 30px;
        top: 0px;
    }
    .hover_show:before{
        top: 255px !important;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span style="color: #666;">${title!}</span>
    </div>
    <div class="main-container fix_every_footer">
        <div class="content">
            <div class="head">
                <ul class="clearfix">
                    <li>
                        <a href="/admin/market/gift/list?nav=0">全部赠品</a>
                    </li>
                    <li><a href="/admin/market/gift/list?nav=1">进行中</a></li>
                    <li><a href="/admin/market/gift/list?nav=2">未开始</a></li>
                    <li><a href="/admin/market/gift/list?nav=3">已过期</a></li>
                    <li><a href="/admin/market/gift/list?nav=4">已停用</a></li>
                    <li class="actives"><a href="##"><#if  (!empty($giftInfo))编辑<#else>添加</#if>赠品活动</a></li>
                </ul>
            </div>
            <div class="gift_con">
                <div class="gift_title">
                    <div class="set_rule img_active1">1.设置活动规则</div>
                    <div class="set_change_goods img_active2">2.设置赠品</div>
                </div>
                <form action="/admin/market/gift/add" id="form1" method="post">
                    {{csrf_field()!}
                    <div class="gift_content_1">
                            <input type="hidden" name="id" value="${giftInfo->id!}"/>
                            <input type="hidden" name="tag_id" value="${giftInfo->rule['tag_id']!}">
                            <input type="hidden" name="card_id" value="${giftInfo->rule['card_id']!}">
                            <div class="con_set_rule clearfix">
                                <div class="basic_pz">
                                    基础配置
                                </div>
                                <ul class="basic_ul">
                                    <li class="clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            活动名称：
                                        </div>
                                        <div class="fr_l">
                                            <input type="text" name="name" value="${giftInfo->name!}"/>
                                            <span>只作为商家记录使用，用户不会看到这个名称</span>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            活动优先级：
                                        </div>
                                        <div class="fr_l">
                                            <input type="text" name="level" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${giftInfo->level!}"/>
                                            <span>用于区分不同赠品活动的优先级，请填写正整数，数值越大优先级越高</span>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            活动时间：
                                        </div>
                                        <div class="fr_l">
                                            <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${giftInfo->start_time!}"/>
                                            至&nbsp;&nbsp;
                                            <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${giftInfo->end_time!}"/>
                                        </div>
                                    </li>
                                    <li class="clearfix">

                                    </li>
                                </ul>
                            </div>
                            <div class="con_set_rule clearfix">
                                <div class="basic_pz">
                                    赠品策略
                                </div>
                                <ul class="basic_ul">
                                    <li class="clearfix">
                                        <input class="group_goods_id" value="${giftInfo->goods_id!}" type="hidden" name="goods_id"/>
                                        <div class="fl">
                                            <em>*</em>
                                            活动商品：
                                        </div>
                                        <div class="fr_l">
                                            <label for="all">
                                                <input type="radio" name="recommend_type" id="all" value="1" <#if  (!$giftInfo || !$giftInfo->goods_id)checked </#if> />全部商品
                                            </label>
                                            <label for="some">
                                                <input type="radio" name="recommend_type" id="some" value="2" <#if  ($giftInfo && $giftInfo->goods_id)checked </#if>/>指定商品
                                            </label>
                                            <div class="choose_goods" <#if ($giftInfo && $giftInfo->goods_id)style="display: inline-block;"</#if> >
                                                <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                                <#if  (!$giftInfo || $giftInfo->status == 0 || strtotime($giftInfo->end_time) < time())
                                                    添加商品
                                                    <#else>
                                                    查看商品
                                                </#if>
                                            </div>
                                            <span class="choose_num" <#if ($giftInfo && $giftInfo->goods_id)style="display: inline-block;"</#if> >
                                                已选：<span>${giftInfo->goods_id ? count(explode(',', $giftInfo->goods_id)) : 0!}</span>件商品
                                            </span>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="fl">
                                            <em>*</em>
                                            赠品条件：
                                        </div>
                                        <input name="condition_str" type="hidden" value="{{ implode(',', $conditionArr)!}" />
                                        <div class="fr_l">
                                            <select name="choose_condition" id="choose_condition">
                                                <option value="0">请选择赠品条件</option>
                                                <#list  ($ruleDoc as $key => $rule)
                                                    <option value="${key!}">${rule!}</option>
                                                </#list>
                                            </select>
                                            <span>以下条件满足其一即可获得赠品,最多可选择3类。</span>
                                        </div>
                                        <div class="condition_content">
                                            <ul class="basic_ul" <#if  ($giftInfo) style="display: block;" </#if>>
                                                <#if  (in_array(1, $conditionArr))
                                                <li class="clearfix" value="1">
                                                    <div class="fl">
                                                        满金额赠送：
                                                    </div>
                                                    <div class="fr_l">
                                                        满<input type="number" name="full_price" value="${giftInfo->rule['full_price']!}">元,送赠品
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅购买活动商品达到指定金额的用户可获得赠品。</span>
                                                    </div>
                                                </li>
                                                </#if>
                                                <#if  (in_array(2, $conditionArr))
                                                <li class="clearfix"  value="2">
                                                    <div class="fl">
                                                        满件数赠送：
                                                    </div>
                                                    <div class="fr_l">
                                                        满<input type="number" name="full_number" value="${giftInfo->rule['full_number']!}">件,送赠品
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅购买活动商品达到指定件数的用户可获得赠品。</span>
                                                    </div>
                                                </li>
                                                </#if>
                                                <#if  (in_array(3, $conditionArr))
                                                <li class="clearfix"  value="3">
                                                    <div class="fl">
                                                        会员卡：
                                                    </div>
                                                    <div class="fr_l">
                                                        <select class="card_id">
                                                            <option value="0" selected="selected">请选择会员卡</option>
                                                            <#list  ($cardList as $card)
                                                                <#if  (!in_array($card->id, explode(',', $giftInfo->rule['card_id'])))
                                                                <option value="${card->id!}" >${card->card_name!}</option>
                                                                </#if>
                                                            </#list>
                                                        </select>
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅持有指定会员卡的用户可获得赠品。</span>
                                                        <div class="card-info" <#if  ($giftInfo) style="display: block;" </#if>>
                                                            <div class="card-info-row">
                                                                <span class="card-choose">已选：</span>
                                                                <#list  ($cardList as $card)
                                                                    <#if  (in_array($card->id, explode(',', $giftInfo->rule['card_id'])))
                                                                    <span class="card_span">
                                                                        <span value="3">${card->card_name!}</span>
                                                                        <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />
                                                                    </span>
                                                                    </#if>
                                                                </#list>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </li>
                                                </#if>
                                                <#if  (in_array(4, $conditionArr))
                                                <li class="clearfix"  value="4">
                                                    <div class="fl">
                                                        会员标签：
                                                    </div>
                                                    <div class="fr_l">
                                                        <select class="label_id">
                                                            <option value="0" selected="selected">请选择会员标签</option>
                                                            <#list  ($tagList as $tag)
                                                                <option value="${tag->tag_id!}" >${tag->tag_name!}</option>
                                                            </#list>
                                                        </select>
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅指定标签下的用户可获得赠品。</span>
                                                        <div class="label-info" <#if  ($giftInfo) style="display: block;" </#if>>
                                                            <div class="label-info-row">
                                                                <span class="label-choose">已选：</span>
                                                                <#list  ($tagList as $tag)
                                                                    <#if  (in_array($tag->tag_id, explode(',', $giftInfo->rule['tag_id'])))
                                                                    <span class="label_span">
                                                                        <span value="4">${tag->tag_name!}</span>
                                                                        <img src="/image/admin/icon_delete.png" alt="" class="label-delete"  />
                                                                    </span>
                                                                    </#if>
                                                                </#list>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </li>
                                                </#if>
                                                <#if  (in_array(5, $conditionArr))
                                                <li class="clearfix"  value="5">
                                                    <div class="fl">
                                                        付款时间：
                                                    </div>
                                                    <div class="fr_l">
                                                        <input type="text" name="pay_start_time" id="fk_startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'fk_endDate\')}'})" autocomplete="off" value="${giftInfo->rule['pay_start_time']!}"/>
                                                        至&nbsp;&nbsp;
                                                        <input type="text" name="pay_end_time" id="fk_endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'fk_startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${giftInfo->rule['pay_end_time']!}"/>
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅在指定时间内付款购买活动商品的用户可获得赠品。</span>
                                                    </div>
                                                </li>
                                                </#if>
                                                <#if  (in_array(6, $conditionArr))
                                                <li class="clearfix"  value="6">
                                                    <div class="fl">
                                                        付款排名：
                                                    </div>
                                                    <div class="fr_l">
                                                        前<input type="text" name="pay_top" value="${giftInfo->rule['pay_top']!}">名付款用户，送赠品
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅前X名付款购买活动商品的用户可获得赠品。</span>
                                                    </div>
                                                </li>
                                                </#if>
                                                <#if  (in_array(7, $conditionArr))
                                                <li class="clearfix"  value="7">
                                                    <div class="fl">
                                                        已购买次数：
                                                    </div>
                                                    <div class="fr_l">
                                                        <input type="number" name="min_pay_num" value="${giftInfo->rule['min_pay_num']!}">
                                                        至&nbsp;&nbsp;
                                                        <input type="number" name="max_pay_num" value="${giftInfo->rule['max_pay_num']!}">次
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅在店铺内购买活动商品达到指定次数的用户可获得赠品。</span>
                                                    </div>
                                                </li>
                                                </#if>
                                                <#if  (in_array(8, $conditionArr))
                                                <li class="clearfix"  value="8">
                                                    <div class="fl">
                                                        用户类型：
                                                    </div>
                                                    <div class="fr_l">
                                                        <select name="user_action" id="">
                                                            <option value="0">请选择用户类型</option>
                                                            <option value="1" <#if  ($giftInfo->rule['user_action'] == 1) selected </#if>>新用户</option>
                                                            <option value="2" <#if  ($giftInfo->rule['user_action'] == 2) selected </#if>>老用户</option>
                                                        </select>
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
                                                        <span>仅指定的用户可获得赠品。</span>
                                                    </div>
                                                </li>
                                                </#if>
                                            </ul>
                                        </div>
                                    </li>
                                    <li class="clearfix">
                                        <div class="fl" style="width: 115px">
                                            <em>*</em>
                                            赠品规则说明 ：
                                        </div>
                                        <div class="fr_l">
                                            <textarea name="explain" id="" cols="30" rows="10" placeholder="此提示将在小程序前端展示，请根据配置的赠品策略谨慎编写赠品规则说明，最多可填写200字。
    例：前100名付款用户可获得赠品，送完即止。" maxlength="200">${giftInfo->explain!}</textarea>
                                            <a href="javascript:;" class="show_eg" style="display: block;">查看示例
                                                <div class="hover_show" style="top: -250px">
                                                    <img src="http://${image_domain!}/image/admin/new_preview_image/gift.jpg" alt="">
                                                </div>
                                            </a>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                    </div>
                    <div class="gift_content_2">
                        <div class="add_change_goods" val="2">添加赠品商品</div>
                        <span style="color: #999">注：请合理设置赠品库存，赠品全部发完活动将提前停止。最多可添加20件赠品</span>
                        <table class="tb_goods">
                            <thead>
                            <tr class="change_tr_first">
                                <td width="25%">商品名称</td>
                                <td>规格</td>
                                <td>商品原价</td>
                                <td>商品库存</td>
                                <td>赠品库存(当前库存/初始库存)</td>
                                <#if  (!$giftInfo || ($giftInfo->status == 1 && strtotime($giftInfo->end_time) < time()))
                                <td>操作</td>
                                </#if>
                            </tr>
                            </thead>
                            <tbody id="main_goods_list">
                            <#list  ($productList as $product)
                                <tr>
                                    <td>
                                        <div class="goods_img">
                                            <img src="${product->prd_img ?: $product->goods_img!}" />
                                        </div>
                                        <div class="goods_info">
                                            <div class="goods_name">${product->goods_name!}</div>
                                        </div>
                                    </td>
                                    <td>${product->prd_desc!}</td>
                                    <td>￥${product->prd_price!}</td>
                                    <td class="prd_number">${product->prd_number!}</td>
                                    <td>
                                        <input type="hidden" name="product_id[]" value="${product->product_id!}"/>
                                        <input type="hidden" name="product_number[]" value="${product->product_number + $product->buy_num!}"/>
                                        <span>${product->product_number!}</span> &nbsp;&nbsp;/
                                        <input type="text" class="change-input" onkeyup="value=value.replace(/[^\d.]/g,'')"  value="${product->product_number + $product->buy_num!}" disabled/>
                                        <img src="/image/admin/good_edit.png" class="goods-number-img">
                                    </td>
                                    <td><a href="javascript:void(0)"  class="change_goods_del">删除</a></td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
            <div class="footer fix_footer">
                <a href="##" class="set1 next1" style="margin-right: 10px;">下一步</a>
                <a href="##" class="set2 prev1" style="margin-right: 10px;display: none;">上一步</a>
                <a href="##" class="set3 save" style="display: none">保存</a>
            </div>
        </div>
    </div>
</div>
<div class="clone">
    <li class="clearfix" value="1">
        <div class="fl">
            满金额赠送：
        </div>
        <div class="fr_l">
            满<input type="number" name="full_price">元,送赠品
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅购买活动商品达到指定金额的用户可获得赠品。</span>
        </div>
    </li>
    <li class="clearfix"  value="2">
        <div class="fl">
            满件数赠送：
        </div>
        <div class="fr_l">
            满<input type="number" name="full_number">件,送赠品
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅购买活动商品达到指定件数的用户可获得赠品。</span>
        </div>
    </li>
    <li class="clearfix"  value="3">
        <div class="fl">
            会员卡：
        </div>
        <div class="fr_l">
            <select class="card_id">
                <option value="0" selected="selected">请选择会员卡</option>
                <#list  ($cardList as $card)
                    <option value="${card->id!}" >${card->card_name!}</option>
                </#list>
            </select>
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅持有指定会员卡的用户可获得赠品。</span>
            <div class="card-info">
                <div class="card-info-row">
                    <span class="card-choose">已选：</span>
                </div>
            </div>
        </div>
    </li>
    <li class="clearfix"  value="4">
        <div class="fl">
            会员标签：
        </div>
        <div class="fr_l">
            <select class="label_id">
                <option value="0" selected="selected">请选择会员标签</option>
                <#list  ($tagList as $tag)
                    <option value="${tag->tag_id!}" >${tag->tag_name!}</option>
                </#list>
            </select>
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅指定标签下的用户可获得赠品。</span>
            <div class="label-info">
                <div class="label-info-row">
                    <span class="label-choose">已选：</span>
                </div>
            </div>
        </div>
    </li>
    <li class="clearfix"  value="5">
        <div class="fl">
            付款时间：
        </div>
        <div class="fr_l">
            <input type="text" name="pay_start_time" id="fk_startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'fk_endDate\')}'})" autocomplete="off" value="${giftInfo->fk_start_time!}"/>
            至&nbsp;&nbsp;
            <input type="text" name="pay_end_time" id="fk_endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'fk_startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${giftInfo->fk_end_time!}"/>
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅在指定时间内付款购买活动商品的用户可获得赠品。</span>
        </div>
    </li>
    <li class="clearfix"  value="6">
        <div class="fl">
            付款排名：
        </div>
        <div class="fr_l">
            前<input type="text" name="pay_top">名付款用户，送赠品
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅前X名付款购买活动商品的用户可获得赠品。</span>
        </div>
    </li>
    <li class="clearfix"  value="7">
        <div class="fl">
            已购买次数：
        </div>
        <div class="fr_l">
            <input type="number" name="min_pay_num">
            至&nbsp;&nbsp;
            <input type="number" name="max_pay_num">次
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅在店铺内购买活动商品达到指定次数的用户可获得赠品。</span>
        </div>
    </li>
    <li class="clearfix"  value="8">
        <div class="fl">
            用户类型：
        </div>
        <div class="fr_l">
            <select name="user_action" id="">
                <option value="0">请选择用户类型</option>
                <option value="1">新用户</option>
                <option value="2">老用户</option>
            </select>
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class="del">
            <span>仅指定的用户可获得赠品。</span>
        </div>
    </li>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script type="text/javascript">
    var hasSaved = false;
    var is_show_save = false;
    var full_price_text = full_number_text = '';
    var goods_iframe_only_show = 0;
</script>
<script src="/js/admin/gift_add.js?v=1.1.10" type="text/javascript"></script>
<script type="text/javascript">
    $('.footer').width($('.content').width());
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }

    $(document).on('blur','.change-input',function (e) {
            $('.ipt-change').next().show();
            if(parseInt($(this).val()) > parseInt($(this).parent().parent().find('.prd_number').text())){
                util.mobile_alert('赠品库存不能大于原商品库存');
                $(this).focus();
                return false;
            }
            var buy_num = parseInt($(this).parent().find('[name="product_number[]"]').val()) - parseInt($(this).prev().text());
            if (parseInt($(this).val()) < buy_num) {
                util.mobile_alert('初始库存不能小于已送赠品数'+buy_num);
                $(this).focus();
                return false;
            }
            $(this).parent().find('input[name="product_number[]"]').val($(this).val());
            $(this).prev().text(parseInt($(this).val()) - buy_num);
            $('.change-input').removeClass('ipt-change');
            $(".change-input").attr("disabled",true);
    });
    $(document).on('click','.goods-number-img',function () {
        $(this).prev().addClass('ipt-change');
        $(this).prev().attr("disabled", false);
        $(this).prev().focus();
        $(this).hide();
    });
    <#if  ($giftInfo)
        <#if  ($giftInfo->status == 0 || strtotime($giftInfo->end_time) < time())
            $('input, select').prop('disabled', 'disabled');
            $('.del, .label_id, .card_id, .goods-number-img, .add_change_goods, .card-delete, .label-delete').remove();
            $('#choose_condition, .change_goods_del').parent().remove();
            goods_iframe_only_show = 1;
        </#if>
        <#if  ($giftInfo->status == 1 && strtotime($giftInfo->start_time) < time() &&
            strtotime($giftInfo->end_time) > time())
            $('input, select').prop('disabled', 'disabled');
            $('input[name="name"], input[type="hidden"]').removeAttrs('disabled');
            $('.del, .label_id, .card_id, .add_change_goods, .card-delete, .label-delete').remove();
            $('#choose_condition, .change_goods_del').parent().remove();
            goods_iframe_only_show = 1;
        </#if>
            is_show_save = true;
        $('.save').css('display', 'inline-block');
    </#if>
</script>
<script type="text/javascript">
    getPowerInfo('main_config','gift','sub_4','赠品策略',0);
</script>