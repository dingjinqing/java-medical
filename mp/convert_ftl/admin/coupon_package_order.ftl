<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/promote_manage.css?v=1.0.1" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">优惠券礼包 / </span>
            <span style="color: #666;">${coupon_pack['couponPack']->act_name!}-活动订单</span>
        </div>
    </div>
    <div class="main-container">
        <div class="promote_list initiate_list" >
            <form action="/admin/market/couponpackage/order" method="post" id="form1">
                {{csrf_field()!}
                <div class="search_some clearfix">
                    <div class="search_list clearfix">
                        <div class="fl" style="width: 270px;">
                            订单号：<input type="text" placeholder="" name="order_sn" value="${input['order_sn']!}">
                        </div>
                        <div class="fl">
                            下单用户信息：<input type="text" name="user_info" placeholder="请输入下单用户昵称/手机号" value="${input['user_info']!}">
                        </div>
                        <div class="fl" style="width: 500px;">
                            下单时间：
                            <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${input['start_time']!}" />
                            &nbsp; 至&nbsp;&nbsp;
                            <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${input['end_time']!}" />
                        </div>
                        <input type="hidden" name="act" id="act" value="">
                    </div>
                    <div class="search_list clearfix">
                        <div class="fl">
                            <button class="btn_search" type="submit">筛选</button>
                            <button class="btn_excel" type="button" onclick="excel(event)">导出数据</button>
                        </div>

                    </div>
                </div>
                <div class="detail_table">
                    <table width="100%">
                        <thead>
                        <tr class="promote_first">
                            <td width="20%">订单号</td>
                            <td width="15%">单价</td>
                            <td width="20%">下单用户信息</td>
                            <td width="15%">下单时间</td>
                            <td width="15%">订单状态</td>
                            <td width="15%">支付金额</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr>
                                <td width="20%">${item->order_sn!}</td>
                                <td width="15%">
                                    <#if ($item->money_paid > 0 || $item->use_account > 0)
                                        ${item->money_paid + $item->use_account!}元
                                    <#elseif>($item->use_score > 0)
                                        ${item->use_score!}积分
                                    <#else>
                                        免费
                                    </#if>
                                </td>
                                <td width="20%">
                                    <a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->username!}</a><br>
                                    <a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->mobile!}</a>
                                </td>
                                <td width="15%">${item->add_time!}</td>
                                <td width="15%">
                                    <#if ($item->order_status == 1)
                                        订单完成
                                    <#else>
                                        订单取消
                                    </#if>
                                </td>
                                <td width="15%">${item->money_paid + $item->use_account!}元</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <div class="paging_footer" style="margin-top: 12px;">
                    <#include "/admin/jump_page_admin.ftl">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('.fix_footer').width($('.main-container').width());
    function picker(){
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    function excel(event){
        $('#act').val('export_excel');
        $('#form1').submit();
    }
    getPowerInfo('main_config','coupon_package','sub_4','优惠券包',0);
</script>