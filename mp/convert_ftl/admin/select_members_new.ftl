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
            width: 50px;
            display: inline-block;
        }
    </style>
</head>
<body style="background:none;height: 320px;">
<div id="set-goods">
    <div class="goods_search">
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" id="page" name="page"/>
            <input type="hidden" id="user" name="user" value="${all_can_send_user!}" disabled/>
            <input type="hidden" id="record_select_value" name="checked_user_id" value="${checkedUserId!}" />
            <input type="hidden" name="is_click_search" value="0"/>
            <div style="color: #666; margin-bottom: 10px; padding-left: 30px;">注：因微信小程序官方限制，本日部分用户可接收的消息数量已达上限，故接收不到本次消息推送</div>
            <div class="search-list">
                <span>id</span>
                <input type="text" name="id" value="${id!}"/>
                <span>昵称</span>
                <input type="text" name="username" value="${username!}">
                <span>手机号</span>
                <input type="text" name="mobile" value="${mobile!}">
            </div>
            <div class="search-list">
                <span style="width: 110px">是否关注公众号</span>
                <select name="is_focusing_official">
                    <option value="0">是否关注公众号</option>
                    <option value="1" <#if  ($is_focusing_official == 1) selected  </#if>>是</option>
                    <option value="2" <#if  ($is_focusing_official == 2) selected  </#if>>否</option>
                </select>
                <button class="btn_search">搜索</button>
            </div>
        </form>
    </div>
    <div class="goods_tb">
        <table width="100%">
            <thead>
            <tr>
                <td><input type="checkbox" name="check_all" /></td>
                <td>id</td>
                <td>昵称</td>
                <td>手机号</td>
                <td>可接收信息数量</td>
                <td>是否关注公众号</td>
            </tr>
            </thead>
            <tbody>
            <#list ($list as $item)
                <tr data-back="true" user_id="${item->user_id!}" username="${item->username!}" mobile="${item->mobile!}">
                    <td><input type="checkbox" name="user_id[]" value="${item->user_id!}" onclick="checkOne(this)" <#if  ($item->form_id_count == 0 && !in_array($item->user_id, $officialAccountUser)) disabled = true </#if>/></td>
                    <td>${item->user_id!}</td>
                    <td>${item->username!}</td>
                    <td>${item->mobile!}</td>
                    <td>${item->form_id_count ?? 0!}</td>
                    <td>
                        <#if  (in_array($item->user_id, $officialAccountUser))
                            是
                            <#else>
                            否
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <#include "/admin/jump_page_admin.ftl">
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
    // 进来时填充数据
    var record_select_value = '${checkedUserId!}' == '' ? [] : '${checkedUserId!}'.split(',');

    $('input[name="user_id[]"]').each(function (index, item) {
        if ($(this).is(':disabled')) {
            $(item).prop('checked', false);
            return true;
        }
        if ($.inArray($(item).val(), record_select_value) > -1) {
            $(item).prop('checked', true);
        } else {
            $(item).prop('checked', false);
        }
    });
    //全选
    $('#set-goods').on('click', 'input[name="check_all"]', function () {
        var is_checked = $(this).is(':checked');
        if (is_checked) {
            record_select_value = $('#user').val().split(',');
        } else {
            record_select_value = [];
        }
        $('input[name="user_id[]"]:not(:disabled)').prop('checked', is_checked);
        $('#record_select_value').val(record_select_value.join(','))
    })

    if (record_select_value.length > 0 && record_select_value.length == $('#user').val().split(',').length) {
        //触发全选
        $('#set-goods input[name="check_all"]').trigger('click');
    }

    //单选
    function checkOne(item) {
        var is_checked = $(item).is(':checked');
        if (is_checked) {
            record_select_value.push($(item).val());
        } else {
            var index = $.inArray($(item).val(), record_select_value);
            record_select_value.splice(index, 1);
        }
        if (record_select_value.length > 0 && record_select_value.length == $('#user').val().split(',').length) {
            $('#set-goods input[name="check_all"]').prop('checked', true);
        } else {
            $('#set-goods input[name="check_all"]').prop('checked', false);
        }
        $('#record_select_value').val(record_select_value.join(','));
    }
    function gopage(page) {
        var last_page = '${list->lastPage()!}';
        if(parseInt(page) > parseInt(last_page)){
            page = last_page;
        }
        $('input[name="is_click_search"]').val(0);
        $("#page").val(page);
        $("#form1").submit();
    }
    $('.btn_search').click(function () {
        $('#record_select_value').val('');
        $('input[name="is_click_search"]').val(1);
        record_select_value = [];
    })
</script>
</body>
</html>