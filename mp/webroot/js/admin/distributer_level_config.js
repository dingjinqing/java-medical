$(function(){
    $(".level_up_route").change(function () {
        if ($(this).val() == 1) {
            $(this).parent().parent().find('.add_distributer').css('display','block');
            $(this).parent().parent().find('.rule_conditions').css('display','none');
        } else {
            $(this).parent().parent().find('.add_distributer').css('display','none');
            $(this).parent().parent().find('.rule_conditions').css('display','block');
        }
    });

    $('.status_on').each(function(){
        var level_id = $(this).attr('level_id');
        $(this).click(function(){
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要启用吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    layer.close(index);
                    util.ajax_json('/admin/market/distribution/distributor/level/update',function (res) {
                        console.log(res)
                        if(res.error == 0){
                            layer.msg('启用成功', {time: 4000},function () { });
                            window.location.reload();
                        }else{
                            util.mobile_alert(res.message)
                        }
                    },{level_id: level_id,level_status: 1});
                });
            });
        })
    });

    $('.status_off').each(function(){
        var level_id = $(this).attr('level_id');
        var level_name = $(this).attr('level_name');
        var level_number = $(this).attr('level_number');
        $(this).click(function(){
            layui.use('layer', function(){
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type:1,
                    title: ['提示', 'text-align:center;padding: 0px;'],
                    offset: 'auto',
                    area: '450px',
                    content: $('#off_status'),
                    btn: ['确定', '取消'],
                    btnAlign: 'r' ,
                    shade: [0.3, '#000'],
                    success:function(index, layero){
                        $("#off_status").find(".level_name").html(level_name);
                        $("#off_status").find(".level_number").html(level_number);
                    },
                    yes:function(index, layero){
                        layer.close(index);
                        util.ajax_json('/admin/market/distribution/distributor/level/update',function (res) {
                            console.log(res)
                            if(res.error == 0){
                                layer.msg('停用成功', {time: 2000},function () { });
                                window.location.reload();
                            }else{
                                util.mobile_alert(res.message)
                            }
                        },{level_id: level_id,level_status: 0});
                    },
                    btn2:function(index,layero){
                        // $('#off_status').hide();
                    }
                })
            })
        })
    });

    $('.btn_strategy_save').click(function(){
        layui.use('layer', function(){
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type:1,
                title: ['提示', 'text-align:center;padding: 0px;'],
                offset: 'auto',
                area: '450px',
                content: $('#save_info'),
                btn: ['确定', '取消'],
                btnAlign: 'r' ,
                shade: [0.3, '#000'],
                // success:function(layero,index){
                //     $('[data-type="auto"]').each(function(){
                //         if($(this).is(':checked')){
                //             var inputArr = $(this).parents('.rule_switch').next('.rule_info').children('.rule_conditions').find('input[type="text"]');
                //             var something = '';
                //             inputArr.each(function(){
                //                 something += $.trim($(this).val());
                //             })
                //             if(something === ''){
                //                 util.mobile_alert('请填写升级规则');
                //                 layer.close(index);
                //                 return false;
                //             }
                //         }
                //     })
                // },
                yes:function(index, layero){
                    var level_data = get_level_data();
                    var level_name_1 = $('[name="level_name_1"]').val();
                    layer.close(index);
                    util.ajax_json('/admin/market/distribution/distributor/level/save',function (res) {
                        // console.log(res)
                        if(res.error == 0){
                            layer.msg('保存成功', {time: 2000},function () {
                                window.location.reload();
                            });
                        }else{
                            util.mobile_alert(res.message)
                        }
                    },{level_name_1: level_name_1,level_data:$.toJSON(level_data)});
                },
                btn2:function(index,layero){

                }
            })
        })
    });

    $('.add_distributer a').click(function(){
        var _this = $(this);
        var level_id = _this.attr('level_id');
        var user_ids = _this.attr('user_ids');
        // console.log(user_ids)
        layui.use('layer', function(){
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type:2,
                title: ['添加分销员', 'text-align:center;padding: 0px;'],
                offset: 'auto',
                area: ['900px','450px'],
                content: '/admin/market/distribution/distributor/popup/list?record_select_value=' + user_ids + '&top_level_id=' + level_id,
                btn: ['确定', '取消'],
                btnAlign: 'r' ,
                shade: [0.3, '#000'],
                yes:function(index, layero){
                    var body = layer.getChildFrame('body', index);
                    var distributor_ids = body.find('#record_select_value').val();
                    // if (distributor_ids == '') {
                    //     util.mobile_alert('请选择分销员');
                    //     return false;
                    // }
                    util.ajax_json('/admin/market/distribution/distributor/level/update',function (res) {
                        if(res.error == 0){
                            _this.attr('user_ids',distributor_ids);
                            layer.msg('添加分销员成功', {time: 2000},function () {
                                window.location.reload();
                            });
                        }else{
                            util.mobile_alert(res.message)
                        }
                    },{level_id:level_id,level_status:-1,level_user_ids:distributor_ids});
                },
                btn2:function(index,layero){

                }
            })
        })
    });
    
    function get_level_data() {
        var level_data = [];
        for(var i = 2; i <= 5; i ++){
            var level = { level_id: i };
            level.level_name = $("[name='level_name_" + i + "']").val();
            level.level_up_route = $("[name='up_route_" + i + "']:checked").val();
            if(level.level_up_route == 1){
                // level.level_user_ids = $("[name='user_ids_" + i + "']").val();
                level.invite_number = level.total_distribution_money = level.total_buy_money = 0;
            }else{
                level.invite_number = $("[name='invite_number_" + i + "']").val();
                level.total_distribution_money = $("[name='distribution_money_" + i + "']").val();
                level.total_buy_money = $("[name='buy_money_" + i + "']").val();
                level.level_user_ids = '';
            }
            level_data.push(level)
        }
        // console.log(level_data);
        return level_data;
    }
})
