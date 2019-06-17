<#include ("system.header")
<#include ("system.article_nav")
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />

<style>
    #cat_list_tbl td {
        text-align: center;
    }
</style>

<form action="/system/article/category" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="category_id" id="category_id" value="${category_id or ""!}">
    {{ csrf_field()!}
    <#if ($msg)
        <div class="box alert alert-block alert-warning msg-info">
            ${msg!}
        </div>
    </#if>

    <div class="box panel panel-body">
        {{ trans("system/article.category.article_category")!}：
        <input type="text" name="category_name" id="category_name" maxlength="20" value="" size="34" class="article_list_height">
        {{ trans("system/article.category.use_footer_nav")!}
        <select name="use_footer_nav" class="article_list_height">
            <option value="0">{{ trans("system/article.category.no")!}</option>
            <option value="1">{{ trans("system/article.category.yes")!}</option>
        </select>
        <button type="button" class="primary article_list_height"
                onclick="add();">{{ trans("system/common.operation.add")!}</button>
    </div>

    <div class="box panel panel-default main-table article-main-table">

        <table id="cat_list_tbl" cellspacing='1' cellpadding='3' width="100%" class="table">
            <tr id="first_tr">
                <th>{{ trans("system/common.operation.id")!}</th>
                <th>{{ trans("system/article.category.article_category")!}</th>
                <th>{{ trans("system/article.category.use_footer_nav")!}</th>
                <th>{{ trans("system/common.operation.operation")!}</th>
            </tr>
            <#list ($data_list as $item)
                <tr>
                    <td>${item->category_id!}</td>
                    <td>${item->category_name!}</td>
                    <td><#if ($item->use_footer_nav == 1)
                            {{ trans("system/article.category.yes")!}
                        <#else>
                            {{ trans("system/article.category.no")!}
                        </#if>
                    </td>
                    <td>
                        <a href="javascript:void(0);"
                           onclick="edit_item('${item->category_id!}','${item->category_name!}','${item->use_footer_nav!}');">{{ trans("system/common.operation.edit")!}</a>
                        &nbsp;&nbsp;
                        <a href="javascript:void(0);"
                           onclick="remove_item(${item->category_id!});">{{ trans("system/common.operation.del")!}</a>
                        &nbsp;&nbsp;
                    </td>
                </tr>
            </#list>
        </table>


        <script>

            function remove_item(id) {
                $("#act").val('del');
                $("#category_id").val(id);
                art.dialog({
                    title: '{{ trans("system/article.category.del_category")!}',
                    content: '{{ trans("system/article.category.confirm_del_category")!}',
                    opacity: 0.1,
                    margin: 0,
                    padding: 0,
                    okVal: '{{ trans("system/common.dialog.ok")!}',
                    ok: function () {
                        $("#form1").submit();
                    },
                    cancelVal: '{{ trans("system/common.dialog.cancel")!}',
                    cancel: function () {
                        return true;
                    }
                });

            }

            function add() {
                if ($('#category_name').val() == '') {
                    art.dialog.tips("{{ trans("system/article.category.category_cant_null")!}");
                    return;
                }
                $("#act").val('add');
                $("#form1").submit();
            }

            function edit_item(id, name, use_footer_nav) {
                var el = $(".edit_category");
                el.find('[name="category_id"]').val(id);
                el.find('[name="category_name"]').val(name);
                el.find('[name="use_footer_nav"]').prop("checked", use_footer_nav == '1');
                art.dialog({
                        title: "{{ trans("system/article.category.edit_category")!}",
                        content: el[0],
                        lock: true,
                        opacity: 0.1,
                        okVal: "{{ trans("system/common.dialog.ok")!}",
                        ok: function () {
                            if (el.find('[name="category_name"]').val() == '') {
                                art.dialog.tips("{{ trans("system/article.category.category_cant_null")!}");
                                return false;
                            }
                            $("#form2").submit();
                            return true;
                        },
                        cancelVal: "{{ trans("system/common.dialog.cancel")!}",
                        cancel: function () {
                            return true;
                        }
                    }
                );
            }

            $(document).ready(function () {
                $(".formtable td").mouseover(function () {
                    $(".formtable tr").removeClass("hover_tr");
                    $(this).parent().addClass("hover_tr");
                });
                // 去掉消息提示
                setTimeout(function () {
                    $(".msg-info").fadeOut(500);
                }, 2000);
            });

        </script>
    </div>
</form>


<div class="template hide">
    <div class="edit_category">
        <form action="/system/article/category" name="form2" id="form2" method="post">
            {{ csrf_field()!}
            {{ trans("system/article.category.article_category")!}：
            <input type="hidden" name="act"  value="edit">
            <input type="hidden" name="category_id" value="">

            <input type="text" name="category_name" maxlength="20" value="" size="34"><br><br>
            {{ trans("system/article.category.use_footer_nav")!}:
            <select name="use_footer_nav">
                <option value="0">{{ trans("system/article.category.no")!}</option>
                <option value="1">{{ trans("system/article.category.yes")!}</option>
            </select>
        </form>
    </div>
</div>

<#include ("system.footer")
