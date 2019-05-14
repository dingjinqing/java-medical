var m_pin_integration = {
    init_ev_el: function(el,data){
        // console.log(data);
        var param = {data: $.toJSON(data)};
        // console.log(param);
        if(data.act_id !=undefined && data.act_id>0){
            util.ajax_json('/admin/ajax/market/integration/info', function (response) {
                console.log(response);
                if (response.error == 0) {
                    var actInfo = response.content;
                    if(actInfo.pin_title == 1){
                        el.find('.title-content').show();
                        el.find('.user_auto').hide();
                        el.find('.inte_total').text(actInfo.inte_total);
                    }else{
                        el.find('.title-content').hide();
                        el.find('.user_auto').show();
                        el.find('.user_auto').text(actInfo.title);
                    }
                    if (actInfo.hide_time == 1){
                        el.find(".p4").hide();
                    }else{
                        el.find(".p4").show();
                    }
                    if (actInfo.hide_active == 1){
                        el.find(".p3").hide();
                    }else{
                        el.find(".p3").show();
                    }

                    el.find('.limit_amount').text(actInfo.limit_amount);
                    el.find('.inte_group').text(actInfo.inte_group);
                    el.find('.start_time').text(actInfo.start_time);
                    el.find('.end_time').text(actInfo.end_time);
                    el.find(".pin_content div").css("color",data.font_color);
                    if (data.module_bg == 1) {
                        el.find('.pin_content').css("background","url("+data.module_img+")");
                        el.find('.pin_content').css("background-size","100% 100%");

                    } else {
                        el.find('.pin_content').css("background","url(/image/admin/pin_background.png)");
                        el.find('.pin_content').css("background-size","100% 100%");
                    }
                } else {
                    util.mobile_alert(response.message);
                }
            },param)

        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        el.find('#origin').click(function () {
            if($(this).prop('checked',true)){
                el.find('.autoo').prop('disabled',true)
            }
        })
        el.find('#auto').click(function(){
            if($(this).prop('checked',true)){
                el.find('.autoo').prop('disabled',false);
            }
        })
        el.find(".add_bgs").click(function(){
            var that = $(this);
            $.jImageManager({
                img_width:720,
                img_height:300,
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    that.find('.pin_ig').attr('src',path);
                    that.find('.pin_ig').css("display",'block');
                    that.find('.change-img2').css("display",'block');
                    manager.change_show_module();
                    hasSaved = false;
                    // that.find("div").css("display",'none');
                }
            });
        });
        if(d.hide_active == '1')  el.find("#active_y").prop("checked",true);
        if(d.hide_time == '1')  el.find("#time_y").prop("checked",true);
        // console.log(d);
        if(d.pin_title == '0') {
            el.find("#auto").prop("checked",true);
            el.find(".autoo").val(d.pin_title_text);
            el.find(".autoo").prop("disabled",false);
        }else{
            el.find("#origin").prop("checked",true);
            el.find(".autoo").prop("disabled",true);
        }
        if (d.module_bg == undefined) d.module_bg = 0;
        el.find('.group_select input[name="module_bg"]').eq(d.module_bg).prop('checked', true);
        if(d.module_bg == 1){
            el.find('.add_bg_img').css("display",'block');
            el.find('.pin_ig').attr('src',d.module_img).css('display', 'block');
            el.find('.change-img2').css('display', 'block');
        }else{
            $('.add_bg_img').css("display",'none');
        }
        if (d.font_color == undefined) d.font_color = "#ffffff";
        el.find("input[name='font_color']").val(d.font_color);
        util.ajax_json('/admin/frame/market/integration/activity', function (response) {
            if (response.error == 0) {
                var list = response.content;
                console.log(list);
                var html = '<option value="0" >请选择</option>';
                for (var i in list){
                    if(list[i].id == d.act_id){
                        console.log(d);
                        html+='<option selected="selected" value='+list[i].id + '>'+list[i].name+'</option>';
                    }else{
                        // html+='<option value='+list[i].id+'>'+list[i].name+'</option>';
                        html+=`<option value='${list[i].id}'>${list[i].name}</option>`;
                    }
                }
                el.find('#pin_integration_act').html(html);
            } else {
                util.mobile_alert(response.message);
            }
        });

        el.find("input,select").change(function () {
            manager.change_show_module();
        });
    },

    show_edit_el:function(data){
        var el = $("#template_list .d_m_pin_integration").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        d.act_id = el.find("#pin_integration_act").val();
        d.pin_title = el.find("input[name='pin_title']:checked").val();
        if(d.pin_title==0){
            d.pin_title_text = el.find(".autoo").val();
        }
        d.hide_active = el.find("#active_y:checked").val();
        d.hide_time = el.find("#time_y:checked").val();
        console.log(el.find("input[name='module_bg']").val());
        if(el.find("input[name='module_bg']:checked").val() == 1){
            if(el.find(".add_bgs .pin_ig").attr("src") == "" || !el.find(".add_bgs .pin_ig").attr("src")){
                util.mobile_alert('请上传活动底图!');
                return false;
            }
        }
        d.module_bg = el.find("input[name='module_bg']:checked").val();
        d.module_img = el.find(".add_bgs .pin_ig").attr("src");
        d.font_color = el.find("input[name='font_color']").val();
        console.log(d);
        return d;
    },
    if_bgshow:function (obj) {
        var add_bg = $(obj).parent().parent().find('.add_bg_img');
        if ($(obj).val() == 1) {
            add_bg.show();
            if( add_bg.find(".add_bgs .pin_ig").attr("src") != '' && add_bg.find(".add_bgs .pin_ig").attr("src") != undefined){
                console.log(add_bg.find(".add_bgs .pin_ig").attr("src"));
                add_bg.find(".add_bgs .pin_ig").css("display","block");
                add_bg.find(".change-img2").css('display','block');
            }else{
                add_bg.find(".add_bgs .pin_ig").css("display","none");
                add_bg.find(".change-img2").css('display','none');
            }
        } else{
            add_bg.hide();
            add_bg.find(".add_bgs .pin_ig").css("display","none");
            add_bg.find(".change-img2").css('display','none');
        }
    }
};
