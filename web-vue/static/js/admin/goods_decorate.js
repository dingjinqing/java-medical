$(document).on('mouseover','#drag_area',function (e) {
    e.preventDefault();
    if ($(this).hasClass("dd_select")) return;
    $(".detail-show-box").removeClass("dd_select");
    $(this).addClass("dd_select");
    // if($(this).prev().attr('class') == 'item_goods'){
    //     $(this).find(".down_img").show();
    // }else{
    //     $(this).find('.up_img').show();
    // }
})
$(document).on('mouseover','.detail-show-box',function (e) {
    e.preventDefault();
    if ($(this).hasClass("dd_select")) return;
    $("#drag_area").removeClass("dd_select");
    $(this).addClass("dd_select");
    // if($(this).prev().attr('class') == 'item_goods'){
    //     $(this).find(".down_img").show();
    // }else{
    //     $(this).find('.up_img').show();
    // }
});
$(document).on('mouseleave','#drag_area,.detail-show-box',function (e) {
    e.preventDefault();
    $(".detail-show-box").removeClass("dd_select");
    $("#drag_area").removeClass("dd_select");
    // $('.up_img').hide();
    // $('.down_img').hide();
});
$(document).on("click", "#position1,#position2", function (e) {
    if($("#position1").prop('checked')){
        $("#drag_area_container").append($('.detail-show-box'));
        $(this).prop('checked',true);
    }else if($("#position2").prop('checked')){
        $("#drag_area_container").append($('#drag_area'));
        $(this).prop('checked',true);
    }

});


var g_insert_index = -1;
var g_dlg = null;

