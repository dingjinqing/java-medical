(function () {
    var mobile;
    var haschild = 3;
    window.edit_account = function (account_id) {
        var tr = $("tr[account_id=" + account_id + "]");
        var account_name = tr.attr('account_name');
        mobile = tr.attr('mobile');
        //alert(mobile)
        $('#edit_account .account_name').text(account_name);
        $('[name="edit_account_name"]').val(account_name);
        $('[name="edit_account_mobile"]').val(mobile);
        $('[name="edit_account_id"]').val(account_id);

        art.dialog({
            title: window.jslang.account_user_list.edit_account_info,
            lock: true,
            opacity: 0.1,
            content: $('#edit_account')[0],
            okVal: window.jslang.account_user_list.save,
            ok: function () {
                newmobile = $('[name="edit_account_mobile"]').val();
                var re = /^1[345789]{1}[0-9]{9}$/;
                if (!re.test(newmobile)) {
                    layer.ready(function () {
                        layer.msg('手机号格式不正确', {time: 2000});
                    });
                    return false;
                }
                haserror = 0;
                if (mobile.toString() != newmobile.toString()) {
                    //alert(1)
                    $.ajax({
                        type: "post",
                        url: "/admin/ajax/account/user/query",
                        dataType: 'json',
                        data: {mobile: newmobile},
                        success: function (data) {
                            if (data.error == 0) {
                                haserror = 0
                            } else {
                                haserror = 1;
                            }
                        },
                        'async': false
                    });
                    if (haserror == 1) {
                        layer.msg('手机号已存在');
                        return false;
                    }
                }
                if (mobile.toString() == newmobile.toString() || haserror == 0) {
                    var param = $("#edit_account :input").serializeArray();
                    //alert(2)
                    util.ajax_json("/admin/ajax/account/user/edit", function (d) {
                        if (d && d.error == 0) {
                            layer.ready(function () {
                                layer.msg('保存成功', {time: 2000});
                            });
                            setTimeout(function () {
                                $('[name="act"]').val('');
                                $("#form1").submit();
                            }, 500);
                        } else {
                            layer.ready(function () {
                                layer.msg('保存失败', {time: 2000});
                            });
                        }
                    }, param);
                    return true;
                }
            },
            cancel: true
        })
    }

    // $(document).ready(function () {
    //     util.init_zero_clipboard($("#copy_to_clip"));
    //     setTimeout(function () {
    //         $(".msg-info").fadeOut(500);
    //     }, 2000);
    // });
}());

//弹框
layui.use('layer', function() { //独立版的layer无需执行这一句
    var $ = layui.jquery, layer = layui.layer;

    //触发事件
    var active = {
        offset: function(othis){
            var type = othis.data('type')
                ,text = othis.data('title')
                ,id = othis.data('id');
            layer.open({
                type: 1
                ,title: [text,'text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: ['450px', '320px']
                ,id: 'layerDemo'+type //防止重复弹出
                ,content: $('#set-' + id) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['确定','取消']
                ,btnAlign: 'r' //按钮居右
                ,scrollbar: false
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    //当点击‘确定’按钮的时候，获取弹出层返回的值
                    // var res = window["layui-layer-iframe" + index].callbackdata();
                    //打印返回的值，看是否有我们想返回的值。
                    // console.log(res);
                    //最后关闭弹出层
                    // layer.close(index);
                    //return false 开启该代码可禁止点击该按钮关闭


                    var mobile = $('[name="mobile"]').val();

                    var re = /^1[345789]{1}[0-9]{9}$/;
                    if ($('[name="account_name"]').val().length == 0) {
                        util.mobile_alert("请输入用户名");
                        return false;
                    }
                    if (!re.test(mobile) && mobile.length == 0) {
                        util.mobile_alert("手机号不正确");
                        return false;
                    }
                    if ($('[name="account_pwd"]').val().length == 0) {
                        util.mobile_alert("请输入密码");
                        return false;
                    }
                    if ($('[name="account_pwd"]').val() != $('[name="confirm_account_pwd"]').val()) {
                        util.mobile_alert("请确认密码");
                        return false;
                    }

                    $('[name="act"]').val('add');
                    layer.ready(function () {
                        layer.msg('保存成功', {time: 2000},function () {
                            $("#form1").submit();
                        });
                    });
                },btn2: function(index, layero){
                    //按钮取消的回调

                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        }
    };
    $('.layui-btn').on('click', function(){
        var othis = $(this), method = othis.data('method');
        active[method] ? active[method].call(this, othis) : '';
    });
});
