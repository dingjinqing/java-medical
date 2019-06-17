<#include ("system.header")

<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/category/list">分类管理列表</a>
    </li>
    <#if ($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="/system/category/edit?cat_id=${category->cat_id!}">分类管理编辑</a></li>
    </#if>
</ul>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>

<form enctype="multipart/form-data" action="${act_url!}" name="form1" id="form1" method="post"
      >
    <input type="hidden" name="act" id="act" value="${act!}">
    <input type="hidden" name="cat_id" id="cat_id" value="${category->cat_id!}">
    <input type="hidden" name="parent_id" id="parent_id" value="${category->parent_id!}">
    <input type="hidden" name="keywords" id="keywords" value="${category->keywords!}">
    {{--<input type="hidden" name="level" value="${category->level!}">--!}
    {{ csrf_field()!}
    <div class="box panel">
        <div class="panel-body">
            <table class="tab_body show table">
                <tr>
                    <td>
                        {{ trans("system/category.cat_name")!}
                    </td>
                    <td>
                        <input type="text" name="cat_name" value="${category->cat_name!}">
                    </td>
                </tr>
                <tr style="display: none;   ">
                    <td>
                        {{ trans("system/category.create_time")!}
                    </td>
                    <td>
                        <input type="text" name="create_time" value="${category->create_time!}">
                    </td>
                </tr>

                <tr>
                    <td>
                        {{ trans("system/category.top_name.first")!}
                    </td>
                    <td>
                        <input type="text" name="first" value="${category->first!}">
                    </td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input type="button" value="保存" class="primary">&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

            </table>

        </div>
    </div>
</form>
<script>
    $(".primary").click(function(){
        if ($("[name='cat_name']").val() == '') {
            art.dialog.tips("商品分类不能为空");
            return false;
        }
        // 写入验证代码
        $("#form1").submit();
    });
</script>


<#include ("system.footer")
