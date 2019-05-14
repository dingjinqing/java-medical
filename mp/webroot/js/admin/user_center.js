$('.basic_info_edit').click(function () {
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['编辑', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['415px','515px']
            , content: '/admin/user/manage/edit?user_id='+user_id //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: '确定'
            , btnAlign: 'c' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function (index) {
                var iframe = layer.getChildFrame('body', index);
                if(iframe.contents().find('#cid').val()){
                    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                    if(reg.test(iframe.contents().find('#cid').val()) === false){
                        util.mobile_alert('输入身份号格式不正确');
                        return false;
                    }
                }
                var data={
                    //user
                    'user_id':user_id,
                    'sex':iframe.contents().find('#sex').val(),
                    'birthday_year':iframe.contents().find('#birthday_year').val(),
                    'birthday_month':iframe.contents().find('#birthday_month').val(),
                    'birthday_day':iframe.contents().find('#birthday_day').val(),
                    'real_name':iframe.contents().find('#real_name').val(),
                    'province_code':iframe.contents().find('#province_code').val(),
                    'city_code':iframe.contents().find('#city_code').val(),
                    'district_code':iframe.contents().find('#district_code').val(),
                    'marital_status':iframe.contents().find('#marital_status').val(),
                    'monthly_income':iframe.contents().find('#monthly_income').val(),
                    'cid':iframe.contents().find('#cid').val(),
                    'education':iframe.contents().find('#education').val(),
                    'industry_info':iframe.contents().find('#industry_info').val(),
            };
                util.ajax_json('/admin/user/manage/edit?user_id='+user_id,function(d){
                    if(d&&d.error==0){
                        layer.ready(function () {
                            layer.msg(d.content, {time: 500},function () {
                                layer.close(index);
                            });
                        });
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                    location.reload();
                },data);
            }
        });
    });
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
                //         $(".asset_account_set").next().html(amount.toFixed(2));
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
                data.user_id = [$(id).find('.user_id').val()];
                data.remark = $(id).find('.remark').val();
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
                //         $(".asset_score_set").next().text(score);
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


        }
    });
}

