<#include "/admin/header.ftl">
<link href="/css/admin/admin_deco_style.css?v=1.0.73" type="text/css" rel="stylesheet">
<link href="/css/admin/new_form.css?v=1.0.5" rel="stylesheet">
<link href="/css/admin/form_decorate.css?v=1.0.79" rel="stylesheet">
<input type="hidden" id="page_id" name="page_id" value="${page->page_id!}">
{{--<input type="hidden" id="page_type" name="page_type" value="${page->page_type!}">--!}
{{--<input type="hidden" id="page_tpl_type" name="page_tpl_type" value="${page->page_tpl_type!}">--!}
<input type="hidden" id="page_content" name="page_content" value="${page->page_content!}">
<input type="hidden" id="form_cfg" name="form_cfg" value="{{ json_encode($page->form_cfg)!}">
<input type="hidden" id="page_state" name="page_state" value="${page->state!}">
<style type="text/css">
    #page_name{
        border:1px solid #eee;
        width: 150px;
        padding-left: 12px;
    }
    .btn-group>.btn{
        float: none;
    }
    .btn-group #save_content{
        margin:0 !important;
    }
    .btn-group #fabue_content{
        background: #f5f5f5;
        color: #a7a7a7;
        margin:0 5px !important;
        border:1px solid #eee;
        cursor: pointer;
    }
    .alert-errror{
        border-left:4px solid #8ac38b;
    }
    .btn_go_back{
        background: #5a8bff;
        color: #fff;
        border:1px solid #5a8bff;
    }
    .btn_go_back:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .btn_go_back:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .add_images {
        border: 2px solid #eee;
        width: 75px;
        height: 75px;
        text-align: center;
        padding-top: 15px;
    }
    #save_content{
        background-color: #5a8bff;
        border: 1px solid #5A8BFF;
    }
    #save_content:hover{
        background-color: #447af9;
        border: 1px solid #447af9;
    }
    #publish_content{
        background-color:#f5f5f5;
        color: #666;
        border: 1px solid #dddddd;
    }
    #publish_content:hover{
        color: #5a8bff;
        border: 1px solid #5a8bff;
    }

    .item_operation img{
        height: 25px;
    }

</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4" >营销管理</a> / </span>
    <span><a href="/admin/market/form/list?top_index=4">表单统计</a> / </span>
    <span style="color: #666;"><#if ($page->state==1)查看<#else>添加</#if>表单</span>
</div>
<#if  ($page->state == 1)
    <div class="box alert alert-errror">
        已发布的表单只可查看，不可编辑。
    </div>
