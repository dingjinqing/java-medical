<#include "/admin/header.ftl">
<style>
    .list_container{
        background:#fff;
        margin: 15px;
        padding: 15px;
    }
    .search_box ul li{
        float: left;
        width: 280px;
    }
    .search_box input {
        width: 150px;
        height: 30px;
        padding-left: 12px;
        border: 1px solid #ccc;
    }

    .search_box select {
        width: 150px;
        height: 30px;
        padding-left: 12px;
        border-width: 1px;
        border-style: solid;
        border-color: rgb(204, 204, 204);
        border-image: initial;
    }
    .btn-choose {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        margin-left: 40px;
    }
    .btn-choose:hover {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #fff;
        cursor: pointer;
    }
    .info_table table {
        border: 1px solid #eaeaec;
        font-size: 13px;
    }
    .info_table table thead tr {
        background: #f5f5f5;
    }
    .info_table table thead th {
        color: #333;
        font-size: 13px;
        font-weight: normal;
        padding: 10px 0;
    }
    .info_table table tr td {
        padding: 10px 5px;
        border: 1px solid #eaeaec;
        text-align: center;
        color: #666;
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
    .jump_page table tr td{
        border:none;
    }
    .jump_page .no_data_style{
        margin-top:10px;
    }
</style>
<div class="title">
    <span>会员管理 / </span><span style="color: #666;"><#if ($card_info->card_type == 0)普通会员卡<#elseif>($card_info->card_type==1)限次会员卡</#if> / </span><span style="color:#666">${title!}</span>
</div>

<div class="list_container">
    <form action="/admin/user/member/code/receivelist?card_id=${request['card_id']!}" id="form1" method="POST">
        {{ csrf_field()!}
        <div class="search_box">
            <ul class="clearfix">
                <li>
                    手机号：<input type="text" placeholder="输入手机号码" name="mobile" value="${request['mobile']!}">
                </li>
                <li>
                    用户昵称：<input type="text" placeholder="输入用户昵称" name="username" value="${request['username']!}">
                </li>
                <li>
                    批次名称：
                    <select name="batch_id" id="">
                        <option value="0">请选择批次名称</option>
                        <#list  ($batch_list as $key => $batch)
                            <option value="${key!}" <#if  ($request['batch_id'] == $key) selected </#if>>${batch!}</option>
                        </#list>
                    </select>
                </li>
                <li>
                    <button class="btn-choose" style="display: inline-block;line-height: 30px;">筛选</button>
                </li>
            </ul>
        </div>
        <div class="info_table">
            <input type="hidden" class="del_input">
            <table width="100%" style="margin-top: 15px;">
                <thead>
                    <tr>
                        <th width="10%">批次名称</th>
                        <th width="5%">ID</th>
                        <th width="15%">用户昵称</th>
                        <th width="10%">手机号</th>
                        <th width="15%">领取时间</th>
                        <th width="15%">领取码</th>
                        <th width="15%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <#list  ($list as $batch)
                    <tr>
                        <td>${batch_list[$batch->batch_id] ?? ''!}</td>
                        <td>${batch->id!}</td>
                        <td><a href="/admin/user/manage/center?user_id=${batch->user_id!}" target="_blank">${batch->username!}</a></td>
                        <td>${batch->mobile!}</td>
                        <td>${batch->receive_time!}</td>
                        <td>
                            ${batch->code!}
                            ${batch->card_no!}
                            ${batch->card_pwd!}
                        </td>
                        <td>
                            <#if  ($batch->del_flag > 0)
                                已废除
                                <#else>
                                <a href="javascript:void(0)" class="del_card_code" id="${batch->id!}">废除</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="clearfix jump_page">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(document).on('click', '.del_card_code', function () {
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">确定要废除?</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/ajax/card/code/del', function (response) {
                    if (response.error == 0) {
                        layer.close(index);
                        util.mobile_alert('删除成功');
                        location.reload();
                    } else {
                        util.mobile_alert(response.message);
                    }
                }, {id:id})
            });
        });
    })

</script>
<#include "/admin/footer.ftl">