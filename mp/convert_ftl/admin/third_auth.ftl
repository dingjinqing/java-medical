<#include "/admin/header.ftl">
    <link rel="stylesheet" href="/css/admin/shop_setting.css?v=1.2" type="text/css" />
    <link rel="stylesheet" href="/css/admin/coupon_manage.css">
    <style type="text/css">
        .btn-save:focus{
            background-color: #447af9 !important;
            border-color: #447af9 !important;
        }
        .btn-save:hover{
            background-color: #447af9 !important;
            border-color: #447af9 !important;
        }
        input[type='text']:focus {
            border: 1px solid #5a8bff;
            box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
            -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
            -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        }
        .erp-auth-li, .erp-auth-li  {
            border-bottom: 1px solid #eee;
            height: 50px;
            line-height: 50px;
        }
        .erp-auth-li span:first-child, .content6 .erp-auth-li span:first-child {
            display: inline-block;
            width: 20%;
        }
        .auth-tab {
            width: 100%;
            height: 50px;
            display: inline-block;
            border-bottom: 1px solid #ccc;
        }
        .auth-tab li {
            float: left;
            margin-right: 123px;
            line-height: 50px;
            cursor: pointer;
        }
        .auth-tab li.head-active {
            border-bottom: 2px solid #5a8bff;
        }

        .business_address input {
            width: 228px;
            height: 32px;
        }
    </style>
<div class="title">
    <span>基础配置 / </span><span style="color: #666;">第三方对接配置</span>
