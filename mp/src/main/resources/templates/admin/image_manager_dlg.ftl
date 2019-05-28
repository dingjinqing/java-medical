@include("admin.header")
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

    <form @if($action!='') action="/admin/public/image/account/dialog" @else action="/admin/frame/image/dialog" @endif name="form1"
          id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="set_cat_id" id="set_cat_id" value="">
        <input type="hidden" name="crop_img_id" id="crop_img_id" value="">
        <input type="hidden" name="no_full" id="no_full" value="{{ $no_full }}">
        <input type="hidden" name="need_img_width" id="need_img_width" value="{{ $need_img_width or 0 }}">
        <input type="hidden" name="need_img_height" id="need_img_height" value="{{ $need_img_height or 0 }}">
        <input type="hidden" name="on_img_cb" id="on_img_cb" value="{{  $on_img_cb }}">

        <input type="hidden" name="op_cat_id" id="op_cat_id" value="">
        <input type="hidden" name="op_cat_name" id="op_cat_name" value="">
        <input type="hidden" name="op_cat_pid" id="op_cat_pid" value="">
        {{ csrf_field() }}

        <div id="image_list" style="min-width: 600px;">
            <table class="image-list-tbl">
                <tr>
                    <td colspan="2" style="padding: 0;">
                        <div class="box btn_chppse_bendi clearfix">
                            <input type="button" class="bt_success" name="up_image_btn" id="up_image_btn"
                                   title="{{ trans("admin/image.uploadImageTip") }}"
                                   value="上传图片">
                            @if($version)
                                <div class="system_info" style="display: inline-block;float: none;margin-top: 0px;">
                                    <p class="system_info_prompt">
                                        @if($version['self']['num']<0)
                                            当前版本为{{$version['self']['version_name']}}，<span>不限制</span>内存空间
                                        @else
                                            当前版本为{{$version['self']['version_name']}}，剩余
                                            <span>{{$version['self']['num']-$version['self']['use'] >0 ? $version['self']['num']-$version['self']['use'] : 0}}</span>
                                            M内存空间
                                        @endif
                                        <img src="http://{{$image_domain}}/image/admin/system_icon.png"/>
                                    </p>
                                    <img src="http://{{$image_domain}}/image/admin/system_shadow.png"
                                         class="system_shadow"/>
                                </div>
                            @endif
                            <div class="text-warning" style="width: 94%;margin: 0px 0 0 10px;">
                                <img src="http://{{$image_domain}}/image/admin/notice_img.png" alt="">
                                {{ trans("admin/image.uploadImageTip") }}
                            </div>
                        </div>
                        <div class="system_info_content" style="top: 56px;left: 134px;">
                            <div class="system_info_content_top">
                                @if($version['all'])
                                    @foreach($version['all'] as $k=>$ver)
                                        @if($ver['num']<0)
                                            @if($k!=0)，@endif{{$ver['version_name']}}<span class="system_v1">不限制</span>
                                            内存空间
                                        @else
                                            @if($k!=0)，@endif{{$ver['version_name']}}<span
                                                    class="system_v1">{{$ver['num']}}</span>M内存空间
                                        @endif
                                    @endforeach
                                @endif
                                {{--高级版最多创建<span class="system_v2">20</span>M内存空间，旗舰版<span class="system_v3">20</span>M内存空间--}}
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
                            <input name="img_cat_id" type="hidden" value="{{ $img_cat_id or 0 }}" id="img_cat_id">
                        </div>
                    </td>
                    <td style="vertical-align:top!important">
                        <div class="box yigehezi">

                            <div class="hide">
                                <span>{{ trans("admin/image.upload_date") }}</span>
                                <input name="start_rq" type="text" value="{{ $start_rq }}" onclick="return picker();"
                                       size=10>
                                <input name="end_rq" type="text" value="{{ $end_rq }}" onclick="return picker();"
                                       size=10>
                                <span>{{ trans("admin/image.img_wh") }}</span>&nbsp;
                                <input name="img_width" type="text" value="{{ $img_width }}" size=5>
                                <input name="img_height" type="text" value="{{ $img_height }}" size=5>
                            </div>
                            <select name="upload_sort_id" id="upload_sort_id">
                                @foreach($upload_sort_list as $key => $item)
                                    <option value="{{ $key }}"
                                            @if ($upload_sort_id == $key) selected="selected" @endif >{{ $item }}</option>
                                @endforeach
                            </select>

                            <input name="keywords" type="text" value="{{ $keywords }}" size=12>
                            <input name="show_type" id="show_type" type="hidden" value="{{ $show_type }}">
                            <input type='submit' name="search" class="btn_sech"
                                   value="{{ trans("admin/image.search") }}">

                            @if ($need_img_width > 0 || $need_img_height > 0)
                                <label style="font-size: 12px"
                                       title="{{ trans("admin/image.need_image_condition") }}： @if ($need_img_width > 0){{ trans("admin/image.width") }}={{ $need_img_width }}px @endif @if ($need_img_height > 0) {{ trans("admin/image.height") }}={{ $need_img_height }}px @endif">
                                    <input type='checkbox' id="search_need" name="search_need"
                                           @if ($search_need) checked @endif value="1">
                                    @if ($need_img_width > 0){{ $need_img_width }}px @endif
                                    x @if ($need_img_height > 0) {{ $need_img_height }}px @endif
                                </label>
                            @endif

                            <p></p>


                        </div>

                        <div class="box img-list">
                            @if (count($data_list) == 0)
                                <div class="text-warning padding-top-10" style="text-align: center;width: 100%;height: 150px;margin-top: 50px">
                                    <img src="http://{{$image_domain}}/image/admin/image_no_data.png" alt="" style="margin-top: 20px">
                                    <p style="color:#999;font-size: 14px;text-align: center;margin-top: 15px">当前文件夹未找到符合要求的图片</p>
                                </div>
                            @endif

                            <ul id="bigimg_data" class="clearfix">
                                @foreach($data_list as $item)
                                    <li p50 title="{{ $item->img_name }}">
                                        <div class="box_img" align="center">
                                            <img class="img-thumbnail"
                                                 src="@if ($item->img_url){{ $item->img_url }}!middle @endif">
                                        </div>

                                        <div class="box_desc">
                                            <input cbx_bigimg type="checkbox" name="cbx_img2[]"
                                                   value="{{ $item->img_id }}"
                                                   url="{{ $item->img_url }}"
                                                   img_name="{{ $item->img_name }}"
                                                   img_size="{{ $item->img_size }}"
                                                   img_width="{{ $item->img_width }}"
                                                   img_height="{{ $item->img_height }}"
                                                   img_path="{{ $item->img_path }}"
                                                   @if ($crop_img_id == $item->img_id)checked @endif
                                            >
                                            <span title="{{ $item->img_name }}">{{ $item->img_name }}</span>
                                        </div>
                                        <div class="img_mask">
                                            @if ($item->img_url)
                                                <p>
                                                    <a class="old_pic" href="{{ $item->img_url }}" target=_blank
                                                       title="{{ trans("admin/image.show_original_image_tip") }}">{{ trans("admin/image.original_image") }}</a>
                                                    <a class="crop_image" url="{{ $item->img_url }}"
                                                       img_id="{{ $item->img_id }}"
                                                       img_width="{{ $item->img_width }}"
                                                       img_height="{{ $item->img_height }}"
                                                       img_path="{{ $item->img_path }}"
                                                       title="{{ trans("admin/image.crop_image_tip") }}">{{ trans("admin/image.crop") }}</a>
                                                    <a class="remove_image"
                                                       url="{{ $item->img_url }}"
                                                       img_id="{{ $item->img_id }}"
                                                       img_width="{{ $item->img_width }}"
                                                       img_height="{{ $item->img_height }}"
                                                       img_path="{{ $item->img_path }}"
                                                       title="{{ trans("admin/image.del_image_tip") }}">{{ trans("admin/image.del") }}</a>
                                                </p>
                                            @endif
                                        </div>
                                        <div class="img_dim">
                                            <p style="text-align:center">{{ $item->img_width }}
                                                x{{ $item->img_height }}</p>
                                        </div>
                                        <div class="img_sel">
                                        </div>
                                    </li>
                                @endforeach
                                <div class="clear"></div>
                            </ul>
                        </div>
                        @if ($data_list->count())
                        <div class="box clearfix" style="margin-top: 15px">
                            <div class="some_tips">
                                {{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),]) }}
                            </div>
                            <div class="some_btn">
                                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page") }}</a>
                                <a href="##"
                                   onClick="return gopage({{ $data_list->currentPage() -1 }});">{{ trans("admin/common.page.pre_page") }}</a>
                                <a href="##"
                                   onClick="return gopage({{ $data_list->currentPage() + 1 }});">{{ trans("admin/common.page.next_page") }}</a>
                                <a href="##"
                                   onClick="return gopage({{ $data_list->lastPage()}});">{{ trans("admin/common.page.last_page") }}</a>
                                <input id="page" name="page" type="text" value="{{ $data_list->currentPage() }}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"/>{{ trans("admin/common.page.page") }}
                                <a href="#" style="width:46px;height: 30px;text-align: center;" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page") }}</a>
                            </div>
                        </div>
                            @endif
                    </td>
                </tr>
            </table>
        </div>

    </form>
