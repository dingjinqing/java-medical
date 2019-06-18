//全选全不选
$("#ipt_all").click(function () {
    var isChecked = $("#ipt_all").prop("checked");
    if($(this).is(':checked')) {
        $(this).prev().attr('src','/image/admin/square_yes.png');
        $(this).parents('#form2').find('.member_list_main').find(".ipt_checkbox").each(function(){
            if(!$(this).is(':checked'))
                $(this).click();
        })
    }else{
        $(this).prev().attr('src','/image/admin/square_no.png');
        $(this).parents('#form2').find('.member_list_main').find(".ipt_checkbox").each(function(){
            if($(this).is(':checked'))
                $(this).click();
        })
    }
});
//选择某个商品
$(".ipt_checkbox").click(function () {
    $(this).attr("checked", "checked");
    var allLength = $(".ipt_checkbox").length;
    var checkedLength = $(".ipt_checkbox:checked").length;
    if(allLength == checkedLength){
        $("#ipt_all").prop("checked",true);
        $("#ipt_all").prev().attr('src','/image/admin/square_yes.png');
    }else {
        $("#ipt_all").prop("checked",false);
    }
    if($(this).is(':checked')){
        $(this).prev().attr('src','/image/admin/square_yes.png');

    }else{
        $(this).prev().attr('src','/image/admin/square_no.png');
        $("#ipt_all").prev().attr('src','/image/admin/square_no.png');
    }
});
function exchange_some(title,id) {
    layer.open({
        type: 1
        ,title: [title,'text-align:center;padding: 0px;']
        ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
        ,area: '600px'
        ,content: $(id) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        ,btn: ['确认','取消']
        ,btnAlign: 'r' //按钮居右
        ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
        ,yes: function(index, layero){ //确定按钮的回调
            //alert(index+",\n"+$(id).html()+",\n\n"+JSON.stringify(layero));

            if(id=='#set-money'){
                //余额充值
                var data={};
                data.account = $(id).find('.money_dis').val();
                data.amount = $(id).find('.amount').val();
                data.user_id = $(id).find('.user_id').val();
                data.remark = $(id).find('.remark').val();
                if (isNaN(parseInt(data.amount))) {
                    util.mobile_alert('请填写合法的余额数');
                    return false;
                }
                if(data.account<=0  && data.amount<0 ){
                    util.mobile_alert('当前余额不足');
                    return false;
                }
                if(data.amount<0 && data.account < data.amount*-1){
                    util.mobile_alert('当前余额不足');
                    return false;
                }
                getAuthorityDetail(1,"","batch_account","","batch_account",data);
                // util.ajax_json('/admin/user/manage/account/add',function(d){
                //     if(d&&d.error==0){
                //         var amount = parseFloat(data.account)+parseFloat(data.amount);
                //         $("#user_"+data.user_id).find('.ipt-money').val(amount.toFixed(2));
                //         util.mobile_alert(d.content);
                //         layer.close(index);
                //     }
                //     else{
                //         util.mobile_alert(d.message);
                //     }
                // },data);
            }
            if(id=='#set-integral'){
                //积分充值
                var data={};
                data.score_dis = $(id).find('.score_dis').val();
                data.score = $(id).find('.score').val();
                data.user_id = $(id).find('.user_id').val().split(",");
                data.remark = $(id).find('.remark').val();
                if (isNaN(parseInt(data.score))) {
                    util.mobile_alert('请填写合法的积分数');
                    return false;
                }
                if(data.score_dis<=0  && data.score<0 ){
                    util.mobile_alert('当前积分不足');
                    return false;
                }
                if(data.score<0 && data.score_dis < data.score*-1){
                    util.mobile_alert('当前积分不足');
                    return false;
                }
                getAuthorityDetail(1,"","batch_score","","batch_score",data);
                // util.ajax_json('/admin/user/manage/score/add',function(d){
                //     if(d&&d.error==0){
                //         var score = parseInt(data.score_dis)+parseInt(data.score);
                //         $("#user_"+data.user_id).find('.ipt-integral').val(score);
                //         util.mobile_alert(d.content);
                //         layer.close(index);
                //     }
                //     else{
                //         util.mobile_alert(d.message);
                //     }
                // },data);
            }
            if(id=='#set-delete'){
                //禁止登录
                var data={};
                data.user_id = $(id).find('.user_id').val();
                data.is_delete = $(id).find('.is_delete').val();
                getAuthorityDetail(1,"","batch_delete","","batch_delete",data);
                // util.ajax_json('/admin/user/manage/del',function(d){
                //     if(d&&d.error==0){
                //         if(data.is_delete==1){
                //             $("#user_"+data.user_id).find('.is_delete').val(0);
                //             $("#user_" + data.user_id).find('.btn_stop').text('禁止登录');
                //         }else {
                //             $("#user_"+data.user_id).find('.is_delete').val(1);
                //             $("#user_" + data.user_id).find('.btn_stop').text('恢复登录');
                //         }
                //         util.mobile_alert(d.content);
                //         layer.close(index);
                //     }
                //     else{
                //         util.mobile_alert(d.message);
                //     }
                // },data);
            }
            if(id=='#all-delete'){
                //禁止登录
                var data={};
                data.user_id =  $(id).find('.user_id').val();
                data.is_delete = 0;
                getAuthorityDetail(1,"","batch_delete","","batch_delete",data);
                // util.ajax_json('/admin/user/manage/del',function(d){
                //     if(d&&d.error>=0){
                //         for (user_id in data.user_id){
                //             $("#user_"+user_id).find('.is_delete').val(1);
                //             $("#user_" +user_id).find('.btn_stop').text('恢复登录');
                //         }
                //         util.mobile_alert(d.content);
                //         layer.close(index);
                //     }
                //     else{
                //         util.mobile_alert(d.message);
                //     }
                // },data);
            }
            //layer.close(index);   //开启则可以关闭弹框
        },btn2: function(index, layero){
            //按钮取消的回调


        }, end: function () {
            $(id).hide();
        }
    });
}
//修改积分
$('.member_list_main').on('click','.btn_integral',function(othis){
    exchange_some('修改积分','#set-integral');
    var ipt_val = $(this).parent().parent().find('.ipt-integral').val();
    $('#set-integral').find('.score_dis').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.user_id').val();
    $('#set-integral').find('.user_id').val(ipt_val);
});
//修改余额
$('.member_list_main').on('click','.btn_money',function(){
    exchange_some('修改余额','#set-money');
    var ipt_val = $(this).parent().parent().find('.ipt-money').val();
    $('#set-money').find('.money_dis').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.user_id').val();
    $('#set-money').find('.user_id').val(ipt_val);
});

