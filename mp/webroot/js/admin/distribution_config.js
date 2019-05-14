$(function () {
    //开关
    $(" .draggable").click(function () {
        var img_iud = $(this).parent().parent().attr('img_id');
        if(img_iud == 0){
            $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat");
            $(this).parent().parent().css("background-size","100% 100%");
            $(this).parent().parent().attr('img_id','1');
            $(this).animate({right:"0px"});
            $(this).prev().prop('checked',false);
            $(this).parent().parent().parent().parent().find(".is_open").text("已开启");
        }else if(img_iud == 1){
            $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat");
            $(this).parent().parent().css("background-size","100% 100%");
            $(this).parent().parent().attr('img_id','0');
            $(this).animate({right:"20px"});
            $(this).prev().prop('checked',true);
            $(this).parent().parent().parent().parent().find(".is_open").text("已关闭");
        }
    });


    //选择商品
    $('.choose_goods').on('click',function(){
        var currentTime = (new Date()).valueOf();
        var startTime = $('input[name="start_time"]').val();
        startTime = startTime.replace(/-/g, '/');
        var time = new Date(startTime);
        time = time.getTime();
        var endTime = $('input[name="end_time"]').val();
        endTime = endTime.replace(/-/g, '/');
        var time1 = new Date(endTime);
        time1 = time1.getTime();
        

        // if(time < currentTime && disabled) {
        //     $('.choose_goods').css('background', '#ebebe4');
        //     return false;
        // }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var checkedId1 = $('input[name="recommend_goods_id"]').val();
            var goods = [];
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
                    // var body = layer.getChildFrame('body', index);
                    // if(goods !='') {
                    //     if(isNaN(goods)) {
                    //         var goods_array = goods.split(',');
                    //         body.contents().find("tr").each(function(){
                    //             if($.inArray($(this).attr("goods_id"),goods_array)>-1){
                    //                 $(this).attr('data-back','false').addClass('goods_tr_choose');
                    //             }
                    //         });
                    //     }
                    //     else{
                    //         body.contents().find("tr").each(function(){
                    //             if($(this).attr("goods_id")==goods){
                    //                 $(this).attr('data-back','false').addClass('goods_tr_choose');
                    //             }
                    //         });
                    //     }
                    // }
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var body = layer.getChildFrame('body', index);
                    var checkedId = iframe.find('#record_select_value').val();
                    $('input[name="recommend_goods_id"]').val(checkedId);
                    
                    if (body.find('#record_select_value').val() == '') {
                        util.mobile_alert('请选择商品');
                        return false;
                    }
                    // if (body.find('#record_select_value').val().split(',').length > 20) {
                    //     util.mobile_alert('最多选择20个商品');
                    //     return false;
                    // }

                    util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                      var list = response.content;
                      var html = '';
                      for (var i in list) {
                        list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                        html += '<tr>' +
                            '        <td>' +
                            '            <div class="goods_img">' +
                            '                <img src="'+list[i].goods_img+'" />' +
                        '            </div>' +
                        '            <div class="goods_info clearfix">' +
                        '                <div class="goods_name">'+list[i].goods_name+'</div>' +
                        '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                                    '<td>'+list[i].goods_number+'</td>' +
                                    '<td><a href="##" item="'+list[i].prd_id+'" class="change_goods_del">删除</a></td>'
                        '        </td>';
                        html += '</tr>';
                      }
                      $('.goods_modal').css('display','block');
                      $('.goods_modal .tbody').html(html);
                      $('.goods_table').css('display','block');
                      $('.goods_table .tbody').html(html);
                      check_goods_area_height()
                      layer.close(index);
                    },{select_id: checkedId});

                    // if(goods_id){
                    //     $(".goods_table tr:gt(0)").remove();
                    //     iframe.contents().find('tr[data-back="false"]').each(function(){
                    //         var el = $('.goods_modal_clone').find('tr').clone();
                    //         console.log(el);
                    //         el.removeClass('hide');
                    //         goods.push($(this).attr('goods_id'));
                    //         el.find('td').eq(0).html($(this).find('td').eq(0).html());
                    //         el.find('td').eq(1).text($(this).find('td').eq(2).text());
                    //         el.find('td').eq(2).text($(this).find('td').eq(3).text());
                    //         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
                    //         $('.goods_table tr:first-child').after(el);
                    //     });

                    //     if($('.goods_table tr').length == 1){
                    //         $('.goods_table').hide();
                    //     } else {
                    //         $('.goods_table').show();
                    //     }
                    // }else{
                    //     $(".goods_modal tr:gt(0)").remove();
                    //     iframe.contents().find('tr[data-back="false"]').each(function(){
                    //         var el = $('.goods_modal_clone').find('tr').clone();
                    //         el.removeClass('hide');
                    //         goods.push($(this).attr('goods_id'));
                    //         el.find('td').eq(0).html($(this).find('td').eq(0).html());
                    //         el.find('td').eq(1).text($(this).find('td').eq(2).text());
                    //         el.find('td').eq(2).text($(this).find('td').eq(3).text());
                    //         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
                    //         $('.goods_modal tr:first-child').after(el);
                    //     });

                    //     if($('.goods_modal tr').length == 1){
                    //         $('.goods_modal').hide();
                    //     } else {
                    //         $('.goods_modal').show();
                    //     }
                    // }
                    // $("input[name='recommend_goods_id']").val(goods);
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
    //选择分类
    $('.add_cate,.edit_cls').on('click',function(){
        var count = $(this).attr("cat_count");
        if(count==0){
            util.mobile_alert('无可选分类');
        }
        else {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 1
                    , title: ['添加平台分类', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['580px','540px']
                    , content: $('#set-category') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , yes: function (index, layero) { //保存按钮的回调
                        var cat_array = [];
                            $(".cat_table tr:gt(0)").remove();
                            var ul = $('<ul>');
                            ul.addClass('cat_ul clearfix');
                            var html= `<div>
                                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span><span class="third_cat">三级分类</span>
                                      </div>`
                            $('input[name="cat_id[]"]:checked').each(function () {
                                cat_array.push($(this).val());
                                // var el = $('<li>');
                                // el.text($(this).next().text());
                                // el.find('.del_cat').attr('cat_id', $(this).val());
                                // $('.cat_modal tr:last-child ul').append(el);
                                // ul.append(el)
                            });
                            $('.cate_li').each(function(){
                                var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                                var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                                var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                                thirdCheck.each(function(){
                                    if($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')){
                                        return;
                                    } else if($(this).is(':checked')) {
                                        var el = $('<li class="third_cat">');
                                            el.text($(this).next().text());
                                            ul.append(el)
                                            return;
                                    }
                                })
                                if(firstCheck.is(':checked') === true){
                                    var el = $('<li class="first_cat">');
                                    el.text(firstCheck.next().text());
                                    ul.append(el)
                                    return;
                                } else if (firstCheck.is(':checked') === false) {
                                    secondCheck.each(function(){
                                        if($(this).is(':checked')){
                                            var el = $('<li class="second_cat">');
                                            el.text($(this).next().text());
                                            ul.append(el)
                                            return;
                                        } 
                                        // else {
                                        //     var thirdCheck = $(that).find('.third_cate').children().find('input[type="checkbox"]');
                                        //     console.log(thirdCheck);
                                        // }
                                    })
                                }
                            })
                            $('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
                            if ($('.cat_table ul li').length === 0) {
                                $('.cat_table').hide();
                            } else {
                                $('.cat_table').show();
                            }
                        $("input[name='recommend_cat_id']").val(cat_array);
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调


                    }
                });
            });
        }
    });
    //一级分类的全选
    $('#set-category').on('click','.first_cate input[type="checkbox"]',function () {
        if($(this).is(':checked')){
            $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",true);
        }else{
            $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",false);
        }
    });
