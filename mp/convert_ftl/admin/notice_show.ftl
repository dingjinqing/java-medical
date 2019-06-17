<#include "/admin/toggle_header.ftl">
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css">
<link href="/css/admin/notice_show.css?v=0.1.8" rel="stylesheet" type="text/css" />
<div class="notice_show_container">
    <div class="notice_container">
        <div class="notice_header">
			<span><a href="/admin/notice/list">通知中心</a> /</span>
			<span style="color: #666">版本迭代<img src="http://${image_domain!}/image/admin/expand.png" style="margin-left: 5px;"></span>
        </div>
        <div class="notice_content_container">
            <div class="notice_content">
                <div class="notice_content_header">
                    <div class="notice_title"><span style="text-align: center">${article->title!}</span></div>
                    <div class="notice_title_title">
                        <#if ($article->author)<div class="title_title"><span>作者:&nbsp;${article->author!} </span></div></#if>
                        {{--<div class="title_title"><span>${article->category_name!}</span></div>--!}
                        <div class="title_title"><span>${article->create_time!}</span></div>
                    </div>
                </div>
                <div class="notice_content_key" <#if (!$article->keyword && !$article->desc) style="display: none" </#if>>
                    <div class="content_key" <#if (!$article->keyword) style="display: none" </#if>><span>关键词:</span><span>${article->keyword!}</span></div>
                    <div class="content_key" <#if (!$article->desc) style="display: none" </#if>><div class="zy">摘要:</div><div class="zy_text">${article->desc!}</div></div>
                </div>
                <div class="notice_content_text">
                    {{--<div class="content_text">--!}
                        {{--<div class="content_text_header"><h4>1.小程序新增砍价功能</h4></div>--!}
                        {{--<div class="content_text_text"><span>此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明此处为详细说明</span></div>--!}
                        {{--<div class="content_text_image"><img src="http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg"></div>--!}
                    {{--</div>--!}
                    {!! $article->content !!}
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

</script>