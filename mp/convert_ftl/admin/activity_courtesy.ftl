<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pay_courtesy.css?v=1.0.3" type="text/css" />
<style type="text/css">
    body{
        padding-bottom: 40px;
    }
    button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    button:focus{
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
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span>活动有礼</span>
    </div>
    <div class="main-container fix_every_footer">
        <form action="/admin/market/lottery/activity/config" method="post" id="form1">
            {{ csrf_field()!}
            <input type="hidden" name="id" value="${config->id!}"/>
            <div class="pay_content">
                <ul class="tab clearfix">
                    <li>
                        <a href="/admin/market/activityreward/list" >所有活动有礼活动</a>
                    </li>
                    <li>
                        <a href="/admin/market/activityreward/list?nav=1" >进行中</a>
                    </li>
                    <li>
                        <a href="/admin/market/activityreward/list?nav=2" >未开始</a>
                    </li>
                    <li>
                        <a href="/admin/market/activityreward/list?nav=3">已过期</a>
                    </li>
                    <li>
                        <a href="/admin/market/activityreward/list?nav=4">已停用</a>
                    </li>
                    <li class="active">
                        <a href="/admin/market/lottery/activity/config">
                            <#if  (!empty($config))
                                编辑${config->name!}活动
                            <#else>
                                添加活动有礼
                            </#if>
                        </a>
                    </li>
                </ul>
                <div class="prompt">
                    <img src="/image/admin/notice_img.png">
                    <span>同一时段仅允许进行一个活动有礼活动</span>
                </div>
                <div class="clearfix pay_two">
                    <a href="/admin/market/activityreward/config">活动送券</a>
                    <a href="/admin/market/lottery/activity/config" class="pay_active">幸运大抽奖</a>
                    <a href="/admin/market/activityreward/customize/config" >自定义</a>
                </div>
                <div class="pay_courtesy clearfix">
                    <div class="pay_left">
                        <div class="left_title"></div>
                        <img src="http://${image_domain!}/image/admin/act.jpg" width="100%"/>
                    </div>
                    <div class="pay_right">
                        <div class="pay_right_title">活动信息</div>
                        <ul class="template_right_ul">
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>活动名称：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <input type="text" name="name" value="${config->name!}"/>
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>活动有效期：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt" style="margin-bottom: 10px;">
                                        生效时间：
                                        <input type="text" name="start_date" class="tb-text date-text" onclick="picker();"
                                               AUTOCOMPLETE="off" value="${config->start_date!}" />
                                    </div>
                                    <div class="right_ipt">
                                        过期时间：
                                        <input type="text" name="end_date" class="tb-text date-text" onclick="picker();"
                                               AUTOCOMPLETE="off" value="${config->end_date!}" />
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>选择抽奖活动：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <#if  ($config->mrking_voucher_id)
                                            <span style="line-height: 30px;">${config->lottery_name!}</span>
                                            <#else>
                                            <select name="mrking_voucher_id" id="lottery-activity">
                                                <option value="">抽奖活动1</option>
                                            </select>
                                            <a href="javascript:void(0)" id="lottery-refresh">刷新 |</a>
                                            <a href="/admin/market/lottery/config" target="_blank">新建</a>
                                        </#if>
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>触发条件：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <label for="new_user">
                                            <input type="radio" id="new_user" name="action" value="1" <#if  ($config->action == 1 || empty($config)) checked </#if> />
                                            新用户
                                        </label>
                                        <label for="all_user">
                                            <input type="radio" id="all_user" name="action" value="2" <#if  ($config->action == 2) checked </#if> />
                                            全部用户
                                        </label>
                                        <p>只针对新用户的活动只对初次进入小程序的用户可见，通常在拉新活动中使用较为常见</p>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="fix_footer">
                <button class="pay_save" type="submit">保存</button>
            </div>
        </form>
    </div>
</div>
<script>
    var hasSaved = true;
    $('#lottery-refresh').click(function () {
        util.ajax_json('/admin/market/activityreward/list', function(response){
            if(response && response.error == 0){
                var html = '';
                for (var i in response.content) {
                    html += '<option value="'+response.content[i].id+'">'+response.content[i].lottery_name+'</option>';
                }
                $('#lottery-activity').html(html);
            }else{
                util.mobile_alert(response.message);
            }
        },{act:"get_lottery_activity"});
    })
    $('.pay_save').click(function(){
        if($('input[name="name"]').val() == ''){
            util.mobile_alert('活动名称不能为空');
            return false;
        }
        var start_date = $('input[name="start_date"]').val();
        if(start_date == ''){
            util.mobile_alert('开始时间不能为空');
            return false;
        }
        var end_date = $('input[name="end_date"]').val();
        if(end_date == ''){
            util.mobile_alert('结束时间不能为空');
            return false;
        }
        if(start_date > end_date){
            util.mobile_alert('开始时间不能大于结束时间');
            return false;
        }
        if ($('input[name="mrking_voucher_id"]').val() == '') {
            util.mobile_alert('请选择抽奖活动');
            return false;
        }
        hasSaved = true;
        // 检查时间是否有重叠
        util.ajax_json('/admin/market/activityreward/list', function(response){
            if(response && response.error == 0){
                $('#form1').submit();
                return false;
            }else{
                util.mobile_alert(response.message);
            }
        },{act:"check_activity", start_date: start_date, end_date: end_date, id: '${_GET['id'] ?? 0!}'});
        return false;
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
            event.returnValue = "确认要离开吗?";
        }
    };
    $(".fix_footer").outerWidth($('.fix_every_footer').width());

    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    var msg = '{{ session("message")!}';
    if (msg != '') {
        util.mobile_alert(msg);
    }
    $('#lottery-refresh').trigger('click');
    <#if  (!empty($config) && $config->start_date<= date("Y-m-d H:i:s"))
        $('input[type="text"], input[type="radio"], select').prop('disabled', true);
        $('input[name="name"]').prop('disabled', false);
    </#if>
    //版本控制
    //    var type = util.getUrlParam('type');
    //    if(type == 1){
    //        getPowerInfo('main_config','coupon_split','sub_4','分裂优惠券',0);
    //    }
</script>
<#include "/admin/footer.ftl">
<script>
    getPowerInfo('main_config','lottery','sub_4','抽奖',0);
</script>