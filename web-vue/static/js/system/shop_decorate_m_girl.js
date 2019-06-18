var m_girl = {
	init_ev_el: function(el,d){
		if(!d.bg_img) d.bg_img = 'image/shop_beautify/girl_bg.png';
		el.find(".tpl_img").attr('src',d.bg_img);	
		el.find(".nav_list").html("");
		if(d.nav_list){
			for(var i in d.nav_list){
				var t  = d.nav_list[i];
				this.add_left_nav(el,t);								
			}
		}
	},
	add_left_nav: function(el,d){
		//if(d.nav_bg_img.indexOf("http://") == -1) d.nav_bg_img = root_url+"admin/" + d.nav_bg_img;
		var el_nav = $("#template_list .nav_item_tpl").clone();
		if(d.nav_logo_img) el_nav.find(".logo_img").attr('src',d.nav_logo_img);
		el_nav.find(".bg_img").attr('src',d.nav_bg_img);
		el_nav.find(".nav_item_title").text(d.nav_title);
		el_nav.find(".nav_item_sumary").text(d.nav_sumary);
		el_nav.appendTo(el.find(".nav_list"));
	},
    get_def_url: function(nav_def){
        var idx = parseInt(nav_def);
        switch (idx){
            case 2: return front_root_url + "index.php?c=cart";
            case 3: return front_root_url + "index.php?c=orderlist" ;
            case 1: return front_root_url +  "index.php?c=index&all=true";
            default : return "";
        }
    },
	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);

		el.find(".bg_img").attr('src',d.bg_img);
		el.find("#bg_link").val(d.bg_link);
		for(var i in d.nav_list){
			m_girl.add_nav_data(el,d.nav_list[i]);
		}
		el.find(".add_nav").click(function(){
            hasSaved = false;
			m_girl.add_nav_data(el);
		});
		el.find(".nav_close").click(function(){
            hasSaved = false;
			$(this).parent().remove();
		});
		el.find(".select_links:first").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				el.find("#bg_link").val(url);
			});
		});
		el.find(".add_image").click(function(){
			$.jImageManager({
				img_width:640,
				img_height:1136,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					el.find(".bg_img").attr('src',path);
                    hasSaved = false;
				}
			});
		});
	},

	add_nav_data: function(el,d){
		if(d == undefined) d = {};
		var el_row = $("#template_list .nav_data_tpl").clone();
		el_row.appendTo(el.find(".nav_list"));
		
		if(d.nav_logo_img) { el_row.find(".nav_logo_img").attr('src',d.nav_logo_img); el_row.find(".del_logo_img").show(); }
		if(d.nav_bg_img) { el_row.find(".nav_bg_img").attr('src',d.nav_bg_img); el_row.find(".del_bg_img").show(); }

		el_row.find("#nav_title").val(d.nav_title);
        var url = this.get_def_url(d.nav_def);
        if(url) d.nav_link = url;
		el_row.find("#nav_link").val(d.nav_link);
		el_row.find("#nav_sumary").val(d.nav_sumary);
		
		el_row.find(".add_logo_image").click(function(){
			$.jImageManager({
				img_width:50,
				img_height:50,
				ok_cb:function(img_arr){
					el_row.find(".nav_logo_img").attr('src',img_arr[0].url);
                    hasSaved = false;
					el_row.find(".del_logo_img").show();
				}
			});
		});
		
		el_row.find(".add_bg_image").click(function(){
			$.jImageManager({
				img_width:172,
				img_height:276,
				ok_cb:function(img_arr){
					el_row.find(".nav_bg_img").attr('src',img_arr[0].url);
                    hasSaved = false;
					el_row.find(".del_bg_img").show();
				}
			});
		});

		el_row.find(".del_logo_img").click(function(){
            hasSaved = false;
			el_row.find(".nav_logo_img").removeAttr("src");
			$(this).hide();
		});

		el_row.find(".del_bg_img").click(function(){
            hasSaved = false;
			el_row.find(".nav_bg_img").removeAttr("src");
			$(this).hide();
		});

		el_row.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				el_row.find("#nav_link").val(url);
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_girl").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.bg_img = el.find(".bg_img").attr('src');
		d.bg_link = el.find("#bg_link").val();
		d.nav_list = [];
		el.find(".nav_list .nav_data").each(function(){
			var t = {};
			t.nav_logo_img = $(this).find(".nav_logo_img").attr('src');
			t.nav_bg_img = $(this).find(".nav_bg_img").attr('src');
			t.nav_title = $(this).find("#nav_title").val();
			t.nav_link = $(this).find("#nav_link").val();
			t.nav_sumary = $(this).find("#nav_sumary").val();
			d.nav_list.push(t);
		});
		return d;
	}
};

