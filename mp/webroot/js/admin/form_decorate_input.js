// 各个模块必须实现init_ev_el 和 show_edit_el函数
// init_ev_el 当添加模块的时候,初始化数据到元素中，中间的元素
// show_edit_el函数，当进行编辑的时候，初始数据到编辑元素中

//姓名
var m_input_name = {
    module_id: 1,
    init_ev_el: function(el,data){
        // if(data.form_title) el.find(".name_title").text(data.form_title);
        if(data.form_title) {
            el.find(".name_title").text(data.form_title);
            el.find(".name_title_place").attr('placeholder','请输入'+ data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id-1;
                el.find(".name_title").text('姓名' + num);
                el.find(".name_title_place").attr('placeholder','请输入姓名' + num);
            }
        }
        if(data.image_type == 1) {
            el.find(".image").css('display', 'inline-block');
            el.find(".image").attr('src',data.name_url);
        } else {
            el.find(".image").css('display', 'none');
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
                var num = this.module_id-1;
                el.find("#form_title").val('姓名' + num);
            }
        }
        if(d.image_type == 1) {
            el.find("input[name='name_image_type'][value='1']").attr('checked', true);
        } else {
            el.find("input[name='name_image_type'][value='0']").attr('checked', true);
            el.find(".tr_image").css('display', 'none');
        };
        el.find("#name_image").attr('src', d.name_url);
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');
        var _this = this;
        var item_el = manager.get_item_el(d.cur_idx);
        el.find(".click_to_change").click(function () {
            $.jImageManager({
                img_width:36,
                img_height:36,
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    el.find("#name_image").attr('src',path);
                    item_el.find(".image").attr('src',path);
                    hasSaved = false;
                    _this.show_edit_el(_this.get_data());
                }
            });
        });
        el.find("input[name='name_image_type']").click(function () {
            hasSaved = false;
            if ($(this).val() == '1') {
                el.find(".tr_image").css('display', 'table-row');
                item_el.find(".image").css('display', 'inline-block');
                item_el.find(".image").attr('src',d.name_url);
            } else {
                el.find(".tr_image").css('display', 'none');
                item_el.find(".image").css('display', 'none');
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
        var el = $("#template_list .d_m_input_name").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.name_url = el.find("#name_image").attr('src');
        d.form_title = el.find("#form_title").val();
        d.image_type = el.find("input[name='name_image_type']:checked").val() == '1' ? 1 : 0;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};

//手机号
var m_input_mobile = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".mobile_title").text(data.form_title);
            el.find(".mobile_title_place").attr('placeholder','请输入' + data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".mobile_title").text('手机号' + num);
                el.find(".mobile_title_place").attr('placeholder','请输入手机号' + num);
            }
        }
        if(data.image_type == 1) {
            el.find(".image").css('display', 'inline-block');
            el.find(".image").attr('src', data.mobile_url);
        } else {
            el.find(".image").css('display', 'none');
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
                el.find("#form_title").val('手机号' + num);
            }
        }
        if(d.image_type == 1) {
            el.find("input[name='mobile_image_type'][value='1']").attr('checked', true)
        } else {
            el.find("input[name='mobile_image_type'][value='0']").attr('checked', true)
            el.find(".tr_image").css('display', 'none');
        }
        el.find("#mobile_image").attr('src', d.mobile_url);
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');
        var _this = this;
        var item_el = manager.get_item_el(d.cur_idx);
        el.find(".click_to_change").click(function () {
            $.jImageManager({
                img_width:36,
                img_height:36,
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    el.find("#mobile_image").attr('src',path);
                    item_el.find(".image").attr('src',path);
                    hasSaved = false;
                    _this.show_edit_el(_this.get_data());
                }
            });
        });
        el.find("input[name='mobile_image_type']").click(function () {
            hasSaved = false;
            if ($(this).val() == '1') {
                el.find(".tr_image").css('display', 'table-row');
                item_el.find(".image").css('display', 'inline-block');
                item_el.find(".image").attr('src',d.mobile_url);
            } else {
                el.find(".tr_image").css('display', 'none');
                item_el.find(".image").css('display', 'none');
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
        var el = $("#template_list .d_m_input_mobile").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.mobile_url = el.find("#mobile_image").attr('src');
        d.form_title = el.find("#form_title").val();
        d.image_type = el.find("input[name='mobile_image_type']:checked").val() == '1' ? 1 : 0;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};

//邮箱
var m_input_email = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".email_title").text(data.form_title);
            el.find(".email_title_place").attr('placeholder','请输入' + data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".email_title").text('邮箱' + num);
                el.find(".email_title_place").attr('placeholder','请输入邮箱' + num);
            }
        }
        if(data.image_type == 1) {
            el.find(".image").css('display', 'inline-block');
            el.find(".image").attr('src', data.email_url);
        } else {
            el.find(".image").css('display', 'none');
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
                el.find("#form_title").val('邮箱' + num);
            }
        }
        if(d.image_type == 1) {
            el.find("input[name='email_image_type'][value='1']").attr('checked', true);
        } else {
            el.find("input[name='email_image_type'][value='0']").attr('checked', true);
            el.find(".tr_image").css('display', 'none');
        }
        el.find("#email_image").attr('src', d.email_url);
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');
        var _this = this;
        var item_el = manager.get_item_el(d.cur_idx);
        el.find(".click_to_change").click(function () {
            $.jImageManager({
                img_width:36,
                img_height:36,
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    el.find("#email_image").attr('src',path);
                    item_el.find(".image").attr('src',path);
                    hasSaved = false;
                    _this.show_edit_el(_this.get_data());
                }
            });
        });
        el.find("input[name='email_image_type']").click(function () {
            hasSaved = false;
            if ($(this).val() == '1') {
                el.find(".tr_image").css('display', 'table-row');
                item_el.find(".image").css('display', 'inline-block');
                item_el.find(".image").attr('src',d.mobile_url);
            } else {
                el.find(".tr_image").css('display', 'none');
                item_el.find(".image").css('display', 'none');
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
        var el = $("#template_list .d_m_input_email").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.email_url = el.find("#email_image").attr('src');
        d.form_title = el.find("#form_title").val();
        d.image_type = el.find("input[name='email_image_type']:checked").val() == '1' ? 1 : 0;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};

//输入框
var m_input_text = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".email_title").text(data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".email_title").text('输入框' + num);
            }
        }
        if(data.placeholder) el.find(".input_box").attr('placeholder',data.placeholder);
        if(data.show_types == '2') {
            el.find(".duohangheng").css('display','none');
            el.find(".hengxiang").css('display','none');
            el.find(".zongxaing").css('display','block');
        } else if (data.show_types == '1') {
            el.find(".duohangheng").css('display','none');
            el.find(".hengxiang").css('display','block');
            el.find(".zongxaing").css('display','none');
        } else {
            el.find(".duohangheng").css('display','block');
            el.find(".hengxiang").css('display','none');
            el.find(".zongxaing").css('display','none');
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
                el.find("#form_title").val('输入框' + num);
            }
        }
        if(d.show_types) {
            el.find("input[name='show_types'][value=" + d.show_types + "]").attr('checked', true);
        } else {
            el.find("input[name='show_types'][value='0']").attr('checked', true);
        }
        el.find("#placeholder").val(d.placeholder);
        el.find("#least_number").val(d.least_number);
        el.find("#most_number").val(d.most_number);
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');

        var item_el = manager.get_item_el(d.cur_idx);
        el.find("input[name='show_types']").click(function(){
            hasSaved = false;
            if($(this).val() == '2') {
                item_el.find(".duohangheng").css('display','none');
                item_el.find(".hengxiang").css('display','none');
                item_el.find(".zongxaing").css('display','block');
            } else if ($(this).val() == '1') {
                item_el.find(".duohangheng").css('display','none');
                item_el.find(".hengxiang").css('display','block');
                item_el.find(".zongxaing").css('display','none');
            } else {
                item_el.find(".duohangheng").css('display','block');
                item_el.find(".hengxiang").css('display','none');
                item_el.find(".zongxaing").css('display','none');
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
        var el = $("#template_list .d_m_input_text").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.show_types = el.find("input[name='show_types']:checked").val()
            ? el.find("input[name='show_types']:checked").val() : 0;
        d.form_title = el.find("#form_title").val();
        d.placeholder = el.find("#placeholder").val();
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.least_number = el.find("#least_number").val() ? el.find("#least_number").val() : 1;
        d.most_number = el.find("#most_number").val() ? el.find("#most_number").val() : 500;
        d.ok_ajax = 1;
        return d;
    }
};

//省市区模块
var m_address = {
    module_id: 1,
    init_ev_el: function(el,data){
        if(data.form_title) {
            el.find(".address_names").text(data.form_title);
        } else {
            if (this.module_id > 2) {
                var num = this.module_id - 1;
                el.find(".address_names").text('省/市/区' + num);
            }
        }
        if(data.with_detail == 1) {
            el.find(".area_detail").css('display', 'block')
        } else {
            el.find(".area_detail").css('display', 'none')
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
                el.find("#form_title").val('省/市/区' + num);
            }
        }
        if(d.with_detail == 1) {
            el.find("input[name='with_detail'][value='1']").attr('checked', true);
        } else {
            el.find("input[name='with_detail'][value='0']").attr('checked', true)
        }
        if(d.confirm == 1) el.find("#confirm").attr('checked','true');

        var item_el = manager.get_item_el(d.cur_idx);
        el.find("input[name='with_detail']").click(function () {
            hasSaved = false;
            if ($(this).val() == '1') {
                item_el.find(".area_detail").css('display', 'block')
            } else {
                item_el.find(".area_detail").css('display', 'none')
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
        var el = $("#template_list .d_m_address").clone();
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
        d.with_detail = el.find("input[name='with_detail']:checked").val() == '1' ? 1 : 0;
        d.confirm = el.find("#confirm:checked").val() ? 1 : 0;
        d.ok_ajax = 1;
        return d;
    }
};