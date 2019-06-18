// 店铺装修js
$('#save_content').mouseover(function () {
    $('#save_content').css({'background': "#447af9", "border": "1px solid #447af9"});
})
$('#save_content').mouseleave(function () {
    $('#save_content').css({'background': "#5a8bff", "border": "1px solid #5s8bff"});
})
var g_insert_index = -1;
var g_dlg = null;

function show_links_dlg2(cb, param) {
    return show_links_dlg(cb, param);
}

// 选择链接对话框
function show_links_dlg(cb, param) {
    //if (g_dlg) g_dlg.close();
    if (param == null) {
        //param = "url";
    }
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择链接', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['800px', '420px']
            , content: '/admin/frame/decoration/link' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                var body = layer.getChildFrame('body', index);

            }
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                cb(iframe.contents().find('tr[data-back="false"]').find(".link").text(),
                    iframe.contents().find('tr[data-back="false"] td:eq(0)').text());
                //显示链接名称（稍后再用）
                // cb(iframe.contents().find('tr[data-back="false"]').find(".name").text());
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
}

//装修栏的显隐藏
$("#api-zero").click(function () {
    $(this).addClass('api-active');
    $('.collapse0').show();
    $('.collapse1').hide();
    $('#api-one').removeClass('api-active');
    $('.collapse2').hide();
    $('#api-two').removeClass('api-active');
});
$("#api-one").click(function () {
    $(this).addClass('api-active');
    $('.collapse0').hide();
    $('#api-zero').removeClass('api-active');
    $('.collapse1').show();
    $('.collapse2').hide();
    $('#api-two').removeClass('api-active');
});
$("#api-two").click(function () {
    $(this).addClass('api-active');
    $('.collapse0').hide();
    $('#api-zero').removeClass('api-active');
    $('.collapse1').hide();
    $('#api-one').removeClass('api-active');
    $('.collapse2').show();
});


function show_browser_page_dlg() {
    if (g_dlg) g_dlg.close();
    var page_id = $("#page_id").val();
    //TODO:预览暂时不需要
    g_dlg = art.dialog.open("../?c=template&page_id=" + page_id + "&preview=0&type=mobile", {
        title: "查看手机页面效果",
        width: 320,
        height: 480,
        padding: 0,
        margin: 0,
        lock: true,
        cancelVal: "关闭",
        cancel: function () {
        }
    });
}


