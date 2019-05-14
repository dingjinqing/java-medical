$('.template_footer').width($('.template_info').width());
//限制字数
function limiWord(obj,num) {
    // var len = 0;
    // for (var i=0; i<$(obj).val().length; i++) {
    //     if ($(obj).val().charCodeAt(i)>127 || $(obj).val().charCodeAt(i)==94) {
    //         len += 2;
    //     } else {
    //         len ++;
    //     }
    // }
    // console.log(len);
    if($(obj).val().length > num){
        $(obj).val( $(obj).val().substring(0,num) );
    }
    $(obj).next().find('.news_num').text( $(obj).val().length ) ;
    if(num == 50){ // textarea的
        if($(obj).val().length > 0){
            $('.add_template').show();
        }else{
            $('.add_template').hide();
        }
    }
}
$('input[name="name"],input[name="title"]').focus(function () {
    $(this).parent().css('border-color','#5a8bff');
});
//value为空 出现红框 input
function blurBlock(obj,title) {
    if($(obj).val() == ''){
        $(obj).parents('.tem_right').find('.right_error').text(title);
        $(obj).parent().css('border-color','#f66');
    }else{
        $(obj).parents('.tem_right').find('.right_error').text('');
        $(obj).parent().css('border-color','#dbdbdb');
    }
}
$('input[name="name"]').blur(function () {
    blurBlock(this,'请填写消息名称');
});
$('input[name="title"]').blur(function () {
    blurBlock(this,'请填写业务标题');
    if($(this).val() != ''){
        $('.left_tem_title').html($(this).val());//左侧内容的显示
    }
});
$('textarea[name="content"]').blur(function () {
    if($(this).val() == ''){
        $(this).parents('.tem_right').find('.right_error').text('请填写业务内容');
        $(this).parents('.choose_template').css('border-color','#f66');
    }else{
        $(this).parents('.tem_right').find('.right_error').text('');
        $(this).parents('.choose_template').css('border-color','#dbdbdb');
        $('.left_tem_content').find('.fr').html($(this).val());//左侧内容的显示
    }
});
//添加会员卡
$('.add_card').click(function () {
    var card_clone = $('.card_choose').children().clone();
    $('.screen_list_card').append(card_clone);
});
$('.screen_list_card').on('click','.reduce_card',function () {
    $(this).parent().remove();
});
$('.add_label').click(function () {
    var card_clone = $('.label_choose').children().clone();
    $('.screen_list_label').append(card_clone);
});
$('.screen_list_label').on('click','.reduce_label',function () {
    $(this).parent().remove();
});
//选择模板
$('.tem_right_content').click(function () {
    var action = $('input[name="action"]').val();
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择内容模板', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['615px', '325px']
            , content: '/admin/market/message/template/defineList'
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , yes: function (index) {
                var iframe = layer.getChildFrame('body', index);
                var check_html = iframe.find('input[name="tem_list"]:checked').next().html();
                $('textarea[name="content"]').val(check_html);
                $('.left_tem_content').find('.fr').html(check_html);//左侧内容的显示
                $('textarea[name="content"]').next().find('.news_num').text(check_html.length);
                layer.close(index);
            }
        });
    });
});

