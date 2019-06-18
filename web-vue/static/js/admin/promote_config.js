//选择商品
$('.el_right').on('click','.choose_goods',function(){
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        var checkedId1 = $('input[name="recommend_goods_id"]').val();
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','430px']
            , content: '/admin/public/purchase/goods/list?is_spec_goods=1&is_single=1&record_select_value='+checkedId1 //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {

            }
            , yes: function (index, layero) { //保存按钮的回调
                var body = layer.getChildFrame('body', index);
                var checkedId = body.find('#record_select_value').val();
                if (body.find('#record_select_value').val() == '') {
                    util.mobile_alert('请选择商品');
                    return false;
                }
                if (body.find('#record_select_value').val().split(',').length > 1) {
                    util.mobile_alert('最多选择1个商品');
                    return false;
                }
                var tr = body.find('.goods_tr_choose');
                
                var goods_name = tr.find('.goods_info').find('.goods_name').eq(0).text();
                var spec_value = tr.find('.goods_info').find('.goods_name').eq(1).text();
                $('input[name="goods_ids"]').val(tr.attr('goods_id'));
                $('input[name="reward_ids"]').val(body.find('#record_select_value').val());
                $('.goods_img img').attr('src',tr.find('.goods_info').find('img').attr('src'));
                $('.goods_name .name').text(goods_name);
                $('.goods_spec').text(spec_value);
                $('.goods_list td').eq(1).text('￥'+tr.children('td').eq(2).text());
                $('.goods_list td').eq(2).text(tr.children('td').eq(3).text());
                $('.goods_info_list').removeClass('hide');
                $('.coupon_info_table').addClass('hide');
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});
//添加参与优惠券
$(".el_right").on('click','.card_add_click',function(){
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
                    iframe.find('.coupon_list').removeClass('card_list_active');
                    $(this).addClass('card_list_active');
                });
            }
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                var list_active = iframe.find('.card_list_active');
                if($(list_active).size() == 0){
                    util.mobile_alert('请选择优惠券');
                    return;
                }
                if($(list_active).size() > 1){
                    util.mobile_alert('最多只能选择1张优惠券哦');
                    return;
                }

                $('input[name="reward_ids"]').val(list_active.find('.coupon_info').attr('coupon_id'));
                $('.coupon_info_table .coupon_name').text(list_active.find('.coupon_name').text());
                $('.coupon_info_table .coupon_money').html(list_active.find('.coupon_list_top').text());
                $('.coupon_info_table .coupon_limit').text(list_active.find('.coupon_center_limit').text());
                
                $('.coupon_info_table').removeClass('hide');
                $('.goods_info_list').addClass('hide');
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});
//删除优惠券
$('.coupon_div').on('click','.coupon_del',function () {
    $(this).parent().remove();
});
//上传分享图片
$('.share_img_area').on("click",".add_img",function() {
    var el = $(this).parent();
    var w = 800;
    var h = 800;
    $.jImageManager({
        img_width: w,
        img_height: h,
        ok_cb: function (img_arr) {
            $("input[name='custom_img_path']").val(img_arr[0].path);
            $(".add_img").attr('src',img_arr[0].url);
            $(".good_img_delete").show();
            hasSaved = false;
        }
    });
});

//删除自定义分享图片
$('.good_img_delete').click(function () {
    $("input[name='custom_img_path']").val('');
    $(".add_img").attr('src',$(".add_img").attr('default_img'));
    $(".good_img_delete").hide();
});

//奖励类型显示不同
$('input[name="reward_type"]').change(function () {
    if($(this).val() == 2){
        $('.choose_goods').css('display','none');
        $('.coupon_div').css('display','block');

        $('.promote_share').find('img').attr('src',$('.promote_share').attr('voucher_img'));
        $('.promote_pictorial').find('img').attr('src',$('.promote_pictorial').attr('voucher_img'));
    }else{
        $('.choose_goods').css('display','block');
        $('.coupon_div').css('display','none');

        $('.promote_share').find('img').attr('src',$('.promote_share').attr('goods_img'));
        $('.promote_pictorial').find('img').attr('src',$('.promote_pictorial').attr('goods_img'));
    }

    if ($(this).val() == 1) {
        $('.market_price').removeClass('hide');
    }else if($(this).val() == 0){
        $('.market_price').addClass('hide');
    }
     if($(this).val() == 1){
        $(".only_disconnt").css("display","block");
     }else{
         $(".only_disconnt").css("display","none");
     }
})
// //活动分享的样式
// $('input[name="activity_share_type"]').change(function () {
//     if($(this).val() == 0){
//         $('.own_config').css('display','none');
//     }else{
//         $('.own_config').css('display','block');
//     }
// })
// //分享图的类型
// $('input[name="share_img_type"]').change(function () {
//     if($(this).val() == 0){
//         $('.share_img_area').css('display','none');
//     }else{
//         $('.share_img_area').css('display','block');
//     }
// })

