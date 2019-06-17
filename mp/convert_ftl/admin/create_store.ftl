<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/store_manage.css?v=1.0.3">
<link rel="stylesheet" href="/js/kindeditor/themes/default/default.css"/>
<link rel="stylesheet" href="/js/kindeditor/plugins/code/prettify.css"/>
<style type="text/css">
    body{
        padding-bottom: 40px;
    }
    .aui_state_focus{
        position: absolute !important;
        top:700px !important;
    }
    .btn_save a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_save a:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .master_diagram_left, .master_diagram_right{
        position: absolute;
        bottom: 0;
    }
    .master_diagram_left{
        left: 0;
    }
    .master_diagram_right{
        right: 0;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span>{{ trans("admin/store_manage.store_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/store_manage.create_store.title")!}</span>
    </div>
    <div class="main-container fix_every_footer">
        <form action="" method="post" id="form1">
            {{ csrf_field()!}
            <input type="hidden" name="store_id" value="${data_list->store_id!}">
            <div class="create_content">
                <div class="store_names">
                    <label for="store_name"><span>*</span>门店名称：</label>
                    <input type="text" id="store_name" placeholder="输入名称" name="store_name" value="${data_list->store_name!}">
                </div>
                <div>
                    <label for="pos_shop_id"><span></span>门店编码：</label>
                    <input type="text" placeholder="门店编码" name="pos_shop_id" value="<#if  (!empty($data_list->pos_shop_id)) ${data_list->pos_shop_id!} </#if>">
                </div>
                <div class="header_persons">
                    <label for="leader"><span>*</span>负责人：</label>
                    <input type="text" placeholder="输入负责人" id="leader" name="manager" value="${data_list->manager!}">
                </div>
                <div class="contact_phones">
                    <label for="contact_phone"><span>*</span>联系电话：</label>
                    <input type="text" onkeyup="value=value.replace(/[^\d\-]/g,'')" id="contact_phone" placeholder="输入联系电话" name="mobile" value="${data_list->mobile!}">
                </div>

                <div class="business_state">
                    <p><span style="width: 7px;display: inline-block;"></span>营业状态：</p>
                    <p class="check_radio">
                        <label for="on_business">
                            <input type="radio" id="on_business" name="business_state" value="1" <#if ($data_list->business_state) checked </#if>>营业中
                        </label>
                        <label for="not_business">
                            <input type="radio" id="not_business" name="business_state" value="0" <#if ($data_list->business_state==0) checked </#if>>关店
                        </label>
                    </p>
                </div>
                <div class="business_time">
                    <input type="hidden" name="opening_time" value="${data_list->opening_time!}">
                    <input type="hidden" name="close_time" value="${data_list->close_time!}">
                    <p><span>*</span>营业时间：</p>
                    <p class="check_radio_time">
                        <label for="everyday">
                            <input type="radio" id="everyday" name="business_type" value="1" <#if ($data_list->business_type) checked </#if> />每天
                        </label>
                        <label for="weekday">
                            <input type="radio" id="weekday" name="business_type" value="0" <#if ($data_list->business_type==0) checked </#if> />工作日
                        </label><br>
                        <p class="set_times">
                            <input type="text" class="begin_before" value="${data_list->begin_before!}" onkeyup="value=value.replace(/[^\d]/g,'')" />:
                            <input type="text" class="begin_end" value="${data_list->begin_end!}" onkeyup="value=value.replace(/[^\d]/g,'')" />-
                            <input type="text" class="close_before" value="${data_list->close_before!}" onkeyup="value=value.replace(/[^\d]/g,'')" />:
                            <input type="text" class="close_end" value="${data_list->close_end!}" onkeyup="value=value.replace(/[^\d]/g,'')" />

                        </p>
                    </p>
                </div>
                <div class="time_notice">24小时制，如9：20-10：50</div>
                <div class="own_group">
                    <label><span style="width: 7px;display: inline-block;"></span>所属分组：</label>
                    <select name="group">
                        <option value="">请选择分组</option>
                        <#list ($group as $item)
                            <option value="${item->group_id!}" <#if ($data_list->group==$item->group_id) selected </#if>>${item->group_name!}</option>
                        </#list>
                    </select>
                    &nbsp;
                    <a href="javascript:void(0)" class="refresh" style="color: #5a8bff"> 刷新 </a>
                    &nbsp;&nbsp;|
                    <a href="/admin/store/group/list" class="btn_add_group store_add_group" target="_blank">添加新分组</a>
                </div>
                <div class="positions">
                    <label><span>*</span>地理位置：</label>
                    <select name="province_code" id="">
                        <#list ($province as $item)
                            <option value="${item->province_id!}" <#if ($data_list->province_code==$item->province_id) selected </#if>>${item->name!}</option>
                        </#list>
                    </select>
                    <select name="city_code" id="">
                        <#list ($city as $item)
                            <option value="${item->city_id!}" <#if ($data_list->city_code==$item->city_id) selected </#if>>${item->name!}</option>
                        </#list>
                    </select>
                    <select name="district_code" id="">
                        <#list ($district as $item)
                            <option value="${item->district_id!}" <#if ($data_list->district_code==$item->district_id) selected </#if>>${item->name!}</option>
                        </#list>
                    </select>
                </div>
                <div class="map_to_locate">
                    <input type="hidden" name="latitude" value="${data_list->latitude!}">
                    <input type="hidden" name="longitude" value="${data_list->longitude!}">
                    <label for="keyword"><span>*</span>地图定位：</label>
                    <input type="text" placeholder="输入详细的位置信息，请勿重复填写省市区" id="keyword" name="address" value="${data_list->address!}">
                    <a href="##" class="btn_search_location" onclick="codeAddress()">地图定位</a>
                    <div id="container"></div>
                </div>

                <div class="special_service">
                    <p style="width: 86px"><span style="width: 7px;display: inline-block;"></span>特色服务：</p>
                    <#if ($data_list->service)
                        <#list ($data_list->service as $item)
                            <p><input type="checkbox" name="special_service[]" value="${item!}" checked>${item!}</p>
                        </#list>
                    <#else>
                        <p><input type="checkbox" name="special_service[]" value="WIFI">WIFI</p>
                        <p><input type="checkbox" name="special_service[]" value="停车位">停车位</p>
                        <p><input type="checkbox" name="special_service[]" value="无烟区">无烟区</p>
                        <p><input type="checkbox" name="special_service[]" value="茶水小食">茶水小食</p>
                        <p><input type="checkbox" name="special_service[]" value="包厢">包厢</p>
                    </#if>
                </div>
                <div class="add_service">
                    <input type="text" placeholder="添加多个特色服务，请用逗号隔开">
                    <a href="##" class="btn_add_services">添加</a>
                </div>
                <div class="publicize_imgs">
                    <label><span style="width: 7px;display: inline-block;">*</span>门店宣传图：</label>
                    <div class="notice">
                        <#if ($data_list->store_img)
                            <#list ($data_list->store_img as $item)
                                <#if ($item)
                                <div class="notice_imgs">
                                    <input type="hidden" name="store_img[]" value="${item!}">
                                    <img src="${item!}" class="add_img" alt="">
                                    <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_imgs">
                                    <img src="http://${image_domain!}/image/admin/master_diagram_left.png" alt="" class="master_diagram_left" style="display: none;">
                                    <img src="http://${image_domain!}/image/admin/master_ diagram_right.png" alt="" class="master_diagram_right" style="display: none;">
                                </div>
                                </#if>
                            </#list>
                        </#if>
                            <div class="notice_imgs">
                                <input type="hidden" name="store_img[]" value="" />
                                <img src="http://${image_domain!}/image/admin/add_img.png" class="add_img" alt="" />
                                <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_imgs" style="display: none;" />
                                <img src="http://${image_domain!}/image/admin/master_diagram_left.png" alt="" class="master_diagram_left" style="display: none;">
                                <img src="http://${image_domain!}/image/admin/master_ diagram_right.png" alt="" class="master_diagram_right" style="display: none;">
                            </div>
                    </div>
                </div>
                <div class="notice_word">最多可上传6张图，每张大小不可超过5M，格式要求为jpg，png</div>
                <div class="store_detail">
                    <label for=""><span style="width: 7px;display: inline-block;"></span>店面详情：</label>
                    <div style="margin-left: 7px" class="detail-edit-box">
                        <input type="hidden" name="content" class="content" value="${data_list->content!}" />
                        <textarea id="editor" name="content" style="width:200px;height:466px;" onchange="getContent()"></textarea>
                    </div>
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer">
            <a href="##">保存</a>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    var hasSaved = true;
    //版本控制
    var num = '${version['num']!}';
    var use = '${version['use']!}';
    var store_id = util.getUrlParam('store_id');
    if(store_id > 0){//编辑
        if(num>=0 && num-use < 0){
            window.location.href = '/admin/authority/not?mod_name=门店数量';
        }
    }else{//新建
        if(num>=0 && num-use <= 0){
            window.location.href = '/admin/authority/not?mod_name=门店数量';
        }
    }
    $(".refresh").click(function () {
        util.ajax_json("/admin/ajax/store/group",function(res){
            if(res != -1){
                $('select[name="group"]').html("");
                var opt = '<option value="">请选择分组</option>';
                for(var i=0;i<res.length;i++){
                    opt += "<option value='"+res[i].group_id+"'>"+res[i].group_name+"</option>"
                }
                $('select[name="group"]').html(opt);
                util.mobile_alert("刷新成功");
            }else{
                util.mobile_alert("刷新成功");
            }
        });
    })
</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/js/kindeditor/lang/{{ config("app.locale")!}.js"></script>
<script type="text/javascript" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>
<script type="text/javascript" src="/js/admin/store_manage.js?v=1.1.1"></script>


{{--地图的js--!}
<script charset="utf-8" src="https://map.qq.com/api/js?v=2.exp&key=GO6BZ-EUL6P-ZLPDD-VYOYE-CCZBH-UFBOU"></script>
<script type="text/javascript">
    

    var geocoder,map,marker = null;
    var markersArray = [];
    //清除之前的标记点
    function clearOverlays() {
        if (markersArray) {
            for (i in markersArray) {
                markersArray[i].setMap(null);
            }
        }
    }
    var init = function() {
        var center = new qq.maps.LatLng(39.916527,116.397128);
        map = new qq.maps.Map(document.getElementById('container'),{
            center: center,
            zoom: 15
        });


        //调用地址解析类
        geocoder = new qq.maps.Geocoder({
            complete : function(result){
                map.setCenter(result.detail.location);
                var marker = new qq.maps.Marker({
                    map:map,
                    position: result.detail.location
                });
                markersArray.push(marker);
                var latitude = marker.map.center.lat;//纬度
                var longitude = marker.map.center.lng;//经度
                var latlngs = marker.map.center;//经纬度
                $("input[name='latitude']").val(latitude);
                $("input[name='longitude']").val(longitude);
            }
        });

        //添加监听事件   获取鼠标单击事件  点击地图获取点的坐标
        qq.maps.event.addListener(map, 'click', function(event) {
            clearOverlays();
            var marker=new qq.maps.Marker({
                position:event.latLng,
                map:map
            });
            qq.maps.event.addListener(map, 'click', function(event) {
                marker.setMap(null);
            });
            var latitude = marker.position.lat;//纬度
            var longitude = marker.position.lng;//经度
            $("input[name='latitude']").val(latitude);
            $("input[name='longitude']").val(longitude);
        });
    };


    /*解析地址*/
    function codeAddress() {
        var address = document.getElementById("keyword").value;
        var city_value = $("select[name='city_code'] option:selected").text();
        var district_value = $("select[name='district_code'] option:selected").text();
        //通过getLocation();方法获取位置信息值
        clearOverlays();
        geocoder.getLocation(city_value+district_value+address);
    }

    //获取城市列表接口设置中心点
    citylocation = new qq.maps.CityService({
        complete : function(result){
            map.setCenter(result.detail.latLng);
        }
    });
    //调用searchLocalCity();方法    根据用户IP查询城市信息。
    citylocation.searchLocalCity();
    $("body").onload = init();
</script>
<script>
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
</script>
<script type="text/javascript">
    /*文本编辑器*/
//    var editor;
//    KindEditor.ready(function(K) {
//        editor = K.create('.content', {
//            resizeType : 1,
//            items : [
//                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
//                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
//                'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
//            afterBlur:function(){this.sync();}
//        });
//
//    });
//    KindEditor.options.cssData = 'body {background: ;}';

    function getContent() {
        var fullHtml = '';
        var div;
        var value;
        KindEditor.ready(function(K) {
            var editor = KindEditor.instances[0];
            fullHtml = editor.fullHtml();
            div = K.query('.ke-edit-iframe');
            //console.log(div);
            editor.sync();
            //value = K('#editor').val();
            //var cmd = K.cmd(document);
        });
        console.log($('#editor').val());

    }

    if($("#keyword").val() != ""){
        if($("input[name='latitude']").val() != "" && $("input[name='longitude']").val() != ""){
            $(".btn_search_location").click();
        }
    }

    $(document).ready(function () {
        initKindEditor("#editor", function () {
            window.keditor.html($(".content").val());
        });
    });

    util.inputChange();
    util.selectChange();
    var body_html = $('.content').val();
    var checkbox_len = $('input[name="special_service[]"]:checked').length;
    $(document).click(function () {
        getContent();
        if(body_html != $('#editor').val()){
            //alert('修改过详情');
            hasSaved = false;
        }
        if(checkbox_len != $('input[name="special_service[]"]:checked').length){
            //alert('修改过checkbox');
            hasSaved = false;
        }
    });
    util.radioChange('business_state');
    util.radioChange('business_type');
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
    $(function(){
        $(document).on('mouseenter','.goods-item-img li',function(){
            if($(this).index() != ($('.goods-item-img li').length - 1)){
                $(this).find('img.master_diagram_left').show()
                $(this).find('img.master_diagram_right').show()
            }      
        })
        $(document).on('mouseleave','.goods-item-img li',function(){
            $(this).find('img.master_diagram_left').hide()
            $(this).find('img.master_diagram_right').hide()
        })
    })
</script>

