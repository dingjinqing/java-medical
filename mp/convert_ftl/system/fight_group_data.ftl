<#include ("system.header")
<#include ("system.features_nav")
<link rel="stylesheet" href="/css/system/fight_group_data.css?v=1.0.0" type="text/css">

<div style="min-height: 1090px;">
    <div class="shop-container">
        <div class="order-info">
            <form action="/system/statistics/activity?activity_type=${activity_type!}" method="post" id="form1">
                {{csrf_field()!}
                <input type="hidden" name="activity_type" value="${activity_type!}">
                <input type="hidden" name="page" value="" id="page">
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl select-option">
                            <span>时间筛选</span>
                            <select name="type" class="select_visit_trend" id="">
                                <option value="1" selected>查看1天</option>
                                <option value="7" <#if ($request['type']==7) selected </#if>>最近7天</option>
                                <option value="30" <#if ($request['type']==30) selected </#if>>最近30天</option>
                            </select>
                            <span class="layui-card-item" style="width: auto">${date[0]!} - ${date[1]!}</span>
                        </div>
                        <div class="fl one">
                            <span>店铺ID</span>
                            <input type="text" name="shop_id" value="${request['shop_id']!}" placeholder='请输入店铺ID' />
                        </div>
                        <div class="fl one">
                            <span>店铺名称</span>
                            <input type="text" name="shop_name" value="${request['shop_name']!}" placeholder='请输入店铺名称' />
                        </div>
                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl" style="margin-left: 35px;">
                            <span>店铺版本</span>
                            <select name="shop_type" id="" class="select_visit_trend">
                                <option value="" selected>选择店铺类型</option>
                                <#list ($version_list as $item)
                                    <option value="${item->level!}" <#if ($request["shop_type"] == $item->level) selected </#if>>${item->version_name!}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="fl" style="margin-left: 35px;">
                            <span>店铺状态</span>
                            <select name="is_enabled" id="" class="select_visit_trend">
                                <option value="-1" <#if (is_null($request["is_enabled"])) selected </#if>>选择店铺状态</option>
                                <option value="1" <#if ($request["is_enabled"] == 1) selected </#if>>禁用</option>
                                <option value="0" <#if ($request["is_enabled"] == 0 && !is_null($request["is_enabled"])) selected </#if>>启用</option>
                            </select>
                        </div>
                        <div class="fl" style="margin-left: 35px;">
                            <span>是否过期</span>
                            <select name="is_use" id="" class="select_visit_trend">
                                <option value="-1" <#if (is_null($request["is_use"])) selected </#if>>请选择是否过期</option>
                                <option value="0" <#if ($request["is_use"] == 0 && !is_null($request["is_use"])) selected </#if>>过期</option>
                                <option value="1" <#if ($request["is_use"] == 1) selected </#if>>未过期</option>
                            </select>
                        </div>
                        <div class="fl">
                            <input type="submit" class="search" value="搜索">
                        </div>
                    </li>
                    </li>
                </ul>
            </form>
        </div>
        <div class="many-people">
            <table class="main-order-table">
                <tbody>
                <#if ($activity_type == 1)
                    <tr class="tr-border">
                        <td>店铺ID</td>
                        <td>店铺名称</td>
                        <td>小程序名称（公司）</td>
                        <td>店铺版本</td>
                        <td>店铺状态</td>
                        <td>到期时间</td>
                        <td>进行中活动数</td>
                        <td>已过期活动数</td>
                        <td>已关闭活动数</td>
                        <td>活动订单数</td>
                        <td>活动订单总金额</td>
                        <td>访问用户数</td>
                        <td>访问用户占比</td>
                        <td>参团用户数</td>
                        <td>成团用户数</td>
                        <td>成团用户占比</td>
                        <td>分享用户数</td>
                        <td>分享用户数占比</td>
                        <td>活动拉新用户数</td>
                    </tr>
                    <#if ($data_list)
                        <#list ($data_list as $value)
                            <tr class="tr-no-border">
                                <td class="no-color-td">${value->shop_id!}</td>
                                <td>${value->shop_name!}</td>
                                <td class="no-color-td">${value->nick_name!}</td>
                                <td class="no-color-td">${shop_type_list[$value->shop_type]!}</td>
                                <td class="no-color-td">${value->is_enabled == 1? "禁用":"启用"!}</td>
                                <td class="no-color-td">${value->expire_time!}</td>
                                <td>${value->currently!}</td>
                                <td>${value->expired!}</td>
                                <td>${value->closed!}</td>
                                <td>${value->order_num!}</td>
                                <td>${value->order_money!}</td>
                                <td>${value->visited_user!}</td>
                                <td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
                                <td>${value->join_user!}</td>
                                <td>${value->success_user!}</td>
                                <td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
                                <td>${value->share_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->new_user!}</td>
                            </tr>
                        </#list>
                    </#if>
                </#if>
                <#if ($activity_type == 3)
                    <tr class="tr-border">
                        <td>店铺ID</td>
                        <td>店铺名称</td>
                        <td>小程序名称（公司）</td>
                        <td>店铺版本</td>
                        <td>店铺状态</td>
                        <td>到期时间</td>
                        <td>进行中活动数</td>
                        <td>已过期活动数</td>
                        <td>已关闭活动数</td>
                        <td>活动订单数</td>
                        <td>访问用户数</td>
                        <td>访问用户占比</td>
                        <td>砍价用户数</td>
                        <td>砍价用户数占比</td>
                        <td>砍价成功用户数</td>
                        <td>砍价成功用户数占比</td>
                        <td>砍价人次</td>
                        <td>分享用户数</td>
                        <td>分享用户数占比</td>
                        <td>活动拉新用户数</td>
                    </tr>
                    <#if ($data_list)
                        <#list ($data_list as $value)
                            <tr class="tr-no-border">
                                <td class="no-color-td">${value->shop_id!}</td>
                                <td>${value->shop_name!}</td>
                                <td class="no-color-td">${value->nick_name!}</td>
                                <td class="no-color-td">${shop_type_list[$value->shop_type]!}</td>
                                <td class="no-color-td">${value->is_enabled == 1? "禁用":"启用"!}</td>
                                <td class="no-color-td">${value->expire_time!}</td>
                                <td>${value->currently!}</td>
                                <td>${value->expired!}</td>
                                <td>${value->closed!}</td>
                                <td>${value->order_num!}</td>
                                <td>${value->visited_user!}</td>
                                <td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
                                <td>${value->join_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->join_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->success_user!}</td>
                                <td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
                                <td>${value->bargain_user_count!}</td>
                                <td>${value->share_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->new_user!}</td>
                            </tr>
                        </#list>
                    </#if>
                </#if>
                <#if ($activity_type == 5)
                    <tr class="tr-border">
                        <td>店铺ID</td>
                        <td>店铺名称</td>
                        <td>小程序名称（公司）</td>
                        <td>店铺版本</td>
                        <td>店铺状态</td>
                        <td>到期时间</td>
                        <td>进行中活动数</td>
                        <td>已过期活动数</td>
                        <td>已关闭活动数</td>
                        <td>活动订单数</td>
                        <td>活动订单总金额</td>
                        <td>访问用户数</td>
                        <td>访问用户占比</td>
                        <td>参与秒杀用户数</td>
                        <td>参与秒杀用户数占比</td>
                        <td>秒杀支付用户数</td>
                        <td>活动拉新用户数</td>
                    </tr>
                    <#if ($data_list)
                        <#list ($data_list as $value)
                            <tr class="tr-no-border">
                                <td class="no-color-td">${value->shop_id!}</td>
                                <td>${value->shop_name!}</td>
                                <td class="no-color-td">${value->nick_name!}</td>
                                <td class="no-color-td">${shop_type_list[$value->shop_type]!}</td>
                                <td class="no-color-td">${value->is_enabled == 1? "禁用":"启用"!}</td>
                                <td class="no-color-td">${value->expire_time!}</td>
                                <td>${value->currently!}</td>
                                <td>${value->expired!}</td>
                                <td>${value->closed!}</td>
                                <td>${value->order_num!}</td>
                                <td>${value->order_money!}</td>
                                <td>${value->visited_user!}</td>
                                <td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
                                <td>${value->join_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->join_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->success_user!}</td>
                                <td>${value->new_user!}</td>
                            </tr>
                        </#list>
                    </#if>
                </#if>
                <#if ($activity_type == 6)
                    <tr class="tr-border">
                        <td>店铺ID</td>
                        <td>店铺名称</td>
                        <td>小程序名称（公司）</td>
                        <td>店铺版本</td>
                        <td>店铺状态</td>
                        <td>到期时间</td>
                        <td>进行中表单数</td>
                        <td>已过期表单数</td>
                        <td>已关闭表单数</td>
                        <td>访问用户数</td>
                        <td>访问用户占比</td>
                        <td>分享用户数</td>
                        <td>分享用户数占比</td>
                        <td>提交表单用户数</td>
                        <td>提交表单用户数占比</td>
                        <td>拉取新用户数</td>
                    </tr>
                    <#if ($data_list)
                        <#list ($data_list as $value)
                            <tr class="tr-no-border">
                                <td class="no-color-td">${value->shop_id!}</td>
                                <td>${value->shop_name!}</td>
                                <td class="no-color-td">${value->nick_name!}</td>
                                <td class="no-color-td">${shop_type_list[$value->shop_type]!}</td>
                                <td class="no-color-td">${value->is_enabled == 1? "禁用":"启用"!}</td>
                                <td class="no-color-td">${value->expire_time!}</td>
                                <td>${value->currently!}</td>
                                <td>${value->expired!}</td>
                                <td>${value->closed!}</td>
                                <td>${value->visited_user!}</td>
                                <td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
                                <td>${value->share_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->success_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->success_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->new_user!}</td>
                            </tr>
                        </#list>
                    </#if>
                </#if>
                <#if ($activity_type == 7)
                    <tr class="tr-border">
                        <td>店铺ID</td>
                        <td>店铺名称</td>
                        <td>小程序名称（公司）</td>
                        <td>店铺版本</td>
                        <td>店铺状态</td>
                        <td>到期时间</td>
                        <td>进行中活动数</td>
                        <td>已过期活动数</td>
                        <td>已关闭活动数</td>
                        <td>活动总积分数</td>
                        <td>已瓜分积分数</td>
                        <td>访问用户数</td>
                        <td>访问用户占比</td>
                        <td>参团用户数</td>
                        <td>成团用户数</td>
                        <td>成团用户占比</td>
                        <td>分享用户数</td>
                        <td>分享用户数占比</td>
                        <td>活动拉新用户数</td>
                    </tr>
                    <#if ($data_list)
                        <#list ($data_list as $value)
                            <tr class="tr-no-border">
                                <td class="no-color-td">${value->shop_id!}</td>
                                <td>${value->shop_name!}</td>
                                <td class="no-color-td">${value->nick_name!}</td>
                                <td class="no-color-td">${shop_type_list[$value->shop_type]!}</td>
                                <td class="no-color-td">${value->is_enabled == 1? "禁用":"启用"!}</td>
                                <td class="no-color-td">${value->expire_time!}</td>
                                <td>${value->currently!}</td>
                                <td>${value->expired!}</td>
                                <td>${value->closed!}</td>
                                <td>${value->order_num!}</td>
                                <td>${value->order_money!}</td>
                                <td>${value->visited_user!}</td>
                                <td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
                                <td>${value->join_user!}</td>
                                <td>${value->success_user!}</td>
                                <td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
                                <td>${value->share_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->new_user!}</td>
                            </tr>
                        </#list>
                    </#if>
                </#if>
                <#if ($activity_type == 8)
                    <tr class="tr-border">
                        <td>店铺ID</td>
                        <td>店铺名称</td>
                        <td>小程序名称（公司）</td>
                        <td>店铺版本</td>
                        <td>店铺状态</td>
                        <td>到期时间</td>
                        <td>进行中活动数</td>
                        <td>已过期活动数</td>
                        <td>已关闭活动数</td>
                        <td>活动订单数</td>
                        <td>奖品发放数量</td>
                        <td>访问用户数</td>
                        <td>访问用户占比</td>
                        <td>参团用户数</td>
                        <td>成团用户数</td>
                        <td>成团用户占比</td>
                        <td>分享用户数</td>
                        <td>分享用户数占比</td>
                        <td>活动拉新用户数</td>
                    </tr>
                    <#if ($data_list)
                        <#list ($data_list as $value)
                            <tr class="tr-no-border">
                                <td class="no-color-td">${value->shop_id!}</td>
                                <td>${value->shop_name!}</td>
                                <td class="no-color-td">${value->nick_name!}</td>
                                <td class="no-color-td">${shop_type_list[$value->shop_type]!}</td>
                                <td class="no-color-td">${value->is_enabled == 1? "禁用":"启用"!}</td>
                                <td class="no-color-td">${value->expire_time!}</td>
                                <td>${value->currently!}</td>
                                <td>${value->expired!}</td>
                                <td>${value->closed!}</td>
                                <td>${value->order_num!}</td>
                                <td>${value->order_money!}</td>
                                <td>${value->visited_user!}</td>
                                <td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
                                <td>${value->join_user!}</td>
                                <td>${value->success_user!}</td>
                                <td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
                                <td>${value->share_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->new_user!}</td>
                            </tr>
                        </#list>
                    </#if>
                </#if>
                <#if ($activity_type == 9)
                    <tr class="tr-border">
                        <td>店铺ID</td>
                        <td>店铺名称</td>
                        <td>小程序名称（公司）</td>
                        <td>店铺版本</td>
                        <td>店铺状态</td>
                        <td>到期时间</td>
                        <td>进行中活动数</td>
                        <td>已过期活动数</td>
                        <td>已关闭活动数</td>
                        <td>活动订单数</td>
                        <td>活动订单总金额</td>
                        <td>访问用户数</td>
                        <td>访问用户占比</td>
                        {{--<td>参团用户数</td>--!}
                        <td>下单用户数</td>
                        {{--<td>下单用户占比</td>--!}
                        <td>分享用户数</td>
                        <td>分享用户数占比</td>
                        <td>活动拉新用户数</td>
                    </tr>
                    <#if ($data_list)
                        <#list ($data_list as $value)
                            <tr class="tr-no-border">
                                <td class="no-color-td">${value->shop_id!}</td>
                                <td>${value->shop_name!}</td>
                                <td class="no-color-td">${value->nick_name!}</td>
                                <td class="no-color-td">${shop_type_list[$value->shop_type]!}</td>
                                <td class="no-color-td">${value->is_enabled == 1? "禁用":"启用"!}</td>
                                <td class="no-color-td">${value->expire_time!}</td>
                                <td>${value->currently!}</td>
                                <td>${value->expired!}</td>
                                <td>${value->closed!}</td>
                                <td>${value->order_num!}</td>
                                <td>${value->order_money!}</td>
                                <td>${value->visited_user!}</td>
                                <td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
                                {{--<td>${value->join_user!}</td>--!}
                                <td>${value->success_user!}</td>
{{--                                <td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>--!}
                                <td>${value->share_user!}</td>
                                <td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
                                <td>${value->new_user!}</td>
                            </tr>
                        </#list>
                    </#if>
                </#if>
                </tbody>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                    <a href="#"
                                       onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">
                                        {{ trans("system/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text"
                                           value="${data_list->currentPage()!}" size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<#include ("system.footer")
<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
</script>