//禁止登录
$('.member_list_main').on('click','.btn_stop',function(){
    var ipt_val = $(this).parent().parent().find('.user_id').val();
    $('#set-delete').find('.user_id').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.is_delete').val();
    $('#set-delete').find('.is_delete').val(ipt_val);
    if(ipt_val==1){
        exchange_some('恢复登录','#set-delete');
        $('#set-delete').find('.exchange_old').html('<span>提示:&nbsp;</span>确定要恢复会员的登录功能吗？');
    }else{
        exchange_some('禁止登录','#set-delete');
        $('#set-delete').find('.exchange_old').html('<span>提示:&nbsp;</span>禁止登录后会员将不能登录了，确定禁止登录吗？');
    }
});
//多选 禁止登录
$('.member_list_footer').on('change','.batch_delete',function(){
    var user_id = [];
    if($(".batch_delete").val()=='check'){
        $('input[name="checkbox"]:checked').each(function () {
            user_id.push($(this).attr('user_id'));
        });
    }else if($(".batch_delete").val()=='search'){
        user_id = -1;
    }
    if (user_id.length == 0) {
        util.mobile_alert('请选择会员');
        $(".batch_delete").val('');
        return false;
    }else {
        exchange_some('禁止登录','#all-delete');
        $('input[name="btn_type"]').val($(this).attr('name'));
        $('#all-delete').find('.user_id').val(user_id);
    }
});
var batch_user_id = [];
var batch_tag_flag = false;//批量添加标签的弹出标签去掉获取用户标签的功能
//多选 批量加标签
$('.member_list_footer').on('change','.batch_tag',function(){
    var data = getPowerInfo('main_config','tag','sub_3','会员标签');
    console.log(data);
    if(data.content != 1){
        return;
    }
    batch_user_id = [];
    if($(".batch_tag").val()=='check'){
        $('input[name="checkbox"]:checked').each(function () {
            batch_user_id.push($(this).attr('user_id'));
        });
    }else if($(".batch_tag").val() == 'search'){
        batch_user_id = -1;
    }
    if (batch_user_id.length == 0) {
        util.mobile_alert('请选择会员');
        $(".batch_tag").val('');
        return false;
    }else {
        batch_tag_flag = true;
        $('.btn_label').click();
        batch_tag_flag = false;
    }
});
//打标签
layui.use('layer', function() { //独立版的layer无需执行这一句
    var $ = layui.jquery, layer = layui.layer;
    //触发事件
    var active = {
        offset: function(othis){
            var type = othis.data('type');
            layer.open({
                type: 1
                ,title: ['标签','text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: '350px'
                ,id: 'layerDemo'+type //防止重复弹出
                ,content: $('#set-label') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['确定','取消']
                ,btnAlign: 'r' //按钮居右
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    var data={};
                    var tag_id = '';
                    var user_tag = {};
                    var batch_tag = false;
                    var i = 0;
                    $('#set-label>div>.label_span').each(function(){
                        if(i<5) {
                            tag_id += $(this).attr('tag_id') + ',';
                            var usertag = {};
                            usertag.tag_id = $(this).attr('tag_id');
                            usertag.tag_name = $(this).attr('data-title');
                            user_tag[i] = usertag;
                            i++;
                        }
                    });
                    data.tag_id = tag_id.substring(0,tag_id.length-1);
                    if(batch_user_id.length>0 || batch_user_id== -1){
                        //批量添加标签
                        data.user_id = batch_user_id;
                        batch_user_id = [];
                        batch_tag = true;
                    }else {
                        data.user_id = $('#set-label>div').attr('user_id');
                    }
                    data.batch_tag = batch_tag;
                    data.user_tag = user_tag;
                    getAuthorityDetail(1,"","batch_tag","","batch_tag",data);
                    // util.ajax_json('/admin/user/tag/edit',function(d){
                    //     if(d&&d.error>=0){
                    //         layer.close(index);
                    //         if(batch_tag){
                    //             $.each(data.user_id, function(index, value, array) {
                    //                 // $("#user_" + value).find('.btn_label').attr('user_tag', JSON.stringify(user_tag));
                    //                 $("#user_" + value).find('.btn_label').attr('user_tag', JSON.stringify(d.content[value]));
                    //             });
                    //         }else {
                    //             $("#user_" + data.user_id).find('.btn_label').attr('user_tag', JSON.stringify(user_tag));
                    //         }
                    //         util.mobile_alert(d.message);
                    //     }
                    //     else{
                    //         util.mobile_alert(d.message);
                    //     }
                    // },data);

                },btn2: function(index, layero){
                    //按钮取消的回调
                    $('#set-label>ul').hide();
                }, end: function () {
                    $('#set-label').hide();
                }
            });
        }
    };
    $('.btn_label').on('click', function(){
        var data = getPowerInfo('main_config','tag','sub_3','会员标签');
        console.log(data);
        if(data.content != 1){
            return;
        }
        $('#set-label>ul').hide();
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
        var user_tag = othis.attr('user_tag');
        var tag_obj = JSON.parse( user_tag );
        $('#set-label>div').html('');
        $('#set-label>ul>.label_list').each(function(){
            $(this).removeClass('li_select');
        });

        if(batch_tag_flag){
            return;
        }else{
            batch_user_id = [];
        }
        var user_id = othis.parent().parent().find('.user_id').val();
        $('#set-label>div').attr('user_id',user_id);
        $.each(tag_obj, function(index, value, array) {
            if(value.tag_name) {
                var html = '<span class="label_span" tag_id="' + value.tag_id + '" data-title="' + value.tag_name + '">';
                html += value.tag_name;
                html += '<img src="/image/admin/icon_delete.png" title="删除" class="delete-label" />';
                html += '</span>';
                $('#set-label>div').append(html);
            }
            $('#set-label>ul>.label_list').each(function(){
                var tag_id = $(this).attr('tag_id');
                if(tag_id == value.tag_id){
                    $(this).addClass('li_select');
                }
            });
        });
    });
});
var flag = true;
$('#set-label>div').click(function(){
    $('.ipt_search').val('');
    if(flag == true){
        $('#set-label>ul').show();
        tag_search('.label_list');
        var label_list = document.getElementsByClassName('label_list');
        var label_span = document.getElementsByClassName('label_span')
        flag = false;
    }else if(flag == false){
        $('#set-label>ul').hide();
        flag = true;
    }
});
$('#set-label').on('click','.delete-label',function(e){
    e.stopPropagation();
    $(this).parent().remove();
    var data_title = $(this).parent().attr('data-title');
    $('.li_select').each(function(){
        if($(this).attr('data-title') == data_title){
            $(this).removeClass('li_select');
        }
    });
    $('#set-label>ul').hide();
    flag = true;
});
$('.label_list').on('click',function(){
    var num = 0;
    $('#set-label>div>.label_span').each(function(){
        num++;
        if(num>=5){
            util.mobile_alert('一个用户最多可以标记5个标签。');
            return;
        }
    });
    if(num<5) {
        var new_label = '';
        var tag_id = '';
        if (!$(this).hasClass('li_select')) {
            new_label = $(this).attr('data-title');
            tag_id = $(this).attr('tag_id');
            var html = '<span class="label_span" tag_id="' + tag_id + '" data-title="' + new_label + '">';
            html += new_label;
            html += '<img src="/image/admin/icon_delete.png" alt="" class="delete-label" />';
            html += '</span>';
            $(this).addClass('li_select');
            $('#set-label>div').append(html);
            $('#set-label>ul').hide();
            flag = true;
        }
    }
});
//顶部的标签
var order_flag = true
$('.order_label_span').click(function(e){
    e.stopPropagation();
    $('.ipt_search').val('');
    if(order_flag == true){
        $(this).next().show();
        tag_search('.list_tag');
        order_flag = false;
    }else if(order_flag == false){
        $(this).next().hide();
        order_flag = true;
    }
});
$('.order_label_span').on('click','.delete-tag',function(e){
    e.stopPropagation();
    $(this).parent().remove();
    var data_title = $(this).parent().attr('data-title');
    var tag_search = '';
    $('.li_none').each(function(){
        if($(this).attr('data-title') == data_title){
            $(this).removeClass('li_none');
        }
    });
    $('.order_tag_ul>.li_none').each(function(){

        if(tag_search==''){
            tag_search = $(this).attr('tag_id');
        }else {
            tag_search += ',' + $(this).attr('tag_id');
        }
    });
    $('.tag_search').val(tag_search);
});
$('.list_tag').on('click',function(e){
    e.stopPropagation();
    var new_label = '' ;
    var tag_id = '';
    if(!$(this).hasClass('li_none')){
        new_label = $(this).attr('data-title');
        tag_id = $(this).attr('tag_id');
        var html = '<span class="tag_span" tag_id="'+tag_id+'" data-title="' + new_label + '">' ;
        html +=  new_label;
        html += '<img src="/image/admin/icon_delete.png" title="删除" class="delete-tag" />';
        html += '</span>';
        $(this).addClass('li_none');
        $('.order_label_span').append(html);
        $(this).parent().hide();
        order_flag = true;
    }
    var tag_search = '';
    $('.li_none').each(function(){
        if(tag_search==''){
            tag_search = $(this).attr('tag_id');
        }else {
            tag_search += ',' + $(this).attr('tag_id');
        }
        $('.tag_search').val(tag_search);
    });

});
$(document).ready(function () {
    var tag_search = $('.tag_search').val();
    if(tag_search) {
        var tag = tag_search.split(",");
        $('.list_tag').each(function () {
            var tag_id = $(this).attr('tag_id');
            var new_label = '';
            //不包含在数组中,则返回 -1;
            if ($.inArray(tag_id, tag) != -1) {
                new_label = $(this).attr('data-title');
                tag_id = $(this).attr('tag_id');
                var html = '<span class="tag_span" tag_id="' + tag_id + '" data-title="' + new_label + '">';
                html += new_label;
                html += '<img src="/image/admin/icon_delete.png" title="删除" class="delete-tag" />';
                html += '</span>';
                $(this).addClass('li_none');
                $('.order_label_span').append(html);
                $(this).parent().hide();
                order_flag = true;
            }
        });
    }

});


