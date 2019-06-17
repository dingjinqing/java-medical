<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/wx_auth_success.css?v=1.0.1" type="text/css"/>

<div style="min-width: 1090px;">
    <div class="title" style="margin: 0;">
        <span>支付宝小程序管理/ </span>
        <span style="color: #666;">小程序授权</span>
    </div>
    <div class="main-container">
        <div class="wx_auth_success clearfix">
            <div class="text-prompt">已绑定小程序</div>
            <div class="auth_info">
                <ul>
                    <li>
                        <span>小程序名称：</span>
                        <span>${mp->nick_name!}</span>
                        <a href="javascript:void(0);" class="auth-link auth_again">重新授权</a>
                    </li>

                    <li>
                        <span>小程序版本：</span>
                        <span>${mp->app_version!}</span>
                    </li>
                    <li>
                        <span>小程序状态：</span>
                        <span>${status_map[$mp->status]!}</span>
                        <#if ( $mp->is_auth_ok == 1 )
                            <a href="javascript:void(0);" class="auth-link upload-code-apply-audit">提交代码审核</a>
                        </#if>
                    </li>
                    <li>
                        <span>授权状态：</span>
                        <span><#if ($mp->is_auth_ok == 1)已授权 <#else> 未授权 </#if></span>
                    </li>

                    <li>
                        <span>小程序码：</span>
                        <img width="150"
                             src="${qrcode['qrcode_img'] ? image_url($qrcode['qrcode_img']) : "/image/admin/shop_default.png"!}"
                             alt=""/>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div id="set-auth">
    <p>只可重新授权当前授权的小程序</p>
</div>
<script type="text/javascript">
    $('.auth_again').click(function () {
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            //触发事件
            layer.open({
                type: 1
                , title: ['提示', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '300px'
                , id: 'layerDemoD' //防止重复弹出
                , content: $('#set-auth') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['重新授权', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //保存按钮的回调
                    window.open("/ali_mini/start/authorization");
                    //layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调

                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        });
    });

    $(".upload-code-apply-audit").click(function () {
        util.ajax_json("/ali_mini/mini/upload/apply/audit", function (d) {
            if (d && d.error > 0) {
                layer.alert(d.message);
            } else if (d && d.error == 0) {
                layer.msg(d.content);
                setTimeout(function () {
                    window.location.reload();
                }, 2000);
            }
        });
    });
    $('.bind_official').click(function () {
        var data = getPowerInfo('main_config', 'authorization', 'sub_2', '微信公众号授权');
        if (data.content == 1) {
            var app_id = $('[name="bind_official"]').val();
            util.ajax_json("/admin/public/service/bind/official", function (response) {
                if (response.error == 0) {
                    util.alert('绑定成功');
                    setTimeout(function () {
                        location.reload();
                    }, 1000);
                } else {
                    util.alert(response.message);
                }
            }, {app_id: app_id});
        }
    })
</script>


<#include "/admin/footer.ftl">