//修改积分
$('.asset_score_set').on('click',function(othis){
    exchange_some('修改积分','#set-integral');
    var ipt_val = $(this).next().html();
    $('#set-integral').find('.score_dis').val(ipt_val);

    $('#set-integral').find('.user_id').val(user_id);
});
//修改余额
$('.asset_account_set').on('click',function(){
    exchange_some('修改余额','#set-money');
    var ipt_val = $(this).next().html();
    $('#set-money').find('.money_dis').val(ipt_val);

    $('#set-money').find('.user_id').val(user_id);
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
                    data.user_id = $('#set-label>div').attr('user_id');
                    getAuthorityDetail(1,"","batch_tag","","batch_tag",data);
                    // util.ajax_json('/admin/user/tag/edit',function(d){
                    //     if(d&&d.error>=0){
                    //         layer.close(index);
                    //         //$('.label_info_edit').attr('user_tag', JSON.stringify(user_tag));
                    //         $('.label_info_content').html('');
                    //         $.each(user_tag,function (i,v) {
                    //             if(v.tag_name){
                    //                 var html = '<span  tag_id="' + v.tag_id + '">';
                    //                 html += v.tag_name;
                    //                 html += '</span>';
                    //                 $('.label_info_content').append(html);
                    //             }
                    //         });
                    //         util.mobile_alert(d.message);
                    //     }
                    //     else{
                    //         util.mobile_alert(d.message);
                    //     }
                    // },data);

                },btn2: function(index, layero){
                    //按钮取消的回调
                    $('#set-label>ul').hide();
                }
            });
        }
    };
    $('.label_info_edit').on('click', function(){
        var data = getPowerInfo('main_config','tag','sub_3','会员标签');
        console.log(data);
        if(data.content != 1){
            return;
        }
        $('#set-label>ul').hide();
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
        var user_tag = [];

        $('.label_info_content').find('span').each(function () {
            var tag_obj = {};
            var span_tag_id = $(this).attr('tag_id');
            var span_tag_name = $(this).html();
            tag_obj.tag_id = span_tag_id;
            tag_obj.tag_name = span_tag_name;
            user_tag.push(tag_obj);
        });
        console.log(user_tag);
        $('#set-label>div').html('');
        $('#set-label>ul>.label_list').each(function(){
            $(this).removeClass('li_select');
        });

        $('#set-label>div').attr('user_id',user_id);
        $.each(user_tag, function(index, value, array) {
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
$(document).on('click','.card_set',function(){
    var card_type = $(this).attr('card_type');
    var obj;
    var card_arr = [];
    var t = [];
    var set_card = $('#set-card');
    $('.set_card_ul').find('.set_card_li').remove();
    $('.btn_add_card').show();
    $('.set_card_last').find('select').hide();
    $('.set_card_last').css('background','#fff');

    //初始化所有的会员卡
    if(card_type == 0){
        if(comment_num == 0){
            util.mobile_alert('您暂无普通卡，无法为会员设置普通卡');
            return;
        }
        obj = $('.asset_common_set');
        $('.comment_clone').html('');
        $('.card_clone2 option').clone().appendTo('.comment_clone');
    }else if(card_type == 1){
        var edit_data = getPowerInfo('main_config','count_card','sub_3','限次会员卡',1);
        //console.log(edit_data);
        if(edit_data.content == -1){
            return;
        }
        if(count_num == 0){
            util.mobile_alert('您暂无限次卡，无法为会员设置限次卡');
            return;
        }
        obj = $('.asset_limit_set');
        $('.count_clone').html('');
        $('.card_clone3 option').clone().appendTo('.count_clone');
    }else if(card_type == 2){
        var edit_data_grade = getPowerInfo('main_config','grade_card','sub_3','等级会员卡',1);
        //console.log(edit_data_grade);
        if(edit_data_grade.content == -1){
            return;
        }
        if(grade_num == 0){
            util.mobile_alert('您暂无等级卡，无法为会员设置等级卡');
            return;
        }
        obj = $('.asset_grade_set');
        $('.grade_clone').html('');
        $('.card_clone4 option').clone().appendTo('.grade_clone');
    }

    //遍历会员已有的会员卡，并使其显示在弹框中
    obj.parent().find('.hide').find('span').each(function () {
        t.card_id = $(this).attr("card_id");
        t.card_no = $(this).attr("card_no");
        t.card_name = $(this).text();
        card_arr.push(t);
        var html = '<li class="set_card_li" card_id="' + t.card_id + '" card_no="' + t.card_no + '">'
            + '<span>' + t.card_name + '</span>'
            + '<img src="/image/admin/icon_delete.png" title="' + t.card_name + '" class="card_delete" />'
            + '</li>';
        if(card_type == 0){
            set_card.find('.set_comment_card').before(html);
            $('.set_comment_card').show();
            $('.set_count_card').hide();
            $('.set_grade_card').hide();
        }else if(card_type == 1){
            set_card.find('.set_count_card').before(html);
            $('.set_count_card').show();
            $('.set_comment_card').hide();
            $('.set_grade_card').hide();
        }else if(card_type == 2){
            set_card.find('.set_grade_card').before(html);
            $('.set_grade_card').show();
            $('.set_comment_card').hide();
            $('.set_count_card').hide();
        }
    });
    //选择会员卡的时候，如果该会员已经有的会员卡将不再显示在选择的下拉框中
    var card_li_id = $('.set_card_li');
    var option_id;
    if(card_type == 0){
        option_id = $('.comment_clone .option');
        $('.set_comment_card').show();
        $('.set_count_card').hide();
        $('.set_grade_card').hide();
    }else if(card_type == 1){
        option_id = $('.count_clone .option');
        $('.set_count_card').show();
        $('.set_comment_card').hide();
        $('.set_grade_card').hide();
    }else if(card_type == 2){
        option_id = $('.grade_clone .option');
        $('.set_grade_card').show();
        $('.set_count_card').hide();
        $('.set_comment_card').hide();
    }
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
        //触发事件
        layer.open({
            type: 1
            ,title: ['设置会员卡','text-align:center;padding: 0px;']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: '500px'
            ,id: 'layerDemoD' //防止重复弹出
            ,content: $('#set-card') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['确定','取消']
            ,btnAlign: 'r' //按钮居右
            ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function(index, layero){ //保存按钮的回调
                var data = {};
                data.user_id = [user_id];
                data.card_info = [];
                var card_info_jia = [];
                var card_li = $('#set-card').find('.set_card_li');
                if(card_type == 1){
                    $('.comment_card_list').find('span').each(function () {
                        var card = {};
                        card.card_id = $(this).attr('card_id');
                        card.card_name = $(this).html();
                        data.card_info.push(card);
                    });
                }else if(card_type == 0){
                    $('.count_card_list').find('span').each(function () {
                        var card = {};
                        card.card_id = $(this).attr('card_id');
                        card.card_name = $(this).html();
                        data.card_info.push(card);
                    });
                }else if(card_type == 2){
                    var card_list = $('.all_card_list[card_type != 2]');
                    card_list.each(function () {
                        var card = {};
                        card.card_id = $(this).attr('card_id');
                        card.card_name = $(this).html();
                        data.card_info.push(card);
                    });
                }
                $.each(card_li,function(k,v){
                    var card = {};
                    card.card_id = $(this).attr('card_id');
                    card.card_name = $(this).find('span').html();
                    //console.log(card);
                    data.card_info.push(card);
                    card_info_jia.push(card);
                });
                console.log(data);
                getAuthorityDetail(1,"","batch_card","","batch_card",data);
                // util.ajax_json('/admin/ajax/user/set/card',function(d){
                //     if(d&&d.error==0){
                //         // data = JSON.parse(data);
                //         var obj_html;
                //         var obj_arr = [];
                //         if(data.card_info === undefined || data.card_info.length == 0){
                //             obj.parent().prev().remove();
                //         }
                //         $.each(card_info_jia,function(i,e){
                //             obj_html = '<span card_id="' + e.card_id + '">' + e.card_name + '</span>';
                //             obj.parent().find('.hide').html('');
                //             obj_arr.push(obj_html);
                //             obj.parent().find('.hide').append(obj_arr);
                //             obj.parent().find('.card_num').html(obj_arr.length);
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
            }
        });
    });
});
//添加
$('.btn_add_card').click(function(){
    var opt_len = $(this).next().find('option').length;
    if(opt_len > 1){
        $(this).hide();
        $(this).next().show();
        $(this).parent().css('background','#f5f5f5');
        $(this).next().change(function(){
            var card_name = $(this).children('option:selected').html();
            var card_id = $(this).children('option:selected').val();
            if(card_id != ''){
                var grade_card = '';
                if($(this).attr('card_type') == 2){
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
                if($(this).attr('card_type') == 2){
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

$('.update_invite').click(function () {
    let user_ids = $(this).attr('user_id');
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择用户', 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , area: ['850px', '500px']
            , content: '/admin/user/select/single'
            , btn: ['确定', '取消']
            , btnAlign: 'r'
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function (index) {
                var iframe = layer.getChildFrame('body', index);
                let invite_id = iframe.find('.user_tbody .user_choose').attr('user_id');
                if (invite_id > 0) {
                    getAuthorityDetail(1,"","change_invite","","change_invite",{invite_id:invite_id,user_ids:user_ids});
                //     util.ajax_json('/admin/user/update/invite',function (res) {
                //         if (res.error == 0) {
                //             util.mobile_alert('修改成功');
                //             window.location.reload();
                //         } else {
                //             util.mobile_alert(res.message)
                //         }
                //     },{invite_id:invite_id,user_ids:user_ids})
                // } else {
                //     util.mobile_alert('请选择邀请人');
                //     return false;
                }

                layer.close(index);
            }
        });
    });
});