<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css"/>
<style type="text/css">
    .tb_paging tr td{
        border: none;
    }
    .tb_paging{
        margin-top: 10px;
    }
    #page{
        width: 80px;
        padding-left: 0;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">定金膨胀</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li class="<#if (!($options['nav'] > 0)) active </#if>">
                    <a href="/admin/market/presale/list?nav=0">全部定金膨胀</a>
                </li>
                <li class="<#if ($options['nav'] == 1) active </#if>">
                    <a href="/admin/market/presale/list?nav=1">进行中</a>
                </li>
                <li class="<#if ($options['nav'] == 2) active </#if>">
                    <a href="/admin/market/presale/list?nav=2">未开始</a>
                </li>
                <li class="<#if ($options['nav'] == 3) active </#if>">
                    <a href="/admin/market/presale/list?nav=3">已过期</a>
                </li>
                <li class="<#if ($options['nav'] == 4) active </#if>">
                    <a href="/admin/market/presale/list?nav=4">已停用</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="search_info">
                <ul class="clearfix">
                    <li class="clearfix">
                        <input name="nav" value="${options['nav']!}" hidden>
                        <div class="fl">
                            <span>活动名称</span>
                            <input type="text" name="presale_name" value="${options['presale_name']!}" placeholder="请输入活动名称">
                        </div>
                        <div class="fl" style="width: 520px">
                            <span>定金支付时间</span>
                            <input type="text" name="pre_start_time" placeholder='选择日期' value="${options['pre_start_time']!}" onclick="picker();" autocomplete="off" />
                            至
                            <input type="text" name="pre_end_time_2" placeholder='选择日期' value="${options['pre_end_time_2']!}" onclick="picker();"  autocomplete="off" />
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="fl" style="width: 520px">
                            <span>尾款支付时间</span>
                            <input type="text" name="start_time" placeholder='选择日期' value="${options['start_time']!}" onclick="picker();" autocomplete="off" />
                            至
                            <input type="text" name="ent_time" placeholder='选择日期' value="${options['ent_time']!}" onclick="picker();"  autocomplete="off" />
                        </div>
                        <div class="fl btn_group">
                            <a href="##" class="btn_search">筛选</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="btn_add_act">
                <a href="{{url('/admin/market/presale/define')!}">添加定金膨胀活动</a>
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                        <tr>
                            <td width="10%">活动名称</td>
                            <td width="12%">定金支付时间</td>
                            <td width="12%">尾款支付时间</td>
                            <td width="6%">已购商品数量</td>
                            <td width="6%">订单数量</td>
                            <td width="6%">已付定金订单数</td>
                            <td width="6%">已付尾款订单数</td>
                            <td width="6%">下单用户数</td>
                            <td width="8%">活动状态</td>
                            <td width="12%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#if  ($data_list)
                            <#list ($data_list as $preSale)
                                <tr>
                                    <td width="9%">${preSale->presale_name!}</td>
                                    <td width="13%">
                                        <div class="each_time">
                                            ${preSale->pre_start_time!}<br/>至<br/>${preSale->pre_end_time!}
                                        </div>
                                        <#if  ($preSale->pre_pay_step == 2)
                                            <div class="each_time">
                                                ${preSale->pre_start_time_2!}<br>至</br>${preSale->pre_end_time_2!}
                                            </div>
                                        </#if>
                                    </td>
                                    <td width="12%">
                                        <#if  ($preSale->presale_type != 1)
                                            ${preSale->start_time!}<br>至</br>${preSale->end_time!}
                                        </#if>
                                    </td>
                                    <td width="6%">${preSale->buy_goods_number!}</td>
                                    <td width="6%">${preSale->order_num!}</td>
                                    <td width="6%">${preSale->presale_money!}</td>
                                    <td width="6%">${preSale->final_money!}</td>
                                    <td width="6%">${preSale->order_user_num!}</td>
                                    <td width="8%">
                                        <#if  ($preSale->show_status == 1) 未开始
                                        <#elseif> ($preSale->show_status == 2) 已结束
                                        <#elseif> ($preSale->show_status == 3) 进行中
                                        <#else> 已停用
                                        </#if>
                                    </td>
                                    <td width="12%">
                                        <#if  (in_array($preSale->show_status,[1,3]))
                                            <a href="{{url('/admin/market/presale/define?presale_id='.$preSale->id)!}">编辑</a>
                                            <div class="erweima">
                                                <a href="##" class="hover_share" identity_id="${preSale->goods_id!}" extend_info="{{json_encode(['presale_id'=>$preSale->id])!}" type="24">分享</a>
                                            </div>
                                            <a class="stop_presale" href="##" id="${preSale->id!}">停用</a>
                                        </#if>
                                        <#if  ($preSale->show_status == 4)
                                            <a href="##" class="start_presale" id="${preSale->id!}">启用</a>
                                        </#if>
                                        <#if  (in_array($preSale->show_status,[2,4]))
                                            <a href="##" class="delete_presale" id="${preSale->id!}">删除</a>
                                        </#if>
                                        <#if  (in_array($preSale->show_status,[2,3,4]))
                                            <a href="/admin/market/presale/order?pin_group_id=${preSale->id!}">查看活动订单</a>
                                            <a href="/admin/market/presale/detail?pin_group_id=${preSale->id!}">活动明细</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
                <#include ('admin.jump_page_admin')
            </div>
        </form>
    </div>
</div>
<#include ('admin.share_common')
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $(".btn_search").click(function () {
        $("#form1").submit();
    });
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    }
    $('.stop_presale').click(function () {
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/presale/update',function (res) {
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
    $('.start_presale').click(function () {
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/presale/update',function (res) {
                    if (res.error == 0) {
                        util.mobile_alert('已启用');
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                },{id:id,status:1})

            });
        });
    });
    $('.delete_presale').click(function () {
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/presale/update',function (res) {
                    if (res.error == 0) {
                        util.mobile_alert('已删除');
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                },{id:id,status:2})

            });
        });
    });
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>
<script>
    /*getPowerInfo('main_config','pre_sale','sub_4','定金膨胀',0);*/
</script>