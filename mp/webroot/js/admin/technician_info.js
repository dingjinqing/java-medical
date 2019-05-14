$(function () {
    /*添加技师头像*/
    $('.add_tech_img').click(function() {
        var w = 200;
        var h = 200;
        var that = $(this);
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                that.find('img').attr("src",path);
                var index = path.lastIndexOf('/upload');
                var enen = path.substring(index+1,path.length);
                $("#bg_img_path").val(enen);
                hasSaved = false;
            }
        });
    });

    if($("#all_service").is(":checked")){
        $(".choose_detail").css("display","none");
    }
    if($("#sth_service").is(":checked")){
        $(".choose_detail").css("display","block");
    }
    $("input[name='service_type']").change(function () {
        if($("#all_service").is(":checked")){
            $(".choose_detail").css("display","none");
        }
       else if($("#sth_service").is(":checked")){
            $(".choose_detail").css("display","block");
        }
    });

    //服务全选全不选
    $(".all_choosed").click(function () {
        hasSaved = false;
        var isChecked = $(".all_choosed").prop("checked");
        if($(this).is(':checked')) {
            $(this).attr('src','/image/admin/square_yes.png');
            $(this).parents('.service_table').find('tbody').find("input[type='checkbox']").each(function(){
                if(!$(this).is(':checked'))
                    $(this).click();
            })
        }else{
            $(this).attr('src','/image/admin/square_no.png');
            $(this).parents('.service_table').find('tbody').find("input[type='checkbox']").each(function(){
                if($(this).is(':checked'))
                    $(this).click();
            })
        }
    });
    $(".service_table tbody tr td input[type='checkbox']").click(function () {
        hasSaved = false;
        $(this).attr("checked", "checked");
        var allLength = $(".service_table tbody tr input[type='checkbox']").length;
        var checkedLength = $(".service_table tbody tr input[type='checkbox']:checked").length;
        if(allLength == checkedLength){
            $(".all_choosed").prop("checked",true);
            $(".all_choosed").attr('src','/image/admin/square_yes.png');
        }else {
            $(".all_choosed").prop("checked",false);
        }
        if($(this).is(':checked')){
            $(this).attr('src','/image/admin/square_yes.png');

        }else{
            $(this).attr('src','/image/admin/square_no.png');
            $(".all_choosed").attr('src','/image/admin/square_no.png');
        }
    });



    $(".tech_btn_add").click(function () {
        if($("#technician_name").val() == ""){
            util.mobile_alert('技师姓名不能为空！');
            return false;
        }
        if($("#technician_mobile").val() == ""){
            util.mobile_alert('手机号码不能为空！');
            return false;
        }

        /*验证正确格式的手机号*/
        var re=/^[1][3,4,5,6,7,8,9][0-9]{9}$/;
        if($(":text[name='technician_mobile']").val() != ""){
            if(!re.test($(":text[name='technician_mobile']").val())){
                util.mobile_alert("请输入有效的手机号");
                return false;
            }
        }

        $("input[name='technician_groups']").val($(".tech_group select option:selected").html());
        // $(".service").each(function () {
        //     // if($(this).is(":checked")){
        //         $(this).val($(this).attr('service_id'));
        //     // }
        // })
        hasSaved = true;
        layer.ready(function () {
            layer.msg('保存成功', {time: 2000},function () {
                $("#form4").submit();
            });
        });
    })

});