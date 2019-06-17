<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pay_courtesy.css?v=1.0.1" type="text/css" />
<style>
    .card_add{
        display:inline-block;
        vertical-align: top;
        height: 98px;
        margin-bottom: 10px;
    }
    .coupon_div{
        display:block;
        width: 360px;
    }
    .coupon_list{
        display:inline-block;
        float:none;
    }
    .card_add img {
        margin-top: 14px;
    }
    .pay_right{
        width: 490px;
    }
    .pay_courtesy {
        width: 835px;
    }
    .coupon_list_bottom{
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
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
                    <a href="/admin/market/payreward/coupon" class="pay_active">普通优惠券</a>
                    <a href="/admin/market/lottery/payreward" link="/admin/market/lottery/payreward" class="version">幸运大抽奖</a>
                    <a href="/admin/market/payreward/payrewardurl" target="_blank">自定义</a>
                </div>
                <div class="pay_courtesy clearfix">
                    <div class="pay_left">
                        <div class="left_title"></div>                        
                        <img src="http://${image_domain!}/image/admin/payreward_coupon.jpg" width="100%"/>
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
                                        <input type="text" placeholder="最多支持10个字" name="activity_names" class="activity_names" id="activity_names" value="${data_list->activity_names!}"/>
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
                            <li class="clearfix">
                                <input type="hidden" name="coupon_ids" value="${data_list->coupon_ids!}">
                                <div class="fl">
                                    <em>*</em>
                                    <span>发放优惠券：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="coupon_div clearfix" coupon_json="" <#if ($data_list) style="display: block" </#if>>
                                        <#if ($data_list)
                                            <#list ($coupon_list as $k=>$cou)
                                                <div class="coupon_list">
                                                    <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del" <#if ($edit==1 && $data_list->act_start_time<= date("Y-m-d H:i:s")) style="display: none" </#if>>
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
                                        </#if>
                                        <div class="card_add card_add_click">
                                        <img src="http://mpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png" alt="">
                                        <p>添加优惠券</p>
                                        </div>  
                                    </div>
                                    <p style="color:#bbb">最多可以添加5张优惠券，已过期和已停用的优惠券不能添加</p>
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
</div>
<#include "/admin/footer.ftl">
<script>
    var hasSaved = true;
    var act_end_time = "${act_end_time!}"
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $('.coupon_div').on('click','.coupon_del',function () {
        var coupon_ids = $("input[name='coupon_ids']").val().split(',');
        for(var i in coupon_ids){
            if(coupon_ids[i] == $(this).next().attr('coupon_id')){
                coupon_ids.splice(i,1);
            }
        }
        $('input[name="coupon_ids"]').val(coupon_ids);
        $(this).parent().remove();
        if(coupon_ids.length<5){
            $('.card_add').show();
            $(".changetext").text("最多可以添加5张优惠券，已过期和已停用的优惠券不能添加");
        }
        hasSaved = false;
    });

    $(".coupon_div").on('click','.card_add_click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择优惠券', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['520px','420px']
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
                    // var coupon_arr = [];
                    var coupon_ids = [];
                    var card_add = $('.card_add').clone();
                    $('.coupon_div').html('');
                    // if($("input[name='coupon_ids']").val()){
                    //     coupon_ids = $("input[name='coupon_ids']").val().split(',');
                    // }
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
                        $('input[name="coupon_ids"]').val(coupon_ids);
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
    var activity_names = $('.activity_names').get(0);
    var flag = false;
    activity_names.addEventListener('compositionstart', function(){
        console.log(1);
        flag = true;
 6  });
    activity_names.addEventListener('compositionend',function(){
        console.log(2)
        flag = false
        limitLength(this.value,'20','','activity_names')
    });
    activity_names.addEventListener('input',function(){
        console.log(3)
        limitLength(this.value,'20','','activity_names')
    });
    function limitLength(value, byteLength, title, attribute) { 
        if(!flag){
            var newvalue = value.replace(/[^\x00-\xff]/g, "**");               
            var length = newvalue.length;
            if (length * 1 <=byteLength * 1){ 
                    return; 
            } 
            var limitDate = newvalue.substr(0, byteLength); 
            var count = 0; 
            var limitvalue = ""; 
            for (var i = 0; i < limitDate.length; i++) { 
                    var flat = limitDate.substr(i, 1); 
                    if (flat == "*") { 
                        count++; 
                    } 
            } 
            var size = 0; 
            var istar = newvalue.substr(byteLength * 1 - 1, 1);
        
            if (count % 2 == 0) { 
                    size = count / 2 + (byteLength * 1 - count); 
                    limitvalue = value.substr(0, size); 
            } else { 
                    size = (count - 1) / 2 + (byteLength * 1 - count); 
                    limitvalue = value.substr(0, size); 
            }
            document.getElementById(attribute).value = limitvalue; 
            return; 
        }         
    }

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
        if($('input[name="least_money"]').val() == ''){
            util.mobile_alert('请填写触发活动金额');
            return false;
        }
        if($('input[name="coupon_ids"]').val() == ''){
            util.mobile_alert('请选择指定优惠券');
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
</script>
<script>
    getPowerInfo('main_config','pay_reward','sub_4','支付有礼',0);
</script>