<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/store_manage.css?v=1.0.1">
<style type="text/css">
    .tb-decorate-list>tbody>tr>td{
        height:50px;
    }
    .btn_add_group a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_add_group a:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span>{{ trans("admin/store_manage.store_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/store_manage.group_list.title")!}</span>
    </div>
    <form action="" method="post" id="form1">
    <div class="main_container">
        <div class="list_content">
            <div class="btn_add_group" style="margin-left: 0">
                <a href="##">新建分组</a>
                <input type="text" value="${keywords!}" class="btn_add_search" name="group_name" onKeyDown="if (event.keyCode==13 || event.which==13) search(this.value);" placeholder='请输入分组查询'/>
                <button class="btn_searchinfo">查询</button>
            </div>
            <div class="list_table">
                    {{csrf_field()!}
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="25%">{{ trans("admin/store_manage.group_list.group_name")!}</th>
                                    <th width="25%">{{ trans("admin/store_manage.group_list.create_time")!}</th>
                                    <th width="25%">{{ trans("admin/store_manage.group_list.store_number")!}</th>
                                    <th width="25%">{{ trans("admin/store_manage.group_list.operation")!}</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list ($data_list as $item)
                                        <tr group_id="${item->group_id!}" group_name="${item->group_name!}">
                                            <td class="group_name">${item->group_name!}</td>
                                            <td>${item->in_time!}</td>
                                            <td>${item->store_number!}</td>
                                            <td>
                                                <a href="#" class="btn_edit">编辑</a>
                                                <a href="#" class="del_group">删除</a>
                                                <a href="/admin/store/manage/list?group=${item->group_id!}" target="_blank">查看门店</a>
                                            </td>
                                        </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="paging_footer">
                      <#include "/admin/jump_page_admin.ftl">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="set-group" style="display: none">
    <div>分组名称</div>
    <input type="text" value="" id="group"/>
</div>

<div id="del-group" style="display: none">
    删除此分组，已有此分组的用户将失去该分组，是否确认删除？
</div>
<script>
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    $('.abort').click(function(){
        $('input[name="del"]').val($(this).attr('act_id'));
        $("#form1").submit();
    });
    $('.btn_edit,.btn_add_group a').click(function(){
        var obj = $(this).parent().parent();
        var group_id = obj.attr('group_id');
        var group_name = obj.attr('group_name');
        $("#set-group").find('input').val(group_name);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['新建分组', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '330px'
                , content: $('#set-group') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var html = layero[0];
                    var data={};
                    data.group_name =  $(html).find('input')[0].value;
                    if(group_id>0) {
                        data.group_id = group_id;
                    }
                    if(data.group_name == "") {
                        util.alert("请输入分组名称");
                    } else {
                        util.ajax_json('/admin/store/group/add',function(d){
                            if(d&&d.error>=0){
                                util.mobile_alert(d.content);
                                layer.close(index);
                                if(data.group_id>0){
                                    obj.find(".group_name").text(data.group_name);
                                    obj.attr('group_name',data.group_name);
                                }else {
                                    window.location.reload();
                                }
                            }
                            else{
                                util.mobile_alert(d.message);
                            }
                        },data);
                    }
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    $('.del_group').click(function(){
        var obj = $(this).parent().parent();
        var group_id = obj.attr('group_id');
        var group_name = obj.attr('group_name');
        $("#set-group").find('input').val(group_name);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提醒', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '425px'
                , content: $('#del-group') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var data={};
                    if(group_id>0) {
                        data.group_id = group_id;
                    }
                    util.ajax_json('/admin/store/group/del',function(d){
                        if(d&&d.error>=0){
                            util.mobile_alert(d.content);
                            layer.close(index);
                            if(data.group_id>0){
                                obj.hide();
                            }else {
                                window.location.reload();
                            }
                        }
                        else{
                            util.mobile_alert(d.message);
                        }
                    },data);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
</script>

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script src="/js/admin/store_manage.js?v=1.0.2" type="text/javascript"></script>
<script type="text/javascript">
    window.search = function(keywords){
        if(keywords==''){
            util.mobile_alert('请输入fenzu 查询');
            return;
        }
        $("#page").val(1);
        $("#form1").submit();
    };
</script>