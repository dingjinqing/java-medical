// $('.queren').click(function () {
//     $('.scroll_image_module').height($(".scroll_image_view .active:first-child").height());
//     console.log($('.scroll_image_module').height());
// })



var m_title = {
	init_ev_el: function(el,data){
		if(data.title != undefined){
			el.find(".new_title").text(data.title);
		}
		if(data.title_model == 1 || data.title_model == undefined){
			el.find(".basiclines").show();
            el.find(".headlines").hide();
            if(data.img_url && data.img_url != '' && data.img_url != undefined){
                el.find(".image").attr("src",data.img_url);
                el.find(".image").css("width","25px");
                el.find(".image").css("height","25px");
                el.find(".image").css("display","");
            }else{
                el.find(".image").attr("src",'');
                el.find(".image").hide();
            }
            if(data.color){
                el.find(".new_title").css("color",data.color);
            }
            if(data.bg_color){
                el.find(".title_module").css("background-color",data.bg_color);
            }
		}else{
            el.find(".basiclines").hide();
            el.find(".headlines").show();
            el.find(".new_title").css("color",'#333333');
            el.find(".title_module").css("background-color",'#ffffff');
            if(data.title_date != undefined){
            	if(data.title_date == ''){
                    el.find(".headdate").hide();
				}else{
                    el.find(".headdate").show();
                    el.find(".headdate").text(data.title_date);
				}
            }
            if(data.title_author != undefined){
                if(data.title_author == ''){
                    el.find(".headauthor").hide();
                }else{
                    el.find(".headauthor").show();
                    el.find(".headauthor").text(data.title_author);
                }
            }
            if(data.link_title != undefined){
                if(data.link_title == ''){
                    el.find(".headlink").hide();
                }else{
                    el.find(".headlink").show();
                    el.find(".headlink").text(data.link_title);
                }
            }
		}
		if(data.tit_center== 1 || data.tit_center == undefined){
			el.find(".title_module").css("text-align",'left');
	    }else{
			el.find(".title_module").css("text-align",'center');
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title_link").val(d.title_link);
		if(d.tit_center == 1 || d.tit_center == undefined){
            el.find("#title_left input").prop("checked",true);
		}else{
            el.find("#title_center input").prop("checked",true);
		}
        el.find('input[name="title_show"]').click(function () {
            if($(this).val() == 1){
                el.find(".m_basic").show();
                el.find(".m_new").hide();
            }else{
                el.find(".m_new").show();
                el.find(".m_basic").hide();
            }
        })
		if(d.title_model== 1 || d.title_model == undefined){
            el.find("input[name='title_show']").eq(0).prop('checked',true);
            el.find(".m_basic").show();
            el.find(".m_new").hide();
            if(d.img_url){
                el.find(".image").attr("src",d.img_url);
                el.find('.image').css("width","70px");
                el.find('.image').css("height","70px");
                el.find(".add_image").css("background","none");
                el.find(".del-has-image").show();
            }else{
                el.find(".del-has-image").hide();
            }
            if(d.color) el.find("input[name='font_color']").val(d.color);
            if(d.bg_color) el.find("input[name='bg_color']").val(d.bg_color);
		}else{
            el.find("input[name='title_show']").eq(1).prop('checked',true);
            el.find(".m_new").show();
            el.find(".m_basic").hide();
            if(d.title_date){
                el.find("input[name='title_date']").val(d.title_date);
			}
			if(d.title_author){
                el.find("input[name='title_author']").val(d.title_author);
			}
			if(d.link_title){
                el.find("input[name='link_title']").val(d.link_title);
			}
		}
        el.find('input[name="title_position"]').click(function () {
        	if($(this).val() == 1){
                el.find("#title_left input").prop("checked",true);
			}else{
                el.find("#title_center input").prop("checked",true);
            }
		})
		el.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link").val(url);
			});
		});
		el.find(".add_image").click(function(){
			var that = $(this);
			$.jImageManager({
				img_width:64,
				img_height:64,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item img").attr('src',path);
                    hasSaved = false;
                    that.find('.image').css("width","70px");
                    that.find('.image').css("height","70px");
					el.find(".del-has-image").show();
                    el.find(".add_image").css("background","none");
                    manager.change_show_module();
				}
			});
		});
		el.find(".del-has-image").click(function(){
            hasSaved = false;
			var is_del = $(".module_body .data_item img").removeAttr('src');
            el.find('.image').css("width","");
            el.find('.image').css("height","");
            el.find(".add_image").css("background","url(/image/admin/shop_beautify/add_decorete.png) no-repeat");
            el.find(".add_image").css("background-size","50%");
            el.find(".add_image").css("background-position","center 48%");
			if(is_del) el.find(".del-has-image").hide();
			manager.change_show_module();
		});
        el.on('input propertychange change','input',function () {
            manager.change_show_module();
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
		d.title_model = el.find("input[name='title_show']:checked").val();
		d.title_link = el.find("#title_link").val();
		d.tit_center=el.find("input[name='title_position']:checked").val();
		d.img_url = el.find("img").attr("src");
		d.color = el.find("input[name='font_color']").val();
        d.bg_color = el.find("input[name='bg_color']").val();
        d.title_date = el.find("input[name='title_date']").val();
        d.title_author = el.find("input[name='title_author']").val();
        d.link_title = el.find("input[name='link_title']").val();
		return d;
	}
};


var m_text = {
	init_ev_el: function(el,data){
	    if(data.title != undefined && data.title != ''){
		    el.find(" .text_module>span").text(data.title);
		}
        if(data.fonts_size != undefined){
			if(data.fonts_size == 1){
				el.find(".text_module>span").css('font-size','18px');
			}else if(data.fonts_size == 2){
				el.find(".text_module>span").css('font-size','14px');
			}else {
				el.find(".text_module>span").css('font-size','12px');
			}
	    }
        if(data.fonts_color != undefined){
            el.find(".text_module>span").css('color',data.fonts_color);
		}else{
            el.find(".text_module>span").css('color','#333333');
		}
        if(data.bgs_color != undefined){
            el.find(".text_module").css('background',data.bgs_color);
		}else{
            el.find(".text_module").css('background','#ffffff');
		}
        if(data.show_pos != undefined){
            if(data.show_pos == 1){
                el.find(".text_module").css('text-align','left');
            }else if(data.show_pos == 2){
                el.find(".text_module").css('text-align','center');
            }else {
                el.find(".text_module").css('text-align','right');
            }
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title").change(function () {
            manager.change_show_module();
        });
		if(d.fonts_size == 1){
			el.find('input[name="fonts_size"]').eq(0).prop('checked',true);
		}else if(d.fonts_size == 2 || d.fonts_size == undefined){
            el.find('input[name="fonts_size"]').eq(1).prop('checked',true);
		}else if(d.fonts_size == 3){
            el.find('input[name="fonts_size"]').eq(2).prop('checked',true);
		}
		if(d.fonts_color != undefined){
            el.find('input[name="fonts_color"]').val(d.fonts_color);
		}else{
            el.find('input[name="fonts_color"]').val('#333333');
		}
        if(d.bgs_color != undefined){
            el.find('input[name="bgs_color"]').val(d.bgs_color);
        }else{
            el.find('input[name="bgs_color"]').val('#ffffff');
        }
        if(d.show_pos == 1 || d.show_pos == undefined){
            el.find('input[name="show_pos"]').eq(0).prop('checked',true);
        }else if(d.show_pos == 2){
            el.find('input[name="show_pos"]').eq(1).prop('checked',true);
        }else if(d.show_pos == 3){
            el.find('input[name="show_pos"]').eq(2).prop('checked',true);
        }
		el.find('input[name="title_link"]').val(d.title_link);
        el.find('.select_links').click(function(){
            var that = $(this);
            hasSaved = false;
            show_links_dlg(function(url){
                that.prev().val(url);
            })
        })
        el.on('change',"input",function () {
            manager.change_show_module();
        })
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
		d.fonts_size = el.find('input[name="fonts_size"]:checked').val();
		d.fonts_color = el.find('input[name="fonts_color"]').val();
        d.bgs_color = el.find('input[name="bgs_color"]').val();
        d.show_pos = el.find('input[name="show_pos"]:checked').val();
        d.title_link = el.find('#title_link').val();
		return d;
	}
};



