<link rel="stylesheet" href="/css/admin/page_not_found.css?v=1.0.1">
<div class="all_not">
    <img src="http://${image_domain!}/image/admin/page_404.png" alt="" class="not_imgs">
    <div class="not_tips">SORRY!您访问的页面找不到了，请重新加载</div>
    <div class="click_go clearfix">
        <a href="javascript:location.reload();" class="load_again">
            <img src="http://${image_domain!}/image/admin/load_again.png" alt="">
            重新加载
        </a>
        <a href="${home_redirect!}" class="go_index" <#if  ($home_redirect == '/system/index') target="_blank" </#if>>
            <img src="http://${image_domain!}/image/admin/to_index.png" alt="">
            返回首页
        </a>
    </div>
</div>