</#if>
<div class="box decoreate_content fix_every_footer" style="min-height:716px;margin: 0 1%;">
    <div class="col-sm-3" style="width:240px;margin:2px;padding: 0 2px;">
        <div class="panel-group" id="accordion">
            <div class="panel panel-default">
                <div class="panel-heading panel-title" data-parent="#accordion" href="#collapse0" style="cursor: default;">
                    表单元素
                </div>
                <div id="collapse0" class="panel-collapse in" style="height: auto;">
                    <div class="panel-body drag-module-list">
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_input_name" title="姓名">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_name.png" alt="">
                            <div>姓名</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_input_mobile" title="手机号">
                            <img src="http://${image_domain!}/image/admin/drag_phone.png" alt="">
                            <div>手机号</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_address" title="省市区">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_area.png" alt="">
                            <div>省市区</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_input_email" title="邮箱">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_emails.png" alt="">
                            <div>邮箱</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_sex" title="性别">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_sex.png" alt="">
                            <div>性别</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_slide" title="下拉">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_slide.png" alt="">
                            <div>下拉</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_input_text" title="输入框">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_input.png" alt="">
                            <div>输入框</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_choose" title="选项">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_choose.png" alt="">
                            <div>选项</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_dates" title="日期">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_date.png" alt="">
                            <div>日期</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_imgs" title="图片上传">
                            <img src="http://${image_domain!}/image/admin/shop_deco/icon_upload.png" alt="">
                            <div>图片上传</div>
                        </li>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading panel-title collapsed" data-parent="#accordion"
                     href="#collapse1" style="cursor: default;">
                    图文类
                </div>
                <div id="collapse1" class="panel-collapse">
                    <div class="panel-body drag-module-list">

                        <li class="drag ModuleList every_case" data-limit="2" module_name="m_scroll_image" title="轮播图">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/dg_rotation.png" alt="">
                            <div>轮播图</div>
                        </li>

                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_rich_text" title="富文本">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/dg_rich_text.png" alt="">
                            <div>富文本</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_image_small" title="图片广告">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/dg_img_advertist.png" alt="">
                            <div>图片广告</div>
                        </li>

                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_text" title="文本模块">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/dg_text.png" alt="">
                            <div>文本模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_dashed_line" title="辅助线">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/dg_helpline.png" alt="">
                            <div>辅助线</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_blank" title="辅助空白">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/dg_helpblank.png" alt="">
                            <div>辅助空白</div>
                        </li>

                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_phone" title="电话模块">
                            <img src="http://${image_domain!}/image/admin/drag_phone.png" alt="">
                            <div>电话模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_official_accounts" title="公众号">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/official_account.png" alt="">
                            <div>公众号</div>
                        </li>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="col-sm-3" style="width:350px; margin:0px 2px 2px 10px; padding:0;">
        <div class="phone_box">
            <div class="phone_top"
                 style="background: url(/image/admin/shop_beautify/phone_tops.png) no-repeat;">
            </div>

            <div class="phone" style="height: 420px;">
                <div id="drag_area_container">
                    <div id="drag_area">
                        <div class="drag_notice" <#if ($page->page_content)style="display: none;"</#if>>拖拽左侧模块进行装修</div>
                        <div class="btn_tijiao" style="color: ${page->form_cfg['font_color'] ?? '#ffffff'!};
                                background-color: ${page->form_cfg['bg_color'] ?? '#ff6666'!}">
                            {{empty($page->form_cfg['notice_name']) ? '提交' : $page->form_cfg['notice_name']!}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    {{--收起样式--!}
    <div class="hide_style col-sm-5">
        <div>表单信息</div>
        <div class="change_show" style="text-align: right;color: #5a8bff;">
            <span class="notices">收起</span>
            <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" alt="">
        </div>
    </div>

    <div id="module_edit" class="col-sm-5" style="max-width:800px; margin:0px 2px 2px 10px; padding-left:10px;width: 43%">
        <div class="module_body_container panel panel-body">
            <div class="module_body"></div>
            <div class="u_editor">
                <textarea id="editor" style="width:100%;height:400px;"></textarea>
            </div>
        </div>
    </div>
    <div id="module_setings" style="display: none" class="col-sm-5">
        <ul class="setting_content">
            <li class="each_detail clearfix">
                <div class="ec_title">标题：</div>
                <div class="ec_content">
                    <input type="text" name="page_name" id="page_name" maxlength=30 size="10" value="${page->page_name!}">
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">有效期：</div>
                <div class="ec_content">
                    <input type="radio" name="expire_type" value="1" checked>永久有效
                    <input type="radio" name="expire_type" value="0" class="chosse_time"
                           <#if ($page->form_cfg['is_forever_valid']=='0') checked </#if>>固定日期
                </div>
            </li>
            <li class="each_detail clearfix show_times" style="display: none;margin-left: 31%">
                <div class="ec_content time_typre" style="width: 280px">
                    <div>开始时间 <input type="text" name="start_time" onclick="picker();"
                        value="${page->form_cfg['start_time']!}" autocomplete="off"></div>
                    <div>结束时间 <input type="text" name="end_time" onclick="picker();"
                        value="${page->form_cfg['end_time']!}" autocomplete="off"></div>
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">提交次数限制：</div>
                <div class="ec_content">
                    <input type="radio" name="post_times" value="1" checked>不限次数
                    <input type="radio" name="post_times" value="0" class="limit_times"
                           <#if ($page->form_cfg['post_times']=='0') checked </#if>>限制次数
                </div>
            </li>
            <li class="each_detail clearfix shoe_times" style="display: none;margin-left: 31%">
                <div class="ec_content cishu_config" style="width: 301px">
                    <div>
                        每天 <input type="text" name="day_times" value="${page->form_cfg['day_times'] ?? 1!}" onkeyup="value=value.replace(/[^\d]/g,'')">,
                        累计 <input type="text" name="total_times" value="${page->form_cfg['total_times'] ?? 1!}" onkeyup="value=value.replace(/[^\d]/g,'')">,
                    </div>
                    <div class="config_notice">默认1次，0代表不限制，累计次数必须大于每天次数</div>
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">总反馈数量限制：</div>
                <div class="ec_content">
                    达 <input onkeyup="value=value.replace(/[^\d]/g,'')" type="text" style="width: 80px" name="get_times" value="${page->form_cfg['get_times'] ?? 0!}" onkeyup="value=value.replace(/[^\d]/g,'')"> 次后不可提交
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title"></div>
                <div class="ec_content" style="color: #999;">默认为0，0代表不限制</div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">底部导航：</div>
                <div class="ec_content">
                    <input type="radio" name="has_bottom" value="1" <#if ($page->form_cfg['has_bottom']==1) checked </#if>>添加
                    <input type="radio" name="has_bottom" value="0" class="has_bottom" <#if ($page->form_cfg['has_bottom']!=1) checked </#if>>不添加
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">提交按钮文字：</div>
                <div class="ec_content">
                    <input type="text" name="notice_name" value="${page->form_cfg['notice_name']!}" placeholder="请输入提示按钮的文字">
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">提交按钮文字颜色：</div>
                <div class="ec_content choose_colors">
                    <input type="color" value="${page->form_cfg['font_color'] ?? '#ffffff'!}" name="font_color">
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">提交按钮背景颜色：</div>
                <div class="ec_content choose_colors">
                    <input type="color" value="${page->form_cfg['bg_color'] ?? '#ff6666'!}" name="bg_color">
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">表单海报背景图：</div>
                <div class="ec_content add_images">
                    <input type="hidden" name="bg_img" value="${page->form_cfg['bg_img']!}"/>
                    <#if  ($page->form_cfg['bg_img'])
                        <img src="${page->form_cfg['bg_img']!}" alt="" style="width: 100%; height: 100%"/>
                        <#else>
                        <img src="http://${image_domain!}/image/admin/add_img_bg.png" alt="" />
                    </#if>
                </div>
                <span style="line-height: 75px; margin-left: 20px; color: #999;">建议尺寸：800*800像素</span>
            </li>
            <li class="each_detail fengexian">
                <div class="hengxian"></div>
                <div class="zi">参与奖励</div>
                <div class="hengxian"></div>
            </li>
            <li class="each_detail clearfix">
                <div class="card_modules_right clearfix" style="float: none;">
                    <div class="song_copon">
                        <div><input type="checkbox" class="send_coupon" <#if  ($page->form_cfg['send_coupon']) checked </#if>>参与送优惠券</div>
                        
                    </div>
                    <div class="coupon_area">
                        <div class="coupon_list_div clearfix" coupon_list_json="{{json_encode($page->form_cfg['send_coupon_list'])!}">

                        </div>
                        <div class="card_add form_add_coupon">
                            <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                            <p>添加优惠券</p>
                        </div>
                    </div>
                    <div class="max_tips">
                        最多可以添加5张优惠券，已过期和已停用的优惠券不能添加
                    </div>
                </div>
                <div class="card_modules_right clearfix" style="float: none;">
                    <div class=""><input type="checkbox" class="send_score" <#if  ($page->form_cfg['send_score']) checked </#if>>
                        参与送积分<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" class="send_score_number" value="${page->form_cfg['send_score_number']!}">分
                    </div>
                </div>
                <div class="form_coupon_list_clone" hidden>
                    <div class="coupon_list">
                        <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del" index=""/>
                        <input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info" />
                        <div class="coupon_list_top">
                            &yen;<span>&times;&times;</span>
                        </div>
                        <div class="coupon_list_center">
                            <div class="coupon_center_limit">满&times;&times;使用</div>
                            <div class="coupon_center_number">剩余<span>&times;&times;</span>张</div>
                        </div>
                        <div class="coupon_list_bottom">
                            领取
                        </div>
                    </div>
                </div>
            </li>
            <li class="each_detail clearfix" style="text-align: center">
                <a class="btn_savr_config">确定</a>
            </li>
        </ul>
    </div>
</div>

<#if ($page->state == 1)
    {{--<div class="btn-group mp_deco_btnscve fix_footer">--!}
        {{--<input type="button" onclick="javascript:history.go(-1)" style="margin-left: -25% !important;" class="btn_go_back" value="返回" >&nbsp--!}
    {{--</div>--!}
<#else>
    <div class="btn-group mp_deco_btnscve fix_footer">
        <input type="button" id="save_content" style="margin-left: -15% !important;" name="save_content" class="btn btn-primary" value="保存" >&nbsp;
        <input type="button" id="publish_content" style="margin-left: 0!important;" name="publish_content" class="btn btn-primary" value="保存并发布">
        <span style="color: #999;font-size: 12px">发布后不可更改</span>&nbsp;
    </div>
</#if>

<div id="template_list">
    <#include ("admin.form_decorate_input")

    <#include ("admin.shop_decorate_m_pictxt")

    <#include ("admin.shop_decorate_m_activity")

    <#include ("admin.form_decorate_select")

</div>

<script>
    var hasSaved = true;

</script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script charset="utf-8" src="/js/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="/js/kindeditor/lang/zh-CN.js"></script>
<script charset="utf-8" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>
<script type="text/javascript" src="/js/admin/form_decorate.js?v=1.1.23"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_pictxt.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_activity.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/admin/form_decorate_input.js?v=1.0.6"></script>
<script type="text/javascript" src="/js/admin/form_decorate_select.js?v=1.0.1"></script>
<script type="text/javascript" src="/js/ZeroClipboard/ZeroClipboard.js"></script>

<script type="text/javascript">
    $(".every_case").click(function () {
        var div_num = $("#drag_area>div").length;
       if(div_num >= 1){
           $(".drag_notice").css("display","none");
       }
    });
    var search_url = window.location.search;
    if(search_url != ''){
        hasSaved = true;
    }
    util.inputChange();
    util.selectChange();
    util.checkboxChange();
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };

    {{--$('body').on("click",".change_show",function () {--!}
        {{--if($("#module_setings").css("display") != 'block'){--!}
            {{--$("#module_setings").css("display",'block');--!}
            {{--$("#module_edit").hide();--!}
            {{--$(".notices").html("收起");--!}
            {{--$(".change_show img").attr("src",'http://${image_domain!}/image/admin/shop_deco/icon_up.png');--!}
        {{--}else{--!}
            {{--$("#module_setings").css("display",'none');--!}
            {{--$(".notices").html("展开");--!}
            {{--$(".change_show img").attr("src",'http://${image_domain!}/image/admin/shop_deco/icon_down.png');--!}
        {{--}--!}
    {{--});--!}
    //固定日期
    if($(".chosse_time").is(":checked")){
        $(".show_times").css("display","block");
    }else{
        $(".show_times").css("display","none");
    }
    $("input[name='expire_type']").change(function () {
        if($(".chosse_time").is(":checked")){
            $(".show_times").css("display","block");
        }else{
            $(".show_times").css("display","none");
        }
    });

    //领取次数
    if($(".limit_times").is(":checked")){
        $(".shoe_times").css("display","block");
    }else{
        $(".shoe_times").css("display","none");
    }
    $("input[name='post_times']").change(function () {
        if($(".limit_times").is(":checked")){
            $(".shoe_times").css("display","block");
        }else{
            $(".shoe_times").css("display","none");
        }
    });
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    }

    $("input[name='font_color']").change(function () {
        hasSaved = false;
        $(".btn_tijiao").css("color",$("input[name='font_color']").val());
    });
    $("input[name='bg_color']").change(function () {
        hasSaved = false;
        $(".btn_tijiao").css("background",$("input[name='bg_color']").val());
    });
    $("input[name='notice_name']").change(function () {
        hasSaved = false;
        var notice_name = $("input[name='notice_name']").val() ? $("input[name='notice_name']").val() : '提交';
        $(".btn_tijiao").text(notice_name);
    });

    if(!$("#module_edit .module_body").html()){
        $("#module_setings").css("display",'block');
    }

    if ($("#page_state").val() == '1') {
        $("input[name='ok']").css('display','none');
        $(".btn_savr_config").css('display','none');
        $(".drag").css('pointer-events','none');
        // $("#module_edit").css('pointer-events','none');
        $("#module_edit input").attr("disabled",true);
        $("#module_setings input").attr("disabled",true);
        $("#module_setings img").parent("div").css("pointer-events",'none');
        $(".item_operation").css("display","none");
    }

    $('.add_images').click(function() {
        var el = $(this).parent();
        var w = 800;
        var h = 800;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find("img").attr("src", path);
                el.find("img").css({
                    'width': '100%',
                    'height': '100%'
                });
                el.find('.add_images').css('paddingTop', '0px');
                $("input[name='bg_img']").val(path);
            }
        });
    });
    <#if  ($page->form_cfg['bg_img'])
        $('.add_images').css('paddingTop', '0px');
    </#if>
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    //版本控制
    var num = '${version['num']!}';
    var use = '${version['use']!}';
    var id  = util.getUrlParam('id');
    var state = '${page->state!}';
    console.log(state);
    if(state != 1 && state != 2){
        if(num != ''){
            if(id > 0){//编辑
                if(num>=0 && num-use < 0){
                    window.location.href = '/admin/authority/not?mod_name=表单数量';
                }
            }else{//新建
                if(num>=0 && num-use <= 0){
                    window.location.href = '/admin/authority/not?mod_name=表单数量';
                }
            }
        }{
            getPowerInfo('num_config','form_decoration','','表单数量',1,0);
        }
    }

</script>