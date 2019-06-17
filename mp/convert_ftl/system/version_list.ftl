<#include ("system.header")
<link rel="stylesheet" href="/css/system/layui/css/layui.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/layui_change.css?v=1.0.0" type="text/css"/>
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/util.js"></script>
<script language="JavaScript" src="/js/system/util.js?v=1.0.9"></script>
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
    .table>thead>tr>th{
        width: 7%;
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
    .btn_add{
        float: right;
        margin-right: 60px;
        cursor: pointer;
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
    }
</style>
<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab"
                                                 onclick="location.href = '/system/version/list';">版本列表</a></li>
</ul>

<form action="/system/version/list" name="form1" id="form1" method="post">
    <input type="hidden" name="page" value="">
    <input type="hidden" name="del">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            版本名称：<input type="text" name="version_name" value="${version_name!}">
            <button  class="btn_search" type="button">{{ trans("system/common.operation.search")!}</button>
            {{--上线时隐藏--!}
            {{--<a href="/system/version/show"><button  class="btn_add" type="button" hidden>新建版本</button></a>--!}
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>版本ID</th>
                    <th>版本名称</th>
                    <th>版本等级</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th hidden>版本介绍</th>
                    <th>操作</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        <td>${item->id!}</td>
                        <td class="shop_ids">${item->version_name!}</td>
                        <td class="shop_ids">${item->level!}</td>
                        <td class="shop_names">${item->created!}</td>
                        <td>${item->update_time!}</td>
                        <td hidden>${item->desc!}</td>
                        <td><a style="color: blue;" href="/system/version/show?act=read&id=${item->id!}">查看</a>
                            {{--<a style="color: blue;" href="/system/version/show?act=edit&id=${item->id!}" hidden>编辑</a>--!}
                            {{--<a style="color: blue;" href="#" class="del" version_id="${item->id!}" hidden>删除</a></td>--!}
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
                                    <a href="#" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                    <a href="#" onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                    <a href="#" onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">{{ trans("system/common.page.next_page")!}</a>
                                    <a href="#" onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a><input id="page" name="page" type="text" value="${data_list->currentPage()!}" size="5" onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
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
    $('.del').click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除么' + '</div>', {
                title: '提示'
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="del"]').val(_this.attr('version_id'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
</script>
<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>