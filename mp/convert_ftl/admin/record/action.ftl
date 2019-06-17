<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.1" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <span>基础配置 / </span><span style="color: #666;">操作记录</span>
    </div>
    <div class="order-container">
        <form action="" method="post" id="form1">
            {{ csrf_field()!}
            <div class="order-info">
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>操作人：</span>
                            <input type="text" name="name_or_mobile" value="${options['name_or_mobile']!}"/>
                        </div>
                        <div class="fl" style="width: 230px;">
                            <span>操作模块：</span>
                            <select name="action_type" style="width:100px;">
                                <option value="0" selected>全部模块</option>
                                <#if (is_array($record_type))
                                    <#list ($record_type as $type_id => $type_name)
                                        <option value="${type_id!}" <#if ($type_id == $options['action_type']) selected </#if>>
                                            ${type_name!}
                                        </option>
                                    </#list>
                                </#if>
                            </select>
                        </div>
                        <div class="fl" style="width: 450px;">
                            <span>操作时间：</span>
                            <input type="text" name="start_time" value="${options['start_time']!}" onclick="picker();"
                                id="startDate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                            至
                            <input type="text" name="end_time" value="${options['end_time']!}" onclick="picker();" id="endDate"
                                onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"
                                style="margin-left: 5px"/>
                        </div>
                        <a class="btn-choose" style="display: inline-block;line-height: 30px;padding-left: 28px;">查询</a>
                    </li>
                </ul>
            </div>
            <div class="member_list_main">
                <table width="100%">
                    <thead>
                    <tr>
                        <td width="20%">操作人</td>
                        <td width="10%">操作模块</td>
                        <td width="15%">操作时间</td>
                        <td width="55%">操作内容</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list ($data_list as $item)
                        <tr>
                            <td>${item->admin_name!}；${item->mobile!}</td>
                            <td>${record_type[$item->action_type]!}</td>
                            <td>${item->add_time!}</td>
                            <td>${item->action_desc!}</td>
                        </tr>
                    </#list>

                    </tbody>
                </table>
            </div>
            <div style="overflow: hidden;padding: 0px 12px 12px 12px;background: #fff;">
                <#include ('admin.jump_page_admin', ['data_list' => $data_list])
            </div>
        </form>
    </div>
</div>

<#include "/admin/footer.ftl">
<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    }
    $('.btn-choose').click(function () {
        $('#form1').submit();
    })
</script>