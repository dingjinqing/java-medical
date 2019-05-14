// 店铺装修js

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
            , area: ['800px','460px']
            , content: '/admin/frame/decoration/link' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                var body = layer.getChildFrame('body', index);

            }
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                cb(iframe.contents().find('tr[data-back="false"]').find(".link").text());
                //显示链接名称（稍后再用）
                // cb(iframe.contents().find('tr[data-back="false"]').find(".name").text());
                hasSaved = false;
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
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


// 初始化拖拽事件
function init_drag_event() {
    $('.drag').click(function () {
        var el = $("#drag_area div.item_selected");
        if (el.length > 0) {
            var ret = save_cur_module();
            if(!ret) return;
        }
        var m = $(this).attr('module_name');
        manager.add_m(-1, m);
        hasSaved = false;
    });

    // 模块拖拽
    $('.drag').draggable({
        appendTo: "parent",
        helper: "clone",
        start: function () {
            g_insert_index = -1;
        },
        drag: function (ev, ui) {
            highlignt_row_item($(ui.helper).offset());
        },
        stop: function () {
            $("#drag_area div.row_item").removeClass("item_bottom_active");
        },
        zIndex: 100 //拖动位置在拖放区域上方
    });

    // 模块拖拽接收
    $("#drag_area_container").droppable({
        activeClass: "ui-state-default",
        hoverClass: "ui-state-hover",
        accept: ":not(.ui-sortable-helper)",
        drop: function (event, ui) {
            var el = $("#drag_area div.item_selected");
            if (el.length > 0) {
                var ret = save_cur_module();
                if(!ret) return;
            }
            var m = $(ui.helper).attr('module_name');
            $(".drag_notice").css('display', 'none');
            manager.add_m(g_insert_index, m);
        }
    }).sortable({
        items: ".row_item:not(.not_allow_drag)",
        sort: function () {
            // gets added unintentionally by droppable interacting with sortable
            // using connectWithSortable fixes this, but doesn't allow you to customize active/hoverClass options
            $(this).removeClass("ui-state-default");
        }
    });

    $("#save_content").click(function (e) {
        e.stopPropagation();
        hasSaved = true;
        var save = save_form_cfg();
        if (!save) return;
        manager.save_all();
    });
    $("#publish_content").click(function (e) {
        e.stopPropagation();
        hasSaved = true;
        var save = save_form_cfg();
        if (!save) return;
        manager.save_all(1);
    });

    $(".btn_savr_config").click(function () {
        var save = save_form_cfg();
        if (!save) return;
        $("#module_setings").css("display",'none');
        $(".change_show img").attr("src",'/image/admin/shop_deco/icon_down.png');
        $(".notices").html("展开");
    });

    $(document).on("hover", "#drag_area div.row_item", function () {
        $(this).addClass("item_active");
    }, function () {
        $(this).removeClass("item_active");
    });

    $(document).on("click", "#drag_area div.row_item", function (e) {
        e.preventDefault();
        hasSaved = false;
        if ($(this).hasClass("item_selected")) return;
        var el = $("#drag_area div.item_selected");
        if (el.length > 0) {
            var ret = save_cur_module();
            if(!ret) return;
        }
        $("#drag_area div.row_item").removeClass("item_selected");
        $(this).addClass("item_selected");
        manager.show_edit_module($(this));
        $("#module_setings").css('display', 'none');
        if ($("#page_state").val() == '1') {
            $("#module_edit input").attr("disabled",true);
            $("#module_edit select").attr("disabled",true);
            $("#module_edit img").parent("div").css("pointer-events",'none');
            $(".ke-edit").css("pointer-events",'none');
        }
        $(".change_show img").attr("src",'/image/admin/shop_deco/icon_down.png');
        $(".notices").html("展开");
    });

    $(document).on("click", "#drag_area div.item_operation .del_img", function (e) {
        var p = $(this).parents(".row_item");
        var idx = parseInt(p.attr('cur_idx'));
        p.remove();
        hasSaved = false;
        manager.del_m(idx);
        if ($("#drag_area>div").length < 1) {
            $(".drag_notice").css("display", "block");
        }
        $("#module_edit").hide();
        $(".hide_style").show();
        e.preventDefault();
    });

    $(document).on("click", "#drag_area div.item_operation .up_img", function (e) {
        var p = $(this).parents(".row_item");
        var pre = p.prev();
        if (pre.length > 0) {
            pre.insertAfter(p);
        }
        hasSaved = false;
        e.preventDefault();
    });

    $(document).on("click", "#drag_area div.item_operation .down_img", function (e) {
        var p = $(this).parents(".row_item");
        var next = p.next();
        if (next.length > 0 && next.attr("class") != 'btn_tijiao') {
            next.insertBefore(p);
        }
        hasSaved = false;
        e.preventDefault();
    });

    $(document).on("click", "#module_edit #ok", function (e) {
        var ret = save_cur_module();
        if (!ret) return;
        // $("#drag_area div.row_item").removeClass("item_selected");
        // $("#module_edit").hide();
        // $(".west_street_img").css("border", "2px solid transparent");
        util.mobile_alert('模块保存成功');
        // cur_num = -1;
        hasSaved = false;
    });

    $(document).on("click",".change_show",function () {
        if($("#module_setings").css("display") != 'block'){
            var ret = save_cur_module();
            if(!ret) return;
            $("#drag_area div.row_item").removeClass("item_selected");
            $("#module_setings").css("display",'block');
            $("#module_edit").hide();
            $(".change_show img").attr("src",'/image/admin/shop_deco/icon_up.png');
            $(".notices").html("收起");
        }else{
            $("#module_setings").css("display",'none');
            $(".change_show img").attr("src",'/image/admin/shop_deco/icon_down.png');
            $(".notices").html("展开");
        }
    });

    $("#browser_page").click(function (e) {
        show_browser_page_dlg();
    });


    function save_cur_module() {

        var m = manager.get_cur_edit_module();
        if (m && !m.no_edit) {
            var d = m.get_data();
            for (var i in manager.g_data_list) {
                var d_i = manager.g_data_list[i];
                if (d.form_title != undefined && d_i.cur_idx != d.cur_idx && d_i.module_name == d.module_name && d_i.form_title == d.form_title) {
                    util.mobile_alert("同类型模块名称不能相同");
                    return false;
                }
            }
            var el = manager.get_item_el(d.cur_idx);
            m.init_ev_el(el, d);	//	JS改变页面样式,已改变
            manager.save_m(d.cur_idx, d);
        }
        return true;

    }

    function save_form_cfg() {
        var form_cfg = {};
        var form_o = $("#module_setings");
        form_cfg.page_name = form_o.find("input[name='page_name']").val();
        if (form_cfg.page_name == '') {
            util.mobile_alert('表单标题不能为空');
            return false;
        }
        form_cfg.is_forever_valid = form_o.find("input[name='expire_type']:checked").val();
        form_cfg.has_bottom = form_o.find("input[name='has_bottom']:checked").val();
        form_cfg.start_time = form_o.find("input[name='start_time']").val();
        form_cfg.end_time = form_o.find("input[name='end_time']").val();
        if (form_cfg.is_forever_valid == '0' && (form_cfg.start_time == '' || form_cfg.end_time == '')) {
            util.mobile_alert('表单有效期不为空');
            return false;
        }
        form_cfg.post_times = form_o.find("input[name='post_times']:checked").val();
        form_cfg.day_times = form_o.find("input[name='day_times']").val();
        form_cfg.total_times = form_o.find("input[name='total_times']").val();
        if (form_cfg.post_times == '0' && form_cfg.day_times > form_cfg.total_times) {
            util.mobile_alert('表单每日提交次数不能大于累计数量');
            return false;
        }
        form_cfg.get_times = form_o.find("input[name='get_times']").val();
        form_cfg.notice_name = form_o.find("input[name='notice_name']").val();
        form_cfg.font_color = form_o.find("input[name='font_color']").val();
        form_cfg.bg_color = form_o.find("input[name='bg_color']").val();
        form_cfg.bg_img = form_o.find("input[name='bg_img']").val();
        form_cfg.send_coupon = form_o.find(".send_coupon").prop('checked') ? 1 : 0;
        form_cfg.send_score = form_o.find(".send_score").prop('checked') ? 1 : 0;
        if (form_cfg.send_coupon == 1) {
            form_cfg.send_coupon_list = JSON.parse($(".setting_content .coupon_list_div").attr('coupon_list_json'))
        }
        // console.log(form_o.find(".send_coupon").prop('checked'));
        if (form_cfg.send_score == 1) {
            form_cfg.send_score_number = $(".setting_content .send_score_number").val();
            if (!(form_cfg.send_score_number > 0)) {
                util.mobile_alert('送积分大于0');
                return false;
            }
        }
        // console.log(form_cfg)
        manager.form_cfg = form_cfg;
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

            $("#drag_area div.row_item").each(function () {
                p = $(this).offset();
                if (pos.left > p.left && pos.top > p.top
                    && pos.left < p.left + $(this).width()
                    && pos.top < p.top + $(this).height()
                ) {
                    $("#drag_area div.row_item").removeClass("item_bottom_active");
                    $(this).addClass("item_bottom_active");
                    g_insert_index = $(this).index();
                    flag = true;
                }
            });
        }
        if (!flag) g_insert_index = -1;
    }

    $(".setting_content .form_add_coupon").click(function(){
        var couponList = JSON.parse($(".coupon_list_div").attr('coupon_list_json') ? $(".coupon_list_div").attr('coupon_list_json') : '{}') ;
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择优惠券', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['520px','420px']
                , content: '/admin/frame/coupon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success: function (layero, index) {
                    var iframe = layer.getChildFrame('body', index);
                    if(util.count(couponList) > 0){
                        for (var i in couponList) {
                            iframe.find('.coupon_list').each(function () {
                                if($(this).find('.coupon_info').attr('coupon_id') == couponList[i].coupon_id){
                                    $(this).addClass('card_list_active');
                                }
                            });
                        }
                    }
                    iframe.find('.coupon_list').click(function () {
                        if($(this).hasClass('card_list_active')){
                            $(this).removeClass('card_list_active');
                        }else{
                            $(this).addClass('card_list_active');
                        }
                    });
                }, yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var list_active = iframe.find('.card_list_active');
                    if($(list_active).size() == 0){
                        util.mobile_alert('请选择优惠券');
                        return;
                    }
                    var coupon_arr = [];
                    $(list_active).each(function () {
                        let coupon = {
                            act_code:$(this).find('.coupon_info').attr('act_code'),
                            coupon_id:$(this).find('.coupon_info').attr('coupon_id'),
                            use_score:$(this).find('.coupon_info').attr('use_score'),
                            score_number:$(this).find('.coupon_info').attr('score_number'),
                            denomination:$(this).find('.coupon_info').attr('denomination'),
                            consume_text:$(this).find('.coupon_center_limit').text().replace(/\s+/g,""),
                            receive_text:$(this).find('.coupon_center_number').text().replace(/\s+/g,""),
                        };
                        coupon_arr.push(coupon)
                    });
                    // if (util.count(couponList) > 0) {
                    //     coupon_arr = couponList.concat(coupon_arr);
                    // }
                    if(coupon_arr.length > 5){
                        util.mobile_alert('最多只能选择5张优惠券哦~');
                        return;
                    }
                    couponList = coupon_arr;
                    $('.coupon_list_div').attr('coupon_list_json',JSON.stringify(couponList));
                    set_coupon_list();
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    function set_coupon_list(){
        var couponList = JSON.parse($(".coupon_list_div").attr('coupon_list_json') ? $(".coupon_list_div").attr('coupon_list_json') : '{}') ;
        // console.log(couponList)
        $('.coupon_list_div').html('')
        for (var j in couponList) {
            var coupon_clone = $('.form_coupon_list_clone').find('.coupon_list').clone();
            coupon_clone.find('.coupon_del').attr('index',j);
            coupon_clone.find('.coupon_info').attr('act_code',couponList[j].act_code);
            coupon_clone.find('.coupon_info').attr('denomination',couponList[j].denomination);
            coupon_clone.find('.coupon_info').attr('coupon_id',couponList[j].coupon_id);
            coupon_clone.find('.coupon_info').attr('use_score',couponList[j].use_score);
            coupon_clone.find('.coupon_info').attr('score_number',couponList[j].score_number);
            if(couponList[j].act_code == "discount"){
                coupon_clone.find('.coupon_list_top').html('<span>' + couponList[j].denomination + '</span>折');
            }
            if(couponList[j].act_code == "voucher"){
                coupon_clone.find('.coupon_list_top').html('￥<span>' + couponList[j].denomination + '</span>');
            }
            coupon_clone.find('.coupon_center_limit').text(couponList[j].consume_text);
            coupon_clone.find('.coupon_center_number').text(couponList[j].receive_text);
            if (couponList[j].use_score == 1) {
                coupon_clone.find('.coupon_list_bottom').text(couponList[j].score_number+'积分 兑换');
            }
            $('.coupon_list_div').append(coupon_clone);
        }

        if(util.count(couponList) == 5){
            $(".form_add_coupon").hide();
        }else{
            $(".form_add_coupon").show();
        }
    }
    set_coupon_list();
    $(".setting_content .coupon_list_div").on('click','.coupon_del',function () {
        var coupon_arr = JSON.parse($(".setting_content .coupon_list_div").attr('coupon_list_json'));
        let index = parseInt($(this).attr('index'));
        // console.log(index)
        if(index >= 0){
            coupon_arr.splice(index,1);
            $(".setting_content .coupon_list_div").attr('coupon_list_json',JSON.stringify(coupon_arr));
            $(this).parent().remove();
            if(util.count(coupon_arr)<5){
                $('.form_add_coupon').show();
            }
            hasSaved = false;
        }
    });
};


