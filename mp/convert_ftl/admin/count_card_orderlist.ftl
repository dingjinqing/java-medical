<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.6" type="text/css" />
<style>
    .search_reason ul .re_li {
        width: 280px;
    }
    /* .goods_info{
        text-align: left;
    } */
    .goods_info img{
        float: left;
        width: 68px;
        height: 68px;
    }
    .goods_info .name{
        overflow: hidden;
        margin-left: 78px;
        text-align: left;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 4;
        -webkit-box-orient: vertical;
    }
    tbody td a:link,tbody td a:visited,tbody td a:hover,tbody td a:active{
        color: #5a8bff;
    }
    .search_reason ul .re_li{
        width: 270px;
    }
    .search_reason ul .re_li span{
        margin-right: 0;
    }
    .jump_page table tbody td{
        border: none;
    }
    .no_data_style{
        margin-top: 15px;
    }
</style>
<div class="title">
    <span><a href="/admin/user/manage/list?top_index=5">会员管理</a> / </span><span>限次会员卡 / </span><span> ${info->card_name!}会员卡订单</span>
</div>
<div class="reserve-container">
    <form action="" method="post" id="form1">
        {{ csrf_field()!}
        <div class="search_reason">
            <ul>
                <input type="hidden" name="getXls">
                <li class="clearfix">
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">订单号：</span>
                        <input type="text" placeholder="请输入订单号" name="order_sn" value="${request['order_sn']!}">
                    </div>
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">会员昵称：</span>
                        <input type="text" placeholder="请输入会员昵称" name="username" value="${request['username']!}">
                    </div>
                    <div class="re_li">
                        <span style="text-align:left;display:inline;">手机号：</span>
                        <input type="text" placeholder="请输入手机号码" name="mobile" value="${request['mobile']!}">
                    </div>
                    <div class="re_li" style="width:280px;">
                        <span style="text-align:left;display:inline;">次数使用类型：</span>
                        <select name="use_type" id="">
                            <option value="0" selected>请选择</option>
                            <option value="1" <#if ($request['use_type'] == 1) selected </#if>>兑换商品</option>
                            <option value="2" <#if ($request['use_type'] == 2) selected </#if>>门店服务</option>
                        </select>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="re_li" style="width: 500px;">
                        <span style="text-align:left;display:inline;">次数变动时间：</span>
                        <input  type="text" name="start_time" value="${request['start_time']!}" placeholder="请选择时间" id="startDate" onclick="picker();"
                                onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"> 至
                        <input type="text" name="end_time" value="${request['end_time']!}" placeholder="请选择时间" id="endDate" onclick="picker();"
                               onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off">
                    </div>
                    <button type="button" class="btn_exel fr" style="vertical-align: top;line-height:26px;width:80px;margin-right:42px">导出表格</button>
                    <button type="button" class="btn_seach fr">筛选</button>
                </li>
            </ul>
        </div>

        <div class="info_table">
            <table width="100%">
                <thead>
                    <tr>
                        <th width="10%">单号</th>
                        <th width="30%">核销内容</th>
                        <th width="15%">会员昵称</th>
                        <th width="10%">手机号</th>
                        <th width="10">变动次数</th>
                        <th width="10%">次数使用类型</th>
                        <th width="15%">次数变动时间</th>
                    </tr>
                </thead>
                <tbody>
                <#list ($data_list as $data)
                    <tr>
                        <td>
                            <#if ($data->count != 0)
                                <a href="/admin/store/services/reserve/detail?order_sn=${data->order_sn!}">${data->order_sn!}</a>
                            <#elseif>($data->exchang_count != 0)
                                <a href="/admin/orders/manage/info?order_sn=${data->order_sn!}">${data->order_sn!}</a>
                            </#if>
                        </td>
                        <td class="goods_info">
                            <div>
                                <img src="${data->img!}" alt="">
                                <div class="name">
                                   ${data->name!}
                                </div>
                            </div>
                        </td>
                        <td><a href="/admin/user/manage/center?user_id=${data->user_id!}&top_index=5&sub_index=0">${data->username!}</a></td>
                        <td>${data->mobile!}</td>
                        <#if ($data->count != 0)
                            <td>${data->count!}</td>
                            <td>门店服务</td>
                        <#elseif>($data->exchang_count != 0)
                            <td>${data->exchang_count!}</td>
                            <td>兑换商品</td>
                        </#if>
                        <td>${data->add_time!}</td>

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
<#include "/admin/footer.ftl">
<script>
function picker() {
    return WdatePicker({dateFmt: 'yyyy-MM-dd'});
}
$('.btn_seach').click(function () {
    $('[name="getXls"]').val('');
    $("#form1").submit();
})
$('.btn_exel').click(function () {
    $('[name="getXls"]').val('getXls');
    $("#form1").submit();
})
</script>