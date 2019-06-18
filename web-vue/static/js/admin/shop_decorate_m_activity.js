 /**
 * 电话模板
 */
var m_phone = {
    init_ev_el: function (el, data) {
        el.find(".phone_number").text(data.title);
        if (data.show_type == 1) {
            //悬浮
            el.find(".phone_module .phone-icon").show();
            el.find(".phone_module .phone_number").hide();
            el.find(".phone_module .phone-icon").css(
                {
                    "background-image": "url(" + data.sps_icon + ")",
                    "width": "45px",
                    "height": "45px",
                    "background-size": "45px"
                }
            );
            el.css(
                {
                    "background-color":"transparent",
                    "position": "absolute",
                    "right": "20px",
                    "bottom": "50px",
                    "z-index": "9999"
                }
            );
            $('#drag_area_container').css('transform', 'translate(0,0)');
            el.find('.item_operation').html(
                '<img class="del_img" src="/image/admin/shop_beautify/add_close.png">'
            );
        } else {
            //普通
            el.find(".phone_module .phone_number").show();
            el.find(".phone_module .phone-icon").hide();
            el.removeAttrs('style');
            if (data.align_type == 1) {
                el.find(".phone_module .phone_number").css("text-align", 'center');
            } else {
                el.find(".phone_module .phone_number").css("text-align", 'left');
            }
            el.find(".phone_module .phone_number").css('color', data.color);
            el.find(".phone_module .phone_number").css('background-color', data.background_color);
            el.find('.item_operation').html(
                '<img class="up_img" src="/image/admin/new_shop_beautify/add_up_use.png">' +
                '<img class="down_img" src="/image/admin/new_shop_beautify/add_down.png">' +
                '<img class="del_img" src="/image/admin/new_shop_beautify/add_close.png">'
            );
        }
    },

    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        el.find("#title").val(d.title);
        d.show_type = d.show_type != undefined ? d.show_type : 0;
        d.align_type = d.align_type != undefined ? d.align_type : 0;
        el.find("input[name='show_type']").eq(d.show_type).prop('checked', true);
        el.find("input[name='show_type']").eq(d.show_type).trigger('click');
        el.find("input[name='align_type'][value='" + d.align_type + "']").prop('checked', true);
        el.find("input[name='color']").val(d.color);
        el.find("input[name='background_color']").val(d.background_color != undefined ? d.background_color : '#ffffff');
        if (d.sps_icon == undefined) {
            el.find('.sps_content input:radio').eq(0).prop('checked', true);
        } else {
            el.find(".sps_content input[src_img='" + d.sps_icon + "']").prop('checked', true);
        }

        el.find("input").change(function () {
            manager.change_show_module();
        })
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_phone").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.title = el.find("#title").val();
        d.show_type = el.find('input[name="show_type"]:checked').val();
        d.sps_icon = el.find('input[name="sps_icon"]:checked').attr('src_img');
        d.align_type = el.find('input[name="align_type"]:checked').val();
        d.color = el.find('input[name="color"]').val();
        d.background_color = el.find('input[name="background_color"]').val();
        return d;
    },
    chooseShowType: function (t) {
        if ($(t).val() == 1) {
            $('#specials').show();
            $('#normala').hide();
        } else {
            $('#normala').show();
            $('#specials').hide();
        }
    },
    checkParam: function () {
        var res = {
            error: 0,
            msg: ''
        };
        var data = m_phone.get_data();
        if (!/^[\d-\+]{7,20}$/.test(data.title)) {
            res.error = 2;
            res.msg = '电话号码非法';
        }
        var phone_num1 = 0;

        if (manager.g_data_list) {
            for (var i in manager.g_data_list) {
                if (manager.g_data_list[i].module_name == 'm_phone' && manager.g_data_list[i].show_type == 1) {
                    if (data.cur_idx != manager.g_data_list[i].cur_idx) {
                        phone_num1 = phone_num1 + 1;
                    }
                }
            }
        }
        if (phone_num1 >= 1 && data.show_type == 1) {
            res.error = 2;
            res.msg = '一个自定义页面仅允许装修一个悬浮电话模块';
        }
        return res;
    }
};
/**
 * 客服模板
 */

