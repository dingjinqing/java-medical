<#include ("system.header")

<form action="/system/mp/official/account/list" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <div class="box">
        <div class="form-query form-inline">

            <select name="is_auth_ok" class="form-control">
                <option value="-1">选择是否授权</option>
                <option value="0" <#if (($request['is_auth_ok'] ?? -1) == 0) selected </#if>>未授权</option>
                <option value="1" <#if (($request['is_auth_ok'] ?? -1) == 1) selected </#if>>已授权</option>
            </select>

            <select name="open_pay" class="form-control">
                <option value="-1">选择是否微信支付</option>
                <option value="0" <#if (($request['open_pay'] ?? -1) == 0) selected </#if>>不支持微信支付</option>
                <option value="1" <#if (($request['open_pay'] ?? -1) == 1) selected </#if>>支持微信支付</option>
            </select>

            <input type="text" class="form-control" name="app_id" placeholder="请输入app_id"
                   value="${request['app_id']!}">

            <input type="text" class="form-control" name="user_name" placeholder="请输入商家用户名"
                   value="${request['user_name']!}">

            <button class="btn btn-primary" name="search">搜索</button>
        </div>
    </div>
    <div class="box panel main-table">
        <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
            <thead>
            <tr>
                <th>商家用户名</th>
                <th>公众号AppId</th>
                <th>昵称</th>
                <th>头像</th>
                <th>是否授权</th>
                <th>是否认证</th>
                <th>是否支持微信支付</th>
                <th>最后授权时间</th>
                <th>绑定的小程序</th>
            </tr>
            </thead>

            <#list ($data_list as $item)
                <tr style="text-align:center;">
                    <td>${item->user_name!}</td>
                    <td>${item->app_id!}</td>
                    <td>${item->nick_name!}</td>
                    <td><img src="${item->head_img!}" width="50"></td>
                    <td>
                        <#if ( $item->is_auth_ok == 1)
                            <span class="label label-success">已授权</span>
                        <#else>
                            <span class="label label-danger">已取消授权</span>
                        </#if>
                    </td>
                    <td>
                        <#if ($item->verify_type_info == "-1")
                            <span class="label label-danger">未认证</span>
                        <#elseif>($item->verify_type_info == "0")
                            <span class="label label-success">已认证</span>
                        </#if>
                    </td>
                    <td>
                        <#if ( $item->open_pay == 1)
                            <span class="label label-success">支持</span>
                        <#else>
                            <span class="label label-danger">不支持</span>
                        </#if>
                    </td>
                    <td>${item->last_auth_time!}</td>

                    <td>
                        <#list ((array)($item->bind_mps) as $mp)
                            <a href="/system/mp/info?app_id=${mp->app_id!}" target="_blank">${mp->nick_name!}</a>
                        </#list>
                    </td>
                </tr>

            </#list>
        </table>
        <table width="" border="0" class="table">
            <tr>
                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                    <a href="##" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                    <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                           size="5"
                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                </td>
            </tr>
        </table>
    </div>
</form>

<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    $('.form-query select.form-control').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    $('[name="search"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });

</script>

<#include ("system.footer")