var m_single_image = {
	init_ev_el: function(el,data){
		if(data.title != undefined && data.title != ''){
			el.find(".app-field .row_content .goods-card .choice_layer_new").text(data.title);
            el.find(".choice_layer_new").removeClass("hide");
			if(data.title_link != undefined &&  data.title_link != ''){
				el.find("a").attr('href', data.title_link);
			}
		}else{
            el.find(".choice_layer_new").addClass("hide");
        }
		if(data.img_url){
			el.find(".image").attr("src",data.img_url);
		}
		
		if(data.title){
			el.find(".image").attr("src",data.img_url);
		}
				
		if(data.name){
				el.find(".app-field .row_content .bs_name:eq(0)").html(data.name);
			}
		if(data.price){
				el.find(".app-field .row_content .bs_price:eq(0)").html('￥'+data.price);
			}
		if(data.mk_price){
				el.find(".app-field .row_content .mk_price:eq(0)").html('￥'+data.mk_price);
			}
		if((data.name != undefined && data.name != '') && (data.price=="" || data.price == undefined) ){
				el.find(".app-field .row_content .bs_price:eq(0)").html("");
			}
		if(data.hide_name== '1'){
			el.find(".app-field .row_content .goods-title").addClass("hide");
		}else{
			el.find(".app-field .row_content .goods-title").removeClass("hide");
		}
        if(data.hide_price== '1'){
			el.find(".app-field .row_content .bs_price").addClass("hide");
			el.find(".app-field .row_content .mk_price").addClass("hide");
		}else{
			el.find(".app-field .row_content .bs_price").removeClass("hide");
			el.find(".app-field .row_content .mk_price").removeClass("hide");
		}		
	    },

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title_link").val(d.title_link);
		$(".module_body .data_item .price_value").val(d.price);
        $(".module_body .data_item .mk_price_val").val(d.mk_price);			
		$(".module_body .data_item .name_value").val(d.name);
		$(".module_body .data_item .goods_value").val(d.goods_id);
		if(d.img_url) {
				el.find(".image").attr("src",d.img_url);
				el.find(".add_image").css("width",'100px');
				el.find(".add_image").css("height",'50px');
				el.find(".image").css("width",'100px');
				el.find(".image").css("height",'50px');
            	el.find(".add_image").css("background",'none');
			};
		if(d.hide_name== '1')  el.find("#hide_goods_name input").attr("checked",true);
		if(d.hide_name== '1')  el.find(".hide_name").removeClass('hide');
		if(d.hide_price== '1')  el.find("#hide_goods_price input").attr("checked",true);
		if(d.hide_price== '1')  el.find(".hide_price").removeClass('hide');
		if(d.goods_id){
			$(".module_body .data_item .text_area").addClass("hide");
		    $(".module_body .data_item #title").val("");
			$(".module_body .data_item .hide_name").removeClass("hide");
			$(".module_body .data_item .hide_price").removeClass("hide");
		}
		el.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link").val(url);
			});
			// show_links_dlg(function(iframe){
			//
			// 	var tr = $("#data_list tr[data_row].active",iframe.document);
			// 	var name = tr.attr("name");
			//     $(".module_body .data_item .name_value").val(name);
			// 	var url = tr.attr("url");
			// 	$(".module_body .data_item #title_link").val(url);
			// 	var price = tr.attr("price");
			// 	$(".module_body .data_item .price_value").val(price);
			// 	var mk_price = tr.attr("z_price");
			// 	$(".module_body .data_item .mk_price_val").val(mk_price);
             //    var goods_id = tr.attr("goods_id");
             //    $(".module_body .data_item .goods_value").val(goods_id);
             //    if(goods_id=='' || goods_id== undefined){
			// 	   $(".module_body .data_item .text_area").removeClass("hide");
			// 	   el.find(".hide_name").addClass('hide');
			// 	   el.find(".hide_price").addClass('hide');
			// 	 }else{
			// 	   $(".module_body .data_item .text_area").addClass("hide");
			// 	   $(".module_body .data_item #title").val("");
			// 	   $(".module_body .data_item .hide_name").removeClass("hide");
			// 	   $(".module_body .data_item .hide_price").removeClass("hide");
			// 	}
			// },"iframe");
		});
		el.find(".add_image").click(function(){
			var that = $(this);
			$.jImageManager({
				img_width:600,
				img_height:300,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item img").attr('src',path);
                    hasSaved = false;
                    that.css("width",'100px');
                    that.css("height",'50px');
                    that.find(".image").css("width",'100px');
                    that.find(".image").css("height",'50px');
                    that.css("background","none");
                    manager.change_show_module();
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_single_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
        if(data.is_preview == 1){
            el.find('#yes').prop("checked","checked");
        }else{
            el.find('#no').prop("checked","checked");
        }
		if(data.title){
			$(".module_body .data_item .text_area").removeClass("hide");
		}
		
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		d.title_link = el.find("#title_link").val();
		d.img_url = el.find("img").attr("src");
		d.name=el.find(".name_value").val();
		d.price=el.find(".price_value").val();
		d.mk_price = el.find(".mk_price_val").val();
		d.goods_id=el.find(".goods_value").val();
		d.hide_name =el.find("#hide_goods_name input[type=checkbox]:checked").val();
		d.hide_price=el.find("#hide_goods_price input[type=checkbox]:checked").val();
        d.is_preview = el.find("input[name='if_preview']:checked").val();
		return d;
	}
};


var m_double_image = {
	init_ev_el: function(el,data){
		if(data.img_url1){
			el.find(".image_view:eq(0) img").attr("src",data.img_url1);
            el.find(".image_view:eq(0)").css("background","none");
            el.find(".image_view:eq(0)").css("background-color","#fff");
            el.find(".image_view:eq(0)").css("height","auto");
		}
		if(data.img_url2){
			el.find(".image_view:eq(1) img").attr("src",data.img_url2);
            el.find(".image_view:eq(1)").css("background","none");
            el.find(".image_view:eq(1)").css("background-color","#fff");
            el.find(".image_view:eq(1)").css("height","auto");
		}
		
		if(data.name1){
				el.find(".app-field .row_content .bs_head:eq(0)").html(data.name1);
			}
		if(data.price1){
				el.find(".app-field .row_content .bs_prix:eq(0)").html('￥'+data.price1);
			}
		if((data.name1 != undefined && data.name1 != '') && (data.price1=="" || data.price1 == undefined) ){
			    el.find(".app-field .row_content .bs_prix:eq(0)").html("");
		}
		
		if(data.name2){
				el.find(".app-field .row_content .bs_head:eq(1)").html(data.name2);
			}
		if(data.price2){
				el.find(".app-field .row_content .bs_prix:eq(1)").html('￥'+data.price2);
			}
		if((data.name2 != undefined && data.name2 != '') && (data.price2=="" || data.price2 == undefined)){
			    el.find(".app-field .row_content .bs_prix:eq(1)").html("");
		}
		if(data.mk_price1){
				el.find(".app-field .row_content .mk_price:eq(0)").html('￥'+data.mk_price1);
			}
		if(data.mk_price2){
				el.find(".app-field .row_content .mk_price:eq(1)").html('￥'+data.mk_price2);
			}
		if(data.hide_name== '1'){
			el.find(".app-field .row_content .goods-title").addClass("hide");
		}else{
			el.find(".app-field .row_content .goods-title").removeClass("hide");
		}
        if(data.hide_price== '1'){
			el.find(".app-field .row_content .goods-price").addClass("hide");
			el.find(".app-field .row_content .market_price").addClass("hide");
		}else{
			el.find(".app-field .row_content .goods-price").removeClass("hide");
			el.find(".app-field .row_content .market_price").removeClass("hide");
		}		
		
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		$(".module_body .data_item #title_link1").val(d.title_link1);
		$(".module_body .data_item #title_link2").val(d.title_link2);
		$(".module_body .data_item .price_value1").val(d.price1);	
		$(".module_body .data_item .name_value1").val(d.name1);
		$(".module_body .data_item .goods_value1").val(d.goods1);
		$(".module_body .data_item .mk_price_val1").val(d.mk_price1);
		$(".module_body .data_item .price_value2").val(d.price2);	
		$(".module_body .data_item .name_value2").val(d.name2);
		$(".module_body .data_item .goods_value2").val(d.goods2);
		$(".module_body .data_item .mk_price_val2").val(d.mk_price2);	
		if(d.hide_name== '1')  el.find("#hide_goods_name input").attr("checked",true);
		if(d.hide_name== '1')  el.find(".hide_name").removeClass('hide');
		if(d.hide_price== '1')  el.find("#hide_goods_price input").attr("checked",true);
		if(d.hide_price== '1')  el.find(".hide_price").removeClass('hide');
		if(d.img_url1) {
			el.find(".image1").attr("src",d.img_url1);
            el.find(".add_image1").css("width","90px");
            el.find(".add_image1").css("height","70px");
            el.find(".add_image1").css("background","none");
            el.find(".image1").css("width","90px");
            el.find(".image1").css("height","70px");
		}
		if(d.img_url2) {
            el.find(".image2").attr("src",d.img_url2);
            el.find(".add_image2").css("width","90px");
            el.find(".add_image2").css("height","70px");
            el.find(".add_image2").css("background","none");
            el.find(".image2").css("width","90px");
            el.find(".image2").css("height","70px");
		}
		if(d.goods2 || d.goods2){
			el.find(".hide_name").removeClass("hide");
		    el.find(".hide_price").removeClass("hide");
		}
		el.find(".select_links1").click(function(){
            hasSaved = false;
            show_links_dlg(function(url){
                $(".module_body .data_item #title_link1").val(url);
            });
			// show_links_dlg(function(iframe){
			// 	var tr = $("#data_list tr[data_row].active",iframe.document);
			// 	var name = tr.attr("name");
			//     $(".module_body .data_item .name_value1").val(name);
			// 	var url = tr.attr("url");
			// 	$(".module_body .data_item #title_link1").val(url);
			// 	var price = tr.attr("price");
			//     $(".module_body .data_item .price_value1").val(price);
			// 	var mk_price1 = tr.attr("z_price");
			// 	$(".module_body .data_item .mk_price_val1").val(mk_price1);
             //    var goods_id = tr.attr("goods_id");
             //    $(".module_body .data_item .goods_value1").val(goods_id);
             //    if(goods_id=='' || goods_id== undefined){
			// 	   el.find(".hide_name").addClass('hide');
			// 	   el.find(".hide_price").addClass('hide');
			// 	}else{
			// 	   el.find(".hide_name").removeClass("hide");
			// 	   el.find(".hide_price").removeClass("hide");
			// 	}
			// },"iframe");
					
		});
		el.find(".select_links2").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link2").val(url);
			});
			// show_links_dlg(function(iframe){
			//
			// 	var tr = $("#data_list tr[data_row].active",iframe.document);
			// 	var name = tr.attr("name");
			//     $(".module_body .data_item .name_value2").val(name);
			// 	var url = tr.attr("url");
			// 	$(".module_body .data_item #title_link2").val(url);
			// 	var price = tr.attr("price");
			//     $(".module_body .data_item .price_value2").val(price);
             //    var mk_price2 = tr.attr("z_price");
			// 	$(".module_body .data_item .mk_price_val2").val(mk_price2);
			// 	var goods_id = tr.attr("goods_id");
             //    $(".module_body .data_item .goods_value2").val(goods_id);
			// 	if(goods_id=='' || goods_id== undefined){
			// 	   el.find(".hide_name").addClass('hide');
			// 	   el.find(".hide_price").addClass('hide');
			// 	}else{
			// 	   el.find(".hide_name").removeClass("hide");
			// 	   el.find(".hide_price").removeClass("hide");
			// 	}
			// },"iframe");
		});
		el.find(".add_image1").click(function(){
			var that = $(this);
			$.jImageManager({
				// img_width:298,
				// img_height:233,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item .image1").attr('src',path);
                    hasSaved = false;
                    that.css("width","90px");
                    that.css("height","70px");
                    that.find(".image1").css("width","90px");
                    that.find(".image1").css("height","70px");
                    that.css("background","none");
                    manager.change_show_module();
				}
			});
		});
		el.find(".add_image2").click(function(){
			var that = $(this);
			$.jImageManager({
				// img_width:298,
				// img_height:233,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item .image2").attr('src',path);
                    hasSaved = false;
                    that.css("width","90px");
                    that.css("height","70px");
                    that.find(".image2").css("width","90px");
                    that.find(".image2").css("height","70px");
                    that.css("background","none");
                    manager.change_show_module();
				}
			});
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_double_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
        if(data.is_preview == 1){
            el.find('#yes').prop("checked","checked");
        }else{
            el.find('#no').prop("checked","checked");
        }
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
		d.name1=el.find(".name_value1").val();
		d.price1=el.find(".price_value1").val();
		d.goods1=el.find(".goods_value1").val();
        d.name2=el.find(".name_value2").val();
		d.price2=el.find(".price_value2").val();
        d.goods2=el.find(".goods_value2").val();
        d.hide_name =el.find("#hide_goods_name input[type=checkbox]:checked").val();
		d.hide_price=el.find("#hide_goods_price input[type=checkbox]:checked").val();
        d.mk_price1 = el.find(".mk_price_val1").val();
        d.mk_price2 = el.find(".mk_price_val2").val();
        d.is_preview = el.find("input[name='if_preview']:checked").val();
       	return d;
	}
};