var m_service = {
    init_ev_el: function (el, data) {
        if (data.service && data.service != 8) {
            var ser_src = '/image/admin/shop_deco/customer' + data.service + '.png';
            el.find('.service_module').find('img').attr('src', ser_src);
        }
        if(data.service == 8){
            el.find('.service_module').find('img').attr('src', data.img_url);
        }
        //console.log(manager.g_data_list);
        if (manager.g_data_list) {
            for (var i in manager.g_data_list) {
                if (manager.g_data_list[i].module_name == 'm_service') {
                    $('.m_service').css('bottom', '100px');
                }
            }
        }
    },

    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        console.log(d);
        el.find('[name="m_service"]').val(el.find('[name="ipt_service"]:checked').val());
        if (d.service) {
            el.find('[name="m_service"]').val(d.service);
            el.find('[name="ipt_service"]').eq(d.service - 1).prop('checked', true);
        } else {
            el.find('[name="ipt_service"]').eq(0).prop('checked', true);
        }
        if(d.service == 8){
            el.find('[name="ipt_service"]').eq(d.service - 1).prev().find('img').attr('src',d.img_url);
        }
        //console.log(manager.g_data_list);
        if (manager.g_data_list) {
            for (var i in manager.g_data_list) {
                if (manager.g_data_list[i].module_name == 'm_phone') {
                    $('.m_service').css('bottom', '100px');
                }
            }
        }
        el.find('[name="ipt_service"]').change(function () {
            manager.change_show_module();
        })
        el.find(".add_image").click(function(){
            var that = $(this);
            $.jImageManager({
                img_width:90,
                img_height:90,
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    $(".module_body .data_item .add_image img").attr('src',path);
                    manager.change_show_module();
                }
            });
        });
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_service").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.service = el.find('[name="ipt_service"]:checked').val();
        if(d.service == 8){
            d.img_url = el.find('.add_image img').attr('src');
        }
        return d;
    },
    /**
     * 添加新模块时，判断是否可以添加
     *
     * @param m
     * @returns {boolean}
     */
    // check_can_add: function (m) {
    //     var num = manager.get_module_num("m_service");
    //     return (num == 0) ? true : "一个自定义页面仅允许装修一个客服模块";
    // },

    /**
     * 保存当前模块检查
     * @returns {{error: number, msg: string}}
     */
    checkParam: function () {
        var res = {
            error: 0,
            msg: ''
        };
        var data = m_service.get_data();
        var service_num = manager.get_module_num("m_service");
        if (service_num > 1) {
            res.error = 2;
            res.msg = '一个自定义页面仅允许装修一个客服模块';
        }
        return res;
    }
};
/**
 * 地图模块
 */
