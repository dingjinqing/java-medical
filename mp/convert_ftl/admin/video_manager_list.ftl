<#include "/admin/header.ftl">

<link rel="stylesheet" href="/css/admin/video_manager.css?v=1.1.31" type="text/css"/>
<link rel="stylesheet" href="/js/Jcrop/css/jquery.Jcrop.css" type="text/css"/>
<link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css?v=1.2.3"/>
<style type="text/css">
    .btn_sech {
        background: #f5f5f5;
        color: #666;
        border-color: #eee;
        height: 30px;
        line-height: 25px;
    }

    .btn_sech:hover {
        background-color: #f5f5f5 !important;
        color: #666;
        border-color: #eee !important;
        text-decoration: none;
    }

    .btn_sech:focus {
        background-color: #f5f5f5 !important;
        color: #666;
        border-color: #eee !important;
        text-decoration: none;
    }

    #upload_sort_id {
        font-size: 12px;
    }

    .video-list-tbl .btn_chppse_bendi {
        padding: 10px;
    }

    .text-warning {
        float: left;
        height: 30px;
        line-height: 30px;
        padding: 0 20px;
        color: #666;
        font-size: 12px;
        background-color: #fff7eb;
        border: 1px solid #ffd5a3;
        margin-left: 20px;
        margin-bottom: 10px;
    }

    #bigvideo_data li div.box_img {
        width: 140px;
        height: 80px;
    }

    #bigvideo_data li div.box_img img {
        max-width: 140px;
        max-height: 80px;
    }

    .each_viedeo {
        width: 140px;
        height: 80px;

    }

    #bigvideo_data .video_dim {
        bottom: 20px;
    }

    .video_names {
        position: absolute;
        top: 82px;
        width: 140px;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    #bigvideo_data {

    }

    .some_button a {
        display: inline-block;
        width: 50%;
        text-align: center;
    }

    .no_video {
        width: 80%;
        height: 200px;
        text-align: center;
        display: flex;
        justify-content: center;
        flex-direction: column;
        color: #666;
        font-size: 13px;
    }

    .no_imgs {
        width: 100%;
        height: 80px;
        margin-bottom: 10px;
    }

    .no_video img {
        width: 80px;
        height: 80px;
    }
</style>

<div class="title">
    <span>图片空间/ </span>
    <span style="color: #666;">图片管理</span>
</div>

