/**
 * Created by 新国 on 2015/9/7.
 */

(function () {
    window.gopage = function (page) {
        $("#page").val(page);
        $("#form1").submit();
    }

    window.remove_cat = function (id) {
        $("#act").val('del');
        $("#img_cat_id").val(id);
        $("#form1").submit();
    }

    var g_dlg = null;
    window.edit_cat = function (id, pid, name) {
        if (g_dlg) {
            g_dlg.close();
        }
        var el = $("#edit_tbl").clone();
        el.find("#img_cat_name2").val(name);
        el.find("#img_cat_parent_id2 option[value='" + pid + "']").attr('selected', 'selected');
        g_dlg = art.dialog({
            title: window.jslang.image_category.edit_cat_name,
            content: el[0],
            okVal: window.jslang.image_category.edit_cat_name,
            ok: function () {
                if ($('#img_cat_name2').val() == '') {
                    art.dialog.tips(window.jslang.image_category.cat_name_cant_null);
                    return false;
                }
                $("#act").val('edit');
                $("#img_cat_id").val(id);
                $("#img_cat_name_edit").val($("#img_cat_name2").val());
                $("#img_cat_parent_id_edit").val($("#img_cat_parent_id2 option:selected").val());
                $("#form1").submit();
                return true;
            },
            cancelVal: window.jslang.image_category.cancel,
            cancel: function () {
            }
        });
    }

    window.add_cat = function (id) {
        if ($('[name="img_cat_name"]').val() == '') {
            art.dialog.tips(window.jslang.image_category.cat_name_cant_null);
            return false;
        }
        $("#act").val('add');
        $("#form1").submit();
    }

    $(document).ready(function () {
        // 去掉消息提示
        setTimeout(function () {
            $(".msg-info").fadeOut(500);
        }, 2000);
    });
}());