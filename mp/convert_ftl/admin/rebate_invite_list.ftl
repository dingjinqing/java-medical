<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.4" type="text/css" />
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
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 /</span><span style="color: #666;">${title!}</span>
</div>
<div class="reserve-container">
    <form action="/admin/market/distribution/distributer/summary?top_index=4" id="form1" method="post">
        <div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>
        {{ csrf_field()!}
        <input type="hidden" name="act">
        <div class="search_reason">
            <ul>
                <li class="clearfix">
                    <div class="monile_num re_li">
                        <span>邀请人手机号</span>
                        <input type="text" placeholder="请填写手机号" name="mobile" value="${request['mobile']!}">
                    </div>
                    <div class="wx_name re_li">
                        <span>邀请人微信昵称</span>
                        <input type="text" placeholder="请填写微信昵称" name="username" value="${request['username']!}">
                    </div>
                    <div class="wx_name re_li">
                        <span>邀请人真实姓名</span>
                        <input type="text" placeholder="请填写真实姓名" name="real_name" value="${request['real_name']!}">
                    </div>
                </li>
                <li class="clearfix">
                    <div class="login_time re_li">
                        <span>被邀请人注册时间</span>
                        <input type="text" onclick="picker()" name="start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['start_time']!}" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type="text" onclick="picker()" name="end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['end_time']!}" autocomplete="off">
                    </div>
                    <button type="button" class="btn_seach">筛选</button>
                </li>
            </ul>
        </div>
        <div class="info_table" style="padding-bottom: 50px">
            {{--<table style="margin-bottom: 20px">--!}
                {{--<tr>--!}
                {{--<#list ($day_reg as $item)--!}
                        {{--<td width="150px">${item->ref_date!}:${item->num!}</td>--!}
                {{--</#list>--!}
                {{--</tr>--!}
            {{--</table>--!}
            <table width="100%" style="margin-bottom: 20px">
                <thead>
                <tr>
                    <td colspan="4"></td>
                    <td style="text-align: right;">当前页总邀请数量：</td>
                    <td>${total_invite!}</td>
                </tr>
                <tr>
                    <th width="15%">邀请人ID</th>
                    <th width="15%">邀请人微信昵称</th>
                    <th width="15%">邀请人真实姓名</th>
                    <th width="15%">邀请人手机号</th>
                    <th width="15%">邀请日期</th>
                    <th width="10%">邀请人数</th>
                </tr>
                </thead>
                <tbody>
                <#list ($data_list->items as $item)
                    <tr>
                        <td>${item->invite_id!}</td>
                        <td><a target="_blank"   href="/admin/market/distribution/distributer/invited/list?top_index=4&fanli_user_id=${item->invite_id!}" style="color: #5a8bff;">${item->username!}</a></td>
                        <td>${item->real_name!}</td>
                        <td>${item->mobile!}</td>
                        <td>${item->ref_date!}</td>
                        <td>${item->num!}</td>
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
    // function picker() {
    //     return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    // }
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


    // var left =  $('.left-menu-content .item-menu:nth-child(6)');
    // left.find("img").attr("src","/image/admin/icon_left/img_distribution_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});
</script>
<script type="text/javascript">
    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
