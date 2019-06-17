<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .tb-decorate-list>tbody>tr>td{
        height:50px;
    }
    .btn_add_group a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_add_group a:focus{
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
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .user_checkchoose{
        margin-left: 30px;
        width: 600px !important;
    }
    .user_checkchoose>input[type="checkbox"]{
        width: 12px;
        height: 12px;
        margin-top: 6px;
        border: none;
        margin-left: 35px;
    }

    input[type="checkbox"]{
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
    input[type="checkbox"]:checked{
        width: 12px;
        height: 12px;
        background: url(/image/admin/square_yes.png) no-repeat;
        background-size: 100%;
    }
    .btn-choose{
        margin-left: 110px;
    }
    .tb_paging{
        width: 55% !important;
    }
    .user_export{
        width: 85px;
        height: 30px;
        border: 1px solid #ccc;
        background: #f5f5f5;
        color: #666;
        margin-left: 15px;
        display: inline-block;
        line-height: 30px;
        padding-left: 14px;
    }
    .user_export:focus {
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none;
    }
    .user_export:hover {
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none;
    }
    .btn_opera{
        border: 1px solid #ccc;
        height: 30px;
        border-radius: 6px;
        margin-left: 10px;
    }
    td .btn_opera{
        border: none;
        color: #5A8BFF;
    }
    .add_card {
        border: none;
        background: #fff;
        color: #5A8BFF;
    }
    .choose_goods {
        margin-left: 20px;
        width: 97px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5A8BFF;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin-bottom: 0 !important;
        display: inline-block;
    }
    .goods{
        margin-left: 20px;
        width: 97px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin-bottom: 0 !important;
        margin-top: 15px;
        display: inline-block;
        float: right;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    .goods a {
        display: inline-block;
        width: 62px;
    }
    .label-delete{
        position: relative;
        cursor: pointer;
        right: -9px;
        top: -10px;
    }
    .export_title{
        background-color: #f5f5f5;
        margin-left: 20px;
        padding-top: 10px;
        height:36px;
        width: 360px;
        border-radius: 2px;
        margin-bottom: 15px;
    }
    .export_content{
        padding-top: 5px;
        margin-left: 20px;
    }
    #set-export{
        padding-top:20px;
        width:400px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/user_list.user_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/user_list.user_list")!}</span>
    </div>
    <div class="order-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                <input type="hidden" name="page" value="1">
                <input type="hidden" name="user_export" value="">
                <input type="hidden" name="batch_act" value="">
                <input type="hidden" name="tag_id" value="">
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>{{ trans("admin/user_list.mobile_number")!}</span>
                            <input type="text" name="mobile" value="${data->mobile!}" placeholder='{{ trans("admin/user_list.ipt_mobile_number")!}' />
                        </div>
                        <div class="fl">
                            <span>{{ trans("admin/user_list.wx_name")!}</span>
                            <input type="text" name="username" value="${data->username!}" placeholder='{{ trans("admin/user_list.ipt_wx_name")!}' />
                        </div>
                        <div class="fl">
                            <span>来源</span>
                            <select name="source">
                                <option value="-2" <#if ($data->source == -2) selected </#if>>全部</option>
                                <option value="-1" <#if ($data->source==-1) selected </#if>>未获取</option>
                                <option value="0" <#if ($data->source == '0') selected </#if>>后台</option>
                                <#if (!empty($source_list))
                                    <#list ($source_list as $source)
                                        <option value="${source->store_id!}" <#if ($data->source == $source->store_id) selected </#if>>${source->store_name!}</option>
                                    </#list>
                                </#if>
                            </select>
                        </div>
                        <div class="fl">
                            <span>{{ trans("admin/user_list.member_card")!}</span>
                            <select name="user_card" id="user_card">
                                <option value="">{{ trans("admin/user_list.all")!}</option>
                                <#list ($card_list as $item)
                                    <option value="${item->id!}" <#if ($item->id==$data->user_card or $item->id == $user_card) selected </#if>>${item->card_name!}</option>
                                </#list>
                            </select>
                        </div>
                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl order_label">
                            <span>{{ trans("admin/user_list.label")!}</span>
                            <input type="hidden" name="tag" class="tag_search" value="${tag_search!}">
                            <span class="order_label_span"   style="width: 150px; height: 30px;"></span>
                            <ul class="order_tag_ul">
                                <li><input type="text" placeholder="搜索标签" class="ipt_search" /></li>
                                <#if ($tag_list[0])
                                    <#list ($tag_list as $item)
                                        <li class="list_tag" tag_id="${item->tag_id!}" data-title="${item->tag_name!}">${item->tag_name!}</li>
                                    </#list>
                                <#else>
                                    <li>你还没有设置过标签<a href="/admin/tag/list?top_index=5">标签管理</a></li>
                                </#if>
                            </ul>
                        </div>
                        <div class="fl" style="width: auto;">
                            <span>{{ trans("admin/user_list.reg_time")!}</span>
                            <input  style="width: 150px; height: 30px;" type="text" name="start_time" value="${data->start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input  style="width: 150px; height: 30px;" type="text" name="end_time" value="${data->end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <div class="fl">
                            <span>邀请人</span>
                            <input type="text" name="invite_user" value="${data->invite_user!}" placeholder='输入邀请人昵称' />
                        </div>
                    </li>
                    <li class="order-info-li clearfix more_search" <#if ($data->cart_start_time || $data->cart_end_time || $data->buy_count_low
                     || $data->buy_count_height ||$data->buy_start_time || $data->buy_end_time || $data->goods_ids || $data->unit_price_low || $data->unit_price_height
                     ||$data->login_start_time || $data->login_end_time) <#else> hidden </#if>>
                        <div class="fl" style="width: auto;margin-left: 40px;">
                            <span style="width: auto;">指定时间内登录有记录</span>
                            <input  style="width: 150px; height: 30px;" type="text" name="login_start_time" value="${data->login_start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input  style="width: 150px; height: 30px;" type="text" name="login_end_time" value="${data->login_end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <div class="fr" style="width: auto;margin-right: 32px">
                            <span style="width: auto;">客单价</span>
                            <input type="text" name="unit_price_low" value="${data->unit_price_low!}" style="width: 150px; height: 30px;margin-left: 20px;" >
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input type="text" name="unit_price_height" value="${data->unit_price_height!}" style="width: 150px; height: 30px;" >
                        </div>
                    </li>
                    <li class="order-info-li clearfix more_search"  <#if ($data->cart_start_time || $data->cart_end_time || $data->buy_count_low
                     || $data->buy_count_height ||$data->buy_start_time || $data->buy_end_time || $data->goods_ids || $data->unit_price_low || $data->unit_price_height
                     ||$data->login_start_time || $data->login_end_time) <#else> hidden </#if>>
                        <div class="fl" style="width: auto;margin-left: 40px;">
                            <span style="width: auto;">指定时间内有加购行为</span>
                            <input  style="width: 150px; height: 30px;" type="text" name="cart_start_time" value="${data->cart_start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input  style="width: 150px; height: 30px;" type="text" name="cart_end_time" value="${data->cart_end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <div class="fr" style="width: auto;margin-right: 32px">
                            <span style="width: auto;">累计购买次数</span>
                            <input type="text" name="buy_count_low" value="${data->buy_count_low!}" style="width: 150px; height: 30px;margin-left: 20px;" >
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input type="text" name="buy_count_height" value="${data->buy_count_height!}">
                        </div>
                    </li>
                    <li class="order-info-li clearfix more_search" <#if ($data->cart_start_time || $data->cart_end_time || $data->buy_count_low
                     || $data->buy_count_height ||$data->buy_start_time || $data->buy_end_time || $data->goods_ids || $data->unit_price_low || $data->unit_price_height
                     ||$data->login_start_time || $data->login_end_time) <#else> hidden </#if>>
                        <div class="fl" style="width:auto;margin-left: 40px;">
                            <span style="width: auto;">指定时间内有交易记录</span>
                            <input  style="width: 150px; height: 30px;" type="text" name="buy_start_time" value="${data->buy_start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                    onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input  style="width: 150px; height: 30px;" type="text" name="buy_end_time" value="${data->buy_end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                    onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <div class="fr" style="width:440px;margin-right: 36px;" id="goods">
                            <input type="hidden" name="goods_ids" value="${data->goods_ids!}">
                            <div>
                                <span style="width: auto;">购买指定商品</span>
                                <div class="choose_goods">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商品
                                </div>
                            </div>
                            <div id="goods_list">
                                <#if ($data->goods_list)
                                    <#list ($data->goods_list as $goods)
                                        <div class="goods"><a href="/admin/goods/manage/edit?goods_id=${goods->goods_id!}" style="color: #5A8BFF" title="${goods->goods_name!}" goods_id="${goods->goods_id!}">${goods->goods_name!}</a>
                                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="label-delete"></div>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </li>
                    <li  class="order-info-li clearfix">
                        <div class="fl user_checkchoose">
                            <input type="checkbox" name="has_mobile" value="1" <#if ($data->has_mobile) checked </#if> class="checkbox_prev has_mobile" >有手机号
                            <input type="checkbox" name="has_score" value="1" <#if ($data->has_score) checked </#if> class="checkbox_prev has_score" >有积分
                            <input type="checkbox" name="has_balance" value="1" <#if ($data->has_balance) checked </#if> class="checkbox_prev has_balance"  >有余额
                            <input type="checkbox" name="has_card" value="1" <#if ($data->has_card) checked </#if> class="checkbox_prev has_card"  >有会员卡
                            <input type="checkbox" name="has_delete" value="1" <#if ($data->has_delete) checked </#if> class="checkbox_prev has_delete"  >已禁止登录
                        </div>
                        <a class="btn-choose" style="display: inline-block;line-height: 30px;padding-left: 28px;">{{ trans("admin/user_list.choose")!}</a>
                        <a class="user_export">{{ trans("admin/user_list.user_export")!}</a>
                    </li>
                    <li class="order-info-li clear-fix" style="text-align: center;">
                        <a href="javascript:void(0);" style="color: #5a8bff;" class="show_more" <#if ($data->cart_start_time || $data->cart_end_time || $data->buy_count_low
                     || $data->buy_count_height ||$data->buy_start_time || $data->buy_end_time || $data->goods_ids || $data->unit_price_low || $data->unit_price_height
                     ||$data->login_start_time || $data->login_end_time) hidden </#if>>更多 <img src="http://${image_domain!}/image/admin/show_more.png" alt=""></a>
                        <a href="javascript:void(0);" style="color: #5a8bff;" class="hid_some" <#if ($data->cart_start_time || $data->cart_end_time || $data->buy_count_low
                     || $data->buy_count_height ||$data->buy_start_time || $data->buy_end_time || $data->goods_ids || $data->unit_price_low || $data->unit_price_height
                     ||$data->login_start_time || $data->login_end_time) <#else> hidden </#if>>收起 <img src="http://${image_domain!}/image/admin/hid_some.png" alt=""></a>
                    </li>
                </ul>
            </form>
        </div>
        <div style="margin-top: 10px;">
            <form action="/admin/user/manage/list?top_index=${_GET['top_index']!}" id="form2" method="post">
                {{ csrf_field()!}
                <input type="hidden" name="has_delete" value="${data->has_delete!}">
                <input type="hidden" name="has_card" value="${data->has_card!}">
                <input type="hidden" name="has_balance" value="${data->has_balance!}">
                <input type="hidden" name="has_score" value="${data->has_score!}">
                <input type="hidden" name="has_mobile" value="${data->has_mobile!}">
                <input type="hidden" name="end_time" value="${data->end_time!}">
                <input type="hidden" name="start_time" value="${data->start_time!}">
                <input type="hidden" name="user_card" value="${data->user_card!}">
                <input type="hidden" name="source" value="${data->source!}">
                <input type="hidden" name="username" value="${data->username!}">
                <input type="hidden" name="mobile" value="${data->mobile!}">
                <input type="hidden" name="tag" class="tag_search" value="${tag_search!}">
                <input type="hidden" name="mini_score" value="${mini_score!}" id="mini_score">

                <input type="hidden" name="login_start_time" value="${data->login_start_time!}">
                <input type="hidden" name="login_end_time" value="${data->login_end_time!}">
                <input type="hidden" name="cart_start_time" value="${data->cart_start_time!}">
                <input type="hidden" name="cart_end_time" value="${data->cart_end_time!}">
                <input type="hidden" name="buy_start_time" value="${data->buy_start_time!}">
                <input type="hidden" name="buy_end_time" value="${data->buy_end_time!}">
                <input type="hidden" name="unit_price_low" value="${data->unit_price_low!}">
                <input type="hidden" name="unit_price_height" value="${data->unit_price_height!}">
                <input type="hidden" name="buy_count_low" value="${data->buy_count_low!}">
                <input type="hidden" name="buy_count_height" value="${data->buy_count_height!}">
                <input type="hidden" name="goods_ids" value="${data->goods_ids!}">


                <div class="member_list_main">
                    <table width="100%">
                        <thead>
                            <tr>
                                <td width="8%">ID</td>
                                <td width="10%">{{ trans("admin/user_list.user_name")!}</td>
                                <td width="8%">{{ trans("admin/user_list.mobile_number")!}</td>
                                <td>邀请人</td>
                                <td width="11%">{{ trans("admin/user_list.yu_e")!}</td>
                                <td width="11%">{{ trans("admin/user_list.score")!}</td>
                                <td width="11%">{{ trans("admin/user_list.user_card")!}</td>
                                <td width="6%">来源</td>
                                <td>{{ trans("admin/user_list.reg_time")!}</td>
                                <td>{{ trans("admin/user_list.operation")!}</td>
                            </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr id="user_${item->user_id!}" >
                                <td style="padding:0">
                                    <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" />
                                    <input type="checkbox" name="checkbox" class="ipt_checkbox" user_id="${item->user_id!}" />
                                    <input type="hidden" class="user_id" value="${item->user_id!}" />
                                    <input type="hidden" class="is_delete" value="${item->is_delete!}" />
                                    <input type="hidden" name="btn_type" />
                                    <span>${item->user_id!}</span>
                                </td>
                                <td>
                                    <span style="display: inline-block;width: 100px;word-break: break-all;">
                                        <a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #5A8BFF;">${item->username!}</a>
                                    </span>
                                </td>
                                <td>${item->mobile!}</td>
                                <td style="width: 9em;word-break: break-all;">
                                    <#if  ($item->invite_id > 0)
                                        <span style="display: inline-block;width: 100px;word-break: break-all;">
                                            <a href="/admin/user/manage/center?user_id=${item->invite_id!}&top_index=5&sub_index=0" style="color: #5A8BFF;">${item->invite_user_name!}</a>
                                        </span>
                                    </#if>
{{--                                    ${item->invite_user_name!}--!}
                                </td>
                                <td width="120px">
                                    <input type="text" value="${item->account or '0.00'!}" class="ipt-money" disabled />
                                    <img src="http://${image_domain!}/image/admin/add_some.png" alt="" class="btn_money" />
                                </td>
                                <td width="120px">
                                    <input type="text" value="${item->score!}" class="ipt-integral" disabled />
                                    <img src="http://${image_domain!}/image/admin/add_some.png" alt="" class="btn_integral" />
                                </td>
                                <td width="130px">
                                    <#if ($item->user_card)
                                        <div class="card_fl" style="line-height:42px;">
                                        <#list ($item->user_card as $card)
                                            <span class="all_card_list" card_type="${card->card_type!}" card_id="${card->card_id!}" style="width:80px;line-height: 20px;text-align: left;" card_no="${card->card_no!}">${card->card_name!}</span>
                                        </#list>
                                        </div>
                                    </#if>
                                    <div class="card_info" style="float: right;">
                                        <a href="##" class="card-setting">设置</a>
                                        <a href="/admin/user/manage/card/list?user_id=${item->user_id!}&sub_index=0&top_index=5&type=1&name=${item->username!}" target="_blank">更多</a>
                                    </div>
                                </td>
                                <td>${item->source_name!}</td>
                                <td>${item->create_time!}</td>
                                <td width="20%" style="text-align: center;">
                                    <a href="/admin/user/manage/account/list?user_id=${item->user_id!}&sub_index=0&top_index=5&type=1&name=${item->username!}" target="_blank" class="btn_opera">{{ trans("admin/user_list.change_money")!}</a>
                                    <a href="/admin/user/manage/score/list?user_id=${item->user_id!}&sub_index=0&top_index=5&type=1&name=${item->username!}" target="_blank" class="btn_opera">{{ trans("admin/user_list.change_integral")!}</a>
                                    <a href="#" class="btn_opera btn_stop"><#if ($item->is_delete) {{ trans("admin/user_list.is_delete")!} <#else> {{ trans("admin/user_list.stop_login")!} </#if></a>
                                    <br />
                                    <a href="#" class="btn_opera btn_label layui-btn-normal" style="margin-left: -55px;" user_tag='${item->user_tag!}' data-method="offset" data-type="label1">{{ trans("admin/user_list.set_label")!}</a>
                                    <a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" target="_blank"  class="btn_opera btn_detail">{{ trans("admin/user_list.look_detail")!}</a>
                                </td>
                            </tr>
                            </#list>

                        </tbody>
                    </table>
                </div>
                <#if ($data_list->count != 0)
                <div class="member_list_footer">
                    <span>
                        <span style="position: relative; margin-left: 30px;">
                            <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" />
                            <input type="checkbox" id="ipt_all" />
                            <label for="ipt_all">{{ trans("admin/user_list.check_all")!}</label>
                        </span>
                        <select name="" id="" class="btn_opera batch_delete" style="width:110px">
                            <option value="">批量禁止登录</option>
                            <option value="check">对选中的人禁止登录</option>
                            <option value="search">对筛选出来的<span>${data_list->total() ?? 0!}</span>人禁止登录</option>
                        </select>
                        <select name="" id="" class="btn_opera batch_tag" style="width:110px">
                            <option value="">批量{{ trans("admin/user_list.add_label")!}</option>
                            <option value="check">对选中的人加标签</option>
                            <option value="search">对筛选出来的<span>${data_list->total() ?? 0!}</span>人加标签</option>
                        </select>
                        <select name="" id="" class="btn_opera batch_card" style="width:124px">
                            <option value="">批量发放会员卡</option>
                            <option value="check">对选中的人发卡</option>
                            <option value="search">对筛选出来的<span>${data_list->total() ?? 0!}</span>人发卡</option>
                        </select>
                        <select name="" id="" class="btn_opera batch_score" style="width:110px">
                            <option value="">批量修改积分</option>
                            <option value="check">对选中的人修改积分</option>
                            <option value="search">对筛选出来的<span>${data_list->total() ?? 0!}</span>人修改积分</option>
                        </select>
                        <select name="" id="" class="btn_opera change_invite" style="width:124px">
                            <option value="">批量修改邀请人</option>
                            <option value="check">对选中的人修改邀请人</option>
                            <option value="search">对筛选出来的<span>${data_list->total() ?? 0!}</span>人修改邀请人</option>
                        </select>
                        {{--<a href="javascript:void(0);" name="batch_delete" class="btn_opera batch_delete">批量禁止</a>--!}
                        {{--<a href="javascript:void(0);" name="batch_tag" class="btn_opera batch_tag">{{ trans("admin/user_list.add_label")!}</a>--!}
                        {{--<a href="javascript:void(0);" name="change_invite" class="btn_opera change_invite" style="border: none;color: #5A8BFF;">批量修改邀请人</a>--!}
                        {{--<a href="javascript:void(0);" name="batch_card" class="btn_opera batch_card" type="all">批量发卡</a>--!}
                        {{--<a href="javascript:void(0);" name="batch_score" class="btn_opera batch_score">批量修改积分</a>--!}
                    </span>
                    <table width="100%" border="0" class="tb_paging">
                        <tr>
                            <td style="text-align:right;">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="#" style="background: rgb(250, 250, 250);" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="#" style="background: rgb(250, 250, 250);"
                                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="#" style="background: rgb(250, 250, 250);"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="#" style="background: rgb(250, 250, 250);"
                                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')" autocomplete="off"
                                >{{ trans("admin/common.page.page")!}
                                <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <#else>
                    <div style="width: 100%;padding: 0px 12px 12px 12px;background: #fff;">
                        <div style="border: 1px solid #eee;">
                            <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                            </div>
                            <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                        </div>
                    </div>
                </#if>
            </form>
        </div>
    </div>
</div>

<div id="set-label">
    <label style="color: #a3a3a3;font-size: 12px;">
        一个用户最多可以打5个标签，超过数量的标签将不再被添加给该用户
    </label>
    <div>

    </div>
    <ul>
        <li><input type="text" placeholder="搜索标签" class="ipt_search" /></li>
        <#if ($tag_list)
            <#list ($tag_list as $item)
                <li class="label_list" tag_id="${item->tag_id!}" data-title="${item->tag_name!}">
                    ${item->tag_name!}
                </li>
            </#list>
        <#else>
            <li>你还没有设置过标签<a href="/admin/tag/list">标签管理</a></li>
        </#if>
    </ul>
</div>
<div id="set-money" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <div class="exchange_old">
        <span>当前金额:</span>
        <input type="text" class="money_dis" disabled />
    </div>
    <div style="margin-bottom: 10px;">
        <span>增加金额:</span>
        <input type="text" class="amount" value=""  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
        <span>（*当余额为正时，增加余额；余额为负时，减少余额*）</span>
    </div>
    <div>
        <span>增加备注:</span>
        <input type="text" class="remark" value="" size="200" style="width: 450px;"/>
    </div>
</div>
<div id="set-integral" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <div class="exchange_old">
        <span>当前积分:</span>
        <input type="text" class="score_dis" disabled />
    </div>
    <div style="margin-bottom: 10px;">
        <span>增加积分:</span>
        <input type="text" class="score" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
        <span>（*当积分为正时，增加积分；积分为负时，减少积分*）</span>
    </div>
    <div>
        <span>增加备注:</span>
        <input type="text" class="remark" value="" size="200" style="width: 450px;"/>
    </div>
</div>
<div id="set-delete" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="is_delete"/>
    <div class="exchange_old">
        <span>提示:&nbsp;</span>禁止登录后会员将不能登录了，确定禁止登录吗？
    </div>
</div>
<div id="all-delete" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="is_delete"/>
    <div class="exchange_old">
        <span>提示:&nbsp;</span>禁止登录后会员将不能登录了，确定禁止登录吗？
    </div>
</div>
<div id="set-card">
    <p>你可以在这里编辑该会员的会员卡信息，添加/删除会员卡</p>
    <div class="clearfix">
        <div class="fl">普通会员卡</div>
        <ul class="set_card_ul">
            <input type="hidden" class="user_card"/>
            <li class="set_card_last card_comment">
                <button class="add_card" card_type="0">添加</button>
                <select class="card_clone select_comment" style="height:25px;">
                    <option value="" class="card_sel">选择会员卡</option>
                    <#list ($card_list as $item)
                        <#if ($item->card_type == 0)
                            <option value="${item->id!}" card_id="${item->id!}" card_type="${item->card_type!}" class="option">${item->card_name!}</option>
                        </#if>
                    </#list>
                </select>
            </li>
        </ul>
    </div>
    <div class="clearfix">
        <div class="fl">限次会员卡</div>
        <ul class="set_card_ul">
            <input type="hidden" class="user_card"/>
            <li class="set_card_last card_count">
                <button class="add_card" card_type="1">添加</button>
                <select class="card_clone select_count" style="height:25px;">
                    <option value="" class="card_sel">选择会员卡</option>
                    <#list ($card_list as $item)
                        <#if ($item->card_type == 1)
                            <option value="${item->id!}" card_id="${item->id!}" card_type="${item->card_type!}" class="option">${item->card_name!}</option>
                        </#if>
                    </#list>
                </select>
            </li>
        </ul>
    </div>
    <div class="clearfix">
        <div class="fl">等级会员卡</div>
        <ul class="set_card_ul">
            <input type="hidden" class="user_card"/>
            <li class="set_card_last card_grade">
                <button class="add_card" card_type="2">添加</button>
                <select class="card_clone select_grade" style="height:25px;">
                    <option value="" class="card_sel">选择会员卡</option>
                    <#list ($card_list as $item)
                        <#if ($item->card_type == 2)
                            <option value="${item->id!}" card_id="${item->id!}" card_type="${item->card_type!}" class="option">${item->card_name!}</option>
                        </#if>
                    </#list>
                </select>
            </li>
        </ul>
    </div>
</div>

<div id="set-export" style="display:none" >

</div>
<select class="card_clone2 card_clone_comment">
    <option value="" class="card_sel">选择会员卡</option>
    <#list ($card_list as $item)
        <#if ($item->card_type == 0)
            <option value="${item->id!}" card_id="${item->id!}" class="option">${item->card_name!}</option>
        </#if>
    </#list>
</select>
<select class="card_clone2 card_clone_count">
    <option value="" class="card_sel">选择会员卡</option>
    <#list ($card_list as $item)
        <#if ($item->card_type == 1)
            <option value="${item->id!}" card_id="${item->id!}" class="option">${item->card_name!}</option>
        </#if>
    </#list>
</select>
<select class="card_clone2 card_clone_grade">
    <option value="" class="card_sel">选择会员卡</option>
    <#list ($card_list as $item)
        <#if ($item->card_type == 2)
            <option value="${item->id!}" card_id="${item->id!}" class="option">${item->card_name!}</option>
        </#if>
    </#list>
</select>
<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(parseInt(page) > parseInt(last_page)){
            page = last_page;
        }
        $("#page").val(page);
        $("#form2").submit();
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    $("input[name='mobile']").blur(function() {
        if($("input[name='mobile']").val() != ""){
            var re=/^1[3456789]\d{9}$/;
            if(!re.test($("input[name='mobile']").val())){
                util.mobile_alert("手机号不正确");
                return false;
            }
        }
    });
</script>


<#include "/admin/footer.ftl">
<script src="{{asset('js/admin/user_list.js')!}?v=1.1.9" type="text/javascript"></script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script>
    // $(".user_export").click(function () {
    //     var pass = getAuthorityDetail(1,"","user_export","","user_export");
    // })


    $('.user_export').click(function(){
        var param = $("#form1").serializeArray();
        var source = $('[name="source"] option:selected').text();
        var user_card = $('[name="user_card"] option:selected').text();
        var start_time = $('[name="start_time"]').val();
        var end_time = $('[name="end_time"]').val();
        var login_start_time = $('[name="login_start_time"]').val();
        var login_end_time = $('[name="login_end_time"]').val();
        var unit_price_low = $('[name="unit_price_low"]').val();
        var unit_price_height = $('[name="unit_price_height"]').val();
        var cart_start_time = $('[name="cart_start_time"]').val();
        var cart_end_time = $('[name="cart_end_time"]').val();
        var buy_count_low = $('[name="buy_count_low"]').val();
        var buy_count_height = $('[name="buy_count_height"]').val();
        var buy_start_time = $('[name="buy_start_time"]').val();
        var buy_end_time = $('[name="buy_end_time"]').val();
        $.each(param, function(i, field){
            if(field.name=='source')
            field.value =source;
            if(field.name=='user_card')
                field.value =user_card;
            if(field.name=='start_time' && field.value)
                field.value =start_time +' 至 '+end_time;
            if(field.name=='login_start_time'&& field.value)
                field.value =login_start_time +' 至 '+login_end_time;
            if(field.name=='unit_price_low' && field.value)
                field.value =unit_price_low +' 至 '+unit_price_height;
            if(field.name=='cart_start_time' && field.value)
                field.value =cart_start_time +' 至 '+cart_end_time;
            if(field.name=='buy_count_low' && field.value)
                field.value =buy_count_low +' 至 '+buy_count_height;
            if(field.name=='buy_start_time' && field.value)
                field.value =buy_start_time +' 至 '+buy_end_time;

        });
            util.ajax_json('/admin/user/list/export', function (response) {
                console.log(response)
                var list = response.content.dataArr;
                var num  = response.content.user_num;
                console.log(list)
                console.log(num)
                var html = ' <div class ="export_title"><p>&nbsp;&nbsp;根据以下条件删选出'+num+'条数据,是否确认导出？</p></div>';
                html+=' <div class ="export_content"><p>筛选条件：</p></div>'
                for (var i in list) {
                    if(list[i].name &&list[i].value){
                        html += '<div class ="export_content"><span>'+list[i].name+'：'+list[i].value+'</span></div>'
                    }else{
                        html += '<div class ="export_content"><span>'+list[i].name+'</span></div>'
                    }

                }
                $('#set-export').html(html);
                if(response.message="success"){
                    layui.use('layer', function() { //独立版的layer无需执行这一句
                        var $ = layui.jquery, layer = layui.layer;
                        layer.open({
                            type: 1
                            , title: ['提示', 'text-align:center;padding: 0px;']
                            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                            , area: ['auto','auto']
                            , content: $('#set-export')//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                            , btn: ['确定', '取消']
                            , btnAlign: 'r' //按钮居右
                            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                            ,success: function (layero, index) {

                            }
                            , yes: function (index, layero) { //保存按钮的回调
                                // var body = layer.getChildFrame('body', index);
                                var pass = getAuthorityDetail(1,"","user_export","","user_export");
                                layer.close(index);
                            }, btn2: function (index, layero) {
                                //按钮取消的回调

                            }
                        });
                    });
                }

            }, {data:param})
    });
    $(".btn-choose").click(function () {
        $('input[name="user_export"]').val("");
        $("#form1").submit();
    })
    //批量修改会员卡
    $(".batch_card").change(function () {
        batch_user_id = [];
        if($(".batch_card").val() == 'check'){
            $('input[name="checkbox"]:checked').each(function () {
                batch_user_id.push($(this).attr('user_id'));
            });
        }else if($(".batch_card").val() == 'search'){
            batch_user_id = -1;
        }
        if (batch_user_id.length == 0) {
            util.mobile_alert('请选择会员');
            $(".batch_card").val('');
            return false;
        }else {
            addCard($(this),"all");
        }
    })
    $(".batch_score").change(function () {
        batch_user_id = [];
        if($(".batch_score").val() == 'check'){
            $('input[name="checkbox"]:checked').each(function () {
                batch_user_id.push($(this).attr('user_id'));
            });
        }else if($(".batch_score").val() == 'search'){
            batch_user_id = -1;
        }
        if (batch_user_id.length == 0) {
            util.mobile_alert('请选择会员');
            $(".batch_score").val('');
            return false;
        }else {
            exchange_some('修改积分','#set-integral');
            $('#set-integral').find('.score_dis').val($('input[name="mini_score"]').val());
            $('#set-integral').find('.exchange_old').find("span").eq(0).html("当前最低积分：");
            $('#set-integral').find('.user_id').val(batch_user_id);
        }

    })
</script>
