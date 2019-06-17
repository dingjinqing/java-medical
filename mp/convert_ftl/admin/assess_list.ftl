<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css" />
<style type="text/css">
    .tb_paging tr td {
        border: none;
    }

    .tb_paging {
        margin-top: 10px;
    }

    #page {
        width: 80px;
        padding-left: 0;
    }

    .operate>a {
        display: inline-block !important;
    }

    .operate>a+a {
        margin-left: 10px;
    }
    .no_data_style{
        margin-top: 10px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">测评</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li class="<#if (!($options['nav'] > 0)) active </#if>">
                    <a href="/admin/market/assess/list?nav=0">全部测评</a>
                </li>
                <li class="<#if ($options['nav'] == 1) active </#if>">
                    <a href="/admin/market/assess/list?nav=1">进行中</a>
                </li>
                <li class="<#if ($options['nav'] == 2) active </#if>">
                    <a href="/admin/market/assess/list?nav=2">未开始</a>
                </li>
                <li class="<#if ($options['nav'] == 3) active </#if>">
                    <a href="/admin/market/assess/list?nav=3">已过期</a>
                </li>
                <li class="<#if ($options['nav'] == 4) active </#if>">
                    <a href="/admin/market/assess/list?nav=4">已停用</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="btn_add_act">
                <a href="{{url('/admin/market/assess/add')!}">添加测评</a>
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                        <tr>
                            <td width="20%">活动名称</td>
                            <td width="12%">创建时间</td>
                            <td width="10%">反馈数</td>
                            <td width="12%">有效期</td>
                            <td width="10%">活动状态</td>
                            <td width="20%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#list ($data_list as $item)
                        <tr assess_id="${item->id!}">
                            <td>${item->act_name!}</td>
                            <td>${item->in_time!}</td>
                            <td><a href="<#if ($item->feedback_num > 0) /admin/market/assess/feedbacklist?assess_id=${item->id!} <#else> javascript:; </#if>">${item->feedback_num!}</a></td>
                            <td><#if ($item->due_time_type == 1)永久有效 <#else> ${item->start_time!}<br />至<br />${item->end_time!} </#if></td>
                            <td>${item->act_status_name!}-${item->pub_name!}</td>
                            <td class="operate">
                                <#if ($item->act_status < 2 && $item->pub_flag == 0)
                                <a href="javascript:;" class="pub">发布</a>
                                </#if>
                                <a href="/admin/market/assess/add?assess_id=${item->id!}">编辑</a>
                                <#if ($item->act_status < 2 )
                                <a href="javascript:;" class="hover_share" identity_id="${item->id!}" type="30">分享</a>
                                </#if>
                                <a href="javascript:;" class="block" is_block="${item->is_block!}">${item->block_name!}</a>
                                <#if ($item->act_status >= 2 && $item->del_flag == 0)
                                <a href="javascript:;" class="del" >删除</a>
                                </#if>
                                <br>
                                <a href="/admin/market/assess/result/list?assess_id=${item->id!}">结果管理</a>
                                <a href="/admin/market/assess/topic/list?assess_id=${item->id!}">题目管理</a>
                                <br>
                                <#if ($item->feedback_num > 0)
                                <a href="/admin/market/assess/feedbacklist?assess_id=${item->id!}">查看反馈</a>
                                <a href="/admin/market/assess/summary?assess_id=${item->id!}">测评统计</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <#include "/admin/jump_page_admin.ftl">
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
        return WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' });
    }
    $('.block').click(function () {
        var assess_id = $(this).parent().parent().attr('assess_id');
        var is_block = $(this).attr('is_block');
        var title = $(this).text();
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要'+title+'该评测吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {};
                param.act = 'block';
                param.assess_id = assess_id;
                param.is_block = is_block == 1 ? 0 : 1;
                util.ajax_json('/admin/market/assess/save', function (res) {
                    if (res.error == 0) {
                        util.mobile_alert(res.message);
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                }, param)
            });
        });
    });

    //发布
    $('.pub').click(function () {
        var assess_id = $(this).parent().parent().attr('assess_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要发布该评测吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {};
                param.act = 'pub';
                param.assess_id = assess_id;
                util.ajax_json('/admin/market/assess/save', function (res) {
                    if (res.error == 0) {
                        util.mobile_alert(res.message);
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                }, param)
            });
        });
    });

    //删除
    $('.del').click(function () {
        var assess_id = $(this).parent().parent().attr('assess_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该评测吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {};
                param.act = 'del';
                param.assess_id = assess_id;
                util.ajax_json('/admin/market/assess/save', function (res) {
                    if (res.error == 0) {
                        util.mobile_alert(res.message);
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                }, param)

            });
        });
    });

    $('.btn_copy').click(function (e) {
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>
<script type="text/javascript">
    getPowerInfo('main_config','assess','sub_4','测评',0);
</script>