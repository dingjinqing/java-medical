<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/brand_related.css?v=1.0.0" type="text/css" />
<div class="title">
    <span><a href="/admin/goods/manage/list?top_index=2">商品管理</a> / </span><span>品牌管理</span>
</div>
<div class="main_container">
    <form action="" method="POST" id="form1">
        {{ csrf_field()!}
        <div class="brand_top">
            <ul class="switch_box clearfix">
                <li><a href="/admin/goods/brand/list?top_index=2">全部品牌</a></li>
                <li class="active"><a href="/admin/goods/brand/classifylist?top_index=2">品牌分类</a></li>
                <li><a href="/admin/goods/brand/showbrandcfg?top_index=2">品牌展示设置</a></li>
            </ul>
            <ul class="search_box clearfix">
                <li>
                    分类名称：<input type="text" name='keywords' value="${keywords!}">
                </li>
                <li>
                    创建时间：<input class="list-center-sel" style="float: none" type="text" name="startDate" placeholder="请选择时间" id="startDate" value="${startDate!}"
                                onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/> —
                    <input class="list-center-sel" style="float: none;margin-left: 0" type="text" name="endDate" placeholder="请选择时间" value="${endDate!}" id="endDate"
                           onclick="picker();"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                </li>
                <li>
                    <button class="btn_search">筛选</button>
                </li>
            </ul>
            <div class="button_box clearfix">
                <button class="fl btn_add">添加品牌分类</button>
            </div>
        </div>
        <div class="brand_bottom">
            <div class="info_table">
                <table width="100%">
                    <thead>
                        <tr>
                            <th width="30%">分类名称</th>
                            <th>包含品牌数量</th>
                            <th>分类优先级</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list ($data_list as $item)
                        <tr>
                            <td class="classify_name"><a href="/admin/goods/brand/list?top_index=2&&cla_id=${item->classify_id!}">${item->classify_name!}</a></td>
                            <td>${item->goods_num!}</td>
                            <td class="classify_priority">${item->first!}</td>
                            <td>${item->create_time!}</td>
                            <td>
                                <a href="javascript:;" brand_class_id="${item->classify_id!}" class="add_brand">添加品牌</a>
                                <a href="javascript:;" brand_calss_id="${item->classify_id!}" class="edit">编辑</a>
                                <a href="javascript:;" brand_class_id="${item->classify_id!}" class="del">删除</a>
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
<#include "/admin/footer.ftl">
<script>
$(document).on('click','button',function(e){
    e.preventDefault();
})
$('.btn_add,.edit').click(function(){
    var title = '',classify_name = '',classify_priority='',classify_id='';
    if($(this).attr('class') == "edit"){
        title = '修改品牌分类'
        classify_name = $(this).parents('tr').find('.classify_name').text();
        classify_priority = $(this).parents('tr').find('.classify_priority').text();
        classify_id = $(this).attr('brand_calss_id');
    } else {
        title = '添加品牌分类'
    }
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.alert(`<div class="add_class">
                     <p>品牌分类名称：<input type="text" name="add_class" value="${classify_name}"></p>
                     <p>分类优先级：<input type="text" name="add_first" style="margin-left:14px" value="${classify_priority}"></p>
                     <p style="margin-left:98px;color:#999">请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前</p>
                     </div>
                    `
            , {
            title: [title, 'text-align:center;padding: 0px;']
            , area: ['400px','265px']
            , closeBtn: 0
            , btnAlign: 'c'
            , btn: ['确定', '取消']
        },function(index){
            let add_class = $('input[name="add_class"]').val()
            let add_first = $('input[name="add_first"]').val()
            util.ajax_json('/admin/goods/brand/classifylist',function (res) {
                if(res.error == 0){
                    layer.msg(res.message, {time: 500},function () {
                        window.location.reload();
                    });
                }else{
                    util.mobile_alert(res.message)
                }
            },{add_cla:add_class,add_first:add_first,classify_id:classify_id});
        });
    });
})
$('.del').click(function(){
    var brand_class_id = $(this).attr('brand_class_id');
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.alert('<div style="text-align: center;">确认要删除该品牌分类吗？</div>', {
            title: ['提醒', 'text-align:center;padding: 0px;']
            , area: '260px'
            , closeBtn: 0
            , btn: ['确定', '取消']
        },function(index){
            util.ajax_json('/admin/goods/brand/classifylist',function (res) {
                if(res.error == 0){
                    layer.msg('删除品牌分类成功', {time: 500},function () {
                        window.location.reload();
                    });
                }else{
                    util.mobile_alert(res.message);
                    layer.close(index);
                }
            },{del:brand_class_id});
        });
    });
});
$('.add_brand').click(function(){
    console.log(1);
    let classify_id = $(this).attr('brand_class_id');
    layui.use('layer', function(){
        var $ = layui.jquery, layer = layui.layer;
        var url = '/admin/goods/brand/selectBrand?not_classify_id='+classify_id ;
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
                var brand_ids = body.find('#record_select_value').val();
                console.log(brand_ids);
                util.ajax_json('/admin/goods/brand/selectBrand',function (res) {
                    if(res.error == 0){
                        layer.msg('添加品牌成功', {time: 1000},function () {
                            window.location.reload();
                        });
                    }else{

                        util.mobile_alert(res.message)
                    }
                },{brand_ids:brand_ids,classify_id:classify_id,});
            }
        })
    })
})

function picker() {
    return WdatePicker({dateFmt: 'yyyy-MM-dd'});
}
$('.btn_search').click(function(){
    $('#form1').submit();
})
</script>