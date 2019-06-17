<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/feedback_statistics.css?v=1.0.3" type="text/css" />
<style type="text/css">
    @media (min-width: 1700px){
        .container{
            min-width: 1700px;
        }
    }

</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4" >营销管理</a> / </span>
    <span><a href="/admin/market/form/list?top_index=4">表单统计</a> / </span>
    <span style="color: #666;">反馈统计</span>
</div>
<div class="container">
    <div class="feedback_container">
        <div class="form_name">${data['page']->page_name!}</div>
        <div class="form_info">
            <ul>
                <li>
                    参与人数：<span>${data['user_num']!}</span>
                </li>
                <li>
                    反馈数：<span>${data['num']!}</span>
                </li>
                <li style="width:360px">
                    有效期：<span>
                        <#if ($data['page']->is_forever_valid == 1)
                            永久有效
                        <#else>
                            ${data['page']->start_time!} - ${data['page']->end_time!}
                        </#if>
                            </span>
                </li>
                <li>
                    状态：<span>
                        <#if ($data['page']->state == 0)
                            未发布
                        <#elseif>($data['page']->state == 1)
                            已发布
                        <#elseif>($data['page']->state == 2)
                            已关闭
                        <#elseif>($data['page']->state == 3)
                            已删除
                        </#if>
                        </span>
                </li>

            </ul>
        </div>
    </div>
    <div class="feedback">
        <div class="info">
            <#if ($data_list)
                <#list ($data_list as $k=>$v)
                    <table>
                        <thead>
                        <td>(<#if ($v->show_types == 1 && $v->module_name == 'm_choose')多选<#else>单选</#if>/<#if ($v->confirm == 1)必填<#else>选填</#if>)${v->form_title!}</td>
                        <td>票数</td>
                        <td width="30%">占比</td>
                        </thead>
                        <tbody>
                        <#if ($v->value)
                            <#list ($v->value as $key=>$value)
                                <#if ($key != '未填')
                                 <tr>
                                    <td>${key!}</td>
                                    <td>${value!}</td>
                                    <td>
                                        <div class="progress" style="display: inline-block;">
                                            {{--<div class="bar" style="width: 60%;">60%</div>--!}
                                            <div class="bar" style="width: <#if ($data['num']){{round($value/$data['num']*100,2)!}% <#else> 0% </#if>"></div>
                                        </div>
                                        <div style="display: inline-block;width: 13%"><#if ($data['num']){{round($value/$data['num']*100,2)!}% <#else> 0% </#if></div>
                                    </td>
                                </tr>
                                 </#if>
                            </#list>
                            <#list ($v->value as $key=>$value)
                                <#if ($key == '未填')
                                    <tr>
                                        <td>${key!}</td>
                                        <td>${value!}</td>
                                        <td>
                                            <div class="progress" style="display: inline-block;">
                                                {{--<div class="bar" style="width: 60%;">60%</div>--!}
                                                <div class="bar" style="width: <#if ($data['num']){{round($value/$data['num']*100,2)!}% <#else> 0% </#if>"></div>
                                            </div>
                                            <div style="display: inline-block;width: 13%"><#if ($data['num']){{round($value/$data['num']*100,2)!}% <#else> 0% </#if></div>
                                        </td>
                                    </tr>
                                    @break
                                </#if>
                            </#list>
                        </#if>
                        <tr>
                            <td colspan="3" style="text-align: right;height: 40px;line-height: 40px;padding-right: 20px"><span>本题有效票数合计：${data['num']-$v->no_num!}</span><span style="margin-left: 20px">本题总票数合计：${data['num']!}</span></td>
                        </tr>
                        </tbody>
                    </table>
                </#list>
            </#if>
        </div>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','form_decoration','sub_4','表单统计',0);
</script>

