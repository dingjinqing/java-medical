<#include ("system.header")
<link rel="stylesheet" href="/css/system/layui/css/layui.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/layui_change.css?v=1.0.0" type="text/css"/>
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script language="JavaScript" src="/js/system/lang/{{ config("app.locale")!}/util.js"></script>
<script language="JavaScript" src="/js/system/util.js?v=1.0.9"></script>
<style type="text/css">
    .renew label{
        margin-left: 4%;
    }
    .renew input[type='text']{
        width: 55%;
    }
    .renew input[type='date']{
        width: 55%;
    }
    .table>thead>tr>th{
        width: 7%;
    }
    .form-control{
        width: 160px;
        display: inline-block;
        margin-left: 16px;
    }
    .panel-body .line_search input{
        width: 160px;
        display: inline-block;
        margin-left: 20px;
        height: 32px;
        padding: 6px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        color: #555;
        background-color: #fff;
        background-image: none;
        border: 1px solid #ccc;
    }
    .line_search{
        margin-bottom:20px;
    }
    .btn_search{
        cursor: pointer;
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        border-radius: 2px;
        width: 60px;
        margin-left: 10px;
    }
    .btn_exel{
        cursor: pointer;
        color: black;
        background-color: white;
        border-color: #c1c1c1;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        border-radius: 2px;
        width: 80px;
        margin-left: 10px;
    }
    .all_checked{
        margin-left: 10px;
    }
    .all_checked div{
        margin-left: 20px;
        cursor: pointer;
        color: #5A8BFF;
        font-weight: 400;
        text-align: center;
        background-color: white;
        border:1px solid #5A8BFF;
        width: 80px;
        height: 25px;
        border-radius: 2px;
        display:inline-block;
    }
    .panel-body select.form-control {
    width: 160px;
    display: inline-block;
    margin-left: 16px;
    }
</style>
<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab"
                                                 onclick="location.href = '/system/chargerenew/list';">升级续费列表</a></li>
</ul>

