$('.nav_show').on('click','li',function(){
    $(this).addClass('nav_show_active').siblings().removeClass('nav_show_active');
});
$("body").on('click','.delete-nav',function(){
    $('.nav_show li').eq($(this).parent().index()).remove();
    if($(".coupon_info .mp_nav").length <= 5){
        $('.add_nav').show();
    }
    $(this).parent().remove();
});
$('body').on('change','input[name="title[]"]',function(){
    var index = $(this).parent().parent().index();
    console.log(index);
    $('.nav_show li').eq(index).find('p').text($(this).val());
});
$('.add_nav').click(function(){
    var obj = $(".add_modal").children().clone();
    var src_1 = obj.find('.nav_icon:eq(0)').find('img').attr("src");
    var src_2 = obj.find('.nav_icon:eq(1)').find('img').attr("src");
    var li_obj = $('.li_modal').clone();
    li_obj.removeClass('hide').removeClass('li_modal');
    li_obj.find('img').eq(0).attr("src",src_2);
    li_obj.find('img').eq(1).attr("src",src_1);
    li_obj.find('p').text(obj.find('input[name="title[]"]').val());
    $('.nav_show .li_modal').before(li_obj);
    $(this).before(obj);
    if($(".coupon_info .mp_nav").length==5){
        $(this).hide();
    }
});
$('body').on('click','.add_img',function() {
    var i = $(this).parent().index('.nav_icon')%2;
    console.log(i);
    var index = $(this).parent().parent().parent().index();
    console.log(index);
    var el = $(this);
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
            el.prev().attr("src", path);
            el.next().val(path);
            $('.nav_show li').eq(index).find('img').eq(i).attr("src", path);
        }
    });

});

var mp_title = '';
var mp_hover_img = '';
var mp_hover_ipt = '';
var mp_normal_img = '';
var mp_normal_ipt = '';
var index = '';
$('.coupon_rule').on('click','.mp_change',function () {
    mp_title = $(this).parent().parent().prev().find('input[name="title[]"]');
    mp_hover_img = $(this).parent().next().find('img');
    mp_hover_ipt = $(this).parent().next().find('input[name="hover[]"]');
    mp_normal_img = $(this).parent().next().next().find('img');
    mp_normal_ipt = $(this).parent().next().next().find('input[name="normal[]"]');
    index = $(this).parent().parent().parent().index();
    $('.mp_list').removeClass('mp_list_active');
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['图标选择', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['735px','200px']
            , content: $('#change-icon') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            //, btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
        });
    });
});
$('.mp_list').click(function () {
    $(this).addClass('mp_list_active').siblings().removeClass('mp_list_active');
    var title = $(this).find('span').html();
    var hover_img = $(this).find('.hover1').attr('src');
    var normal_img = $(this).find('.normal1').attr('src');
    mp_title.val(title);
    $('.nav_show li').eq(index).find('p').text(title);
    mp_hover_img.attr('src',hover_img);
    mp_hover_ipt.val(hover_img);
    $('.nav_show li').eq(index).find('.nav_red').attr('src',hover_img);
    mp_normal_img.attr('src',normal_img);
    mp_normal_ipt.val(normal_img);
    $('.nav_show li').eq(index).find('.nav_gray').attr('src',normal_img);
    window.layer.closeAll();
});

$('.coupon_info').on('click','.btn_link',function () {
    var ipt_link = $(this).prev();
    var _page = $(this).prev().val();
    var _val = $(this).attr('val');
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['选择链接', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['800px','430px']
            , content: '/admin/frame/decoration/link?bottom=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function (index) {
                var iframe = layer.getChildFrame('body', index);
                var link = iframe.contents().find('tr[data-back="false"]').find(".link").text();
                if(_val == 1 && link == 'pages/customer/customer'){
                    util.mobile_alert('第一个不能添加客服，请重新选择');
                    return;
                }
                var appid = iframe.contents().find('tr[data-back="false"]').find(".appid").text();
                var page_arr = [];
                $('.coupon_rule').find('input[name="page[]"]').each(function (i,v) {
                    page_arr.push($(v).val());
                });
                var page_index = $.inArray("pages/storelist/storelist", page_arr);
                if(_page != 'pages/storelist/storelist'){
                    if(page_index >= 0 && link == "pages/storelist/storelist"){
                        util.mobile_alert('底部导航只能添加一个门店，请选择其他链接');
                        return;
                    }
                }
                ipt_link.val(link);
                ipt_link.attr('data-appid',appid);
                layer.close(index);
            }
        });
    });
});