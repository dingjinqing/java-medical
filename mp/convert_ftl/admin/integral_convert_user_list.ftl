<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/integral_manage.css?v=1.0.2" type="text/css" />
<div class="title">
    <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
    <span><a href="/admin/market/integral/convert/list?nav=1&top_index=4">积分兑换</a> / </span><span style="color: #666;">积分兑换用户列表</span>
</div>
<div class="main_container">
    <form action="" method="post" id="form1">
        {{ csrf_field()!}
        <input type="hidden" name="act"/>
        <ul class="search_items">
        <li class="clearfix">
            <div>
                <span>昵称</span>
                <input type="text" name="username" placeholder="请填写用户昵称" value="${request['username']!}">
            </div>
            <div>
                <span>手机号</span>
                <input type="text" name="mobile" placeholder="请填写用户手机号" value="${request['mobile']!}">
            </div>
            <button class="btn_searchs" onclick="search('search')">筛选</button>
            <button class="btn_excels" onclick="search('excel')">导出表格</button>
        </li>
    </ul>
        <div class="convert_table">
            <table width="100%">
                <thead>
                <tr>
                    <td width="7%">用户ID</td>
                    <td width="10%">订单号</td>
                    <td width="20%">商品名称</td>
                    <td width="8%">兑换现金(元)</td>
                    <td width="10%">兑换积分数</td>
                    <td width="10%">用户昵称</td>
                    <td width="10%">手机号</td>
                    <td width="5%">兑换商品数</td>
                    <td width="15%">兑换时间</td>
                </tr>
                </thead>
                <tbody>
                <#list  ($list as $item)
                <tr>
                    <td width="7%">${item->user_id!}</td>
                    <td width="10%">${item->order_sn!}</td>
                    <td width="20%">
                        <div class="goods_info clearfix">
                            <div class="goods_img">
                                <img src="${item->goods_img!}" alt="">
                            </div>
                            <div class="goods_names">
                                <p class="g_names">${item->goods_name!}</p>
                                {{--<p class="g_spec">规格：谁谁谁</p>--!}
                            </div>
                        </div>
                    </td>
                    <td width="8%">${item->money!}</td>
                    <td width="10%">${item->score!}</td>
                    <td width="10%">${item->username!}</td>
                    <td width="10%">${item->mobile!}</td>
                    <td width="5%">${item->number!}</td>
                    <td width="15%">${item->add_time!}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#if ($list ->count())
            <div class="paging_footer">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        <#else>
            <div class="paging_footer" style="    margin-top: -315px;width: 97%;margin-left: 23px;">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </#if>
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
    getPowerInfo('main_config','integral_goods','sub_4','积分商品',0);
</script>
