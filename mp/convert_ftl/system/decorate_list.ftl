<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.1" type="text/css" >
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />
<style type="text/css">
    .table>thead>tr>th{
        width: 7%;
    }
</style>
<ul id="tab" class="nav nav-tabs">

    <li class="active"><a href="#" data-toggle="tab"
                                                 onclick="location.href = '/system/decoration/template';">装修模板列表</a></li>
    <li><a data-toggle="tab" url="#" onclick="location.href = '/system/decoration/first/page';">添加模板</a></li>
    <#if ($nav_type==3)
        <li <#if ($nav_type==3)class="active"</#if>><a href="#" data-toggle="tab" url="">编辑模板</a></li>
    </#if>
</ul>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url_arr = ['/system/decoration/template', '/system/decoration/first/page'];
        var idx = $(this).parent().index();
        if (url_arr[idx] != undefined) {
            window.location.href = url_arr[idx];
        }
    });
</script>
<form action="/system/decoration/template" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <input type="hidden" name="off" value="" id="off">
    <input type="hidden" name="on" value="" id="on">
    <div class="box panel ">
        <div class="panel-body">
            模板名称：<input type="text" name="page_name" value="<#if (!empty($request["page_name"])) ${request["page_name"]!} </#if> " size="6" placeholder="输入模板名称进行查询" style="width: 15%; height: 30px">
            <span class="text-info" style="margin-right: 20px"></span>
            <input type="submit" name="search" value="{{ trans("system/common.operation.search")!}" style="height: 28px">
            {{--<input type="submit" name="add" value="新建模板">--!}
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table article-main-table" id="main-table">
                <thead>
                <tr>
                    <th>模板名称</th>
                    <th>创建时间</th>
                    <th>模板状态</th>
                    <th>操作</th>
                </tr>
                </thead>

                <#list ($page as $item)
                    <tr style="text-align:center;">
                        <td>${item->page_name!}</td>
                        <td>${item->create_time!}</td>
                        <td><#if ($item->page_enabled == 1)可用 <#else> 已停用</#if></td>
                        <td>
                            <a href="/system/decoration/first/page?id=${item->page_id!}">编辑</a>
                            <#if ($item->page_enabled == 1)
                            <a href="#" class="page_enabled_off" page_id="${item->page_id!}">停用</a>
                            <#else>
                            <a href="#" class="page_enabled_on" page_id="${item->page_id!}">启用</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table article-bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$page->perPage(),'currentPage'=>$page->currentPage(),'count'=>$page->count,'total'=>$page->total(),])!}
                                    <a href="#"
                                       onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${page->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${page->currentPage() + ($page->lastPage() > $page->currentPage() ? 1: 0)!});">
                                        {{ trans("system/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${page->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text"
                                           value="${page->currentPage()!}" size="5"
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
        var last_page = '${page->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    $("#main-table").FixedHead({tableLayout: "fixed"});

    $(".page_enabled_off").click(function(){
        var _this = $(this);
        $("#off").val(_this.attr("page_id"));
        $("#form1").submit();
    })
    $(".page_enabled_on").click(function(){
        var _this = $(this);
        $("#on").val(_this.attr("page_id"));
        $("#form1").submit();
    })

</script>


<#include ("system.footer")
