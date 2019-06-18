
var m_group_draw = {
    init_ev_el: function(el,data){
        console.log(data);
        if(is_group_draw == 0){
            el.find('.no_use').show();
        }
        if (data.group_draw_id != undefined && data.group_draw_id != -1) {
            util.ajax_json('/admin/frame/market/groupdraw/goinglist', function (response) {
                if (response.error == 0) {
                    if(data.name_set == 1){
                        el.find('.center_pin_draw .pin_group .group_name').html(data.group_draw_name);
                    }else{
                        el.find('.center_pin_draw .pin_group .group_name').html("拼团抽奖")
                    }



                    el.find('.center_pin_draw .pin_group_center p').html(response.content.min_join_num+'人参与');
                    if (data.show_clock == 1) {
                        el.find('.center_pin_draw .pin_group .group_time').html(response.content.time_doc);
                    } else {
                        el.find('.center_pin_draw .pin_group .group_time').html('');
                    }
                    el.find(".pin_group").css("color",data.font_color);
                    if (data.module_bg == 1) {
                        el.find('.center_pin_draw').css("background","url("+data.module_img+")");
                        el.find('.center_pin_draw').css("background-size","100% 100%");

                    } else {
                        el.find('.center_pin_draw').css("background","url(/image/admin/fighting_group_draw1.jpg)");
                        el.find('.center_pin_draw').css("background-size","100% 100%");
                    }
                }
            }, {act:'group_draw_info',id:data.group_draw_id})
        }
    },
    fill_edit_el: function(el,d){
        el.attr('cur_idx',d.cur_idx);
        el.attr('module_name',d.module_name);
        el.find(".add_bgs").click(function(){
            var that = $(this);
            $.jImageManager({
                img_width:720,
                img_height:260,
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
        util.ajax_json('/admin/frame/market/groupdraw/goinglist', function (response) {
            var html = '';
            if (response.error == 0) {
                var list = response.content;
                for (var i in list) {
                    html += '<option value="'+i+'">'+list[i]+'</option>';
                }
                el.find('#group_draw_select').append(html);
                if (d.group_draw_id != undefined) {
                    el.find('#group_draw_select option[value="'+d.group_draw_id+'"]').prop('selected', 'selected');
                }
                if (d.name_set == undefined) d.name_set = 0;
                el.find('.set_names input[name="name_set"]').eq(d.name_set).prop('checked', true);
                if (d.name_set == 1) {
                    el.find('.set_names input[name="group_draw_name"]').val(d.group_draw_name);
                }else{
                    el.find('.set_names input[name="group_draw_name"]').val('');
                }
                console.log(d);
                if (d.show_clock == undefined) d.show_clock = 1;
                el.find('.group_select input[name="show_clock"]').eq(d.show_clock).prop('checked', true);
                if (d.module_bg == undefined) d.module_bg = 0;
                el.find('.group_select input[name="module_bg"]').eq(d.module_bg).prop('checked', true);
                if(d.module_bg == 1){
                    el.find('.add_bg_img').css("display",'block');
                    el.find('.pin_ig').attr('src',d.module_img).css('display', 'inline-block');
                    el.find('.change-img2').css('display', 'block');
                }else{
                    el.find('.add_bg_img').css("display",'none');
                }
                if (d.font_color == undefined) d.font_color = "#ffffff";
                el.find("input[name='font_color']").val(d.font_color);

            }
        }, {act:'going_activity'})

        el.find("input,select").change(function () {
            manager.change_show_module();
        })
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_group_draw").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el,data);
        $("#module_edit").show();
    },
    get_data: function(){
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
        if (el.find("#group_draw_select").val() == '') {
            util.mobile_alert('请选择拼团抽奖活动!');
            return false;
        }
        if(el.find("input[name='module_bg']:checked").val() == 1){
            if(el.find(".add_bgs .pin_ig").attr("src") == "" || !el.find(".add_bgs .pin_ig").attr("src")){
                util.mobile_alert('请上传活动底图!');
                return false;
            }
        }
        d.group_draw_id = el.find("#group_draw_select").val();
        d.name_set = el.find('.set_names input:checked').val();
        var group_draw_name = el.find('input[name="group_draw_name"]').val();
        if(d.name_set == 1){
            d.group_draw_name = group_draw_name;
        }else{
            d.group_draw_name = "拼团抽奖";
        }

        d.show_clock = el.find('input[name="show_clock"]:checked').val();
        d.font_color = el.find("input[name='font_color']").val();
        d.module_bg = el.find("input[name='module_bg']:checked").val();
        d.module_img = el.find(".add_bgs .pin_ig").attr("src");
        console.log(d);
        return d;
    },
    if_bgshow:function (obj) {
        var add_bg = $(obj).parent().parent().find('.add_bg_img');
        if ($(obj).val() == 1) {
            add_bg.show();
            if( add_bg.find(".add_bgs .pin_ig").attr("src") != '' && add_bg.find(".add_bgs .pin_ig").attr("src") != undefined){
                add_bg.find(".change-img2").css('display','block');
                add_bg.find(".add_bgs .pin_ig").css("display","block");
            }
        } else{
            add_bg.hide();
            add_bg.find(".add_bgs .pin_ig").css("display","none");
            add_bg.find(".change-img2").css('display','none');
        }
    }
};
