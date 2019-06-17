<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pay_courtesy.css?v=1.0.4" type="text/css" />
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
    a:link,a:focus,a:hover,a:active{
        text-decoration: none;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span>支付有礼</span>
    </div>
    <div class="main-container fix_every_footer">
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="ids" value="${data_list->id!}">
            <div class="pay_content">
                <ul class="tab clearfix">
                    <li <#if ($nav_type==0)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=0" >全部支付有礼活动</a>
                    </li>
                    <li <#if ($nav_type==1)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=1">进行中</a>
                    </li>
                    <li <#if ($nav_type==2)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=2" >未开始</a>
                    </li>
                    <li <#if ($nav_type==3)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=3">已过期</a>
                    </li>
                    <li <#if ($nav_type==4)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=4">已停用</a>
                    </li>
                    <li <#if ($nav_type==5)class="active"</#if>><a href="/admin/market/payreward/add"><#if ($data_list->id)编辑${data_list->activity_names!}活动 <#else>添加支付有礼活动</#if></a></li>
                </ul>
                <div class="prompt">
                    <img src="http://${image_domain!}/image/admin/notice_img.png">
                    <span>同一时段仅允许进行一个支付有礼活动</span>
                </div>
                <div class="clearfix pay_two">
                    <a href="/admin/market/payreward/add" target="_blank">分裂优惠券</a>
                    <a href="/admin/market/payreward/coupon" target="_blank">普通优惠券</a>
                    <a href="/admin/market/lottery/payreward"  link="/admin/market/lottery/payreward" class="pay_active version">幸运大抽奖</a>
                    <a href="/admin/market/payreward/payrewardurl" target="_blank">自定义</a>
                </div>
                <div class="pay_courtesy clearfix">
                    <div class="pay_left">
                        <div class="left_title"></div>
                        <img src="http://${image_domain!}/image/admin/pay.jpg" width="100%"/>
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
                                        <input type="text" placeholder="最多支持10个字" name="activity_names" value="${data_list->activity_names!}"/>
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
                                        生效时间：<input <#if ($edit==1 && $data_list->act_start_time<= date("Y-m-d H:i:s")) disabled </#if> type="text" name="act_start_time" value="${data_list->act_start_time!}" id="actstartDate"
                                               onfocus="picker()" autocomplete="off"/>
                                    </div>
                                    <div class="right_ipt">
                                        过期时间：<input <#if ($edit==1 && $data_list->act_start_time<= date("Y-m-d H:i:s")) disabled </#if> type="text" name="act_end_time" value="${data_list->act_end_time!}" id="actendDate"
                                                    onfocus="picker()" autocomplete="off"/>

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
                                        <select name="lottery" id="">
                                            <option value="">请选择抽奖活动</option>
                                            <#list ($lottery_list as $item)
                                            <option value="${item->id!}" <#if ($data_list->lottery == $item->id) selected </#if>>${item->lottery_name!}</option>
                                            </#list>
                                        </select>
                                        <a href="##" class="refresh">刷新 |</a>
                                        <a href="/admin/market/lottery/config" target="_blank">新建</a>
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
                                        支付金额满
                                        <input <#if ($edit==1 && $data_list->act_start_time<= date("Y-m-d H:i:s")) disabled </#if> type="text" name="least_money" value="${data_list->least_money!}" style="width: 60px" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                        元
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="fix_footer">
                <div class="pay_save">保存</div>
            </div>
        </form>
    </div>
</div>
<script>
    var hasSaved = true;
</script>
<#include "/admin/footer.ftl">
<script>
    var act_end_time = "${act_end_time!}";
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
    //刷新
    $(".refresh").click(function () {
        util.ajax_json("/admin/market/lottery/isgoing",function (res) {
            if(res != -1){
                $('select[name="lottery"]').html("");
                var opt = '<option value="">请选择抽奖活动</option>';
                for(var i=0;i<res.length;i++){
                    opt += "<option value='"+res[i].id+"'>"+res[i].lottery_name+"</option>"
                }
                $('select[name="lottery"]').html(opt);
                util.mobile_alert("刷新成功");
            }else{
                util.mobile_alert("刷新成功");
            }
        })
    });
    //保存
    $(".pay_save").click(function () {
        //保存验证
        if($('input[name="activity_names"]').val() == ''){
            util.mobile_alert('请填写活动名称');
            return false;
        }
        if($('input[name="act_start_time"]').val() == ''){
            util.mobile_alert('请填写活动开始时间');
            return false;
        }
        if($('input[name="act_end_time"]').val() == ''){
            util.mobile_alert('请填写活动结束时间');
            return false;
        }
        if($('input[name="act_start_time"]').val()>$('input[name="act_end_time"]').val() ){
            util.mobile_alert('结束时间不能早于开始时间');
            return false;
        }
        if($("select[name='lottery']").val() == ''){
            util.mobile_alert('请选择抽奖活动');
            return false;
        }
        if($('input[name="least_money"]').val() == ''){
            util.mobile_alert('请填写触发活动金额');
            return false;
        }
        if(("${edit!}"==1 && "${data_list->act_end_time<date("Y-m-d H:i:s")!}") || !"${edit!}"){
            if(act_end_time && $('input[name="act_start_time"]').val() < act_end_time){
                util.mobile_alert('该时间段存在进行中的活动');
                return false;
            }
        }
        var data = {
            id : $("input[name='id']").val(),
            act_start_time : $("input[name='act_start_time']").val(),
            act_end_time : $("input[name='act_end_time']").val()
        };
        if(("${edit!}"==1 && "${data_list->act_end_time<date("Y-m-d H:i:s")!}") || !"${edit!}"){
            util.ajax_json("/admin/market/payreward/isconflict",function(d){
                if(d&&d.error==0){
                    layer.ready(function () {
                        layer.msg('保存成功', {time: 2000},function () {
                            $("#form1").submit();
                        });
                    });
                }else{
                    layer.msg(d.message,{time:1000});
                    location.reload();
                }
            },data);
        }else{
            layer.ready(function () {
                layer.msg('保存成功', {time: 2000},function () {
                    $("#form1").submit();
                });
            });
        }
    });
    $(".version").click(function () {
        var _html = '抽奖';
        var mod = 'lottery';
        var url = $(this).attr("link");
        $(this).attr('href','##');
        $(this).removeAttr('target');
        if(getAuthorityDetail(0,url) == 0){
            var data = {};
            data = getPowerInfo('main_config',mod,'sub_4',_html);
            if(data.content == 1){
                $(this).attr('href',url);
                $(this).attr('target','_blank');
            }
        }
    });
    // 版本控制
        getPowerInfo('main_config','lottery','sub_4','抽奖',0);
</script>