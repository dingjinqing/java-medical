<#include ("system.header")

<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<form action="/system/shop/renew" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>店铺ID</th>
                    <th>店铺名称</th>
                    <th>手机号</th>
                    <th>所属账号</th>
                    <th>续费金额</th>
                    <th>续费时间</th>
                    <th>到期时间</th>
                    <th>操作员</th>
                    <th>说明</th>
                </tr>
                <#list ($message as $item)
                <tr style="text-align: center">
                    <td>${item->shop_id!}</td>
                    <td>${item->shop_name!}</td>
                    <td>${item->mobile!}</td>
                    <td>${item->sys_name!}</td>
                    <td>${item->renew_money!}</td>
                    <td>${item->renew_date!}</td>
                    <td>${item->expire_time!}</td>
                    <td>${item->operators!}</td>
                    <td>${item->renew_desc!}</td>
                </tr>
                </#list>
                </thead>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                    <a href="#"
                                       onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">
                                        {{ trans("system/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text"
                                           value="${data_list->currentPage()!}" size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
<script type="text/javascript">
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    $("#main-table").FixedHead({tableLayout: "fixed"});
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
</script>
<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>