$('.save').click(function () {
    blurBlock('input[name="name"]','请填写消息名称');
    blurBlock('input[name="title"]','请填写业务标题');
    if($('textarea[name="content"]').val() == ''){
        $('textarea[name="content"]').parents('.tem_right').find('.right_error').text('请填写业务内容');
        $('textarea[name="content"]').parents('.choose_template').css('border-color','#f66');
        return;
    }
    if($('input[name="name"]').val() == ''){
        return;
    }
    if($('input[name="title"]').val() == ''){
        return;
    }
    
    if ($('input[name="send_action"]:checked').val() == 2) {
        if ($('input[name="start_time"]').val() == '' ||
            $('input[name="end_time"]').val() == '') {
            util.mobile_alert(' 持续发送请选择日期区间');
            return false;
        }
        if ($('input[name="start_time"]').val() >= $('input[name="end_time"]').val()) {
            util.mobile_alert('起始日期不能大于终止日期');
            return false;
        }
    }
    if ($('input[name="send_action"]:checked').val() == 3 && $('input[name="task_start_time"]').val() == '') {
        util.mobile_alert('定时发送请选择发送日期');
        return false;
    }

    if ($('input[name="point_start_time"]').val() || $('input[name="point_end_time"]').val()) {
        if (!$('input[name="point_start_time"]').val()) {
            util.mobile_alert('请选择指定时间内有登录记录开始日期');
            return false;
        }

        if (!$('input[name="point_end_time"]').val() ) {
            util.mobile_alert('请选择指定时间内有登录记录结束日期');
            return false;
        }
        
    }

    if($('#join-person').find('input[type="checkbox"]:checked').length < 1){
        util.mobile_alert("请至少选择一种类型发送人群");
        return false;
    }

    //指定购买商品
    if ($('input[name="goods_box"]:checked').val() == 1) {
        if (!$('input[name="goods_ids"]').val()) {
            util.mobile_alert('请选择指定商品');
            return false;
        }
    }

    //持有会员卡
    if ($('input[name="card_box"]:checked').val() == 1) {
        if (!$('input[name="card_list"]').val()) {
            util.mobile_alert('请选择会员卡');
            return false;
        }
    }

    //属于标签
    if ($('input[name="tag_box"]:checked').val() == 1) {
        if (!$('input[name="tag_list"]').val()) {
            util.mobile_alert('请选择标签');
            return false;
        }
    }

    //指定会员
    if ($('input[name="member_box"]:checked').val() == 1) {
        if (!$('input[name="send_member"]').val()) {
            util.mobile_alert('请选择指定会员');
            return false;
        }
    }


    hasSaved = true;
    layer.ready(function () {
        layer.msg('保存成功', {time: 2000},function () {
            $("#form1").submit();
        });
    });
});

$('.add_template').on('click', function () {
    var action = $('select[name="action"]').val();
    var content = $('textarea[name="content"]').val();
    util.ajax_json('/admin/market/message/template/list', function (response) {
        if (response.error == 0) {
            util.mobile_alert('添加模板成功');
        }
    }, {act: "add_template", action: action, content: content});
});

