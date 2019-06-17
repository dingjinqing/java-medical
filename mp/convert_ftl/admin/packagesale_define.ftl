<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/packagesale_manage.css?v=1.1.1" type="text/css"/>
<style type="text/css">
    .goods_area::-webkit-scrollbar{
        width:4px;
        height:4px;
    }
    .goods_area::-webkit-scrollbar-track{
        background: #fff;
        border-radius:2px;
    }
    .goods_area::-webkit-scrollbar-thumb{
        background: #dddddd;
        border-radius:2px;
    }
    .goods_area::-webkit-scrollbar-thumb:hover{
        background: #747474;
    }
    .goods_area::-webkit-scrollbar-corner{
        background: #fff;
    }
    .goods_modal th{
        border:1px solid #eee;
    }
    .goods_modal{
        margin-top: 0;
        display: none;
        border: none;
        width: 100%;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">打包一口价</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/package/list?nav=0">全部打包一口价活动</a>
                </li>
                <li >
                    <a href="/admin/market/package/list?nav=1">进行中</a>
                </li>
                <li >
                    <a href="/admin/market/package/list?nav=2">未开始</a>
                </li>
                <li >
                    <a href="/admin/market/package/list?nav=3">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/package/list?nav=4">已停用</a>
                </li>
                <li class="active">
                    <a>添加打包一口价活动</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="package_info">
                <input type="text" name="id" value="${package_sale->id!}" hidden>
                <div class="clearfix">
                    <div class="config_title"><em>*</em> 活动名称:</div>
                    <div class="config_content">
                        <input type="text" placeholder="请输入活动名称" name="package_name"  value="${package_sale->package_name!}" class="act_names">
                        <span class="config_tips">只作为商家记录使用，用户不会看到这个名称</span>
                    </div>
                </div>
                <div class="clearfix">
                    <div class="config_title"><em>*</em> 活动时间:</div>
                    <div class="config_content">
                        <input type="text"  name="start_time"  placeholder='选择日期'
                               value="${package_sale->start_time!}"  onclick="picker();" autocomplete="off"  class="start_date"/>
                        至
                        <input type="text"  name="end_time" style="margin-left: 10px" placeholder='选择日期'
                               value="${package_sale->end_time!}" onclick="picker();"  autocomplete="off"  class="end_date"/>
                        <span class="config_tips">同一时间可以有多个一口价活动</span>
                    </div>
                </div>
                <div  class="clearfix">
                    <div class="config_title" style="line-height: 20px">活动规则:</div>
                    <div class="config_content">
                        <span class="config_tips have_width">
                            最多可配置3个商品组，买家在每组商品中分别选择指定件数，即可以“结算总价格”结算<br>
                            例如：商家可设置3个商品组，买家需在每个商品组中选择1件，3组共3件以总价格100元结算
                        </span>
                        <a href="javascript:;" class="show_eg">查看示例
                            <div class="hover_show">
                                <img src="http://${image_domain!}/image/admin/new_preview_image/packagesale.jpg" alt="">
                            </div>
                        </a>
                    </div>
                </div>
                <div class="clearfix">
                    <div class="config_title"><em>*</em> 结算总价格:</div>
                    <div class="config_content">
                        <input type="text" name="total_money" value="${package_sale->total_money!}" class="sum_money" onkeyup="value=value.replace(/[^\d.]/g,'')"> 元
                    </div>
                </div>
                <div class="clearfix">
                    <div class="config_title" style="line-height: 27px"><em>*</em> 商品组:</div>
                    <div class="config_content">
                        <div class="clearfix each_gr">
                            <label for="gr1">
                                <input type="checkbox" id="gr1" name="goods_group[]" value="1" checked>商品组1
                            </label>
                            <div class="gr_set">
                                <div class="name_set">
                                    名称 <input type="text" name="group_name_1" maxlength="4"  class="group_name" value="${package_sale->group_name_1!}">
                                    <span class="config_tips">商品组名称，最多可填4个字</span>
                                </div>
                                <div class="num_set">
                                    需选择 <input type="text" name="goods_number_1" class="goods_number" onkeyup="value=value.replace(/[^\d]/g,'')"  value="${package_sale->goods_number_1!}"> 件
                                    <span class="config_tips">该商品组需要选购的商品数量，请填写正整数</span>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix each_gr">
                            <label for="gr2">
                                <input type="checkbox" id="gr2" name="goods_group[]" value="2" <#if ($package_sale->goods_group_2 == 1) checked </#if>>商品组2
                            </label>
                            <div class="gr_set">
                                <div class="name_set">
                                    名称 <input type="text" name="group_name_2" class="group_name" maxlength="4" value="${package_sale->group_name_2!}">
                                    <span class="config_tips">商品组名称，最多可填4个字</span>
                                </div>
                                <div class="num_set">
                                    需选择 <input type="text" name="goods_number_2" class="goods_number"  onkeyup="value=value.replace(/[^\d]/g,'')" value="${package_sale->goods_number_2!}"> 件
                                    <span class="config_tips">该商品组需要选购的商品数量，请填写正整数</span>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix each_gr">
                            <label for="gr3">
                                <input type="checkbox" id="gr3" name="goods_group[]" value="3" <#if ($package_sale->goods_group_3 == 1) checked </#if>>商品组3
                            </label>
                            <div class="gr_set">
                                <div class="name_set">
                                    名称 <input type="text" name="group_name_3" class="group_name" maxlength="4"  value="${package_sale->group_name_3!}">
                                    <span class="config_tips">商品组名称，最多可填4个字</span>
                                </div>
                                <div class="num_set">
                                    需选择 <input type="text" name="goods_number_3" class="goods_number" onkeyup="value=value.replace(/[^\d]/g,'')"  value="${package_sale->goods_number_3!}"> 件
                                    <span class="config_tips">该商品组需要选购的商品数量，请填写正整数</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clearfix" style="margin-bottom: 10px;">
                    <div class="config_title" style="line-height: 20px;"><em>*</em> 添加商品:</div>
                    <div class="config_content">
                        <span class="config_tips">请给每个商品组分别添加商品</span>
                    </div>
                </div>
                <div class="clearfix">
                    <div class="config_title"></div>
                    <div class="config_content">
                        <div class="goods_areas" >
                            <div class="ga_title clearfix">
                                <a href="##" class="active" ga_index="0">商品组1</a>
                                <a href="##" ga_index="1" <#if ($package_sale->goods_group_2 != 1)  style="display: none" </#if>>商品组2</a>
                                <a href="##" ga_index="2" <#if ($package_sale->goods_group_3 != 1)  style="display: none" </#if>>商品组3</a>
                            </div>
                            <div class="sth_table" style="padding-right: 20px">
                                <input name="goods_ids_1" class="goods_ids" value="${package_sale->goods_ids_1!}" hidden>
                                <input name="cat_ids_1" class="cat_ids" value="${package_sale->cat_ids_1!}" hidden>
                                <input name="sort_ids_1" class="sort_ids" value="${package_sale->sort_ids_1!}" hidden>
                                <div class="btn_add_goods" >
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商品
                                </div>
                                <div class="goods_area" style="width:93%;margin:0;padding-right:5px;max-height: 500px;overflow-y: scroll;border: none">
                                <table class="goods_modal" <#if ($package_sale->goods_ids_1) style="margin-top:0;display: block;border:none;width: 100%" </#if>>
                                    <thead>
                                        <tr>
                                            <th>商品名称</th>
                                            <th>价格</th>
                                            <th>库存</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody class="tbody">
                                    <#if ($package_sale->goods_ids_1)
                                        <#list ($package_sale->goods_array1 as $item)
                                            <tr>
                                                <td width="50%">
                                                    <div class="goods_info clearfix">
                                                        <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                                        <div class="goods_name">
                                                            ${item->goods_name!}
                                                        </div>
                                                    </div>
                                                </td>
                                                <td width="15%">${item->shop_price!}</td>
                                                <td width="15%">${item->goods_number!}</td>
                                                {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                <td width="20%"><a href="javascript:void(0)" item="${item->goods_id!}" class="change_goods_del">删除</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                    </tbody>
                                </table>
                                </div>
                                {{--添加平台分类--!}
                                <div class="choosed_info clearfix" >
                                    <div class="add_cate">
                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />添加平台分类
                                    </div>
                                </div>

                                <table class="cat_table" width="380px" cat_array="${package_sale->cat_ids_1!}">
                                    <tr>
                                        <th width="100%"><span>平台分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_cls">编辑</a><a href="javascript:;" class="del_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                                {{--添加商家分类--!}
                                <div class="goods_sort_module " >
                                    <div class="add_sort">
                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                        添加商家分类
                                    </div>
                                </div>
                                <table class="sort_table" width="380px" sort_array="${package_sale->cat_ids_1!}">
                                    <tr>
                                        <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_sort_cls">编辑</a><a href="javascript:;" class="del_sort_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                            </div>
                            <div class="sth_table" style="display: none">
                                <input name="goods_ids_2" class="goods_ids" value="${package_sale->goods_ids_2!}" hidden>
                                <input name="cat_ids_2" class="cat_ids" value="${package_sale->cat_ids_2!}" hidden>
                                <input name="sort_ids_2" class="sort_ids" value="${package_sale->sort_ids_2!}" hidden>
                                <div class="btn_add_goods">
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商品
                                </div>
                                <div class="goods_area" style="width:93%;margin:0;padding-right:5px;max-height: 500px;overflow-y: scroll;border: none">
                                <table class="goods_modal" <#if ($package_sale->goods_ids_2) style="display: block;" </#if>>
                                    <thead>
                                    <tr>
                                        <th>商品名称</th>
                                        <th>价格</th>
                                        <th>库存</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody class="tbody">
                                    <#if ($package_sale->goods_ids_2)
                                        <#list ($package_sale->goods_array2 as $item)
                                            <tr>
                                                <td width="50%">
                                                    <div class="goods_info clearfix">
                                                        <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                                        <div class="goods_name">
                                                            ${item->goods_name!}
                                                        </div>
                                                    </div>
                                                </td>
                                                <td width="15%">${item->shop_price!}</td>
                                                <td width="15%">${item->goods_number!}</td>
                                                {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                <td width="20%"><a href="javascript:void(0)" item="${item->goods_id!}" class="change_goods_del">删除</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                    </tbody>
                                </table>
                                </div>
                                {{--添加平台分类--!}
                                <div class="choosed_info clearfix" >
                                    <div class="add_cate">
                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />添加平台分类
                                    </div>
                                </div>

                                <table class="cat_table" width="380px" cat_array="${strategy->recommend_cat_id!}">
                                    <tr>
                                        <th width="100%"><span>平台分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_cls">编辑</a><a href="javascript:;" class="del_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                                {{--添加商家分类--!}
                                <div class="goods_sort_module ">
                                    <div class="add_sort">
                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                        添加商家分类
                                    </div>
                                </div>
                                <table class="sort_table" width="380px" sort_array="${package_sale->sort_ids_2!}">
                                    <tr>
                                        <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_sort_cls">编辑</a><a href="javascript:;" class="del_sort_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                            </div>
                            <div class="sth_table" style="display: none">
                                <input name="goods_ids_3" class="goods_ids" value="${package_sale->goods_ids_3!}" hidden>
                                <input name="cat_ids_3" class="cat_ids" value="${package_sale->cat_ids_3!}" hidden>
                                <input name="sort_ids_3" class="sort_ids" value="${package_sale->sort_ids_3!}" hidden>
                                <div class="btn_add_goods" >
                                    <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                    添加商品
                                </div>
                                <div class="goods_area" style="width:93%;margin:0;padding-right:5px;max-height: 500px;overflow-y: scroll;border: none">
                                <table class="goods_modal" <#if ($package_sale->goods_ids_3) style="display: block;" </#if>>
                                    <thead>
                                    <tr>
                                        <th>商品名称</th>
                                        <th>价格</th>
                                        <th>库存</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody class="tbody">
                                    <#if ($package_sale->goods_ids_3)
                                        <#list ($package_sale->goods_array3 as $item)
                                            <tr>
                                                <td width="50%">
                                                    <div class="goods_info clearfix">
                                                        <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                                        <div class="goods_name">
                                                            ${item->goods_name!}
                                                        </div>
                                                    </div>
                                                </td>
                                                <td width="15%">${item->shop_price!}</td>
                                                <td width="15%">${item->goods_number!}</td>
                                                {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                <td width="20%"><a href="javascript:void(0)" item="${item->goods_id!}" class="change_goods_del">删除</a></td>
                                            </tr>
                                        </#list>
                                    </#if>
                                    </tbody>
                                </table>
                                </div>
                                {{--添加平台分类--!}
                                <div class="choosed_info clearfix" >
                                    <div class="add_cate">
                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />添加平台分类
                                    </div>
                                </div>

                                <table class="cat_table" width="380px" cat_array="${strategy->recommend_cat_id!}">
                                    <tr>
                                        <th width="100%"><span>平台分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_cls">编辑</a><a href="javascript:;" class="del_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                                {{--添加商家分类--!}
                                <div class="goods_sort_module " >
                                    <div class="add_sort">
                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                                        添加商家分类
                                    </div>
                                </div>
                                <table class="sort_table" width="380px" sort_array="${package_sale->sort_ids_3!}">
                                    <tr>
                                        <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_sort_cls">编辑</a><a href="javascript:;" class="del_sort_cat">删除</a></div></th>
                                        <!-- <th width="30%" >操作</th> -->
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer">
            <a href="##">保存</a>
        </div>
    </div>
</div>
<div id="set-category-1" class="set-category" style="display: none">
    <#if (!$cat_list1)
        <div class="no_category">
            <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无分类</p>
            </div>
        </div>
    <#else>
        <ul>
            <#if ($cat_list1)
                <#list ($cat_list1 as $item)
                    <li class="cate_li">
                        <div class="first_cate">
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" />
                            <span>
                            <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                            <label for="cat_${item->cat_id!}" goods_num="${item->goods_num!}">${item->cat_name!}(${item->goods_num!})</label>
                        </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                                <span>
                                    <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                    <label for="cat_${sub_item->cat_id!}" goods_num="${sub_item->goods_num!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                                </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                                <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                                <label for="cat_${th_item->cat_id!}" goods_num="${th_item->goods_num!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                            </span>
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </#list>
                            </div>
                        </#if>
                    </li>
                </#list>
            </#if>
        </ul>
    </#if>
</div>
<div id="set-category-2" class="set-category" style="display: none">
    <#if (!$cat_list2)
        <div class="no_category">
            <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无分类</p>
            </div>
        </div>
    <#else>
        <ul>
            <#if ($cat_list2)
                <#list ($cat_list2 as $item)
                    <li class="cate_li">
                        <div class="first_cate">
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" />
                            <span>
                            <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                            <label for="cat_${item->cat_id!}" goods_num="${item->goods_num!}">${item->cat_name!}(${item->goods_num!})</label>
                        </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                                <span>
                                    <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                    <label for="cat_${sub_item->cat_id!}" goods_num="${sub_item->goods_num!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                                </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                                <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                                <label for="cat_${th_item->cat_id!}" goods_num="${th_item->goods_num!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                            </span>
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </#list>
                            </div>
                        </#if>
                    </li>
                </#list>
            </#if>
        </ul>
    </#if>
</div>
<div id="set-category-3" class="set-category" style="display: none">
    <#if (!$cat_list3)
        <div class="no_category">
            <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无分类</p>
            </div>
        </div>
    <#else>
        <ul>
            <#if ($cat_list3)
                <#list ($cat_list3 as $item)
                    <li class="cate_li">
                        <div class="first_cate">
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" />
                            <span>
                            <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                            <label for="cat_${item->cat_id!}" goods_num="${item->goods_num!}">${item->cat_name!}(${item->goods_num!})</label>
                        </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                                <span>
                                    <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                    <label for="cat_${sub_item->cat_id!}" goods_num="${sub_item->goods_num!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                                </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                                <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                                <label for="cat_${th_item->cat_id!}" goods_num="${th_item->goods_num!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                            </span>
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </#list>
                            </div>
                        </#if>
                    </li>
                </#list>
            </#if>
        </ul>
    </#if>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
{{--<script type="text/javascript" src="/js/admin/select_sort.js?v=1.0.2"></script>--!}

<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
//    商品组

    $(".each_gr input[type='checkbox']").each(function () {
        if ($(this).is(":checked")) {
            $(this).parent().parent().find('.gr_set').css("display",'block');
        } else {
            $(this).parent().parent().find('.gr_set').css("display",'none');
        }
        $(this).click(function () {
            var checkbox_index = $(this).val();
            if (checkbox_index == 1) return false;
            if($(this).is(":checked")){
                $(this).parent().parent().find('.gr_set').css("display",'block');
                $('.ga_title > a').eq((parseInt(checkbox_index) - 1)).css("display",'inline-block');
            }else{
                $(this).parent().parent().find('.gr_set').css("display",'none');
                $(this).parent().parent().find('.group_name').val('');
                $(this).parent().parent().find('.goods_number').val('');
                $('.ga_title > a').eq((parseInt(checkbox_index) - 1)).css("display",'none');
                var sth_table_div = $('.sth_table').eq((parseInt(checkbox_index) - 1));
                sth_table_div.find('.goods_ids').val('');
                sth_table_div.find('.goods_modal .tbody').html('');
                sth_table_div.find('.goods_modal').css('display','none');
                sth_table_div.find('.cat_ids').val('');
                sth_table_div.find('.del_cat').click();
                sth_table_div.find('.sort_ids').val('');
                sth_table_div.find('.del_sort_cat').click();
            }
            $('.ga_title > a').eq(0).click();
        })
    })
    $('.ga_title > a').click(function () {
        $('.ga_title > a').removeClass('active');
        $(this).addClass('active');
        $('.sth_table').css('display','none');
        $('.sth_table').eq($(this).index()).css('display','block');
        // console.log($(this).index())
    });

    //添加商品
    $('.btn_add_goods').on('click',function(){
        var sth_table_div = $(this).parent();
        var goods_ids = sth_table_div.find('.goods_ids').val();
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px','430px']
                , content: '/admin/public/purchase/goods/list?record_select_value=' + goods_ids //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var goods = sth_table_div.find('.goods_ids').val();
                    var body = layer.getChildFrame('body', index);
                    if(goods !='') {
                        if(isNaN(goods)) {
                            var goods_array = goods.split(',');
                            body.contents().find("tr").each(function(){
                                if($.inArray($(this).attr("goods_id"),goods_array)>-1){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                        else{
                            body.contents().find("tr").each(function(){
                                if($(this).attr("goods_id")==goods){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                    }
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var checkedId = iframe.find('#record_select_value').val();
                    sth_table_div.find('.goods_ids').val(checkedId);
                    if (checkedId) {
                        util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                            // console.log(response);
                            var list = response.content;
                            var html = '';
                            for (var i in list) {
                                list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                                html += '<tr>' +
                                    '        <td>' +
                                    '            <div class="goods_img" >' +
                                    '                <img src="'+list[i].goods_img+'" style="width: 40px;height: 40px;"/>' +
                                    '            </div>' +
                                    '            <div class="goods_info clearfix">' +
                                    '                <div class="goods_name">'+list[i].goods_name+'</div>' +
                                    '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                                    '<td>'+list[i].goods_number+'</td>' +
                                    '<td><a href="##" item="'+list[i].goods_id+'" class="change_goods_del">删除</a></td>'
                                '        </td>';
                                html += '</tr>';
                            }
                            sth_table_div.find('.goods_modal').css('display','block');
                            sth_table_div.find('.goods_modal .tbody').html(html);
                            layer.close(index);
                        },{select_id: checkedId});
                    } else {
                        sth_table_div.find('.goods_modal').css('display','none');
                    }
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    $("body").on('click','.change_goods_del',function(){
        var del_goods_id = $(this).attr('item');
        var sth_table_div = $(this).parents('.sth_table');
        var goods = sth_table_div.find('.goods_ids').val();
        console.log(goods)
        if(isNaN(goods)) {
            var goods_array = goods.split(',');
            for (var i = 0; i < goods_array.length; i++) {
                if (goods_array[i] == del_goods_id) {
                    goods_array.splice(i, 1);
                    break;
                }
            }

            sth_table_div.find('.goods_ids').val(goods_array.join());
        }
        else{
            sth_table_div.find('.goods_ids').val('');
            sth_table_div.find('.goods_modal').css('display','none');
        }
        $(this).parent().parent().remove();
    });

    //选择商品分类
    $('.add_sort,.edit_sort_cls').on('click',function(){
        var sth_table_div = $(this).parents('.sth_table');
        var sort_ids = sth_table_div.find('.sort_ids').val();
        console.log(sort_ids)
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['添加商家分类', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['580px','540px']
                , content: '/admin/frame/goods/sort/select?type=2&sort_ids=' + sort_ids //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success:function(layero,index){

                }
                , yes: function (index, layero) { //保存按钮的回调
                    var cat_array = [];
                    var iframe = layer.getChildFrame('body', index);
                    sth_table_div.find(".sort_table tr:gt(0)").remove();
                    var ul = $('<ul>');
                    ul.addClass('cat_ul clearfix');
                    var html= `<div>
                                    示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span>
                                </div>`
                    iframe.find('input[name="sort_id[]"]:checked').each(function () {
                        cat_array.push($(this).val());
                    });
                    iframe.find('.sort_li').each(function(){
                        var firstCheck = $(this).find('.first_sort_cate').find('input[type="checkbox"]');
                        var secondCheck = $(this).find('.second_sort_cate').children().children('span').find('input[type="checkbox"]');
                        if(firstCheck.is(':checked') === true){
                            var el = $('<li class="first_cat">');
                            el.text(firstCheck.next().text());
                            ul.append(el);
                            return;
                        } else if (firstCheck.is(':checked') === false) {
                            secondCheck.each(function(){
                                if($(this).is(':checked')){
                                    var el = $('<li class="second_cat">');
                                    el.text($(this).next().text());
                                    ul.append(el);
                                    return;
                                }
                            })
                        }
                    });
                    sth_table_div.find('.sort_table tr:first-child').after($('<tr>').append(html).append(ul));
                    if (sth_table_div.find('.sort_table ul li').length === 0) {
                        sth_table_div.find('.sort_table').hide();
                    } else {
                        sth_table_div.find('.sort_table').show();
                    }
                    sth_table_div.find(".sort_ids").val(cat_array.join());
                    // console.log(cat_array);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    $("body").on('click','.del_sort_cat',function(){
        var sth_table_div = $(this).parents('.sth_table');
        sth_table_div.find('.sort_ids').val('');
        sth_table_div.find('.sort_table').find('tr').eq(1).remove();
        sth_table_div.find('.sort_table').hide();
    })
    $('.sth_table').each(function () {
        var sth_table_div = $(this);
        var sort_id = sth_table_div.find(".sort_ids").val();
        var ul = $('<ul>');
        ul.addClass('cat_ul clearfix');
        var html= `<div>
                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span>
                       </div>`
        util.ajax_json('/admin/frame/goods/sort/select', function(response){
            let sort_list = response.content.sort_list;
            console.log(sort_list)
            sort_list.forEach(element => {
                if(element.checked && element.level === 0){
                var el = $('<li class="first_cat">');
                el.text(element.sort_name + '('+ element.sort_goods_num +')');
                ul.append(el)
                return false;
            } else {
                element.child.forEach(elem => {
                    if(elem.checked && elem.level === 1){
                    var el = $('<li class="second_cat">');
                    el.text(elem.sort_name + '('+ elem.sort_goods_num +')');
                    ul.append(el)
                    return false;
                }
            })
            }
        });
        },{type:1,sort_ids:sort_id});
        if(sort_id){
            sth_table_div.find('.sort_table tr:first-child').after($('<tr>').append(html).append(ul));
            sth_table_div.find('.sort_table').show()
        }else{
            sth_table_div.find('.sort_table').hide()
        }
    });

    //选择分类
    $('.add_cate,.edit_cls').on('click',function(){
        var sth_table_div = $(this).parents('.sth_table');
        var cat_index = sth_table_div.index();
        console.log(sth_table_div.find(".cat_ids").val())
        layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 1
                    , title: ['添加平台分类', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['580px','540px']
                    , content: $('#set-category-' + cat_index) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , yes: function (index, layero) { //保存按钮的回调
                        var cat_array = [];
                        var iframe = $('#set-category-' + cat_index);
                        sth_table_div.find(".cat_table tr:gt(0)").remove();
                        var ul = $('<ul>');
                        ul.addClass('cat_ul clearfix');
                        var html= `<div>
                                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span><span class="third_cat">三级分类</span>
                                      </div>`
                        iframe.find('input[name="cat_id[]"]:checked').each(function () {
                            cat_array.push($(this).val());
                        });
                        console.log(cat_array)
                        iframe.find('.cate_li').each(function(){
                            var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                            var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                            var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                            thirdCheck.each(function(){
                                if($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')){
                                    return;
                                } else if($(this).is(':checked')) {
                                    var el = $('<li class="third_cat">');
                                    el.text($(this).next().text());
                                    ul.append(el)
                                    return;
                                }
                            })
                            if(firstCheck.is(':checked') === true){
                                var el = $('<li class="first_cat">');
                                el.text(firstCheck.next().text());
                                ul.append(el)
                                return;
                            } else if (firstCheck.is(':checked') === false) {
                                secondCheck.each(function(){
                                    if($(this).is(':checked')){
                                        var el = $('<li class="second_cat">');
                                        el.text($(this).next().text());
                                        ul.append(el)
                                        return;
                                    }
                                })
                            }
                        })
                        sth_table_div.find('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
                        if (sth_table_div.find('.cat_table ul li').length === 0) {
                            sth_table_div.find('.cat_table').hide();
                        } else {
                            sth_table_div.find('.cat_table').show();
                        }
                        sth_table_div.find(".cat_ids").val(cat_array.join());
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调


                    }
                });
            });
    });
    //删除分类
    $(".del_cat").click(function(){
        var sth_table_div = $(this).parents('.sth_table');
        var cat_index = sth_table_div.index();
        console.log(sth_table_div.find(".cat_ids").val())
        sth_table_div.find('.cat_ids').val('');
        sth_table_div.find('.cat_table').find('tr').eq(1).remove();
        $('#set-category-'+cat_index+' ul input[type="checkbox"]:checked').each(function(){
            $(this).prop('checked',false);
        })
        sth_table_div.find('.cat_table').hide();
    })
    //一级分类的全选
    $('.first_cate input[type="checkbox"]').click(function () {
        if($(this).is(':checked')){
            $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",true);
        }else{
            $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",false);
        }
    });
    //二级分类的选中
    $('.second_cate .second_box').click(function () {
        var allLengthS = $(this).parent().parent().parent().find('.second_box').length;
        var checkedLengthS = $(this).parent().parent().parent().find('.second_box:checked').length;
        /* 二级分类点击，下面所有三级分类被选中 */
        $(this).parent().next().find('input[type="checkbox"]').prop("checked",$(this).prop("checked"));
        /* 二级分类全部选中，一级选中 */
        if(allLengthS === checkedLengthS){
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
        } else {
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
        }
    });
    //三级分类的选中
    $('.third_cate input[type="checkbox"]').click(function () {
        $(this).attr("checked", "checked");
        var allLength = $(this).parent().parent().find('input[type="checkbox"]').length;
        var checkedLength = $(this).parent().parent().find('input[type="checkbox"]:checked').length;
        if(allLength === checkedLength){
            $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",true);
        } else {
            $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",false);
        }
        var secondLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]').length;
        var secondCheckedLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]:checked').length;
        /* 三级全部选中 判断二级是否全部选中 */
        if(secondLength === secondCheckedLength){
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
        } else {
            $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
        }
    });
    //点击展开二级和三级分类
    $('.cate_open').click(function(){
        var flag_open = $(this).attr('data-flag');
        if(flag_open == 'true'){
            $(this).parent().next().show();
            $(this).attr('src','/image/admin/cate_jian.png');
            $(this).attr('data-flag','false');
            flag_open = 'false';
        }else if(flag_open == 'false'){
            $(this).parent().next().hide();
            $(this).attr('src','/image/admin/cate_jia.png');
            $(this).attr('data-flag','true');
            flag_open = 'true';
        }
    });
    $('.sth_table').each(function () {
        var sth_table_div = $(this);
        var cat_id = sth_table_div.find(".cat_ids").val();
        console.log(cat_id)
        if(cat_id){
            var category_div = $('#set-category-'+ $(this).index());
            sth_table_div.find(".cat_table tr:gt(0)").remove();
            var ul = $('<ul>');
            ul.addClass('cat_ul clearfix');
            var html= `<div>
                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span><span class="third_cat">三级分类</span>
                        </div>`
            category_div.find('.cate_li').each(function(){
                var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                thirdCheck.each(function(){
                    if($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')){
                        return;
                    } else if($(this).is(':checked')) {
                        var el = $('<li class="third_cat">');
                        el.text($(this).next().text());
                        ul.append(el)
                        return;
                    }
                })
                if(firstCheck.is(':checked') === true){
                    var el = $('<li class="first_cat">');
                    el.text(firstCheck.next().text());
                    ul.append(el)
                    return;
                } else if (firstCheck.is(':checked') === false) {
                    secondCheck.each(function(){
                        if($(this).is(':checked')){
                            var el = $('<li class="second_cat">');
                            el.text($(this).next().text());
                            ul.append(el)
                            return;
                        }
                    })
                }
            })
            sth_table_div.find('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
            // console.log(1)
            sth_table_div.find('.cat_table').show()
        }else{
            sth_table_div.find('.cat_table').hide()
        }
    });

    $(".btn_save").click(function () {
        if($(".act_names").val() == ''){
            util.mobile_alert('请输入活动名称');
            return false;
        }
        if($(".start_date").val() == ''){
            util.mobile_alert('请选择活动开始时间');
            return false;
        }
        if($(".end_date").val() == ''){
            util.mobile_alert('请选择活动结束时间');
            return false;
        }
        if($(".sum_price").val() == ''){
            util.mobile_alert('请输入总价格');
            return false;
        }
        util.ajax_json('/admin/market/package/save',function (res) {
            console.log(res)
            if (res.error == 0) {
                $('[name="id"]').val(res.content);
                util.mobile_alert('保存成功');
                window.location.href = '/admin/market/package/list?nav=1';
            } else {
                util.mobile_alert(res.message);
            }
        },$('#form1').serialize())
    })
    $('.look_example').mouseover(function () {
        $('.show_img').css('display', 'block');
        $('.show_img').mouseover(function () {
            $('.show_img').css('display', 'block');
        })
        $('.show_img').mouseleave(function () {
            $('.show_img').css('display', 'none');
        })
    })

    $('.look_example').mouseleave(function () {
        $('.show_img').css('display', 'none');
    })
</script>
<script>
    getPowerInfo('main_config','package_sale','sub_4','打包一口价',0);
    $(".goods_number").blur(function(){
        if($(this).val() != "" && $(this).val() > 10){
            $(this),focus();
            $(this).val("1");
            util.mobile_alert('商品最多设置十件');
            return false;
        }
    })

</script>