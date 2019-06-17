<#include "/admin/header.ftl">

<link rel="stylesheet" href="/css/admin/image_manager.css?v=1.0.2" type="text/css"/>
<link rel="stylesheet" href="/js/Jcrop/css/jquery.Jcrop.css" type="text/css"/>
<link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css"/>


<div class="manager-container1">

    <form action="/admin/manage/image/list" name="form1" id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="set_cat_id" id="set_cat_id" value="">
        <input type="hidden" name="crop_img_id" id="crop_img_id" value="">
        <input type="hidden" name="no_full" id="no_full" value="${no_full!}">
        <input type="hidden" name="need_img_width" id="need_img_width" value="${need_img_width or 0!}">
        <input type="hidden" name="need_img_height" id="need_img_height" value="${need_img_height or 0!}">

        <input type="hidden" name="op_cat_id" id="op_cat_id" value="">
        <input type="hidden" name="op_cat_name" id="op_cat_name" value="">
        <input type="hidden" name="op_cat_pid" id="op_cat_pid" value="">

        {{ csrf_field()!}

        <#if  ($need_img_width > 0 || $need_img_height > 0)
            <div class="box panel panel-body" style="color:red; line-height:24px;">
                {{ trans("admin/image.need_image_condition")!}：
                <#if  ($need_img_width > 0){{ trans("admin/image.width")!}=${need_img_width!}px </#if>
                <#if  ($need_img_height > 0) {{ trans("admin/image.height")!}=${need_img_height!}px </#if>
                &nbsp;&nbsp;
                <input type='checkbox' id="search_need" name="search_need" <#if  ($search_need) checked
                       </#if> value="1">{{ trans("admin/image.search_needed_image")!}
            </div>
        </#if>

        <#if  ($msg)
            <div class="box panel panel-body">
                <span style="color:red;">${msg!}</span>
            </div>
        </#if>

        <div id="image_list" style="min-width: 1000px">
            <table style="width: 100%;height: 100%;">
                <tr>
                    <td style="vertical-align: top;width: 220px;padding:0;">
                        <div class="box" style="width: 200px;height: 505px">
                            <div class="ztree img-cat-tree" id="img-cat-tree">

                            </div>

                            <input name="img_cat_id" type="hidden" value="${img_cat_id or 0!}" id="img_cat_id">
                        </div>
                    </td>
                    <td style="vertical-align: top;padding:0;">
                        <div class="box ">

                            <div class="hide">
                                <span>{{ trans("admin/image.upload_date")!}</span>
                                <input name="start_rq" type="text" value="${start_rq!}" onclick="return picker();"
                                       size=10>
                                <input name="end_rq" type="text" value="${end_rq!}" onclick="return picker();"
                                       size=10>
                                <span>{{ trans("admin/image.img_wh")!}</span>&nbsp;
                                <input name="img_width" type="text" value="${img_width!}" size=5>
                                <input name="img_height" type="text" value="${img_height!}" size=5>
                            </div>
                            <div id="log"></div>
                            <input type="button" class="bt_success" name="up_image_btn" id="up_image_btn"
                                   title="{{ trans("admin/image.uploadImageTip")!}"
                                   value="{{ trans("admin/image.upload_image")!}">

                            <select name="upload_sort_id" id="upload_sort_id">
                                <#list ($upload_sort_list as $key => $item)
                                    <option value="${key!}"
                                            <#if  ($upload_sort_id == $key) selected="selected" </#if> >${item!}</option>
                                </#list>
                            </select>

                            <input name="keywords" type="text" value="${keywords!}" size=12>
                            <input name="show_type" id="show_type" type="hidden" value="${show_type!}">
                            <input type='submit' name="search" value="{{ trans("admin/image.search")!}">

                            <input name="convert_view_mode" id="convert_view_mode" type="button"
                                   value="<#if  ($show_type =='list'){{ trans("admin/image.big_show")!}<#else>{{ trans("admin/image.list_show")!}</#if>">

                            {{ trans("admin/image.rows_per_page")!}：
                            <select name="rows_per_page">
                                <option value="30" <#if  ($rows_per_page== "30") selected="selected" </#if> >30</option>
                                <option value="50" <#if  ($rows_per_page== "50") selected="selected" </#if> >50</option>
                                <option value="75" <#if  ($rows_per_page== "75") selected="selected" </#if> >75</option>
                                <option value="100" <#if  ($rows_per_page== "100") selected="selected" </#if> >100
                                </option>
                            </select>

                            <p></p>

                            <div class="text-warning">{{ trans("admin/image.uploadImageTip")!}</div>

                        </div>

                        <div class="box img-list" style="height: 320px;overflow: auto">
                            <#if  (count($data_list) == 0){{ trans("admin/image.fit_image_not_found")!} </#if>

                            <table id="list_data" cellspacing='1' cellpadding='3'
                                   width="100%" <#if  ($show_type != "list") style="display:none;" </#if>>
                                <tr>
                                    <th>{{ trans("admin/image.select")!}</th>
                                    <th>{{ trans("admin/image.image")!}</th>
                                    <th>{{ trans("admin/image.img_name")!}</th>
                                    <th>{{ trans("admin/image.img_type")!}</th>
                                    <th>{{ trans("admin/image.img_wh")!}</th>
                                    <th>{{ trans("admin/image.img_size")!}</th>
                                    <th>{{ trans("admin/image.upload_time")!}</th>
                                    <th>{{ trans("admin/image.img_cat_name")!}</th>
                                    <th>{{ trans("admin/image.category.operation")!}</th>
                                </tr>
                                <#list  ($data_list as $item)
                                    <tr>
                                        <td align="left">
                                            <input cbx_list type="checkbox" name="cbx_img[]" value="${item->img_id!}"
                                                   url="${item->img_url!}" img_name="${item->img_name!}"
                                                   img_size="${item->img_size!}" img_width="${item->img_width!}"
                                                   img_height="${item->img_height!}" img_path="${item->img_path!}"
                                                   <#if  ($crop_img_id == $item->img_id)checked </#if>>
                                        </td>
                                        <td align="center"
                                            style="width:56px;height:56px;padding:2px;vertical-align:middle;">
                                            <img class="img-thumbnail"
                                                 src="<#if  ($item->img_url)${item->img_url!}!middle </#if>"
                                                 style="max-width:50px;max-height:50px;">
                                        </td>
                                        <td align="center"
                                            style="max-width:200px;word-wrap:break-word; overflow:hidden;">${item->img_name!}
                                        </td>
                                        <td align="center">${item->img_type!}</td>
                                        <td align="center">${item->img_width!}x${item->img_height!}</td>
                                        <td align="center">${item->img_size!}</td>
                                        <td align="center">${item->upload_time!}</td>
                                        <td align="center">${item->img_cat_name!}</td>
                                        <td align="center">
                                            <a href="${item->img_url!}"
                                               target=_blank>{{ trans("admin/image.browse_orig_img")!}</a>&nbsp;&nbsp;
                                            <a class="crop_image" url="${item->img_url!}"
                                               img_id="${item->img_id!}"
                                               img_width="${item->img_width!}"
                                               img_height="${item->img_height!}"
                                               img_path="${item->img_path!}">{{ trans("admin/image.crop_image")!}</a>&nbsp;&nbsp;
                                            <a class="remove_image" url="${item->img_url!}"
                                               img_id="${item->img_id!}"
                                               img_width="${item->img_width!}"
                                               img_height="${item->img_height!}"
                                               img_path="${item->img_path!}">{{ trans("admin/common.operation.del")!}</a>&nbsp;&nbsp;
                                        </td>
                                    </tr>
                                </#list>

                            </table>
                            <ul id="bigimg_data" <#if  ($show_type == "list") style="display:none;" </#if> >
                                <#list  ($data_list as $item)
                                    <li p50 title="${item->img_name!}">
                                        <div class="box_img" align="center">
                                            <img class="img-thumbnail"
                                                 src="<#if  ($item->img_url)${item->img_url!}!middle </#if>">
                                        </div>

                                        <div class="box_desc">
                                            <input cbx_bigimg type="checkbox" name="cbx_img2[]"
                                                   value="${item->img_id!}"
                                                   url="${item->img_url!}" img_name="${item->img_name!}"
                                                   img_size="${item->img_size!}" img_width="${item->img_width!}"
                                                   img_height="${item->img_height!}" img_path="${item->img_path!}"
                                                   <#if  ($crop_img_id == $item->img_id)checked </#if>>
                                            <span title="${item->img_name!}">${item->img_name!}</span>
                                        </div>
                                        <div class="img_mask">
                                            <#if  ($item->img_url)
                                                <p>
                                                    <a class="old_pic" href="${item->img_url!}" target=_blank
                                                       title="{{ trans("admin/image.show_original_image_tip")!}">{{ trans("admin/image.original_image")!}</a>
                                                    <a class="crop_image" url="${item->img_url!}"
                                                       img_id="${item->img_id!}"
                                                       img_width="${item->img_width!}"
                                                       img_height="${item->img_height!}"
                                                       img_path="${item->img_path!}"
                                                       title="{{ trans("admin/image.crop_image_tip")!}">{{ trans("admin/image.crop")!}</a>
                                                    <a class="remove_image"
                                                       url="${item->img_url!}"
                                                       img_id="${item->img_id!}"
                                                       img_width="${item->img_width!}"
                                                       img_height="${item->img_height!}"
                                                       img_path="${item->img_path!}"
                                                       title="{{ trans("admin/image.del_image_tip")!}">{{ trans("admin/image.del")!}</a>
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
                        <div class="box">
                            <table cellspacing='1' cellpadding='3' width="100%">
                                <tr>
                                    <td>
                                        <label><input type="checkbox" selall id="selall"
                                                      value="1">{{ trans("admin/image.sel_all")!}</label>
                                        <div style="display:inline-block;padding-left: 50px">
                                            <input type='button' name="copy_img_url" id="copy_img_url"
                                                   value="{{ trans("admin/image.copy_link")!}">
                                            <input type='button' name="copy_img_url_code" id="copy_img_url_code"
                                                   value="{{ trans("admin/image.copy_code")!}">
                                            <input type='button' name="bat_set_img_cat" id="bat_set_img_cat"
                                                   value="{{ trans("admin/image.bat_category")!}">
                                            <input type='button' name="bat_remove_img" id="bat_remove_img"
                                                   value="{{ trans("admin/image.bat_del")!}">
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

<div class="hide">
    <select name="upload_img_cat_id" id="upload_img_cat_id">
        <option value="0">{{ trans("admin/image.my_image")!}</option>
        <#list  ($img_cat_list as $item)
            <option value="${item->img_cat_id!}">
                @for($i=0;$i<$item->level;$i++)
                    &nbsp; &nbsp;&nbsp;
                @endfor
                ${item->img_cat_name!}
            </option>
        </#list>
    </select>

</div>

<div id="rMenu">
    <ul>
        <li id="m_add">{{ trans("admin/image.create_dir")!}</li>
        <li id="m_del">{{ trans("admin/image.del_dir")!}</li>
        <li id="m_rename">{{ trans("admin/image.rename_dir")!}</li>
    </ul>
</div>

<#if  ($no_full == '1')

    <script>
        $("body").css("min-width", "0");
        $("body").css("max-height", "250px;");
    </script>
</#if>

<script>
    var img_cat_arr =@json($img_cat_arr);

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>

<style>
    .remote-crop-container {
        padding: 10px 15px;
    }

    .crop-input-form {
        margin: 5px 0 5px 0;
    }
    .crop-input-form:after{
        content: '';
        display: block;
        clear: both;
    }
    .crop-input-line {
        height: 24px;
        line-height: 24px;
        float: left;
    }

    .crop-text-label {
        width: 100px;
    }

    .crop-input {
        height: 20px;
    }

    .crop-table {
        border-spacing: 0;
        width: 350px;
        height: 150px;
    }

    .crop-table td {
        padding: 0;
        vertical-align: middle;
        width: 175px;
    }

    .crop-image-container {
        border: 1px solid #F79D01;
        display: table-cell;
        vertical-align: middle;
        width: 150px;;
        height: 150px;
    }

    .crop-target {
        max-width: 150px;
        max-height: 150px;
    }

    .crop-preview {
        margin: 2px;
        boder: 1px solid #F79D01;
        width: 100px;
        height: 100px;
        overflow: hidden;
    }

    .jcrop-holder {
        display: block;
        float: none;
        margin-bottom: 0;
    }

</style>

<div id="j_upload_div" class="remote-crop-container">
    <div class="crop-input-form">
        <div class="crop-input-line">
            <span class="crop-text-label">裁剪图片宽度：</span>
            <input type="text" id="crop_width" size=3 class="crop-input">
        </div>
        <div class="crop-input-line">
            <span class="crop-text-label">裁剪图片高度：</span>
            <input type="text" id="crop_height" size=3 class="crop-input">
        </div>
        <div class="crop-input-line">
            <span class="crop-text-label">裁剪图片比例：</span>
            <input type="text" id="aspectRatio" size=3 class="crop-input">
        </div>
        <div class="crop-input-line">
            <span class="text-warning crop-text-hint">剪裁后图片宽高为空，剪裁为实际宽高存储</span>
        </div>
    </div>
    <table id="j_upload_pic" class="crop-table">
        <tr>
            <td>
                <div class="crop-image-container">
                    <img src="" id="target" class="crop-target"/>
                </div>
            </td>
            <td>
                <div id="preview_div">
                    <img src="" id="preview" class="crop-preview"/>
                </div>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript" src="/js/admin/page.js?v=1.0.0"></script>
<script type="text/javascript" src="/js/Jcrop/js/jquery.Jcrop.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ajaxuploader/ajaxfileupload.js?v=1.0.5"></script>
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.all.js?v=1.0.5"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/image_manager.js?v=1"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/admin/jCropRemoteImage.js?v=1.0.5"></script>
<script language="JavaScript" src="/js/admin/image_manager_tb.js?v=1.0.4"></script>


<#include "/admin/footer.ftl">
