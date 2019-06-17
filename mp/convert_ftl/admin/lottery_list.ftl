<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/lottery_manage.css?v=1.0.0" type="text/css" />
<style>
    .lottery_table .paging_footer{
        padding: 0px;
    }
    .lottery_table .paging_footer table tr td{
        border: 0px;
    }

    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .new_marketing:hover{
        color:#666;
        text-decoration: underline;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4" class="new_marketing">营销管理</a> / </span><span style="color: #666;">幸运大抽奖</span>
</div>
<div class="main-container">
    <div class="list_nav">
        <ul class="nav_lists clearfix">
            <li <#if ($option['nav'] == 0)class="actives"</#if>><a href="/admin/market/lottery/list?top_index=4">全部抽奖活动</a></li>
            <li <#if ($option['nav'] == 1)class="actives"</#if>><a href="/admin/market/lottery/list?top_index=4&nav=1">进行中</a></li>
            <li <#if ($option['nav'] == 2)class="actives"</#if>><a href="/admin/market/lottery/list?top_index=4&nav=2">未开始</a></li>
            <li <#if ($option['nav'] == 3)class="actives"</#if>><a href="/admin/market/lottery/list?top_index=4&nav=3">已过期</a></li>
            <li <#if ($option['nav'] == 4)class="actives"</#if>><a href="/admin/market/lottery/list?top_index=4&nav=4">已停用</a></li>
        </ul>
    </div>
    <div class="add_lottery">
        <a href="/admin/market/lottery/config?top_index=4">添加抽奖活动</a>
    </div>
</div>
<div class="main-container" style="padding-top: 0px;">
    <form action="/admin/market/lottery/list" method="post" id="form1">
        {{csrf_field()!}
        <input name="nav" hidden value="${option['nav']!}">
        <div class="lottery_table" style="padding-top: 10px">
            <table width="100%">
                <thead>
                    <td width="15%">活动名称</td>
                    <td width="20%">有效期</td>
                    <td width="15%">活动状态</td>
                    <td width="10%">参与人次</td>
                    <td width="10%">中奖人次</td>
                    <td width="20%">操作</td>
                </thead>
                <tbody>
                    <#if ($data_list)
                        <#list ($data_list as $item)
                        <tr>
                            <td width="15%">${item->lottery_name!}</td>
                            <td width="20%">${item->start_time!}<br/>至<br/>${item->end_time!}</td>
                            <td width="15%">${item->status_name!}</td>
                            <td width="10%">${item->join_number!}</td>
                            <td width="10%">${item->award_number!}</td>
                            <td width="20%" class="tb-decorate-a">
                                <input hidden name="close_id">
                                <input hidden name="delete_id">
                                <input hidden name="restart_id">
                                <#if (in_array($item->show_status,[1,2]))
                                    <a href="/admin/market/lottery/config?top_index=4&id=${item->id!}" class="btn_edit">编辑</a>
                                    <div class="erweima">
                                            <a href="javascript:void(0)" identity_id="${item->id!}" class="btn_share hover_share" type="17">分享</a>
                                    </div>

                                    <a href="javascript:void(0)" class="btn_close" id="${item->id!}">停用</a>
                                </#if>
                                <#if (in_array($item->show_status,[3,4]))
                                    <a href="javascript:void(0)" class="btn_delete" id="${item->id!}">删除</a>
                                </#if>
                                <#if (in_array($item->show_status,[4]))
                                    <a href="javascript:void(0)" class="btn_restart" id="${item->id!}" end_time="${item->end_time!}">启用</a>
                                </#if>
                                <#if (in_array($item->show_status,[1,3,4]))
                                    <a href="/admin/market/lottery/detail?top_index=4&id=${item->id!}" class="btn_detail">抽奖明细</a>
                                    <a href="/admin/user/source/detail?source=lottery&act_id=${item->id!}&top_index=4&sub_index=${sub_index!}" class="btn_user">获取新用户明细</a>
                                </#if>

                            </td>
                        </tr>
                        </#list>
                    </#if>
                </tbody>
            </table>
            <div class="paging_footer">
             <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </form>
</div>
<#include ('admin.share_common')
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

    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<#include "/admin/footer.ftl">
<script>
    getPowerInfo('main_config','lottery','sub_4','抽奖',0);
</script>