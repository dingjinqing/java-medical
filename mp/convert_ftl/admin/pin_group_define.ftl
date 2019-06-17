<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/common_share.css?v=1.2.1" type="text/css" />
<style type="text/css">
    body{
        padding-bottom: 40px;
    }
    input[name='is_default']{
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        width: 12px;
        height: 12px;
        background: url(/image/admin/square_no.png) no-repeat;
        background-size: 100%;
        position: relative;
        top: 1px;
        margin-right: 5px;
    }
    input[name='is_default']:checked{
        width: 12px;
        height: 12px;
        background: url(/image/admin/square_yes.png) no-repeat;
        background-size: 100%;
    }
    .goods-namess {
        padding: 8px 2px;
        width: auto;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        background: #f4f4f4;
    }
    .goods-btn-modify {
        padding: 8px 2px;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #5A8BFF;
        cursor: pointer;
    }
    #product-info table tr td{
        border: 1px solid #eee;
    }
    .prompt {
        color: #999;
        margin-left: 20px;
    }
    input[readonly] {
        background: #EBEBE4;
    }
    .pin_group_footer button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .pin_group_footer button:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type=radio] {
        margin-top:0
    }
    .leader_off_tips{
        display: inline-block;
        color: #999;
        margin-left: 45px;
    }
    .tb-pin-group td > table > tbody > tr > th{
        min-width:165px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4" >营销管理</a> / </span>
        <span style="color: #666;">多人拼团</span>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if ($request['nav'] == 0) class="active" </#if>>
                    <a href="/admin/market/pingroup/list?nav=0">全部拼团活动</a>
                </li>
                <li <#if ($request['nav'] == 1) class="active" </#if>>
                    <a href="/admin/market/pingroup/list?nav=1">进行中</a>
                </li>
                <li <#if ($request['nav'] == 2) class="active" </#if>>
                    <a href="/admin/market/pingroup/list?nav=2">未开始</a>
                </li>
                <li <#if ($request['nav'] == 3) class="active" </#if>>
                    <a href="/admin/market/pingroup/list?nav=3">已过期</a>
                </li>
                <li <#if ($request['nav'] == 4) class="active" </#if>>
                    <a href="/admin/market/pingroup/list?nav=4">已停用</a>
                </li>
                <li <#if ($request['nav'] == 5) class="active" </#if>>
                    <a href="/admin/market/pingroup/list?nav=5"><#if ($_GET['id'])编辑<#else>添加</#if>拼团</a>
                </li>
            </ul>
        </div>
        <div class="return-pin-group-box" style="padding-top: 20px;">
            <form name="formData" id="form1" method="post">
                {{ csrf_field()!}
                <input type="hidden" name="id" value="${_GET['id']!}"/>
                <table class="tb-pin-group">
                    <tbody>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>拼团类型：</span>
                        </td>
                        <td>
                            <input type="radio" name="activity_type" value="1" <#if (!empty($basicData) && $basicData->activity_type==1 || empty($basicData)) checked </#if> <#if (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s")) readonly disabled </#if>>普通拼团
                            <input type="radio" name="activity_type" value="2" style="margin-left:30px;" <#if (!empty($basicData) && $basicData->activity_type==2) checked </#if> <#if (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s")) readonly disabled </#if>>老带新拼团
                            <span class="prompt">所有用户都可以开团，但只有新用户才能参团</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>活动名称：</span>
                        </td>
                        <td>
                            <input type="text" class="name" placeholder="请输入活动名称" name="name" value="${basicData->name!}">
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>活动商品：</span>
                        </td>
                        <td>
                            <#if (empty($basicData->goods_id))
                                <input type="button" value="+ 选择商品" id="sel-goods-btn" class="choose_goods" name="choose_goods">
                            </#if>
                            <input type="hidden" name="goods_id" value="${basicData->goods_id!}">
                            <span class="goods-namess" <#if  (empty($goodsInfo->goods_name)) style="display: none;" </#if>>${goodsInfo->goods_name!}</span>
                            <#if (empty($basicData->goods_id) || $basicData->start_time>date("Y-m-d H:i:s"))
                                <span class="goods-btn-modify" <#if ($basicData->start_time<=date("Y-m-d H:i:s")) style="display: none;" </#if>>修改</span>
                            </#if>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>有效期：</span>
                        </td>
                        <td>
                                    <span>
                                        <#if (!empty($basicData->start_time) && $basicData->start_time<=date("Y-m-d H:i:s"))
                                            <input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time"
                                                   id="startDate" readonly> 至
                                            <input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time"
                                                   id="endDate" readonly>
                                        <#else>
                                            <input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time"
                                                   id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                                            <input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time"
                                                   id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                                        </#if>
                                    </span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>成团人数：</span>
                        </td>
                        <td>
                            <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')" class="limit_amount" placeholder="成团人数" name="limit_amount" value="${basicData->limit_amount or 2!}"
                                   <#if (!empty($basicData->limit_amount) && $basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>; min="2"
                            />
                            <span class="prompt">不可小于2人</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left">下单商品数量：</span>
                        </td>
                        <td>
                            单次下单购买拼团商品数量最小
                            <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')"  name="limit_buy_num"
                                   value="${basicData->limit_buy_num!}"
                                   <#if (!empty($basicData->limit_buy_num) && $basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>;/> 件
                            <span class="prompt">请填写正整数，不填或为0表示不限制数量</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">  </td>
                        <td>
                            单次下单购买拼团商品数量最大
                            <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')" name="limit_max_num"
                                   value=""
                                   <#if (!empty($basicData->limit_max_num) && $basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>; min="2"
                            /> 件
                            <span class="prompt">请填写正整数，不填或为0表示不限制数量</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>参团限制：</span>
                        </td>
                        <td>
                            每人最多参加
                            <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')" class="join_limit" placeholder="" name="join_limit" value="${basicData->join_limit or 0!}" min="0" <#if ($basicData && $basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>>
                            次新团
                            <span class="prompt">默认为0，0表示不限制数量。仅限制参与其他用户所开的团的数量</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>开团限制：</span>
                        </td>
                        <td>
                            每人最多开启
                            <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')" class="open_limit" placeholder="" name="open_limit" value="${basicData->open_limit or 0!}" min="0" <#if ($basicData && $basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>>
                            次新团
                            <span class="prompt">默认为0，0表示不限制数量。仅限制同一用户的开团数量</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" style="margin-bottom:12px;"><strong>&nbsp;</strong>默认成团：</span>
                        </td>
                        <td style="display:flex;">
                            <span style="margin-right: 20px; float: left; margin-top: 8px;">
                                <input type="checkbox" name="is_default" <#if  ($basicData->is_default == 1 ) checked </#if> <#if  (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>/>
                                开启默认成团
                            </span>
                            <span style="color: #999;flex:1;margin-top:8px;">开启默认成团后，24小时内人数未满的团，系统将会模拟“匿名买家”凑满人数，使该团成团。
                            你只需要对已付款参团的真实买家发货。建议合理开启，以提高成团率
                            </span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" style="    margin-bottom: 17px;"><strong>&nbsp;</strong>团长优惠：</span>
                        </td>
                        <td>
                            <span>
                                <input type="checkbox" name="is_grouper_cheap" style="vertical-align: text-top;margin-right: 5px;" <#if  ($basicData->is_grouper_cheap == 1 ) checked </#if> <#if (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s")) readonly disabled </#if>>
                                团长优惠
                            </span>
                            <p class="leader_off_tips">开启团长(开团人)优惠后，团长将享受更优惠价格，有助于提高开团率和成团率。
                            </p>
                            <p class="leader_off_tips" style="margin-left: 126px;"><span style="color: red;">注：</span>默认成团的团长也能享受团长优惠，为避免不必要的损失，请谨慎设置
                            </p>
                        </td>
                    </tr>
                    <#if (!($basicData->id && $basicData->start_time <= date("Y-m-d H:i:s") && !$basicData->reward_coupon_id))
                        <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" style="    margin-bottom: 17px;"><strong>&nbsp;</strong>鼓励奖：</span>
                        </td>
                        <td>
                            <div class="coupon_content">
                                <input type="hidden" name="reward_coupon_id" value="${basicData->reward_coupon_id!}">
                                <div class="tem_right">
                                    <p style="color: #999; padding-bottom: 10px;">买家拼团失败后给予一定奖励，可提升买家复购</p>
                                    <div class="coupon_div clearfix" coupon_json="" <#if ($basicData->coupon_list) style="display: block" </#if>>
                                        <#list ($basicData->coupon_list ?? [] as $k => $cou)
                                            <div class="coupon_list">
                                                <#if (!($basicData->id && $basicData->start_time <= date("Y-m-d H:i:s")))
                                                    <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del">
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
                                        <#if (!($basicData->id && $basicData->start_time <= date("Y-m-d H:i:s")))
                                            <#if  (!$basicData->id || count(explode(',', $basicData->reward_coupon_id)) < 5))
                                            <div class="card_add card_add_click">
                                                <img src="http://mpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png" alt="">
                                                <p>添加优惠券</p>
                                            </div>
                                            </#if>
                                        </#if>
                                    </div>
                                    <p style="color:#999999;">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </#if>
                    <tr >
                        <td>
                            <span class="tb-full-left"><strong>*</strong>优惠设置：</span>
                        </td>
                    </tr>
                    <tr style="text-align: center;" id="product-info">
                        <td colspan="2">
                            <table>
                                <tr>
                                    <th style="width: 250px">商品名称/规格</th>
                                    <th style="width: 250px">原价（元）</th>
                                    <th style="width: 200px">拼团价（元）</th>
                                    <th <#if (!(!empty($basicData) && $basicData->is_grouper_cheap==1)) style="width: 200px;display:none;"</#if>>团长优惠价（元）</th>
                                    <th style="width: 250px;">原库存</th>
                                    <th style="width: 250px">拼团库存</th>
                                </tr>
                                <tr style="display: none;">
                                    <input type="hidden" name="product_id[]" value="" />
                                    <input type="hidden" name="original_price[]" value="" />
                                    <td class="goods_name" style="width: 250px">

                                    </td>
                                    <td class="original_price" style="width: 250px">

                                    </td>
                                    <td class="pin_group_price" style="width: 200px">
                                        <input type="text"  name="pin_group_price[]" value="" style="width:155px"/>
                                    </td>
                                    <td class="leader_price" style="width: 200px;display:none;">
                                        <input type="text"  name="leader_price[]" value="" style="width:155px"/>
                                    </td>
                                    <td class="original_stock" style="width: 250px">

                                    </td>
                                    <td class="stock" style="width: 250px">
                                        <input type="text" name="stock[]" value="" style="width:200px"/>
                                    </td>
                                </tr>
                                <#list ($pinGoodsInfo as $key => $item)
                                    <tr class="set_line_value">
                                        <input type="hidden" name="product_id[]" value="${item->product_id!}" />
                                        <input type="hidden" name="original_price[]" value="${originPrice[$item->product_id]!}" />
                                        <td class="goods_name">
                                            <#if (empty($goodsSpec[$key]->prd_desc))
                                                ${goodsInfo->goods_name!}
                                            <#else>
                                                ${goodsSpec[$key]->prd_desc!}
                                            </#if>
                                        </td>
                                        <td class="original_price">
                                            ${originPrice[$item->product_id]!}
                                        </td>
                                        <td class="pin_group_price">
                                            <input type="text"  name="pin_group_price[]" value="${item->pin_group_price!}" <#if ($basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>/>
                                        </td>
                                        <td class="leader_price" <#if (!$basicData->is_grouper_cheap) style="display:none;" </#if>>
                                        <input type="text"  name="leader_price[]" value="${item->grouper_price!}" <#if ($basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>/>
                                        </td>
                                        <td class="original_price">
                                            ${originStock[$item->product_id]!}
                                        </td>
                                        <td class="stock">
                                            <input type="text" name="stock[]" value="<#if  ($originStock[$item->product_id] < $item->stock) ${originStock[$item->product_id]!} <#else> ${item->stock!} </#if>" <#if ($basicData->start_time<=date("Y-m-d H:i:s")) readonly </#if>/>
                                        </td>
                                    </tr>
                                </#list>
                            </table>
                        </td>
                    </tr>
                    <tr <#if (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s")) hidden </#if>>
                        <td>
                            <span class="tb-full-left"><strong>&nbsp;</strong>批量设置：</span>
                        </td>
                        <td>
                            <span>
                                <a href="javascript:void(0)" class="set_line" onclick="util.set_line_value('pin_group_price',1)" >拼团价</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="set_line" onclick="util.set_line_value('leader_price',1)" >团长优惠价</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="set_line" onclick="util.set_line_value('stock',1)" >拼团库存</a>
                            </span>
                        </td>
                    </tr>
                    <tr class="share_module" style="line-height: 33px;">
                        <td><span class="tb-full-left"></span>店铺分享：</td>
                        <td>
                            <div class="fl" style="position: relative; ">
                                <input type="radio" name="share_action" value="1" <#if  (!$module_share || $module_share['share_action'] == 1) checked </#if>/> 默认样式
                                <a href="javascript:;" class="show_eg">查看示例
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/share/pin_share.jpg"/>
                                    </div>
                                </a>
                                <a href="javascript:;" class="show_eg">下载海报
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/share/pin_pictorial.jpg"/>
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
                                    <div class="module_share_image" style="margin-left: 60px;">
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
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>

        </div>
        <div class="pin_group_footer fix_footer">
            <button type="submit" onclick="if(pinGroup.save() === false) return false;">保存</button>
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
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/common_share.js?v=1.1.1"></script>

<script>
    $(function(){
        $('input[name="is_grouper_cheap"]').click(function(){
            if($(this).prop("checked")){
                $('#product-info').find('th').eq(3).show();
                $('#product-info').find('tr').each(function(){
                    if($(this).index() > 0){
                        $(this).find('td.leader_price').show()
                    }
                })
            }else{
                $('#product-info').find('th').eq(3).hide();
                $('#product-info').find('tr').each(function(){
                    if($(this).index() > 0){
                        $(this).find('td.leader_price').hide()
                    }
                })
            }
        })
    })

    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    <#if  (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s"))
        $('input[name="is_default"]').click(function () {
            return false;
        });
    </#if>
    //选择商品
    $('.choose_goods, .goods-btn-modify').on('click',function(){

        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center; padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px', '430px']
                , content: '/admin/frame/goods/select?is_check_single=1&is_tips=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var goods = $('.tb-pin-group input[name="goods_id"]').val();
                    var body = layer.getChildFrame('body', index);
                    if(goods !='') {
                        if(isNaN(goods)) {
                            var goods_array = goods.split(',');
                            body.contents().find("tr").each(function(){
                                if($.inArray($(this).attr("goods_id"),goods_array)>-1){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                        else{
                            body.contents().find("tr").each(function(){
                                if($(this).attr("goods_id")==goods){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                    }
                }
                , yes: function (index, layero) {
                    //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    iframe.contents().find('tr[data-back="false"]').each(function(){
                        var goods_id = $(this).attr('goods_id');
                        var goods_name = $(this).find('.goods_name').text();
                        $(".tb-pin-group input[name='goods_id']").val(goods_id);
                        $(".tb-pin-group input[name='goods_id']").next().html(goods_name);
                        pinGroup.getSpecGoodsInfo(goods_id, goods_name);
                        $('#sel-goods-btn').remove();
                        $('.goods-namess, .goods-btn-modify').show();
                        return true;
                    });
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    var pinGroup = {
        //存储规格商品
        specProduct:{
            prd_id: [],
            prd_number: [],
            prd_price: []
        },
        //获得规格商品
        getSpecGoodsInfo: function (goods_id, goods_name) {
            util.ajax_json('/admin/market/pingroup/list', function (response) {
                if (response.error == 0) {
                    pinGroup.clearSpecProduct();
                    $('#product-info tr:gt(1)').remove();
                    var tr = '<tr class="set_line_value">' +$('#product-info tr:nth-of-type(2)').html()+ '</tr>';
                    $.each(response.content, function (index, item) {
                        $('#product-info table tbody').append(tr);
                        var last_tr = $('#product-info tr:nth-last-child(1)');
                        last_tr.find('.goods_name').text(item.prd_desc == '' ? goods_name : item.prd_desc);
                        last_tr.find('.original_price').text(item.prd_price);
                        last_tr.find('[name="product_id[]"]').val(item.prd_id);
                        last_tr.find('.original_stock').text(item.prd_number);
                        last_tr.find('[name="original_price[]"]').val(item.prd_price);
                        pinGroup.specProduct.prd_id.push(item.prd_id);
                        pinGroup.specProduct.prd_number.push(item.prd_number);
                        pinGroup.specProduct.prd_price.push(item.prd_price);
                        last_tr.show();
                    });
                    console.log(pinGroup.specProduct);
                } else {
                    util.mobile_alert(response.message);
                }
            }, {action: "get_goods_spec", goods_id: goods_id});
        },
        save: function (e) {
            //e.stopPropagation();
            if($('input[name="name"]').val() == '') {
                util.mobile_alert('请填写活动名称');
                return false;
            }
            if($('input[name="goods_id"]').val() == '') {
                util.mobile_alert('请选择商品');
                return false;
            }
            if($('input[name="start_time"]').val() == '' || $('input[name="end_time"]').val() == ''){
                util.mobile_alert('请填写有效期');
                return false;
            }
            if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
                util.mobile_alert('开始时间不能大于结束时间');
                return false;
            }
            if($('input[name="limit_amount"]').val() == '') {
                util.mobile_alert('请填写成团人数');
                return false;
            }
            if(parseInt($('input[name="limit_amount"]').val()) < 2) {
                util.mobile_alert('成团人数应大于2');
                return false;
            }
            if($('input[name="join_limit"]').val() == '' || parseInt($('input[name="join_limit"]').val()) < 0) {
                util.mobile_alert('请填写参团限制');
                return false;
            }
            if($('input[name="open_limit"]').val() == '' || parseInt($('input[name="open_limit"]').val()) < 0) {
                util.mobile_alert('请填写开团限制');
                return false;
            }
            <#if  (empty($basicData))
            var msg = '';
            $('#product-info input[name="pin_group_price[]"]').each(function (index, item) {
                if(index < 1) return true;
                if($(item).val() == '' || parseFloat($(item).val()) < 0) {
                    msg = '拼团价不能为空';
                    $(this).focus();
                    return false;
                }
                var prd_price = parseFloat(pinGroup.specProduct.prd_price[index - 1]);
                if(parseFloat($(item).val()) > prd_price) {
                    msg = '拼团价不能大于原价' +prd_price;
                    $(this).focus();
                    return false;
                }
            });
            if($('input[name="is_grouper_cheap"]').prop('checked')){
                $('#product-info input[name="leader_price[]"]').each(function (index, item) {
                    if(index < 1) return true;
                    if($(item).val() == '') {
                        msg = '团长优惠价不能为空';
                        $(this).focus();
                        return false;
                    }
                    if(parseFloat($(item).val()) < 0) {
                        msg = '团长优惠价不能小于0';
                        $(this).focus();
                        return false;
                    }
                    var pin_price = parseFloat($(item).parent().prev().find('input[name="pin_group_price[]"]').val());
                    if(parseFloat($(item).val()) > pin_price) {
                        msg = '团长优惠价不能大于拼团价' +pin_price;
                        $(this).focus();
                        return false;
                    }
                });
            }

            $(document).find('#product-info input[name="stock[]"]').each(function (index, item) {
                if(index < 1) return true;
                if($(item).val() == '' || parseInt($(item).val()) < 0) {
                    msg = '拼团库存不能为空';
                    $(this).focus();
                    return false;
                }

                var prd_number = parseInt(pinGroup.specProduct.prd_number[index - 1])
                if(parseInt($(item).val()) > prd_number) {
                    msg = '拼团库存不能大于原库存' +prd_number;
                    $(this).focus();
                    return false;
                }
            });
            if(msg != '') {
                util.mobile_alert(msg);
                return false;
            }
            </#if>
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
            //提交
            util.ajax_json('/admin/market/pingroup/add', function (response) {
                if (response.error == 0) {
                    hasSaved = true;
                    layer.ready(function () {
                        layer.msg('保存成功', {time: 2000},function () {
                            window.location = '/admin/market/pingroup/list?nav=0';
                        });
                    });
                } else {
                    util.mobile_alert(response.message);
                }
            }, $('#form1').serialize());
            return false;
        },
        clearSpecProduct: function () {
            pinGroup.specProduct.prd_id = [];
            pinGroup.specProduct.prd_number = [];
            pinGroup.specProduct.prd_price = [];
        }
    };
    var hasSaved = true;
    util.inputChange();
    util.checkboxChange();
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
    //编辑页初始化规格商品--提交校验数据合法性
    var goodsSpecArr = '${goodsSpec!}'.replace(/&quot;/g, '"');
    goodsSpecArr = JSON.parse(goodsSpecArr);
    $.each(goodsSpecArr, function (index, item) {
        pinGroup.specProduct.prd_id.push(item.prd_id);
        pinGroup.specProduct.prd_price.push(item.prd_price);
        pinGroup.specProduct.prd_number.push(item.prd_number);
    });
    console.log(pinGroup.specProduct);
</script>

<#include "/admin/footer.ftl">
<script>
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
</script>
<script type="text/javascript">
    getPowerInfo('main_config','pin_group','sub_4','拼团',0);

    $(".coupon_div").on('click','.card_add_click',function(){
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
                    if($('.coupon_div').find('.coupon_list').size() > 0){
                        $('.coupon_div').find('.coupon_list').each(function () {
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
                    var card_add = $('.card_add').clone();
                    $('.coupon_div').html('');
                    $(list_active).each(function (i) {
                        var coupon_clone = $('.coupon_list_clone').find('.coupon_list').clone();
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
                        $('.coupon_div').show();
                        $('.coupon_div').prepend(coupon_clone).append(card_add);
                        coupon_ids.push($(this).find('.coupon_info').attr('coupon_id'));
                        $('input[name="reward_coupon_id"]').val(coupon_ids);
                    });
                    if($('.coupon_div').find('.coupon_list').length==5){
                        $(".card_add_click").hide();
                    }else{
                        $(".card_add_click").show();
                    }
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    $('.coupon_div').on('click','.coupon_del',function () {
        var coupon_ids = $("input[name='reward_coupon_id']").val().split(',');
        var index = $.inArray($(this).next().attr('coupon_id'), coupon_ids);
        if (index <= -1) return false;
        coupon_ids.splice(index,1);
        $('input[name="reward_coupon_id"]').val(coupon_ids.join(','));
        $(this).parent().remove();
        if(coupon_ids.length < 5){
            $('.card_add').show();
        }
        hasSaved = false;
    });
    <#if  ($basicData->id && count(explode(',', $basicData->reward_coupon_id)) >= 5)
        $('.card_add').hide();
    </#if>
</script>
