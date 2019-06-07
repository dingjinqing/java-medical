<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/image_manager.css?v=1.1.6" type="text/css"/>
<link rel="stylesheet" href="/js/Jcrop/css/jquery.Jcrop.css" type="text/css"/>
<link rel="stylesheet" href="/js/zTree/css/zTreeStyle/zTreeStyle.css?v=1.1.6"/>
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

    .layui-layer-title {
        text-align: center !important;
        padding: 0 !important;
    }

</style>

<div class="manager-container1">

    <form action="${action}" name="form1"
          id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="set_cat_id" id="set_cat_id" value="">
        <input type="hidden" name="crop_img_id" id="crop_img_id" value="">
        <input type="hidden" name="no_full" id="no_full" value="${input_map.no_full!}">
        <input type="hidden" name="need_img_width" id="need_img_width" value="${input_map.need_img_width!0}">
        <input type="hidden" name="need_img_height" id="need_img_height" value="${input_map.need_img_height!0}">
        <input type="hidden" name="on_img_cb" id="on_img_cb" value="${input_map.on_img_cb!}">

        <input type="hidden" name="op_cat_id" id="op_cat_id" value="">
        <input type="hidden" name="op_cat_name" id="op_cat_name" value="">
        <input type="hidden" name="op_cat_pid" id="op_cat_pid" value="">

        <div id="image_list" style="min-width: 600px;">
            <table class="image-list-tbl">
                <tr>
                    <td colspan="2" style="padding: 0;">
                        <div class="box btn_chppse_bendi clearfix">
                            <input type="button" class="bt_success" name="up_image_btn" id="up_image_btn"
                                   title="上传图片支持jpeg、jpg、png、bmp格式，为保障前端加载顺利，单张图片大小不能超过5M"
                                   value="上传图片">
                            <#if (version??)>
                                <div class="system_info" style="display: inline-block;float: none;margin-top: 0px;">
                                    <p class="system_info_prompt">
                                        <#if (version['self']['num']<0)>
                                            当前版本为${version['self']['version_name']!}，<span>不限制</span>内存空间
                                        <#else>
                                            当前版本为${version['self']['version_name']!}，剩余
                                            <span><#if version['self']['num']-version['self']['use'] >0> ${version['self']['num']-version['self']['use']}<#else>0</#if></span>
                                            M内存空间
                                        </#if>
                                        <img src="http://${image_domain!}/image/admin/system_icon.png"/>
                                    </p>
                                    <img src="http://${image_domain!}/image/admin/system_shadow.png"
                                         class="system_shadow"/>
                                </div>
                            </#if>
                            <div class="text-warning" style="width: 94%;margin: 0px 0 0 10px;">
                                <img src="http://${image_domain!}/image/admin/notice_img.png" alt="">
                                上传图片支持jpeg、jpg、png、bmp格式，为保障前端加载顺利，单张图片大小不能超过5M
                            </div>
                        </div>
                        <div class="system_info_content" style="top: 56px;left: 134px;">
                            <div class="system_info_content_top">
                                <#if (version['all']??)>
                                    <#list version['all'] as ver>
                                        <#if (ver['num']<0)>
                                            <#if (ver?index!=0)> ，</#if>${ver['version_name']!}<span class="system_v1">不限制</span>
                                            内存空间
                                        <#else>
                                            <#if (ver?index!=0)> ，</#if>${ver['version_name']!}<span
                                                    class="system_v1">${ver['num']!}</span>M内存空间
                                        </#if>
                                    </#list>
                                </#if>
                            </div>
                            <div class="system_info_content_bottom">
                                <a href="/admin/version/notice?mod_name=图片空间大小" target="_blank">了解更多</a>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="box img-tree-container">
                            <div class="ztree img-cat-tree" id="img-cat-tree">
                            </div>
                            <input name="img_cat_id" type="hidden" value="${img_cat_id!0}" id="img_cat_id">
                        </div>
                    </td>
                    <td style="vertical-align:top!important">
                        <div class="box yigehezi">

                            <div class="hide">
                                <span>上传时间</span>
                                <input name="start_rq" type="text" value="${start_rq!}" onclick="return picker();"
                                       size=10>
                                <input name="end_rq" type="text" value="${end_rq!}" onclick="return picker();"
                                       size=10>
                                <span>图片宽高</span>&nbsp;
                                <input name="img_width" type="text" value="${img_width!}" size=5>
                                <input name="img_height" type="text" value="${img_height!}" size=5>
                            </div>
                            <select name="upload_sort_id" id="upload_sort_id">
                            	<#list upload_sort_list as item>
                                    <option value="${item?index}"  <#if input_map.upload_sort_id?? &&  (input_map.upload_sort_id?number == item?index)> selected="selected" </#if> >${item!}</option>
                                </#list>
                                
                            </select>

                            <input name="keywords" type="text" value="${keywords!}" size=12>
                            <input name="show_type" id="show_type" type="hidden" value="${show_type!}">
                            <input type='submit' name="search" class="btn_sech"
                                   value="搜索">

							<#if (input_map['search_need']! != "") || (input_map['need_img_height']! != "") && (input_map['need_img_height']?number > 0) >
                                <label style="font-size: 12px"
                                       title="所需图片必须满足： <#if (input_map['need_img_width']! != "") && (input_map['need_img_width']?number > 0)>宽度=${input_map['need_img_width']!}px </#if> <#if  (input_map['need_img_height']! != "") && (input_map['need_img_height']?number > 0) >高度=${input_map['need_img_height']!}px </#if>">
                                    <input type='checkbox' id="search_need" name="search_need"
                                           <#if  (input_map['search_need']! != "")> checked </#if> value="1">
                                           <#if  (input_map['need_img_width']! != "") && (input_map['need_img_width']?number > 0)>${input_map['need_img_width']!} </#if> 
                                           <#if  (input_map['need_img_height']! != "") && (input_map['need_img_height']?number > 0) >x${input_map['need_img_height']!} </#if>

                                </label>
                            </#if>

                            <p></p>


                        </div>

                        <div class="box img-list">
                            <#if  data_list?size == 0>
                                <div class="text-warning padding-top-10" style="text-align: center;width: 100%;height: 150px;margin-top: 50px">
                                    <img src="http://${image_domain!}/image/admin/image_no_data.png" alt="" style="margin-top: 20px">
                                    <p style="color:#999;font-size: 14px;text-align: center;margin-top: 15px">当前文件夹未找到符合要求的图片</p>
                                </div>
                            </#if>

                            <ul id="bigimg_data" class="clearfix">
                                <#list data_list as item>
                                    <li p50 title="${item.img_name!}">
                                        <div class="box_img" align="center">
                                            <img class="img-thumbnail"
                                                 src="<#if  (item.img_url??)> ${item.img_url!}!middle </#if>">
                                        </div>

                                        <div class="box_desc">
                                            <input cbx_bigimg type="checkbox" name="cbx_img2[]"
                                                   value="${item.img_id!}"
                                                   url="${item.img_url!}"
                                                   img_name="${item.img_name!}"
                                                   img_size="${item.img_size!}"
                                                   img_width="${item.img_width!}"
                                                   img_height="${item.img_height!}"
                                                   img_path="${item.img_path!}"
                                                   <#if (input_map.crop_img_id! !="") && (input_map.crop_img_id?number == item.img_id)>checked </#if>
                                            >
                                            <span title="${item.img_name!}">${item.img_name!}</span>
                                        </div>
                                        <div class="img_mask">
                                            <#if  (item.img_url??)>
                                                <p>
                                                    <a class="old_pic" href="${item.img_url!}" target=_blank
                                                       title="显示原图">原图</a>
                                                    <a class="crop_image" url="${item.img_url!}"
                                                       img_id="${item.img_id!}"
                                                       img_width="${item.img_width!}"
                                                       img_height="${item.img_height!}"
                                                       img_path="${item.img_path!}"
                                                       title="裁剪图片">裁剪</a>
                                                    <a class="remove_image"
                                                       url="${item.img_url!}"
                                                       img_id="${item.img_id!}"
                                                       img_width="${item.img_width!}"
                                                       img_height="${item.img_height!}"
                                                       img_path="${item.img_path!}"
                                                       title="删除图片">删除</a>
                                                </p>
                                            </#if>
                                        </div>
                                        <div class="img_dim">
                                            <p style="text-align:center">${item.img_width!}
                                                x${item.img_height!}</p>
                                        </div>
                                        <div class="img_sel">
                                        </div>
                                    </li>
                                </#list>
                                <div class="clear"></div>
                            </ul>
                        </div>
                        <#if  (data_list?size > 0) >
                        <div class="box clearfix" style="margin-top: 15px">
                            <div class="some_tips">
                                ${page.pageInfo}
                            </div>
                            <div class="some_btn">
                                <a href="#"
                                   onClick="return gopage(1);">第一页</a>
                                <a href="#"
                                   onClick="return gopage(${page.prePage});">上一页</a>
                                <a href="#"
                                   onClick="return gopage( ${page.nextPage});">下一页</a>
                                <a href="#"
                                   onClick="return gopage(${page.lastPage});">最后一页</a>
                                <input id="page" name="page" type="text"
                                       value="${page.currentPage}" size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">页
                                <a href="#" style="width:46px;height: 30px;text-align: center;" onClick="gopage($('#page').val())" >跳转</a>
                            </div>
                        </div>
                            </#if>
                    </td>
                </tr>
            </table>
        </div>

    </form>
