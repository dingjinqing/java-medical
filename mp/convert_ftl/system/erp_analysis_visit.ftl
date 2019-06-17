<#include ("system.header")
<script src="/js/layui/layui.js" type="text/javascript"></script>
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
<style>
    #analysis {
        min-width: 1090px;
        margin-top: 10px;
    }
    #analysis .layui-table tr th:not(:first-child), #analysis .layui-table tr td:not(:first-child){
        text-align: center;
    }
    #visit-page-table th {
        cursor: pointer;
    }
    #visit-page-table .asc-or-desc span {
        color: #5A8BFF;
        padding-left: 5px;
    }
    #analysis .layui-card-header .select_visit_trend {
        width: 160px;
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        color: #333;
        font-size: 14px;
        margin-right: 10px;
    }
    #analysis .layui-card .layui-card-header {
        font-size: 16px;
        border: none;
    }
</style>
<div id="analysis">
    <div class="layui-fluid">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <form action="/system/analysis/visit/erp" method="psot"  id="myform">
                        <input type="hidden" name="shop_id" value="${shop_id!}">
                        <input type="hidden" name="nick_name" value="${nick_name!}">
                        <div class="layui-card-header">
                            <span>店铺名称 (${nick_name!})</span>
                            <span class="item-image">
                                <select name="word" onchange="javascript:myform.submit();" class="select_visit_trend">
                                    <#list ($active as $key => $item)
                                        <option value="${key!}" <#if ($string==$key) selected </#if>>${item!}</option>
                                    </#list>
                                </select>
                            </span>
                            <span><button type="submit">刷新</button></span>
                        </div>
                    </form>
                    <div class="layui-row layui-col-space12 demo-list" id="visit-page-table">
                        <table class="layui-table" style="width: 99%; margin-top: 20px; margin-left: 5px;">
                            <thead>
                                <tr>
                                    <th>来源</th>
                                    <th>日期</th>
                                    <th>访问人数</th>
                                    <th>访问人次</th>
                                </tr>
                            </thead>
                            <tbody>
                            <#if ($data[0])
                                <#list ($data as $item)
                                    <tr>
                                        <td>${string_name!}</td>
                                        <td>${item->add_time2!}</td>
                                        <td>${item->num!}</td>
                                        <td>${item->total_count!}</td>
                                    </tr>
                                </#list>
                            <#else>
                                <tr>
                                    <td>暂无相关数据</td>
                                </tr>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include ("system.footer")