$(document).on('click', '.tem_right_link',function () {
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择链接', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['800px','430px']
            , content: '/admin/frame/decoration/link?bottom=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function (index) {
                var iframe = layer.getChildFrame('body', index);
                var link = iframe.contents().find('tr[data-back="false"]').find(".link").text();
                $('.tem_right_link').html(link);
                $('.tem_right_link').css("width","auto");
                $('[name="page_link"]').val(link);
                layer.close(index);
            }
        });
    });
});
function loadUserList(paramJson) {
    var user_id = $('[name="user_id"]').val() != '' ? $('[name="user_id"]').val().split(',') : [];
    var record_select_value = $('#record_select_value').val() != '' ? $('#record_select_value').val().split(',') : [];
    util.ajax_json('/admin/frame/user/getUserMember?is_show_page=1', function (response) {
        util.loadPage(response.content.list.total, response.content.list.current_page, function (res) {
            paramJson.page = res.curr;
            loadUserList(paramJson);
        }, 'pageshow', 15)
        var list = response.content.list.data, html = '';
        $('#user').val(response.content.all_can_send_user);

        for (var i in list) {
            var template_table = $('#template_table').clone();
            template_table.find('tr').attr('user_id', list[i].user_id);
            template_table.find('tr td').eq(0).find('input').val(list[i].user_id);
            template_table.find('tr td').eq(1).html(list[i].user_id);
            template_table.find('tr td').eq(2).html(list[i].username);
            template_table.find('tr td').eq(3).html(list[i].mobile);
            template_table.find('tr td').eq(4).html(list[i].form_id_count ? list[i].form_id_count : 0);
            if ($.inArray(list[i].user_id, response.content.officialAccountUser) === -1) {
                if (isNaN(parseInt(list[i].form_id_count)) || parseInt(list[i].form_id_count) <= 0) {
                    template_table.find('tr td').eq(0).find('input').attr('disabled', true);
                }
                template_table.find('tr td').eq(5).html('否');
            } else {
                template_table.find('tr td').eq(5).html('是');
            }
            if ($.inArray(list[i].user_id.toString(), record_select_value) > -1) {
                template_table.find('tr td').eq(0).find('input').attr('checked', true);
                /*if ($.inArray(list[i].user_id.toString(), record_select_value) === -1) {
                    record_select_value.push(list[i].user_id);
                }*/
            }
            html += template_table.html();
        }

        if(list.length < 1){
            $('.no_data_style').show()
            $('#pageshow').hide()
        } else {
            $('.no_data_style').hide()
            $('#pageshow').show()
        }
        
        $('#set-goods tbody').html(html);
        $('#record_select_value').val(record_select_value.join(','));
        if ($('#record_select_value').val() != '' && $('#user').val() != '' &&
            $('#record_select_value').val().split(',').length === $('#user').val().split(',').length) {
            $('[name="check_all"]').prop('checked', true);
        } else {
            $('[name="check_all"]').prop('checked', false);
        }
    }, paramJson);
}
function getSearchParam() {
    var cart_box = 0;
    if ($('input[name="cart_box"]:checked').val() == 1) 
        cart_box = 1;

    var card_id = '', tag_id = '', send_member='',goods_ids='';
    if ($('input[name="card_box"]:checked').val() == 1) 
        card_id = $("input[name='card_list']").val();
    
    if ($('input[name="tag_box"]:checked').val() == 1) 
        tag_id = $("input[name='tag_list']").val();

    if ($('input[name="member_box"]:checked').val() == 1) 
        send_member = $("input[name='send_member']").val();

    if ($('input[name="goods_box"]:checked').val() == 1) 
        goods_ids = $("input[name='goods_ids']").val();

    var have_pay = '', no_pay = '',  min_count = '',  max_count = '';
    var min_ave_price = '', max_ave_price = '';
    var point_start_time = '', point_end_time = '';
    if ($('input[name="custom_box"]:checked').val() == 1) {
        have_pay = $('input[name="have_pay"]').val();
        no_pay = $('input[name="no_pay"]').val();
        min_count = $('input[name="min_count"]').val();
        max_count = $('input[name="max_count"]').val();
        min_ave_price = $('input[name="min_ave_price"]').val();
        max_ave_price = $('input[name="max_ave_price"]').val();
        point_start_time = $('input[name="point_start_time"]').val();
        point_end_time = $('input[name="point_end_time"]').val();
    }
    paramJson = {};
    paramJson.cart_box = cart_box;
    paramJson.card_id = card_id;
    paramJson.tag_id = tag_id;
    paramJson.have_pay = have_pay;
    paramJson.no_pay = no_pay;
    paramJson.min_count = min_count;
    paramJson.max_count = max_count;
    paramJson.min_ave_price = min_ave_price;
    paramJson.max_ave_price = max_ave_price;
    paramJson.is_click_search = 0;
    paramJson.user = send_member;
    paramJson.goods_ids = goods_ids;
    return paramJson;
}
$(document).on('click', '.look_people', function () {
    var paramJson = getSearchParam();
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['选择用户', 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: ['800px', '500px']
            , content: $('#set-goods')
            , btn: ['确定', '取消']
            , btnAlign: 'r'
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                $('#set-goods').removeClass('hide');
                $('input[name="id"]').val('');
                $('input[name="username"]').val('');
                $('input[name="mobile"]').val('');
                $('input[name="is_focusing_official"]').val('');
                $('#record_select_value').val($('[name="user_id"]').val());
                loadUserList(paramJson);
            }
            ,yes: function (index) {
                $('[name="user_id"]').val($('#record_select_value').val());
                if ($('#record_select_value').val() != '')  {
                    $('.user_count').html($('#record_select_value').val().split(',').length);
                } else {
                    $('.user_count').html(0);
                }
                layer.close(index);
            }
        });
    });

})

