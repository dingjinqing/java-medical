<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/coupon_manage.css?v=1.0.0">
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.0">
<style>
    a {
        text-decoration: none;
    }
    .coupon-search:hover{
        color:#fff;
        text-decoration: none;
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .coupon-search:focus{
        color:#fff;
        text-decoration: none;
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .tb_paging td a {
        text-decoration: none;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <#if ($type==1)
        <span>分裂优惠券管理 /</span>
        <span style="color: #666;">领取明细</span>
        <#elseif>($type == 3)
            <span>支付有礼 /</span>
            <span style="color: #666;">领取明细</span>
        <#else>
            <#if ($get_coupon)
                <span><a href="/admin/market/grant/list?top_index=4">{{ trans("admin/market_manage.coupon_grant_title")!}</a> / </span><span><span class="ellipsis">${coupon_name!}</span> - </span>
            <#else>
                <span><a href="/admin/market/coupon/manage?nav=1&top_index=4">{{ trans("admin/market_manage.coupon_manage_title")!}</a> / </span><span><span class="ellipsis">${coupon_name!}</span> - </span>
            </#if>
        <span style="color: #666;">领取明细</span>
        </#if>
    </div>
    <div class="main-container">
        <#if ($type != 3)
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a <#if ($type != 1) href="/admin/market/coupon/manage?nav=0" <#else> href="/admin/market/payreward/split?nav=0" </#if>><#if ($type==1)所有分裂优惠券<#else>所有优惠券</#if></a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/admin/market/coupon/manage?nav=1" <#else> href="/admin/market/payreward/split?nav=1" </#if>>进行中</a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/admin/market/coupon/manage?nav=2" <#else> href="/admin/market/payreward/split?nav=2" </#if>>未开始</a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/admin/market/coupon/manage?nav=3" <#else> href="/admin/market/payreward/split?nav=3" </#if>>已过期</a>
                </li>
                <li>
                    <a <#if ($type != 1) href="/admin/market/coupon/manage?nav=4" <#else> href="/admin/market/payreward/split?nav=4" </#if>>已停用</a>
                </li>
                <li class="active">
                    <a href="/admin/public/coupon/get/list?top_index=${top_index!}&sub_index=${sub_index!}&id=${id!}&type=${type!}" target="_blank">领取明细</a>
                </li>
            </ul>
        </div>
        </#if>

        {{--<script>--!}
            {{--// tab切换--!}
            {{--$("[data-toggle='tab']").click(function () {--!}
                {{--var url_arr = ['/admin/public/coupon/get/list', '/admin/market/coupon/manage'];--!}
                {{--var idx = $(this).parent().index();--!}
                {{--if (url_arr[idx] != undefined) {--!}
                    {{--window.location.href = url_arr[idx];--!}
                {{--}--!}
            {{--});--!}
        {{--</script>--!}
        <form <#if ($type == 3) action="/admin/public/coupon/get/list?sub_index=${sub_index!}&top_index=${top_index!}&type=3&pay_act_id=${payreward->id!}" <#else> <#if (!$access_id && $user_id) action="/admin/public/coupon/get/list?type=1&user_id=${user_id!}&sub_index=${sub_index!}&top_index=${top_index!}"
              <#elseif>(!$access_id && !$use_id) action="/admin/public/coupon/get/list?id=${id!}&sub_index=${sub_index!}&top_index=${top_index!}" <#else>
        action = "/admin/public/coupon/get/list?id=${id!}&access_id=${access_id!}&sub_index=${sub_index!}&top_index=${top_index!}"
              </#if> </#if> method="post" id="form1" name="form1">
            {{ csrf_field()!}
            <input type="hidden" name="access_id" value="${access_id!}">
            <div class="box panel panel-body list-center-fee">
            <div class="form-inline shop-template-container">
                <#if ($type== 3)
                    <input type="hidden" name="pay_act_id" value="${payreward->id!}">
                    <label class="fee-pad">优惠券：</label>
                    <select name="id"  class="coupon-sel" style="width: 170px;margin-right: 60px;">
                        <option value="${payreward->coupon_ids!}" selected>全部优惠券</option>
                        <#list ($payreward_coupon_list as $coupon)
                        <option value="${coupon->id!}" <#if ($id == $coupon->id && !strstr($id,',')) selected </#if>>${coupon->act_name!}</option>
                        </#list>
                    </select>
                <#else>
                    <input type="hidden" name="id" value="${id!}">
                </#if>
                <input type="hidden" name="del"/>
                <label class="fee-pad">手机号：</label>
                <input type="text" placeholder="请输入手机号" name="mobile" value="${mobile!}">
                <label class="fee-pad user-name">用户昵称：</label>
                <input type="text" name="username" value="${username!}">
                <label class="fee-pad user-name">使用状态：</label>
                <select name="status"  class="coupon-sel">
                    <option value="0">全部</option>
                    <option value="1" <#if ($status == 1) selected </#if>>未使用</option>
                    <option value="2" <#if ($status == 2) selected </#if>>已使用</option>
                    <option value="3" <#if ($status == 3) selected </#if>>已过期</option>
                    <option value="4" <#if ($status == 4) selected </#if>>已废除</option>
                </select>
                <button type="button" class="coupon-search">搜索</button>
            </div>
        </div>
        <div class="return-goods-box">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr class="get-list-th">
                                <th width="9%">{{ trans("admin/market_manage.coupon_get_list.user_nickname")!}</th>
                                <th width="10%">{{ trans("admin/market_manage.coupon_get_list.mobile")!}</th>
                                <th width="10%">{{ trans("admin/market_manage.coupon_get_list.coupon_name")!}</th>
                                <th width="9%">{{ trans("admin/market_manage.coupon_get_list.get_source")!}</th>
                                <th width="9%">{{ trans("admin/market_manage.coupon_get_list.use_score")!}</th>
                                <th width="6%">{{ trans("admin/market_manage.coupon_get_list.is_use")!}</th>
                                <th width="10%">{{ trans("admin/market_manage.coupon_get_list.use_order_code")!}</th>
                                <th width="10%">{{ trans("admin/market_manage.coupon_get_list.date_limit")!}</th>
                                <th width="10%">{{ trans("admin/market_manage.coupon_get_list.get_time")!}</th>
                                <th width="10%">{{ trans("admin/market_manage.coupon_get_list.use_time")!}</th>
                                <th width="9%">{{ trans("admin/market_manage.coupon_get_list.operation")!}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if ($user_coupon_list)
                                <#list ($user_coupon_list as $item)
                                    <tr>
                                        <td><a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->username!}</a></td>
                                        <td>${item->mobile!}</td>
                                        <td>${item->act_name!}</td>
                                        {{--<1表单送券2支付送券3活动送券4积分兑换5直接领取6分裂优惠券7crm领券8幸运大抽奖9定向发券>--!}
                                        <td>
                                            <#if  ($item->get_source == 1) 表单送券
                                            <#elseif> ($item->get_source == 2) 支付送券
                                            <#elseif> ($item->get_source == 3) 活动送券
                                            <#elseif> ($item->get_source == 4) 积分兑换
                                            <#elseif> ($item->get_source == 5) 直接领取
                                            <#elseif> ($item->get_source == 6) 分裂优惠券
                                            <#elseif> ($item->get_source == 7) crm同步
                                            <#elseif> ($item->get_source == 8) 幸运大抽奖
                                            <#elseif> ($item->get_source == 9) 定向发券
                                            <#elseif> ($item->get_source == 10) 会员导入发券
                                            <#elseif> ($item->get_source == 11) 好友帮助砍价发券
                                            <#elseif> ($item->get_source == 12) 砍价失败激励
                                            <#elseif> ($item->get_source == 13) 拼团失败激励
                                            <#elseif> ($item->get_source == 14) 拼团抽奖失败激励
                                            <#elseif> ($item->get_source == 15) 优惠券礼包
                                            <#else> 其他
                                            </#if>
                                        </td>
                                        <td><#if  ($item->get_source == 4 && $item->use_score == 1)${item->score_number!}</#if></td>
                                        <td><#if ($item->is_used ==1) 是 <#elseif>($item->is_used == 3)已废除<#else> <#if (date('Y-m-d H:i:s')<=$item->end_time) 否 <#else> 已过期 </#if> </#if></td>
                                        <td>${item->order_sn!}</td>
                                        <td>${item->start_time!}<br/>至<br/>${item->end_time!}</td>
                                        <td>${item->created!}</td>
                                        <td>${item->used_time!}</td>
                                        <td class="tb-decorate-a">
                                            <#if ($item->is_used ==0 && date('Y-m-d H:i:s')<=$item->end_time) <a href="javascript:void(0)" class="abort" act_id="${item->id!}">{{ trans("admin/market_manage.coupon_get_list.abate")!} </#if></a>

                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            <div class="paging_footer">
           <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
        </form>
    </div>
</div>
<script>
    {{--function gopage(page) {--!}
        {{--var last_page = '${user_coupon_list -> lastPage()!}';--!}
        {{--if(parseInt(page) > parseInt(last_page)) {--!}
            {{--page = last_page;--!}
        {{--}--!}
        {{--$("#page").val(page);--!}
        {{--$("#form1").submit();--!}
    {{--}--!}
    $(".coupon-search").click(function(){
        $("#form1").submit();
    });
    $('.abort').click(function(){
        $('input[name="del"]').val($(this).attr("act_id"));
        $("#form1").submit();
    });
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
