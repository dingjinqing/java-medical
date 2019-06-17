<#include ("system.header")

<#include ("system.mp_tab")

<link rel="stylesheet" href="/css/system/mp_table_list.css" type="text/css"/>
<form action="/system/mp/version/stat" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <div class="box">
        <div class="form-inline">
            <#list ($conditionFields as $field => $item)
                <select name="${field!}" class="condition form-control">
                    <option value="-1">选择${item['title']!}</option>
                    <#list ($item['value_list'] as $key => $value)
                        <option value="${key!}"
                                <#if ($key == ($request[$field] ?? -1)) selected="selected" </#if>>${value!}</option>
                    </#list>
                </select>
            </#list>
        </div>
        <br>
        <div class="form-inline">
            显示列：
            <#list ($conditionFields as $field => $item)
                <label><input type="checkbox" name="show_${field!}" value="1" class="show-item"
                              <#if (($request["show_".$field] ?? "") == "1") checked="1" </#if>>${item['title']!}
                </label>&nbsp;&nbsp;
            </#list>
        </div>
    </div>
    <div class="box panel main-table">
        <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
            <thead>
            <tr>
                <#list ($conditionFields as $field => $item)
                    <#if ($request["show_".$field] == "1")
                        <th>${item['title']!}</th>
                    </#if>
                </#list>
                <th>店铺数量</th>
            </tr>
            </thead>

            <#list ($data_list as $item)
                <tr style="text-align:center;">

                    <#list ($conditionFields as $field => $item2)
                        <#if ($request["show_".$field] == "1")
                            <td>${item2['value_list'][$item->$field]!}</td>
                        </#if>
                    </#list>

                    <td>
                        <a href="/system/mp/auth/list?<#list ($conditionFields as $field => $item2){{ ($request["show_".$field] == "1") ? "&".$field."=".$item->$field:""!}</#list>">${item->number!}</a>
                    </td>
                </tr>

            </#list>
        </table>
    </div>
</form>

<script>
    $('.condition').change(function () {
        $("#form1").submit();
    });

    $('.show-item').change(function () {
        $("#form1").submit();
    });

    $('[name="submit"]').click(function () {
        $("#form1").submit();
    });

</script>

<#include ("system.footer")
