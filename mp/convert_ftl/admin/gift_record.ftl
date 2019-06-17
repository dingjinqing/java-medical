<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/lottery_manage.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .lottery_table td{
        padding:15px 0;
    }
    .search_inputs{
        margin-bottom: 10px;
    }
    .lottery_table{
        padding-top:10px;
    }
    .paging_footer{
        margin-top: 0;
    }
</style>
<div class="main-container">
    <div class="title">
        <span><a href="/admin/market/view">营销管理</a>  / </span><a href="/admin/market/gift/list">赠品 / </a><span style="color: #666;">赠品明细</span>
    </div>
    <form action="/admin/market/gift/record?id=${request['id']!}" method="post" id="form1">
        {{csrf_field()!}
        <ul class="search_inputs">
            <li class="clearfix">
                <div>
                    <span>手机号</span>
                    <input name="mobile" type="text" value="${request['mobile']!}" placeholder="请输入手机号">
                </div>
                <div>
                    <span>微信昵称</span>
                    <input name="username" type="text" value="${request['username']!}" placeholder="请输入微信昵称">
                </div>
                <div>
                    <span>赠送时间</span>
                    <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${request['start_time']!}"/>
                    至&nbsp;&nbsp;
                    <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${request['end_time']!}"/>
                </div>
                <a href="##" class="btn_searchs">查询</a>
            </li>
        </ul>
        <div class="lottery_table" style="min-height: 0px !important;" >
            <table width="100%">
                <thead>
                <td width="17%">订单号</td>
                <td width="17%">用户ID</td>
                <td width="17%">昵称</td>
                <td width="17%">手机号</td>
                <td width="17%">赠送时间</td>
                <td width="17%">赠品件数</td>
                </thead>
                <tbody>
                <#list  ($list as $record)
                        <tr>
                            <td><a target="_blank" href="/admin/orders/manage/info?order_sn=${record->order_sn!}">${record->order_sn!}</a></td>
                            <td>${record->user_id!}</td>
                            <td><a target="_blank"  href="/admin/user/manage/center?user_id=${record->user_id!}" >${record->username!}</a></td>
                            <td>${record->mobile!}</td>
                            <td>${record->pay_time ?: $record->add_time!}</td>
                            <td>${record->gift_num!}</td>
                        </tr>
                </tbody>
                </#list>
            </table>
        </div>
        <#if ($list ->count())
            <div class="paging_footer">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        <#else>
            <div class="paging_footer" style="padding: 0 23px;height: 130px;">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </#if>
    </form>
</div>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<script type="text/javascript">

    $(".btn_searchs").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    })

</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','gift','sub_4','赠品策略',0);
</script>