// 初始化拖拽事件
function init_drag_event() {
    $('.drag').click(function () {
        var scm = save_cur_module();
        if (!scm) return false;
        hasSaved = false;
        var m = $(this).attr('module_name');
        manager.add_m(-1, m);
    });

    // 模块拖拽
    $('.drag').draggable({
        appendTo: ".panel-group",
        helper: "clone",
        start: function () {
            g_insert_index = -1;
        },
        drag: function (ev, ui) {
            highlignt_row_item($(ui.helper).offset());
        },
        stop: function () {
            $("#drag_area div.row_item").removeClass("placeholder");
        },
        zIndex: 100 //拖动位置在拖放区域上方
    });

    // 模块拖拽接收
    $("#drag_area_container").droppable({
        activeClass: "ui-state-default",
        hoverClass: "ui-state-hover",
        accept: ":not(.ui-sortable-helper)",
        drop: function (event, ui) {
            var scm = save_cur_module();
            if (!scm) return false;
            var m = $(ui.helper).attr('module_name');
            $(".drag_notice").css('display', 'none');
            manager.add_m(g_insert_index, m);
            // $("#drag_area div.row_item").removeClass("placeholder");
        }
    });
    //
    $("#drag_area").sortable({
        items: ".row_item:not(.not_allow_drag)",
        sort: function () {
            // gets added unintentionally by droppable interacting with sortable
            // using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
            $(this).removeClass("ui-state-default");
        },
        placeholder:"ui-state-highlight",
    });
    // if (is_card == 0) {
    //     $('.m_card').addClass('not_allow_drag');
    // }
    // if (is_coupon == 0) {
    //     $('.m_coupon').addClass('not_allow_drag');
    // }
    // if (is_bargain == 0) {
    //     $('.m_bargain').addClass('not_allow_drag');
    // }
    // if (is_video == 0) {
    //     $('.m_video').addClass('not_allow_drag');
    // }
    // if (is_integral == 0) {
    //     $('.m_integral').addClass('not_allow_drag');
    // }
    // if (is_seckill == 0) {
    //     $('.m_seckill').addClass('not_allow_drag');
    // }
    // if (is_group_draw == 0) {
    //     $('.m_group_draw').addClass('not_allow_drag');
    // }
    // if (m_pin_integration == 0) {
    //     $('.m_pin_integration').addClass('not_allow_drag');
    // }
    //
    // $('.m_service').addClass('not_allow_drag');
    $("#save_content").click(function () {
        var scm = save_cur_module();
        if (!scm) return false;
        event.stopPropagation();
        hasSaved = true;
        manager.save_all();
    });
    $("#save_draft_content").click(function (event) {
        var scm = save_cur_module();
        if (!scm) return false;
        event.stopPropagation();
        hasSaved = true;
        manager.save_all();
    });
    $("#save_preview_content").click(function (event) {
        var scm = save_cur_module();
        if (!scm) return false;
        event.stopPropagation();
        hasSaved = true;
        manager.save_all(2);
    });
    $("#recovery_content").click(function (event) {
        var scm = save_cur_module();
        if (!scm) return false;
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '页面草稿将置为线上已发布的页面' + '</div>', {
                title: ['页面草稿回退提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定回退', '取消回退']
            }, function (index) {
                event.stopPropagation();
                hasSaved = true;
                manager.save_all(3);
            });
        });

    });

    util.inputChange();
    util.selectChange();
    // window.onbeforeunload = function (event) {
    //     if (hasSaved == false) {
    //         return '确认要离开吗';
    //     }
    // };
    $(document).on("mouseover", "#drag_area div.row_item", function (e) {
        $(this).addClass("item_hover")
        if($(this).find('.no_use').css('display') != 'none' && $(this).find('.no_use').length > 0 ){
            $(this).find(".item_operation").hide();
            $(this).find(".item_module_title").hide();
        }else{
            $(this).find(".item_operation").show();
            $(this).find(".item_module_title").show();
        }
        window.event? window.event.cancelBubble = true : e.stopPropagation();
    })
    $(document).on("mouseleave", "#drag_area div.row_item", function (e) {
        $(this).removeClass("item_hover");
        $(this).find(".item_operation").hide();
        $(this).find(".item_module_title").hide();
        window.event? window.event.cancelBubble = true : e.stopPropagation();
    })

    $(document).on("click", "#drag_area div.row_item", function (e) {
        if (manager.page_cfg.is_ok != 1) {
            var res = manager.set_page_cfg();
            if (!res) return;
        }
        e.preventDefault();
        hasSaved = false;
        if ($(this).hasClass("item_selected")) return;
        var el = $("#drag_area div.item_selected");
        if (el.length > 0) {
            var scm = save_cur_module();
            if (!scm) return;
        }
        $("#drag_area div.row_item").removeClass("item_selected");
        $("#drag_area div.row_item .item_operation").hide();
        $("#drag_area div.row_item .item_module_title").hide();
        $(this).addClass("item_selected");
        manager.show_edit_module($(this));
    });

    $(document).on("click", "#drag_area div .del_img", function (e) {
        e.stopPropagation();
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (ind) {
                hasSaved = false;
                var p = _this.parents(".row_item");
                var idx = parseInt(p.attr('cur_idx'));
                var md_name = _this.parents(".row_item").attr("module_name");
                p.remove();
                manager.del_m(idx);
                if ($("#drag_area>div").length < 1) {
                    $(".drag_notice").css("display", "block");
                }
                var body_name =$(".data_item").attr("module_name");
                if( md_name == body_name){
                    $("#module_edit").hide();
                }
                var index = $.inArray(idx, manager.is_decorate_module);
                if (index > -1) {
                    manager.is_decorate_module.splice(index, 1);
                }
                e.preventDefault();
                layer.close(ind);
            });
        });
    });

    $(document).on("click", "#drag_area div.item_operation .up_img", function (e) {
        hasSaved = false;
        var p = $(this).parents(".row_item");
        var pre = p.prev();
        if (pre.length > 0) {
            pre.insertAfter(p);
        }
        e.preventDefault();
    });

    $(document).on("click", "#drag_area div.item_operation .down_img", function (e) {
        hasSaved = false;
        var p = $(this).parents(".row_item");
        var next = p.next();
        if (next.length > 0) {
            next.insertBefore(p);
        }
        e.preventDefault();
    });

    $(document).on("click", "#module_edit #ok", function (e) {
        hasSaved = false;
        var scm = save_cur_module();
        if (!scm) return false;
        util.mobile_alert('模块保存成功');
        // $("#drag_area div.row_item").removeClass("item_selected");
        // $("#module_edit").hide();
        // $(".west_street_img").css("border", "2px solid transparent");
        // cur_num = -1;
    });

    $("#browser_page").click(function (e) {
        show_browser_page_dlg();
    });

    $(document).on("click", ".change_show", function () {
        if ($("#module_setings").css("display") != 'block') {
            var scm = save_cur_module();
            if (!scm) return;
            manager.page_cfg.is_ok = 0;
            $("#drag_area div.row_item").removeClass("item_selected");
            $("#module_setings").css("display", 'block');
            $("#module_edit").hide();
            $(".change_show img").attr("src", '/image/admin/shop_deco/icon_up.png');
            $(".notices").html("收起");
        } else {
            var res = manager.set_page_cfg();
            if (!res) return;
            $("#module_setings").css("display", 'none');
            $(".change_show img").attr("src", '/image/admin/shop_deco/icon_down.png');
            $(".notices").html("展开");
        }
    });

    $(document).on("click", ".btn_savr_config", function () {
        var res = manager.set_page_cfg();
        if (!res) return;
        $("#module_setings").css("display", 'none');
        $(".change_show img").attr("src", '/image/admin/shop_deco/icon_down.png');
        $(".notices").html("展开");
    });

    function save_cur_module() {
        var m = manager.get_cur_edit_module();
        if (m && !m.no_edit) {
            var d = m.get_data();

            if ($.inArray(d.module_name, ['m_phone', 'm_map', 'm_service']) > -1) {
                var res = m.checkParam();
                if (res.error != 0) {
                    util.mobile_alert(res.msg);
                    return false;
                }
            }
            if (!d) return false;
            var el = manager.get_item_el(d.cur_idx);
            m.init_ev_el(el, d);	//	JS改变页面样式,已改变
            manager.save_m(d.cur_idx, d);
            // var cur_idx = $("#drag_area div.row_item.item_selected").attr('cur_idx');
            var index = $.inArray(parseInt(d.cur_idx), manager.is_decorate_module);
            if (index > -1) {
                manager.is_decorate_module.splice(index, 1);
            }
            // if (d.module_name == 'm_shop') {
            //     var param = d;
            //     param.op = 'save';
            //     util.ajax_json("?c=shop&m=shop_info", function (d) {
            //     }, param);
            // }
        }
        return true;
    }

    /**
     * 高亮拖拽时划过的模块项
     */
    function highlignt_row_item(pos) {
        var p = $("#drag_area").offset();
        var flag = false;
        if (pos.left > p.left && pos.top > p.top
            && pos.left < p.left + $("#drag_area").width()
            && pos.top < p.top + $("#drag_area").height()) {

            $("#drag_area div.row_item").each(function (idx,item) {
                p = $(this).offset();
                if (pos.left > p.left && pos.top > p.top
                    && pos.left < p.left + $(this).width()
                    && pos.top < p.top + $(this).height()
                ) {
                    $("#drag_area div.row_item").removeClass("placeholder");
                    $(this).addClass("placeholder");
                    g_insert_index = $(this).index();
                    flag = true;
                }
            });
        }
        if (!flag) g_insert_index = -1;
    }


};


