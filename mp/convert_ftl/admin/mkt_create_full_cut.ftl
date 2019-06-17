<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/full_cut.css?v=1.0.2" type="text/css" />
<style>
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
    input[type='number']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .fix_footer{
        position: fixed;
        left: 160px;
    }
    .goods_modal{
        width: 345px;
        border: none !important;
    }
    .goods_modal thead:nth-child(1){
        border: 1px solid #ddd;
    }
    /*.goods_table, .goods_modal{
        border: none;
    }
    .goods_table thead{
        border: 1px solid #ddd;
    }*/
    .goods_area{
        max-width: 380px;
        padding-right: 5px;
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
        padding: 10px 10px 0 10px;
        margin-top: 10px;
        background: #f5f5f5;
        width:560px;
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
        margin-bottom:10px;
    }
    .card-delete {
        position: absolute;
        right: -10px;
        top: -7px;
        cursor: pointer;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">{{ trans("admin/market_manage.full_cut_title")!}</span>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/fullcut/list?nav=0">全部满折满减活动</a>
                </li>
                <li>
                    <a href="/admin/market/fullcut/list?nav=1">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/fullcut/list?nav=2">未开始</a>
                </li>
                <li>
                    <a href="/admin/market/fullcut/list?nav=3">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/fullcut/list?nav=4">已停用</a>
                </li>
                <li class="active">
                    <#if ($id)
                    <a href="/admin/market/fullcut/edit?top_index=4&id=${id!}">编辑满折满减活动</a>
                    <#else>
                    <a href="/admin/market/fullcut/create?top_index=4">添加满折满减活动</a>
                    </#if>
                </li>
            </ul>
        </div>

        {{--<script>--!}
            {{--// tab切换--!}
            {{--$("[data-toggle='tab']").click(function () {--!}
                {{--var url_arr = ['/admin/market/fullcut/create', '/admin/market/fullcut/list', '', '', ''];--!}
                {{--var idx = $(this).parent().index();--!}
                {{--if (url_arr[idx] != undefined) {--!}
                    {{--window.location.href = url_arr[idx];--!}
                {{--}--!}
            {{--});--!}
        {{--</script>--!}

        <div class="return-goods-box">
            <form name="formData" id="form1" method="post" method="/admin/market/fullcut/create">
                {{ csrf_field()!}
                <div class="goods-box-edit">
                    <table class="goods-edit-basic">
                        {{--<table class="tb-crt-full-cut">--!}
                            {{--<thead>--!}
                            {{--<tr>--!}
                                {{--<th colspan="2">--!}
                                    {{--<span>--!}
                                        {{--<#if ($id)--!}
                                        {{--编辑满折满减活动--!}
                                            {{--<#else>--!}
                                            {{--添加满折满减活动--!}
                                        {{--</#if>--!}
                                    {{--</span>--!}
                                {{--</th>--!}
                            {{--</tr>--!}
                            {{--</thead>--!}
                        <tbody>
                        <tr>
                            <td style="width:20%">
                                <span class="tb-full-left"><strong>*</strong>活动名称：</span>
                            </td>
                            <td>
                                <input type="text" class="act-name" placeholder="请输入活动名称" name="act_name" value="${act->act_name!}">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="tb-full-left"><strong>*</strong>活动类型：</span>
                            </td>
                            <td>
                                <div class="act-status">
                                    <input type="radio" name="type" id="full-cut-sel" value="2" <#if ($act->type == 2 || (!$act)) checked </#if>>
                                    <span class="sp-full">满减</span>
                                    <input type="radio" name="type" id="per-full-cut" value="1" <#if ($act->type==1) checked </#if>>
                                    <span class="sp-full">每满减</span>
                                    <input type="radio" name="type" id="full-cut-fold" value="3" <#if ($act->type==3) checked </#if>>
                                    <span class="sp-full">满折</span>
                                    <input type="radio" name="type" id="full-cut-fold" value="4" <#if ($act->type==4) checked </#if>>
                                    <span class="sp-full">仅第X件打折</span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top !important;">
                                <span class="tb-full-left"><strong>*</strong>满减金额：</span>
                            </td>
                            <td>
                                <dl class="full-dl">
                                    <dt <#if  ($act->type == 4) style="display: none;" </#if>>
                                        <div class="money-status">
                                            <input type="radio" name="full" id="full-money" <#if ($act->rule[0]->full_money>0||(!$act)) checked </#if> value="1">
                                            <span>满金额</span>
                                        </div>
                                    </dt>
                                    <dd>
                                        <div act_type="1"  <#if ($act->type == 1) style="display:block" </#if>>
                                            每满&nbsp;<input type="text" class="tb-text" name="full_money[]" onkeyup="value=value.replace(/[^\d.]/g,'')" <#if ($act->type == 1 && $act->rule[0]->full_money>0)value="${act->rule[0]->full_money!}" </#if>>&nbsp;元，
                                            减&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="tb-text" name="reduce_money[]" <#if ($act->type == 1 && $act->rule[0]->full_money>0) value="${act->rule[0]->reduce_money!}" </#if>/>&nbsp;元
                                        </div>
                                        <div act_type="2" <#if ($act->type == 2 || (!$act)) style="display:block" </#if>>
                                            <#if ($act->type == 2 && $act->rule[0]->full_money>0)
                                                <#list ($act->rule as $item)
                                            <div>满&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="full_money[]" value="${item->full_money!}">&nbsp;元，
                                            减&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="reduce_money[]" value="${item->reduce_money!}"/>&nbsp;元
                                                <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                            </div>
                                                </#list>
                                            <#else>
                                                <div>满&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="full_money[]">&nbsp;元，
                                                    减&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="reduce_money[]"/>&nbsp;元
                                                    <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                                </div>
                                            </#if>
                                            <div class="add_modal hide">
                                                <div>
                                                满&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="full_money[]">&nbsp;元，
                                                减&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="reduce_money[]"/>&nbsp;元
                                                <img src="http://${image_domain!}/image/admin/sign_del.png" class="del_line">
                                                </div>
                                            </div>
                                        </div>
                                        <div act_type="3"  <#if ($act->type == 3) style="display:block" </#if>>
                                            <#if ($act->type == 3 && $act->rule[0]->full_money>0)
                                                <#list ($act->rule as $item)
                                                    <div>满&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${item->full_money!}" class="tb-text" name="full_money[]">&nbsp;元，
                                                        打&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="tb-text" value="${item->discount!}" name="discount[]"/>&nbsp;折
                                                        <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                                    </div>
                                                </#list>
                                            <#else>
                                            <div>满&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')"  name="full_money[]">&nbsp;元，
                                            打&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="discount[]"/>&nbsp;折
                                                <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                            </div>
                                            </#if>
                                            <div class="add_modal hide">
                                                <div>
                                                满&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="tb-text" name="full_money[]">&nbsp;元，
                                                打&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="tb-text" name="discount[]"/>&nbsp;折
                                                <img src="http://${image_domain!}/image/admin/sign_del.png" class="del_line">
                                                </div>
                                            </div>
                                        </div>
                                    </dd>
                                    <dt <#if  ($act->type == 4) style="display: none;" </#if>>
                                        <div class="money-status">
                                            <input type="radio" name="full"  id="full-count" value="0" <#if ($act->rule[0]->amount>0) checked </#if>>
                                            <span>满件数</span>
                                        </div>
                                    </dt>
                                    <dd>
                                        <div act_type="1"  <#if ($act->type == 1) style="display:block" </#if>>
                                            每满&nbsp;<input type="number" onkeyup="value=value.replace(/[^\d]/g,'')"  class="tb-text" name="amount[]" <#if ($act->type == 1)value="${act->rule[0]->amount!}" </#if>>&nbsp;件，
                                            减&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="reduce_money[]" <#if ($act->type == 1)value="${act->rule[0]->reduce_money!}" </#if>/>&nbsp;元
                                        </div>
                                        <div act_type="2"  <#if ($act->type == 2 || (!$act)) style="display:block" </#if>>
                                            <#if ($act->type == 2 && $act->rule[0]->amount>0)
                                                <#list ($act->rule as $item)
                                            <div>满&nbsp;<input type="number" onkeyup="value=value.replace(/[^\d]/g,'')"  class="tb-text" name="amount[]" value="${item->amount!}">&nbsp;件，
                                            减&nbsp;<input type="text" class="tb-text" name="reduce_money[]" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${item->reduce_money!}"/>&nbsp;元
                                                <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                            </div>
                                                </#list>
                                            <#else>
                                                <div>满&nbsp;<input type="number" onkeyup="value=value.replace(/[^\d]/g,'')"  class="tb-text" name="amount[]">&nbsp;件，
                                                    减&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="reduce_money[]"/>&nbsp;元
                                                    <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                                </div>
                                                </#if>
                                                <div class="add_modal hide">
                                                    <div>
                                                        满&nbsp;<input type="number" onkeyup="value=value.replace(/[^\d]/g,'')"   class="tb-text" name="amount[]">&nbsp;件，
                                                        减&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="reduce_money[]"/>&nbsp;元
                                                        <img src="http://${image_domain!}/image/admin/sign_del.png" class="del_line">
                                                    </div>
                                                </div>
                                        </div>
                                        <div act_type="3"  <#if ($act->type == 3) style="display:block" </#if>>
                                            <#if ($act->type == 3 && $act->rule[0]->amount>0)
                                                <#list ($act->rule as $item)
                                            <div>满&nbsp;<input type="number" class="tb-text" onkeyup="value=value.replace(/[^\d]/g,'')"  name="amount[]" value="${item->amount!}">&nbsp;件，
                                            打&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="discount[]" value="${item->discount!}"/>&nbsp;折
                                                <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                            </div>
                                                </#list>
                                            <#else>
                                                    <div>满&nbsp;<input type="number" onkeyup="value=value.replace(/[^\d]/g,'')"  class="tb-text" name="amount[]">&nbsp;件，
                                                        打&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="tb-text" name="discount[]"/>&nbsp;折
                                                        <img src="http://${image_domain!}/image/admin/sign_jia.png" class="add_line">
                                                    </div>
                                                </#if>

                                            <div class="add_modal hide">
                                                <div>
                                                满&nbsp;<input type="number" class="tb-text" onkeyup="value=value.replace(/[^\d]/g,'')"  name="amount[]">&nbsp;件，
                                                打&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="tb-text" name="discount[]"/>&nbsp;折
                                                <img src="http://${image_domain!}/image/admin/sign_del.png" class="del_line">
                                                </div>
                                            </div>
                                        </div>
                                        <div act_type="4"  <#if ($act->type == 4) style="display:block" </#if>>
                                            <#if ($act->type == 4 && $act->rule[0]->amount>0)
                                                <#list ($act->rule as $item)
                                                        第&nbsp;<input type="number" class="tb-text" onkeyup="value=value.replace(/[^\d]/g,'')"  name="amount[]" value="${item->amount!}">&nbsp;件，
                                                        打&nbsp;<input type="text" class="tb-text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="discount[]" value="${item->discount!}"/>&nbsp;折
                                                </#list>
                                            <#else>
                                                    第&nbsp;<input type="number" onkeyup="value=value.replace(/[^\d]/g,'')"  class="tb-text" name="amount[]" min="1">&nbsp;件，
                                                    打&nbsp;<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="tb-text" name="discount[]" min="0"/>&nbsp;折
                                            </#if>
                                        </div>
                                    </dd>
                                </dl>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <span class="tb-full-left"><strong>*</strong>有效期：</span>
                            </td>
                            <td>
                                <span>
                                    <input type="text" class="tb-text date-text" value="${act->start_time!}" name="start_time"
                                           id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" > 至
                                    <input type="text" class="tb-text date-text" value="${act->end_time!}"  name="end_time"
                                           id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off">
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top !important;">
                                <span class="tb-full-left"><strong>*</strong>活动商品：</span>
                            </td>
                            <td class="goods-td">
                                <ul>
                                    <li>
                                        <div class="goods-status">
                                            <input type="radio"  id="all-goods-full" name="radio_goods"  value="0" <#if (!$act->recommend_goods_id) checked </#if> has_goods="${has_goods!}">
                                            <span>全部商品</span><span class="tips">同一个商品只能添加进入一个满折满减活动,选择全部商品则无法再次创建满折满减活动。</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="goods-status">
                                            <input type="radio" id="one-goods-full"  name="radio_goods" value="1" <#if ($act->recommend_goods_id) checked </#if>  all_goods="${all_goods!}">
                                            <span>指定商品</span>
                                        </div>
                                    </li>
                                    <li class="mkt_part_goods">
                                        <input type="button" value="+ 选择商品" id="sel-goods-btn" class="choose_goods" name="choose_goods">
                                        <input type="hidden" name="recommend_goods_id" value="${act->recommend_goods_id!}">
                                        <div class="goods_area">
                                            <#if ($act->recommend_goods_id)
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
                                                    <#list ($act->goods_info as $item)
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
                                                            <td><a href="javascript:void(0)" goods_id="${item->goods_id!}" class="del">删除</a></td>
                                                        </tr>
                                                    </#list>
                                                </tbody>
                                                </table>
                                            <#else>
                                                <table class="goods_modal">
                                                    <thead>
                                                        <tr>
                                                            <th width="45%">商品名称</th>
                                                            <th width="15%">价格</th>
                                                            <th width="15%">库存</th>
                                                            <th width="25%">操作</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody class="tbody"></tbody>
                                                </table>
                                            </#if>
                                        </div>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr class="card_goods">
                            <td style="vertical-align: top!important;">会员专享活动：</td>
							<td style="padding-top: 2px;"><input type="checkbox" name="is_card_exclusive" <#if ($act->card_id) checked </#if> style="padding:0;border:0;vertical-align: text-top;">
                                <span style="color:#000;line-height:30px;vertical-align: middle;">用户持有会员卡才可以参与活动</span>
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
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="coupon_footer fix_footer">
                    <button onclick="return false;" class="save">保存</button>
                </div>
            </form>
        </div>
    </div>
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
    var hasSaved = true;
    util.inputChange();
    util.radioChange('type');
    util.radioChange('full');
    util.radioChange('radio_goods');
    var goods_id = '${act->recommend_goods_id!}';
    <#if ($id && $act->start_time<=date("Y-m-d H:i:s"))
    var name;
    $('input').each(function(){
        name =$(this).attr("name");
        if( name!='recommend_goods_id' && name!='_token' && name!='choose_goods' && name!='act_name' && name!='goods_have_card_str'){
            $(this).prop('disabled','disabled');
        }
    });
    </#if>

    function picker(){
        hasSaved = false;
        return WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
    }
    $(".add_line").click(function(){
        var obj = $(this).parent().parent().find('.add_modal').children().clone();
        $(this).parent().parent().find('.add_modal').before(obj);
        hasSaved = false;
    });
    $(".full-dl").on('click','.del_line',function(){
        $(this).parent().remove();
    });
    $("input[name='type']").change(function(){
        $('[act_type]').hide();
        $('[act_type='+$(this).val()+']').show();
        if ($(this).val() == 4) {
            $('[act_type]').parent().parent().find('dt').hide();
            $('[act_type]').parent().css('marginLeft', '0');
        } else {
            $('[act_type]').parent().parent().find('dt').show();
            $('[act_type]').parent().css('marginLeft', '21px');
        }
    });
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
        hasSaved = false;
        if($('.goods_table tr').length == 1){
            $('.goods_table').hide();
        }
        check_goods_area_height();
    });
    $('input[name="radio_goods"]').click(function () {
        if($(this).val() == 0){
            $('.mkt_part_goods').hide();
        }
        if($(this).val() == 1){
            $('.mkt_part_goods').show();
        }
    });
    if($('input[name="radio_goods"]:checked').val() == 0){
        $('.mkt_part_goods').hide();
    }else{
        $('.mkt_part_goods').show();
    }
    //选择商品
    $('.choose_goods').on('click',function(){
        var goods_array = $('.goods_table').attr('goods_array');
        if(!goods_array){
            goods_array = 0;
        }
        var currentTime = (new Date()).valueOf();
        var startTime = $('input[name="start_time"]').val();
        startTime = startTime.replace(/-/g, '/');
        var time = new Date(startTime);
        time = time.getTime();
        var endTime = $('input[name="end_time"]').val();
        endTime = endTime.replace(/-/g, '/');
        var time1 = new Date(endTime);
        time1 = time1.getTime();
        if(time < currentTime && '${act->act_name!}') {
            $('.choose_goods').css('background', '#ebebe4');
            return false;
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var checkedId1 = $('input[name="recommend_goods_id"]').val();
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px','430px']
                , content: '/admin/public/purchase/goods/list?promotion=1&id_array='+goods_array+'&record_select_value='+checkedId1//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var goods = $('input[name="recommend_goods_id"]').val();
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
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var goods=[];
                    var body = layer.getChildFrame('body', index);
                    var checkedId = iframe.find('#record_select_value').val();
                    $('input[name="recommend_goods_id"]').val(checkedId);
                    util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                        console.log(response);
                        var list = response.content;
                        var html = '';
                        for (var i in list) {
                            list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                            html += '<tr>' +
                                '        <td>' +
                                '            <div class="goods_img">' +
                                '                <img src="'+list[i].goods_img+'" />' +
                            '            </div>' +
                            '            <div class="goods_info">' +
                            '                <div class="goods_name">'+list[i].goods_name+'</div>' +
                            '                <div class="goods_spec">'+list[i].prd_desc+'</div>' +
                            '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                                        '<td>'+list[i].goods_number+'</td>' +
                                        '<td><a href="##" item="'+list[i].prd_id+'" class="change_goods_del">删除</a></td>'
                            '        </td>';
                            html += '</tr>';
                        }
                        console.log(html)
                        
                        $('.goods_table').css('display','block');
                        $('.goods_table .tbody').html(html);
                        $('.goods_modal').css('display','block');
                        $('.goods_modal .tbody').html(html);
                        check_goods_area_height()
                        layer.close(index);
                    },{select_id: checkedId});

                    // if(goods_id){
                    //     $(".goods_table tr:gt(0)").remove();
                    //     iframe.contents().find('tr[data-back="false"]').each(function(){
                    //         var el = $('.goods_modal_clone').find('tr').clone();
                    //         el.removeClass('hide');
                    //         goods.push($(this).attr('goods_id'));
                    //         el.find('td').eq(0).html($(this).find('td').eq(0).html());
                    //         el.find('td').eq(1).text($(this).find('td').eq(2).text());
                    //         el.find('td').eq(2).text($(this).find('td').eq(3).text());
                    //         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
                    //         $('.goods_table tr:first-child').after(el);
                    //     });
                    //     $('.goods_table').show();
                    // }else{
                    //     $(".goods_modal tr:gt(0)").remove();
                    //     iframe.contents().find('tr[data-back="false"]').each(function(){
                    //         var el = $('.goods_modal_clone').find('tr').clone();
                    //         el.removeClass('hide');
                    //         goods.push($(this).attr('goods_id'));
                    //         el.find('td').eq(0).html($(this).find('td').eq(0).html());
                    //         el.find('td').eq(1).text($(this).find('td').eq(2).text());
                    //         el.find('td').eq(2).text($(this).find('td').eq(3).text());
                    //         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
                    //         $('.goods_modal tr:first-child').after(el);
                    //     });
                    //     $('.goods_modal').show();
                    // }
                    // $("input[name='recommend_goods_id']").val(goods);
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    $(document).on('click', '.change_goods_del', function () {
        var good_id = $('input[name="recommend_goods_id"]').val();
        var goodIds = good_id.split(',');
        var index = $.inArray($(this).attr('item'), goodIds);
        if (index) {
            goodIds.splice(index, 1);
        }
        $('input[name="recommend_goods_id"]').val(goodIds.join(','));
        $(this).parent().parent().parent().find('tr').length < 2 ? $('.goods_modal').css('display','none') : $(this).parent().parent().remove();
        check_goods_area_height()
    });
    
    $('.save').click(function(){
        if($('input[name="act_name"]').val() == ''){
            util.mobile_alert('请填写名称');
            return false;
        }
        if($('input[name="start_time"]').val() == ''){
            util.mobile_alert('请填写开始时间');
            return false;
        }
        if($('input[name="end_time"]').val() == ''){
            util.mobile_alert('请填写结束时间');
            return false;
        }
        if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
            util.mobile_alert('开始时间不能大于结束时间');
            return false;
        }
        if($('input[name="radio_goods"]:checked').val() == '1'){
            if($('input[name="goods_id"]').val() == ''){
                util.mobile_alert('请选择商品');
                return false;
            }
        }
        if($("input[name='is_card_exclusive']").is(":checked") && $('.card-info-row').find('.card_span').length <=0 ){
            util.mobile_alert('请选择专享会员卡');
            return false;
        }

        if($('input[name="radio_goods"]:checked').attr('has_goods') == 1){
                util.mobile_alert('您有商品参与了其他的满折满减活动，无法设置全部商品参与');
                return false;
        }

        var flag=0;
        var has=0;
        var type = $('input[name="type"]:checked').val();
        var full = $('input[name="full"]:checked').val();
        if (type == 4) {
            $("[act_type="+type+"]").find('input').each(function () {
                if ($(this).val() == '' || isNaN(parseInt($(this).val()))) {
                    flag++;
                    $(this).focus();
                    return false;
                }
            })
            has = 1;
        } else {
            $("[act_type="+type+"]:eq("+(1-parseInt(full))+")").find('input').each(function(){
                if($(this).next().attr("name")!='' && $(this).next().attr("name")!=undefined && $(this).val()!='' && $(this).next().val() == ''){
                    flag++;
                    $(this).next().focus();
                    return false;
                }
                if($(this).next().attr("name")!='' && $(this).next().attr("name")!=undefined && $(this).val()!='' && $(this).next().val() != ''){
                    has++;
                }
            });
        }

        if(flag>0 || has ==0){
            util.mobile_alert('活动信息不完整');
            return false;
        }
        else{
            $("[act_type][act_type!="+type+"]").find('input').prop('disabled','disabled');
            $("[act_type="+type+"]:eq("+parseInt(full)+")").find('input').prop('disabled','disabled');
        }
        var n=0;
        $('[name="discount[]"]').each(function(){
            if($(this).val()!=''&&($(this).val()>=10 || $(this).val()<=0)){
                    $(this).focus();
                    n++;
                    return false;
            }
        });
        if(n>0){
            util.mobile_alert('折扣值需为0到10之间的数字');
            return false;
        }
        hasSaved = true;
        layer.ready(function () {
            layer.msg('保存成功', {time: 2000},function () {
                $("#form1").submit();
            });
        });
    });

    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
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
    check_goods_area_height()
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
            if($('.card-info-row').find('.card_span').length > 0){
                $('.card-info').css("display",'block');
            }
        }else{
            $('.card_select_box').css("display",'none');
            $('.card-info').css("display",'none');         
        }
    })

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
<script>
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
    //版本控制
    getPowerInfo('main_config','full_cut','sub_4','满折满减',0);
</script>