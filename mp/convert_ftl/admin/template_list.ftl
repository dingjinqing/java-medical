<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/template_record_list.css?v=1.0.0" type="text/css" />
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span style="color: #666;">消息推送</span>
</div>
<form action="/admin/market/message/template/list" method="post" id="form1">
    {{ csrf_field()!}
<div class="main-container">
    <div  style="background: #fff;margin: 10px 0px;height: 55px;margin-top: 0px">
        {{--<ul class="custom_title clearfix">--!}
            {{--<li><a href="##">自定义消息模板</a></li>--!}
        {{--</ul>--!}
        <div class="btn_add_template" style="float:left;">
            <a href="/admin/market/message/template/add?top_index=4&sub_index=15" target="_blank">添加模板消息</a>
        </div>
    </div>
    <div class="template_list" style="padding-top: 10px">
        {{--<div class="system_info" style="display: inline-block;float: none;margin-top: 12px;">--!}
            {{--<p class="system_info_prompt">--!}
                {{--当前版本为高级版，还可创建 <span>5</span>条模板消息--!}
                {{--<img src="http://${image_domain!}/image/admin/system_icon.png" />--!}
            {{--</p>--!}
            {{--<img src="http://${image_domain!}/image/admin/system_shadow.png" class="system_shadow" />--!}
            {{--<div class="system_info_content">--!}
                {{--<div class="system_info_content_top">--!}
                    {{--基础版不可创建，高级版最多创建<span class="system_v2">20</span>条模板消息，旗舰版不限制--!}
                {{--</div>--!}
                {{--<div class="system_info_content_bottom">--!}
                    {{--<a href="##">了解更多</a>--!}
                {{--</div>--!}
            {{--</div>--!}
        {{--</div>--!}
        <div class="template_tab">
            <table class="tb-decorate-list" width="100%">
                <thead>
                <tr>
                    <th width="14%">消息名称</th>
                    <th width="14%">业务标题</th>
                    <th>发送时间</th>
                    <th>送达数量</th>
                    <th>回访数量</th>
                    <th>回访率</th>
                    <th>发送状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list  ($list as $item)
                    <tr>
                        <td> ${item->name!}</td>
                        <td> ${item->title!}</td>
                        <td>
                            <#if  ($item->send_action == 1)
                                ${item->add_time!}
                            <#else>
                                ${item->start_time!}
                            </#if>
                        </td>
                        <td> ${item->send_num!}</td>
                        <td> ${item->visit_num!}</td>
                        <td> ${item->visit_per!}</td>
                        <td>
                            <#if  ($item->send_action == 1) 已发送 </#if>
                            <#if  ($item->send_action == 2)
                                <#if  ($item->end_time < date('Y-m-d H:i:s'))
                                    已发送
                                <#elseif> ($item->start_time < date('Y-m-d H:i:s') && $item->end_time > date('Y-m-d H:i:s'))
                                    发送中
                                <#else>
                                    未发送
                                </#if>
                            </#if>
                            <#if  ($item->send_action == 3)
                                <#if  ($item->start_time > date('Y-m-d H:i:s'))
                                    未发送
                                <#else>
                                    已发送
                                </#if>
                            </#if>
                        </td>
                        <td class="tb-decorate-a">
                            <a href="/admin/market/message/template/detail?id=${item->id!}&top_index=4" target="_blank">查看详情</a>
                            <a href="/admin/market/message/template/recordList?id=${item->id!}&top_index=4"" target="_blank">发送记录</a>
                            <a onclick="delTemplate(${item->id!})">删除</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <div class="clearfix" style="margin-top: 12px;padding: 0 12px">
        <#include "/admin/jump_page_admin.ftl">
        </div>
    </div>
</div>
</form>
<script type="text/javascript">
    $('.template_list').css('min-height',$(window).height()-160);
    function delTemplate(id) {
        layer.confirm('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', { title: ['提醒', 'text-align:center;padding: 0px;'] }, function (index) {
            util.ajax_json('/admin/market/message/template/list', function (response) {
                if (response.error == 0) {
                    util.mobile_alert('删除成功');
                    window.location.reload();
                }
            }, {act: "del_template", id:id});
            layer.close(index);
        });
    }

</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','message_template','sub_4','消息推送',0);
</script>