// 模块数据管理

var manager = {
    cur_idx: 100,
    g_data_list: {},
    page_cfg: {},
    is_decorate_module: [],    //处理模块不装修直接保存的问题
    init_page_cfg: function () {
        var d = this.page_cfg;
        var el = $("#module_setings");
        if(d.page_name){
            $(".phone_page_title").text(d.page_name);
        }

        if (d.bg_types == 1) {
            el.find('input[name="bg_types"]').eq(1).attr('checked', 'checked');
            el.find(".page_bg_image").val(d.page_bg_image);
            el.find(".bg_image").attr('src', d.page_bg_image);
            $("#drag_area_container #drag_area").css('background-image','url('+d.page_bg_image+')');
            $("#drag_area_container #drag_area").css('background-repeat',"repeat-y")
            $("#drag_area_container #drag_area").css('background-size','100% auto')
        } else {
            el.find('input[name="bg_types"]').eq(0).attr('checked', 'checked');
            el.find('input[name="page_bg_color"]').val(d.page_bg_color);
            $("#drag_area_container #drag_area").css('background-color',d.page_bg_color)
        }
        if (d.has_bottom == 1) {
            el.find('input[name="has_bottom"]').eq(0).attr('checked', 'checked');
        } else {
            el.find('input[name="has_bottom"]').eq(1).attr('checked', 'checked');
        }

        // if (d.show_margin == 0) {
        //     el.find("#show").prop("checked", "checked");
        //     el.find("#margin_height").parent().parent().css("display", "block");
        //     if (d.margin_val) {
        //         el.find("#margin_height").val(d.margin_val);
        //         $("#drag_area .row_item").css("margin-bottom",d.margin_val+"px");
        //         $("#drag_area .m_goods").css("margin-bottom",'10px');
        //         $("#drag_area .m_bargain").css("margin-bottom",'10px');
        //         $("#drag_area .m_integral").css("margin-bottom",'10px');
        //         $("#drag_area .m_seckill").css("margin-bottom",'10px');
        //     } else {
        //         el.find("#margin_height").val('10');
        //         $("#drag_area .row_item").css("margin-bottom","10px");
        //         $("#drag_area .m_goods").css("margin-bottom",'10px');
        //         $("#drag_area .m_bargain").css("margin-bottom",'10px');
        //         $("#drag_area .m_integral").css("margin-bottom",'10px');
        //         $("#drag_area .m_seckill").css("margin-bottom",'10px');
        //     }
        // } else {
        //     el.find("#no_show").prop("checked", "checked");
        //     el.find("#margin_height").parent().parent().css("display", "none");
        //     $("#drag_area .row_item").css("margin-bottom","0px");
        //     $("#drag_area .m_goods").css("margin-bottom",'10px');
        //     $("#drag_area .m_bargain").css("margin-bottom",'10px');
        //     $("#drag_area .m_integral").css("margin-bottom",'10px');
        //     $("#drag_area .m_seckill").css("margin-bottom",'10px');
        // }
    },
    set_page_cfg: function () {
        var el = $("#module_setings");
        this.page_cfg.page_name = el.find("#page_name").val();
        this.page_cfg.bg_types = el.find('input[name="bg_types"]:checked').val() == "1" ? 1 : 0;
        this.page_cfg.has_bottom = el.find('input[name="has_bottom"]:checked').val() == "1" ? 1 : 0;
        this.page_cfg.page_bg_color = el.find('input[name="page_bg_color"]').val();
        this.page_cfg.page_bg_image = el.find(".page_bg_image").val();
        this.page_cfg.is_ok = 1;
        this.page_cfg.show_margin = el.find("input[name='show_margin']:checked").val();
        this.page_cfg.margin_val = el.find("#margin_height").val();
        // console.log(this.page_cfg)
        this.init_page_cfg();
        return true;
    },
    get_module: function (module_name) {
        var m;
        try {
            eval('m = ' + module_name + ';');
            return m;
        } catch (e) {
            util.mobile_alert("此模块暂未实现");
            console.debug(module_name + ' not found');
            return null;
        }
    },

    add_m: function (index, module_name, data) {
        var m = this.get_module(module_name);
        if (!m) return;

        if (m.check_can_add) {
            var res = m.check_can_add(data);
            if (res !== true) {
                util.mobile_alert(res);
                return;
            }
        }

        console.log(this.page_cfg)
        if (util.count(this.page_cfg) > 0 && this.page_cfg.is_ok != 1) {
            var res = this.set_page_cfg();
            if (!res) return;
        }
        var el = $("#template_list ." + module_name).clone();
        if (index == -1) {
            el.appendTo($("#drag_area"));
        }else {
            g_insert_index--; // 修正row_item的正确索引位置
            el.insertAfter($("#drag_area div.row_item:eq(" + g_insert_index + ")"));
        }
        if (data == undefined) {
            data = {};
            this.cur_idx++;
            el.attr('cur_idx', this.cur_idx);
            el.attr('module_name', module_name);
            data.cur_idx = this.cur_idx;
            data.module_name = module_name;
            this.g_data_list["c_" + this.cur_idx] = data;
            if ($.inArray(module_name, ['m_goods_search', 'm_dashed_line', 'm_blank','m_official_accounts']) == -1) {
                //注意：不需要装修的要排除
                this.is_decorate_module.push(data.cur_idx);
            }
        } else {
            el.attr('cur_idx', data.cur_idx);
            el.attr('module_name', module_name);
        }
        console.log(data)
        m.init_ev_el(el, data);
        $("#drag_area div.row_item").removeClass("item_selected");
        el.addClass("item_selected");
        el.find(".item_operation").hide();
        if (this.page_cfg.show_margin == 0) {
            if (this.page_cfg.margin_val) {
                $("#drag_area .row_item").css("margin-bottom",this.page_cfg.margin_val+"px");
                $("#drag_area .m_goods").css("margin-bottom",'10px');
                $("#drag_area .m_bargain").css("margin-bottom",'10px');
                $("#drag_area .m_integral").css("margin-bottom",'10px');
                $("#drag_area .m_seckill").css("margin-bottom",'10px');
            } else {
                $("#drag_area .row_item").css("margin-bottom","10px");
                $("#drag_area .m_goods").css("margin-bottom",'10px');
                $("#drag_area .m_bargain").css("margin-bottom",'10px');
                $("#drag_area .m_integral").css("margin-bottom",'10px');
                $("#drag_area .m_seckill").css("margin-bottom",'10px');
            }
        } else {
            $("#drag_area .row_item").css("margin-bottom","0px");
            $("#drag_area .m_goods").css("margin-bottom",'10px');
            $("#drag_area .m_bargain").css("margin-bottom",'10px');
            $("#drag_area .m_integral").css("margin-bottom",'10px');
            $("#drag_area .m_seckill").css("margin-bottom",'10px');
        }
        this.scroll_to_visible(el);
        if (!this.init_module)
            this.show_edit_module(el);

    },

    del_m: function (idx) {
        if (this.g_data_list["c_" + idx] != undefined) {
            delete this.g_data_list["c_" + idx];
        }
    },

    save_m: function (idx, data) {
        this.g_data_list["c_" + idx] = data;
    },

    get_m_data: function (idx) {
        return this.g_data_list["c_" + idx];
    },

    get_item_el: function (idx) {
        return $("#drag_area div.row_item[cur_idx=" + idx + "]");
    },
    scroll_to_visible: function (el_m) {
        var el_top = $(el_m).position().top;
        var el_bottom = $(el_m).outerHeight() + el_top;
        if (el_top > $("#drag_area").height() || el_bottom < 0) {
            $("#drag_area").scrollTop(el_top + $("#drag_area").scrollTop());
        }
    },
    show_edit_module: function (el) {
        var idx = parseInt(el.attr('cur_idx'));
        var module_name = el.attr('module_name');

        var m = this.get_module(module_name);
        if (!m) return;
        if (m.no_edit) {
            $("#module_edit").hide();
        } else {
            var data = this.g_data_list["c_" + idx];
            m.show_edit_el(data);
            $("#module_edit").show();
            $("#module_setings").css("display", 'none');
            $(".change_show img").attr("src", '/image/admin/shop_deco/icon_down.png');
            $(".notices").html("展开");
        }
        if (module_name == 'm_rich_text') {
            $(".u_editor").show();
        } else {
            $(".u_editor").hide();
        }
        if (module_name == 'm_text_image') {
            $(".u_editor_text").show();
        } else {
            $(".u_editor_text").hide();
        }

    },
    get_cur_edit_module: function () {
        var item = $("#drag_area div.row_item.item_selected");
        if (item.length == 0) return null;
        return this.get_module(item.attr('module_name'));
    },
    get_module_num: function (module_name) {
        var module_num = 0;
        for (var i in this.g_data_list) {
            if (this.g_data_list[i].module_name == module_name)
                module_num++;
        }
        return module_num;
    },

    save_all: function () {
        if ($("#page_name").val() == '') {
            art.dialog.tips("页面名称不能为空");
            return;
        }
        if($("#page_img").val() == ''){
            art.dialog.tips("模板封图不能为空");
            return;
        }
        var t = {};
        var i = 0;
        $("#drag_area div.row_item").each(function () {
            var cur_idx = parseInt($(this).attr('cur_idx'));
            t["c_" + cur_idx] = manager.g_data_list["c_" + cur_idx];
            if(t["c_" + cur_idx].module_name=='m_official_accounts'){
                i++;
            }
        });
        if (i > 1) {
            util.mobile_alert("引导公众号模块只能有一个");
            return;
        }
        t['page_cfg'] = this.page_cfg;
        this.g_data_list = t;
        var param = {page_id: $("#page_id").val(), page_content: $.toJSON(this.g_data_list), page_name: $("#page_name").val(), op: 'save_content',page_img:$("#page_img").val()};
        util.ajax_json('/system/decoration/update', function (d) {
            if (d && d.error == 0) {
                art.dialog.tips('保存成功');
                if($("#page_id").val()==''){
                    $("#page_id").val(d.content);
                }
            } else if (d && d.error > 0) {
                art.dialog.tips(d.message);
            }
        }, param);
    },
    init_page: function () {
        if ($("#page_tpl_type").val() == '1') {
            this.init_module = true;
            this.g_data_list = {};
            this.cur_idx = 100;
            this.add_m(-1, 'm_shop');
            this.add_m(-1, 'm_all_goods');
        } else {
            try {
                this.init_module = true;
                this.cur_idx = 100;
                this.g_data_list = $.parseJSON($("#page_content").val());
                if (($("#page_tpl_type").val() == '3') && util.count(this.g_data_list) == 0) {
//                    this.add_m(-1, 'm_shop');
                } else {
                    for (var i in this.g_data_list) {
                        var d = this.g_data_list[i];
                        if (i == 'page_cfg') {
                            this.page_cfg = $.isPlainObject(d) ? d : {};
                            continue;
                        }
                        if (d.module_name == 'm_shop') delete d.ok_ajax;
                        this.cur_idx = parseInt(d.cur_idx) > this.cur_idx ? parseInt(d.cur_idx) : this.cur_idx;
                        this.add_m(-1, d['module_name'], d);
                    }
                    this.cur_idx = parseInt(this.cur_idx) + 1;
                }
                //初始化页面控制模块
                this.init_page_cfg();
            } catch (e) {
                this.g_data_list = {};
                this.cur_idx = 100;
                if ($("#page_tpl_type").val() == '2') {
                    this.add_m(-1, 'm_girl');
                } else if ($("#page_tpl_type").val() == '3') {
//                    this.add_m(-1, 'm_shop');
                }
            }
        }
        this.init_module = false;
        //默认显示页面配置
        this.page_cfg.is_ok = 0;
        $("#drag_area div.row_item").removeClass("item_selected");
        $("#module_setings").css("display", 'block');
        $(".change_show img").attr("src", '/image/admin/shop_deco/icon_up.png');
        $(".notices").html("收起");
        // var t = $("#drag_area div.row_item:first");
        // if (t.length > 0) {
        //     $("#drag_area div.row_item").removeClass("item_selected");
        //     t.addClass("item_selected");
        //     manager.show_edit_module(t);
        // }
    },

    check_module_data: function (cur_data) {
        // console.log(cur_data)
        if (cur_data.module_name == 'm_goods') {
            if (cur_data.recommend_type == '1' && cur_data.goods_items.length == 0) {
                util.mobile_alert('请选择推荐商品');
                return false;
            }
        } else if (cur_data.module_name == 'm_scroll_image') {
            for (var i in cur_data.img_items) {
                if (!cur_data.img_items[i].img_url) {
                    util.mobile_alert('请上传轮播图片');
                    return false;
                }
            }
        } else if (cur_data.module_name == 'm_magic_cube') {
            if (util.count(cur_data.data) == 0) {
                util.mobile_alert('请选择橱窗模块');
                return false;
            }
            let td_number = 0;
            for (var i in cur_data.data) {
                if (!cur_data.data[i].img_url) {
                    util.mobile_alert('请上传图片');
                    return false;
                }
                td_number = td_number + (cur_data.data[i].cols * cur_data.data[i].rows);
            }
            if (td_number < ((cur_data.table_size.cols * cur_data.table_size.rows))) {
                util.mobile_alert('图片必须添加满');
                return false;
            }
        } else if (cur_data.module_name == 'm_single_image') {
            if (!cur_data.img_url) {
                util.mobile_alert('请上传图片');
                return false;
            }
        } else if (cur_data.module_name == 'm_double_image') {
            if (!cur_data.img_url1 || !cur_data.img_url2) {
                util.mobile_alert('请上传图片');
                return false;
            }
        } else if (cur_data.module_name == 'm_image_guide') {
            // console.log(typeof cur_data.img_url5);
            if (!cur_data.img_url1 || !cur_data.img_url2 || !cur_data.img_url3 || !cur_data.img_url4 || (cur_data.image_guide5 == 1 && !cur_data.img_url5)) {
                util.mobile_alert('请上传图片');
                return false;
            }
        } else if (cur_data.module_name == 'm_title') {
            if (!cur_data.title) {
                util.mobile_alert('请填写标题');
                return false;
            }
        } else if (cur_data.module_name == 'm_text') {
            if (!cur_data.title) {
                util.mobile_alert('请填写文本');
                return false;
            }
        } else if (cur_data.module_name == 'm_rich_text') {
            if (!cur_data.rich_text) {
                util.mobile_alert('请填写富文本内容');
                return false;
            }
        } else if (cur_data.module_name == 'm_image_small') {
            if (!cur_data.img_url) {
                util.mobile_alert('请上传图片');
                return false;
            }
        } else if (cur_data.module_name == 'm_multi_image') {
            for (var i in cur_data.img_items) {
                if (!cur_data.img_items[i].img_url) {
                    util.mobile_alert('请上传图片');
                    return false;
                }
            }
        } else if (cur_data.module_name == 'm_phone') {
            if (!cur_data.title) {
                util.mobile_alert('请填写电话号码');
                return false;
            }
        } else if (cur_data.module_name == 'm_video') {
            if (!cur_data.video_url) {
                util.mobile_alert('请上传视频');
                return false;
            }
        } else if (cur_data.module_name == 'm_card') {
            if (!cur_data.card_id) {
                util.mobile_alert('请选择会员卡');
                return false;
            }
        } else if (cur_data.module_name == 'm_coupon') {
            if (!cur_data.coupon_arr || cur_data.coupon_arr.length == 0) {
                util.mobile_alert('请选择优惠券');
                return false;
            }
        } else if (cur_data.module_name == 'm_integral') {
            if (!cur_data.integral_goods || cur_data.integral_goods.length == 0) {
                util.mobile_alert('请选择积分活动');
                return false;
            }
        } else if (cur_data.module_name == 'm_bargain'){
            if(!cur_data.bargain_goods || cur_data.bargain_goods.length == 0){
                util.mobile_alert('请选择砍价活动商品');
                return false;
            }
        } else if (cur_data.module_name == 'm_seckill'){
            if(!cur_data.seckill_goods || cur_data.seckill_goods.length == 0){
                util.mobile_alert('请选择秒杀活动商品');
                return false;
            }
        } else if (cur_data.module_name == 'm_pin_integration'){
            if(cur_data.act_id <= 0){
                util.mobile_alert('请选择瓜分积分活动');
                return;
            }
        } else if (cur_data.module_name == 'm_goods_group'){
            if(cur_data.sort_group_arr == ''){
                util.mobile_alert('请添加商品分组');
                return;
            }else{
                var sort_group_arr = cur_data.sort_group_arr;
                for(var i in sort_group_arr){
                    if(sort_group_arr[i].is_all !=1 && sort_group_arr[i].group_goods_id == ''){
                        util.mobile_alert('请为商品分组添加商品');
                        return;
                    }
                }
            }
        }else if(cur_data.module_name == 'm_service'){
            var str = cur_data.img_url;
            if(str != undefined && str.indexOf('customer_add.png') != -1){
                util.mobile_alert('请选择自定义客服图片');
                return false;
            }
        }
        console.log(cur_data);
        if ($.inArray(cur_data.cur_idx, manager.is_decorate_module) > -1) {
            util.mobile_alert('存在未装修的模板');
            return false;
        }
        return true;
    },

    change_show_module: function () {
        var cur_m = this.get_cur_edit_module();
        if (!cur_m) return false;
        var cur_data = cur_m.get_data();
        console.log('change_show_module');
        cur_m.init_ev_el(this.get_item_el(cur_data.cur_idx),cur_data);
        return true;
        // console.log('change_show_module');
    }
};


jQuery(function ($) {
    initKindEditor("#editor", function () {
        init_drag_event();
        manager.init_page();
    });
    // initKindEditor("#editor_text",function(){
    // },'','m_text_image');
});
