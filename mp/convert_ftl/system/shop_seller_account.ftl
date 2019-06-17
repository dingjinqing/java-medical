<#include ("system.header")
<script src="/js/layui/layui.js" type="text/javascript"></script>
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<style type="text/css">
    .tb-decorate-list>tbody>tr>td{
        height:50px;
    }
    .btn_add_group a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_add_group a:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .order-info-li .fl{
        width: 300px;
        margin-top: 15px;
    }
    .order-info-li .fl span {
        width: 100px;
    }
    .f1 input{
        width:auto;
    }
    .f2{
        width: 300px;
        margin-top: 10px;
    }
    .f2 span{
        width: 100px;
        display: inline-block;
    }
    .f2 input, .f2 select{
        width: 150px;
        height: 30px;
        padding-left: 12px;
        border: 1px solid #ccc;
    }
    .member_list_main table tr{
        height: 32px;
    }
    .layui-layer-content {
        text-align: center;
    }
</style>
<ul id="tab" class="nav nav-tabs">
    <li class="active">
        <a href="#" data-toggle="tab" onclick="location.href = '/system/seller/account/list';">卖家列表</a>
    </li>
</ul>
<div style="min-width: 1090px;">
    <div class="order-container">
        <form action="" method="post" id="form1">
        <div class="order-info">
                <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>店铺名称 / ID</span>
                            <input type="text" name="keywords" value="${request['keywords']!}" placeholder='店铺名称|店铺ID' />
                        </div>
                        <div class="fl">
                            <span>卖家账号</span>
                            <input type="text" name="seller_account" value="${request['seller_account']!}" placeholder='卖家账号' />
                        </div>
                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>ERP类型</span>
                            <select name="seller_account_action">
                                <option value="-1">选择ERP类型</option>
                                <option value="0" <#if  (isset($request['seller_account_action']) && $request['seller_account_action'] == 0) selected </#if>>E快帮</option>
                                <option value="1" <#if  ($request['seller_account_action'] == 1) selected </#if>>旺店通标准版</option>
                                <option value="2" <#if  ($request['seller_account_action'] == 2) selected </#if>>旺店通企业版</option>
                                <option value="3" <#if  ($request['seller_account_action'] == 3) selected </#if>>旺店通旗舰版</option>
                                <option value="4" <#if  ($request['seller_account_action'] == 4) selected </#if>>短信用户</option>
                                <option value="5" <#if  ($request['seller_account_action'] == 5) selected </#if>>o2o用户</option>
                            </select>
                        </div>
                        <div class="fl">
                            <span>账号状态</span>
                            <select name="seller_account_status">
                                <option value="-1">请选择</option>
                                <option value="0" <#if  (isset($request['seller_account_status']) && $request['seller_account_status'] == 0) selected </#if>>禁用</option>
                                <option value="1" <#if  ($request['seller_account_status'] == 1) selected </#if>>启用</option>
                            </select>
                        </div>
                        <div>
                            <span style="margin-left: 30px;">已绑定账号</span>
                            <span style="margin-left: 25px;">
                                <input type="checkbox" name="is_bind" value="1" style="width: 15px; height: 15px;" <#if  ($request['is_bind'] == 1) checked </#if>/>
                                <input type="button" name="search" value="{{ trans("system/common.operation.search")!}" style="margin-top: 15px; width: 60px; margin-left: 70px; background-color: #6f97c2; color: #fff;"/>
                            </span>
                        </div>
                    </li>
                </ul>
        </div>
        <div class="member_list_main">
            <table width="100%">
                <thead>
                <tr style="height: 32px;">
                    <td>店铺名称</td>
                    <td>店铺id</td>
                    <td>绑定erp类型</td>
                    <td>卖家账号</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>
                <#list ($data_list as $item)
                    <tr shop_id = '${item->shop_id!}'>
                        <td>${item->shop_name!}</td>
                        <td>${item->shop_id!}</td>
                        <td>
                            <#if  (!empty($item->seller_account))
                                <#if  ($item->seller_account_action == 0)
                                    E快帮
                                <#elseif> ($item->seller_account_action == 1)
                                    旺店通标准版
                                <#elseif> ($item->seller_account_action == 2)
                                    旺店通企业版
                                <#elseif> ($item->seller_account_action == 3)
                                    旺店通旗舰版
                                <#elseif> ($item->seller_account_action == 4)
                                    短信用户
                                <#elseif> ($item->seller_account_action == 5)
                                    o2o用户
                                </#if>
                                <span class="seller-account-action hide">
                                            ${item->seller_account_action!}
                                        </span>
                            </#if>
                        </td>
                        <td class="seller-account">${item->seller_account!} </td>
                        <td>
                            <a href="javascript:void(0)" shop_id = '${item->shop_id!}' class="edit-seller-shop">编辑</a>
                            <#if  (!empty($item->seller_account))
                                <#if  ($item->seller_account_status == 0)
                                    <a href="javascript:void(0)" shop_id = '${item->shop_id!}' class="enable-seller-shop">- 启用</a>
                                <#else>
                                    <a href="javascript:void(0)" shop_id = '${item->shop_id!}' class="disable-seller-shop">- 禁用</a>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="member_list_footer">
            <table width="" border="0" class="tb_paging">
                <tr>
                    <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                        <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                        <a href="##"
                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                        <a href="##"
                           onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                        <a href="##"
                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                               size="5"
                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                    </td>
                </tr>
            </table>
        </div>
        </form>
    </div>