$(document).click(function(e){
    $('.order_tag_ul').hide();
    order_flag = true;
});

//搜索
function tag_search(li_class){
    $('.ipt_search').click(function(e){
        e.stopPropagation();
    });
    $(li_class).show();
    $('.ipt_search').keyup(function(e){
        e.stopPropagation();
        var search_text = $(this).val();
        var search_li = $(li_class);
        if(search_text != ''){
            search_li.hide()
            var reg_li = search_li.filter(":contains('"+search_text+"')");
            reg_li.show();
            if(reg_li.length == ''){
                util.mobile_alert('没有该标签');
            }
        }else{
            search_li.show();
        }
    });
}
//设置会员卡
$(document).on('click','.card-setting',function(){
    var obj = $(this);
    addCard(obj)
});
function addCard(obj,type) {
    var card_arr = [];
    var t = [];
    var set_card = $('#set-card');
    $('.set_card_ul').find('.set_card_li').remove();
    $('.btn_add_card').show();
    $('.set_card_last').find('select').hide();
    $('.set_card_last').css('background','#fff');
    //初始化所有的会员卡
    $('.card_clone').html('');
    $('.card_clone_comment option').clone().appendTo('.select_comment');
    $('.card_clone_count option').clone().appendTo('.select_count');
    $('.card_clone_grade option').clone().appendTo('.select_grade');
    //遍历会员已有的会员卡，并使其显示在弹框中
    obj.parent().prev().find('span').each(function () {
        t.card_id = $(this).attr("card_id");
        t.card_no = $(this).attr("card_no");
        t.card_name = $(this).text();
        t.card_type = $(this).attr('card_type');
        card_arr.push(t);
        var html = '<li class="set_card_li" card_id="' + t.card_id + '" card_no="' + t.card_no + '">'
            + '<span>' + t.card_name + '</span>'
            + '<img src="/image/admin/icon_delete.png" title="' + t.card_name + '" class="card_delete" />'
            + '</li>';
        if(t.card_type == 0){
            set_card.find('.card_comment').before(html);
        }else if(t.card_type == 1){
            set_card.find('.card_count').before(html);
        }else if(t.card_type == 2){
            set_card.find('.card_grade').before(html);
        }
    });
    //确认某类型卡是否存在，存在的话则该类型卡显示添加按钮，否则就不显示添加按钮值显示select
    card_type('.card_comment');
    card_type('.card_count');
    card_type('.card_grade');
    //选择会员卡的时候，如果该会员已经有的会员卡将不再显示在选择的下拉框中
    var card_li_id = $('.set_card_li');
    var option_id = $('.card_clone .option');
    $.each(option_id,function(){
        var _this = $(this);
        _this_val = _this.val();
        //console.log(_this_val);
        card_li_id.each(function(){
            if($(this).attr('card_id') == _this_val){
                _this.remove();
                //console.log(_this);
            }
        });
    });

    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        var _this = $(this);
        //触发事件
        layer.open({
            type: 1
            ,title: ['设置会员卡','text-align:center;padding: 0px;']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: ['500px','380px']
            ,id: 'layerDemoD' //防止重复弹出
            ,content: $('#set-card') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['确定','取消']
            ,btnAlign: 'r' //按钮居右
            ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function(index, layero){ //保存按钮的回调
                var data = {};
                if(type == 'all'){
                    data.user_id = batch_user_id;
                }else{
                    data.user_id = [obj.parent().parent().parent().find('.ipt_checkbox').attr('user_id')];
                }
                data.card_info = [];
                var card_li = $('#set-card').find('.set_card_li');
                $.each(card_li,function(k,v){
                    var card = {};
                    card.card_id = $(this).attr('card_id');
                    card.card_name = $(this).find('span').html();
                    //console.log(card);
                    data.card_info.push(card);
                });
                getAuthorityDetail(1,"","batch_card","","batch_card",data);
                // var datas = JSON.stringify(data);
                // util.ajax_json('/admin/ajax/user/set/card',function(d){
                //     if(d&&d.error==0){
                //         // data = JSON.parse(data);
                //         var obj_html;
                //         var obj_arr = [];
                //         if(data.card_info === undefined || data.card_info.length == 0){
                //             obj.parent().prev().remove();
                //         }
                //         $.each(data.card_info,function(i,e){
                //             obj_html = '<span card_id="' + e.card_id + '">' + e.card_name + '</span>';
                //             obj.parent().prev().children().remove();
                //             obj_arr.push(obj_html);
                //             obj.parent().prev().append(obj_arr);
                //         });
                //         layer.close(index);
                //         layer.msg('保存成功', {time: 2000},function () {
                //             location.reload();
                //         });
                //     }
                //     else{
                //         util.mobile_alert(d.message);
                //     }
                // },data);
            },btn2: function(index, layero){
                //按钮取消的回调
                //return false 开启该代码可禁止点击该按钮关闭
            }, end: function () {
                $('#set-card').hide();
            }
        });
    });
}
//添加
$('.add_card').click(function(){
    var _this = $(this);
    var opt_len = _this.next().find('option').length;
    var card_type = _this.attr('card_type');
    if(opt_len > 1){
        _this.hide();
        _this.next().show();
        _this.parent().css('background','#f5f5f5');
        _this.next().change(function(){
            var card_name = $(this).children('option:selected').html();
            var card_id = $(this).children('option:selected').val();
            if(card_id != ''){
                var grade_card = '';
                if(card_type == 2){
                    var card_li = $(this).parent().parent().find('.set_card_li');
                    grade_card = '<option value="' + card_li.attr("card_id") + '" card_id="' + card_li.attr("card_id") + '" class="option">' + card_li.find("span").text() + '</option>';
                    $(this).parent().parent().find('.set_card_li').remove();
                }
                var li_html  = '<li class="set_card_li" card_id="'+card_id+'">' ;
                li_html += '<span>' + card_name + '</span>' ;
                li_html += '<img src="/image/admin/icon_delete.png" class="card_delete" />';
                li_html += '</li>';
                $(this).parent().before(li_html);
                $(this).children('option:selected').remove();
                if(card_type == 2){
                    $(this).append(grade_card);
                }
                $(this).hide();
                $(this).parent().css('background','#fff');
                $(this).prev().show();
            }
        });
    }else{
        util.mobile_alert('没有更多会员卡了，无法继续添加');
    }
});
//删除
$('.set_card_ul').on('click','.card_delete',function(){
    var delete_clone = $(this).parent().clone();
    var opt_card_id = delete_clone.attr('card_id');
    var opt_card_name = delete_clone.find('span').html();
    var option_html = '<option value="' + opt_card_id + '" class="option">' + opt_card_name + '</option>';
    $(this).parent().parent().find('.card_sel').after(option_html);
    $(this).parent().remove();
});
//
function card_type(class_name){
    if($(class_name).parent().find('.set_card_li').length == 0){
        $(class_name).find('.btn_add_card').click();
    }
}

