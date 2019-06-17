<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/score_cfg.css?v=1.0.6" type="text/css" />
<style type="text/css">
    .save:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .save:focus{
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
    .pay_fl input[type="checkbox"] {
        opacity: 0;
    }
    .store_pay_fl,
    .shopping_pay_fl,
    .login_pay_fl{
        width: 40px;
        height: 20px;
        background: url(../../image/admin/off_1.png) left top no-repeat;
        background-size: 100% 100%;
        position: relative;
        /*top: 15px;*/
        color: #333;
        font-size: 14px;
        float: left;
        margin-right: 20px;
        margin-bottom: 10px;
    }
    .store_pay_fl label,
    .shopping_pay_fl label,
    .login_pay_fl label{
        width: 40px;
        height: 20px;
    }
    .store_pay_fl input[type='checkbox'],
    .shopping_pay_fl input[type='checkbox'],
    .login_pay_fl input[type='checkbox']{
        opacity: 0;
    }
    .draggable_disabled{
        width: 20px;
        height: 20px;
        position: absolute;
        top: 0px;
        right: 20px;
        cursor: pointer;
    }
    .login_score_cfg{
        display:none;
    }
    .shopping_score_cfg{
        display:none;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/score_cfg.member_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/score_cfg.score_manage")!}</span>
    </div>
    <div class="main-container fix_every_footer">
    <div class="list_nav">
        <ul class="clearfix">
            <li <#if  (empty($request['nav'])) class="actives" </#if>>
                <a href="/admin/user/score/cfg?top_index=5">积分规则设置</a>
            </li>
            <li <#if  ($request['nav'] == 1) class="actives" </#if>>
                <a href="/admin/user/score/add?top_index=5&nav=1">前端展示设置</a>
            </li>
        </ul>
    </div>
    <div class='title_rule'>请在此设置店铺积分管理相关规则：</div>
        <form action="/admin/user/score/cfg" id="form1" method="post" name="formData">
            {{ csrf_field()!}
            <div class="score_rule">
                <div class="text-prompt">
                 <span class='blue_border'></span>    
                {{ trans("admin/score_cfg.score_rule")!}</div>
                <div class="rule-explain">
                    <ul>
                        <li class="clearfix">
                            <span>{{ trans("admin/score_cfg.score_limit_time")!}</span>
                            <div class="fl">
                                <div>
                                    <label for="limit-one">
                                        <input type="radio" name="score_limit" id="limit-one" <#if ($score_limit ==0)checked </#if> value="0"/>
                                        {{ trans("admin/score_cfg.lang_lang")!}
                                    </label>
                                </div>
                                <div>
                                    <label for="limit-two">
                                        <input type="radio" name="score_limit" id="limit-two" <#if ($score_limit ==1) checked </#if> value="1"/>
                                        从获得开始至
                                        <select name="score_year" id="score_year">
                                            <option value="1" <#if ($score_year ==1) selected </#if>>一年后</option>
                                            <option value="2" <#if ($score_year ==2) selected </#if>>两年后</option>
                                            <option value="3" <#if ($score_year ==3) selected </#if>>三年后</option>
                                            <option value="4" <#if ($score_year ==4) selected </#if>>四年后</option>
                                            <option value="5" <#if ($score_year ==5) selected </#if>>五年后</option>
                                        </select>
                                        <select name="score_month"/>
                                            <option value="0">请选择：</option>
                                            <#list ($month as $item)
                                                <option value="${item!}" <#if ($score_month == $item)selected="selected" </#if>>${item!}</option>
                                            </#list>
                                        </select> 月
                                        <select name="score_day"/>
                                            <option value="0">请选择：</option>
                                            <#list ($day1 as $item)
                                                <option class="day_option1" value="${item!}" <#if ($score_day == $item)selected="selected" </#if>>${item!}</option>
                                            </#list>
                                        </select> 日
                                    </label>
                                </div>
								<div style="color: red">例如，设置为“明年的01月01日”，即是指用户今年获得的积分将在明年1月1日 24:00:00失效
								</div>
                                <div>
                                    <label for="limit-three">
                                        <input type="radio" name="score_limit" id="limit-three" <#if ($score_limit == 2) checked </#if> value="2"/>
                                        从获得积分当天起
                                        <input type="number" name="score_limit_number" min="1" value="${score_limit_number!}"/>
                                        <select name="score_period">
                                            <option value="1" <#if  ($score_period == 1) selected </#if>>日</option>
                                            <option value="7" <#if  ($score_period == 7) selected </#if>>周</option>
                                            <option value="30" <#if  ($score_period == 30) selected </#if>>月</option>
                                        </select>
                                        内有效
                                    </label>
                                </div>
                            </div>
                        </li>
                        <li class="clearfix">
                            <span>{{ trans("admin/score_cfg.score_exchange")!}</span>
                            <div class="fl"><div>{{ trans("admin/score_cfg.hun_to_one")!}</div></div>
                        </li>
                        <li class="clearfix" hidden>
                            <span>{{ trans("admin/score_cfg.score_protection")!}</span>
                            <div class="fl">
                                <div>
                                    {{ trans("admin/score_cfg.score_get")!}
                                    <input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="score_protect" value="${score_protect!}"/>
                                    {{ trans("admin/score_cfg.not_use")!}
                                </div>
                               <p>{{ trans("admin/score_cfg.p_explain")!}</p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="score_rule">
                <ul>
                    <li class="clearfix" style="margin-top: 10px; line-height: 50px;">
                        <div style="padding: 0px 25px;">
                            <span>积分支付限制</span>
                            <div style="display: inline-block; margin-left: 10px;">
                                <input type="radio" name="score_pay_limit" value="0" <#if  (!$score_pay_limit) checked </#if> /> 不限制
                                <input type="radio" name="score_pay_limit" value="1" style="margin-left: 10px;" <#if  ($score_pay_limit) checked </#if>/> 自定义
                                不足<input type="number" min="100" name="score_pay_num" value="${score_pay_num!}"/> 积分不可使用积分支付
                                <span style="color: #999; margin-left: 10px;">请填写大于100积分且为100积分的整数倍的数值</span>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="score_rule score_get_rule">
                <div class="text-prompt">
                   <span class='blue_border'></span>  
                    {{ trans("admin/score_cfg.score_get_rule")!}
                    <span>{{ trans("admin/score_cfg.card_first")!}</span>
                </div>
                <div class="rule-explain" style="padding-bottom:0;">
                    <div class="clearfix" style="margin-bottom:20px;">
                        <span class="fl" style="margin-left:30px">购物送积分</span>
                        <div class="shopping_pay_fl" <#if (!$shopping_score) img_id="1" <#else> img_id="0" </#if> style="float: left;top:0;margin-left:13px;">
                        <label>
                            <input type="checkbox" name="shopping_score">
                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" mod_name="购物送积分"/>
                        </label>
                        </div>
                        <span style="color: #999"><strong style="color:#000;margin-right:15px;font-weight:400;"></strong>开关开启，则订单完成后会给用户按照积分获取规则赠送积分，关闭则不赠送</span>
                    </div>
                    <ul>
                        <li class="clearfix shopping_score_cfg" style="margin-left:110px;margin-bottom:20px">
                            <div class="fl">
                                <div>
                                    <label for="get-one" class="buy_score">
                                        <input type="radio" name="score_type" id="get-one" <#if ($score_type==0)checked </#if> value="0" />
                                        <#if ($buy && $shopping_score)
                                            <#list ($buy as $k=>$item)
                                                <#if ($k>0)<div class="add_line"> <#else> <span> </#if>

                                                    {{ trans("admin/score_cfg.shopping_full")!}
                                                    <input type="text" name="buy[]" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${item->set_val!}"/>
                                                    {{ trans("admin/score_cfg.give")!}
                                                    <input type="text" name="score[]" onkeyup="value=value.replace(/[^\d.]/g,'')"  value="${item->set_val2!}"/>
                                                    {{ trans("admin/score_cfg.score")!}
                                                    <#if ($k==0) <span class="get_add"><img src="http://${image_domain!}/image/admin/sign_jia.png" alt="" /></span> <#else> <span class="get_del"><img src="http://${image_domain!}/image/admin/sign_del.png" alt="" /></span> </#if>
                                                    <#if ($k>0)</div> <#else> </span> </#if>
                                            </#list>
                                        <#else>
                                            <span>
                                                {{ trans("admin/score_cfg.shopping_full")!}
                                                <input type="text" name="buy[]" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                                {{ trans("admin/score_cfg.give")!}
                                                <input type="text" name="score[]" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                                {{ trans("admin/score_cfg.score")!}
                                                <span class="get_add">
                                                    <img src="http://${image_domain!}/image/admin/sign_jia.png" alt="" />
                                                </span>
                                            </span>
                                        </#if>
                                    </label>
                                    <div class="add_modal hide">
                                        <div class="add_line">
                                            {{ trans("admin/score_cfg.shopping_full")!}
                                            <input type="text"  name="buy[]" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                            {{ trans("admin/score_cfg.give")!}
                                            <input type="text" name="score[]" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                            {{ trans("admin/score_cfg.score")!}
                                            <span class="get_del">
                                                <img src="http://${image_domain!}/image/admin/sign_del.png" alt="" />
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div style="margin-left:-14px">
                                    <label for="get-two">
                                        <input type="radio" name="score_type" id="get-two" <#if ($shopping_score && $score_type==1)checked </#if>  value="1"/>
                                        {{ trans("admin/score_cfg.shopping_one_full")!}
                                        <input type="text" name="buy_each" onkeyup="value=value.replace(/[^\d.]/g,'')" <#if ($shopping_score && $score_type==1) value="${buy_each->set_val!}" <#else> value="" </#if>/>
                                        {{ trans("admin/score_cfg.give")!}
                                        <input type="text" name="score_each" onkeyup="value=value.replace(/[^\d.]/g,'')" <#if ($shopping_score && $score_type==1) value="${buy_each->set_val2!}" <#else> value="" </#if>/>
                                        {{ trans("admin/score_cfg.score")!}
                                    </label>
                                </div>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div class="rule-explain" style=" height:50px;padding-left:0;padding-top:0;">
                                <span class="fl">门店买单送积分</span>
                                <div class="store_pay_fl" <#if (!$store_score) img_id="0" <#else> img_id="1" </#if> style="margin-left:15px;">
                                <label>
                                <input type="checkbox" name="store_score">
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" mod_name="买单送积分"/>
                                </label>
                                </div>
                                <span style="color: #999"><strong style="color:#000;margin-right:15px;font-weight:400;"></strong>开关开启，则会在用户门店买单后按照购物送积分规则给用户赠送积分，关闭则不赠送</span>
                            </div>
                        </li>
                        <li class="clearfix">
                             <div class="clearfix" style="padding:0 0 20px 0;">
                                <span class="fl" style="margin-left:30px">登录送积分</span>
                                <div class="login_pay_fl" <#if (!$login_score) img_id="1" <#else> img_id="0" </#if> style="float: left;top:0;margin-left:13px;">
                                <label>
                                    <input type="checkbox" name="login_score">
                                    <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" mod_name="登录送积分"/>
                                </label>
                                 </div>  
                                 <span style="color: #999"><strong style="color:#000;margin-right:15px;font-weight:400;"></strong>开关开启，则用户登录后会给用户按照积分获取规则赠送积分，关闭则不赠送</span>
                            </div>
                        </li>
                        <li class="clearfix login_score_cfg" style="margin-left:110px;margin-bottom:20px;">
                            <div class="fl">
                                <div>
                                    {{ trans("admin/score_cfg.login_give")!}
                                    <input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="score_login" <#if ($login_score) value="${score_login!}" <#else> value="" </#if>/>
                                    {{ trans("admin/score_cfg.score")!}
                                    <span style="color: #f66">每日仅首次登录赠送积分</span>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="rule-explain" style="padding-top:0">
                    <span class="fl" style="margin-left:30px;">签到送积分</span>
                    <div class="pay_fl" img_id="${signInScore['enable'] == 1 ? 0 : 1!}" style="float: left;top:0;margin-left:13px;">
                        <label>
                            <input type="checkbox" name="sign_in_score">
                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" mod_name="签到送积分"/>
                        </label>
                    </div>
                    <span style="color: #999"><strong style="color:#000;margin-right:15px;font-weight:400;" ></strong>开关开启，则系统开启签到送积分功能</span>
                </div>
                <div class="rule-explain sign_explain" style="display: none;padding-top:0;margin-left:110px;">
                    <ul>
                        <li class="clearfix">
                            <div class="fl sign_score">
                                <#list  ($signInScore['score'] as $item)
                                <div class="sign_score_span">
                                    连续签到
                                    <span class="sign_days">${loop->index + 1!}</span>
                                    天，送
                                    <input type="text" name="sign_score[]" value="${item!}" />
                                    积分
                                    <#if  ($loop->first)
                                    <span class="get_sign_add">
                                        <img src="http://${image_domain!}/image/admin/sign_jia.png" alt="">
                                    </span>
                                    </#if>
                                    <#if  ($loop->last && $loop->last != $loop->first)
                                    <span class="get_sign_del">
                                        <img src="http://${image_domain!}/image/admin/sign_del.png" alt="">
                                    </span>
                                    </#if>
                                </div>
                                </#list>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="mp_footer fix_footer">
                <button  class="save">保存</button>
            </div>
        </form>
    </div>
</div>
<div class="hide sign_score_clone">
    <div class="sign_score_span">
        连续签到
        <span class="sign_days">1</span>
        天，送
        <input type="text" name="sign_score[]" value="1" />
        积分
        <span class="get_sign_del">
            <img src="http://${image_domain!}/image/admin/sign_del.png" alt="">
        </span>
    </div>
</div>
<select id="day1" hidden/>
    <option value="0">请选择：</option>
    <#list ($day1 as $item)
        <option value="${item!}" >${item!}</option>
    </#list>
</select>
<select id="day2" hidden/>
    <option value="0">请选择：</option>
    <#list ($day2 as $item)
        <option value="${item!}" >${item!}</option>
    </#list>
</select>
<select id="day3" hidden/>
    <option value="0">请选择：</option>
    <#list ($day3 as $item)
        <option value="${item!}">${item!}</option>
    </#list>
</select>
<select id="day4" hidden/>
    <option value="0">请选择：</option>
    <#list ($day4 as $item)
    <option value="${item!}" >${item!}</option>
    </#list>
</select>
<script>
    //版本控制
    var sign_score = '${version['sign_score']!}';
    var pay_score = '${version['pay_score']!}';
    if(sign_score == -1){
        $("input[name='sign_in_score']").attr("disabled","true");
        $("input[name='sign_in_score']").next().removeClass("draggable");
        $("input[name='sign_in_score']").next().addClass("draggable_disabled");
    }
    if(pay_score == -1){
        $("input[name='store_score']").attr("disabled","true");
        $("input[name='store_score']").next().removeClass("draggable");
        $("input[name='store_score']").next().addClass("draggable_disabled");
    }
    $(".draggable_disabled").click(function () {
        util.systemNotice(1,'',$(this).attr("mod_name"));
    });
    $('.get_add').click(function(){
        var obj = $(".add_modal").children().clone();
        $(".buy_score").append(obj);
    });
    $('.get_sign_add').click(function () {
//        if($('.sign_explain').find('input[name="sign_score[]"]').last().val() == ''){
//            util.mobile_alert('请填写赠送积分')
//            return false;
//        }
        var sign_day = $('.sign_score').find('.sign_score_span:last-child').find('.sign_days').html();
        //console.log(sign_day);
        $('.sign_score_clone').find('.sign_days').html(Number(sign_day)+1);
        var obj = $('.sign_score_clone').find('.sign_score_span').clone();
        if ($('.sign_score .sign_score_span').length > 1) {
            $('.sign_score .sign_score_span:last-of-type .get_sign_del').remove();
        }
        $('.sign_score').append(obj);
    });
    $('body').on('click','.get_sign_del',function () {
        $(this).parent().remove();
        if ($('.sign_score .sign_score_span').length > 1) {
            $('.sign_score .sign_score_span:last-of-type').append(
                `<span class="get_sign_del">
                    <img src="http://${image_domain!}/image/admin/sign_del.png" alt="">
                </span>`
            );
        }
    });
    $('.sign_explain').on('blur','input[name="sign_score[]"]',function () {
        if($(this).val() == ''){
            $(this).val('0');
        }
    });
    $('select[name="score_month"]').change(function(){
        if($("#limit-two").is(":checked")){
            var arr1 = [1,3,5,7,8,10,12];
            var arr2 = [4,6,9,11];
            var arr3 = [2];
            $("select[name='score_day']").html("");
            if($.inArray(parseInt($("select[name='score_month'] option:selected").text()),arr1)>=0){
                $("select[name='score_day']").html($("#day1").html());
            }else if($.inArray(parseInt($("select[name='score_month'] option:selected").text()),arr2) >=0){
                $("select[name='score_day']").html($("#day2").html());
            }else if($.inArray(parseInt($("select[name='score_month'] option:selected").text()),arr3) >=0){
                var mydate = new Date();
                var year = "" + mydate.getFullYear();
                if((parseInt($("select[name='score_year'] option:selected").val())+parseInt(year)) % 4 >0){
                    $("select[name='score_day']").html($("#day4").html());
                }else{
                    $("select[name='score_day']").html($("#day3").html());
                }
            }
        }
    });
    $('.save').click(function(){
        var flag = 0;
        if($("input[name='score_type']").val() == 'on'){
            if($("input[name='score_type']:checked").val() == 0) {
                $("input[name='buy[]']").each(function (i, e) {
                    if ($(this).val() != '') {
                        if ($("input[name='score[]']").eq(i).val() == '') {
                            flag++;
                            util.mobile_alert('请填写赠送积分');
                            return false;
                        }
                    }
                });
            }
            else{
                if ($("input[name='buy_each']").val() == '') {
                    flag++;
                    util.mobile_alert('请填写每满金额');
                    return false;
                }
                if ($("input[name='score_each']").val() == '') {
                    flag++;
                    util.mobile_alert('请填写赠送积分');
                    return false;
                }
            }
        }

        if($("#limit-two").is(":checked")){
            var limit_month = $("select[name='score_month'] option:selected").text();
            var limit_day = $("select[name='score_day'] option:selected").text();
            if(limit_month == "请选择："){
                util.mobile_alert('请选择有效月份');
                return false;
            }
            if(limit_day == "请选择：") {
                util.mobile_alert('请选择有效日期');
                return false;
            }
        }
        if($("#limit-three").is(":checked")) {
            var score_limit_number = parseInt($('input[name="score_limit_number"]').val());
            var score_period = parseInt($("select[name='score_day']").val());
            if (isNaN(score_limit_number) || score_limit_number <= 0
                || isNaN(score_period) || score_period <= 0) {
                util.mobile_alert('请配置正确的积分有效期');
                return false;
            }
        }
        if (parseInt($('input[name="score_pay_limit"]:checked').val()) == 1) {
            var score_pay_num = parseInt($('input[name="score_pay_num"]').val());
            if (isNaN(score_pay_num) || score_pay_num < 100 || score_pay_num % 100 != 0) {
                util.mobile_alert('积分支付限制应大于等于100，且为100的整数倍');
                return false;
            }
        }
        if(flag == 0) {
            layer.ready(function () {
                layer.msg('保存成功', {time: 2000},function () {
                    $("#form1").submit();
                });
            });
        }
    });
    $("body").on('click','.get_del',function(){
        $(this).parent().remove();
    });
    $(".draggable").click(function(){
        var img_id = $(this).parent().parent().attr("img_id");
        if(img_id == 0 ){
            $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
            $(this).animate({right:"0px"});
            $(this).prev().prop('checked',false);
            if($(this).parent().parent().attr('class') == 'store_pay_fl'){

            }
            if($(this).parent().parent().attr('class') == 'pay_fl'){
                $('.sign_explain').show();
            }
        } else if(img_id == 1){
            $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
            $(this).animate({right:"20px"});
            $(this).prev().prop('checked', true);
            if($(this).parent().parent().attr('class') == 'store_pay_fl'){

            }
            if($(this).parent().parent().attr('class') == 'pay_fl'){
                $('.sign_explain').hide();
            }
        }

    });
    $('.draggable').trigger('click');
    $('input[name="login_score"]').click(function(){
        check_switchStatus();
    })
    $('input[name="shopping_score"]').click(function(){
        check_switchStatus();
    })
    function check_switchStatus(){
        let shopping_score = $('input[name="shopping_score"]').prop('checked') ? 'block' : 'none';
        let login_score = $('input[name="login_score"]').prop('checked') ? 'block' : 'none';
        $('.shopping_score_cfg').css("display",shopping_score);
        $('.login_score_cfg').css("display",login_score);
    }
    
    function check_checkedStatus(){
        $('input[type="checkbox"]').each(function(){
            let checkStatus = $(this).prop('checked');
            $(this).parent().parent().next().children('strong').text(checkStatus ? "已开启" : "已关闭");
        })
        $('input[type="checkbox"]').click(function(){
        let checkStatus = $(this).prop('checked');
        $(this).parent().parent().next().children('strong').text(checkStatus ? "已开启" : "已关闭");
            if(checkStatus){
                $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
                $(this).next().animate({right:"0px"});
                if($(this).parent().parent().attr('class') == 'pay_fl'){
                    $('.sign_explain').show();
                }
            } else {
                $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
                $(this).next().animate({right:"20px"});
                if($(this).parent().parent().attr('class') == 'pay_fl'){
                    $('.sign_explain').hide();
                }
            }
        })
    }
    check_checkedStatus();
    check_switchStatus();
</script>
<#include "/admin/footer.ftl">
    <script>
        $(".fix_footer").outerWidth($('.fix_every_footer').width());
    </script>