<#include "/admin/toggle_header.ftl">
<link rel="stylesheet" href="/css/admin/notice_list.css?v=1.0.2" type="text/css"/>
<style>
    .mian-ad{
        margin-top: 10px;
    }
    .paging_footer{
        height: auto !important;
    }
</style>
<form class="list_show_container" method="post" id="form1" name="form1">
    {{ csrf_field()!}
    <div class="list_show_content">
        <div class="show_header">
            <span>通知中心<img src="http://${image_domain!}/image/admin/expand.png" style="margin-left: 5px;"></span>
        </div>
        <div action="" id="form1">
            <div class="show_text">
                <div class="text_container">
                    <#list ($data_list as $item)
                        <div class="bbtz">
                            <div class="bbtz_text">
                                <div class="bbtz_text_header">
                                    <span  <#if (!in_array($item->article_id,$article_id)) class="red_radio" </#if>></span>
                                    <span class="bbtz_text_header_text">${item->title!}</span>
                                </div>
                                <div class="bbtz_text_text"><span>${item->desc!}</span></div>
                            </div>
                            <div class="bbtz_watch">
                                <div class="bbtz_watch_time"><span>${item->create_time!}</span></div>
                                <div class="bbtz_watch_click">
                                    <div class="bbtz_watch_click_see"><a href="/admin/notice/show?article_id=${item->article_id!}">查看详情</a></div>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
                <div class="paging_footer">
                  <#include "/admin/jump_page_admin.ftl">
                </div>
                <div class="mian-ad">
                    <a href="##"><img src="http://${image_domain!}/image/admin/ad_img.png" /></a>
                </div>
            </div>
        </div>
    </div>
</form>

<script>

</script>
