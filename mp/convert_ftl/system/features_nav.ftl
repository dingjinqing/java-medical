<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/statistics/view">功能数据统计</a></li>
    <#if ($nav_type==1)
    <li class="active" ><a href="#" data-toggle="tab" url="/system/statistics/activity">按${activity[$activity_type]!}活动数据统计</a></li>
    </#if>
    <li <#if ($nav_type==2)class="active"</#if>><a href="#" data-toggle="tab" url="/system/statistics/shop">按店铺数据统计</a></li>
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

