<link rel="stylesheet" href="/css/admin/lottery_manage.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .lottery_table td{
        padding:15px 0;
    }
    .lottery_table{
        padding-top:10px;
    }
    .lottery_table table{
        border-collapse: collapse;
        border-spacing: 0;
    }
    .paging_footer{
        margin-top: 0;
    }
    .lottery_table{
        min-height: 100px !important;
    }

</style>
<div class="main-container">
        <div class="lottery_table">
            <table width="100%">
                <thead>
                <td width="10%">用户ID</td>
                <td width="10%">用户昵称</td>
                <td>手机号码</td>
                <td width="8%">是否新用户</td>
                <td>参团时间</td>
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
</div>
<script type="text/javascript">
    // getPowerInfo('main_config','pin_integration','sub_4','组队瓜分积分',0);
</script>
