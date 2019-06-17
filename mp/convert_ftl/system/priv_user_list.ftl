<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.1" type="text/css" >
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />
<style type="text/css">
    .user_info_lists tr{
        height:50px;
    }
    .user_info_lists tr td:first-of-type{
        padding-right: 10px;
    }
    .user_info_lists tr input{
        height:30px;
    }
    .user_info_lists tr select{
        height:30px;
    }
</style>
<ul id="tab" class="nav nav-tabs">
    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab">{{ trans("system/priv_user_list.account_manage")!}</a></li>
    <li <#if ($nav_type==1)class="active"</#if>><a href="#" data-toggle="tab">{{ trans("system/priv_user_list.role_manage")!}</a></li>
</ul>

<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url_arr = ['/system/account/user/list', '/system/account/role/list'];
        var idx = $(this).parent().index();
        if (url_arr[idx] != undefined) {
            window.location.href = url_arr[idx];
        }
    });
</script>

<form action="/system/account/user/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="account_id" id="account_id" value="">
    {{ csrf_field()!}
    <div class="box panel panel-body">
        <table cellspacing='1' cellpadding='3' class="user_info_lists">
            <tr>
                <td>{{ trans("system/priv_user_list.account_name")!}</td>
                <td><input type="text" name="account_name" maxlength="20" value="" size="34">
                </td>
            </tr>
            <tr>
                <td>{{ trans("system/priv_user_list.account_mobile")!}</td>
                <td><input type="text" name="mobile" maxlength="20" value="" size="34">
                </td>
            </tr>

            <tr>
                <td>{{ trans("system/priv_user_list.account_password")!}</td>
                <td><input type="password" name="account_pwd" maxlength="20" value="" size="34">
                </td>
            </tr>
            <tr>
                <td>{{ trans("system/priv_user_list.account_confirm_password")!}</td>
                <td><input type="password" name="confirm_account_pwd" maxlength="20" value=""
                           size="34">
                </td>
            </tr>
            <tr>
                <td>{{ trans("system/priv_user_list.act_role")!}</td>
                <td>
                    <select name="role_id">
                        <#list ($role_list as $item)
                            <option value="${item->role_id!}">${item->role_name!}</option>
                        </#list>
                    </select>
                    <a href="/system/account/role/list">{{ trans("system/priv_user_list.add_role")!}</a>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="button" class="primary" name="add_account" value="{{ trans("system/priv_user_list.add_account")!}">
                </td>
            </tr>
        </table>
    </div>


    <#if ($msg)
        <div class="box alert alert-warning msg-info">
            ${msg!}
        </div>
    </#if>

    <div class="box panel panel-default main-table">
        <div class="layer">
            <table id="list_tbl" cellspacing='1' cellpadding='3' width="100%" class="table article-main-table">
                <thead>
                <tr id="first_tr">
                    <th>{{ trans("system/priv_user_list.account_name")!}</th>
                    <th>{{ trans("system/priv_user_list.account_mobile")!}</th>
                    <th>{{ trans("system/priv_user_list.role_name")!}</th>
                    <th>{{ trans("system/common.operation.operation")!}</th>
                </tr>
                </thead>
                <#list ($data_list as $item)
                    <tr account_id=${item->account_id!} role_id="${item->role_id!}"
                        account_name="${item->account_name!}" mobile="${item->mobile!}">
                        <td style="text-align:center">${item->account_name!}</td>
                        <td style="text-align:center">${item->mobile!}</td>
                        <td style="text-align:center">${item->role_name!}</td>
                        <td style="text-align:center">
                            <a href="javascript:void(0);"
                               onclick="edit_account(${item->account_id!});">{{ trans("system/common.operation.edit")!}</a>&nbsp;&nbsp;
                            <a href="javascript:void(0);"
                               onclick="remove_account(${item->account_id!});">{{ trans("system/common.operation.del")!}</a>&nbsp;&nbsp;
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
        <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table article-bottom-table">
            <tr>
                <td>&nbsp;</td>
                <td align="right">
                    <table width="100%" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="#" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                <a href="#" onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                <a href="#" onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>
                                <a href="#" onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage()!}" size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <script>

            function gopage(page) {
                var last_page = '${data_list->lastPage()!}';
                if (page > last_page) {
                    page = last_page;
                }
                $("#page").val(page);
                $("#form1").submit();
            }

            function remove_account(id) {
                if (confirm("{{ trans("system/priv_user_list.account_confirm_password")!}")) {
                    $("#act").val('del');
                    $("#account_id").val(id);
                    $("#form1").submit();
                }
            }

            $(document).ready(function () {
                $(".formtable td").mouseover(function () {
                    $(".formtable tr").removeClass("hover_tr");
                    $(this).parent().addClass("hover_tr");
                });
            });
            $("#list_tbl").FixedHead({tableLayout: "fixed"});
        </script>
    </div>
</form>

<div id="template" style="display: none">
    <table id="edit_account" cellspacing='1' cellpadding='3'>
        <tr>
            <td>{{ trans("system/priv_user_list.account_name")!}</td>
            <td><span class="account_name"></span>
                <input type="hidden" name="edit_account_id" maxlength="20" readonly value="" size="34">
                <input type="hidden" name="edit_account_name" maxlength="20" readonly value="" size="34">
            </td>
        </tr>
        <tr>
            <td>{{ trans("system/priv_user_list.account_mobile")!}</td>
            <td>
                <input type="text" name="edit_account_mobile" maxlength="20" value=""
                       size="34">
            </td>
        </tr>
        <tr>
            <td>{{ trans("system/priv_user_list.account_password")!}</td>
            <td><input type="password" name="edit_account_pwd" maxlength="20" placeholder="{{ trans("system/priv_user_list.null_no_del_old_password")!}" value=""
                       size="34">
            </td>
        </tr>
        <tr>
            <td>{{ trans("system/priv_user_list.act_role")!}</td>
            <td>
                <select name="edit_role_id">
                    <#list ($role_list as $item)
                        <option value="${item->role_id!}">${item->role_name!}</option>
                    </#list>
                </select>
            </td>
        </tr>
    </table>
</div>

<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/priv_user_list.js"></script>
<script language="JavaScript" src="/js/system/priv_user_list.js?v=1.0.0"></script>


<#include ("system.footer")