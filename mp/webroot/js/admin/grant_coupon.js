$(function(){
    $('.refresh').click(function () {
        util.ajax_json("/admin/ajax/coupon",function(res){
            if(res != -1){
                $('select[name="act_id"]').html("");
                var opt = '<option value="">请选择优惠券</option>';
                for(var i=0;i<res.length;i++){
                    opt += "<option value='"+res[i].id+"'>"+res[i].act_name+"</option>"
                }
                $('select[name="act_id"]').html(opt);
                util.mobile_alert("刷新成功");
            }else{
                util.mobile_alert("刷新成功");
            }
        });
    })

    var label_arry = [];
    //选择会员标签
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
        if ($('input[name="tag_box"]:checked').val() == 1)
            getUserMember();
    });
    //删除会员标签
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
        if ($('input[name="tag_box"]:checked').val() == 1)
            getUserMember();
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
        $('input[name="card_id"]').val(card_arry.join(','));
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
        $('input[name="card_id"]').val(card_arry.join(','));
        if(card_arry.length == 0){
            $('.card-info').hide();
        }
        if ($('input[name="card_box"]:checked').val() == 1) getUserMember();
    });

    //删除优惠券
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
    //添加优惠券
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
    
    //删除商品
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
    });

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

    //选择会员
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
    });

    //全选
    $('#set-goods input[name="check_all"]').click(function () {
        var is_checked = $(this).is(':checked');
        if (is_checked) {
            record_select_value = $('#user').val().split(',');
        } else {
            record_select_value = [];
        }
        $('input[name="user_id[]"]:not(:disabled)').prop('checked', is_checked);
        $('#record_select_value').val(record_select_value.join(','));
    });

    $('.btn_search').click(function () {
        var paramJson = getSearchParam();
        paramJson.is_click_search = 1;
        paramJson.page = 1;
        paramJson.id = $('input[name="id"]').val();
        paramJson.username = $('input[name="username"]').val();
        paramJson.mobile = $('input[name="mobile"]').val();
        loadUserList(paramJson);
    })

    /*表单验证*/
    $(".btn_grant a").click(function () {
        /*值不为空*/
        if($("input[name='act_name']").val() == ""){
            util.mobile_alert("活动名称不能为空");
            return false;
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
            if (!$('input[name="card_id"]').val()) {
                util.mobile_alert('请选择会员卡');
                return false;
            }
        }

        //属于标签
        if ($('input[name="tag_box"]:checked').val() == 1) {
            if (!$('input[name="tag_id"]').val()) {
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

        /*值不为空*/
        if($("input[name='coupon_ids']").val() == ""){
            util.mobile_alert("请选择优惠券");
            return false;
        }

        if($('.send_time').find('input[type="radio"]:checked').length < 1){
            util.mobile_alert("请选择发送时间");
            return false;
        }
        if($('.send_time').find('input[type="radio"]:checked').val() == 2){
            if($('#sendDate').val() == ""){
                util.mobile_alert("请填写发送时间");
                return false;
            }
        }
        
        $("#form1").submit();
    });

    $(".fix_footer").outerWidth($('.fix_every_footer').width());
    //版本控制
    getPowerInfo('main_config','coupon_grant','sub_4','定向发券',0);
    
})

function picker(){
    return WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
}

function getUserMember(cb) {
    var param = getSearchParam();
    util.ajax_json('/admin/frame/user/getUserMember',function (d) {
        if (d.error == 0) {
            $('input[name="user_id"]').val(d.content);
            if (d.content)  {
                $('.user_count').html(d.content.split(',').length);
            } else {
                $('.user_count').html(0);
            }
        } else {
            console.log(response);
        }
    },param);
}

$('#join-person input').change(function () {
    getUserMember();
});

$('.clone input').blur(function () {
    if ($('input[name="custom_box"]:checked').val() == 1) {
        getUserMember();
    }
});

$(document).on('click', '.look_people', function () {
    var paramJson = getSearchParam();
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['预计发放用户', 'text-align:center;padding: 0px;']
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

});

function loadUserList(paramJson) {
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
                template_table.find('tr td').eq(5).html('否');
            } else {
                template_table.find('tr td').eq(5).html('是');
            }
            if ($.inArray(list[i].user_id.toString(), record_select_value) > -1) {
                template_table.find('tr td').eq(0).find('input').attr('checked', true);
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
        card_id = $("input[name='card_id']").val();
    
    if ($('input[name="tag_box"]:checked').val() == 1) 
        tag_id = $("input[name='tag_id']").val();

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
    paramJson.point_start_time = point_start_time;
    paramJson.point_end_time = point_end_time;
    paramJson.goods_ids = goods_ids;
    paramJson.user = send_member;
    paramJson.is_click_search = 0;
    paramJson.isMarket= 1;

    return paramJson;
}

//单选
function checkOne(item) {
    record_select_value = $('#record_select_value').val() != '' ? $('#record_select_value').val().split(',') : [];
    var is_checked = $(item).is(':checked');
    if (is_checked) {
        record_select_value.push($(item).val());
    } else {
        var index = $.inArray($(item).val(), record_select_value);
        record_select_value.splice(index, 1);
    }
    if (record_select_value.length > 0 && record_select_value.length == $('#user').val().split(',').length) {
        $('#set-goods input[name="check_all"]').prop('checked', true);
    } else {
        $('#set-goods input[name="check_all"]').prop('checked', false);
    }
    $('#record_select_value').val(record_select_value.join(','));
}