<#include ("system.header")
<link href="/css/admin/goods_list.css?v=1.0.6" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<style type="text/css">
    .share_td{
        position: relative;
    }
    .share_td .share_span{
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 67px;
        left:-70px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }
    .share_td .share_span .share_sj{
        position: absolute;
        right: 90px;
        top: -7px;
    }
    .share_td.share_span span{
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }
    .share_td .share_span .code_imgs{
        display: block;
        margin:0 auto;
    }
    .share_td .share_span a{
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
        width: 100%;
        text-align: center;
        margin-left: 0;
    }
    .hover_share{
        display: inline-block;
        width: 29px;
    }
    .share_link{
        padding-top: 15px;
        width: 100%;
    }
    .share_link input{
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }
    .share_link button{
        float: right;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
    }

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
    }
    .list-center {
        height: auto;
    }
    .list-center > span{
        margin: 0;
        padding: 0;
    }
    .list-center .search-bl{
        width: 150px;
    }
    .list-center .search-bl{
        margin:10px 0 10px 40px;
    }
    .list-center .search-bl input{
        width: 122px;
    }
    .list-center > span > .list-center-sel{
        margin-left: 40px;
    }
    .list-center > span > input{
        width: 150px;
        height: 30px;
        border: 1px solid rgb(204, 204, 204);
        border-radius: 3px;
        padding-left: 8px;
    }
    .list a {
        color: #5E88FC;
    }
    .share_td .share_span{
        position: absolute;
        top:80px;
        left:-110px;
    }
