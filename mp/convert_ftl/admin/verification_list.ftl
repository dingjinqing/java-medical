<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.6" type="text/css" />
<style>
    .add_verification{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        height: 30px;
        line-height: 25px;
    }
    .add_verification:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #fff;
    }
    tbody td a:link,tbody td a:visited,tbody td a:hover,tbody td a:active{
        color: #5a8bff;
    }
    tbody td a+a{
        margin-left: 10px;
    }
    .jump_page table tbody td{
        border: none;
    }
    .no_data_style{
        margin-top: 15px;
    }
</style>
<div class="title">
    <span><a href="/admin/store/manage/list?top_index=6">门店管理</a> / </span><span>门店列表 / </span><span> 核销员列表</span>
</div>
<div class="reserve-container">
    <form action="" method="post" id="form1">
        {{ csrf_field()!}
        <div class="search_reason">
            <ul>
                <input type="text" name="store_id" value="${options['store_id']!}" hidden>
                <input type="text" name="getXls" hidden>
                <li class="clearfix">
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">手机号：</span>
                        <input type="text" placeholder="请输入手机号码" name="mobile" value="${options['mobile']!}">
                    </div>
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">微信昵称：</span>
                        <input type="text" placeholder="请输入微信昵称" name="username" value="${options['username']!}">
                    </div>
                    <button type="button" class="btn_seach">筛选</button>
                    <button type="button" class="btn_exel" style="vertical-align: top;margin-right:85px;line-height:26px;width:80px;">导出表格</button>
                    <a type="button" class="fr add_verification">添加核销员</a>
                </li>
            </ul>
        </div>
        <div class="info_table">
            <table width="100%">
                <thead>
                    <tr>
                        <th width="10%">用户ID</th>
                        <th width="25%">昵称</th>
                        <th width="20%">手机号</th>
                        <th width="20%">核销订单数量</th>
                        <th width="25%">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <#if  (count($data_list))
                        <#list ($data_list as $verifier)
                            <tr>
                                <td>${verifier->user_id!}</td>
                                <td><a href="/admin/user/manage/center?user_id=${verifier->user_id!}&top_index=5&sub_index=0">${verifier->username!}</a></td>
                                <td>${verifier->mobile!}</td>
                                <td>${verifier->verify_orders ?? 0!}</td>
                                <td>
                                    <a href="/admin/orders/manage/list?deliver_type=1&top_index=3&verifier_list=1&verifier_mobile=${verifier->mobile!}&store_id=${options['store_id']!}">查看订单</a>
                                    <a href="javascript:void(0);" verifier_id="${verifier->id!}" class="verification_del">删除</a>
                                </td>
                            </tr>
                        </#list>
                    </#if>
                </tbody>
            </table>
            <div class="clearfix jump_page">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </form>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $(function(){
        $('.add_verification').click(function(){
            var store_id = $('[name="store_id"]').val();
            layui.use('layer', function(){
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type:2,
                    title: ['添加核销员', 'text-align:center;padding: 0px;'],
                    offset: 'auto',
                    area: ['900px','450px'],
                    content: '/admin/frame/user/select?verify_store_id=' + store_id,
                    btn: ['确定', '取消'],
                    btnAlign: 'r' ,
                    shade: [0.3, '#000'],
                    yes:function(index, layero){
                        var body = layer.getChildFrame('body', index);
                        var verifier_ids = body.find('#check_choice').val();
                        util.ajax_json('/admin/store/verification/update',function (res) {
                            if(res.error == 0){
                                layer.msg('添加核销员成功', {time: 500},function () {
                                    window.location.reload();
                                });
                            }else{
                                // console.log(res)
                                util.mobile_alert(res.message)
                            }
                        },{action:'add_verifier', store_id:store_id, verifier_ids:verifier_ids});
                    }
                })
            })
        });
        $('.verification_del').click(function(){
            var verifier_id = $(this).attr('verifier_id');
            console.log('222')
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">确认要删除该核销员吗？</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                },function(index){
                    util.ajax_json('/admin/store/verification/update',function (res) {
                        if(res.error == 0){
                            layer.msg('删除核销员成功', {time: 500},function () {
                                window.location.reload();
                            });
                        }else{
                            util.mobile_alert(res.message);
                            layer.close(index);
                        }
                    },{action:'del_verifier', verifier_id:verifier_id});
                });
            });
        });
        $('.btn_seach').click(function () {
            $("#form1").submit();
        })
        $('.btn_exel').click(function () {
            $('[name="getXls"]').val('getXls');
            $("#form1").submit();
            $('[name="getXls"]').val('');
        })
    })
    
</script>