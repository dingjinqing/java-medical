<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.6" type="text/css" />
<style>
    .add_channel{
        height: 30px;
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
        margin-left: 15px;
        display:inline-block;
        padding:0 10px;
        border-raidus:2px;
    }
    .add_channel:link{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
    }
    .add_channel:visited{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
    }
    .add_channel:hover{
        background-color: #447af9;
        border-color: #447af9;
        text-decoration: none;
        color: #fff;
    }
    .add_channel:active{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
    }
    input[type="text"]{
        width: 175px;
        padding-left: 12px;
        border: 1px solid #ccc;
    }
    .info_table a{
        color: #5a8bff;
    }
    .erweima{
        position: relative;
        display: block;
    }
    .btn_seach {
        display: inline-block;
        vertical-align: top;
        margin-right: 85px;
        line-height: 26px;
        padding-left: 20px;
    }

    .btn_seach:visited {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
    }
    .btn_seach:link {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
    }
    .jump_page table tbody td{
        border: none;
    }
    .jump_page table tbody td input[type="text"]{
        width:80px;
    }
    .no_data_style{
        margin-top: 15px;
    }

</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>渠道页面分析</span>
</div>
<div class="reserve-container">
    <form action="" method="post" id="form1">
        {{ csrf_field()!}
        <div class="search_reason">
            <ul>
                <li class="clearfix">
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">渠道页面名称：</span>
                        <input type="text" placeholder="请输入页面名称" name="channel_name" value="${request['channel_name']!}">
                    </div>
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">来源页面：</span>
                        <input type="text" placeholder="请输入来源页面" name="source" value="${request['source']!}">
                    </div>
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">来源页面类型：</span>
                        <select name="source_type" id="">
                            <option value="" selected>请选择类型</option>
                            <option value="1" <#if ($request['source_type'] == 1) selected </#if>>自定义页面</option>
                            <option value="2" <#if ($request['source_type'] == 2) selected </#if>>商品详情</option>
                        </select>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="fl" style="width: auto;margin-bottom:10px;">
                        <span style="margin-right: 25px;margin-left: 28px;">注册时间：</span>
                        <input style="width: 175px; height: 30px;" type="text" name="start_time" value="${request['start_time']!}" placeholder="请选择时间" id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input style="width: 175px; height: 30px;" type="text" name="end_time" value="${request['end_time']!}" placeholder="请选择时间" id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off">
                    </div>
                    <div class="fr">

                        <a class="btn_seach" style="margin-left:30px;">筛选</a>
                        <a href="javascript:;" class="add_channel" style="vertical-align: top;margin-right:107px;line-height:26px;">添加渠道页面</a>
                    </div>
                </li>
            </ul>
        </div>
        <div class="info_table">
            <table width="100%">
                <thead>
                    <tr>
                        <th width="15%">渠道页面名称</th>
                        <th width="10%">源页面</th>
                        <th width="20%">源路径</th>
                        <th width="10%">源页面类型</th>
                        <th width="10%">昨日访问次数</th>
                        <th width="10%">昨日访问人数</th>
                        <th width="10%">添加时间</th>
                        <th width="5%">状态</th>
                        <th width="20%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <#list ($data_list as $k=>$v)
                    <tr>
                        <td>
                            ${v->channel_name!}
                        </td>
                        <td>
                            ${v->source!}
                            <br>
                            <a href="/admin/market/channel/statistical?id=${v->id!}">数据查询</a>
                        </td>
                        <td>
                            <#if ($v->source_type== 0)
                                pages/index/index?page=${v->page_id!}
                                <#elseif>($v->source_type == 1)
                                pages/item/item?good_id=${v->goods_id!}
                            </#if>
                        </td>
                        <td>
                            ${source_type[$v->source_type]!}
                        </td>
                        <td>${v->data['channel_pv']!} </td>
                        <td>${v->data['channel_uv']!} </td>
                        <td>
                            ${v->add_time!}
                        </td>
                        <td>
                            <#if ($v->del_flag == 1)
                               已废除
                            <#else>
                                使用中
                            </#if>
                        </td>
                        <td>
                            <div class="erweima">
                                <a href="##" class="hover_share"  <#if ($v->source_type == 1) type = '2' identity_id = '${v->goods_id!}' <#else> type = "7"  identity_id = '${v->page_id!}'</#if>  share="${v->share!}" extend_info = '{"share":"${v->share!}"}'>分享</a>
                            </div>
                            <#if ($v->del_flag == 1)
                                <a href="javascript:;" class="channel_del" type = '0' id="${v->id!}">启用渠道页</a>
                            <#else>
                            <a href="javascript:;" class="channel_del" type = '1' id="${v->id!}">废除渠道页</a>
                                </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
            <div class="clearfix jump_page">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </form>
</div>
<#include ('admin.share_common')
<#include "/admin/footer.ftl">

<script>
$('.add_channel').click(function(){
    var _this = $(this);
    layui.use('layer', function(){
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type:2,
            title: ['添加渠道页面', 'text-align:center;padding: 0px;'],
            offset: 'auto',
            area: ['900px','450px'],
            content: '/admin/market/channel/add',
            btn: ['确定', '取消'],
            btnAlign: 'r' ,
            shade: [0.3, '#000'],
            yes:function(index, layero){
                var body = layer.getChildFrame('body', index);
                if(body.find('.channel_name').find("input[name='channel_name']").val() == ''){
                    util.mobile_alert("请填写渠道页面名称");
                    return false;
                }
                if(body.find('input[name="channel_switch"]:checked').val() == 1 && !body.find('input[name="page_check"]:checked').attr("page_id")){
                    util.mobile_alert("请选择页面");
                    return false;
                }
                if(body.find('input[name="channel_switch"]:checked').val() == 2 && !body.find("#set-goods").find('.goods_tr_choose').attr("goods_id")){
                    util.mobile_alert("请选择商品");
                    return false;
                }
                util.ajax_json('/admin/market/channel/judge',function (res) {
                    if(res.error == 0){
                        var data = {};
                        data.act='add';
                        data.source_type = body.find('input[name="channel_switch"]:checked').val();
                        if(data.source_type == 1){
                            data.source_type = 0;
                            data.page_id = body.find('input[name="page_check"]:checked').attr("page_id");
                            data.goods_id = 0;
                        }else if(data.source_type == 2){
                            data.source_type = 1;
                            data.goods_id = body.find("#set-goods").find('.goods_tr_choose').attr("goods_id");
                            data.page_id = 0;
                        }
                        data.channel_name = body.find('.channel_name').find("input[name='channel_name']").val();
                        util.ajax_json('/admin/market/channel/add',function (res) {
                            if(res.error == 0){
                                layer.msg('添加成功', {time: 1500},function () {
                                    window.location.reload();
                                });
                            }else{
                                util.mobile_alert(res.message);
                                layer.close(index);
                            }
                        },data);
                    }else{
                        layer.msg('该渠道页名称已存在', {time: 1500},function () {
                        });
                        return false
                    }
                },{channel_name:body.find('.channel_name').find("input[name='channel_name']").val()});
            }
        })
    })
})

