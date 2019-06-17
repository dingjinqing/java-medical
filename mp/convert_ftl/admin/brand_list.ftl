<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/brand_related.css?v=1.0.0" type="text/css" />
<style>
    /*预览*/
    .comm_back{
        width: 100%;
        height: 100%;
        background: rgba(0,0,0,0.7);
        display: none;
        position: absolute;
        left: 0;
        top: 0;
    }
    .comm_back .item{
        display: none;
    }
    .comm_back .item img{
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
    }
    .close_comm{
        position: fixed;
        top: 90px;
        right: 20px;
        display: none;
        z-index: 301;
        cursor: pointer;
    }
    .btn_add {
         color: #fff !important;
    }
</style>
<div class="title">
    <span><a href="/admin/goods/manage/list?top_index=2">商品管理</a> / </span><span>品牌管理</span>
</div>
<div class="main_container">
    <form action="/admin/goods/brand/list" method="POST" id="form1">
        {{ csrf_field()!}
        <input type="hidden" name="del">
        <input type="hidden" name="sort_field" id="sort_field" value="${sort_field!}">
        <input type="hidden" name="sort_way" id="sort_way" value="${sort_way!}">
    <div class="brand_top">
        <ul class="switch_box clearfix">
            <li class="active"><a href="/admin/goods/brand/list?top_index=2">全部品牌</a></li>
            <li><a href="/admin/goods/brand/classifylist?top_index=2">品牌分类</a></li>
            <li><a href="/admin/goods/brand/showbrandcfg?top_index=2">品牌展示设置</a></li>
        </ul>
        <ul class="search_box clearfix">
            <li>
                品牌名称：<input type="text" name='keywords' value="${keywords!}">
            </li>
            <li>
                创建时间：<input class="list-center-sel" style="float: none" type="text" name="startDate" placeholder="请选择时间" id="startDate" value="${startDate!}"
                            onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/> —
                <input class="list-center-sel" style="float: none;margin-left: 0" type="text" name="endDate" placeholder="请选择时间" value="${endDate!}" id="endDate"
                       onclick="picker();"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
            </li>
            <li>
                品牌分类：
                <select name="classify_id" id="">
                    <option value="0">请选择</option>
                    <#list ($classify_list as $item)
                            <option value="${item->classify_id!}"
                                    <#if ($classify_id == $item->classify_id)selected="selected"</#if>>${item->classify_name!}</option>
                    </#list>
                </select>
            </li>
            <li>
                是否为推荐品牌：
                <select name="is_recommend" id="">
                    <option value="-1">请选择</option>
                    <option value="1" <#if  ($is_recommend ==1) selected </#if>>是</option>
                    <option value="0" <#if  ($is_recommend ==0) selected </#if>>否</option>
                </select>
            </li>
        </ul>
        <div class="button_box clearfix">
            <a class="fl btn_add" href="/admin/goods/brand/addBrand?top_index=2" >添加品牌</a>
            <button class="fr btn_search">筛选</button>
        </div>
    </div>
    <div class="brand_bottom">
        <div class="info_table">
            <table width="100%">
                <thead>
                    <tr>
                        <th width="30%"> <a href="javascript:void(0);" onClick="return sort_f(1);">品牌名称</a> <span id="sort_symbo">${sort_symbo[1]!}</span></th>
                        <th width="10%">品牌logo</th>
                        <th width="10%">优先级</th>
                        <th width="10%">品牌分类</th>
                        <th width="10%">包含商品数量</th>
                        <th width="10%">推荐品牌</th>
                        <th width="15%">创建时间</th>
                        <th width="15%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <#list ($data_list as $item)
                    <tr>
                        <td>${item->brand_name!}</td>
                        <td class="ctrl-img"><img src="${item->logo!}" alt="" class="click_img" style="width:50px;"></td>
                        <td>${item->first!}</td>
                        <td>${item->classify_name!}</td>
                        <#if ($item->goods_num>0)
                        <td><a href="/admin/goods/manage/list?top_index=2&bra_id=${item->id!}">${item->goods_num!}</a></td><#else>
                            <td><a href="/admin/goods/brand/list?top_index=2">${item->goods_num!}</a></td>
                        </#if>
                        <td><#if ($item->is_recommend==1)是 <#else> 否 </#if></td>
                        <td>${item->add_time!}</td>
                        <td>
                            <a href="/admin/goods/brand/editBrand?top_index=2&brand_id=${item->id!}">编辑</a>
                            <a href="javascript:;" class="del" brand_id="${item->id!}">删除</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="clearfix jump_page">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </div>
    </form>
</div>
<div class="comm_back">
    <img src="http://${image_domain!}/image/wxapp/close_icon.png" class="close_comm" />
    <div class="item">

    </div>
</div>
<#include "/admin/footer.ftl">
<script>
$(document).on('click','button',function(e){
    e.preventDefault();
})
function picker() {
    return WdatePicker({dateFmt: 'yyyy-MM-dd'});
}
$('.del').click(function(){
    var brand_id = $(this).attr('brand_id');
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.alert('<div style="text-align: center;">确认要删除该品牌吗？</div>', {
            title: ['提醒', 'text-align:center;padding: 0px;']
            , area: '260px'
            , closeBtn: 0
            , btn: ['确定', '取消']
        },function(index){
            util.ajax_json('/admin/goods/brand/list',function (res) {
                if(res.error == 0){
                    layer.msg('删除品牌成功', {time: 500},function () {
                        window.location.reload();
                    });
                }else{
                    util.mobile_alert(res.message);
                    layer.close(index);
                }
            },{del:brand_id});
        });
    });
});
$('.ctrl-img').on('click','.click_img',function(){
    $('.comm_back').show();
    $('.close_comm').show();
    $('.item').show();
    // $('.comm_all').css('padding-bottom','30px');
    $('.item').html('');
    var img_src = $(this).parent().find('img').attr('src');
    var item_html = `<img src="${img_src}" />`;
    $(item_html).appendTo($('.item'));
    $('.comm_back,.close_comm,.carousel-inner').click(function(){
        $('.comm_back').hide();
        $('.close_comm').hide();
        $('.item').hide();
        item_html = '';
    });
});
window.sort_f = function (field) {
    if ($("#sort_field").val() == field) {
        if ($("#sort_way").val() == 'desc') {
            $("#sort_way").val('asc');
        } else {
            $("#sort_way").val('desc');
        }
    } else {
        $("#sort_field").val(field);
        $("#sort_way").val('asc');
    }
    $("#form1").submit();
};
$('.btn_search').click(function(){
    $('#form1').submit();
})
$('[name="classify_id"]').change(function(){
    $("#form1").submit();
});
$('[name="is_recommend"]').change(function(){
    $("#form1").submit();
});
</script>