$(function () {
    //分类图标
    $(document).on("click",'.add_icon_list .sort_icon span',function() {
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
                el.parent().prev().prev().val(path);
                // el.find(".sele_img").attr("src", path);
            }
        });
    });


    //添加链接
    $(document).on('click','.add_path_list .add_path',function () {
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
    //预览
        $(".hover_shoe").hover(function () {
            $(".show_to").css("display","block")
        },function () {
            $(".show_to").css("display","none")
        })
    
    //添加子类
    $(".add_child_item").click(function () {
        var obj = $(".clone_item").children().clone();
        $(".add_child_item").before(obj);
        if($(".child_content").find(".each_itema").length == 0){
            $(".child_content").css("padding","0");
            $(".child_content").css("background-color","#fff");
        }else{
            $(".child_content").css("padding","15px 10px");
            $(".child_content").css("background-color","#f8f8f8");
        }
    })
    $(document).on("click",".del_this",function () {
        $(this).parent().remove();
        if($(".child_content").find(".each_itema").length == 0){
            $(".child_content").css("padding","0");
            $(".child_content").css("background-color","#fff");
        }else{
            $(".child_content").css("padding","15px 10px");
            $(".child_content").css("background-color","#f8f8f8");
        }
    })

    //图标选择
    $(document).on('click','.select_sort_icon',function(){
        var _this = $(this);
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
                ,yes: function (index, layero) {
                    var iframe = layer.getChildFrame('body', index);
                    var imgf_path = iframe.contents().find('input[name="special_one"]:checked').prev().attr('src');
                    _this.next().css("background",'url(' + imgf_path +')');
                    _this.next().css("background-size",'70px 70px');
                    _this.parent().prev().prev().val(imgf_path);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    $(".btn_to_save").click(function () {
        if($(".reco_rank").val() < 1){
            $(".reco_rank").val(1) ;
        }else{
            if($(".reco_rank").val()<1 || $(".reco_rank").val()>100){
                util.mobile_alert('请填写1-100之间的整数');
                return false;
            }
        }

        if($(".reco_name").val().length<1){
            util.mobile_alert('请填写推荐分类名称');
            return false;
        }
        var children = [];
        var is_save = 1;
        $(".detail_lis .each_itema").each(function (i) {
            if($(this).find(".chilie_sort_name").val() == ''){
                is_save= -1;
                util.mobile_alert('请填写子分类名称');
                return false;
            }
            if($(this).find(".sort_img").val() == ''){
                is_save= -1;
                util.mobile_alert('请填写子分类图标');
                return false;
            }
            children[i] = {
                'sort_name':$(this).find(".chilie_sort_name").val(),
                'sort_img':$(this).find(".sort_img").val(),
                'img_link':$(this).find(".path_input").val(),
                'sort_id':$(this).find('.child_id').val(),
                'parent_id':$(this).find('.parent').val()
            }
        })
        if(is_save==-1){
            return false;
        }
        children = JSON.stringify(children)
        $("input[name='children']").val(children);
        hasSaved = true;
        $.ajax({
            type: "post",
            url: "/admin/goods/sort/add?type=1",
            data: $("#form1").serialize(),
            dataType: "json",

            success: function(data){
                $('#resText').empty();   //清空resText里面的所有内容
                console.log(data.error);
                console.log(data.message);
                if (data.error == 0){
                    util.mobile_alert('保存成功！');
                    location.href = "list?type=1";
                }else{
                    util.mobile_alert(data.content);
                }



            }
        });
        // layer.ready(function () {
        //     layer.msg('保存成功', {time: 1000},function () {
        //         $("#form1").submit();
        //     });
        // });
    })
    $(".btn_to_update").click(function () {
        if($(".reco_rank").val() < 1){
            $(".reco_rank").val(1) ;
        }else{
            if($(".reco_rank").val()<1 || $(".reco_rank").val()>100){
                util.mobile_alert('请填写1-100之间的整数');
                return false;
            }
        }

        if($(".reco_name").val().length<1){
            util.mobile_alert('请填写推荐分类名称');
            return false;
        }
        var children = [];
        var is_save = 1;
        $(".detail_lis .each_itema").each(function (i) {
            if($(this).find(".chilie_sort_name").val() == ''){
                is_save= -1;
                util.mobile_alert('请填写子分类名称');
                return false;
            }
            if($(this).find(".sort_img").val() == ''){
                is_save= -1;
                util.mobile_alert('请填写子分类图标');
                return false;
            }
            children[i] = {
                'sort_name':$(this).find(".chilie_sort_name").val(),
                'sort_img':$(this).find(".sort_img").val(),
                'img_link':$(this).find(".path_input").val(),
                'sort_id':$(this).find('.child_id').val(),
                'parent_id':$(this).find('.parent').val()
            }
        })
        if(is_save==-1){
            return false;
        }
        children = JSON.stringify(children)
        $("input[name='children']").val(children);
        hasSaved = true;
        $.ajax({
            type: "post",
            url: "/admin/goods/sort/edit?type=1",
            data: $("#form1").serialize(),
            dataType: "json",
            success: function(data){
                $('#resText').empty();   //清空resText里面的所有内容
                if (data.error == 0){
                    util.mobile_alert('保存成功！');
                    location.href = "list?type=1";
                }else{
                    util.mobile_alert(data.content);
                }



            }
        });
        // layer.ready(function () {
        //     layer.msg('保存成功', {time: 1000},function () {
        //         $("#form1").submit();
        //     });
        // });
    })
})