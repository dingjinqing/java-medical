<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.6" type="text/css" />
<style>
    .search_reason ul .re_li span{
        margin-right: 0;
        width: 70px;
    }
    .re_li .btn_seach{
        margin-left: 20px;
        height: 31px;
    }
    .add_group{
        display: block;
        width: 130px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        background: #5a8bff;
        color: #fff;
        border-radius: 2px;
        color: #fff;
    }
    .add_group:hover,.add_group:link{
        color: #fff;
    }
    tbody td a{
        color: #5a8bff;
    }
    tbody td a:hover,tbody td a:link{
        color: #5a8bff;
    }
    tbody td a+a{
        margin-left: 10px;
    }
    input[type="text"]{
        border:1px solid #ccc;
        border-radius:2px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span> 分销员分组</span>
</div>
<div class="reserve-container">
    <form action="" method="post" id="form1">
        {{ csrf_field()!}
        <div class="pages_nav clearfix">
            <#if ($request['all_users'] != 1)
                <#include ("admin.distributio_title")
            </#if>
        </div>
        <div class="search_reason">
            <ul>
                <li class="clearfix">
                    <div class="re_li">
                        <span style="text-align:left">分组名称：</span>
                        <input type="text" placeholder="请输入分组名称" name="groupname" value="">
                        <button type="button" class="btn_seach">筛选</button>
                    </div>
                </li>
            </ul>
        </div>
        <div class="info_table">
            <a href="javascript:;" class="add_group">添加分销员分组</a>
            <table width="100%" style="margin-top: 10px;">
                <thead>
                    <tr>
                        <th width="15%">分组名称</th>
                        <th width="20%">包含分销员数量</th>
                        <th width="20%">创建时间</th>
                        <th width="15%">是否为默认分组</th>
                        <th width="20%">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>这是一号分组</td>
                        <td><a href="/admin/market/distribution/distributer/list?top_index=4">20</a></td>
                        <td>2018-11-05 12:14:55</td>
                        <td>是 <a href="javascript:;" class="default">取消默认</a></td>
                        <td>
                            <a href="javascript:;" class="edit" group_id="">编辑</a>
                            <a href="javascript:;" class="del" group_id="">删除</a>
                            <a href="javascript:;" class="add_distributer" group_id="" user_ids="">添加分销员</a>
                        </td>
                    </tr>
                    <tr>
                        <td>这是二号分组</td>
                        <td><a href="/admin/market/distribution/distributer/list?top_index=4">20</a></td>
                        <td>2018-11-05 12:14:55</td>
                        <td>否 <a href="javascript:;" class="default">设为默认</a></td>
                        <td>
                            <a href="javascript:;" class="edit" group_id="">编辑</a>
                            <a href="javascript:;" class="del" group_id="">删除</a>
                            <a href="javascript:;" class="add_distributer" group_id="" user_ids="">添加分销员</a>
                        </td>
                    </tr>
                </tbody>
            </table>
            {{--<div class="clearfix">
                <#include "/admin/jump_page_admin.ftl">
            </div>--!}
        </div>
    </form>
</div>
<#include "/admin/footer.ftl">
<script>
    $('.add_group').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '分销员分组名称: <input type="text" name="newgroup" form="form1" margin-left:15px;>' + '</div>', {
                title: ['添加分销员分组', 'text-align:center;padding: 0px;']
                , area: '315px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    $('.edit').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '分销员分组名称: <input type="text" name="editgroup" form="form1" margin-left:15px;> <input type="hidden" name="editid" form="form1">' + '</div>', {
                title: ['修改分销员分组', 'text-align:center;padding: 0px;']
                , area: '315px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="editid"]').val(_this.attr("group_id")); 
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    $('.del').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？<input type="hidden" name="del" form="form1">' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="del"]').val(_this.attr("group_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    $('.add_distributer').click(function(){
        var _this = $(this);
        var group_id = _this.attr('group_id');
        var user_ids = _this.attr('user_ids');
        layui.use('layer', function(){
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type:2,
                title: ['添加分销员', 'text-align:center;padding: 0px;'],
                offset: 'auto',
                area: ['900px','450px'],
                content: '/admin/market/distribution/distributor/popup/list?record_select_value=' + user_ids + '&group_id=' + group_id,
                btn: ['确定', '取消'],
                btnAlign: 'r' ,
                shade: [0.3, '#000'],
                yes:function(index, layero){
                    var body = layer.getChildFrame('body', index);
                    var distributor_ids = body.find('#record_select_value').val();
                    // if (distributor_ids == '') {
                    //     util.mobile_alert('请选择分销员');
                    //     return false;
                    // }
                    util.ajax_json('',function (res) {
                        if(res.error == 0){
                            _this.attr('user_ids',distributor_ids);
                            layer.msg('添加分销员成功', {time: 2000},function () {
                                window.location.reload();
                            });
                        }else{
                            util.mobile_alert(res.message)
                        }
                    },{group_id:group_id,level_status:-1,group_user_ids:distributor_ids});
                },
                btn2:function(index,layero){

                }
            })
        })
    })
</script>