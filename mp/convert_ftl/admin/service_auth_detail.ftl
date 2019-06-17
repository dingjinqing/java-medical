<#include "/admin/toggle_header.ftl">
<link rel="stylesheet" href="/css/admin/wx_auth_success.css?v=1.0.0" type="text/css"/>
<style type="text/css">
    .prompt_mes{
        margin-top: -4px;
        cursor: pointer;
    }
    .detail_mes{
        width: 280px;
        padding: 10px;
        border: 1px solid #ccc;
        font-size: 12px;
        position: absolute;
        margin: -80px 0 0 300px;
        display: none;
    }
    .detail_mes_top{
        border-bottom: 1px solid #eee;
        padding-bottom: 10px;
    }
    .detail_mes p{
        font-weight: bold;
        text-align: center;
    }
    ul li{
        float: none;
    }
    .again_sq{
             color: #5a8bff;
             text-decoration: none;
         }
    .again_sq{
        color: #5a8bff;
        text-decoration: underline;
    }
    .again_sq{
        color: #5a8bff;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title" style="margin: 0;">
        <span>授权列表/ </span>
        <span style="color: #666;">服务号详情</span>
    </div>
    <div class="main-container">
        <div class="wx_auth_success clearfix">
            <div class="auth_info">
                <ul>
                    <li>
                        <span>公众微信号：</span>
                        <span>${officialAccount->app_id!}</span>
                    </li>
                    <li>
                        <span>公众号昵称：</span>
                        <span>${officialAccount->nick_name!}</span>
                    </li>
                    <li>
                        <span>公众号二维码：</span>
                        <img src="{{ image_url($officialAccount->qrcode_url)!}" width="100" alt=""/>
                    </li>
                    <li>
                        <span>主体名称：</span>
                        <span>${officialAccount->principal_name!}</span>
                    </li>
                    <li>
                        <span>公众号类型：</span>
                        <span>
                            <#if  ($officialAccount->account_type == 0)
                                订阅号
                                <#elseif> ($officialAccount->account_type == 1)
                                微信认证订阅号
                                <#elseif> ($officialAccount->account_type == 2)
                                服务号
                                <#elseif> ($officialAccount->account_type == 3)
                                微信认证服务号
                            </#if>
                        </span>
                        <a href="/wechat/official/account/authorization" target="_blank" class="again_sq">重新授权</a>
                        <img src="http://${image_domain!}/image/admin/analysis_tishi.png" class="prompt_mes" />
                        <div class="detail_mes" style="margin: -80px 0 0 400px;">
                            <div class="detail_mes_top">
                                如果你的公众号已成功升级（从未认证升为认证号，或从订阅号升为服务号），请点击重新授权
                            </div>
                            <div>
                                <p>如何升级：</p>
                                如需对公众号进行微信认证，请登录“微信公众平台-> 公众号设置”，在“认证情况”栏目，点击申请微信认证
                            </div>
                        </div>
                    </li>
                    <li>
                        <span>授权状态：</span>
                        <span>
                            <#if  ($officialAccount->is_auth_ok == 1)
                                已授权
                            <#else>
                                已取消
                            </#if>
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $('.prompt_mes').hover(function () {
        $('.detail_mes').show();
    },function () {
        $('.detail_mes').hide();
    });
</script>

<#include "/admin/footer.ftl">