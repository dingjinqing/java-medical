<#include ("system.header")
<link href="/css/system/shop_list.css" rel="stylesheet" type="text/css"/>
<link href="/css/system/table_list.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .table>thead>tr>th{
        width: 7%;
    }
    .shop_number:after{
        content: '';
        display: block;
        clear: both;
    }
    .shop_number a{
        display: inline-block;
    }
    .shop_number a:first-of-type{
        width: 60%;
        text-align: right;
        float: left;
    }
    .shop_number a:last-of-type{
        width: 30%;
        text-align: left;
        float: right;
        height:18px;
    }
    .shop_number a:last-of-type img{
        height:12px;
        margin-top: 2px;
    }
    .user-name {
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90, 139, 255, 1) !important;
    }
    .show_more{
        cursor: pointer;
    }
    .take_up{
        cursor: pointer;
    }
</style>

<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/account/list">商家账号列表</a>
    </li>
    <li <#if ($nav_type==2)class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/account/add">商家账号添加</a>
    </li>
    <#if ($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="#">商家账号编辑</a></li>
    </#if>
</ul>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>


<form action="/system/shop/account/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="page" value="">
    <input type="hidden" name="sys_id" id="sys_id" value="">
    <input type="hidden" name="sum_sys_id" id="sum_sys_id" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
            <select name="state">
                <option value="0" selected>选择审核状态</option>
                <option value="1" <#if ($request['state']==1) selected </#if>>申请中</option>
                <option value="2" <#if ($request['state']==2) selected </#if>>审核通过</option>
                <option value="3" <#if ($request['state']==3) selected </#if>>审核不通过</option>
                <option value="4" <#if ($request['state']==4) selected </#if>>已禁用</option>
            </select>
            {{--<select name="shop_grade" hidden>--!}
                {{--<option value="0">选择店铺等级</option>--!}
                {{--<option value="1" <#if ($request['shop_grade']==1) selected </#if>>普通店</option>--!}
                {{--<option value="2" <#if ($request['shop_grade']==2) selected </#if>>专营店</option>--!}
                {{--<option value="3" <#if ($request['shop_grade']==3) selected </#if>>精品店</option>--!}
                {{--<option value="4" <#if ($request['shop_grade']==4) selected </#if>>旗舰店</option>--!}
            {{--</select>--!}
            {{--<select name="business_state">--!}
                {{--<option value="-1">选择营业状态</option>--!}
                {{--<option value="0" <#if ($business_state==0) selected </#if>>未营业</option>--!}
                {{--<option value="1" <#if ($business_state==1) selected </#if>>营业</option>--!}
            {{--</select>--!}
            <input type="text" name="keywords" placeholder="请输入用户名、昵称" value="${request['keywords']!}">
            <input type="text" name="company" placeholder="请输入公司名称" value="${request['company']!}">
            <input type="submit" class="search" name="search" value="{{ trans("system/common.operation.search")!}">
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>昵称</th>
                    <th>公司名称</th>
                    <th>审核状态</th>
                    <th>店铺等级</th>
                    {{--<th>最大店铺数</th>--!}
                    <th>店铺总数</th>
                    <th>添加时间</th>
                    <th>首次续费</th>
                    <th>到期时间</th>
                    <th>续费总额</th>
                    <th>手机号</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list ($data_list as $item)
                    <tr style="text-align:center;">
                        <td class="user-name">${item->user_name!}</td>
                        <td>${item->account_name!}</td>
                        <td>${item->company!}</td>
                        {{--<td class="principal_name">--!}
                            {{--<#if ($item->principal_name)--!}
                                {{--<#list ($item->principal_name as $k=>$val)--!}
                                    {{--<span class="show_principal" style="display: block;margin-bottom: 10px">(${val->principal_name ?? '个人'!})</span>--!}
                                {{--</#list>--!}
                            {{--</#if>--!}
                            {{--<span class="show_more hide" style="color: #5A8BFF"> 显示更多</span>--!}
                            {{--<span class="take_up hide" style="color: #5A8BFF"> 收起</span>--!}
                        {{--</td>--!}
                        <td><#if ($item->state == 2)申请通过<#elseif>($item->state == 3)审核不通过<#elseif>($item->state == 4)已禁用<#else>申请中</#if></td>
                        {{--<td><#if ($item->business_state == 1)营业<#else> 未营业 </#if></td>--!}
                        <td><#if ($item->shop_grade == 4)旗舰店<#elseif>($item->shop_grade == 3)精品店<#elseif>($item->shop_grade == 2)专营店<#else>普通店</#if></td>
                        {{--<td align="right">${item->max_sku_num!}</td>--!}
{{--                        <td align="right">${item->max_shop_num!}</td>--!}
                        <td class="shop_number">
                            <a href="/system/shop/list?sys_id=${item->sys_id!}">${item->shop_number!}</a>
                            <a href="/system/shop/add?sys_id=${item->sys_id!}">
                                <img src="http://${image_domain!}/image/admin/add_some.png" title="添加店铺" alt="添加店铺">
                            </a>&nbsp;
                        </td>
                        <td>${item->add_time!}</td>
                        <td>${item->buy_time!}</td>
                        <td>${item->end_time!}</td>
                        <td align="right">${item->renew_money!}</td>
                        <td><#if ($item->mobile == '')未设置<#elseif>($item->mobile != "")${item->mobile!}</#if></td>
                        <td>
                            <a href="/system/shop/account/edit?sys_id=${item->sys_id!}">编辑</a>
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

    $('[name="state"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    $('[name="shop_grade"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    $('[name="business_state"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });

    function remove(id) {
        if (confirm("确定删除此行数据吗？")) {
            $("#act").val('del');
            $("#sys_id").val(id);
            $("#form1").submit();
        }
    }

    //$("#main-table").FixedHead({tableLayout: "fixed"});
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数

    $('.principal_name').each(function (i,v) {
        if($('.principal_name').eq(i).find('.show_principal').length > 1){
            $('.principal_name').eq(i).find('.show_principal').each(function (j,k) {
                $('.principal_name').eq(i).find('.show_principal').eq(j).addClass('hide');
            })
            $('.principal_name').eq(i).find('.show_principal').eq(0).removeClass('hide');
            $('.principal_name').eq(i).find('.show_more').removeClass('hide');
        }
    })
    $('.show_more').click(function () {
        $(this).parent().find('.show_principal').removeClass('hide');
        $(this).addClass('hide');
        $(this).parent().find('.take_up').removeClass('hide');
    })
    $('.take_up').click(function () {
        $(this).addClass('hide');
        $(this).parent().find('.show_more').removeClass('hide');
        $(this).parent().find('.show_principal').each(function (i,v) {
            $(v).addClass('hide');
        })
        $(this).parent().find('.show_principal').eq(0).removeClass('hide');
    })
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>

<#include ("system.footer")