$('.set2').click(function(){
    $('.gift_content_2').hide();
    $('.set1').show();
    $('.set2,.set3').hide();
    $('.gift_content_1').show();
    $('.set_rule').removeClass('img_active3');
    $('.set_change_goods').removeClass('img_active4');
})
$('.set1').click(function(){
    var that = $(this);
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
        util.mobile_alert('开始时间不能大于结束时间');
        return false;
    }
    if($('input[name="condition_str"]').val() == ''){
        util.mobile_alert('请选择赠品条件');
        return false;
    }else{

        var arry = $('input[name="condition_str"]').val().split(',');
        var count = 0;
        $(".condition_content li").each(function (idx,item) {
            if($(this).val() == '1'){
                if($(this).find("input").val() == ''){
                    util.mobile_alert('请输入金额');
                    count ++;
                    return false;
                }
            }
            if($(this).val() == '2'){
                if($(this).find("input").val() == ''){
                    util.mobile_alert('请输入件数');
                    count ++;
                    return false;
                }
            }
            if($(this).val() == '3'){
                if($(this).find(".card_span").length == 0){
                    util.mobile_alert('请选择会员卡');
                    count ++;
                    return false;
                }
            }
            if($(this).val() == '4'){
                if($(this).find(".label_span").length == 0){
                    util.mobile_alert('请选择会员标签');
                    count ++;
                    return false;
                }
            }
            if($(this).val() == '5'){
                if($(this).find("input[name='pay_start_time']").val() == ''){
                    $(this).find("input").focus();
                    util.mobile_alert('请输入付款开始时间');
                    count ++;
                    return false;
                }
                if($(this).find("input[name='pay_end_time']").val() == ''){
                    $(this).find("input").focus();
                    util.mobile_alert('请输入付款结束时间');
                    count ++;
                    return false;
                }
            }
            if($(this).val() == '6'){
                if($(this).find("input").val() == ''){
                    $(this).find("input").focus();
                    util.mobile_alert('请输入付款排名');
                    count ++;
                    return false;
                }
            }
            if($(this).val() == '7'){
                if($(this).find("input[name='min_pay_num']").val() == ''){
                    $(this).find("input").focus();
                    util.mobile_alert('请输入购买次数');
                    count ++;
                    return false;
                }
                if($(this).find("input[name='max_pay_num']").val() == ''){
                    $(this).find("input").focus();
                    util.mobile_alert('请输入购买次数');
                    count ++;
                    return false;
                }
            }
            if($(this).val() == '8'){
                if($(this).find("select[name='user_action']").val() == 0){
                    $(this).find("input").focus();
                    util.mobile_alert('请输入用户类型');
                    count ++;
                    return false;
                }
            }
        })
    }
    if(count > 0){
        return false;
    }
    if($("textarea[name='explain']").val() == ''){
        util.mobile_alert('请输入赠品规则');
        return false;
    }
    $(this).hide();
    $('.gift_content_1').hide();
    $('.set2,.set3').show();
    $('.gift_content_2').show();
    $('.set_change_goods').addClass('img_active4');
    $('.set_rule').addClass('img_active3');
})
$('input[name="recommend_type"]').click(function(){
    if($(this).val() == 2){
       $('.choose_goods').css('display','inline-block');
        $('.choose_num').show();
    }else{
        $('.choose_goods').css('display','none');
        $('.choose_num').hide();
    }
});
$("#choose_condition").change(function(){
    var that = $(this);
    var op_val = that.children('option:selected').attr('value');
    var parent = $(".condition_content .basic_ul");
    var el = $(".clone").clone(true);
    var len = parent.children().length;
    var condition_arr = $('[name="condition_str"]').val() != '' ? $('[name="condition_str"]').val().split(',') : [];
    if( len > 2){
        util.mobile_alert('最多只可选择三个条件');
        that.find('option[value="0"]').attr('selected',true);
        return false;
    }
    if(op_val !== 0){
        el.find('li').each(function(idx,em){
            if($(this).attr('value') == op_val){
                condition_arr.push(op_val);
                $('[name="condition_str"]').val(condition_arr.join(','));
                let cl = $(this).clone(true);
                parent.append(cl);
                len ++;
            }
        })
    }
    if( len > 0 && len <= 3){
        parent.show();
    }
    that.children('option:selected').remove();
});
$('.del').click(function () {
   var parent = $(this).parent().parent();
   var op_val = parent.attr('value');
   var name = $.trim(parent.find('.fl').text());
   var p = $('#choose_condition');
   var ul = $(".condition_content .basic_ul");
   var condition_arr = $('[name="condition_str"]').val() != '' ? $('[name="condition_str"]').val().split(',') : [];

   name = name.substring(0,name.length -1);
   var cl = ` <option value="${op_val}">${name}</option>`;
   parent.remove();
   p.append(cl);
   let len = ul.children().length;
   if(len <= 0){
       ul.hide();
   }
   var index = $.inArray(op_val, condition_arr);
   if (index > -1) condition_arr.splice(index, 1);
   $('[name="condition_str"]').val(condition_arr.join(','));
})
var label_arry = [];
$(".label_id").change(function(){
    var label_name = $(this).children('option:selected').text();
    var op_val = $(this).children('option:selected').attr('value');
    if(label_arry.length == 0){
        $('.label-info').show();
    }
    label_arry.push(op_val);
    $('input[name="tag_id"]').val(label_arry.join(','));
    var img = ' <img src="/image/admin/icon_delete.png" alt="" class="label-delete"  />'
    var span =' <span class="label_span">';
    var inner_html = span + '<span value="'+op_val+'">'+ label_name + '</span>' + img + '</span>';
    $(this).parent().find(".label-info-row").append(inner_html);
    $(this).children('option:selected').remove();
});
$('.label-info-row').on('click','.label-delete',function(){
    var op_name = $(this).parent().html();
    var opp_val = $(this).prev().attr("value");
    $(this).parent().remove();
    var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
    $('.label_id').append(op_html);
    label_arry.splice($.inArray(opp_val,label_arry),1);
    $('input[name="tag_id"]').val(label_arry.join(','));
    if(label_arry.length == 0){
        $('.label-info').hide();
    }
});
var card_arry = [];
$(".card_id").change(function(){
    var card_name = $(this).children('option:selected').text();
    var op_val = $(this).children('option:selected').attr('value');
    if(card_arry.length == 0){
        $('.card-info').show();
    }
    card_arry.push(op_val);
    $('input[name="card_id"]').val(card_arry.join(','));
    var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
    var span =' <span class="card_span">';
    var inner_html = span + '<span value="'+op_val+'">'+ card_name + '</span>' + img + '</span>';
    $(this).parent().find(".card-info-row").append(inner_html);
    $(this).children('option:selected').remove();
});
$('.card-info-row').on('click','.card-delete',function(){
    var op_name = $(this).parent().html();
    var opp_val = $(this).prev().attr("value");
    $(this).parent().remove();
    var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
    $('.card_id').append(op_html);
    card_arry.splice($.inArray(opp_val,card_arry),1);
    $('input[name="card_id"]').val(card_arry.join(','));
    if(card_arry.length == 0){
        $('.card-info').hide();
    }
});
$(document).on('click',' .choose_goods', function(e){
    var that = $(this).parent().parent();
    var checkedId1 = that.find('.group_goods_id').val();
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','430px']
            , content: '/admin/public/purchase/goods/list?record_select_value='+checkedId1+'&iframe_only_show='+goods_iframe_only_show  //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var goods_array = [];
                body.find(".goods_item_list .goods_item").each(function(){
                    goods_array.push($(this).attr("goods_id"));
                });
                body.contents().find("tr").each(function(){
                    if($.inArray($(this).attr("goods_id"),goods_array)> -1){
                        $(this).attr('data-back','false').addClass('goods_tr_choose');
                    }
                });
            }
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                var checkedId = iframe.find('#record_select_value').val();
                var count;
                that.find('.group_goods_id').val(checkedId);
                if(checkedId != ''){
                    count = checkedId.split(',').length;
                }else{
                    count = 0;
                }
                if(count != 0){
                    that.find('.choose_num span').text(count);
                }else{
                    util.mobile_alert('请选择商品');
                    that.find('.choose_num span').text(0);
                    return false;
                }
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
                var iframe = layer.getChildFrame('body', index);
                var checkedId = iframe.find('#record_select_value').val();
                if(checkedId == '' && that.find('.group_goods_id').val()== ''){;
                    that.find("#all").prop('checked',true);
                    that.find('.choose_goods,.choose_num').hide();
                }
            },cancel: function (index, layero) {
                //按钮取消的回调
                var iframe = layer.getChildFrame('body', index);
                var checkedId = iframe.find('#record_select_value').val();
                if(checkedId == '' && that.find('.group_goods_id').val()== ''){
                    that.find("#all").prop('checked',true);
                    that.find('.choose_goods,.choose_num').hide();
                }
            }
        });
    });
});