var geocoder, map, marker = null;
var markersArray = [];
var m_map = {
    init_ev_el: function (el, data) {
        $('#drag_area #map_content').attr('id', 'map_content' + data.cur_idx);
        var title = data.city != undefined ? data.city + data.area + ',' + data.address : '显示地址';
        var map = m_map.mapInit('map_content' + data.cur_idx, false);
        m_map.codeAddress(map, data.city, data.area, data.address);
        el.find('.map_module .map_head_text').html(title);
    },
    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        el.find('[name="province"] option[value="' + d.province_code + '"]').prop('selected', 'selected');
        el.find('[name="province"]').trigger('change');
        setTimeout(function () {
            el.find('[name="city"] option[value="' + d.city_code + '"]').prop('selected', 'selected');
            el.find('[name="city"]').trigger('change');
            setTimeout(function () {
                el.find('[name="area"] option[value="' + d.area_code + '"]').prop('selected', 'selected');
            }, 1000);
        }, 1000);
        el.find('[name="address"]').val(d.address);
        // el.find('[name="address"]').change(function () {
        //     manager.change_show_module();
        // });
        var map_show = d.map_show == undefined ? 1 : d.map_show;
        el.find('[name="map_show"][value="' + map_show + '"]').prop('checked', true);
        m_map.mapInit();
        m_map.codeAddress(m_map.mapInit(), d.city, d.area, d.address);
    },
    show_edit_el: function (data) {
        var el = $("#template_list .d_m_map").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.province = el.find('[name="province"] option:selected').text();
        d.province_code = el.find('[name="province"]').val();
        d.city = el.find('[name="city"] option:selected').text();
        d.city_code = el.find('[name="city"]').val();
        d.area = el.find('[name="area"] option:selected').text();
        d.area_code = el.find('[name="area"]').val();
        d.address = el.find('[name="address"]').val();
        d.map_show = el.find('[name="map_show"]:checked').val();
        d.latitude = el.find('[name="latitude"]:hidden').val();
        d.longitude = el.find('[name="longitude"]:hidden').val();
        return d;
    },
    mapInit: function (ele, scale_show, latitude, longitude) {
        ele = typeof arguments[0] != 'undefined' ? arguments[0] : 'container';
        scale_show = typeof arguments[1] != 'undefined' ? arguments[1] : true;
        latitude = typeof arguments[2] != 'undefined' ? arguments[2] : '39.916527';
        longitude = typeof arguments[3] != 'undefined' ? arguments[3] : '116.397128';
        var center = new qq.maps.LatLng(latitude, longitude);
        var jsonData = {
            center: center,
            zoom: 15
        };
        if (scale_show === true) {
            //jsonData.zoom = 15;
        } else {
            jsonData.zoomControl = false;
            jsonData.panControl = false;
            jsonData.mapTypeControl = false;
        }
        map = new qq.maps.Map(document.getElementById(ele), jsonData);
        return map;
    },
    clearOverlays: function () {
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setMap(null);
            }
        }
    },
    codeAddress: function (map, city, area, address) {
        map = (typeof arguments[0] != 'undefined' && arguments[0] != '') ? arguments[0] : m_map.mapInit();
        city = (typeof arguments[1] != 'undefined' && arguments[1] != '') ? arguments[1] : $("#module_edit .d_m_map select[name='city'] option:selected").text();
        area = (typeof arguments[2] != 'undefined' && arguments[2] != '') ? arguments[2] : $("#module_edit .d_m_map select[name='area'] option:selected").text();
        address = (typeof arguments[3] != 'undefined' && arguments[3] != '') ? arguments[3] : $('#module_edit .d_m_map input[name="address"]').val();
        //通过getLocation();方法获取位置信息值
        m_map.clearOverlays();
        var geocoder = initGeocoder(map);
        geocoder.getLocation(city + area + address);
    },
    //省联动
    changeProvice: function (t) {
        var data = {};
        data.province_id = $(t).val();
        util.ajax_json('/admin/ajax/user/address', function (d) {
            if (d && d.error == 0) {
                var html = '';
                var first_city_id = d.content.city[0].city_id;
                $.each(d.content.city, function (i, e) {
                    html += '<option value="' + e.city_id + '" >' + e.name + '</option>';
                });
                $("select[name='city']").empty().append(html);
                html = '';
                $.each(d.content.district, function (i, e) {
                    html += '<option value="' + e.district_id + '" >' + e.name + '</option>';
                });
                $("select[name='area']").empty().append(html);
                $('#map_code_address').trigger('click');
            }
        }, data)
    },
    //市区联动
    changeCity: function (t) {
        var data = {};
        data.city_id = $(t).val();
        util.ajax_json('/admin/ajax/user/address', function (d) {
            if (d && d.error == 0) {
                var html = '';
                $.each(d.content, function (i, e) {
                    html += '<option value="' + e.district_id + '" >' + e.name + '</option>';
                });
                $("select[name='area']").empty().append(html);
                $('#map_code_address').trigger('click');
            }
        }, data)
    },
    checkParam: function () {
        var res = {
            error: 0,
            msg: ''
        };
        var data = m_map.get_data();
        if (data.city_code == '' || data.area_code == '') {
            res.error = 2;
            res.msg = '请选择地址';
        }
        if (data.latitude == '' || data.longitude == '') {
            res.error = 2;
            res.msg = '请定位地图';
        }
        return res;
    }
};

var map = m_map.mapInit();

//添加监听事件 获取鼠标单击事件 点击地图获取点的坐标
qq.maps.event.addListener(map, 'click', function (event) {
    m_map.clearOverlays();
    var marker = new qq.maps.Marker({
        position: event.latLng,
        map: map
    });
    /*qq.maps.event.addListener(map, 'click', function(event) {
        marker.setMap(null);
    });*/
    var latitude = marker.position.lat;     //纬度
    var longitude = marker.position.lng;    //经度
    $("input[name='latitude']").val(latitude);
    $("input[name='longitude']").val(longitude);
});

//调用地址解析类
function initGeocoder(map) {
    var geocoder = new qq.maps.Geocoder({
        complete: function (result) {
            map.setCenter(result.detail.location);
            var marker = new qq.maps.Marker({
                map: map,
                position: result.detail.location
            });
            markersArray.push(marker);
            var latitude = marker.map.center.lat;      //纬度
            var longitude = marker.map.center.lng;     //经度
            var latlngs = marker.map.center;           //经纬度
            $("input[name='latitude']").val(latitude);
            $("input[name='longitude']").val(longitude);
        }
    });
    return geocoder;
}

