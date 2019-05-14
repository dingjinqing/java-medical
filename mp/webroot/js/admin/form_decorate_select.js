// 各个模块必须实现init_ev_el 和 show_edit_el函数
// init_ev_el 当添加模块的时候,初始化数据到元素中，中间的元素
// show_edit_el函数，当进行编辑的时候，初始数据到编辑元素中

//性别
var m_sex = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".sex_title").text(data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".sex_title").text('性别' + num);
            }
        }
        if(data.show_types == 1) {
            el.find(".col_style").css('display','none');
            el.find(".row_style").css('display','block');
        } else {
            el.find(".col_style").css('display','block');
            el.find(".row_style").css('display','none');
        }
        if(data.confirm == 1) {
            el.find(".module-star").css('display','inline-block');
        }else {
            el.find(".module-star").css('display', 'none');
        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.form_title) {
            el.find("#form_title").val(d.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find("#form_title").val('性别' + num);
            }
        }
        if(d.show_types == 1) {
            el.find("input[name='sex_show_types'][value='1']").attr('checked', true);
        } else {
            el.find("input[name='sex_show_types'][value='0']").attr('checked', true);
        }
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');

        var item_el = manager.get_item_el(d.cur_idx);
        el.find("input[name='sex_show_types']").click(function () {
            hasSaved = false;
            if ($(this).val() == '1') {
                item_el.find(".col_style").css('display','none');
                item_el.find(".row_style").css('display','block');
            } else {
                item_el.find(".col_style").css('display','block');
                item_el.find(".row_style").css('display','none');
            }
        })
        el.find("#confirm").click(function () {
            if(el.find("#confirm:checked").val()){
                item_el.find(".module-star").css('display','inline-block');
            }else{
                item_el.find(".module-star").css('display', 'none');
            }
        })
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_sex").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.form_title = el.find("#form_title").val();
        d.show_types = el.find("input[name='sex_show_types']:checked").val() == '1' ? 1 : 0;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};

//下拉模块
var m_slide = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".slide_names").text(data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".slide_names").text('下拉' + num);
            }
        }
        if(data.confirm == 1) {
            el.find(".module-star").css('display','inline-block');
        }else {
            el.find(".module-star").css('display', 'none');
        }
    },
    add_select_tr: function (el,value) {
        if(value == undefined) value = '';
        var obj = el.find(".copy_this").clone();
        obj.removeClass('copy_this');
        obj.removeAttr('style');
        var id = el.find(".select_row").length;
        obj.addClass('select_tr_' + id);
        obj.find(".first_option input").attr('sel_id', id);
        obj.find(".first_option input").attr('name', 'selects');
        obj.find(".first_option a").attr('sel_id', id);
        if (value) {
            obj.find(".first_option input").val(value);
        } else {
            obj.find(".first_option input").val('选项' + id);
        }
        el.find(".copy_this").before(obj);
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.form_title) {
            el.find("#form_title").val(d.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find("#form_title").val('下拉' + num);
            }
        }
        if (d.selects) {
            for (var i in d.selects) {
                if(i == 1){
                    el.find(".select_tr_1").find(".first_option input").val(d.selects[1]);
                } else {
                    this.add_select_tr(el, d.selects[i]);
                }
            }
        } else {
            this.add_select_tr(el);
        }
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');
        var _this = this;
        var item_el = manager.get_item_el(d.cur_idx);
        el.find(".add_opn").click(function(){
            hasSaved = false;
            _this.add_select_tr(el);
            _this.show_edit_el(_this.get_data())
        });
        el.find(".del_sel_tr").click(function(){
            hasSaved = false;
            var sel_id = $(this).attr('sel_id');
            el.find(".select_tr_" + sel_id).remove();
            _this.show_edit_el(_this.get_data())
        });
        el.find("#confirm").click(function () {
            if(el.find("#confirm:checked").val()){
                item_el.find(".module-star").css('display','inline-block');
            }else{
                item_el.find(".module-star").css('display', 'none');
            }
        })
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_slide").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.form_title = el.find("#form_title").val();
        var selects = {};
        el.find("input[name='selects']").each(function () {
            selects[$(this).attr("sel_id")] = $(this).val();
        });
        d.selects = selects;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};

