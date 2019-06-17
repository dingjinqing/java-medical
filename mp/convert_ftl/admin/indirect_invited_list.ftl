<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.6" type="text/css" />
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
    .data{
        background: #fff;
        padding: 10px 500px;
        margin-bottom: 10px;
        padding-bottom: 10px;
    }
    /*.data ul li{*/
        /*margin:0 auto;*/
        /*display:inline-block ;*/
        /*width: 200px;*/
        /*text-align: center;*/
        /*border-right:2px solid #CCCCCC;*/
    /*}*/
    /* 分页样式 */
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
    input[name='page']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging tr td,.tb_paging tr td a{
        color: #333;
        font-size: 14px;
    }
    .tb_paging{
        border: 0 !important;
    }
    .data {
        background: #fff;
        padding: 10px 470px;
        margin-bottom: 10px;
        padding-bottom: 10px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span><a href="/admin/market/distribution/distributer/list?top_index=4">分销员列表</a> / </span><span style="color: #666;">${rebate_user->username!}-间接邀请用户列表</span>
</div>
<div class="reserve-container">
    <form action="/admin/market/distribution/distributer/indirect/invited/list?top_index=4" id="form1" method="post">
        {{ csrf_field()!}
        <input type="hidden" name="act">
        <input type="hidden" name="rebate_user_id" value="${request['rebate_user_id']!}">
        <div class="search_reason">
            <ul>
                <li class="clearfix">
                    <div class="monile_nums re_li">
                        <span>手机号</span>
                        <input type="text" placeholder="请填写手机号" name="mobile" value="${request['mobile']!}">
                    </div>
                    <div class="wx_names re_li">
                        <span>微信昵称</span>
                        <input type="text" placeholder="请填写微信昵称" name="username" value="${request['username']!}">
                    </div>
                </li>
                <li class="clearfix">
                    <div class="login_time re_li">
                        <span>注册时间</span>
                        <input type="text" onclick="picker()" name="start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['start_time']!}" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type="text" onclick="picker()" name="end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['end_time']!}" autocomplete="off">
                    </div>
                    <button type="button" class="btn_seach">筛选</button>
                    <button type="button" class="btn_exel">导出</button>
                </li>
            </ul>
        </div>
        <div class="data">
            <ul>
                <li>间接邀请累计获得佣金数：<span style="color:red">{{number_format($distributor_money,2)!}</span></li>
            </ul>
        </div>
        <div class="info_table" style="padding-bottom:50px">
{{--            {{dd($data_list)!}--!}
            <table width="100%" style="margin-bottom:20px">
                <thead>
                    <tr>
                        <th width="15%">用户昵称</th>
                        <th width="10%">用户手机号</th>
                        <th width="10%">注册时间</th>
                        <th width="15%">上级用户昵称</th>
                        <th width="10%">上级用户手机号</th>
                        <th width="15%">累积返利订单数</th>
                        <th width="15%">累积订单返利商品总金额</th>
                        <th width="15%">累积返利佣金金额</th>
                    </tr>
                </thead>
                <tbody>
                    <#if ($data_list)
                        <#list ($data_list as $user)
                            <tr>
                                <td><a href="/admin/user/manage/center?user_id=${user->user_id!}&top_index=5&sub_index=0" style="color: #0E70CA"  target="_blank">${user->username!}</a></td>
                                <td>${user->mobile!}</td>
                                <td>${user->create_time!}</td>
                                <td>${user->upper_name!}</td>
                                <td>${user->upper_mobile!}</td>
                                <td>${user->order_number ?? 0!}</td>
                                <td>{{number_format($user->total_can_fanli_money,2)!}</td>
                                <td>{{number_format($user->total_fanli_money,2)!}</td>
                            </tr>
                        </#list>
                    </#if>
                </tbody>
            </table>
            {{--${data_list->links()!}--!}
            <table border="0" style="border:none;width: 95%;margin-left: 9px;" class="tb_paging">
                <tr>
                    <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                        <a href="#"
                        onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                        <a href="#"
                        onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                        <a href="#"
                        onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                        <a href="#"
                        onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                            size="5"
                            onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                        <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                    </td>
                </tr>
            </table>
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
    function gopage(page) {
        $("input[name='act']").val("");
        var last_page = '${data_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

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
