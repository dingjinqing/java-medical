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
        width: 400px;
        margin-top: 15px;
    }
    .order-info-li .fl span {
        width: 125px;
    }
    .f1 input{
        width:auto;
    }
</style>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="order-info">
            <form action="/system/account/enteradmin/check" method="post" id="form1">
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>*账号名称/店铺ID：</span>
                            <input type="text" name="account" value="{{ session('_old_input')['account']!}" placeholder='账号' />
                        </div>
                        <div class="fl">
                            <span>*密码</span>
                            <input type="text" name="password" value="{{ session('_old_input')['password']!}" placeholder='密码' />
                        </div>
                        <input type="submit" value="Enter Admin" style="margin-top: 15px; width: 98px; margin-left: 70px;" />
                    </li>
                </ul>
            </form>
        </div>
        <div>
        </div>
    </div>
</div>
<script>
    var msg = '{{ session("message")!}';
    if (msg != '') {
        util.mobile_alert(msg);
    }
    $('input[type="submit"]').click(function (e) {
        e.preventDefault();
        var account = $('input[name="account"]').val();
        var password = $('input[name="password"]').val();
        layui.use('layer', function(){
            var layer = layui.layer;
            util.ajax_json('/system/account/enteradmin/check', function (response) {
                if (response.error == 0) {
                    top.location = response.content;
                } else {
                    layer.msg(response.message);
                    return false;
                }
            }, {account:account,password:password})
        });
    })
</script>
<#include ("system.footer")
