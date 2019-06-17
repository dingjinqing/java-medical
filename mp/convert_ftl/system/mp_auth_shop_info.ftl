<#include ("system.header")

<#include ("system.mp_tab")


<form enctype="multipart/form-data" action="/system/mp/info?app_id=${app_id!}" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" id="app_id" value="${app_id!}">
    {{ csrf_field()!}
    <input type="hidden" id="content" name="content">
</form>

<#if ($message)
    <div class="box">
        <div class="alert alert-block alert-warning">
            ${message!}
        </div>
    </div>
</#if>

<div class="box">
    <input type="hidden" id="app_id" value="${app_id!}">
    <div class="row">
        <div class="col-sm-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    授权基本信息
                    <button class="btn btn-success btn-update-mp">更新授权信息</button>
                </div>
                <div class="panel-body">
                    <table class=" table table-bordered">
                        <tr>
                            <td>
                                店铺ID
                            </td>
                            <td>
                                ${mp_auth_shop->shop_id!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                app_id
                            </td>
                            <td>
                                ${mp_auth_shop->app_id!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                主体名称
                            </td>
                            <td>
                                ${mp_auth_shop->principal_name!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                昵称
                            </td>
                            <td>
                                ${mp_auth_shop->nick_name!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                用户名
                            </td>
                            <td>
                                ${mp_auth_shop->user_name!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                别名
                            </td>
                            <td>
                                ${mp_auth_shop->alias!}
                            </td>
                        </tr>

                        <tr>
                            <td>
                                头像
                            </td>
                            <td>
                                <img src=" ${mp_auth_shop->head_img!}" style="width: 50px">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序二维码
                            </td>
                            <td>
                                <#if ($mp_auth_shop->qrcode_url)
                                    <img src="{{ image_url($mp_auth_shop->qrcode_url)!}" style="width: 100px">
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序授权权限：
                            </td>
                            <td>
                                <#list ($func_info as $func)
                                    <p>
                                        <#if ($func['has'] == 1)
                                            <span class="label label-success">已授权</span>
                                        <#else>
                                            <span class="label label-danger">未授权</span>
                                        </#if>
                                        ${func['name']!}
                                    </p>
                                </#list>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                是否认证
                            </td>
                            <td>
                                <#if ($mp_auth_shop->verify_type_info == "-1")
                                    <span class="label label-danger">未认证</span>
                                <#elseif>($mp_auth_shop->verify_type_info == "0")
                                    <span class="label label-success">已认证</span>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                是否支持微信支付
                            </td>
                            <td>
                                <#if ( $mp_auth_shop->open_pay == 1)
                                    <span class="label label-success">是</span>
                                <#else>
                                    <span class="label label-danger">否</span>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                是否支持卡券
                            </td>
                            <td>
                                <#if ( $mp_auth_shop->open_card == 1)
                                    <span class="label label-success">是</span>
                                <#else>
                                    <span class="label label-warning">否</span>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                是否授权
                            </td>
                            <td>
                                <#if ( $mp_auth_shop->is_auth_ok == 1)
                                    <span class="label label-success">已授权</span>
                                <#else>
                                    <span class="label label-danger">已取消授权</span>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                最后授权时间
                            </td>
                            <td>
                                ${mp_auth_shop->last_auth_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                最后取消授权时间
                            </td>
                            <td>
                                ${mp_auth_shop->last_cancel_auth_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                记录时间
                            </td>
                            <td>
                                ${mp_auth_shop->create_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                商户号
                            </td>
                            <td>
                                ${mp_auth_shop->pay_mch_id!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                商户密钥
                            </td>
                            <td>
                                ${mp_auth_shop->pay_key!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                证书内容
                            </td>
                            <td>
                                <div class="text-overflow"
                                     title="${mp_auth_shop->pay_cert_content!}">${mp_auth_shop->pay_cert_content!}</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                私钥内容
                            </td>
                            <td>
                                <div class="text-overflow"
                                     title="${mp_auth_shop->pay_key_content!}">${mp_auth_shop->pay_key_content!}</div>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                子商户模式
                            </td>
                            <td>
                                <#if ($mp_auth_shop->is_sub_merchant == 0)非子商户
                                <#elseif> ($mp_auth_shop->is_sub_merchant == 1)微铺宝子商户
                                <#elseif> ($mp_auth_shop->is_sub_merchant == 2)通联子商户
                                </#if>
                                <button class="btn btn-success btn-set-sub-merchant"
                                        is_sub_merchant="${mp_auth_shop->is_sub_merchant!}">设置子商户
                                </button>
                                <div class="padding-top-10 text-danger">
                                    谨慎设置子商户，设置之前，请确认商户支付已经申请成功。
                                </div>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                通联子商户支付配置
                            </td>
                            <td>
                                通联支付子商户APPID：<span
                                        class="union_pay_app_id">${mp_auth_shop->union_pay_app_id!}</span><br>
                                通联支付子商户商户号：<span class="union_pay_cus_id">${mp_auth_shop->union_pay_cus_id!}</span>
                                <br>
                                通联支付子商户密钥：<span
                                        class="union_pay_app_key">${mp_auth_shop->union_pay_app_key!}</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    上传审核发布信息
                    <a href="/system/mp/operate/log/list?app_id=${mp_auth_shop->app_id!}"
                       target="_blank">查看版本操作日志</a>
                    <button class="btn btn-success btn-upload-audit">一键提交审核</button>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered">

                        {{--<tr>--!}
                        {{--<td>--!}
                        {{--authorizer_info--!}
                        {{--</td>--!}
                        {{--<td>--!}
                        {{--${mp_auth_shop->authorizer_info!}--!}
                        {{--</td>--!}
                        {{--</tr>--!}
                        {{--<tr>--!}
                        {{--<td>--!}
                        {{--authorization_info--!}
                        {{--</td>--!}
                        {{--<td>--!}
                        {{--${mp_auth_shop->authorization_info!}--!}
                        {{--</td>--!}
                        {{--</tr>--!}

                        <tr>
                            <td width="180px">
                                设置服务器域名
                            </td>
                            <td>
                                ${mp_auth_shop->is_modify_domain!}
                                <button class="btn btn-success btn-modify-domain">修改域名</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                绑定代码模板ID
                            </td>
                            <td>
                                ${mp_auth_shop->bind_template_id!}
                                <button class="btn btn-success btn-upload">上传代码（模板ID：${current_template_id!}）
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                上传代码状态
                            </td>
                            <td>
                                ${mp_auth_shop->upload_state ? "已上传":"未上传"!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                最后上传代码时间
                            </td>
                            <td>
                                ${mp_auth_shop->last_upload_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序体验者
                            </td>
                            <td>
                                <#list ($mp_auth_shop->tester as $tester)
                                    <span class="tester">${tester!}
                                        <a href="javascript:void(0)" tester="${tester!}"
                                           class="btn btn-link btn-remove-tester"><b>x</b></a>
                                    </span>
                                    &nbsp;&nbsp;
                                </#list>
                                <button class="btn btn-success btn-add-tester">添加微信体验者</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                体验二维码
                            </td>
                            <td>
                                <#if ($mp_auth_shop->upload_state == 1 && $mp_auth_shop->test_qr_path)
                                    <img src=" {{ image_url($mp_auth_shop->test_qr_path)!}" style="width: 150px">
                                </#if>
                                <button class="btn btn-success btn-get-test-qr">获取二维码</button>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                可选类目
                            </td>
                            <td>
                                {{ print_r($mp_auth_shop->category,true)!}
                                <button class="btn btn-success btn-get-category">获取可选类目</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                页面配置
                            </td>
                            <td>
                                {{ print_r($mp_auth_shop->page_cfg,true)!}
                                <button class="btn btn-success btn-get-page">获取页面配置</button>
                            </td>
                        </tr>

                        <tr>
                            <td>
                                提交审核ID
                            </td>
                            <td>
                                ${mp_auth_shop->audit_id!}
                                <button class="btn btn-success btn-submit">提交审核</button>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                提交审核状态
                            </td>
                            <td>
                                ${audit_state_map[$mp_auth_shop->audit_state]!}
                                <#if ($mp_auth_shop->audit_state == 1)
                                    <button class="btn btn-success btn-refresh-audit-state">刷新审核状态</button>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                提交审核时间
                            </td>
                            <td>
                                ${mp_auth_shop->submit_audit_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                审批成功时间
                            </td>
                            <td>
                                ${mp_auth_shop->audit_ok_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                审批失败原因
                            </td>
                            <td>
                                ${mp_auth_shop->audit_fail_reason!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                发布代码状态
                            </td>
                            <td>
                                ${mp_auth_shop->publish_state!}
                                <button class="btn btn-success btn-publish">发布代码</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                发布时间
                            </td>
                            <td>
                                ${mp_auth_shop->publish_time!}
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="template hide">
    <div class="setting-sub-merchant">
        <table class="table ">
            <tr>
                <td>子商户模式</td>
                <td>
                    <select id="is_sub_merchant">
                        <option value="0">非子商户</option>
                        <option value="1">微铺宝子商户</option>
                        <option value="2">通联子商户</option>
                    </select>
                </td>
            </tr>
            <tr class="union-pay-info">
                <td>通联支付配置</td>
                <td>
                    <span class="text-warning">
                        以下配置只有通联子商户时生效
                    </span>
                </td>
            </tr>
            <tr>
                <td>
                    商户APPID：
                </td>
                <td>
                    <input type="text" id="union_pay_app_id">
                </td>
            </tr>
            <tr>
                <td>
                    商户商户号：
                </td>
                <td>
                    <input type="text" id="union_pay_cus_id">
                </td>
            </tr>
            <tr>
                <td>
                    商户密钥：
                </td>
                <td>
                    <input type="text" id="union_pay_app_key">
                </td>
            </tr>
        </table>
    </div>
</div>
<script>

    $(".btn-remove-tester").click(function () {
        $("#act").val("remove-tester");
        $("#content").val($(this).attr("tester"));
        $("#form1").submit();
    });

    $(".btn-add-tester").click(function () {
        var tester;
        if (tester = prompt("请输入体验微信号")) {
            $("#act").val("add-tester");
            $("#content").val(tester);
            $("#form1").submit();
        }
    });


    $(".btn-set-sub-merchant").click(function () {
        var is_sub_merchant = $(this).attr("is_sub_merchant");
        $(".setting-sub-merchant #is_sub_merchant").val(is_sub_merchant);
        $(".setting-sub-merchant #union_pay_app_id").val($(".union_pay_app_id").text());
        $(".setting-sub-merchant #union_pay_cus_id").val($(".union_pay_cus_id").text());
        $(".setting-sub-merchant #union_pay_app_key").val($(".union_pay_app_key").text());
        art.dialog({
            title: '设置子商户信息',
            content: $(".setting-sub-merchant")[0],
            opacity: 0.1,
            margin: 0,
            padding: 0,
            okVal: '确定',
            ok: function () {
                $("#act").val("setting-sub-merchant");
                var sub_merchant = {
                    is_sub_merchant: $(".setting-sub-merchant #is_sub_merchant").val(),
                    union_pay_app_id: $(".setting-sub-merchant #union_pay_app_id").val(),
                    union_pay_cus_id: $(".setting-sub-merchant #union_pay_cus_id").val(),
                    union_pay_app_key: $(".setting-sub-merchant #union_pay_app_key").val(),
                }
                $("#content").val($.toJSON(sub_merchant));
                $("#form1").submit();
            },
            cancelVal: '取消',
            cancel: function () {
                return true;
            }
        });
    });

    $(".btn-modify-domain").click(function () {
        $("#act").val("modify-domain");
        $("#form1").submit();
    });

    $(".btn-upload").click(function () {
        $("#act").val("upload-code");
        $("#form1").submit();
    });

    $(".btn-submit").click(function () {
        $("#act").val("submit-audit");
        $("#form1").submit();
    });

    $(".btn-publish").click(function () {
        $("#act").val("publish-code");
        $("#form1").submit();
    });

    $(".btn-get-test-qr").click(function () {
        $("#act").val("get-test-qr");
        $("#form1").submit();
    });


    $(".btn-get-category").click(function () {
        $("#act").val("get-category");
        $("#form1").submit();
    });

    $(".btn-get-page").click(function () {
        $("#act").val("get-page");
        $("#form1").submit();
    });

    $(".btn-upload-audit").click(function () {
        $("#act").val("upload-audit");
        $("#form1").submit();
    });

    $(".btn-update-mp").click(function () {
        $("#act").val("update-mp");
        $("#form1").submit();
    });

    $(".btn-refresh-audit-state").click(function () {
        $("#act").val("refresh-audit-state");
        $("#form1").submit();
    });

</script>
<#include ("system.footer")
