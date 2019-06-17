<#include ("system.header")
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/user_list.css">
<style>
    a {
        text-decoration: none;
    }
    .coupon-search:hover{
        color:#fff;
        text-decoration: none;
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .coupon-search:focus{
        color:#fff;
        text-decoration: none;
        background-color: #447af9 !important;
        border-color: #447af9 !important;
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
    .return-goods-box .paging_footer{
        margin-top: 20px;
    }
    .shop-template-container input{
        padding-left: 12px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/market_manage.market_manage_title")!} / </span>
        <#if ($type==1)
        <span>分裂优惠券管理</span>
        <#else>
        <span style="color: #666;">{{ trans("admin/market_manage.coupon_manage_title")!}</span>
        </#if>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a <#if ($type != 1) href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=0" <#else> href="/system/coupon/split?shop_id=${data->shop_id!}&nav=0" </#if>><#if ($type==1)所有分裂优惠券<#else>所有优惠券</#if></a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=1" <#else> href="/system/coupon/split?shop_id=${data->shop_id!}&nav=1" </#if>>进行中</a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=2" <#else> href="/system/coupon/split?shop_id=${data->shop_id!}&nav=2" </#if>>未生效</a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=3" <#else> href="/system/coupon/split?shop_id=${data->shop_id!}&nav=3" </#if>>已过期</a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=4" <#else> href="/system/coupon/split?shop_id=${data->shop_id!}&nav=4" </#if>>已停用</a>
                </li>
                <li class="active">
                    <a href="/system/coupon/get/list?shop_id=${data->shop_id!}&id=${id!}&type=${type!}" target="_blank">领取明细</a>
                </li>
            </ul>
        </div>

        <form <#if (!$access_id && $user_id) action="/system/coupon/get/list?shop_id=${data->shop_id!}&type=1&user_id=${user_id!}"
              <#elseif>(!$access_id && !$use_id) action="/system/coupon/get/list?shop_id=${data->shop_id!}&id=${id!}" <#else>
        action = "/system/coupon/get/list?shop_id=${data->shop_id!}&id=${id!}&access_id=${access_id!}"
              </#if> method="post" id="form1" name="form1">
            {{ csrf_field()!}
            <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
            <div class="box panel panel-body list-center-fee">
            <div class="form-inline shop-template-container">
                    <input type="hidden" name="del"/>
                    <label class="fee-pad">手机号：</label>
                    <input type="text" placeholder="请输入手机号" name="mobile" value="${mobile!}">
                    <label class="fee-pad user-name">用户昵称：</label>
                    <input type="text" name="username" value="${username!}">
                    <label class="fee-pad user-name">使用状态：</label>
                    <select name="status"  class="coupon-sel">
                        <option value="0">全部</option>
                        <option value="1" <#if ($status == 1) selected </#if>>未使用</option>
                        <option value="2" <#if ($status == 2) selected </#if>>已使用</option>
                        <option value="3" <#if ($status == 3) selected </#if>>已过期</option>
                        <option value="4" <#if ($status == 4) selected </#if>>已废除</option>
                    </select>
                    <button type="button" class="coupon-search">搜索</button>
            </div>
        </div>
        <div class="return-goods-box">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr class="get-list-th">
                                <th width="12%">{{ trans("admin/market_manage.coupon_get_list.user_nickname")!}</th>
                                <th width="12%">{{ trans("admin/market_manage.coupon_get_list.mobile")!}</th>
                                <th width="10%">{{ trans("admin/market_manage.coupon_get_list.coupon_name")!}</th>
                                <th width="6%">{{ trans("admin/market_manage.coupon_get_list.is_use")!}</th>
                                <th width="11%">{{ trans("admin/market_manage.coupon_get_list.use_order_code")!}</th>
                                <th width="13%">{{ trans("admin/market_manage.coupon_get_list.date_limit")!}</th>
                                <th width="13%">{{ trans("admin/market_manage.coupon_get_list.get_time")!}</th>
                                <th width="11%">{{ trans("admin/market_manage.coupon_get_list.use_time")!}</th>
                                <th width="11%">{{ trans("admin/market_manage.coupon_get_list.operation")!}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if ($user_coupon_list)
                                <#list ($user_coupon_list as $item)
                                    <tr>
                                        <td><a href="/system/user/center?shop_id=${data->shop_id!}&user_id=${item->user_id!}">${item->username!}</a></td>
                                        <td>${item->mobile!}</td>
                                        <td>${item->act_name!}</td>
                                        <td><#if ($item->is_used ==1) 是 <#elseif>($item->is_used == 3)已废除<#else> <#if (date('Y-m-d H:i:s')<=$item->end_time) 否 <#else> 已过期 </#if> </#if></td>
                                        <td>${item->order_sn!}</td>
                                        <td>${item->start_time!}至${item->end_time!}</td>
                                        <td>${item->created!}</td>
                                        <td>${item->used_time!}</td>
                                        <td class="tb-decorate-a">
                                            <#if ($item->is_used ==0 && date('Y-m-d H:i:s')<=$item->end_time) <a href="javascript:void(0)" class="abort" act_id="${item->id!}">{{ trans("admin/market_manage.coupon_get_list.abate")!} </#if></a>

                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            <div class="paging_footer">
                <table width="100%" border="0" class="tb_paging">
                    <tr>
                        <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$user_coupon_list->perPage(),'currentPage'=>$user_coupon_list->currentPage(),'count'=>$user_coupon_list->count,'total'=>$user_coupon_list->total()])!}
                            <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                            <a href="#"
                               onClick="return gopage(${user_coupon_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                            <a href="#"
                               onClick="return gopage(${user_coupon_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                            <a href="#"
                               onClick="return gopage(${user_coupon_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                            <input id="page" name="page" type="text" value="${user_coupon_list->currentPage()!}"
                                   size="5"
                                   onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        </form>
    </div>
</div>
<script>
    function gopage(page) {
        var last_page = '${user_coupon_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    $(".coupon-search").click(function(){
        $("#form1").submit();
    });
    $('.abort').click(function(){
        $('input[name="del"]').val($(this).attr("act_id"));
        $("#form1").submit();
    });
    var page_home = '${user_coupon_list->currentPage()!}'; //当前页码数
    var page_all = '${user_coupon_list->count!}'            //总页码数
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")
