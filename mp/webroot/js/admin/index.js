/**
 * Created by 新国 on 2017/5/15.
 */

$(document).ready(function () {
    $(".account,.log-menu").hover(function () {
        $(".menu_top").show();
        $(".log-menu").show();
    }, function () {
        $(".menu_top").hide();
        $(".log-menu").hide();
    });

    // $(".item-menu a").click(function () {
    //     $(".item-menu a[zid=1]").removeClass("active").children("img[cid=1]").removeClass("on").attr("fid", "0");
    //     $(".item-menu a[zid=1]").children("img[cid=0]").addClass("on");
    //     $(".item-menu a[zid=1]").attr("zid", "0");
    //     $(this).addClass("active");
    //     $(this).attr("zid", "1");
    //     $(this).children("img[cid=1]").addClass("on").attr("fid", "1");
    //     $(this).children("img[cid=0]").removeClass("on");
    //     var link = $(this).attr("link");
    //     if(link && link !="#"){
    //         $("#main").attr("src",link);
    //     }
    // });
    // $(".sub-menu a").click(function () {
    //     $(".item-menu a[zid=1]").removeClass("active").children("img[cid=1]").removeClass("on").attr("fid", "0");
    //     $(".item-menu a[zid=1]").children("img[cid=0]").addClass("on");
    //     $(".item-menu a[zid=1]").attr("zid", "0");
    //     $(this).parent().parent().prev().addClass("active").attr("zid", "1").children("img[cid=1]").addClass("on").attr("fid", "1");
    //     $(this).parent().parent().prev().children("img[cid=0]").removeClass("on");
    //     var link = $(this).attr("link");
    //     if(link && link !="#"){
    //         $("#main").attr("src",link);
    //     }
    // });

    //左边导航设置
    var ym=window.location.href;
    var jie=ym.split("/");
    var arry=jie.slice(3);
    var b_arry=jie.slice(3,6);
    var newarry=arry.join("/");
    var b_newarry=b_arry.join('/');
    var newstr='/'+newarry;
    var b_newstr='/'+b_newarry;
    var light_b=b_newstr.split('?');
    var localurl=parseInt(window.location.href.split('top_index=')[1])+1;//本地url
    var wxsqurl='/wechat/mini/info?type=1&top_index=1';//小程序授权url
    var wxsqurll='/wechat/no/authorization?top_index=1';//小程序授权需匹配的本地uel
    $(".first").each(function(){
        if($(this).attr("bid")==localurl){
            $(".first").css("background-color","");
            $(this).css("background-color","#437AF9");
        }
    });

    // $(".left-menu-content .item-menu ").each(function(){
    //     var ahref = $(this).find("a").attr("href");
    //     var ahref_array = ahref.split("/").slice(1,4);
    //     var new_ahref = '/' + ahref_array.join('/');
    //     var light_ahref = new_ahref.split('?');
    //     // console.log(light_ahref[0]);
    //     if($(this).find("a").attr("href")==newstr || new_ahref == b_newstr ){
    //         $(this).find("img").eq(0).attr("class","");
    //         $(this).find("img").eq(1).attr("class","on");
    //         $(this).find("a").attr("class","active");
    //         $(this).find("span").css({"color":"white","opacity":"1"});
    //         $(this).hover(function(){
    //             $(this).find("img").eq(0).attr("class","");
    //             $(this).find("img").eq(1).attr("class","on");
    //             $(this).find("a").attr("class","active");
    //         },function(){
    //             $(this).find("img").eq(0).attr("class","");
    //             $(this).find("img").eq(1).attr("class","on");
    //             $(this).find("a").attr("class","active");
    //         })
    //     };
    // });

    $(".left-menu-content .item-menu ").each(function(){
        if($(this).find("a").attr("href")==wxsqurl&&newstr==wxsqurll){
            $(this).find("img").eq(0).attr("class","");
            $(this).find("img").eq(1).attr("class","on");
            $(this).find("a").attr("class","active");
            $(this).find("span").css({"color":"white","opacity":"1"});
            $(this).hover(function(){
                $(this).find("img").eq(0).attr("class","");
                $(this).find("img").eq(1).attr("class","on");
                $(this).find("a").attr("class","active");
            },function(){
                $(this).find("img").eq(0).attr("class","");
                $(this).find("img").eq(1).attr("class","on");
                $(this).find("a").attr("class","active");
            })
        };
    });

    // $(".left-menu-content[cid!=1]").hide();
    // $(".first").click(function () {
    //     var link = $(this).attr("link");
    //     var aid = $(this).attr("bid");
    //     $(".left-menu-content[cid=" + aid + "]").show();
    //     $(".left-menu-content[cid!=" + aid + "]").hide();
    //     if(link && link !="#"){
    //         $("#main").attr("src",link);
    //     }
    //
    // });
    // var sign = $(".sign").attr("value");
    // if (sign == 1) {
    //     $("#main").attr("src", "?c=index_decoration");
    // }
    // var cid = $(".right-menu").attr("cid");
    // if (cid > 0) {
    //     $(".left-menu-content").hide();
    //     $(".left-menu-content[cid=" + cid + "]").show();
    // }
    //
    // var b=$(window).height()-85;
    // $(".left-menu-back").css('height',b);

});


$(".out_head").mouseover(function () {
    $(this).hide();
    $('.global_contact').slideDown();
});
$(".global_contact").mouseleave(function () {
    $('.global_contact').slideUp();
    $(".out_head").show();
});

$(document).on('click', '#feedback_submit', function () {
    if (!$('#form_footer_global_feedback').valid()) {
        $('input.error')[0].focus();
        return false;
    }
    var param = {};
    param.name = $("#contact_feedback_name").val();
    param.mobile = $("#contact_feedback_phone").val();
    var msg_length = $("#contact_feedback_message").val().length;
    param.content = msg_length > 1024 ? $("#contact_feedback_message").val().substr(0, 1024) : $("#contact_feedback_message").val();
    ajax_json("/admin/feedback", function (data) {
        if (data && data.error == 0) {
            layer.msg('提交成功，我们会尽快与联系~');
            $('#form_footer_global_feedback').reset();
        }
        else if (data && data.error > 0) {
            layer.msg('提交出错啦，再试一次吧，也可直接联系QQ客服哦~');
        }
    }, param);
    $('#contact_feedback').modal('hide');
    return true;

    function ajax_json(url, cb, params, failcb) {
        if (params == undefined) params = {};
        $.ajax({
            type: 'post',
            url: url,
            data: params,
            success: function (data) {
                try {
                    var d = $.parseJSON(data);
                    cb(d);
                } catch (e) {
                    messageBox("提交失败");
                    if (failcb)
                        failcb();
                }
            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
            }
        });
    }
});




