<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .btn_to_give a:hover,.btn_searchinfo:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_to_give a:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    /*input[type='text']:focus {*/
        /*border: 1px solid #5a8bff !important;*/
        /*box-shadow: 0 0 5px rgba(90,139,255, 1) !important;*/
        /*-webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;*/
        /*-moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;*/
    /*}*/
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .click_tips{
        float: right;
        margin:10px;
    }
    .btn_to_give{
        padding: 0px !important;
    }
    .btn_to_give a{
        display: inline-block;
    }
    .search-bl{
        width: 228px;
        height: 30px;
        margin: 10px 10px;
        display: inline-block;
        border-radius: 3px;
        border: 1px solid #ccc;
        line-height: 30px;
    }
    .primary{
        width: 195px;
        height: 26px;
        background-color: #fff;
        border: none;
        color: #333;
        font-size: 14px;
        padding-left: 8px;
        outline:none;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
    }

    .tb-decorate-list_tb {
        width: 100%;
        height: 100%;
        /*margin-left: 9px;*/
        margin: 0 auto;
        border: 1px solid #EFEDEE;
    }

    .tb-decorate-list_tb>tbody>tr>td {
        height: 80px;
        text-align: center;
        border: 1px solid #EFEDEE;
        
    }

    .tb-decorate-list_tb td{
        padding: 0;
    }

    .tb-decorate-list_tb>tbody>tr>td>a,
    .tb-decorate-list_tb>tbody>tr>td.tb-decorate-a {
        color: #5A8BFF;
    }
    .send_condition_td{
        text-align: left;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">{{ trans("admin/market_manage.coupon_grant_title")!}</span>
    </div>
    <div class="main-container">
        {{--<div class="coupon_type">--!}
            {{--<input type="hidden" value="${voucher!}" class="coupons_amonut">--!}
            {{--<ul>--!}
                {{--<li class="normal_type">--!}
                    {{--<a href="/admin/market/coupon/manage">普通优惠券</a>--!}
                {{--</li>--!}
                {{--<li class="fenlie_type">--!}
                    {{--<a href="/admin/coupon/split" link="/admin/coupon/split" class="edition_type">分裂优惠券</a>--!}
                {{--</li>--!}
                {{--<li class="give_to_sb actives">--!}
                    {{--<a href="/admin/market/grant/list" link="/admin/market/grant/list" class="edition_type">定向发券</a>--!}
                {{--</li>--!}
                {{--<li class="active_to_sb">--!}
                    {{--<a href="/admin/market/activityreward/list?nav=1" link="/admin/market/activityreward/list" class="edition_type">活动送券</a>--!}
                {{--</li>--!}
            {{--</ul>--!}
        {{--</div>--!}

        {{--发券部分--!}
        <form action="/admin/market/grant/list" method="post" id="form2">
            {{csrf_field()!}
        <div class="give_coupon">
            <div class="btn_to_give">
                <span>
                        <span style="padding-left: 30px">发劵活动名称</span>
                        <span class="search-bl">
                            <input type="text" name='keywords' value="${keywords!}" placeholder="请输入发劵活动名称"
                                   class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span>
                        <button class="btn_searchinfo">查询</button>
                    </span>
                {{--<#if ($voucher > 0)--!}
                <a href="/admin/market/grant" target="_blank" class="click_tips">发券</a>
                {{--</#if>--!}
                {{--<#if ($voucher == 0)--!}
                {{--<a href="##" class="click_tips">发券</a>--!}
                {{--</#if>--!}
            </div>
            <div class="give_coupon_table">
                {{--<input type="hidden"  >--!}
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="10%">{{ trans("admin/market_manage.grant_coupon.activities_name")!}</th>
                                    <th width="10%">{{ trans("admin/market_manage.grant_coupon.create_time")!}</th>
                                    <th width="17%">{{ trans("admin/market_manage.grant_coupon.active_crowd")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.send_action")!}</th>
                                    <th width="7%">{{ trans("admin/market_manage.grant_coupon.send_status")!}</th>
                                    
                                    <th>
                                        <table >
                                            <thead>
                                                <th width="7%">{{ trans("admin/market_manage.grant_coupon.coupon_names")!}</th>
                                                
                                                <th width="7%">{{ trans("admin/market_manage.grant_coupon.use_conditions")!}</th>
                                                
                                                <th width="7%">{{ trans("admin/market_manage.grant_coupon.coupon_price")!}</th>
                                                
                                                <th width="9%">{{ trans("admin/market_manage.grant_coupon.coupon_validity")!}</th>
                                                
                                                <th width="4%">{{ trans("admin/market_manage.grant_coupon.operation")!}</th>
                                            </thead>
                                        </table>
                                    </th>
                                                
                                </tr>
                                </thead>
                                <#list ($data_list as $item)
                                    <tr>
                                        <td>${item->act_name!}</td>
                                        <td>${item->in_time!}</td>
                                        <td style="text-align: left;">
                                            <#if ($item->send_condition->cart_box == 1)
                                                <li>30天内在本店内有加入购物车行为人群</li>
                                            </#if>

                                            <#if ($item->send_condition->custom_box == 1 || $item->act_id > 0)
                                                <#if ($item->have_pay>0)
                                                    <li>${item->have_pay!}天内有交易记录</li>
                                                </#if>
                                                <#if ($item->no_pay>0)
                                                    <li>${item->no_pay!}天内无交易记录</li>
                                                </#if>
                                                <#if ($item->min_count>0)
                                                        购买次数大于${item->min_count!}次<br/>
                                                </#if>
                                                <#if ($item->max_count>0)
                                                    购买次数小于${item->max_count!}次<br/>
                                                </#if>
                                                <#if ($item->max_ave_price>0)
                                                        购买商品均价小于${item->max_ave_price!}元<br/>
                                                </#if>

                                                <#if ($item->min_ave_price>0)
                                                        购买商品均价大于${item->min_ave_price!}元<br/>
                                                </#if>

                                                <#if (!empty($item->send_condition->point_start_time) && !empty($item->send_condition->point_end_time))
                                                    ${item->send_condition->point_start_time!}--${item->send_condition->point_end_time!}内有登录记录<br/>
                                                </#if>
                                            </#if>

                                            <#if ($item->card_id && $item->send_condition->card_box == 1)
                                                持有会员卡
                                                    <#list ($item->card_list as $k=>$v)
                                                        <#if ($k>0)、</#if> <a href="/admin/user/manage/list?user_card=${v->id!}&top_index=5" style="color: #5A8BFF">${v->card_name!}</a>
                                                    </#list>
                                                    <br/>
                                            </#if>
                                            <#if ($item->tag_id && $item->send_condition->tag_box == 1)
                                                属于标签
                                                    <#list ($item->tag_list as $k=>$v)
                                                        <#if ($k>0)、</#if> <a href="/admin/user/manage/list?tag=${v->tag_id!}&top_index=5" style="color: =#5A8BFF;">${v->tag_name!}</a>
                                                    </#list>
                                                    <br/>
                                            </#if>

                                            <#if ($item->send_condition->goods_ids && $item->send_condition->goods_box == 1)
                                                购买指定商品<br/>
                                            </#if>

                                            <#if ($item->user && $item->send_condition->member_box == 1)
                                                手动添加会员
                                            </#if>
                                        </td>
                                        <td>${item->send_action_name!}</td>
                                        <td>${item->send_status_name!}</td>
                                        <td style="padding: 0;border: 0px solid #EFEDEE;">
                                            <table class="tb-decorate-list_tb">
                                                <tbody>
                                                    <#list ($item->couponInfo as $coupon)
                                                    <tr>
                                                        <td width="7%">${coupon->act_name!}</td>
                                                        <td width="7%"><#if ($coupon->least_consume>0)满${coupon->least_consume!}元可用<#else>无限制</#if></td>
                                                        <td width="7%"><#if ($coupon->act_code == 'voucher')${coupon->denomination!}元 <#else> ${coupon->denomination!}折 </#if></td>
                                                        <td width="9%"><#if ($coupon->validity>0) 自领取之日起${coupon->validity!}天有效 <#else> ${coupon->start_time!}<br/>至<br/>${coupon->end_time!}</#if></td>
                                                        <td width="4%"><a href="/admin/public/coupon/get/list?top_index=${top_index!}&sub_index=${sub_index!}&id=${coupon->id!}&access_id=${item->id!}" target="_blank">发放明细</a></td>
                                                    </tr>
                                                    </#list>
                                                </tbody>
                                            </table>
                                            
                                        </td>
                                        
                                        
                                        <!-- <td>${item->act_name_b!}</td>
                                        <td><#if ($item->least_consume>0)满${item->least_consume!}元可用<#else>无限制</#if></td>
                                        
                                        <td><#if ($item->act_code == 'voucher')${item->denomination!}元 <#else> ${item->denomination!}折 </#if> </td>
                                        
                                        <td><#if ($item->validity>0) 自领取之日起${item->validity!}天有效 <#else> ${item->start_time!}<br/>至<br/>${item->end_time!}</#if></td>
                                        
                                        <td>
                                            {{--<a href="##">查看活动详情</a><br>--!}
                                            <a href="/admin/public/coupon/get/list?id=${item->act_id!}&access_id=${item->id!}" target="_blank">发放明细</a>
                                        </td> -->
                                    </tr>
                                </#list>
                            </table>
                        </div>
                    </div>
                    <div class="paging_footer">
                      <#include "/admin/jump_page_admin.ftl">
                    </div>
                </form>
            </div>
        </div>
        {{--发券部分结束--!}
    </div>
</div>

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include "/admin/footer.ftl">
<script type="text/javascript">
   var coupon_nums = $(".coupons_amonut").val();
   if(coupon_nums == 0){
       $(".click_tips").click(function () {
           util.mobile_alert("暂无可发放的优惠券，请先添加优惠券！");
       })
   }
</script>
<script type="text/javascript">
    getPowerInfo('main_config','coupon_grant','sub_4','定向发券',0);
</script>
