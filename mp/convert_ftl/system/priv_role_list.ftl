<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.1" type="text/css" >
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />
<ul id="tab" class="nav nav-tabs">
    <li <#if ($nav_type==0)class="active"</#if>><a href="#"
                                                 data-toggle="tab">{{ trans("system/priv_user_list.account_manage")!}</a>
    </li>
    <li <#if ($nav_type==1)class="active"</#if>><a href="#"
                                                 data-toggle="tab">{{ trans("system/priv_user_list.role_manage")!}</a>
    </li>
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

<div class="box panel panel-body">
    <input type="button" class="primary" name="add_role" value="{{ trans("system/priv_role_list.add_role")!}">
</div>


<form action="/system/account/role/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="role_id" id="role_id" value="">
    {{ csrf_field()!}
    <div class="box panel main-table">
        <div class="layer">

            <table id="list_tbl" cellspacing='1' cellpadding='3' width="100%" class="table article-main-table">
                    <thead>
                <tr id="first_tr">
                    <th>{{ trans("system/priv_role_list.role_name")!}</th>
                    <th>{{ trans("system/common.operation.operation")!}</th>
                </tr>
                </thead>
                <#list ($data_list as $item)
                    <tr role_id="${item->role_id!}" role_name="${item->role_name!}"
                        privilege_list="${item->privilege_list!}">
                        <td>${item->role_name!}</td>
                        <td>
                            <a href="javascript:void(0);"
                               onclick="edit_role(${item->role_id!});">{{ trans("system/common.operation.edit")!}</a>&nbsp;&nbsp;
                            <a href="javascript:void(0);"
                               onclick="remove_role(${item->role_id!});">{{ trans("system/common.operation.del")!}</a>&nbsp;&nbsp;
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
                                <a href="#"
                                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                       size="5"
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

            function remove_role(id) {
                if (confirm("{{ trans("system/priv_role_list.confirm_del_role")!}")) {
                    $("#act").val('del');
                    $("#role_id").val(id);
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

    <div id="role_template">
        <input type="hidden" name="cur_role_id">
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td>{{ trans("system/priv_role_list.role_name")!}</td>
                <td><input type="text" name="role_name" maxlength="20" placeholder="{{ trans("system/priv_role_list.please_input_role_name")!}" value="" size="34" style="height:30px">
                </td>
            </tr>
            <tr>
                <td>{{ trans("system/priv_role_list.role_privilege")!}</td>
                <td>
                    <table class="table table-bordered">
                        <#list ($menu_cfg as $item)
                            <tr>
                                <td>
                                    <b>${item->name!}</b>&nbsp;
                                    <input type="checkbox" name="main_cbx[]"
                                           checked="true" priv_name="${item->en_name!}">
                                </td>
                                <td>

                                    <ul class="role_ul">

                                        <#list ((array)$item->sub as $sub_item)
                                            <#if  (!$sub_item->all)
                                                <div style='clear:both;'></div>
                                                <li <#if ($sub_item->hide)style="display:none"</#if>>
                                                    <input type="checkbox" main="${item->en_name!}" class='two_click'
                                                           name_a="two" name="sub_cbx[]" checked="true"
                                                           value="${sub_item->en_name!}"
                                                           priv_name="${sub_item->en_name!}"/>
                                                    <span style='color:blue;font-weight: bold'>${sub_item->name!}</span>
                                                    &nbsp;&nbsp;
                                                </li>
                                                <div style='clear:both;'></div>
                                            </#if>

                                            <div id="role_template1" class='three_input' style='display:block; margin-left: 20px'>
                                                <#list ((array)$sub_item->sub as $three_item)
                                                    <#if ($three_item->name)
                                                        <li style="list-style-type:none;<#if ($three_item->hide)display:none </#if>">
                                                            <input type='checkbox' main="${item->en_name!}"
                                                                   name_a="third" name="sub_cbx[]" checked="true"
                                                                   value="${three_item->en_name!}"
                                                                   main_next="${sub_item->en_name!}"/>
                                                            ${three_item->name!}
                                                        </li>
                                                    </#if>
                                                </#list>
                                            </div>

                                        </#list>
                                    </ul>
                                </td>
                            </tr>
                        </#list>
                    </table>
                </td>
            </tr>
        </table>
    </div>
</div>
<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/priv_role_list.js"></script>
<script language="JavaScript" src="/js/system/priv_role_list.js?v=1.01"></script>

<#include ("system.footer")