</div>
<div class="order-container">
    <div class="setting-head">
        <ul class="setting-head-ul clearfix">
            <li <#if ($act == 'erp')class="head-active"</#if>><a href="##">ERP授权配置</a></li>
            <li <#if ($act == 'pos')class="head-active"</#if>><a href="##">POS授权配置</a></li>
            <li <#if ($act == 'crm')class="head-active"</#if>><a href="##">CRM授权配置</a></li>
        </ul>
        <div class="setting-content">
            <div class="_content content1" <#if ($act == 'erp')style="display: block" </#if>>
                <ul style="width: 840px; border: 1px solid #ededed; padding: 0px 30px;">
                    <li class="erp-auth-li clearfix">
                        <span>服务名称</span>
                        <span class="pay-text">
                            ${app->app_name!}
                        </span>
                    </li>
                    <li class="erp-auth-li clearfix">
                        <span>ERP产品版本</span>
                        <span class="pay-text">
                            <select name="product" erp_id="${erpAuth->id!}" style="width: 200px; height: 32px;">
                                <option value="1" <#if  ($erpAuth->product == 1) selected </#if> >企业版</option>
                                <option value="2" <#if  ($erpAuth->product == 2) selected </#if>>旗舰版</option>
                            </select>
                        </span>
                    </li>
                    <li class="erp-auth-li clearfix">
                        <span>SessionKey</span>
                        <span class="pay-text">
                            ${erpAuth->session_key!}
                        </span>
                        (<a href="/admin/config/third?act=erp&auth_action=erp_auth&operate=reset&id=${erpAuth->id!}"  style="color: rgb(90, 139, 255);" class="auth_reset">重置</a>)
                    </li>
                    <li class="erp-auth-li clearfix">
                        <span>是否已授权</span>
                        <span class="pay-text">
                            <#if  ($erpAuth->status == 1) 已授权 <#else> 未授权 </#if>
                        </span>
                    </li>
                    <li class="erp-auth-li clearfix">
                        <span>操作</span>
                        <span class="pay-text">
                            <#if  ($erpAuth->status == 1)
                                <a href="/admin/config/third?act=erp&auth_action=erp_auth&operate=or_enable&status=0&id=${erpAuth->id!}" style="color: rgb(90, 139, 255);" class="del_auth">删除授权</a>
                            <#else>
                                <a href="/admin/config/third?act=erp&auth_action=erp_auth&operate=or_enable&status=1&id=${erpAuth->id!}" style="color: rgb(90, 139, 255);" class="to_auth" auth_action="to_auth_erp">授权</a>
                            </#if>
                            </span>
                    </li>
                    <li class="erp-auth-li business_address clearfix" style="height: 300px;">
                        <div class="text-prompt">商家收货地址</div>
                        <form action="/admin/config/third?act=erp&operate=business_address" method="post">
                            {{ csrf_field()!}
                            <div>
                                <span>收件人：</span>
                                <span><input type="text" name="consignee" value="${businessAdress['consignee']!}"/></span>
                            </div>
                            <div>
                                <span>退货地址：</span>
                                <span><input type="text" name="return_address" value="${businessAdress['return_address']!}"/></span>
                            </div>
                            <div>
                                <span>商家电话：</span>
                                <span><input type="text" name="merchant_telephone" value="${businessAdress['merchant_telephone']!}"/></span>
                            </div>
                            <div>
                                <span>邮编：</span>
                                <span><input type="text" name="zip_code" value="${businessAdress['zip_code']!}"/></span>
                            </div>
                            <input type="submit" value="保存" style="width: 50px; line-height: 26px; margin: 10px 336px; background: rgb(90, 139, 255);" />
                        </form>
                    </li>
                </ul>
            </div>
            <div class="_content content2" <#if ($act == 'pos')style="display: block" </#if>>
                <ul style="width: 840px; border: 1px solid #ededed; padding: 0px 30px; margin-top: 20px;">
                    <li class="erp-auth-li clearfix">
                        <span>服务名称</span>
                        <span class="pay-text">
                            ${posApp->app_name!}
                        </span>
                    </li>
                    {{--<li class="erp-auth-li clearfix">
                        <span>SessionKey</span>
                        <span class="pay-text">
                            ${posAuth->session_key!}
                        </span>
                        (<a href="/admin/config/third?act=pos&auth_action=pos_auth&operate=reset&id=${posAuth->id!}"  style="color: rgb(90, 139, 255);">重置</a>)
                    </li>--!}
                    <form action="/admin/config/third?act=pos&auth_action=pos_auth&operate=resetPos&id=${posAuth->id!}" method="post">
                        {{ csrf_field()!}
                        <li class="erp-auth-li clearfix">
                            <span>PosSessionKey</span>
                            <span class="pay-text">
                                <input type="text" name="pos_session_key" value="${posAuth->pos_session_key!}" style="height: 32px; width: 228px;" />
                            </span>
                            <button type="submit" style="line-height: 24px; background: rgb(90, 139, 255);">提交</button>
                        </li>
                    </form>

                    <li class="erp-auth-li clearfix">
                        <span>是否已授权</span>
                        <span class="pay-text">
                            <#if  ($posAuth->status == 1) 已授权 <#else> 未授权 </#if>
                        </span>
                    </li>
                    <li class="erp-auth-li clearfix">
                        <span>操作</span>
                        <span class="pay-text">
                            <#if  ($posAuth->status == 1)
                                <a href="/admin/config/third?act=pos&auth_action=pos_auth&operate=or_enable&status=0&id=${posAuth->id!}" style="color: rgb(90, 139, 255);" class="del_auth">删除授权</a>
                            <#else>
                                <a href="/admin/config/third?act=pos&auth_action=pos_auth&operate=or_enable&status=1&id=${posAuth->id!}" style="color: rgb(90, 139, 255);" class="to_auth" auth_action="to_auth_pos">授权</a>
                            </#if>
                            <a href="/admin/config/third?act=pos&operate=sync_store" style="color: rgb(90, 139, 255); margin-left: 50px; font-weight: bold;" class="sync_store">同步POS门店</a>
                            <span style="margin-left: 20px;">注：仅同步未对接门店</span>
                            <a href="/admin/config/third?act=pos&operate=sync_goods" style="color: rgb(90, 139, 255); margin-left: 50px; font-weight: bold;" class="sync_store">同步POS商品到系统商品</a>
                        </span>
                    </li>
                </ul>
            </div>
            <div class="_content content3" <#if ($act == 'crm')style="display: block" </#if>>
                <ul style="width: 840px; border: 1px solid #ededed; padding: 0px 30px;">
                    <li class="erp-auth-li clearfix">
                        <span>服务名称</span>
                        <span class="pay-text">
                            ${crmApp->app_name!}
                        </span>
                    </li>
                    <li class="erp-auth-li clearfix">
                        <span>SessionKey</span>
                        <span class="pay-text">
                            ${crmAuth->session_key!}
                        </span>
                        (<a href="/admin/config/third?act=crm&auth_action=pos_auth&operate=reset&id=${crmAuth->id!}"  style="color: rgb(90, 139, 255);" class="auth_reset">重置</a>)
                    </li>
                    <form action="/admin/config/third?act=crm&operate=crm_auth&id=${crmAuth->id!}" method="post">
                        {{ csrf_field()!}
                        <li class="erp-auth-li clearfix">
                            <span>AppKey</span>
                            <span class="pay-text">
                                <input type="text" name="app_key" value="${crmAuth->app_key!}" style="height: 32px; width: 228px;" />
                            </span>
                        </li>
                        <li class="erp-auth-li clearfix">
                            <span>AppSecret</span>
                            <span class="pay-text">
                                    <input type="text" name="app_secret" value="${crmAuth->app_secret!}" style="height: 32px; width: 228px;" />
                                </span>
                            <button type="submit" style="line-height: 24px; background: rgb(90, 139, 255);">提交</button>
                        </li>
                    </form>
                    <li class="erp-auth-li clearfix">
                        <span>是否已授权</span>
                        <span class="pay-text">
                            <#if  ($crmAuth->status == 1) 已授权 <#else> 未授权 </#if>
                        </span>
                    </li>
                    <li class="erp-auth-li clearfix">
                        <span>操作</span>
                        <span class="pay-text">
                            <#if  ($crmAuth->status == 1)
                                <a href="/admin/config/third?act=crm&auth_action=crm_auth&operate=or_enable&status=0&id=${crmAuth->id!}" style="color: rgb(90, 139, 255);" class="del_auth">删除授权</a>
                            <#else>
                                <a href="/admin/config/third?act=crm&auth_action=crm_auth&operate=or_enable&status=1&id=${crmAuth->id!}" style="color: rgb(90, 139, 255);" class="to_auth" auth_action="to_auth_crm">授权</a>
                            </#if>
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<#include "/admin/footer.ftl">
<script src="/js/admin/util.js?v=1.0.1" type="text/javascript"></script>
<script>

    $('.setting-head-ul').on('click','li',function(){
        $(this).addClass('head-active').siblings().removeClass('head-active');
        $(this).parent().next().find('._content').eq($(this).index()).show().siblings().hide();
    });

    $(".sync_store").click(function () {
        layer.msg('同步中...', {time: 10000});
    })

    $('.auth_reset').click(function () {
        event.preventDefault();
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '<span style="color:red;">请谨慎重置</span>，确定要这样操作吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var href = that.attr('href');
                window.location.href = href;
                layer.close(index);
            });
        });
    })
    $('.del_auth').click(function () {
        event.preventDefault();
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '<span style="color:red;">请谨慎操作，取消授权后数据将不再推送</span>，确定要取消授权吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var href = that.attr('href');
                window.location.href = href;
                layer.close(index);
            });
        });
    })
    $('.to_auth').click(function () {
        event.preventDefault();
        var auth_action = $(this).attr('auth_action');
        if (auth_action == 'to_auth_erp') {
            if ($('[name="consignee"]').val() == '' ||
                $('[name="return_address"]').val() == '' ||
                $('[name="merchant_telephone"]').val() == '' ||
                $('[name="zip_code"]').val() == '') {
                layer.msg('请先填写商家收货地址');
                return false;
            }
        } else if (auth_action == 'to_auth_pos') {
            if ($('[name="pos_session_key"]').val() == '') {
                layer.msg('请先填写PosSessionKey');
                return false;
            }
        } else if (auth_action == 'to_auth_crm') {
            if ($('[name="app_key"]').val() == '' || $('[name="app_secret"]').val() == '') {
                layer.msg('请先填写AppKey, AppSecret');
                return false;
            }
        }
        window.location.href = $(this).attr('href');
    });

    // 切换ERP产品
    $('.content1 [name="product"]').change(function () {
        var id = $(this).attr('erp_id');
        var val = $(this).val();
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '<span style="color:red;">请谨慎操作，切换产品版本对应不同的ERP产品</span>，确定要操作吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                location.href = '/admin/config/third?act=erp&product='+val+'&id='+id;
                layer.close(index);
            });
        });
        var index = val == 1 ? 2 : 1;
        $('.content1 [name="product"] option[value='+index+']').prop('selected', 'selected');
    })

    var errorMsg = '${errorMsg!}';
    if (errorMsg != '') {
        layer.msg(errorMsg);
    }
</script>
