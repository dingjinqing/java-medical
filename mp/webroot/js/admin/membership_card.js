$(function () {
    $("input[name='bg_type']").click(function () {
        $("input[name='bg_type']").removeClass('checkbox_actives');
        $(this).addClass('checkbox_actives');
    });

    $("input[name='offset']").click(function () {
        $("input[name='offset']").removeClass('checkbox_actives');
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
    $("input[name='offset_money']").click(function () {
        $("input[name='offset_money']").removeClass('checkbox_actives');
        $(this).addClass('checkbox_actives');
    });


    /*添加规则*/
    $('.btn_add_lines').click(function(){
        var obj = $(".same_modal").children().clone();
        $(".first_manzeng").append(obj);
        hasSaved = false;
    });
    $('.btn_add_money').click(function(){
        var obj = $(".same_money_modal").children().clone();
        $(".first_manzeng_money").append(obj);
        hasSaved = false;
    });
    /*移除满赠规则*/
    $("body").on('click','.del_lines',function () {
        $(this).parent().remove();
    });
    $('body').on('click','.del_score_lines',function () {
        score_rule($('input[name="offset"]:checked'));
    });
    $('body').on('click','.del_money_lines',function () {
        charge_rule($('input[name="offset_money"]:checked'));
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
        /*验证部分输入框只能输入数字*/
        var reg = /^\d+(?=\.{0,1}\d+$|$)/;

        var cards_name = $(":text[name='card_name']").val();
        var member_discount = $(":text[name='member_disconut']").val();
        var user_notice = $("textarea").val();

        if(cards_name == ""){
            util.mobile_alert('会员卡名称不能为空');
            return false;
        }

        /*至少选择一项会员权益*/
        if($("input[name='power']:checked").length<1 && $("input[name='pay_own_good']:checked").length<1 ){
            util.mobile_alert('至少选择一项会员权益');
            return false;
        }


        /*判断选中的权益填写规则*/
        for(var i=0;i<$("input[name='power']").length;i++){
            if(i==0 && $("input[name='power']")[i].checked==true){
                if(!reg.test($(".mem_advan input[name='discount']").val())){
                    util.mobile_alert("会员折扣请输入0-10的数字");
                    return false;
                }
            }
            if(i==1 && $("input[name='power']")[i].checked==true){
                if(!reg.test($(".mem_advan input[name='sorce']").val()) &&
                    !reg.test($("input[name='per_get_scores']").val())){
                    util.mobile_alert("赠送积分请输入大于0的数字");
                    return false;
                // if($(".mem_advan input[name='sorce']").val() != ''){
                //     if(!reg.test($(".mem_advan input[name='sorce']").val())){
                //         layer.msg("赠送积分请输入大于0的数字");
                //         return false;
                //     }
                // }
                // if($("input[name='per_get_scores']").val() != ""){
                //     if(!reg.test($("input[name='per_get_scores']").val())){
                //         layer.msg("每满减赠送积分请输入大于0的数字");
                //         return false;
                //     }
                }
            }
            if(i==2 && $("input[name='power']")[i].checked==true){
                if($("input[name='offset_money']").val() != 2) {
                    if(!reg.test($(".mem_advan input[name='send_money']").val()) &&
                        !reg.test($("input[name='per_get_moneys']").val())){
                        util.mobile_alert("赠送金额请输入大于0的数字");
                        return false;
                    }
                // if($("input[name='per_goods_moneys']").val() !=""){
                //     if(!reg.test($("input[name='per_goods_moneys']").val())){
                //         layer.msg("每满减金额请输入大于0的数字");
                //         return false;
                //     }
                // }
                // if($("input[name='per_get_moneys']").val() != ""){
                //     if(!reg.test($("input[name='per_get_moneys']").val())){
                //         layer.msg("每满减赠送积分请输入大于0的数字");
                //         return false;
                //     }
                }
            }
        }

        if($('input[name="expire_type"]:checked').val() == '0'){
            if($('input[name="start_time"]:eq(0)').val() == '' && $('input[name="end_time"]:eq(0)').val() == '') {
                util.mobile_alert("请输入有效期");
                return false;
            }
        }else if (parseInt($('input[name="expire_type"]:checked').val()) == 1){
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
        /*保存满赠策略*/
        var man_zeng = [];
        var i = 0;
        $("input[name='goods_moneys[]'").each(function () {
            var zuhe = {};
            score = $("input[name='get_scores[]'").eq(i).val();
            i++;
            zuhe.money = $(this).val();
            zuhe.score = score;
            if(zuhe.money!='') {
                man_zeng.push(zuhe);
            }
        });
        /*每满减的数据*/
        var each_man = {};
        each_man.each_money = $("input[name='per_goods_moneys']").val();
        each_man.each_score = $("input[name='per_get_scores']").val();
        if(each_man.each_money!=''){
            man_zeng.push(each_man);
        }

        /*选取的类型*/
        var offset_type = {};
        offset_type.offset_type = "offset";
        if($("input[name='power']")[1].checked != true){
            offset_type.value =  -1;
        }else{
            offset_type.value =  $('input[name="offset"]:checked').val();
        }
        man_zeng.unshift(offset_type);

        var buy_score = JSON.stringify(man_zeng);
        $("input[name='buy_score']").val(buy_score);

        /*充值*/
        var chongzhi=[];
        var j = 0;
        /*充送*/
        $("input[name='moneys[]'").each(function () {
            var chong = {};
            score = $("input[name='get_moneys[]'").eq(j).val();
            j++;
            chong.money = $(this).val();
            chong.score = score;
            if(chong.money!='') {
                chongzhi.push(chong);
            }
        });
        if(chongzhi.length == 0){
            var chong = {};
            chong.money = 0;
            chong.score = 0;
            chongzhi.push(chong);
        }
        /*每满减的数据*/
        var each_chong = {};
        each_chong.each_money = $("input[name='per_moneys']").val();
        each_chong.each_score = $("input[name='per_get_moneys']").val();
        if(each_chong.each_money!=''){
            chongzhi.push(each_chong);
        }

        /*选取的类型*/
        var offset_money_type = {};
        offset_money_type.offset_type = "offset";
        if($("input[name='power']")[2].checked != true){
            offset_money_type.value =  -1;
        }else{
            offset_money_type.value =  $('input[name="offset_money"]:checked').val();
        }
        chongzhi.unshift(offset_money_type);
        var buy_momey = JSON.stringify(chongzhi);
        $("input[name='charge_money']").val(buy_momey);
        for(var j=0;j<$("input[name='power']").length;j++){
            if(j==0 && $("input[name='power']")[j].checked == false){
                $("input[name='discount']").val('');
            }else if(j==1 && $("input[name='power']")[j].checked == false){
                $("input[name='sorce']").val('');
                $("input[name='buy_score']").val('');
            }else if(j==2 && $("input[name='power']")[j].checked == false){
                $("input[name='send_money']").val('');
                $("input[name='charge_money']").val('');
            }
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
    util.radioChange('offset');
    util.radioChange('offset_money');
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
    };
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
    $("input[name='expire_type']").change(function () {
        if($(this).val() == 2){
            $(".effect_date span").html("永久有效");
        }
    })
    if($("input[name='expire_type']:checked").val() == 2){
        $(".effect_date span").html("永久有效");
    }
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
                })

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
            })

        }
    });
    //会员折扣
    $('input[name="discount"]').blur(function () {
        if($('.power_discount').is(':checked')){
            if($('input[name="discount"]').val() != '' && $('input[name="discount_is_all"]:checked').val() == '1'){
                $('.power_content').show();
                $('.power_content').html('全部商品折扣' + $('input[name="discount"]').val() + '折');
            } else if ($('input[name="discount"]').val() != '' && $('input[name="discount_is_all"]:checked').val() == '0'){
                $('.power_content').show();
                $('.power_content').html('指定商品折扣' + $('input[name="discount"]').val() + '折');
            } else{
                $('.power_content').hide();
            }
        }
    });
    /*卡积分显示*/
    $('input[name="sorce"]').blur(function () {
        if($('.power_score').is(':checked')){
            if($('input[name="sorce"]').val() != ''){
                $('.make_give').show();
                $('.make_give').html('开卡赠送' + $('input[name="sorce"]').val() + '积分');
            }else{
                $('.make_give').hide();
            }
        }
    });
    //卡充值规则
    $('input[name="send_money"]').blur(function () {
        if($('.power_charge').is(':checked')){
            if($('input[name="send_money"]').val() != ''){
                $('.c_make_give').show();
                $('.c_make_give').html('开卡赠送' + $('input[name="send_money"]').val() + '元');
            }else{
                $('.c_make_give').hide();
            }
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
        if($("textarea[name='desc']").val() != ""){
            $(".notice-content").show();
            $(".notice-content").html($("textarea[name='desc']").val());
        }else{
            $(".notice-content").hide();
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
                        console.log($(this).find('td'))
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
    }else{
        if($('input[name="store_list_type"]:checked').val() == -1){
            $('.store-content').show();
            $('.store-content').html('不可在门店使用');
        }else{
            $('.store-content').show();
            $('.store-content').html('全部门店');
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
    if($("input[name='pay_own_good']").is(":checked")){
        $('.choose_box_2').css("display",'block');
        $('.e_power_title').show()
        onCatload($('input[name="cat_ids_1"]'))
        onSortload($('input[name="sort_ids_1"]'))  
    }else{
        $('.choose_box_2').css("display",'none')       
    }
    if($("input[name='discount_is_all']:nth-of-type(1)").is(":checked")){
        $('.choose_box_1').css("display",'none');
    }else{
        $('.choose_box_1').css("display",'block');
        onSortload($('input[name="sort_ids"]'))
        onCatload($('input[name="cat_ids"]'))
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
    $("input[name='pay_own_good']").change(function () {
        if($("input[name='pay_own_good']").is(":checked")){
            $('.choose_box_2').css("display",'block');
            $('.e_power_title').show()
        }else{
            $('.choose_box_2').css("display",'none')
            $('.e_power_title').hide()
        }
    })
    $("input[name='discount_is_all']").change(function () {
        if($("input[name='discount_is_all']:nth-of-type(1)").is(":checked")){
            $('.choose_box_1').css("display",'none');
            $('.power_content').html('全部商品折扣' + $('input[name="discount"]').val() + '折');
        }else{
            $('.choose_box_1').css("display",'block')       
            $('.power_content').html('指定商品折扣' + $('input[name="discount"]').val() + '折');
        }
    })
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
    //选择商品
    $('.choose_goods').on('click',function(){
        chooseGoods(this,0)
    });
    $('.add_cate').on('click',function(){
        var count = $(this).attr("cat_count");
        if(count==0){
            util.mobile_alert('无可选分类');
        }
        else {
            selectCat(this)
        }
    });
    $('.add_sort').on('click',function(){
        var count = $(this).attr("cat_count");
        if(count==0){
            util.mobile_alert('无可选分类');
        }
        else {
            selectSort(this)
        }
    });
    
    function chooseGoods(that,type){
        // 0 未选  1已选
        var is_exc = 0;
        var goods_ids = $(that).parents('[class^="choose_box"]').find('[name^="goods_ids"]');
        var checkedId1 = goods_ids.val();
        if($(that).parents('[class^="choose_box"]').attr('class') === 'choose_box_2'){
            is_exc = 1
        }
        var  content = '/admin/public/purchase/goods/list?record_select_value='+checkedId1 + '&is_exc=' + is_exc;
        if(type == 1){
            content = '/admin/public/purchase/goods/list?record_select_value='+checkedId1 + '&is_exc=' + is_exc + '&iframe_only_show=1';
        }else{
            content = '/admin/public/purchase/goods/list?record_select_value='+checkedId1 + '&is_exc=' + is_exc + '&iframe_only_select=1';
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;


            console.log(checkedId1);
            var goods = [];
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px','430px']
                , content: content //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var body = layer.getChildFrame('body', index);
                    var checkedId = iframe.find('#record_select_value').val();
                    goods_ids.val(checkedId);
                    console.log(checkedId)
                    console.log(checkedId.split(',').length);
                    $(that).next().children('strong').text(checkedId.split(',').length);
                    if (body.find('#record_select_value').val() == '') {
                        util.mobile_alert('请选择商品');
                        return false;
                    }else{
                        if(is_exc == 1){
                            $(".choose_box_2").find(".choose_num").eq(0).css("display","inline");
                        }else{
                            $(".choose_num").eq(0).css("display","inline");
                        }
                    }
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    }
    function selectCat(that){
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var cat_ids = $(that).parents('[class^="choose_box"]').find('[name^="cat_ids"]');
            var is_exc = 0;
            if($(that).parents('[class^="choose_box"]').attr('class') === 'choose_box_2'){
                is_exc = 1
            }
            var checkedId1 = cat_ids.val()
            console.log(checkedId1)
            layer.open({
                type: 2
                , title: ['添加平台分类', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['580px','540px']
                , content: '/admin/frame/goods/cat/select?cat_ids=' + checkedId1 + '&is_exc=' +  is_exc//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //保存按钮的回调
                    var cat_array = [];
                    // var cat_goods_num = [];
                    var iframe = layer.getChildFrame('body', index);
                    // var body = layer.getChildFrame('body', index);
                    // var checkedId = iframe.find('#record_select_value').val();
                    iframe.find('input[name="cat_id[]"]:checked').each(function () {
                        cat_array.push($(this).val());
                    });
                    // iframe.find('.cate_li').each(function(){
                    //     var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                    //     var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                    //     var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                    //     thirdCheck.each(function(){
                    //         if($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')){
                    //             return;
                    //         } else if($(this).is(':checked')) {
                    //             cat_goods_num.push($(this).next().attr('goods_num'));
                    //                 return;
                    //         }
                    //     })
                    //     if(firstCheck.is(':checked') === true){
                    //         cat_goods_num.push(firstCheck.next().attr('goods_num'));
                    //         return;
                    //     } else if (firstCheck.is(':checked') === false) {
                    //         secondCheck.each(function(){
                    //             if($(this).is(':checked')){
                    //                 cat_goods_num.push($(this).next().attr('goods_num'));
                    //                 return;
                    //             } 
                    //         })
                    //     }
                    // });
                    $(that).next().children('strong').text(cat_array.length);
                    cat_ids.val(cat_array)
                    if(cat_ids.val()){
                        if(is_exc == 1){
                            $(".choose_box_2").find(".choose_num").eq(1).css("display","inline");
                        }else{
                            $(".choose_num").eq(1).css("display","inline");
                        }
                    }
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                    

                }
            });
        });
    }
    $('[class^="choose_box"]').each(function(){
        if($(this).find('[name^="goods_ids"]').val() != ''){
            let num = $(this).find('[name^="goods_ids"]').val().split(',').length;
            $(this).find('.choose_goods').next().children('strong').text(num);
        }
    })
    function selectSort(that){
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var is_exc = 0;
            var sort_ids = $(that).parents('[class^="choose_box"]').find('[name^="sort_ids"]')
            if($(that).parents('[class^="choose_box"]').attr('class') === 'choose_box_2'){
                is_exc = 1;
            }
            var checkedId1 = sort_ids.val();
            console.log(is_exc);
            layer.open({
                type: 2
                , title: ['添加商家分类', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['580px','540px']
                , content: '/admin/frame/goods/sort/select?type=2&sort_ids='+checkedId1 + '&is_exc=' + is_exc//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success:function(layero,index){

                }
                , yes: function (index, layero) { //保存按钮的回调
                    var sort_array = [];
                    // var sort_goods_num = [];
                    var iframe = layer.getChildFrame('body', index);
                    // var body = layer.getChildFrame('body', index);
                    // var checkedId = iframe.find('#record_select_value').val();
                    iframe.find('input[name="sort_id[]"]:checked').each(function () {
                        sort_array.push($(this).val());
                    });
                    // iframe.find('.sort_li').each(function(){
                    //     var firstCheck = $(this).find('.first_sort_cate').find('input[type="checkbox"]');
                    //     var secondCheck = $(this).find('.second_sort_cate').children().children('span').find('input[type="checkbox"]');
                    //     if(firstCheck.is(':checked') === true){
                    //         sort_goods_num.push(firstCheck.next().attr('sort_num'));
                    //         return;
                    //     } else if (firstCheck.is(':checked') === false) {
                    //         secondCheck.each(function(){
                    //             if($(this).is(':checked')){
                    //                 sort_goods_num.push($(this).next().attr('sort_num'));
                    //                 return;
                    //             } 
                    //         })
                    //     }
                    // });
                    $(that).next().children('strong').text(sort_array.length);
                    sort_ids.val(sort_array)
                    if(sort_ids.val()){
                        if(is_exc == 1){
                            $(".choose_box_2").find(".choose_num").eq(2).css("display","inline");
                        }else{
                            $(".choose_num").eq(2).css("display","inline");
                        }
                    }
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                    layer.close(index);

                }
            });
        });
    }
    // function sum(arr){
    //     if (arr.length == 0) {
    //         return 0;
    //     }
    //     else if (arr.length == 1) {
    //         return parseInt(arr[0]);
    //     } else {
    //         return parseInt(arr[0]) + sum(arr.slice(1));
    //     }
    // }
    function onSortload(el){
        var sort_id = el.val();
        // var is_exc = 0;
        // if($(el).parents('[class^="choose_box"]').attr('class') === 'choose_box_2'){
        //     is_exc = 1
        // }
        // util.ajax_json('/admin/frame/goods/sort/select', function(response){
        //     let sort_list = response.content.sort_list;
        //     var sort_num = 0;
        //     getArray(sort_list,'child',sort_num);
        //     console.log(sort_num);
        //     el.parents('[class^="choose_box"]').find('.add_sort').next().children('strong').text(sort_num)
        // },{type:1,sort_ids:sort_id,is_exc:is_exc});
        var sort_num = 0
        if(sort_id !== ''){
            sort_num = sort_id.split(',').length;
        }
        el.parents('[class^="choose_box"]').find('.add_sort').next().children('strong').text(sort_num);
    }

    function onCatload(el){
        var cat_id = el.val();
        // console.log(cat_id)
        // var is_exc = 0;
        // if($(el).parents('[class^="choose_box"]').attr('class') === 'choose_box_2'){
        //     is_exc = 1
        // }
        // util.ajax_json('/admin/frame/goods/cat/select', function(response){
        //     let cat_list = response.content.cat_list;
        //     console.log(cat_list)
        //     var cat_num = 0;
        //     getArray(cat_list,'child',cat_num)
        //     console.log(cat_num)
        //     el.parents('[class^="choose_box"]').find('.add_cate').next().children('strong').text(cat_num)
        // },{type:1,cat_ids:cat_id,is_exc:is_exc});
        var cat_num = 0
        if(cat_id !== ''){
            cat_num = cat_id.split(',').length;
        }
        el.parents('[class^="choose_box"]').find('.add_cate').next().children('strong').text(cat_num)

    }
    $('input[name="pay_score"]').val($('input[name="pay_score"]').val().substring(0,$('input[name="pay_score"]').val().length - 3))
    // function getArray(data,name,arr){
    //     for (var i in data) {
    //         if(!(typeof(data[i]['checked']) == 'undefined')){
    //             arr = arr+1;
    //         }
    //         if (!(typeof(data[i][name]) == 'undefined')) {
    //             // arr.push(data[i][value])
    //             break;
    //         } else {
    //             getArray(data[i].child, name,arr);
    //         }
    //     }
    // }

    $(".choose_num").click(function () {
        if($(this).prev().attr("class") == 'choose_goods'){
            chooseGoods($(this).prev(),1);
        }
        if($(this).prev().attr("class") == 'add_sort'){
            selectSort($(this).prev(),1);
        }
        if($(this).prev().attr("class") == 'add_cate'){
            selectCat($(this).prev(),1);
        }
    })
});