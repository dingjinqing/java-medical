$(function () {
    //选择商品
    if($("#alls").is(":checked")){
        $(".choosed_info").hide();
    }
    if($("#somes").is(":checked")){
        $(".choosed_info").show();
    }

    $("input[name='goods_all']").change(function () {
        if($("#alls").is(":checked")){
            $(".choosed_info").hide();
        }
        if($("#somes").is(":checked")){
            $(".choosed_info").show();
        }
    });

    //设置默认背景
    var img_path1 = $(".ud_li select option:selected").val();
    $(".have_bg").css("background-image","url("+img_path1+")");
    $(".have_bg").css("background-size","100%,100%");
    $(".ud_li select").change(function () {
        var img_path1 = $(".ud_li select option:selected").val();
        $(".have_bg").css("background-image","url("+img_path1+")");
        $(".have_bg").css("background-size","100%,100%");
    })

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
                $(".have_bg").css("background-image","url("+path+")");
                $(".have_bg").css("background-size","100%,100%");
            }
        });
    });
})