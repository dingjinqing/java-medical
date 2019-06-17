<#include ("system.header")
<link href="/css/system/table_list.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .table>thead>tr>th{
        width: 7%;
    }
    .shop_number:after{
        content: '';
        display: block;
        clear: both;
    }
    .shop_number a{
        display: inline-block;
    }
    .shop_number a:first-of-type{
        width: 60%;
        text-align: right;
        float: left;
    }
    .shop_number a:last-of-type{
        width: 30%;
        text-align: left;
        float: right;
        height:18px;
    }
    .shop_number a:last-of-type img{
        height:12px;
        margin-top: 2px;
    }
    .user-name {
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
    }
    .show_more{
        cursor: pointer;
    }
    .take_up{
        cursor: pointer;
    }

    .form-control{
        width: 160px;
        display: inline-block;
        margin-left: 20px;
    }
    .search{
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
        border: 1px solid #ccc;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 22px;
        border-radius: 2px;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        display: inline-block;
        width: 60px;
    }
</style>

<ul id="tab" class="nav nav-tabs">
    <li ><a href="#" data-toggle="tab" onclick="location.href = '/system/statistics/incomeStatistics';">收入统计</a></li>
    <li class="active"><a href="#" data-toggle="tab" >续费明细</a></li>
</ul>


<form action="/system/statistics/incomeDetail" name="form1" id="form1" method="post">
    <!-- <input type="hidden" name="page" value="">
    <input type="hidden" name="start_time"  value="${request['start_time']!}">
    <input type="hidden" name="end_time"  value="${request['end_time']!}">
    <input type="hidden" name="is_use"  value="${request['is_use']!}">
    <input type="hidden" name="shop_type"  value="${request['shop_type']!}"> -->
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            <select name="is_use" id="" class="form-control">
                <option value="-1" selected>选择店铺状态</option>
                <option value="1" <#if ($request['is_use'] == 1) selected </#if>>使用中</option>
                <option value="2" <#if ($request['is_use'] == 2) selected </#if>>已过期</option>
            </select>
            <select name="is_enabled" id="" class="form-control" style="margin-left: 0px">
                <option value="" selected>选择禁用状态</option>
                <option value="1" <#if ($request['is_enabled'] == 1) selected </#if>>已禁用</option>
                <option value="0" <#if ($request['is_enabled'] == 0 && !is_null($request['is_enabled'])) selected </#if>>未禁用</option>
            </select>
            <select name="shop_type" id="" class="form-control">
                <option value="-1" selected>选择店铺类型</option>
                <#list ($version_list as $item)
                    <option value="${item->level!}" <#if ($request['shop_type'] == $item->level) selected </#if>>${item->version_name!}</option>
                </#list>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            续费时间：<input  style="width: 150px; height: 30px;" type="text" name="start_time" value="${request['start_time']!}" placeholder='请输入开始时间' id="startDate" onclick="picker();"
               onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
            &nbsp;&nbsp; 至 &nbsp;&nbsp;
            <input  style="width: 150px; height: 30px;" type="text" name="end_time" value="${request['end_time']!}" placeholder='请输入结束时间' id="endDate" onclick="picker();"
               onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
            <button class="btn btn-primary" name="search">搜索</button>
        </div>
    </div>

    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>店铺ID</th>
                    <th>店铺名</th>
                    <th>联系方式</th>
                    <th>续费金额</th>
                    <th>续费日期</th>
                    <th>到期日期</th>
                    <th>备注</th>
                </tr>
                </thead>
                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        <td><a href="">${item->shop_id!}</a></td>
                        <td>${item->shop_name!}</td>
                        <td>${item->mobile!}</td>
                        <td>${item->renew_money!}</td>
                        <td>${item->renew_date!}</td>
                        <td>${item->expire_time!}</td>
                        <td>${item->renew_desc!}</td>
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
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数

</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include ("system.footer")