//选项模块
var m_choose = {
    module_id: 1,
    add_ev_sel_row: function (el, type, value) {
        var obj = el.find("."+ type +" .copy_row").clone();
        obj.removeClass('copy_row');
        obj.addClass('add_row');
        obj.removeAttr('style');
        obj.find(".row_value").text(value);
        el.find("."+ type +" .copy_row").before(obj);
    },
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".choose_name").text(data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".choose_name").text('选项' + num);
            }
        }
        el.find(".add_row").remove();
        if(data.show_types == 1) {
            el.find(".radio_style").css('display','none');
            el.find(".check_style").css('display','block');
            if (data.selects) {
                for (var i in data.selects) {
                    if (i == 1) {
                        el.find(".check_1 .row_value").text(data.selects[1])
                    } else {
                        this.add_ev_sel_row(el, 'check_style', data.selects[i]);
                    }
                }
            } else {
                this.add_ev_sel_row(el, 'check_style', '选项2');
            }
        } else {
            el.find(".radio_style").css('display','block');
            el.find(".check_style").css('display','none');
            if (data.selects) {
                for (var i in data.selects) {
                    if (i == 1) {
                        el.find(".radio_1 .row_value").text(data.selects[1])
                    } else {
                        this.add_ev_sel_row(el, 'radio_style', data.selects[i]);
                    }
                }
            } else {
                this.add_ev_sel_row(el, 'radio_style', '选项2');
            }
        }
        if(data.confirm == 1) {
            el.find(".module-star").css('display','inline-block');
        }else {
            el.find(".module-star").css('display', 'none');
        }
    },
    add_select_tr: function (el,value) {
        var obj = el.find(".copy_this").clone();
        obj.removeClass('copy_this');
        obj.removeAttr('style');
        var id = el.find(".select_row").length;
        if(value == undefined) value = '选项' + id;
        obj.addClass('select_tr_' + id);
        obj.find(".first_option input").attr('sel_id', id);
        obj.find(".first_option input").attr('name', 'selects');
        obj.find(".first_option a").attr('sel_id', id);
        obj.find(".first_option input").val(value);
        el.find(".copy_this").before(obj);
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.form_title) {
            el.find("#form_title").val(d.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find("#form_title").val('选项' + num);
            }
        }
        if(d.show_types == 1) {
            el.find("input[name='choose_show_types'][value='1']").attr('checked', true);
        } else {
            el.find("input[name='choose_show_types'][value='0']").attr('checked', true);
        }
        if (d.selects) {
            for (var i in d.selects) {
                if(i == 1){
                    el.find(".select_tr_1").find(".first_option input").val(d.selects[1]);
                } else {
                    this.add_select_tr(el, d.selects[i]);
                }
            }
        } else {
            this.add_select_tr(el);
        }
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');
        var _this = this;
        var item_el = manager.get_item_el(d.cur_idx);
        el.find(".add_opn").click(function(){
            hasSaved = false;
            _this.add_select_tr(el);
            _this.init_ev_el(item_el,_this.get_data());
            _this.show_edit_el(_this.get_data())
        });
        el.find(".del_sel_tr").click(function(){
            hasSaved = false;
            var sel_id = $(this).attr('sel_id');
            el.find(".select_tr_" + sel_id).remove();
            _this.init_ev_el(item_el,_this.get_data());
            _this.show_edit_el(_this.get_data())
        });
        el.find("input[name='choose_show_types']").click(function () {
            hasSaved = false;
            _this.init_ev_el(item_el,_this.get_data())
        })
        el.find("#confirm").click(function () {
            if(el.find("#confirm:checked").val()){
                item_el.find(".module-star").css('display','inline-block');
            }else{
                item_el.find(".module-star").css('display', 'none');
            }
        })
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_choose").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.form_title = el.find("#form_title").val();
        d.show_types = el.find("input[name='choose_show_types']:checked").val() == '1' ? 1 : 0;
        var selects = {};
        el.find("input[name='selects']").each(function () {
            selects[$(this).attr("sel_id")] = $(this).val();
        });
        d.selects = selects;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};


//日期模块
var m_dates = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".dates_names").text(data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".dates_names").text('日期' + num);
            }
        }
        if(data.confirm == 1) {
            el.find(".module-star").css('display','inline-block');
        }else {
            el.find(".module-star").css('display', 'none');
        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.form_title) {
            el.find("#form_title").val(d.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find("#form_title").val('日期' + num);
            }
        }
        if(d.date_types == 1) {
            el.find("input[name='date_types'][value='1']").attr('checked', true);
        } else {
            el.find("input[name='date_types'][value='0']").attr('checked', true);
        }
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');
        var item_el = manager.get_item_el(d.cur_idx);
        el.find("#confirm").click(function () {
            if(el.find("#confirm:checked").val()){
                item_el.find(".module-star").css('display','inline-block');
            }else{
                item_el.find(".module-star").css('display', 'none');
            }
        })
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_dates").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.form_title = el.find("#form_title").val();
        d.date_types = el.find("input[name='date_types']:checked").val() == '1' ? 1 : 0;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};


//图片模块
var m_imgs = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".mod_title").text(data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".mod_title").text('图片上传' + num);
            }
        }
        if(data.confirm == 1) {
            el.find(".module-star").css('display','inline-block');
        }else {
            el.find(".module-star").css('display', 'none');
        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.form_title) {
            el.find("#form_title").val(d.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find("#form_title").val('图片上传' + num);
            }
        }
        el.find("select[name='max_number']").find("option:contains("+ d.max_number +")").attr("selected",true);
        if(d.size_types == 1) {
            el.find("input[name='size_types'][value='1']").attr('checked', true);
        } else {
            el.find("input[name='size_types'][value='0']").attr('checked', true);
            el.find(".image_size").css('display', 'none');
        }
        el.find("input[name='width_size']").val(d.width_size );
        el.find("input[name='height_size']").val(d.height_size);
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');

        el.find("input[name='size_types']").click(function () {
            hasSaved = false;
            if ($(this).val() == '1') {
                el.find(".image_size").css('display', 'table-row');
            } else {
                el.find(".image_size").css('display', 'none');
            }
        })
        var item_el = manager.get_item_el(d.cur_idx);
        el.find("#confirm").click(function () {
            if(el.find("#confirm:checked").val()){
                item_el.find(".module-star").css('display','inline-block');
            }else{
                item_el.find(".module-star").css('display', 'none');
            }
        })
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_imgs").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.form_title = el.find("#form_title").val();
        d.max_number = el.find("select[name='max_number']").find("option:selected").val();
        d.size_types = el.find("input[name='size_types']:checked").val() == '1' ? 1 : 0;
        d.width_size = el.find("input[name='width_size']").val();
        d.height_size = el.find("input[name='height_size']").val();
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};