function show_links_dlg2(cb, param) {
    return show_links_dlg(cb, param);
}



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
        if (d.page_bg_color) el.find('input[name="page_bg_color"]').val(d.page_bg_color);
        if (d.bg_types == 1) {
            el.find('input[name="bg_types"]').eq(1).attr('checked', 'checked');
        } else {
            el.find('input[name="bg_types"]').eq(0).attr('checked', 'checked');
        }
        if (d.has_bottom == 1) {
            el.find('input[name="has_bottom"]').eq(0).attr('checked', 'checked');
        } else {
            el.find('input[name="has_bottom"]').eq(1).attr('checked', 'checked');
        }
        if (d.page_bg_image) {
            el.find(".page_bg_image").val(d.page_bg_image);
            el.find(".bg_image").attr('src', d.page_bg_image);
        }
        if (d.show_margin == 0) {
            el.find("#show").prop("checked", "checked");
            el.find("#margin_height").parent().parent().css("display", "block");
            if (d.margin_val) {
                el.find("#margin_height").val(d.margin_val);
                $("#drag_area .row_item").css("margin-bottom",d.margin_val+"px");
                $("#drag_area .m_goods").css("margin-bottom",'10px');
                $("#drag_area .m_bargain").css("margin-bottom",'10px');
                $("#drag_area .m_integral").css("margin-bottom",'10px');
                $("#drag_area .m_seckill").css("margin-bottom",'10px');
            } else {
                el.find("#margin_height").val('10');
                $("#drag_area .row_item").css("margin-bottom","10px");
                $("#drag_area .m_goods").css("margin-bottom",'10px');
                $("#drag_area .m_bargain").css("margin-bottom",'10px');
                $("#drag_area .m_integral").css("margin-bottom",'10px');
                $("#drag_area .m_seckill").css("margin-bottom",'10px');
            }
        } else {
            el.find("#no_show").prop("checked", "checked");
            el.find("#margin_height").parent().parent().css("display", "none");
            $("#drag_area .row_item").css("margin-bottom","0px");
            $("#drag_area .m_goods").css("margin-bottom",'10px');
            $("#drag_area .m_bargain").css("margin-bottom",'10px');
            $("#drag_area .m_integral").css("margin-bottom",'10px');
            $("#drag_area .m_seckill").css("margin-bottom",'10px');
        }
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
        if (is_card == 0 && module_name == 'm_card') {
            $('.m_card ').removeClass('item_selected');
            util.systemNotice(1, '', '会员卡模块');
            $('#module_edit').hide();
        }
        if (is_coupon == 0 && module_name == 'm_coupon') {
            $('.m_coupon ').removeClass('item_selected');
            util.systemNotice(1, '', '优惠券模块');
            $('#module_edit').hide();
        }
        if (is_bargain == 0 && module_name == 'm_bargain') {
            $('.m_bargain ').removeClass('item_selected');
            util.systemNotice(1, '', '砍价模块');
            $('#module_edit').hide();
        }
        if (is_video == 0 && module_name == 'm_video') {
            $('.m_video ').removeClass('item_selected');
            util.systemNotice(1, '', '视频模块');
            $('#module_edit').hide();
        }
        if (is_integral == 0 && module_name == 'm_integral') {
            $('.m_integral ').removeClass('item_selected');
            util.systemNotice(1, '', '积分兑换');
            $('#module_edit').hide();
        }
        if (is_seckill == 0 && module_name == 'm_seckill') {
            $('.m_seckill ').removeClass('item_selected');
            util.systemNotice(1, '', '秒杀模块');
            $('#module_edit').hide();
        }
        if (is_group_draw == 0 && module_name == 'm_group_draw') {
            $('.m_group_draw ').removeClass('item_selected');
            util.systemNotice(1, '', '拼团抽奖模块');
            $('#module_edit').hide();
        }
        if (is_pin_integration == 0 && module_name == 'm_pin_integration'){
            $('.m_pin_integration ').removeClass('item_selected');
            util.systemNotice(1, '', '瓜分积分模块');
            $('#module_edit').hide();
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

    save_all: function (state) {
        if ($("#page_name").val() == '') {
            util.mobile_alert("页面名称不能为空");
            return;
        }
        /*if (manager.is_decorate_module.length > 0) {
            util.mobile_alert("存在未装修的模块");
            return;
        }*/
        var t = {};
        var flag = false;
        var i = 0;
        $("#drag_area div.row_item").each(function () {
            var cur_idx = parseInt($(this).attr('cur_idx'));

            t["c_" + cur_idx] = manager.g_data_list["c_" + cur_idx];
            if(t["c_" + cur_idx].module_name=='m_official_accounts'){
                i++;
            }
            if (!manager.check_module_data(t["c_" + cur_idx])) {
                $("#drag_area div.row_item").removeClass("item_selected");
                $(this).addClass("item_selected");
                manager.scroll_to_visible($(this));
                manager.show_edit_module($(this));
                flag = true;
                return false;
            }
        });
        if (i > 1) {
            util.mobile_alert("引导公众号模块只能有一个");
            return;
        }
        if (flag) return;
        var res = this.set_page_cfg();
        if (!res) return;
        t['page_cfg'] = this.page_cfg;
        this.g_data_list = t;
        var data = {
            config_name: 'num_config',
            mod_name: 'decorate_num'
        }
        var param = {
            page_id: $("#page_id").val(),
            page_content: $.toJSON(this.g_data_list),
            page_name: $("#page_name").val(),
            op: 'save_content',
            page_enabled: 1,
            page_state: state ? state : 0
        };
        util.ajax_json("/admin/version/judgment", function (d) {
            console.log(param)
            var self = d.content.self;
            if (page_id > 0) {
                if (self.num >= self.use || self.num < 0) {
                    util.ajax_json('/admin/manage/decorate/update', function (res) {
                        console.log(res)
                        if (res && res.error == 0) {
                            util.mobile_alert('保存成功');
                            if ($("#page_id").val() == '') {
                                $("#page_id").val(res.content);
                            }
                        } else if (res && res.error == 2) {
                            // util.mobile_alert(res.content);
                            layui.use('layer', function () { //独立版的layer无需执行这一句
                                var layer = layui.layer;
                                layer.open({
                                    type: 1
                                    , title: '手机扫码预览'
                                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                                    , id: 'preview1' //防止重复弹出
                                    , content: '<div style="padding: 20px 40px;"><img style="width:180px;height: 180px" src="' + res.content + '"></div>'
                                    , shade: 0 //不显示遮罩
                                });
                            })
                        } else {
                            util.mobile_alert(res.message);
                        }
                    }, param);
                } else {
                    util.systemNotice(2, '装修页面数量已达到' + self.num + '个', '装修页面数量');
                }
            } else {
                if (self.num > self.use || self.num < 0) {
                    util.ajax_json('/admin/manage/decorate/update', function (res) {
                        console.log(res)
                        if (res && res.error == 0) {
                            util.mobile_alert('保存成功');
                            if ($("#page_id").val() == '') {
                                $("#page_id").val(res.content);
                            }
                        } else if (res && res.error == 2) {
                            // util.mobile_alert(res.content.code_url);
                            layui.use('layer', function () { //独立版的layer无需执行这一句
                                var layer = layui.layer;
                                layer.open({
                                    type: 1
                                    , title: '手机扫码预览'
                                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                                    , id: 'preview2' //防止重复弹出
                                    , content: '<div style="padding: 20px 40px;"><img style="width:180px;height: 180px" src="' + res.content + '"></div>'
                                    , shade: 0 //不显示遮罩
                                });
                            })
                        } else {
                            util.mobile_alert(res.message);
                        }
                    }, param);
                } else {
                    util.systemNotice(2, '装修页面数量已达到' + self.num + '个', '装修页面数量');
                }
            }
        }, data);

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
    },


    change_show_module: function () {
        var cur_m = this.get_cur_edit_module();
        var cur_data = cur_m.get_data();
        cur_m.init_ev_el(this.get_item_el(cur_data.cur_idx),cur_data);
        console.log('change_show_module');
    },
    getColors:function (str, opacity) {
        var sColor = str.toLowerCase();
        if (sColor) {
            if (sColor.length === 4) {
                var sColorNew = "#";
                for (var i = 1; i < 4; i += 1) {
                    sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1));
                }
                sColor = sColorNew;
            }
            //处理六位的颜色值
            var sColorChange = [];
            for (var i = 1; i < 7; i += 2) {
                sColorChange.push(parseInt("0x" + sColor.slice(i, i + 2)));
            }
            return "rgba(" + sColorChange.join(",") + "," + opacity + ")";
        } else {
            return sColor;
        }
    }
};


jQuery(function ($) {
    initKindEditor("#editor", function () {
        // init_drag_event();
        manager.init_page();
        the_color = manager.getColors(set_colors[0],1);
        linear_color = manager.getColors(set_colors[0],0.8);
        var coupon_color = manager.getColors(set_colors[0],0.4);
        console.log(the_color)
        $(".new_class").css("color",set_colors[0]);
        $(".border_class").css("border-color",set_colors[0]);
        $(".back_color").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
        $(".new_back").css("background-color",set_colors[0]);
        $(".coupon_color").css("color",coupon_color);
        $(".coupon_border").css("border-color",coupon_color);
    });

});

