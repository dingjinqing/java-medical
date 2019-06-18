$(function () {
    $('.add_group').click(function(){
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                type: 1,
                title: ['添加分销员分组', 'text-align:center;padding: 0px;'],
                area: '315px',
                content: '<div style="text-align: center;padding-top:10px;">' + '分销员分组名称: <input type="text" class="new_group_name" margin-left:15px;>' + '</div>',
                btn: ['确定', '取消'],
                yes:function(index){
                    let group_name = $('.new_group_name').val();
                    if (!group_name) {
                        util.mobile_alert('分组名称不能为空');
                        return false;
                    }
                    util.ajax_json('/admin/market/distributor/group/update',function (res) {
                        if(res.error == 0){
                            layer.msg('添加分销员分组成功', {time: 1500},function () {
                                window.location.reload();
                            });
                        }else{
                            util.mobile_alert(res.message);
                            return false;
                        }
                    },{action:'add_group',group_name:group_name});
                }
            })
        });
    });
    $('.edit').click(function(){
        var group_id = $(this).attr('group_id');
        var old_group_name = $(this).parent().parent().find('td').eq(0).text();
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                type: 1,
                title: ['修改分销员分组', 'text-align:center;padding: 0px;'],
                area: '315px',
                content: '<div style="text-align: center;padding-top:10px;">' + '分销员分组名称: <input type="text" class="new_group_name">' + '</div>',
                btn: ['确定', '取消'],
                success: function() {
                    $('.new_group_name').val(old_group_name);
                },
                yes:function(index){
                    let group_name = $('.new_group_name').val();
                    if (!group_name) {
                        util.mobile_alert('分组名称不能为空');
                        return false;
                    }
                    util.ajax_json('/admin/market/distributor/group/update',function (res) {
                        if(res.error == 0){
                            layer.msg('修改分销员分组成功', {time: 1500},function () {
                                window.location.reload();
                            });
                        }else{
                            util.mobile_alert(res.message);
                            return false;
                        }
                    },{action:'edit_group', group_id:group_id, group_name:group_name});
                }
            })
        });
    });
    $('.del').click(function(){
        var group_id = $(this).attr('group_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">确认要删除该分组吗？</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/admin/market/distributor/group/update',function (res) {
                    if(res.error == 0){
                        layer.msg('删除分销员分组成功', {time: 1500},function () {
                            window.location.reload();
                        });
                    }else{
                        util.mobile_alert(res.message);
                        layer.close(index);
                    }
                },{action:'delete_group', group_id:group_id});
            });
        });
    });
    $('.default').click(function(){
        var group_id = $(this).attr('group_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">确认要设为默认分组吗？</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/admin/market/distributor/group/update',function (res) {
                    if(res.error == 0){
                        layer.msg('修改成功', {time: 500},function () {
                            window.location.reload();
                        });
                    }else{
                        util.mobile_alert(res.message);
                        layer.close(index);
                    }
                },{action:'default_group', group_id:group_id});
            });
        });
    });
    $('.remove_default').click(function(){
        var group_id = $(this).attr('group_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">确认要取消默认分组吗？</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/admin/market/distributor/group/update',function (res) {
                    if(res.error == 0){
                        layer.msg('取消默认分销员分组成功', {time: 1500},function () {
                            window.location.reload();
                        });
                    }else{
                        util.mobile_alert(res.message);
                        layer.close(index);
                    }
                },{action:'remove_default_group', group_id:group_id});
            });
        });
    })
    $('.add_distributer').click(function(){
        var _this = $(this);
        var group_id = _this.attr('group_id');
        // var user_ids = _this.attr('user_ids');
        layui.use('layer', function(){
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type:2,
                title: ['添加分销员', 'text-align:center;padding: 0px;'],
                offset: 'auto',
                area: ['900px','450px'],
                content: '/admin/market/distribution/distributor/popup/list?except_group_id=' + group_id,
                btn: ['确定', '取消'],
                btnAlign: 'r' ,
                shade: [0.3, '#000'],
                yes:function(index, layero){
                    var body = layer.getChildFrame('body', index);
                    var distributor_ids = body.find('#record_select_value').val();
                    util.ajax_json('/admin/market/distributor/group/update',function (res) {
                        if(res.error == 0){
                            layer.msg('添加分销员成功', {time: 1500},function () {
                                window.location.reload();
                            });
                        }else{
                            util.mobile_alert(res.message)
                        }
                    },{action:'update_group_user', group_id:group_id, group_user_ids:distributor_ids});
                }
            })
        })
    });
    $('.btn_seach').click(function () {
        $("#form1").submit();
    })
});