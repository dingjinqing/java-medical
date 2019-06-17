<#include "/admin/header.ftl">
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/technician_list.css?v=1.0.2" type="text/css" />
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <style>
        a{
            text-decoration: none;
        }
        .change-input{
            width: auto;
        }
        #set-goods{
            background: #fff;
            padding: 10px 25px 15px;
        }
        .coupon_type{
            background: #fff;
            padding:10px 0 0;
        }
        .coupon_type ul{
            list-style:none;
            background: #f5f5f5;
            width: 97%;
            margin:0 auto;
            border:1px solid #f3f3f3;
        }
        .coupon_type ul:after{
            content: '';
            display: block;
            clear: both;
        }
        .coupon_type ul li{
            float: left;
            width: 100px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            cursor: pointer;
        }
        .coupon_type ul li a{
            display: block;
            width: 100%;
            height:100%;
            color: black;
        }
        .coupon_type ul .actives{
            background: #fff;
        }
    </style>
<div class="title">
    <span><a href="/admin/store/manage/list?top_index=6" style="color:  black;margin-left: 0px">门店列表（${store->store_name!}）</a> /</span>
    <span><a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6"  style="color:  #666;margin-left: 0px">${technician_title!}管理</a></span>
    {{--<span> / ${store->store_name!}</span>--!}
</div>
<div class="main-container">
    <div class="coupon_type">
        <ul style="font-size: 14px">
            <li class="normal_type " >
                <a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6 ">预约管理</a>
            </li>
            <li class="fenlie_type">
                <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" class="edition_type">服务管理</a>
            </li>
            <li class="fenlie_type actives">
                <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" class="edition_tech" title="技师管理">${technician_title!}管理</a>
            </li>
            <li class="give_to_sb">
                <a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6">评价管理</a>
            </li>
        </ul>
    </div>
    <div class="list_tech">
        <div class="">
            <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6"  class="techtab">${technician_title!}列表</a>
            <a href="/admin/store/services/technician/group?store_id=${store_id!}&top_index=6"  class="techtab techactive">${technician_title!}分组</a>
            <a href="/admin/store/services/technician/add?store_id=${store_id!}&top_index=6"  class="techtab">添加${technician_title!}</a>
        </div>
    </div>
    <div id="set-goods" class="clearfix">
        <div class="goods_search add_tech_groups clearfix">
            <input type="text" name="store_id" hidden value="${store_id!}">
            <input type="text" name="group_name" placeholder="请输入分类名称">
            <a href="##">保存</a>
        </div>
        <div class="goods_tb tech_group_tab">
            <table width="100%">
                <thead>
                    <tr>
                        <td>分组名称</td>
                        <td>创建时间</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody>
                    {{--不写在循环里，新增clone用的--!}
                    <tr class="clone_one" style="display:none">
                        <td><input type="text" value="" class="group_names" readonly="true"></td>
                        <td></td>
                        <td>
                            <a href="##" class="btn_edits">编辑</a>
                            <a href="##" class="btn_deletes">删除</a>
                        </td>
                    </tr>
                    {{--这里开始循环--!}
                    <#list ($data_list as $item)
                    <tr>
                        {{--<input type="text" name="store_id" hidden value="${item->group_id!}">--!}
                        <td><input type="text" name="group_name" class="change-input" group_id="${item->group_id!}" value="${item->group_name!}" readonly="true"></td>
                        <td>${item->add_time!}</td>
                        <td>
                            <a href="##" class="btn_edits">编辑</a>
                            <a href="##" class="btn_deletes" group_id="${item->group_id!}">删除</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <form action="/admin/store/services/technician/group?store_id=${store_id!}&top_index=6" method="post" id="form1">
            {{ csrf_field()!}
   <#include "/admin/jump_page_admin.ftl">
        </form>
    </div>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script language="JavaScript" src="/js/admin/util.js?v=1.0.3"></script>
<script>

    $(".btn_edits").click(function () {
        $(this).parent().parent().find("input[name='group_name']").removeAttr('readonly');
        $(this).parent().parent().find("input[name='group_name']").css("border","1px solid #eee");
        $(this).parent().parent().find("input[name='group_name']").focus();
    })

    $(".change-input").blur(function () {
        $(this).attr("readonly",'readonly');
        $(this).css("border","none");
    });

    $(".add_tech_groups a").click(function () {
        if($(".add_tech_groups input").val() ==""){
            util.mobile_alert('请填写新增分组名称！');
            return false;
        }
        if($(".add_tech_groups input").val() !=""){
            var store_id = $('input[name="store_id"]').val();;
            var group_name = $('input[name="group_name"]').val();
            util.ajax_json("/admin/store/services/technician/group/add", function (d) {
                if (d && d.error == 0) {
                    window.location.reload();
                } else if (d && d.error > 0) {
                    layer.msg(d.message);
                }
            }, {store_id : store_id, group_name : group_name});
        }

    });

    $(".change-input").change(function(){
        var data={};
        data.group_id = $(this).attr("group_id");
        data[$(this).attr('name')] = $(this).val();
        console.log(data)
        util.ajax_json('/admin/store/services/technician/group/update',function(d){
            console.log(d)
            if(d&&d.error==0){
                util.mobile_alert(d.content);
            } else{
                util.mobile_alert(d.error);
            }
        },data);
    });

    $(".btn_deletes").click(function () {
        var _this = $(this);
        var group_id = $(this).attr("group_id");
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {

                // var group_id = $(this).attr("group_id");
                util.ajax_json('/admin/store/services/technician/group/del',function(d){
                    if(d&&d.error==0){
                        util.mobile_alert(d.content);
                        window.location.reload();
                    } else {
                        util.mobile_alert(d.error);
                    }
                },{group_id : group_id});
                layer.close(index);

            });
        });

    })

</script>
<#include "/admin/footer.ftl">
