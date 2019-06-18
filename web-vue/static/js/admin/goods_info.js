$(document).ready(function () {
    initKindEditor("#editor", function () {
        window.keditor.html($("#goods_desc").val());
    });
});
function picker(){
    return WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
}
function get_spec(){
    var spec_id = [];
    $('input[name="spec_name[]"]').each(function(i,e){
        if(i>1 && $(this).val()!='') {
            spec_id.push($(this).attr('spec_id'));
        }
    });
    var goods_spec = [];
    $('input[name="spec_val[]"][spec_id=0] ').each(function(i,e){
        if($(this).val()!=''){
            goods_spec.push($(this).attr('item_id'));
        }
    });
    var total_goods_spec =[];
    var spec = $('input[name="spec_name[]"][spec_id=0]').val();
    if (spec_id.length>0) {
        var total_i = 0;
        $.each(spec_id, function (i, e) {
            total_i = 0;
            $.each(goods_spec, function (i2, e2) {
                $('input[name="spec_val[]"][spec_id=' + parseInt(i+1) + '] ').each(function (i1, e1) {
                    if($(this).val()!='') {
                        total_goods_spec[total_i] = e2 + ":" + $(this).attr('item_id');
                        total_i++;
                    }
                });
            });
            goods_spec=[];
            $.each(total_goods_spec,function(id,el){
                goods_spec.push(el);
            });
            spec = spec+'  '+$('input[name="spec_name[]"][spec_id='+parseInt(i+1)+']').val();
        });
    } else {
        $.each(goods_spec,function(id,el){
            total_goods_spec.push(el);
        });
    }
    $('.spec_name_th').text(spec);
    $('.grade_price_td').text(spec);
    var spec_array=[];
    var spec_name = '';
    var prd_desc = '';
    var obj;
    var old_goods_spec = [];
    $(".spec-price tr:gt(0)").each(function(){
        if(!$(this).hasClass('last_spec')){
            old_goods_spec.push($(this).find('input').attr('spec_val_id'));
        }
    });
    var total_obj=[];
    var same=0;
    $.each(total_goods_spec,function(i,e){
        if(old_goods_spec && $.inArray(e,old_goods_spec)>-1){
            same++;
        }else{
            spec_name = '';
            prd_desc = '';
            if(isNaN(e)){
                spec_array = e.split(':');
                $.each(spec_array,function(id,el){
                    spec_name = spec_name+$('input[name="spec_val[]"][spec_id="'+id+'"][item_id="'+el+'"]').val()+'  ';
                    if(id==0){
                        prd_desc = $('input[name="spec_name[]"][spec_id="'+id+'"]').val()+":"+$('input[name="spec_val[]"][spec_id="'+id+'"][item_id="'+el+'"]').val();
                    } else {
                        prd_desc =  prd_desc +';' + $('input[name="spec_name[]"][spec_id="' + id + '"]').val() + ":" + $('input[name="spec_val[]"][spec_id="' + id + '"][item_id="'+el+'"]').val();
                    }
                });
            } else{
                spec_name = $('input[name="spec_val[]"][spec_id="0"][item_id="'+e+'"]').val();
                prd_desc = $('input[name="spec_name[]"][spec_id="0"]').val()+":"+spec_name;
            }
            // console.log(spec_name+' : '+prd_desc)
            obj = $('.spec_price_modal tbody').children().clone();
            obj.find('.spec_item').text(spec_name);
            obj.find('input').attr('spec_val_id',e);
            obj.find('input[name="prd_desc[]"]').attr('value',prd_desc);
            total_obj.push(obj);
        }
    });
    if(old_goods_spec){
        $('.spec-price tr').each(function(i,e){
            if(i>0 && (!$(this).hasClass('last_spec'))){
                var cur_spec = $(this).find("input[name='prd_price[]']").attr('spec_val_id');
                if ($.inArray(cur_spec,total_goods_spec) == -1) {
                    $(this).remove();
                } else {
                    var spec_name = '';
                    var prd_desc = '';
                    var spec_list = cur_spec.split(':');
                    $.each(spec_list,function(id,el){
                        spec_name = spec_name+$('input[name="spec_val[]"][spec_id="'+id+'"][item_id="'+el+'"]').val()+'  ';
                        if(id==0){
                            prd_desc = $('input[name="spec_name[]"][spec_id="'+id+'"]').val()+":"+$('input[name="spec_val[]"][spec_id="'+id+'"][item_id="'+el+'"]').val();
                        } else {
                            prd_desc =  prd_desc +';' + $('input[name="spec_name[]"][spec_id="' + id + '"]').val() + ":" + $('input[name="spec_val[]"][spec_id="' + id + '"][item_id="'+el+'"]').val();
                        }
                    });
                    $(this).find('.spec_item').text(spec_name);
                    $(this).find('input[name="prd_desc[]"]').attr('value',prd_desc);
                }
            }
        });
    }
    // return;
    $.each(total_obj,function(i,e){
        if($.inArray(e.attr('spec_val_id'),old_goods_spec) == -1){
            $('.spec-price .last_spec').before(e);
        }
    });
    grade_price();
    if($(".if_change_dis_price").is(":checked")){
        changeRebatePrice();
    }
    hasSaved = false;
};
$('.show-spec').on('change','input[name="spec_val[]"]',function(){
    get_spec();
    // is_has_prd = 1;
});
$('.basic-table').on('click','.add_spec_value',function(){
    var obj = $(".spec_val_modal").children().clone();
    var spec_id = $(this).prev().find('input').attr('spec_id');
    var item_id = $(this).prev().find('input').attr('item_id');
    obj.find('input').attr('spec_id',spec_id);
    obj.find('input').attr('item_id',parseInt(item_id)+1);
    $(this).before(obj);
});
$('.add_all').click(function () {
    $('.show-spec').show();
    $('.spec_price').show();
    $(this).hide();
    is_has_prd = is_has_prd+1;
    grade_price();
    $('.goods_prd_sn').hide();
});
$('.add_one').click(function () {
    var spec_id = $(this).attr('spec_id');
    var obj = $('.spec_modal tbody').children().clone();
    obj.find('input').attr('spec_id',spec_id);
    $('.last_tr').before(obj);
    spec_id = parseInt(spec_id)+1;
    $(this).attr('spec_id',spec_id);
    is_has_prd = is_has_prd+1;
    grade_price();
    //$('.spec-price').hide();
    // $('.spec-price tr').each(function(i,e){
    //     if(i>0 && (!$(this).hasClass('last_spec'))){
    //         $(this).remove();
    //     }
    // });
    //$('.add_spec_price').show();
});
function update_price(){
    var shop_price = -1;
    $('input[name="prd_price[]"]:gt(0)').each(function(i,e){
        if(shop_price == -1 || (shop_price != -1 && (parseFloat(shop_price)-parseFloat($(this).val())>0))){
            shop_price = $(this).val();
        }
    });
    $('input[name="shop_price"]').val(shop_price);
}
function update_number(){
    var goods_number = 0;
    $('input[name="prd_number[]"]:gt(0)').each(function(i,e){
        goods_number = goods_number+parseInt($(this).val());
    });
    $('input[name="goods_number"]').val(goods_number);
}
$('input[name="goods_sn"]').change(function(){
    var data={};
    data.goods_sn = $(this).val();
    var el = $(this);
    util.ajax_json('/admin/ajax/goods/check',function(d){
        if(d.content==1){
            util.mobile_alert('商品货号已存在');
            el.focus();
            el.attr('flag',1);
        }
        else{
            el.attr('flag',0);
        }
    },data);
});
$("input[name='goods_weight'],input[name='shop_price'],input[name='market_price']").change(function(){
    if(isNaN($(this).val())){
        util.mobile_alert('请填写正确的数字格式');
        $(this).focus();
        $(this).attr('flag',1);
    }
    else{
        $(this).attr('flag',0);
    }
});
$('.btn-save').click(function(event){
    event.stopPropagation();
    var flag =0;
    $('input[name="sale_time"]').val($('.choose-time').text());
    if($('#level2').val()>0){
        $("input[name='cat_id']").val($('#level2').val());
    }else if($('#level1').val()>0) {
        $("input[name='cat_id']").val($('#level1').val());
    }else if($('#level0').val()>0) {
        $("input[name='cat_id']").val($('#level0').val());
    }
    //判空验证
    if($("input[name='goods_name']").val() == ''){
        util.mobile_alert('商品名称不能为空');
        return false;
    }else{
        $(".item_name>span").text($("input[name='goods_name']").val());
    }
    if($("input[name='goods_number']").val() == ''){
        util.mobile_alert('商品库存不能为空');
        return false;
    }else{
        $(".kucun>span").text($("input[name='goods_number']").val());
    }
    var limit_buy_num = parseInt($("input[name='limit_buy_num']").val());
    var limit_max_num = parseInt($("input[name='limit_max_num']").val());
    if($("input[name='limit_buy_num']").val() != '' && (limit_buy_num < 0 || limit_buy_num != $("input[name='limit_buy_num']").val())) {
        util.mobile_alert("最小限购数量不合法");
        return false;
    }
    if($("input[name='limit_buy_num']").val() != '' && limit_max_num < 0) {
        util.mobile_alert("最大限购数量不合法");
        return false;
    }
    if(limit_buy_num > 0 && limit_max_num > 0 && limit_buy_num >= limit_max_num){
        util.mobile_alert("最大限购数量不能小于最小限购数量");
        return false;
    }
    if($("input[name='shop_price']").val()== ''){
        util.mobile_alert('商品售价不能为空');
        return false;
    }else{
        $(".item_price>span").text($("input[name='shop_price']").val());
    }
    if($("input[name='cat_id']").val() == ''){
        util.mobile_alert('平台分类不能为空');
        return false;
    }
    if($("select[name='unit']").val() == '自定义'){
        if($("input[name='unit_temp']").val() == ''){
            util.mobile_alert('自定义单位不能为空');
            return false;
        }
    }
    if($(".shelves li #custom_sale").is(":checked")){
        if($("#custom_sale").parent().find(".choose-time ").html() == "" || $("#custom_sale").parent().find(".choose-time ").html()== "0000-00-00 00:00:00" || $("#custom_sale").parent().find(".choose-time ").html()=="选择上架售卖时间"){
            util.mobile_alert('请选择上架时间');
            return false;
        }
    }

    if($('input[name="goods_img[]"]:eq(0)').val() == ''){
        util.mobile_alert('商品图片不能为空');
        return false;
    }else{
        $(".item_img>img").attr('src',$('input[name="goods_img[]"]:eq(0)').val());
    }
    $('input').each(function(){
        if($(this).attr('flag') == 1){
            flag++;
            util.mobile_alert('输入不合法');
            $(this).focus();
            return false;
        }
    });
    if($("input[name='is_card_exclusive']").is(":checked") && $('.card-info-row').find('.card_span').length <=0 ){
        util.mobile_alert('请选择专享会员卡');
        return false;
    }
    if(flag>0){
        return false;
    }

    var goods_has_spec = false;
    if($('input[name="spec_name[]"]').length>1 && $('input[name="spec_name[]"]:eq(1)').val() != '') {
        goods_has_spec = true;
        //整理规格数据
        var spec_info = {};
        var spec_group = {};
        var spec_total = [];
        var spec_id = 0;
        $('input[name="spec_name[]"]:gt(0)').each(function () {
            spec_group = {};
            spec_id = $(this).attr('spec_id');
            spec_group['spec_name'] = $(this).val();
            var spec_values = {};
            $('input[name="spec_val[]"][spec_id="' + spec_id + '"]').each(function () {
                // spec_group['spec_values'].push($(this).val());
                spec_values[$(this).attr('item_id')] = $(this).val();
            });
            spec_group['spec_values'] = JSON.stringify(spec_values);
            spec_total.push(spec_group);
        });
        var prd_info = {};
        var prd_total = [];
        var obj;
        $('input[name="prd_price[]"]:gt(0)').each(function () {
            prd_info = {};
            obj = $(this).parent().parent();
            prd_info['spec_val_id'] = $(this).attr("spec_val_id");
            prd_info['prd_sn'] = obj.find('input[name="prd_sn[]"]').val();
            prd_info['prd_img'] = obj.find('input[name="prd_img[]"]').val();
            prd_info['prd_desc'] = obj.find('input[name="prd_desc[]"]').val();
            prd_info['prd_number'] = obj.find('input[name="prd_number[]"]').val();
            prd_info['prd_cost_price'] = obj.find('input[name="prd_cost_price[]"]').val();
            prd_info['prd_price'] = $(this).val();
            if($(".grade_card").find("input[type='checkbox']:checked").length>0){
                //存等级卡的会员价
                var grade_card_price = [];
                var cards = [];
                $(".card_goods_price_tb .prd_list_grade").each(function (i,v) {
                    if($(v).find(".grade_name_td").text()==obj.find('.spec_item').text()){
                        $(v).find(".grade_prd").each(function (j,k) {
                            if($(k).css('display') != "none"){
                                grade_card_price .push($(k).find("input").val());
                                cards.push($(k).attr("grade"));
                            }
                        })
                    }
                });
                var card_price = {
                    grade_card_price:grade_card_price,
                    cards:cards
                };
            }else{
                var card_price = {
                    grade_card_price:[],
                    cards:[]
                };
            }
            prd_info['grade_card_price'] = JSON.stringify(card_price);
            prd_total.push(prd_info);
        });
        spec_info['spec'] = spec_total;
        spec_info['prd'] = prd_total;
        $("input[name='spec_info']").val(JSON.stringify(spec_info));
    }else{
        if($(".grade_card").find("input[type='checkbox']:checked").length>0){
            //存等级卡的会员价
            var grade_card_price = [];
            var cards = [];
            $(".card_goods_price_tb .prd_list_grade").each(function (i,v) {
                $(v).find(".grade_prd").each(function (j,k) {
                    if($(k).css('display') != "none"){
                        grade_card_price .push($(k).find("input").val());
                        cards.push($(k).attr("grade"));
                    }
                })
            });
            var card_price = {
                grade_card_price:grade_card_price,
                cards:cards
            };
            $("input[name='no_spec_grade']").val(JSON.stringify(card_price));
        }
    }
    window.keditor.sync();
    if($('.item_goods').next().attr('id') == 'drag_area'){
       $('#is_page_up').val(1);
    }else{
       $('#is_page_up').val(0);
       console.log(0);
    }
    $("#goods_desc").val($('#editor').val());
    var is_grade_val = 0;
    $('.grade_card').find('input[type="checkbox"]:checked').each(function () {
        var _this = $(this);
        //console.log(_this.val());
        $('.card_goods_price_tb').find('.rank_ipt_' + _this.val() + '').find('input').each(function(){
           if($(this).val() == ''){
               is_grade_val += 1;
               util.mobile_alert('请输入等级会员卡价格');
               $(this).focus();
               return false;
           }else if(parseFloat($(this).val()) > parseFloat($(this).parent().parent().find(".grade_goods_price").text())) {
               is_grade_val += 1;
               util.mobile_alert('等级会员卡价格不能大于原商品价格');
               $(this).focus();
               return false;
           }
        });
        if(is_grade_val > 0){
            return false;
        }
    });
    // console.log(is_grade_val);
    if(is_grade_val > 0){
        return;
    }

    if ($('[name="can_rebate"]').is(':checked')) {
        let rebate_price = rebatePriceList();
        let rebate_list = [];
        let re_cost_price = {};
        if (goods_has_spec) {
            for (var prd_total_id in prd_total) {
                re_cost_price[prd_total[prd_total_id].spec_val_id] = parseFloat(prd_total[prd_total_id].prd_cost_price || 0);
            }
        } else {
            re_cost_price[0] = parseFloat($("input[name='cost_price']").val() || 0)
        }
        for (var rebate_id in rebate_price) {
            let rebate_flag = true;
            let td_index = 1;
            if (rebate_price[rebate_id].min_price > rebate_price[rebate_id].advise_price
                || rebate_price[rebate_id].advise_price > rebate_price[rebate_id].max_price){
                util.mobile_alert('分销改价建议售价应大于最低售价且小于最高售价');
                rebate_flag = false;
            } else {
                if (goods_has_spec) {
                    if (rebate_price[rebate_id].min_price < re_cost_price[rebate_id]){
                        util.mobile_alert('分销改价最低售价应大于成本价');
                        td_index = 2;
                        rebate_flag = false;
                    }
                } else {
                    if (rebate_price[rebate_id].min_price < re_cost_price[0]){
                        util.mobile_alert('分销改价最低售价应大于成本价');
                        td_index = 2;
                        rebate_flag = false;
                    }
                }
            }
            if (!rebate_flag) {
                $('.dis_price table tr').eq(rebate_price[rebate_id].index).find('td').eq(td_index).find('input').focus()
                return false;
            }
            rebate_list.push(rebate_price[rebate_id])
        }
        $('input[name="rebate_price"]').val(JSON.stringify(rebate_list))
    }

    hasSaved = true;
    var data = {
        config_name:'num_config',
        mod_name:'goods_num'
    }
    util.ajax_json("/admin/version/judgment",function (d) {
        var self = d.content.self;
        var goods_prd_sn = $('input[name="goods_prd_sn"]').val();
        var toSubmit = function () {
            checkGoodsSn(function () {
                if(self.num >= self.use || self.num<0){
                    //重量运费模板时 判断商品重量
                    if($("select[name='deliver_template_id']").val()){
                        util.ajax_json("/admin/frame/deliver/fee/template/judge",function (d1) {
                            if(d1.error == 1 && !$('input[name="goods_weight"]').val()){
                                util.mobile_alert('请填写商品重量');
                                $('input[name="goods_weight"]').focus();
                                return false;
                            }else{
                                if (is_submit) return false;
                                is_submit = true;
                                $('#form1').submit();
                                util.mobile_alert('保存成功');
                            }
                        },{id:$("select[name='deliver_template_id']").val()})
                    }else{
                        if (is_submit) return false;
                        is_submit = true;
                        $('#form1').submit();
                        util.mobile_alert('保存成功');
                    }
                }else {
                    util.systemNotice(2,'商品数量已达到'+self.num+'个','商品数量');
                }
            });
        }
        if (is_has_prd == 0 && goods_prd_sn != '') {
            util.ajax_json('/admin/ajax/goods/check/prdsn', function (response) {
                if (response.error) {
                    util.mobile_alert("商品规格编码已存在，请检查");
                    $('input[name="goods_prd_sn"]').focus();
                    return false;
                }
                toSubmit();
            }, {prd_sn:goods_prd_sn, goods_id:goods_id});
        } else {
            toSubmit();
        }
    },data);
});

