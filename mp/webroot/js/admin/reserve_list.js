//新增预约
$('.btn_add_activity').on('click',function(){
    var store_id = $(this).attr('store_id');
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['新增预约', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['600px','390px']
            // , area: ['825px','430px']
            , content: '/admin/frame/reserve/activity/add?store_id='+store_id //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {
            }
            , yes: function (index, layero) { //保存按钮的回调
                //可发送ajax
                var iframe = layer.getChildFrame('body', index);
                var param = {
                    "store_id":store_id,
                    "mobile" : iframe.contents().find('input[name="contact_mobile"]').val(),
                    "subscriber":iframe.contents().find("input[name='reserve_names']").val(),
                    "service_date":iframe.contents().find("input[name='reserve_arrive_time']").val(),
                    "service_id":iframe.contents().find("select[name='service_names']").val(),
                    "technician_id":iframe.contents().find("select[name='technician_names']").val(),
                    "add_message":iframe.contents().find("textarea[name='add_message']").val(),
                    "type" : 1,
                };
                param.store_id = store_id;
                param.user_id = iframe.contents().find("input[name='user_id']").val();
                var re = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
                if (!re.test(param.mobile) && param.mobile == '') {
                    util.mobile_alert("手机号不正确");
                    return false;
                }
                if(param.subscriber == '') {
                    util.mobile_alert("预约人不能为空");
                    return false;
                }
                if(param.service_date == '') {
                    util.mobile_alert("预约时间不能为空");
                    return false;
                } 
                if(param.service_id == 0) {
                    util.mobile_alert("预约服务不能为空");
                    return false;
                }
                util.ajax_json("/admin/ajax/reserve/addservice", function (d) {
                    if (d && d.error == 0) {
                        layer.msg("操作成功");
                        // layer.contect()
                        location.reload();
                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                }, param);

                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});

//添加备注
$('.add_note').on('click',function(){
    var store_id = $(this).attr('store_id');
    var order_id = $(this).attr("name");
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['添加备注', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['350px','240px']
            , content: '/admin/frame/reserve/note/add' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {

            }
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                var param = {
                    "admin_message":iframe.contents().find("textarea[name='admin_message']").val(),
                };
                // param.store_id = store_id;
                // param.order_id = $("#admin_meaage").attr("name");
                param.order_id = order_id;
                util.ajax_json("/admin/ajax/reserve/note/doadd", function (d) {
                    if (d && d.error == 0) {
                        layer.msg("操作成功");
                        // layer.contect()
                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                }, param);
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});



//添加技师分组
$('.manage-tech-group').on('click',function(){
    var store_id = $('input[name="store_id"]').val();
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['分组管理', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['750px']
            , content: '/admin/store/services/technician/group?store_id=' + store_id //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {

            }
            , yes: function (index, layero) { //保存按钮的回调
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});

// 取消订单
$('.btn_cansell').on('click',function(){
    var _this = $(this);
    layer.open({
        type: 1
        ,title: ["取消订单提示",'text-align:center;padding: 0px;']
        ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
        ,area: '300px'
        ,content: $("#set-delete") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        ,btn: ['确认','取消']
        ,btnAlign: 'r' //按钮居右
        ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
        ,yes: function(index, layero){ //确定按钮的回调
            // 提示是否取消预约
            var data={};
            data.order_id = _this.attr("order_id")
            // data.store_id = _this.attr("store_id");
            // data.order_status = _this.attr("order_status");
            data.cancelReason = $("input[name='cancelReason']").val();
            if(data.cancelReason.length < 1){
                util.mobile_alert('请输入取消原因');
                return false;
            }
            if(data.cancelReason.length > 50){
                util.mobile_alert('输入字数过长');
                return false;
            }
            util.ajax_json('/admin/store/services/reserve/cancelled',function(d){
                if(d&&d.error==0){
                    util.mobile_alert(d.content);
                    layer.close(index);
                    location.reload();
                }
                else{
                    util.mobile_alert(d.message);
                }
            },data);
        },btn2: function(index, layero){
            //按钮取消的回调

        }
    });
})


