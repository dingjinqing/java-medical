<style type="text/css">
    .absolute-left-menu{
        display: none;
    }
</style>
<#include "/admin/header.ftl">
<style type="text/css">
    body {
        padding: 0px;
    }
    .question_success_container{
        width: 100%;
        height: auto;
        min-height: 700px;
    }
    .success_container{
        width: 80%;
        height: auto;
        min-height: 600px;
        background-color: white;
        margin: 85px auto;
        position: relative;
        top: 20px;
        display: flex;
        flex-direction:column;
        justify-content: center;
        align-items: center;
    }
    .success_img{
        width: 400px;
        height: 200px;
    }
    .success_img>img{
        width: 100%;
        height: 100%;
    }
    .success_text{
        margin-top: 60px;
        font-size: 20px;
        color: #333;
    }
    .success_back{
        margin-top: 18px;
        font-size: 14px;
    }
    .success_back>a{
        color: #5A8BFF;
        text-decoration: none;
    }
    .success_back>a:hover{
        color: #5A8BFF;
        text-decoration: underline;
    }
</style>
<div class="question_success_container">
    <div class="success_container">
        <div class="success_img"><img src="/image/admin/question_success.png"></div>
        <div class="success_text">感谢您的反馈！我们会努力做得更好！</div>
        <div class="success_back"><#if ($cate == 0)<a href="/admin/survey/overview"> <#else><a href="/admin/account/shop/select"></#if>返回首页&nbsp;></a></div>
    </div>
</div>
<#include "/admin/footer.ftl">
