<#include ("system.header")

<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/cfg/list">配置信息列表</a>
    </li>
    <li <#if ($nav_type==2)class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/cfg/add">配置信息添加</a>
    </li>
    <#if ($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="#">配置信息编辑</a></li>
    </#if>
</ul>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>


<form action="/system/shop/cfg/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="page" value="">
    <input type="hidden" name="" id="" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            <input type="text" name="keywords" placeholder="请输入关键词" value="${keywords!}">
            <input type="submit" name="search" value="{{ trans("system/common.operation.search")!}">
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    
                    <th width="210px">操作</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        
                        <td>
                            <a href="/system/shop/cfg/edit?=${item->!}">编辑</a>&nbsp;&nbsp;
                            <a href="javascript:void(0);" onclick="remove('${item->!}')">删除</a>&nbsp;&nbsp;
                        </td>
                    </tr>
                </#list>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0">
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


<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    function remove(id) {
        if (confirm("确定删除此行数据吗？")) {
            $("#act").val('del');
            $("#").val(id);
            $("#form1").submit();
        }
    }

    $("#main-table").FixedHead({tableLayout: "fixed"});
</script>

<#include ("system.footer")
