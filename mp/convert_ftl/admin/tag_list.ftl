<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_tag.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .btn_add span:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_add span:focus{
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
    .no_data_style{
        width: 98% !important;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>会员管理 / </span>
        <span style="color: #666;">标签管理</span>
    </div>
    <div class="main-container">
        <form action="" id="form1" method="post">
            {{ csrf_field()!}
            <div class="user_tag" style="min-height: 500px;">
                <div class="btn_add">

                    <span>新建标签</span>
                    <input type="text" value="${keywords!}" class="btn_add_search" name="tag_name" onKeyDown="if (event.keyCode==13 || event.which==13) search(this.value);" placeholder='请输入标签查询'/>
                    <button class="btn_searchinfo">查询</button>
                </div>
                <div>
                    <table class="tb_tag" width="98%">
                        <thead>
                        <tr>
                            <td width="24%">标签名</td>
                            <td width="24%">创建时间</td>
                            <td width="24%">用户数</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                        <tr tag_id="${item->tag_id!}" tag_name="${item->tag_name!}">
                            <td class="tag_name">${item->tag_name!}</td>
                            <td>${item->in_time!}</td>
                            <td>${item->user_number!}</td>
                            <td>
                                <a href="#" class="btn_edit">编辑</a>
                                <a href="#" class="del_tag">删除</a>
                                <a href="/admin/user/manage/list?tag=${item->tag_id!}&top_index=5" target="_blank">查看用户</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <div class="paging_footer">
              <#include "/admin/jump_page_admin.ftl">
                </div>
            </div>
        </form>
    </div>
</div>
<div id="set-tag">
    <div>标签名称</div>
    <input type="text" value="" class="ipt_tag" />
</div>

<div id="del-tag">
    删除此标签，已有此标签的用户将失去该标签，是否确认删除？
</div>

<script type="text/javascript">
    window.search = function(keywords){
        if(keywords==''){
            util.mobile_alert('请输入标签查询');
            return;
        }
        $("#page").val(1);
        $("#form1").submit();
    };

    $('.btn_edit,.btn_add span').click(function(){
        var obj = $(this).parent().parent();
        var tag_id = obj.attr('tag_id');
        var tag_name = obj.attr('tag_name');
        $("#set-tag").find('input').val(tag_name);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['新建标签', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '330px'
                , content: $('#set-tag') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var html = layero[0];
                    var data={};
                    if($('.ipt_tag').val().length > 10){
                        util.mobile_alert('名称最多只能填写10个字');
                        $('.ipt_tag').val( $('.ipt_tag').val().substring(0,10) );
                        return;
                    }
                    data.tag_name =  $(html).find('input')[0].value;
                    if(tag_id>0) {
                        data.tag_id = tag_id;
                    }
                    if(data.tag_name == "") {
                        util.mobile_alert("请输入标签名称！");
                        $(html).find('input').focus();
                    } else {
                        util.ajax_json('/admin/tag/add',function(d){
                            if(d&&d.error>=0){
                                util.mobile_alert(d.content);
                                layer.close(index);
                                if(data.tag_id>0){
                                    obj.find(".tag_name").text(data.tag_name);
                                    obj.attr('tag_name',data.tag_name);
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


                }, end: function () {
                    $('#set-tag').hide();
                }
            });
        });
    });

    $('.del_tag').click(function(){
        var obj = $(this).parent().parent();
        var tag_id = obj.attr('tag_id');
        var tag_name = obj.attr('tag_name');
        $("#set-tag").find('input').val(tag_name);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提醒', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '425px'
                , content: $('#del-tag') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var data={};
                    if(tag_id>0) {
                        data.tag_id = tag_id;
                    }
                    util.ajax_json('/admin/tag/del',function(d){
                        if(d&&d.error>=0){
                            util.mobile_alert(d.content);
                            layer.close(index);
                            if(data.tag_id>0){
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


                }, end: function () {
                    $('#del-tag').hide();
                }
            });
        });
    });
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','tag','sub_3','会员标签',0);
</script>