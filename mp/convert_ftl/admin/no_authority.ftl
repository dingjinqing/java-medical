<#include "/admin/header.ftl">
<style type="text/css">
    body{
        background: #fff;
        padding-left: 0;
    }
    .all{
        text-align: center;
        color: #999;
    }
    p{
        margin: 10px 0;
    }
    .all div{
        width: 85px;
        height: 30px;
        line-height: 30px;
        color: #fff;
        background: #6387f9;
        margin: 0 auto;
        cursor: pointer;
    }
    .absolute-left-menu{
        display: none;
    }
</style>
<div class="all">
    <img src="http://${image_domain!}/image/admin/no_authority.png"  style="width: 320px;" />
    <#if ($type == 'subaccount')
        <#if ($left == 1)
            <style>
                .absolute-left-menu{
                    display: block;
                }
            </style>
        </#if>
        <p style="font-size: 16px;margin-top: 20px">${text!}</p>
    <#else>
    <p style="font-size: 16px;margin-top: 20px">您当前版本暂无该功能使用权限</p>
    <div style="margin-top: 20px;border-radius:4px">了解更多</div>
    </#if>
</div>


<#include "/admin/footer.ftl">
<script type="text/javascript">
    var window_hei = $(window).height();
    var head_hei = $('#header').height();
    var all_hei = $('.all').height();
    var all_pad = (window_hei-head_hei-all_hei)/2;
    $('body').height(window_hei-head_hei);
    $('.all').css('padding-top',all_pad);
    var mod = util.getUrlParam('mod_name');
    if(mod == '商品数量' || mod == '装修页面数量' || mod == '门店数量' || mod == '表单数量'){
        $('p').html('您当前版本使用数量已达最大限制');
    }
    $('.all div').click(function(){
       window.location.href = '/admin/version/notice?mod_name='+ mod;
    });
</script>
