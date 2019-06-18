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

    $(".item-menu a").click(function () {
        $(".item-menu a[zid=1]").removeClass("active").children("img[cid=1]").removeClass("on").attr("fid", "0");
        $(".item-menu a[zid=1]").children("img[cid=0]").addClass("on");
        $(".item-menu a[zid=1]").attr("zid", "0");
        $(this).addClass("active");
        $(this).attr("zid", "1");
        $(this).children("img[cid=1]").addClass("on").attr("fid", "1");
        $(this).children("img[cid=0]").removeClass("on");
        var link = $(this).attr("link");
        if(link && link !="#"){
            $("#main").attr("src",link);
        }
    });
    $(".sub-menu a").click(function () {
        $(".item-menu a[zid=1]").removeClass("active").children("img[cid=1]").removeClass("on").attr("fid", "0");
        $(".item-menu a[zid=1]").children("img[cid=0]").addClass("on");
        $(".item-menu a[zid=1]").attr("zid", "0");
        $(this).parent().parent().prev().addClass("active").attr("zid", "1").children("img[cid=1]").addClass("on").attr("fid", "1");
        $(this).parent().parent().prev().children("img[cid=0]").removeClass("on");
        var link = $(this).attr("link");
        if(link && link !="#"){
            $("#main").attr("src",link);
        }
    });
    $(".item-menu").hover(function () {
        $(this).children("a").addClass("active").children("img[cid=1]").addClass("on");
        $(this).children("a").children("img[cid=0]").removeClass("on");

        $(this).children(".sub-menu").show();
        var distance = $(this).position().top;
        var height_div = $(this).children(".sub-menu").height();
        if (distance >= 45) {
            if (height_div == 45) {
                $(this).children(".sub-menu").css("top", distance + "px");
            }
            else if (height_div == 45 * 2) {
                $(this).children(".sub-menu").css("top", distance - 22 + "px");
            }
            else {
                $(this).children(".sub-menu").css("top", distance - 45 + "px");
            }
        }

    }, function () {
        $(this).children(".sub-menu").hide();
        $(this).children("a[zid=0]").removeClass("active");
        $(this).children("a").children("img[cid=0]").addClass("on");
        $(this).children("a").children("img[fid=0]").removeClass("on");
        $("img[fid=1]").prev().removeClass("on");
    });

    $(".left-menu-content[cid!=1]").hide();
    $(".first").click(function () {
        var aid = $(this).attr("bid");
        $(".left-menu-content[cid=" + aid + "]").show();
        $(".left-menu-content[cid!=" + aid + "]").hide();
        var link = $(this).attr("link");
        if(link && link !="#"){
            $("#main").attr("src",link);
        }

    });
    var sign = $(".sign").attr("value");
    if (sign == 1) {
        $("#main").attr("src", "?c=index_decoration");
    }
    var cid = $(".right-menu").attr("cid");
    if (cid > 0) {
        $(".left-menu-content").hide();
        $(".left-menu-content[cid=" + cid + "]").show();
    }

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
    ajax_json("/system/feedback", function (data) {
        if (data && data.error == 0) {
            art.dialog.tips('提交成功，我们会尽快与联系~');
            $('#form_footer_global_feedback').reset();
        }
        else if (data && data.error > 0) {
            art.dialog.tips('提交出错啦，再试一次吧，也可直接联系QQ客服哦~');
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