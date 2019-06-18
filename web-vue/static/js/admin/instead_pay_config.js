$(".draggable").click(function () {
    var img_iud = $(this).parent().parent().attr('img_id');
    if(img_iud == 0){
        $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat");
        $(this).parent().parent().css("background-size","100% 100%");
        $(this).parent().parent().attr('img_id','1');
        $(this).animate({right:"0px"});
        $(this).prev().prop('checked', true);
        $('.all_config_item').css("display","block");
        $(this).parent().parent().parent().parent().find(".is_open").text("已开启");
    }else if(img_iud == 1){
        $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat");
        $(this).parent().parent().css("background-size","100% 100%");
        $(this).parent().parent().attr('img_id','0');
        $(this).animate({right:"20px"});
        $(this).prev().prop('checked', false);
        $('.all_config_item').css("display","none");
        $(this).parent().parent().parent().parent().find(".is_open").text("已关闭");
    }
});
$(".a_example").hover(function () {
    $(".ins_example").css("display","block");
},function () {
    $(".ins_example").css("display","none");
})
$(".show_more").hover(function () {
    $('.more_content').css("display",'block')
},function () {
    $('.more_content').css("display",'none')
})
$('.save_instead_pay').click(function () {
    util.ajax_json('/admin/market/insteadpay/config', function (response) {
        if (response.error == 0) {
            util.mobile_alert('设置成功');
            window.location.reload();
        } else {
            util.mobile_alert(response.message);
        }
    }, $('#form1').serialize())
})
