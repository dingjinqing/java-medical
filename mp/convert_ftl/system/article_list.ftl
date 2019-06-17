<#include ("system.header")
<#include ("system.article_nav")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.1" type="text/css" >
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />
<style>

    .ctrl-img{
        position: relative;
    }
    .ctrl-img img{
        width: 50px;
        height: 48px;
        border-radius: 2px;
        border: 1px solid #ccc;
        margin: 2px 5px 0 0;
    }
    /*预览*/
    .comm_back{
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        left: 0;
        background: rgba(0,0,0,0.7);
        display: none;
    }
    .close_comm{
        position: fixed;
        top: 10px;
        right: 20px;
        display: none;
        z-index: 301;
        cursor: pointer;
    }
    #myCarousel img{
        margin: 0 auto;
    }
    .comm_all{
        width: 100%;
        position: absolute;
        top: 100px;
        left: 0;
        z-index: 300;
    }
    #main-table th {
        width: 188px;
    }
</style>
<form action="/system/article/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="status" id="status" value="">
    <input type="hidden" name="page" value="">
    <input type="hidden" name="id" id="id" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            <label>{{ trans("system/article.category.article_category")!}：</label>
            <select name="category_id" class="article_list_height">
                <option value="0">{{ trans("system/article.list.select_category")!}</option>
                <#list ($categories as $item)
                    <option value="${item->category_id!}"
                            <#if ($category_id == $item->category_id) selected="selected"</#if>>${item->category_name!}
                    </option>
                </#list>
            </select>
            <input type="text" name="keywords" value="${keywords!}" class="article_list_height">
            <input type="submit" name="search" value="{{ trans("system/common.operation.search")!}" class="article_list_height">
        </div>
    </div>
    <div class="box panel main-table article-main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>{{ trans("system/article.list.title")!}</th>
                    <th>分类</th>
                    <th>头图</th>
                    <th>更新时间</th>
                    <th>状态</th>
                    <th>是否推荐</th>
                    <th width="210px">操作</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        <td style="min-width:100px;">
                            ${item->title!}
                        </td>
                        <td>
                            ${item->category_name!}
                        </td>

                        <td class="ctrl-img">
                            <#if ($item->head_pic)<img src="${item->head_pic!}" class="click_img"/><#else> 无 </#if>
                        </td>
                        <td>${item->update_time!}</td>
                        <td><#if ($item->status==1)<span class="label label-success">已发布</span> <#else> 未发布 </#if></td>
                        <td><#if ($item->is_recommend==1)<span class="label label-success">是</span> <#else> 否 </#if></td>
                        <td>
                            <a href="/system/article/edit?article_id=${item->article_id!}">编辑</a>&nbsp;&nbsp;
                            <#if ($item->status==0)
                                <a href="javascript:void(0);"
                                   onclick="up_article('${item->article_id!}',1);">重新发布</a>
                            <#else>
                                <a href="javascript:void(0);"
                                   onclick="up_article('${item->article_id!}',0);">标记为未发布</a>
                            </#if>

                            <a id="remove_article" href="javascript:void(0);"
                               onclick="remove_article('${item->article_id!}');">删除</a>
                        </td>
                    </tr>
                </#list>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table article-bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                    <a href="#"
                                       onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">
                                        {{ trans("system/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text"
                                           value="${data_list->currentPage()!}" size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>

</form>
<div class="comm_back"></div>
<img src="http://${image_domain!}/image/wxapp/close_icon.png" class="close_comm" />
<div class="comm_all">
    <div id="myCarousel" class="carousel slide">
        <div class="carousel-inner">
        </div>
        <a class="carousel-control left" href="#myCarousel" data-slide="prev"></a>
        <a class="carousel-control right" href="#myCarousel" data-slide="next"></a>
    </div>
</div>


<script>
    // $('.carousel').carousel('pause');//禁止轮播 自动轮播
    $('.ctrl-img').on('click','.click_img',function(){
        $('.comm_back').show();
        $('.close_comm').show();
        $('.comm_all').css('padding-bottom','30px');
        var img_src = [];
        var img_index = $(this).index();
        $.each($(this).parent().find('img'),function(){  //获取当前评论的图片路径
            img_src.push($(this).attr('src'));
        });
        var item_html = '';
        $.each(img_src,function(k,v){   //将路径赋给轮播
            if(img_index == k){
                item_html += '<div class="item active">';
                item_html += '<img src="' + v + '" alt="' + k + ' slide">';
                item_html += '</div>';
            }else{
                item_html += '<div class="item">';
                item_html += '<img src="' + v + '" alt="' + k + ' slide">';
                item_html += '</div>';
            }
        });
        $('.carousel-inner').append(item_html);
        $('.comm_back,.close_comm,.carousel-inner').click(function(){
            $('.comm_back').hide();
            $('.close_comm').hide();
            $('.comm_all').css('padding-bottom','0px');
            item_html = '';
            $('.carousel-inner').html('');
        });
    });

    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    function remove_article(id) {
        if (confirm("确定删除此文章吗？")) {
            $("#act").val('del');
            $("#id").val(id);
            $("#form1").submit();
        }
    }

    function up_article(id,status) {
        var ret;
        if(status == '1'){
            ret = confirm("确定发布此文章吗？");
        }else{
            ret = confirm("确定把文章标记为未发布吗？");
        }
        if (ret) {
            $("#act").val('up');
            $("#status").val(status);
            $("#id").val(id);
            $("#form1").submit();
        }
    }

    $('[name="category_id"]').change(function () {
        $("#form1").submit();
    });
    // $("#main-table").FixedHead({tableLayout: "fixed"});
</script>

<#include ("system.footer")
