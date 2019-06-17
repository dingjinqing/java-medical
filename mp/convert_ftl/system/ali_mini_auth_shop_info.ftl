<#include ("system.header")

<#include ("system.ali_mini_tab")


<form enctype="multipart/form-data" action="/system/ali_mini/info?app_id=${app_id!}" name="form1" id="form1"
      method="post">
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
                    <button class="btn btn-success btn-update-mini">更新授权信息</button>
                </div>
                <div class="panel-body">
                    <table class=" table table-bordered">
                        <tr>
                            <td>
                                店铺ID
                            </td>
                            <td>
                                ${ali_mini_auth_shop->shop_id!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                app_id
                            </td>
                            <td>
                                ${ali_mini_auth_shop->auth_app_id!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                名称
                            </td>
                            <td>
                                ${ali_mini_auth_shop->app_name!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                英文名称
                            </td>
                            <td>
                                ${ali_mini_auth_shop->app_english_name!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                类别
                            </td>
                            <td>
                                ${ali_mini_auth_shop->app_category_ids!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序的简介
                            </td>
                            <td>
                                ${ali_mini_auth_shop->app_slogan!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序的简介
                            </td>
                            <td>
                                <img src=" ${ali_mini_auth_shop->app_slogan!}" style="width: 50px">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                电话
                            </td>
                            <td>
                                ${ali_mini_auth_shop->service_phone!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                邮箱
                            </td>
                            <td>
                                ${ali_mini_auth_shop->service_email!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                头像
                            </td>
                            <td>
                                <#if ($ali_mini_auth_shop->app_logo)
                                    <img src="{{ image_url($ali_mini_auth_shop->app_logo)!}" style="width: 100px">
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序功能
                            </td>
                            <td>
                                ${ali_mini_auth_shop->app_desc!}
                            </td>
                        </tr>

                        <tr>
                            <td>
                                是否授权
                            </td>
                            <td>
                                <#if ( $ali_mini_auth_shop->is_auth_ok == 1)
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
                                ${ali_mini_auth_shop->last_auth_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                最后取消授权时间
                            </td>
                            <td>
                                ${ali_mini_auth_shop->last_cancel_auth_time!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                记录时间
                            </td>
                            <td>
                                ${ali_mini_auth_shop->create_time!}
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
                    <a href="/system/ali_mini/operate/log/list?app_id=${ali_mini_auth_shop->auth_app_id!}"
                       target="_blank">查看版本操作日志</a>
                </div>
                <div class="panel-body">
                    <table class="table table-bordered">


                        <tr>
                            <td width="180px">
                                小程序版本号
                            </td>
                            <td>
                                ${ali_mini_auth_shop->app_version!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序状态
                            </td>
                            <td>
                                ${status_map[$ali_mini_auth_shop->status]!}
                                <button class="btn btn-success btn-upload-code">上传代码（最新版本：${last_version!}）</button>
                                <button class="btn btn-success btn-submit-audit">提交审核</button>
                                <button class="btn btn-success btn-gray-online">灰度上架</button>
                                <button class="btn btn-success btn-gray-cancel">灰度下架</button>
                                <button class="btn btn-success btn-online">正式上架</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序版本提交审核的时间
                            </td>
                            <td>
                                ${ali_mini_auth_shop->detail_info->gmt_apply_audit!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序版本上架时间
                            </td>
                            <td>
                                ${ali_mini_auth_shop->detail_info->gmt_online!}
                            </td>
                        </tr>

                        <tr>
                            <td>
                                审批失败原因
                            </td>
                            <td>
                                ${ali_mini_auth_shop->detail_info->reject_reason!}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                体验版打包状态
                            </td>
                            <td>
                                ${experience_status_map[$ali_mini_auth_shop->experience_status]!}
                                <#if ($ali_mini_auth_shop->experience_status == "notExpVersion")
                                    <button class="btn btn-success btn-set-experience">设置成体验版</button>
                                <#elseif>($ali_mini_auth_shop->experience_status == "expVersionPackged")
                                    <button class="btn btn-success btn-cancel-experience">取消成体验版</button>
                                </#if>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                小程序体验者
                            </td>
                            <td>
                                <#list ($ali_mini_auth_shop->tester as $tester)
                                    <span class="tester">${tester->logon_id!}
                                        <a href="javascript:void(0)" tester="${tester->user_id!}"
                                           class="btn btn-link btn-remove-tester"><b>x</b></a>
                                    </span>
                                    &nbsp;&nbsp;
                                </#list>
                                <button class="btn btn-success btn-add-tester">添加体验者</button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                体验二维码
                            </td>
                            <td>
                                <#if ($ali_mini_auth_shop->exp_qr_code_url)
                                    <img src=" {{ image_url($ali_mini_auth_shop->exp_qr_code_url)!}"
                                         style="width: 150px">
                                </#if>
                                <button class="btn btn-success btn-get-test-qr">获取二维码</button>
                            </td>
                        </tr>

                    </table>
                </div>
            </div>
        </div>

    </div>
</div>
<script>

    $(".btn-set-experience").click(function () {
        $("#act").val("set-experience");
        $("#form1").submit();
    });

    $(".btn-cancel-experience").click(function () {
        $("#act").val("cancel-experience");
        $("#form1").submit();
    });

    $(".btn-remove-tester").click(function () {
        $("#act").val("remove-tester");
        $("#content").val($(this).attr("tester"));
        $("#form1").submit();
    });

    $(".btn-add-tester").click(function () {
        var tester;
        if (tester = prompt("请输入体验支付宝登录号")) {
            $("#act").val("add-tester");
            $("#content").val(tester);
            $("#form1").submit();
        }
    });


    $(".btn-upload-code").click(function () {
        $("#act").val("upload-code");
        $("#form1").submit();
    });

    $(".btn-submit-audit").click(function () {
        $("#act").val("submit-audit");
        $("#form1").submit();
    });

    $(".btn-gray-online").click(function () {
        $("#act").val("gray-online");
        $("#form1").submit();
    });
    $(".btn-gray-cancel").click(function () {
        $("#act").val("gray-cancel");
        $("#form1").submit();
    });

    $(".btn-online").click(function () {
        $("#act").val("online");
        $("#form1").submit();
    });

    $(".btn-get-test-qr").click(function () {
        $("#act").val("get-test-qr");
        $("#form1").submit();
    });

    $(".btn-refresh-detail").click(function () {
        $("#act").val("refresh-detail");
        $("#form1").submit();
    });

</script>
<#include ("system.footer")
