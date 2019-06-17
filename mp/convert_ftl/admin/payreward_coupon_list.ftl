<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/lottery_manage.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .lottery_table td{
        padding:15px 0;
    }
    .lottery_table{
        margin-top: 10px;
    }
    .paging_footer{
        margin-top: 0px;
    }
    .search_inputs li{
        display: inline-block;
        padding: 0 20px;
    }
    th {
        height: 35px;
        background: #F5F5F5;
        border: 0;
    }
</style>
<div class="main-container">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>
            <a href="/admin/market/payreward/list?top_index=4&nav=1">支付有礼</a>
            / ${act_info->activity_names!} /</span><span style="color: #666;">活动明细</span>
    </div>
        <ul class="search_inputs">
            <li class="clearfix">
                活动名称：${act_info->activity_names!}
            </li>
            <li class="clearfix">
                活动有效期：${act_info->act_start_time!} - ${act_info->act_end_time!}
            </li>
            <li class="clearfix">
                触发条件：支付金额满${act_info->least_money!}元
            </li>
        </ul>
        <div class="lottery_table" style="padding-top: 10px">
            <table class="tb-decorate-list">
                <thead>
                <tr>
                    <th width="16%">{{ trans("admin/market_manage.coupon_manage.coupon_name")!}</th>
                    <th width="7%">{{ trans("admin/market_manage.coupon_manage.value")!}</th>
                    <th width="7%">{{ trans("admin/market_manage.coupon_manage.lower_fee")!}</th>
                    <th width="7%">{{ trans("admin/market_manage.coupon_manage.count")!}</th>
                    <th width="7%">{{ trans("admin/market_manage.coupon_manage.get_limit")!}</th>
                    <th width="17%">{{ trans("admin/market_manage.coupon_manage.date_limit")!}</th>
                    <th width="7%">{{ trans("admin/market_manage.coupon_manage.get_person")!}</th>
                    <th width="7%">{{ trans("admin/market_manage.coupon_manage.give_person")!}</th>
                    <th width="7%">{{ trans("admin/market_manage.coupon_manage.used")!}</th>
                    <th width="18%">{{ trans("admin/market_manage.coupon_manage.operation")!}</th>
                </tr>
                </thead>
                <tbody>
                <#if ($data_list)
                    <#list ($data_list as $item)
                        <tr>
                            <td>
                                <span>${item->act_name!}</span>
                            </td>
                            <td><#if ($item->act_code=='voucher')${item->denomination!}元 <#else>${item->denomination!}折 </#if></td>
                            <td><#if ($item->use_consume_restrict ==0) 无限制<#else> <#if  ($item->least_consume == 0)无限制 <#else> ${item->least_consume!}</#if> </#if></td>
                            <td>${item->surplus!}</td>
                            <td><#if  ($item->receive_per_person == 0) 不限制 <#else> 限领${item->receive_per_person!}张 </#if> </td>
                            <td><#if ($item->validity >0)领取开始${item->validity!}天内有效 <#else> ${item->start_time!}<br/>至<br/>${item->end_time!}</#if></td>
                            <td>${item->receive_person!}/${item->receive_amount!}</td>
                            <td>${item->giveout_person!}/${item->giveout_amount!}</td>
                            <td>${item->used_amount!}</td>
                            <td class="tb-decorate-a">
                                <a href="/admin/public/coupon/get/list?top_index=${top_index!}&sub_index=${sub_index!}&id=${item->id!}" target="_blank">{{ trans("admin/market_manage.coupon_manage.get_detail")!}</a>
                            </td>
                        </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
</div>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script>
    getPowerInfo('main_config','pay_reward','sub_4','支付有礼',0);
</script>