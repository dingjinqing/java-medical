<#include "/admin/header.ftl">
<link href="/js/jquery-ui-1.11.1/jquery-ui.css" rel="stylesheet">
<script language="JavaScript" src="/js/jquery-ui-1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/css/admin/video_manager.css?v=1.0.4" type="text/css"/>


<style>
    html, body {
        margin: 0;
        padding: 0;
        background: #fff;
        width:100%;
        overflow: hidden;
    }
</style>


<input type="hidden" name="video_max_num" id="video_max_num" value="${video_max_num or 1!}">
<input type="hidden" name="need_video_width" id="need_video_width" value="${need_video_width or 0!}">
<input type="hidden" name="need_video_height" id="need_video_height" value="${need_video_height or 0!}">
<input type="hidden" name="show_video_sort" id="show_video_sort" value="${show_video_sort or 1!}">

<input type="hidden" name="on_video_cb" id="on_video_cb" value="${on_video_cb!}">


<iframe class="video_iframe" <#if ($account) src="/admin/video/account/dialog?on_video_cb=on_video_cb&no_full=1&need_video_width=${need_video_width!}&need_video_height=${need_video_height!}" <#else>
src="/admin/frame/video/dialog?on_video_cb=on_video_cb&no_full=1&need_video_width=${need_video_width!}&need_video_height=${need_video_height!}"</#if>
        style="width:100%;height:420px;border:none;">
</iframe>

<div class="select-img-list"
     <#if  ($max_video_num == 1 || $max_video_num != 1 && $show_video_sort==0)style="display: none" </#if>>
    <p>已选<span class="img-num">0</span>张视频，拖动可修改插入顺序</p>
    <ul id="bigvideo_data" class="select-img-data clearfix">

    </ul>
</div>


<div class="template hide">
    <li p50 class="sel-img-template">
        <div class="box_img" align="center">
            <img class="img-thumbnail" src="">
        </div>

        <div class="box_desc">
            <span title=""></span>
        </div>
        <div class="video_mask">
            <p>
                <a class="old_pic" href="" target=_blank title="播放视频">播</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a class="remove_video" href="javascript:void(0);" title="移除视频">X</a>
            </p>
        </div>
        <div class="video_dim">
            <p style="text-align:center"></p>
        </div>
        <div class="video_sel">
        </div>
    </li>
</div>

<script language="JavaScript" src="/js/admin/select_video_dlg.js?v=1.0.5"></script>


<#include "/admin/footer.ftl">
