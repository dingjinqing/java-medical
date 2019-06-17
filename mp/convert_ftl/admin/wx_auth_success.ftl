<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/wx_auth_success.css?v=1.0.2" type="text/css" />
<style type="text/css">
    .orderinfo_page {
        float: left;
        margin-right: 100px;
    }

    .orderinfo_page>div {
        float: left;
    }

    .orderinfo_page>div a {
        color: #5a8bff;
        margin-left: 10px;
    }

    .orderinfo_page>div input[type='checkbox'] {
        top: 1px;
        margin-right: 10px;
    }

    .look_ex {
        position: relative;
    }

    .look_ex div {
        position: absolute;
        width: 100px;
        height: 100px;
        display: none;
    }

    .look_ex div img {
        position: absolute;
        top: -390px;
        left: 65px;
        z-index: 99;
    }
    .hover_show:before{
        top:269px !important;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title" style="margin: 0;">
        <span>小程序管理/ </span>
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
                        {{--<a href="/wechat/official/account/authorization" target="_blank">授权公众号</a>--!}
                    </li>
                    <li>
                        <#if  (!$mp->link_official_app_id)
                        <span>请选择需要绑定的公众号：</span>
                        <select name="bind_official" id="">
                            <option value="">请选择</option>
                            <#list  ($officialList as $item)
                            <#if  ($item['bind_open_app_id'])
                            <option value="${item['app_id']!}">${item['nick_name']!}</option>
                            </#if>
                            </#list>
                        </select>
                        <button class="auth_btn bind_official">确定</button>
                        <#if  (empty($mp->principal_name))
                        <span>请优先<a href="javascript:void(0);" class="auth-link auth_again"
                                style="margin-left: 0px;">重新授权</a>小程序， 再授权公众号</span>
                        <#else>

                        <#if  (empty($officialList))
                        <span style="display: inline-block;margin-left:20px;">暂无公众号可绑定，立即</span>
                        <a href="/wechat/official/account/authorization" target="_blank"
                            style="margin-left:0; color: #5A8BFF;">添加授权</a>
                        <#else>
                        <p class="auth_p">仅可以绑定和该店铺同主体的公众号，绑定后不可以更换，请您谨慎选择</p>
                        </#if>
                        </#if>
                        <#elseif> (!$officialAccount->is_auth_ok)
                        <span>已绑定的公众号：</span>
                        ${officialAccount->nick_name!} <span
                            style="margin-left: 20px; color: #b94a48;">此公众号已取消授权，无法给用户发送公众号消息、分销员返利佣金不能提现，如有需要，请重新授权</span>
                        <#else>
                        <span>已绑定的公众号：</span>
                        ${officialAccount->nick_name!}
                        </#if>
                    </li>
                    <li>
                        <span>小程序版本：</span>
                        <span>${mp_version->user_version!}</span><#if ($current_template_id != $mp->bind_template_id)
                        <span class="text-danger">（最新版本${current_version->user_version!}）</span>
                        </#if>

                    </li>
                    <li>
                        <span>审核状态：</span>
                        <span>${audit_state_map[$mp->audit_state]!}</span>
                        <#if ( $mp->is_auth_ok == 1
                        && ( ($current_template_id == $mp->bind_template_id) && in_array($mp->audit_state,[0,3])
                        || ($current_template_id != $mp->bind_template_id) && in_array($mp->audit_state,[0,2,3])
                        )
                        )
                        <a href="javascript:void(0);" class="auth-link upload-code-apply-audit">提交代码审核</a>
                        </#if>
                    </li>
                    <li>
                        <span>授权状态：</span>
                        <span><#if ($mp->is_auth_ok == 1)已授权 <#else> 未授权 </#if></span>
                    </li>
                    <li>
                        <span>小程序头像：</span>
                        <img src="${mp->head_img!}" width="100" alt="" />
                    </li>
                    <li>
                        <span>小程序码：</span>
                        <img width="150"
                            src="${qrcode['qrcode_img'] ? $qrcode['qrcode_img'] : "/image/admin/shop_default.png"!}"
                            alt="" />
                    </li>
                    <li>
                        <span>微信认证：</span>
                        <span><#if ($mp->verify_type_info == 0)已认证 <#else> 未认证 </#if></span>
                    </li>
                    <li>
                        <span>原始ID：</span>
                        <span>${mp->user_name!}</span>
                    </li>
                    <li>
                        <span>AppID：</span>
                        <span>${mp->app_id!}</span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="wx_auth_success clearfix">
            <div class="text-prompt" id="goods_circle">微信好物圈功能设置</div>
            <div style="padding:20px 25px">
                <div class="check-box-container <#if ($enabled_wx_shopping_list) checked  </#if>"
                    <#if ($enabled_wx_shopping_list) </#if>>
                    <label>
                        <input type="checkbox" name="enabled_wx_shopping_list" <#if ($enabled_wx_shopping_list)
                            checked="true" </#if>>
                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" />
                    </label>
                </div>
                <div class="label-check">开关开启，小程序前端可同步购物车商品及支付完成订单信息到微信好物圈，助力享有小程序搜索能力。<a
                        href="/wechat/mini/goodslist?top_index=1" target="_blank" style="color: #5a8bff;">功能介绍</a></div>
                {{--<div class="" style="margin-left: 50px;color: #999;margin-top:10px;">
                    <text style="color:#ff0000">注：</text>开启或关闭【微信好物圈】，需重新授权小程序并勾选/取消勾选好物圈权限，才能生效
                </div>
                <div class="if_open" style="margin-top: 10px;margin-left:25px;">
                    <input type="radio" name="" style="margin-right: 10px">启用好物推荐 <text style="color: #999;margin-left: 5px">启用后，用户可在小程序前端主动推荐商品至微信购物圈</text>
                </div>--!}
                <div class="" style="margin-left: 50px;color: #999;margin-top:10px;">
                    <text style="color:#ff0000">注：</text>开启或关闭【好物圈】，需重新授权小程序并勾选/取消勾选好物圈权限，才能生效
                </div>
                <div class="" style="margin-left: 50px;margin-top:10px;">
                    【好物推荐】在小程序端展示位置：
                </div>
                <div style="margin-left: 50px;margin-top: 10px" class="clearfix">
                    <div class="orderinfo_page clearfix">
                        <div class="checkbox_item">
                            <input type="checkbox" name="shipping_recommend[]" value="2" <#if  ($wx_shopping_recommend &&
                                in_array(2, explode(',', $wx_shopping_recommend))) checked </#if>>订单详情页
                        </div>
                        <div class="look_order ">
                            <a href="javascript:;" class="show_eg">查看示例
                                <div class="hover_show" style = 'top:-266px'>
                                    <img src="http://${image_domain!}/image/admin/new_preview_image/order_thing.jpg" alt="">
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="orderinfo_page clearfix">
                        <div class="checkbox_item">
                            <input type="checkbox" name="shipping_recommend[]" value="1" <#if  ($wx_shopping_recommend &&
                                in_array(1, explode(',', $wx_shopping_recommend))) checked </#if>>商品详情页
                        </div>
                        <div class="look_item ">
                            <a href="javascript:;" class="show_eg">查看示例
                                <div class="hover_show"  style = 'top:-266px'>
                                    <img src="http://${image_domain!}/image/admin/new_preview_image/goods_thing.jpg" alt="">
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
                <div style="margin-left: 50px; margin-top: 10px;" class="clearfix">
                    <#if  ($mp)
                    <#if  (!empty($plugin))
                    当前【${plugin['nickname']!}】${pulginStatusMp[$plugin['status']]!}
                    <#else>
                    好物圈插件尚未申请， <a href="javascript:void(0)" onclick="submit_apply_plugin()"
                        style="color: #5a8bff; margin-top: 20px;">提交申请</a>
                    </#if>
                    </#if>
                    <div style="color:red">注：好物圈插件申请后，小程序公众平台后台不要关闭或者删除此插件，负责小程序将无法正常使用。若想关闭此功能，请联系客服、运营人员，协助操作。</div>
                </div>
                <input type="button" class="btn btn-primary" value="保存" onclick="save_shipping_cart()"
                    style="background: #5a8bff; margin-left: 50px; margin-top: 20px;">
            </div>
        </div>
        <div class="wx_auth_success clearfix">
            <div class="text-prompt">短信设置</div>
            <div style="padding:20px 25px">
                <#if  (!$shopInfo->sms_account)
                <input type="text" name="sms_account" style="height: 30px;" />
                <a class="add_sms_account" style="color: #5a8bff; cursor: pointer;">创建充值账号</a>
                <#else>
                当前充值账号是： <span style="font-weight: bold;">${shopInfo->sms_account!}</span>，
                剩余金额： <span style="font-weight: bold;">￥${smsAccount['balance'] ?? 0.00!}</span>，
                剩余短信条数： <span style="font-weight: bold;">${smsAccount['sms_num'] ?? 0!}</span>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <#if  (stripos(request()->url(), 'mp.weipubao.cn') === false)
                <a target="_blank"
                    href="http://101.200.202.174/sms/api/alipay/index.php?sms_account=${shopInfo->sms_account!}"
                    style="color: #5a8bff; cursor: pointer;">前往充值</a>
                <#else>
                <a target="_blank" href="http://alipay.wangdian.cn?sms_account=${shopInfo->sms_account!}"
                    style="color: #5a8bff; cursor: pointer;">前往充值</a>
                </#if>
                </#if>
            </div>
        </div>
    </div>

