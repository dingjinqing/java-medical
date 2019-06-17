<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/packagesale_manage.css?v=1.0.4" type="text/css"/>
<style type="text/css">
    .no_data_style{
        margin-top: 10px !important;
    }
    .tb_paging{
        margin-top: 10px !important;
    }
    .tb_paging tr td{
        border:none !important;
    }
    .tb_paging input[type='text']{
        width: 80px;
        padding-left: 0;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">打包一口价</span>
        </div>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if (!($options['nav'] > 0)) class="active" </#if>>
                    <a href="/admin/market/package/list?nav=0">全部打包一口价活动</a>
                </li>
                <li <#if ($options['nav'] == 1) class="active" </#if>>
                    <a href="/admin/market/package/list?nav=1">进行中</a>
                </li>
                <li <#if ($options['nav'] == 2) class="active" </#if>>
                    <a href="/admin/market/package/list?nav=2">未开始</a>
                </li>
                <li <#if ($options['nav'] == 3) class="active" </#if>>
                    <a href="/admin/market/package/list?nav=3">已过期</a>
                </li>
                <li <#if ($options['nav'] == 4) class="active" </#if>>
                    <a href="/admin/market/package/list?nav=4">已停用</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="search_info">
                <ul class="clearfix">
                    <input name="nav" value="${options['nav']!}" hidden>
                    <li class="clearfix">
                        <div class="fl">
                            <span>活动名称</span>
                            <input type="text" placeholder="请输入活动名称" name="package_name" value="${options['package_name']!}">
                        </div>
                        <div class="fl" style="width: 510px">
                            <span>活动时间</span>
                            <input type="text"  name="start_time"  placeholder='选择日期'  onclick="picker();" autocomplete="off" value="${options['start_time']!}"/>
                            至
                            <input type="text"  name="end_time" placeholder='选择日期' onclick="picker();"  autocomplete="off" value="${options['end_time']!}"/>
                        </div>
                        <div class="fl btn_group">
                            <a href="##" class="btn_search">筛选</a>
                            <a href="##" class="btn_reset">重置</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="btn_add_act">
                <a href="{{url('/admin/market/package/define')!}">添加打包一口价活动</a>
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                        <tr>
                            <td width="10%">活动名称</td>
                            <td width="15%">活动时间</td>
                            <td width="15%">活动信息</td>
                            <td width="10%">已购商品数量</td>
                            <td width="10%">订单数量</td>
                            <td width="10%">下单用户数</td>
                            <td width="10%">活动状态</td>
                            <td width="10%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                    {{--{{dd($data_list)!}--!}
                    <#if ($data_list)
                        <#list ($data_list as $package)
                            <tr>
                                <td width="10%">${package->package_name!}</td>
                                <td width="15%">${package->start_time!}至${package->end_time!}</td>
                                <td width="15%">
                                    ${package->total_money!}元${package->goods_number_1+$package->goods_number_2+$package->goods_number_3!}件<br/>
                                    ${package->group_name_1!}:${package->goods_number_1!}件
                                    <#if ($package->goods_group_2) <br/>${package->group_name_2!}:${package->goods_number_2!}件 </#if>
                                    <#if ($package->goods_group_3) <br/>${package->group_name_3!}:${package->goods_number_3!}件 </#if>
                                </td>
                                <td width="10%">${package->sale_goods_number!}</td>
                                <td width="10%">${package->sale_order_number!}</td>
                                <td width="10%">${package->join_user_number!}</td>
                                <td width="10%">
                                    <#if ($package->show_status == 1)未开始
                                    <#elseif>($package->show_status == 2)已过期
                                    <#elseif>($package->show_status == 3)进行中
                                    <#else>已停用
                                    </#if>
                                </td>
                                <td width="10%">
                                    <#if (in_array($package->show_status,[1,3]))
                                        <a href="{{url('/admin/market/package/define?id='.$package->id)!}">编辑</a>
                                        <div class="erweima">
                                            <a href="javascript:void(0);" class="hover_share" identity_id="${package->id!}" type="25">分享</a>
                                        </div>
                                        <a class="stop_package" href="javascript:void(0);" id="${package->id!}">停用</a>
                                    </#if>
                                    <#if ($package->show_status == 0)
                                        <a class="start_package" href="javascript:void(0);" id="${package->id!}">启用</a>
                                    </#if>
                                    <#if (in_array($package->show_status,[0,2]))
                                        <a class="delete_package" href="javascript:void(0);" id="${package->id!}">删除</a>
                                    </#if>
                                    <#if (in_array($package->show_status,[0,2,3]))
                                        <a href="/admin/market/package/order?pin_group_id=${package->id!}">查看活动订单</a>
                                        <a href="/admin/market/package/detail?pin_group_id=${package->id!}">活动明细</a>
                                    </#if>

                                </td>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
                <#include ('admin.jump_page_admin')
            </div>
            <div>

            </div>
        </form>
    </div>
</div>
<#include ('admin.share_common')
<#include "/admin/footer.ftl">
<script type="text/javascript">
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $('.stop_package').click(function () {
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/package/update',function (res) {
                    if (res.error == 0) {
                        util.mobile_alert('已停用');
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                },{id:id,status:0})
            });
        });
    });
    $('.start_package').click(function () {
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/package/update',function (res) {
                    if (res.error == 0) {
                        util.mobile_alert('已启用');
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                },{id:id,status:1})
            });
        });
    });
    $('.delete_package').click(function () {
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/package/update',function (res) {
                    if (res.error == 0) {
                        util.mobile_alert('已删除');
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                },{id:id,status:2});
            });
        });
    });
    $('.btn_search').click(function () {
        $('#form1').submit();
    });
    $('.btn_reset').click(function () {
        $('[name="package_name"]').val('');
        $('[name="start_time"]').val('');
        $('[name="end_time"]').val('');
    });

    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>
<script>
    getPowerInfo('main_config','package_sale','sub_4','打包一口价',0);
</script>