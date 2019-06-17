<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.2" type="text/css" />
<style>
    #off_status{
        display: none;
    }
    #save_info{
        display: none;
    }
    .pop_up_container{
        padding: 0 16px;
        border-bottom: 1px solid #eee;
    }
    .off_status_container p{
        margin-bottom: 14px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;">分销员等级配置</span>
</div>
<form method="post">
    {{ csrf_field()!}
{{--    {{dd($level_number)!}--!}
    <div class="reserve-container">
        <div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>
        <div class="level_container clearfix">
            <div class="prompt">
                <img src="http://${image_domain!}/image/admin/notice_img.png" alt="">
                <span>提示：每次修改分销员等级，将会有大量分销员受到影响，请谨慎操作</span>
            </div>
            <div class="level_list">
                <table class="level_table" width="100%">
                    <thead>
                        <tr>
                            <th width="10%">等级</th>
                            <th width="20%">等级名称</th>
                            <th width="35%">升级规则</th>
                            <th width="15%">分销员数量</th>
                            <th width="20%">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- 一级 -->
                        <tr>
                            <td>一级</td>
                            <td class="level_name">
                                <input type="text" name="level_name_1" value="{{!$level_data[1]->level_name ? '普通分销员' : $level_data[1]->level_name!}" id="level_name_1" onkeyup="limitLength(value,20,'','level_name_1')">
                            </td>
                            <td class="level_rule">成为分销员后，默认即是该等级</td>
                            <td class="distributer_num"><a href="/admin/market/distribution/distributer/list?top_index=4&all_users=1&level_id=1">${level_number[1] ?? 0!}</a></td>
                            <td><span>已启动</span></td>
                        </tr>
                        <!-- 二级 -->
                        <tr>
                            <td>二级</td>
                            <td class="level_name"><input type="text" name="level_name_2" value="${level_data[2]->level_name!}" id="level_name_2" onkeyup="limitLength(value,20,'','level_name_2')"></td>
                            <!-- 规则切换 -->
                            <td class="level_rule">
                                <div class="rule_switch">
                                    <input type="radio" class="level_up_route" name="up_route_2" checked value="0" data-type="auto">自动升级
                                    <input type="radio" class="level_up_route" name="up_route_2" <#if ($level_data[2]->level_up_route == 1) checked </#if> value="1">手动升级
                                </div>
                                <div class="rule_info">
                                    <!-- 手动 -->
                                    <div class="add_distributer"  <#if ($level_data[2]->level_up_route != 1) style="display:none;" </#if>>
                                        <#if ($level_data[2]->level_status == 1)
                                            <a level_id="2" user_ids="${level_data[2]->level_user_ids!}" href="javascript:;">+ 添加分销员</a>
                                            <input type="text" hidden name="user_ids_2" value="${level_data[2]->level_user_ids!}">
                                        </#if>
                                    </div>
                                    <!-- 自动 -->
                                    <div class="rule_conditions" <#if ($level_data[2]->level_up_route == 1) style="display:none;" </#if>>
                                        <p class="conditions_top">累计邀请用户数达
                                            <input type="text" name="invite_number_2" value="${level_data[2]->invite_number > 0 ? $level_data[2]->invite_number : ''!}">个</p>
                                        <p class="conditions_bottom"><span>或</span>累计推广金达
                                            <input type="text" name="distribution_money_2" value="${level_data[2]->total_distribution_money > 0 ? $level_data[2]->total_distribution_money : ''!}">元</p>
                                        <p class="conditions_bottom"><span>或</span>累积推广金与消费金总和达
                                            <input type="text" name="buy_money_2" value="${level_data[2]->total_buy_money > 0 ? $level_data[2]->total_buy_money : ''!}">元</p>
                                    </div>
                                </div>
                            </td>
                            <td class="distributer_num"><a href="/admin/market/distribution/distributer/list?top_index=4&all_users=1&level_id=2">${level_number[2] ?? 0!}</a></td>
                            <!-- 启/停状态切换 -->
                            <td class="status_switch">
                                <#if ($level_data[2]->level_status == 1)
                                    <div class="status_top"><span>已启动</span></div>
                                    <div class="status_bottom">
                                        <p class="status_off active" level_id="${level_data[2]->level_id!}"level_name="${level_data[2]->level_name!}" level_number="${level_number[2] ?? 0!}"><a href="javascript:;">停用</a></p>
                                    </div>
                                <#else>
                                    <div class="status_top"><span>已停用</span></div>
                                    <div class="status_bottom">
                                        <p class="status_on active" level_id="${level_data[2]->level_id!}"><a href="javascript:;">启用</a></p>
                                    </div>
                                </#if>
                            </td>
                        </tr>
                        <!-- 三级 -->
                        <tr>
                            <td>三级</td>
                            <td class="level_name"><input type="text" name="level_name_3" value="${level_data[3]->level_name!}" id="level_name_3" onkeyup="limitLength(value,20,'','level_name_3')"></td>
                            <td class="level_rule">
                                <div class="rule_switch">
                                    <input type="radio" class="level_up_route" name="up_route_3" checked value="0" data-type="auto">自动升级
                                    <input type="radio" class="level_up_route" name="up_route_3" <#if ($level_data[3]->level_up_route == 1) checked </#if> value="1">手动升级
                                </div>
                                <div class="rule_info">
                                    <!-- 手动 -->
                                    <div class="add_distributer"  <#if ($level_data[3]->level_up_route != 1) style="display:none;" </#if>>
                                        <#if ($level_data[3]->level_status == 1)
                                            <a level_id="3" user_ids="${level_data[3]->level_user_ids!}" href="javascript:;">+ 添加分销员</a>
                                            <input type="text" hidden name="user_ids_3" value="${level_data[3]->level_user_ids ?? ''!}">
                                        </#if>
                                    </div>
                                    <!-- 自动 -->
                                    <div class="rule_conditions" <#if ($level_data[3]->level_up_route == 1) style="display:none;" </#if>>
                                        <p class="conditions_top">累计邀请用户数达
                                            <input type="text" name="invite_number_3" value="${level_data[3]->invite_number > 0 ? $level_data[3]->invite_number : ''!}">个</p>
                                        <p class="conditions_bottom"><span>或</span>累计推广金达
                                            <input type="text" name="distribution_money_3" value="${level_data[3]->total_distribution_money > 0 ? $level_data[3]->total_distribution_money : ''!}">元</p>
                                        <p class="conditions_bottom"><span>或</span>累积推广金与消费金总和达
                                            <input type="text" name="buy_money_3" value="${level_data[3]->total_buy_money > 0 ? $level_data[3]->total_buy_money : ''!}">元</p>
                                    </div>
                                </div>
                            </td>
                            <td class="distributer_num"><a href="/admin/market/distribution/distributer/list?top_index=4&all_users=1&level_id=3">${level_number[3] ?? 0!}</a></td>
                            <td class="status_switch">
                                <#if ($level_data[3]->level_status == 1)
                                    <div class="status_top"><span>已启动</span></div>
                                    <div class="status_bottom">
                                        <p class="status_off active" level_id="${level_data[3]->level_id!}"level_name="${level_data[3]->level_name!}" level_number="${level_number[3] ?? 0!}"><a href="javascript:;">停用</a></p>
                                    </div>
                                <#else>
                                    <div class="status_top"><span>已停用</span></div>
                                    <div class="status_bottom">
                                        <p class="status_on active" level_id="${level_data[3]->level_id!}"><a href="javascript:;">启用</a></p>
                                    </div>
                                </#if>
                            </td>
                        </tr>
                        <!-- 四级 -->
                        <tr>
                            <td>四级</td>
                            <td class="level_name"><input type="text" name="level_name_4" value="${level_data[4]->level_name!}" id="level_name_4" onkeyup="limitLength(value,20,'','level_name_4')"></td>
                            <td class="level_rule">
                                <div class="rule_switch">
                                    <input type="radio" class="level_up_route" name="up_route_4" checked value="0" data-type="auto">自动升级
                                    <input type="radio" class="level_up_route" name="up_route_4" <#if ($level_data[4]->level_up_route == 1) checked </#if> value="1">手动升级
                                </div>
                                <div class="rule_info">
                                    <!-- 手动 -->
                                    <div class="add_distributer"  <#if ($level_data[4]->level_up_route != 1) style="display:none;" </#if>>
                                        <#if ($level_data[4]->level_status == 1)
                                            <a level_id="4" user_ids="${level_data[4]->level_user_ids!}" href="javascript:;">+ 添加分销员</a>
                                            <input type="text" hidden name="user_ids_4" value="${level_data[2]->level_user_ids!}">
                                        </#if>
                                    </div>
                                    <!-- 自动 -->
                                    <div class="rule_conditions" <#if ($level_data[4]->level_up_route == 1) style="display:none;" </#if>>
                                        <p class="conditions_top">累计邀请用户数达
                                            <input type="text" name="invite_number_4" value="${level_data[4]->invite_number > 0 ? $level_data[4]->invite_number : ''!}">个</p>
                                        <p class="conditions_bottom"><span>或</span>累计推广金达
                                            <input type="text" name="distribution_money_4" value="${level_data[4]->total_distribution_money > 0 ? $level_data[4]->total_distribution_money : ''!}">元</p>
                                        <p class="conditions_bottom"><span>或</span>累积推广金与消费金总和达
                                            <input type="text" name="buy_money_4" value="${level_data[4]->total_buy_money > 0 ? $level_data[4]->total_buy_money : ''!}">元</p>
                                    </div>
                                </div>
                            </td>
                            <td class="distributer_num"><a href="/admin/market/distribution/distributer/list?top_index=4&all_users=1&level_id=4">${level_number[4] ?? 0!}</a></td>
                            <td class="status_switch">
                                <#if ($level_data[4]->level_status == 1)
                                    <div class="status_top"><span>已启动</span></div>
                                    <div class="status_bottom">
                                        <p class="status_off active" level_id="${level_data[4]->level_id!}"level_name="${level_data[4]->level_name!}" level_number="${level_number[4] ?? 0!}"><a href="javascript:;">停用</a></p>
                                    </div>
                                <#else>
                                    <div class="status_top"><span>已停用</span></div>
                                    <div class="status_bottom">
                                        <p class="status_on active"  level_id="${level_data[4]->level_id!}"><a href="javascript:;">启用</a></p>
                                    </div>
                                </#if>
                            </td>
                        </tr>
                        <!-- 五级 -->
                        <tr>
                            <td>五级</td>
                            <td class="level_name"><input type="text" name="level_name_5" value="${level_data[5]->level_name!}" id="level_name_5" onkeyup="limitLength(value,20,'','level_name_5')"></td>
                            <td class="level_rule">
                                <div class="rule_switch">
                                    <input type="radio" class="level_up_route" name="up_route_5" checked value="0" data-type="auto">自动升级
                                    <input type="radio" class="level_up_route" name="up_route_5" <#if ($level_data[5]->level_up_route == 1) checked </#if> value="1">手动升级
                                </div>
                                <div class="rule_info">
                                    <!-- 手动 -->
                                    <div class="add_distributer"  <#if ($level_data[5]->level_up_route != 1) style="display:none;" </#if>>
                                        <#if ($level_data[5]->level_status == 1)
                                            <a level_id="5" user_ids="${level_data[5]->level_user_ids!}" href="javascript:;">+ 添加分销员</a>
                                            <input type="text" hidden name="user_ids_5" value="${level_data[5]->level_user_ids!}">
                                        </#if>
                                    </div>
                                    <!-- 自动 -->
                                    <div class="rule_conditions" <#if ($level_data[5]->level_up_route == 1) style="display:none;" </#if>>
                                        <p class="conditions_top">累计邀请用户数达
                                            <input type="text" name="invite_number_5" value="${level_data[5]->invite_number > 0 ? $level_data[5]->invite_number : ''!}">个</p>
                                        <p class="conditions_bottom"><span>或</span>累计推广金达
                                            <input type="text" name="distribution_money_5" value="${level_data[5]->total_distribution_money > 0 ? $level_data[5]->total_distribution_money : ''!}">元</p>
                                        <p class="conditions_bottom"><span>或</span>累积推广金与消费金总和达
                                            <input type="text" name="buy_money_5" value="${level_data[5]->total_buy_money > 0 ? $level_data[5]->total_buy_money : ''!}">元</p>
                                    </div>
                                </div>
                            </td>
                            <td class="distributer_num"><a href="/admin/market/distribution/distributer/list?top_index=4&all_users=1&level_id=5">${level_number[5] ?? 0!}</a></td>
                            <td class="status_switch">
                                <#if ($level_data[5]->level_status == 1)
                                    <div class="status_top"><span>已启动</span></div>
                                    <div class="status_bottom">
                                        <p class="status_off active" level_id="${level_data[5]->level_id!}" level_name="${level_data[5]->level_name!}" level_number="${level_number[5] ?? 0!}"><a href="javascript:;">停用</a></p>
                                    </div>
                                <#else>
                                    <div class="status_top"><span>已停用</span></div>
                                    <div class="status_bottom">
                                        <p class="status_on active" level_id="${level_data[5]->level_id!}"><a href="javascript:;">启用</a></p>
                                    </div>
                                </#if>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>   
        </div>
    </div>
</form>
<!-- 停用按钮弹框 start -->
<div id="off_status">
    <div class="off_status_container pop_up_container">
        <div class="prompt">
            <img src="http://${image_domain!}/image/admin/notice_img.png" alt="">
            <span>停用后，该等级下所有分销员将自动降级为一级，请谨慎操作</span>
        </div>
        <p>当前等级：<span class="level_name"></span></p>
        <p>该等级现有分销员：<span class="level_number"></span>个</p>
    </div>
</div>
<!-- 停用按钮弹框 end -->

<!-- 保存按钮弹框 start -->
<div id="save_info">
    <div class="pop_up_container">
        <p style="margin:16px 0;">等级设置修改后，将有大量分销员的等级受到影响，是否保存当前修改内容</p>
    </div>
</div>
<!-- 保存按钮弹框 end -->
<div class="pages_bottom">
    <a href="##" class="btn_strategy_save">保存</a>
</div>
<#include "/admin/footer.ftl">
<script src="/js/admin/distributer_level_config.js?v=1.0.1" type="text/javascript"></script>

<script>
    function limitLength(value, byteLength, title, attribute) { 
                var newvalue = value.replace(/[^\x00-\xff]/g, "**");               
                var length = newvalue.length;         
                if (length * 1 <=byteLength * 1){ 
                        return; 
                } 
                var limitDate = newvalue.substr(0, byteLength); 
                var count = 0; 
                var limitvalue = ""; 
                for (var i = 0; i < limitDate.length; i++) { 
                        var flat = limitDate.substr(i, 1); 
                        if (flat == "*") { 
                            count++; 
                        } 
                } 
                var size = 0; 
                var istar = newvalue.substr(byteLength * 1 - 1, 1);
            
                if (count % 2 == 0) { 
                        size = count / 2 + (byteLength * 1 - count); 
                        limitvalue = value.substr(0, size); 
                } else { 
                        size = (count - 1) / 2 + (byteLength * 1 - count); 
                        limitvalue = value.substr(0, size); 
                } 
            document.getElementById(attribute).value = limitvalue; 
            return; 
        }
</script>