var m_multi_image = {
    init_ev_el: function(el,data){
		el.find(".title span").text(data.title);
		if(data.title){
            el.find(".title span").text(data.title);
            el.find('.m_multi_image_title').show();
		}else{
			el.find('.m_multi_image_title').hide();
		}
		if(data.img_items){
			for(var i in data.img_items){
				var t  = data.img_items[i];
				if(t.img_url) el.find("td:eq("+i+") img").attr("src",t.img_url);
                /*if(t.name)  $(".row_item .bs_caption:eq("+i+")").html(t.name);
				if(t.price) $(".row_item .bs_rate:eq("+i+")").html('￥'+t.price);
				if((t.name != undefined && t.name != '') && (t.price=="" || t.price == undefined)){
					$(".row_item .bs_rate:eq("+i+")").html("");
				}*/
				
			}
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find(".image_row_item").remove();
		for(var i=0;i<6;i++){
			if(i<3 || d.img_items && d.img_items[i]){
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
        el.find("#title").change(function () {
            manager.change_show_module();
        })
		// console.log(d.cur_idx);
		// console.log(d.title);
        // $(".m_multi_image_title").each(function () {
        	// // console.log($(this).parent().attr("cur_idx"));
         //    if( ($(this).parent().attr("cur_idx") == d.cur_idx && d.title == '') || $(this).parent().attr("cur_idx") == d.cur_idx && d.title == undefined){
         //        $(this).addClass("hide");
         //    }else {
         //        $(this).removeClass("hide");
         //    }
        // })
	},
	add_image_row_item: function(el,d,i){
		if(d == undefined) d = {};
		//alert(i);
		var el_row = $("#template_list .d_m_multi_image .image_row_item").clone();
		el_row.appendTo(el.find(".multi_image_tbl"));
		if(d.img_url) {
            el_row.find(".image").attr('src',d.img_url);
            el_row.find(".add_image").css("width","100px");
            el_row.find(".add_image").css("height","60px");
            el_row.find(".image").css("width","100px");
            el_row.find(".image").css("height","60px");
            el_row.find(".jiahao").css("display","none");
		}
		el_row.find("#title_link").val(d.title_link);
		/*if(d.name) el_row.find(".name_worth").val(d.name);
		if(d.price) el_row.find(".price_worth").val(d.price);
		if(d.goods_id) el_row.find(".goods_val").val(d.goods_id);*/
		el_row.find(".add_image").click(function(){
			var that = $(this);
			$.jImageManager({
				img_width:250,
				img_height:150,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					el_row.find(".image").attr('src',path);
                    hasSaved = false;
                    that.css("width","100px");
                    that.css("height","60px");
                    el_row.find(".image").css("width","100px");
                    el_row.find(".image").css("height","60px");
                    el_row.find(".jiahao").css("display","none");
                    manager.change_show_module();
				}
			});
		});
		el_row.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				el_row.find("#title_link").val(url);
			});
			// show_links_dlg(function(iframe){
			//
			// 	var tr = $("#data_list tr[data_row].active",iframe.document);
			// 	var url = tr.attr("url");
			// 	el_row.find("#title_link").val(url);
			// 	/*var name = tr.attr("name");
			// 	el_row.find(".name_worth").val(name);
			// 	var price = tr.attr("price");
			//     el_row.find(".price_worth").val(price);
			// 	var goods_id = tr.attr("goods_id");
			// 	el_row.find(".goods_val").val(goods_id);*/
			// },"iframe");
		});
		el_row.find(".up_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var pre = p.prev(".image_row_item");
			if(pre.length > 0){
				pre.insertAfter(p);
			}
			manager.change_show_module();
		});
		el_row.find(".down_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var next = p.next(".image_row_item");
			if(next.length > 0){
				next.insertBefore(p);
			}
            manager.change_show_module();
		});
		el_row.find(".remove").click(function(){
			var p = $(this).parents(".multi_image_tbl");
            hasSaved = false;
			if(p.find(".image_row_item").length <= 3){
				return;
			} else {
                $(this).parents(".image_row_item").remove();
                manager.change_show_module();
			}
		});
		el_row.appendTo(el.find(".multi_image_tbl"));
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_multi_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
        if(data.is_preview == 1){
            el.find('#yes').prop("checked","checked");
        }else{
            el.find('#no').prop("checked","checked");
        }
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
        d.is_preview = el.find("input[name='if_preview']:checked").val();
		d.img_items = [];
		el.find(".image_row_item").each(function(){
			d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val()
			});
			/*,'name':$(this).find(".name_worth").val(),'price':$(this).find(".price_worth").val()
			,'goods_id':$(this).find(".goods_val").val()*/
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
			var h = 100;  //初始图片高度
			var w = 314;  //图片充满宽度
			var tp = 0;   //宽高比例
			for(var i in data.img_items){
				var t  = data.img_items[i];
				// console.log(t);
				if(t.img_url) {
					//var img = $("#template_list .m_scroll_image .scroll_image_view img").clone();
					// var img = $(el).find(".scroll_image_view img").clone();
					// img.appendTo(el.find(".scroll_image_view"));
					// //var span = $("#template_list .m_scroll_image .scroll_image_dot_nav span").clone();
					// var span = $(el).find(".scroll_image_dot_nav span").clone();
					// span.appendTo(el.find(".scroll_image_dot_nav"));
					// img.attr("src",t.img_url);
					$(el).find(".scroll_image_view img:first-child").attr("src", t.img_url);
					var src_width = t.src_width;
					var src_height = t.src_height;
					// console.log(src_width+"--"+src_width);
                    var temp = parseFloat(src_height/src_width);
                    // console.log("temp="+ temp);
					if(temp > tp){
						tp = temp;
					}
                    $(el).find(".scroll_image_view img").css("width","100%");
                    $(el).find(".scroll_image_view .contain_circle").find('div').addClass("hide");
                    $(el).find(".scroll_image_view .small_circle6").removeClass("hide");
					idx++;
					if (idx == 2) {
                        $(el).find(".scroll_image_view .small_circle3").removeClass("hide");
                    } else if (idx == 3) {
                        $(el).find(".scroll_image_view .small_circle3").removeClass("hide");
                        $(el).find(".scroll_image_view .small_circle2").removeClass("hide");
                    } else if (idx == 4) {
                        $(el).find(".scroll_image_view .small_circle3").removeClass("hide");
                        $(el).find(".scroll_image_view .small_circle2").removeClass("hide");
                        $(el).find(".scroll_image_view .small_circle1").removeClass("hide");
                    } else if (idx == 5) {
						$(el).find(".scroll_image_view .small_circle5").removeClass("hide");
						$(el).find(".scroll_image_view .small_circle3").removeClass("hide");
						$(el).find(".scroll_image_view .small_circle2").removeClass("hide");
						$(el).find(".scroll_image_view .small_circle1").removeClass("hide");
					} else if (idx == 6) {
						$(el).find(".scroll_image_view .small_circle6").removeClass("hide");
						$(el).find(".scroll_image_view .small_circle5").removeClass("hide");
						$(el).find(".scroll_image_view .small_circle3").removeClass("hide");
						$(el).find(".scroll_image_view .small_circle2").removeClass("hide");
						$(el).find(".scroll_image_view .small_circle1").removeClass("hide");
					}
					//var h = $(el).find(".scroll_image_view img:first-child").height();
					var h = parseFloat(tp * w);
                    // console.log("tp="+ tp +",h= "+h);
				}
				if(h!==0){
					$(el).find(".scroll_image_module").height(h);
				}else{
                    $(el).find(".scroll_image_module").height("150px");
				}
			}
			// if(this.timer) clearInterval(this.timer);
			// if(idx > 0){
			// 	el.find(".scroll_image_view img:eq(0)").addClass("active");
			// 	el.find(".scroll_image_dot_nav span:eq(0)").addClass("nav_active");
			// 	this.timer = setInterval(function(){
			// 		var i = el.find(".scroll_image_view img.active").index();
			// 		i = (i+1) % idx;
			// 		el.find(".scroll_image_view img").removeClass("active");
			// 		el.find(".scroll_image_dot_nav span").removeClass("nav_active");
			// 		el.find(".scroll_image_view img:eq("+i+")").addClass("active");
			// 		el.find(".scroll_image_dot_nav span:eq("+i+")").addClass("nav_active");
			// 	},10000);
			// }
		   // if($(el).find(".scroll_image_view img:first-child").attr("src", t.img_url);)
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find(".image_row_item").remove();
		// for(var i=0;i<6;i++){
		// 	if(i<1 || d.img_items && d.img_items[i]){
		// 		var t = d.img_items ? (d.img_items[i] ? d.img_items[i] : {}) : {};
		// 		this.add_image_row_item(el,t);
		// 	}
		// }
		if(d.img_items && d.img_items.length >= 0){
            for (var i in d.img_items) {
                this.add_image_row_item(el,d.img_items[i]);
            }
        }
		el.find(".add_row_item").click(function(){
			if(el.find(".image_row_item").length >= 6){
                util.mobile_alert("列表超过上限!");
				return;
			}
			$.jImageManager({
				max_img_num:0,
                ok_cb:function(img_arr){
					if(img_arr.length > 6 - el.find(".image_row_item").length){
						util.mobile_alert("列表超过上限!");
						return false;
					} else {
						for(let i = 0; i < img_arr.length;i++){
							let path = img_arr[i].img_url ?  img_arr[i].img_url : img_arr[i].url;
							m_scroll_image.add_image_row_item(el,{img_url: path,src_width:img_arr[i].img_width,src_height:img_arr[i].img_height});
						}
					}
                    manager.change_show_module();
                    hasSaved = false;
                }
            });
		});
	},
	add_image_row_item: function(el,d){
		if(d == undefined) d = [];
		var el_row = $("#template_list .d_m_scroll_image .image_row_item").clone();
		el_row.appendTo(el.find(".scroll_image_tbl"));
		if(d.img_url){
            el_row.find(".image").attr('src',d.img_url);
            el_row.find(".add_image").css("width","108.5px");
            el_row.find(".add_image").css("height","35px");
            el_row.find(".image").css("width","108.5px");
            el_row.find(".image").css("height","35px");
            el_row.find(".image").attr('src_width',d.src_width);
            el_row.find(".image").attr('src_height',d.src_height);
            el_row.find(".jiahao").css("display","none");
		}
		el_row.find("#title_link").val(d.title_link);

		el_row.find(".add_image").click(function(){
			var that = $(this);
			$.jImageManager({
				// img_width:620,
				// img_height:310,
				ok_cb:function(img_arr){
                    console.log(img_arr);
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					el_row.find(".image").attr('src',path);
                    el_row.find(".image").attr('src_width',img_arr[0].img_width);
                    el_row.find(".image").attr('src_height',img_arr[0].img_height);
                    hasSaved = false;
                    that.find(".image").css("width","108.5px");
                    that.find(".image").css("height","35px");
                    that.css("width","108.5px");
                    that.css("height","35px");
                    that.find(".jiahao").css("display","none");
                    manager.change_show_module();
				}
			});
		});
		el_row.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				el_row.find("#title_link").val(url);
			});
		});
		el_row.find(".up_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var pre = p.prev(".image_row_item");
			if(pre.length > 0){
				pre.insertAfter(p);
                manager.change_show_module();
			}
		});
		el_row.find(".down_arrow").click(function(){
            hasSaved = false;
			var p = $(this).parents(".image_row_item");
			var next = p.next(".image_row_item");
			if(next.length > 0){
				next.insertBefore(p);
                manager.change_show_module();
			}
		});
		el_row.find(".remove").click(function(){
			var p = $(this).parents(".scroll_image_tbl");
			// if(p.find(".image_row_item").length <= 1){
			// 	return;
			// } else {
                $(this).parents(".image_row_item").remove();
                manager.change_show_module();
			// }
		});
		el_row.appendTo(el.find(".scroll_image_tbl"));
	},
	show_edit_el:function(data){
		var el = $("#template_list .d_m_scroll_image").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		if(data.is_preview == 1){
			el.find('#yes').prop("checked","checked");
		}else{
            el.find('#no').prop("checked","checked");
		}
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.img_items = [];
		d.is_preview = el.find("input[name='if_preview']:checked").val();
		el.find(".image_row_item").each(function(){
			d.img_items.push({img_url:$(this).find(".image").attr("src"),'title_link':$(this).find("#title_link").val(),'src_width':$(this).find(".image").attr("src_width"), 'src_height':$(this).find(".image").attr("src_height")});
		});
		console.log(d);
		return d;
	}
};


