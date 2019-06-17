<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.2" type="text/css" />
<style>
    .paging_footer{
        margin-top: 0px;
    }
    .paging_footer table td{
        border: none;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;">分销策略列表页</span>
</div>
<div class="main-container">
    <div class="pages_nav clearfix">
        <#include ("admin.distributio_title")
    </div>
    <div class="rebate_ul">
        <ul class="clearfix">
            <li <#if ($nav == 0)class="rebate_active"</#if>>
                <a href="/admin/market/distribution/strategy/list?top_index=4">全部策略</a>
            </li>
            <li <#if ($nav == 1)class="rebate_active"</#if>>
                <a href="/admin/market/distribution/strategy/list?top_index=4&nav=1">进行中</a>
            </li>
            <li <#if ($nav == 2)class="rebate_active"</#if>>
                <a href="/admin/market/distribution/strategy/list?top_index=4&nav=2">未开始</a>
            </li>
            <li <#if ($nav == 3)class="rebate_active"</#if>>
                <a href="/admin/market/distribution/strategy/list?top_index=4&nav=3">已过期</a>
            </li>
            <li <#if ($nav == 4)class="rebate_active"</#if>>
                <a href="/admin/market/distribution/strategy/list?top_index=4&nav=4">已停用</a>
            </li>
        </ul>
    </div>
    <div class="add_rebate">
        <a href="/admin/market/distribution/strategy/config" target="_blank">添加返利策略</a>
    </div>
</div>
<div class="main-container" style="padding-top: 0px;">
    <form action="/admin/market/distribution/strategy/list?top_index=4" method="post" id="form1">
        {{csrf_field()!}
        <input hidden name="nav" value="${nav!}">
        <div class="rebate_list">
            <table width="100%">
                <tr class="rebate_first">
                    <td width="15%">返利策略名称</td>
                    <td width="20%">有效期</td>
                    <td width="10%">返利比例</td>
                    <td width="10%">优先级</td>
                    <td width="15%">创建时间</td>
                    <td width="15%">状态</td>
                    <td width="15%">操作</td>
                </tr>
                <#if ($data_list)
                    <#list ($data_list as $item)
                        <tr class="rebate_tr">
                            <td>${item->strategy_name!}</td>
                            <td>${item->start_time!}<br/>至<br/>${item->end_time!}</td>
                            <td>${item->fanli_ratio!}%</td>
                            <td>${item->strategy_level!}</td>
                            <td>${item->add_time!}</td>
                            <td>${item->status_name!}</td>
                            <td>
                                <input hidden name="close_id">
                                <input hidden name="delete_id">
                                <input hidden name="restart_id">
                                <#if (in_array($item->show_status,[1,2]))
                                    <a style="display: inline-block;" href="/admin/market/distribution/strategy/config?id=${item->id!}">编辑</a>
                                    <a style="display: inline-block;" href="javascript:void(0)" class="btn_close" id="${item->id!}">停用</a>
                                </#if>
                                <#if (in_array($item->show_status,[4]))
                                    <a  style="display: inline-block;" href="javascript:void(0)" class="btn_restart" id="${item->id!}" end_time="${item->end_time!}">启用</a>
                                </#if>
                                <#if (in_array($item->show_status,[2,3,4]))
                                    <a style="display: inline-block;" href="javascript:void(0)" class="btn_delete" id="${item->id!}">删除</a>
                                </#if>

                            </td>
                        </tr>
                    </#list>
                </#if>

            </table>
            <div class="paging_footer" style="margin-top: 12px">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>

    </form>
</div>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script type="text/javascript">


    $(".btn_close").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $("input[name='close_id']").val($(_this).attr("id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    $(".btn_delete").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $("input[name='delete_id']").val($(_this).attr("id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    $(".btn_restart").click(function () {
        var _this = $(this);
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
        if(_this.attr("end_time")<= now){
            util.mobile_alert("该活动已过期，不可启用");
            return false;
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $("input[name='restart_id']").val($(_this).attr("id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>