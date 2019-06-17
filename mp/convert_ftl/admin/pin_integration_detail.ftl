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
    .search_inputs li div{
        margin-bottom: 10px;
    }
</style>
<div class="main-container">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
            <span style="color: #666;"><a href="/admin/market/integration/list">组团瓜分积分</a>/</span>
            <span style="color: #666;">${basicData->name!}-参与人数明细</span>
        </div>
    </div>
    <form action="/admin/market/integration/detail?top_index=4" method="post" id="form1">
        {{csrf_field()!}
        {{--        {{print_r($data_list)!}--!}
        <input name="id" value="${basicData->id!}" hidden>
        <ul class="search_inputs">
            <li class="clearfix">
                <div>
                    <span>用户手机号</span>
                    <input name="mobile" type="text" value="${request['mobile']!}" placeholder="请输入手机号">
                </div>
                <div>
                    <span style="width: 84px">用户昵称</span>
                    <input name="username" type="text" value="${request['username']!}" placeholder="请输入用户昵称">
                </div>
                <div>
                    <span>参团时间</span>
                    <span>
                      <input type="text" class="tb-text date-text" value="${request['start_time']!}" name="start_time"
                             id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                      <input type="text" class="tb-text date-text" value="${request['end_time']!}"  name="end_time"
                               id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                    </span>
                </div>
            </li>
            <li class="clearfix">
                <div>
                    <span style="margin-right: 24px">是否团长</span>
                    <select name="is_grouper" id="">
                        <option value="0" selected>请选择</option>
                        <option value="2" <#if ($request['is_grouper']==2) selected </#if>>是</option>
                        <option value="1" <#if ($request['is_grouper']==1) selected </#if>>否</option>
                    </select>
                </div>
                <div>
                    <span>邀请用户数量</span>
                    <input name="invite_num" type="text" value="${request['invite_num']!}" placeholder="请输入邀请用户数量">
                </div>
                <div>
                    <span>瓜分积分</span>
                    <span>
                      <input type="text" class="tb-text date-text" value="${request['start_inte']!}" name="start_inte"> 至
                      <input type="text" class="tb-text date-text" value="${request['end_inte']!}"  name="end_inte">
                    </span>
                </div>
            </li>
            <li class="clearfix">
                <div>
                    <span >是否新用户</span>
                    <select name="is_new" id="">
                        <option value="0" selected>请选择</option>
                        <option value="2" <#if ($request['is_new']==2) selected </#if>>是</option>
                        <option value="1" <#if ($request['is_new']==1) selected </#if>>否</option>
                    </select>
                </div>
                <div>
                    <span style="width: 83px">团ID</span>
                    <input name="group_id" type="text" value="${request['group_id']!}" placeholder="请输入团ID">
                </div>
                <a href="##" class="btn_searchs">查询</a>
            </li>
        </ul>
        <div class="lottery_table" <#if (!$list -> count()) style="min-height: 0px !important;" </#if>>
            <table width="100%">
                <thead>
                <td width="10%">用户ID</td>
                <td width="10%">用户昵称</td>
                <td>手机号码</td>
                <td width="8%">是否新用户</td>
                <td>参团时间</td>
                <td width="10%">团ID</td>
                <td width="8%">邀请用户数量</td>
                <td>消耗积分</td>
                <td>是否团长</td>
                </thead>
                <tbody>
                    <#list ($list as $item)
                        <tr>
                            <td>${item->user_id!}</td>
                            <td>${item->username!}</td>
                            <td>${item->mobile!}</td>
                            <td>
                                <#if ($item->is_new==1)
                                    是
                                <#else>
                                    否
                                </#if>
                            </td>
                            <td>${item->start_time!}</td>
                            <td>${item->group_id!}</td>
                            <td>${item->invite_num!}</td>
                            <td>${item->integration!}</td>
                            <td>
                                <#if ($item->is_grouper==1)
                                    是
                                    <#else>
                                    否
                                </#if>
                            </td>
                        </tr>
                        </#list>
                </tbody>
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
        if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
            util.mobile_alert('开始时间不能大于结束时间');
            return false;
        }
        if($('input[name="start_time2"]').val() > $('input[name="end_time2"]').val()){
            util.mobile_alert('开始时间不能大于结束时间');
            return false;
        }
        $("#form1").submit();
    })
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }

</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','pin_integration','sub_4','组队瓜分积分',0);
</script>