<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/promote_manage.css?v=1.0.1" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">优惠券礼包 / </span>
            <span style="color: #666;">活动名称-领取明细</span>
        </div>
    </div>
    <div class="main-container">
        <div class="promote_list initiate_list" >
            <form action="" method="post" id="form1">
                {{csrf_field()!}
                <div class="search_some clearfix">
                    <div class="search_list clearfix">
                        <div class="fl">
                            用户昵称：<input type="text" placeholder="请输入用户昵称" name="username" value="${input['username']!}">
                        </div>
                        <div class="fl">
                            手机号：<input type="text" placeholder="请输入用户手机号" name="mobile" value="${input['mobile']!}">
                        </div>
                        <div class="fl" style="width: 500px;">
                            领取时间：
                            <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${input['start_time']!}" />
                            &nbsp; 至&nbsp;&nbsp;
                            <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${input['end_time']!}" />
                        </div>
                    </div>
                    <div class="search_list clearfix">
                        <div class="fl">
                            领取方式：
                            <select name="access_mode" id="">
                                <option value="-1" <#if ($input['access_mode'] == -1) selected </#if>>全部</option>
                                <option value="0" <#if ($input['access_mode'] == '0') selected </#if>>现金</option>
                                <option value="1" <#if ($input['access_mode'] == '1') selected </#if>>积分</option>
                                <option value="2" <#if ($input['access_mode'] == '2') selected </#if>>免费</option>
                            </select>
                        </div>
                        <div class="fl" style="width: 460px;">
                            订单号：<input type="text" placeholder="" name="order_sn" value="${input['order_sn']!}">
                            <button class="btn_search">筛选</button>
                        </div>
                    </div>
                </div>
                <div class="detail_table">
                    <table width="100%">
                        <thead>
                        <tr class="promote_first">
                            <td width="15%">用户昵称</td>
                            <td width="20%">手机号</td>
                            <td width="10%">领取方式</td>
                            <td width="20%">订单号</td>
                            <td width="20%">领取时间</td>
                            <td width="15%">已领取优惠券数量</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr>
                                <td width="15%"><a href="##">${item->username!}</a></td>
                                <td width="20%">${item->mobile!}</td>
                                <td width="10%">
                                    <#if ($item->access_mode == 0)
                                        现金购买
                                    <#elseif>($item->access_mode == 1)
                                        积分购买
                                    <#elseif>($item->access_mode == 2)
                                        免费领取
                                    </#if>
                                </td>
                                <td width="20%">${item->order_sn!}</td>
                                <td width="20%">${item->add_time!}</td>
                                <td width="15%">${item->ct!}</td>
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
</script>
<script type="text/javascript">
    getPowerInfo('main_config','coupon_package','sub_4','优惠券包',0);
</script>