//二级分类的选中
    $('#set-category').on('click','.second_cate .second_box',function () {
        var allLengthS = $(this).parent().parent().parent().find('.second_box').length;
        var checkedLengthS = $(this).parent().parent().parent().find('.second_box:checked').length;
        // if(allLengthS == checkedLengthS){
        //     // $(this).parent().parent().parent().prev().find('input[type="checkbox"]').prop('checked',true);
        //     if(checkedLengthS>0){
        //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',true);
        //     }else{
        //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',false);

        //     }
        // }else{
        //     if(checkedLengthS>0){
        //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',true);
        //     }else{
        //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',false);
        //     }
        // }
        /* 二级分类点击，下面所有三级分类被选中 */
        $(this).parent().next().find('input[type="checkbox"]').prop("checked",$(this).prop("checked"));
        /* 二级分类全部选中，一级选中 */
        if(allLengthS === checkedLengthS){
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
        } else {
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
        }
    });
//三级分类的选中
    $('#set-category').on('click','.third_cate input[type="checkbox"]',function () {
        $(this).attr("checked", "checked");
        var allLength = $(this).parent().parent().find('input[type="checkbox"]').length;
        var checkedLength = $(this).parent().parent().find('input[type="checkbox"]:checked').length;
        if(allLength === checkedLength){
            $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",true);
        } else {
            $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",false);
        }
        var secondLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]').length;
        var secondCheckedLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]:checked').length;
        /* 三级全部选中 判断二级是否全部选中 */
        if(secondLength === secondCheckedLength){
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
        } else {
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
        }
    });
