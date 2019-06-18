$(function () {
    $(".choosed_service").click(function () {
        $(".put_down_hide").toggle();
    })
    $(".click_choose_tech").click(function () {
        $(".down_technician").toggle();
    })

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
            title: "添加会员",
            lock: true,
            opacity: 0.1,
            content: $('#edit_account')[0],
            okVal: "确定",
            ok: function () {
                newmobile = $('[name="edit_account_mobile"]').val();
                var re = /^1[3456789]{1}[0-9]{9}$/;
                if (!re.test(newmobile)) {
                    util.mobile_alert('手机号格式不正确');
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
                        util.mobile_alert('手机号已存在');
                        return false;
                    }
                }
                if (mobile.toString() == newmobile.toString() || haserror == 0) {
                    var param = $("#edit_account :input").serializeArray();
                    //alert(2)
                    util.ajax_json("/admin/ajax/account/user/edit", function (d) {
                        if (d && d.error == 0) {
                            util.mobile_alert('更新成功');
                            setTimeout(function () {
                                $('[name="act"]').val('');
                                $("#form1").submit();
                            }, 500);
                        } else {
                            util.mobile_alert('更新失败')
                        }
                    }, param)
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
    /*添加会员*/
    $('.btn_add_members a').on('click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            parent.layer.open({
                type: 2
                , title: ['添加会员', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['825px','430px']
                , content: '/admin/frame/user/select?flag=service' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var iframe = parent.layer.getChildFrame('body', index);
                    iframe.find('tbody').find('tr').each(function () {
                       if($(this).attr('user_id') == $("input[name='user_id']").val()){
                           $(this).addClass('goods_tr_choose');
                       }
                    });
                    iframe.find('tbody').find('tr').click(function () {
                        iframe.find('tbody').find('tr').removeClass('goods_tr_choose');
                        $(this).addClass('goods_tr_choose');
                    });
                }
                , yes: function (index, layero) {
                    //保存按钮的回调
                    var iframe = parent.layer.getChildFrame('body', index);
                    // var iframe = layer.getChildFrame('body', index);
                    var user=[];
                    var username;
                    var mobile ;
                    var user_id;
                    iframe.contents().find('.goods_tr_choose').each(function(){
                        user.push($(this).attr('user_id'));
                        user_id = ($(this).attr('user_id'));
                        username = ($(this).attr("username"));
                        mobile = ($(this).attr("mobile"));
                    });
                    if(iframe.contents().find('.goods_tr_choose').length>1){
                        util.mobile_alert("只能选择一个会员");
                    }else{
                        $("input[name='user']").val(user);
                        $("input[name='user_id']").val(user_id);
                        $("input[name='reserve_names']").val(username);
                        $("input[name='contact_mobile']").val(mobile);
                        $("input[name='reserve_names']").removeAttr("hidden");
                        $("#add_members").removeClass("choose_member").addClass("edit_member");
                        $("#add_members a").text("修改会员");
                        parent.layer.close(index);
                    }
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
});
