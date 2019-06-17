<#include "/admin/toggle_header.ftl">
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/layui_change.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/account_user.css?v=1.0.1">
<style>
    .no_data_style{
        width: 94% !important;
        border: 1px solid #eee;
        margin-left: 50px !important;
        margin-top: 10px !important;
    }
</style>
<div style="padding: 10px;">
<ul class="add-child-ul">
    <li>
        <input type="button" value="{{ trans('admin/account_user_list.add_child_account')!}"
               class="add-child-btn layui-btn layui-btn-normal" data-method="offset" data-id="template-add"
               data-title="{{ trans('admin/account_user_list.add_child_account')!}">
    </li>
</ul>

<form action="/admin/account/user/list" name="form1" id="form1" method="post" class="add-account-form">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="account_id" id="account_id" value="">
    {{ csrf_field()!}
    <div class="box panel panel-body" id="set-template-add">
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td>{{ trans("admin/account_user_list.account_name")!}</td>
                <td><input type="text" name="account_name" maxlength="20" value="" size="34">
                </td>
            </tr>
            <tr>
                <td>{{ trans("admin/account_user_list.account_mobile")!}</td>
                <td><input type="text" name="mobile" maxlength="20" value="" size="34">
                </td>
            </tr>

            <tr>
                <td>{{ trans("admin/account_user_list.account_password")!}</td>
                <td><input type="password" name="account_pwd" maxlength="20" value="" size="34">
                </td>
            </tr>
            <tr>
                <td>{{ trans("admin/account_user_list.account_confirm_password")!}</td>
                <td><input type="password" name="confirm_account_pwd" maxlength="20" value=""
                           size="34">
                </td>
            </tr>
        </table>
    </div>

    <#if ($msg)
        <div class="box alert alert-warning msg-info">
            ${msg!}
        </div>
    </#if>

    <div class="box panel panel-default main-table" id="main-table">
        <div class="layer">
            <table id="list_tbl" cellspacing='1' cellpadding='3' width="90%" class="table">
                <thead>
                <tr id="first_tr">
                    <th>{{ trans("admin/account_user_list.account_name")!}</th>
                    <th>{{ trans("admin/account_user_list.account_mobile")!}</th>
                    <th>{{ trans("admin/account_user_list.create_time")!}</th>
                    <th>{{ trans("admin/account_user_list.shop")!}</th>
                    <th>{{ trans("admin/account_user_list.role")!}</th>
                    <th>{{ trans("admin/common.operation.operation")!}</th>
                </tr>
                </thead>
                <tbody class="account_tb">
                <#list ($data_list as $item)
                    <tr account_id=${item->account_id!} role_id="${item->role_id!}"
                        account_name="${item->account_name!}" mobile="${item->mobile!}" >
                        <td style="text-align:center">${item->account_name!}</td>
                        <td style="text-align:center">${item->mobile!}</td>
                        <td style="text-align:center">${item->create_time!}</td>
                        <td style="text-align:center">
                            <#if ($item->shop_role)
                                <table cellspacing='0' cellpadding='0' style="width: 100%;height: 100%;margin: 0 auto;">
                                    <#list ($item->shop_role as $shop_role)
                                        <#if ($shop_role->shop_name != '')
                                            <tr>
                                                <td style="text-align:center;height:40px;">${shop_role->shop_name!}</td>
                                            </tr>
                                        </#if>
                                    </#list>
                                </table>
                            <#else>
                            </#if>
                        </td>
                        <td style="text-align:center">
                            <#if ($item->shop_role)
                                <table cellspacing='0' cellpadding='0' style="width: 100%;height: 100%;margin: 0 auto;">
                                    <#list ($item->shop_role as $shop_role)
                                        <#if ($shop_role->shop_name != '')
                                            <tr>
                                                <td style="text-align:center;height:40px;">
                                                    <a href="/admin/config/role/list?shop_id=${shop_role->shop_id!}&id=${item->account_id!}">
                                                        ${shop_role->role_name!}
                                                    </a>
                                                </td>
                                            </tr>
                                        </#if>
                                    </#list>
                                </table>
                            <#else>
                            </#if>
                        </td>
                        <td style="text-align:center">
                            <a href="javascript:void(0);"
                               onclick="edit_account(${item->account_id!});">{{ trans("admin/common.operation.edit")!}</a>&nbsp;&nbsp;
                            <a href="javascript:void(0);"
                               onclick="remove_account(${item->account_id!});">{{ trans("admin/common.operation.del")!}</a>&nbsp;&nbsp;
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
      <#include "/admin/jump_page_admin.ftl">
    </div>
</form>
</div>
<div id="template" style="display: none">
    <table id="edit_account" cellspacing='1' cellpadding='3'>
        <tr>
            <td>{{ trans("admin/account_user_list.account_name")!}</td>
            <td><span class="account_name"></span>
                <input type="hidden" name="edit_account_id" maxlength="20" readonly value="" size="34">
                <input type="hidden" name="edit_account_name" maxlength="20" readonly value="" size="34">
            </td>
        </tr>
        <tr>
            <td>{{ trans("admin/account_user_list.account_mobile")!}</td>
            <td>
                <input type="text" name="edit_account_mobile" maxlength="20" value=""
                       size="34">
            </td>
        </tr>
        <tr>
            <td>{{ trans("admin/account_user_list.account_password")!}</td>
            <td><input type="password" name="edit_account_pwd" maxlength="20"
                       placeholder="{{ trans("admin/account_user_list.null_no_del_old_password")!}" value=""
                       size="34">
            </td>
        </tr>
    </table>
</div>

<script>
    function remove_account(id) {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("#act").val('del');
                $("#account_id").val(id);
                $("#form1").submit();
                layer.close(index);
            });
        });
    }

    $(document).ready(function () {
        $(".formtable td").mouseover(function () {
            $(".formtable tr").removeClass("hover_tr");
            $(this).parent().addClass("hover_tr");
        });
    });

</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include "/admin/footer.ftl">


<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/account_user_list.js?v=1.1"></script>
<script language="JavaScript" src="/js/admin/account_user_list.js?v=1.0.3"></script>