<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_since.css" type="text/css"/>
<div class="title">
    <span>订单管理 / </span><span style="color: #666;">物流查询</span>
</div>


<div class="order-container">
    <div class="order-info">
        <div id="expr_tpl_id">
            <h2>${shipping['name']!} &nbsp;&nbsp; ${shipping['expr_no']!}</h2>
            <br/>
            <table class="table table-striped">
                <tr class="expr_tpl_li">
                    <th class="text-info rec_time">时间</th>
                    <td class="text-info rec_info">地点和跟踪进度</td>
                </tr>
                <#list ((array)$shipping['data'] as $i=>$item)
                    <tr class="expr_tpl_li">
                        <th class="text-info <#if ($i==0) text-danger </#if> rec_time">${item['time']!}</th>
                        <td class="text-info <#if ($i==0) text-danger </#if> rec_info">${item['context']!}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</div>


<script src="/js/admin/order_list.js?v=1.0.1" type="text/javascript"></script>
<#include "/admin/footer.ftl">