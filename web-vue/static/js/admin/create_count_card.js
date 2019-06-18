$(function () {
    $("input[name='bg_type']").click(function () {
        $("input[name='bg_type']").removeClass('checkbox_actives');
        $(this).addClass('checkbox_actives');
    });

    $("input[name='expire_type']").click(function () {
        $("input[name='expire_type']").removeClass('checkbox_actives');
        $(this).addClass('checkbox_actives');
    });
    $("input[name='activation']").click(function () {
        $("input[name='activation']").removeClass('checkbox_actives');
        $(this).addClass('checkbox_actives');
        if($(this).val() == 1){
            $(".activation_cfg").show();
        }else{
            $(".activation_cfg").hide();
        }
    });
    $("input[name='store_list_type']").click(function () {
        $("input[name='store_list_type']").removeClass('checkbox_actives');
        $(this).addClass('checkbox_actives');
        if($('input[name="store_list_type"]:checked').val()==1){
            $('.add_store').removeAttr("hidden");
        }
    });
    /*添加背景图*/
    $('.add_img').click(function() {
        var w = 540;
        var h = 300;
        var that = $(this);
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                that.html("");
                that.append("<img src='"+path+"'>");
                var index = path.lastIndexOf('/upload');
                var enen = path.substring(index+1,path.length);
                $("#bg_img_path").val(enen);
                hasSaved = false;
                if($("#bg_img").is(":checked")){
                    if($(".add_img").html()!= ""){
                        $(".example_card").css("background","url('"+$(".add_img img").attr("src")+"')");
                        $(".example_card").css("background-size","100%");
                    }
                }
            }
        });
    });

    /*选择背景颜色*/
    $(":text[name='bg_color']").bigColorpicker(function(el,color){
        $(el).val(color);
        hasSaved = false;
    });
    $(":text[name='bg_color']").blur(function () {
        $(this).css("background",$(this).val());
    });

    /*提交信息时的一些验证*/
    $(".btn_save").click(function (e) {
        e.stopPropagation();
        var reg = /^\d+(?=\.{0,1}\d+$|$)/;

        var cards_name = $(":text[name='card_name']").val();
        var member_discount = $(":text[name='member_disconut']").val();
        var user_notice = $("textarea").val();

        if(cards_name == ""){
            util.mobile_alert('会员卡名称不能为空');
            return false;
        }
        if($("input[name='store_list_type']:checked").val() != -1){
            if($("input[name='count']").val() !=""){
                if(!reg.test($("input[name='count']").val())){
                    util.mobile_alert("允许使用次数请输入大于0的数字");
                    return false;
                }
            }else{
                util.mobile_alert("请输入允许使用次数");
                return false;
            }
            if($("input[name='use_time_type']:checked").length<1){
                util.mobile_alert("至少选择一项使用时间");
                return false;
            }
        }
        if($("input[name='is_exchang']:checked").val() != 0){
            if($("input[name='exchang_count']").val() !=""){
                if(!reg.test($("input[name='exchang_count']").val())){
                    util.mobile_alert("可兑换商品数量请输入大于0的数字");
                    return false;
                }
            } else {
                util.mobile_alert("请输入可兑换商品数量");
                return false;
            }
        }
        // if($("input[name='use_time_type']:checked").length == 0){
        //     layer.msg("请选择允许使用时间");
        //     return false;
        // }

        /*验证部分输入框只能输入数字*/
        if($('input[name="expire_type"]:checked').val() == '0'){
            if($('input[name="start_time"]:eq(0)').val() == '' && $('input[name="end_time"]:eq(0)').val() == '') {
                util.mobile_alert("请输入有效期");
                return false;
            }
        } else if (parseInt($('input[name="expire_type"]:checked').val()) == 1){
            if($('input[name="receive_day"]:eq(0)').val() == ''){
                util.mobile_alert("请输入有效期");
                return false;
            }
        }
        //会员卡购买验证
        if($('input[name="is_pay"]:checked').val() == '1'){
            if($('input[name="pay_type"]:checked').val() == '0' && $('input[name="pay_money"]').val() == ''){
                util.mobile_alert("请输入金额");
                $('input[name="pay_money"]').focus();
                return false;
            } else if ($('input[name="pay_type"]:checked').val() == '1' && $('input[name="pay_score"]').val() == '') {
                util.mobile_alert("请输入积分");
                $('input[name="pay_score"]').focus();
                return false;
            }
        }
        if($('input[name="is_pay"]:checked').val() == 2){
            if($('input[name="batch_name"]:disabled').length == 0){
                util.mobile_alert("请至少生成一项领取码");
                return false
            }
        }
        //验证激活配置
        if($("input[name='activation']:checked").val() == 1){
            if($('input[name="activation_cfg_box"]:checked').length<1){
                util.mobile_alert("请选择激活配置信息");
                return false;
            }else{
                var activation_cfg = [];
                $('input[name="activation_cfg_box"]:checked').each2(function (i,v) {
                    activation_cfg.push($(v).val());
                })
                $("input[name='activation_cfg']").val(activation_cfg);
            }
        }

        //选择部分门店时，必须选择门店
        if($('input[name="store_list_type"]:checked').val() == 1){
            if($('input[name="store_list"]').val() == '' || $('input[name="store_list"]').val()==0){
                util.mobile_alert("请添加门店");
                return false;
            }
        }
        /*验证正确格式的手机号*/
        // var re=/^[1][3,4,5,6,7,8,9][0-9]{9}$/;
        // var re=/(^(\d{3,4}-)?\d{7,8})$|(1[3|5|6|7|8|9]\d{9})/ ;
        //
        // if($(":text[name='mobile']").val() != ""){
        //     if(!re.test($(":text[name='mobile']").val())){
        //         util.mobile_alert("请输入有效的电话号码");
        //         return false;
        //     }
        //     // if(!re.test($(":text[name='mobile']").val())){
        //     //     util.mobile_alert("请输入有效的手机号");
        //     //     return false;
        //     // }
        // }
        if($("input[name='use_time_type']:checked").length>1){
            $("input[name='use_time']").val(0);
        }else{
            $("input[name='use_time']").val($("input[name='use_time_type']:checked").val());
        }

        if(act=='edit'){
            //修改提示
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                // var checkedId1 = el.find('input[name="recommend_goods_id"]').val();
                layer.open({
                    type: 1
                    , title: ['提示', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['350px','260px']
                    , content: $(".second_edit") //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确认并保存', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {

                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        hasSaved = true;
                        layer.ready(function () {
                            layer.msg('保存成功', {time: 2000},function () {
                                var batch_arr = [], receive_action = $('[name="receive_action"]:checked').val(),
                                    obj = receive_action == 1 ? $('.code_set') : $('.key_set');

                                $.each(obj.find('input[name="batch_id[]"]'), function (index, item) {
                                    batch_arr[index] = $(item).val();
                                })
                                if (batch_arr.length > 0) {
                                    $('input[name="batch_id_str"]').val(batch_arr.join(','));
                                }
                                $("#form1").submit();
                            });
                        });
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                        layer.close(index);
                        return false;
                    }
                });
            });
        }else{
            hasSaved = true;
            layer.ready(function () {
                layer.msg('保存成功', {time: 2000},function () {
                    var batch_arr = [], receive_action = $('[name="receive_action"]:checked').val(),
                        obj = receive_action == 1 ? $('.code_set') : $('.key_set');

                    $.each(obj.find('input[name="batch_id[]"]'), function (index, item) {
                        batch_arr[index] = $(item).val();
                    })
                    if (batch_arr.length > 0) {
                        $('input[name="batch_id_str"]').val(batch_arr.join(','));
                    }
                    $("#form1").submit();
                });
            });
        }
    });

    util.inputChange();
    util.selectChange();
    util.checkboxChange();
    util.radioChange('expire_type');
    util.radioChange('store_list_type');
    util.radioChange('bg_type');
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗';
        }
    };
    /*左侧数据展示*/
    /*名字*/
    if($("input[name='card_name']").val() != ""){
        $(".em_card_name").html($("input[name='card_name']").val());
    }
    $("input[name='card_name']").change(function () {
        /*名字*/
        if($("input[name='card_name']").val() != ""){
            $(".em_card_name").html($("input[name='card_name']").val());
        }
    });
    /*背景色*/
    if($("#bg_color").is(":checked")){
        if($("input[name='bg_color']").val() != ""){
            $(".example_card").css("background",$("input[name='bg_color']").val());
        }
    }
    $("input[name='bg_color']").blur(function () {
        if($("#bg_color").is(":checked")){
            if($("input[name='bg_color']").val() != ""){
                $(".example_card").css("background",$("input[name='bg_color']").val());
            }
        }
    });
    $("input[name='bg_type']").change(function () {
        if($("#bg_color").is(":checked")){
            if($("#bg_color").is(":checked")){
                if($("input[name='bg_color']").val() != ""){
                    $(".example_card").css("background",$("input[name='bg_color']").val());
                }
            }
            $("input[name='bg_color']").blur(function () {
                if($("input[name='bg_color']").val() != ""){
                    $(".example_card").css("background",$("input[name='bg_color']").val());
                }
            })
        }
    });
    /*背景图*/
    if($("#bg_img").is(":checked")){
        if($(".add_img").html()!= ""){
            $(".example_card").css("background","url('"+$(".add_img img").attr("src")+"')");
            $(".example_card").css("background-size","100%");
        }
    }
    $("input[name='bg_type']").change(function () {
        if($("#bg_img").is(":checked")){
            if($(".add_img").html()!= ""){
                $(".example_card").css("background","url('"+$(".add_img img").attr("src")+"')");
                $(".example_card").css("background-size","100%");
            }
        }
    });
    /*有效期-确定日期*/
    if($("#fixed_date").is(":checked")){
        var st_ti = $("input[name='start_time']").val();
        var end_ti = $("input[name='end_time']").val();
        $(".effect_date span").html(st_ti+" - "+end_ti);
    }
    if($("#fixed_date").is(":checked")){
        var st_ti = $("input[name='start_time']").val();
        var end_ti = $("input[name='end_time']").val();
        $(".effect_date span").html(st_ti+" - "+end_ti);
        $("input[name='start_time']").blur(function () {
            if(st_ti != "" && end_ti !=""){
                var st_ti = $("input[name='start_time']").val();
                var end_ti = $("input[name='end_time']").val();
                $(".effect_date span").html(st_ti+" - "+end_ti);
            }
        });
        $("input[name='end_time']").blur(function () {
            if(st_ti != "" && end_ti !=""){
                var st_ti = $("input[name='start_time']").val();
                var end_ti = $("input[name='end_time']").val();
                $(".effect_date span").html(st_ti+" - "+end_ti);
            }
        });
    }
    $("input[name='expire_type']").change(function () {
        if($("#fixed_date").is(":checked")){
            var st_ti = $("input[name='start_time']").val();
            var end_ti = $("input[name='end_time']").val();
            $(".effect_date span").html(st_ti+" - "+end_ti);
            $("input[name='start_time']").blur(function () {
                if(st_ti != "" && end_ti !=""){
                    var st_ti = $("input[name='start_time']").val();
                    var end_ti = $("input[name='end_time']").val();
                    $(".effect_date span").html(st_ti+" - "+end_ti);
                }
            });
            $("input[name='end_time']").blur(function () {
                if(st_ti != "" && end_ti !=""){
                    var st_ti = $("input[name='start_time']").val();
                    var end_ti = $("input[name='end_time']").val();
                    $(".effect_date span").html(st_ti+" - "+end_ti);
                }
            })
        }
    });

    /*有效期-即日起*/
    if($("#choose_date").is(":checked")){
        var st_ti = $("input[name='receive_day']").val();
        var end_ti = $("select[name='date_type'] option:selected").text();
        if(st_ti != "" && end_ti !=""){
            $(".effect_date span").html("自领取之日起"+st_ti+end_ti+"内有效");
        }
        $("input[name='receive_day']").blur(function () {
            var st_ti = $("input[name='receive_day']").val();
            var end_ti = $("select[name='date_type'] option:selected").text();
            if(st_ti != "" && end_ti !=""){
                var st_ti = $("input[name='receive_day']").val();
                var end_ti = $("select[name='date_type'] option:selected").text();
                $(".effect_date span").html("自领取之日起"+st_ti+end_ti+"内有效");
                $("select[name='date_type']").change(function () {
                    var end_ti = $("select[name='date_type'] option:selected").text();
                    $(".effect_date span").html("自领取之日起"+st_ti+end_ti+"内有效");
                })

            }
        });
    }
    $("input[name='expire_type']").change(function () {
        if($("#choose_date").is(":checked")){
            var st_ti = $("input[name='receive_day']").val();
            var end_ti = $("select[name='date_type'] option:selected").text();
            if(st_ti != "" && end_ti !=""){
                var st_ti = $("input[name='receive_day']").val();
                var end_ti = $("select[name='date_type'] option:selected").text();
                $(".effect_date span").html("自领取之日起"+st_ti+end_ti+"内有效");
                $("select[name='date_type']").change(function () {
                    var end_ti = $("select[name='date_type'] option:selected").text();
                    $(".effect_date span").html("自领取之日起"+st_ti+end_ti+"内有效");
                });

            }
            $("input[name='receive_day']").blur(function () {
                var st_ti = $("input[name='receive_day']").val();
                var end_ti = $("select[name='date_type'] option:selected").text();
                if(st_ti != "" && end_ti !=""){
                    var st_ti = $("input[name='receive_day']").val();
                    var end_ti = $("select[name='date_type'] option:selected").text();
                    $(".effect_date span").html("自领取之日起"+st_ti+end_ti+"内有效");
                    $("select[name='date_type']").change(function () {
                        var end_ti = $("select[name='date_type'] option:selected").text();
                        $(".effect_date span").html("自领取之日起"+st_ti+end_ti+"内有效");
                    });
                }
            })

        }
    });
    $("input[name='expire_type']").change(function () {
        if($(this).val() == 2){
            $(".effect_date span").html("永久有效");
        }
    })
    if($("input[name='expire_type']:checked").val() == 2){
        $(".effect_date span").html("永久有效");
    }
    /*次数显示*/
    if($("input[name='count']").val() != ''){
        $(".power_content").css("display","block");
        $(".power_content").find('p:eq(1)').html("可使用服务数量："+$("input[name='count']").val());
    }
    $("input[name='count']").blur(function () {
        if($("input[name='count']").val() != ''){
            $(".power_content").css("display","block");
            $(".power_content").find('p:eq(1)').html("可使用服务数量："+$("input[name='count']").val());
        }
    });

    /*联系电话*/
    if($("input[name='mobile']").val() != ""){
        $(".contact_mobile").css("display","block");
        $(".contact_mobile p:last-of-type").html($("input[name='mobile']").val());
    }
    $("input[name='mobile']").change(function () {
        if($("input[name='mobile']").val() != ""){
            $(".contact_mobile").css("display","block");
            $(".contact_mobile p:last-of-type").html($("input[name='mobile']").val());
        }
    });
    /*使用说明*/
    if($("textarea[name='desc']").val() != ""){
        $(".notice-content").show();
        $(".notice-content").html($("textarea[name='desc']").val());
    }
    $("textarea[name='desc']").blur(function () {
        $(".notice-content").show();
        if($("textarea[name='desc']").val() != ""){
            $(".notice-content").html($("textarea[name='desc']").val());
        }
    });

    /*添加门店*/
    $('.add_store').click(function () {
        layer.open({
            type: 2
            ,title: ['选择门店','text-align:center;padding: 0']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: ['750px','430px']
            ,id: 'layerDemo' //防止重复弹出
            ,content: '/admin/frame/store/select?store_id=' + $("input[name='store_list']").val() //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['保存','取消']
            ,btnAlign: 'r' //按钮居右
            ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {
                var goods = $('input[name="store_list"]').val();
                var body = layer.getChildFrame('body', index);
                if(goods !='') {
                    if(isNaN(goods)) {
                        var goods_array = goods.split(',');
                        body.contents().find(".choose_store").each(function(){
                            if($.inArray($(this).attr("store_id"),goods_array)>-1){
                                $(this).attr('auto','true').addClass('goods_tr_choose');
                            }
                        });
                    }
                    else{
                        body.contents().find(".choose_store").each(function(){
                            if($(this).attr("store_id")==goods){
                                $(this).attr('auto','true').addClass('goods_tr_choose');
                            }
                        });
                    }
                }
            }
            ,yes: function(index, layero){ //保存按钮的回调
                var store = [];
                var iframe = layer.getChildFrame('body', index);
                if(store_list){
                    $(".store_table tr:gt(0)").remove();
                    $('.store_table tr:first-child,.store_modal tr:first-child').siblings().remove();
                    iframe.contents().find('tr[auto="true"]').each(function () {
                        store.push($(this).attr('store_id'));
                        var el = $('.store_modal_clone').find('tr').clone();
                        el.find('td').eq(0).text($(this).find('td').eq(1).text());
                        el.find('.del').attr('store_id', $(this).attr('store_id'));
                        $('.store_table tr:first-child,.store_modal tr:first-child').after(el);
                        if($('input[name="store_list_type"]:checked').val() == 1){
                            $('.store-content').show();
                            var sc_arr = [];
                            $('.store_table_tr,.store_modal_tr').each(function () {
                                sc_arr.push($(this).find('td').eq(0).text());
                            });
                            var sc_string = sc_arr.join('，');
                            $('.store-content').html(sc_string);
                        }
                    });
                    $('.store_table,.store_modal').show();
                }else{
                    $(".store_modal tr:gt(0)").remove();
                    iframe.contents().find('tr[auto="true"]').each(function () {
                        store.push($(this).attr('store_id'));
                        var el = $('.store_modal_clone').find('tr').clone();
                        el.find('td').eq(0).text($(this).find('td').eq(1).text());
                        el.find('.del').attr('store_id', $(this).attr('store_id'));
                        $('.store_modal tr:first-child').after(el);
                        if($('.store_modal tr').length == 1){
                            $('.store_modal').hide();
                        }
                        if($('input[name="store_list_type"]:checked').val() == 1){
                            $('.store-content').show();
                            var sc_arr = [];
                            $('.store_modal .store_modal_tr').each(function () {
                                sc_arr.push($(this).find('td').eq(0).text());
                            });
                            var sc_string = sc_arr.join('，');
                            $('.store-content').html(sc_string);
                        }
                    });
                    $('.store_modal').show();
                }
                $("input[name='store_list']").val(store);
                layer.close(index);
            },btn2: function(index, layero){
                //按钮取消的回调
            }
        });
    });
    //门店显示
    if($('input[name="store_list_type"]:checked').val() == 1){
        if($('.store_table tr').length >=2){
            $('.store-content').show();
            var sc_arr = [];
            $('.store_table_tr').each(function () {
                sc_arr.push($(this).find('td').eq(0).text());
            });
            var sc_string = sc_arr.join('，');
            $('.store-content').html(sc_string);
        }
        $('.add_store').removeAttr("hidden");
        $('.store_use_count').show()
        $('.store_use_time').show()
        $('.store_modal').css('display','table')
    }else{
        if($('input[name="store_list_type"]:checked').val() == -1){
            $('.store-content').show();
            $('.store-content').html('不可在门店使用');
            $('.store_use_time').hide()
            $('.store_modal').css('display','none')
        }else{
            $('.store-content').show();
            $('.store-content').html('全部门店');
            $('.store_use_count').show()
            $('.store_use_time').show()
            $('.store_modal').css('display','none')
        }
    }
    //会员卡激活
    $('input[name="activation"]').change(function () {
        if($('#act_yes').is(':checked')){
            $('.act_receive').show();
        }
        if($('#act_no').is(':checked')){
            $('.act_receive').hide();
        }
    });

    if($('input[name="activation"]:checked').val() == 1){
        $('.act_receive').show();
    }
    if($("input[name='is_pay']:checked").val() != 1){
        $('.isbuy_set').css("display",'none');
    }else{
        $('.isbuy_set').css("display",'block')       
    }
    if($("input[name='is_pay']:checked").val() != 2){
        $('.access_code').hide()
    } else {
        $('.access_code').show()
    }
    $("input[name='is_pay']").change(function () {
        if($("input[name='is_pay']:checked").val() != 1){
            $('.isbuy_set').css("display",'none');
        }else{
            $('.isbuy_set').css("display",'block')       
        }
        if($("input[name='is_pay']:checked").val() != 2){
            $('.access_code').hide()
        } else {
            $('.access_code').show()            
        }
    })
    $('input[name="pay_score"]').val($('input[name="pay_score"]').val().substring(0,$('input[name="pay_score"]').val().length - 3))


    $('.add_goods').click(function(){
        var record_select_value = $('input[name="exchang_goods"]').val();
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px','450px']
                , content: '/admin/public/purchase/goods/list?is_spec_goods=0&record_select_value='+record_select_value+'&iframe_only_select=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
    
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var body = layer.getChildFrame('body', index);
                    var checkedId = body.find('#record_select_value').val();
                    if (body.find('#record_select_value').val() == '') {
                        util.mobile_alert('请选择商品');
                        return false;
                    }
                    if (body.find('#record_select_value').val().split(',').length > 20) {
                        util.mobile_alert('最多选择20个商品');
                        return false;
                    }
                    $('input[name="exchang_goods"]').val(body.find('#record_select_value').val());
                    $(".choose_num").show();
                    util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                        console.log(response.content);
                        var list = response.content;
                        var html = '';
                        for (var i in list) {
                            html+=`<li class="clearfix">
                                        <img src="${list[i].goods_img}">
                                        <div>
                                            <p title="${list[i].goods_name}">${list[i].goods_name}</p>
                                            <p>￥${list[i].shop_price}</p>
                                        </div>
                                    </li>`;
                        }
                        if(list.length > 0){
                            $('.count_card_goods').show();
                        } else {
                            $('.count_card_goods').hide();
                        }
                        if(list.length > 2){
                            $('.count_card_goods').find('.more').show();
                        } else {
                            $('.count_card_goods').find('.more').hide();
                        }
                        $('.goods_box').html(html);
                        $('.add_goods').next('span').find('span').html(list.length);
                        layer.close(index);
                    }, {is_spec_goods: 0,  select_id: body.find('#record_select_value').val()})
    
                }, btn2: function (index, layero) {
                    //按钮取消的回调
    
                }
            });
        });
    });
    $('.choose_num').click(function(){
        var record_select_value = $('input[name="exchang_goods"]').val();
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px','450px']
                , content: '/admin/public/purchase/goods/list?is_spec_goods=0&record_select_value='+record_select_value+'&iframe_only_show=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {

                }
                , yes: function (index, layero) { //保存按钮的回调
                    var body = layer.getChildFrame('body', index);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调

                }
            });
        });
    });
    let more_flag = 0
    $('.more').click(function(){
        if(more_flag == 0){
            $('.goods_box').css('max-height','none');
            $('.more').find('span').attr('class','top')
            more_flag = 1
        } else {
            $('.goods_box').css('max-height','83px');
            $('.more').find('span').attr('class','down')
            more_flag = 0
        }

    })
    if($('input[name="is_exchang"]:checked').val() != '0' && $("input[name='exchang_count']").val() != ''){
        $(".power_content").css("display","block");
        $(".power_content").find('p:eq(0)').html("可兑换商品数量："+$("input[name='exchang_count']").val());
    }
    $('input[name="exchang_count"]').blur(function(){
        if($('input[name="is_exchang"]:checked').val() != '0' && $(this).val() != ''){
            $(".power_content").css("display","block");
            $(".power_content").find('p:eq(0)').html("可兑换商品数量："+$(this).val());
        }
    })
    $('input[name="is_exchang"]').click(function(){
        let status = $('input[name="is_exchang"]:checked').val();
        switch (status) {
            case '0':
                $(this).parent().siblings().hide();
                $('.count_card_goods').hide()
                $('.more').hide();
                $('.power_content p:eq(0)').hide()
                break;
            case '1':
                $(this).parent().siblings().show();
                $('.count_card_goods').show()
                break;
            case '2':
                $(this).parent().siblings().show();
                $(this).parent().siblings().last().hide();
                $('.count_card_goods').show()
                $('.goods_box').html('');
                $('.more').hide();
                break;
            default:
                break;
        }
    })
    $('input[name="store_list_type"]').click(function(){
        let status = $('input[name="store_list_type"]:checked').val();
        switch (status) {
            case '1':
                $(this).parent().siblings().hide();
                $('.using_store').show();
                $('.useing_time').show();
                $('.store_use_time').show()
                $('.store_use_count').show()            
                $('.power_content p:eq(1)').show()
                break;
            case '0':
                $(this).parent().siblings().show();
                $('.using_store').show();
                $('.useing_time').show();
                break;
            case '-1':
                $(this).parent().siblings().hide();
                $('.useing_time').hide();
            default:
                break;
        }
    })
    if($('input[name="store_list_type"]:checked').val() == 0){
        $('input[name="store_list_type"]').parent().siblings().show();
        $('.using_store').show();
        $('.useing_time').show();
    } else if($('input[name="store_list_type"]:checked').val() == -1) {
        $('input[name="store_list_type"]').parent().siblings().hide();
        $('.useing_time').hide();
    } else {
        $('.using_store').show();
        $('.useing_time').show();
    }
    
    if($('.goods_box li').length>2){
        $('.more').show();
    } else {
        $('.more').hide();
    }
    let store_use_status = $('input[name="is_exchang"]:checked').val();
    switch (store_use_status) {
        case '0':
            $('input[name="is_exchang"]').parent().siblings().hide();
            $('.count_card_goods').hide()
            break;
        case '1':
            $('input[name="is_exchang"]').parent().siblings().show();
            $('.count_card_goods').show()
            break;
        case '2':
            $('input[name="is_exchang"]').parent().siblings().show();
            $('input[name="is_exchang"]').parent().siblings().last().hide();
            $('.count_card_goods').show()
            $('.goods_box').html('');
            break;
        default:
            break;
    }
});
