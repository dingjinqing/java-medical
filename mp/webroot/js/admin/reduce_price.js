//选择商品
$('.choose_goods, .goods-btn-modify').on('click',function(){
    var checkedId1 = $('input[name="goods_id"]').val();
    var id = $("input[name='id']").val() > 0 ? $("input[name='id']").val() : -1;
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','450px']
            , content: '/admin/public/purchase/goods/list?reduce_price='+id+'&record_select_value='+checkedId1+'&is_tips=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {

            }
            , yes: function (index, layero) { //保存按钮的回调
                $(".reduce_content").removeClass("hide");
                var body = layer.getChildFrame('body', index);
                var checkedId = body.find('#record_select_value').val();
                if (body.find('#record_select_value').val() == '') {
                    util.mobile_alert('请选择商品');
                    return false;
                }
                if (body.find('#record_select_value').val().split(',').length > 100) {
                    util.mobile_alert('最多选择100个商品');
                    return false;
                }
                var old_goods = $('input[name="goods_id"]').val().split(",");
                util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                    var list = response.content;
                    var html = '';
                    for (var i in list) {
                        if($.inArray(list[i].goods_id.toString(),old_goods) == -1){
                            // list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                            html +=`<tr class="${ list[i].goods_id }">
                                       <td><input type="checkbox" class="choose_single"></td>
                                       <td>
                                           <div class="goods_img">
                                               <img src="${ list[i].goods_img }" />
                                           </div>
                                           <div class="goods_info">
                                               <div class="goods_name">${ list[i].goods_name }</div>
                                           </div>
                                       </td>
                                       <td class="original_price">${ list[i].shop_price}</td>
                                       <td>${list[i].goods_number }</td>
                                       <td><input type="text" class="price_zk" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">折</td>
                                       <td><input type="text" class="price_jj" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">元</td>
                                       <td class="price_zhj">
                                           <div class="price_red" >
                                           </div>
                                           <input type="text" class="price_zh" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" style="font-size: 14px">元
                                           <div class="price_blue" goods_id="${ list[i].goods_id }" prd_num="${ list[i].prd_num }">
                                               <span>${ list[i].prd_num }</span>
                                               个规格降价
                                           </div>
                                       </td>
                                       <td><a item="${ list[i].goods_id }" class="change_goods_del">删除</a></td>
                                   </tr>`;
                        }
                    }
                    if($("input[name='id']").val() == ""){
                        $(".add_content").css("display","block");
                        // $(".edit_content").css("display",'none');
                    }
                    $(".reduce_tbody").append(html);
                    if(old_goods[0]!=""){
                        for(var j in old_goods){
                            if(!in_array(old_goods[j],checkedId.split(","))){
                                $(".reduce_tbody").find('.'+ old_goods[j] ).remove();
                            }
                        }
                    }
                    $('input[name="goods_id"]').val(checkedId);
                    util.ajax_json("/admin/reduce/select",function (res) {
                        var list = res.content.data;
                        $(".reduce_tbody").find("tr").each(function (j,k) {
                            var html_prd = '';
                            for (var m in list) {
                                if($.inArray(list[m].goods_id.toString(),old_goods) == -1){
                                    if ($(".reduce_tbody").find("tr").eq(j).attr("class") == list[m].goods_id) {
                                        var list_prd = list[m].prd_list;
                                        for (var n in list_prd) {
                                            html_prd += `<input type="hidden" name="${ list_prd[n].prd_id }" prd_id="${ list_prd[n].prd_id }" value="${ list_prd[n].prd_price }" goods_id="${ list_prd[n].goods_id }" class="spec_value" prd_price="${ list_prd[n].price }">`;
                                        }
                                    }
                                }
                            }
                            $(".reduce_tbody").find("tr").eq(j).append(html_prd);
                        })
                    },{select_goods_id:checkedId})
                    if ($("#choose_all").prop('checked')) {
                        $(".choose_single").prop('checked', 'checked');
                    } else {
                        $(".choose_single").prop('checked', false);
                    }
                    $(".price_blue").each(function (q,w) {
                        if($(".price_blue").eq(q).attr("prd_num")<1){
                            $(".price_blue").eq(q).hide();
                        }
                    })
                    layer.close(index);
                }, {select_id:checkedId})

            }, btn2: function (index, layero) {
                //按钮取消的回调

            }
        });
    });
});
function in_array(search,array){
    for(var i in array){
        if(array[i]==search){
            return true;
        }
    }
    return false;
}


$(document).on('click','.price_blue',function () {
    var _this = $(this);
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['多规格折后价', 'text-align:center;padding: 0px;font-size:16px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['650px','300px']
            , content: '/admin/reduce/select?goods_id='+_this.attr("goods_id")+"&id="+$("input[name='id']").val()  //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (layero, index) {
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']];
                var spec =_this.parent().parent().find(".spec_value");
                var arry = [];
                spec.each(function (i,item) {
                    var goods_price =$(this).val();
                    arry.push(goods_price);
                })
                body.find(".label_tbody tr").each(function (i,item) {
                    $(this).find(".price").val(arry[i]);
                })

            }
            , yes: function (index, layero) { //保存按钮的回调
                var body = layer.getChildFrame('body', index);
                var iframeWin = window[layero.find('iframe')[0]['name']];
                var spec =_this.parent().parent().find(".spec_value");
                var price = [];
                var prd_price = [];
                var flag = 0;
                body.find(".label_tbody tr").each(function (i,item) {
                    price[i] = $(this).find(".price").val();
                    prd_price[i] = $(this).find(".prd_price").text();
                    if(parseFloat(prd_price[i]) < parseFloat(price[i])){
                        flag++;
                        $(this).find(".price").focus();
                        return false;
                    }
                })
                if(flag > 0){
                    util.mobile_alert("折后价不能大于原价");
                    return false;
                }
                spec.each(function (i,item) {
                  $(this).val(price[i]);
                })
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
                // $("#set-label").hide();
            },cancel:function (index, layero) {
                // $("#set-label").hide();
            }
        });
    });
});

//设置商品
$(".reduce_text").click(function () {
    $(this).parent().find("input[type='radio']").prop("checked",true);
})

$("#prd_discount").blur(function () {
    if($(this).val() < 0 || $(this).val() > 10){
        util.mobile_alert('折扣只能输入0-10');
        $(this).focus();
    }
    if($(this).val().indexOf(".") != -1){
        var a = parseFloat($(this).val()).toFixed(2);
    }else{
        var a = $(this).val();
    }
    $(this).val(a);
});
$("#prd_reduce , #prd_after").blur(function () {
    if($(this).val().indexOf(".") != -1){
        var a = parseFloat($(this).val()).toFixed(2);
    }else{
        var a = $(this).val();
    }
    $(this).val(a);
});



//全选
$(document).on('change','#choose_all',function () {
    if ($(this).prop('checked')) {
        $(".choose_single").prop('checked', 'checked');
    } else {
        $(".choose_single").prop('checked', false);
    }
});

$(document).on('change','.choose_single',function () {
    if ($(".choose_single:checked").length == $(".choose_single").length) {
        $("#choose_all").prop('checked', true);
    } else {
        $("#choose_all").prop('checked', false);
    }
})
//删除
$(document).on('click','.change_goods_del',function () {
    var that = $(this);
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.alert('<div style="text-align: center;">' + '确认要删除吗?' + '</div>', {
            title: ['提醒', 'text-align:center;padding: 0px;']
            , area: '260px'
            , closeBtn: 0
            , btn: ['确定', '取消']
        },function(index){
            that.parent().parent().remove();
            var ids = that.attr("item");
            if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
                $("#choose_all").prop('checked', true);
            } else {
                $("#choose_all").prop('checked', false);
            }
            var old_goods = $('input[name="goods_id"]').val().split(",");
            var new_goods = [];
            for( var i in old_goods){
                if(ids != old_goods[i]){
                    new_goods.push(old_goods[i]);
                }
            }
            $('input[name="goods_id"]').val(new_goods.join(","));
            layer.close(index);
        });
    });
});

//批量删除
$(".price_choose_del").click(function () {
    var that = $(this);
    if($(".choose_single:checked").length == 0){
        util.mobile_alert("请选择删除的商品");
    }else{
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                var old_goods = $('input[name="goods_id"]').val().split(",");
                var del_goods = [];
                $(".change_goods_del").each(function () {
                    if($(this).parent().parent().find(".choose_single").prop("checked")){
                        $(this).parent().parent().remove();
                        var ids = $(this).attr("item");
                        del_goods.push(ids);
                    }
                })
                var new_goods = [];
                for(var i in old_goods){
                    if(!in_array(old_goods[i],del_goods)){
                        new_goods.push(old_goods[i]);
                    }
                }
                $('input[name="goods_id"]').val(new_goods.join(","));
                if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
                    $("#choose_all").prop('checked', true);
                } else {
                    $("#choose_all").prop('checked', false);
                }
                layer.close(index);
            });
        });
    }
});
//批量价格取整
$(".price_choose_int").click(function () {
    var that = $(this);
    if($(".choose_single:checked").length == 0){
        util.mobile_alert("请选择取整的商品");
    }else{
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要取整吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $(".price_zh").each(function () {
                    if($(this).parent().parent().find(".choose_single").prop("checked")){
                        $(this).val(Math.round(parseFloat($(this).val())));
                    }
                })
                $(".spec_value").each(function () {
                    if($(this).parent().find(".choose_single").prop("checked")){
                        $(this).val(Math.round(parseFloat($(this).val())));
                    }
                })
                layer.close(index);
            });
        });
    }
})


//批量打折 批量减价 批量折后价
$('.reduce_choose').click(function () {
    var _this = $(this);
    if($(".choose_single:checked").length == 0){
        util.mobile_alert("请选择商品后再进行操作");
        return false;
    }
    var flag = 0;
    var blog = 0;
    var click =true;
  $(".reduce_text").each(function (index,item) {
     if($(this).prev().is(':checked')==true){
         var prd = $(this).val();
         if(prd == ''){
             util.mobile_alert("设置值不可为空");
             $(this).focus;
             flag ++;
             return false;
         }
       if( index == 0){
             if(prd > 10){
                 flag ++;
                 return false;
             }

           $(".price_zk").each(function (idx,item) {
               if($(this).parent().parent().find("input[type='checkbox']").is(":checked") == true){
                   var origin_price = parseFloat($(this).parent().parent().find(".original_price").text()); //原价
                   var new_price = (origin_price * parseFloat(prd / 10)).toFixed(2);
                   var jj = parseFloat(origin_price - new_price).toFixed(2);
                   $(this).val(prd);
                   $(this).parent().next().find(".price_jj").val(jj);
                   $(this).parent().parent().find(".price_zh").val(new_price);
                   var spec = $(this).parent().parent().find(".spec_value");
                   spec.each(function (i,item) {
                       var spec_price =$(this).attr("prd_price");
                       var new_spec_price = (spec_price * (parseFloat(prd / 10))).toFixed(2);
                       $(this).val(new_spec_price);
                   })
               }
               // else{
               //     $(this).val('');
               //     $(this).parent().next().find(".price_jj").val('');
               //     $(this).parent().parent().find(".price_zh").val('');
               // }
           })
       }
       if( index == 1){
           $(".price_jj").each(function (idx,item) {
               if($(this).parent().parent().find("input[type='checkbox']").is(":checked") == true){
                   var origin_price = parseFloat($(this).parent().parent().find(".original_price").text()); //原价
                   var new_price = parseFloat(origin_price - prd).toFixed(2);
                   var percent = parseFloat(new_price / origin_price).toFixed(2);
                   var new_percent = parseFloat(percent * 10).toFixed(2);
                   var spec = $(this).parent().parent().find(".spec_value");
                   spec.each(function (i,item) {
                       var spec_price =$(this).attr("prd_price");
                       var new_spec_price = parseFloat(spec_price - prd).toFixed(2);
                       $(this).val(new_spec_price);
                       if(new_spec_price < 0){
                           $(this).parent().find('.price_red').text("降价后规格价不得小于0");
                           return false;
                       }
                       // else if(new_spec_price > spec_price){
                       //     $(this).parent().find('.price_red').text("折后规格价不得大于规格原价");
                       //     return false;
                       // }
                   })
                   if(new_price > origin_price){
                       $(this).parent().parent().find(".price_red").text("降价后金额需要小于原价");
                       $(this).focus();
                   }else if(new_price < 0){
                       $(this).parent().parent().find(".price_red").text("降价后金额不得小于0");
                       $(this).focus();
                   }else if(origin_price > 0 && origin_price < 0.01){
                       $(this).parent().parent().find(".price_red").text("此商品不可降价");
                       $(this).focus();
                   }else{
                       $(this).parent().parent().find(".price_red").text("");
                   }
                   $(this).val(prd);
                   $(this).parent().prev().find(".price_zk").val(new_percent);
                   $(this).parent().parent().find(".price_zh").val(new_price);

               }
               // else{
               //     $(this).val('');
               //     $(this).parent().prev().find(".price_zk").val('');
               //     $(this).parent().parent().find(".price_zh").val('');
               // }
           })
       }
       if( index == 2){
           $(".price_zh").each(function (idx,item) {
               if($(this).parent().parent().find("input[type='checkbox']").is(":checked") == true){
                   var origin_price = parseFloat($(this).parent().parent().find(".original_price").text()); //原价
                   var jj_price = parseFloat(origin_price - prd).toFixed(2);
                   var percent = parseFloat(prd / origin_price).toFixed(2);
                   var new_percent = parseFloat(percent * 10).toFixed(2);
                   var spec = $(this).parent().parent().find(".spec_value");
                   spec.each(function (i,item) {
                       var spec_price =$(this).attr("prd_price");
                       var new_spec_price = prd;
                       $(this).val(new_spec_price);
                       if(new_spec_price < 0){
                           $(this).parent().find('.price_red').text("降价后规格价不得小于0");
                           return false;
                       }else if(new_spec_price > spec_price){
                           $(this).parent().find('.price_red').text("折后规格价不得大于规格原价");
                           return false;
                       }
                   })
                   if(prd > origin_price){
                       $(this).parent().find(".price_red").text("降价金额需小于原价");
                       $(this).focus();
                   }else if(prd < 0){
                       $(this).parent().find(".price_red").text("降价后金额不得小于0");
                       $(this).focus();
                   }else if(origin_price > 0 && origin_price < 0.01){
                       $(this).parent().find(".price_red").text("此商品不可降价");
                       $(this).focus();
                   }else{
                       $(this).parent().parent().find(".price_red").text("");
                   }
                       $(this).val(prd);
                       $(this).parent().prev().find(".price_jj").val(jj_price);
                       $(this).parent().parent().find(".price_zk").val(new_percent);
               }
               // else{
               //     $(this).val('');
               //     $(this).parent().prev().find(".price_jj").val('');
               //     $(this).parent().parent().find(".price_zk").val('');
               // }
           })
       }
         flag ++;
    }
  })
    if(flag == 0){
      util.mobile_alert("请设置折扣方式");
    }
})

//取消
$(".reduce_cancel").click(function () {
    var that = $(this);
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.alert('<div style="text-align: center;">' + '确认要清空设置吗?' + '</div>', {
            title: ['提醒', 'text-align:center;padding: 0px;']
            , area: '260px'
            , closeBtn: 0
            , btn: ['确定', '取消']
        },function(index){
            $("#choose_all").prop("checked",false);
            $(".price_zk,.price_jj,.price_zh").each(function () {
               $(this).val("");
                $(this).parent().parent().find("input[type='checkbox']").prop("checked",false);
            })
            $(".reduce_text").each(function () {
                $(this).prev().prop('checked',false);
                $(this).val("");
            })
            layer.close(index);
        });
    });
})

// $(document).on('click',".price_zk,.price_jj,.price_zh",function () {
//     $(this).parent().parent().find("input[type='checkbox']").prop("checked",true);
//     if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
//         $("#choose_all").prop('checked', true);
//     } else {
//         $("#choose_all").prop('checked', false);
//     }
// })


//单独设置
$(document).on("input propertychange blur",".price_zk",function () {
    if(edit != '0'){
        var prd = $(this).val();
        var origin_price = parseFloat($(this).parent().parent().find(".original_price").text());
        var new_price = (origin_price * parseFloat(prd / 10)).toFixed(2);
        var jj =parseFloat(origin_price - new_price ).toFixed(2);
        var spec = $(this).parent().parent().find(".spec_value");
        spec.each(function (i,item) {
            var spec_price =$(this).attr("prd_price");
            var new_spec_price = (spec_price * (parseFloat(prd / 10))).toFixed(2);
            $(this).val(new_spec_price);
        })
        $(this).parent().parent().find(".price_jj").val(jj);
        $(this).parent().parent().find(".price_zh").val(new_price);
        if(prd == ''){
            $(this).parent().parent().find(".price_jj").val('');
            $(this).parent().parent().find(".price_zh").val('');
            // $(this).parent().parent().find("input[type='checkbox']").prop("checked",false);
            // if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
            //     $("#choose_all").prop('checked', true);
            // } else {
            //     $("#choose_all").prop('checked', false);
            // }
        }
    }

    // else{
    //     $(this).parent().parent().find("input[type='checkbox']").prop("checked",true);
    //     if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
    //         $("#choose_all").prop('checked', true);
    //     } else {
    //         $("#choose_all").prop('checked', false);
    //     }
    // }

    if(prd < 0 || prd> 10){
        util.mobile_alert('折扣只能输入0-10');
        $(this).focus();
    }
    if(new_price > origin_price){
        $(this).parent().parent().find(".price_red").text("降价后金额需要小于原价");
        $(this).focus();
    }else if(new_price < 0){
        $(this).parent().parent().find(".price_red").text("降价后金额不得小于0");
        $(this).focus();
    }else if(origin_price > 0 && origin_price < 0.01){
        $(this).parent().parent().find(".price_red").text("此商品不可降价");
        $(this).focus();
    }else{
        $(this).parent().parent().find(".price_red").text("");
    }
});

$(document).on("input propertychange blur",".price_jj",function () {
    if(edit != '0'){
        var prd = $(this).val();
        var origin_price = parseFloat($(this).parent().parent().find(".original_price").text());
        var new_price = parseFloat(origin_price - prd ).toFixed(2);
        var percent = parseFloat(new_price / origin_price).toFixed(2);;
        var new_percent = parseFloat(percent * 10).toFixed(2);
        var spec = $(this).parent().parent().find(".spec_value");
        $(this).parent().parent().find(".price_zk").val(new_percent);
        $(this).parent().parent().find(".price_zh").val(new_price);
        if(prd == ''){
            $(this).parent().parent().find(".price_zk").val('');
            $(this).parent().parent().find(".price_zh").val('');
            // $(this).parent().parent().find("input[type='checkbox']").prop("checked",false);
            // if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
            //     $("#choose_all").prop('checked', true);
            // } else {
            //     $("#choose_all").prop('checked', false);
            // }
        }
    }

    // else{
    //     $(this).parent().parent().find("input[type='checkbox']").prop("checked",true);
    //     if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
    //         $("#choose_all").prop('checked', true);
    //     } else {
    //         $("#choose_all").prop('checked', false);
    //     }
    // }
    spec.each(function (i,item) {
        var spec_price =$(this).attr("prd_price");
        var new_spec_price = parseFloat(spec_price - prd).toFixed(2);
        $(this).val(new_spec_price);
        if(new_spec_price < 0){
            $(this).parent().find('.price_red').text("降价后规格价不得小于0");
            return false;
        }
        // else if(new_spec_price > spec_price){
        //     $(this).parent().find('.price_red').text("折后规格价不得大于规格原价");
        //     return false;
        // }
    })
    if(new_price > origin_price){
        $(this).parent().parent().find(".price_red").text("降价后金额需要小于原价");
        $(this).focus();
    }else if(new_price < 0){
        $(this).parent().parent().find(".price_red").text("降价后金额不得小于0");
        $(this).focus();
    }else if(origin_price > 0 && origin_price < 0.01){
        $(this).parent().parent().find(".price_red").text("此商品不可降价");
        $(this).focus();
    }else{
        $(this).parent().parent().find(".price_red").text("");
    }

})

$(document).on("input propertychange blur",".price_zh",function () {
    if(edit !='0'){
        var prd = $(this).val();
        var origin_price = parseFloat($(this).parent().parent().find(".original_price").text()); //原价
        var jj_price = parseFloat(origin_price - prd).toFixed(2);
        var percent = parseFloat(prd / origin_price).toFixed(2);
        var new_percent = parseFloat(percent * 10).toFixed(2);
        var spec = $(this).parent().parent().find(".spec_value");
        spec.each(function (i,item) {
            var spec_price =$(this).attr("prd_price");
            var new_spec_price = prd;
            $(this).val(new_spec_price);
        })
        $(this).parent().parent().find(".price_jj").val(jj_price);
        $(this).parent().parent().find(".price_zk").val(new_percent);
        if(prd == ''){
            $(this).parent().parent().find(".price_jj").val('');
            $(this).parent().parent().find(".price_zk").val('');
            // $(this).parent().parent().find("input[type='checkbox']").prop("checked",false);
            // if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
            //     $("#choose_all").prop('checked', true);
            // } else {
            //     $("#choose_all").prop('checked', false);
            // }
        }
    }

    // else{
    //     $(this).parent().parent().find("input[type='checkbox']").prop("checked",true);
    //     if (($(".choose_single:checked").length == $(".choose_single").length) && $(".choose_single").length != 0 ) {
    //         $("#choose_all").prop('checked', true);
    //     } else {
    //         $("#choose_all").prop('checked', false);
    //     }
    // }
    spec.each(function (i,item) {
        var spec_price =$(this).attr("prd_price");
        var new_spec_price = prd;
        $(this).val(new_spec_price);
        if(new_spec_price < 0){
            $(this).parent().find('.price_red').text("降价后规格价不得小于0");
            return false;
        }else if(new_spec_price > spec_price){
            $(this).parent().find('.price_red').text("折后规格价不得大于规格原价");
            return false;
        }
    })
    if(prd > origin_price){
        $(this).parent().find(".price_red").text("降价金额需小于原价");
        $(this).focus();
    }else if(prd < 0){
        $(this).parent().find(".price_red").text("降价后金额不得小于0");
        $(this).focus();
    }else if(origin_price > 0 && origin_price < 0.01){
        $(this).parent().find(".price_red").text("此商品不可降价");
        $(this).focus();
    }else{
        $(this).parent().parent().find(".price_red").text("");
    }
})

$(document).on('blur','.price_zk,.price_jj,.price_zh',function () {
    if($(this).val().indexOf(".") != -1){
        var a = parseFloat($(this).val()).toFixed(2);
    }else{
        var a = $(this).val();
    }
    $(this).val(a);
})

$(".save").click(function () {
    var _this = $(this);
    var flag = 0;
    $('.price_zh').each(function () {
        // if($(this).parent().parent().find("input[type='checkbox']").is(":checked") == true) {
            var shop_flag = $("input[name='shop_flag']").val();
            if($(this).val()== '' && shop_flag!=2){
                flag ++;
                util.mobile_alert('请设置折扣价');
                $(this).focus();
                return false;
            }
            if($.trim($(this).parent().find('.price_red').text()).length > 0){
                flag ++;
                util.mobile_alert('折扣价设置有误');
                $(this).focus();
                return false;
            }
        // }
    })
    if(flag > 0){
        return false;
    }
    if($('input[name="name"]').val() == '') {
        util.mobile_alert('请填写活动名称');
        return false;
    }
    if($('input[name="start_time"]').val() == '' || $('input[name="end_time"]').val() == ''){
        util.mobile_alert('请填写有效期');
        return false;
    }
    if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
        util.mobile_alert('开始时间不能大于结束时间');
        return false;
    }
    if($('input[name="limit_amount"]').val() == '') {
        util.mobile_alert('请填写限购数量');
        return false;
    }
    if(!(/(^[1-9]\d*$)/.test($('input[name = "limit_amount"]').val())) && $('input[name = "limit_amount"]').val() != 0 && ! /^\d+$/.test($('input[name = "limit_amount"]').val())){
        util.mobile_alert('限购数量请输入正整数');
        return false;
    }
    if($('input[name="goods_id"]').val() == '') {
        util.mobile_alert('请选择商品');
        return false;
    }
    var goods = [];
    $(".reduce_tbody").find("tr").each(function (i,v) {
        var prd_list = [];
        $(".reduce_tbody").find("tr").eq(i).find(".spec_value").each(function (k,l) {
            var prd_detail = {
                'goods_id': $(l).attr("class"),
                'product_id':$(l).attr('prd_id'),
                'prd_price':$(l).val(),
            };
            prd_list.push(prd_detail);
        })
        var goods_detail = {
            'discount':$(".reduce_tbody").find("tr").eq(i).find(".price_zk").val(),
            'reduce_price':$(".reduce_tbody").find("tr").eq(i).find(".price_jj").val(),
            'goods_price':$(".reduce_tbody").find("tr").eq(i).find(".price_zh").val(),
            'goods_id':$(".reduce_tbody").find("tr").eq(i).attr("class"),
            'is_checked':$(".reduce_tbody").find("tr").eq(i).find('input[type="checkbox"]').is(":checked") == true ? 1 : 0,
            'prd_list':prd_list,
        };
        goods.push(goods_detail);
    });
    $("input[name='goods']").val(JSON.stringify(goods));


    if($("#zhouqi").prop("checked") != true){
        $('input[name="period_action"]').val("");
        $('input[name="point_time"]').val("");
        $('input[name="extend_time"]').val("");
    }
    if (parseInt($('[name="share_action"]:checked').val()) == 2) {
        if ($('[name="share_doc"]').val() == '') {
            util.mobile_alert('请选择文案');
            return false;
        }
        if (parseInt($('[name="share_img_action"]:checked').val()) == 2 &&
            $('[name="share_img"]').val() == '') {
            util.mobile_alert('请上传图片');
            return false;
        }
    }
    hasSaved = true;
    layer.ready(function () {
        layer.msg('保存成功', {time: 2000},function () {
            $("#form1").submit();
        });
    });
    // $('#form1').submit();
})


var hasSaved = true;
util.inputChange();
util.checkboxChange();

//编辑页初始化规格商品--提交校验数据合法性
// var goodsSpecArr = '{{ $goodsSpec }}'.replace(/&quot;/g, '"');
// goodsSpecArr = JSON.parse(goodsSpecArr);
// $.each(goodsSpecArr, function (index, item) {
//     secGroup.specProduct.prd_id.push(item.prd_id);
//     secGroup.specProduct.prd_price.push(item.prd_price);
//     secGroup.specProduct.prd_number.push(item.prd_number);
// });
// console.log(secGroup.specProduct);

