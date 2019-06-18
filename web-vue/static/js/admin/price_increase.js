//添加换购规则
$('.con_set_rule').on('click','.add_rule',function () {
    var list_len = $('.con_set_rule').find('.rule_list').length;
    //console.log(list_len);
    var list_clone = $('.rule_clone').children().clone();
    if(list_len == 1){
        list_clone.find('.fl').find('span').text('2');
        list_clone.find('.add_rule').css('display','inline-block');
        $('.rule_list1').find('.add_rule').hide();
        list_clone.addClass('rule_list2');
        $('.rule_list1').after(list_clone);
    }
    if(list_len == 2){
        list_clone.find('.add_rule').hide();
        $('.add_rule').hide();
        if($('.rule_list2').find('.fl').find('span').text() == 2){
            list_clone.find('.fl').find('span').text('3');
            $('.rule_list2').after(list_clone);
        }else{
            list_clone.find('.fl').find('span').text('2');
            $('.rule_list1').after(list_clone);
        }

    }
});
//删除规则
$('.con_set_rule').on('click','.del_rule',function () {
    var list_len = $('.con_set_rule').find('.rule_list').length;
    console.log(list_len);
    var rule_num = $(this).parent().prev().find('span').text();
    if(list_len == 2){
        $(this).parent().parent().prev().find('.add_rule').show();
    }
    if(list_len == 3){
        if(Number(rule_num) == 2){
            $(this).parent().parent().next().find('.add_rule').show();
            $(this).parent().parent().next().find('span').text(2);
        }
        if(Number(rule_num) == 3){
            $(this).parent().parent().prev().find('.add_rule').show();
        }
    }
    $(this).parent().parent().remove();
});

var rule_arr = [];

//下一步  准备去设置主商品
$('.next1').click(function () {
    if($('input[name="name"]').val() == ''){
        $('input[name="name"]').focus();
        util.mobile_alert('请输入活动名称');
        return;
    }
    if($('input[name="level"]').val() == ''){
        $('input[name="level"]').focus();
        util.mobile_alert('请输入活动优先级');
        return;
    }
    if($('input[name="start_time"]').val() == ''){
        $('input[name="start_time"]').focus();
        util.mobile_alert('请输入活动时间');
        return;
    }
    if($('input[name="end_time"]').val() == ''){
        $('input[name="end_time"]').focus();
        util.mobile_alert('请输入活动时间');
        return;
    }

    if ($('input[name="start_time"]') > $('input[name="end_time"]')) {
        util.mobile_alert('开始时间不能大于结束时间!');
        return false;
    }
    var msg = '', prev_value = 0;
    $('.con_set_rule  input[name="full_price[]"]').each (function (index, item) {
        console.log(parseFloat($(item).val()));
        if ($(item).val() == '' || parseFloat($(item).val()) <= 0) {
            msg = '规则设置不合法，请检查!';
            $(item).focus();
            return false;
        }
        if (parseFloat($(item).val()) < prev_value) {
            msg = '规则设置不合法，请检查!';
            $(item).focus();
            return false;
        }
        prev_value = parseInt($(item).val());
    });
    $('.con_set_rule  input[name="purchase_price[]"]').each (function (index, item) {
        if ($(item).val() == '' || parseFloat($(item).val()) <= 0) {
            msg = '规则设置不合法，请检查!';
            $(item).focus();
            return false;
        }
    });
    if (msg != '') {
        util.mobile_alert(msg);
        return false;
    }
    // if($('input[name="max_change_purchase"]').val() == ''){
    //     $('input[name="max_change_purchase"]').focus();
    //     util.mobile_alert('请输入最大换购数');
    //     return;
    // }
    // var a = 0;
    // var _this;
    // $('.con_set_rule').find('.rule_list').find('input').each(function (i,v) {
    //    if($(v).val() == ''){
    //        a = 1;
    //        _this = $(v);
    //        return false;
    //     }
    // });
    // if(a==1){
    //     _this.focus();
    //     util.mobile_alert('请输入换购规则');
    //     return false;
    // }
    //添加换购规则
    rule_arr = [];
    $('.con_set_rule').find('.rule_list').each(function(key,val){
        var rule_obj = {};
        rule_obj.id = $(val).find('.fl').find('span').text();
        rule_obj.full_price = $(val).find('input[name="full_price[]"]').val();
        rule_obj.purchase_price = $(val).find('input[name="purchase_price[]"]').val();
        rule_arr.push(rule_obj)
    });
    console.log(rule_arr);
    $('.set_main_goods').addClass('blue_back');
    $('.con_set_rule').hide();
    $('.set1').hide();
    $('.set2').css('display','inline-block');
    $('.con_set_main').show();
});
//下一步  去设置换购商品
$('.next2').click(function () {
    if ($('input[name="goods_id"]').val() == '') {
        util.mobile_alert('请选择主商品');
        return false;
    }
    //判断有几个规则并显示
    var rule_len = rule_arr.length;
    for(var i in rule_arr){
        $('.change_rule').each(function (k, v) {
           var _val = $(this).attr('val');
           if(_val == rule_arr[i].id){
               $(this).show();
               $('.change_con_goods'+rule_arr[i].id).find('.span1').text(rule_arr[i].full_price);
               $('.change_con_goods'+rule_arr[i].id).find('.span2').text(rule_arr[i].purchase_price);
           }
        });
    }

    //
    $('.con_set_main').hide();
    $('.con_set_change').show();
    $('.set_change_goods').addClass('blue_back1');
    $('.set2').hide();
    $('.set3').css('display','inline-block');
});

