<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/article/list">文章列表</a></li>
    <li <#if ($nav_type==1)class="active"</#if>><a href="#" data-toggle="tab" url="/system/article/category">文章分类</a></li>
    <li <#if ($nav_type==2)class="active"</#if>><a href="#" data-toggle="tab" url="/system/article/add">文章发布</a></li>
    <#if ($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="#">文章编辑</a></li>
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