<form action="/system/chargerenew/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="page" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            <div class="line_search">
            店铺名称：<input type="text" name="shop_name" value="${request['shop_name']!}">
            所属账号：<input type="text" name="sys_name" value="${request['sys_name']!}">
            申请升级或续费账号：<input type="text" name="apply_name" value="${request['apply_name']!}">
            是否联系：<select name="contact" id="" class="form-control">
                <option value="-1" selected>全部</option>
                <option value="0" <#if (!is_null($request['contact']) && $request['contact'] == 0) selected </#if>>未联系</option>
                <option value="1" <#if ($request['contact'] == 1) selected </#if>>已联系</option>
            </select>
            </div>
            <div class="line_search">
            店铺类型：
            <select name="shop_type" id="" class="form-control">
                <option value="" selected>选择店铺类型</option>
                <#list ($version_list as $item)
                    <option value="${item->level!}" <#if ($request["shop_type"] == $item->level) selected </#if>>${item->version_name!}</option>
                </#list>
            </select>
            申请时间：<input type="text" name="start_time"  onclick="picker()"  value="${request['start_time']!}" id="actstartDate"
                        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"> -
                <input type="text" name="end_time"  onclick="picker()"  value="${request['end_time']!}" id="actstartDate"
                                                                                                                                                                        onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off">
            申请类型：<select name="apply_type" id="" class="form-control">
                <option value="-1" selected>全部</option>
                <option value="0" <#if (!is_null($request['apply_type']) && $request['apply_type'] == 0) selected </#if>>升级申请</option>
                <option value="1" <#if ($request['apply_type'] == 1) selected </#if>>续费申请</option>
            </select>
                <button  class="btn_search" type="button">{{ trans("system/common.operation.search")!}</button>
                <button  class="btn_exel" type="button">导出表格</button>
            </div>
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>店铺名称<br>（ID）</th>
                    {{--<th>店铺名称</th>--!}
                    <th>手机号</th>
                    <th>创建时间</th>
                    <th>店铺状态</th>
                    <th>店铺类型</th>
                    <th>所属账号</th>
                    <th><#if (!is_null($request['apply_type']) && $request['apply_type'] == 0) 申请升级账号 <#elseif>($request['apply_type'] == 1)申请续费账号 <#else> 申请升级或续费账号 </#if></th>
                    <th>申请账号类型</th>
                    <th>申请时间</th>
                    <th>申请类型</th>
                    <th>来源功能模块</th>
                    <th>备注</th>
                    <th width="210px">操作</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        <td class="shop_ids"><input type="checkbox" ids="${item->id!}" style="margin-right: 5px"><a href="/system/shop/list?keywords=${item->shop_id!}" style="color: #5a8bff;">${item->shop_name!}</a><br>${item->shop_id!}</td>
                        {{--<td class="shop_names"><a href="/system/shop/list?keywords=${item->shop_id!}">${item->shop_name!}</a></td>--!}
                        <td>${item->mobile!}</td>
                        <td>${item->shop_created!}</td>
                        <td class="expire_time" expire_time="${item->expire_time!}"><#if ($item->expire_time > date('Y-m-d H:i:s',time()))使用中<#elseif>($item->expire_time < date('Y-m-d H:i:s',time()))已过期</#if></td>
{{--                        <td><#if ($item->shop_type == 0)高级版<#elseif>($item->shop_type == 1)体验版<#elseif>($item->shop_type == 2)基础版<#elseif>($item->shop_type == 3)旗舰版</#if></td>--!}
                        <td>${version_array[$item->shop_type]!}</td>
                        <td><a href="/system/shop/account/edit?sys_id=${item->sys_id!}">${item->user_name!}</a>
                        <td><a href="/system/shop/account/edit?sys_id=${item->apply_id!}">${item->apply_name!}</a>
                        <td>${item->apply_id == $item->sys_id?"主账号":"子账号"!}</td>
                        <td>${item->created!}</td>
                        <td>${item->apply_type==1?"续费":"升级"!}</td>
                        <td>${item->apply_mod!}</td>
                        <td>
                           <span class="half"  style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">${item->desc!}</span>
                            <span class="all" hidden>${item->desc!}</span>
                            <a href="javascript:void(0)" class="show_more" style="color: #5a8bff;" <#if (!strlen($item->desc)) hidden </#if>>展开</a>
                            <a href="javascript:void(0)" class="show_little"  style="color: #5a8bff;" hidden>收起</a>
                        </td>
                        <td>
                            <a href="#" ids="${item->id!}" class="btn_contact" <#if ($item->contact == 0) style="color:#5a8bff" </#if>><#if ($item->contact == 0) 确认联系 <#else> 已联系 </#if></a>
                            <br>
                            <a href="#" ids="${item->id!}" class="btn_desc" style="color:#5a8bff" desc="${item->desc!}">备注</a>
                        </td>
                    </tr>
                </#list>
            </table>
            <div>
                <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                    <tr>
                        <td>
                            <div class="all_checked fl">
                                <input type="checkbox">
                                <div class="all_contact" style="line-height:25px;">确认联系</div>
                            </div>
                        </td>
                        <td align="right">
                            <table width="100%" border="0" class="tb_paging">
                                <tr>
                                    <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                        <a href="#"
                                           onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">
                                            {{ trans("system/common.page.next_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                        <input id="page" name="page" type="text"
                                               value="${data_list->currentPage()!}" size="5"
                                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</form>

<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    $("#main-table").FixedHead({tableLayout: "fixed"});

    $("#shop_id").val($(".btn_renew").attr('shop_id'));
    $("#re_sys_id").val($(".btn_renew").attr('sys_id'));

    $(".btn_exel").click(function () {
        $("#act").val('exel');
        $("#form1").submit();
    })
    $(".btn_search").click(function () {
       $("#act").val('');
       $('#form1').submit();
    })
    function picker(){
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $(".btn_contact").click(function () {
        var _this = $(this);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提示', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '300px'
                , content: '<div style=\"text-align: center;margin-top: 10px\">' + '确认联系么' + '</div>' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确认', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var data = {
                        'ids':[_this.attr("ids")],
                    };
                    util.ajax_json('/system/apply/contact', function (d) {
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
    $(".all_checked input").click(function () {
        var isChecked = $(".all_checked input").prop("checked");
        if($(this).is(':checked')) {
            $(this).parents('.layer').find('.table').find("input[type='checkbox']").each(function(){
                if(!$(this).is(':checked'))
                    $(this).click();
            })
        }else{
            $(this).prev().attr('src','/image/admin/square_no.png');
            $(this).parents('.layer').find('.table').find("input[type='checkbox']").each(function(){
                if($(this).is(':checked'))
                    $(this).click();
            })
        }
    });
    $(".all_checked .all_contact").click(function () {
        var _this = $(this);
        var ids = [];
        $('.layer .table').find("input[type='checkbox']:checked").each(function () {
            ids.push($(this).attr('ids'));
        });
        if (ids.length == 0) {
            util.mobile_alert('未选择任何数据');
            return false;
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提示', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '300px'
                , content: '<div style=\"text-align: center;margin-top: 10px\">' + '确认联系么？' + '</div>' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确认', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var data = {
                        'ids':ids
                    };
                    util.ajax_json('/system/apply/contact', function (d) {
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
    $(".btn_desc").click(function () {
        var _this = $(this);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提示', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '300px'
                , content: '<div style="text-align: center;margin-top: 10px"><textarea name="" id="desc" cols="30" rows="5">'+_this.attr("desc")+'</textarea></div>' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确认', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var data = {
                        'ids':[_this.attr("ids")],
                        'desc':$('#desc').val()
                    };
                    util.ajax_json('/system/apply/desc', function (d) {
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
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数

    $(".show_more").click(function () {
        $(this).hide();
        $(this).parent().find(".half").hide();
        $(this).parent().find(".all").show();
        $(this).parent().find(".show_little").show();
    })
    $(".show_little").click(function () {
        $(this).hide();
        $(this).parent().find(".half").show();
        $(this).parent().find(".all").hide();
        $(this).parent().find(".show_more").show();
    })
</script>
<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>