</div>


<div id="rMenu">
    <ul>
        <li id="m_add">创建目录</li>
        <li id="m_del">删除目录</li>
        <li id="m_rename">重命名</li>
    </ul>
</div>


<script>
var img_cat_arr =${img_cat_arr};
var has_version = <#if version??>true<#else>false</#if>;
</script>

<#noparse>
<script>

    function gopage(page) {
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>
</#noparse>

<script type="text/javascript" src="/js/Jcrop/js/jquery.Jcrop.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ajaxuploader/ajaxfileupload.js?v=1.0.5"></script>
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.all.js?v=1.0.5"></script>
<script language="JavaScript" src="/js/admin/lang/zh-CN/image_manager.js?v=1"></script>
<script language="JavaScript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script type="text/javascript" src="/js/admin/jCropRemoteImage.js?v=1.0.6"></script>
<script language="JavaScript" src="/js/admin/image_manager_dlg.js?v=1.0.5"></script>


<#include "/admin/footer.ftl">

<script>
    <#if (version??)>
    $('body').on('click', 'input[type="file"]', function (e) {
        var data = getPowerInfo('num_config', 'picture_num', '', '图片空间大小', 1, 0);
        if (data.tip == 1) {
            e.preventDefault();
        }
        console.log(data);
    });
    </#if>
</script>
