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
            <div class="btn_add_act" >
                <a <#if ($pub_flag != 1) href="{{url('/admin/market/assess/result/add?assess_id='.$assess_id)!}" </#if> >添加结果</a>
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                        <tr>
                            <td>编号</td>
                            <td>结果标题</td>
                            <td>奖励</td>
                            <td>领奖限制</td>
                            <td>判断条件</td>
                            <td width="10%">操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#list ($data_list as $item)
                        <tr>
                            <td>${item->id!}</td>
                            <td>${item->result!}</td>
                            <td>${item->reward_type_name!}</td>
                            <td>${item->reward_limit_name!}</td>
                            <td>${item->judge_type!}</td>
                            <td class="operate" result_id="${item->id!}">
                                <a href="/admin/market/assess/result/add?result_id=${item->id!}&assess_id=${assess_id!}" class="show_tip" target="_blank" data-tips="编辑" style="display: inline; position: static;"><img src="/image/admin/decorate_editor.png" alt=""></a>
                                <#if ($pub_flag != 1)
                                <a href="javascript:;" class="show_tip del" data-tips="删除" style="display: inline; position: static;"><img src="/image/admin/decorate_delete.png" alt=""></a>   
                                </#if>    
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
            <a class="back" href="{{url('/admin/market/assess/topic/list?assess_id='.$assess_id)!}">上一步</a>
            <a class="save" style="margin-left:30px" href="{{url('/admin/market/assess/list?assess_id='.$assess_id)!}">去发布</a>
            <#else>
            <a class="back" href="{{url('/admin/market/assess/add?assess_id='.$assess_id)!}">上一步</a>
            <a class="save" style="margin-left:30px" href="{{url('/admin/market/assess/topic/list?assess_id='.$assess_id)!}">下一步</a>
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

    //删除
    $('.del').click(function () {
        var result_id = $(this).parent().attr('result_id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该测评结果吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {};
                param.act = 'del';
                param.result_id = result_id;
                util.ajax_json('/admin/market/assess/result/save', function (res) {
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