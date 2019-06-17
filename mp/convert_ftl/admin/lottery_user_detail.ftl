<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/lottery_manage.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .lottery_table td{
        padding:15px 0;
    }
</style>
<div class="main-container">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span><a href="/admin/market/market/lottery/list?id=${options['lottery_id']!}">幸运大抽奖</a> / </span><span><span class="ellipsis">${lottery_name!}</span> - </span><span style="color: #666;">获取新用户明细</span>

    </div>
    <form action="/admin/market/lottery/user/detail?top_index=4&id=${options['id']!}" method="post" id="form1">
        {{csrf_field()!}
{{--        {{print_r($data_list)!}--!}
        <ul class="search_inputs">
            <li class="clearfix">
                <div>
                    <span>手机号</span>
                    <input name="mobile" type="text" value="${options['mobile']!}" placeholder="请输入手机号">
                </div>
                <div>
                    <span>用户昵称</span>
                    <input name="username" type="text" value="${options['username']!}" placeholder="请输入用户昵称">
                </div>
                <div>
                    <span>邀请人</span>
                    <input name="invite_username" type="text" value="${options['invite_username']!}" placeholder="请输入邀请人昵称">
                </div>
                <a href="##" class="btn_searchs">查询</a>
            </li>
        </ul>
        <div class="lottery_table">
            <table width="100%">
                <thead>
                    <td width="25%">新用户昵称</td>
                    <td width="25%">新用户手机号</td>
                    <td width="25%">注册时间</td>
                    <td width="25%">邀请人</td>
                </thead>
                <tbody>
                    <#if ($data_list)
                        <#list ($data_list as $user)
                            <tr>
                                <td><a target="_blank"  href="/admin/user/manage/center?user_id=${user->user_id!}&top_index=5&sub_index=0" >${user->username!}</a></td>
                                <td>${user->mobile!}</td>
                                <td>${user->create_time!}</td>
                                <td>${user->invite_username!}</td>
                            </tr>
                        </#list>
                    </#if>

                </tbody>
            </table>
        </div>
        <div class="paging_footer">
       <#include "/admin/jump_page_admin.ftl">
        </div>
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