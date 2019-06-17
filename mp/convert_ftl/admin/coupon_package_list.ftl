<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/promote_manage.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/coupon_package_manage.css?v=1.0.0" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">优惠券礼包</span>
        </div>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs clearfix">
                <li <#if ($nav_type==0)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=0">全部优惠券礼包</a></li>
                <li <#if ($nav_type==1)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=1">进行中</a></li>
                <li <#if ($nav_type==2)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=2">未开始</a></li>
                <li <#if ($nav_type==3)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=3">已过期</a></li>
                <li <#if ($nav_type==4)class="active"</#if>><a href="/admin/market/couponpackage/list?nav=4">已停用</a></li>
            </ul>
        </div>
        <div class="btn_adds clearfix">
            <a href="/admin/market/couponpackage/info?top_index=4" target="_blank">添加优惠券礼包</a>
        </div>
    </div>
    <div class="main-container">
        <div class="promote_list">
            <form action="/admin/market/couponpackage/list" method="post" id="form1">
                {{csrf_field()!}
                <div class="search_some clearfix">
                    <div class="search_list clearfix">
                        <div class="fl">
                            活动名称：<input type="text" placeholder="请输入活动名称" name="act_name" value="${input['act_name']!}">
                        </div>
                        <div class="fl">
                            礼包名称：<input type="text" placeholder="请输入礼包名称" name="pack_name" value="${input['pack_name']!}">
                        </div>
                        <div class="fl">
                            领取方式：
                            <select name="access_mode">
                                <option value="-1" <#if ($input['access_mode'] == -1) selected </#if>>全部</option>
                                <option value="0" <#if ($input['access_mode'] === '0') selected </#if>>现金</option>
                                <option value="1" <#if ($input['access_mode'] === '1') selected </#if>>积分</option>
                                <option value="2" <#if ($input['access_mode'] === '2') selected </#if>>免费</option>
                            </select>
                            <button class="btn_search">筛选</button>
                        </div>
                        <input name="nav" type="hidden" value="${nav_type!}">
                    </div>
                </div>
                <table width="100%">
                    <thead>
                    <tr class="promote_first">
                        <td width="10%">活动名称</td>
                        <td width="10%">礼包名称</td>
                        <td width="15%">有效期</td>
                        <td width="5%">优惠券种类数/礼包</td>
                        <td width="5%">优惠券数量/礼包</td>
                        <td width="5%">可发放礼包数</td>
                        <td width="10%">领取方式</td>
                        <td width="10%">购买金额</td>
                        <td width="5%">已领取礼包数</td>
                        <td width="10%">活动状态</td>
                        <td width="15%">操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list ($data_list as $data)
                        <tr>
                            <td width="10%">${data->act_name!}</td>
                            <td width="10%">${data->pack_name!}</td>
                            <td width="15%">
                                ${data->start_time!}
                                至
                                ${data->end_time!}
                            </td>
                            <td width="5%">${data->v_ct!}</td>
                            <td width="5%">${data->v_amount!}</td>
                            <td width="5%">${data->total_amount!}</td>
                            <td width="10%">
                                <#if ($data->access_mode==0) 现金购买
                                <#elseif>($data->access_mode==1) 积分购买
                                <#else> 直接领取
                                </#if>
                            </td>
                            <td width="10%">
                                <#if ($data->access_mode==0) ${data->access_cost!}元
                                <#elseif>($data->access_mode==1) {{floor($data->access_cost)!}积分
                                <#else> 免费
                                </#if>
                            </td>
                            <td width="5%">${data->issued_amount!}</td>
                            <td width="10%">
                                <#if ($data->state==0)
                                    已停用
                                <#elseif>($data->end_time < date("Y-m-d H:i:s",time()))
                                    已过期
                                <#elseif>($data->start_time < date("Y-m-d H:i:s",time()))
                                    进行中
                                <#elseif>($data->start_time > date("Y-m-d H:i:s",time()))
                                    未开始
                                </#if>
                            </td>
                            <td width="15%" class="some_edit">
                                <a href="/admin/market/couponpackage/info?&id=${data->id!}">编辑 - </a>
                                <a href="##" identity_id="${data->id!}" class="hover_share" type="31">分享</a><br>
                                <#if ($data->end_time > date("Y-m-d H:i:s",time()) && $data->start_time < date("Y-m-d H:i:s",time()))
                                    <#if ($data->state == 1)
                                        <a href="/admin/market/couponpackage/action?&action=0&id=${data->id!}">停用</a><br>
                                    <#else>
                                        <a href="/admin/market/couponpackage/action?&action=1&id=${data->id!}">启用 -</a>
                                        <a pack_id="${data->id!}" href="#" class="del">删除</a><br>
                                    </#if>
                                </#if>
                                <a href="/admin/market/couponpackage/order?&id=${data->id!}">查看订单 - </a>
                                <a href="/admin/market/couponpackage/detail?&id=${data->id!}">领取明细</a>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <div class="paging_footer" style="margin-top: 12px;">
                    <#include "/admin/jump_page_admin.ftl">
                </div>
            </form>
        </div>

    </div>
</div>
<#include ('admin.share_common')
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
    $(document).on('click','.del',function(){
        var data = {};
        data.id = $(this).attr('pack_id');
        var pack_tr = $(this);

        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json("/admin/market/couponpackage/del",function(d){
                    if(d&&d.error==0){
                        util.mobile_alert(d.content);
                        pack_tr.parent().parent().remove();
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                },data);
                layer.close(index);
            });
        });
    })
    getPowerInfo('main_config','coupon_package','sub_4','优惠券包',0);
</script>