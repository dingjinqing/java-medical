<#include ("system.header")

<link rel="stylesheet" href="/css/system/image_manager.css?v=1.0.1" type="text/css"/>
<link rel="stylesheet" href="/js/Jcrop/css/jquery.Jcrop.css" type="text/css"/>
<link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css"/>


<div class="manager-container1">

    <form action="/system/image/dialog" name="form1" id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="set_cat_id" id="set_cat_id" value="">
        <input type="hidden" name="crop_img_id" id="crop_img_id" value="">
        <input type="hidden" name="no_full" id="no_full" value="${no_full!}">
        <input type="hidden" name="need_img_width" id="need_img_width" value="${need_img_width or 0!}">
        <input type="hidden" name="need_img_height" id="need_img_height" value="${need_img_height or 0!}">
        <input type="hidden" name="on_img_cb" id="on_img_cb" value="${on_img_cb!}">

        <input type="hidden" name="op_cat_id" id="op_cat_id" value="">
        <input type="hidden" name="op_cat_name" id="op_cat_name" value="">
        <input type="hidden" name="op_cat_pid" id="op_cat_pid" value="">
        {{ csrf_field()!}

        <div id="image_list" style="min-width: 600px">
            <table class="image-list-tbl">
                <tr>
                    <td>
                        <div class="box img-tree-container">
                            <div class="ztree img-cat-tree" id="img-cat-tree">
                            </div>
                            <input name="img_cat_id" type="hidden" value="${img_cat_id or 0!}" id="img_cat_id">
                        </div>
                    </td>
                    <td style="vertical-align:top!important">
                        <div class="box" style="border-bottom: 1px solid #eee;">

                            <div class="hide">
                                <span>{{ trans("system/image.upload_date")!}</span>
                                <input name="start_rq" type="text" value="${start_rq!}" onclick="return picker();"
                                       size=10>
                                <input name="end_rq" type="text" value="${end_rq!}" onclick="return picker();"
                                       size=10>
                                <span>{{ trans("system/image.img_wh")!}</span>&nbsp;
                                <input name="img_width" type="text" value="${img_width!}" size=5>
                                <input name="img_height" type="text" value="${img_height!}" size=5>
                            </div>
                            <input type="button" class="bt_success" name="up_image_btn" id="up_image_btn"
                                   title="{{ trans("system/image.uploadImageTip")!}"
                                   value="{{ trans("system/image.upload_image")!}">

                            <select name="upload_sort_id" id="upload_sort_id">
                                <#list ($upload_sort_list as $key => $item)
                                    <option value="${key!}"
                                            <#if  ($upload_sort_id == $key) selected="selected" </#if> >${item!}</option>
                                </#list>
                            </select>

                            <input name="keywords" type="text" value="${keywords!}" size=12>
                            <input name="show_type" id="show_type" type="hidden" value="${show_type!}">
                            <input type='submit' name="search" value="{{ trans("system/image.search")!}">

                            <#if  ($need_img_width > 0 || $need_img_height > 0)
                                <label title="{{ trans("system/image.need_image_condition")!}： <#if  ($need_img_width > 0){{ trans("system/image.width")!}=${need_img_width!}px </#if> <#if  ($need_img_height > 0) {{ trans("system/image.height")!}=${need_img_height!}px </#if>">
                                    <input type='checkbox' id="search_need" name="search_need"
                                           <#if  ($search_need) checked </#if> value="1">
                                    <#if  ($need_img_width > 0)${need_img_width!}px </#if>
                                    x <#if  ($need_img_height > 0) ${need_img_height!}px </#if>
                                </label>
                            </#if>

                            <p></p>

                            <div class="text-warning">{{ trans("system/image.uploadImageTip")!}</div>

                        </div>

                        <div class="box img-list">
                            <#if  (count($data_list) == 0){{ trans("system/image.fit_image_not_found")!} </#if>

                            <ul id="bigimg_data" class="clearfix">
                                <#list ($data_list as $item)
                                    <li p50 title="${item->img_name!}">
                                        <div class="box_img" align="center">
                                            <img class="img-thumbnail"
                                                 src="<#if  ($item->img_url)${item->img_url!}!middle </#if>">
                                        </div>

                                        <div class="box_desc">
                                            <input cbx_bigimg type="checkbox" name="cbx_img2[]"
                                                   value="${item->img_id!}"
                                                   url="${item->img_url!}"
                                                   img_name="${item->img_name!}"
                                                   img_size="${item->img_size!}"
                                                   img_width="${item->img_width!}"
                                                   img_height="${item->img_height!}"
                                                   img_path="${item->img_path!}"
                                                   <#if  ($crop_img_id == $item->img_id)checked </#if>
                                            >
                                            <span title="${item->img_name!}">${item->img_name!}</span>
                                        </div>
                                        <div class="img_mask">
                                            <#if  ($item->img_url)
                                                <p>
                                                    <a class="old_pic" href="${item->img_url!}" target=_blank
                                                       title="{{ trans("system/image.show_original_image_tip")!}">{{ trans("system/image.original_image")!}</a>
                                                    <a class="crop_image" url="${item->img_url!}"
                                                       img_id="${item->img_id!}"
                                                       img_width="${item->img_width!}"
                                                       img_height="${item->img_height!}"
                                                       img_path="${item->img_path!}"
                                                       title="{{ trans("system/image.crop_image_tip")!}">{{ trans("system/image.crop")!}</a>
                                                    <a class="remove_image"
                                                       url="${item->img_url!}"
                                                       img_id="${item->img_id!}"
                                                       img_width="${item->img_width!}"
                                                       img_height="${item->img_height!}"
                                                       img_path="${item->img_path!}"
                                                       title="{{ trans("system/image.del_image_tip")!}">{{ trans("system/image.del")!}</a>
                                                </p>
                                            </#if>
                                        </div>
                                        <div class="img_dim">
                                            <p style="text-align:center">${item->img_width!}
                                                x${item->img_height!}</p>
                                        </div>
                                        <div class="img_sel">
                                        </div>
                                    </li>
                                </#list>
                                <div class="clear"></div>
                            </ul>
                        </div>
                        <div class="box clearfix">
                            <table width="100%" border="0">
                                <tr style="text-align: right">
                                    <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                        <a href="#"
                                           onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">
                                            {{ trans("system/common.page.next_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                               size="5"
                                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
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
        <li id="m_add">{{ trans("system/image.create_dir")!}</li>
        <li id="m_del">{{ trans("system/image.del_dir")!}</li>
        <li id="m_rename">{{ trans("system/image.rename_dir")!}</li>
    </ul>
</div>

<script>
    var img_cat_arr =@json($img_cat_arr);

    function gopage(page) {
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>

<script type="text/javascript" src="/js/Jcrop/js/jquery.Jcrop.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ajaxuploader/ajaxfileupload.js?v=1.0.4"></script>
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.all.js?v=1.0.4"></script>
<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/image_manager.js"></script>
<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/system/jCropRemoteImage.js?v=1.0.4"></script>
<script language="JavaScript" src="/js/system/image_manager_dlg.js?v=1.0.3"></script>


<#include ("system.footer")
