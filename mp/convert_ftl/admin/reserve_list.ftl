<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/reserve_info.css?v=1.0.2" type="text/css" />
<style type="text/css">
    .btn_searchinfo:hover,.btn_searchinfo:focus,
    .btn_add_activity:hover,.btn_add_activity:focus {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    .tb_paging td a {
        text-decoration: none;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .coupon_type{
        background: #fff;
        padding:10px 0 0;
    }
    .coupon_type ul{
        list-style:none;
        background: #f5f5f5;
        width: 97%;
        margin:0 auto;
        border:1px solid #f3f3f3;
    }
    .coupon_type ul:after{
        content: '';
        display: block;
        clear: both;
    }
    .coupon_type ul li{
        float: left;
        width: 100px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        cursor: pointer;
    }
    .coupon_type ul li a{
        display: block;
        width: 100%;
        height:100%;
    }
    .coupon_type ul .actives{
        background: #fff;
    }
    .exchange-num{
        padding: 10px 20px;
        border-bottom: 1px solid #eee;
        display: none;
    }
    .exchange-num div{
        line-height: 30px;
    }
    .exchange-num input{
        width: 100px;
        height: 30px;
        padding-left: 12px;
        margin: 0 10px;
        background-color: #fff;
        border: 1px solid #ccc;
    }
    .exchange-num .exchange_old input{
        border: 0;
    }
    .exchange-num .exchange_old{
        margin-bottom: 10px;
    }
    .exchange-num div:last-child span:last-child{
        color: #f66;
    }
</style>
<div class="title">
    <span><a href="/admin/store/manage/list?top_index=6">门店列表（${store->store_name!}）</a> / </span>
    <span style="color: #666;"><a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6">预约管理</a></span>
    {{--<span> / ${store->store_name!}</span>--!}
</div>
<form action="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6" method="post" id="form1">
    <input type="hidden" name="order_status" value="${order_status!}">
    <div class="reserve-container">
        <div class="coupon_type">
            <ul>
                <li class="normal_type actives">
                    <a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6 ">预约管理</a>
                </li>
                <li class="fenlie_type">
                    <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" class="edition_type">服务管理</a>
                </li>
                <li class="fenlie_type">
                    <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" class="edition_tech" title="技师管理">${technician_title!}管理</a>
                </li>
                <li class="give_to_sb">
                    <a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6">评价管理</a>
                </li>
            </ul>
        </div>
        <div class="reserve-info">
            {{ csrf_field()!}
            <ul>
                    {{--<input type="text" name="store_id" hidden value="${store_id!}">--!}
                    <input type="text" name="store_id" hidden value="${data_list->store_id!}">
                    <li class="search_info_li clearfix">
                        <div class="re_li ">
                            <span>预约手机号</span>
                            <input type="text" name="search_phone" placeholder="请填写手机号" value="${request['search_phone']!}">
                        </div>
                        <div class="re_li reverse_time" style="width: auto">
                            <span>预约时间</span>
                            <input type="text"  name="search_start_time"  placeholder='选择日期' id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['search_start_time']!}" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input type="text" style="margin-right: 90px;" name="search_end_time" placeholder='选择日期' id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" value="${request['search_end_time']!}" autocomplete="off"/>
                        </div>
                    </li>
                    <li class="search_info_li clearfix">
                        <div class="re_li">
                            <span>${technician_title!}</span>
                            <input type="text" name="technician_name" placeholder="请填写${technician_title!}姓名" value="${request['technician_name']!}">
                        </div>
                        <input type="text" placeholder="请输入预约人姓名、服务名称进行查询" class="search_input" name="search_content" value="${request['search_content']!}">
                        <button class="btn_searchinfo">查询</button>
                        <span class="btn_add_activity" store_id="${store_id!}">新建预约活动</span>
                        {{--<input type="hidden" name="store_id" value="${store_id!}">--!}
                    </li>
                </ul>
        </div>
        <div class="reserve-list">
            <ul class="reserve-list-nav clearfix">
                <li id="all" <#if (!isset($order_status)) class="nav-active" </#if>><a href="#">
                        <span class="wait_re">全部预约 <span class="wait_num">${num['all']!}</span></span>
                    </a></li>
                <li id="pending_pay" <#if (isset($order_status) && $order_status==3) class="nav-active" </#if>><a href="#">
                        <span class="wait_re">待付款 <span class="wait_num">${num['pending_pay']!}</span></span></a>
                </li>
                <li id="wait" <#if (isset($order_status) && $order_status==0) class="nav-active" </#if>><a href="#">
                        <span class="wait_re">待服务 <span class="wait_num">${num['wait']!}</span></span>
                    </a></li>
                <li id="cancell" <#if (isset($order_status) && $order_status==1) class="nav-active" </#if>><a href="#">
                        <span class="wait_re">已取消 <span class="wait_num">${num['cancell']!}</span></span>
                    </a></li>
                <li hidden><a href="##">已过期</a></li>
                <li id="finish" <#if (isset($order_status) && $order_status==2) class="nav-active" </#if>><a href="#">
                        <span class="wait_re">已完成 <span class="wait_num">${num['finish']!}</span></span></a></li>
            </ul>
            <div class="reserve-list-table">
                <table width="100%">
                    <thead>
                    <tr>
                        <td width="160px">预约人</td>
                        <td width="160px">服务名称</td>
                        <td width="160px">预约手机号</td>
                        <td width="200px">预约到店时间</td>
                        <td width="200px">${technician_title!}</td>
                        <td width="160px">预约支付金额</td>
                        <td width="300px">留言</td>
                        <td width="250px">操作</td>
                    </tr>
                    <tr class="jiange">
                        <td colspan="8"></td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list ($data_list as $item)
                        <tr class="reserve_tb_head">
                            <td colspan="8">
                                <span class="span1">
                                    <span style="color: #999;">预约单号：</span>
                                    ${item->order_sn!}
                                </span>
                                <#if (!empty($item->pay_name))
                                <span class="span2">支付方式：${item->pay_name!}</span>
                                </#if>
                                {{--<#if (empty($item->user_id) && $item->order_status==0)--!}
                                    {{--<span class="span2">核销码：${item->verify_code!}</span>--!}
                                {{--</#if>--!}
                                <span class="fr">
                                    <a href="##" class="add_note" name="${item->order_id!}" id="admin_meaage" store_id="${store_id!}">添加备注</a>
                                    <a href="/admin/store/services/reserve/detail?store_id=${store_id!}&top_index=6&order_sn=${item->order_sn!}" target="_blank">查看详情</a>
                                    <a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6&order_sn=${item->order_sn!}" target="_blank">查看评价</a>
                                </span>
                            </td>
                        </tr>
                        <tr class="reserve_tb_body">
                            <td width="160px">${item->subscriber!}</td>
                            <td width="160px">${item->service_name!}</td>
                            <td width="160px">${item->mobile!}</td>
                            <td width="200px">${item->service_date!} ${item->service_period!}</td>
                            <td width="200px">${item->technician_name!}</td>
                            <td width="160px">${item->service_subsist!}</td>
                            <td width="300px">${item->add_message!}</td>
                            <td width="250px">
                                <#if ($item->order_status==0)
                                        {{--<a href="/admin/store/services/reserve/cancelled?order_id=${item->order_id!}&store_id=${item->store_id!}&order_status=${item->order_status!}"--!}
                                           {{--class="btn_cansell" order_id="${item->order_id!}" store_id="${item->store_id!}" order_status="${item->order_status!}">取消</a>--!}
                                    <a href="#" class="btn_cansell" order_id="${item->order_id!}" store_id="${item->store_id!}" order_status="${item->order_status!}">取消</a>
                                        <a href="##" store_id="${store_id!}" order_id="${item->order_id!}" order_sn="${item->order_sn!}" class="btn_finish">核销</a>
                                <#elseif>($item->order_status==1)
                                    <span>已取消</span>
                                    {{--<a href="" style="margin-top: 5px" >退款并取消</a>--!}
                                <#elseif>($item->order_status==2)
                                    <span>已完成</span>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                    <tbody style="border: none;">
                    <tr>
                        <td colspan="8"></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="clearfix">
            <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </div>
    <div id="set-delete" class="exchange-num">
        <div class="exchange_old" >
            {{--<span>提示:&nbsp;</span>确定取消该订单吗？--!}
            <input type="text" name="cancelReason" value="" placeholder="请输入取消原因" style="width: 240px">
        </div>
    </div>
</form>
<script type="text/javascript">

    //分页
    function gopage(page) {
        var last_page = parseInt('${data_list -> lastPage()!}');
        if (parseInt(page) > last_page) {
            page = last_page;
        }
        $("#act").val("");
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    var path_url = window.location.href;
    var indesx = path_url .lastIndexOf("=");
    path_url  = path_url .substring(indesx + 1, path_url .length);
    $("input[name='store_id']").val(path_url);
    $(".btn_searchinfo").click(function () {
        // if($("input[name='search_phone']").val() != "") {
        //     var re = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
        //     if (!re.test($("input[name='search_phone']").val())) {
        //         layer.msg("请输入有效的手机号");
        //         return false;
        //     }
        // }
        $("#form1").submit();
    })
    function removeAllClass() {
            $("#all").removeClass("nav-active");
            $("#wait").removeClass("nav-active");
            $("#cancell").removeClass("nav-active");
            $("#finish").removeClass("nav-active");
    }
    $("#all").on("click",function(){
        $("input[name='order_status']").val(" ");
        $("#page").val(1);
        $("#form1").submit();
    });
    $("#wait").on("click",function(){
        $("input[name='order_status']").val(0);
        $("#page").val(1);
        $("#form1").submit();
    });
    $("#cancell").on("click",function(){
        $("input[name='order_status']").val(1);
        $("#page").val(1);
        $("#form1").submit();
    });
    $("#finish").on("click",function(){
        $("input[name='order_status']").val(2);
        $("#page").val(1);
        $("#form1").submit();
    });
    $("#pending_pay").on("click",function(){
        $("input[name='order_status']").val(3);
        $("#page").val(1);
        $("#form1").submit();
    });
</script>
<script language="JavaScript" src="/js/admin/reserve_detail.js?v=1.0.0"></script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/reserve_list.js?v=1.0.2" type="text/javascript"></script>
<#include "/admin/footer.ftl">
