<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/reserve_info.css?v=1.0.3" type="text/css" />
<style type="text/css">
    body{
        padding-bottom: 40px;
    }
    .show-box-white{
        overflow: auto;
    }
    .coupon_type{
        background: #fff;
        padding:10px 0 0;
    }
    .coupon_type ul{
        list-style:none;
        background: #f5f5f5;
        width: 97%;
        margin:0 auto;
        border:1px solid #f3f3f3;
    }
    .coupon_type ul:after{
        content: '';
        display: block;
        clear: both;
    }
    .coupon_type ul li{
        float: left;
        width: 100px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        cursor: pointer;
    }
    .coupon_type ul li a{
        display: block;
        width: 100%;
        height:100%;
        color: black;
    }
    .coupon_type ul .actives{
        background: #fff;
    }
    .aui_main{
        height:420px !important;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1);
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) ;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1);
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
    .basic-table input[name="service_h"]{
        width: 80px;
    }
    .basic-table input[name="service_m"]{
        width: 80px;
    }
</style>
<div class="title">
    {{--<span>预约管理 / </span><span style="color: #666;">${title!}</span>--!}
    <span><a href="/admin/store/manage/list" style="color:  black;margin-left: 0px">门店列表（${store_info->store_name!}）</a> /</span>
    <span><a href="/admin/store/services/reserve/list?store_id=${store_id!}"  style="color:  #666;margin-left: 0px">服务管理</a></span>
    {{--<span> / ${store_info->store_name!}</span>--!}
</div>
<div class="main-container">
    <div class="coupon_type">
        <ul>
            <li class="normal_type ">
                <a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6">预约管理</a>
            </li>
            <li class="fenlie_type actives">
                <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" class="edition_type">服务管理</a>
            </li>
            <li class="fenlie_type">
                <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" class="edition_tech" title="技师管理">${technician_title!}管理</a>
            </li>
            <li class="give_to_sb">
                <a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6">评价管理</a>
            </li>
        </ul>
    </div>
    <div style="padding-left: 25px;background: #fff">
        <div class="clearfix top-banner" style="margin-left: 0;padding-top: 10px">
            <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6"  class="create-good">服务列表</a>
            <a href="/admin/store/services/service/type?store_id=${store_id!}&top_index=6"  class="create-good ">分类管理</a>
            <a href="/admin/store/services/service/add?store_id=${store_id!}&top_index=6"  class="create-good actiove">${title!}</a>
            <a href="##" class="create-good" style="display: none;">编辑服务</a>
        </div>
    </div>

    <div class="goods-box fix_every_footer">
        <div class="goods-box-top">
            <div class="box-top-info box-top-first box-top-active">1.编辑基本信息</div>
            <div class="box-top-info box-top-detail">2.编辑服务详情</div>
        </div>
        <form name="formData" action="/admin/store/services/service/add?store_id=${store_id!}"  id="form1" method="post" >
            {{ csrf_field()!}
            <input type="hidden" name="act" value="edit">
            <input type="hidden" name="store_id" value="${store_id!}">
            <input type="hidden" name="service_id" value="${service_id!}">
            <input type="hidden" name="act" value="${act!}">
            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <table width="100%" class="basic-table">
                        <tr>
                            <td ><span>*</span>服务名称：</td>
                            <td><input type="text" name="service_name" value="${data_list->service_name!}" style="width: 400px;"/></td>
                        </tr>
                        {{--<tr>--!}
                            {{--<td >服务编码：</td>--!}
                            {{--<td><input type="text" name="service_sn"  value="${data_list->service_sn!}" style="width: 400px;" /></td>
                        </tr>--!}
                        <tr>
                            <td >服务价格：</td>
                            <td>
                                <input type="text" name="service_price" value="${data_list->service_price!}" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                <span class="tips">服务详情页显示的服务价，仅显示。不填则不显示服务价格</span>
                            </td>
                        </tr>
                        <tr>
                            <td >预约订金：</td>
                            <td>
                                <input type="text" name="service_subsist"  value="${data_list->service_subsist!}" onkeyup="value=value.replace(/[^\d.]/g,'')"/>
                                <span class="tips">线上支付价格，服务详情页显示为订金字样</span>
                            </td>
                        </tr>
                        <tr>
                            <td >收费说明：</td>
                            <td>
                                {{--<input type="text" name="charge_resolve"  value="<#if ($data_list->charge_resolve) ${data_list->charge_resolve!} <#else> 预约订金包含在服务价格内，到店仅需支付剩余价格 </#if> " style="width:400px;"/>--!}
                                <input type="text" name="charge_resolve"  value="<#if ($data_list->charge_resolve) ${data_list->charge_resolve!}  </#if> " style="width:400px;"/>
                                <span class="tips">服务详情页的收费说明内容</span>
                            </td>
                        </tr>
                        <tr>
                            <td ><span>*</span>服务分类：</td>
                            <td>
                                <select name="cat_id" id="cat_id">
                                    <option value="0">选择分类</option>
                                    <#list ($cat as $item)
                                        <option value="${item->cat_id!}" <#if ($item->cat_id==$data_list->cat_id) selected </#if>>${item->cat_name!}</option>
                                    </#list>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>上下架：</td>
                            <td class="">
                                <ul class="shelves">
                                    <li class="shelves_li">
                                        <input type="radio" name="service_shelf" id="now_sale" checked value="1" />
                                        <label for="now_sale">上架</label>
                                    </li>
                                    <li class="shelves_li">
                                        <input type="radio" name="service_shelf" id="stop_sale" value="0" />
                                        <label for="stop_sale">下架</label>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><span>*</span>服务主图：</td>
                            <td class="">
                                <ul class="goods-item-img clearfix">
                                <#if ($data_list->service_img)
                                    <#list ($data_list->service_img as $item)
                                        <#if ($item)
                                        <li style="margin-bottom: 5px">
                                            <input type="hidden" name="service_img[]" value="${item!}">
                                            <img src="${item!}" class="add_img" alt="">
                                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete"  />
                                            <img src="http://${image_domain!}/image/admin/master_diagram_left.png" alt="" class="master_diagram_left" style="display: none;">
                                            <img src="http://${image_domain!}/image/admin/master_ diagram_right.png" alt="" class="master_diagram_right" style="display: none;">
                                        </li>
                                        </#if>
                                    </#list>
                                </#if>
                                    <li style="margin-bottom: 5px">
                                        <input name="service_img[]" type="hidden">
                                        <img src="http://${image_domain!}/image/admin/add_img.png" class="add_img" alt="" />
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" style="display: none;" />
                                        <img src="http://${image_domain!}/image/admin/master_diagram_left.png" alt="" class="master_diagram_left" style="display: none;">
                                        <img src="http://${image_domain!}/image/admin/master_ diagram_right.png" alt="" class="master_diagram_right" style="display: none;">
                                    </li>
                                </ul>
                                <div class="goods-suggest">建议尺寸：800*800像素</div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><span>*</span>服务模式：</td>
                            <td class="choose_service_type">
                                <div>
                                    <input type="radio" name="service_type" id="no_tech" value="0" <#if ($data_list->service_type == 0)checked </#if>>服务+时间
                                </div>
                                <div>
                                    <input type="radio" name="service_type" id="with_tech" value="1" <#if ($data_list->service_type == 1)checked </#if> > 服务+时间+${technician_title!}
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><span>*</span>可服务日期：</td>
                            <td class="reverseable_date">
                                {{--<input type="hidden" name="service_time">--!}
                                <div class="choose_date">
                                    <input type="text" onclick="picker() "id="startDate" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" name="start_date" value="${data_list->start_date!}" style="margin-right: 5px" autocomplete="off">至
                                    <input type="text" onclick="picker() "id="endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" name="end_date" value="${data_list->end_date!}" autocomplete="off">
                                </div>
                                <div class="tips_able">前端用户预约所选择的服务日期按照该限制显示</div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><span>*</span>服务时段：</td>
                            <td>
                                <input type="hidden" name="start_period">
                                <input type="hidden" name="end_period">
                                <div class="choose_period">
                                    <span class="start_hour" hidden>${store_info->begin_before!}</span>
                                    <span class="start_min" hidden>${store_info->begin_end!}</span>
                                    <span class="end_hour" hidden>${store_info->close_before!}</span>
                                    <span class="end_min" hidden>${store_info->close_end!}</span>
                                    <input type="text" act="hour" name="start_hour" value="${data_list->begin_before!}" onkeyup="value=value.replace(/[^\d]/g,'')"  /> :
                                    <input type="text" act="min" name="start_min" value="${data_list->begin_end!}" onkeyup="value=value.replace(/[^\d]/g,'')" /> -
                                    <input type="text" act="hour" name="end_hour" value="${data_list->close_before!}" onkeyup="value=value.replace(/[^\d]/g,'')"  /> :
                                    <input type="text" act="min" name="end_min" value="${data_list->close_end!}" onkeyup="value=value.replace(/[^\d]/g,'')"  />
                                </div>
                                <div class="tips_able">时间段应在门店营业时间内(营业时间：${store_info->begin_before!}:${store_info->begin_end!}-${store_info->close_before!}:${store_info->close_end!})，前端用户所选择的服务时段将按照该时段进行拆分</div>
                                {{--<div class="tips_able">前端用户所选择的服务时段将按照该时段进行拆分</div>--!}
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><span>*</span>服务时长：</td>
                            <td class="time_period">
                                <div>
                                    <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')" min="0" max="23" name="service_h" value="{{ floor($data_list->service_duration/60)!}" onblur="checkServiceTime(this)"/> 小时
                                    <input type="number" onkeyup="value=value.replace(/[^\d]/g,'')" min="0" max="59" name="service_m" value="{{ bcmod($data_list->service_duration,60)!}" onblur="checkServiceTime(this)"/> 分钟
                                </div>
                                {{--<select name="service_duration">--!}
                                    {{--<option value="15" <#if ($data_list->service_duration==15) selected </#if>>15分钟</option>--!}
                                    {{--<option value="30"  <#if ($data_list->service_duration==30) selected </#if>>30分钟</option>--!}
                                    {{--<option value="45"  <#if ($data_list->service_duration==45) selected </#if>>45分钟</option>--!}
                                    {{--<option value="60"  <#if ($data_list->service_duration==60) selected </#if>>60分钟</option>--!}
                                    {{--<option value="90"  <#if ($data_list->service_duration==90) selected </#if>>90分钟</option>--!}
                                    {{--<option value="120"  <#if ($data_list->service_duration==120) selected </#if>>120分钟</option>--!}
                                    {{--<option value="150"  <#if ($data_list->service_duration==150) selected </#if>>150分钟</option>--!}
                                    {{--<option value="180"  <#if ($data_list->service_duration==180) selected </#if>>180分钟</option>--!}
                                {{--</select>--!}

                                <div>前端用户所选择的服务时长将按照该时长进行限制</div>
                            </td>
                        </tr>
                        <tr class="no_techs">
                            <td style="vertical-align: top!important;"><span>*</span>同时段内可服务人数：</td>
                            <td>
                                <label>
                                    <input style="width: 80px;" type="text" name="services_number" value="${data_list->services_number!}" onkeyup="value=value.replace(/[^\d.]/g,'')">人
                                </label>
                                <div style="color:#999;">该服务在同一时间段内可预约的最多人数</div>
                            </td>
                        </tr>
                        <tr class="with_techs" style="display: none;">
                            <td style="vertical-align: top!important;"><span>*</span>同时段内${technician_title!}可服务人数：</td>
                            <td>
                                <label>
                                    <input style="width: 80px;" type="text" name="tech_services_number" value="${data_list->tech_services_number!}" onkeyup="value=value.replace(/[^\d.]/g,'')">人
                                </label>
                                <div style="color:#999;">该服务在同一时间段内每个${technician_title!}可以被预约的次数</div>
                            </td>
                        </tr>
                    </table>

                </div>
                <div class="goods-edit-detail clearfix">
                    <div class="detail-show fl">
                        <div class="detail-show-top">服务详情效果预览</div>
                        <div class="detail-show-box">

                            <div class="show-box-white" id="show"></div>
                        </div>
                    </div>
                    <div class="detail-edit fl">
                        <div class="detail-edit-box">
                            <input type="hidden" id="service_desc" name="service_desc" value="${data_list->content!}">
                                      <textarea id="editor" name="content" style="width:200px;height:466px;"
                                                onchange="getContent()"></textarea>
                        </div>
                    </div>
                    {{--<input value="确定" onclick="getContent()" type="button" class="btn_get">--!}
                </div>
            </div>
        </form>
    </div>
    <div class="goods-save fix_footer">
        <a  class="btn-save" onclick="getContent()">保存</a>
        <a  class="btn-next">下一步</a>
        <a  class="btn-prev" style="display: none;">上一步</a>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    var hasSaved = true;

</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/js/kindeditor/lang/{{ config("app.locale")!}.js"></script>
<script type="text/javascript" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>
<script language="JavaScript" src="/js/admin/reserve_info.js?v=1.2.1"></script>

<script type="text/javascript">
    var checked_service_name = true;

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
        //$('#editor').val(fullHtml);
        //console.log(value);
        $('#show').html(fullHtml);
    }


    $(".box-top-detail").click(function () {
        if($(".detail-edit-box #service_desc").val() != ""){
            $(".show-box-white").html($(".detail-edit-box #service_desc").val());
        }
    });

    $(".btn-next").click(function () {
        if($(".detail-edit-box #service_desc").val() != ""){
            $(".show-box-white").html($(".detail-edit-box #service_desc").val());
        }
    });

    $(".basic-table [name='service_name']").blur(function () {
        var service_name = $(this).val();
        var service_id = $('#form1 [name="service_id"]').val();
        var _this = $(this);
        util.ajax_json($('#form1').attr('action'), function (response) {
            if(response.error == 0) {
                checked_service_name = false;
                util.mobile_alert('服务名称已存在');
            } else {
                checked_service_name = true;
            }
        }, {service_id:service_id, service_name:service_name});
    });
    $('.choose_period').find('input').blur(function () {
        if($(this).val() == ""){
            util.mobile_alert("服务时段不能为空！");
            $(this).focus();
            return false;
        }
        if($(this).val() != "") {
            if($(this).attr('act') == 'hour'){
                if(Number($(this).val()) > 23 || $(this).val().length>2){
                    util.mobile_alert("请输入正确的小时数！");
                    $(this).val("");
                    $(this).focus();
                }
            }else if($(this).attr('act') == 'min'){
                if(Number($(this).val()) >= 60 || $(this).val().length>2){
                    util.mobile_alert("请输入正确的分钟数！");
                    $(this).val("");
                    $(this).focus();
                }
            }

            if($(this).val().length == 1){
                $(this).val('0'+$(this).val());
            }
        }
    });
    function checkServiceTime(that){
        if($(that).attr('name') === 'service_h'){
            if(Number($(that).val()) < 0){
                util.mobile_alert("请输入正确的小时数！");
                $(that).val("");
                $(that).focus();
            }
        } else if($(that).attr('name') === 'service_m'){
            if(Number($(that).val()) < 0 || Number($(that).val()) > 59){
                util.mobile_alert("请输入正确的分钟数！");
                $(that).val("");
                $(that).focus();
            }
        }
    }
    util.inputChange();
    util.selectChange();
    util.radioChange('service_shelf');
    util.radioChange('service_type');
    var body_html = $('#service_desc').val();
    $(document).click(function () {
        getContent();
        if(body_html != $('#editor').val()){
            //alert('修改过详情');
            hasSaved = false;
        }
    });
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
</script>
<script type="text/javascript">
    getPowerInfo('main_config','service','sub_5','服务管理',0);
    $('input[name="service_type"]').click(function(){
       if($(this).val() == 1){
           var data = data = getPowerInfo('main_config','technician','sub_5','技师管理');
           //console.log(data);
           if(data.content == -1){
               //$(this).attr('disabled','disabled');
               $(this).parent().prev().find('input').prop('checked',true);
           }
       }
    });
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