</div>

<div id="rMenu">
    <ul>
        <li id="m_add">{{ trans("admin/image.create_dir") }}</li>
        <li id="m_del">{{ trans("admin/image.del_dir") }}</li>
        <li id="m_rename">{{ trans("admin/image.rename_dir") }}</li>
    </ul>
</div>

<script>
    var img_cat_arr =@json($img_cat_arr);

    function gopage(page) {
        var last_page = '{{ $data_list -> lastPage() }}';
        if (parseInt(page) > parseInt(last_page)) {
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
<script type="text/javascript" src="/js/ajaxuploader/ajaxfileupload.js?v=1.0.5"></script>
<script type="text/javascript" src="/js/zTree/js/jquery.ztree.all.js?v=1.0.5"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale") }}/image_manager.js?v=1"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale") }}/image_common.js"></script>
<script type="text/javascript" src="/js/admin/jCropRemoteImage.js?v=1.0.6"></script>
<script language="JavaScript" src="/js/admin/image_manager_dlg.js?v=1.0.5"></script>


@include("admin.footer")
<script>
    @if($version)
    $('body').on('click', 'input[type="file"]', function (e) {
        var data = getPowerInfo('num_config', 'picture_num', '', '图片空间大小', 1, 0);
        if (data.tip == 1) {
            e.preventDefault();
        }
        console.log(data);
    });
    @endif
</script>
