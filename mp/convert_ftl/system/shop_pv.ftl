<#include ("system.header")
<link href="/css/admin/goods_list.css?v=1.0.6" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<link rel="stylesheet" href="/css/system/shop_pv.css" type="text/css" />
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
    .member_list_main thead td a{
        color: #5A8BFF;
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
                        <div class="fl">
                            <span>店铺ID</span>
                            <input type="text" name="shop_id" value="${data->shop_id!}" placeholder='店铺ID' />
                        </div>
                        <div class="fl">
                            <span>店铺名称</span>
                            <input type="text" name="shop_name" value="${data->shop_name!}" placeholder='店铺名称' />
                        </div>

                    </li>
                    <li class="order-info-li clearfix">

                        <div class="fl" style="width: auto;">
                            <span>{{ trans("admin/shop.analysis_time")!}</span>
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
            <form action="" id="form2" method="post">
                {{ csrf_field()!}
                <input type="hidden" name="sort_field" id="sort_field" value="${sort_field!}">
                <input type="hidden" name="sort_way" id="sort_way" value="${sort_way!}">
                <input type="hidden" name="shop_id" value="${data->shop_id!}">
                <input type="hidden" name="shop_name" value="${data->shop_name!}">
                <input type="hidden" name="start_time" value="${data->start_time!}">
                <input type="hidden" name="end_time" value="${data->end_time!}">
                <div class="member_list_main">
                    <table width="100%">
                        <thead>
                        <tr>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(1);">店铺ID</a>
                                <span id="sort_symbo">${sort_symbo[1]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(2);">店铺名称</a>
                                <span id="sort_symbo">${sort_symbo[2]!}</span></td>
                            <td width="5%">小程序名</td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(3);">统计日期</a>
                                <span id="sort_symbo">${sort_symbo[3]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(4);">总访问量</a>
                                <span id="sort_symbo">${sort_symbo[4]!}</span>
                            </td>
                            <td width="5%">总用户数</td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(5);">访问次数</a>
                                <span id="sort_symbo">${sort_symbo[5]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(6);">访问人数</a>
                                <span id="sort_symbo">${sort_symbo[6]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(7);">打开次数</a>
                                <span id="sort_symbo">${sort_symbo[7]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(8);">转发次数</a>
                                <span id="sort_symbo">${sort_symbo[8]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(9);">转发人数</a>
                                <span id="sort_symbo">${sort_symbo[9]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(10);">新用户数</a>
                                <span id="sort_symbo">${sort_symbo[10]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(11);">人均停留时长</a>
                                <span id="sort_symbo">${sort_symbo[11]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(12);">次均停留时长</a>
                                <span id="sort_symbo">${sort_symbo[12]!}</span>
                            </td>
                            <td width="5%">
                                <a href="javascript:void(0);" onClick="return sort_f(13);">平均访问深度</a>
                                <span id="sort_symbo">${sort_symbo[13]!}</span>
                            </td>
                            <td width="25%">{{ trans("admin/user_list.operation")!}</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr id="user_${item->shop_id!}" >
                                <td>${item->shop_id!}</td>
                                <td>${item->shop_name!}</td>
                                <td>${item->nick_name!}</td>
                                <td>${item->ref_date!}</td>
                                <td>${item->visit_total!}</td>
                                <td>${item->user_num!}</td>
                                <td>${item->visit_pv!}</td>
                                <td>${item->visit_uv!}</td>
                                <td>${item->session_cnt!}</td>
                                <td>${item->share_pv!}</td>
                                <td>${item->share_uv!}</td>
                                <td>${item->visit_uv_new!}</td>
                                <td>${item->stay_time_uv!}</td>
                                <td>${item->stay_time_session!}</td>
                                <td>${item->visit_depth!}</td>

                                <td width="30%" style="text-align: center;">
                                    <a href="/system/shop/shop_view?shop_id=${item->shop_id!}&nick_name=${item->nick_name!}" target="_blank"  class="btn_opera btn_detail">店铺概览</a> |
                                    <a href="/system/analysis/basic/yesterday?shop_id=${item->shop_id!}&nick_name=${item->nick_name!}" target="_blank"  class="btn_opera btn_detail">访问趋势</a> |
                                    <a href="/system/goods/analysis/detail?shop_id=${item->shop_id!}&nick_name=${item->nick_name!}" target="_blank"  class="btn_opera btn_detail">商品统计</a> |
                                    <a href="/system/goods/analysis/shop?shop_id=${item->shop_id!}&nick_name=${item->nick_name!}" target="_blank"  class="btn_opera btn_detail">店铺统计</a>
                                    <br>
                                    <a href="/system/order/analysis/detail?shop_id=${item->shop_id!}&nick_name=${item->nick_name!}" target="_blank"  class="btn_opera btn_detail">订单统计</a> |
                                    <a href="/system/order/list?shop_id=${item->shop_id!}" target="_blank"  class="btn_opera btn_detail">店铺订单</a> |
                                    <a href="/system/user/analysis?shop_id=${item->shop_id!}" target="_blank"  class="btn_opera btn_detail">会员统计</a> |
                                    <a href="/system/analysis/visit/erp?shop_id=${item->shop_id!}&nick_name=${item->nick_name!}&word=erp2.0" target="_blank"  class="btn_opera btn_detail">访问统计</a>

                                </td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
                <div class="member_list_footer">
                    <table width="" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="##"
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
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form2").submit();
    }
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
    window.sort_f = function (field) {
        if ($("#sort_field").val() == field) {
            if ($("#sort_way").val() == 'desc') {
                $("#sort_way").val('asc');
            } else {
                $("#sort_way").val('desc');
            }
        } else {
            $("#sort_field").val(field);
            $("#sort_way").val('desc');
        }
        $("#form2").submit();
    };

</script>


<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
