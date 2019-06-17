<#include "/admin/header.ftl">

<ul id="tab" class="nav nav-tabs">
    <li <#if ($nav_type==0)class="active"</#if>><a href="#"
                                                 data-toggle="tab">选择店铺</a>
    </li>
    <li <#if ($nav_type==1)class="active"</#if>><a href="#"
                                                 data-toggle="tab">子账号管理</a>
    </li>
</ul>


<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url_arr = ['/admin/account/shop/select', '/admin/account/user/list'];
        var idx = $(this).parent().index();
        if (url_arr[idx] != undefined) {
            window.location.href = url_arr[idx];
        }
    });
</script>
<br>
<div class="alert alert-block alert-info">
    创建新店铺，请联系010-xxxxxx
</div>

<div class="box panel main-table">
    <div class="layer">

        <table id="list_tbl" cellspacing='1' cellpadding='3' width="100%" class="table">
            <thead>
            <tr id="first_tr">
                <th>店铺ID</th>
                <th>店铺名称</th>
                <th>操作</th>
            </tr>
            </thead>
            <#list ($data_list as $item)
                <tr>
                    <td>${item->shop_id!}</td>
                    <td>${item->shop_name!}</td>
                    <td>
                        <a href="/admin/account/shop/switch?shop_id=${item->shop_id!}">选择店铺</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </#list>
        </table>
    </div>
</div>

<#include "/admin/footer.ftl">