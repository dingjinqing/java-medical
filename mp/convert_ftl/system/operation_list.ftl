<#include ("system.header")
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<style type="text/css">
    .renew label{
        margin-left: 4%;
    }
    .renew input[type='text']{
        width: 55%;
    }
    .renew input[type='date']{
        width: 55%;
    }

    .form-control{
        width: 160px;
        display: inline-block;
        margin-left: 16px;
    }
    .panel-body  input{
        width: 160px;
        display: inline-block;
        margin-left: 20px;
        height: 32px;
        padding: 6px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        color: #555;
        background-color: #fff;
        background-image: none;
        border: 1px solid #ccc;
    }
    .btn_search{
        cursor: pointer;
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        border-radius: 2px;
        width: 60px;
        margin-left: 10px;
    }
</style>
<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" onclick="location.href = '/system/operation/list';">操作日志</a></li>
    <li ><a href="#" data-toggle="tab"onclick="location.href = '/system/log/list';">错误日志</a></li>
    {{--<#if ($nav_type==2)--!}
    {{--<li class="active"><a href="#" data-toggle="tab" url="#">店铺添加</a></li>--!}
    {{--</#if>--!}
    {{--<#if ($nav_type==3)--!}
    {{--<li class="active"><a href="#" data-toggle="tab" url="#">店铺编辑</a></li>--!}
    {{--</#if>--!}
</ul>

<form action="/system/operation/list" name="form1" id="form1" method="post">
    <input type="hidden" name="page" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            店铺名称：<input type="text" name="shop_name" value="${shop_name!}">
            <button  class="btn_search" type="button">{{ trans("system/common.operation.search")!}</button>
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th width="5%">ID</th>
                    <th width="5%">店铺ID</th>
                    <th width="7%">店铺名称</th>
                    <th width="7%">操作员</th>
                    <th width="7%">操作时间</th>
                    <th width="50%">操作内容</th>
                    <th width="7%">ip</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        <td width="5%">${item->id!}</td>
                        <td width="5%" class="shop_ids">${item->shop_id!}<br>${item->type == 1?" (账号)":""!}</td>
                        <td width="7%" class="shop_names"><#if ($item->type == 0)${item->shop_name!} <#elseif>($item->type==1) ${item->user_name!}
                            <br> (账号)</#if></td>
                        <td width="7%">${item->operator!}</td>
                        <td width="7%">${item->created!}</td>
                        <td width="50%">${item->desc!}</td>
                        <td width="7%">${item->ip!}</td>
                    </tr>

                </#list>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                    <a href="##" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                    <a href="##" onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                    <a href="##" onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">{{ trans("system/common.page.next_page")!}</a>
                                    <a href="##" onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a><input id="page" name="page" type="text" value="${data_list->currentPage()!}" size="5" onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
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
    if(page>last_page){
        page = last_page;
    }
        $("#page").val(page);
        $("#form1").submit();
    }
    $("#main-table").FixedHead({tableLayout: "fixed"});

    $("#shop_id").val($(".btn_renew").attr('shop_id'));
    $("#re_sys_id").val($(".btn_renew").attr('sys_id'));

    $(".btn_search").click(function () {
        $('#form1').submit();
    })
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
</script>
<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>