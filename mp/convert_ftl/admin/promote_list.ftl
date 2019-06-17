<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/promote_manage.css?v=1.0.0" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">好友助力</span>
        </div>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs clearfix">
                <li <#if ($post_data['act_status'] == 0) class="active" </#if>><a href="/admin/market/promote/list?act_status=0">全部好友助力活动</a></li>
                <li <#if ($post_data['act_status'] == 1) class="active" </#if>><a href="/admin/market/promote/list?act_status=1">进行中</a></li>
                <li <#if ($post_data['act_status'] == 2) class="active" </#if>><a href="/admin/market/promote/list?act_status=2">未开始</a></li>
                <li <#if ($post_data['act_status'] == 3) class="active" </#if>><a href="/admin/market/promote/list?act_status=3">已过期</a></li>
                <li <#if ($post_data['act_status'] == 4) class="active" </#if>><a href="/admin/market/promote/list?act_status=4">已停用</a></li>
            </ul>
        </div>
        <div class="btn_adds clearfix">
            <a href="/admin/market/promote/info?top_index=4" target="_blank">添加好友助力活动</a>
        </div>
    </div>
    <div class="main-container">
       <div class="promote_list">
           <form action="/admin/market/promote/list" method="post" id="form1">
               {{csrf_field()!}
               <div class="search_some clearfix">
                  <input type="hidden" name="act_status" value="${post_data['act_status']!}">
                   <div class="search_list clearfix">
                       <div class="fl">
                           活动名称：<input type="text" placeholder="请输入活动名称" name="act_name" value="${post_data['act_name']!}">
                       </div>
                       <div class="fl" style="width: 530px;">
                           活动时间：
                           <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${post_data['start_time']!}"/>
                           &nbsp;至&nbsp;&nbsp;
                           <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${post_data['end_time']!}"/>
                       </div>
                       <div class="fl">
                           奖励类型：
                           <select name="reward_type">
                               <option value="-1">全部</option>
                               <#list ($reward_type as $type_key => $type_name)
                               <option value="${type_key!}" <#if ($post_data['reward_type'] == $type_key && isset($post_data['reward_type'])) selected </#if>>${type_name!}</option>
                               </#list>
                           </select>
                           <button class="btn_search">筛选</button>
                       </div>
                   </div>
               </div>
               <table width="100%">
                   <thead>
                        <tr class="promote_first">
                            <td width="15%">活动名称</td>
                            <td width="20%">活动有效期</td>
                            <td width="10%">奖励类型</td>
                            <td width="10%">奖励库存</td>
                            <td width="15%">已领取奖励数量</td>
                            <td width="10%">活动状态</td>
                            <td width="25%">操作</td>
                        </tr>
                   </thead>
                   <tbody>
                    <#list ($data_list as $item)
                       <tr act_id="${item->id!}">
                           <td width="15%">${item->act_name!}</td>
                           <td width="20%">${item->start_time!} 至 ${item->end_time!}</td>
                           <td width="10%">${item->reward_type_name!}</td>
                           <td width="10%">${item->market_store!}</td>
                           <td width="15%">${item->receive_num!}</td>
                           <td width="10%" class="act_status_name">${item->act_status_name!}</td>
                           <td width="25%" class="some_edit">
                               <a href="/admin/market/promote/info?act_id=${item->id!}" class="edit <#if ($item->act_status == 2 || $item->is_block == 1) hide </#if>">编辑 </a>
                               <a href="##" class="share_common  <#if ($item->act_status == 2 || $item->is_block == 1) hide <#else> hover_share </#if>" identity_id="0" type="29" extend_info="${item->extend_info!}"> 分享 </a>
                               <a href="##" class="del_btn <#if (!($item->act_status == 2 || $item->is_block == 1)) hide </#if>"> 删除 </a>
                               <#if ($item->act_status >=1 || $item->is_block == 1) 
                                 <a href="/admin/market/promote/receive?top_index=4&act_id=${item->id!}"> 领取明细 </a>
                                 <a href="/admin/market/promote/initiate?top_index=4&act_id=${item->id!}"> 发起明细 </a>
                                 <a href="/admin/market/promote/participate?top_index=4&act_id=${item->id!}"> 参与明细 </a>
                               </#if>
                               <#if ($item->act_status <= 1)
                                <#if ($item->is_block == 1)
                                  <a href="##" class="block_btn" is_block="0"> 启用</a>
                                <#else>
                                  <a href="##" class="block_btn" is_block="1"> 停用</a>
                                </#if>
                               </#if>
                           </td>
                       </tr>
                      </#list>
                   </tbody>
               </table>
               {{--分页和无数据的样式--!}
               <div class="paging_footer" style="margin-top: 12px;">
                   <#include "/admin/jump_page_admin.ftl">
               </div>
           </form>


       </div>
    </div>
</div>
<#include ('admin.share_common')
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script src="/js/admin/promote_list.js?v=1.0.6" type="text/javascript"></script>
<script type="text/javascript">
    getPowerInfo('main_config','promote','sub_4','好友助力',0);
</script>