$('.goods-item-img').on('click','.good_img_delete',function(){
    $(this).parent().remove();
    hasSaved = false;
});
$('.add_img').click(function() {
    var _this = this;
    var w = 800;
    var h = 800;
    // $.jImageManager({
    //     img_width: w,
    //     img_height: h,
    //     ok_cb: function (img_arr) {
    //         var path = img_arr[0].img_url;
    //         if (path == undefined) {
    //             path = img_arr[0].url;
    //         }
    //         el.find("img").eq(0).attr("src", path);
    //         el.find("input").attr("value", path);
    //         hasSaved = false;
    //         el.removeClass('add_class');
    //         el.find("img").eq(1).show();
    //         obj.before(el);
    //     }
    // });
    $.jImageManager({
        max_img_num: 0,
        img_width: w,
        img_height: h,
        ok_cb: function (img_arr) {
            img_arr.forEach(element => {
                if($(".goods-item-img li").length >=6 ){
                    util.mobile_alert('最多上传5张图！');
                    return false;
                }
                var el = $(_this).parent().clone();
                var obj = $(_this).parent();
                var path = element.img_url;
                if (path == undefined) {
                    path = element.url;
                }
                el.find("img").eq(0).attr("src", path);
                el.find("input").attr("value", path);
                hasSaved = false;
                el.removeClass('add_class');
                el.find("img").eq(1).show();
                obj.before(el);
            });
        }
    });
});

