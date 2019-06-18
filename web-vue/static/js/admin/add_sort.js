$(function () {
    //分类头图
    if($(".sele_img").attr('src').indexOf('add_simple')>-1){
        $(".del_this").css("display","none");
        $('.title_mgadsa .change_div').css("display", 'none');
    }else{
        $(".del_this").css("display","block");
        $('.title_mgadsa .change_div').css("display", 'block');
    }
    $('.title_mgadsa .sele_img').click(function() {
        var el = $(this).parent();
        var w = 510;
        var h = 200;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find(".del_this").css("display", 'block');
                el.find(".change_div").css("display", 'block');
                el.find(".sele_img").attr("src", path);
                el.find(".sele_img").css("width", '240px');
                el.find(".sele_img").css("heihgt", '90px');
                $("input[name='sort_img']").val(path);
            }
        });
    });
    $(document).on("click",".title_mgadsa .del_this",function () {
        $('.title_mgadsa .sele_img').attr("src",'/image/admin/add_simple.png');
        $('.title_mgadsa .del_this').css("display", 'none');
        $('.title_mgadsa .change_div').css("display", 'none');
        $("input[name='sort_img']").val("");
    });

    //添加链接
    $('.add_path_list').on('click','.add_path',function () {
        var ipt_link = $(this).prev();
        var _page = $(this).prev().val();
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
                    var appid = iframe.contents().find('tr[data-back="false"]').find(".appid").text();
                    ipt_link.val(link);
                    ipt_link.attr('data-appid',appid);
                    layer.close(index);
                }
            });
        });
    });

    //分类图标
    $('.add_icon_list .sort_icon span').click(function() {
        var el = $(this).parent();
        var w = 150;
        var h = 140;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.css("background", 'url('+path+')');
                el.css("background-size", '70px 70px');
                $("input[name='sort_img']").val(path);
                // el.find(".sele_img").attr("src", path);
            }
        });
    });

    //显示和隐藏
    if($('input[name="sort_radio"]:checked').val() != '0'){
        $(".choose_imfdd").css("display","none");
    }else{
        $(".choose_imfdd").css("display","block");
    }
    $('input[name="sort_radio"]').change(function () {
        if($('input[name="sort_radio"]:checked').val() != '0'){
            $(".choose_imfdd").css("display","none");
        }else{
            $(".choose_imfdd").css("display","block");
        }
    });


    if($('input[name="sort_radio"]:checked').val() == '0'){
        $(".fenleideicon").css("display","none");
    }else{
        $(".fenleideicon").css("display","block");
    }
    $('input[name="sort_radio"]').change(function () {
        if($('input[name="sort_radio"]:checked').val() == '0'){
            $(".fenleideicon").css("display","none");
        }else{
            $(".fenleideicon").css("display","block");
        }
    });

    //图标选择
    $('.select_sort_icon').on('click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            parent.layer.open({
                type: 2
                , title: ['图标选择', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['865px','430px']
                , content: '/admin/goods/sort/icon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) {
                    var iframe = layer.getChildFrame('body', index);
                    var imgf_path = iframe.contents().find('input[name="special_one"]:checked').prev().attr('src');
                    $(".sort_icon").css("background",'url(' + imgf_path +')');
                    $(".sort_icon").css("background-size",'70px 70px');
                    $("input[name='sort_img']").val(imgf_path);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    //预览
    $(".hover_shoe").mouseover(function () {
        $(".show_to").css("display","block");
        $(".show_to").mouseover(function () {
            $(".show_to").css("display","block");
        });
        $(".show_to").mouseleave(function () {
            $(".show_to").css("display","none");
        })
    });
    $(".hover_shoe").mouseleave(function () {
        $(".show_to").css("display","none");
    })

    $(".btn_to_save").click(function () {
        if($('input[name="sort_radio"]:checked').val() == 0){
            $('select[name="parent_id"]').find('[value=0]').prop('selected', 'selected');
        }else if($('input[name="sort_radio"]:checked').val() == 1){
            if($("select[name='parent_id']").val()==''){
                util.mobile_alert('请填写一级分类');
                return false;
            }
        }
        if($(".sort_name").val() == ""){
            util.mobile_alert('请填写分类名称');
            return false;
        }
        if($(".sort_rank").val() == ""){
            $(".sort_rank").val(1);
            // util.mobile_alert('请填写分类优先级');
            // return false;
        }else{
            if($(".sort_rank").val()<1 || $(".sort_rank").val()>100){
                util.mobile_alert('请填写1-100之间的整数');
                return false;
            }
        }
        if($('input[name="sort_radio"]:checked').val() != 0 && $("input[name='sort_img']").val() == ''){
            util.mobile_alert('请填写分类图标');
            return false;
        }
        hasSaved = true;
        $.ajax({
            type: "post",
            url: "/admin/goods/sort/add?type=0",
            data: $("#form1").serialize(),
            dataType: "json",

            success: function(data){
                $('#resText').empty();   //清空resText里面的所有内容
                console.log(data.error);
                console.log(data.message);
                if (data.error == 0){
                    util.mobile_alert('保存成功');
                    location.href = "list?type=0";
                }else{
                    util.mobile_alert('分类'+data.content+"已存在");
                }



            }
        });
    })
    $(".btn_to_update").click(function () {
        if($(".sort_name").val() == ""){
            util.mobile_alert('请填写分类名称');
            return false;
        }
        if($(".sort_rank").val() == ""){
            $(".sort_rank").val(1);
            // util.mobile_alert('请填写分类优先级');
            // return false;
        }else{
            if($(".sort_rank").val()<1 || $(".sort_rank").val()>100){
                util.mobile_alert('请填写1-100之间的整数');
                return false;
            }
        }
        if($("select[name='parent_id']").val() != 0 && $("input[name='sort_img']").val() == ''){
            util.mobile_alert('请填写分类图标');
            return false;
        }
        hasSaved = true;
        $.ajax({
            type: "post",
            url: "/admin/goods/sort/edit?type=0",
            data: $("#form1").serialize(),
            dataType: "json",

            success: function(data){
                $('#resText').empty();   //清空resText里面的所有内容
                console.log(data.error);
                console.log(data.message);
                if (data.error == 0){
                    util.mobile_alert('保存成功');
                    location.href = "list?type=0";
                }else{
                    util.mobile_alert('分类'+data.content+"已存在");
                }



            }
        });
    })

});