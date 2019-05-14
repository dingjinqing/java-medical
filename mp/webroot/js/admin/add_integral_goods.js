$(function () {
    //选择商品
    $('.choose_goods, .goods-btn-modify').on('click',function(){

        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center; padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px', '430px']
                , content: '/admin/frame/goods/select?is_check_single=1&integral_mall=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var goods = $('#choose-goods input[name="goods_id"]').val();
                    var body = layer.getChildFrame('body', index);
                    body.contents().find("tr").each(function(){
                        if($(this).attr("goods_id") == goods){
                           $(this).attr('data-back','false').addClass('goods_tr_choose');
                        }
                    });
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
                , yes: function (index, layero) {
                    //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    iframe.contents().find('tr[data-back="false"]').each(function(){
                        var goods_id = $(this).attr('goods_id');
                        var goods_name = $(this).find('.goods_name').text();
                        var el = $("#choose-goods");
                        el.find('input[name="goods_id"]').val(goods_id);
                        el.find('.goods-namess').text(goods_name);
                        getSpecProduct(goods_id, goods_name);
                        $('#sel-goods-btn').remove();
                        $('.goods-namess, .goods-btn-modify').show();
                        return true;
                    });
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    function getSpecProduct(goods_id, goods_name) {
        util.ajax_json('/admin/market/integral/convert/list', function (response) {
            if (response.error == 0) {
                var tr = '<tr class="set_line_value">' +$('#product-info tr:nth-of-type(1)').html()+ '</tr>';
                $('#product-info').html('');
                $.each(response.content, function (index, item) {
                    $('#product-info').append(tr);
                    var last_tr = $('#product-info tr:nth-last-child(1)');
                    last_tr.find('.product-info-template').removeClass('product-info-template');
                    last_tr.find('td').eq(0).text(item.prd_desc == '' ? goods_name : item.prd_desc);
                    last_tr.find('td').eq(1).text(item.prd_price);
                    last_tr.find('td').eq(3).text(item.prd_number);
                    last_tr.find('[name="product_id[]"]').val(item.prd_id);
                    last_tr.show();
                });
            } else {
                util.mobile_alert(response.message);
            }
        }, {act: "get_goods_spec", goods_id: goods_id});
    }
    //保存验证
    $(".pin_group_footer button").click(function () {
        if($('input[name="name"]').val() == ""){
            util.mobile_alert('请填写活动名称');
            return false;
        }
        if (!$('input[name="goods_id"]').val()) {
            util.mobile_alert('请选择商品');
            return false;
        }
        if($(".kaishi").val() == ""){
            util.mobile_alert('请填写有效期');
            return false;
        }
        if($(".jeishu").val() == ""){
            util.mobile_alert('请填写有效期');
            return false;
        }
        if ($(".kaishi").val() > $(".jeishu").val()) {
            util.mobile_alert('活动开始时间不能大于结束时间');
            return false;
        }
        if($(".most_nums").val() == ""){
            util.mobile_alert('请填写单个用户最多可兑换数量');
            return false;
        }
        var msg = '';
        $('#product-info input[name="score[]"]:not(".product-info-template")').each(function (index, item) {
            if($(item).val() == '' || parseFloat($(item).val()) < 0) {
                msg = '兑换所需积分不能为空';
                $(this).focus();
                return false;
            }
        });
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
        $(document).find('.goods_price_box input[name="money[]"]').each(function(index, item){
            console.log(parseFloat($(item).val()))
            console.log(($(item).parents('tr').find('.goods_price').text()))
            if(parseFloat($(item).val()) > ($(item).parents('tr').find('.goods_price').text())){
                msg = '兑换所需金额不能大于原价';
                $(this).focus();
                return false;
            }
        })
        $(document).find('#product-info input[name="stock[]"]:not(".product-info-template")').each(function (index, item) {
            if($(item).val() == '' || parseInt($(item).val()) < 1) {
                msg = '兑换商品库存不能为空';
                $(this).focus();
                return false;
            }
            if (parseInt($(item).val()) > parseInt($(this).parent().prev().text())) {
                msg = '兑换商品库存不能大于原库存';
                $(this).focus();
                return false;
            }
        });
        if (msg != '') {
            util.mobile_alert(msg);
            return false;
        }
        // 检查时间是否有重叠
        var start_time = $(".kaishi").val();
        var end_time = $(".jeishu").val();
        var goods_id = $('input[name="goods_id"]').val();
        util.ajax_json('/admin/market/integral/convert/list', function(response){
            if(response && response.error == 0){
                $('#form1').submit();
                hasSaved = true;
                return;
            }else{
                util.mobile_alert(response.message);
            }
        },{act:"isset_same_activity", goods_id: goods_id, start_time: start_time, end_time: end_time});
    })
})