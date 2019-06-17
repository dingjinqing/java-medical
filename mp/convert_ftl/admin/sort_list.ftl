<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sort_list_manage.css?v=1.1.0" type="text/css" />
<style type="text/css">
    .category_list{
        min-height:400px !important;
    }
    .btn_list{
        background: #fff;
        height:30px;
    }
    .btn_list a{
        background-color: #5a8bff;
        border: 1px solid #5A8BFF;
        color: #fff;
        font-size: 14px;
        margin-left: 21px;
        height:30px;
        line-height:30px;
        display: block;
        width: 90px;
        text-align: center;
    }
    .sort_nav{
        margin-bottom: 0;
    }
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
    .category_list>table th:nth-child(1) {
        width: 20%;
    }
    .category_list>table td:first-of-type {
        padding-left: 10px;
        text-align: left;
    }
    .category_list>table .disnone td:first-of-type {
        padding-left: 20px;
    }
    .category_list>table th:nth-child(3) {
        width: 25%;
    }
    .category_list>table th:nth-child(4) {
        width: 10%;
    }
    .category_list>table .disnone td:first-of-type {
        padding-left: 35px;
    }
</style>
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">商家分类管理</span>
</div>
<div class="sort_container">
    <form action="" method="post" id="form1">
        {{csrf_field()!}
        <input type="hidden" name="del">
        <div class="sort_navlist">
            <ul class="sort_nav clearfix">
                <li class="sort_list sort_avtives" >
                    <a href="/admin/goods/sort/list?top_index=2&type=0">分类列表</a>
                </li>
                <li class="reco_sort_list" >
                    <a href="/admin/goods/sort/list?top_index=2&type=1">推荐分类</a>
                </li>
                <li class="add_sort" hidden >
                    <a href="/admin/goods/sort/add?top_index=2&type=0" >添加分类</a>
                </li>
                <li class="add_reco_sort" hidden >
                    <a href="/admin/goods/sort/add?top_index=2&type=1" >添加推荐分类</a>
                </li>
            </ul>
        </div>
        <div class="btn_list">
            <a href="/admin/goods/sort/add?top_index=2&type=0" target="_blank">添加分类</a>
        </div>
        <div class="category_list">
            <table>
                <thead>
                <tr>
                    <th>分类名称</th>
                    <th>分类图标</th>
                    <th>分类链接</th>
                    <th>分类优先级</th>
                    <th>添加时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody class="sort_list">
                <#if ($data_list)
                    <#list ($data_list as $item)
                    <tr parent_id="${item['parent_id']!}" <#if ($item['parent_id'] != 0) class="disnone ${item['parent_id']!}" </#if>>
                        <td sort_id="${item['sort_id']!}">
                            <img src="http://${image_domain!}/image/admin/sort_add.png" <#if ($item['parent_id'] != 0 || $item['has_child'] == 0) hidden </#if> style="cursor: pointer;float:left;margin-top:4px">
                            <span <#if ($item['parent_id'] != 0 || $item['has_child'] == 0) style="display: inline-block;width: 9px" </#if> ></span>
                            <span  style="<#if ($item['has_child'] == 0) margin-left:12px;float:left; <#else> display: inherit; </#if> word-break:break-all;">{{str_replace('&nbsp;','',$item['sort_name'])!}(${item['goodsNum']!})</span>
                        </td>
                        <td class="ctrl-img"><img src="${item['sort_img']!}" alt="" class="click_img" style="height:50px;"></td>
                        <td>${item['img_link']!}</td>
                        <td>${item['first']!}</td>
                        <td>${item['create_time']!}</td>
                        <td><a href="/admin/goods/sort/edit?sort_id=${item['sort_id']!}" target="_blank">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="del" sort_id="${item['sort_id']!}" parent_id="${item['parent_id']!}" has_child="${item['has_child']!}">删除</a></td>
                    </tr>
                    </#list>
                </#if>
            </table>
            <#if (!$data_list)
                <div class="no_data_style" style="width: 99%;border: 1px solid #eee;height: 100px;margin-top: 10px;margin-left: 10px;">
                    <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                        <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                    </div>
                    <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                </div>
            </#if>
        </div>
    </form>
</div>
<div class="comm_back">
    <img src="http://${image_domain!}/image/wxapp/close_icon.png" class="close_comm" />
    <div class="item">
        
    </div>
</div>
<script >
    $(function(){
       // $("tbody tr td:nth-child(4)").append("");
       $('tbody tr').each(function(){
           $(this).find('td:eq(0) img').click(function(){
            if($(this).attr("src")=="http://${image_domain!}/image/admin/sort_add.png"){
                $(this).attr("src","http://${image_domain!}/image/admin/sort_minus.png");
            }else{
                $(this).attr("src","http://${image_domain!}/image/admin/sort_add.png");
            }
            var sort_id = $(this).parent().attr('sort_id');
            $(this).parent().parent().parent().find('.'+sort_id).toggle();
           })
        //    $(this).find('td:eq(1)').click(function(){

        //    })
       });
    //    $("tbody tr img").after("&nbsp;");
    })
    $('.del').on('click', function () {
        var _this = $(this);
        if(_this.attr('parent_id')==0 && _this.attr("has_child")>0){
            var title = '删除一级分类，会删除该分类下的所有二级分类，确定删除吗？';
        }else{
            var title = '确定要删除该分类吗？';
        }
        layer.open({
            type: 1,
            title: ['提示', 'text-align:center;padding: 0px;'],
            content:'<div style="text-align: left;padding: 15px 15px">' + title + '</div>',
            area: "300px",
            btn: ['确定', '取消'],
            yes: function (index) {
                var param = {
                    del : _this.attr('sort_id'),
                };
                util.ajax_json("/admin/goods/sort/list", function (response) {
                    if(response.error == 0){
                        util.mobile_alert(response.message);
                        location.reload();
                    }else{
                        util.mobile_alert(response.message);
                    }
                }, param);
                layer.close(index);
            }
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
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','sort','sub_1','商家分类',0);
</script>