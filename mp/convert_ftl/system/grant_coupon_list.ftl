<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .btn_to_give a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_to_give a:focus{
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
    .paging_footer{
        margin-top: 20px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/market_manage.market_manage_title")!} / </span>
        <span style="color: #666;">{{ trans("admin/market_manage.coupon_manage_title")!}</span>
    </div>
    <div class="main-container">
        <div class="coupon_type">
            <input type="hidden" value="${voucher!}" class="coupons_amonut">
            <ul>
                <li class="normal_type">
                    <a href="/system/coupon/manage?shop_id=${shop_id!}">普通优惠券</a>
                </li>
                <li class="fenlie_type">
                    <a href="/system/coupon/split?shop_id=${shop_id!}">分裂优惠券</a>
                </li>
                <li class="give_to_sb actives">
                    <a href="/system/coupon/grant/list?shop_id=${shop_id!}">定向发券</a>
                </li>
            </ul>
        </div>

        {{--发券部分--!}
        <div class="give_coupon">
            <div class="give_coupon_table">
                <form action="/system/coupon/manage?shop_id=${shop_id!}" method="post" id="form2">
                    {{csrf_field()!}
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="10%">{{ trans("admin/market_manage.grant_coupon.activities_name")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.create_time")!}</th>
                                    <th width="14%">{{ trans("admin/market_manage.grant_coupon.active_crowd")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.participate_number")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.coupon_names")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.use_conditions")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.coupon_price")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.coupon_validity")!}</th>
                                    <th width="10%">{{ trans("admin/market_manage.grant_coupon.operation")!}</th>
                                </tr>
                                </thead>
                                <#list ($data_list as $item)
                                    <tr>
                                        <td>${item->act_name!}</td>
                                        <td>${item->in_time!}</td>
                                        <td>
                                            <#if ($item->have_pay>0)
                                                ${item->have_pay!}天内有交易记录<br/>
                                            </#if>
                                            <#if ($item->no_pay>0)
                                                ${item->have_pay!}天内无交易记录<br/>
                                            </#if>
                                            <#if ($item->max_count>0)
                                                    购买次数大于${item->max_count!}次<br/>
                                            </#if>
                                            <#if ($item->min_count>0)
                                                购买次数小于${item->min_count!}次<br/>
                                            </#if>
                                            <#if ($item->max_count>0)
                                                    购买商品均价小于${item->min_ave_price!}元<br/>
                                            </#if>
                                            <#if ($item->max_count>0)
                                                    购买商品均价大于${item->max_ave_price!}次<br/>
                                            </#if>
                                            <#if ($item->card_id)
                                                持有会员卡
                                                    <#list ($item->card_list as $k=>$v)
                                                        <#if ($k>0)、</#if>${v->card_name!}
                                                    </#list>
                                                    <br/>
                                            </#if>
                                            <#if ($item->tag_id)
                                                属于标签
                                                    <#list ($item->tag_list as $k=>$v)
                                                        <#if ($k>0)、</#if>${v->tag_name!}
                                                    </#list>
                                                    <br/>
                                            </#if>
                                            <#if ($item->user)
                                                手动添加会员
                                            </#if>
                                        </td>
                                        <td>${item->number!}</td>
                                        <td>${item->act_name_b!}</td>
                                        <td><#if ($item->least_consume>0)满${item->least_consume!}元可用<#else>无限制</#if></td>
                                        <td><#if ($item->act_code == 'voucher')${item->denomination!}元 <#else> ${item->denomination!}折 </#if> </td>
                                        <td><#if ($item->validity>0) 自领取之日起${item->validity!}天有效 <#else> ${item->start_time!}至${item->end_time!}</#if></td>
                                        <td>
                                            <a href="/system/coupon/get/list?shop_id=${shop_id!}&id=${item->act_id!}&access_id=${item->id!}" target="_blank">发放明细</a>
                                        </td>
                                    </tr>
                                </#list>
                            </table>
                        </div>
                    </div>
                    <div class="paging_footer">
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
        {{--发券部分结束--!}
    </div>
</div>
<script>
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
</script>
<script>
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}'            //总页码数
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include ("system.footer")
<script type="text/javascript">
   var coupon_nums = $(".coupons_amonut").val();
   if(coupon_nums == 0){
       $(".click_tips").click(function () {
           util.mobile_alert("暂无可发放的优惠券，请先添加优惠券！");
       })
   }
</script>
