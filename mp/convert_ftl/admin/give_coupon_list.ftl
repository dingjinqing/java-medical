<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/gift_manage.css?v=1.0.5" type="text/css" />
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css?v=1.0.1">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />

<div style="min-width:1090px">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span style="color: #666;">活动有礼</span>
    </div>
    <div class="main-container">
        {{--<div class="coupon_type">--!}
            {{--<ul>--!}
                {{--<li class="normal_type">--!}
                    {{--<a href="/admin/market/coupon/manage?nav=1 ">普通优惠券</a>--!}
                {{--</li>--!}
                {{--<li class="fenlie_type ">--!}
                    {{--<a href="/admin/coupon/split?nav=1" link="/admin/coupon/split?nav=1" class="edition_type">分裂优惠券</a>--!}
                {{--</li>--!}
                {{--<li class="give_to_sb">--!}
                    {{--<a href="/admin/market/grant/list" link="/admin/market/grant/list" class="edition_type">定向发券</a>--!}
                {{--</li>--!}
                {{--<li class="active_to_sb actives">--!}
                    {{--<a href="/admin/market/activityreward/list?nav=1" link="/admin/market/activityreward/list">活动送券</a>--!}
                {{--</li>--!}
            {{--</ul>--!}
        {{--</div>--!}

        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li <#if  ($request['nav'] == 0) class="active" </#if>>
                        <a href="/admin/market/activityreward/list" >全部活动有礼活动</a>
                    </li>
                    <li <#if  ($request['nav'] == 1) class="active" </#if>>
                        <a href="/admin/market/activityreward/list?nav=1" >进行中</a>
                    </li>
                    <li <#if  ($request['nav'] == 2) class="active" </#if>>
                        <a href="/admin/market/activityreward/list?nav=2" >未开始</a>
                    </li>
                    <li <#if  ($request['nav'] == 3) class="active" </#if>>
                        <a href="/admin/market/activityreward/list?nav=3">已过期</a>
                    </li>
                    <li <#if  ($request['nav'] == 4) class="active" </#if>>
                        <a href="/admin/market/activityreward/list?nav=4">已停用</a>
                    </li>
                </ul>
            </div>

            <ul class="add-child-ul">
                <li>
                    <a href="/admin/market/activityreward/config" class="add-child-btn" target="_blank">
                        添加活动有礼活动
                    </a>
                </li>
            </ul>
            <div class="return-goods-box">
                <form action="/admin/market/activityreward/list" method="post" id="form1">
                    {{csrf_field()!}
                    <input name="act" type="hidden">
                    <input name="id" type="hidden">
                    <input name="nav" type="hidden" value="${request['nav'] ?? 0!}">
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="12%">活动名称</th>
                                    <th>触发条件</th>
                                    <th width="25%">活动时间</th>
                                    {{--<th width="30%">发放优惠券</th>--!}
                                    {{--<th width="12%">领取人数</th>--!}
                                    <th>活动类型</th>
                                    <th>活动状态</th>
                                    <th width="21%">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list  ($list as $item)
                                    <tr>
                                        <td>${item->name!}</td>
                                        <td><#if  ($item->action == 1)
                                                新用户
                                            <#elseif>($item->action == 2)
                                                全部用户
                                            </#if>
                                        </td>
                                        <td>
                                            ${item->start_date!} <br/> 至 <br/>${item->end_date!}
                                        </td>
                                        {{--<td>
                                            <#list  ($item->couponArr as $coupon)
                                                <a href="/admin/market/coupon/edit?id=${coupon->id!}" target="_blank">${coupon->act_name!};</a>
                                            </#list>
                                        </td>--!}
                                        {{--<td> ${item->record_total!} </td>--!}
                                        <td>
                                            <#if  ($item->activity_action == 1)
                                                活动送券
                                            <#elseif> ($item->activity_action == 2)
                                                幸运大抽奖
                                            <#elseif> ($item->activity_action == 3)
                                                自定义
                                            </#if>
                                        </td>
                                        <td>
                                            <#if  ($item->nav == 1)
                                                未开始
                                                <#elseif> ($item->nav == 2)
                                                进行中
                                                <#elseif> ($item->nav == 3)
                                                已过期
                                                <#elseif> ($item->nav == 4)
                                                已停用
                                            </#if>
                                        </td>
                                        <td>
                                            <#if  (in_array($item->nav, [1, 2]))
                                                <#if  ($item->activity_action == 1)
                                                    <a href="/admin/market/activityreward/config?id=${item->id!}" target="_blank"> &nbsp;编辑&nbsp;</a>&nbsp;&nbsp;
                                                <#elseif> ($item->activity_action == 2)
                                                    <a href="/admin/market/lottery/activity/config?id=${item->id!}" target="_blank"> &nbsp;编辑&nbsp;</a>&nbsp;&nbsp;
                                                <#elseif> ($item->activity_action == 3)
                                                    <a href="/admin/market/activityreward/customize/config?id=${item->id!}" target="_blank"> &nbsp;编辑&nbsp;</a>&nbsp;&nbsp;
                                                </#if>
                                            </#if>
                                            <#if  (!in_array($item->nav, [3, 4]) && $item->status == 1)
                                                <a href="#" class="close_activity" item="${item->id!}"> 停用&nbsp;</a> &nbsp;
                                            </#if>
                                            <#if  ($item->is_enable == 1)
                                                    <a href="#" class="enable_activity" item="${item->id!}" end_time="${item->end_date!}"> 启用&nbsp;</a> &nbsp;
                                            </#if>
                                            <#if  (in_array($item->nav, [3, 4]))
                                                <a href="#" class="del_activity" item="${item->id!}"> 删除&nbsp;</a> &nbsp;
                                            </#if>
                                            <#if (in_array($item->nav,[2,3,4]))
                                                <#if  ($item->activity_action == 1)
                                                        <a href="/admin/market/activityreward/detail?id=${item->id!}"> 发放明细</a>

                                                <#elseif> ($item->activity_action == 2)
                                                        <a href="/admin/market/lottery/detail?source=login&act_id=${item->id!}&id=${item->mrking_voucher_id!}" target="_blank"> 抽奖明细</a>
                                                <#elseif> ($item->activity_action == 3)
                                                </#if>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="paging_footer">
                        <#include "/admin/jump_page_admin.ftl">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(".enable_activity").click(function () {
        var that = $(this);
        var myDate = new Date();
        //获取当前年
        var year=myDate.getFullYear();
        //获取当前月
        var month=myDate.getMonth()+1;
        //获取当前日
        var date=myDate.getDate();
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var m=myDate.getMinutes();     //获取当前分钟数(0-59)
        var s=myDate.getSeconds();
        var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
        if(that.attr("end_time")<= now){
            util.mobile_alert("该活动已过期，不可启用");
            return false;
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用该活动吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("[name='act']").val("enable");
                $("[name='id']").val(that.attr('item'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".close_activity").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用该活动吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("[name='act']").val("close");
                $("[name='id']").val(that.attr('item'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".del_activity").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该活动吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("[name='act']").val("del");
                $("[name='id']").val(that.attr('item'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    // getPowerInfo('main_config','coupon_gift','sub_4','活动送券',0);
    getPowerInfo('main_config','activity_reward','sub_4','活动有礼',0);
</script>