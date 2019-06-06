<#include "/admin/header.ftl">
<link href="/css/admin/iconfont.css?v=1.0.2" rel="stylesheet">
<link href="/css/admin/admin_deco_style.css?v=1.0.5" type="text/css" rel="stylesheet">
<link href="/css/admin/shop_decorate.css?v=2.2.7" rel="stylesheet">


<style type="text/css">
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
<input type="hidden" id="page_id" name="page_id" value="${page->page_id!}">
<input type="hidden" id="page_type" name="page_type" value="${page->page_type!}">
<input type="hidden" id="page_tpl_type" name="page_tpl_type" value="${page->page_tpl_type!}">
<input type="hidden" id="page_content" name="page_content" value="${page->page_content!}">

<div class="page_title">
    <span>小程序管理 / </span>
    <span style="color: #666;">自定义页面装修</span>
</div>
{{--<#if  ($page->page_tpl_type == '0')--!}
    {{--<div class="box alert alert-success">--!}
        {{--鼠标拖动左侧模块图标到中间手机模型框中定制您的页面，尽情装修吧。--!}
    {{--</div>--!}
{{--</#if>--!}
{{--<div class="box decoreate_content" style="min-height:655px;margin: 0 1%;">--!}
<div class="box decoreate_content fix_every_footer" style="min-height:905px;margin: 0 10px;">
    <div class="decorate-title" style="margin-bottom: 12px">
        <span style="font-size: 14px;color: #333;padding-left: 10px">组件库</span>
        <span style="font-size: 12px;color: #999;padding-left: 5px">可拖拽使用</span>
    </div>
    <div class="col-sm-3" style="width:240px;margin:2px;padding: 0 2px;">
        <div class="panel-group" id="accordion"
             <#if  ($page && $page->page_tpl_type != '0' && $page->page_tpl_type != '3') style="display:none;"</#if>>
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
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_image_guide" title="图片导航">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_guide.png" alt="">
                            <div>图片导航</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_image_adver" title="图片新告">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_img_advertist.png" alt="">
                            <div>图片广告</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_magic_cube" title="魔方多图">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_imgwindow.png" alt="">
                            <div>魔方多图</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_hot_area" title="图片热区">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/hot-area.png" alt="">
                            <div>图片热区</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_text_image" title="左图右文">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/text_image.png" alt="">
                            <div>左图右文</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_text" title="文本模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_text.png" alt="">
                            <div>文本模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_rich_text" title="富文本">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_rich_text.png" alt="">
                            <div>富文本</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_blank" title="辅助空白">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_helpblank.png" alt="">
                            <div>辅助空白</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_dashed_line" title="辅助线">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_helpline.png" alt="">
                            <div>辅助线</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_title" title="标题模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_title.png" alt="">
                            <div>标题模块</div>
                        </li>
                        <li <#if ($version_mod["m_video"] == 0) class="drag_isabled ModuleList every_case"
                            <#else> class="drag ModuleList every_case" </#if> data-limit="1" module_name="m_video"
                            title="视频模块">
                            <#if ($version_mod["m_video"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_video_disabled.png"/>
                                <img src="http://${image_domain!}/image/admin/system_icon.png" class="drag_info"/>
                                <img src="http://${image_domain!}/image/admin/system_shadow.png"
                                     class="system_shadow2"/>
                                <div class="system_info_content2">
                                    <div class="system_info_content_top">该模块仅高级版和旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=视频模块" target="_blank">了解更多</a>
                                    </div>
                                </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_video.png" alt="">
                            </#if>
                            <div>视频模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_shop_announce" title="店铺公告">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/shop_announce.png" alt="">
                            <div>店铺公告</div>
                        </li>
                        <li class="drag ModuleList every_case"  data-limit="-1" module_name="m_official_accounts" title="公众号">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/official_account.png" alt="">
                            <div>公众号</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_service" title="客服模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/service.png" alt="">
                            <div>客服模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_phone" title="电话模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/drag_phone.png" alt="">
                            <div>电话模块</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="-1" module_name="m_shop" title="店招模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/dg_navigation.png" alt="">
                            <div>店招设置</div>
                        </li>
                        <li class="drag ModuleList every_case" data-limit="1" module_name="m_map" title="地图模块">
                            <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_map.png" alt="">
                            <div>地图模块</div>
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
                        <li <#if ($version_mod["m_member_card"] == 0) class="drag_isabled ModuleList every_case"
                            <#else> class="drag ModuleList every_case" </#if> data-limit="1" module_name="m_card"
                            title="会员卡模块">
                            <#if ($version_mod["m_member_card"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/deco_card_disabled.png"/>
                                <img src="http://${image_domain!}/image/admin/system_icon.png" class="drag_info"/>
                                <img src="http://${image_domain!}/image/admin/system_shadow.png"
                                     class="system_shadow2"/>
                                <div class="system_info_content2">
                                    <div class="system_info_content_top">该模块仅高级版和旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=会员卡模块" target="_blank">了解更多</a>
                                    </div>
                                </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/deco_card.png"/>
                            </#if>
                            <div>会员卡</div>
                        </li>
                        <li <#if ($version_mod["m_voucher"] == 0) class="drag_isabled ModuleList every_case"
                            <#else> class="drag ModuleList every_case" </#if> data-limit="1" module_name="m_coupon"
                            title="优惠券模块">
                            <#if ($version_mod["m_voucher"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/deco_voucher_disabled.png"/>
                                <img src="http://${image_domain!}/image/admin/system_icon.png" class="drag_info"/>
                                <img src="http://${image_domain!}/image/admin/system_shadow.png"
                                     class="system_shadow2"/>
                                <div class="system_info_content2">
                                    <div class="system_info_content_top">该模块仅高级版和旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=优惠券模块" target="_blank">了解更多</a>
                                    </div>
                                </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/deco_voucher.png"/>
                            </#if>
                            <div>优惠券</div>
                        </li>
                        <li <#if ($version_mod["m_bargain"] == 0) class="drag_isabled ModuleList every_case"
                            <#else> class="drag ModuleList every_case" </#if> data-limit="1" module_name="m_bargain"
                            title="砍价模块">
                            <#if ($version_mod["m_bargain"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/bargain_disabled.png"/>
                                <img src="http://${image_domain!}/image/admin/system_icon.png" class="drag_info"/>
                                <img src="http://${image_domain!}/image/admin/system_shadow.png"
                                     class="system_shadow2"/>
                                <div class="system_info_content2">
                                    <div class="system_info_content_top">该模块仅旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=砍价模块" target="_blank">了解更多</a>
                                    </div>
                                </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/bargain.png"/>
                            </#if>
                            <div>砍价</div>
                        </li>
                        <li <#if ($version_mod["m_integral_goods"] == 0) class="drag_isabled ModuleList every_case"
                            <#else> class="drag ModuleList every_case" </#if> data-limit="-1" module_name="m_integral"
                            title="">
                            <#if ($version_mod["m_integral_goods"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_integral_deco_disabled.png"/>
                                <img src="http://${image_domain!}/image/admin/system_icon.png" class="drag_info"/>
                                <img src="http://${image_domain!}/image/admin/system_shadow.png"
                                     class="system_shadow2"/>
                                <div class="system_info_content2 system_info_content_single">
                                    <div class="system_info_content_top">该模块仅高级版和旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=积分商品模块" target="_blank">了解更多</a>
                                    </div>
                                </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_integral_deco.png" alt="">
                            </#if>
                            <div>积分兑换</div>
                        </li>
                        <li <#if ($version_mod["m_seckill_goods"] == 0) class="drag_isabled ModuleList every_case" <#else> class="drag ModuleList every_case" </#if> data-limit="1" module_name="m_seckill" title="秒杀">
                            <#if ($version_mod["m_seckill_goods"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/icon_secKill_disabled.png" />
                                <img src="http://${image_domain!}/image/admin/system_icon.png" class="drag_info" />
                                <img src="http://${image_domain!}/image/admin/system_shadow.png" class="system_shadow2" />
                                <div class="system_info_content2">
                                    <div class="system_info_content_top">该模块仅高级版和旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=秒杀模块" target="_blank">了解更多</a>
                                    </div>
                                </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/secKill.png" alt="">
                            </#if>
                            <div>秒杀</div>
                        </li>
                        <li <#if ($version_mod["m_group_draw"] == 0) class="drag_isabled ModuleList every_case" <#else> class="drag ModuleList every_case" </#if> data-limit="1" module_name="m_group_draw" title="拼团抽奖">
                            <#if ($version_mod["m_group_draw"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/fight_group_disabled.png" />
                                <img src="http://${image_domain!}/image/admin/system_icon.png" class="drag_info" />
                                <img src="http://${image_domain!}/image/admin/system_shadow.png" class="system_shadow2" />
                                <div class="system_info_content2 system_info_content_single">
                                    <div class="system_info_content_top">该模块仅高级版和旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=拼团抽奖模块" target="_blank">了解更多</a>
                                    </div>
                                </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/fight_group.png" alt="">
                            </#if>
                            <div>拼团抽奖</div>
                        </li>
                        <li <#if ($version_mod["m_pin_integration"] == 0) class="drag_isabled ModuleList every_case " <#else> class="drag ModuleList every_case" </#if> data-limit="1" module_name="m_pin_integration" title="瓜分积分">
                            <#if ($version_mod["m_pin_integration"] == 0)
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/pin_integration_disabled.png" />
                                <img src="http://${image_domain!}/image/admin/system_icon.png"   class="drag_info" />
                                <img src="http://${image_domain!}/image/admin/system_shadow.png"   class="system_shadow2" />
                                <div class="system_info_content2">
                                    <div class="system_info_content_top">该模块仅高级版和旗舰版可用</div>
                                    <div class="system_info_content_bottom">
                                        <a href="/admin/version/notice?mod_name=瓜分积分模块 " target="_blank">了解更多</a>
                                </div>
                            </div>
                            <#else>
                                <img src="http://${image_domain!}/image/admin/new_shop_beautify/pin_integration.png">
                            </#if>
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
                    <div id="drag_area">
                        <div class="drag_notice" <#if ($page->page_content)style="display: none;"</#if>>拖拽左侧模块进行装修</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    {{--收起样式--!}
    <div class="hide_style col-sm-5">
        <div>页面设置</div>
        <div class="change_show" style="text-align: right;color: #5a8bff;">
            <span class="notices">收起</span>
            <img src="http://${image_domain!}/image/admin/shop_deco/icon_up.png" alt="">
        </div>
    </div>

    <div id="module_edit" class="col-sm-5"
         style="max-width:725px; margin:0px 2px 2px 10px; padding-left:10px;width: 43%">
        {{--<h1>模块编辑</h1>--!}
        {{--<div style="border-bottom:1px dotted #d4d4d4; margin-top:5px; margin-bottom:5px;"></div>--!}
        <div class="module_body_container panel panel-body">
            <div class="module_body"></div>
            <div class="u_editor">
                <textarea id="editor" style="width:100%;height:400px;"></textarea>
            </div>
        </div>
        {{--<div style="margin-top:15px;">--!}
        {{--<input class="btn btn-primary" type='button' id="ok" name="ok" value="确定">--!}
        {{--</div>--!}
    </div>


    <div id="module_setings" style="display: none" class="col-sm-5">
        <ul class="setting_content" style="height: 415px;">
            <li class="each_detail clearfix">
                <div class="ec_title">页面名称：</div>
                <div class="ec_content">
                    <input type="text" name="page_name" id="page_name" maxlength=30 size="10"
                           value="${page->page_name!}">
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">页面分类：</div>
                <div class="ec_content">
                    <select name="cat_id" id="page_cat_id" style="border: 1px solid #E5E5E5;height: 30px;padding-left: 12px;width: 193px;">
                        <option value="" selected>请选择页面分类</option>
                        <#list ($page_cat_list as $k=>$name)
                            <option value="${k!}" <#if ($page->cat_id == $k) selected </#if>>${name!}</option>
                        </#list>
                    </select>
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">底部导航：</div>
                <div class="ec_content">
                    <input type="radio" name="has_bottom" value="1">添加
                    <input type="radio" name="has_bottom" value="0" checked style="margin-left: 10px">不添加
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">模块间距：</div>
                <div class="ec_content">
                    <label for="show">
                        <input type="radio" name="show_margin" id="show" value="0" checked>添加
                    </label>
                    <label for="no_show" style="margin-left: 10px">
                        <input type="radio" name="show_margin" id="no_show" value="1">不添加
                    </label>
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title"></div>
                <div class="ec_content">
                    间距高度：<input type="text" value="10" id="margin_height" style="width: 60px;padding-left: 0;text-align: center"> 像素
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title">
                    <input type="radio" name="bg_types" value="0" class='bg_co'>背景颜色：
                </div>
                <div class="ec_content choose_colors">
                    <input type="color" value="#f5f5f5" name="page_bg_color">
                    <input class="js-reset-bg huo_col chongzhi" type="button" value="重置"
                           onclick="resetColor(this)">
                </div>
            </li>
            <li class="each_detail clearfix">
                <div class="ec_title"><input type="radio" name="bg_types" value="1">背景图片：</div>
                <div class="ec_content choose_imgs">
                    <input class="page_bg_image" hidden>
                    <img style="margin-top: 0" class="bg_image"
                         src="http://${image_domain!}/image/admin/add_img_bg.png" alt="">
                </div>

            </li>

            <li class="each_detail clearfix" style="text-align: center;margin-bottom: 0">
                <div class="ec_title"></div>
                <div class="ec_content">
                    <a class="btn_savr_config">确定</a>
                </div>

            </li>
        </ul>
    </div>

</div>

<div class="btn-group mp_deco_btnscve fix_footer">
    <input type="button" id="save_content" name="save_content" class="btn btn-primary" value="保存并发布">&nbsp;
    <input type="button" id="save_draft_content" name="save_draft_content" class="btn btn-primary" value="保存为草稿">&nbsp;
    <input type="button" id="save_preview_content" name="save_preview_content" class="btn btn-primary" value="预览效果">&nbsp;
    <#if  ($page->page_id > 0)
        <a id="recovery_content" class="btn btn-recovery" href="javascript:void(0)">回退到当前已发布版本</a>
        <span>注：点击“回退到当前已发布版本”，页面草稿将置为线上已发布的页面</span>
    </#if>
</div>


<div id="template_list">


    <#include ("admin.shop_decorate_m_shop")


    <#include ("admin.shop_decorate_m_goods")


    <#include ("admin.shop_decorate_m_goods_top")


    <#include ("admin.shop_decorate_m_pictxt")


    <#include ("admin.shop_decorate_m_activity")


    <#include ("admin.shop_decorate_m_magic_cube")

    <#include ("admin.shop_decorate_m_hot_area")

    <#if  ($page->page_tpl_type == '2')
        <#include ("admin.shop_decorate_m_girl")

    <#elseif> ($page->page_tpl_type == "4")
        <#include ("admin.shop_decorate_m_west_street")
    </#if>

    <#include ("admin.shop_decorate_m_card"){{--会员卡--!}
    <#include ("admin.shop_decorate_m_coupon")
    <#include ("admin.shop_decorate_m_bargain")
    <#include ("admin.shop_decorate_m_seckill")
    <#include ("admin.shop_decorate_m_pin_integration")
    <#include ("admin.shop_decorate_m_group_draw")
    <#include ("admin.shop_decorate_m_goods_group")
</div>

<script>
    // $(document).ready(function () {
    //     util.init_zero_clipboard($("#copy_to_clip"));
    //
    // });
    var url = 'http://${image_domain!}/';
    var hasSaved = true;
    var is_card = "${version_mod["m_member_card"]!}";
    var is_coupon = "${version_mod["m_voucher"]!}";
    var is_bargain = "${version_mod["m_bargain"]!}";
    var is_video = "${version_mod["m_video"]!}";
    var is_integral = "${version_mod["m_integral_goods"]!}";
    var is_seckill = "${version_mod["m_seckill_goods"]!}";
    var is_group_draw = "${version_mod["m_group_draw"]!}";
    var is_pin_integration = "${version_mod["m_pin_integration"]!}";
    var set_colors = @json($shop_style);
    var the_color;
    var linear_color;

</script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&key=GO6BZ-EUL6P-ZLPDD-VYOYE-CCZBH-UFBOU"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script language="JavaScript" src="/js/admin/jVideoManager.js"></script>
<script charset="utf-8" src="/js/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="/js/kindeditor/lang/zh-CN.js"></script>
<script charset="utf-8" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>

<script type="text/javascript" src="{{asset("js/admin/mp_decorate.js")!}?v=1.1.16"></script>

<script type="text/javascript" src="{{asset('js/admin/shop_decorate_m_goods.js')!}?v=1.4.1"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_shop.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_pictxt.js?v=1.0.24"></script>
<script type="text/javascript" src="{{asset('js/admin/shop_decorate_m_magic_cube.js')!}?v=1.0.9"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_activity.js?v=1.3.9"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_girl.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_west_street.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_hot_area.js?v=1.0.4"></script>

{{--会员卡js--!}
<script type="text/javascript" src="/js/admin/shop_decorate_m_card.js?v=1.0.5"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_coupon.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_bargain.js?v=1.0.5"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_seckill.js?v=1.0.5"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_group_draw.js?v=2.1.4"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_pin_integration.js?v=1.2.6"></script>
<script type="text/javascript" src="/js/admin/shop_decorate_m_goods_group.js?v=1.4.0"></script>
<script type="text/javascript">


    $(".every_case").click(function () {
        var div_num = $("#drag_area>div").length;
        if (div_num >= 1) {
            $(".drag_notice").css("display", "none");
        }
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
<#include "/admin/footer.ftl">
<script>
    //版本控制
    var num = '${version['num']!}';
    var use = '${version['use']!}';
    var page_id = util.getUrlParam('id');
    if (page_id > 0) {//编辑
        if (num >= 0 && num - use < 0) {
            window.location.href = '/admin/authority/not?mod_name=装修页面数量';
        }
    } else {//新建
        if (num >= 0 && num - use <= 0) {
            window.location.href = '/admin/authority/not?mod_name=装修页面数量';
        }
    }
    $(".drag_isabled").click(function () {
        util.systemNotice(1, '', $(this).attr("title"));
    });
    $('.system_info_content_bottom a').click(function (e) {
        e.stopPropagation();
    });

    // var left =  $('.left-menu-content .item-menu:nth-child(1)');
    // left.find("img").attr("src","/image/admin/icon_left/picture_setting_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});

    // var right =  $('.left-menu-content .item-menu:nth-child(2)');
    // right.find("img").attr("src","/image/admin/icon_left/picture_space.png");
    // right.find("a").css("cssText","background:#323A4D !important");

</script>