$(document).on('click','.master_diagram_left',function () {
    var pre_src = $(this).parents('li').prev().find("img").eq(0).attr('src');
    var cur_src = $(this).prev().prev().attr('src');
    if(pre_src) {
        $(this).parents('li').prev().find("img").eq(0).attr('src', cur_src);
        $(this).parents('li').prev().find("input").eq(0).val(cur_src);
        $(this).prev().prev().attr('src', pre_src);
        $(this).prev().prev().prev().val(pre_src);
    }
})

$(document).on('click','.master_diagram_right',function () {
    var add_img_src = $('.goods-item-img li:last').find('.add_img').attr('src');
    var next_src = $(this).parents('li').next().find("img").eq(0).attr('src');
    var cur_src = $(this).prev().prev().prev().attr('src');
    if(next_src && next_src != add_img_src) {
        $(this).parents('li').next().find('img').eq(0).attr('src', cur_src);
        $(this).parents('li').next().find("input").eq(0).val(cur_src);
        $(this).prev().prev().prev().attr('src', next_src);
        $(this).prev().prev().prev().prev().val(next_src);
    }
})

if($('.add-goods-video').attr('src').indexOf('add_video') > -1){
    $(".good_img_deletes").css("display","none");
}else{
    $(".good_img_deletes").css("display","block");
}