//获取城市列表接口设置中心点
var citylocation = new qq.maps.CityService({
    complete: function (result) {
        map.setCenter(result.detail.latLng);
    }
});
//调用searchLocalCity();方法    根据用户IP查询城市信息
citylocation.searchLocalCity();

var m_video = {
    init_ev_el: function (el, data) {
        if (is_video == 0) {
            el.find('.no_use').show();
        }
        if (data.video_url != undefined) {
            el.find('video').attr('src', data.video_url);
        }
        if(data.video_poster != undefined){
            if(data.video_poster == 2){
                if(data.img_url != undefined && data.img_url != ''){
                  el.find('video').attr('poster',data.img_url);
                }
            }else{
                el.find('video').removeAttr('poster');
            }
        }
        if(data.video_title != undefined){
            el.find('.video_title span').text(data.video_title);
        }

    },

    fill_edit_el: function (el, d) {
        console.log(d);
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        //el.find(".btn_playa").attr('href', d.video_url);
        //el.find('.btn_playa').show();
        el.find('[name="video"]').val(d.video_url);
        el.find('[name="video_img"]').val(d.video_img);
        el.find('[name="video_size"]').val(d.video_size);
        el.find('[name="video_width"]').val(d.video_width);
        el.find('[name="video_height"]').val(d.video_height);
        if(d.video_title){
            el.find('input[name="video_title"]').val(d.video_title);
        }
        if(d.img_url){
            el.find(".image").attr("src",d.img_url);
            el.find('.image').css("width","90px");
            el.find('.image').css("height","90px");
            el.find(".add_image").css("background","none");
        }
        if (d.video_img != undefined && d.video_img != '') {
            el.find('.add-video').attr('src', d.video_img);
            el.find('.add-video').css({width: '100%', height: '100%', marginTop: '0px'});
        }
        el.find('input[name="video_poster"]').click(function () {
            if($(this).val() == 1){
                el.find('.upload').hide();
            }else{
                el.find('.upload').show();
            }
        })
        if(d.video_poster == 1){
            el.find('.upload').hide();
            el.find('#poster_1').prop('checked',true);
        }else if(d.video_poster == 2){
            el.find('.upload').show();
            el.find('#poster_2').prop('checked',true);
        }else{
            el.find('#poster_1').prop('checked',true);
        }
        el.find(".add_image").click(function(){
            var that = $(this);
            $.jImageManager({
                img_width:355,
                img_height:200,
                ok_cb:function(img_arr){
                    var path = img_arr[0].img_url;
                    if (path == undefined) {
                        path = img_arr[0].url;
                    }
                    $(".module_body .data_item .image").attr('src',path);
                    hasSaved = false;
                    that.find('.image').css("width","90px");
                    that.find('.image').css("height","90px");
                    el.find(".add_image").css("background","none");
                    manager.change_show_module();
                }
            });
        });
        el.on('input propertychange change','input',function () {
            manager.change_show_module();
        });
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_video").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.video_url = el.find('[name="video"]').val();
        d.video_img = el.find('[name="video_img"]').val();
        d.video_size = el.find('[name="video_size"]').val();
        d.video_width = el.find('[name="video_width"]').val();
        d.video_height = el.find('[name="video_height"]').val();
        d.video_title = el.find('input[name="video_title"]').val();
        d.video_poster = el.find('input[name="video_poster"]:checked').val();
        d.img_url = el.find(".image").attr("src");
        return d;
    },
    selectVideo: function (obj) {
        var obj = $(obj);
        $.jVideoManager({
            ok_cb: function (video) {
                video = video[0];
                console.log(video);
                obj.find('img').attr("src", video.snapshot_url);
                obj.find("input[name='video']").val(video.url);
                obj.find("input[name='video_size']").val(parseFloat(video.video_size / 1024 / 1024).toFixed(2));
                obj.find("input[name='video_img']").val(video.snapshot_url);
                obj.find("input[name='video_width']").val(video.video_width);
                obj.find("input[name='video_height']").val(video.video_height);
                //obj.parent().find(".btn_playa").attr('href',video.url);
                //obj.parent().find(".btn_playa").css("display","block");
                obj.find("img").css("width", "100%");
                obj.find("img").css("height", "100%");
                obj.find("img").css("marginTop", "0px");
                manager.change_show_module();
            }
        });
    }
};


