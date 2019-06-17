<#include "/admin/header.ftl">

<link rel="stylesheet" href="/css/admin/video_manager.css?v=1.0.2" type="text/css"/>
<link rel="stylesheet" href="/js/Jcrop/css/jquery.Jcrop.css" type="text/css"/>
<link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css"/>

<div class="title">
    <span>小视频 / </span><span style="color: #666;">小视频空间管理</span>
</div>

<div class="main-container">

    <div class="manager-container1">

        <form action="/admin/manage/video/list" name="form1" id="form1" method="post">
            <input type="hidden" name="act" id="act" value="">
            <input type="hidden" name="set_cat_id" id="set_cat_id" value="">
            <input type="hidden" name="crop_video_id" id="crop_video_id" value="">
            <input type="hidden" name="no_full" id="no_full" value="${no_full!}">
            <input type="hidden" name="need_video_width" id="need_video_width" value="${need_video_width or 0!}">
            <input type="hidden" name="need_video_height" id="need_video_height" value="${need_video_height or 0!}">

            <input type="hidden" name="op_cat_id" id="op_cat_id" value="">
            <input type="hidden" name="op_cat_name" id="op_cat_name" value="">
            <input type="hidden" name="op_cat_pid" id="op_cat_pid" value="">

            {{ csrf_field()!}

            <#if  ($need_video_width > 0 || $need_video_height > 0)
                <div class="box panel panel-body" style="color:red; line-height:24px;">
                    {{ trans("admin/video.need_video_condition")!}：
                    <#if  ($need_video_width > 0){{ trans("admin/video.width")!}=${need_video_width!}px </#if>
                    <#if  ($need_video_height > 0) {{ trans("admin/video.height")!}=${need_video_height!}px </#if>
                    &nbsp;&nbsp;
                    <input type='checkbox' id="search_need" name="search_need" <#if  ($search_need) checked
                           </#if> value="1">{{ trans("admin/video.search_needed_video")!}
                </div>
            </#if>

            <#if  ($msg)
                <div class="box panel panel-body">
                    <span style="color:red;">${msg!}</span>
                </div>
            </#if>

            <div id="video_list" style="min-width: 1000px">
                <table style="width: 100%;height: 100%;">
                    <tr>
                        <td style="vertical-align: top;width: 220px;padding:0;">
                            <div class="box" style="width: 200px;height: 505px">
                                <div class="ztree img-cat-tree" id="img-cat-tree">

                                </div>

                                <input name="video_cat_id" type="hidden" value="${video_cat_id or 0!}"
                                       id="video_cat_id">
                            </div>
                        </td>
                        <td style="vertical-align: top;padding:0;">
                            <div class="box ">

                                <div class="hide">
                                    <span>{{ trans("admin/video.upload_date")!}</span>
                                    <input name="start_rq" type="text" value="${start_rq!}"
                                           onclick="return picker();"
                                           size=10>
                                    <input name="end_rq" type="text" value="${end_rq!}" onclick="return picker();"
                                           size=10>
                                    <span>{{ trans("admin/video.video_wh")!}</span>&nbsp;
                                    <input name="video_width" type="text" value="${video_width!}" size=5>
                                    <input name="video_height" type="text" value="${video_height!}" size=5>
                                </div>
                                <div id="log"></div>
                                <input type="button" class="bt_success" name="up_video_btn" id="up_video_btn"
                                       title="{{ trans("admin/video.upload_video_tip")!}"
                                       value="{{ trans("admin/video.upload_video")!}">

                                <select name="upload_sort_id" id="upload_sort_id">
                                    <#list ($upload_sort_list as $key => $item)
                                        <option value="${key!}"
                                                <#if  ($upload_sort_id == $key) selected="selected" </#if> >${item!}</option>
                                    </#list>
                                </select>

                                <input name="keywords" type="text" value="${keywords!}" size=12>
                                <input name="show_type" id="show_type" type="hidden" value="${show_type!}">
                                <input type='submit' name="search" value="{{ trans("admin/video.search")!}">

                                <input name="convert_view_mode" id="convert_view_mode" type="button"
                                       value="<#if  ($show_type =='list'){{ trans("admin/video.big_show")!}<#else>{{ trans("admin/video.list_show")!}</#if>">

                                {{ trans("admin/video.rows_per_page")!}：
                                <select name="rows_per_page">
                                    <option value="30" <#if  ($rows_per_page== "30") selected="selected" </#if> >30
                                    </option>
                                    <option value="50" <#if  ($rows_per_page== "50") selected="selected" </#if> >50
                                    </option>
                                    <option value="75" <#if  ($rows_per_page== "75") selected="selected" </#if> >75
                                    </option>
                                    <option value="100" <#if  ($rows_per_page== "100") selected="selected" </#if> >100
                                    </option>
                                </select>

                                <p></p>

                                <div class="text-warning">{{ trans("admin/video.upload_video_tip")!}</div>

                            </div>

                            <div class="box img-list" style="height: 320px;overflow: auto">
                                <#if  (count($data_list) == 0){{ trans("admin/video.fit_video_not_found")!} </#if>

                                <table id="list_data" cellspacing='1' cellpadding='3' class="table table-striped"
                                       width="100%" <#if  ($show_type != "list") style="display:none;" </#if>>
                                    <tr>
                                        <th>{{ trans("admin/video.select")!}</th>
                                        <th>{{ trans("admin/video.video")!}</th>
                                        <th>{{ trans("admin/video.video_name")!}</th>
                                        <th>{{ trans("admin/video.video_type")!}</th>
                                        <th>{{ trans("admin/video.video_wh")!}</th>
                                        <th>{{ trans("admin/video.video_size")!}</th>
                                        <th>{{ trans("admin/video.upload_time")!}</th>
                                        <th>{{ trans("admin/video.video_cat_name")!}</th>
                                        <th>{{ trans("admin/video.category.operation")!}</th>
                                    </tr>
                                    <#list  ($data_list as $item)
                                        <tr>
                                            <td align="left">
                                                <input cbx_list type="checkbox" name="cbx_img[]"
                                                       value="${item->video_id!}"
                                                       url="${item->snapshot_url!}"
                                                       video_name="${item->video_name!}"
                                                       video_size="${item->video_size!}"
                                                       video_width="${item->video_width!}"
                                                       video_height="${item->video_height!}"
                                                       video_path="${item->video_path!}"
                                                       <#if  ($crop_video_id == $item->video_id)checked </#if>>
                                            </td>
                                            <td align="center"
                                                style="width:56px;height:56px;padding:2px;vertical-align:middle;">
                                                <img class="img-thumbnail"
                                                     src="<#if  ($item->snapshot_url)${item->snapshot_url!} </#if>"
                                                     style="max-width:50px;max-height:50px;">
                                            </td>
                                            <td align="center"
                                                style="max-width:200px;word-wrap:break-word; overflow:hidden;">${item->video_name!}
                                            </td>
                                            <td align="center">${item->video_type!}</td>
                                            <td align="center">${item->video_width!}x${item->video_height!}</td>
                                            <td align="center">${item->video_size!}</td>
                                            <td align="center">${item->upload_time!}</td>
                                            <td align="center">${item->video_cat_name!}</td>
                                            <td align="center">
                                                <a href="${item->video_url!}"
                                                   target=_blank>{{ trans("admin/video.browse_orig_img")!}</a>&nbsp;&nbsp;
                                                <a class="crop_video" style="visibility: hidden"
                                                   url="${item->snapshot_url!}"
                                                   video_id="${item->video_id!}"
                                                   video_width="${item->video_width!}"
                                                   video_height="${item->video_height!}"
                                                   video_path="${item->video_path!}">{{ trans("admin/video.crop_video")!}</a>&nbsp;&nbsp;
                                                <a class="remove_video" url="${item->snapshot_url!}"
                                                   video_id="${item->video_id!}"
                                                   video_width="${item->video_width!}"
                                                   video_height="${item->video_height!}"
                                                   video_path="${item->video_path!}">{{ trans("admin/common.operation.del")!}</a>&nbsp;&nbsp;
                                            </td>
                                        </tr>
                                    </#list>

                                </table>
                                <ul id="bigvideo_data" <#if  ($show_type == "list") style="display:none;" </#if> >
                                    <#list  ($data_list as $item)
                                        <li p50 title="${item->video_name!}">
                                            <div class="box_img" align="center">
                                                <img class="img-thumbnail"
                                                     src="<#if  ($item->snapshot_url)${item->snapshot_url!} </#if>">
                                            </div>

                                            <div class="box_desc">
                                                <input cbx_bigimg type="checkbox" name="cbx_img2[]"
                                                       value="${item->video_id!}"
                                                       url="${item->snapshot_url!}"
                                                       video_name="${item->video_name!}"
                                                       video_size="${item->video_size!}"
                                                       video_width="${item->video_width!}"
                                                       video_height="${item->video_height!}"
                                                       video_path="${item->video_path!}"
                                                       <#if  ($crop_video_id == $item->video_id)checked </#if>>
                                                <span title="${item->video_name!}">${item->video_name!}</span>
                                            </div>
                                            <div class="video_mask">
                                                <#if  ($item->snapshot_url)
                                                    <p>
                                                        <a class="old_pic" href="${item->video_url!}" target=_blank
                                                           title="{{ trans("admin/video.browse_orig_img")!}">{{ trans("admin/video.browse_orig_img")!}</a>
                                                        <a class="crop_video" style="visibility: hidden"
                                                           url="${item->snapshot_url!}"
                                                           video_id="${item->video_id!}"
                                                           video_width="${item->video_width!}"
                                                           video_height="${item->video_height!}"
                                                           video_path="${item->video_path!}"
                                                           title="{{ trans("admin/video.crop_video_tip")!}">{{ trans("admin/video.crop")!}</a>
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
                                        </li>
                                    </#list>
                                    <div class="clear"></div>
                                </ul>
                            </div>
                            <div class="box">
                                <table cellspacing='1' cellpadding='3' width="100%">
                                    <tr>
                                        <td>
                                            <label><input type="checkbox" selall id="selall"
                                                          value="1">{{ trans("admin/video.sel_all")!}</label>
                                            <div style="display:inline-block;padding-left: 50px">
                                                <input type='button' name="bat_set_video_cat" id="bat_set_video_cat"
                                                       value="{{ trans("admin/video.bat_category")!}">
                                                <input type='button' name="bat_remove_img" id="bat_remove_img"
                                                       value="{{ trans("admin/video.bat_del")!}">
                                            </div>

                                        </td>
                                        <td align="right">
                                          <#include "/admin/jump_page_admin.ftl">
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

<div id="rMenu">
    <ul>
        <li id="m_add">{{ trans("admin/video.create_dir")!}</li>
        <li id="m_del">{{ trans("admin/video.del_dir")!}</li>
        <li id="m_rename">{{ trans("admin/video.rename_dir")!}</li>
    </ul>
</div>

<#if  ($no_full == '1')

    <script>
        $("body").css("min-width", "0");
        $("body").css("max-height", "250px;");
    </script>
</#if>

<script>
    var video_cat_arr =@json($video_cat_arr);

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>
<script>

</script>
<script type="text/javascript" src="/js/admin/page.js?v=1.0.0"></script>
<script type="text/javascript" src="/js/Jcrop/js/jquery.Jcrop.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ajaxuploader/ajaxfileupload.js?v=1.0.2"></script>
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.all.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/video_manager.js?v=1.0.1"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/video_common.js"></script>
<script language="JavaScript" src="/js/admin/video_manager_tb.js?v=1.0.2"></script>


<#include "/admin/footer.ftl">
