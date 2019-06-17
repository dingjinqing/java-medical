<#include ("system.header")

<link rel="stylesheet" href="/css/system/image_manager.css?v=1.0.1" type="text/css"/>
<link rel="stylesheet" href="/js/Jcrop/css/jquery.Jcrop.css" type="text/css"/>
<link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css"/>
<style>
    .btn_pagin table:first-child td:first-child{
        min-width: 326px;
    }
    .btn_pagin table:first-child td:first-child label{
        float: left;
    }
    .btn_pagin table:first-child td:first-child div{
        float: left;
        margin-left: 5px;
    }
    [type="button"]{
        padding: 3px 6px;
    }
    .btn_pagin table:last-child {
        min-width: 600px;
    }
    .file_select,.pic_search{
        width: 150px;
        height: 26px;
        border-radius: 3px;
        vertical-align: middle;
        /* outline: none; */
        border: 1px solid #ccc;
    }
    select[name="rows_per_page"]{
        min-width: 50px;
        height: 26px;
        vertical-align: middle;
        border-radius: 3px;
    }
</style>

<div class="manager-container1">

    <form action="/system/image/list" name="form1" id="form1" method="post">
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
                {{ trans("system/image.need_image_condition")!}：
                <#if  ($need_img_width > 0){{ trans("system/image.width")!}=${need_img_width!}px </#if>
                <#if  ($need_img_height > 0) {{ trans("system/image.height")!}=${need_img_height!}px </#if>
                &nbsp;&nbsp;
                <input type='checkbox' id="search_need" name="search_need" <#if  ($search_need) checked
                       </#if> value="1">{{ trans("system/image.search_needed_image")!}
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
                                <span>{{ trans("system/image.upload_date")!}</span>
                                <input name="start_rq" type="text" value="${start_rq!}" onclick="return picker();"
                                       size=10>
                                <input name="end_rq" type="text" value="${end_rq!}" onclick="return picker();"
                                       size=10>
                                <span>{{ trans("system/image.img_wh")!}</span>&nbsp;
                                <input name="img_width" type="text" value="${img_width!}" size=5>
                                <input name="img_height" type="text" value="${img_height!}" size=5>
                            </div>
                            <div id="log"></div>
                            <input type="button" class="bt_success" name="up_image_btn" id="up_image_btn"
                                   title="{{ trans("system/image.uploadImageTip")!}"
                                   value="{{ trans("system/image.upload_image")!}">

                            <select name="upload_sort_id" id="upload_sort_id" class="file_select">
                                <#list ($upload_sort_list as $key => $item)
                                    <option value="${key!}"
                                            <#if  ($upload_sort_id == $key) selected="selected" </#if> >${item!}</option>
                                </#list>
                            </select>

                            <input name="keywords" type="text" value="${keywords!}" size=12 class="pic_search">
                            <input name="show_type" id="show_type" type="hidden" value="${show_type!}">
                            <input type='submit' name="search" value="{{ trans("system/image.search")!}">

                            <input name="convert_view_mode" id="convert_view_mode" type="button"
                                   value="<#if  ($show_type =='list'){{ trans("system/image.big_show")!}<#else>{{ trans("system/image.list_show")!}</#if>">

                            {{ trans("system/image.rows_per_page")!}：
                            <select name="rows_per_page">
                                <option value="30" <#if  ($rows_per_page== "30") selected="selected" </#if> >30</option>
                                <option value="50" <#if  ($rows_per_page== "50") selected="selected" </#if> >50</option>
                                <option value="75" <#if  ($rows_per_page== "75") selected="selected" </#if> >75</option>
                                <option value="100" <#if  ($rows_per_page== "100") selected="selected" </#if> >100
                                </option>
                            </select>

                            <p></p>

                            <div class="text-warning">{{ trans("system/image.uploadImageTip")!}</div>

                        </div>

                        <div class="box img-list" style="height: 320px;overflow: auto">
                            <#if  (count($data_list) == 0){{ trans("system/image.fit_image_not_found")!} </#if>

                            <table id="list_data" cellspacing='1' cellpadding='3'
                                   width="100%" <#if  ($show_type != "list") style="display:none;" </#if>>
                                <tr>
                                    <th>{{ trans("system/image.select")!}</th>
                                    <th>{{ trans("system/image.image")!}</th>
                                    <th>{{ trans("system/image.img_name")!}</th>
                                    <th>{{ trans("system/image.img_type")!}</th>
                                    <th>{{ trans("system/image.img_wh")!}</th>
                                    <th>{{ trans("system/image.img_size")!}</th>
                                    <th>{{ trans("system/image.upload_time")!}</th>
                                    <th>{{ trans("system/image.img_cat_name")!}</th>
                                    <th>{{ trans("system/image.category.operation")!}</th>
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
                                               target=_blank>{{ trans("system/image.browse_orig_img")!}</a>&nbsp;&nbsp;
                                            <a class="crop_image" url="${item->img_url!}"
                                               img_id="${item->img_id!}"
                                               img_width="${item->img_width!}"
                                               img_height="${item->img_height!}"
                                               img_path="${item->img_path!}">{{ trans("system/image.crop_image")!}</a>&nbsp;&nbsp;
                                            <a class="remove_image" url="${item->img_url!}"
                                               img_id="${item->img_id!}"
                                               img_width="${item->img_width!}"
                                               img_height="${item->img_height!}"
                                               img_path="${item->img_path!}">{{ trans("system/common.operation.del")!}</a>&nbsp;&nbsp;
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
                                            <input cbx_bigimg type="checkbox" name="cbx_img2[]" value="${item->img_id!}"
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
                        <div class="box btn_pagin">
                            <table cellspacing='1' cellpadding='3' width="100%">
                                <tr>
                                    <td>
                                        <label><input type="checkbox" selall id="selall"
                                                      value="1">{{ trans("system/image.sel_all")!}</label>
                                        <div>
                                            <input type='button' name="copy_img_url" id="copy_img_url"
                                                   value="{{ trans("system/image.copy_link")!}">
                                            <input type='button' name="copy_img_url_code" id="copy_img_url_code"
                                                   value="{{ trans("system/image.copy_code")!}">
                                            <input type='button' name="bat_set_img_cat" id="bat_set_img_cat"
                                                   value="{{ trans("system/image.bat_category")!}">
                                            <input type='button' name="bat_remove_img" id="bat_remove_img"
                                                   value="{{ trans("system/image.bat_del")!}">
                                        </div>

                                    </td>
                                    <td align="right">
                                        <table width="100%" border="0">
                                            <tr>
                                                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count(),'total'=>$data_list->total(),])!}
                                                    <a href="#"
                                                       onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                                    <a href="#"
                                                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                                    <a href="#"
                                                       onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">
                                                        {{ trans("system/common.page.next_page")!}</a>
                                                    <a href="#"
                                                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                                    <input id="page" name="page" type="text"
                                                           value="${data_list->currentPage()!}" size="5"
                                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                                </td>
                                            </tr>
                                        </table>
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
        <option value="0">{{ trans("system/image.my_image")!}</option>
        <#list  ($img_cat_list as $item)
            <option value="${item->img_cat_id!}">
                @for($i=0;$i<$item->level;$i++)
                    &nbsp;&nbsp;&nbsp;
                @endfor
                ${item->img_cat_name!}
            </option>
        </#list>
    </select>

</div>

<div id="rMenu">
    <ul>
        <li id="m_add">{{ trans("system/image.create_dir")!}</li>
        <li id="m_del">{{ trans("system/image.del_dir")!}</li>
        <li id="m_rename">{{ trans("system/image.rename_dir")!}</li>
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
<script type="text/javascript" src="/js/system/jCropRemoteImage.js"></script>
<script language="JavaScript" src="/js/system/image_manager_tb.js?v=1.0.2"></script>


<#include ("system.footer")
