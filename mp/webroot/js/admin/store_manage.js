$(function () {


    /*添加图片验证不超过六张*/
    $('.add_img').click(function() {
        var img_number = $(".notice .notice_imgs").length;
        if(img_number >=7 ){
            util.mobile_alert('最多上传6张图！');
            return false;
        }
        var el = $(this).parent().clone();
        var obj = $(this).parent();
        var w = 750;
        var h = 520;
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
                // el.find("img").eq(0).hover(function () {
                //     el.find("img").eq(2).show();
                //     el.find("img").eq(3).show();
                // }, function () {
                //     el.find("img").eq(2).hide();
                //     el.find("img").eq(3).hide();
                // })
                // el.find("img").eq(2).hover(function () {
                //     el.find("img").eq(2).show();
                //     el.find("img").eq(3).show();
                // }, function () {
                //     el.find("img").eq(2).hide();
                //     el.find("img").eq(3).hide();
                // })
                // el.find("img").eq(3).hover(function () {
                //     el.find("img").eq(2).show();
                //     el.find("img").eq(3).show();
                // }, function () {
                //     el.find("img").eq(2).hide();
                //     el.find("img").eq(3).hide();
                // })
                // el.find("img").eq(2).click(function () {
                //     var pre_src = $(this).parents('.notice_imgs').prev().find('img').eq(0).attr('src');
                //     var cur_src = $(this).prev().prev().attr('src');
                //     if(pre_src) {
                //         $(this).parents('.notice_imgs').prev().find('img').eq(0).attr('src', cur_src);
                //         $(this).parents('.notice_imgs').prev().find('input').val(cur_src);
                //         $(this).prev().prev().attr('src', pre_src);
                //         $(this).prev().prev().prev().val(pre_src)
                //     }
                // })
                // el.find("img").eq(3).click(function () {
                //     var add_img_src = $('.notice').find('.notice_imgs:last').find('.add_img').attr('src');
                //     console.log(add_img_src)
                //     var next_src = $(this).parents('.notice_imgs').next().find('img').eq(0).attr('src');
                //     var cur_src = $(this).prev().prev().prev().attr('src');
                //     if(next_src && next_src != add_img_src) {
                //         $(this).parents('.notice_imgs').next().find('img').eq(0).attr('src', cur_src);
                //         $(this).parents('.notice_imgs').next().find('input').val(cur_src);
                //         $(this).prev().prev().prev().attr('src', next_src);
                //         $(this).prev().prev().prev().prev().val(next_src);
                //     }
                // })
                obj.before(el);
            }
        });
    });
    $(document).on('click','.master_diagram_left',function () {
        var pre_src = $(this).parents('.notice_imgs').prev().find('img').eq(0).attr('src');
        var cur_src = $(this).prev().prev().attr('src');
        if(pre_src) {
            $(this).parents('.notice_imgs').prev().find('img').eq(0).attr('src', cur_src);
            $(this).parents('.notice_imgs').prev().find('input').val(cur_src);
            $(this).prev().prev().attr('src', pre_src);
            $(this).prev().prev().prev().val(pre_src)
        }
    })
    $(document).on('click','.master_diagram_right',function () {
        var add_img_src = $('.notice').find('.notice_imgs:last').find('.add_img').attr('src');
        var next_src = $(this).parents('.notice_imgs').next().find('img').eq(0).attr('src');
        var cur_src = $(this).prev().prev().prev().attr('src');
        if(next_src && next_src != add_img_src) {
            $(this).parents('.notice_imgs').next().find('img').eq(0).attr('src', cur_src);
            $(this).parents('.notice_imgs').next().find('input').val(cur_src);
            $(this).prev().prev().prev().attr('src', next_src);
            $(this).prev().prev().prev().prev().val(next_src);
        }
    })

    /*删除图片*/
    $('.notice').on('click','.del_imgs',function(){
        $(this).parent().remove();
        hasSaved = false;
    });
    
    /*添加标签*/
    $(".btn_add_services").click(function () {
        var new_services = $(".add_service input[type='text']").val();
        new_services = new_services.split(/[,|，]/);
        for(var i=0;i<new_services.length;i++){
            new_services[i] = new_services[i].replace(/(^\s*)|(\s*$)/g, "");
            if(new_services[i].length >4){
                util.mobile_alert("长度不能超过四个字！");
                return false;
            }
            if(new_services[i]==""){
                util.mobile_alert('请输入有效的字符！');
                return false;
            }
            if(new_services[i] != ""){
                $(".special_service").append('<p> <input type="checkbox" name="special_service[]" value="'+new_services[i]+'" checked>'+ new_services[i] +'</p>');
                hasSaved = false;
            }
        }
        $(".add_service input[type='text']").val("");
    });

    /*营业时间限制*/
    $(".set_times").find('input').blur(function () {
        if($(this).val().length == 1){
            $(this).val("0" + $(this).val());
        }
    })
    $(".begin_before").blur(function () {
        if($(this).val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(this).val() != "") {
            if(Number($(this).val()) > 23 || $(this).val().length>2){
                util.mobile_alert("请输入正确的小时数！");
                $(this).val("");
                $(this).focus();
            }
        }
    });
    $(".begin_end").blur(function () {
        if($(this).val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(this).val() != "") {
            if(Number($(this).val())>60 || $(this).val().length>2){
                util.mobile_alert("请输入正确的分钟数！");
                $(this).val("");
                $(this).focus();
            }
        }
    });
    $(".close_before").blur(function () {
        if($(this).val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(this).val() != "") {
            if(Number($(this).val()) > 23 || $(this).val().length>2){
                util.mobile_alert("请输入正确的小时数！");
                $(this).val("");
                $(this).focus();
            }
        }
    });

    $(".close_end").blur(function () {
        if($(this).val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(this).val() != "") {
            if(Number($(this).val())>60 || $(this).val().length>2){
                util.mobile_alert("请输入正确的分钟数！");
                $(this).val("");
                $(this).focus();
            }
        }
    });
    

    /*一些表单输入验证*/
    $(".btn_save a").click(function (e) {
        e.stopPropagation();
        /*店铺名称*/
        if($("#store_name").val() == ""){
            util.mobile_alert('店铺名称不能为空！');
            return false;
        }
        /*负责人*/
        if($("input[name='manager']").val() == ""){
            util.mobile_alert('负责人不能为空！');
            return false;
        }
        /*电话号码*/
        if($("#contact_phone").val() == ""){
            util.mobile_alert('联系电话不能为空！');
            return false;
        }
        if($("#contact_phone").val() != ""){
            // var re=/(^(\d{3,4}-)?\d{7,8})$|(1[3|5|6|7|8|9]\d{9})/ ;
            // if(!re.test($("#contact_phone").val())){
            //     util.mobile_alert("请输入有效的电话号码");
            //     return false;
            // }
            if($("#contact_phone").val().length > 20){
                util.mobile_alert("请输入20个字符内的电话号码");
                return false;
            }
        }
        /* 营业时间*/
        if($(".begin_before").val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(".begin_before").val() != "") {
            if(Number($(this).val()) > 23 || $(this).val().length>2){
                util.mobile_alert("请输入正确的小时数！");
                $(this).val("");
                $(this).focus();
            }
        }
        if($(".begin_end").val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(".begin_end").val() != "") {
            if(Number($(this).val())>60 || $(this).val().length>2){
                util.mobile_alert("请输入正确的分钟数！");
                $(this).val("");
                $(this).focus();
            }
        }
        if($(".close_before").val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(".close_before").val() != "") {
            if(Number($(this).val()) > 23 || $(this).val().length>2){
                util.mobile_alert("请输入正确的小时数！");
                $(this).val("");
                $(this).focus();
            }
        }
        if($(".close_end").val() == ""){
            util.mobile_alert("营业时间不能为空！");
            return false;
        }
        if($(".close_end").val() != "") {
            if(Number($(this).val())>60 || $(this).val().length>2){
                util.mobile_alert("请输入正确的分钟数！");
                $(this).val("");
                $(this).focus();
            }
        }
        if (Number($(".begin_before").val()) > Number($(".close_before").val())
            && (Number($(".close_before").val()) != 0 || Number($(".close_end").val()) != 0)) {
            util.mobile_alert("起始营业时间不能大于终止营业时间");
            return false;
        }
        if (Number($(".begin_before").val()) == Number($(".close_before").val()) &&
            Number($(".begin_end").val()) >= Number($(".close_end").val())) {
            util.mobile_alert("起始营业时间不能等于终止营业时间");
            return false;
        }
        /*地址信息*/
        if($("#keyword").val() == ""){
            util.mobile_alert('位置信息不能为空！');
            return false;
        }
        $("input[name='opening_time']").val($(".begin_before").val()+":"+$(".begin_end").val());
        $("input[name='close_time']").val($(".close_before").val()+":"+$(".close_end").val());

        if($("input[name='latitude']").val() == "" || $("input[name='longitude']").val() == ""){
            util.mobile_alert('请先点击设置地图定位！');
            return false;
        }
        if($('input[name="store_img[]"]:eq(0)').val() == ''){
            util.mobile_alert('门店宣传图片不能为空');
            return false;
        }
        window.keditor.sync();
        $(".content").val($('#editor').val());
        hasSaved = true;
        var data = {
            config_name:'num_config',
            mod_name:'store_num'
        }
        util.ajax_json("/admin/version/judgment",function (d) {
            var self = d.content.self, is_save_success = false;
            if(store_id>0){
                if(self.num >= self.use || self.num<0){
                    is_save_success = true;
                }
            }else {
                if(self.num > self.use || self.num<0){
                    is_save_success = true;
                }
            }
            if (is_save_success) {
                var pos_shop_id = parseInt($('[name="pos_shop_id"]').val());
                if (!isNaN(pos_shop_id) && pos_shop_id > 0) {
                    util.ajax_json('/admin/ajax/store/checkcoding', function (res) {
                        if (res.error == 0) {
                            $('#form1').submit();
                            layer.ready(function () {
                                layer.msg('保存成功', {time: 2000},function () {
                                    $("#form1").submit();
                                });
                            });
                        } else {
                            util.mobile_alert(res.message);
                            return false;
                        }
                    }, {store_id: store_id, pos_shop_id: pos_shop_id})
                } else {
                    $('#form1').submit();
                    layer.ready(function () {
                        layer.msg('保存成功', {time: 2000},function () {
                            $("#form1").submit();
                        });
                    });
                }
            } else {
                util.systemNotice(2,'门店数量已达到'+self.num+'个','门店数量');
            }
        },data);

    });

    /*省市区的联动*/
    $("select[name='province_code']").change(function(){
        var pid= $(this).val();
        var data = {};
        data.province_id = pid;
        util.ajax_json('/admin/ajax/user/address',function(d){
            if(d && d.error == 0){
                var html = '';
                var first_city_id = d.content.city[0].city_id;
                $.each(d.content.city,function(i,e){
                    html +='<option value="'+e.city_id+'" >'+e.name+'</option>';
                });
                $("select[name='city_code']").empty().append(html);
                html='';
                $.each(d.content.district,function(i,e){
                    html +='<option value="'+e.district_id+'" >'+e.name+'</option>';
                });
                $("select[name='district_code']").empty().append(html);
            }
        },data)
    });
    $("select[name='city_code']").change(function(){
        var cid= $(this).val();
        var data = {};
        data.city_id = cid;
        util.ajax_json('/admin/ajax/user/address',function(d){
            if(d && d.error == 0){
                var html = '';
                $.each(d.content,function(i,e){
                    html +='<option value="'+e.district_id+'" >'+e.name+'</option>';
                });
                $("select[name='district_code']").empty().append(html);
            }
        },data)
    });

});

$('.close-store').click(function () {
    var obj = $(this);
    var business_state = $(this).parent().parent().find(".business_state");
    var data = {};
    data.store_id = $(this).attr('store_id');
    util.ajax_json('/admin/store/manage/close',function(d){
        if(d && d.error == 0){
            if(d.message==1){
                //关闭
                util.mobile_alert('门店已歇业！');
                obj.html('开业');
                business_state.text("未营业");
            }else{
                //开启
                util.mobile_alert('门店已开业！');
                obj.text('歇业');
                business_state.text("营业");
            }
        }
    },data)
});