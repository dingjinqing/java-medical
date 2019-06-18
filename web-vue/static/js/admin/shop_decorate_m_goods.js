

var m_goods = {
	init_ev_el: function(el,data) {
        if (data.title != undefined && data.title != '') {
            el.find(".p_goods_title").text(data.title);
        }

        if (data.tit_center == '1') {
            el.find(".tit_cent").css("text-align", 'center');
            el.find(".title").css("border-style", 'none none none none');
        } else {
            el.find(".tit_cent").css("text-align", 'left');
            el.find(".title").css("border-style", 'none none none solid');
        }
        if (data.img_url) {
            el.find(".image").attr("src", data.img_url);
            el.find(".title").css("border-style", 'none none none none');
        }
        if ((data.title != undefined && data.title != '') || (data.img_url != undefined && data.img_url != '')) {
            el.find(".tit_cent").removeClass('hide');
        } else {
            el.find(".tit_cent").addClass('hide');
        }

        var good_flag = !isNaN(parseInt(data.col_type));
		data.goods_num = data.goods_num ? data.goods_num : 4;//默认4个商品
		console.log(data);
        var param = {op: 'get', data: $.toJSON(data)};
        var goodsContent = [];
        function callbackInitGoods() {
            var initGoods = [];
            for (var i in data.goods_items) {
                for (var j in goodsContent){
                    if (goodsContent[j].goods_id == data.goods_items[i].goods_id) {
						console.log(goodsContent[j])
						data.goods_items[i].is_delete = goodsContent[j].is_delete;
						data.goods_items[i].is_on_sale = goodsContent[j].is_on_sale;
						data.goods_items[i].goods_number = goodsContent[j].goods_number;
						if(goodsContent[j].is_on_sale != 0 && goodsContent[j].is_delete != 1 && goodsContent[j].goods_number != 0){
							initGoods.push(goodsContent[j]);
						}
                    }
                }
			}
			// if(data.recommend_type != 1){
				if (initGoods.length <= 0) {
					initGoods = goodsContent;
				}
			// }
            m_goods.init_el(el, initGoods);
            if (good_flag == true && parseInt(data.col_type) == 0) {
                for (var i in initGoods) {
                    m_goods.add_single_good_el(el, initGoods[i], data.goods_display,data.if_radius,data.goods_module_style,data.cart_btn,data.cart_btn_choose);
                }
            } else if (good_flag == true && parseInt(data.col_type) == 2) {
                for (var i = 0; i < initGoods.length; i += 3) {
                    m_goods.add_three_good_el(el, initGoods[i], initGoods[i+1], initGoods[i + 2],data.if_radius,data.goods_module_style,data.cart_btn,data.cart_btn_choose);
                }
            } else if(good_flag == true && parseInt(data.col_type) == 3){
                // for (var i = 0; i < initGoods.length; i += 3) {
                    m_goods.add_some_good_el(el, initGoods[0], initGoods[1], initGoods[2],data.if_radius,data.goods_module_style,data.cart_btn,data.cart_btn_choose);
                // }
			}else {
                for (var i = 0; i < initGoods.length; i += 2) {
                    m_goods.add_double_good_el(el, initGoods[i], initGoods[i + 1],data.if_radius,data.goods_module_style,data.cart_btn,data.cart_btn_choose);
                }
            }
        }
        util.ajax_json("/admin/ajax/mp/goods", function (d) {
            goodsContent = d.content;
            callbackInitGoods();
        }, param);
	},

	add_single_good_el: function(el,d,goods_display,if_radius,goods_module_style,cart_btn,cart_btn_choose){
		var e = $("#template_list .single_goods_module").clone();
		if(d.img_url) e.find(".item_img").attr('src',d.img_url);
	    if(d.goods_price) e.find(".p_goods_item .item-price .bs_prix:eq(0)").text("￥"+d.goods_price);
        if(cart_btn == 0){
            switch(parseInt(cart_btn_choose))
            {
                case 0:
                    e.find('.cart_btn:eq(0)').css('display','inline-block');
                    break;
                case 1:
                    e.find('.cart_btn:eq(1)').css('display','inline-block');
                    break;
                case 2:
                    e.find('.cart_btn:eq(2)').css('display','inline-block');
                    break;
                case 3:
                    e.find('.cart_btn:eq(3)').css('display','inline-block');
                    break;
            }
        }else{
          e.find('.cart_btn').css('display','none');
        }
        if(d.market_price && d.market_price != 0 && cart_btn != 0){
            e.find(".item-picture .item-price .market_price").removeClass("hide");
            e.find(".p_goods_item .item-price .mk_price:eq(0)").text("￥"+d.market_price);
        }else{
            e.find(".item-picture .item-price .market_price").addClass("hide");
        }
		if(d.hide_name == 1)  e.find(".item-picture .item-price .goods-title").addClass("hide");
        if(d.hide_label == 1)  e.find(".label-control").addClass("hide");
		if(d.hide_price == 1){
			e.find(".item-picture .item-price .goods-price").addClass("hide");
		    e.find(".item-picture .item-price .market_price").addClass("hide");
		}
		e.find(".p_goods_item .item-price .bs_head:eq(0)").text(d.goods_name);
		e.find(".item-picture").show();
		if(goods_display == 1){
			e.find('.item_img').css("width",'150px');
            e.find('.item_img').css("height",'150px');
		}else{
            e.find('.item_img').css("width",'100%');
            e.find('.item_img').css("height",'auto');
		}
        if(if_radius == 1){
            e.find(".item-picture").css("border-radius",'8px');
		}
		if(goods_module_style == 2){
			e.find(".item-picture").addClass('border_1px')
		} else if (goods_module_style == 1) {
			e.find(".item-picture").addClass('border_shadow')
		} else {
			e.find(".item-picture").addClass('no_boder')
		}
		if(d.label != null){
			if(d.label.len != 3){
				d.label.len ='';
			}
			e.find(".label-control").addClass("label-style" +d.label.list_pattern + d.label.len);
			if(d.label.list_pattern == 4){
                e.find(".label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
            if(d.label.list_pattern == 3){
                e.find(".label-control").css("color",the_color);
                e.find(".label-control").css("border-color",the_color);
            }
            if(d.label.list_pattern == 2){
                e.find(".label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
			e.find(".label-control span").text(d.label.name);
		}else{
            e.find(".label-control").addClass("hide");
		}
		e.appendTo(el);
		// console.log(d);
		// console.log(d.label.name);
	},

	add_double_good_el: function(el,d1,d2,if_radius,goods_module_style,cart_btn,cart_btn_choose){
		var e = $("#template_list .double_goods_module").clone();
        if(d1.img_url) e.find(".p_goods_item1 .item_img").attr('src',d1.img_url);
        if(d1.goods_price) e.find(".p_goods_item1 .item-price .bs_prix:eq(0)").text("￥"+d1.goods_price);
        if(cart_btn == 0){
            switch(parseInt(cart_btn_choose))
            {
                case 0:
                    e.find('.p_goods_item1 .cart_btn:eq(0)').css('display','inline-block');
                    e.find('.p_goods_item2 .cart_btn:eq(0)').css('display','inline-block');
                    break;
                case 1:
                    e.find('.p_goods_item1 .cart_btn:eq(1)').css('display','inline-block');
                    e.find('.p_goods_item2 .cart_btn:eq(1)').css('display','inline-block');
                    break;
                case 2:
                    e.find('.p_goods_item1 .cart_btn:eq(2)').css('display','inline-block');
                    e.find('.p_goods_item2 .cart_btn:eq(2)').css('display','inline-block');
                    break;
                case 3:
                    e.find('.p_goods_item1 .cart_btn:eq(3)').css('display','inline-block');
                    e.find('.p_goods_item2 .cart_btn:eq(3)').css('display','inline-block');
                    break;
            }
        }else{
            e.find('.cart_btn').css('display','none');
        }
        if(d1.market_price && d1.market_price != 0 && cart_btn == 1){
            e.find(".item-picture .item-price .market_price").removeClass("hide");
            e.find(".p_goods_item1 .item-price .mk_price:eq(0)").text("￥"+d1.market_price);
        }else{
            e.find(".p_goods_item1 .item-price .market_price").addClass("hide");
        }
		if(d1.hide_name == 1)  e.find(".item-picture .item-price .goods-title").addClass("hide");
        if(d1.hide_label == 1)  e.find(".label-control").addClass("hide");
		if(d1.hide_price == 1){
			e.find(".item-picture .item-price .goods-price").addClass("hide");
		    e.find(".item-picture .item-price .market_price").addClass("hide");
		}
		e.find(".p_goods_item1 .item-price .bs_head:eq(0)").text(d1.goods_name);
		e.find(".p_goods_item1 .item-picture").show();
        if(d1.label != null){
            if(d1.label.len != 3){
                d1.label.len ='';
            }

            e.find(".p_goods_item1 .label-control").addClass("label-style" +d1.label.list_pattern + d1.label.len);
            if(d1.label.list_pattern == 4){
                e.find(".p_goods_item1 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
            if(d1.label.list_pattern == 3){
                e.find(".p_goods_item1 .label-control").css("color",the_color);
                e.find(".p_goods_item1 .label-control").css("border-color",the_color);
            }
            if(d1.label.list_pattern == 2){
                e.find(".p_goods_item1 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
            e.find(".p_goods_item1 .label-control span").text(d1.label.name);
        }
        if(d1.label == null){
            e.find(".label-control").addClass("hide");
		}
        if(if_radius == 1){
            e.find(".item-picture").css("border-radius",'8px');
		}
		if(goods_module_style == 2){
			e.find(".item-picture").addClass('border_1px')
		} else if (goods_module_style == 1) {
			e.find(".item-picture").addClass('border_shadow')
		} else {
			e.find(".item-picture").addClass('no_boder')
		}
		if(d2){
			e.find(".p_goods_item2 .item_img").attr('src',d2.img_url);
			e.find(".p_goods_item2 .item-price .bs_prix:eq(0)").text("￥"+d2.goods_price);
            if(d2.market_price && d2.market_price != 0 && cart_btn == 1){
                e.find(".item-picture .item-price .market_price").removeClass("hide");
                e.find(".p_goods_item2 .item-price .mk_price:eq(0)").text("￥"+d2.market_price);
            }else{
                e.find(".p_goods_item2 .item-price .market_price").addClass("hide");
            }
			e.find(".p_goods_item2 .item-price .bs_head:eq(0)").text(d2.goods_name);
			e.find(".p_goods_item2 .item-picture").show();
            if(d2.label != null){
                if(d2.label.len != 3){
                    d2.label.len ='';
                }
                e.find(".p_goods_item2 .label-control").addClass("label-style" +d2.label.list_pattern + d2.label.len);
                if(d2.label.list_pattern == 4){
                    e.find(".p_goods_item2 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                if(d2.label.list_pattern == 3){
                    e.find(".p_goods_item2 .label-control").css("color",the_color);
                    e.find(".p_goods_item2 .label-control").css("border-color",the_color);
                }
                if(d2.label.list_pattern == 2){
                    e.find(".p_goods_item2 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                e.find(".p_goods_item2 .label-control span").text(d2.label.name);
            }else{
                e.find(".label-control").addClass("hide");
			}
		}else{
			e.find(".p_goods_item2 .item-picture").hide();
		}
		e.appendTo(el);
	},
	
	add_three_good_el: function(el,d1,d2,d3,if_radius,goods_module_style,cart_btn,cart_btn_choose){
		var e = $("#template_list .three_goods_module").clone();
        if(d1.img_url) e.find(".t_goods_item1 .item_img").attr('src',d1.img_url);
        if(d1.goods_price) e.find(".t_goods_item1 .item-price .bs_prix:eq(0)").text("￥"+d1.goods_price);
        if(cart_btn == 0){
            switch(parseInt(cart_btn_choose))
            {
                case 0:
                    e.find('.t_goods_item1 .cart_btn:eq(0)').css('display','inline-block');
                    e.find('.t_goods_item2 .cart_btn:eq(0)').css('display','inline-block');
                    e.find('.t_goods_item3 .cart_btn:eq(0)').css('display','inline-block');
                    break;
                case 1:
                    e.find('.t_goods_item1 .cart_btn:eq(1)').css('display','inline-block');
                    e.find('.t_goods_item2 .cart_btn:eq(1)').css('display','inline-block');
                    e.find('.t_goods_item3 .cart_btn:eq(1)').css('display','inline-block');
                    break;
            }
        }else{
            e.find('.cart_btn').css('display','none');
        }
		if(d1.hide_name == 1)  e.find(".item-picture .item-price .goods-title").addClass("hide");
        if(d1.hide_label == 1)  e.find(".label-control").addClass("hide");
		if(d1.hide_price == 1){
			e.find(".item-picture .item-price .goods-price").addClass("hide");
		    e.find(".item-picture .item-price .market_price").addClass("hide");
		}
		e.find(".t_goods_item1 .item-price .bs_head:eq(0)").text(d1.goods_name);
		e.find(".t_goods_item1 .item-picture").show();
        if(d1.label != null){
            if(d1.label.len != 3){
                d1.label.len ='';
            }
            e.find(".t_goods_item1 .label-control").addClass("label-style" +d1.label.list_pattern + d1.label.len);
            if(d1.label.list_pattern == 4){
                e.find(".t_goods_item1 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
            if(d1.label.list_pattern == 3){
                e.find(".t_goods_item1 .label-control").css("color",the_color);
                e.find(".t_goods_item1 .label-control").css("border-color",the_color);
            }
            if(d1.label.list_pattern == 2){
                e.find(".t_goods_item1 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
            e.find(".t_goods_item1 .label-control span").text(d1.label.name);
        }
        if(d1.label == null){
            e.find(".label-control").addClass("hide");
        }
        if(if_radius == 1){
            e.find(".item-picture").css("border-radius",'8px');
		}
		if(goods_module_style == 2){
			e.find(".item-picture").addClass('border_1px')
		} else if (goods_module_style == 1) {
			e.find(".item-picture").addClass('border_shadow')
		} else {
			e.find(".item-picture").addClass('no_boder')
		}
		if(d2){
			e.find(".t_goods_item2 .item_img").attr('src',d2.img_url);
			e.find(".t_goods_item2 .item-price .bs_prix:eq(0)").text("￥"+d2.goods_price);
			e.find(".t_goods_item2 .item-price .bs_head:eq(0)").text(d2.goods_name);
			e.find(".t_goods_item2 .item-picture").show();
            if(d2.label != null){
                if(d2.label.len != 3){
                    d2.label.len ='';
                }
                e.find(".t_goods_item2 .label-control").addClass("label-style" +d2.label.list_pattern + d2.label.len);
                if(d2.label.list_pattern == 4){
                    e.find(".t_goods_item2 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                if(d2.label.list_pattern == 3){
                    e.find(".t_goods_item2 .label-control").css("color",the_color);
                    e.find(".t_goods_item2 .label-control").css("border-color",the_color);
                }
                if(d2.label.list_pattern == 2){
                    e.find(".t_goods_item2 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                e.find(".t_goods_item2 .label-control span").text(d2.label.name);
            }else{
                    e.find(".label-control").addClass("hide");
			}
		}else{
			e.find(".t_goods_item2 .item-picture").hide();
		}
		if(d3){
			e.find(".t_goods_item3 .item_img").attr('src',d3.img_url);
			e.find(".t_goods_item3 .item-price .bs_prix:eq(0)").text("￥"+d3.goods_price);
			e.find(".t_goods_item3 .item-price .bs_head:eq(0)").text(d3.goods_name);
			e.find(".t_goods_item3 .item-picture").show();
            if(d3.label != null){
                if(d3.label.len != 3){
                    d3.label.len ='';
                }
                e.find(".t_goods_item3 .label-control").addClass("label-style" +d3.label.list_pattern + d3.label.len);
                if(d3.label.list_pattern == 4){
                    e.find(".t_goods_item3 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                if(d3.label.list_pattern == 3){
                    e.find(".t_goods_item3 .label-control").css("color",the_color);
                    e.find(".t_goods_item3 .label-control").css("border-color",the_color);
                }
                if(d3.label.list_pattern == 2){
                    e.find(".t_goods_item3 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                e.find(".t_goods_item3 .label-control span").text(d3.label.name);
            }else{
                e.find(".label-control").addClass("hide");
            }
		}else{
			e.find(".t_goods_item3 .item-picture").hide();
		}
		e.appendTo(el);
	},
    add_some_good_el:function (el,d1,d2,d3,if_radius,goods_module_style,cart_btn,cart_btn_choose) {
        var e = $("#template_list .four_goods_module").clone();
        if(d1.img_url) e.find(".t_goods_item1 .item_img").attr('src',d1.img_url);
        if(d1.goods_price) e.find(".t_goods_item1 .item-price .bs_prix:eq(0)").text("￥"+d1.goods_price);
        if(cart_btn == 0){
            switch(parseInt(cart_btn_choose))
            {
                case 0:
                    e.find('.t_goods_item1 .cart_btn:eq(0)').css('display','inline-block');
                    e.find('.t_goods_item2 .cart_btn:eq(0)').css('display','inline-block');
                    e.find('.t_goods_item3 .cart_btn:eq(0)').css('display','inline-block');
                    break;
                case 1:
                    e.find('.t_goods_item1 .cart_btn:eq(1)').css('display','inline-block');
                    e.find('.t_goods_item2 .cart_btn:eq(1)').css('display','inline-block');
                    e.find('.t_goods_item3 .cart_btn:eq(1)').css('display','inline-block');
                    break;
            }
        }else{
            e.find('.cart_btn').css('display','none');
        }
        if(d1.hide_name == 1)  e.find(".item-picture .item-price .goods-title").addClass("hide");
        if(d1.hide_label == 1)  e.find(".label-control").addClass("hide");
        if(d1.hide_price == 1){
            e.find(".item-picture .item-price .goods-price").addClass("hide");
            e.find(".item-picture .item-price .market_price").addClass("hide");
        }
        e.find(".t_goods_item1 .item-price .bs_head:eq(0)").text(d1.goods_name);
        e.find(".t_goods_item1 .item-picture").show();
        if(d1.label != null){
            if(d1.label.len != 3){
                d1.label.len ='';
            }
            e.find(".t_goods_item1 .label-control").addClass("label-style" +d1.label.list_pattern + d1.label.len);
            if(d1.label.list_pattern == 4){
                e.find(".t_goods_item1 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
            if(d1.label.list_pattern == 3){
                e.find(".t_goods_item1 .label-control").css("color",the_color);
                e.find(".t_goods_item1 .label-control").css("border-color",the_color);
            }
            if(d1.label.list_pattern == 2){
                e.find(".t_goods_item1 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
            }
            e.find(".t_goods_item1 .label-control span").text(d1.label.name);
        }
        if(if_radius == 1){
            e.find(".item-picture").css("border-radius",'8px');
		}
		if(goods_module_style == 2){
			e.find(".item-picture").addClass('border_1px')
		} else if (goods_module_style == 1) {
			e.find(".item-picture").addClass('border_shadow')
		} else {
			e.find(".item-picture").addClass('no_boder')
		}
        if(d1.label == null){
            e.find(".label-control").addClass("hide");
        }

        if(d2){
            e.find(".t_goods_item2 .item_img").attr('src',d2.img_url);
            e.find(".t_goods_item2 .item-price .bs_prix:eq(0)").text("￥"+d2.goods_price);
            e.find(".t_goods_item2 .item-price .bs_head:eq(0)").text(d2.goods_name);
            e.find(".t_goods_item2 .item-picture").show();
            if(d2.label != null){
                if(d2.label.len != 3){
                    d2.label.len ='';
                }
                e.find(".t_goods_item2 .label-control").addClass("label-style" +d2.label.list_pattern + d2.label.len);
                if(d2.label.list_pattern == 4){
                    e.find(".t_goods_item2 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                if(d2.label.list_pattern == 3){
                    e.find(".t_goods_item2 .label-control").css("color",the_color);
                    e.find(".t_goods_item2 .label-control").css("border-color",the_color);
                }
                if(d2.label.list_pattern == 2){
                    e.find(".t_goods_item2 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                e.find(".t_goods_item2 .label-control span").text(d2.label.name);
            }else{
                e.find(".label-control").addClass("hide");
            }
        }else{
            e.find(".t_goods_item2 .item-picture").hide();
        }
        if(d3){
            e.find(".t_goods_item3 .item_img").attr('src',d3.img_url);
            e.find(".t_goods_item3 .item-price .bs_prix:eq(0)").text("￥"+d3.goods_price);
            e.find(".t_goods_item3 .item-price .bs_head:eq(0)").text(d3.goods_name);
            e.find(".t_goods_item3 .item-picture").show();
            if(d3.label != null){
                if(d3.label.len != 3){
                    d3.label.len ='';
                }
                e.find(".t_goods_item3 .label-control").addClass("label-style" +d3.label.list_pattern + d3.label.len);
                if(d3.label.list_pattern == 4){
                    e.find(".t_goods_item3 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                if(d3.label.list_pattern == 3){
                    e.find(".t_goods_item3 .label-control").css("color",the_color);
                    e.find(".t_goods_item3 .label-control").css("border-color",the_color);
                }
                if(d3.label.list_pattern == 2){
                    e.find(".t_goods_item3 .label-control").css("background","linear-gradient(to right, "+linear_color+", "+the_color+")");
                }
                e.find(".t_goods_item3 .label-control span").text(d3.label.name);
            }else{
                e.find(".label-control").addClass("hide");
            }
        }else{
            e.find(".t_goods_item3 .item-picture").hide();
        }
        e.appendTo(el);
    },
	init_el: function(el,list){
		el.find(".single_goods_module").remove();
		el.find(".double_goods_module").remove();
		el.find(".three_goods_module").remove();
		el.find(".four_goods_module").remove();
		if(list.length > 0){
			el.find(".item_no_data").hide();
		}else{
			el.find(".item_no_data").show();
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("#title").val(d.title);
		el.find("#title_link").val(d.title_link);
		var t = parseInt(d.recommend_type) == 1 ? 1 : 0;
		el.find("[name='recommend_type'] option:eq("+t+")").prop('selected',true);
		if(!isNaN(parseInt(d.col_type))){
			d.col_type=d.col_type
		}else{
			d.col_type =1;
		}
		el.find("[name='col_type']").val(d.col_type);
        el.find('.img_list').removeClass('blue_border');
        el.find('.img_list').children().removeClass('blue_border');
        el.find('.img_list').children().eq(d.col_type).addClass('blue_border');

        if(d.col_type == 0){
            el.find(".display_show").css('display','table-row');
		}else{
            el.find(".display_show").css('display','none');
		}
        if(d.col_type == 2 || d.col_type == 3){
            el.find('#shop_cart_3').attr('disabled',true);
            el.find('#shop_cart_4').attr('disabled',true);
        }
        if(d.goods_display == 1){
        	$('#center').prop('checked',true);
		}else{
            $('#paved').prop('checked',true);
		}
		el.find("[name='goods_num']").val(parseInt(d.goods_num) > 1 ? d.goods_num : 4);
		el.find("[name='min_price']").val(d.min_price);
		el.find("[name='max_price']").val(d.max_price);
		el.find("[name='keywords']").val(d.keywords);
		el.find("[name='sort_type']").val(parseInt(d.sort_type) > 1 ? d.sort_type : 1);
		el.find("[name='category']").val(parseInt(d.category) > 0 ? d.category : 0);
		if(d.img_url){
            el.find(".add_image img").attr("src",d.img_url);
            el.find(".add_image").css("background",'none');
			el.find(".del-has-image").show();
		}else{
			el.find(".del-has-image").hide();	
		} 
		if(d.hide_name != 1)  el.find("#hide_goods_name input").attr("checked",true);
		if(d.hide_price != 1)  el.find("#hide_goods_price input").attr("checked",true);
		if(d.hide_label != 1)  el.find("#hide_goods_label input").attr("checked",true);
        if (el.find('input[name="cart_checked"]:checked').length <= 0) {
            el.find('input[name="cart_checked"]').eq(0).prop('checked', true);
        }
        if(d.cart_btn == 0){
            el.find("#shop_cart_btn").attr("checked",true);
            el.find('.btn_cart').show();
            el.find('input[name="cart_checked"]').each(function(){
                if($(this).val() == d.cart_btn_choose){
                    $(this).prop('checked',true);
                }else if(d.cart_btn_choose == undefined && index == 0){
                    $(this).prop('checked',true);
                    return false;
                }
            })
        }else{
            el.find("#shop_cart_btn").attr("checked",false);
            el.find('.btn_cart').hide();
        }
		if(d.tit_center== '1')  el.find("#title_center input").attr("checked",true);
		if(typeof d.goods_items == 'object'){
            var checkedGoodsId = [];
			for(var i in d.goods_items){
				m_goods.add_goods_item(el,d.goods_items[i]);
				checkedGoodsId.push(d.goods_items[i].goods_id);
			}
            el.find('input[name="recommend_goods_id"]').val(checkedGoodsId.join(','));
		}
		if (d.if_radius == 1) {
            el.find('input[name="if_radius"]').eq(1).attr("checked",true);
		}else{
            el.find('input[name="if_radius"]').eq(0).attr("checked",true);
		}
		if (d.goods_module_style == 2) {
            el.find('input[name="goods_module_style"]').eq(2).attr("checked",true);
		} else if (d.goods_module_style == 1) {
            el.find('input[name="goods_module_style"]').eq(1).attr("checked",true);
		} else {
			el.find('input[name="goods_module_style"]').eq(0).attr("checked",true);
		}

        el.find('[name="goods_area"]').val(d.goods_area || 'all');
        el.find(".add_goods_area").attr('area_data',d.goods_area_data);
        let area_length = util.count((d.goods_area_data || '').split(','));
        if (d.goods_area == 'sort') {
            el.find(".goods_area_detail").show();
            el.find(".add_goods_area").html('+添加商家分类');
            el.find(".select_area_show").html('已选择分类：' + area_length + '个分类')
        } else if (d.goods_area == 'cat') {
            el.find(".goods_area_detail").show();
            el.find(".add_goods_area").html('+添加平台分类');
            el.find(".select_area_show").html('已选择分类：' + area_length + '个分类')
        } else if (d.goods_area == 'brand') {
            el.find(".goods_area_detail").show();
            el.find(".add_goods_area").html('+添加商品品牌');
            el.find(".select_area_show").html('已选择品牌：' + area_length + '个品牌')
        } else if (d.goods_area == 'label') {
            el.find(".goods_area_detail").show();
            el.find(".add_goods_area").html('+添加商品标签');
            el.find(".select_area_show").html('已选择标签：' + area_length + '个标签')
        } else {
            el.find(".goods_area_detail").hide();
            el.find(".add_goods_area").attr('area_data','');
        }
		this.fill_edit_action_el(el);
	},

    fill_edit_action_el: function(el) {
        el.find(".add_image").click(function(){
            var that = $(this);
            $.jImageManager({
                img_width:25,
                img_height:25,
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    $(".module_body .data_item img").attr('src',path);

                    el.find(".del-has-image").show();
                    that.css("background",'none');
                    manager.change_show_module();
                }
            });
        });
        el.find(".del-has-image").click(function(e){
            e.stopPropagation();
            el.find(".add_image img").attr("src","");
            el.find(".add_image").css("background","url(/image/admin/shop_beautify/add_decorete.png) no-repeat");
            el.find(".add_image").css("background-size","45%");
            el.find(".add_image").css("background-position","center");
            el.find(".del-has-image").hide();
            manager.change_show_module();
        });
        el.find(".select_links").click(function(){
            show_links_dlg(function(url){
                $(".module_body .data_item #title_link").val(url);
            });
        });
        if(el.find("[name='recommend_type']").val() == "0"){
            $(".module_body .d_m_goods .auto_recommend").show();
            $(".module_body .d_m_goods .custom_goods").hide();
        }else{
            $(".module_body .d_m_goods .auto_recommend").hide();
            $(".module_body .d_m_goods .custom_goods").show();
        }
        el.find("[name='recommend_type']").change(function(){
            if($(this).val() == "0"){
                $(".module_body .d_m_goods .auto_recommend").show();
                $(".module_body .d_m_goods .custom_goods").hide();
            }else{
                $(".module_body .d_m_goods .auto_recommend").hide();
                $(".module_body .d_m_goods .custom_goods").show();
            }
        });

        el.find(".add_goods").click(function(){
            var goods_array = [];
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                var checkedId1 = el.find('input[name="recommend_goods_id"]').val();
                layer.open({
                    type: 2
                    , title: ['选择商品', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['945px','430px']
                    , content: '/admin/public/purchase/goods/list?record_select_value='+checkedId1 //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        $(".goods_item_list .goods_item").each(function(){
                            goods_array.push($(this).attr("goods_id"));
                        });
                        body.contents().find("tr").each(function(){
                            if($.inArray($(this).attr("goods_id"),goods_array)>-1){
                                $(this).attr('data-back','false').addClass('goods_tr_choose');
                            }
                        });
                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var checkedId = iframe.find('#record_select_value').val();
                        var result;
                        if(checkedId != '' && checkedId != undefined){
                            var newCheckID = checkedId.split(',');
                            var oldCheckID = goods_array;
                            result = newCheckID.filter(function(item1) {
                                return oldCheckID.every(function(item2) {
                                    return item2 !== item1
                                })
                            })
                            if(result == ''){
                                checkedId = newCheckID.join(',');
                            }else{
                                checkedId = result.concat(oldCheckID).join(',');
                            }
                        }
                        console.log(checkedId);
                        el.find('input[name="recommend_goods_id"]').val(checkedId);
                        util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                            var content = response.content;
                            var list = [];
                            var chec = checkedId.split(',');
                            chec.map(function(item){
                               for(var i = 0;i<content.length;i++){
                                   if(content[i].goods_id == item){
                                       list.push(content[i]);
                                       content.splice(i,1);
                                       break;
                                   }
                               }
                            })
                            el.find('.goods_item_list').html('');
                            for (var i in list) {
                                list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                                var goods = {};
                                goods.goods_id = list[i].goods_id;
                                goods.goods_name = list[i].goods_name;
                                goods.img_url = list[i].goods_img;
                                goods.goods_price = list[i].goods_price;
                                goods.is_delete = list[i].is_delete;
                                goods.is_on_sale = list[i].is_on_sale;
                                m_goods.add_goods_item(el, goods);
                            }
                            manager.change_show_module();
                        },{select_id: checkedId});
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                    }
                });
            });
        });
        el.find(".single_list").click(function () {
            el.find("#col_type").val("0");
            el.find('#shop_cart_3').attr('disabled',false);
            el.find('#shop_cart_4').attr('disabled',false);
            el.find('.display_show').show();
        });
        el.find(".double_list").click(function () {
            el.find("#col_type").val("1");
            el.find('#shop_cart_3').attr('disabled',false);
            el.find('#shop_cart_4').attr('disabled',false);
            el.find('.display_show').hide();
        });
        el.find(".triple_list").click(function () {
            el.find("#col_type").val("2");
            if(el.find('#shop_cart_3').prop('checked') == true || el.find('#shop_cart_4').prop('checked') == true){
               el.find('#shop_cart_1').prop('checked',true);
            }
            el.find('#shop_cart_3').attr('disabled',true);
            el.find('#shop_cart_4').attr('disabled',true);
            el.find('.display_show').hide();
        });
        el.find(".fourth_list").click(function () {
            el.find("#col_type").val("3");
            if(el.find('#shop_cart_3').prop('checked') == true || el.find('#shop_cart_4').prop('checked') == true){
                el.find('#shop_cart_1').prop('checked',true);
            }
            el.find('#shop_cart_3').attr('disabled',true);
            el.find('#shop_cart_4').attr('disabled',true);
            el.find('.display_show').hide();
        });

        el.find('[name="goods_area"]').change(function () {
            console.log('dddd');
            let goods_area = $(this).val();
            if (goods_area == 'sort') {
                el.find(".goods_area_detail").show();
                el.find(".add_goods_area").html('+添加商家分类');
                el.find(".add_goods_area").attr('area_data','');
                el.find(".select_area_show").html('')
            } else if (goods_area == 'cat') {
                el.find(".goods_area_detail").show();
                el.find(".add_goods_area").html('+添加平台分类');
                el.find(".add_goods_area").attr('area_data','');
                el.find(".select_area_show").html('')
            } else if (goods_area == 'brand') {
                el.find(".goods_area_detail").show();
                el.find(".add_goods_area").html('+添加商品品牌');
                el.find(".add_goods_area").attr('area_data','');
                el.find(".select_area_show").html('')
            } else if (goods_area == 'label') {
                el.find(".goods_area_detail").show();
                el.find(".add_goods_area").html('+添加商品标签');
                el.find(".add_goods_area").attr('area_data','');
                el.find(".select_area_show").html('')
            } else {
                el.find(".goods_area_detail").hide();
                el.find(".add_goods_area").attr('area_data','');
            }
        })
        var _this = this;
        el.find(".add_goods_area").click(function () {
            let goods_area = el.find('[name="goods_area"]').val();
            let goods_area_data = $(this).attr('area_data');
            _this.add_goods_area(el,goods_area,goods_area_data);
        })
        el.find('#shop_cart_btn').click(function(){
            if($(this).prop('checked') == true){
                el.find('.btn_cart').show();
            }
            if($(this).prop('checked') == false){
                el.find('.btn_cart').hide();
            }

        })
    },
	add_goods_item: function(el,d){
		var r = $(document).find("#template_list div.goods_item[hide]").clone();
		var goods_status = $('<span class="goods_status"></span>');
		r.removeAttr('hide');
		r.attr('goods_id',d.goods_id)
		if(d.is_delete === 1){
			goods_status.text('已删除')
			r.find('.goods_name').before(goods_status)
		} else if (d.is_on_sale === 0 || d.goods_number === 0){
			goods_status.text('已下架')
			r.find('.goods_name').before(goods_status)
		}
		r.find(".goods_name").text(d.goods_name);
		r.find(".goods_price").text(d.goods_price);
		r.find("img").attr("src",d.img_url);
		r.appendTo(el.find(".goods_item_list"));
		r.find(".up_arrow").click(function(){
			var p = $(this).parents(".goods_item");
			var pre = p.prev();
			if(pre.length > 0){
				pre.insertAfter(p);
			}
            manager.change_show_module();
		});
		r.find(".down_arrow").click(function(){
			var p = $(this).parents(".goods_item");
			var next = p.next();
			if(next.length > 0){
				next.insertBefore(p);
			}
            manager.change_show_module();
		});
		r.find(".remove").click(function(){
			var goods_id = el.find('input[name="recommend_goods_id"]').val();
		    var goodIds = goods_id.split(',');
		    var index = $.inArray($(this).parents('.goods_item').attr('goods_id'), goodIds);
		    if (index != '' || index != undefined) {
		        goodIds.splice(index, 1);
		    }
		    el.find($('input[name="recommend_goods_id"]')).val(goodIds.join(','));

			if($(this).parent().parent().length == 1)
				$(this).parents(".goods_item").remove();
			else
				$(this).parent().parent().remove();
            manager.change_show_module();
		});
	},

    add_goods_area: function(el,goods_area,goods_area_data) {
        if (goods_area == 'sort') {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['添加商家分类', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['580px','440px']
                    , content: '/admin/frame/goods/sort/select?type=3&sort_ids='+goods_area_data //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , yes: function (index, layero) { //保存按钮的回调
                        var cat_array = [];
                        var iframe = layer.getChildFrame('body', index);
                        iframe.find('input[name="sort_id[]"]:checked').each(function () {
                            cat_array.push($(this).val());
                        });
                        if (util.count(cat_array) > 0) {
                            el.find(".select_area_show").html('已选择分类：' + cat_array.length + '个分类')
                        } else {
                            el.find(".select_area_show").html('')
                        }
                        el.find(".add_goods_area").attr('area_data',cat_array.join(','));
                        manager.change_show_module();
                        layer.close(index);
                    }, btn2: function (index) {
                        //按钮取消的回调
                    }
                });
            });
        } else if (goods_area == 'cat') {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['添加平台分类', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['580px','440px']
                    , content: '/admin/frame/goods/cat/select?type=2&cat_ids='+goods_area_data //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , yes: function (index) { //保存按钮的回调
                        var cat_array = [];
                        var iframe = layer.getChildFrame('body', index);
                        iframe.find('input[name="cat_id[]"]:checked').each(function () {
                            cat_array.push($(this).val());
                        });

                        if (util.count(cat_array) > 0) {
                            el.find(".select_area_show").html('已选择分类：' + cat_array.length + '个分类')
                        } else {
                            el.find(".select_area_show").html('')
                        }
                        el.find(".add_goods_area").attr('area_data',cat_array.join(','));
                        manager.change_show_module();
                        layer.close(index);
                    }, btn2: function (index) {
                        //按钮取消的回调
                    }
                });
            });// /admin/goods/label/select
        } else if (goods_area == 'brand') {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['添加商品品牌', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['750px','440px']
                    , content: '/admin/frame/goods/brand/select?select_ids='+goods_area_data //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , yes: function (index) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var select_ids = iframe.find('#select_ids').val();
                        if (select_ids) {
                            el.find(".select_area_show").html('已选择品牌：' + select_ids.split(',').length + '个品牌')
                        } else {
                            el.find(".select_area_show").html('')
                        }
                        el.find(".add_goods_area").attr('area_data',select_ids);
                        manager.change_show_module();
                        layer.close(index);
                    }, btn2: function (index) {
                        //按钮取消的回调
                    }
                });
            });
        } else if (goods_area == 'label') {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['添加商品标签', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['750px','440px']
                    , content: '/admin/frame/goods/label/select?select_ids='+goods_area_data //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , yes: function (index) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var select_ids = iframe.find('#select_ids').val();
                        if (select_ids) {
                            el.find(".select_area_show").html('已选择标签：' + select_ids.split(',').length + '个标签')
                        } else {
                            el.find(".select_area_show").html('')
                        }
                        el.find(".add_goods_area").attr('area_data',select_ids);
                        manager.change_show_module();
                        layer.close(index);
                    }, btn2: function (index) {
                        //按钮取消的回调
                    }
                });
            });
        } else {
            el.find(".goods_area_detail").hide();
            el.find(".add_goods_area").attr('area_data','');
        }
    },
	show_edit_el:function(data){
		var el = $("#template_list .d_m_goods").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
        this.auto_save(el);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.title = el.find("#title").val();
		d.title_link = el.find("#title_link").val();
		d.recommend_type = el.find("[name='recommend_type']").val();
		if(d.recommend_type == 1){
            d.goods_items = [];
			var gi = el.find(".goods_item_list .goods_item");
            gi.each(function(){
                var t = {goods_id:$(this).attr('goods_id')};
				t.img_url = $(this).find("img").attr("src");
                t.goods_name = $(this).find(".goods_name").text();
                t.goods_price = $(this).find(".goods_price").text();
                d.goods_items.push(t);
			});
        }else{
		    d.goods_items = [];
        }
		d.col_type = el.find("[name='col_type']").val();
		d.goods_display = el.find("[name='goods_display']:checked").val();
		d.goods_num = el.find("[name='goods_num']").val();
		d.min_price = el.find("[name='min_price']").val();
		d.max_price = el.find("[name='max_price']").val();
		d.keywords = el.find("[name='keywords']").val();
		d.sort_type = el.find("[name='sort_type']").val();
		d.category = el.find("[name='category']").val();
		d.img_url = el.find("img").attr("src");
		if(el.find("#hide_goods_name input[type=checkbox]").prop('checked') == true){
            d.hide_name = 0;
        }else{
            d.hide_name = 1;
        }
        if(el.find("#hide_goods_price input[type=checkbox]").prop('checked') == true){
            d.hide_price = 0;
        }else{
            d.hide_price = 1;
        }
        if(el.find("#hide_goods_label input[type=checkbox]").prop('checked') == true){
            d.hide_label = 0;
        }else{
            d.hide_label = 1;
        }
        if(el.find("#shop_cart_btn").prop('checked') == true){
            d.cart_btn = 0;
            d.cart_btn_choose = el.find('input[name="cart_checked"]:checked').val();
        }else{
            d.cart_btn = 1;
        }
		d.tit_center=el.find("#title_center input[type=checkbox]:checked").val();
		d.if_radius = el.find("input[name='if_radius']:checked").val();
		d.goods_module_style = el.find("input[name='goods_module_style']:checked").val();
		d.goods_area = el.find("[name='goods_area']").val();
		d.goods_area_data = el.find(".add_goods_area").attr('area_data') || '';
		console.log(d)
		return d;
	},
    auto_save: function (el) {
        el.find("input").change( function () {
            manager.change_show_module();
        });
        el.find("select").change( function () {
            manager.change_show_module();
        });
        el.find(".change-to-save").click(function () {
            manager.change_show_module();
        })
    }
};







window.m_goods_top = {
	init_ev_el: function(el,data){
		data.goods_num = data.goods_num ? data.goods_num : 4;//默认4个商品
		var param = {data:$.toJSON(data)};
		util.ajax_json("?c=good&m=get_m_goods_top",function(d){
			m_goods_top.init_el(el,d.content);
			m_goods_top.add_good_el(el,d.content);
		},param);
	},

	add_good_el: function(el,d){
		if(d.length > 0){
			var e = $("#template_list .goods_top_module").clone();
			for(var i =0; i<3; i++){
				var idx = i + 1;
				if(d[i]){
					e.find("div[item-id='"+idx+"'] .item-img").attr('src',d[i].img_url);
					e.find("div[item-id='"+idx+"'] .item-name").text(d[i].goods_name);
					e.find("div[item-id='"+idx+"'] .item-price").text(d[i].goods_price+"元");
					e.find("div[item-id='"+idx+"'] .item-sale_num").text("销量： "+(parseInt(d[i].goods_sale_num)+parseInt(d[i].add_sale_num))+"件");
				}else{
					e.find("div[item-id='"+idx+"']").remove();
				}
			}
			e.appendTo(el);
		}		
		
	},

	init_el: function(el,list){
		el.find(".goods_top_module").remove();
		if(list.length > 0){
			el.find(".item_no_data").hide();
		}else{
			el.find(".item_no_data").show();
		}
	},

	fill_edit_el: function(el,d){
		el.attr('cur_idx',d.cur_idx);
		el.attr('module_name',d.module_name);
		el.find("[name='min_price']").val(d.min_price);
		el.find("[name='max_price']").val(d.max_price);
		el.find("[name='keywords']").val(d.keywords);
		el.find("[name='category']").val(parseInt(d.category) > 0 ? d.category : 0);
		el.find("[name='goods_num']").val(parseInt(d.goods_num) > 1 ? d.goods_num : 4);
	},

		
	show_edit_el:function(data){
		var el = $("#template_list .d_m_goods_top").clone();
		$(".module_body").html("");
		el.appendTo($(".module_body"));
		this.fill_edit_el(el,data);
		$("#module_edit").show();
	},
	get_data: function(){
		var el = $(".module_body .data_item");
		var cur_idx = parseInt(el.attr('cur_idx'));
		var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
		d.min_price = el.find("[name='min_price']").val();
		d.max_price = el.find("[name='max_price']").val();
		d.keywords = el.find("[name='keywords']").val();
		d.category = el.find("[name='category']").val();
		d.goods_num = el.find("[name='goods_num']").val();
		return d;
	}
};



var m_goods_search = {
	no_edit:true,
	init_ev_el: function(el,data){},
	show_edit_el:function(data){},
	get_data: function(){ return {};}
};


var m_all_goods = {
	no_edit:true,
	init_ev_el: function(el,data){},
	show_edit_el:function(data){},
	get_data: function(){ return {};}
};


