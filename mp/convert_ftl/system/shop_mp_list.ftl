<#include ("system.header")
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
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
</style>
<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab"
                                                 onclick="location.href = '/system/shop/list';">店铺列表</a></li>
    <#if ($nav_type==2)
        <li class="active"><a href="#" data-toggle="tab" url="#">店铺添加</a></li>
    </#if>
    <#if ($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="#">店铺编辑</a></li>
    </#if>
</ul>

<form action="/system/shop/mp/list" name="form1" id="form1" method="post">
    <input type="hidden" name="page" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">

            <input type="text" style="width: 17%" name="keywords" placeholder="请输入店铺ID、店铺名称" value="${keywords!}">

            <select name="shop_type" id="" class="form-control">
                <option value="-1" selected>选择店铺类型</option>
                {{--<option value="0" <#if (!is_null($shop_type) && $shop_type == 0) selected </#if>>通用版</option>--!}
                {{--<option value="1" <#if ($shop_type == 1) selected </#if>>试用版</option>--!}
                <#list ($version_list as $item)
                    <option value="${item->id!}" <#if ($shop_type == $item->id) selected </#if>>${item->version_name!}</option>
                </#list>
            </select>
            <select name="open_pay" id="" class="form-control">
                <option value="-1" selected>选择支付类型</option>
                <option value="0" <#if (!is_null($open_pay) && $open_pay == 0) selected </#if>>未开通</option>
                <option value="1" <#if ($open_pay == 1) selected </#if>>已开通</option>
            </select>
            <select name="is_enabled" id="" class="form-control">
                <option value="-1" selected>选择禁用类型</option>
                <option value="0" <#if (!is_null($is_enabled) && $is_enabled == 0) selected </#if>>未禁用</option>
                <option value="1" <#if ($is_enabled == 1) selected </#if>>已禁用</option>
            </select>
            <input type="submit" name="search" value="{{ trans("system/common.operation.search")!}">
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>店铺ID</th>
                    <th>小程序名称</th>
                    <th>公司名称</th>
                    <th>创建时间</th>
                    <th>首发时间</th>
                    <th>现发时间</th>
                    <th>支付</th>
                    <th>店铺类型</th>
                    <th>店铺状态</th>
                    <th>是否禁用</th>
                    <th>续费总金额</th>
                    <th>到期时间</th>
                    <th>查看</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        <td class="shop_ids">${item->shop_id!}</td>
                        <td class="shop_names">${item->nick_name!}</td>
                        <td >${item->principal_name!}</td>
                        <td>${item->start_time!}</td>
                        <td>${item->create_time!}
                            (<span title="${item->template_id!}">${item->user_version!}</span>)
                        </td>
                        <td>${item->last_upload_time!}
                            (<span title="${item->bind_template_id!} ">${item->bind_user_version!}</span>)
                        </td>
                        <td><#if ($item->open_pay == 0)无支付<#elseif>($item->open_pay == 1)有支付</#if></td>
                        {{--<td><#if ($item->shop_type == 0)高级版<#elseif>($item->shop_type == 1)体验版<#elseif>($item->shop_type == 2)基础版<#elseif>($item->shop_type == 3)旗舰版</#if></td>--!}
                        <td>${version_array[$item->shop_type]!}</td>
                        <td><#if ($item->expire_time > date('Y-m-d H:i:s',time()))使用中<#elseif>($item->expire_time < date('Y-m-d H:i:s',time()))已过期</#if></td>
                        <td>${item->is_enabled==1?"是":"否"!}</td>
                        </td>
                        <td class="renew_mon">
                            <#if ($item->renew_money != 0)${item->renew_money!}</#if>
                            <#if ($item->renew_money == 0)暂未续费</#if>
                        </td>
                        <td class="renew_tim">
                            <#if ($item->expire_time != "")${item->expire_time!}</#if>
                            <#if ($item->expire_time == "")暂未续费</#if>
                        </td>
                        <td>
                            <a href="/system/mp/info?app_id=${item->app_id!}" class="btn_detail">查看</a>
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
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
</script>
<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>