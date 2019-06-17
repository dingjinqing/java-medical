<#include ("system.header")

<#include ("system.mp_tab")

<form action="/system/mp/deploy/list" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <div class="box">
        <div class="form-inline">
            <select name="template_id" class="form-control">
                <option value="-1">选择模板Id</option>
                <#list ($template_list as $item)
                    <option value="${item->template_id!}"
                            <#if ($item->template_id == ($request['template_id'] ?? -1)) selected="selected" </#if>>${item->user_version!}</option>
                </#list>
            </select>

            <select name="audit_state" class="form-control">
                <option value="-1">选择审核状态</option>
                <option value="0" <#if (($request['audit_state'] ?? -1)  == 0) selected </#if>>未提交审核</option>
                <option value="1" <#if (($request['audit_state'] ?? -1)  == 1) selected </#if>>审核中</option>
                <option value="2" <#if (($request['audit_state'] ?? -1) == 2) selected </#if>>审核通过</option>
                <option value="3" <#if (($request['audit_state'] ?? -1) == 3) selected </#if>>审核未通过</option>
            </select>

            <select name="publish_state" class="form-control">
                <option value="-1">选择发布状态</option>
                <option value="0" <#if (($request['publish_state'] ?? -1) == 0) selected </#if>>未发布</option>
                <option value="1" <#if (($request['publish_state'] ?? -1) == 1) selected </#if>>已发布</option>
            </select>

            <input type="text" class="form-control" name="app_id" placeholder="请输入app_id"
                   value="${request['app_id']!}">

            <button class="btn btn-primary" name="search">搜索</button>
        </div>
    </div>
    <div class="box panel main-table">
        <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
            <thead>
            <tr>
                <th>模板ID</th>
                <th>app_id</th>
                <th>昵称</th>
                <th>头像</th>
                <th>上传代码时间</th>
                <th>审核状态</th>
                <th>发布状态</th>
                <th>发布时间</th>
                <th>错误日志</th>
            </tr>
            </thead>

            <#list ($data_list as $item)
                <tr style="text-align:center;">
                    <td>${item->bind_template_id!}</td>
                    <td>${item->app_id!}</td>
                    <td>${item->nick_name!}</td>
                    <td><img src="${item->head_img!}" width="50"></td>
                    <td>${item->last_upload_time!}</td>
                    <td>${audit_state_map[$item->audit_state]!}</td>
                    <td>
                        <#if ( $item->publish_state == 1)
                            <span class="label label-success">已发布</span>
                        <#else>
                            <span class="label label-danger">未发布</span>
                        </#if>
                    </td>
                    <td>${item->publish_time ?? ""!}</td>
                    <td width="200px">
                        <#if ($item->deploy_log)
                            <textarea onclick="this.select();" class="text-align-left text-danger"
                                      title="${item->deploy_log!}"
                                      style="width: 100%;max-height: 60px;overflow-y: auto; border: none" readonly>
                                ${item->deploy_log!}
                            </textarea>
                        </#if>
                    </td>
                </tr>

            </#list>
        </table>
        <table width="" border="0" class="tb_paging" style="width: 100%">
            <tr>
                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                    <a href="##" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                    <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                           size="5"
                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                </td>
            </tr>
        </table>
    </div>
</form>

<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    $('[name="template_id"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    $('[name="audit_state"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    $('[name="publish_state"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    $('[name="search"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });

</script>

<#include ("system.footer")
