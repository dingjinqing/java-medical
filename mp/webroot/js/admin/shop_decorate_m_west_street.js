var m_west_street = {

	init_ev_el: function(el,d){
		var index;
		var flag = -1;
		if(!d.bg_img) d.bg_img = 'image/shop_beautify/west_street_bg.png';
		el.find(".tpl_img").attr('src',d.bg_img);	
		el.find(".nav_list").html("");		
		this.add_left_nav(el,d.nav_list);
	},
	add_left_nav: function(el,d){		
		
		for(var i in d){
			nav_bg_img = el.find(".west_street_img").eq(i);
			src = d[i].nav_bg_img;						
			nav_bg_img.attr("src",src);

			remove_title = ".west_street_title_" + i;
			el.find(remove_title).remove(remove_title);
			title = d[i].nav_title;			
			west_street_title = el.find(".west_street_title").clone().attr("class","west_street_title_"+i).html(title);			
			west_street_nav_div = el.find(".west_street_title");			
			$(west_street_title).prependTo(west_street_nav_div);

		}

	},
    get_def_url: function(nav_def){
        var idx = parseInt(nav_def);
        switch (idx){
        	case 1: return front_root_url + "index.php?c=index&all=true";
            case 2: return front_root_url + "index.php?c=index&all=true";
            case 3: return front_root_url + "index.php?c=index&all=true";
            case 4: return front_root_url + "index.php?c=index&all=true";
            case 5: return front_root_url + "index.php?c=orderlist";
            case 6: return front_root_url + "index.php?c=cart";
            default : return "";
        }
    },

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);

		el.find(".bg_img").attr('src',d.bg_img);		
		el.find("#bg_link").val(d.bg_link);

		for(var i in d.nav_list){						
			m_west_street.add_nav_data(el,d.nav_list[i],i);
		}
		
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

	add_nav_data: function(el,d,i){		
		if(d == undefined) d = {};
		var el_row = $("#template_list .nav_data_tpl").clone();
		el_row.appendTo(el.find(".nav_list"));		
		if(d.nav_bg_img) { el_row.find(".nav_bg_img").attr('src',d.nav_bg_img); el_row.find(".del_bg_img").show(); }
		el_row.find("#nav_title").val(d.nav_title);
        var url = this.get_def_url(d.nav_def);
        if(url) d.nav_link = url;
		el_row.find("#nav_link").val(d.nav_link);		
		var size = "";
		switch(i){
			case '0':
				size = "315x466";
				break;				
			case '1':				
			case '2':	
			case '4':				
			case '5':
				size = "315x286";
				break;
			case '3':
				size = "609x286";
				break;
		}
		el_row.find("#size_tips").html("尺寸为"+size);		
		el_row.find(".add_bg_image").click(function(){
			
			var index = $(".add_bg_image").index(this);
			var msg = "";
			switch(index){
				case 0:
						nav_bg_img_width = 315;
						nav_bg_img_height = 466;
						msg = "315x466";
					break;
				case 1:
				case 2:
				case 4:
				case 5:
						nav_bg_img_width = 315;
						nav_bg_img_height = 228;				
						msg = "315x228";
					break;
				case 3:
						nav_bg_img_width = 608;
						nav_bg_img_height = 286;								
						msg = "608x286";
					break;	
			}							
			$.jImageManager({
				img_width:nav_bg_img_width,
				img_height:nav_bg_img_height,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					el_row.find(".nav_bg_img").attr('src',path);
                    hasSaved = false;
					el_row.find(".del_bg_img").show();
				}
			});
			
		});

		el_row.find(".del_logo_img").click(function(){
			//el_row.find(".nav_logo_img").removeAttr("src");
            hasSaved = false;
			$(this).hide();
		});

		el_row.find(".del_bg_img").click(function(){
			el_row.find(".nav_bg_img").removeAttr("src");
            hasSaved = false;
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
		var el = $("#template_list .d_m_west_street").clone();
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
			t.nav_bg_img = $(this).find(".nav_bg_img").attr('src');
			t.nav_title = $(this).find("#nav_title").val();
			t.nav_link = $(this).find("#nav_link").val();
			d.nav_list.push(t);
		});
		return d;
	}
};


