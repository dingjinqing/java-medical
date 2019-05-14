$(function () {

    $('[name="presale_type"]').change(function () {
        changeGoodsTableTd();
        var presale_type = $(this).val();
        console.log(presale_type);
        if (presale_type == 1) {
            $('[name="pre_pay_step"]').val(1);
            $('.presale_type_0').hide();
            $('.presale_type_0 .pre_start_time').val('');
            $('.presale_type_0 .pre_end_time').val('');
            $('.some_money1').hide();
            $('[name="pre_start_time_2"]').val('');
            $('[name="pre_end_time_2"]').val('');
            $('[name="start_time"]').val('');
            $('[name="end_time"]').val('');
            $('.presale_type_1').show();
            $('.return_type_dev').hide();
            $('.deliver_type_span').text('');
        } else {
            $('.presale_type_0').show();
            $('.presale_type_1').hide();
            $('.presale_type_1 .pre_start_time').val('');
            $('.presale_type_1 .pre_end_time').val('');
            $('.return_type_dev').show();
            $('.add_tr').show();
            $('.deliver_type_span').text('尾款');
        }
        $('[name="pre_start_time"]').val('');
        $('[name="pre_end_time"]').val('');
    });

    //2阶段定金
    $(".add_tr").click(function () {
        $(".some_money1").css("display",'block');
        $('[name="pre_pay_step"]').val(2);
        $(this).hide();
        changeGoodsTableTd();
    });
    $(".delete_pay_times2").click(function () {
        $(".some_money1").css("display",'none');
        $('[name="pre_pay_step"]').val(1);
        $('.add_tr').show();
        changeGoodsTableTd();
    });


    $(".btns_common > a").click(function () {
        let _index = parseInt($(this).index()) + 3;
        if (isNaN(_index)) return false;
        var first_input = 0;
        $(".pre_product_table > tbody > tr").each2(function () {
            if ($(this).index() == 0) {
                first_input = $(this).find("td").eq(_index).find("input").val();
            } else {
                $(this).find("td").eq(_index).find("input").val(first_input).change();
            }

        })
    });

    //选择商品
    $('.choose_goods, .change_select').on('click',function(){

        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center; padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px', '430px']
                , content: '/admin/frame/goods/select?is_check_single=1&is_need_onsale=0&is_tips=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var goods = $('.presale_info input[name="goods_id"]').val();
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
                , yes: function (index, layero) {
                    //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    iframe.contents().find('tr[data-back="false"]').each(function(){
                        var goods_id = $(this).attr('goods_id');
                        var goods_name = $(this).find('.goods_name').html();
                        $(".presale_info input[name='goods_id']").val(goods_id);
                        $(".select_goods").html(goods_name).show();
                        initGoodsProduct(goods_id);
                        console.log(goods_id);
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

    goodsNumberChange();
    function initGoodsProduct(goodsId) {
        util.ajax_json('/admin/market/presale/product',function (res) {
            console.log(res);
            var table = $(".pre_product_table > tbody").html('');
            if (res.error == 0) {
                var products = res.content;
                // console.log(products)
                for (var i in products) {
                    let tr = $("<tr goods_id=" + products[i].goods_id + " prd_id=" + products[i].prd_id + ">" +
                        "<td width=\"10%\">"+products[i].prd_desc+"</td>\n" +
                        "<td width=\"10%\">"+products[i].prd_price+"</td>\n" +
                        "<td width=\"10%\">"+products[i].prd_number+"</td>\n" +
                        "<td width=\"10%\">\n" +
                        "    <input type=\"text\" class=\"act_price\" value="+products[i].prd_price+" onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">\n" +
                        "</td>\n" +
                        "<td width=\"10%\">\n" +
                        "   <input type=\"text\" class=\"act_number\" value="+products[i].prd_number+" onkeyup=\"value=value.replace(/[^\\d]/g,'')\">\n" +
                        "</td>\n" +
                        "<td width=\"10%\">\n" +
                        "    <input type=\"text\" class=\"pre_money\" onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">\n" +
                        "</td>\n" +
                        "<td width=\"15%\">\n" +
                        "    <input type=\"text\" class=\"onece_pre\" onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">\n" +
                        "</td>\n" +
                        "<td width=\"15%\">\n" +
                        "    <input type=\"text\" class=\"twice_pre\" onkeyup=\"value=value.replace(/[^\\d.]/g,'')\">\n" +
                        "</td>" +
                        "</tr>");
                    tr.appendTo(table);
                }

                $(".pi_content > a").show();
                $(".pre_product_div").show();
                $(".choose_goods").hide();
                changeGoodsTableTd();
                goodsNumberChange()
            } else {

            }

        },{goods_id:goodsId})

    }

    function changeGoodsTableTd(){
        var presale_type = $('[name="presale_type"]:checked').val();
        var pre_pay_step = $('[name="pre_pay_step"]').val();

        var table_tr = $(".pre_product_table tr");
        var btn_common = $(".btns_common > a");

        if (presale_type == 1) {
            table_tr.each(function () {
                $(this).find('td').eq(5).hide().find('input').val('');
                $(this).find('td').eq(6).hide().find('input').val('');
                $(this).find('td').eq(7).hide().find('input').val('');
                if (table_tr.length > 2) {
                    $(this).find('td').eq(0).show();
                } else {
                    $(this).find('td').eq(0).hide();
                }
            })
            btn_common.eq(2).hide();
            btn_common.eq(3).hide();
            btn_common.eq(4).hide();
        } else {
            table_tr.each(function () {
                if (pre_pay_step == 2) {
                    $(this).find('td').eq(5).show();
                    $(this).find('td').eq(6).show();
                    $(this).find('td').eq(7).show();
                } else {
                    $(this).find('td').eq(5).show();
                    $(this).find('td').eq(6).show();
                    $(this).find('td').eq(7).hide().find('input').val('');
                }
                if (table_tr.length > 2) {
                    $(this).find('td').eq(0).show();
                } else {
                    $(this).find('td').eq(0).hide();
                }
            });
            if (pre_pay_step == 2) {
                btn_common.eq(2).show();
                btn_common.eq(3).show();
                btn_common.eq(4).show();
            } else {
                btn_common.eq(2).show();
                btn_common.eq(3).show();
                btn_common.eq(4).hide();
            }
        }
    }

    function goodsNumberChange() {
        $(".act_number").change(function () {
            let goods_number = parseInt($(this).parent().parent().find('td').eq(2).text());
            if (parseInt($(this).val()) > goods_number) {
                util.mobile_alert('活动数量不能大于商品数量');
                $(this).val(goods_number);
            }
        });
    }

    function getGoodsProduct(preSaleType,prePayStep) {
        var table_tr = $(".pre_product_table > tbody > tr");
        var goods_product = [];
        var flag = true;
        table_tr.each(function () {
            let product = {
                goods_id: $(this).attr('goods_id'),
                product_id: $(this).attr('prd_id'),
                presale_price: parseFloat($(this).find('.act_price').val()),
                presale_number: $(this).find('.act_number').val(),
                presale_money: $(this).find('.pre_money').val() ? parseFloat($(this).find('.pre_money').val()) : 0,
                pre_discount_money_1: $(this).find('.onece_pre').val() ? parseFloat($(this).find('.onece_pre').val()) : 0,
                pre_discount_money_2: $(this).find('.twice_pre').val() ? parseFloat($(this).find('.twice_pre').val()) : 0
            };
            // console.log(product)
            if (preSaleType != 1) {
                if (product.presale_price < product.pre_discount_money_1 || product.pre_discount_money_1 < product.presale_money) {
                    util.mobile_alert("抵扣金额必须大于定金，小于活动价格");
                    $(this).find('.onece_pre').focus();
                    flag = false;
                    return false;
                }
                if (prePayStep == 2) {
                    if (product.presale_price < product.pre_discount_money_2 || product.pre_discount_money_2 < product.presale_money) {
                        util.mobile_alert("抵扣金额必须大于定金，小于活动价格");
                        $(this).find('.twice_pre').focus();
                        flag = false;
                        return false;
                    }
                }
            }
            goods_product.push(product)
        });
        if (!flag) return false;
        return goods_product;
    }

    // 保存
    $(".save_pre_sale").click(function () {
        if($(".act_names").val() == ""){
            util.mobile_alert("请填写活动名称");
            return false;
        }
        //定金的两个时间
        let presale_type = $("[name='presale_type']:checked").val();
        let pre_pay_step = $("input[name='pre_pay_step']").val()
        if (presale_type == 1) {
            let pre_start_time = $(".quankuan .pre_start_time").val();
            let pre_end_time = $(".quankuan .pre_end_time").val();
            if (!pre_start_time || !pre_end_time) {
                util.mobile_alert("请填写定金支付时间");
                return false;
            }
            $("input[name='pre_start_time']").val(pre_start_time);
            $("input[name='pre_end_time']").val(pre_end_time)
        } else {
            let pre_start_time = $(".some_money .pre_start_time").val();
            let pre_end_time = $(".some_money .pre_end_time").val();
            if (!pre_start_time || !pre_end_time) {
                util.mobile_alert("请填写定金支付时间");
                return false;
            }
            if (pre_pay_step == 2 && (
                !$("input[name='pre_start_time_2']").val() ||
                !$("input[name='pre_end_time_2']").val()
            )) {
                util.mobile_alert("请填写定金支付时间");
                return false;
            }

            if (!$("input[name='start_time']").val()
                || !$("input[name='end_time']").val()) {
                util.mobile_alert("请填写尾款支付时间");
                return false;
            }
            $("input[name='pre_start_time']").val(pre_start_time);
            $("input[name='pre_end_time']").val(pre_end_time)
        }

        if (!$("[name='goods_id']").val()) {
            util.mobile_alert("请选择活动商品");
            return false;
        }

        var goods_product = getGoodsProduct(presale_type,pre_pay_step);
        // console.log(goods_product)
        if (!goods_product) return false;

        for(var i in goods_product){
            //商品金额
            if(!goods_product[i].presale_price){
                util.mobile_alert("请填写商品活动价格");
                return false;
            }
            if(!goods_product[i].presale_number){
                util.mobile_alert("请填写商品活动库存");
                return false;
            }
        }
        $("[name='goods_products']").val(JSON.stringify(goods_product));

        //发货时间
        if($("[name='deliver_type']:checked").val() == 1){
            if($(".send_time2 input[type='text']").val() == ""){
                util.mobile_alert("请填写发货时间");
                return false;
            }
        }else{
            if($(".send_time1 input[type='text']").val() == ""){
                util.mobile_alert("请选择发货时间");
                return false;
            }
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
        util.ajax_json('/admin/market/presale/save',function (res) {
            console.log(res)
            if (res.error == 0) {
                $("[name='id']").val(res.content);
                util.mobile_alert('保存成功');
                window.location.href = '/admin/market/presale/list?nav=1';
            } else {
                util.mobile_alert(res.message);
            }
        },$("#form1").serialize())
    })
});