var m_integral = {
    init_ev_el: function (el, data) {
        if (is_integral == 0) {
            el.find('.no_use').show();
        }
        console.log(data);
        var operateClass = 'integral_default_ul';
        if (data.integral_goods && data.integral_goods.length > 0) {
            if (data.list_styles == 2) {
                el.find('.integral_default_ul').html('');
                el.find('.integral_ul_clone').addClass('hide')
                el.find('.integral_default_ul').removeClass('hide');
                for (var i in data.integral_goods) {
                    var integral_single = el.find('.integral_template').find('.double_act').clone();
                    el.find('.integral_default_ul').append(integral_single);
                }
                operateClass = 'integral_default_ul';
            } else {
                el.find('.integral_ul_clone').html('');
                el.find('.integral_default_ul').addClass('hide');
                el.find('.integral_ul_clone').removeClass('hide');
                for (var j in data.integral_goods) {
                    var integral_double = el.find('.integral_template').find('.single_acts').clone();
                    el.find('.integral_ul_clone').append(integral_double);
                }
                operateClass = 'integral_ul_clone';
            }
            el.find('.' + operateClass).find('li').each(function (key, value) {
                $(value).find('.integral_default_img img').attr('src', data.integral_goods[key].goods_img);
                $(value).find('.goods_namess').text(data.integral_goods[key].goods_name);
                $(value).find('.integral_price .score').text(data.integral_goods[key].score);
                if (data.integral_goods[key].money != 'undefined' && data.integral_goods[key].money != '' && data.integral_goods[key].money > 0){
                    $(value).find('.integral_price .money_price').show();
                    $(value).find('.integral_price .money_price em').text(data.integral_goods[key].money)
                } else {
                    $(value).find('.integral_price .money_price').hide();
                }
                if (data.show_goods_price) {
                    $(value).find('.orignakl_orice').show();
                    $(value).find('.orignakl_orice').text('￥' + data.integral_goods[key].prd_price);
                } else {
                    $(value).find('.orignakl_orice').hide();
                }
            });
        }
    },

    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        m_integral.selectIntegralList(el, d);
        m_integral.delIntegral(el, d);
        if (d.list_styles == undefined) {
            el.find('input[name="list_styles"]').eq(1).prop('checked', true);
        } else {
            el.find('input[name="list_styles"][value="' + d.list_styles + '"]').prop('checked', true);
        }
        el.find('input[name="show_goods_price"]').prop('checked', d.show_goods_price);
        if (d.integral_goods && d.integral_goods.length > 0) {
            el.find('.integral_goods_tb').show();
            var integral_goods_json = JSON.stringify(d.integral_goods);
            el.find('.integral_goods_tb').attr('integral_goods_json', integral_goods_json);
            for (var i in d.integral_goods) {
                var integral_select_tr = el.find('.integral_table_clone').find('.integral_select_tr').clone();
                el.find('.integral_goods_tb').find('.integral_select_th').after(integral_select_tr);
            }
            el.find('.integral_goods_tb').find('.integral_select_tr').each(function (i, v) {
                $(v).find('.integral_select_img').attr('src', d.integral_goods[i].goods_img);
                $(v).find('.integral_select_name').text(d.integral_goods[i].goods_name);
                $(v).find('.integral_select_name').attr('title',d.integral_goods[i].goods_name);
                $(v).find('.integral_original').text('价格：'+ d.integral_goods[i].prd_price);
                $(v).find('td').eq(1).text(d.integral_goods[i].stock_sum);
                if(d.integral_goods[i].money != 'undefined' && d.integral_goods[i].money != '' && d.integral_goods[i].money > 0){
                    $(v).find('td').eq(2).text('￥'+ d.integral_goods[i].money + ' + ' +d.integral_goods[i].score + '积分');
                } else {
                    $(v).find('td').eq(2).text(d.integral_goods[i].score + '积分');
                }
                /*$(v).find('td').eq(4).text(d.integral_goods[i].start_time);
                $(v).find('td').eq(5).text(d.integral_goods[i].end_time);*/
                $(v).find('.integral_select_del').attr("goods_id", d.integral_goods[i].goods_id);
                $(v).find('.integral_select_del').attr("act_id", d.integral_goods[i].integral_goods_id);
                console.log(d.integral_goods[i]);
                var now_time = new Date();
                var now_time_s = now_time.getTime()
                var end_time =  new Date(d.integral_goods[i].end_time);
                var end_time_s = end_time.getTime();
                var ne_total = (end_time_s - now_time_s)/1000;
                if(d.integral_goods[i].act_status === 0 || ne_total < 0 && d.integral_goods[i].act_del_flag != 1){
                    $(v).find('td').eq(3).text('活动停用')
                } else if(d.integral_goods[i].act_del_flag === 1){
                    $(v).find('td').eq(3).text('活动删除')
                } else if(d.integral_goods[i].goods_is_delete === 1){
                    $(v).find('td').eq(3).text('商品删除')
                } else if(d.integral_goods[i].goods_is_on_sale === 0){
                    $(v).find('td').eq(3).text('商品下架')
                }
            });
            el.find('.integral_goods_tb').attr('integral_goods_json', JSON.stringify(d.integral_goods));
        }

        el.find("input").change(function () {
            manager.change_show_module();
        })
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_integral").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        console.log(el.find('.integral_goods_tb').attr('integral_goods_json'));
        if (el.find('.integral_goods_tb').attr('integral_goods_json')) {
            d.integral_goods = JSON.parse(el.find('.integral_goods_tb').attr('integral_goods_json'));
        }
        d.list_styles = el.find('input[name="list_styles"]:checked').val();
        d.show_goods_price = el.find('input[name="show_goods_price"]').prop('checked');
        return d;
    },
    selectIntegralList: function (el, d) {
        el.find(".goods_adds").click(function () {
            layui.use('layer', function () {
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['选择积分活动', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['825px', '420px']
                    , content: '/admin/frame/market/integral/mall/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , success: function (layero, index) {
                        var iframe = layer.getChildFrame('body', index);
                        var iframe_tr = iframe.find('.goods_tb').find('tbody').find('tr');
                        var acr_i = 0;
                        iframe_tr.click(function () {
                            var flag_back = $(this).attr('data-back');
                            if (flag_back == 'true') {
                                acr_i += 1;
                                if (acr_i >= 7) {
                                    util.mobile_alert('最多只能添加6个商品');
                                    acr_i = 6;
                                    return;
                                }
                                $(this).addClass('goods_tr_choose');
                                $(this).attr('data-back', 'false');
                                flag_back = 'false';
                            } else if (flag_back == 'false') {
                                acr_i -= 1;
                                $(this).removeClass('goods_tr_choose');
                                $(this).attr('data-back', 'true');
                                flag_back = 'true';
                            }
                        });
                        if (el.find('.integral_goods_tb').find('.integral_select_tr').size() > 0) {
                            acr_i = el.find('.integral_goods_tb').find('.integral_select_tr').size();
                            el.find('.integral_goods_tb').find('.integral_select_tr').each(function () {
                                var _this = $(this);
                                iframe.find('.goods_tb').find('tr').each(function () {
                                    if ($(this).attr('goods_id') == _this.find('.integral_select_del').attr('goods_id')) {
                                        $(this).addClass('goods_tr_choose');
                                        $(this).attr('data-back', 'false');
                                    }
                                });
                            });
                        }
                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var iframe_tr = iframe.find('.goods_tr_choose');
                        if (iframe_tr.length >= 7) {
                            util.mobile_alert('最多只能添加6个商品');
                            return;
                        }
                        var integral_goods = [];
                        el.find('.integral_goods_tb').find('.integral_select_tr').remove();
                        $.each(iframe_tr, function (idx, val) {
                            integral_goods[idx] = {};
                            integral_goods[idx].goods_id = $(val).attr('goods_id');
                            integral_goods[idx].integral_goods_id = $(val).attr('act_id');
                            integral_goods[idx].goods_img = $(val).find('.goods_img').find('img').attr('src');
                            integral_goods[idx].goods_name = $(val).find('.goods_name').text();
                            integral_goods[idx].stock_sum = $(val).find('td').eq(1).text();
                            integral_goods[idx].prd_price = $(val).find('td').eq(2).text();
                            integral_goods[idx].money = $(val).find('td').eq(3).text();
                            integral_goods[idx].score = $(val).find('td').eq(4).text();
                            integral_goods[idx].start_time = $(val).find('td').eq(5).text();
                            integral_goods[idx].end_time = $(val).find('td').eq(6).text();
                            integral_goods[idx].is_on_sale = $(val).find('td').eq(5).attr('is_on_sale');
                            integral_goods[idx].is_delete = $(val).find('td').eq(5).attr('is_delete');
                            var integral_select_tr = el.find('.integral_table_clone').find('.integral_select_tr').clone();
                            el.find('.integral_goods_tb').find('.integral_select_th').after(integral_select_tr);
                            var integral_goods_json = JSON.stringify(integral_goods);
                            el.find('.integral_goods_tb').attr('integral_goods_json', integral_goods_json);
                        });
                        el.find('.integral_goods_tb').show();
                        el.find('.integral_goods_tb').find('.integral_select_tr').each(function (i, v) {
                            console.log(integral_goods);
                            $(v).find('.integral_select_img').attr('src', integral_goods[i].goods_img);
                            $(v).find('.integral_select_name').text(integral_goods[i].goods_name);
                            $(v).find('.integral_select_name').attr('title',integral_goods[i].goods_name);
                            $(v).find('.integral_original').text('价格：'+ integral_goods[i].prd_price);
                            $(v).find('td').eq(1).text(integral_goods[i].stock_sum);
                            if(integral_goods[i].money != 'undefined' && integral_goods[i].money != '' && integral_goods[i].money > 0){
                                $(v).find('td').eq(2).text('￥'+ integral_goods[i].money + ' + ' +integral_goods[i].score + '积分');
                            } else {
                                $(v).find('td').eq(2).text(integral_goods[i].score + '积分');
                            }
                            /*$(v).find('td').eq(4).text(integral_goods[i].start_time);
                            $(v).find('td').eq(5).text(integral_goods[i].end_time);*/
                            $(v).find('.integral_select_del').attr("goods_id", integral_goods[i].goods_id);
                            $(v).find('.integral_select_del').attr("act_id", integral_goods[i].integral_goods_id);
                            if(integral_goods[i].is_delete === "1"){
                                $(v).find('td').eq(3).text('商品删除')
                            } else if(integral_goods[i].is_on_sale === "0" ){
                                $(v).find('td').eq(3).text('商品下架')
                            }
                        });
                        manager.change_show_module();
                        hasSaved = false;
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                    }
                });
            });
        });
    },
    delIntegral: function (el, d) {
        el.find('.integral_goods_tb').on('click', '.integral_select_del', function () {
            var integral_arr = JSON.parse(el.find('.integral_goods_tb').attr('integral_goods_json'));
            for (var i in integral_arr) {
                if (integral_arr[i].goods_id == $(this).attr('goods_id')) {
                    integral_arr.splice(i, 1);
                }
            }
            el.find('.integral_goods_tb').attr('integral_goods_json', JSON.stringify(integral_arr));
            $(this).parent().parent().remove();
            if (integral_arr.length == 0) {
                el.find('.integral_goods_tb').hide();
            }
            manager.change_show_module();
            hasSaved = false;
        });
    }

};


