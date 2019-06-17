<#include "/admin/header.ftl">

    <link href="/css/admin/goods_list.css?v=1.0.4" rel="stylesheet" type="text/css"/>
    <style>
        a {
            text-decoration: none;
        }
        .create-good:hover{
            color:#fff;
            text-decoration: none;
            background-color: #447af9 !important;
            border-color: #447af9 !important;
        }
        .create-good:focus{
            color:#fff;
            text-decoration: none;
            background-color: #447af9 !important;
            border-color: #447af9 !important;
        }
        .tb_paging td a {
            text-decoration: none;
        }
        .tb_paging td a:hover{
            background: #fff !important;
            color: #5a8bff;
            border:1px solid #5a8bff;
        }
        .tb_paging td a:focus{
            background: #5a8bff !important;
            color: #fff;
            border:1px solid #5a8bff;
        }
        input[name='page']:focus {
            border: 1px solid #5a8bff;
            box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
            -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
            -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        }
        .item-menu a{
            margin: 0;
        }
        .is_sync {
            border: 1px red solid;
            padding: 0px 3px;
            color: red;
            border-radius: 2px;
            font-size: 12px;
            display: inline-block;
        }
    </style>
    <div class="goods-container">
        <div class="title">
            <span>{{ trans("admin/store_manage.store_manage")!} / </span>
            <span>
               {{ trans("admin/store_manage.store_good.title")!}
            </span>
        </div>

        <form name="form" action="/admin/store/goods/list?store_id=${store_id!}" id="form1" method="post">
        <div class="main-container">
            <div class="list-center" style="height: auto;padding-bottom: 5px">
                {{ csrf_field()!}
                <input type="hidden" name="store_id" class="store_id" value="${store_id!}">
                <input type="hidden" name="prd_id" value="">
                <input type="hidden" name="act" class="act" value="sel">
                <span>
                    <div class="list_store_name">门店名称：<span>${store_name!}</span></div>
                    <select name="cat_id" class="list-center-sel">
                        ${cat_id!}
                        <option value="0">请选择商品分类</option>
                        <#if (!empty($cat_list))
                        <#list ($cat_list as $item)
                            <option value="${item['cat_id']!}"
                                <#if ($cat_id == $item['cat_id'])selected="selected"</#if>>${item['cat_name']!}</option>
                        </#list>
                            </#if>
                    </select>
                    <select name="is_on_sale" class="list-center-sel">
                            <option value="-1" <#if ($is_on_sale == -1)selected="selected"</#if>>请选择上架状态</option>
                            <option value="1" <#if ($is_on_sale == 1)selected="selected"</#if>>上架的商品</option>
                            <option value="0" <#if ($is_on_sale == 0)selected="selected"</#if>>下架的商品</option>
                    </select>
                    <select name="is_sync" class="list-center-sel" onchange="submit()">
                        <option value="-1" <#if ($input['is_sync'] == -1)selected="selected"</#if>>是否已同步POS</option>
                        <option value="1" <#if ($input['is_sync'] == 1)selected="selected"</#if>>是</option>
                        <option value="0" <#if (isset($input['is_sync']) && $input['is_sync'] == 0)selected="selected"</#if>>否</option>
                    </select>
                    <span class="search-bl">
                        <input type="text" name='keywords' value="${keywords!}" placeholder="{{ trans("admin/shop.list-center.search")!} | 条码"
                               class="primary" >
                        <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search" style="margin: 0 0 3px 5px;">
                    </span>
                    <a href="/admin/store/goods/add?store_id=${store_id!}" class="create-good">更新商品</a>
                </span>
            </div>

            <div class="list-bottom">
                <table align="center" class="list-bottom-show">
                    <thead>
                        <tr id="list-bottom-top">
                            <th></th>
                            <th>{{trans("admin/shop.list-bottom.name")!}</th>
                            <th >{{trans("admin/shop.list-bottom.prd_desc")!}</th>
                            <th>分类</th>
                            <th>{{trans("admin/shop.list-bottom.price")!}</th>
                            <th>库存</th>
                            <th>{{trans("admin/shop.list-bottom.prd_sn")!}</th>
                            <th>商品条码</th>
                            <th>{{trans("admin/shop.list-bottom.operate")!}</th>
                        </tr>
                    </thead>
                    <tbody id="" class="list">
                    <#list ($data_list as $item)
                        <tr>
                            <td align="center" width="5%" style="border-right: none;position: relative;" >
                                <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" />
                                <input type="checkbox" name="checkbox" prd_id="${item->prd_id!}">
                            </td>
                            <td align="left" class="goods-name" width="25%" style="border-left: none;">
                                <span>
                                    <img src="${item->goods_img!}" alt="" class="name-img">
                                    <span class="list-name" style="">
                                        <#if  ($item->is_sync)
                                        <span class="is_sync">已同步</span>
                                        </#if>
                                        ${item->goods_name!}
                                    </span>
                                </span>
                            </td>
                            <td align="center" width="15%" >
                                ${item->prd_desc!}
                            </td>
                            <td align="center" width="10%">${item->cat_name!}</td>
                            <td align="center" width="5%">
                                <#if  ($item->is_sync)
                                    ${item->product_price!}
                                <#else>
                                    ${item->prd_price!}
                                </#if>
                            </td>
                            <td align="center" width="5%">
                                <#if  ($item->is_sync)
                                    ${item->product_number!}
                                <#else>
                                    ${item->prd_number!}
                                </#if>
                            </td>
                            <td align="center" width="10%">
                                ${item->prd_sn!}
                            </td>
                            <td align="center" width="10%" class="on_sale">
                                <div>${item->prd_codes!}</div>
                            </td>
                            <td align="left" width="15%">
                                <p style="margin-bottom: 10px;text-align: center;">
                                    <#if ($item->is_on_sale == 1)
                                        <a href="javascript:void(0);" class="change-onSale" prd_id="${item->prd_id!}" act="off_sale" style="margin: 0;color: blue">{{ trans("admin/shop.list-bottom-operate.li_done")!}</a>
                                        {{--{{ trans("admin/shop.list-bottom.is_on_sale")!}--!}
                                    <#elseif>($item->is_on_sale == 0)
                                        <a href="javascript:void(0);" class="change-onSale" prd_id="${item->prd_id!}" act="on_sale" style="margin: 0;color: blue">{{ trans("admin/shop.list-bottom-operate.li_on")!}</a>
                                        {{--{{ trans("admin/shop.list-bottom.is_off_sale")!}--!}
                                    </#if>
                                </p>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                    <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table" border="0">
                        <tr >
                            <#if  ($data_list->count() > 0)
                            <td style="padding-left: 15px;">
                                <span style="position:relative;">&nbsp;&nbsp;
                                    <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" style="top: -1px;" />
                                    <input type="checkbox" id="select-all" />&nbsp;&nbsp;
                                    <label for="select-all">全选</label>
                                </span>
                                    <input name="off_sale" type="button" value="下架" class="down tb-btm-left off_sale" style="color: #0E70CA">
                                    <input name="on_sale" type="button" value="上架" class="down tb-btm-left on_sale"  style="color: #0E70CA">
                            </td>
                            </#if>
                            <td align="right">
                                <#include "/admin/jump_page_admin.ftl">
                            </td>
                        </tr>
                    </table>
                </table>
            </div>
        </div>
        </form>
    </div>


<script>

    $(".change-onSale").click(function(){
        var obj = $(this);
        var data={};
        data.act = $(this).attr("act");
        data.prd_id = $(this).attr("prd_id");
        data.store_id = $(".store_id").val();
        util.ajax_json('/admin/store/goods/sale',function(d){
            if(d&&d.error==0){
                if(data.act=='off_sale'){
                    obj.attr("act",'on_sale');
                    obj.html('上架');
                }else{
                    obj.attr("act",'off_sale');
                    obj.html('下架');
                }
                util.mobile_alert(d.content);
            }
            else{
                util.mobile_alert(d.message);
            }
        },data);
    });

    $('[name="is_on_sale"]').change(function(){
        $("#form1").submit();
    });

    $('input[type="button"]').click(function(){
        var prd_id = [0];
        $('input[name="checkbox"]:checked').each(function () {
            prd_id.push($(this).attr('prd_id'));
        });
        if (prd_id.length == 0) {
            util.mobile_alert('未选择任何商品');
            return false;
        }
        else {
            $('input[name="act"]').val($(this).attr('name'));
            $('input[name="prd_id"]').val(prd_id);
            $("#form1").submit();
        }
    });

    $("#search").click(function() {
        $('#page').val(1);
        $("#form1").submit();
    });
    $('input[name="keywords"]').keydown(function () {
        if (event.keyCode == 13 || event.which == 13) {
            $('#page').val(1);
            $("#form1").submit();
        }
    })
    $(".create-good").click(function () {
        layer.msg('更新中...', {time: 20000});
    })
</script>

<#include "/admin/footer.ftl">
<script src="/js/admin/goods_list.js?v=1.0.4" type="text/javascript"></script>
<script src="/js/admin/page.js?v=1.0.2" type="text/javascript"></script>
