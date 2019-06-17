<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/lottery_manage.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .lottery_table td{
        padding:15px 0;
    }
    .lottery_table{
        margin-top: 10px;
    }
    .paging_footer{
        margin-top: 0px;
    }
    .prize{
        display: inline-block;
        margin-right: 5px;
        max-width: 100px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        vertical-align: middle;
    }
</style>
<div class="main-container">
    <div class="title">
        {{--<span>营销管理</span><span>幸运大抽奖 / </span><span style="color: #666;">抽奖明细</span>--!}
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>
            <#if ($source == 'login')<a href="/admin/market/activityreward/list?top_index=4&nav=1">活动有礼</a>
            <#elseif>($source == 'pay')<a href="/admin/market/payreward/list?top_index=4&nav=1">支付有礼</a>
            <#else><a href="/admin/market/lottery/list?top_index=4&nav=1">幸运大抽奖</a>
            </#if>/ ${lottery_name!} /</span><span style="color: #666;">抽奖明细</span>
    </div>
    <form action="/admin/market/lottery/detail?top_index=4&id=${options['id']!}&sub_index=${sub_index!}" method="post" id="form1">
        {{csrf_field()!}
        <ul class="search_inputs">
            <li class="clearfix">
                <div>
                    <span>手机号</span>
                    <input name="mobile" type="text" value="${options['mobile']!}" placeholder="请输入手机号">
                </div>
                <div>
                    <span>用户昵称</span>
                    <input name="user_name" type="text" value="${options['user_name']!}" placeholder="请输入用户昵称">
                </div>
                <#if ($source)
                    <input hidden name="lottery_source" value="${options['lottery_source']!}">
                <#else>
                    <div>
                        <span>抽奖来源</span>
                        <select name="lottery_source">
                            <option value="-1">全部</option>
                            <option <#if ($options['lottery_source'] == "0") selected </#if> value="0">分享</option>
                            <option <#if ($options['lottery_source'] == "1") selected </#if> value="1">活动有礼</option>
                            <option <#if ($options['lottery_source'] == "2") selected </#if> value="2">支付有礼</option>
                        </select>
                    </div>
                </#if>
                <input hidden name="source" value="${source!}">
                <input hidden name="act_id" value="${act_id!}">
                <div>
                    <span>抽奖次数来源</span>
                    <select name="chance_source">
                        <option value="-1">全部</option>
                        <option <#if ($options['chance_source'] == "0") selected </#if> value="0">免费</option>
                        <option <#if ($options['chance_source'] == "1") selected </#if> value="1">分享</option>
                        <option <#if ($options['chance_source'] == "2") selected </#if> value="2">积分</option>
                    </select>
                </div>


            </li>
            <li class="clearfix">
                <div>
                    <span>是否中奖</span>
                    <select name="lottery_grade">
                        <option value="-1">全部</option>
                        <option <#if ($options['lottery_grade'] == "0") selected </#if> value="0">${lottery_level[0]!}</option>
                        <option <#if ($options['lottery_grade'] == "1") selected </#if> value="1">${lottery_level[1]!}</option>
                        <option <#if ($options['lottery_grade'] == "2") selected </#if> value="2">${lottery_level[2]!}</option>
                        <option <#if ($options['lottery_grade'] == "3") selected </#if> value="3">${lottery_level[3]!}</option>
                        <option <#if ($options['lottery_grade'] == "4") selected </#if> value="4">${lottery_level[4]!}</option>
                    </select>
                </div>
                <div>
                    <a href="##" class="btn_searchs">查询</a>
                </div>
            </li>
        </ul>
        <div class="lottery_table">
            <table width="100%">
                <thead>
                    <td width="15%">用户昵称</td>
                    <td width="15%">手机号码</td>
                    <td width="20%">抽奖时间</td>
                    <#if (!$source)
                        <td width="10%">抽奖来源</td>
                    </#if>
                    <td width="10%">抽奖次数来源</td>
                    <td width="10%">是否中奖</td>
                    <td width="20%">奖品</td>
                </thead>
                <tbody>
                    <#if ($data_list)
                    <#list ($data_list as $value)
                        <tr>
                            <td>${value->username!}</td>
                            <td>${value->mobile!}</td>
                            <td>${value->add_time!}</td>
                            <#if (!$source)
                                <td>
                                    <#if ($value->lottery_source == 1)活动有礼
                                    <#elseif>($value->lottery_source == 2)支付有礼
                                    <#else>分享
                                    </#if>
                                </td>
                            </#if>
                            <td>
                                <#if ($value->chance_source == 1)分享获得
                                <#elseif>($value->chance_source == 2)消耗${value->lottery_cost!}积分
                                <#else>免费
                                </#if>
                            </td>
                            <td>${value->lottery_grade_name!}</td>
                            <#if ($value->lottery_type !=4)
                            <td><span class="prize">${value->lottery_award!}</span>
                            </td>
                            <#else>
                            <td><span class="prize">${value->goods_name!}</span>
                                <#if ($value->present_status == 1)
                                <a href="/admin/orders/manage/info?order_sn=${value->order_sn!}" style="color:#5a8bff;display:inline-block;vertical-align: middle;text-decoration: none;margin-bottom: 0">查看订单</a>
                                    <#elseif>($value->present_status == 0)未领取
                                    <#else>已过期
                                </#if>
                                </td>
                            </#if>
                        </tr>
                    </#list>
                    </#if>
                </tbody>
            </table>
        </div>
        <#if ($data_list ->count())
        <div class="paging_footer">
          <#include "/admin/jump_page_admin.ftl">
        </div>
        <#else>
    <div class="paging_footer" style="    margin-top: -334px;width: 97%;margin-left: 23px;">
            <#include "/admin/jump_page_admin.ftl">
        </div>
        </#if>
    </form>
</div>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script>

    $(".btn_searchs").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    })

    getPowerInfo('main_config','lottery','sub_4','抽奖',0);
</script>