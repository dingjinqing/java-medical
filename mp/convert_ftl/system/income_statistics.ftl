<#include ("system.header")
<link rel="stylesheet" href="/css/system/layui/css/layui.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/layui_change.css?v=1.0.0" type="text/css"/>
<!-- <link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/> -->
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/util.js"></script>
<script language="JavaScript" src="/js/system/util.js?v=1.0.9"></script>
<style type="text/css">
    .renew{
        width: 25%;
    //height:250px;
        position: fixed;
        top:5%;
        left:40%;
        z-index: 9999;
        border:1px solid #eee;
        background: #ffffff;
        display: none;
    }
    .renew label{
        margin-left: 4%;
    }
    .renew input[type='text']{
        width: 55%;
    }
    .renew input[type='date']{
        width: 55%;
    }
    .re_title{
        width: 100%;
        border-bottom: 1px solid #ccc;
        padding:15px 0;
        text-align: center;
        margin-bottom: 20px;
    }
    .btn_conf{
        background: #86a7cb;
        color: #fff;
        margin-left: 20%;
        margin-right: 3%;
    }
    #new_time{
        height:23px;
    }
    .shop_message{
        margin-left:4%;
        margin-bottom: 15px;
    }
    .table>thead>tr>th{
        width: 7%;
    }
    .mask{
        width: 100%;
        height: 100%;
        background: #000;
        opacity:0.5;
        position: absolute;
        top:0;
        left:0;
        z-index: 9999;
        display: none;
    }
    .form-control{
        width: 160px;
        display: inline-block;
        margin-left: 20px;
    }
    .search{
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
        border: 1px solid #ccc;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 22px;
        border-radius: 2px;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        display: inline-block;
        width: 60px;
    }
</style>
<ul id="tab" class="nav nav-tabs">

    <li class="active"><a href="#" data-toggle="tab" onclick="location.href = '/system/statistics/incomeStatistics';">收入统计</a></li>
    <li><a href="" data-toggle="tab" onclick="location.href ='/system/statistics/incomeDetail';">续费明细</a></li>
</ul>
<form action="/system/statistics/incomeStatistics" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            <select name="is_use" id="" class="form-control">
                <option value="-1" selected>选择店铺状态</option>
                <option value="1" <#if ($post_data['is_use'] == 1) selected </#if>>使用中</option>
                <option value="2" <#if ($post_data['is_use'] == 2) selected </#if>>已过期</option>
            </select>
            <select name="is_enabled" id="" class="form-control" style="margin-left: 0px">
                <option value="" selected>选择禁用状态</option>
                <option value="1" <#if ($post_data['is_enabled'] == 1) selected </#if>>已禁用</option>
                <option value="0" <#if ($post_data['is_enabled'] == 0 && !is_null($post_data['is_enabled'])) selected </#if>>未禁用</option>
            </select>
            <select name="shop_type" id="" class="form-control">
                <option value="-1" selected>选择店铺类型</option>
                <#list ($version_list as $item)
                    <option value="${item->level!}" <#if ($post_data['shop_type'] == $item->level) selected </#if>>${item->version_name!}</option>
                </#list>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            续费时间：<input  style="width: 150px; height: 30px;" type="text" name="start_time" value="${post_data['start_time']!}" placeholder='请输入开始时间' id="startDate" onclick="picker();"
               onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
            &nbsp;&nbsp; 至 &nbsp;&nbsp;
            <input  style="width: 150px; height: 30px;" type="text" name="end_time" value="${post_data['end_time']!}" placeholder='请输入结束时间' id="endDate" onclick="picker();"
               onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
            <button class="btn btn-primary" name="search">搜索</button>
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>续费总收入</th>
                    <th>续费店铺数</th>
                    <th>店铺续费均价</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tr style="text-align:center;">
                    <td >${total_income!}</td>
                    <td >${total_shop_num!}</td>
                    <td >${avg!}</td>
                    <td>
                        <a href="/system/statistics/incomeDetail?is_use=${post_data['is_use']!}&shop_type=${post_data['shop_type']!}&start_time=${post_data['start_time']!}&end_time=${post_data['end_time']!}&is_enabled=${post_data['is_enabled']!}" class="btn_detail">查看详情</a><br>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>


<script>
    
    $(".search").click(function () {
        $("#form1").submit();
    })
</script>
<#include ("system.footer")
