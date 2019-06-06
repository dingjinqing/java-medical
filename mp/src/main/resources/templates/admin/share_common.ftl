<style>
    .hover_share{
        display: inline-block !important;
        width: 30px;
        color: #5a8bff !important;
    }
    .hover_share:hover{
        color: #5A8BFF;
    }
    .share_span{
        background: #fff;
        position: fixed;
        margin: auto;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        z-index: 9;
        width: 320px;
        height: 300px;
        box-shadow:0px 1px 9.6px 2.4px #dddddd;
        border-radius: 4px;
        text-align: left;
        display: none;
    }
    .share_head{
        text-align: center;
        padding: 12px 10px;
        position: relative;
    }
    .share_head span{
        color: #333;
        font-size: 14px;
    }
    .share_head  .share_sj{
        position: absolute;
        right: 20px;
        top: 16px;
        cursor: pointer;
    }

    .share_span .code_imgs{
        display: block;
        margin:0 auto;
    }
    .share_span a{
        color: #999;
        font-size: 14px;
        display: inline-block;
        height: 40px;
        line-height: 40px;
        width: 100%;
        text-align: center;
        margin-left: 0;
        border-bottom: 1px solid #eee;
    }
    .share_bottom{
        padding:10px 15px;
        width: 100%;
    }
    .share_bottom input{
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }
    .share_bottom button{
        margin-left: 20px;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
    }

</style>
<div class="share_span">

    <div class="share_head">
        <span>扫一扫，分享给好友吧~</span>
        <img src="http://${image_domain!}/image/admin/share_close.png" class="share_sj">
    </div>
    <div class="share_middle">
        <img src="http://${image_domain!}/image/admin/T2P489_20190402153839.jpg" alt="" width="160px;height:160px" class="code_imgs">
        <a href="##" download=""  class="down_imgs">下载二维码</a>
    </div>
    <div class="share_bottom">
        <input type="text" value="" id="fe_text" />
        <button class="btn_copy" id="d_clip_button" data-clipboard-target="fe_text">复制</button>
    </div>
</div>
<#noparse>
<script>
    function hasAttr(selector,attr){
        if(typeof($(selector).attr(attr)) != "undefined" ){
            return true;
        }
    }
    $(".hover_share").click(function(){
        var identity_id = $(this).attr('identity_id');
        var type = $(this).attr('type');
        var extend_info = $(this).attr('extend_info');
        $(".share_span").hide();
        util.ajax_json('/admin/ajax/common/getqrcode',function(d){
            if(d&&d.error==0){
                $(".share_span").find('.code_imgs').attr("src",d.content.qrcode_img);
                $(".share_span").find('.down_imgs').attr("href",d.content.qrcode_img);
                $(".share_span").find("#fe_text").val(d.content.type_url);
                $(".share_span").show();
            }else{
                util.mobile_alert(d.message);
            }
        },{type : type ,identity_id : identity_id,extend_info : extend_info});
    });
    $('.share_sj').click(function(){
        $('.share_span').hide();
    })
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>
</#noparse>