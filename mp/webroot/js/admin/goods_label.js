$(function(){
    //切换
    if($("input[name='is_all']:nth-of-type(1)").is(":checked")){
        $('.choose_goods').css("display",'none');
        $('.goods_area').css("display",'none');
        $('.add_cate').css("display",'none');
        $('.add_sort').css("display",'none');
        $('.sort_area').css("display",'none');
    }else{
        $('.choose_goods').css("display",'block');
        $('.goods_area').css("display",'block');
        $('.add_cate').css("display",'block');
        $('.add_sort').css("display",'block');
        $('.sort_area').css("display",'block');
    }
    $("input[name='is_all']").change(function () {
        if($("input[name='is_all']:nth-of-type(1)").is(":checked")){
            $('.choose_goods').css("display",'none');
            $('.goods_area').css("display",'none');
            $('.add_cate').css("display",'none');
            $('.add_sort').css("display",'none');
            $('.sort_area').css("display",'none');
        }else{
            $('.choose_goods').css("display",'block');
            $('.goods_area').css("display",'block');
            $('.add_cate').css("display",'block');
            $('.add_sort').css("display",'block');
            $('.sort_area').css("display",'block');
        }
    })

    //选择商品
    $('.choose_goods').on('click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var checkedId1 = $('input[name="gl_goods_id"]').val();
            console.log(checkedId1);
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
                    var body = layer.getChildFrame('body', index);
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
                    $('input[name="gl_goods_id"]').val(checkedId);

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
                                '            <div class="goods_info">' +
                                '                <div class="goods_name">'+list[i].goods_name+'</div>' +
                                '                <div class="goods_spec">'+list[i].prd_desc+'</div>' +
                                '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                                '<td>'+list[i].goods_number+'</td>' +
                                '<td><a href="##" goods_id='+list[i].goods_id+' class="del">删除</a></td>'
                            '        </td>';
                            html += '</tr>';
                        }
                        $('.label_tbody').html(html);
                        check_goods_area_height()
                        $('.goods_table').show();
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

    // //选择商品
    // $('.choose_goods').on('click',function(){
    //     layui.use('layer', function() { //独立版的layer无需执行这一句
    //         var $ = layui.jquery, layer = layui.layer;
    //         layer.open({
    //             type: 2
    //             , title: ['选择商品', 'text-align:center;padding: 0px;']
    //             , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
    //             , area: ['825px','430px']
    //             , content: '/admin/frame/goods/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
    //             , btn: ['确定', '取消']
    //             , btnAlign: 'r' //按钮居右
    //             , shade: [0.3, '#000'] //显示遮罩透明度和颜色
    //             ,success: function (layero, index) {
    //                 var goods = $('input[name="gl_goods_id"]').val();
    //                 console.log(goods);
    //                 var body = layer.getChildFrame('body', index);
    //                 if(goods !='') {
    //                     if(isNaN(goods)) {
    //                         var goods_array = goods.split(',');
    //                         body.contents().find("tr").each(function(){
    //                             if($.inArray($(this).attr("goods_id"),goods_array)>-1){
    //                                 $(this).attr('data-back','false').addClass('goods_tr_choose');
    //                             }
    //                         });
    //                     }
    //                     else{
    //                         body.contents().find("tr").each(function(){
    //                             if($(this).attr("goods_id")==goods){
    //                                 $(this).attr('data-back','false').addClass('goods_tr_choose');
    //                             }
    //                         });
    //                     }
    //                 }
    //             }
    //             , yes: function (index, layero) { //保存按钮的回调
    //                 // var recommend_goods_id = $("input[name='recommend_goods_id']").val();
    //                 var iframe = layer.getChildFrame('body', index);
    //                 var goods=[];
    //                 if(goods_id){
    //                     $(".goods_table tr:gt(0)").remove();
    //                     iframe.contents().find('tr[data-back="false"]').each(function(){
    //                         var el = $('.goods_modal_clone').find('tr').clone();
    //                         // console.log(el);
    //                         el.removeClass('hide');
    //                         goods.push($(this).attr('goods_id'));
    //                         el.find('td').eq(0).html($(this).find('td').eq(0).html());
    //                         el.find('td').eq(1).text($(this).find('td').eq(2).text());
    //                         el.find('td').eq(2).text($(this).find('td').eq(3).text());
    //                         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
    //                         $('.goods_table tbody').after(el);
    //                     });
    //                     // $('.goods_table').show();
    //                     if ($('.goods_table tr').length <= 1) {
    //                         $('.goods_table').hide();
    //                     } else {
    //                         $('.goods_table').show();
    //                     }
    //                 }else{
    //                     $(".goods_modal tr:gt(0)").remove();
    //                     iframe.contents().find('tr[data-back="false"]').each(function(){
    //                         var el = $('.goods_modal_clone').find('tr').clone();
    //                         el.removeClass('hide');
    //                         goods.push($(this).attr('goods_id'));
    //                         el.find('td').eq(0).html($(this).find('td').eq(0).html());
    //                         el.find('td').eq(1).text($(this).find('td').eq(2).text());
    //                         el.find('td').eq(2).text($(this).find('td').eq(3).text());
    //                         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
    //                         $('.goods_modal tbody').after(el);
    //                     });
    //                     // $('.goods_modal').show();
    //                     if ($('.goods_modal tr').length <= 1) {
    //                         $('.goods_modal').hide();
    //                     } else {
    //                         $('.goods_modal').show();
    //                     }
    //                 }
    //                 $("input[name='gl_goods_id']").val(goods.join(','));
    //                 layer.close(index);
    //             }, btn2: function (index, layero) {
    //                 //按钮取消的回调
    //             }
    //         });
    //     });
    // });

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
                                })
                            }
                        });
                        $('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
                        if ($('.cat_table ul li').length === 0) {
                            $('.cat_table').hide();
                        } else {
                            $('.cat_table').show();
                        }
                        $("input[name='gl_cat_id']").val(cat_array);

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

    //保存验证
    $(".btn_save a").click(function () {
        if($(".label_name").val() == ""){
            util.mobile_alert('请填写商品标签名称');
            $(".label_name").focus();
            return false;
        }
        if($(".label_level").val().replace(/(^\s*)|(\s*$)/g, "") == ""){
            util.mobile_alert('请填写优先级');
            $(".label_level").focus();
            return false;
        }
        if($("[name='is_all']:checked").val() === 0 && $("[name='gl_goods_id']").val() == '' && $("[name='gl_cat_id']").val() == '') {
            util.mobile_alert('请选择商品');
            return false;
        }
        // if($(".gd_detail").prop("checked") == false && $(".gd_list").prop("checked") == false){
        //     util.mobile_alert("请选择前端应用模块");
        //     return false;
        // }
        util.ajax_json('/admin/goods/label/add',function (res) {
            if (res.error == 0) {
                hasSaved = true;
                util.mobile_alert(res.content);
                window.location = '/admin/goods/label/list?top_index=2';
            } else {
                util.mobile_alert(res.message);
            }
        },$("#form1").serialize())
    })

    $('.show_displaybcg').mouseover(function () {
        $('.contain_show_bcg').css("display","block");
        $('.contain_show_bcg').mouseover(function () {
            $('.contain_show_bcg').css("display","block");
        });
        $('.contain_show_bcg').mouseleave(function () {
            $('.contain_show_bcg').css("display","none");
        })
    });
    $('.show_displaybcg').mouseleave(function () {
        $('.contain_show_bcg').css("display","none");
    });

    $('.show_displaybcg2').mouseover(function () {
        $('.contain_show_bcg2').css("display","block");
        $('.contain_show_bcg2').mouseover(function () {
            $('.contain_show_bcg2').css("display","block");
        });
        $('.contain_show_bcg2').mouseleave(function () {
            $('.contain_show_bcg2').css("display","none");
        })
    });
    $('.show_displaybcg2').mouseleave(function () {
        $('.contain_show_bcg2').css("display","none");
    });

    $(".tc_label").click(function () {
        $(".label_name").val("");
        var label_val = $(this).text();
        $(".label_name").val(label_val);
        $(".down_label").css("display","none");
    })

    $(".label_name").focus(function () {
        $(".down_label").css("display","block");
    })
    // $(".label_name").on("input ", function() {
    //     var $this = $(this),
    //         _val = $.trim($this.val()),
    //         len = 0;
    //     for (var i=0; i<_val.length; i++) {
    //         var c = _val.charCodeAt(i);
    //         //单字节加1
    //         if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
    //             len++;
    //         }
    //         else {
    //             len+=2;
    //         }
    //     }
    //     if (len > 8) {
    //         // $this.val(_val.substring(0, 3));
    //         $this.val(cut_str(_val,4))
    //     }
    // });
    
    // function cut_str(str, len){
    //     var char_length = 0;
    //     for (var i = 0; i < str.length; i++){
    //         var son_str = str.charAt(i);
    //         encodeURI(son_str).length > 2 ? char_length += 1 : char_length += 0.5;
    //         if (char_length >= len){
    //             var sub_len = char_length == len ? i+1 : i;
    //             return str.substr(0, sub_len);
    //             break;
    //         }
    //     }
    // }

    $(".label_level").blur(function () {
        if (!(/(^[1-9]\d*$)/.test($(this).val()))) {
            util.mobile_alert("优先级必须为正整数");
            $(this).focus();
            $(this).val("");
        }
    });

    document.onclick = function(e) {
        e = window.event || e;
        obj = $(e.srcElement || e.target);
        if (obj.attr('class') != 'label_name' && obj.attr(".class") !='down_label') {
                $('.down_label').hide();
         }
    };
  $(".gd_list").click(function () {
      if($(".gd_list").prop("checked")){
          $(".label-content").show();
      }else{
          $(".label-content").hide();
      }
  })
    function onCateload(){
        console.log(cat_id)
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
                    })
                }
            })
            $('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
            $('.cat_table').show()
        }else{
            $('.cat_table').hide()
        }
    }
    onCateload();
    check_goods_area_height()
})