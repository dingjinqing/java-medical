<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.6" type="text/css" />
<style>
    .search_reason ul .re_li span{
        margin-right: 0;
        width: 70px;
        display: block;
    }
    .re_li .btn_seach{
        margin-left: 20px;
        height: 31px;
    }
    .add_group{
        width: 130px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        background: #5a8bff;
        color: #fff;
        border-radius: 2px;
        color: #fff;
        display:block;
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
    .new_group_name{
        height: 30px;
        width: 130px;
    }
    .jump_page table tbody td{
        border: none;
    }
    .no_data_style{
        margin-top: 15px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span> 分销员分组</span>
</div>
<div class="reserve-container">
    <form action="/admin/market/distribution/group" method="post" id="form1">
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
                        <span style="text-align:left;display:inline;">分组名称：</span>
                        <input type="text" placeholder="请输入分组名称" name="group_name" value="${options['group_name']!}">
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
                    <#if  ($data_list) 
                        <#list ($data_list as $group)
                            <tr>
                                <td>${group->group_name!}</td>
                                <td>
                                    <a href="/admin/market/distribution/distributer/list?top_index=4&group_id=${group->id!}">
                                        ${group->user_ids ? count(explode(',',$group->user_ids)) : 0!}
                                    </a>
                                </td>
                                <td>${group->add_time!}</td>
                                <td><#if  ($group->is_default) 是 <a href="javascript:void(0);" class="remove_default" group_id="${group->id!}">取消默认</a>
                                    <#else>否 <a href="javascript:void(0);" class="default" group_id="${group->id!}">设为默认</a>
                                    </#if> </td>
                                <td>
                                    <a href="javascript:void(0);" class="edit" group_id="${group->id!}">编辑</a>
                                    <a href="javascript:void(0);" class="del" group_id="${group->id!}">删除</a>
                                    <a href="javascript:void(0);" class="add_distributer" group_id="${group->id!}">添加分销员</a>
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
<script type="text/javascript" src="{{asset('js/admin/distributor_group.js')!}?1.0.1"></script>
