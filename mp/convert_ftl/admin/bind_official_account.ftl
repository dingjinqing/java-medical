<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/wx_auth_success.css?v=1.0.0" type="text/css"/>
<style type="text/css">
    #official-list {
        width: 160px;
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        color: #333;
        font-size: 14px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title" style="margin: 0;">
        <span>授权列表/ </span>
        <span style="color: #666;">绑定公众号</span>
    </div>
    <div class="main-container">
        <div class="wx_auth_success clearfix" style="line-height: 50px; padding: 5px 20px;">
            <#if  ($mpShop && $mpShop->is_auth_ok)
            <form action="/admin/public/service/bind/official" method="post" id="form">
                <input type="hidden" name="is_bind" value="1"/>
                {{ csrf_field()!}
                <#if  (!empty($list))
                请选择需要绑定的公众号
                <select id="official-list" name="app_id">
                    <#list  ($list as $item)
                        <#if  ($item['bind_open_app_id'])
                            <option value="${item['app_id']!}">${item['nick_name']!}</option>
                        </#if>
                    </#list>
                </select>
                <input class="btn btn-primary queren" type="submit" value="确定" style="margin-left: 20px; background: #5A8BFF;">
                <div>仅可以绑定和该店铺同主体的公众号，绑定后不可以更换，请您谨慎选择</div>
                    <#else>
                <div>暂无公众号可绑定， 立即<a href="/wechat/official/account/authorization" target="_blank" style="color: #5A8BFF;">添加授权</a></div>
                </#if>
            </form>
                <#else>
                <a href="/wechat/no/authorization" style="color: #5A8BFF;">请先前往小程序授权</a>
            </#if>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">