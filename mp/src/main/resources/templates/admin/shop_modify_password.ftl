<#include "/admin/toggle_header.ftl">
    <link rel="stylesheet" href="/css/admin/shop_modify_passwd.css?v=1.0.13" type="text/css" />
    {{--<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />--!}
<div class="shop_container">
    <div class="account-title">
        <span style="color: #333;">账户设置</span>
    </div>
    <div class="order-container">
        <div class="shop-config">
            <form action="/admin/account/manage" id="form1" method="post">
                {{ csrf_field()!}
                <ul>
                    <li class="clearfix">
                        <div class="fl">登录账户：</div>
                        <div class="fl">
                            <input type="text" name='user_name' value="${user['user_name']!}" class="mdy-input" disabled/>
                            <a href="javascript:void(0);" class="update">修改登录密码</a>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">联系电话：</div>
                        <div class="fl">
                            <input type="text" name='mobile' value="${data_list->mobile!}" class="mdy-input" disabled="disabled"/>
                            {{--<a href="javascript:void(0);" class="change_number">修改手机号</a>--!}
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">账户昵称：</div>
                        <div class="fl">
                            <input type="text" name='account_name' value="${data_list->account_name!}" class="mdy-input"/>
                        </div>
                    </li>

                    <li class="clearfix">
                        <div class="fl">账户头像：</div>
                        <div class="fl logo-img add_img">
                            <img <#if ($data_list->shop_avatar != '') src="${data_list->shop_avatar!}" <#else> src="http://${image_domain!}/image/admin/icon_4.png" </#if> />
                            <input type="hidden" name="shop_avatar" value="${data_list->shop_avatar!}">
                            <span>修改头像</span>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">&nbsp;</div>
                        <div class="fl">
                            <button class="btn-save">确认修改</button>
                            <a href="/admin/account/shop/select" class="ret-list-btn">返回店铺列表</a>
                        </div>
                    </li>
                </ul>
            </form>
            <form action="" id="form2" method="post">
                {{ csrf_field()!}
                <ul>
                    <li class="clearfix">
                        <div class="fl">旧密码：</div>
                        <div class="fl">
                            <input type="password" name='pass_old' value="" class="mdy-input pass_old"/>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">新密码：</div>
                        <div class="fl">
                            <input type="password" name='pass' value="" class="mdy-input pass"/>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">确认新密码：</div>
                        <div class="fl">
                            <input type="password" name='pass_con' value="" class="mdy-input pass_con"/>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl">&nbsp;</div>
                        <div class="fl">
                            <button class="btn-save-pass"  onClick="submitAction();return false;">确认修改</button>
                            <a href="##" class="re-back">返回</a>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
    </div>
</div>
<div id="set-number">
    <ul>
        <li>
            <span>原手机号：</span>
            <input type="text" value="${data_list->mobile!}" name="old_number" disabled />
        </li>
        <li>
            <span>验证码：</span>
            <input type="text" />
            <button>获取短信验证码</button>
        </li>
        <li>
            <span>新手机号：</span>
            <input type="text" name="new_number"/>
        </li>
    </ul>
</div>

</form>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
{{--<script src="/js/layui/layui.js" type="text/javascript"></script>--!}
<script>
    var shop_id = '${res!}';
    if(shop_id > 0){
        util.mobile_alert('保存成功');
    }else if(shop_id==0){
        util.mobile_alert('保存失败');
    }
    $('.add_img').click(function() {
        var el = $(this);
        var w = 52;
        var h = 52;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find("img").attr("src", path);
                el.find('input').val(path);
            }
        });
    });
    $('.update').click(function(){
        $('#form1').hide();
        $('#form2').show();
        $('.account-title').find('span').html('密码修改');
    });
    $('.re-back').click(function(){
        $('#form1').show();
        $('#form2').hide();
        $('.account-title').find('span').html('账户设置');
    });
    // $('.btn-save-pass').click(function(){
    //     $('#form1').show();
    //     $('#form2').hide();
    //     $('.account-title').find('span').html('账户设置');
    // });
    $('.change_number').click(function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            //触发事件
            layer.open({
                type: 1
                ,title: ['修改手机号','text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: '430px'
                ,id: 'layerDemoD' //防止重复弹出
                ,content: $('#set-number') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['保存','取消']
                ,btnAlign: 'r' //按钮居右
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    layer.close(index);
                    //return false 开启该代码可禁止点击该按钮关闭
                },btn2: function(index, layero){
                    //按钮取消的回调

                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        });
    });

    function  submitAction(){
        var pass_old = $(".pass_old").val();
        var pass = $(".pass").val();
        var pass_con = $(".pass_con").val();
        if(pass_old == ""){
            $(".pass_old").focus();
            layer.msg('原密码不能为空');
            return false;
        }
        // else if (pass_old.length<6 || pass_old.length>16){
        //     $(".pass_old").focus();
        //     layer.msg('原密码长度为6到16位之间');
        //     return false;
        // }
        if(pass == ""){
            $(".pass").focus();
            layer.msg('新密码不能为空');
            return false;
        }else if (pass.length<6 || pass.length>16){
            $(".pass").focus();
            layer.msg('新密码长度为6到16位之间');
            return false;
        }
        if(pass_con == ""){
            $(".pass_con").focus();
            layer.msg('确认密码不能为空');
            return false;
        }else if (pass_con.length<6 || pass_con.length>16){
            $(".pass_con").focus();
            layer.msg('确认密码长度为6到16位之间');
            return false;
        }
        if(pass_con.length != pass.length || pass_con != pass){
            $(".pass_con").focus();
            layer.msg('新密码与确认密码不一致');
            return false;
        }
        var data={};
        data.pass_old = pass_old;
        data.pass = pass;
        data.pass_con = pass_con;
        util.ajax_json('/admin/account/manage',function(d){
            if(d&&d.error==0){
                util.mobile_alert(d.message);
                setTimeout(function(){
                    window.location.reload();
                },1200);
                return false;
            }
            else{
                util.mobile_alert(d.message);
                return false;
            }
        },data);
    }


</script>