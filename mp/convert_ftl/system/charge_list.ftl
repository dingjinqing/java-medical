<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.51" type="text/css" />
<style>
    .order-info-li .fl{  width:25%; }
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
    .list_info{
        border-bottom: 1px solid #eee;
        margin-bottom: 15px;
    }
    .list_info a{
        display: inline-block;
        height: 40px;
        line-height: 30px;
        width: 80px;
        text-align: center;
        font-size: 14px;
        margin: 0;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>会员管理 / </span>
        <span style="color: #666;">会员卡充值明细</span>
    </div>
    <div class="order-container">
        <div class="order-info">
            <div class="list_info">
                <a href="/system/user/charge/list?shop_id=${data->shop_id!}&card_no=${request["card_no"]!}&card_type=${request["card_type"]!}" style="border-bottom: 2px solid #5E88FC;">充值明细</a>
                <a href="/system/user/consume/list?shop_id=${data->shop_id!}&card_no=${request["card_no"]!}&card_type=${request["card_type"]!}">消费明细</a>
            </div>
            <form action="/system/user/charge/list" method="post" id="form1">
                {{ csrf_field()!}
                <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>昵称</span>
                            <input type="text" name="username" value="${request['username']!}" placeholder="输入昵称" />
                        </div>
                        <div class="fl">
                            <span>手机号码</span>
                            <input type="text" name="mobile" value="${request['mobile']!}" placeholder="输入手机号码" />
                        </div>

                        <div class="fl" style="width:auto">
                            <span>领取时间</span>
                            <input type="text" name="start_time" value="${request['start_time']!}" placeholder="" />
                            至
                            <input type="text" name="end_time" value="${request['end_time']!}" placeholder="" />
                        </div>
                        <button class="btn-choose">筛选</button>
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
                            <td>会员昵称</td>
                            <td>手机号</td>
                            <#if ($card_type == 0 or $request["card_type"] == 0)
                                <td>余额变动明细</td>
                                <td>余额变动原因</td>
                                <td>余额变动时间</td>
                            <#elseif>($card_type == 1 or $request["card_type"] == 1)
                                <td>次数变动明细</td>
                                <td>次数变动原因</td>
                                <td>次数变动时间</td>
                            </#if>
                            <td width="20%">备注</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $data)
                            <tr>
                                <td>${data->username!}</td>
                                <td>${data->mobile!}</td>
                                <td><#if ($data->type==0)${data->charge!}
                                    <#elseif>($data->type==1)${data->count!}</#if>
                                </td>
                                <td>${data->reason!}</td>
                                <td>${data->add_time!}</td>
                                <td>${data->message!}</td>
                                {{--<td><input type="text" value="${data->message!}" style="border: none" class="edit_message" order_sn="${data->order_sn!}" id="${data->id!}"></td>--!}
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
    var page_all = '${data_list->count!}'            //总页码数
</script>

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include ("system.footer")