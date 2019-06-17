<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pay_courtesy.css?v=1.0.1" type="text/css" />
<style>
    /* .card_add{
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
    } */
    .pay_left{
        position: relative;
    }
    .custom_popup_container{
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        width: 230px;
        top: 150px;
    }
    .custom_top{
        position: absolute;
        width: 20px;
        height: 20px;
        right: 24px;
    }
    .custom_xian{
        position: absolute;
        width: 1px;
        background-color: #fff;
        height: 20px;
        top:20px;
        right: 34px;

    }
    .img_content{
        width: 100%;
        background: #fff;
        height: 270px;
        margin-top: 40px;
        border-radius: 5px;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
    }
    .no_custom_img{
        text-align: center;
    }
    .no_custom_img span{
        color: #999;
        display: block;
        margin-top: 15px;
    }
    .no_custom_img img{
        width: 80px;
        height: 80px;
    }
    .img_content > img{
        width: 100%;
        height: 100%;
    }
    .add_image {
        width: 70px;
        height: 70px;
        background: url(/image/admin/btn_add.png) no-repeat;
        box-shadow: 0 0 0 #fff;
        /* background-position: center 48%;
        background-size: 50%; */
        padding-top: 40px;
        padding-left: 8px;
        color: #9a9a9a;
        border: none;
        margin-right: 10px;
        float: left;
    }
    .logo_img {
        float: left;
        display: none;
        border: 1px solid #eee;
        position: relative;
        margin-right:10px;
    }
    .logo_img img{
        width: 70px;
        height: 70px;
    }
    .logo_img span{
        text-align: center;
        width: 100%;
        line-height: 18px;
        position: absolute;
        left: 0;
        bottom: 0;
        background-color: rgba(0,0,0,0.5);
        color: #fff;
        font-size: 12px;
        display: none;
        cursor: pointer;
    }
    [type="button"]:hover, [type="button"]:active, [type="button"]:focus, [type="reset"]:hover, [type="reset"]:active, [type="reset"]:focus {
        background-color: transparent;
        border-color: #e5e5e5;
    }
    .pay_right+.pay_right{
        margin-top:10px;
    }
    .tips{
        color:#999;
    }
    .choose_goods{
        margin-top: 15px;
    }
    .add_goods > span:first-child{
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5A8BFF;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        display: inline-block;
    }
    .add_goods > span:first-child > img{
        vertical-align: baseline;
    }
    .add_goods > span:last-child{
        margin-left: 10px;
    }
    .add_goods{
        margin-top: 10px;
    }
    .choose_goods{
        display: none;
    }
    .choose_link{
        background-color: #fff;
        color: #000;
        width: 80px;
        line-height: 28px;
        border: 1px solid #dbdbdb;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span>活动有礼</span>
    </div>
    <div class="main-container fix_every_footer">
        <form action="/admin/market/activityreward/customize/config" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="id" value="${data_list->id!}"/>
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
                            <#if  (!empty($data_list))
                                编辑${data_list->name!}活动
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
                    <a href="/admin/market/lottery/activity/config" >幸运大抽奖</a>
                    <a href="/admin/market/activityreward/customize/config" class="pay_active">自定义</a>
                </div>
                <div class="pay_courtesy clearfix">
                    <div class="pay_left">
                        <div class="left_title"></div>                        
                        <img src="http://${image_domain!}/image/admin/activityreward_index.jpg" width="100%"/>
                        <div class="custom_popup_container">
                            <img src="http://${image_domain!}/image/wxapp/split_btn1.png" alt="" class="custom_top">
                            <i class="custom_xian"></i>
                            <div class="img_content">
                                <div class="no_custom_img" <#if (!empty($data_list)) hide </#if>>
                                    <img src="http://${image_domain!}/image/admin/no_custom_img.png" alt="">
                                    <span>
                                        活动图片
                                    </span>
                                </div>
                                <#if (!empty($data_list)) <img src="${data_list->customize_img_path!}" alt=""> <#else> <img src="" alt="" style="display:none;"> </#if>
                            </div>
                        </div>
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
                                        <input type="text" placeholder="最多支持10个字" name="name" class="activity_names" id="activity_names" value="${data_list->name!}"/>
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
                                        生效时间：<input <#if ($edit==1 && $data_list->start_date<= date("Y-m-d H:i:s")) disabled </#if> type="text" name="start_date" value="${data_list->start_date!}" id="actstartDate"
                                                onfocus="picker()" autocomplete="off"/>
                                    </div>
                                    <div class="right_ipt">
                                        过期时间：<input <#if ($edit==1 && $data_list->end_date<= date("Y-m-d H:i:s")) disabled </#if> type="text" name="end_date" value="${data_list->end_date!}" id="actendDate"
                                                    onfocus="picker()" autocomplete="off"/>

                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>活动图片：</span>
                                </div>
                                <div class="tem_right" style="margin-top:7px;">
                                    <input type="hidden" name="customize_img_path" value="${data_list->customize_img_path!}">
                                    <div class="logo_img" style="display: none;">
                                        <img src="" alt="">
                                        <span class="choose_img">重新选择</span>
                                    </div>
                                    <input type="button" value="" class="add_image" style="display: block;" >
                                    <span style="margin-top: 25px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：560px * 700px</span>
                                </div> 
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>设置链接：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <input type="text" name="customize_url" value="${data_list->customize_url!}">
                                        <button class="choose_link">选择链接</button>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="pay_right">
                        <div class="pay_right_title">触发条件</div>
                        <ul class="template_right_ul">
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>用户类型：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <label>
                                            <input type="radio" name="action" value="1" <#if (!empty($data_list) && $data_list->action==1) checked <#elseif>(empty($data_list)) checked </#if>>新用户
                                        </label>
                                        <label style='margin-left:30px;'>
                                            <input type="radio" name="action" value="2" <#if (!empty($data_list) && $data_list->action==2) checked </#if>>全部用户
                                        </label>
                                    </div>
                                    <div class="tips" style="margin-top: 10px;width:270px;">
                                        只针对新用户的活动只对初次进入小程序的用户可见，通常在拉新活动中使用较为常见
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
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
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


    $(".add_image,.choose_img").click(function(){
        var that = $(this);
        $.jImageManager({
            img_width:560,
            img_height:700,
            ok_cb:function(img_arr){
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                $('.logo_img').show()
                $('.logo_img img').attr('src',path);
                $('input[name="customize_img_path"]').val(path);
                that.hide();
                $('.img_content > img').attr('src',path);
                $('.img_content > img').show();
                $('.no_custom_img').hide();
            }
        });
        hasSaved = false;
    });
    $(document).on('mouseenter','.logo_img',function(){
        $('.logo_img').find('span').show()
    });
    $(document).on('mouseleave','.logo_img',function(){
        $('.logo_img').find('span').hide()
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
        if($('input[name="name"]').val() == ''){
            util.mobile_alert('请填写活动名称');
            return false;
        }
        if($('input[name="start_date"]').val() == ''){
            util.mobile_alert('请填写活动开始时间');
            return false;
        }
        if($('input[name="end_date"]').val() == ''){
            util.mobile_alert('请填写活动结束时间');
            return false;
        }
        if($('input[name="start_date"]').val()>$('input[name="end_date"]').val() ){
            util.mobile_alert('结束时间不能早于开始时间');
            return false;
        }
        if($('input[name="customize_img_path"]').val() == ''){
            util.mobile_alert('请选择活动图片');
            return false;
        }
        if($('input[name="customize_url"]').val() == ''){
            util.mobile_alert('请填写活动跳转链接');
            return false;
        }
        if($('input[name="action"]:checked').length == 0){
            util.mobile_alert('请选择触发用户类型');
            return false;
        }
        // if(("${edit!}"==1 && "${data_list->act_end_time<date("Y-m-d H:i:s")!}") || !"${edit!}"){
        //     if(act_end_time && $('input[name="act_start_time"]').val() < act_end_time){
        //         util.mobile_alert('该时间段存在进行中的活动');
        //         return false;
        //     }
        // }
        hasSaved = true;
        // 检查时间是否有重叠
        util.ajax_json('/admin/market/activityreward/list', function(response){
            if(response && response.error == 0){
                $('#form1').submit();
                return false;
            }else{
                util.mobile_alert(response.message);
            }
        },{act:"check_activity", start_date: $('input[name="start_date"]').val(), end_date: $('input[name="end_date"]').val(), id: '${_GET['id'] ?? 0!}'});

        // var data = {
        //     id : $("input[name='id']").val(),
        //     act_start_time : $("input[name='act_start_time']").val(),
        //     act_end_time : $("input[name='act_end_time']").val()
        // };
        // if(("${edit!}"==1 && "${data_list->act_end_time<date("Y-m-d H:i:s")!}") || !"${edit!}"){
        //     util.ajax_json("/admin/market/payreward/isconflict",function(d){
        //         if(d&&d.error==0){
        //             layer.ready(function () {
        //                 layer.msg('保存成功', {time: 2000},function () {
        //                     $("#form1").submit();
        //                 });
        //             });
        //         }else{
        //             layer.msg(d.message,{time:1000});
        //             location.reload();
        //         }
        //     },data);
        // }else{
        //     layer.ready(function () {
        //         layer.msg('保存成功', {time: 2000},function () {
        //             $("#form1").submit();
        //         });
        //     });
        // }
    });

    $('.choose_link').click(function (e) {
        e.preventDefault();
        var ipt_link = $(this).prev();
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择链接', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['800px','460px']
                , content: '/admin/frame/decoration/link' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    var iframe = layer.getChildFrame('body', index);
                    var link = iframe.contents().find('tr[data-back="false"]').find(".link").text();
                    var appid = iframe.contents().find('tr[data-back="false"]').find(".appid").text();
                    ipt_link.val(link);
                    ipt_link.attr('data-appid',appid);
                    layer.close(index);
                }
            });
        });
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
    if($.trim($('input[name="payreward_logo"]').val()) != ''){
        $('.img_content > img').attr('src',$('input[name="payreward_logo"]').val());
        $('.img_content > img').show();
        $('.no_custom_img').hide();
    }
</script>
<script>
    getPowerInfo('main_config','activity_reward','sub_4','活动有礼',0);
</script>