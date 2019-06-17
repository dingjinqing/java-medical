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
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span><a href="/admin/market/distribution/goods/brokerage/detail?top_index=4">商品返利统计</a> / </span><span style="color: #666;">${title!}</span>
</div>
<div class="reserve-container">
    <form action="/admin/market/distribution/goods/brokerage/content?top_index=4" id="form1" method="post">
        {{csrf_field()!}
        <input type="hidden" name="act">
        <input type="hidden" name="goods_id" value="${request['goods_id']!}">
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
                    <span>返利时间</span>
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
                <div class="rage_state re_li">
                    <span>返利状态</span>
                    <select name="settlement_flag" id="">
                        <option value="-1" selected>全部</option>
                        <option value="1" <#if ($request['settlement_flag']==1) selected </#if>>已返利</option>
                        <option value="0" <#if (!is_null($request['settlement_flag']) && $request['settlement_flag']==0) selected </#if>>待返利</option>
                        <option value="2" <#if ($request['settlement_flag']==2) selected </#if>>不返利</option>
                    </select>
                </div>
                <button type="button" class="btn_seach" style="margin-left: 65px">筛选</button>
                <button type="button" class="btn_exel">导出</button>
            </li>
        </ul>
    </div>
    <div class="info_table" style="padding-bottom: 50px">
        <table width="100%" style="margin-bottom: 20px">
            <thead>
            <tr>
                <th width="15%">商品名称</th>
                <th width="5%">返利商品金额</th>
                <th width="10%">商品所属订单号</th>
                <th width="10%">下单用户手机号</th>
                <th width="10%">下单用户昵称</th>
                <th width="10%">分销员手机号</th>
                <th width="10%">分销员微信昵称</th>
                <th width="5%">返利比例</th>
                <th width="5%">商品返利佣金金额</th>
                <th width="10%">返利状态</th>
                <th width="10%">返利日期</th>
            </tr>
            </thead>
            <tbody>
            <#list ($data_list as $item)
            <tr>
                <td width="15%">
                    <div class="goods_infos clearfix">
                        <a target="_blank" href="/admin/goods/manage/edit?goods_id=${item->goods_id!}&top_index=2" style="color: #0E70CA"  target="_blank">
                        <div class="gi_left"><img src="${item->goods_img!}" alt=""></div>
                        <div class="gi_right">
                            <div class="goods_name">${item->goods_name!}</div>
                            <div class="goods_spec">${item->prd_desc!}</div>
                        </div>
                        </a>
                    </div>
                </td>
                <td width="5%"><#if ($item->settlement_flag == 0 || $item->settlement_flag == 1) {{number_format($item->can_calculate_money * ($item->goods_number-$item->return_number),2)!} <#else> 0.00 </#if></td>
                <td width="10%"><a target="_blank" href="/admin/orders/manage/info?top_index=3&order_sn=${item->order_sn!}" style="color: #5a8bff;"  target="_blank">${item->order_sn!}</a></td>
                <td width="10%">${item->mobile!}</td>
                <td width="10%"><a target="_blank" href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #0E70CA"  target="_blank">${item->username!}</a></td>
                <td width="10%">${item->fanli_mobile!}</td>
                <td width="10%"><a target="_blank" href="/admin/user/manage/center?user_id=${item->fanli_user_id!}&top_index=5&sub_index=0" style="color: #0E70CA"  target="_blank">${item->fanli_username!}</a></td>
                <td width="5%">${item->fanli_percent * 100!}%</td>
                <td width="5%"><#if ($item->settlement_flag == 0 || $item->settlement_flag == 1) {{number_format($item->fanli_money * ($item->goods_number-$item->return_number),2)!} <#else> 0.00 </#if></td>
                <td width="10%"><#if ($item->settlement_flag == 0)待返利
                        <#elseif>($item->settlement_flag == 2)不返利
                        {{--<#if ($item->goods_number == $item->return_number)已退款待返利<#else>待返利 </#if>--!}
                    <#elseif>($item->settlement_flag == 1)已返利 </#if></td>
                <td width="10%">${item->finished_time!}</td>
            </tr>
            </#list>
            </tbody>
        </table>
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
        if(parseInt(page) > parseInt(last_page)) {
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
































