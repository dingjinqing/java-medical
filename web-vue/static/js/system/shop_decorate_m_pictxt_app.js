

var m_title = {
	init_ev_el: function(el,data){
		if(data.title != undefined && data.title != ''){
			el.find("span").text(data.title);
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title_link").val(d.title_link);
		if(d.action)    el.find("#app_action").val(d.action);
		if(d.goods_id)  el.find("#goods_id").val(d.goods_id);
		if(d.goods_name)  el.find("#goods_name").val(d.goods_name);
		if(d.price)      el.find("#price").val(d.price);
		if(d.cat_id)     el.find("#cat_id").val(d.cat_id);
		el.find(".select_links").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link").val(url);
				if(app_action=='good'){
					el.find("#app_action").val(app_action);
					el.find("#goods_id").val(goods_id);
					el.find("#goods_name").val(name);
					el.find("#price").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action").val(app_action);
					el.find("#cat_id").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action").val(app_action);
				}else if(app_action=='url'){
					el.find("#app_action").val(url);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_title").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		/* d.title_link = el.find("#title_link").val();
		d.app_action =  el.find("#app_action").val();
		if(d.app_action=='good'){
			d.action ='good';
			d.goods_id= el.find("#goods_id").val();
			d.goods_name= el.find("#goods_name").val();
			d.price= el.find("#price").val();
		}else if(d.app_action=='cat'){
			d.action ='cat';
			d.cat_id= el.find("#cat_id").val();
		}else if(d.app_action=='collect' || d.app_action== 'new' || d.app_action=='category' || d.app_action=='order' || d.app_action=='personal'){
			d.action =d.app_action;
		}else{
			d.action =d.app_action;
		} */
		return d;
	}
};


var m_text = {
	init_ev_el: function(el,data){
	    if(data.title != undefined && data.title != ''){
		    el.find("span").text(data.title);
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_text").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		return d;
	}
};



var m_single_image = {
	init_ev_el: function(el,data){
		if(data.title != undefined && data.title != ''){
			el.find("a").text(data.title);
            el.find(".single_img_title").removeClass("hide");
			if(data.title_link != undefined &&  data.title_link != ''){
				el.find("a").attr('href', data.title_link);
			}
		}else{
            el.find(".single_img_title").addClass("hide");
        }
		if(data.img_url){
			el.find(".image").attr("src",data.img_url);
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		/* el.find("#title").val(d.title); */
		el.find("#title_link").val(d.title_link);
		if(d.img_url)   el.find(".image").attr("src",d.img_url);
		if(d.action)    el.find("#app_action").val(d.action);
		if(d.goods_id)  el.find("#goods_id").val(d.goods_id);
		if(d.goods_name)  el.find("#goods_name").val(d.goods_name);
		if(d.price)      el.find("#price").val(d.price);
		if(d.cat_id)     el.find("#cat_id").val(d.cat_id);
		el.find(".select_links").click(function(){
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link").val(url);
				if(app_action=='good'){
					el.find("#app_action").val(app_action);
					el.find("#goods_id").val(goods_id);
					el.find("#goods_name").val(name);
					el.find("#price").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action").val(app_action);
					el.find("#cat_id").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action").val(app_action);
				}else if(app_action=='url'){
					el.find("#app_action").val(app_action);
				}

			},"iframe");
            hasSaved = false;
		});
		el.find(".add_image").click(function(){
			$.jImageManager({
				img_width:600,
				img_height:300,
				ok_cb:function(img_arr){
					$(".module_body .data_item img").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_single_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		d.title_link = el.find("#title_link").val();
		d.img_url = el.find("img").attr("src");
		d.app_action =  el.find("#app_action").val();
		if(d.app_action=='good'){
			d.action ='good';
			d.goods_id= el.find("#goods_id").val();
			d.goods_name= el.find("#goods_name").val();
			d.price= el.find("#price").val();
		}else if(d.app_action=='cat'){
			d.action ='cat';
			d.cat_id= el.find("#cat_id").val();
		}else if(d.app_action=='collect' || d.app_action== 'new' || d.app_action=='category' || d.app_action=='order' || d.app_action=='personal'){
			d.action =d.app_action;
		}else{
			d.action =d.app_action;
		}
		return d;
	}
};


var m_double_image = {
	init_ev_el: function(el,data){
		if(data.img_url1){
			el.find(".image_view:eq(0) img").attr("src",data.img_url1);
            el.find(".image_view:eq(0)").css("background","");
		}
		if(data.img_url2){
			el.find(".image_view:eq(1) img").attr("src",data.img_url2);
            el.find(".image_view:eq(1)").css("background","");
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		$(".module_body .data_item #title_link1").val(d.title_link1);
		$(".module_body .data_item #title_link2").val(d.title_link2);
		if(d.img_url1) el.find(".image1").attr("src",d.img_url1);
		if(d.img_url2) el.find(".image2").attr("src",d.img_url2);
		el.find(".select_links1").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link1").val(url);
			});
		});
		el.find(".select_links2").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link2").val(url);
			});
		});
		el.find(".add_image1").click(function(){
			$.jImageManager({
				img_width:298,
				img_height:233,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image1").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image2").click(function(){
			$.jImageManager({
				img_width:298,
				img_height:233,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image2").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_double_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title_link1 = el.find("#title_link1").val();
		d.img_url1 = el.find(".image1").attr("src");
		d.title_link2 = el.find("#title_link2").val();
		d.img_url2 = el.find(".image2").attr("src");
		return d;
	}
};

var m_multi_image = {
	init_ev_el: function(el,data){
		el.find(".title span").text(data.title);
		if(data.img_items){
			for(var i in data.img_items){
				var t  = data.img_items[i];
				if(t.img_url) el.find("td:eq("+i+") img").attr("src",t.img_url);
            }
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find(".image_row_item").remove();
		for(var i=0;i<10;i++){
			if(i<4 || d.img_items && d.img_items[i]){
				var t = d.img_items ? (d.img_items[i] ? d.img_items[i] : {}) : {};
				this.add_image_row_item(el,t,i);
			}
		}
		el.find(".add_row_item").click(function(){
			if(el.find(".image_row_item").length >=10){
				util.mobile_alert("列表超过上限!");
				return;
			}
            hasSaved = false;
			m_multi_image.add_image_row_item(el,{},'');
		});
	},
	add_image_row_item: function(el,d,i){
		if(d == undefined) d = {};
		var el_row = $("#template_list .d_m_multi_image .image_row_item").clone();
		el_row.appendTo(el.find(".multi_image_tbl"));
		if(d.img_url) el_row.find(".image").attr('src',d.img_url);
		el_row.find("#title_link").val(d.title_link);
		if(d.action)    el_row.find("#app_action").val(d.action);
		if(d.goods_id)  el_row.find("#goods_id").val(d.goods_id);
		if(d.goods_name)  el_row.find("#goods_name").val(d.goods_name);
		if(d.price)      el_row.find("#price").val(d.price);
		if(d.cat_id)     el_row.find("#cat_id").val(d.cat_id);
		el_row.find(".add_image").click(function(){
			$.jImageManager({
				img_width:180,
				img_height:180,
				ok_cb:function(img_arr){
					el_row.find(".image").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el_row.find(".select_links").click(function(){
			/*show_links_dlg(function(url){
				el_row.find("#title_link").val(url);
			});*/
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				el_row.find("#title_link").val(url);
				if(app_action=='good'){
					el_row.find("#app_action").val(app_action);
					el_row.find("#goods_id").val(goods_id);
					el_row.find("#goods_name").val(name);
					el_row.find("#price").val(price);
				}else if(app_action=='cat'){
					el_row.find("#app_action").val(app_action);
					el_row.find("#cat_id").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el_row.find("#app_action").val(app_action);
				}else if(app_action=='url'){
					el_row.find("#app_action").val(app_action);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
		el_row.find(".up_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var pre = p.prev(".image_row_item");
			if(pre.length > 0){
				pre.insertAfter(p);
			}
		});
		el_row.find(".down_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var next = p.next(".image_row_item");
			if(next.length > 0){
				next.insertBefore(p);
			}
		});
		el_row.find(".remove").click(function(){
            hasSaved = false;
			var p = $(this).parents(".multi_image_tbl");
			if(p.find(".image_row_item").length <= 4){
				return;
			}
			else
				$(this).parents(".image_row_item").remove();				
		});
		el_row.appendTo(el.find(".multi_image_tbl"));
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_multi_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		d.img_items = [];
		el.find(".image_row_item").each(function(){
			/* d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val()
			}); */
			
			if($(this).find("#app_action").val()=='good'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),'action':$(this).find("#app_action").val(),'goods_id':$(this).find("#goods_id").val(),'goods_name':$(this).find("#goods_name").val(),'price':$(this).find("#price").val()
				});
			}else if($(this).find("#app_action").val()=='cat'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    'cat_id':$(this).find("#cat_id").val()
				});
			}else if($(this).find("#app_action").val()=='collect' || $(this).find("#app_action").val()== 'new' || $(this).find("#app_action").val()=='category' || $(this).find("#app_action").val()=='order' || $(this).find("#app_action").val()=='personal'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    });
			}else{
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    });
			}
		});
		return d;
	}
};


var m_dashed_line = {
	no_edit:true,
	init_ev_el: function(el,data){
	},

	show_edit_el:function(data){
		$(".module_body").html("");
	},
	get_data: function(){
		return {};
	}
};





// 轮播图片

var m_scroll_image = {
	timer: 0,
	init_ev_el: function(el,data){
		/*el.find(".scroll_image_view").html("");*/
		el.find(".scroll_image_dot_nav").html("");
		if(data.img_items){
			/*el.find(".scroll_image_view").html("");*/
			var idx = 0;
			for(var i in data.img_items){
				var t  = data.img_items[i];
				if(t.img_url) {
					var img = $("#template_list .m_scroll_image .scroll_image_view img").clone();
					img.appendTo(el.find(".scroll_image_view"));
					var span = $("#template_list .m_scroll_image .scroll_image_dot_nav span").clone();
					span.appendTo(el.find(".scroll_image_dot_nav"));
					img.attr("src",t.img_url);
					$(".scroll_image_view img:first-child").attr("src", t.img_url);
					idx++;
				}
			}
			if(this.timer) clearInterval(this.timer);
			if(idx > 0){
				el.find(".scroll_image_view img:eq(0)").addClass("active");
				el.find(".scroll_image_dot_nav span:eq(0)").addClass("nav_active");
				this.timer = setInterval(function(){
					var i = el.find(".scroll_image_view img.active").index();
					i = (i+1) % idx;
					el.find(".scroll_image_view img").removeClass("active");
					el.find(".scroll_image_dot_nav span").removeClass("nav_active");
					el.find(".scroll_image_view img:eq("+i+")").addClass("active");
					el.find(".scroll_image_dot_nav span:eq("+i+")").addClass("nav_active");
				},10000);
			}
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find(".image_row_item").remove();
		for(var i=0;i<4;i++){
			if(i<2 || d.img_items && d.img_items[i]){
				var t = d.img_items ? (d.img_items[i] ? d.img_items[i] : {}) : {};
				this.add_image_row_item(el,t);
			}
		}
		el.find(".add_row_item").click(function(){
			if(el.find(".image_row_item").length >=4){
                util.mobile_alert("列表超过上限!");
				return;
			}
            hasSaved = false;
			m_scroll_image.add_image_row_item(el,{});
		});
	},
	add_image_row_item: function(el,d){
		if(d == undefined) d = {};
		var el_row = $("#template_list .d_m_scroll_image .image_row_item").clone();
		el_row.appendTo(el.find(".scroll_image_tbl"));
		if(d.img_url) el_row.find(".image").attr('src',d.img_url);
		el_row.find("#title_link").val(d.title_link);
		if(d.action)    el_row.find("#app_action").val(d.action);
		if(d.goods_id)  el_row.find("#goods_id").val(d.goods_id);
		if(d.goods_name)  el_row.find("#goods_name").val(d.goods_name);
		if(d.price)      el_row.find("#price").val(d.price);
		if(d.cat_id)     el_row.find("#cat_id").val(d.cat_id);
		el_row.find(".add_image").click(function(){
			$.jImageManager({
				img_width:600,
				img_height:300,
				ok_cb:function(img_arr){
					el_row.find(".image").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el_row.find(".select_links").click(function(){
			/* show_links_dlg(function(url){
				el_row.find("#title_link").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				el_row.find("#title_link").val(url);
				if(app_action=='good'){
					el_row.find("#app_action").val(app_action);
					el_row.find("#goods_id").val(goods_id);
					el_row.find("#goods_name").val(name);
					el_row.find("#price").val(price);
				}else if(app_action=='cat'){
					el_row.find("#app_action").val(app_action);
					el_row.find("#cat_id").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el_row.find("#app_action").val(app_action);
				}else if(app_action=='url'){
					el_row.find("#app_action").val(app_action);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
		el_row.find(".up_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var pre = p.prev(".image_row_item");
			if(pre.length > 0){
				pre.insertAfter(p);
			}
		});
		el_row.find(".down_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var next = p.next(".image_row_item");
			if(next.length > 0){
				next.insertBefore(p);
			}
		});
		el_row.find(".remove").click(function(){
            hasSaved = false;
			var p = $(this).parents(".scroll_image_tbl");
			if(p.find(".image_row_item").length <= 1){
				return;
			}
			else
				$(this).parents(".image_row_item").remove();				
		});
		el_row.appendTo(el.find(".scroll_image_tbl"));
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_scroll_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.img_items = [];
		el.find(".image_row_item").each(function(){
			
			if($(this).find("#app_action").val()=='good'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),'action':$(this).find("#app_action").val(),'goods_id':$(this).find("#goods_id").val(),'goods_name':$(this).find("#goods_name").val(),'price':$(this).find("#price").val()
				});
			}else if($(this).find("#app_action").val()=='cat'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    'cat_id':$(this).find("#cat_id").val()
				});
			}else if($(this).find("#app_action").val()=='collect' || $(this).find("#app_action").val()== 'new' || $(this).find("#app_action").val()=='category' || $(this).find("#app_action").val()=='order' || $(this).find("#app_action").val()=='personal'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    });
			}else{
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    });
			}
			
		});
		return d;
	}
};


// 辅助空白模块
var m_blank = {
	get_valid_height: function(h){
		h = parseInt(h);
		if(isNaN(h)) h = 20;
		if(h < 20) h = 20;
		if(h > 60) h = 60;
		return h;
	},
	init_ev_el: function(el,data){
		data.blank_height = this.get_valid_height(data.blank_height);
		el.find(".m_blank_module").css("height",data.blank_height + "px");
	},
	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		d.blank_height = this.get_valid_height(d.blank_height);
		el.find("#blank_height").val(d.blank_height);
		el.find("#blank_height").keydown(function(e){
			if(!(e.keyCode == 0x09 || e.keyCode == 0x08 || e.keyCode == 37 || e.keyCode == 39 
				|| e.keyCode >= 0x30 && e.keyCode <= 0x39)) return false;
		});
		el.find("#blank_height").keyup(function(e){
			var v = parseInt($(this).val());
			if(!isNaN(v)){
				if(v<20) v = 20;
				if(v>60) v = 60;
				var el = $("#drag_area div.item_selected");
				el.find(".m_blank_module").css("height",v + "px");
			}
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_blank").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.blank_height = this.get_valid_height(el.find("#blank_height").val());
		return d;
	}
};





// 富文本模块

var m_rich_text = {
	init_ev_el: function(el,data){
		if(data.rich_text) el.find(".m_rich_text_module").html(data.rich_text);
	},

	fill_edit_el: function(el,d){
		this.show_flag = true;
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		if(!d.rich_text) d.rich_text = "";
		UE.getEditor("editor").setContent(d.rich_text);
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_rich_text").clone();
		$(".module_body").html("");		
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.rich_text = UE.getEditor("editor").getContent();
		return d;
	}
};

var m_navigation = {
	init_ev_el: function(el,data){
		if(data.img_url1){
			el.find(".image_view_:eq(0) img").attr("src",data.img_url1);
		}
		if(data.img_url3){
			el.find(".image_view_:eq(1) img").attr("src",data.img_url3);
		}
		if(data.title2 != undefined && data.title != ''){
			el.find(".title_set:eq(0)").text(data.title2);
		}
		if(data.img_url2){
			el.find(".image_view_:eq(2) img").attr("src",data.img_url2);
		}
		
		if(data.img_url1){
			el.find(".image_view_:eq(0) img").attr("src",data.img_url1);
		}
		if(data.img_url3){
			el.find(".image_view_:eq(1) img").attr("src",data.img_url3);
		}
		if(data.title2 != undefined && data.title != ''){
			el.find(".title_set:eq(0)").text(data.title2);
		}
		if(data.img_url2){
			el.find(".image_view_:eq(2) img").attr("src",data.img_url2);
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title2").val(d.title2);
		$(".module_body .data_item #title_link1").val(d.title_link1);
		$(".module_body .data_item #title_link2").val(d.title_link2);
		$(".module_body .data_item #title_link3").val(d.title_link3);
		if(d.img_url1) el.find(".image1").attr("src",d.img_url1);
		if(d.img_url2) el.find(".image2").attr("src",d.img_url2);
		if(d.img_url3) el.find(".image3").attr("src",d.img_url3);
		el.find(".select_links1").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link1").val(url);
			});
		});
		el.find(".select_links2").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link2").val(url);
			});
		});
		el.find(".select_links3").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link3").val(url);
			});
		});
		el.find(".add_image1").click(function(){
			$.jImageManager({
				img_width:183,
				img_height:36,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image1").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image2").click(function(){
			$.jImageManager({
				img_width:88,
				img_height:88,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image2").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image3").click(function(){
			$.jImageManager({
				img_width:127,
				img_height:34,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image3").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_navigation").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title_link1 = el.find("#title_link1").val();
		d.img_url1 = el.find(".image1").attr("src");
		d.title = el.find("#title").val();
		d.title2 = el.find("#title2").val();
		d.title_link3 = el.find("#title_link3").val();
		d.title_link2 = el.find("#title_link2").val();
		d.img_url2 = el.find(".image2").attr("src");
		d.img_url3 = el.find(".image3").attr("src");
		return d;
	}
};

var m_image_small = {
	init_ev_el: function(el,data){
		if(data.title != undefined && data.title != ''){
			el.find("a").text(data.title);
            el.find(".single_img_title").removeClass("hide");
			if(data.title_link != undefined &&  data.title_link != ''){
				el.find("a").attr('href', data.title_link);
			}
		}else{
            el.find(".single_img_title").addClass("hide");
        }
		if(data.img_url){
			el.find(".image").attr("src",data.img_url);
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title_link").val(d.title_link);
		if(d.img_url) el.find(".image").attr("src",d.img_url);
		el.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link").val(url);
			});
		});
		el.find(".add_image").click(function(){
			$.jImageManager({
				//img_width:620,
				//img_height:38,
				ok_cb:function(img_arr){
					$(".module_body .data_item img").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_image_small").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		d.title_link = el.find("#title_link").val();
		d.img_url = el.find("img").attr("src");
		return d;
	}
};

var m_image_guide = {
	init_ev_el: function(el,data){
		if(data.img_url1){
			el.find(".image_view_:eq(0) img").attr("src",data.img_url1);
		}
		if(data.img_url2){
			el.find(".image_view_:eq(1) img").attr("src",data.img_url2);
		}
		if(data.img_url3){
			el.find(".image_view_:eq(2) img").attr("src",data.img_url3);
		}
		if(data.img_url4){
			el.find(".image_view_:eq(3) img").attr("src",data.img_url4);
		}
		if(data.title1 != undefined && data.title != ''){
			el.find(".title_set:eq(0)").text(data.title1);
		}
		if(data.title2 != undefined && data.title != ''){
			el.find(".title_set:eq(1)").text(data.title2);
		}
		if(data.title3 != undefined && data.title != ''){
			el.find(".title_set:eq(2)").text(data.title3);
		}
		if(data.title4 != undefined && data.title != ''){
			el.find(".title_set:eq(3)").text(data.title4);
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title1").val(d.title1);
		el.find("#title2").val(d.title2);
		el.find("#title3").val(d.title3);
		el.find("#title4").val(d.title4);
		$(".module_body .data_item #title_link1").val(d.title_link1);
		$(".module_body .data_item #title_link2").val(d.title_link2);
		$(".module_body .data_item #title_link3").val(d.title_link3);
		$(".module_body .data_item #title_link4").val(d.title_link4);
		if(d.img_url1) el.find(".image1").attr("src",d.img_url1);
		if(d.img_url2) el.find(".image2").attr("src",d.img_url2);
		if(d.img_url3) el.find(".image3").attr("src",d.img_url3);
		if(d.img_url4) el.find(".image4").attr("src",d.img_url4);
		
		if(d.action1)    el.find("#app_action1").val(d.action1);
		if(d.goods_id1)  el.find("#goods_id1").val(d.goods_id1);
		if(d.goods_name1)  el.find("#goods_name1").val(d.goods_name1);
		if(d.price1)      el.find("#price1").val(d.price1);
		if(d.cat_id1)     el.find("#cat_id1").val(d.cat_id1);
		
		if(d.action2)    el.find("#app_action2").val(d.action2);
		if(d.goods_id2)  el.find("#goods_id2").val(d.goods_id2);
		if(d.goods_name2)  el.find("#goods_name2").val(d.goods_name2);
		if(d.price2)      el.find("#price2").val(d.price2);
		if(d.cat_id2)     el.find("#cat_id2").val(d.cat_id2);
		
		if(d.action3)    el.find("#app_action3").val(d.action3);
		if(d.goods_id3)  el.find("#goods_id3").val(d.goods_id3);
		if(d.goods_name3)  el.find("#goods_name3").val(d.goods_name3);
		if(d.price3)      el.find("#price3").val(d.price3);
		if(d.cat_id3)     el.find("#cat_id3").val(d.cat_id3);
		
		if(d.action4)    el.find("#app_action4").val(d.action4);
		if(d.goods_id4)  el.find("#goods_id4").val(d.goods_id4);
		if(d.goods_name4)  el.find("#goods_name4").val(d.goods_name4);
		if(d.price4)      el.find("#price4").val(d.price4);
		if(d.cat_id4)     el.find("#cat_id4").val(d.cat_id4);
		 
		el.find(".select_links1").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link1").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link1").val(url);
			    if(app_action=='good'){
					el.find("#app_action1").val(app_action);
					el.find("#goods_id1").val(goods_id);
					el.find("#goods_name1").val(name);
					el.find("#price1").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action1").val(app_action);
					el.find("#cat_id1").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action1").val(app_action);
				}else if(app_action=='url'  || app_action=='coupon'){
					el.find("#app_action1").val(app_action);
				}
			    	
			},"iframe",2);
            hasSaved = false;
		});
		el.find(".select_links2").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link2").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link2").val(url);
			    if(app_action=='good'){
					el.find("#app_action2").val(app_action);
					el.find("#goods_id2").val(goods_id);
					el.find("#goods_name2").val(name);
					el.find("#price2").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action2").val(app_action);
					el.find("#cat_id2").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action2").val(app_action);
				}else if(app_action=='url' || app_action=='coupon'){
					el.find("#app_action2").val(app_action);
				}
			    	
			},"iframe",2);
            hasSaved = false;
		});
		el.find(".select_links3").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link3").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				 $(".module_body .data_item #title_link3").val(url);
			    if(app_action=='good'){
					el.find("#app_action3").val(app_action);
					el.find("#goods_id3").val(goods_id);
					el.find("#goods_name3").val(name);
					el.find("#price3").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action3").val(app_action);
					el.find("#cat_id3").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action3").val(app_action);
				}else if(app_action=='url' || app_action=='coupon'){
					el.find("#app_action3").val(app_action);
				}
			    	
			},"iframe",2);
            hasSaved = false;
		});
		el.find(".select_links4").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link4").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link4").val(url);
			    if(app_action=='good'){
					el.find("#app_action4").val(app_action);
					el.find("#goods_id4").val(goods_id);
					el.find("#goods_name4").val(name);
					el.find("#price4").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action4").val(app_action);
					el.find("#cat_id4").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action4").val(app_action);
				}else if(app_action=='url'|| app_action=='coupon'){
					el.find("#app_action4").val(app_action);
				}
			    	
			},"iframe",2);
            hasSaved = false;
		});
		el.find(".add_image1").click(function(){
			$.jImageManager({
				img_width:140,
				img_height:140,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image1").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image2").click(function(){
			$.jImageManager({
				img_width:140,
				img_height:140,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image2").attr('src',img_arr[0].url);
				}
			});
		});
		el.find(".add_image3").click(function(){
			$.jImageManager({
				img_width:140,
				img_height:140,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image3").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image4").click(function(){
			$.jImageManager({
				img_width:140,
				img_height:140,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image4").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_image_guide").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title_link1 = el.find("#title_link1").val();
		d.img_url1 = el.find(".image1").attr("src");
		d.title1 = el.find("#title1").val();
		d.app_action1 =  el.find("#app_action1").val();
		if(d.app_action1=='good'){
			d.action1 ='good';
			d.goods_id1= el.find("#goods_id1").val();
			d.goods_name1= el.find("#goods_name1").val();
			d.price1= el.find("#price1").val();
		}else if(d.app_action1=='cat'){
			d.action1 ='cat';
			d.cat_id1= el.find("#cat_id1").val();
		}else if(d.app_action1=='collect' || d.app_action1== 'new' || d.app_action1=='category' || d.app_action1=='order' || d.app_action1=='personal'){
			d.action1 =d.app_action1;
		}else{
			d.action1 =d.app_action1;
		}
		
		d.title_link2 = el.find("#title_link2").val();
		d.img_url2 = el.find(".image2").attr("src");
		d.title2 = el.find("#title2").val();
		d.app_action2 =  el.find("#app_action2").val();
		if(d.app_action2=='good'){
			d.action2 ='good';
			d.goods_id2= el.find("#goods_id2").val();
			d.goods_name2= el.find("#goods_name2").val();
			d.price2= el.find("#price2").val();
		}else if(d.app_action2=='cat'){
			d.action2 ='cat';
			d.cat_id2= el.find("#cat_id2").val();
		}else if(d.app_action2=='collect' || d.app_action2== 'new' || d.app_action2=='category' || d.app_action2=='order' || d.app_action2=='personal'){
			d.action2 =d.app_action2;
		}else{
			d.action2 =d.app_action2;
		}
		
		d.title_link3 = el.find("#title_link3").val();
		d.img_url3 = el.find(".image3").attr("src");
		d.title3 = el.find("#title3").val();
		d.app_action3 =  el.find("#app_action3").val();
		if(d.app_action3=='good'){
			d.action3 ='good';
			d.goods_id3= el.find("#goods_id3").val();
			d.goods_name3= el.find("#goods_name3").val();
			d.price3= el.find("#price3").val();
		}else if(d.app_action3=='cat'){
			d.action3 ='cat';
			d.cat_id3= el.find("#cat_id3").val();
		}else if(d.app_action3=='collect' || d.app_action3== 'new' || d.app_action3=='category' || d.app_action3=='order' || d.app_action3=='personal'){
			d.action3 =d.app_action3;
		}else{
			d.action3 =d.app_action3;
		}
		
		d.title_link4 = el.find("#title_link4").val();
		d.img_url4 = el.find(".image4").attr("src");
		d.title4 = el.find("#title4").val();
		d.app_action4 =  el.find("#app_action4").val();
		if(d.app_action4=='good'){
			d.action4 ='good';
			d.goods_id4= el.find("#goods_id4").val();
			d.goods_name4= el.find("#goods_name4").val();
			d.price4= el.find("#price4").val();
		}else if(d.app_action4=='cat'){
			d.action4 ='cat';
			d.cat_id4= el.find("#cat_id4").val();
		}else if(d.app_action4=='collect' || d.app_action4== 'new' || d.app_action4=='category' || d.app_action4=='order' || d.app_action4=='personal'){
			d.action4 =d.app_action4;
		}else{
			d.action4 =d.app_action4;
		}
		return d;
	}
};

//四宫格
var m_image_four = {
	init_ev_el: function(el,data){
		if(data.img_url1){
			el.find(".image_view_:eq(0) img").attr("src",data.img_url1);
		}
		if(data.img_url2){
			el.find(".image_view_:eq(1) img").attr("src",data.img_url2);
		}
		if(data.img_url3){
			el.find(".image_view_:eq(2) img").attr("src",data.img_url3);
		}
		if(data.img_url4){
			el.find(".image_view_:eq(3) img").attr("src",data.img_url4);
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		$(".module_body .data_item #title_link1").val(d.title_link1);
		$(".module_body .data_item #title_link2").val(d.title_link2);
		$(".module_body .data_item #title_link3").val(d.title_link3);
		$(".module_body .data_item #title_link4").val(d.title_link4);
		if(d.img_url1) el.find(".image1").attr("src",d.img_url1);
		if(d.img_url2) el.find(".image2").attr("src",d.img_url2);
		if(d.img_url3) el.find(".image3").attr("src",d.img_url3);
		if(d.img_url4) el.find(".image4").attr("src",d.img_url4);
		
		if(d.action1)    el.find("#app_action1").val(d.action1);
		if(d.goods_id1)  el.find("#goods_id1").val(d.goods_id1);
		if(d.goods_name1)  el.find("#goods_name1").val(d.goods_name1);
		if(d.price1)      el.find("#price1").val(d.price1);
		if(d.cat_id1)     el.find("#cat_id1").val(d.cat_id1);
		
		if(d.action2)    el.find("#app_action2").val(d.action2);
		if(d.goods_id2)  el.find("#goods_id2").val(d.goods_id2);
		if(d.goods_name2)  el.find("#goods_name2").val(d.goods_name2);
		if(d.price2)      el.find("#price2").val(d.price2);
		if(d.cat_id2)     el.find("#cat_id2").val(d.cat_id2);
		
		if(d.action3)    el.find("#app_action3").val(d.action3);
		if(d.goods_id3)  el.find("#goods_id3").val(d.goods_id3);
		if(d.goods_name3)  el.find("#goods_name3").val(d.goods_name3);
		if(d.price3)      el.find("#price3").val(d.price3);
		if(d.cat_id3)     el.find("#cat_id3").val(d.cat_id3);
		
		if(d.action4)    el.find("#app_action4").val(d.action4);
		if(d.goods_id4)  el.find("#goods_id4").val(d.goods_id4);
		if(d.goods_name4)  el.find("#goods_name4").val(d.goods_name4);
		if(d.price4)      el.find("#price4").val(d.price4);
		if(d.cat_id4)     el.find("#cat_id4").val(d.cat_id4);
		 
		el.find(".select_links1").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link1").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link1").val(url);
			    if(app_action=='good'){
					el.find("#app_action1").val(app_action);
					el.find("#goods_id1").val(goods_id);
					el.find("#goods_name1").val(name);
					el.find("#price1").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action1").val(app_action);
					el.find("#cat_id1").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action1").val(app_action);
				}else if(app_action=='url'){
					el.find("#app_action1").val(app_action);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
		el.find(".select_links2").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link2").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link2").val(url);
			    if(app_action=='good'){
					el.find("#app_action2").val(app_action);
					el.find("#goods_id2").val(goods_id);
					el.find("#goods_name2").val(name);
					el.find("#price2").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action2").val(app_action);
					el.find("#cat_id2").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action2").val(app_action);
				}else if(app_action=='url'){
					el.find("#app_action2").val(app_action);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
		el.find(".select_links3").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link3").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				 $(".module_body .data_item #title_link3").val(url);
			    if(app_action=='good'){
					el.find("#app_action3").val(app_action);
					el.find("#goods_id3").val(goods_id);
					el.find("#goods_name3").val(name);
					el.find("#price3").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action3").val(app_action);
					el.find("#cat_id3").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action3").val(app_action);
				}else if(app_action=='url'){
					el.find("#app_action3").val(app_action);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
		el.find(".select_links4").click(function(){
			/* show_links_dlg(function(url){
				 $(".module_body .data_item #title_link4").val(url);
			}); */
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				$(".module_body .data_item #title_link4").val(url);
			    if(app_action=='good'){
					el.find("#app_action4").val(app_action);
					el.find("#goods_id4").val(goods_id);
					el.find("#goods_name4").val(name);
					el.find("#price4").val(price);
				}else if(app_action=='cat'){
					el.find("#app_action4").val(app_action);
					el.find("#cat_id4").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el.find("#app_action4").val(app_action);
				}else if(app_action=='url'){
					el.find("#app_action4").val(app_action);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
		el.find(".add_image1").click(function(){
			$.jImageManager({
//				img_width:220,
//				img_height:280,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image1").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image2").click(function(){
			$.jImageManager({
//				img_width:320,
//				img_height:140,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image2").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image3").click(function(){
			$.jImageManager({
//				img_width:160,
//				img_height:140,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image3").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image4").click(function(){
			$.jImageManager({
//				img_width:160,
//				img_height:140,
				ok_cb:function(img_arr){
					$(".module_body .data_item .image4").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_image_four").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title_link1 = el.find("#title_link1").val();
		d.img_url1 = el.find(".image1").attr("src");
		d.title1 = el.find("#title1").val();
		d.app_action1 =  el.find("#app_action1").val();
		if(d.app_action1=='good'){
			d.action1 ='good';
			d.goods_id1= el.find("#goods_id1").val();
			d.goods_name1= el.find("#goods_name1").val();
			d.price1= el.find("#price1").val();
		}else if(d.app_action1=='cat'){
			d.action1 ='cat';
			d.cat_id1= el.find("#cat_id1").val();
		}else if(d.app_action1=='collect' || d.app_action1== 'new' || d.app_action1=='category' || d.app_action1=='order' || d.app_action1=='personal'){
			d.action1 =d.app_action1;
		}else{
			d.action1 =d.app_action1;
		}
		
		d.title_link2 = el.find("#title_link2").val();
		d.img_url2 = el.find(".image2").attr("src");
		d.title2 = el.find("#title2").val();
		d.app_action2 =  el.find("#app_action2").val();
		if(d.app_action2=='good'){
			d.action2 ='good';
			d.goods_id2= el.find("#goods_id2").val();
			d.goods_name2= el.find("#goods_name2").val();
			d.price2= el.find("#price2").val();
		}else if(d.app_action2=='cat'){
			d.action2 ='cat';
			d.cat_id2= el.find("#cat_id2").val();
		}else if(d.app_action2=='collect' || d.app_action2== 'new' || d.app_action2=='category' || d.app_action2=='order' || d.app_action2=='personal'){
			d.action2 =d.app_action2;
		}else{
			d.action2 =d.app_action2;
		}
		
		d.title_link3 = el.find("#title_link3").val();
		d.img_url3 = el.find(".image3").attr("src");
		d.title3 = el.find("#title3").val();
		d.app_action3 =  el.find("#app_action3").val();
		if(d.app_action3=='good'){
			d.action3 ='good';
			d.goods_id3= el.find("#goods_id3").val();
			d.goods_name3= el.find("#goods_name3").val();
			d.price3= el.find("#price3").val();
		}else if(d.app_action3=='cat'){
			d.action3 ='cat';
			d.cat_id3= el.find("#cat_id3").val();
		}else if(d.app_action3=='collect' || d.app_action3== 'new' || d.app_action3=='category' || d.app_action3=='order' || d.app_action3=='personal'){
			d.action3 =d.app_action3;
		}else{
			d.action3 =d.app_action3;
		}
		
		d.title_link4 = el.find("#title_link4").val();
		d.img_url4 = el.find(".image4").attr("src");
		d.title4 = el.find("#title4").val();
		d.app_action4 =  el.find("#app_action4").val();
		if(d.app_action4=='good'){
			d.action4 ='good';
			d.goods_id4= el.find("#goods_id4").val();
			d.goods_name4= el.find("#goods_name4").val();
			d.price4= el.find("#price4").val();
		}else if(d.app_action4=='cat'){
			d.action4 ='cat';
			d.cat_id4= el.find("#cat_id4").val();
		}else if(d.app_action4=='collect' || d.app_action4== 'new' || d.app_action4=='category' || d.app_action4=='order' || d.app_action4=='personal'){
			d.action4 =d.app_action4;
		}else{
			d.action4 =d.app_action4;
		}
		return d;
	}
};

var m_image_six = {
	init_ev_el: function(el,data){
		el.find(".title span").text(data.title);
		if(data.img_items){
			for(var i in data.img_items){
				var t  = data.img_items[i];
				if(t.img_url) el.find("td:eq("+i+") img").attr("src",t.img_url);
            }
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find(".image_row_item").remove();
		for(var i=0;i<6;i++){
			if(i<6 || d.img_items && d.img_items[i]){
				var t = d.img_items ? (d.img_items[i] ? d.img_items[i] : {}) : {};
				this.add_image_row_item(el,t,i);
			}
		}
		el.find(".add_row_item").click(function(){
			if(el.find(".image_row_item").length >=6){
                util.mobile_alert("列表超过上限!");
				return;
			}
            hasSaved = false;
			m_multi_image.add_image_row_item(el,{},'');
		});
	},
	add_image_row_item: function(el,d,i){
		if(d == undefined) d = {};
		var el_row = $("#template_list .d_m_image_six .image_row_item").clone();
		el_row.appendTo(el.find(".multi_image_tbl"));
		if(d.img_url) el_row.find(".image").attr('src',d.img_url);
		el_row.find("#title_link").val(d.title_link);
		if(d.action)    el_row.find("#app_action").val(d.action);
		if(d.goods_id)  el_row.find("#goods_id").val(d.goods_id);
		if(d.goods_name)  el_row.find("#goods_name").val(d.goods_name);
		if(d.price)      el_row.find("#price").val(d.price);
		if(d.cat_id)     el_row.find("#cat_id").val(d.cat_id);
		el_row.find(".add_image").click(function(){
			$.jImageManager({
				img_width:180,
				img_height:210,
				ok_cb:function(img_arr){
					el_row.find(".image").attr('src',img_arr[0].url);
                    hasSaved = false;
				}
			});
		});
		el_row.find(".select_links").click(function(){
			/*show_links_dlg(function(url){
				el_row.find("#title_link").val(url);
			});*/
			show_links_dlg(function(iframe){
				var tr = $("#data_list tr[data_row].active",iframe.document);
				var url = tr.attr("url");
				var goods_id=tr.attr("goods_id");
				var name = tr.attr("name");
				var cat_id = tr.attr("cat_id");
				var app_action = tr.attr("app_action");
				var price = tr.attr("price");
				el_row.find("#title_link").val(url);
				if(app_action=='good'){
					el_row.find("#app_action").val(app_action);
					el_row.find("#goods_id").val(goods_id);
					el_row.find("#goods_name").val(name);
					el_row.find("#price").val(price);
				}else if(app_action=='cat'){
					el_row.find("#app_action").val(app_action);
					el_row.find("#cat_id").val(cat_id);
				}else if(app_action=='collect' || app_action== 'new' || app_action=='category' || app_action=='order' || app_action=='personal'){
					el_row.find("#app_action").val(app_action);
				}else if(app_action=='url'){
					el_row.find("#app_action").val(app_action);
				}
			    	
			},"iframe");
            hasSaved = false;
		});
		el_row.find(".up_arrow").click(function(){
			var p = $(this).parents(".image_row_item");
			var pre = p.prev(".image_row_item");
			if(pre.length > 0){
				pre.insertAfter(p);
			}
            hasSaved = false;
		});
		el_row.find(".down_arrow").click(function(){
			var p = $(this).parents(".image_row_item");
			var next = p.next(".image_row_item");
			if(next.length > 0){
				next.insertBefore(p);
			}
            hasSaved = false;
		});
		el_row.find(".remove").click(function(){
			var p = $(this).parents(".multi_image_tbl");
			if(p.find(".image_row_item").length <= 3){
				return;
			}
			else{
                $(this).parents(".image_row_item").remove();
                hasSaved = false;
			}
		});
		el_row.appendTo(el.find(".multi_image_tbl"));
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_image_six").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		d.img_items = [];
		el.find(".image_row_item").each(function(){
			/* d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val()
			}); */
			
			if($(this).find("#app_action").val()=='good'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),'action':$(this).find("#app_action").val(),'goods_id':$(this).find("#goods_id").val(),'goods_name':$(this).find("#goods_name").val(),'price':$(this).find("#price").val()
				});
			}else if($(this).find("#app_action").val()=='cat'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    'cat_id':$(this).find("#cat_id").val()
				});
			}else if($(this).find("#app_action").val()=='collect' || $(this).find("#app_action").val()== 'new' || $(this).find("#app_action").val()=='category' || $(this).find("#app_action").val()=='order' || $(this).find("#app_action").val()=='personal'){
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    });
			}else{
				d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),action:$(this).find("#app_action").val(),
			    });
			}
		});
		return d;
	}
};

