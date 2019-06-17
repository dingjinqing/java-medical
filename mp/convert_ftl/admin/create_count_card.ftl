<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/jquery.bigcolorpicker.css">
<link href="/css/admin/membership_card.css?v=1.2.3" rel="stylesheet" type="text/css"/>
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
    .checkbox_prev{
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        width: 12px;
        height: 12px;
        background: url(/image/admin/square_no.png) no-repeat;
        background-size: 100%;
        margin-left: 20px;
        position: relative;
        top: 1px;
        margin-right: 4px;
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
    .mem_cont .mc_left {
        width: 325px;
    }
    .mc_right_bottom{
        margin-left:345px;
    }
    .mem_goods div{
        line-height:30px;
        margin-bottom:10px;
    }
    .mem_goods input[type="text"]{
        width:110px;
        border: 1px solid #ccc;
        margin: 0 8px 0 5px;
    }
    .add_goods{
        display:inline-block;
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5A8BFF;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin-right:20px;
    }
    .mc_left::-webkit-scrollbar {
        display: none;
    }
</style>
<div class="title">
    <span>会员管理 / </span><span style="color: #666;">限次会员卡</span>
</div>
<div class="mem_cont fix_every_footer">
    <div class="mc_left" style="overflow-y: auto;overflow-x: hidden;">
        <div class="ml_title"></div>
        <div class="ml_content">
            <div class="example_card">
                <div class="card_detail">
                    <img src="${shop_logo->shop_avatar!}" alt="" class="store_logo">
                    <div style="display: inline-block;width: 200px;vertical-align: middle;">
                        <span class="em_card_name"></span>
                        <div class="effect_date">有效期：<span></span></div>
                    </div> 
                </div>
            </div>
            <div class="discount_power">
                <div class="power_title">会员权益（抵扣次数）</div>
                <div class="power_content">
                    <p></p>
                    <p></p>
                </div>
            </div>
            <div class="count_card_goods">
                <div class="goods_box_title">适用商品</div>
                <ul class="goods_box">
                    <#if ($data_list->exchang_goods)
                        <#list ($exchang_goods_list as $goods)
                            <li class="clearfix">
                                <img src="${goods->goods_img!}" alt="">
                                <div>
                                    <p title="${goods->goods_name!}">${goods->goods_name!}</p>
                                    <p>￥300.00</p>
                                </div>
                            </li>
                        </#list>
                    </#if>
                </ul>
                <p class="more"><span class="down">查看更多</span></p>
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
            <div class="useing_time">
                <div class="time-title">允许使用时间</div>
                <div class="time-content"></div>
            </div>
        </div>
    </div>
    <div class="mc_right">
        <form action="" method="post" id="form1">
            {{ csrf_field()!}
            <input type="hidden" name="bg_img" id="bg_img_path" value="${data_list->bg_img!}">
            <input type="hidden" name="buy_score" value="">
            <input type="hidden" name="charge_money" value="">
            <input type="hidden" name="id" value="${data_list->id!}">
            <input type="hidden" name="card_type" value="1">
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
            <div class="mem_efftime">
                <div><span class="must">*</span>会员有效期：</div>
                <div>
                    <div style="margin-bottom: 10px" class="effect_times">
                        <input type="radio" name="expire_type" <#if ($data_list->expire_type==0)checked="checked" class="checkbox_actives" </#if> id="fixed_date" value="0"> 固定日期
                        <input type="text" style="width: 25%;" name="start_time"  value="${data_list->start_time!}" id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                        到 <input type="text" style="width: 25%;" name="end_time" value="${data_list->end_time!}"id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                    </div>
                    <span style="color: #9d9d9d;padding-left: 0px;display: inline-block;position: relative; top: -3px;">
                        例如：选择日期2018-1-2到2018-1-5，表示有效期为2018-1-2 00:00:00 到2018-1-5 24:00:00
                    </span>
                    <div>
                        <input type="radio" name="expire_type" <#if ($data_list->expire_type==1)checked="checked" class="checkbox_actives" </#if> id="choose_date" value="1"> 自领取之日起<input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="receive_day" value="${data_list->receive_day!}">
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
            <div class="mem_goods">
                <div>适用商品：</div>
                <input type="hidden" name="exchang_goods" value="${data_list->exchang_goods!}">
                <div style="margin-bottom:0">
                    <div>
                        <input type="radio" name="is_exchang" value="0" checked>不可兑换商品
                        <input type="radio" name="is_exchang" value="1" style="margin-left:20px" <#if ($data_list->is_exchang == 1) checked </#if>>部分商品
                        <input type="radio" name="is_exchang" value="2" style="margin-left:20px;" <#if ($data_list->is_exchang == 2) checked </#if>>全部商品
                    </div>
                    <div style="display:none">
                        允许兑换：<input type="text" name="exchang_count" value="${data_list->exchang_count!}">次
                    </div>
                    <div style="display:none">
                        运费策略：
                        <input type="radio" name="exchang_freight" value="0" checked>免运费
                        <input type="radio" name="exchang_freight" value="1" style="margin-left:30px;" <#if ($data_list->exchang_freight == 1) checked </#if>>使用商品运费策略
                    </div>
                    <div style="margin-bottom:0;display:none;">
                        <div class='add_goods'>+添加商品</div><span style="color:#5a8bff;" class="choose_num">已选择商品<span><#if ($data_list->exchang_goods) {{count($exchang_goods_list)!} <#else> 0 </#if></span>件</span><span style="margin-left:20px;color:#999;">最多可选择20件</span>
                    </div>
                </div>
            </div>
            <div class="mem_advan clearfix">
                <div>使用门店：</div>
                <div>
                    {{--<div>--!}
                        {{--<input type="radio" name="store_use_switch" value="1" checked>不可在门店使用--!}
                        {{--<input type="radio" name="store_use_switch" value="0" style="margin-left:30px" <#if (!is_null($data_list->store_use_switch) && $data_list->store_use_switch ==0) checked </#if>>可在门店使用--!}
                    {{--</div>--!}
                    <div style="height:auto;">
                        <input type="hidden" name="store_list" value="${data_list->store_list or 0!}">
                            <input type="radio" name="store_list_type" <#if ($data_list->store_list==0)checked="checked" class="checkbox_actives" </#if> id="all_store" value="0" onclick="radio_choose(this)" />
                            <label for="all_store">全部门店</label>
                            <input style="margin-left: 30px" style="z-index: 2" type="radio" name="store_list_type" <#if ($data_list->store_list!= 0 && $data_list->store_list!= -1)checked="checked" class="checkbox_actives" </#if> id="part_store" value="1" onclick="radio_choose(this)" />
                            <label style="z-index: 2" for="part_store">部分门店</label>
                            <input style="margin-left: 30px" style="z-index: 2" type="radio" name="store_list_type" <#if ($data_list->store_list==-1)checked="checked" class="checkbox_actives" </#if> id="part_store" value="-1" onclick="radio_choose(this)" />
                            <label style="z-index: 2">不可在门店使用</label>
                        <div>
                            <span style="color: #9d9d9d;    margin-left: 25px;">该配置仅限制核销门店预约服务时会员卡的使用</span>
                        </div>
                        <#if ($data_list->store_list && $data_list->store_list != "{}" && $data_list->store_list != -1)
                            <table class="store_table">
                                <tr height="30px">
                                    <th width="200px">门店名称</th>
                                    <th width="100px">操作</th>
                                </tr>
                                <#list ($data_list->storeinfo_list as $store_info)
                                    <tr class="store_table_tr">
                                        <td>${store_info->store_name!}</td>
                                        <td><a href="javascript:void(0)" store_id="${store_info->store_id!}" class="del" >删除</a></td>
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
                        <div class="add_store"  hidden>+添加门店</div>
                    </div>
                    <div style="display:none" class="store_use_time">允许使用时间：<input type="checkbox" name="use_time_type" value="1" class="checkbox_prev"
                                       <#if ($data_list->use_time=='' || $data_list->use_time==1 ||$data_list->use_time == 0)checked </#if> data-text="工作日" id="working" onclick="checkbox_click(this)" />
                        <label for="working">工作日</label>
                        &nbsp;
                        <input type="checkbox" name="use_time_type" value="2" class="checkbox_prev"
                               <#if ($data_list->use_time=='' || $data_list->use_time==2 ||$data_list->use_time == 0)checked </#if> data-text="双休日" id="weekend" onclick="checkbox_click(this)" />
                        <label for="weekend">双休日</label>
                    </div>
                    <div style="display:none" class="store_use_count">
                        <input type="hidden" name="use_time" <#if ($data_list->use_time!='') value="${data_liist->use_time!}" </#if>>
                        <div>允许使用<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="count" value="${data_list->count!}">次</div>
                    </div>
                    
                </div>
            </div>    
            <div class="use_notice">
                <div><span class="must" style="display: none;">*</span>使用须知：</div>
                <div><textarea name="desc" id="" cols="30" rows="10">${data_list->desc!}</textarea></div>
            </div>
            <div class="mobiles">
                <div>联系电话：</div>
                <div><input type="text" name="mobile" onkeyup="value=value.replace(/[^\d\-]/g,'')" value="${data_list->mobile!}"></div>
            </div>
    </div>
    <div class="mc_right_bottom">
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
                                        <input type="text" form="form1" name="batch_name" value="${batch->name!}" disabled/> <a href="javascript:;" class="upload" receive-action="1">领取码</a>
                                        <a href="javascript:;" class="increase">增加批次</a> <a href="javascript:;" class="remove_batch">废除批次</a>
                                    </p>
                                </#list>
                            <#else>
                                <p code_index="1" batch_id="0"><span>批次1</span> <span>批次名称</span>
                                    <input type="hidden" name="batch_id[]" />
                                    <input type="text" form="form1" name="batch_name" value=""/> <a href="javascript:;" class="upload" receive-action="1">领取码</a>
                                    <a href="javascript:;" class="increase">增加批次</a> <a href="javascript:;"  class="remove_batch">废除批次</a><a href="javascript:;" class="import_record" receive-action="1">生成/导入记录</a>
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
                                    <a href="javascript:;" class="increase">增加批次</a> <a href="javascript:;">废除批次</a><a href="javascript:;" class="import_record" receive-action="2">生成/导入记录</a>
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
                        <input  type="radio" name="activation" <#if ($data_list->activation==0)checked="checked"  class="checkbox_actives" </#if> id="act_no" <#if ($data_list->activation==1 && $act == 'edit') onclick="examine_num()" </#if> value="0">否
                        <input  style="z-index: 2;margin-left: 30px" type="radio" name="activation" <#if ($data_list->activation==1)checked="checked" class="checkbox_actives" </#if> id="act_yes" value="1">是
                    </div>
                    <div style="height:20px">
                        <span style="font-size: 13px;line-height: 17px;margin: 0px;    margin-left: 20px;">选择后，请勾选你需要用户填写的信息</span>
                    </div>
                    <div class="activation_cfg clearfix" <#if ($data_list->activation==0 || empty($data_list)) style="display: none" </#if>>
                        <input type="hidden" name="activation_cfg" form="form1" value="{{json_encode($data_list->activation_cfg)!}">
                        {{--<div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="username" <#if ($data_list->activation_cfg && in_array('username',$data_list->activation_cfg)) checked </#if>>会员昵称</div>--!}
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="real_name" <#if ($data_list->activation_cfg && in_array('real_name',$data_list->activation_cfg)) checked </#if>>真实姓名</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="mobile" <#if ($data_list->activation_cfg && in_array('mobile',$data_list->activation_cfg)) checked </#if>>手机号</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="cid" <#if ($data_list->activation_cfg && in_array('cid',$data_list->activation_cfg)) checked </#if>>身份证号码</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="sex" <#if ($data_list->activation_cfg && in_array('sex',$data_list->activation_cfg)) checked </#if>>性别</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="birthday" <#if ($data_list->activation_cfg && in_array('birthday',$data_list->activation_cfg)) checked </#if>>生日</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="marital_status" <#if ($data_list->activation_cfg && in_array('marital_status',$data_list->activation_cfg)) checked </#if>>婚姻状况</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="education" <#if ($data_list->activation_cfg && in_array('education',$data_list->activation_cfg)) checked </#if>>教育程度</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="industry_info" <#if ($data_list->activation_cfg && in_array('industry_info',$data_list->activation_cfg)) checked </#if>>所在行业</div>
                        <div class="fl in_li"><input type="checkbox" name="activation_cfg_box" form="form1" value="address" <#if ($data_list->activation_cfg && in_array('address',$data_list->activation_cfg)) checked </#if>>所在地</div>
                    </div>
                    <div class="activation_cfg"  <#if ($data_list->activation==0 || empty($data_list)) style="display: none" </#if>>
                    <span>激活信息是否需要审核：</span>
                    <div><input type="radio" name="examine" value="0" <#if ($data_list->examine == 0) checked </#if> <#if ($data_list->examine == 1 && $act == "edit") onclick="examine_num()" </#if> form="form1">无需审核 <input type="radio" name="examine" value="1" <#if ($data_list->examine == 1) checked </#if> form="form1" style="margin-left:25px;">需要审核</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="btn_save fix_footer">
    <a href="##">保存</a>
</div>

</form>

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
            <p style="padding-left: 73px;color: #999;">导入的领取码不可与店铺内已有领取码重复</p>
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
<table class="store_modal_clone hide">
    <tr class="store_modal_tr">
        <td></td>
        <td><a href="javascript:void(0)" class="del">删除</a></td>
    </tr>
</table>
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
    var store_list = '${data_list->store_list!}';
	
</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/jquery.bigcolorpicker.min.js"></script>
<script src="/js/admin/create_count_card.js?v=1.0.2"></script>
<script src="/js/admin/card_code.js?v=1.0.0"></script>
<script type="text/javascript">
    var card_id = "${data_list->id!}";
    var card_batch = @json($card_batch) || [];
    var act = "${act!}";
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
            $('.store_use_count').show()            
            $('.store_use_time').show()        
        }
        if($('.store_table tr').length == 1){
            $('.store_table').hide();
        }
        if($('.store_modal tr').length == 1){
            $('.store_modal').hide();
        }
    });
    //允许使用时间
        //初始化
    var s_html = '' ;
    var working_h = '';
    var weekend_h = '';
    if($('#working').prop('checked') == true){
        working_h = $('#working').attr('data-text') + ' ';
    }
    if($('#weekend').prop('checked') == true){
        weekend_h = $('#weekend').attr('data-text') + ' ';
    }
    s_html = working_h + weekend_h;
    if(s_html != ''){
        $('.time-content').show();
        $('.time-content').html(s_html);
    }
    function checkbox_click(obj) {
        if(obj.value == 1){
            if(obj.checked == true){
                working_h = $('#working').attr('data-text') + ' ';
            }else{
                working_h = "";
            }
        }
        if(obj.value == 2){
            if(obj.checked == true){
                weekend_h = $('#weekend').attr('data-text') + ' ';
            }else{
                weekend_h = "";
            }
        }
        s_html = working_h + weekend_h;
        if(s_html != ''){
            $('.time-content').show();
            $('.time-content').html(s_html);
        }
        if(weekend_h == '' && working_h == ''){
            $('.time-content').hide();
        }
    }
//选择全部门店或者部分门店时，左侧门店相关信息的显示
    function radio_choose(obj) {
        if(obj.value == 0){
            $('.store-content').html('全部门店');
            $('input[name="store_list"]').val(0);
            $('.add_store').attr("hidden","true");
            $('.store_use_count').show()
            $('.store_use_time').show()
            $('.store_modal').css('display','none')
            $('.store_table').css('display','none')
        }
        if(obj.value == -1){
            $('.store-content').html('不可在门店使用');
            $('input[name="store_list"]').val(-1);
            $('.add_store').attr("hidden","true");
            $('.store_use_count').hide()
            $('.store_use_time').hide()
            $('.store_modal').css('display','none')
            $('.store_table').css('display','none')
        }
        if(obj.value == 1){
            $('.add_store').removeAttr("hidden");
            $('.store_use_time').show()
            $('.store-content').html('');
            $('.store_use_count').show()
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
    if($("input[name='exchang_goods']").val())
        $(".choose_num").css("display","inline");
</script>
<script type="text/javascript">
    getPowerInfo('main_config','count_card','sub_3','限次会员卡',0);
</script>
