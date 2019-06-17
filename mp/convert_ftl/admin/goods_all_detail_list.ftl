<#include "/admin/header.ftl">
<link href="/css/admin/goods_list.css?v=1.2.3" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    input[name='page']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging tr td,.tb_paging tr td a{
        color: #333;
        font-size: 14px;
    }
    .tb_paging{
        border: 0 !important;
    }
    .create-good{
        border:1px solid #5a8bff;
    }
    .goods_fr input {
        height: 30px;
        width: 90px;
        text-align: center;
        border-radius: 3px;
        padding: 0px;
        margin: 0;
    }
    .create-good:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    .create-good:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    #search {
        margin-left: 5px;
        margin-bottom: 4px;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        line-height:30px;
    }
    .goods_new{
        padding-left: 12px;
    }
    .bottom-table{
        border-left: none;
    }
    .goods_fr{
        margin-left:0;
        display:inline-block;
        margin-top: 0;
    }
    .search-bl{
        width: 150px;
        margin-top: 0;
    }
    .primary {
        width: 120px;
    }
    .goods_fr input{
        border-radius: 3px;
        border: 1px solid #ccc;
        height:30px;
    }
    .create-good,.system_info{
        float:left;
        margin-right: 10px;
        margin-left:18px;
    }
    .create-good,.btn_searchinfo{
        border-radius:2px;
    }
    .system_info{
        margin:10px 0 0 0;
    }
    .card_exclusive{
        left: 1px;
        bottom: 1px;
    }
    .list-center-sel{
        margin-left: 0;
        margin-top: 0;
    }
    .list-center-sel+.list-center-sel{
        margin-left: 18px;
    }
    .sel_time{
        margin-left: 0;
        display: inline-block;
    }
    .sel_font{
        text-align: left;
    }
    .btn-excel {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        line-height:25px;
    }
    .btn-excel:hover {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
        width: 85px;
        height: 30px;
    }
    .btn-excel:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    .list-bottom-show tbody > tr > td:nth-child(4){
        word-break: break-all;
    }
    .change-input{
        width: 170px;
        height: 70px;
    }
    .goods_main_img img{
        width:50px;
    }
    .hide_title{
        font-size: 10px;
        color:gray;
        cursor: pointer;
    }
    .role_ul li{
        margin-top: 12px;margin-left:6.5px;
    }
    .choose_field{
        height: auto;
        background-color: #5A8BFF;
        color: #fff;
        /*float: right;*/
        margin-right: 20px;
        padding: 5px 15px;
        -webkit-border-radius: 0px;
        -moz-border-radius: 0px;
        border-radius: 2px;
        margin-top:10px;
        margin-left: 10px;
        margin-top: 10px;
        display: block;
        width: 100px;
        height: 31px;
        float: left;
    }
    .choose_field:hover{
        color: #fff;
        cursor: pointer;
    }
    .goods_img img {
        width:50px;
    }
    .sure_up{
        height: auto;
        width:10%;
        font-size: 6px;
        background-color: #5A8BFF;
        color: #fff;
        /*float: right;*/
        margin-right: 1px;
        padding: 5px 5px;
        -webkit-border-radius: 0px;
        -moz-border-radius: 0px;
        border-radius: 2px;
        margin-top:13px;
    }
</style>

