<#include ("system.header")

<#include ("system.mp_tab")

<link rel="stylesheet" href="/css/system/mp_table_list.css" type="text/css"/>
<form action="/system/mp/auth/list" name="form1" id="form1" method="post">
    {{ csrf_field()!}
    <div class="box">
        <div class="form-query form-inline">
            <select name="template_id" class="form-control">
                <option value="-1">选择模板Id</option>
                <#list ($template_list as $item)
                    <option value="${item->template_id!}"
                            <#if ($item->template_id == ($request['template_id'] ?? -1)) selected="selected" </#if>>${item->user_version!}</option>
                </#list>
            </select>

            <select name="is_auth_ok" class="form-control">
                <option value="-1">选择是否授权</option>
                <option value="0" <#if (($request['is_auth_ok'] ?? -1) == 0) selected </#if>>未授权</option>
                <option value="1" <#if (($request['is_auth_ok'] ?? -1) == 1) selected </#if>>已授权</option>
            </select>

            <select name="open_pay" class="form-control">
                <option value="-1">选择是否微信支付</option>
                <option value="0" <#if (($request['open_pay'] ?? -1) == 0) selected </#if>>不支持微信支付</option>
                <option value="1" <#if (($request['open_pay'] ?? -1) == 1) selected </#if>>支持微信支付</option>
            </select>

            <select name="audit_state" class="form-control">
                <option value="-1">选择审核状态</option>
                <option value="0" <#if (($request['audit_state'] ?? -1)  == 0) selected </#if>>未提交审核</option>
                <option value="1" <#if (($request['audit_state'] ?? -1)  == 1) selected </#if>>审核中</option>
                <option value="2" <#if (($request['audit_state'] ?? -1) == 2) selected </#if>>审核通过</option>
                <option value="3" <#if (($request['audit_state'] ?? -1) == 3) selected </#if>>审核未通过</option>
            </select>

            <select name="publish_state" class="form-control">
                <option value="-1">选择发布状态</option>
                <option value="0" <#if (($request['publish_state'] ?? -1) == 0) selected </#if>>未发布</option>
                <option value="1" <#if (($request['publish_state'] ?? -1) == 1) selected </#if>>已发布</option>
            </select>

            <select name="is_use" class="form-control">
                <option value="-1" selected>选择店铺状态</option>
                <option value="1" <#if ($request['is_use'] == 1) selected </#if>>使用中</option>
                <option value="2" <#if ($request['is_use'] == 2) selected </#if>>已过期</option>
            </select>

            <input type="text" class="form-control" name="app_id" placeholder="请输入app_id"
                   value="${request['app_id']!}">

            <input type="text" class="form-control" name="shop_id" placeholder="请输入shop_id"
                   value="${request['shop_id']!}">

            <input type="text" class="form-control" name="nick_name" placeholder="请输入小程序名称"
                   value="${request['nick_name']!}">

            <button class="btn btn-primary" name="search">搜索</button>
        </div>
    </div>
    <div class="box panel main-table">
        <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
            <thead>
            <tr>
                <th>app_id</th>
                <th>店铺ID</th>
                <th>昵称</th>
                <th>头像</th>
                <th>是否授权</th>
                <th>是否认证</th>
                <th>是否支持微信支付</th>
                <th>最后授权时间</th>
                <th>绑定模板ID</th>
                <th>审核状态</th>
                <th>发布状态</th>
                <th>店铺状态</th>
                <th>操作</th>
            </tr>
            </thead>

            <#list ($data_list as $item)
                <tr style="text-align:center;">
                    <td>${item->app_id!}</td>
                    <td>${item->shop_id!}</td>
                    <td>${item->nick_name!}</td>
                    <td><img src="${item->head_img!}" width="50"></td>
                    <td>
                        <#if ( $item->is_auth_ok == 1)
                            <span class="label label-success">已授权</span>
                        <#else>
                            <span class="label label-danger">已取消授权</span>
                        </#if>
                    </td>
                    <td>
                        <#if ($item->verify_type_info == "-1")
                            <span class="label label-danger">未认证</span>
                        <#elseif>($item->verify_type_info == "0")
                            <span class="label label-success">已认证</span>
                        </#if>
                    </td>
                    <td>
                        <#if ( $item->open_pay == 1)
                            <span class="label label-success">支持</span>
                        <#else>
                            <span class="label label-danger">不支持</span>
                        </#if>
                    </td>
                    <td>${item->last_auth_time!}</td>
                    <td>${item->bind_template_id!}</td>
                    <td title="${item->audit_ok_time!}">
                        <#if ($item->audit_state == 0) 未提交 <#elseif>($item->audit_state == 1)
                            审核中 <#elseif>($item->audit_state == 2)审核成功<#elseif>($item->audit_state == 3)审核失败</#if> </td>
                    <td title="${item->publish_time!}">
                        <#if ( $item->publish_state == 1 && $item->publish_time >= $item->audit_ok_time)
                            <span class="label label-success">已发布</span>
                        <#else>
                            <span class="label label-danger">未发布</span>
                        </#if>
                    </td>
                    <td><#if ($item->expire_time >= date("Y-m-d H:i:s",time()))使用中
                            <#else> 已过期
                            </#if>
                    </td>
                    <td>
                        <a href="/system/mp/info?app_id=${item->app_id!}" target="_blank">查看详细</a>
                        <a href="/system/mp/operate/log/list?app_id=${item->app_id!}" target="_blank">版本操作日志</a>
                    </td>
                </tr>

            </#list>
        </table>
        <table width="" border="0" class="table tb_paging">
            <tr>
                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                    <a href="##" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>
                    <a href="##"
                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                    <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                           size="5"
                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                </td>
            </tr>
        </table>
    </div>
</form>

<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    $('.form-query select.form-control').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    $('[name="search"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
</script>

<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>