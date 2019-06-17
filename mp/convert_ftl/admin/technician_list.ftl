<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/technician_list.css?v=1.0.6" type="text/css" />
<style type="text/css">
    .layui-layer-iframe iframe{
        height:350px;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
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
    <span><a href="/admin/store/manage/list?top_index=6" style="color:  black">门店列表（${store->store_name!}）</a> /</span>
    <span><a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6"  style="color:  #666">${technician_title!}管理</a></span>
    {{--<span> / ${store->store_name!}</span>--!}
    {{--<span>${technician_title!}管理 / </span>--!}
    {{--<span style="color: #666;">${technician_title!}列表</span>--!}
</div>
<div class="main-container">
    <div class="coupon_type">
        <ul>
            <li class="normal_type">
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
            <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6"  class="techtab techactive">${technician_title!}列表</a>
            <a href="/admin/store/services/technician/group?store_id=${store_id!}&top_index=6"  class="techtab">${technician_title!}分组</a>
            <a href="/admin/store/services/technician/add?store_id=${store_id!}&top_index=6"  class="techtab" target="_blank">添加${technician_title!}</a>

        </div>
    </div>

    <form action="/admin/store/services/technician/list?top_index=6" method="post" id="form1">
        <div class="">
            <div class="reserve-info">
                {{ csrf_field()!}
                <div class="rl_title clearfix">
                    <input type="text" name="store_id" hidden value="${store_id!}">
                    {{--<span class="change_name">--!}
                        {{--<input type="text" class="tech_names" value="${technician_title!}" readonly>--!}
                        {{--<a href="##">改名</a>--!}
                    {{--</span>--!}
                    <span>
                        <label for="tech-names">${technician_title!}姓名：</label>
                        <input type="text" name="technician_name" id="tech-names" value="${post_data['technician_name']!}" placeholder="${technician_title!}姓名">
                    </span>
                    <span>
                        <label for="tech-group">${technician_title!}分组：</label>
                        <select name="group_id" class="tech_group">
                            <option value="">全部</option>
                            <#list ($group_list as $group)
                                <option value="${group->group_id!}" <#if ($group->group_id == $post_data['group_id'])selected </#if>>${group->group_name!} </option>
                            </#list>
                        </select>
                    </span>
                    <span>
                        <label for="tech-phone">联系电话：</label>
                        <input type="text" name="technician_mobile" id="tech-phone" value="${post_data['technician_mobile']!}" placeholder="${technician_title!}电话">
                    </span>
                    <button class="btn_searchinfo">查询</button>
                    {{--<a href="##"  class="manage-tech-group">${technician_title!}分组</a>--!}
                    {{--<a href="##"  class="create-tech">添加${technician_title!}</a>--!}
                </div>
            </div>
            <div class="reserve-list">
                <div class="reserve-list-table">
                    <table width="100%">
                        <thead>
                        <tr>
                            <td width="160px">${technician_title!}姓名</td>
                            <td width="160px">${technician_title!}分组</td>
                            <td width="160px">联系电话</td>
                            <td width="200px">服务项目</td>
                            <td width="160px">添加时间</td>
                            <td width="300px">介绍</td>
                            <td width="150px">操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#if ($data_list)
                        <#list ($data_list as $technician)
                        <tr class="reserve_tb_body">
                            <td width="160px">${technician->technician_name!}</td>
                            <td width="160px">${technician->group_name!}</td>
                            <td width="160px">${technician->technician_mobile!}</td>
                            <td width="200px"><div style="width: 200px;word-break: break-all"><#list ($technician->service_detail as $i)${i->service_name!}；</#list></div></td>
                            <td width="160px">${technician->add_time!}</td>
                            <td width="300px">${technician->technician_introduce!}</td>
                            <td width="250px">
                                <a href="/admin/schedule/list?technician_id=${technician->id!}&top_index=6" target="_blank">排班管理</a>
                                <a href="/admin/store/services/technician/add?technician_id=${technician->id!}&top_index=6" target="_blank">编辑</a>
                            </td>
                        </tr>
                        </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>
                <div class="clearfix">
                 <#include "/admin/jump_page_admin.ftl">
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    //分页
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#act").val("");
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    
    $(".btn_searchinfo").click(function () {
        // if($("input[name='technician_mobile']").val() != "") {
        //     var re = /^[1][3,4,5,7,8][0-9]{9}$/;
        //     if (!re.test($("input[name='technician_mobile']").val())) {
        //         layer.msg("请输入有效的手机号");
        //         return false;
        //     }
        // }
        $("#form1").submit();
    })
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/reserve_list.js?v=1.0.3" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','technician','sub_5','技师管理',0);
</script>
