var m_card = {
    init_ev_el: function(el,data){
        //var is = '{{$version_mod["m_member_card"]}}';
        console.log(data);
        if(data.card_type==0){
            el.find('.card_type').text('普通卡');
        }else if(data.card_type==1){
            el.find('.card_type').text('限次卡');
        }else if(data.card_type == 2){
            el.find('.card_type').text('等级卡');
        }
        if(data.card_name != ''){
            el.find('.card_content_right').find('div').text(data.card_name);
        }
        if(data.legal != ''){
            el.find('.card_content_right').find('p').text(data.legal);
        }else{
            el.find('.card_content_right').find('p').text('');
        }
        if(data.bg_type==0){
            el.find('.card_back_module').css('background',data.bg_color);
        }
        if(data.bg_type == 1){
            el.find('.card_back_module').css('background-image','url(' + data.bg_img + ')');
            el.find('.card_back_module').css('background-repeat','no-repeat');
            el.find('.card_back_module').css('background-size','cover');
        }
        if(data.is_pay == 1 && data.pay_fee != ''){
            el.find('.card_pay_fee').show();
            if(data.pay_type == 0){
                el.find('.card_pay_fee').text('￥'+ data.pay_fee + '元'); 
            } else if(data.pay_type == 1) {
                el.find('.card_pay_fee').text(data.pay_fee.substring(0,data.pay_fee.length - 3) + '积分');
            }
        } else {
            el.find('.card_pay_fee').hide();
        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        if(d.card_id != '' && d.card_id != undefined){
            el.find('.card_right_content').show();
            el.find(".card_add").hide();
        }
        el.find('.card_name').text(d.card_name);
        if(d.card_type==2){
            el.find('.card_state').hide();
            el.find('.card_grade').show();
            el.find('.card_grade').text(d.card_grade);
        }else{
            el.find('.card_state').show();
            el.find('.card_grade').hide();
            el.find('.card_state').text(d.card_state);
        }
        el.find('.card_right_content p').text(d.receive_day);
        el.find('#card_info').attr('card_id',d.card_id);
        el.find('#card_info').attr('card_type',d.card_type);
        el.find('#card_info').attr('legal',d.legal);
        el.find('#card_info').attr('bg_type',d.bg_type);
        el.find('#card_info').attr('bg_color',d.bg_color);
        el.find('#card_info').attr('bg_img',d.bg_img);
        if(d.bg_type==0){
            el.find('.card_back').css('background',d.bg_color);
        }
        if(d.bg_type == 1){
            el.find('.card_back').css('background-image','url(' + d.bg_img + ')');
            el.find('.card_back').css('background-repeat','no-repeat');
            el.find('.card_back').css('background-size','cover');
        }
        if(d.card_type == 2){
            el.find('.card_back').find('p').hide();
        }
        m_card.add_card_info(el,d);
    },
    add_card_info:function(el,d){
        el.find(".card_add_click").click(function(){
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['选择会员卡', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['620px','420px']
                    , content: '/admin/frame/card/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {
                        var iframe = layer.getChildFrame('body', index);
                        if(el.find('.card_back').find('#card_info').attr('card_id') > 0){
                            iframe.find('.card_list').each(function () {
                                if($(this).find('.card_info').attr('card_id') == el.find('.card_back').find('#card_info').attr('card_id')){
                                    $(this).addClass('card_list_active');
                                }
                            });
                        }
                        iframe.find('.card_list').click(function () {
                            $(this).addClass('card_list_active').siblings().removeClass('card_list_active');
                        })
                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var list_active = iframe.find('.card_list_active');
                        var card_name = list_active.find('.card_name').text();//会员卡名
                        var receive_day = list_active.find('.receive_day').text();//有效期
                        receive_day = receive_day.replace(/\s+/g,"");//去空格
                        var card_state = list_active.find('.card_state').text();//使用中或未生效
                        card_state = card_state.replace(/\s+/g,"");
                        var card_id = list_active.find('.card_info').attr('card_id');//卡id
                        var card_type = list_active.find('.card_info').attr('card_type');//卡类型
                        var legal = list_active.find('.card_info').attr('legal');//卡权益
                        var bg_type = list_active.find('.card_info').attr('bg_type');//背景类型
                        var bg_color = list_active.find('.card_info').attr('bg_color');
                        var bg_img = list_active.find('.card_info').attr('bg_img');
                        var is_pay = list_active.find('.card_info').attr('is_pay');
                        var pay_type = list_active.find('.card_info').attr('pay_type');
                        var pay_fee = list_active.find('.card_info').attr('pay_fee');
                        var card_grade = list_active.find('.card_grade').text();
                        bg_img = url + bg_img;
                        el.find('.card_right_content').show();
                        el.find('.card_name').text(card_name);
                        if(card_type == 2){
                            el.find('.card_state').hide();
                            el.find('.card_grade').show();
                            el.find('.card_grade').text(card_grade);
                        }else{
                            el.find('.card_state').show();
                            el.find('.card_grade').hide();
                            el.find('.card_state').text(card_state);
                        }
                        el.find('.card_right_content p').text(receive_day);
                        el.find('#card_info').attr('card_id',card_id);
                        el.find('#card_info').attr('card_type',card_type);
                        el.find('#card_info').attr('legal',legal);
                        el.find('#card_info').attr('bg_type',bg_type);
                        el.find('#card_info').attr('bg_color',bg_color);
                        el.find('#card_info').attr('bg_img',bg_img);
                        el.find('#card_info').attr('pay_fee',pay_fee);
                        el.find('#card_info').attr('is_pay',is_pay);
                        el.find('#card_info').attr('pay_type',pay_type);
                        if(bg_type==0){
                            el.find('.card_back').css('background',bg_color);
                        }
                        if(bg_type == 1){
                            el.find('.card_back').css('background-image','url(' + bg_img + ')');
                            el.find('.card_back').css('background-repeat','no-repeat');
                            el.find('.card_back').css('background-size','cover');
                        }
                        if(card_type == 2){
                            el.find('.card_back').find('p').hide();
                        }else{
							el.find('.card_back').find('p').show();
						}
                        el.find(".card_add").hide();
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
        var el = $("#template_list .d_m_card").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.card_id = el.find('#card_info').attr('card_id');
        // if(d.card_id == ""){
        //     util.mobile_alert('请选择会员卡');
        //     $('#module_edit').show();
        //     return false;
        // }
        d.card_name = el.find('.card_name').text();
        d.card_state = el.find('.card_state').text();
        d.card_grade = el.find('.card_grade').text();
        d.receive_day = el.find('.card_right_content p').text();
        d.card_type = el.find('#card_info').attr('card_type');
        d.legal = el.find('#card_info').attr('legal');
        d.bg_type = el.find('#card_info').attr('bg_type');
        d.bg_color = el.find('#card_info').attr('bg_color');
        d.bg_img = el.find('#card_info').attr('bg_img');
        d.is_pay = el.find('#card_info').attr('is_pay');
        d.pay_type = el.find('#card_info').attr('pay_type');
        d.pay_fee = el.find('#card_info').attr('pay_fee');
        return d;
    }
};