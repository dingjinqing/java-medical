<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.33" type="text/css" />
<link rel="stylesheet" href="/css/system/user_list.css" type="text/css" />
<style>
    .btn-choose:hover, .btn-choose:focus    {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/user_score_list.score_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/user_score_list.score_list")!}</span>
    </div>
    <div class="order-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                {{ csrf_field()!}
                <input type="hidden" class="user_id" name="user_id"  value="${user_id!}" />
                <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
                <ul>
                    <li class="order-info-li clearfix">
                        {{--<div class="fl">--!}
                            {{--<span>{{ trans("admin/user_score_list.mobile")!}</span>--!}
                            {{--<input type="text" name="mobile" value="${data->mobile!}" placeholder='{{ trans("admin/user_score_list.set_mobile")!}' />--!}
                        {{--</div>--!}
                        <div class="fl">
                            <span>{{ trans("admin/user_score_list.order_sn")!}</span>
                            <input type="text" name="order_sn" value="${data->order_sn!}" placeholder='{{ trans("admin/user_score_list.order_sn")!}' />
                        </div>
                        <div class="fl" style="width: auto;">
                            <span>{{ trans("admin/user_score_list.in_time")!}</span>
                            <input type="text" name="start_time" value="${data->start_time!}" placeholder='{{ trans("admin/user_score_list.choose_time")!}' id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_score_list.zhi")!}&nbsp;&nbsp;
                            <input type="text" name="end_time" value="${data->end_time!}" placeholder='{{ trans("admin/user_score_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <button class="btn-choose">{{ trans("admin/user_score_list.choose")!}</button>
                    </li>

                </ul>
            </form>
        </div>
        <div>
            <form action="" id="form2" method="">
                {{ csrf_field()!}
                <div class="member_list_main">
                    <input type="hidden" class="user_id" name="user_id"  value="${user_id!}" />
                    <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
                    <table width="100%">
                        <thead>
                            <tr>
                                <td>{{ trans("admin/user_score_list.user_name")!}</td>
                                <td>{{ trans("admin/user_score_list.mobile")!}</td>
                                <td>{{ trans("admin/user_score_list.order_sn")!}</td>
                                <td>{{ trans("admin/user_score_list.score")!}</td>
                                <td>{{ trans("admin/user_score_list.in_time")!}</td>
                                <td>{{ trans("admin/user_score_list.expire_time")!}</td>
                                <td>{{ trans("admin/user_score_list.remark")!}</td>
                            </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr id="user_${item->user_id!}">
                                <td style="position:relative;">
                                    <span>
                                        <a style="color: blue;"href="/system/user/center?shop_id=${data->shop_id!}&user_id=${item->user_id!}" target="_blank"> ${item->username!}</a>
                                    </span>
                                </td>
                                <td>${item->mobile!}</td>
                                <td>${item->order_sn!}</td>
                                <td>${item->score!}</td>
                                <td>${item->in_time!}</td>
                                <td>
                                    <#if ($item->expire_time == "0000-00-00 00:00:00")永久有效
                                        <#else> ${item->expire_time!}
                                        </#if>
                                </td>
                                <td>${item->remark!}</td>
                            </tr>
                            </#list>

                        </tbody>
                    </table>
                </div>
                <div class="member_list_footer">
                    <table width="100%" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form2").submit();
    }
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>
<script>
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}'            //总页码数
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include ("system.footer")