$('.member_list_footer').on('change','.change_invite',function(){
    var user_id = [];
    // $('input[name="checkbox"]:checked').each(function () {
    //     user_id.push($(this).attr('user_id'));
    // });
    // if (user_id.length == 0) {
    //     util.mobile_alert('请选择会员');
    //     return false;
    // }else {
    //     changeInviteUser(user_id);
    // }
    if($(".change_invite").val() == 'check'){
        $('input[name="checkbox"]:checked').each(function () {
            user_id.push($(this).attr('user_id'));
        });
    }else if($(".change_invite").val() == 'search'){
        user_id = -1;
    }
    if (user_id.length == 0) {
        util.mobile_alert('请选择会员');
        $(".change_invite").val('');
        return false;
    }else {
        changeInviteUser(user_id);
    }
});

function changeInviteUser(user_ids) {
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择用户', 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: ['850px', '500px']
            , content: '/admin/user/select/single?source=user_list'
            , btn: ['确定', '取消']
            , btnAlign: 'r'
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero,index) {
                var body = layer.getChildFrame('body', index);
                body.find('.user_number').html(util.count(user_ids))
            }
            , yes: function (index) {
                var iframe = layer.getChildFrame('body', index);
                // var checked_user = iframe.find('#record_select_value').val();
                let invite_id = iframe.find('.user_tbody .user_choose').attr('user_id');
                if (invite_id > 0) {
                    getAuthorityDetail(1,"","change_invite","","change_invite",{invite_id:invite_id,user_ids:user_ids});
                    // util.ajax_json('/admin/user/update/invite',function (res) {
                    //     if (res.error == 0) {
                    //         if (util.count(res.content) > 0) {
                    //             let str = '';
                    //             for(var i in res.content) {
                    //                 str += res.content[i] + '<br/>'
                    //             }
                    //             layui.use('layer', function () {
                    //                 var layer = layui.layer;
                    //                 layer.alert('<div style="text-align: left">' + str + '</div>', {
                    //                     title: ['提醒', 'text-align:center;padding: 0px;']
                    //                     , area: ['320px','240px']
                    //                     , btnAlign: 'c'
                    //                     , btn: ['确定']
                    //                 }, function (index) {
                    //                     window.location.reload();
                    //                 });
                    //             });
                    //         } else {
                    //             layer.msg('修改成功', {time: 500},function () {
                    //                 window.location.reload();
                    //             });
                    //         }
                    //     } else {
                    //         util.mobile_alert(res.message)
                    //     }
                    // },{invite_id:invite_id,user_ids:user_ids})
                } else {
                    util.mobile_alert('请选择邀请人');
                    $(".change_invite").val('');
                    return false;
                }

                layer.close(index);
            }
        });
    });
}
$(".show_more").click(function () {
    $(".hid_some").show();
    $(".show_more").hide();
    $(".more_search").show();
})
$(".hid_some").click(function () {
    $(".show_more").show();
    $(".hid_some").hide();
    $(".more_search").hide();
})