//选择标签
var label_arry = [];
$(".tag_id").change(function(){
    var label_name = $(this).children('option:selected').text();
    var op_val = $(this).children('option:selected').attr('value');
    if(label_arry.length == 0){
        $('.label-info').show();
    }
    label_arry.push(op_val);
    $('input[name="tag_list"]').val(label_arry.join(','));
    var img = ' <img src="/image/admin/icon_delete.png" alt="" class="label-delete"  />'
    var span =' <span class="label_span">';
    var inner_html = span + '<span value="'+op_val+'">'+ label_name + '</span>' + img + '</span>';
    $(this).parent().find(".label-info-row").append(inner_html);
    $(this).children('option:selected').remove();
    if ($('input[name="tag_box"]:checked').val() == 1) getUserMember();
});
//删除标签
$('.label-info-row').on('click','.label-delete',function(){
    var op_name = $(this).parent().html();
    var opp_val = $(this).prev().attr("value");
    $(this).parent().remove();
    var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
    $('.tag_id').append(op_html);
    label_arry.splice($.inArray(opp_val,label_arry),1);
    $('input[name="tag_list"]').val(label_arry.join(','));
    if(label_arry.length == 0){
        $('.label-info').hide();
    }
    if ($('input[name="tag_box"]:checked').val() == 1) getUserMember();
});

//选择会员卡
var card_arry = [];
$(".card_id").change(function(){
    var card_name = $(this).children('option:selected').text();
    var op_val = $(this).children('option:selected').attr('value');
    if(card_arry.length == 0){
        $('.card-info').show();
    }
    card_arry.push(op_val);
    $('input[name="card_list"]').val(card_arry.join(','));
    var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
    var span =' <span class="card_span">';
    var inner_html = span + '<span value="'+op_val+'">'+ card_name + '</span>' + img + '</span>';
    $(this).parent().find(".card-info-row").append(inner_html);
    $(this).children('option:selected').remove();
    if ($('input[name="card_box"]:checked').val() == 1) getUserMember();
});
//删除会员卡
$('.card-info-row').on('click','.card-delete',function(){
    var op_name = $(this).parent().html();
    var opp_val = $(this).prev().attr("value");
    $(this).parent().remove();
    var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
    $('.card_id').append(op_html);
    card_arry.splice($.inArray(opp_val,card_arry),1);
    $('input[name="card_list"]').val(card_arry.join(','));
    if(card_arry.length == 0){
        $('.card-info').hide();
    }
    if ($('input[name="card_box"]:checked').val() == 1) getUserMember();
});

$('.goods_list').on('click','.img-delete',function(){
    let goods_id = $(this).attr('goods_id');
    let goods_ids = $('input[name="goods_ids"]').val().split(',');
    let index = goods_ids.indexOf(goods_id)
    if(index != -1){
        goods_ids.splice(index,1)
        $(this).parent().remove();
        $('input[name="goods_ids"]').val(goods_ids);
        if ($('input[name="goods_box"]:checked').val()==1) getUserMember();      
    }
    if($('.goods_list li').length < 4){
        $('.add_goods').show();
    }
})

//选择自定义
$("#choose_condition").change(function(){
    var that = $(this);
    var op_val = that.children('option:selected').attr('value');
    var parent = $(".choose_content");
    var el = $(".clone").clone(true);

    if(op_val !== 0){
        el.find('div').each(function(idx,em){
            if($(this).attr('value') == op_val){
                let cl = $(this).clone(true);
                parent.append(cl);
            }
        })
    }
    that.children('option:selected').remove();
});
//删除自定义
$('.choose_content').on('click','.del',function(){
    var parent = $(this).parent();
    var op_val = parent.attr('value');
    var name = $.trim(parent.find('span:eq(0)').text());
    var p = $('#choose_condition');
    var ul = $(".choose_content");
    name = name.substring(0,name.length -1);
    var cl = ` <option value="${op_val}">${name}</option>`;
    parent.remove();
    p.append(cl);
    if ($('input[name="custom_box"]:checked').val() == 1) {
        getUserMember();
    }
})

//添加会员
$('.add_member').click(function(){
    var that = $(this);
    layui.use('layer', function(){
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type:2,
            title: ['添加用户', 'text-align:center;padding: 0px;'],
            offset: 'auto',
            area: ['900px','450px'],
            content: '/admin/frame/user/select',
            btn: ['确定', '取消'],
            btnAlign: 'r' ,
            shade: [0.3, '#000'],
            yes:function(index, layero){
                var body = layer.getChildFrame('body', index);
                var check_choice = body.find('#check_choice').val();
                $("input[name='send_member']").val(check_choice);
                if ($('input[name="member_box"]:checked').val() == 1) getUserMember();
                var number = 0;
                if (check_choice) number = check_choice.split(',').length;
                that.next().text(number);
                layer.close(index);
            }
        })
    })
})

