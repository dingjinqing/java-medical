<style type="text/css">
    #common_user_header {
        padding: 12px 16px;
    }
    #common_user_header .search-list input {
        width: 140px;
        height: 30px;
        border: 1px solid #ccc;
        padding-left: 12px;
        margin-right: 34px;
        margin-left: 4px;
        border-radius: 3px;
    }
    #common_user_header input[type='checkbox'] {
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        width: 12px;
        height: 12px;
        background: url(/image/admin/square_no.png) no-repeat;
        background-size: 100%;
        margin-right: 3px;
        margin-top: 0;
        position: relative;
        top: 3px;
    }
    #common_user_header input[type="checkbox"]:checked {
        background: url(/image/admin/square_yes.png) no-repeat;
    }
    #common_user_header .common_user_search {
        width: 70px;
        height: 30px;
        border: none;
        background: #5A8BFF;
        color: #fff;
        border-radius: 3px;
    }
    #common_user_header thead td {
        background: #faf9f8;
        text-align: center;
        color: #333;
    }
    #common_user_header tbody td {
        text-align: center;
    }
</style>
<div id="common_user_template">
    <table width="100%">
        <tbody>
        <tr data-back="true">
            <td><input type="checkbox" name="common_user_id[]" value="" onclick="loadCommonUserList.checkOne(this)" /></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</div>
<div id="common_user_header" class="hide">
    <form action="" method="post" id="common_user_form">
        <div>
            <input type="hidden" id="checked_common_user"/>
            <div class="search-list">
                <span>ID</span>
                <input type="text" name="common_user_id" value=""/>
                <span>昵称</span>
                <input type="text" name="common_user_name" value="">
                <span>手机号</span>
                <input type="text" name="common_user_mobile" value="">
                <a class="common_user_search" style="display: inline-block; text-align: center; line-height: 30px; cursor: pointer;">搜索</a>
            </div>
        </div>
        <div style="margin-top: 15px;">
            <table width="100%">
                <thead>
                <tr>
                    <td>本页全选：<input type="checkbox" name="common_check_all" onclick="loadCommonUserList.checkAll(this)" /></td>
                    <td>ID</td>
                    <td>昵称</td>
                    <td>手机号</td>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div class="no_data_style" style="width: 100%;border: 1px solid #eee;margin-top:10px;">
                <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                </div>
                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
            </div>
            <div id="common_user_page_show" style="text-align: right;"></div>
        </div>
    </form>
</div>
<script>
    var loadCommonUserList = {
        init: function (page, isFromSearch) {
            if (isFromSearch === true) {
                $('#checked_common_user').val('');
            }
            var userParamJson = {
                'id' : $('#common_user_header').find('[name="common_user_id"]').val(),
                'username' : $('#common_user_header').find('[name="common_user_name"]').val(),
                'mobile' : $('#common_user_header').find('[name="common_user_mobile"]').val(),
                'page' : page ? page : 1,
                'checked_common_user': $('#checked_common_user').val()
            };
            util.ajax_json('/admin/frame/user/newlist', function (response) {
                util.loadPage(response.content.user_list.total, response.content.user_list.current_page, function (res) {
                    loadCommonUserList.init(res.curr);
                }, 'common_user_page_show', 15)
                var list = response.content.user_list.data, html = '';
                var checked_common_user = response.content.request['checked_common_user'];
                $('#checked_common_user').val(checked_common_user);
                checked_common_user = $('#checked_common_user').val() != '' ? $('#checked_common_user').val().split(',') : [];

                for (var i in list) {
                    var common_user_template = $('#common_user_template').clone();
                    common_user_template.find('tr').attr('user_id', list[i].user_id);
                    common_user_template.find('tr td').eq(0).find('input').val(list[i].user_id);
                    common_user_template.find('tr td').eq(1).html(list[i].user_id);
                    common_user_template.find('tr td').eq(2).html(list[i].username);
                    common_user_template.find('tr td').eq(3).html(list[i].mobile);
                    if ($.inArray(list[i].user_id.toString(), checked_common_user) > -1) {
                        common_user_template.find('tr td').eq(0).find('input').attr('checked', true);
                    }
                    html += common_user_template.html();
                }

                if(list.length < 1){
                    $('.no_data_style').show()
                    $('#common_user_page_show').hide()
                } else {
                    $('.no_data_style').hide()
                    $('#common_user_page_show').show()
                }
                $('#common_user_header tbody').html(html);

                // 检查是否全选
                var checkedLen = $('#common_user_header tbody input[type="checkbox"]:checked').length;
                var hasCheckedLen = $('#common_user_header tbody input[type="checkbox"]').length;
                if (hasCheckedLen > 0 && checkedLen == hasCheckedLen) {
                    $('input[name="common_check_all"]').prop('checked', true);
                } else {
                    $('input[name="common_check_all"]').prop('checked', false);
                }
            }, userParamJson);
        },
        checkOne: function (item) {
            // 单选
            var record_select_value = $('#checked_common_user').val() != '' ? $('#checked_common_user').val().split(',') : [];
            var is_checked = $(item).is(':checked');
            if (is_checked) {
                record_select_value.push($(item).val());
            } else {
                var index = $.inArray($(item).val(), record_select_value);
                if (index > -1) record_select_value.splice(index, 1);
            }
            var checkedLen = $('#common_user_header tbody input[type="checkbox"]:checked').length;
            var hasCheckedLen = $('#common_user_header tbody input[type="checkbox"]').length;
            if (hasCheckedLen > 0 && checkedLen == hasCheckedLen) {
                $('input[name="common_check_all"]').prop('checked', true);
            } else {
                $('input[name="common_check_all"]').prop('checked', false);
            }

            $('#checked_common_user').val(record_select_value.join(','));
        },
        checkAll: function (obj) {
            // 本页全选
            var record_select_value = $('#checked_common_user').val() != '' ? $('#checked_common_user').val().split(',') : [];
            var is_checked = $(obj).is(':checked');
            $('#common_user_header tbody input[type="checkbox"]').each(function (index, item) {
                var index = $.inArray($(item).val(), record_select_value);
                if (is_checked) {
                    if (index === -1) record_select_value.push($(item).val());
                } else {
                    if (index > -1) record_select_value.splice(index, 1);
                }
                $(item).prop('checked', is_checked);
            })
            $('#checked_common_user').val(record_select_value.join(','));
        }
    };

    $(document).on('click', '#open_common_user', function () {
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['用户列表', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['850px', '500px']
                , content: $('#common_user_header')
                , btn: ['确定', '取消']
                , btnAlign: 'r'
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success: function (layero, index) {
                    $('#common_user_header').removeClass('hide');
                    $('#common_user_header input').val('');
                    $('#checked_common_user').val($('[name="send_member"]').val());
                    loadCommonUserList.init();
                }
                ,yes: function (index) {
                    $('[name="send_member"]').val($('#checked_common_user').val());
                    if(typeof getUserMember != 'undefined' && getUserMember instanceof Function) {
                        getUserMember();
                    }
                    if ($('#checked_common_user').val() != '')  {
                        $('.common_member_num').html($('#checked_common_user').val().split(',').length);
                    } else {
                        $('.common_member_num').html(0);
                    }
                    layer.close(index);
                }
            });
        });

    });
    $(document).on('click', '.common_user_search', function () {
        loadCommonUserList.init(1, true);
    })

</script>