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
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;">商品返利统计</span>
</div>
<div class="reserve-container">
    <form action="/admin/market/distribution/goods/brokerage/detail?top_index=4" id="form1" method="post">
        {{csrf_field()!}
        <input type="hidden" name="act">
    <div class="pages_nav clearfix">
        <#include ("admin.distributio_title")
    </div>
    <div class="search_reason">
        <ul>
            <li class="clearfix">
                <div class="searchg_iytma re_li">
                    <select name="cat_id" id="">
                        <option value="">请选择商品分类</option>
                        <#list ($cat_list as $cat)
                            <option value="${cat['cat_id']!}" <#if ($request['cat_id'] == $cat['cat_id']) selected </#if>>${cat['cat_name']!}</option>
                        </#list>
                    </select>
                    <input type="text" placeholder="搜索商品" name="goods_name" class="searcg_goods" value="${request['goods_name']!}">
                </div>
                <button type="button" class="btn_seach">筛选</button>
                <button type="button" class="btn_exel">导出</button>
            </li>
        </ul>
    </div>
    <div class="info_table" style="padding-bottom: 50px">
        <table width="100%" style="margin-bottom: 20px">
            <thead>
                <tr>
                    <th width="20%">商品名称</th>
                    <th width="10%">商品价格</th>
                    <th width="10%">商品所属分类</th>
                    <th width="10%">商品总销量</th>
                    <th width="10%">已返利总数量</th>
                    <th width="10%">已返利总佣金</th>
                    <th width="20%">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list ($data_list as $item)
                <tr>
                    <td width="20%"><a target="_blank" href="/admin/goods/manage/edit?goods_id=${item->goods_id!}&top_index=2" style="color: #0E70CA"  target="_blank">${item->goods_name!}</a></td>
                    <td width="10%">{{number_format($item->shop_price,2)!}</td>
                    <td width="10%">${item->cat_name!}</td>
                    <td width="10%">${item->goods_sale_num!}</td>
                    <td width="10%">${item->sale_number!}</td>
                    <td width="10%">{{number_format($item->prd_total_fanli,2)!}</td>
                    <td width="20%"><a target="_blank" href="/admin/market/distribution/goods/brokerage/content?top_index=4&goods_id=${item->goods_id!}" style="color: #5a8bff;">查看明细</a></td>
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

    // var left =  $('.left-menu-content .item-menu:nth-child(7)');
    // left.find("img").attr("src","/image/admin/icon_left/img_distribution_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});
</script>
<script type="text/javascript">
    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
































