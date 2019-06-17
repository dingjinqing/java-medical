<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/gift_manage.css?v=1.0.10" type="text/css" />
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css?v=1.0.0">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/shop_decorate.css?v=1.0.0">
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
    .prompt1{
        width: 100%;
        margin-top: 5px;
        height: 40px;
        line-height: 40px;
        border: 1px solid #F2E1C8;
        background: #FFF7EC;
        color: #666;
        margin-bottom: 12px;
        padding-left: 12px;
    }
    .prompt1 img{
        margin-top: -2px;
    }
    .pay_two{
        margin-bottom: 20px;
        width: 100%;
        text-align: center;
        display: flex;
    }
    .pay_two a{
        display: block;
        height: 50px;
        line-height: 50px;
        background: #F8F8F8;
        color: #666;
        flex: 1;
        float: left;
    }
    .pay_two .pay_active{
        background: #5a8bff;
        color: #fff;
    }
    .add_quan_img img {
        margin: 30px 0 7px;
    }
    .add_quan_img{
        margin-top:0;
        height:100px;
    }
</style>
<div style="min-width:1090px">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span style="color: #666;">活动有礼</span>
    </div>
    <div class="main-container">
        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
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
                        <a href="">
                            <#if  (!empty($config))
                                编辑${config->name!}活动
                                <#else>
                                添加活动有礼
                            </#if>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="config-quan-box">
                <div class="prompt1" style="margin-top: 10px;">
                    <img src="/image/admin/notice_img.png">
                    <span>同一时段仅允许进行一个活动有礼活动</span>
                </div>
                <div class="clearfix pay_two">
                    <a href="/admin/market/activityreward/config" class="pay_active">活动送券</a>
                    <a href="/admin/market/lottery/activity/config?top_index=4" link="/admin/market/lottery/activity/config?top_index=4" class="version">幸运大抽奖</a>
                    <a href="/admin/market/activityreward/customize/config">自定义</a>
                </div>
                <form action="/admin/market/activityreward/config" method="post" id="form1">
                    <input type="hidden" name="id" value="${_GET['id'] ?? 0!}"/>
                    {{csrf_field()!}
                    <div class="quan-pin-group">
                        <div class="clearfix">
                            <div class="quan-config">
                                <span class="quan-full-left"><strong>*</strong>是否只针对新用户：</span>
                            </div>
                            <div class="quan-config">
                                <label><input type="radio" name="action" value="1" <#if  ($config->action == 1 || empty($config)) checked </#if> /> 新用户</label>
                                <label><input type="radio" name="action"  value="2" <#if  ($config->action == 2) checked </#if> /> 全部用户</label>
                                <p class="prompt">只针对新用户的活动只对初次进入小程序的用户可见，通常在拉新活动中使用较为常见</p>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="quan-config">
                                <span class="quan-full-left"><strong>*</strong>活动名称：</span>
                            </div>
                            <div class="quan-config">
                                <input type="text" name="name"  placeholder="请输入活动名称" maxlength="20" value="${config->name!}"/>
                                <span class="limit_quan">(请输入20字以内)</span>
                                <p class="prompt">只作为商家记录使用，用户不会看到这个名称</p>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="quan-config">
                                <span class="quan-full-left"><strong>*</strong>活动宣传语：</span>
                            </div>
                            <div class="quan-config">
                                <input type="text" name="title"  placeholder="请输入活动宣传语"  maxlength="20" value="${config->title!}"/>
                                <span class="limit_quan">(请输入20字以内)</span>
                                <p class="prompt">展示在前端页面，用于优惠活动通知</p>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="quan-config">
                                <span class="quan-full-left"><strong>*</strong>背景图：</span>
                            </div>
                            <div class="quan-config">
                                <label class="bck_label">
                                    <input type="radio" name="bg_action" value="1" <#if  ($config->bg_action == 1 || empty($config)) checked </#if>/>
                                    <div class="add_bcg_img" >
                                        <img src="/image/admin/gift_config_shape.png" />
                                    </div>
                                    <div class="bcg_yl"><a href="#" class="show_displaybcg">预览</a></div>
                                    <div class="contain_show_bcg">
                                        <div class="sj_bcgb"></div>
                                        <div class="sj_bcgw"></div>
                                        <div class="show_bcg">
                                            <img src="http://${image_domain!}/image/admin/new_preview_image/coupon_config/gift_config3.jpg" />
                                        </div>
                                    </div>
                                </label>

                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="quan-config">
                                <span class="quan-full-left"><strong>*</strong>有效期：</span>
                            </div>
                            <div class="quan-config">
                                <input type="text" name="start_date" class="tb-text date-text" onclick="picker();"
                                       AUTOCOMPLETE="off" value="${config->start_date!}" /> 至
                                <input type="text" name="end_date" class="tb-text date-text" onclick="picker();"
                                       AUTOCOMPLETE="off" value="${config->end_date!}" />
                                <p class="prompt">同一时间内仅支持一个活动</p>
                            </div>
                        </div>
                        <div class="clearfix">
                            <div class="quan-config">
                                <span class="quan-full-left"><strong>*</strong>发放优惠券：</span>
                            </div>
                            <div class="quan-config">
                                <div class="data_item d_m_coupon edit_coupon_modules">
                                    <div class="card_modules_right_quan clearfix">
                                        <div style="display:flex;flex-wrap: wrap;width:600px">
                                            <div class="coupon_div clearfix" coupon_json="{{ json_encode($couponArr)!}" style="display: block;">
                                                <#list  ($couponArr as $item)
                                                    <div class="coupon_list">
                                                        {{--<img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del" />--!}
                                                        <input type="hidden" coupon_id="${item->id!}" act_code="" denomination="" class="coupon_info" name="mrking_voucher_id[]" value="${item->id!}"/>
                                                        <div class="coupon_list_top">
                                                            <#if ($item->act_code == 'voucher')
                                                                ¥${item->denomination!}
                                                            <#else>
                                                                折${item->denomination!}
                                                            </#if>
                                                        </div>
                                                        <div class="coupon_list_center">
                                                            <#if ($item->use_consume_restrict == 0)
                                                                <div class="coupon_center_limit">无限制</div>
                                                            <#else>
                                                                <div class="coupon_center_limit">满${item->least_consume!} 元使用</div>
                                                            </#if>
                                                            <div class="coupon_center_number">剩余<span>${item->surplus!}</span>张</div>
                                                        </div>
                                                        <div class="coupon_list_bottom">
                                                            领取
                                                        </div>
                                                    </div>
                                                </#list>
                                            </div>
                                            <div class=" add_quan_img">
                                                <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                                                <p>添加优惠券</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="coupon_list_clone hide">
                                        <div class="coupon_list">
                                            <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del" />
                                            <input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info" name="mrking_voucher_id[]"/>
                                            <div class="coupon_list_top">
                                                &yen;<span>&times;&times;</span>
                                            </div>
                                            <div class="coupon_list_center">
                                                <div class="coupon_center_limit">满&times;&times;使用</div>
                                                <div class="coupon_center_number">剩余<span>&times;&times;</span>张</div>
                                            </div>
                                            <div class="coupon_list_bottom">
                                                领取
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <p class="prompt changetext">最多可以添加5张优惠券，已过期和已停用的优惠券不能添加
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="save_bottom_footer" style="width: 1179px">
                        <div class="save" type="submit">保存</div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var hasSaved = true;
    $('.save_bottom_footer').width($('.main-container').width());

    $('.show_displaybcg').mouseover(function () {
        $('.contain_show_bcg').css("display","block");
        $('.contain_show_bcg').mouseover(function () {
            $('.contain_show_bcg').css("display","block");
        });
        $('.contain_show_bcg').mouseleave(function () {
            $('.contain_show_bcg').css("display","none");
        })
    });
    $('.show_displaybcg').mouseleave(function () {

        $('.contain_show_bcg').css("display","none");
    });
    $('.show_bcg').click(function () {
        var img = $(this).find('img').attr('src');
        $('.TempContainer img').attr('src',img);
        $('.TempContainer').show();
    })
    $('.TempContainer').click(function(){
        $(this).hide();
    })


    $('.save').click(function(){
        if($('input[name="name"]').val() == ''){
            util.mobile_alert('活动名称不能为空');
            return false;
        }
        if($('input[name="title"]').val() == ''){
            util.mobile_alert('活动宣传语不能为空');
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
        if($(".coupon_div").attr("coupon_json")=='[]'){
            util.mobile_alert('请添加优惠券');
            return false;
        }
        // 检查时间是否有重叠
        util.ajax_json('/admin/market/activityreward/list', function(response){
            if(response && response.error == 0){
                $('#form1').submit();
                hasSaved = true;
                return;
            }else{
                util.mobile_alert(response.message);
            }
        },{act:"check_activity", start_date: start_date, end_date: end_date, id: '${_GET['id'] ?? 0!}'});
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
            event.returnValue = "确认要离开吗？";
        }
    };

    $(".add_quan_img").click(function(){
        var startDate = $('input[name="start_date"]').val();
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择优惠券', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['520px','420px']
                , content: '/admin/frame/coupon/select?nav=6&start_date='+startDate //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
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
                    var coupon_arr = [];
                    $('.coupon_div').html('');
                    $(list_active).each(function (i) {
                        coupon_arr[i] = {};
                        coupon_arr[i].act_code = $(this).find('.coupon_info').attr('act_code');
                        coupon_arr[i].denomination = $(this).find('.coupon_info').attr('denomination');
                        coupon_arr[i].consume_text = $(this).find('.coupon_center_limit').text().replace(/\s+/g,"");
                        coupon_arr[i].receive_text = $(this).find('.coupon_center_number').text().replace(/\s+/g,"");
                        coupon_arr[i].coupon_id = $(this).find('.coupon_info').attr('coupon_id');
                        var coupon_clone = $('.coupon_list_clone').find('.coupon_list').clone();
                        $('.coupon_div').show();
                        $('.coupon_div').append(coupon_clone);
                        var coupon_json = JSON.stringify(coupon_arr);
                        $('.coupon_div').attr('coupon_json',coupon_json);

                    });
                    $('.coupon_div').find('.coupon_list').each(function (j,v) {
                        $(v).find('.coupon_info').attr('act_code',coupon_arr[j].act_code);
                        $(v).find('.coupon_info').attr('denomination',coupon_arr[j].denomination);
                        $(v).find('.coupon_info').attr('coupon_id',coupon_arr[j].coupon_id);
                        $(v).find('[name="mrking_voucher_id[]"]').val(coupon_arr[j].coupon_id);
                        if(coupon_arr[j].act_code == "discount"){
                            $(v).find('.coupon_list_top').html('<span>' + coupon_arr[j].denomination + '</span>折');
                        }
                        if(coupon_arr[j].act_code == "voucher"){
                            $(v).find('.coupon_list_top').html('￥<span>' + coupon_arr[j].denomination + '</span>');
                        }
                        $(v).find('.coupon_center_limit').text(coupon_arr[j].consume_text);
                        $(v).find('.coupon_center_number').text(coupon_arr[j].receive_text);
                    });
                    console.log(coupon_arr);
                    if(coupon_arr.length==5){
                        $(".add_quan_img").hide();
                        $(".changetext").text("最多可以添加5张优惠券，已过期和已停用的优惠券不能添加");
                    }else{
                        $(".add_quan_img").show();
                    }
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    $('.coupon_div').on('click','.coupon_del',function () {
        var coupon_arr = JSON.parse($('.coupon_div').attr('coupon_json'));
        for(var i in coupon_arr){
            if(coupon_arr[i].coupon_id == $(this).next().attr('coupon_id')){
                coupon_arr.splice(i,1);
            }
        }
        console.log(coupon_arr);
        $('.coupon_div').attr('coupon_json',JSON.stringify(coupon_arr));
        $(this).parent().remove();
        if(coupon_arr.length<5){
            $('.add_quan_img').show();
            $(".changetext").text("最多可以添加5张优惠券，已过期和已停用的优惠券不能添加");
        }
        hasSaved = false;
    });

    var msg = '{{ session("message")!}';
    if (msg != '') {
        util.mobile_alert(msg);
    }
    <#if  (!empty($config) && $config->start_date<=date("Y-m-d H:i:s"))
        $('input[type="text"], input[type="radio"]').prop('disabled', true);
        $('.add_quan_img').hide();
        $('input[name="name"], input[name="title"]').prop('disabled', false);
    </#if>
</script>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script type="text/javascript">
    // getPowerInfo('main_config','coupon_gift','sub_4','活动送券',0);
    getPowerInfo('main_config','activity_reward','sub_4','活动有礼',0);
</script>
<script>
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