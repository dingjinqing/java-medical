<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.5" type="text/css" />
<style>
    .info_table .tb_paging{
        float: right;
    }
    .info_table .tb_paging td{
        padding: 0;
        border: none;
        text-align: right;
    }
    .info_table .tb_paging input{
        height: 30px;
        border: 1px solid #dedede;
        text-align: center;
        margin: 0 8px;
    }
    .info_table .tb_paging tr td a {
        display: inline-block;
        border: 1px solid #dedede;
        padding: 0px 8px;
        height: 30px;
        line-height: 30px;
        margin-left: 5px;
    }
    .info_table .tb_paging tr td a:first-child {
        margin-left: 15px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;"><#if ($request['fanli_user_id']) ${request['fanli_username']!}的</#if> 佣金统计</span>
</div>
<div class="reserve-container">
    <form action="" id="form1" method="post">
        {{csrf_field()!}
        <input type="hidden" name="act">
        <input type="hidden" name="fanli_user_id" value="${request['fanli_user_id']!}">
    <div class="pages_nav clearfix">
        <#include ("admin.distributio_title")
    </div>
    <div class="search_reason">
        <ul>
            <li class="clearfix">
                <div class="monile_num re_li">
                    <span>手机号</span>
                    <input type="text" placeholder="请填写手机号" name="fanli_mobile" value="${request['fanli_mobile']!}">
                </div>
                <div class="wx_name re_li">
                    <span>微信昵称</span>
                    <input type="text" placeholder="请填写微信昵称" name="fanli_username" value="${request['fanli_username']!}">
                </div>
                <div class="invite_num re_li">
                    <span>被邀请用户手机号</span>
                    <input type="text" placeholder="请填写被邀请用户手机号" name="mobile" value="${request['mobile']!}">
                </div>
            </li>
            <li class="clearfix">
                <div class="invite_name re_li">
                    <span>被邀请用户昵称</span>
                    <input type="text" placeholder="请填写被邀请用户昵称" name="username" value="${request['username']!}">
                </div>
                <div class="login_time re_li">
                    <span>注册时间</span>
                    <input type="text" onclick="picker()" name="start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['start_time']!}" autocomplete="off">
                    &nbsp;&nbsp;至&nbsp;&nbsp;
                    <input type="text" onclick="picker()" name="end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['end_time']!}" autocomplete="off">
                </div>
            </li>
            <li class="clearfix">
                <div class="rage_num re_li">
                    <span>返利订单号</span>
                    <input type="text" placeholder="请输入返利订单号" name="order_sn" value="${request['order_sn']!}">
                </div>
                <div class="rage_time re_li">
                    <span>返利日期</span>
                    <input type="text" onclick="picker()" name="fanli_start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['fanli_start_time']!}" autocomplete="off">
                    &nbsp;&nbsp;至&nbsp;&nbsp;
                    <input type="text" onclick="picker()" name="fanli_end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['fanli_end_time']!}" autocomplete="off">
                </div>
            </li>
            <li class="clearfix">
                <div class="rage_state re_li">
                    <span>返利状态</span>
                    <select name="settlement_flag" id="">
                        <option value="-1" selected>全部</option>
                        <option value="1" <#if ($request['settlement_flag']==1) selected </#if>>已返利</option>
                        <option value="0" <#if (!is_null($request['settlement_flag']) && $request['settlement_flag']==0) selected </#if>>待返利</option>
                        <option value="2" <#if ($request['settlement_flag']==2) selected </#if>>不返利</option>
                    </select>
                </div>
                <div class="re_li">
                    <span>分销员分组</span>
                    <select name="group_id" id="">
                        <option value="-1">请选择分组</option>
                        <option value="0" <#if (isset($request['group_id']) && $request['group_id'] == 0) selected </#if>>未分组</option>
                        <#if ($distributor_group)
                            <#list ($distributor_group as $group)
                                <option <#if ($request['group_id'] == $group->id) selected </#if>
                                value="${group->id!}">${group->group_name!}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="re_li">
                    <span>返利关系</span>
                    <select name="rebate_level" id="">
                        <option value="-1">请选择返利关系</option>
                        <option value="0" <#if (isset($request['rebate_level']) && $request['rebate_level'] == 0) selected </#if>>自购返利</option>
                        <option value="1" <#if ($request['rebate_level'] == 1) selected </#if>>直接返利</option>
                        <option value="2" <#if ($request['rebate_level'] == 2) selected </#if>>间接返利</option>

                    </select>
                </div>
                <button type="button" class="btn_exel fr" style="margin-right:85px;margin-bottom:20px">导出</button>
                <button type="button" class="btn_seach fr" style="margin-left: 65px">筛选</button>
            </li>
        </ul>
    </div>
    <div class="info_table" style="padding-bottom: 50px">
        <table width="100%" style="margin-bottom: 20px">
            <thead>
                <tr>
                    <th width="7%">分销员昵称</th>
                    <th width="7%">手机号</th>
                    <th width="6%">真实姓名</th>
                    <th width="8%">分销员分组</th>
                    <th width="7%">返利订单号</th>
                    <th width="7%">订单总金额</th>
                    <th width="7%">下单用户手机号</th>
                    <th width="7%">下单用户昵称</th>
                    <th width="7%">返利关系</th>
                    <th width="7%">订单返利商品总金额</th>
                    <th width="7%">返利佣金金额</th>
                    <th width="8%">下单时间</th>
                    <th width="7%">返利状态</th>
                    <th width="8%">返利日期</th>
                </tr>
            </thead>
            <tbody>
            <#list ($data_list as $item)
                <tr>
                    <td><a target="_blank"  href="/admin/user/manage/center?user_id=${item->fanli_user_id!}&top_index=5&sub_index=0" style="color: #0E70CA"  target="_blank">${item->fanli_username!}</a></td>
                    <td>${item->fanli_mobile!}</td>
                    <td>${item->real_name!}</td>
                    <td>
                        <#if  ($item->invite_group > 0)
                            ${distributor_group[$item->invite_group]->group_name!}
                        <#else> 未分组
                        </#if>
                    </td>
                    <td><a target="_blank"  href="/admin/orders/manage/info?top_index=3&order_sn=${item->order_sn!}" style="color: #5a8bff;">${item->order_sn!}</a></td>
                    <td>{{number_format($item->order_amount,2)!}</td>
                    <td>${item->mobile!}</td>
                    <td><a target="_blank"  href="/admin/user/manage/center?user_id=${item->order_user_id!}&top_index=5&sub_index=0" style="color: #0E70CA">${item->username!}</a></td>
                     <td> <!-- 直接邀请 | 间接邀请 | 自购返利-->
                         <#if ($item->rebate_level == 1)直接邀请
                         <#elseif>($item->rebate_level == 2)间接邀请
                         <#else> 自购返利
                         </#if>
                     </td>
{{--                    <td width="10%">{{number_format(floatval($item->fanli_money) / floatval($item->fanli_percent),2)!}</td>--!}
                    <td><#if ($item->settlement_flag == 0 || $item->settlement_flag == 1) {{number_format($item->fanli_goods_money,2)!} <#else> 0.00 </#if></td>
                    <td><#if ($item->settlement_flag == 0 || $item->settlement_flag == 1) {{number_format($item->total_rebate_money,2)!} <#else> 0.00 </#if></td>
                    <td>${item->ordered_time!}</td>
                    <td><#if ($item->settlement_flag == 0)待返利
                            <#elseif>($item->settlement_flag == 2)不返利
                        <#elseif>($item->settlement_flag == 1)已返利 </#if></td>
                    <td>${item->finished_time!}</td>
                </tr>
            </#list>
            </tbody>
        </table>
      <#include "/admin/jump_page_admin.ftl">
    </div>
    </form>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('.btn_seach').click(function () {
        $("#page").val(1);
        $("input[name='act']").val("");
        $("#form1").submit();
    })
    $(".btn_exel").click(function () {
        $("input[name='act']").val("export_csv");
        $("#form1").submit();
    })

    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }
    // var left =  $('.left-menu-content .item-menu:nth-child(7)');
    // left.find("img").attr("src","/image/admin/icon_left/img_distribution_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});
</script>
<script type="text/javascript">
    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
































