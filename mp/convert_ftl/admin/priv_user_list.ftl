<#include "/admin/header.ftl">

<link rel="stylesheet" href="/css/admin/priv_role.css?v=1.0.2">
<style type="text/css">
    #list_tbl {
        width: 96%;
    }
    #list_tbl th {
        width: 25%;
    }
</style>
<div class="title">
    <span>{{ trans('admin/priv_user_list.found_config')!}&nbsp;/</span>
    <span>{{ trans('admin/priv_user_list.pre_group_manage')!}</span>
</div>

<div class="biggest-box">
    <div class="nav-role">
        <ul id="tab" class="nav-child-tabs">
            <li <#if ($nav_type==0)class="active"</#if>>
                <a <#if ($shop_id) href="/admin/config/role/list?shop_id=${shop_id!}" <#else>   href="/admin/config/role/list" </#if>>{{ trans("admin/priv_role_list.manage_shop_account")!}</a>
            </li>
            <li <#if ($nav_type==2)class="active"</#if>>
                <a <#if ($shop_id) href="/admin/config/role/group/list?shop_id=${shop_id!}" <#else> href="/admin/config/role/group/list" </#if>>{{ trans("admin/priv_role_list.manage_pre_group")!}</a>
            </li>
        </ul>
    </div>
    <script>
        // tab切换
        // $("[data-toggle='tab']").click(function () {
        //     var url_arr = ['/admin/config/role/list', '/admin/config/role/group/list'];
        //     var idx = $(this).parent().index();
        //     if (url_arr[idx] != undefined) {
        //         window.location.href = url_arr[idx];
        //     }
        // });
    </script>

    <form action="/admin/config/role/list" name="form1" id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="rec_id" id="rec_id" value="">
        {{ csrf_field()!}
            <div class="box panel panel-body">
                <table cellspacing='1' cellpadding='3'>
                    <tr>
                        <td>{{ trans('admin/priv_user_list.account_mobile')!}</td>
                        <td>
                            <select name="account_id" class="add-account-sel">
                                <#list ($account_list as $account)
                                    <option value="${account->account_id!}" <#if ($account_id == $account->account_id) selected </#if>>${account->mobile!}</option>
                                </#list>
                            </select>
                            {{--<a href="/admin/account/user/list">添加子账号</a>--!}
                        </td>
                    </tr>
                    <tr>
                        <td>{{ trans("admin/priv_user_list.pre_group")!}</td>
                        <td>
                            <select name="role_id" class="add-account-sel">
                                <#list ($role_list as $item)
                                    <option value="${item->role_id!}">${item->role_name!}</option>
                                </#list>
                            </select>
                            <a href="/admin/config/role/group/list" class="add-child-a">{{ trans("admin/priv_user_list.add_pre_group")!}</a>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="button" class="primary save-btn" name="add_account_role"
                                   value="{{ trans('admin/priv_user_list.save_btn')!}">
                        </td>
                    </tr>
                </table>
            </div>


            <#if ($msg)
                <div class="box alert alert-warning msg-info">
                    ${msg!}
                </div>
            </#if>

            <div class="box panel panel-default main-table">
                {{--<div class="layer">--!}
                    <table id="list_tbl" cellspacing='1' cellpadding='3' width="70%" class="table">
                        <thead>
                        <tr id="first_tr">
                            <th>{{ trans("admin/priv_user_list.account_name")!}</th>
                            <th>{{ trans("admin/priv_user_list.account_mobile")!}</th>
                            <th>{{ trans("admin/priv_user_list.role_name")!}</th>
                            <th>第三方公众号</th>
                            <th>{{ trans("admin/common.operation.operation")!}</th>
                        </tr>
                        </thead>
                    <#if ($data_list->count())
                        <#list ($data_list as $item)
                            <tr rec_id=${item->rec_id!} account_id=${item->account_id!} role_id="${item->role_id!}"
                                account_name="${item->account_name!}" mobile="${item->mobile!}">
                                <td style="text-align:center">${item->account_name!}</td>
                                <td style="text-align:center">${item->mobile!}</td>
                                <td style="text-align:center">${item->role_name!}</td>
                                <td>
                                    <#if  ($item->is_bind)
                                        已绑定
                                        <#else>
                                        未绑定
                                    </#if>
                                </td>
                                <td style="text-align:center">
                                    <a href="javascript:void(0);"
                                       onclick="edit_account_role(${item->rec_id!});">{{ trans("admin/common.operation.edit")!}</a>&nbsp;&nbsp;
                                    <a href="javascript:void(0);" style="margin-left: 26px;"
                                       onclick="remove_account_role(${item->rec_id!});">{{ trans("admin/common.operation.del")!}</a>&nbsp;&nbsp;
                                    <#if  ($item->official_open_id)
                                        <#if  ($item->is_bind)
                                            <a href="javascript:void(0);" style="margin-left: 26px;"
                                               class="bind_action" act="del_bind" rec_id="${item->account_id!}">解绑</a>&nbsp;&nbsp;
                                        <#else>
                                            <a href="javascript:void(0);" style="margin-left: 26px;"
                                               class="bind_action" act="bind" rec_id="${item->account_id!}">去绑定</a>&nbsp;&nbsp;
                                        </#if>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                            </#if>
                    </table>
                <#if (!$data_list->count())
                    <div class="no_data_style" style="width: 1213px;border: 1px solid #eee;height: 100px;margin-top: 10px;margin-left: 20px;">
                        <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                        </div>
                        <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                    </div>
                </#if>
                </div>

                <script>
                    function remove_account_role(id) {
                        layui.use('layer', function () {
                            var layer = layui.layer;
                            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                                title: ['提醒', 'text-align:center;padding: 0px;']
                                , area: '260px'
                                , closeBtn: 0
                                , btn: ['确定', '取消']
                            },function(index){
                                $("#act").val('del');
                                $("#rec_id").val(id);
                                $("#form1").submit();
                                layer.close(index);
                            });
                        });
                    }
                    $(document).ready(function () {
                        $(".formtable td").mouseover(function () {
                            $(".formtable tr").removeClass("hover_tr");
                            $(this).parent().addClass("hover_tr");
                        });
                    });
                    $("#list_tbl").FixedHead({tableLayout: "fixed"});
                </script>
    </form>
</div>

<div id="template" style="display: none">
    <table id="edit_account" cellspacing='1' cellpadding='3'>
        <tr>
            <td>{{ trans("admin/priv_user_list.account_name")!}</td>
            <td><span class="account_name"></span>
                <input type="hidden" name="edit_account_id" maxlength="20" readonly value="" size="34">
                <input type="hidden" name="edit_rec_id" maxlength="20" readonly value="" size="34">
            </td>
        </tr>
        <tr>
            <td>{{ trans("admin/priv_user_list.act_role")!}</td>
            <td>
                <select name="edit_role_id" style="height:23px;">
                    <#list ($role_list as $item)
                        <option value="${item->role_id!}">${item->role_name!}</option>
                    </#list>
                </select>
            </td>
        </tr>
    </table>
</div>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/priv_user_list.js"></script>
<script language="JavaScript" src="/js/admin/priv_user_list.js?v=1.0.2"></script>

<script>
    $('.bind_action').click(function () {
        var act = $(this).attr('act');
        var rec_id = $(this).attr('rec_id');

        if (act == 'del_bind') {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">请确认是否真的解绑?</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    operateBind(act, rec_id, function (res) {
                        if (res) {
                            layer.close(index);
                            window.location.reload();
                        }
                    });
                });
            });
        } else {
            operateBind(act, rec_id, function (res) {
                window.location.reload();
            });
        }
    });
    function operateBind(act, rec_id, cb) {
        util.ajax_json('/admin/survey/official/bind', function (res) {
            if (res.error == 0) {
                util.mobile_alert('操作成功');
                cb(true);
            } else {
                util.mobile_alert(res.message);
                cb(false);
            }
        }, {act:act, rec_id: rec_id, from_child_account: 1});
    }
</script>
<#include "/admin/footer.ftl">