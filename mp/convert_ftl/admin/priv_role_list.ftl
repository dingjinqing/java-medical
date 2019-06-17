<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/priv_role.css?v=1.7.2">
<style type="text/css">
    .aui_content {
        padding-top: 0 !important;
        padding: 0 !important;
    }

    .fr {
        float: right;
    }

    .aui_icon,
    .aui_main {
        padding-top: 0 !important;
    }

    #role_template {
        width: 741px !important;
        height: 449px !important;
        overflow-y: scroll !important;
    }

    .aui_state_lock {
        top: 50px !important;
    }

    [data-toggle="switch_box"] {
        display: none;
    }

    [data-toggle="switch_box"].active {
        display: block;
    } 

    .function_role {
        margin-top:16px;
    }

    .function_title {
        display: block;
        margin-bottom:10px;
    }

    .password_box {
        width: 100%;
        padding-left: 10px;
        border: 1px solid #eee;
    }

    .password_set {
        padding: 5px 0;
        border-bottom: 1px solid #eee;
    }

    .password_tips {
        float: left;
        color: #999
    }

    .password_set a {
        float: right;
        margin-right: 10px;
        padding: 2px 20px;
        border: 1px solid #eee;
        border-radius: 2px;
        margin-top: 6px;
        color: #000;
        text-decoration: none;
    }

    .password_info {
        padding: 8px 0;
    }

    .password_info p {
        line-height: 45px;
    }

    .password_info input[type="password"] {
        margin-left: 5px;
        height: 30px;
        width: 150px;
    }

    .password_info span.red {
        color: red;
    }

    .functional_groups {
        display: inline-block;
        width: 100%;
    }

    .functional_groups table {
        width: 100%;
    }

    .functional_groups table td {
        border: 1px solid #eee;
    }

    label.switch {
        display: inline-block;
        background-color: #ddd;
        height: 20px;
        width: 40px;
        cursor: pointer;
        border-radius: 25px;
        margin-bottom: 0;
        vertical-align: middle;
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    }

    label.switch::before {
        content: '';
        position: relative;
        display: block;
        left: 0;
        border-radius: 25px;
        height: 100%;
        width: 20px;
        background-color: white;
        opacity: 1;
        box-shadow: 1px 1px 1px 1px rgba(0, 0, 0, 0.08);
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    }

    /* label.switch:after {
        
    } */
    input.switch {
        display: none;
    }

    /* input.switch:checked~label:after {
    opacity: 1;
    } */

    /* 选中后，未选中样式消失 */
    input.switch:checked~label:before {
        left: 21px;
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    }

    /*选中后label的背景色改变*/
    input.switch:checked~label {
        background-color: #f7931e;
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    }
    input[type="text"]{
        padding-left: 5px;
    }