$(document).on("click",".good_img_deletes",function () {
    var paths = $(".add_video_image").val();
    var obj = $(this).parent();
    obj.find('.add-goods-video').attr("src",paths);
    obj.find('.add-goods-video').css("width","auto");
    obj.find('.add-goods-video').css("height","auto");
    $(this).css("display","none");
    obj.find("input[name='goods_video']").val('');
    obj.find("input[name='goods_video_img']").val('');
    obj.find("input[name='goods_video_size']").val('');
    obj.find("input[name='goods_video_id']").val('');
	$(".btn_playa").css("display","none");
})

$('.add-goods-video').click(function() {
    var el = $(this);
    var obj = $(this).parent();

    $.jVideoManager({
        ok_cb: function (video) {
            video = video[0];
            el.attr("src", video.snapshot_url);
            obj.find("input[name='goods_video']").val(video.url);
            obj.find("input[name='goods_video_img']").val(video.snapshot_url);
            obj.find("input[name='goods_video_size']").val(video.video_size);
            obj.find("input[name='goods_video_id']").val(video.video_id);
            obj.find(".btn_playa").attr('href',video.url);
            obj.find(".btn_playa").css("display","block");
            obj.find(".add-goods-video").css("width","100%");
            obj.find(".add-goods-video").css("height","100%");
            obj.find(".add-goods-video").css("margin-top","-3px");
            obj.find(".good_img_deletes").css("display","block");
            hasSaved = false;
        }
    });
});

function prdChange(data) {
    var val = $(data).val(); //获得当前输入框的值
    var values = [];
    $("input[name='prd_sn[]']").each(function(index, item){
        if ($(this).val() != '') {
            values.push($(this).val());
        }
    });
    var index = $.inArray($(data).val(), values);
    if (index > -1) values.splice(index, 1);
    if ($.inArray($(data).val(), values) > -1) {
        util.mobile_alert("规格编码不能重复，请重新输入!");
        $(data).val("");
        return false;
    }

    util.ajax_json('/admin/ajax/goods/check/prdsn', function (response) {
        if (response.error) {
            util.mobile_alert("规格编码不能重复，请重新输入!");
            $(data).val("");
            return false;
        }
    }, {prd_sn:val, goods_id:goods_id});
}

$('.shelves_li input').click(function(){
    if($(this).is(":checked")){
        $('.shelves_li').find('img').attr('src','/image/admin/check_no.png');
        $(this).prev().find('img').attr('src','/image/admin/check_yes.png');
    }
});
$('.box-top-first,.btn-prev').click(function(){
    $('.box-top-detail').removeClass('box-top-detail2');
    $('.box-top-first').removeClass('box-top-first2');
    $('.goods-edit-detail').hide();
    $('.goods-edit-basic').show();
    $('.btn-next').show();
    $('.btn-prev').hide();
});

