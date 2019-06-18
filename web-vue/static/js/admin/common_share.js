$('.module_share_image .add_image, .module_share_image .choose_img').click(function(){
    var el = $(this).parent();
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
            el.find('.choose_img').show();
            el.find(".add_image").hide();
            el.find("img").attr("src", path);
            el.find('input[name="share_img"]').val(path);
        }
    });
});
$(document).on('mouseenter','.module_share_image .choose_img',function(){
    $(this).find('span').show();
});
$(document).on('mouseleave','.module_share_image .choose_img',function(){
    $(this).find('span').hide();
});