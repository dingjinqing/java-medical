<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<link rel="stylesheet" href="/css/system/shop_pv.css" type="text/css" />
<link rel="stylesheet" href="/css/system/user_list.css" type="text/css" />
<style type="text/css">
    .tb-decorate-list>tbody>tr>td{
        height:50px;
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
     .order-info-li .fl{
        width: 266px;
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
                            <span>{{ trans("admin/user_list.mobile_number")!}</span>
                            <input type="text" name="mobile" value="${data->mobile!}" placeholder='{{ trans("admin/user_list.ipt_mobile_number")!}' />
                        </div>
                        <div class="fl">
                            <span>来源</span>
                            <select name="source" id="">
                                <option value="1">全部</option>
                                <option value="2">后台</option>
                                <option value="3">牡丹园门店</option>
                            </select>
                        </div>
                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>{{ trans("admin/user_list.wx_name")!}</span>
                            <input type="text" name="username" value="${data->username!}" placeholder='{{ trans("admin/user_list.ipt_wx_name")!}' />
                        </div>
                        <div class="fl" style="width: auto;">
                            <span>{{ trans("admin/user_list.reg_time")!}</span>
                            <input type="text" name="start_time" value="${data->start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input type="text" name="end_time" value="${data->end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <div class="fl2">
                            <input type="checkbox" name="has_mobile" class="ipt_checkbox" value="1" <#if ($data->has_mobile) checked </#if>/>
                            <label>有手机号</label>
                        </div>
                        <div class="fl2">
                            <input type="checkbox" name="has_account" class="ipt_checkbox" value="1" <#if ($data->has_account) checked </#if>/>
                            <label>有余额</label>
                        </div>
                        <div class="fl2">
                            <input type="checkbox" name="has_score" class="ipt_checkbox" value="1" <#if ($data->has_score) checked </#if> /><label>有积分</label>

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
                            <tr>
                                <td width="8%">ID</td>
                                <td width="8%">用户ID</td>
                                <td width="8%">店铺ID</td>
                                <td width="8%">店铺名称</td>
                                <td width="10%">{{ trans("admin/user_list.user_name")!}</td>
                                <td width="8%">{{ trans("admin/user_list.mobile_number")!}</td>
                                <td>openid</td>
                                <td width="12%">{{ trans("admin/user_list.yu_e")!}</td>
                                <td width="12%">{{ trans("admin/user_list.score")!}</td>
                                <td>{{ trans("admin/user_list.reg_time")!}</td>
                                <td>{{ trans("admin/user_list.operation")!}</td>
                            </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr id="user_${item->user_id!}" >
                                <td>${item->id!}</td>
                                <td style="padding:0">
                                    <input type="hidden" class="user_id" value="${item->user_id!}" />
                                    <input type="hidden" class="is_delete" value="${item->is_delete!}" />
                                    <span>${item->user_id!} <#if ($item->is_delete)[删]</#if></span>
                                </td>
                                <td>${item->shop_id!}</td>
                                <td>${item->shop_name!}(${item->nick_name!}) </td>
                                <td>
                                    <span style="display: inline-block;width: 100px;word-break: break-all;">
                                        <a href="/system/user/center?shop_id=${item->shop_id!}&user_id=${item->user_id!}" style="color: #5A8BFF;">${item->username!}</a>
                                    </span>
                                </td>
                                <td>${item->mobile!}</td>
                                <td style="width: 9em;word-break: break-all;">${item->wx_openid!}</td>
                                <td width="120px">
                                    <input type="text" value="${item->account or '0.00'!}" class="ipt-money" disabled />
                                </td>
                                <td width="120px">
                                    <input type="text" value="${item->score!}" class="ipt-integral" disabled />

                                </td>
                                <td>${item->create_time!}</td>
                                <td width="20%" style="text-align: center;">
                                    <a href="/system/order/list?shop_id=${item->shop_id!}&user_id=${item->user_id!}" class="btn_opera btn_stop">查看订单</a>  |
                                    <a href="/system/user/center?shop_id=${item->shop_id!}&user_id=${item->user_id!}" class="btn_opera btn_detail">{{ trans("admin/user_list.look_detail")!}</a>
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