// 辅助空白模块
var m_blank = {
	get_valid_height: function(h){
		h = parseInt(h);
		if(isNaN(h)) h = 10;
		if(h < 10) h = 10;
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
		    console.log(e)
			if(!(e.keyCode == 0x09 || e.keyCode == 0x08 || e.keyCode == 37 || e.keyCode == 39
				|| e.keyCode >= 0x30 && e.keyCode <= 0x39)) return false;
		});
		el.find("#blank_height").keyup(function(e){

			var v = parseInt($(this).val());
            console.log(v)
			if(!isNaN(v)){
                console.log(v)
				if(v<10) v = 10;
				if(v>60) v = 60;
				var ell = $("#drag_area div.row_item.item_selected");
				ell.find(".m_blank_module").css("height",v + "px");
			}
		});
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_blank").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
		console.log('asdfasdfa')
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
		//UE.getEditor("editor").setContent(d.rich_text);
        window.keditor.html(d.rich_text);
        el.find('#editor').change(function () {
            console.log($('#editor').val())
        })

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
		//d.rich_text = UE.getEditor("editor").getContent();
        window.keditor.sync();
        d.rich_text = $('#editor').val();
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
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item .image1").attr('src',path);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image2").click(function(){
			$.jImageManager({
				img_width:88,
				img_height:88,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item .image2").attr('src',path);
                    hasSaved = false;
				}
			});
		});
		el.find(".add_image3").click(function(){
			$.jImageManager({
				img_width:127,
				img_height:34,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item .image3").attr('src',path);
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
			if(el.find(".image").attr("src") != ""){
                el.find(".image").parent().find("p").css("display","none");
                el.find(".image").parent().css("background","#fff");
			}
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title_link").val(d.title_link);
		if(d.img_url){
            el.find(".image").attr("src",d.img_url);
            el.find(".image").css("width","70px");
		}
		el.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link").val(url);
			});
		});
		el.find(".add_image").click(function(){
			$.jImageManager({
				// img_width:630,
				// img_height:630,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					$(".module_body .data_item .image").attr('src',path);
                    hasSaved = false;
					el.find(".add_image").css("display","none");
					el.find(".del_icon").css("display","block");
					manager.change_show_module();
					/*$(".single_img p").css("display","none");*/
				}
			});
		});
		el.find(".del_icon").click(function () {
            el.find(".add_image").css("display","block");
            $(".module_body .data_item .image").attr('src','');
            hasSaved = false;
            el.find(".del_icon").css("display","none");
            el.find(".image").css("width","");
            manager.change_show_module();
        })

        el.find("#title").change(function () {
            manager.change_show_module();
        })
	},

	show_edit_el:function(data){
		var el = $("#template_list .d_m_image_small").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
        if(data.is_preview == 1){
            el.find('#yes').prop("checked","checked");
        }else{
            el.find('#no').prop("checked","checked");
        }
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		d.title_link = el.find("#title_link").val();
		d.img_url = el.find("img").attr("src");
        d.is_preview = el.find("input[name='if_preview']:checked").val();
		return d;
	}
};

