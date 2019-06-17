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
                <li>
                    <a href="/admin/market/assess/list?nav=0">全部测评</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=1">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=2">未开始</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=3">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=4">已停用</a>
                </li>
                <li class="active">
                    <a href="javascript:;">${title!}</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="btn_add_act">
                <a <#if ($pub_flag != 1)href="{{url('/admin/market/assess/topic/add?assess_id='.$assess_id)!}" </#if>>添加题目</a>
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                        <tr>
                            <td>编号</td>
                            <td>题目名称</td>
                            <td>选项类型</td>
                            <td width="25%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#list ($data_list as $key=> $item)
                        <tr>
                            <td>${key+1!}</td>
                            <td>${item->topic_title!}</td>
                            <td>${item->topic_type_name!}、${item->option_type_name!}</td>
                            <td class="operate" topic_id="${item->id!}" topic_level="${item->topic_level!}">
                                <a href="/admin/market/assess/topic/add?topic_id=${item->id!}&assess_id=${assess_id!}" class="show_tip" target="_blank" data-tips="编辑" style="display: inline; position: static;"><img src="/image/admin/decorate_editor.png" alt=""></a> 
                                <#if ($pub_flag != 1)
                                <a href="javascript:;" class="show_tip del" data-tips="删除" style="display: inline; position: static;"><img src="/image/admin/decorate_delete.png" alt=""></a>
                                <a href="javascript:;" class="show_tip copy" data-tips="复制" style="display: inline; position: static;"><img src="/image/admin/decorate_copy.png" alt=""></a>
                                <a href="javascript:;" class="show_tip prev" data-tips="上移" data-index="${key!}" style="display: inline; position: static;"><img src="/image/admin/up.png" alt=""></a>
                                <a href="javascript:;" class="show_tip next" data-tips="下移" data-index="${key!}" data-index="" style="display: inline; position: static;"><img src="/image/admin/down.png" alt=""></a>
                                </#if>

                                <a href="/admin/market/assess/topic/summary?topic_id=${item->id!}" style="display: inline; position: static;">选项统计</a>           
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <#include ('admin.jump_page_admin')
            </div>
        </form>
        <div class="btn_save fix_footer" style="width: 1199px; display: block;">
            <#if ($assess_judge_type == 1)
            <a class="back" href="{{url('/admin/market/assess/add?assess_id='.$assess_id)!}">上一步</a>
            <a class="save" style="margin-left:30px" href="{{url('/admin/market/assess/result/list?assess_id='.$assess_id)!}">下一步</a>
            <#else>
            <a class="back" href="{{url('/admin/market/assess/result/list?assess_id='.$assess_id)!}">上一步</a>
            <a class="save" style="margin-left:30px" href="{{url('/admin/market/assess/list?assess_id='.$assess_id)!}">去发布</a>
            </#if>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('.operate').on("mouseenter",'a.show_tip img',function(){
        $(this).parent().css({
            "display":"inline-block",
            "position" : "relative"
        })
        $('<div class="tips"></div>').html($(this).parent().data('tips')).appendTo(this.parentElement);
    }).on('mouseleave','a.show_tip img',function(){
        $(this).parent().css({
            "display":"inline",
            "position":"static"
        })
        $(this).parent().find('.tips').remove();
    });

    //上移
    $('.content_table').on('click','.prev',function(){
        
        if($(this).parents('tr').prev().length != 0){
            var obj = $(this);
            var self_topic_id    = obj.parent().attr('topic_id');
            var self_topic_level = obj.parent().attr('topic_level');

            var move_topic_id    = obj.parents('tr').prev().find('.operate').attr('topic_id');
            var move_topic_level = obj.parents('tr').prev().find('.operate').attr('topic_level');

            var param = {};
            param.self_topic_id    = self_topic_id;
            param.self_topic_level = move_topic_level;
            param.move_topic_id    = move_topic_id;
            param.move_topic_level = self_topic_level;
            util.ajax_json('/admin/market/assess/topic/move', function (res) {
                if (res.error == 0) {
                    util.mobile_alert(res.message)
                    let beforeIndex = obj.parents('tr').prev().find('.prev').attr('data-index');
                    let index =  obj.parents('tr').find('.prev').attr('data-index');
                    obj.parent().attr('topic_level',move_topic_level);
                    obj.parents('tr').prev().find('.operate').attr('topic_level',self_topic_level);
                    obj.parents('tr').find('.prev,.next').attr('data-index',beforeIndex);
                    obj.parents('tr').find('td:eq(0)').text(parseInt(beforeIndex) + 1);
                    obj.parents('tr').prev().find('.prev,.next').attr('data-index',index);
                    obj.parents('tr').prev().find('td:eq(0)').text(parseInt(index) + 1);
                    obj.parents('tr').prev().before(obj.parents('tr'));

                    
                } else {
                    util.mobile_alert(res.message)
                }
            }, param);
        }
    })

    //下移
    $('.content_table').on('click','.next',function(){
        
        if($(this).parents('tr').next().length != 0){

            var obj = $(this);
            var self_topic_id    = obj.parent().attr('topic_id');
            var self_topic_level = obj.parent().attr('topic_level');

            var move_topic_id    = obj.parents('tr').next().find('.operate').attr('topic_id');
            var move_topic_level = obj.parents('tr').next().find('.operate').attr('topic_level');

            var param = {};
            param.self_topic_id    = self_topic_id;
            param.self_topic_level = move_topic_level;
            param.move_topic_id    = move_topic_id;
            param.move_topic_level = self_topic_level;
            util.ajax_json('/admin/market/assess/topic/move', function (res) {
                if (res.error == 0) {
                    util.mobile_alert(res.message)
                    let afterIndex = obj.parents('tr').next().find('.prev').attr('data-index');
                    let index =  obj.parents('tr').find('.prev').attr('data-index');
                    obj.parent().attr('topic_level',move_topic_level);
                    obj.parents('tr').next().find('.operate').attr('topic_level',self_topic_level);
                    obj.parents('tr').find('.prev,.next').attr('data-index',afterIndex);
                    obj.parents('tr').find('td:eq(0)').text(parseInt(afterIndex) + 1);
                    obj.parents('tr').next().find('.prev,.next').attr('data-index',index);
                    obj.parents('tr').next().find('td:eq(0)').text(parseInt(index) + 1);
                    obj.parents('tr').next().after(obj.parents('tr'));

                    

                } else {
                    util.mobile_alert(res.message)
                }
            }, param);    
        }
    })

    //删除
    $('.del').click(function () {
        var topic_id = $(this).parent().attr('topic_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该测评题目吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {};
                param.act = 'del';
                param.topic_id = topic_id;
                util.ajax_json('/admin/market/assess/topic/save', function (res) {
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

    //复制
    $('.copy').click(function () {
        var topic_id = $(this).parent().attr('topic_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要复制该测评题目吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {};
                param.topic_id  = topic_id;
                param.assess_id = ${assess_id!};
                util.ajax_json('/admin/market/assess/topic/copy', function (res) {
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
</script>
<script type="text/javascript">
    getPowerInfo('main_config','assess','sub_4','测评',0);
</script>