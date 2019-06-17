<#include ("system.header")
<link href="/css/system/admin_deco_style.css?v=1.0.7" type="text/css" rel="stylesheet">
<link href="/css/system/shop_decorate.css?v=1.1.1" rel="stylesheet">

<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.0" type="text/css" />

<input type="hidden" id="page_type" name="page_type" value="${page->page_type!}">
<input type="hidden" id="page_tpl_type" name="page_tpl_type" value="${page->page_tpl_type!}">

<input type="hidden" id="page_id" name="page_id" value="${page->page_id!}">
<input type="hidden" id="page_content" name="page_content" value="${page->page_content!}">
<style>
    .goods-item-img li{
        float: left;
        margin-right: 15px;
        background: #f7f7f7;
        border: 1px solid #ccc;
        width: 80px;
        height: 80px;
        text-align: center;
        line-height: 80px;
        font-size: 0;
        position: relative;
    }
    .goods-item-img li img{
        max-width: 100%;
        max-height: 100%;
    }
    .img-delete{
        position: absolute;
        top: -8px;
        right: -8px;
    }
    tr td{
        padding: 8px 10px;
    }
    .choose_colors input {
        width: 60px;
        margin-right: 10px;
    }

    .each_detail .ec_title {
        width: 100px;
    }

    body {
        padding-bottom: 40px;
    }

    .drag_isabled {
        display: inline-block;
        float: left;
        line-height: 24px;
        width: 100px;
        height: 84px;
        margin: 5px 0 10px 10px;
        cursor: move;
        position: relative;
    }

    .choose_imgs .bg_image {
        max-width: 100%;
        max-height: 100%;
    }

    .each_detail .choose_imgs {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .collapse0 {
        display: none;
    }

    .collapse2 {
        display: none;
    }

    .left-menu-content .item-menu:nth-child(2) span{
        opacity: 0.7!important;
    }
    .youxianji{
        margin-bottom: 0 !important;
    }
</style>
{{--<div class="page_title">--!}
    {{--<span>装修模板 / </span>--!}
    {{--<span style="color: #666;">模板详情</span>--!}
{{--</div>--!}
<ul id="tab" class="nav nav-tabs">

    <li><a href="#" data-toggle="tab" onclick="location.href = '/system/decoration/template'">装修模板列表</a></li>
    <li <#if (empty($page)) class="active" </#if> ><a href="#">添加模板</a></li>
    <#if (!empty($page))
    <li class="active"><a href="#" data-toggle="tab" url="">编辑模板</a></li>
    </#if>
</ul>
<div class="box decoreate_content clearfix">
    <div class="col-sm-3" style="width:240px;margin:2px;padding: 0 2px;">
        <div style="padding:15px 0px;">
            名称：<input type="text" name="page_name" id="page_name" maxlength=30 size="10" value="${page->page_name!}">
        </div>
        <div style="padding:15px 0px;">
            封图：<ul class="goods-item-img clearfix" style="padding-left: 50px;position: relative;top: -18px;">
                <#if ($page->page_img)
                    <li style="margin-bottom: 5px">
                        <input type="hidden" name="page_img" value="${page->page_img!}" id="page_img">
                        <img src="${page->page_img!}" class="add_img" alt="${page->page_name!}">
                        {{--<img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete"  />--!}
                    </li>
                <#else>
                <li style="margin-bottom: 5px">
                    <input name="page_img" type="hidden" value="" id="page_img">
                    <img src="http://${image_domain!}/image/admin/add_img.png" class="add_img" alt=""/>
                    {{--<img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" style="display: none;" />--!}
                </li>
                </#if>
            </ul>
        </div>
        <div class="panel-group" id="accordion">
            <div class="panel-heading panel-title collapsed" data-parent="#accordion"
                 style="cursor: default;">
                <span class="api-title api-active" id="api-one">图文组件</span>
                <span class="api-title" id="api-zero">商品组件</span>
                <span class="api-title" id="api-two">营销组件</span>
            </div>
            <div class="panel panel-default collapse1">
                <div id="collapse1" class="panel-collapse">
                    <div class="panel-body drag-module-list">

                        <li class="drag ModuleList every_case" data-limit="2" module_name="m_scroll_image" title="轮播图">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_rotation.png" alt="">
                            <div>轮播图</div>
                        </li>

                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_magic_cube" title="魔方多图">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_imgwindow.png" alt="">
                            <div>魔方多图</div>
                        </li>
                        {{--<li class="drag ModuleList every_case" data-limit="-1" module_name="m_single_image"--!}
                        {{--title="单列图片">--!}
                        {{--<img src="http://${image_domain!}/image/admin/shop_beautify/dg_imgline.png" alt="">--!}
                        {{--<div>单列图片</div>--!}
                        {{--</li>--!}
                        {{--<li class="drag ModuleList every_case" data-limit="-1" module_name="m_double_image"--!}
                        {{--title="双列图片">--!}
                        {{--<img src="http://${image_domain!}/image/admin/shop_beautify/dg_lines.png" alt="">--!}
                        {{--<div>双列图片</div>--!}
                        {{--</li>--!}
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_image_guide" title="图片导航">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_guide.png" alt="">
                            <div>图片导航</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_title" title="标题模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_title.png" alt="">
                            <div>标题模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_text" title="文本模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_text.png" alt="">
                            <div>文本模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_dashed_line" title="辅助线">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_helpline.png" alt="">
                            <div>辅助线</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_blank" title="辅助空白">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_helpblank.png" alt="">
                            <div>辅助空白</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_rich_text" title="富文本">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_rich_text.png" alt="">
                            <div>富文本</div>
                        </li>
                        {{--<li class="drag ModuleList every_case" data-limit="-1" module_name="m_image_small" title="图片广告">--!}
                        {{--<img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_img_advertist.png" alt="">--!}
                        {{--<div>图片广告</div>--!}
                        {{--</li>--!}
                        {{--<li class="drag ModuleList every_case" data-limit="2" module_name="m_multi_image" title="多图模块">--!}
                        {{--<img src="http://${image_domain!}/image/admin/shop_beautify/dg_moreimgs.png" alt="">--!}
                        {{--<div>多图</div>--!}
                        {{--</li>--!}
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_image_adver" title="图片新告">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_img_advertist.png" alt="">
                            <div>图片广告</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_shop" title="店招模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_navigation.png" alt="">
                            <div>店招设置</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_phone" title="电话模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/drag_phone.png" alt="">
                            <div>电话模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_map" title="地图模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_map.png" alt="">
                            <div>地图模块</div>
                        </li>
                        <li
                            class="drag ModuleList every_case"  data-limit="1" module_name="m_video"
                            title="视频模块">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_video.png" alt="">
                            <div>视频模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_service" title="客服模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/service.png" alt="">
                            <div>客服模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_hot_area" title="图片热区">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/hot-area.png" alt="">
                            <div>图片热区</div>
                        </li>
                        <li class="drag ModuleList every_case"  data-limit="-1" module_name="m_official_accounts" title="公众号">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/official_account.png" alt="">
                            <div>公众号</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_text_image" title="左图右文">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/text_image.png" alt="">
                            <div>左图右文</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_shop_announce" title="店铺公告">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/shop_announce.png" alt="">
                            <div>店铺公告</div>
                        </li>
                    </div>
                </div>
            </div>

            <div class="panel panel-default collapse0">
                <div id="collapse0" class="panel-collapse in" style="height: auto;">
                    <div class="panel-body drag-module-list">
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_goods" title="商品">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/deco_goods.png" alt="">
                            <div>商品</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_goods_search" title="商品搜索">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_search.png" alt="">
                            <div>商品搜索</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_goods_group" title="商品分组">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/goods_group.png" alt="">
                            <div>商品分组</div>
                        </li>
                    </div>
                </div>
            </div>

            <div class="panel panel-default collapse2">
                <div id="collapse2" class="panel-collapse in" style="height: auto;">
                    <div class="panel-body drag-module-list">
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_card"
                            title="会员卡模块">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/deco_card.png"/>
                            <div>会员卡</div>
                        </li>
                        <li class="drag ModuleList every_case"  data-limit="1" module_name="m_coupon"
                            title="优惠券模块">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/deco_voucher.png"/>
                            <div>优惠券</div>
                        </li>
                        <li class="drag ModuleList every_case"  data-limit="1" module_name="m_bargain"
                            title="砍价模块">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/bargain.png"/>
                            <div>砍价</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_integral"
                            title="">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_integral_deco.png" alt="">
                            <div>积分兑换</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_seckill" title="秒杀">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/secKill.png" alt="">
                            <div>秒杀</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_group_draw" title="拼团抽奖">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/fight_group.png" alt="">
                            <div>拼团抽奖</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_pin_integration" title="瓜分积分">
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/pin_integration.png">
                            <div>瓜分积分</div>
                        </li>
                    </div>
                </div>
            </div>
        </div>
        {{--<div class="box panel panel-body">--!}
        {{--<a href="${page_url!}" id="page_url" target="_blank" style="display: none;">${page_url!}</a>--!}
        {{--</div>--!}
    </div>

    <div class="col-sm-3" style="width:385px; margin:0px 2px 2px 10px; padding:0;">
        <div class="phone_box">
            <div class="phone_top"
                 style="background: url(/image/admin/new_shop_beautify/page_name.png) no-repeat;">
                <span class="phone_page_title"></span>
            </div>
            <div class="phone">
                <div id="drag_area_container">
                    <div id="drag_area" style="background: #f5f5f5;">
                        <div class="drag_notice" <#if ($page->page_content)style="display: none;"</#if>>拖拽左侧模块进行装修</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="module_edit" class="col-sm-5" style="max-width: 725px; margin: 0px 2px 2px 10px; padding-left: 10px; width: 43%; display: none;">
        {{--<h1>模块编辑</h1>--!}
        {{--<div style="border-bottom:1px dotted #d4d4d4; margin-top:5px; margin-bottom:5px;"></div>--!}
        <div class="module_body_container panel panel-body">
            <div class="module_body"></div>
            <div class="u_editor" >
                <textarea id="editor" style="width:100%;height:400px;"></textarea>
            </div>
        </div>
        {{--<div style="margin-top:15px;">--!}
        {{--<input class="btn btn-primary" type='button' id="ok" name="ok" value="确定">--!}
        {{--</div>--!}
    </div>

</div>

<div class="btn-group mp_deco_btnscve">
    <input type="button" id="save_content" name="save_content" class="btn btn-primary" value="保存">&nbsp;
</div>


<div id="template_list">


    <#include ("system.shop_decorate_m_shop")


    <#include ("system.shop_decorate_m_goods")


    <#include ("system.shop_decorate_m_goods_top")


    <#include ("system.shop_decorate_m_pictxt")


    <#include ("system.shop_decorate_m_activity")


    <#include ("system.shop_decorate_m_magic_cube")

    <#include ("system.shop_decorate_m_hot_area")

    <#if  ($page->page_tpl_type == '2')
        <#include ("system.shop_decorate_m_girl")

    <#elseif> ($page->page_tpl_type == "4")
        <#include ("system.shop_decorate_m_west_street")
    </#if>

    <#include ("system.shop_decorate_m_card"){{--会员卡--!}
    <#include ("system.shop_decorate_m_coupon")
    <#include ("system.shop_decorate_m_bargain")
    <#include ("system.shop_decorate_m_seckill")
    <#include ("system.shop_decorate_m_pin_integration")
    <#include ("system.shop_decorate_m_group_draw")
    <#include ("system.shop_decorate_m_goods_group")

</div>

{{--<script>--!}
    {{--// $(document).ready(function () {--!}
    {{--//     util.init_zero_clipboard($("#copy_to_clip"));--!}
    {{--//--!}
    {{--// });--!}
{{--</script>--!}


<script type="text/javascript">
    $(".every_case").click(function () {
        var div_num = $("#drag_area>div").length;
        if(div_num >= 1){
            $(".drag_notice").css("display","none");
        }
    })
    $('.add_img').click(function() {
        var el = $(this);
        var w = 150;
        var h = 210;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.attr("src", path);
                el.prev().val(path);
                el.next().show();
            }
        });
    });
    $('[name="bg_types"]').click(function () {
        if ($(this).val() == 1) {
            $("#drag_area_container #drag_area").css('background-image','url('+$('.page_bg_image').val()+')');
            $("#drag_area_container #drag_area").css('background-repeat',"repeat-y")
            $("#drag_area_container #drag_area").css('background-size','100% auto')

        } else {
            $("#drag_area_container #drag_area").css('background-image','');
            $("#drag_area_container #drag_area").css('background-color',$('[name="page_bg_color"]').val())
        }
    });
    function resetColor(that){
        $(that).prev().val('#f5f5f5')
        if ($('input[name="bg_types"]:checked').val() != 1) {
            $("#drag_area_container #drag_area").css('background-color','#f5f5f5')
        }
    }
    $("[name='page_bg_color']").change(function () {
        if ($('input[name="bg_types"]:checked').val() != 1) {
            $("#drag_area_container #drag_area").css('background-color',$(this).val())
        }
    });
    $(".choose_imgs").click(function () {
        var el = $(this);
        $.jImageManager({
            // img_width: 750,
            // img_height: 1400,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find(".page_bg_image").val(path);
                el.find(".bg_image").attr('src', path);
                if ($('input[name="bg_types"]:checked').val() == 1) {
                    $("#drag_area_container #drag_area").css('background-image','url('+path+')');
                    $("#drag_area_container #drag_area").css('background-repeat',"repeat-y")
                    $("#drag_area_container #drag_area").css('background-size','100% auto')
                }
                hasSaved = false;
            }
        });
    });

    $('.drag_isabled').hover(function () {
        $(this).find('.system_info_content2').show();
        $(this).find('.system_shadow2').show();
    }, function () {
        $(this).find('.system_info_content2').hide();
        $(this).find('.system_shadow2').hide();
    })


    $(document).on("change",'input[name="show_margin"]',function () {
        if($("#show").is(":checked")){
            $("#margin_height").parent().parent().css("display","block");
            var mard = $("#margin_height").val() + "px";
            $("#drag_area .row_item").css("margin-bottom",mard);
            $("#drag_area .m_goods").css("margin-bottom",'10px');
            $("#drag_area .m_bargain").css("margin-bottom",'10px');
            $("#drag_area .m_integral").css("margin-bottom",'10px');
            $("#drag_area .m_seckill").css("margin-bottom",'10px');
        }else if($("#no_show").is(":checked")){
            $("#margin_height").parent().parent().css("display","none");
            $("#drag_area .row_item").css("margin-bottom","0");
            $("#drag_area .m_goods").css("margin-bottom",'10px');
            $("#drag_area .m_bargain").css("margin-bottom",'10px');
            $("#drag_area .m_integral").css("margin-bottom",'10px');
            $("#drag_area .m_seckill").css("margin-bottom",'10px');
        }
    })
    if($("#show").is(":checked")){
        $("#margin_height").blur(function () {
            var mard = $("#margin_height").val() + "px";
            $("#drag_area .row_item").css("margin-bottom",mard);
            $("#drag_area .m_goods").css("margin-bottom",'10px');
            $("#drag_area .m_bargain").css("margin-bottom",'10px');
            $("#drag_area .m_integral").css("margin-bottom",'10px');
            $("#drag_area .m_seckill").css("margin-bottom",'10px');
        })
    }
</script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&key=GO6BZ-EUL6P-ZLPDD-VYOYE-CCZBH-UFBOU"></script>
<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/system/jImageManager.js"></script>
<script language="JavaScript" src="/js/system/jVideoManager.js"></script>
<script charset="utf-8" src="/js/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="/js/kindeditor/lang/zh-CN.js"></script>
<script charset="utf-8" src="/js/system/kindeditor-init.js"></script>
<script type="text/javascript" src="/js/system/decorate.js?v=1.2.1"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_goods.js?v=1.2.4"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_shop.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_magic_cube.js?v=1.0.5"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_activity.js?v=1.3.8"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_girl.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_west_street.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_hot_area.js?v=1.0.4"></script>

{{--会员卡js--!}
<script type="text/javascript" src="/js/system/shop_decorate_m_card.js?v=1.0.6"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_coupon.js?v=1.0.6"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_bargain.js?v=1.0.6"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_seckill.js?v=1.0.6"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_group_draw.js?v=2.1.6"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_pin_integration.js?v=1.2.7"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_pictxt.js?v=1.0.1"></script>
<script type="text/javascript" src="/js/system/shop_decorate_m_goods_group.js?v=1.2.6"></script>
<script type="text/javascript" src="/js/layui/layui.js"></script>
<#include ("system.footer")