var m_image_guide = {
	init_ev_el: function(el,data){

        m_image_guide.add_img_nav(2,data);
	},

    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.nav_group != undefined) {
            var group = d.nav_group;
            if (group.length == 5){
                var e = $('#template_list .edit_guideimg_modules .clone_one').clone(true);
                el.find(".ul-sortable").append(e);
            }
            el.find(".choice_con").each(function(i){
				if(d.nav_group[i]){
					$(this).val(d.nav_group[i].nav_name);
				}
            });
            el.find(".choice_url").each(function(i){
				if(d.nav_group[i]){
					$(this).val(d.nav_group[i].nav_link);
				}
            });
            el.find(".choice_ach .image1").each(function (i) {
				if(d.nav_group[i]){
					$(this).attr('src',d.nav_group[i].nav_src);
				}
            })
		}else{
            var origin_arr = [{group_name:'导航一'},{group_name:'导航二'},{group_name:'导航三'},{group_name:'导航四'}];
            el.find(".choice_con").each(function(i){
                $(this).val(origin_arr[i].group_name);
            });
		}

		if(d.nav_style != undefined){
            el.find(".guide_nav_style").removeClass("guide_select");
            if(d.nav_style == 1){
                el.find(".guide_circle").addClass('guide_select');
            }else{
                el.find(".guide_rect").addClass('guide_select');
            }
        }
        if(d.font_color != undefined){
            el.find("input[name='fonts_color']").val(d.font_color);
        }
        if(d.bg_color != undefined){
            el.find("input[name='bgs_color']").val(d.bg_color);
        }
        el.on('input propertychange','input',function () {
            m_image_guide.add_img_nav();
        });
        el.find('.guide_nav_style').click(function (e) {
            el.find(".guide_nav_style").removeClass('guide_select');
            $(this).addClass('guide_select');
            manager.change_show_module();
        });
        el.find('[class^="sortable_btn select_links"]').click(function(){
            var that = $(this);
            hasSaved = false;
            show_links_dlg(function(url){
                that.prev().val(url);
            })
        })
        el.on('click','.ab_choice .up_img',function (e) {
            var parent = $(this).parent().parent();
            var pre = parent.prev();
            pre.insertAfter(parent);
            m_image_guide.add_img_nav();
            e.preventDefault();
        })
        el.on('click','.ab_choice .down_img',function (e) {
            var parent = $(this).parent().parent();
            var pre = parent.next();
            pre.insertBefore(parent);
            m_image_guide.add_img_nav();
            e.preventDefault();
        });
        el.on('click','.ab_choice .del_img',function (e) {
            hasSaved = false;
            var _this = $(this);
            var ch = el.find(".choice");
            if(ch.length <= 1 ){
                util.mobile_alert('列表不能小于1个！');
                return false;
            }else{
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                        title: ['提醒', 'text-align:center;padding: 0px;']
                        , area: '260px'
                        , closeBtn: 0
                        , btn: ['确定', '取消']
                    }, function (ind) {
                        hasSaved = false;
                        _this.parent().parent().remove();
                        m_image_guide.add_img_nav();
                        layer.close(ind);
                    });
                });
            }
            e.preventDefault();
        });
        el.on('click','.choice-image .add-image',function (e) {
            var that = $(this);
			$.jImageManager({
				img_width:140,
				img_height:140,
				ok_cb:function(img_arr){
					var path = img_arr[0].img_url;
					if (path == undefined) {
						path = img_arr[0].url;
					}
					that.find('.image1').attr('src',path);
					hasSaved = false;
					that.css("background","none");
					m_image_guide.add_img_nav();
					manager.change_show_module();
				}
			});
        });
        el.find(".add_row_item").click(function(){
        	var ch = el.find(".choice");
        	if(ch.length >= 5 ){
        		util.mobile_alert('列表超过上限！');
        		return false;
			}else{
				$.jImageManager({
					max_img_num:0,
					img_width:140,
					img_height:140,
					ok_cb:function(img_arr){
						if(img_arr.length > 5 - el.find(".ul-sortable li").length){
							util.mobile_alert('列表超过上限');
							return false;
						} else {
							for(let i = 0; i < img_arr.length;i++){
								let path = img_arr[i].img_url ?  img_arr[i].img_url : img_arr[i].url;
								var e = $('#template_list .edit_guideimg_modules .clone_one').clone(true);
								e.find('.image1').attr('src',path);
								hasSaved = false;
								el.find(".ul-sortable").append(e);
								m_image_guide.add_img_nav();
								manager.change_show_module();
							}
						}
					}
				});
			}
            m_image_guide.add_img_nav();
        });

        // el.on('change',"input",function () {
        //     manager.change_show_module();
        // })
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
		var nav_group = [];
		el.find(".ul-sortable .choice").each(function (i) {
            nav_group[i] = {
                'nav_name':$(this).find(".choice_con").val(),
                'nav_link':$(this).find(".choice_url").val(),
				'nav_src':$(this).find(".image1").attr('src') ,
            }
        })
        d.nav_style = el.find(".guide_select").attr('module-guide');
		d.font_color = el.find("input[name='fonts_color']").val();
		d.bg_color = el.find("input[name='bgs_color']").val();
        d.nav_group = nav_group;
        console.log(d);
        return d;
	},
    add_img_nav:function(type = 1,data = ''){
        if(type == 1){
            var cur_m = manager.get_cur_edit_module();
            if (!cur_m) return false;
            var cur_data = cur_m.get_data();
        }else{
            var cur_data = data;
        }
        var origin_arr = [{group_name:'导航一'},{group_name:'导航二'},{group_name:'导航三'},{group_name:'导航四'}];
        var e = manager.get_item_el(cur_data.cur_idx);
        e.find(".content_img_row .image_view_").css('display','none');
        if(cur_data.nav_group != undefined){
            var nav_group = cur_data.nav_group;
            var count = 0;
			for(var i = 0;i < nav_group.length;i++){
			    if((nav_group[i].nav_name).trim() == ''){
                    count ++;
                }
                e.find(".content_img_row .image_view_  .guide_nodel img").eq(i).attr('src','');
				e.find(".content_img_row .image_view_").eq(i).css('display','block');
				e.find(".content_img_row .image_view_  .title_set").eq(i).text(nav_group[i].nav_name);
				e.find(".content_img_row .image_view_  .guide_nodel img").eq(i).attr('src',nav_group[i].nav_src);
			}
			if(count == nav_group.length){
			    e.find(".title_set").hide();
            }else{
                e.find(".title_set").show();
            }
            if(cur_data.nav_style == 1){
                e.find(".image_view_ .guide_nodel").addClass('style_circle');
                e.find(".image_view_ .guide_nodel img").addClass('style_circle');
            }else{
                e.find(".image_view_ .guide_nodel").removeClass('style_circle');
                e.find(".image_view_ .guide_nodel img").removeClass('style_circle');
            }
            e.find(".title_set").css('color',cur_data.font_color);
            e.find(".content_img_row").css('background',cur_data.bg_color);
		}else{
            e.find(".image_view_ .guide_nodel").addClass('style_circle');
            e.find(".image_view_ .guide_nodel img").addClass('style_circle');
            for(var i = 0;i < 4;i++){
                e.find(".content_img_row .image_view_").eq(i).css('display','block');
                e.find(".content_img_row .image_view_  .title_set").eq(i).text(origin_arr[i].group_name);
            }
		}
    },
};

