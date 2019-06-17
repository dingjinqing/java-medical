<#include "/admin/header.ftl">
<link href="/css/admin/goods_list.css?v=1.2.3" rel="stylesheet" type="text/css"/>
<style type="text/css">
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
    
    .search-bl{
        width: 150px;
        margin-top: 0;
    }
    .primary {
        width: 120px;
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
    
    .list-bottom-show tbody > tr > td:nth-child(4){
        word-break: break-all;
    }
    .nav_list{
        line-height: 40px;
    }
</style>

<div class="goods-container">
    <div class="title">
        <span>{{ trans("admin/shop.list-top.li_top_name")!} </span>/
        <span>评价管理</span>
    </div>
    <div class="main-container">
        <form name="form" action="/admin/goods/comment/add/manage?nav=2&top_index=2"  id="form1" method="post">
            <input type="hidden" name="del">
            <input type="hidden" value="${goods_all_labels_id_str!}" name="goods_all_labels_id_str">
            <input type="hidden" value="${goods_all_labels_name_str!}" name="goods_all_labels_name_str">
            <input type="hidden" name="export" id="export">
            <input type="hidden" name="erp_export" id="erp_export">
            <div style="background-color: #fff">
                <ul id="tab" class="nav_list clearfix">
                    <li <#if ($nav==0)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/list?nav=0">评价记录</a>
                    </li>
                    <li <#if ($nav==1)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/list?nav=1">评价审核</a></li>

                    <#if ($addCommentSwitch == 1)
                        <li <#if ($nav==2)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/add/manage?nav=2">商品列表</a></li>
                    </#if>
                </ul>
                <script>
                    // tab切换
                    $("[data-toggle='tab']").click(function () {
                        var url = $(this).attr("url");
                        if (url != undefined) {
                            window.location.href = url;
                        }
                    });
                </script>
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
                        <button class="btn_searchinfo">查询</button>
                    </li>

                </ul>
            </div>

            <input type="hidden" name="sort_field" id="sort_field" value="${sort_field!}">
            <input type="hidden" name="sort_way" id="sort_way" value="${sort_way!}">
            <div class="list-bottom" style="padding:10px">
                <table align="center" class="list-bottom-show">
                    <thead style="border: 1px solid #eee;">
                    <tr id="list-bottom-top">
                        <th width="17%">{{trans("admin/shop.list-bottom.name")!}</th>
                        <th width="10%">{{trans("admin/shop.list-bottom.good_sn")!}</th>
                        <th width="8%">商家分类</th>
                        <th width="8%">
                            <a href="javascript:void(0);" onClick="return sort_f(1);">{{trans("admin/shop.list-bottom.price")!}</a>
                            <span id="sort_symbo">${sort_symbo[1]!}</span>
                        </th>
                        <th width="7%">
                            <a href="javascript:void(0);" onClick="return sort_f(2);">{{trans("admin/shop.list-bottom.good_count")!}</a>
                            <span id="sort_symbo">${sort_symbo[2]!}</span>
                        </th>
                        <th width="7%">
                            <a href="javascript:void(0);" onClick="return sort_f(3);">{{trans("admin/shop.list-bottom.sale_sum")!}</a>
                            <span id="sort_symbo">${sort_symbo[3]!}</span>
                        </th>
                        <th width="7%">
                            <a href="javascript:void(0);" onClick="return sort_f(4);">访客数</a>
                            <span id="sort_symbo">${sort_symbo[4]!}</span>
                        </th>
                        <th width="7%">
                            <a href="javascript:void(0);" onClick="return sort_f(5);">浏览量</a>
                            <span id="sort_symbo">${sort_symbo[5]!}</span>
                        </th>
                        <th width="8%">
                            <a href="javascript:void(0);" onClick="return sort_f(6);">实际评价数</a>
                            <span id="sort_symbo">${sort_symbo[6]!}</span>
                        </th>
                        <th width="8%">
                            <a href="javascript:void(0);" onClick="return sort_f(7);">添加评价数</a>
                            <span id="sort_symbo">${sort_symbo[7]!}</span>
                        </th>
                        <th width="16%">{{trans("admin/shop.list-bottom.operate")!}</th>
                    </tr>
                    </thead>
                    <tbody id="" class="list">
                    <#list ($data_list as $item)
                        <tr>
                            <td align="left" class="goods-name"  style="border-left: none; position: relative;" title="${item->goods_name_title!}">
                                <span >
                                    <span class="clearfix" style="margin-left: 5px;display:block;width:230px;position: relative;">
                                        <#if  ($item->is_card_exclusive == 1)
                                            <span class="card_exclusive">专享商品</span>
                                        </#if>

                                        <img src="${item->goods_img!}!small" alt="" class="name-img">
                                    <span class="list-name">
                                        <#if  ($item->goods_type == 1)
                                            <span style="border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px;display: inline-block">拼团</span>
                                        <#elseif>($item->goods_type == 3)
                                            <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">砍价</span>
                                        <#elseif>($item->goods_type == 5)
                                            <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">秒杀</span>
                                        <#elseif>($item->goods_type == 7)
                                            <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">限时降价</span>
                                        </#if>
                                        <#if  ($item->source == 0 && in_array($shop_flag,[1,2]))
                                            <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">自营</span>
                                        </#if>
                                        {!! $item->goods_name !!}
                                        </span>
                                    </span>
                                </span>
                            </td>
                            <td align="center" >${item->goods_sn!}</td>
                            <td align="center" >${item->sort_name!}</td>
                            <td align="center" >${item->shop_price!}</td>
                            <td align="center" >${item->goods_number!}</td>
                            <td align="center" >${item->goods_sale_num!}</td>
                            <td align="center" >${item->uv ?? 0!}</td>
                            <td align="center" >${item->pv ?? 0!}</td>
                            <td align="center" >${item->real_com_num!}</td>
                            <td align="center" >${item->shop_com_num!}</td>
                            <td align="center"  class="share_td" goods_id="${item->goods_id!}">
                                <div>
                                    <div style="display: inline-block;position: relative">
                                        <a href="/admin/goods/comment/list?nav=0&goods_id=${item->goods_id!}">查看</a>
                                    </div>
                                    <span class="goods_new">
                                        <a href="/admin/goods/comment/add?nav=3&goods_id=${item->goods_id!}" class="" >添加评价</a>
                                    </span>
                                </div>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            {{--分页和无数据的样式--!}
           <div class="paging_footer" style="margin-top: 12px;">
               <#include "/admin/jump_page_admin.ftl">
           </div>
        </form>
    </div>
</div>

<#include ("admin.share_common")
<script>
    $("#export").val('');
    function gopage(page) {
        var last_page = '${data_list -> last_page!}';
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        $("#page").val(page);
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
    
    
    $("#search").click(function () {
        $("#form1").submit();
    });

    $('select[name="sort_id"]').change(function() {
        $("#form1").submit();
    });
    
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
