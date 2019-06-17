<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sort_manage.css?v=1.0.10" type="text/css" />
<style type="text/css">
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
        width: 110px;
        text-align: center;
    }
    .change_div{
        width: 100%;
        position: absolute;
        bottom:0;
        background: rgba(0,0,0,0.5);
        color: #fff;
        padding:5px 0;
        font-size: 12px;
        text-align: center;
        /*display: none;*/
    }
    .choose_imfdd .title_mgadsa{
        width: 230px;
    }
    .title_mgadsa img{
        max-width: 100%;
        max-height:100%;
    }
    .category_list>table td:first-of-type {
        padding-left: 20px;
        text-align: left;
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
        width: 12%;
    }
    .category_list>table th:nth-child(3) {
        width: 20%;
    }
    .category_list>table th:nth-child(4) {
        width: 10%;
    }
</style>
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">商家分类管理</span>
</div>
<div class="sort_container">
    <form action="" method="post" id="form1">
        {{csrf_field()!}
        <input type="hidden" name="type" value="1">
        <input type="hidden" name="act">
        <div class="sort_navlist">
            <ul class="sort_nav clearfix">
                <li class="sort_list" >
                    <a href="/admin/goods/sort/list?top_index=2&type=0">分类列表</a>
                </li>
                <li class="reco_sort_list sort_avtives" >
                    <a href="/admin/goods/sort/list?top_index=2&type=1">推荐分类</a>
                </li>
                <li class="add_sort " hidden>
                    <a href="/admin/goods/sort/add?top_index=2" >添加分类</a>
                </li>
                <li class="add_reco_sort" hidden>
                    <a href="/admin/goods/sort/add?top_index=2&type=1" >添加推荐分类</a>
                </li>
            </ul>

            <div class="reco_list_content">
                <ul>
                    <li class="detail_lis clearfix">
                        <div class="item_title"><span style="color: red;"></span>是否启用推荐分类：</div>
                        <div class="radio_open">
                            <input type="radio" name="recomment_sort_status" <#if ($cfg['recomment_sort_status'] == 1) checked </#if> value="1">启用
                            <input type="radio" name="recomment_sort_status" <#if ($cfg['recomment_sort_status'] == 0) checked </#if> value="0">关闭
                        </div>
                    </li>
                    <li class="detail_lis clearfix ">
                        <div class="item_title"></div>
                        <div class="tips_sort">限制小程序前端分类列表是否展示推荐分类</div>
                    </li>
                    <input type="hidden" name="recomment_sort_img" value="${cfg['recomment_sort_img']!}">
                    <li class="detail_lis clearfix sort_title_img choose_imfdd">
                        <div class="item_title" style="line-height: inherit"><span style="color: red;"></span>分类头图：</div>
                        <div class="title_mgadsa">
                            <img <#if ($cfg['recomment_sort_img']) src="${cfg['recomment_sort_img']!}" <#else>
                            src="http://${image_domain!}/image/admin/add_simple.png" </#if> alt="" class="sele_img">
                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_this">
                            <div class="change_div">更换图片</div>
                        </div>
                    </li>
                    <li class="detail_lis clearfix ">
                        <div class="item_title"></div>
                        <div class="tips_sort">显示在分类页顶部，不填写则不显示，建议尺寸510*200</div>
                    </li>
                    <li class="detail_lis clearfix ">
                        <div class="item_title"></div>
                        <div class="add_path_list clearfix">
                            <div>头图链接：</div>
                            <input type="text" class="path_input" name="recomment_img_link" value="${cfg['recomment_img_link']!}" />
                            <a href="##" class="add_path">添加链接</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="btn_list">
                <a href="/admin/goods/sort/add?top_index=2&type=1" target="_blank">添加推荐分类</a>
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
                    <tbody>
                    <#if ($data_list)
                        <#list ($data_list as $item)
                        <tr parent_id="${item['parent_id']!}" <#if ($item['parent_id'] != -1) class="disnone ${item['parent_id']!}" </#if>>
                            <td sort_id="${item['sort_id']!}"><img hidden src="http://${image_domain!}/image/admin/sort_add.png">${item['sort_name']!}(${item['goodsNum']!})</td>
                            <td class="ctrl-img"><img src="${item['sort_img']!}" alt="" class="click_img" style="width:50px;"></td>
                            <td>${item['img_link']!}</td>
                            <td>${item['first']!}</td>
                            <td>${item['create_time']!}</td>
                            <td><a href="/admin/goods/sort/edit?sort_id=${item['sort_id']!}" target="_blank" <#if ($item['parent_id'] != -1) hidden </#if>>编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="del" sort_id="${item['sort_id']!}" parent_id="${item['parent_id']!}" has_child="${item['has_child']!}">删除</a></td>
                        </tr>
                        </#list>
                    </#if>
                    </tbody>
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
        </div>
    </form>
    <div class="pages_bottom">
        <a href="##" class="btn_to_save">保存</a>
    </div>
</div>
<div class="comm_back">
    <img src="http://${image_domain!}/image/wxapp/close_icon.png" class="close_comm" />
    <div class="item">
        
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript">
    if($(".sele_img").attr('src').indexOf('add_simple')>-1){
        $(".del_this").css("display","none");
        $('.title_mgadsa .change_div').css("display", 'none');
    }else{
        $(".del_this").css("display","block");
        $('.title_mgadsa .change_div').css("display", 'block');
    }
    //分类头图
    $('.title_mgadsa .sele_img').click(function() {
        var el = $(this).parent();
        var w = 510;
        var h = 200;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find(".del_this").css("display", 'block');
                el.find(".change_div").css("display", 'block');
                el.find(".sele_img").css("width", '240px');
                el.find(".sele_img").css("heihgt", '90px');
                el.find(".sele_img").attr("src", path);
                $("input[name='recomment_sort_img']").val(path);
            }
        });
    });
    $(document).on("click",".title_mgadsa .del_this",function () {
        $('.title_mgadsa .sele_img').attr("src",'/image/admin/add_simple.png');
        $('.title_mgadsa .del_this').css("display", 'none');
        $('.title_mgadsa .change_div').css("display", 'none');
        $("input[name='recomment_sort_img']").val("");
    });

    {{--$("tbody img").click(function(){--!}
        {{--if($(this).attr("src")=="http://${image_domain!}/image/admin/sort_add.png"){--!}
            {{--$(this).attr("src","http://${image_domain!}/image/admin/sort_minus.png");--!}
        {{--}else{--!}
            {{--$(this).attr("src","http://${image_domain!}/image/admin/sort_add.png");--!}
        {{--}--!}
        {{--var sort_id = $(this).parent().attr('sort_id');--!}
        {{--$(this).parent().parent().parent().find('.'+sort_id).toggle();--!}
    {{--});--!}
    $("tbody tr img").after("&nbsp;");
    $('.btn_to_save').click(function () {
        $('input[name="act"]').val('cfg');
        layer.ready(function () {
            layer.msg('保存成功', {time: 2000},function () {
                $("#form1").submit();
            });
        });
    });
    //添加链接
    $('.add_path_list').on('click','.add_path',function () {
        var ipt_link = $(this).prev();
        var _page = $(this).prev().val();
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择链接', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['800px','430px']
                , content: '/admin/frame/decoration/link?bottom=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    var iframe = layer.getChildFrame('body', index);
                    var link = iframe.contents().find('tr[data-back="false"]').find(".link").text();
                    var appid = iframe.contents().find('tr[data-back="false"]').find(".appid").text();
                    ipt_link.val(link);
                    ipt_link.attr('data-appid',appid);
                    layer.close(index);
                }
            });
        });
    });
    $('.del').on('click', function () {
        var _this = $(this);
        if(_this.attr('parent_id')==-1 && _this.attr("has_child")>0){
            var title = '删除一级分类，会删除该分类下的所有子类，确定删除吗？';
        }else{
            var title = '确定要删除该分类吗？';
        }
        layer.open({
            type: 1,
            title: ['提醒', 'text-align:center;padding: 0px;'],
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
<script type="text/javascript">
    getPowerInfo('main_config','sort','sub_1','商家分类',0);
</script>
