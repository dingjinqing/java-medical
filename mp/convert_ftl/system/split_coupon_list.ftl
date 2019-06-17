<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .btn_to_give a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_to_give a:focus{
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
    .return-goods-box .paging_footer{
        margin-top: 20px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/market_manage.market_manage_title")!} / </span>
        <span style="color: #666;">分裂优惠券</span>
    </div>
    <div class="main-container">
        <div class="coupon_type">
            <ul>
                <li class="normal_type">
                    <a href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=1 ">普通优惠券</a>
                </li>
                <li class="fenlie_type actives">
                    <a href="/system/coupon/split?shop_id=${data->shop_id!}&nav=1">分裂优惠券</a>
                </li>
                <li class="give_to_sb">
                    <a href="/system/coupon/grant/list?shop_id=${data->shop_id!}&">定向发券</a>
                </li>
            </ul>
        </div>

        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li <#if ($nav_type==0)class="active"</#if>>
                        <a href="/system/coupon/split?shop_id=${data->shop_id!}&nav=0" >所有分裂优惠券</a>
                    </li>
                    <li <#if ($nav_type==1)class="active"</#if>>
                        <a href="/system/coupon/split?shop_id=${data->shop_id!}&nav=1">进行中</a>
                    </li>
                    <li <#if ($nav_type==2)class="active"</#if>>
                        <a href="/system/coupon/split?shop_id=${data->shop_id!}&nav=2" >未生效</a>
                    </li>
                    <li <#if ($nav_type==3)class="active"</#if>>
                        <a href="/system/coupon/split?shop_id=${data->shop_id!}&nav=3">已过期</a>
                    </li>
                    <li <#if ($nav_type==4)class="active"</#if>>
                        <a href="/system/coupon/split?shop_id=${data->shop_id!}&nav=4">已停用</a>
                    </li>
                </ul>
            </div>
            <ul class="add-child-ul">
                <li>
                    <a href="/system/coupon/add?shop_id=${data->shop_id!}&type=1" class="add-child-btn" target="_blank">
                        {{ trans("admin/market_manage.split_coupon_list.add_split_coupon")!}
                    </a>
                </li>
            </ul>
            <div class="return-goods-box">
                <form action="/system/coupon/split?shop_id=${data->shop_id!}&" method="post" id="form1">
                    {{csrf_field()!}
                    <input name="del" type="hidden">
                    <input name="delete" type="hidden">
                    <input name="nav" type="hidden" value="${nav_type!}">
                    <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                    <tr>
                                        <th width="8%">{{ trans("admin/market_manage.split_coupon_list.activity_name")!}</th>
                                        <th width="12%">{{ trans("admin/market_manage.split_coupon_list.activity_date_limit")!}</th>
                                        <th width="10%">{{ trans("admin/market_manage.split_coupon_list.coupon_name")!}</th>
                                        <th width="8%">{{ trans("admin/market_manage.split_coupon_list.coupon_count")!}</th>
                                        <th width="8%">{{ trans("admin/market_manage.split_coupon_list.coupon_price")!}</th>
                                        <th width="12%">{{ trans("admin/market_manage.split_coupon_list.coupon_data_limit")!}</th>
                                        <th width="8%">{{ trans("admin/market_manage.split_coupon_list.get_person")!}</th>
                                        <th width="7%">{{ trans("admin/market_manage.split_coupon_list.used")!}</th>
                                        <th width="7%">{{ trans("admin/market_manage.split_coupon_list.activity_state")!}</th>
                                        <th width="12%">{{ trans("admin/market_manage.split_coupon_list.operation")!}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <#list ($coupon_list as $item)
                                    <tr>
                                        <td>${item->activity_names!}</td>
                                        <td>${item->act_start_time!} 至 ${item->act_end_time!}</td>
                                        <td>${item->act_name!}</td>
                                        <td>${item->surplus!}</td>
                                        <td><#if ($item->act_code=='voucher')${item->denomination!}元 <#else>${item->denomination!}折 </#if></td>
                                        <td><#if ($item->validity >0)领取开始${item->validity!}天内有效 <#else> ${item->start_time!} 至 ${item->end_time!}</#if></td>
                                        <td>${item->receive_person!}/${item->receive_amount!}</td>
                                        <td>${item->used_amount!}</td>
                                        <td>
                                            <#if ($item->enabled==0)
                                        已停用
                                                <#elseif>($item->act_end_time < date("Y-m-d H:i:s",time()))
                                                已过期

                                                <#elseif>($item->act_start_time < date("Y-m-d H:i:s",time()))
                                            进行中
                                                <#elseif>($item->act_start_time > date("Y-m-d H:i:s",time()))
                                                未生效
                                                </#if>
                                        </td>
                                        <td>
                                            <a href="/system/coupon/edit?shop_id=${data->shop_id!}&id=${item->id!}&type=1"
                                               <#if ($item->enabled == 0 || $item->act_end_time < date("Y-m-d H:i:s",time()))
                                            hidden </#if> target="_blank">编辑</a>
                                            <a  href="javascript:void(0)" class="abort" act_id="${item->id!}"
                                                <#if ($item->enabled == 0 || $item->act_end_time < date("Y-m-d H:i:s",time()))
                                                hidden </#if> >停用</a>
                                            <a href="/system/coupon/get/list?shop_id=${data->shop_id!}&id=${item->id!}&type=1" target="_blank">{{ trans("admin/market_manage.coupon_manage.get_detail")!}</a>
                                            <#if ($item->enabled==0 || $item->act_end_time < date("Y-m-d H:i:s",time()))
                                                <a href="#" class="del" coupon_id="${item->id!}">删除</a>
                                            </#if>

                                        </td>
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="paging_footer">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$coupon_list->perPage(),'currentPage'=>$coupon_list->currentPage(),'count'=>$coupon_list->count,'total'=>$coupon_list->total(),])!}
                                    <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${coupon_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${coupon_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${coupon_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text" value="${coupon_list->currentPage()!}"
                                           size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function gopage(page) {
        var last_page = '${coupon_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    $('.abort').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用吗？' + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="del"]').val(_this.attr('act_id'));
                $("#form1").submit();
            });
        });
    });
    $(".del").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除么' + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="delete"]').val(_this.attr("coupon_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
</script>
<script>
    var page_home = '${coupon_list->currentPage()!}'; //当前页码数
    var page_all = '${coupon_list->count!}'            //总页码数
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")