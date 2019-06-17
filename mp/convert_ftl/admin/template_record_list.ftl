<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/template_record_list.css?v=1.0.0" type="text/css" />
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span><a href="/admin/market/message/template/list?top_index=4">消息模板</a> / </span><span style="color: #666;">消息模板发送记录</span>
</div>
<form action="/admin/market/message/template/recordList?id=${_GET['id']!}&top_index=4" method="post" id="form1">
    {{ csrf_field()!}
    <div class="main-container">
        <div class="template_list">
            {{--<ul class="custom_title clearfix">
                <li><a href="##">自定义消息模板</a></li>
            </ul>--!}
            <div class="template_tab" style="padding-top: 20px;">
                <table class="tb-decorate-list" width="100%">
                    <thead>
                        <tr>
                            <th width="28%">用户</th>
                            <th>发送类型</th>
                            <th>是否已送达</th>
                            <th>是否已点击</th>
                            <th>首次访问时间</th>
                            <th>发送时间</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list  ($list as $item)
                        <tr>
                            <td> ${item->username!}</td>
                            <td>
                                <#if  ($item->template_platform == 1)
                                    小程序
                                    <#else>
                                    公众号
                                </#if>
                            </td>
                            <td> <#if  ($item->send_status == 1) 是 <#else> 否 </#if> </td>
                            <td> <#if  ($item->is_visit == 1) 是 <#else> 否 </#if> </td>
                            <td> ${item->visit_time!}</td>
                            <td> ${item->add_time!}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="clearfix" style="margin-top: 20px;">
               <#include "/admin/jump_page_admin.ftl">
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript">
    $('.template_list').css('min-height',$(window).height()-160);
    //分页

</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','message_template','sub_4','消息推送',0);
</script>