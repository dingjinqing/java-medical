<#include "/admin/header.ftl">
    <link rel="stylesheet" href="/css/admin/shop_collect_config.css?v=1.0.1" type="text/css"/>
    <div>
        <div class="title">
            <div>
                <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
                <span style="color: #666;">收藏有礼</span>
            </div>
        </div>
        <form action="" method="POST" id="form1">
            {{csrf_field()!}
            <div class="main_container">
                <div class="collect_title">
                    <div class="it_left">
                        <div class="bold_one">收藏有礼</div>
                        <div style="color:#000;font-weight: 600;">引导用户将您的店铺小程序添加至微信”我的小程序“可有效提升店铺小程序打开率，用户活跃度等多项指标。</div>
                        <div>注：1、由于微信的限制，系统无法获知用户是否已收藏小程序，此功能仅作为引导性提示。</div>
                        <div style="padding-left:27px;">2、会出现用户未收藏或取消收藏小程序也获得收藏奖励的情况，请知悉。</div>
                    </div>
                    <div class="it_right">
                        <div class="it_switch" img_id="0">
                            <label for="">
                                <input type="checkbox" name="on_off" />
                                <img class="draggable" src="http://${image_domain!}/image/admin/circle.png" alt="">
                            </label>
                        </div>
                        <span class="is_open">已关闭</span>
                    </div>
                </div>
                <div class="bottom_container clearfix">
                    <div id="myCarousel" class="carousel slide fl set_left" data-interval="4000" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#myCarousel" data-slide-to="0" 
                                class="active"></li>
                            <li data-target="#myCarousel" data-slide-to="1"></li>
                        </ol>   
                        <div class="carousel-inner">
                            <div class="item active">
                                <img src="/image/admin/collect_slide1.png" alt="First slide">
                            </div>
                            <div class="item">
                                <img src="/image/admin/collect_slide2.png" alt="Second slide">
                            </div>
                        </div>
                    </div> 
                    <div class="set_right fr">
                        <div>
                            <label for="actstartDate">活动时间</label>
                            <input type="text" name="start_time" <#if (!empty($data_list['start_time'])) value="${data_list['start_time']!}" </#if> id="actstartDate" onfocus="picker()" autocomplete="off"/> 至 <input type="text" name="end_time" <#if (!empty($data_list['end_time'])) value="${data_list['end_time']!}" </#if> id="actendDate"
                            onfocus="picker()" autocomplete="off"/>
                        </div>
                        <div>
                            <span>收藏奖励</span>
                            <input type="checkbox" name="score_status" <#if (!empty($data_list['score']) && $data_list['score']>0) checked </#if>>积分
                            <input type="text" name="score" <#if (!empty($data_list['score']) && $data_list['score']>0) value="${data_list['score']!}" </#if>>积分
                        </div>
                        <div class="coupon">
                            <input type="checkbox" name="coupon_status" <#if (!empty($data_list['coupon_ids']) && $data_list['coupon_ids']!='') checked </#if>>优惠券
                            <div class="coupon_box clearfix">
                                <input type="hidden" name="coupon_ids" value="${data_list['coupon_ids']!}">
                                <div class="tem_right">
                                    <div class="coupon_div clearfix" coupon_json="" <#if ($data_list) style="display: block" </#if>>
                                        <#if ($data_list['coupons'])
                                            <#list ($data_list['coupons'] as $k=>$cou)
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
                                    <p style="color:#bbb">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
                                </div>
                            </div>
                        </div>
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
    <script>
        var hasSaved = true;
        function picker(){
            hasSaved = false;
            return WdatePicker(
                {
                    dateFmt: 'yyyy-MM-dd HH:mm:ss',
                    autoUpdateOnChanged: false
                }
            );
        }
        $(function(){
            $(".draggable").click(function () {
                var img_iud = $(this).parent().parent().attr('img_id');
                if(img_iud == 0){
                    $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat");
                    $(this).parent().parent().css("background-size","100% 100%");
                    $(this).parent().parent().attr('img_id','1');
                    $(this).animate({right:"0px"});
                    $(this).prev().prop('checked', true);
                    $('.bottom_container').css("display","block");
                    $(this).parent().parent().parent().parent().find(".is_open").text("已开启");
                } else if(img_iud == 1) {
                    $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat");
                    $(this).parent().parent().css("background-size","100% 100%");
                    $(this).parent().parent().attr('img_id','0');
                    $(this).animate({right:"20px"});
                    $(this).prev().prop('checked', false);
                    $('.bottom_container').css("display","none");
                    $(this).parent().parent().parent().parent().find(".is_open").text("已关闭");
                }
            });
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
            if($('.coupon_div').find('.coupon_list').length >4){
                $('.card_add').hide();
            }
            var status = '${data_list['on_off']!}';
            if (parseInt(status) == 1) {
                $(".draggable").trigger('click');
            } else {
                $('.bottom_container').css("display","none");
            }
            $('.pay_save').click(function(){
                if($('[name="on_off"]').is(':checked')){
                    if($('[name="start_time"]').val() == ''){
                    util.mobile_alert('请选择活动开始时间');
                    return false;
                    }
                    if($('[name="end_time"]').val() == ''){
                        util.mobile_alert('请选择活动结束时间');
                        return false;
                    }
                    if(!$('[name="score_status"]').is(':checked') && !$('[name="coupon_status"]').is(':checked')){
                        util.mobile_alert('请至少选择一种奖励方式');
                        return false;
                    }
                    if($('[name="score_status"]').is(':checked') && $('[name="score"]').val() == ''){
                        util.mobile_alert('请输入奖励积分');
                        return false;
                    }
                    if($('[name="coupon_status"]').is(':checked') && $('[name="coupon_ids"]').val() == ''){
                        util.mobile_alert('请选择奖励优惠券');
                        return false;
                    }
                    if(!$('[name="score_status"]').is(':checked')){
                        $('[name="score"]').val(0);
                    }
                    if(!$('[name="coupon_status"]').is(':checked')){
                        $('[name="coupon_ids"]').val('');
                    }
                } else {
                    $('[name="start_time"]').val('');
                    $('[name="end_time"]').val('');
                    $('[name="score"]').val(0);
                    $('[name="coupon_ids"]').val('');
                }
                $('#form1').submit();
                // util.ajax_json('',function(d){
                //     console.log(d)
                // },data);
            })
        })
    </script>
<#include "/admin/footer.ftl">