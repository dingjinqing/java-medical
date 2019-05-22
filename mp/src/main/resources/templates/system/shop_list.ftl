<#include "/system/header.ftl">

<link rel="stylesheet" href="/css/system/layui/css/layui.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/layui_change.css?v=1.0.0" type="text/css"/>
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script language="JavaScript" src="/js/system/lang/zh-CN/util.js"></script>
<script language="JavaScript" src="/js/system/util.js?v=1.0.9"></script>
<style type="text/css">
    .renew{
        width: 25%;
        //height:250px;
        position: fixed;
        top:5%;
        left:40%;
        z-index: 9999;
        border:1px solid #eee;
        background: #ffffff;
        display: none;
    }
    .renew label{
        margin-left: 4%;
    }
    .renew input[type='text']{
        width: 55%;
    }
    .renew input[type='date']{
        width: 55%;
    }
    .re_title{
        width: 100%;
        border-bottom: 1px solid #ccc;
        padding:15px 0;
        text-align: center;
        margin-bottom: 20px;
    }
    .btn_conf{
        background: #86a7cb;
        color: #fff;
        margin-left: 20%;
        margin-right: 3%;
    }
    #new_time{
        height:23px;
    }
    .shop_message{
        margin-left:4%;
        margin-bottom: 15px;
    }
    .table>thead>tr>th{
        width: 7%;
    }
    .mask{
        width: 100%;
        height: 100%;
        background: #000;
        opacity:0.5;
        position: absolute;
        top:0;
        left:0;
        z-index: 9999;
        display: none;
    }
    .form-control{
        width: 160px;
        display: inline-block;
        margin-left: 20px;
    }
    .search{
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
        border: 1px solid #ccc;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 22px;
        border-radius: 2px;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        display: inline-block;
        width: 60px;
    }
</style>
<ul id="tab" class="nav nav-tabs">

    <li <#if (nav_type==0)>class="active"</#if>><a href="#" data-toggle="tab"
                                                 onclick="location.href = '/system/shop/list';">店铺列表</a></li>
    <#if (nav_type==2)>
        <li class="active"><a href="#" data-toggle="tab" url="#">店铺添加</a></li>
    </#if>
    <#if (nav_type==3)>
        <li class="active"><a href="#" data-toggle="tab" url="#">店铺编辑</a></li>
    </#if>
</ul>