//上一步  回到设置规则页面
$('.prev1').click(function () {
    $('.set_main_goods').removeClass('blue_back');
    $('.con_set_rule').show();
    $('.set2').hide();
    $('.set1').css('display','inline-block');
    $('.con_set_main').hide();
});

//上一步  回到设置主商品页面
$('.prev2').click(function () {
    $('.con_set_main').show();
    $('.con_set_change').hide();
    $('.set_change_goods').removeClass('blue_back1');
    $('.set3').hide();
    if (is_show_save) $('.save').css('display','inline-block');
    $('.set2').css('display','inline-block');
});


//切换规则
$('.con_set_change').on('click','.change_rule',function () {
    var _val = $(this).attr('val');
    $('.change_rule').removeClass('change_active');
    for(var i in rule_arr){
        $('.change_rule').each(function (k, v) {
            var _val = $(this).attr('val');
            if(_val == rule_arr[i].id){
                $(this).show();
            }
        });
    }
    $(this).addClass('change_active');
    $('.change_con_goods').hide();
    $('.change_con_goods'+_val).show();
});


//添加换购商品
$('.add_change_goods').click(function(){
    var is_spec_goods = $(this).attr('item') == 1 ? 0 : 1;
    var change_active = $('.change_active').attr('val');
    var record_select_value = $(this).attr('item') == 1 ? $('input[name="goods_id"]').val() : $('input[name="product_id['+change_active+']"]').val();
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','450px']
            , content: '/admin/public/purchase/goods/list?is_spec_goods='+is_spec_goods+'&record_select_value='+record_select_value //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
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
                if (body.find('#record_select_value').val().split(',').length > 20) {
                    util.mobile_alert('最多选择20个商品');
                    return false;
                }
                util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                    var list = response.content;
                    var html = '';
                    for (var i in list) {
                        list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                        html += '<tr>' +
                            '        <td>' +
                            '            <div class="goods_img">' +
                            '                <img src="'+list[i].goods_img+'" />' +
                        '            </div>' +
                        '            <div class="goods_info">' +
                        '                <div class="goods_name">'+list[i].goods_name+'</div>' +
                        '                <div class="goods_spec">'+list[i].prd_desc+'</div>' +
                        '            </div>' +
                        '        </td>';
                        if (is_spec_goods == 1) {
                            html += '<td>￥'+list[i].prd_price+'</td>' +
                                    '<td>'+list[i].prd_number+'</td>' +
                                    '<td><a href="##" item="'+list[i].prd_id+'" class="change_goods_del">删除</a></td>';
                        } else {
                            html += '<td>￥'+list[i].shop_price+'</td>' +
                                '<td>'+list[i].goods_number+'</td>' +
                                '<td><a href="##" item="'+list[i].goods_id+'" class="goods_del">删除</a></td>';
                        }
                        html += '</tr>';
                    }
                    if (is_spec_goods == 1) {
                        $('input[name="product_id['+change_active+']"]').val(checkedId);
                        $('.change_con_goods'+change_active).find('tbody').html(html);
                    } else {
                        $('input[name="goods_id"]').val(checkedId);
                        $('#main_goods_list').html(html);
                    }
                    layer.close(index);
                }, {is_spec_goods: is_spec_goods,  select_id: body.find('#record_select_value').val()})

            }, btn2: function (index, layero) {
                //按钮取消的回调

            }
        });
    });
});
$(document).on('click', '.goods_del', function () {
    var goods_id = $('input[name="goods_id"]').val();
    var goodIds = goods_id.split(',');
    var index = $.inArray($(this).attr('item'), goodIds);
    if (index >= 0) {
        goodIds.splice(index, 1);
    }
    $('input[name="goods_id"]').val(goodIds.join(','));
    $(this).parent().parent().remove();
});
$(document).on('click', '.change_goods_del', function () {
    var change_active = $('.change_active').attr('val');
    var product_id = $('input[name="product_id[' + change_active + ']"]').val();
    var productIds = product_id.split(',');
    var index = $.inArray($(this).attr('item'), productIds);
    if (index >= 0) {
        productIds.splice(index, 1);
    }
    $('input[name="product_id[' + change_active + ']"]').val(productIds.join(','));
    $(this).parent().parent().remove();
});
$('.save').click(function () {

    for (var i in rule_arr) {
        if ($('input[name="product_id['+rule_arr[i].id+']"]').val() == '') {
            msg = '请选择换购商品！';
            util.mobile_alert(msg);
            return false;
        }
    }
    hasSaved = true;
    $('#form1').submit();
});