<div class="main-container">

    <form action="/admin/manage/video/list" name="form1" id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="set_cat_id" id="set_cat_id" value="">
        <input type="hidden" name="crop_video_id" id="crop_video_id" value="">
        <input type="hidden" name="no_full" id="no_full" value="${no_full!}">
        <input type="hidden" name="need_video_width" id="need_video_width" value="${need_video_width or 0!}">
        <input type="hidden" name="need_video_height" id="need_video_height" value="${need_video_height or 0!}">
        <input type="hidden" name="on_video_cb" id="on_video_cb" value="${on_video_cb!}">

        <input type="hidden" name="op_cat_id" id="op_cat_id" value="">
        <input type="hidden" name="op_cat_name" id="op_cat_name" value="">
        <input type="hidden" name="op_cat_pid" id="op_cat_pid" value="">
        {{ csrf_field()!}

        <div id="video_list" style="min-width: 600px;">
            <table class="video-list-tbl">
                <tr>
                    <td colspan="2" style="padding: 0;">
                        <div class="box btn_chppse_bendi clearfix">
                            <input type="button" class="bt_success" name="up_video_btn" id="up_video_btn"
                                   title="{{ trans("admin/video.upload_video_tip")!}"
                                   value="{{ trans("admin/video.upload_video")!}">
                            <#if ($version)
                                <div class="system_info" style="display: inline-block;float: none;margin-top: 0px;">
                                    <p class="system_info_prompt">
                                        <#if ($version['self']['num']<0)
                                            当前版本为${version['self']['version_name']!}，<span>不限制</span>内存空间
                                        <#else>
                                            当前版本为${version['self']['version_name']!}，剩余
                                            <span>${version['self']['num']-$version['self']['use'] >0 ? $version['self']['num']-$version['self']['use'] : 0!}</span>
                                            M内存空间
                                        </#if>
                                        {{--当前版本为高级版，剩余<span>100</span>M内存空间--!}
                                        <img src="http://${image_domain!}/image/admin/system_icon.png"/>
                                    </p>
                                    <img src="http://${image_domain!}/image/admin/system_shadow.png"
                                         class="system_shadow"/>
                                </div>
                            </#if>
                            <div class="text-warning" style="width: 94%;margin: 0px 0 0 10px;">
                                <img src="/image/admin/notice_img.png" alt="">
                                {{ trans("admin/video.upload_video_tip")!}
                            </div>
                        </div>
                        <div class="system_info_content" style="top: 203px;left: 280px;">
                            <div class="system_info_content_top">
                                <#if ($version['all'])
                                    <#list ($version['all'] as $k=>$ver)
                                        <#if ($ver['num']>=0)
                                            <#if ($k!=0)，</#if>${ver['version_name']!}<span
                                                    class="system_v1">${ver['num']!}</span>M内存空间
                                        <#else>
                                            <#if ($k!=0)，</#if>${ver['version_name']!}<span class="system_v1">不限制</span>
                                            内存空间
                                        </#if>
                                    </#list>
                                </#if>
                                {{--基础版<span class="system_v1">20</span>M内存空间，高级版最多创建<span class="system_v2">20</span>M内存空间，旗舰版<span class="system_v3">20</span>M内存空间--!}
                            </div>
                            <div class="system_info_content_bottom">
                                <a href="/admin/version/notice?mod_name=视频空间大小" target="_blank">了解更多</a>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align:top!important;padding-bottom: 10px">
                        <div class="box img-tree-container">
                            <div class="ztree img-cat-tree" id="img-cat-tree" style="height: 360px">
                            </div>
                            <input name="video_cat_id" type="hidden" value="${video_cat_id or 0!}" id="video_cat_id">
                        </div>
                    </td>
                    <td style="vertical-align:top!important;padding: 0 20px 10px 0">
                        <div class="box yigehezi">

                            <div class="hide">
                                <span>{{ trans("admin/video.upload_date")!}</span>
                                <input name="start_rq" type="text" value="${start_rq!}" onclick="return picker();"
                                       size=10>
                                <input name="end_rq" type="text" value="${end_rq!}" onclick="return picker();"
                                       size=10>
                                <span>{{ trans("admin/video.video_wh")!}</span>&nbsp;
                                <input name="video_width" type="text" value="${video_width!}" size=5>
                                <input name="video_height" type="text" value="${video_height!}" size=5>
                            </div>
                            <select name="upload_sort_id" id="upload_sort_id">
                                <#list ($upload_sort_list as $key => $item)
                                    <option value="${key!}"
                                            <#if  ($upload_sort_id == $key) selected="selected" </#if> >${item!}</option>
                                </#list>
                            </select>

                            <input name="keywords" type="text" value="${keywords!}" size=12>
                            <input name="show_type" id="show_type" type="hidden" value="${show_type!}">
                            <input type='submit' name="search" class="btn_sech"
                                   value="{{ trans("admin/video.search")!}">

                            <#if  ($need_video_width > 0 || $need_video_height > 0)
                                <label style="font-size: 12px"
                                       title="{{ trans("admin/video.need_video_condition")!}： <#if  ($need_video_width > 0){{ trans("admin/video.width")!}=${need_video_width!}px </#if> <#if  ($need_video_height > 0) {{ trans("admin/video.height")!}=${need_video_height!}px </#if>">
                                    <input type='checkbox' id="search_need" name="search_need"
                                           <#if  ($search_need) checked </#if> value="1">
                                    <#if  ($need_video_width > 0)${need_video_width!}px </#if>
                                    x <#if  ($need_video_height > 0) ${need_video_height!}px </#if>
                                </label>
                            </#if>

                            <p></p>


                        </div>

                        <div class="box img-list">
                            <#if  (count($data_list) == 0)
                                <div class="no_video">
                                    <div class="no_imgs"><img src="http://${image_domain!}/image/admin/no_video.png"/>
                                    </div>
                                    <div class="no_state">{{ trans("admin/video.fit_video_not_found")!}</div>
                                </div>
                            </#if>

                            <ul id="bigvideo_data" class="clearfix">
                                <#list ($data_list as $item)
                                    <li p50 title="${item->video_name!}">
                                        <div class="box_img" align="center">
                                            <img class="img-thumbnail each_viedeo"
                                                 src="<#if  ($item->snapshot_url)${item->snapshot_url!} </#if>">
                                        </div>
                                        <div class="box_desc">
                                            <input cbx_bigimg type="checkbox" name="cbx_img2[]"
                                                   value="${item->video_id!}"
                                                   url="${item->video_url!}"
                                                   video_name="${item->video_name!}"
                                                   video_size="${item->video_size!}"
                                                   video_width="${item->video_width!}"
                                                   video_height="${item->video_height!}"
                                                   snapshot_url="${item->snapshot_url!}"
                                                   video_path="${item->video_path!}"
                                                   <#if  ($crop_video_id == $item->video_id)checked </#if>
                                            >
                                            <span title="${item->video_name!}">${item->video_name!}</span>
                                        </div>
                                        <div class="video_mask">
                                            <#if  ($item->snapshot_url)
                                                <p class="some_button">
                                                    <a class="old_pic" href="${item->video_url!}" target=_blank
                                                       title="{{ trans("admin/video.show_original_video_tip")!}">播放</a>
                                                    {{--<a class="crop_video" style="visibility: hidden;" url="${item->video_url!}"--!}
                                                    {{--video_id="${item->video_id!}"--!}
                                                    {{--video_width="${item->video_width!}"--!}
                                                    {{--video_height="${item->video_height!}"--!}
                                                    {{--video_path="${item->video_path!}"--!}
                                                    {{--title="{{ trans("admin/video.crop_video_tip")!}">{{ trans("admin/video.crop")!}</a>--!}
                                                    <a class="remove_video"
                                                       url="${item->snapshot_url!}"
                                                       video_id="${item->video_id!}"
                                                       video_width="${item->video_width!}"
                                                       video_height="${item->video_height!}"
                                                       video_path="${item->video_path!}"
                                                       title="{{ trans("admin/video.del_video_tip")!}">{{ trans("admin/video.del")!}</a>
                                                </p>
                                            </#if>
                                        </div>
                                        <div class="video_dim">
                                            <p style="text-align:center">${item->video_width!}
                                                x${item->video_height!}</p>
                                        </div>
                                        <div class="video_sel">
                                        </div>
                                        <div class="video_names">${item->video_name!}</div>
                                    </li>
                                </#list>
                                <div class="clear"></div>
                            </ul>
                        </div>
                        <div class="box" style="margin-top: 15px">
                            <table width="100%" border="0" class="tb_paging" style="float:none;">
                                <tr>
                                    <td align="left" style="width: 200px;padding: 0">
                                        <input type='button' name="bat_set_video_cat" id="bat_set_video_cat"
                                               value="批量归类"
                                               class="btn-common" style="margin-left: 0">
                                        <input type='button' name="bat_remove_video" id="bat_remove_video" value="批量删除"
                                               class="btn-common">
                                    </td>
                                    <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                        <a href="#"
                                           onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                               size="5"
                                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                        <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </div>

    </form>
</div>

<div id="rMenu">
    <ul>
        <li id="m_add">{{ trans("admin/video.create_dir")!}</li>
        <li id="m_del">{{ trans("admin/video.del_dir")!}</li>
        <li id="m_rename">{{ trans("admin/video.rename_dir")!}</li>
    </ul>
</div>

<div class="hide">
    <select name="upload_video_cat_id" id="upload_video_cat_id">
        <option value="0">{{ trans("admin/video.my_video")!}</option>
        <#list  ($video_cat_list as $item)
            <option value="${item->video_cat_id!}">
                @for($i=0;$i<$item->level;$i++)
                    &nbsp; &nbsp;&nbsp;
                @endfor
                ${item->video_cat_name!}
            </option>
        </#list>
    </select>
</div>

<script>
    var video_cat_arr =@json($video_cat_arr);

    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>
<script type="text/javascript" src="/js/Jcrop/js/jquery.Jcrop.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ajaxuploader/ajaxfileupload.js?v=1.0.1"></script>
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.all.js?v=1.0.1"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/video_manager.js?v=1"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/video_common.js"></script>
<script language="JavaScript" src="/js/admin/video_manager_dlg.js?v=1.0.1"></script>

<#include "/admin/footer.ftl">
<script>
    <#if ($version)
    $('body').on('click', 'input[type="file"]', function (e) {
        var data = getPowerInfo('num_config', 'video_num', '', '视频空间大小', 1, 0);
        if (data.tip == 1) {
            e.preventDefault();
        }
        console.log(data);
    });
    </#if>
</script>
