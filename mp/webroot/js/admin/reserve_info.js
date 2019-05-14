$(document).ready(function () {
    initKindEditor("#editor", function () {
        window.keditor.html($("#service_desc").val());
    });
});
function picker(){
    return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
}

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
$('.btn-save').click(function(e){
    e.stopPropagation();
    if (!confirmation()) return false;
    hasSaved = true;
    layer.ready(function () {
        layer.msg('保存成功', {time: 2000},function () {
            $("#form1").submit();
        });
    });
});
$('.goods-item-img').on('click','.good_img_delete',function(){
    $(this).parent().remove();
    hasSaved = false;
});
$('.add_img').click(function() {
    var max_img = $(".goods-item-img li").length;
    if(max_img>=6){
        util.mobile_alert('最多上传5张图！');
        return false;
    }
    var el = $(this).parent().clone();
    var obj = $(this).parent();
    var w = 800;
    var h = 800;
    $.jImageManager({
        img_width: w,
        img_height: h,
        ok_cb: function (img_arr) {
            var path = img_arr[0].img_url;
            if (path == undefined) {
                path = img_arr[0].url;
            }
            el.find("img").eq(0).attr("src", path);
            el.find("input").attr("value", path);
            hasSaved = false;
            el.removeClass('add_class');
            el.find("img").eq(1).show();
            obj.before(el);
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
$('.box-top-first,.btn-prev').click(function(){
    $('.box-top-detail').removeClass('box-top-detail2');
    $('.box-top-first').removeClass('box-top-first2');
    $('.goods-edit-detail').hide();
    $('.goods-edit-basic').show();
    $('.btn-next').show();
    $('.btn-prev').hide();
});

$('.btn-next,.box-top-detail').click(function(){
    if (!confirmation()) return false;
    $('.box-top-detail').addClass('box-top-detail2');
    $('.box-top-first').addClass('box-top-first2');
    $('.goods-edit-detail').show();
    $('.goods-edit-basic').hide();
    $('.btn-prev').show();
    $('.btn-next').hide();
});

$("input[name='service_type']").change(function () {

    if($("#no_tech").is(":checked")){
        $(".with_techs").hide();
        $(".no_techs").show();
    }
    if($("#with_tech").is(":checked")){
        $(".no_techs").hide();
        $(".with_techs").show();
    }
});

if($("#no_tech").is(":checked")){
    $(".with_techs").hide();
    $(".no_techs").show();
}
if($("#with_tech").is(":checked")){
    $(".no_techs").hide();
    $(".with_techs").show();
}

function confirmation() {
    //判空验证
    if($("input[name='service_name']").val() == ''){
        util.mobile_alert('服务名称不能为空');
        return false;
    }

    if($("#cat_id").val() == 0){
        util.mobile_alert('必须选择服务分类');
        return false;
    }

    if($("#no_tech").is(":checked")) {
        if ($("input[name='services_number']").val() == '') {
            util.mobile_alert('可预约人数不能为空');
            return false;
        }
    }
    if($("#with_tech").is(":checked")){
        if ($("input[name='tech_services_number']").val() == '') {
            util.mobile_alert('技师可预约人数不能为空');
            return false;
        }
    }

    if($('input[name="service_img[]"]:eq(0)').val() == ''){
        util.mobile_alert('服务主图不能为空');
        return false;
    }

    var start_date = $("input[name='start_date']").val();
    if(start_date.length==0){
        util.mobile_alert('可服务开始日期未填写');
        $("input[name='start_date']").focus();
        return false;
    }
    var end_date = $("input[name='end_date']").val();
    if(end_date.length==0){
        util.mobile_alert('可服务结束日期未填写');
        $("input[name='end_date']").focus();
        return false;
    }

    var start_hour = parseInt($("input[name='start_hour']").val());
    if(!(start_hour>=0 && start_hour<24)){
        util.mobile_alert('开始时间的小时在0到24内');
        $("input[name='start_hour']").focus();
        return false;
    }
    var end_hour = parseInt($("input[name='end_hour']").val());
    if(!(end_hour>=0 && end_hour<24)){
        util.mobile_alert('结束时间的小时在0到24内');
        $("input[name='end_hour']").focus();
        return false;
    }
    var start_min = parseInt($("input[name='start_min']").val());
    if(!(start_min>=0 && start_min<=59)){
        util.mobile_alert('开始时间分钟在0到24内');
        $("input[name='start_min']").focus();
        return false;
    }
    var end_min = parseInt($("input[name='end_min']").val());
    if(!(end_min>=0 && end_min<=59)){
        util.mobile_alert('结束时间分钟在0到24内');
        $("input[name='end_min']").focus();
        return false;
    }
    if(start_hour < parseInt($(".start_hour").text()) || (start_hour == parseInt($(".start_hour").text()) && start_min < parseInt($(".start_min").text()))){
        util.mobile_alert('服务开始时间应在门店营业时段内');
        $("input[name='start_hour']").focus();
        return false;
    }
    if(end_hour > parseInt($(".end_hour").text()) || (end_hour == parseInt($(".end_hour").text()) && end_min > parseInt($(".end_min").text()))){
        util.mobile_alert('服务结束时间应在门店营业时段内');
        $("input[name='end_hour']").focus();
        return false;
    }

    var start_period = $("input[name='start_hour']").val()+":"+$("input[name='start_min']").val();
    var end_period = $("input[name='end_hour']").val()+":"+$("input[name='end_min']").val();
    $("input[name='start_period']").val(start_period);
    $("input[name='end_period']").val(end_period);

    var services_number = $("input[name='services_number']").val();
    if(services_number.length==0 && services_number>0){
        util.mobile_alert('服务人数要填写');
        $("input[name='services_number']").focus();
        return false;
    }
    if (typeof checked_service_name != undefined && !checked_service_name) {
        util.mobile_alert('服务名称已存在');
        return false;
    }
    return true;
}