$(".btn-next,.box-top-detail").click(function() {
    var flag =0;
    if($('#level2').val()>0){
        $("input[name='cat_id']").val($('#level2').val());
    }else if($('#level1').val()>0) {
        $("input[name='cat_id']").val($('#level1').val());
    }else if($('#level0').val()>0) {
        $("input[name='cat_id']").val($('#level0').val());
    }
    //判空验证
    if($("input[name='goods_name']").val() == ''){
        util.mobile_alert('商品名称不能为空');
        return false;
    }else{
        $(".item_name>span").text($("input[name='goods_name']").val());
    }
    if($("input[name='goods_number']").val() == ''){
        util.mobile_alert('商品库存不能为空');
        return false;
    }else{
        $(".kucun>span").text($("input[name='goods_number']").val());
    }
    if($("input[name='shop_price']").val()== ''){
        util.mobile_alert('商品售价不能为空');
        return false;
    }else{
        $(".item_price>span").text($("input[name='shop_price']").val());
    }
    // console.log($("input[name='cat_id']").val());
    if($("input[name='cat_id']").val() == ''){
        util.mobile_alert('商品分类不能为空');
        return false;
    }
    if($('input[name="goods_img[]"]:eq(0)').val() == ''){
        util.mobile_alert('商品图片不能为空');
        return false;
    }else{
        $(".item_img>img").attr('src',$('input[name="goods_img[]"]:eq(0)').val());
    }
    $('input').each(function(){
        if($(this).attr('flag') == 1){
            flag++;
            util.mobile_alert('输入不合法');
            $(this).focus();
            return false;
        }
    });
    if(flag>0){
        return false;
    }
    $('.box-top-first').addClass('box-top-first2');
    $('.box-top-detail').addClass('box-top-detail2');
    $('.goods-edit-detail').show();
    $('.goods-edit-basic').hide();
    $('.btn-prev').show();
    $('.btn-next').hide();
});
$('.spec-price').on('click','.add_spec_img',function(){
    var el = $(this);
    var w = 800;
    var h = 800;
    $.jImageManager({
        img_width: w,
        img_height: h,
        ok_cb: function(img_arr){
            var path = img_arr[0].img_url;
            if(path == undefined){
                path = img_arr[0].url;
            }
            el.attr("src", path);
            el.parent().find("input[name='prd_img[]']").attr("value", path);
            el.next().show();
        }
    });
});
$('.spec-price').on('click','.prd_img_delete',function(){
    $(this).prev().attr('src','/image/admin/add_img_gg.png');
    $(this).parent().find("input[name='prd_img[]']").attr("value", '');
    $(this).hide();
});
$('.same_img').click(function(){
    var src = $("input[name='prd_img[]']").eq(1).val();
    $("input[name='prd_img[]']").each(function(){
        $(this).attr('value',src);
    });
    $(".add_spec_img").each(function(){
        $(this).attr('src',src);
        $(this).next().show();
    })
});
$('.same_number').click(function(){
    var num = $("input[name='prd_number[]']").eq(1).val();
    $("input[name='prd_number[]']").each(function(){
        $(this).val(num);
    });
    update_number();
});
$('.same_price').click(function(){
    var price = $("input[name='prd_price[]']").eq(1).val();
    $("input[name='prd_price[]']").each(function(){
        $(this).val(price);
    });
    update_price();
});
$('[name="deliver_template_id"]').change(function () {
    // alert( $(this).val())
    $(".deliver-template-info").addClass("hide");
    $(".deliver-template-info.template-" + $(this).val()).removeClass("hide");
    $(".deliver-template-info.template-article").removeClass("hide");
});
$('.show-spec').on('click','.spec_span img',function(){
    if($(this).prev().attr('name') == 'spec_name[]'){
        $(this).closest('tr').next().remove();
        $(this).closest('tr').remove();
        var this_name = $(this).parent().parent().parent().parent().find('[name="spec_name[]"]').length;
        if(this_name == 0){
            is_has_prd = 0;
        }
    }
    else{
        $(this).parent().remove();
        is_has_prd = is_has_prd-1;
    }
    //更新spec_id
    var spec_id = 0;
    var next_spec_id=1;
    $("input[name='spec_name[]']:gt(0)").each(function(i,e){
        spec_id = $(this).attr('spec_id');
        $("input[name='spec_val[]'][spec_id="+spec_id+"]").each(function(i1,e1){
            $(this).attr('spec_id',i);
        });
        $(this).attr('spec_id',i);
        next_spec_id = i+1;
    });
    $(".add_one").attr('spec_id',next_spec_id);
    get_spec();
    if($('input[name="spec_name[]"]').length ==1){
        $(".show-spec").hide();
        var obj = $('.spec_modal tbody').children().clone();
        obj.find('input').attr('spec_id',spec_id);
        $('.last_tr').before(obj);
        $(".spec_price").hide();
        $(".add-spec").show();
        $(".spec_name_th").text('');
        $('.grade_price_td').hide();
        $('.grade_name_td').hide();
        $('.grade_prd').find('input').val($('input[name="shop_price"]').val());
        $('.grade_goods_price').html($('input[name="shop_price"]').val());
        $('.goods_prd_sn').show();
        return false;
    }
});
function select_cat(level,parent_id) {
    get_cat_lc(level);
    if(level < 2){
        var url = "/admin/ajax/category/level/list";
        var param = {};
        param.level = parseInt(level+1);
        if (parent_id == null || parent_id == '') {
            param.parent_id = $('#level' + level).val();
        }else{
            param.parent_id = parent_id;
        }
        if(level==0){
            $('#level1').html( '');
            $('#level1').hide();
            $('#level2').html( '');
            $('#level2').hide();
        }
        var op = '<option value="0">选择分类</option>';

        util.ajax_json(url, function (d) {
            if (d && d.error == 0) {
                var next_level = parseInt(level+1).toString();
                var html = '';
                var cat_id = 0;
                for (var i in d.content){
                    var t = d.content[i];
                    cat_id = d.content[0].cat_id;
                    html += '<option value="'+t.cat_id+'"';
                    if(i==0) {
                        // html += ' selected="selected" ';
                    }
                    html += '>'+ t.cat_name +'</option>';
                }
                setTimeout(function () {
                    if(html) {
                        $('#level' + next_level).html(op + html);
                        $('#level' + next_level).show();
                    }else{
                        $('#level' + next_level).html( html);
                        $('#level' + next_level).hide();
                    }
                    if(param.level<=2 && cat_id>0) {
                        //select_cat(param.level,cat_id);
                    }
                }, 500);
                return true;
            } else if (d && d.error < 0) {
                util.mobile_alert('获取数据失败');
                return false;
            }
        }, param);
    }
}

function get_cat_lc(level) {
    var url = "/admin/ajax/category/label";
    var param = [];
    var level0 = $('select[name="level0"]').val();
    var level1 = $('select[name="level1"]').val();
    var level2 = $('select[name="level2"]').val();
    param[0] = level0;
    if(level>0){
        param.push(level1);
    }
    if(level>1){
        param.push(level2);
    }
    console.log(param);
    var kk = 1;
    util.ajax_json(url, function (d) {
        if (d && d.error == 0) {
            var inner_html = '';
            var inner_html1 = '';
            var labels = d.content.labels;
            var cards = d.content.cards;
            for (var i in labels){
                var span =' <span class="label_span">';
                inner_html += span + '<span>'+ labels[i].name + '</span>' + '</span>';
            }
            for (var j in cards){
                var span1 =' <span class="card_span">';
                inner_html1 += span + '<span>'+ cards[j].card_name + '</span>' + '</span>';
            }
            if(inner_html !=''){
                $('.label-info-cat').html('<span class="label-cat">所属种类标签：</span>'+inner_html);
                $('.label-info1').show();
            }else{
                $('.label-info1').hide();
            }
            if(inner_html1 !=''){
                $('.card-info-cat').html('<span class="card-cat">所属种类会员卡：</span>'+inner_html1);
                $('.card-info1').show();
            }else{
                $('.card-info1').hide();
            }
            return true;
        } else if (d && d.error < 0) {
            util.mobile_alert('获取数据失败');
            return false;
        }
    }, {cat:param});
}

function select_sort() {
    var url = "/admin/ajax/sort/label";
    var sort_id = $('select[name="sort_id"]').val();
    util.ajax_json(url, function (d) {
        if (d && d.error == 0) {
            var inner_html = '';
            var inner_html1 = '';
            var labels = d.content.labels;
            var cards = d.content.cards;
            for (var i in labels){
                var span =' <span class="label_span">';
                inner_html += span + '<span>'+ labels[i].name + '</span>' + '</span>';
            }
            for (var j in cards){
                var span1 =' <span class="card_span">';
                inner_html1 += span1 + '<span>'+ cards[j].card_name + '</span>' + '</span>';
            }
            if(inner_html !=''){
                $('.label-info-sort').html('<span class="label-sort">所属种类标签：</span>'+inner_html);
                $('.label-info2').show();
            }else{
                $('.label-info2').hide();
            }
            if(inner_html1 !=''){
                $('.card-info-sort').html('<span class="card-sort">所属种类会员卡：</span>'+inner_html1);
                $('.card-info2').show();
            }else{
                $('.card-info2').hide();
            }
            return true;
        } else if (d && d.error < 0) {
            util.mobile_alert('获取数据失败');
            return false;
        }
    }, {sort_id: sort_id});
}

