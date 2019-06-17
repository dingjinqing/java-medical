<#include ("system.header")

<#include ("system.mp_tab")

<link rel="stylesheet" href="/css/system/mp_table_list.css" type="text/css"/>
<form action="/system/mp/operate/log/list" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <div class="box">
        <div class="form-inline">
            <select name="template_id" class="form-control">
                <option value="-1">选择小程序版本</option>
                <#list ($version_map as $item)
                    <option value="${item->template_id!}"
                            <#if ($item->template_id == ($request['template_id'] ?? -1)) selected="selected" </#if>>${item->user_version!}</option>
                </#list>
            </select>

            <input type="text" class="form-control" name="app_id" placeholder="请输入app_id"
                   value="${request['app_id']!}">

            <button class="btn btn-primary" name="search">搜索</button>
        </div>
    </div>
    <div class="box panel main-table">
        <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
            <thead>
            <tr>
                <th width="200px">时间</th>
                <th width="200px">app_id</th>
                <th width="200px">昵称</th>
                <th>日志内容</th>
                <th width="100px">小程序版本</th>
                <th width="100px">操作</th>
            </tr>
            </thead>

            <#list ($data_list as $item)
                <tr style="text-align:center;">
                    <td>${item->create_time!}</td>

                    <td><a href="/system/mp/operate/log/list?app_id=${item->app_id!}">${item->app_id!}</a></td>
                    <td><a href="/system/mp/operate/log/list?app_id=${item->app_id!}">${item->nick_name!}</a>
                    </td>
                    <td style="text-align: left">${item->memo!}</td>
                    <td>${version_map[$item->template_id]->user_version!}</td>
                    <td><a href="/system/mp/info?app_id=${item->app_id!}" target="_blank">查看详情</a></td>
                </tr>

            </#list>
        </table>
        <table width="" border="0" class="tb_paging" style="width: 100%">
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

    $('[name="template_id"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });

    $('[name="search"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
</script>

<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>