</div>
<div id="set-auth">
    <p>只可重新授权当前授权的小程序</p>
</div>
<#include ('admin.preview_common')
<script type="text/javascript">
    $('.auth_again').click(function () {
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery,
                layer = layui.layer;
            //触发事件
            layer.open({
                type: 1,
                title: ['提示', 'text-align:center;padding: 0px;'],
                offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    ,
                area: '300px',
                id: 'layerDemoD' //防止重复弹出
                    ,
                content: $('#set-auth') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,
                btn: ['重新授权', '取消'],
                btnAlign: 'r' //按钮居右
                    ,
                shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,
                yes: function (index, layero) { //保存按钮的回调
                    window.open("/wechat/start/authorization");
                    //layer.close(index);
                },
                btn2: function (index, layero) {
                    //按钮取消的回调

                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        });
    });

    $(".upload-code-apply-audit").click(function () {
        util.ajax_json("/wechat/mini/upload/apply/audit", function (d) {
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
            }, {
                app_id: app_id
            });
        }
    })

    $('[name="enabled_wx_shopping_list"]').change(function () {
        var checked = $(this).prop('checked');
        if (checked) {
            $(this).parents(".check-box-container").addClass("checked");
        } else {
            $(this).parents(".check-box-container").removeClass("checked");
        }
        util.ajax_json("/wechat/mini/switch/shopping/list", function () {
            layer.msg("设置成功");
        }, {
            enabled_wx_shopping_list: checked ? 1 : 0
        });
    });
    $('.add_sms_account').click(function () {
        var sms_account = $('[name="sms_account"]').val();
        if (sms_account == '') {
            util.mobile_alert('请填写账号名称');
            return false;
        }
        util.ajax_json("/wechat/mini/create/smsaccount", function (res) {
            if (res.error == 0) {
                util.mobile_alert('创建成功');
                location.reload();
            } else {
                layer.msg(res.message);
            }
        }, {
            sms_account: sms_account
        });
    })

    $(".look_order").hover(function () {
        $(".order_example").css("display", 'block')
    }, function () {
        $(".order_example").css("display", 'none')
    })

    $(".look_item").hover(function () {
        $(".item_example").css("display", 'block')
    }, function () {
        $(".item_example").css("display", 'none')
    })

    function save_shipping_cart() {
        var shipping_recommend = [];
        var checked = $('[name="enabled_wx_shopping_list"]').prop('checked');
        $('[name="shipping_recommend[]"]:checked').each(function () {
            shipping_recommend.push($(this).val());
        })
        util.ajax_json("/wechat/mini/switch/shopping/list", function () {

            layer.msg("设置成功");
        }, {
            enabled_wx_shopping_list: checked ? 1 : 0,
            wx_shopping_recommend: shipping_recommend.join(',')
        });
    }

    function submit_apply_plugin() {
        util.ajax_json("/wechat/mini/apply/plugin", function (response) {
            if (response.error == 0) {
                layer.msg("设置成功");
                location.reload();
            } else {
                layer.msg(response.message);
            }
        });
    }
</script>


<#include "/admin/footer.ftl">