//添加会员价
//没规格
if($('.hid_grade').val()){
    var hid_grade_arr = JSON.parse($('.hid_grade').val());
}else{
    var hid_grade_arr = {};
}
if(is_has_prd == 0 && $('.hid_grade').val()) {
    for (var i in hid_grade_arr) {
        $('.rank_name_' + hid_grade_arr[i].grade + '').hide();
    }
}
$('.grade_card').find('input[type="checkbox"]').each(function () {
    //console.log($(this).val());
    $('.rank_name_' + $(this).val() + '').hide();
});
$('.grade_card').find('input[type="checkbox"]:checked').each(function () {
    //console.log($(this).val());
    $('.rank_name_' + $(this).val() + '').show();
    if(is_has_prd > 1){
        $('.same_'+ $(this).val() +'').show();
        $('.same_td_first').show();
    }
});

//会员价格
var grade_price_arr = [];
function grade_price_func(){
    grade_price_arr = [];
    if($('.card_goods_price_tb').find('.prd_list_grade').length >= 1){
        $('.card_goods_price_tb').find('.prd_list_grade').each(function(i,v){
            grade_price_arr[i] = [];
            $(v).find('.grade_prd').each(function () {
                var grade_obj = {};
                grade_obj.grade = $(this).attr('grade');
                grade_obj.price = $(this).find('input').val();
                grade_price_arr[i].push(grade_obj);
            });
        });
        // if(grade_price_arr[0].length < $('.grade_card').find('input[type="checkbox"]').length){
        //     $('.grade_card').find('input[type="checkbox"]').each(function(){
        //        if($(this).val())
        //     });
        // }
        console.log(grade_price_arr);
    }
}
grade_price_func();

var grade_card = [];
$('.grade_card').find('input[type="checkbox"]').click(function () {
    var _this = $(this);
    grade_card = [];
    $(".grade_card").find("input").each(function (i,v) {
        if($(v).prop("checked")==true && $(v).val() !=_this.val()){
            grade_card.push($(v).val());
        }
    });
    var shop_price = $('input[name="shop_price"]').val();
    if(shop_price == ''){
        util.mobile_alert('请填写商品价格');
        $(this).prop('checked',false);
        $('input[name="shop_price"]').focus();
        return;
    }
    if($(this).is(':checked')){
        $('.rank_name_'+ $(this).val() +'').show();
        $('.rank_ipt_'+ $(this).val() +'').show();
        if(is_has_prd > 1){
            $('.same_'+ $(this).val() +'').show();
            $('.same_td_first').show();
        }
    }else{
        $('.rank_name_'+ $(this).val() +'').hide();
        $('.rank_ipt_'+ $(this).val() +'').hide();
        $('.same_'+ $(this).val() +'').hide();
        $('.rank_ipt_'+ $(this).val() +'').each(function () {
            $(this).find('input').val($(this).parent().find('.grade_goods_price').text());
        });
    }
    grade_price_func();
    var obj_clone = $('.grade_price_clone tbody').children().clone();
    if(is_has_prd == 0){
        $('.card_goods_price_tb').find('.prd_list_grade').remove();
        $('.last_grade_tr').before(obj_clone);
        $('.grade_price_td').hide();
        $('.grade_name_td').hide();
        $('.rank_ipt_'+ $(this).val() +'').find('input').val(shop_price);
    }else{
        grade_price();
        $('.grade_price_td').show();
        $('.grade_name_td').show();
    }

    if(is_has_prd==0){
        $('.grade_goods_price').html(shop_price);
        $('.grade_prd').find('input').val(shop_price);
    }
    if($('.card_goods_price_tb').find('.prd_list_grade').length >= 1){
            var grade_obj_leng = $('.card_goods_price_tb').find('.prd_list_grade').length;
            for(var j=0;j<grade_obj_leng;j++){
                var grade_obj = $('.card_goods_price_tb').find('.prd_list_grade').eq(j).find(".grade_prd")
                $.each(grade_obj,function(key,value){
                    for(var i in grade_price_arr[j]) {
                        if(in_array($(value).attr("grade"),grade_card)){
                            if($(value).attr("grade") == grade_price_arr[j][i].grade){
                                $(value).find('input').val(grade_price_arr[j][i].price);
                            }
                        }else{
                            break;
                        }
                        // if (grade_price_arr[i][key]) {
                        //     if ($(value).attr('grade') == grade_price_arr[i][key].grade) {
                        //         $(value).find('input').val(grade_price_arr[i][key].price);
                        //     }
                        // }
                    }
                });
            }
            //console.log(grade_obj);
            //for(var k in grade_obj){
            for(var j in grade_price_arr[i]){

            }
    }

    $('.tr_grade').show();

    if($('.grade_card').find('input[type="checkbox"]:checked').length == 0){
        $('.tr_grade').hide();
    }
    if($('.grade_card').find('input[type="checkbox"]:checked').length <= 1){
        $(".same_tr_price").hide();
        $(".card_goods_price_tb").find(".same").hide();
    }else{
        $(".same_tr_price").show();
        $(".card_goods_price_tb").find(".same").show()
    }
});
function grade_price() {
    var grade = [];
    var tr = $(".spec-price").find('tr');
    if($(".spec-price").find('tr').length>1){
        //修改规格之前的会员价
        var grade_old = $(".card_goods_price_tb tbody").children();
        $('.card_goods_price_tb').find('.prd_list_grade').remove();
        is_has_prd = $(".spec-price").find('tr').length-2;
        if(is_has_prd>0){
            $('.card_goods_price').text('规格价格(元)');
            $('.grade_price_td').show();
            $('.grade_name_td').show();
        }else{
            $('.card_goods_price').text('商品价格(元)');
            $('.grade_price_td').hide();
            $('.grade_name_td').hide();
            var obj_clone = $('.grade_price_clone tbody').children().clone();
            $('.card_goods_price_tb').find('.prd_list_grade').remove();
            $('.last_grade_tr').before(obj_clone);
        }
        for(var i=1;i<tr.length-1;i++){
            var obj = $(".grade_price_clone tbody").children().clone();
            obj.find('.grade_name_td').text($('.spec-price').find('tr').eq(i).find('td').eq(0).text());
            if($('.spec-price').find('tr').eq(i).find("input[name='prd_price[]']").val() != '') {
                obj.find('.grade_goods_price').text($('.spec-price').find('tr').eq(i).find("input[name='prd_price[]']").val());
                if(grade_old && grade_old.length>1){
                    for(var j=0;j<obj.find('.grade_prd').length;j++){
                        if(grade_old.eq(i).find('.grade_prd').length > 0){
                            for(var k=0;k<grade_old.eq(i).find('.grade_prd').length;k++){
                                if(obj.find('.grade_prd').eq(j).attr("grade") == grade_old.eq(i).find('.grade_prd').eq(k).attr("grade")){
                                    if(grade_old.eq(i).find('.grade_prd').eq(k).find('input').val() != '' && obj.find('.grade_goods_price').text()==grade_old.eq(i).find(".grade_goods_price").text()){
                                        obj.find('.grade_prd').eq(j).find('input').val(grade_old.eq(i).find('.grade_prd').eq(k).find('input').val());
                                    }else{
                                        obj.find('.grade_prd').eq(j).find('input').val($('.spec-price').find('tr').eq(i).find("input[name='prd_price[]']").val());
                                    }
                                }
                            }
                        }else{
                            obj.find('.grade_prd').eq(j).find('input').val($('.spec-price').find('tr').eq(i).find("input[name='prd_price[]']").val());
                        }
                    }
                }else{
                    obj.find('.grade_prd').find('input').val($('.spec-price').find('tr').eq(i).find("input[name='prd_price[]']").val());
                }
            }else{
                obj.find('.grade_goods_price').text($('input[name="shop_price"]').val());
            }
            if($('grade_card').find('input[type="checkbox"]:checked').length<2){
                obj.find('.same').hide();
            }
            grade.push(obj);
        }
        $.each(grade,function(i,e){
            $('.last_grade_tr').before(e);
        });
    }
    $('.grade_card').find('input[type="checkbox"]:checked').each(function () {
        if(is_has_prd > 1){
            $('.same_'+ $(this).val() +'').show();
            $('.same_td_first').show();
        }else{
            $('.same_'+ $(this).val() +'').hide();
            $('.same_td_first').hide();
        }
        if($('.grade_card').find('input[type="checkbox"]:checked').length > 1){
            $(".same").show();
        }
    });
}

