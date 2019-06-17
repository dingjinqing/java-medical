<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0" type="text/css" />
<style>
    .tb-decorate-a{
        position: relative;
    }
    .tb-decorate-a .share_span{
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 48px;
        left:-90px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }
    .tb-decorate-a .a{
        width: 28px;
        height:30px;
        display: inline-block;
    }
    .tb-decorate-a .share_span .share_sj{
        position: absolute;
        right: 120px;
        top: -7px;
    }
    .tb-decorate-a .share_span span{
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }
    .tb-decorate-a .share_span .code_imgs{
        display: block;
        margin:0 auto;
    }
    .tb-decorate-a .share_span a{
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
    }
    .share_link{
        padding-top: 15px;
        width: 100%;
    }
    .share_link input{
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }
    .share_link button{
        float: right;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
    }
    .add-child-btn:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .add-child-btn:focus{
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
    .title div a.active {
        color: #5a8bff;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div style="float: left;">
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span><a href="/admin/market/pingroup/list?nav=1&top_index=4">多人拼团</a> /</span>
                <span><span class="ellipsis">${basicData->name!}</span>- </span>
            <span style="color: #666;">参团明细</span>
        </div>
        <div style="float: right; margin-right: 10px;">
            <a href="/admin/market/pingroup/detail?id=${request['pin_activity_id']!}&is_grouper=0" <#if  ($request['is_grouper'] == 0) class="active" </#if>>参团用户列表</a> |
            <a href="/admin/market/pingroup/detail?id=${request['pin_activity_id']!}&is_grouper=1" <#if  ($request['is_grouper'] == 1) class="active" </#if>>团列表</a>
        </div>
    </div>
    <div class="main-container">
        <form action="/admin/market/pingroup/detail?id=${request['pin_activity_id']!}&is_grouper=${request['is_grouper']!}" method="post" id="form1" name="form1">
            {{ csrf_field()!}
        <div class="box panel panel-body list-center-fee">
            <div class="form-inline shop-template-container">
                <ul class="clearfix">
                    <li class="pin-group-info-li clearfix">
                        <div class="fl">
                            <span>
                                <#if  ($request['is_grouper'] == 1)
                                    团长手机号
                                <#else>
                                    用户手机号
                                </#if>

                            </span>
                            <input type="text" name="mobile" placeholder="手机号" value="${request['mobile']!}"/>
                        </div>
                        <div class="fl">
                            <span>
                                <#if  ($request['is_grouper'] == 1)
                                    团长昵称
                                <#else>
                                    用户昵称
                                </#if>
                            </span>
                            <input type="text" name="username" placeholder="昵称" value="${request['username']!}"/>
                        </div>
                        <div class="fl">
                            <span>成团状态</span>
                            <select name="status" id="status">
                                <option value="-1">全部</option>
                                <option value="0" <#if  (isset($request['status']) && $request['status'] == 0) selected </#if>>成团中</option>
                                <option value="1" <#if  ($request['status'] == 1) selected </#if>>已成团</option>
                                <option value="2" <#if  ($request['status'] == 2) selected </#if>>未成团</option>
                            </select>
                        </div>
                        <div class="fl">
                            <button type="submit" class="pin-group-search">搜索</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="return-pin-group-box">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <#if  ($request['is_grouper'] == 1)
                                <tr class="get-list-th">
                                    <th width="7%"> 团长名称 </th>
                                    <th width="12%"> 团长手机号 </th>
                                    <th width="10%"> 已参团用户数量 </th>
                                    <th width="11%"> 是否已成团 </th>
                                    <th width="13%"> 开团时间 </th>
                                    <th width="13%"> 成团时间 </th>
                                </tr>
                                <#else>
                                <tr class="get-list-th">
                                    <th width="7%"> 团长名称 </th>
                                    <th width="12%"> 团长手机号 </th>
                                    <th width="10%"> 参团用户名称 </th>
                                    <th width="10%"> 参团用户手机号 </th>
                                    <th width="11%"> 是否已成团 </th>
                                    <th width="13%"> 是否默认成团 </th>
                                    <th width="13%"> 订单号 </th>
                                    <th width="13%"> 参团时间 </th>
                                    <th width="13%"> 成团时间 </th>
                                </tr>
                            </#if>
                            </thead>
                            <tbody>
                            <#if  ($request['is_grouper'] == 1)
                                <#list ($list as $item)
                                    <tr>
                                        <td>${item->username!}</td>
                                        <td>${item->mobile!}</td>
                                        <td>${userSum[$item->group_id]!}</td>
                                        <td>
                                            <#if  ($item->status == 1)
                                                已成团
                                                <#elseif> ($item->status == 2)
                                                未成团
                                                <#else>
                                                成团中
                                            </#if>
                                        </td>
                                        <td>${item->start_time!}</td>
                                        <td>
                                            <#if  ($item->status == 1)
                                                ${item->end_time!}
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                                <#else>
                                    <#list ($list as $item)
                                        <tr>
                                            <#if  ($loop->index == 0 || $item->group_id != $list[$loop->index - 1]->group_id)
                                                <td rowspan="${userSum[$item->group_id]!}">${item->username!}</td>
                                                <td rowspan="${userSum[$item->group_id]!}">${item->mobile!}</td>
                                            </#if>
                                            <td>${item->username!}</td>
                                            <td>${item->mobile!}</td>
                                            <td>
                                                <#if  ($item->status == 1)
                                                    已成团
                                                <#elseif> ($item->status == 2)
                                                    未成团
                                                <#else>
                                                    成团中
                                                </#if>
                                            </td>
                                            <td>
                                                <#if  ($basicData->is_default)
                                                    是
                                                    <#else>
                                                    否
                                                </#if>
                                            </td>
                                            <td>${item->order_sn!}</td>
                                            <td>${item->start_time!}</td>
                                            <#if  ($loop->index == 0 || $item->group_id != $list[$loop->index - 1]->group_id)
                                            <td rowspan="${userSum[$item->group_id]!}">
                                                <#if  ($item->status == 1)
                                                ${item->end_time!}
                                                </#if>
                                            </td>
                                            </#if>
                                        </tr>
                                    </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            <div class="paging_footer">
              <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
        </form>
    </div>
</div>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','pin_group','sub_4','拼团',0);
</script>
