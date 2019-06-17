<html style="height: 320px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <style>
        a{
            text-decoration: none;
        }
        .search-list {
            line-height: 50px;
        }
        .search-list span {
            display: inline-block;
        }
        .user_choose{
            background: #eee;
        }
    </style>
</head>
<body style="background:none;height: 320px;">
<div id="set-goods">
    <form action="/admin/user/select/single" method="post">
        <div class="goods_search">
                {{csrf_field()!}
            <#if  ($option['source'] == 'user_list')
                <div style="color: #666; margin-bottom: 10px;">已选用户数：<span class="user_number" style="padding-right: 10px"></span>请选择需要绑定的邀请人</div>
            </#if>

            <div class="search-list">
                <span>昵称</span>
                <input type="text" name="username" value="${option['username']!}">
                <span>手机号</span>
                <input type="text" name="mobile" value="${option['mobile']!}">
                <span>真实姓名</span>
                <input type="text" name="real_name" value="${option['real_name']!}">
                <button class="btn_search">搜索</button>
            </div>
        </div>
        <div class="goods_tb">
            <table width="100%">
                <thead>
                <tr>
                    <td>用户ID</td>
                    <td>昵称</td>
                    <td>手机号</td>
                    <td>真实姓名</td>
                </tr>
                </thead>
                <tbody class="user_tbody">
                <#list ($data_list as $item)
                    <tr class="" data-back="true" user_id="${item->user_id!}">
                        <td>${item->user_id!}</td>
                        <td>${item->username!}</td>
                        <td>${item->mobile!}</td>
                        <td>${item->real_name!}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#include "/admin/jump_page_admin.ftl">
    </form>

</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>

    $(".user_tbody tr").click(function () {
        if ($(this).hasClass('user_choose')){
            $(".user_tbody tr").removeClass('user_choose');
        } else {
            $(".user_tbody tr").removeClass('user_choose');
            $(this).addClass('user_choose');
        }
    });
</script>
</body>
</html>