var m_image_adver = {
    init_ev_el: function(el,data){
        // console.log(data);
        if (data.image_list && data.image_list.length > 0) {
            if (data.image_type == 4 && data.module_title) {
                el.find('.slide_nav_title').html(data.module_title).css('display','');
            } else {
                el.find('.slide_nav_title').css('display','none');
            }
            el.find('.adver_image_module_small').addClass('hide');
            var image_type_name = '';
            if(data.image_type == 1){
                image_type_name = 'twice_img';
            } else if(data.image_type == 2){
                image_type_name = 'slide_big';
            } else if(data.image_type == 3){
                image_type_name = 'slide_small';
            } else if(data.image_type == 4){
                image_type_name = 'slide_nav';
            } else {
                image_type_name = 'once_image_area';
            }
            el.find('.new_image_type').addClass('hide');
            el.find('.' + image_type_name).removeClass('hide');
            el.find('.image_show').remove();
            for (var i in data.image_list){
                var image_model = el.find('.' + image_type_name + '_model').clone().css('display','')
                    .removeClass(image_type_name + '_model').addClass('image_show').addClass('img_index_' + i);
                image_model.find("img").attr('src',data.image_list[i].image);
                if (data.image_list[i].title) {
                    image_model.find('.title').html(data.image_list[i].title).css('display','');
                } else {
                    image_model.find('.title').css('display','none');
                }
                el.find('.' + image_type_name).append(image_model);
            }
			this.set_image_spacing(el,data)
        }
    },
    set_image_spacing: function (el,data) {
    	var image_length = data.image_list.length;
        if(data.image_type == 1){
            var left_class_ids  = '';
            var right_class_ids  = '';
            for (var i = 0; i < image_length; i ++) {
                if (i%2 == 0) {
                	if (i + 3 >  image_length) {
                        left_class_ids += '.img_index_' + i;
					} else {
                        left_class_ids += '.img_index_' + i + ', ';
					}
				} else {
                	if (i + 3 > image_length) {
                        right_class_ids += '.img_index_' + i;
					} else {
                        right_class_ids += '.img_index_' + i + ', ';
					}
					// 同行取最大高
                    let left_ratio = data.image_list[i-1].height / data.image_list[i-1].width;
                    let right_ratio = data.image_list[i].height / data.image_list[i].width;
                    let big_ratio = left_ratio > right_ratio ? left_ratio : right_ratio;
                    let big_height = (el.find('.img_index_' + (i-1)).width() - data.image_space/2) * big_ratio;
                    // console.log(big_height);
                    el.find('.img_index_' + (i-1) + ',.img_index_' + i).css('height',big_height + 'px')
				}
				if (i < 2) el.find('.img_index_' + i).css('margin-top',data.image_space + 'px');
            }
            el.find(left_class_ids).css('padding-right', data.image_space/2 + 'px');
            el.find(right_class_ids).css('padding-left', data.image_space/2 + 'px');
            el.find(left_class_ids).css('margin-bottom', data.image_space + 'px');
            el.find(right_class_ids).css('margin-bottom', data.image_space + 'px');
        } else if(data.image_type == 2 || data.image_type == 3 || data.image_type == 4){
            var class_ids = '';
            for (var i = 0; i < image_length - 1; i ++) {
                if (i == image_length - 2) {
                    class_ids += '.img_index_' + i;
                } else {
                    class_ids += '.img_index_' + i + ', ';
                }
            }
            // console.log(class_ids)
            el.find(class_ids).css('margin-right', data.image_space + 'px');
        } else {
            var class_ids = '';
            for (var i = 0; i < image_length; i ++) {
                if (i == image_length - 1) {
                    class_ids += '.img_index_' + i;
				} else {
                    class_ids += '.img_index_' + i + ', ';
				}
            }
            // console.log(class_ids)
            el.find('.img_index_0').css('margin-top', data.image_space + 'px');
            el.find(class_ids).css('margin-bottom', data.image_space + 'px');

        }
    },
    add_image_area:function (el,image_data) {
        if (!image_data) image_data = [];
        var image_area = el.find('.img_area_model').clone().show().addClass('new_image_area').removeClass('img_area_model');
        if(image_data.image) image_area.find(".image").attr('src',image_data.image);
        if(image_data.image) image_area.find(".image").attr('img_width',image_data.width);
        if(image_data.image) image_area.find(".image").attr('img_height',image_data.height);
        if(image_data.title) image_area.find('[name="title"]').val(image_data.title);
        if(image_data.link) image_area.find('[name="title_link"]').val(image_data.link);

        image_area.find(".select_links").click(function(){
            hasSaved = false;
            var select_links = $(this);
            show_links_dlg(function(url){
                select_links.parent().find('[name="title_link"]').val(url);
            });
        });
        image_area.find(".add_image").click(function(){
            var _image = $(this);
            $.jImageManager({
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    _image.find("img").attr('src',path);
                    _image.find("img").attr('img_width',img_arr[0].img_width);
                    _image.find("img").attr('img_height',img_arr[0].img_height);
                    manager.change_show_module();
                    hasSaved = false;
                }
            });
        });
        image_area.find("input[name='title']").change(function () {
            manager.change_show_module();
        });
        image_area.find(".up_arrow").click(function(){
            hasSaved = false;
            var p = $(this).parents(".new_image_area");
            var pre = p.prev(".new_image_area");
            if(pre.length > 0){
                pre.insertAfter(p);
                manager.change_show_module();
            }
        });
        image_area.find(".down_arrow").click(function(){
            hasSaved = false;
            var p = $(this).parents(".new_image_area");
            var next = p.next(".new_image_area");
            if(next.length > 0){
                next.insertBefore(p);
                manager.change_show_module();
            }
        });
        image_area.find(".remove").click(function(){
            var p = $(this).parents(".new_image_list");
            if(p.find(".new_image_area").length <= 1){
                return;
            } else {
                $(this).parents(".new_image_area").remove();
                manager.change_show_module();
            }
        });
        el.find('.new_image_list').append(image_area);
    },

    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        el.find(".image-type").eq(d.image_type ? parseInt(d.image_type) : 0).addClass('actives');
        el.find("input[name='if_preview']").eq(d.is_preview ? parseInt(d.is_preview) : 0).prop('checked',true);
        el.find("input[name='image_space']").val(d.image_space);
        if (d.image_type == 4){
            el.find(".module_title").show();
            el.find("input[name='module_title']").val(d.module_title);
        } else {
            el.find(".module_title").hide();
        }
        var advise_size_list = [750,375,670,305,142];
        el.find(".advise_size_int").html(advise_size_list[d.image_type ? parseInt(d.image_type) : 0]);
        var _this = this;
        if(d.image_list && d.image_list.length >= 0){
            for (var i in d.image_list) {
                _this.add_image_area(el,d.image_list[i]);
            }
        }

        el.find(".image-type").click(function () {
            el.find(".image-type").removeClass('actives');
            $(this).addClass('actives');
            if ($(this).attr('image-type') == 4){
                el.find(".module_title").css('display','');
            } else {
                el.find(".module_title").css('display','none');
            }
            var advise_size_list = [750,375,670,305,142];
            el.find(".advise_size_int").html(advise_size_list[$(this).attr('image-type') ? parseInt($(this).attr('image-type')) : 0]);
            manager.change_show_module();
        });

        el.find(".add_img_area").click(function () {
			let new_data = _this.get_data();
			if (new_data.image_list.length >= 10) {
				util.mobile_alert('该模块最多添加10张图片');
				return false;
			}
            $.jImageManager({
				max_img_num:0,
                ok_cb:function(img_arr){
					if(img_arr.length > 10 - new_data.image_list.length){
						util.mobile_alert('该模块最多添加10张图片');
						return false;
					} else {
						for(let i = 0; i < img_arr.length;i++){
							let path = img_arr[i].img_url ?  img_arr[i].img_url : img_arr[i].url;
							_this.add_image_area(el,{image: path,width:img_arr[i].img_width,height:img_arr[i].img_height});
						}
					}
                    manager.change_show_module();
                    hasSaved = false;
                }
            });
        });

        // el.find(".del_icon").click(function () {
        //     el.find(".add_image").css("display","block");
        //     $(".module_body .data_item .image").attr('src','');
        //     hasSaved = false;
        //     el.find(".del_icon").css("display","none");
        //     el.find(".image").css("width","");
        // })

        el.find("input").change(function () {
            manager.change_show_module();
        })
    },

    show_edit_el:function(data){
        var el = $("#template_list .d_m_image_adver").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        if(data.is_preview == 1){
            el.find('#yes').prop("checked","checked");
        }else{
            el.find('#no').prop("checked","checked");
        }
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.image_type = el.find(".actives").attr('image-type');
        d.is_preview = el.find("input[name='if_preview']:checked").val();
        if(el.find("input[name='image_space']").val() > 20){
        	util.mobile_alert("图片间隙尺寸最大为20");
            el.find("input[name='image_space']").val("");
		}
        d.image_space = el.find("input[name='image_space']").val();
        d.module_title = el.find("input[name='module_title']").val();
        d.image_list = [];
        el.find(".new_image_area").each(function () {
            if ($(this).find(".image").attr('src')) {
                d.image_list.push({
                    image: $(this).find(".image").attr('src'),
                    width: $(this).find(".image").attr('img_width'),
                    height: $(this).find(".image").attr('img_height'),
                    title: $(this).find('[name="title"]').val(),
                    link: $(this).find('[name="title_link"]').val()
                })
            }
        });
        console.log(d);
        return d;
    }
};

