<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.6" type="text/css" />
<style type="text/css">
    .tb-decorate-list>tbody>tr>td{
        height:50px;
    }
    .order-info-li .fl{
        width: 270px;
    }
    .order-info-li {
        min-width: 1090px;
    }
    .btn_add_group a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_add_group a:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
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
    .order-info-li .fl2{
        width:95px;
        float: left;
        margin-top:5px;
    }
    .order-info-li .fl2 input{
        width:16px;
        float: left;
        margin-top:-5px;
        margin-left: 20px;
     }
    .f1 input{
        width:auto;
    }
    .order-info-li:nth-of-type(2) input{
        width:150px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                <input type="hidden" name="page" value="1">
                <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
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
                        <div class="fl">
                            <span>账户ID</span>
                            <input type="text" name="sys_id" value="${data->sys_id!}" placeholder='账户ID' />
                        </div>
                        <div class="fl">
                            <span>账户名称</span>
                            <input type="text" name="user_name" value="${data->user_name!}" placeholder='账户名称' />
                        </div>
                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl" style="width: auto;">
                            <span>登录时间</span>
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
                {{ csrf_field()!}
                <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
                <div class="member_list_main">
                    <table width="100%">
                        <thead>
                            <tr style="height: 32px;">
                                <td width="10%">账户ID</td>
                                <td width="10%">用户ID</td>
                                <td width="10%">账户名称</td>
                                <td width="10%">店铺ID</td>
                                <td width="15%">店铺名称</td>
                                <td width="15%">小程序名</td>
                                <td width="10%">IP</td>
                                <td>登录时间</td>
                            </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr id="user_${item->user_id!}" style="height: 32px;">
                                <td>${item->sys_id!}</td>
                                <td style="padding:0">
                                    <span>${item->user_id!} </span>
                                </td>
                                <td>${item->user_name!}</td>
                                <td>${item->shop_id!}</td>
                                <td>${item->shop_name!}</td>
                                <td>${item->nick_name!} </td>
                                <td>${item->user_ip!}
                                </td>

                                <td>${item->add_time!}</td>
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


<div id="set-delete" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="is_delete"/>
    <div class="exchange_old">
        <span>提示:&nbsp;</span>禁止登录后会员将不能登录了，确定禁止登录吗？
    </div>
</div>
<div id="all-delete" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="is_delete"/>
    <div class="exchange_old">
        <span>提示:&nbsp;</span>禁止登录后会员将不能登录了，确定禁止登录吗？
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

    $("input[name='mobile']").blur(function() {
        if($("input[name='mobile']").val() != ""){
            var re=/^1[3456789]\d{9}$/;
            if(!re.test($("input[name='mobile']").val())){
                util.mobile_alert("手机号不正确");
                return false;
            }
        }
    });
</script>


<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
