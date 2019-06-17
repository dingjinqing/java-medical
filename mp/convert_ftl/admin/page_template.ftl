
<link rel="stylesheet" href="/css/admin/page_template.css?v=1.0.2" type="text/css">

<div class="template_view clearfix">
	<div class="clearfix">
		<div class="template_li template_li_custom" style="cursor: pointer;">
			<div class="template_back">
				<a href="/admin/manage/decorate/page" target="_blank">自定义</a>
			</div>
				<img src="http://${image_domain!}/image/admin/shop_beautify/shop_decorate_module1.jpg" alt="" width="150px" />
				<p>自定义模板</p>
		</div>
		<#list ($template as $item)
			<div class="template_li template_li_custom">
				<div class="template_back">
					<a href="/admin/manage/decorate/page?template_id=${item->page_id!}" target="_blank">使用模板</a>
				</div>
				<img src="${item->page_img!}" alt="" width="150px" />
				<p>${item->page_name!}</p>

			</div>
		</#list>
	</div>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	$('.template_li_custom').hover(function () {
		$(this).find('.template_back').show();
    },function () {
        $(this).find('.template_back').hide();
    })
</script>