<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/jquery.bigcolorpicker.css">
<link href="/css/admin/membership_card.css?v=1.2.7" rel="stylesheet" type="text/css"/>
<style>
    body{
        padding-bottom: 40px;
    }
    input[type='text']:focus, textarea:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }

    .btn_save a:hover, .btn_save a:focus    {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .del{
        color:#5A8BFF ;cursor:pointer;
    }
    div .checkbox_prev{
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
    .checkbox_prev:checked{
        width: 12px;
        height: 12px;
        background: url(/image/admin/square_yes.png) no-repeat;
        background-size: 100%;
    }
    .btn_save a{
        margin-left: 0%;
    }
    .in_li{
        margin-right:10px;
        width: 90px;
    }
    .in_li input{
        margin-right:10px;
    }
    .activation_cfg{
        margin-left: 20px;
    }
</style>
<div class="title">
    <span>会员管理 / </span><span style="color: #666;">普通会员卡</span>
</div>
<div class="mem_cont fix_every_footer">
    <div class="mc_left" style="overflow-y: auto;overflow-x: hidden;">
        <div class="ml_title"></div>
        <div class="ml_content">
            <div class="example_card">
                <div class="card_detail">
                    <img src="${shop_logo->shop_avatar!}" alt="" class="store_logo">
                    <span class="em_card_name"></span>
                </div>
                <div class="effect_date">有效期：<span></span></div>
            </div>
            <div class="discount_power">
                <div class="power_title">会员权益（折扣）</div>
                <div class="power_content"></div>
            </div>
            <div class="score_power">
                <div class="s_power_title">会员折扣（积分）</div>
                <div class="s_power_detail">
                    <p class="make_give"></p>
                    <div class="man">
                        <p>购物满<span class="man_money"></span>送<span class="man_jian"></span>积分</p>
                    </div>
                    <p class="every_man"></p>
                </div>
            </div>
            <div class="card_exclusive">
                <div class="e_power_title">会员专享商品</div>
            </div>
            <div class="charge_power">
                <div class="c_power_title">卡充值规则</div>
                <div class="c_power_detail">
                    <p class="c_make_give"></p>
                    <div class="c_man">
                        <p></p>
                    </div>
                    <p class="c_every_man"></p>
                </div>
            </div>
            <div class="contact_mobile">
                <div></div>
                <p>联系电话：</p>
                <p></p>
            </div>
            <div class="ex_use_notice">
                <div class="notice-title">会员卡使用说明</div>
                <div class="act_receive">领取后需要填写激活信息进行激活才可使用</div>
                <div class="notice-content"></div>
            </div>
            <div class="using_store">
                <div class="store-title">使用门店</div>
                <div class="store-content"></div>
            </div>
        </div>
    </div>
    <div class="mc_right">
        <div class="right_title">基础设置</div>
        <form action="" method="post" id="form1">
            {{ csrf_field()!}
            <input type="hidden" name="bg_img" id="bg_img_path" value="${data_list->bg_img!}">
            <input type="hidden" name="buy_score" value="">
            <input type="hidden" name="charge_money" value="">
            <input type="hidden" name="id" value="${data_list->id!}">
            <input type="hidden" name="card_type" value="${data_list->card_type or 0!}">
            {{--<div class="card_type">
                <div>卡类型：</div>
                <div>
                    <select name="card_type" id="card_types">
                        <option value="0">普通会员卡</option>
                        <option value="1">升级会员卡</option>
                        <option value="2">规则会员卡</option>
                    </select>
                </div>
            </div>--!}
            <div class="card_name">
                <div><span class="must">*</span>会员卡名称：</div>
                <div><input type="text" name="card_name" value="${data_list->card_name!}"></div>
            </div>
            <div class="bg_type">
                <div>背景图：</div>
                <div class="bg_choose">
                    <div>
                        <input type="radio" name="bg_type" id="bg_color" <#if ($data_list->bg_type==0) checked="checked" class="checkbox_actives" </#if>  value="0" >背景色
                        <input type="text" style="margin-left: 3%;height: 30px;border: 1px solid #ccc;width: 65px;background: ${data_list->bg_color!}" name="bg_color" value="${data_list->bg_color!}">
                    </div>
                    <div class="back_images">
                        <input type="radio" name="bg_type" id="bg_img" <#if ($data_list->bg_type==1) checked="checked" class="checkbox_actives" </#if>  value="1">
                        <span>背景图</span>
                        <a href="##" class="add_img">
                            <#if ($data_list->bg_img)
                                <img src="http://${image_domain!}/${data_list->bg_img!}"/>
                            </#if>
                        </a>
                    </div>
                </div>
            </div>
            <div class="mem_advan">
                <div><span class="must">*</span>会员权益：</div>
                <div>
                    <div>
                        <input  type="checkbox" name="power" value="0" class="checkbox_prev power_discount" <#if (($data_list->discount != "" && $act=='edit') || $data_list->discount == '' && empty($act)) checked </#if> onclick="power1(this)" />
                        会员折扣
                        <input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="discount" value="${data_list->discount!}">折
                    </div>
                    <div class="member_discount_goods">
                        <span>会员折扣商品</span>
                        <input type="radio" name="discount_is_all"  value="1" checked>全部商品
                        <input type="radio" name="discount_is_all" value="0" <#if ($data_list->discount_is_all===0)checked </#if> style="margin-left:50px;">指定商品
                        <div class="choose_box_1">
                            <input  type="hidden" name="goods_ids" value="${data_list->discount_goods_id!}">
                            <input  type="hidden" name="cat_ids" value="${data_list->discount_cat_id!}">
                            <input  type="hidden" name="sort_ids" value="${data_list->discount_sort_id!}">
                            <div>
                                <div class="choose_goods" style="display: inline-block; margin-bottom: 10px;">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商品
                                </div>
                                <span class="choose_num">已选择商品：<strong>0</strong>件</span>
                            </div>
                            <div>
                                <div class="add_sort" style="display: inline-block;">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商家分类
                                </div>
                                <span class="choose_num">已选择分类：<strong>0</strong>个分类</span>
                            </div>
                            <div>
                                <div class="add_cate" style="display: inline-block;">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加平台分类
                                </div>
                                <span class="choose_num">已选择分类：<strong>0</strong>个分类</span>
                            </div>
                        </div>
                    </div>
                    <div class="member_discount_goods" style="margin-top:20px;">
                        <input type="checkbox" name="pay_own_good"  <#if ($data_list->pay_own_good == 1) checked </#if>>
                        <span>会员专享商品</span><span style="color: #666;margin-left: 25px;">选择仅供持有此会员卡用户购买的商品</span>
                        <div class="choose_box_2">
                            <input  type="hidden" name="goods_ids_1" value="${data_list->goodsCard->gc_goods_id!}">
                            <input  type="hidden" name="cat_ids_1" value="${data_list->goodsCard->gc_cat_id!}">
                            <input  type="hidden" name="sort_ids_1" value="${data_list->goodsCard->gc_sort_id!}">
                            <div>
                                <div class="choose_goods" style="display: inline-block; margin-bottom: 10px;">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商品
                                </div>
                                <span class="choose_num">已选择商品：<strong>0</strong>件</span>
                            </div>
                            <div>
                                <div class="add_sort" style="display: inline-block;">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商家分类
                                </div>
                                <span class="choose_num">已选择分类：<strong>0</strong>个分类</span>
                            </div>
                            <div>
                                <div class="add_cate" style="display: inline-block;">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加平台分类
                                </div>
                                <span class="choose_num">已选择分类：<strong>0</strong>个分类</span>
                            </div>
                        </div>
                    </div>
                    <div>
                        <input type="checkbox" name="power" value="1" class="checkbox_prev power_score" <#if ($data_list->buy_score_flag) checked </#if> onclick="power2(this)" />积分获取&nbsp;&nbsp;&nbsp;
                        开卡赠送
                        <input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="sorce" value="${data_list->sorce!}"  />积分
                    </div>
                    <div class="first_manzeng" style="margin-left: 50px;">
                        <input type="radio" name="offset" id="once_offset" <#if ($data_list->offset==0) checked="checked" class="checkbox_actives" </#if> value="0" onclick="score_rule(this)" />
                        <#list  ($data_list->buy_score as $item)
                            {{--<#if  ($loop->last)
                            <#else>--!}
                                <#if  ($loop->first)
                                购物满<input  type="text" name="goods_moneys[]" value="${item->money!}" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 送
                                <input  type="text" name="get_scores[]" value="${item->score!}" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 积分
                                <a href="##" class="btn_add_lines"><img src="http://${image_domain!}/image/admin/sign_jia.png" alt=""></a>
                                <#elseif>(!$loop->last)
                                    <div class="add_lines">
                                        购物满<input  type="text" name="goods_moneys[]" value="${item->money!}"  onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 送
                                        <input  type="text" name="get_scores[]" value="${item->score!}" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 积分
                                        <a href="##" class="del_lines del_score_lines"><img src="http://${image_domain!}/image/admin/sign_del.png" alt=""> </a>
                                    </div>
                                </#if>
                            {{--</#if>--!}
                        </#list>
                    </div>
                    <div style="margin-left: 50px;">
                        <#list  ($data_list->buy_score as $item)
                            <#if  ($loop->last)
                                <input  type="radio" name="offset" id="per_offset" <#if ($data_list->offset==1) checked="checked" class="checkbox_actives" </#if> value="1" onclick="score_rule(this)" />
                                购物每满 <input  type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="per_goods_moneys" value="${item->each_money!}" onblur="score_rule('#per_offset')" />
                                送 <input  type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="per_get_scores" value="${item->each_score!}" onblur="score_rule('#per_offset')" />积分

                            </#if>
                        </#list>
                    </div>
                    <div class="version" style="margin-top: 10px;"><input  type="checkbox" name="power" value="2" class="checkbox_prev power_charge"
                                                          <#if ($data_list->charge_money_flag && $data_list->charge_money_flag!=-3) checked </#if> onclick="power3(this)" />卡充值&nbsp;&nbsp;&nbsp;开卡赠送<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="send_money" value="${data_list->send_money!}">元</div>
                    <div class="version" style="margin-left:50px;">
                        <input  type="radio" name="offset_money" id="only_charge" <#if ($data_list->offset_money==2 && $data_list->charge_money_flag!=-3) checked="checked" class="checkbox_actives" </#if> value="2" onclick="charge_rule(this)" />仅充值
                    </div>
                    <div class="version first_manzeng_money" style="margin-left: 50px;">
                        <input  type="radio" name="offset_money" id="once_money_offset" <#if ($data_list->offset_money==0 && $data_list->charge_money_flag!=-3) checked="checked" class="checkbox_actives" </#if> value="0" onclick="charge_rule(this)" />
                        <#list  ($data_list->charge_money as $item)

                                <#if  ($loop->first)
                                    充值满<input type="text" name="moneys[]" <#if ( $data_list->charge_money_flag!=-3) value="${item->money!}" </#if> onkeyup="this.value=this.value.replace(/[^\.\d]/g,'');this.value=this.value.replace('.','');" onblur="charge_rule('#once_money_offset')" /> 送
                                    <input type="text" name="get_moneys[]" <#if ( $data_list->charge_money_flag!=-3) value="${item->score!}" </#if> onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="charge_rule('#once_money_offset')" /> 元
                                    <a href="##" class="btn_add_money"><img src="http://${image_domain!}/image/admin/sign_jia.png" alt=""></a>
                                <#elseif>(!$loop->last)
                                    <div class="add_lines">
                                        充值满<input  type="text" name="moneys[]" <#if ( $data_list->charge_money_flag!=-3) value="${item->money!}"  </#if> onkeyup="this.value=this.value.replace(/[^\.\d]/g,'');this.value=this.value.replace('.','');" onblur="charge_rule('#once_money_offset')" /> 送
                                        <input type="text" name="get_moneys[]" <#if ( $data_list->charge_money_flag!=-3) value="${item->score!}" </#if> onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="charge_rule('#once_money_offset')" /> 元
                                        <a href="##" class="del_lines del_money_lines"><img src="http://${image_domain!}/image/admin/sign_del.png" alt=""> </a>
                                    </div>
                                </#if>

                        </#list>
                    </div>
                    <div class="version" style="margin-left: 50px;">
                        <#list  ($data_list->charge_money as $item)
                            <#if  ($loop->last)
                                <input type="radio" name="offset_money" id="per_money_offset" <#if ($data_list->offset_money==1 && $data_list->charge_money_flag!=-3) checked="checked" class="checkbox_actives" </#if> value="1" onclick="charge_rule(this)" />
                                充值每满 <input  type="text" onkeyup="this.value=this.value.replace(/[^\.\d]/g,'');this.value=this.value.replace('.','');" name="per_moneys" <#if ( $data_list->charge_money_flag!=-3) value="${item->each_money!}" </#if> onblur="charge_rule('#per_money_offset')" />
                                送 <input  type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="per_get_moneys" <#if ( $data_list->charge_money_flag!=-3) value="${item->each_score!}" </#if> onblur="charge_rule('#per_money_offset')" />元

                            </#if>
                        </#list>
                    </div>
                </div>
            </div>
            <div class="mem_efftime">
                <div><span class="must">*</span>会员有效期：</div>
                <div>
                    <div style="margin-bottom: 10px" class="effect_times">
                        <input  type="radio" name="expire_type" <#if ($data_list->expire_type==0)checked="checked" class="checkbox_actives" </#if> id="fixed_date" value="0"> 固定日期
                        <input type="text" style="width: 25%;" name="start_time"  value="${data_list->start_time!}" id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                        到 <input  type="text" style="width: 25%;" name="end_time" value="${data_list->end_time!}"id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                    </div>
                    <span style="color: #9d9d9d;padding-left: 0px;display: inline-block;position: relative; top: -3px;">
                        例如：选择日期2018-1-2到2018-1-5，表示有效期为2018-1-2 00:00:00 到2018-1-5 24:00:00
                    </span>
                    <div>
                        <input  type="radio" name="expire_type" <#if ($data_list->expire_type==1)checked="checked" class="checkbox_actives" </#if> id="choose_date" value="1"> 自领取之日起<input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="receive_day" value="${data_list->receive_day!}">
                        <select name="date_type" id="date_types" style="width:22%;height: 30px;">
                            <option value="0" <#if ($data_list->date_type==0) selected </#if> >日</option>
                            <option value="1" <#if ($data_list->date_type==1) selected </#if> >周</option>
                            <option value="2" <#if ($data_list->date_type==2) selected </#if> >月</option>
                        </select>内有效
                    </div>
                    <div>
                        <input type="radio" name="expire_type"
                               <#if ($data_list->expire_type == 2) checked
                               class="checkbox_actives" </#if> id="fixed_date" value="2"> 永久有效
                    </div>
                </div>
            </div>
            <div class="store_list">
                <div><span class="must">*</span>使用门店：</div>
                <input type="hidden" name="store_list" value="${data_list->store_list or 0!}">
                <div>
                    <div>
                        <input type="radio" name="store_list_type" <#if ($data_list->store_list==0)checked="checked" class="checkbox_actives" </#if> id="all_store" value="0" onclick="radio_choose(this)" />
                        <label for="all_store">全部门店</label>
                        <input style="margin-left: 30px" style="z-index: 2" type="radio" name="store_list_type" <#if ($data_list->store_list!= 0 && $data_list->store_list!= -1)checked="checked" class="checkbox_actives" </#if> id="part_store" value="1" onclick="radio_choose(this)" />
                        <label style="z-index: 2" for="part_store">部分门店</label>
                        <input style="margin-left: 30px" style="z-index: 2" type="radio" name="store_list_type" <#if ($data_list->store_list==-1)checked="checked" class="checkbox_actives" </#if> id="no_store" value="-1" onclick="radio_choose(this)" />
                        <label style="z-index: 2">不可在门店使用</label>
                    </div>
                    <div style="margin-left: 25px;">
                        <span style="color: #9d9d9d;">配置仅限制门店买单、门店自提、核销门店预约服务时的会员卡使用，线上购买发货默认所有会员卡均可以使用</span>
                    </div>
                    <#if ($data_list->store_list && $data_list->store_list != "{}" && $data_list->store_list!= -1)
                        <table class="store_table">
                            <tr height="30px">
                                <th width="200px">门店名称</th>
                                <th width="100px">操作</th>
                            </tr>
                            <#list ($data_list->storeinfo_list as $store_info)
                            <tr class="store_table_tr">
                                <td>${store_info->store_name!}</td>
                                <td><a href="javascript:void(0)" store_id="${store_info->store_id!}" <#if ($act!="edit")class="del" </#if>>删除</a></td>
                            </tr>
                            </#list>
                        </table>
                    <#else>
                        <table class="store_modal">
                            <tr>
                                <th width="200px">门店名称</th>
                                <th width="100px">操作</th>
                            </tr>
                        </table>
                    </#if>
                    <div class="add_store" hidden>+添加门店</div>
                </div>
            </div>
            <div class="use_notice">
                <div><span class="must" style="display: none">*</span>使用须知：</div>
                <div><textarea name="desc" id="" cols="30" rows="10">${data_list->desc!}</textarea></div>
            </div>
            <div class="mobiles">
                <div>联系电话：</div>
                <div><input type="text" name="mobile" onkeyup="value=value.replace(/[^\d\-]/g,'')" value="${data_list->mobile!}"></div>
            </div>
    </div>
    <div class="mc_right_bottom" style="margin-left:320px">
        <div class="right_title">领取设置
        <#if ($act=='edit' && $data_list->is_pay ==2)
        <a href="/admin/user/member/code/receivelist?top_index=5&card_id=${data_list->id!}" style="color:#5a8bff;">查看领取详情</a>
        </#if>
        </div>
        <div class="ifbuy clearfix">
            <div><span class="must">*</span>是否需要购买：</div>
            <div>
                <div>
                    <input type="radio" name="is_pay" value="0" <#if ($data_list->is_pay ==0)checked </#if> form="form1">直接领取
                    <input type="radio" name="is_pay" value="1" <#if ($data_list->is_pay ==1)checked </#if> style="margin-left:30px;" form="form1">需要购买
                    <input type="radio" name="is_pay" value="2" <#if ($data_list->is_pay ==2)checked </#if> style="margin-left:30px;">需要领取码
                </div>
                <div class="isbuy_set">
                    <div>
                        <input type="radio" name="pay_type" value="0" <#if ($data_list->pay_type==0) checked </#if> form="form1">现金购买<input type="text" name="pay_money"  <#if ($data_list->pay_type==0) value="${data_list->pay_fee!}" </#if> form="form1">元
                    </div>
                    <div>
                        <input type="radio" name="pay_type"  value="1" <#if ($data_list->pay_type==1) checked </#if> form="form1">积分购买<input type="text" name="pay_score" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d+)*$/,'');value=value.replace(/[^\d]/g,'')" <#if ($data_list->pay_type==1) value="${data_list->pay_fee!}" </#if> form="form1">分
                    </div>
                </div>
                <div class="access_code">
                    <input type="hidden" name="batch_id_str" />
                    <div class="code_set clearfix">
                        <span class="fl" style="margin-top:2px;">
                            <input type="radio" name="receive_action" <#if ($data_list->receive_action == 1 || !$data_list->receive_action) checked </#if> value="1" form="form1"> 领取码领取
                        </span>
                        <div class="code_set_box">
                            <#if  ($data_list->receive_action == 1 && $card_batch->count() > 0)
                                <#list  ($card_batch as $batch)
                                    <p code_index="${loop->iteration!}" batch_id="${batch->id!}">
                                        <span>批次${loop->iteration!}</span> <span>批次名称</span>
                                        <input type="hidden" name="batch_id[]" value="${batch->id!}" />
                                        <input type="text" form="form1" name="batch_name" value="${batch->name!}" disabled /> <a href="javascript:;" class="upload" receive-action="1">领取码</a>
                                        <a href="javascript:;" class="increase">增加批次</a> <a href="javascript:;" class="remove_batch">废除批次</a><a href="javascript:;" class="import_record" receive-action="1">生成/导入记录</a>
                                    </p>
                                </#list>
                            <#else>
                                <p code_index="1" batch_id="0"><span>批次1</span> <span>批次名称</span>
                                    <input type="hidden" name="batch_id[]" />
                                    <input type="text" form="form1" name="batch_name" value=""/> <a href="javascript:;" class="upload" receive-action="1">领取码</a>
                                    <a href="javascript:;" class="increase">增加批次</a> <a href="javascript:;" class="remove_batch">废除批次</a><a href="javascript:;" class="import_record" receive-action="1">生成/导入记录</a>
                                </p>
                            </#if>
                        </div>
                    </div>
                    <div class="key_set clearfix">
                        <span class="fl" style="margin-top: 2px;">
                            <input type="radio" name="receive_action" <#if ($data_list->receive_action == 2) checked </#if> value="2" form="form1"> 卡号+密码领取
                        </span>
                        <div class="key_set_box">
                            <#if  ($data_list->receive_action == 2 && $card_batch->count() > 0)
                                <#list  ($card_batch as $batch)
                                    <p key_index="${loop->iteration!}" batch_id="${batch->id!}"><span>批次${loop->iteration!}</span> <span>批次名称</span>
                                        <input type="hidden" name="batch_id[]" value="${batch->id!}" />
                                        <input type="text" form="form1" name="batch_name" value="${batch->name!}" disabled> <a href="javascript:;" class="upload" receive-action="2">卡号+密码</a>
                                        <a href="javascript:;" class="increase">增加批次</a> <a href="javascript:;" class="remove_batch">废除批次</a><a href="javascript:;" class="import_record" receive-action="2">生成/导入记录</a>
                                    </p>
                                </#list>
                                <#else>
                                <p key_index="1" batch_id="0"><span>批次1</span> <span>批次名称</span>
                                    <input type="hidden" name="batch_id[]" />
                                    <input type="text" form="form1" name="batch_name"> <a href="javascript:;" class="upload" receive-action="2">卡号+密码</a>
                                    <a href="javascript:;" class="increase">增加批次</a> <a href="javascript:;" class="remove_batch">废除批次</a><a href="javascript:;" class="import_record" receive-action="2">生成/导入记录</a>
                                </p>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="ifactive">
            <div><span class="must">*</span>是否需要激活：</div>
            <div>
                <div>
                    <input  type="radio" name="activation" <#if ($data_list->activation==0)checked="checked"  class="checkbox_actives" </#if> id="act_no" value="0" <#if ($data_list->activation==1 && $act == 'edit') onclick="examine_num()" </#if> form="form1">否
                    <input  style="z-index: 2 ;margin-left: 30px" type="radio" name="activation" <#if ($data_list->activation==1)checked="checked" class="checkbox_actives" </#if> id="act_yes" value="1" form="form1">是
                </div>
                <div style="height:20px">
                    <span style="font-size: 13px;line-height: 17px;margin: 0px;    margin-left: 20px;">选择后，请勾选你需要用户填写的信息</span>
                </div>
                <div class="activation_cfg clearfix" <#if ($data_list->activation==0 || empty($data_list)) style="display: none" </#if>>
                    <input type="hidden" form="form1" name="activation_cfg" value="{{json_encode($data_list->activation_cfg)!}">
                    {{--<div class="fl in_li"><input form="form1" type="checkbox" name="activation_cfg_box" value="username" <#if (in_array('username',$data_list->activation_cfg)) checked </#if>>会员昵称</div>--!}   
                    <div class="fl in_li"><input type="checkbox" form="form1" name="activation_cfg_box" value="real_name" <#if (in_array('real_name',$data_list->activation_cfg)) checked </#if>>真实姓名</div>
                    <div class="fl in_li"><input form="form1" type="checkbox" name="activation_cfg_box" value="mobile" <#if (in_array('mobile',$data_list->activation_cfg)) checked </#if>>手机号</div>
                    <div class="fl in_li"><input type="checkbox"  form="form1" name="activation_cfg_box" value="cid" <#if (in_array('cid',$data_list->activation_cfg)) checked </#if>>身份证号码</div>
                    <div class="fl in_li"><input type="checkbox" form="form1" name="activation_cfg_box" value="sex" <#if (in_array('sex',$data_list->activation_cfg)) checked </#if>>性别</div>
                    <div class="fl in_li"><input type="checkbox"  form="form1" name="activation_cfg_box" value="birthday" <#if (in_array('birthday',$data_list->activation_cfg)) checked </#if>>生日</div>
                    <div class="fl in_li"><input type="checkbox" form="form1" name="activation_cfg_box" value="marital_status" <#if (in_array('marital_status',$data_list->activation_cfg)) checked </#if>>婚姻状况</div>
                    <div class="fl in_li"><input type="checkbox"  form="form1" name="activation_cfg_box" value="education" <#if (in_array('education',$data_list->activation_cfg)) checked </#if>>教育程度</div>
                    <div class="fl in_li"><input type="checkbox"  form="form1" name="activation_cfg_box" value="industry_info" <#if (in_array('industry_info',$data_list->activation_cfg)) checked </#if>>所在行业</div>
                    <div class="fl in_li"><input type="checkbox"  form="form1" name="activation_cfg_box" value="address" <#if (in_array('address',$data_list->activation_cfg)) checked </#if>>所在地</div>
                </div>
                <div class="activation_cfg"  <#if ($data_list->activation==0 || empty($data_list)) style="display: none" </#if>>
                    <span>激活信息是否需要审核：</span>
                    <div><input type="radio" name="examine" value="0" <#if ($data_list->examine == 0) checked </#if> <#if ($data_list->examine == 1 && $act == "edit") onclick="examine_num()" </#if> form="form1">无需审核 <input type="radio" name="examine" value="1" <#if ($data_list->examine == 1) checked </#if> style="margin-left:25px;" form="form1">需要审核</div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="btn_save fix_footer">
     <a href="##">保存</a>
</div>

</form>

<div class="same_modal hide">
    <div class="add_lines">
        购物满
        <input type="text" name="goods_moneys[]" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 送
        <input type="text" name="get_scores[]" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 积分
        <a href="##" class="del_lines del_score_lines"><img src="http://${image_domain!}/image/admin/sign_del.png" alt=""> </a>
    </div>

</div>
<div class="same_money_modal hide">
    <div class="add_lines">
        充值满
        <input type="text" name="moneys[]" onkeyup="this.value=this.value.replace(/[^\.\d]/g,'');this.value=this.value.replace('.','');" onblur="charge_rule('#once_money_offset')" /> 送
        <input type="text" name="get_moneys[]" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="charge_rule('#once_money_offset')" /> 元
        <a href="##" class="del_lines del_money_lines"><img src="http://${image_domain!}/image/admin/sign_del.png" alt=""> </a>
    </div>

</div>
<table class="store_modal_clone hide">
    <tr class="store_modal_tr">
        <td></td>
        <td><a href="javascript:void(0)" class="del">删除</a></td>
    </tr>
</table>
<div id="set-category">
    <#if (!$cat_list)
        <div class="no_category">
            <div>
            <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
            <p>暂无分类</p>
            </div>    
        </div>
    <#else>
        <ul>
            <#if ($cat_list)
                <#list ($cat_list as $item)
                    <li class="cate_li">
                        <div class="first_cate">
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" />
                            <span>
                        <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                        <label for="cat_${item->cat_id!}">${item->cat_name!}(${item->goods_num!})</label>
                    </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                            <span>
                                <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                <label for="cat_${sub_item->cat_id!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                            </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                            <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                            <label for="cat_${th_item->cat_id!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                        </span>
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </#list>
                            </div>
                        </#if>
                    </li>
                </#list>
            </#if>
        </ul>
    </#if>
</div>

<div class="code_popup">
    <div><input type="radio" name="action" value="1" checked form="form1">自动生成领取码 <span class="tips">将随机生成xxx个唯一领取码，领取码由数字+字母组成</span></div>
    <div class="create_code">
        <p><span>领取码前缀:</span>  <input type="text" name="code_prefix" form="form1" maxlength="4"> <span class="tips">0-4个数字或字母</span></p>
        <p><span>领取码位数:</span>  <input type="text" name="code_size" form="form1"> <span class="tips">领取码组成位数，限制6-12位</span></p>
        <p><span>领取数量:</span>  <input type="text" name="number" form="form1"></p>
        <a href="javascript:;" class="export">导出领取码</a>
    </div>
    <div>
        <input type="radio" name="action" value="2" form="form1">导入领取码 <span class="tips">需导入已有领取码</span>
    </div>
    <div class="upload_code">
        <div class="clearfix">
            <p>第一步：<a href="{{ main_url('doc/会员卡领取码模板.xls')!}" style="color:#5a8bff">下载导入模板</a></p>
            <p>第二步：导入领取码</p>
            <span>上传文件：</span>
            <div class="file_box clearfix">
                <span class="fl filetext"></span>
                <span class="fr" onclick="document.getElementById('file1').click()">浏览...</span>
            </div>
            <input type="file" name="card_import" form="form1" id="file1" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[0].innerText=this.value">
            <p style="padding-left: 73px;color: #999;">请严格按照导入模板格式进行导入</p>
        </div>
    </div>
</div>

<div class="key_popup">
    <div><input type="radio" name="action" value="1" checked form="form1">自动生成卡号+密码 <span class="tips">将随机生成xxx组不重复卡号+密码，均由数字+字母组成</span></div>
    <div class="create_key">
        <p><span>卡号前缀:</span> <input type="text" name="card_prefix" form="form1" maxlength="4"> <span class="tips">0-4个数字或字母</span></p>
        <p><span>卡号位数:</span>  <input type="text" name="card_size" form="form1"> <span class="tips">卡号组成位数，限制6-12位</span></p>
        <p><span>密码位数:</span>  <input type="text" name="card_pwd_size" form="form1"> <span class="tips">密码组成位数</span></p>
        <p><span>领取数量:</span>  <input type="text" name="number" form="form1"></p>
        <a href="javascript:;" class="export" style="margin-left:98px;">导出卡号+密码</a>
    </div>
    <div>
        <input type="radio" name="action" value="2" form="form1">导入卡号+密码 <span class="tips">需导入已有卡号+密码</span>
    </div>
    <div class="upload_key">
        <div class="clearfix">
            <p>第一步：<a href="{{ main_url('doc/会员卡号+密码模板.xls')!}" style="color:#5a8bff">下载导入模板</a></p>
            <p>第二步：导入卡号+密码</p>
            <span>上传文件：</span>
            <div class="file_box clearfix">
                <span class="fl filetext"></span>
                <span class="fr" onclick="document.getElementById('file2').click()">浏览...</span>
            </div>
            <input type="file" name="card_import" id="file2" form="form1" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[1].innerText=this.value">
            <p style="padding-left: 73px;color: #999;">请严格按照导入模板格式进行导入</p>
            <p style="padding-left: 73px;color: #999;">导入的卡号不可与店铺内已有卡号重复</p>
        </div>
    </div>
</div>
<div class="second_edit" style="display: none;">
    <div style="margin: 10px">
        <p>编辑会员卡信息，将会导致会员权益变更，可能出现客诉，是否确认修改会员卡信息？</p>
        <p style="color: red">注：</p>
        <p>1、修改会员权益将导致已领会员卡用户的会员权益发生变更，请谨慎修改！</p>
        <p>2、修改有效期不会影响已领的会员卡，修改后的有效期只对新领卡的用户生效</p>
    </div>
</div>
<script type="text/javascript">
    var hasSaved = true;
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    var card_batch = @json($card_batch) || [];
    var card_id = "${data_list->id!}";
    var act = "${act!}";
    //版本控制
    var version = '${version!}';
    if(version == -1){
        $(".version input").attr("disabled","true");
        $(".version input[type='checkbox']").removeAttr("checked");
        $(".version input[type='radio']").removeClass("checkbox_actives");
        $(".version input").val("");
    }
    $(".version").click(function () {
        if(version == -1){
            util.systemNotice(1,'',"充值会员卡");
        }
    });
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });

    $("#main-table").FixedHead({tableLayout: "fixed"});
    function picker(){
        hasSaved = false;
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }
    $('input[name="discount"]').blur(function () {
        if($('input[name="power"]').prop('checked')==true){
            if($(this).val() > 10 || $(this).val() <= 0){
                util.mobile_alert('请输入0-10之间的数字');
                $(this).focus();
                $(this).val('');
            }
        }
    });
    var store_list = '${data_list->store_list!}';
    //删除
    $("body").on('click','.del',function(){
        var store_id = $(this).attr('store_id');
        var goods = $('input[name="store_list"]').val();
        if(isNaN(goods)) {
            var goods_array = goods.split(',');
            for (var i = 0; i < goods_array.length; i++) {
                if (goods_array[i] == store_id) {
                    goods_array.splice(i, 1);
                    break;
                }
            }
            $('input[name="store_list"]').val(goods_array.join());
        }
        else{
            $('input[name="store_list"]').val('');
        }
        $(this).parent().parent().remove();
        if($('input[name="store_list_type"]:checked').val() == 1) {
            var sc_arr = [];
            if (store_list) {
                $('.store_table_tr').each(function () {
                    sc_arr.push($(this).find('td').eq(0).text());
                });
            } else {
                $('.store_modal .store_modal_tr').each(function () {
                    sc_arr.push($(this).find('td').eq(0).text());
                });
            }
            var sc_string = sc_arr.join('，');
            $('.store-content').html(sc_string);
        }
        if($('.store_table tr').length == 1){
            $('.store_table').hide();
        }
        if($('.store_modal tr').length == 1){
            $('.store_modal').hide();
        }
    });
    //选择全部门店或者部分门店时，左侧门店相关信息的显示
    function radio_choose(obj) {
        if(obj.value == 0){
            $('.store-content').html('全部门店');
            $('input[name="store_list"]').val(0);
            $('.add_store').attr("hidden","true");
            $('.store_modal').css('display','none')
            $('.store_table').css('display','none')
        }
        if(obj.value == -1){
            $('.store-content').html('不可在门店使用');
            $('input[name="store_list"]').val(-1);
            $('.add_store').attr("hidden","true");
            $('.store_modal').css('display','none')
            $('.store_table').css('display','none')
        }
        if(obj.value == 1){
            $('.add_store').removeAttr("hidden");
            $('.store-content').html('');
        }
    }
    //是否选折扣
    function power1(obj) {
        if($(obj).is(":checked")){
            if($("input[name='discount']").val() != '' && $('input[name="discount_is_all"]:checked').val() == '1'){
                $(".power_content").css("display","block");
                $(".power_content").html("全部商品折扣"+$("input[name='discount']").val()+"折");
            } else if ($('input[name="discount"]').val() != '' && $('input[name="discount_is_all"]:checked').val() == '0'){
                $(".power_content").css("display","block");
                $(".power_content").html("指定商品折扣"+$("input[name='discount']").val()+"折");
            }
        } else{
            $(".power_content").hide();
        }
    }
    //积分
    function score_rule(obj) {
        if($('.power_score').is(':checked')){
            if($(obj).val() == 1){
                if(!$(obj).is(":checked")) return;
                var per_goods_moneys = $('input[name="per_goods_moneys"]').val();
                var per_get_scores = $('input[name="per_get_scores"]').val();
                $('.every_man').show();
                $('.man').hide();
                $('.every_man').html('购物每满' + per_goods_moneys + '送' + per_get_scores + '积分');
            }
            if($(obj).val() == 0){
                if(!$(obj).is(":checked")) return;
                var score_arr = [];
                $('.mem_advan').find('input[name="goods_moneys[]"]').each(function () {
                    var score_obj = {};
                    if($(this).val() != '')
                        score_obj.money = $(this).val();
                    if(score_obj.money)
                        score_obj.score = $(this).next().val();
                    score_arr.push(score_obj);
                });
                console.log(score_arr);
                $('.every_man').hide();
                $('.man').show();
                $('.man p').show();
                var c_man_html = '';
                for(var i in score_arr){
                    if(score_arr[i].money)
                        c_man_html += "<p>购物满" + score_arr[i].money + "送" + score_arr[i].score +"积分</p>"
                }
                $('.man').html(c_man_html);

            }
        }
    }
    function power2(obj) {
        if($(obj).is(":checked")){
            $('.s_power_detail').show();
            if($('input[name="sorce"]').val() != ''){
                $('.make_give').show();
                $('.make_give').html('开卡赠送' + $('input[name="sorce"]').val() + '积分');
            }
            score_rule($('input[name="offset"]:checked'));
        }else{
            $('.s_power_detail').hide();
        }
    }
    //卡充值规则
    function charge_rule(obj) {
        if($('.power_charge').is(':checked')){
            if($(obj).val() == 2){
                if(!$(obj).is(":checked")) return;
                $('.c_every_man').show();
                $('.c_man').hide();
                $('.c_every_man').html('仅充值');
            }
            if($(obj).val() == 1){
                if(!$(obj).is(":checked")) return;
                var per_moneys = $('input[name="per_moneys"]').val();
                var per_get_moneys = $('input[name="per_get_moneys"]').val();
                $('.c_every_man').show();
                $('.c_man').hide();
                $('.c_every_man').html('充值每满' + per_moneys + '送' + per_get_moneys);
            }
            if($(obj).val() == 0){
                if(!$(obj).is(":checked")) return;
                var money_arr = [];
                $('.mem_advan').find('input[name="moneys[]"]').each(function () {
                    var money_obj = {};
                    if($(this).val() != '')
                        money_obj.money = $(this).val();
                    if(money_obj.money)
                        money_obj.score = $(this).next().val();
                    money_arr.push(money_obj);
                });
                //console.log(money_arr);
                $('.c_every_man').hide();
                $('.c_man').show();
                $('.c_man p').show();
                var c_man_html = '';
                for(var i in money_arr){
                    if(money_arr[i].money)
                        c_man_html += "<p>充值满" + money_arr[i].money + "送" + money_arr[i].score +"</p>"
                }
                $('.c_man').html(c_man_html);

            }
        }
    }
    function power3(obj) {
        if($(obj).is(":checked")){
            $('.c_power_detail').show();
            if($('input[name="send_money"]').val() != ''){
                $('.c_make_give').show();
                $('.c_make_give').html('开卡赠送' + $('input[name="send_money"]').val() + '元');
            }
            charge_rule($('input[name="offset_money"]:checked'));
        }else{
            $('.c_power_detail').hide();
        }
    }
    //审核人数弹框
    function examine_num(){
        util.ajax_json('/admin/ajax/card/examine/user',function(res){
            console.log(res)
            if(res.error==0){
                if(res.content.un_deal_user_num > 0){
                    layer.open({
                        type: 1,
                        title: ["提示", "text-align:center;padding: 0px;"],
                        content: '<div style="padding: 40px 20px;;text-align: center;border-bottom:1px solid #e5e5e5;">此会员卡存在<a style="color:#5A8BFF;" href="/admin/user/member/examine?card_id='+card_id+'&nav=1">'+res.content.un_deal_user_num+'</a>条激活申请，修改将会自动通过审核</div>',
                        area: 'auto',
                        btn: ['关闭'],
                        btnAlign: 'c',
                        yes: function (index) {
                            layer.close(index);
                        }
                    });
                }
            }
        },{id:card_id});
    }
    if($(".choose_box_1").find("input[name='goods_ids']").val())
        $(".choose_box_1").find(".choose_num").eq(0).css("display","inline");
    if($(".choose_box_1").find("input[name='cat_ids']").val())
        $(".choose_box_1").find(".choose_num").eq(1).css("display","inline");
    if($(".choose_box_1").find("input[name='sort_ids']").val())
        $(".choose_box_1").find(".choose_num").eq(2).css("display","inline");
    if($(".choose_box_2").find("input[name='goods_ids_1']").val())
        $(".choose_box_2").find(".choose_num").eq(0).css("display","inline");
    if($(".choose_box_2").find("input[name='cat_ids_1']").val())
        $(".choose_box_2").find(".choose_num").eq(1).css("display","inline");
    if($(".choose_box_2").find("input[name='sort_ids_1']").val())
        $(".choose_box_2").find(".choose_num").eq(2).css("display","inline");
</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/jquery.bigcolorpicker.min.js"></script>
<script src="/js/admin/membership_card.js?v=1.2.10"></script>
<script src="/js/admin/card_code.js?v=1.0.0"></script>
<script type="text/javascript">
    power1('.power_discount');
    power2('.power_score');
    power3('.power_charge');
</script>