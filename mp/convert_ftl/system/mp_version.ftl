<#include ("system.header")

<#include ("system.mp_tab")
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<form action="/system/mp/version" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <input type="hidden" name="act" id="act">
    <input type="hidden" name="template_id" id="template_id">
    <div class="box ">
        <input type="button" class="btn btn-primary" onclick="return syn_mp();" name="syn" value="同步微信开放平台小程序代码模版">
    </div>
    <div class="box panel main-table">

        <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
            <thead>
            <tr>
                <th>模板ID</th>
                <th>模板版本号</th>
                <th>当前包版本</th>
                <th>模板描述</th>
                <th>模板添加时间</th>
                <th>开发appid</th>
                <th>开发app名称</th>
                <th>开发者</th>
                <th>是否删除</th>
                <th>是否当前使用</th>
                <th>操作</th>
            </tr>
            </thead>

            <#list ($data_list as $item)
                <tr style="text-align:center;">
                    <td>${item->template_id!}</td>
                    <td>${item->user_version!}</td>
                    <td>
                        <select name="package_version" template_id = "${item->template_id!}">
                            <option value="1" <#if  ($item->package_version == 1) selected </#if>>普通版本</option>
                            <option value="2" <#if  ($item->package_version == 2) selected </#if>>好物版本</option>
                        </select>
                    </td>
                    <td style="width: 15%">${item->user_desc!}</td>
                    <td>${item->create_time!}</td>
                    <td>${item->source_miniprogram_appid!}</td>
                    <td>${item->source_miniprogram!}</td>
                    <td>${item->developer!}</td>
                    <td>
                        <#if ($item->del_flag == "1")
                            已删除
                        <#elseif>($item->del_flag == "0")
                            正常
                        </#if>
                    </td>
                    <td>
                        <#if ($item->current_in_use == "1")
                            <span class="label label-success">当前使用</span>
                        <#elseif>($item->current_in_use == "0")
                            否
                        </#if>
                    </td>
                    <td style="width: 15%">
                        <div class="text-align-left padding-5">
                            <#if ($item->del_flag == "0")
                                <a class="center-block" href="javascript:void(0)"
                                   onclick="return batch_apply(${item->template_id!})">
                                    批量上传并提交审核
                                </a>

                                <a class="center-block"
                                   href="/system/mp/operate/log/list?template_id=${item->template_id!}">
                                    查看当前版本操作日志
                                </a>

                                <a class="center-block" href="javascript:void(0)"
                                   onclick="return set_current_in_use(${item->template_id!})">
                                    设置为当前使用版本
                                </a>
                            </#if>
                        </div>
                    </td>
                </tr>

            </#list>
        </table>
    </div>
</form>

<script>

    function syn_mp() {
        $("#act").val("syn");
        $("#form1").submit();
    }

    function batch_apply(id) {
        if (confirm("此操作将批量为所有小程序客户上传模板版本" + id + "并提交审核，是否确认此操作？")) {
            $("#act").val("batch_apply");
            $("#template_id").val(id);
            $("#form1").submit();
        }
    }

    function batch_publish(id) {
        if (confirm("此操作将批量发布所有小程序客户已审核通过的小程序（模板版本为" + id + "），是否确认此操作？")) {
            $("#act").val("batch_publish");
            $("#template_id").val(id);
            $("#form1").submit();
        }
    }

    function set_current_in_use(id) {
        $("#act").val("set_current_in_use");
        $("#template_id").val(id);
        $("#form1").submit();
    }

    $('[name="package_version"]').change(function () {
        util.ajax_json('/system/mp/package/version', function (response) {
                if (response.error == 0) {
                    util.mobile_alert('设置成功');
                    location.reload();
                } else {
                    util.mobile_alert(response.msg);
                }
        }, {template_id: $(this).attr('template_id'), package_version : $(this).val() })
    })
</script>

<#include ("system.footer")
