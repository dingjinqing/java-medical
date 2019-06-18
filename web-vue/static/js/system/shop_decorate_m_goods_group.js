
var m_goods_group = {
    init_ev_el: function(el,data) {
        var param = { is_init:1,data: $.toJSON(data)};
        util.ajax_json("/admin/ajax/mp/goods/group", function (response) {
            var content = response.content;
            if(response != undefined && content.goods_info != ''){
                m_goods_group.add_group_nav(2,data);
                    var goods_img = [];
                    var goods_name = [];
                    var goods_price = [];
                    var market_price = [];
                    var goods_len = content.goods_info.length;
                    for(var i in content.goods_info){
                        goods_img.push(content.goods_info[i].goods_img);
                        goods_name.push(content.goods_info[i].goods_name);
                        goods_price.push(content.goods_info[i].goods_price);
                        market_price.push(content.goods_info[i].market_price);
                    }
                    data.goods_img = goods_img;
                    data.goods_name = goods_name;
                    data.goods_price = goods_price;
                    data.goods_len = goods_len;
                    data.market_price = market_price;
                    m_goods_group.add_group_module_el(el,data,data.if_radius,data.module_style,data.position_style);
            }
        }, param);
    },
    /**
     * 商品列表样式
     */
    add_group_module_el:function(el,d,if_radius,module_style,position_style){
       var shop_style = parseInt(d.shop_style);
       var goods_len = d.goods_len;
       var name_arr = d.sort_group_arr;
       var modue_el = $(".module_body .data_item");
       var e;
       var eli;
       if(position_style == 0){
           switch (shop_style){
               case  1:
                   e = $("#template_list .group_big_module").clone();
                   eli = $("#template_list .group_big_module .group_goods_wrapper").clone();
                   break;
               case  2:
                   e = $("#template_list .group_double_module").clone();
                   eli = $("#template_list .group_double_module .group_goods_wrapper").clone();
                   break;
               case  3:
                   e = $("#template_list .group_trible_module").clone();
                   eli = $("#template_list .group_trible_module .group_goods_wrapper").clone();
                   break;
               case  4:
                   e = $("#template_list .group_list_module").clone();
                   eli = $("#template_list .group_list_module .group_goods_wrapper").clone();
                   break;
               case  5:
                   e = $("#template_list .group_slide_module").clone();
                   eli = $("#template_list .group_slide_module .group_goods_wrapper").clone();
                   break;
           }
           el.find(".group_nav_left_container").hide();
           el.find(".group_nav_show_two").nextAll().remove();
           e.appendTo(el);
           e.find(".group_goods_wrapper").remove();
       }else{
           e = $('#template_list .group_left_module').clone();
           eli = $("#template_list .group_left_module .group_goods_wrapper").clone();
           el.find(".group_nav_show_two").nextAll().remove();
           el.find(".group_left_content").empty();
           el.find(".group_nav_show_two").hide();
           el.find(".group_nav_show_one").hide();
           el.find(".group_nav_left_container").show();
           e.appendTo(el.find(".group_left_content"));
           e.find(".group_goods_wrapper").remove();
           e.find(".group_left_title").text(name_arr[0].group_name);
       }

        if(goods_len > 12){
            goods_len = 12;
        }
        for(var i = 0;i < goods_len;i++) {
            var emore = eli;
            emore.find(".goods-title").text(d.goods_name[i]);
            emore.find(".item_img").attr('src',d.goods_img[i]);
            if(d.market_price[i] != null){
                emore.find(".group-origin-price").text('￥' + d.market_price[i]);
            }else{
                emore.find(".group-origin-price").text('');
            }
            emore.find(".group-goods-price").text('￥'+d.goods_price[i]);
            emore.clone().appendTo( e.find(".group_goods_container"));
        }
        if(if_radius == 1){
            e.find(".group_goods_item").css("border-radius",'8px');
        }
        if(module_style == 1){
            e.find(".group_goods_item").addClass("no_boder");
        }else if(module_style == 2){
            e.find(".group_goods_item").addClass("border_shadow");
        }else{
            e.find(".group_goods_item").addClass("border_1px");
        }
    },

    fill_edit_el: function(el,d){
        console.log(el);
        console.log(d);
        var sort_group_arr = d.sort_group_arr;
        var sort_group = $('.clone_sort_cat .sort_group').clone();
        var sort_id;
        var sort_name;
        var sort_num;
        for(var i in sort_group_arr){
            sort_group.find('.sort_name').text(sort_group_arr[i].sort_name);
            sort_group.find('#group_name').val(sort_group_arr[i].group_name);
            sort_group.find('.group_goods1').text(sort_group_arr[i].sort_goods_num+'件');
            if(sort_group_arr[i].is_all == 1){
                sort_group.find('.is_all').eq(0).attr('checked', 'checked');
            }else {
                sort_group.find('.is_all').eq(1).attr('checked', 'checked');
                sort_group.find('.group_goods2').text(sort_group_arr[i].group_goods_num+'件');
            }
            var ed = $(".module_body .data_item");
            var elements = ed.find('.sort_group');
            var len = elements.length +1;
            sort_group.find('.is_all').attr('name','is_all_'+len);
            sort_group.find('.sort_id').val(sort_group_arr[i].sort_id);
            sort_group.find('.group_goods_id').val(sort_group_arr[i].group_goods_id);
            el.find('.group_content').append(sort_group.clone());
            el.find('.group_content .sort_group').show();
        }
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        el.find("#title").val(d.title);
        el.find("#title_link").val(d.title_link);
        el.find('input[name="shop_style"]').click(function () {
            m_goods_group.get_ajax();
        })
        el.find('.is_all').click(function () {
            m_goods_group.get_ajax();
        })
       switch (parseInt(d.shop_style)){
           case 1:
               el.find('input[name="shop_style"]').eq(0).prop('checked',true);
               break;
           case 2:
               el.find('input[name="shop_style"]').eq(1).prop('checked',true);
               break;
           case 3:
               el.find('input[name="shop_style"]').eq(2).prop('checked',true);
               break;
           case 4:
               el.find('input[name="shop_style"]').eq(3).prop('checked',true);
               break;
           case 5:
               el.find('input[name="shop_style"]').eq(4).prop('checked',true);
               break;
       }
       if(d.shop_style == undefined){
           el.find('input[name="shop_style"]').eq(0).prop('checked',true);
       }
        if(d.group_display == 0){
            el.find('input[name="group_display"]').eq(1).prop("checked",true);
        }else{
            el.find('input[name="group_display"]').eq(0).prop("checked",true);
        }
       if(d.menu_style == 1){
           el.find('input[name="menu_style"]').eq(1).prop("checked",true);
       }else{
           el.find('input[name="menu_style"]').eq(0).prop("checked",true);
       }
        if(d.position_style == 1){
            el.find(".left_none").hide();
            el.find('input[name="position_style"]').eq(1).prop("checked",true);
        }else{
            el.find(".left_none").show();
            el.find('input[name="position_style"]').eq(0).prop("checked",true);
        }
        if (d.if_radius == 1) {
            el.find('input[name="if_radius"]').eq(1).prop("checked",true);
        }else{
            el.find('input[name="if_radius"]').eq(0).prop("checked",true);
        }
        if (d.module_style == 3) {
            el.find('input[name="module_style"]').eq(2).prop("checked",true);
        } else if (d.module_style == 2) {
            el.find('input[name="module_style"]').eq(1).prop("checked",true);
        } else {
            el.find('input[name="module_style"]').eq(0).prop("checked",true);
        }
        el.find("input[name='position_style'],#group_display_d,#group_display_n").click(function () {
            if($(this).val() == 0){
                el.find(".left_none").show();
            }else{
                el.find(".left_none").hide();
            }
        });
        el.find(".del-has-image").click(function(e){
            e.stopPropagation();
            // var is_del = $(".module_body .data_item img").attr('src','/image/admin/shop_beautify/add_decorete.png');
            // if(is_del)
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
        $(document).off('click',' .choose_sort');
        $(document).on('click',' .choose_sort',function(e){
            e.stopPropagation();
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                // var checkedId1 = el.find('input[name="recommend_goods_id"]').val();
                layer.open({
                    type: 2
                    , title: ['选择商家分类', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['945px','430px']
                    , content: '/admin/frame/goods/sort/select?type=3' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {
                        var iframe = layer.getChildFrame('body', index);
                        var sortArr = [];
                        var first_box = iframe.find('.first_box');
                        var second_box = iframe.find('.second_box');
                        first_box.on('click',function () {
                            if($(this).is(':checked')){
                                first_box.prop('checked',false);
                                second_box.prop('checked',false);
                                $(this).find('.second_box').prop('checked',true);
                                $(this).prop('checked',true);
                            }else{
                                first_box.prop('checked',false);
                                second_box.prop('checked',false);
                            }
                        })
                        second_box.on('click',function () {
                            if($(this).is(':checked')){
                                first_box.prop('checked',false);
                                second_box.prop('checked',false);
                                $(this).prop('checked',true);
                            }else{
                                second_box.prop('checked',false);
                            }
                        })
                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var body = layer.getChildFrame('body', index);
                        var sortSingleCheck = body.find('.first_box:checked');
                        var sortTwoCheck = body.find('.second_box:checked');
                        var sort_group = $('.clone_sort_cat .sort_group').clone();
                        var sort_id;
                        var sort_name;
                        var sort_num;
                        var sort_length;
                        var idx;
                        if(sortSingleCheck.length == 1){
                            sort_id = sortSingleCheck.val();
                            sort_name = sortSingleCheck.next().attr('sort_name');
                            sort_num = sortSingleCheck.next().attr('sort_num');
                        }else if(sortTwoCheck.length != 0){
                            sort_id = sortTwoCheck.val();
                            sort_name = sortTwoCheck.next().attr('sort_name');
                            sort_num = sortTwoCheck.next().attr('sort_num');
                        }else{
                            util.mobile_alert('请选择要添加的商品分类');
                            return false;
                        }
                        var ed = $(".module_body .data_item");
                        var elements = ed.find('.sort_group:last');
                        var is_all = elements.find(".is_all").attr("name");
                        if( is_all != undefined){
                            var is_len = is_all.slice('7') + 1;
                        }else{
                            var is_all = 0;
                        }
                        sort_group.find('.group_name span:first').text(sort_name);
                        sort_group.find('.cus_group_name input').val(sort_name);
                        sort_group.find('.group_goods1').text(sort_num + '件');
                        sort_group.find('.is_all').attr('name','is_all_'+ is_len);
                        sort_group.find('.sort_id').val(sort_id);
                        sort_group.css('display','block');
                        ed.find('.group_content').append(sort_group);
                        if(len >=10){
                            $('.add_sort_cat').addClass('hide');
                        }
                        m_goods_group.add_group_nav();
                        m_goods_group.get_ajax();
;                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                    }
                });
            });
        });
        $(document).off('click',' .add_goods');
        $(document).on('click',' .add_goods', function(e){
            var that = $(this).parent().parent().parent();
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                var checkedId1 = that.find('.group_goods_id').val();
                var sort = that.find('.sort_id').val();
                layer.open({
                    type: 2
                    , title: ['选择商品', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['945px','430px']
                    , content: '/admin/public/purchase/goods/list?is_sort=1&record_select_value='+checkedId1 + '&sort_id='+sort //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {
                        var body = layer.getChildFrame('body', index);
                        var goods_array = [];
                        body.find(".goods_item_list .goods_item").each(function(){
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
                        var count;
                        that.find('.group_goods_id').val(checkedId);
                        if(checkedId != ''){
                           count = checkedId.split(',').length;
                        }else{
                            count = 0;
                        }
                        if(count != 0){
                            that.find('.group_goods2').text(count+'件');
                        }else{
                            util.mobile_alert('请选择商品!')
                            return false;
                        }
                        m_goods_group.get_ajax();
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                        var iframe = layer.getChildFrame('body', index);
                        var checkedId = iframe.find('#record_select_value').val();
                        if(checkedId == '' && that.find('.group_goods_id').val()== ''){
                            m_goods_group.get_ajax();
                            that.find(".group_goods1").prev().prop('checked',true);
                        }
                    }
                });
            });
        });
       el.on('input propertychange','.sort_group .cus_group_name input',function () {
            m_goods_group.add_group_nav();
        });
        el.find(".sort_group .group_item_operation .up_img").off().on('click',function (e) {
                var parent = $(this).parent().parent();
                var pre = parent.prev();
                pre.insertAfter(parent);
                m_goods_group.add_group_nav();
            m_goods_group.get_ajax();
                e.preventDefault();
                e.stopPropagation()
        })
        el.find(".sort_group .group_item_operation .down_img").off().on('click',function (e) {
            var parent = $(this).parent().parent();
            var pre = parent.next();
            pre.insertBefore(parent);
            m_goods_group.add_group_nav();
            m_goods_group.get_ajax();
            e.preventDefault();
            e.stopPropagation()
        })
        el.on('click',".group_item_operation .del_img",function (e) {
            e.stopPropagation();
            var _this = $(this);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (ind) {
                    hasSaved = false;
                    var p = _this.parents(".sort_group");
                    p.remove();
                    m_goods_group.add_group_nav();
                    m_goods_group.get_ajax();
                    e.preventDefault();
                    var ed = $(".module_body .data_item");
                    var elements = ed.find('.sort_group');
                    var len = elements.length;
                    if(len <=9){
                        $('.add_sort_cat').removeClass('hide');
                    }
                    layer.close(ind);
                });
            });
        })
    },
    /**
     * 商品分组导航栏
    */
    add_group_nav:function(type = 1,data = ''){
        if(type == 1){
            var cur_m = manager.get_cur_edit_module();
            if (!cur_m) return false;
            var cur_data = cur_m.get_data();
        }else{
            var cur_data = data;
        }
        var e = manager.get_item_el(cur_data.cur_idx);
        var name_arr = cur_data.sort_group_arr;
        var origin_arr = [{group_name:'分组一'},{group_name:'分组二'},{group_name:'分组三'},{group_name:'分组四'}];
        if( cur_data.position_style == 0){
            e.find(".group_nav_left_container").hide();
            if(name_arr.length >= 4 ){
                e.find(".group_nav_show_one").hide();
                e.find(".group_nav_show_two").show();
            }else if( name_arr.length > 0 && name_arr.length < 4){
                e.find(".group_nav_show_two").hide();
                e.find(".group_nav_show_one").css('display','flex');
                e.find(".group_nav_show_one .group_nav_single").hide();
                if(cur_data.group_display == 1){
                    for(var i = 0; i< name_arr.length ;i++){
                        e.find(".group_nav_show_one .group_nav_single").eq(i+1).css('display','block');
                    }
                }else{
                    for(var i = 0; i< name_arr.length ;i++){
                        e.find(".group_nav_show_one .group_nav_single").eq(i).css('display','block');
                    }
                }
            }else{
                name_arr = origin_arr;
                e.find(".group_nav_show_one").hide();
                e.find(".group_nav_show_two").show();
            }
            if(cur_data.group_display == 1){
                e.find(".group_nav_show_one .group_nav_single_content span").eq(0).text('全部');
                e.find(".group_nav_show_two .group_nav_single_content span").eq(0).text('全部');
                for(var i = 0;i < name_arr.length;i++){
                    if(name_arr[i].group_name == ''){
                        util.mobile_alert('自定义分组名不可为空！')
                        return false;
                    } else{
                        e.find(".group_nav_show_one .group_nav_single_content span").eq(i+1).text(name_arr[i].group_name);
                        e.find(".group_nav_show_two .group_nav_single_content span").eq(i+1).text(name_arr[i].group_name);
                    }

                }
            }else{
                for(var i = 0;i < name_arr.length;i++){
                    if(name_arr[i].group_name == '') {
                        util.mobile_alert('自定义分组名不可为空！')
                        return false;
                    }else{
                        e.find(".group_nav_show_one .group_nav_single_content span").eq(i).text(name_arr[i].group_name);
                        e.find(".group_nav_show_two .group_nav_single_content span").eq(i).text(name_arr[i].group_name);
                    }
                }
            }
        }else{
            e.find(".group_nav_show_one").hide();
            e.find(".group_nav_show_two").hide();
            e.find(".group_nav_left_container").show();
            e.find(".group_nav_left .group_nav_left_icon").hide();
            if(name_arr.length <= 0){
                name_arr = origin_arr;
            }
            for(var i = 0;i < name_arr.length;i++){
                if(name_arr[i].group_name == '') {
                    util.mobile_alert('自定义分组名不可为空！')
                    return false;
                }else{
                    e.find(".group_nav_left .group_nav_left_icon").eq(i).show();
                    e.find(".group_nav_left .group_nav_left_icon span").eq(i).text(name_arr[i].group_name);
                }
            }
        }
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_goods_group").clone();
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
        var sort_group_arr = [];
        var group_goods_num;
        el.find(".group_content .sort_group").each(function (i) {
            if($(this).find(".is_all:checked").val() == 1){
                group_goods_num = parseInt($(this).find(".group_goods1").text());
            }else{
                group_goods_num = parseInt($(this).find(".group_goods2").text());
            }
            sort_group_arr[i] = {
                'sort_name':$(this).find(".sort_name").text(),
                'group_name':$(this).find("#group_name").val(),
                'sort_id':$(this).find(".sort_id").val(),
                'group_goods_id':$(this).find(".group_goods_id").val(),
                'group_goods_num':group_goods_num,
                'is_all':$(this).find(".is_all:checked").val(),
                'sort_goods_num': parseInt($(this).find(".group_goods1").text()),
            }
        })
        d.sort_group_arr = sort_group_arr;
        d.menu_style = el.find("input[name='menu_style']:checked").val();
        d.position_style = el.find("input[name='position_style']:checked").val();
        d.shop_style = el.find("input[name='shop_style']:checked").val();
        d.if_radius = el.find("input[name='if_radius']:checked").val();
        d.sort_length = el.find(".group_content ,sort_group").length;
        d.module_style = el.find("input[name='module_style']:checked").val();
        d.group_display = el.find("input[name='group_display']:checked").val();
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
    },

    get_ajax : function(){
        var data = m_goods_group.get_data();
        util.ajax_json(' /admin/ajax/mp/goods/group', function (response) {
            var content = response.content;
            console.log(content);
            if(content.goods_info != undefined){
                var goods_img = [];
                var goods_name = [];
                var goods_price = [];
                var market_price = [];
                var goods_len = content.goods_info.length;
                for(var i in content.goods_info){
                    goods_img.push(content.goods_info[i].goods_img);
                    goods_name.push(content.goods_info[i].goods_name);
                    goods_price.push(content.goods_info[i].goods_price);
                    market_price.push(content.goods_info[i].market_price);
                }
                data.goods_img = goods_img;
                data.goods_name = goods_name;
                data.goods_price = goods_price;
                data.market_price = market_price;
                data.goods_len = goods_len;
                m_goods_group.add_group_module_el(manager.get_item_el(data.cur_idx),data,data.if_radius,data.module_style,data.position_style);
                manager.change_show_module();
            }
        },{data: $.toJSON(data)});
    },

};








