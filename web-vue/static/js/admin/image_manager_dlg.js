(function ($) {

    function call_parent_img_cb(img_info) {
        var on_img_cb = $("#on_img_cb").val();
        if (on_img_cb && window.parent && window.parent.window[on_img_cb]) {
            on_img_cb = window.parent.window[on_img_cb];
            on_img_cb(img_info);
        }
    }

    function remove_item(img_id) {
        if (!img_id) {
            var show_type = $('[name="show_type"]').val();
            if (show_type == 'list' && $("input[cbx_list]:checked").length == 0
                || show_type == 'bigimg' && $("input[cbx_bigimg]:checked").length == 0) {
                layer.msg(window.jslang.image_manager.select_del_image);
                return false;
            }
        }
        // if (!confirm(window.jslang.image_manager.confirm_del_image)) {
        //     return false;
        // }
        if (img_id) {
            $("input[cbx_list]").prop('checked', false);
            $("input[cbx_bigimg]").prop('checked', false);
            $("input[cbx_bigimg][value='" + img_id + "']").prop('checked', 'checked');
            $("input[cbx_list][value='" + img_id + "']").prop('checked', 'checked');
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: '提示'
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
                , shade: 0
            }, function (index) {
                $("#act").val("del");
                $("#form1").submit();
                layer.close(index);
            });
        });
    }

    // $("#allAndNotAll").change(function () {
    //
    // })


    $("#allAndNotAll").change(function () {
        if ($(this).prop('checked')) {
            $("input[cbx_list]").prop('checked', 'checked');
            $("input[cbx_bigimg]").prop('checked', 'checked');
            $("li .img_sel").show();
        } else {
            $("input[cbx_list]").prop('checked', false);
            $("input[cbx_bigimg]").prop('checked', false);
            $("li .img_sel").hide();
        }
    });

    $("input[cbx_list]").change(function () {
        var img_id = $(this).val();
        var el = $("input[cbx_bigimg][value='" + img_id + "']");
        if ($(this).prop('checked')) {
            el.prop('checked', true);
            el.parents("li").find(".img_sel").show();
        } else {
            el.prop('checked', false);
            el.parents("li").find(".img_sel").hide();
        }
        if ($("input[cbx_list]:checked").length == $("input[cbx_list]").length) {
            $("#allAndNotAll").prop('checked', 'checked');
        } else {
            $("#allAndNotAll").prop('checked', false);
        }

    });
    $("input[cbx_bigimg]").change(function () {
        var img_id = $(this).val();
        if ($(this).prop('checked')) {
            $("input[cbx_list][value='" + img_id + "']").prop('checked', 'checked');
        } else {
            $("input[cbx_list][value='" + img_id + "']").prop('checked', false);
        }
        if ($("input[cbx_bigimg]:checked").length == $("input[cbx_bigimg]").length) {
            $("#allAndNotAll").prop('checked', 'checked');
        } else {
            $("#allAndNotAll").prop('checked', false);
        }
    });

    function set_cat_id() {
        var cat_el = $("#upload_img_cat_id").clone().attr("id", "upload_img_cat_id_art");
        layer.open({
            type: 1
            , title: [window.jslang.image_manager.move_to_new_dir, 'text-align:center;padding: 0px;']
            , offset: 'auto'
            , content: "<div style='padding:15px;border-bottom: 1px solid #eee;'>"+cat_el[0].outerHTML+"</div>"
            , btn: ['确定', '取消']
            , area: ['170px','160px']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {

            }, yes: function (index, layero) {
                $("#act").val('set_cat_id');
                $("#set_cat_id").val($('#upload_img_cat_id_art').val());
                $("#form1").submit();
                layer.close(index);
                return true;
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    }


    function operate_submit(op) {
        var show_type = $('[name="show_type"]').val();
        var el_chk = $("input[cbx_list]:checked");
        var el_bigimg_chk = $("input[cbx_bigimg]:checked");
        if (show_type == 'list' && el_chk.length == 0
            || show_type == 'bigimg' && el_bigimg_chk.length == 0) {
            layer.msg(window.jslang.image_manager.select_image);
            return;
        }
        if (op == '3') {
            set_cat_id();
            return;
        }
        var cbx = (show_type == 'list') ? el_chk : el_bigimg_chk;
        var el = $("<textarea style='width:600px;height:200px;overflow:auto;'></textarea>");
        cbx.each(function () {
            var txt = (op == '1') ? $(this).attr('url') : "<img src='" + $(this).attr('url') + "' >";
            var t = el.text();
            if (t == '') {
                el.text(txt);
            } else {
                el.text(t + "\n" + txt);
            }
        });
        var title = (op == '1') ? window.jslang.image_manager.copy_img_url
            : window.jslang.image_manager.copy_img_url_code;
        layer.msg(title);
    }


    $("#copy_img_url").click(function () {
        operate_submit(1);
    });

    $("#copy_img_url_code").click(function () {
        operate_submit(2);
    });

    $("#bat_set_img_cat").click(function () {
        operate_submit(3);
    });

    //$('[name="img_cat_id"]').change(function () {
    //    $("#form1").submit();
    //});

    $('[name="rows_per_page"]').change(function () {
        $("#form1").submit();
    });

    $('[name="upload_sort_id"]').change(function () {
        $("#form1").submit();
    });

    $("#list_data a.crop_image").click(function () {
        var w = parseInt($("#need_img_width").val());
        var h = parseInt($("#need_img_height").val());
        var opts = {
            remote_img_path: $(this).attr('img_path'),
            remote_img_url: $(this).attr('url'),
            remote_img_id: $(this).attr('img_id'),
            img_cat_id: $("#img_cat_id").val(),
            ok_cb: function (img_info) {
                $("#crop_img_id").val(img_info.img_id);
                img_info.selected = true;
                call_parent_img_cb(img_info);
                $("#upload_sort_id").val(0);
                $("#page").val(1);
                $("#form1").submit();
            }
        };
        if (!isNaN(w) && w > 0) opts.crop_width = w;
        if (!isNaN(h) && h > 0) opts.crop_height = h;
        $.jCropRemoteImage(opts);
    });

    $("#bigimg_data a.crop_image").click(function (e) {
        var w = parseInt($("#need_img_width").val());
        var h = parseInt($("#need_img_height").val());
        var opts = {
            remote_img_path: $(this).attr('img_path'),
            remote_img_url: $(this).attr('url'),
            remote_img_id: $(this).attr('img_id'),
            img_cat_id: $("#img_cat_id").val(),
            ok_cb: function (img_info) {
                img_info.selected = true;
                call_parent_img_cb(img_info);
                $("#upload_sort_id").val(0);
                $("#page").val(1);
                $("#form1").submit();
            }
        };
        if (!isNaN(w) && w > 0) opts.crop_width = w;
        if (!isNaN(h) && h > 0) opts.crop_height = h;
        $.jCropRemoteImage(opts);
    });

    $("#bigimg_data a.remove_image").click(function (e) {
        remove_item($(this).attr('img_id'));
    });

    $("#list_data a.remove_image").click(function (e) {
        remove_item($(this).attr('img_id'));
    });

    $("#bat_remove_img").click(function () {
        remove_item();
    });

    $("#bigimg_data li img").click(function () {
        var el = $(this).parents("li").find("[cbx_bigimg]");
        el.prop("checked", !el.prop("checked"));
        if (el.prop("checked")) {
            $(this).parents("li").find(".img_sel").show();
        } else {
            $(this).parents("li").find(".img_sel").hide();
        }
        var o = {
            img_id: $(el).val(), url: $(el).attr('url'), path: $(el).attr('img_path'),
            img_path: $(el).attr('img_path'),
            img_name: $(el).attr('img_name'), img_size: $(el).attr('img_size'),
            img_width: parseInt(el.attr("img_width")), img_height: parseInt(el.attr("img_height")),
            selected: el.prop("checked")
        };
        call_parent_img_cb(o);
        if ($("input[cbx_bigimg]:checked").length == $("input[cbx_bigimg]").length) {
            $("#allAndNotAll").prop('checked', 'checked');
        } else {
            $("#allAndNotAll").prop('checked', false);
        }
    });


    $("#search_need").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    });

    var formData = {
        dynamic_data: [{
            name: "img_cat_id",
            selector: "#img_cat_id"
        }]
    };
    util.init_image_upload('up_image_btn', function (d) {
        if (d && d.error == 0) {
            for (var i in d.content) {
                var o = d.content[i];
                o.selected = true;
                call_parent_img_cb(o);
            }
            $("#upload_sort_id").val(0);
            $("#page").val(1);
            $("#form1").submit();
        } else {
            layer.msg(d.message);
        }
    }, formData, true);

    var zTreeObj;
    // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
    var setting = {
        view: {
            selectedMulti: false
        },
        edit: {
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
            keep: {
                parent: true
            },
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: beforeDrag,
            beforeRemove: beforeRemove,
            beforeRename: beforeRename,
            onClick: onNodeClick,
            onRightClick: OnRightClick,
            beforeDrop: beforeDrop,
            onRename: onRename,
            onDrop: onDrop
        }
    };


    function beforeDrop(treeId, treeNodes, targetNode, moveType) {
        console.log(treeNodes.length + "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot"));
        if (targetNode.id == 0 && moveType != "inner") return false;
    }

    function beforeDrag(treeId, treeNodes) {
        if (treeNodes && treeNodes.length > 0) {
            if (treeNodes[0].id == 0) return false;
        }
        return true;
    }

    function beforeRemove(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("img-cat-tree");
        zTree.selectNode(treeNode);
        return confirm(window.jslang.image_manager.del + treeNode.name
            + window.jslang.image_manager.del_tips);
    }


    function beforeRename(treeId, treeNode, newName, isCancel) {
        if (newName.length == 0) {
            alert(window.jslang.image_manager.node_cant_null);
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            setTimeout(function () {
                zTree.editName(treeNode)
            }, 10);
            return false;
        }
        return true;
    }


    function OnRightClick(event, treeId, treeNode) {
        if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
            //zTree.cancelSelectedNode();
            var node = zTree.getNodeByParam("id", 0);
            zTree.selectNode(node, false);
            showRMenu("root", event.clientX, event.clientY);
        } else if (treeNode && !treeNode.noR) {
            zTree.selectNode(treeNode);
            if (treeNode.id == 0) {
                showRMenu("root", event.clientX, event.clientY);
            } else {
                showRMenu("node", event.clientX, event.clientY);
            }
        }
    }

    function showRMenu(type, x, y) {
        $("#rMenu ul").show();
        if (type == "root") {
            $("#m_del").hide();
            $("#m_rename").hide();
        } else {
            $("#m_del").show();
            $("#m_rename").show();
        }
        x = x + $(document).scrollLeft();
        y = y + $(document).scrollTop();

        var body_h = $("body").height();
        if (y + rMenu.height() + 10 > body_h) {
            y = y - rMenu.height();
        }
        rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});

        $("body").bind("mousedown", onBodyMouseDown);
    }

    function hideRMenu() {
        if (rMenu) rMenu.css({"visibility": "hidden"});
        $("body").unbind("mousedown", onBodyMouseDown);
    }

    function onBodyMouseDown(event) {
        if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
            rMenu.css({"visibility": "hidden"});
        }
    }

    function onRename(event, treeId, treeNode, isCancel) {
        if (isCancel == undefined || isCancel == false) {
            var param = {
                act: 'op_cat_rename',
                op_cat_id: treeNode.id,
                op_cat_name: treeNode.name
            };
            submit_cat_op(param);
        }
    }

    function onDrop(event, treeId, treeNodes, targetNode, moveType) {
        if (treeNodes && treeNodes.length > 0) {
            var param = {
                act: 'op_cat_move',
                op_cat_id: treeNodes[0].id,
                op_cat_pid: treeNodes[0].getParentNode().id
            };
            submit_cat_op(param);
        }
    }

    function onRemove(e, treeId, treeNode) {
    }

    function addTreeNode() {
        hideRMenu();
        var name = prompt(window.jslang.image_manager.please_input_node_name);
        if (name) {
            var node = zTree.getSelectedNodes()[0];
            if (!node) {
                node = zTree.getNodeByParam("id", 0);
            }
            var param = {
                act: 'op_cat_add',
                op_cat_name: name,
                op_cat_pid: node.id
            };
            submit_cat_op(param);
        }
    }

    function submit_cat_op(param) {
        $("#act").val(param.act);
        $("#op_cat_id").val(param.op_cat_id);
        $("#op_cat_name").val(param.op_cat_name);
        $("#op_cat_pid").val(param.op_cat_pid);
        $("#form1").submit();
    }

    function removeTreeNode() {
        hideRMenu();
        var nodes = zTree.getSelectedNodes();
        if (nodes && nodes.length > 0) {
            var msg = window.jslang.image_manager.confirm_del_image_dir;
            if (confirm(msg) == true) {
                var param = {
                    act: 'op_cat_del',
                    op_cat_id: nodes[0].id,
                };
                submit_cat_op(param);
            }
        }
    }

    function editTreeNode() {
        hideRMenu();
        var nodes = zTree.getSelectedNodes();
        if (nodes && nodes.length > 0) {
            zTree.editName(nodes[0]);
        }
    }

    var zTree, rMenu;

    function onNodeClick(event, treeId, treeNode, clickFlag) {
        $("#img_cat_id").val(treeNode.id);
        $("#page").val(1);
        $("#form1").submit();
    }

    $("#m_add").click(function () {
        addTreeNode();
    });

    $("#m_del").click(function () {
        removeTreeNode();
    });

    $("#m_rename").click(function () {
        editTreeNode();
    });

    $(document).ready(function () {
        zTree = $.fn.zTree.init($("#img-cat-tree"), setting, img_cat_arr);
        var sel_id = parseInt($("#img_cat_id").val());
        var node = zTree.getNodeByParam("id", isNaN(sel_id) ? 0 : sel_id);
        zTree.selectNode(node, true);
        rMenu = $("#rMenu");
        $(".ztree li a").each(function (index, element) {
            var down_menu_el = $(this).next(".menu_down");
            var tid = $(this).attr("id");
            var treeId = tid.replace("img-cat-", "").replace("_a", "");
            if (down_menu_el.length == 0) {
                down_menu_el = $("<span class='menu_down'></span>");
                $(this).after(down_menu_el);
                $(down_menu_el).attr("tid", treeId);
                if (index != 0)
                    $(down_menu_el).css({"visibility": "hidden"});
                $(down_menu_el).click(function (event) {
                    var tid = $(this).attr("tid");
                    var treeNode = zTree.getNodeByTId(tid);
                    downMenuClick(event, treeNode);
                });
                $(down_menu_el).contextmenu(function (event) {
                    var tid = $(this).attr("tid");
                    var treeNode = zTree.getNodeByTId(tid);
                    downMenuClick(event, treeNode);
                    event.stopPropagation();
                    event.preventDefault();
                });
            }
        });

        $(".ztree li a").hover(function () {
            $(".ztree li .menu_down").css({"visibility": "hidden"});
            var down_menu_el = $(this).next(".menu_down").css({"visibility": "visible"});
        });
    });

    function downMenuClick(event, treeNode) {
        if (!treeNode) {
            //zTree.cancelSelectedNode();
            var node = zTree.getNodeByParam("id", 0);
            zTree.selectNode(node, false);
            showRMenu("root", event.clientX, event.clientY);
        } else if (treeNode && !treeNode.noR) {
            zTree.selectNode(treeNode);
            if (treeNode.id == 0) {
                showRMenu("root", event.clientX, event.clientY);
            } else {
                showRMenu("node", event.clientX, event.clientY);
            }
        }
    }


    window.cancel_select_img = function (img_id) {
        var cbx = null;
        if (!isNaN(parseInt(img_id)))
            cbx = $("input[cbx_bigimg][value='" + img_id + "']");
        else
            cbx = $("input[cbx_bigimg][img_path='" + img_id + "']");
        if (cbx.length > 0 && cbx.prop('checked')) {
            cbx.prop('checked', false);
            $(cbx).parents("li").find(".img_sel").hide();
        }
    }

    window.select_img = function (img_id) {
        var cbx = null;
        if (!isNaN(parseInt(img_id)))
            cbx = $("input[cbx_bigimg][value='" + img_id + "']");
        else
            cbx = $("input[cbx_bigimg][img_path='" + img_id + "']");
        if (cbx.length > 0 && !cbx.prop('checked')) {
            cbx.prop('checked', true);
            $(cbx).parents("li").find(".img_sel").show();
        }
    }

})(jQuery);
