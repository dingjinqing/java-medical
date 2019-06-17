<#include ("system.header")

<#include ("system.article_nav")

<link rel="stylesheet" href="/js/kindeditor/themes/default/default.css"/>
<link rel="stylesheet" href="/js/kindeditor/plugins/code/prettify.css"/>
<link rel="stylesheet" href="/css/admin/goods_edit.css?v=1.0.9" type="text/css" />
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />
<style type="text/css">
    .aui_main{
        height:420px !important;
    }
</style>
<form enctype="multipart/form-data" action="${act_url!}" name="form1" id="form1" method="post"
      onsubmit="return on_submit();">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="article_id" id="article_id" value="${article->article_id!}">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            <table class="tab_body show table">

                <tr>
                    <td>标题：</td>
                    <td><input type="text" name="title" value="${article->title!}" size="64" class="article_list_height"><span
                                style="color:red;">*</span>
                    </td>
                </tr>
                {{--<tr>--!}
                    {{--<td>作者：</td>--!}
                    {{--<td><input type="text" name="author" value="${article->author!}" size="64"><span--!}
                                {{--style="color:red;">*</span>--!}
                    {{--</td>--!}
                {{--</tr>--!}
                <tr>
                    <td>文章分类：</td>
                    <td>
                        <select name="category_id" class="article_list_height">
                            <#list ($categories as $item)
                                <option value="${item->category_id!}"
                                        <#if ($article->category_id == $item->category_id) selected="selected" </#if>>${item->category_name!}
                                </option>
                            </#list>
                        </select>

                        <a href="/system/article/category"> 管理文章分类</a>
                    </td>
                </tr>
                <tr>
                    <td>是否推荐：</td>
                    <td><input type="checkbox" name="is_recommend" <#if  ($article->is_recommend == 1)checked </#if>
                        value="1">是否推荐
                    </td>
                </tr>
                {{--<tr>--!}
                    {{--<td>是否置顶：</td>--!}
                    {{--<td><input type="checkbox" name="is_top" <#if  ($article->is_top == 1)checked </#if> value="1">是否置顶--!}
                    {{--</td>--!}
                {{--</tr>--!}
                <tr>
                    <td>是否发布：</td>
                    <td><input type="checkbox" name="status" <#if  ($article->status == 1)checked </#if> value="1">是否发布
                    </td>
                </tr>
                {{--<tr>--!}
                    {{--<td>底部导航显示：</td>--!}
                    {{--<td>--!}
                        {{--<input type="checkbox" name="show_footer" <#if  ($article->show_footer == 1)checked--!}
                               {{--</#if> value="1">是否底部导航显示--!}
                        {{--<span class="text-warning">文章分类如果用于底部导航，则文章根据此条件做过滤显示</span>--!}
                    {{--</td>--!}
                {{--</tr>--!}
                <tr>
                    <td>关键词：</td>
                    <td><input type="text" name="keyword" value="${article->keyword!}" size="64"  class="article_list_height"><span
                                {{--style="color:red;">*</span>--!}
                    </td>
                </tr>
                <tr>
                    <td>摘要：</td>
                    <td><textarea id="desc" name="desc" rows="6" placeholder="摘要"
                                  style="width:480px">${article->desc!}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>文章封面：</td>
                    <td><ul class="goods-item-img clearfix">
                            <#if ($article->head_pic)
                                    <li>
                                        <input name="head_pic[]" type="hidden" value="${article->head_pic!}">
                                        <img src="${article->head_pic!}" class='' alt="" />
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" />
                                    </li>
                            </#if>
                            <li>
                                <input name="head_pic[]" type="hidden">
                                <img src="http://${image_domain!}/image/admin/add_img_bg.png" class="add_img" alt="" />
                                <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" style="display: none;" />
                            </li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>内容：</td>
                    <td>
                        <input type="hidden" id="content" name="content" value="${article->content!}">

                        <textarea id="editor" style="width:660px;height:400px;"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input type="submit" value="保存" class="primary">&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

            </table>

        </div>
    </div>
</form>
<script type="text/javascript" src="/js/system/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/system/jImageManager.js"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/js/kindeditor/lang/{{ config("app.locale")!}.js"></script>
<script type="text/javascript" src="/js/system/kindeditor-init.js"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script type="text/javascript">
    $('.goods-item-img').on('click','.good_img_delete',function(){
        $(this).parent().remove();
        hasSaved = false;
    });
    $('.add_img').click(function() {
        var img_number = $(".goods-item-img .good_img_delete").length;
        if(img_number >=2 ){
            util.mobile_alert('最多上传1张图！');
            return false;
        }
        var el = $(this).parent().clone();
        var obj = $(this).parent();
        var w = 290;
        var h = 220;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find("img").eq(0).attr("src", path);
                el.find("input").attr("value", path);
                hasSaved = false;
                el.removeClass('add_class');
                el.find("img").eq(1).show();
                obj.before(el);
            }
        });
    });

    var count=0;
    $('.ql_style').click(function () {
        count++;
        if(count%2!==0){
            $(this).find('img').css('display','block');
            $(this).css('border','1px solid #5a8bff');
        }else{
            $(this).find('img').css('display','none');
            $(this).css('border','1px solid #ddd');
        };
        if($(this).find('img').css('display','block')&& $(this).css('border','1px solid #5a8bff')){
            $(this).addClass('addd');
            $("#category").val($(this).children("p").eq(0).text());
            $(this).siblings().find('img').css('display','none');
            $(this).siblings().css('border','1px solid #ddd');
            $(this).siblings().removeClass('addd');
        };
        console.log(count);
    });

    $("#inputtext").on("input propertychange", function() {
        var $this = $(this),
            _val = $.trim($this.val()),
            count = "";
        console.log(_val);
        if (_val.length > 200) {
            $this.val(_val.substring(0, 200));
        }
        count = $.trim($this.val()).length;
        $(".change_number").text(count);
    });
    $(document).ready(function () {
        initKindEditor("#editor", function () {
            window.keditor.html($("#content").val());
        });
    });

    function on_submit() {
        window.keditor.sync();
        $("#content").val($('#editor').val());
        if ($('[name="title"]').val() == "") {
            art.dialog.tips("标题不能为空");
            $('[name="title"]').focus();
            return false;
        }

        return true;
    }

</script>
<#include ("system.footer")