<form action="/system/shop/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="shop_id" id="shop_id" value="">
    <input type="hidden" name="re_sys_id" id="re_sys_id" value="">
    <input type="hidden" id="re_money" name="re_money" value="">
    <input type="hidden" id="re_time" name="re_time" value="">
    <input type="hidden" id="re_desc" name="re_desc" value="">
    <input type="hidden" id="shop_mol" name="shop_mol" value="">
    <input type="hidden" id="re_shop_name" name="re_shop_name" value="">
    <div class="box panel ">
        <div class="panel-body">
            <input type="text" name="account_key" value="${inputMap['account_key']!}" size="6" placeholder="账号ID、公司" style="width: 220px">
            <span class="text-info" style="margin-right: 20px">${account.user_name!}</span>
            <input type="text" style="width: 250px" name="keywords" placeholder="店铺ID、店铺名称、手机号、小程序名称" value="${inputMap['keywords']!}">
            <select name="is_use" id="" class="form-control">
                <option value="-1" selected>选择店铺状态</option>
                <option value="1" <#if ("${inputMap['is_use']!}" == "1")> selected </#if>>使用中</option>
                <option value="2" <#if ("${inputMap['is_use']!}" == "2")> selected </#if>>已过期</option>
            </select>
            <select name="shop_type" id="" class="form-control">
                <option value="-1" selected>选择店铺类型</option>
                <#list version_list as item>
                    <option value="${item.level}" <#if ("${inputMap['shop_type']!}" == "${item.level}")> selected </#if>>${item.version_name}</option>
                </#list>
            </select>
            <select name="shop_flag" id="" class="form-control">
                <option value="" selected>选择店铺标识</option>
                <#list shop_flag_list as flag >
                <option value="${flag?index}" <#if ("${inputMap['shop_flag']!}" == "flag?index" && "${inputMap['shop_flag']!}" !="")> selected </#if>>${flag}</option>
                </#list>
            </select>

        </div>
        <div class="panel-body">
            <select name="is_enabled" id="" class="form-control" style="margin-left: 0px">
                <option value="" selected>选择禁用状态</option>
                <option value="1" <#if ("${inputMap['is_enabled']!}" == "1")> selected </#if>>已禁用</option>
                <option value="0" <#if ("${inputMap['is_enabled']!}" == "0" && "${inputMap['is_enabled']!}" !="")> selected </#if>>未禁用</option>
            </select>
            <select name="hid_bottom" id="" class="form-control" >
                <option value="" selected>底部导航</option>
                <option value="1" <#if ("${inputMap['hid_bottom']!}" == "1")> selected </#if>>隐藏</option>
                <option value="0" <#if ("${inputMap['hid_bottom']!}"== "0" && "${inputMap['hid_bottom']!}" != "" )> selected </#if>>显示</option>
            </select>
            <div class="search" >搜索</div>
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>账号ID</th>
                    <th>店铺ID<br>(店铺类型)</th>
                    <th>店铺名称</th>
                    <th>小程序名称<br>(公司)</th>
                    <th>手机号</th>
                    <th>创建时间</th>
                    <th>到期时间<br>(店铺状态)</th>
                    <th>是否禁用</th>
                    <th>小程序授权</th>
                    <th>所属账号</th>
                    <th>续费总金额</th>
                    <th>店铺标识</th>
                    <th>底部导航</th>
                    <th>操作</th>
                </tr>
                </thead>

                <#list data_list as item>
                    <tr style="text-align:center;">
                        <td >${item.sys_id}<br/><font color="#d2d2d2">${item.account_info.company}</font> </td>
                        <td class="shop_ids">${item.shop_id}<br>(${version_array[item.shop_type]})</td>
                        <td class="shop_names">${item.shop_name}</td>
                        <td class="shop_names"><#if (item.nick_name??)>${item.nick_name}<br/><font color="#d2d2d2">(${item.principal_name})</font> </#if></td>
                        <td>${item.mobile}</td>
                        <td>${item.created}</td>
                        <td class="expire_time" expire_time="${item.expire_time}"><#if ("${item.expire_time!}" != "")>${item.expire_time}</#if>
                            <#if ("${item.expire_time!}" == "")>暂未续费</#if>
                            <br><#if "${item.use!}" == "1">(使用中)<#elseif ("${item.use!}" == "2")>(已过期)</#if></td>
{{--                        <td><#if item.is_enabled == 1>是<#else>否</#if></td>--}
                        <td><a href="#" class="is_enabled" shop_id="${item.shop_id}" style="color: #86a7cb;"><#if item.is_enabled == 1>已禁止<#else>未禁止</#if></a></td>
                        <td>
                        <#if item.app_id??>
                        <#if item.is_auth_ok == 1>已授权<#else>已取消授权</#if>
                        <#else>
                        未绑定授权
                        </#if></td>
                        <td><a href="/system/shop/account/list?user_name=${item.account_info.user_name}" style="color: #86a7cb;">${item.account_info.user_name}</a>
                        </td>
                        <td class="renew_mon">
                            <#if (item.renew_money != 0)>${item.renew_money}</#if>
                            <#if (item.renew_money == 0)>暂未续费</#if>
                        </td>
                        <td>
                            <#if (item.shop_flag??)>${shop_flag_list[item.shop_flag]}<#else>店+</#if></td>
                        <td><a href="#" class="hid_bottom" shop_id="${item.shop_id}" style="color: #86a7cb;"> <#if item.hid_bottom == 1>隐藏<#else>显示</#if></a></td>
                        <td>
                            <#if ( item.app_id??)>
                                <a href="/system/mp/info?app_id=${item.app_id}">查看小程序授权信息</a>&nbsp;<br/>
                            </#if>
                            <a href="/system/shop/edit?shop_id=${item.shop_id}">编辑</a>&nbsp;&nbsp;<br/>
                            <a href="#" class="btn_renew" shop_id="${item.shop_id}" sys_id="${item.sys_id}"  shop_name="${item.shop_name} " shop_mol="${item.mobile}" >续费</a><br/>
                            <a href="/system/version/show?shop_id=${item.shop_id}">版本权限</a><br/>
                            <a href="/system/shop/renew?shop_id=${item.shop_id}" class="btn_detail">查看续费</a><br>
                                <a href="/system/goods/analysis?shop_id=${item.shop_id}">运营数据</a>

                        </td>
                    </tr>

                </#list>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">${page.pageInfo}
                                    <a href="#"
                                       onClick="return gopage(1);">第一页</a>
                                    <a href="#"
                                       onClick="return gopage(${page.prePage});">上一页</a>
                                    <a href="#"
                                       onClick="return gopage( ${page.nextPage});">下一页</a>
                                    <a href="#"
                                       onClick="return gopage(${page.lastPage});">最后一页</a>
                                    <input id="page" name="page" type="text"
                                           value="${page.currentPage}" size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">页
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            
        </div>
    </div>


</form>

<div class="mask"></div>
<div class="renew">
    <div class="re_title">店铺续费窗口</div>
    <div class="shop_message">店铺名称：<span></span></div>
    <label for="new_money">续费金额：</label>
    <input type="text" id="new_money" name="new_money" placeholder="请输入要续费的金额" value=""><br><br>
    <label for="last_time" style="margin-left: 4%">上次续费到期时间：</label>
    <span id="last_time" name="last_time" value=""></span><br><br>
    <label for="new_time" style="margin-left: 4%">到期时间：</label>
    <input type="date" id="new_time" name="new_time" placeholder="请选择服务到期的日期" value=""><br><br>
    <label for="new_time" style="margin-left: 4%">续费说明：</label>
    <input type="text" id="new_desc" name="new_desc" placeholder="请输入说明" value=""><br><br>
    <input type="button" value="确定" class="btn_conf">
    <input type="button" value="取消" class="btn_can">
</div>

<script>
var page_home = ${page.currentPage};
var page_all = ${page.lastPage};
</script>

<#noparse>
<script>
    function gopage(page) {
        $("#page").val(page);
        $("#form1").submit();
    }
    $(".search").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    })
    // $("#main-table").FixedHead({tableLayout: "fixed"});

    $("#shop_id").val($(".btn_renew").attr('shop_id'));
    $("#re_sys_id").val($(".btn_renew").attr('sys_id'));

    $(".btn_renew").click(function () {
        var that = $(this);
        $(".renew").css("display","block");
        $(".mask").css("display","block");
        var shop_name = that.parent().parent().find('.shop_names').html();
        var expire_time = that.parent().parent().find('.expire_time').attr('expire_time');
        var shop_ids = that.parent().parent().find('.shop_ids').html();
        $(".shop_message span").html(shop_name+"("+shop_ids+")");
        $("#last_time").html(expire_time);

        $(".btn_can").click(function () {
            if($(".renew").css("display") == "block"){
                $(".renew").css("display","none");
                $(".mask").css("display","none");
                $("#re_time").val("");
                $("#re_money").val("");
            }
        })
        $(".btn_conf").click(function () {
            var renew_money = $("#new_money").val();
            var renew_time = $("#new_time").val();
            var new_desc = $("#new_desc").val();
            if(renew_money == ""){
                alert("金额不能为空");
                $("#new_money").focus();
                return false;
            }
            if(renew_time == ""){
                alert("日期不能为空");
                $("#new_time").focus();
                return false;
            }else if(renew_time < expire_time){
                alert('本次续费到期日期不可小于上次续费到期日期');
                $("#new_time").focus();
                return false;
            }
            if(new_desc == ""){
                alert("说明不能为空");
                $("#new_desc").focus();
                return false;
            }

            if(renew_time != "" && renew_money != ""){
                $(".renew").css("display","none");
                $(".mask").css("display","none");
                $("#act").val('renew');
                $("#shop_id").val(that.attr('shop_id'));
                $("#re_sys_id").val(that.attr('sys_id'));
                $("#re_money").val($("#new_money").val());
                $("#re_time").val($("#new_time").val());
                $("#re_desc").val($("#new_desc").val());
                $("#shop_mol").val(that.attr('shop_mol'));
                $("#re_shop_name").val(that.attr('shop_name'));
                $("#form1").submit();
            }
        })
        $(".mask").click(function () {
            $(".renew").css("display","none");
            $(".mask").css("display","none");
            $("#re_time").val("");
            $("#re_money").val("");
        })
    })
    $(".is_enabled").click(function () {
        var _this = $(this);
        if(_this.html()=='未禁止'){
            var is_enabled = 1;
            var text = '确认要禁用么';
        }else{
            var is_enabled = 0;
            var text = '确认要启用么'
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提示', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '300px'
                , content: '<div style=\"text-align: center;margin-top: 10px\">' + text + '</div>' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确认', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调

                    var data = {
                        'act':'is_enabled',
                        'is_enabled':is_enabled,
                        'shop_id':_this.attr('shop_id')
                    };
                    util.ajax_json('/system/ajax/action', function (d) {
                        if (d && d.error == 0) {
                            util.mobile_alert(d.content);
                            layer.close(index);
                            location.reload();
                        }else {
                            util.mobile_alert(d.message);
                        }
                    }, data);
                    //layer.close(index);   //开启则可以关闭弹框
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    $(".hid_bottom").click(function () {
        var _this = $(this);
        if(_this.html()=='显示'){
            var hid_bottom = 1;
            var text = '确认要隐藏么';
        }else{
            var hid_bottom = 0;
            var text = '确认要显示么'
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提示', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '300px'
                , content: '<div style=\"text-align: center;margin-top: 10px\">' + text + '</div>' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确认', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调

                    var data = {
                        'act':'hid_bottom',
                        'hid_bottom':hid_bottom,
                        'shop_id':_this.attr('shop_id')
                    };
                    util.ajax_json('/system/ajax/action', function (d) {
                        if (d && d.error == 0) {
                            util.mobile_alert(d.content);
                            layer.close(index);
                            location.reload();
                        }else {
                            util.mobile_alert(d.message);
                        }
                    }, data);
                    //layer.close(index);   //开启则可以关闭弹框
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    })

</script>
</#noparse>
<#include "/system/footer.ftl">

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
