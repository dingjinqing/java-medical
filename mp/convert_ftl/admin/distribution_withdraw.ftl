<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.5" type="text/css" />
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;">返利提现审核</span>
</div>
<style>
    .btn_exel{
        width: 85px;
        height: 30px;
        border: 1px solid #ccc;
        background: #f5f5f5;
        color: #666;
        margin-left: 15px;
        display: inline-block;
        text-align: center;
    }
</style>
<form action="/admin/market/distribution/widthdraw" method="post" id="form1" style="padding-bottom: 100px">
    {{ csrf_field()!}
    <input type="hidden" name="act">
    <div class="reserve-container">
        <div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>
        <div class="withdraw_filter">
            <ul>
                <li class="clearfix">
                    <div class="re_li">
                        <span>提现单号</span>
                        <input type="text" name="order_sn" value="${request['order_sn']!}" placeholder="请输入提现单号">
                    </div>
                    <div class="login_time re_li">
                        <span>申请时间</span>
                        <input type="text" onclick="picker()" name="start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['start_time']!}" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type="text" onclick="picker()" name="end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['end_time']!}" autocomplete="off">
                    </div>
                </li>
                <li class="clearfix">
                    <div class="with_money re_li">
                        <span>提现金额</span>
                        <input type="text" style="margin-right: 7px" onkeyup="value=value.replace(/[^\.\d]/g,'')" name="min" value="${request['min']!}">
                        至
                        <input type="text" style="margin-left: 6px" onkeyup="value=value.replace(/[^\.\d]/g,'')" name="max" value="${request['max']!}">
                    </div>
                    <div class="re_li">
                        <span>处理状态</span>
                        <select name="status" id="">
                            <option value="0" selected>全部处理状态</option>
                            <option value="5" <#if ($request['status'] == 5) selected </#if>>出账失败</option>
                            <option value="4" <#if ($request['status'] == 4) selected </#if>>出账成功</option>
                            <option value="3" <#if ($request['status'] == 3) selected </#if>>已审核，待出账</option>
                            <option value="2" <#if ($request['status'] == 2) selected </#if>>已驳回申请</option>
                            <option value="1" <#if ($request['status'] == 1) selected </#if>>待审核</option>
                        </select>
                    </div>
                    <a href="##" class="btn_filter">筛选</a>
                    <a href="##" class="btn_res">重置筛选</a>
                    <button type="button" class="btn_exel">导出</button>
                </li>
            </ul>
        </div>
        <div class="withdraw_table" style="margin-bottom: 0px;min-height: 380px">
            <table width="100%">
                <thead>
                    <tr>
                        <td width="10%">申请人</td>
                        <td width="10%">申请时间</td>
                        <td width="12%">提现单号</td>
                        <td width="5%">提现金额</td>
                        <td width="10%">操作时间</td>
                        <td width="13%">处理状态</td>
                        <td width="15%">驳回原因</td>
                        <td width="15%">处理备注</td>
                        <td width="10%">操作</td>
                    </tr>
                </thead>
                <tbody>
                <#list ($data_list as $item)
                    <tr>
                        <td width="10%">
                            <a href="/admin/user/manage/center?top_index=5&user_id=${item->user_id!}&sub_index=0" target="_blank">${item->username!}</a>
                        </td>
                        <td width="10%">${item->add_time!}</td>
                        <td width="12%">${item->order_sn!}</td>
                        <td width="5%">￥${item->withdraw_cash!}</td>
                        <td width="10%">${item->update_time!}</td>
                        <td width="13%">${status[$item->status]!}</td>
                        <td width="15%">${item->refuse_desc!}</td>
                        <td width="15%">${item->desc!}</td>
                        <td width="10%">
                            <a href="/admin/market/distribution/widthdraw/detail?top_index=4&id=${item->id!}&order_sn=${item->order_sn!}&user_id=${item->user_id!}" target="_blank">查看详情</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="paging_footer" style="margin-top: 12px">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </div>
</form>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="{{asset('js/admin/distribution_config.js')!}?v=1.1.7"></script>
<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }
    $(".btn_res").click(function () {
        $(".withdraw_filter").find("input").each2(function (i,v) {
            $(v).val("");
        })
        $('select[name="status"]').find('[value=0]').prop('selected', 'selected');
    })
    $(".btn_filter").click(function () {
        $("#form1").submit();
    })
    $(".btn_exel").click(function () {
        $("input[name='act']").val("export_csv");
        $("#form1").submit();
        $("input[name='act']").val(" ");
    })

    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