//点击展开二级和三级分类
    $('#set-category').on('click','.cate_open',function(){
        var flag_open = $(this).attr('data-flag');
        if(flag_open == 'true'){
            $(this).parent().next().show();
            $(this).attr('src','/image/admin/cate_jian.png');
            $(this).attr('data-flag','false');
            flag_open = 'false';
        }else if(flag_open == 'false'){
            $(this).parent().next().hide();
            $(this).attr('src','/image/admin/cate_jia.png');
            $(this).attr('data-flag','true');
            flag_open = 'true';
        }
    });

    //选择商品
    if($("#alls").is(":checked")){
        $(".choosed_info").hide();
    }
    if($("#somes").is(":checked")){
        $(".choosed_info").show();
    }

    $("input[name='recommend_type']").change(function () {
        if($("#alls").is(":checked")){
            $(".choosed_info").hide();
            $(".goods_table").hide();
            $(".cat_table").hide();
            $(".cat_modal").hide();
            $(".goods_modal").hide();
        }
        if($("#somes").is(":checked")){
            $(".choosed_info").show();
            $(".goods_table").show();
            if(cat_id){
                $(".cat_table").show();
            } else {
                $(".cat_table").hide();
            }
           
            if($(".cat_modal").find('tr').length >1){
                $(".cat_modal").show();
            }
            if($(".goods_modal").find('tr').length >1){
                $(".goods_modal").show();
            }
        }
    });

    // //设置默认背景
    // var img_path1 = $(".ud_li select option:selected").val();
    // $(".have_bg").css("background-image","url("+img_path1+")");
    // $(".have_bg").css("background-size","100%,100%");
    // $(".ud_li select").change(function () {
    //     var img_path1 = $(".ud_li select option:selected").val();
    //     $(".have_bg").css("background-image","url("+img_path1+")");
    //     $(".have_bg").css("background-size","100%,100%");
    // })

    //上传图片
    $('.add_images').click(function() {
        var el = $(this).parent();
        var w = 640;
        var h = 640;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find("img").attr("src", path);
                el.find("img").attr("class",'no_margin');
                $("input[name='bg_img']").val(path);
                $(".have_bg").css("background-image","url("+path+")");
                $(".have_bg").css("background-size","100%,100%");
            }
        });
    });

    $(".rebeat_percent input[name='scale']").blur(function () {
        if($('input[name="status"]').parent().parent().attr("img_id")==1){
            var fanli_srt = $(this).val();
            if(fanli_srt == ""){
                util.mobile_alert('请填写返利比例');
                $(this).focus();
            }
            if(fanli_srt != ""){
                if(parseFloat(fanli_srt) > 100){
                    util.mobile_alert('返利比例值不能大于100%');
                    $(this).focus();
                }
                if(fanli_srt.indexOf('.') != -1){
                    var fanli_index = fanli_srt.lastIndexOf("\.");
                    var fanli_boj = fanli_srt.substring(fanli_index+1,fanli_index.length);
                    if(parseFloat(fanli_boj) > 1){
                        util.mobile_alert('小数点后请保留一位小数');
                        $(this).focus();
                    }
                }
            }
        }


    })
    if(!$("#status input[type='checkbox']").is(":checked")){
        $('.config_detail ul li').hide();
        $('.config_detail ul li:first-of-type').show();
        $('.config_detail ul li:nth-of-type(2)').show();
        $('.config_detail').css("padding-bottom","10px");
    }else{
        $('.config_detail ul li').show();
    }
    $("#status input[type='checkbox']").change(function () {
        if(!$("#status input[type='checkbox']").is(":checked")){
            $('.config_detail ul li').hide();
            $('.config_detail ul li:first-of-type').show();
            $('.config_detail ul li:nth-of-type(2)').show();
            $('.config_detail').css("padding-bottom","10px");
        }else{
            $('.config_detail ul li').show();
        }
    })
    $('input[name="judge_status"]').change(function(){
        if($(this).is(':checked')){
            $('.distribution_info').show()
        } else {
            $('.distribution_info').hide()
        }
    })
    // if($('input[name="judge_status"]').is(':checked')){
    //     $('.distribution_info').show()
    // } else {
    //     $('.distribution_info').hide()
    // }
    $('input[name="activation"]').change(function(){
        if($(this).is(':checked')){
            $('.activation_cfg').show()
        } else {
            $('.activation_cfg').hide()
        }
    });
    // if($('input[name="activation"]').is(':checked')){
    //     //     $('.activation_cfg').show()
    //     // } else {
    //     //     $('.activation_cfg').hide()
    //     // }
    $(".btn_to_save").on("click",function(){
        if($("#status").attr("img_id")==1){
            // var bili_val = $(".rebeat_percent input[name='scale']").val();
            // if(bili_val == ""){
            //     util.mobile_alert('请填写返利比例');
            //     return false;
            // }
            // if(bili_val != ""){
            //     if(parseFloat(bili_val) > 100){
            //         util.mobile_alert('返利比例值不能大于100');
            //         $(".rebeat_percent input[name='scale']").focus();
            //         return false;
            //     }
            //     if(bili_val.indexOf('.') != -1){
            //         var fanli_index = bili_val.lastIndexOf("\.");
            //         var fanli_boj = bili_val.substring(fanli_index+1,fanli_index.length);
            //         if(parseFloat(fanli_boj) > 1){
            //             util.mobile_alert('小数点后请保留一位小数');
            //             $(".rebeat_percent input[name='scale']").focus();
            //             return false;
            //         }
            //     }
            // }
            // if($("#somes").is(":checked")
            //     && ($("input[name='recommend_goods_id']").val() == '' &&
            //         $("input[name='recommend_cat_id']").val() == '')) {
            //     util.mobile_alert('请选择商品或分类');
            //     return false;
            // }
            // if($("#alls").is(":checked")){
            //     $("input[name='recommend_goods_id']").val('');
            //     $("input[name='recommend_cat_id']").val('');
            // }
        }
        if($('input[name="withdraw_status"]:checked') == 1){
            if($('input[name="withdraw_cash"]').val()<1){
                util.mobile_alert("返利最小提现金额应大于1");
                return false;
            }
        }
        // console.log($('.activation_cfg:checked').length)
		if($('input[name="activation"]').is(':checked')){
            if($('.activation_cfg:checked').length < 1){
                util.mobile_alert("至少选择一项需提交信息");
                return false;
            }
        }
		if($('input[name="protect_type"]').val() != 1 && $('input[name="vaild_type"]').val() != 1){
            let vaild = parseInt($('input[name="vaild"]').val());
            let protect_date = parseInt($('input[name="protect_date"]').val());
            // console.log(vaild)
            // console.log(protect_date)
            if(vaild > 0 && protect_date > 0 && vaild > protect_date){
                util.mobile_alert("分销员保护期应大于返利有效期");
                return false;
            }
        }
        layer.ready(function () {
            layer.msg('保存成功', {time: 1000},function () {
                $("#form1").submit();
            });
        });
    })


    function onCateload(){
        if(cat_id){
            $(".cat_table tr:gt(0)").remove();
            var ul = $('<ul>');
            ul.addClass('cat_ul clearfix');
            var html= `<div>
                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span><span class="third_cat">三级分类</span>
                        </div>`
            $('.cate_li').each(function(){
                var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                thirdCheck.each(function(){
                    if($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')){
                        return;
                    } else if($(this).is(':checked')) {
                        var el = $('<li class="third_cat">');
                            el.text($(this).next().text());
                            ul.append(el)
                            return;
                    }
                })
                if(firstCheck.is(':checked') === true){
                    var el = $('<li class="first_cat">');
                    el.text(firstCheck.next().text());
                    ul.append(el)
                    return;
                } else if (firstCheck.is(':checked') === false) {
                    secondCheck.each(function(){
                        if($(this).is(':checked')){
                            var el = $('<li class="second_cat">');
                            el.text($(this).next().text());
                            ul.append(el)
                            return;
                        } 
                        // else {
                        //     var thirdCheck = $(that).find('.third_cate').children().find('input[type="checkbox"]');
                        //     console.log(thirdCheck);
                        // }
                    })
                }
            })
            $('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
            // console.log(1)
            $('.cat_table').show()
        }else{
            $('.cat_table').hide()
        }
    }
    onCateload();
    check_goods_area_height()
});