var m_text_image = {
    init_ev_el: function(el,data){
        // if(data.title != undefined && data.title != ''){
        //     el.find(" .text_module>span").text(data.title);
		// }
		this.change_left_module(el,data)
		if(data.rich_text) el.find(".t_i_text").html(data.rich_text);
    },

    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		// el.find("#title").val(d.title);
		
		
        if(d.ti_type == 1){
            el.find('input[name="ti_type"][value="1"]').prop("checked","checked");
        }else{
            el.find('input[name="ti_type"][value="0"]').prop("checked","checked");
        }
        if(d.img_style == 1){
            el.find('input[name="img_style"][value="1"]').prop("checked","checked");
        }else{
            el.find('input[name="img_style"][value="0"]').prop("checked","checked");
		}
		if(d.img_url){
			el.find(".image").attr('src',d.img_url);
			el.find(".image").attr('src_width',d.img_width);
			el.find(".image").attr('src_height',d.img_height);
			el.find(".image").css({
				"width":"70px",
				"height":"70px",
				"margin-left":"0px",
				"margin-top":"-5px"
			});
			el.find(".jiahao").css("display","none");
		}
		if(d.title_link) el.find('#title_link').val(d.title_link);
		el.find('[class^="add_image deco_add_img"]').click(function(){
			console.log(1);
			var that = $(this);
			$.jImageManager({
				img_width:360,
				img_height:360,
				ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
					that.find(".image").attr('src',path);
                    that.find(".image").attr('src_width',img_arr[0].img_width);
                    that.find(".image").attr('src_height',img_arr[0].img_height);
                    hasSaved = false;
                    that.find(".image").css({
						"width":"70px",
						"height":"70px",
						"margin-left":"0px",
						"margin-top":"-5px"
					});
                    that.find(".jiahao").css("display","none");
                    hasSaved = false;
                    that.css("background","none");
                    manager.change_show_module();
				}
			});
		});
		el.find(".select_links").click(function(){
            hasSaved = false;
			show_links_dlg(function(url){
				 $(".module_body .data_item #title_link").val(url);
			});
		});
        // el.find("#title").change(function () {
        //     manager.change_show_module();
		// });
		var text_image_items = [
			'undo', 'redo', '|', 'fontsize','bold',
			'italic', 'removeformat'
		];
		KindEditor.ready(function(K) {
			window.text_image = K.create('#editor_text', {
				items:text_image_items,
				autoHeightMode: false,
				resizeType:0,
				allowFileManager: false,
				pasteType : 1,
				allowImageRemote : false,
				afterChange:function(){
					this.sync();
					manager.change_show_module();
				}
			});
		});
		if(!d.rich_text) d.rich_text = "";		
		window.text_image.html(d.rich_text);
		el.find("input").change(function () {
            manager.change_show_module();
		})
		el.find("textarea").change(function () {
            manager.change_show_module();
		})
	},
	change_left_module:function(el,data){
		el.find('.text_image_module').html('');
		data.ti_type = data.ti_type ? data.ti_type : 0;
		data.img_style = data.img_style ? data.img_style : 0;
		if((data.ti_type == 0) && (data.img_style == 0)){
			var element = $('#template_list .text_image_view_big_left').clone();
		}
		if((data.ti_type == 0) && (data.img_style == 1)){
			var element = $('#template_list .text_image_view_small_left').clone();
		}
		if((data.ti_type == 1) && (data.img_style == 0)){
			// var element = $('.text_image_view_small_left').clone();
			var element = $('#template_list .text_image_view_big_right').clone();
		}
		if((data.ti_type == 1) && (data.img_style == 1)){
			var element = $('#template_list .text_image_view_small_right').clone();
		}
		if(data.rich_text) element.find(".t_i_text").html(data.rich_text);
		if(data.img_url) element.find('.t_i_img').attr('src',data.img_url);
		el.find('.text_image_module').html(element);
	},
    show_edit_el:function(data){
        var el = $("#template_list .d_m_text_image").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.ti_type = el.find("input[name='ti_type']:checked").val();
        d.img_style = el.find("input[name='img_style']:checked").val();
        d.img_url = el.find(".image").attr("src");
        d.src_height = el.find(".image").attr("src_height");
        d.src_width = el.find(".image").attr("src_width");
		d.title_link = el.find("#title_link").val();
		d.rich_text = el.find("#editor_text").val();
        return d;
    }
};
