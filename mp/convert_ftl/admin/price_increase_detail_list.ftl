<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/integral_manage.css?v=1.0.2" type="text/css" />
<style>
    .search_items{
        margin-top: 0;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view">营销管理</a>  / </span><a href="/admin/market/purchase/list">加价购 / </a><span style="color: #666;">换购明细</span>
</div>
<div class="main_container">
    <form action="" method="post" id="form1">
        {{ csrf_field()!}
        <input type="hidden" name="act"/>
        <ul class="search_items">
            <li class="clearfix">
                <div>
                    <span>昵称</span>
                    <input type="text" name="username" placeholder="请填写用户昵称" value="${request['username']!}" />
                </div>
                <div>
                    <span>手机号</span>
                    <input type="text" name="mobile" placeholder="请填写用户手机号" value="${request['mobile']!}" />
                </div>
                <div>
                    <span>换购数量</span>
                    <input type="text" name="purchase_change_num" placeholder="换购数量"  value="${request['purchase_change_num']!}" />
                </div>
                <button class="btn_searchs" onclick="search('search')">筛选</button>
                <button class="btn_excels" onclick="search('excel')">导出表格</button>
            </li>
        </ul>
        <div class="convert_table">
            <table width="100%">
                <thead>
                <tr>
                    <td >用户id</td>
                    <td >昵称</td>
                    <td width="100px">手机号</td>
                    <td width="160px">订单号</td>
                    <td width="220px">换购时间</td>
                    <td >主商品总金额</td>
                    <td >换购数量</td>
                    <td>换购总金额</td>
                </tr>
                </thead>
                <tbody>
                <#list  ($list as $item)
                    <tr>
                        <td>${item->user_id!}</td>
                        <td>
                            <a href="/admin/user/manage/center?user_id=${item->user_id!}&sub_index=0&top_index=5" target="_blank">${item->username!}</a>
                        </td>
                        <td>${item->mobile!}</td>
                        <td>
                            <a href="/admin/orders/manage/info?order_sn=${item->order_sn!}" target="_blank">${item->order_sn!}</a>
                        </td>
                        <td>${item->add_time!}</td>
                        <td>${item->total_goods_price!}</td>
                        <td>${item->purchase_change_num!}</td>
                        <td>${item->total_purchase_price!}</td>
                    </tr>
                 </#list>
                </tbody>
            </table>
        </div>
        <div class="paging_footer">
          <#include "/admin/jump_page_admin.ftl">
        </div>
    </form>
</div>
<script>
    function search(act) {
        $('input[name="act"]').val(act);
        $('#form1').submit();
    }
</script>
<#include "/admin/footer.ftl">
<script>
    getPowerInfo('main_config','purchase_price','sub_4','加价购',0);
    //    getPowerInfo('main_config','integral_goods','sub_4','积分商品',0);
</script>

