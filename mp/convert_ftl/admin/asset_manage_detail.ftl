<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.4" type="text/css" />
<style>
    .nav-role {
        width: 100%;
        height: 55px;
        background-color: #fff;
        margin: 0 auto;
        font-size: 14px;
        box-sizing: border-box;
    }
    .nav-child-tabs>li.active>a {
        background: #fff !important;

        box-shadow: 0 2px 0 #5A8BFF;

        padding-bottom: 13px;
        box-sizing: border-box;
    }
    ul.nav-child-tabs>li {
        float: left;
        margin-right: 15px;
    }
    .layui-card-item {
        color: #999;
        font-size: 14px;
    }
    .layui-card-header {
        font-size: 16px;
        border: none;
        color: #333;
        padding-left: 22px;
        background-color: #fff;
    }
    .layui-card-header .select_visit_trend {
        width: 160px;
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        color: #333;
        font-size: 14px;
        margin-right: 10px;
        padding-left: 12px;
    }
    .nav-child-tabs {
        width: 97%;
        height: 50px;
        margin-left: 23px;
        padding-left: 0;
        line-height: 52px;
        border-bottom: 1px solid #eee;
        list-style: none;
    }
    .info_table .tb_paging{
        float: right;
    }
    .info_table .tb_paging td{
        padding: 0;
        border: none;
        text-align: right;
    }
    .info_table .tb_paging input{
        height: 30px;
        border: 1px solid #dedede;
        text-align: center;
        margin: 0 8px;
    }
    .info_table .tb_paging tr td a {
        display: inline-block;
        border: 1px solid #dedede;
        padding: 0px 8px;
        height: 30px;
        line-height: 30px;
        margin-left: 5px;
    }
    .info_table .tb_paging tr td a:first-child {
        margin-left: 15px;
    }
    .search_reason ul .re_li input{
        width: 160px;
        border-radius: 2px;
    }
    .search_reason ul .re_li span{
        width: 60px;
    }
    .search_reason ul .searchg_iytma select{
        width: 160px;
        border-radius: 3px;
        border: 1px solid #ccc;
    }
    .search_reason{
        margin-bottom: 0px;
    }
    .search_reason ul .btn_exel{
        width: 110px !important;
    }
    [type="button"]{
        padding: 0px 12px;
    }
    .search_reason ul .login_time{
        width: 475px;
    }
    .search_reason ul .searchg_iytma{
        width: 300px;
    }
</style>
<div class="title">
    <span><a href="/admin/survey/overview?top_index=0">概况</a> / </span><span><a href="/admin/survey/asset/manage?top_index=0&nav=${asset_content!}">资产管理</a> </span>
</div>
<div class="reserve-container">
    <div class="nav-role">
        <ul id="tab" class="nav-child-tabs">
            <li <#if ($asset_content==0) class="active" </#if>>
                <a href="/admin/survey/asset/detail?asset_content=0">现金资产管理</a>
            </li>
            <li <#if ($asset_content==1) class="active" </#if>>
                <a href="/admin/survey/asset/detail?asset_content=1">积分资产管理</a>
            </li>
        </ul>
    </div>
    {{--<div class="layui-card-header" style="margin-bottom: 10px">--!}
            {{--<select name="visit_trend" class="select_visit_trend core_index_type" >--!}
                {{--<option value="1">昨日</option>--!}
                {{--<option value="7">最近7天</option>--!}
                {{--<option value="30">最近30天</option>--!}
            {{--</select>--!}
            {{--<span class="layui-card-item">2018年11月06日</span>--!}
            {{-----!}
            {{--<span class="layui-card-item">2018年11月06日</span>--!}
    {{--</div>--!}
    <form action="/admin/survey/asset/detail?asset_content=${asset_content!}" id="form1" method="post">
        {{csrf_field()!}
        <input type="hidden" name="act">
        <div class="search_reason" style="padding: 10px 23px">
            <ul>
                <li class="clearfix">
                    <div class="login_time re_li">
                        <span>交易时间</span>
                        <input type="text" id="startDate" value="${basicData['start_time']!}" onclick="picker()" name="start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type="text" id="endDate" value="${basicData['end_time']!}" onclick="picker()" name="end_time" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})"  autocomplete="off">
                    </div>
                    <div class="searchg_iytma re_li">
                        <span>交易单号</span>
                        <input type="text" name="trade_sn" value="${basicData['trade_sn']!}" placeholder="请输入交易单号">
                    </div>
                    <div class="searchg_iytma re_li">
                        <span>资金流向</span>
                        <select name="trade_flow" id="trade_flow">
                            <option value="0" selected>全部</option>
                            <option value="1" <#if ($basicData['trade_flow']==1) selected </#if>>收入</option>
                            <option value="2" <#if ($basicData['trade_flow']==2) selected </#if>>支出</option>
                            <#if ($asset_content==0)
                                <option value="3" <#if ($basicData['trade_flow']==3) selected </#if>>待确定收入</option>
                            </#if>
                        </select>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="login_time re_li">
                        <span>交易金额</span>
                        <input type="text" value="${basicData['start_num']!}" name="start_num" >
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type="text" value="${basicData['end_num']!}"name="end_num">
                    </div>
                    <div class="searchg_iytma re_li">
                        <span>交易类型</span>
                        <select name="trade_type" id="trade_type">
                            <option value="0" selected>全部交易类型</option>
                            <#list ($trade_type as $tt)
                                <option value="${tt['value']!}" <#if ($basicData['trade_type']==$tt['value']) selected </#if>> ${tt['name']!}</option>
                            </#list>
                        </select>
                    </div>
                    <button type="button" class="btn_seach">筛选</button>
                    <button type="button" class="btn_exel">重置筛选条件</button>
                </li>
            </ul>
        </div>
        <div class="info_table" style="padding-bottom: 50px">
            <table width="100%" style="margin-bottom: 20px">
                <thead>
                <tr>
                    <th width="20%">交易时间</th>
                    <th width="10%">交易单号</th>
                    <th width="10%">交易<#if ($asset_content==1)积分<#else>金额</#if></th>
                    <th width="10%">交易用户</th>
                    <th width="10%">交易类型</th>
                    <th width="10%">资金流向</th>
                    <th width="20%">交易状态</th>
                </tr>
                </thead>
                <tbody>
                <#list ($data_list as $item)
                    <tr>
                        <td width="20%">${item->trade_time!}</td>
                        <td width="20%">${item->trade_sn!}</td>
                        <td width="10%">${item->trade_num!}</td>
                        <td width="10%"><a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #5A8BFF">${item->username!}</a></td>
                        <td width="10%">${trade_type[$item->trade_type - 1]['name']!}</td>
                        <td width="10%"><#if ($item->trade_flow==0)收入<#elseif>($item->trade_flow==1)支出<#else>待确定收入</#if></td>
                        <td width="10%"><#if ($item->trade_status==0)已入账<#else>已出账</#if></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
            <#include "/admin/jump_page_admin.ftl">
        </div>
    </form>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $(document).on("click", ".btn_seach", function (e) {
        $("#form1").submit();
    })
    $(document).on("click", ".btn_exel", function (e) {
        $('.search_reason').find("input").each(function (i,v) {
            $(v).val('');
        })
        $("#trade_type").val(0);
        $("#trade_flow").val(0);
    })
    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }


</script>
<script type="text/javascript">
</script>
