</style>
<div class="title">
    <span>{{ trans('admin/priv_user_list.found_config')!}&nbsp;/</span>
    <span>{{ trans('admin/priv_user_list.pre_group_manage')!}</span>
</div>

<div class="biggest-box">
    <div class="nav-role">
        <ul id="tab" class="nav-child-tabs">
            <li <#if ($nav_type==0)class="active" </#if>>
                <a href="#" data-toggle="tab">{{ trans("admin/priv_role_list.manage_shop_account")!}</a>
            </li>
            <li <#if ($nav_type==2)class="active" </#if>>
                <a href="#" data-toggle="tab">{{ trans("admin/priv_role_list.manage_pre_group")!}</a>
            </li>
        </ul>
    </div>

    <script>
        // tab切换
        $("[data-toggle='tab']").click(function () {
            var url_arr = ['/admin/config/role/list', '/admin/config/role/group/list'];
            var idx = $(this).parent().index();
            if (url_arr[idx] != undefined) {
                window.location.href = url_arr[idx];
            }
        });
    </script>

    <div class="box panel panel-body panel-body-btn">
        <input type="button" id="add-role" class="primary layui-btn" name="add_role" value="{{ trans("admin/priv_role_list.add_pre_group")!}"
            data-id="pre-edit" data-method="offset" data-title="添加权限组" style="height: 26px;" />
    </div>


    <form action="/admin/config/role/group/list" name="form1" id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="role_id" id="role_id" value="">
        <input type="hidden" name="edit_pass" id="edit_pass" value="">
        {{ csrf_field()!}
        <div class="box panel main-table">
            {{--<div class="layer">--!}
                <table id="list_tbl" align="center" s cellspacing='1' cellpadding='3' class="table pre-tbl">
                    <thead>
                        <tr id="first_tr">
                            <th>{{ trans("admin/priv_role_list.role_name")!}</th>
                            <th>{{ trans("admin/priv_role_list.create_time")!}</th>
                            <th style="width: 15%">{{ trans("admin/common.operation.operation")!}</th>
                        </tr>
                    </thead>
                    <#if ($data_list->count())
                    <#list ($data_list as $item)
                    <tr role_id="${item->role_id!}" role_name="${item->role_name!}" privilege_list="${item->privilege_list!}" role_pass="${item->role_pass!}" privilege_pass="${item->privilege_pass!}">
                        <td>${item->role_name!}</td>
                        <td>${item->create_time!}</td>
                        <td>
                            <a href="javascript:void(0);" onclick="edit_role(${item->role_id!});" class="layui-btn layui-btn-normal"
                                style="vertical-align: 0;" data-id="pre-edit" data-method="third" data-title="{{ trans("admin/common.operation.edit")!}">{{
                                trans("admin/common.operation.edit")!}</a>&nbsp;&nbsp;
                            <a href="javascript:void(0);" onclick="remove_role(${item->role_id!});">{{
                                trans("admin/common.operation.del")!}</a>&nbsp;&nbsp;
                        </td>
                    </tr>
                    </#list>
                    </#if>
                </table>
                <#if (!$data_list->count())
                <div class="no_data_style" style="width: 70%;border: 1px solid #eee;height: 100px;margin-top: 10px;margin-left: 20px;">
                    <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                        <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                    </div>
                    <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                </div>
                </#if>
            </div>
            {{--<table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">--!}
                {{--<tr>--!}
                    {{--<td>&nbsp;</td>--!}
                    {{--<td align="right">--!}
                        {{--<table width="100%" border="0">--!}
                            {{--<tr>--!}
                                {{--<td align="right">{{
                                    trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count(),'total'=>$data_list->total(),])!}--!}
                                    {{--<a href="#" onClick="return gopage(1);">{{
                                        trans("admin/common.page.first_page")!}</a>--!}
                                    {{--<a href="#" --!} {{--onClick="return gopage(${data_list->currentPage() -1!}
                                        );">{{ trans("admin/common.page.pre_page")!}</a>--!}
                                    {{--<a href="#" --!} {{--onClick="return gopage(${data_list->currentPage() + 1!}
                                        );">{{ trans("admin/common.page.next_page")!}</a>--!}
                                    {{--<a href="#" --!} {{--onClick="return gopage(${data_list->lastPage()!} );">{{
                                        trans("admin/common.page.last_page")!}</a>--!}
                                    {{--<input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                        --!} {{--size="5"--!}
                                        {{--onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                        --!} {{--</td>--!} {{--</tr>--!} {{--</table>--!} {{--</td>--!} {{--</tr>--!}
                                        {{--</table>--!} <script>

                                            function gopage(page) {
                                                $("#page").val(page);
                                                $("#form1").submit();
                                            }

                                            function remove_role(id) {
                                                layui.use('layer', function () {
                                                    var layer = layui.layer;
                                                    layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                                                        title: ['提醒', 'text-align:center;padding: 0px;']
                                                        , area: '260px'
                                                        , closeBtn: 0
                                                        , btn: ['确定', '取消']
                                                    }, function (index) {
                                                        $("#act").val('del');
                                                        $("#role_id").val(id);
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
                                            $("#list_tbl").FixedHead({ tableLayout: "fixed" });
                                        </script>
    </form>
</div>

<div id="set-pre-edit" style="display: none">

    <div id="role_template" style="overflow-y: scroll;padding: 0px 20px;">
        <input type="hidden" name="cur_role_id">
        <#if ($sub_account_id == 0)
        <ul class="role_switch clearfix">
            <li class="active">页面权限管理</li>
            <li>功能权限管理</li>
        </ul>
        </#if>
        <div data-toggle="switch_box" class="active">
            <table cellspacing='1' cellpadding='3' style="width: 100%;">
                <tr>
                    
                    <td style="padding: 16px 0 0 0;">
                        <p style="margin-bottom: 10px;">{{
                        trans("admin/priv_role_list.name_pre_group")!}</p>
                        <input type="text" name="role_name" maxlength="20" style="width:200px;height:30px;padding-left: 12px;border: 1px solid #ddd;"
                            placeholder="{{ trans("admin/priv_role_list.please_input_role_name")!}" value="" size="34">
                    </td>
                </tr>
                <tr>
                    <td style="padding: 16px 0 10px 0;vertical-align: top !important;">{{
                        trans("admin/priv_role_list.pre_pre_group")!}</td>
                    <!-- <td style="padding-top: 6px;">
                    <td></td> -->
                </tr>
                <tr>
                        <table class="table table-bordered">
                            <#if ($menu_cfg)
                            <#list ($menu_cfg as $index=>$top)
                            <tr>
                                <td style="width:30%;">
                                    <input type="checkbox" name="main_cbx[]" checked="true" priv_name="${top->en_name!}">
                                    ${top->name!}&nbsp;
                                </td>
                                <td>
                                    <ul class="role_ul">
                                        <#list ($authority as $sub_item)
                                        <#if ($sub_item['top_index'] == $index)
                                            {{--<div style='clear:both;'></div>--!}
                                            <li style="display: inline-block; width: 122px">
                                                <input type="checkbox" main="${top->en_name!}" class='two_click' name_a="two"
                                                    name="sub_cbx[]" checked="true" value="${sub_item['en_name']!}"
                                                    priv_name="${sub_item['en_name']!}" pre_name="{{json_encode($sub_item['pre_name'])!}"/>
                                                <img src="" alt="">
                                                <span>${sub_item['name']!}</span>
                                            </li>
                                            {{--<div style='clear:both;'></div>--!}
                                        </#if>
                                        {{--<div id="role_template1" class='three_input' style='display:block'>--!}
                                            {{--<#list ((array)$sub_item->sub as $three_item)--!}
                                            {{--<#if ($three_item->name)--!}
                                            {{--<li style="list-style-type:none;float: left;<#if ($three_item->hide)display:none </#if>">--!}
                                                {{--<input type='checkbox' main="${item->en_name!}" name_a="third" name="sub_cbx[]"--!}
                                                    {{--checked="true" value="${three_item->en_name!}" main_next="${sub_item->en_name!}" />--!}
                                                {{--${three_item->name!}--!}
                                            {{--</li>--!}
                                            {{--</#if>--!}
                                            {{--</#list>--!}
                                        {{--</div>--!}
                                        </#list>
                                    </ul>
                                </td>
                            </tr>
                            </#list>
                            </#if>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <#if ($sub_account_id == 0)
        <div data-toggle="switch_box">
            <div class="function_role set_role_pass" style="display:block;">
                <div class="function_title">功能权限密码</div>
                <div class="password_box">
                    <div class="password_set clearfix" style="border-bottom:none;">
                        <div class="password_tips">
                            <p>设置功能权限密码后，开启密码开关</p>
                            <p>此权限组用户在进行对应功能操作时，需要先输入此密码</p>
                        </div>
                        <a href="javascript:;" data-sign="set_password">设置密码</a>
                    </div>
                </div>
            </div>
            <div class="function_role edit_role_pass" style="display:none;">
                <div class="function_title">功能权限密码</div>
                <div class="password_box">
                    <div class="password_set clearfix" style="border-bottom:none;">
                        <div class="password_tips">
                            <p>设置功能权限密码后，开启密码开关</p>
                            <p>此权限组用户在进行对应功能操作时，需要先输入此密码</p>
                        </div>
                        <a href="javascript:;" data-sign="update_password">修改密码</a>
                    </div>
                </div>
            </div>
            <div class="function_role" style="display:none;">
                <div class="function_title">功能权限密码</div>
                <div class="password_box">
                    {{--<div>功能权限密码</div>--!}
                    <div class="password_set clearfix">
                        <div class="password_tips">
                            <p>设置功能权限密码后，开启密码开关</p>
                            <p>此权限组用户在进行对应功能操作时，需要先输入此密码</p>
                        </div>
                        <a href="javascript:;" data-sign="save_password" hidden >保存</a>
                    </div>
                    <div class="password_info">
                        <p><span class="red">*</span><span>登录密码：</span><input type="password" placeholder="请输入管理员登录密码" name="login_pass" class="login_pass"></p>
                        <p><span class="red">*</span><span class="new_password">设置密码：</span><input type="password" placeholder="请输入功能权限密码" name="role_pass" class="role_pass"> <span style="color:#999">修改后的新密码将替换原有密码</span></p>
                    </div>
                </div>
            </div>
            <div class="function_role" id="role_pass">
                {{--<div class="function_title">功能组权限</div>--!}
                <div class="functional_groups">
                    <div class="function_title">功能组权限</div>
                    <table>
                        <tbody>
                            <tr>
                                <td rowspan="13" width="20%">功能权限管理</td>
                                <td>
                                    <span class="fl">
                                        <input type="checkbox" name="privilege_pass[]" value="user_export">会员导出
                                    </span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox1" name="privilege_pass_status[]" value="user_export">
                                        <label for="checkbox1" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="return_order">退款退货审核</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox2" name="privilege_pass_status[]" value="return_order">
                                        <label for="checkbox2" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="distribution_withdraw">分销返利提现</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox3" name="privilege_pass_status[]" value="distribution_withdraw">
                                        <label for="checkbox3" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="scan_code_purchase">扫码购订单强制核销</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox4" name="privilege_pass_status[]" value="scan_code_purchase">
                                        <label for="checkbox4" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="card_order_return">会员卡订单退款</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox5" name="privilege_pass_status[]" value="card_order_return">
                                        <label for="checkbox5" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="batch_score">修改积分</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox6" name="privilege_pass_status[]" value="batch_score">
                                        <label for="checkbox6" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="batch_account">修改余额</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox12" name="privilege_pass_status[]" value="batch_account">
                                        <label for="checkbox12" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="batch_card">发放会员卡</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox7" name="privilege_pass_status[]" value="batch_card">
                                        <label for="checkbox7" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="batch_delete">禁止登录</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox8" name="privilege_pass_status[]" value="batch_delete">
                                        <label for="checkbox8" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="batch_tag">打标签</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox9" name="privilege_pass_status[]" value="batch_tag">
                                        <label for="checkbox9" class="switch"></label>
                                        <span></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="change_invite">修改邀请人</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox10" name="privilege_pass_status[]" value="change_invite">
                                        <label for="checkbox10" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="goods_export">商品导出</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox11" name="privilege_pass_status[]" value="goods_export">
                                        <label for="checkbox11" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <span class="fl"><input type="checkbox"  name="privilege_pass[]" value="order_export">订单导出</span>
                                    <span class="fr">
                                        <span>密码：</span>
                                        <input type="checkbox" class="switch pass_box" id="checkbox13" name="privilege_pass_status[]" value="order_export">
                                        <label for="checkbox13" class="switch"></label>
                                        <span style="color: rgb(153, 153, 153);"></span>
                                    </span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        </#if>
    </div>
</div>
<#include "/admin/footer.ftl">
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/priv_role_list.js"></script>
<script language="JavaScript" src="/js/admin/priv_role_list.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/md5.js"></script>
<script>
    var login_pass = "${login_pass->password!}";
    var sub_account_id = "${sub_account_id!}";
    //弹框
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        //触发事件
        var active = {
            offset: function (othis) {
                var type = othis.data('type')
                    , text = othis.data('title')
                    , id = othis.data('id');
                layer.open({
                    type: 1
                    , title: [text, 'text-align:center;padding-left: 10px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['741px', '531px']
                    , id: 'layerDemo' + type //防止重复弹出
                    , content: $('#set-' + id) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['保存', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , yes: function (index, layero) { //保存按钮的回调
                        if ($('[name="role_name"]').val() == "") {
                            util.mobile_alert("权限组名称不能为空！");
                            return false;
                        }
                        var pass_type = false;
                        $(".functional_groups").find(".pass_box").each(function (q) {
                            if($(".functional_groups").find(".pass_box").eq(q).prop("checked") == true)
                                pass_type = true;
                        })
                        if(pass_type){
                            if(hex_md5($(".login_pass").val()) != login_pass){
                                util.mobile_alert("登录密码输入有误");
                                return false;
                            }
                            if($(".role_pass").val() == ""){
                                util.mobile_alert("功能密码设置不能为空");
                                return false;
                            }
                        }
                        var param = $("#role_template :input").serializeArray();
                         util.ajax_json("/admin/config/role/add", function (d) {
                            if (d && d.error == 0) {
                                layer.ready(function () {
                                    layer.msg(window.jslang.priv_role_list.add_sucess, { time: 2000 });
                                });
                                setTimeout(function () {
                                    $('[name="act"]').val('');
                                    $("#form1").submit();
                                }, 500);
                            } else {
                                layer.ready(function () {
                                    layer.msg(window.jslang.priv_role_list.add_failed, { time: 2000 });
                                });
                            }
                        }, param);
                        return true;
                        //return false 开启该代码可禁止点击该按钮关闭
                    }, btn2: function (index, layero) {
                        //按钮取消的回调

                        //return false 开启该代码可禁止点击该按钮关闭
                    }
                });
            }

        };
        $('.layui-btn').on('click', function () {
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
        $('.role_switch li').click(function () {
            let index = $(this).index();
            $('.role_switch li').removeClass('active');
            $(this).addClass('active');
            $('[data-toggle="switch_box"]').removeClass('active');
            $('[data-toggle="switch_box"]').eq(index).addClass('active')
        })
        function checkedStatus() {
            $('.functional_groups .fr input').each(function () {
                let checkStatus = $(this).prop('checked') ? "已开启" : "已关闭";
                $(this).next().next().text(checkStatus)
                if($(this).prop('checked')){
                    $(this).next().next().css("color","#000");
                } else {
                    $(this).next().next().css("color","#999");
                }
            })
            $('.functional_groups .fr input').click(function () {
                let checkStatus = $(this).prop('checked') ? "已开启" : "已关闭";
                $(this).next().next().text(checkStatus)
                if($(this).prop('checked')){
                    $(this).next().next().css("color","#000");
                } else {
                    $(this).next().next().css("color","#999");
                }
            })
        }
        $('[data-sign]').click(function(){
            console.log(this);
            if($(this).data('sign') === "set_password"){
                $('[data-sign="save_password"]').parents('.function_role').show();
                $('[data-sign="save_password"]').find('.new_password').text('设置密码');
                $(this).parents('.function_role').hide();
            }
            if($(this).data('sign') === "update_password"){
                $('[data-sign="save_password"]').parents('.function_role').show();
                $('[data-sign="save_password"]').find('.new_password').text('设置新密码');
                $(this).parents('.function_role').hide();
                $("input[name='edit_pass']").val(1);
            }
            if($(this).data('sign') === "save_password"){
                let AdminPassword = $(this).parents('.function_role').find('input[name="login_pass"]');
                let rolePassword= $(this).parents('.function_role').find('input[name="privilege_pass"]');
                if(AdminPassword.val() === ""){
                    util.mobile_alert("请输入管理员登录密码");
                    AdminPassword.focus();
                    return false;
                }
                if(rolePassword.val() === ""){
                    util.mobile_alert("请输入功能权限密码");
                    rolePassword.focus();
                    return false;
                }
            }
        })
        $('.pass_box').click(function(){
            let checkStatus = $(this).prop('checked');
            console.log($(this));
            console.log($(this).parents('fr').prev('fl').find('input'));
            $(this).parents('.fr').prev('.fl').find('input').prop('checked',checkStatus);
        })
        checkedStatus()
        $('.role_switch li').eq(0).trigger('click');
    });
</script>