function prd_price_blur(obj) {
    grade_price();
    checkProfit1(obj);
}
//同一规格统一会员价
$('.same_prd_member').click(function () {
    $('.card_goods_price_tb').find('.grade_prd').each(function(){
        var _this = $(this);
       if(_this.css('display') == 'none'){
           _this.attr('is_none','0');
       }else{
           _this.attr('is_none','1');
       }
    });
    //console.log($('.grade_prd[is_none="1"]').eq(0).find('input').val());
    $('.prd_list_grade').each(function () {
       $(this).find('input').val($(this).find('.grade_prd[is_none="1"]').eq(0).find('input').val());
    });
});
//同一会员等级统一会员价
$('.same_grade_member').click(function () {
    if($('.hid_grade').val()) {
        var hid_grade_arr = JSON.parse($('.hid_grade').val());
        for (var i in hid_grade_arr) {
            $('[grade="'+ hid_grade_arr[i].grade +'"]').find('input').val($('.card_goods_price_tb').find('.prd_list_grade').eq(0).find('[grade="'+ hid_grade_arr[i].grade +'"]').find('input').val());
        }
    }
});
//同一行统一会员价
$('.goods-edit-basic').on('click','.same_tr_price',function () {
    $(this).parent().parent().find('.grade_prd').each(function(){
        var _this = $(this);
        if(_this.css('display') == 'none'){
            _this.attr('is_none','0');
        }else{
            _this.attr('is_none','1');
        }
    });
    $(this).parent().parent().find('input').val($(this).parent().parent().find('.grade_prd[is_none="1"]').eq(0).find('input').val());
});
//同一列统一会员价
if(hid_grade_arr){
    for (var i in hid_grade_arr) {
        $('.same_' + hid_grade_arr[i].grade + '').find('a').click(function(){
            $('[grade="' + $(this).attr("grade") + '"]').find('input').val($('.card_goods_price_tb').find('.prd_list_grade').eq(0).find('[grade="'+ $(this).attr("grade") +'"]').find('input').val());
        });
    }
}
function in_array(search,array){
    for(var i in array){
        if(array[i]==search){
            return true;
        }
    }
    return false;
}
var label_arry = [];
if ($('input[name="goods_have_label_str"]').val()){
    label_arry = $('input[name="goods_have_label_str"]').val().split(',');
    var label_name = $('input[name="goods_have_label_name"]').val().split(',');
    for (var i in label_arry){
        var img = ' <img src="/image/admin/icon_delete.png" alt="" class="label-delete"  />'
        var span =' <span class="label_span">';
        var inner_html = span + '<span value="'+label_arry[i]+'">'+ label_name[i] + '</span>' + img + '</span>';
        $(".label-info-row").append(inner_html);
        $('#label_id').children("option[value='"+label_arry[i]+"']").remove();
        // if(!in_array(all_label[i].id,label_arry)){
        //     html+='<option value='+all_label[i].id+'>'+all_label[i].name+'</option>';
        // }
    }
    $('.label-info').show();
}


$('.label-info-row').on('click','.label-delete',function(){
    var op_name = $(this).parent().html();
    var opp_val = $(this).prev().attr("value");
   $(this).parent().remove();
   var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
   $('#label_id').append(op_html);
    label_arry.splice($.inArray(opp_val,label_arry),1);
    $('input[name="goods_have_label_str"]').val(label_arry.join(','));
    if(label_arry.length == 0){
        $('.label-info').hide();
    }
});

