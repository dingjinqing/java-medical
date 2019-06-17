<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.2" type="text/css" />
<style>
    .rebate_list td .erweima{
        position: relative;
    }
    .rebate_list td .erweima a{
        color: #5a8bff;
    }
    .paging_footer{
        margin-top: 0;
    }
    .paging_footer td{
        border:none !important;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
    <span style="color: #666;">加价购</span>
</div>
<div class="main-container">
    <div class="rebate_ul">
        <ul class="clearfix">
            <li <#if  (!isset($request['nav']) || $request['nav'] == 0) class="rebate_active" </#if>>
                <a href="/admin/market/purchase/list?nav=0">全部加价购</a>
            </li>
            <li <#if  ($request['nav'] == 1) class="rebate_active" </#if>><a href="/admin/market/purchase/list?nav=1">进行中</a></li>
            <li <#if  ($request['nav'] == 2) class="rebate_active" </#if>><a href="/admin/market/purchase/list?nav=2">未开始</a></li>
            <li <#if  ($request['nav'] == 3) class="rebate_active" </#if>><a href="/admin/market/purchase/list?nav=3">已过期</a></li>
            <li <#if  ($request['nav'] == 4) class="rebate_active" </#if>><a href="/admin/market/purchase/list?nav=4">已停用</a></li>
        </ul>
    </div>
    <div class="add_rebate">
        <a href="/admin/market/purchase/add" target="_blank">添加加价购活动</a>
    </div>
</div>
<div class="main-container" style="padding-top: 0px;">
    <div class="rebate_list">
        <form action="/admin/market/purchase/list?nav=${request['nav']!}" method="post" id="form1">
            {{ csrf_field()!}
            <div class="increase_choose">
                <div class="increase_list1 clearfix">
                    <div class="fl">
                        活动名称：<input type="text" placeholder="请输入活动名称" name="name" value="${request['name']!}"/>
                    </div>
                    <div class="fl">
                        活动时间：
                        <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${request['start_time']!}"/>
                        &nbsp;至&nbsp;&nbsp;
                        <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${request['end_time']!}"/>
                    </div>
                </div>
                <div class="increase_list2 clearfix">
                    <div class="fl">
                        加价购条件：满 &nbsp;<input type="text" name="min_full_price" value="${request['min_full_price']!}"/>&nbsp;元至 &nbsp;<input type="text" name="max_full_price" value="${request['max_full_price']!}"/>&nbsp;元
                    </div>
                    <div class="fl">
                        换购条件：满 &nbsp;<input type="text" name="min_purchase_price" value="${request['min_purchase_price']!}"/>&nbsp;元至 &nbsp;<input type="text" name="max_purchase_price" value="${request['max_purchase_price']!}"/>&nbsp;元
                        <button class="btn_inrease">筛选</button>
                    </div>

                </div>
            </div>
            <table width="100%">
                <tr class="rebate_first">
                    <td>活动名称</td>
                    <td>活动时间</td>
                    <td>活动优先级</td>
                    <td>活动信息</td>
                    <td>单笔最大换购数量</td>
                    <td>已换购商品数量</td>
                    <td>操作</td>
                </tr>
                <#list  ($list as $item)
                <tr class="rebate_tr" item="${item->id!}">
                    <td>${item->name!}</td>
                    <td>
                        ${item->start_time!} <br/>
                        至<br/>
                        ${item->end_time!}
                    </td>
                    <td>
                        <input type="text" class="change-input" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${item->level!}" disabled />
                        <img src="/image/admin/good_edit.png" class="goods-number-img">
                    </td>
                    <td>
                        <#list  ($item->rule as $rule)
                            满${rule->full_price!}加价${rule->purchase_price!}换购<br/>
                        </#list>
                    </td>
                    <td>
                        <#if  ($item->max_change_purchase > 0)
                            ${item->max_change_purchase!}
                            <#else>
                            不限制
                        </#if>
                    </td>
                    <td>${item->change_purchase_sum!}</td>
                    <td>
                        <#if  (in_array($item->nav, [1, 2]))
                            <a href="/admin/market/purchase/add?id=${item->id!}" target="_blank"> 编辑 </a>
                            <div class="erweima">
                                <a href="javascript:void(0);" class="hover_share" identity_id="${item->id!}" type="21">分享</a>
                            </div>
                        </#if>
                        <#if  (!in_array($item->nav, [3, 4]) && $item->status == 1)
                            <a href="#" class="close_purchase" item="${item->id!}"> 停用 </a>
                        </#if>
                        <#if  ($item->is_enable == 1)
                            <a href="#" class="enable" item="${item->id!}"> 启用 </a>
                        </#if>
                        <a href="/admin/orders/manage/list?purchase_price_id=${item->id!}&purchase_price_order=1&top_index=4">查看换购订单</a>
                        <a href="/admin/market/purchase/record?purchase_price_id=${item->id!}">换购明细</a>
                        <#if  (in_array($item->nav, [3, 4]))
                            <a href="#" class="del" item="${item->id!}"> 删除 </a>
                        </#if>
                    </td>
                </tr>
                </#list>
            </table>
        </form>
        <div class="paging_footer" style="margin-top: 12px;">
            <#include "/admin/jump_page_admin.ftl">
        </div>
    </div>

</div>
<#include ('admin.share_common')
<#include "/admin/footer.ftl">
<script type="text/javascript">
    function picker(){
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $(document).on('click','.goods-number-img',function () {
        $(this).prev().addClass('ipt-change');
        $(this).prev().attr("disabled", false);
        $(this).prev().focus();
        $(this).hide();
    });

    $(".enable").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/admin/market/purchase/list', function(response){
                    if(response && response.error == 0){
                        window.location.reload();
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"enable", id:that.attr('item')});
                layer.close(index);
            });
        });
    });
    $(".close_purchase").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/admin/market/purchase/list', function(response){
                    if(response && response.error == 0){
                        window.location.reload();
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"disable", id:that.attr('item')});
                layer.close(index);
            });
        });
    });
    $(".del").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/admin/market/purchase/list', function(response){
                    if(response && response.error == 0){
                        window.location.reload();
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"del", id:that.attr('item')});
                layer.close(index);
            });
        });
    });
    var level;
    $(".change-input").focus(function () {
        level = $(this).val();
    });
    $(".change-input").blur(function (e) {
        var new_level = $(this).val();
        if(Number(new_level) == Number(level) && new_level != ''){
            $('.ipt-change').next().show();
            $('.change-input').removeClass('ipt-change');
            $(".change-input").attr("disabled",true);
        }
    });
    $(".change-input").change(function(e){
        var purchase_price_id = $(this).parent().parent().attr('item');
        var level = $(this).val();
        var that = $(this);
        if (level == '') {
            util.mobile_alert('优先级不能为空！');
            $(this).focus();
            return false;
        }
        util.ajax_json('/admin/market/purchase/list', function(response){
            if (response && response.error == 0){
                util.mobile_alert('设置成功');
                that.removeClass('ipt-change');
                that.next().show();
                $(".change-input").attr("disabled", true);
                window.location = '/admin/market/purchase/list?nav=${request['nav']!}';
            }else{
                util.mobile_alert(response.message);
            }
        },{act:"set_level", id:purchase_price_id, level: level});
    });
    getPowerInfo('main_config','purchase_price','sub_4','加价购',0);
</script>