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
    .operate > a+a{
        margin-left:15px;
    }
    .tips{
        position: absolute;
        background: url('/image/admin/decorate_tips.png');
        width: 38px;
        height: 24px;
        padding: 0;
        color: #fff;
        font-size: 12px;
        line-height: 19px;
        text-align: center;
        top: -28px;
        left: -12px;
    }
    .fix_footer a{
        display:inline-block;
    }
    .fix_footer .back,.fix_footer .back:hover,.fix_footer .back:active,.fix_footer .back:visited,.fix_footer .back:link {
        background:#fff;
        border:1px solid #666;
        color:#666;
    }
    .fix_footer .save {
        border:1px solid #5a8bff;
    }
    .fix_footer{
        width:calc(100% - 170px) !important;
    }
    .no_data_style{
        margin-top: 10px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">${title!}</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li class="<#if ($options['nav'] == 0) active </#if>">
                    <a href="/admin/market/comment/gift/list?nav=0">全部评价有礼</a>
                </li>
                <li class="<#if ($options['nav'] == 1) active </#if>">
                    <a href="/admin/market/comment/gift/list?nav=1">进行中</a>
                </li>
                <li class="<#if ($options['nav'] == 2) active </#if>">
                    <a href="/admin/market/comment/gift/list?nav=2">未开始</a>
                </li>
                <li class="<#if ($options['nav'] == 3) active </#if>">
                    <a href="/admin/market/comment/gift/list?nav=3">已过期</a>
                </li>
                <li class="<#if ($options['nav'] == 4) active </#if>">
                    <a href="/admin/market/comment/gift/list?nav=4">已停用</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="btn_add_act">
                <a href="{{url('/admin/market/comment/gift/add')!}">添加评价有礼</a>                
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                        <tr>
                            <td>活动名称</td>
                            <td>触发条件</td>
                            <td>活动有效期</td>
                            <td>活动奖励</td>
                            <td>活动状态</td>
                            <td width="15%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#list ($data_list as $item)
                        <tr>
                            <td>${item->name!}</td>
                            <td>${item->award_desc!}</td>
                            <td>
                            <#if ($item->is_forever == 1)
                            永久有效
                            <#else>
                                ${item->start_time!}<br/>至<br/>${item->end_time!}
                            </#if>
                            </td>
                            <td>
                                <#if ($item->award_type == 1)
                                积分
                                <#elseif>($item->award_type == 2)
                                优惠券
                                <#elseif>($item->award_type == 3)
                                余额
                                <#elseif>($item->award_type == 4)
                                幸运大抽奖
                                <#else>
                                自定义
                                </#if>
                            </td>
                            <td> 
                                <#if ($item->act_status == 1)
                                进行中
                                <#elseif>($item->act_status == 2)
                                未开始
                                <#elseif>($item->act_status == 3)
                                已过期
                                <#elseif>($item->act_status == 4)
                                未启用
                                </#if>
                            </td>
                            <td class="operate">
                                <#if ($item->act_status == 4)
                                <a href="javascript:;" style="display: inline; position: static;" class="start" act_id="${item->id!}">启用</a>
                                </#if>
                                <#if (in_array($item->act_status, [1, 2]))
                                <a href="javascript:;" style="display: inline; position: static;" class="stop" act_id="${item->id!}">停用</a>
                                <a href="/admin/market/comment/gift/add?id=${item->id!}" style="display: inline; position: static;">编辑</a>
                                </#if>
                                <#if (in_array($item->act_status, [3, 4]))
                                <a href="javascript:;" style="display: inline; position: static;" class="del" act_id="${item->id!}">删除</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <#include ('admin.jump_page_admin')
            </div>
        </form>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    
    $('.operate').on('click','a',function(){
        let type = $(this).attr('class');
        let act_id = $(this).attr('act_id')
        if(type && act_id){
            let msg = ''
            if(type == 'start'){
                msg = '确认要启用该活动吗？'
            }else if(type == 'stop'){
                msg = '确认要停用该活动吗？'
            }else if(type == 'del'){
                msg = '确认要删除该活动吗？'
            }
            layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + msg + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/ajax/ca/status', function (res) {
                    if (res.error == 0) {
                        window.location.reload();
                    } else {
                        util.mobile_alert(res.message)
                    }
                    layer.close(index);
                }, {act:type,act_id:act_id})
            });
        });
        }
    })
    // //删除
    // $('.del').click(function () {

    //     var act_id = $(this).attr('act_id');
    //     layui.use('layer', function () {
    //         var layer = layui.layer;
    //         layer.alert('<div style="text-align: center;">' + '确认要删除该活动吗？' + '</div>', {
    //             title: ['提醒', 'text-align:center;padding: 0px;']
    //             , area: '260px'
    //             , closeBtn: 0
    //             , btn: ['确定', '取消']
    //         }, function (index) {
    //             util.ajax_json('', function (res) {
    //                 if (res.error == 0) {
                        
    //                 } else {
    //                     util.mobile_alert(res.message)
    //                 }
    //                 layer.close(index);
    //             }, {act:'del',act_id:act_id})
    //         });
    //     });
    // });
    // //停用
    // $('.stop').click(function () {
    //     var act_id = $(this).attr('act_id');
    //     layui.use('layer', function () {
    //         var layer = layui.layer;
    //         layer.alert('<div style="text-align: center;">' + '确认要停用该活动吗？' + '</div>', {
    //             title: ['提醒', 'text-align:center;padding: 0px;']
    //             , area: '260px'
    //             , closeBtn: 0
    //             , btn: ['确定', '取消']
    //         }, function (index) {
    //             util.ajax_json('', function (res) {
    //                 if (res.error == 0) {
                        
    //                 } else {
    //                     util.mobile_alert(res.message)
    //                 }
    //                 layer.close(index);
    //             }, {act:'stop',act_id:act_id})
    //         });
    //     });
    // });
</script>
{{--<script>--!}
    {{--getPowerInfo('main_config', 'comment_gift', 'sub_4', '评价有礼', 0);--!}
{{--</script>--!}