// 模块数据管理

var manager = {
    cur_idx: 100,
    g_data_list: {},
    page_cfg: {},
    form_cfg: {},
    init_page_cfg: function () {
        var d = this.page_cfg;
    },
    get_page_cfg: function (k) {
        return this.page_cfg["page_cfg"][k];
    },
    set_page_cfg: function (k, v) {
        this.page_cfg[k] = v;
        this.init_page_cfg();
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
        var el = $("#template_list ." + module_name).clone();
        if (index == -1){
            var tjbuttom = $(".btn_tijiao").clone();
            $(".btn_tijiao").remove();
            el.appendTo($("#drag_area"));
            $("#drag_area").append(tjbuttom);
        } else {
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
        } else {
            el.attr('cur_idx', data.cur_idx);
            el.attr('module_name', module_name);
        }
        if (m.module_id) m.module_id ++ ;
        m.init_ev_el(el, data);
        $("#drag_area div.row_item").removeClass("item_selected");
        el.addClass("item_selected");
        this.scroll_to_visible(el);
        if (!this.init_module) {
            this.show_edit_module(el);
            $("#module_setings").css('display', 'none');
            $(".change_show img").attr("src",'/image/admin/shop_deco/icon_down.png');
            $(".notices").html("展开");
        }

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
        }
        if (module_name == 'm_rich_text') {
            $(".u_editor").show();
        } else {
            $(".u_editor").hide();
        }

    },
    get_cur_edit_module: function () {
        var item = $("#drag_area div.row_item.item_selected");
        if (item.length == 0) return null;
        return this.get_module(item.attr('module_name'));
    },

    save_all: function (state) {
        if ($("#page_name").val() == '') {
            util.mobile_alert("页面名称不能为空");
            return;
        }
        var t = {};
        var t_length = 0;
        var _this = this;
        var flag = true;
        var number = 0;
        $("#drag_area div.row_item").each(function () {
            var cur_idx = parseInt($(this).attr('cur_idx'));
            var c_cur_idx = "c_" + cur_idx;
            var cur = _this.g_data_list[c_cur_idx];
            for (var i in _this.g_data_list) {
                var d = _this.g_data_list[i];
                if (cur.form_title != undefined && i != c_cur_idx && cur.module_name == d.module_name && cur.form_title == d.form_title) {
                    util.mobile_alert("同类型模块名称不能相同");
                    flag = false;
                }
            }
            var cur_length = 0;
            for (var i in cur) {
                cur_length ++ ;
            }
            if (cur.module_name != 'm_dashed_line' && cur.module_name != 'm_official_accounts' && cur_length <= 2) number ++ ;
            t[c_cur_idx] = cur;
            t_length ++;
        });
        if(!flag) return;
        if(number > 0) {
            util.mobile_alert("请保存表单模块");
            return;
        }
        if (t_length == 0) {
            util.mobile_alert("请添加表单模块");
            return;
        }
        // t['page_cfg'] = this.page_cfg;
        // t['form_cfg'] = $.toJSON(manager.form_cfg); //TODO: 添加表单配置项
        var page_name = this.form_cfg.page_name; //TODO: 写入真实的开始时间
        var is_forever_valid = this.form_cfg.is_forever_valid; //TODO: 写入真实的开始时间
        var start_time = this.form_cfg.start_time; //TODO: 写入真实的开始时间
        var end_time = this.form_cfg.end_time; //TODO: 写入真实的结束时间
        this.g_data_list = t;
        var data = {
            config_name:'num_config',
            mod_name:'form_num'
        }
        var param = {
            page_id: $("#page_id").val(), page_content: $.toJSON(this.g_data_list), form_cfg: $.toJSON(manager.form_cfg),
            page_name: page_name, is_forever_valid: is_forever_valid, start_time: start_time, end_time: end_time,
            state: state ? 1 : 0, op: 'save_content', page_enabled: 1
        };
        util.ajax_json("/admin/version/judgment",function (d) {
            var self = d.content.self;
            if(id>0){
                if(self.num >= self.use || self.num<0){
                    util.ajax_json('/admin/market/form/update', function (d) {
                        if (d && d.error == 0) {
                            if (state == 1) {
                                util.mobile_alert('保存并发布成功');
                                location.href = '/admin/market/form/list';
                            } else {
                                util.mobile_alert('保存成功');
                                if ($("#page_id").val() == '') {
                                    $("#page_id").val(d.content);
                                }
                                location.href = '/admin/market/form/list';
                            }
                            hasSaved = true;
                        } else if (d && d.error > 0) {
                            util.mobile_alert(d.message);
                        }
                    }, param);
                }else {
                    util.systemNotice(2,'表单数量已达到'+self.num+'个','表单数量');
                }
            }else {
                if(self.num > self.use || self.num<0){
                    util.ajax_json('/admin/market/form/update', function (d) {
                        if (d && d.error == 0) {
                            if (state == 1) {
                                util.mobile_alert('保存并发布成功');
                                location.href = '/admin/market/form/list';
                            } else {
                                util.mobile_alert('保存成功');
                                if ($("#page_id").val() == '') {
                                    $("#page_id").val(d.content);
                                }
                                location.href = '/admin/market/form/list';
                            }
                            hasSaved = true;
                        } else if (d && d.error > 0) {
                            util.mobile_alert(d.message);
                        }
                    }, param);
                }else {
                    util.systemNotice(2,'表单数量已达到'+self.num+'个','表单数量');
                }
            }
        },data);
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
                this.form_cfg = $.parseJSON($("#form_cfg").val());
                console.log(this.form_cfg)
                // if (($("#page_tpl_type").val() == '3') && util.count(this.g_data_list) == 0) {
                if (util.count(this.g_data_list) == 0) {
//                    this.add_m(-1, 'm_shop');
                } else {
                    for (var i in this.g_data_list) {
                        var d = this.g_data_list[i];
                        if (i == 'page_cfg') {
                            this.page_cfg = $.isPlainObject(d) ? d : {};
                            this.init_page_cfg();
                            continue;
                        }
                        this.cur_idx = parseInt(d.cur_idx) > this.cur_idx ? parseInt(d.cur_idx) : this.cur_idx;
                        this.add_m(-1, d['module_name'], d);
                    }
                    this.cur_idx = parseInt(this.cur_idx) + 1;
                }
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
        $("#drag_area div.row_item").removeClass("item_selected");
        $("#module_setings").css("display",'block');
        $("#module_edit").hide();
        $(".change_show img").attr("src",'/image/admin/shop_deco/icon_up.png');
        $(".notices").html("收起");
        // var t = $("#drag_area div.row_item:first");
        // if (t.length > 0) {
        //     $("#drag_area div.row_item").removeClass("item_selected");
        //     t.addClass("item_selected");
        //     manager.show_edit_module(t);
        //     $("#module_setings").css('display', 'none');
        //     $(".change_show img").attr("src",'/image/admin/shop_deco/icon_down.png');
        //     $(".notices").html("展开");
        // }
    },
    change_show_module: function () {
        var cur_m = this.get_cur_edit_module();
        var cur_data = cur_m.get_data();
        cur_m.init_ev_el(this.get_item_el(cur_data.cur_idx),cur_data);
        console.log('change_show_module');
    }
};


jQuery(function ($) {
    initKindEditor("#editor", function () {
        init_drag_event();
        manager.init_page();
    });

    $("#page_no_margin").change(function () {
        var page_no_margin = $(this).prop("checked") ? "1" : "0";
        manager.set_page_cfg("page_no_margin", page_no_margin);
    });
    $(".module_body .del_sel_tr").click(function(){
        console.log($(".del_sel_tr"));
        var sel_id = $(this).attr('sel_id');
        hasSaved = false;
        console.log(sel_id)
        // el.find(".select_tr_" + sel_id).remove();
    });
});
