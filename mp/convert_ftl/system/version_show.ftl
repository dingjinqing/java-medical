<#include ("system.header")
<link href="/css/system/version_show.css?v=1.0.0" rel="stylesheet" type="text/css">
<style>
    .btn_save{
        margin-left: 100px;
        cursor: pointer;
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
    }
    .role_ul{
        width: 640px;
    }
    .role_ul li{
        width: 120px;
        height: 21px;
    }
    select{
        width: 250px;
        height: 30px;
        padding-left: 12px;
        border: 1px solid #ccc;
    }
    .version_mod{
        color: #999;
    }
    .fl_num{
        min-width:310px;
    }
</style>
<div class="container">
    <form action="/system/version/show" method="post" id="form1">
        {{csrf_field()!}
        <input type="hidden" name="content_config">
        <input type="hidden" name="shop_id" value="${shop_id!}">
        <input type="hidden" name="id" value="${id!}">
        <ul>
        <li class="container_li clearfix">
            <div class="fl">版本名称</div>
            <div class="fl_r">
                <input type="text" name="version_name" value="${version_name!}"/>
            </div>
        </li>
            <li class="container_li clearfix">
                <div class="fl">版本等级</div>
                <div class="fl_r">
                    <select name="level" id="">
                        <option value="" selected>请选择版本等级</option>
                        <option value="v1" <#if ($level == 'v1') selected </#if> <#if ($level_list && in_array('v1',$level_list) && $level != 'v1') disabled </#if>>v1 体验版</option>
                        <option value="v2" <#if ($level == 'v2') selected </#if> <#if ($level_list && in_array('v2',$level_list) && $level != 'v2') disabled </#if>>v2 基础版</option>
                        <option value="v3" <#if ($level == 'v3') selected </#if> <#if ($level_list && in_array('v3',$level_list) && $level != 'v3') disabled </#if>>v3 高级版</option>
                        <option value="v4" <#if ($level == 'v4') selected </#if> <#if ($level_list && in_array('v4',$level_list) && $level != 'v4') disabled </#if>>v4 旗舰版</option>
                    </select>
                    {{--<input type="text" name="level" value="${level!}"/>--!}
                </div>
            </li>
        <li class="clearfix">
            <div class="fl">版本权限</div>
                <div class="fl_r">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <td style="width:30%;">
                                <input type="checkbox" name="main_cbx[]" checked priv_name="first_web_manage" value="sub_0[]">
                                统计管理&nbsp;
                            </td>
                            <td>
                                <ul class=" role_ul clearfix">
                                    <#list ($version_content['main_config']['sub_0'] as $k=>$v)
                                    <li>
                                        <input type="checkbox" main="first_web_manage" name_a="two" name="sub_0[]" class="sub_0"
                                               <#if ($main_config && in_array($k,$main_config['sub_0'])) checked="true" </#if>
                                               <#if ($main_config && in_array($k,$main_config['sub_0']) && in_array($k,$shop_main_config['sub_0'])) version="1" </#if>
                                               value="${k!}" priv_name="analysis_basic">
                                        <span>${v!}</span>
                                        &nbsp;&nbsp;
                                    </li>
                                    </#list>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:30%;">
                                <input type="checkbox" name="main_cbx[]" checked="true" priv_name="first_web_decoration" value="sub_1[]">
                                商品管理&nbsp;
                            </td>
                            <td>
                                <ul class=" role_ul clearfix">
                                    <#list ($version_content['main_config']['sub_1'] as $k=>$v)
                                        <li>
                                            <input type="checkbox" main="first_web_manage" name_a="two" name="sub_1[]" class="sub_1"
                                                   <#if ($main_config && in_array($k,$main_config['sub_1'])) checked="true" </#if>
                                                   <#if ($main_config && in_array($k,$main_config['sub_1']) && in_array($k,$shop_main_config['sub_1'])) version="1" </#if>
                                                   value="${k!}" priv_name="analysis_basic">
                                            <span>${v!}</span>
                                            &nbsp;&nbsp;
                                        </li>
                                    </#list>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:30%;">
                                <input type="checkbox" name="main_cbx[]" checked="true" priv_name="first_web_decoration" value="sub_2[]">
                                小程序管理&nbsp;
                            </td>
                            <td>
                                <ul class=" role_ul clearfix">
                                    <#list ($version_content['main_config']['sub_2'] as $k=>$v)
                                        <li>
                                            <input type="checkbox" main="first_web_manage" name_a="two" name="sub_2[]" class="sub_2"
                                                   <#if ($main_config && in_array($k,$main_config['sub_2'])) checked="true" </#if>
                                                   <#if ($main_config && in_array($k,$main_config['sub_2']) && in_array($k,$shop_main_config['sub_2'])) version="1" </#if>
                                                   value="${k!}" priv_name="analysis_basic">
                                            <span>${v!}</span>
                                            &nbsp;&nbsp;
                                        </li>
                                    </#list>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:30%;">
                                <input type="checkbox" name="main_cbx[]" checked="true" priv_name="first_trade_manage" value="sub_3[]">
                                会员管理&nbsp;
                            </td>
                            <td>
                                <ul class=" role_ul clearfix">
                                    <#list ($version_content['main_config']['sub_3'] as $k=>$v)
                                        <li>
                                            <input type="checkbox" main="first_web_manage" name_a="two" name="sub_3[]" class="sub_3"
                                                   <#if ($main_config && in_array($k,$main_config['sub_3'])) checked="true" </#if>
                                                   <#if ($main_config && in_array($k,$main_config['sub_3']) && in_array($k,$shop_main_config['sub_3'])) version="1" </#if>
                                                   value="${k!}" priv_name="analysis_basic">
                                            <span>${v!}</span>
                                            &nbsp;&nbsp;
                                        </li>
                                    </#list>
                                    {{--<li>--!}
                                        {{--<input type="checkbox" main="first_trade_manage" name_a="two" name="sub_3[]" class="sub_3" checked="true" value="/admin/order/list" priv_name="order">--!}
                                        {{--<span>会员卡</span>--!}
                                        {{--&nbsp;&nbsp;--!}
                                    {{--</li>--!}
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:30%;">
                                <input type="checkbox" name="main_cbx[]" checked="true" priv_name="first_market_manage" value="sub_4[]">
                                营销管理&nbsp;
                            </td>
                            <td>
                                <ul class=" role_ul clearfix">
                                    <#list ($version_content['main_config']['sub_4'] as $k=>$v)
                                        <li>
                                            <input type="checkbox" main="first_web_manage" name_a="two" name="sub_4[]" class="sub_4"
                                                   <#if ($main_config && in_array($k,$main_config['sub_4'])) checked="true" </#if>
                                                   <#if ($main_config && in_array($k,$main_config['sub_4']) && in_array($k,$shop_main_config['sub_4'])) version="1" </#if>
                                                   value="${k!}" priv_name="analysis_basic">
                                            <span>${v!}</span>
                                            &nbsp;&nbsp;
                                        </li>
                                    </#list>
                                    {{--<li>--!}
                                        {{--<input type="checkbox" main="first_market_manage" name_a="two" name="sub_4[]" class="sub_4" checked="true" value="/admin/market/coupon/manage" priv_name="market_act">--!}
                                        {{--<span>优惠券管理</span>--!}
                                        {{--&nbsp;&nbsp;--!}
                                    {{--</li>--!}
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:30%;">
                                <input type="checkbox" name="main_cbx[]" checked="true" priv_name="store_manage" value="sub_5[]">
                                门店管理&nbsp;
                            </td>
                            <td>
                                <ul class=" role_ul clearfix">
                                    <#list ($version_content['main_config']['sub_5'] as $k=>$v)
                                        <li>
                                            <input type="checkbox" main="first_web_manage" name_a="two" name="sub_5[]" class="sub_5"
                                                   <#if ($main_config && in_array($k,$main_config['sub_5'])) checked="true" </#if>
                                                   <#if ($main_config && in_array($k,$main_config['sub_5']) && in_array($k,$shop_main_config['sub_5'])) version="1" </#if>
                                                   value="${k!}" priv_name="analysis_basic">
                                            <span>${v!}</span>
                                            &nbsp;&nbsp;
                                        </li>
                                    </#list>
                                </ul>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="clearfix num_config">
                    <div style="margin-bottom: 15px"><span style="color:#999;">数量填-1时表示不限制数量</span></div>
                    <div class="fl_num">
                        <span>图片空间大小：</span>
                        <input <#if ($shop_id) disabled </#if> type="text" name="picture_num" value="${num_config['picture_num']!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        <#if ($shop_id && $num_config['picture_num']!=-1)
                        +
                        <input type="text" name="picture_num_plus" value="${num_config_plus['picture_num_plus'] ?? 0!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        </#if>
                        M
                    </div>
                    <div class="fl_num">
                        <span>视频空间大小：</span>
                        <input <#if ($shop_id) disabled </#if> type="text" name="video_num" value="${num_config['video_num']!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        <#if ($shop_id && $num_config['video_num']!=-1)
                        +
                        <input type="text" name="video_num_plus" value="${num_config_plus['video_num_plus'] ?? 0!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        </#if>
                        M
                    </div>
                </div>
                <div class="clearfix">
                    <div class="fl_num">
                        <span>商品数量：</span>
                        <input <#if ($shop_id) disabled </#if> type="text" name="goods_num" value="${num_config['goods_num']!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        <#if ($shop_id && $num_config['goods_num']!=-1)
                        +
                        <input type="text" name="goods_num_plus" value="${num_config_plus['goods_num_plus'] ?? 0!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        </#if>
                        个
                    </div>
                    <div class="fl_num">
                        <span>门店数量：</span>
                        <input <#if ($shop_id) disabled </#if> type="text" name="store_num" value="${num_config['store_num']!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        <#if ($shop_id && $num_config['store_num']!= -1)
                        +
                        <input type="text" name="store_num_plus" value="${num_config_plus['store_num_plus'] ?? 0!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        </#if>
                        个
                    </div>
                </div>
                <div class="clearfix">
                    <div class="fl_num">
                        <span>装修页面数量：</span>
                        <input <#if ($shop_id) disabled </#if> type="text" name="decorate_num" value="${num_config['decorate_num']!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        <#if ($shop_id && $num_config['decorate_num']!=-1)
                        +
                        <input type="text" name="decorate_num_plus" value="${num_config_plus['decorate_num_plus'] ?? 0!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        </#if>
                        个
                    </div>
                    <div class="fl_num">
                        <span>表单数量：</span>
                        <input <#if ($shop_id) disabled </#if> type="text" name="form_num" value="${num_config['form_num']!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        <#if ($shop_id && $num_config['form_num'] != -1)
                        +
                        <input type="text" name="form_num_plus" value="${num_config_plus['form_num_plus'] ?? 0!}" onkeyup="value=value.replace(/[^\d-]/g,'')"/>
                        个
                        </#if>
                    </div>
                </div>
            </div>
                <div class="clearfix"></div>
                <button  class="btn_save" type="button" url="/system/version/show?act=add">保存</button>

        </li>
    </ul>
    </form>
</div>
<script>
    if("${act!}" == 'read'){
        $("#form1").find('input').attr('disabled','true');
        $("select").attr("disabled",'true');
        $(".btn_save").hide();
    }
    if("${act!}"=='add'){
        $("#form1").find("input").attr("checked","true");
    }
    if("${shop_id!}"){
        $("input[name='version_name']").attr("disabled",'true');
        $("select").attr("disabled",'true');
        $("#form1").find("li input[type='checkbox']").each(function (i,v) {
            if($(v).attr("checked") == 'checked' && $(v).attr('version') == 1){
                $(v).attr("disabled",'true');
                $(v).next().addClass("version_mod");
            }
        })
    }
    //点击二级时。默认选中一级  回头在写（保存时的验证也没写）
    $(".role_ul li input[type='checkbox']").click(function () {
        var _name = $(this).attr('name');
        ipt_check(_name);
    });
    function ipt_check(sub_n){
        //$(".role_ul li input[name='" + n + "']").click(function () {
            if($(".role_ul li input[name='" + sub_n + "']:checked").length > 0){
                $("input[value='" + sub_n + "']").prop('checked',true);
            }
        //});
    }
    var content={};
    var main={};
    var num = {};
    $(".btn_save").click(function () {
        // $("input[name='main_cbx[]']").each(function (i,v) {
        $("input[name='main_cbx[]']:checked").each(function (i,v) {
            var top = $(v).val();
            var sub = [];
            $(v).parent().next().find("."+top.substr(0,5)).each(function (j,k) {
                if($(v).parent().next().find("."+top.substr(0,5))[j].checked){
                    if( "${shop_id!}}"){
                        if($(k).attr("disabled") != 'disabled'){
                            sub.push($(k).val());
                        }
                    }else{
                        sub.push($(k).val());
                    }
                }
            })
            if(top.substr(0,5)=='sub_0'){
                main.sub_0=sub;
            }else if(top.substr(0,5)=='sub_1'){
                main.sub_1=sub;
            }else if(top.substr(0,5)=='sub_2'){
                main.sub_2=sub;
            }else if(top.substr(0,5)=='sub_3'){
                main.sub_3=sub;
            }else if(top.substr(0,5)=='sub_4'){
                main.sub_4=sub;
            }else if(top.substr(0,5)=='sub_5'){
                main.sub_5=sub;
            }else if(top.substr(0,5)=='sub_6'){
                main.sub_6=sub;
            }else if(top.substr(0,5)=='sub_7'){
                main.sub_7=sub;
            }
        })
        if("${shop_id!}"){
            num.picture_num_plus=$("input[name='picture_num_plus']").val();
            num.video_num_plus=$("input[name='video_num_plus']").val();
            num.goods_num_plus=$("input[name='goods_num_plus']").val();
            num.store_num_plus=$("input[name='store_num_plus']").val();
            num.decorate_num_plus=$("input[name='decorate_num_plus']").val();
            num.form_num_plus=$("input[name='form_num_plus']").val();
        }else{
            num.picture_num=$("input[name='picture_num']").val();
            num.video_num=$("input[name='video_num']").val();
            num.goods_num=$("input[name='goods_num']").val();
            num.store_num=$("input[name='store_num']").val();
            num.decorate_num=$("input[name='decorate_num']").val();
            num.form_num=$("input[name='form_num']").val();
        }
        content.main_config=main;
        content.num_config = num;
        var content_config = JSON.stringify(content);
        $('input[name="content_config"]').val(content_config);
        if($("input[name='version_name']").val() == '' && "${shop_id!}" == ''){
            util.mobile_alert("版本名称不能为空");
            return false;
        }
        $(".num_config input").each(function (i,v) {
            if($(v).val()<-1){
                var txt = $(v).prev().text();
                util.mobile_alert(txt+"数量不能小于-1");
                return false;
            }
        });
        if("${shop_id!}" == ''){
            if($("select").val() == ''){
                util.mobile_alert("版本等级不能为空");
                return false;
            }else{
                var data = {
                    id:$("input[name='id']").val(),
                    level:$("select").val()
                };
                util.ajax_json('/system/version/level', function (response) {
                    if (response.error == 0) {
                        console.log(content_config);
                        $('#form1').submit();
                    }else {
                        util.mobile_alert("版本等级不能为重复");
                    }
                }, data);
            }
        }else{
            $("#form1").submit();
        }


    })
</script>
<#include ("system.footer")