</style>

    <div class="goods-container">
        {{--<div class="title">--!}
            {{--<span>--!}
                {{--{{ trans("admin/shop.list-top.li_top_name")!}--!}
            {{--</span>/--!}
            {{--<span>--!}
                {{--<#if ($nav == 0)--!}
                    {{--{{ trans("admin/shop.list-top.li_name")!}--!}
                {{--<#elseif>($nav == 1)--!}
                    {{--{{ trans("admin/shop.list-top.li_name2")!}--!}
                {{--<#elseif>($nav == 2)--!}
                    {{--{{ trans("admin/shop.list-top.li_name3")!}--!}
                {{--</#if>--!}
            {{--</span>--!}
        {{--</div>--!}
        <div class="main-container">
            <form name="form" action="/system/goods/list"  id="form1" method="post">
                <input type="hidden" name="del">
                <input type="hidden" name="shop_id">
                <div class="list-center">
                        {{ csrf_field()!}
                        <input name="goods_id" type="hidden">
                        <input name="all" type="hidden">
                        <input name="nav" type="hidden" value="${nav!}">
                    <span>
                        <select name="cat_id" class="list-center-sel">
                            <option value="0">请选择平台分类</option>
                            <#list ($cat_list as $item)
                                <option value="${item['cat_id']!}"
                                        <#if ($cat_id == $item['cat_id'])selected="selected"</#if>>${item['cat_name']!}</option>
                            </#list>
                        </select>
                        <span class="search-bl" style="margin-left:65px;">
                            <input type="text" name='shop_id' value="${shop_id!}" placeholder="店铺ID"
                                   class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span>
                        <span class="search-bl" style="margin-left:31px">
                            <input type="text" name='shop_name' value="${shop_name!}" placeholder="店铺名称"
                                   class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span>
                        <br/>
                        <span class="search-bl">
                            <input type="text" name='keywords' value="${keywords!}" placeholder="{{ trans("admin/shop.list-center.search")!}"
                                   class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span>
                        <span>{{ trans("admin/user_list.reg_time")!}</span>
                        <input type="text" name="start_time" value="${data->start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                               onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>
                        &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                        <input type="text" name="end_time" value="${data->end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                               onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>

                        <button class="btn_searchinfo">查询</button>
                    </span>
                </div>

                <div class="list-bottom">
                <table align="center" class="list-bottom-show">
                    <thead>
                        <tr id="list-bottom-top">
                            <th>ID</th>
                            <th>店铺ID</th>
                            <th>店铺名称</th>
                            <th>商品ID</th>
                            <th>{{trans("admin/shop.list-bottom.name")!}</th>
                            <th>{{trans("admin/shop.list-bottom.price")!}</th>
                            <th>{{trans("admin/shop.list-bottom.good_sn")!}</th>
                            <th>平台分类</th>
                            <th>{{trans("admin/shop.list-bottom.good_count")!}</th>
                            <th>{{trans("admin/shop.list-bottom.sale_sum")!}</th>
                            <th>{{trans("admin/shop.list-bottom.add_time")!}</th>
                            <th>{{trans("admin/shop.list-bottom.operate")!}</th>
                        </tr>
                    </thead>
                    <tbody id="" class="list">
                    <#list ($data_list as $item)
                        <tr>

                            <td align="center" width="4%">${item->id!}</td>
                            <td align="center" width="5%">
                                <a href="/system/goods/list?shop_id=${item->shop_id!}" target="_blank">${item->shop_id!}</a>
                            </td>
                            <td align="center" width="5%">
                                <a href="/system/goods/list?shop_id=${item->shop_id!}" target="_blank">${item->shop_name!}</a>
                            </td>
                            <td align="center" width="5%">${item->goods_id!}</td>
                            <td align="left" class="goods-name share_td" width="19%" style="border-left: none; position: relative;" >
                                <span>
                                    <img src="${item->goods_img!}!small" alt="" class="name-img">
                                    <span class="list-name">
                                        <#if  ($item->goods_type == 1)
                                            <span style="border: 1px red solid; padding: 0px 6px; color: red; border-radius: 5px;">拼团</span>
                                        </#if>
                                        <span style="margin-left: 5px;">
                                            ${item->goods_name!}
                                        </span>
                                    </span>
                                </span>
                                <a href="##" class="hover_share" shop_id="${item->shop_id!}" goods_id="${item->goods_id!}">分享</a>
                                <div class="share_span">
                                    <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" class="share_sj">
                                    <span>扫一扫，分享给好友吧~</span>
                                    <img src="http://${image_domain!}/image/admin/qrcode.png" alt="" width="120px" class="code_imgs">
                                    <a href="##" download=""  class="down_imgs">下载二维码</a>
                                    <span class="share_link">
                                        <input type="text" value="" id="fe_text" />
                                        <button class="btn_copy" id="d_clip_button" data-clipboard-target="fe_text">复制</button>
                                    </span>
                                </div>
                            </td>
                            <td align="center" width="9%">${item->shop_price!}</td>
                            <td align="center" width="11%">${item->goods_sn!}</td>
                            <td align="center" width="8%">${item->cat_name!}</td>
                            <td align="center" width="9%">${item->goods_number!}</td>
                            <td align="center" width="8%">${item->goods_sale_num!}</td>
                            <td align="center" width="8%">${item->add_time!}</td>
                            <td align="center" width="24%">
                                <p>
                                    <a href="/system/goods/edit?goods_id=${item->goods_id!}&shop_id=${item->shop_id!}" target="_blank">{{ trans("admin/shop.list-bottom-operate.look")!}</a>
                                    {{--<a href="##" class="hover_share" shop_id="${item->shop_id!}" goods_id="${item->goods_id!}">分享</a>--!}
                                </p>
                                <p>
                                    <a href="#" class="del" goods_id="${item->goods_id!}" shop_id="${item->shop_id!}">删除</a>
                                </p>
                                <p>
                                    <a href="/system/good/comment?shop_id=${item->shop_id!}&goods_id=${item->goods_id!}" target="_blank">{{ trans("admin/shop.list-bottom-operate.look_evaluate")!}</a>
                                </p>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                    <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table" border="0">
                        <tr >
                            <td style="padding-left: 15px;">

                            </td>
                            <td align="right">
                                <table width="100%" border="0" class="tb_paging">
                                    <tr>
                                        <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                            <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                            <a href="#"
                                               onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                            <a href="#"
                                               onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                            <a href="#"
                                               onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                            <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                                   size="5"
                                                   onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>

                </table>
            </div>
            </form>
        </div>
    </div>

<script src="/js/admin/goods_list.js?v=1.0.2" type="text/javascript"></script>
<script>
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        console.log(page);

        $("#form1").submit();
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}'            //总页码数

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
                layer.alert('<div style="text-align: center;">' + '确认要下架么' + '</div>', {
                    title: false
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                },function(index){
                    $('input[name="all"]').val(_this.attr('name'));
                    $('input[name="goods_id"]').val(goods_id);
                    $('input[name="shop__id"]').val(shop__id);
                    $("#form1").submit();
                    layer.close(index);
                });
            });

        }
    });


    $(".hover_share").hover(function(){
        var goods_id = $(this).attr("goods_id");
        var shop_id = $(this).attr("shop_id");
        var that = $(this);
        util.ajax_json('/system/goods/getqrcode',function(d){
            if(d&&d.error==0){
                that.parent().parent().find('.code_imgs').attr("src",d.content.qrcode_img);
                that.parent().parent().find('.down_imgs').attr("href",d.content.qrcode_img);
                that.parent().parent().find("#fe_text").val(d.content.type_url);
                that.parent().parent().find('.share_span').show();
            }else{
                util.mobile_alert(d.message);
            }
        },{'goods_id':goods_id,'shop_id':shop_id});
    },function(){
        $(this).parent().parent().find('.share_span').hide();
    });
    $('.share_span').hover(function () {
        $(this).show();
    },function () {
        $(this).hide();
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
            layer.alert('<div style="text-align: center;">' + '确认要删除么' + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="del"]').val(_this.attr("goods_id"));
                $('input[name="shop_id"]').val(_this.attr("shop_id"));
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
                layer.alert('<div style="text-align: center;">' + '确认要删除么' + '</div>', {
                    title: '提示'
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

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")

