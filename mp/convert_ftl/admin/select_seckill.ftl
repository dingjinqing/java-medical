<html style="height: 320px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <style>
        a{
            text-decoration: none;
        }
        .goods_name{
            float: left;
            width: 140px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
    </style>
</head>
<body style="background:none;height: 320px;">
<div id="set-goods">
    <form action="/admin/seckill/select?is_check_single=${isCheckSingle!}" method="post" id="form1">
        {{csrf_field()!}
        <div class="goods_search">
            <span>关键词</span>
            <input type="text" name="keywords" value="${post['keywords']!}"/>
            <button class="btn_search">搜索</button>
        </div>
        <div class="goods_tb">
            <table width="100%">
                <thead>
                <tr>
                    <td style="width:17%;">活动名称</td>
                    <td style="width:25%;">商品信息</td>
                    <td>售价</td>
                    <td width="80px">活动库存</td>
                    <td>开始时间</td>
                    <td>结束时间</td>
                </tr>
                </thead>
                <tbody>
                <#list ($data_list as $item)
                    <tr data-back="true" goods_id="${item->goods_id!}" act_id="${item->sk_id!}" sec_price="${item->sec_price!}" sale_num="${item->sale_num!}">
                        <td>${item->name!}</td>
                        <td style="padding: 8px 5px">
                            <div class="goods_info clearfix">
                                <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                <div class="goods_name" >
                                    ${item->goods_name!}
                                </div>
                            </div>
                        </td>
                        <td>${item->shop_price!}</td>
                        <td>${item->stock!}</td>
                        <td is_status="${item->is_going!}" is_on_sale="${item->is_on_sale!}" is_delete="${item->is_delete!}">${item->start_time!}</td>
                        <td>${item->end_time!}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <#include "/admin/jump_page_admin.ftl">
    </form>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>

</body>
</html>