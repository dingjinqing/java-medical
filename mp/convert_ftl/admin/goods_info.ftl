<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css?v=1.2.5" type="text/css" />
<link href="/css/admin/goods_decorate.css?v=1.1.9" rel="stylesheet">
<link href="/css/admin/admin_deco_style.css?v=1.0.5" type="text/css" rel="stylesheet">
<style type="text/css">
    .buxianshi{
        display: none;
    }
    body{
        padding-bottom: 40px;
    }
    .tb-decorate-list>tbody>tr>td{
        height:50px;
    }
    .btn-save:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-save:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
    }
    .show-box-white{
        overflow: auto;
    }
    .aui_main{
        height:420px !important;
    }
    /*.add-goods-video{*/
    /*width: 150px;*/
    /*height: 80px;*/
    /*}*/
    .goods-item-video li{
        float: left;
        margin-right: 15px;
        background: #f7f7f7;
        border: 1px solid #ccc;
        width: 80px;
        height: 81px;
        text-align: center;
        line-height: 80px
    }
    .goods-item-video li img{
        max-width: 100%;
        max-height:100%;
    }
    .btn_playa {
        color: #5a8bff !important;
        width: 80px;
        height: 20px;
        line-height: 20px;
        position: absolute;
        left: 110px;
        text-align: left;
        top: 0;
        z-index: 999;
    }
    .jiaogeshane{
        position: absolute;
        bottom: 25px;
        width: 800px;
        height: 30px;
        line-height: 30px;
        left: 84px;
    }
    .same_tr_price,.same_td_price a{
        color: #5a8bff;
    }
    .same_td_price{
        display: none;
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
    .card_select_box{
        display:none;
    }
    .each_title_item{
        font-weight: bold;
    }
    .search_template{
        display: inline-block;
        width: 70px;
        height: 30px;
        line-height: 30px;
        background: #5a8bff;
        text-align: center;
        color: white;
        border-radius: 4px;
    }
    .add_brand {
        background: #fff;
        color: #333;
        border: 1px solid #ccc;
        width: 160px;
        height: 30px;
        display:inline-block;
        line-height: 30px;
        text-align: center;
        cursor: pointer;
        vertical-align: middle;
        text-overflow: ellipsis;
        white-space: nowrap;
        overflow: hidden;
    }
</style>
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">添加商品</span>
</div>
<div class="main-container ">
    <div class="goods-box fix_every_footer">
        <div class="goods-box-top">
            <div class="box-top-info box-top-first box-top-active">1.编辑基本信息</div>
            <div class="box-top-info box-top-detail">2.编辑商品详情</div>
        </div>
        <form name="formData" <#if ($nav==0) action="/admin/goods/manage/add" <#else> action="/admin/goods/manage/edit?goods_id=${goods->goods_id!}&top_index=${_GET['top_index'] ?? 0!}"  </#if>  id="form1" method="post" >
            {{ csrf_field()!}
            <input type="hidden" name="spec_info"/>
            <input type="hidden" name="no_spec_grade">
            <input type="hidden" id="page_id" name="page_id" value="${page->page_id!}">
            <input type="hidden" id="page_type" name="page_type" value="${page->page_type!}">
            <input type="hidden" id="page_tpl_type" name="page_tpl_type" value="${page->page_tpl_type!}">
            <input type="hidden" id="page_content" name="page_content" value="${page->page_content!}">
            <input type="hidden" id="is_page_up" name="is_page_up" value="${goods->is_page_up!}">
            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <table width="100%" class="basic-table">
                        <tr>
                            <td colspan="2" style="text-align: left !important;">
                                <div class="each_title_item">基本信息</div>
                            </td>
                        </tr>
                        <tr>
                            <td ><em>*</em>商品名称：</td>
                            <td><input type="text" name="goods_name" value="${goods->goods_name!}" style="width: 400px;"/>
                                <#if ($import_goods == 6) <button type="button"  class="btn-common btn-import-goods" onclick="importGoods()" >导入淘宝天猫商品</button> </#if>
                            </td>
                        </tr>
                        <tr>
                            <td >商品广告词：</td>
                            <td><input type="text" name="goods_ad"  value="${goods->goods_ad!}" style="width: 400px;" /></td>
                        </tr>
                        <tr>
                            <td >商品货号：</td>
                            <td><input type="text" name="goods_sn" value="${goods->goods_sn!}" placeholder=""/><span style="color:#999;margin-left:15px;">不填则由系统自动生成货号</span></td>
                        </tr>
                        <tr>
                            <td style="width: 105px;padding: 13px 0;color: #666;position: relative;vertical-align: top !important;"><em>*</em>平台分类：</td>
                            <td>
                                <input type="hidden" name="cat_id">
                                <#if ($cat_list1)
                                    <select name="level0" id="level0" onchange="select_cat(0);">
                                        <option value="0">请选择平台分类</option>
                                        <#list ($cat_list1 as $item)
                                            <option value="${item->cat_id!}" <#if ($goods->cat_id == $item->cat_id) selected="selected" </#if>>${item->cat_name!}</option>
                                        </#list>
                                    </select>
                                    <select name="level1" id="level1" onchange="select_cat(1);" style="display: none">
                                    </select>
                                    <select name="level2" id="level2"  onchange="select_cat(2);"style="display: none">
                                    </select>
                                </#if>
                                <#if ($cat_array)
                                    <#list ($cat_array as $k=>$row)
                                        <select name="level${k!}" id="level${k!}" onchange="select_cat(${k!});">
                                            <option value="0">请选择平台分类</option>
                                            <#list ($row['cat_list'] as $item)
                                                <option value="${item->cat_id!}" <#if ($row['cat_id'] == $item->cat_id) selected="selected" </#if>>${item->cat_name!}</option>
                                            </#list>
                                        </select>
                                    </#list>
                                    <#if (count($cat_array) ==1)
                                        <select name="level1" id="level1" onchange="select_cat(1);" style="display: none">
                                        </select>
                                        <select name="level2" id="level2"  onchange="select_cat(2);"style="display: none">
                                        </select>
                                    </#if>
                                    <#if (count($cat_array) ==2)
                                        <select name="level2" id="level2"  onchange="select_cat(2);"style="display: none">
                                        </select>
                                    </#if>
                                </#if>
                                <div class="label-info1 template-0" style="display: none">
                                    <div class="label-info-cat">
                                        <span class="label-cat">所属种类标签：</span>

                                    </div>
                                </div>
                                <div class="card-info1 template-0" style="display: none">
                                    <div class="card-info-cat">
                                        <span class="card-cat">所属专享会员卡：</span>

                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><em>*</em>商品主图：</td>
                            <td class="">
                                <ul class="goods-item-img clearfix">
                                    <#if ($goods->img_list)
                                        <#list ($goods->img_list as $item)
                                            <#if  (!empty($item))
                                                <li>
                                                    <input name="goods_img[]" type="hidden" value="${item!}">
                                                    <img src="${item!}" class='' alt="" />
                                                    <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" />
                                                    <img src="http://${image_domain!}/image/admin/master_diagram_left.png" alt="" class="master_diagram_left" style="display:none;">
                                                    <img src="http://${image_domain!}/image/admin/master_ diagram_right.png" alt="" class="master_diagram_right" style="display:none;">
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>
                                    <li>
                                        <input name="goods_img[]" type="hidden">
                                        <img src="http://${image_domain!}/image/admin/add_img.png" class="add_img" alt="" />
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" style="display: none;" />
                                        <img src="http://${image_domain!}/image/admin/master_diagram_left.png" alt="" class="master_diagram_left" style="display: none;">
                                        <img src="http://${image_domain!}/image/admin/master_ diagram_right.png" alt="" class="master_diagram_right" style="display: none;">
                                    </li>
                                    <div class="goods-suggest">建议尺寸：800*800像素</div>
                                </ul>

                            </td>
                        </tr>
                        {{--更多配置--!}
                        <tr>
                            <td colspan="2" style="text-align: left!important;" if_show="1">
                                <div class="show_more show_basic">
                                    <text>收起更多配置</text>
                                    <img src="http://${image_domain!}/image/admin/info_up.png" alt="">
                                </div>
                            </td>
                        </tr>
                        <tr class="basic_info">
                            <td >单位：</td>
                            <td>
                                <#if ($goods->unit && !in_array($goods->unit,(array)$unit))
                                    <select name="unit" onclick="unitPop(this)">
                                        <#list ((array)$unit as $item)
                                            <option value="${item!}" <#if ('自定义' == $item)selected="selected"</#if>>${item!}</option>
                                        </#list>
                                    </select>
                                    <span id="unit_pop" > &nbsp;<input type="text" name="unit_temp"  id="unit_input" value="${goods->unit!}" style=" width: 100px;" />&nbsp;<span class="grade_card_prompt">长度限制为3个中文字符</span></span>
                                <#else>
                                    <select name="unit" onclick="unitPop(this)">
                                        <#list ((array)$unit as $item)
                                            <option value="${item!}" <#if ($goods->unit == $item)selected="selected"</#if>>${item!}</option>
                                        </#list>
                                    </select>
                                    <span id="unit_pop" style="display: none;"> &nbsp;<input type="text" name="unit_temp"  id="unit_input" value="" style=" width: 100px;" />&nbsp;<span class="grade_card_prompt">长度限制为3个中文字符</span></span>
                                </#if>
                            </td>
                        </tr>
                        <tr class="basic_info">
                            <td style="width: 105px;padding: 13px 0;color: #666;position: relative;vertical-align: top !important;">商家分类：</td>
                            <td>
                                <input type="hidden" name="sort_id">
                                <select name="sort_id" id="level0" onchange="select_sort();">
                                    <option value="0">请选择商家分类</option>
                                    <#if ($sort_list)
                                        <#list ((array)$sort_list as $k=>$item)
                                            <option value="${item['sort_id']!}" <#if ($goods->sort_id == $item['sort_id']) selected </#if>>${item['sort_name']!}</option>
                                        </#list>
                                    </#if>
                                </select>
                                <div class="label-info2 template-0" style="display: none">
                                    <div class="label-info-sort">
                                        <span class="label-sort">所属种类标签：</span>
                                    </div>
                                </div>
                                <div class="card-info2 template-0" style="display: none">
                                    <div class="card-info-sort">
                                        <span class="card-sort">所属专享会员卡：</span>

                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr class="basic_info">
                            <input type="hidden" value="${goods_have_label_str!}" name="goods_have_label_str">
                            <input type="hidden" value="${goods_have_label_name!}" name="goods_have_label_name">
                            <input type="hidden" value="${cat_have_label_name!}" name="cat_have_label_name">
                            <input type="hidden" value="${sort_have_label_name!}" name="sort_have_label_name">
                            <input type="hidden" value="${cat_have_card_name!}" name="cat_have_card_name">
                            <input type="hidden" value="${sort_have_card_name!}" name="sort_have_card_name">
                            <td style="text-align: right;width: 105px;padding: 13px 0;color: #666;position: relative;vertical-align: top !important;">商品标签：</td>
                            <td class="gd_label">
                                <select name="label_id" id="label_id" class="label_option">
                                    <option value="0"  selected="selected" >请选择商品标签</option>
                                    <#if (count($goods_labels)>0)
                                        <#list ($goods_labels as $gl)
                                            <option value="${gl->id!}" >${gl->name!}</option>
                                        </#list>
                                    </#if>
                                </select>
                                <a href="javascript:void(0)" class="refresh-label">刷新</a>&nbsp;&nbsp;|
                                <a href="/admin/goods/label/add" target="_blank">新建标签 </a>&nbsp;&nbsp;|
                                <a href="/admin/goods/label/list" target="_blank">管理标签 </a>
                                {{--<div class="deliver-template-info template-0 <#if ($goods->deliver_template_id >0) hide </#if> ">--!}
                                <div class="label-info template-0" style="display: none">
                                    <div class="label-info-row">
                                        <span class="lable-choose">已选：</span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr class="basic_info">
                            <td>商品品牌：</td>
                            <td class="gd_label">
                                <input type="hidden" name="brand_id" value="${goods->brand_id ?? 0!}">
                                <span class="add_brand">{{($goods->brand_name && $goods->brand_name != '') ? $goods->brand_name : '添加品牌'!}</span>
                                <a href="/admin/goods/brand/addBrand" target="_blank">新建品牌 </a>&nbsp;&nbsp;|
                                <a href="/admin/goods/brand/list" target="_blank">管理品牌 </a>
                            </td>
                        </tr>
                        <tr class="basic_info">
                            <td style="vertical-align: top!important;">商品视频：</td>
                            <td class="">
                                <ul class="goods-item-video">
                                    <li>
                                        <input name="goods_video" type="hidden" value="${goods->goods_video!}">
                                        <input name="goods_video_img" type="hidden" value="${goods->goods_video_img!}">
                                        <input name="goods_video_size" type="hidden" value="${goods->goods_video_size!}">
                                        <input name="goods_video_id" type="hidden" value="${goods->goods_video_id!}">
                                        <input hidden class="add_video_image" value="http://${image_domain!}/image/admin/add_video.png" />
                                        <#if ($goods->goods_video)
                                            <img  src="${goods->goods_video_img!}" class="add-goods-video" style="width: 100%;height: 100%;"/>
                                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_deletes" style="display: none;" />
                                            <a href="${goods->goods_video!}" target="_blank" class="btn_playa">播放</a>
                                        <#else>
                                            <img src="http://${image_domain!}/image/admin/add_video.png" class="add-goods-video" />
                                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_deletes" style="display: none;" />
                                            <a href="" style="display: none;"  class="btn_playa" target="_blank" >播放</a>
                                        </#if>
                                        <span class="jiaogeshane" style="color: #999;">上传视频仅支持MP4格式。为保障无线端各种网络环境下正常播放，只支持上传大小不超过10M，时长不超过3分钟的视频。</span>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        {{--库存和价格--!}
                        <tr>
                            <td colspan="2" style="text-align: left !important;">
                                <div class="each_title_item">库存/价格信息</div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;">商品规格：</td>
                            <td>
                                <button  class="add-spec add_all" onClick='return false;' <#if ($has_prd)style="display: none;"</#if>>添加规格</button>
                                <table class="spec_modal hide">
                                    <tr>
                                        <td>规格名：</td>
                                        <td><span class="spec_span"><input type="text" name="spec_name[]"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete"  /></span></td></td>
                                    </tr>
                                    <tr>
                                        <td>规格值：</td>
                                        <td>
                                            <span class="spec_span"><input type="text" name='spec_val[]' item_id="0"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" /></span>
                                            <span class="add_spec_value">添加规格值</span>
                                        </td>
                                    </tr>
                                </table>
                                <span class="spec_val_modal hide"><span class="spec_span"><input type="text" name='spec_val[]'/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" /></span></span>
                                <#if ($has_prd)
                                    <div class="show-spec">
                                        <table width="100%">
                                            <#list ((array)$prd_list['spec_list'] as $k=>$v)
                                                <tr>
                                                    <td>规格名：</td>
                                                    <td><span class="spec_span"><input type="text" name="spec_name[]" spec_id="${v['index']!}" value="${v['spec_name']!}"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete"  /></span></td>
                                                </tr>
                                                <tr>
                                                    <td>规格值：</td>
                                                    <td>
                                                        <#list ((array)$v['spec_vals'] as $k1 => $v1)
                                                            <span  class="spec_span"><input type="text" name='spec_val[]'  spec_id="${v['index']!}" item_id="${k1!}" value="${v1!}"/><img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" /></span>
                                                        </#list>
                                                        <span class="add_spec_value">添加规格值</span>
                                                    </td>
                                                </tr>
                                            </#list>
                                            <tr class="last_tr">
                                                <td colspan="2"><button class="add-spec add_one" onClick='return false;' <#if ($has_prd) spec_id = "{{count($prd_list['spec_list'])!}"  <#else> spec_id = "1" </#if>>添加规格选项</button></td>
                                            </tr>
                                        </table>
                                    </div>
                                <#else>
                                    <div class="show-spec" style="display:none">
                                        <table width="100%">
                                            <tr>
                                                <td>规格名：</td>
                                                <td>
                                                    <span class="spec_span">
                                                        <input type="text" name="spec_name[]" spec_id="0"/>
                                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete"  />
                                                    </span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>规格值：</td>
                                                <td>
                                                    <span  class="spec_span">
                                                        <input type="text" name='spec_val[]'  spec_id="0" item_id="0"/>
                                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete" />
                                                    </span>
                                                    <span class="add_spec_value">添加规格值</span>
                                                </td>
                                            </tr>
                                            <tr class="last_tr">
                                                <td colspan="2"><button class="add-spec add_one" spec_id = "1"  onClick='return false;'>添加规格选项</button></td>
                                            </tr>
                                        </table>
                                    </div>
                                </#if>
                            </td>
                        </tr>
                        <tr class="spec_price" <#if (!$has_prd) style="display: none" </#if>>
                            <td style="vertical-align: top!important;">规格价格：</td>
                            <td>
                                {{--<button  class="add-spec add_spec_price" onClick='return false;'>添加规格价</button>--!}
                                <table class="spec_price_modal" style="display: none">
                                    <input type="hidden" name="shop_flag" value="${shop_flag!}">
                                    <input type="hidden" name="profit" value="${profit!}">
                                    <tr>
                                        <td class="spec_item"></td>
                                        <td><input type="text" name="prd_price[]" onblur="prd_price_blur(this)" /></td>
                                        <td><input type="text" name="prd_cost_price[]" onblur="checkProfit(this)" /></td>
                                        <td><input type="number" name="prd_number[]" min="0"/></td>
                                        <td><input type="text" name="prd_sn[]" onchange="prdChange(this);" /></td>
                                        <td>
                                            <div class="spec-img">
                                                <input type="hidden" name="prd_img[]"/>
                                                <input type="hidden" name="prd_desc[]"/>
                                                <img class='add_spec_img' src="http://${image_domain!}/image/admin/add_img_gg.png" alt="" width="36px" />
                                                <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete prd_img_delete" style='display: none;' />
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                                <div class="spec-price" >
                                    <table width="100%">
                                        <tr>
                                            <td class="spec_name_th">${prd_list['spec_name']!}</td>
                                            <td><em>*</em>价格(元)</td>
                                            <td>成本价格(元)</td>
                                            <td><em>*</em>库存</td>
                                            <td>规格编码</td>
                                            <td>规格图片</td>
                                        </tr>
                                        <#if ($has_prd)
                                            <#list ((array)($prd_list['prd_list']) as $item)
                                                <tr>
                                                    <td class="spec_item">${item['prd_name']!}</td>
                                                    <td><input type="text" onblur="prd_price_blur(this)" name="prd_price[]" value="${item['prd_price']!}" spec_val_id="${item['spec_val_id']!}" <#if ($goods->is_control_price) disabled </#if>/></td>
                                                    <td><input type="text" name="prd_cost_price[]" value="${item['prd_cost_price']!}" onblur="checkProfit(this)" spec_val_id="${item['spec_val_id']!}"/></td>
                                                    <td><input type="number" min="0" name="prd_number[]" spec_val_id="${item['spec_val_id']!}" value="${item['prd_number']!}"/></td>
                                                    <td>
                                                        <input type="text" name="prd_sn[]" spec_val_id="${item['spec_val_id']!}" value="${item['prd_sn']!}" onchange="prdChange(this);"/>
                                                    </td>
                                                    <td>
                                                        <div class="spec-img">
                                                            <input type="hidden" name="prd_img[]" spec_val_id="${item['spec_val_id']!}" value="${item['prd_img']!}"/>
                                                            <input type="hidden" name="prd_desc[]"spec_val_id="${item['spec_val_id']!}"  value="${item['prd_desc']!}"/>
                                                            <img class='add_spec_img' <#if ($item['prd_img'])src="${item['prd_img']!}"<#else> src="http://${image_domain!}/image/admin/add_img.png" </#if> alt="" width="36px" />
                                                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete prd_img_delete" style='display: none;' />
                                                        </div>
                                                    </td>
                                                </tr>
                                            </#list>
                                        </#if>
                                        <tr class="last_spec">
                                            <td colspan="5" class="batche-set">
                                                <span>批量设置：</span>
                                                <a href="javascript:void(0)" class="same_price">价格</a>
                                                <a href="javascript:void(0)" class="same_cost_price">成本价格</a>
                                                <a href="javascript:void(0)" class="same_number">库存</a>
                                                <a href="javascript:void(0)" class="same_img">规格图片</a>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td ><em>*</em>商品库存：</td>
                            <td><input type="number" name="goods_number"  value="${goods->goods_number!}" min="0"/><span style="color:#999;margin-left:15px;">设置了规格库存商品库存将失效，不在前端展示</span></td>
                        </tr>
                        <tr>
                            <td ><em>*</em>商品价格：</td>
                            <td><input type="text" name="shop_price" value="${goods->shop_price!}" <#if ($goods->is_control_price == 1) disabled </#if>/><span style="color:#999;margin-left:15px;">设置了规格价格商品价格将失效，不在前端展示</span></td>
                        </tr>
                        <tr>
                            <td >市场价格：</td>
                            <td><input type="text" name="market_price" value="${goods->market_price!}"  /></td>
                        </tr>
                        <#if (count($grade_card))
                            <tr>
                                <td style="vertical-align: top!important;">会员价：</td>
                                <td>
                                    <div class="grade_card">
                                        <#list ($grade_card as $grade)
                                            <label for="${grade->grade!}">
                                                <input type="checkbox" id="${grade->grade!}" value="${grade->grade!}"
                                                       <#if ($prd_list['grade_card'] && in_array($grade->grade,$prd_list['grade_card'])) checked </#if>/>
                                                <span>${grade->card_name!}</span>(${grade->grade!})
                                            </label>
                                        </#list>
                                    </div>
                                    <p class="grade_card_prompt">
                                        会员价仅针对等级会员卡设定，非等级会员卡不可设置会员价。若等级会员卡也包含会员折扣，则会员价和会员折扣可同时享受，优先计算会员价
                                    </p>
                                </td>
                            </tr>
                            {{--<#if ($prd_list['grade_card'])--!}
                            <tr  class="tr_grade" <#if (($prd_list['prd_list'] && $prd_list['grade_card']) || $prd_list['grade_card'])) style="display:table-row" </#if> >
                                <td style="vertical-align: top!important;">会员价设置：</td>
                                <td>
                                    <div class="grade_price">
                                        <table class="grade_price_clone hide">
                                            <tr class="prd_list_grade">
                                                <td class="grade_name_td">红色</td>
                                                <td class="grade_goods_price">100</td>
                                                <#list ($grade_card as $grade)
                                                    <td class="rank_ipt_${grade->grade!} grade_prd" grade="${grade->grade!}" <#if ($prd_list['grade_card'] && in_array($grade->grade,$prd_list['grade_card'])) <#else> style="display: none;" </#if>>
                                                        <input type="text" class="grade_price_value"/>元
                                                    </td>
                                                </#list>
                                                <td class="same" ><a href="##" class="same_tr_price">统一会员价</a></td>
                                            </tr>
                                        </table>
                                        <table width="100%" class="card_goods_price_tb">
                                            <tr class="grade_first_tr">
                                                <#if (!is_null($has_prd) && $has_prd)
                                                    <td class="grade_price_td" >${prd_list['spec_name']!}</td>
                                                <#elseif>(is_null($has_prd))
                                                    <td class="grade_price_td" >规格名</td>
                                                <#else>
                                                    <td class="grade_price_td" style="display: none;">规格名</td>
                                                </#if>
                                                <td class="card_goods_price"><#if ($has_prd) 规格价格(元) <#else> 商品价格(元) </#if></td>
                                                <#if ($grade_card)
                                                    <input type="hidden" value="${grade_card!}" class="hid_grade" />
                                                    <#list ($grade_card as $grade)
                                                        <td class="rank_name_${grade->grade!}" grade="${grade->grade!}" >${grade->card_name!}</td>
                                                    </#list>
                                                </#if>
                                                <td class="same"  <#if (count($prd_list['grade_card']) <= 1) hidden </#if>></td>
                                            </tr>
                                            <#if ($has_prd )
                                                <#list ((array)($prd_list['prd_list']) as $item)
                                                    <tr class="prd_list_grade">
                                                        <td class="grade_name_td">${item['prd_name']!}</td>
                                                        <td class="grade_goods_price">${item['prd_price']!}</td>
                                                        <#if ($item['grade_card_price'])
                                                            <#list ($item['grade_card_price']['grade_price'] as $k=>$price)
                                                                <td class="rank_ipt_${k!} grade_prd" grade="${k!}">
                                                                    <input type="text" class="grade_price_value" value="${price!}"/>元
                                                                </td>
                                                            </#list>
                                                        </#if>
                                                        <td class="same" <#if (count($prd_list['grade_card']) <= 1) hidden </#if>><a href="##" class="same_tr_price">统一会员价</a></td>
                                                    </tr>
                                                </#list>
                                            <#elseif>(!is_null($has_prd))
                                                <tr class="prd_list_grade">
                                                    <td class="grade_goods_price">${goods->shop_price!}</td>
                                                    <#if ($no_spec_grade)
                                                        <#list ($no_spec_grade['grade_price'] as $k=>$price)
                                                            <td class="rank_ipt_${k!} grade_prd" grade="${k!}">
                                                                <input type="text" class="grade_price_value" value="${price!}"/>元
                                                            </td>
                                                        </#list>
                                                    </#if>
                                                    <td class="same" <#if (count($prd_list['grade_card']) <= 1) hidden </#if>><a href="##" class="same_tr_price">统一会员价</a></td>
                                                </tr>
                                            </#if>
                                            <tr class="last_grade_tr">
                                                {{--<td colspan="5" class="batche-set">--!}
                                                {{--<span>批量设置：</span>--!}
                                                {{--<a href="javascript:void(0)" class="same_prd_member">同一规格统一会员价</a>--!}
                                                {{--<a href="javascript:void(0)" class="same_grade_member">同一会员等级统一会员价</a>--!}
                                                {{--</td>--!}
                                                <td class="same_td_first" style="display:none"></td>
                                                <td></td>
                                                <#list ($grade_card as $grade)
                                                    <td class="same_td_price same_${grade->grade!}"><a href="##" grade="${grade->grade!}">统一会员价</a></td>
                                                </#list>
                                                <td class="same" hidden></td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            {{--</#if>--!}
                        </#if>
                        {{--更多配置--!}
                        <tr>
                            <td colspan="2" style="text-align: left!important;" if_show="1">
                                <div class="show_more kucun_more">
                                    <text>收起更多配置</text>
                                    <img src="http://${image_domain!}/image/admin/info_up.png" alt="">
                                </div>
                            </td>
                        </tr>
                        <tr class="kucun_info">
                            <td >最小限购数量：</td>
                            <td><input type="number" name="limit_buy_num" value="${goods->limit_buy_num!}" min="1" placeholder="" /><span style="color:#999;margin-left:15px;">0或不填表示不限制购买数量</span></td>
                        </tr>
                        <tr class="kucun_info">
                            <td >最大限购数量：</td>
                            <td><input type="number" name="limit_max_num" value="${goods->limit_max_num > 0 ? $goods->limit_max_num : ''!}" min="1" placeholder="" /><span style="color:#999;margin-left:15px;">0或不填表示不限制购买数量</span></td>
                        </tr>
                        <tr class="kucun_info">
                            <td >成本价格：</td>
                            <td><input type="text" name="cost_price" value="${goods->cost_price!}"  /></td>
                        </tr>
                        <#if ($base_sale_flag == 1)
                            <tr class="kucun_info">
                                <td >初始销量：</td>
                                <td><input type="number" name="base_sale"  value="${goods->base_sale!}" min="0"/><span style="color:#999;margin-left:15px;">设置后，您的用户看到的销量=初始销量+下单量，初始销量不计入统计。</span></td>
                            </tr>
                        </#if>
                        <tr class="goods_prd_sn kucun_info" <#if ($has_prd) style="display: none" </#if>>
                            <td style="vertical-align: top!important;">商品规格编码：</td>
                            <td><input type="text" name="goods_prd_sn" value="${noSpecProduct['prd_sn']!}" /></td>
                        </tr>
                        {{--配送信息--!}
                        <tr>
                            <td colspan="2" style="text-align: left !important;">
                                <div class="each_title_item">配送信息</div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;"><em>*</em>运费模板：</td>
                            <td class="goods_deliver">
                                <select name="deliver_template_id">
                                    <option value="0">店铺默认运费模板</option>
                                    <#list ($template_list->template_list as $item)
                                        <option value="${item->deliver_template_id!}"
                                                <#if  ($goods->deliver_template_id == $item->deliver_template_id)selected="selected"</#if> >
                                            <#if  ($item->flag == 0)普通--<#else>重量--</#if>${item->template_name!}
                                        </option>
                                    </#list>
                                </select>
                                <a href="javascript:void(0)" class="refresh">刷新</a>&nbsp;&nbsp;|
                                <a href="/admin/goods/deliver/template/add" target="_blank">新建模板 </a>&nbsp;&nbsp;|
                                <a href="/admin/goods/deliver/template/list" target="_blank">管理模板 </a>
                                <div class="deliver-template-info template-0 <#if ($goods->deliver_template_id >0) hide </#if> ">
                                    <div class="template-info-row">
                                        ${template_list->shop_template->content_desc->desc!}
                                        <div class="pull-right">
                                            <a href="/admin/goods/deliver/template/list"
                                               target="_blank">查看详情</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="shuaxin">
                                    <#list  (($template_list->template_list) as $item)
                                        <div class="deliver-template-info template-${item->deliver_template_id!} <#if  ($goods->deliver_template_id != $item->deliver_template_id) hide </#if> ">
                                            <#list  ((array)($item->content_desc) as $key =>$item2)
                                                <#if  ($key==0)
                                                    <div class="template-info-row">
                                                        ${item2!}
                                                        <div class="pull-right">
                                                            <a href="/admin/goods/deliver/template/edit?deliver_template_id=${item->deliver_template_id!}"
                                                               target="_blank">查看详情</a>
                                                        </div>
                                                    </div>
                                                <#else>
                                                    <#if ($key==1)
                                                        <hr>
                                                        <div class="template-info-row" style="margin-bottom: 8px;">
                                                            指定可配送区域运费:
                                                        </div>
                                                    </#if>
                                                    <div class="template-info-row">
                                                        ${item2!}
                                                    </div>
                                                </#if>
                                            </#list>
                                            <#if ($item->fee_0_condition_desc)
                                                <#list  ($item->fee_0_condition_desc as $key=>$item2)
                                                    <#if ($key==0)
                                                        <hr>
                                                        <div class="template-info-row" style="margin-bottom:8px;">
                                                            指定条件包邮可配送区域运费:
                                                        </div>
                                                    </#if>
                                                    <div class="template-info-row">
                                                        ${item2!}
                                                    </div>
                                                </#list>
                                            </#if>
                                        </div>
                                    </#list>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td >商品重量：</td>
                            <td><input type="text" name="goods_weight"  value="${goods->goods_weight!}"/><text style="margin-left:15px">Kg</text></td>
                        </tr>
                        {{--其他信息--!}
                        <tr>
                            <td colspan="2" style="text-align: left !important;">
                                <div class="each_title_item">其他信息</div>
                            </td>
                        </tr>
                        <tr class="card_goods">
                            <td style="vertical-align: top!important;">会员专享商品：</td>
                            <td><input type="checkbox" name="is_card_exclusive" <#if ($goods->is_card_exclusive ==1) checked </#if> style="padding:0;border:0;margin-top:8px;">
                                <span style="line-height:30px;">用户持有会员卡才可以购买此商品</span>
                                <input type="hidden" value="${goods_have_card_str!}" name="goods_have_card_str">
                                <input type="hidden" value="${goods_have_card_name!}" name="goods_have_card_name">
                                <div>
                                    <div class="card_select_box">
                                        <select name="" id="card_id">
                                            <option value="0">请选择会员卡</option>
                                            <#list ($goods_cards as $gc)
                                                <option value="${gc->id!}">${gc->card_name!}</option>
                                            </#list>
                                        </select>
                                        <a href="javascript:void(0)" class="refresh-card">刷新</a>&nbsp;&nbsp;|
                                        <a href="/admin/user/member/create?top_index=5" target="_blank">新建会员卡 </a>&nbsp;&nbsp;|
                                        <a href="/admin/user/member/list?top_index=5" target="_blank">管理会员卡 </a>
                                    </div>
                                    <div class="card-info template-0" style="display: none">
                                        <div class="card-info-row">
                                            <span class="card-choose">已选：</span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>

                        {{--分销改价--!}
                        <tr>
                            <td style="vertical-align: top!important;">分销改价：</td>
                            <td>
                                <div>
                                    <input type="checkbox" class="if_change_dis_price" name="can_rebate"
                                           <#if  ($goods->can_rebate == 1) checked </#if>
                                           style="border:none;padding:0">允许分销员分销商品时修改商品售价
                                    <input name="rebate_price" hidden>
                                </div>
                            </td>
                        </tr>
                        <tr class="set_dis_price">
                            <td style="vertical-align: top!important;" ></td>
                            <td <#if  (!($goods->can_rebate == 1)) class="buxianshi" </#if>>
                                <div class="dis_price" <#if  (!($goods->can_rebate == 1)) style="display: none" </#if>>
                                    <table width="100%">
                                        <tr>
                                            <td class="spec_name_th" <#if (!$has_prd) style="display: none" </#if>>规格</td>
                                            <td>建议售价(元)</td>
                                            <td>最低售价(元)</td>
                                            <td>最高售价（元）</td>
                                        </tr>
                                        <#if  ($goods->can_rebate == 1 && is_array($prd_list['rebate_price']))
                                            <#list ($prd_list['rebate_price'] as $rebate_item)
                                                <tr>
                                                    <td class="spec_item" spec_val_id="${rebate_item['spec_val_id']!}" <#if (!$has_prd) style="display: none" </#if>>
                                                        ${rebate_item['spec_desc']!}}
                                                    </td>
                                                    <td><input type="text" value="${rebate_item['advise_price']!}"/></td>
                                                    <td><input type="text" value="${rebate_item['min_price']!}"/></td>
                                                    <td><input type="text" value="${rebate_item['max_price']!}"/></td>
                                                </tr>
                                            </#list>
                                        </#if>
                                        <tr class="last_spec" <#if (!$has_prd) style="display: none" </#if>>
                                            <td colspan="5" class="batche-set">
                                                <span>批量设置：</span>
                                                <a href="javascript:void(0)" class="rebate_min_price">最低售价</a>
                                                <a href="javascript:void(0)" class="rebate_max_price">最高售价</a>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="vertical-align: top!important;">上下架：</td>
                            <td class="">
                                <ul class="shelves">
                                    <li class="shelves_li">
                                        <input type="radio" name="sale_type" id="now_sale"  value="0" <#if ($goods->sale_type == 0 || ($goods->is_on_sale == 1 && $goods->sale_type != 1)) checked </#if> />
                                        <label for="now_sale">立即上架售卖</label>
                                    </li>
                                    <li class="shelves_li">

                                        <input type="radio" name="sale_type" id="custom_sale" value="1" <#if ($goods->sale_type == 1) checked </#if> />
                                        <label for="custom_sale">自定义上架时间</label>
                                        <label class="choose-time" for="sale_time"  onclick="picker();"><#if ($goods->sale_time)${goods->sale_time!}<#else>选择上架售卖时间</#if></label>
                                        <input type="hidden" name="sale_time" id="sale_time" value="${goods->sale_time!}" >
                                    </li>
                                    <li class="shelves_li">

                                        <input type="radio" name="sale_type" id="stop_sale" value="2" <#if ($goods->sale_type == 2 || ($goods->is_on_sale === 0 && $goods->sale_type != 1)) checked </#if>/>
                                        <label for="stop_sale">暂不售卖，放入仓库</label>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                    </table>

                </div >
                <div class="goods-edit-detail clearfix">
                    <div class="col-sm-3" style="width:385px; margin:0px 2px 2px 10px; padding:0;">
                        <div class="phone_box">
                            <div class="phone_top"
                                 style="background: url(/image/admin/new_shop_beautify/page_name.png) no-repeat;">
                                <span class="phone_page_title">商品详情</span>
                            </div>
                            <div class="phone">
                                <div id="drag_area_container">
                                    <div class="item_goods">
                                         <div class="item_img">
                                             <img src="http://${image_domain!}/upload/1221495/image/20181129/crop_P1vtYO9ddPYZtuAN.jpeg" alt="">
                                         </div>
                                         <div class="item_name">
                                             <span></span>
                                         </div>
                                        <div class="item_price">
                                            ￥<span></span>
                                        </div>
                                        <div class="item_mess">
                                            <div class="kucun">库存：<span></span></div>
                                            {{--<div class="yunfei">运费：<span>0</span></div>--!}
                                        </div>
                                    </div>
                                    <div id="drag_area" >

                                    
                                        <div class="show_area area_two  <#if (!$page->page_id) hide </#if>">
                                                <div>已选择模板:<span style="color: #5a8bff;padding-left:5px;">${page->page_name!}</span></div>
                                        </div>
                                    
                                        <div class="show_area area_one <#if ($page->page_id) hide </#if>">
                                                <h1>自定义内容区域</h1>
                                                <p>可在右侧选择商品页模板</p>
                                                <p>未添加内容时,不显示此模块内容</p>
                                        </div>

                                    </div>
                                    <div class="detail-show-box">
                                        {{--<div class="drag_img">--!}
                                            {{--<img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">--!}
                                            {{--<img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">--!}
                                        {{--</div>--!}
                                        <div class="sp_title">
                                            <span>商品详情</span>
                                        </div>
                                        <div class="show-box-white" id="show">
                                            <div class="show-begin">
                                                <p>可在右侧编辑商品详情</p>
                                                <p>未添加内容时,不显示此模块内容</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="detail-edit fl">
                        <div class="right-message clearfix">
                            <span class="message-title">商品基本信息</span>
                            <span class="message-content">商品信息为固定样式仅供参考，请以实际效果为准</span>
                        </div>
                        <div class="right-message clearfix">
                            <span class="message-title">模块位置</span>
                            <span class="message-content">
                                <label for="position1" style="margin-left: 28px">
                                     <input type="radio" name="image_position" id="position1">自定义内容在上
                                </label>
                               <label for="position2" style="margin-left: 10px">
                                      <input type="radio" name="image_position" id="position2">商品详情在上
                                </label>
                            </span>
                        </div>
                        <div class="right-message2 clearfix zdy">
                            <span class="message-title-line">自定义内容</span>
                            <div class="message-content2">
                                <span class="goods_title">商品页模板</span>

                                <#if ($page->page_id)
                                    <span class="template-show" style="display: block">
                                        <span class="template_name">${page->page_name!}</span>
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete1">
                                    </span>
                                    <#else>
                                    <span class="template-show">
                                        <span class="template_name"></span>
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete1">
                                    </span>
                                </#if>

                                <span class="choose_template">选择模板</span>
                                <a class="refresh_temp add_goods">刷新</a>
                                <a class="add_goods" href="/admin/manage/decorate/page" target="_blank">添加模板</a>
                            </div>
                        </div>
                        <div class="right-message2 clearfix sp">
                            <span class="message-title-line">商品详情</span>
                            <div class="detail-edit-box">
                                <input type="hidden" id="goods_desc" name="goods_desc" value="${goods->goods_desc!}">
                                <textarea id="editor" name="content" style="width:200px;height:466px;" onchange="getContent()"></textarea>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="goods-save fix_footer">
        <a  class="btn-save" onclick="getContent()" >保存</a>
        <a  class="btn-next">下一步</a>
        <a  class="btn-prev" style="display: none;">上一步</a>
        <a  class="btn-prev" id="preview_template" style="display: none;">预览效果</a>
    </div>
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
<div id="choose_page" style="display: none">
    <div class="goods-box-edit">
        <div class="goods-edit-basic goods_tem">
           <div class="goods_search">
               <form action="">
                   <span style="font-size: 14px;margin-left: 10px">页面名称</span>
                   <input type="text" name="pageKeywords" value="${pageKeywords!}"  placeholder="请输入页面名称"  />
                   <span style="font-size: 14px;margin-left: 10px">页面分类</span>
                   <select name="page_cat_id" id="page_cat_id">
                       <option value="0">请选择分类</option>
                       <#list ($page_cat_list as $k=>$name)
                           <option value="${k!}">${name!}</option>
                       </#list>
                   </select>
                   <span class="search_template">搜索</span>
               </form>
           </div>
            <table class="tb-decorate-list choose_c">
                <thead>
                <tr>
                    <th width="25%">{{ trans("admin/shop_decorate.shop_decorate_list.page_name")!}</th>
                    <th width="30%">{{ trans("admin/shop_decorate.shop_decorate_list.create_time")!}</th>
                    <th width="20%">{{ trans("admin/shop_decorate.shop_decorate_list.is_index")!}</th>
                </tr>
                </thead>
                <tbody class="temp_list">
                <#list ($pageList as $item)
                    <tr data-back="true" page_id="${item->page_id!}"  page_type="${item->page_type!}" page_tpl_type="${item->page_tpl_type!}" page_content="${item->page_content!}">
                        <td>
                            <span class="tem_name">${item->page_name!}</span>
                        </td>
                        <td>${item->create_time!}</td>
                        <td><#if ($item->page_type)是<#else>否</#if></td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <#if (count($pageList)<=0)
                <div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                    <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                        <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                    </div>
                    <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                </div>
            </#if>
        </div>
    </div>
</div>
<script type="text/javascript">
    var hasSaved = true;
    var has_prd = "${has_prd!}";
    var is_has_prd = 0;
    if(has_prd !='' ){
        is_has_prd = $('input[name="prd_price[]"]').length-1;
    }
    
    function unitPop(obj) {
        var val = $(obj).val();

        if (val == '自定义') {
            $('#unit_pop').show();
            return;
        }else{
            $('#unit_pop').hide();
            return;
        }
    }

    $("#preview_template").click(function (event) {
        var page_id = $("input[name='page_id']").val();
        util.ajax_json('/admin/manage/decorate/show', function (res) {
            if (res && res.error == 2) {
                if (!res.content) {
                    util.mobile_alert(res.message);
                    return false;
                }
                // util.mobile_alert(res.content.code_url);
                layui.use('layer', function () { //独立版的layer无需执行这一句
                    var layer = layui.layer;
                    layer.open({
                        type: 1
                        , title: '手机扫码预览'
                        , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                        , id: 'preview2' //防止重复弹出
                        , content: '<div style="padding: 20px 40px;"><img style="width:180px;height: 180px" src="' + res.content + '"></div>'
                        , shade: 0 //不显示遮罩
                    });
                })
            } else {
                util.mobile_alert(res.message);
            }
        }, {page_id: page_id});
    });


    $("#unit_input").on("input ", function() {
        var $this = $(this),
            _val = $.trim($this.val()),
            len = 0;
        for (var i=0; i<_val.length; i++) {
            var c = _val.charCodeAt(i);
            //单字节加1
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
                len++;
            }
            else {
                len+=2;
            }
        }
        if (len > 6) {
            // $this.val(_val.substring(0, 3));
            $this.val(cut_str(_val,3))
        }
    });
    function cut_str(str, len){
        var char_length = 0;
        for (var i = 0; i < str.length; i++){
            var son_str = str.charAt(i);
            encodeURI(son_str).length > 2 ? char_length += 1 : char_length += 0.5;
            if (char_length >= len){
                var sub_len = char_length == len ? i+1 : i;
                return str.substr(0, sub_len);
                break;
            }
        }
    }

</script>
<#include "/admin/footer.ftl">
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
    var is_submit = false;
    console.log(set_colors)

</script>
<script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp&key=GO6BZ-EUL6P-ZLPDD-VYOYE-CCZBH-UFBOU"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/video_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.1.3"></script>
<script language="JavaScript" src="/js/admin/jVideoManager.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/js/kindeditor/lang/{{ config("app.locale")!}.js"></script>
<script type="text/javascript" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>
<script language="JavaScript" src="{{asset('js/admin/goods_info.js')!}?v=6.5.4"></script>
<script language="JavaScript" src="{{asset('js/admin/goods_rebate.js')!}?v=1.3.6"></script>
<script type="text/javascript" src="/js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript">

    //版本控制
    var num = "${version['num']!}";
    var use = '${version['use']!}';
    var pageId = '${page->page_id!}';
    var goods_id  = util.getUrlParam('goods_id');
    var image_domain = "${image_domain!}";

    if(goods_id > 0){//编辑
        if(num>=0 && num-use < 0){
            window.location.href = '/admin/authority/not?mod_name=商品数量';
        }
    }else{//新建
        if(num>=0 && num-use <= 0){
            window.location.href = '/admin/authority/not?mod_name=商品数量';
        }
    }

    //置灰or显示商品价格和库存
    AshPlacing();

    if(pageId){
        $('.show_area').hide();
    }

    //进入页面查询是否交换
    $(function () {
        var drag_area = $('#drag_area');
        var box = $('.detail-show-box');
        var is_page_up = $('#is_page_up').val();
        if(is_page_up == 0){
            box.after(drag_area);
            $('#position2').prop('checked',true);
        }else{
            $('#position1').prop('checked',true);
        }
        if($('input[name="goods_desc"]').val() != ''){
            console.log($('input[name="goods_desc"]').val());
            $(document).find('.show-box-white').html($('input[name="goods_desc"]').val());
        }
    })

    function importGoods() {
        layer.prompt({
            value: '',
            title: '请输入淘宝或天猫商品的URL',
        }, function(value, index, elem){
            layer.close(index);
            util.show_loading = true;
            util.ajax_json("/admin/ajax/goods/import",function (d) {
                if(d && d.error == 0){
                    window.location.href = '/admin/goods/manage/edit?goods_id='+d.content;
                }else if(d && d.error>0){
                    layer.msg(d.message);
                }
            },{url:value})
        });
    }

    function getContent() {
        var fullHtml = '';
        var div;
        var value;
        var show_begin = `   <div class="show-begin">
                                       <p>可在右侧编辑商品详情</p>
                                       <p>未添加内容时,不显示此模块内容</p>
                               </div>`;
        KindEditor.ready(function(K) {
            var editor = KindEditor.instances[0];
            fullHtml = editor.fullHtml();
            div = K.query('.ke-edit-iframe');
            //console.log(div);
            editor.sync();
            //value = K('#editor').val();
            //var cmd = K.cmd(document);
        });
        //console.log($('#editor').val());
        //$('#editor').val(fullHtml);
        //console.log(value);
        if(fullHtml == "<!doctype html>"){
            $('#show').html(show_begin);
        }else{
            console.log(fullHtml);
            $('#show').html(fullHtml);
        }
    }

    $(document).on('blur','.grade_price_value',function(){
        if($(this).val() == ''){
            util.mobile_alert('请输入等级会员卡价格');
            $(this).focus();
            return false;
        }else if(parseFloat($(this).val()) > parseFloat($(this).parent().parent().find(".grade_goods_price").text())) {
            util.mobile_alert('等级会员卡价格不能大于原商品价格');
            $(this).focus();
            return false;
        }
    });
    $("input[name='goods_number']").blur(function() {
        var goods_number = $("input[name='goods_number']").val();
        if(goods_number < 0) {
            util.mobile_alert("库存不能少于0");
        }
    });
    $("input[name='limit_buy_num']").blur(function() {
        var limit_buy_num = parseInt($("input[name='limit_buy_num']").val());
        if($("input[name='limit_buy_num']").val() != '' && (limit_buy_num < 0 || limit_buy_num != $("input[name='limit_buy_num']").val())) {
            util.mobile_alert("最小限购数量不合法");
        }
    });
    $("input[name='unit_temp']").blur(function() {
        if($("input[name='unit_temp']").val() == '' ) {
            util.mobile_alert("自定义单位不能为空");
        }
    });

    util.inputChange();
    util.selectChange();
    var sale_type_val = $('input[name="sale_type"]:checked').val();
    $('input[name="sale_type"]').click(function () {
        if($(this).val() != sale_type_val){
            //alert('修改过radio');
            hasSaved = false;
        }
    });
    var body_html = $('#goods_desc').val();
    var label_html = $('.choose-time').html();
    $('.goods-box-edit').click(function () {
        getContent();
        if(body_html != $('#editor').val()){
            //alert('修改过详情');
            hasSaved = false;
        }
        if(label_html != $('.choose-time').html()){
            //alert('修改过label');
            hasSaved = false;
        }
    });

    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            event.returnValue = "确认要离开吗？";
        }
    };
    var deliver_template_id = "${goods->deliver_template_id!}";
    $(".refresh").click(function () {
        util.ajax_json("/admin/frame/deliver/fee/template/template_list",function(res){
            if(res != -1){
                $('select[name="deliver_template_id"]').html("");
                var opt = '<option value="0">店铺默认运费模板</option>';
                var div = "";
                for(var i=0;i<res.length;i++){
                    if(deliver_template_id != res[i].deliver_template_id){
                        div += '<div class="deliver-template-info template-'+res[i].deliver_template_id+' hide" >';
                    }else{
                        div += '<div class="deliver-template-info template-'+res[i].deliver_template_id+'">';
                    }
                    var content_desc = res[i].content_desc;
                    content_desc.forEach(function (val,index) {
                        if(index == 0){
                            div += '<div class="template-info-row">'+val+'<div class="pull-right"><a href="/admin/goods/deliver/template/edit?deliver_template_id='+res[i].deliver_template_id+'" target="_blank">查看详情</a></div></div>'
                        }else{
                            if(index == 1){
                                div += '<hr><div class="template-info-row" style="margin-bottom: 8px;">指定可配送区域运费:</div>'
                            }
                            div += '<div class="template-info-row">'+val+'</div>';
                        }
                    })
                    var fee_0_condition_desc = res[i].fee_0_condition_desc;
                    if(fee_0_condition_desc){
                        fee_0_condition_desc.forEach(function (v,j) {
                            if(j==0){
                                div += '<hr><div class="template-info-row" style="margin-bottom:8px;">指定条件包邮可配送区域运费:</div>'
                            }
                            div += '<div class="template-info-row">'+v+'</div>';
                        })
                    }
                    console.log(div);
                    div+="</div>"
                    opt += '<option value="'+res[i].deliver_template_id+'">';
                    if(res[i].flag == 0){
                        opt += "普通--" + res[i].template_name + "</option>";
                    }else{
                        opt += "重量--" + res[i].template_name + "</option>";
                    }
                }
                $('select[name="deliver_template_id"]').html(opt);
                $(".goods_deliver .shuaxin").html(div);
                util.mobile_alert("刷新成功");
            }else{
                util.mobile_alert("刷新成功");
            }
        });
    })
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
    $('.choose_template').on('click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['选择模板', 'text-align:center; padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['745px', '430px']
                , content: $('#choose_page')//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    $('.tb-decorate-list tbody tr.goods_tr_choose').each( function () {
                        $(this).removeClass('goods_tr_choose');
                        $(this).attr('data-back','true');
                    })
                    $(".goods_tem").show();
                }
                , yes: function (index, layero) {
                    var count = 0;
                    $('.tb-decorate-list tbody tr').each(function () {
                        if($(this).attr("data-back") == 'false'){
                            count ++;
                        }
                    })
                    if(count == 0){
                        util.mobile_alert("请选择模板!");
                        return false;
                    }
                   var tem_name = $('tr[data-back="false"]').find(".tem_name").text();
                   var page_id = $('tr[data-back="false"]').attr("page_id");
                   $('#page_id').val(page_id);
                   $(".template_name").text(tem_name);
                   $(".template-show").show();
                   $('.area_one').addClass('hide');
                   $('.area_two').removeClass('hide');
                   $('.area_two span').text(tem_name);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    $('.refresh_temp').click(function () {
        $('input[name="pageKeywords"]').val('');
        $('#page_cat_id').val(0);
        searchTemplate('',0,'刷新');
    });
    $('.search_template').click(function () {
        var keywords = $('[name="pageKeywords"]').val();
        var page_cat_id = $('#page_cat_id option:selected').val();
        searchTemplate(keywords,page_cat_id,'搜索');
    });
    function searchTemplate(keywords,page_cat_id,op_name){
        util.ajax_json('/admin/ajax/template/refresh',function(d){
            if(d.error){
                util.mobile_alert(op_name+'失败');
                return false;
            }
            else{
                var all_page = d.content.page_list;
                var page_cat = d.content.page_cat_list;
                var html = '';
                var html1 = `<option value="0">请选择分类</option>`;
                console.log(all_page.length);
                if(all_page.length>0){
                    for (var i in all_page){
                        html+=`<tr data-back="true" page_id="${all_page[i].page_id}"  page_type="${all_page[i].page_type}" page_tpl_type="${all_page[i].page_tpl_type}" page_content='${all_page[i].page_content}'>
                        <td>
                        <span class="tem_name">${all_page[i].page_name}</span>
                        </td>
                        <td>${all_page[i].create_time}</td>
                        <td>${all_page[i].page_type ? '是' : '否'}</td>
                        </tr>`;
                    }
                    $('.temp_list').html(html);
                    if($('.goods_tem').find('.no_data_style').length>=0){
                        $('.goods_tem').find('.no_data_style').remove();
                    }
                }else{
                    var html2 = `<div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                        <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                        </div>
                        <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                    </div>`;
                    $('.temp_list').html('');
                    if($('.goods_tem').find('.no_data_style').length<=0){
                        $('.goods_tem').append(html2);
                    }
                }
                for (var i in page_cat){
                    html1 +=`<option value="${i}" ${i == page_cat_id ? 'selected' : ''}>${page_cat[i]}</option>`;
                }
                $('#page_cat_id').html(html1);
                util.mobile_alert(op_name+'成功');
            }
        },{keywords:keywords,page_cat_id:page_cat_id});
    }
    $(".img-delete1").click(function () {
        $(this).parent().hide();
        $('.area_one').removeClass('hide');
        $('.area_two').addClass('hide');
        $('#page_id').val(0);
    })
    $('#choose_page').on('click','.tb-decorate-list tbody tr', function(){
        var flag_back = $(this).attr('data-back');
        $('.tb-decorate-list tbody tr.goods_tr_choose').each( function () {
            $(this).removeClass('goods_tr_choose');
            $(this).attr('data-back','true');
        })
        if (flag_back == 'true') {
            $(this).addClass('goods_tr_choose');
            $(this).attr('data-back', 'false');
        }
    });
    $('.add_brand').click(function(){
        let checkedId = $('input[name="brand_id"]').val();
        layui.use('layer', function(){
            var $ = layui.jquery, layer = layui.layer;
            var url = '/admin/goods/brand/selectBrand?is_single=1&record_select_value=' + checkedId;
            layer.open({
                type:2,
                title: ['添加品牌', 'text-align:center;padding: 0px;'],
                offset: 'auto',
                area: ['650px','450px'],
                content: url,
                btn: ['确定', '取消'],
                btnAlign: 'r' ,
                shade: [0.3, '#000'],
                yes:function(index, layero){
                    var body = layer.getChildFrame('body', index);
                    var brand_id = body.find('#record_select_value').val();
                    var brand_name= body.find('[name="single_brand_name"]').val();
                    $('[name="brand_id"]').val(brand_id);
                    $('.add_brand').text(brand_name ? brand_name : '添加品牌');
                    layer.close(index);
                }
            })
        })
    })
    function checkProfit(that){
        if($('input[name="shop_flag"]').val() == 2){
            if($('input[name="profit"]').val()){
                let profit_price = parseFloat($(that).val() * (1 + $('input[name="profit"]').val() / 100));
                let prd_price = parseFloat($(that).parent().prev().find('input[name="prd_price[]"]').val());
                if(profit_price.toFixed(2) > prd_price){
                    util.mobile_alert("商品价格未达到分销比例"+$('input[name="profit"]').val()+"%!");
                    $(that).focus();
                }
            }
        }
    }
    function checkProfit1(that){
        if($('input[name="shop_flag"]').val() == 2){
            if($('input[name="profit"]').val()){
                let profit_price = parseFloat($(that).parent().next().find('input[name="prd_cost_price[]"]').val() * (1 + $('input[name="profit"]').val() / 100));
                let prd_price = parseFloat($(that).val());
                if(profit_price.toFixed(2) > prd_price){
                    util.mobile_alert("商品价格未达到分销比例"+$('input[name="profit"]').val()+"%!");
                    $(that).focus();
                }
            }
        }
    }
    $(document).on('mouseover','#drag_area',function (e) {
    e.preventDefault();
    if ($(this).hasClass("dd_select")) return;
    $(".detail-show-box").removeClass("dd_select");
    $(this).addClass("dd_select");
})
$(document).on('mouseover','.detail-show-box',function (e) {
    e.preventDefault();
    if ($(this).hasClass("dd_select")) return;
    $("#drag_area").removeClass("dd_select");
    $(this).addClass("dd_select");
});
$(document).on('mouseleave','#drag_area,.detail-show-box',function (e) {
    e.preventDefault();
    $(".detail-show-box").removeClass("dd_select");
    $("#drag_area").removeClass("dd_select");
});
$(document).on("click", "#position1,#position2", function (e) {
    if($("#position1").prop('checked')){
        $("#drag_area_container").append($('.detail-show-box'));
        $(this).prop('checked',true);
    }else if($("#position2").prop('checked')){
        $("#drag_area_container").append($('#drag_area'));
        $(this).prop('checked',true);
    }

});

</script>