$("#label_id").change(function(){
    var label_name = $(this).children('option:selected').html();
    var op_val = $(this).children('option:selected').attr('value');
    if(label_arry.length == 0){
        $('.label-info').show();
    }
    label_arry.push(op_val);
    $('input[name="goods_have_label_str"]').val(label_arry.join(','));
    var img = ' <img src="/image/admin/icon_delete.png" alt="" class="label-delete"  />'
    var span =' <span class="label_span">';
    var inner_html = span + '<span value="'+op_val+'">'+ label_name + '</span>' + img + '</span>';
    $(this).parent().find(".label-info-row").append(inner_html);
    $(this).children('option:selected').remove();
});
let card_arry = [];
console.log($('input[name="goods_have_card_str"]').val());
if ($('input[name="goods_have_card_str"]').val()){
    card_arry = $('input[name="goods_have_card_str"]').val().split(',');
    var card_name = $('input[name="goods_have_card_name"]').val().split(',');
    for (var i in card_arry){
        var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
        var span =' <span class="card_span">';
        var inner_html = span + '<span value="'+card_arry[i]+'">'+ card_name[i] + '</span>' + img + '</span>';
        $(".card-info-row").append(inner_html);
        $('#card_id').children("option[value='"+card_arry[i]+"']").remove();
        // if(!in_array(all_label[i].id,label_arry)){
        //     html+='<option value='+all_label[i].id+'>'+all_label[i].name+'</option>';
        // }
    }
    $('.card-info').show();
}
$('.card-info-row').on('click','.card-delete',function(){
    var op_name = $(this).parent().html();
    var opp_val = $(this).prev().attr("value");
   $(this).parent().remove();
   var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
   $('#card_id').append(op_html);
    card_arry.splice($.inArray(opp_val,card_arry),1);
    $('input[name="goods_have_card_str"]').val(card_arry.join(','));
    if(card_arry.length == 0){
        $('.card-info').hide();
    }
});
$('#card_id').change(function(){
    var card_name = $(this).children('option:selected').html();
    var card_val = $(this).children('option:selected').attr('value');
    if(card_arry.length == 0){
        $('.card-info').show();
    }
    card_arry.push(card_val);
    $('input[name="goods_have_card_str"]').val(card_arry.join(','));
    var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
    var span =' <span class="card_span">';
    var inner_html = span + '<span value="'+card_val+'">'+ card_name + '</span>' + img + '</span>';
    $(this).parent().parent().find(".card-info-row").append(inner_html);
    $(this).children('option:selected').remove();
})
$('.refresh-label').click(function () {
    util.ajax_json('/admin/goods/label/all',function(d){
        if(d.error==1){
            util.mobile_alert('刷新失败');
            return false;
        }
        else{
            var all_label = d.content.labels;
            var html = '<option value="0"  selected="selected" >请选择商品标签</option>';
            for (var i in all_label){
                html+='<option value='+all_label[i].id+'>'+all_label[i].name+'</option>';
            }
            $('.label_option').html(html);
            for (var j in label_arry){
                $('#label_id').children("option[value='"+label_arry[j]+"']").remove();
            }
            util.mobile_alert('刷新成功');
        }
    });
});

if ($('input[name="cat_have_label_name"]').val()!=''){
    var cat_label_name = $('input[name="cat_have_label_name"]').val().split(',');
    var cat_label = '';
    for (var i in cat_label_name){
        var span =' <span class="label_span">';
        var inner_html = span + '<span>'+ cat_label_name[i] + '</span>' + '</span>';
        $(".label-info-cat").append(inner_html);
    }
    $('.label-info1').show();
};

if ($('input[name="sort_have_label_name"]').val()!=''){
    var sort_label_name = $('input[name="sort_have_label_name"]').val().split(',');
    var sort_label = '';
    for (var i in sort_label_name){
        var span =' <span class="label_span">';
        var inner_html = span + '<span>'+ sort_label_name[i] + '</span>' + '</span>';
        $(".label-info-sort").append(inner_html);
    }
    $('.label-info2').show();
};

if ($('input[name="cat_have_card_name"]').val()!=''){
    var cat_card_name = $('input[name="cat_have_card_name"]').val().split(',');
    var cat_card = '';
    for (var i in cat_card_name){
        var span =' <span class="card_span">';
        var inner_html = span + '<span>'+ cat_card_name[i] + '</span>' + '</span>';
        $(".card-info-cat").append(inner_html);
    }
    $('.card-info1').show();
};

if ($('input[name="sort_have_card_name"]').val()!=''){
    var sort_card_name = $('input[name="sort_have_card_name"]').val().split(',');
    var sort_card = '';
    for (var i in sort_card_name){
        var span =' <span class="card_span">';
        var inner_html = span + '<span>'+ sort_card_name[i] + '</span>' + '</span>';
        $(".card-info-sort").append(inner_html);
    }
    $('.card-info2').show();
};
if($("input[name='is_card_exclusive']").is(":checked")){
    $('.card_select_box').css("display",'block');
    if($('.card-info-row').find('.card_span').length > 0){
        $('.card-info').css("display",'block');
    }
}else{
    $('.card_select_box').css("display",'none');
    $('.card-info').css("display",'none');      
}
$("input[name='is_card_exclusive']").change(function () {
    if($("input[name='is_card_exclusive']").is(":checked")){
        $('.card_select_box').css("display",'block');
        if($('.card-info-row').find('.card_span').length > 0){
            $('.card-info').css("display",'block');
        }
    }else{
        $('.card_select_box').css("display",'none');
        $('.card-info').css("display",'none');         
    }
})
$('.refresh-card').click(function () {
    util.ajax_json('/admin/ajax/card/exclusive',function(d){
        if(d.error!=0){
            util.mobile_alert('刷新失败');
            return false;
        }
        else{
            var all_card = d.content.cards;
            var html = '<option value="0"  selected="selected" >请选择会员卡</option>';
            for (var i in all_card){
                html+='<option value='+all_card[i].id+'>'+all_card[i].card_name+'</option>';
            }
            $('#card_id').html(html);
            for (var j in card_arry){
                $('#card_id').children("option[value='"+card_arry[j]+"']").remove();
            }
            util.mobile_alert('刷新成功');
        }
    });
});

//展开收起
//基本信息
$(".show_basic").click(function () {
    if($(this).parent().attr('if_show') == 1){
        $(".basic_info").addClass("buxianshi");
        $(this).find('text').text('展开更多配置');
        $(this).parent().attr('if_show','0');
        $(this).find('img').attr('src',"http://"+image_domain+"/image/admin/info_down.png")
    }else{
        $(".basic_info").removeClass("buxianshi");
        $(this).find('text').text('收起更多配置');
        $(this).parent().attr('if_show','1');
        $(this).find('img').attr('src',"http://"+image_domain+"/image/admin/info_up.png")
    }
})
//库存信息
$(".kucun_more").click(function () {
    if($(this).parent().attr('if_show') == 1){
        $(".kucun_info").addClass("buxianshi");
        $(this).find('text').text('展开更多配置');
        $(this).parent().attr('if_show','0');
        $(this).find('img').attr('src',"http://"+image_domain+"/image/admin/info_down.png")
    }else{
        $(".kucun_info").removeClass("buxianshi");
        $(this).find('text').text('收起更多配置');
        $(this).parent().attr('if_show','1');
        $(this).find('img').attr('src',"http://"+image_domain+"/image/admin/info_up.png")
    }
});

function checkGoodsSn(cb) {
    var goods_sn = $('[name="goods_sn"]').val();
    util.ajax_json('/admin/ajax/goods/check',function(response){
        if(response.content){
            util.mobile_alert('商品货号已存在');
            return false;
        }
        cb();
    }, {goods_sn:goods_sn, goods_id: goods_id});
}