//失败赠送类型
$('input[name="failed_send_type"]').change(function () {
    if($(this).val() == 0){
        $('.failed_send_coupon').addClass('hide');
        $('.failed_send_score').addClass('hide');
        $('.coupon_list_clone').addClass('hide');
    }else{
        var act_type = $("input[name='act']").val();
        if ($(this).val() == 1) {
            if (act_type == 'add') {
                $('.failed_send_coupon').removeClass('hide');
            }else{
                $('.coupon_list_clone').removeClass('hide');
            }
            $('.failed_send_score').addClass('hide');
        }else{
            if (act_type == 'add') {
                $('.failed_send_coupon').addClass('hide');
            }else{
                $('.coupon_list_clone').addClass('hide');
            }
            $('.failed_send_score').removeClass('hide');
        }
    }
})

//添加失败赠送优惠券
$(".el_right").on('click','.failed_send_click',function(){
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
                    iframe.find('.coupon_list').removeClass('card_list_active');
                    $(this).addClass('card_list_active');
                });
            }
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                var list_active = iframe.find('.card_list_active');
                if($(list_active).size() == 0){
                    util.mobile_alert('请选择优惠券');
                    return;
                }
                if($(list_active).size() > 1){
                    util.mobile_alert('最多只能选择1张优惠券哦');
                    return;
                }

                $('input[name="failed_send_coupon"]').val(list_active.find('.coupon_info').attr('coupon_id'));
                $('.failed_send_coupon').html('');
                $(list_active).each(function (i) {
                    var coupon_clone = $('.coupon_list_clone').find('.coupon_list')
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
                    $('.coupon_list_clone').removeClass('hide');
                });
                hasSaved = false;
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});

//保存验证
$('.btn_save').click(function () {
    if($("input[name='act_name']").val() == ''){
        util.mobile_alert('活动名称不能为空');
        return false
    }
    if($("input[name='start_time']").val() == ''){
        util.mobile_alert('请选择活动有效期');
        return false
    }
    if($("input[name='end_time']").val() == ''){
        util.mobile_alert('请选择活动有效期');
        return false
    }
    if($("input[name='reward_duration']").val() == ''){
        util.mobile_alert('奖励有效期不能为空');
        return false
    }
    if($('input[name="promote_amount"]').val() == ''){
        util.mobile_alert('所需助力值不能为空');
        return false
    }
    if($('input[name="promote_times"]').val() == ''){
        util.mobile_alert('所需助力次数不能为空');
        return false
    }
    if($('input[name="lanuch_limit_duration"]').val() == ''){
        util.mobile_alert('发起次数限制不能为空');
        return false
    }
    
    if($('.share_add_times').val() == ''){
        util.mobile_alert('分享所增加助力机会不能为空');
        return false
    }

    var reward_type = $('input[name="reward_type"]:checked').val();
    if ( reward_type == 0 || reward_type ==1) {
        var g_market_store = $('input[name="g_market_store"]').val();
        if (!g_market_store) {
            util.mobile_alert('活动库存不能为空');
            return false
        }

        var goods_store = parseInt($('.goods_store').text());
        if (g_market_store > goods_store) {
            util.mobile_alert('活动数量不能超过商品库存');
            return false
        }
        var market_price = $('input[name="market_price"]').val();
        if (reward_type == 1 && !market_price) {
            util.mobile_alert('活动价不能为空');
            return false
        }
    }



    var param = {};
    param = $("#form1").serializeArray();
    util.ajax_json('/admin/market/promote/save',function (d) {
        if (d.error == 0) {
            util.mobile_alert(d.content)
            setTimeout(function () {
                location.href='/admin/market/promote/list';
            }, 1000);
        }else{
            util.mobile_alert(d.message);
        }
    },param);

})


$(".view_act_rules").click(function(){
    layui.use('layer', function() {
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['好友助力活动规则', 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: ['614px','auto']
            , content: $('.act_rules')
            , btnAlign: 'c' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                $('.act_rules').show()
            },cancel:function(){
                $('.act_rules').hide()
            }
        });
    });
})

$(".view_promote_rules").click(function(){
    layui.use('layer', function() {
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['随机助力值规则', 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: ['744px','auto']
            , content: $('.promote_rules')
            , btnAlign: 'c' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                $('.promote_rules').show()
            },cancel:function(){
                $('.promote_rules').hide()
            }
        });
    });
})
