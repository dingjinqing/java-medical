
//添加排班弹框
$('.btn_add').click(function(){
    //初始化
    $('#add_tech').removeAttr('disabled');
    $('#add_tech').find('option:first-child').prop('selected',true);
    $('.schedule_week .add_select').find('option:first-child').prop('selected',true);
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['添加技师排班', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: '720px'
            , content: $('#add-schedule') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , yes: function (index, layero) { //确定按钮的回调
                // if($('#add_tech option:selected').val() == ''){
                //     util.mobile_alert('请选择技师');
                //     return false;
                // }
                var _isInvalid = false;  //借助外部变量来阻止each中的事件继续进行
                $('.tech_name').each(function(){
                    if($(this).html() == $('#add_tech option:selected').val()){
                        util.mobile_alert('该技师已添加过了，请选择其他技师');
                        _isInvalid = true;
                        return false;
                    }
                });
                if (_isInvalid) {
                    return false;
                }
                var shifts_obj = {};
                shifts_obj.week_obj = {};
                $.each($('.schedule_week .add_select'),function(key,value){
                    shifts_obj.week_obj[key] = {};
                    shifts_obj.week_obj[key].type = $(value).find('option:selected').attr('type');
                    shifts_obj.week_obj[key].name = $(value).find('option:selected').val();
                    shifts_obj.week_obj[key].time = $(value).find('option:selected').attr('data-time');
                });
                shifts_obj.tech = $('#add_tech option:selected').val();
                var tech_html = '';
                tech_html += '<tr>';
                tech_html += '<td><span class="tech_name">' + shifts_obj.tech + '</span></td>'
                $.each(shifts_obj.week_obj,function (k,v) {
                    if(v.type == 0){
                        tech_html += '<td><span class="sche_span">' + v.name + '</span><div></div></td>';
                    }else{
                        tech_html += '<td><span class="sche_span">' + v.name + '</span><div>' + v.time + '</div></td>';
                    }
                });
                tech_html += '<td><a href="##" class="operate_sche">编辑</a></td>';
                tech_html += '</tr>';
                $('.schedule_tech_table').append(tech_html);
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});
//编辑技师排班
$('.schedule_tech_table').on('click','.operate_sche',function () {
    var param = 'store_id='+ $("#store_id").val()+'&technician_id='+$("#technician_id").val()+'&start_date='+$("#curday").html()+'&business_type='+$("#business_type").val();
    //     store_id=$("#store_id").val(),
    //     technician_id:$("#technician_id").val(),
    //     start_date:$("#curday").html(),
    //     // schedule_list:schedule_list
    // }
    // console.log(param)
    // var _this = $(this);
    // var this_tech = _this.parent().parent().find('.tech_name').html();
    // $('#add_tech').attr('disabled','disabled');
    // $('#add_tech option').each(function () {
    //     $(this).prop('selected',false);
    //     if($(this).val() == this_tech){
    //         $(this).prop('selected',true);
    //     }
    // });
    var this_td = $(this).parent().parent().find('td');
    // var week_yuan = $('.schedule_week .add_select');
    this_td = this_td.slice(1,8);
    // $.each(this_td,function (i,val) {
    //     $(val).find('.sche_span').html()
    //     $.each($(week_yuan[i]).find('option'),function (k,v) {
    //         $(this).prop('selected',false);
    //         if($(this).val() == $(val).find('.sche_span').html()){
    //             $(this).prop('selected',true);
    //         }
    //     });
    // });
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['添加技师排班', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: '720px'
            , content: "/admin/schedule/manage?" + param //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , yes: function (index, layero) { //确定按钮的回调
                var schedule_list = [];
                var iframe = layer.getChildFrame('body', index);
                $.each(iframe.contents().find(".schedule_week .add_select"),function (k) {
                    schedule_list[k] = $(this).find('option:selected').attr('schedule_id');
                })
                var param = {
                    store_id:$("#store_id").val(),
                    technician_id:$("#technician_id").val(),
                    start_date:$("#curday").html(),
                    schedule_list:schedule_list
                }
                // console.log(param)
                util.ajax_json("/admin/store/services/technician/schedule/add",function (d) {
                    if(d && d.error == 0){
                        layer.msg("修改成功");
                        $.each(this_td,function (k,v) {
                            var tech_schedule = d.content[k];
                            var td_html = '';
                            if(tech_schedule && tech_schedule.schedule_id > 0){
                                td_html += '<span class="sche_span">' + tech_schedule.schedule_name + '</span>';
                                td_html += '<div>' + tech_schedule.begin_time + '-' + tech_schedule.end_time + '</div>';
                            }else{
                                td_html += '<span class="sche_span">无排班</span>';
                                td_html += '<div></div>';
                            }
                            $(v).html(td_html);
                        });
                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                },param)


                layer.closeAll();
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});
//设置班次
$('.btn_set').click(function () {
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['班次设置', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['735px','350px']
            , content: $('#shift-manage') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            //, btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , yes: function (index, layero) { //确定按钮的回调

            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});
//添加班次
$('.sm_btn_add').click(function () {
    var option_b =$(".sel_begin option:selected"); //获取开始时间选中的项
    var option_e =$(".sel_end option:selected"); //获取开始时间选中的项
    var ipt_val = $('.ipt_shifts').val();  //获取班次的名称
    var shift_clone = '';
    shift_clone = $('.shift_clone').clone();
    var clone_opt_b = shift_clone.find('.add_begin').find('option');
    var clone_opt_e = shift_clone.find('.add_end').find('option');
    //判断是否输入了班次的名称
    if($('.ipt_shifts').val() == ''){
        util.mobile_alert('请输入班次的名称');return;
    }
    //开始时间
    if(option_b.val() == '请选择开始时间'){
        util.mobile_alert('请选择开始时间');return;
    }
    //结束时间
    if(option_e.val() == '请选择结束时间'){
        util.mobile_alert('请选择结束时间');return;
    }
    //console.log(parseInt(option_b.val().replace(":","")));
    //console.log(parseInt(option_e.val().replace(":","")));
    if(parseInt(option_b.val().replace(":","")) >= parseInt(option_e.val().replace(":",""))){
        util.mobile_alert('结束时间不能在开始时间之前，请重新选择');return;
    }

    //还原
    $('.ipt_shifts').val('');
    $('.sel_begin').find('option:first-child').attr('selected','selected');
    $('.sel_end').find('option:first-child').attr('selected','selected');
    var param = {
        store_id: $('#store_id').val(),
        schedule_name: ipt_val,
        begin_time: option_b.val(),
        end_time: option_e.val(),
    };
    util.ajax_json("/admin/schedule/add", function (d) {
        if (d && d.error == 0) {
            layer.msg("添加成功");
            var sche = d.content;
            shift_clone.removeClass('shift_clone');
            $(shift_clone).find('input[name="schedule_id"]').val(sche.schedule_id); //班次的名称
            $(shift_clone).find('input[name="schedule_name"]').val(sche.schedule_name); //班次的名称
            $.each(clone_opt_b,function(){
                if($(this).val() == sche.begin_time){
                    $(this).attr('selected',true);
                }
            });
            $.each(clone_opt_e,function(){
                if($(this).val() == sche.end_time){
                    $(this).attr('selected',true);
                }
            });
            $('.shifts_list').prepend(shift_clone);
        } else if (d && d.error > 0) {
            layer.msg(d.message);
        }
    }, param);
});
//删除班次
$('.shifts_list').on('click','.sm_btn_del',function () {
    var obj = $(this);
    var schedule_id = obj.parent().find('input[name="schedule_id"]').val();
    util.ajax_json("/admin/schedule/del", function (d) {
        if (d && d.error == 0) {
            layer.msg("操作成功");
            obj.parent().remove();
        } else if (d && d.error > 0) {
            layer.msg(d.message);
        }
    }, {schedule_id : schedule_id});
});
//保存
$('.shifts_list').on('click','.sm_btn_save',function () {
    var param = {
        store_id: $('#store_id').val(),
        schedule_id: $(this).parent().find('input[name="schedule_id"]').val(),
        schedule_name: $(this).parent().find('input[name="schedule_name"]').val(),
        begin_time: $(this).parent().find('select[name="begin_time"]').val(),
        end_time: $(this).parent().find('select[name="end_time"]').val(),
    };
    util.ajax_json("/admin/schedule/update", function (d) {
        if (d && d.error == 0) {
            layer.msg("操作成功");
        } else if (d && d.error > 0) {
            layer.msg(d.message);
        }
    }, param);
});