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
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span><a href="/admin/market/distribution/config">分销配置</a> / </span><span style="color: #666;">推广文案配置</span>
</div>
<div class="reserve-container">
    <form style="padding-bottom: 80px" action="/admin/market/distribution/goods/brokerage/detail?top_index=4" id="form1" method="post">
        {{csrf_field()!}
        <input type="hidden" name="act">
        {{--<div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>--!}
        <div class="passage_configd">
            <div class="config_tips">
                <img src="http://${image_domain!}/image/admin/notice_img.png" alt="">
                若系统开启分销员审核功能，则该文案展示在用户申请页面。或分销员可在分销中心邀请其他用户注册为分销员，此时普通用户通过邀请链接点击也是查看该推广文案。
            </div>
            <div class="config_details clearfix">
                <div class="cd_left">
                    <div class="show_names">${document['title']!}</div>
                    <img class="show_title" src="http://${image_domain!}/image/admin/shop_beautify/phone_tops.png" alt="">
                    <div class="show_pass"></div>
                </div>
                <div class="cd_right">
                    <ul>
                        <li class="clearfix tb-decorate-a">
                            <span class="item_titles">分享地址：</span>
                            <div class="url_content">
                                <input type="text" style='opacity: 0;position: absolute;' id="copy_url" value="pages/distributionspread/distributionspread">
                                <span>pages/distributionspread/distributionspread</span>
                                <a href="javascript:void(0)" onclick="copy('copy_url')">复制</a>
                                <a href="javascript:void(0)" class="hover_share" identity_id="" type="18" style="width: 70px">立即分享</a>
                            </div>
                        </li>
                        <li class="clearfix">
                            <span class="item_titles">页面标题：</span>
                            <input type="text" value="${document['title']!}" name="page_names" class="page_namess">
                        </li>
                        <li class="clearfix">
                            <span class="item_titles">页面内容：</span>
                            <a href="##" class="btn_use_default">使用模板文案</a>
                        </li>
                        <li class="clearfix">
                            <span class="item_titles"></span>
                            <div class="detail-edit-box">
                                <input hidden="hidden" class="default_document" value="${default['document']!}">
                                <textarea id="editor" name="content" style="width:200px;height:466px;" >${document["document"]!}</textarea>
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
    $('.show_pass').html($('#editor').val());

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
        var title = $('.page_namess').val();
        if (title == '') {
            util.mobile_alert('标题不能为空');
            return;
        }
        var param = {title:title,document:$('#editor').val()};

        util.ajax_json('/admin/market/distribution/copywriting/save',function (res) {
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
    
    $(".btn_use_default").click(function () {
        window.keditor.html($(".default_document").val());
        $('.show_pass').html($(".default_document").val());
    })
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>