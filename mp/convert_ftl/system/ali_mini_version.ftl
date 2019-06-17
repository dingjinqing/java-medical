<#include ("system.header")

<#include ("system.mp_tab")
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<form action="/system/ali_mini/version" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <input type="hidden" name="act" id="act">
    <div class="box ">
        <div>
            <table class="table table-striped">
                <tr>
                    <td width="150px">版本号：</td>
                    <td><input type="text" name="app_version" size="31"></td>
                </tr>
                <tr>
                    <td width="150px">版本描述：</td>
                    <td><input type="text" name="app_version_desc" size="31"></td>
                </tr>
                <tr>
                    <td width="150px"></td>
                    <td><input type="button" class="btn btn-primary" onclick="return add_app_version();" name="add_version"
                               value="添加新版本"></td>
                </tr>
            </table>
        </div>

    </div>
    <div class="box panel main-table">

        <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>版本号</th>
                <th>版本描述</th>
                <th>添加时间</th>
            </tr>
            </thead>

            <#list ($data_list as $item)
                <tr style="text-align:center;">
                    <td>${item->rec_id!}</td>
                    <td>${item->app_version!}</td>
                    <td>${item->app_version_desc!}</td>
                    <td>${item->create_time!}</td>
                </tr>
            </#list>
        </table>
    </div>
</form>

<script>
    function add_app_version() {
        if ($('[name="app_version"]').val() == "") {
            art.dialog.tips("版本不能为空");
            return;
        }
        $("#act").val("add");
        $("#form1").submit();
    }
</script>

<#include ("system.footer")