var m_qq = {
    init_ev_el: function (el, data) {
        el.find(".qq_number").text(data.title);
    },

    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        el.find("#title").val(d.title);
        el.find("[name='recommend_type']").change();
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_qq").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.title = el.find("#title").val();
        return d;
    }
};


var m_wx = {
    init_ev_el: function (el, data) {
        el.find(".wx_number").text(data.title);
    },

    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        el.find("#title").val(d.title);
        el.find("[name='recommend_type']").change();
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_wx").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.title = el.find("#title").val();
        return d;
    }
};

var m_official_accounts = {
    // no_edit:true,
    init_ev_el: function(el,data){
        util.ajax_json("/admin/official/get", function (d) {
            var officialInfo = d.content;
            var e = $("#template_list .m_official_accounts .official_accounts");
            if(officialInfo.authShopName){
                el.find('.mini_title').text(officialInfo.authShopName);
            }
            if(officialInfo.officialAccountsName){
                el.find('.official_accounts_name').text(officialInfo.officialAccountsName);
            }
        });
    },
    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
    },
    show_edit_el:function(data){
        var el = $("#template_list .d_m_official_accounts").clone();
        // console.log('13214');
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        $("#module_edit").show();
    },
    get_data: function(){
        return {};
    }
};

var m_shop_announce = {
    init_ev_el: function (el, data) {
      el.find(".shop_announce").css('background',data.bg_color);
      el.find(".shop_announce span").text(data.shop_text);
      el.find(".shop_announce span").css('color',data.font_color);
    },

    fill_edit_el: function (el, d) {
        el.attr('cur_idx', d.cur_idx);
        el.attr('module_name', d.module_name);
        el.find('.shop_text').val(d.shop_text);
        if(d.font_color == undefined){
            el.find('[name="fonts_color"]').val('#333333');
        }else{
            el.find('[name="fonts_color"]').val(d.font_color);
        }
        if(d.bg_color == undefined){
            el.find('[name="bgs_color"]').val('#fcf9dd');
        }else{
            el.find('[name="bgs_color"]').val(d.bg_color);
        }

        if(d.title_link) el.find('#title_link').val(d.title_link);
        el.find(".select_links").click(function(){
            hasSaved = false;
            show_links_dlg(function(url){
                $(".module_body .data_item #title_link").val(url);
            });
        });
        el.find("input").change(function () {
            manager.change_show_module();
        })
    },

    show_edit_el: function (data) {
        var el = $("#template_list .d_m_shop_announce").clone();
        $(".module_body").html("");
        el.appendTo($(".module_body"));
        this.fill_edit_el(el, data);
        $("#module_edit").show();
    },
    get_data: function () {
        var el = $(".module_body .data_item");
        var cur_idx = parseInt(el.attr('cur_idx'));
        var d = {cur_idx: cur_idx, 'module_name': el.attr('module_name')};
        d.shop_text = el.find('.shop_text').val();
        d.font_color = el.find('[name="fonts_color"]').val();
        d.bg_color = el.find('[name="bgs_color"]').val();
        d.title_link = el.find('[name="title_link"]').val();
        return d;
    }
};


 var m_goods_search = {
     init_ev_el: function (el, data) {
         if(data.search_style == 0){
            el.find(".goods_search_module .panel").css("border-radius",'5px')
         }else{
             el.find(".goods_search_module .panel").css("border-radius",'30px')
         }
         if(data.search_font == 0){
             el.find(".goods_search_module .panel").css("height",'40px');
             el.find(".goods_search_module .panel").css("line-height",'31px');
         }else if(data.search_font == 1){
             el.find(".goods_search_module .panel").css("height",'34px');
             el.find(".goods_search_module .panel").css("line-height",'27px');
         }else{
             el.find(".goods_search_module .panel").css("height",'28px');
             el.find(".goods_search_module .panel").css("line-height",'24px');
         }
         if(data.box_color){
             el.css("background-color",data.box_color);
         }
         if(data.back_color){
             el.find(".panel").css("background-color",data.back_color);
         }
     },

     fill_edit_el: function (el, d) {
         el.attr('cur_idx', d.cur_idx);
         el.attr('module_name', d.module_name);
         console.log(el)
         d.search_font = d.search_font != undefined? d.search_font: 1
         if(d.search_style == 0){
             el.find("#square").prop("checked",true);
         }else{
             el.find("#circle").prop("checked",true);
         }
         if(d.search_font == 0){
             el.find("#high").prop("checked",true);
         }else if(d.search_font == 1){
             el.find("#middle").prop("checked",true);
         }else{
             el.find("#low").prop("checked",true);
         }
         if(d.box_color){
             el.find("input[name='box_color']").val(d.box_color);
         }
         if(d.back_color){
             el.find("input[name='back_color']").val(d.back_color);
         }
         el.find('input[name="search_style"]').click(function () {
             if($(this).val() == 0){
                 el.find("#square").prop("checked",true);
             }else{
                 el.find("#circle").prop("checked",true);
             }
         })
         el.find('input[name="search_font"]').click(function () {
             if($(this).val() == 0){
                 el.find("#high").prop("checked",true);
             }else if($(this).val() == 1){
                 el.find("#middle").prop("checked",true);
             }else{
                 el.find("#low").prop("checked",true);
             }
         })
         el.find("input").change(function () {
             manager.change_show_module();
         })

     },

     show_edit_el: function (data) {
         var el = $("#template_list .d_m_goods_search").clone();
         $(".module_body").html("");
         el.appendTo($(".module_body"));
         this.fill_edit_el(el, data);
         $("#module_edit").show();
     },
     get_data: function () {
         var el = $(".module_body .data_item");
         var cur_idx = parseInt(el.attr('cur_idx'));
         var d = {cur_idx:cur_idx,'module_name':el.attr('module_name')};
         d.search_style = el.find("input[name='search_style']:checked").val();
         d.search_font = el.find("input[name='search_font']:checked").val();
         d.box_color = el.find("input[name='box_color']").val();
         d.back_color = el.find("input[name='back_color']").val();
         return d;
     }
 };