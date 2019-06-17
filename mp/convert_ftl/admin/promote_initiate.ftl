<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/promote_manage.css?v=1.0.1" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">好友助力 / </span>
            <span style="color: #666;">${act_name!}-发起明细</span>
        </div>
    </div>
    <div class="main-container">
        <div class="promote_list initiate_list" >
            <form action="/admin/market/promote/initiate" method="post">
                {{csrf_field()!}
                <input type="hidden" name="act" value="" class="act">
                <input type="hidden" name="act_id" value="${request['act_id']!}">
                <div class="search_some clearfix">
                    <div class="search_list clearfix">
                        <div class="fl">
                            用户昵称：<input type="text" placeholder="请输入用户昵称" name="user_name" value="${request['user_name']!}">
                        </div>
                        <div class="fl">
                            手机号：<input type="text" placeholder="请输入用户手机号" name="user_mobile" value="${request['user_mobile']!}">
                        </div>
                        <div class="fl">
                            助力活动ID：<input type="text" placeholder="请输入助力活动ID" name="id" value="${id!}">
                        </div>
                    </div>
                    <div class="search_list">
                        <div class="fl">
                            活动发起人：<input type="text" placeholder="请输入活动发起人" name="act_initiate" value="${request['act_initiate']!}">
                        </div>
                        <div class="fl" style="width: 460px;">
                            是否成功助力：
                            <select name="is_success" id="">
                                <option value="0">全部</option>
                                <option value="1" <#if  ($is_success ==1) selected </#if>>是</option>
                                <option value="2" <#if  ($is_success ==2) selected </#if>>否</option>
                            </select>
                            <button class="btn_search">筛选</button>
                            <button class="btn_excel">导出数据</button>
                        </div>
                    </div>
                </div>
                <div class="detail_table">
                    <table width="100%">
                        <thead>
                        <tr class="promote_first">
                            <td width="15%">发起用户昵称</td>
                            <td width="20%">发起用户手机号</td>
                            <td width="10%">助力活动ID</td>
                            <td width="10%">参与人数</td>
                            <td width="15%">助力次数</td>
                            <td width="10%">助力值</td>
                            <td width="25%">是否助力成功</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                        <tr>
                            <td width="15%"><a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->username!}</a></td>
                            <td width="20%">${item->mobile!}</td>
                            <td width="10%">${item->id!}</td>
                            <td width="10%"><a href="/admin/market/promote/participate?top_index=4&act_id=${item->promote_id!}&id=${item->id!}">${item->dis_particate_man!}</a></td>
                            <td width="15%">${item->particate_man!}</td>
                            <td width="10%">${item->dis_particate_sum!}</td>
                            <td width="25%"><#if ($item->promote_status==1||$item->promote_status==2)是<#else>否</#if></td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <#if ($data_list ->count())
                    <div class="paging_footer">
                        <#include "/admin/jump_page_admin.ftl">
                    </div>
                <#else>
                    <div class="paging_footer" style="padding: 0 23px;height: 130px;">
                        <#include "/admin/jump_page_admin.ftl">
                    </div>
                </#if>
            </form>

        </div>
    </div>
</div>
<script>
    $('.btn_search').click(function(){
        $('.act').val('');
        $('#form1').submit();
    })
    $('.btn_excel').click(function(){
        $('.act').val('export');
        $('#form1').submit();
       // $('.act').val('');
    })
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','promote','sub_4','好友助力',0);
</script>