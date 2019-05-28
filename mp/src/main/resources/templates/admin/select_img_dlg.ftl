@include("admin.header")
<link href="/js/jquery-ui-1.11.1/jquery-ui.css" rel="stylesheet">
<script language="JavaScript" src="/js/jquery-ui-1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="/css/admin/image_manager.css?v=1.0.4" type="text/css"/>


<style>
    html, body {
        margin: 0;
        padding: 0;
        background: #fff;
        width:100%;
        overflow: hidden;
    }
</style>


<input type="hidden" name="img_max_num" id="img_max_num" value="{{ $img_max_num or 1 }}">
<input type="hidden" name="need_img_width" id="need_img_width" value="{{ $need_img_width or 0 }}">
<input type="hidden" name="need_img_height" id="need_img_height" value="{{ $need_img_height or 0 }}">
<input type="hidden" name="show_img_sort" id="show_img_sort" value="{{ $show_img_sort or 1 }}">

<input type="hidden" name="on_img_cb" id="on_img_cb" value="{{ $on_img_cb }}">


<iframe class="img_iframe" @if($account) src="/admin/public/image/account/dialog?on_img_cb=on_img_cb&no_full=1&need_img_width={{ $need_img_width }}&need_img_height={{ $need_img_height }}" @else
src="/admin/frame/image/dialog?on_img_cb=on_img_cb&no_full=1&need_img_width={{ $need_img_width }}&need_img_height={{ $need_img_height }}"@endif
        style="width:100%;height:420px;border:none;">
</iframe>

<div class="select-img-list"
     @if ($max_img_num == 1 || $max_img_num != 1 && $show_img_sort==0)style="display: none" @endif>
    <p>已选<span class="img-num">0</span>张图片，拖动可修改插入顺序</p>
    <ul id="bigimg_data" class="select-img-data clearfix">

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
        <div class="img_mask">
            <p>
                <a class="old_pic" href="" target=_blank title="显示原图">原</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a class="remove_image" href="javascript:void(0);" title="移除图片">X</a>
            </p>
        </div>
        <div class="img_dim">
            <p style="text-align:center"></p>
        </div>
        <div class="img_sel">
        </div>
    </li>
</div>

<script language="JavaScript" src="/js/admin/select_img_dlg.js?v=1.0.4"></script>


@include("admin.footer")
