(function ($) {

    $("#convert_view_mode").click(function () {
        if ($('[name="show_type"]').val() == 'list') {
            $('[name="show_type"]').val('bigimg');
            $("#bigvideo_data").show();
            $("#list_data").hide();
            $(this).val(window.jslang.video_manager.list_show);
        } else {
            $('[name="show_type"]').val('list');
            $("#bigvideo_data").hide();
            $("#list_data").show();
            $(this).val(window.jslang.video_manager.big_video_show);
        }
    });

    function call_parent_video_cb(video_info) {
        var on_video_cb = $("#on_video_cb").val();
        if (on_video_cb && window.parent && window.parent.window[on_video_cb]) {
            on_video_cb = window.parent.window[on_video_cb];
            on_video_cb(video_info);
        }
    }

    function remove_item(video_id) {
        if (!video_id) {
            var show_type = $('[name="show_type"]').val();
            if (show_type == 'list' && $("input[cbx_list]:checked").length == 0
                || show_type == 'bigimg' && $("input[cbx_bigimg]:checked").length == 0) {
                layer.msg(window.jslang.video_manager.select_del_video);
                return false;
            }
        }
        // if (!confirm(window.jslang.video_manager.confirm_del_video)) {
        //     return false;
        // }
        if (video_id) {
            $("input[cbx_list]").prop('checked', false);
            $("input[cbx_bigimg]").prop('checked', false);
            $("input[cbx_bigimg][value='" + video_id + "']").prop('checked', 'checked');
            $("input[cbx_list][value='" + video_id + "']").prop('checked', 'checked');
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: left;">' + '确认要删除么' + '</div>', {
                title: '提示'
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("#act").val("del");
                $("#form1").submit();
                layer.close(index);
            });
        });
        // $("#act").val("del");
        // $("#form1").submit();
    }


    $("#selall").change(function () {
        if ($(this).prop('checked')) {
            $("input[cbx_list]").prop('checked', 'checked');
            $("input[cbx_bigimg]").prop('checked', 'checked');
            $("li .video_sel").show();
        } else {
            $("input[cbx_list]").prop('checked', false);
            $("input[cbx_bigimg]").prop('checked', false);
            $("li .video_sel").hide();
        }
    });

    $("input[cbx_list]").change(function () {
        var video_id = $(this).val();
        var el = $("input[cbx_bigimg][value='" + video_id + "']");
        if ($(this).prop('checked')) {
            el.prop('checked', true);
            el.parents("li").find(".video_sel").show();
        } else {
            el.prop('checked', false);
            el.parents("li").find(".video_sel").hide();
        }
        if ($("input[cbx_list]:checked").length == $("input[cbx_list]").length) {
            $("#selall").prop('checked', 'checked');
        } else {
            $("#selall").prop('checked', false);
        }

    });
    $("input[cbx_bigimg]").change(function () {
        var video_id = $(this).val();
        if ($(this).prop('checked')) {
            $("input[cbx_list][value='" + video_id + "']").prop('checked', 'checked');
        } else {
            $("input[cbx_list][value='" + video_id + "']").prop('checked', false);
        }
        if ($("input[cbx_list]:checked").length == $("input[cbx_list]").length) {
            $("#selall").prop('checked', 'checked');
        } else {
            $("#selall").prop('checked', false);
        }
    });

    var g_dlg = null;

    function set_cat_id() {
        if (g_dlg) {
            g_dlg.close();
        }
        var cat_el = $("#upload_video_cat_id").clone().attr("id", "upload_video_cat_id_art");
        g_dlg = art.dialog({
            title: window.jslang.video_manager.move_to_new_dir,
            content: cat_el[0],
            okVal: window.jslang.video_manager.move,
            ok: function () {
                $("#act").val('set_cat_id');
                $("#set_cat_id").val($('#upload_video_cat_id_art').val());
                $("#form1").submit();
                return true;
            },
            cancelVal: window.jslang.video_manager.cancel,
            cancel: function () {
            }
        });
    }


    function operate_submit(op) {
        var show_type = $('[name="show_type"]').val();
        var el_chk = $("input[cbx_list]:checked");
        var el_bigvideo_chk = $("input[cbx_bigimg]:checked");
        if (show_type == 'list' && el_chk.length == 0
            || show_type == 'bigimg' && el_chk.length == 0) {
            layer.msg(window.jslang.video_manager.select_video);
            return;
        }
        if (op == '3') {
            set_cat_id();
            return;
        }
        var cbx = (show_type == 'list') ? el_chk : el_bigvideo_chk;
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
        if (g_dlg) {
            g_dlg.close();
        }
        var title = (op == '1') ? window.jslang.video_manager.copy_video_url
            : window.jslang.video_manager.copy_video_url_code;
        g_dlg = art.dialog({
            padding: 0,
            title: title,
            content: el[0],
            okVal: window.jslang.video_manager.ok,
            ok: function () {
                return true;
            },
            cancelVal: window.jslang.video_manager.cancel,
            cancel: function () {
            }
        });
    }


    $("#copy_video_url").click(function () {
        operate_submit(1);
    });

    $("#copy_video_url_code").click(function () {
        operate_submit(2);
    });

    $("#bat_set_video_cat").click(function () {
        operate_submit(3);
    });

    //$('[name="video_cat_id"]').change(function () {
    //    $("#form1").submit();
    //});

    $('[name="rows_per_page"]').change(function () {
        $("#form1").submit();
    });

    $('[name="upload_sort_id"]').change(function () {
        $("#form1").submit();
    });

    $("#list_data a.crop_video").click(function () {
        var w = parseInt($("#need_video_width").val());
        var h = parseInt($("#need_video_height").val());
        var opts = {
            remote_video_path: $(this).attr('video_path'),
            remote_video_url: $(this).attr('url'),
            remote_video_id: $(this).attr('video_id'),
            video_cat_id: $("#video_cat_id").val(),
            ok_cb: function (video_info) {
                $("#crop_video_id").val(video_info.video_id);
                video_info.selected = true;
                call_parent_video_cb(video_info);
                $("#upload_sort_id").val(0);
                $("#page").val(1);
                $("#form1").submit();
            }
        };
        if (!isNaN(w) && w > 0) opts.crop_width = w;
        if (!isNaN(h) && h > 0) opts.crop_height = h;
        $.jCropRemoteImage(opts);
    });

    $("#bigvideo_data a.crop_video").click(function (e) {
        var w = parseInt($("#need_video_width").val());
        var h = parseInt($("#need_video_height").val());
        var opts = {
            remote_video_path: $(this).attr('video_path'),
            remote_video_url: $(this).attr('url'),
            remote_video_id: $(this).attr('video_id'),
            video_cat_id: $("#video_cat_id").val(),
            ok_cb: function (video_info) {
                video_info.selected = true;
                call_parent_video_cb(video_info);
                $("#upload_sort_id").val(0);
                $("#page").val(1);
                $("#form1").submit();
            }
        };
        if (!isNaN(w) && w > 0) opts.crop_width = w;
        if (!isNaN(h) && h > 0) opts.crop_height = h;
        $.jCropRemoteImage(opts);
    });

    $("#bigvideo_data a.remove_video").click(function (e) {
        remove_item($(this).attr('video_id'));
    });

    $("#list_data a.remove_video").click(function (e) {
        remove_item($(this).attr('video_id'));
    });

    $("#bat_remove_img").click(function () {
        remove_item();
    });

    $("#bigvideo_data li img").click(function () {
        var w = parseInt($("#need_video_width").val());
        var h = parseInt($("#need_video_height").val());
        var el = $(this).parents("li").find("[cbx_bigimg]");
        if (w > 0 && parseInt(el.attr("video_width")) != w || h > 0 && parseInt(el.attr("video_height")) != h) {
            $(this).parents("li").find("a.crop_video").click();
        } else {
            el.click();

            if (el.prop("checked")) {
                $(this).parents("li").find(".video_sel").show();
            } else {
                $(this).parents("li").find(".video_sel").hide();
            }
            var o = {
                video_id: $(el).val(), url: $(el).attr('url'), path: $(el).attr('video_path'),
                video_path: $(el).attr('video_path'),
                video_name: $(el).attr('video_name'), video_size: $(el).attr('video_size'),
                video_width: parseInt(el.attr("video_width")), video_height: parseInt(el.attr("video_height")),
                selected: el.prop("checked")
            };
            call_parent_video_cb(o);
        }
    });


    $("#search_need").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    });

    var formData = {
        dynamic_data: [{
            name: "video_cat_id",
            selector: "#video_cat_id"
        }]
    };
    util.init_video_upload('up_video_btn', function (d) {
        if (d && d.error == 0) {
            for (var i in d.content) {
                var o = d.content[i];
                o.selected = true;
                call_parent_video_cb(o);
            }
            $("#upload_sort_id").val(0);
            $("#page").val(1);
            $("#form1").submit();
        } else {
            util.mobile_alert(d.message);
            // layer.msg(d.message);
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
        console.log(treeNodes.length + "," + (targetNode ? (targetNode.tId + ", " + targetNode.name) : "isRoot" ));
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
        return confirm(window.jslang.video_manager.del + treeNode.name
            + window.jslang.video_manager.del_tips);
    }


    function beforeRename(treeId, treeNode, newName, isCancel) {
        if (newName.length == 0) {
            alert(window.jslang.video_manager.node_cant_null);
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
        var name = prompt(window.jslang.video_manager.please_input_node_name);
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
            var msg = window.jslang.video_manager.confirm_del_video_dir;
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
        $("#video_cat_id").val(treeNode.id);
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
        zTree = $.fn.zTree.init($("#img-cat-tree"), setting, video_cat_arr);
        var sel_id = parseInt($("#video_cat_id").val());
        var node = zTree.getNodeByParam("id", isNaN(sel_id) ? 0 : sel_id);
        zTree.selectNode(node, true);
        rMenu = $("#rMenu");
    });


    window.cancel_select_img = function (video_id) {
        var cbx = null;
        if (!isNaN(parseInt(video_id)))
            cbx = $("input[cbx_bigimg][value='" + video_id + "']");
        else
            cbx = $("input[cbx_bigimg][video_path='" + video_id + "']");
        if (cbx.length > 0 && cbx.prop('checked')) {
            cbx.prop('checked', false);
            $(cbx).parents("li").find(".video_sel").hide();
        }
    }

    window.select_img = function (video_id) {
        var cbx = null;
        if (!isNaN(parseInt(video_id)))
            cbx = $("input[cbx_bigimg][value='" + video_id + "']");
        else
            cbx = $("input[cbx_bigimg][video_path='" + video_id + "']");
        if (cbx.length > 0 && !cbx.prop('checked')) {
            cbx.prop('checked', true);
            $(cbx).parents("li").find(".video_sel").show();
        }
    }

})(jQuery);