//选择商品
$('.choose_goods').click(function(){
    var checkedId = $('input[name="goods_ids"]').val();
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','450px']
            , content: '/admin/public/purchase/goods/list?record_select_value='+checkedId //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {

            }
            , yes: function (index, layero) { //保存按钮的回调
                var body = layer.getChildFrame('body', index);
                var checkedId = body.find('#record_select_value').val();
                $('input[name="goods_ids"]').val(checkedId);
                if (body.find('#record_select_value').val() == '') {
                    util.mobile_alert('请选择商品');
                    return false;
                }
                if (body.find('#record_select_value').val().split(',').length > 3) {
                    util.mobile_alert('最多选择3个商品');
                    return false;
                }
                util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                    var list = response.content;
                    var html = '';
                    for (var i in list) {
                        html += '<div class="goods"><a style="color:#5a8bff" title="'+list[i].goods_name+'" href="/admin/goods/manage/edit?goods_id='+list[i].goods_id+'" goods_id="'+list[i].goods_id+'">'+list[i].goods_name+'</a>' +
                            '<img src="/image/admin/icon_delete.png" alt="" class="label-delete"  /></div>'
                    }
                    $('#goods_list').html(html);
                    layer.close(index);
                }, {select_id: body.find('#record_select_value').val()})
            }, btn2: function (index, layero) {
                //按钮取消的回调

            }
        });
    });
});
$('#goods_list').on('click','.label-delete',function () {
    var goods_id = $(this).prev().attr("goods_id");
    var goods_ids = $('input[name="goods_ids"]').val().split(",");

    var new_goods = [];
    for( var i in goods_ids){
        if(goods_id != goods_ids[i]){
            new_goods.push(goods_ids[i]);
        }
    }
    $('input[name="goods_ids"]').val(new_goods.join(","));
    $(this).parent().remove();

})
