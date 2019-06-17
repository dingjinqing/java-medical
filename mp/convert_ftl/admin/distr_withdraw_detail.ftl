<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.4" type="text/css" />
<style>
    #test1>div{
        width: 100%;
        text-align: right;
    }
    .test_tab{
        width: 100%;;
    }
    .test_tab tr td{
        border: none !important;
    }
    .layui-laypage .layui-laypage-curr .layui-laypage-em{
        background: #5a8bff !important;
        color: #fff;
        border: 1px solid #5a8bff;
        text-decoration: none;
        padding: 0;
    }
    .layui-laypage a, .layui-laypage span{
        border: 1px solid #dedede;
        margin: 0 0px 5px 5px;
        background: rgb(250,250,250);
    }
    .layui-laypage a:hover{
        background: #fff !important;
        color: #5a8bff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }
    .layui-laypage button{
        background: rgb(250,250,250);
    }
    .layui-laypage button:hover{
        background: #fff !important;
        color: #5a8bff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }
    .layui-laypage button:focus{
        background: #5a8bff !important;
        color: #fff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span>返利提现审核 / </span><span style="color: #666;">提现申请详情</span>
</div>
{{--<form action="/admin/market/distribution/withdraw/detail" method="post" id="form1" style="padding-bottom: 100px">--!}
    {{--{{ csrf_field()!}--!}
<input type="hidden" name="order_sn" value="${data->order_sn!}">
    <div class="reserve-container">
        <div class="this_detail">
            <div class="each_title">提现申请详情</div>
            <div class="three_area">
                <div class="each_one">
                    <div class="normal_font">申请提现金额</div>
                    <div class="bold_font">￥${data->withdraw_cash!}</div>
                </div>
                <div class="each_one">
                    <div class="normal_font">出账类型</div>
                    <div class="bold_font">${type[$data->type]!}</div>
                </div>
                <div class="each_one">
                    <div class="normal_font">处理状态</div>
                    <div class="bold_font">${status[$data->status]!}</div>
                </div>
            </div>
        </div>
        <div class="some_btn">
            <#if ($data->status == 1)
            <a href="##" class="btn_success check">提现信息审核完成</a>
            </#if>
            <#if ($data->status == 3 || $data->status == 5)
            <a href="##" class="btn_success billing">确认出账</a>
            </#if>
            <#if ($data->status != 2 && $data->status != 4)
            <a href="##" class="btn_refuess">驳回提现申请</a>
            </#if>
            <a href="##" class="btn_msg">添加备注</a>
        </div>
        {{--基本信息--!}
        <div class="basic_msg withdraw_detail ">
            <div class="each_title">提现申请基本信息</div>
            <table width="80%">
                <tr>
                    <td width="30%">提现单号：${data->order_sn!}</td>
                    <td width="30%">申请时间：${data->add_time!}</td>
                </tr>
                <tr>
                    <td width="30%">出账类型：${type[$data->type]!}</td>
                    <td width="30%">申请金额：￥${data->withdraw_cash!}</td>
                </tr>
                <tr>
                    <td width="30%">用户ID：${data->user_id!}</td>
                    <td width="30%">注册时间：${data->create_time!}</td>
                </tr>
                <tr>
                    <td width="30%">用户昵称：<a href="/admin/user/manage/center?top_index=5&user_id=${data->user_id!}&sub_index=0" target="_blank">${data->username!}</a></td>
                    <td width="30%">真实姓名：${data->real_name!}</td>
                </tr>
                <tr>
                    <td width="30%">手机号：${data->mobile!}</td>
                    <td width="30%">处理状态：${status[$data->status]!}</td>
                </tr>
                <tr>
                    <td width="60%" colspan="2">备注信息：${data->desc ?? '（不通过核实，需填写备注信息）'!}</td>
                </tr>
                <tr>
                    <td width="60%" colspan="2">驳回申请原因：${data->refuse_desc ?? '（驳回申请，需填写备注信息）'!}</td>
                </tr>
            </table>
        </div>
        {{--转账明细信息--!}
        <div class="withdraw_detail">
            <div class="each_title">转账明细信息</div>
            <table width="80%">
                <thead>
                    <tr>
                        <td width="15%">提现单号</td>
                        <td width="8%">流水号</td>
                        <td width="8%">用户提现序号</td>
                        <td width="10%">出账类型</td>
                        <td width="10%">可提现金额</td>
                        <td width="10%">申请提现金额</td>
                        <td width="10%">操作时间</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td width="15%">${data->order_sn!}</td>
                        <td width="8%">${data->withdraw_num!}</td>
                        <td width="8%">${data->withdraw_user_num!}</td>
                        <td width="10%">${type[$data->type]!}</td>
                        <td width="10%">￥${data->withdraw!}</td>
                        <td width="10%">￥${data->withdraw_cash!}</td>
                        <td width="10%">${data->update_time!}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        {{--当前用户提现记录--!}
        <div class="withdraw_detail">
            <div class="each_title">当前用户提现记录</div>
            <table width="100%">
                <thead>
                <tr id="list-bottom-top">
                    <td width="12%">提现单号</td>
                    <td width="8%">流水号</td>
                    <td width="10%">提现序号</td>
                    <td width="10%">出账类型</td>
                    <td width="15%">申请提现金额</td>
                    <td width="10%">申请时间</td>
                    <td width="10%">处理状态</td>
                    <td width="15%">处理备注</td>
                    <td width="10%">操作</td>
                </tr>
                </thead>
                <tbody id="user_withdraw">
                {{--<#list ($data_list as $item)--!}
                {{--<tr>--!}
                    {{--<td width="12%">${item->order_sn!}</td>--!}
                    {{--<td width="8%">${item->withdraw_num!}</td>--!}
                    {{--<td width="10%">${item->withdraw_user_num!}</td>--!}
                    {{--<td width="10%">${type[$item->type]!}</td>--!}
                    {{--<td width="15%">￥${item->withdraw_cash!}</td>--!}
                    {{--<td width="10%">${item->add_time!}</td>--!}
                    {{--<td width="10%">${status[$item->status]!}</td>--!}
                    {{--<td width="15%">${item->desc!}</td>--!}
                    {{--<td width="10%">--!}
                        {{--<a href="/admin/market/distribution/widthdraw/detail?top_index=4&id=${item->id!}&order_sn=${item->order_sn!}&user_id=${item->user_id!}" target="_blank">查看详情</a>--!}
                    {{--</td>--!}
                {{--</tr>--!}
                {{--</#list>--!}
                </tbody>
                <table class="test_tab">
                    <tr>
                        <td><div id="test1"></div></td>
                    </tr>
                </table>
            </table>
        </div>
    </div>
{{--</form>--!}

<div class="modal_btn" style="display: none;" id="refuess">
    <div class="mo_title">请填写驳回提现申请的原因</div>
    <textarea name="refuse_desc" id="refuse_desc" cols="50" rows="5">${data->refuse_desc!}</textarea>
</div>

<div class="modal_btn" style="display: none;" id="beizhu">
    <div class="mo_title">请填写备注信息</div>
    <textarea name="desc" id="desc" cols="50" rows="5">${data->desc!}</textarea>
</div>
<#include "/admin/footer.ftl">

<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript">
    var laypage;
    layui.use('laypage', function(){
        laypage = layui.laypage;
    });
    {{--核实的提示--!}
    $('.check').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: left;text-indent: 2em;">' + '点击核实提现信息完成操作前请确认已经对提取金额、提取申请账号确实进行了核对并核实无误，确定要核实完成吗？' + '</div>', {
                title: ['确认核实提现信息', 'text-align:center;padding: 0px;']
                , area: '560px'
                , closeBtn: 0
                , btn: ['确定', '取消']
                ,yes: function(index, layero){ //确定按钮的回调
                    util.ajax_json('/admin/market/distribution/widthdraw/ajax', function (response) {
                        if (response.error == 0) {
                            util.mobile_alert("操作成功");
                            location.reload();
                        }else{
                            util.mobile_alert("操作失败");
                        }
                    },{action:'check',order_sn:$("input[name='order_sn']").val()});
                    layer.close(index);   //开启则可以关闭弹框
                },btn2: function(index, layero){
                    //按钮取消的回调


                }, end: function () {
                    $('#refuess').hide();
                }
            })
        });
    });
    $('.billing').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;text-indent: 2em;">' + '确认要出账吗？' + '</div>', {
                title: ['提示', 'text-align:center;padding: 0px;']
                , area: '300px'
                , closeBtn: 0
                , btn: ['确定', '取消']
                ,yes: function(index, layero){ //确定按钮的回调
                    var param = {
                        action:'billing',
                        order_sn:$("input[name='order_sn']").val()
                    };
                    getAuthorityDetail(1,"","distribution_withdraw","","distribution_withdraw",param)
                    // util.ajax_json('/admin/market/distribution/widthdraw/ajax', function (response) {
                    //     if (response.error == 0) {
                    //         util.mobile_alert("操作成功");
                    //         location.reload();
                    //     }else{
                    //         util.mobile_alert(response.message);
                    //     }
                    // },{action:'billing',order_sn:$("input[name='order_sn']").val()});
                    layer.close(index);   //开启则可以关闭弹框
                },btn2: function(index, layero){
                    //按钮取消的回调


                }, end: function () {
                    $('#refuess').hide();
                }
            },function(index){
                layer.close(index);
            });
        });
    });
    //驳回原因
    $('.btn_refuess').click(function(){
        layer.open({
            type: 1
            ,title: ['驳回申请原因','text-align:center;padding: 0px;']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: '450px'
            ,content: $('#refuess') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['确认','取消']
            ,btnAlign: 'r' //按钮居右
            ,yes: function(index, layero){ //确定按钮的回调
                util.ajax_json('/admin/market/distribution/widthdraw/ajax', function (response) {
                    if (response.error == 0) {
                        util.mobile_alert("操作成功");
                        location.reload();
                    }else{
                        util.mobile_alert("操作失败");
                    }
                },{action:'refuse',order_sn:$("input[name='order_sn']").val(),data:$("#refuse_desc").val()});
                layer.close(index);   //开启则可以关闭弹框
            },btn2: function(index, layero){
                //按钮取消的回调


            }, end: function () {
                $('#refuess').hide();
            }
        });
    });
    //添加备注
    $('.btn_msg').click(function(){
        layer.open({
            type: 1
            ,title: ['添加备注','text-align:center;padding: 0px;']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: '450px'
            ,content: $('#beizhu') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['确认','取消']
            ,btnAlign: 'r' //按钮居右
            ,yes: function(index, layero){ //确定按钮的回调
                util.ajax_json('/admin/market/distribution/widthdraw/ajax', function (response) {
                    if (response.error == 0) {
                        util.mobile_alert("操作成功");
                        location.reload();
                    }else{
                        util.mobile_alert("操作失败");
                    }
                },{action:'desc',order_sn:$("input[name='order_sn']").val(),data:$("#desc").val()});
                layer.close(index);   //开启则可以关闭弹框
            },btn2: function(index, layero){
                //按钮取消的回调

            }, end: function () {
                $('#refuess').hide();
            }
        });
    });



    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
    }
    getPowerInfo('main_config','distribution','sub_4','分销',0);
    var withdraw = {
        withdraw_page: function ( page, order_sn) {
            page = arguments[0] == undefined ? 1 : page;
            order_sn = arguments[1] == undefined ? 1 : order_sn;
            util.ajax_json('/admin/market/distribution/widthdraw/detail', function (response) {
                if (response.error == 0) {
                    util.loadPage(response.content.total, response.content.current_page, function (res) {
                        withdraw.withdraw_page(res.curr,order_sn);
                    })
                    var list = response.content.data, html = '';
                    var type = {2:'小程序账户出账',1:'公众号出账'};
                    for (var i in list) {
                        if(list[i].desc == null){
                            list[i].desc = "";
                        }
                        html += '<tr>'+
                            '<td width="12%">' +list[i].order_sn+'</td>' +
                            '<td width=8%>'+list[i].withdraw_num+'</td>' +
                            '<td width="10px">'+list[i].withdraw_user_num+'</td>' +
                            '<td width="10px">'+type[list[i].type]+'</td>' +
                            '<td width="15px">￥'+list[i].withdraw_cash+'</td>' +
                            '<td width="10px">'+list[i].add_time+'</td>' +
                            '<td width="10px">'+response.message[list[i].status]+'</td>' +
                            '<td width="15px">'+list[i].desc +'</td>' +
                            '<td width="10px"><a href="/admin/market/distribution/widthdraw/detail?top_index=4&id='+ list[i].id +'&order_sn='+ list[i].order_sn +'&user_id='+ list[i].user_id +'" target="_blank">查看详情</a>' +
                            '</tr>';
                    }
                    $('#user_withdraw').html(html);
                } else {
                    util.mobile_alert(response.message);
                }
            }, { page:page, order_sn: order_sn,})
        },
    }
    $(document).ready(function (){
        withdraw.withdraw_page(1,$("input[name='order_sn']").val());
    })
</script>
