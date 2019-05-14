var m_coupon = {
    init_ev_el: function(el,data){
        if(is_coupon == 0){
            el.find('.no_use').show();
        }
        if(data.coupon_arr){
            if(data.coupon_arr.length >= 1){
                if(typeof (data.coupon_arr) == 'string'){
                    data.coupon_arr = JSON.parse(data.coupon_arr);
                }
                if(data.coupon_arr.length >= 3){
                    el.find('.coupon_module3').show();
                    el.find('.coupon_module2').hide();
                    el.find('.coupon_module1').hide();
                    el.find('.coupon_module3').find('.coupon_list').each(function (i,v) {
                        if(data.coupon_arr[i].act_code == "discount"){
                            $(v).find('.coupon_list_top').html('<span>' + data.coupon_arr[i].denomination + '</span>折');
                        }
                        if(data.coupon_arr[i].act_code == "voucher"){
                            $(v).find('.coupon_list_top').html('￥<span>' + data.coupon_arr[i].denomination + '</span>');
                        }
                        $(v).find('.coupon_center_limit').text(data.coupon_arr[i].consume_text);
                        $(v).find('.coupon_center_number').text(data.coupon_arr[i].receive_text);
                        if (data.coupon_arr[i].use_score == 1) {
                            $(v).find('.coupon_list_bottom').text(data.coupon_arr[i].score_number + '积分 兑换');
                        }

                    });
                }
                if(data.coupon_arr.length == 2){
                    el.find('.coupon_module2').show();
                    el.find('.coupon_module1').hide();
                    el.find('.coupon_module3').hide();
                    el.find('.coupon_module2').find('.coupon_list').each(function (i,v) {
                        if(data.coupon_arr[i].act_code == "discount"){
                            $(v).find('.coupon_list_top').html('<span>' + data.coupon_arr[i].denomination + '</span>折');
                        }
                        if(data.coupon_arr[i].act_code == "voucher"){
                            $(v).find('.coupon_list_top').html('￥<span>' + data.coupon_arr[i].denomination + '</span>');
                        }
                        $(v).find('.coupon_center_limit').text(data.coupon_arr[i].consume_text);
                        $(v).find('.coupon_center_number').text(data.coupon_arr[i].receive_text);
                        if (data.coupon_arr[i].use_score == 1) {
                            $(v).find('.coupon_list_bottom').text(data.coupon_arr[i].score_number + '积分 兑换');
                        }
                    });
                }
                if(data.coupon_arr.length == 1){
                    el.find('.coupon_module1').show();
                    el.find('.coupon_module2').hide();
                    el.find('.coupon_module3').hide();
                    el.find('.coupon_module1').find('.coupon_list').each(function (i,v) {
                        if(data.coupon_arr[i].act_code == "discount"){
                            $(v).find('.coupon_list_top').html('<span>' + data.coupon_arr[i].denomination + '</span>折');
                        }
                        if(data.coupon_arr[i].act_code == "voucher"){
                            $(v).find('.coupon_list_top').html('￥<span>' + data.coupon_arr[i].denomination + '</span>');
                        }
                        $(v).find('.coupon_center_limit').text(data.coupon_arr[i].consume_text);
                        $(v).find('.coupon_center_number').text(data.coupon_arr[i].receive_text);
                        if (data.coupon_arr[i].use_score == 1) {
                            $(v).find('.coupon_list_bottom').text(data.coupon_arr[i].score_number + '积分 兑换');
                        }
                    });
                }
            }else{
                el.find('.coupon_module').find('.coupon_list').each(function (i,v) {
                    $(v).find('.coupon_list_top').html('￥<span>××</span>');
                    $(v).find('.coupon_center_limit').text('满××使用');
                    $(v).find('.coupon_center_number').text('剩余××张');
                });
            }
        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.coupon_arr){
            if(typeof (d.coupon_arr) == 'string'){
                d.coupon_arr = JSON.parse(d.coupon_arr);
            }
            var coupon_json = JSON.stringify(d.coupon_arr);
            el.find('.coupon_div').show();
            el.find('.coupon_div').attr('coupon_json',coupon_json);
            for(var j in d.coupon_arr){
                var coupon_clone = el.find('.coupon_list_clone').find('.coupon_list').clone();
                el.find('.coupon_div').append(coupon_clone);
            }
            el.find('.coupon_div').find('.coupon_list').each(function (i,v) {
                $(v).find('.coupon_info').attr('coupon_id',d.coupon_arr[i].coupon_id);
                $(v).find('.coupon_info').attr('act_code',d.coupon_arr[i].act_code);
                $(v).find('.coupon_info').attr('denomination',d.coupon_arr[i].denomination);
                if(d.coupon_arr[i].act_code == "discount"){
                    $(v).find('.coupon_list_top').html('<span>' + d.coupon_arr[i].denomination + '</span>折');
                }
                if(d.coupon_arr[i].act_code == "voucher"){
                    $(v).find('.coupon_list_top').html('￥<span>' + d.coupon_arr[i].denomination + '</span>');
                }
                $(v).find('.coupon_center_limit').text(d.coupon_arr[i].consume_text);
                $(v).find('.coupon_center_number').text(d.coupon_arr[i].receive_text);
            });
        }
        m_coupon.add_coupon_info(el,d);
        el.find('.coupon_div').on('click','.coupon_del',function () {
            var coupon_arr = JSON.parse(el.find('.coupon_div').attr('coupon_json'));
            for(var i in coupon_arr){
                if(coupon_arr[i].coupon_id == $(this).next().attr('coupon_id')){
                    coupon_arr.splice(i,1);
                }
            }
            console.log(coupon_arr);
            el.find('.coupon_div').attr('coupon_json',JSON.stringify(coupon_arr));
            $(this).parent().remove();
            if(coupon_arr.length<6){
                $('.card_add_click').show();
            }
            manager.change_show_module();
            hasSaved = false;
        });
    },
    add_coupon_info:function(el,d){
        el.find(".card_add_click").click(function(){
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['选择优惠券', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['520px','420px']
                    , content: '/admin/frame/coupon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {
                        var iframe = layer.getChildFrame('body', index);
                        if(el.find('.coupon_div').find('.coupon_list').size() > 0){
                            el.find('.coupon_div').find('.coupon_list').each(function () {
                                var _this = $(this);
                                iframe.find('.coupon_list').each(function () {
                                    if($(this).find('.coupon_info').attr('coupon_id') == _this.find('.coupon_info').attr('coupon_id')){
                                        $(this).addClass('card_list_active');
                                    }
                                });
                            });
                        }
                        iframe.find('.coupon_list').click(function () {
                            if($(this).hasClass('card_list_active')){
                                $(this).removeClass('card_list_active');
                            }else{
                                $(this).addClass('card_list_active');
                            }
                        });
                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var list_active = iframe.find('.card_list_active');
                        if($(list_active).size() == 0){
                            util.mobile_alert('请选择优惠券');
                            return;
                        }
                        if($(list_active).size() > 6){
                            util.mobile_alert('最多只能选择6张优惠券哦~');
                            return;
                        }
                        var coupon_arr = [];
                        el.find('.coupon_div').html('');
                        $(list_active).each(function (i) {
                            coupon_arr[i] = {};
                            coupon_arr[i].act_code = $(this).find('.coupon_info').attr('act_code');
                            coupon_arr[i].denomination = $(this).find('.coupon_info').attr('denomination');
                            coupon_arr[i].consume_text = $(this).find('.coupon_center_limit').text().replace(/\s+/g,"");
                            coupon_arr[i].receive_text = $(this).find('.coupon_center_number').text().replace(/\s+/g,"");
                            coupon_arr[i].coupon_id = $(this).find('.coupon_info').attr('coupon_id');
                            coupon_arr[i].use_score = $(this).find('.coupon_info').attr('use_score');
                            coupon_arr[i].score_number = $(this).find('.coupon_info').attr('score_number');
                            var coupon_clone = el.find('.coupon_list_clone').find('.coupon_list').clone();
                            el.find('.coupon_div').show();
                            el.find('.coupon_div').append(coupon_clone);
                            var coupon_json = JSON.stringify(coupon_arr);
                            el.find('.coupon_div').attr('coupon_json',coupon_json);

                        });
                        el.find('.coupon_div').find('.coupon_list').each(function (j,v) {
                            $(v).find('.coupon_info').attr('act_code',coupon_arr[j].act_code);
                            $(v).find('.coupon_info').attr('denomination',coupon_arr[j].denomination);
                            $(v).find('.coupon_info').attr('coupon_id',coupon_arr[j].coupon_id);
                            $(v).find('.coupon_info').attr('use_score',coupon_arr[j].use_score);
                            $(v).find('.coupon_info').attr('score_number',coupon_arr[j].score_number);
                            if(coupon_arr[j].act_code == "discount"){
                                $(v).find('.coupon_list_top').html('<span>' + coupon_arr[j].denomination + '</span>折');
                            }
                            if(coupon_arr[j].act_code == "voucher"){
                                $(v).find('.coupon_list_top').html('￥<span>' + coupon_arr[j].denomination + '</span>');
                            }
                            $(v).find('.coupon_center_limit').text(coupon_arr[j].consume_text);
                            $(v).find('.coupon_center_number').text(coupon_arr[j].receive_text);
                            if (coupon_arr[j].use_score == 1) {
                                $(v).find('.coupon_list_bottom').text(coupon_arr[j].score_number+'积分 兑换');
                            }
                        });
                        if(coupon_arr.length==6){
                            $(".card_add_click").hide();
                        }else{
                            $(".card_add_click").show();
                        }
                        // console.log(coupon_arr);
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
        var el = $("#template_list .d_m_coupon").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        if(el.find('.coupon_div').attr('coupon_json')){
            d.coupon_arr = JSON.parse(el.find('.coupon_div').attr('coupon_json'));
        }
        return d;
    }
};