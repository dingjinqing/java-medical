<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/jquery.bigcolorpicker.css" />
<link href="/css/admin/membership_card.css?v=1.2.7" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/create_grade_card.css?v=1.0.1" type="text/css" />
<div class="title">
    <span>会员管理 / </span><span style="color: #666;">等级会员卡</span>
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
        </div>
    </div>
    <div class="mc_right">
        <form action="" method="post" id="form2">
            {{ csrf_field()!}
            <input type="hidden" name="bg_img" id="bg_img_path" value="${data_list->bg_img!}">
            <input type="hidden" name="buy_score" value="${data_list->bug_score!}">
            <input type="hidden" name="id" value="${data_list->id!}">
            <input type="hidden" name="card_type" value="${data_list->card_type or 2!}">
            <input type="hidden" name="grade_condition" value="${data_list->grade_condition!}">
            <input type="hidden" name="act">
            <div class="grade_div">
                <div class="grade_div_title">基础设置</div>
                <div>
                    <div>本卡是否启用：</div>
                    <div>
                        <div style='float:left;'>
                            <input type="radio" name="flag" value="1" <#if ($data_list->flag == 1) checked </#if>/>启用
                        </div>
                        <div style='float:left;margin-left:25px;'>
                            <input type="radio" name="flag" value="2" <#if ($data_list->flag == 2 || !$data_list->flag) checked </#if> />停用
                        </div>
                    </div>
                </div>
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
                        <div style="margin-bottom:0;">
                            <input  type="checkbox" name="power" value="0" class="checkbox_prev power_discount" <#if (($data_list->discount != "" ) || $data_list->discount == '' && empty($act)) checked </#if> onclick="power1(this)" />
                            会员折扣
                            <input  type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="discount" value="${data_list->discount!}">折
                        </div>
                        <p class="count_prompt">若商品设置会员价，则该等级会员卡折扣和会员价共享，优先计算会员价</p>
                        <div>
                            <input  type="checkbox" name="power" value="1" class="checkbox_prev power_score" <#if ($data_list->buy_score_flag) checked </#if> onclick="power2(this)" />积分获取&nbsp;&nbsp;&nbsp;
                            开卡赠送
                            <input  type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="sorce" value="${data_list->sorce!}"  />积分
                        </div>
                        <div class="first_manzeng" style="margin-left: 50px;">
                            <input type="radio" name="offset" id="once_offset" <#if ($data_list->offset==0) checked="checked" class="checkbox_actives" </#if> value="0" onclick="score_rule(this)" />
                            <#list  ($data_list->buy_score as $item)
                                <#if  ($loop->last)
                                <#else>
                                    <#if  ($loop->first)
                                        购物满<input  type="text" name="goods_moneys[]" value="${item->money!}" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 送
                                        <input  type="text" name="get_scores[]" value="${item->score!}" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 积分
                                        <a href="##" class="btn_add_lines"><img src="http://${image_domain!}/image/admin/sign_jia.png" alt=""></a>
                                        <#elseif>(!$loop->last)
                                        <div class="add_lines">
                                        购物满<input  type="text" name="goods_moneys[]" value="${item->money!}"  onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 送
                                        <input type="text" name="get_scores[]" value="${item->score!}" onkeyup="value=value.replace(/[^\d.]/g,'')" onblur="score_rule('#once_offset')" /> 积分
                                        <a href="##" class="del_lines del_score_lines"><img src="http://${image_domain!}/image/admin/sign_del.png" alt=""> </a>
                                        </div>
                                    </#if>
                                </#if>
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
                        <div style="height:auto !important;">
                            <input type="checkbox" name="pay_own_good" <#if ($data_list->pay_own_good == 1) checked </#if> style="position:unset;">会员专享商品
                            <span style="color:#666;margin-left:10px;">选择仅供持有此会员卡用户购买的商品</span>
                            <p style="margin-left: 16px;color: red;">注：持有高等级会员卡的用户可以购买较低等级会员卡专享商品</p>
                            <div class="choose_box_2 clearfix" style="height:auto !important;">
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
                                <div style="margin-bottom:0;">
                                    <div class="add_cate" style="display: inline-block;">
                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                        添加平台分类
                                    </div>
                                    <span class="choose_num">已选择分类：<strong>0</strong>个分类</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="use_notice">
                    <div><span class="must" style="display: none">*</span>使用须知：</div>
                    <div><textarea name="desc" id="" cols="30" rows="10">${data_list->desc!}</textarea></div>
                </div>
                <div class="mobiles">
                    <div>联系电话：</div>
                    <div><input type="text" onkeyup="value=value.replace(/[^\d\-]/g,'')" name="mobile" value="${data_list->mobile!}"></div>
                </div>
            </div>
            <div class="grade_div">
                <div class="grade_div_title">升级设置</div>
                
                <div>
                    <div><span class="must">*</span>升级条件:</div>
                    <div class="grade_set">
                        <p class="count_prompt2" style="margin-top: 5px;">低等级用户满足升级条件会自动升级为高等级卡</p>
                        <div style="margin-bottom:0;">
                            累积积分达到<input type="text" name="grade_score" value="{{json_decode($data_list->grade_condition)->grade_score!}" />分
                        </div>
                        <div style="margin:0;">
                            或&nbsp;
                        </div>
                        <div>
                            累积消费总额达到<input type="text" name="grade_money" value="{{json_decode($data_list->grade_condition)->grade_money!}" />元
                            <span style="color: #999;font-size: 13px;">仅包含微信、余额支付</span>

                        </div>
                    </div>
                </div>
                <div>
                    <div><span class="must">*</span>会员卡等级:</div>
                    <div>
                        <select name="grade" id="">
                            <option value=""  selected >请选择会员卡等级</option>
                            <option value="v1" <#if ($data_list->grade == 'v1') selected </#if> <#if ($grade_list && in_array('v1',$grade_list) && $data_list->grade!='v1') disabled </#if>>v1</option>
                            <option value="v2" <#if ($data_list->grade == 'v2') selected </#if> <#if ($grade_list && in_array('v2',$grade_list) && $data_list->grade!='v2') disabled </#if>>v2</option>
                            <option value="v3" <#if ($data_list->grade == 'v3') selected </#if> <#if ($grade_list && in_array('v3',$grade_list) && $data_list->grade!='v3') disabled </#if>>v3</option>
                            <option value="v4" <#if ($data_list->grade == 'v4') selected </#if> <#if ($grade_list && in_array('v4',$grade_list) && $data_list->grade!='v4') disabled </#if>>v4</option>
                            <option value="v5" <#if ($data_list->grade == 'v5') selected </#if> <#if ($grade_list && in_array('v5',$grade_list) && $data_list->grade!='v5') disabled </#if>>v5</option>
                            <option value="v6" <#if ($data_list->grade == 'v6') selected </#if> <#if ($grade_list && in_array('v6',$grade_list) && $data_list->grade!='v6') disabled </#if>>v6</option>
                            <option value="v7" <#if ($data_list->grade == 'v7') selected </#if> <#if ($grade_list && in_array('v7',$grade_list) && $data_list->grade!='v7') disabled </#if>>v7</option>
                            <option value="v8" <#if ($data_list->grade == 'v8') selected </#if> <#if ($grade_list && in_array('v8',$grade_list) && $data_list->grade!='v8') disabled </#if>>v8</option>
                            <option value="v9" <#if ($data_list->grade == 'v9') selected </#if> <#if ($grade_list && in_array('v9',$grade_list) && $data_list->grade!='v9') disabled </#if>>v9</option>
                        </select>
                        <p class="count_prompt2">数字越大等级越高，当会员满足相应条件时会自动发放对应等级的会员卡</p>
                    </div>

                </div>
            </div>
    </div>
    <div class="mc_right_bottom" style="margin-top:-10px;margin-left:320px">
        <div class="right_title">领取设置</div>
        <div class="ifactive">
            <div><span class="must">*</span>是否需要激活：</div>
            <div>
                <div>
                    <input  type="radio" name="activation" <#if ($data_list->activation==0)checked="checked"  class="checkbox_actives" </#if> id="act_no" value="0" <#if ($data_list->activation==1 && $act == 'edit') onclick="examine_num()" </#if> form="form2">否
                    <input  style="z-index: 2 ;margin-left: 30px" type="radio" name="activation" <#if ($data_list->activation==1)checked="checked" class="checkbox_actives" </#if> id="act_yes" value="1" form="form2">是
                </div>
                <div style="height:20px">
                    <span style="font-size: 13px;line-height: 17px;margin: 0px;    margin-left: 20px;">选择后，请勾选你需要用户填写的信息</span>
                </div>
                <div class="activation_cfg clearfix" <#if ($data_list->activation==0 || empty($data_list)) style="display: none" </#if>>
                    <input type="hidden" form="form2" name="activation_cfg" value="{{json_encode($data_list->activation_cfg)!}">
                    {{--<div class="fl in_li"><input form="form2" type="checkbox" name="activation_cfg_box" value="username" <#if (in_array('username',$data_list->activation_cfg)) checked </#if>>会员昵称</div>--!}   
                    <div class="fl in_li"><input type="checkbox" form="form2" name="activation_cfg_box" value="real_name" <#if (in_array('real_name',$data_list->activation_cfg)) checked </#if>>真实姓名</div>
                    <div class="fl in_li"><input form="form2" type="checkbox" name="activation_cfg_box" value="mobile" <#if (in_array('mobile',$data_list->activation_cfg)) checked </#if>>手机号</div>
                    <div class="fl in_li"><input type="checkbox"  form="form2" name="activation_cfg_box" value="cid" <#if (in_array('cid',$data_list->activation_cfg)) checked </#if>>身份证号码</div>
                    <div class="fl in_li"><input type="checkbox" form="form2" name="activation_cfg_box" value="sex" <#if (in_array('sex',$data_list->activation_cfg)) checked </#if>>性别</div>
                    <div class="fl in_li"><input type="checkbox"  form="form2" name="activation_cfg_box" value="birthday" <#if (in_array('birthday',$data_list->activation_cfg)) checked </#if>>生日</div>
                    <div class="fl in_li"><input type="checkbox" form="form2" name="activation_cfg_box" value="marital_status" <#if (in_array('marital_status',$data_list->activation_cfg)) checked </#if>>婚姻状况</div>
                    <div class="fl in_li"><input type="checkbox"  form="form2" name="activation_cfg_box" value="education" <#if (in_array('education',$data_list->activation_cfg)) checked </#if>>教育程度</div>
                    <div class="fl in_li"><input type="checkbox"  form="form2" name="activation_cfg_box" value="industry_info" <#if (in_array('industry_info',$data_list->activation_cfg)) checked </#if>>所在行业</div>
                    <div class="fl in_li"><input type="checkbox"  form="form2" name="activation_cfg_box" value="address" <#if (in_array('address',$data_list->activation_cfg)) checked </#if>>所在地</div>
                </div>
                <div class="activation_cfg"  <#if ($data_list->activation==0 || empty($data_list)) style="display: none" </#if>>
                    <span>激活信息是否需要审核：</span>
                    <div><input type="radio" name="examine" value="0" <#if ($data_list->examine == 0) checked </#if>  <#if ($data_list->examine == 1 && $act == "edit") onclick="examine_num()" </#if> form="form2">无需审核 <input type="radio" name="examine" value="1" <#if ($data_list->examine == 1) checked </#if> style="margin-left:25px;" form="form2">需要审核</div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="btn_save fix_footer">
    <a href="##" class="save" is_get = "save">保存</a>
    <a href="##" class="save" is_get = "save_get">保存并发卡</a>
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
<div id="set_stop_use">
    <div class="stop_prompt">
        共<span class="use_count">98</span>位会员拥有该卡
        {{--停用后不可再启用，您可选择停用该卡后，拥有该卡的用户批量更换为其他等级卡，也可直接停用，若直接停用本卡则已经领取的等级用户卡列表也会停用该等级卡，不在享受等级权限--!}
    </div>
    <div class="grade_stop_radio">
        <label for="direct_stop">
            <input type="radio" name="grade_flag" value="0" id="direct_stop" checked />
            直接停用
        </label>
        <label for="change_stop">
            <input type="radio" name="grade_flag" value="1" id="change_stop" />
            停用后置换为其他等级卡
        </label>
    </div>
    <div class="grade_stop_select1 grade_no_content">
        停用后拥有该卡用户默认设置为:
        <select name="new_card_id" id="new_card_id">
            <option value="">请选择会员卡</option>
            <#list ($grade_card as $item)
                <option value="${item->id!}" grade="${item->grade!}">${item->card_name!}</option>
            </#list>
        </select>
        <div>直接停用本卡，拥有本卡的用户批量更换为设置的新等级卡</div>
    </div>
    <div class="grade_stop_select0 grade_no_content">
        直接停用本卡，则已经领取的等级用户也会停用该等级卡，不再享受等级会员权限
    </div>
</div>
<script type="text/javascript">
    var hasSaved = true;
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    var flag = "${data_list->flag!}";
    var use_count = "${data_list->use_count!}";
    var card_id = "${data_list->id!}";
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


    //是否选折扣
    function power1(obj) {
        if($(obj).is(":checked")){
            if($("input[name='discount']").val() != ''){
                $(".power_content").css("display","block");
                $(".power_content").html("会员折扣"+$("input[name='discount']").val()+"折");
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
<script src="/js/admin/create_grade_card.js?v=1.1.10"></script>
<script type="text/javascript">
    power1('.power_discount');
    power2('.power_score');
    getPowerInfo('main_config','grade_card','sub_3','等级会员卡',0);
</script>
