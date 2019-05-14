
    //奖品设置的切换
    $("input[name='present_type']").change(function () {
        if($("#scores").is(":checked")){
            $(".four_one").css("display","none");
            $(".score_set").css("display","block");
        }
        if($("#yues").is(":checked")){
            $(".four_one").css("display","none");
            $(".yue_set").css("display","block");
        }
        if($("#coupons").is(":checked")){
            $(".four_one").css("display","none");
            $(".coupon_set").css("display","block");
        }
        if($("#owns").is(":checked")){
            $(".four_one").css("display","none");
            $(".own_set").css("display","block");
        }
        if($("#gifts").is(":checked")){
            $(".four_one").css("display","none");
            $(".gift_set").css("display","block");
        }

    });

    $(".award_coupon").change(function () {
        var num = $(".award_coupon option:selected").attr('num');
        $(".coupon_num").val(num);
        $(".show_coupon_num").html(num);
    });

    //显示和隐藏
    if($("input[name='can_share']:nth-of-type(2)").is(":checked")){
        $(".share_lottery").find(".normal_tips").css("display","none");
        $(".share_lottery").find(".each_times").css("display","none");
    }else{
        $(".share_lottery").find(".normal_tips").css("display","block");
        $(".share_lottery").find(".each_times").css("display","block");
    }
    $("input[name='can_share']").change(function () {
        if($("input[name='can_share']:nth-of-type(2)").is(":checked")){
            $(".share_lottery").find(".normal_tips").css("display","none");
            $(".share_lottery").find(".each_times").css("display","none");
        }else{
            $(".share_lottery").find(".normal_tips").css("display","block");
            $(".share_lottery").find(".each_times").css("display","block");
        }
    })

    if($("input[name='can_use_score']:nth-of-type(2)").is(":checked")){
        $(".pay_lottery").find(".normal_tips").css("display","none");
        $(".pay_lottery").find(".each_times").css("display","none");
        $(".pay_lottery").find(".money_times").css("display","none");
    }else{
        $(".pay_lottery").find(".normal_tips").css("display","block");
        $(".pay_lottery").find(".each_times").css("display","block");
        $(".pay_lottery").find(".money_times").css("display","block");
    }
    $("input[name='can_use_score']").change(function () {
        if($("input[name='can_use_score']:nth-of-type(2)").is(":checked")){
            $(".pay_lottery").find(".normal_tips").css("display","none");
            $(".pay_lottery").find(".each_times").css("display","none");
            $(".pay_lottery").find(".money_times").css("display","none");
        }else{
            $(".pay_lottery").find(".normal_tips").css("display","block");
            $(".pay_lottery").find(".each_times").css("display","block");
            $(".pay_lottery").find(".money_times").css("display","block");
        }
    })

    //左侧内容显示
    $(".start_time").blur(function () {
        $(".start").html($(this).val());
    })
    $(".end_time").blur(function () {
        $(".end").html($(this).val());
    })
    $(".activity_tips").blur(function () {
        $(".huodong_tips").html($(this).val());
    })
    $("input[name='no_award_icon']").blur(function () {
        if($(this).val() != ""){
            $(".lo_item:nth-of-type(2)").find('div').html($(this).val());
        }else{
            $(".lo_item:nth-of-type(2)").find('div').html("谢谢参与");
        }
    });
    $(".icon_text").blur(function () {
        if($(".first_award").hasClass('checked_tab')){
            $(".lo_item:nth-of-type(4)").find("div").html($(this).val());
        }else if($(".second_award").hasClass('checked_tab')){
            $(".lo_item:nth-of-type(5)").find("div").html($(this).val());
            $(".lo_item:nth-of-type(9)").find("div").html($(this).val());
        }else if($(".third_award").hasClass('checked_tab')){
            $(".lo_item:nth-of-type(3)").find("div").html($(this).val());
            $(".lo_item:nth-of-type(7)").find("div").html($(this).val());
        }else if($(".fourth_award").hasClass('checked_tab')){
            $(".lo_item:nth-of-type(1)").find("div").html($(this).val());
            $(".lo_item:nth-of-type(6)").find("div").html($(this).val());
            $(".lo_item:nth-of-type(8)").find("div").html($(this).val());
        }
    })


    //上传未中奖图标
    $('.add_no_award').click(function() {
        var el = $(this).parent();
        var w = 80;
        var h = 80;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.parent().parent().find(".icon_imgs").attr("src", path);
                $('.lo_item:nth-of-type(2)').find('img').attr("src",path);
                hasSaved = false;
            }
        });
    });
    //清空未中奖图标
    $(".clear_no_award").click(function () {
        $(this).parent().parent().parent().find(".icon_imgs").attr("src", '');
    })

    //上传中奖图标
    $('.add_award').click(function() {
        var el = $(this).parent();
        var w = 80;
        var h = 80;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.parent().parent().find(".icon_gr_imgs").attr("src", path);
                if($(".first_award").hasClass('checked_tab')){
                    $(".lo_item:nth-of-type(4)").find("img").attr("src", path);
                }else if($(".second_award").hasClass('checked_tab')){
                    $(".lo_item:nth-of-type(5)").find("img").attr("src", path);
                    $(".lo_item:nth-of-type(9)").find("img").attr("src", path);
                }else if($(".third_award").hasClass('checked_tab')){
                    $(".lo_item:nth-of-type(3)").find("img").attr("src", path);
                    $(".lo_item:nth-of-type(7)").find("img").attr("src", path);
                }else if($(".fourth_award").hasClass('checked_tab')){
                    $(".lo_item:nth-of-type(1)").find("img").attr("src", path);
                    $(".lo_item:nth-of-type(6)").find("img").attr("src", path);
                    $(".lo_item:nth-of-type(8)").find("img").attr("src", path);
                }
                hasSaved = false;
            }
        });
    });
    //清空中奖图标
    $(".clear_award").click(function () {
        $(this).parent().parent().parent().find(".icon_gr_imgs").attr("src", '');
    })

    //未中奖图标选择
    $('.choose_icon').on('click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            parent.layer.open({
                type: 2
                , title: ['图标选择', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['450px','350px']
                , content: '/admin/market/lottery/icon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) {
                    var iframe = layer.getChildFrame('body', index);
                    var imgf_path = iframe.contents().find('input[name="icon_lottery"]:checked').prev().attr('src');
                    $(".icon_imgs").attr("src",imgf_path);
                    $('.lo_item:nth-of-type(2)').find('img').attr("src",imgf_path);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    $(".chance").blur(function () {
        if($(this).val() >100 || $(this).val() < 0){
            util.mobile_alert("请输入0-100之间的数字");
            $(this).focus();
        }
        if($(this).val().indexOf(".") > -1){
            if($(this).val().split('.')[1].length > 2){
                util.mobile_alert("小数点后请输入两位有效数字");
                $(this).focus();
            }
        }
    })

    //中奖图标选择
    $('.choose_gr_icon').on('click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            parent.layer.open({
                type: 2
                , title: ['图标选择', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['450px','350px']
                , content: '/admin/market/lottery/icon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) {
                    var iframe = layer.getChildFrame('body', index);
                    var imgf_path = iframe.contents().find('input[name="icon_lottery"]:checked').prev().attr('src');
                    $(".icon_gr_imgs").attr("src",imgf_path);
                    if($(".first_award").hasClass('checked_tab')){
                        $(".lo_item:nth-of-type(4)").find("img").attr("src", imgf_path);
                    }else if($(".second_award").hasClass('checked_tab')){
                        $(".lo_item:nth-of-type(5)").find("img").attr("src", imgf_path);
                        $(".lo_item:nth-of-type(9)").find("img").attr("src", imgf_path);
                    }else if($(".third_award").hasClass('checked_tab')){
                        $(".lo_item:nth-of-type(3)").find("img").attr("src", imgf_path);
                        $(".lo_item:nth-of-type(7)").find("img").attr("src", imgf_path);
                    }else if($(".fourth_award").hasClass('checked_tab')){
                        $(".lo_item:nth-of-type(1)").find("img").attr("src", imgf_path);
                        $(".lo_item:nth-of-type(6)").find("img").attr("src", imgf_path);
                        $(".lo_item:nth-of-type(8)").find("img").attr("src", imgf_path);
                    }
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    //保存的验证
    $(".btn_save_cfg").click(function () {
        if($(".act_mame").val() == ""){
            util.mobile_alert("请填写活动名称！");
            return false;
        }
        if($(".end_time").val() == ""){
            util.mobile_alert("请填写活动过期时间！");
            return false;
        }
        if($(".start_time").val() == ""){
            util.mobile_alert("请填写活动生效时间！");
            return false;
        }
        if($(".end_time").val() < $(".start_time").val()){
            util.mobile_alert("结束时间必须大于开始时间！");
            return false;
        }
        if($(".activity_tips").val() == ""){
            util.mobile_alert("请填写活动说明！");
            return false;
        }
        if($(".act_mame").val() == ""){
            util.mobile_alert("请填写活动名称！");
            return false;
        }
        if($(".chance").val() == ""){
            util.mobile_alert('请填写中奖概率！');
            return false;
        }

        $("input[name='no_award_image']").val($(".icon_imgs").attr('src'));

        if(!lottery_manager.check_all()) return false;
        hasSaved = true;
        util.ajax_json("/admin/market/lottery/edit",function (response) {
            if (response.error == 0) {
                hasSaved = true;
                util.mobile_alert('保存成功');
                window.location = '/admin/market/lottery/list?top_index=4';
            } else {
                util.mobile_alert('保存失败');
            }
        },$("#form1").serialize())
        return false;
    });

    $(".btn_fresh").click(function () {
        util.ajax_json('/admin/coupon/list/going',function (response) {
            console.log(response.content)
            if (response.error == 0) {
                var option_list = '<option value="" num="0">未选择</option>';
                for (var i in response.content) {
                    option_list += '<option value="' + response.content[i].id + '" num="' +
                        response.content[i].surplus + '">' + response.content[i].act_name +  '</option>'
                }
                $(".award_coupon").html(option_list);
            }
        })
    });

    function select_award_level(_this) {
        var save_award = lottery_manager.save_cur_award();
        if (!save_award) return false;
        $(".award_level").removeClass('checked_tab');
        $(_this).addClass('checked_tab');
        var award_level = $(_this).attr("level");
        var cur_award = eval('lottery_manager.' + award_level);
        lottery_manager.show_cur_award(cur_award)
    }

    var lottery_manager = {
        first_award: {},
        second_award: {},
        third_award: {},
        fourth_award: {},
        save_cur_award: function () {
            var content = $("#lottery_module");
            var award_level = content.find(".checked_tab").attr('level');
            var cur_award = {};
            cur_award.chance = parseFloat(content.find(".chance").val());
            cur_award.present_type = parseInt(content.find("input[name='present_type']:checked").val());
            cur_award.present_number = parseInt(content.find(".present_number").val());
            cur_award.keep_days = parseInt(content.find(".keep_days").val())?parseInt(content.find(".keep_days").val()):7;
            if(cur_award.present_type == 1){
                cur_award.present_detail = content.find(".award_account").val();
            }else if(cur_award.present_type == 2){
                cur_award.present_detail = content.find(".award_coupon option:selected").val();
                var coupon_num = parseInt(content.find(".award_coupon option:selected").attr('num'));
                if (parseInt(cur_award.present_number) > coupon_num) {
                    util.mobile_alert('奖品数不能大于优惠券数量');
                    return false;
                }
                content.find(".award_coupon option[value='']").prop('selected', true);
            }else if(cur_award.present_type == 3){
                cur_award.present_detail = content.find(".award_other").val();
            }else if(cur_award.present_type == 4){
                cur_award.present_detail = content.find(".goods_info").attr('prd_id');
                var prd_num = parseInt(content.find(".goods_info").attr('num'));
                if (parseInt(cur_award.present_number) > prd_num) {
                    util.mobile_alert('奖品数不能大于商品数量');
                    return false;
                }
            }else{
                cur_award.present_detail = content.find(".award_score").val();
            }
            cur_award.icon_imgs = content.find(".icon_gr_imgs").attr('src');
            cur_award.icon_text = content.find(".icon_text").val();
            eval('this.' + award_level + '=' + JSON.stringify(cur_award));
            return true;
        },

        show_cur_award: function (cur_award) {
            if(!cur_award) cur_award = {};
            console.log(cur_award)
            var content = $("#lottery_module");
            content.find(".chance").val(cur_award.chance);
            var present_type = isNaN(parseInt(cur_award.present_type)) ? 0 : parseInt(cur_award.present_type);
            if(present_type==3){
                content.find("input[name='present_type']").eq(4).prop('checked','checked');
            }
            if(present_type==4){
                content.find("input[name='present_type']").eq(3).prop('checked','checked');
            }
            if(present_type<3){
                content.find("input[name='present_type']").eq(present_type).prop('checked','checked');
            }
            content.find(".four_one").css("display","none");
            content.find(".coupon_type input").val('');
            content.find(".show_coupon_num").html(0);
            content.find(".tbody").html('');
            content.find(".goods_area").css("display","none");
            if(present_type == 1){
                content.find(".yue_set").css("display","block");
                content.find(".award_account").val(cur_award.present_detail);
            }else if(cur_award.present_type == 2){
                content.find(".coupon_set").css("display","block");
                content.find(".award_coupon option[value='"+parseInt(cur_award.present_detail)+"']").prop('selected', true);
                content.find(".coupon_num").val($(".award_coupon option:selected").attr('num'));
                content.find(".show_coupon_num").html($(".award_coupon option:selected").attr('num'));
            }else if(cur_award.present_type == 3){
                content.find(".own_set").css("display","block");
                content.find(".award_other").val(cur_award.present_detail);
            }else if(cur_award.present_type == 4){
                content.find(".gift_set").css("display","block");
                content.find('input[name="recommend_goods_id"]').val(cur_award.present_detail);
                if(!cur_award.present_detail){
                    $('.choose_goods').css('background', '#ffffff');
                }else{
                    util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                        console.log(response);
                        var list = response.content;
                        var html = '';
                        for (var i in list) {
                            list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                            html += '<tr>' +
                                '        <td>' +
                                '            <div class="goods_img">' +
                                '                <img src="'+list[i].goods_img+'" />' +
                                '            </div>' +
                                '            <div class="goods_info clearfix" num="'+list[i].goods_number+'" prd_id="'+list[i].prd_id+'">' +
                                '                <div class="goods_name">'+list[i].goods_name+list[i].prd_desc+'</div>' +
                                '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                                '<td>'+list[i].goods_number+'</td>' +
                                '<td><a href="##" item="'+list[i].prd_id+'" class="change_goods_del">删除</a></td>'
                            '        </td>';
                            html += '</tr>';
                        }
                        $('.goods_area').css('display','block');
                        $('.goods_modal').css('display','block');
                        $('.goods_modal .tbody').html(html);
                        $('.goods_table').css('display','block');
                        $('.goods_table .tbody').html(html);
                        check_goods_area_height()
                        // layer.close(index);

                    },{select_id: cur_award.present_detail,is_spec_goods: 1});
                    content.find(".award_other").val(cur_award.present_detail);
                }

            }else{
                content.find(".score_set").css("display","block");
                content.find(".award_score").val(cur_award.present_detail);
            }
            content.find(".present_number").val(cur_award.present_number);
            if(cur_award.keep_days){
                content.find(".keep_days").val(cur_award.keep_days);
            }else{
                content.find(".keep_days").val(7);
            }

            content.find(".icon_gr_imgs").attr('src',cur_award.icon_imgs);
            content.find(".icon_text").val(cur_award.icon_text);
        },

        init_lottery: function () {
            this.first_award = $.parseJSON($("input[name='first_award']").val() ? $("input[name='first_award']").val() : '{}');
            this.second_award = $.parseJSON($("input[name='second_award']").val() ? $("input[name='second_award']").val() : '{}');
            this.third_award = $.parseJSON($("input[name='third_award']").val() ? $("input[name='third_award']").val() : '{}');
            this.fourth_award = $.parseJSON($("input[name='fourth_award']").val() ? $("input[name='fourth_award']").val() : '{}');
            if(this.first_award.icon_imgs){
                $(".lo_item:nth-of-type(4)").find("img").attr("src", this.first_award.icon_imgs);
            }
            if(this.second_award.icon_imgs){
                $(".lo_item:nth-of-type(5)").find("img").attr("src", this.second_award.icon_imgs);
                $(".lo_item:nth-of-type(9)").find("img").attr("src", this.second_award.icon_imgs);
            }
            if(this.third_award.icon_imgs){
                $(".lo_item:nth-of-type(3)").find("img").attr("src", this.third_award.icon_imgs);
                $(".lo_item:nth-of-type(7)").find("img").attr("src", this.third_award.icon_imgs);
            }
            if(this.fourth_award.icon_imgs){
                $(".lo_item:nth-of-type(1)").find("img").attr("src", this.fourth_award.icon_imgs);
                $(".lo_item:nth-of-type(6)").find("img").attr("src", this.fourth_award.icon_imgs);
                $(".lo_item:nth-of-type(8)").find("img").attr("src", this.fourth_award.icon_imgs);
            }
            if(this.first_award.icon_text){
                $(".lo_item:nth-of-type(4)").find("div").html(this.first_award.icon_text);
            }
            if(this.second_award.icon_text){
                $(".lo_item:nth-of-type(5)").find("div").html(this.second_award.icon_text);
                $(".lo_item:nth-of-type(9)").find("div").html(this.second_award.icon_text);
            }
            if(this.third_award.icon_text){
                $(".lo_item:nth-of-type(3)").find("div").html(this.third_award.icon_text);
                $(".lo_item:nth-of-type(7)").find("div").html(this.third_award.icon_text);
            }
            if(this.fourth_award.icon_text){
                $(".lo_item:nth-of-type(1)").find("div").html(this.fourth_award.icon_text);
                $(".lo_item:nth-of-type(6)").find("div").html(this.fourth_award.icon_text);
                $(".lo_item:nth-of-type(8)").find("div").html(this.fourth_award.icon_text);
            }
            this.show_cur_award(this.first_award)
        },

        check_all: function () {
            if (!this.save_cur_award()) return false;
            var total_chance = parseFloat(this.first_award.chance) + parseFloat(this.second_award.chance)
                + parseFloat(this.third_award.chance) + parseFloat(this.fourth_award.chance);
            if (total_chance > 100) {
                util.mobile_alert('中奖概率之和不能大于100%')
                return false;
            }
            if(this.first_award.chance > 0 && !this.first_award.present_detail){
                util.mobile_alert('请填写一等奖奖品内容')
                return false;
            }
            if(this.second_award.chance > 0 && !this.second_award.present_detail){
                util.mobile_alert('请填写二等奖奖品内容')
                return false;
            }
            if(this.third_award.chance > 0 && !this.third_award.present_detail){
                util.mobile_alert('请填写三等奖奖品内容')
                return false;
            }
            if(this.fourth_award.chance > 0 && !this.fourth_award.present_detail){
                util.mobile_alert('请填写四等奖奖品内容')
                return false;
            }
            $("input[name='first_award']").val($.toJSON(this.first_award));
            $("input[name='second_award']").val($.toJSON(this.second_award));
            $("input[name='third_award']").val($.toJSON(this.third_award));
            $("input[name='fourth_award']").val($.toJSON(this.fourth_award));
            return true;
        }
    };

    //选择商品
    $('.choose_goods').on('click',function(){
        var goods_spec_id =$('input[name="recommend_goods_id"]').val();
        if(goods_spec_id !=''){
            var currentTime = (new Date()).valueOf();
            var startTime = $('input[name="start_time"]').val();
            startTime = startTime.replace(/-/g, '/');
            var time = new Date(startTime);
            time = time.getTime();
            var endTime = $('input[name="end_time"]').val();
            endTime = endTime.replace(/-/g, '/');
            var time1 = new Date(endTime);
            time1 = time1.getTime();
            var id =$('input[name="id"]').val();
            if(time < currentTime && id>0) {
                $('.choose_goods').css('background', '#ebebe4');
                return false;
            }
        }else{
            $('.choose_goods').css('background', '#ffffff');
        }

        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var checkedId1 = $('input[name="recommend_goods_id"]').val();
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px','430px']
                , content: '/admin/public/purchase/goods/list?is_spec_goods=1&is_single=1&record_select_value='+checkedId1 //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var goods = $('input[name="recommend_goods_id"]').val();
                    var body = layer.getChildFrame('body', index);
                    if(goods !='') {
                        if(isNaN(goods)) {
                            var goods_array = goods.split(',');
                            body.contents().find("tr").each(function(){
                                if($.inArray($(this).attr("goods_id"),goods_array)>-1){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                        else{
                            body.contents().find("tr").each(function(){
                                if($(this).attr("goods_id")==goods){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                    }
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var recommend_goods_id = $("input[name='recommend_goods_id']").val();
                    var iframe = layer.getChildFrame('body', index);
                    var goods=[];
                    var body = layer.getChildFrame('body', index);
                    var checkedId = iframe.find('#record_select_value').val();
                    $('input[name="recommend_goods_id"]').val(checkedId);
                    console.log(checkedId);
                    util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                        console.log(response);
                        var list = response.content;
                        var html = '';
                        for (var i in list) {
                            list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                            html += '<tr>' +
                                '        <td>' +
                                '            <div class="goods_img">' +
                                '                <img src="'+list[i].goods_img+'" />' +
                                '            </div>' +
                                '            <div class="goods_info clearfix" num="'+list[i].goods_number+'" prd_id="'+list[i].prd_id+'">' +
                                '                <div class="goods_name">'+list[i].goods_name+list[i].prd_desc+'</div>' +
                                '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                                '<td>'+list[i].goods_number+'</td>' +
                                '<td><a href="##" item="'+list[i].prd_id+'" class="change_goods_del">删除</a></td>'
                            '        </td>';
                            html += '</tr>';
                        }
                        $('.goods_area').css('display','block');
                        $('.goods_modal').css('display','block');
                        $('.goods_modal .tbody').html(html);
                        $('.goods_table').css('display','block');
                        $('.goods_table .tbody').html(html);
                        check_goods_area_height()
                        layer.close(index);

                    },{select_id: checkedId,is_spec_goods: 1});
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    $(document).on('click', '.change_goods_del', function () {
        var good_id = $('input[name="recommend_goods_id"]').val();
        var goodIds = good_id.split(',');
        var index = $.inArray($(this).attr('item'), goodIds);
        if (index) {
            goodIds.splice(index, 1);
        }
        $('input[name="recommend_goods_id"]').val(goodIds.join(','));
        $(this).parent().parent().parent().find('tr').length < 1 ? $('.goods_modal').css('display','none') : $(this).parent().parent().remove();
        if($('.goods_table tbody').find('tr').length < 1){
            $('.goods_table').css('display','none')
        }
        if($('.goods_modal tbody').find('tr').length < 1){
            $('.goods_modal').css('display','none')
        }
        check_goods_area_height()
    });
    function check_goods_area_height(){
        let goods_modal = $('.goods_modal').outerHeight();
        let goods_table = $('.goods_table').outerHeight();
        if( goods_table > 300 || goods_modal > 300){
            $('.goods_area').css({
                'height': '300px',
                'overflow-y': 'scroll',
            })
        } else {
            $('.goods_area').css({
                'height': 'auto',
                'overflow-y': 'auto',
            })
        }
    }

    lottery_manager.init_lottery();