</div>


<div id="seller-account-template" class="hide">
    <div class="f2">
        <span>erp类型</span>
        <select name="seller_account_action">
            <option value="0">E快帮</option>
            <option value="1">旺店通标准版</option>
            <option value="2">旺店通企业版</option>
            <option value="3">旺店通旗舰版</option>
            <option value="4">短信用户</option>
            <option value="5">o2o用户</option>
        </select>
    </div>
    <div class="f2">
        <span>卖家账号</span>
        <input type="text" name="seller_account" value=""/>
    </div>
    <div class="f2">
        <span>小程序店铺ID</span>
        <input type="text" name="shop_id" value="" readonly/>
    </div>
</div>

<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(parseInt(page) > parseInt(last_page)){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    $('input[name="search"]').click(function () {
        $("#page").val(1);
        $("#form1").submit();
    })
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数

    layui.use('layer', function(){
        var layer = layui.layer;
    });
    $('.edit-seller-shop').on('click', function () {
        var that = $(this);
        var shop_id = $(this).attr('shop_id');
        var seller_account_action = parseInt($(this).parent().parent().find('.seller-account-action').text());
        var seller_account =  $(this).parent().parent().find('.seller-account').text();

        layer.open({
            type: 1,
            title: "店铺绑定卖家账号",
            content: $('#seller-account-template').html(),
            area: "500px",
            btn: ['确定', '取消'],
            success: function(layero, index){
                layero.find('select[name="seller_account_action"] option[value="'+seller_account_action+'"]').prop('selected', 'selected');
                layero.find('input[name="seller_account"]').val(seller_account);
                layero.find('input[name="shop_id"]').val(shop_id);
            },
            yes: function (index, layero) {
                if(layero.find('input[name="seller_account"]').val() == ''){
                    util.mobile_alert('卖家账号不能为空！');
                    return false;
                }
                if(layero.find('input[name="shop_id"]').val() == ''){
                    util.mobile_alert('shop_id不能为空！');
                    return false;
                }
                var param = {
                    seller_account_action : layero.find('select[name="seller_account_action"]').val(),
                    seller_account : layero.find('input[name="seller_account"]').val(),
                    shop_id : layero.find('input[name="shop_id"]').val(),
                };
                util.ajax_json("/system/seller/add/account", function (d) {
                    if (d && d.error == 0) {
                        layer.msg("保存成功",{
                            time: 2000
                        },function () {
                            location.reload();
                        });
                        layer.close(index);
                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                }, param);
            }
        });

    });
    $(".enable-seller-shop").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用该卖家账号吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/system/seller/account/list', function(response){
                    if(response && response.error == 0){
                        window.location.reload();
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"enable", shop_id:that.attr('shop_id')});
                layer.close(index);
            });
        });
    });
    $(".disable-seller-shop").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要禁用该卖家账号吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/system/seller/account/list', function(response){
                    if(response && response.error == 0){
                        window.location.reload();
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"disable", shop_id:that.attr('shop_id')});
                layer.close(index);
            });
        });
    });
</script>


<#include ("system.footer")
