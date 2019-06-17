<#include ("system.header")
<link rel="stylesheet" href="/css/system/pin_integration.css?v=1.0.2" type="text/css" />

<style type="text/css">
    body{
        padding-bottom: 40px;
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
    input[name='page']:focus ,input[type="text"]:focus{
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging tr td,.tb_paging tr td a{
        color: #333;
        font-size: 14px;
    }
    .tb_paging{
        border: 0 !important;
        margin-top: 10px;
        margin-bottom: 5px;
        float: none;
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
    input[readonly] {
        background: #EBEBE4;
    }
    .pin_integration_footer button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .pin_integration button:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .pin_integration_footer button:active, .pin_integration_footer button:visited{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .limit_amount{
        margin-right: 5px;
    }
</style>
<div style="min-width: 1090px;">
    {{--<div class="title">--!}
        {{--<span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>--!}
        {{--<span style="color: #666;"><a href="/admin/market/integration/list">组团瓜分积分</a></span>--!}
    {{--</div>--!}
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/system/market/integration/list?nav=0&top_index=4">全部瓜分积分活动</a>
                </li>
                <li>
                    <a href="/system/market/integration/list?nav=1&top_index=4">进行中</a>
                </li>
                <li>
                    <a href="/system/market/integration/list?nav=2&top_index=4">未开始</a>
                </li>
                <li>
                    <a href="/system/market/integration/list?nav=3&top_index=4">已过期</a>
                </li>
                <li>
                    <a href="/system/market/integration/list?nav=4&top_index=4">已停用</a>
                </li>
                <li class="active">
                    <#if ($id)
                        <a href="/system/market/integration/list?top_index=4&id=${id!}">编辑瓜分积分活动</a>
                    <#else>
                        <a href="/system/market/integration/list?top_index=4">添加瓜分积分活动</a>
                    </#if>
                </li>
            </ul>
        </div>
        <div class="return-pin-integration-box" style="padding-top: 20px;">
            <form name="formData" id="form1" method="post" action="/system/market/integration/add?top_index=4">
                {{ csrf_field()!}
                <input type="hidden" name="id" value="${_GET['id']!}"/>
                <table class="tb-pin-integration">
                    <tbody>
                    <tr style="height: 50px;">
                        <td style="width: 100px ;">
                            <span class="tb-full-left" ><strong>*</strong>活动名称：</span>
                        </td>
                        <td>
                            <input type="text" class="name" placeholder="请填写活动名称" name="name" value="${basicData->name!}" maxlength="20">
                        </td>

                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" ><strong>*</strong>宣传语：</span>
                        </td>
                        <td style="position: relative">
                            <input type="text" class="xuanchuan" name="advertise" value="${basicData->advertise or '积分购物可抵现金'!}" maxlength="10"
                                   <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s")) readonly </#if>;
                            />
                            <a href="javascript:;" class="show_eg">查看示例
                                <div class="hover_show">
                                    <img src="http://${image_domain!}/image/system/new_preview_image/pin_integration.jpg" alt="" style="height: 500px">
                                </div>
                            </a>
                            <span class="prompt">限制10个字以内</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>有效期：</span>
                        </td>
                        <td>
                                    <span>
                                        <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s"))
                                            <input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time"
                                                   id="startTime" readonly> 至
                                            <input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time"
                                                   id="endTime" readonly>
                                        <#else>
                                            <input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time"
                                                   id="startTime" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                                            <input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time"
                                                   id="endTime" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                                        </#if>
                                    </span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" ><strong>*</strong>瓜分积分总数：</span>
                        </td>
                        <td>
                            <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s"))
                            <input type="text" class="inte_total" placeholder="" name="inte_total" value="${basicData->inte_total!}" min="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" readonly> 积分
                            <span class="prompt" >0表示不限制数量,修改总量时只能增加,不能减少,请谨慎设置</span>
                            <#else>
                            <input type="text" class="inte_total" placeholder="" name="inte_total" value="${basicData->inte_total!}" min="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/> 积分
                            <span class="prompt" >0表示不限制数量,修改总量时只能增加,不能减少,请谨慎设置</span>
                            </#if>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" ><strong>*</strong>单团瓜分内容：</span>
                        </td>
                        <td>
                            <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s"))
                            <input type="text" class="limit_amount" placeholder="" name="limit_amount" value="${basicData->limit_amount!}" min="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" readonly>人，瓜分
                            <input type="text" class="inte_group" placeholder="" name="inte_group" value="${basicData->inte_group!}" min="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"readonly> 积分
                            <#else>
                            <input type="text" class="limit_amount" placeholder="" name="limit_amount" value="${basicData->limit_amount!}" min="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>人，瓜分
                            <input type="text" class="inte_group" placeholder="" name="inte_group" value="${basicData->inte_group!}" min="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/> 积分
                            </#if>
                            <span class="prompt">成团人数需≥2人且≤20人,瓜分积分数量需大于成团人数</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" ><strong>*</strong>参团限制：</span>
                        </td>
                        <td>
                            每人最多参加
                            <input type="text" class="join_limit" placeholder="" name="join_limit" value="${basicData->join_limit or 1!}"
                                   <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s")) readonly </#if>; min="0" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"
                            /> 次新团
                            <span class="prompt">默认为1,0表示不限制数量。仅限制参与其他用户所开的团的数量。</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px;vertical-align: top !important;padding-top: 12px;">
                            <span class="tb-full-left" ><strong>*</strong>瓜分方式：</span>
                        </td>
                        <td>
                            <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s"))
                            <label for="pin1" class="pinn">
                                <input type="radio" name="divide_type" id="pin1" value="0" <#if ($basicData->divide_type==0) checked </#if>  disabled>按邀请好友数量瓜分
                                <span class="prompt">(邀请好友数量越多获得积分越多)</span>
                            </label>
                            <label for="pin2" class="pinn">
                                <input type="radio" name="divide_type" id="pin2" value="1" <#if ($basicData->divide_type==1) checked </#if> disabled>好友均分
                                <span class="prompt">(每个人获得积分数量相同)</span>
                            </label>
                            <label for="pin3" class="pinn">
                                <input type="radio" name="divide_type" id="pin3" value="2" <#if ($basicData->divide_type==2) checked </#if> disabled>随机瓜分
                                <span class="prompt">(每个人获得随机数量积分)</span>
                            </label>
                            <#else>
                            <label for="pin1" class="pinn">
                                <input type="radio" name="divide_type" id="pin1" value="0" <#if ($basicData->divide_type==0) checked </#if>>按邀请好友数量瓜分
                                <span class="prompt">(邀请好友数量越多获得积分越多)</span>
                            </label>
                            <label for="pin2" class="pinn">
                                <input type="radio" name="divide_type" id="pin2" value="1" <#if ($basicData->divide_type==1) checked </#if>>好友均分
                                <span class="prompt">(每个人获得积分数量相同)</span>
                            </label>
                            <label for="pin3" class="pinn">
                                <input type="radio" name="divide_type" id="pin3" value="2" <#if ($basicData->divide_type==2) checked </#if>>随机瓜分
                                <span class="prompt">(每个人获得随机数量积分)</span>
                            </label>
                            </#if>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px;vertical-align: top !important;padding-top: 10px;">
                            <span class="tb-full-left" ><strong>*</strong>瓜分限制：</span>
                        </td>
                        <td>
                            <p>用户开团24小时后,拼团未满员是否可以瓜分积分</p>
                            <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s"))
                            <label for="yes" style="margin-right: 20px">
                                <input type="radio" name="is_day_divide" id="yes" value="1" <#if ($basicData->is_day_divide==1) checked </#if> disabled>是
                            </label>
                            <label for="no" >
                                <input type="radio" name="is_day_divide" id="no" value="0" <#if ($basicData->is_day_divide==0) checked </#if> disabled>否
                            </label>
                            <#else>
                            <label for="yes" style="margin-right: 20px">
                                <input type="radio" name="is_day_divide" id="yes" value="1" <#if ($basicData->is_day_divide==1) checked </#if>>是
                            </label>
                            <label for="no" >
                                <input type="radio" name="is_day_divide" id="no" value="0" <#if ($basicData->is_day_divide==0) checked </#if>>否
                            </label>
                            </#if>
                        </td>
                    </tr>
                    </tbody>
                </table>

            </form>

        </div>
        <div class="pin_integration_footer fix_footer">
            <button type="submit" class="save" onclick="if(save() === false) return false;">保存</button>
        </div>
    </div>
</div>

<#include ('system.preview_common')
<script>
    hasSaved = true;
    var edit = '${edit!}';

    $('.yl,.yl_img').mouseover(function () {
        $(".yl_img").show();
    })
    $('.yl,.yl_img').mouseleave(function () {
        $(".yl_img").hide();
    })

    $(".inte_total").blur(function () {
        if($(this).val() < parseInt($(this).attr("value"))){
            util.mobile_alert('瓜分积分总数不可减少');
            $(this).focus();
        }
    })

    $(".xuanchuan").blur(function () {
        if($(this).val() == ''){
            util.mobile_alert('宣传语不能为空');
            $(this).focus();
            return false;
        }
    })

    //失去焦点判断
    $('.limit_amount').blur(function(){
        var num = $('input[name = "limit_amount"]').val();
        if(!(/(^[1-9]\d*$)/.test(num)) && num != 0){
            util.mobile_alert('瓜分人数请输入正整数');
            $(this).focus();
            return false;
        }
        if(parseInt($('input[name="limit_amount"]').val()) < 2) {
            util.mobile_alert('成团人数应大于等于2人');
            $(this).focus();
            return false;
        }
        if(parseInt($('input[name="limit_amount"]').val()) > 20 ){
            util.mobile_alert('成团人数应小于等于20人');
            $(this).focus();
            return false;
        }
    });
    $('.join_limit').blur(function(){
        var num = $('input[name = "join_limit"]').val();
        if(!(/(^[1-9]\d*$)/.test(num)) && num != 0){
            util.mobile_alert('参团限制请输入正整数');
            $(this).focus();
            return false;
        }
    });

    $('.inte_group').blur(function(){
        if(parseInt($('input[name="inte_group"]').val()) < parseInt($('input[name="limit_amount"]').val()) ) {
            util.mobile_alert('单团瓜分积分数需要大于成团人数');
            $(this).focus();
            return false;
        }
        if(parseInt($('input[name="inte_group"]').val()) > parseInt($('input[name="inte_total"]').val()) ){
            util.mobile_alert('单团瓜分积分数不能大于总积分数');
            $(this).focus();
            return false;
        }
    })

    function save() {
        var _this = $(this);
        if($('.xuanchuan').val() == ''){
            return false;
        }
        if($('input[name="name"]').val() == '') {
            util.mobile_alert('请填写活动名称');
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
        if($('input[name="inte_total"]').val() == '') {
            util.mobile_alert('请填写瓜分积分总数');
            $(this).focus();
            return false;
        }
        if($('input[name="inte_total"]').val() < parseInt($('input[name="inte_total"]').attr("value"))) {
            return false;
        }

        if($('input[name="limit_amount"]').val() == '') {
            util.mobile_alert('请填写瓜分人数');
            $(this).focus();
            return false;
        }
        if(!(/(^[1-9]\d*$)/.test($('input[name = "limit_amount"]').val())) && $('input[name = "limit_amount"]').val() != 0 && ! /^\d+$/.test($('input[name = "limit_amount"]').val())){
            util.mobile_alert('瓜分人数请输入正整数');
            $(this).focus();
            return false;
        }
        if(parseInt($('input[name="limit_amount"]').val()) < 2) {
            util.mobile_alert('成团人数应大于等于2人');
            $(this).focus();
            return false;
        }
        if(parseInt($('input[name="limit_amount"]').val()) > 20 ){
            util.mobile_alert('成团人数应小于等于20人');
            $(this).focus();
            return false;
        }
        if($('input[name="inte_group"]').val() == '') {
            util.mobile_alert('请填写瓜分积分数');
            $(this).focus();
            return false;
        }


        if(parseInt($('input[name="inte_group"]').val()) < parseInt($('input[name="limit_amount"]').val()) ) {
            util.mobile_alert('瓜分积分数需要大于成团人数');
            $(this).focus();
            return false;
        }
        if(parseInt($('input[name="inte_group"]').val()) > parseInt($('input[name="inte_total"]').val()) ){
            util.mobile_alert('单团瓜分积分数不能大于总积分数');
            $(this).focus();
            return false;
        }


        if($('input[name="join_limit"]').val() == '' || parseInt($('input[name="join_limit"]').val()) < 0) {
            util.mobile_alert('请填写参团限制');
            return false;
        }
        util.ajax_json('/system/market/integration/add', function (response) {
            if (response.error == 0) {
                hasSaved = true;
                layer.ready(function () {
                    layer.msg('保存成功', {time: 1000},function () {
                        window.location = '/system/market/integration/list?nav=0';
                    });
                });
            } else {
                util.mobile_alert(response.message);
            }
        }, $('#form1').serialize());
        return false;
    };

    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                realTimeFmt:"HH:mm:ss",
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    };



</script>
<#include ("system.footer")
{{--<script type="text/javascript" src="/js/admin/reduce_price.js?v=1.1.9"></script>--!}
<script type="text/javascript">
    // getPowerInfo('main_config','pin_integration','sub_4','组队瓜分积分',0);
</script>
