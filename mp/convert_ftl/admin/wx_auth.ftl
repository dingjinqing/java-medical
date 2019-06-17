<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/wx_auth.css?v=1.0.1" type="text/css"/>

<div style="min-width: 1090px;">
    <div class="title" style="margin: 0;">
        <span>小程序管理/ </span>
        <span style="color: #666;">小程序授权</span>
    </div>
    <div class="main-container">
        <div class="wx_auth clearfix">
            <div class="auth_list">
                <div class="auth_list_top">绑定已有小程序</div>
                <div class="auth_list_content">
                    <div class="content_text">
                        <p>我已经拥有小程序</p>
                        <p>小程序管理员可将小程序一键授权给我们</p>
                    </div>
                </div>
                <div class="auth_footer">
                    <a href="/wechat/start/authorization"><button>我已有小程序，一键授权</button></a>
                </div>
            </div>
            <div class="auth_list">
                <div class="auth_list_top">注册小程序</div>
                <div class="auth_list_content">
                    <div class="content_text">
                        <p>我还没有小程序</p>
                        <p>去微信平台申请小程序</p>
                        <a href="https://mp.weixin.qq.com/debug/wxadoc/introduction/index.html?t=2018227" target="_blank">查看教程</a>
                    </div>
                </div>
                <div class="auth_footer">
                    <a href="https://mp.weixin.qq.com/"
                       target="_blank" ><button>去微信平台申请小程序，立即申请</button></a>
                </div>
            </div>
        </div>
    </div>
</div>


<#include "/admin/footer.ftl">