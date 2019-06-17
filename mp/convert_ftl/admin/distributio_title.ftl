<div class="pages_nav clearfix">
    <ul class="clearfix">
        <li class="distribution_config <#if ($tab_nav==0) actives </#if>">
            <a href="/admin/market/distribution/config?top_index=4">分销配置</a>
        </li>
        <li class="distribution_level_config <#if ($tab_nav==1) actives </#if>">
            <a href="/admin/market/distribution/distributer/level/config?top_index=4">分销员等级配置</a>
        </li>
        <li class="distribution_config <#if ($tab_nav==2) actives </#if>">
            <a href="/admin/market/distribution/strategy/list?top_index=4&nav=1">返利策略配置</a>
        </li>
        <li class="distributer_list <#if ($tab_nav==3) actives </#if>">
            <a href="/admin/market/distribution/distributer/list?top_index=4">分销员列表</a>
        </li>
        <li class="distributer_group <#if ($tab_nav==4) actives </#if>">
            <a href="/admin/market/distribution/group?top_index=4">分销员分组</a>
        </li>
        <li class="commision_count <#if ($tab_nav==5) actives </#if>">
            <a href="/admin/market/distribution/brokerage/detail?top_index=4">佣金统计</a>
        </li>
        <li class="rebate_count <#if ($tab_nav==6) actives </#if>">
            <a href="/admin/market/distribution/goods/brokerage/detail?top_index=4">商品返利统计</a>
        </li>
        <li class="distribution_widthdraw <#if ($tab_nav==7) actives </#if>">
            <a href="/admin/market/distribution/widthdraw?top_index=4">返利提现审核</a>
        </li>
        <#if ($fanli_cfg['judge_status'] == 1)
            <li class="distributer_check <#if ($tab_nav==8) actives </#if>">
                <a href="/admin/market/distribution/distributer/check?top_index=4">分销员审核</a>
            </li>
            {{--<li class="distributer_check" >
                <a href="/admin/market/distribution/copywriting?top_index=4">推广文案配置</a>
            </li>--!}
        </#if>

        <li class="promotion_language <#if ($tab_nav==9) actives </#if>">
            <a href="/admin/market/distribution/promotionLanguage/list?top_index=4">分销推广语</a>
        </li>
    </ul>
</div>