$('.add_change_goods').click(function(){
    var is_spec_goods = $(this).attr('item') == 1 ? 0 : 1;
    var record_select_value = [];
    $('input[name="product_id[]"]').each(function () {
        record_select_value.push($(this).val());
    });
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','450px']
            , content: '/admin/public/purchase/goods/list?is_spec_goods='+is_spec_goods+'&record_select_value='+record_select_value.join(',')//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
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
                            html +='<td>'+list[i].prd_desc+'</td>'+
                                '<td>￥'+list[i].prd_price+'</td>' +
                                '<td class="prd_number">'+list[i].prd_number+'</td>' +
                                '<input type="hidden" name="product_id[]" value="'+list[i].prd_id+'"/>'+
                                '<input type="hidden" name="product_number[]" value="'+list[i].prd_number+'"/>'+
                                '<td>'+ '  <input type="text" class="change-input" onkeyup="value=value.replace(/[^\\d.]/g,\'\')" value="'+list[i].prd_number+'"  />'+
                                '<img src="/image/admin/good_edit.png" class="goods-number-img">'+'</td>' +
                                '<td><a href="##" item="'+list[i].prd_id+'" class="change_goods_del">删除</a></td>' +
                               '</tr>';
                    }
                    $('#main_goods_list').html(html);
                    layer.close(index);
                }, {is_spec_goods: is_spec_goods,  select_id: checkedId})

            }, btn2: function (index, layero) {
                //按钮取消的回调

            }
        });
    });
});
$('.see_').hover(function() {
               $(this).find('img').show();
           }, function() {
               $(this).find('img').hide();
});

$(document).on('click', '.change_goods_del', function () {
    $(this).parent().parent().remove();
});


$(document).on('click', '.save', function () {
    var postData = $('#form1').serializeArray();
    util.ajax_json('/admin/market/gift/add', function (response) {
        if (response.error == 0) {
            util.mobile_alert('保存成功');
            location.href = '/admin/market/gift/list?nav=1';
        } else {
            util.mobile_alert(response.message);
            return false;
        }

    }, $('#form1').serialize())
})
