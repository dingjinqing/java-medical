
// 各个模块必须实现init_ev_el 和 show_edit_el函数
// init_ev_el 当添加模块的时候,初始化数据到元素中，中间的元素
// show_edit_el函数，当进行编辑的时候，初始数据到编辑元素中

// 店铺模块
var m_shop = {
	init_ev_el: function(el,data){
		if(data.ok_ajax){
			if(data.bg_url) el.find(".shop_bg img").attr('src',data.bg_url);
			if(data.logo_url) el.find(".shop_logo img").attr('src',data.logo_url);
			el.find(".shop_name").text(data.shop_name);
			el.find(".shop_desc").text(data.shop_notice);
		}else{
			// var param = {op:'get'};
			// util.ajax_json("?c=shop&m=shop_info",function(d){
			// 	d = d.content;
			// 	if(d.bg_url) el.find(".shop_bg img").attr('src',d.bg_url);
			// 	if(d.logo_url) el.find(".shop_logo img").attr('src',d.logo_url);
			// 	el.find(".shop_name").text(d.shop_name);
			// 	el.find(".shop_desc").text(d.shop_notice);
			//},param);
            if(data.bg_url) el.find(".shop_bg img").attr('src',data.bg_url);
            el.find(".shop_name").text(data.shop_name);
            el.find(".shop_desc").text(data.shop_notice);
		}
	},
	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
        if(d.bg_url) {
        	el.find(".shop_bg_img").attr('src',d.bg_url).show();
        	el.find('.del_img_icon').show();
        	el.find('#upload_bg_file').hide();
        }
        if(d.shop_name){
            el.find("#shop_name").val(d.shop_name);
		}
        el.find("#shop_notice").val(d.shop_notice);
		// var param = {op:'get'};
		// util.ajax_json("?c=shop&m=shop_info",function(d){
		// 	d = d.content;
		// 	if(d.bg_url) el.find(".shop_bg_img").attr('src',d.bg_url);
		// 	if(d.bg_url) el.find(".shop_logo_img").attr('src',d.logo_url);
		// 	if(d.bg_url) el.find(".shop_bg_img").attr('path',d.shop_bg_path);
		// 	if(d.bg_url) el.find(".shop_logo_img").attr('path',d.shop_avatar);
		// 	el.find("#shop_name").val(d.shop_name);
		// 	el.find("#shop_notice").val(d.shop_notice);
		//},param);

		el.find("#select_pic").change(function(){
            el.find(".shop_bg_img").css("display","block");
			el.find(".shop_bg_img").attr('src',$(this).find("option:selected").val());
			el.find(".shop_bg_img").attr('path',$(this).find("option:selected").attr('path'));
			el.find("#upload_bg_file").css("display","none");
			manager.change_show_module();
		});

		el.find("#upload_bg_file").click(function(){
			$.jImageManager({
				img_width:640,
				img_height:300,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .shop_bg_img").attr('src',path);
					$(".module_body .shop_bg_img").attr('path',img_arr[0].img_path);
                    hasSaved = false;
                    manager.change_show_module();
				}
			});
			if($(this).parent().find(".shop_bg_img").attr("src") != "undefined"){
                $(this).parent().find(".shop_bg_img").css("display","block");

                $(this).css("display","none");
			}


		});
        el.find(".shop_bg_img").mouseover(function () {
            $(this).parent().find(".del_img_icon").css("display","block");
            $(this).parent().find(".del_img_icon").click(function () {
				$(this).css("display",'none');
                $(this).parent().find(".shop_bg_img").css("display","none");
                $(this).parent().parent().find("#upload_bg_file").css("display","block");
                hasSaved = false;
            })
        });

		el.find("#upload_logo_file").click(function(){
			$.jImageManager({
				img_width:160,
				img_height:160,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .shop_logo_img").attr('src',path);
					$(".module_body .shop_logo_img").attr('path',img_arr[0].img_path);
                    hasSaved = false;
				}
			});
		});

		el.find("input").change(function () {
			manager.change_show_module();
        })
	},
	show_edit_el:function(data){
		var el = $("#template_list .d_m_shop").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.shop_name = el.find("#shop_name").val();
		d.shop_notice = el.find("#shop_notice").val();
		d.shop_avatar = el.find(".shop_logo_img").attr('path');
		d.shop_bg_path = el.find(".shop_bg_img").attr('path');
		d.logo_url = el.find(".shop_logo_img").attr('src');
		d.bg_url = el.find(".shop_bg_img").attr('src');
		d.ok_ajax = 1;
		return d;
	}

};
