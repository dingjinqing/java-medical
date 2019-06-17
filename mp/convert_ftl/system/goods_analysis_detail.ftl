<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<style type="text/css">

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
    .order-info-li .fl2{
        width:90px;
        float: left;
        margin-top:5px;
    }
    .order-info-li .fl2 input{
        width:16px;
        float: left;
        margin-top:-5px;
        margin-left: 20px;
     }
    .member_list_main tbody td{
        padding:15px 0;
    }
    .member_list_main thead td{
        padding:15px 0;
    }
</style>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                <input type="hidden" name="page" value="1">
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">

                        <div class="fl" style="width: auto;">
                            <span>{{ trans("admin/user_list.reg_time")!}</span>
                            <input type="text" name="start_time" value="${data->start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input type="text" name="end_time" value="${data->end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>

                        <button class="btn-choose">{{ trans("admin/user_list.choose")!}</button>
                    </li>
                </ul>
            </form>
        </div>
        <div>
            <form action="" id="form2" method="">
                <input type="hidden" name="shop_id" value="${shop_id!}">
                <input type="hidden" name="nick_name" value="${nick_name!}">
                {{ csrf_field()!}
                <div class="member_list_main">
                    <table width="100%">
                        <thead>
                            <tr>
                                <td width="8%">店铺ID</td>
                                <td width="8%">店铺名称</td>
                                <td width="15%">小程序名称</td>
                                <td width="8%">添加时间</td>
                                <td width="10%">商品数量</td>
                                <td>{{ trans("admin/user_list.operation")!}</td>
                            </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list->items as $item)
                            <tr id="shop_${item->shop_id!}" >
                                <td>${item->shop_id!}</td>
                                <td>${item->shop_name!}</td>
                                <td>${nick_name!}</td>
                                <td>${item->time!}</td>
                                <td style="width: 9em;word-break: break-all;">${item->num!}</td>

                                <td width="20%" style="text-align: center;">
                                    <a href="/system/goods/list?shop_id=${shop_id!}&start_time=${item->time!}&end_time=${item->time!}" target="_blank"  class="btn_opera btn_detail">{{ trans("admin/user_list.look_detail")!}</a>
                                </td>
                            </tr>
                            </#list>

                        </tbody>
                    </table>
                </div>
                <div class="member_list_footer">
                    <table width="" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage,'currentPage'=>$data_list->currentPage,'count'=>$data_list->count,'total'=>$data_list->total])!}
                                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->lastPage!});">{{ trans("admin/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage!}"
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
        var last_page = '${data_list->lastPage!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form2").submit();
    }
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    var page_home = '${data_list->currentPage!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数

</script>


<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