<div class="goods-container">
    <div class="title" style="width:250%;">
            <span>
                {{ trans("admin/shop.list-top.li_top_name")!}
            </span>/
        <span>
                <#if ($nav == 0)
                {{ trans("admin/shop.list-top.li_name")!}
            <#elseif>($nav == 1)
                {{ trans("admin/shop.list-top.li_name2")!}
            <#elseif>($nav == 2)
                {{ trans("admin/shop.list-top.li_name3")!}
            </#if>
            </span>
    </div>
    <div class="main-container" style="width:250%;">
        <form name="form" action="/admin/goods/manage/list?nav=${nav!}&top_index=2"  id="form1" method="post">
            <input type="hidden" name="del">
            <input type="hidden" value="${goods_all_labels_id_str!}" name="goods_all_labels_id_str">
            <input type="hidden" value="${goods_all_labels_name_str!}" name="goods_all_labels_name_str">
            <input type="hidden" name="export" id="export">
            <input type="hidden" name="erp_export" id="erp_export">
            <div style="background-color: #fff">
                <ul class="nav_list clearfix">
                    <li <#if ($nav == 0)class="active"</#if>><a href="/admin/goods/manage/list?top_index=2">出售中</a></li>
                    <li <#if ($nav == 1)class="active"</#if>><a href="/admin/goods/manage/list?nav=1&top_index=2">已售罄</a></li>
                    <li <#if ($nav == 2)class="active"</#if>><a href="/admin/goods/manage/list?nav=2&top_index=2">仓库中</a></li>
                </ul>
            </div>
            <div class="list-center">
                {{ csrf_field()!}
                <input name="goods_id" type="hidden">
                <input name="all" type="hidden">
                <input name="nav" type="hidden" value="${nav!}">
                <ul class="search_list clearfix">
                    <li>
                        商品名称：
                        <span class="search-bl">
                            <input type="text" name='keywords' value="${keywords!}" placeholder="{{ trans("admin/shop.list-center.search")!}"
                                    class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span>
                    </li>
                    <li>
                        平台分类：
                        <select name="cat_id" class="list-center-sel">
                            <option value="0">请选择平台分类</option>
                            <#list ($cat_list as $item)
                                <#if ($item['goods_num']>0)
                                    <option value="${item['cat_id']!}"
                                            <#if ($cat_id == $item['cat_id'])selected="selected"</#if>>${item['cat_name']!}(${item['goods_num']!})</option>
                                </#if>
                            </#list>
                        </select>
                    </li>
                    <li>
                        商家分类：
                        <select name="sort_id" class="list-center-sel">
                            <option value="0">请选择商家分类</option>
                            <#list ($sort_list as $item)
                                <#if ($item['goods_num']>0)
                                    <option value="${item['sort_id']!}"
                                            <#if ($sort_id == $item['sort_id'])selected="selected"</#if>>${item['sort_name']!}(${item['goods_num']!})</option>
                                </#if>
                            </#list>
                        </select>
                    </li>
                    <li>
                        商品标签：
                        <select name="goods_label_id" id="" class="list-center-sel">
                            <option value="0">请选择商品标签</option>
                            <#list ($goodsLabelList as $item)
                                <option value="${item->id!}" <#if ($goods_label_id == $item->id) selected </#if>>${item->name!}</option>
                            </#list>
                        </select>
                    </li>
                    
                    <#if ($shop_flag ==2)
                    <li>
                        商品品牌：
                        <select name="brand_id" class="list-center-sel">
                            <option value="0">请选择品牌</option>
                            <#list ($brand_list as $item)
                                <option value="${item['id']!}"
                                        <#if ($brand_id == $item['id'])selected="selected"</#if>>${item['brand_name']!}</option>
                            </#list>
                        </select>
                    </li>
                    </#if>
                    <#if (in_array($shop_flag,[1,2]))
                    <li>
                        商品来源：
                        <select name="source" id="" class="list-center-sel">
                            <option value="0">请选择商品来源</option>
                            <option value="1" <#if ($post['source'] == 1) selected </#if>>自营商品</option>
                            <option value="2" <#if ($post['source'] == 2) selected </#if>>非自营商品</option>
                        </select>
                    </li>
                    </#if>
                    <li>
                        活动类型：
                        <select name="activity_type" id="" class="list-center-sel">
                            <option value="0">请选择活动类型</option>
                            <option value="1" <#if ($post['activity_type'] == 1) selected </#if>>拼团</option>
                            <option value="3" <#if ($post['activity_type'] == 3) selected </#if>>砍价</option>
                            <option value="5" <#if ($post['activity_type'] == 5) selected </#if>>秒杀</option>
                            <option value="6" <#if ($post['activity_type'] == 6) selected </#if>>限时降价</option>
                            <option value="98" <#if ($post['activity_type'] == 98) selected </#if>>加价购</option>
                            <option value="99" <#if ($post['activity_type'] == 99) selected </#if>>打包一口价</option>
                            <option value="10" <#if ($post['activity_type'] == 10) selected </#if>>定金膨胀</option>
                        </select>
                    </li>
                    <#if ($shop_flag==2)
                    <li>
                        <span class="sel_time">
                            <span  class="sel_font">上架时间：</span>
                        <input class="list-center-sel" style="float: none;width:80px" type="text" name="startDate" placeholder="请选择时间" id="startDate" value="${startDate!}"
                               onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/> —
                        <input class="list-center-sel" style="float: none;margin-left: 0;width:80px;" type="text" name="endDate" placeholder="请选择时间" value="${endDate!}" id="endDate"
                               onclick="picker();"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/></span>
                    </li>
                    </#if>
                    <li>
                            <span class="goods_fr">
                                价格：<input type="text" name="min_goods_price" value="${post['min_goods_price']!}"> 至 <input type="text" name="max_goods_price" value="${post['max_goods_price']!}">
                            </span>
                    </li>
                    <li>
                        <button class="btn_searchinfo">查询</button>
                        <button type="button" class="btn-excel" style="margin-left: 50px;">导出商品</button>
                    </li>
                    
                    <!--  <button type="button" class="btn-erp-excel" style="margin-left: 12px;">导出erp表格</button> -->

                </ul>
                <div>
                    <a href="/admin/goods/manage/add" link="/admin/goods/manage/add"  class="create-good goods_edition" target="_blank" data-title="商品数量" data-name="goods_num" edit="0">添加商品</a>
                    <div class="system_info">
                        <p class="system_info_prompt">
                            <#if ($version['self']['num']<0)
                                当前版本为${version['self']['version_name']!}，<span>不限制</span>商品个数
                            <#else>
                                当前版本为${version['self']['version_name']!}，还可创建 <span>${version['self']['num']-$version['self']['use'] >0 ? $version['self']['num']-$version['self']['use'] : 0!}</span>个商品
                            </#if>
                            <img src="http://${image_domain!}/image/admin/system_icon.png" />
                        </p>
                        <div class="system_info_content">
                            <div class="system_info_content_top">
                                <#if ($version['all'])
                                    <#list ($version['all'] as $k=>$ver)
                                        <#if ($ver['num']<0)
                                            <#if ($k!=0)，</#if>${ver['version_name']!}<span class="system_v1">不限制</span>商品个数
                                        <#else>
                                            <#if ($k!=0)，</#if>${ver['version_name']!}最多创建<span class="system_v1">${ver['num']!}</span>个商品
                                        </#if>
                                    </#list>
                                </#if>
                                {{--基础版最多创建<span class="system_v1">5</span>个商品，高级版最多创建<span class="system_v2">20</span>个商品，旗舰版不限制--!}
                            </div>
                            <div class="system_info_content_bottom">
                                <a href="/admin/version/notice?mod_name=商品数量" target="_blank">了解更多</a>
                            </div>
                        </div>
                    </div>
                </div>
                <span class="choose_field" goods_show_field="${goods_show_field!}">自定义字段</span>

            </div>

            <input type="hidden" name="sort_field" id="sort_field" value="${sort_field!}">
            <input type="hidden" name="sort_way" id="sort_way" value="${sort_way!}">
            <div class="list-bottom" style="padding:10px">
                <table align="center" class="list-bottom-show" style="width:100%;">
                    <thead style="border: 1px solid #eee;">
                    <tr id="list-bottom-top">
                        <th width="4%"></th>
                        <#list ($show_field as $k=>$v)
                            <#if ($k=='goods_name'||$k=='parent_sort_name'  )
                                <th width="10%" class="is_hide">${v!}</th>
                            <#elseif>($k == 'sort_name')
                                <th width="7%" class="is_hide">${v!}</th>
                            <#else>
                                <th width="6%" class="is_hide">${v!}</th>
                            </#if>

                        </#list>
                        <th width="4%">操作</th>
                    </tr>
                    </thead>
                    <tbody id="" class="list">

                    <#list ($goods_items as $item)
                        <tr>
                            <td align="center"  style="border-right: none;position: relative;" >
                                <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" />
                                <input type="checkbox" name="checkbox" goods_id="${item->goods_id!}">
                            </td>
                            <#list ($show_field as $k=>$v)
                                <#if ($k == 'goods_img')
                                    <td align="center">{!! $item->$k !!}</td>
                                <#else>
                                    <#if ($k == 'goods_name' || $k == 'goods_ad')
                                        <td align="center">
                                            <span>
                                                <textarea type="text" name="${k!}" goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" value="" class="change-input" style="width:80%;height: 70%;" disabled >${item->$k!}</textarea>
                                            <img src="http://${image_domain!}/image/admin/good_edit.png" alt="" class="goods-number-img" />
                                            </span>
                                            <div style="font-size:8px;margin-top: 7px;display: none;float:right;"  class="up_btn">
                                                <span class="sure_up" flag="1">确定</span>
                                            </div>
                                        </td>
                                    <#elseif>($k == 'cat_name')
                                        <td align="center">
                                            <span>
                                                <input type="text" name="${k!}" value="${item->$k!}" style='width:65%;height:30px;' class="change-input" disabled />
                                            <img src="http://${image_domain!}/image/admin/good_edit.png" alt=""  goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" class='update_cat' />
                                            </span>
                                            <div style="font-size:8px;margin-top: 7px;display: none;float:right;"  class="up_btn">
                                                <span class="sure_up" flag="1">确定</span>
                                            </div>
                                        </td>
                                    <#elseif>($k == 'sort_name')
                                        <td align="center">
                                            <span style="margin-left: -40px;">
                                                <#if ($item->sort_name)
                                                    <input type="text" name="${k!}" goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" value="${item->$k!}" style='width:65%;height:30px;text-align: right;' class="change-input" disabled />
                                                </#if>
                                            <img src="http://${image_domain!}/image/admin/good_edit.png" alt="" goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" style='margin-right: 10px;float: right;line-height: 85px;margin-top: 3%;'class="update_sort" />
                                            </span>
                                            <div style="font-size:8px;margin-top: 7px;display: none;float:right;" class="up_btn">
                                                <span class="sure_up" flag="1">确定</span>
                                            </div>
                                        </td>
                                    <#elseif>($k == 'unit')
                                        <td align="center">
                                            <span style="margin-left: -40px;">
                                                <input type="text" name="${k!}" goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" value="${item->$k!}" style='width:65%;height:30px;text-align: right;' class="change-input" disabled />
                                                <img src="http://${image_domain!}/image/admin/good_edit.png" alt="" goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" style='margin-right: 10px;float: right;line-height: 85px;margin-top: 3%;'class="update_unit" />
                                            </span>
                                            <div style="font-size:8px;margin-top: 7px;display: none;float:right;" class="up_btn">
                                                <span class="sure_up" flag="1">确定</span>
                                            </div>
                                        </td>
                                    <#elseif>($k == 'prd_desc' || $k == 'prd_sn')
                                        <td align="center">
                                            <span style="margin-left: -40px;">
                                                <input type="text" name="${k!}" goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" value="${item->$k!}" style='width:65%;height:30px;text-align: right;' class="change-input" disabled />
                                            </span>
                                        </td>
                                        <#else>
                                        <td align="center">
                                            <span>
                                                <input type="text" name="${k!}" goods_id="${item->goods_id!}" prd_id="${item->prd_id!}" value="${item->$k!}" style='width:65%;height:30px;' class="change-input" disabled />
                                            <img src="http://${image_domain!}/image/admin/good_edit.png" alt="" id='' class="goods-number-img" />
                                            </span>
                                            <div style="font-size:8px;margin-top: 7px;display: none;float:right;" class="up_btn">
                                                <span class="sure_up" flag="1">确定</span>
                                            </div>
                                        </td>
                                        </#if>
                                    </#if>
                            </#list>
                            <td align="center"  class="share_td">
                                <div>
                                    <a href="/admin/goods/manage/edit?goods_id=${item->goods_id!}&top_index=${_GET['top_index'] ?? 2!}" link="/admin/goods/manage/edit?goods_id=${item->goods_id!}&top_index=${_GET['top_index'] ?? 0!}&sub_index=${sub_index!}" target="_blank" class="goods_edition" data-title="商品数量" data-name="goods_num" edit="1">{{ trans("admin/shop.list-bottom-operate.li_edit")!}</a>
                                    <span class="goods_new">
                                    <#if ($nav == 0)<a href="/admin/goods/manage/list?act=off_sale&goods_id=${item->goods_id!}&nav=${nav!}">{{ trans("admin/shop.list-bottom-operate.li_done")!}</a>
                                        <#elseif>($nav == 2)<a href="/admin/goods/manage/list?act=on_sale&goods_id=${item->goods_id!}&nav=${nav!}">上架</a></#if>
                                    </span>
                                </div>
                                <div>
                                    <div style="display: inline-block;position: relative">
                                        <a href="##" class="hover_share"  type = '2' identity_id="${item->goods_id!}">分享</a>
                                    </div>
                                    <span class="goods_new">
                                        <a href="#" class="del" goods_id="${item->goods_id!}">删除</a>
                                    </span>
                                </div>
                                <#if ($item->comment_num > 0)
                                    <div class="goods_ck">
                                        <a href="/admin/goods/comment/list?goods_id=${item->goods_id!}" target="_blank">{{ trans("admin/shop.list-bottom-operate.look_evaluate")!}（${item->comment_num!}）</a>
                                    </div>
                                </#if>
                            </td>
                    </#list>
                    </tbody>
                    <#if ($data_list->total_num == 0)
                        <table style="width: 100%;border: 1px solid #eee;position: relative;margin-top: 16px;">
                            <tr>
                                <td style="width: 30px;height: 33px; margin: 18px auto auto auto;display: flex;justify-content: center;align-items: center;" >
                                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</td>
                            </tr>
                        </table>
                    <#else>
                        <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table" border="0">
                            <tr >
                                <td style="padding-left: 15px;">
                                <span style="position:relative;">&nbsp;&nbsp;
                                    <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" style="top: -1px;" />
                                    <input type="checkbox" id="select-all" />&nbsp;&nbsp;
                                    <label for="select-all">全选</label>
                                </span>
                                    <#if ($nav == 0)
                                        <input name="off_sale" type="button" id="icon_deletes" value="下架" class="down tb-btm-left">
                                    <#elseif>($nav == 2)
                                        <input name="on_sale" type="button" id="icon_ups" value="上架" class="down tb-btm-left">
                                    </#if>
                                    <input name="del" type="button"  value="删除" class="tb-del tb-btm-left" id="del_goods">
                                    <input name="deliver" type="button" id="set_deliver" value="设置运费模板"
                                           class="pay-conf tb-btm-left layui-btn layui-btn-normal"
                                           data-id="config-temp" data-method="offset" data-title="批量设置运费模板" style="margin-top: -4px;"/>
                                </td>
                                <td align="right">
                                    <table width="100%" border="0" class="tb_paging">
                                        <tr>
                                            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->per_page,'currentPage'=>$data_list->current_page,'count'=>$data_list->count,'total'=>$data_list->total_num,])!}
                                                <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                                <a href="#"
                                                   onClick="return gopage(${data_list->current_page -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                                <a href="#"
                                                   onClick="return gopage(${data_list->current_page + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                                <a href="#"
                                                   onClick="return gopage(${data_list->last_page!});">{{ trans("admin/common.page.last_page")!}</a>
                                                <input id="page" name="page" type="text" value="${data_list->current_page!}"
                                                       size="5"
                                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')"  autocomplete="off">{{ trans("admin/common.page.page")!}
                                                <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                                            </td>
                                        </tr>
                                    </table>

                                </td>
                            </tr>
                        </table>
                    </#if>
                </table>
            </div>
        </form>
    </div>
</div>
{{--设置运费模版--!}
<div id="set-config-temp">
    <div class="config-temp-cont">
        <div>
            <select name="deliver_template_id" class="fee-def">
                <option value="0">店铺默认运费模板</option>
                <#if  (!empty($template_list->template_list))
                    <#list ($template_list->template_list as $item)
                        <option value="${item->deliver_template_id!}"
                                <#if  ($goods->deliver_template_id == $item->deliver_template_id)selected="selected"</#if> >
                            <#if  ($item->flag == 0)普通--<#else>重量--</#if>${item->template_name!}
                        </option>
                    </#list>
                </#if>
            </select>
            <a href="/admin/goods/deliver/template/add" class="temp add-temp" target="_blank">添加新模板</a>
            <a href="/admin/goods/deliver/template/list" class="temp manage-tmp">管理模板</a>
        </div>
        {{--<p>--!}
        {{--<label for="">店铺统一运费：0元</label>--!}
        {{--<a href="" class="view-detail">查看详情</a>--!}
        {{--</p>--!}
        <div class="panel panel-heading deliver-template-info template-0 ">
            <div class="template-info-row clearfix">
                ${template_list->shop_template->content_desc->desc!}
                <div class="pull-right">
                    <a href="/admin/goods/deliver/template/list"
                       target="_blank">查看详情</a>
                </div>
            </div>
        </div>
        <#if  (empty($template_list->template_list))
            <#list  ($template_list->template_list as $item)
                <div class="panel panel-heading deliver-template-info template-${item->deliver_template_id!} <#if  ($goods_deliver_fee_template_id != $item->deliver_template_id) hide </#if> ">
                    <#list  ($item->content_desc as $key =>$item2)
                        <#if  ($key==0)
                            <div class="template-info-row">
                                ${item2!}
                                <div class="pull-right">
                                    <a href=/admin/goods/deliver/template/edit?deliver_template_id=${item->deliver_template_id!}"
                                       target="_blank">查看详情</a>
                                </div>
                            </div>
                        <#else>
                            <#if ($key==1)
                                <hr>
                                <div class="template-info-row">
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
                                <div class="template-info-row">
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
        </#if>
    </div>

</div>
{{--设置平台分类--!}
<div id="set-cat" style="margin-left: 50px;">
    <table>
        <tr>
            <td style="width: 105px;padding: 13px 0;color: #666;position: relative;vertical-align: top !important;"><em></em>平台分类：</td>
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
            </td>
        </tr>
    </table>
</div>
{{--设置商家分类--!}
<div id="set_sort">
    <div style="margin-left: 10%;">
        <input type="hidden" name="sort_id">
        商家分类：<select name="sort_id1" id="set_id">
            <option value="0">请选择商家分类</option>
            <#if ($sort_list)
                <#list ((array)$sort_list as $k=>$item)
                    <option value="${item['sort_id']!}" <#if ($goods->sort_id == $item['sort_id']) selected </#if>>${item['sort_name']!}</option>
                </#list>
            </#if>
        </select>
    </div>
</div>
{{--设置单位--!}
<div id="set_unit">
    <div style="margin-left: 10%;">
        <#if ($goods->unit && !in_array($goods->unit,(array)$unit))
           选择单位： <select name="unit" onclick="unitPop(this)" id="set_un">
                <#list ((array)$unit as $item)
                    <option value="${item!}" <#if ('自定义' == $item)selected="selected"</#if>>${item!}</option>
                </#list>
            </select>
            <span id="unit_pop" > &nbsp;<input type="text" name="unit_temp"  id="unit_input" value="${goods->unit!}" style=" width: 100px;" />&nbsp;<span class="grade_card_prompt">长度限制为3个中文字符</span></span>
        <#else>
            选择单位：<select name="unit" onclick="unitPop(this)" id="set_un">
                <#list ((array)$unit as $item)
                    <option value="${item!}" <#if ($goods->unit == $item)selected="selected"</#if>>${item!}</option>
                </#list>
            </select>
            <span id="unit_pop" style="display: none;"> &nbsp;<input type="text" name="unit_temp"  id="unit_input" value="" style=" width: 100px;" />&nbsp;<span class="grade_card_prompt">长度限制为3个中文字符</span></span>
        </#if>
    </div>
</div>
<div id="set-label" >
    <div class="text_warn">
        <img src="/image/admin/notice_img.png">
        <span>可以在这里编辑商品标签信息,添加或删除标签</span>
    </div>
    <input type="hidden" value="" name="goods_have_label_str">
    <input type="hidden" value="" name="goods_have_label_name">

    <div class="clearfix" style="margin-top: 10px">
        <div class="fl">商品标签:</div>
        <div class="set_label_content">
            {{--<button class="btn_add_label" label_type="0">添加</button>--!}
            <select name="label_id" id="label_id" class="label_option">
                <option value="0"  selected="selected" >请选择商品标签</option>
            </select>
        </div>
    </div>
    <div class="clearfix label-choose" style="display: none;margin-top: 10px" >
        <div class="fl" >已选:</div>
        <div class="label_info">
            <div class="label-info-row">
            </div>
        </div>
    </div>
</div>
<#include ("admin.share_common")
<div id="set-pre-edit" style="display: none;">
    <div id="role_template" style="overflow-y: scroll;padding: 0px 20px;height:100%;">
        <input name="namestr" value="" type="hidden">
        <div data-toggle="switch_box" class="active">
            <ul class="role_ul">
                <li style="display: inline-block;width: 122px;">
                    <input type="checkbox"
                           name="sub_cbx[]" checked="true" value="cat_name"
                           onclick="return false;" class="necessary_chose two_click basic_info"/>
                    <span style="color: red;">平台分类</span>
                </li>
                <li style="display: inline-block; width: 122px">
                    <input type="checkbox" class='two_click basic_info'
                           name="sub_cbx[]" checked="true" value="sort_name"
                    />
                    <span>商家分类</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click basic_info'
                           name="sub_cbx[]" checked="true" value="goods_sn"
                    />
                    <span>货品编号</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click basic_info'
                           name="sub_cbx[]" checked="true" value="goods_name"
                    />
                    <span>货品名称</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="prd_desc"
                    />
                    <span>规格名称</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="goods_ad"
                    />
                    <span>商品广告词</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="prd_sn"
                    />
                    <span>商家编码</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="goods_number"
                    />
                    <span>库存</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="market_price"
                    />
                    <span>市场价</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="shop_price"
                    />
                    <span>零售价</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="is_on_sale"
                    />
                    <span>上架状态</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="limit_buy_num"
                    />
                    <span>最小限购数</span>
                </li> <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="limit_max_num"
                    />
                    <span>最大限购数</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="goods_weight"
                    />
                    <span>重量</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="unit"
                    />
                    <span>单位量词</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="goods_img"
                    />
                    <span>商品主图</span>
                </li>
                <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="goods_detail"
                    />
                    <span>商品详情</span>
                </li> <li style="display: inline-block;width: 122px ">
                    <input type="checkbox" class='two_click'
                           name="sub_cbx[]" checked="true" value="cost_price"
                    />
                    <span>成本价</span>
                </li>
                {{--<li style="display: inline-block;width: 122px ">--!}
                    {{--<input type="checkbox" class='two_click'--!}
                           {{--name="sub_cbx[]" checked="true" value="fee"--!}
                    {{--/>--!}
                    {{--<span>快递费</span>--!}
                {{--</li>--!}
                {{--<li style="display: inline-block;width: 122px ">--!}
                    {{--<input type="checkbox" class='two_click'--!}
                           {{--name="sub_cbx[]" checked="true" value="brand_name"--!}
                    {{--/>--!}
                    {{--<span>品牌</span>--!}
                {{--</li>--!}
                {{--<li style="display: inline-block;width: 122px ">--!}
                    {{--<input type="checkbox" class='two_click'--!}
                           {{--name="sub_cbx[]" checked="true" value="sale_num"--!}
                    {{--/>--!}
                    {{--<span>天销量</span>--!}
                {{--</li>--!}
                {{--<li style="display: inline-block;width: 122px ">--!}
                    {{--<input type="checkbox" class='two_click'--!}
                           {{--name="sub_cbx[]" checked="true" value="wuliu"--!}
                    {{--/>--!}
                    {{--<span>物流</span>--!}
                {{--</li>--!}
            </ul>
        </div>
    </div>
</div>
<script>
    $("#export").val('');
    function gopage(page) {
        var last_page = '${data_list -> last_page!}';
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        // var href = ;
        $("#page").val(page);
        // $('#form1').attr('action', href);
        $("#form1").submit();
    }
    var page_home = '${data_list->current_page!}'; //当前页码数
    var page_all = '${data_list->count!}';          //总页码数


    window.sort_f = function (field) {
        if ($("#sort_field").val() == field) {
            if ($("#sort_way").val() == 'desc') {
                $("#sort_way").val('asc');
            } else {
                $("#sort_way").val('desc');
            }
        } else {
            $("#sort_field").val(field);
            $("#sort_way").val('desc');
        }
        $("#form1").submit();
    };
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    $('[name="brand_id"]').change(function(){
        $("#form1").submit();
    });
    $('[name="deliver_template_id"]').change(function () {
        // alert( $(this).val())
        $(".deliver-template-info").addClass("hide");
        $(".deliver-template-info.template-" + $(this).val()).removeClass("hide");
        $(".deliver-template-info.template-article").removeClass("hide");
    });
    $('#icon_deletes').click(function(){

        var goods_id = [];
        $('input[name="checkbox"]:checked').each(function () {
            goods_id.push($(this).attr('goods_id'));
        });
        if (goods_id.length == 0) {
            //util.alert('未选择任何商品');
            util.mobile_alert('未选择任何商品');
            return false;
        }
        else {
            var _this = $(this);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要下架吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                },function(index){
                    $('input[name="all"]').val(_this.attr('name'));
                    $('input[name="goods_id"]').val(goods_id);
                    $("#form1").submit();
                    layer.close(index);
                });
            });

        }
    });
    $('#set_deliver').click(function(){
        var goods_id = [];
        $('input[name="checkbox"]:checked').each(function () {
            goods_id.push($(this).attr('goods_id'));
        });
        if (goods_id.length == 0) {
            util.mobile_alert('未选择任何商品');
            return false;
        } else {
            deliver_open(goods_id);
        }
    });
    var _val;
    $(".change-input").focus(function () {
        _val = $(this).val();
    });
    $(".change-input").blur(function (e) {
        var b_val = $(this).val();
        if(b_val != _val){
            $(this).val(_val);
        }
        if(Number(b_val) == Number(_val) && b_val != ''){
            //console.log(111);
            $('.ipt-change').next().show();
            $('.change-input').removeClass('ipt-change');
            $(".change-input").attr("disabled",true);
        }
    });

    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
    $("#search").click(function () {
        $("#form1").submit();
    });
    $(".del").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="del"]').val(_this.attr("goods_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $('#del_goods').click(function(){
        var goods_id = [];
        $('input[name="checkbox"]:checked').each(function () {
            goods_id.push($(this).attr('goods_id'));
        });
        if (goods_id.length == 0) {
            //util.alert('未选择任何商品');
            util.mobile_alert('未选择任何商品');
            return false;
        } else {
            var _this = $(this);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                },function(index){
                    $('input[name="all"]').val(_this.attr('name'));
                    $('input[name="goods_id"]').val(goods_id);
                    $("#form1").submit();
                    layer.close(index);
                });
            });

        }
    });

    $(".btn-excel").click(function () {
        //  $('#page').val(1);
      getAuthorityDetail(1,"","goods_export","","goods_export");
      // $("#export").val("export_csv");
      // $("#form1").submit();
      //   $("#export").val('');
    })
    $(".btn-erp-excel").click(function () {
        //  $('#page').val(1);
        $("#erp_export").val("erp_export_csv");
        $("#form1").submit();
        $("#erp_export").val('');
    })

    $('.hide_title').each(function () {
        $(this).click(function () {
            $(this).parents('.is_hide').css('display','none');
            var _value = $(this).parents('.is_hide').attr('_value');
            $('.'+_value).css('display','none');
        })
    })
    $(".choose_field").click(function () {
        $('[type="checkbox"]').prop("checked", false);
        $('.necessary_chose').prop("checked",true);
        var goods_show_field = $(this).attr('goods_show_field');
        if (goods_show_field == "") goods_show_field = "{}";
        var goods_show_field = goods_show_field.split(",");
        // console.log(orderExport_list);
        for (var j in goods_show_field) {
            $('[name="sub_cbx[]"][value="' + goods_show_field[j] + '"]').prop('checked', true);
        }
        layer.open({
            type: 1
            , title: ['自定义显示字段']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['570px', '330px']
            , content: $('#set-pre-edit') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确认', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000000'] //显示遮罩透明度和颜色
            , yes: function (index, layero) { //保存按钮的回调
                var nameArr = [];
                $('[name="act"]').val('');
                $('.two_click:checked').each(function(){
                    nameArr.push($(this).next().text());
                })
                $('[name="namestr"]').val(nameArr.join(','));
                var param = $("#role_template :input").serializeArray();
                console.log(param);
                util.ajax_json("/admin/goods/detail/field", function (d) {
                    if (d && d.error == 0) {
                        layer.close(index);
                        location.href = "/admin/goods/detail/list";
                    }
                }, param);
                return true;
                //return false 开启该代码可禁止点击该按钮关闭
            }, btn2: function (index, layero) {
                //按钮取消的回调
                //return false 开启该代码可禁止点击该按钮关闭
            }
        });
    })
    var _val;
    var _new_val;
    $(".change-input").focus(function () {
        _val = $(this).val();
    });

    $('.sure_up').each(function () {
        $(this).mousedown(function () {
            $(".change-input").change(function(e) {
                var data = {};
                var url = '/admin/goods/manage/updateGoods'
                var el = $(this);
                data['field'] = $(this).attr('name');
                data.goods_id = $(this).attr("goods_id");
                data.prd_id = $(this).attr("prd_id");
                data[$(this).attr('name')] = $(this).val();
                console.log(data);
                _new_val = $(this).val();
                util.ajax_json(url,function(d){
                    if(d&&d.error==0) {
                        util.mobile_alert(d.content.content);
                        el.val(_new_val);
                    }
                },data);
            })
        })
    })

    $(".change-input").blur(function () {
        $('.up_btn').hide();
        $('.goods-number-img').show();

        var b_val = $(this).val();
        if(Number(b_val) == Number(_val) && b_val != ''){
            //console.log(111);
            $('.ipt-change').next().show();
//            $('.change-input').removeClass('ipt-change');
//            $(".change-input").attr("disabled",true);
        }
    });


    $('.goods-number-img').each(function () {
        $(this).click(function () {
            $(this).parents().children('.up_btn').show();
        })
    })
    var _new_cat_name;
    $('.update_cat').click(function(){
        var goods_id = $(this).attr('goods_id');
        var prd_id = $(this).attr('prd_id');
        var this1 = $(this).prev();
        update_cat(goods_id,prd_id,this1);
    });

    function update_cat(goods_id,prd_id,this1){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            $('#level1').on("change", function() {
                _new_cat_name = ($("#level1 option:selected").text());
            });
                //触发事件
            layer.open({
                type: 1
                ,title: ['设置平台分类','text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: '650px'
                ,id: 'layerDemoD' //防止重复弹出
                ,content: $('#set-cat') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['保存','取消']
                ,btnAlign: 'r' //按钮居右
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    var data={};
                    data['field'] = 'cat_name';
                    data.goods_id = goods_id;
                    data.prd_id = prd_id;
                    data.level0 = $('[name="level0"]').val();
                    data.level1 = $('[name="level1"]').val();
                    util.ajax_json('/admin/goods/manage/updateGoods',function(d){
                        console.log(d);
                        if(d&&d.error==0){
                            util.mobile_alert(d.content.content);
                            this1.val(d.content.message);
                        }
                        else{
                            util.mobile_alert(d.message);
                        }
                    },data);
                    layer.close(index);
                    //return false 开启该代码可禁止点击该按钮关闭
                },btn2: function(index, layero){
                    //按钮取消的回调

                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        });
    }
//    设置商家分类
    var _new_sort_name;
    $('.update_sort').click(function() {
        var goods_id = $(this).attr('goods_id');
        var prd_id = $(this).attr('prd_id');
        var this1 = $(this).prev();
        update_sort(goods_id,prd_id,this1);
    });
    function update_sort(goods_id,prd_id,this1){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            $('#sort_id').on("change", function() {
                _new_sort_name = ($("#sort_id option:selected").text());
            });
            //触发事件
            layer.open({
                type: 1
                ,title: ['设置商家分类','text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: '650px'
                ,id: 'layerDemoD' //防止重复弹出
                ,content: $('#set_sort') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['保存','取消']
                ,btnAlign: 'r' //按钮居右
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    var data={};
                    data['field'] = 'sort_name';
                    data.goods_id = goods_id;
                    data.prd_id = prd_id;
                    data.sort_id = $("#set_id option:selected").val();
                    util.ajax_json('/admin/goods/manage/updateGoods',function(d){
                        if(d&&d.error==0){
                            console.log(d);
                            util.mobile_alert(d.content.content);
                            this1.val(d.content.message);
                        }
                        else{
                            util.mobile_alert(d.message);
                        }
                    },data);
                    layer.close(index);
                },btn2: function(index, layero){
                }
            });
        });
    }
//设置单位
    var _new_unit;
    $('.update_unit').click(function() {
        var goods_id = $(this).attr('goods_id');
        var prd_id = $(this).attr('prd_id');
        var this1 = $(this).prev();
        update_unit(goods_id,prd_id,this1);
    });
    function update_unit(goods_id,prd_id,this1){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            $('#set_un').on("change", function() {
                _new_unit = ($("#set_un option:selected").text());
            });

            //触发事件
            layer.open({
                type: 1
                ,title: ['设置单位','text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: '650px'
                ,id: 'layerDemoD' //防止重复弹出
                ,content: $('#set_unit') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['保存','取消']
                ,btnAlign: 'r' //按钮居右
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    var data={};
                    data['field'] = 'unit';
                    data.goods_id = goods_id;
                    data.prd_id = prd_id;
                    data.unit = $("#set_un option:selected").val();
                    util.ajax_json('/admin/goods/manage/updateGoods',function(d){
                        if(d&&d.error==0){
                            console.log(d);
                            util.mobile_alert(d.content.content);
                            this1.val(_new_unit);
                        }
                        else{
                            util.mobile_alert(d.message);
                        }
                    },data);
                    layer.close(index);
                },btn2: function(index, layero){
                }
            });
        });
    }
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/goods_list.js?v=1.1.3" type="text/javascript"></script>
<script language="JavaScript" src="{{asset('js/admin/goods_info.js')!}?v=6.5.1"></script>
<#include "/admin/footer.ftl">
