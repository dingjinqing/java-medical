var m_bargain = {
    init_ev_el: function(el,data){
        if(is_bargain == 0){
            el.find('.no_use').show();
        }
        var now_time = new Date();
        var now_time_s = now_time.getTime();
        if(data.bargain_goods){
            if(data.bargain_goods.length > 0){
                el.find('.bargain_default_ul').html('');
                if(data.list_style == 1){
                    for(var i in data.bargain_goods){
                        var bargain_single = el.find('.bargain_ul_clone').find('.single_act').clone();
                        el.find('.bargain_default_ul').append(bargain_single);
                    }
                }else{
                    for(var j in data.bargain_goods){
                        var bargain_double = el.find('.bargain_ul_clone').find('.double_act').clone();
                        el.find('.bargain_default_ul').append(bargain_double);
                    }
                }
                el.find('.bargain_default_ul').find('li').each(function (key,value) {
                    $(value).find('.bargain_default_img').find('img').attr('src',data.bargain_goods[key].goods_img);
                    $(value).find('.bargain_info_name').text(data.bargain_goods[key].goods_name);
                    $(value).find('.bargain_num').text('仅剩' + data.bargain_goods[key].bargain_num + '件');
                    $(value).find('.bargain_price').find('span').text(data.bargain_goods[key].expectation_price);
                    if(data.goods_price){
                        $(value).find('.bargain_old').show();
                        $(value).find('.bargain_old').find('span').text(data.bargain_goods[key].goods_price);
                    }else{
                        $(value).find('.bargain_old').hide();
                    }
                    if(data.goods_count_down){
                        $(value).find('.bargain_time_down').show();
                        if(data.bargain_goods[key].act_status == 1){
                            $(value).find('.bargain_time_down').find('div').text('距结束还剩');
                            var end_time =  new Date(data.bargain_goods[key].act_end_time);
                            var end_time_s = end_time.getTime();
                            var ne_total = (end_time_s - now_time_s)/1000;
                            var day = parseInt(ne_total / (24*60*60));//计算整数天数
                            var afterDay = ne_total - day*24*60*60;//取得算出天数后剩余的秒数
                            var hour = parseInt(afterDay/(60*60));//计算整数小时数
                            var afterHour = ne_total - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
                            var min = parseInt(afterHour/60);//计算整数分
                            var second = Math.ceil(ne_total - day*24*60*60 - hour*60*60 - min*60);//取得算出分后剩余的秒数
                            // if(data.list_style == 1){
                            //     $(value).find('.bargain_time_down').find('p').text('距结束' + day+'天'+hour+'时'+min+'分');
                            // } else {
                            //     $(value).find('.bargain_time_down').find('p').text('距结束：' + day+'天'+hour+'时'+min+'分');
                            // }
                                $(value).find('.bargain_time_down').find('p').text(day+'天'+hour+'时'+min+'分');
                            // $(value).find('.bargain_time_down').find('p').text(data.bargain_goods[key].act_end_time);
                        }else{
                            // $(value).find('.bargain_time_down').find('div').text('开始时间');
                            var md = data.bargain_goods[key].act_begin_time.split("-");

                             var md2 = md[2].split(" ");
                             var new_md = md[1]+'月'+md2[0]+'日'+" " +md2[1];
                            $(value).find('.bargain_time_down').find('p').text(new_md);
                            // $(value).find('.bargain_time_down').find('p').text(data.bargain_goods[key].act_begin_time);
                        }

                    }else{
                        $(value).find('.bargain_time_down').hide();
                    }
                    if(data.free_btn){
                        $(value).find('.bargain_free_btn').show();
                    }else{
                        $(value).find('.bargain_free_btn').hide();
                    }
                });
            }
        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.list_style == 1){
            el.find('#single_list').prop('checked',true);
        }else{
            el.find('#double_list').prop('checked',true);
        }
        if(d.bargain_goods){
            if(d.bargain_goods.length > 0){
                el.find('.bargain_goods_tb').show();
                var bargain_goods_json = JSON.stringify(d.bargain_goods);
                el.find('.bargain_goods_tb').attr('bargain_goods_json',bargain_goods_json);
                for(var i in d.bargain_goods){
                    var bargain_select_tr = el.find('.bargain_table_clone').find('.bargain_select_tr').clone();
                    el.find('.bargain_goods_tb').find('.bargain_select_th').after(bargain_select_tr);
                }
                el.find('.bargain_goods_tb').find('.bargain_select_tr').each(function (i,v) {
                    $(v).find('.bargain_select_img').attr('src',d.bargain_goods[i].goods_img);
                    $(v).find('.bargain_select_name').text(d.bargain_goods[i].goods_name);
                    $(v).find('.expectation_price').text(d.bargain_goods[i].expectation_price);
                    $(v).find('td').eq(2).text(d.bargain_goods[i].bargain_num);
                    $(v).find('td').eq(1).text(d.bargain_goods[i].act_begin_time);
                    $(v).find('.bargain_select_del').attr("goods_id",d.bargain_goods[i].goods_id);
                    $(v).find('.bargain_select_del').attr("act_id",d.bargain_goods[i].act_id);
                    var now_time = new Date();
                    var now_time_s = now_time.getTime()
                    var end_time =  new Date(d.bargain_goods[i].act_end_time);
                    var end_time_s = end_time.getTime();
                    var ne_total = (end_time_s - now_time_s)/1000;
                    if(d.bargain_goods[i].act_status === 1 || ne_total < 0 && d.bargain_goods[i].act_del_flag != 1){
                        $(v).find('td').eq(4).text('活动停用')
                    } else if(d.bargain_goods[i].act_del_flag === 1){
                        $(v).find('td').eq(4).text('活动删除')
                    } else if(d.bargain_goods[i].goods_is_delete === 1){
                        $(v).find('td').eq(4).text('商品删除')
                    } else if(d.bargain_goods[i].goods_is_on_sale === 0){
                        $(v).find('td').eq(4).text('商品下架')
                    }
                });
            }
        }
        if(d.goods_price == false){
            el.find('input[name="goods_price"]').prop('checked',false);
        }else{
            el.find('input[name="goods_price"]').prop('checked',true);
        }
        if(d.goods_count_down == false){
            el.find('input[name="goods_count_down"]').prop('checked',false);
        }else{
            el.find('input[name="goods_count_down"]').prop('checked',true);
        }
        if(d.free_btn == false){
            el.find('input[name="free_btn"]').prop('checked',false);
        }else{
            el.find('input[name="free_btn"]').prop('checked',true);
        }
        m_bargain.add_card_info(el,d);
        el.find('.bargain_item').on('click','.bargain_select_del',function () {
            var bargain_arr = JSON.parse(el.find('.bargain_goods_tb').attr('bargain_goods_json'));
            for(var i in bargain_arr){
                if(bargain_arr[i].goods_id == $(this).attr('goods_id')){
                    bargain_arr.splice(i,1);
                }
            }
            console.log(bargain_arr);
            el.find('.bargain_goods_tb').attr('bargain_goods_json',JSON.stringify(bargain_arr));
            $(this).parent().parent().remove();
            if(bargain_arr.length==0){
                el.find('.bargain_goods_tb').hide();
            }
            manager.change_show_module();
            hasSaved = false;
        });
        el.find("input").change(function () {
            manager.change_show_module();
        })
    },
    add_card_info:function(el,d){
        el.find(".bargain_add_goods").click(function(){
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['选择砍价活动', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['825px','420px']
                    , content: '/admin/frame/market/bargain/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {
                        var iframe = layer.getChildFrame('body', index);
                        var iframe_tr = iframe.find('.goods_tb').find('tbody').find('tr');
                        var acr_i = 0;
                        iframe_tr.click(function () {
                            var flag_back = $(this).attr('data-back');
                            if(flag_back == 'true'){
                                acr_i += 1;
                                if(acr_i >= 7){
                                    util.mobile_alert('最多只能添加6个商品');
                                    acr_i = 6;
                                    return;
                                }
                                $(this).addClass('goods_tr_choose');
                                $(this).attr('data-back','false');
                                flag_back = 'false';
                            }else if(flag_back == 'false'){
                                acr_i -= 1;
                                $(this).removeClass('goods_tr_choose');
                                $(this).attr('data-back','true');
                                flag_back = 'true';
                            }
                        });
                        if(el.find('.bargain_goods_tb').find('.bargain_select_tr').size() > 0){
                            acr_i = el.find('.bargain_goods_tb').find('.bargain_select_tr').size();
                            el.find('.bargain_goods_tb').find('.bargain_select_tr').each(function () {
                                var _this = $(this);
                                iframe.find('.goods_tb').find('tr').each(function () {
                                    if($(this).attr('goods_id') == _this.find('.bargain_select_del').attr('goods_id')){
                                        $(this).addClass('goods_tr_choose');
                                        $(this).attr('data-back','false')
                                    }
                                });
                            });
                        }
                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var iframe_tr = iframe.find('.goods_tr_choose');
                        if(iframe_tr.length >= 7){
                            util.mobile_alert('最多只能添加6个商品');
                            return;
                        }
                        var bargain_goods = [];
                        el.find('.bargain_goods_tb').find('.bargain_select_tr').remove();
                        $.each(iframe_tr,function (idx,val) {
                        //for(var idx in iframe_tr){
                            bargain_goods[idx] = {};
                            bargain_goods[idx].goods_id = $(val).attr('goods_id');
                            bargain_goods[idx].act_id = $(val).attr('act_id');
                            bargain_goods[idx].goods_img = $(val).find('.goods_img').find('img').attr('src');
                            bargain_goods[idx].goods_name = $(val).find('.goods_name').text();
                            bargain_goods[idx].goods_name = $(val).find('.goods_name').text();
                            bargain_goods[idx].goods_price = $(val).find('td').eq(2).text();
                            if($(val).find('td').eq(2).text().split('.')[0].length>=5){
                                bargain_goods[idx].goods_price = $(val).find('td').eq(2).text().split('.')[0];
                            }
                            bargain_goods[idx].expectation_price = $(val).find('td').eq(3).text();
                            bargain_goods[idx].bargain_num = $(val).find('td').eq(4).text();
                            bargain_goods[idx].act_begin_time = $(val).find('td').eq(5).text();
                            bargain_goods[idx].act_status = $(val).find('td').eq(5).attr('is_status');
                            bargain_goods[idx].is_on_sale = $(val).find('td').eq(5).attr('is_on_sale');
                            bargain_goods[idx].is_delete = $(val).find('td').eq(5).attr('is_delete');
                            bargain_goods[idx].act_end_time = $(val).find('td').eq(6).text();
                            console.log(bargain_goods);
                            var bargain_select_tr = el.find('.bargain_table_clone').find('.bargain_select_tr').clone();
                            el.find('.bargain_goods_tb').find('.bargain_select_th').after(bargain_select_tr);
                            var bargain_goods_json = JSON.stringify(bargain_goods);
                            el.find('.bargain_goods_tb').attr('bargain_goods_json',bargain_goods_json);
                        //}
                        });
                        console.log(bargain_goods);
                        el.find('.bargain_goods_tb').show();
                        el.find('.bargain_goods_tb').find('.bargain_select_tr').each(function (i,v) {
                            $(v).find('.bargain_select_img').attr('src',bargain_goods[i].goods_img);
                            $(v).find('.bargain_select_name').text(bargain_goods[i].goods_name);
                            $(v).find('.expectation_price').text(bargain_goods[i].expectation_price);
                            $(v).find('td').eq(3).text(bargain_goods[i].bargain_num);
                            $(v).find('td').eq(1).text(bargain_goods[i].act_begin_time);
                            $(v).find('.bargain_select_del').attr("goods_id",bargain_goods[i].goods_id);
                            $(v).find('.bargain_select_del').attr("act_id",bargain_goods[i].act_id);
                            if(bargain_goods[i].is_delete === "1"){
                                $(v).find('td').eq(4).text('商品删除')
                            } else if(bargain_goods[i].is_on_sale === "0"){
                                $(v).find('td').eq(4).text('商品下架')
                            }
                        });
                        manager.change_show_module();
                        hasSaved = false;
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                    }
                });
            });
        });
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_bargain").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.list_style = el.find('input[name="list_style"]:checked').val();
        d.goods_price = el.find('input[name="goods_price"]').prop('checked');
        d.goods_count_down = el.find('input[name="goods_count_down"]').prop('checked');
        d.free_btn = el.find('input[name="free_btn"]').prop('checked');
        if(el.find('.bargain_goods_tb').attr('bargain_goods_json')){
            d.bargain_goods = JSON.parse(el.find('.bargain_goods_tb').attr('bargain_goods_json'));
        }
        // if(!d.bargain_goods || d.bargain_goods.length == 0){
        //     util.mobile_alert('请选择活动商品');
        //     return;
        //     $("#module_edit").show();
        // }
        return d;
    }
};