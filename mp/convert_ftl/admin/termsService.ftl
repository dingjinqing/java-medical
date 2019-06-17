<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.8" type="text/css" />
<style type="text/css">
    .search_res ul .re_li{
        width: 275px;
    }

    .search_res ul .re_li span{
        width: 65px;
        margin-right: 10px;
    }
    .btn_found{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        height: 30px;
        line-height: 25px;
        width: 70px;
    }
    .btn_found:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .btn_found:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .ke-container{
        width: 470px !important;
    }
    .show_pass{
        /*height:520px;*/
        overflow-y: auto;
        padding:0 8px;
    }
    .cd_left{
        position: relative;
    }
    .cd_left .show_names{
        width: 100%;
        position: absolute;
        top: 0;
        height: 55px;
        color: #fff;
        text-align: center;
        line-height: 70px;
    }
    .tb-decorate-a {
        position: relative;
    }
    .preview_area{
        color: #c01110;
        font-size: 30px;
        position: absolute;
        top: 40%;
        left: 35%;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">基础配置</a> / </span><span><a href="/admin/market/distribution/config">交易配置</a> / </span><span style="color: #666;">服务条款配置</span>
</div>
<div class="reserve-container">
    <form style="padding-bottom: 80px" action="/admin/market/distribution/goods/brokerage/detail?top_index=4" id="form1" method="post">
        {{csrf_field()!}
        <input type="hidden" name="act">
        {{--<div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>--!}
        <div class="passage_configd">
            <div class="config_details clearfix">
                <div class="cd_left">
                    {{--<div class="show_names"></div>--!}
                    <img class="show_title" src="http://${image_domain!}/image/admin/shop_beautify/phone_tops.png" alt="">

                    <div class="show_pass">

                            <span class="preview_area">预览区</span>

                    </div>
                </div>
                <div class="cd_right">
                    <ul>
                        <li class="clearfix tb-decorate-a">
                            <span class="item_titles" style="width: 110px;">服务条款配置</span>
                        </li>
                        <li class="clearfix">
                            <span class="item_titles">条款内容：</span>
                        </li>
                        <li class="clearfix">
                            <span class="item_titles"></span>
                            <div class="detail-edit-box">
                                {{--<input hidden="hidden" class="default_document" value="${default['document']!}">--!}
                                <textarea id="editor" name="content" style="width:200px;height:466px;" >${document!}</textarea>
                            </div>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </form>
    <div class="pages_bottom " style="margin-left: 0">
        <a href="##" class="btn_to_saves">保存</a>
    </div>
</div>
<#include ('admin.share_common')
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="/js/kindeditor/lang/{{ config("app.locale")!}.js"></script>
<script type="text/javascript" src="/js/admin/kindeditor-init.js?v=1.0.1"></script>
<script type="text/javascript">
    function copy(input_id)
    {
        var obj = document.getElementById(input_id);
        console.log(obj)
        obj.select();
        var copy_res = document.execCommand("Copy");
        if (copy_res) util.mobile_alert('复制成功')
    }
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    $(document).ready(function () {
        initKindEditor("#editor", function () {
            // window.keditor.html();
        });
    });
    if($('#editor').val()){
        $('.show_pass').html($('#editor').val());
    }

    function getContent() {
        var fullHtml = '';
        var div;
        KindEditor.ready(function(K) {
            var editor = KindEditor.instances[0];
            fullHtml = editor.fullHtml();
            div = K.query('.ke-edit-iframe');
            editor.sync();
        });
        $('.show_pass').html(fullHtml);
    }
    var invite_document = $('#goods_desc').val();
    $(document).click(function () {
        getContent();
        if(invite_document != $('#editor').val()){
            //alert('修改过详情');
            hasSaved = false;
        }
    });

    $(".btn_to_saves").click(function () {
        getContent()
        // var title = $('.page_namess').val();
        // if (title == '') {
        //     util.mobile_alert('标题不能为空');
        //     return;
        // }
        var param = {document:$('#editor').val()};

        util.ajax_json('/admin/config/termsService/save',function (res) {
            if (res.error == 0) {
                util.mobile_alert('保存成功')
            } else {
                util.mobile_alert('保存失败')
            }
        },param)
    })

    $(".page_namess").change(function () {
        $(".show_names").html($(this).val());
    })
    

    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>