function picker(){
    hasSaved = false;
    return WdatePicker(
        {
            dateFmt: 'yyyy-MM-dd',
            autoUpdateOnChanged: false
        }
    );
}
$('.btn_copy').click(function(e){
    e.preventDefault();
    let prev = $(this).prev();
    prev[0].select();
    document.execCommand("Copy");
})
$('.info_table').on('click','.channel_del',function(){
    var id = $(this).attr('id');
    var del = $(this).attr("type"); // 1 废除 0恢复
    if(del == 1){
        var text = "确认要废除该渠道页吗";
    }else{
        var text = "确认要恢复该渠道页吗";
    }
    layui.use('layer', function () {
    var layer = layui.layer;
    layer.alert('<div style="text-align: center;">'+ text +'</div>', {
        title: ['提醒', 'text-align:center;padding: 0px;']
        , area: '260px'
        , closeBtn: 0
        , btn: ['确定', '取消']
    },function(index){
        var data={
            id:id,
            del:del,
            act:'del'
        };
            util.ajax_json('/admin/market/channel/list',function (res) {
                if(res.error == 0){
                    layer.msg('修改成功', {time: 1500},function () {
                        window.location.reload();
                    });
                }else{
                    util.mobile_alert(res.message);
                    layer.close(index);
                }
            },data);
        });
    });
})

    $(".btn_seach").click(function () {
        $("#form1").submit();
    })

</script>